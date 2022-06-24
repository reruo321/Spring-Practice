package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class DataRestApplication

fun main(){
    SpringApplication.run(DataRestApplication::class.java)
}