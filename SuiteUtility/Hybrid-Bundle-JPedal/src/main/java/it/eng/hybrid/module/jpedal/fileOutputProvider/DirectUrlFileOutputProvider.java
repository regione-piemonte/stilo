package it.eng.hybrid.module.jpedal.fileOutputProvider;

import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

public class DirectUrlFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger( DirectUrlFileOutputProvider.class);
	
	private String outputUrl;
	private String cookie;

	@Override
	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager)  throws Exception {
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
				HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart("userfile", lFileBody)
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
				logger.info("Response " + result);
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
//			}
			
		} catch (Exception e) {
			logger.info("Errore", e);
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
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName,
			PreferenceManager preferenceManager) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
