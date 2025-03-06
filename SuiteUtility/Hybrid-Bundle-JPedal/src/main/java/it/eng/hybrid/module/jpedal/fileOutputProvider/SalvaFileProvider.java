package it.eng.hybrid.module.jpedal.fileOutputProvider;

import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;






import java.applet.AppletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JApplet;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

public class SalvaFileProvider {

	public final static Logger logger = Logger.getLogger(SalvaFileProvider.class);
	
	private String saveAsOutputurl;
	private String callBack;
	private AppletContext appletContext;

	public void saveAs(final InputStream in, final String fileInputName, 
			PreferenceManager preferenceManager) throws Exception {
		logger.info("Metodo saveAs");
		//Recupero dove fare l'output
		saveAsOutputurl = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SAVE_AS_OUTPUTURL );
		CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
		byte[] contentFile;
		CloseableHttpResponse response = null;
		String lStringOriginalFileName = fileInputName;
		File lFileOut;
		try {
			contentFile = IOUtils.toByteArray(in);
			HttpPost request = new HttpPost(saveAsOutputurl);
			RequestConfig config = RequestConfig.custom().build();
			lFileOut = File.createTempFile("fileTemp", ".tmp");
			IOUtils.write(contentFile, new FileOutputStream(lFileOut));
			FileBody lFileBody = new FileBody(lFileOut);
			StringBody lStringBodyIdSelected = new StringBody(lStringOriginalFileName!=null?lStringOriginalFileName:"", ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
			.addPart("userfile", lFileBody)
			.addPart("idSelected", lStringBodyIdSelected)
			.build();
			request.setEntity(reqEntity);
			request.setConfig(config);

			String result = null;
			BufferedReader br = null;
			response = lDefaultHttpClient.execute(request);
			System.out.println(response.getStatusLine());
			StringBuilder sb = new StringBuilder();
			String line;
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			if (response.getStatusLine().getStatusCode()!=200) throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
			if (callBack!=null && !callBack.equals("")){
				String lStrToInvoke = "javascript:" + result;
				getAppletContext().showDocument(new URL(lStrToInvoke));
			}
		} catch (Exception e) {
			logger.info("Errore", e );
		} finally {
			if (response!=null){
				response.close();
			}
		}
	}
	
	public void saveOutputParameter(PreferenceManager preferenceManager) throws Exception {
		saveAsOutputurl = preferenceManager.getConfiguration().getString(ConfigConstants.PROPERTY_SAVE_AS_OUTPUTURL);
		//setAppletContext(applet.getAppletContext());
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public AppletContext getAppletContext() {
		return appletContext;
	}

	public void setAppletContext(AppletContext appletContext) {
		this.appletContext = appletContext;
	}

}
