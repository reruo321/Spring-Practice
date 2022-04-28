package com.example.demo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("comments")
data class Comment( @Id
                   private val postId: Int,
                   private var id: Int,
                   private val name: String,
                   private val email: String,
                   private val body: String)
{
    override fun toString(): String {
        return "[Post No. $postId] - ID. $id\n\"$body\"\n- $name ($email)"
    }
}