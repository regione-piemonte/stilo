package it.eng.utility.cryptosigner.controller.impl.timestamp;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.GenTimeAccuracy;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.bouncycastle.util.Store;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.ITimeStampController;
import it.eng.utility.cryptosigner.controller.ITimeStampValidator;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.TimeStampValidityBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.exception.NoSignerException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Consente di effettuare l'analisi di file contenenti marche temporali, oppure associati a marche temporali detached . Nel caso siano presenti delle marche
 * temporali aggiuntive (che estendono quella da analizzare), esse devono essere specificate come parametri aggiuntivi del metodo corrispondente all'interno di
 * un array,
 * 
 * @author Stefano Zennaro
 *
 */
public class TimeStampController implements ITimeStampController {

	private AbstractSigner signer;

	private List<TimeStampValidityBean> timeStampValidity;

	private ITimeStampValidator timeStampValidator;

	private SignerUtil signerUtil = SignerUtil.newInstance();
	private static Logger log = Logger.getLogger(TimeStampController.class);
	
	static {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	private void populateCommonAttributes(DocumentAndTimeStampInfoBean documentAndTimeStampInfo, ValidationInfos validationInfos,
			boolean executeCurrentDateValidation) {

		TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();

		/*
		 * Tipo di algorimto utilizzato durante la generazione dell'hash del messaggio - l'algoritmo impiegato per effettuare l'impronta del file marcato
		 */
		TimeStampTokenInfo tokenInfo = timeStampToken.getTimeStampInfo();
		documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_HASH_ALGORITHM, tokenInfo.getMessageImprintAlgOID());

		/*
		 * Riferimento temporale in millisecondi (se disponibili) della marca
		 */
		GenTimeAccuracy accuracy = tokenInfo.getGenTimeAccuracy();
		Long millis = accuracy != null ? tokenInfo.getGenTime().getTime() + tokenInfo.getGenTimeAccuracy().getMillis() : tokenInfo.getGenTime().getTime();
		documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_MILLISECS, millis.toString());

		/*
		 * Data del riferimento temporale
		 */
		Date timestampDate = new Date(tokenInfo.getGenTime().getTime());
		documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_DATE, timestampDate);

		/*
		 * Controlla se il certificato della TSA e nella lista dei certificati accreditati
		 */
		Boolean isCertificateInList = false;
		Collection<CRL> embeddedCRLs = null;
		Collection<Certificate> saCertificates = null;

		// Seriale identificativo della TSA
		BigInteger tsaSerial = timeStampToken.getSID().getSerialNumber();
		try {

			// MODIFICA ANNA 1.53
			// CertStore certStore = timeStampToken.getCertificatesAndCRLs("Collection", "BC");
			Store certStore = timeStampToken.toCMSSignedData().getCertificates();
			// saCertificates = (Collection<Certificate>)certStore.getCertificates(null);
			saCertificates = certStore.getMatches(null);
			// embeddedCRLs = (Collection<CRL>)certStore.getCRLs(null);
			Store embeddedCRLsStore = timeStampToken.toCMSSignedData().getCRLs();
			embeddedCRLs = embeddedCRLsStore.getMatches(null);

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

						// Controllo se il certificato di firma e attendibile
						ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

						//X509Certificate qualifiedCertificate = certificatesAuthorityStorage.retriveCA(saX509Certificate.getSubjectX500Principal());
						CACertificate caCertBean = certificatesAuthorityStorage.retriveValidCA(saX509Certificate, true);
						X509Certificate qualifiedCertificate = caCertBean.getCertificate();
						String caCountry = caCertBean.getCountry();
						String caServiceProviderName = caCertBean.getServiceProviderName();
						String caServiceName = caCertBean.getServiceName();
						log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
						
						log.info("-- qualifiedCertificate " + qualifiedCertificate);
						if (qualifiedCertificate == null) {
							isCertificateInList = false;
							validationInfos.addErrorWithCode(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID,
									MessageHelper.getMessage(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID));
							validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_CERTIFICATE_TSA_NOTVALID));
						} else {
							PublicKey publicKey = qualifiedCertificate.getPublicKey();
							if (org.bouncycastle.util.Arrays.constantTimeAreEqual(saCertificate.getPublicKey().getEncoded(),
									qualifiedCertificate.getPublicKey().getEncoded())) {

								/*
								 * Verifico la data di scadenza temporale del certificato indicato nello storage
								 */
								if (timestampDate.after(qualifiedCertificate.getNotAfter())) {
									validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_DATE_AFTER,
											MessageHelper.getMessage(MessageConstants.SIGN_TSA_DATE_AFTER, timestampDate, qualifiedCertificate.getNotAfter()));
									validationInfos.addError(
											MessageHelper.getMessage(MessageConstants.SIGN_TSA_DATE_AFTER, timestampDate, qualifiedCertificate.getNotAfter()));
									isCertificateInList = false;
								} else if (timestampDate.before(qualifiedCertificate.getNotBefore())) {
									validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_DATE_BEFORE, MessageHelper
											.getMessage(MessageConstants.SIGN_TSA_DATE_BEFORE, timestampDate, qualifiedCertificate.getNotBefore()));
									validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TSA_DATE_BEFORE, timestampDate,
											qualifiedCertificate.getNotBefore()));
									isCertificateInList = false;
								} else
									isCertificateInList = true;
							} else {
								validationInfos.addErrorWithCode(MessageConstants.SIGN_TSA_ERROR_STORAGE,
										MessageHelper.getMessage(MessageConstants.SIGN_TSA_ERROR_STORAGE));
								validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TSA_ERROR_STORAGE));
								isCertificateInList = false;
							}
						}

						if (isCertificateInList) {
							/*
							 * controllo che il certificato non faccia parte della CRL indicata dalla TSA
							 */
							// Storage delle CRL
							ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();
							Principal issuerDN = saX509Certificate.getIssuerDN();
							X509CRL historicalCRL = null;
							try {
								historicalCRL = crlStorage.retriveCRL(saX509Certificate);
							} catch (CryptoStorageException e) {
								// Si e verificato un errore durante il recupero della CRL storicizzata
							}

							// Verifico se la data di prossimo aggiornamento della CRL e >= della
							// data del riferimento temporale
							if (historicalCRL != null && historicalCRL.getNextUpdate().after(timestampDate))
								checkCRL(validationInfos, saX509Certificate, historicalCRL, timestampDate);
							else {

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
													checkCRL(validationInfos, saX509Certificate, envelopeCrl, timestampDate);

													// La CRL deve essere storicizzata
													try {
														crlStorage.insertCRL(envelopeCrl, caCountry, caServiceProviderName, caServiceName );
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
																try {
																	crlStorage.insertCRL(x509EmbeddedCRL, caCountry, caServiceProviderName, caServiceName );
																} catch (CryptoStorageException e2) {
																	log.warn("CryptoStorageException", e2);
																}
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

						}

					}

				}
			}
		} catch (Exception e) {
			log.warn("Eccezione populateCommonAttributes", e);
		}
		documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_RECOGNIZED_CERTIFICATE, isCertificateInList.toString());

		/*
		 * Controllo della validita attuale del timestamp
		 */
		if (executeCurrentDateValidation
				&& !timeStampValidator.isTimeStampCurrentlyValid(timeStampToken, getTimeStampValidityForTimeStampToken(timeStampToken))) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_NOTVALID, MessageHelper.getMessage(MessageConstants.SIGN_MARK_NOTVALID));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_NOTVALID));
		}

	}

	/**
	 * Effetta l'analisi di un file contenente marche temporali.
	 * 
	 * Il risultato dell'analisi produce una struttura contenente gli esiti dei seguenti controlli:
	 * <ul>
	 * <li>Riconoscimento del formato di busta</li>
	 * <li>Recupero delle marche temporali</li>
	 * <li>Individuazione del tipo di marche (detached/embedded)</li>
	 * <li>Validazione della corretta associazione tra marca temporale e contenuto firmato</li>
	 * <li>Validazione del certificato di timestamp, rispetto:
	 * <ul>
	 * <li>Alle CRL</li>
	 * <li>Alle CA accreditate</li>
	 * </ul>
	 * </li>
	 * <li>Eventuale controllo di validita rispetto alla data corrente</li>
	 * </ul>
	 * 
	 * @param file
	 *            busta contenente i timestamp
	 * @param executeCurrentDateValidation
	 *            flag per indicare l'esecuzione della validazione rispetto alla data attuale
	 * @return informazioni sulle marche temporali contenute nella busta
	 * @throws FileNotFoundException
	 *             se il file da analizzare non puo essere recuperato
	 * @throws CryptoSignerException
	 *             se si e verificato un errore durante le fasi di analisi
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, boolean executeCurrentDateValidation) throws FileNotFoundException, CryptoSignerException {

		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfoList = new ArrayList<DocumentAndTimeStampInfoBean>();

		try {
			signer = signerUtil.getSignerManager(file);
			
			TimeStampToken[] timeStampTokens = signer.getTimeStampTokens(file);

			if (timeStampTokens != null) {
				for (TimeStampToken timeStampToken : timeStampTokens) {

					DocumentAndTimeStampInfoBean documentAndTimeStampInfo = new DocumentAndTimeStampInfoBean();
					documentAndTimeStampInfo.setAssociatedFile(file);
					documentAndTimeStampInfo.setTimeStampToken(timeStampToken);

					/*
					 * Formato della marca temporale
					 */
					documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_TIMESTAMP_FORMAT, signer.getFormat());

					/*
					 * Tipo di marca (EMBEDDED)
					 */
					documentAndTimeStampInfo.setTimeStampTokenType(DocumentAndTimeStampInfoBean.TimeStampTokenType.EMBEDDED);

					/*
					 * Verifica che la marca temporale corrisponda al file di appartenenza
					 */
					ValidationInfos infos = null;
					try {
						infos = signer.validateTimeStampTokensEmbedded();
					} catch (CMSException e) {
					}
					if (infos == null) {
						infos = new ValidationInfos();
						infos.addWarning(signer.getClass().getName() + " non ha potuto completare la validazione oppure la marca non e di tipo: "
								+ documentAndTimeStampInfo.getTimeStampTokenType());
					}

					/*
					 * Genera gli attributi comuni per tutte le marche temporali
					 */
					populateCommonAttributes(documentAndTimeStampInfo, infos, executeCurrentDateValidation);
					documentAndTimeStampInfo.setValidationInfos(infos);
					documentAndTimeStampInfoList.add(documentAndTimeStampInfo);
				}
			}
		} catch (NoSignerException e1) {
			log.debug("Il file non è firmato");
		}
		

		return documentAndTimeStampInfoList.toArray(new DocumentAndTimeStampInfoBean[documentAndTimeStampInfoList.size()]);
	}

	/**
	 * @see it.eng.utility.cryptosigner.controller.impl.timestamp.TimeStampController#checkTimeStamps(File, boolean) checkTimeStamps(File,boolean)
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file) throws FileNotFoundException, CryptoSignerException {
		return checkTimeStamps(file, true);
	}

	/**
	 * Effetta l'analisi di un file con marca temporale detached.
	 * 
	 * Il risultato dell'analisi produce una struttura contenente gli esiti dei seguenti controlli:
	 * <ul>
	 * <li>Riconoscimento del formato di busta</li>
	 * <li>Recupero delle marche temporali</li>
	 * <li>Individuazione del tipo di marche (detached/embedded)</li>
	 * <li>Validazione della corretta associazione tra marca temporale e contenuto firmato</li>
	 * <li>Validazione del certificato di timestamp, rispetto:
	 * <ul>
	 * <li>Alle CRL</li>
	 * <li>Alle CA accreditate</li>
	 * </ul>
	 * </li>
	 * <li>Eventuale controllo di validita rispetto alla data corrente</li>
	 * </ul>
	 * 
	 * @param file
	 *            contenuto marcato
	 * @param detachedTimeStamp
	 *            timestamp associato al contenuto
	 * @param executeCurrentDateValidation
	 *            flag per indicare l'esecuzione della validazione rispetto alla data attuale
	 * @return informazioni sulle marche temporali contenute nella busta
	 * @throws FileNotFoundException
	 *             se il file da analizzare non puo essere recuperato
	 * @throws CryptoSignerException
	 *             se si e verificato un errore durante le fasi di analisi
	 */
	private DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File detachedTimeStamp, boolean executeCurrentDateValidation)
			throws FileNotFoundException, CryptoSignerException {
		List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfoList = new ArrayList<DocumentAndTimeStampInfoBean>();

		try {
			signer = signerUtil.getSignerManager(detachedTimeStamp);
	
			TimeStampToken[] timeStampTokens = signer.getTimeStampTokens(detachedTimeStamp);
	
			if (timeStampTokens != null) {
				for (TimeStampToken timeStampToken : timeStampTokens) {
	
					DocumentAndTimeStampInfoBean documentAndTimeStampInfo = new DocumentAndTimeStampInfoBean();
	
					documentAndTimeStampInfo.setAssociatedFile(file);
					documentAndTimeStampInfo.setTimeStampToken(timeStampToken);
	
					/*
					 * Formato della marca temporale
					 */
					documentAndTimeStampInfo.setProperty(DocumentAndTimeStampInfoBean.PROP_TIMESTAMP_FORMAT, signer.getFormat());
	
					/*
					 * Tipo di marca (DETACHED)
					 */
					documentAndTimeStampInfo.setTimeStampTokenType(DocumentAndTimeStampInfoBean.TimeStampTokenType.DETACHED);
	
					/*
					 * Verifica che la marca temporale corrisponda al file di appartenenza
					 */
					ValidationInfos infos = signer.validateTimeStampTokensDetached(file);
					if (infos == null) {
						infos = new ValidationInfos();
						infos.addWarning(
								"Il signer non ha effettuato la validazione oppure la marca non e di tipo: " + documentAndTimeStampInfo.getTimeStampTokenType());
					}
	
					/*
					 * Genera gli attributi comuni per tutte le marche temporali
					 */
					populateCommonAttributes(documentAndTimeStampInfo, infos, executeCurrentDateValidation);
	
					documentAndTimeStampInfo.setValidationInfos(infos);
					documentAndTimeStampInfoList.add(documentAndTimeStampInfo);
	
				}
			}
		} catch (NoSignerException e1) {
			log.debug("Il file non è firmato");
		}
		return documentAndTimeStampInfoList.toArray(new DocumentAndTimeStampInfoBean[documentAndTimeStampInfoList.size()]);
	}

	/**
	 * @see it.eng.utility.cryptosigner.controller.impl.timestamp.TimeStampController#checkTimeStamps(File, File, boolean) checkTimeStamps(File, File, boolean)
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File detachedTimeStamp) throws FileNotFoundException, CryptoSignerException {
		return checkTimeStamps(file, detachedTimeStamp, true);
	}

	/**
	 * Recupera il signer che e stato individuato in seguito all'estrazione del timestamp
	 */
	public AbstractSigner getSigner() {
		return signer;
	}

	public void reset() {
		this.signer = null;
	}

	private void checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);
		// il certificato e stato revocato
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
	 * @see it.eng.utility.cryptosigner.controller.impl.timestamp.TimeStampController#checkTimeStamps(File, boolean) checkTimeStamps(File, boolean)
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File... timeStampExtensionChain) throws FileNotFoundException, CryptoSignerException {
		DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos = checkTimeStamps(file, false);
		validateTimeStampsChain(documentAndTimeStampInfos, file, timeStampExtensionChain);
		return documentAndTimeStampInfos;
	}

	/**
	 * @see it.eng.utility.cryptosigner.controller.impl.timestamp.TimeStampController#checkTimeStamps(File, File, boolean) checkTimeStamps(File, File, boolean)
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File detachedTimeStamp, File... timeStampExtensionChain)
			throws FileNotFoundException, CryptoSignerException {
		DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos = checkTimeStamps(file, detachedTimeStamp, false);
		validateTimeStampsChain(documentAndTimeStampInfos, detachedTimeStamp, timeStampExtensionChain);
		return documentAndTimeStampInfos;
	}

	/**
	 * Recupera la lista dei bean che memorizzano i periodi di validita dei tipi di marche temporali
	 * 
	 * @return lista
	 */
	public List<TimeStampValidityBean> getTimeStampValidity() {
		return timeStampValidity;
	}

	/**
	 * Definisce la lista dei bean che memorizzano i periodi di validita dei tipi di marche temporali
	 * 
	 * @param timeStampValidity
	 */
	public void setTimeStampValidity(List<TimeStampValidityBean> timeStampValidity) {
		Arrays.sort((new ArrayList<TimeStampValidityBean>(timeStampValidity)).toArray());
		this.timeStampValidity = timeStampValidity;
	}

	/**
	 * Recupera l'istanza del validatore {@link it.eng.utility.cryptosigner.controller.ITimeStampValidator} preposto alla verifica della validita corrente delle
	 * marche temporali
	 */
	public ITimeStampValidator getTimeStampValidator() {
		return timeStampValidator;
	}

	/**
	 * Definisce l'istanza del validatore {@link it.eng.utility.cryptosigner.controller.ITimeStampValidator} preposto alla verifica della validita corrente
	 * delle marche temporali
	 */
	public void setTimeStampValidator(ITimeStampValidator timeStampValidator) {
		this.timeStampValidator = timeStampValidator;
	}

	private void validateTimeStampsChain(DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos, File signedFile, File[] timeStampExtensionChain)
			throws CryptoSignerException {

		// Controllo se esiste una catena di estensioni del timestamp
		if (timeStampExtensionChain == null)
			return;

		// Recupero i timestampToken presenti nel documento
		List<TimeStampToken> timeStampTokens = new ArrayList<TimeStampToken>();
		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {
			timeStampTokens.add(documentAndTimeStampInfo.getTimeStampToken());
		}

		TimeStampToken[] currentTimeStampTokens = timeStampTokens.toArray(new TimeStampToken[timeStampTokens.size()]);
		File currentFile = signedFile;
		for (int i = 0; i < timeStampExtensionChain.length; ++i) {

			File timeStampExtensionFile = timeStampExtensionChain[i];

			// Recupero il formato..
			AbstractSigner extensionSigner = null;
			try {
				extensionSigner = signerUtil.getSignerManager(timeStampExtensionFile);
			} catch (NoSignerException e1) {
				log.debug("Il file non è firmato");
			}
			
			if (extensionSigner != null) {

				// Valido il file detached rispetto al timestamp attuale
				ValidationInfos timeStampTokenExtensionValidation = extensionSigner.validateTimeStampTokensDetached(currentFile);
				if (timeStampTokenExtensionValidation == null || !timeStampTokenExtensionValidation.isValid()) {
					setAllValidationInfos(documentAndTimeStampInfos, new String[] { "L'estensione della marca temporale #" + (i + 1) + " contenuta nel file: "
							+ timeStampExtensionFile + " e invalida per il file: " + currentFile }, null);
					break;
				}

				TimeStampToken[] timeStampExtensions = extensionSigner.getTimeStampTokens(currentFile);
				if (timeStampExtensions != null && timeStampExtensions.length != 0) {

					// Aggiungo la lista delle estensioni ai bean di informazioni
					for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {
						List<TimeStampToken> timeStampExtensionTokens = documentAndTimeStampInfo.getTimeStampExtensionChain();
						if (timeStampExtensionTokens == null) {
							documentAndTimeStampInfo.setTimeStampExtensionChain(timeStampExtensionTokens = new ArrayList<TimeStampToken>());
						}
						timeStampExtensionTokens.addAll(Arrays.asList(timeStampExtensions));
					}

					// Valido l'estensione del periodo di validita
					if (!validateTimeStampExtensionListOverTimeStampList(documentAndTimeStampInfos, currentTimeStampTokens, timeStampExtensions,
							timeStampExtensionFile, currentFile))
						break;

					// Valido la TSA dell'estensione
					validateTimeStampExtensionListTSA(documentAndTimeStampInfos, timeStampExtensions, timeStampExtensionFile);
				}

				// Verifico l'attuale validita dell'ultima estensione del timestamp
				if (i == timeStampExtensionChain.length - 1) {
					if (!validateLastTimeStampExtensions(documentAndTimeStampInfos, timeStampExtensions, timeStampExtensionFile))
						break;
				}

				// Aggiorno il file da validare e la lista dei timeStamp associati
				currentFile = timeStampExtensionFile;
				currentTimeStampTokens = timeStampExtensions;
			}
		}
	}

	private void validateTimeStampExtensionListTSA(DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos, TimeStampToken[] timeStampExtensions,
			File timeStampExtensionFile) {
		for (int i = 0; i < timeStampExtensions.length; ++i) {
			TimeStampToken timeStampExtension = timeStampExtensions[i];
			DocumentAndTimeStampInfoBean tmpInfo = new DocumentAndTimeStampInfoBean();
			tmpInfo.setTimeStampToken(timeStampExtension);
			ValidationInfos tmpValidationInfos = new ValidationInfos();
			populateCommonAttributes(tmpInfo, tmpValidationInfos, false);
			if (!tmpValidationInfos.isValid()) {
				setAllValidationInfos(documentAndTimeStampInfos, new String[] { "L'estensione della marca temporale #" + (i + 1) + " contenuta nel file: "
						+ timeStampExtensionFile + " contiene un certificato non valido: " + tmpValidationInfos.getErrors() }, null);
			}
			// if (tmpValidationInfos.getWarnings()!=null && tmpValidationInfos.getWarnings().size()!=0) {
			if (tmpValidationInfos.getWarnings() != null && tmpValidationInfos.getWarnings().length != 0) {
				setAllValidationInfos(documentAndTimeStampInfos, null, new String[] { "L'estensione della marca temporale #" + (i + 1) + " contenuta nel file: "
						+ timeStampExtensionFile + " contiene un certificato con il seguente problema: " + tmpValidationInfos.getWarnings() });
			}
		}

	}

	private boolean validateLastTimeStampExtensions(DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos, TimeStampToken[] timeStampExtensions,
			File timeStampExtensionFile) {
		boolean result = true;
		for (int i = 0; i < timeStampExtensions.length; ++i) {
			TimeStampToken timeStampExtension = timeStampExtensions[i];
			if (!timeStampValidator.isTimeStampCurrentlyValid(timeStampExtension, getTimeStampValidityForTimeStampToken(timeStampExtension))) {
				setAllValidationInfos(documentAndTimeStampInfos, new String[] {
						"L'estensione della marca temporale #" + (i + 1) + " contenuta nel file: " + timeStampExtensionFile + " non e correntemente valida" },
						null);
				result = false;
			}
		}
		return result;
	}

	private boolean validateTimeStampExtensionListOverTimeStampList(DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos,
			TimeStampToken[] currentTimeStampTokens, TimeStampToken[] timeStampExtensions, File timeStampExtensionFile, File currentFile) {
		boolean result = true;
		for (int i = 0; i < currentTimeStampTokens.length; i++) {
			TimeStampToken currentTimeStampToken = currentTimeStampTokens[i];
			for (int j = 0; j < timeStampExtensions.length; j++) {
				TimeStampToken timeStampExtension = timeStampExtensions[j];
				if (!timeStampValidator.isTimeStampExtended(currentTimeStampToken, getTimeStampValidityForTimeStampToken(currentTimeStampToken),
						timeStampExtension, getTimeStampValidityForTimeStampToken(timeStampExtension))) {
					setAllValidationInfos(documentAndTimeStampInfos,
							new String[] { "L'estensione della marca temporale #" + (j + 1) + " contenuta nel file: " + timeStampExtensionFile + " ("
									+ timeStampExtension.getTimeStampInfo().getGenTime() + ")" + " non estende la marca temporale #" + (i + 1)
									+ " contenuta nel file: " + currentFile + " (" + currentTimeStampToken.getTimeStampInfo().getGenTime() + ")" },
							null);
					result = false;
				}
			}
		}
		return result;
	}

	private void setAllValidationInfos(DocumentAndTimeStampInfoBean[] documentAndTimeStampInfos, String[] errors, String[] warnings) {
		if (documentAndTimeStampInfos == null)
			return;
		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo : documentAndTimeStampInfos) {
			if (documentAndTimeStampInfo.getValidationInfos() == null)
				documentAndTimeStampInfo.setValidationInfos(new ValidationInfos());
			ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
			validationInfos.addErrors(errors);
			for (String error : errors) {
				validationInfos.addErrorWithCode("", error);
			}
			validationInfos.addWarnings(warnings);
			for (String warning : warnings) {
				validationInfos.addWarningWithCode("", warning);
			}
		}
	}

	private TimeStampValidityBean getTimeStampValidityForTimeStampToken(TimeStampToken timeStampToken) {
		if (timeStampValidity == null || timeStampValidity.size() == 0)
			return null;
		TimeStampValidityBean result = null;
		Iterator<TimeStampValidityBean> iterator = timeStampValidity.iterator();
		Calendar timeStampTokenCalendar = Calendar.getInstance();
		timeStampTokenCalendar.setTime(timeStampToken.getTimeStampInfo().getGenTime());
		Calendar tmpCal = Calendar.getInstance();
		while (iterator.hasNext()) {
			TimeStampValidityBean timeStampValidityBean = iterator.next();
			if (timeStampValidityBean.getBegin() == null)
				result = timeStampValidityBean;
			else {
				tmpCal.setTime(timeStampValidityBean.getBegin());
				if (tmpCal.before(timeStampTokenCalendar))
					result = timeStampValidityBean;
				else
					break;
			}
		}
		return result;
	}

}
