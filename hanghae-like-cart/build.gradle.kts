import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.palantir.docker")

}
dependencies {

    implementation(kotlin("stdlib"))
}

docker {
    name = project.name + ":" + version
    setDockerfile(file("../Dockerfile"))
    files(tasks.bootJar.get().outputs.files)
    buildArgs(
        mapOf(
            "JAR_FILE" to tasks.bootJar.get().outputs.files.singleFile.name
        )
    )
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}
