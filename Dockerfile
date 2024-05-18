# Stage 1: Build the Java application using Maven image
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image for running the application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/user-service-0.0.1-SNAPSHOT.jar .
CMD ["curl" ,"https://storage.googleapis.com/sliyp_website/starry-embassy-396610-b64f645a7598.json" ,">>" ,"service_key.json"]
CMD ["export","GOOGLE_APPLICATION_CREDENTIALS","=", "$(pwd)/service_key.json"]
CMD ["source","~/.bashrc"]
CMD ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]

