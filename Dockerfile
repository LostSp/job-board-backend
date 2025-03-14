# Use an official OpenJDK runtime (Change version if needed)
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
#COPY target/*.jar app.jar
COPY target/JobBoard-1.0-SNAPSHOT.jar app.jar

# Expose port 8080 (or the port your Spring Boot app runs on)
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "app.jar"]COPY target/JobBoard-1.0-SNAPSHOT.jar app.jar
