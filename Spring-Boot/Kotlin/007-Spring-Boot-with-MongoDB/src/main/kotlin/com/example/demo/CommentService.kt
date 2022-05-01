package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service("commentService")
class CommentService(@Autowired private val commentRepository: CommentRepository){
    private val client = WebClient.create()

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
    fun createComment(comment: Comment): Mono<Comment>{
        return commentRepository.save(comment)
    }
}