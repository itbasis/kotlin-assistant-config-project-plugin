package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformCommonPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

class AutoConfigureKotlinDependenciesPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    with(project) {

      //
      plugins.withType<KotlinPlatformCommonPlugin> {
        dependencies {
          add(IMPLEMENTATION_CONFIGURATION_NAME, kotlin("stdlib-common"))
        }
      }
      //
      plugins.withType<KotlinPlatformJvmPlugin> {
        dependencies {
          val javaVersion = JavaVersion.current()
          if (javaVersion.isJava8Compatible) {
            add(IMPLEMENTATION_CONFIGURATION_NAME, kotlin("stdlib-jdk8"))
          } else if (javaVersion.isJava7Compatible) {
            add(IMPLEMENTATION_CONFIGURATION_NAME, kotlin("stdlib-jdk7"))
          } else {
            add(IMPLEMENTATION_CONFIGURATION_NAME, kotlin("stdlib"))
          }
        }
      }
    }
  }
}
