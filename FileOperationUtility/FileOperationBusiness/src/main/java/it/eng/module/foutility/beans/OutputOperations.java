/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType.ErrorsMessage;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType.Warnings;
import it.eng.module.foutility.beans.generated.MessageType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.utility.cryptosigner.controller.bean.ErrorBean;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * bean che conserva l'output di tutte le operazioni
 * 
 * @author Russo
 *
 */
public class OutputOperations {

	public static final Logger log = LogManager.getLogger(OutputOperations.class);

	Map<String, AbstractResponseOperationType> controllerResult = new LinkedHashMap<String, AbstractResponseOperationType>();
	// varie proprietà memorizzate dai controller durante l'esecuzione
	Map<String, Object> extractedProperties = new HashMap<String, Object>();
	private static final String RESULT_FILE = "RESULT_FILE";

	/**
	 * @return the controllerResult
	 */
	public Map<String, AbstractResponseOperationType> getControllerResult() {
		return controllerResult;
	}

	/**
	 * @param controllerResult
	 *            the controllerResult to set
	 */
	public void setControllerResult(Map<String, AbstractResponseOperationType> controllerResult) {
		this.controllerResult = controllerResult;
	}

	public void addResult(String controllerName, AbstractResponseOperationType result) {
		if (controllerResult == null) {
			controllerResult = new HashMap<String, AbstractResponseOperationType>();
		}
		if (controllerResult.containsKey(controllerName)) {
			AbstractResponseOperationType resultbean = controllerResult.get(controllerName);
			// resultbean.getExtractedProperties().putAll(result.getExtractedProperties());
			// if(result.getValidationInfos()!=null){
			// if(result.getValidationInfos().getErrors()!=null){
			// resultbean.addErrors(result.getValidationInfos().getErrors());
			// }
			// if(result.getValidationInfos().getWarnings()!=null){
			// resultbean.addWarnings(result.getValidationInfos().getWarnings());
			// }
			// }
		} else {
			controllerResult.put(controllerName, result);
		}
	}

	public AbstractResponseOperationType getResult(String controllername) {
		return controllerResult == null ? null : controllerResult.get(controllername);
	}

	@Override
	public String toString() {
		return "OutputOperations [controllerResult=" + controllerResult + "]";
	}

	public List<AbstractResponseOperationType> getResponses() {
		List<AbstractResponseOperationType> ret = new ArrayList<AbstractResponseOperationType>();
		ret.addAll(getControllerResult().values());
		return ret;
	}

	public static void addErrors(AbstractResponseOperationType result, String errorCode, String[] messages, VerificationStatusType status) {
		if (messages == null) {
			return;
		}
		if (result.getErrorsMessage() == null) {
			ErrorsMessage em = new ErrorsMessage();
			result.setErrorsMessage(em);
		}
		// Collections.addAll( result.getErrorsMessage().getErrMessage(),messages);
		for (String msg : messages) {
			MessageType message = new MessageType();
			message.setCode(errorCode);
			message.setDescription(MessageHelper.getMessage(errorCode, msg));
			log.error("Errore: " + errorCode + " " + message.getDescription());
			result.getErrorsMessage().getErrMessage().add(message);
		}

		if (status != null) {
			// imposto lo stato ad error sul risultato poichè l'errore non consente l'esecuzione
			// dell'operazione
			result.setVerificationStatus(status);
		}
	}

	public static void addErrors(AbstractResponseOperationType result, List<ErrorBean> errors, VerificationStatusType status) {
		if (result.getErrorsMessage() == null) {
			ErrorsMessage em = new ErrorsMessage();
			result.setErrorsMessage(em);
		}
		for (ErrorBean error : errors) {
			MessageType message = new MessageType();
			message.setCode(error.getCode());
			message.setDescription(error.getDescription());
			log.error("Errore: " + error.getCode() + " " + error.getDescription());
			result.getErrorsMessage().getErrMessage().add(message);
		}

		if (status != null) {
			// imposto lo stato ad error sul risultato poichè l'errore non consente l'esecuzione
			// dell'operazione
			result.setVerificationStatus(status);
		}
	}

	/**
	 * aggiunge un errore alla risposta
	 * 
	 * @param result
	 * @param message
	 */
	public static void addError(AbstractResponseOperationType response, String errorCode, Object... params) {
		addError(response, errorCode, null, params);
	}

	public static void addError(AbstractResponseOperationType response, String errorCode, VerificationStatusType status, Object... params) {
		if (errorCode == null) {
			return;
		}
		if (response.getErrorsMessage() == null) {
			ErrorsMessage em = new ErrorsMessage();
			response.setErrorsMessage(em);
		}
		// response.getErrorsMessage().getErrMessage().add(message);
		MessageType messageType = new MessageType();
		messageType.setCode(errorCode);
		messageType.setDescription(MessageHelper.getMessage(errorCode, params));
		log.error("Errore: " + errorCode + " " + messageType.getDescription());
		response.getErrorsMessage().getErrMessage().add(messageType);

		if (status != null) {
			// imposto lo stato ad error sul risultato poichè l'errore non consente l'esecuzione
			// dell'operazione
			response.setVerificationStatus(status);
		}
	}

	/**
	 * aggiunge un warning
	 * 
	 * @param result
	 * @param message
	 */
	public static void addWarning(AbstractResponseOperationType result, String warningCode, Object... params) {
		if (warningCode == null) {
			return;
		}
		if (result.getWarnings() == null) {
			Warnings em = new Warnings();
			result.setWarnings(em);
		}
		// result.getWarnings().getWarnMessage().add(message);
		MessageType messageType = new MessageType();
		messageType.setCode(warningCode);
		messageType.setDescription(MessageHelper.getMessage(warningCode, params));
		log.warn("Warning: " + warningCode + " " + messageType.getDescription());
		result.getWarnings().getWarnMessage().add(messageType);

	}

	public static void addWarnings(AbstractResponseOperationType result, List<ErrorBean> messages) {
		if (messages == null) {
			return;
		}
		if (result.getWarnings() == null) {
			Warnings em = new Warnings();
			result.setWarnings(em);
		}
		// Collections.addAll( result.getWarnings().getWarnMessage(),messages);
		for (ErrorBean msg : messages) {
			MessageType message = new MessageType();
			message.setCode(msg.getCode());
			message.setDescription(msg.getDescription());
			log.warn("Warning: " + msg.getCode() + " " + msg.getDescription());
			result.getWarnings().getWarnMessage().add(message);
		}
	}

	public static void addWarnings(AbstractResponseOperationType result, String warningCode, String[] messages) {
		if (messages == null) {
			return;
		}
		if (result.getWarnings() == null) {
			Warnings em = new Warnings();
			result.setWarnings(em);
		}
		// Collections.addAll( result.getWarnings().getWarnMessage(),messages);
		for (String msg : messages) {
			MessageType message = new MessageType();
			message.setCode(warningCode);
			message.setDescription(msg);
			log.warn("Warning: " + warningCode + " " + msg);
			result.getWarnings().getWarnMessage().add(message);
		}
	}

	public void addProperty(String property, Object obj) {
		if (extractedProperties == null)
			extractedProperties = new HashMap<String, Object>();
		//log.debug("Aggiungo la property " + property );
		extractedProperties.put(property, obj);
	}

	public Object getProperty(String property) {
		//log.debug("extractedProperties " + extractedProperties);
		//log.debug("Recupero la property " + property);
		if (extractedProperties == null)
			return null;
		return extractedProperties.get(property);
	}

	public <T> T getPropOfType(String property, Class<T> clazz) {
		if (extractedProperties == null)
			return null;
		T o = null;
		try {
			o = (T) extractedProperties.get(property);
		} catch (Exception ex) {
			log.warn("GetPropOfType", ex);
		}
		return o;
	}

	public boolean isResponseInState(Class clazz, VerificationStatusType... status) {
		AbstractResponseOperationType response = getResult(clazz.getName());
		return isResponseInState(response, status);
	}

	public boolean isResponseInState(AbstractResponseOperationType response, VerificationStatusType... status) {
		if (response == null)
			return false;
		List<VerificationStatusType> statusList = Arrays.asList(status);
		return statusList.contains(response.getVerificationStatus());
	}

	public void setFileResult(File resultFile) {
		this.addProperty(RESULT_FILE, resultFile);
	}

	public File getFileResult() {
		return this.getPropOfType(RESULT_FILE, File.class);
	}
}
