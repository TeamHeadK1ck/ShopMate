buildscript {
    extra.apply {
        set("jetpack_navigation", "2.7.7")
    }

    dependencies{
        //плагин для передачи данных между экранами на базе jetpack navigation
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}