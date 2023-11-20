/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class InvioRaccomandateBean {
	
	private String id_busta;
	private String idUd;
	private String tipo;
	private String idPoste;
	private String numeroProtocollo;
	private Date dataProtocollo;
	private String annoProtocollo;
	private Date dataInvio;
	private String statoLavorazioneRacc;
	private Date dataAggiornamentoStato;
	private String datiMittente;
	private String destinatario;
	private Double importoTotale;
	private Double imponibile;
	private String iva;
	private String nroRaccomandata;
	private String nroLettera;
	private Date dataRaccomandata;
	private String idRicevuta;
	private Date epm_ts;
	private String epm_key;
	private Boolean isDaPostalizzare;
	
	public String getId_busta() {
		return id_busta;
	}
	public void setId_busta(String id_busta) {
		this.id_busta = id_busta;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getIdPoste() {
		return idPoste;
	}
	public void setIdPoste(String idPoste) {
		this.idPoste = idPoste;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getStatoLavorazioneRacc() {
		return statoLavorazioneRacc;
	}
	public void setStatoLavorazioneRacc(String statoLavorazioneRacc) {
		this.statoLavorazioneRacc = statoLavorazioneRacc;
	}
	public Date getDataAggiornamentoStato() {
		return dataAggiornamentoStato;
	}
	public void setDataAggiornamentoStato(Date dataAggiornamentoStato) {
		this.dataAggiornamentoStato = dataAggiornamentoStato;
	}
	public String getDatiMittente() {
		return datiMittente;
	}
	public void setDatiMittente(String datiMittente) {
		this.datiMittente = datiMittente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public Double getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(Double importoTotale) {
		this.importoTotale = importoTotale;
	}
	public Double getImponibile() {
		return imponibile;
	}
	public void setImponibile(Double imponibile) {
		this.imponibile = imponibile;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getNroRaccomandata() {
		return nroRaccomandata;
	}
	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}
	public String getNroLettera() {
		return nroLettera;
	}
	public void setNroLettera(String nroLettera) {
		this.nroLettera = nroLettera;
	}
	public Date getDataRaccomandata() {
		return dataRaccomandata;
	}
	public void setDataRaccomandata(Date dataRaccomandata) {
		this.dataRaccomandata = dataRaccomandata;
	}
	public String getIdRicevuta() {
		return idRicevuta;
	}
	public void setIdRicevuta(String idRicevuta) {
		this.idRicevuta = idRicevuta;
	}
	public Date getEpm_ts() {
		return epm_ts;
	}
	public void setEpm_ts(Date epm_ts) {
		this.epm_ts = epm_ts;
	}
	public String getEpm_key() {
		return epm_key;
	}
	public void setEpm_key(String epm_key) {
		this.epm_key = epm_key;
	}
	public Boolean getIsDaPostalizzare() {
		return isDaPostalizzare;
	}
	public void setIsDaPostalizzare(Boolean isDaPostalizzare) {
		this.isDaPostalizzare = isDaPostalizzare;
	}	
	
}
