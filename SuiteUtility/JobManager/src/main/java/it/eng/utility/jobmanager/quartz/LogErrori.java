package it.eng.utility.jobmanager.quartz;

import java.util.ArrayList;
import java.util.List;

public class LogErrori {

	private static ArrayList<ErrorCodeMessage> logMap = new ArrayList<ErrorCodeMessage>();
	
	public static synchronized void addErrorLog(ErrorCodeEnum errorCodeEnum, String errorMessage)
	{			
		int errorCode = errorCodeEnum != null ? errorCodeEnum.getErrorCode() : ErrorCodeEnum.ERRORE_SCONOSCIUTO.getErrorCode();
		
		//Inserisco il codice e il messaggio di errore nella mappa che � stata creata
		logMap.add( new ErrorCodeMessage(errorCode, errorMessage) );
	}
	
	public static List<ErrorCodeMessage> getErrorLog()
	{
		return logMap;
	}
	
	/**
	 * Ritorna il numero di errori che sono stati salvati 
	 * @return
	 */
	public static int size()
	{
		return logMap.size();
	}
	
	/**
	 * Metodo che ritorna il massimo valore tra gli errori presenti in lista
	 * @return
	 */
	public static int getMaximumErrorCode()
	{
		int maximumErrorCode = Integer.MIN_VALUE;
		
		//Per ogni entry inserita nella lista degli errori
		for(ErrorCodeMessage item : logMap)
		{
			/*
			 * Se il codice di errore di questa entry � maggiore di quello massimo attuale
			 * allora diventa il nuovo massimo
			 */
			if(item.getCode() > maximumErrorCode)
			{
				maximumErrorCode = item.getCode();	
			}
		}
		
		return maximumErrorCode;
	}
	
	/**
	 * Metodo che ritorna, sotto forma di stringa, la lista degli errori che sono stati generati
	 * dai job avviati.
	 * @return
	 */
	public static String getTextError()
	{
		StringBuilder errorText = new StringBuilder("\nErrori generati: \n");
		//Per ogni entry inserita nella lista degli errori
		for(ErrorCodeMessage item : logMap)
		{
			/*
			 * Si aggiunge alla stringa di output la sequenza dei vari errori generati
			 */
			errorText.append(item.getCode()).append(" - ").append(item.getMessage()).append("\n");
		}
		
		return errorText.toString();
	}
	
	
	
	
	/**
	 * Enumerazione degli errori che possono essere generati e del rispettivo codice di errore
	 * @author MMANIERO
	 *
	 */
	public enum ErrorCodeEnum {
		ERRORE_SCONOSCIUTO(-1),
		NESSUN_ERRORE(0),
		WARNING(4),
		ERRORE_APPLICATIVO(8),
		ERRORE_DI_SISTEMA(12);
		
		private int errorCode;
		
		private ErrorCodeEnum(int code){
			errorCode = code;
		}
		
		public int getErrorCode(){
			return errorCode;
		}
		
		
		public static int valueOfError(String name) {
			for (ErrorCodeEnum errorType : ErrorCodeEnum.values()) {
				if (errorType.name().equals(name)) {
					return errorType.errorCode;
				}
			}
			return -1;
		}
	}
	
	
	

	public static class ErrorCodeMessage
	{
		private int code;
		private String message;
		
		public ErrorCodeMessage(int code, String message)
		{
			this.code = code;
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
	}
}


