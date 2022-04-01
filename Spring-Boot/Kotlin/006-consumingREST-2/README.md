# Consuming a RESTful Web Service 2
In the previous project we used RestTemplate to consume the web services. If you have seen [the official Spring documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) on the class, you might have also noticed that as of 5.0, the class is in maintenance mode. Instead, they recommend us to use [org.springframework.web.reactive.client.WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)! After learning it, we will also post the contents using our POJO on the web!

## Objective
We will study how to fetch and post web services, using WebClient. Also, we will study many related things!

## Outline
* Request - http headers, http method, requested url, bean type, http body
* Response - http status, http headers, http body

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
#### Filter
#### Client Connector
#### Codec
