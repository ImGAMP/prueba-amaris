# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml y descarga dependencias para optimizar cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Construye el WAR sin tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final liviana con JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia el WAR empaquetado desde la etapa anterior
COPY --from=build /app/target/prueba-amaris.war app.war

# Expone el puerto del backend
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.war"]
