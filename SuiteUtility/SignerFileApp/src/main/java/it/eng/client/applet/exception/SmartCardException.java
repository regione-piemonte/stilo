package it.eng.client.applet.exception;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;



public class SmartCardException extends Exception{

	private String errormessage;
	
	 public SmartCardException(String message) {
			super(message);
			this.errormessage=message;
	 }
	
	public SmartCardException(Exception e) {
		//Setto il messaggio di errore
		//controllo se ho il messaggio di errore
		if(e.getCause() != null){
			String mess = e.getCause().getMessage();
			if(mess != null){
				if( mess.equalsIgnoreCase( "CKR_PIN_LOCKED")){
					errormessage = Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINLOCKED );//"Pin bloccato!";
				}else if(mess.equalsIgnoreCase("CKR_TOKEN_NOT_PRESENT ")){
					errormessage = Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_TOKENNOTPRESENT );//Smart Card non presente!";
				}else if(mess.equalsIgnoreCase("CKR_PIN_INVALID")){
					errormessage = Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PININVALID );//"Pin invalido!";
				}else if(mess.equalsIgnoreCase("CKR_PIN_INCORRECT")){
					errormessage = Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PININCORRECT );//"Pin non corretto!";
				}else if(mess.equalsIgnoreCase("CKR_PIN_EXPIRED")){
					errormessage = Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINEXPIRED );//"Pin scaduto!";
				}else{
					errormessage = mess;
				}
			}
		} else{
			errormessage = e.getMessage();
		}
	}
		
	@Override
	public String getMessage() {
		return errormessage;
	}
}