# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS builder
LABEL org.opencontainers.image.source="https://github.com/DevOpsApproachFall2024/khanhtran_victoralago_devops_final"

# Set the working directory
WORKDIR /WeatherAppApi

# Copy Maven configuration and project files
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-jammy

# Set working directory in the container
WORKDIR /WeatherAppApi

# Copy the Spring Boot JAR from the builder stage
COPY --from=builder /WeatherAppApi/target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]