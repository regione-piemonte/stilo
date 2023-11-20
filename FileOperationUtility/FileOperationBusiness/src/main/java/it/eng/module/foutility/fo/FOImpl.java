/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;

import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse.FileOperationResults;
import it.eng.module.foutility.beans.generated.Response.GenericError;
import it.eng.module.foutility.util.FileOpConfigHelper;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.InputFileUtil;

import java.io.File;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(endpointInterface = "it.eng.module.foutility.fo.FileOperationWS",targetNamespace="it.eng.fileoperation.ws")
//@HandlerChain(file="handler.xml")
public class FOImpl implements FileOperationWS{
	
	private static final Logger log = LogManager.getLogger(FOImpl.class);

	@Override
	//@Operation(name="eseguiOperazioni")
	public Response execute(FileOperation input) {
		//public Response execute( InputFileOperationType inputFileOperationType,  FileOperation.Operations operations) {
		//FileOperation input = null;
		//Timestamp timestamp = new Timestamp(System.nanoTime());
		String requestKey = UUID.randomUUID().toString();
		log.info("File operation started - requestKey:" + requestKey);
		
		//bean della risposta
		Response ret= new Response();
		//FileOperationController foc= new FileOperationController();
		//foc.setSistemaOperativo(null);
		FileOperationController foc= FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);
		InputFileBean ifb = null;
		File currTempDir = null;
		try {
			currTempDir=FileOpConfigHelper.makeTempDir(requestKey);
			ifb = InputFileUtil.readInputFileBean( input, currTempDir, foc.getSistemaOperativo(), requestKey );
		} catch (Exception e1) {
			log.error(requestKey + " - errore nella lettura dell'input", e1);
			//genero errore generico
			GenericError er= new GenericError();
			er.getErrorMessage().add(e1.getMessage());
			ret.setGenericError(er);
			return ret;
		} 
		//imposto la dir temporanea
		ifb.setTemporaryDir(currTempDir);
		//verifico che è specificata almento una operazione
		if(input.getOperations()==null || input.getOperations().getOperation()==null || input.getOperations().getOperation().size()==0){
			log.error(requestKey + " - Nessuna operazione da eseguire");
			//genero errore generico
			GenericError er= new GenericError();
			er.getErrorMessage().add("Nessuna operazione da eseguire");
			ret.setGenericError(er);
			return ret;
		}
		
		//costruisce i ctrl in base all'input passato 
		//foc.buildCtrl(input.getOperations());
		OutputOperations result=null;
		
		
		result=foc.executeControll(ifb, input.getOperations(), requestKey);
		log.debug(requestKey + " - Costruisco la response");
		//build response
		FileoperationResponse fileoperationResponse= new FileoperationResponse();
		FileOperationResults results= new FileOperationResults();
		results.getFileOperationResult().addAll(result.getResponses());
		fileoperationResponse.setFileOperationResults(results);
		ret.setFileoperationResponse(fileoperationResponse);
		//imposta il file di ritorno se prodotto
		File fileresult=result.getFileResult();
		if( fileresult!=null ){
			log.debug(requestKey + " - Response file:"+fileresult.getAbsolutePath());
			DataHandler dh;
//			try {
//				dh = new DataHandler(fileresult.toURI().toURL());
//			} catch (MalformedURLException e) {
//				log.fatal("fatal attach file",e);
//				throw new RuntimeException(e);
//			}
			DataSource ds = new FileDataSource(fileresult);
			dh = new DataHandler(ds);
			fileoperationResponse.setFileResult(dh);
		}
		
//		log.info("Provo a cancellare " + fileresult);
//		boolean esitoCancellazione = fileresult.delete();
//		log.info("esitoCancellazione " + esitoCancellazione);
		
		log.info(requestKey + " - File operation terminated");
		//System.gc();
		return ret;
	}
	
	

}
