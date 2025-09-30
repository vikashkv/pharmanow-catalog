FROM openjdk:21-jre-slim
WORKDIR /app
LABEL authors="vikash.vishwakarma"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]