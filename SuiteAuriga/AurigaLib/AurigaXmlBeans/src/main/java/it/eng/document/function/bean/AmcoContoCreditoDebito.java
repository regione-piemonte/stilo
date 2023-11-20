/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoContoCreditoDebito implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String descrizione;
	private String natura;
	private String tipologia;
	
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getNatura() {
		return natura;
	}
	
	public void setNatura(String natura) {
		this.natura = natura;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	@Override
	public String toString() {
		return "AmcoContoCreditoDebito [codice=" + codice + ", descrizione=" + descrizione + ", natura=" + natura
				+ ", tipologia=" + tipologia + "]";
	}
	
}
