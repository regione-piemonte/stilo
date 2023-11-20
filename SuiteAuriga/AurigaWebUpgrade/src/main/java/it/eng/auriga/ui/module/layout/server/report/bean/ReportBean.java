/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

public class ReportBean {

	private String tipoReport;
	private List<String> tipoDiRegistrazione;
	private Date da;
	private Date a;
	private Integer level;
	private Integer idSoggetto;
	
	public String getTipoReport() {
		return tipoReport;
	}
	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}
	public List<String> getTipoDiRegistrazione() {
		return tipoDiRegistrazione;
	}
	public void setTipoDiRegistrazione(List<String> tipoDiRegistrazione) {
		this.tipoDiRegistrazione = tipoDiRegistrazione;
	}
	public Date getDa() {
		return da;
	}
	public void setDa(Date da) {
		this.da = da;
	}
	public Date getA() {
		return a;
	}
	public void setA(Date a) {
		this.a = a;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
}
