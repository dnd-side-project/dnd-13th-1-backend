# ✅ 1단계: 빌드용 (Gradle + Java 21)
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Gradle 캐시를 위해 필요한 파일 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build -x test --no-daemon || true

# 전체 소스 복사 후 실제 빌드
COPY . .
RUN gradle clean build -x test -PdockerBuild --no-daemon

# ✅ 2단계: 런타임용 (경량 JDK 21)
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# JAR 파일 복사
COPY --from=builder /app/build/libs/app.jar app.jar

# HTTP 포트 오픈
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
