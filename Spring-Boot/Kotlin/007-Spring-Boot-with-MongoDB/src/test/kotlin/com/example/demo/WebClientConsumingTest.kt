package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [WebClientConsuming::class])
class WebClientConsumingTest(@Autowired private val commentRepository: CommentRepository) {
	@ParameterizedTest
	@Throws(Exception::class)
	fun saveCommentTest() {
		val comment = Comment(123, 456, "Test Guy", "test@test.com", "TestTestTest")
		commentRepository.save(comment)
		Assertions.assertNotNull(comment.postId)
	}
}
