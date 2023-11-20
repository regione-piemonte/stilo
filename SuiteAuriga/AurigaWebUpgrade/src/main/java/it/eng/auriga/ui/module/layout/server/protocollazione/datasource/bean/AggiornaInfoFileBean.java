/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

public class AggiornaInfoFileBean {

	private List<CalcolaFirmaBean> fileVerPreFirma;
	private HashMap<String, CalcolaFirmaBean> fileFirmati;
	private Boolean aggiornamentoFirmeParziale;
	private boolean firmaAvanzamentoIterAtti;

	public List<CalcolaFirmaBean> getFileVerPreFirma() {
		return fileVerPreFirma;
	}

	public void setFileVerPreFirma(List<CalcolaFirmaBean> fileVerPreFirma) {
		this.fileVerPreFirma = fileVerPreFirma;
	}

	public HashMap<String, CalcolaFirmaBean> getFileFirmati() {
		return fileFirmati;
	}

	public void setFileFirmati(HashMap<String, CalcolaFirmaBean> fileFirmati) {
		this.fileFirmati = fileFirmati;
	}

	public Boolean getAggiornamentoFirmeParziale() {
		return aggiornamentoFirmeParziale;
	}

	public void setAggiornamentoFirmeParziale(Boolean aggiornamentoFirmeParziale) {
		this.aggiornamentoFirmeParziale = aggiornamentoFirmeParziale;
	}
	
	public boolean isFirmaAvanzamentoIterAtti() {
		return firmaAvanzamentoIterAtti;
	}
	
	public void setFirmaAvanzamentoIterAtti(boolean firmaAvanzamentoIterAtti) {
		this.firmaAvanzamentoIterAtti = firmaAvanzamentoIterAtti;
	}

}
