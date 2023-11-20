/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class XmlFiltroAutoRegoleProtAutoCaselleBean implements Serializable {

	private static final long serialVersionUID = 1408081511980507231L;
	
	@XmlVariabile(nome = "accountRicezione", tipo = TipoVariabile.SEMPLICE)
	private String accountRicezione;

	@XmlVariabile(nome = "accountMittente", tipo = TipoVariabile.SEMPLICE)
	private String accountMittente;
	
	@XmlVariabile(nome = "@OggettoEmailMatchList", tipo = TipoVariabile.LISTA)
	private List<OggettoEmailMatchListBean> oggettoEmailMatchList;
	
	@XmlVariabile(nome = "@BodyEmailMatchList", tipo = TipoVariabile.LISTA)
	private List<BodyEmailMatchListBean> bodyEmailMatchList;
	
	@XmlVariabile(nome = "@Destinatari", tipo = TipoVariabile.LISTA)
	private List<DestinatariRegoleProtAutoBean> destinatari;
	
	@XmlVariabile(nome = "tipoMail", tipo = TipoVariabile.SEMPLICE)
	private String tipoMail;
	
	@XmlVariabile(nome = "tipoAccountRicezione", tipo = TipoVariabile.SEMPLICE)
	private String tipoAccountRicezione;
	
	@XmlVariabile(nome = "@DatiSegnatura", tipo = TipoVariabile.LISTA)
	private List<DatiSegnaturaRegoleProtAutoBean> datiSegnatura;

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

	public List<OggettoEmailMatchListBean> getOggettoEmailMatchList() {
		return oggettoEmailMatchList;
	}

	public void setOggettoEmailMatchList(List<OggettoEmailMatchListBean> oggettoEmailMatchList) {
		this.oggettoEmailMatchList = oggettoEmailMatchList;
	}

	public List<BodyEmailMatchListBean> getBodyEmailMatchList() {
		return bodyEmailMatchList;
	}

	public void setBodyEmailMatchList(List<BodyEmailMatchListBean> bodyEmailMatchList) {
		this.bodyEmailMatchList = bodyEmailMatchList;
	}

	public List<DestinatariRegoleProtAutoBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatariRegoleProtAutoBean> destinatari) {
		this.destinatari = destinatari;
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

	public List<DatiSegnaturaRegoleProtAutoBean> getDatiSegnatura() {
		return datiSegnatura;
	}

	public void setDatiSegnatura(List<DatiSegnaturaRegoleProtAutoBean> datiSegnatura) {
		this.datiSegnatura = datiSegnatura;
	}

}
