import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.KotlinAssistantConfigProjectPlugin

rootProject.name = "kotlin-assistant-config-project-plugin"

apply<KotlinAssistantConfigProjectPlugin>()

arrayOf("common", "jvm").forEach {
  include(":samples:sample-$it")
}
