package it.eng.client.applet.operation;


import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardException;

import java.io.File;
import java.security.AuthProvider;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

public abstract class AbstractSigner {

	 
	public static String dir = System.getProperty("user.home");
	public static String cryptodll = "dllCrypto";
	public static String cryptoConfig = cryptodll+File.separator+"config";
	public static String cryptoConfigFile = cryptoConfig+File.separator+"crypto.config";
	
	public static String externalDllPath = "dllPath";
	
   protected AuthProvider provider = null;
	
	/**
	 * Definisce se la busta è una base 64
	 */
	private boolean base64 = false;
	
	public boolean isBase64() {
		return base64;
	}

	public void setBase64(boolean base64) {
		this.base64 = base64;
	}
	
	public AbstractSigner(AuthProvider provider) {
		this.provider = provider;
	}
	
	private CertStore getCertStore(X509Certificate certificate) throws Exception {
		  ArrayList<Certificate> list = new ArrayList<Certificate>();
		  list.add(certificate);
		  return CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
	}
	 
	public abstract X509Certificate[] getSigningCertificates(char[] pin) throws SmartCardException;
	
	public abstract PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin) throws SmartCardException;
	
	public abstract X509Certificate[] getSigningCertificates(char[] pin, char[] cardPin) throws SmartCardException;
	
	public abstract PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin, char[] cardPin) throws SmartCardException;
	
	
	
		public AuthProvider getProvider() {
			return provider;
		}
		
		
}