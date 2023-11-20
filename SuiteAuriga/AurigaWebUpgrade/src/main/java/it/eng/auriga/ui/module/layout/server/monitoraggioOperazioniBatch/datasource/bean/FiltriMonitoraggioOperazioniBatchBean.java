/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.io.Serializable;
import java.util.Date;


public class FiltriMonitoraggioOperazioniBatchBean implements Serializable
{
  @XmlVariabile(nome="TipoOperazione", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String tipoOperazione;
  
  @XmlVariabile(nome="TipoOggetti", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String tipoOggettiDaProcessare;
  
  @XmlVariabile(nome="DtRichiestaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
  private Date dtRichiestaDa;
  
  @XmlVariabile(nome="DtRichiestaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
  private Date dtRichiestaA;
  
  @XmlVariabile(nome="DtDecorrOperDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
  private Date dtSchedulazioneDa;
  
  @XmlVariabile(nome="DtDecorrOperA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
  private Date dtSchedulazioneA;
  
  @XmlVariabile(nome="StatoRichiesta", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String statoRichiesta;
  
  @XmlVariabile(nome="Motivazione", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String motivoRichiesta;
  
  @XmlVariabile(nome="IdUserRichiesta", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String utenteRichiestaSottomissione;
  
  @XmlVariabile(nome="TipoOperazioneTipoEventoOrig", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String tipoEventoScatenante;
  
  @XmlVariabile(nome="CodTipoOggEvtOrigSu", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String eventoScatenanteSuTipoOggetto;
  
  @XmlVariabile(nome="TsFineUltElabDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA)
  private Date dtFineUltimaElaborazioneDa;
  
  @XmlVariabile(nome="TsFineUltElabA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA)
  private Date dtFineUltimaElaborazioneA;

public String getTipoOperazione() {
	return tipoOperazione;
}

public void setTipoOperazione(String tipoOperazione) {
	this.tipoOperazione = tipoOperazione;
}

public String getTipoOggettiDaProcessare() {
	return tipoOggettiDaProcessare;
}

public void setTipoOggettiDaProcessare(String tipoOggettiDaProcessare) {
	this.tipoOggettiDaProcessare = tipoOggettiDaProcessare;
}

public Date getDtRichiestaDa() {
	return dtRichiestaDa;
}

public void setDtRichiestaDa(Date dtRichiestaDa) {
	this.dtRichiestaDa = dtRichiestaDa;
}

public Date getDtRichiestaA() {
	return dtRichiestaA;
}

public void setDtRichiestaA(Date dtRichiestaA) {
	this.dtRichiestaA = dtRichiestaA;
}

public Date getDtSchedulazioneDa() {
	return dtSchedulazioneDa;
}

public void setDtSchedulazioneDa(Date dtSchedulazioneDa) {
	this.dtSchedulazioneDa = dtSchedulazioneDa;
}

public Date getDtSchedulazioneA() {
	return dtSchedulazioneA;
}

public void setDtSchedulazioneA(Date dtSchedulazioneA) {
	this.dtSchedulazioneA = dtSchedulazioneA;
}

public String getStatoRichiesta() {
	return statoRichiesta;
}

public void setStatoRichiesta(String statoRichiesta) {
	this.statoRichiesta = statoRichiesta;
}

public String getMotivoRichiesta() {
	return motivoRichiesta;
}

public void setMotivoRichiesta(String motivoRichiesta) {
	this.motivoRichiesta = motivoRichiesta;
}

public String getUtenteRichiestaSottomissione() {
	return utenteRichiestaSottomissione;
}

public void setUtenteRichiestaSottomissione(String utenteRichiestaSottomissione) {
	this.utenteRichiestaSottomissione = utenteRichiestaSottomissione;
}

public String getTipoEventoScatenante() {
	return tipoEventoScatenante;
}

public void setTipoEventoScatenante(String tipoEventoScatenante) {
	this.tipoEventoScatenante = tipoEventoScatenante;
}

public String getEventoScatenanteSuTipoOggetto() {
	return eventoScatenanteSuTipoOggetto;
}

public void setEventoScatenanteSuTipoOggetto(String eventoScatenanteSuTipoOggetto) {
	this.eventoScatenanteSuTipoOggetto = eventoScatenanteSuTipoOggetto;
}

public Date getDtFineUltimaElaborazioneDa() {
	return dtFineUltimaElaborazioneDa;
}

public void setDtFineUltimaElaborazioneDa(Date dtFineUltimaElaborazioneDa) {
	this.dtFineUltimaElaborazioneDa = dtFineUltimaElaborazioneDa;
}

public Date getDtFineUltimaElaborazioneA() {
	return dtFineUltimaElaborazioneA;
}

public void setDtFineUltimaElaborazioneA(Date dtFineUltimaElaborazioneA) {
	this.dtFineUltimaElaborazioneA = dtFineUltimaElaborazioneA;
}
  
  
}