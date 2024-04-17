pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 使用一些国内的镜像来提升下载速度。
        // 阿里云仓库
        maven("https://maven.aliyun.com/repository/public/")
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
rootProject.name = "compose-eyepetizer"
include(":app")
include(":compose-mvi")
