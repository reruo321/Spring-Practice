# Settings
## Project Initialization
Since the free Community Edition of Intellij IDEA does not provide a default project initializer for spring boot, you should create it manually.
Go to [this website](https://start.spring.io/), check options, and generate a project file.

My first setting was:

- Project: Gradle Project
- Language: Kotlin
- Spring Boot: 2.6.3
- Project Metadata:

    (Default)
    
    Group - com.example
    
    Artifact - demo
    
    Name - demo
    
    Description - Demo project for Spring Boot
    
    Package name - com.example.demo
    
- Packaging: Jar
- Java: 17
- Dependencies: Spring Boot DevTools, Spring Web, Thymeleaf

After downloading it, you can open it with your IDE by pointing to the project ('demo' for me) folder, on File > Open.

## Test
When you open the downloaded project, you will see DemoApplicationTests in src > test > kotlin > com.example.demo. Give running it a try.

![001Init](https://user-images.githubusercontent.com/48712088/154465956-40c7c360-dce2-4309-932d-0d976cb2fdfd.png)

If you saw this ASCII art and some successful event logs, you may set your environment correctly.
It is okay to fail it at the first time, (I took a hour to solve it!) so take a look at tips below.

## IDE Settings
If you got an error message like "Unknown JVM target version: 17", you should adjust version of Gradle JVM. You can see the current options from **build.gradle.kts**.

※ **Update the IDE before going to start.** The old version of IDE would cause some troubles or even give no options while setting the latest versions of plugins or SDKs. ([Guideline for Intellij IDEA](https://www.jetbrains.com/help/idea/update.html))

### File > Project Structure
1. Go Project Settings > Project, and check your SDK version. You can download some simply on the IDE, provided by vendors, or from the official Oracle website.
2. (Optional) For Language level, select the test version for the latest one.
3. Move on to Project Settings > Modules, and select your project. Click Dependencies tab, and change the Module SDK.
4. Select Kotlin for your sources. Adjust the Language level and API version.

### File > Settings
1. (Optional for macOS: open Intellij IDEA > Preferences.)
2. Open the tab on the left side. Build, Execution, Deployment > Build Tools > Gradle.
3. (Optional) Select "Intellij IDEA" for Build and run using/Run tests using. It will make building much faster, but might cause some problems on not supporting parts. 
4. Select Gradle JVM.
