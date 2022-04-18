# Appendix
Here are some (maybe) useful tips for handling the environment!

## build.gradle.kts Settings

### Set the Source Directory
    sourceSets.main{
       java.srcDir("src/main/kotlin")
    }

### Set the Main Class
If you want to set the class named *exampleApplication* as the main, follow this syntax. Do not forget to add **Kt** to the class name!

    springBoot {
      mainClass.set("com.example.demo.exampleApplicationKt")
    }


### Exclude a Source File
    sourceSets.main{
      java.exclude("**/exampleApplication.kt")
    }

## Issues
### Not Recognized Dependencies?
If you added some new dependencies but no luck to import them, look at the right side of Intellij. Click Gradle > Refresh Icon. (Reload All Gradle Projects)

![000dependency](https://user-images.githubusercontent.com/48712088/159534365-3d41bb4b-9b97-4f3e-a671-77398507e60f.png)

### GradleWrapperMain

    Error: Could not find or load main class org.gradle.wrapper.GradleWrapperMain
    Caused by: java.lang.ClassNotFoundException: org.gradle.wrapper.GradleWrapperMain

After some modifications on the project directory, you suddenly encounter this error...

Solutions:

1. Check the directory of your Gradle files. It should be like this:
 
        root
        └ gradle
            └ wrapper
                └ gradle.wrapper.jar
                └ gradle.wrapper.properties
        └ build.gradle.kts
        └ gradlew
        └ gradlew.bat
        └ settings.gradle.kts
        
2. Check the contents of your Gradle files. In my case, all of the files are located correctly. But when I saw the size of **gradle-wrapper.jar**, OOPS! I noticed it was empty. Copy or download a new file to overwrite the corrupted one.
