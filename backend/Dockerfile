FROM maven:3.8.4-openjdk-11-slim as build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 5002
ENTRYPOINT ["java", "-jar", "app.jar"] 