package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WebClientConsuming{
    companion object{
        private val log = LoggerFactory.getLogger(WebClientConsuming::class.java)
    }
}

fun main(){
    SpringApplication.run(WebClientConsuming::class.java)
}