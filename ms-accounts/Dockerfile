# Build Stage
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom pai e o pom do módulo, para cache do Maven
COPY pom.xml ./
COPY ms-accounts/pom.xml ./ms-accounts/

# Copia o código fonte do módulo
COPY ms-accounts/src ./ms-accounts/src

# Build apenas o módulo (subprojeto)
RUN mvn -f ms-accounts/pom.xml clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o jar construído do estágio anterior
COPY --from=build /app/ms-accounts/target/ms-accounts-1.0-SNAPSHOT.jar app.jar

# Porta exposta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
