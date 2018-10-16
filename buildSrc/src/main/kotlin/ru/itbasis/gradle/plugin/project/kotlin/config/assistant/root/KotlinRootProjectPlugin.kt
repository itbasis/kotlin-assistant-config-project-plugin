package ru.itbasis.gradle.plugin.project.kotlin.config.assistant.root

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.plugin
import org.gradle.kotlin.dsl.repositories
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.kotlinVersion
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.plugins.AutoConfigureKotlinDependenciesPlugin

class KotlinRootProjectPlugin : Plugin<Project> {
  override fun apply(rootProject: Project) {
    if (rootProject != rootProject.rootProject) {
      throw GradleException("The plugin can only be applied to the root project")
    }

    val kotlinVersion: String = rootProject.extra.kotlinVersion()

    rootProject.buildscript {
      repositories {
        jcenter()
        if (kotlinVersion.startsWith("1.3")) {
          add(maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/"))
        }
      }
      dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
      }
    }

    rootProject.allprojects {
      configureIntellijIdea(this)

      repositories {
        jcenter()
        mavenCentral()
      }

      apply {
        plugin<DependenciesManagerPlugin>()
//        plugin<AutoConfigureSpotlessPlugin>()
        plugin<AutoConfigureKotlinDependenciesPlugin>()
      }
    }
  }

  private fun configureIntellijIdea(project: Project) {
    project.allprojects {
      plugins.findPlugin(IdeaPlugin::class.java) ?: plugins.apply(IdeaPlugin::class.java)

      configure<IdeaModel> {
        module {
          isDownloadSources = false
          isDownloadJavadoc = false
        }
      }
    }
  }

  private fun configureSpotless(project: Project) {}
}
