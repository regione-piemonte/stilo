/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;

import java.io.Serializable;
import java.util.Date;

public class ChatControllerBean implements Serializable {

	private String idChat;
	private String nomeChat;
	private String codArgomento;
	private String descrArgomento;
	private boolean privata;
	private String[] partecipanti;
	private Date tsInvio;
	private AurigaLoginBean loginInfo;
	private String username;
	private String messaggio;
	private String[] history;

	public String getIdChat() {
		return idChat;
	}

	public void setIdChat(String idChat) {
		this.idChat = idChat;
	}

	public String getNomeChat() {
		return nomeChat;
	}

	public void setNomeChat(String nomeChat) {
		this.nomeChat = nomeChat;
	}

	public String getCodArgomento() {
		return codArgomento;
	}

	public void setCodArgomento(String codArgomento) {
		this.codArgomento = codArgomento;
	}

	public String getDescrArgomento() {
		return descrArgomento;
	}

	public void setDescrArgomento(String descrArgomento) {
		this.descrArgomento = descrArgomento;
	}

	public boolean isPrivata() {
		return privata;
	}

	public void setPrivata(boolean privata) {
		this.privata = privata;
	}

	public String[] getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(String[] partecipanti) {
		this.partecipanti = partecipanti;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public AurigaLoginBean getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(AurigaLoginBean loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String[] getHistory() {
		return history;
	}

	public void setHistory(String[] history) {
		this.history = history;
	}

}
