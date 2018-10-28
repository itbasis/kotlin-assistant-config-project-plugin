package ru.itbasis.gradle.plugin.project.kotlin.config.assistant

import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

const val LATEST_INTEGRATION = "latest.integration"
const val LATEST_RELEASE = "latest.release"

object DependencyVersions {
  val spotlessPluginGradle = DefaultExternalModuleDependency("com.diffplug.spotless", "spotless-plugin-gradle", LATEST_INTEGRATION)
  val ktLint = DefaultExternalModuleDependency("com.github.shyiko", "ktlint", LATEST_INTEGRATION)
  //
  val coroutinesCoreCommon = DefaultExternalModuleDependency("org.jetbrains.kotlinx", "kotlinx-coroutines-core-common", LATEST_INTEGRATION)
  val coroutinesCore = DefaultExternalModuleDependency("org.jetbrains.kotlinx", "kotlinx-coroutines-core", LATEST_INTEGRATION)
  //
  val kotlintestCore = DefaultExternalModuleDependency("io.kotlintest", "kotlintest-core", LATEST_INTEGRATION)
  val kotlintestAssertions = DefaultExternalModuleDependency("io.kotlintest", "kotlintest-assertions", LATEST_INTEGRATION)
  val kotlintestRunnerJvm = DefaultExternalModuleDependency("io.kotlintest", "kotlintest-runner-jvm", LATEST_INTEGRATION)
  val kotlintestRunnerJunit4 = DefaultExternalModuleDependency("io.kotlintest", "kotlintest-runner-junit4", LATEST_INTEGRATION)
  val kotlintestRunnerJunit5 = DefaultExternalModuleDependency("io.kotlintest", "kotlintest-runner-junit5", LATEST_INTEGRATION)
  //
  val mockkCommon = DefaultExternalModuleDependency("io.mockk", "mockk-common", LATEST_INTEGRATION)
  val mockk = DefaultExternalModuleDependency("io.mockk", "mockk", LATEST_INTEGRATION)
}
