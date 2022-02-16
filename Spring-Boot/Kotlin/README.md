# Spring Boot/Kotlin
For this project, I use Kotlin 1.6 for Spring Boot, with Gradle, JDK 17, in Intellij IDEA 2021.3.2 Commumity Edition.

## Settings
If you want to use Gradle JVM 17 for Kotlin, you should upgrade your IDE to the latest version, and then the Kotlin plugin to 1.6. Otherwise, you cannot access to JVM 17 option from your IDE at all.

Open build.gradle, and change

    jvmTarget = "YOUR_JVM_VERSION"
    
(for my case "17") in kotlinOptions.
