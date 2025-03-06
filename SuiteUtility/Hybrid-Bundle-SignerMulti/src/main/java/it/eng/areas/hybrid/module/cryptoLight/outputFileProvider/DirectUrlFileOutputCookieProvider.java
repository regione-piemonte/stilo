package it.eng.areas.hybrid.module.cryptoLight.outputFileProvider;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;



public class DirectUrlFileOutputCookieProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(DirectUrlFileOutputCookieProvider.class);
	
	static final long serialVersionUID = 1004L;

	private String outputUrl;
	private String cookie;

	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;

	/**
	 * Classe di utilità per la gestione dell' invio multipart con la corretta dimensione senza appoggiarsi su fs locale  
	 * @author Giobo
	 *
	 */
	public class ByteArrayBody extends AbstractContentBody {
		/**
		 * The contents of the file contained in this part.
		 */
		private byte[] data;

		/**
		 * The name of the file contained in this part.
		 */
		private String filename;

		/**
		 * Creates a new ByteArrayBody.
		 * 
		 * @param data
		 *          The contents of the file contained in this part.
		 * @param mimeType
		 *          The mime type of the file contained in this part.
		 * @param filename
		 *          The name of the file contained in this part.
		 */
		public ByteArrayBody(InputStream is, final String filename) throws Exception {
			super(ContentType.APPLICATION_OCTET_STREAM);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			IOUtils.copy(is, os);

			this.data = os.toByteArray();
			this.filename = filename;
		}


		@Override
		public String getFilename() {
			return filename;
		}

		@Override
		public void writeTo(OutputStream out) throws IOException {
			out.write(data);
		}

		@Override
		public String getCharset() {
			return null;
		}

		@Override
		public String getTransferEncoding() {
			return MIME.ENC_BINARY;
		}

		@Override
		public long getContentLength() {
			return data.length;
		}

	}

	@Override
	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, X509Certificate certificatoDiFirma, String... params) throws Exception {
		System.out.println("DirectUrlFileOutputCookieProvider version:"+serialVersionUID);

		if(getOutputUrl()!=null){
			CloseableHttpClient httpClient = ProxyDefaultHttpClient.getClientToUse();
			//CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = null;
			try {
				HttpPost request = new HttpPost(getOutputUrl());
				//Invio i cookie per la sessione
				request.addHeader("Cookie",getCookie());
				RequestConfig config = RequestConfig.custom().build();
				HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addPart("file", new ByteArrayBody(in,fileInputName))				    
						.addTextBody("fileid", id)
						.addTextBody("tipobusta", tipoBusta)
						.addTextBody("filename", fileInputName)
						.build();
				request.setEntity(reqEntity);
				request.setConfig(config);

				String result = null;
				BufferedReader br = null;
				response = httpClient.execute(request);
				System.out.println(response.getStatusLine());
				StringBuilder sb = new StringBuilder();
				String line;
				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
				if (response.getStatusLine().getStatusCode()!=200) throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
			} catch (Exception e) {
				logger.info("Errore", e );
			} finally {
				if (response!=null){
					response.close();
				}
			}
		}
		return FileOutputProviderOperationResultEnum.OK;
	}

	@Override
	public void saveOutputParameter() throws Exception {
		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTURL );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );

//			if( applet!=null ){			
//				String cookie = (String)JSObject.getWindow(applet).eval("document.cookie");
//				logger.info("cookie " + cookie);
//				setCookie( cookie );
//			}

			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if( autoClosePostSignString!=null )
				autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

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
