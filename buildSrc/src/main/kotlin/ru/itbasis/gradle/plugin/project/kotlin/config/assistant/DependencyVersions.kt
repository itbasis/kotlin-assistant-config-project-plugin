package ru.itbasis.gradle.plugin.project.kotlin.config.assistant

import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

object DependencyVersions {
  val spotlessPluginGradle =
    DefaultExternalModuleDependency("com.diffplug.spotless", "spotless-plugin-gradle", "3.15.0")
  val ktLint = DefaultExternalModuleDependency("com.github.shyiko", "ktlint", "0.29.0")
}