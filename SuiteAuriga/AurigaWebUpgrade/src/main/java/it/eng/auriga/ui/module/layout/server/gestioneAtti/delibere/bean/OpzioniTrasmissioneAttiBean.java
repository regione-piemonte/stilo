/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author dbe4235
 *
 */

public class OpzioniTrasmissioneAttiBean {
	
	private String flgInclusiPareri;
	private String flgInclAllegatiPI;	
	private String flgIntXPubbl;
	private String flgInvioAttiTrasmessi;
	
	private String mittente;
	private String destinatari;
	private String destinatariCC;
	private String oggetto;
	private String body;
	
	private List<ArgomentiOdgXmlBean> listaArgomentiOdgXmlBean;

	public String getFlgInclusiPareri() {
		return flgInclusiPareri;
	}
	public void setFlgInclusiPareri(String flgInclusiPareri) {
		this.flgInclusiPareri = flgInclusiPareri;
	}
	public String getFlgInclAllegatiPI() {
		return flgInclAllegatiPI;
	}
	public void setFlgInclAllegatiPI(String flgInclAllegatiPI) {
		this.flgInclAllegatiPI = flgInclAllegatiPI;
	}
	public String getFlgIntXPubbl() {
		return flgIntXPubbl;
	}
	public void setFlgIntXPubbl(String flgIntXPubbl) {
		this.flgIntXPubbl = flgIntXPubbl;
	}
	public String getFlgInvioAttiTrasmessi() {
		return flgInvioAttiTrasmessi;
	}
	public void setFlgInvioAttiTrasmessi(String flgInvioAttiTrasmessi) {
		this.flgInvioAttiTrasmessi = flgInvioAttiTrasmessi;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<ArgomentiOdgXmlBean> getListaArgomentiOdgXmlBean() {
		return listaArgomentiOdgXmlBean;
	}
	public void setListaArgomentiOdgXmlBean(List<ArgomentiOdgXmlBean> listaArgomentiOdgXmlBean) {
		this.listaArgomentiOdgXmlBean = listaArgomentiOdgXmlBean;
	}
}