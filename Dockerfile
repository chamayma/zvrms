# --- Stage 1: Build the application ---
FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Install native maven inside the build container
RUN apk add --no-cache maven

# Copy only the maven configuration file first to cache dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the rest of your source code and build the package
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# --- Stage 2: Run the application ---
FROM docker.io/library/eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy only the compiled jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]