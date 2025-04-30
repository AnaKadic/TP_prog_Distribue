# Déploiement d'un service avec Docker et Kubernetes  (TP Masterinfo)

Ce projet est une application web de location de voitures développée dans le cadre du TP de Programmation Distribuée (Master Info).  
L'application est construite avec **Spring Boot**, packagée avec **Docker**, et déployée sur **Minikube (Kubernetes)**.

---

## Technologies utilisées

- Java 17
- Spring Boot 3.x
- Gradle
- Docker
- Kubernetes (Minikube)
- kubectl

---
## Fonctionnalités

- `GET /cars` → Liste toutes les voitures disponibles
- `PUT /cars/{plateNumber}` → Loue une voiture sur une période donnée
- `GET /cars/{plateNumber}/reservations` → Affiche les réservations pour une voiture
- `GET /cars/{plateNumber}/available?begin=XX&end=XX` → Vérifie si une voiture est dispo sur une période

---
## Build du projet Java

```bash
./gradlew build
```

Le fichier `.jar` est généré dans le dossier `build/libs`.

---
## Docker

### 1. Build de l’image Docker :

```bash
docker build -t anakadic/rental-app:1 .
```

### 2. Lancer le conteneur localement :

```bash
docker run -p 4000:8080 anakadic/rental-app:1
```

Test local :

```bash
curl http://localhost:4000/cars
```

---

##  Publication de l’image sur Docker Hub

```bash
docker login
docker push anakadic/rental-app:1
```

---

## Déploiement Kubernetes avec Minikube

### 1. Démarrage de Minikube

```bash
minikube start --driver=docker
```

### 2. Déploiement de l’app

```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### 3. Obtenir l’URL publique

```bash
minikube service rental-app --url
```

Puis tester :

```bash
curl http://<minikube-ip>:<nodePort>/cars
```

---

##  Scaler le service

```bash
kubectl scale deployment rental-app --replicas=2
```

---

##  Tester l'application

### Lister les voitures :
```bash
curl http://<minikube-ip>:<nodePort>/cars
```

### Louer une voiture :
```bash
curl -X PUT -H "Content-Type: application/json" \
  -d '{"begin" : "12/10/2023", "end" : "12/10/2023"}' \
  "http://<minikube-ip>:<nodePort>/cars/Ferrari?rent=true"
```

### Voir les réservations :
```bash
curl http://<minikube-ip>:<nodePort>/cars/Ferrari/reservations
```

### Vérifier disponibilité :
```bash
curl "http://<minikube-ip>:<nodePort>/cars/Ferrari/available?begin=12/10/2023&end=12/10/2023"
```

---

##  Pull Request et branche

```bash
git checkout -b newcarservice
git add .
git commit -m "Ajout des routes /reservations et /available"
git push origin newcarservice
```

Crée ensuite une Pull Request depuis GitHub.

---

##  Auteure

- Ana Kadic — [GitHub](https://github.com/AnaKadic)
