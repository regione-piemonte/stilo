package it.eng.areas.hybrid.module.cryptoLight.outputFileProvider;

import java.applet.AppletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

public class AurigaProxyMultiHashFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger( AurigaProxyMultiHashFileOutputProvider.class );
	
	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String cookie;
	private AppletContext appletContext;
	private String callBackStart;
	
	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;

	private String callBackResult = null;
	
	@Override
	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, X509Certificate certificatoDiFirma, String... params) throws Exception {
		logger.info("Metodo saveOutputFile");
		if(getOutputUrl()!=null){
			if (getCallBackStart()!=null){
			}
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			CloseableHttpResponse response = null;
			String lStringOriginalFileName = fileInputName;
			File lFileOut;
			
			try {
				HttpPost request = new HttpPost(getOutputUrl());
				RequestConfig config = RequestConfig.custom().build();
				lFileOut = File.createTempFile("fileTemp", ".tmp");
				logger.info("idDoc " + params[0]);
//				StringBody lStringBodyIdDoc = new StringBody( params[0]!=null? params[0]:"", ContentType.TEXT_PLAIN);
//				StringBody lStringBodySignedBean = new StringBody(params[1]!=null?params[1]:"", ContentType.TEXT_PLAIN);
//				StringBody lStringBodyFirmatario = new StringBody( params[2]!=null? params[2]:"", ContentType.TEXT_PLAIN);
//				StringBody lStringBodyPathFileTemp = new StringBody(params[3]!=null?params[3]:"", ContentType.TEXT_PLAIN);
//				StringBody lStringBodyVersioneDoc = new StringBody( params[4]!=null? params[4]:"", ContentType.TEXT_PLAIN);
//				HttpEntity reqEntity = MultipartEntityBuilder.create()
//				.addPart("idDoc", lStringBodyIdDoc)
//				.addPart("signedBean", lStringBodySignedBean)
//				.addPart("firmatario", lStringBodyFirmatario)
//				.addPart("pathFileTemp", lStringBodyPathFileTemp)
//				.addPart("versioneDoc", lStringBodyVersioneDoc)
//				.build();
//				request.setEntity(reqEntity);
//				request.setConfig(config);
//
//				String result = null;
//				BufferedReader br = null;
//				response = lDefaultHttpClient.execute(request);
				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
				if(params[0]!=null){
					logger.info("idDoc " + params[0]);
					lListParames.add(new BasicNameValuePair("idDoc", params[0]));
				}
				if(params[1]!=null){
					lListParames.add(new BasicNameValuePair("signedBean", params[1]));
				}
				if(params[2]!=null){
					logger.info("firmatario " + params[2]);
					lListParames.add(new BasicNameValuePair("firmatario", params[2]));
				}
				if(params[3]!=null){
					logger.info("pathFileTemp " + params[3]);
					lListParames.add(new BasicNameValuePair("pathFileTemp", params[3]));
				}
				if(params[4]!=null){
					logger.info("versioneDoc " + params[4]);
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
				if (callBack!=null && !callBack.equals("")){
//					String lStrToInvoke = "javascript:" + callBack + "('" + result +"');";
//					JSObject.getWindow(applet).eval( lStrToInvoke );
					callBackResult = result;
				}
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
//				try {
//					String cookie = (String)JSObject.getWindow(applet).eval("document.cookie");
//					logger.info("cookie " + cookie);
//					setCookie( cookie );
//				} catch (Exception e){
//
//				}
//				setAppletContext( applet.getAppletContext() );
//			}
			idSelected = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDSELECTED );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_IDSELECTED + ": " + idSelected);
			setIdSelected( idSelected );

			callBack = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACK );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack(callBack);

			callBackStart = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACK_START );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACK_START + ": " + callBackStart);
			setCallBackStart(callBackStart);

			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if( autoClosePostSignString!=null )
				autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

			callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
			
			
//			if( applet!=null ){			
//				setApplet(applet);
//			}
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

	public String getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(String idSelected) {
		this.idSelected = idSelected;
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

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}
	
	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}
	
	@Override
	public String getCallbackResult() {
		return callBackResult;
	}

	@Override
	public String getCallback() {
		return this.callBack;
	}

}
