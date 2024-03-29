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
If you are the first time to try MongoDB, go to [the website](https://www.mongodb.com/) to register your organization. (Individual user is of course OK.)
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

#### View
For instance to make a view of all comments in the repository, try this. It will allow us to READ them via the web.

    @GetMapping("/comment")
    fun getComments(): Flux<Comment>{
        return commentService.findAll()
    }

#### Main Class
Do not forget an annotation, **@EnableMongoRepositories**!

    @SpringBootApplication
    @EnableMongoRepositories
    class WebClientConsuming{...}

## Programming with Reactive Repository
### Publish-Subscribe Pattern
I used ReactiveMongoRepository, the reactive version of MongoRepository, in my project. If your project contains a reactive repository too,
you should be careful on the publish-subscribe pattern while programming.

**Publish-Subscribe**(Pub-Sub) is a messaging pattern allowing its messages to be broadcast asynchronously
to different parts of a system. A *publisher* broadcasts a message by pushing it to a *topic*.
Message topics transfer and push instantly the messages out to all *subscribers*.
The subscribers of a topic will receive every broadcast message, except filtered one by the user's custom policies for them.
The publishers do not need to know about the usage of the message, and the subscribers does not need to know about the publishers.

In this pattern, it is possible to create event-driven services, without message queue querying for messages.
It also allows developers to create different independent functions being performed in parallel, using the same message.

### Blocking
If you block the Webflux starting with a very few threads, it will bring a high risk of thread starvation.
No new threads spawn in your reactive applications, and their small thread pool will be blocked until they receive response from the database.

Wrapping a blocking call at least prevents those worst situations, but will lose a lot of benefits of reactive streams anyway...

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

If you successfully sent it, have a look at the web with READ operation. It will POST the new CREATEd comment.

### READ
**READ** operation literally READs the data from the database.

Oh, what is the problem when MongoDB says my data exists well, but does not show on the web?
If your READ looks like

    [{}, {}]

it will not be your or your application's fault, but actually the data is hidden. Private fields in the document (POJO) will make the view of them private, too.

#### Partial READ
You can also READ specific data in the database! For example, to make a comment with the postId value you put into the URL come out,

    // WebConsumingController.kt
    @GetMapping(value = ["/comment/post/{postId}"])
    fun getCommentByPostId(@PathVariable("postId") postId: Int): Mono<Comment>{
        return commentService.findByPostId(postId)
    }

    // CommentService.kt
    fun findByPostId(postId: Int): Mono<Comment>{
    return commentRepository.findById(postId)
        .switchIfEmpty(Mono.error(NotFoundException()))
    }

We can also query documents and READ them by typing their own mapped URL!

you can set *postId* as the path variable of http://localhost:8080/comment/post/#POST_ID# while using @GetMapping,
so that changing #POST_ID# part shows a READ result with your *postId* input.
For example, if you access to http://localhost:8080/comment/post/12345, you will see a comment whose *postId* is 12345.

Especially for _id, the interface **ReactiveCrudRepository** provides a useful method **findById()**, no need to implement our custom query.
Since we marked *postId* with @Id, we can easily define a READ service querying *postId*, by calling commentRepository.findById().

### Update
By using ReactiveCrudRepository.save(), we can either UPDATE a document, or create a new one if the entity's ID is not in the database.

    fun updateComment(newComment: Comment, postId: Int): Mono<Comment>{
    return commentRepository.save(newComment)
    }

Note that when trying to do a partial update in the reactive repository,
make sure that the code also contains *Subscription*, not just *Assembly* which only has description.
Nothing happens until you subscribe. Let's see my example.

    fun updateCommentOnlyBody(newBody: String, postId: Int): String{
        findByPostId(postId).flatMap{
            it.body = newBody
            commentRepository.save(it)
        }.subscribe()
        return "Post No. $postId is updated: \"$newBody\""
    }

In this function, findByPostId(postId).flatMap{...} returns a publisher and maps it,
acting as a recipe to change a field (body) of specific item emitted by Mono and then update it.
However, it does not execute the publisher. This is why subscribe() needs to be there.

To call update method in controller, you need @PutMapping.

### Delete
We can DELETE documents with specific ID, or found by specific queries, like other operations!
Simply use deleteById(), or create a custom delete query.

    // CommentService.kt
    fun deleteCommentsByUserName(name: String): String{
        commentRepository.deleteQueryByUserName(name).subscribe()
    return "Deleted $name's Posts"
    }

Especially for DELETE, you can even use the annotation, @DeleteQuery!

    // CommentRepository.kt
    @DeleteQuery("{'name': ?0}")
    fun deleteQueryByUserName(name: String): Flux<Comment>

## Test
You can also do some unit tests on both Spring Boot and MongoDB.

    @DataMongoTest(properties = ["spring.mongodb.embedded.version=4.0.2"])

While using Embedded MongoDB, make sure to notify its version for *properties* of @DataMongoTest,
or the test will not work.

## Security Configuration
**Spring Security** is a customizable authentication and access-control framework for Spring-based applications.

CSRF(Cross-Site Request Forgery) protection is one of the recommended security option,
which prevents an attacker to induce authenticated users so that they execute unwanted actions on the application.
However, if we are only creating a service that is used by non-browser client, it may be disabled.

    @EnableWebSecurity
    @Configuration
    internal class SecurityConfig : WebSecurityConfigurerAdapter() {
        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http.csrf().disable()
        }
    }

This example code shows the way to disable CSRF protection...

## Exceptions
### MongoSocketOpenException
If anything in your connection is wrong, you will get these exceptions...

    No server chosen by com.mongodb.reactivestreams.client.internal.ClientSessionHelper...
    exception={com.mongodb.MongoSocketOpenException: Exception opening socket}...
    java.net.ConnectException: Connection refused: no further information...
    There was an unexpected error (type=Service Unavailable, status=503).

Have a look at some solutions below.

1. Is your MongoDB cluster connected correctly? Check the status of the cluster from MongoDB Atlas. Wake it up if it is paused.
2. Check the application.properties setting once again.
3. Spring Boot has an auto-configuration feature, which triggers Mongo(Reactive)AutoConfiguration after detecting the Mongo driver. If you are not mind to use it, remove the exclusion from the annotation, @EnableAutoConfiguration.
4. In case you use Mongo(Reactive)AutoConfiguration, make sure it can connect to localhost:27017.
5. Check your own configuration again if you removed auto-configuration.

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
