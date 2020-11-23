FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:8-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=MAVEN_BUILD /build/target/simplesurvey-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "simplesurvey-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080