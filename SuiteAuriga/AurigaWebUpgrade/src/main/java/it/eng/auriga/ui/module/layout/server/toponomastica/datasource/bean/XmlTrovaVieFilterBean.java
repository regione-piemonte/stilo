/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author cristiano
 *
 */

public class XmlTrovaVieFilterBean {

	/**
	 * Filtro di ricerca CERCA
	 */
	@XmlVariabile(nome = "DesToponimo", tipo = TipoVariabile.SEMPLICE)
	private String desToponimo;
	@XmlVariabile(nome = "OperToponimo", tipo = TipoVariabile.SEMPLICE)
	private String operToponimo;
	@XmlVariabile(nome = "CodIstatComune", tipo = TipoVariabile.SEMPLICE)
	private String codIstatComune;

	
	/**
	 * @return the codIstatComune
	 */
	public String getCodIstatComune() {
		return codIstatComune;
	}

	
	/**
	 * @param codIstatComune the codIstatComune to set
	 */
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	/**
	 * @return the desToponimo
	 */
	public String getDesToponimo() {
		return desToponimo;
	}

	/**
	 * @param desToponimo
	 *            the desToponimo to set
	 */
	public void setDesToponimo(String desToponimo) {
		this.desToponimo = desToponimo;
	}

	/**
	 * @return the operToponimo
	 */
	public String getOperToponimo() {
		return operToponimo;
	}

	/**
	 * @param operToponimo
	 *            the operToponimo to set
	 */
	public void setOperToponimo(String operToponimo) {
		this.operToponimo = operToponimo;
	}
}
