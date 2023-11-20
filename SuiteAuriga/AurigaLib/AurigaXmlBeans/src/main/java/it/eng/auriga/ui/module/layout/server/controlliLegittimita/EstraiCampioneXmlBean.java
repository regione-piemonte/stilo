/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;


import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class EstraiCampioneXmlBean {
	
	@NumeroColonna(numero = "1")
	private String flgUdFolder;

	@NumeroColonna(numero = "2")
	private String idUdFolder;
	
	@NumeroColonna(numero = "3")
	private String nome;
	
	@NumeroColonna(numero = "4")
	private String segnatura;
	
	@NumeroColonna(numero = "6")
	private String segnaturaXOrd;
	
	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRegistrazione;
	
	@NumeroColonna(numero = "32")
	private String tipo;
	
	@NumeroColonna(numero = "18")
	private String oggetto;
	
	@NumeroColonna(numero = "91")
	private String mittenti;
	
	@NumeroColonna(numero = "92")
	private String destinatari;
	
	@NumeroColonna(numero = "204")
	private String flgAnnReg;
	
	@NumeroColonna(numero = "261")
	private String attoAutAnnullamento;
	
	@NumeroColonna(numero = "263")
	@TipoData(tipo = Tipo.DATA)
	private Date tsPresaInCarico;
	
	@NumeroColonna(numero = "10")
	private String repertorio;
	
	@NumeroColonna(numero = "260")
	private String motivoAnnullamento;
	
	@NumeroColonna(numero = "216")
	private String flgPresaInCarico;
	
	@NumeroColonna(numero = "14")
	private String nroProvvisorioAtto;
	

	@NumeroColonna(numero = "52")
	private String pubblicazione;
	
	@NumeroColonna(numero = "226")
	private String uoProponente;
	
	@NumeroColonna(numero = "90")
	private String info;
	
	@NumeroColonna(numero = "62")
	private String estremiInvioNotifiche;
	
	@NumeroColonna(numero = "29")
	private String idFolderApp;

	@NumeroColonna(numero = "30")
	private String percorsoFolderApp;
	
	@NumeroColonna(numero = "217")
	private String percorsoNome;
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}
	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSegnatura() {
		return segnatura;
	}
	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
	public String getSegnaturaXOrd() {
		return segnaturaXOrd;
	}
	public void setSegnaturaXOrd(String segnaturaXOrd) {
		this.segnaturaXOrd = segnaturaXOrd;
	}

	public Date getTsRegistrazione() {
		return tsRegistrazione;
	}
	public void setTsRegistrazione(Date tsRegistrazione) {
		this.tsRegistrazione = tsRegistrazione;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getMittenti() {
		return mittenti;
	}
	public void setMittenti(String mittenti) {
		this.mittenti = mittenti;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getFlgAnnReg() {
		return flgAnnReg;
	}
	public void setFlgAnnReg(String flgAnnReg) {
		this.flgAnnReg = flgAnnReg;
	}
	public String getAttoAutAnnullamento() {
		return attoAutAnnullamento;
	}
	public void setAttoAutAnnullamento(String attoAutAnnullamento) {
		this.attoAutAnnullamento = attoAutAnnullamento;
	}
	public Date getTsPresaInCarico() {
		return tsPresaInCarico;
	}
	public void setTsPresaInCarico(Date tsPresaInCarico) {
		this.tsPresaInCarico = tsPresaInCarico;
	}
	public String getRepertorio() {
		return repertorio;
	}
	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}
	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}
	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}
	public String getFlgPresaInCarico() {
		return flgPresaInCarico;
	}
	public void setFlgPresaInCarico(String flgPresaInCarico) {
		this.flgPresaInCarico = flgPresaInCarico;
	}
	public String getNroProvvisorioAtto() {
		return nroProvvisorioAtto;
	}
	public void setNroProvvisorioAtto(String nroProvvisorioAtto) {
		this.nroProvvisorioAtto = nroProvvisorioAtto;
	}
	public String getPubblicazione() {
		return pubblicazione;
	}
	public void setPubblicazione(String pubblicazione) {
		this.pubblicazione = pubblicazione;
	}
	public String getUoProponente() {
		return uoProponente;
	}
	public void setUoProponente(String uoProponente) {
		this.uoProponente = uoProponente;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getEstremiInvioNotifiche() {
		return estremiInvioNotifiche;
	}
	public void setEstremiInvioNotifiche(String estremiInvioNotifiche) {
		this.estremiInvioNotifiche = estremiInvioNotifiche;
	}
	public String getIdFolderApp() {
		return idFolderApp;
	}
	public void setIdFolderApp(String idFolderApp) {
		this.idFolderApp = idFolderApp;
	}
	public String getPercorsoFolderApp() {
		return percorsoFolderApp;
	}
	public void setPercorsoFolderApp(String percorsoFolderApp) {
		this.percorsoFolderApp = percorsoFolderApp;
	}
	public String getPercorsoNome() {
		return percorsoNome;
	}
	public void setPercorsoNome(String percorsoNome) {
		this.percorsoNome = percorsoNome;
	}
}
