# Prodotto
Stilo

# Descrizione del prodotto
La soluzione STILO è rivolta alla gestione dematerializzata degli Atti Amministrativi attraverso  flussi  che coprono le fasi di composizione, controllo di regolarità contabile, adozione, archiviazione e predisposizione alla pubblicazione degli atti prodotti dall’Ente, in una logica di processo che parte e si chiude nel fascicolo, in cui viene raccolta tutta la documentazione relativa al procedimento di cui l’Atto è parte determinante. 
Il sistema è in grado di gestire invio di PEO e PEC, interne e esterne all'Ente,  nonchè notifiche dei workflow e comunicazioni di servizio, oltre che report. Permette l'apposizione di firme digitali per l'adozione degli Atti e marche temporali per garantirne la validità nel tempo. E' inoltre predisposto per la
registrazione delle presenze in aula e delle votazioni che portano all'adozione degli Atti collegiali. 

# Prerequisiti di sistema
Java Runtime: JSE8, Java Platform Standard Edition 8
PL/SQL: Linguaggio procedurale di Oracle RDBMS 11 o superiori
JDK e JRE Compatibility level: JEE8, Java Platform Enterprise Edition 8.0

Librerie/componenti open-source o di terze parti utilizzati
Activiti Alfresco BPM: motore BPMN 2.0 di gestione dei processi. La modellazione grafica – web – dei processi avviene tramite il suo componente Acitiviti Modeler
OpenOffice:	per la conversione dei file delle suite Ms/Open/Libre Office in pdf come pure dei file text/plain e rtf

# Versioning
Il codice sorgente viene gestito tramite maven e versionato su git.
Versione 2.0.0

# Copyrights
 (C) Copyright Regione Piemonte – 2023
 (C) Copyright Comune di Torino – 2023
 (C) Copyright Città Metropolitana di Torino – 2023

# License
Stilo è rilasciato sotto licenza “AGPL 3 or later”, con una “GPL linking exception”

“GPL linking exception” 
Stilo funziona con molte librerie di terze parti, tutte open source. Tali librerie sono rilasciate con diverse licenze open source, non sempre compatibili con la AGPL 3. Pertanto, come eccezione speciale, il titolare del copyright del software prevede un'eccezione specifica nei confronti di queste librerie (elencato nei file BOM.TXT) e ti dà il permesso di collegare questo software con queste librerie e anche di produrre un eseguibile, indipendentemente dai termini di licenza di queste librerie e di copiare e distribuire l'eseguibile risultante secondo i termini di tua scelta, a condizione che rispetti anche, per questo software e anche per ciascuna delle librerie collegate, i termini e le condizioni della licenza per quel modulo. Una libreria indipendente è un modulo che non è derivato o basato su questo software. Se modifichi questo software, puoi estendere questa eccezione alla tua versione del software, ma non sei obbligato a farlo. Se non desideri farlo, elimina questa dichiarazione di eccezione dalla tua versione modificandone le dipendenze.
Si tenga presente che CSI-Piemonte aderisce alla posizione dell'Unione Europea per quanto riguarda l'estensione della cosiddetta "viralità", specificatamente in riferimento al collegamento dinamico. Ciononostante speriamo di condividere il nostro software per il bene della comunità più vasta, quindi cerchiamo di gestire le licenze il più possibile in modo trasparente. Ciò significa anche che se pensate che possa essere sollevata qualche obiezione, fatecelo sapere; saremo lieti di imparare e migliorare la nostra conoscenza e il rispetto delle regole della licenza!
Per lo stesso motivo tieni presente che per la build di Stilo dovrai scaricare in autonomia alcune librerie (es. bootstrap, jquery, com.google.gwt, … ) rilasciate solo sotto “GPL v 2”, che è stata dichiarata non compatibile con “AGPL 3 or later”  da FSF. Altrimenti, sei libero di correggere e sostituire tali librerie, e in tal caso saremo felici di conoscerlo e condividerlo.