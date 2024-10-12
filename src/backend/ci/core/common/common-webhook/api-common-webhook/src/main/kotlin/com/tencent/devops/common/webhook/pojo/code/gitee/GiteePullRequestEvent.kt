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

package com.tencent.devops.common.webhook.pojo.code.gitee

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GiteePullRequestEvent(
    @get:JsonProperty @field:JsonProperty
    val action: String?,
    @get:JsonProperty("action_desc") @field:JsonProperty("action_desc")
    val actionDesc: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val author: Author,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val body: String,
    val enterprise: Any? = null,
    @get:JsonProperty("hook_id") @field:JsonProperty("hook_id")
    val hookID: Long,
    @get:JsonProperty("hook_name") @field:JsonProperty("hook_name")
    val hookName: String,
    @get:JsonProperty("hook_url") @field:JsonProperty("hook_url")
    val hookURL: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val iid: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val languages: List<Any?>,
    @get:JsonProperty("merge_commit_sha") @field:JsonProperty("merge_commit_sha")
    val mergeCommitSHA: Any? = null,
    @get:JsonProperty("merge_status") @field:JsonProperty("merge_status")
    val mergeStatus: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val number: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val password: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val project: Project,
    @get:JsonProperty("pull_request") @field:JsonProperty("pull_request")
    val pullRequest: PullRequest,
    @get:JsonProperty("push_data") @field:JsonProperty("push_data")
    val pushData: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val repository: Project,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    override val sender: GiteeSender,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val sign: String,
    @get:JsonProperty("source_branch") @field:JsonProperty("source_branch")
    val sourceBranch: String,
    @get:JsonProperty("source_repo") @field:JsonProperty("source_repo")
    val sourceRepo: Repo,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val state: String,
    @get:JsonProperty("target_branch") @field:JsonProperty("target_branch")
    val targetBranch: String,
    @get:JsonProperty("target_repo") @field:JsonProperty("target_repo")
    val targetRepo: Repo,
    @get:JsonProperty("target_user") @field:JsonProperty("target_user")
    val targetUser: Author?,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val timestamp: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val title: String,
    @get:JsonProperty("updated_by") @field:JsonProperty("updated_by")
    val updatedBy: Author,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val url: String
) : GiteeEvent(sender) {
    companion object {
        const val classType = "merge_request_hooks"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Project(
    @get:JsonProperty("clone_url") @field:JsonProperty("clone_url")
    val cloneURL: String,
    @get:JsonProperty("created_at") @field:JsonProperty("created_at")
    val createdAt: String,
    @get:JsonProperty("default_branch") @field:JsonProperty("default_branch")
    val defaultBranch: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val description: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val fork: Boolean,
    @get:JsonProperty("forks_count") @field:JsonProperty("forks_count")
    val forksCount: Long,
    @get:JsonProperty("full_name") @field:JsonProperty("full_name")
    val fullName: String,
    @get:JsonProperty("git_http_url") @field:JsonProperty("git_http_url")
    val gitHTTPURL: String,
    @get:JsonProperty("git_ssh_url") @field:JsonProperty("git_ssh_url")
    val gitSSHURL: String,
    @get:JsonProperty("git_svn_url") @field:JsonProperty("git_svn_url")
    val gitSvnURL: String,
    @get:JsonProperty("git_url") @field:JsonProperty("git_url")
    val gitURL: String,
    @get:JsonProperty("has_issues") @field:JsonProperty("has_issues")
    val hasIssues: Boolean,
    @get:JsonProperty("has_pages") @field:JsonProperty("has_pages")
    val hasPages: Boolean,
    @get:JsonProperty("has_wiki") @field:JsonProperty("has_wiki")
    val hasWiki: Boolean,
    val homepage: Any? = null,
    @get:JsonProperty("html_url") @field:JsonProperty("html_url")
    val htmlURL: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val id: Long,
    val language: Any? = null,
    val license: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val name: String,
    @get:JsonProperty("name_with_namespace") @field:JsonProperty("name_with_namespace")
    val nameWithNamespace: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val namespace: String,
    @get:JsonProperty("open_issues_count") @field:JsonProperty("open_issues_count")
    val openIssuesCount: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val owner: Author,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val path: String,
    @get:JsonProperty("path_with_namespace") @field:JsonProperty("path_with_namespace")
    val pathWithNamespace: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val private: Boolean,
    @get:JsonProperty("pushed_at") @field:JsonProperty("pushed_at")
    val pushedAt: String,
    @get:JsonProperty("ssh_url") @field:JsonProperty("ssh_url")
    val sshURL: String,
    @get:JsonProperty("stargazers_count") @field:JsonProperty("stargazers_count")
    val stargazersCount: Long,
    @get:JsonProperty("svn_url") @field:JsonProperty("svn_url")
    val svnURL: String,
    @get:JsonProperty("updated_at") @field:JsonProperty("updated_at")
    val updatedAt: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val url: String,
    @get:JsonProperty("watchers_count") @field:JsonProperty("watchers_count")
    val watchersCount: Long
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PullRequest(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val additions: Long,
    val assignee: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val assignees: List<Author>,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val base: Base,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val body: String,
    @get:JsonProperty("changed_files") @field:JsonProperty("changed_files")
    val changedFiles: Long,
    @get:JsonProperty("closed_at") @field:JsonProperty("closed_at")
    val closedAt: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val comments: Long,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val commits: Long,
    @get:JsonProperty("created_at") @field:JsonProperty("created_at")
    val createdAt: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val deletions: Long,
    @get:JsonProperty("diff_url") @field:JsonProperty("diff_url")
    val diffURL: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val head: Base,
    @get:JsonProperty("html_url") @field:JsonProperty("html_url")
    val htmlURL: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val id: Long,
    @get:JsonProperty("issues") @field:JsonProperty("issues")
    val issues: List<Any?>,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val labels: List<Any?>,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val languages: List<Any?>,
    @get:JsonProperty("merge_commit_sha") @field:JsonProperty("merge_commit_sha")
    val mergeCommitSHA: Any? = null,
    @get:JsonProperty("merge_reference_name") @field:JsonProperty("merge_reference_name")
    val mergeReferenceName: String,
    @get:JsonProperty("merge_status") @field:JsonProperty("merge_status")
    val mergeStatus: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val mergeable: Boolean,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val merged: Boolean,
    @get:JsonProperty("merged_at") @field:JsonProperty("merged_at")
    val mergedAt: Any? = null,
    val milestone: Any? = null,
    @get:JsonProperty("need_review") @field:JsonProperty("need_review")
    val needReview: Boolean,
    @get:JsonProperty("need_test") @field:JsonProperty("need_test")
    val needTest: Boolean,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val number: Long,
    @get:JsonProperty("patch_url") @field:JsonProperty("patch_url")
    val patchURL: String,
    @get:JsonProperty("stale_issues") @field:JsonProperty("stale_issues")
    val staleIssues: List<Any?>,
    @get:JsonProperty("stale_labels") @field:JsonProperty("stale_labels")
    val staleLabels: List<Any?>,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val state: String,
    val tester: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val testers: List<Author>,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val title: String,
    @get:JsonProperty("updated_at") @field:JsonProperty("updated_at")
    val updatedAt: String,
    @get:JsonProperty("updated_by") @field:JsonProperty("updated_by")
    val updatedBy: Author,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val user: Author
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Base(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val label: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val ref: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val repo: Project,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val sha: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val user: Author
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Repo(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val project: Project,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val repository: Project
)
