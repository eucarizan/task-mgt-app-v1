#!/usr/bin/env bash

# Configuration
GROUP_ID="dev.nj"
ARTIFACT_ID="tms"
PROJECT_NAME="Task Management System"
DESCRIPTION="Spring Boot API for task management - Hyperskill Java Backend"
PACKAGE_NAME="dev.nj.tms"
SPRING_BOOT_VERSION="3.4.4"
JAVA_VERSION="17"

# Dependencies (comma-separated)
DEPENDENCIES="web,security,data-jpa,h2,lombok"

# Generate project
curl https://start.spring.io/starter.zip \
    -o "${ARTIFACT_ID}.zip" \
    -d "type=maven-project" \
    -d "language=java" \
    -d "groupId=${GROUP_ID}" \
    -d "artifactId=${ARTIFACT_ID}" \
    -d "name=${PROJECT_NAME}" \
    -d "description=${DESCRIPTION}" \
    -d "packageName=${PACKAGE_NAME}" \
    -d "javaVersion=${JAVA_VERSION}" \
    -d "dependencies=${DEPENDENCIES}" \
    -d "bootVersion=${SPRING_BOOT_VERSION}"

echo "Project generated: ${ARTIFACT_ID}.zip"
