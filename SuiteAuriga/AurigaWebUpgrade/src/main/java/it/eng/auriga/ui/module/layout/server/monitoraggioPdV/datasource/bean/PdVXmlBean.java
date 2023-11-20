/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class PdVXmlBean {

	/**
		 1:  Id. interno del PdV 
		 2:  Nome logico del PdV (inviato al conservatore come suo identificativo)
		 3:  Cod. Applicazione IT produttrice
		 9:  Nome classe documentale per Conservatore
		 10: Nome Conservatore
		 11: Denominazione produttore
		 12: N.ro di item contenuti
		 13: Dimensione del pacchetto fisico (in MB)
		 14: Data e ora di generazione (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		 15: Impronta 
		 16: Algoritmo calcolo impronta
		 17: Encoding per calcolo impronta
		 18: Stato attuale 
		 19: Data e ora in cui ha assunto lo stato attuale (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		 20: Data e ora di invio al conservatore (attestata da ricevuta di accettazione/rifiuto trasmissione dove c'è CFT) (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		 21: Data e ora di recupero del RdV (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		 22: Data e ora di generazione del Rapporto di Versamento attestata dal conservatore (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		 23: Tempo per ritorno ricevuta accettazione/rifiuto trasmissione(in min)
		 24: Tempo per ritorno RdV (in ore)
		 25: Codice eventuale errore di trasmissione al conservatore
		 26: Messaggio eventuale errore di trasmissione al conservatore
		 27: N.ro di item su cui il conservatore ha rilevato errori
		 28: N.ro di item del pacchetto che risultano conservati (quelli per i quali è arrivato il Rapporto di Archiviazione)
		 29: Nro di tentativi di trasmissione al conservatore
		 30: Nro di tentativi di recupero RdV
		 31: Id assegnato al PdV dal conservatore
		 32: (1/0) Flag presenza file indice del PdV
		 33: (1/0) Flag presenza file .inf del PdV (per POSTEL)
		 34: (1/0) Flag presenza file di ricevuta accettazione/rifiuto trasmissione (per POSTEL)
		 35: (1/0) Flag presenza file RdV (per POSTEL)
		 36: Nome processo Banca - Applicazione IT produttrice
		 37: Cod. errore recupero RdV
		 38: Messaggio errore recupero RdV
		 39: Row IdRecord
		 40: Url Rapporto di versamento
		 41: (1/0) Flag che se 1 indica che RdV è un p7m
	 */
	
	@NumeroColonna(numero = "1")
	private String idPdV; 
	
	@NumeroColonna(numero = "2")
	private String etichetta; 
	
	@NumeroColonna(numero = "3")
	private String codProcessoBancaProd;
	
	@NumeroColonna(numero = "9")
	private String nomeClasseConservata;
	
	@NumeroColonna(numero = "10")
	private String conservatoreEsternoBanca;
	
	@NumeroColonna(numero = "11")
	private String denominazioneSoggettoProd;	
	
	@NumeroColonna(numero = "12")
	private String numDocumentiPdV;	
	
	@NumeroColonna(numero = "13")
	private String  dimensione;
	
	@NumeroColonna(numero = "14")
	@TipoData(tipo = Tipo.DATA)
	private Date dataGenerazionePdV;
	
	@NumeroColonna(numero = "15")
	private String impronta;
	
	@NumeroColonna(numero = "16")
	private String algoritmo;
	
	@NumeroColonna(numero = "17")
	private String encoding;
	
	@NumeroColonna(numero = "18")
	private String stato; 
	
	@NumeroColonna(numero = "19")
	@TipoData(tipo = Tipo.DATA)
	private Date dataUltimoAggStato;
	
	@NumeroColonna(numero = "20")
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvio;
	
	@NumeroColonna(numero = "21")
	@TipoData(tipo = Tipo.DATA)
	private Date dataRecuperoRdV;
	
	@NumeroColonna(numero = "22")
	@TipoData(tipo = Tipo.DATA)
	private Date datGenerazioneRdV;
	
	@NumeroColonna(numero = "23")
	private String tempoRitornoRicevutaAccRifTrasm;

	@NumeroColonna(numero = "24")
	private String tempoRitornoRdV;
	
	@NumeroColonna(numero = "25")
	private String codErroreTrasmissionePdV; 
	
	@NumeroColonna(numero = "26")
	private String messaggioErroreTrasmissionePdV; 
	
	@NumeroColonna(numero = "27")
	private String  numDocumentiInErrore;
	
	@NumeroColonna(numero = "28")
	private String  numDocumentiConservati;

	@NumeroColonna(numero = "29")
	private String  numTentativiTrasmissione;
	
	@NumeroColonna(numero = "30")
	private String  numTentativiRecuperoRdV;
	
	@NumeroColonna(numero = "31")
	private String idPdVConservatore;
	
	@NumeroColonna(numero = "32")
	private Boolean flgFileIndiceXml;
	
	@NumeroColonna(numero = "33")
	private Boolean flgFileInfPdV;
	
	@NumeroColonna(numero = "34")
	private Boolean flgFileRicevutaTrasm;

	@NumeroColonna(numero = "35")
	private Boolean flgFileRdV;

//	@NumeroColonna(numero = "36")
//	private String nomeProcessoBancaProd;
	
	@NumeroColonna(numero = "37")
	private String codErroreRecuperoRdV;
	
	@NumeroColonna(numero = "38")
	private String messaggioErroreRecuperoRdV;
	
	@NumeroColonna(numero = "39")
	private String rowIdRecord;
	
	@NumeroColonna(numero = "40")
	private String urlRdV;
	
	@NumeroColonna(numero = "41")
	private String fileP7M;
	
	public String getIdPdV() {
		return idPdV;
	}
	public void setIdPdV(String idPdV) {
		this.idPdV = idPdV;
	}
	public String getEtichetta() {
		return etichetta;
	}
	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}
	public String getCodProcessoBancaProd() {
		return codProcessoBancaProd;
	}
	public void setCodProcessoBancaProd(String codProcessoBancaProd) {
		this.codProcessoBancaProd = codProcessoBancaProd;
	}
	public String getNomeClasseConservata() {
		return nomeClasseConservata;
	}
	public void setNomeClasseConservata(String nomeClasseConservata) {
		this.nomeClasseConservata = nomeClasseConservata;
	}
	public String getConservatoreEsternoBanca() {
		return conservatoreEsternoBanca;
	}
	public void setConservatoreEsternoBanca(String conservatoreEsternoBanca) {
		this.conservatoreEsternoBanca = conservatoreEsternoBanca;
	}
	public String getDenominazioneSoggettoProd() {
		return denominazioneSoggettoProd;
	}
	public void setDenominazioneSoggettoProd(String denominazioneSoggettoProd) {
		this.denominazioneSoggettoProd = denominazioneSoggettoProd;
	}
	public String getNumDocumentiPdV() {
		return numDocumentiPdV;
	}
	public void setNumDocumentiPdV(String numDocumentiPdV) {
		this.numDocumentiPdV = numDocumentiPdV;
	}
	public String getDimensione() {
		return dimensione;
	}
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}
	public Date getDataGenerazionePdV() {
		return dataGenerazionePdV;
	}
	public void setDataGenerazionePdV(Date dataGenerazionePdV) {
		this.dataGenerazionePdV = dataGenerazionePdV;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getAlgoritmo() {
		return algoritmo;
	}
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getDataUltimoAggStato() {
		return dataUltimoAggStato;
	}
	public void setDataUltimoAggStato(Date dataUltimoAggStato) {
		this.dataUltimoAggStato = dataUltimoAggStato;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public Date getDataRecuperoRdV() {
		return dataRecuperoRdV;
	}
	public void setDataRecuperoRdV(Date dataRecuperoRdV) {
		this.dataRecuperoRdV = dataRecuperoRdV;
	}
	public Date getDatGenerazioneRdV() {
		return datGenerazioneRdV;
	}
	public void setDatGenerazioneRdV(Date datGenerazioneRdV) {
		this.datGenerazioneRdV = datGenerazioneRdV;
	}
	public String getTempoRitornoRicevutaAccRifTrasm() {
		return tempoRitornoRicevutaAccRifTrasm;
	}
	public void setTempoRitornoRicevutaAccRifTrasm(String tempoRitornoRicevutaAccRifTrasm) {
		this.tempoRitornoRicevutaAccRifTrasm = tempoRitornoRicevutaAccRifTrasm;
	}
	public String getTempoRitornoRdV() {
		return tempoRitornoRdV;
	}
	public void setTempoRitornoRdV(String tempoRitornoRdV) {
		this.tempoRitornoRdV = tempoRitornoRdV;
	}
	public String getNumDocumentiInErrore() {
		return numDocumentiInErrore;
	}
	public void setNumDocumentiInErrore(String numDocumentiInErrore) {
		this.numDocumentiInErrore = numDocumentiInErrore;
	}
	public String getNumDocumentiConservati() {
		return numDocumentiConservati;
	}
	public void setNumDocumentiConservati(String numDocumentiConservati) {
		this.numDocumentiConservati = numDocumentiConservati;
	}
	public String getNumTentativiTrasmissione() {
		return numTentativiTrasmissione;
	}
	public void setNumTentativiTrasmissione(String numTentativiTrasmissione) {
		this.numTentativiTrasmissione = numTentativiTrasmissione;
	}
	public String getNumTentativiRecuperoRdV() {
		return numTentativiRecuperoRdV;
	}
	public void setNumTentativiRecuperoRdV(String numTentativiRecuperoRdV) {
		this.numTentativiRecuperoRdV = numTentativiRecuperoRdV;
	}
	public String getIdPdVConservatore() {
		return idPdVConservatore;
	}
	public void setIdPdVConservatore(String idPdVConservatore) {
		this.idPdVConservatore = idPdVConservatore;
	}
	public Boolean getFlgFileIndiceXml() {
		return flgFileIndiceXml;
	}
	public void setFlgFileIndiceXml(Boolean flgFileIndiceXml) {
		this.flgFileIndiceXml = flgFileIndiceXml;
	}
	public Boolean getFlgFileInfPdV() {
		return flgFileInfPdV;
	}
	public void setFlgFileInfPdV(Boolean flgFileInfPdV) {
		this.flgFileInfPdV = flgFileInfPdV;
	}
	public Boolean getFlgFileRicevutaTrasm() {
		return flgFileRicevutaTrasm;
	}
	public void setFlgFileRicevutaTrasm(Boolean flgFileRicevutaTrasm) {
		this.flgFileRicevutaTrasm = flgFileRicevutaTrasm;
	}
	public Boolean getFlgFileRdV() {
		return flgFileRdV;
	}
	public void setFlgFileRdV(Boolean flgFileRdV) {
		this.flgFileRdV = flgFileRdV;
	}
//	public String getNomeProcessoBancaProd() {
//		return nomeProcessoBancaProd;
//	}
//	public void setNomeProcessoBancaProd(String nomeProcessoBancaProd) {
//		this.nomeProcessoBancaProd = nomeProcessoBancaProd;
//	}
	
	public String getCodErroreRecuperoRdV() {
		return codErroreRecuperoRdV;
	}
	
	public void setCodErroreRecuperoRdV(String codErroreRecuperoRdV) {
		this.codErroreRecuperoRdV = codErroreRecuperoRdV;
	}
	
	public String getMessaggioErroreRecuperoRdV() {
		return messaggioErroreRecuperoRdV;
	}
	
	public void setMessaggioErroreRecuperoRdV(String messaggioErroreRecuperoRdV) {
		this.messaggioErroreRecuperoRdV = messaggioErroreRecuperoRdV;
	}
	
	public String getCodErroreTrasmissionePdV() {
		return codErroreTrasmissionePdV;
	}
	
	public void setCodErroreTrasmissionePdV(String codErroreTrasmissionePdV) {
		this.codErroreTrasmissionePdV = codErroreTrasmissionePdV;
	}
	
	public String getMessaggioErroreTrasmissionePdV() {
		return messaggioErroreTrasmissionePdV;
	}
	
	public void setMessaggioErroreTrasmissionePdV(String messaggioErroreTrasmissionePdV) {
		this.messaggioErroreTrasmissionePdV = messaggioErroreTrasmissionePdV;
	}
	public String getRowIdRecord() {
		return rowIdRecord;
	}
	public void setRowIdRecord(String rowIdRecord) {
		this.rowIdRecord = rowIdRecord;
	}
	
	public String getUrlRdV() {
		return urlRdV;
	}
	
	public void setUrlRdV(String urlRdV) {
		this.urlRdV = urlRdV;
	}
	
	public String getFileP7M() {
		return fileP7M;
	}
	
	public void setFileP7M(String fileP7M) {
		this.fileP7M = fileP7M;
	}
	
}
