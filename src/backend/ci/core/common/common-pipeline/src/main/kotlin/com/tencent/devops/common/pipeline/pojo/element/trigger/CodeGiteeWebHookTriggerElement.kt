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

package com.tencent.devops.common.pipeline.pojo.element.trigger

import com.tencent.devops.common.api.enums.RepositoryType
import com.tencent.devops.common.pipeline.enums.StartType
import com.tencent.devops.common.pipeline.pojo.element.trigger.enums.CodeEventType
import com.tencent.devops.common.pipeline.pojo.element.trigger.enums.PathFilterType

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "Gitee事件触发", description = CodeGiteeWebHookTriggerElement.classType)
data class CodeGiteeWebHookTriggerElement(
    @get:Schema(title = "任务名称", required = true)
    override val name: String = "Gitee变更触发",
    @get:Schema(title = "id", required = false)
    override var id: String? = null,
    @get:Schema(title = "状态", required = false)
    override var status: String? = null,
    @get:Schema(title = "仓库ID", required = true)
    val repositoryHashId: String?,
    @get:Schema(title = "branch", required = false)
    val branchName: String?,
    @get:Schema(title = "新版的gitee原子的类型", required = false)
    val repositoryType: RepositoryType? = null,
    @get:Schema(title = "新版的gitee代码库名", required = false)
    val repositoryName: String? = null,
    @get:Schema(title = "eventType", required = false)
    val eventType: CodeEventType? = CodeEventType.PUSH,
    @get:Schema(title = "excludeBranch", required = false)
    val excludeBranchName: String?,
    @get:Schema(title = "路径过滤类型", required = true)
    val pathFilterType: PathFilterType? = PathFilterType.NamePrefixFilter,
    @get:Schema(title = "includePaths", required = false)
    val includePaths: String?,
    @get:Schema(title = "excludePaths", required = false)
    val excludePaths: String?,
    @get:Schema(title = "includeUsers", required = false)
    val includeUsers: List<String>? = null,
    @get:Schema(title = "excludeUsers", required = false)
    val excludeUsers: List<String>?,
    @get:Schema(title = "block", required = false)
    val block: Boolean?,
    @get:Schema(title = "tagName", required = false)
    val tagName: String? = null,
    @get:Schema(title = "excludeTagName", required = false)
    val excludeTagName: String? = null,
    @get:Schema(title = "excludeSourceBranchName", required = false)
    val excludeSourceBranchName: String? = null,
    @get:Schema(title = "includeSourceBranchName", required = false)
    val includeSourceBranchName: String? = null,
    @get:Schema(title = "includeCommitMsg", required = false)
    val includeCommitMsg: String? = null,
    @get:Schema(title = "excludeCommitMsg", required = false)
    val excludeCommitMsg: String? = null
) : WebHookTriggerElement(name, id, status) {
    companion object {
        const val classType = "codeGiteeWebHookTrigger"
    }

    override fun getClassType() = classType

    override fun findFirstTaskIdByStartType(startType: StartType): String {
        return if (startType.name == StartType.WEB_HOOK.name) {
            this.id!!
        } else {
            super.findFirstTaskIdByStartType(startType)
        }
    }
}
