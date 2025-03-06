package it.eng.utility.cryptosigner.controller.impl.timestamp;

import java.security.Principal;
import java.security.cert.CRL;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;
import it.eng.utility.cryptosigner.utils.MessageHelper;

public class TSARevocation extends AbstractTimeStampController {

	private static Logger log = LogManager.getLogger(TSARevocation.class);

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String TSA_REVOCATION_CHECK = "performTSARevocation";

	public String getCheckProperty() {
		return TSA_REVOCATION_CHECK;
	}

	@Override
	public boolean execute(InputTimeStampBean input, OutputTimeStampBean output, boolean eseguiValidazioni) throws ExceptionController {

		// Recupero i timestamp
		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos = output.getDocumentAndTimeStampInfos();
		if (documentAndTimeStampInfos == null || documentAndTimeStampInfos.size() == 0){
			log.debug("Nessuna marca presente, esito esecuzione controllo false");
			return false;
		}

		boolean result = true;

		log.info("getCheckProperty TSA_REVOCATION_CHECK " + getCheckProperty() );
		
		ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();
		log.debug("Sono presenti " + documentAndTimeStampInfos.size() + " marche");
		try {
			for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {

				ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
				TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();
				HashMap<String, Object> validityInfo = documentAndTimeStampInfo.getValidityInfo();
				Date timestampDate = timeStampToken.getTimeStampInfo().getGenTime();
				Object qualifiedCertificateObj = validityInfo.get(DocumentAndTimeStampInfoBean.PROP_QUALIFIED_CERTIFICATE);

				X509Certificate saX509Certificate = null;

				if (qualifiedCertificateObj != null) {
					saX509Certificate = (X509Certificate) qualifiedCertificateObj;
					// MODIFICA ANNA 1.53
					// Collection<CRL> embeddedCRLs = (Collection<CRL>)(timeStampToken.getCertificatesAndCRLs("Collection",
					// BouncyCastleProvider.PROVIDER_NAME).getCRLs(null));
					Store embeddedCRLsStore = timeStampToken.toCMSSignedData().getCRLs();
					Collection<CRL> embeddedCRLs = embeddedCRLsStore.getMatches(null);

					/*
					 * controllo che il certificato non faccia parte della CRL indicata dalla TSA
					 */
					// Storage delle CRL
					ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();
					Principal issuerDN = saX509Certificate.getIssuerDN();
					X509CRL historicalCRL = null;
					try {
						log.debug("Cerco la crl dell'issuer della tsa " + issuerDN.getName() );
						//historicalCRL = crlStorage.retriveCRL(issuerDN.toString());
						historicalCRL = crlStorage.retriveCRL( saX509Certificate );
					} catch (CryptoStorageException e) {
						// Si e verificato un errore durante il recupero della CRL storicizzata
						log.error("Errore ", e);
					}

					// Verifico se la data di prossimo aggiornamento della CRL e >= della
					// data del riferimento temporale
					log.debug("Verifico se la CRL storica esiste e se il suo periodo di validite e' applicabile");
					boolean isQualified;
					if (historicalCRL != null && historicalCRL.getNextUpdate().after(timestampDate)){
						log.debug("data di prossimo aggiornamento della CRL " + historicalCRL.getNextUpdate());
						log.debug("Verifico la crl storica");
						isQualified = checkCRL(validationInfos, saX509Certificate, historicalCRL, timestampDate);
						log.debug("Verifica CRL " + isQualified);
					} else {

						log.debug("La CRL storica non e' stato trovata oppure il suo periodo di validite non e' applicabile");
						if (historicalCRL != null && historicalCRL.getNextUpdate().after(timestampDate)) {
							log.debug("data di prossimo aggiornamento della CRL " + historicalCRL.getNextUpdate());
						}
						
						// Se la CRL storica non e stato trovata oppure
						// se il suo periodo di validita non e applicabile
						// cerco di scaricare la CRL dal distribution point
						try {
							log.debug(" cerco di scaricare la CRL");
							Vector<String> urlCRLDistributionPoints = null;
							try {
								urlCRLDistributionPoints = signerUtil.getURLCrlDistributionPoint(saX509Certificate);
							} catch (CryptoSignerException e) {
								// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
								// tengo traccia dell'errore ma considero il certificato comunque accreditato
								// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
								validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
								validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
										MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
								throw new CryptoSignerException(e.getMessage());
							} catch (Exception e) {
								// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
								// tengo traccia dell'errore ma considero il certificato comunque accreditato
								// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
								validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
								validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
										MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
								throw new CryptoSignerException(e.getMessage());
							}
							log.info("Questo certificato ha: " + urlCRLDistributionPoints.size() + " punti di distribuzione");
							int contaCRLScaricate = 0;
							for (int i = 0; i < urlCRLDistributionPoints.size(); i++) {
								boolean ciclaCRL = true;
								try {
									while (ciclaCRL) {
										String urlCRLDistributionPoint = urlCRLDistributionPoints.get(i);

										log.info("Si esegue lo scarico della CRL per il seguente punto di distribuzione: " + urlCRLDistributionPoint);

										X509CRL envelopeCrl = null;
										if (urlCRLDistributionPoint != null)
											envelopeCrl = signerUtil.getCrlByURL(urlCRLDistributionPoint);

										if (envelopeCrl != null) {
											contaCRLScaricate++;
											
											// La CRL deve essere storicizzata
											try {
												
												X509Certificate qualifiedCertificate = null;
												String caCountry = null;
												String caServiceProviderName = null;
												String caServiceName = null;
												
												CACertificate caCertBean = certificatesAuthorityStorage.retriveValidTSA(saX509Certificate, false);
												qualifiedCertificate = caCertBean.getCertificate();
												caCountry = caCertBean.getCountry();
												caServiceProviderName = caCertBean.getServiceProviderName();
												caServiceName = caCertBean.getServiceName();
												log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
												
												log.debug("Storicizzo la crl");
												crlStorage.insertCRL(envelopeCrl, caCountry, caServiceProviderName, caServiceName );
											} catch (CryptoStorageException e) {
												log.error("Errore nell'insermento nella cartella locale della CRL: " + e.getMessage());
											}
											
											isQualified = checkCRL(validationInfos, saX509Certificate, envelopeCrl, timestampDate);
											log.debug("Verifica CRL " + isQualified);
											
										}
										ciclaCRL = false;
									}
								} catch (Exception e) {
									// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
									// tengo traccia dell'errore ma considero il certificato comunque accreditato
									// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
									validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
									validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
											MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
									throw new CryptoSignerException(e.getMessage());
								}

							}

							if (contaCRLScaricate != urlCRLDistributionPoints.size()) {
								log.warn("Abbiamo scaricato " + contaCRLScaricate + " CRL" + " a fronte di " + urlCRLDistributionPoints.size()
										+ " punti di distribuzione.");
							}

						} catch (CryptoSignerException e) {

							// Se si e verificato un errore durante lo scaricamento
							// dal distribution point, oppure questo non e indicato nella busta

							// se l'ente di certificazione del timestamp ha un issuer (diverso da se stesso)
							// recupero il certificato dell'issuer e lo valido rispetto al riferimento temporale
							// (non serve validarlo rispetto alle CRL perche se fosse stato revocato
							// dovrebbe essere stato revocato anche il certificato della TSA - a cascata)
							X500Principal issuerPrincipal = saX509Certificate.getIssuerX500Principal();
							X509Certificate issuerCertificate = null;
							if (!saX509Certificate.getSubjectX500Principal().equals(issuerPrincipal)) {
								issuerCertificate = certificatesAuthorityStorage.retriveCA(saX509Certificate, false);
							} else {
								issuerCertificate = saX509Certificate;
							}

							if (issuerCertificate != null) {
								try {
									issuerCertificate.checkValidity();

									// Se il certificato di certificazione e ancora valido
									// doveva essere possibile scaricare la CRL,
									// poiche cio non e avvenuto, restituisco un errore
									validationInfos.addErrorWithCode(CertMessage.CERT_CRL_NOT_DOWNLOADABLE,
											MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE));
									validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE));
									result = false;
								} catch (CertificateExpiredException e1) {

									// Il certificato di certificazione e scaduto
									// verifico sulle CRL della busta
									boolean existsEmbeddedCRLReferredToIssuer = false;
									if (embeddedCRLs != null) {

										for (CRL embeddedCRL : embeddedCRLs) {
											if (embeddedCRL instanceof X509CRL) {
												X509CRL x509EmbeddedCRL = (X509CRL) embeddedCRL;

												// Verifico che la CRL sia relativa al certificato dell'issuer
												try {
													x509EmbeddedCRL.verify(issuerCertificate.getPublicKey());

													// Verifico che la CRL della busta abbia una data di
													// validita successiva al riferimento temporale
													if (x509EmbeddedCRL.getNextUpdate().after(timestampDate)) {

														checkCRL(validationInfos, saX509Certificate, (X509CRL) embeddedCRL, timestampDate);

														// Tengo traccia che almeno una CRL nella busta e relativa
														// al certificato dell'issuer
														existsEmbeddedCRLReferredToIssuer |= true;

														// Se e valida si puo storicizzare
//														try {
//															crlStorage.insertCRL(x509EmbeddedCRL);
//														} catch (CryptoStorageException e2) {
//															log.warn("CryptoStorageException", e2);
//														}
													}

												} catch (Exception e2) {

													// Una CRL nella busta non e relativa al certificato dell'issuer
													existsEmbeddedCRLReferredToIssuer |= false;
												}

											}
										}

										if (!existsEmbeddedCRLReferredToIssuer)
											validationInfos.addWarning("CRL non verificabile: nella busta sono presenti " + embeddedCRLs.size()
													+ " CRL ma nessuna e valida rispetto al certificato dell'issuer");
									}
								}
							} else {
								validationInfos.addWarning("CRL non verificabile: non e stato possibile reperire il certificato dell'issuer");
							}
						}
					}
				} else {
					log.debug("Property " + DocumentAndTimeStampInfoBean.PROP_QUALIFIED_CERTIFICATE + " non presente ");
				}
			}
		} catch (Exception e) {
			throw new ExceptionController(e);
		}

		return result;
	}

	private boolean checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);
		// il certificato e stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addErrorWithCode(CertMessage.CERT_REVOKED_BEFORE,
						MessageHelper.getMessage(CertMessage.CERT_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				return false;
			} else if (date == null){
				log.debug(MessageHelper.getMessage(CertMessage.CERT_REVOKED_AFTER, crlEntry.getRevocationDate(), date));
				validationInfos.addErrorWithCode(CertMessage.CERT_REVOKED_AFTER,
						MessageHelper.getMessage(CertMessage.CERT_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_REVOKED_AFTER, crlEntry.getRevocationDate()));
				return false;
			}
		} else {
			log.debug("certificato non presente nella CRL");
		}
		return true;
	}

}
