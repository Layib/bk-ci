package com.tencent.devops.auth.service.self

import com.tencent.devops.auth.constant.AuthMessageCode
import com.tencent.devops.auth.pojo.enum.OauthType
import com.tencent.devops.common.api.enums.ScmType
import com.tencent.devops.common.api.exception.ErrorCodeException
import com.tencent.devops.common.api.pojo.Page
import com.tencent.devops.common.client.Client
import com.tencent.devops.repository.api.ServiceRepositoryResource
import com.tencent.devops.repository.pojo.RepoOauthRefVo

/**
 * 代码库OAUTH授权
 */
abstract class AbstractRepoOauthService(
    open val client: Client,
    open val oauthType: OauthType
) : OauthService {
    override fun relRepo(userId: String, projectId: String, page: Int, pageSize: Int): Page<RepoOauthRefVo> {
        return client.get(ServiceRepositoryResource::class).listOauthRepo(
            projectId = projectId,
            userId = userId,
            scmType = convertOauthType(),
            page = page,
            pageSize = pageSize
        ).data ?: Page(page = page, pageSize = pageSize, records = listOf(), count = 0)
    }

    override fun delete(userId: String, projectId: String) {
        // 检查是否还有关联代码库
        if (countOauthRepo(projectId = projectId, userId = userId) > 0) {
            throw ErrorCodeException(
                errorCode = AuthMessageCode.OAUTH_INFO_OCCUPIED_CANNOT_DELETE
            )
        }
        //TODO: 调用接口删除oauth信息
    }

    private fun convertOauthType(): ScmType {
        return when (oauthType) {
            OauthType.GIT -> ScmType.CODE_GIT
            OauthType.GITHUB -> ScmType.GITHUB
        }
    }

    fun countOauthRepo(projectId: String, userId: String): Long {
        return client.get(ServiceRepositoryResource::class).countOauthRepo(
            projectId = projectId,
            userId = userId,
            scmType = convertOauthType()
        ).data ?: 0
    }
}