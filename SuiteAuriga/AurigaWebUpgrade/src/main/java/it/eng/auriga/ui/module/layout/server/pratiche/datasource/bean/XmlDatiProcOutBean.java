/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


public class XmlDatiProcOutBean {
	 
	@XmlVariabile(nome="#IdProcessType", tipo=TipoVariabile.SEMPLICE)
	private String tipoProcedimento;
	@XmlVariabile(nome="#NomeProcessType", tipo=TipoVariabile.SEMPLICE)
	private String descrTipoProcedimento;
	@XmlVariabile(nome="#IdFolder", tipo=TipoVariabile.SEMPLICE)
	private String idFolder;
	@XmlVariabile(nome="ID_REL_RICHIEDENTE", tipo=TipoVariabile.SEMPLICE)
	private String idSoggEsterno;					
	@XmlVariabile(nome="ID_UD_ISTANZA", tipo=TipoVariabile.SEMPLICE)
	private String idUd;		
	@XmlVariabile(nome="ID_RICHIEDENTE_IN_ANAG_EST", tipo=TipoVariabile.SEMPLICE)
	private String idSoggGiuridico;
	@XmlVariabile(nome="#DesStatoProc", tipo=TipoVariabile.SEMPLICE)
	private String stato;					
	@XmlVariabile(nome="#OggettoProc", tipo=TipoVariabile.SEMPLICE)
	private String descrizioneSintetica;
	@XmlVariabile(nome="#EstremiProcessoShort", tipo=TipoVariabile.SEMPLICE)
	private String intestazione;
	@XmlVariabile(nome="#EstremiProcessoLong", tipo=TipoVariabile.SEMPLICE)
	private String nomeProgetto;	
	@XmlVariabile(nome="#ID_DOC_RIC_AVVIO", tipo=TipoVariabile.SEMPLICE)
	private String idDocAttestatoAvvioProc;
	@XmlVariabile(nome="#MessaggioUltimaAttivita", tipo=TipoVariabile.SEMPLICE)
	private String messaggioUltimaAttivita;
	@XmlVariabile(nome="#@TemplateForAutomaticGenAtTaskCompl", tipo=TipoVariabile.LISTA)
	private List<ModelloAttivitaBean> listaModelliAttivita;	
	@XmlVariabile(nome="WarningConcorrenza", tipo=TipoVariabile.SEMPLICE)
	private String warningConcorrenza;
	@XmlVariabile(nome="#IsAtto", tipo=TipoVariabile.SEMPLICE)
	private String isAtto;
	
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getDescrTipoProcedimento() {
		return descrTipoProcedimento;
	}
	public void setDescrTipoProcedimento(String descrTipoProcedimento) {
		this.descrTipoProcedimento = descrTipoProcedimento;
	}
	public String getIdSoggEsterno() {
		return idSoggEsterno;
	}
	public void setIdSoggEsterno(String idSoggEsterno) {
		this.idSoggEsterno = idSoggEsterno;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getIdSoggGiuridico() {
		return idSoggGiuridico;
	}
	public void setIdSoggGiuridico(String idSoggGiuridico) {
		this.idSoggGiuridico = idSoggGiuridico;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDescrizioneSintetica() {
		return descrizioneSintetica;
	}
	public void setDescrizioneSintetica(String descrizioneSintetica) {
		this.descrizioneSintetica = descrizioneSintetica;
	}
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
	public String getNomeProgetto() {
		return nomeProgetto;
	}
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
	public String getIdDocAttestatoAvvioProc() {
		return idDocAttestatoAvvioProc;
	}
	public void setIdDocAttestatoAvvioProc(String idDocAttestatoAvvioProc) {
		this.idDocAttestatoAvvioProc = idDocAttestatoAvvioProc;
	}
	public String getMessaggioUltimaAttivita() {
		return messaggioUltimaAttivita;
	}
	public void setMessaggioUltimaAttivita(String messaggioUltimaAttivita) {
		this.messaggioUltimaAttivita = messaggioUltimaAttivita;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public List<ModelloAttivitaBean> getListaModelliAttivita() {
		return listaModelliAttivita;
	}
	public void setListaModelliAttivita(List<ModelloAttivitaBean> listaModelliAttivita) {
		this.listaModelliAttivita = listaModelliAttivita;
	}
	public String getWarningConcorrenza() {
		return warningConcorrenza;
	}
	public void setWarningConcorrenza(String warningConcorrenza) {
		this.warningConcorrenza = warningConcorrenza;
	}
	public String getIsAtto() {
		return isAtto;
	}
	public void setIsAtto(String isAtto) {
		this.isAtto = isAtto;
	}
	
}