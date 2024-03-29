package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@EnableWebSecurity
@Configuration
internal class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
    }
}

@RestController
class WebConsumingController(@Autowired private val commentService: CommentService) {
    @GetMapping("/comment")
    fun getComments(): Flux<Comment>{
        return commentService.findAll()
    }
    @GetMapping("/web")
    fun getCommentsFromWeb(): Flux<Comment>{
        return commentService.getCommentsFromWeb()
    }
    @GetMapping(value = ["/comment/post/{postId}"])
    fun getCommentByPostId(@PathVariable("postId") postId: Int): Mono<Comment>{
        return commentService.findByPostId(postId)
    }
    @GetMapping(value = ["/comment/username/{name}"])
    fun getCommentByUserName(@PathVariable("name") name: String): Flux<Comment>{
        return commentService.findByUserName(name)
    }
    @PostMapping(value = ["/comment"])
    fun postComments(@RequestBody comment: Comment): Mono<Comment> {
        return commentService.createComment(comment)
    }
    @PutMapping(value = ["/comment/update/{postId}"])
    fun updateComment(@RequestBody newComment: Comment, @PathVariable("postId") postId: Int): Mono<Comment>{
        return commentService.updateComment(newComment, postId)
    }
    @PutMapping(value = ["/comment/update/body/{postId}"])
    fun updateCommentOnlyBody(@RequestParam body: String, @PathVariable("postId") postId: Int): String{
        return commentService.updateCommentOnlyBody(body, postId)
    }
    @DeleteMapping(value = ["/comment/delete/{postId}"])
    fun deleteComment(@PathVariable("postId") postId: Int): String{
        return commentService.deleteComment(postId)
    }
    @DeleteMapping(value = ["/comment/delete/user/{name}"])
    fun deleteCommentByUserName(@PathVariable("name") name: String): String{
        return commentService.deleteCommentsByUserName(name)
    }
}