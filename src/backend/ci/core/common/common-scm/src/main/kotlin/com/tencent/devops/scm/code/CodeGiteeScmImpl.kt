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

package com.tencent.devops.scm.code

import com.tencent.devops.common.api.constant.CommonMessageCode
import com.tencent.devops.common.api.constant.CommonMessageCode.GITEE_HOOK_URL_EMPTY
import com.tencent.devops.common.api.constant.CommonMessageCode.GITEE_TOKEN_EMPTY
import com.tencent.devops.common.api.constant.CommonMessageCode.GITEE_TOKEN_FAIL
import com.tencent.devops.common.api.constant.CommonMessageCode.USER_ACCESS_CHECK_FAIL
import com.tencent.devops.common.api.enums.ScmType
import com.tencent.devops.common.web.utils.I18nUtil
import com.tencent.devops.scm.IScm
import com.tencent.devops.scm.code.git.api.GitApi
import com.tencent.devops.scm.code.gitee.api.GiteeApi
import com.tencent.devops.scm.config.GitConfig
import com.tencent.devops.scm.exception.ScmException
import com.tencent.devops.scm.pojo.GitCommit
import com.tencent.devops.scm.pojo.GitDiff
import com.tencent.devops.scm.pojo.GitMrChangeInfo
import com.tencent.devops.scm.pojo.GitMrInfo
import com.tencent.devops.scm.pojo.RevisionInfo
import com.tencent.devops.scm.utils.code.git.GitUtils
import org.slf4j.LoggerFactory
import java.net.URLEncoder

@SuppressWarnings("TooManyFunctions")
class CodeGiteeScmImpl constructor(
    override val projectName: String,
    override val branchName: String?,
    override val url: String,
    private val token: String,
    gitConfig: GitConfig,
    private val event: String? = null
) : IScm {

    private val apiUrl = GitUtils.getGitApiUrl(apiUrl = gitConfig.giteeApiUrl, repoUrl = url)

    override fun createBranch(branch: String, ref: String) {
        gitApi.createBranch(apiUrl, token, projectName, branch, ref)
    }

    override fun deleteBranch(branch: String) {
        return gitApi.deleteBranch(apiUrl, token, projectName, branch)
    }

    override fun getCommits(branch: String?, all: Boolean, page: Int, size: Int): List<GitCommit> {
        return gitApi.listCommits(apiUrl, branch, token, projectName, all, page, size)
    }

    override fun getCommitDiff(sha: String): List<GitDiff> {
        return gitApi.getCommitDiff(apiUrl, sha, token, projectName)
    }

    override fun getLatestRevision(): RevisionInfo {
        val branch = branchName ?: "master"
        val gitBranch = gitApi.getBranch(host = apiUrl, token = token, projectName = projectName, branchName = branch)
        return RevisionInfo(
            revision = gitBranch.commit.id,
            updatedMessage = gitBranch.commit.message,
            branchName = branch,
            authorName = gitBranch.commit.authorName
        )
    }

    override fun getBranches(search: String?, page: Int, pageSize: Int): List<String> {
        return gitApi.listBranches(
            host = apiUrl,
            token = token,
            projectName = projectName,
            search = search,
            page = page,
            pageSize = pageSize
        )
    }

    override fun getTags(search: String?) =
        gitApi.listTags(
            host = apiUrl,
            token = token,
            projectName = projectName,
            search = search
        )

    override fun checkTokenAndPrivateKey() {
        try {
            getBranches()
        } catch (ignored: Throwable) {
            logger.warn("Fail to check the gitee token", ignored)
            throw ScmException(
                I18nUtil.getCodeLanMessage(
                    CommonMessageCode.GIT_INVALID_PRIVATE_KEY_OR_PASSWORD,
                    params = arrayOf(ScmType.CODE_GITEE.name, ignored.message ?: "")
                ),
                ScmType.CODE_GITEE.name
            )
        }
    }

    override fun checkTokenAndUsername() {
        try {
            getBranches()
        } catch (ignored: Throwable) {
            logger.warn("Fail to check the gitee token", ignored)
            throw ScmException(
                ignored.message ?: I18nUtil.getCodeLanMessage(
                    USER_ACCESS_CHECK_FAIL
                ),
                ScmType.CODE_GITEE.name
            )
        }
    }

    override fun addWebHook(hookUrl: String) {
        if (token.isEmpty()) {
            throw ScmException(
                I18nUtil.getCodeLanMessage(GITEE_TOKEN_EMPTY),
                ScmType.CODE_GITEE.name
            )
        }
        if (hookUrl.isEmpty()) {
            throw ScmException(
                I18nUtil.getCodeLanMessage(GITEE_HOOK_URL_EMPTY),
                ScmType.CODE_GITEE.name
            )
        }
        try {
            logger.info("[HOOK_API]|$apiUrl")
            giteeApi.addWebhook(apiUrl, token, projectName, hookUrl, event)
        } catch (ignored: Throwable) {
            throw ScmException(
                ignored.message ?: I18nUtil.getCodeLanMessage(GITEE_TOKEN_FAIL),
                ScmType.CODE_GITEE.name
            )
        }
    }

    override fun addCommitCheck(
        commitId: String,
        state: String,
        targetUrl: String,
        context: String,
        description: String,
        block: Boolean,
        targetBranch: List<String>?
    ) = Unit

    override fun addMRComment(mrId: Long, comment: String) = Unit

    override fun lock(repoName: String, applicant: String, subpath: String) {
        logger.info("gitee can not lock")
    }

    override fun unlock(repoName: String, applicant: String, subpath: String) {
        logger.info("gitlab can not unlock")
    }

    override fun getMergeRequestChangeInfo(mrId: Long): GitMrChangeInfo {
        val url = "projects/${URLEncoder.encode(projectName, "UTF-8")}/merge_requests/$mrId/changes"
        return gitApi.getMergeRequestChangeInfo(
            host = apiUrl,
            token = token,
            url = url
        )
    }

    override fun getMrInfo(mrId: Long): GitMrInfo {
        val url = "projects/${URLEncoder.encode(projectName, "UTF-8")}/merge_requests/$mrId"
        return gitApi.getMrInfo(
            host = apiUrl,
            token = token,
            url = url
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(CodeGiteeScmImpl::class.java)
        private val gitApi = GitApi()
        private val giteeApi = GiteeApi()
    }
}
