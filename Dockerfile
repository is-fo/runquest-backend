FROM maven:3.9.6-eclipse-temurin-17 AS builder
LABEL authors="isvac"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

COPY target/runquest-backend-1.0-SNAPSHOT.jar app.jar
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/runquest-backend-1.0-SNAPSHOT.jar app.jar
ENV PORT=7070

CMD ["java", "-jar", "app.jar"]
