# kotlin-assistant-config-project-plugin

check: `./jvmw ./gradlew clean check`

## Use

`settings.gradle.kts` :
```kotlin
import ru.itbasis.gradle.plugin.project.kotlin.config.assistant.KotlinAssistantConfigProjectPlugin

buildscript {
  repositories {
    gradlePluginPortal()
  }
  dependencies {
    classpath("ru.itbasis.gradle.plugins:kotlin-assistant-config-project-plugin:VERSION")
  }
}

apply<KotlinAssistantConfigProjectPlugin>()
```

[Module examples](./samples)
