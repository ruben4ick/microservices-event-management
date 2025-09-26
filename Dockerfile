# 1. Build
FROM gradle:8.10.1-jdk21 AS builder

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x ./gradlew

RUN ./gradlew dependencies --no-daemon || return 0

COPY . .

RUN ./gradlew clean bootJar --no-daemon


# 2. Run
FROM openjdk:21-jdk AS runtime

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
