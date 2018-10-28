package ru.itbasis.gradle.plugin.project.kotlin.config.assistant

import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.embeddedKotlinVersion

fun ExtraPropertiesExtension.getOrDefault(
  propertyName: String,
  default: (() -> String?) = { "" }
                                         ): String {
  if (has(propertyName)) {
    return get(propertyName).toString()
  }

  return default.invoke().toString()
}

fun ExtraPropertiesExtension.getOrDefaultVersion(
  propertyName: String,
  default: (() -> String?) = { LATEST_INTEGRATION }
                                                ): String {
  return getOrDefault(propertyName.substringBeforeLast(".version") + ".version", default)
}

fun ExtraPropertiesExtension.kotlinVersion(): String {
  return getOrDefaultVersion("kotlin") { embeddedKotlinVersion }
}
