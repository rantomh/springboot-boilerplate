FROM maven:3.9.9-eclipse-temurin-23 AS build
 
WORKDIR /app
 
COPY pom.xml .
RUN mvn dependency:go-offline
 
COPY src ./src
 
RUN mvn clean package -DskipTests
 
FROM eclipse-temurin:23-jre-alpine
 
WORKDIR /app
 
COPY --from=build /app/target/springboot-boilerplate-0.0.1-SNAPSHOT.jar app.jar
COPY .env .env
 
EXPOSE 8080
 
ENV JAVA_TOOL_OPTIONS="-Xms1g -Xmx1g -XX:MaxRAM=2g -XX:+UseContainerSupport -Duser.timezone=UTC"
ENV SPRING_PROFILES_ACTIVE=dev
 
ENTRYPOINT ["java", "-jar", "app.jar"]