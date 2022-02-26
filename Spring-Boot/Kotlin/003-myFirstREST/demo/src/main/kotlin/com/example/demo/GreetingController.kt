package com.example.demo

import java.util.concurrent.atomic.AtomicLong

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    companion object{
        private const val template: String = "Hello, %s!"
        private val counter = AtomicLong()
    }
    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting{
        return Greeting(counter.incrementAndGet(), String.format(template, name))
    }
}