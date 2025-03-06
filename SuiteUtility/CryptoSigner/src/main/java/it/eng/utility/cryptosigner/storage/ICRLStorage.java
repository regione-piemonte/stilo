package it.eng.utility.cryptosigner.storage;

import it.eng.utility.cryptosigner.exception.CryptoStorageException;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

/**
 * Storage delle CRL
 * @author Rigo Michele
 * @version 0.1
 */
public interface ICRLStorage {
	
	/**
	 * Inserico una CRL nello storage associata ad un certificato
	 * @param crl
	 */
	public void insertCRL(X509CRL crl, String country, String serviceProviderName, String serviceName) throws CryptoStorageException;
	
	/**
	 * Recupero la CRL dal certificato
	 * @param crl
	 * @return
	 */
	public X509CRL retriveCRL(X509Certificate signatureCertificate) throws CryptoStorageException;
	
}
