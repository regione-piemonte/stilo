/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author cristiano
 *
 */
public class TrovaCiviciBean {

	/**
	 * 1: ID del toponimo (PK interna)
	 */
	private String idCivico;
	/**
	 * 2: Civico completo (nro + eventuale esponente e/o barrato)
	 */
	private String civico;
	/**
	 * 3: Nro civico (solo la parte numerica a meno di esponente e/o barrato)
	 */
	private Integer nrCivico;
	/**
	 * 4: Esponente/barrato del civico
	 */
	private String esponenteBarrato;
	/**
	 * 7: CAP
	 */
	private String cap;
	/**
	 * 8: ZONA
	 */
	private String zona;

	/**
	 * @return the idCivico
	 */
	public String getIdCivico() {
		return idCivico;
	}

	/**
	 * @param idCivico
	 *            the idCivico to set
	 */
	public void setIdCivico(String idCivico) {
		this.idCivico = idCivico;
	}

	/**
	 * @return the civico
	 */
	public String getCivico() {
		return civico;
	}

	/**
	 * @param civico
	 *            the civico to set
	 */
	public void setCivico(String civico) {
		this.civico = civico;
	}

	/**
	 * @return the nrCivico
	 */
	public Integer getNrCivico() {
		return nrCivico;
	}

	/**
	 * @param nrCivico
	 *            the nrCivico to set
	 */
	public void setNrCivico(Integer nrCivico) {
		this.nrCivico = nrCivico;
	}

	/**
	 * @return the esponenteBarrato
	 */
	public String getEsponenteBarrato() {
		return esponenteBarrato;
	}

	/**
	 * @param esponenteBarrato
	 *            the esponenteBarrato to set
	 */
	public void setEsponenteBarrato(String esponenteBarrato) {
		this.esponenteBarrato = esponenteBarrato;
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

}
