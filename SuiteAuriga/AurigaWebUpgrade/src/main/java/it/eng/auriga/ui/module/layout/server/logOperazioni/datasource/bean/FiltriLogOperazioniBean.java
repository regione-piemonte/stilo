/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import java.io.Serializable;
import java.util.Date;


public class FiltriLogOperazioniBean implements Serializable
{
  
  @XmlVariabile(nome="TipoOperazione", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String tipoOperazione;
  
  @XmlVariabile(nome="EsitoOperazione", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String esitoOperazione;
    
  @XmlVariabile(nome="TsOperazioneDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA)
  private Date tsOperazioneDa;
  
  @XmlVariabile(nome="TsOperazioneA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA)
  private Date tsOperazioneA;

  @XmlVariabile(nome="OperazioneEffettuataDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String operazioneEffettuataDa;
  
public String getTipoOperazione() {
	return tipoOperazione;
}

public void setTipoOperazione(String tipoOperazione) {
	this.tipoOperazione = tipoOperazione;
}

public String getEsitoOperazione() {
	return esitoOperazione;
}

public void setEsitoOperazione(String esitoOperazione) {
	this.esitoOperazione = esitoOperazione;
}

public Date getTsOperazioneDa() {
	return tsOperazioneDa;
}

public void setTsOperazioneDa(Date tsOperazioneDa) {
	this.tsOperazioneDa = tsOperazioneDa;
}

public Date getTsOperazioneA() {
	return tsOperazioneA;
}

public void setTsOperazioneA(Date tsOperazioneA) {
	this.tsOperazioneA = tsOperazioneA;
}

public String getOperazioneEffettuataDa() {
	return operazioneEffettuataDa;
}

public void setOperazioneEffettuataDa(String operazioneEffettuataDa) {
	this.operazioneEffettuataDa = operazioneEffettuataDa;
}
  
    
}