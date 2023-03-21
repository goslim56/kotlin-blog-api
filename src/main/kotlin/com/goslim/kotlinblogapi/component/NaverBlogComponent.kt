package com.goslim.kotlinblogapi.component

import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogDTO
import com.goslim.kotlinblogapi.dto.naver.ResponseNaverBlogDTO
import com.goslim.kotlinblogapi.enumeration.SortType
import com.goslim.kotlinblogapi.exception.OpenApiResponseException
import mu.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NaverBlogComponent(
    private val restTemplate: RestTemplate,
) : BlogComponentInterface {
    private var requestEntity: HttpEntity<Void>? = null
    val domain = "https://openapi.naver.com/v1/search/blog"

    override fun searchBlogs(requestBlogDTO: RequestBlogDTO): ResponseBlogDTO {
        val query = "?query=${requestBlogDTO.keyword}" + (requestBlogDTO.sort?.run {
            "&sort=" + when (requestBlogDTO.sort) {
                SortType.ACCURACY -> "sim"
                SortType.RECENCY -> "date"
            }
        } ?: "") +
                "&start=${requestBlogDTO.page}" +
                "&display=${requestBlogDTO.size}"

        val response =
            restTemplate.exchange("$domain$query", HttpMethod.GET, getRequestEntity(), ResponseNaverBlogDTO::class.java)
        return response.body?.run {
            ResponseBlogDTO(
                responseNaverBlogDTO = this,
                page = requestBlogDTO.page,
                size = requestBlogDTO.size
            )
        }
            ?: throw OpenApiResponseException("Naver Blog API Response Body is null")
    }

    private fun getRequestEntity(): HttpEntity<Void> {
        if (requestEntity == null) {
            val headers = HttpHeaders()
            headers.set("X-Naver-Client-Id", "rVcbGSjf4W9pvsp7sC7o")
            headers.set("X-Naver-Client-Secret", "thHY_YtL78")
            requestEntity = HttpEntity(headers)
        }
        return requestEntity!!
    }
}