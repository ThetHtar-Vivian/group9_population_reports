FROM openjdk:17

WORKDIR /app

# Copy fat jar (whatever its name is)
COPY target/*-jar-with-dependencies.jar /app/app.jar

# Create a folder for report output
RUN mkdir -p /app/reports

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
