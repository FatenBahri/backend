FROM maven:3.9.4-eclipse-temurin-17 AS build
# Copier le code source dans le container
WORKDIR /app
COPY . .
# Compiler le projet et générer le .war
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
# Répertoire où l'app sera lancée
WORKDIR /app
COPY --from=build /app/target/*war app.war
# Exposer le port
EXPOSE 8089
# Commande d'exécution
ENTRYPOINT ["java", "-jar", "app.war"]
