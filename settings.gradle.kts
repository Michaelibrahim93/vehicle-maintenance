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
        mavenCentral()
    }
}

rootProject.name = "MaintenanceAlarm"
include(":app")
include(":core:resources")
include(":core:resources")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":vehicles:data")
include(":vehicles:domian")
include(":vehicles:test-utils")
include(":vehicles:presentation")
include(":profile:domain")
include(":profile:data")
include(":profile:presentation")
