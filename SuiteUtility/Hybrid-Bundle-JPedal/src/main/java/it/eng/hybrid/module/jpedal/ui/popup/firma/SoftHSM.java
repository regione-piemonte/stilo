package it.eng.hybrid.module.jpedal.ui.popup.firma;

import java.io.*;
import java.math.*;
import java.util.*;

import java.security.*;
import java.security.interfaces.*;
import java.security.cert.*;

import sun.security.pkcs11.*;
import org.bouncycastle.x509.*;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class SoftHSM 
{
    public static void main(String args[]) throws Exception {
        // Set up the Sun PKCS 11 provider
       
    				        
//        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
//        Provider p = new sun.security.pkcs11.SunPKCS11(confStream);
//		
    	String configName = "viewer/gui/popups/firma/softhsm.cfg";
        Provider p = new SunPKCS11(configName);

        if (-1 == Security.addProvider(p)) {
            throw new RuntimeException("could not add security provider");
        }

        // Load the key store
        char[] pin = "12345".toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS11", p);
        ks.load(null, pin);

        showProperty(ks);
        
        
        
        // Generate the key
        SecureRandom sr = new SecureRandom();
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", p);
        keyGen.initialize(1024, sr);
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey pk = keyPair.getPrivate();

        // Java API requires a certificate chain
        X509Certificate[] chain = generateV3Certificate(keyPair);

        ks.setKeyEntry("ALIAS-GOES-HERE", pk, "abcd".toCharArray(), chain);

        ks.store(null);

        //System.out.println("OK");
        
        showProperty(ks);
    }


    public static X509Certificate[] generateV3Certificate(KeyPair pair) throws InvalidKeyException, NoSuchProviderException, SignatureException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(new X500Principal("CN=Test Certificate"));
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 10000));
        Date dataScadenza = new Date(2015, 1, 1);
        certGen.setNotAfter(dataScadenza);
        certGen.setSubjectDN(new X500Principal("CN=Test Certificate"));
        certGen.setPublicKey(pair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSA");

        certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
        certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment | KeyUsage.nonRepudiation));
        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));

        certGen.addExtension(X509Extensions.SubjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.rfc822Name, "test@test.test")));

        X509Certificate[] chain = new X509Certificate[1];
        chain[0] = certGen.generateX509Certificate(pair.getPrivate(), "SunPKCS11-SoftHSM");

        return chain;
    }
    
    public static void showProperty(KeyStore ks) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException{
    	Enumeration enumeration = ks.aliases();
		while(enumeration.hasMoreElements()){
			String alias = enumeration.nextElement().toString();
			System.out.println(alias);
			PrivateKey privateKey = (PrivateKey)ks.getKey(alias, null); 
			System.out.println(privateKey);
			X509Certificate certificate=(X509Certificate)ks.getCertificate(alias);
			System.out.println(certificate);
		}
    }
}
