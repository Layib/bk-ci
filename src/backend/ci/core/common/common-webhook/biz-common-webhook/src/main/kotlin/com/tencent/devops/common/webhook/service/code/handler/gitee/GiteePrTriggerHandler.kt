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
import com.tencent.devops.common.webhook.annotation.CodeWebhookHandler
import com.tencent.devops.common.webhook.pojo.code.WebHookParams
import com.tencent.devops.common.webhook.service.code.filter.WebhookFilter
import com.tencent.devops.repository.pojo.Repository
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_AUTHOR
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_NUMBER
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_SOURCE_BRANCH
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_SOURCE_URL
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_TARGET_BRANCH
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_TARGET_URL
import com.tencent.devops.common.webhook.pojo.code.BK_REPO_GIT_WEBHOOK_MR_TITLE
import com.tencent.devops.common.webhook.pojo.code.GITEE_PR_NUMBER
import com.tencent.devops.common.webhook.pojo.code.gitee.GiteePullRequestEvent
import com.tencent.devops.common.webhook.service.code.filter.BranchFilter
import com.tencent.devops.common.webhook.service.code.filter.ProjectNameFilter
import com.tencent.devops.common.webhook.service.code.filter.UserFilter
import com.tencent.devops.common.webhook.service.code.handler.CodeWebhookTriggerHandler
import com.tencent.devops.common.webhook.service.code.pojo.WebhookMatchResult
import com.tencent.devops.common.webhook.util.WebhookUtils
import com.tencent.devops.process.engine.service.code.filter.CommitMessageFilter
import org.slf4j.LoggerFactory

@CodeWebhookHandler
@SuppressWarnings("TooManyFunctions")
class GiteePrTriggerHandler : CodeWebhookTriggerHandler<GiteePullRequestEvent> {

    companion object {
        private val logger = LoggerFactory.getLogger(GiteePrTriggerHandler::class.java)
    }

    override fun eventClass(): Class<GiteePullRequestEvent> {
        return GiteePullRequestEvent::class.java
    }

    override fun getUrl(event: GiteePullRequestEvent): String {
        return event.hookURL
    }

    override fun getUsername(event: GiteePullRequestEvent): String {
        return event.sender.login.toString()
    }

    override fun getRevision(event: GiteePullRequestEvent): String {
        return event.repository.cloneURL
    }

    override fun getRepoName(event: GiteePullRequestEvent): String {
        return event.repository.fullName
    }

    override fun getBranchName(event: GiteePullRequestEvent): String {
        return event.targetBranch
    }

     fun getSourceBranchName(event: GiteePullRequestEvent): String {
        return event.sourceBranch
    }

    override fun getEventType(): CodeEventType {
        return CodeEventType.MERGE_REQUEST
    }

    override fun getMessage(event: GiteePullRequestEvent): String? {
        return event.title
    }

    override fun getEnv(event: GiteePullRequestEvent): Map<String, Any> {
        return mapOf(GITEE_PR_NUMBER to event.number)
    }

    override fun getHookSourceUrl(event: GiteePullRequestEvent): String? {
        return event.sourceRepo.project.cloneURL
    }

    override fun getHookTargetUrl(event: GiteePullRequestEvent): String? {
        return event.targetUser?.url
    }

    override fun preMatch(event: GiteePullRequestEvent): WebhookMatchResult {
        if (!(event.action == "opened" || event.action == "reopened" || event.action == "synchronize")) {
            logger.info("Gitee pull request no open or update")
            return WebhookMatchResult(false)
        }
        return WebhookMatchResult(true)
    }

    override fun getWebhookFilters(
        event: GiteePullRequestEvent,
        projectId: String,
        pipelineId: String,
        repository: Repository,
        webHookParams: WebHookParams,
    ): List<WebhookFilter> {
        with(webHookParams) {
            val projectNameFilter = ProjectNameFilter(
                pipelineId = pipelineId,
                projectName = repository.projectName,
                triggerOnProjectName = event.repository.fullName
            )
            val branchFilter = BranchFilter(
                pipelineId = pipelineId,
                triggerOnBranchName = getBranchName(event),
                includedBranches = WebhookUtils.convert(branchName),
                excludedBranches = WebhookUtils.convert(excludeBranchName)
            )
            // 源分支过滤器
            val sourceBranchFilter = BranchFilter(
                pipelineId = pipelineId,
                triggerOnBranchName = getSourceBranchName(event),
                includedBranches = WebhookUtils.convert(includeSourceBranchName),
                excludedBranches = WebhookUtils.convert(excludeSourceBranchName)
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
            return listOf(projectNameFilter, userFilter, branchFilter, sourceBranchFilter, commitMessageFilter)
        }
    }

    override fun retrieveParams(
        event: GiteePullRequestEvent,
        projectId: String?,
        repository: Repository?
    ): Map<String, Any> {
        val startParams = mutableMapOf<String, Any>()
        startParams[BK_REPO_GIT_WEBHOOK_MR_AUTHOR] = event.sender.login.toString()
        startParams[BK_REPO_GIT_WEBHOOK_MR_NUMBER] = event.number
        startParams[BK_REPO_GIT_WEBHOOK_MR_TARGET_URL] = event.targetRepo.project.cloneURL
        startParams[BK_REPO_GIT_WEBHOOK_MR_SOURCE_URL] = event.sourceRepo.project.cloneURL
        startParams[BK_REPO_GIT_WEBHOOK_MR_TITLE] = event.title
        startParams[BK_REPO_GIT_WEBHOOK_MR_TARGET_BRANCH] = event.targetBranch
        startParams[BK_REPO_GIT_WEBHOOK_MR_SOURCE_BRANCH] = event.sourceBranch
        return startParams
    }
}
