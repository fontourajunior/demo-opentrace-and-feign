FROM openjdk:11
VOLUME /tmp
ADD target/demo-opentrace-0.0.1-SNAPSHOT.jar demo-opentrace-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo-opentrace-0.0.1-SNAPSHOT.jar"]


#FROM openjdk:11
#
#COPY ./target/demo-opentrace-0.0.1-SNAPSHOT.jar /usr/app/
#
#WORKDIR /usr/app
#
#RUN sh -c 'touch demo-opentrace-0.0.1-SNAPSHOT.jar'
#
#ENTRYPOINT ["java","-jar","demo-opentrace-0.0.1-SNAPSHOT.jar"]