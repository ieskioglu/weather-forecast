# Use an official OpenJDK image as the base image
FROM openjdk:21

# Copy the built jar file from the build context
COPY target/weather-forecast-1.0.0.jar weather-forecast.jar

EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "weather-forecast.jar"]