FROM openjdk:17-ea-11-jdk-slim
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "/app.jar"]
