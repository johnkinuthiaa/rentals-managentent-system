FROM ubuntu:latest
LABEL authors="slippery"
WORKDIR /app


COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline


ENTRYPOINT ["top", "-b"]

COPY .src ./src
CMD["./mvnw","spring-boot:run"]