package com.example.demo.quote

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Quote(val text: String, val author: String?) {
    @Override
    override fun toString(): String {
        return "\"$text\"\n   - $author"
    }
}