package com.goslim.kotlinblogapi.service

import com.goslim.kotlinblogapi.component.KakaoBlogComponent
import com.goslim.kotlinblogapi.component.NaverBlogComponent
import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogPopularKeywordDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogPopularKeywordDTO.SearchDTO
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class BlogService(
    private val kakaoBlogComponent: KakaoBlogComponent,
    private val naverBlogComponent: NaverBlogComponent,
    private val searchService: SearchService,
) {
    @Cacheable("blogs")
    fun getBlogs(requestBlogDTO: RequestBlogDTO):ResponseBlogDTO {
        return runCatching {
            kakaoBlogComponent.searchBlogs(requestBlogDTO)
        }.getOrElse {naverBlogComponent.searchBlogs(requestBlogDTO) }
    }

    fun getPopularBlogKeyword(): ResponseBlogPopularKeywordDTO {
        return ResponseBlogPopularKeywordDTO(searchService.getPopularBlogKeywordTop10().map(::SearchDTO))
    }
}