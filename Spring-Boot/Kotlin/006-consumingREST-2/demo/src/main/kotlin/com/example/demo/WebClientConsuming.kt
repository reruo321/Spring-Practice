package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class WebClientConsuming{
    companion object{
	private val log = LoggerFactory.getLogger(WebClientConsuming::class.java)
    }
    @Bean
    fun webClientRestCall(): CommandLineRunner {
	return CommandLineRunner {
	    val client = WebClient.builder().build()
	    val commentFlux = client.get()
		    .uri("https://jsonplaceholder.typicode.com/comments")
		    .retrieve()
		    .bodyToFlux(Comment::class.java)
	    commentFlux.subscribe(System.out::println)
	}
    }
}

fun main(){
    SpringApplication.run(WebClientConsuming::class.java)
}
