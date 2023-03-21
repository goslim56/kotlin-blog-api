package com.goslim.kotlinblogapi.scheduler

import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CacheSchedule {
    private val log = KotlinLogging.logger {}

    @CacheEvict("blogs")
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    fun emptyBlogsCache() {
        log.info("emptying blogs cache")
    }
}