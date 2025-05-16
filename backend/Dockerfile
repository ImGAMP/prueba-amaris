# Etapa 1: Build del WAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos necesarios y resolver dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y construir el WAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen con WildFly
FROM quay.io/wildfly/wildfly:latest

# Copiar WAR al directorio de despliegue
COPY --from=build /app/target/prueba-amaris.war /opt/jboss/wildfly/standalone/deployments/

# Opcional: abrir el puerto 8080 (puerto por defecto de WildFly)
EXPOSE 8080
