FROM openjdk:17-alpine
LABEL maintainer="dnhdang94"

# Add userInfo and group
RUN addgroup -g 7979 dnhdang94 && \
    adduser -D -u 7979 -G dnhdang94 dnhdang94

# PROJECT_PATH variable is passed from command "docker --build-arg"
ARG PROJECT_PATH=.

# Set work directory
WORKDIR /app
# Change owner and permissions
RUN chown -R dnhdang94:dnhdang94 /app
RUN chmod 755 /app

# Run as userInfo
USER c4imn

# Copy compiled jar from host to work directory inside Docker image
COPY $PROJECT_PATH/target/*.jar /app/app.jar

# Expose ports
EXPOSE 8080

# Execute service
ENTRYPOINT ["java", "-jar", "-XX:+ExitOnOutOfMemoryError", "-XX:+CrashOnOutOfMemoryError", "app.jar"]
