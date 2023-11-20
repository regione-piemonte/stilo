/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class ModelliDocXmlBean {
	
	/*
	-- 1:  Identificativo del modello
	-- 2:  Nome del modello
	-- 3:  Descrizione dettagliata del modello
	-- 4:  Identificativo del formato del modello
	-- 5:  Formato del modello (descrittivo)
	-- 6:  Nome del tipo di documento/fascicolo/procedimento cui è associato il modello
	-- 7:  Identificativo del documento della repository che costituisce il modello
	-- 8:  Annotazioni sul modello
	-- 9:  Flag di annullamento logico (valori 1/0)
	-- 10: Motivi dell'annullamento logico
	-- 11: Cod. identificativo del modello nel sistema di provenienza
	-- 12: Timestamp di creazione del modello (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
	-- 13: Descrizione dell'utente di creazione del modello
	-- 14: Timestamp di ultima modifica dei dati del modello (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
	-- 15: Descrizione dell'utente di ultima modifica dei dati del modello
	-- 16: (valori 1/0) Indicatore di modello riservato dal sistema e non modificabile da applicativo
	-- 17: (valori 1/0) Indicatore di modulo da compilare on-line (attributo custom PER_COMPILAZIONE_ONLINE)
	-- 18: (valori 1/0/NULL) Indicatore di modulo on-line profilato completamente (1) o meno (0)
	-- 19: URI del file del modello
	-- 20: (flag 1/0) Indica se i documenti generati da modello devono essere restituiti in forma immodificabile pdf (1) o nel formato originale del modello (0)
	-- 21: indica il tipo di modello, ovvero con che generatore deve avvenire la generazione del file a partire dal modello: odt_con_freemarkers, docx_con_campi_controllo, docx_con_placeholder
	-- 22: Display filename del file del modello
    -- 23: Tipo di oggetto cui è associato il modello: TD = Tipo documento, TF = Tipo folder, TP = Tipo di processo/procedimento             
    -- 24: Id. del tipo documento/folder/procedimento cui è associato il modello
    -- 25: Nome della tabella in cui sono archiviati gli oggetti i cui metadati sono rappresentati nel modello
	*/
	
	@NumeroColonna(numero = "1")
	private String idModello;
	@NumeroColonna(numero = "2")
	private String nome;
	@NumeroColonna(numero = "3")
	private String descrizione;
	@NumeroColonna(numero = "4")
	private String idFormatoFile;	
	@NumeroColonna(numero = "5")
	private String nomeFormatoFile;	
	@NumeroColonna(numero = "6")
	private String nomeEntitaAssociata;	
	@NumeroColonna(numero = "7")
	private String idDoc;
	@NumeroColonna(numero = "8")
	private String annotazioni;
	@NumeroColonna(numero = "9")
	private String flgAnnLogico;
	@NumeroColonna(numero = "10")
	private String motiviAnnLogico;
	@NumeroColonna(numero = "11")
	private String codProvenienza;	
	@NumeroColonna(numero = "12")
	@TipoData(tipo = Tipo.DATA)
	private Date tsCreazione;
	@NumeroColonna(numero = "13")
	private String desUteCreazione;
	@NumeroColonna(numero = "14")
	@TipoData(tipo = Tipo.DATA)
	private Date desUltimoAgg;
	@NumeroColonna(numero = "15")
	private String idUteUltimoAgg;	
	@NumeroColonna(numero = "16")
	private String flgSistema;	
	@NumeroColonna(numero = "17")
	private String flgModuloOnline;
	@NumeroColonna(numero = "18")
	private String flgProfCompleta;	
	@NumeroColonna(numero = "19")
	private String uriFile;
	@NumeroColonna(numero = "20")
	private String flgGeneraPdf;
	@NumeroColonna(numero = "21")
	private String tipoModello;
	@NumeroColonna(numero = "22")
	private String nomeFile;
	@NumeroColonna(numero = "23")
	private String tipoEntitaAssociata;
	@NumeroColonna(numero = "24")
	private String idEntitaAssociata;
	@NumeroColonna(numero = "25")
	private String nomeTabella;
	
	public String getIdModello() {
		return idModello;
	}
	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getIdFormatoFile() {
		return idFormatoFile;
	}
	public void setIdFormatoFile(String idFormatoFile) {
		this.idFormatoFile = idFormatoFile;
	}
	public String getNomeFormatoFile() {
		return nomeFormatoFile;
	}
	public void setNomeFormatoFile(String nomeFormatoFile) {
		this.nomeFormatoFile = nomeFormatoFile;
	}
	public String getNomeEntitaAssociata() {
		return nomeEntitaAssociata;
	}
	public void setNomeEntitaAssociata(String nomeEntitaAssociata) {
		this.nomeEntitaAssociata = nomeEntitaAssociata;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}
	public String getFlgAnnLogico() {
		return flgAnnLogico;
	}
	public void setFlgAnnLogico(String flgAnnLogico) {
		this.flgAnnLogico = flgAnnLogico;
	}
	public String getMotiviAnnLogico() {
		return motiviAnnLogico;
	}
	public void setMotiviAnnLogico(String motiviAnnLogico) {
		this.motiviAnnLogico = motiviAnnLogico;
	}
	public String getCodProvenienza() {
		return codProvenienza;
	}
	public void setCodProvenienza(String codProvenienza) {
		this.codProvenienza = codProvenienza;
	}
	public Date getTsCreazione() {
		return tsCreazione;
	}
	public void setTsCreazione(Date tsCreazione) {
		this.tsCreazione = tsCreazione;
	}
	public String getDesUteCreazione() {
		return desUteCreazione;
	}
	public void setDesUteCreazione(String desUteCreazione) {
		this.desUteCreazione = desUteCreazione;
	}
	public Date getDesUltimoAgg() {
		return desUltimoAgg;
	}
	public void setDesUltimoAgg(Date desUltimoAgg) {
		this.desUltimoAgg = desUltimoAgg;
	}
	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}
	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}
	public String getFlgSistema() {
		return flgSistema;
	}
	public void setFlgSistema(String flgSistema) {
		this.flgSistema = flgSistema;
	}
	public String getFlgModuloOnline() {
		return flgModuloOnline;
	}
	public void setFlgModuloOnline(String flgModuloOnline) {
		this.flgModuloOnline = flgModuloOnline;
	}
	public String getFlgProfCompleta() {
		return flgProfCompleta;
	}
	public void setFlgProfCompleta(String flgProfCompleta) {
		this.flgProfCompleta = flgProfCompleta;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getFlgGeneraPdf() {
		return flgGeneraPdf;
	}
	public void setFlgGeneraPdf(String flgGeneraPdf) {
		this.flgGeneraPdf = flgGeneraPdf;
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getTipoEntitaAssociata() {
		return tipoEntitaAssociata;
	}
	public void setTipoEntitaAssociata(String tipoEntitaAssociata) {
		this.tipoEntitaAssociata = tipoEntitaAssociata;
	}
	public String getIdEntitaAssociata() {
		return idEntitaAssociata;
	}
	public void setIdEntitaAssociata(String idEntitaAssociata) {
		this.idEntitaAssociata = idEntitaAssociata;
	}
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	
}
