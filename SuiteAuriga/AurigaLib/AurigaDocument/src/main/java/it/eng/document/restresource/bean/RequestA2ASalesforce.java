/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestA2ASalesforce")
public class RequestA2ASalesforce {
	
	private String idTransazione;
	private String sistema;
	private String transactionDateTime;
	private String idSFDC;
	private String rObjectId;
	private String archivio;
	private String Stato;
	private String Descrizione;
	
	public String getIdTransazione() {
		return idTransazione;
	}
	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public String getIdSFDC() {
		return idSFDC;
	}
	public void setIdSFDC(String idSFDC) {
		this.idSFDC = idSFDC;
	}
	public String getrObjectId() {
		return rObjectId;
	}
	public void setrObjectId(String rObjectId) {
		this.rObjectId = rObjectId;
	}
	public String getArchivio() {
		return archivio;
	}
	public void setArchivio(String archivio) {
		this.archivio = archivio;
	}
	public String getStato() {
		return Stato;
	}
	public void setStato(String stato) {
		Stato = stato;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
		
}
