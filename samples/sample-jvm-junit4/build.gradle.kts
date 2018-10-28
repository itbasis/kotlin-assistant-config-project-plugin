import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

apply<KotlinPlatformJvmPlugin>()

dependencies {
  "expectedBy"(project(":samples:sample-common"))
}
