# Use an official OpenJDK runtime
FROM openjdk:21-jdk-slim AS build

# Set the working directory for application
WORKDIR /app

# Copy the Maven wrapper and project files
COPY . .

# Build the application (this generates target/*.jar)
RUN ./mvnw clean package -DskipTests

# Use a minimal JDK runtime for running the application
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the generated JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

