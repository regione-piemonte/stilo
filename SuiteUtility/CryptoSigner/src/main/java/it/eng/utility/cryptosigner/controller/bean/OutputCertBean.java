package it.eng.utility.cryptosigner.controller.bean;

import java.util.Map;

import org.apache.log4j.Logger;





 
public class OutputCertBean extends OutputBean{
	public static final Logger log = Logger.getLogger(OutputCertBean.class);
	/**
	 * Tipo: List<ISignature> - lista delle firme  
	 */
	//public static final String SIGNATURE_PROPERTY				= "Signatures";
	
	/**
	 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla validita di ciascuna firma
	 */
	//public static final String SIGNATURE_VALIDATION_PROPERTY	= "Signature Validation Infos";
	
	/**
	 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla validita di ciascuna firma
	 */
	//public static final String CERTIFICATE_VALIDATION_PROPERTY	= "Certficate Validation Infos";
	
	/**
	 *  Tipo: ValidationInfos  - informazioni sulla scadenza del certificato
	 */
	public static final String CERTIFICATE_EXPIRATION	= "CERTIFICATE_EXPIRATION";
	
	/**
	 *  Tipo:   ValidationInfos  - informazioni sulla revoca dei certificati
	 */
	public static final String CRL_VERIFY			= "CRL_VERIFY";
	
	/**
	 *  Tipo:ValidationInfos validazioni ralative al controllo di attendibilità CA
	 */
	public static final String CA_VERIFY = "CA_VERIFY";
	
	/**
	 *  Tipo: ValidationInfos - informazioni sulla validita del formato rispetto al periodo di validita associato
	 */
	//public static final String FORMAT_VALIDITY_PROPERTY 		= "FormatCheckValidity";
	
	/**
	 *  Tipo: List<DocumentAndTimeStampInfo> 
	 */
	//public static final String TIME_STAMP_INFO_PROPERTY			= "TimeStampProperty";
	
	/**
	 *  Tipo:   X509Certificate -  certificato di certificazione accreditato
	 */
	public static final String CERTIFICATE_RELIABILITY_PROPERTY = "CertificateReliabilityProperty";
	
	/**
	 *  Tipo: String - cone dell'eventuale controller che a andato in errore
	 */
	public static final String MASTER_SIGNER_EXCEPTION_PROPERTY = "MasterSignerExceptionProperty"; 
	
	 
	
	public boolean isValid(boolean strict){
		boolean ret=true;
		//controllo tutte le prop di tipo validationInfo per vedere se sono valide
		Map<String,ValidationInfos> props=getPropsOfType(ValidationInfos.class);
		java.util.Set<String> keys=props.keySet();
		for (String key : keys) {
			ValidationInfos info=props.get(key);
			if(!info.isValid(strict)){
				ret=false;
				log.debug("chiave  "+key+" associata a validazione fallita");
				break;
			}
		}
		return ret;
	}
	
	 
}
