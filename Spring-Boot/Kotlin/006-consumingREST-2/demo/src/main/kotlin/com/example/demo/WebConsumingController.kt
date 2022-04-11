package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.filter.HiddenHttpMethodFilter
import org.springframework.web.reactive.function.client.WebClient

import reactor.core.publisher.Flux
import java.util.*

@Configuration
internal class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
    }
}

@RestController
@RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
class WebConsumingController {
    @Autowired
    val commentService = CommentService()
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    fun getComments(): Flux<Comment>{
        return commentService.getComments()
    }
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    fun postComments(@RequestBody comment: Comment): List<String>?{
        val commentList = commentService.postComments(comment)
                                        .collectList()
                                        .share()
                                        .block()
        return commentList?.map { "Post No. ${it.postId} by ${it.name}\n" }
    }
}

@Service("commentService")
class CommentService{
    private val client = WebClient.create()
    fun getComments(): Flux<Comment> {
        return client.get()
                .uri("https://jsonplaceholder.typicode.com/comments")
                .retrieve()
                .bodyToFlux(Comment::class.java)
    }
    fun postComments(comment: Comment): Flux<Comment> {
        return client.post()
                .uri("https://jsonplaceholder.typicode.com/comments")
                .retrieve()
                .bodyToFlux(Comment::class.java)
    }
}