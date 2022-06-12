# Spring Boot with React.js
This time we will learn how to combine powerful backend of Spring Boot with React.js for easily understandable UI!

[The Official Guide](https://spring.io/guides/tutorials/react-and-spring-data-rest/)

Because there is already a great official guide for the project, my README will focus more on some Kotlin-specific things.

## Spring Data REST
**Spring Data REST** is built on top of the Spring Data project and makes it easy
to build hypermedia-driven REST web services that connect to Spring Data repositories.
## React.js
**React.js** is one of the Javascript library for creating a UI.
## H2
**H2** is a Java-based open-source JDBC API. It has embedded and server modes.

## @Entity
If you are trying to make a class annotated with @Entity, you might get this error.

    The class must have a public or protected, no-argument constructor.

To remove it, we should add a plugin called **No-arg compiler**.
In case you did not add JPA plugin, let's put them together!

(build.gradle.kts)

    plugins {
    val kotlinVersion = "1.6.21"
        ...
    	id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    }

    apply{
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.noarg")
    }
