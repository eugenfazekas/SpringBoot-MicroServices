FROM adoptopenjdk:11-jre-hotspot
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 4800
