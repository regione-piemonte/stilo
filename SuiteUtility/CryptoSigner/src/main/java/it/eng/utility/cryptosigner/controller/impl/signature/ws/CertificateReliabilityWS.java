package it.eng.utility.cryptosigner.controller.impl.signature.ws;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.x500.X500Principal;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.signature.AbstractSignerController;
import it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceProxy;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class CertificateReliabilityWS extends AbstractSignerController {

	private String endPoint;

	private CertificateReliabilityServiceProxy certificateReliabilityProxy = new CertificateReliabilityServiceProxy();

	private CertificateFactory certificateFactory = null;

	/**
	 * Propriet� restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String CERTIFICATE_RELIABILITY_CHECK = "performCertificateReliability";

	public String getCheckProperty() {
		return CERTIFICATE_RELIABILITY_CHECK;
	}

	// I passi per verificare la correttezza del certificato sono i seguenti:
	// - recupero la firma del certificato
	// - recupero l'issuer del certificato
	// - guardo se nella lista dei certificatori accreditati � presente l'issuer (stesso DN)
	// - se � presente trovo la chiave pubblica
	// - sbusto la firma con la chiave pubblica e verifico il risultato con digest del certificato

	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		if (this.endPoint != null)
			certificateReliabilityProxy.setEndpoint(endPoint);

		boolean result = true;

		// try {
		Date referenceDate = input.getReferenceDate();
		if (referenceDate == null)
			referenceDate = new Date();
		Calendar referenceCalendar = Calendar.getInstance();
		referenceCalendar.setTime(referenceDate);

		Map<ISignature, ValidationInfos> unqualifiedSignatureValidationInfos = new HashMap<ISignature, ValidationInfos>();

		Map<ISignature, X509Certificate> issuerQualifiedCertificates = new HashMap<ISignature, X509Certificate>();
		ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

		// Firme
		List<ISignature> signatures = null;
		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			signatures = (List<ISignature>) output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			if (eseguiValidazioni)
				result = populateUnqualifiedSignaturesList(unqualifiedSignatureValidationInfos, issuerQualifiedCertificates, signatures,
						certificatesAuthorityStorage, referenceCalendar);

			// Popolo la lista delle firme con certificato non accreditato
			output.setProperty(OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY, unqualifiedSignatureValidationInfos);

			// Popolo la lista dei certificati accreditati
			output.setProperty(OutputSignerBean.CERTIFICATE_RELIABILITY_PROPERTY, issuerQualifiedCertificates);
		}

		// } catch (CryptoSignerException e) {
		// throw new ExceptionController(e);
		// }
		return result;
	}

	public boolean populateUnqualifiedSignaturesList(Map<ISignature, ValidationInfos> unqualifiedSignatureValidationInfos,
			Map<ISignature, X509Certificate> issuerQualifiedCertificates, List<ISignature> signatures, ICAStorage certificatesAuthorityStorage,
			Calendar referenceCalendar) {

		boolean result = true;

		for (ISignature signature : signatures) {

			X509Certificate signatureCertificate = signature.getSignerBean().getCertificate();
			X500Principal issuerPrincipal = signatureCertificate.getIssuerX500Principal();

			// Controllo se il certificato attuale � contenuto tra quelli accreditati
			Certificate qualifiedCertificate = null;
			byte[] encodedQualifiedCertificate = null;
			InputStream qualifiedCertificateIS = null;
			ValidationInfos validationInfos = new ValidationInfos();

			try {
				if (certificateFactory == null)
					certificateFactory = CertificateFactory.getInstance("X.509");
				encodedQualifiedCertificate = certificateReliabilityProxy.checkReliableCertificate(issuerPrincipal.getEncoded(), referenceCalendar);
			} catch (CertificateException e1) {
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e1.getMessage()));
				validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
						MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e1.getMessage()));
			} catch (RemoteException e) {
				// Si � verificato un errore durante il recupero del certificato accreditato,
				// questo non pu� essere considerato attendibile
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
				validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
						MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			} catch (WSCryptoSignerException e) {
				if (e.getValidationInfos() != null)
					validationInfos = e.getValidationInfos();
				else {
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
					validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
							MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
				}
			}

			if (encodedQualifiedCertificate != null) {
				qualifiedCertificateIS = new ByteArrayInputStream(encodedQualifiedCertificate);
				try {
					qualifiedCertificate = certificateFactory.generateCertificate(qualifiedCertificateIS);
				} catch (CertificateException e) {
				}
			}

			if (qualifiedCertificate == null || !(qualifiedCertificate instanceof X509Certificate)) {
				unqualifiedSignatureValidationInfos.put(signature, validationInfos);
			} else {
				// issuerQualifiedCertificates.put(signature, (X509Certificate)qualifiedCertificate);

				PublicKey publicKey = qualifiedCertificate.getPublicKey();

				try {
					signatureCertificate.verify(publicKey);

					// Aggiungo il certificato del firmatario a quelli accreditati
					if (qualifiedCertificate instanceof X509Certificate)
						issuerQualifiedCertificates.put(signature, (X509Certificate) qualifiedCertificate);

				} catch (Exception e) {
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
					validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
							MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
					unqualifiedSignatureValidationInfos.put(signature, validationInfos);
				}
			}

			result &= validationInfos.isValid();

			if (performCounterSignaturesCheck) {
				List<ISignature> counterSignatures = signature.getCounterSignatures();
				result &= populateUnqualifiedSignaturesList(unqualifiedSignatureValidationInfos, issuerQualifiedCertificates, counterSignatures,
						certificatesAuthorityStorage, referenceCalendar);
			}

		}
		// TODO Auto-generated method stub
		return result;
	}

	/**
	 * @return the endPoint
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint
	 *            the endPoint to set
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
}
