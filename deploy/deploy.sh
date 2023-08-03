#!/bin/bash

# Clean and package the project
.././mvnw clean package -f ../pom.xml

# Extract the dependencies
mkdir -p ../target/dependency && (cd ../target/dependency; jar -xf ../*.jar)

# Copy the internal SDK JAR file to the desired location
cp ~/.m2/repository/com/company/feature-toggles/company-feature-toggles-sdk/1.0-SNAPSHOT/company-feature-toggles-sdk-1.0-SNAPSHOT.jar ../target/dependency

# Build the Docker image
docker build -t movies:v0.0.1 ../ --progress plain
