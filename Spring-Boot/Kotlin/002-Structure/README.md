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
These are the files or folders included in the solution root. I referred to the demo project downloaded from Spring Initializr. Some may be different due to the environment such as the IDE version.
### .gradle
![002dotGradle](https://user-images.githubusercontent.com/48712088/154800008-2526f362-1c63-4cc5-8742-9e7b20fd5455.png)

A folder named **.gradle**, containing native information and caches (on plugins and dependencies), is used by our IDE, Intellij IDEA. At the first time the project is built, it downloads plugins and dependencies into the folder, and gets them from it when they are needed. the IDE This can be safely ignored by users, and we should set our version control to ignore it.
### .idea
![002idea](https://user-images.githubusercontent.com/48712088/154806596-e9e1c239-670e-46b3-be37-c1766f528ee1.png)

The **.idea** folder contains Intellij's project specific setting files.
### build
![002build](https://user-images.githubusercontent.com/48712088/154809898-88c2f798-95a6-4cce-af62-3edeea4455d5.png)

The outputs for the Gradle build will be generated in **build** folder.

### gradle
![002gradle](https://user-images.githubusercontent.com/48712088/154809900-fc307e70-5c60-4222-b52b-ce2969bf57da.png)

In **gradle**, there is a subfolder **wrapper**. It contains the setting files for the Gradle Wrapper, gradle-wrapper.jar & gradle-wrapper.properties. When creating a Gradle project, the IDE uses them.

### out
![002out](https://user-images.githubusercontent.com/48712088/154807341-8510e2f3-eb34-4249-ae2e-74fd43d4aef7.png)

The outputs for the Intellij build will be generated in **out** folder.

### src
![002src](https://user-images.githubusercontent.com/48712088/154807348-9265053e-1895-47a0-b159-c50a026f8d3d.png)

Here are some subfolders with "main", and "test" in the **src**. Kotlin source files live in the main folder, while the test source ones are in the test.

### Root
![002others](https://user-images.githubusercontent.com/48712088/154811118-2e6fde92-3bca-4a81-97c5-616e909c4778.png)

These are individual files in the solution root.

* .gitignore: This is not a build or source file, but for Github uploading. This will prevent to upload unnecessary things. It should be in the root to work properly.
* **build.gradle.kts**: The main configuration file for the project's build and dependencies.
* **gradlew**: A shell script for executing the build with the Gradle Wrapper.
* **gradlew.bat**: A Windows batch script for executing the build with the Gradle Wrapper.
* HELP.md: A markdown file for some help references.
* **settings.gradle.kts**: [Name settings script](https://docs.gradle.org/current/userguide/kotlin_dsl.html).

#### .kt
This is the normal Kotlin source file being compiled by the Kotlin compiler.
#### .kts
.kts is the script file, without needing an additional compilation.

    kotlinc -script FILE_NAME.kts
    
You can run it with this command. It is like a bash or python script, so it does not need main function in it.

.gradle.kts extension for the build scripts especially activates the Kotlin DSL.

### build.gradle(.kts)
**build.gradle** is the main configuration file for the project's build and dependencies, which is in the solution root. I uploaded my file, which is provided for you too as a default when using Spring Initializr. Let's take a look at the sample step by step.

    import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
    
First of all, you need to import KotlinCompile to build a Kotlin project with Gradle.

    plugins {
        id("org.springframework.boot") version "2.6.3"
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
        kotlin("jvm") version "1.6.10"
        kotlin("plugin.spring") version "1.6.10"
    }
    
We can apply some plugins Gradle provides, using the Gradle plugins DSL. You can find community plugins from [https://plugins.gradle.org/](the Gradle plugin portal). Give the fully qualified plugin id to declare dependencies of them.

        id("org.springframework.boot") version "2.6.3" 

You can also apply Kotlin plugins for Gradle, such as *jvm* or *plugin.spring* like the example.

        kotlin("jvm") version "1.6.10"

Now go on to the next codes... Here are some properties.

        group = "com.example"
        version = "0.0.1-SNAPSHOT"
        java.sourceCompatibility = JavaVersion.VERSION_17
        
* group: If the build produces a JAR file published to a repository, it will be under the specified group.
* version: The version of this project.
* java.sourceCompatibility: Define which language version of Java your source files should be treated as.

        repositories {
            mavenCentral()
        }
        
Repository! It might be familiar if you are a Git user. In Gradle, it is used to specify where to download the library for the dependencies, using a repositories {} block.

        dependencies {
            implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
            implementation("org.springframework.boot:spring-boot-starter-web")
            implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            developmentOnly("org.springframework.boot:spring-boot-devtools")
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
        
And you can choose the dependencies for your project. ([More information](https://docs.gradle.org/current/userguide/building_java_projects.html)) What you have selected from the Initializr would have been declared here.
* implementation: Used for compilation & runtime
* developmentOnly: Prevents devtools from being transitively applied to other modules that use your project
* testImplementation: Test equivalent of implementation

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }
        
This is the configuration of all Kotlin compilation tasks in the project. ([Documentation](https://kotlinlang.org/docs/gradle.html#compiler-options))
* -Xjsr305: The compiler flag for the JSR 305 checks, having one of the following options: -Xjsr305={strict|warn|ignore}. This is related to null-safety for the Spring API in Kotlin.

        tasks.withType<Test> {
            useJUnitPlatform()
        }
        
Finally the last thing! The block configures the test task registered in the project.
* useJUnitPlatform(): Uses JUnit Platform, one of the most popular unit-testing frameworks in the Java ecosystem.
