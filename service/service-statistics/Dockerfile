FROM openjdk:11-jdk-alpine
VOLUME /tmp
COPY ./target/service-statistics-0.0.1-SNAPSHOT.jar sta.jar
ENTRYPOINT ["java","-jar","/sta.jar", "&"]