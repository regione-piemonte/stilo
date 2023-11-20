/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


@XmlRootElement
public class CreaModDocNestleInBean extends CreaModFatturaInBean {

	private static final long serialVersionUID = -6609881844566442763L;


	@XmlVariabile(nome = "DATA_EVASIONE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String dataEvasione;
	
	@XmlVariabile(nome = "VETTORE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String vettore;
	
	@XmlVariabile(nome = "LDV_Doc", tipo = TipoVariabile.SEMPLICE)
	private String letteraDiVettura;
	
	@XmlVariabile(nome = "CDC_Doc", tipo = TipoVariabile.SEMPLICE)
	private String centroDiCosto;
	
	@XmlVariabile(nome = "NAZIONE_DESTINATARIO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nazione;

	@XmlVariabile(nome = "NRO_PAGINE_Ud", tipo = TipoVariabile.SEMPLICE)
	private Integer nroPagine;

	public String getDataEvasione() {
		return dataEvasione;
	}

	public void setDataEvasione(String dataEvasione) {
		this.dataEvasione = dataEvasione;
	}

	public String getVettore() {
		return vettore;
	}

	public void setVettore(String vettore) {
		this.vettore = vettore;
	}

	public String getLetteraDiVettura() {
		return letteraDiVettura;
	}

	public void setLetteraDiVettura(String letteraDiVettura) {
		this.letteraDiVettura = letteraDiVettura;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public Integer getNroPagine() {
		return nroPagine;
	}

	public void setNroPagine(Integer nroPagine) {
		this.nroPagine = nroPagine;
	}
	
}
