package com.example.demo

import org.springframework.data.mongodb.core.mapping.Document

@Document("comments")
data class Comment(val postId: Int,
                   var id: Int,
                   val name: String,
                   val email: String,
                   val body: String)
{
    override fun toString(): String {
        return "[Post No. $postId] - ID. $id\n\"$body\"\n- $name ($email)"
    }
}