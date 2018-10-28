package ru.itbasis.gradle.plugin.project.kotlin.config.assistant

import org.gradle.api.Plugin
import org.gradle.api.plugins.PluginContainer
import kotlin.reflect.KClass

internal fun PluginContainer.hasPluginByName(plugin: KClass<out Plugin<*>>): Boolean {
  return firstOrNull { it.javaClass.canonicalName == plugin.java.canonicalName } != null
}
