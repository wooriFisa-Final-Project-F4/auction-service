FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/auction-service-0.0.1-SNAPSHOT.jar /app/auction-service.jar
CMD ["java", "-jar", "/app/auction-service.jar"]