package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class WebClientConsumingTest(@Autowired private val commentRepository: CommentRepository) {
	@Test
	fun saveCommentTest() {
		val comment = Comment(123, 456, "Test Guy", "test@test.com", "TestTestTest")
		commentRepository.save(comment)
		Assertions.assertNotNull(comment.postId)
	}
}
