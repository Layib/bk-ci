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

package com.tencent.devops.scm.code.gitee.api

import com.fasterxml.jackson.module.kotlin.readValue
import com.tencent.devops.common.api.constant.CommonMessageCode
import com.tencent.devops.common.api.constant.HTTP_403
import com.tencent.devops.common.api.util.JsonUtil
import com.tencent.devops.common.api.util.OkhttpUtils
import com.tencent.devops.common.web.utils.I18nUtil
import com.tencent.devops.scm.code.git.CodeGitWebhookEvent
import com.tencent.devops.scm.code.git.api.GitHook
import com.tencent.devops.scm.exception.GitApiException
import com.tencent.devops.scm.pojo.GitServerError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory

@Suppress("ALL")
open class GiteeApi {

    companion object {
        private val logger = LoggerFactory.getLogger(GiteeApi::class.java)
        private const val OPERATION_ADD_WEBHOOK = "添加WEBHOOK"
        private const val OPERATION_UPDATE_WEBHOOK = "修改WEBHOOK"
        private const val OPERATION_LIST_WEBHOOK = "查询WEBHOOK"
    }

    fun addWebhook(
        host: String,
        token: String,
        projectName: String,
        hookUrl: String,
        event: String?,
        secret: String? = null
    ): GitHook? {
        logger.info("[$host|$projectName|$hookUrl|$event] Start add the web hook")
        val existHooks = getHooks(host, token, projectName)
        if (existHooks.isNotEmpty()) {
            existHooks.forEach {
                if (it.url == hookUrl) {
                    val exist = when (event) {
                        null -> {
                            it.pushEvents
                        }
                        CodeGitWebhookEvent.PUSH_EVENTS.value -> {
                            it.pushEvents
                        }
                        CodeGitWebhookEvent.TAG_PUSH_EVENTS.value -> {
                            it.tagPushEvents
                        }
                        CodeGitWebhookEvent.ISSUES_EVENTS.value -> {
                            it.issuesEvents
                        }
                        CodeGitWebhookEvent.NOTE_EVENTS.value -> {
                            it.noteEvents
                        }
                        CodeGitWebhookEvent.MERGE_REQUESTS_EVENTS.value -> {
                            it.mergeRequestsEvents
                        }
                        CodeGitWebhookEvent.REVIEW_EVENTS.value -> {
                            it.reviewEvents
                        }
                        else -> false
                    }

                    if (exist) {
                        logger.info("The web hook url($hookUrl) and event($event) is already exist($it)")
                        if (!secret.isNullOrBlank()) {
                            updateHook(
                                host = host,
                                hookId = it.id,
                                token = token,
                                projectName = projectName,
                                hookUrl = hookUrl,
                                event = event,
                                secret = secret
                            )
                        }
                        return null
                    }
                }
            }
        }
        // Add the wed hook
        return addHook(host, token, projectName, hookUrl, event, secret)
    }

    /**
     * @Description: 添加gitee的webhook
     * @Author: Jackychen
     * @Date: 2022/7/22
     */
    private fun addHook(
        host: String,
        token: String,
        projectName: String,
        hookUrl: String,
        event: String? = null,
        secret: String? = null
    ): GitHook {
        val body = webhookBody(token, hookUrl, event, secret)
        val request = post(host, token, "repos/$projectName/hooks", body)
        try {
            return callMethod(OPERATION_ADD_WEBHOOK, request, GitHook::class.java)
        } catch (t: GitApiException) {
            if (t.code == HTTP_403) {
                throw GitApiException(
                    t.code,
                    I18nUtil.getCodeLanMessage(CommonMessageCode.WEBHOOK_ADD_FAIL, params = arrayOf("Developer"))
                )
            }
            throw t
        }
    }

    private fun getHooks(host: String, token: String, projectName: String): List<GitHook> {
        val request = get(
            host,
            token,
            "repos/${projectName}/hooks?access_token=${token}&page=1&per_page=100"
        )
        val result = JsonUtil.getObjectMapper().readValue<List<GitHook>>(getBody(OPERATION_LIST_WEBHOOK, request))
        return result.sortedBy { it.createdAt }.reversed()
    }

    private fun webhookBody(token: String, hookUrl: String, event: String?, secret: String?): String {
        val params = mutableMapOf<String, String>()
        params["access_token"] = token
        params["url"] = hookUrl
        if (event == null) {
            params[CodeGitWebhookEvent.PUSH_EVENTS.value] = true.toString()
        } else {
            val codeGitEvent = CodeGitWebhookEvent.find(event)
            params[codeGitEvent!!.value] = true.toString()
            if (codeGitEvent != CodeGitWebhookEvent.PUSH_EVENTS) {
                params[CodeGitWebhookEvent.PUSH_EVENTS.value] = false.toString()
            }
        }
        if (!secret.isNullOrBlank()) {
            params["token"] = secret
        }
        params[CodeGitWebhookEvent.ENABLE_SSL_VERIFICATION.value] = false.toString()
        return JsonUtil.getObjectMapper().writeValueAsString(params)
    }

    private fun updateHook(
        host: String,
        hookId: Long,
        token: String,
        projectName: String,
        hookUrl: String,
        event: String? = null,
        secret: String? = null
    ): GitHook {
        logger.info("Start to update webhook of host $host by project $projectName")
        val body = webhookBody(token, hookUrl, event, secret)
        val request = put(host, token, "repos/${projectName}/hooks/$hookId", body)
        return callMethod(OPERATION_UPDATE_WEBHOOK, request, GitHook::class.java)
    }

    private val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    fun post(host: String, token: String, url: String, body: String) =
        request(host, token, url ).post(body.toRequestBody(mediaType)).build()

    private fun get(host: String, token: String, url: String) =
        request(host, token, url).get().build()

    private fun put(host: String, token: String, url: String, body: String) =
        request(host, token, url ).put(body.toRequestBody(mediaType)).build()

    protected open fun request(host: String, token: String, url: String): Request.Builder {
        return Request.Builder()
            .url("$host/$url")
            .addHeader("Content-Type", "application/json")
    }

    private fun <T> callMethod(operation: String, request: Request, classOfT: Class<T>): T {
        OkhttpUtils.doHttp(request).use { response ->
            if (!response.isSuccessful) {
                handleApiException(operation, response.code, response.body?.string() ?: "")
            }
            return JsonUtil.getObjectMapper().readValue(response.body!!.string(), classOfT)
        }
    }

    private fun getBody(operation: String, request: Request): String {
        OkhttpUtils.doHttp(request).use { response ->
            if (!response.isSuccessful) {
                handleApiException(operation, response.code, response.body?.string() ?: "")
            }
            return response.body!!.string()
        }
    }

    private fun handleApiException(operation: String, code: Int, body: String) {
        logger.warn("Fail to call git api because of code $code and message $body")
        val apiError = JsonUtil.to(body, GitServerError::class.java)
        throw GitApiException(code, apiError.message ?: "")
    }
}
