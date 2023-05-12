# Use a base image with Java 16 installed
FROM adoptopenjdk:16-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/Homee-backend.jar /app

# Expose the port on which your application listens
EXPOSE 8080

# Set the entry point command to run your application when the container starts
CMD ["java", "-jar", "Homee-backend.jar"]