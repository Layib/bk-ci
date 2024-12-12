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
package com.tencent.devops.openapi.resources.apigw.v4

import com.tencent.devops.common.api.enums.ScmCode
import com.tencent.devops.common.api.pojo.Result
import com.tencent.devops.common.client.Client
import com.tencent.devops.common.web.RestResource
import com.tencent.devops.openapi.api.apigw.v4.ApigwOauthResourceV4
import com.tencent.devops.repository.api.ServiceOauthResource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@RestResource
class ApigwOauthResourceV4Impl @Autowired constructor(private val client: Client) : ApigwOauthResourceV4 {
    override fun isOauth(
        appCode: String?,
        apigwType: String?,
        userId: String,
        scmCode: String
    ): Result<Boolean> {
        logger.info("OPENAPI_OAUTH_V4|$userId|verify if $scmCode oauth authorization has been performed")
        val result = when (scmCode) {
            ScmCode.TGIT.name -> {
                client.get(ServiceOauthResource::class).isOAuth(
                    userId = userId,
                    redirectUrl = null,
                    redirectUrlType = null
                ).data?.status
            }

            ScmCode.GITHUB.name -> {
                client.get(ServiceOauthResource::class).githubOAuth(
                    userId = userId
                ).data?.status
            }

            else -> {
                null
            }
        }
        return Result(result == AUTHORIZED_STATUS)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ApigwOauthResourceV4Impl::class.java)
        private const val AUTHORIZED_STATUS = 200
    }
}
