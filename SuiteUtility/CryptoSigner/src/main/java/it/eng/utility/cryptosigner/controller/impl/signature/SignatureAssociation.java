package it.eng.utility.cryptosigner.controller.impl.signature;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;

import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.utils.MessageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Verifica la corretta associazione tra il contenuto firmato e le firme
 * Per ciascuna firma presente nel 
 * {@link it.eng.utility.cryptosigner.controller.bean.InputSignerBean bean di input} 
 * viene richiamato il metodo {@link it.eng.utility.cryptosigner.data.signature.ISignature#verify} 
 * della firma e salvato il risultato
 */
public class SignatureAssociation extends AbstractSignerController {
	
	static Logger log = LogManager.getLogger( SignatureAssociation.class);
	
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni)
			throws ExceptionController {
		boolean result= true;
		List<ISignature> signatures = null;
		 
		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			signatures = (List<ISignature>)output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			Map<ISignature, ValidationInfos> validationInfosMap = new HashMap<ISignature, ValidationInfos>(); 
			if( eseguiValidazioni )
				result = populateValidationInfosMapFromSignatureList(validationInfosMap, signatures);
			output.setProperty(OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY, validationInfosMap);
		}
		return result;
	}

	private boolean populateValidationInfosMapFromSignatureList(Map<ISignature, ValidationInfos> validationInfosMap, List<ISignature> signatures) {
		boolean result = true;
		if (signatures==null || signatures.size()==0)
			// Perche la busta sia valida deve esserci almeno una firma
			return false;
		
		log.info("Numero di firme individuate: " + signatures.size() );
		int i = 0;
		for (ISignature signature: signatures) {
			i++;
			//log.info("signature " + signature);
			log.info("Verifica firma n " + i);
			ValidationInfos validationInfos = signature.verify();
			log.info("validationInfos.isValid(): " + validationInfos.isValid());
			
//			// Controllo che il bit non repudation del keyusage sia a 1
//			boolean[] keyUsage = signature.getSignerBean().getCertificate().getKeyUsage();
//			if (keyUsage==null || !keyUsage[1]){
//				log.error(MessageHelper.getMessage( CertMessage.CERT_NONREPUDIATION ));
//				validationInfos.addErrorWithCode( CertMessage.CERT_NONREPUDIATION, MessageHelper.getMessage( CertMessage.CERT_NONREPUDIATION ) );
//				validationInfos.addError( MessageHelper.getMessage( CertMessage.CERT_NONREPUDIATION ) );
//			} else {
//				log.debug("keyUsage non ripudio a true");
//			}
			validationInfosMap.put(signature, validationInfos);
			result &= validationInfos.isValid();
			if (performCounterSignaturesCheck) {
				List<ISignature> counterSignatures = signature.getCounterSignatures();
				populateValidationInfosMapFromSignatureList(validationInfosMap, counterSignatures);
			}
			
		}
		return result;
	}
		
}
