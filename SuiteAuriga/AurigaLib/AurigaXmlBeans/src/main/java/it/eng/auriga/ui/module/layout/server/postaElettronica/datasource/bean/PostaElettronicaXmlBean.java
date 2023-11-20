/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;

public class PostaElettronicaXmlBean {

	@NumeroColonna(numero = "1")
	private String idEmail;
	@NumeroColonna(numero = "3")
	private String flgIo;
	@NumeroColonna(numero = "2")
	private String messageId;
	@NumeroColonna(numero = "4")
	private String idCasella;
	@NumeroColonna(numero = "5")
	private String casellaRicezione;
	@NumeroColonna(numero = "6")
	private String categoria;
	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRicezione;
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInvioClient;
	@NumeroColonna(numero = "9")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInvio;
	@NumeroColonna(numero = "10")
	private Long dimensione;
	@NumeroColonna(numero = "11")
	private String uriEmail;
	@NumeroColonna(numero = "12")
	private String flgSpam;
	@NumeroColonna(numero = "13")
	private String flgStatoSpam;
	@NumeroColonna(numero = "14")
	private String modalitaRicevutaPec;
	@NumeroColonna(numero = "15")
	private String statoConsolidamento;
	@NumeroColonna(numero = "16")
	private Integer nroAllegati;
	@NumeroColonna(numero = "17")
	private Integer nroAllegatiFirmati;
	@NumeroColonna(numero = "18")
	private Integer nroAllegatiNonFirmati;
	@NumeroColonna(numero = "19")
	private String flgEmailFirmata;
	@NumeroColonna(numero = "20")
	private String flgNoAssocAuto;
	@NumeroColonna(numero = "21")
	private String accountMittente;
	@NumeroColonna(numero = "22")
	private String oggetto;
	@NumeroColonna(numero = "23")
	private String corpo;
	@NumeroColonna(numero = "24")
	private String destinatari;
	@NumeroColonna(numero = "25")
	private String destinatariPrimari;
	@NumeroColonna(numero = "26")
	private String destinatariCC;
	@NumeroColonna(numero = "27")
	private String destinatariCCN;
	@NumeroColonna(numero = "28")
	private String avvertimenti;
	@NumeroColonna(numero = "29")
	private String inCaricoA;
	@NumeroColonna(numero = "30")
	private String flgRichConferma;
	@NumeroColonna(numero = "31")
	@TipoData(tipo = Tipo.DATA)
	private Date tsAssegn;
	@NumeroColonna(numero = "67")
	private String messaggioAssegnazione;
	@NumeroColonna(numero = "32")
	private String assegnatario;
	@NumeroColonna(numero = "33")
	private String flgInviataRisposta;
	@NumeroColonna(numero = "34")
	private String flgInoltrata;
	@NumeroColonna(numero = "35")
	private String flgStatoProt;
	@NumeroColonna(numero = "37")
	private String flgNotifInteropEcc;
	@NumeroColonna(numero = "38")
	private String flgNotifInteropConf;
	@NumeroColonna(numero = "39")
	private String flgNotifInteropAgg;
	@NumeroColonna(numero = "54")
	private String flgNotifInteropAnn;
	@NumeroColonna(numero = "40")
	private String livPriorita;

	private String estremiProtCollegati;

	@NumeroColonna(numero = "46")
	private String listEstremiRegProtAssociati;
	@NumeroColonna(numero = "47")
	private String listIdUdProtAssociati;

	private String estremiDocInviato;
	@NumeroColonna(numero = "49")
	private String idEmailCollegata;
	@NumeroColonna(numero = "48")
	private String protocolliDestinatari;
	@NumeroColonna(numero = "55")
	private String allegatiEmail;
	@NumeroColonna(numero = "56")
	private String altIcona;

	@NumeroColonna(numero = "59")
	private String tipoEmail;
	@NumeroColonna(numero = "60")
	private String sottotipoEmail;
	@NumeroColonna(numero = "61")
	private String flgEmailCertificata;
	@NumeroColonna(numero = "62")
	private String flgInteroperabile;
	@NumeroColonna(numero = "57")
	private String protocolloMittente;
	@NumeroColonna(numero = "58")
	private String oggettoProtocolloMittente;
	@NumeroColonna(numero = "63")
	private String iconaMicroCategoria;
	@NumeroColonna(numero = "64")
	private String statoLavorazione;
	@NumeroColonna(numero = "65")
	private Integer gradoUrgenzaContrassegno;
	@NumeroColonna(numero = "66")
	private String idRecDizContrassegno;
	@NumeroColonna(numero = "36")
	private String contrassegno;

	@NumeroColonna(numero = "69")
	private String codAzioneDaFare;
	@NumeroColonna(numero = "68")
	private String azioneDaFare;
	@NumeroColonna(numero = "70")
	private String dettaglioAzioneDaFare;
	@NumeroColonna(numero = "71")
	private String id;

	@NumeroColonna(numero = "72")
	private String progrDownloadStampa;
	@NumeroColonna(numero = "73")
	private String progrOrdinamento;
	@NumeroColonna(numero = "74")
	@TipoData(tipo = Tipo.DATA)
	private Date tsSalvataggioEmail;
	@NumeroColonna(numero = "75")
	private Integer nroGiorniAperta;
	@NumeroColonna(numero = "76")
	private String statoInvio;
	@NumeroColonna(numero = "77")
	private String statoAccettazione;
	@NumeroColonna(numero = "78")
	private String statoConsegna;
	@NumeroColonna(numero = "79")
	private String descrizioneStatoInvio;
	@NumeroColonna(numero = "80")
	private String descrizioneStatoAccettazione;
	@NumeroColonna(numero = "81")
	private String descrizioneStatoConsegna;
	@NumeroColonna(numero = "82")
	private String inCaricoDal;
	@NumeroColonna(numero = "84")
	private String annotazioni;
	@NumeroColonna(numero = "85")
	private String flgRichConfermaLettura;
	@NumeroColonna(numero = "86")
	private String tagApposto;
	
	@NumeroColonna(numero = "87")
	@TipoData(tipo = Tipo.DATA)
	private Date tsChiusura;
	
	@NumeroColonna(numero = "88")
	private String chiusuraEffettuataDa;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getFlgIo() {
		return flgIo;
	}

	public void setFlgIo(String flgIo) {
		this.flgIo = flgIo;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getCasellaRicezione() {
		return casellaRicezione;
	}

	public void setCasellaRicezione(String casellaRicezione) {
		this.casellaRicezione = casellaRicezione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getTsRicezione() {
		return tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}

	public Date getTsInvioClient() {
		return tsInvioClient;
	}

	public void setTsInvioClient(Date tsInvioClient) {
		this.tsInvioClient = tsInvioClient;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public Long getDimensione() {
		return dimensione;
	}

	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}

	public String getUriEmail() {
		return uriEmail;
	}

	public void setUriEmail(String uriEmail) {
		this.uriEmail = uriEmail;
	}

	public String getFlgSpam() {
		return flgSpam;
	}

	public void setFlgSpam(String flgSpam) {
		this.flgSpam = flgSpam;
	}

	public String getFlgStatoSpam() {
		return flgStatoSpam;
	}

	public void setFlgStatoSpam(String flgStatoSpam) {
		this.flgStatoSpam = flgStatoSpam;
	}

	public String getModalitaRicevutaPec() {
		return modalitaRicevutaPec;
	}

	public void setModalitaRicevutaPec(String modalitaRicevutaPec) {
		this.modalitaRicevutaPec = modalitaRicevutaPec;
	}

	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}

	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	public Integer getNroAllegati() {
		return nroAllegati;
	}

	public void setNroAllegati(Integer nroAllegati) {
		this.nroAllegati = nroAllegati;
	}

	public Integer getNroAllegatiFirmati() {
		return nroAllegatiFirmati;
	}

	public void setNroAllegatiFirmati(Integer nroAllegatiFirmati) {
		this.nroAllegatiFirmati = nroAllegatiFirmati;
	}

	public Integer getNroAllegatiNonFirmati() {
		return nroAllegatiNonFirmati;
	}

	public void setNroAllegatiNonFirmati(Integer nroAllegatiNonFirmati) {
		this.nroAllegatiNonFirmati = nroAllegatiNonFirmati;
	}

	public String getFlgEmailFirmata() {
		return flgEmailFirmata;
	}

	public void setFlgEmailFirmata(String flgEmailFirmata) {
		this.flgEmailFirmata = flgEmailFirmata;
	}

	public String getFlgNoAssocAuto() {
		return flgNoAssocAuto;
	}

	public void setFlgNoAssocAuto(String flgNoAssocAuto) {
		this.flgNoAssocAuto = flgNoAssocAuto;
	}

	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}

	public String getDestinatariPrimari() {
		return destinatariPrimari;
	}

	public void setDestinatariPrimari(String destinatariPrimari) {
		this.destinatariPrimari = destinatariPrimari;
	}

	public String getDestinatariCC() {
		return destinatariCC;
	}

	public void setDestinatariCC(String destinatariCC) {
		this.destinatariCC = destinatariCC;
	}

	public String getDestinatariCCN() {
		return destinatariCCN;
	}

	public void setDestinatariCCN(String destinatariCCN) {
		this.destinatariCCN = destinatariCCN;
	}

	public String getAvvertimenti() {
		return avvertimenti;
	}

	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}

	public String getFlgRichConferma() {
		return flgRichConferma;
	}

	public void setFlgRichConferma(String flgRichConferma) {
		this.flgRichConferma = flgRichConferma;
	}

	public Date getTsAssegn() {
		return tsAssegn;
	}

	public void setTsAssegn(Date tsAssegn) {
		this.tsAssegn = tsAssegn;
	}

	public String getMessaggioAssegnazione() {
		return messaggioAssegnazione;
	}

	public void setMessaggioAssegnazione(String messaggioAssegnazione) {
		this.messaggioAssegnazione = messaggioAssegnazione;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public String getFlgInviataRisposta() {
		return flgInviataRisposta;
	}

	public void setFlgInviataRisposta(String flgInviataRisposta) {
		this.flgInviataRisposta = flgInviataRisposta;
	}

	public String getFlgInoltrata() {
		return flgInoltrata;
	}

	public void setFlgInoltrata(String flgInoltrata) {
		this.flgInoltrata = flgInoltrata;
	}

	public String getFlgStatoProt() {
		return flgStatoProt;
	}

	public void setFlgStatoProt(String flgStatoProt) {
		this.flgStatoProt = flgStatoProt;
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

	public String getLivPriorita() {
		return livPriorita;
	}

	public void setLivPriorita(String livPriorita) {
		this.livPriorita = livPriorita;
	}

	public String getEstremiProtCollegati() {
		return estremiProtCollegati;
	}

	public void setEstremiProtCollegati(String estremiProtCollegati) {
		this.estremiProtCollegati = estremiProtCollegati;
	}

	public String getEstremiDocInviato() {
		return estremiDocInviato;
	}

	public void setEstremiDocInviato(String estremiDocInviato) {
		this.estremiDocInviato = estremiDocInviato;
	}

	public String getIdEmailCollegata() {
		return idEmailCollegata;
	}

	public void setIdEmailCollegata(String idEmailCollegata) {
		this.idEmailCollegata = idEmailCollegata;
	}

	public String getProtocolliDestinatari() {
		return protocolliDestinatari;
	}

	public void setProtocolliDestinatari(String protocolliDestinatari) {
		this.protocolliDestinatari = protocolliDestinatari;
	}

	public String getAllegatiEmail() {
		return allegatiEmail;
	}

	public void setAllegatiEmail(String allegatiEmail) {
		this.allegatiEmail = allegatiEmail;
	}

	public String getAltIcona() {
		return altIcona;
	}

	public void setAltIcona(String altIcona) {
		this.altIcona = altIcona;
	}

	public String getTipoEmail() {
		return tipoEmail;
	}

	public void setTipoEmail(String tipoEmail) {
		this.tipoEmail = tipoEmail;
	}

	public String getSottotipoEmail() {
		return sottotipoEmail;
	}

	public void setSottotipoEmail(String sottotipoEmail) {
		this.sottotipoEmail = sottotipoEmail;
	}

	public String getFlgEmailCertificata() {
		return flgEmailCertificata;
	}

	public void setFlgEmailCertificata(String flgEmailCertificata) {
		this.flgEmailCertificata = flgEmailCertificata;
	}

	public String getFlgInteroperabile() {
		return flgInteroperabile;
	}

	public void setFlgInteroperabile(String flgInteroperabile) {
		this.flgInteroperabile = flgInteroperabile;
	}

	public String getProtocolloMittente() {
		return protocolloMittente;
	}

	public void setProtocolloMittente(String protocolloMittente) {
		this.protocolloMittente = protocolloMittente;
	}

	public String getOggettoProtocolloMittente() {
		return oggettoProtocolloMittente;
	}

	public void setOggettoProtocolloMittente(String oggettoProtocolloMittente) {
		this.oggettoProtocolloMittente = oggettoProtocolloMittente;
	}

	public String getIconaMicroCategoria() {
		return iconaMicroCategoria;
	}

	public void setIconaMicroCategoria(String iconaMicroCategoria) {
		this.iconaMicroCategoria = iconaMicroCategoria;
	}

	public String getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(String statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public Integer getGradoUrgenzaContrassegno() {
		return gradoUrgenzaContrassegno;
	}

	public void setGradoUrgenzaContrassegno(Integer gradoUrgenzaContrassegno) {
		this.gradoUrgenzaContrassegno = gradoUrgenzaContrassegno;
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

	public String getCodAzioneDaFare() {
		return codAzioneDaFare;
	}

	public void setCodAzioneDaFare(String codAzioneDaFare) {
		this.codAzioneDaFare = codAzioneDaFare;
	}

	public String getAzioneDaFare() {
		return azioneDaFare;
	}

	public void setAzioneDaFare(String azioneDaFare) {
		this.azioneDaFare = azioneDaFare;
	}

	public String getDettaglioAzioneDaFare() {
		return dettaglioAzioneDaFare;
	}

	public void setDettaglioAzioneDaFare(String dettaglioAzioneDaFare) {
		this.dettaglioAzioneDaFare = dettaglioAzioneDaFare;
	}

	public String getListEstremiRegProtAssociati() {
		return listEstremiRegProtAssociati;
	}

	public void setListEstremiRegProtAssociati(String listEstremiRegProtAssociati) {
		this.listEstremiRegProtAssociati = listEstremiRegProtAssociati;
	}

	public String getListIdUdProtAssociati() {
		return listIdUdProtAssociati;
	}

	public void setListIdUdProtAssociati(String listIdUdProtAssociati) {
		this.listIdUdProtAssociati = listIdUdProtAssociati;
	}

	public String getProgrDownloadStampa() {
		return progrDownloadStampa;
	}

	public void setProgrDownloadStampa(String progrDownloadStampa) {
		this.progrDownloadStampa = progrDownloadStampa;
	}

	public String getProgrOrdinamento() {
		return progrOrdinamento;
	}

	public void setProgrOrdinamento(String progrOrdinamento) {
		this.progrOrdinamento = progrOrdinamento;
	}

	public Date getTsSalvataggioEmail() {
		return tsSalvataggioEmail;
	}

	public void setTsSalvataggioEmail(Date tsSalvataggioEmail) {
		this.tsSalvataggioEmail = tsSalvataggioEmail;
	}

	public String getStatoInvio() {
		return statoInvio;
	}

	public void setStatoInvio(String statoInvio) {
		this.statoInvio = statoInvio;
	}

	public String getStatoConsegna() {
		return statoConsegna;
	}

	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}

	public String getStatoAccettazione() {
		return statoAccettazione;
	}

	public void setStatoAccettazione(String statoAccettazione) {
		this.statoAccettazione = statoAccettazione;
	}

	public String getDescrizioneStatoInvio() {
		return descrizioneStatoInvio;
	}

	public void setDescrizioneStatoInvio(String descrizioneStatoInvio) {
		this.descrizioneStatoInvio = descrizioneStatoInvio;
	}

	public String getDescrizioneStatoConsegna() {
		return descrizioneStatoConsegna;
	}

	public void setDescrizioneStatoConsegna(String descrizioneStatoConsegna) {
		this.descrizioneStatoConsegna = descrizioneStatoConsegna;
	}

	public String getDescrizioneStatoAccettazione() {
		return descrizioneStatoAccettazione;
	}

	public void setDescrizioneStatoAccettazione(String descrizioneStatoAccettazione) {
		this.descrizioneStatoAccettazione = descrizioneStatoAccettazione;
	}

	public Integer getNroGiorniAperta() {
		return nroGiorniAperta;
	}

	public void setNroGiorniAperta(Integer nroGiorniAperta) {
		this.nroGiorniAperta = nroGiorniAperta;
	}

	public String getInCaricoA() {
		return inCaricoA;
	}

	public void setInCaricoA(String inCaricoA) {
		this.inCaricoA = inCaricoA;
	}

	public String getInCaricoDal() {
		return inCaricoDal;
	}

	public void setInCaricoDal(String inCaricoDal) {
		this.inCaricoDal = inCaricoDal;
	}

	/**
	 * @return the annotazioni
	 */
	public String getAnnotazioni() {
		return annotazioni;
	}

	/**
	 * @param annotazioni
	 *            the annotazioni to set
	 */
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	/**
	 * @return the flgRichConfermaLettura
	 */
	public String getFlgRichConfermaLettura() {
		return flgRichConfermaLettura;
	}

	/**
	 * @param flgRichConfermaLettura
	 *            the flgRichConfermaLettura to set
	 */
	public void setFlgRichConfermaLettura(String flgRichConfermaLettura) {
		this.flgRichConfermaLettura = flgRichConfermaLettura;
	}

	/**
	 * @return the tagApposto
	 */
	public String getTagApposto() {
		return tagApposto;
	}

	/**
	 * @param tagApposto the tagApposto to set
	 */
	public void setTagApposto(String tagApposto) {
		this.tagApposto = tagApposto;
	}

	public Date getTsChiusura() {
		return tsChiusura;
	}

	public void setTsChiusura(Date tsChiusura) {
		this.tsChiusura = tsChiusura;
	}

	public String getChiusuraEffettuataDa() {
		return chiusuraEffettuataDa;
	}

	public void setChiusuraEffettuataDa(String chiusuraEffettuataDa) {
		this.chiusuraEffettuataDa = chiusuraEffettuataDa;
	}

}
