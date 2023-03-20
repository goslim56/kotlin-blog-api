package com.goslim.kotlinblogapi.controller

import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogDTO
import com.goslim.kotlinblogapi.dto.ResponseBlogPopularKeywordDTO
import com.goslim.kotlinblogapi.service.BlogService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@Api(tags = ["블로그 API"])
@RequestMapping("/blogs")
@RestController
class BlogController(
    private val blogService: BlogService,
) {

    @GetMapping
    @ApiOperation(value = "블로그 목록 조회")
    fun getBlogs(@Valid requestBlogDTO: RequestBlogDTO): ResponseEntity<ResponseBlogDTO> {
        return ResponseEntity(blogService.getBlogs(requestBlogDTO), HttpStatus.OK)
    }

    @GetMapping("/keywords")
    @ApiOperation(value = "블로그 인기 검색어 조회")
    fun getPopularBlogKeyword(): ResponseEntity<ResponseBlogPopularKeywordDTO> {
        return ResponseEntity(blogService.getPopularBlogKeyword(), HttpStatus.OK)
    }
}