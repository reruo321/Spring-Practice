package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebClientConsumingTest {

	@Test
	fun checkFetching(){
		val client = WebClientConsuming()
		client.webClientRestCall()
	}
}
