package org.jpedal.examples.viewer.fileProvider;

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
import org.jpedal.examples.viewer.AppletViewer;
import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.utils.LogWriter;

public class AurigaProxyFileOutputProvider implements FileOutputProvider {

	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String cookie;
	private AppletContext appletContext;
	private String callBackStart;
	private JApplet applet;
	private boolean fileUploaded = false;

	@Override
	public void saveOutputFile(final InputStream in, final String fileInputName, PreferenceManager preferenceManager)
	throws Exception {
		LogWriter.writeLog("Metodo saveOutputFile");
		if(getOutputUrl()!=null){
			AppletViewer lAppletViewer = (AppletViewer)applet;
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
				HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart("userfile", lFileBody)
				.addPart("idSelected", lStringBodyIdSelected)
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
	}

	@Override
	public void saveOutputParameter(JApplet applet, PreferenceManager preferenceManager) throws Exception {

		String urlOutput = null;
		try {
			urlOutput = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OUTPUTURL );
			LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );

			if( applet!=null ){	
				this.applet = applet;
				try {
					String cookie = (String)JSObject.getWindow(applet).eval("document.cookie");
					LogWriter.writeLog("cookie " + cookie);
					setCookie( cookie );
				} catch (Exception e){

				}
				setAppletContext( applet.getAppletContext() );
			}

			callBack = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CALLBACK );
			LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack(callBack);

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
		saveOutputFile(in, fileInputName, preferenceManager);
		LogWriter.writeLog("Upload andato a buon fine: " + fileUploaded);
		return fileUploaded;
	}

}
