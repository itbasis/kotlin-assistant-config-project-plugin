package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.configure
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.ktLint
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.spotlessPluginGradle

class AutoConfigureSpotlessPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    with(project) {
      rootProject.buildscript {
        dependencies {
          classpath(spotlessPluginGradle)
        }
      }

      apply<SpotlessPlugin>()

      configurations.create("ktlint")

      dependencies.add("ktlint", ktLint)

      configure<SpotlessExtension> {
        encoding("UTF-8")

        kotlin {
          ktlint().userData(mapOf())
//          KotlinFormatExtension(KtLintStep.defaultVersion(), mapOf())

          target("src/**/*.kt")
        }

        kotlinGradle {
          target("*.gradle.kts", "settings.gradle.kts")

          ktlint()
        }
      }
    }
  }
}
