/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
																					
public class PrivilegiEntitaSezioneSetXmlBean {
	
  @NumeroColonna(numero="1")
  private String idOggetto;
  
  @NumeroColonna(numero="2")
  private String tipoOggetto;
  
  @NumeroColonna(numero="3")
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

public String getListaCodiciPrivilegiOggetto() {
	return listaCodiciPrivilegiOggetto;
}

public void setListaCodiciPrivilegiOggetto(String listaCodiciPrivilegiOggetto) {
	this.listaCodiciPrivilegiOggetto = listaCodiciPrivilegiOggetto;
}
  
  
}
