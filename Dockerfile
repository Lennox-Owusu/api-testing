FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy only Maven-related files first (layer caching)
COPY pom.xml .
COPY src ./src

# Run tests at container runtime, not build time
CMD ["mvn", "clean", "test"]