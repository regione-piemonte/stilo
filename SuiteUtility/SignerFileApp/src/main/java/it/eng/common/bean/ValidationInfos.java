package it.eng.common.bean;

import java.util.StringTokenizer;

/**
 * Bean contenenti informazioni sull'esito di una verifica, in termini di 
 * errori e avvisi che sono stati generati
 *
 */
public class ValidationInfos {
	
String[] warnings = null;
	
	String[] errors = null;
	
	private static final String WARNINGS_CODE = "warnings";
	private static final String ERRORS_CODE = "errors";
	private static final String ISVALID_CODE = "isValid";
	
	
	/**
	 * Abbreviazione per errors.isEmpty()
	 * @return
	 */
	public boolean isValid(){
		return (errors==null || errors.length==0);
	}
	
	/**
	 * Verifica la presenza di errori e avvisi
	 * @param strict verifica anche la presenza di avvisi
	 * @return true se non sono presenti erorri ed eventualmente avvisi
	 */
	public boolean isValid(boolean strict){
		return strict ? isValid() && (warnings==null || warnings.length==0) : isValid();
	}
	
	/**
	 * Aggiunge un nuovo avviso a quelli presenti
	 * @param message
	 */
	public void addWarning(String message){
		int length = warnings==null? 0 : warnings.length;
		String[] temp = new String[length+1];
		if (warnings!=null)
			System.arraycopy(warnings,0,temp,0,length);
		temp[length] = message;
		warnings = temp;
	}
	
	/**
	 * Aggiunge una lista di avvisi a quelli presenti
	 * @param warnings
	 */
	public void addWarnings(String[] newWarnings){
		if (newWarnings==null || newWarnings.length == 0)
			return;
		int length = warnings==null? 0 : warnings.length;
		String[] temp = new String[length+newWarnings.length];
		if (warnings!=null)
			System.arraycopy(warnings,0,temp,0,length);	
		for (int i=0;i<newWarnings.length; i++)
			temp[length + i] = newWarnings[i];
		warnings = temp;
	}
	
	/**
	 * Aggiunge un errore a quelli presenti
	 * @param error
	 */
	public void addError(String error){
		int length = errors==null? 0 : errors.length;
		String[] temp = new String[length+1];
		if (errors!=null)
			System.arraycopy(errors, 0, temp, 0, length);
		temp[length] = error;
		errors = temp;
		System.out.println("dopo add " + errors);
	}

	/**
	 * Aggiunge una lista di errori a quelli presenti
	 * @param errors
	 */
	public void addErrors(String[] newErrors){
		if (newErrors==null || newErrors.length == 0)
			return;
		int length = errors==null? 0 : errors.length;
		String[] temp = new String[length+newErrors.length];
		if (errors!=null)
			System.arraycopy(errors,0,temp,0,length);
		for (int i=0;i<newErrors.length; i++)
			temp[length + i] = newErrors[i];
		errors = temp;
	}
	
	/**
	 * Recupera gli avvisi presenti
	 * @return
	 */
	public String[] getWarnings() {
		return warnings;
	}

	/**
	 * Recupera gli errori presenti
	 * @return
	 */
	public String[] getErrors() {
		return errors;
	}
	
	public String getErrorsString(){
		StringBuffer errorsBuf = new StringBuffer();
		System.out.println("in get " + errors);
		if (errors!=null){
			System.out.println("in get lunghezza " + errors.length);
			for (int i=0; i<errors.length; i++){
				errorsBuf.append(errors[i]);
				if (i != errors.length - 1)
					errorsBuf.append(", ");
			}
		}
		System.out.println("buf " + errorsBuf );
		return errorsBuf.toString();
	}
	
	public String getWarningsString(){
		StringBuffer warningsBuf = new StringBuffer();
		if (warnings!=null){
			for (int i=0; i<warnings.length; i++){
				warningsBuf.append(warnings[i]);
				if (i != warnings.length - 1)
					warningsBuf.append(", ");
			}
		}
		return warningsBuf.toString();
	}
	
	public String toString(){
//		return "[Validated " + validatedObject +"] warnings: " + warnings +", errors: " + errors +", isValid: " + isValid();
		StringBuffer warningsBuf = new StringBuffer("[");
		if (warnings!=null)
			for (int i=0;i <warnings.length; i++)
				if (i != warnings.length - 1)
					warningsBuf.append(warnings[i] + ", ");
				else
					warningsBuf.append(warnings[i] + "]");
		else 
			warningsBuf.append("]");
		StringBuffer errorsBuf = new StringBuffer("[");
		if (errors!=null)
			for (int i=0;i <errors.length; i++){
				if (i != errors.length - 1)
					errorsBuf.append(errors[i] + ", ");
				else
					errorsBuf.append(errors[i] + "]");
			}
		else 
			errorsBuf.append("]");
		return WARNINGS_CODE + ": " + warningsBuf + ", " + ERRORS_CODE + ": " + errorsBuf +", " + ISVALID_CODE + ": " + isValid();
	}

	public static ValidationInfos fromString(String string){
		if (string==null)
			return null;
		ValidationInfos result = new ValidationInfos();
		
		String warnings = null;
		int wStartIdx= string.indexOf(WARNINGS_CODE);
		if (wStartIdx!=-1){
			warnings = string.substring(wStartIdx);
			wStartIdx = warnings.indexOf("[");
			warnings = warnings.substring(wStartIdx);
			int nOpen = 0;
			for (int i = 0; i< warnings.length(); i++){
				if (warnings.charAt(i)=='[') nOpen ++;
				else if (warnings.charAt(i) == ']')	nOpen -- ;
				if (nOpen==0){
					warnings = warnings.substring(1, i);
					break;
				}
			}
		}
				
		String errors = null;
		int eStartIdx= string.indexOf(ERRORS_CODE);
		if (eStartIdx!=-1){
			errors = string.substring(eStartIdx);
			eStartIdx = errors.indexOf("[");
			errors = errors.substring(eStartIdx);
			int nOpen = 0;
			for (int i = 0; i< errors.length(); i++){
				if (errors.charAt(i)=='[')	nOpen ++;
				else if (errors.charAt(i) == ']')	nOpen -- ;
				if (nOpen==0){
					errors = errors.substring(1, i);
					break;
				}
			}
		}
		
		StringTokenizer wTokenizer = new StringTokenizer(warnings, ",");
		while (wTokenizer.hasMoreElements()){
			String wToken = wTokenizer.nextToken();
			result.addWarning(wToken);
		}
		
		StringTokenizer eTokenizer = new StringTokenizer(errors, ",");
		while (eTokenizer.hasMoreElements()){
			String eToken = eTokenizer.nextToken();
			result.addError(eToken);
		}
		
		return result;
	}
	
	/**
	 * @param warnings the warnings to set
	 */
	public void setWarnings(String[] warnings) {
		this.warnings = warnings;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(String[] errors) {
		this.errors = errors;
	}

}