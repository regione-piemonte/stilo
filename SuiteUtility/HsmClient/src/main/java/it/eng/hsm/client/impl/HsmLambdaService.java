package it.eng.hsm.client.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.XadesData;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.LambdaServiceConfig;
import it.eng.hsm.client.config.RestConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;
import it.eng.hsm.client.util.ConnectionFactory;
import it.eng.hsm.client.util.JWTUtils;

public class HsmLambdaService extends HsmImpl {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public SignResponseBean firmaCades(List<byte[]> listaBytesFileDaFirmare, SignOption lambdaServiceOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}
	
	@Override
	public SignResponseBean firmaPades(List<byte[]> listaBytesFileDaFirmare, SignOption lambdaServiceOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaCadesParallela(List<byte[]> bytesFileDaFirmare, SignOption lambdaServiceOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaXades(List<byte[]> listaBytesFileDaFirmare, SignOption lambdaServiceOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file xades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof LambdaServiceConfig)) {
			logger.error("Non e' stata specificata la configurazione per LambdaService" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione LambdaService non specificata");
		}

		LambdaServiceConfig lambdaServiceConfig = (LambdaServiceConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = lambdaServiceConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}

		boolean sigillo = (lambdaServiceOption != null) && lambdaServiceOption.isSigillo();
		
		logger.debug("sigillo: " + sigillo);
		
		//https://apitest.comune.genova.it:28243/timbrodigitale/xades
		String urlEndpoint = restConfig.getUrlEndpoint();

		logger.debug("urlEndpoint: " + urlEndpoint);
		if (urlEndpoint == null ) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		DefaultClientConfig cc = new DefaultClientConfig();
		Client client = null;
		if (lambdaServiceConfig.getProxyConfig() != null && lambdaServiceConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(lambdaServiceConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}

		String path = "/timbrodigitale/xades/";
		
		System.out.println("urlEndpoint " + urlEndpoint);
		WebResource webResource = client.resource(urlEndpoint);

		XadesData xadesData = new XadesData();
		xadesData.setCfg(lambdaServiceConfig.getCfg());
		xadesData.setOper("xades-bes");
		int index1 = 1;
		for (byte[] bytesFileDaFirmare : listaBytesFileDaFirmare) {
			
			logger.debug("Aggiungo il file con indice " + index1 );
			String fileEncoded = Base64.getEncoder().encodeToString(bytesFileDaFirmare);
			xadesData.setData(fileEncoded);
			index1++;
		}
		String alias = lambdaServiceConfig.getAlias();//"GT50";
		String urlAud = lambdaServiceConfig.getUrlAud();//"https://apitest.comune.genova.it:28243/oauth2/token";
		String appCode = lambdaServiceConfig.getAppCode();//"PROTOCOLLO_TEST";

		String jwtToken = getJWT(alias, lambdaServiceConfig.getCfg(), urlAud, appCode);
		
		System.out.println("PATH  " + path);
		ClientResponse response = webResource
				.path(path)
				.header("Authorization", "Bearer " + lambdaServiceConfig.getIdSession())
				.header("x-comge-jwt-assertion", jwtToken)
				.header("Content-Type", "application/json")
				.post(ClientResponse.class, xadesData);
			
		logger.info("response.getStatus() " + response.getStatus());
		System.out.println("response status " + response.getStatus());
		
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma xades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.setMessage(messageBean);
			for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		} else {
			String responseJson = response.getEntity(String.class);
			try {
				// firma singola - output direttamente il firmato
				FileResponseBean fileResponseBean = new FileResponseBean();
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				fileResponseBean.setMessage(messageBean);
				String encodedDocFirmato =  getDocFirmato(responseJson);
				byte[] decodedFirmato = Base64.getDecoder().decode(encodedDocFirmato);
				fileResponseBean.setFileFirmato(decodedFirmato);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} catch (Exception e) {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma xades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for (int i = 0; i < listaBytesFileDaFirmare.size(); i++) {
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
		}
		setSignResponseMessage(signResponseBean);
		return signResponseBean;
		// throw new UnsupportedOperationException("Funzionalita' non supportata");
	}
	
	private String getJWT(String alias, String cfg, String urlAud, String appCode) {
		String jwtToken = "";
		InputStream is;
		try {
			//is = new FileInputStream(pathKs);
			is = getClass().getClassLoader().getResourceAsStream("keystore/" + alias + ".jks");
			
		    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		    keystore.load(is, "changeit".toCharArray());
		    
		    
		    Key key = keystore.getKey(alias, "changeit".toCharArray());
		    System.out.println("dopo getKey" + key);
		    
		    
		    if (key instanceof PrivateKey) {
		    	PrivateKey pk = (PrivateKey)key;
		    	
		    	Certificate cert = keystore.getCertificate(alias);
		    	
		    	jwtToken = JWTUtils.generateToken(pk, cert, alias, cfg, urlAud, appCode);
		    	System.out.println(jwtToken);
		    	
		    	//JWTUtils.validateToken(jwtToken, alias, publicKey);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jwtToken;
	}

	@Override
	public SignResponseBean firmaMultiplaHash(List<HashRequestBean> listaHashDaFirmare) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di apertura sessione di firma - INIZIO");
		SessionResponseBean sessionResponseBean = new SessionResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof LambdaServiceConfig)) {
			logger.error("Non e' stata specificata la configurazione per LambdaService " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione LambdaService non specificata");
		}
		
		LambdaServiceConfig lambdaServiceConfig = (LambdaServiceConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = lambdaServiceConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}
		
		String urlEndpoint = restConfig.getUrlEndpoint();
		logger.debug("urlEndpoint: " + urlEndpoint );
		if (urlEndpoint == null) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		DefaultClientConfig cc = new DefaultClientConfig();
		Client client = null;
		if (lambdaServiceConfig.getProxyConfig() != null && lambdaServiceConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(lambdaServiceConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}
		
		WebResource webResource = client.resource(urlEndpoint);
		
		String path = "/manageToken/getToken";
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("Key", lambdaServiceConfig.getKey());
		formData.add("Secret", lambdaServiceConfig.getSecret());
		
		ClientResponse response = 
				webResource.path(path)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.header("x-requested-with", "XMLHttpRequest")
				.post(ClientResponse.class, formData);
		
		if (Response.Status.OK.getStatusCode() == response.getStatus()) {
			String responseJson  = response.getEntity(String.class);
			logger.debug("responseJson: " + responseJson );
			if (responseJson!=null && !responseJson.equalsIgnoreCase("")) {
				try {
					String access_token = getAccessToken(responseJson);
					sessionResponseBean.setSessionId(access_token);
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					sessionResponseBean.setMessage(messageBean);
				} catch (JSONException e) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("");
					messageBean.setStatus(ResponseStatus.KO);
					sessionResponseBean.setMessage(messageBean);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("");
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			}
		} else {
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("");
			messageBean.setStatus(ResponseStatus.KO);
			sessionResponseBean.setMessage(messageBean);
		}
		
		
		
		return sessionResponseBean;
	}
	
	private String getAccessToken(String response) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		String authToken = jsonObject.getString("access_token");
		return authToken;
	}
	
	private String getDocFirmato(String response) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		String doc = jsonObject.getString("doc");
		return doc;
	}

	@Override
	public SignResponseBean firmaMultiplaHashInSession(List<HashRequestBean> listaHashDaFirmare, String sessionId) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public MessageBean chiudiSessioneFirmaMultipla(String sessionId) throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di chiusura sessione di firma - INIZIO");
		MessageBean messageBean = new MessageBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof LambdaServiceConfig)) {
			logger.error("Non e' stata specificata la configurazione per LambdaService " + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione LambdaService non specificata");
		}
		
		LambdaServiceConfig lambdaServiceConfig = (LambdaServiceConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = lambdaServiceConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}
		
		String urlEndpoint = restConfig.getUrlEndpoint();
		logger.debug("urlEndpoint: " + urlEndpoint );
		if (urlEndpoint == null) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}

		DefaultClientConfig cc = new DefaultClientConfig();
		Client client = null;
		if (lambdaServiceConfig.getProxyConfig() != null && lambdaServiceConfig.getProxyConfig().isUseProxy()) {
			URLConnectionClientHandler ch = new URLConnectionClientHandler(new ConnectionFactory(lambdaServiceConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}

		String path = "/manageToken/revokeToken";
		
		WebResource webResource = client.resource(urlEndpoint);
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("Key", lambdaServiceConfig.getKey());
		formData.add("Secret", lambdaServiceConfig.getSecret());
		formData.add("RevokeToken", sessionId);
		
		ClientResponse response = 
				webResource.path(path)
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.header("x-requested-with", "XMLHttpRequest")
				.post(ClientResponse.class, formData);
		
		if (Response.Status.OK.getStatusCode() == response.getStatus()) {
			MultivaluedMap<String, String> hh = response.getHeaders();
			List<String> revokedToken = hh.get("RevokedAccessToken");
			if( revokedToken!=null && revokedToken.size()>0){
				messageBean.setStatus(ResponseStatus.OK);
			} else {
				messageBean.setStatus(ResponseStatus.KO);
			}
		} else {
			String messageString = response.getEntity(String.class);
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
		}
		return messageBean;
		//throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	public static void main(String[] args) throws JSONException {
		DefaultClientConfig cc = new DefaultClientConfig();
		
		String urlEndpoint="https://apitest.comune.genova.it:28243/";
		Client client = Client.create(cc);
		WebResource webResource = client.resource(urlEndpoint).path("timbrodigitale/xades");
		
		WebResource.Builder builder = webResource.getRequestBuilder();
		
		 XadesData xadesData = new XadesData();
			xadesData.setCfg("GEProtocolloXades");
			xadesData.setOper("xades-bes");
			xadesData.setData("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjxyb290Pg0KICAgIDxjYW1wbzE+ICBhYWFhPC9jYW1wbzE+DQogICAgPGNhbXBvMj5iYmJiYjwvY2FtcG8yPg0KPC9yb290Pg==");
		
			String s="{ \"oper\": \"xades-bes\",    \r\n"
					+ "\"cfg\": \"GEProtocolloXades\",    \r\n"
					+ "\"data\": \"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjxyb290Pg0KICAgIDxjYW1wbzE+ICBhYWFhPC9jYW1wbzE+DQogICAgPGNhbXBvMj5iYmJiYjwvY2FtcG8yPg0KPC9yb290Pg==\"}";
			
			System.out.println("chiamo");
			/* ClientResponse response = webResource
					.path("timbrodigitale/xades")
					.header("x-comge-jwt-assertion", "eyJ4NWMiOlsiTUlJQ3RqQ0NBWjZnQXdJQkFnSUVaY1RqeFRBTkJna3Foa2lHOXcwQkFRc0ZBREFjTVFzd0NRWURWUVFHRXdKSlZERU5NQXNHQTFVRUF3d0VSMVExTURBZ0Z3MHlOREF5TURneE5ESXpNREZhR0E4eU1EY3dNREl3TnpFME1qTXdNVm93SERFTE1Ba0dBMVVFQmhNQ1NWUXhEVEFMQmdOVkJBTU1CRWRVTlRBd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUJEd0F3Z2dFS0FvSUJBUUM3ZmFrbGVxaXIyM1pCYU5adjBycEFZM3BFdFFzdktWdFRkMGtMR1Q1MThNR0ZNZ1VPTVF4SHd1WnpFc3cwVklBZW8yMXNFOGxkUDdSMzFTWUd6NFNXdDc1Q3lmN2FheXQxQzVGYzI2VDBWYUFXVzlCbmtvd2dpajJkdVVjU0ZoWFNCVkRzRm5xSFBxUnhhcXRoVGI1aTg4aHdwVjJxdWFCMFNVWm5XR2tENEdXUWFIa2ZLZlptRm9JMk1wZVVNSFM0czlXUmt6bVBFUXZGZ0NURGNHRDJIaDd2SE1Zdk9QejE0OUM3RWU5b3cvTGhmNmFyekQvc3NZWWwvU1RIWHo2czJ5TkZtSXMwbmhKVC94SzZieWNBaGpPSENFclRJcXJsYjZKMWY3T3dsbG04UmhOL2hYdFJhTkpQRVJCL3NaR1oxNHdCUFJJWWI1VHJzdGZiVVdGOUFnTUJBQUV3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUtoQWJacnBIcWNzQUY1UVBwQmExcGdoNFBKaDUvcDFUOTRqb0tnalNpUTd4MytYMU5hWVdkUEM0VXoxMGQ5eEhkSkhxOEgvTU03NzdkZU5SV1QzWlQvZ2wzbTRsVVBiTWZCUXIvWFY2Q2FoNnZYdzdBcVFqR0x3WUJNWGhLK1QxbUpQZ2Evb3ZBK2I3RHFLclpiVlVxdzY0ZzRQVkJQeWh6aVUvZ21vWEVaR1JpdjN6MUx1NndsNUpJYW01N2t0MmYwVzhDcWpIZlVveEw3eHE4RDhLYjJRdUVKRU5yVjJiTVR6aE1MeHp4dFJsU2ZLbVR2YWpIbVJaeWQ3Rk1qRWV1UkxZZVcvNUJGQjRLS3dCeVFDbkxkMmhnWDlsbXZsQllQdjFXeWRUa1FCTGxBVG0zR1g5NGlDaWtUOG5RZDVKNlpna1NrVHVDV1FjMlpFMTVETnBHMD0iXSwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYiLCJ4NXQjUzI1NiI6Imt6LUVkSmVVeVNadEY2WUpWN3Q4THJyR096RnRmVkhwejhjMm1HbG5jblkifQ.eyJhdWQiOiJodHRwczovL2FwaXRlc3QuY29tdW5lLmdlbm92YS5pdDoyODI0My9vYXV0aDIvdG9rZW4iLCJuYmYiOjE3MDc0OTM4ODYsImNmZyI6IkdFUHJvdG9jb2xsb1hhZGVzIiwiaXNzIjoiR1Q1MCIsImlhdCI6MTcwNzQ5Mzg4NiwidXNlcl9jZiI6IiIsImNsaWVudF9pZCI6IlBST1RPQ09MTE9fVEVTVCIsImFwcF9jb2RlIjoiUFJPVE9DT0xMT19URVNUIiwic3ViIjoiR1Q1MCIsImV4cCI6MTcwNzU4MDI4NiwianRpIjoiMWYxZTg4NWYtNDVlNC00NDNiLTg5NGYtYmJiM2VjNzE2MTNjIn0.HzH9wiBHPsYtTX1WNM4__QvqyGcbFm6uEZTyw6jmm2kDeqVhg0GbrJkEGwa4Za-jc2b-h7mBwTt0_3yOI9UjSW8fvTmb8m44Ty3CPQpCNbR6KkFomfo19EXlT2Mmwhz9oegwy15xZn98A0M6JfiQu3qbFMOEJb2oaMEXn0skSI3zeTK2oxQjfuEDKvuRXyyjm0X25xfBRXhQMTFVABQeLFKhHKphdQth2o_qhzGOT7EF0KVintlHf6HPmUDSd46e-e_VsYm3xXBtO9swLWt3KUUrcpP7yPN4lmddVefFUkFwv6cfOZRbX9qzq5u478NoPZ5YJbtevR0iiEa8-Jwf2w")
					.header("Authorization", "Bearer eyJ4NXQiOiJaR05oWldSalltWTRNakkyWldGa016QTBZelpqTVdabVptUTVaV0l3WW1SbFpUWmlNamxpWVEiLCJraWQiOiJPV0ZqTjJZM01HTmxPV1k0WVRFMU1qRTNOemN6TW1KbVpETTNPV1ZsWkdZeE1UQTRNbVUxTm1OaVpETXhaVFppTVROa05qWmpaamd6TXpkbFpqVXlNUV9SUzI1NiIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJzb3R0b3Njcml0dG9yZSIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiSGdmRWNRZHdQbWRvam00Q3p5NDFIMVBJR0ZZYSIsIm5iZiI6MTcwNzQ5Mzg4NiwiYXpwIjoiSGdmRWNRZHdQbWRvam00Q3p5NDFIMVBJR0ZZYSIsInNjb3BlIjoiZGV2aWNlXzQ4MjA2LjAiLCJpc3MiOiJodHRwczpcL1wvbG9jYWxob3N0Ojk0NDNcL29hdXRoMlwvdG9rZW4iLCJleHAiOjE3MDc0OTc0ODYsImlhdCI6MTcwNzQ5Mzg4NiwianRpIjoiNTc1YzRkNzgtNGI5OS00NGRlLWE4NzctNThiMTIyZDJhMTIzIn0.jAvgZ-Mr8q0mVn6wzDQXsEvfMsZD_xadphwao_UA5lSQecX2vyijUC-F-QGWFL-mfBv7iO6dq7INaET0mnfbiSVr4q1Cd94R3qUz26nbSuMxaXZcLJTXNrlc9O_066VQ9Vr0AOQjrfrLquj7WDgdhWrjD-SosQ7qCBETNpMRewnHZJNubqpwEKpvxETt2YhT18sbSmg2ngOsP5VJPZAhft14_tKXGFnlZCXCwXMalOheT8LCf7EN8zJZ6zassYR5qmGQbDKaT0aWigtpYUjKwqFb_1tIDjRhcAEvfBTMoGstmBGZ42lifRkaWJRhfkUsAbSYllCw5P8eIYzEj_ZINXvJflljWyPScolFHHIoXGFuAsGDAabW75E5q3KTtPhSD4AveDoyABB1y0ypR2z6RKHsZyvYLJm94QHAwCjbBKWHHMVPG4BqY4m0Jqxyti5saaBGnTxfC7XWFuYy8VbrShoqI16hx1nswdvyBOrAaHVU8e5_qb_qB8CqSDKGOhDuExYwfU0ULFb433EQpDvkbymoQOvmXRrWN9Wz45amW3QcnHvp4ZBKQEmT2SX4Wj7lCL8sCv6LMcTJQnNHm2LcUX8AwvFialXfv9biUCeiu1NLCVL2XVxQ67YXfBaOju2J5uI_JunMMIDA6PJbt9n3MpPv-WQVPzVZdMaKeFXDU0g")
					//.header("x-requested-with", "XMLHttpRequest")
					.accept(MediaType.APPLICATION_JSON)
					.post(
				 ClientResponse.class, xadesData);*/
			
			builder.header("x-comge-jwt-assertion", "eyJ4NWMiOlsiTUlJQ3RqQ0NBWjZnQXdJQkFnSUVaY1RqeFRBTkJna3Foa2lHOXcwQkFRc0ZBREFjTVFzd0NRWURWUVFHRXdKSlZERU5NQXNHQTFVRUF3d0VSMVExTURBZ0Z3MHlOREF5TURneE5ESXpNREZhR0E4eU1EY3dNREl3TnpFME1qTXdNVm93SERFTE1Ba0dBMVVFQmhNQ1NWUXhEVEFMQmdOVkJBTU1CRWRVTlRBd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUJEd0F3Z2dFS0FvSUJBUUM3ZmFrbGVxaXIyM1pCYU5adjBycEFZM3BFdFFzdktWdFRkMGtMR1Q1MThNR0ZNZ1VPTVF4SHd1WnpFc3cwVklBZW8yMXNFOGxkUDdSMzFTWUd6NFNXdDc1Q3lmN2FheXQxQzVGYzI2VDBWYUFXVzlCbmtvd2dpajJkdVVjU0ZoWFNCVkRzRm5xSFBxUnhhcXRoVGI1aTg4aHdwVjJxdWFCMFNVWm5XR2tENEdXUWFIa2ZLZlptRm9JMk1wZVVNSFM0czlXUmt6bVBFUXZGZ0NURGNHRDJIaDd2SE1Zdk9QejE0OUM3RWU5b3cvTGhmNmFyekQvc3NZWWwvU1RIWHo2czJ5TkZtSXMwbmhKVC94SzZieWNBaGpPSENFclRJcXJsYjZKMWY3T3dsbG04UmhOL2hYdFJhTkpQRVJCL3NaR1oxNHdCUFJJWWI1VHJzdGZiVVdGOUFnTUJBQUV3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUtoQWJacnBIcWNzQUY1UVBwQmExcGdoNFBKaDUvcDFUOTRqb0tnalNpUTd4MytYMU5hWVdkUEM0VXoxMGQ5eEhkSkhxOEgvTU03NzdkZU5SV1QzWlQvZ2wzbTRsVVBiTWZCUXIvWFY2Q2FoNnZYdzdBcVFqR0x3WUJNWGhLK1QxbUpQZ2Evb3ZBK2I3RHFLclpiVlVxdzY0ZzRQVkJQeWh6aVUvZ21vWEVaR1JpdjN6MUx1NndsNUpJYW01N2t0MmYwVzhDcWpIZlVveEw3eHE4RDhLYjJRdUVKRU5yVjJiTVR6aE1MeHp4dFJsU2ZLbVR2YWpIbVJaeWQ3Rk1qRWV1UkxZZVcvNUJGQjRLS3dCeVFDbkxkMmhnWDlsbXZsQllQdjFXeWRUa1FCTGxBVG0zR1g5NGlDaWtUOG5RZDVKNlpna1NrVHVDV1FjMlpFMTVETnBHMD0iXSwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYiLCJ4NXQjUzI1NiI6Imt6LUVkSmVVeVNadEY2WUpWN3Q4THJyR096RnRmVkhwejhjMm1HbG5jblkifQ.eyJhdWQiOiJodHRwczovL2FwaXRlc3QuY29tdW5lLmdlbm92YS5pdDoyODI0My9vYXV0aDIvdG9rZW4iLCJuYmYiOjE3MDc3MzI5NTAsImNmZyI6IkdFUHJvdG9jb2xsb1hhZGVzIiwiaXNzIjoiR1Q1MCIsImlhdCI6MTcwNzczMjk1MCwidXNlcl9jZiI6IiIsImNsaWVudF9pZCI6IlBST1RPQ09MTE9fVEVTVCIsImFwcF9jb2RlIjoiUFJPVE9DT0xMT19URVNUIiwic3ViIjoiR1Q1MCIsImV4cCI6MTcwNzgxOTM1MCwianRpIjoiMjM2YjJkNTQtYmNjNy00MDIyLWJhYWUtZjBjMjlhZjY5M2RkIn0.Qc4a_63zSLbUBPoegIiYzKDwgyPqz7_5vh2x3GWgHD7KJ9QqU4Otp4dh0s0bLbaku5jGF89NXnZ9Od5gueEeRzPWVqwqXhkOjvCftall-MqQLWzZ5SJdAUKKE_OOUiFshaVp8xKkpB_CBZXD6ZEmocB6Bpix4w72QcmZaIPn_D_2OcIp9aFwhtxT9XBiz1nJXNmTVGV0tAI6Q6ZIJSWdawpWX4xRzaKD2Z7BbdZ0tBlfZhUltK6qCm9jWEMuYsB5ecXMcjThffewrVtpMBxu43cUTSR_-3Y18X9W4eXcTATxl7AuctuidhFATd3SiVX1AA3_YCFR1Q6N-JGWTpKDgg");
			builder.header("Authorization", "Bearer eyJ4NXQiOiJaR05oWldSalltWTRNakkyWldGa016QTBZelpqTVdabVptUTVaV0l3WW1SbFpUWmlNamxpWVEiLCJraWQiOiJPV0ZqTjJZM01HTmxPV1k0WVRFMU1qRTNOemN6TW1KbVpETTNPV1ZsWkdZeE1UQTRNbVUxTm1OaVpETXhaVFppTVROa05qWmpaamd6TXpkbFpqVXlNUV9SUzI1NiIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJzb3R0b3Njcml0dG9yZSIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiSGdmRWNRZHdQbWRvam00Q3p5NDFIMVBJR0ZZYSIsIm5iZiI6MTcwNzczMjk1MywiYXpwIjoiSGdmRWNRZHdQbWRvam00Q3p5NDFIMVBJR0ZZYSIsInNjb3BlIjoiZGV2aWNlXzI2MDYxMi4wIiwiaXNzIjoiaHR0cHM6XC9cL2xvY2FsaG9zdDo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNzA3NzM2NTUzLCJpYXQiOjE3MDc3MzI5NTMsImp0aSI6IjUzNTE3YWZiLTA2OGItNGFlNS1hNmM0LThjYjRiNDJiNjUyMiJ9.FVU0Tf7Ihu-hkiJ2_-bNUfEj62hKvYnfOuzwQADteVYS5S298V4fFm_27EURHV2FOQTDDrs0-VE6d1CyOKJexw_32je_L08JArLnXOVpv_OxxFg9tx-1JyFFc19p2V90aLkwE7UB0EkaUFfEwBQpO9ABJj9FOv4qqaq8cckswYkObQYQpL-x6Bt0LambLDUvXHUSpfrWgl8qEMcjhgKJABIL1uIdWte5GW2RhxXQur3nNCk-TXwXWrIgkW-b0A6x5TamWdmpe3iaQ2eYWrBbHNpcCiwYXUj_74G_0Xz-FpN_iygCsdIObUwDEq2sJD6cdJwNFVxboBj5ZV0Ul5QPqC5_jtFBo4SzI23ggK33HID5DbWP4fTeBpvu8CL1Dh_I0Sr081dQPneUrex_iMWfayUCW8izPJAkb_5dDiFmMfMpFXRmJXLpvJ4VodUHPiZaZ9OyFP6gdNndFT8iFxgebKC5XHsSRAr8aFbf5AFn5XC-KN4d9M7sJzXhobApsANwy3SLuTqAlQzFiappqBRiTwx1zdyb0T91eApf6fvtKPi_6CAPmvTqbgmtq3ym4cHp41xwvZHhMKcNx9FRohGOpNOCnQAndXZ2g3tcvevArGNF87k4xD9_EODlrLnxbGMUjajmpZKUpdEWvAUJcKIZgilEx-M7R1cdFvQMYOSd2H4");

		    ClientResponse response = builder.header("Content-Type", "application/json")
		    		//.accept(MediaType.APPLICATION_JSON)
		    		.post(ClientResponse.class, s);
		   
			 System.out.println("ottengo" +  response.getStatus());
			 String messageString = response.getEntity(String.class);
			 System.out.println(messageString);
	}
	
}
