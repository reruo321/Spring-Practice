# Structure

## Components
### Spring Framework
We are using Spring framework for our project. It supports us for developing applications.
### Spring Boot
Spring Boot is an extension of the Spring framework to make developing web application and microservices. It provides all features of Spring but also easier to use. It is very useful to develop REST(REpresentational State Transfer) API.
### Gradle
Gradle is the most common build tool in Kotlin, and recommended since it provides Kotlin DSL(Domain Specific Language).

## Files
Both .kt and .kts are Kotlin files including Kotlin source codes.
### .kt
This is the normal Kotlin source file being compiled by the Kotlin compiler.
### .kts
.kts is the script file, without needing an additional compilation.

    kotlinc -script FILE_NAME.kts
    
You can run it with this command. It is like a bash or python script, so it does not need main function in it.
