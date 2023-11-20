/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class SoggettoGruppoEmailBean  {
	
	private String tipoMembro; 
	private String idSoggettoGruppo; 
	private String denominazioneSoggetto;
	private String indirizzoMailSoggetto;
	private String flgMembroDaEliminare;
	
	public String getTipoMembro() {
		return tipoMembro;
	}
	public void setTipoMembro(String tipoMembro) {
		this.tipoMembro = tipoMembro;
	}
	public String getIdSoggettoGruppo() {
		return idSoggettoGruppo;
	}
	public void setIdSoggettoGruppo(String idSoggettoGruppo) {
		this.idSoggettoGruppo = idSoggettoGruppo;
	}
	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}
	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}
	public String getIndirizzoMailSoggetto() {
		return indirizzoMailSoggetto;
	}
	public void setIndirizzoMailSoggetto(String indirizzoMailSoggetto) {
		this.indirizzoMailSoggetto = indirizzoMailSoggetto;
	}
	public String getFlgMembroDaEliminare() {
		return flgMembroDaEliminare;
	}
	public void setFlgMembroDaEliminare(String flgMembroDaEliminare) {
		this.flgMembroDaEliminare = flgMembroDaEliminare;
	}

}