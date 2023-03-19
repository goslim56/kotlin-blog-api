package com.goslim.kotlinblogapi.dto.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseKakaoBlogDTO(
    val documents: List<BlogContentDTO>,
    val meta: MetaDTO
) {
    data class MetaDTO(
        @JsonProperty("total_count")
        val totalCount: Int,
        @JsonProperty("pageable_count")
        val pageableCount: Int,
        @JsonProperty("is_end")
        val isEnd: Boolean,
    )

    data class BlogContentDTO(
        val title: String,
        val contents: String,
        val url: String,
        @JsonProperty("blogname")
        val blogName: String,
        val thumbnail: String,
        val datetime: String,
    )
}