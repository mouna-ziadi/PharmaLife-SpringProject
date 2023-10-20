FROM openjdk:8
EXPOSE 8082
ADD target/spring-boot-cloudypi.jar spring-boot-cloudypi.jar
ENTRYPOINT ["java","-jar","/spring-boot-cloudypi.jar"]