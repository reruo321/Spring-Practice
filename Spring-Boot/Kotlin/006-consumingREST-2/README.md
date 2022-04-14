# Consuming a RESTful Web Service 2
In the previous project we used RestTemplate to consume the web services. If you have seen [the official Spring documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) on the class, you might have also noticed that as of 5.0, the class is in maintenance mode. Instead, they recommend us to use [org.springframework.web.reactive.client.WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)! After learning it, we will also post the contents using our POJO on the web!

## Objective
We will study how to fetch and post web services, using WebClient. Also, we will study many related things!

## WebClient
While RestTemplate is a blocking and synchronous client, **WebClient** is a non-blocking, reactive, and asynchronous web client, supporting HTTP/1.1. It is a part of Spring WebFlux, which provides reactive programming support for web applications.

To use it,
1. Create an instance of WebClient.
2. Be ready for a request.
3. Get & Handle the response.

### WebClient.Builder
The interface **WebClinent.Builder** provides us a lot of options so that we can either use default values, or even customize them for a mutable builder.

Here are some options you can configure with the interface.
#### URI
**URI**, or **Uniform Resource Identifier**, is a unique sequence of characters that identifies a logical or physical resource used by web technologies. Both URL(Uniform Resource Locator), whose concept sounds familiar to us, and URN(Uniform Resource Name) are URI.
#### Header
As we have learned on the last project, **HTTP Header** contains a list of informative strings. Fields *Content-Type* and *Cookie* are also included in it.
#### Cookie
While we are surfing the web, a web server creates small blocks of data called **HTTP cookies**, and places them into our devices via the web browser.
#### Request
It enables a consumer to customize every request being built.
#### Filter
We can also add a filter to edit or examine a client request/response.
#### Client Connector
Configure the ClientHttpConnector to use, whose default is set to ReactorClientHttpConnector.
#### Codec
**Codec** encodes/decodes the outgoing/incoming data from the network call.

## GET
We will first consume the contents from [here](https://jsonplaceholder.typicode.com/comments) using WebClient! Do not worry, the way to do it is quite similar to that of RestTemplate.

Before we start to program our project, let's add two dependencies!

	implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.6")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")

And looking at the website data, we can declare our comment POJO to deserialize it.

(Comment.kt)

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
    
Next, let's declare the application! We may use create() or builder().build() to create a new WebClient.

	val client = WebClient.create()
	
OR
	
	val client = WebClient.builder().build()

If you call a builder, you can build it after adding some configurations!

	val client = WebClient.builder()
	.baseUrl("http://localhost:8080")
	.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
	.build()

If you finish your WebClient creation, it's time to get the data! Wait, what are *Mono* and *Flux*? They are reactive types implementing the *Publisher* interface. If you want to retrieve a single resource, choose **Mono**. It handles zero or one result. Otherwise, select **Flux** to represent 0..N items.

Since we are going to GET the flux of Comments, let's call retrieve(). If you are only interested in the body, use bodyToFlux() after that. To extract a ResponseEntity with status, headers, and body, use toEntity().

	val commentFlux = client.get()
		.uri("https://jsonplaceholder.typicode.com/comments")
		.retrieve()
		.bodyToFlux(Comment::class.java)

※ There are advanced alternatives to use awaitExchange{} or exchangeFlow{} (For Java, use exchangeToMono() and exchangeToFlux()) instead of retrieve(), for more controls via access to the ClientResponse.

Once you get it, subscribe it to consume the data from the upstream publisher, Flux. We can print it by putting println() into subscribe().

	commentFlux.subscribe(System.out::println)

(WebClientConsuming.kt)

	package com.example.demo

	import org.slf4j.LoggerFactory
	import org.springframework.boot.CommandLineRunner
	import org.springframework.boot.SpringApplication
	import org.springframework.boot.autoconfigure.SpringBootApplication
	import org.springframework.context.annotation.Bean
	import org.springframework.web.reactive.function.client.WebClient

	@SpringBootApplication
	class WebClientConsuming{
	    companion object{
		private val log = LoggerFactory.getLogger(WebClientConsuming::class.java)
	    }
	    @Bean
	    fun webClientRestCall(): CommandLineRunner {
		return CommandLineRunner {
		    val client = WebClient.builder().build()
		    val commentFlux = client.get()
			    .uri("https://jsonplaceholder.typicode.com/comments")
			    .retrieve()
			    .bodyToFlux(Comment::class.java)
		    commentFlux.subscribe(System.out::println)
		}
	    }
	}

	fun main(){
	    SpringApplication.run(WebClientConsuming::class.java)
	}

## POST
Now we are going to post our data using WebClient! Let's post three new comments, using WebClient.post().

To check the result,

	./gradlew bootRun
	
And try to open http://localhost:8080/post. Use an application such as Postman to easily send your POST request!

![006post](https://user-images.githubusercontent.com/48712088/163443217-58c51513-15ca-43a7-858b-dc58f6fac351.png)

### HTTP 405

	// console
	[org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported]
	// uri
	There was an unexpected error (type=Method Not Allowed, status=405).
	Request method 'GET' not supported

The error happens because the server does not support the method sent in the client request. Therefore, we should explicitly define a mapping for unsupported methods. Add this to the controller, and it will support both GET and POST.

	@RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])

(The solution will look like this.)

	@RestController
	@RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
	class WebConsumingController {...}
