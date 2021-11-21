FROM openjdk:11
COPY target/events-0.0.1-SNAPSHOT.jar events-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/events-0.0.1-SNAPSHOT.jar"]