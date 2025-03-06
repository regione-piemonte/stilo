package it.eng.client.applet.fileProvider;


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

public class MultiHashFileOutputProvider implements FileOutputProvider {

	private String outputFileName;

	private String outputUrl;

	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;
	
	@Override
	public boolean saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, String... params) throws Exception {
		if(getOutputUrl()!=null){
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			CloseableHttpResponse response = null;
			try {
				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
				if(params[0]!=null){
					LogWriter.writeLog("idDoc " + params[0]);
					lListParames.add(new BasicNameValuePair("idDoc", params[0]));
				}
				if(params[1]!=null){
					lListParames.add(new BasicNameValuePair("signedBean", params[1]));
				}
				if(params[2]!=null){
					LogWriter.writeLog("firmatario " + params[2]);
					lListParames.add(new BasicNameValuePair("firmatario", params[2]));
				}
				if(params[3]!=null){
					LogWriter.writeLog("pathFileTemp " + params[3]);
					lListParames.add(new BasicNameValuePair("pathFileTemp", params[3]));
				}
				if(params[4]!=null){
					LogWriter.writeLog("versioneDoc " + params[4]);
					lListParames.add(new BasicNameValuePair("versioneDoc", params[4]));
				}
				HttpUriRequest upload = RequestBuilder.post()
                .setUri(new URI(getOutputUrl()))
                .addParameters(lListParames.toArray(new NameValuePair[]{}))
                .build();
				
				String result = null;
				BufferedReader br = null;
				response = lDefaultHttpClient.execute(upload);
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
				LogWriter.writeLog("Errore", e );
				return false;
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
		
		String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
		if( autoClosePostSignString!=null )
			autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

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
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}
	
	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}
}
