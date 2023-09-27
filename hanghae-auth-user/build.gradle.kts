dependencies {

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
