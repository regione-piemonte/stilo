/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

/**
 * The persistent class for the DMV_DATI_EMAIL_X_DL database tabl.
 * 
 */
@Entity
@Table(name = "DMV_DATI_EMAIL_X_DL", schema = "AURI_OWNER")
@Immutable
public class DmvDatiEmailXDl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CASELLA_RICEZIONE")
	private String casellaRicezione;

	@Id
	@Column(name = "ID_EMAIL", updatable = false, nullable = false)
	private String idEmail;

	@Column(name = "MESSAGE_ID")
	private String messageId;

	private String mittente;

	@Column(name = "NOMI_ALLEGATI_EMAIL")
	private String nomiAllegatiEmail;

	@Column(name = "OGGETTO_EMAIL")
	private String oggettoEmail;

	@Lob
	@Column(name = "TESTO_EMAIL")
	private String testoEmail;

	@Temporal(TemporalType.DATE)
	@Column(name = "TS_INVIO")
	private Date tsInvio;

	public DmvDatiEmailXDl() {
	}

	public String getCasellaRicezione() {
		return this.casellaRicezione;
	}

	public void setCasellaRicezione(String casellaRicezione) {
		this.casellaRicezione = casellaRicezione;
	}

	public String getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMittente() {
		return this.mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getNomiAllegatiEmail() {
		return this.nomiAllegatiEmail;
	}

	public void setNomiAllegatiEmail(String nomiAllegatiEmail) {
		this.nomiAllegatiEmail = nomiAllegatiEmail;
	}

	public String getOggettoEmail() {
		return this.oggettoEmail;
	}

	public void setOggettoEmail(String oggettoEmail) {
		this.oggettoEmail = oggettoEmail;
	}

	public String getTestoEmail() {
		return this.testoEmail;
	}

	public void setTestoEmail(String testoEmail) {
		this.testoEmail = testoEmail;
	}

	public Date getTsInvio() {
		return this.tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

}