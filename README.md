[![](https://jitpack.io/v/hoonsa-lim/android-library-media.svg)](https://jitpack.io/#hoonsa-lim/android-library-media)

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
    val version = "latest release tag"
    implementation("com.github.hoonsa-lim:android-library-media:$version")
}

```
