package it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean;

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class RegoleProtocollazioneAutomaticaCaselleXMLFiltroInSezioneCacheBean {
	
	@XmlVariabile(nome="AccountRicezione", tipo = TipoVariabile.SEMPLICE)
	private String accountRicezione;
	@XmlVariabile(nome="AccountMittente", tipo = TipoVariabile.SEMPLICE)
	private String accountMittente;
	@XmlVariabile(nome = "@OggettoEmailMatchList", tipo = TipoVariabile.LISTA)
	private List<OggettoEmailMatch> listaOggettoEmail;
	@XmlVariabile(nome = "@BodyEmailMatchList", tipo = TipoVariabile.LISTA)
	private List<BodyEmailMatch> listaBodyEmail;
	@XmlVariabile(nome = "@Destinatari", tipo = TipoVariabile.LISTA)
	private List<IndirizziDestinatari> listaIndirizziDestinatari;
	@XmlVariabile(nome="TipoMail", tipo = TipoVariabile.SEMPLICE)
	private String tipoMail;
	@XmlVariabile(nome="TipoAccountRicezione", tipo = TipoVariabile.SEMPLICE)
	private String tipoAccountRicezione;
	@XmlVariabile(nome = "@DatiSegnatura", tipo = TipoVariabile.LISTA)
	private List<DatiSegnatura> listaDatiSegnatura;
	
	public String getAccountRicezione() {
		return accountRicezione;
	}
	public void setAccountRicezione(String accountRicezione) {
		this.accountRicezione = accountRicezione;
	}
	public String getAccountMittente() {
		return accountMittente;
	}
	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}
	public List<OggettoEmailMatch> getListaOggettoEmail() {
		return listaOggettoEmail;
	}
	public void setListaOggettoEmail(List<OggettoEmailMatch> listaOggettoEmail) {
		this.listaOggettoEmail = listaOggettoEmail;
	}
	public List<BodyEmailMatch> getListaBodyEmail() {
		return listaBodyEmail;
	}
	public void setListaBodyEmail(List<BodyEmailMatch> listaBodyEmail) {
		this.listaBodyEmail = listaBodyEmail;
	}
	public List<IndirizziDestinatari> getListaIndirizziDestinatari() {
		return listaIndirizziDestinatari;
	}
	public void setListaIndirizziDestinatari(List<IndirizziDestinatari> listaIndirizziDestinatari) {
		this.listaIndirizziDestinatari = listaIndirizziDestinatari;
	}
	public String getTipoMail() {
		return tipoMail;
	}
	public void setTipoMail(String tipoMail) {
		this.tipoMail = tipoMail;
	}
	public String getTipoAccountRicezione() {
		return tipoAccountRicezione;
	}
	public void setTipoAccountRicezione(String tipoAccountRicezione) {
		this.tipoAccountRicezione = tipoAccountRicezione;
	}
	public List<DatiSegnatura> getListaDatiSegnatura() {
		return listaDatiSegnatura;
	}
	public void setListaDatiSegnatura(List<DatiSegnatura> listaDatiSegnatura) {
		this.listaDatiSegnatura = listaDatiSegnatura;
	}

}
