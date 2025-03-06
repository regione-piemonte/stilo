package it.eng.hybrid.module.selectCertificati.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import it.eng.hybrid.module.selectCertificati.preferences.PreferenceKeys;
import it.eng.hybrid.module.selectCertificati.preferences.PreferenceManager;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

public class FirmatarioOutputProvider implements GenericResponse {

	public final static Logger logger = Logger.getLogger(FirmatarioOutputProvider.class);
	
	private String outputFileName;

	private String outputUrl;

	private boolean autoClosePostSearch = false;
	private String callBackAskForClose = null;
	
	@Override
	public boolean saveOutput(String... params) throws Exception {
			
		if(getOutputUrl()!=null){
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			CloseableHttpResponse response = null;
			try {
				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
				if(params[0]!=null){
					logger.info("pin " + params[0]);
					lListParames.add(new BasicNameValuePair("pin", params[0]));
				}
				
				if(params[1]!=null){
					logger.info("firmatari " + params[1]);
					lListParames.add(new BasicNameValuePair("firmatari", params[1]));
				}
				
				
				HttpUriRequest upload = RequestBuilder.post()
                .setUri(new URI(getOutputUrl()))
                .addParameters(lListParames.toArray(new NameValuePair[]{}))
                .build();
				
				String result = null;
				BufferedReader br = null;
				response = lDefaultHttpClient.execute(upload);
				logger.info("Response status: " + response.getStatusLine());
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
	public void saveOutputParameter() throws Exception {
		String outputFileName = null;
		try {
			outputFileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTFILENAME );
			logger.info( "Parametro  " + PreferenceKeys.PROPERTY_OUTPUTFILENAME + ": " + outputFileName);
			setOutputFileName( outputFileName );
		} catch (Exception e1) {
			logger.error(e1);
		}

		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTURL );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );
		} catch (Exception e1) {
			logger.error(e1);
		}
		
		String autoClosePostSearchString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSEARCH);
		logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSEARCH + ": " + autoClosePostSearchString);
		if( autoClosePostSearchString!=null )
			autoClosePostSearch  = Boolean.valueOf( autoClosePostSearchString ); 

		callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
		logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
		
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
