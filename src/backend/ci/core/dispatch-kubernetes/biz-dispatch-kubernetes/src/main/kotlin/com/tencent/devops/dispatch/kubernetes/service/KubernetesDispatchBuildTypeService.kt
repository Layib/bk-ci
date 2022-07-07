/*
 * Tencent is pleased to support the open source community by making BK-CI 蓝鲸持续集成平台 available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-CI 蓝鲸持续集成平台 is licensed under the MIT license.
 *
 * A copy of the MIT License is included in this file.
 *
 *
 * Terms of the MIT License:
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tencent.devops.dispatch.kubernetes.service

import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.dispatch.sdk.BuildFailureException
import com.tencent.devops.common.dispatch.sdk.pojo.DispatchMessage
import com.tencent.devops.common.pipeline.type.BuildType
import com.tencent.devops.dispatch.common.common.BUILDER_NAME
import com.tencent.devops.dispatch.common.common.ENV_JOB_BUILD_TYPE
import com.tencent.devops.dispatch.common.common.ENV_KEY_AGENT_ID
import com.tencent.devops.dispatch.common.common.ENV_KEY_AGENT_SECRET_KEY
import com.tencent.devops.dispatch.common.common.ENV_KEY_GATEWAY
import com.tencent.devops.dispatch.common.common.ENV_KEY_PROJECT_ID
import com.tencent.devops.dispatch.common.common.SLAVE_ENVIRONMENT
import com.tencent.devops.dispatch.kubernetes.client.KubernetesBuilderClient
import com.tencent.devops.dispatch.kubernetes.client.KubernetesTaskClient
import com.tencent.devops.dispatch.kubernetes.common.ConstantsMessage
import com.tencent.devops.dispatch.kubernetes.common.ErrorCodeEnum
import com.tencent.devops.dispatch.kubernetes.components.LogsPrinter
import com.tencent.devops.dispatch.kubernetes.interfaces.DispatchBuildLog
import com.tencent.devops.dispatch.kubernetes.interfaces.DispatchBuildTypeService
import com.tencent.devops.dispatch.kubernetes.pojo.Builder
import com.tencent.devops.dispatch.kubernetes.pojo.DeleteBuilderParams
import com.tencent.devops.dispatch.kubernetes.pojo.DockerRegistry
import com.tencent.devops.dispatch.kubernetes.pojo.Pool
import com.tencent.devops.dispatch.kubernetes.pojo.StartBuilderParams
import com.tencent.devops.dispatch.kubernetes.pojo.StopBuilderParams
import com.tencent.devops.dispatch.kubernetes.pojo.TaskStatusEnum
import com.tencent.devops.dispatch.kubernetes.pojo.builds.DispatchBuildBuilderStatus
import com.tencent.devops.dispatch.kubernetes.pojo.builds.DispatchBuildOperateBuilderParams
import com.tencent.devops.dispatch.kubernetes.pojo.builds.DispatchBuildOperateBuilderType
import com.tencent.devops.dispatch.kubernetes.pojo.builds.DispatchBuildTaskStatus
import com.tencent.devops.dispatch.kubernetes.pojo.builds.DispatchBuildTaskStatusEnum
import com.tencent.devops.dispatch.kubernetes.pojo.hasException
import com.tencent.devops.dispatch.kubernetes.pojo.readyToStart
import com.tencent.devops.dispatch.kubernetes.utils.CommonUtils
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service("kubernetesDispatchBuildTypeService")
class KubernetesDispatchBuildTypeService @Autowired constructor(
    private val logsPrinter: LogsPrinter,
    private val kubernetesTaskClient: KubernetesTaskClient,
    private val kubernetesBuilderClient: KubernetesBuilderClient
) : DispatchBuildTypeService {

    companion object {
        private val logger = LoggerFactory.getLogger(KubernetesDispatchBuildTypeService::class.java)
    }

    override val shutdownLockBaseKey = "dispatch_kubernetes_shutdown_lock_"

    override val log = DispatchBuildLog(
        readyStartLog = "准备创建kubernetes构建机...",
        startContainerError = "启动kubernetes构建容器失败，请联系蓝盾助手反馈处理.\n容器构建异常请参考：",
        troubleShooting = "Kubernetes构建异常，请联系蓝盾助手排查，异常信息 - "
    )

    @Value("\${bcs.resources.builder.cpu}")
    override var cpu: Double = 32.0

    @Value("\${bcs.resources.builder.memory}")
    override var memory: String = "65535"

    @Value("\${bcs.resources.builder.disk}")
    override var disk: String = "500"

    @Value("\${bcs.entrypoint}")
    override val entrypoint: String = "bcs_init.sh"

    override val helpUrl: String? = ""

    override fun getBuilderStatus(
        buildId: String,
        vmSeqId: String,
        userId: String,
        builderName: String,
        retryTime: Int
    ): Result<DispatchBuildBuilderStatus> {
        val result = kubernetesBuilderClient.getBuilderDetail(
            buildId = buildId,
            vmSeqId = vmSeqId,
            userId = userId,
            name = builderName,
            retryTime = retryTime
        )
        if (result.isNotOk()) {
            return Result(result.status, result.message)
        }

        val status = when {
            result.data!!.readyToStart() -> DispatchBuildBuilderStatus.READY_START
            result.data.hasException() -> DispatchBuildBuilderStatus.HAS_EXCEPTION
            else -> DispatchBuildBuilderStatus.BUSY
        }

        return Result(status)
    }

    override fun operateBuilder(
        buildId: String,
        vmSeqId: String,
        userId: String,
        builderName: String,
        param: DispatchBuildOperateBuilderParams
    ): String {
        return kubernetesBuilderClient.operateBuilder(
            buildId = buildId,
            vmSeqId = vmSeqId,
            userId = userId,
            name = builderName,
            param = when (param.type) {
                DispatchBuildOperateBuilderType.DELETE -> DeleteBuilderParams()
                DispatchBuildOperateBuilderType.STOP -> StopBuilderParams()
            }
        )
    }

    override fun createAndStartBuilder(
        dispatchMessages: DispatchMessage,
        containerPool: Pool,
        poolNo: Int,
        cpu: Double,
        mem: String,
        disk: String
    ): Pair<String, String> {
        with(dispatchMessages) {
            val (host, name, tag) = CommonUtils.parseImage(containerPool.container!!)
            val userName = containerPool.credential?.user
            val password = containerPool.credential?.password

            val builderName = getOnlyName(userId)
            val bcsTaskId = kubernetesBuilderClient.createBuilder(
                buildId = buildId,
                vmSeqId = vmSeqId,
                userId = userId,
                bcsBuilder = Builder(
                    name = builderName,
                    image = "$name:$tag",
                    registry = DockerRegistry(host, userName, password),
                    cpu = cpu,
                    mem = mem,
                    disk = disk,
                    env = mapOf(
                        ENV_KEY_PROJECT_ID to projectId,
                        ENV_KEY_AGENT_ID to id,
                        ENV_KEY_AGENT_SECRET_KEY to secretKey,
                        ENV_KEY_GATEWAY to gateway,
                        "TERM" to "xterm-256color",
                        SLAVE_ENVIRONMENT to "Bcs",
                        ENV_JOB_BUILD_TYPE to (dispatchType?.buildType()?.name ?: BuildType.PUBLIC_BCS.name),
                        BUILDER_NAME to builderName
                    ),
                    command = listOf("/bin/sh", entrypoint)
                )
            )
            logger.info(
                "buildId: $buildId,vmSeqId: $vmSeqId,executeCount: $executeCount,poolNo: $poolNo createBuilder, " +
                    "taskId:($bcsTaskId)"
            )
            logsPrinter.printLogs(
                this,
                "下发创建构建机请求成功，" +
                    "builderName: $builderName 等待机器创建..."
            )

            val (taskStatus, failedMsg) = kubernetesTaskClient.waitTaskFinish(userId, bcsTaskId)

            if (taskStatus == TaskStatusEnum.SUCCEEDED) {
                // 启动成功
                logger.info(
                    "buildId: $buildId,vmSeqId: $vmSeqId,executeCount: $executeCount,poolNo: $poolNo create bcs " +
                        "vm success, wait vm start..."
                )
                logsPrinter.printLogs(this, "构建机创建成功，等待机器启动...")
            } else {
                // 清除构建异常容器，并重新置构建池为空闲
                clearExceptionBuilder(builderName)
                throw BuildFailureException(
                    ErrorCodeEnum.CREATE_VM_ERROR.errorType,
                    ErrorCodeEnum.CREATE_VM_ERROR.errorCode,
                    ErrorCodeEnum.CREATE_VM_ERROR.formatErrorMessage,
                    "${ConstantsMessage.TROUBLE_SHOOTING}构建机创建失败:${failedMsg ?: taskStatus.message}"
                )
            }
            return Pair(startBuilder(dispatchMessages, builderName, poolNo, cpu, mem, disk), builderName)
        }
    }

    override fun startBuilder(
        dispatchMessages: DispatchMessage,
        builderName: String,
        poolNo: Int,
        cpu: Double,
        mem: String,
        disk: String
    ): String {
        with(dispatchMessages) {
            val bcsTaskId = kubernetesBuilderClient.operateBuilder(
                buildId = buildId,
                vmSeqId = vmSeqId,
                userId = userId,
                name = builderName,
                param = StartBuilderParams(
                    env = mapOf(
                        ENV_KEY_PROJECT_ID to projectId,
                        ENV_KEY_AGENT_ID to id,
                        ENV_KEY_AGENT_SECRET_KEY to secretKey,
                        ENV_KEY_GATEWAY to gateway,
                        "TERM" to "xterm-256color",
                        SLAVE_ENVIRONMENT to "Bcs",
                        ENV_JOB_BUILD_TYPE to (dispatchType?.buildType()?.name ?: BuildType.PUBLIC_BCS.name),
                        BUILDER_NAME to builderName
                    ),
                    command = listOf("/bin/sh", entrypoint)
                )
            )

            return bcsTaskId
        }
    }

    private fun DispatchMessage.clearExceptionBuilder(builderName: String) {
        try {
            // 下发删除，不管成功失败
            logger.info("[$buildId]|[$vmSeqId] Delete builder, userId: $userId, builderName: $builderName")
            kubernetesBuilderClient.operateBuilder(
                buildId = buildId,
                vmSeqId = vmSeqId,
                userId = userId,
                name = builderName,
                param = DeleteBuilderParams()
            )
        } catch (e: Exception) {
            logger.error("[$buildId]|[$vmSeqId] delete builder failed", e)
        }
    }

    override fun waitTaskFinish(userId: String, taskId: String): DispatchBuildTaskStatus {
        val startResult = kubernetesTaskClient.waitTaskFinish(userId, taskId)
        if (startResult.first == TaskStatusEnum.SUCCEEDED) {
            return DispatchBuildTaskStatus(DispatchBuildTaskStatusEnum.SUCCEEDED, null)
        } else {
            return DispatchBuildTaskStatus(DispatchBuildTaskStatusEnum.FAILED, startResult.second)
        }
    }

    private fun getOnlyName(userId: String) = "bcs-${userId}${System.currentTimeMillis()}-" +
        RandomStringUtils.randomAlphabetic(16)
}
