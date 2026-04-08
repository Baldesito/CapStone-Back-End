# ✈️ Baldesito-Fly API (Backend)

Il cuore logico della piattaforma Baldesito-Fly. Una REST API robusta costruita con Spring Boot per gestire la ricerca voli, l'autenticazione degli utenti e il sistema di prenotazioni.

## 🚀 Tecnologie Utilizzate
- **Java 21** & **Spring Boot 3.4.2**
- **Spring Security & JWT**: Gestione sicura dell'autenticazione e autorizzazione.
- **Spring Data JPA**: Interfacciamento con il database relazionale.
- **PostgreSQL**: Database di produzione ospitato su Render.
- **Amadeus SDK**: Integrazione con API esterne per dati di volo in tempo reale.
- **Docker**: Containerizzazione per il deployment.

## ✨ Funzionalità Principali
- **Autenticazione**: Registrazione e Login con password criptate (BCrypt) e rilascio di token JWT.
- **Ricerca Voli**: Integrazione con Amadeus API per voli globali.
- **Booking System**: Gestione delle prenotazioni utente con persistenza su DB.
- **Security**: Protezione delle rotte sensibili e gestione dei ruoli (USER/ADMIN).

## 🛠️ Installazione Locale
1. Clona il repository.
2. Configura le variabili d'ambiente (`application.properties`) con le tue API Key di Amadeus e credenziali DB.
3. Esegui `./mvnw spring-boot:run`.

## 🌐 Deployment
L'applicazione è containerizzata tramite Docker e distribuita su **Render**.
