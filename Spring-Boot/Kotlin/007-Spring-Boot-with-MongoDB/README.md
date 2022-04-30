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


### MongoDB
If you are the first time to try MongoDB, go to [the website](https://www.mongodb.com/) to register your organization. (allowing individual)
Once you log in, create a new project, and a new cluster in it. Cluster in MongoDB usually means either a "sharded cluster" or a "replica set".
It prevents the fatal data loss when the only main server fails. It is also good for extra read operations capacity and data locality.

![007mongo](https://user-images.githubusercontent.com/48712088/164534251-6e0d5887-213e-4eeb-a62f-dc40e643ac9b.png)

When you succeed in creating a cluster, click "Connect" and select "Connect your application" so that you get the connection string.

Copy and paste it into *application.properties*, and change the uri and the database name to yours.
Even if there is no database with the specified name in the cluster, it will be automatically created.

(src/main/resources/application.properties)

    spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/myDatabase
    spring.data.mongodb.database=myDatabase

Oh, here is a nice [official MongoDB guide](https://www.mongodb.com/compatibility/spring-boot) to start it with Spring Boot, so let's follow that step by step!

#### @Document
While a **document** in MongoDB acts as a record in RDB, **collection** does as a table. 
An annotation **@Document** will set the collection name, being used by a model.
The model in our project is the POJO, the Comment class.

    @Document("comments")
    data class Comment(...){...}

Also, add an annotation **@Id** to specify the primary key __id of the document, or MongoDB will automatically generate the field.

    @Id
    private val postId: Int

#### Spring Boot MongoDB API
There is an API to easily connect the model and the DB, and to use methods for CRUD operations.
Its implementation is done in a repository, so we will create this one.
Add **@Repository** to your repository interface!

    @Repository
    interface CommentRepository: ReactiveMongoRepository<Comment, Int> {
        @Query("{'id': ?0}")
        fun findByUserId(id: String): Flux<Comment>
    }

※ NOTE: A **@Query** is used to specify a custom query. A MongoDB query
    
    @Query("{'id': ?0}")

will be

    SELECT * FROM comment WHERE id = ?

in a SQL query.

#### Services
You might feel somewhat messy to the codes of services, so declare them into a class.

    @Service("commentService")
    class CommentService(@Autowired private val commentRepository: CommentRepository){...}

CRUD operations can become a part of the application services. For example, a READ method findAll() will be

    fun findAll(): Flux<Comment>{
        return commentRepository.findAll()
    }

#### Controller
Since we are going to use the MVC, we should additionally implement a controller and a view. This enables us to see the operations on the web. Otherwise, we should check them using the command line interface, *CommandLineRunner*, as the tutorial does.

Let the repository **@Autowired** into the controller, so that the controller can interact with it.

    @RestController
    class WebConsumingController(@Autowired private val commentService: CommentService) {...}

To make a view of all comments in the repository, try this. It will allow us to READ them via the web.

    @GetMapping("/comment")
    fun getComments(): Flux<Comment>{
        return commentService.findAll()
    }

#### Main Class
Do not forget an annotation, **@EnableMongoRepositories**!

    @SpringBootApplication
    @EnableMongoRepositories
    class WebClientConsuming{...}

## CRUD
After programming, we are going to run the application! First, make sure the MongoDB cluster is connected. On the terminal,

	./gradlew bootRun

Open http://localhost:8080/web, and see if the contents from https://jsonplaceholder.typicode.com/comments exist. Note that this one is from the previous project, just to check the connection.

After checking, open http://localhost:8080/comment. Right now, you will see nothing other than a pair of [ ]. This is because our database does not have any data to show yet.
Stay tuned, and start our main dish, CRUD!

### CREATE
Let's **CREATE** our document!

Use an application such as Postman to easily send your POST request! Do not forget to send your request in JSON type.

![006post](https://user-images.githubusercontent.com/48712088/163443217-58c51513-15ca-43a7-858b-dc58f6fac351.png)

1. Select POST, and give http://localhost:8080/comment as a request URL.
2. Select Body, raw, and JSON.
3. Write your JSON to POST and click Send.

If you successfully sent it, have a look at the web with READ operation. It will show the new document by your CREATE.

### READ
**READ** operation literally READs the data from the database.

Oh, what is the problem when MongoDB says my data exists well, but does not show on the web?
If your READ looks like

    [{}, {}]

it will not be your or your application's fault, but actually the data is hidden. Private fields in the document (POJO) will make the view of them private, too.

### Partial READ
You can also READ specific data in the database! For example, to make a comment with the postId value you put into the URL come out,

    // WebConsumingController.kt
    @GetMapping(value = ["/comment/{postId}"])
    fun getCommentByPostId(@PathVariable("postId") postId: Int): Mono<Comment>{
        return commentService.findByPostId(postId)
    }

    // CommentService.kt
    fun findByPostId(postId: Int): Mono<Comment>{
    return commentRepository.findById(postId)
        .switchIfEmpty(Mono.error(NotFoundException()))
    }

you can set *postId* as the path variable of http://localhost:8080/comment/#POST_ID# while using @GetMapping,
so that changing #POST_ID# part shows a READ result with your *postId* input.
For example, if you access to http://localhost:8080/comment/12345, you will see a comment whose *postId* is 12345.

Note that since the @Id-annotated *postId* field is mapped to the _id field,
the function findByPostId can simply get the comment by calling commentRepository.**findById()**.

## Exceptions
### MongoSocketOpenException
If anything in your setting is wrong, you will get these exceptions...

    No server chosen by com.mongodb.reactivestreams.client.internal.ClientSessionHelper...
    exception={com.mongodb.MongoSocketOpenException: Exception opening socket}...
    java.net.ConnectException: Connection refused: no further information...
    There was an unexpected error (type=Service Unavailable, status=503).

Have a look at some solutions below.

1. Is your MongoDB cluster connected correctly? Check the application.properties setting once again.

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
