/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RichiedenteXMLBean {
	
	@NumeroColonna(numero = "55")
	private String idSoggGiuridico;
	@NumeroColonna(numero = "5")
	private String denominazione;
	@NumeroColonna(numero = "6")
	private String cognome;
	@NumeroColonna(numero = "7")
	private String nome;
	@NumeroColonna(numero = "8")
	private String flgPersonaFisica;
	@NumeroColonna(numero = "9")
	private String codFiscale;
	@NumeroColonna(numero = "10")
	private String partitaIva;
	
	@NumeroColonna(numero = "71")
	private String statoSedeLegale;	
	@NumeroColonna(numero = "75")
	private String tipoToponimoSedeLegale;
	@NumeroColonna(numero = "61")
	private String toponimoSedeLegale;
	@NumeroColonna(numero = "62")
	private String civicoSedeLegale;
	@NumeroColonna(numero = "69")
	private String nomeComuneSedeLegale;
	@NumeroColonna(numero = "68")
	private String comuneSedeLegale;	
	@NumeroColonna(numero = "72")
	private String provinciaSedeLegale;
	@NumeroColonna(numero = "67")
	private String frazioneSedeLegale;
	@NumeroColonna(numero = "66")
	private String capSedeLegale;
	@NumeroColonna(numero = "73")
	private String zonaSedeLegale;
	@NumeroColonna(numero = "74")
	private String complementoIndirizzoSedeLegale;
	
	@NumeroColonna(numero = "38")
	private String statoRecapito;	
	@NumeroColonna(numero = "58")
	private String tipoToponimoRecapito;
	@NumeroColonna(numero = "28")
	private String toponimoRecapito;
	@NumeroColonna(numero = "29")
	private String civicoRecapito;
	@NumeroColonna(numero = "36")
	private String nomeComuneRecapito;
	@NumeroColonna(numero = "35")
	private String comuneRecapito;
	@NumeroColonna(numero = "47")
	private String provinciaRecapito;
	@NumeroColonna(numero = "34")
	private String frazioneRecapito;
	@NumeroColonna(numero = "33")
	private String capRecapito;
	@NumeroColonna(numero = "56")
	private String zonaRecapito;
	@NumeroColonna(numero = "57")
	private String complementoIndirizzoRecapito;
	
	@NumeroColonna(numero = "42")
	private String email;
	@NumeroColonna(numero = "40")
	private String telefono;
	
	
	public String getIdSoggGiuridico() {
		return idSoggGiuridico;
	}
	public void setIdSoggGiuridico(String idSoggGiuridico) {
		this.idSoggGiuridico = idSoggGiuridico;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getStatoSedeLegale() {
		return statoSedeLegale;
	}
	public void setStatoSedeLegale(String statoSedeLegale) {
		this.statoSedeLegale = statoSedeLegale;
	}
	public String getTipoToponimoSedeLegale() {
		return tipoToponimoSedeLegale;
	}
	public void setTipoToponimoSedeLegale(String tipoToponimoSedeLegale) {
		this.tipoToponimoSedeLegale = tipoToponimoSedeLegale;
	}
	public String getToponimoSedeLegale() {
		return toponimoSedeLegale;
	}
	public void setToponimoSedeLegale(String toponimoSedeLegale) {
		this.toponimoSedeLegale = toponimoSedeLegale;
	}
	public String getCivicoSedeLegale() {
		return civicoSedeLegale;
	}
	public void setCivicoSedeLegale(String civicoSedeLegale) {
		this.civicoSedeLegale = civicoSedeLegale;
	}
	public String getNomeComuneSedeLegale() {
		return nomeComuneSedeLegale;
	}
	public void setNomeComuneSedeLegale(String nomeComuneSedeLegale) {
		this.nomeComuneSedeLegale = nomeComuneSedeLegale;
	}
	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}
	public void setComuneSedeLegale(String comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}
	public String getProvinciaSedeLegale() {
		return provinciaSedeLegale;
	}
	public void setProvinciaSedeLegale(String provinciaSedeLegale) {
		this.provinciaSedeLegale = provinciaSedeLegale;
	}
	public String getFrazioneSedeLegale() {
		return frazioneSedeLegale;
	}
	public void setFrazioneSedeLegale(String frazioneSedeLegale) {
		this.frazioneSedeLegale = frazioneSedeLegale;
	}
	public String getCapSedeLegale() {
		return capSedeLegale;
	}
	public void setCapSedeLegale(String capSedeLegale) {
		this.capSedeLegale = capSedeLegale;
	}
	public String getZonaSedeLegale() {
		return zonaSedeLegale;
	}
	public void setZonaSedeLegale(String zonaSedeLegale) {
		this.zonaSedeLegale = zonaSedeLegale;
	}
	public String getComplementoIndirizzoSedeLegale() {
		return complementoIndirizzoSedeLegale;
	}
	public void setComplementoIndirizzoSedeLegale(
			String complementoIndirizzoSedeLegale) {
		this.complementoIndirizzoSedeLegale = complementoIndirizzoSedeLegale;
	}
	public String getStatoRecapito() {
		return statoRecapito;
	}
	public void setStatoRecapito(String statoRecapito) {
		this.statoRecapito = statoRecapito;
	}
	public String getTipoToponimoRecapito() {
		return tipoToponimoRecapito;
	}
	public void setTipoToponimoRecapito(String tipoToponimoRecapito) {
		this.tipoToponimoRecapito = tipoToponimoRecapito;
	}
	public String getToponimoRecapito() {
		return toponimoRecapito;
	}
	public void setToponimoRecapito(String toponimoRecapito) {
		this.toponimoRecapito = toponimoRecapito;
	}
	public String getCivicoRecapito() {
		return civicoRecapito;
	}
	public void setCivicoRecapito(String civicoRecapito) {
		this.civicoRecapito = civicoRecapito;
	}
	public String getNomeComuneRecapito() {
		return nomeComuneRecapito;
	}
	public void setNomeComuneRecapito(String nomeComuneRecapito) {
		this.nomeComuneRecapito = nomeComuneRecapito;
	}
	public String getComuneRecapito() {
		return comuneRecapito;
	}
	public void setComuneRecapito(String comuneRecapito) {
		this.comuneRecapito = comuneRecapito;
	}
	public String getProvinciaRecapito() {
		return provinciaRecapito;
	}
	public void setProvinciaRecapito(String provinciaRecapito) {
		this.provinciaRecapito = provinciaRecapito;
	}
	public String getFrazioneRecapito() {
		return frazioneRecapito;
	}
	public void setFrazioneRecapito(String frazioneRecapito) {
		this.frazioneRecapito = frazioneRecapito;
	}
	public String getCapRecapito() {
		return capRecapito;
	}
	public void setCapRecapito(String capRecapito) {
		this.capRecapito = capRecapito;
	}
	public String getZonaRecapito() {
		return zonaRecapito;
	}
	public void setZonaRecapito(String zonaRecapito) {
		this.zonaRecapito = zonaRecapito;
	}
	public String getComplementoIndirizzoRecapito() {
		return complementoIndirizzoRecapito;
	}
	public void setComplementoIndirizzoRecapito(String complementoIndirizzoRecapito) {
		this.complementoIndirizzoRecapito = complementoIndirizzoRecapito;
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
	public String getFlgPersonaFisica() {
		return flgPersonaFisica;
	}
	public void setFlgPersonaFisica(String flgPersonaFisica) {
		this.flgPersonaFisica = flgPersonaFisica;
	}		
	
}