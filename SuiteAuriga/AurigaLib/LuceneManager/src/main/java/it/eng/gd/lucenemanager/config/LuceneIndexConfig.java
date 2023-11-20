/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.store.FSLockFactory;

import it.eng.gd.lucenemanager.bean.SearchCategory;

/**
 * Configurazione lucene
 * 
 * @author jravagnan
 * 
 */
@Entity
public class LuceneIndexConfig {

	public static final String CONFIG_NAME = "luceneindex";

	private Logger log = Logger.getLogger(LuceneIndexConfig.class);

	// path di default dell'indice
	private File path;

	// massimo numero di documenti per l'overflow
	private Integer maxdocuments = 100;

	// percorsi specifici per varie categorie
	private Map<String, String> categoryPath;

	// eventuale lockFactory da utilizzare per l'indice
	private FSLockFactory fsFactory;

	private String writeTimeout = "5000";

	public Integer getMaxdocuments() {
		return maxdocuments;
	}

	public void setMaxdocuments(Integer maxdocuments) {
		this.maxdocuments = maxdocuments;
	}

	public FSLockFactory getFsFactory() {
		return fsFactory;
	}

	public void setFsFactory(FSLockFactory fsFactory) {
		this.fsFactory = fsFactory;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public Map<String, String> getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(Map<String, String> categoryPath) {
		this.categoryPath = categoryPath;
	}

	/**
	 * restituisce il path corretto
	 * 
	 * @param category
	 * @return
	 */
	public File retrieveCorrectPath(SearchCategory category, String idDominio, String dbName) {
		File pathFile = null;
		if (category != null) {
			String path = getCategoryPath().get(category.getValore());
			if ("REP_DOC".equals(category.getValore()) && StringUtils.isNotBlank(dbName) && StringUtils.isNotBlank(idDominio)) {
				if (!path.endsWith(File.separator))
					path += File.separator;
				path += dbName + "_" + idDominio;
			}
			pathFile = new File(path);
		} else {
			pathFile = getPath();
		}
		log.debug("PATH IN CUI VADO A CERCARE GLI INDICI: " + pathFile.getAbsolutePath());
		return pathFile;
	}

	public String getWriteTimeout() {
		return writeTimeout;
	}

	public void setWriteTimeout(String writeTimeout) {
		this.writeTimeout = writeTimeout;
	}

}