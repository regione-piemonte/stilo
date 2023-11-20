/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;


/**
 * The persistent class for the WS_TRACE database table.
 * 
 */
@Entity
@Table(name="WS_TRACE")
public class WSTrace implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "TS_INVOCAZIONE")
	private Date tsInvocazione;
	@Column(name = "TEMPO_RISPOSTA_WS")
	private String tempoRispostaWs;
	@Column(name = "TEMPO_RISPOSTA_STORE")
	private String tempoRispostaStore;
	@Column(name = "TEMPO_RISPOSTA_LOGIN")
	private String tempoRispostaLogIn;
	@Column(name = "SERVICE")
	private String service; 
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "APPLICAZIONE")
	private String applicazione;
	@Column(name = "ISTANZA")
	private String instanza;
	@Column(name = "XML_REQUEST")
	private String xmlRequest;
	@Column(name = "XML_RESPONSE")
	private String xmlResponse;
	@Column(name = "FILES_INPUT")
	private String filesInput;
	@Column(name = "HASH")
	private String hash;
	@Column(name = "RISULTATO")
	private String risultato;
	@Column(name = "FLG_IN_ERRORE", nullable = false, precision = 1, scale = 0)
	private Boolean flgInErrore;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTsInvocazione() {
		return tsInvocazione;
	}
	public void setTsInvocazione(Date tsInvocazione) {
		this.tsInvocazione = tsInvocazione;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	public String getInstanza() {
		return instanza;
	}
	public void setInstanza(String instanza) {
		this.instanza = instanza;
	}
	public String getXmlRequest() {
		return xmlRequest;
	}
	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}
	public String getXmlResponse() {
		return xmlResponse;
	}
	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}
	public String getFilesInput() {
		return filesInput;
	}
	public void setFilesInput(String filesInput) {
		this.filesInput = filesInput;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getRisultato() {
		return risultato;
	}
	public void setRisultato(String risultato) {
		this.risultato = risultato;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Boolean getFlgInErrore() {
		return flgInErrore;
	}
	public void setFlgInErrore(Boolean flgInErrore) {
		this.flgInErrore = flgInErrore;
	}
	public String getTempoRispostaWs() {
		return tempoRispostaWs;
	}
	public void setTempoRispostaWs(String tempoRispostaWs) {
		this.tempoRispostaWs = tempoRispostaWs;
	}
	public String getTempoRispostaStore() {
		return tempoRispostaStore;
	}
	public void setTempoRispostaStore(String tempoRispostaStore) {
		this.tempoRispostaStore = tempoRispostaStore;
	}
	public String getTempoRispostaLogIn() {
		return tempoRispostaLogIn;
	}
	public void setTempoRispostaLogIn(String tempoRispostaLogIn) {
		this.tempoRispostaLogIn = tempoRispostaLogIn;
	}

}