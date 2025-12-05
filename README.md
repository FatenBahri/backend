###Backend Spring Boot Project

Ceci est le backend Spring Boot de l'application Full-Stack School Management.

##1. Comment lancer le projet localement
Prérequis

Java 17 ou supérieur

Maven

(Optionnel) Postman pour tester les APIs

Étapes

Ouvrir un terminal dans le dossier backend :
cd path/to/backend

Construire le projet :
mvn clean install

Lancer l’application :
mvn spring-boot:run

L’API backend sera disponible sur :
http://localhost:8089

(Optionnel) Tester les endpoints avec Postman ou un autre client API.

##2. Endpoints API
Authentification

POST /login → Se connecter en tant qu’admin et récupérer un JWT

POST /register → Créer un nouvel admin

Gestion des étudiants

GET /students?page=1 → Récupérer tous les étudiants avec pagination

GET /students/{id} → Récupérer un étudiant par ID

POST /students → Créer un nouvel étudiant

PUT /students/{id} → Mettre à jour un étudiant existant

DELETE /students/{id} → Supprimer un étudiant

Remarque : Tous les endpoints étudiants nécessitent un JWT dans le header Authorization :
Authorization: Bearer <token>

##3. Tests unitaires

StudentService : testé avec la vraie base de données pour vérifier l’intégration complète

AdminService : testé avec des mocks pour isoler la logique et éviter de modifier des données sensibles

Ceci montre la compréhension des deux approches de tests.

##4. Sécurité et validation

Mot de passe hashé avec BCrypt avant stockage

JWT protège tous les endpoints étudiants

Validation DTO avec @NotBlank, @Size, etc.

Limitation des tentatives de login (5 par minute max par username/IP)

##5. Fonctionnalités futures (non implémentées)

Export / import des étudiants en CSV ou Excel

Documentation Swagger / OpenAPI complète

Dockerisation complète (backend + frontend + base de données)

##6. Notes / conseils

Pour tester les données des étudiants, utiliser Postman si le frontend n’est pas fonctionnel

Inclure le JWT dans les headers pour tous les endpoints protégés

Exemple d’Authorization header :
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

##7. Structure du projet

##backend/
├─ controller → Gestion des requêtes HTTP
├─ service → Logique métier
├─ repository → Accès à la base de données
├─ entity → Entités JPA
├─ dto → Objets de transfert de données
├─ security → JWT, authentification et hashing
└─ test → Tests unitaires