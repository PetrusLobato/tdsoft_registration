FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-21-jdk maven

WORKDIR /app
COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/registration-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]