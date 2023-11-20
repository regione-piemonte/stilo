######################
INFORMAZIONI
######################

La dipendenza 'GdprAurigaValve-x.y.z.jar' deve contenere le seguenti classi:
--------------------------------------------------------------
it.eng.gdpr.ClientDataHttpSupport.class
it.eng.gdpr.ParametriClientExtractor.class
--------------------------------------------------------------
it.eng.gdpr.tomcat.ClientDataThreadLocal.class
it.eng.gdpr.tomcat.AurigaValve.class
--------------------------------------------------------------

Da notare che le classi 'ClientDataHttpSupport' e 'ParametriClientExtractor' 
sono anche contenute in '<war>/WEB_INF/lib/GdprUtils-x.y.z.jar'.

Il jar 'GdprAurigaValve-x.y.z.jar' deve essere presente (solo) nella directory '<tomcat>/lib'
se si verifica almeno una delle situazioni seguenti:

1) Nel file xml '<tomcat>/conf/server.xml' è presente il tag 

   <Valve className="it.eng.gdpr.tomcat.AurigaValve" />

   Altrimenti si ottiene un errore bloccante in fase di installazione del WAR.

2) Nel file 'jaas/login.config' è indicata la classe

   'it.eng.utility.authentication.module.EnhancedLoginModuleImpl'

   Altrimenti si può incorrere in un errore bloccante in fase di autenticazione.


La classe 'AurigaValve' in fase di autenticazione raccoglie dalla richiesta le informazioni sul client 
e le "passa avanti" tramite 'ClientDataThreadLocal', in modo tale da renderle disponibili in particolare 
al modulo jaas 'EnhancedLoginModuleImpl'.

Se la classe 'AurigaValve' non venisse eseguita, ad esempio perché il suo tag <Valve> è assente/commentato,
allora le informazioni sul client non sarebbero più disponibili al modulo jaas.
Al di fuori del modulo jaas, con la disponibilità della richiesta http le stesse informazioni sul client
possono essere raccolte come fa il Valve: è il caso della classe 'it.eng.auriga.ui.module.layout.server.common.LoginDataSource'.

##################
CONCLUSIONI
##################

Per fare in modo che vengano registrati in db i dati del client quando 
1) l'autenticazione passa per Jaas e
2) l'autenticazione fallisce (ad es. username e/o password errati)

si deve 

a) aggiungere nella directory '<tomcat>/lib' il jar 

      GdprAurigaValve-x.y.z.jar

b) aggiungere nel file xml '<tomcat>/conf/server.xml' il tag

      <Valve className="it.eng.gdpr.tomcat.AurigaValve" />

c) indicare nel file 'jaas/login.config' la classe 

      it.eng.utility.authentication.module.EnhancedLoginModuleImpl
      




   












