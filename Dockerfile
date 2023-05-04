FROM openjdk:17-oracle

WORKDIR /home/spring

COPY build/libs/*.jar /home/spring/app.jar

CMD ["java", "-jar", "/home/spring/app.jar"]
