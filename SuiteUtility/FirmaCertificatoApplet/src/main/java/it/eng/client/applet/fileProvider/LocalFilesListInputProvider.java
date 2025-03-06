package it.eng.client.applet.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.server.util.Encryptor;

public class LocalFilesListInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		List<FileBean> fileBeanList = new ArrayList<FileBean>();

		String count = PreferenceManager.getString(PreferenceKeys.PROPERTY_NUMFILES);
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_NUMFILES + ": " + count);
		for (int i = 0; i < Integer.parseInt(count); i++) {
			FileBean bean = new FileBean();

			String localFile = null;
			try {
				localFile = PreferenceManager.getString(PreferenceKeys.PROPERTY_LOCALFILE + i);
				LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_LOCALFILE + i + " :" + localFile);
			} catch (Exception e) {
			}

			String filename = null;
			try {
				filename = PreferenceManager.getString(PreferenceKeys.PROPERTY_FILENAME + i);
				LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILENAME + i + " :" + filename);
			} catch (Exception e) {
			}

			if (localFile == null || filename == null)
				throw new Exception(Messages.getMessage(MessageKeys.MSG_ERROR_MISSINGPARAMETERS));

			File file = new File(localFile);
			bean.setFile(file);
			bean.setFileName(filename);

			fileBeanList.add(bean);
		}
		response = new FileInputResponse();
		response.setFileBeanList(fileBeanList);

		return response;
	}

	@Override
	public String getPin() throws Exception {
		String pin = null;
		try {
			pin = PreferenceManager.getString(PreferenceKeys.PROPERTY_PIN);

			//Decodifico il pin
			pin = Encryptor.decodificaConZero(pin);

//			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_PIN + " :" + pin);
		} catch (Exception e) {
		}

		return pin;
	}

	@Override
	public String getAlias() throws Exception {
		String alias = null;
		try {
			alias = PreferenceManager.getString(PreferenceKeys.PROPERTY_ALIAS);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_ALIAS + " :" + alias);
		} catch (Exception e) {
		}

		return alias;
	}

	@Override
	public String getMetodoFirma() throws Exception {
		String metodoFirma = null;
		try {
			metodoFirma = PreferenceManager.getString(PreferenceKeys.PROPERTY_METODO_FIRMA);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_METODO_FIRMA + " :" + metodoFirma);
		} catch (Exception e) {
		}

		return metodoFirma;
	}

}
