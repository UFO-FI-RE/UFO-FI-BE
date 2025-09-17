FROM openjdk:17

WORKDIR /app

COPY build/libs/*.jar ufo_fi_be.jar

EXPOSE 8080

CMD java -jar -Dspring.profiles.active=prod ufo_fi_be.jar