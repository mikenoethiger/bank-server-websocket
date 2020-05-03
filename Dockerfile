# Build jar
FROM gradle:jre14 AS build

WORKDIR /home/gradle
COPY . .
RUN gradle jar

# Create image
FROM openjdk:15-jdk-alpine

COPY --from=build /home/gradle/build/libs/bank-server-websocket.jar .

CMD ["java", "-jar", "bank-server-websocket.jar"]