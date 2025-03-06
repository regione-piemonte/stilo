package it.eng.utility.cryptosigner.controller.impl.signature;

import java.security.Principal;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Prende in considerazione come riferimento temporale il timestamp in input (se presente) oppure la data attuale. Verifica la validita dei certificati di firma
 * rispetto
 * <ol>
 * <li>Alle CRL recuperate dalla firma digitale (se presenti)</li>
 * <li>Alla CRL scaricata dal distribution point indicato nella busta e il controllo prende in considerazione solamente le CRL valide al riferimento temporale,
 * presenti tra gli attributi firmati di ciascuna firma</li>
 * <li>Alla CRL fornita in input e deve essere valida rispetto al riferimento temporale</li>
 * <li>Alla CRL storicizzata al momento del riferimento temporale</li>
 * </ol>
 * 
 * @author Stefano Zennaro
 */
public class CertificateRevocation extends AbstractSignerController {

	public static final String CERTIFICATE_REVOCATION_CHECK = "performCertificateRevocation";

	public static final Logger log = LogManager.getLogger(CertificateRevocation.class);

	public String getCheckProperty() {
		return CERTIFICATE_REVOCATION_CHECK;
	}

	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		boolean result = true;

		Date referenceDate = input.getReferenceDate();
		if (referenceDate == null)
			referenceDate = Calendar.getInstance().getTime();

		/*
		 * TODO: vecchia implementazione da rimuovere
		 */
		// DocumentAndTimeStampInfoBean timeStampInfo= input.getDocumentAndTimeStampInfo();
		// // recupero il riferimento temporale dal timestamptoken
		// Date referenceDate = null;
		// try {
		// referenceDate = timeStampInfo.getTimeStampToken().getTimeStampInfo().getGenTime();
		// }catch(Exception e) {
		// referenceDate = Calendar.getInstance().getTime();
		// }

		Map<ISignature, ValidationInfos> validationInfosMap = new HashMap<ISignature, ValidationInfos>();

		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			List<ISignature> signatures = (List<ISignature>) output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			if (eseguiValidazioni)
				result = populateValidationInfosMapFromInputOutput(validationInfosMap, input, output, signatures, referenceDate);
			// Aggiungo le informazioni all'outputBean
			output.setProperty(OutputSignerBean.CRL_VALIDATION_PROPERTY, validationInfosMap);

		}
		return result;
	}

	private boolean populateValidationInfosMapFromInputOutput(Map<ISignature, ValidationInfos> validationInfosMap, InputSignerBean input,
			OutputSignerBean output, List<ISignature> signatures, Date referenceDate) {

		Map<ISignature, ValidationInfos> expiredCertificates = (Map<ISignature, ValidationInfos>) output
				.getProperty(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY);
		Map<ISignature, X509Certificate> certificateReliabilityMap = (Map<ISignature, X509Certificate>) output
				.getProperty(OutputSignerBean.CERTIFICATE_RELIABILITY_PROPERTY);
		Collection<CRL> embeddedCRLs = input.getSigner().getEmbeddedCRLs(input.getEnvelope());
		CRL inputCRL = input.getCrl();
		Collection<? extends Certificate> embeddedCertificates = input.getSigner().getEmbeddedCertificates(input.getEnvelope());

		boolean result = true;

		// Storage delle CRL
		ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();

		if (signatures != null){
			int numFirma = 1;
			for (ISignature signature : signatures) {
				log.debug("Verifica della crl per la firma numero " + numFirma++);
				
				ValidationInfos validationInfos = new ValidationInfos();

				/*
				 * Verifico se il certificato era gia scaduto alla data di riferimento temporale
				 */
				ValidationInfos certificateExpirationInfo = expiredCertificates.get(signature);
				if (!certificateExpirationInfo.isValid()) {
					log.error(MessageHelper.getMessage(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED));
					validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED), true);
					validationInfos.addErrorWithCode(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED,
							MessageHelper.getMessage(CertMessage.CERT_CRL_CERTIFICATE_EXPIRED), true);
				}

				else {
					X509Certificate signatureCertificate = signature.getSignerBean().getCertificate();
					
					if( signatureCertificate!=null ){
						Principal issuerDN = signatureCertificate.getIssuerDN();
						X500Principal issuerPrincipal = signatureCertificate.getIssuerX500Principal();
						
						/*
						 * Recupero la CRL dell'issuer del certificato (chi ha emesso il certificato)
						 */
	
						X509CRL historicalCRL = null;
						try {
							log.debug("Verifico se esiste la CRL storica della CA che ha emesso il certificato di firma " + issuerDN.getName() );
							historicalCRL = crlStorage.retriveCRL(signatureCertificate);
							//historicalCRL = crlStorage.retriveCRL(issuerDN.toString());
						} catch (CryptoStorageException e) {
							// Si e verificato un errore durante il recupero della CRL storicizzata
							log.error("CryptoStorageException", e);
	
						}
						// Verifico se la data di prossimo aggiornamento della CRL e >= della
						// data del riferimento temporale
						boolean isQualified;
						if( historicalCRL== null){
							log.debug("La crl storicizzata non esiste, va scaricata");
						}
						
						if (historicalCRL != null && historicalCRL.getNextUpdate().after(referenceDate)) {
							log.debug("Data di prossimo aggiornamento della CRL " + historicalCRL.getNextUpdate());
							isQualified = checkCRL(validationInfos, signatureCertificate, historicalCRL, referenceDate);
							log.debug("Verifica CRL " + isQualified);
						} else {
	
							log.debug("La CRL storica non e' stato trovata oppure il suo periodo di validite non e' applicabile");
							if (historicalCRL != null ) {
								log.debug("Data di prossimo aggiornamento della CRL " + historicalCRL.getNextUpdate() + " - la CRL non e' aggiornata e va scaricata nuovamente");
							}
							// Se la CRL storica non e stato trovata oppure
							// se il suo periodo di validite non e applicabile
							// cerco di scaricare la CRL dal distribution point
							try {
								log.debug("Cerco di scaricare la CRL");
								Vector<String> urlCRLDistributionPoints = null;
								try {
									urlCRLDistributionPoints = signerUtil.getURLCrlDistributionPoint(signatureCertificate);
								} catch (CryptoSignerException e) {
									// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
									// tengo traccia dell'errore ma considero il certificato comunque accreditato
									// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
									validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
									validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
											MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
									//log.info("Scarico CRL END");
									throw new CryptoSignerException(e.getMessage());
								} catch (Exception e) {
									// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
									// tengo traccia dell'errore ma considero il certificato comunque accreditato
									// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
									validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
									validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
											MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
									//log.info("Scarico CRL END");
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
												
												X509Certificate qualifiedCertificate = null;
												String caCountry = null;
												String caServiceProviderName = null;
												String caServiceName = null;
												try {
													ICAStorage certificatesAuthorityStorage= FactorySigner.getInstanceCAStorage();
													CACertificate caCertBean = certificatesAuthorityStorage.retriveValidCA(signatureCertificate, false);
													qualifiedCertificate = caCertBean.getCertificate();
													caCountry = caCertBean.getCountry();
													caServiceProviderName = caCertBean.getServiceProviderName();
													caServiceName = caCertBean.getServiceName();
													log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
													
													// La CRL deve essere storicizzata
													log.debug("Storicizzo la crl");
													crlStorage.insertCRL(envelopeCrl, caCountry, caServiceProviderName, caServiceName );
												} catch (CryptoStorageException e) {
													log.error("Errore nell'insermento nella cartella locale della CRL: " + e.getMessage());
												}
												
												isQualified = checkCRL(validationInfos, signatureCertificate, envelopeCrl, referenceDate);
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
										log.info("Scarico CRL END");
										throw new CryptoSignerException(e.getMessage());
									}
								}
	
								if (contaCRLScaricate != urlCRLDistributionPoints.size()) {
									log.warn("Abbiamo scaricato " + contaCRLScaricate + " CRL" + " a fronte di " + urlCRLDistributionPoints.size()
											+ " punti di distribuzione.");
	
									X509Certificate issuerCertificate = null;
									if (certificateReliabilityMap != null) {
										issuerCertificate = certificateReliabilityMap.get(signature);
									} else {
										log.info("vertificateREliabilityMap nulla");
									}
									if (issuerCertificate == null) {
										issuerCertificate = SignerUtil.getCertificateFromCollection(issuerDN, embeddedCertificates);
									}
	
									if (issuerCertificate != null) {
										try {
											issuerCertificate.checkValidity();
											// Se il certificato di certificazione e ancora valido
											// doveva essere possibile scaricare la CRL,
											// poiche cio non e avvenuto, restituisco un errore
											validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE), true);
											validationInfos.addErrorWithCode(CertMessage.CERT_CRL_NOT_DOWNLOADABLE,
													MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE), true);
										} catch (CertificateNotYetValidException e1) {
											validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION), true);
											validationInfos.addErrorWithCode(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION,
													MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION), true);
										} catch (CertificateExpiredException e) {
											// Il certificato di certificazione e scaduto
											// verifico sulle CRL della busta
											log.info("CertificateExpiredException", e);
										}
									}
								}
								log.info("Scarico CRL END");
	
							} catch (CryptoSignerException e) {
								// Se si e verificato un errore durante lo scaricamento
								// dal distribution point, oppure questo non e indicato nella busta
								// si verifica se il certificato dell'issuer e ancora valido
								// TODO vertificateREliabilityMap può essere nullo se non fai il controllo di reliability
								X509Certificate issuerCertificate = null;
								if (certificateReliabilityMap != null) {
									issuerCertificate = certificateReliabilityMap.get(signature);
								} else {
									log.debug("vertificateReliabilityMap nulla");
								}
								if (issuerCertificate == null) {
									issuerCertificate = SignerUtil.getCertificateFromCollection(issuerDN, embeddedCertificates);
								}
	
								if (issuerCertificate != null) {
									try {
										issuerCertificate.checkValidity();
										// Se il certificato di certificazione e ancora valido
										// doveva essere possibile scaricare la CRL,
										// poiche cio non e avvenuto, restituisco un errore
										validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE), true);
										validationInfos.addErrorWithCode(CertMessage.CERT_CRL_NOT_DOWNLOADABLE,
												MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE), true);
									} catch (CertificateExpiredException e1) {
	
										// Il certificato di certificazione e scaduto
										// verifico sulle CRL della busta
										boolean existsEmbeddedCRLReferredToIssuer = false;
										if (embeddedCRLs != null && embeddedCRLs.size() != 0) {
	
											for (CRL embeddedCRL : embeddedCRLs) {
												if (embeddedCRL instanceof X509CRL) {
													X509CRL x509EmbeddedCRL = (X509CRL) embeddedCRL;
	
													// Verifico che la CRL sia relativa al certificato dell'issuer
													try {
														x509EmbeddedCRL.verify(issuerCertificate.getPublicKey());
	
														// Verifico che la CRL della busta abbia una data di
														// validita successiva al riferimento temporale
														if (x509EmbeddedCRL.getNextUpdate().after(referenceDate)) {
	
															checkCRL(validationInfos, signatureCertificate, (X509CRL) embeddedCRL, referenceDate);
	
															// Tengo traccia che almeno una CRL nella busta e relativa
															// al certificato dell'issuer
															existsEmbeddedCRLReferredToIssuer |= true;
	
															// Se e valida si puo storicizzare
	//														try {
	//															crlStorage.insertCRL(x509EmbeddedCRL);
	//														} catch (CryptoStorageException e2) {
	//															log.warn("CryptoStorageException ", e2);
	//														}
														}
	
													} catch (Exception e2) {
	
														// Una CRL nella busta non e relativa al certificato dell'issuer
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
	
										// Non sono presenti CRL nella busta oppure nessuna delle presenti e valida
										// verifico rispetto alla CRL in input
										if (embeddedCRLs == null || embeddedCRLs.size() == 0 || !existsEmbeddedCRLReferredToIssuer) {
											if (inputCRL instanceof X509CRL) {
												X509CRL x509InputCRL = (X509CRL) inputCRL;
												try {
													x509InputCRL.verify(issuerCertificate.getPublicKey());
													if (x509InputCRL.getNextUpdate().after(referenceDate)) {
														checkCRL(validationInfos, signatureCertificate, x509InputCRL, referenceDate);
	
														// Se e valida si puo storicizzare
	//													try {
	//														crlStorage.insertCRL(x509InputCRL);
	//													} catch (CryptoStorageException e2) {
	//														log.warn("CryptoStorageException ", e2);
	//													}
													} else {
														validationInfos.addErrorWithCode(CertMessage.CERT_CRL_VALIDATION_DATE,
																MessageHelper.getMessage(CertMessage.CERT_CRL_VALIDATION_DATE, referenceDate));
														validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_VALIDATION_DATE, referenceDate));
													}
	
												} catch (Exception e2) {
													validationInfos.addErrorWithCode(CertMessage.CERT_CRL_ISSUER_CERTIFICATE,
															MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE));
													validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE));
												}
											} else {
												validationInfos.addErrorWithCode(CertMessage.CERT_CRL_WITHOUT_CERTIFICATE,
														MessageHelper.getMessage(CertMessage.CERT_CRL_WITHOUT_CERTIFICATE), true);
												validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_WITHOUT_CERTIFICATE), true);
											}
										}
	
									} catch (CertificateNotYetValidException e1) {
										validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION), true);
										validationInfos.addErrorWithCode(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION,
												MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION), true);
									}
	
								} else {
									validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND), true);
									validationInfos.addErrorWithCode(CertMessage.CERT_CRL_NOT_FOUND, MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND),
											true);
								}
	
							}
	
						}
					} else {
						
						log.error(MessageHelper.getMessage(CertMessage.CERT_NOTFOUND));
						validationInfos.addErrorWithCode(CertMessage.CERT_NOTFOUND,
								MessageHelper.getMessage(CertMessage.CERT_NOTFOUND));
						validationInfos
								.addError(MessageHelper.getMessage(CertMessage.CERT_NOTFOUND));
					}
				}

				// Aggiungo il risultato della validazione della CRL della firma
				validationInfosMap.put(signature, validationInfos);

				result &= validationInfos.isValid();

				if (performCounterSignaturesCheck) {
					List<ISignature> counterSignatures = signature.getCounterSignatures();
					result &= populateValidationInfosMapFromInputOutput(validationInfosMap, input, output, counterSignatures, referenceDate);
				}
			}
			}
		return result;
	}

	private boolean checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);

		// il certificato e stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addErrorWithCode(CertMessage.CERT_CA_CRL_REVOKED_BEFORE,
						MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				return false;
			} else if (date == null) {
				validationInfos.addErrorWithCode(CertMessage.CERT_CA_CRL_REVOKED_AFTER,
						MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				return false;
			}
		} else {
			log.debug("Il certificato non presente nella CRL, il certificato NON e' revocato");
		}
		return true;
	}
}
