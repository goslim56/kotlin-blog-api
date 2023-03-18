package com.goslim.kotlinblogapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinBlogApiApplication

fun main(args: Array<String>) {
    runApplication<KotlinBlogApiApplication>(*args)
}
