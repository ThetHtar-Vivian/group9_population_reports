FROM eclipse-temurin:24-jdk

# Copy fat jar (whatever its name is)
COPY ./target/PopulationTest.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "PopulationTest.jar", "db:3306", "10000", "com.mysql.cj.jdbc.Driver"]
