package com.goslim.kotlinblogapi.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.time.Duration
import org.apache.http.impl.client.HttpClientBuilder

@Configuration
class RestTemplateConfig {

    @Bean
    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class, KeyStoreException::class)
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        val httpClient = HttpClientBuilder.create().setMaxConnTotal(50).setMaxConnPerRoute(20).build()
        val factory = HttpComponentsClientHttpRequestFactory(httpClient)
        factory.setConnectTimeout(Duration.ofSeconds(3).toMillis().toInt())
        factory.setReadTimeout(Duration.ofSeconds(10).toMillis().toInt())

        return restTemplateBuilder.requestFactory { factory }.build()
    }
}
