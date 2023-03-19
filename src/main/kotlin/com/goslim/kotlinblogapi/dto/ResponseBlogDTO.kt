package com.goslim.kotlinblogapi.dto

import com.goslim.kotlinblogapi.dto.kakao.ResponseKakaoBlogDTO
import com.goslim.kotlinblogapi.dto.naver.ResponseNaverBlogDTO
import com.goslim.kotlinblogapi.util.DateFormatUtil
import io.swagger.annotations.ApiModelProperty

data class ResponseBlogDTO(
    @ApiModelProperty(value = "블로그 검색 결과 개수",  example = "324")
    var totalCount: Int,
    val page: Int,
    val size: Int,
    var blogs: List<BlogDTO>,

) {
    constructor(responseKakaoBlogDTO:ResponseKakaoBlogDTO, page: Int, size:Int): this(
        totalCount = responseKakaoBlogDTO.meta.totalCount,
        blogs = responseKakaoBlogDTO.documents.map(::BlogDTO),
        size = size,
        page = page
    )

    constructor(responseNaverBlogDTO: ResponseNaverBlogDTO, page: Int, size:Int): this(
        totalCount = responseNaverBlogDTO.total,
        blogs = responseNaverBlogDTO.items.map(::BlogDTO),
        size = size,
        page = page
    )

    data class BlogDTO(
        @ApiModelProperty(value = "블로그 포스트 제목",  example = "324")
        val title: String,

        @ApiModelProperty(value = "블로그 포스트 URL",  example = "324")
        val blogURL: String,

        @ApiModelProperty(value = "블로그 포스트 내용", example = "위치 : 인천광역시 부평구 부평동 529-40, 501 호 동아웰빙타운 운영 시간 : 24 시간 방문가능 주차 : 1 시간 무료주차 가능 ...")
        val contents: String,

        @ApiModelProperty(value = "블로그 이름", example = "인기있는 블로그")
        val blogName: String,

        @ApiModelProperty(value = "블로그 포스트 게시 날짜", example = "2023-03-19T00:00:00")
        val postdate: String,
    ) {
        constructor(blogContentDTO: ResponseKakaoBlogDTO.BlogContentDTO):this (
            title = blogContentDTO.title,
            blogURL = blogContentDTO.url,
            contents = blogContentDTO.contents,
            blogName = blogContentDTO.blogName,
            postdate = blogContentDTO.datetime.split(".")[0],
        )

        constructor(itemsDto: ResponseNaverBlogDTO.ItemsDto):this (
            title = itemsDto.title,
            blogURL = itemsDto.link,
            contents = itemsDto.description,
            blogName = itemsDto.bloggername,
            postdate = DateFormatUtil.changeDateFormat( oldFormat = "yyyyMMdd", dateString = itemsDto.postdate)
        )
    }

}
