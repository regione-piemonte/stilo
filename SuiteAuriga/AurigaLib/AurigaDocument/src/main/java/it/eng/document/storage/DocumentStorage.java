/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import it.eng.auriga.module.config.ModuleConfig;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

public class DocumentStorage {

	private static Logger logger = Logger.getLogger(DocumentStorage.class);

	public static String getTipoStorage(BigDecimal idDominio) throws StorageException {
		logger.debug("istanzio lo storage per l'idDominio: " + idDominio);
		StorageService lService = getStorage(idDominio != null ? idDominio.toString() : null);
		logger.debug("servizio di storage(config): " + lService.getConfigurazioniStorage() + " di tipo: " + lService.getTipoStorage());
		return lService.getTipoStorage();
	}

	public static String getConfigurazioniStorage(BigDecimal idDominio) throws StorageException {
		StorageService lService = getStorage(idDominio != null ? idDominio.toString() : null);
		return lService.getConfigurazioniStorage();
	}

	public static String store(File lFile, BigDecimal idDominio, String... params) throws StorageException {
		StorageService lService = getStorage(idDominio != null ? idDominio.toString() : null);
		return lService.store(lFile, params);
	}

	public static String storeInput(InputStream lFile, BigDecimal idDominio, String... params) throws StorageException {
		StorageService lService = getStorage(idDominio != null ? idDominio.toString() : null);
		return lService.storeStream(lFile, params);
	}

	public static String storeForEnte(File lFile, String... params) throws StorageException {
		StorageService lService = getStorageForEnte();
		return lService.store(lFile, params);
	}

	public static String storeInputForEnte(InputStream lFile, String... params) throws StorageException {
		StorageService lService = getStorageForEnte();
		return lService.storeStream(lFile, params);
	}

	public static String storeDefault(File lFile, String... params) throws StorageException {
		StorageService lService = getStorageDefault();
		return lService.store(lFile, params);
	}

	public static String storeInputDefault(InputStream lFile, String... params) throws StorageException {
		StorageService lService = getStorageDefault();
		return lService.storeStream(lFile, params);
	}

	public static File extract(String uri, BigDecimal idDominio) throws StorageException {
		StorageService lStorage = getStorage(idDominio != null ? idDominio.toString() : null);
		return lStorage.extractFile(uri);
	}
	
	public static void delete(String uri, BigDecimal idDominio) throws StorageException {
		StorageService lStorage = getStorage(idDominio != null ? idDominio.toString() : null);
		lStorage.delete(uri);
	}
	
	public static File getRealFile(String uri, BigDecimal idDominio) throws StorageException {
		StorageService lStorage = getStorage(idDominio != null ? idDominio.toString() : null);
		return lStorage.getRealFile(uri);
	}
	
	public static String getRealPathFile(String uri, BigDecimal idDominio) throws StorageException {
		StorageService lStorage = getStorage(idDominio != null ? idDominio.toString() : null);
		return lStorage.getRealFile(uri).toURI().toString();
	}

	private static StorageService getStorageForEnte() {
		final String enteDiConnessione = SubjectUtil.subject.get().getIdDominio();
		return getStorage(enteDiConnessione);
	}

	private static StorageService getStorage(final String storage) {
		StorageService storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
				logger.debug("Recuperato module config");
				logger.debug("Nome utilizzatore " + storage);
				if (mc != null && mc.getStorages().size() > 0) {
					for (String lString : mc.getStorages().keySet()) {
						logger.debug("Storage moduli " + lString + " valori " + mc.getStorages().get(lString));
					}
					if (mc.getStorages().containsKey(storage)) {
						logger.debug("Id Storage vale " + mc.getStorages().get(storage));
						return mc.getStorages().get(storage);
					} else {
						logger.debug("Id Storage non trovato. Prendo quello di default che vale " + mc.getStorages().get("DEFAULT"));
						return mc.getStorages().get("DEFAULT");
					}
				} else {
					logger.error("L'identificativo del DB di storage non e' correttamente configurato, "
							+ "controllare il file di configurazione del modulo.");
					return null;
				}
			}
		});
		return storageService;
	}

	private static StorageService getStorageDefault() {
		StorageService storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {
			public String getUtilizzatoreStorageId() {
				ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
				logger.debug("Recuperato module config");
				if (mc != null && mc.getStorages().size() > 0) {
					logger.debug("Id Storage default vale " + mc.getStorages().get("DEFAULT"));
					return mc.getStorages().get("DEFAULT");
				} else {
					logger.error("L'identificativo del DB di storage non e' correttamente configurato, "
							+ "controllare il file di configurazione del modulo.");
					return null;
				}
			}
		});
		return storageService;
	}
	
	

}
