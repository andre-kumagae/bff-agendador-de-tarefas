FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/bff-agendador-tarefas.jar

EXPOSE 8083

CMD ["java", "-jar", "/app/bff-agendador-tarefas.jar"]