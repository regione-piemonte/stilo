package it.eng.utility.cryptosigner.controller.impl.cert;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
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
public class CertificateReliability extends AbstractCertController {

	private static final Logger log = Logger.getLogger(CertificateReliability.class);
	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability#getCheckProperty getCheckProperty}
	 */
	public static final String CERTIFICATE_RELIABILITY_CHECK = "performCertificateReliability";

	public String getCheckProperty() {
		return CERTIFICATE_RELIABILITY_CHECK;
	}

	// I passi per verificare la correttezza del certificato sono i seguenti:
	// - recupero la firma del certificato
	// - recupero l'issuer del certificato
	// - guardo se nella lista dei certificatori accreditati e presente l'issuer (stesso DN)
	// - se e presente trovo la chiave pubblica
	// - sbusto la firma con la chiave pubblica e verifico il risultato con digest del certificato

	public boolean execute(InputCertBean input, OutputCertBean output, boolean eseguiValidazioni) throws ExceptionController {

		boolean result = true;
		if (eseguiValidazioni)
			result = populateUnqualifiedSignaturesList(input, output);

		return result;
	}

	public boolean populateUnqualifiedSignaturesList(InputCertBean input, OutputCertBean output) {

		boolean result = true;
		ValidationInfos validationInfos = new ValidationInfos();
		boolean isQualified = false;
		X509Certificate qualifiedCertificate = null;
		String caCountry = null;
		String caServiceProviderName = null;
		String caServiceName = null;
		try {
			FactorySigner.getInstanceCertificateAuthority().updateCertificate();
		} catch (CryptoSignerException e2) {
			log.warn("impossibile aggiornare la lista delle CA, uso quella precedente", e2);
			// TODO make config if this must be blocking control
			validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_UPDATE_LIST_CA_WARNING));
			validationInfos.addWarningWithCode(CertMessage.CERT_UPDATE_LIST_CA_WARNING, MessageHelper.getMessage(CertMessage.CERT_UPDATE_LIST_CA_WARNING));
		}
		if (input != null && input.getCertificate() != null) {
			Date referenceDate = input.getReferenceDate();
			if (referenceDate == null)
				referenceDate = new Date();
			X509Certificate signatureCertificate = input.getCertificate();

			ICAStorage certificatesAuthorityStorage = FactorySigner.getInstanceCAStorage();

			// prendo l'issuer dn (TODO potrebbero esserci degli omonimi!?)
			X500Principal issuerPrincipal = signatureCertificate.getIssuerX500Principal();

			// Controllo se il certificato attuale e contenuto tra quelli accreditati

			log.debug("----------------------------------------");
			log.debug("issuerPrincipal:"+issuerPrincipal);
			log.debug("issuerPrincipal:"+signatureCertificate.getIssuerDN());
			log.debug("----------------------------------------");
			try {
				//qualifiedCertificate = certificatesAuthorityStorage.retriveCA(issuerPrincipal);
				CACertificate caCertBean = certificatesAuthorityStorage.retriveValidCA(signatureCertificate, false);
				qualifiedCertificate = caCertBean.getCertificate();
				caCountry = caCertBean.getCountry();
				caServiceProviderName = caCertBean.getServiceProviderName();
				caServiceName = caCertBean.getServiceName();
				log.debug("caCountry " + caCountry + " caServiceProviderName " + caServiceProviderName+ " caServiceName " + caServiceName );
			} catch (CryptoStorageException e1) {
				log.info("errore nel recupero del certificato dal repository di quelli attendibili",e1);
				//e1.printStackTrace();
				//in caso di errore vuol dire che non riesci a recuperare il cert, ma la conf � stata trovata
				//se invece l'issuer non c'� non � trovato ritorna null
				//validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_ERROR_FROM_STORAGE), true);
				validationInfos.addError( MessageHelper.getMessage(CertMessage.CERT_CA_ERROR_FROM_STORAGE), true);
				validationInfos.addErrorWithCode( CertMessage.CERT_CA_ERROR_FROM_STORAGE, MessageHelper.getMessage(CertMessage.CERT_CA_ERROR_FROM_STORAGE), true);
			}

			if (qualifiedCertificate == null) {
				isQualified = false;
				// validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_NOT_PRESENT));
				validationInfos.addErrorWithCode(CertMessage.CERT_CA_NOT_PRESENT, MessageHelper.getMessage(CertMessage.CERT_CA_NOT_PRESENT));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_NOT_PRESENT));
			} else {

				// Di default il certificato e accreditato se presente nella lista del cnipa
				isQualified = true;

				// Occorre controllare che il certificato sia ancora attivo
				if (referenceDate.after(qualifiedCertificate.getNotAfter())) {
					isQualified = false;
					// validationInfos.addError("Il certificato di certificazione e accreditato ma e scaduto in data: " + qualifiedCertificate.getNotAfter() + "
					// precedente al riferimento temporale: " + referenceDate);
					validationInfos.addErrorWithCode(CertMessage.CERT_CA_EXPIRED,
							MessageHelper.getMessage(CertMessage.CERT_CA_EXPIRED, qualifiedCertificate.getNotAfter(), referenceDate));
					validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_EXPIRED, qualifiedCertificate.getNotAfter(), referenceDate));
				} else if (referenceDate.before(qualifiedCertificate.getNotBefore())) {
					isQualified = false;
					// validationInfos.addError("Il certificato di certificazione e accreditato ma e entrato in vigore in data: " +
					// qualifiedCertificate.getNotBefore() + " successivo al riferimento temporale: " + referenceDate);
					validationInfos.addErrorWithCode(CertMessage.CERT_CA_NOT_VALID_YET,
							MessageHelper.getMessage(CertMessage.CERT_CA_NOT_VALID_YET, qualifiedCertificate.getNotBefore(), referenceDate));
					validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_NOT_VALID_YET, qualifiedCertificate.getNotBefore(), referenceDate));
				}
				// controllo che non sia stato revocato rispetto alla CRL indicata dal suo distribution point
				else {

					// Storage delle CRL
					ICRLStorage crlStorage = FactorySigner.getInstanceCRLStorage();
					// Issuer del certificato di certificazione
					Principal qualifiedCertificateIssuer = qualifiedCertificate.getIssuerDN();
					X509CRL qualifiedCertificateCRL = null;
					// TODO dovresti controllare anche se la CRL Ã¨ valida!?
					try {
						qualifiedCertificateCRL = crlStorage.retriveCRL(qualifiedCertificate);
					} catch (CryptoStorageException e) {
					}
					if (qualifiedCertificateCRL != null) {
						isQualified = checkCRL(validationInfos, signatureCertificate, qualifiedCertificateCRL, referenceDate);
						// non aggiungo errori anche se il controllo Ã¨ negativo il cert non Ã¨ disponibile
						// ma il controllo finale risulta superato!?
					} else {
						// Se la CRL non era presente nello storage
						// verifico rispetto al distribution point
						Vector<String> urlCRLDistributionPoints = null;
						try {
							urlCRLDistributionPoints = signerUtil.getURLCrlDistributionPoint(qualifiedCertificate);
						} catch (CryptoSignerException e) {
							// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
							// tengo traccia dell'errore ma considero il certificato comunque accreditato
							// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
							validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_NOT_EXECUTED));
							validationInfos.addWarningWithCode(CertMessage.CERT_CA_CRL_NOT_EXECUTED,
									MessageHelper.getMessage(CertMessage.CERT_CA_CRL_NOT_EXECUTED));
							isQualified = true;
						} catch (Exception e) {
							// Non e stato possibile validare il certificato di certificazione rispetto alle CRL
							// tengo traccia dell'errore ma considero il certificato comunque accreditato
							// (poiche presente nella lista del CNIPA e attivo al riferimento temporale)
							validationInfos.addWarning(MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
							validationInfos.addWarningWithCode(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING,
									MessageHelper.getMessage(CertMessage.CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING));
							isQualified = true;
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
										isQualified = checkCRL(validationInfos, qualifiedCertificate, envelopeCrl, referenceDate);

										// La CRL deve essere storicizzata
										try {
											crlStorage.insertCRL(envelopeCrl, caCountry, caServiceProviderName, caServiceName );
										} catch (CryptoStorageException e) {
											log.error("ERRORE nell'insermento nella cartella locale della CRL: " + e.getMessage());
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
								isQualified = true;
							}
						}

						if (contaCRLScaricate != urlCRLDistributionPoints.size()) {
							log.warn("Abbiamo scaricato " + contaCRLScaricate + " CRL" + " a fronte di " + urlCRLDistributionPoints.size()
									+ " punti di distribuzione.");
						}
					}
				}

				PublicKey publicKey = qualifiedCertificate.getPublicKey();

				// verifica che la chiave della cia non sia compromessa !?
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
			validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_INPUTEMPTY), true);
			validationInfos.addErrorWithCode(CertMessage.CERT_INPUTEMPTY, MessageHelper.getMessage(CertMessage.CERT_INPUTEMPTY), true);
		}
		if (isQualified)
			output.setProperty(OutputCertBean.CERTIFICATE_RELIABILITY_PROPERTY, qualifiedCertificate);

		// info di validazione sul fatto che il certificato Ã¨ affidabile
		output.setProperty(OutputCertBean.CA_VERIFY, validationInfos);

		result &= validationInfos.isValid();

		// if (performCounterSignaturesCheck) {
		// List<ISignature> counterSignatures = signature.getCounterSignatures();
		// result &= populateUnqualifiedSignaturesList(unqualifiedSignatureValidationInfos, issuerQualifiedCertificates, counterSignatures,
		// certificatesAuthorityStorage, referenceDate);
		// }

		return result;
	}

	private boolean checkCRL(ValidationInfos validationInfos, X509Certificate signatureCertificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(signatureCertificate);

		// il certificato e stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				// validationInfos.addError("Certificato revocato in data: " + crlEntry.getRevocationDate() + " (antecedente a: "+date+")");
				validationInfos.addErrorWithCode(CertMessage.CERT_CA_CRL_REVOKED_BEFORE,
						MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				return false;
			} else if (date == null) {
				// validationInfos.addError("Certificato gia revocato in data: " + crlEntry.getRevocationDate());
				validationInfos.addErrorWithCode(CertMessage.CERT_CA_CRL_REVOKED_AFTER,
						MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				return false;
			}
		}
		return true;
	}

}
