package it.eng.utility.cryptosigner.controller.impl.signature.ws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.tsp.TimeStampToken;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.controller.impl.signature.AbstractSignerController;
import it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService;
import it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceProxy;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class CertificateRevocationWS extends AbstractSignerController {

	private String endPoint;

	private CertificateRevocationServiceProxy certificateRevocationProxy = new CertificateRevocationServiceProxy();

	private CertificateFactory certificateFactory = null;

	@Override
	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		if (this.endPoint != null)
			certificateRevocationProxy.setEndpoint(endPoint);

		boolean result = true;

		DocumentAndTimeStampInfoBean timeStampInfo = input.getDocumentAndTimeStampInfo();

		Date referenceDate = input.getReferenceDate();
		if (referenceDate == null)
			referenceDate = Calendar.getInstance().getTime();

		// recupero il timestamptoken
		// TimeStampToken timeStampToken = timeStampInfo== null ? null : timeStampInfo.getTimeStampToken();
		// Collection<? extends Certificate> embeddedCertificates = input.getSigner().getEmbeddedCertificates();
		// Collection<CRL> embeddedCRLs = input.getSigner().getEmbeddedCRLs();
		// CRL inputCRL = input.getCrl();

		Map<ISignature, ValidationInfos> validationInfosMap = new HashMap<ISignature, ValidationInfos>();

		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			List<ISignature> signatures = (List<ISignature>) output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			// result = populateValidationInfosMapFromSignaturesAndTimeStamp(validationInfosMap, signatures, timeStampToken, embeddedCertificates, embeddedCRLs,
			// inputCRL);
			if (eseguiValidazioni)
				result = populateValidationInfosMapFromInputOutput(validationInfosMap, input, output, signatures, referenceDate);

			// Aggiungo le informazioni all'outputBean
			output.setProperty(OutputSignerBean.CRL_VALIDATION_PROPERTY, validationInfosMap);

		}

		return result;
	}

	// private boolean populateValidationInfosMapFromSignaturesAndTimeStamp(
	// Map<ISignature, ValidationInfos> validationInfosMap,
	// List<ISignature> signatures, TimeStampToken timeStampToken,
	// Collection<? extends Certificate> embeddedCertificates,
	// Collection<CRL> embeddedCRLs,
	// CRL inputCRL) {
	private boolean populateValidationInfosMapFromInputOutput(Map<ISignature, ValidationInfos> validationInfosMap, InputSignerBean input,
			OutputSignerBean output, List<ISignature> signatures, Date referenceDate) {

		Map<ISignature, ValidationInfos> expiredCertificates = (Map<ISignature, ValidationInfos>) output
				.getProperty(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY);
		Collection<CRL> embeddedCRLs = input.getSigner().getEmbeddedCRLs( input.getEnvelope() );
		CRL inputCRL = input.getCrl();
		Collection<? extends Certificate> embeddedCertificates = input.getSigner().getEmbeddedCertificates( input.getEnvelope() );
		DocumentAndTimeStampInfoBean timeStampInfo = input.getDocumentAndTimeStampInfo();

		// recupero il timestamptoken
		TimeStampToken timeStampToken = timeStampInfo == null ? null : timeStampInfo.getTimeStampToken();

		CertificateRevocationService certificateRevocation = certificateRevocationProxy.getCertificateRevocationService();

		for (ISignature signature : signatures) {

			ValidationInfos validationInfos = new ValidationInfos();

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(referenceDate);

			/*
			 * Verifico se il certificato era già scaduto alla data di riferimento temporale
			 */
			ValidationInfos certificateExpirationInfo = expiredCertificates.get(signature);
			if (!certificateExpirationInfo.isValid()) {
				validationInfos.addErrorWithCode(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED, MessageHelper.getMessage(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED));
			}

			X509Certificate signatureCertificate = signature.getSignerBean().getCertificate();

			/*
			 * Recupero le codifiche del certificato e timestamp
			 */
			try {

				byte[] encodedCertificate = signatureCertificate.getEncoded();
				X509Certificate issuerCertificate = SignerUtil.getCertificateFromCollection(signatureCertificate.getIssuerDN(), embeddedCertificates);
				try {

					/*
					 * CHIAMATA AL WEB SERVICE
					 */
					if (issuerCertificate == null)
						validationInfos = certificateRevocation.checkCertificateRevocationStatus(encodedCertificate, calendar);
					else {
						byte[] encodedIssuer = issuerCertificate.getEncoded();
						validationInfos = certificateRevocation.checkCertificateAndIssuerRevocationStatus(encodedCertificate, encodedIssuer, calendar);
					}

				} catch (RemoteException e) {
					validationInfos = new ValidationInfos();
					validationInfos.addErrorWithCode(MessageConstants.GM_WSCALL_ERROR,
							MessageHelper.getMessage(MessageConstants.GM_WSCALL_ERROR, certificateRevocationProxy, e.getMessage()));
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_WSCALL_ERROR, certificateRevocationProxy, e.getMessage()));
				} catch (WSCryptoSignerException e) {
					validationInfos = new ValidationInfos();

					// Controllo se l'errore generato è dovuto alla scadenza del certificato di certificazione
					if (e.getErrorCode() == WSCryptoSignerException.CRL_DISTRIBUTION_POINT_UNREACHABLE) {

						byte[] encodedReliableCertificate = e.getAttachedSerializedObject();

						// Controllo se è stato possibile recuperare un certificato di certificazione
						// dallo storage delle CA
						if (encodedReliableCertificate == null) {
							InputStream certificateIS = new ByteArrayInputStream(encodedReliableCertificate);
							try {
								if (certificateFactory == null)
									certificateFactory = CertificateFactory.getInstance("X.509");
								issuerCertificate = (X509Certificate) certificateFactory.generateCertificate(certificateIS);
							} catch (Exception e1) {
								if (certificateIS != null)
									try {
										certificateIS.close();
									} catch (IOException e2) {
									}
								// Si è verificato un errore durante la
								// deserializzazione del certificato di certificazione
							}
						}

						// Controllo se è disponibile il certificato di certificazione
						if (issuerCertificate != null) {

							// Il certificato di certificazione � scaduto
							// e il distribution point � irraggiungibile,
							// verifico sulle CRL della busta
							// Date referenceDate = timeStampToken.getTimeStampInfo().getGenTime();
							// if (referenceDate==null)
							// referenceDate = new Date();

							boolean existsEmbeddedCRLReferredToIssuer = false;
							if (embeddedCRLs != null && embeddedCRLs.size() != 0) {

								for (CRL embeddedCRL : embeddedCRLs) {
									if (embeddedCRL instanceof X509CRL) {
										X509CRL x509EmbeddedCRL = (X509CRL) embeddedCRL;

										// Verifico che la CRL sia relativa al certificato dell'issuer
										try {
											x509EmbeddedCRL.verify(issuerCertificate.getPublicKey());

											// Verifico che la CRL della busta abbia una data di
											// validità successiva al riferimento temporale
											if (x509EmbeddedCRL.getNextUpdate().after(referenceDate)) {

												checkCRL(validationInfos, signatureCertificate, (X509CRL) embeddedCRL, referenceDate);

												// Tengo traccia che almeno una CRL nella busta è relativa
												// al certificato dell'issuer
												existsEmbeddedCRLReferredToIssuer |= true;
											}

										} catch (Exception e2) {

											// Una CRL nella busta non � relativa al certificato dell'issuer
											existsEmbeddedCRLReferredToIssuer |= false;
										}

									}
								}

								if (!existsEmbeddedCRLReferredToIssuer) {
									validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VALIDATION_WARNING, embeddedCRLs.size()));
									validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VALIDATION_WARNING,
											MessageHelper.getMessage(CertMessage.CERT_CRL_VALIDATION_WARNING, embeddedCRLs.size()));
								}
							}

							// Non sono presenti CRL nella busta oppure nessuna delle presenti è valida
							// verifico rispetto alla CRL in input
							if (embeddedCRLs == null || !existsEmbeddedCRLReferredToIssuer) {
								if (inputCRL instanceof X509CRL) {
									X509CRL x509InputCRL = (X509CRL) inputCRL;
									try {
										x509InputCRL.verify(issuerCertificate.getPublicKey());
										if (x509InputCRL.getNextUpdate().after(referenceDate)) {
											checkCRL(validationInfos, signatureCertificate, x509InputCRL, referenceDate);

										} else {
											validationInfos.addErrorWithCode(CertMessage.CERT_CRL_VALIDATION_DATE,
													MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND, referenceDate));
											validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND, referenceDate));
										}

									} catch (Exception e2) {
										validationInfos.addErrorWithCode(CertMessage.CERT_CRL_ISSUER_CERTIFICATE,
												MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE));
										validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE));
									}
								}

							}
						}

						else {
							validationInfos.addErrorWithCode(CertMessage.CERT_CRL_NOT_FOUND, MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND));
							validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND));
						}
					}

					// Se non si tratta di un errore dovuto alla scadenza del certificato di certificazione
					// recupero il messaggio di errore originario
					else {
						validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
						validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
								MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
					}
				}

			} catch (CertificateEncodingException e1) {
				validationInfos.addErrorWithCode(CertMessage.CERT_SERIALIZATION_ERROR,
						MessageHelper.getMessage(CertMessage.CERT_SERIALIZATION_ERROR, e1.getMessage()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_SERIALIZATION_ERROR, e1.getMessage()));
			}

			validationInfosMap.put(signature, validationInfos);
		}

		return true;
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

	private void checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);

		// il certificato � stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				validationInfos.addErrorWithCode(CertMessage.CERT_REVOKED_BEFORE,
						MessageHelper.getMessage(CertMessage.CERT_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
			} else if (date == null) {
				validationInfos.addErrorWithCode(CertMessage.CERT_REVOKED_AFTER,
						MessageHelper.getMessage(CertMessage.CERT_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_REVOKED_AFTER, crlEntry.getRevocationDate()));
			}
		}
	}
}
