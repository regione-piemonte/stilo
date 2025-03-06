package it.eng.utility.cryptosigner.controller.impl.signature;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Controlla la validita temporale dei certificati richiamando
 * il metodo checkValidity di ciascun certificato di firma rispetto 
 * al riferimento temporale o la data attuale
 * @author Stefano Zennaro
 *
 */
public class CertificateExpiration extends AbstractSignerController{

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateExpiration#getCheckProperty getCheckProperty}
	 */
	public static final String CERTIFICATE_EXPIRATION_CHECK = "performCertificateExpiration";
	
	private static Logger log = LogManager.getLogger( CertificateExpiration.class );
	
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
	public String getCheckProperty() {
		return CERTIFICATE_EXPIRATION_CHECK;
	}
	
	
	/**
	 * L'esecuzione prevede i seguenti passi:
	 * <ul>
	 * 	<li>Recupero delle informazioni sul timestamp dal bean di input.</li>
	 * 	<li>Verifica la validita dei certificati di ciascuna firma rispetto alla data del timestamp</li>
	 * </ul>
	 * @return boolean 
	 */
	
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni)
			throws ExceptionController {
		
		boolean result = true;
		
		Date referenceDate = input.getReferenceDate();

		//DocumentAndTimeStampInfoBean tsinfo = input.getDocumentAndTimeStampInfo();
		//log.info("tsinfo:: " + tsinfo);
		/*if (tsinfo != null ) {
			
			ValidationInfos vinfos=tsinfo.getValidationInfos();
			if(vinfos!=null){
				if(vinfos.isValid()){
					Date dataMarca = tsinfo.getTimeStampToken().getTimeStampInfo().getGenTime() ;
					log.info("dataMarca "  + dataMarca);
					
					if( referenceDate == null )
						referenceDate = dataMarca;
					else {
						if( referenceDate.before( dataMarca ) ){
							log.info("dataRiferimento " + referenceDate + " precedente alla data marca "  + dataMarca + " ");
						} else {
							log.info("dataRiferimento " + referenceDate + " successiva alla data marca "  + dataMarca + " ");
							log.info("dataMarca "  + dataMarca);
							referenceDate = dataMarca;
						}
					}
				} else {
					log.debug("marca presente ma non valida, non devo considerarne la data");
				}
			}
		} else {
			log.debug("marca non presente, non devo considerarne la data");
		}*/
		
		Map<ISignature, ValidationInfos> validationInfosMap = new HashMap<ISignature, ValidationInfos>(); 
		
		List<ISignature> signatures = null;
		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			signatures = (List<ISignature>)output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			
			String refDatString = "";
			try {
				refDatString = format.format(referenceDate); 
			} catch (Exception e) {
				log.error("Errore", e);
			}
			
			if( referenceDate!=null && refDatString.equalsIgnoreCase("01-01-1970")){
				eseguiValidazioni = false;
				log.debug("Data di riferimento pari a 01-01-1970, non si eseguono i controlli sul certificato");
			}
			
			log.info("eseguiValidazioni: " + eseguiValidazioni );
			if( eseguiValidazioni ){
				result = populateValidationInfosMapFromSignatureList(validationInfosMap, signatures, referenceDate);
			}
			
			output.setProperty(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY, validationInfosMap);
		}
		
		return result;
	}

	private boolean populateValidationInfosMapFromSignatureList(Map<ISignature, ValidationInfos> validationInfosMap, List<ISignature> signatures, Date referenceDate) {
		boolean result = true;
		if( signatures != null )
		for (ISignature signature: signatures) {
			
			ValidationInfos validationInfos = new ValidationInfos();
			
			/*
			 * Verifica della validita dei certificati di firma :
			 */
			X509Certificate signatureCertificate = signature.getSignerBean().getCertificate();
			
			if( signatureCertificate!=null ){
				// Controllo che il bit non repudation del keyusage sia a 1
				boolean[] keyUsage = signature.getSignerBean().getCertificate().getKeyUsage();
				if (keyUsage==null || !keyUsage[1]){
					log.error(MessageHelper.getMessage( CertMessage.CERT_NONREPUDIATION ));
					validationInfos.addErrorWithCode( CertMessage.CERT_NONREPUDIATION, MessageHelper.getMessage( CertMessage.CERT_NONREPUDIATION ) );
					validationInfos.addError( MessageHelper.getMessage( CertMessage.CERT_NONREPUDIATION ) );
				} else {
					log.debug("keyUsage non ripudio a true");
				}
				
				/*
				 * Verifico che il certificato sia valido rispetto ai valori di expiration indicati nel certificato stesso
				 */
				try {
					if (referenceDate==null) {
						log.debug("Verifico la validita' del certificato alla data corrente");
						signatureCertificate.checkValidity();
						log.debug("Il certificato di firma e' in corso di validita'");
					} else {
						log.debug("Verifico la validita' del certificato alla data di riferimento " + referenceDate);
						signatureCertificate.checkValidity(referenceDate);
						log.debug("Il certificato di firma e' in corso di validita'");
					}
				} catch (Exception e) {
					validationInfos.addErrorWithCode( CertMessage.CERT_EXPIRED, MessageHelper.getMessage( CertMessage.CERT_EXPIRED, referenceDate, e.getMessage() ) );
					validationInfos.addError( MessageHelper.getMessage( CertMessage.CERT_EXPIRED, referenceDate, e.getMessage() ) );
					log.debug("Il certificato di firma e' scaduto");
				}
			}
			
			validationInfosMap.put(signature, validationInfos);
			
			result &= validationInfos.isValid();
			
			if (performCounterSignaturesCheck) {
				List<ISignature> counterSignatures = signature.getCounterSignatures();
				populateValidationInfosMapFromSignatureList(validationInfosMap, counterSignatures, referenceDate);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String refDatString = "";
		Date referenceDate = null;
		try {
			refDatString = format.format(referenceDate); 
		} catch (Exception e) {
			log.error("Errore", e);
		}
	}
}
