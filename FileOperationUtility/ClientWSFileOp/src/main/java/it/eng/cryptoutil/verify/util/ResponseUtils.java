/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.AbstractResponseOperationType.ErrorsMessage;
import it.eng.fileOperation.clientws.AbstractResponseOperationType.Warnings;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.VerificationStatusType;

import it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults;

import java.util.Hashtable;
import java.util.List;

public class ResponseUtils {

	//public static final  Logger log = LogManager.getLogger( ResponseUtils.class );
	public static boolean isResponseOK(FileOperationResponse risposta){
		boolean result = true;
		if(risposta.getGenericError()!=null){
			result=false;
		}
		
		 it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults=new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
		 if(risposta.getFileoperationResponse()!=null && risposta.getFileoperationResponse().getFileOperationResults()!=null){
			 foresults.getFileOperationResult().addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());	 
		 }
		 return result && isResponseOK(foresults);
	}
	
	 
	public static boolean isResponseOK(FileOperationResults results){
		boolean result = true;
		 
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();

		for(AbstractResponseOperationType risultato : listaRisultati){
			//if(risultato.getVerificationStatus()==null || risultato.getVerificationStatus().equals(VerificationStatusType.ERROR ) || 
			if(risultato.getVerificationStatus()==null ){
				
			} else 
			if(risultato.getVerificationStatus().equals(VerificationStatusType.ERROR ) || 
					risultato.getVerificationStatus().equals(VerificationStatusType.KO )){
				result = false;
			}
		}
		
		return result;
	}
	
	public static Hashtable<String, VerificationStatusType> getStatiVerifica(FileOperationResults results){
		 
		Hashtable<String, VerificationStatusType> tableStati = new Hashtable<String, VerificationStatusType>();
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			tableStati.put(risultato.getClass().getName(), risultato.getVerificationStatus());
		}
		return tableStati;
	}
	
	public static VerificationStatusType getStatiVerifica(FileOperationResults results, Class responseClazz){
		Hashtable<String, VerificationStatusType> tableStati = getStatiVerifica( results );
		VerificationStatusType statoVerificaOperazione = tableStati.get( responseClazz.getName() );
		if( statoVerificaOperazione==null ){
			statoVerificaOperazione = VerificationStatusType.KO;
		}
		return statoVerificaOperazione;
	}
	
	public static VerificationStatusType getStatiVerifica(FileOperationResults results, String idOperazione){
		Hashtable<String, VerificationStatusType> tableStati = getStatiVerifica( results );
		VerificationStatusType statoVerificaOperazione = tableStati.get( idOperazione );
		if( statoVerificaOperazione==null ){
			statoVerificaOperazione = VerificationStatusType.KO;
		}
		return statoVerificaOperazione;
	}
	
	public static Hashtable<String, Warnings> getWarnings(FileOperationResults results){
		 
		Hashtable<String, Warnings> tableStati = new Hashtable<String, Warnings>();
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			tableStati.put(risultato.getClass().getName(), risultato.getWarnings());
		}
		return tableStati;
	}
	
	public static Hashtable<String, ErrorsMessage> getOperationErrors(FileOperationResults results){
		 
		Hashtable<String, ErrorsMessage> tableErrori = new Hashtable<String, ErrorsMessage>();
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			if(risultato.getVerificationStatus().equals(VerificationStatusType.ERROR ) || 
					risultato.getVerificationStatus().equals(VerificationStatusType.KO )){
				tableErrori.put(risultato.getClass().getName(), risultato.getErrorsMessage() );
			}
		}
		return tableErrori;
	}
	
	public static ErrorsMessage getOperationErrors(FileOperationResults results, String idOperazione){
		Hashtable<String, ErrorsMessage> tableErrori = getOperationErrors(results);
		return tableErrori.get( idOperazione );
	}
	
	public static <T extends AbstractResponseOperationType>  T  getResponse(FileOperationResults results,Class<T > clazz){
		T ret=null;
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			if(risultato.getClass().equals(clazz)){
				ret=(T)risultato;
				break;
			}
		}
		return ret;
	}
	
	 
}
