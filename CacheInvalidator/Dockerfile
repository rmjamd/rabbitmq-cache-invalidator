# Use a base image with OpenJDK 11 and Maven installed
FROM openjdk:11-jdk AS build

# Set the working directory in the container
WORKDIR /CacheInvalidator

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the Maven project
RUN apt-get update && apt-get install -y maven
RUN mvn clean package

# Create a new stage for the runtime image
FROM tomcat:8-jdk11-openjdk
# Remove the existing ROOT application
RUN rm -rf /usr/local/tomcat/webapps/CacheInvalidator.war

# Copy the WAR file from the build stage to the Tomcat webapps directory
COPY --from=build /CacheInvalidator/target/CacheInvalidator.war /usr/local/tomcat/webapps/CacheInvalidator.war
# Expose ports 8081, 8082, and 8083
EXPOSE 8081
EXPOSE 8082
EXPOSE 8083
# Start the Tomcat server
CMD ["catalina.sh", "run"]