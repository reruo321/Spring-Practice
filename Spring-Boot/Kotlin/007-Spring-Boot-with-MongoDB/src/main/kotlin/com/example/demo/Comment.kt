package com.example.demo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("comments")
data class Comment( @Id
                   val postId: Int,
                   private val id: Int,
                   val name: String,
                   private val email: String,
                   var body: String)
{
    override fun toString(): String {
        return "[Post No. $postId] - ID. $id\n\"$body\"\n- $name ($email)"
    }
}