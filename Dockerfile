# Use an official Maven image with OpenJDK 21 for building
FROM maven AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with OpenJDK 21
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app
# Copy the jar file from the build stage

COPY --from=build /app/target/doctor-service-1.0.0-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080
	
# Run the application
ENTRYPOINT ["java","-jar","app.jar"]