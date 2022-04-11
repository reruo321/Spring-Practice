package com.example.demo

data class Comment(val postId: Int,
                    val id: Int,
                    val name: String,
                    val email: String,
                    val body: String)
{
    override fun toString(): String {
        return "[Post No. $postId] - ID. $id\n\"$body\"\n- $name ($email)"
    }
}