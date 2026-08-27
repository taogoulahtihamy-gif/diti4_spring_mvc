# DITI4 Spring MVC Final

Projet fusionné et finalisé avec :

- Spring Boot / Spring MVC
- PostgreSQL
- Spring Data JPA / Hibernate
- Flyway pour les migrations
- Swagger / OpenAPI
- Gestion globale des erreurs REST
- Validation Jakarta
- Thymeleaf
- Docker / Docker Compose

## Démarrage recommandé avec Docker

Prérequis : Docker et Docker Compose.

```bash
docker compose up --build
```

Application :

- http://localhost:8083
- Swagger UI : http://localhost:8083/swagger-ui.html
- OpenAPI JSON : http://localhost:8083/v3/api-docs

La base PostgreSQL Docker est exposée sur le port `5433`.

## Démarrage local sans Docker

Prérequis : Java 21, PostgreSQL et Maven (ou Maven Wrapper).

Par défaut l'application utilise :

- base : `diti4_pring_mvc`
- utilisateur : `postgres`
- mot de passe : `postgres`
- PostgreSQL : `localhost:5433`
- application : port `8082`

Les paramètres peuvent être remplacés avec :

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SERVER_PORT`

Puis :

```bash
./mvnw spring-boot:run
```

## API principale

### Produits

- `GET /api/produits`
- `POST /api/produits`
- `GET /api/produits/{id}`
- `PUT /api/produits/{id}`
- `DELETE /api/produits/{id}`

### Types de produits

- `GET /api/type-produits`
- `POST /api/type-produits`
- `GET /api/type-produits/{id}`
- `PUT /api/type-produits/{id}`
- `DELETE /api/type-produits/{id}`

## Gestion des erreurs

Les erreurs REST sont renvoyées en JSON avec notamment :

- horodatage
- statut HTTP
- type d'erreur
- message
- chemin appelé
- erreurs de validation éventuelles

Les cas 400, 404 et 500 sont centralisés dans le gestionnaire global d'exceptions.

## Migration de base de données

Flyway exécute les scripts placés dans :

```text
src/main/resources/db/migration/
```

Migration initiale :

```text
V1__creation_tables.sql
```

Hibernate utilise `ddl-auto=validate` : il ne crée pas automatiquement les tables.
