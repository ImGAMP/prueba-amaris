# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con JDK ligero
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/prueba-amaris.war app.war

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.war"]
