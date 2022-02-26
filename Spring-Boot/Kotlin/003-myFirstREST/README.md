# My First RESTful Web Service
This follows [the official guide](https://spring.io/guides/gs/rest-service/) for building the RESTful Web Service for the first time.

## Objective
As the website tutorial says, we will make a simple web service. Read it for more details, please!
What in my project a little bit differs from the original guide would be the language. I'll use Kotlin (wanna study the language), instead of using Java. Both of them perform very nice works, though.

## Creation
After following the settings from the previous units, it's time to create the classes for the project! I used the demo project donwloaded from [Spring Initializr](https://start.spring.io/) as a base.

First, let's create a new class in src/main/kotlin/com/example/demo/Greeting.kt.

    package com.example.demo

    data class Greeting(val id: Long, val content: String)
    
Quite simple in Kotlin! This will be the resource representation class for the data **id** and **content**. The web starter we chose contains Jackson JSON, a suite of data-processing tools for Java, automatically gathers instances of type **Greeting** into JSON for us.

Next, we need a controller to handle HTTP requests. Attach **@RestController** annotation to the components. Define **GreetingController** in src/main/kotlin/com/example/demo/GreetingController.kt to handle **GET** requests for **/greeting** by returning a new instance of the Greeting class.

    package com.example.demo

    import java.util.concurrent.atomic.AtomicLong

    import org.springframework.web.bind.annotation.GetMapping
    import org.springframework.web.bind.annotation.RequestParam
    import org.springframework.web.bind.annotation.RestController

    @RestController
    class GreetingController {
        companion object{
            private const val template: String = "Hello, %s!"
            private val counter = AtomicLong()
        }
        @GetMapping("/greeting")
        fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting{
            return Greeting(counter.incrementAndGet(), String.format(template, name))
        }
    }

## Test
If you finished your project, run the application by putting this on the terminal.

    ./gradlew bootRun

![003tomcat](https://user-images.githubusercontent.com/48712088/155746385-bb17aa31-a9e4-4a2c-8636-d5752ab61a84.png)

Wait a minute, and if you see this line, your service will be up on http://localhost:8080/. After the message "Completed initialization in N ms", let's check http://localhost:8080/greeting!


## Explanation
