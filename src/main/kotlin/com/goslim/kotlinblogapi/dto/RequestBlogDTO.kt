package com.goslim.kotlinblogapi.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.goslim.kotlinblogapi.enumeration.SortType
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RequestBlogDTO(
    @ApiModelProperty(value = "검색어", required = false, example = "맛집")
    @field:NotNull(message = "검색어(keyword)는 필수입니다")
    val keyword: String? = null,

    @ApiModelProperty(value = "정렬", required = false, example = "ACCURACY")
//    @field:NotBlank(message = "{}")
    val sort: SortType? = null,

    @field:Min(1)
    @field:Max(50)
    val size: Int = 20,
    @field:Min(1)
    @field:Max(50)
    val page: Int = 1,
)
// {
//    @JsonIgnore
//    fun getPageable(): Pageable =
//        PageRequest.of(this.page - 1, this.size)
//}