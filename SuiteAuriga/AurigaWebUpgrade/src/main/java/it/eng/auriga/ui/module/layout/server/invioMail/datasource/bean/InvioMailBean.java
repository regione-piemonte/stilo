/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.OperazioneMassivaPostaElettronicaBean;
import it.eng.aurigamailbusiness.bean.DettagliXlsIndirizziEmailXmlBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class InvioMailBean extends OperazioneMassivaPostaElettronicaBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943122913941025629L;

	private String mittente;
	private String casellaIsPec;
	private String destinatari;
	private String oggetto;
	private String textHtml;
	private String bodyHtml;
	private String bodyText;
	private String destinatariCC;
	private String destinatariCCN;
	private Boolean salvaInviati;
	private Boolean confermaLettura;
	private Boolean chiudiMailDiProvenienza;
	private String uriMail;
	private String dimensioneMail;
	/**
	 * Id email di riferimento in caso di inoltro o risposta
	 */
	private String idEmail;
	private List<IdMailInoltrataMailXmlBean> listaIdEmailPredecessore;
	private List<AttachmentBean> attach;
	private Boolean interoperabile;
	private String aliasAccountMittente;
	private String tipoRelCopia;

	private String destinatariHidden;
	private String idEmailUD;
	private String mailPredecessoreStatoLav;
	
	/**
	 * Invia mail separate a ciascun destinatario
	 */
	private Boolean flgInvioSeparato;
	
	private String codCanaleInvioDest;

	
	// Uri del file xls con gli indirizzi email dei destinatari
	private String uriXls;
	private String nomeFileXls;
	private MimeTypeFirmaBean infoFileXls;
	
	// Range delle righe del foglio xls
	private Integer rigaXlsDa;
	private Integer rigaXlsA;
	
	// Lista indirizzi caricati dal foglio excel
	private  List<DettagliXlsIndirizziEmailXmlBean>  dettagliXlsIndirizziEmail;
	
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

	public void setAttach(List<AttachmentBean> attach) {
		this.attach = attach;
	}

	public List<AttachmentBean> getAttach() {
		return attach;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public Boolean getInteroperabile() {
		return interoperabile;
	}

	public void setInteroperabile(Boolean interoperabile) {
		this.interoperabile = interoperabile;
	}

	public Boolean getConfermaLettura() {
		return confermaLettura;
	}

	public void setConfermaLettura(Boolean confermaLettura) {
		this.confermaLettura = confermaLettura;
	}
	
	public Boolean getChiudiMailDiProvenienza() {
		return chiudiMailDiProvenienza;
	}

	public void setChiudiMailDiProvenienza(Boolean chiudiMailDiProvenienza) {
		this.chiudiMailDiProvenienza = chiudiMailDiProvenienza;
	}

	public String getAliasAccountMittente() {
		return aliasAccountMittente;
	}

	public void setAliasAccountMittente(String aliasAccountMittente) {
		this.aliasAccountMittente = aliasAccountMittente;
	}

	public String getUriMail() {
		return uriMail;
	}

	
	public String getDestinatariHidden() {
		return destinatariHidden;
	}

	public void setDestinatariHidden(String destinatariHidden) {
		this.destinatariHidden = destinatariHidden;
	}

	public String getIdEmailUD() {
		return idEmailUD;
	}
	
	public void setIdEmailUD(String idEmailUD) {
		this.idEmailUD = idEmailUD;
	}

	public String getMailPredecessoreStatoLav() {
		return mailPredecessoreStatoLav;
	}

	public void setMailPredecessoreStatoLav(String mailPredecessoreStatoLav) {
		this.mailPredecessoreStatoLav = mailPredecessoreStatoLav;
	}

	public void setUriMail(String uriMail) {
		this.uriMail = uriMail;
	}

	public String getDimensioneMail() {
		return dimensioneMail;
	}

	public void setDimensioneMail(String dimensioneMail) {
		this.dimensioneMail = dimensioneMail;
	}

	public String getCasellaIsPec() {
		return casellaIsPec;
	}

	public void setCasellaIsPec(String casellaIsPec) {
		this.casellaIsPec = casellaIsPec;
	}

	public List<IdMailInoltrataMailXmlBean> getListaIdEmailPredecessore() {
		return listaIdEmailPredecessore;
	}

	public void setListaIdEmailPredecessore(List<IdMailInoltrataMailXmlBean> listaIdEmailPredecessore) {
		this.listaIdEmailPredecessore = listaIdEmailPredecessore;
	}

	public String getTipoRelCopia() {
		return tipoRelCopia;
	}

	public void setTipoRelCopia(String tipoRelCopia) {
		this.tipoRelCopia = tipoRelCopia;
	}

	public Boolean getFlgInvioSeparato() {
		return flgInvioSeparato;
	}

	public void setFlgInvioSeparato(Boolean flgInvioSeparato) {
		this.flgInvioSeparato = flgInvioSeparato;
	}

	public String getCodCanaleInvioDest() {
		return codCanaleInvioDest;
	}

	public void setCodCanaleInvioDest(String codCanaleInvioDest) {
		this.codCanaleInvioDest = codCanaleInvioDest;
	}

	public String getUriXls() {
		return uriXls;
	}

	public void setUriXls(String uriXls) {
		this.uriXls = uriXls;
	}

	public List<DettagliXlsIndirizziEmailXmlBean> getDettagliXlsIndirizziEmail() {
		return dettagliXlsIndirizziEmail;
	}

	public void setDettagliXlsIndirizziEmail(List<DettagliXlsIndirizziEmailXmlBean> dettagliXlsIndirizziEmail) {
		this.dettagliXlsIndirizziEmail = dettagliXlsIndirizziEmail;
	}

	public String getNomeFileXls() {
		return nomeFileXls;
	}

	public void setNomeFileXls(String nomeFileXls) {
		this.nomeFileXls = nomeFileXls;
	}

	public MimeTypeFirmaBean getInfoFileXls() {
		return infoFileXls;
	}

	public void setInfoFileXls(MimeTypeFirmaBean infoFileXls) {
		this.infoFileXls = infoFileXls;
	}

	public Integer getRigaXlsDa() {
		return rigaXlsDa;
	}

	public void setRigaXlsDa(Integer rigaXlsDa) {
		this.rigaXlsDa = rigaXlsDa;
	}

	public Integer getRigaXlsA() {
		return rigaXlsA;
	}

	public void setRigaXlsA(Integer rigaXlsA) {
		this.rigaXlsA = rigaXlsA;
	}

	

}