package it.eng.areas.hybrid.module.cryptoLight.tools;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import it.eng.areas.hybrid.module.cryptoLight.tools.ITrace.Level;
import it.eng.areas.hybrid.module.cryptoLight.util.Base64Utils;
import it.eng.areas.hybrid.module.cryptoLight.util.SmartCardUtils;






public class SmartCardChecker {
  private static final String LOG_HEADER = "---------------------------------------------------";
  public static final String VERSION = "1.0";
  
  public void check(ITrace trace, String pin) throws Exception {
    Properties properties = new Properties();
    
    trace.trace(Level.TRACE, LOG_HEADER);
    trace.trace(Level.INFO, "SmartCardChecker "+VERSION);
    trace.trace(Level.TRACE, LOG_HEADER);
    trace.trace(Level.INFO, "Java version: "+System.getProperty("java.version"));
    trace.trace(Level.INFO, "JVM home: "+System.getProperty("java.home"));
    trace.trace(Level.INFO, "Data Model: "+System.getProperty("sun.arch.data.model")); 
    trace.trace(Level.TRACE, "\n");
    
    
    if ("1.6".compareTo(System.getProperty("java.version")) > 0) {
      trace.trace(Level.ERROR, "La versione della Java Virtual Machine dovrebbe essere 1.6 o superiore per l'accesso al PKCS#11\n");
    }
    
    trace.trace(Level.TRACE, "Cerco il file smartcardcheck.properties con la lista delle dll utente da verificare...");
    
    properties.load(this.getClass().getResourceAsStream("dll.properties"));
    
    File propertiesFile = new File("smartcardcheck.properties");
    if (propertiesFile.exists()) {
      properties.load(new FileInputStream(propertiesFile));
      trace.trace(Level.TRACE, "File smartcardcheck.properties caricato\n");
    } else {
      trace.trace(Level.TRACE, "File smartcardcheck.properties non presente\n");
    }
    
    
    boolean found = false;
    
    for (int i = 0; i < 199; i++) {
      String dll = properties.getProperty("provider.dll."+i);
      if (dll != null) {
        try {
          trace.trace(Level.TRACE, "Verifica dll: "+dll+"...");
          KeyStore keyStore = SmartCardUtils.loadKeyStoreFromSmartCard(dll, pin, null);
          trace.trace(Level.INFO, "Accesso riuscito con dll: "+dll);
          
          Enumeration<String> aliasesEnum = keyStore.aliases();
          while (aliasesEnum.hasMoreElements()) {
            trace.trace(Level.INFO, "Alias certificato:"+aliasesEnum.nextElement());
          }
          
          trace.trace(Level.TRACE, "Log certificati\n\n");
          aliasesEnum = keyStore.aliases();
          while (aliasesEnum.hasMoreElements()) {
            String alias = aliasesEnum.nextElement();
            trace.trace(Level.TRACE, LOG_HEADER);
            trace.trace(Level.TRACE, alias);
            trace.trace(Level.TRACE, LOG_HEADER);
            X509Certificate certificate = (X509Certificate)keyStore.getCertificate(alias);
            trace.trace(Level.TRACE, certificate.toString());
            trace.trace(Level.TRACE, "\n\n");
            
          }
          
          trace.trace(Level.TRACE, "Export certificati base64\n\n");
          aliasesEnum = keyStore.aliases();
          while (aliasesEnum.hasMoreElements()) {
            String alias = aliasesEnum.nextElement();
            trace.trace(Level.TRACE, LOG_HEADER);
            trace.trace(Level.TRACE, alias);
            trace.trace(Level.TRACE, LOG_HEADER);
            X509Certificate certificate = (X509Certificate)keyStore.getCertificate(alias);
            trace.trace(Level.TRACE, Base64Utils.base64Encode(certificate.getEncoded()));
            trace.trace(Level.TRACE, "\n\n");
            
          }
          
          found = true;
        } catch (Exception e) {
          if (e instanceof LoginException || e.getCause() instanceof LoginException) {
            trace.trace(Level.ERROR, "Accesso fallito. Causa "+e.getMessage());
            trace.trace(Level.ERROR, "Una possibile causa è il PIN ERRATO\n");
          } else
          
          trace.trace(Level.TRACE, "Accesso fallito. Causa:\n "+e.getMessage()+"\n");
        }
      }
    }
    if (!found) {
      trace.trace(Level.ERROR, "Non è stato possibile accedere alla SmartCard\n");
    } else {
      trace.trace(Level.INFO, "Accesso alla SmartCard avvenuto con successo!\n");
    }
  }

}
