/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author cristiano
 *
 */

public class TrovaVieBean {

	/**
	 * 1: ID del toponimo (PK interna)
	 */
	private String idToponimo;
	/**
	 * 2: Nome del toponimo (compreso tipo)
	 */
	private String descrNomeToponimo;
	/**
	 * 3: Tipo toponimo (via, piazza ecc)
	 */
	private String tipoToponimo;
	/**
	 * 6: Codice identificativo del toponimo nel sistema esterno di gestione del viario
	 */
	private String codiceViarioToponimo;
	/**
	 * 7: CAP
	 */
	private String capVie;
	/**
	 * 8: ZONA
	 */
	private String zonaVie;

	/**
	 * @return the idToponimo
	 */
	public String getIdToponimo() {
		return idToponimo;
	}

	/**
	 * @param idToponimo
	 *            the idToponimo to set
	 */
	public void setIdToponimo(String idToponimo) {
		this.idToponimo = idToponimo;
	}

	/**
	 * @return the descrNomeToponimo
	 */
	public String getDescrNomeToponimo() {
		return descrNomeToponimo;
	}

	/**
	 * @param descrNomeToponimo
	 *            the descrNomeToponimo to set
	 */
	public void setDescrNomeToponimo(String descrNomeToponimo) {
		this.descrNomeToponimo = descrNomeToponimo;
	}

	/**
	 * @return the tipoToponimo
	 */
	public String getTipoToponimo() {
		return tipoToponimo;
	}

	/**
	 * @param tipoToponimo
	 *            the tipoToponimo to set
	 */
	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}

	/**
	 * @return the codiceViarioToponimo
	 */
	public String getCodiceViarioToponimo() {
		return codiceViarioToponimo;
	}

	/**
	 * @param codiceViarioToponimo
	 *            the codiceViarioToponimo to set
	 */
	public void setCodiceViarioToponimo(String codiceViarioToponimo) {
		this.codiceViarioToponimo = codiceViarioToponimo;
	}

	/**
	 * @return the capVie
	 */
	public String getCapVie() {
		return capVie;
	}

	/**
	 * @param capVie
	 *            the capVie to set
	 */
	public void setCapVie(String capVie) {
		this.capVie = capVie;
	}

	/**
	 * @return the zonaVie
	 */
	public String getZonaVie() {
		return zonaVie;
	}

	/**
	 * @param zonaVie
	 *            the zonaVie to set
	 */
	public void setZonaVie(String zonaVie) {
		this.zonaVie = zonaVie;
	}

}
