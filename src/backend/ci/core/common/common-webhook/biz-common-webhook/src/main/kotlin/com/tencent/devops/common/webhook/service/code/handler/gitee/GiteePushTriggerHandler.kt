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

package com.tencent.devops.common.webhook.service.code.handler.gitee

import com.tencent.devops.common.pipeline.pojo.element.trigger.enums.CodeEventType
import com.tencent.devops.common.pipeline.pojo.element.trigger.enums.PathFilterType
import com.tencent.devops.common.webhook.annotation.CodeWebhookHandler
import com.tencent.devops.common.webhook.pojo.code.WebHookParams
import com.tencent.devops.common.webhook.service.code.filter.ProjectNameFilter
import com.tencent.devops.common.webhook.service.code.filter.UserFilter
import com.tencent.devops.common.webhook.service.code.filter.WebhookFilter
import com.tencent.devops.common.webhook.service.code.handler.CodeWebhookTriggerHandler
import com.tencent.devops.common.webhook.util.WebhookUtils
import com.tencent.devops.repository.pojo.Repository
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_SVN_WEBHOOK_COMMIT_TIME
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_SVN_WEBHOOK_REVERSION
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_SVN_WEBHOOK_USERNAME
import com.tencent.devops.common.webhook.pojo.code.gitee.GiteePushEvent
import com.tencent.devops.common.webhook.service.code.filter.BranchFilter
import com.tencent.devops.common.webhook.service.code.filter.PathPrefixFilter
import com.tencent.devops.process.engine.service.code.filter.CommitMessageFilter

@CodeWebhookHandler
@SuppressWarnings("TooManyFunctions")
class GiteePushTriggerHandler : CodeWebhookTriggerHandler<GiteePushEvent> {
    override fun eventClass(): Class<GiteePushEvent> {
        return GiteePushEvent::class.java
    }

    override fun getUrl(event: GiteePushEvent): String {
        return event.hookURL ?: ""
    }

    override fun getUsername(event: GiteePushEvent): String {
        return event.pusher?.userName ?: ""
    }

    override fun getRevision(event: GiteePushEvent): String {
        return event.repository?.cloneURL ?: ""
    }

    override fun getRepoName(event: GiteePushEvent): String {
        return event.repository?.fullName ?: ""
    }

    override fun getBranchName(event: GiteePushEvent): String {
        val branchNameSplit = event.ref?.split("refs/heads/")
        // gitee的提交分支名很奇怪：  ref": "refs/heads/dev"
        // 只能切割字符串取最后一个判断为变动的分支名
        return branchNameSplit?.get(1)?.trim().toString()
    }

    override fun getEventType(): CodeEventType {
        return CodeEventType.PUSH
    }

    override fun getMessage(event: GiteePushEvent): String? {
        val messageList = event.commits?.map { it?.message }
        // 只包含一个commit信息的情况
        if(messageList?.size == 1){
            return messageList.first()
        }
        // 拼接多个commit的message取判断
        val messageString = StringBuilder()
        messageList?.forEach {
            messageString.append(it)
        }
        return messageString.toString()
    }

    fun getPathList(event: GiteePushEvent): List<String> {
        val pathList = mutableListOf<String>()
        event.commits?.forEach { commit ->
            commit.modified.forEach {
                pathList.add(it)
            }
        }
        return pathList
    }

    override fun getWebhookFilters(
        event: GiteePushEvent,
        projectId: String,
        pipelineId: String,
        repository: Repository,
        webHookParams: WebHookParams
    ): List<WebhookFilter> {
        with(webHookParams) {
            val projectNameFilter = ProjectNameFilter(
                pipelineId = pipelineId,
                projectName = repository.projectName,
                triggerOnProjectName = event.repository?.fullName  ?: ""
            )
            val branchFilter = BranchFilter(
                pipelineId = pipelineId,
                triggerOnBranchName = getBranchName(event),
                includedBranches = WebhookUtils.convert(branchName),
                excludedBranches = WebhookUtils.convert(excludeBranchName)
            )
            val userFilter = UserFilter(
                pipelineId = pipelineId,
                triggerOnUser = getUsername(event),
                includedUsers = WebhookUtils.convert(includeUsers),
                excludedUsers = WebhookUtils.convert(excludeUsers)
            )
            val commitMessageFilter = CommitMessageFilter(
                includeCommitMsg = includeCommitMsg,
                excludeCommitMsg = excludeCommitMsg,
                eventCommitMessage = getMessage(event) ?: "",
                pipelineId = pipelineId
            )
            val pathFilter = PathPrefixFilter(
                pipelineId = pipelineId,
                triggerOnPath = getPathList(event),
                includedPaths = WebhookUtils.convert(includePaths),
                excludedPaths = WebhookUtils.convert(excludePaths)
            )
            //tagName的匹配规则跟分支匹配规则一致，可以复用
            val tagNameFilter = BranchFilter(
                pipelineId = pipelineId,
                triggerOnBranchName = getBranchName(event),
                includedBranches = WebhookUtils.convert(tagName),
                excludedBranches = WebhookUtils.convert(excludeTagName)
            )
            return listOf(projectNameFilter, userFilter, branchFilter, commitMessageFilter, pathFilter, tagNameFilter)
        }
    }

    private fun WebHookParams.getIncludePaths(projectRelativePath: String) =
        // 如果没有配置包含路径，则需要跟代码库url做对比
        if (relativePath.isNullOrBlank()) {
            // 模糊匹配需要包含整个路径
            if (pathFilterType == PathFilterType.NamePrefixFilter) {
                listOf(
                    WebhookUtils.getFullPath(
                        projectRelativePath = projectRelativePath,
                        relativeSubPath = ""
                    )
                )
            } else {
                listOf(
                    WebhookUtils.getFullPath(
                        projectRelativePath = projectRelativePath,
                        relativeSubPath = "**"
                    )
                )
            }
        } else {
            WebhookUtils.convert(relativePath).map { path ->
                WebhookUtils.getFullPath(
                    projectRelativePath = projectRelativePath,
                    relativeSubPath = path
                )
            }
        }

    override fun retrieveParams(
        event: GiteePushEvent,
        projectId: String?,
        repository: Repository?
    ): Map<String, Any> {
        val startParams = mutableMapOf<String, Any>()
        startParams[BK_REPO_SVN_WEBHOOK_REVERSION] = event.repository?.cloneURL  ?: ""
        startParams[BK_REPO_SVN_WEBHOOK_USERNAME] = event.headCommit?.author?.name  ?: ""
        startParams[BK_REPO_SVN_WEBHOOK_COMMIT_TIME] = event.headCommit?.timestamp ?: 0L
        return startParams
    }
}
