/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.ALFRESCO)
public class AlfrescoStorage implements Storage {
	
	private static final String AUTHORIZATION = "Authorization";

	private AlfrescoStorageConfig alfrescoStorageConfig;

	private WebResource webResource;
	
	private String username;
	private String password;
	private String storeType;
	private String storeId;
	private String serviceEndpoint;
	private Integer serviceTimeout;


	private String idStorage;
	private String utilizzatoreStorage;

	private static Logger logger = Logger.getLogger(AlfrescoStorageConfig.class);

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			alfrescoStorageConfig = (AlfrescoStorageConfig) XMLUtil.newInstance().deserializeIgnoringDtd(xmlConfig, AlfrescoStorageConfig.class);
			if (alfrescoStorageConfig != null) {
				this.username = alfrescoStorageConfig.getUsername();
				this.password = alfrescoStorageConfig.getPassword();
				this.storeType = alfrescoStorageConfig.getStoreType();
				this.storeId = alfrescoStorageConfig.getStoreId();
				this.serviceEndpoint = alfrescoStorageConfig.getServiceEndpoint();
				this.serviceTimeout = alfrescoStorageConfig.getServiceTimeout();
			}
		} catch (Exception e) {
			logger.warn("Eccezione configurazione SharePointStorageConfig", e);
			throw new StorageException(e);
		}

	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		throw new StorageException("Metodo insert non implementato su AlfrescoStorage");
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		logger.debug("Metodo insertInput");
		throw new StorageException("Metodo insertInput non implementato su AlfrescoStorage");
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		throw new StorageException("Metodo printMessage non implementato su AlfrescoStorage");
	}

	@Override
	public void delete(String id) throws StorageException {
		throw new StorageException("Metodo delete non implementato su AlfrescoStorage");
	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		logger.debug("Start retrieve File Stream");
		logger.info("Cerco il documento con id " + id);
		
		try {
			
			Client client = Client.create(new DefaultClientConfig());
			client.setReadTimeout(serviceTimeout);
			
			String path = serviceEndpoint + "/alfresco/service/api/node/content/" + storeType + "/" + storeId + "/" + id;
			webResource = client.resource(path);

			String tokenBasicAuth = username + ":" + password;
			tokenBasicAuth = Base64.encodeBase64String(tokenBasicAuth.getBytes());
			String autorization = "Basic " + tokenBasicAuth;

			ClientResponse response = webResource
					.header(AUTHORIZATION, autorization).get(ClientResponse.class);
			
			if (response != null && response.getStatus() == 200) {
				InputStream contentStream = response.getEntityInputStream();
				return contentStream;
			} else {
				logger.error("URL invocato: " + path);
				logger.error("Username: " + username + ", Password: " + password + ", TokenBasicAuth: " + tokenBasicAuth);
				if (response == null) {
					logger.error("Response: NULL");
					throw new StorageException("Response null");
				} else {
					logger.error("Response: " + response);
					logger.error("Response status: " + response.getStatus());
					throw new StorageException("Response con errore " + response.getStatus());
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
			throw new StorageException(e);
		} 
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		logger.debug("Start retrieve File Stream");
		logger.info("Cerco il documento con id " + id);
		
		try {
			
			Client client = Client.create(new DefaultClientConfig());
			client.setReadTimeout(serviceTimeout);
			
			String path = serviceEndpoint + "/alfresco/service/api/node/content/" + storeType + "/" + storeId + "/" + id;
			webResource = client.resource(path);

			String tokenBasicAuth = username + ":" + password;
			tokenBasicAuth = Base64.encodeBase64String(tokenBasicAuth.getBytes());
			String autorization = "Basic " + tokenBasicAuth;

			ClientResponse response = webResource
					.header(AUTHORIZATION, autorization).get(ClientResponse.class);
			
			if (response != null && response.getStatus() == 200) {
				InputStream contentStream = response.getEntityInputStream();
				File tempFile = File.createTempFile("temp", "");
	            FileUtils.copyInputStreamToFile(contentStream, tempFile);
				return tempFile;
			} else {
				logger.error("URL invocato: " + path);
				logger.error("Username: " + username + ", Password: " + password + ", TokenBasicAuth: " + tokenBasicAuth);
				if (response == null) {
					logger.error("Response: NULL");
					throw new StorageException("Response null");
				} else {
					logger.error("Response: " + response);
					logger.error("Response status: " + response.getStatus());
					throw new StorageException("Response con errore " + response.getStatus());
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
			throw new StorageException(e);
		} 


	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		return retrieveFile(id);
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		throw new StorageException("Metodo writeFileItem non implementato su AlfrescoStorage");
	}

	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}

	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;
	}

	private File getFile(byte[] datiFile, String extension) throws IOException {
		File temp = File.createTempFile("temp", extension);
		FileOutputStream out = new FileOutputStream(temp);
		out.write(datiFile);
		out.flush();
		out.close();
		return temp;
	}
}
