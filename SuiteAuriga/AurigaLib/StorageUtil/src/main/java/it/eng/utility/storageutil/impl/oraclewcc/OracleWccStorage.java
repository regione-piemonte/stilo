/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.mail.Message;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.TemporaryRepository;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.TemporaryRepositoryImpl;
import it.eng.utility.storageutil.impl.filesystem.util.EnvironmentVariableConfigManager;
import it.eng.utility.storageutil.impl.oraclewcc.connector.Connector;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;
import oracle.stellent.ridc.protocol.intradoc.IntradocClient;

@TipoStorage(tipo = StorageType.ORACLEWCC)
public class OracleWccStorage implements Storage {

	private static Logger logger = Logger.getLogger(OracleWccStorage.class);
	public Connector INSTANCE;
	@SuppressWarnings("unused")
	private String idStorage;
	@SuppressWarnings("unused")
	private String utilizzatoreStorage;
	static public final String DOCUMENT_TYPE = "PROT";
	// attenzione in inail il tipo DOCUMENT_TYPE � "PROT"
	static public final String SECURITY_GROUP = "Protocollo";

	// Repository temporano da cui recuperare i files
	protected TemporaryRepository temporaryRepository;

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			OracleWccStorageConfig config = (OracleWccStorageConfig) XMLUtil.newInstance().deserialize(xmlConfig, OracleWccStorageConfig.class);
			if (config != null) {
				EnvironmentVariableConfigManager.verifyPathsForVariable(config);
				INSTANCE = Connector.getInstance(config);
				this.temporaryRepository = new TemporaryRepositoryImpl(config.getTempRepositoryBasePath());
			}
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	private String executeCheckIn(IdcContext ctx, InputStream is, long length, String id) throws IdcClientException {
		IntradocClient idcClient = INSTANCE.getIntradocClient();
		DataBinder binder = idcClient.createBinder();
		binder.putLocal("IdcService", "CHECKIN_UNIVERSAL");
		// add a file

		TransferFile transferFile = new TransferFile(is, id, length);
		binder.addFile("primaryFile", transferFile);

		binder.putLocal("dDocTitle", id);
		binder.putLocal("dDocType", DOCUMENT_TYPE);
		binder.putLocal("dSecurityGroup", SECURITY_GROUP);

		// TODO eventuali metadati custom del modello

		ServiceResponse response = idcClient.sendRequest(ctx, binder);
		// Convert the response to a dataBinder
		DataBinder responseData = response.getResponseAsBinder();

		logger.info(String.format("Checking response: %s", responseData.getLocal("StatusMessage")));
		return responseData.getLocal("dDocName");

	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		InputStream is = null;
		try {
			String hash = null;
			String encoding = null;

			if (params != null && params.length > 0) {
				hash = params[0];
				encoding = params[1];
			} else {
				InputStream tmpIs = null;
				try {
					tmpIs = new FileInputStream(file);
					hash = calcImpronta(tmpIs, "base64");
					encoding = "base64";
				} finally {
					if (tmpIs != null)
						try {
							tmpIs.close();
						} catch (Exception e) {
						}
				}
			}

			logger.debug("Invocato metodo insert");
			is = new FileInputStream(file);
			IdcContext ctx = INSTANCE.getIdcContext();
			logger.debug("Recuperato il contesto ");

			String id = executeCheckIn(ctx, is, file.length(), UUID.randomUUID().toString());

			if (hash != null && encoding != null) // && algoritmo!=null)
			{
				InputStream downloadFile = retrieve(id);
				String hash_download = calcImpronta(downloadFile, encoding);
				if (!hash.equals(hash_download)) {
					logger.error("L'hash del file scaricato non corrisponde a quello del file inviato");
					throw new StorageException("L'hash del file scaricato non corrisponde a quello del file inviato");
				}
			}
			return id;

		} catch (IdcClientException e) {
			logger.error(e.getMessage(), e);
			throw new StorageException(e);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new StorageException(e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception e) {
				}
		}
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		String relativePath = File.separator + StorageUtil.getUniqueFileName();
		this.getTemporaryRepository().storeInputStreamToRelativeFile(inputStream, relativePath);
		try {
			File f = this.getTemporaryRepository().retrieveFileFromRelativePath(relativePath);
			return insert(f);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new StorageException(e);
		}
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		// non necessario
		return null;
	}

	@Override
	public void delete(String id) throws StorageException {
		try {
			logger.debug("Invocato metodo delete");
			IdcContext ctx = INSTANCE.getIdcContext();
			// DataBinder docInfoResult = retrieveDocInfo(ctx, model);
			// String dID=docInfoResult.getLocal("dID");
			IntradocClient idcClient = INSTANCE.getIntradocClient();
			DataBinder binder = idcClient.createBinder();
			binder.putLocal("IdcService", "DELETE_DOC");
			// binder.putLocal("dID", id);
			binder.putLocal("dDocName", id);
			// send the initial request
			ServiceResponse response = idcClient.sendRequest(ctx, binder);
			DataBinder responseBinder = response.getResponseAsBinder();
			logger.debug("ESEGUITO DELETE:" + responseBinder.getLocal("StatusMessage"));
		} catch (IdcClientException e) {
			logger.error("errore durante la fase di delete", e);
			throw new StorageException(e.getMessage());
		}
	}

	private DataBinder retrieveDocInfo(IdcContext ctx, String id) throws IdcClientException {
		IntradocClient idcClient = INSTANCE.getIntradocClient();
		DataBinder binder = idcClient.createBinder();
		binder.putLocal("IdcService", "DOC_INFO_BY_NAME");
		binder.putLocal("dDocName", id);
		// send the initial request
		ServiceResponse response = idcClient.sendRequest(ctx, binder);
		DataBinder responseBinder = response.getResponseAsBinder();
		return responseBinder;
	}

	@Override
	public InputStream retrieve(String id) throws StorageException {
		try {
			logger.debug("Invocato metodo retrieve");
			logger.info("sono pre IdcContext---->>>>>>>>>");
			IdcContext ctx = INSTANCE.getIdcContext();
			DataBinder docInfoResult = retrieveDocInfo(ctx, id);
			String dID = docInfoResult.getLocal("dID");
			IntradocClient idcClient = INSTANCE.getIntradocClient();
			DataBinder dataBinder = idcClient.createBinder();

			// retrieve the file, check the file size
			dataBinder.putLocal("IdcService", "GET_FILE");
			dataBinder.putLocal("dID", dID);
			ServiceResponse response = idcClient.sendRequest(ctx, dataBinder);
			int reportedSize = Integer.parseInt(response.getHeader("Content-Length"));
			logger.info("size ---->>>>>>>>>" + reportedSize);

			// The file is streamed back to us in the response
			InputStream stream = response.getResponseStream();
			return stream;
		} catch (Exception e) {
			logger.error("errore nel download del file", e);
			throw new StorageException(e.getMessage());
		}
	}

	private String calcImpronta(InputStream input, String encoding) throws StorageException {
		try {
			if (encoding.equalsIgnoreCase("hex")) {
				return DigestUtils.sha256Hex(input);
			} else if (encoding.equalsIgnoreCase("base64")) {
				byte[] hash = DigestUtils.sha256(input);
				return Base64.encodeBase64String(hash);
			}
			return null;
		} catch (Exception e) {
			logger.error("errore nel calcolo dell'hash dell'input stream appena scaricato", e);
			throw new StorageException(e.getMessage());
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("errore nella chiusura dell'input stream su cui si � calcolato l'hash", e);
				}
		}
	}

	@Override
	public File retrieveFile(String id) throws StorageException {
		try {
			getTemporaryRepository().storeInputStreamToRelativeFile(retrieve(id), id);
			return (id != null) ? this.getTemporaryRepository().retrieveFileFromRelativePath(id) : null;
		} catch (FileNotFoundException e) {
			logger.error("errore nel download del file", e);
			throw new StorageException(e.getMessage());
		}
	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		// non necessario
		return null;
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		// non necessario
		return null;
	}

	@Override
	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;

	}

	@Override
	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;

	}

	public TemporaryRepository getTemporaryRepository() {
		return temporaryRepository;
	}

	public void setTemporaryRepository(TemporaryRepository temporaryRepository) {
		this.temporaryRepository = temporaryRepository;
	}
}