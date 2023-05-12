# Use a base image with Maven and Java 17 installed
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Set executable permissions for the Maven wrapper script
RUN chmod +x mvnw

# Build the application using Maven
RUN ./mvnw package -DskipTests

# Use a new base image with Java 17 installed
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/homee_backend-0.0.1-SNAPSHOT.jar /app/homee_backend.jar

# Expose the port on which your application listens
EXPOSE 8080

# Set the entry point command to run your application when the container starts
CMD ["java", "-jar", "homee_backend.jar"]
