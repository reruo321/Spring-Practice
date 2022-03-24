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

