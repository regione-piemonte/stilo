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

public class DirectUrlAurigaFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(DirectUrlAurigaFileOutputProvider.class);
	
	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String cookie;
	private AppletContext appletContext;

	@Override
	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception {
		logger.info("Metodo saveOutputFile");
		if(getOutputUrl()!=null){
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			byte[] contentFile;
			CloseableHttpResponse response = null;
			File lFileOut;
			try {
				contentFile = IOUtils.toByteArray(in);
				HttpPost request = new HttpPost(getOutputUrl());
				RequestConfig config = RequestConfig.custom().build();
				lFileOut = File.createTempFile("fileTemp", ".tmp");
				IOUtils.write(contentFile, new FileOutputStream(lFileOut));
				FileBody lFileBody = new FileBody(lFileOut);
				StringBody lStringBody = new StringBody(getIdSelected()!=null?getIdSelected():"", ContentType.TEXT_PLAIN);
				HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart("userfile", lFileBody)
				.addPart("idSelected", lStringBody)
				.build();
				logger.info("idSelected = " + idSelected);
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
					String lStrToInvoke = "javascript:" + callBack + "('" + result +"');";
					if( getAppletContext()!=null )
						getAppletContext().showDocument(new URL( lStrToInvoke ) );
				}
			} catch (Exception e) {
				logger.info("Errore", e );
			} finally {
				if (response!=null){
					response.close();
				}
			}
		}

	}

	@Override
	public void saveOutputParameter(PreferenceManager preferenceManager) throws Exception {
		String urlOutput = null;
		try {
			urlOutput = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OUTPUTURL );
			logger.info("Parametro " + ConfigConstants.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );

//			if( applet!=null ){			
//				String cookie = (String)JSObject.getWindow(applet).eval("document.cookie");
//				logger.info("cookie " + cookie);
//				setCookie( cookie );
//
//				setAppletContext( applet.getAppletContext() );
//			}

			idSelected = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_IDSELECTED );
			logger.info("Parametro " + ConfigConstants.PROPERTY_IDSELECTED + ": " + idSelected);
			setIdSelected( idSelected );

			callBack = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CALLBACK );
			logger.info("Parametro " + ConfigConstants.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack( callBack );

		} catch (Exception e) {
			logger.info("Errore ", e );
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

	public String getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(String idSelected) {
		this.idSelected = idSelected;
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

	@Override
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName,
			PreferenceManager preferenceManager) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


}
