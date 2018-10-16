import org.gradle.api.tasks.wrapper.Wrapper.DistributionType

tasks.withType(Wrapper::class.java) {
  gradleVersion = "5.0-milestone-1"
  distributionType = DistributionType.ALL
}