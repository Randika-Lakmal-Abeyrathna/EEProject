FROM adoptopenjdk/openjdk11:latest

EXPOSE 8300

ADD target/eureka-server.jar eureka-server

ENTRYPOINT ["java","-jar","eureka-server"]