package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Configuration
internal class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
    }
}

@RestController
//@RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
class WebConsumingController(@Autowired private val commentService: CommentService) {
    @GetMapping("/comment")
    fun getComments(): Flux<Comment>{
        return commentService.findAll()
    }
    @GetMapping("/web")
    fun getCommentsFromWeb(): Flux<Comment>{
        return commentService.getCommentsFromWeb()
    }
    @GetMapping(value = ["/comment/{id}/"])
    fun getCommentByPostId(@PathVariable("id") id: Int): Mono<Comment>{
        return commentService.findByPostId(id)
    }
    @PostMapping(value = ["/comment"])
    fun postComments(@RequestBody comment: Comment): Mono<Comment> {
        return commentService.saveComment(comment)
    }
}
