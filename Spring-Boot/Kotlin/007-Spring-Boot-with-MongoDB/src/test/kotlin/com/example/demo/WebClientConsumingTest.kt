package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@EnableAutoConfiguration(exclude=[MongoReactiveAutoConfiguration::class, MongoReactiveDataAutoConfiguration::class])
class WebClientConsumingTest(){
	@Test
	fun loader(){
	}
}
