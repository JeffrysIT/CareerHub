# Base image
FROM mysql:latest

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=careerhub
ENV MYSQL_USER=careerhub
ENV MYSQL_PASSWORD=careerhub

# Copy the Flyway configuration file to the container
COPY flyway.conf /flyway/conf/flyway.conf

# Copy the SQL migration scripts to the container
COPY db/migration /flyway/sql

# Run the Flyway migration
#RUN /flyway/flyway -configFiles=flyway.conf migrate

# Expose port 3306 for MySQL
EXPOSE 3306

FROM openjdk:8-jdk-alpine

COPY target/CareerHub.war /app.war

CMD ["java", "-jar", "/app.war"]