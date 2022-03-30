# Consuming a RESTful Web Service 2
In the previous project we used RestTemplate to consume the web services. If you have seen [the official Spring documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) on the class, you might have also noticed that as of 5.0, the class is in maintenance mode. Instead, they recommend us to use [org.springframework.web.reactive.client.WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)! After learning it, we will also post the contents using our POJO on the web!

## Objective
We will study how to fetch and post web services, using WebClient. Also, we will study many related things!

## WebClient
While RestTemplate is a blocking and synchronous client, **WebClient** is a non-blocking, reactive, and asynchronous web client, supporting HTTP/1.1.

To use it,
1. Create an instance of WebClient.
2. Be ready for a request.
3. Get & Handle the response.

### WebClient.Builder
The interface **WebClinent.Builder** provides us a lot of options so that we can either use default values, or even customize them for a mutable builder.

Here are some options you can configure with the interface.
#### URI
#### Header
#### Cookie
#### Request
#### Filter
#### Client Connector
#### Codec