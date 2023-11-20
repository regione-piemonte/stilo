/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ModelloAttivitaBean {

	@NumeroColonna(numero = "1")
	private String activityName;
	@NumeroColonna(numero = "2")
	private String esitiXGenModello;
	@NumeroColonna(numero = "3")
	private String idTipoDoc;
	@NumeroColonna(numero = "4")
	private String nomeTipoDoc;
	@NumeroColonna(numero = "5")
	private String descrizione;
	@NumeroColonna(numero = "6")
	private String nomeFile;
	@NumeroColonna(numero = "7")
	private String formato;
	@NumeroColonna(numero = "8")
	private Boolean flgDaFirmare;
	@NumeroColonna(numero = "9")
	private Boolean flgLocked;
	@NumeroColonna(numero = "10")
	private String uri;
	@NumeroColonna(numero = "11")
	private String tipoModello;		
	@NumeroColonna(numero = "12")
	private Boolean flgParteDispositivo;	
	@NumeroColonna(numero = "13")
	private String idModello;
	@NumeroColonna(numero = "14")
	private String nomeModello;
	@NumeroColonna(numero = "15")
	private Boolean flgSkipAnteprima;		
	@NumeroColonna(numero = "16")
	private Boolean flgParere;	
	@NumeroColonna(numero = "17")
	private Boolean flgNoPubbl;	
	@NumeroColonna(numero = "18")
	private Boolean flgPubblicaSeparato;
	@NumeroColonna(numero = "19") // Firma automatica (true/false)
	private Boolean flgFirmaAuto;
	@NumeroColonna(numero = "20") // Userid certificato di firma automatica
	private String userIdFirmaAuto;
	@NumeroColonna(numero = "21") // Password/PIN per firma automatica
	private String passwordFirmaAuto;
	@NumeroColonna(numero = "22") // Firma automatica in delega (true/false)
	private Boolean flgDelegaFirmaAuto;
	@NumeroColonna(numero = "23") // Userid delegante per firma automatica
	private String firmaInDelegaFirmaAuto;
	@NumeroColonna(numero = "24") // Provider per firma automatica
	private String providerFirmaAuto;	
	@NumeroColonna(numero = "25")
	private Boolean flgPostAvanzamentoFlusso;
	@NumeroColonna(numero = "26")
	private String categoriaNumDaDare;
	@NumeroColonna(numero = "27")
	private String siglaNumDaDare;
	@NumeroColonna(numero = "28") // Se true l'allegato va sempre a creare un nuovo documento, mai a versionarne uno esistente dello stesso tipo
	private Boolean flgCreaNuovoDoc; 
	@NumeroColonna(numero = "29") // Considerato solo se colonna 8 = true. Se true indica che va fatta la firma grafica in caso di firma PAdES	
	private Boolean flgFirmaGrafica;
	@NumeroColonna(numero = "30") // Nro pagina in cui mettere la firma grafica. Nri da 1 in su + -1 che indica l'ultima pagina
	private String nroPaginaFirmaGrafica; 
	@NumeroColonna(numero = "31") // Riga della pagina in cui mettere la firma grafica (8 è la prima dall'alto e 1 l'ultima)
	private String nroRigaFirmaGrafica; 
	@NumeroColonna(numero = "32") // Colonna della pagina in cui mettere la firma grafica. Valori da 1 a 3 (1 prima a sinistra)
	private String nroColonnaFirmaGrafica; 
	@NumeroColonna(numero = "33") // Testo da mettere nella firma grafica. Da gestire i placeholder $intestatarioCertificato$ e $dataCorrente$
	private String testoFirmaGrafica;
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getEsitiXGenModello() {
		return esitiXGenModello;
	}
	public void setEsitiXGenModello(String esitiXGenModello) {
		this.esitiXGenModello = esitiXGenModello;
	}
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public Boolean getFlgDaFirmare() {
		return flgDaFirmare;
	}
	public void setFlgDaFirmare(Boolean flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}
	public Boolean getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}	
	public Boolean getFlgParteDispositivo() {
		return flgParteDispositivo;
	}	
	public void setFlgParteDispositivo(Boolean flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}
	public String getIdModello() {
		return idModello;
	}
	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}
	public String getNomeModello() {
		return nomeModello;
	}
	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}
	public Boolean getFlgSkipAnteprima() {
		return flgSkipAnteprima;
	}
	public void setFlgSkipAnteprima(Boolean flgSkipAnteprima) {
		this.flgSkipAnteprima = flgSkipAnteprima;
	}
	public Boolean getFlgParere() {
		return flgParere;
	}
	public void setFlgParere(Boolean flgParere) {
		this.flgParere = flgParere;
	}
	public Boolean getFlgNoPubbl() {
		return flgNoPubbl;
	}
	public void setFlgNoPubbl(Boolean flgNoPubbl) {
		this.flgNoPubbl = flgNoPubbl;
	}
	public Boolean getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}
	public void setFlgPubblicaSeparato(Boolean flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}
	public Boolean getFlgFirmaAuto() {
		return flgFirmaAuto;
	}
	public void setFlgFirmaAuto(Boolean flgFirmaAuto) {
		this.flgFirmaAuto = flgFirmaAuto;
	}
	public String getUserIdFirmaAuto() {
		return userIdFirmaAuto;
	}
	public void setUserIdFirmaAuto(String userIdFirmaAuto) {
		this.userIdFirmaAuto = userIdFirmaAuto;
	}
	public String getPasswordFirmaAuto() {
		return passwordFirmaAuto;
	}
	public void setPasswordFirmaAuto(String passwordFirmaAuto) {
		this.passwordFirmaAuto = passwordFirmaAuto;
	}
	public Boolean getFlgDelegaFirmaAuto() {
		return flgDelegaFirmaAuto;
	}
	public void setFlgDelegaFirmaAuto(Boolean flgDelegaFirmaAuto) {
		this.flgDelegaFirmaAuto = flgDelegaFirmaAuto;
	}
	public String getFirmaInDelegaFirmaAuto() {
		return firmaInDelegaFirmaAuto;
	}
	public void setFirmaInDelegaFirmaAuto(String firmaInDelegaFirmaAuto) {
		this.firmaInDelegaFirmaAuto = firmaInDelegaFirmaAuto;
	}
	public String getProviderFirmaAuto() {
		return providerFirmaAuto;
	}
	public void setProviderFirmaAuto(String providerFirmaAuto) {
		this.providerFirmaAuto = providerFirmaAuto;
	}
	public Boolean getFlgPostAvanzamentoFlusso() {
		return flgPostAvanzamentoFlusso;
	}
	public void setFlgPostAvanzamentoFlusso(Boolean flgPostAvanzamentoFlusso) {
		this.flgPostAvanzamentoFlusso = flgPostAvanzamentoFlusso;
	}
	public String getCategoriaNumDaDare() {
		return categoriaNumDaDare;
	}
	public void setCategoriaNumDaDare(String categoriaNumDaDare) {
		this.categoriaNumDaDare = categoriaNumDaDare;
	}
	public String getSiglaNumDaDare() {
		return siglaNumDaDare;
	}
	public void setSiglaNumDaDare(String siglaNumDaDare) {
		this.siglaNumDaDare = siglaNumDaDare;
	}
	public Boolean getFlgCreaNuovoDoc() {
		return flgCreaNuovoDoc;
	}
	public void setFlgCreaNuovoDoc(Boolean flgCreaNuovoDoc) {
		this.flgCreaNuovoDoc = flgCreaNuovoDoc;
	}
	public Boolean getFlgFirmaGrafica() {
		return flgFirmaGrafica;
	}
	public void setFlgFirmaGrafica(Boolean flgFirmaGrafica) {
		this.flgFirmaGrafica = flgFirmaGrafica;
	}
	public String getNroPaginaFirmaGrafica() {
		return nroPaginaFirmaGrafica;
	}
	public void setNroPaginaFirmaGrafica(String nroPaginaFirmaGrafica) {
		this.nroPaginaFirmaGrafica = nroPaginaFirmaGrafica;
	}
	public String getNroRigaFirmaGrafica() {
		return nroRigaFirmaGrafica;
	}
	public void setNroRigaFirmaGrafica(String nroRigaFirmaGrafica) {
		this.nroRigaFirmaGrafica = nroRigaFirmaGrafica;
	}
	public String getNroColonnaFirmaGrafica() {
		return nroColonnaFirmaGrafica;
	}
	public void setNroColonnaFirmaGrafica(String nroColonnaFirmaGrafica) {
		this.nroColonnaFirmaGrafica = nroColonnaFirmaGrafica;
	}
	public String getTestoFirmaGrafica() {
		return testoFirmaGrafica;
	}
	public void setTestoFirmaGrafica(String testoFirmaGrafica) {
		this.testoFirmaGrafica = testoFirmaGrafica;
	}

}
