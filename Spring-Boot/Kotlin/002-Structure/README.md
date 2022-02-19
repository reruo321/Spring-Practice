# Structure
This time we will learn the structure of the project to understand it.
## Components
### Spring Framework
We are using Spring framework for our project. It supports us for developing applications.
### Spring Boot
Spring Boot is an extension of the Spring framework to make developing web application and microservices. It provides all features of Spring but also easier to use. It is very useful to develop REST(REpresentational State Transfer) API.
### Gradle
Gradle is the most common build tool in Kotlin, and recommended since it provides Kotlin DSL(Domain Specific Language).

## Files
These are the files included in the project.
### .gradle
![002dotGradle](https://user-images.githubusercontent.com/48712088/154800008-2526f362-1c63-4cc5-8742-9e7b20fd5455.png)

A folder **.gradle**, containing native information and caches (on plugins and dependencies), is used by our IDE, Intellij IDEA. At the first time the project is built, it download plugins and dependencies into the folder, and gets them from it when they are needed. the IDE This can be safely ignored by users, and we should set our version control to ignore it.
### .idea
![002idea](https://user-images.githubusercontent.com/48712088/154806596-e9e1c239-670e-46b3-be37-c1766f528ee1.png)

 Since Intellij IDEA 2017.3,

### out
![002out](https://user-images.githubusercontent.com/48712088/154807341-8510e2f3-eb34-4249-ae2e-74fd43d4aef7.png)

### src
![002src](https://user-images.githubusercontent.com/48712088/154807348-9265053e-1895-47a0-b159-c50a026f8d3d.png)

Both .kt and .kts are Kotlin files including Kotlin source codes.
#### .kt
This is the normal Kotlin source file being compiled by the Kotlin compiler.
#### .kts
.kts is the script file, without needing an additional compilation.

    kotlinc -script FILE_NAME.kts
    
You can run it with this command. It is like a bash or python script, so it does not need main function in it.
