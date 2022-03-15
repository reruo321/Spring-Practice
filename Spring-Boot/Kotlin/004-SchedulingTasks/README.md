# Scheduling Tasks
Let's move on to [the second guide](https://spring.io/guides/gs/scheduling-tasks/)! This time, we learn
how to build an application with scheduling tasks.

## Objective
We are going to embody a function to the application, which prints out the current time every five seconds.

## Creation
Before we create classes, we need to add a dependency to use awaitility library on Gradle.

(The guide says we should specify the version 3.1.2 for this project.)

	testImplementation("org.awaitility:awaitility:3.1.2")

Next, create ScheduledTasks.kt in your package. (for me com.example.demo)

    package com.example.demo

    import org.slf4j.LoggerFactory
    import org.springframework.scheduling.annotation.Scheduled
    import org.springframework.stereotype.Component
    import java.text.SimpleDateFormat
    import java.util.*

    @Component
    class ScheduledTasks {
        companion object {
            private val log = LoggerFactory.getLogger(ScheduledTasks::class.java)
            private val dateFormat = SimpleDateFormat("HH:mm:ss")
        }
        @Scheduled(fixedRate = 5000)
        fun reportCurrentTime(){
            log.info("The time is now {}", dateFormat.format(Date()))
        }
    }

Finally, let's make SchedulingTasksApplication.kt!

    package com.example.demo

    import org.springframework.boot.SpringApplication
    import org.springframework.boot.autoconfigure.SpringBootApplication
    import org.springframework.scheduling.annotation.EnableScheduling

    @SpringBootApplication
    @EnableScheduling
    class SchedulingTasksApplication

    fun main(args: Array<String>){
        SpringApplication.run(SchedulingTasksApplication::class.java)
    }

## Explanation
### @Component
**@Component** ([Detailed Guide](https://www.baeldung.com/spring-component-annotation)) allows Spring to automatically detect our custom beans. (We learned from Unit 003 that @RestController = @Controller + @ResponseBody + α!) It also acts a meta-annotation of @Controller, @Service, and @Repository which are the same and composed with it.

If an object is in a package outside of the project scope, or if it comes from a third-party source, we cannot annotate @Component to it.

Although both @Component and @Bean gather beans at runtime, @Component is added to classes, while @Bean is for methods so that Spring can store their results as beans. @Component is compatible with Spring's auto-detection, but you need manal class instantiation for @Bean. Since the latter decouples the instantiation of the bean from its class definition, it is useful when to make third-party classes into Spring beans or introduce bean logic.

### @Scheduled
**@Scheduled** annotation makes a task scheduled.

To enable support for scheduling, use **@EnableScheduling**. It will allow detection of @Scheduled annotations on any Spring-managed bean in the container.

	@Configuration
	@EnableScheduling
	class MyConfig{
		// ...@Bean...
	}
	
And to make MyTask.mywork() is called once every 5000ms,
	
	package com.mycon.tasks
	class MyTask{
		@Scheduled(fixedRate=5000)
		fun mywork(){
			// ...
		}
	}

If MyTask was annotated with @Component, you can set the configuration like this:

	@Configuration
	@EnableScheduling
	@ComponentScan(basePackages="com.mycon.tasks")
	public class MyConfig{
	}
	
We can also declare methods with @Scheduled directly within @Configuration classes.

	@Configuration
	@EnableScheduling
	class MyConfig{
	
		@Scheduled(fixedRate=5000)
		fun mywork(){
			// ...
		}
	}

#### Rules
To annotate a method with it
1) It should have a void return type. (Even if not, its return will be ignored.)
2) It should not expect any parameters.

* **Bean**: The key concept of the Spring framework - An object that is instantiated, assembled, and otherwise managed by a Spring IoC container.
* **IoC**(Inversion of Control): Principle in SE which transfers the control of objects or portions of a program to a container or framework, most in OOP. DI is one of the mechanisms we can achieve IoC.

## Test
After finishing the creation, let's run the application!

    ./gradlew bootRun

![004schedule](https://user-images.githubusercontent.com/48712088/158059073-3d8419f1-b3c0-4b34-9976-ac9dcf69cdf3.png)
