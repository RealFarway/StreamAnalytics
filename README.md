# StreamAnalytics

Stream Analytics Collector permette di collezionare metadati sugli stream in corso in
un’applicazione tramite l’API di Wowza Streaming Engine.

## Come utilizzarlo

È molto semplice:
* Aggiungere i file .jar (StreamAnalytics/lib) alla directory lib di Wowza Streaming Engine (Wowza Streaming Engine x.x.xx+x\lib).
* Impostare la _AuthenticationMethod_ proprietà nel _Server.xml_ (Wowza Streaming Engine x.x.xx+x\conf) a _none_. (al
momento non è stata sviluppata la parte di autenticazione per le chiamate API)
* Aggiungere il modulo all’applicazione
* Impostare le custom properties

### Custom Properties

A Stream Analytics Collector si possono impostare 3 custom properties:
* **baseUrl**: String -> rappresenta il base URL del server, default value è:
http://localhost:8087
* **serverName**: String -> il nome del server, default value è: defaultServerName
* **analyticsLogInterval**: Integer -> quanto spesso si devono salvare i dati,
rappresentato in secondi, default value è: 3

Le prime due sono le più importanti, se il baseUrl o il serverName sono sbagliati il modulo
non funzionerà.
