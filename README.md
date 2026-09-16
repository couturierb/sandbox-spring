# Spring Sandbox

Ce projet est un petit environnement de test basé sur Spring Boot pour explorer plusieurs mécanismes web et messaging :

- un endpoint de santé pour vérifier que le serveur démarre correctement ;
- un produit Kafka pour publier des messages sur un topic ;
- un consommateur Kafka pour écouter des événements ;
- une API SSE (Server-Sent Events) pour diffuser des notifications en temps réel.

Le but est de servir de sandbox de démonstration et de validation rapide avant d'intégrer des fonctionnalités plus complexes dans une application Spring.

## Prérequis

- Java 21
- Maven 3.9+
- Kafka accessible sur `localhost:9092`
    - `docker pull apache/kafka:4.3.1`
    - `docker run -p 9092:9092 apache/kafka:4.3.1`
- Un topic Kafka nommé `quickstart-events`
- Optionnel : les script fournis par Kafka (pour créer le topic par ex) : https://kafka.apache.org/21/getting-started/quickstart/
- Optionnel : un front-end sur `http://localhost:5173` si vous utilisez les endpoints SSE

## Structure principale

- `com.sandbox.controller.HealthController` : endpoint `/ok`
- `com.sandbox.kafka.KafkaController` : endpoint `/publish`
- `com.sandbox.kafka.KafkaProducer` : envoi de messages Kafka
- `com.sandbox.kafka.KafkaConsumer` : écoute des messages Kafka
- `com.sandbox.sse.SseController` : endpoints SSE (`/sse` et `/sse/kafka`)

## Démarrage

### Sans Kafka

```bash
mvn spring-boot:run
```

### Avec le profil Kafka

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=kafka
```

Le serveur démarre par défaut sur :

```text
http://localhost:8080
```

## Exemples de test

### Vérifier le serveur

```bash
curl http://localhost:8080/ok
```

### Publier un message Kafka

```bash
curl "http://localhost:8080/publish?message=bonjour"
```

### Consulter le flux SSE

```bash
curl -N http://localhost:8080/sse
```

## Remarques

- Le profil `kafka` active les composants Kafka.
- La configuration Kafka est définie dans `src/main/resources/application.yml`.
- Les endpoints SSE sont configurés pour accepter les requêtes depuis `http://localhost:5173`.
