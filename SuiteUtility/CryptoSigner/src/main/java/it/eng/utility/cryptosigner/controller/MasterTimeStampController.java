package it.eng.utility.cryptosigner.controller;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;

import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.TimeStampValidityBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

import java.io.File;
import java.security.Security;
import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TimeStampToken;

public class MasterTimeStampController {

	private static Logger log = LogManager.getLogger(MasterTimeStampController.class);
	
	// Controller da invocare per l'analisi
	private List<ISignerController> controllers;

	// Mappa dei flag indicanti i controlli da effettuare
	private Map<String,Boolean> checks;
	
	// Lista delle crl 
	private CRL crl;
	
	// Indica se uno dei controlli bloccanti non e andato a buon fine
	private boolean interrupted = false;
	
	private ITimeStampValidator timeStampValidator;
	
	private List<TimeStampValidityBean> timeStampValidity;

	static{
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null){
			log.info("-----aggiungo il provider ");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}
	
	/**
	 * Recupera i controller configurati
	 * @return i controller configurati
	 */
	public List<ISignerController> getControllers() {
		return controllers;
	}

	/**
	 * Definisce i controller su cui effettuare l'analisi
	 * @param controllers la lista dei controlli cui cui iterare l'analisi
	 */
	public void setControllers(List<ISignerController> controllers) {
		this.controllers = controllers;
	}
	
	/**
	 * Effettua l'analisi richiamando l'esecuzione di ciascun 
	 * controller configurato. 
	 * @param input bean contenente le informazioni in input per eseguire i controlli
	 * @return bean
	 * @throws ExceptionController
	 */
	public OutputTimeStampBean executeControll(InputTimeStampBean input, boolean childValidation)throws ExceptionController{
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null){
			log.info("-----aggiungo il provider ");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		OutputTimeStampBean output = new OutputTimeStampBean();
		this.execute(input, output, childValidation);
		return output;
	}
	
	/**
	 * Esegue la sequenza di controlli sul bean di input, iterandoli 
	 * sul contenuto qualora esso risulti ulteriormente firmato
	 *
	 */
	private void execute(InputTimeStampBean input,OutputTimeStampBean output, boolean childValidation)throws ExceptionController{
		boolean result;
		input.setChecks(checks);
		input.setCrl(crl);
		log.debug("Controller da eseguire " + controllers);
		for (ISignerController controller:controllers) {
			log.debug("Eseguo il controller " + controller );
			if (controller.canExecute(input)) {
				try {
					long start = System.currentTimeMillis();
					result = controller.execute(input, output, childValidation);
					log.debug( "Esito esecuzione controllo " + result );
					if (!result && controller.isCritical()) {
						interrupted = true;
						break;
					}
					long elapsedTimeMillis = System.currentTimeMillis()-start;
					log.debug("Controllo: " + controller.getClass().getSimpleName() + " eseguito con successo in " + elapsedTimeMillis + "ms");
				}catch(ExceptionController e) {
					if (controller.isCritical()) {
						interrupted = true;
						throw e;
					}
				}
			}
		}
		
		// Occorre controllare le estensioni delle marche temporali
		// quindi iterare i controlli su ciascuna marca
		checkExtensions(input, output, childValidation);
	}
	
	private void checkExtensions(InputTimeStampBean input, OutputTimeStampBean output, boolean childValidation)throws ExceptionController{
		
		// Recupero la catena delle estensioni
		File[] timeStampExtensionChain = input.getTimeStampExtensionsChain();
		File timestampFile = input.getTimeStampWithContentFile()==null ? 
				input.getTimeStampFile() : 
				input.getTimeStampWithContentFile();
		
//		if (input.getTimeStampExtensionsChain()!=null) {
			// Recupero i timestamp 
			List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos = output.getDocumentAndTimeStampInfos();
			if (documentAndTimeStampInfos==null || documentAndTimeStampInfos.size()==0)
				return;
			
			// Se non ci sono estensioni della marca temporale
			// controllo la validita attuale della marca
			if (timeStampExtensionChain==null || timeStampExtensionChain.length==0) {
				for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo: documentAndTimeStampInfos ) {
					TimeStampToken timeStampToken = documentAndTimeStampInfo.getTimeStampToken();
					ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
					TimeStampValidityBean timeStampValidityBean = getTimeStampValidityForTimeStampToken(timeStampToken);
					if (!timeStampValidator.isTimeStampCurrentlyValid(timeStampToken, timeStampValidityBean)) {
						Date referenceDate = input.getReferenceDate();
						if (referenceDate==null){
							validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_NOTVALID, MessageHelper.getMessage( MessageConstants.SIGN_MARK_NOTVALID ) );
							validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_MARK_NOTVALID ) );
						} else{
							if (timeStampValidator.isTimeStampValidAtDate(timeStampToken, timeStampValidityBean, referenceDate)){
								validationInfos.addWarning( MessageHelper.getMessage( MessageConstants.SIGN_MARK_EXTENDED_VALIDITY, referenceDate ) );
								validationInfos.addWarningWithCode( MessageConstants.SIGN_MARK_EXTENDED_VALIDITY, MessageHelper.getMessage( MessageConstants.SIGN_MARK_EXTENDED_VALIDITY, referenceDate ) );
							} else {
								validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_NOTVALID_TIME, MessageHelper.getMessage( MessageConstants.SIGN_MARK_NOTVALID_TIME, referenceDate) );
								validationInfos.addError( MessageHelper.getMessage( MessageConstants.SIGN_MARK_NOTVALID_TIME, referenceDate) );
							}
						}
					}
				}
			}
			
			// Se ci sono estesioni della marca temporale
			// controllo che siano corrette
			else{
				validateTimeStampsChain(documentAndTimeStampInfos, timestampFile, timeStampExtensionChain, childValidation);
			}
			
//		}
	}
	

	/**
	 * @return the timeStampValidator
	 */
	public ITimeStampValidator getTimeStampValidator() {
		return timeStampValidator;
	}

	/**
	 * @param timeStampValidator the timeStampValidator to set
	 */
	public void setTimeStampValidator(ITimeStampValidator timeStampValidator) {
		this.timeStampValidator = timeStampValidator;
	}

	/**
	 * @return the timeStampValidity
	 */
	public List<TimeStampValidityBean> getTimeStampValidity() {
		return timeStampValidity;
	}

	/**
	 * @param timeStampValidity the timeStampValidity to set
	 */
	public void setTimeStampValidity(List<TimeStampValidityBean> timeStampValidity) {
		this.timeStampValidity = timeStampValidity;
	}
	
	
	private TimeStampValidityBean getTimeStampValidityForTimeStampToken(TimeStampToken timeStampToken) {
		if (timeStampValidity == null || timeStampValidity.size()==0 || timeStampToken==null)
			return null;
		TimeStampValidityBean result = null;
		Iterator<TimeStampValidityBean> iterator = timeStampValidity.iterator();
		Calendar timeStampTokenCalendar = Calendar.getInstance();
		timeStampTokenCalendar.setTime(timeStampToken.getTimeStampInfo().getGenTime());
		Calendar tmpCal = Calendar.getInstance();
		while (iterator.hasNext()) {
			TimeStampValidityBean timeStampValidityBean = iterator.next();
			if (timeStampValidityBean.getBegin()==null)
				result=timeStampValidityBean;
			else{
				tmpCal.setTime(timeStampValidityBean.getBegin());
				if (tmpCal.before(timeStampTokenCalendar))
					result=timeStampValidityBean;
				else
					break;
			}
		}
		return result;
	}

	
	private void validateTimeStampsChain(
			List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos,
			File signedFile, File[] timeStampExtensionChain, boolean childValidation) throws ExceptionController {

		//Controllo se esiste una catena di estensioni del timestamp
		if (timeStampExtensionChain==null || timeStampExtensionChain.length==0)
			return;
		
		//Recupero i timestampToken presenti nel documento
		List<TimeStampToken> timeStampTokens = new ArrayList<TimeStampToken>();		
		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo: documentAndTimeStampInfos) {
			timeStampTokens.add(documentAndTimeStampInfo.getTimeStampToken());
		}
		
		// recupero la prima estensione
		File firstExtension = timeStampExtensionChain[0];
		InputTimeStampBean input = new InputTimeStampBean();
		input.setChecks(checks);
		input.setTimeStampFile(firstExtension);
		input.setContentFile(signedFile);

		if (timeStampExtensionChain.length>1) {
			File[] extensionChainTail = new File[timeStampExtensionChain.length-1];
			System.arraycopy(timeStampExtensionChain, 1, extensionChainTail, 0, timeStampExtensionChain.length-1);
			input.setTimeStampExtensionsChain(extensionChainTail);
		}
		try {
			OutputTimeStampBean output = executeControll(input, childValidation);
			
			List<DocumentAndTimeStampInfoBean> documentAndTimeStampExtensionInfos = output.getDocumentAndTimeStampInfos();
			if (documentAndTimeStampExtensionInfos==null || documentAndTimeStampExtensionInfos.size()==0)
				throw new ExceptionController("L'analisi non ha dato alcun riscontro");
			
			// Recupero i timestamp presenti nell'estensione della marca
			List<TimeStampToken> timeStampExtensions = new ArrayList<TimeStampToken>();
			for (DocumentAndTimeStampInfoBean documentAndTimeStampExtensionInfo: documentAndTimeStampExtensionInfos) {
				ValidationInfos extensionValidationInfos = documentAndTimeStampExtensionInfo.getValidationInfos();
				if (!extensionValidationInfos.isValid())
					throw new ExceptionController(extensionValidationInfos.toString());
				timeStampExtensions.add(documentAndTimeStampExtensionInfo.getTimeStampToken());
			}
			// Valido l'estensione del periodo di validita
			File timestampFile = input.getTimeStampWithContentFile()==null ? input.getTimeStampFile() : input.getTimeStampWithContentFile();
			validateTimeStampExtensionListOverTimeStampList(documentAndTimeStampInfos, timeStampTokens, timeStampExtensions, firstExtension, timestampFile);
		}catch(ExceptionController e) {
			setAllValidationInfos(documentAndTimeStampInfos, new String[] { MessageHelper.getMessage( MessageConstants.SIGN_MARK_EXTENSION, firstExtension, e.getMessage() )}, null );
		}
	}
	
	private boolean validateTimeStampExtensionListOverTimeStampList(
			List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos,
			List<TimeStampToken> currentTimeStampTokens,
			List<TimeStampToken> timeStampExtensions, File timeStampExtensionFile, File currentFile) {
		boolean result = true;
		for (int i=0; i<currentTimeStampTokens.size(); i++) {			
			TimeStampToken currentTimeStampToken = currentTimeStampTokens.get(i);
			for (int j=0; j<timeStampExtensions.size(); j++) {
				TimeStampToken timeStampExtension = timeStampExtensions.get(j);
				if (!timeStampValidator.isTimeStampExtended(
							currentTimeStampToken, 
							getTimeStampValidityForTimeStampToken(currentTimeStampToken), 
							timeStampExtension, 
							getTimeStampValidityForTimeStampToken(timeStampExtension)
						)
					) {
					setAllValidationInfos(documentAndTimeStampInfos, 
						new String[]{"L'estensione della marca temporale #" + (j+1) + " contenuta nel file: " + 
							timeStampExtensionFile + 
							" (" + timeStampExtension.getTimeStampInfo().getGenTime() + ")"+
							" non estende la marca temporale #" + (i+1) + " contenuta nel file: " + 
							currentFile + 
							" (" + currentTimeStampToken.getTimeStampInfo().getGenTime() + ")"
						}, 
						null
					);
					result = false;
				}
			}
		}
		return result;
	}
	
	private void setAllValidationInfos(List<DocumentAndTimeStampInfoBean> documentAndTimeStampInfos, String[] errors, String[] warnings) {
		if (documentAndTimeStampInfos==null)
			return;
		for (DocumentAndTimeStampInfoBean documentAndTimeStampInfo: documentAndTimeStampInfos) {
			if (documentAndTimeStampInfo.getValidationInfos()==null)
				documentAndTimeStampInfo.setValidationInfos(new ValidationInfos());
			ValidationInfos validationInfos = documentAndTimeStampInfo.getValidationInfos();
			validationInfos.addErrors(errors);
			for (String error: errors) {
				validationInfos.addErrorWithCode("", error);
			}
			validationInfos.addWarnings(warnings);
			for (String warning: warnings) {
				validationInfos.addWarningWithCode("", warning );
			}
		}
	}

	/**
	 * @return the checks
	 */
	public Map<String, Boolean> getChecks() {
		return checks;
	}

	/**
	 * @param checks the checks to set
	 */
	public void setChecks(Map<String, Boolean> checks) {
		this.checks = checks;
	}

	/**
	 * @return the crl
	 */
	public CRL getCrl() {
		return crl;
	}

	/**
	 * @param crl the crl to set
	 */
	public void setCrl(CRL crl) {
		this.crl = crl;
	}

	/**
	 * @return the interrupted
	 */
	public boolean isInterrupted() {
		return interrupted;
	}
}