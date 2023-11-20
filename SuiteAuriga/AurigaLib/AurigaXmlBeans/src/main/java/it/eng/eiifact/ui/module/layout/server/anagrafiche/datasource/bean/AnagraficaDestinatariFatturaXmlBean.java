/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class AnagraficaDestinatariFatturaXmlBean {

	@NumeroColonna(numero="1")
	private String idSoggetto;

	@NumeroColonna(numero="2")
	private String nominativoRiferimentoCliente;

	@NumeroColonna(numero="25")
	private String descApplicazione;

	@TipoData(tipo = Tipo.DATA)
	@NumeroColonna(numero="28")
	private Date tsIns;

	@TipoData(tipo = Tipo.DATA)
	@NumeroColonna(numero="28")
	private Date tsInsDestinatario;
	
	@NumeroColonna(numero="29")
	private String uteIns;
	
	@TipoData(tipo = Tipo.DATA)
	@NumeroColonna(numero="30")
	private Date tsLastUpd;
	
	@NumeroColonna(numero="31")
	private String uteLastUpd;
	
	@NumeroColonna(numero="32")
	private Integer flgDiSistema;
	
	@NumeroColonna(numero="33")
	private Integer flgValido;
	
	@NumeroColonna(numero="40")
	private String email;
	
	@NumeroColonna(numero="44")
	private String tel;
	
	@NumeroColonna(numero="53")
	private Boolean flgSelXFinalita;
	
	@NumeroColonna(numero="70")
	private String idDestinatario;

	@NumeroColonna(numero="71")
	private String listaDescSocietaDestinatario;
	
	@NumeroColonna(numero="72")
	private String codiceCliente;

	@NumeroColonna(numero="73")
	private String denominazioneCliente;

	@NumeroColonna(numero="74")
	private String partitaIvaCodiceFiscale;
	
	@NumeroColonna(numero="75")
	private String idCliente;

	@NumeroColonna(numero="76")
	private String listaDescCanaleInvioDestinatario; 

	@NumeroColonna(numero="77")
	private String metodoRegistrazione;

	@NumeroColonna(numero="78")
	private String listaDescSocietaCliente;

    @NumeroColonna(numero="79")
    private String codiceCommittente;
   
    @NumeroColonna(numero="80")
    private String descrizioneCommerciale;
   
    @NumeroColonna(numero="81")
    private String descrizioneAgente;
	
	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getNominativoRiferimentoCliente() {
		return nominativoRiferimentoCliente;
	}

	public void setNominativoRiferimentoCliente(String nominativoRiferimentoCliente) {
		this.nominativoRiferimentoCliente = nominativoRiferimentoCliente;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public String getUteIns() {
		return uteIns;
	}

	public void setUteIns(String uteIns) {
		this.uteIns = uteIns;
	}

	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	public String getUteLastUpd() {
		return uteLastUpd;
	}

	public void setUteLastUpd(String uteLastUpd) {
		this.uteLastUpd = uteLastUpd;
	}

	public Integer getFlgDiSistema() {
		return flgDiSistema;
	}

	public void setFlgDiSistema(Integer flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}

	public Integer getFlgValido() {
		return flgValido;
	}

	public void setFlgValido(Integer flgValido) {
		this.flgValido = flgValido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String destinatario) {
		this.idDestinatario = destinatario;
	}

	public String getListaDescSocietaDestinatario() {
		return listaDescSocietaDestinatario;
	}

	public void setListaDescSocietaDestinatario(String listaDescSocietaDestinatario) {
		this.listaDescSocietaDestinatario = listaDescSocietaDestinatario;
	}

	public String getCodiceCliente() {
		return codiceCliente;
	}

	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}

	public String getDenominazioneCliente() {
		return denominazioneCliente;
	}

	public void setDenominazioneCliente(String denominazioneCliente) {
		this.denominazioneCliente = denominazioneCliente;
	}

	public String getPartitaIvaCodiceFiscale() {
		return partitaIvaCodiceFiscale;
	}

	public void setPartitaIvaCodiceFiscale(String partitaIvaCodiceFiscale) {
		this.partitaIvaCodiceFiscale = partitaIvaCodiceFiscale;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getListaDescCanaleInvioDestinatario() {
		return listaDescCanaleInvioDestinatario;
	}

	public void setListaDescCanaleInvioDestinatario(String listaDescCanaleInvioDestinatario) {
		this.listaDescCanaleInvioDestinatario = listaDescCanaleInvioDestinatario;
	}

	public String getMetodoRegistrazione() {
		return metodoRegistrazione;
	}

	public void setMetodoRegistrazione(String metodoRegistrazione) {
		this.metodoRegistrazione = metodoRegistrazione;
	}

	public Date getTsInsDestinatario() {
		return tsInsDestinatario;
	}

	public void setTsInsDestinatario(Date tsInsDestinatario) {
		this.tsInsDestinatario = tsInsDestinatario;
	}

	public String getListaDescSocietaCliente() {
		return listaDescSocietaCliente;
	}

	public void setListaDescSocietaCliente(String listaDescSocietaCliente) {
		this.listaDescSocietaCliente = listaDescSocietaCliente;
	}

	public String getDescApplicazione() {
		return descApplicazione;
	}

	public void setDescApplicazione(String descApplicazione) {
		this.descApplicazione = descApplicazione;
	}

	public String getCodiceCommittente() {
		return codiceCommittente;
	}

	public void setCodiceCommittente(String codiceCommittente) {
		this.codiceCommittente = codiceCommittente;
	}

	public String getDescrizioneCommerciale() {
		return descrizioneCommerciale;
	}

	public void setDescrizioneCommerciale(String descrizioneCommerciale) {
		this.descrizioneCommerciale = descrizioneCommerciale;
	}

	public String getDescrizioneAgente() {
		return descrizioneAgente;
	}

	public void setDescrizioneAgente(String descrizioneAgente) {
		this.descrizioneAgente = descrizioneAgente;
	}

}
