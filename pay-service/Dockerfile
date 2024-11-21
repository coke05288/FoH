# 1단계: 서비스 빌드 단계
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

RUN ./gradlew clean build -x test --stacktrace --no-daemon || return 0

COPY src/ src/

RUN ./gradlew bootJar -x test --no-daemon

# 2단계: 서비스 실행 단계
FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV SPRING_PROFILE="prod"
ENV EUREKA_INSTANCE_HOSTNAME="pay-service"
ENV EUREKA_SERVER_HOSTNAME="hzipyb-foh-alb-553236548.ap-northeast-2.elb.amazonaws.com"
ENV EUREKA_SERVER_PORT=7000

EXPOSE 8103

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "app.jar"]