package com.example.demo.quote

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

@SpringBootApplication
class QuoteConsumingRestApplication{
    companion object{
        private val log = LoggerFactory.getLogger(QuoteConsumingRestApplication::class.java)
    }
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate{
        val rest = builder.build()
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = ObjectMapper().registerKotlinModule()
        converter.supportedMediaTypes = listOf(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        rest.messageConverters.add(0, converter)
        return rest
    }
    @Bean
    @Throws(Exception::class)
    fun run(restTemplate: RestTemplate): CommandLineRunner {
        return CommandLineRunner {
            val response = restTemplate.getForObject("https://type.fit/api/quotes", arrayOf<Quote>()::class.java)
            log.info(response?.joinToString("\n"))
        }
    }
}

fun main(){
    SpringApplication.run(QuoteConsumingRestApplication::class.java)
}