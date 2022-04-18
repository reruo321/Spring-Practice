package com.example.demo

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface CommentRepository: ReactiveMongoRepository<Comment, Int> {
    @Query("{id:'?0'}")
    fun findByUserId(id: String): Flux<Comment>
}