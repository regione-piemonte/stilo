package it.eng.utility.cryptosigner.controller.impl.signature;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;

import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.utils.MessageHelper;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Verifica la corretta corrispondenza tra il certificato dell'issuer
 * e quello del firmatario
 * @author Stefano Zennaro
 *
 */
public class CertificateAssociation extends AbstractSignerController{

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String CERTIFICATE_ASSOCIATION_CHECK = "performCertificateAssociation";
	
	private static Logger log = LogManager.getLogger( CertificateAssociation.class);
			
	public String getCheckProperty() {
		return CERTIFICATE_ASSOCIATION_CHECK;
	}
	
	
	@Override
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni)
			throws ExceptionController {
		
		List<ISignature> signatures = null;
		Map<ISignature, ValidationInfos> validationInfosMap = new HashMap<ISignature, ValidationInfos>();
		Map<ISignature, X509Certificate> certificateReliabilityMap = null;
		
		boolean result = true;
		
		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			signatures = (List<ISignature>)output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			certificateReliabilityMap = (Map<ISignature, X509Certificate>)output.getProperty(OutputSignerBean.CERTIFICATE_RELIABILITY_PROPERTY);
			if(eseguiValidazioni) 
				result = populateValidationInfosMapFromSignatureList(validationInfosMap, signatures, input, certificateReliabilityMap);
			output.setProperty(OutputSignerBean.CERTIFICATE_VALIDATION_PROPERTY, validationInfosMap);
		}
		return result;
	}

	private boolean populateValidationInfosMapFromSignatureList(
			Map<ISignature, ValidationInfos> validationInfosMap,
			List<ISignature> signatures, InputSignerBean input, Map<ISignature, X509Certificate> certificateReliabilityMap) {
		boolean result = true;
		if( signatures != null )
		for (ISignature signature: signatures) {
			
			// Certificato del firmatario
			X509Certificate signatureCertificate = signature.getSignerBean().getCertificate();
			
			X509Certificate issuerCertificate = null;
			
			// Recupero il certificato accreditato (se presente)
			if (certificateReliabilityMap!=null)
				issuerCertificate = certificateReliabilityMap.get(signature);
			
			// se il certificato non e accreditato
			if (issuerCertificate==null) {
				Collection<?extends Certificate> embeddedCertificates = input.getSigner().getEmbeddedCertificates(input.getEnvelope());
				if (embeddedCertificates != null)
					issuerCertificate = SignerUtil.getCertificateFromCollection(signatureCertificate.getIssuerX500Principal(), embeddedCertificates);
			}
			
			ValidationInfos validationInfos = new ValidationInfos();
			
			// Se non e stato possibile reperire il certificato dell'issuer
			// restituisco un errore
			if (issuerCertificate == null) {
				validationInfos.addWarning( MessageHelper.getMessage( CertMessage.CERT_NO_CERTIFICATE_WARNING ));
				validationInfos.addWarningWithCode( CertMessage.CERT_NO_CERTIFICATE_WARNING, MessageHelper.getMessage( CertMessage.CERT_NO_CERTIFICATE_WARNING ));
				result = false;
			}
			
			else{
				try {
					signatureCertificate.verify(issuerCertificate.getPublicKey());					
				} catch (Exception e) {
					validationInfos.addErrorWithCode( CertMessage.CERT_ASSOCIATION_ISSUER, MessageHelper.getMessage( CertMessage.CERT_ASSOCIATION_ISSUER ) );
					validationInfos.addError( MessageHelper.getMessage( CertMessage.CERT_ASSOCIATION_ISSUER ) );
					result = false;
				}
			}
			validationInfosMap.put(signature, validationInfos);
			
			if (performCounterSignaturesCheck) {
				List<ISignature> counterSignatures = signature.getCounterSignatures();
				result = populateValidationInfosMapFromSignatureList(validationInfosMap, counterSignatures, input, certificateReliabilityMap);
			}
		}
		return result;
	}
	
}
