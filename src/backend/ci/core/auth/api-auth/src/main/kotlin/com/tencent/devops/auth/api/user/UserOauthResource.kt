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
 *
 */

package com.tencent.devops.auth.api.user

import com.tencent.devops.auth.pojo.OauthRepository
import com.tencent.devops.auth.pojo.UserOauthInfo
import com.tencent.devops.auth.pojo.enum.OauthType
import com.tencent.devops.common.api.auth.AUTH_HEADER_USER_ID
import com.tencent.devops.common.api.pojo.Page
import com.tencent.devops.common.api.pojo.Result
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Tag(name = "AUTH_RESOURCE", description = "用户态-iam资源映射")
@Path("/user/oauth/resource/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface UserOauthResource {
    @GET
    @Path("/{projectId}/")
    @Operation(summary = "获取用户OAuth授权列表")
    fun list(
        @Parameter(description = "用户名", required = true)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID")
        @PathParam("projectId")
        projectId: String
    ): Result<List<UserOauthInfo>>

    @GET
    @Path("/{projectId}/relRepo")
    @Operation(summary = "获取用户授权代码库")
    fun relRepo(
        @Parameter(description = "用户名", required = true)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID")
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "授权类型", required = true)
        @QueryParam("oauthType")
        oauthType: OauthType,
        @Parameter(description = "第几页", required = false, example = "1")
        @QueryParam("page")
        page: Int? = null,
        @Parameter(description = "每页多少条", required = false, example = "20")
        @QueryParam("pageSize")
        pageSize: Int? = null
    ): Result<Page<OauthRepository>>

    @DELETE
    @Path("/{projectId}/delete")
    @Operation(summary = "删除oauth授权")
    fun delete(
        @Parameter(description = "用户名", required = true)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "项目ID")
        @PathParam("projectId")
        projectId: String,
        @Parameter(description = "授权类型", required = true)
        @QueryParam("oauthType")
        oauthType: OauthType
    ): Result<Boolean>

    @DELETE
    @Path("/reOauth")
    @Operation(summary = "重置授权")
    fun reOauth(
        @Parameter(description = "用户名", required = true)
        @HeaderParam(AUTH_HEADER_USER_ID)
        userId: String,
        @Parameter(description = "授权类型", required = true)
        @QueryParam("oauthType")
        oauthType: OauthType
    ): Result<Boolean>
}
