/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DestinatariBean implements Serializable{

	@NumeroColonna(numero = "1")
	private String idRubrica;
	
	@NumeroColonna(numero = "2")
	private String codProvenienza;

	@NumeroColonna(numero = "3")
	private TipoDestinatario tipo;
	
	@NumeroColonna(numero = "4")
	private String denominazioneCognome;
	
	@NumeroColonna(numero = "5")
	private String nome;
	
	@NumeroColonna(numero = "6")
	private String codiceFiscale;
	
	@NumeroColonna(numero = "7")
	private String partitaIva;
	
	@NumeroColonna(numero = "17")
	private String mezzoTrasmissioneDestinatario;
	
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
    private String codToponimo;

    @NumeroColonna(numero = "25")
    private String toponimoIndirizzo;
    
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
    private String comune;
    
    @NumeroColonna(numero = "33")
	private String nomeComuneCitta;
    
    private String provincia;

    @NumeroColonna(numero = "34")
	private String stato;
    
    @NumeroColonna(numero = "35")
	private String nomeStato;
    
    @NumeroColonna(numero = "36")
	private String indirizzoMail;
	
    @NumeroColonna(numero = "39")
	private Flag perConoscenza;
	
	@NumeroColonna(numero = "45")
    private String zona;
    
    @NumeroColonna(numero = "46")
	private String complementoIndirizzo;
	
    @NumeroColonna(numero = "47")
    private String appendici;
    
    @NumeroColonna(numero = "49")
    private String tipoToponimo;
    
	@NumeroColonna(numero = "52")
	private String indirizzoDestinatario;

	@NumeroColonna(numero = "53")
	private String tipoSoggetto;

    @NumeroColonna(numero = "54")
    private String idSoggetto;
    
    @NumeroColonna(numero = "57")
   	private Flag assegna;
    
	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}

	public String getCodProvenienza() {
		return codProvenienza;
	}

	public void setCodProvenienza(String codProvenienza) {
		this.codProvenienza = codProvenienza;
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

	public String getCodToponimo() {
		return codToponimo;
	}

	public void setCodToponimo(String codToponimo) {
		this.codToponimo = codToponimo;
	}

	public String getToponimoIndirizzo() {
		return toponimoIndirizzo;
	}

	public void setToponimoIndirizzo(String toponimoIndirizzo) {
		this.toponimoIndirizzo = toponimoIndirizzo;
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

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getNomeComuneCitta() {
		return nomeComuneCitta;
	}

	public void setNomeComuneCitta(String nomeComuneCitta) {
		this.nomeComuneCitta = nomeComuneCitta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNomeStato() {
		return nomeStato;
	}

	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
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

	public String getTipoToponimo() {
		return tipoToponimo;
	}

	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}

	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}

	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
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

	public Flag getAssegna() {
		return assegna;
	}

	public void setAssegna(Flag assegna) {
		this.assegna = assegna;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
}
