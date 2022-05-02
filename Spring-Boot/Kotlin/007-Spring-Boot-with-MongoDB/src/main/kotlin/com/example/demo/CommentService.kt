package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
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
    fun updateCommentBody(newBody: String, postId: Int) {
        commentRepository.findById(postId)
                .switchIfEmpty(Mono.error(NotFoundException()))
                .map { it.body = newBody
                    commentRepository.save(it)
                }
    }
    // DELETE
}