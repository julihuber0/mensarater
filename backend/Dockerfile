FROM docker.io/library/maven:3.9.11-eclipse-temurin-25-alpine AS build
EXPOSE 8080
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM docker.io/library/eclipse-temurin:25-jre-alpine
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=deploy
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]