package com.example.demo

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class Book(@Id @GeneratedValue private val id: Long,
            private val name: String,
            private val genre: String
        )
{
    @Override
    override fun equals(other: Any?): Boolean {
        if(this === other)
            return true
        if(other == null || javaClass.kotlin != other.javaClass.kotlin)
            return false
        val book = other as Book
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(genre, book.genre)
    }

}
