package com.goslim.kotlinblogapi

import com.goslim.kotlinblogapi.component.KakaoBlogComponent
import com.goslim.kotlinblogapi.component.NaverBlogComponent
import com.goslim.kotlinblogapi.dto.RequestBlogDTO
import com.goslim.kotlinblogapi.service.BlogService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class KotlinBlogApiApplicationTests(
    @Autowired val blogService: BlogService,
    @Autowired val mockMvc: MockMvc,
    @Autowired val kakaoBlogComponent: KakaoBlogComponent,
    @Autowired val naverBlogComponent: NaverBlogComponent
) {


    @Test
    fun `블로그 인기 검색어  조회 API 테스트`() {
        for( i in 1..5) {
            mockMvc.perform(get("/blogs").param("keyword", "맛집"))
        }
        for( i in 1..4) {
            mockMvc.perform(get("/blogs").param("keyword", "카페"))
        }
        val responseBlogPopularKeywordDTO = blogService.getPopularBlogKeyword()
        assertTrue(responseBlogPopularKeywordDTO.data.size >= 2)
        assertTrue(responseBlogPopularKeywordDTO.data[0].count >= 5)
        assertTrue(responseBlogPopularKeywordDTO.data[1].count >= 4)
    }

    @Test
    fun `블로그 인기 검색어  조회 API 최대 개수 테스트`() {
        mockMvc.perform(get("/blogs").param("keyword", "맛집"))
        mockMvc.perform(get("/blogs").param("keyword", "카페"))
        mockMvc.perform(get("/blogs").param("keyword", "개발자"))
        mockMvc.perform(get("/blogs").param("keyword", "컴퓨터"))
        mockMvc.perform(get("/blogs").param("keyword", "바디로션"))
        mockMvc.perform(get("/blogs").param("keyword", "식이섬유"))
        mockMvc.perform(get("/blogs").param("keyword", "마스크"))
        mockMvc.perform(get("/blogs").param("keyword", "텀블러"))
        mockMvc.perform(get("/blogs").param("keyword", "비타민"))
        mockMvc.perform(get("/blogs").param("keyword", "오메가3"))
        mockMvc.perform(get("/blogs").param("keyword", "애플페이"))
        mockMvc.perform(get("/blogs").param("keyword", "스프링"))
        mockMvc.perform(get("/blogs").param("keyword", "역삼역"))
        mockMvc.perform(get("/blogs").param("keyword", "강남역"))
        mockMvc.perform(get("/blogs").param("keyword", "교대역"))
        mockMvc.perform(get("/blogs").param("keyword", "서초역"))
        mockMvc.perform(get("/blogs").param("keyword", "방배역"))
        mockMvc.perform(get("/blogs").param("keyword", "사당역"))
        mockMvc.perform(get("/blogs").param("keyword", "낙성대역"))
        mockMvc.perform(get("/blogs").param("keyword", "서울대입구역"))
        mockMvc.perform(get("/blogs").param("keyword", "봉천역"))
        mockMvc.perform(get("/blogs").param("keyword", "신림역"))
        mockMvc.perform(get("/blogs").param("keyword", "신대방역"))
        mockMvc.perform(get("/blogs").param("keyword", "구로디지털단지역"))
        val responseBlogPopularKeywordDTO = blogService.getPopularBlogKeyword()
        assertTrue(responseBlogPopularKeywordDTO.data.size == 10)
    }


    @Test
    fun `블로그 조회 API 테스트`() {
        mockMvc.perform(
            get("/blogs").param("keyword", "맛집").param("page", "1")
                .param("size", "10").param("sorting", "ACCURACY")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `블로그 잘못된 파라미터 조회 API 테스트`() {
        mockMvc.perform(
            get("/blogs")
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun `카카오 블로그 검색 기능에 대한 테스트 케이스`() {
        val requestBlogDTO = RequestBlogDTO(keyword = "맛집")
        val responseBlogDTO = kakaoBlogComponent.searchBlogs(requestBlogDTO)
        assertEquals(requestBlogDTO.page, responseBlogDTO.page)
        assertNotNull(responseBlogDTO.blogs)
    }


    @Test
    fun `카카오 블로그 검색 페지이 기능에 대한 테스트 케이스`() {
        val requestBlogDTO1 = RequestBlogDTO(keyword = "맛집", page = 2)
        val responseBlogDTO1 = kakaoBlogComponent.searchBlogs(requestBlogDTO1)

        val requestBlogDTO2 = RequestBlogDTO(keyword = "맛집", page = 1)
        val responseBlogDTO2 = kakaoBlogComponent.searchBlogs(requestBlogDTO2)
        assertEquals(responseBlogDTO1.blogs.size, responseBlogDTO2.blogs.size)
        assertNotNull(responseBlogDTO1.blogs[0].blogURL, responseBlogDTO1.blogs[1].blogURL)
    }


    @Test
    fun `네이버 블로그 검색 기능에 대한 테스트 케이스`() {
        val requestBlogDTO = RequestBlogDTO(keyword = "맛집")
        val responseBlogDTO = naverBlogComponent.searchBlogs(requestBlogDTO)
        assertEquals(requestBlogDTO.page, responseBlogDTO.page)
        assertNotNull(responseBlogDTO.blogs)
    }

    @Test
    fun `네이버 블로그 검색 페지이 기능에 대한 테스트 케이스`() {
        val requestBlogDTO1 = RequestBlogDTO(keyword = "맛집", page = 2)
        val responseBlogDTO1 = naverBlogComponent.searchBlogs(requestBlogDTO1)

        val requestBlogDTO2 = RequestBlogDTO(keyword = "맛집", page = 1)
        val responseBlogDTO2 = naverBlogComponent.searchBlogs(requestBlogDTO2)
        assertEquals(responseBlogDTO1.blogs.size, responseBlogDTO2.blogs.size)
        assertNotNull(responseBlogDTO1.blogs[0].blogURL, responseBlogDTO1.blogs[1].blogURL)
    }
}
