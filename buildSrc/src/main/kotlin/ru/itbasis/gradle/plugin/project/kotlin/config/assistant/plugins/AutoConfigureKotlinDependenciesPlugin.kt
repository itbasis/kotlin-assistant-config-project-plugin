@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.tasks.testing.junit.JUnitTestFramework
import org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestFramework
import org.gradle.api.plugins.JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME
import org.gradle.api.plugins.JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformCommonPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.coroutinesCore
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.coroutinesCoreCommon
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.kotlintestAssertions
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.kotlintestCore
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.kotlintestRunnerJunit4
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.kotlintestRunnerJunit5
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.kotlintestRunnerJvm
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.mockk
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.DependencyVersions.mockkCommon
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.getOrDefault
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.hasPluginByName

class AutoConfigureKotlinDependenciesPlugin : Plugin<Project> {
  private var useCoroutines: Boolean = false

  override fun apply(project: Project) {
    println("apply dependencies from $project")
    useCoroutines = project.extra.getOrDefault("useCoroutines").toBoolean()
    //
    project.afterEvaluate {
      with(project.plugins) {
        //
        if (hasPluginByName(KotlinPlatformCommonPlugin::class)) {
          configureDependenciesCommon(project)
        }
        //
        if (hasPluginByName(KotlinPlatformJvmPlugin::class)) {
          configureDependenciesJvm(project)
        }
      }
    }
  }

  private fun configureDependenciesCommon(project: Project) {
    with(project) {
      logger.info("configure Common dependencies for $project")
      dependencies {
        listOfNotNull(kotlin("stdlib-common"), coroutinesCoreCommon.takeIf { useCoroutines }).forEach { dependencyNotation ->
          add(IMPLEMENTATION_CONFIGURATION_NAME, dependencyNotation)
        }

        listOf(
          kotlin("test-common"), kotlin("test-annotations-common"), kotlintestCore, kotlintestAssertions, mockkCommon
              ).forEach { dependencyNotation ->
          add(TEST_IMPLEMENTATION_CONFIGURATION_NAME, dependencyNotation)
        }
      }
    }
  }

  private fun configureDependenciesJvm(project: Project) {
    with(project) {
      logger.info("configure JVM dependencies for $project")
      dependencies {
        val javaVersion = JavaVersion.current()
        val kotlinStdlib = when {
          javaVersion.isJava8Compatible -> kotlin("stdlib-jdk8")
          javaVersion.isJava7Compatible -> kotlin("stdlib-jdk7")
          else                          -> kotlin("stdlib")
        }

        listOfNotNull(kotlinStdlib, coroutinesCore.takeIf { useCoroutines }).forEach { dependencyNotation ->
          add(IMPLEMENTATION_CONFIGURATION_NAME, dependencyNotation)
        }

        listOf(mockk).forEach { dependencyNotation ->
          add(TEST_IMPLEMENTATION_CONFIGURATION_NAME, dependencyNotation)
        }

        tasks.withType(Test::class).all {
          val kotlinTestRunner = when (testFramework) {
            is JUnitPlatformTestFramework -> kotlintestRunnerJunit5
            is JUnitTestFramework         -> kotlintestRunnerJunit4
            else                          -> kotlintestRunnerJvm
          }

          listOf(kotlinTestRunner).forEach { dependencyNotation ->
            add(TEST_IMPLEMENTATION_CONFIGURATION_NAME, dependencyNotation)
          }
        }

      }
    }
  }
}
