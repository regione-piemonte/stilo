/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class InvioUDMailBean {
	
	private String mittente;
	private String idCasellaMittente;
	private String destinatari;
	private String oggetto;
	private String body;  
	private String textHtml;
	private String bodyHtml;
	private String bodyText;
	private String destinatariCC;
	private String destinatariCCN;
	private Boolean salvaInviati;
	private Boolean richiestaConferma;
	private Boolean confermaLetturaShow;
	private Boolean confermaLettura;
	private Boolean flgInvioSeparato;
	private Boolean segnaturaPresente;
	private String idUD;
	private String flgTipoProv;
	private String tipoMail;
	private String avvertimenti;
	private List<AttachmentUDBean> attach;
	private List<DestinatariPecBean> destinatariPec;
	private String idMailPartenza;
	private String nomeFilePdf;
	private String uriFilePdf;
	private InfoFileRecord infoFilePdf;
	
	/** Se provengo da lista atti completi o dettaglio atto*/
	private Boolean invioMailFromAtti; 
	
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getIdCasellaMittente() {
		return idCasellaMittente;
	}
	public void setIdCasellaMittente(String idCasellaMittente) {
		this.idCasellaMittente = idCasellaMittente;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getDestinatariCC() {
		return destinatariCC;
	}
	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}
	public String getDestinatariCCN() {
		return destinatariCCN;
	}
	public void setDestinatariCCN(String destinatariCCN) {
		this.destinatariCCN = destinatariCCN;
	}
	public void setSalvaInviati(Boolean salvaInviati) {
		this.salvaInviati = salvaInviati;
	}
	public Boolean getSalvaInviati() {
		return salvaInviati;
	}
	
	public void setAttach(List<AttachmentUDBean> attach) {
		this.attach = attach;
	}
	public List<AttachmentUDBean> getAttach() {
		return attach;
	}
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	public String getIdUD() {
		return idUD;
	}
	public void setTipoMail(String tipoMail) {
		this.tipoMail = tipoMail;
	}
	public String getTipoMail() {
		return tipoMail;
	}
	public void setRichiestaConferma(Boolean richiestaConferma) {
		this.richiestaConferma = richiestaConferma;
	}
	public Boolean getRichiestaConferma() {
		return richiestaConferma;
	}
	public void setSegnaturaPresente(Boolean segnaturaPresente) {
		this.segnaturaPresente = segnaturaPresente;
	}
	public Boolean getSegnaturaPresente() {
		return segnaturaPresente;
	}
	public void setDestinatariPec(List<DestinatariPecBean> destinatariPec) {
		this.destinatariPec = destinatariPec;
	}
	public List<DestinatariPecBean> getDestinatariPec() {
		return destinatariPec;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAvvertimenti() {
		return avvertimenti;
	}
	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}
	public void setIdMailPartenza(String idMailPartenza) {
		this.idMailPartenza = idMailPartenza;
	}
	public String getIdMailPartenza() {
		return idMailPartenza;
	}
	public String getFlgTipoProv() {
		return flgTipoProv;
	}
	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}
	public Boolean getConfermaLetturaShow() {
		return confermaLetturaShow;
	}
	public void setConfermaLetturaShow(Boolean confermaLetturaShow) {
		this.confermaLetturaShow = confermaLetturaShow;
	}
	public Boolean getConfermaLettura() {
		return confermaLettura;
	}
	public void setConfermaLettura(Boolean confermaLettura) {
		this.confermaLettura = confermaLettura;
	}
	public String getTextHtml() {
		return textHtml;
	}
	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}
	public String getBodyHtml() {
		return bodyHtml;
	}
	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}
	public String getBodyText() {
		return bodyText;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	public Boolean getFlgInvioSeparato() {
		return flgInvioSeparato;
	}
	public void setFlgInvioSeparato(Boolean flgInvioSeparato) {
		this.flgInvioSeparato = flgInvioSeparato;
	}
	public String getNomeFilePdf() {
		return nomeFilePdf;
	}
	public void setNomeFilePdf(String nomeFilePdf) {
		this.nomeFilePdf = nomeFilePdf;
	}
	public String getUriFilePdf() {
		return uriFilePdf;
	}
	public void setUriFilePdf(String uriFilePdf) {
		this.uriFilePdf = uriFilePdf;
	}
	public InfoFileRecord getInfoFilePdf() {
		return infoFilePdf;
	}
	public void setInfoFilePdf(InfoFileRecord infoFilePdf) {
		this.infoFilePdf = infoFilePdf;
	}
	public Boolean getInvioMailFromAtti() {
		return invioMailFromAtti;
	}
	public void setInvioMailFromAtti(Boolean invioMailFromAtti) {
		this.invioMailFromAtti = invioMailFromAtti;
	}

}