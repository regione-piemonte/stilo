package it.eng.utility.cryptosigner.controller.impl.cert;

import it.eng.utility.cryptosigner.controller.bean.InputCertBean;
import it.eng.utility.cryptosigner.controller.bean.OutputCertBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.utils.MessageHelper;

import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * Controlla la validita temporale del certificato richiamando
 * il metodo checkValidity  rispetto 
 * al riferimento temporale o la data attuale 
 * @author Stefano Zennaro
 *
 */
public class CertificateExpiration extends AbstractCertController{

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateExpiration#getCheckProperty getCheckProperty}
	 */
	public static final String CERTIFICATE_EXPIRATION_CHECK = "performCertificateExpiration";
	

	public String getCheckProperty() {
		return CERTIFICATE_EXPIRATION_CHECK;
	}


	/**
	 * L'esecuzione prevede i seguenti passi:
	 * <ul>
	 * 	<li>Se è pasato un certificato nell'input verifica se è valido rispetto alla data passata 
	 *   o la data corrente se è null</li>
	 * </ul>
	 * @return boolean 
	 */

	public boolean execute(InputCertBean input, OutputCertBean output, boolean eseguiValidazioni)
	throws ExceptionController {
		boolean result = true;
		//conserva le info di validazione relative all'espirazione del certificato
		if( eseguiValidazioni )
			result = populateValidationInfos(input,output);
		return result;
	}

	private boolean populateValidationInfos( InputCertBean input, OutputCertBean output) {
		boolean result = true;
		ValidationInfos validationInfos = new ValidationInfos();
		//TODO check data available
		if(input!=null && input.getCertificate()!=null){
			X509Certificate x509Certificate=input.getCertificate();
			Date referenceDate =input.getReferenceDate();

			/*
			 * Verifico che il certificato sia valido rispetto ai valori di expiration indicati nel certificato stesso
			 */
			try {
				log.debug("Verifico la validita' temporale del certificato di firma ");
				if (referenceDate==null)
					x509Certificate.checkValidity();
				else
					x509Certificate.checkValidity(referenceDate);
				
				log.debug("Il certificato di firma e' in corso di validita'");
			} catch (Exception e) {
//				Object[] par=new Object[2];
//				par[0]=x509Certificate.getNotAfter();
//				par[1]=e.getMessage();
				
				validationInfos.addErrorWithCode(CertMessage.CERT_EXPIRED, MessageHelper.getMessage(CertMessage.CERT_EXPIRED, x509Certificate.getNotAfter(),e.getMessage()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_EXPIRED, x509Certificate.getNotAfter(),e.getMessage()));
				//validationInfos.addError("Il certificato e scaduto in data: "+ x509Certificate.getNotAfter() + " - " + e.getMessage());
				log.debug("Il certificato e scaduto in data: "+ x509Certificate.getNotAfter() );
			}
		}else{
			validationInfos.addErrorWithCode( CertMessage.CERT_NOTFOUND, MessageHelper.getMessage( CertMessage.CERT_NOTFOUND ) );
			validationInfos.addError( MessageHelper.getMessage( CertMessage.CERT_NOTFOUND ) );
		}
		output.setProperty(OutputCertBean.CERTIFICATE_EXPIRATION, validationInfos);


		result &= validationInfos.isValid();

		//			if (performCounterSignaturesCheck) {
		//				List<ISignature> counterSignatures = signature.getCounterSignatures();
		//				populateValidationInfosMapFromSignatureList(validationInfosMap, counterSignatures, referenceDate);
		//			}

		return result;
	}

}
