package com.tencent.devops.scm.code.gitee

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitCompareResult (
    val commit: Commit?,
    val commits: List<Commit>,
    val diffs: List<Diffs>,
    @JsonProperty("compare_timeout")
    val compareTimout: Boolean,
    @JsonProperty("compare_same_ref")
    val compareSameRef: Boolean?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Commit (
        val id: String,
        @JsonProperty("short_id")
        val shortId: String,
        @JsonProperty("created_at")
        val createAt: String,
        @JsonProperty("parent_ids")
        val parentIds: List<String>?,
        val title: String?,
        val message: String?,
        @JsonProperty("author_name")
        val authorName: String?,
        @JsonProperty("author_email")
        val authorEmail: String?,
        @JsonProperty("authored_date")
        val authoredDate: String,
        @JsonProperty("committer_name")
        val committerName: String,
        @JsonProperty("committer_email")
        val committerEmail: String,
        @JsonProperty("committed_date")
        val committedDate: String
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Diffs(
        @JsonProperty("old_path")
        val oldPath: String,
        @JsonProperty("new_path")
        val newPath: String,
        @JsonProperty("a_mode")
        val aMode: String,
        @JsonProperty("b_mode")
        val bMode: String,
        @JsonProperty("new_file")
        val newFile: Boolean,
        @JsonProperty("renamed_file")
        val renamedFile: Boolean,
        @JsonProperty("deleted_file")
        val deletedFile: Boolean,
        val diff: String
    )
}
