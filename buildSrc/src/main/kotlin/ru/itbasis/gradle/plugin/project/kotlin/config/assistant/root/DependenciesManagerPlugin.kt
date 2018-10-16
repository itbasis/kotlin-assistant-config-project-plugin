package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.root

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.kotlinVersion

class DependenciesManagerPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val kotlinVersion: String = project.extra.kotlinVersion()

    var useGoogleRepo = false

    with(project) {
      configurations.all {
        resolutionStrategy {
          failOnVersionConflict()

          eachDependency {
            useGoogleRepo = useGoogleRepo ||
              (requested.group.startsWith("com.android") || requested.group.startsWith("android"))

            when (requested.group) {
              "org.jetbrains.kotlin" -> {
                useVersion(kotlinVersion)
                when (requested.name) {
                  "kotlin-stdlib-jre7" -> this.useTarget("${requested.group}:kotlin-stdlib-jdk7:$kotlinVersion")
                  "kotlin-stdlib-jre8" -> this.useTarget("${requested.group}:kotlin-stdlib-jdk8:$kotlinVersion")
                }
              }

            }
          }

        }
      }

      repositories {
        jcenter()
        mavenCentral()

        if (useGoogleRepo) {
          add(google())
        }

        if (kotlinVersion.startsWith("1.3")) {
          add(maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/"))
        }
      }
    }
  }
}
