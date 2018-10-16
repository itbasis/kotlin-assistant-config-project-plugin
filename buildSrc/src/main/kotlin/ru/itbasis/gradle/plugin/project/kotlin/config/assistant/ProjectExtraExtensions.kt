package ru.itbasis.gradle.plugin.project.kotlin.config.assistant

import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.embeddedKotlinVersion

fun ExtraPropertiesExtension.getOrDefault(
  propertyName: String,
  default: (() -> Any?)? = null
                                         ): Any? {
  if (has(propertyName)) {
    return get(propertyName)
  }

  return default?.invoke()
}

fun ExtraPropertiesExtension.kotlinVersion(): String {
  return getOrDefault("kotlin.version") { embeddedKotlinVersion }.toString()
}