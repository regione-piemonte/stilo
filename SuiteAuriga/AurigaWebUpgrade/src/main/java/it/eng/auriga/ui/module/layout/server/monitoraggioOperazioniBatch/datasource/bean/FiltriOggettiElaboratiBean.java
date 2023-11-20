/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import java.io.Serializable;
import java.util.Date;


public class FiltriOggettiElaboratiBean implements Serializable
{
  
  @XmlVariabile(nome="TipoOggetti", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String tipoOggetti;
  
  @XmlVariabile(nome="EsitoElaborazione", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String esitoElaborazione;
 
  @XmlVariabile(nome="CtxMsgErrore", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  private String ctxMsgErrore;
  
  @XmlVariabile(nome="TsFineUltElabDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA)
  private Date dtFineUltimaElaborazioneDa;
  
  @XmlVariabile(nome="TsFineUltElabA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA)
  private Date dtFineUltimaElaborazioneA;
  
  
  private String idRichiesta;
  
  

public String getTipoOggetti() {
	return tipoOggetti;
}

public void setTipoOggetti(String tipoOggetti) {
	this.tipoOggetti = tipoOggetti;
}

public String getEsitoElaborazione() {
	return esitoElaborazione;
}

public void setEsitoElaborazione(String esitoElaborazione) {
	this.esitoElaborazione = esitoElaborazione;
}

public String getCtxMsgErrore() {
	return ctxMsgErrore;
}

public void setCtxMsgErrore(String ctxMsgErrore) {
	this.ctxMsgErrore = ctxMsgErrore;
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

public String getIdRichiesta() {
	return idRichiesta;
}

public void setIdRichiesta(String idRichiesta) {
	this.idRichiesta = idRichiesta;
}
  
   
}