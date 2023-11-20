/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData.Tipo;
import it.eng.document.TipoData;
import java.util.Date;
																					

public class LogOperazioniXmlBean
{
  
  @NumeroColonna(numero="1")
  @TipoData(tipo=Tipo.DATA_ESTESA)
  private Date tsOperazione;
	
  @NumeroColonna(numero="2")
  private String nroProgrOperazione;
  
  @NumeroColonna(numero="3")
  private String tipoOperazione;
  
  @NumeroColonna(numero="4")
  private String idUtenteOperazione;
  
  @NumeroColonna(numero="5")
  private String descUtenteOperazione;
  
  @NumeroColonna(numero="6")
  private String idUtenteDelegatoOperazione;
  
  @NumeroColonna(numero="7")
  private String descUtenteDelegatoOperazione;
  
  @NumeroColonna(numero="8")
  private String dettagliOperazione;
  
  @NumeroColonna(numero="9")
  private String esitoOperazione;

  @NumeroColonna(numero="10")
  private String idLogOperazione;
  
public String getIdLogOperazione() {
	return idLogOperazione;
}

public void setIdLogOperazione(String idLogOperazione) {
	this.idLogOperazione = idLogOperazione;
}

public Date getTsOperazione() {
	return tsOperazione;
}

public void setTsOperazione(Date tsOperazione) {
	this.tsOperazione = tsOperazione;
}

public String getNroProgrOperazione() {
	return nroProgrOperazione;
}

public void setNroProgrOperazione(String nroProgrOperazione) {
	this.nroProgrOperazione = nroProgrOperazione;
}

public String getTipoOperazione() {
	return tipoOperazione;
}

public void setTipoOperazione(String tipoOperazione) {
	this.tipoOperazione = tipoOperazione;
}

public String getIdUtenteOperazione() {
	return idUtenteOperazione;
}

public void setIdUtenteOperazione(String idUtenteOperazione) {
	this.idUtenteOperazione = idUtenteOperazione;
}

public String getDescUtenteOperazione() {
	return descUtenteOperazione;
}

public void setDescUtenteOperazione(String descUtenteOperazione) {
	this.descUtenteOperazione = descUtenteOperazione;
}

public String getIdUtenteDelegatoOperazione() {
	return idUtenteDelegatoOperazione;
}

public void setIdUtenteDelegatoOperazione(String idUtenteDelegatoOperazione) {
	this.idUtenteDelegatoOperazione = idUtenteDelegatoOperazione;
}

public String getDescUtenteDelegatoOperazione() {
	return descUtenteDelegatoOperazione;
}

public void setDescUtenteDelegatoOperazione(String descUtenteDelegatoOperazione) {
	this.descUtenteDelegatoOperazione = descUtenteDelegatoOperazione;
}

public String getDettagliOperazione() {
	return dettagliOperazione;
}

public void setDettagliOperazione(String dettagliOperazione) {
	this.dettagliOperazione = dettagliOperazione;
}

public String getEsitoOperazione() {
	return esitoOperazione;
}

public void setEsitoOperazione(String esitoOperazione) {
	this.esitoOperazione = esitoOperazione;
}
      
}
