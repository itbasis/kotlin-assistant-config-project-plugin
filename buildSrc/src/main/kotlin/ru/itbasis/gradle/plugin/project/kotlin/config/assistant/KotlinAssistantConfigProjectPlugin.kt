package ru.itbasis.gradle.plugin.project.kotlin.config.assistant

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.apply
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.root.KotlinRootProjectPlugin

class KotlinAssistantConfigProjectPlugin : Plugin<Settings> {
  override fun apply(settings: Settings) {
    settings.gradle.beforeProject {
      rootProject.apply<KotlinRootProjectPlugin>()
    }
    settings.gradle.projectsLoaded {
//			configureBuildScriptDependencies(rootProject)

//			configurePlugins(rootProject)
    }
  }

//	private fun configurePlugins(rootProject: Project) {
//		rootProject.allprojects {
//			plugins.apply(KotlinProjectPlugin::class.java)
//		}
//	}

//	private fun configureBuildScriptDependencies(rootProject: Project) {
//		data class DependencyData(
//			val dependencyGroup: String, val dependencyName: String, val dependencyVersion: String
//		)
//		rootProject.buildscript {
//			arrayOf(
//				DependencyData(dependencyGroup = "org.jetbrains.kotlin",
//					dependencyName = "kotlin-gradle-plugin",
//					dependencyVersion = rootProject.dependencyVersion("kotlin.version") { embeddedKotlinVersion }),
//				DependencyData(
//					dependencyGroup = "gradle.plugin.com.dorongold.plugins",
//					dependencyName = "task-tree",
//					dependencyVersion = rootProject.dependencyVersion("task-tree.plugin.version")
//				),
//				DependencyData(
//					dependencyGroup = "gradle.plugin.org.jlleitschuh.gradle",
//					dependencyName = "ktlint-gradle",
//					dependencyVersion = rootProject.dependencyVersion("Ktlint.plugin.version")
//				),
//				DependencyData(
//					dependencyGroup = "gradle.plugin.io.gitlab.arturbosch.detekt",
//					dependencyName = "detekt-gradle-plugin",
//					dependencyVersion = rootProject.dependencyVersion("detekt.plugin.version")
////				), DependencyData(
////					"org.jetbrains.kotlin",
////					"kotlin-native-gradle-plugin",
////					dependencyVersion = rootProject.dependencyVersion("kotlinNativeVersion")
//				)
//			).map { (dependencyGroup, dependencyName, dependencyVersion) ->
//				return@map "$dependencyGroup:$dependencyName:$dependencyVersion"
//			}.forEach { dependencyNotation ->
//				rootProject.logger.info("buildscript use dependency: $dependencyNotation")
//				dependencies.classpath(dependencyNotation)
//			}
//		}

//	}
}
