/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class PostaInUscitaRegistrazioneBean {

	private String idEmail;
	private String tipoCasellaMittente;			
	private Date dataInvio;
	private String destinatari;
	private String statoConsolidamento;
	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getTipoCasellaMittente() {
		return tipoCasellaMittente;
	}
	public void setTipoCasellaMittente(String tipoCasellaMittente) {
		this.tipoCasellaMittente = tipoCasellaMittente;
	}	
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}
	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	
}
