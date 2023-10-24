# android-library-media

## Modules
1. metadata : A collection of codes wrapped for ease of use with MediaMetadataRetriever.
2. app : sample app.

## How to use
```kotlin
//settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url = uri("https://www.jitpack.io") }
    }
}

//app/build.gradle
dependencies {
    ...
    implementation("com.github.hoonsa-lim:android-library-media:1.0.0")
}

```
