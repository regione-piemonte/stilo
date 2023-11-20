/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.utility.XmlListaSimpleBean;

/**
 * 
 * @author dbe4235
 *
 */

public class InfoTrasmissioneAttiXmlBean implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@XmlVariabile(nome="ReinvioAttiTrasmessi", tipo=TipoVariabile.SEMPLICE)
	private String reinvioAttiTrasmessi;
	
	@XmlVariabile(nome="AccountMittente", tipo=TipoVariabile.SEMPLICE)
	private String accountMittente;
	
	@XmlVariabile(nome="@Destinatari", tipo=TipoVariabile.SEMPLICE)
	private List<XmlListaSimpleBean> listaDestinatari;
	
	@XmlVariabile(nome="Subject", tipo=TipoVariabile.SEMPLICE)
	private String subject;
	
	@XmlVariabile(nome="Body", tipo=TipoVariabile.SEMPLICE)
	private String body;

	public String getReinvioAttiTrasmessi() {
		return reinvioAttiTrasmessi;
	}

	public void setReinvioAttiTrasmessi(String reinvioAttiTrasmessi) {
		this.reinvioAttiTrasmessi = reinvioAttiTrasmessi;
	}

	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	public List<XmlListaSimpleBean> getListaDestinatari() {
		return listaDestinatari;
	}

	public void setListaDestinatari(List<XmlListaSimpleBean> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}