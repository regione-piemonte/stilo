/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class StatisticheMailStoricizzateFiltriXmlBean {
	
	// FILTRI
	
	@XmlVariabile(nome="DataInvioDa", tipo=TipoVariabile.SEMPLICE)
	private String dataInvioDa;
	
	@XmlVariabile(nome="DataInvioA", tipo=TipoVariabile.SEMPLICE)
	private String dataInvioA;
	
	@XmlVariabile(nome="DataChiusuraDa", tipo=TipoVariabile.SEMPLICE)
	private String dataChiusuraDa;
	
	@XmlVariabile(nome="DataChiusuraA", tipo=TipoVariabile.SEMPLICE)
	private String dataChiusuraA;
	
	@XmlVariabile(nome="DataStoricizzazioneDa", tipo=TipoVariabile.SEMPLICE)
	private String dataStoricizzazioneDa;
	
	@XmlVariabile(nome="DataStoricizzazioneA", tipo=TipoVariabile.SEMPLICE)
	private String dataStoricizzazioneA;
	
	@XmlVariabile(nome="DataUltimoAggDa", tipo=TipoVariabile.SEMPLICE)
	private String dataUltimoAggDa;
	
	@XmlVariabile(nome="DataUltimoAggA", tipo=TipoVariabile.SEMPLICE)
	private String dataUltimoAggA;
	
	@XmlVariabile(nome="@Caselle", tipo=TipoVariabile.LISTA)
	private List<CaselleMailStoricizzateXmlBean> caselle;
	
	@XmlVariabile(nome="@UOAssegnatarie", tipo=TipoVariabile.LISTA)
	private List<UOAssegnatarieMailStoricizzateXmlBean> uOAssegnatarie;
	
	@XmlVariabile(nome="StatoLavorazione", tipo=TipoVariabile.SEMPLICE)
	private String statoLavorazione;
	
	@XmlVariabile(nome="MinNroMesiSenzaOperazioni", tipo=TipoVariabile.SEMPLICE)
	private String minNroMesiSenzaOperazioni;

	public String getDataInvioDa() {
		return dataInvioDa;
	}

	public void setDataInvioDa(String dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}

	public String getDataInvioA() {
		return dataInvioA;
	}

	public void setDataInvioA(String dataInvioA) {
		this.dataInvioA = dataInvioA;
	}

	public String getDataChiusuraDa() {
		return dataChiusuraDa;
	}

	public void setDataChiusuraDa(String dataChiusuraDa) {
		this.dataChiusuraDa = dataChiusuraDa;
	}

	public String getDataChiusuraA() {
		return dataChiusuraA;
	}

	public void setDataChiusuraA(String dataChiusuraA) {
		this.dataChiusuraA = dataChiusuraA;
	}

	public String getDataStoricizzazioneDa() {
		return dataStoricizzazioneDa;
	}

	public void setDataStoricizzazioneDa(String dataStoricizzazioneDa) {
		this.dataStoricizzazioneDa = dataStoricizzazioneDa;
	}

	public String getDataStoricizzazioneA() {
		return dataStoricizzazioneA;
	}

	public void setDataStoricizzazioneA(String dataStoricizzazioneA) {
		this.dataStoricizzazioneA = dataStoricizzazioneA;
	}

	public String getDataUltimoAggDa() {
		return dataUltimoAggDa;
	}

	public void setDataUltimoAggDa(String dataUltimoAggDa) {
		this.dataUltimoAggDa = dataUltimoAggDa;
	}

	public String getDataUltimoAggA() {
		return dataUltimoAggA;
	}

	public void setDataUltimoAggA(String dataUltimoAggA) {
		this.dataUltimoAggA = dataUltimoAggA;
	}

	public List<CaselleMailStoricizzateXmlBean> getCaselle() {
		return caselle;
	}

	public void setCaselle(List<CaselleMailStoricizzateXmlBean> caselle) {
		this.caselle = caselle;
	}

	public List<UOAssegnatarieMailStoricizzateXmlBean> getuOAssegnatarie() {
		return uOAssegnatarie;
	}

	public void setuOAssegnatarie(List<UOAssegnatarieMailStoricizzateXmlBean> uOAssegnatarie) {
		this.uOAssegnatarie = uOAssegnatarie;
	}

	public String getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public String getMinNroMesiSenzaOperazioni() {
		return minNroMesiSenzaOperazioni;
	}

	public void setMinNroMesiSenzaOperazioni(String minNroMesiSenzaOperazioni) {
		this.minNroMesiSenzaOperazioni = minNroMesiSenzaOperazioni;
	}
}