/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.mail.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.filepool.fplibrary.FPClip;
import com.filepool.fplibrary.FPLibraryConstants;
import com.filepool.fplibrary.FPLibraryException;
import com.filepool.fplibrary.FPPool;
import com.filepool.fplibrary.FPTag;

import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.TemporaryRepository;
import it.eng.utility.storageutil.annotations.TipoStorage;
import it.eng.utility.storageutil.enums.StorageType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.TemporaryRepositoryImpl;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.storageutil.util.XMLUtil;

@TipoStorage(tipo = StorageType.CENTERA)
public class CenteraStorage implements Storage {

	private static Logger logger = Logger.getLogger(CenteraStorage.class);

	protected FPPool pool;

	// Repository temporano da cui recuperare i files
	protected TemporaryRepository temporaryRepository;

	private String idStorage;
	private String utilizzatoreStorage;

	// prefissi dei tag utilizzati
	public static final String FILE_PREFIX_TAG = "FILE_";
	// public static final String MAINDOCUMENT_PREFIX_TAG = "MAINDOCUMENT_";
	// public static final String ATTACHMENT_PREFIX_TAG = "ATTACHMENT_";
	// public static final String REFERENCE_PREFIX_TAG = "REFERENCE_";
	// public static final String RELATION_PREFIX_TAG = "RELATION_";

	// nome degli attributi
	public static final String DIGEST_ATTRIBUTE = "DIGEST";
	public static final String DIGEST_TYPE_ATTRIBUTE = "DIGEST_TYPE";
	public static final String URI_ATTRIBUTE = "URI";

	private static final long DIMENSION_MAX = 300000000;

	// public static final String INSTANCECLASS_ATTRIBUTE = "INSTANCE_CLASS";
	// public static final String DOC_TYPE_ATTRIBUTE = "DOC_TYPE";
	// public static final String RELATION_TYPE_ATTRIBUTE = "RELATION_TYPE";
	// public static final String REFERENCE_TYPE_ATTRIBUTE = "REFERENCE_TYPE";
	// public static final String XML_OUTLINE_ATTRIBUTE = "XML_OUTLINE";
	// public static final String ATTACHMENT_NROPROGR = "ATTACHMENT_NROPROGR";

	public CenteraStorage() {

	}

	@Override
	public void configure(String xmlConfig) throws StorageException {
		try {
			CenteraStorageConfig config = (CenteraStorageConfig) XMLUtil.newInstance().deserialize(xmlConfig,
					CenteraStorageConfig.class);
			;
			if (config != null) {
				this.pool = new FPPool(config.getCenteraPoolAddresses());
				this.temporaryRepository = new TemporaryRepositoryImpl(config.getTempRepositoryBasePath());
			}
		} catch (FPLibraryException e) {
			String errore = getFPLibraryExceptionInfo(e);
			logger.warn(errore);
			throw new StorageException(errore);
		} catch (Exception e) {
			logger.warn("Eccezione configurazione CenteraStorage", e);
			throw new StorageException(e);
		}
	}

	@Override
	public String insert(File file, String... params) throws StorageException {
		String relativePath = null;
		try {

			relativePath = File.separator + StorageUtil.getUniqueFileName();
			this.getTemporaryRepository().storeInputStreamToRelativeFile(new FileInputStream(file), relativePath);
			FPClip clip = new FPClip(pool);

			FPTag rootTag = clip.getTopTag();
			// String escapedRelativePath =
			// CenteraPathEscape.escapeCenteraTag(relativePath);
			FPTag documentTag = new FPTag(rootTag, FILE_PREFIX_TAG);
			rootTag.Close();
			InputStream is = temporaryRepository.retrieveInputStreamFromRelativePath(relativePath);

			// Popolo gli attributi
			// documentTag.setAttribute(DIGEST_ATTRIBUTE, "SHA-1");
			// documentTag.setAttribute(DIGEST_TYPE_ATTRIBUTE,
			// DigestUtils.shaHex(is));
			// documentTag.setAttribute(URI_ATTRIBUTE, relativePath);
			documentTag.setAttribute("filename", relativePath);
			documentTag.BlobWrite(is);
			String clipId = clip.Write();
			is.close();
			documentTag.Close();
			clip.Close();
			return clipId;
		} catch (FPLibraryException e) {
			String errore =  getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		}catch (Throwable e) {
			logger.error(e.getMessage());
			throw new StorageException(e.getMessage());
		} finally {
			try {
				temporaryRepository.deleteRelativeFile(relativePath);
			} catch (Throwable e1) {
			}
		}
	}

	@Override
	public void delete(String id) throws StorageException {
		try {
			// elimino eventuali File.separator iniziali per compatibilit� con
			// il filesystemStorage
			String idNoSlash = id;
			while (idNoSlash.startsWith(File.separator)) {
				idNoSlash = idNoSlash.substring(1);
			}
			FPClip.Delete(pool, id);
		} catch (FPLibraryException e) {
			String errore =  getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		}catch (Throwable e) {
			logger.error(e.getMessage());
			throw new StorageException(e.getMessage());
		}
	}

	public InputStream retrieve(String id) throws StorageException {
		FPClip clip = null;
		FPTag rootTag = null;
		InputStream inputstream = null;
		try {
			// elimino eventuali File.separator iniziali per compatibilit� con
			// il filesystemStorage
			String idNoSlash = id;
			while (idNoSlash.startsWith(File.separator)) {
				idNoSlash = idNoSlash.substring(1);
			}

			clip = new FPClip(pool, id, FPLibraryConstants.FP_OPEN_ASTREE);

			rootTag = clip.getTopTag().getFirstChild();

			String relativePath = null;
			if (rootTag != null) {
				inputstream = retrieveInpuStream(rootTag);
				relativePath = rootTag.getStringAttribute("filename");
				this.getTemporaryRepository().storeInputStreamToRelativeFile(inputstream, relativePath);
			}

//			rootTag.Close();
			// clip.Close();
			return (relativePath != null)
					? this.getTemporaryRepository().retrieveInputStreamFromRelativePath(relativePath) : null;
		} catch (FPLibraryException e) {
			String errore = getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			throw new StorageException(e.getMessage());
		} finally{
			try {
				if ( rootTag != null ) rootTag.Close();
				if ( clip != null ) clip.Close();
			} catch (Throwable e2) {
			}
		}
	}

	public TemporaryRepository getTemporaryRepository() {
		return temporaryRepository;
	}

	public void setTemporaryRepository(TemporaryRepository temporaryRepository) {
		this.temporaryRepository = temporaryRepository;
	}

	@Override
	public String insertInput(InputStream inputStream, String... params) throws StorageException {
		String relativePath = null;
		try {
			relativePath = File.separator + StorageUtil.getUniqueFileName();
			this.getTemporaryRepository().storeInputStreamToRelativeFile(inputStream, relativePath);
			FPClip clip = new FPClip(pool);
			FPTag rootTag = clip.getTopTag();
			// String escapedRelativePath =
			// CenteraPathEscape.escapeCenteraTag(relativePath);
			FPTag documentTag = new FPTag(rootTag, FILE_PREFIX_TAG);
			InputStream is = temporaryRepository.retrieveInputStreamFromRelativePath(relativePath);
			// Popolo gli attributi
			// documentTag.setAttribute(DIGEST_ATTRIBUTE, "SHA-1");
			// documentTag.setAttribute(DIGEST_TYPE_ATTRIBUTE,
			// DigestUtils.shaHex(is));
			// documentTag.setAttribute(URI_ATTRIBUTE, relativePath);
			documentTag.setAttribute("filename", relativePath);
			documentTag.BlobWrite(is);
			is.close();
			documentTag.Close();
			rootTag.Close();
			String clipId = clip.Write();
			clip.Close();
			return clipId;
		} catch (FPLibraryException e) {
			String errore = getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			throw new StorageException(e.getMessage());
		} finally {
			try {
				temporaryRepository.deleteRelativeFile(relativePath);
			} catch (Throwable e1) {
			}
		}
	}

	@Override
	public String printMessage(Message lMessage) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writeFileItem(FileItem fileItem) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	public File retrieveFile(String id) throws StorageException {
		FPClip clip = null;
		FPTag rootTag = null;
		InputStream inputstream = null;
		try {
			// elimino eventuali File.separator iniziali per compatibilit� con
			// il filesystemStorage
			// String idNoSlash = id;
			// while (idNoSlash.startsWith(File.separator)) {
			// idNoSlash = idNoSlash.substring(1);
			// }

			clip = new FPClip(pool, id, FPLibraryConstants.FP_OPEN_ASTREE);

			rootTag = clip.getTopTag().getFirstChild();
			String relativePath = null;
			if (rootTag != null) {
				logger.debug("Dimensione BLOB ["+ rootTag.getBlobSize() +"] con filename["+rootTag.getStringAttribute("filename")+"]");
				inputstream = retrieveInpuStream(rootTag);
				relativePath = rootTag.getStringAttribute("filename");
				this.getTemporaryRepository().storeInputStreamToRelativeFile(inputstream, relativePath);

			}

			return (relativePath != null) ? this.getTemporaryRepository().retrieveFileFromRelativePath(relativePath)
					: null;
		}catch (FPLibraryException e) {
			String errore = getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new StorageException(e);
		}finally{
			try {
				if ( rootTag != null ) rootTag.Close();
				if ( clip != null ) clip.Close();
			} catch (Throwable e2) {
			}
		}
	}

	private static String getFPLibraryExceptionInfo(FPLibraryException exception) {
		StringBuffer ret = new StringBuffer();
		ret.append("{");
		ret.append("ErrorCode;").append(exception.getErrorCode());
		ret.append(";");
		ret.append("Message:").append(exception.getMessage());
		ret.append(";");
		ret.append("ErrorString:").append(exception.getErrorString());
		ret.append(";");
		ret.append("system Error Code:").append(exception.getSystemErrorCode());
		ret.append("}");
		return ret.toString();
	}

	@Override
	public File retrievRealFile(String id) throws StorageException {
		return retrieveFile(id);
	}

	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}

	public void setUtilizzatoreStorage(String idUtilizzatore) {
		this.utilizzatoreStorage = idUtilizzatore;
	}
	
	private InputStream retrieveInpuStream(FPTag rootTag) throws StorageException {
		InputStream ret = null;
		try {
			if (rootTag != null) {
				if (rootTag.getBlobSize() > DIMENSION_MAX) {
					logger.warn("Retrieve large file ["+ rootTag.getBlobSize() +"]");
					ret = retrieveLargeFile(rootTag);
				} else {
					ret = retrieveNormalFile(rootTag);
				}
			}else{
				throw new Exception("Root tag is null");
			}
		} catch (Exception e) {
			throw new StorageException(e);
		} finally {
		}	
		return ret;
	}

	public InputStream retrieveLargeFile(FPTag rootTag) throws StorageException {
		try {
			logger.debug("Dimensione BLOB [" + rootTag.getBlobSize() + "] con filename["
					+ rootTag.getStringAttribute("filename") + "]");

			return BlobReadPartial.retrieveIS(rootTag, 5);
		} catch (FPLibraryException e) {
			String errore = getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new StorageException(e);
		} finally {
		}
	}

	public InputStream retrieveNormalFile(FPTag rootTag) throws StorageException {

		try {
			ByteArrayOutputStream outstream = null;
			logger.debug("Dimensione BLOB [" + rootTag.getBlobSize() + "] con filename["
					+ rootTag.getStringAttribute("filename") + "]");
			outstream = new ByteArrayOutputStream();

			rootTag.BlobRead(outstream);
			return new ByteArrayInputStream(outstream.toByteArray());

		} catch (FPLibraryException e) {
			String errore = getFPLibraryExceptionInfo(e);
			logger.error(errore);
			throw new StorageException(errore);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new StorageException(e);
		}
	}
	
}
