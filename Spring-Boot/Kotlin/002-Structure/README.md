# Structure

## Files
Both .kt and .kts are Kotlin files including Kotlin source codes.
### .kt
This is the normal Kotlin source file being compiled by the Kotlin compiler.
### .kts
.kts is the script file, without needing an additional compilation.

    kotlinc -script FILE_NAME.kts
    
You can run it with this command. It is like a bash or python script, so it does not need main function in it.
