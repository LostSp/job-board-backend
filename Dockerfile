FROM mcr.microsoft.com/playwright/java:v1.40.0-jammy

WORKDIR /app

# Copy project files
COPY . /app

# Build the JAR inside the container
RUN ./mvnw clean package

# Copy the built JAR
CMD ["java", "-jar", "/app/target/JobBoard-1.0-SNAPSHOT.jar"]

