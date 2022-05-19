package com.example.demo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension


@DataMongoTest //(properties = ["spring.mongodb.embedded.version=4.0.2"])
@ExtendWith(SpringExtension::class)
class WebClientConsumingTest{
    @Test
    fun commentSaveTest(@Autowired commentRepository: CommentRepository){
        val comment = Comment(999, 12345, "Test Guy", "test@test.com", "Test!")
        commentRepository.save(comment)
    }
}
