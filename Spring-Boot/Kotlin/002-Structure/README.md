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

#### build.gradle(.kts)
**build.gradle** is the main configuration file for the project's build and dependencies. Let's take a look at the sample step by step. (I uploaded my file above.)


#### .kt
This is the normal Kotlin source file being compiled by the Kotlin compiler.
#### .kts
.kts is the script file, without needing an additional compilation.

    kotlinc -script FILE_NAME.kts
    
You can run it with this command. It is like a bash or python script, so it does not need main function in it.

.gradle.kts extension for the build scripts especially activates the Kotlin DSL.
