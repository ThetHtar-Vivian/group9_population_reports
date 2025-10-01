FROM openjdk:17
COPY ./target/world_population-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "world_population-0.1.0.1-jar-with-dependencies.jar"]