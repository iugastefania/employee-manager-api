FROM openjdk:17
COPY target/project6-0.0.1-SNAPSHOT.jar app.jar
ARG db_host
ENV DB_HOST=$db_host
ENTRYPOINT ["java","-jar","/app.jar"]
