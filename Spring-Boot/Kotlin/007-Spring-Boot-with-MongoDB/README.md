# Spring Boot with MongoDB
Spring Boot supports connections on various databases, and we will learn how to connect MongoDB to the Spring Boot project.

## MongoDB
**MongoDB** is a NoSQL(Not only SQL) database program, which does not use table to store data, unlike relational DBs.
Among the various types of NoSQL, it is document database, using document as a record storing information on an object & any of its related metadata.
Its values can be strings, integers, ...etc., or even objects! Also, the documents can be stored in formats such as JSON, BSON, and XML.
The database supports a group of documents called "collection", and not all the elements should have the same fields.

With Spring Boot, we can build a flexible REST API for MongoDB, allowing ourselves to execute CRUD(Create, Read, Update, Delete)!

## Outline
![007structure](https://user-images.githubusercontent.com/48712088/165133252-391572fd-bd34-4bef-8d9d-9af380bd120e.png)

## Settings
### MongoDB
If you are the first time to try MongoDB, go to [the website](https://www.mongodb.com/) to register your organization. (allowing individual)
Once you log in, create a new project, and a new cluster in it. Cluster in MongoDB usually means either a "sharded cluster" or a "replica set".
It prevents the fatal data loss when the only main server fails. It is also good for extra read operations capacity and data locality.

![007mongo](https://user-images.githubusercontent.com/48712088/164534251-6e0d5887-213e-4eeb-a62f-dc40e643ac9b.png)

When you succeed in creating a cluster, click "Connect" and select "Connect your application" so that you get the connection string.
Copy and paste it into src/main/resources/application.properties. Do not forget to add your own password!

(src/main/resources/application.properties) Change the uri and database name to yours.
Even if there is no database with specified name in the cluster, it will be automatically created.

    spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/myDatabase
    spring.data.mongodb.database=myDatabase

If your setting is wrong, you will get these exceptions...

    No server chosen by com.mongodb.reactivestreams.client.internal.ClientSessionHelper...
    exception={com.mongodb.MongoSocketOpenException: Exception opening socket}...
    java.net.ConnectException: Connection refused: no further information...
    There was an unexpected error (type=Service Unavailable, status=503).

### Dependencies
Add some additional dependencies to use MongoDB and Spring Security. The latter framework dependency will be optional, but note that my project tried this one too.

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:2.6.6")
    implementation("org.springframework.boot:spring-boot-starter-security")

This is the full list of mine:

    dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:2.6.6")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    }



## POST
Now we are going to post our data using WebClient! Let's post three new comments, using WebClient.post().

To check the result,

	./gradlew bootRun

And try to open http://localhost:8080/post. Use an application such as Postman to easily send your POST request! Do not forget to send your request in JSON type.

![006post](https://user-images.githubusercontent.com/48712088/163443217-58c51513-15ca-43a7-858b-dc58f6fac351.png)

If you get the response in form you set from your application, it means your communication was right.

### 
e will realize the POST result on our website, not only just checking the response! We are going to add a dependency to use ReactiveMongoRepository, the reactive Mongo-specific Repository interface.

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:2.6.6")

### HTTP 405

	// console
	[org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported]
	// uri
	There was an unexpected error (type=Method Not Allowed, status=405).
	Request method 'GET' not supported

The error happens because the server does not support the method sent in the client request. Therefore, we should explicitly define a mapping for unsupported methods. Add this to the controller, and it will support both GET and POST.

	@RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])

(The solution will look like this.)

	@RestController
	@RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
	class WebConsumingController {...}
