FROM openjdk:11-jdk-alpine
VOLUME /tmp
COPY ./target/api_gateway-0.0.1-SNAPSHOT.jar api_gateway.jar
ENTRYPOINT ["java","-jar","/api_gateway.jar", "&"]