@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.root

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.findPlugin
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.plugin
import org.gradle.kotlin.dsl.repositories
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.getOrDefaultVersion
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.kotlinVersion
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.plugins.AutoConfigureKotlinDependenciesPlugin

class KotlinRootProjectPlugin : Plugin<Project> {
  override fun apply(rootProject: Project) {
    if (rootProject != rootProject.rootProject) {
      throw GradleException("The plugin can only be applied to the root project")
    }

    with(rootProject) {
      logger.lifecycle("rootProject: $rootProject")

      val kotlinVersion: String = rootProject.extra.kotlinVersion()
      val detektVersion: String = rootProject.extra.getOrDefaultVersion("detekt")
      val shadowJarVersion: String = rootProject.extra.getOrDefaultVersion("shadowJar")

      buildscript {
        repositories {
          jcenter()
          gradlePluginPortal()
        }
        dependencies {
          classpath(kotlin("gradle-plugin", kotlinVersion))
          classpath("gradle.plugin.io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
          classpath("com.github.jengelman.gradle.plugins:shadow:$shadowJarVersion")
        }
      }

      allprojects {
        configureIntellijIdea(this)

        apply {
          plugin<DetektPlugin>()
          plugin<DependenciesManagerPlugin>()
          // TODO https://github.com/shyiko/ktlint/pull/294
          // plugin<AutoConfigureSpotlessPlugin>()
          plugin<AutoConfigureKotlinDependenciesPlugin>()
        }

        afterEvaluate {
          tasks.findByName("check")?.dependsOn("detekt")

          if (plugins.hasPlugin(JavaPlugin::class.java)) {
            plugins.apply(ShadowPlugin::class.java)
          }
        }
      }
    }
  }

  private fun configureIntellijIdea(project: Project) {
    project.allprojects {
      plugins.findPlugin(IdeaPlugin::class)
      ?: plugins.apply(IdeaPlugin::class.java)

      configure<IdeaModel> {
        module {
          isDownloadSources = false
          isDownloadJavadoc = false
        }
      }
    }
  }
}
