package it.eng.utility.cryptosigner.controller.impl.signature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorConfig;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorFactory;
import it.eng.utility.cryptosigner.controller.bean.InputBean;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Controlla se nel file sbustato ci sono macro o codice eseguibile per usare il controller occorre impostarlo sul SigManager e impostare il mimetype del file
 * da verificare
 *
 */
public class CodeDetectionValidation extends AbstractSignerController {

	/**
	 * Proprieta restituita dal metodo {@link it.eng.utility.cryptosigner.controller.impl.signature.CodeDetectionValidation#getCheckProperty getCheckProperty}
	 */
	public static final String CODE_DETECTOR_CHECK = "performCodeDetection";
	public static final String CODE_DETECTOR_PDF_COMMENTI_CHECK = "performCodeDetectionPdfCommenti";
	public static final String CODE_DETECTOR_PDF_EDITABILI_CHECK = "performCodeDetectionPdfEditabili";
	
	private static Logger log = LogManager.getLogger(CodeDetectionValidation.class);

	protected static final String MSOFFICE_KEY = "dynamicCode.msoffice";
	protected static final String MSOFFICE2007_KEY = "dynamicCode.msoffice2007";
	protected static final String MSPROJECT_KEY = "dynamicCode.msproject";
	protected static final String MSVISIO_KEY = "dynamicCode.msvisio";
	protected static final String OPENOFFICE_KEY = "dynamicCode.openoffice";
	//protected static final String PDF_KEY = "dynamicCode.pdf";

	public String getCheckProperty() {
		return CODE_DETECTOR_CHECK;
	}

	protected DynamicCodeDetectorFactory detectorFactory;
	protected static DynamicCodeDetectorConfig defaultDetectorConfig = null;

	// mime type to test direttamnte passato in input
	private MimeType mimeTypeInput;

	public boolean execute(InputSignerBean input, OutputSignerBean output, boolean eseguiValidazioni) throws ExceptionController {

		ValidationInfos vinfo = new ValidationInfos();
		// verifico se i file è firmato se è così salto il controllo
		// if(output.getContent().isPossiblySigned()){
		// log.debug("il contenuto è firmato salto la verifica");
		// output.setProperty(OutputSignerBean.CODE_DETECTION_VALIDATION_PRIOPERTY, vinfo);
		// return true;
		// }
		boolean ret = false;
		// TODO ora il mime lo devi passare altrimenti se non valorizzato si
		// dovrebbe rilevare dal file
		MimeType mimeType = getMimeTypeInput();
		log.debug("mimeType file da verificare: " + mimeType);
		if (mimeType == null) {
			// TODO mmeType non passato devi rilevarlo dal file
			vinfo.addError(MessageHelper.getMessage(MessageConstants.CD_MIMETYPE_NOTFOUND), true);
			vinfo.addErrorWithCode(MessageConstants.CD_MIMETYPE_NOTFOUND, MessageHelper.getMessage(MessageConstants.CD_MIMETYPE_NOTFOUND), true);
			output.setProperty(OutputSignerBean.CODE_DETECTION_VALIDATION_PRIOPERTY, vinfo);
			return ret;
		}

		// elimino al specificità se presente
		try {
			mimeType = new MimeType(mimeType.getBaseType());
		} catch (MimeTypeParseException e) {
			log.warn("MimeTypeParseException ", e);
		}

		// prendo i file sbustato
		File file = null;
		try {
			file = File.createTempFile("temp", "sbustato", input.getTempDir());
			log.debug("file temp: " + file.getAbsolutePath());
			FileOutputStream outputstream = new FileOutputStream(file);
			IOUtils.copyLarge(output.getContent().getContentStream(), outputstream);
			outputstream.close();
			//output.getContent().getSigner().closeFileStream();
			output.getContent().setExtractedFile(file);
		} catch (Exception ex) {
			vinfo.addError(ex.getMessage(), true);
			output.setProperty(OutputSignerBean.CODE_DETECTION_VALIDATION_PRIOPERTY, vinfo);
			return false;
		}

		setupDynamicCodeDetectorConfig();
		// Definisce la configurazione del controller
		Properties configProperties = new Properties();
		DynamicCodeDetectorConfig detectorConfig = null;
		try {
			log.debug("Chiamo il setup");
			detectorConfig = setUpConfig(configProperties, input);
		} catch (Exception e1) {
			log.fatal("fatal initial setup ", e1);
			vinfo.addError(e1.getMessage(), true);
			output.setProperty(OutputSignerBean.CODE_DETECTION_VALIDATION_PRIOPERTY, vinfo);
			return ret;
		}
		detectorFactory = new DynamicCodeDetectorFactory(detectorConfig);

		synchronized (detectorFactory) {

			// Effettua i controlli

			DynamicCodeDetector detector = null;
			try {
				detector = detectorFactory.getDetectorByMimeType(mimeType);
				log.debug("detector " + detector);

				if (detector != null) {
					it.eng.suiteutility.dynamiccodedetector.ValidationInfos dynamicValidationInfos = detector.detect(file, mimeType);
					if (dynamicValidationInfos != null) {
						if (!dynamicValidationInfos.isValid()) {

							// contiene codice eseguibile
							vinfo.addErrors(dynamicValidationInfos.getErrors());
							for (String error : dynamicValidationInfos.getErrors()) {
								vinfo.addErrorWithCode("", error);
							}
							if( dynamicValidationInfos.getWarnings()!=null ){
								vinfo.addWarnings(dynamicValidationInfos.getWarnings());
								for (String warning : dynamicValidationInfos.getWarnings()) {
									vinfo.addWarningWithCode("", warning);
								}
							}
							log.debug("dynamicVali:" + dynamicValidationInfos);
						} else {
							ret = true;
							log.debug("il file non contiene codice eseguibile");
						}
					}
				} else {
					// vinfo.addError( MessageHelper.getMessage( MessageConstants.CD_MIMETYPE_NOTVERIFIABLE, mimeType),true);
					// vinfo.addErrorWithCode( MessageConstants.CD_MIMETYPE_NOTVERIFIABLE, MessageHelper.getMessage( MessageConstants.CD_MIMETYPE_NOTVERIFIABLE,
					// mimeType),true);
					log.info("Nessun detector configurato, formato per cui la verifica e' inutile");
					ret = true;
				}
			} catch (DynamicCodeDetectorException e) {
				vinfo.addError(e.getMessage(), true);
			}
		}
		output.setProperty(OutputSignerBean.CODE_DETECTION_VALIDATION_PRIOPERTY, vinfo);
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
			log.warn("DynamicCodeDetectorException ", e);
		} catch (SAXException e) {
			log.warn("SAXException ", e);
		} catch (IOException e) {
			log.warn("IOException ", e);
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

	protected DynamicCodeDetectorConfig setUpConfig(Properties config, InputSignerBean input) throws DynamicCodeDetectorException, SAXException, IOException {
		String msOfficeFlag = config.getProperty(MSOFFICE_KEY, "true");
		String msOffice2007Flag = config.getProperty(MSOFFICE2007_KEY, "true");
		String msProjectFlag = config.getProperty(MSPROJECT_KEY, "true");
		String msVisioFlag = config.getProperty(MSVISIO_KEY, "true");
		String openOfficeFlag = config.getProperty(OPENOFFICE_KEY, "true");
		boolean pdfFlag = false;
		boolean pdfFlagCommenti = getCheckValue(input, CODE_DETECTOR_PDF_COMMENTI_CHECK);
		boolean pdfFlagEditabili = getCheckValue(input, CODE_DETECTOR_PDF_EDITABILI_CHECK);
		
		if( pdfFlagCommenti )//|| pdfFlagEditabili)
			pdfFlag = true;
		
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
						|| ("PDFCommentiDetector".equals(detectorName) && true==(pdfFlagCommenti))
						|| ("PDFEditableDetector".equals(detectorName) && true==(pdfFlagEditabili))
						|| ("PDFDetector".equals(detectorName) && true==pdfFlag))
					dynamicConfigMap.put(key, detectors);
			}
		}
		
		log.debug("Mappa detectors " + dynamicConfigMap);
		DynamicCodeDetectorConfig detectorConfig = new DynamicCodeDetectorConfig(dynamicConfigMap);
		return detectorConfig;
	}

	public MimeType getMimeTypeInput() {
		return mimeTypeInput;
	}

	public void setMimeTypeInput(MimeType mimeTypeInput) {
		this.mimeTypeInput = mimeTypeInput;
	}
	
	

}
