/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DestinatariOutBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idRubrica;
	
	@NumeroColonna(numero = "2")
	private String uriFileExcel;
	
	@NumeroColonna(numero = "3")
	private TipoDestinatario tipo;
	
	@NumeroColonna(numero = "4")
	private String denominazioneCognome;
	
	@NumeroColonna(numero = "5")
	private String nome;
	
	@NumeroColonna(numero = "6")
	private String codiceFiscale;
	
	// Dati del mezzo di trasmissione del destinatario
	@NumeroColonna(numero = "17")
	private String mezzoTrasmissioneDestinatario;

	@NumeroColonna(numero = "18")
	private String descrizioneMezzoTrasmissioneDestinatario;

	@NumeroColonna(numero = "20")
    @TipoData(tipo=Tipo.DATA_SENZA_ORA)
    private Date dataRaccomandataDestinatario;

	@NumeroColonna(numero = "21")
	private String nroRaccomandataDestinatario;
	
	@NumeroColonna(numero = "22")
    @TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataNotificaDestinatario;

    @NumeroColonna(numero = "23")
    private String nroNotificaDestinatario;

    @NumeroColonna(numero = "24")
    private String ciToponimo;

    @NumeroColonna(numero = "25")
    private String indirizzo;
    
    @NumeroColonna(numero = "26")
    private String frazione;
    
    @NumeroColonna(numero = "27")
    private String civico;
    
    @NumeroColonna(numero = "28")
	private String interno;
    
    @NumeroColonna(numero = "29")
	private String scala;
    
    @NumeroColonna(numero = "30")
	private String piano;
    
    @NumeroColonna(numero = "31")
	private String cap;
	
    @NumeroColonna(numero = "32")
    private String codIstatComune;
    
    @NumeroColonna(numero = "33")
	private String comune;
    
    @NumeroColonna(numero = "34")
	private String codIstatStato;
    
    @NumeroColonna(numero = "35")
	private String stato;
    
    @NumeroColonna(numero = "39")
   	private Flag perConoscenza;   	
	
    @NumeroColonna(numero = "45")
    private String zona;
    
    @NumeroColonna(numero = "46")
	private String complementoIndirizzo;
	
    @NumeroColonna(numero = "47")
    private String appendici;
    
    /*************************
     *   Dati del soggetto   *
     *************************/
    @NumeroColonna(numero = "48")
	private String codRapido;
	
	@NumeroColonna(numero = "49")
	private String tipoDestinatario;
	
	@NumeroColonna(numero = "50")
	private String tipoSoggetto;

	@NumeroColonna(numero = "51")
	private String idSoggetto;
	/*************************/
        
	@NumeroColonna(numero = "52")
	private String indirizzoDestinatario;

	@NumeroColonna(numero = "53")
	private String descrizioneIndirizzo;

    @NumeroColonna(numero = "54")
    private String tipoToponimo;

    @NumeroColonna(numero = "55")
   	private Flag flgAssegnaAlDestinatario;   	
	
    @NumeroColonna(numero = "56")
   	private String indirizzoMail;   	
    
    @NumeroColonna(numero = "57")
    private String displayFileNameExcel;   	
	
	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}

	public TipoDestinatario getTipo() {
		return tipo;
	}

	public void setTipo(TipoDestinatario tipo) {
		this.tipo = tipo;
	}

	public String getDenominazioneCognome() {
		return denominazioneCognome;
	}

	public void setDenominazioneCognome(String denominazioneCognome) {
		this.denominazioneCognome = denominazioneCognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getMezzoTrasmissioneDestinatario() {
		return mezzoTrasmissioneDestinatario;
	}

	public void setMezzoTrasmissioneDestinatario(
			String mezzoTrasmissioneDestinatario) {
		this.mezzoTrasmissioneDestinatario = mezzoTrasmissioneDestinatario;
	}

	public String getDescrizioneMezzoTrasmissioneDestinatario() {
		return descrizioneMezzoTrasmissioneDestinatario;
	}

	public void setDescrizioneMezzoTrasmissioneDestinatario(
			String descrizioneMezzoTrasmissioneDestinatario) {
		this.descrizioneMezzoTrasmissioneDestinatario = descrizioneMezzoTrasmissioneDestinatario;
	}

	public Date getDataRaccomandataDestinatario() {
		return dataRaccomandataDestinatario;
	}

	public void setDataRaccomandataDestinatario(Date dataRaccomandataDestinatario) {
		this.dataRaccomandataDestinatario = dataRaccomandataDestinatario;
	}

	public String getNroRaccomandataDestinatario() {
		return nroRaccomandataDestinatario;
	}

	public void setNroRaccomandataDestinatario(String nroRaccomandataDestinatario) {
		this.nroRaccomandataDestinatario = nroRaccomandataDestinatario;
	}

	public Date getDataNotificaDestinatario() {
		return dataNotificaDestinatario;
	}

	public void setDataNotificaDestinatario(Date dataNotificaDestinatario) {
		this.dataNotificaDestinatario = dataNotificaDestinatario;
	}

	public String getNroNotificaDestinatario() {
		return nroNotificaDestinatario;
	}

	public void setNroNotificaDestinatario(String nroNotificaDestinatario) {
		this.nroNotificaDestinatario = nroNotificaDestinatario;
	}

	public String getCiToponimo() {
		return ciToponimo;
	}

	public void setCiToponimo(String ciToponimo) {
		this.ciToponimo = ciToponimo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getFrazione() {
		return frazione;
	}

	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCodIstatStato() {
		return codIstatStato;
	}

	public void setCodIstatStato(String codIstatStato) {
		this.codIstatStato = codIstatStato;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Flag getPerConoscenza() {
		return perConoscenza;
	}

	public void setPerConoscenza(Flag perConoscenza) {
		this.perConoscenza = perConoscenza;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getComplementoIndirizzo() {
		return complementoIndirizzo;
	}

	public void setComplementoIndirizzo(String complementoIndirizzo) {
		this.complementoIndirizzo = complementoIndirizzo;
	}

	public String getAppendici() {
		return appendici;
	}

	public void setAppendici(String appendici) {
		this.appendici = appendici;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}

	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
	}

	public String getDescrizioneIndirizzo() {
		return descrizioneIndirizzo;
	}

	public void setDescrizioneIndirizzo(String descrizioneIndirizzo) {
		this.descrizioneIndirizzo = descrizioneIndirizzo;
	}

	public String getTipoToponimo() {
		return tipoToponimo;
	}

	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}

	public Flag getFlgAssegnaAlDestinatario() {
		return flgAssegnaAlDestinatario;
	}

	public void setFlgAssegnaAlDestinatario(Flag flgAssegnaAlDestinatario) {
		this.flgAssegnaAlDestinatario = flgAssegnaAlDestinatario;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}

	public String getUriFileExcel() {
		return uriFileExcel;
	}

	public void setUriFileExcel(String uriFileExcel) {
		this.uriFileExcel = uriFileExcel;
	}

	public String getDisplayFileNameExcel() {
		return displayFileNameExcel;
	}

	public void setDisplayFileNameExcel(String displayFileNameExcel) {
		this.displayFileNameExcel = displayFileNameExcel;
	}
	
}
