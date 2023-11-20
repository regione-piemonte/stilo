/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class DmvDatiEmailDlBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5858903044212618237L;

	private String idEmail;
	private String casellaRicezione;
	private String messageId;
	private String mittente;
	private String tsInvio;
	private String oggettoEmail;
	private String testoEmail;
	private String nomiAllegatiEmail;

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getCasellaRicezione() {
		return casellaRicezione;
	}

	public void setCasellaRicezione(String casellaRicezione) {
		this.casellaRicezione = casellaRicezione;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(String tsInvio) {
		this.tsInvio = tsInvio;
	}

	public String getOggettoEmail() {
		return oggettoEmail;
	}

	public void setOggettoEmail(String oggettoEmail) {
		this.oggettoEmail = oggettoEmail;
	}

	public String getTestoEmail() {
		return testoEmail;
	}

	public void setTestoEmail(String testoEmail) {
		this.testoEmail = testoEmail;
	}

	public String getNomiAllegatiEmail() {
		return nomiAllegatiEmail;
	}

	public void setNomiAllegatiEmail(String nomiAllegatiEmail) {
		this.nomiAllegatiEmail = nomiAllegatiEmail;
	}
}
