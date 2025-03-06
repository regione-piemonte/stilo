package it.eng.utility.cryptosigner.controller.impl.timestamp.ws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.ErrorBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.timestamp.AbstractTimeStampController;
import it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceProxy;
import it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class TSAReliabilityWS extends AbstractTimeStampController {

	private String endPoint;

	private static Logger log = Logger.getLogger(TSAReliabilityWS.class);

	private CertificateReliabilityServiceProxy certificateReliabilityProxy = new CertificateReliabilityServiceProxy();

	private CertificateFactory certificateFactory = null;

	/**
	 * Propriet� restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String TSA_RELIABILITY_CHECK = "performTSAReliability";

	public String getCheckProperty() {
		return TSA_RELIABILITY_CHECK;
	}

	@Override
	public boolean execute(InputTimeStampBean input, OutputTimeStampBean output, boolean eseguiValidazioni) throws ExceptionController {

		if (this.endPoint != null)
			certificateReliabilityProxy.setEndpoint(endPoint);

		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos = output.getDocumentAndTimeStampInfos();
		if (documentAndTimeStampInfos == null || documentAndTimeStampInfos.size() == 0)
			return false;

		boolean result = true;

		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {

			TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();
			ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
			HashMap<String, Object> validityInfo = documentAndTimeStampInfo.getValidityInfo();
			Date timestampDate = (Date) validityInfo.get(DocumentAndTimeStampInfoBean.PROP_DATE);
			try {
				if (certificateFactory == null)
					certificateFactory = CertificateFactory.getInstance("X.509");

				Calendar referenceDate = new GregorianCalendar();
				referenceDate.setTime(timestampDate);

				// Seriale identificativo della TSA
				BigInteger tsaSerial = timeStampToken.getSID().getSerialNumber();
				Boolean isCertificateInList = false;
				Collection<CRL> embeddedCRLs = null;
				Collection<Certificate> saCertificates = null;

				// MODIFICA ANNA 1.53
				// CertStore certStore = timeStampToken.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
				Store certStore = timeStampToken.toCMSSignedData().getCertificates();
				// saCertificates = (Collection<Certificate>)certStore.getCertificates(null);
				saCertificates = certStore.getMatches(null);
				// embeddedCRLs = (Collection<CRL>)certStore.getCRLs(null);
				embeddedCRLs = (Collection<CRL>) timeStampToken.toCMSSignedData().getCRLs();

				for (Certificate saCertificate : saCertificates) {

					if (saCertificate instanceof X509Certificate) {

						X509Certificate saX509Certificate = (X509Certificate) saCertificate;
						// Controllo se il certificato corrisponde a quello della TSA
						if (saX509Certificate.getSerialNumber().equals(tsaSerial)) {

							// # various interpretations of the RDN fields exist
							// # the following are presented as generally accepted
							// # values. In the case of personal certificates bizarre values
							// # can appear in the fields
							// # C = ISO3166 two character country code
							// # ST = state or province
							// # L = Locality; generally means city
							// # O = Organization - Company Name
							// # OU = Organization Unit - division or unit
							// # CN = CommonName - entity name e.g. www.example.com
							documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_SID, saX509Certificate.getSubjectX500Principal().getName());

							// Controllo se il certificato attuale � contenuto tra quelli accreditati
							X509Certificate qualifiedCertificate = null;
							byte[] encodedQualifiedCertificate = null;
							InputStream qualifiedCertificateIS = null;

							try {
								encodedQualifiedCertificate = certificateReliabilityProxy
										.checkReliableCertificate(saX509Certificate.getSubjectX500Principal().getEncoded(), referenceDate);
							} catch (RemoteException e) {
								// Si � verificato un errore durante il recupero del certificato accreditato,
								// questo non pu� essere considerato attendibile
								validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
								validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
										MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
							} catch (WSCryptoSignerException e) {
								if (e.getValidationInfos() != null) {
									validationInfos.addErrors(e.getValidationInfos().getErrors());
									for (ErrorBean error : e.getValidationInfos().getErrorsBean()) {
										validationInfos.addErrorWithCode(error.getCode(), error.getDescription());
									}
								} else {
									validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
									validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
											MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
								}
							}

							if (encodedQualifiedCertificate != null) {
								qualifiedCertificateIS = new ByteArrayInputStream(encodedQualifiedCertificate);
								try {
									qualifiedCertificate = (X509Certificate) certificateFactory.generateCertificate(qualifiedCertificateIS);
								} catch (CertificateException e) {
									log.warn("CertificateException", e);
								} finally {
									if (qualifiedCertificateIS != null) {
										try {
											qualifiedCertificateIS.close();
										} catch (IOException e) {
											log.warn("IOException", e);
										}
									}
								}
							}

							// Se il soggetto non � accreditato, prova con l'issuer
							if (qualifiedCertificate == null) {

								// Controllo se il certificato dell'issuer � contenuto tra quelli accreditati
								byte[] encodedIssuerCertificate = null;
								InputStream issuerCertificateIS = null;
								Certificate issuerCertificate = null;

								encodedIssuerCertificate = certificateReliabilityProxy
										.checkReliableCertificate(saX509Certificate.getIssuerX500Principal().getEncoded(), referenceDate);

								if (encodedIssuerCertificate != null) {
									issuerCertificateIS = new ByteArrayInputStream(encodedIssuerCertificate);
									try {
										issuerCertificate = certificateFactory.generateCertificate(issuerCertificateIS);
									} catch (CertificateException e) {
										log.warn("CertificateException", e);
									} finally {
										if (qualifiedCertificateIS != null) {
											qualifiedCertificateIS.close();
										}
									}
								}

								log.info("---- issuerCertificate " + issuerCertificate);
								if (issuerCertificate == null) {
									isCertificateInList = false;
									validationInfos.addErrorWithCode(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID,
											MessageHelper.getMessage(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID));
									validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID));
								}
								// Si considera qualificato il certificato se emesso da un issuer accreditato
								else {
									try {
										saX509Certificate.verify(issuerCertificate.getPublicKey());
										qualifiedCertificate = saX509Certificate;
									} catch (Exception e) {
										validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_ISSUER_NOTVALID,
												MessageHelper.getMessage(MessageConstants.SIGN_TSA_ISSUER_NOTVALID));
										validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TSA_ISSUER_NOTVALID));
									}
								}

							}

							if (qualifiedCertificate != null) {
								PublicKey publicKey = qualifiedCertificate.getPublicKey();
								if (org.bouncycastle.util.Arrays.constantTimeAreEqual(saCertificate.getPublicKey().getEncoded(), publicKey.getEncoded())) {
									/*
									 * Verifico la data di scadenza temporale del certificato indicato nello storage
									 */
									if (timestampDate.after(qualifiedCertificate.getNotAfter())) {
										validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TSA_DATE_AFTER, timestampDate,
												qualifiedCertificate.getNotAfter()));
										validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_DATE_AFTER, MessageHelper
												.getMessage(MessageConstants.SIGN_TSA_DATE_AFTER, timestampDate, qualifiedCertificate.getNotAfter()));
										isCertificateInList = false;
										result = false;
									} else if (timestampDate.before(qualifiedCertificate.getNotBefore())) {
										validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TSA_DATE_BEFORE, timestampDate,
												qualifiedCertificate.getNotBefore()));
										validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_DATE_BEFORE, MessageHelper
												.getMessage(MessageConstants.SIGN_TSA_DATE_BEFORE, timestampDate, qualifiedCertificate.getNotBefore()));
										isCertificateInList = false;
										result = false;
									} else {
										isCertificateInList = true;
										documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_QUALIFIED_CERTIFICATE, qualifiedCertificate);
									}
								} else {
									validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_ERROR_STORAGE,
											MessageHelper.getMessage(MessageConstants.SIGN_TSA_ERROR_STORAGE));
									validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TSA_ERROR_STORAGE));
									isCertificateInList = false;
									result = false;
								}

							}
						}
					}

				}

				documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_RECOGNIZED_CERTIFICATE, isCertificateInList.toString());

			} catch (Exception e) {
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
				validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR,
						MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			}

		}

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
