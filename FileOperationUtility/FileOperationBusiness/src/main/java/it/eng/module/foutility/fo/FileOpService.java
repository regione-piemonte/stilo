/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.module.foutility.beans.FOResponse;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse.FileOperationResults;
import it.eng.module.foutility.beans.generated.Response.GenericError;
import it.eng.module.foutility.exception.FOException;
import it.eng.module.foutility.util.FileoperationContextProvider;

import java.io.File;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service(name="FileOpService")
public class FileOpService {
	private static final Logger log = LogManager.getLogger(FileOpService.class);
	
	@Operation(name="eseguiOperazioni")
	public FOResponse execute(Operations operations, String fileName, File signedFile,File timestamp)throws FOException {
		String requestKey = UUID.randomUUID().toString();
		log.debug( requestKey + "File operation started");
		
		//bean della risposta
	 	FOResponse resp= new FOResponse();
		FileOperationController foc= FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);
		InputFileBean ifb = new InputFileBean();
		if(signedFile!=null && signedFile.exists()){
			ifb.setInputFile(signedFile);
		}else{
			//throw new FOException("file in input non trovato");
			GenericError er= new GenericError();
			er.getErrorMessage().add("file in input non trovato");
			resp.setGenericError(er);
			return resp;
		}
		if(fileName!=null){
			ifb.setFileName(fileName);
		}
		//verifico che è specificata almento una operazione
		if(operations==null || operations.getOperation()==null || operations.getOperation().size()==0){
			//ritorno una risposta vuota TODO launch error?
			log.warn("nessuna operazione specificata !");
			GenericError er= new GenericError();
			er.getErrorMessage().add("nessuna operazione specificata !");
			resp.setGenericError(er);
			return resp;
		}
		log.debug("input ricevuto inizio esecuzione operazioni tempDir:"+ifb.getTemporaryDir());
		//costruisce i ctrl in base all'input passato 
		//foc.buildCtrl(operations);
		OutputOperations result=null;
		result=foc.executeControll(ifb, operations, requestKey);
		
		//OutputOperations result= foc.executeControll(ifb);
		
		log.debug("building response");
		
		//build response
		FileOperationResults results= new FileOperationResults();
		results.getFileOperationResult().addAll(result.getResponses());
		resp.setForesults(results);
		if( result.getFileResult()!=null){
			resp.setResponsefile( result.getFileResult() ) ;
			boolean esito = result.getFileResult().delete();
			log.info("Esito cancellazione " + esito);
		}
		log.debug("File operation  Service terminated");
		return resp;
	}
}
