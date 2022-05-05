package com.example.demo

import org.springframework.data.mongodb.repository.DeleteQuery
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface CommentRepository: ReactiveMongoRepository<Comment, Int> {
    @Query("{'name': ?0}")
    fun findByUserName(id: String): Flux<Comment>
    @DeleteQuery("{'name': ?0}")
    fun deleteQueryByUserName(name: String): Flux<Comment>
}