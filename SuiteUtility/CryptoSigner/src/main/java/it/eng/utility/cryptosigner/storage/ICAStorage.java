package it.eng.utility.cryptosigner.storage;

import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;

import java.security.cert.X509Certificate;
import java.util.List;

import javax.security.auth.x500.X500Principal;
/**
 * Storage dei certificati
 * @author Rigo Michele
 * @version 0.1
 */
public interface ICAStorage {

	/**
	 * Inserisco una nuovo Certificato se esiste già viene sovrascritto
	 * @param certificate
	 */
	public void insertCA(X509Certificate certificate, String country, String serviceProviderName, String serviceName, String serviceStatus, String serviceType) throws CryptoStorageException;
	
	public void updateCA(X509Certificate certificate, String country, String serviceProviderName, String serviceName, String serviceStatus, String serviceType, boolean active) throws CryptoStorageException;
	
	/**
	 * Recupero il certificato dal soggetto X500Principal, se non trova il certificato restituisce null.
	 * @param subject
	 * @return X509Certificate
	 */
	public X509Certificate retriveCA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException;

	public CACertificate retriveValidCA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException;

	public CACertificate retriveValidTSA(X509Certificate x509Certificato, boolean isSubject) throws CryptoStorageException;
	/**
	 * Restituisce la lista dei certificati attivi.
	 * @param subject
	 * @return
	 */
	public List<X509Certificate> retriveActiveCA() throws CryptoStorageException;
	public List<X509Certificate> retriveActiveCA_IT() throws CryptoStorageException;
	
	
	/**
	 * revoco il certificato
	 * @param certificate
	 */
	public void revokeCA(X509Certificate certificate) throws CryptoStorageException;
	
	/**
	 * Controlla se il certificato è ancora attivo
	 * @param certificate
	 */
	public boolean isActive(X509Certificate certificate) throws CryptoStorageException;
}