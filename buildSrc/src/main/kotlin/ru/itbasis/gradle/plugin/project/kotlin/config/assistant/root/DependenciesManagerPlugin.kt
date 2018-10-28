@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.root

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.kotlinVersion

class DependenciesManagerPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val logger = project.logger

    val kotlinVersion: String = project.extra.kotlinVersion()
    logger.info("kotlinVersion: $kotlinVersion")

    var useGoogleRepo = false

    with(project) {
      configurations.all {
        resolutionStrategy {
          failOnVersionConflict()

          eachDependency {
            useGoogleRepo = useGoogleRepo || (requested.group.startsWith("com.android") || requested.group.startsWith("android"))

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

        if (useGoogleRepo) {
          add(google())
        }
      }
    }
  }
}
