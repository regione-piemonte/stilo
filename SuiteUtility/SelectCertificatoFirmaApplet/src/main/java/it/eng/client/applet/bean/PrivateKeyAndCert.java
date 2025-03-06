package it.eng.client.applet.bean;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * contenitore per la chiave e il certificato da usare per la firma
 *
 */
public class PrivateKeyAndCert implements Serializable {
	
	private PrivateKey privateKey;
	private X509Certificate certificate;
	private Certificate[] chain;
	private String alias;
		
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public X509Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	public Certificate[] getChain() {
		return chain;
	}
	public void setChain(Certificate[] chain) {
		this.chain = chain;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}
