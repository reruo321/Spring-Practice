package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseLoader(@Autowired private val repository: BookRepository): CommandLineRunner {
    @Override
    @Throws(Exception::class)
    override fun run(vararg args: String?) {
        repository.save(Book("TEPS", "Study"))
    }


}