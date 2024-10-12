package com.tencent.devops.scm.code.gitee

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitMergeRequest (
    val id: Int?,
    val iid: Int?,
    @JsonProperty("project_id")
    val projectId: Int?,
    val title: String?,
    val description: String?,
    val state: String?,
    @JsonProperty("merge_status")
    val mergeStatus: String?,
    @JsonProperty("web_url")
    val webUrl: String?,
    val reference: String?,
    @JsonProperty("source_project_id")
    val sourceProjectId: Int,
    @JsonProperty("source_branch")
    val sourceBranch: String,
    @JsonProperty("target_project_id")
    val targetProjectId: Int,
    @JsonProperty("target_branch")
    val targetBranch: String
)
