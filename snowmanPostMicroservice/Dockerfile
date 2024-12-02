FROM eclipse-temurin:17-jdk-focal

RUN mkdir docker
WORKDIR ./docker

COPY .mvn/ .mvn
COPY pom.xml .
COPY mvnw .
RUN  ./mvnw dependency:go-offline
COPY src ./src


EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]


