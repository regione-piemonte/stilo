package it.eng.utility.cryptosigner.controller.bean;





/**
 * Bean contenente tutte le proprieta di output settate dai vari controller durante il processo principale.
 * Attraverso gli step di analisi dei controller questo bean viene via via popolato delle proprieta
 * che ciascun controller estrae. Alla fine conterra il risultato di un intero ciclo di analisi.
 * L'attributo 'child' contiene il riferimento ad un oggetto della medesima classe e puo essere valorizzato
 * con il risultato del ciclo successivo.
 * Sono definite le proprieta indicate dai parametri seguenti:
 * <ul>
 * 	<li>ENVELOPE_FORMAT_PROPERTY: formato della busta</li>
 * 	<li>SIGNATURE_PROPERTY: lista delle firme</li>
 * 	<li>SIGNATURE_VALIDATION_PROPERTY: informazioni sulla validita di ciascuna firma</li>
 * 	<li>CERTIFICATE_EXPIRATION_PROPERTY: informazioni sulla scadenza dei certificati di ciascuna firma</li>
 * 	<li>CRL_VALIDATION_PROPERTY: informazioni sulla revoca dei certificati</li>
 * 	<li>CERTIFICATE_UNQUALIFIED_PROPERTY: lista delle firme con certificato non riconosciuto </li>
 * 	<li>FORMAT_VALIDITY_PROPERTY: informazioni sulla validita del formato rispetto al periodo di validita associato</li>
 * 	<li>TIME_STAMP_INFO_PROPERTY: informazioni sulle marche temporali associate</li>
 * </ul>
 * @author Stefano Zennaro
 *
 */
public class OutputSignerBean extends OutputBean{

	/**
	 *  Tipo: String - formato della busta
	 */
	public static final String ENVELOPE_FORMAT_PROPERTY			= "Envelope Format";	
	
	/**
	 * Tipo: List<ISignature> - lista delle firme
	 */
	public static final String SIGNATURE_PROPERTY				= "Signatures";
	
	/**
	 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla validita di ciascuna firma
	 */
	public static final String SIGNATURE_VALIDATION_PROPERTY	= "Signature Validation Infos";
	
	/**
	 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla validita di ciascuna firma
	 */
	public static final String CERTIFICATE_VALIDATION_PROPERTY	= "Certficate Validation Infos";
	
	/**
	 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla scadenza dei certificati di ciascuna firma
	 */
	public static final String CERTIFICATE_EXPIRATION_PROPERTY	= "Certificate Expiration Infos";
	
	/**
	 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla revoca dei certificati
	 */
	public static final String CRL_VALIDATION_PROPERTY			= "CRL Validation Infos";
	
	/**
	 *  Tipo:List<ISignature> - lista delle firme con certificato non riconosciuto 
	 */
	public static final String CERTIFICATE_UNQUALIFIED_PROPERTY = "Unqualified certificates";
	
	/**
	 *  Tipo: ValidationInfos - informazioni sulla validita del formato rispetto al periodo di validita associato
	 */
	public static final String FORMAT_VALIDITY_PROPERTY 		= "FormatCheckValidity";
	
	/**
	 *  Tipo: List<DocumentAndTimeStampInfo> 
	 */
	public static final String TIME_STAMP_INFO_PROPERTY = "TimeStampProperty";
	public static final String MAP_SIGNATURE_TIME_STAMP_PROPERTY	= "MapSignatureTimeStampProperty";
	
	/**
	 *  Tipo: Map<ISignature, X509Certificate> - corrispondenza tra firma e certificato di certificazione accreditato
	 */
	public static final String CERTIFICATE_RELIABILITY_PROPERTY = "CertificateReliabilityProperty";
	
	/**
	 *  Tipo: String - cone dell'eventuale controller che a andato in errore
	 */
	public static final String MASTER_SIGNER_EXCEPTION_PROPERTY = "MasterSignerExceptionProperty"; 
	
	/**
	 *  Tipo: ValidationInfos - informazioni sulla rilevazione di codice eseguibile
	 */
	public static final String CODE_DETECTION_VALIDATION_PRIOPERTY 		= "CodeDetectionValidationProperty";
	
	/*
	 * Contenuto sbustato della firma
	 */
	private ContentBean content;
	
	/**
	 * Recupera il contenuto sbustato
	 * @return bean
	 */
	public ContentBean getContent() {
		return content;
	}

	/**
	 * Definisce il contenuto sbustato 
	 * @param content
	 */
	public void setContent(ContentBean content) {
		this.content = content;
	}

	
}
