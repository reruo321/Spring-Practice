# Consuming a RESTful Web Service 2
In the previous project we used RestTemplate to consume the web services. If you have seen [the official Spring documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) on the class, you might have also noticed that as of 5.0, the class is in maintenance mode. Instead, they recommend us to use [org.springframework.web.reactive.client.WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)! After learning it, we will also post the contents using our POJO on the web!

## Objective
We will study how to fetch and post web services, using WebClient. Also, we will study many related things!
