/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType.ErrorsMessage;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType.Warnings;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse.FileOperationResults;
import it.eng.module.foutility.beans.generated.VerificationStatusType;

import java.util.Hashtable;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseUtils {

	public static final  Logger log = LogManager.getLogger( ResponseUtils.class );
	
	public static boolean isResponseOK(FileOperationResults results){
		boolean result = true;
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		//CtrlBuilder builder=FileCtrlFactory.getCtrlBuilder();
		
		for(AbstractResponseOperationType risultato : listaRisultati){
			if(risultato.getVerificationStatus().equals(VerificationStatusType.ERROR ) || 
					risultato.getVerificationStatus().equals(VerificationStatusType.KO )){
				//IFileController ctrl = builder.getCTRLFromResponse(risultato);
				//log.debug("Errore nell'operazione " + ctrl.getOperationType() );
				log.debug("Errore nell'operazione " +risultato);
				result = false;
			}
		}
		
		return result;
	}
//	
	public static Hashtable<String, VerificationStatusType> getStatiVerifica(FileOperationResults results){
		//CtrlBuilder builder=FileCtrlFactory.getCtrlBuilder();
		Hashtable<String, VerificationStatusType> tableStati = new Hashtable<String, VerificationStatusType>();
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			//IFileController ctrl = builder.getCTRLFromResponse(risultato);
			//tableStati.put(ctrl.getOperationType(), risultato.getVerificationStatus());
			tableStati.put(risultato.getClass().getName(), risultato.getVerificationStatus());
		}
		return tableStati;
	}
//	
	public static VerificationStatusType getStatiVerifica(FileOperationResults results, String idOperazione){
		Hashtable<String, VerificationStatusType> tableStati = getStatiVerifica( results );
		VerificationStatusType statoVerificaOperazione = tableStati.get( idOperazione );
		if( statoVerificaOperazione==null ){
			statoVerificaOperazione = VerificationStatusType.KO;
		}
		return statoVerificaOperazione;
	}
//	
	public static Hashtable<String, Warnings> getWarnings(FileOperationResults results){
		//CtrlBuilder builder=FileCtrlFactory.getCtrlBuilder();
		Hashtable<String, Warnings> tableStati = new Hashtable<String, Warnings>();
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			//IFileController ctrl = builder.getCTRLFromResponse(risultato);
			//tableStati.put(ctrl.getOperationType(), risultato.getWarnings());
			tableStati.put(risultato.getClass().getName(), risultato.getWarnings());
		}
		return tableStati;
	}
//	
	public static Hashtable<String, ErrorsMessage> getOperationErrors(FileOperationResults results){
		//CtrlBuilder builder=FileCtrlFactory.getCtrlBuilder();
		Hashtable<String, ErrorsMessage> tableErrori = new Hashtable<String, ErrorsMessage>();
		List<AbstractResponseOperationType> listaRisultati = results.getFileOperationResult();
		for(AbstractResponseOperationType risultato : listaRisultati){
			if(risultato.getVerificationStatus().equals(VerificationStatusType.ERROR ) || 
					risultato.getVerificationStatus().equals(VerificationStatusType.KO )){
				//IFileController ctrl = builder.getCTRLFromResponse(risultato);
				//tableErrori.put(ctrl.getOperationType(), risultato.getErrorsMessage() );
				tableErrori.put(risultato.getClass().getName(), risultato.getErrorsMessage() );
			}
		}
		return tableErrori;
	}
//	
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
