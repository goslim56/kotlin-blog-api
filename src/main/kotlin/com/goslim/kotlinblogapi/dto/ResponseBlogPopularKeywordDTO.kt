package com.goslim.kotlinblogapi.dto

import com.goslim.kotlinblogapi.dto.kakao.ResponseKakaoBlogDTO
import com.goslim.kotlinblogapi.dto.naver.ResponseNaverBlogDTO
import com.goslim.kotlinblogapi.entity.Search
import com.goslim.kotlinblogapi.util.DateFormatUtil
import io.swagger.annotations.ApiModelProperty

data class ResponseBlogPopularKeywordDTO(
    val data: List<SearchDTO>
) {
    data class SearchDTO(
        @ApiModelProperty(value = "블로그 키워드 ", example = "맛집")
        var keyword: String,

        @ApiModelProperty(value = "키워드 검색 횟수 ", example = "2")
        val count: Long,
    ) {
        constructor(search: Search) : this(
            keyword = search.keyword,
            count = search.count,
        )
    }
}