package it.eng.utility.cryptosigner.controller.bean;

import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.tsp.TimeStampToken;

/**
 * Racchiude le informazioni sull'analisi di marche temporali.
 * Attualmente le informazioni contenute sono le seguenti:
 * <ul>
 * 	<li>associatedFile: file associato alla marca temporale</li>
 * 	<li>timeStampToken: timestamp secondo il formato di bouncycastle</li>
 * 	<li>validationInfos: informazioni sugli errori verificatisi durante le fasi di analisi
 * 	<li>timeStampTokenType: tipo di timeStamp (EMBEDDED/DETACHED)</li>
 *  <li>timeStampExtensionChain: lista delle estensioni della marca temporale</li>
 *  <li>tokenInfos: informazioni sulla marca temporale. Attualmente sono definite le seguenti proprieta:
 *  	<ul>
 *  		<li>PROP_HASH_ALGORITHM: OID dell'algoritmo di digest del timestamp</li>
 *  		<li>PROP_MILLISECS: somma della data di riferimento temporale e il massimo scostamento in millisecondi contenuto nella marca</li>
 *  		<li>PROP_DATE: abbreviazione per Date(PROP_MILLISECS)</li>
 *  		<li>PROP_TIMESTAMP_FORMAT: formato della busta contenente il timestamp</li>
 *  		<li>PROP_RECOGNIZED_CERTIFICATE: true se il certificato e contenuto nella lista di certificate accreditati</li>
 *  		<li>PROP_SID: nome della TSA</li>
 *  	</ul>
 * 	</li>
 * </ul>
 * @author Stefano Zennaro
 *
 */
public class DocumentAndTimeStampInfoBean {
	
	/**
	 * OID dell'algoritmo di digest del timestamp
	 */
	public static final String PROP_HASH_ALGORITHM = "Hash Algorithm";
	
	/**
	 * somma della data di riferimento temporale e il massimo scostamento in millisecondi contenuto nella marca
	 */
	public static final String PROP_MILLISECS = "Temporal reference";
	
	/**
	 * abbreviazione per Date(PROP_MILLISECS)
	 */
	public static final String PROP_DATE = "Date reference";
	
	/**
	 * formato della busta contenente il timestamp
	 */
	public static final String PROP_TIMESTAMP_FORMAT = "Timestamp Format";
	
	/**
	 * true se il certificato e contenuto nella lista di certificate accreditati
	 */
	public static final String PROP_RECOGNIZED_CERTIFICATE = "TSA Recognized Certificate";
	
	/**
	 * nome della TSA
	 */
	public static final String PROP_SID = "Signer";
	
	
	/**
	 * Certificato accreditato
	 */
	public static final String PROP_QUALIFIED_CERTIFICATE = "Qualified Certificate";
	
	//private DocumentBean documentBean;
		
	private File associatedFile;
	
	private TimeStampToken timeStampToken;
	
//	private Properties tokenInfos;
	private HashMap<String, Object> tokenInfos;
	
	private ValidationInfos validationInfos;
	
	/**
	 * Tipo di timestamp: EMBEDDED/DETACHED
	 * @author Stefano Zennaro
	 *
	 */
	public enum TimeStampTokenType{EMBEDDED, DETACHED};
	private TimeStampTokenType timeStampTokenType;
	
	/*
	 * Aggiunto..
	 */
	private List<TimeStampToken> timeStampExtensionChain;
	
	/**
	 * Controlla se il timestampToken contiene un certificato attualmente valido
	 * e preferibile affidarsi ad un controllo esterno per 
	 * verificare correttamente la data attuale {@link it.eng.crypto.controller.ITimeStampValidator ITimeStampValidator})
	 * @return true se il certificato del timestamp e attualmente valido
	 */
//	public boolean isCurrentlyValid() 
//	{
//		
//		try 
//		{
//			timeStampToken.getSID().getCertificate().checkValidity();
//		} 
//		catch (CertificateException e) 
//		{
//			return false;
//		}
//		return true;
//		
//		/*
//		
//			
//	        SignerId signerId = timeStampToken.getSID();
//	        BigInteger signerCertSerialNumber = signerId.getSerialNumber();
//	        X500Name signerCertIssuer = signerId.getIssuer();
//	        //LOG.log(POILogger.DEBUG, "signer cert serial number: " + signerCertSerialNumber);
//	        //LOG.log(POILogger.DEBUG, "signer cert issuer: " + signerCertIssuer);
//
//	        // TSP signer certificates retrieval
//	        Collection<X509CertificateHolder> certificates = timeStampToken.getCertificates().getMatches(null);
//	       
//	        X509CertificateHolder signerCert = null;
//	        Map<X500Name, X509CertificateHolder> certificateMap = new HashMap<X500Name, X509CertificateHolder>();
//	        
//	        for (X509CertificateHolder certificate : certificates) 
//	        {
//	            if (signerCertIssuer.equals(certificate.getIssuer()) && signerCertSerialNumber.equals(certificate.getSerialNumber())) 
//	            {
//	                signerCert = certificate;
//	            }
//	            //certificateMap.put(certificate.getSubject(), certificate);
//	        }
//	        
//	        signerCert.isValidOn(new Date());
//	       
//			
//		*/
//		return true;
//	}
	
	/**
	 * e preferibile affidarsi ad un controllo esterno per 
	 * verificare correttamente la data {@link it.eng.crypto.controller.ITimeStampValidator ITimeStampValidator})
	 * Controlla se il timestampToken contiene un certificato valido alla data di riferimento in ingresso
	 * @param date data di riferimento per il controllo
	 * @return true se il certificato del timestamp era valido alla data considerata
	 */
	public boolean isValidAtDate(Date date) 
	{
		/*try 
		{*/
			//timeStampToken.getSID().getCertificate().checkValidity(date);
			
		  SignerId signerId = timeStampToken.getSID();
	        BigInteger signerCertSerialNumber = signerId.getSerialNumber();
	        X500Name signerCertIssuer = signerId.getIssuer();
	        //LOG.log(POILogger.DEBUG, "signer cert serial number: " + signerCertSerialNumber);
	        //LOG.log(POILogger.DEBUG, "signer cert issuer: " + signerCertIssuer);

	        // TSP signer certificates retrieval
	        Collection<X509CertificateHolder> certificates = timeStampToken.getCertificates().getMatches(null);
	       
	        X509CertificateHolder signerCert = null;
	        Map<X500Name, X509CertificateHolder> certificateMap = new HashMap<X500Name, X509CertificateHolder>();
	        
	        for (X509CertificateHolder certificate : certificates) 
	        {
	            if (signerCertIssuer.equals(certificate.getIssuer()) && signerCertSerialNumber.equals(certificate.getSerialNumber())) 
	            {
	                signerCert = certificate;
	            }
	            //certificateMap.put(certificate.getSubject(), certificate);
	        }
	        
	        signerCert.isValidOn(date);
				
		/*} 
		catch (CertificateException e) 
		{
			return false;
		}*/
		return true;
	}

	/**
	 * Recupera le informazioni sulla marca temporale
	 * @return le informazioni sulla marca temporale 
	 */
	public HashMap getValidityInfo() {
		return tokenInfos;
	}
	
	
	/**
	 * Recupera il file contenente la marca temporale 
	 * @return il file della marca temporale
	 */
	public File getAssociatedFile() {
		return associatedFile;
	}

	/**
	 * Definisce i file firmati dalla marca temporale
	 * @param associatedFile i file firmati dalla marca temporale
	 */
	public void setAssociatedFile(File associatedFile) {
		this.associatedFile = associatedFile;
	}

	/**
	 * Recupera il timestamp contenuto nel file 
	 * @return il timestamp 
	 */
	public TimeStampToken getTimeStampToken() {
		return timeStampToken;
	}

	/**
	 * Definisce il timestamp della marca
	 * @param timeStampToken
	 */
	public void setTimeStampToken(TimeStampToken timeStampToken) {
		this.timeStampToken = timeStampToken;
	}
	
	/**
	 * Aggiunge una proprieta alla lista delle informazioni 
	 * sulla marca temporale
	 * @param name	chiave della proprieta
	 * @param value	valore della proprieta
	 */
	public void setProperty(String name, Object value) {
		if (tokenInfos == null)
			tokenInfos = new HashMap<String, Object>();
		tokenInfos.put(name, value);
	}
	
	/**
	 * Recupera il tipo di timeStamp (EMBEDDED/DETACHED)
	 * @return il tipo di timestamp
	 */
	public TimeStampTokenType getTimeStampTokenType() {
		return timeStampTokenType; 
	}

	/**
	 * Definisce il tipo di timestamp
	 * @param timeStampTokenType
	 */
	public void setTimeStampTokenType(TimeStampTokenType timeStampTokenType) {
		this.timeStampTokenType = timeStampTokenType;
	}

	/**
	 * Stampa le seguenti informazioni:
	 * <ul>
	 * 	<li>File associato</li>
	 * 	<li>marca temporale</li>
	 * 	<li>tipo di timestamp</li>
	 * 	<li>estensioni della marca</li>
	 * 	<li>informazioni sulla marca</li>
	 * 	<li>esito delle verifiche della marca</li>
	 * </ul>
	 * @return string
	 */
	public String toString() {
		return " Associated File: " + associatedFile + ",\n timeStampToken: " + timeStampToken + ",\n timeStampTokenType: " +  getTimeStampTokenType() + (timeStampExtensionChain==null?"": ",\n timeStampExtensions: " + timeStampExtensionChain) + ", \n tokenInfos: " + tokenInfos + ",\n validationInfos: " + validationInfos;
	}

	/**
	 * Recupera le informazioni sulle verifiche della marca temporale
	 * @return l'esito delle verifiche della marca
	 */
	public ValidationInfos getValidationInfos() {
		return validationInfos;
	}

	/**
	 * Definisce le informazioni sulle verifiche della marca temporale
	 * @param validationInfos
	 */
	public void setValidationInfos(ValidationInfos validationInfos) {
		this.validationInfos = validationInfos;
	}

	/**
	 * Recupera la lista delle estensioni della marca temporale
	 * @return la lista delleestensioni della marca temporale
	 */
	public List<TimeStampToken> getTimeStampExtensionChain() {
		return timeStampExtensionChain;
	}

	/**
	 * Definisce la lista delle estensioni della marca temporale
	 * @param timeStampExtensionChain  lista delle estensioni della marca temporale
	 */
	public void setTimeStampExtensionChain(
			List<TimeStampToken> timeStampExtensionChain) {
		this.timeStampExtensionChain = timeStampExtensionChain;
	}

}
