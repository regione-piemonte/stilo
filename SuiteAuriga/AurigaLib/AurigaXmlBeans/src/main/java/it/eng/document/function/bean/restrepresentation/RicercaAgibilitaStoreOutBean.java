/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Antonio Peluso
 */

public class RicercaAgibilitaStoreOutBean {

	//Id. del dominio in cui viene evasa la richiesta
	private BigDecimal idDominio;

	//Estremi di protocollo in entrata assegnato alla richiesta
	private String estremiProtRichiesta;
	
	//ista dei certificati di agibilità restituiti
	private List<String> listaCertificati;
	
	//lista dei file da restituire. Ogni file-riga contiene le colonne
	//	-- 1) URI in notazione storageUtil
	//	-- 2) Nome con cui mostrare il file
	//	-- 3) mimetype
	//	-- 4) dimensione in bytes
	//	-- 5) idUD
	private List<FileAgibilitaOutBean> filesAgibilita;
	
	//ID_UD assegnato alla risposta protocollata	
	private BigDecimal idUDRisposta;
	
	//ID_DOC del documento primario della risposta protocollata
	private BigDecimal idDocRisposta;
	
	//Estremi di protocollo in uscita assegnato alla risposta
	private String estremiProtRisposta;
	
	//Id. dell'utente del modulo mail da usare per la trasmissione mail della risposta
	private String idUtenteInvioMail;
	
	//Account e-mail da cui inviare la mail con la risposta
	private String accountMittenteMail;
	
	//Oggetto della mail da inviare
	private String oggettoMail;
	
	//Token da utilizzare per le connessioni alle store
	private String connectionToken;
	
	//Corpo della mail da inviare (html)
	private String corpoMail;
	
	//Se 1 il/i file delle agibilità possono essere allegati alla mail, se 0 no
	private Boolean flgFileAllegatiMail;
	
	//Nome del modello da usare per il pdf di risposta
	private String nomeTemplate;
	
	//Tipo del modello (ai fini di come si iniettano i dati)
	private String tipoTemplate;
	
	//Contenuto da mettere nel barcode da appore sul file della risposta
	private String contenutoBarcode;
	
	//Testo in chiaro da mettere vicino al barcode
	private String testoInChiaroBarcode;
	
	//Destinatari ai quali inviare la mail
	private String destinatariMail;
	
	//id Modello
	private String uriTemplate;
	
	//uri Modello
	private BigDecimal idTemplate;	

	private Integer errorCode;
	
	private String errorContext;
	
	private String errMsg;	

	public String getEstremiProtRichiesta() {
		return estremiProtRichiesta;
	}

	public void setEstremiProtRichiesta(String estremiProtRichiesta) {
		this.estremiProtRichiesta = estremiProtRichiesta;
	}



	public BigDecimal getIdUDRisposta() {
		return idUDRisposta;
	}

	public void setIdUDRisposta(BigDecimal idUDRisposta) {
		this.idUDRisposta = idUDRisposta;
	}

	public BigDecimal getIdDocRisposta() {
		return idDocRisposta;
	}

	public void setIdDocRisposta(BigDecimal idDocRisposta) {
		this.idDocRisposta = idDocRisposta;
	}

	public String getEstremiProtRisposta() {
		return estremiProtRisposta;
	}

	public void setEstremiProtRisposta(String estremiProtRisposta) {
		this.estremiProtRisposta = estremiProtRisposta;
	}

	public String getIdUtenteInvioMail() {
		return idUtenteInvioMail;
	}

	public BigDecimal getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(BigDecimal idDominio) {
		this.idDominio = idDominio;
	}

	public void setIdUtenteInvioMail(String idUtenteInvioMail) {
		this.idUtenteInvioMail = idUtenteInvioMail;
	}

	public String getAccountMittenteMail() {
		return accountMittenteMail;
	}

	public void setAccountMittenteMail(String accountMittenteMail) {
		this.accountMittenteMail = accountMittenteMail;
	}

	public String getOggettoMail() {
		return oggettoMail;
	}

	public void setOggettoMail(String oggettoMail) {
		this.oggettoMail = oggettoMail;
	}

	public String getCorpoMail() {
		return corpoMail;
	}

	public void setCorpoMail(String corpoMail) {
		this.corpoMail = corpoMail;
	}

	public Boolean getFlgFileAllegatiMail() {
		return flgFileAllegatiMail;
	}

	public void setFlgFileAllegatiMail(Boolean flgFileAllegatiMail) {
		this.flgFileAllegatiMail = flgFileAllegatiMail;
	}

	public String getNomeTemplate() {
		return nomeTemplate;
	}

	public void setNomeTemplate(String nomeTemplate) {
		this.nomeTemplate = nomeTemplate;
	}

	public String getTipoTemplate() {
		return tipoTemplate;
	}

	public void setTipoTemplate(String tipoTemplate) {
		this.tipoTemplate = tipoTemplate;
	}

	public String getContenutoBarcode() {
		return contenutoBarcode;
	}

	public void setContenutoBarcode(String contenutoBarcode) {
		this.contenutoBarcode = contenutoBarcode;
	}

	public String getTestoInChiaroBarcode() {
		return testoInChiaroBarcode;
	}

	public void setTestoInChiaroBarcode(String testoInChiaroBarcode) {
		this.testoInChiaroBarcode = testoInChiaroBarcode;
	}
	

	public List<String> getListaCertificati() {
		return listaCertificati;
	}

	public void setListaCertificati(List<String> listaCertificati) {
		this.listaCertificati = listaCertificati;
	}

	public List<FileAgibilitaOutBean> getFilesAgibilita() {
		return filesAgibilita;
	}

	public void setFilesAgibilita(List<FileAgibilitaOutBean> filesAgibilita) {
		this.filesAgibilita = filesAgibilita;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorContext() {
		return errorContext;
	}

	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public String getUriTemplate() {
		return uriTemplate;
	}

	public void setUriTemplate(String uriTemplate) {
		this.uriTemplate = uriTemplate;
	}

	public BigDecimal getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(BigDecimal idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getDestinatariMail() {
		return destinatariMail;
	}

	public void setDestinatariMail(String destinatariMail) {
		this.destinatariMail = destinatariMail;
	}

	public String getConnectionToken() {
		return connectionToken;
	}

	public void setConnectionToken(String connectionToken) {
		this.connectionToken = connectionToken;
	}

	

}
