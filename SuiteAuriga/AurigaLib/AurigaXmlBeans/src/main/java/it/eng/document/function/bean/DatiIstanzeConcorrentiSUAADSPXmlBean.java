/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class DatiIstanzeConcorrentiSUAADSPXmlBean implements Serializable {

	@NumeroColonna(numero ="1")
	private String nroProtocollo;
	
	@NumeroColonna(numero ="2")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPresentazione;

	@NumeroColonna(numero ="3")
	private String nroPratica;

	@NumeroColonna(numero ="4")
	private String mittente;

	public String getNroProtocollo() {
		return nroProtocollo;
	}

	public void setNroProtocollo(String nroProtocollo) {
		this.nroProtocollo = nroProtocollo;
	}

	public Date getDataPresentazione() {
		return dataPresentazione;
	}

	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}

	public String getNroPratica() {
		return nroPratica;
	}

	public void setNroPratica(String nroPratica) {
		this.nroPratica = nroPratica;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	
}
