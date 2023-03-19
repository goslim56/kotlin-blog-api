package com.goslim.kotlinblogapi.dto.naver

import java.time.LocalDate

data class ResponseNaverBlogDTO(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<ItemsDto>
) {
    data class ItemsDto(
        val title: String,
        val link: String,
        val description: String,
        val bloggername: String,
        val bloggerlink: String,
        val postdate: String,
    )
}