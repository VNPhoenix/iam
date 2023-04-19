FROM dnhdang94/openjdk:8-jre-alpine
LABEL maintainer="dnhdang94"

# Add user and group
RUN addgroup -g 7979 dnhdang94 && \
    adduser -D -u 7979 -G dnhdang94 dnhdang94

# PROJECT_PATH variable is passed from command "docker --build-arg"
ARG PROJECT_PATH=.

# Set work directory
WORKDIR /app
# Change owner and permissions
RUN chown -R dnhdang94:dnhdang94 /app
RUN chmod 755 /app

# Run as user
USER c4imn

# Copy compiled jar from host to work directory inside Docker image
COPY $PROJECT_PATH/out/identity-access-management*.jar /app/identity-access-management.jar

# Expose ports
EXPOSE 8080

# Execute service
ENTRYPOINT ["java", "-jar", "-XX:+ExitOnOutOfMemoryError", "-XX:+CrashOnOutOfMemoryError", "identity-access-management.jar"]
