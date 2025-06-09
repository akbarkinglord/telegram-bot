FROM openjdk:11-jre-slim
COPY DictionaryBot-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]