package it.eng.utility.cryptosigner.data.signature;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLValidateContext;

import org.apache.log4j.Logger;

import es.mityc.firmaJava.libreria.xades.DatosFirma;
import es.mityc.firmaJava.libreria.xades.ResultadoEnum;
import es.mityc.firmaJava.libreria.xades.ResultadoValidacion;
import it.eng.utility.cryptosigner.bean.SignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.utils.MessageConstants;

/**
 * Implementa una firma digitale di tipo XML (utilizzata nel formato XAdES)
 * 
 * @author Stefano Zennaro
 */
public class XaDESSignature implements ISignature {

	public static final Logger log = Logger.getLogger(XaDESSignature.class);

	private XMLSignature signature;
	private X509Certificate certificate;
	private XMLValidateContext validateContext;
	private List<ISignature> counterSignatures;
	private ResultadoValidacion validationResult;

	public XaDESSignature(XMLSignature signature, XMLValidateContext validateContext, X509Certificate certificate) {
		this.signature = signature;
		this.validateContext = validateContext;
		this.certificate = certificate;
	}

	public byte[] getSignatureBytes() {
		return signature.getSignatureValue().getValue();
	}

	public SignerBean getSignerBean() {
		SignerBean signerBean = new SignerBean();
		signerBean.setCertificate(this.certificate);
		if (this.certificate != null) {
			signerBean.setIusser(this.certificate.getIssuerX500Principal());
			signerBean.setSubject(this.certificate.getSubjectX500Principal());
		}
		DatosFirma dati = getValidationResult().getDatosFirma();
		Date signingTime = dati.getFechaFirma();
		if (signingTime != null) {
			signerBean.setSigningTime(signingTime);
		}

		return signerBean;
	}

	public ValidationInfos verify() {
		ValidationInfos validationInfos = new ValidationInfos();

		try {

			log.info("signature.getSignatureValue() " + signature.getSignatureValue());
			signature.getSignatureValue().validate(validateContext);

			ResultadoEnum esitoValidazione = getValidationResult().getResultado();
			log.info("esitoValidazione " + esitoValidazione);
			// if( esitoValidazione!=null && !esitoValidazione.equals(ResultadoEnum.VALID)){
			if (esitoValidazione != null && esitoValidazione.equals(ResultadoEnum.INVALID)) {
				validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR, getValidationResult().getLog());
			}

		} catch (javax.xml.crypto.dsig.XMLSignatureException e) {
			log.error("Errore verify", e);
			validationInfos.addError("Firma non valida");
			// validationInfos.addError( MessageHelper.getMessage( MessageConstants.GM_GENERIC_ERROR, e.getMessage() ) );
			// validationInfos.addErrorWithCode( MessageConstants.GM_GENERIC_ERROR, MessageHelper.getMessage( MessageConstants.GM_GENERIC_ERROR, e.getMessage()
			// ));
			validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR, "Firma non valida");
		}
		return validationInfos;
	}

	public XMLSignature getSignature() {
		return signature;
	}

	public void setSignature(XMLSignature signature) {
		this.signature = signature;
	}

	public void setCounterSignatures(List<ISignature> counterSignatures) {
		this.counterSignatures = counterSignatures;
	}

	public List<ISignature> getCounterSignatures() {
		return counterSignatures;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public void setValidateContext(XMLValidateContext validateContext) {
		this.validateContext = validateContext;
	}

	public ResultadoValidacion getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(ResultadoValidacion validationResult) {
		this.validationResult = validationResult;
	}

}
