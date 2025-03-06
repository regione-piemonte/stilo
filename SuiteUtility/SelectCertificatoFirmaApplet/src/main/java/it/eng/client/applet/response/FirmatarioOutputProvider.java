package it.eng.client.applet.response;

import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.util.PreferenceKeys;

import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;

public class FirmatarioOutputProvider implements GenericResponse {

	private String outputFileName;

	private String outputUrl;

	private boolean autoClosePostSearch = false;
	private String callBackAskForClose = null;
	
	@Override
	public boolean saveOutput(String... params) throws Exception {
		
		/**
		 * Questo metodo viene normalmente chiamato quando si vuole eseguire una chiamata
		 * ad una classe in Auriga 
		 * In questa situazione non è utilizzato perchè il passaggio di parametri
		 * viene eseguito tramite callback
		 */
			
//		if(getOutputUrl()!=null){
//			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
//			CloseableHttpResponse response = null;
		
		
			//Prelevo i valori del pin e dell'alias da utilizzare per firmare il pdf
//			try {
//				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
//				if(params[0]!=null){
////					LogWriter.writeLog("pin " + params[0]);
//					lListParames.add(new BasicNameValuePair("pin", params[0]));
//				}
//				
//				if(params[1]!=null){
////					LogWriter.writeLog("firmatari " + params[1]);
//					lListParames.add(new BasicNameValuePair("firmatari", params[1]));
//				}
//				
////				LogWriter.writeLog("outputURL: "+getOutputUrl());
//				
////				HttpUriRequest upload = RequestBuilder.post()
////	                .setUri(new URI(getOutputUrl()))
////	                .addParameters(lListParames.toArray(new NameValuePair[]{}))
////	                .build();
////				
////				String result = null;
////				BufferedReader br = null;
////				response = lDefaultHttpClient.execute(upload);
//				System.out.println(response.getStatusLine());
//				StringBuilder sb = new StringBuilder();
//				String line;
//				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//				while ((line = br.readLine()) != null) {
//					sb.append(line);
//				}
//				result = sb.toString();
//				if (response.getStatusLine().getStatusCode()!=200) throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
//			} catch (Exception e) {
//				LogWriter.writeLog("Errore", e );
//				return false;
//			} finally {
//				if (response!=null){
//					response.close();
//				}
//			}
//		}
		return true;
	}

	@Override
	public void saveOutputParameter(JApplet applet) throws Exception {
		String outputFileName = null;
		try {
			outputFileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTFILENAME );
			LogWriter.writeLog( "Parametro  " + PreferenceKeys.PROPERTY_OUTPUTFILENAME + ": " + outputFileName);
			setOutputFileName( outputFileName );
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTURL );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String autoClosePostSearchString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSEARCH);
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSEARCH + ": " + autoClosePostSearchString);
		if( autoClosePostSearchString!=null )
			autoClosePostSearch  = Boolean.valueOf( autoClosePostSearchString ); 

		callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
		
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getOutputUrl() {
		return outputUrl;
	}
	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}

	@Override
	public boolean getAutoClosePostSearch() {
		return autoClosePostSearch;
	}

	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}

}
