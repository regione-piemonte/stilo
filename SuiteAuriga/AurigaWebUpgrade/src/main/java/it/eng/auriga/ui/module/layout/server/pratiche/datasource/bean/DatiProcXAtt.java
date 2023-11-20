/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class DatiProcXAtt implements Serializable {
		
	@XmlVariabile(nome="GPA_ID_UL", tipo=TipoVariabile.SEMPLICE)
	private String idUnitaLocale;	
	@XmlVariabile(nome="GPA_NOME_UL", tipo=TipoVariabile.SEMPLICE)
	private String nomeUnitaLocale;	
	@XmlVariabile(nome="GPA_ID_GEOM_AREA_INTERV_VIA", tipo=TipoVariabile.SEMPLICE)
	private String idUbicazioneAreaIntervento;
	@XmlVariabile(nome="ID_UD_ISTANZA", tipo=TipoVariabile.SEMPLICE)
	private String idUdIstanza;
	@XmlVariabile(nome="URI_RIC_AVVIO", tipo=TipoVariabile.SEMPLICE)
	private String uriRicAvvio;
	@XmlVariabile(nome="EMAIL_PROPONENTE", tipo=TipoVariabile.SEMPLICE)
	private String emailProponente;	
	@XmlVariabile(nome="NRO_PRATICA", tipo=TipoVariabile.SEMPLICE)
	private String nroPratica;
	@XmlVariabile(nome="ANNO_PRATICA", tipo=TipoVariabile.SEMPLICE)
	private String annoPratica;
	@XmlVariabile(nome="TIPO_PROCEDIMENTO", tipo=TipoVariabile.SEMPLICE)
	private String tipoProcedimento;
	@XmlVariabile(nome="NRO_SERIE_TIPO_PROC", tipo=TipoVariabile.SEMPLICE)
	private String nroSerieTipoProc;
	@XmlVariabile(nome="ANNO_SERIE_TIPO_PROC", tipo=TipoVariabile.SEMPLICE)
	private String annoSerieTipoProc;
	@XmlVariabile(nome="NRO_REGISTRO_TIPO_PROC", tipo=TipoVariabile.SEMPLICE)
	private String nroRegistroTipoProc;
	@XmlVariabile(nome="ANNO_REGISTRO_TIPO_PROC", tipo=TipoVariabile.SEMPLICE)
	private String annoRegistroTipoProc;
	@XmlVariabile(nome="DT_AVVIO_PROC", tipo=TipoVariabile.SEMPLICE)
	private String dtAvvioProc;
	@XmlVariabile(nome="NRO_PROT_AVVIO", tipo=TipoVariabile.SEMPLICE)
	private String nroProtAvvio;	
	@XmlVariabile(nome="#IdDefProcFlow", tipo=TipoVariabile.SEMPLICE)
	private String idDefProcFlow;	
	@XmlVariabile(nome="#IdInstProcFlow", tipo=TipoVariabile.SEMPLICE)
	private String idInstProcFlow;		
	@XmlVariabile(nome="#DesUOProponente", tipo=TipoVariabile.SEMPLICE)
	private String desUoProponente;		
	@XmlVariabile(nome="#NomeResponsabileUOProponente", tipo=TipoVariabile.SEMPLICE)
	private String nomeResponsabileUoProponente;	
	@XmlVariabile(nome="#RifAttoInOrganigramma", tipo=TipoVariabile.SEMPLICE)
	private String rifAttoInOrganigramma; 
	@XmlVariabile(nome="ListaNextTask", tipo=TipoVariabile.SEMPLICE)
	private String listaNextTask;
	@XmlVariabile(nome="AbilitaCallApplTitoliEdilizi", tipo=TipoVariabile.SEMPLICE)
	private Boolean abilitaCallApplTitoliEdilizi;
	@XmlVariabile(nome="AnnoProtocolloIstanza", tipo=TipoVariabile.SEMPLICE)
	private String annoProtocolloIstanza;
	@XmlVariabile(nome="NroProtocolloIstanza", tipo=TipoVariabile.SEMPLICE)
	private String nroProtocolloIstanza;
	@XmlVariabile(nome="IdDocIstanza", tipo=TipoVariabile.SEMPLICE)
	private String idDocIstanza;			
	
	public String getIdUnitaLocale() {
		return idUnitaLocale;
	}
	public void setIdUnitaLocale(String idUnitaLocale) {
		this.idUnitaLocale = idUnitaLocale;
	}
	public String getNomeUnitaLocale() {
		return nomeUnitaLocale;
	}
	public void setNomeUnitaLocale(String nomeUnitaLocale) {
		this.nomeUnitaLocale = nomeUnitaLocale;
	}
	public String getIdUbicazioneAreaIntervento() {
		return idUbicazioneAreaIntervento;
	}
	public void setIdUbicazioneAreaIntervento(String idUbicazioneAreaIntervento) {
		this.idUbicazioneAreaIntervento = idUbicazioneAreaIntervento;
	}
	public String getIdUdIstanza() {
		return idUdIstanza;
	}
	public void setIdUdIstanza(String idUdIstanza) {
		this.idUdIstanza = idUdIstanza;
	}	
	public String getEmailProponente() {
		return emailProponente;
	}
	public void setEmailProponente(String emailProponente) {
		this.emailProponente = emailProponente;
	}
	public String getUriRicAvvio() {
		return uriRicAvvio;
	}
	public void setUriRicAvvio(String uriRicAvvio) {
		this.uriRicAvvio = uriRicAvvio;
	}
	public String getNroPratica() {
		return nroPratica;
	}
	public void setNroPratica(String nroPratica) {
		this.nroPratica = nroPratica;
	}
	public String getAnnoPratica() {
		return annoPratica;
	}
	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getNroSerieTipoProc() {
		return nroSerieTipoProc;
	}
	public void setNroSerieTipoProc(String nroSerieTipoProc) {
		this.nroSerieTipoProc = nroSerieTipoProc;
	}
	public String getAnnoSerieTipoProc() {
		return annoSerieTipoProc;
	}
	public void setAnnoSerieTipoProc(String annoSerieTipoProc) {
		this.annoSerieTipoProc = annoSerieTipoProc;
	}
	public String getNroRegistroTipoProc() {
		return nroRegistroTipoProc;
	}
	public void setNroRegistroTipoProc(String nroRegistroTipoProc) {
		this.nroRegistroTipoProc = nroRegistroTipoProc;
	}
	public String getAnnoRegistroTipoProc() {
		return annoRegistroTipoProc;
	}
	public void setAnnoRegistroTipoProc(String annoRegistroTipoProc) {
		this.annoRegistroTipoProc = annoRegistroTipoProc;
	}
	public String getDtAvvioProc() {
		return dtAvvioProc;
	}
	public void setDtAvvioProc(String dtAvvioProc) {
		this.dtAvvioProc = dtAvvioProc;
	}
	public String getNroProtAvvio() {
		return nroProtAvvio;
	}
	public void setNroProtAvvio(String nroProtAvvio) {
		this.nroProtAvvio = nroProtAvvio;
	}	
	public String getIdDefProcFlow() {
		return idDefProcFlow;
	}
	public void setIdDefProcFlow(String idDefProcFlow) {
		this.idDefProcFlow = idDefProcFlow;
	}
	public String getIdInstProcFlow() {
		return idInstProcFlow;
	}
	public void setIdInstProcFlow(String idInstProcFlow) {
		this.idInstProcFlow = idInstProcFlow;
	}
	public String getDesUoProponente() {
		return desUoProponente;
	}
	public void setDesUoProponente(String desUoProponente) {
		this.desUoProponente = desUoProponente;
	}
	public String getNomeResponsabileUoProponente() {
		return nomeResponsabileUoProponente;
	}
	public void setNomeResponsabileUoProponente(
			String nomeResponsabileUoProponente) {
		this.nomeResponsabileUoProponente = nomeResponsabileUoProponente;
	}
	public String getRifAttoInOrganigramma() {
		return rifAttoInOrganigramma;
	}
	public void setRifAttoInOrganigramma(String rifAttoInOrganigramma) {
		this.rifAttoInOrganigramma = rifAttoInOrganigramma;
	}
	public String getListaNextTask() {
		return listaNextTask;
	}
	public void setListaNextTask(String listaNextTask) {
		this.listaNextTask = listaNextTask;
	}
	public Boolean getAbilitaCallApplTitoliEdilizi() {
		return abilitaCallApplTitoliEdilizi;
	}
	public void setAbilitaCallApplTitoliEdilizi(Boolean abilitaCallApplTitoliEdilizi) {
		this.abilitaCallApplTitoliEdilizi = abilitaCallApplTitoliEdilizi;
	}
	public String getAnnoProtocolloIstanza() {
		return annoProtocolloIstanza;
	}
	public void setAnnoProtocolloIstanza(String annoProtocolloIstanza) {
		this.annoProtocolloIstanza = annoProtocolloIstanza;
	}
	public String getNroProtocolloIstanza() {
		return nroProtocolloIstanza;
	}
	public void setNroProtocolloIstanza(String nroProtocolloIstanza) {
		this.nroProtocolloIstanza = nroProtocolloIstanza;
	}
	public String getIdDocIstanza() {
		return idDocIstanza;
	}
	public void setIdDocIstanza(String idDocIstanza) {
		this.idDocIstanza = idDocIstanza;
	}
	
}