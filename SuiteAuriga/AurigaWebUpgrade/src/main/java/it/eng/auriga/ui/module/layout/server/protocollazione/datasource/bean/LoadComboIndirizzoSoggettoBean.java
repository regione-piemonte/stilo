/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class LoadComboIndirizzoSoggettoBean extends VisualBean{

    
	@NumeroColonna(numero="1")
	private String indirizzoDisplay;
	
	@NumeroColonna(numero="2")
	private String codiceTipoIndirizzo;
	
	@NumeroColonna(numero="3")
	private String tipoIndirizzo;

	@NumeroColonna(numero="4")
	private String dettaglioTipoIndirizzo;

	@NumeroColonna(numero="5")
	private String cardinalitaTipoIndirizzo;
	
	@NumeroColonna(numero="6")
	private String descrizioneCompletaIndirizzo;
	
	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataValidoDal;
	
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataValidoFinoAl;
	
	@NumeroColonna(numero="9")
	private String toponimo;
	
	@NumeroColonna(numero="10")
	private String indirizzo;
	
	@NumeroColonna(numero="11")
	private String civico;
	
	@NumeroColonna(numero="12")
	private String interno;	
	
	@NumeroColonna(numero="13")
	private String scala;	
	
	@NumeroColonna(numero="14")
	private String piano;	
	
	@NumeroColonna(numero="15")
	private String cap;
	
	@NumeroColonna(numero="16")
	private String frazione;

	@NumeroColonna(numero="17")
	private String codIstatComune;
	
	@NumeroColonna(numero="18")
	private String comune;
	
	@NumeroColonna(numero="19")
	private String codIstatStato;
	
	@NumeroColonna(numero="20")
	private String stato;
	
	@NumeroColonna(numero="21")
	private String provincia;
	
	@NumeroColonna(numero="22")
	private String zona;

    @NumeroColonna(numero="23")
	private String complementoIndirizzo;
	
	@NumeroColonna(numero="24")
	private String tipoToponimo;
	
	@NumeroColonna(numero="25")
	private String appendici;
	
	@NumeroColonna(numero="26")
	private String idIndirizzo;

	public String getIndirizzoDisplay() {
		return indirizzoDisplay;
	}

	public String getCodiceTipoIndirizzo() {
		return codiceTipoIndirizzo;
	}

	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}

	public String getDettaglioTipoIndirizzo() {
		return dettaglioTipoIndirizzo;
	}

	public String getCardinalitaTipoIndirizzo() {
		return cardinalitaTipoIndirizzo;
	}

	public String getDescrizioneCompletaIndirizzo() {
		return descrizioneCompletaIndirizzo;
	}

	public Date getDataValidoDal() {
		return dataValidoDal;
	}

	public Date getDataValidoFinoAl() {
		return dataValidoFinoAl;
	}

	public String getToponimo() {
		return toponimo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public String getInterno() {
		return interno;
	}

	public String getScala() {
		return scala;
	}

	public String getPiano() {
		return piano;
	}

	public String getCap() {
		return cap;
	}

	public String getFrazione() {
		return frazione;
	}

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public String getComune() {
		return comune;
	}

	public String getCodIstatStato() {
		return codIstatStato;
	}

	public String getStato() {
		return stato;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getZona() {
		return zona;
	}

	public String getComplementoIndirizzo() {
		return complementoIndirizzo;
	}

	public String getTipoToponimo() {
		return tipoToponimo;
	}

	public String getAppendici() {
		return appendici;
	}

	public String getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIndirizzoDisplay(String indirizzoDisplay) {
		this.indirizzoDisplay = indirizzoDisplay;
	}

	public void setCodiceTipoIndirizzo(String codiceTipoIndirizzo) {
		this.codiceTipoIndirizzo = codiceTipoIndirizzo;
	}

	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}

	public void setDettaglioTipoIndirizzo(String dettaglioTipoIndirizzo) {
		this.dettaglioTipoIndirizzo = dettaglioTipoIndirizzo;
	}

	public void setCardinalitaTipoIndirizzo(String cardinalitaTipoIndirizzo) {
		this.cardinalitaTipoIndirizzo = cardinalitaTipoIndirizzo;
	}

	public void setDescrizioneCompletaIndirizzo(String descrizioneCompletaIndirizzo) {
		this.descrizioneCompletaIndirizzo = descrizioneCompletaIndirizzo;
	}

	public void setDataValidoDal(Date dataValidoDal) {
		this.dataValidoDal = dataValidoDal;
	}

	public void setDataValidoFinoAl(Date dataValidoFinoAl) {
		this.dataValidoFinoAl = dataValidoFinoAl;
	}

	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public void setCodIstatStato(String codIstatStato) {
		this.codIstatStato = codIstatStato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public void setComplementoIndirizzo(String complementoIndirizzo) {
		this.complementoIndirizzo = complementoIndirizzo;
	}

	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}

	public void setAppendici(String appendici) {
		this.appendici = appendici;
	}

	public void setIdIndirizzo(String idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	
	
	
	
	
}
