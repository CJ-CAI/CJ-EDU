FROM java:8
VOLUME /tmp
COPY ./target/service-edu-0.0.1-SNAPSHOT.jar edu.jar
ENTRYPOINT ["java","-jar","/edu.jar", "&"]