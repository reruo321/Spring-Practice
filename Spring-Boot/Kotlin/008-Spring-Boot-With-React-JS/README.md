# Spring Boot with React.js
This time we will learn how to combine powerful backend of Spring Boot with React.js for easily understandable UI!

[The Official Guide](https://spring.io/guides/tutorials/react-and-spring-data-rest/)

Because there is already a great official guide for the project, my README will focus more on some Kotlin-specific things.

[guide 2](https://developer.okta.com/blog/2020/01/13/kotlin-react-crud)

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

## Launching the Backend
After writing all backend sources whose extension will be .kt, let's

    ./gradlew bootRun

on the terminal.

(Note that the official tutorial uses Maven, instead of Gradle.)

If you check a URL http://localhost:8080/api/books, you can see a list of the links in the application.
Also, a URL such as http://localhost:8080/api/books/1 allows you to see an entity.

## npm
**npm**(Node Package Manager) packages are defined in files called *package.json*, which is written in JSON.
It must include at least two fields: *name* and *version*.

You should make and put package.json in the root of the project!

(Example of package.json)

    {
        "name": "spring-data-rest-and-reactjs",
        "version": "0.1.0",
        "description": "Spring Data REST & React JS Test in Kotlin + Gradle Environment",
        "repository": {
        "type": "git",
        "url": "git@github.com:reruo321/Spring-Practice.git"
        },
        ...
    }

## Frontend Settings
We will create a client application and add some dependencies.

### Yarn
**Yarn** is a package manager, and you can download it from [the official website](https://yarnpkg.com/getting-started/install).

#### Installing Yarn
The manager is recommended to be managed through **Corepack**, so with Node.js >= 16.10 version,

    corepack enable

However, if you are stuck on this step because of an internal error,

    Error: EPERM: operation not permitted, open 'C:\Program Files\nodejs\pnpm.ps1'

Try running the CMD or the IDEA as administrator.

## Issues
### Base Path is Not Working

    spring.data.rest.base-path=/api

This only works on the Spring Boot whose version is below 2.0.

    server.servlet.context-path=/api

Use this instead for 2.X version.

### Failed to configure a DataSource

    Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

There is [a good explanation](https://www.baeldung.com/spring-boot-failed-to-configure-data-source) on this problem,
and you can follow the settings to solve this issue.

### Extension Support
You can ignore the support tooltips on .js or .css in Intellij IDEA Community, even though a specialized editor for them does not exist in this edition.
