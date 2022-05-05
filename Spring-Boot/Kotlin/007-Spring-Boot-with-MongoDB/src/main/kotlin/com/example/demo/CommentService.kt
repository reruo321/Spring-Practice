package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.logging.Logger

@Service("commentService")
class CommentService(@Autowired private val commentRepository: CommentRepository){
    private val client = WebClient.create()
    // CREATE
    fun createComment(comment: Comment): Mono<Comment>{
        return commentRepository.save(comment)
    }
    // READ
    fun getCommentsFromWeb(): Flux<Comment> {
        return client.get()
                .uri("https://jsonplaceholder.typicode.com/comments")
                .retrieve()
                .bodyToFlux(Comment::class.java)
    }
    fun findAll(): Flux<Comment>{
        return commentRepository.findAll()
    }
    fun findByPostId(postId: Int): Mono<Comment>{
        return commentRepository.findById(postId)
                .switchIfEmpty(Mono.error(NotFoundException()))
    }
    fun findByUserName(name: String): Flux<Comment>{
        return commentRepository.findByUserName(name)
                .switchIfEmpty(Flux.error(NotFoundException()))
    }
    // UPDATE
    fun updateComment(newComment: Comment, postId: Int): Mono<Comment>{
        return commentRepository.save(newComment)
    }
    // DELETE
    fun deleteComment(postId: Int): String{
        commentRepository.deleteById(postId)
                .switchIfEmpty(Mono.error(NotFoundException()))
        return "Deleted No. $postId Post"
    }
    fun deleteCommentsByUserName(name: String): String{
        commentRepository.deleteQueryByUserName(name).subscribe()
        return "Deleted $name's Posts"
    }
}