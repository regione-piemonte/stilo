/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputConversionType;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.merge.request.MergeDocumentRequest;
import it.eng.module.foutility.beans.merge.response.MergeDocumentResponse;
import it.eng.module.foutility.fo.FileOperationController;
import it.eng.module.foutility.fo.InputFileBean;
import it.eng.module.foutility.merge.common.InputStreamDataSource;
import it.eng.module.foutility.merge.common.MergeDocument;
import it.eng.module.foutility.util.FileOpConfigHelper;
import it.eng.module.foutility.util.FileoperationContextProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.remoting.jaxws.JaxWsSoapFaultException;

@WebService(targetNamespace = "http://mergedocument.webservices.jaxws.eng.it/", endpointInterface = "it.eng.module.foutility.merge.WSIMergeDocument", name = "WSMergeDocument")
@MTOM(enabled = true, threshold = 0)
// @HandlerChain(file = "handler.xml")
public class WSMergeDocument implements WSIMergeDocument {

	static Logger aLogger = LogManager.getLogger(WSMergeDocument.class.getName());

	public MergeDocumentResponse merge(MergeDocumentRequest mergeDocumentRequest) {
		MergeDocumentResponse mergeDocumentResponse = new MergeDocumentResponse();

		InputStream is = null;
		try {
			String requestKey = UUID.randomUUID().toString();
			aLogger.info("Inizio WSMergeDocument serviceImplementation");

			// CONTROLLI PRELIMINARI
			// prendo i file fisici allegati
			// AttachmentPart[] attachments = getMessageAttachments();
			DataHandler[] handlers = getMessageDataHandlers();
			InputFileBean[] files = new InputFileBean[handlers.length];
			File[] convertedFiles = new File[handlers.length];

			// preparo la directory temporanea su cui mettere il file
			// Se la directory temporanea non c'e', la creo.
			FileOperationController foc = FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);

			File tmpWorkDirectoryFile = FileOpConfigHelper.makeTempDir(requestKey);
			if (!tmpWorkDirectoryFile.exists()) {
				tmpWorkDirectoryFile.mkdirs();
			}

			// Scorro gli attachents e li salvo in un Array
			for (int i = 0; i < handlers.length; i++) {
				FileOutputStream fos = null;
				String fileOri = null;
				try {
					DataSource dSource = handlers[i].getDataSource();
					is = dSource.getInputStream();
					fileOri = tmpWorkDirectoryFile + File.separator + randomHexString() + "_" + getStrDate();
					aLogger.debug("Serializzato su:" + fileOri);
					File to = new File(fileOri);
					fos = new FileOutputStream(to);
					byte[] buffer = new byte[2048];
					int byte_count = 0;
					while ((byte_count = is.read(buffer)) > 0) {
						fos.write(buffer, 0, byte_count);
					}

				} catch (Exception e) {
					aLogger.debug("Exception: " + e.getMessage(), e);
				} finally {
					if (fos != null) {
						fos.close();
					}
				}
				InputFileBean tmp = new InputFileBean();
				tmp.setInputFile(new File(fileOri));
				files[i] = tmp;
				OutputOperations output = new OutputOperations();
				InputConversionType input = new InputConversionType();
				FileOperation fo = new FileOperation();
				Operations ops = new Operations();
				fo.setOperations(ops);
				fo.getOperations().getOperation().add(input);
				foc = FileoperationContextProvider.getApplicationContext().getBean("FileOperationController", FileOperationController.class);
				foc.buildCtrl(ops, requestKey);
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
			// Prendo l'input stream lo inserisco nell'attachment di risposta
			attachStream(inputStream);
			
			 // CONVERSIONE DEL FILE
			 if (!attachStream(inputStream)) {
				 //throw new ServiceException(errorcontext, errorcode, cause);
			 }
			mergeDocumentResponse.setServiceReturn("ok");
		} catch (Exception excptn) {
			aLogger.error("WSMergeDocument: " + excptn.getMessage(), excptn);
			mergeDocumentResponse.setServiceReturn("ko");
		} finally {
			aLogger.info("Fine WSMergeDocument serviceImplementation");
		}
		return mergeDocumentResponse;
	}

	public boolean attachStream(InputStream is) throws Exception {
		DataSource ds = null;
		try {
			MessageContext msgContext = context.getMessageContext();
			ds = new InputStreamDataSource(is);

			DataHandler handler = new DataHandler(ds);

			Map<String, DataHandler> mapDataHandlers = (Map<String, DataHandler>) msgContext.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
			mapDataHandlers.put(handler.getName(), handler);

			msgContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);

			return true;
		} catch (Exception e) {
			aLogger.error("Errore in attachStream: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean attachFile(File mioFile) throws Exception {
		try {
			// reperisco il contesto
			MessageContext msgContext = context.getMessageContext();

			// Get the file from the filesystem
			FileDataSource fileDS = new FileDataSource(mioFile);
			DataHandler handler = new DataHandler(fileDS);

			// Create the attachment as a DIME attachment

			Map<String, DataHandler> mapDataHandlers = (Map<String, DataHandler>) msgContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
			mapDataHandlers.put(handler.getName(), handler);

			// aggiunge l'attachment all response
			msgContext.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, mapDataHandlers);
			return true;
		} catch (Exception e) {
			aLogger.error("Errore in attachFile(File file = " + mioFile + ")" + e.getMessage(), e);
			return false;
		}
	}

	@Resource
	private WebServiceContext context;

	public DataHandler[] getMessageDataHandlers() throws JaxWsSoapFaultException {
		// reperisco il contesto
		MessageContext msgContext = context.getMessageContext();
		Map<String, DataHandler> mapDataHandler = (Map<String, DataHandler>) msgContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);

		// restituisco gli attachment
		Collection<DataHandler> dataHandlers = mapDataHandler.values();
		DataHandler[] handlers = new DataHandler[dataHandlers.size()];
		handlers = dataHandlers.toArray(handlers);

		return handlers;
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
