dependencies {

    // 로그백 의존성
    implementation(group = "ca.pjer", name = "logback-awslogs-appender", version = "1.6.0")
    // 프로퍼티 제어 in xml
    implementation("org.codehaus.janino:janino:3.1.7")
}

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}

