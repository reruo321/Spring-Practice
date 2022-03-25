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
Now let's move on to the next website, https://type.fit/api/quotes! You might have tried this by modifying the previous codes a little bit...

### First Try
(Quote.kt)

    package com.example.demo

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Quote(val text: String, val author: String) {
        @Override
        override fun toString(): String {
            return "\"$text\"\n   - $author"
        }
    }
    
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

Testing this, it seems to be okay but soon it ends with an exception.

    java.lang.IllegalStateException: Failed to execute CommandLineRunner

### Content Type

If you look at the message carefully, you will find

    Caused by: org.springframework.web.client.UnknownContentTypeException: Could not extract response: no suitable HttpMessageConverter found for response type [class [Lcom.example.demo.Quote;] and content type [text/plain;charset=UTF-8] 

It says response type or "content type" is not suitable. **Content type** is an indicator telling how to interpret the data present in the request/response. It is one kind of **HTTP headers**, which is a data structure representing HTTP request or response headers. JSON is also one of the content types we can consume or produce! Moreover, there are three directives in the HTTP headers Content-type: **media type**, **charset**, and **boundary**.

Then how can we know what kind of media from the response is? Let's find it on the first worked case!

    val response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", arrayOf<Post>()::class.java)
        
From here, we could get the response. It would contain headers with the information of content type. We can dig it up by calling the getters...

    // Kotlin expression
    response.headers.contentType
    
    // Java expression
    response.getHeaders().getContentType()
    
When you get a log printing the name of the content type, you will see

    application/json;charset=utf-8

Oh, the first website was giving us the response with the JSON type! Then how about the second one?

Before we try some deeper logging, to check the response in string format, change a statement something like this,

    val response = restTemplate.getForEntity("https://type.fit/api/quotes", arrayOf<Quote>()::class.java)
    
To this:

    val response = restTemplate.getForEntity("https://type.fit/api/quotes", String::class.java)
    
This will retrieve a response in ResponseEntity\<String\> type. Now see its content type.

    text/plain;charset=UTF-8

Okay, it turned out to be plain text!

One more thing: if you try these two statements,

    val quotes = response.body
    log.info(quotes)
    
You can log the whole contents response from the website!

![005plaintext](https://user-images.githubusercontent.com/48712088/159326602-6ed032ed-aec4-4e26-9bfe-55c53a596fe9.png)

### Deserialize
Now you may wonder how to convert the plain text to JSON, so that you can finally deserialize the data to your custom POJO. Take a look at restTemplate().

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate{
        val rest = builder.build()
        return rest
    }

If you log rest.messageConverters and their supportedMediaTypes,

    rest.messageConverters.forEach{ log.info("$it - ${it.supportedMediaTypes}\n") }

you will notice a bunch of message converters included in your RestTemplate. Among them, we need **MappingJackson2HttpMessageConverter**, which can read and write JSON using Jackson's ObjectMapper.

Oh, you might already have the converter, but it won't support text/plain.

![005converter](https://user-images.githubusercontent.com/48712088/159547611-6d9de23f-2136-4254-b87d-77c6910a6121.png)

(Look, none of my MappingJackson2HttpMessageConverters support text/plain!)

What we should do here is: of course let's add a converter supporting it!

        val converter = MappingJackson2HttpMessageConverter()
        converter.supportedMediaTypes = listOf(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        rest.messageConverters.add(0, converter)

p.s. If you have a trouble with the conversion while using Kotlin, it might be:

    No Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator.

This occurs because your Jackson library does not know how to create your model without any empty constructor, or a constructor with some parameters not annotated with @JsonProperty("field_name"). Use the annotation @JsonProperty("field_name"), or if you think it will be a little bit dirty, register a module called [jackson-module-kotlin](https://github.com/FasterXML/jackson-module-kotlin).

(build.gradle.kts) Add it into the dependencies.

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    
(ConsumingRestApplication.kt) Register the module with your ObjectMapper instance. Pick up one of the usage from the website!

    converter.objectMapper = ObjectMapper().registerKotlinModule()

Here are the final form of my custom restTemplate().

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate{
        val rest = builder.build()
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = ObjectMapper().registerKotlinModule()
        converter.supportedMediaTypes = listOf(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)
        rest.messageConverters.add(0, converter)
        return rest
    }

### Non-nullable Type Error
Almost done! Now you will get an error like this:

    Caused by: org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Instantiation of [simple type, class com.example.demo.Quote] value failed for JSON property author due to missing (therefore NULL) value for creator parameter author which is a non-nullable type;

This might be trivial but very important. (I was also in for an hour) Let's go back to see our POJO definition.

(Quote.kt)

    package com.example.demo

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Quote(val text: String, val author: String) {
        @Override
        override fun toString(): String {
            return "\"$text\"\n   - $author"
        }
    }
    
The funny thing is, if you erase all things on *author*, you can fetch Quotes! What was the problem? By looking the website,

![005quote](https://user-images.githubusercontent.com/48712088/159761639-203bd50b-589e-4f64-b6b2-8e3af23ba96e.png)

There were some quotes whose author is *null*. Since we defined *author* in a non-nullable type, the constructor could not get the data! Let's add a question mark to the author's type, String.

    data class Quote(val text: String, val author: String?)

![005output](https://user-images.githubusercontent.com/48712088/159762491-a33dce57-fccd-4370-b7f1-6bb314ce21a6.png)

Awesome!

## Running
I made the codes into two separated projects. If you want to run it, you should edit build.gradle to set the main class.

(build.gradle.kts) Write one of these to set the main class of the whole project.

    springBoot {
    //   mainClass.set("com.example.demo.post.PostConsumingRestApplicationKt")
    //   mainClass.set("com.example.demo.quote.QuoteConsumingRestApplicationKt")
    }

After setting, let's run it on the terminal!

    ./gradlew bootRun

The project is gonna be too big, so I will continue some more related studies in the next project...
