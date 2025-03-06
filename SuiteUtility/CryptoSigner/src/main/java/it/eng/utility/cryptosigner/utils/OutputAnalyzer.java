package it.eng.utility.cryptosigner.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.signature.ISignature;

public class OutputAnalyzer {

	OutputSignerBean outputSignerBean;

	private static final Logger log = Logger.getLogger(OutputAnalyzer.class);

	public OutputAnalyzer(OutputSignerBean outputSignerBean) {
		this.outputSignerBean = outputSignerBean;
	}

	/**
	 * Stampa un report dettagliato della struttura che contiene l'esito delle analisi di file firmati e marcati
	 */
	public void printReport() {
		OutputSignerBean currentOutput = outputSignerBean;
		int step = 1;
		while (currentOutput != null) {
			analyzeOutputStep(currentOutput);
			currentOutput = currentOutput.getChild();
			step++;
		}
	}

	/**
	 * Recupera il numero di buste
	 * 
	 * @return il numero di buste
	 */
	public int getNumberOfEnvelopes() {
		OutputSignerBean currentOutput = outputSignerBean;
		int envelopes = 0;
		while (currentOutput != null) {
			currentOutput = currentOutput.getChild();
			envelopes++;
		}
		return envelopes;
	}

	/**
	 * Recupera il numero di firme digitali
	 * 
	 * @return il numero di firme digitali
	 */
	public int getNumberOfSignatures() {
		OutputSignerBean currentOutput = outputSignerBean;
		int nSignatures = 0;
		while (currentOutput != null) {
			List<ISignature> signatures = (List<ISignature>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			if (signatures != null) {
				nSignatures += signatures.size();
			}
			currentOutput = currentOutput.getChild();
		}
		return nSignatures;
	}

	/**
	 * Recupera il numero di controfirme
	 * 
	 * @return il numero di controfirme
	 */
	public int getNumberOfCounterSignatures() {
		OutputSignerBean currentOutput = outputSignerBean;
		int nCounterSignatures = 0;
		while (currentOutput != null) {
			List<ISignature> signatures = (List<ISignature>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
			if (signatures != null) {
				for (ISignature signature : signatures) {
					List<ISignature> counterSignatures = signature.getCounterSignatures();
					if (counterSignatures != null) {
						nCounterSignatures += counterSignatures.size();
					}
				}
			}
			currentOutput = currentOutput.getChild();
		}
		return nCounterSignatures;
	}

	/**
	 * Recupera il numero di firme marcate
	 * 
	 * @return il numero di firme marcate
	 */
	public int getNumberOfTimeStampedSignatures() {
		OutputSignerBean currentOutput = outputSignerBean;
		int nTimeStampedSignatures = 0;
		while (currentOutput != null) {
			List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos = (List<DocumentAndTimeStampInfoBean>) currentOutput
					.getProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY);
			if (documentAndTimeStampInfos != null) {
				List<ISignature> signatures = (List<ISignature>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
				if (signatures != null) {
					nTimeStampedSignatures += signatures.size();
				}
			}
			currentOutput = currentOutput.getChild();
		}
		return nTimeStampedSignatures;
	}

	/**
	 * Recupera i dati di accreditamento dei certificati di firma
	 * 
	 * @return dati accreditamento
	 */
	public ValidationInfos getQualifiedCertificateInfos() {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos result = new ValidationInfos();
		while (currentOutput != null) {
			Map<ISignature, ValidationInfos> unqualifiedSignatures = (Map<ISignature, ValidationInfos>) currentOutput
					.getProperty(OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY);
			if (unqualifiedSignatures != null) {
				Set<ISignature> unqualifiedSignatureSet = unqualifiedSignatures.keySet();
				for (ISignature unqualifiedSignature : unqualifiedSignatureSet) {
					String subject = unqualifiedSignature.getSignerBean().getSubject().getName();
					ValidationInfos unqualifiedInfos = unqualifiedSignatures.get(unqualifiedSignature);
					if (!unqualifiedInfos.isValid()) {
						result.addError(subject + " ha una certificato di firma non accreditato: " + unqualifiedInfos.getErrorsString());
					}
				}

			}
			currentOutput = currentOutput.getChild();
		}
		return result;
	}

	/**
	 * REcupera i dati di validita dei certificati (scadenza + corretta associazione)
	 * 
	 * @return dati accreditamento
	 */
	public ValidationInfos getCertificateValidityInfos() {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos result = new ValidationInfos();
		while (currentOutput != null) {
			Map<ISignature, ValidationInfos> certificateExpirations = (Map<ISignature, ValidationInfos>) currentOutput
					.getProperty(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY);
			Map<ISignature, ValidationInfos> certificateAssociation = (Map<ISignature, ValidationInfos>) currentOutput
					.getProperty(OutputSignerBean.CERTIFICATE_VALIDATION_PROPERTY);
			if (certificateExpirations != null) {
				Set<ISignature> expiredSignatureSet = certificateExpirations.keySet();
				for (ISignature expiredSignature : expiredSignatureSet) {
					String subject = expiredSignature.getSignerBean().getSubject().getName();
					ValidationInfos expirationInfos = certificateExpirations.get(expiredSignature);
					if (!expirationInfos.isValid()) {
						result.addError(subject + ": " + expirationInfos.getErrorsString());
						if (expirationInfos.getWarnings() != null)
							result.addWarning(subject + ": " + expirationInfos.getWarningsString());
					}
				}
			}
			if (certificateAssociation != null) {
				Set<ISignature> associatedSignatureSet = certificateExpirations.keySet();
				for (ISignature associatedSignature : associatedSignatureSet) {
					String subject = associatedSignature.getSignerBean().getSubject().getName();
					ValidationInfos associationInfos = certificateExpirations.get(associatedSignature);
					if (!associationInfos.isValid()) {
						result.addError(subject + ": " + associationInfos.getErrorsString());
						if (associationInfos.getWarnings() != null)
							result.addWarning(subject + ": " + associationInfos.getWarningsString());
					}
				}
			}
			currentOutput = currentOutput.getChild();
		}
		return result;
	}

	/**
	 * Recupera le informazioni sulle CRL
	 * 
	 * @return crl
	 */
	public ValidationInfos getCRLInfos() {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos result = new ValidationInfos();
		while (currentOutput != null) {
			Map<ISignature, ValidationInfos> crlValidation = (Map<ISignature, ValidationInfos>) currentOutput
					.getProperty(OutputSignerBean.CRL_VALIDATION_PROPERTY);
			if (crlValidation != null) {
				Set<ISignature> crlValidationSet = crlValidation.keySet();
				for (ISignature crlInfo : crlValidationSet) {
					String subject = crlInfo.getSignerBean().getSubject().getName();
					ValidationInfos crlValidationInfos = crlValidation.get(crlInfo);
					if (!crlValidationInfos.isValid()) {
						result.addError(subject + ": " + crlValidationInfos.getErrorsString());
						if (crlValidationInfos.getWarnings() != null)
							result.addWarning(subject + ": " + crlValidationInfos.getWarningsString());
					}
				}
			}
			currentOutput = currentOutput.getChild();
		}
		return result;
	}

	/**
	 * Recupera le informazioni sulla validita delle firme
	 * 
	 * @return dati accreditamento
	 */
	public ValidationInfos getSignatureValidationInfos() {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos result = new ValidationInfos();
		while (currentOutput != null) {
			Map<ISignature, ValidationInfos> signatureValidations = (Map<ISignature, ValidationInfos>) currentOutput
					.getProperty(OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY);
			if (signatureValidations != null) {
				Set<ISignature> signatureValidationSet = signatureValidations.keySet();
				for (ISignature signatureValidation : signatureValidationSet) {
					String subject = signatureValidation.getSignerBean().getSubject().getName();
					ValidationInfos signatureValidationInfos = signatureValidations.get(signatureValidation);
					if (!signatureValidationInfos.isValid()) {
						result.addError(subject + ": " + signatureValidationInfos.getErrorsString());
						if (signatureValidationInfos.getWarnings() != null)
							result.addWarning(subject + ": " + signatureValidationInfos.getWarningsString());
					}
				}
			}
			currentOutput = currentOutput.getChild();
		}
		return result;
	}

	public ValidationInfos getValidationResults() {
		return getValidationResults(new OutputAnalyzerFilter());
	}

	public ValidationInfos getValidationResults(OutputAnalyzerFilter filter) {
		OutputSignerBean currentOutput = outputSignerBean;
		ValidationInfos validationResults = new ValidationInfos();
		while (currentOutput != null) {
			validateOutputStep(currentOutput, validationResults, filter);
			currentOutput = currentOutput.getChild();
		}
		return validationResults;
	}

	private void injectValidationInfos(ValidationInfos from, ValidationInfos to) {
		if (from == null)
			return;
		to.addErrors(from.getErrors());
		to.addWarnings(from.getWarnings());
	}

	protected void validateOutputStep(OutputSignerBean currentOutput, ValidationInfos validationInfos, OutputAnalyzerFilter filter) {

		/*
		 * Proprieta in output
		 */

		// TimeStamp
		List<DocumentAndTimeStampInfoBean> timeStampInfos = filter.isAcceptedOutput(OutputSignerBean.TIME_STAMP_INFO_PROPERTY)
				? (List<DocumentAndTimeStampInfoBean>) currentOutput.getProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY) : null;

		// Formato busta
		String outputFormat = filter.isAcceptedOutput(OutputSignerBean.ENVELOPE_FORMAT_PROPERTY)
				? (String) currentOutput.getProperty(OutputSignerBean.ENVELOPE_FORMAT_PROPERTY) : null;

		// Firme
		List<ISignature> signatures = filter.isAcceptedOutput(OutputSignerBean.SIGNATURE_PROPERTY)
				? (List<ISignature>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_PROPERTY) : null;

		// Validita delle firme
		Map<ISignature, ValidationInfos> signatureValidations = filter.isAcceptedOutput(OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY)
				? (Map<ISignature, ValidationInfos>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY) : null;

		// Validita del formato
		ValidationInfos formatValidity = filter.isAcceptedOutput(OutputSignerBean.FORMAT_VALIDITY_PROPERTY)
				? (ValidationInfos) currentOutput.getProperty(OutputSignerBean.FORMAT_VALIDITY_PROPERTY) : null;

		// Scadenza dei certificati
		Map<ISignature, ValidationInfos> certificateExpirations = filter.isAcceptedOutput(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY)
				? (Map<ISignature, ValidationInfos>) currentOutput.getProperty(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY) : null;

		// Revoca dei certificati
		Map<ISignature, ValidationInfos> crlValidation = filter.isAcceptedOutput(OutputSignerBean.CRL_VALIDATION_PROPERTY)
				? (Map<ISignature, ValidationInfos>) currentOutput.getProperty(OutputSignerBean.CRL_VALIDATION_PROPERTY) : null;

		// Certificati accreditati
		Map<ISignature, ValidationInfos> unqualifiedSignatures = filter.isAcceptedOutput(OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY)
				? (Map<ISignature, ValidationInfos>) currentOutput.getProperty(OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY) : null;

		// Validita dei certificati
		Map<ISignature, ValidationInfos> certificateAssociation = filter.isAcceptedOutput(OutputSignerBean.CERTIFICATE_VALIDATION_PROPERTY)
				? (Map<ISignature, ValidationInfos>) currentOutput.getProperty(OutputSignerBean.CERTIFICATE_VALIDATION_PROPERTY) : null;

		// Errore durante l'esecuzione di un controllo bloccante
		String masterSignerException = filter.isAcceptedOutput(OutputSignerBean.MASTER_SIGNER_EXCEPTION_PROPERTY)
				? (String) currentOutput.getProperty(OutputSignerBean.MASTER_SIGNER_EXCEPTION_PROPERTY) : null;

		if (timeStampInfos != null && timeStampInfos.size() != 0) {
			for (DocumentAndTimeStampInfoBean timeStampInfo : timeStampInfos)
				injectValidationInfos(timeStampInfo.getValidationInfos(), validationInfos);
		}

		int i = 1;
		if (signatures != null) {
			if (masterSignerException != null && !"".equals(masterSignerException.trim())) {
				validationInfos.addErrorWithCode(MessageConstants.GM_EXECUTION_CONTROLLER,
						MessageHelper.getMessage(MessageConstants.GM_EXECUTION_CONTROLLER, masterSignerException));
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_EXECUTION_CONTROLLER, masterSignerException));
			}

			injectValidationInfos(formatValidity, validationInfos);

			for (ISignature signature : signatures) {
				injectValidationInfos(signatureValidations.get(signature), validationInfos);

				if (certificateExpirations != null)
					injectValidationInfos(certificateExpirations.get(signature), validationInfos);

				if (crlValidation != null)
					injectValidationInfos(crlValidation.get(signature), validationInfos);

				if (certificateAssociation != null)
					injectValidationInfos(certificateAssociation.get(signature), validationInfos);

				if (unqualifiedSignatures != null) {
					ValidationInfos certificateReliabilityInfo = unqualifiedSignatures.get(signature);
					if (certificateReliabilityInfo != null)
						validationInfos.addError(createCertificateReliabilityError(certificateReliabilityInfo));
				}

				List<ISignature> counterSignatures = signature.getCounterSignatures();
				if (counterSignatures != null && counterSignatures.size() != 0) {
					String signatureIndex = "[" + i + "]";
					validateCounterSignatures(validationInfos, signatureIndex, counterSignatures, signatureValidations, certificateExpirations, crlValidation,
							unqualifiedSignatures);
				}
				i++;
			}
		}
	}

	private void validateCounterSignatures(ValidationInfos validationInfos, String signatureIndex, List<ISignature> countersignatures,
			Map<ISignature, ValidationInfos> signatureValidations, Map<ISignature, ValidationInfos> certificateExpirations,
			Map<ISignature, ValidationInfos> crlValidation, Map<ISignature, ValidationInfos> unqualifiedSignatures) {
		int i = 1;
		for (ISignature countersignature : countersignatures) {
			validateCounterSignature(validationInfos, countersignature, signatureValidations, certificateExpirations, crlValidation, unqualifiedSignatures);
			List<ISignature> counterCountersignatures = countersignature.getCounterSignatures();
			if (counterCountersignatures != null && counterCountersignatures.size() != 0) {
				validateCounterSignatures(validationInfos, signatureIndex + "[" + i + "]", counterCountersignatures, signatureValidations,
						certificateExpirations, crlValidation, unqualifiedSignatures);
			}
			i++;
		}
	}

	protected void validateCounterSignature(ValidationInfos validationInfos, ISignature countersignature, Map<ISignature, ValidationInfos> signatureValidations,
			Map<ISignature, ValidationInfos> certificateExpirations, Map<ISignature, ValidationInfos> crlValidation,
			Map<ISignature, ValidationInfos> unqualifiedSignatures) {

		injectValidationInfos(signatureValidations.get(countersignature), validationInfos);

		if (certificateExpirations != null)
			injectValidationInfos(certificateExpirations.get(countersignature), signatureValidations.get(countersignature));

		if (crlValidation != null)
			injectValidationInfos(crlValidation.get(countersignature), validationInfos);

		if (unqualifiedSignatures != null) {
			ValidationInfos certificateReliabilityInfo = unqualifiedSignatures.get(countersignature);
			if (certificateReliabilityInfo != null)
				validationInfos.addError(createCertificateReliabilityError(certificateReliabilityInfo));
		}
	}

	private String createCertificateReliabilityError(ValidationInfos certificateReliabilityInfo) {
		String errorMessage = "Certificato della firma NON ACCREDITATO: ";
		String[] errors = certificateReliabilityInfo.getErrors();
		if (errors != null) {
			errorMessage += " errors:[";
			for (String error : certificateReliabilityInfo.getErrors())
				errorMessage += error + ", ";
			errorMessage += "]";
		}
		String[] warnings = certificateReliabilityInfo.getWarnings();
		if (warnings != null) {
			errorMessage += " warnings:[";
			for (String warning : certificateReliabilityInfo.getWarnings())
				errorMessage += warning + ", ";
			errorMessage += "]";
		}
		return errorMessage;
	}

	protected void analyzeOutputStep(OutputSignerBean currentOutput) {

		/*
		 * Proprieta in output
		 */
		List<DocumentAndTimeStampInfoBean> timeStampInfos = (List<DocumentAndTimeStampInfoBean>) currentOutput
				.getProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY);
		String outputFormat = (String) currentOutput.getProperty(OutputSignerBean.ENVELOPE_FORMAT_PROPERTY);
		List<ISignature> signatures = (List<ISignature>) currentOutput.getProperty(OutputSignerBean.SIGNATURE_PROPERTY);
		Map<ISignature, ValidationInfos> signatureValidations = (Map<ISignature, ValidationInfos>) currentOutput
				.getProperty(OutputSignerBean.SIGNATURE_VALIDATION_PROPERTY);
		ValidationInfos formatValidity = (ValidationInfos) currentOutput.getProperty(OutputSignerBean.FORMAT_VALIDITY_PROPERTY);
		Map<ISignature, ValidationInfos> certificateExpirations = (Map<ISignature, ValidationInfos>) currentOutput
				.getProperty(OutputSignerBean.CERTIFICATE_EXPIRATION_PROPERTY);
		Map<ISignature, ValidationInfos> crlValidation = (Map<ISignature, ValidationInfos>) currentOutput.getProperty(OutputSignerBean.CRL_VALIDATION_PROPERTY);
		Map<ISignature, ValidationInfos> unqualifiedSignatures = (Map<ISignature, ValidationInfos>) currentOutput
				.getProperty(OutputSignerBean.CERTIFICATE_UNQUALIFIED_PROPERTY);
		Map<ISignature, ValidationInfos> certificateAssociation = (Map<ISignature, ValidationInfos>) currentOutput
				.getProperty(OutputSignerBean.CERTIFICATE_VALIDATION_PROPERTY);
		String masterSignerException = (String) currentOutput.getProperty(OutputSignerBean.MASTER_SIGNER_EXCEPTION_PROPERTY);

		if (timeStampInfos == null || timeStampInfos.size() == 0) {
			log.debug("Timestamp non trovato");
		} else {
			for (DocumentAndTimeStampInfoBean timeStampInfo : timeStampInfos)
				log.debug("Marca temporale: \n" + timeStampInfo);
		}

		int nFirme = signatures == null ? 0 : signatures.size();
		log.debug("Numero di firme: " + nFirme + "\n");

		int i = 1;
		if (signatures != null) {
			log.debug("Formato della busta: " + outputFormat);
			if (masterSignerException != null && !"".equals(masterSignerException.trim())) {
				log.debug("Errore durante l'esecuzione dei controlli relativo al controller: " + masterSignerException);
			} else {
				log.debug("Esecuzione dei controlli conclusa correttamente");
			}
			log.debug("Controllo di validita temporale del formato: " + formatValidity);
			for (ISignature signature : signatures) {

				log.debug("[Firma " + i + "]");

				ValidationInfos signatureValidationInfos = signatureValidations.get(signature);
				log.debug("Validazione della firma: " + signatureValidationInfos);

				if (certificateExpirations != null) {
					ValidationInfos certificateExpirationInfos = certificateExpirations.get(signature);
					log.debug("Scadenza del certificato: " + certificateExpirationInfos);
				}

				if (crlValidation != null) {
					ValidationInfos crlValidationInfos = crlValidation.get(signature);
					log.debug("Revoca del certificato: " + crlValidationInfos);
				}

				if (certificateAssociation != null) {
					ValidationInfos certificateAssociationInfos = certificateAssociation.get(signature);
					log.debug("Associazione tra certificato di firma e issuer: " + certificateAssociationInfos);
				}

				if (unqualifiedSignatures != null) {
					ValidationInfos certificateReliabilityInfo = unqualifiedSignatures.get(signature);
					if (certificateReliabilityInfo != null)
						log.debug("Certificato di firma NON ACCREDITATO: " + certificateReliabilityInfo);
					else
						log.debug("Certificato di firma ACCREDITATO");
				}

				List<ISignature> counterSignatures = signature.getCounterSignatures();
				if (counterSignatures != null && counterSignatures.size() != 0) {
					String padding = "\t\t";
					String signatureIndex = "[" + i + "]";
					printCounterSignatures(padding, signatureIndex, counterSignatures, signatureValidations, certificateExpirations, crlValidation,
							unqualifiedSignatures);
				}
				i++;
			}
		}
	}

	protected void printCounterSignatures(String padding, String signatureIndex, List<ISignature> countersignatures,
			Map<ISignature, ValidationInfos> signatureValidations, Map<ISignature, ValidationInfos> certificateExpirations,
			Map<ISignature, ValidationInfos> crlValidation, Map<ISignature, ValidationInfos> unqualifiedSignatures) {
		log.debug("La firma " + signatureIndex + ". contiene " + countersignatures.size() + " controfirme");
		int i = 1;
		for (ISignature countersignature : countersignatures) {
			printCounterSignatureCheck(padding, countersignature, signatureValidations, certificateExpirations, crlValidation, unqualifiedSignatures);
			List<ISignature> counterCountersignatures = countersignature.getCounterSignatures();
			if (counterCountersignatures != null && counterCountersignatures.size() != 0) {
				String newPadding = padding + "\t";
				printCounterSignatures(newPadding, signatureIndex + "[" + i + "]", counterCountersignatures, signatureValidations, certificateExpirations,
						crlValidation, unqualifiedSignatures);
			}
			i++;
		}
	}

	protected void printCounterSignatureCheck(String padding, ISignature countersignature, Map<ISignature, ValidationInfos> signatureValidations,
			Map<ISignature, ValidationInfos> certificateExpirations, Map<ISignature, ValidationInfos> crlValidation,
			Map<ISignature, ValidationInfos> unqualifiedSignatures) {
		ValidationInfos countersignatureValidationInfos = signatureValidations.get(countersignature);
		log.debug(padding + " Validazione della controfirma: " + countersignatureValidationInfos);

		if (certificateExpirations != null) {
			ValidationInfos countersignatureCertificateExpirationInfos = certificateExpirations.get(countersignature);
			log.debug(padding + " Scadenza del certificato della controfirma: " + countersignatureCertificateExpirationInfos);
		}

		if (crlValidation != null) {
			ValidationInfos countersignatureCrlValidationInfos = crlValidation.get(countersignature);
			log.debug(padding + " Revoca del certificato della controfirma: " + countersignatureCrlValidationInfos);
		}

		if (unqualifiedSignatures != null) {
			ValidationInfos certificateReliabilityInfo = unqualifiedSignatures.get(countersignature);
			if (certificateReliabilityInfo != null)
				log.debug(padding + " Certificato della controfirma NON ACCREDITATO: " + certificateReliabilityInfo);
			else
				log.debug(padding + " Certificato della controfirma ACCREDITATO");
		}
	}

}
