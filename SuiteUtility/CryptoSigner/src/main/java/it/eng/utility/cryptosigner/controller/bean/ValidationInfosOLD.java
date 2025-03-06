package it.eng.utility.cryptosigner.controller.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean contenenti informazioni sull'esito di una verifica, in termini di 
 * errori e avvisi che sono stati generati
 * @author Administrator
 *
 */
public class ValidationInfosOLD {
	
//	Object validatedObject;
	
	ArrayList<String> warnings = new ArrayList<String>();
	
	ArrayList<String> errors = new ArrayList<String>();
	
	/**
	 * Abbreviazione per errors.isEmpty()
	 * @return booelan
	 */
	public boolean isValid() {
		return (errors==null || errors.size()==0);
	}
	
	/**
	 * Verifica la presenza di errori e avvisi
	 * @param strict verifica anche la presenza di avvisi
	 * @return true se non sono presenti erorri ed eventualmente avvisi
	 */
	public boolean isValid(boolean strict) {
		return strict ? isValid() && (warnings==null || warnings.size()==0) : isValid();
	}
	
	/**
	 * Aggiunge un nuovo avviso a quelli presenti
	 * @param message
	 */
	public void addWarning(String message) {
		this.warnings.add(message);
	}
	
	/**
	 * Aggiunge una lista di avvisi a quelli presenti
	 * @param warnings
	 */
	public void addWarnings(String[] warnings) {
		if (warnings!=null && warnings.length!=0)
			this.warnings.addAll(Arrays.asList(warnings));
	}
	
	/**
	 * Aggiunge un errore a quelli presenti
	 * @param error
	 */
	public void addError(String error) {
		this.errors.add(error);
	}

	/**
	 * Aggiunge una lista di errori a quelli presenti
	 * @param errors
	 */
	public void addErrors(String[] errors) {
		if (errors!=null && errors.length!=0)
			this.errors.addAll(Arrays.asList(errors));
	}
	
	/**
	 * Recupera gli avvisi presenti
	 * @return list
	 */
	public List<String> getWarnings() {
		return warnings;
	}

	/**
	 * Recupera gli errori presenti
	 * @return list
	 */
	public List<String> getErrors() {
		return errors;
	}
	
	public String toString() {
//		return "[Validated " + validatedObject +"] warnings: " + warnings +", errors: " + errors +", isValid: " + isValid();
		return "warnings: " + warnings +", errors: " + errors +", isValid: " + isValid();
	}

//	public Object getValidatedObject() {
//		return validatedObject;
//	}
//
//	public void setValidatedObject(Object validatedObject) {
//		this.validatedObject = validatedObject;
//	}
}
