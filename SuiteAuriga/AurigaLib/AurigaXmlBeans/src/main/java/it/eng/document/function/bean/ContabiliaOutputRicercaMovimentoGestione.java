/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaOutputRicercaMovimentoGestione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ContabiliaRicercaMovimentoGestioneStiloResponse entry;
	private Integer totaleRisultati;
	private boolean ok;
	private String messaggio;
	private boolean timeout;
	private boolean rispostaNonRicevuta;
	
	public ContabiliaRicercaMovimentoGestioneStiloResponse getEntry() {
		return entry;
	}
	
	public void setEntry(ContabiliaRicercaMovimentoGestioneStiloResponse entry) {
		this.entry = entry;
	}
	
	public Integer getTotaleRisultati() {
		return totaleRisultati;
	}
	
	public void setTotaleRisultati(Integer totaleRisultati) {
		this.totaleRisultati = totaleRisultati;
	}
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public String getMessaggio() {
		return messaggio;
	}
	
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	public boolean isTimeout() {
		return timeout;
	}
	
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	
	public boolean isRispostaNonRicevuta() {
		return rispostaNonRicevuta;
	}
	
	public void setRispostaNonRicevuta(boolean rispostaNonRicevuta) {
		this.rispostaNonRicevuta = rispostaNonRicevuta;
	}
	
	@Override
	public String toString() {
		return "ContabiliaOutputRicercaMovimentoGestione [entry=" + entry + ", ok=" + ok + ", messaggio=" + messaggio
				+ ", timeout=" + timeout + ", rispostaNonRicevuta=" + rispostaNonRicevuta + "]";
	}
	
}
