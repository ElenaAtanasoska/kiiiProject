FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
LABEL authors="Elena Atanasoska 211209"
WORKDIR /app
COPY --from=build /app/target/*.jar kiiiProject-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/kiiiProject-0.0.1-SNAPSHOT.jar"]