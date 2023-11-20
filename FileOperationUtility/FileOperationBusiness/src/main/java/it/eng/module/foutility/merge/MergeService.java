/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputConversionType;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.merge.request.MergeDocumentRequest;
import it.eng.module.foutility.beans.merge.response.MergeDocumentResponse;
import it.eng.module.foutility.fo.FileOperationController;
import it.eng.module.foutility.fo.InputFileBean;
import it.eng.module.foutility.merge.common.MergeDocument;
import it.eng.module.foutility.util.FileOpConfigHelper;
import it.eng.module.foutility.util.FileoperationContextProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service(name="MergeService")
public class MergeService {
	
	static Logger aLogger = LogManager.getLogger(WSMergeDocument.class.getName());

	@Operation(name="merge")
	public  MergeDocumentResponse getBean( MergeDocumentRequest request ){
		MergeDocumentResponse mergeDocumentResponse = new MergeDocumentResponse();

		InputStream is = null;
		try {
			String requestKey = UUID.randomUUID().toString();
			aLogger.info("Inizio WSMergeDocument serviceImplementation");

			// CONTROLLI PRELIMINARI
			// prendo i file fisici allegati
			// AttachmentPart[] attachments = getMessageAttachments();
			List<File> allegati = request.getAllegati();
			
			InputFileBean[] files = new InputFileBean[allegati.size()];
			File[] convertedFiles = new File[allegati.size()];

			// preparo la directory temporanea su cui mettere il file
			// Se la directory temporanea non c'e', la creo.
			FileOperationController foc = FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);

			File tmpWorkDirectoryFile = FileOpConfigHelper.makeTempDir(requestKey);
			

			// Scorro gli attachents e li salvo in un Array
			for (int i = 0; i < allegati.size(); i++) {
				
				InputFileBean tmp = new InputFileBean();
				tmp.setInputFile(allegati.get(i));
				files[i] = tmp;
				OutputOperations output = new OutputOperations();
				// pdfConvCtrl.execute(tmp, customInput, output);
				InputConversionType input = new InputConversionType();
				// input.setPdfA(true);
				FileOperation fo = new FileOperation();
				Operations ops = new Operations();
				fo.setOperations(ops);
				fo.getOperations().getOperation().add(input);
				foc = FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);
				foc.buildCtrl(ops, requestKey);
				// pdfConvCtrl.execute(tmp, customInput, output);
				OutputOperations result = null;
				result = foc.executeControll(tmp, ops, requestKey);
				convertedFiles[i] = result.getFileResult();
			}

			// Creo un file temporaneo per la merge
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			// Faccio la merge dei file

			MergeDocument merge = MergeDocument.newInstance();
			merge.mergeDocument(convertedFiles, output);

			// Converto l'outputstream in inputStream
			ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

			output.close();
			inputStream.close();

			File returnFile = new File(tmpWorkDirectoryFile + File.separator +"MergeFiles.pdf");
			FileOutputStream fos = new FileOutputStream(returnFile);
			int data;
			while ((data = inputStream.read()) != -1) {
				char ch = (char) data;
				fos.write(ch);
			}
			fos.flush();
			fos.close();
			// Prendo l'input stream lo inserisco nell'attachment di risposta
			// attachStream(inputStream);
			mergeDocumentResponse.getAllegati().add(returnFile);
			mergeDocumentResponse.setServiceReturn("1");
		} catch (Exception excptn) {
			aLogger.error("WSMergeDocument: " + excptn.getMessage(), excptn);
			mergeDocumentResponse.setServiceReturn("0");
		} finally {
			aLogger.info("Fine WSMergeDocument serviceImplementation");
		}
		return mergeDocumentResponse;
	}

	public final static String randomHexString() {
		StringBuffer nmfl = new StringBuffer();
		byte casuali[] = new byte[10];
		new Random().nextBytes(casuali);
		for (int jj = 0; jj < casuali.length; jj++) {
			nmfl.append(upper(casuali[jj]));
			nmfl.append(lower(casuali[jj]));
		}
		return nmfl.toString();
	}

	private static final String[] hexdigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private final static String upper(byte ottetto) {
		return hexdigits[((ottetto + 128) & 0xF0) >> 4];
	}

	private final static String lower(byte ottetto) {
		return hexdigits[((ottetto + 128) & 0x0F)];
	}

	public static String getStrDate() {
		Calendar c = Calendar.getInstance();

		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DATE);
		int h = c.get(Calendar.HOUR_OF_DAY);
		int mi = c.get(Calendar.MINUTE);
		int ms = c.get(Calendar.MILLISECOND);
		String mm = Integer.toString(m);
		String dd = Integer.toString(d);
		String hh = Integer.toString(h);
		String mmi = Integer.toString(mi);
		String mms = Integer.toString(ms);

		String repDt = c.get(Calendar.YEAR) + (m < 10 ? "0" + mm : mm) + (d < 10 ? "0" + dd : dd);
		String repOra = (h < 10 ? "0" + hh : hh) + (mi < 10 ? "0" + mmi : mmi) + (ms < 10 ? "0" + mms : mms);

		return repDt + repOra;
	}
}
