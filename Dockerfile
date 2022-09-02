FROM openjdk:11-jre-slim
COPY /build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-Xmx400M","-jar","/app.jar"]
