/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class XMLDatiRegOutRegoleProtAutoCaselleBean implements Serializable {

	private static final long serialVersionUID = 5773663251414552214L;

	@XmlVariabile(nome = "IdUORegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String idUORegistrazione;

	@XmlVariabile(nome = "CodRapidoUORegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String codRapidoUORegistrazione;
	
	@XmlVariabile(nome = "DenomUORegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String denomUORegistrazione;
	
	@XmlVariabile(nome = "IdUserRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String idUserRegistrazione;
	
	@XmlVariabile(nome = "DesUserRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String desUserRegistrazione;
	
	@XmlVariabile(nome = "CodCategoriaReg", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaReg;
	
	@XmlVariabile(nome = "SiglaRegistro", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistro;
	
	@XmlVariabile(nome = "IdMittenteInRubrica", tipo = TipoVariabile.SEMPLICE)
	private String idMittenteInRubrica;
	
	@XmlVariabile(nome = "DenomMittente", tipo = TipoVariabile.SEMPLICE)
	private String denomMittente;
	
	@XmlVariabile(nome = "@Destinatari", tipo = TipoVariabile.LISTA)
	private List<DestinatariDatiRegRegoleProtAutoBean> destinatari;
	
	@XmlVariabile(nome = "@ClassifFolderTit", tipo = TipoVariabile.LISTA)
	private List<ClassifFolderTitRegoleProtAutoBean> classifFolderTit;
	
	@XmlVariabile(nome = "@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustomRegoleProtAutoBean> folderCustom;
	
	@XmlVariabile(nome = "Riservatezza", tipo = TipoVariabile.SEMPLICE)
	private String flgRiservatezza;
	
	@XmlVariabile(nome = "OpzTimbratura", tipo = TipoVariabile.SEMPLICE)
	private String opzTimbratura;
	
	@XmlVariabile(nome = "InvioConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String flgInvioConfermaReg;
	
	@XmlVariabile(nome = "DestinatarioConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String destinatarioConfermaReg;
	
	@XmlVariabile(nome = "OggettoConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String oggettoConfermaReg;
	
	@XmlVariabile(nome = "TestoConfermaReg", tipo = TipoVariabile.SEMPLICE)
	private String testoConfermaReg;

	public String getIdUORegistrazione() {
		return idUORegistrazione;
	}

	public void setIdUORegistrazione(String idUORegistrazione) {
		this.idUORegistrazione = idUORegistrazione;
	}

	public String getCodRapidoUORegistrazione() {
		return codRapidoUORegistrazione;
	}

	public void setCodRapidoUORegistrazione(String codRapidoUORegistrazione) {
		this.codRapidoUORegistrazione = codRapidoUORegistrazione;
	}

	public String getDenomUORegistrazione() {
		return denomUORegistrazione;
	}

	public void setDenomUORegistrazione(String denomUORegistrazione) {
		this.denomUORegistrazione = denomUORegistrazione;
	}

	public String getIdUserRegistrazione() {
		return idUserRegistrazione;
	}

	public void setIdUserRegistrazione(String idUserRegistrazione) {
		this.idUserRegistrazione = idUserRegistrazione;
	}

	public String getDesUserRegistrazione() {
		return desUserRegistrazione;
	}

	public void setDesUserRegistrazione(String desUserRegistrazione) {
		this.desUserRegistrazione = desUserRegistrazione;
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

	public List<DestinatariDatiRegRegoleProtAutoBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatariDatiRegRegoleProtAutoBean> destinatari) {
		this.destinatari = destinatari;
	}

	public List<ClassifFolderTitRegoleProtAutoBean> getClassifFolderTit() {
		return classifFolderTit;
	}

	public void setClassifFolderTit(List<ClassifFolderTitRegoleProtAutoBean> classifFolderTit) {
		this.classifFolderTit = classifFolderTit;
	}

	public List<FolderCustomRegoleProtAutoBean> getFolderCustom() {
		return folderCustom;
	}

	public void setFolderCustom(List<FolderCustomRegoleProtAutoBean> folderCustom) {
		this.folderCustom = folderCustom;
	}

	public String getFlgRiservatezza() {
		return flgRiservatezza;
	}

	public void setFlgRiservatezza(String flgRiservatezza) {
		this.flgRiservatezza = flgRiservatezza;
	}

	public String getOpzTimbratura() {
		return opzTimbratura;
	}

	public void setOpzTimbratura(String opzTimbratura) {
		this.opzTimbratura = opzTimbratura;
	}

	public String getFlgInvioConfermaReg() {
		return flgInvioConfermaReg;
	}

	public void setFlgInvioConfermaReg(String flgInvioConfermaReg) {
		this.flgInvioConfermaReg = flgInvioConfermaReg;
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
