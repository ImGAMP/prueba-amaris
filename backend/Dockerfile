# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml y preparar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir el WAR ejecutable
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el WAR generado
COPY --from=build /app/target/prueba-amaris.war app.war

# Exponer el puerto
EXPOSE 8080

# Ejecutar el WAR
ENTRYPOINT ["java", "-jar", "app.war"]
