package com.tencent.devops.repository.pojo

import com.tencent.devops.common.api.enums.ScmType
import com.tencent.devops.repository.pojo.enums.RepoAuthType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "代码库模型-Code平台Gitee")
class CodeGiteeRepository(
    @get:Schema(title = "代码库别名", required = true)
    override val aliasName: String,
    @get:Schema(title = "URL", required = true)
    override val url: String,
    @get:Schema(title = "凭据id", required = true)
    override val credentialId: String,
    @get:Schema(title = "gitee项目名称", example = "soda/soda_ci_example_proj", required = true)
    override val projectName: String,
    @get:Schema(title = "用户名", required = true)
    override var userName: String,
    @get:Schema(title = "项目id", required = true)
    override val projectId: String?,
    @get:Schema(title = "仓库hash id", required = false)
    override val repoHashId: String?,
    @get:Schema(title = "仓库认证类型", required = false)
    val authType: RepoAuthType? = RepoAuthType.HTTPS,
    @get:Schema(title = "仓库是否开启pac", required = false)
    override val enablePac: Boolean?,
    @get:Schema(title = "yaml同步状态", required = false)
    override val yamlSyncStatus: String?
) : Repository {
    companion object {
        const val classType = "gitee"
    }

    override fun getStartPrefix() =
            when (authType) {
                RepoAuthType.SSH -> "git@"
                RepoAuthType.OAUTH -> "http://"
                RepoAuthType.HTTP -> "http://"
                RepoAuthType.HTTPS -> "https://"
                else -> "git@"
            }

    override fun getScmType(): ScmType {
        return ScmType.CODE_GITEE
    }
}
