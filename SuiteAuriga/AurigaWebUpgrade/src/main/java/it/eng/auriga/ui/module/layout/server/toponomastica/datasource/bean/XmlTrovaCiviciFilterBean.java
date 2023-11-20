/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


public class XmlTrovaCiviciFilterBean {

	/**
	 * Filtro di ricerca NrCivico
	 */
	@XmlVariabile(nome = "IdToponimo", tipo = TipoVariabile.SEMPLICE)
	private String idToponimo;
	@XmlVariabile(nome = "NriCiviciDa", tipo = TipoVariabile.SEMPLICE)
	private String nrCivicoDa;
	@XmlVariabile(nome = "NriCiviciA", tipo = TipoVariabile.SEMPLICE)
	private String nrCivicoA;
	@XmlVariabile(nome = "Esponente", tipo = TipoVariabile.SEMPLICE)
	private String esponente;
	@XmlVariabile(nome = "Zona", tipo = TipoVariabile.SEMPLICE)
	private String zona;

	/**
	 * @return the nrCivicoDa
	 */
	public String getNrCivicoDa() {
		return nrCivicoDa;
	}

	/**
	 * @param nrCivicoDa
	 *            the nrCivicoDa to set
	 */
	public void setNrCivicoDa(String nrCivicoDa) {
		this.nrCivicoDa = nrCivicoDa;
	}

	/**
	 * @return the nrCivicoA
	 */
	public String getNrCivicoA() {
		return nrCivicoA;
	}

	/**
	 * @param nrCivicoA
	 *            the nrCivicoA to set
	 */
	public void setNrCivicoA(String nrCivicoA) {
		this.nrCivicoA = nrCivicoA;
	}

	/**
	 * @return the esponente
	 */
	public String getEsponente() {
		return esponente;
	}

	/**
	 * @param esponente
	 *            the esponente to set
	 */
	public void setEsponente(String esponente) {
		this.esponente = esponente;
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
}
