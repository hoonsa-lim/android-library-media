[![](https://jitpack.io/v/hoonsa-lim/android-library-media.svg)](https://jitpack.io/#hoonsa-lim/android-library-media)

# android-library-media

## Modules
1. metadata : A collection of codes wrapped for ease of use with MediaMetadataRetriever.
2. media-picker : Wrapped the [photo picker](https://developer.android.com/training/data-storage/shared/photopicker) tool for ease of use.
3. app : sample app.

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
    val version = "latest release tag. ex) 1.0.0"
    val module = "module name. ex) media-picker"
    implementation("com.github.hoonsa-lim.android-library-media:$module:$version")
    
    //ex) implementation("com.github.hoonsa-lim.android-library-media:media-picker:1.0.0")
}

```
