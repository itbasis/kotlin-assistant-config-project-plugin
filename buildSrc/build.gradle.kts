@file:Suppress("SpellCheckingInspection", "UnstableApiUsage")

//import org.gradle.kotlin.dsl.plugins.dsl.KotlinDslPlugin
import com.gradle.publish.PluginBundleExtension
import com.gradle.publish.PublishPlugin
import org.gradle.internal.impldep.org.joda.time.DateTime
import org.gradle.internal.impldep.org.joda.time.format.DateTimeFormat
import org.gradle.internal.impldep.org.joda.time.format.DateTimeFormatter
import java.util.Date
import java.text.SimpleDateFormat

buildscript {
  val kotlinVersion: String = if (extra.has("kotlin.version")) extra["kotlin.version"]!!.toString() else embeddedKotlinVersion

  repositories {
    jcenter()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlinVersion))
    classpath("com.gradle.publish:plugin-publish-plugin:+")
  }
}

plugins {
  `kotlin-dsl`
}

apply {
  //  plugin<KotlinDslPlugin>()
  plugin<MavenPublishPlugin>()
  plugin<JavaGradlePluginPlugin>()
  plugin<PublishPlugin>()
}

repositories {
  jcenter()
  mavenCentral()
  gradlePluginPortal()
}

dependencies {
  "api"(kotlin("gradle-plugin"))
  "api"("com.gradle.publish:plugin-publish-plugin:+")

  "implementation"("com.diffplug.spotless:spotless-plugin-gradle:+")
  "implementation"("gradle.plugin.io.gitlab.arturbosch.detekt:detekt-gradle-plugin:+")
}

val isSnapshot = extra.get("snapshot")!!.toString().toBoolean()
val pluginVersion = SimpleDateFormat(if (isSnapshot) "yyyyMMdd" else "yyyyMMdd-HHmmss").format(Date()) + (if (isSnapshot) "-SNAPSHOT" else "")
logger.lifecycle("pluginVersion: $pluginVersion")
val url = "https://github.com/itbasis/kotlin-assistant-config-project-plugin"
description = "Gradle plugin for $url"

val sourcesJar by tasks.creating(Jar::class) {
  classifier = "sources"
  from(sourceSets["main"].allSource)
}

configure<GradlePluginDevelopmentExtension> {
  plugins {
    create(rootProject.name) {
      id = "${project.group}.${project.name}"
      implementationClass = "ru.itbasis.gradle.plugin.project.kotlin.config.assistant.KotlinAssistantConfigProjectPlugin"
      version = pluginVersion
    }
  }
}

configure<PluginBundleExtension> {
  website = url
  vcsUrl = url
  description = project.description
  plugins {
    with(getAt(project.name)) {
      id = "${project.group}.${project.name}"
      displayName = project.description
      version = pluginVersion
      tags = listOf("kotlin", "gradle", "plugins", "project", "configuration")
    }
  }
}

tasks.getByName("publish").dependsOn("publishPlugins")
