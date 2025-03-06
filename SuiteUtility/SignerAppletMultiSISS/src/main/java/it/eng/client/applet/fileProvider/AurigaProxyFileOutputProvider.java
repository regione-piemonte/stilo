package it.eng.client.applet.fileProvider;


import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.applet.AppletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

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

public class AurigaProxyFileOutputProvider implements FileOutputProvider {

	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String cookie;
	private AppletContext appletContext;
	private String callBackStart;
	private JApplet applet;

	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;
	
	@Override
	public boolean saveOutputFile(String idFile, InputStream in, String fileInputName, String tipoBust, String... params)
	throws Exception {
		LogWriter.writeLog("Metodo saveOutputFile");
		if(getOutputUrl()!=null){
			if (getCallBackStart()!=null){
			}
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			byte[] contentFile;
			CloseableHttpResponse response = null;
			String lStringOriginalFileName = fileInputName;
			File lFileOut;
			try {
				contentFile = IOUtils.toByteArray(in);
				HttpPost request = new HttpPost(getOutputUrl());
				RequestConfig config = RequestConfig.custom().build();
				lFileOut = File.createTempFile("fileTemp", ".tmp");
				IOUtils.write(contentFile, new FileOutputStream(lFileOut));
				FileBody lFileBody = new FileBody(lFileOut);
				StringBody lStringBodyIdSelected = new StringBody(lStringOriginalFileName!=null?lStringOriginalFileName:"", ContentType.TEXT_PLAIN);
				StringBody lStringBodyIdFile = new StringBody(idFile!=null?idFile:"", ContentType.TEXT_PLAIN);
				HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart("userfile", lFileBody)
				.addPart("idSelected", lStringBodyIdSelected)
				.addPart("idFile", lStringBodyIdFile)
				.build();
				LogWriter.writeLog("idSelected = " + idSelected);
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
					JSObject.getWindow(applet).eval( lStrToInvoke );
				}
			} catch (Exception e) {
				LogWriter.writeLog("Errore", e );
			} finally {
				if (response!=null){
					response.close();
				}
			}
		}
		return true;
	}

	@Override
	public void saveOutputParameter(JApplet applet) throws Exception {
		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTURL );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );

			if( applet!=null ){		
				try {
					String cookie = (String)JSObject.getWindow(applet).eval("document.cookie");
					LogWriter.writeLog("cookie " + cookie);
					setCookie( cookie );
				} catch (Exception e){

				}
				setAppletContext( applet.getAppletContext() );
			}

			idSelected = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDSELECTED );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_IDSELECTED + ": " + idSelected);
			setIdSelected( idSelected );

			callBack = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACK );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack(callBack);

			callBackStart = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACK_START );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACK_START + ": " + callBackStart);
			setCallBackStart(callBackStart);

			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if( autoClosePostSignString!=null )
				autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

			callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
			
			
			if( applet!=null ){			
				setApplet(applet);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
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

	public void setCallBackStart(String callBackStart) {
		this.callBackStart = callBackStart;
	}

	public String getCallBackStart() {
		return callBackStart;
	}

	public JApplet getApplet() {
		return applet;
	}

	public void setApplet(JApplet applet) {
		this.applet = applet;
	}

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}
	
	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}
}
