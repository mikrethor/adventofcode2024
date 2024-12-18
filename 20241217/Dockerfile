# Étape 1 : Utiliser une image de base légère avec JDK 17 (ou adapté à votre projet)
FROM azul/zulu-openjdk-alpine:17

# Étape 2 : Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3 : Copier le fichier JAR de l'application dans le conteneur
COPY target/adventofcode-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : Exposer le port sur lequel l'application écoute (par défaut 8080 pour Spring Boot)
EXPOSE 8080

# Étape 5 : Lancer l'application avec Java
CMD ["java", "-jar", "app.jar"]
