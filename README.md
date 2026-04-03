# Hello Spring Boot Docker Example

Ce projet est un exemple minimal d'application Spring Boot exposant un endpoint REST, prêt à être containerisé avec Docker.

## Lancer en local

```sh
gradle bootRun
```

## Builder le JAR

```sh
gradle build
```

Le JAR sera généré dans `build/libs/Hello-0.0.1-SNAPSHOT.jar`.

## Construire l'image Docker

```sh
docker build -t hello .
```

## Lancer le conteneur

```sh
docker run -p 4000:8080 -t hello
```

Accéder à l'application sur http://localhost:4000/
