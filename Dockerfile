FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir /app
ADD ./src /app/src
ADD ./pom.xml /app
ADD ./mvnw /app
ADD ./.mvn /app/.mvn
WORKDIR /app
RUN ./mvnw package
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/target/spring-kafka-example-1.0-SNAPSHOT.jar"]
# ENTRYPOINT ["sleep","10000"]
