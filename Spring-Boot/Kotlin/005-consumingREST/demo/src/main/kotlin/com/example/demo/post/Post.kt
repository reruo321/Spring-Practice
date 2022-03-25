package com.example.demo.post

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Post(val userId: Int, val id: Int, val title: String, val body: String) {
    @Override
    override fun toString(): String {
        return "User ID: $userId\nNo. $id) \"$title\"\n$body\n"
    }
}