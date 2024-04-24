FROM openjdk:17-ea-11-jdk-slim

RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/* \

# Gradle 설치
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://services.gradle.org/distributions/gradle-8.7-bin.zip && \
    unzip -d /opt/gradle gradle-8.7-bin.zip && \
    rm gradle-8.7-bin.zip
ENV PATH="/opt/gradle/gradle-8.7/bin:${PATH}"

WORKDIR /app
COPY . .

COPY src/main/resources/application.yml /app/src/main/resources/

RUN gradle clean build

RUN cp build/libs/member-service-0.0.1-SNAPSHOT.jar member-service.jar

ENTRYPOINT ["java", "-jar", "member-service.jar"]