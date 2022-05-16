package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class WebClientConsumingTest(@Autowired private val commentRepository: CommentRepository) {
	@Test
	fun saveCommentTest() {
		val comment = Comment(123, 456, "Test Guy", "test@test.com", "TestTestTest")
		commentRepository.save(comment)
		Assertions.assertNotNull(comment.postId)
	}
}
