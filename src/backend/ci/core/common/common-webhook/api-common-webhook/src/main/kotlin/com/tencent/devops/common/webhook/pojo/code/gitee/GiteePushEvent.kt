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
data class GiteePushEvent(
    @get:JsonProperty @field:JsonProperty
    val after: String?,
    @get:JsonProperty @field:JsonProperty
    val before: String?,
    @get:JsonProperty @field:JsonProperty
    val commits: List<Commit>?,
    @get:JsonProperty("commits_more_than_ten") @field:JsonProperty("commits_more_than_ten")
    val commitsMoreThanTen: Boolean?,
    @get:JsonProperty @field:JsonProperty
    val compare: String?,
    @get:JsonProperty @field:JsonProperty
    val created: Boolean?,
    @get:JsonProperty @field:JsonProperty
    val deleted: Boolean?,
    val enterprise: Any? = null,
    @get:JsonProperty("head_commit") @field:JsonProperty("head_commit")
    val headCommit: Commit?,
    @get:JsonProperty("hook_id") @field:JsonProperty("hook_id")
    val hookID: Long?,
    @get:JsonProperty("hook_name") @field:JsonProperty("hook_name")
    val hookName: String?,
    @get:JsonProperty("hook_url") @field:JsonProperty("hook_url")
    val hookURL: String?,
    @get:JsonProperty @field:JsonProperty
    val password: String?,
    @get:JsonProperty("push_data") @field:JsonProperty("push_data")
    val pushData: Any? = null,
    @get:JsonProperty @field:JsonProperty
    val pusher: Pusher?,
    @get:JsonProperty @field:JsonProperty
    val ref: String?,
    @get:JsonProperty @field:JsonProperty
    val repository: GiteeRepository?,
    @get:JsonProperty @field:JsonProperty
    override val sender: GiteeSender,
    @get:JsonProperty @field:JsonProperty
    val sign: String?,
    @get:JsonProperty @field:JsonProperty
    val timestamp: String?,
    @get:JsonProperty("total_commits_count") @field:JsonProperty("total_commits_count")
    val totalCommitsCount: Long?,
    @get:JsonProperty @field:JsonProperty
    val user: Pusher?,
    @get:JsonProperty("user_id") @field:JsonProperty("user_id")
    val userID: Long?,
    @get:JsonProperty("user_name") @field:JsonProperty("user_name")
    val userName: String?
) : GiteeEvent(sender) {
    companion object {
        const val classType = "push"
    }
}


@JsonIgnoreProperties(ignoreUnknown = true)
data class Commit(
    @get:JsonProperty @field:JsonProperty
    val added: List<Any?>?,
    @get:JsonProperty @field:JsonProperty
    val author: Author?,
    @get:JsonProperty @field:JsonProperty
    val committer: Author?,
    @get:JsonProperty @field:JsonProperty
    val distinct: Boolean?,
    @get:JsonProperty @field:JsonProperty
    val id: String?,
    @get:JsonProperty @field:JsonProperty
    val message: String?,
    @get:JsonProperty @field:JsonProperty
    val modified: List<String>,
    @get:JsonProperty("parent_ids") @field:JsonProperty("parent_ids")
    val parentIDS: List<String?>?,
    @get:JsonProperty @field:JsonProperty
    val removed: List<Any?>?,
    @get:JsonProperty @field:JsonProperty
    val timestamp: String?,
    @get:JsonProperty("tree_id") @field:JsonProperty("tree_id")
    val treeID: String?,
    @get:JsonProperty @field:JsonProperty
    val url: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Pusher(
    @get:JsonProperty @field:JsonProperty
    val email: String?,
    @get:JsonProperty @field:JsonProperty
    val id: Long?,
    @get:JsonProperty @field:JsonProperty
    val name: String?,
    @get:JsonProperty @field:JsonProperty
    val url: String?,
    @get:JsonProperty("user_name") @field:JsonProperty("user_name")
    val userName: String?,
    @get:JsonProperty @field:JsonProperty
    val username: String?
)










