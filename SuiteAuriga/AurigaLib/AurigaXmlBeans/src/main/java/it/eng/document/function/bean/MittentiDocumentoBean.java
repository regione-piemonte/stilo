/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class MittentiDocumentoBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String idRubrica;
	
	@NumeroColonna(numero = "2")
	private String codProvenienza;

	@NumeroColonna(numero = "3")
	private TipoMittente tipo;

	@NumeroColonna(numero = "4")
	private String denominazioneCognome;

	@NumeroColonna(numero = "5")
	private String nome;

	@NumeroColonna(numero = "6")
	private String codiceFiscale;
	
	@NumeroColonna(numero = "7")
	private String partitaIva;

	private String provCIUo;

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

	@NumeroColonna(numero = "45")
	private String zona;

	@NumeroColonna(numero = "46")
	private String complementoIndirizzo;

	@NumeroColonna(numero = "47")
	private String appendici;

	@NumeroColonna(numero = "49")
	private String tipoToponimo;

	@NumeroColonna(numero = "50")
	private String email;
		
	@NumeroColonna(numero = "51")
	private String telefono;
		
	@NumeroColonna(numero = "52")
	private String username;

	@NumeroColonna(numero = "53")
	private String tipoSoggetto;

    @NumeroColonna(numero = "54")
    private String idSoggetto;
    
	@NumeroColonna(numero = "55")
	private Flag flgAssegnaAlMittente;   	
		
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

	public TipoMittente getTipo() {
		return tipo;
	}

	public void setTipo(TipoMittente tipo) {
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

	public String getProvCIUo() {
		return provCIUo;
	}

	public void setProvCIUo(String provCIUo) {
		this.provCIUo = provCIUo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Flag getFlgAssegnaAlMittente() {
		return flgAssegnaAlMittente;
	}

	public void setFlgAssegnaAlMittente(Flag flgAssegnaAlMittente) {
		this.flgAssegnaAlMittente = flgAssegnaAlMittente;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

}
