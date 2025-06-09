# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline  # Helps cache dependencies for faster builds
COPY . .
RUN mvn clean package -DskipTests
RUN ls -lh /app/target/

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar /app/book-details-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "book-details-0.0.1.jar"]
