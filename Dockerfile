FROM openjdk:23
EXPOSE 8080
WORKDIR webapp
ADD target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]