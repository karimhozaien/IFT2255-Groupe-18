# Build
FROM maven:3.9.9-eclipse-temurin-23 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY data ./data
RUN mvn clean package -DskipTests

# Execute
FROM eclipse-temurin:23-jre
WORKDIR /app
RUN mkdir -p data
COPY --from=builder /app/data ./data
COPY --from=builder /app/target/untitled-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
