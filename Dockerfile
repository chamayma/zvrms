# --- Stage 1: Build the application ---
FROM docker.io/library/eclipse-temurin:17-jdk-alpine AS build
    WORKDIR /app

# Copy maven files first to cache dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Fix line endings of mvnw script (in case you are building on Windows/Linux cross-environments)
RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw && chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy source code and build the package
COPY src ./src
RUN ./mvnw clean package -DskipTests

# --- Stage 2: Run the application ---
FROM docker.io/library/eclipse-temurin:17-jre-alpineWORKDIR /app

# Copy only the compiled jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]