FROM openjdk:8

MAINTAINER : Rajshekar Reddy<reddymh@gmail.com>

COPY target/spring-boot-demo.jar .

EXPOSE 8088

ENTRYPOINT ["java", "-jar" ,"spring-boot-demo.jar"]
