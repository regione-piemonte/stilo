package it.eng.hybrid.module.firmaCertificato.bean;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * contenitore per la chiave e il certificato da usare per la firma
 *
 */
public class PrivateKeyAndCert {
	
	private PrivateKey privateKey;
	private X509Certificate certificate;
	private Certificate[] chain;
	private boolean validita;
	private String motivoErrore;
		
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
	public boolean isValidita() {
		return validita;
	}
	public void setValidita(boolean validita) {
		this.validita = validita;
	}
	public String getMotivoErrore() {
		return motivoErrore;
	}
	public void setMotivoErrore(String motivoErrore) {
		this.motivoErrore = motivoErrore;
	}
	
}
