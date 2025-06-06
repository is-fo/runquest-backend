FROM eclipse-temurin:17-jre-alpine

LABEL authors="isvac"

WORKDIR /app

COPY target/runquest-backend-1.0-SNAPSHOT.jar app.jar

ENV PORT=7070

CMD ["java", "-jar", "app.jar"]
