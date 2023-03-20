package com.goslim.kotlinblogapi.repository

import com.goslim.kotlinblogapi.entity.Search
import org.springframework.data.jpa.repository.JpaRepository

interface SearchRepository:JpaRepository<Search, Long> {
    fun findTop10ByOrderByCountDesc(): List<Search>
    fun findAllByKeywordIn(keywords: List<String>): List<Search>
}