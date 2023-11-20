/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */


public class DettaglioPostaElettronicaDestinatario {
	
	@NumeroColonna(numero = "1")
	private String indirizzo;	
	@NumeroColonna(numero = "2")
	private String statoConsolidamento;
	@NumeroColonna(numero = "3")
	private String flgNotifInteropEcc;		
	@NumeroColonna(numero = "4")
	private String flgNotifInteropConf;		
	@NumeroColonna(numero = "5")
	private String flgNotifInteropAgg;		
	@NumeroColonna(numero = "6")
	private String flgNotifInteropAnn;
	@NumeroColonna(numero = "7")
	private String flgRicevutaLettura;
	@NumeroColonna(numero = "8")
	private String estremiProt;
	@NumeroColonna(numero = "9")
	private String iconaStatoConsegna;
	@NumeroColonna(numero = "10")
	private String statoConsegna;
	@NumeroColonna(numero = "11")
	private String msgErrMancataConsegnaDestinatario;
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}
	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}
	public String getFlgNotifInteropEcc() {
		return flgNotifInteropEcc;
	}
	public void setFlgNotifInteropEcc(String flgNotifInteropEcc) {
		this.flgNotifInteropEcc = flgNotifInteropEcc;
	}
	public String getFlgNotifInteropConf() {
		return flgNotifInteropConf;
	}
	public void setFlgNotifInteropConf(String flgNotifInteropConf) {
		this.flgNotifInteropConf = flgNotifInteropConf;
	}
	public String getFlgNotifInteropAgg() {
		return flgNotifInteropAgg;
	}
	public void setFlgNotifInteropAgg(String flgNotifInteropAgg) {
		this.flgNotifInteropAgg = flgNotifInteropAgg;
	}
	public String getFlgNotifInteropAnn() {
		return flgNotifInteropAnn;
	}
	public void setFlgNotifInteropAnn(String flgNotifInteropAnn) {
		this.flgNotifInteropAnn = flgNotifInteropAnn;
	}
	public String getFlgRicevutaLettura() {
		return flgRicevutaLettura;
	}
	public void setFlgRicevutaLettura(String flgRicevutaLettura) {
		this.flgRicevutaLettura = flgRicevutaLettura;
	}
	public String getEstremiProt() {
		return estremiProt;
	}
	public void setEstremiProt(String estremiProt) {
		this.estremiProt = estremiProt;
	}
	public String getIconaStatoConsegna() {
		return iconaStatoConsegna;
	}
	public void setIconaStatoConsegna(String iconaStatoConsegna) {
		this.iconaStatoConsegna = iconaStatoConsegna;
	}
	public String getStatoConsegna() {
		return statoConsegna;
	}
	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}
	public String getMsgErrMancataConsegnaDestinatario() {
		return msgErrMancataConsegnaDestinatario;
	}
	public void setMsgErrMancataConsegnaDestinatario(String msgErrMancataConsegnaDestinatario) {
		this.msgErrMancataConsegnaDestinatario = msgErrMancataConsegnaDestinatario;
	}
	
	
}