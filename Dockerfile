FROM openjdk:21-jdk-alpine
VOLUME /tmp
COPY target/task-management-system.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]