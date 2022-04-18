package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class WebClientConsuming{
    companion object{
        private val log = LoggerFactory.getLogger(WebClientConsuming::class.java)
    }
}

fun main(){
    SpringApplication.run(WebClientConsuming::class.java)
}