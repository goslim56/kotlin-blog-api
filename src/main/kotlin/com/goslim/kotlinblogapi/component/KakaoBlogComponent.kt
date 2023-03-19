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
    private val log = KotlinLogging.logger {}
    val domain = "https://dapi.kakao.com/v2/search/blog"

    override fun searchBlogs(requestBlogDTO: RequestBlogDTO): ResponseBlogDTO {
        val headers = HttpHeaders()
        headers.set("Authorization", "KakaoAK 0d96763cbd37ef027b9ab40a9aaa34e4")
        val requestEntity: HttpEntity<Void> = HttpEntity(headers)
        val query = "?query=${requestBlogDTO.keyword}" +
                (requestBlogDTO.sort?.run { "&sort=" + this.name.lowercase() } ?: "") +
                "&page=${requestBlogDTO.page}" +
                "&size=${requestBlogDTO.size}"
        log.info { "query url: $domain$query " }
        val response =
            restTemplate.exchange("$domain$query", HttpMethod.GET, requestEntity, ResponseKakaoBlogDTO::class.java)
        log.info { "response:  ${response}" }
        return response.body?.run {
            ResponseBlogDTO(
                responseKakaoBlogDTO = this,
                page = requestBlogDTO.page,
                size = requestBlogDTO.size
            )
        } ?: throw OpenApiResponseException("Kakao Blog API Response Body is null")
    }
}