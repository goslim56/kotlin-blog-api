package com.goslim.kotlinblogapi.service

import com.goslim.kotlinblogapi.entity.Search
import com.goslim.kotlinblogapi.repository.SearchRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Service
class SearchService(
    private val searchRepository: SearchRepository
) {

    private var searchCountMap = ConcurrentHashMap<String, Long>()
    private var searchCountUpCallCount: AtomicInteger = AtomicInteger(0)
    private val syncCount =  0 // 테스트를 위해 낮은숫자로 설정, 트래픽 양에 따라 값을 증가

    fun searchCountUp(keyword: String) {
        searchCountMap[keyword]?.run { searchCountMap.put(keyword, this.plus(1)) } ?: searchCountMap.put(keyword, 1)
        syncSearchCount()
    }

    fun getPopularBlogKeywordTop10(): List<Search> {
        return searchRepository.findTop10ByOrderByCountDesc()
    }

    @Transactional
    fun syncSearchCount() {
        if (searchCountUpCallCount.getAndIncrement() < syncCount) return
        searchCountUpCallCount = AtomicInteger(0)
        val searches = searchRepository.findAllByKeywordIn(searchCountMap.keys().toList())

        for ((keyword, count) in searchCountMap) {
            searchRepository.save(searches.firstOrNull {
                it.keyword == keyword
            }?.apply { this.count += count } ?: Search(keyword = keyword, count = count))
        }
        searchCountMap = ConcurrentHashMap<String, Long>()

    }
}