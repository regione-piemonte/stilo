/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.ResponseCodeDetector;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CheckPdfCommenti;
import it.eng.module.foutility.util.CheckPdfEditabili;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorConfig;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorFactory;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;

/**
 * determina se un file contiene codice eseguibile
 *
 */
public class CodeDetectorCtrl extends AbstractFileController {

	public static final Logger log = LogManager.getLogger(CodeDetectorCtrl.class);

	// public static final String CHECK_OID_ROOT = "checkOidRoot";
	// public static final String COMPONENTS_TO_CHECK = "componentsToCheck";
	// public static final String PREVIOUS_RESULT = "previousResult";
	//
	// public static final String DETECTED_COMPONENTS = "DetectedComponents";

	protected static final String MSOFFICE_KEY = "dynamicCode.msoffice";
	protected static final String MSOFFICE2007_KEY = "dynamicCode.msoffice2007";
	protected static final String MSPROJECT_KEY = "dynamicCode.msproject";
	protected static final String MSVISIO_KEY = "dynamicCode.msvisio";
	protected static final String OPENOFFICE_KEY = "dynamicCode.openoffice";
	//protected static final String PDF_KEY = "dynamicCode.pdf";

	protected DynamicCodeDetectorFactory detectorFactory;

	public String operationType;

	protected static DynamicCodeDetectorConfig defaultDetectorConfig = null;

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey) {
		boolean ret = false;
		ResponseCodeDetector response = new ResponseCodeDetector();

		log.debug(requestKey + " - Metodo execute di CodeDetectorCtrl ");

		// leggo il file in ingresso che e' lo sbustato se il file in input era firmato o il originale se non lo era
		File file = output.getPropOfType(UnpackCtrl.EXTRACTED_FILE, File.class);
		if (file == null) {
			log.warn( requestKey+ " - file sbustato non trovato uso il file di input");
			file = input.getInputFile();
		}
		log.info(requestKey + " - Elaborazione file " + file);

		// leggo il formato come risultato di un controllo eseguito in precedenza
		MimeType mimeType = output.getPropOfType(FormatRecognitionCtrl.DETECTED_MIME, MimeType.class);
		log.debug(requestKey+ " - Mimetype determinato: " + mimeType);
		if (mimeType == null) {
			// mmeType non determinato
			OutputOperations.addError(response, FileOpMessage.CD_OP_MIMETIPE_NOTFOUND, VerificationStatusType.ERROR);
			output.addResult(this.getClass().getName(), response);
			return ret;
		}
		// elimino al specificità se presente
		try {
			mimeType = new MimeType(mimeType.getBaseType());
		} catch (MimeTypeParseException e) {
			log.warn(requestKey+ " - Eccezione CodeDetectorCtrl", e);
		}
		log.debug(requestKey+ " - Mimetype: " + mimeType);

		setupDynamicCodeDetectorConfig();

		// Definisce la configurazione del controller
		Properties configProperties = new Properties();
		DynamicCodeDetectorConfig detectorConfig = null;
		try {
			log.debug("Chiamo il setup");
			detectorConfig = setUpConfig(requestKey, configProperties);
		} catch (Exception e1) {
			log.fatal(requestKey+ " - fatal initial setup ", e1);
			OutputOperations.addError(response, FileOpMessage.CD_OP_ERROR, VerificationStatusType.ERROR, e1.getMessage());
			output.addResult(this.getClass().getName(), response);
			return ret;
		}
		detectorFactory = new DynamicCodeDetectorFactory(detectorConfig);

		synchronized (detectorFactory) {

			// Effettua i controlli
			DynamicCodeDetector detector = null;
			try {
				detector = detectorFactory.getDetectorByMimeType(mimeType);
				log.debug("detector: " + detector);

				if (detector != null) {
					// per office se il file non contiene lal giusta estensione non converte
					// correttamente per cui lo rinomino
					// il file viene rinominato prima durante il riconoscimento formato
					// se i file non possiede l'estensione richiesta non viene trasformato correttamente
					// String officeFileRealFormat = FormatUtils.officeMimeTypes2OfficeFormat.getProperty(mimeType.toString());
					// File newFilename = new File(FilenameUtils.removeExtension(file.getAbsolutePath()) + "t." + officeFileRealFormat);
					// boolean resRen = file.renameTo(newFilename);
					// if(resRen){
					// file=newFilename;
					// }
					ValidationInfos dynamicValidationInfos = detector.detect(file, mimeType);

					if (dynamicValidationInfos != null) {
						if (!dynamicValidationInfos.isValid()) {
							// contiene codice eseguibile
							OutputOperations.addErrors(response, FileOpMessage.CD_OP_ERROR, dynamicValidationInfos.getErrors(), VerificationStatusType.KO);
							OutputOperations.addWarnings(response, FileOpMessage.CD_OP_WARNING, dynamicValidationInfos.getWarnings());
							log.error(requestKey+ " - Il file contiene codice eseguibile ");
						} else {
							response.setVerificationStatus(VerificationStatusType.OK);
							OutputOperations.addWarnings(response, FileOpMessage.CD_OP_WARNING, dynamicValidationInfos.getWarnings());
							OutputOperations.addErrors(response, FileOpMessage.CD_OP_ERROR, dynamicValidationInfos.getErrors(), VerificationStatusType.OK);
							log.info(requestKey+ " - il file non contiene codice eseguibile");
							ret = true;
						}
					}
				} else {
					// OutputOperations.addError(response, FileOpMessage.CD_OP_MIMETIPE_NOTVERIFIABLE, mimeType);
					// response.setVerificationStatus(VerificationStatusType.ERROR);
					log.info(requestKey+ " - Nessun detector configurato, formato per cui la verifica e' inutile");
					response.setVerificationStatus(VerificationStatusType.OK);
				}
			} catch (DynamicCodeDetectorException e) {
				OutputOperations.addError(response, FileOpMessage.CD_OP_ERROR, VerificationStatusType.KO, e.getMessage());
			}
		}
		output.addResult(this.getClass().getName(), response);
		return ret;
	}

	protected File getConfigFile() {
		return new File(DynamicCodeDetectorConfig.class.getResource("/it/eng/suiteutility/dynamiccodedetector/config/config.xml").getFile());
	}

	protected InputStream getConfig() {
		return DynamicCodeDetectorConfig.class.getResourceAsStream("/it/eng/suiteutility/dynamiccodedetector/config/config.xml");
	}

	public DynamicCodeDetectorConfig getDefaultDetectorConfig() {
		return defaultDetectorConfig;
	}

	public void setDefaultDetectorConfig(DynamicCodeDetectorConfig config) {
		defaultDetectorConfig = config;
	}

	protected void setupDynamicCodeDetectorConfig() {
		try {
			DynamicCodeDetectorConfig config = getDefaultDetectorConfig();
			if (config == null) {
				// config = new DynamicCodeDetectorConfig(getConfigFile());
				config = new DynamicCodeDetectorConfig(getConfig());
				setDefaultDetectorConfig(config);
			}
		} catch (DynamicCodeDetectorException e) {
			log.warn("Eccezione setupDynamicCodeDetectorConfig", e);
		} catch (SAXException e) {
			log.warn("Eccezione setupDynamicCodeDetectorConfig", e);
		} catch (IOException e) {
			log.warn("Eccezione setupDynamicCodeDetectorConfig", e);
		}
	}

	// public DynamicCodeController(){
	// super();
	// }
	//
	// public DynamicCodeController(File msgFolder){
	// super(msgFolder);
	// setupDynamicCodeDetectorConfig();
	// }

	protected DynamicCodeDetectorConfig setUpConfig(String requestKey, Properties config) throws DynamicCodeDetectorException, SAXException, IOException {
		String msOfficeFlag = config.getProperty(MSOFFICE_KEY, "true");
		String msOffice2007Flag = config.getProperty(MSOFFICE2007_KEY, "true");
		String msProjectFlag = config.getProperty(MSPROJECT_KEY, "true");
		String msVisioFlag = config.getProperty(MSVISIO_KEY, "true");
		String openOfficeFlag = config.getProperty(OPENOFFICE_KEY, "true");
		boolean isVerificaPdfAttiva = false;
		boolean isVerificaPdfCommentiAttiva = CheckPdfCommenti.isAttivaVerificaPdfCommenti();
		log.debug(requestKey + " - Verifica pdf con commenti attiva? " + isVerificaPdfCommentiAttiva);
		
		boolean isVerificaPdfEditabiliAttiva = CheckPdfEditabili.isAttivaVerificaPdfEditable();
		log.debug(requestKey + " - Verifica pdf editabili attiva? " + isVerificaPdfEditabiliAttiva);
		
		if( isVerificaPdfCommentiAttiva )//|| isVerificaPdfEditabiliAttiva)
			isVerificaPdfAttiva = true;
		log.debug(requestKey + " - Verifica pdf attiva? " + isVerificaPdfAttiva);
		
		
		Map<String, Collection<DynamicCodeDetector>> dynamicConfigMap = new HashMap<String, Collection<DynamicCodeDetector>>();
		Map<String, Collection<DynamicCodeDetector>> defaultDynamicConfigMap = getDefaultDetectorConfig().getMimeTypesDetectorAssociation();
		Set<String> keyset = defaultDynamicConfigMap.keySet();
		for (String key : keyset) {
			Collection<DynamicCodeDetector> detectors = defaultDynamicConfigMap.get(key);
			for (DynamicCodeDetector detector : detectors) {
				String detectorName = detector.getClass().getSimpleName();
				if (("OfficeMacroDetector".equals(detectorName) && "true".equalsIgnoreCase(msOfficeFlag))
						|| ("Office2007MacroDetector".equals(detectorName) && "true".equalsIgnoreCase(msOffice2007Flag))
						|| ("ProjectMacroDetector".equals(detectorName) && "true".equalsIgnoreCase(msProjectFlag))
						|| ("VisioMacroDetector".equals(detectorName) && "true".equalsIgnoreCase(msVisioFlag))
						|| ("OpenOfficeMacroDetector".equals(detectorName) && "true".equalsIgnoreCase(openOfficeFlag))
						|| ("PDFCommentiDetector".equals(detectorName) && true==isVerificaPdfCommentiAttiva )
						|| ("PDFEditableDetector".equals(detectorName) && true==isVerificaPdfEditabiliAttiva)
						|| ("PDFDetector".equals(detectorName) && true==isVerificaPdfAttiva))
					dynamicConfigMap.put(key, detectors);
			}
		}
		log.debug("Mappa detectors " + dynamicConfigMap);
		DynamicCodeDetectorConfig detectorConfig = new DynamicCodeDetectorConfig(dynamicConfigMap);
		return detectorConfig;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
