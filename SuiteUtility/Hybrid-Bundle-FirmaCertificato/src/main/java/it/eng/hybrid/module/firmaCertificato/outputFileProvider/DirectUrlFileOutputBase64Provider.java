package it.eng.hybrid.module.firmaCertificato.outputFileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceKeys;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceManager;

/**
 * Classe per il post di un file in base64 con cookie ed riferimenti del dato
 * @author Giobo
 *
 */
public class DirectUrlFileOutputBase64Provider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(DirectUrlFileOutputBase64Provider.class);

	static final long serialVersionUID = 1001L;

	private String outputUrl;
	private String cookie;
	private boolean autoClosePostSign = false;
	private String callBackAskForClose;

	@Override
	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta,X509Certificate certificatoDiFirma, String... params) throws Exception {
		System.out.println("DirectUrlFileOutputBase64Provider version:"+serialVersionUID);

		if (getOutputUrl() != null) {
			try {
				String fileBase64 = new String(Base64.encode(IOUtils.toByteArray(in)));

				URL url = new URL(getOutputUrl());
				URLConnection conn = url.openConnection();

				// Invio i cookie per la sessione
				if (cookie != null) {
					conn.setRequestProperty("Cookie", cookie);
				}

				String data = "data=" + URLEncoder.encode(fileBase64, "UTF-8") + "&filename=" + URLEncoder.encode(fileInputName, "UTF-8") + "&tipobusta=" + URLEncoder.encode(tipoBusta, "UTF-8")+ "&fileid=" + URLEncoder.encode(id, "UTF-8");

				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(data);
				wr.flush();

				// Get the response
				ByteArrayOutputStream document = new ByteArrayOutputStream();

				InputStream inputStream = conn.getInputStream();

				int len = 0;
				byte[] buffer = new byte[4096];

				while ((len = inputStream.read(buffer)) > 0) {
					document.write(buffer, 0, len);
				}

				inputStream.close();
				document.close();

				String result = new String(document.toByteArray());
				if (!"OK".equals(result)) {
					throw new IOException("Errore nella comunicazione con il server:" + result);
				}

			} catch (Exception e) {
				logger.info("Errore", e);
				throw new IOException("Errore nella comunicazione con il server:" + e.getMessage());
			}
		}
		return FileOutputProviderOperationResultEnum.OK;
	}

	@Override
	public void saveOutputParameter() throws Exception {
		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUTURL);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl(urlOutput);

			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if(autoClosePostSignString!=null) {
				autoClosePostSign  = Boolean.valueOf(autoClosePostSignString);
			}

			callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
		} catch (Exception e1) {
			logger.error(e1);
		}
	}

	public String getOutputUrl() {
		return outputUrl;
	}

	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}

	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}

	@Override
	public String getCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCallbackResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
