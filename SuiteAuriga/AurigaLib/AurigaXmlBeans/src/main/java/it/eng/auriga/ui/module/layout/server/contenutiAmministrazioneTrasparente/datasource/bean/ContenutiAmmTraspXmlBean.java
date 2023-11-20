/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class ContenutiAmmTraspXmlBean {

	@NumeroColonna(numero="1")
	private String idContenuto;

	@NumeroColonna(numero="2")
	private String tipoContenuto;
	
	@NumeroColonna(numero="3")
	private String titoloContenuto;
	
	@NumeroColonna(numero="4")
	private String htmlContenuto;
	
	@NumeroColonna(numero="5")
	private Boolean flgMostraDatiCreazione;
	
	@NumeroColonna(numero="6")
	private String htmlUltimoAgg;
	
	@NumeroColonna(numero="7")
	private Integer nroRecTabella;
	
	@NumeroColonna(numero="8")
	private String uriFile;
	
	@NumeroColonna(numero="9")
	private String displayFile;
	
	@NumeroColonna(numero="10")
	private Boolean flgFileFirmato;
	
	@NumeroColonna(numero="11")
	private Boolean flgFileFirmatoCades;
	
	@NumeroColonna(numero="12")
	private Boolean flgFilePdf;
	
	@NumeroColonna(numero="13")
	private String mimeTypeFile;
	
	@NumeroColonna(numero="14")
	private String improntaFile;
	
	@NumeroColonna(numero="15")
	private String algoritmoImpronta;
	
	@NumeroColonna(numero="16")
	private String encodingImpronta;
	
	@NumeroColonna(numero="17")
	private String idDocFile;
	
	@NumeroColonna(numero="18")
	private String nroVersioneFile;

	@NumeroColonna(numero="19")
	private String idUd;
		
	@NumeroColonna(numero = "20")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioPubblicazione;
	
	@NumeroColonna(numero = "21")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtFinePubblicazione;

	@NumeroColonna(numero="22")
	private Boolean flgContenutoPubblicato;

	@NumeroColonna(numero="23")
	private Boolean flgExportOpenData;
	
	@NumeroColonna(numero="25")
	private String statoRichiestaPubblicazione;
	
	@NumeroColonna(numero="26")
	private Boolean flgAbilAutorizzarePubblicazione;

	@NumeroColonna(numero="27")
	private Boolean flgAbilRespingerePubblicazione;
	
	@NumeroColonna(numero="28")
	private Boolean flgAbilEliminaContenuto;
	
	@NumeroColonna(numero="29")
	private Boolean flgAbilModificaContenuto;
	
	@NumeroColonna(numero="30")
	private Boolean flgContenutoEliminato;
	
	private String idSezione;

	
	private String recProtetto;
	private String flgAnnullato;
	private String flgValido;
	
	private String showContenutiTabellaButton;
	
	public String getIdContenuto() {
		return idContenuto;
	}

	public void setIdContenuto(String idContenuto) {
		this.idContenuto = idContenuto;
	}

	public String getTipoContenuto() {
		return tipoContenuto;
	}

	public void setTipoContenuto(String tipoContenuto) {
		this.tipoContenuto = tipoContenuto;
	}

	public String getTitoloContenuto() {
		return titoloContenuto;
	}

	public void setTitoloContenuto(String titoloContenuto) {
		this.titoloContenuto = titoloContenuto;
	}

	public String getHtmlContenuto() {
		return htmlContenuto;
	}

	public void setHtmlContenuto(String htmlContenuto) {
		this.htmlContenuto = htmlContenuto;
	}

	public Boolean getFlgMostraDatiCreazione() {
		return flgMostraDatiCreazione;
	}

	public void setFlgMostraDatiCreazione(Boolean flgMostraDatiCreazione) {
		this.flgMostraDatiCreazione = flgMostraDatiCreazione;
	}

	public String getHtmlUltimoAgg() {
		return htmlUltimoAgg;
	}

	public void setHtmlUltimoAgg(String htmlUltimoAgg) {
		this.htmlUltimoAgg = htmlUltimoAgg;
	}

	public Integer getNroRecTabella() {
		return nroRecTabella;
	}

	public void setNroRecTabella(Integer nroRecTabella) {
		this.nroRecTabella = nroRecTabella;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getDisplayFile() {
		return displayFile;
	}

	public void setDisplayFile(String displayFile) {
		this.displayFile = displayFile;
	}

	public Boolean getFlgFileFirmato() {
		return flgFileFirmato;
	}

	public void setFlgFileFirmato(Boolean flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}

	public Boolean getFlgFileFirmatoCades() {
		return flgFileFirmatoCades;
	}

	public void setFlgFileFirmatoCades(Boolean flgFileFirmatoCades) {
		this.flgFileFirmatoCades = flgFileFirmatoCades;
	}

	public Boolean getFlgFilePdf() {
		return flgFilePdf;
	}

	public void setFlgFilePdf(Boolean flgFilePdf) {
		this.flgFilePdf = flgFilePdf;
	}

	public String getMimeTypeFile() {
		return mimeTypeFile;
	}

	public void setMimeTypeFile(String mimeTypeFile) {
		this.mimeTypeFile = mimeTypeFile;
	}

	public String getImprontaFile() {
		return improntaFile;
	}

	public void setImprontaFile(String improntaFile) {
		this.improntaFile = improntaFile;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public String getEncodingImpronta() {
		return encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	public String getIdDocFile() {
		return idDocFile;
	}

	public void setIdDocFile(String idDocFile) {
		this.idDocFile = idDocFile;
	}

	public String getNroVersioneFile() {
		return nroVersioneFile;
	}

	public void setNroVersioneFile(String nroVersioneFile) {
		this.nroVersioneFile = nroVersioneFile;
	}

	public String getRecProtetto() {
		return recProtetto;
	}

	public void setRecProtetto(String recProtetto) {
		this.recProtetto = recProtetto;
	}

	public String getFlgAnnullato() {
		return flgAnnullato;
	}

	public void setFlgAnnullato(String flgAnnullato) {
		this.flgAnnullato = flgAnnullato;
	}

	public String getFlgValido() {
		return flgValido;
	}

	public void setFlgValido(String flgValido) {
		this.flgValido = flgValido;
	}


	public Boolean getFlgContenutoPubblicato() {
		return flgContenutoPubblicato;
	}

	public void setFlgContenutoPubblicato(Boolean flgContenutoPubblicato) {
		this.flgContenutoPubblicato = flgContenutoPubblicato;
	}

	public Boolean getFlgExportOpenData() {
		return flgExportOpenData;
	}

	public void setFlgExportOpenData(Boolean flgExportOpenData) {
		this.flgExportOpenData = flgExportOpenData;
	}

	public Date getDtInizioPubblicazione() {
		return dtInizioPubblicazione;
	}

	public void setDtInizioPubblicazione(Date dtInizioPubblicazione) {
		this.dtInizioPubblicazione = dtInizioPubblicazione;
	}

	public Date getDtFinePubblicazione() {
		return dtFinePubblicazione;
	}

	public void setDtFinePubblicazione(Date dtFinePubblicazione) {
		this.dtFinePubblicazione = dtFinePubblicazione;
	}

	public String getIdSezione() {
		return idSezione;
	}

	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getShowContenutiTabellaButton() {
		return showContenutiTabellaButton;
	}

	public void setShowContenutiTabellaButton(String showContenutiTabellaButton) {
		this.showContenutiTabellaButton = showContenutiTabellaButton;
	}

	public String getStatoRichiestaPubblicazione() {
		return statoRichiestaPubblicazione;
	}

	public void setStatoRichiestaPubblicazione(String statoRichiestaPubblicazione) {
		this.statoRichiestaPubblicazione = statoRichiestaPubblicazione;
	}

	public Boolean getFlgAbilAutorizzarePubblicazione() {
		return flgAbilAutorizzarePubblicazione;
	}

	public void setFlgAbilAutorizzarePubblicazione(Boolean flgAbilAutorizzarePubblicazione) {
		this.flgAbilAutorizzarePubblicazione = flgAbilAutorizzarePubblicazione;
	}

	public Boolean getFlgAbilRespingerePubblicazione() {
		return flgAbilRespingerePubblicazione;
	}

	public void setFlgAbilRespingerePubblicazione(Boolean flgAbilRespingerePubblicazione) {
		this.flgAbilRespingerePubblicazione = flgAbilRespingerePubblicazione;
	}

	public Boolean getFlgAbilEliminaContenuto() {
		return flgAbilEliminaContenuto;
	}

	public void setFlgAbilEliminaContenuto(Boolean flgAbilEliminaContenuto) {
		this.flgAbilEliminaContenuto = flgAbilEliminaContenuto;
	}

	public Boolean getFlgAbilModificaContenuto() {
		return flgAbilModificaContenuto;
	}

	public void setFlgAbilModificaContenuto(Boolean flgAbilModificaContenuto) {
		this.flgAbilModificaContenuto = flgAbilModificaContenuto;
	}

	public Boolean getFlgContenutoEliminato() {
		return flgContenutoEliminato;
	}

	public void setFlgContenutoEliminato(Boolean flgContenutoEliminato) {
		this.flgContenutoEliminato = flgContenutoEliminato;
	}

	
}
