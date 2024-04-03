FROM openjdk:21
ARG JAR_FILE=file-service/target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:", "-jar","/app.jar"]