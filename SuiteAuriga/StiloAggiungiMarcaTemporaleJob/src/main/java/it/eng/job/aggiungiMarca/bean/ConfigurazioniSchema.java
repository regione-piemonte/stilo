/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;

import it.eng.job.aggiungiMarca.constants.PostManageActionEnum;
import it.eng.utility.storageutil.StorageService;



/**
 * Configurazioni schema 
 * 
 *
 */


public class ConfigurazioniSchema {

	private String locale;
	private String token;
	private String idDominio;
	private String schema;
	
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	

	
	
}
