@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

apply<KotlinPlatformJvmPlugin>()

dependencies {
  "expectedBy"(project(":samples:sample-common"))
}

tasks.withType(Test::class.java).all {
  useJUnitPlatform {}
}
