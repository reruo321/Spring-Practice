package com.example.demo

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1,
    val name: String = "",
    var genre: String = ""
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

    @Override
    override fun hashCode(): Int {
        return Objects.hash(id, name, genre)
    }

    @Override
    override fun toString(): String {
        return "Book{\nid=$id,\nname=$name,\ngenre=$genre\n}"
    }
}
