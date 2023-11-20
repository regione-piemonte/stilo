/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
																					
public class PrivilegiEntitaSezioneGetXmlBean {
	
  @NumeroColonna(numero="1")
  private String idOggetto;
  
  @NumeroColonna(numero="2")
  private String tipoOggetto;
  
  @NumeroColonna(numero="3")
  private String codiceOggetto;
  
  @NumeroColonna(numero="4")
  private String denominazioneOggetto;

  @NumeroColonna(numero="5")
  private String listaCodiciPrivilegiOggetto;

public String getIdOggetto() {
	return idOggetto;
}

public void setIdOggetto(String idOggetto) {
	this.idOggetto = idOggetto;
}

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

public String getListaCodiciPrivilegiOggetto() {
	return listaCodiciPrivilegiOggetto;
}

public void setListaCodiciPrivilegiOggetto(String listaCodiciPrivilegiOggetto) {
	this.listaCodiciPrivilegiOggetto = listaCodiciPrivilegiOggetto;
}

  
 }
