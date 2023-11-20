/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputDigestType;
import it.eng.module.foutility.beans.generated.InputFormatRecognitionType;
import it.eng.module.foutility.beans.generated.InputSigVerifyType;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse.FileOperationResults;
import it.eng.module.foutility.beans.generated.Response.GenericError;
import it.eng.module.foutility.util.FileOpConfigHelper;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.module.foutility.util.InputFileUtil;

@WebService(endpointInterface = "it.eng.module.foutility.fo.FileOperationNoOutputWS", targetNamespace = "it.eng.fileoperation.ws")
// @HandlerChain(file="handler.xml")
public class FONoOutputImpl implements FileOperationNoOutputWS {

	private static final Logger log = LogManager.getLogger(FONoOutputImpl.class);

	@Override
	// @Operation(name="eseguiOperazioni")
	public Response execute(FileOperation input) {
		// public Response execute( InputFileOperationType inputFileOperationType, FileOperation.Operations operations) {
		// FileOperation input = null;
		String requestKey = UUID.randomUUID().toString();
		log.info("File operation noOutput started " + requestKey);
		
		// bean della risposta
		Response ret = new Response();
		FileOperationController foc = FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);
		InputFileBean ifb = null;
		File currTempDir = FileOpConfigHelper.makeTempDir(requestKey);
		try {
			ifb = InputFileUtil.readInputFileBean(input, currTempDir, foc.getSistemaOperativo(), requestKey);
		} catch (Exception e1) {
			log.error("errore nella lettura dell'input", e1);
			// genero errore generico
			GenericError er = new GenericError();
			er.getErrorMessage().add(e1.getMessage());
			ret.setGenericError(er);
			return ret;
		}
		// imposto la dir temporanea
		ifb.setTemporaryDir(currTempDir);
		log.debug("current Temp Dir:" + currTempDir);
		// verifico che è specificata almento una operazione
		if (input.getOperations() == null || input.getOperations().getOperation() == null || input.getOperations().getOperation().size() == 0) {
			// genero errore generico
			GenericError er = new GenericError();
			er.getErrorMessage().add("Nessuna operazione da eseguire");
			ret.setGenericError(er);
			return ret;
		}

		List<AbstractInputOperationType> operations = input.getOperations().getOperation();
		boolean isFileOutRequired = true;
		for (AbstractInputOperationType operation : operations) {
			if (operation instanceof InputFormatRecognitionType)
				isFileOutRequired = false;
			else if (operation instanceof InputDigestType)
				isFileOutRequired = false;
			else if (operation instanceof InputSigVerifyType)
				isFileOutRequired = false;
			else if (operation instanceof InputSigVerifyType)
				isFileOutRequired = false;
			else
				isFileOutRequired = true;
		}

		log.debug("input ricevuto inizio esecuzione operazioni tempDir:" + ifb.getTemporaryDir());
		// costruisce i ctrl in base all'input passato
		//foc.buildCtrl(input.getOperations());
		OutputOperations result = null;
		result = foc.executeControll(ifb, input.getOperations(), requestKey);
		log.debug("building response");
		// build response
		FileoperationResponse fileoperationResponse = new FileoperationResponse();
		FileOperationResults results = new FileOperationResults();
		results.getFileOperationResult().addAll(result.getResponses());
		fileoperationResponse.setFileOperationResults(results);
		ret.setFileoperationResponse(fileoperationResponse);
		// imposta il file di ritorno se prodotto
		File fileresult = result.getFileResult();
		log.debug("fileResult " + fileresult);
		if (fileresult != null && isFileOutRequired) {
			log.debug("attach response file:" + fileresult.getAbsolutePath());
			DataHandler dh;
			// try {
			// dh = new DataHandler(fileresult.toURI().toURL());
			// } catch (MalformedURLException e) {
			// log.fatal("fatal attach file",e);
			// throw new RuntimeException(e);
			// }
			DataSource ds = new FileDataSource(fileresult);
			dh = new DataHandler(ds);
			fileoperationResponse.setFileResult(dh);
		} else {
			// cancello la dir temporanea
			deleteFiles(currTempDir);
		}
		log.debug("File operation no output terminated");
		// System.gc();
		return ret;
	}

	public static void deleteFiles(File fileToDelete) {
		// log.info("Provo a cancellare " + fileToDelete);
		if (fileToDelete.isFile()) {
			FileUtils.deleteQuietly(fileToDelete);
		} else if (fileToDelete.isDirectory()) {
			File[] filesInDir = fileToDelete.listFiles();
			for (File fileInDir : filesInDir) {
				deleteFiles(fileInDir);
			}
			FileUtils.deleteQuietly(fileToDelete);
		}
	}

}
