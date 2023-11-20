/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class DettaglioPostaElettronicaEmailCollegata {
	
	@NumeroColonna(numero = "1")
	private String flgRigaEmailVsDest;
	@NumeroColonna(numero = "2")
	private String idEmail;
	@NumeroColonna(numero = "3")
	private String flgIO;		
	@NumeroColonna(numero = "4")
	private String categoria;		
	@NumeroColonna(numero = "5")
	private String tipo;		
	@NumeroColonna(numero = "6")
	private String tsInvio;
	@NumeroColonna(numero = "7")
	private String tsScarico;				
	@NumeroColonna(numero = "8")
	private String indirizzoMitt;
	@NumeroColonna(numero = "9")
	private String cognomeNomeMitt;
	//@NumeroColonna(numero = "10")
	//private String statoConsolidamento;	// Deprecato			
	@NumeroColonna(numero = "11")
	private String estremiProtMitt;
	@NumeroColonna(numero = "12")
	private String tipoDest;								
	@NumeroColonna(numero = "13")
	private String indirizzoDest;						
	//@NumeroColonna(numero = "14")
	//private String statoConsolidamentoDest;	// Deprecato							
	@NumeroColonna(numero = "15")
	private String flgNotifInteropEcc;		
	@NumeroColonna(numero = "16")
	private String flgNotifInteropConf;		
	@NumeroColonna(numero = "17")
	private String flgNotifInteropAgg;		
	@NumeroColonna(numero = "18")
	private String flgNotifInteropAnn;
	@NumeroColonna(numero = "19")
	private String flgRicevutaLettura;
	@NumeroColonna(numero = "20")
	private String estremiProtDest;
	@NumeroColonna(numero = "21")
	private String sottotipo;
	@NumeroColonna(numero = "22")
	private String flgPEC;
	@NumeroColonna(numero = "23")
	private String flgInteroperabile;
	@NumeroColonna(numero = "24")
	private String iconaMicroCategoria;
	@NumeroColonna(numero = "25")
	private String statoLavorazione;
	@NumeroColonna(numero = "26")
	private String idRecDizContrassegno;
	@NumeroColonna(numero = "27")
	private String contrassegno;
	@NumeroColonna(numero = "28")
	private String id;
	@NumeroColonna(numero = "29")
	private String progrDownloadStampa;
	@NumeroColonna(numero = "30")
	private String tsIns;
	@NumeroColonna(numero = "31")
	private String iconaStatoInvio;
	@NumeroColonna(numero = "32")
	private String statoInvio;
	@NumeroColonna(numero = "33")
	private String iconaStatoAccettazione;
	@NumeroColonna(numero = "34")
	private String statoAccettazione;
	@NumeroColonna(numero = "35")
	private String iconaStatoConsegna;
	@NumeroColonna(numero = "36")
	private String statoConsegna;
	@NumeroColonna(numero = "37")
	private String iconaStatoConsegnaDestinatario;
	@NumeroColonna(numero = "38")
	private String statoConsegnaDestinatario;

	
	
	public String getFlgRigaEmailVsDest() {
		return flgRigaEmailVsDest;
	}
	public void setFlgRigaEmailVsDest(String flgRigaEmailVsDest) {
		this.flgRigaEmailVsDest = flgRigaEmailVsDest;
	}
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getFlgIO() {
		return flgIO;
	}
	public void setFlgIO(String flgIO) {
		this.flgIO = flgIO;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTsInvio() {
		return tsInvio;
	}
	public void setTsInvio(String tsInvio) {
		this.tsInvio = tsInvio;
	}
	public String getTsScarico() {
		return tsScarico;
	}
	public void setTsScarico(String tsScarico) {
		this.tsScarico = tsScarico;
	}
	public String getIndirizzoMitt() {
		return indirizzoMitt;
	}
	public void setIndirizzoMitt(String indirizzoMitt) {
		this.indirizzoMitt = indirizzoMitt;
	}
	public String getCognomeNomeMitt() {
		return cognomeNomeMitt;
	}
	public void setCognomeNomeMitt(String cognomeNomeMitt) {
		this.cognomeNomeMitt = cognomeNomeMitt;
	}
	
	public String getEstremiProtMitt() {
		return estremiProtMitt;
	}
	public void setEstremiProtMitt(String estremiProtMitt) {
		this.estremiProtMitt = estremiProtMitt;
	}
	public String getTipoDest() {
		return tipoDest;
	}
	public void setTipoDest(String tipoDest) {
		this.tipoDest = tipoDest;
	}
	public String getIndirizzoDest() {
		return indirizzoDest;
	}
	public void setIndirizzoDest(String indirizzoDest) {
		this.indirizzoDest = indirizzoDest;
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
	public String getEstremiProtDest() {
		return estremiProtDest;
	}
	public void setEstremiProtDest(String estremiProtDest) {
		this.estremiProtDest = estremiProtDest;
	}
	public String getSottotipo() {
		return sottotipo;
	}
	public void setSottotipo(String sottotipo) {
		this.sottotipo = sottotipo;
	}
	public String getFlgPEC() {
		return flgPEC;
	}
	public void setFlgPEC(String flgPEC) {
		this.flgPEC = flgPEC;
	}
	public String getFlgInteroperabile() {
		return flgInteroperabile;
	}
	public void setFlgInteroperabile(String flgInteroperabile) {
		this.flgInteroperabile = flgInteroperabile;
	}
	public String getIconaMicroCategoria() {
		return iconaMicroCategoria;
	}
	public void setIconaMicroCategoria(String iconaMicroCategoria) {
		this.iconaMicroCategoria = iconaMicroCategoria;
	}
	public String getIdRecDizContrassegno() {
		return idRecDizContrassegno;
	}
	public void setIdRecDizContrassegno(String idRecDizContrassegno) {
		this.idRecDizContrassegno = idRecDizContrassegno;
	}
	public String getContrassegno() {
		return contrassegno;
	}
	public void setContrassegno(String contrassegno) {
		this.contrassegno = contrassegno;
	}
	public String getStatoLavorazione() {
		return statoLavorazione;
	}
	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProgrDownloadStampa() {
		return progrDownloadStampa;
	}
	public void setProgrDownloadStampa(String progrDownloadStampa) {
		this.progrDownloadStampa = progrDownloadStampa;
	}
	public String getTsIns() {
		return tsIns;
	}
	public void setTsIns(String tsIns) {
		this.tsIns = tsIns;
	}
	public String getStatoInvio() {
		return statoInvio;
	}
	public void setStatoInvio(String statoInvio) {
		this.statoInvio = statoInvio;
	}
	public String getStatoAccettazione() {
		return statoAccettazione;
	}
	public void setStatoAccettazione(String statoAccettazione) {
		this.statoAccettazione = statoAccettazione;
	}
	public String getStatoConsegna() {
		return statoConsegna;
	}
	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}
	public String getIconaStatoInvio() {
		return iconaStatoInvio;
	}
	public void setIconaStatoInvio(String iconaStatoInvio) {
		this.iconaStatoInvio = iconaStatoInvio;
	}
	public String getIconaStatoAccettazione() {
		return iconaStatoAccettazione;
	}
	public void setIconaStatoAccettazione(String iconaStatoAccettazione) {
		this.iconaStatoAccettazione = iconaStatoAccettazione;
	}
	public String getIconaStatoConsegna() {
		return iconaStatoConsegna;
	}
	public void setIconaStatoConsegna(String iconaStatoConsegna) {
		this.iconaStatoConsegna = iconaStatoConsegna;
	}
	public String getStatoConsegnaDestinatario() {
		return statoConsegnaDestinatario;
	}
	public void setStatoConsegnaDestinatario(String statoConsegnaDestinatario) {
		this.statoConsegnaDestinatario = statoConsegnaDestinatario;
	}
	public String getIconaStatoConsegnaDestinatario() {
		return iconaStatoConsegnaDestinatario;
	}
	public void setIconaStatoConsegnaDestinatario(
			String iconaStatoConsegnaDestinatario) {
		this.iconaStatoConsegnaDestinatario = iconaStatoConsegnaDestinatario;
	}
	
	
	
	
}