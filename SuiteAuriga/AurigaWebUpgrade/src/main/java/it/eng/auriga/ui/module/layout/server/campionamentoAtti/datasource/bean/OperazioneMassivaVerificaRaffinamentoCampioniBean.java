/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

public class OperazioneMassivaVerificaRaffinamentoCampioniBean extends OperazioneMassivaBean<VerificaRaffinamentoCampioniBean> implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	private Date dataInizioPeriodoVerifica;
	private Date dataFinePeriodoVerifica;
	private String tipoRaggruppamento;
	private String percentualeCampionamento;

	public Date getDataInizioPeriodoVerifica() {
		return dataInizioPeriodoVerifica;
	}

	public void setDataInizioPeriodoVerifica(Date dataInizioPeriodoVerifica) {
		this.dataInizioPeriodoVerifica = dataInizioPeriodoVerifica;
	}

	public Date getDataFinePeriodoVerifica() {
		return dataFinePeriodoVerifica;
	}

	public void setDataFinePeriodoVerifica(Date dataFinePeriodoVerifica) {
		this.dataFinePeriodoVerifica = dataFinePeriodoVerifica;
	}

	public String getTipoRaggruppamento() {
		return tipoRaggruppamento;
	}

	public void setTipoRaggruppamento(String tipoRaggruppamento) {
		this.tipoRaggruppamento = tipoRaggruppamento;
	}

	public String getPercentualeCampionamento() {
		return percentualeCampionamento;
	}

	public void setPercentualeCampionamento(String percentualeCampionamento) {
		this.percentualeCampionamento = percentualeCampionamento;
	}	
	
}
