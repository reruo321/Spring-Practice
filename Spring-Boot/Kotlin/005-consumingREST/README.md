# Consuming a RESTful Web Service
We will check [the official guide](https://spring.io/guides/gs/consuming-rest/) to create a REST-consuming application.

## Objective
We are going to build an application to retrieve a random Spring Boot quotation, using RestTemplate. We will fetch the resources from a website!

Since the guide used https://quoters.apps.pcfone.io/api/random, we are going to see how the website works first...

![005siteerror](https://user-images.githubusercontent.com/48712088/159046332-9f4e3b1e-f6be-4532-aed9-df4f650a0aad.png)

??? I do not think we can test our project with this website... Maybe deprecated?

So, I instead chose two other websites: https://jsonplaceholder.typicode.com/ and https://type.fit/api/quotes.

## Posts
Let's try to fetch some 'post' data from https://jsonplaceholder.typicode.com/posts!

First, create a data class, Post. Free to declare toString() yourself to easily check the log if fetched correctly.

(Post.kt)

    package com.example.demo

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Post(val userId: Int, val id: Int, val title: String, val body: String) {
        @Override
        override fun toString(): String {
            return "User ID: $userId\nNo. $id) \"$title\"\n$body\n"
        }
    }

Next, write a consuming application.

(ConsumingRestApplication.kt)

    package com.example.demo

    import org.slf4j.LoggerFactory
    import org.springframework.boot.CommandLineRunner
    import org.springframework.boot.SpringApplication
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.web.client.RestTemplateBuilder
    import org.springframework.context.annotation.Bean
    import org.springframework.http.converter.StringHttpMessageConverter
    import org.springframework.web.client.RestTemplate
    import java.nio.charset.StandardCharsets

    @SpringBootApplication
    class ConsumingRestApplication{
        companion object{
            private val log = LoggerFactory.getLogger(ConsumingRestApplication::class.java)
        }
        @Bean
        fun restTemplate(builder: RestTemplateBuilder): RestTemplate{
            val rest = builder.build()
            rest.messageConverters.add(0, StringHttpMessageConverter(StandardCharsets.UTF_8))
            return rest
        }
        @Bean
        @Throws(Exception::class)
        fun run(restTemplate: RestTemplate): CommandLineRunner {
            return CommandLineRunner {
            val response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", arrayOf<Post>()::class.java)
            val posts = response.body
            log.info(posts?.joinToString("\n"))
            }
        }
    }

    fun main(){
        SpringApplication.run(ConsumingRestApplication::class.java)
    }

### Result
    ./gradlew bootRun

![005post](https://user-images.githubusercontent.com/48712088/159284520-efd0e446-f7c3-40ff-b5c5-501f4fd6fa8f.png)

Works well!

## Quotes
Now let's move on to the next website, https://type.fit/api/quotes! You might have tried this modifying the previous codes a little bit...

    package com.example.demo

    import org.slf4j.LoggerFactory
    import org.springframework.boot.CommandLineRunner
    import org.springframework.boot.SpringApplication
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.boot.web.client.RestTemplateBuilder
    import org.springframework.context.annotation.Bean
    import org.springframework.http.converter.StringHttpMessageConverter
    import org.springframework.web.client.RestTemplate
    import java.nio.charset.StandardCharsets

    @SpringBootApplication
    class ConsumingRestApplication{
        companion object{
            private val log = LoggerFactory.getLogger(ConsumingRestApplication::class.java)
        }
        @Bean
        fun restTemplate(builder: RestTemplateBuilder): RestTemplate{
            val rest = builder.build()
            rest.messageConverters.add(0, StringHttpMessageConverter(StandardCharsets.UTF_8))
            return rest
        }
        @Bean
        @Throws(Exception::class)
        fun run(restTemplate: RestTemplate): CommandLineRunner {
            return CommandLineRunner {

                val response = restTemplate.getForEntity("https://type.fit/api/quotes", arrayOf<Quote>()::class.java)
                val quotes = response.body
                log.info(quotes?.joinToString("\n"))
            }
        }
    }

    fun main(){
        SpringApplication.run(ConsumingRestApplication::class.java)
    }

## Explanation

## Test
