dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    implementation("org.springframework.security:spring-security-crypto:6.0.2")
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
