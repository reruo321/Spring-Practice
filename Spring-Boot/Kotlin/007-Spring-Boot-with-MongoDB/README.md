# Spring Boot with MongoDB
Spring Boot supports connections on various databases, and we will use **MongoDB** in this project.

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
