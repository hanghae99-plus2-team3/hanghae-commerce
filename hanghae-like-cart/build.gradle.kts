dependencies {
    implementation("com.google.guava:guava:32.1.3-jre")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
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
