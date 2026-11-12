
# =========================
# ESTÁGIO 1 - BUILD
# =========================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia arquivos do Gradle
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle

# Permissão e download das dependências
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# Copia código-fonte
COPY src src

# Compila sem executar testes
RUN ./gradlew bootJar -x test --no-daemon

# =========================
# ESTÁGIO 2 - EXECUÇÃO
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/customer-ms-0.0.1-SNAPSHOT.jar customer-ms.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "customer-ms.jar"]
