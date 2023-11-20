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

/**
 * The persistent class for the DMV_DATI_EMAIL_X_DL database table.
 * 
 */
@Entity
@Table(name = "T_TEMP_STORE_EMAIL", schema = "AURI_OWNER")
public class TTempStoreEmail implements Serializable {

	private static final long serialVersionUID = -7981567700461366479L;

	@Column(name = "ID_CASELLA")
	private String idCasella;

	@Id
	@Column(name = "ID_EMAIL")
	private String idEmail;

	@Column(name = "MESSAGE_ID")
	private String messageId;

	@Temporal(TemporalType.DATE)
	@Column(name = "TS_INVIO")
	private Date tsInvio;

	@Column(name = "ACCOUNT_MITTENTE")
	private String accountMittente;

	@Column(name = "OGGETTO")
	private String oggetto;

	@Lob
	@Column(name = "CORPO")
	private String corpo;

	@Lob
	@Column(name = "NOMI_ALLEGATI")
	private String nomiAllegati;

	public TTempStoreEmail() {
	}

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getNomiAllegati() {
		return nomiAllegati;
	}

	public void setNomiAllegati(String nomiAllegati) {
		this.nomiAllegati = nomiAllegati;
	}

}