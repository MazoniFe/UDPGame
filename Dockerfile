
FROM openjdk:17-jdk-slim

WORKDIR /app


COPY target/*.jar app.jar

EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]
