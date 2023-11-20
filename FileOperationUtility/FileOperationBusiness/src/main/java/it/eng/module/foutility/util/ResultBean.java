/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * contiene il risultato di una operazione eseguita
 * @author Russo
 *
 */
public class ResultBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2354247585680400785L;

	Map<String, Object> extractedProperties = new HashMap<String, Object>();
	
	ValidationInfos validationInfos = new ValidationInfos();

	/**
	 * @return the extractedProperties
	 */
	public Map<String, Object> getExtractedProperties() {
		return extractedProperties;
	}

	/**
	 * @param extractedProperties the extractedProperties to set
	 */
	public void setExtractedProperties(Map<String, Object> extractedProperties) {
		this.extractedProperties = extractedProperties;
	}

	/**
	 * @return the validationInfos
	 */
	public ValidationInfos getValidationInfos() {
		return validationInfos;
	}

	/**
	 * @param validationInfos the validationInfos to set
	 */
	public void setValidationInfos(ValidationInfos validationInfos) {
		this.validationInfos = validationInfos;
	}
	
	public void addProperty(String property, Object obj){
		if (extractedProperties==null)
			extractedProperties = new HashMap<String, Object>();
		extractedProperties.put(property, obj);
	}
	
	public Object getProperty(String property){
		if (extractedProperties==null)
			return null;
		return extractedProperties.get(property);
	}
	
	public void addError(String error){
		if (validationInfos==null)
			validationInfos = new ValidationInfos();
		validationInfos.addError(error);
	}
	
	public void addErrors(String[] errors){
		if (validationInfos==null)
			validationInfos = new ValidationInfos();
		validationInfos.addErrors(errors);
	}
	
	public void addWarning(String warning){
		if (validationInfos==null)
			validationInfos = new ValidationInfos();
		validationInfos.addWarning(warning);
	}
	
	public void addWarnings(String[] warnings){
		if (validationInfos==null)
			validationInfos = new ValidationInfos();
		validationInfos.addWarnings(warnings);
	}
	
	public boolean isValid(boolean strict){
		if(validationInfos!=null){
			return validationInfos.isValid();
		}else{
			return true;
		}
	}

	@Override
	public String toString() {
		return "ResultBean [extractedProperties=" + extractedProperties
				+ ", validationInfos=" + validationInfos + "]";
	}
	
	
}
