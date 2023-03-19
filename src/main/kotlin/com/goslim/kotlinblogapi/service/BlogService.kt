package com.goslim.kotlinblogapi.service

import com.goslim.kotlinblogapi.component.KakaoBlogComponent
import com.goslim.kotlinblogapi.component.NaverBlogComponent
import com.goslim.kotlinblogapi.component.SearchCounterComponent
import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogDTO
import org.springframework.stereotype.Service

@Service
class BlogService(
    private val kakaoBlogComponent: KakaoBlogComponent,
    private val naverBlogComponent: NaverBlogComponent,
    private val searchCounterComponent: SearchCounterComponent
) {
    fun getBlogs(requestBlogDTO: RequestBlogDTO):ResponseBlogDTO {
        return runCatching {
            kakaoBlogComponent.searchBlogs(requestBlogDTO)
        }.getOrElse {naverBlogComponent.searchBlogs(requestBlogDTO) }.also {
            searchCounterComponent.increaseCount(requestBlogDTO.keyword!!)
        }
    }
}