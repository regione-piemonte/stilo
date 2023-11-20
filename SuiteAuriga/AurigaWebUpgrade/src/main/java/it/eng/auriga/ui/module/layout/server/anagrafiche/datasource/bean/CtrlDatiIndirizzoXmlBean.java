/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author cristiano
 *
 */

public class CtrlDatiIndirizzoXmlBean {

	// 1: Codice via
	@NumeroColonna(numero = "1")
	private String codiceVia;

	// 2: Descrizione via (esattamente come a video)
	@NumeroColonna(numero = "2")
	private String descrizioneVia;

	// 3: N. civico (solo la prima parte, non seconda parte)
	@NumeroColonna(numero = "3")
	private String nrCivico;

	// 7: CAP
	@NumeroColonna(numero = "7")
	private String cap;

	// 9: Codice ISTAT del comune riferimento
	@NumeroColonna(numero = "9")
	private String codiceComune;

	// 10: Nome del comune di riferimento
	@NumeroColonna(numero = "10")
	private String nomeComune;

	// 11: Codice ISTAT ITALIA
	@NumeroColonna(numero = "11")
	private String codIstat;

	// 12: ITALIA
	@NumeroColonna(numero = "12")
	private String stato;

	// 13: Zona
	@NumeroColonna(numero = "13")
	private String zona;

	// 16: Seconda parte del civico
	@NumeroColonna(numero = "16")
	private String appendici;

	/**
	 * @return the codiceVia
	 */
	public String getCodiceVia() {
		return codiceVia;
	}

	/**
	 * @param codiceVia
	 *            the codiceVia to set
	 */
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	/**
	 * @return the descrizioneVia
	 */
	public String getDescrizioneVia() {
		return descrizioneVia;
	}

	/**
	 * @param descrizioneVia
	 *            the descrizioneVia to set
	 */
	public void setDescrizioneVia(String descrizioneVia) {
		this.descrizioneVia = descrizioneVia;
	}

	/**
	 * @return the nrCivico
	 */
	public String getNrCivico() {
		return nrCivico;
	}

	/**
	 * @param nrCivico
	 *            the nrCivico to set
	 */
	public void setNrCivico(String nrCivico) {
		this.nrCivico = nrCivico;
	}

	/**
	 * @return the cap
	 */
	public String getCap() {
		return cap;
	}

	/**
	 * @param cap
	 *            the cap to set
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}

	/**
	 * @return the codiceComune
	 */
	public String getCodiceComune() {
		return codiceComune;
	}

	/**
	 * @param codiceComune
	 *            the codiceComune to set
	 */
	public void setCodiceComune(String codiceComune) {
		this.codiceComune = codiceComune;
	}

	/**
	 * @return the nomeComune
	 */
	public String getNomeComune() {
		return nomeComune;
	}

	/**
	 * @param nomeComune
	 *            the nomeComune to set
	 */
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}

	/**
	 * @return the codIstat
	 */
	public String getCodIstat() {
		return codIstat;
	}

	/**
	 * @param codIstat
	 *            the codIstat to set
	 */
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	/**
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato
	 *            the stato to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * @param zona
	 *            the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * @return the appendici
	 */
	public String getAppendici() {
		return appendici;
	}

	/**
	 * @param appendici
	 *            the appendici to set
	 */
	public void setAppendici(String appendici) {
		this.appendici = appendici;
	}
}
