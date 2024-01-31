# the first stage of our build will use a maven 3.8.5 parent image
FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD

LABEL maintainer="Pavel Bakanov"
# copy the pom and src code to the container
COPY ./ ./

# package our application code without tests
#RUN mvn package -Dmaven.test.skip
RUN mvn clean package

# the second stage of our build will use open jdk 11
FROM amazoncorretto:17.0.7-alpine

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/spring_seminar5-0.0.1-SNAPSHOT.jar /spring_seminar5-0.0.1-SNAPSHOT.jar

# instruction for open port
EXPOSE 8181
# set the startup command to execute the jar
CMD ["java","-jar", "/spring_seminar5-0.0.1-SNAPSHOT.jar"]