package it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean;

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class RegoleProtocollazioneAutomaticaCaselleXMLDatiRegInSezioneCacheBean {
	
	@XmlVariabile(nome="IdUORegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String idUORegistrazione;
	@XmlVariabile(nome="IdUserRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String idUserRegistrazione;
	@XmlVariabile(nome="CodCategoriaReg", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaReg;
	@XmlVariabile(nome="SiglaRegistro", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistro;
	@XmlVariabile(nome="IdMittenteInRubrica", tipo = TipoVariabile.SEMPLICE)
	private String idMittenteInRubrica;
	@XmlVariabile(nome="DenomMittente", tipo = TipoVariabile.SEMPLICE)
	private String denomMittente;
	@XmlVariabile(nome = "@Destinatari", tipo = TipoVariabile.LISTA)
	private List<RegistrazioneDestinatari> listaIndirizziDestinatari;
	@XmlVariabile(nome = "@ClassifFolderTit", tipo = TipoVariabile.LISTA)
	private List<ClassifFolderTit> listClassifFolderTit;
	@XmlVariabile(nome = "@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustom> listFolderCustom;
	@XmlVariabile(nome="Riservatezza", tipo = TipoVariabile.SEMPLICE)
	private String riservatezza;
	@XmlVariabile(nome="OpzTimbratura", tipo = TipoVariabile.SEMPLICE)
	private String opzTimbratura;
	@XmlVariabile(nome="InvioConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String invioConfermaReg;
	@XmlVariabile(nome="DestinatarioConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String destinatarioConfermaReg;
	@XmlVariabile(nome="OggettoConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String oggettoConfermaReg;
	@XmlVariabile(nome="TestoConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String testoConfermaReg;
	
	public String getIdUORegistrazione() {
		return idUORegistrazione;
	}
	public void setIdUORegistrazione(String idUORegistrazione) {
		this.idUORegistrazione = idUORegistrazione;
	}
	public String getIdUserRegistrazione() {
		return idUserRegistrazione;
	}
	public void setIdUserRegistrazione(String idUserRegistrazione) {
		this.idUserRegistrazione = idUserRegistrazione;
	}
	public String getCodCategoriaReg() {
		return codCategoriaReg;
	}
	public void setCodCategoriaReg(String codCategoriaReg) {
		this.codCategoriaReg = codCategoriaReg;
	}
	public String getSiglaRegistro() {
		return siglaRegistro;
	}
	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	public String getIdMittenteInRubrica() {
		return idMittenteInRubrica;
	}
	public void setIdMittenteInRubrica(String idMittenteInRubrica) {
		this.idMittenteInRubrica = idMittenteInRubrica;
	}
	public String getDenomMittente() {
		return denomMittente;
	}
	public void setDenomMittente(String denomMittente) {
		this.denomMittente = denomMittente;
	}
	public List<RegistrazioneDestinatari> getListaIndirizziDestinatari() {
		return listaIndirizziDestinatari;
	}
	public void setListaIndirizziDestinatari(List<RegistrazioneDestinatari> listaIndirizziDestinatari) {
		this.listaIndirizziDestinatari = listaIndirizziDestinatari;
	}
	public List<ClassifFolderTit> getListClassifFolderTit() {
		return listClassifFolderTit;
	}
	public void setListClassifFolderTit(List<ClassifFolderTit> listClassifFolderTit) {
		this.listClassifFolderTit = listClassifFolderTit;
	}
	public List<FolderCustom> getListFolderCustom() {
		return listFolderCustom;
	}
	public void setListFolderCustom(List<FolderCustom> listFolderCustom) {
		this.listFolderCustom = listFolderCustom;
	}
	public String getRiservatezza() {
		return riservatezza;
	}
	public void setRiservatezza(String riservatezza) {
		this.riservatezza = riservatezza;
	}
	public String getOpzTimbratura() {
		return opzTimbratura;
	}
	public void setOpzTimbratura(String opzTimbratura) {
		this.opzTimbratura = opzTimbratura;
	}
	public String getInvioConfermaReg() {
		return invioConfermaReg;
	}
	public void setInvioConfermaReg(String invioConfermaReg) {
		this.invioConfermaReg = invioConfermaReg;
	}
	public String getDestinatarioConfermaReg() {
		return destinatarioConfermaReg;
	}
	public void setDestinatarioConfermaReg(String destinatarioConfermaReg) {
		this.destinatarioConfermaReg = destinatarioConfermaReg;
	}
	public String getOggettoConfermaReg() {
		return oggettoConfermaReg;
	}
	public void setOggettoConfermaReg(String oggettoConfermaReg) {
		this.oggettoConfermaReg = oggettoConfermaReg;
	}
	public String getTestoConfermaReg() {
		return testoConfermaReg;
	}
	public void setTestoConfermaReg(String testoConfermaReg) {
		this.testoConfermaReg = testoConfermaReg;
	}

}
