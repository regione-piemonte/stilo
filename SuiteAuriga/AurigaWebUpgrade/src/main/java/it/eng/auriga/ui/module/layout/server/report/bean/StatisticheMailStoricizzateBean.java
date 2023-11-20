/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

/**
 * @author DANCRIST
 */

public class StatisticheMailStoricizzateBean {
	
	/**
	 *  FILTRI 
	 */
	private Date dtStoricizzazioneDa;
	private Date dtStoricizzazioneA;
	private Date dtChiusuraDa;
	private Date dtChiusuraA;
	private Date dtUltimoAggiornamentoDa;
	private Date dtUltimoAggiornamentoA;
	private Date dtInvioDa;
	private Date dtInvioA;
	private List<String> caselle;
	// DIM_ORGANIGRAMMA_NONSTD = true
	private List<StruttureMailStoricizzateBean> strutture;
	// DIM_ORGANIGRAMMA_NONSTD = false
	private List<String> struttureNONSTD;
	
	private String statoLavorazione;
	private Integer nrMesi;
	
	/**
	 * RAGGRUPPAMENTI
	 */
	private String raggruppaTipologiaPeriodo;
	private String raggruppaPeriodo;
	private String raggruppaUo;
	private Boolean flgRaggruppaStatoLavorazioneEmail;
	private Boolean flgRaggruppaArchivio;
	private Boolean flgRaggruppaCaselle;
	
	public Date getDtStoricizzazioneDa() {
		return dtStoricizzazioneDa;
	}
	public void setDtStoricizzazioneDa(Date dtStoricizzazioneDa) {
		this.dtStoricizzazioneDa = dtStoricizzazioneDa;
	}
	public Date getDtStoricizzazioneA() {
		return dtStoricizzazioneA;
	}
	public void setDtStoricizzazioneA(Date dtStoricizzazioneA) {
		this.dtStoricizzazioneA = dtStoricizzazioneA;
	}
	public Date getDtChiusuraDa() {
		return dtChiusuraDa;
	}
	public void setDtChiusuraDa(Date dtChiusuraDa) {
		this.dtChiusuraDa = dtChiusuraDa;
	}
	public Date getDtChiusuraA() {
		return dtChiusuraA;
	}
	public void setDtChiusuraA(Date dtChiusuraA) {
		this.dtChiusuraA = dtChiusuraA;
	}
	public Date getDtUltimoAggiornamentoDa() {
		return dtUltimoAggiornamentoDa;
	}
	public void setDtUltimoAggiornamentoDa(Date dtUltimoAggiornamentoDa) {
		this.dtUltimoAggiornamentoDa = dtUltimoAggiornamentoDa;
	}
	public Date getDtUltimoAggiornamentoA() {
		return dtUltimoAggiornamentoA;
	}
	public void setDtUltimoAggiornamentoA(Date dtUltimoAggiornamentoA) {
		this.dtUltimoAggiornamentoA = dtUltimoAggiornamentoA;
	}
	public Date getDtInvioDa() {
		return dtInvioDa;
	}
	public void setDtInvioDa(Date dtInvioDa) {
		this.dtInvioDa = dtInvioDa;
	}
	public Date getDtInvioA() {
		return dtInvioA;
	}
	public void setDtInvioA(Date dtInvioA) {
		this.dtInvioA = dtInvioA;
	}
	public List<String> getCaselle() {
		return caselle;
	}
	public void setCaselle(List<String> caselle) {
		this.caselle = caselle;
	}
	public List<StruttureMailStoricizzateBean> getStrutture() {
		return strutture;
	}
	public void setStrutture(List<StruttureMailStoricizzateBean> strutture) {
		this.strutture = strutture;
	}
	public List<String> getStruttureNONSTD() {
		return struttureNONSTD;
	}
	public void setStruttureNONSTD(List<String> struttureNONSTD) {
		this.struttureNONSTD = struttureNONSTD;
	}
	public String getStatoLavorazione() {
		return statoLavorazione;
	}
	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}
	public Integer getNrMesi() {
		return nrMesi;
	}
	public void setNrMesi(Integer nrMesi) {
		this.nrMesi = nrMesi;
	}
	public String getRaggruppaTipologiaPeriodo() {
		return raggruppaTipologiaPeriodo;
	}
	public void setRaggruppaTipologiaPeriodo(String raggruppaTipologiaPeriodo) {
		this.raggruppaTipologiaPeriodo = raggruppaTipologiaPeriodo;
	}
	public String getRaggruppaPeriodo() {
		return raggruppaPeriodo;
	}
	public void setRaggruppaPeriodo(String raggruppaPeriodo) {
		this.raggruppaPeriodo = raggruppaPeriodo;
	}
	public String getRaggruppaUo() {
		return raggruppaUo;
	}
	public void setRaggruppaUo(String raggruppaUo) {
		this.raggruppaUo = raggruppaUo;
	}
	public Boolean getFlgRaggruppaStatoLavorazioneEmail() {
		return flgRaggruppaStatoLavorazioneEmail;
	}
	public void setFlgRaggruppaStatoLavorazioneEmail(Boolean flgRaggruppaStatoLavorazioneEmail) {
		this.flgRaggruppaStatoLavorazioneEmail = flgRaggruppaStatoLavorazioneEmail;
	}
	public Boolean getFlgRaggruppaArchivio() {
		return flgRaggruppaArchivio;
	}
	public void setFlgRaggruppaArchivio(Boolean flgRaggruppaArchivio) {
		this.flgRaggruppaArchivio = flgRaggruppaArchivio;
	}
	public Boolean getFlgRaggruppaCaselle() {
		return flgRaggruppaCaselle;
	}
	public void setFlgRaggruppaCaselle(Boolean flgRaggruppaCaselle) {
		this.flgRaggruppaCaselle = flgRaggruppaCaselle;
	}
	
}