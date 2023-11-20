/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.gd.lucenemanager.bean.SearchCategory;

import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Configurazione lucene
 * 
 * @author jravagnan
 * 
 */
@Entity
public class LuceneIndexDbConfig {

	public static final String CONFIG_NAME = "luceneIndexDbConfigurator";

	private Logger log = Logger.getLogger(LuceneIndexDbConfig.class);

	// nomeTabellaDefault
	private String nomeTabella;

	// percorsi specifici per varie categorie
	private Map<String, String> categoryPath;

	private String writeTimeout = "5000";

	public Map<String, String> getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(Map<String, String> categoryPath) {
		this.categoryPath = categoryPath;
	}

	/**
	 * restituisce il nomeTabellaCorretto
	 * 
	 * @param category
	 * @return
	 */
	public String retrieveCorrectTable(SearchCategory category, String idDominio, String dbName) {
		String nomeTabella = null;
		if (category != null) {
			String nome_tabella = getCategoryPath().get(category.getValore());
			if ("REP_DOC".equals(category.getValore()) && StringUtils.isNotBlank(dbName) && StringUtils.isNotBlank(idDominio)) {
				nome_tabella += "_";
				nome_tabella += idDominio;
			}
			nomeTabella = nome_tabella;
		} else {
			nomeTabella = getNomeTabella();
		}
		log.debug("TABELLA IN CUI VADO A CERCARE GLI INDICI: " + nomeTabella);
		return nomeTabella;
	}

	public String getWriteTimeout() {
		return writeTimeout;
	}

	public void setWriteTimeout(String writeTimeout) {
		this.writeTimeout = writeTimeout;
	}

	public String getNomeTabella() {
		return nomeTabella;
	}

	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

}