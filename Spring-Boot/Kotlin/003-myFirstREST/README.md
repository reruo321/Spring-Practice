# My First RESTful Web Service
This follows [the official guide](https://spring.io/guides/gs/rest-service/) for building the RESTful Web Service for the first time.

## Objective
As the website tutorial says, we will make a simple web service. Read it for more details, please!
What in my project a little bit differs from the original guide would be the language. I'll use Kotlin (wanna study the language), instead of using Java. Both of them perform very nice works, though.

## Creation
After following the settings from the previous units, it's time to create the classes for the project! I used the demo project donwloaded from [Spring Initializr](https://start.spring.io/) as a base.

First, let's create a new class in *src/main/kotlin/com/example/demo/Greeting.kt*.

## Test
If you finished your project, run the application by putting this on the terminal.

    ./gradlew bootRun

![003tomcat](https://user-images.githubusercontent.com/48712088/155746385-bb17aa31-a9e4-4a2c-8636-d5752ab61a84.png)

Wait a minute, and if you see this line, your service will be up on http://localhost:8080/. After the message "Completed initialization in N ms", let's check http://localhost:8080/greeting!


## Explanation
