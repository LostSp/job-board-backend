FROM mcr.microsoft.com/playwright/java:v1.40.0-jammy

WORKDIR /app

COPY target/JobBoard-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
