# Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy project files and build the JAR
COPY . /app
RUN mvn clean package -DskipTests

# Use a lightweight image to run the app
FROM mcr.microsoft.com/playwright/java:v1.40.0-jammy

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/JobBoard-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]


