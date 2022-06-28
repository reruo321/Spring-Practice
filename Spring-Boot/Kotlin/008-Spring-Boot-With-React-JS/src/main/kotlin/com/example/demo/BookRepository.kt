package com.example.demo

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
interface BookRepository: CrudRepository<Book, Long> {
}