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
data class GiteeNoteEvent(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val action: String,
    @get:JsonProperty("action_desc", required = true) @field:JsonProperty("action_desc", required = true)
    val actionDesc: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val author: Author,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val comment: Comment,
    val enterprise: Any? = null,
    @get:JsonProperty("hook_id", required = true) @field:JsonProperty("hook_id", required = true)
    val hookID: String,
    @get:JsonProperty("hook_name", required = true) @field:JsonProperty("hook_name", required = true)
    val hookName: String,
    @get:JsonProperty("hook_url", required = true) @field:JsonProperty("hook_url", required = true)
    val hookURL: String,
    @get:JsonProperty("issue", required = true) @field:JsonProperty("issue", required = true)
    val issue: Issue,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val note: String,
    @get:JsonProperty("noteable_id", required = true) @field:JsonProperty("noteable_id", required = true)
    val noteableID: String,
    @get:JsonProperty("noteable_type", required = true) @field:JsonProperty("noteable_type", required = true)
    val noteableType: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val password: String,
    @get:JsonProperty("per_iid", required = true) @field:JsonProperty("per_iid", required = true)
    val perIid: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val project: Project,
    @get:JsonProperty("pull_request") @field:JsonProperty("pull_request")
    val pullRequest: Any? = null,
    @get:JsonProperty("push_data") @field:JsonProperty("push_data")
    val pushData: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val repository: Project,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    override val sender: GiteeSender,
    @get:JsonProperty("short_commit_id") @field:JsonProperty("short_commit_id")
    val shortCommitID: Any? = null,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val sign: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val timestamp: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val title: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val url: String
) : GiteeEvent(sender) {
    companion object {
        const val classType = "issue_hooks"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Comment(
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val body: String,
    @get:JsonProperty("created_at", required = true) @field:JsonProperty("created_at", required = true)
    val createdAt: String,
    @get:JsonProperty("html_url", required = true) @field:JsonProperty("html_url", required = true)
    val htmlURL: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val id: String,
    @get:JsonProperty("updated_at", required = true) @field:JsonProperty("updated_at", required = true)
    val updatedAt: String,
    @get:JsonProperty(required = true) @field:JsonProperty(required = true)
    val user: Author
)
