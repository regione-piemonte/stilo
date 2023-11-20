/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class LoginException extends Exception{

	private static final long serialVersionUID = 2281995706137640868L;


	public enum ErrorType {
		APPLICAZIONE_NON_TROVATA("Applicazione non trova"),
		PASSWORD_NON_VALIDA("Password non valida"),
		UTENTE_NON_TROVATO("Utente non trovato"),
		UTENTE_NON_ATTIVO("Utente non attivo"),
		ERRORE_GENERICO("Errore generico"),
		APPLICAZIONE_NON_ATTIVA("Applicazione non attiva"),
		CAMPI_NON_VALIDI("Alcuni dei campi obbligatori risultano vuoti");
		
		private String message;
		private ErrorType(String pMessage){
			message = pMessage;
		}
		
		public String getMessage(){
			return message;
		}
		
	}
	
	private ErrorType error;
	
	
	public LoginException(ErrorType pError, Exception pException){
		super(pException);
		setError(pError);
	}
	
	public LoginException(ErrorType pErrorType){
		super(pErrorType.getMessage());
		setError(pErrorType);
	}


	public void setError(ErrorType error) {
		this.error = error;
	}


	public ErrorType getError() {
		return error;
	}
}
