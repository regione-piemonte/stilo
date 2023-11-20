/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class ReportTrasparenzaAmministrativaBean {

	private String tipoReport;
	private Date da;
	private Date a;
	private Date dataRif;
	private String idSezione;
	
	public String getTipoReport() {
		return tipoReport;
	}
	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
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
	public Date getDataRif() {
		return dataRif;
	}
	public void setDataRif(Date dataRif) {
		this.dataRif = dataRif;
	}
	public String getIdSezione() {
		return idSezione;
	}
	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}
	
}
