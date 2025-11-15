FROM eclipse-temurin:24-jdk

# Copy fat jar (whatever its name is)
COPY ./target/world_population.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "world_population.jar", "db:3306", "10000", "com.mysql.cj.jdbc.Driver"]
