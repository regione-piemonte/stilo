/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData.Tipo;
import it.eng.document.TipoData;
import java.util.Date;
																					

public class OggettiElaboratiBean
{
  
  @NumeroColonna(numero="1")
  private String tipoOggettoElaborato;
  
  @NumeroColonna(numero="2")
  private String idOggettoElaborato;
  
  @NumeroColonna(numero="3")
  @TipoData(tipo=Tipo.DATA_ESTESA)
  private Date dataUltimaElaborazione;
  
  @NumeroColonna(numero="4")
  private String esitoElaborazione;
  
  @NumeroColonna(numero="5")
  private String codiceErroreElaborazione;
  
  @NumeroColonna(numero="6")
  private String descErroreElaborazione;
  
  @NumeroColonna(numero="7")
  private String numeroElaborazioni;
  
  @NumeroColonna(numero="8")
  private String estremiOggettoElaborato;
  
  @NumeroColonna(numero="9")
  private String flgDettagli;

public String getTipoOggettoElaborato() {
	return tipoOggettoElaborato;
}

public void setTipoOggettoElaborato(String tipoOggettoElaborato) {
	this.tipoOggettoElaborato = tipoOggettoElaborato;
}

public String getIdOggettoElaborato() {
	return idOggettoElaborato;
}

public void setIdOggettoElaborato(String idOggettoElaborato) {
	this.idOggettoElaborato = idOggettoElaborato;
}

public Date getDataUltimaElaborazione() {
	return dataUltimaElaborazione;
}

public void setDataUltimaElaborazione(Date dataUltimaElaborazione) {
	this.dataUltimaElaborazione = dataUltimaElaborazione;
}

public String getEsitoElaborazione() {
	return esitoElaborazione;
}

public void setEsitoElaborazione(String esitoElaborazione) {
	this.esitoElaborazione = esitoElaborazione;
}

public String getCodiceErroreElaborazione() {
	return codiceErroreElaborazione;
}

public void setCodiceErroreElaborazione(String codiceErroreElaborazione) {
	this.codiceErroreElaborazione = codiceErroreElaborazione;
}

public String getDescErroreElaborazione() {
	return descErroreElaborazione;
}

public void setDescErroreElaborazione(String descErroreElaborazione) {
	this.descErroreElaborazione = descErroreElaborazione;
}

public String getNumeroElaborazioni() {
	return numeroElaborazioni;
}

public void setNumeroElaborazioni(String numeroElaborazioni) {
	this.numeroElaborazioni = numeroElaborazioni;
}

public String getEstremiOggettoElaborato() {
	return estremiOggettoElaborato;
}

public void setEstremiOggettoElaborato(String estremiOggettoElaborato) {
	this.estremiOggettoElaborato = estremiOggettoElaborato;
}

public String getFlgDettagli() {
	return flgDettagli;
}

public void setFlgDettagli(String flgDettagli) {
	this.flgDettagli = flgDettagli;
}
    
}
