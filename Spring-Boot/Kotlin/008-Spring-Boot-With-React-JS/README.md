# Spring Boot with React.js
This time we will learn how to combine powerful backend of Spring Boot with React.js for easily understandable UI!

[The Official Guide](https://spring.io/guides/tutorials/react-and-spring-data-rest/)

Of course, there is already a great official guide for the project.

However, as it uses Java-Maven environment, we should pay attention to Kotlin-Gradle-specific things.

[Guide for Kotlin - Build a CRUD Application with Kotlin and React](https://developer.okta.com/blog/2020/01/13/kotlin-react-crud)

Based on this guide, I'll build up my own project.

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
    	kotlin("plugin.spring") version kotlinVersion
    }

    apply{
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.noarg")
    }

## Launching the Backend
※ NOTE: There are several ways to configure context path.

The guide from okta Developer added a class called *RepositoryRestConfigurer* with @Configuration,
and the official one suggests adding this to application.properties.

    spring.data.rest.base-path=/api

However, if those do not work well, any Spring Boot users with its version >= 1.2 would try this one!

Open *application.properties*, and set the property *server.servlet.context-path* as */api*.

(application.properies)

    server.servlet.context-path=/api

After writing all backend sources, whose names will be Book.kt, BookRepository.kt, DataRestApplication.kt, let's

    ./gradlew bootRun

on the terminal.

If you check a URL http://localhost:8080/api/books, you can see a list of the links in the application.
Also, a URL such as http://localhost:8080/api/books/1 allows you to see an entity with specific ID.

### Context Path & Servlet Path
There are two things called **context path** and **servlet path**.

The context path is a name with which a web application is accessed, thus the root of the application.
The default path is served as ("/"), and configuring the context path will also affect the servlet path.

    server.servlet.context-path=/context-path

On the other hand, the servlet path represents the path of the main DispatcherServlet.
DispatcherServlet acts as the front controller for Spring-based web applications,
which accepts all requests coming into the website and makes decisions that who are the right controllers to handle them.

    spring.mvc.servlet.path=/servlet-path

Applying both of properties will output the application servlet path: http://localhost:8080/context-path/servlet-path.

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
The manager is recommended to be managed through **Corepack**, so with Node.js >= 16.10 version,

    corepack enable

After settings, move to the *client* in the directory, and you can run yarn!

    cd client
    yarn start

However, if you are stuck on this step because of an internal error,

    Error: EPERM: operation not permitted, open 'C:\Program Files\nodejs\pnpm.ps1'

Try running the CMD or the IDEA as administrator. After that, try this command...

    yarn init -2

Suddenly, OMG! Too many things make us annoyed.

    yarn : 이 시스템에서 스크립트를 실행할 수 없으므로 C:\P    rogramFiles\nodejs\yarn.ps1 파일을 로드할 수 없습니다.
    ...
    + FullyQualifiedErrorId : UnauthorizedAccess

Let's run Windows PowerShell app as administrator, too.

    > ExecutionPolicy
    Restricted
    > Set-ExecutionPolicy Unrestricted 
    (type this) y
    > ExecutionPolicy
    Unrestricted

After solving the error, *yarn init -2* will output something like this on the IDEA terminal.

    {
        name: '008-Spring-Boot-With-React-JS',
        packageManager: 'yarn@3.2.2'
    }

Use this to update Yarn to the latest version.

    yarn set version stable

### Proxy Setting
Instead of using config values to set the resource server's URL, you can use proxy.

Add this to **client**/package.json file. Not one in the root directory!

    "proxy": "http://localhost:8080",

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
If you are using Intellij IDEA in Community version, you would not be able to get supports for .js or .css files,
or compose HTTP requests using .http files... (Ultimate version only)

You can use other applications such as Postman as an alternative for HTTP request.

### React Router Issues
Following the guide with the latest version of React Router, you might encounter some errors.
These occur because of difference of syntax in major version changes.

    (App.js)
    export 'Switch' (imported as 'Switch') was not found in 'react-router-dom'

If you read [the official documentation of React Router](https://reactrouter.com/docs/en/v6/upgrading/v5),
you may notice we should upgrade all <Switch> elements to <Routes>.

    export 'withRouter' (imported as 'withRouter') was not found in 'react-router-dom'

Since withRouter is no longer supported in v6, you should use **React hooks** such as useNavigate, useLocation, and useParams.
They must be used within a functional component or a custom React hook.
That is, they are not allowed to be called by a class component or at the top level, since it is not a Higher Order Component.

    Matched leaf route at location "/" does not have an element.
    This means it will render an <Outlet /> with a null value by default resulting in an "empty" page.

Also, React Router v6 does not use <Route exact>,
and all child route element and route *component* prop should be moved to a named *element* prop.

    // Code from guide
    return (
      <Router>
        <Switch>
          <Route
            path='/'
            exact={true}
            render={(props) => <Home {...props} api={api} navbar={navbar}/>}
          />
          <Route
            path='/book-list'
            exact={true}
            render={(props) => <BookList {...props} api={api} navbar={navbar}/>}
          />
          <Route
            path='/book-list/:id'
            render={(props) => <BookEdit {...props} api={api} navbar={navbar}/>}
          />
        </Switch>
      </Router>
    )

This would be adjusted to:

    // v6
    return (
      <Router>
        <Routes>
          <Route
            path='/'
            element={<Home api={api} navbar={navbar}/>}
          />
          <Route
            path='/book-list'
            element={<BookList api={api} navbar={navbar}/>}
          />
          <Route
            path='/book-list/:id/*'
            element={<BookEdit api={api} navbar={navbar}/>}
          />
        </Routes>
      </Router>
    )