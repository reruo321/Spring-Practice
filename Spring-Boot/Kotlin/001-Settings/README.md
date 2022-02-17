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
When you open the project, you will see DemoApplicationTests in src > test > kotlin > com.example.demo. Give running it a try.

![001Init](https://user-images.githubusercontent.com/48712088/154465956-40c7c360-dce2-4309-932d-0d976cb2fdfd.png)

If you saw this ASCII art and some successful event logs, you may set your environment correctly.
It is okay to fail it at the first time, (I took a hour to solve it!) so take a look at tips below.

## IDE Settings
If you got a error message like "Unknown JVM target version: 17", you should adjust version of Gradle JVM.



1. Go File > Settings (Intellij IDEA > Preferences for macOS).
2. Open the tab on the left side. Build, Execution, Deployment > Build Tools > Gradle.
3. (Optional) Select "Intellij IDEA" for Build and run using/Run tests using. It will make building much faster, but might cause some problems on not supporting parts. 
4. Select Gradle JVM. You can download some simply on the IDE, provided by vendors, or from the Oracle website.
5. IF you still have a problem with downloaded JVM, (e.g. the option becomes red text, or deselected after closing the window) 

If you want to use Gradle JVM 17 for Kotlin, you should upgrade your IDE to the latest version to get Kotlin plugin version 1.6.
Otherwise, you cannot access to JVM 17 option from your IDE at all.

Open build.gradle, and change

    jvmTarget = "YOUR_JVM_VERSION"
    
(for my case "17") in kotlinOptions.
