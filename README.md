Social Network Demo – Progetto Full Stack

Questo è un progetto **demo completo di social network**, pensato per mostrare le competenze full-stack su **Java Spring Boot e Angular**.

---

##  Funzionalità principali

-  Registrazione e login con autenticazione JWT (access token + refresh token + httpOnly)
-  Sistema di amicizie (invia, accetta, rifiuta)
-  Post pubblici con commenti e like
-  Notifiche in tempo reale tramite **WebSocket** e **JMS**
-  Chat privata in tempo reale con **WebSocket STOMP**
- Caricamento immagini su Cloudinary (con secure_url per post e profili amici).Per il profilo personale, invece, ho scelto di gestire l'immagine come array di byte, ricevuto dal backend.
-  Interfaccia moderna e responsive (Tailwind CSS)

### Tecnologie utilizzate

### Backend (Spring Boot):
- Java 18
- Spring Boot 3
- Spring Security + JWT + HttpOnly
- Spring WebSocket (STOMP)
- Spring JMS (ActiveMQ)
- JPA + Hibernate
- Cloudinary SDK
- Maven

 ### Frontend (Angular):
- Angular 14
- RxJS (BehaviorSubject)
- Angular Router & Services
- Tailwind
- Infinite Scroll & Lazy Loading

## Requisiti
- Java 17 o superiore
- Node.js + Angular CLI
- ActiveMQ broker JMS
- Connessione a Cloudinary (per le immagini)
- Database MySQL

## Lavori in corso
- Miglioramento notifiche personalizzate
- Validazione età
- Condivisione post


