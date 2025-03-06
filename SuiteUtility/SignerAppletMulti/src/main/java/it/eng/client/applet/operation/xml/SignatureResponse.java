package it.eng.client.applet.operation.xml;

import it.eng.common.type.SignerType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Classe per la verifica della firma di un documento
 * @author Giobo
 *
 */
public class SignatureResponse {

	private byte[] signedDocumentBytes;

	private SignerType signatureType;
	private List<SignatureCertificateResponse> certificates = new ArrayList<SignatureCertificateResponse>();
	private SignatureValidity valid;

	public SignatureResponse(SignerType signatureType) {
		this.signatureType = signatureType;
	}

	public List<SignatureCertificateResponse> getCertificates() {
		//return Collections.unmodifiableList(certificates);  
		return certificates;  
	}

	public SignerType getSignatureType() {
		return signatureType;
	}

	/**
	 * Verifica che tutte le firme siano valide. <br>
	 * <u>Attenzione</u>: il metodo non verifica la validit� dei certificati, ma solo che le firme siano state apposte dai
	 * con le chiavi private dei certificati presenti
	 * @return
	 */
	public SignatureValidity getSignatureValidity() {
		if (valid == null) {
			boolean valid = true;
			for (SignatureCertificateResponse certificateResponse : certificates) {
				valid &= certificateResponse.isValid();
			}

			this.valid = valid ? SignatureValidity.VALID : SignatureValidity.INVALID;
		}
		return valid;
	}


	public InputStream getSignedDocument() {
		if (signedDocumentBytes != null) {
			return new ByteArrayInputStream(signedDocumentBytes);
		}

		return null;
	}

	@Override
	public String toString() {
		if (certificates.size() > 0) {
			return "{" +  certificates.get(0).getCertificate().getSubjectDN().toString()+" - " + valid + "}";
		} else {
			return "{Nessun certificato presente}";
		}
	}

	public SignatureValidity getValid() {
		return valid;
	}

	public void setValid(SignatureValidity valid) {
		this.valid = valid;
	}

	public void setCertificates(List<SignatureCertificateResponse> certificates) {
		this.certificates = certificates;
	}

}
