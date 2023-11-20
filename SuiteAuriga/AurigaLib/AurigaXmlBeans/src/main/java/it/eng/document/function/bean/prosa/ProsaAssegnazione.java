/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProsaAssegnazione implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codiceAssegnazione;
	private java.util.Calendar dataScadenza;
	private Long idProtocollo;
	private String note;
	private String ufficioAssegnatario;
	private String utenteAssegnatario;

	public String getCodiceAssegnazione() {
		return codiceAssegnazione;
	}

	public void setCodiceAssegnazione(String codiceAssegnazione) {
		this.codiceAssegnazione = codiceAssegnazione;
	}

	public java.util.Calendar getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(java.util.Calendar dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Long getIdProtocollo() {
		return idProtocollo;
	}

	public void setIdProtocollo(Long idProtocollo) {
		this.idProtocollo = idProtocollo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUfficioAssegnatario() {
		return ufficioAssegnatario;
	}

	public void setUfficioAssegnatario(String ufficioAssegnatario) {
		this.ufficioAssegnatario = ufficioAssegnatario;
	}

	public String getUtenteAssegnatario() {
		return utenteAssegnatario;
	}

	public void setUtenteAssegnatario(String utenteAssegnatario) {
		this.utenteAssegnatario = utenteAssegnatario;
	}

}
