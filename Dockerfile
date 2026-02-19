FROM gradle:8.5-jdk21-alpine AS builder
WORKDIR /app
COPY gradle gradle
COPY src src
COPY build.gradle .
COPY gradlew .
COPY gradlew.bat .
COPY settings.gradle .
RUN gradle bootJar -x test --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]