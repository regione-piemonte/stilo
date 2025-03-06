package it.eng.areas.hybrid.module.cryptoLight.util;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class SmartCardUtils {
  public static final String PKCS11_KEYSTORE_TYPE = "PKCS11";
  public static final String X509_CERTIFICATE_TYPE = "X.509";
  public static final String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA";
  public static final String SUN_PKCS11_PROVIDER_CLASS = "sun.security.pkcs11.SunPKCS11";

  public static final int digitalSignature = 0;
  public static final int nonRepudiation = 1;
  public static final int keyEncipherment = 2;
  public static final int dataEncipherment = 3;
  public static final int keyAgreement = 4;
  public static final int keyCertSign = 5;
  public static final int cRLSign = 6;
  public static final int encipherOnly = 7;
  public static final int decipherOnly = 8;

  private SmartCardUtils() {

  }

  /**
   * Inizializza il KeyStore della SmartCart
   * 
   * @param aPKCS11LibraryFileName
   * @param aSmartCardPIN
   * @return
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public static KeyStore loadKeyStoreFromSmartCard(String aPKCS11LibraryFileName, String aSmartCardPIN, ClassLoader  classLoader) throws GeneralSecurityException, IOException {
    // Creo un file di configurazione
    String pkcs11ConfigSettings = "name = SmartCard\n" + "library = " + aPKCS11LibraryFileName + "\n";
    pkcs11ConfigSettings += "disabledMechanisms={ CKM_SHA1_RSA_PKCS }\n";

    byte[] pkcs11ConfigBytes = pkcs11ConfigSettings.getBytes();
    ByteArrayInputStream confStream = new ByteArrayInputStream(pkcs11ConfigBytes);

    // Instanzio il provider dinamicamente
    try {
      Class<?> sunPkcs11Class = (classLoader != null) ?  classLoader.loadClass(SUN_PKCS11_PROVIDER_CLASS) : Class.forName(SUN_PKCS11_PROVIDER_CLASS);
      Constructor<?> pkcs11Constr = sunPkcs11Class.getConstructor(java.io.InputStream.class);
      Provider pkcs11Provider = (Provider) pkcs11Constr.newInstance(confStream);
      Security.addProvider(pkcs11Provider);
      System.out.println("Provider name:" + pkcs11Provider.getName());
    } catch (Exception e) {
      throw new KeyStoreException("Can initialize Sun PKCS#11 security " + "provider. Reason: " + e, e);
    }

    // Leggo il keystore dalla SmartCard
    char[] pin = (aSmartCardPIN != null) ? aSmartCardPIN.toCharArray() : null;
    KeyStore keyStore = KeyStore.getInstance(PKCS11_KEYSTORE_TYPE);
    keyStore.load(null, pin);
    return keyStore;
  }

  private static String getCertificateAliasByKeyUsage(KeyStore keyStore, int... mandatoryKey) throws KeyStoreException {
    Enumeration<String> aliasesEnum = keyStore.aliases();
    while (aliasesEnum.hasMoreElements()) {
      String alias = aliasesEnum.nextElement();
      Certificate certificate = keyStore.getCertificate(alias);
      if (certificate instanceof X509Certificate) {
        boolean[] keyUsage = ((X509Certificate) certificate).getKeyUsage();
        boolean passed = true;
        for (int bitIdx : mandatoryKey) {
          passed &= keyUsage[bitIdx];
        }
        if (passed) {
          return alias;
        }
      }

    }
    return null;
  }

  /**
   * Ritorna il nome dell'alias del certificato di firma in base alle linee
   * guida<br>
   * Il Cerificato di autenticazione DEVE avere il bit 1 (nonRepudiation) del
   * KeyUsage Settato
   * 
   * @param keyStore
   * @return
   * @throws KeyStoreException
   */
  public static String getSignatureCertificateAlias(KeyStore keyStore) throws KeyStoreException {
    return getCertificateAliasByKeyUsage(keyStore, nonRepudiation);
  }

  /**
   * Ritorna il nome dell'alias del certificato di autenticazione in base alle
   * linee guida<br>
   * Il Cerificato di autenticazione DEVE avere il bit 1 (DigitalSignature) del
   * KeyUsage Settato
   * 
   * @param keyStore
   * @return
   * @throws KeyStoreException
   */
  public static String getIdentityCertificateAlias(KeyStore keyStore) throws KeyStoreException {
    return getCertificateAliasByKeyUsage(keyStore, digitalSignature);
  }


  public static byte[] streamToArray(InputStream src) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream(100);

    int b;
    while ((b = src.read()) >= 0) {
      buffer.write(b);
    }
    return buffer.toByteArray();

  }

}
