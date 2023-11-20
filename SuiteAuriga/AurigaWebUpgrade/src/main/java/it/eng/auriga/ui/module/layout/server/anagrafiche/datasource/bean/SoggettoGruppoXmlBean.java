/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class SoggettoGruppoXmlBean  {
	
	@NumeroColonna(numero = "1")
	private String tipoMembro; 
	@NumeroColonna(numero = "2")
	private String idSoggettoGruppo; 
	@NumeroColonna(numero = "3")
	private String codiceRapidoSoggetto;
	@NumeroColonna(numero = "4")
	private String denominazioneSoggetto;
	@NumeroColonna(numero = "5")
	private String codfiscaleSoggetto;
	@NumeroColonna(numero = "6")
	private String flgMembroDaEliminare;
	@NumeroColonna(numero = "7")
	private String tipologiaSoggetto;
	@NumeroColonna(numero = "8")
	private String flgInOrganigramma;
	
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
	public String getCodiceRapidoSoggetto() {
		return codiceRapidoSoggetto;
	}
	public void setCodiceRapidoSoggetto(String codiceRapidoSoggetto) {
		this.codiceRapidoSoggetto = codiceRapidoSoggetto;
	}
	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}
	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}
	public String getCodfiscaleSoggetto() {
		return codfiscaleSoggetto;
	}
	public void setCodfiscaleSoggetto(String codfiscaleSoggetto) {
		this.codfiscaleSoggetto = codfiscaleSoggetto;
	}
	public String getFlgMembroDaEliminare() {
		return flgMembroDaEliminare;
	}
	public void setFlgMembroDaEliminare(String flgMembroDaEliminare) {
		this.flgMembroDaEliminare = flgMembroDaEliminare;
	}	
	public String getFlgInOrganigramma() {
		return flgInOrganigramma;
	}
	public void setFlgInOrganigramma(String flgInOrganigramma) {
		this.flgInOrganigramma = flgInOrganigramma;
	}
	public String getTipologiaSoggetto() {
		return tipologiaSoggetto;
	}
	public void setTipologiaSoggetto(String tipologiaSoggetto) {
		this.tipologiaSoggetto = tipologiaSoggetto;
	}

}