/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class VersionamentoFileBean {

	private boolean esitoVersionamento = false;
	private String messaggioErrore;
	
	
	public boolean isEsitoVersionamento() {
		return esitoVersionamento;
	}
	public void setEsitoVersionamento(boolean esitoVersionamento) {
		this.esitoVersionamento = esitoVersionamento;
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	
		
}
