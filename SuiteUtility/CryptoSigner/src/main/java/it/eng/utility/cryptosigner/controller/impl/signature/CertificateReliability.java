package it.eng.utility.cryptosigner.controller.impl.signature;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.CACertificate;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Effettua il controllo di validita delle CA rispetto alla lista fornita dal CNIPA. I passi per verificare la correttezza del certificato sono i seguenti:
 * <ol>
 * <li>recupero della firma del certificato e dell'issuer</li>
 * <li>verifica se nella lista dei certificatori accreditati e presente l'issuer (stesso DN)</li>
 * <li>se e presente si sbusta la firma con la chiave pubblica e si verifica il risultato con digest del certificato</li>
 * </ol>
 * 
 * @author Stefano Zennaro
 *
 */
public class CertificateReliability extends AbstractSignerController {

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String CERTIFICATE_RELIABILITY_CHECK = "performCertificateReliability";
	public static final Logger log = LogManager.getLogger(CertificateReliability.class);

	public String getCheckProperty() {
		return CERTIFICATE_RELIABILITY_CHECK;
	}

	// I passi per verificare la correttezza del certificato sono i seguenti:
	// - recupero la firma del certificato
	// - recupero l'issuer del certificato
	// - guardo se nella lista dei certificatori accreditati e presente l'issuer (stesso DN)
	// - se e presente trovo la chiave pubblica
	// - sbusto la firma con la chiave pubblica e verifico il risultato con digest del certificato

	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		boolean result = true;

		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}

		// try {

		// List<ISignature> unqualifiedSignatures = new ArrayList<ISignature>();
		Map<ISignature, ValidationInfos> unqualifiedSignatureValidationInfos = new HashMap<ISignature, ValidationInfos>();
		Map<ISignature, X509Certificate> issuerQualifiedCertificates = new HashMap<ISignature, X509Certificate>();

		Date referenceDate = input.getReferenceDate();
		if (referenceDate == null)
			referenceDate = new Date();

		ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

		// Firme
		List<ISignature> signatures = null;
		if (output.getProperties().containsKey(OutputSignerBean.SIGNATURE_PROPERTY)) {
			signatures = (List<ISignature>) output.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			if (eseguiValidazioni)
				result = populateUnqualifiedSignaturesList(unqualifiedSignatureValidationInfos, issuerQualifiedCertificates, signatures,
						certificatesAuthorityStorage, referenceDate);

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
			Date referenceDate) {

		boolean result = true;

		if (signatures != null){
			int numFirma = 1;
			for (ISignature signature : signatures) {
				log.debug("Verifica della ca per la firma numero " + numFirma);
				numFirma++;
				ValidationInfos validationInfos = new ValidationInfos();
				X509Certificate signatureCertificate = signature.getSignerBean().getCertificate();
				
				boolean isQualified = false;
				X509Certificate qualifiedCertificate = null;
				if( signatureCertificate!=null ){
					X500Principal issuerPrincipal = signatureCertificate.getIssuerX500Principal();
	
					// Controllo se il certificato attuale e contenuto tra quelli accreditati
					String caCountry = null;
					String caServiceProviderName = null;
					String caServiceName = null;
					Date dataAttivazioneCertificato;
					Date dataScadenzaCertificato;
					Date dataRevocaCertificato = null;
					try {
						log.debug("Controllo se il certificato della CA (che ha emesso il certificato di firma) " + issuerPrincipal.getName() + " e' contenuto tra quelli accreditati");
						//qualifiedCertificate = certificatesAuthorityStorage.retriveCA(issuerPrincipal);
						CACertificate caCertBean = certificatesAuthorityStorage.retriveValidCA(signatureCertificate, false);
						qualifiedCertificate = caCertBean.getCertificate();
						caCountry = caCertBean.getCountry();
						caServiceProviderName = caCertBean.getServiceProviderName();
						caServiceName = caCertBean.getServiceName();
						dataAttivazioneCertificato = caCertBean.getDataAttivazioneCertificato();
						dataScadenzaCertificato = caCertBean.getDataScadenzaCertificato();
						dataRevocaCertificato = caCertBean.getDataRevocaCertificato();
						log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
					} catch (CryptoStorageException e1) {
						// TODO Auto-generated catch block
						log.error("CryptoStorageException " + e1);
					}
					
	
					if (qualifiedCertificate == null) {
						isQualified = false;
						validationInfos.addErrorWithCode(CertMessage.CERT_CA_NOT_PRESENT, MessageHelper.getMessage(CertMessage.CERT_CA_NOT_PRESENT));
						validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_NOT_PRESENT));
					} else {
	
						// Di default il certificato e accreditato se presente nella lista del cnipa
						isQualified = true;
	
						log.debug("CA accreditata individuata. Controllo se e' in data di validita'");
						log.debug("certificato CA - data scadenza " + qualifiedCertificate.getNotAfter());
						log.debug("certificato CA - data attivazione " + qualifiedCertificate.getNotBefore());
						// Occorre controllare che il certificato sia ancora attivo
						if (referenceDate.after(qualifiedCertificate.getNotAfter())) {
							isQualified = false;
							log.error(MessageHelper.getMessage(CertMessage.CERT_CA_EXPIRED, qualifiedCertificate.getNotAfter(), referenceDate));
							validationInfos.addErrorWithCode(CertMessage.CERT_CA_EXPIRED,
									MessageHelper.getMessage(CertMessage.CERT_CA_EXPIRED, qualifiedCertificate.getNotAfter(), referenceDate));
							validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_EXPIRED, qualifiedCertificate.getNotAfter(), referenceDate));
						} else if (referenceDate.before(qualifiedCertificate.getNotBefore())) {
							isQualified = false;
							log.error(MessageHelper.getMessage(CertMessage.CERT_CA_NOT_VALID_YET, qualifiedCertificate.getNotBefore(), referenceDate));
							validationInfos.addErrorWithCode(CertMessage.CERT_CA_NOT_VALID_YET,
									MessageHelper.getMessage(CertMessage.CERT_CA_NOT_VALID_YET, qualifiedCertificate.getNotBefore(), referenceDate));
							validationInfos
									.addError(MessageHelper.getMessage(CertMessage.CERT_CA_NOT_VALID_YET, qualifiedCertificate.getNotBefore(), referenceDate));
						} else if (dataRevocaCertificato!=null && referenceDate.after(dataRevocaCertificato)) {
							isQualified = false;
							log.error(MessageHelper.getMessage(CertMessage.CERT_CA_REVOKED, dataRevocaCertificato, referenceDate));
							validationInfos.addErrorWithCode(CertMessage.CERT_CA_REVOKED,
									MessageHelper.getMessage(CertMessage.CERT_CA_REVOKED, dataRevocaCertificato, referenceDate));
							validationInfos
									.addError(MessageHelper.getMessage(CertMessage.CERT_CA_REVOKED, dataRevocaCertificato, referenceDate));
						}
						// controllo che non sia stato revocato rispetto alla CRL indicata dal suo distribution point
						else {
							isQualified = true;
	//						log.debug("controllo che il certificato della CA non sia stato revocato rispetto alla CRL indicata dal suo distribution point");
	//						// Storage delle CRL
	//						ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();
	//						// Issuer del certificato di certificazione
	//						Principal qualifiedCertificateIssuer = qualifiedCertificate.getIssuerDN();
	//						X509CRL qualifiedCertificateCRL = null;
	//						try {
	//							//log.debug("qualifiedCertificate.getIssuerDN() " + qualifiedCertificate.getIssuerDN().getName() );
	//							//log.debug("qualifiedCertificate.getSubjectDN() " + qualifiedCertificate.getSubjectDN().getName() );
	//							qualifiedCertificateCRL = crlStorage.retriveCRL(qualifiedCertificateIssuer.getName());
	//							//qualifiedCertificateCRL = crlStorage.retriveCRL(qualifiedCertificateIssuer.toString());
	//						} catch (CryptoStorageException e) {
	//						}
	//						
	//						log.debug("Verifico se la CRL storica esiste e se il suo periodo di validite e' applicabile");
	//						if (qualifiedCertificateCRL != null && qualifiedCertificateCRL.getNextUpdate().after(referenceDate)) {
	//							log.debug("data di prossimo aggiornamento della CRL " + qualifiedCertificateCRL.getNextUpdate());
	//							log.debug("CRL presente nello storage, la verifico");
	//							isQualified = checkCRL(validationInfos, signatureCertificate, qualifiedCertificateCRL, referenceDate);
	//							log.debug("Verifica CRL " + isQualified);
	//						} else {
	//							// Se la CRL non era presente nello storage
	//							// verifico rispetto al distribution point
	//							if (qualifiedCertificateCRL != null && qualifiedCertificateCRL.getNextUpdate().after(referenceDate)) {
	//								log.debug("data di prossimo aggiornamento della CRL " + qualifiedCertificateCRL.getNextUpdate());
	//							}
	//							
	//							log.debug("La CRL non era presente nello storage oppure il suo periodo di validite non e' applicabile, verifico rispetto al distribution point");
	//							Vector<String> urlCRLDistributionPoints = null; 
	//							try {
	//								urlCRLDistributionPoints = signerUtil.getURLCrlDistributionPoint(qualifiedCertificate);
	//							} catch (CryptoSignerException e) {
	//								// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
	//								// tengo traccia dell'errore ma considero il certificato comunque accreditato
	//								// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
	//								validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
	//								validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
	//										MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
	//								isQualified = true;
	//							} catch (Exception e) {
	//								// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
	//								// tengo traccia dell'errore ma considero il certificato comunque accreditato
	//								// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
	//								validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
	//								validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
	//										MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
	//								isQualified = true;
	//							}
	//							log.info("urlCRLDistributionPoints:: " + urlCRLDistributionPoints);
	//							if( urlCRLDistributionPoints!=null ){
	//								log.info("Questo certificato ha: " + urlCRLDistributionPoints.size() + " punti di distribuzione");
	//								int contaCRLScaricate = 0;
	//								for (int i = 0; i < urlCRLDistributionPoints.size(); i++) {
	//									boolean ciclaCRL = true;
	//									try {
	//										while (ciclaCRL) {
	//											String urlCRLDistributionPoint = urlCRLDistributionPoints.get(i);
	//	
	//											log.info("Si esegue lo scarico della CRL per il seguente punto di distribuzione: " + urlCRLDistributionPoint);
	//	
	//											X509CRL envelopeCrl = null;
	//											if (urlCRLDistributionPoint != null)
	//												envelopeCrl = signerUtil.getCrlByURL(urlCRLDistributionPoint);
	//											if (envelopeCrl != null) {
	//												contaCRLScaricate++;
	//												isQualified = checkCRL(validationInfos, qualifiedCertificate, envelopeCrl, referenceDate);
	//	
	//												// La CRL deve essere storicizzata
	//												try {
	//													//log.debug("Storicizzo la CRL");
	//													crlStorage.insertCRL(envelopeCrl, caCountry, caServiceProviderName, caServiceName );
	//												} catch (CryptoStorageException e) {
	//													log.error("Errore nell'insermento nella cartella locale della CRL: " + e.getMessage());
	//												}
	//											}
	//											ciclaCRL = false;
	//										}
	//									} catch (Exception e) {
	//										// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
	//										// tengo traccia dell'errore ma considero il certificato comunque accreditato
	//										// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
	//										validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
	//										validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
	//												MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
	//										isQualified = true;
	//									}
	//								}
	//								
	//								if (contaCRLScaricate != urlCRLDistributionPoints.size()) {
	//									log.warn("Abbiamo scaricato " + contaCRLScaricate + " CRL" + " a fronte di " + urlCRLDistributionPoints.size()
	//											+ " punti di distribuzione.");
	//								}
	//							}
	//						}
						}
	
						// PublicKey publicKey = qualifiedCertificate.getPublicKey();
						//
						// try {
						// signatureCertificate.verify(publicKey);
						//
						// //Aggiungo il certificato del firmatario a quelli accreditati
						// if (signatureCertificate instanceof X509Certificate)
						// issuerQualifiedCertificates.put(signature, (X509Certificate)signatureCertificate);
						//
						// }catch (Exception e) {
						// unqualifiedSignatures.add(signature);
						// }
					}
				} else {
					
					log.error(MessageHelper.getMessage(CertMessage.CERT_NOTFOUND));
					validationInfos.addErrorWithCode(CertMessage.CERT_NOTFOUND,
							MessageHelper.getMessage(CertMessage.CERT_NOTFOUND));
					validationInfos
							.addError(MessageHelper.getMessage(CertMessage.CERT_NOTFOUND));
				}

				if (isQualified)
					issuerQualifiedCertificates.put(signature, qualifiedCertificate);
				else
					unqualifiedSignatureValidationInfos.put(signature, validationInfos);

				result &= validationInfos.isValid();

				if (performCounterSignaturesCheck) {
					List<ISignature> counterSignatures = signature.getCounterSignatures();
					result &= populateUnqualifiedSignaturesList(unqualifiedSignatureValidationInfos, issuerQualifiedCertificates, counterSignatures,
							certificatesAuthorityStorage, referenceDate);
				}

			}
		}

		return result;
	}

	private boolean checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);
			
		// il certificato e stato revocato
		if (crlEntry != null) {
			log.debug("Data revoca CRL " + crlEntry.getRevocationDate() );
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addErrorWithCode(CertMessage.CERT_CRL_REVOKED_BEFORE,
						MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				return false;
			} else if (date == null) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addErrorWithCode(CertMessage.CERT_CRL_REVOKED_AFTER,
						MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				return false;
			}
		} else {
			log.debug("certificato non presente nella CRL");
		}
		return true;
	}

}
