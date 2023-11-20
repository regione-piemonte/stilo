/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ReportResultBean implements Comparable<ReportResultBean>{

	@NumeroColonna(numero = "1")
	private String idSoggetto;
	@NumeroColonna(numero = "2")
	private String label;
	@NumeroColonna(numero = "3")
	private String valore;
	@NumeroColonna(numero = "4")
	private String perc;
	@NumeroColonna(numero = "5")
	private String percArrotondata;
	
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getPerc() {
		return perc;
	}
	public void setPerc(String perc) {
		this.perc = perc;
	}
	public String getPercArrotondata() {
		return percArrotondata;
	}
	public void setPercArrotondata(String percArrotondata) {
		this.percArrotondata = percArrotondata;
	}
	@Override
	public int compareTo(ReportResultBean o) {
		return label.compareTo(o.getLabel());
	}
	
}
