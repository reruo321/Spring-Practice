package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class DataRestApplication{
    @Bean
    fun run(@Autowired repository: BookRepository) = ApplicationRunner {
        repository.save(Book("TEPS", "Study"))
    }
}

fun main(){
    SpringApplication.run(DataRestApplication::class.java)
}