# Use a base image with Maven and Java 16 installed
FROM adoptopenjdk:17-jre-hotspot AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Set executable permissions for the Maven wrapper script
RUN chmod +x mvnw

# Build the application using Maven
RUN ./mvnw package -DskipTests

# Use a new base image with Java 16 installed
FROM adoptopenjdk:16-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/Homee-backend.jar /app

# Expose the port on which your application listens
EXPOSE 8080

# Set the entry point command to run your application when the container starts
CMD ["java", "-jar", "Homee-backend.jar"]
