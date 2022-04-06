# km_rest_sdk


A simple HTTPS networking library for Kotlin/Android.


## Features

- [x] Makes HTTPS `GET`/`POST` requests
- [x] Makes Network Requests using [coroutines]
- [x] Builds new Database when needed [Room]
- [x] Persists data into your database
- [x] History of all requests can be displayed onto the console


## SDK Implementation steps : 

1. Add the JitPack repository to your root build file `settings.gradle` by including `maven{ url 'https://jitpack.io'}` in your repositories block

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

      implementation 'com.github.kmdn89:km_rest_sdk:1.0.1'

}
```



## SDK Usage steps : 

1. Build a new database (if not already done or if needed)

```kotlin
val db = buildNewDB(this)
```


2. Make HTTPS GET request

```kotlin
getRequest(db)
```


2. Make HTTPS POST request

```kotlin
postRequest(db)
```


3. Display History of all requests onto Console

```kotlin
displayHistory(db)
```
