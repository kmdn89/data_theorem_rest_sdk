# data_theorem_rest_sdk


A simple HTTP networking library for Kotlin/Android.


## Features

- [x] Makes HTTP `GET`/`POST` requests
- [x] Makes Network Requests using [coroutines]
- [x] Build new Database when needed [Room]
- [x] Persist data into your database
- [x] History of all requests can be displayed onto the console


## SDK Implementation steps : 

1. Add the JitPack repository to your build file `settings.gradle` by including `maven{ url 'https://jitpack.io'}` in your repositories 

```groovy
repositories {
        google()
        mavenCentral()
        maven{ url 'https://jitpack.io'}
        jcenter() // Warning: this repository is going to shut down soon
    }
```

2. Add the dependency in your application `build.gradle` file


```groovy
dependencies {

  implementation 'com.github.kmdn89:data_theorem_rest_sdk:Tag'

}
```



## SDK Usage steps : 

1. Build a new database (if not already done or if needed)

        ```kotlin
        val db = buildNewDB(this)
        ```

2.a Make HTTP GET request

        ```kotlin
        getRequest(db)
        ```
2.b Make HTTP POST request

        ```kotlin
        postRequest(db)
        ```

3. Display History of request onto Console

        ```kotlin
        displayHistory(db)
        ```
