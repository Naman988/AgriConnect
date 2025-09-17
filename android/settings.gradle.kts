// settings.gradle.kts
dependencyResolutionManagement {
    // Disallow repositories in project build scripts; centralize them here.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        // add other repos if you need (jitpack etc.)
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    // keep plugin versions centralized here if desired
    // (optional — you can also use version catalog or declare plugin versions per-plugin)
}

rootProject.name = "AgriConnect"
include(":app")
