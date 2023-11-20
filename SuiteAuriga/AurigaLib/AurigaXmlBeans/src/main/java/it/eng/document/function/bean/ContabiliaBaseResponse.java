/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaBaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ContabiliaEnte ente;
	private List<ContabiliaErrore> errori;
	private ContabiliaEsito esito;
	private List<ContabiliaMessaggio> messaggi;
	
	public ContabiliaEnte getEnte() {
		return ente;
	}
	
	public void setEnte(ContabiliaEnte ente) {
		this.ente = ente;
	}
	
	public List<ContabiliaErrore> getErrori() {
		return errori;
	}
	
	public void setErrori(List<ContabiliaErrore> errori) {
		this.errori = errori;
	}
	
	public ContabiliaEsito getEsito() {
		return esito;
	}
	
	public void setEsito(ContabiliaEsito esito) {
		this.esito = esito;
	}
	
	public List<ContabiliaMessaggio> getMessaggi() {
		return messaggi;
	}
	
	public void setMessaggi(List<ContabiliaMessaggio> messaggi) {
		this.messaggi = messaggi;
	}
	
	@Override
	public String toString() {
		return "ContabiliaBaseResponse [ente=" + ente + ", errori=" + errori + ", esito=" + esito + ", messaggi="
				+ messaggi + "]";
	}
	
}
