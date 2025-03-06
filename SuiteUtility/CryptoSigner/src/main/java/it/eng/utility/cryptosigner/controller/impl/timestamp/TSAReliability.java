package it.eng.utility.cryptosigner.controller.impl.timestamp;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class TSAReliability extends AbstractTimeStampController {

	private static Logger log = LogManager.getLogger(TSAReliability.class);

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String TSA_RELIABILITY_CHECK = "performTSAReliability";

	public String getCheckProperty() {
		return TSA_RELIABILITY_CHECK;
	}

	@Override
	public boolean execute(InputTimeStampBean input, OutputTimeStampBean output, boolean eseguiValidazioni) throws ExceptionController {

		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos = output.getDocumentAndTimeStampInfos();
		if (documentAndTimeStampInfos == null || documentAndTimeStampInfos.size() == 0){
			log.debug("Nessuna marca presente, esito esecuzione controllo false");
			return false;
		}

		log.info("getCheckProperty TSA_RELIABILITY_CHECK " + getCheckProperty() );
		boolean result = true;
		
		log.debug("Sono presenti " + documentAndTimeStampInfos.size() + " marche");
		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {

			//marca 
			TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();
			ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
			HashMap<String, Object> validityInfo = documentAndTimeStampInfo.getValidityInfo();
			Date timestampDate = (Date) validityInfo.get(DocumentAndTimeStampInfoBean.PROP_DATE);
			
			// Seriale identificativo della TSA
			BigInteger tsaSerial = timeStampToken.getSID().getSerialNumber();
			log.info("Numero seriale: " + tsaSerial);
			
			Boolean isCertificateInList = false;
			//Collection<CRL> embeddedCRLs = null;
			// Collection<Certificate> saCertificates = null;
			Collection<X509CertificateHolder> saCertificates = null;

			try {

				// MODIFICA ANNA 1.53
				// CertStore certStore = timeStampToken.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
				Store certStore = timeStampToken.toCMSSignedData().getCertificates();
				// saCertificates = (Collection<Certificate>)certStore.getCertificates(null);
				saCertificates = certStore.getMatches(null);
				// embeddedCRLs = (Collection<CRL>)certStore.getCRLs(null);
				//Store embeddedCRLsStore = timeStampToken.toCMSSignedData().getCRLs();
				//embeddedCRLs = embeddedCRLsStore.getMatches(null);

				// Controllo se il certificato di firma e attendibile
				ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

				for (X509CertificateHolder saCertificateHolder : saCertificates) {
					CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

					// if (saCertificate instanceof X509Certificate) {
					if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
						log.info("-----aggiungo il provider ");
						Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
					}
					
					// ricavo il certificato della tsa che ha generato la marca
					X509Certificate saX509Certificate = (X509Certificate) certFactory
							.generateCertificate(new ByteArrayInputStream(saCertificateHolder.getEncoded()));
					log.info("Certificato TSA: " + saX509Certificate);
					
					// X509Certificate saX509Certificate = (X509Certificate) saCertificate;
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
						log.info("TSA subject Name = " + saX509Certificate.getSubjectX500Principal());
						documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_SID, saX509Certificate.getSubjectX500Principal().getName());

						// FIXME: da cambiare!!
						X509Certificate qualifiedCertificate = null;
						String tsaCountry = null;
						String tsaServiceProviderName = null;
						String tsaServiceName = null;
						CACertificate tsaCertBean = certificatesAuthorityStorage.retriveValidTSA(saX509Certificate, true);
						log.info("tsaCertBean " + tsaCertBean);
						qualifiedCertificate = tsaCertBean.getCertificate();
						tsaCountry = tsaCertBean.getCountry();
						tsaServiceProviderName = tsaCertBean.getServiceProviderName();
						tsaServiceName = tsaCertBean.getServiceName();
						log.debug("tsaCountry " + tsaCountry + " tsaServiceProviderName " + tsaServiceProviderName+ " tsaServiceName " + tsaServiceName );
						
						if (qualifiedCertificate == null) {

							// Se il soggetto non e' accreditato, provo con l'issuer
							log.debug("il subject non e' accreditato, provo con l'issuer " + saX509Certificate.getIssuerX500Principal());
							CACertificate tsaIssuerCertBean = certificatesAuthorityStorage.retriveValidTSA(saX509Certificate, false);
							X509Certificate issuerCertificate = tsaIssuerCertBean.getCertificate();
							//log.info("-- issuerCertificate " + issuerCertificate);
							if (issuerCertificate == null) {
								log.debug("Certificato per l'issuer della tsa non trovato");
								isCertificateInList = false;
								validationInfos.addErrorWithCode(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID,
										MessageHelper.getMessage(CertMessage.SIGN_CERTIFICATE_TSA_NOTVALID));
								validationInfos.addError(MessageHelper.getMessage(CertMessage.SIGN_CERTIFICATE_TSA_NOTVALID));
							}
							// Si considera qualificato il certificato se emesso da un issuer accreditato
							else {
								try {
									saX509Certificate.verify(issuerCertificate.getPublicKey());
									qualifiedCertificate = saX509Certificate;
									log.debug("TSA valida");
								} catch (Exception e) {
									validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_ISSUER_NOTVALID,
											MessageHelper.getMessage(CertMessage.SIGN_TSA_ISSUER_NOTVALID));
									validationInfos.addError(MessageHelper.getMessage(CertMessage.SIGN_TSA_ISSUER_NOTVALID));
								}
							}

						}

						if (qualifiedCertificate != null) {
							PublicKey publicKey = qualifiedCertificate.getPublicKey();
							if (org.bouncycastle.util.Arrays.constantTimeAreEqual(saX509Certificate.getPublicKey().getEncoded(), publicKey.getEncoded())) {
								
								log.debug("TSA accreditata individuata. Controllo se e' in data di validita'");
								log.debug("certificato TSA - data scadenza " + qualifiedCertificate.getNotAfter());
								log.debug("certificato TSA - data attivazione " + qualifiedCertificate.getNotBefore());
								
								/*
								 * Verifico la data di scadenza temporale del certificato indicato nello storage
								 */
								if (timestampDate.after(qualifiedCertificate.getNotAfter())) {
									validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_DATE_AFTER,
											MessageHelper.getMessage(CertMessage.SIGN_TSA_DATE_AFTER, timestampDate, qualifiedCertificate.getNotAfter()));
									validationInfos.addError(
											MessageHelper.getMessage(CertMessage.SIGN_TSA_DATE_AFTER, timestampDate, qualifiedCertificate.getNotAfter()));
									log.error("Errore: " + MessageHelper.getMessage(CertMessage.SIGN_TSA_DATE_AFTER));
									isCertificateInList = false;
									result = false;
								} else if (timestampDate.before(qualifiedCertificate.getNotBefore())) {
									validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_DATE_BEFORE,
											MessageHelper.getMessage(CertMessage.SIGN_TSA_DATE_BEFORE, timestampDate, qualifiedCertificate.getNotBefore()));
									validationInfos.addError(
											MessageHelper.getMessage(CertMessage.SIGN_TSA_DATE_BEFORE, timestampDate, qualifiedCertificate.getNotBefore()));
									log.error("Errore: " + MessageHelper.getMessage(CertMessage.SIGN_TSA_DATE_BEFORE));
									isCertificateInList = false;
									result = false;
								} else {
									isCertificateInList = true;
									documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_QUALIFIED_CERTIFICATE, qualifiedCertificate);
								}
							} else {
								log.error("Errore: " + MessageHelper.getMessage(CertMessage.SIGN_TSA_ERROR_STORAGE));
								validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_ERROR_STORAGE,
										MessageHelper.getMessage(CertMessage.SIGN_TSA_ERROR_STORAGE));
								validationInfos.addError(MessageHelper.getMessage(CertMessage.SIGN_TSA_ERROR_STORAGE));
								isCertificateInList = false;
								result = false;
							}

						}
					}

					// }
				}
			} catch (Exception e) {
				log.warn("Eccezione TSAReliability", e);
				return false;
			}
			documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_RECOGNIZED_CERTIFICATE, isCertificateInList.toString());
		}

		return result;
	}

}
