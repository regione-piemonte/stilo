/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class VisualizzaVersioniFileBean {

	private Integer nr;
	private String nome;
	private String iconaFirmata;
	private String iconaAcquisitaDaScanner;
	private String creataDa;
	private Date creataIl;
	private String ultimoAggEffDa;
	private Date dtUltimoAggEff;
	private Integer dimensione;
	private String flgConvertibilePdf;
	private String mimetype;
	private String uriFile;
	private String impronta;
	private String algoritmo;
	private String encoding;

	/**
	 * @return the nr
	 */
	public Integer getNr() {
		return nr;
	}

	/**
	 * @param nr
	 *            the nr to set
	 */
	public void setNr(Integer nr) {
		this.nr = nr;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the creataDa
	 */
	public String getCreataDa() {
		return creataDa;
	}

	/**
	 * @param creataDa
	 *            the creataDa to set
	 */
	public void setCreataDa(String creataDa) {
		this.creataDa = creataDa;
	}

	/**
	 * @return the creataIl
	 */
	public Date getCreataIl() {
		return creataIl;
	}

	/**
	 * @param creataIl
	 *            the creataIl to set
	 */
	public void setCreataIl(Date creataIl) {
		this.creataIl = creataIl;
	}

	/**
	 * @return the ultimoAggEffDa
	 */
	public String getUltimoAggEffDa() {
		return ultimoAggEffDa;
	}

	/**
	 * @param ultimoAggEffDa
	 *            the ultimoAggEffDa to set
	 */
	public void setUltimoAggEffDa(String ultimoAggEffDa) {
		this.ultimoAggEffDa = ultimoAggEffDa;
	}

	/**
	 * @return the dtUltimoAggEff
	 */
	public Date getDtUltimoAggEff() {
		return dtUltimoAggEff;
	}

	/**
	 * @param dtUltimoAggEff
	 *            the dtUltimoAggEff to set
	 */
	public void setDtUltimoAggEff(Date dtUltimoAggEff) {
		this.dtUltimoAggEff = dtUltimoAggEff;
	}

	/**
	 * @return the dimensione
	 */
	public Integer getDimensione() {
		return dimensione;
	}

	/**
	 * @param dimensione
	 *            the dimensione to set
	 */
	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}

	public String getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}

	public void setFlgConvertibilePdf(String flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	/**
	 * @return the uriFile
	 */
	public String getUriFile() {
		return uriFile;
	}

	/**
	 * @param uriFile
	 *            the uriFile to set
	 */
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getIconaAcquisitaDaScanner() {
		return iconaAcquisitaDaScanner;
	}

	public void setIconaAcquisitaDaScanner(String iconaAcquisitaDaScanner) {
		this.iconaAcquisitaDaScanner = iconaAcquisitaDaScanner;
	}

	public String getIconaFirmata() {
		return iconaFirmata;
	}

	public void setIconaFirmata(String iconaFirmata) {
		this.iconaFirmata = iconaFirmata;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
