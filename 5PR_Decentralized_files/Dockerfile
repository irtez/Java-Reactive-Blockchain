FROM amazoncorretto:20
WORKDIR /app
COPY target/PR5-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80
CMD ["java", "-Dspring.datasource.url=jdbc:postgresql://host.docker.internal:5432/postgres", "-jar", "app.jar"]