package com.goslim.kotlinblogapi.component

import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogDTO
import com.goslim.kotlinblogapi.dto.kakao.ResponseKakaoBlogDTO
import com.goslim.kotlinblogapi.exception.OpenApiResponseException
import mu.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class KakaoBlogComponent(
    private val restTemplate: RestTemplate,
) : BlogComponentInterface {
    private var requestEntity: HttpEntity<Void>? = null
    val domain = "https://dapi.kakao.com/v2/search/blog"

    override fun searchBlogs(requestBlogDTO: RequestBlogDTO): ResponseBlogDTO {
        val query = "?query=${requestBlogDTO.keyword}" +
                (requestBlogDTO.sort?.run { "&sort=" + this.name.lowercase() } ?: "") +
                "&page=${requestBlogDTO.page}" +
                "&size=${requestBlogDTO.size}"
        val response =
            restTemplate.exchange("$domain$query", HttpMethod.GET, getRequestEntity(), ResponseKakaoBlogDTO::class.java)
        return response.body?.run {
            ResponseBlogDTO(
                responseKakaoBlogDTO = this,
                page = requestBlogDTO.page,
                size = requestBlogDTO.size
            )
        } ?: throw OpenApiResponseException("Kakao Blog API Response Body is null")
    }

    private fun getRequestEntity(): HttpEntity<Void> {
        if (requestEntity == null) {
            val headers = HttpHeaders()
            headers.set("Authorization", "KakaoAK 0d96763cbd37ef027b9ab40a9aaa34e4")
            requestEntity = HttpEntity(headers)
        }
        return requestEntity!!
    }
}