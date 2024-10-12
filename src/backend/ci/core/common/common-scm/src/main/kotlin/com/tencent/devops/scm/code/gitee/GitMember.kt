package com.tencent.devops.scm.code.gitee

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitMember(
    val id: Int,
    val name: String,
    val username: String,
    val state: String,
    @JsonProperty("avatar_url")
    val adatarUrl: String,
    @JsonProperty("web_url")
    val webUrl: String,
    @JsonProperty("access_level")
    val accessLevel: Int,
    @JsonProperty("expires_at")
    val expiresAt: String?
)
