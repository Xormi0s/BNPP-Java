
### Stappen om het project te draaien

Volg deze stappen om zowel de back-end als de front-end applicatie op te starten.

------------------------------------------------------------------------------------------


1. Back-end: Spring Boot Applicatie

Stappen om de backend te draaien:

1. **Navigeer naar de `bookstore` map**:

    cd backend


2. **Commando om de applicatie te starten**:

    - **Met Maven**:
      Voer de volgende commando uit:

      mvn spring-boot:run

3. De Spring Boot applicatie zal nu draaien op de standaard poort **`8080`**. Je kunt de backend openen door naar `http://localhost:8080` te gaan.


------------------------------------------------------------------------------------------


Front-end: React Applicatie

Stappen om de frontend te draaien:

1. **Navigeer naar de `bookstore-frontend` map**:

    cd bookstore-frontend


2. **Installeer de benodigde Node.js dependencies**:
    Voer het volgende commando uit om alle dependencies te installeren die in de `package.json` staan:

    npm install


3. **Start de React applicatie**:
    Nadat de dependencies zijn geïnstalleerd, kun je de frontend applicatie starten door het volgende commando uit te voeren:

    npm start


4. De React applicatie zal nu draaien op de poort `3000`. Je kunt de frontend openen door naar `http://localhost:3000` te gaan.



-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


Het is belangrijk dat de Spring Boot applicatie draait onder `http://localhost:8080` en de React applicatie onder `http://localhost:3000`
Alle configuratie is hiermee opgezet.

