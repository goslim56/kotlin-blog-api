package com.goslim.kotlinblogapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@SpringBootApplication
class KotlinBlogApiApplication

fun main(args: Array<String>) {
    runApplication<KotlinBlogApiApplication>(*args)
}
