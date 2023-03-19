package com.goslim.kotlinblogapi.component

import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogDTO
import com.goslim.kotlinblogapi.enumeration.SortType

interface BlogComponentInterface {
    fun searchBlogs(requestBlogDTO: RequestBlogDTO): ResponseBlogDTO
}