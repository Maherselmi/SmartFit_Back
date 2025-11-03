🏋️‍♂️ Fitness Center Platform
📘 Description du Projet

La plateforme Fitness Center est une application complète de gestion de centre de fitness intégrant une API REST Spring Boot, une API IA Flask et une interface Angular.
Elle permet la gestion des clients, plannings, abonnements, nutrition, ainsi que des fonctionnalités prédictives et intelligentes via des modèles d’intelligence artificielle.
🧩 Architecture Générale
───────────────────────────────────────────────────┐
│                COUCHE PRÉSENTATION (Angular)     │
│  • Interfaces adhérents, coachs, admins          │
│  • Dashboards et visualisations interactives     │
└──────────────────────┬───────────────────────────┘
│ API REST (JSON)
┌──────────────────────┴───────────────────────────┐
│                COUCHE MÉTIER (Spring Boot)       │
│  • Gestion clients, plannings, abonnements       │
│  • Sécurité et authentification (JWT)            │
│  • Communication avec API IA Flask               │
└──────────────────────┬───────────────────────────┘
│
┌──────────────┼──────────────┐
│              │              │
┌───────┴─────┐ ┌─────┴──────┐ ┌────┴─────────┐
│   MySQL     │ │ Flask API  │ │ Telegram Bot │
│  Database   │ │    IA      │ │     API      │
└─────────────┘ └────────────┘ └──────────────┘

⚙️ Technologies Utilisées
| Couche              | Technologie           | Description                               |
| ------------------- | --------------------- | ----------------------------------------- |
| **Frontend**        | Angular 17            | Interface utilisateur responsive          |
| **Backend**         | Spring Boot 3         | API REST principale                       |
| **IA**              | Flask + Scikit-learn  | API IA pour prédictions et analyses       |
| **Base de données** | MySQL                 | Stockage des données clients et plannings |
| **Sécurité**        | Spring Security + JWT | Authentification et autorisation          |
| **Documentation**   | Swagger UI            | Documentation interactive de l’API        |
| **Tests**           | Postman / JUnit       | Tests unitaires et d’intégration          |

📂 Structure du Projet
🧠 API IA (Flask)
flask-ia-api/
├── app.py
├── models/
│   ├── progression.py
│   ├── nutrition.py
│   ├── recommendation.py
│   └── performance.py
├── utils/
│   ├── preprocessor.py
│   └── validator.py
├── data/
│   └── trained_models/
└── requirements.txt

☕ API REST (Spring Boot)
fitness-api/
├── src/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── config/
├── application.properties
└── pom.xml
🚀 Installation et Lancement
1️⃣ Prérequis

Java 17+

Node.js 18+

Python 3.10+

MySQL en local

Maven et Angular CLI

2️⃣ Lancer la base de données

Créer la base :
CREATE DATABASE fitness_center;
3️⃣ Lancer le backend (Spring Boot)
cd fitness-api
mvn spring-boot:run

4️⃣ Lancer l’API IA (Flask)
cd flask-ia-api
pip install -r requirements.txt
python app.py

5️⃣ Lancer le frontend (Angular)
cd fitness-frontend
npm install
ng serve --port 4200
🌐 Endpoints Principaux
| Module      | Méthode | Endpoint                            | Description                        |
| ----------- | ------- | ----------------------------------- | ---------------------------------- |
| Clients     | `GET`   | `/clients`                          | Liste des clients                  |
| Clients     | `POST`  | `/clients`                          | Ajouter un client                  |
| Plannings   | `GET`   | `/plannings/search?date=YYYY-MM-DD` | Recherche par date                 |
| Nutrition   | `POST`  | `/nutrition/generate`               | Génère un menu personnalisé        |
| Abonnements | `GET`   | `/abonnements/recommendations`      | Recommandations IA                 |
| IA          | `POST`  | `/predict`                          | Prédiction de progression physique |

🧪 Tests API (Postman)

Une collection Postman est fournie :

Tests CRUD sur tous les modules

Vérifications des statuts 200 / 201 / 400 / 404 / 500

Variables d’environnement (baseUrl, iaUrl)

Scénarios de test IA avec données réelles

📁 Fichier : Fitness_Center_API_Collection.json
🤖 Fonctionnalités IA


| Modèle              | Tâche                     | Description                            |
| ------------------- | ------------------------- | -------------------------------------- |
| Régression Linéaire | Prédiction de progression | Estimation de la performance future    |
| Random Forest       | Recommandations           | Suggestions d’abonnement et programmes |
| K-Means             | Segmentation              | Catégorisation automatique des profils |
| LSTM                | Séries temporelles        | En développement – tendances physiques |

👨‍💻 Auteur

Mariem Achoury
📧 contact@example.com

💼 Projet académique - 2025
🎯 Objectif : Intégrer l’IA dans la gestion intelligente de centres de fitness.
