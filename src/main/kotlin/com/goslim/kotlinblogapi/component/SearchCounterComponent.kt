package com.goslim.kotlinblogapi.component

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class SearchCounterComponent {
    private val counterMap = ConcurrentHashMap<String, Int>()

    fun increaseCount(keyword: String) {
        val currentCount = counterMap.getOrDefault(keyword, 0)
        counterMap[keyword] = currentCount + 1
    }

    fun getCount(keyword: String): Int {
        return counterMap[keyword] ?: 0
    }
}