package com.example.demo

import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<Book, Long> {
}