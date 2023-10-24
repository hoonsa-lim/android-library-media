pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { url = uri("https://www.jitpack.io") }
        mavenCentral()
    }
}

rootProject.name = "android-library-media"
include(":app")
include(":metadata")
