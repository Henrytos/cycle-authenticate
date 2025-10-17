FROM maven:3.9.11-amazoncorretto-21 AS builder

WORKDIR /app

COPY . .

ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring.devtools.restart.enabled=true"]