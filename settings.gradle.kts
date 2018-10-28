import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.KotlinAssistantConfigProjectPlugin

rootProject.name = "kotlin-assistant-config-project-plugin"

apply<KotlinAssistantConfigProjectPlugin>()

arrayOf("common", "jvm-junit4", "jvm-junit5").forEach {
  include(":samples:sample-$it")
}
