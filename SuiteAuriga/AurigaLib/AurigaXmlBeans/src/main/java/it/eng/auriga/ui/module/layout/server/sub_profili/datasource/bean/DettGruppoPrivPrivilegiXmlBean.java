/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
																					

public class DettGruppoPrivPrivilegiXmlBean
{
	
  @NumeroColonna(numero="1")
  private String tipoOggetto;
  
  @NumeroColonna(numero="2")
  private String codiceOggetto;
  
  @NumeroColonna(numero="3")
  private String denominazioneOggetto;

  @NumeroColonna(numero="4")
  private String nriLivelloClassificazione;
  
  @NumeroColonna(numero="5")
  private String listaCodiciPrivilegiOggetto;
  
  @NumeroColonna(numero="6")
  private String listaDescrizionePrivilegiOggetto;

public String getTipoOggetto() {
	return tipoOggetto;
}

public void setTipoOggetto(String tipoOggetto) {
	this.tipoOggetto = tipoOggetto;
}

public String getCodiceOggetto() {
	return codiceOggetto;
}

public void setCodiceOggetto(String codiceOggetto) {
	this.codiceOggetto = codiceOggetto;
}

public String getDenominazioneOggetto() {
	return denominazioneOggetto;
}

public void setDenominazioneOggetto(String denominazioneOggetto) {
	this.denominazioneOggetto = denominazioneOggetto;
}

public String getNriLivelloClassificazione() {
	return nriLivelloClassificazione;
}

public void setNriLivelloClassificazione(String nriLivelloClassificazione) {
	this.nriLivelloClassificazione = nriLivelloClassificazione;
}

public String getListaCodiciPrivilegiOggetto() {
	return listaCodiciPrivilegiOggetto;
}

public void setListaCodiciPrivilegiOggetto(String listaCodiciPrivilegiOggetto) {
	this.listaCodiciPrivilegiOggetto = listaCodiciPrivilegiOggetto;
}

public String getListaDescrizionePrivilegiOggetto() {
	return listaDescrizionePrivilegiOggetto;
}

public void setListaDescrizionePrivilegiOggetto(String listaDescrizionePrivilegiOggetto) {
	this.listaDescrizionePrivilegiOggetto = listaDescrizionePrivilegiOggetto;
}

 }
