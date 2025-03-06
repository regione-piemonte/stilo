In questo progetto abbiamo cercato di portare tutte le operazioni che ricorrono frequentemente
nell'elaborazione di file pdf. 
In questo modo cerchiamo di evitare di duplicare gli stessi controlli e le stesse funzionalit� 
in pi� parti, semplificandone cos� la manutenzione.

Nei vari package si trovano operazioni legate alle seguenti funzionalit�: 
	- verifica e gestione di pdf con commenti
	- verifica e gestione di pdf editabili (anche del tipo xform)
	- verifica e gestione di pdf/A
	- verifica e gestione di pdf multilayer
	- operazioni di fusione di pi� pdf
	- operazioni generiche di riscrittura di pdf 
	- operazioni generiche sui pdf (calcolo numero pagine, ecc)
	
L'obiettivo dei vari metodi di verifica � quello di restituire sempre un bean genericoPdfBean
contenente tutti i dati relativi alle verifiche (se ha commenti e in che pagine, se � editabile,
se � pdfa, se � multilayer, ecc) 

Nei package di test sono stati riportati test unitari delle varie tipologie di operazioni 
e sono presenti tutta una serie di files su cui effettuare le varie verifiche (man mano che 
incontriamo casi particolari dovremmo aggiungerli in modo da poterli eseguire facilmente senza 
dover ogni volta reperire casistiche da testare) 