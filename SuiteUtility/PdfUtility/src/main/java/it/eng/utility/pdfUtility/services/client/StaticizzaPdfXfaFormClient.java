package it.eng.utility.pdfUtility.services.client;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class StaticizzaPdfXfaFormClient {
	
	public static final Logger logger = LogManager.getLogger(StaticizzaPdfXfaFormClient.class);

	private static final String PARAM_FILE = "in_document_pdf";
	private static final String PARAM_SPECIFICHE = "in_xml_specifiche";
	private static final String AUTHORIZATION = "Authorization";
	
	private String username;
	private String password;
	private Integer timeout;
	private String endpoint;
	
	private WebResource webResource;
	private Client client;
	
	public StaticizzaPdfXfaFormClient(StaticizzaPdfXfaFormConfigBean configBean) throws Exception {		
		if(configBean!=null && configBean.getUsername()!=null && 
				configBean.getPassword()!=null && configBean.getEndpoint()!=null  && configBean.getTimeout()!=null) {
			username = configBean.getUsername();
			password = configBean.getPassword();
			timeout = configBean.getTimeout();
			endpoint = configBean.getEndpoint();
		}else {
			throw new Exception("Parametri di configurazione mancanti o incompleti");
		}		
		
		try {	
			ClientConfig cc = new DefaultClientConfig();
			cc.getClasses().add(MultiPartWriter.class);
			client = Client.create(cc);
			logger.debug("Imposto il timeout " + timeout);
			client.setReadTimeout(timeout);

			logger.debug("Richiamo l'endpoint " + endpoint);
			webResource = client.resource(endpoint);			
			
		}  catch (Exception e) {
			String errorMesage = "Errore durante la configurazione del servizio: Errore = " + e.getMessage();
			logger.error(errorMesage);
			throw new Exception(errorMesage);
		}			
	
	}
	
	public InputStream staticizzaPdfXfaForm(File filePdf, XmlSpecificheBean xmlSpecifiche) throws Exception {
		String responseString = null;
		
		try {
			
			String xmlSpecificheString="<ManagePdf><Command>"+xmlSpecifiche.getCommand()+"</Command></ManagePdf>";
			logger.debug("xmlSpecificheString " + xmlSpecificheString);
			
			FormDataMultiPart multiForm;
			multiForm = new FormDataMultiPart();
			multiForm.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
			
//			multiForm.field(PARAM_SPECIFICHE, xmlSpecifiche);
			
			multiForm.bodyPart(new FormDataBodyPart(PARAM_SPECIFICHE, xmlSpecificheString, MediaType.APPLICATION_JSON_TYPE));
			
			FileDataBodyPart fdbp = new FileDataBodyPart(PARAM_FILE, filePdf);
			multiForm.bodyPart(fdbp);
			
//			MultipartFile fileMultiPart;	
//			fileMultiPart = new MockMultipartFile((filePdf.getPath()), new FileInputStream(filePdf.getPath()));
//	
//			FormDataBodyPart bodyPart = new FormDataBodyPart(PARAM_FILE, new ByteArrayInputStream(fileMultiPart.getBytes()),
//					MediaType.APPLICATION_OCTET_STREAM_TYPE);
//			bodyPart.setContentDisposition(FormDataContentDisposition.name(PARAM_FILE).fileName(filePdf.getName()).build());
//			multiForm.bodyPart(bodyPart);	
			
			String tokenBasicAuth = username + ":" + password;
			tokenBasicAuth = Base64.encodeBase64String(tokenBasicAuth.getBytes());
			String autorization = "Basic " + tokenBasicAuth;
			logger.debug("autorization " + autorization);
			
			ClientResponse response = 
					webResource
					.accept(MediaType.APPLICATION_XML)
					//.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.MULTIPART_FORM_DATA)
					.header(AUTHORIZATION, autorization)
					.post(ClientResponse.class, multiForm);

			if (Response.Status.OK.getStatusCode() == response.getStatus()) {
				responseString = response.getEntity(String.class);
				logger.info("responseString: " + responseString);
				
			} else {
				logger.error("Errore nell'invocazione del servizio " + response.getStatus());
			}
				
		
		if (StringUtils.isNotBlank(responseString)) {			
			/*LA RISPOSTA E' DI QUESTO TIPO, VERIFICARE SE IL SERVIZIO HA CONVERTITO IL FILE IN BASE AL CODICE DI OUTPUT E 
			 * RECUPERARE IL LINK PER IL DOWNLOAD E PASSARLO ALLA FUNZIONE			 * 
			 * 
			 * <result>
					<out_xml_output>
						<Output>
							<CodiceOutput>0</CodiceOutput>
						</Output>
					</out_xml_output>
					<out_document_pdf length="14329" contentType="application/pdf" file="RegistroRepertorio.pdf" basename="RegistroRepertorio.pdf" ADOBE_SAVE_MODE_REQUIRED_ATTRIBUTE="false" wsfilename="RegistroRepertorio.pdf" ADOBE_SAVE_MODE_FORCE_COMPRESSED_OBJECTS_ATTRIBUTE="false" ADOBE_SAVE_MODE_ATTRIBUTE="NO_CHANGE">http://tst-pdfedit.comuneparma.local:80/DocumentManager/docm1663519330874/c26aa35f4d747e84df5fe0339fab3023?type=YXBwbGljYXRpb24vcGRm</out_document_pdf>
				</result>
			 * 
			 * */		
			
			
			
			String pathDownloadFile = StaticizzaPdfXfaFormUtil.getPathDownload(responseString);
			logger.info("pathDownloadFile " + pathDownloadFile);
			
			if( pathDownloadFile!=null ){
				InputStream isFileStaticizzato = downloadFile(pathDownloadFile);
				return isFileStaticizzato;
			} 
				
		}
		else {
			String errorMessage = "Il servizio non ha restituito risultato";
			logger.error(errorMessage);
			return null;
		}
		} catch(Exception e) {
			String errorMessage = "Errore durante la chiamata al servizio: " + e.getMessage();
			logger.error(errorMessage, e);
			return null;
		}
		
		return null;
	}
	
	private InputStream downloadFile(String pathDownloadFile) {
		InputStream isStaticizzato = null;
		
		Map<String, List<String>> queryParams = getQueryParams(pathDownloadFile);
		
		String[] splitEndpoint = pathDownloadFile.split("\\?");
		String endpoint = splitEndpoint[0];
		
		WebResource webResource = client.resource(endpoint);
		ClientResponse response = null;
		
		for(String nameParam : queryParams.keySet()) {
			webResource.queryParam(nameParam, queryParams.get(nameParam).get(0));
		}
		
		response = webResource.get(ClientResponse.class);
		
				
		if (Response.Status.OK.getStatusCode() == response.getStatus()) {
			isStaticizzato = response.getEntityInputStream();
		} else {
			logger.debug("Il servizio di download ha restituito errore " + response.getStatus());
		}				
		
		return isStaticizzato;
	}
	
	public static Map<String, List<String>> getQueryParams(String url) {
	    try {
	        Map<String, List<String>> params = new HashMap<String, List<String>>();
	        String[] urlParts = url.split("\\?");
	        if (urlParts.length > 1) {
	            String query = urlParts[1];
	            for (String param : query.split("&")) {
	                String[] pair = param.split("=");
	                String key = URLDecoder.decode(pair[0], "UTF-8");
	                String value = "";
	                if (pair.length > 1) {
	                    value = URLDecoder.decode(pair[1], "UTF-8");
	                }

	                List<String> values = params.get(key);
	                if (values == null) {
	                    values = new ArrayList<String>();
	                    params.put(key, values);
	                }
	                values.add(value);
	            }
	        }

	        logger.debug("params " + params);
	        return params;
	    } catch (UnsupportedEncodingException ex) {
	        throw new AssertionError(ex);
	    }
	}

	
}
