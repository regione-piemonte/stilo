/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.util.Date;
import java.util.List;

public class InvioUDMailOutBean {

	@XmlVariabile(nome = "CasellaMittente", tipo = TipoVariabile.SEMPLICE)
	private String casellaMittente;
	@XmlVariabile(nome = "IdCasellaMittente", tipo = TipoVariabile.SEMPLICE)
	private String idCasellaMittente;
	@XmlVariabile(nome = "IndirizziDestinatari", tipo = TipoVariabile.SEMPLICE)
	private String destinatari;
	@XmlVariabile(nome = "IndirizziDestinatariCC", tipo = TipoVariabile.SEMPLICE)
	private String destinatariCC;
	@XmlVariabile(nome = "IndirizziDestinatariCCN", tipo = TipoVariabile.SEMPLICE)
	private String destinatariCCN;
	@XmlVariabile(nome = "Subject", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;
	@XmlVariabile(nome = "Body", tipo = TipoVariabile.SEMPLICE)
	private String body;
	@XmlVariabile(nome = "RichiestaConferma", tipo = TipoVariabile.SEMPLICE)
	private Flag richiestaConferma;
	@XmlVariabile(nome = "ConfermaLetturaShow", tipo = TipoVariabile.SEMPLICE)
	private Flag confermaLetturaShow;
	@XmlVariabile(nome = "ConfermaLettura", tipo = TipoVariabile.SEMPLICE)
	private Flag confermaLettura;
	@XmlVariabile(nome = "SalvataggioInInviati", tipo = TipoVariabile.SEMPLICE)
	private Flag flagSalvaInviati;
	@XmlVariabile(nome = "Avvertimenti", tipo = TipoVariabile.SEMPLICE)
	private String avvertimenti;
	@XmlVariabile(nome = "@Attachment", tipo = TipoVariabile.LISTA)
	private List<InvioUDMailOutAttachmentBean> attachments;
	@XmlVariabile(nome = "@Destinatari", tipo = TipoVariabile.LISTA)
	private List<InvioUDMailOutDestinatariBean> destinatariList;
	@XmlVariabile(nome = "CodCategoriaReg", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaReg;
	@XmlVariabile(nome = "SiglaReg", tipo = TipoVariabile.SEMPLICE)
	private String siglaReg;
	@XmlVariabile(nome = "AnnoReg", tipo = TipoVariabile.SEMPLICE)
	private String annoReg;
	@XmlVariabile(nome = "NumeroReg", tipo = TipoVariabile.SEMPLICE)
	private String numeroReg;
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "DataReg", tipo = TipoVariabile.SEMPLICE)
	private Date dataReg;
	@XmlVariabile(nome = "AliasAccountMittente", tipo = TipoVariabile.SEMPLICE)
	private String aliasAccountMittente;
	@XmlVariabile(nome = "CodCanaleInvioDest", tipo = TipoVariabile.SEMPLICE)
	private String codCanaleInvioDest;
	
	/** INVIO MAIL ATTI */
	@XmlVariabile(nome = "IdModelloFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String idModelloFoglioFirme;
	@XmlVariabile(nome = "NomeModelloFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String nomeModelloFoglioFirme;
	@XmlVariabile(nome = "URIFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String uriFoglioFirme;
	@XmlVariabile(nome = "TipoModelloFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String tipoModelloFoglioFirme;
	@XmlVariabile(nome = "DisplayFilenameFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String displayFilenameFoglioFirme;
	
	public String getCasellaMittente() {
		return casellaMittente;
	}

	public void setCasellaMittente(String casellaMittente) {
		this.casellaMittente = casellaMittente;
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

	public Flag getRichiestaConferma() {
		return richiestaConferma;
	}

	public void setRichiestaConferma(Flag richiestaConferma) {
		this.richiestaConferma = richiestaConferma;
	}

	public Flag getFlagSalvaInviati() {
		return flagSalvaInviati;
	}

	public void setFlagSalvaInviati(Flag flagSalvaInviati) {
		this.flagSalvaInviati = flagSalvaInviati;
	}

	public String getAvvertimenti() {
		return avvertimenti;
	}

	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}

	public void setAttachments(List<InvioUDMailOutAttachmentBean> attachments) {
		this.attachments = attachments;
	}

	public List<InvioUDMailOutAttachmentBean> getAttachments() {
		return attachments;
	}

	public void setDestinatariList(List<InvioUDMailOutDestinatariBean> destinatariList) {
		this.destinatariList = destinatariList;
	}

	public List<InvioUDMailOutDestinatariBean> getDestinatariList() {
		return destinatariList;
	}

	public Flag getConfermaLetturaShow() {
		return confermaLetturaShow;
	}

	public void setConfermaLetturaShow(Flag confermaLetturaShow) {
		this.confermaLetturaShow = confermaLetturaShow;
	}

	public Flag getConfermaLettura() {
		return confermaLettura;
	}

	public void setConfermaLettura(Flag confermaLettura) {
		this.confermaLettura = confermaLettura;
	}

	public String getCodCategoriaReg() {
		return codCategoriaReg;
	}

	public void setCodCategoriaReg(String codCategoriaReg) {
		this.codCategoriaReg = codCategoriaReg;
	}

	public String getSiglaReg() {
		return siglaReg;
	}

	public void setSiglaReg(String siglaReg) {
		this.siglaReg = siglaReg;
	}

	public String getAnnoReg() {
		return annoReg;
	}

	public void setAnnoReg(String annoReg) {
		this.annoReg = annoReg;
	}

	public String getNumeroReg() {
		return numeroReg;
	}

	public void setNumeroReg(String numeroReg) {
		this.numeroReg = numeroReg;
	}

	public Date getDataReg() {
		return dataReg;
	}

	public void setDataReg(Date dataReg) {
		this.dataReg = dataReg;
	}

	public String getAliasAccountMittente() {
		return aliasAccountMittente;
	}

	public void setAliasAccountMittente(String aliasAccountMittente) {
		this.aliasAccountMittente = aliasAccountMittente;
	}

	public String getCodCanaleInvioDest() {
		return codCanaleInvioDest;
	}

	public void setCodCanaleInvioDest(String codCanaleInvioDest) {
		this.codCanaleInvioDest = codCanaleInvioDest;
	}

	public String getIdModelloFoglioFirme() {
		return idModelloFoglioFirme;
	}

	public String getNomeModelloFoglioFirme() {
		return nomeModelloFoglioFirme;
	}

	public String getUriFoglioFirme() {
		return uriFoglioFirme;
	}

	public String getTipoModelloFoglioFirme() {
		return tipoModelloFoglioFirme;
	}

	public String getDisplayFilenameFoglioFirme() {
		return displayFilenameFoglioFirme;
	}

	public void setIdModelloFoglioFirme(String idModelloFoglioFirme) {
		this.idModelloFoglioFirme = idModelloFoglioFirme;
	}

	public void setNomeModelloFoglioFirme(String nomeModelloFoglioFirme) {
		this.nomeModelloFoglioFirme = nomeModelloFoglioFirme;
	}

	public void setUriFoglioFirme(String uriFoglioFirme) {
		this.uriFoglioFirme = uriFoglioFirme;
	}

	public void setTipoModelloFoglioFirme(String tipoModelloFoglioFirme) {
		this.tipoModelloFoglioFirme = tipoModelloFoglioFirme;
	}

	public void setDisplayFilenameFoglioFirme(String displayFilenameFoglioFirme) {
		this.displayFilenameFoglioFirme = displayFilenameFoglioFirme;
	}
}