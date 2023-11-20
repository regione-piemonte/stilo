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

public class DocPdVXmlBean {
	
	/**
	-- 1:  Tipo di item. U = Unità documentarie, D = Singoli documenti, F = Fascicoli/aggregati
	-- 2:  Id. interno dell'item
	-- 3:  Operazione da effettuare sull'item: I = Versamento (Insert); U = Aggiornamento (di dati e/o file) (Update); D = Cancellazione (Delete)
	-- 4:  Id. dell'item inviato al Conservatore
	-- 5:  Id. della tipologia - di documento o aggregato - dell'item
	-- 6:  Nome della tipologia - di documento o aggregato - dell'item
	-- 7:  Impronta 
	-- 8:  Algoritmo calcolo impronta
	-- 9:  Encoding per calcolo impronta
	-- 10: Dimensione del/i file dell'item (in bytes)
	-- 11: Data entro cui l'item doveva essere inviato al sistema di conservazione (nel formato dato dal parametro di conf. FMT_STD_DATA)
	-- 12: Esito elaborazione conservatore: OK, KO, W(arning)
	-- 13: Codice/i eventuali errore rilevati dal conservatore
	-- 14: Messaggio/i eventuali errori rilevati dal conservatore
	-- 15: Id. assegnato all'item dal conservatore
	-- 16: Attributi principali che identificano l'item
	-- 17: N.ro di tentativi di invio in conservazione dell'item al momento dell'invio con il dato PdV
	-- 18: Tipo dell'item precedente - U = Unità documentaria, D = Singolo documento, F = Fascicolo/aggregato - che deve essere preliminarmente inviato in conservazione
	-- 19: Id. interno dell'item precedente che deve essere preliminarmente inviato in conservazione
	-- 20: Id. della tipologia - di documento o aggregato - dell'item precedente che deve essere preliminarmente inviato in conservazione
	-- 21: Nome della tipologia - di documento o aggregato - dell'item precedente che deve essere preliminarmente inviato in conservazione
	-- 22: Attributi principali che identificano l'item precedente che deve essere preliminarmente inviato in conservazione
	 */
	
	@NumeroColonna(numero = "2")
	private String idPdV;
	
	@NumeroColonna(numero = "4")
	private String idDocOriginale;
	
	@NumeroColonna(numero = "5")
	private String idTipologiaDoc;
	
	@NumeroColonna(numero = "6")
	private String nomeTipologiaDoc;
	
	@NumeroColonna(numero = "7")
	private String impronta;
	
	@NumeroColonna(numero = "8")
	private String algoritmo;
	
	@NumeroColonna(numero = "9")
	private String encoding;
	
	@NumeroColonna(numero = "10")
	private String dimensione;
	
	@NumeroColonna(numero = "11")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvio;
	
	@NumeroColonna(numero = "12")
	private String esito;
	
	@NumeroColonna(numero = "13")
	private String codiceErrore;
	
	@NumeroColonna(numero = "14")
	private String messaggioErrore;
	
	@NumeroColonna(numero = "15")
	private String idDocSdC;
	
	@NumeroColonna(numero = "16")
	private String attributiPrincipali;
	
	@NumeroColonna(numero = "17")
	private String  numTentativiInvioConserv;

	
	public String getIdPdV() {
		return idPdV;
	}
	public void setIdPdV(String idPdV) {
		this.idPdV = idPdV;
	}
	public String getIdDocOriginale() {
		return idDocOriginale;
	}
	public void setIdDocOriginale(String idDocOriginale) {
		this.idDocOriginale = idDocOriginale;
	}
	public String getIdTipologiaDoc() {
		return idTipologiaDoc;
	}
	public void setIdTipologiaDoc(String idTipologiaDoc) {
		this.idTipologiaDoc = idTipologiaDoc;
	}
	public String getNomeTipologiaDoc() {
		return nomeTipologiaDoc;
	}
	public void setNomeTipologiaDoc(String nomeTipologiaDoc) {
		this.nomeTipologiaDoc = nomeTipologiaDoc;
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
	public String getDimensione() {
		return dimensione;
	}
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	public String getIdDocSdC() {
		return idDocSdC;
	}
	public void setIdDocSdC(String idDocSdC) {
		this.idDocSdC = idDocSdC;
	}
	public String getAttributiPrincipali() {
		return attributiPrincipali;
	}
	public void setAttributiPrincipali(String attributiPrincipali) {
		this.attributiPrincipali = attributiPrincipali;
	}
	public String getNumTentativiInvioConserv() {
		return numTentativiInvioConserv;
	}
	public void setNumTentativiInvioConserv(String numTentativiInvioConserv) {
		this.numTentativiInvioConserv = numTentativiInvioConserv;
	}
}
