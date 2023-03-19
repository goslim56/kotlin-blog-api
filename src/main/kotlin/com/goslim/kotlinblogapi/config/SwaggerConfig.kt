package com.goslim.kotlinblogapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.ZoneId


import com.fasterxml.classmate.TypeResolver
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import springfox.documentation.service.ApiInfo

@EnableWebMvc
@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.OAS_30)
            .directModelSubstitute(ZoneId::class.java, String::class.java)
            .apiInfo(swaggerInfo())
            .select() // 하단 경로의 API를 문서화
            .apis(RequestHandlerSelectors.basePackage("com.goslim.kotlinblogapi.controller")) // 하단의 경로만 문서화할 수 있음
            .paths(PathSelectors.any()).build()
            .useDefaultResponseMessages(false)
    }
    private fun swaggerInfo() = ApiInfoBuilder()
        .title("블로그 검색 API")
        .description("블로그 검색 서비스")
        .version("1.0.0")
        .build()

//    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        registry.addResourceHandler("swagger-ui.html")
//            .addResourceLocations("classpath:/META-INF/resources/")
//        registry.addResourceHandler("/webjars/**")
//            .addResourceLocations("classpath:/META-INF/resources/webjars/")
//    }
//        .apis(RequestHandlerSelectors.basePackage("com.goslim.kotlinblogapi.controller"))

//    private fun getConsumeContentTypes(): Set<String> {
//        val consumes = HashSet<String>()
//        consumes.add("multipart/form-data")
//        return consumes
//    }
//
//    private fun getProduceContentTypes(): Set<String> {
//        val produces = HashSet<String>()
//        produces.add("application/json;charset=UTF-8")
//        return produces
//    }

//    "com.goslim.kotlinblogapi.controller"
}