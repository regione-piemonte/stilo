package it.eng.utility.cryptosigner.bean;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;
	   

/**
 * Bean di utilit� che consente di memorizzare le informazioni relative 
 * a una singola firma. Vengono memorizzati:
 * <ul>
 * 	<li>certificato di firma</li>
 * 	<li>emittente del certificato di firma</li>
 * 	<li>firmatario</li>
 * </ul>
 * @author Stefano Zennaro
 *
 */
public class SignerBean {
	/*
	 * Certificato di firma 
	 */
	private X509Certificate certificate;
	/*
	 * Emittente del certificato di firma
	 */
	private X500Principal iusser;
	/*
	 * Firmatario
	 */
	private Principal subject;
	
	/***
	 * SigningTime
	 */
	private Date signingTime;
	
	/**
	 * Recupera l'emittente del certificato di firma
	 * @return emittente del certificato di firma
	 */
	public X500Principal getIusser() {
		return iusser;
	}

	/**
	 * Definisce l'emittente del certificato di firma
	 * @param iusser emittente del certificato di firma
	 */
	public void setIusser(X500Principal iusser) {
		this.iusser = iusser;
	}

	/**
	 * Recupera il firmatario
	 * @return firmatario
	 */
	public Principal getSubject() {
		return subject;
	}

	/**
	 * Definisce il firmatario
	 * @param subject firmatario
	 */
	public void setSubject(Principal subject) {
		this.subject = subject;
	}

	/**
	 * Recupera il certificato di firma
	 * @return certificato di firma
	 */
	public X509Certificate getCertificate() {
		return certificate;
	}

	/**
	 * Definisce il certificato di firma
	 * @param certificate certificato di firma
	 */
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	
	public Date getSigningTime() {
		return signingTime;
	}

	public void setSigningTime(Date signingTime) {
		this.signingTime = signingTime;
	}

	public String toString(){
//		return "certificate:\n" + certificate + ",\nissuer:\n" + iusser + ",\nsubject:\n" + subject;
		return "certificate:\n" + certificate + ",\nsubject:\n" + subject;
	}
	
	
}
