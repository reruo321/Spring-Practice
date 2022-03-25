package com.example.demo.post

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class PostConsumingRestApplication{
    companion object{
        private val log = LoggerFactory.getLogger(PostConsumingRestApplication::class.java)
    }
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate{
        return builder.build()
    }
    @Bean
    @Throws(Exception::class)
    fun run(restTemplate: RestTemplate): CommandLineRunner {
        return CommandLineRunner {
        val response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", arrayOf<Post>()::class.java)
        val posts = response.body
        log.info(posts?.joinToString("\n"))
        }
    }
}

fun main(){
    SpringApplication.run(PostConsumingRestApplication::class.java)
}