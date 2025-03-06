###################
MAGGIOLI
###################

La documentazione pdf fornita da Maggioli visualizza diversi xsd 
che definiscono sempre due elementi principali 'richiesta' e 'risultato'
ma senza definire un namespace!
Questo fatto, oltre a NON essere corretto, è stato bloccante nello sviluppo standard del client
soprattutto per quanto riguarda il risultato che è restituito dalla chiamata al servizio 
e di cui bisogna fare l'unmarshal.

Dunque perché funzioni il client, 
è necessario escludere le classi 'package-info' (generate automaticamente) 
in cui compare l'annotation @XmlSchema.