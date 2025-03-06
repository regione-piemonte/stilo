package it.eng.utility.cryptosigner.controller.impl.timestamp.ws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.security.cert.CRL;
import java.security.cert.Certificate;
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

import org.bouncycastle.tsp.TimeStampToken;
import org.python.jline.internal.Log;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.ErrorBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.controller.impl.timestamp.AbstractTimeStampController;
import it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceProxy;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class TSARevocationWS extends AbstractTimeStampController {

	/**
	 * Propriet� restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String TSA_REVOCATION_CHECK = "performTSARevocation";

	private String endPoint;

	private CertificateRevocationServiceProxy certificateRevocationProxy = new CertificateRevocationServiceProxy();

	private CertificateFactory certificateFactory = null;

	public String getCheckProperty() {
		return TSA_REVOCATION_CHECK;
	}

	@Override
	public boolean execute(InputTimeStampBean input, OutputTimeStampBean output, boolean eseguiValidazioni) throws ExceptionController {

		// Recupero i timestamp
		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos = output.getDocumentAndTimeStampInfos();
		if (documentAndTimeStampInfos == null || documentAndTimeStampInfos.size() == 0)
			return false;

		if (this.endPoint != null)
			certificateRevocationProxy.setEndpoint(endPoint);

		boolean result = true;

		ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {

			ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
			TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();
			HashMap<String, Object> validityInfo = documentAndTimeStampInfo.getValidityInfo();
			Date timestampDate = timeStampToken.getTimeStampInfo().getGenTime();
			Object qualifiedCertificateObj = validityInfo.get(DocumentAndTimeStampInfoBean.PROP_QUALIFIED_CERTIFICATE);
			Collection<? extends Certificate> embeddedCertificates = output.getSigner().getEmbeddedCertificates( input.getContentFile());

			X509Certificate saX509Certificate = null;

			if (qualifiedCertificateObj != null) {

				Calendar calendar = new GregorianCalendar();
				calendar.setTime(timestampDate);

				try {

					saX509Certificate = (X509Certificate) qualifiedCertificateObj;
					// MODIFICA ANNA 1.53
					// Collection<CRL> embeddedCRLs = (Collection<CRL>)(timeStampToken.getCertificatesAndCRLs("Collection",
					// BouncyCastleProvider.PROVIDER_NAME).getCRLs(null));
					Collection<CRL> embeddedCRLs = (Collection<CRL>) (timeStampToken.toCMSSignedData().getCRLs());

					byte[] encodedCertificate = saX509Certificate.getEncoded();
					X509Certificate issuerCertificate = SignerUtil.getCertificateFromCollection(saX509Certificate.getIssuerDN(), embeddedCertificates);
					try {

						/*
						 * CHIAMATA AL WEB SERVICE
						 */
						if (issuerCertificate == null)
							validationInfos = certificateRevocationProxy.checkCertificateRevocationStatus(encodedCertificate, calendar);
						else {
							byte[] encodedIssuer = issuerCertificate.getEncoded();
							validationInfos = certificateRevocationProxy.checkCertificateAndIssuerRevocationStatus(encodedCertificate, encodedIssuer, calendar);
						}
					} catch (RemoteException e) {
						validationInfos = new ValidationInfos();
						validationInfos.addErrorWithCode(MessageConstants.GM_WSCALL_ERROR,
								MessageHelper.getMessage(MessageConstants.GM_WSCALL_ERROR, certificateRevocationProxy, e.getMessage()));
						validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_WSCALL_ERROR, certificateRevocationProxy, e.getMessage()));
					} catch (WSCryptoSignerException e) {

						if (e.getValidationInfos() != null) {
							validationInfos.addErrors(e.getValidationInfos().getErrors());
							for (ErrorBean error : e.getValidationInfos().getErrorsBean()) {
								validationInfos.addErrorWithCode(error.getCode(), error.getDescription());
							}
						}
						// Controllo se l'errore generato � dovuto alla scadenza del certificato di certificazione
						if (e.getErrorCode() == WSCryptoSignerException.CRL_DISTRIBUTION_POINT_UNREACHABLE) {
							byte[] encodedReliableCertificate = e.getAttachedSerializedObject();

							// Controllo se � stato possibile recuperare un certificato di certificazione
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
											Log.warn("IOException", e2);
										}
									// Si è verificato un errore durante la
									// deserializzazione del certificato di certificazione
								}
							}

							// Controllo se è disponibile il certificato di certificazione
							if (issuerCertificate != null) {

								// Il certificato di certificazione è scaduto
								// e il distribution point è irraggiungibile,
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
												if (x509EmbeddedCRL.getNextUpdate().after(timestampDate)) {

													checkCRL(validationInfos, saX509Certificate, (X509CRL) embeddedCRL, timestampDate);

													// Tengo traccia che almeno una CRL nella busta è relativa
													// al certificato dell'issuer
													existsEmbeddedCRLReferredToIssuer |= true;
												}

											} catch (Exception e2) {

												// Una CRL nella busta non è relativa al certificato dell'issuer
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
							} else {
								validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_NO_ISSUER_CERTIFICATE_WARNING));
								validationInfos.addWarningWithCode(CertMessage.CERT_CRL_NO_ISSUER_CERTIFICATE_WARNING,
										MessageHelper.getMessage(CertMessage.CERT_CRL_NO_ISSUER_CERTIFICATE_WARNING));
							}

						}
					}
				} catch (Exception e) {
					validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
					validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
							MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
				}
			}
		}

		// TODO Auto-generated method stub
		return true;
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
