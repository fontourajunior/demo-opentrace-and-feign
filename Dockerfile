FROM openjdk:11
VOLUME /tmp
ADD target/demo-opentrace-0.0.1-SNAPSHOT.jar demo-opentrace-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo-opentrace-0.0.1-SNAPSHOT.jar"]
