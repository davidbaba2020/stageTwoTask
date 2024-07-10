# Stage 1: Build the JAR file
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Build the final image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/stageTwoTask.jar ./stageTwoTask.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "stageTwoTask.jar"]