# Etapa 1: construir el WAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: WildFly
FROM quay.io/wildfly/wildfly:latest

# Copiar el WAR con nombre correcto
COPY --from=build /app/target/amaris-back.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080
