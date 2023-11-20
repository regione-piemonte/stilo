/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlDatiProcBean {
	 
	@XmlVariabile(nome="#IdProcessType", tipo=TipoVariabile.SEMPLICE)
	private String tipoProcedimento;
	@XmlVariabile(nome="#NomeProcessType", tipo=TipoVariabile.SEMPLICE)
	private String descrTipoProcedimento;
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
	
}
