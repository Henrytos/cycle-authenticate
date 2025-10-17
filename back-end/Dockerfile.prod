FROM maven:3.9.11-amazoncorretto-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM ubuntu/jre:21-24.04_stable

COPY --from=builder /app/target/cycle-authenticate-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]