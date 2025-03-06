package it.eng.utility.cryptosigner.controller.impl.cert;

import java.security.Principal;
import java.security.cert.CRL;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.apache.log4j.Logger;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.InputCertBean;
import it.eng.utility.cryptosigner.controller.bean.OutputCertBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
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
 */
public class CertificateRevocation extends AbstractCertController {

	public static final String CERTIFICATE_REVOCATION_CHECK = "performCertificateRevocation";
	private static final Logger log = Logger.getLogger(CertificateRevocation.class);

	public String getCheckProperty() {
		return CERTIFICATE_REVOCATION_CHECK;
	}

	public boolean execute(InputCertBean input, OutputCertBean output, boolean eseguiValidazioni) throws ExceptionController {

		boolean result = true;
		if (eseguiValidazioni)
			result = populateValidationInfosMapFromInputOutput(input, output);
		return result;
	}

	private boolean populateValidationInfosMapFromInputOutput(InputCertBean input, OutputCertBean output) {
		ValidationInfos validationInfos = new ValidationInfos();
		boolean result = true;
		if (input != null && input.getCertificate() != null) {
			// se sono stati impostati sfrutto i risultati del controllo precedente
			ValidationInfos certificateExpirationInfo = (ValidationInfos) output.getProperty(OutputCertBean.CERTIFICATE_EXPIRATION);
			X509Certificate certificateReliability = (X509Certificate) output.getProperty(OutputCertBean.CERTIFICATE_RELIABILITY_PROPERTY);

			Collection<CRL> embeddedCRLs = null;// input.getSigner().getEmbeddedCRLs();
			CRL inputCRL = input.getCrl();
			// Collection<? extends Certificate> embeddedCertificates = input.getSigner().getEmbeddedCertificates();

			X509Certificate certificate = input.getCertificate();
			Date referenceDate = input.getReferenceDate();
			if (referenceDate == null)
				referenceDate = Calendar.getInstance().getTime();
			// Storage delle CRL
			ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();

			/*
			 * Verifico se il certificato era gia scaduto alla data di riferimento temporale usando i dati del controllo precedente, in tal caso non effettuo il
			 * controllo successivo TODO non si deve fare il controllo anche per il certificato della CA per vedere se è valido temporalmente poichè se fallisce
			 * quello del cert è inutile poichè si fa' quello del
			 */
			// skip this chek to execute in anyway
			if (false && certificateExpirationInfo != null && !certificateExpirationInfo.isValid()) {
				validationInfos.addErrorWithCode(CertMessage.CERT_EXPIRED, MessageHelper.getMessage(CertMessage.CERT_EXPIRED));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_EXPIRED));
			}

			else {
				X509Certificate signatureCertificate = certificate;
				// FIXME la crl dell'issuer è sempre la stessa di quello del cert?
				Principal issuerDN = signatureCertificate.getIssuerDN();
				X500Principal issuerPrincipal = signatureCertificate.getIssuerX500Principal();
				/*
				 * Recupero la CRL dell'issuer del certificato
				 */

				X509CRL historicalCRL = null;
				try {
					historicalCRL = crlStorage.retriveCRL( signatureCertificate);
				} catch (CryptoStorageException e) {
					log.debug("errore durante il recupro della CRL locale", e);
					// Si e verificato un errore durante il recupero della CRL storicizzata

				}
				// Verifico se la data di prossimo aggiornamento della CRL e >= della
				// data del riferimento temporale
				if (historicalCRL != null && historicalCRL.getNextUpdate().after(referenceDate)) {
					log.debug(" uso  CRL storicizzata");
					checkCRL(validationInfos, signatureCertificate, historicalCRL, referenceDate);
				} else {

					// Se la CRL storica non e stato trovata oppure
					// se il suo periodo di validite non e applicabile
					// cerco di scaricare la CRL dal distribution point
					try {
						log.debug(" cerco di scaricare la CRL");
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
										checkCRL(validationInfos, signatureCertificate, envelopeCrl, referenceDate);

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
											
											log.debug("Storicizzo la CRL");
											crlStorage.insertCRL(envelopeCrl, caCountry, caServiceProviderName, caServiceName);
										} catch (CryptoStorageException e) {
											log.error("Errore nell'insermento nella cartella locale della CRL: " + e.getMessage());
										}
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
						// TODO rendere questi check configurabili
						// Se si e verificato un errore durante lo scaricamento
						// dal distribution point, oppure questo non e indicato nel certificato
						// si verifica se il certificato dell'issuer e ancora valido

						// a parte il caso della CRL forninta in input gestito più avanti
						// se siamo in questa eccezione non siamo risuciti a scaricare la CRL e quindi a fare il
						// controllo
						// validationInfos.setCannotEecute(true);
						X509Certificate issuerCertificate = certificateReliability;
						if (issuerCertificate == null) {
							// TODO da RIVEDERE e CHIEDERE
							// issuerCertificate = SignerUtil.getCertificateFromCollection(issuerDN, embeddedCertificates);
							// provo a recuperare il certificato dell'issuer dal repository locale
							ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

							try {
								issuerCertificate = certificatesAuthorityStorage.retriveCA(signatureCertificate, false);
							} catch (CryptoStorageException e1) {
								log.info("il certificato non è contenuto nella lista attuale di quelli attendibili", e1);
							}
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
								// validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_DOWNLOADABLE),true);
							} catch (CertificateExpiredException e1) {

								// Il certificato di certificazione e scaduto
								// verifico sulle CRL della busta
								boolean existsEmbeddedCRLReferredToIssuer = false;
								// if (embeddedCRLs!=null && embeddedCRLs.size()!=0) {
								//
								// for (CRL embeddedCRL: embeddedCRLs) {
								// if (embeddedCRL instanceof X509CRL) {
								// X509CRL x509EmbeddedCRL = (X509CRL) embeddedCRL;
								//
								// // Verifico che la CRL sia relativa al certificato dell'issuer
								// try {
								// x509EmbeddedCRL.verify(issuerCertificate.getPublicKey());
								//
								// // Verifico che la CRL della busta abbia una data di
								// // validita successiva al riferimento temporale
								// if (x509EmbeddedCRL.getNextUpdate().after(referenceDate)) {
								//
								// checkCRL(validationInfos, signatureCertificate, (X509CRL)embeddedCRL, referenceDate);
								//
								// // Tengo traccia che almeno una CRL nella busta e relativa
								// // al certificato dell'issuer
								// existsEmbeddedCRLReferredToIssuer |= true;
								//
								// // Se e valida si puo storicizzare
								// try {
								// crlStorage.insertCRL(x509EmbeddedCRL);
								// }catch(CryptoStorageException e2) {
								// e2.printStackTrace();
								// }
								// }
								//
								// } catch (Exception e2) {
								//
								// // Una CRL nella busta non e relativa al certificato dell'issuer
								// existsEmbeddedCRLReferredToIssuer |= false;
								// }
								//
								// }
								// }
								//
								// if (!existsEmbeddedCRLReferredToIssuer)
								// validationInfos.addWarning("Nella busta sono presenti " + embeddedCRLs.size() + " CRL ma nessuna e valida rispetto al
								// certificato dell'issuer");
								// }

								// Non sono presenti CRL nella busta oppure nessuna delle presenti e valida
								// verifico rispetto alla CRL in input TODO possibilità di passaare le CRL come input!?
								if (embeddedCRLs == null || embeddedCRLs.size() == 0 || !existsEmbeddedCRLReferredToIssuer) {
									if (inputCRL instanceof X509CRL) {
										X509CRL x509InputCRL = (X509CRL) inputCRL;
										try {
											x509InputCRL.verify(issuerCertificate.getPublicKey());
											if (x509InputCRL.getNextUpdate().after(referenceDate)) {
												checkCRL(validationInfos, signatureCertificate, x509InputCRL, referenceDate);

												// Se e valida si puo storicizzare
//												try {
//													crlStorage.insertCRL(x509InputCRL);
//												} catch (CryptoStorageException e2) {
//													log.info("CryptoStorageException ", e);
//												}
												// validationInfos.setCannotEecute(false);
											} else {
												validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_VALIDATION_DATE, referenceDate), true);
												validationInfos.addErrorWithCode(CertMessage.CERT_CRL_VALIDATION_DATE,
														MessageHelper.getMessage(CertMessage.CERT_CRL_VALIDATION_DATE, referenceDate), true);
											}

										} catch (Exception e2) {
											validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE), true);
											validationInfos.addErrorWithCode(CertMessage.CERT_CRL_ISSUER_CERTIFICATE,
													MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE), true);
										}
									} else {
										validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_WITHOUT_CERTIFICATE), true);
										validationInfos.addErrorWithCode(CertMessage.CERT_CRL_WITHOUT_CERTIFICATE,
												MessageHelper.getMessage(CertMessage.CERT_CRL_WITHOUT_CERTIFICATE), true);
									}
								}

							} catch (CertificateNotYetValidException e1) {
								validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION), true);
								validationInfos.addErrorWithCode(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION,
										MessageHelper.getMessage(CertMessage.CERT_CRL_ISSUER_CERTIFICATE_VALIDATION), true);
							}

						} else {
							validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND), true);
							validationInfos.addErrorWithCode(CertMessage.CERT_CRL_NOT_FOUND, MessageHelper.getMessage(CertMessage.CERT_CRL_NOT_FOUND), true);
						}

					}

				}

			}
		} else {
			validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_INPUTEMPTY), true);
			validationInfos.addErrorWithCode(CertMessage.CERT_INPUTEMPTY, MessageHelper.getMessage(CertMessage.CERT_INPUTEMPTY), true);
		}
		// Aggiungo il risultato della validazione della CRL della firma
		output.setProperty(OutputCertBean.CRL_VERIFY, validationInfos);

		result &= validationInfos.isValid();

		// if (performCounterSignaturesCheck) {
		// List<ISignature> counterSignatures = signature.getCounterSignatures();
		// result &= populateValidationInfosMapFromInputOutput(validationInfosMap, input, output, counterSignatures, referenceDate);
		// }

		return result;
	}

	private void checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);

		// il certificato e stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				// validationInfos.addError("Certificato revocato in data:Certificato gie revocato in data: " + crlEntry.getRevocationDate() + " (antecedente a:
				// "+date+")");
				validationInfos.addErrorWithCode(CertMessage.CERT_CRL_REVOKED_BEFORE,
						MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
			} else if (date == null) {
				// validationInfos.addError("Certificato gie revocato in data: " + crlEntry.getRevocationDate());
				validationInfos.addErrorWithCode(CertMessage.CERT_CRL_REVOKED_AFTER,
						MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
			}
		}
	}
}
