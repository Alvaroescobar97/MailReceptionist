FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=microservicio/build/libs/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/application.jar"]