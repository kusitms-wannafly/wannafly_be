FROM openjdk:17-oracle

ADD . /home/spring
WORKDIR /home/spring

COPY build/libs/*.jar /home/spring/app.jar
COPY docker-compose.yml /home/spring/docker-compose.yml

#CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/home/spring/app.jar"]
