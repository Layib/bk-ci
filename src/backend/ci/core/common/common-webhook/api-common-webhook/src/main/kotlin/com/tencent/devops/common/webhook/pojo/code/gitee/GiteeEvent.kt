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
import com.tencent.devops.common.webhook.pojo.code.CodeWebhookEvent

@Suppress("ALL")
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GiteeEvent(
    open val sender: GiteeSender
) : CodeWebhookEvent

@JsonIgnoreProperties(ignoreUnknown = true)
data class GiteeUser(
    val name: String,
    val email: String,
    val username: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Author(
    @get:JsonProperty @field:JsonProperty
    val email: String?,
    @get:JsonProperty @field:JsonProperty
    val id: Long?,
    @get:JsonProperty @field:JsonProperty
    val name: String?,
    val remark: Any? = null,
    val time: String? = null,
    @get:JsonProperty @field:JsonProperty
    val url: String?,
    val user: Any? = null,
    @get:JsonProperty("user_name") @field:JsonProperty("user_name")
    val userName: String?,
    @get:JsonProperty @field:JsonProperty
    val username: String?
)

@Suppress("ALL")
@JsonIgnoreProperties(ignoreUnknown = true)
data class GiteeRepository(
    @get:JsonProperty("clone_url") @field:JsonProperty("clone_url")
    val cloneURL: String?,
    @get:JsonProperty("created_at") @field:JsonProperty("created_at")
    val createdAt: String?,
    @get:JsonProperty("default_branch") @field:JsonProperty("default_branch")
    val defaultBranch: String?,
    @get:JsonProperty @field:JsonProperty
    val description: String?,
    @get:JsonProperty @field:JsonProperty
    val fork: Boolean?,
    @get:JsonProperty("forks_count") @field:JsonProperty("forks_count")
    val forksCount: Long?,
    @get:JsonProperty("full_name") @field:JsonProperty("full_name")
    val fullName: String?,
    @get:JsonProperty("git_http_url") @field:JsonProperty("git_http_url")
    val gitHTTPURL: String?,
    @get:JsonProperty("git_ssh_url") @field:JsonProperty("git_ssh_url")
    val gitSSHURL: String?,
    @get:JsonProperty("git_svn_url") @field:JsonProperty("git_svn_url")
    val gitSvnURL: String?,
    @get:JsonProperty("git_url") @field:JsonProperty("git_url")
    val gitURL: String?,
    @get:JsonProperty("has_issues") @field:JsonProperty("has_issues")
    val hasIssues: Boolean?,
    @get:JsonProperty("has_pages") @field:JsonProperty("has_pages")
    val hasPages: Boolean?,
    @get:JsonProperty("has_wiki") @field:JsonProperty("has_wiki")
    val hasWiki: Boolean?,
    val homepage: Any? = null,
    @get:JsonProperty("html_url") @field:JsonProperty("html_url")
    val htmlURL: String?,
    @get:JsonProperty @field:JsonProperty
    val id: Long?,
    val language: Any? = null,
    val license: Any? = null,
    @get:JsonProperty @field:JsonProperty
    val name: String?,
    @get:JsonProperty("name_with_namespace") @field:JsonProperty("name_with_namespace")
    val nameWithNamespace: String?,
    @get:JsonProperty @field:JsonProperty
    val namespace: String?,
    @get:JsonProperty("open_issues_count") @field:JsonProperty("open_issues_count")
    val openIssuesCount: Long?,
    @get:JsonProperty @field:JsonProperty
    val owner: GiteeSender?,
    @get:JsonProperty @field:JsonProperty
    val path: String?,
    @get:JsonProperty("path_with_namespace") @field:JsonProperty("path_with_namespace")
    val pathWithNamespace: String?,
    @get:JsonProperty @field:JsonProperty
    val private: Boolean?,
    @get:JsonProperty("pushed_at") @field:JsonProperty("pushed_at")
    val pushedAt: String?,
    @get:JsonProperty("ssh_url") @field:JsonProperty("ssh_url")
    val sshURL: String?,
    @get:JsonProperty("stargazers_count") @field:JsonProperty("stargazers_count")
    val stargazersCount: Long?,
    @get:JsonProperty("svn_url") @field:JsonProperty("svn_url")
    val svnURL: String?,
    @get:JsonProperty("updated_at") @field:JsonProperty("updated_at")
    val updatedAt: String?,
    @get:JsonProperty @field:JsonProperty
    val url: String?,
    @get:JsonProperty("watchers_count") @field:JsonProperty("watchers_count")
    val watchersCount: Long?
)

@Suppress("ALL")
@JsonIgnoreProperties(ignoreUnknown = true)
data class GiteeSender(
    @get:JsonProperty("avatar_url") @field:JsonProperty("avatar_url")
    val avatarURL: String?,
    @get:JsonProperty @field:JsonProperty
    val email: String?,
    @get:JsonProperty("html_url") @field:JsonProperty("html_url")
    val htmlURL: String?,
    @get:JsonProperty @field:JsonProperty
    val id: Long?,
    @get:JsonProperty @field:JsonProperty
    val login: String?,
    @get:JsonProperty @field:JsonProperty
    val name: String?,
    val remark: Any? = null,
    @get:JsonProperty("site_admin") @field:JsonProperty("site_admin")
    val siteAdmin: Boolean?,
    @get:JsonProperty @field:JsonProperty
    val type: String?,
    @get:JsonProperty @field:JsonProperty
    val url: String?,
    @get:JsonProperty("user_name") @field:JsonProperty("user_name")
    val userName: String?,
    @get:JsonProperty @field:JsonProperty
    val username: String?
)
