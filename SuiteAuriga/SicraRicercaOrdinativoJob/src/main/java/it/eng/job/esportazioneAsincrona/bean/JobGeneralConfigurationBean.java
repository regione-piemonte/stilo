/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Locale;

/**
 * Dati generali di configurazione, condivisi da tutte le configurazione
 * specifiche
 * 
 * @author massimo malvestio
 *
 */
public class JobGeneralConfigurationBean {

	private String schema;

	private Integer tipoDominio;

	private Locale locale;

	private String dateFormat;

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the tipoDominio
	 */
	public Integer getTipoDominio() {
		return tipoDominio;
	}

	/**
	 * @param tipoDominio
	 *            the tipoDominio to set
	 */
	public void setTipoDominio(Integer tipoDominio) {
		this.tipoDominio = tipoDominio;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}
