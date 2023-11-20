/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@XmlRootElement
public class AllegatiInvioMailXmlBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7156469145328708315L;

	/**
	 * Nome file
	 */
	@NumeroColonna(numero = "1")
	private String nomeFile;

	/**
	 * Mimetype
	 */
	@NumeroColonna(numero = "2")
	private String mimetype;

	/**
	 * La colonna 3 non è usata attualmente
	 */

	/**
	 * (valori 1/0) Se 1 indica se firmato digitalmente
	 */
	@NumeroColonna(numero = "4")
	private Integer flgFirmato;

	/**
	 * Dimensione in bytes
	 */
	@NumeroColonna(numero = "5")
	private BigDecimal dimensione;

	/**
	 * Impronta
	 */
	@NumeroColonna(numero = "6")
	private String impronta;

	/**
	 * Algoritmo calcolo impronta
	 */
	@NumeroColonna(numero = "7")
	private String algoritmo;

	/**
	 * Encoding impronta (hex o base64)
	 */
	@NumeroColonna(numero = "8")
	private String encoding;

	/**
	 * @return the nomeFile
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @param nomeFile
	 *            the nomeFile to set
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	/**
	 * @return the mimetype
	 */
	public String getMimetype() {
		return mimetype;
	}

	/**
	 * @param mimetype
	 *            the mimetype to set
	 */
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	/**
	 * @return the flgFirmato
	 */
	public Integer getFlgFirmato() {
		return flgFirmato;
	}

	/**
	 * @param flgFirmato
	 *            the flgFirmato to set
	 */
	public void setFlgFirmato(Integer flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	/**
	 * @return the dimensione
	 */
	public BigDecimal getDimensione() {
		return dimensione;
	}

	/**
	 * @param dimensione
	 *            the dimensione to set
	 */
	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	/**
	 * @return the impronta
	 */
	public String getImpronta() {
		return impronta;
	}

	/**
	 * @param impronta
	 *            the impronta to set
	 */
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	/**
	 * @return the algoritmo
	 */
	public String getAlgoritmo() {
		return algoritmo;
	}

	/**
	 * @param algoritmo
	 *            the algoritmo to set
	 */
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
