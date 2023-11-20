/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="email")
public class RigaResultTrovaEmail {
	
	@NumeroColonna(numero = "1")
	@XmlElement(name="idEmail")
	private String idEmail;
	
	@NumeroColonna(numero = "2")
	@XmlElement(name="messageId")
	private String messageId;
	
	@NumeroColonna(numero = "3")
	@XmlElement(name="flgIo")
	private String flgIo;
	
	@NumeroColonna(numero = "4")
	@XmlElement(name="idCasella")
	private String idCasella;
	
	@NumeroColonna(numero = "5")
	@XmlElement(name="casellaRicezione")
	private String casellaRicezione;
	
	@NumeroColonna(numero = "6")
	@XmlElement(name="categoria")
	private String categoria;
	
	@NumeroColonna(numero = "7") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsRicezione")
	private Date tsRicezione;
	
	@NumeroColonna(numero = "8") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsInvioClient")
	private Date tsInvioClient;
	
	@NumeroColonna(numero = "9") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsInvio")
	private Date tsInvio;
	
	@NumeroColonna(numero = "10")
	@XmlElement(name="dimensione")
	private Long dimensione;
	
	@NumeroColonna(numero = "11")
	@XmlElement(name="uriEmail")
	private String uriEmail;
	
	@NumeroColonna(numero = "12")
	@XmlElement(name="flgSpam")
	private String flgSpam;
	
	@NumeroColonna(numero = "13")
	@XmlElement(name="flgStatoSpam")
	private String flgStatoSpam;
	
	@NumeroColonna(numero = "14")
	@XmlElement(name="modalitaRicevutaPec")
	private String modalitaRicevutaPec;
	
	@NumeroColonna(numero = "15")
	@XmlElement(name="statoConsolidamento")
	private String statoConsolidamento;
	
	@NumeroColonna(numero = "16")
	@XmlElement(name="nroAllegati")
	private Integer nroAllegati;
	
	@NumeroColonna(numero = "17")
	@XmlElement(name="nroAllegatiFirmati")
	private Integer nroAllegatiFirmati;
	
	@NumeroColonna(numero = "18")
	@XmlElement(name="nroAllegatiNonFirmati")
	private Integer nroAllegatiNonFirmati;
	
	@NumeroColonna(numero = "19")
	@XmlElement(name="flgEmailFirmata")
	private String flgEmailFirmata;
	
	@NumeroColonna(numero = "20")
	@XmlElement(name="flgNoAssocAuto")
	private String flgNoAssocAuto;
	
	@NumeroColonna(numero = "21")
	@XmlElement(name="accountMittente")
	private String accountMittente;
	
	@NumeroColonna(numero = "22")
	@XmlElement(name="oggetto")
	private String oggetto;
	
	@NumeroColonna(numero = "23")
	@XmlElement(name="corpo")
	private String corpo;
	
	@NumeroColonna(numero = "24")
	@XmlElement(name="destinatari")
	private String destinatari;
	
	@NumeroColonna(numero = "25")
	@XmlElement(name="destinatariPrimari")
	private String destinatariPrimari;
	
	@NumeroColonna(numero = "26")
	@XmlElement(name="destinatariCC")
	private String destinatariCC;
	
	@NumeroColonna(numero = "27")
	@XmlElement(name="destinatariCCN")
	private String destinatariCCN;
	
	@NumeroColonna(numero = "28")
	@XmlElement(name="avvertimenti")
	private String avvertimenti;
	
	@NumeroColonna(numero = "29")
	@XmlElement(name="inCaricoA")
	private String inCaricoA;
	
	@NumeroColonna(numero = "30")
	@XmlElement(name="flgRichConferma")
	private String flgRichConferma;
	
	@NumeroColonna(numero = "31") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsAssegn")
	private Date tsAssegn;
	
	@NumeroColonna(numero = "32")
	@XmlElement(name="assegnatario")
	private String assegnatario;
	
	@NumeroColonna(numero = "33")
	@XmlElement(name="flgInviataRisposta")
	private String flgInviataRisposta;
	
	@NumeroColonna(numero = "34")
	@XmlElement(name="flgInoltrata")
	private String flgInoltrata;
	
	@NumeroColonna(numero = "35")
	@XmlElement(name="flgStatoProt")
	private String flgStatoProt;
	
	@NumeroColonna(numero = "36")
	@XmlElement(name="contrassegno")
	private String contrassegno;
	
	@NumeroColonna(numero = "37")
	@XmlElement(name="flgNotifInteropEcc")
	private String flgNotifInteropEcc;
	
	@NumeroColonna(numero = "38")
	@XmlElement(name="flgNotifInteropConf")
	private String flgNotifInteropConf;
	
	@NumeroColonna(numero = "39")
	@XmlElement(name="flgNotifInteropAgg")
	private String flgNotifInteropAgg;
	
	@NumeroColonna(numero = "40")
	@XmlElement(name="livPriorita")
	private String livPriorita;
	
	@NumeroColonna(numero = "41")
	@XmlElement(name="destinatariInvio")
	private String destinatariInvio;
	
	@NumeroColonna(numero = "42")
	@XmlElement(name="destinatariInvioInA")
	private String destinatariInvioInA;
	
	@NumeroColonna(numero = "43")
	@XmlElement(name="destinatariInvioInCc")
	private String destinatariInvioInCc;
	
	@NumeroColonna(numero = "44")
	@XmlElement(name="destinatariInvioInCcn")
	private String destinatariInvioInCcn;
	
	@NumeroColonna(numero = "45")
	@XmlElement(name="folder")
	private String folder;

	@NumeroColonna(numero = "46")
	@XmlElement(name="listEstremiRegProtAssociati")
	private String listEstremiRegProtAssociati;
	
	@NumeroColonna(numero = "47")
	@XmlElement(name="listIdUdProtAssociati")
	private String listIdUdProtAssociati;
	
	@NumeroColonna(numero = "48")
	@XmlElement(name="protocolliDestinatari")
	private String protocolliDestinatari;
	
	@NumeroColonna(numero = "49")
	@XmlElement(name="idEmailCollegata")
	private String idEmailCollegata;
	
	@NumeroColonna(numero = "50")
	@XmlElement(name="stato")
	private String stato;
	
	@NumeroColonna(numero = "51")
	@XmlElement(name="flgCommenti")
	private String flgCommenti;

	@NumeroColonna(numero = "52")
	@XmlElement(name="flgAssegnataUtente")
	private String flgAssegnataUtente;
	
	@NumeroColonna(numero = "53")
	@XmlElement(name="flgAssegnataUtenteUO")
	private String flgAssegnataUtenteUO;

	@NumeroColonna(numero = "54")
	@XmlElement(name="flgNotifInteropAnn")
	private String flgNotifInteropAnn;

	@NumeroColonna(numero = "55")
	@XmlElement(name="allegatiEmail")
	private String allegatiEmail;
	
	@NumeroColonna(numero = "56")
	@XmlElement(name="altIcona")
	private String altIcona;
	
	@NumeroColonna(numero = "57")
	@XmlElement(name="protocolloMittente")
	private String protocolloMittente;
	
	@NumeroColonna(numero = "58")
	@XmlElement(name="oggettoProtocolloMittente")
	private String oggettoProtocolloMittente;

	@NumeroColonna(numero = "59")
	@XmlElement(name="tipoEmail")
	private String tipoEmail;
	
	@NumeroColonna(numero = "60")
	@XmlElement(name="sottotipoEmail")
	private String sottotipoEmail;
	
	@NumeroColonna(numero = "61")
	@XmlElement(name="flgEmailCertificata")
	private String flgEmailCertificata;
	
	@NumeroColonna(numero = "62")
	@XmlElement(name="flgInteroperabile")
	private String flgInteroperabile;
	
	@NumeroColonna(numero = "63")
	@XmlElement(name="iconaMicroCategoria")
	private String iconaMicroCategoria;
	
	@NumeroColonna(numero = "64")
	@XmlElement(name="statoLavorazione")
	private String statoLavorazione;
	
	@NumeroColonna(numero = "65")
	@XmlElement(name="gradoUrgenzaContrassegno")
	private Integer gradoUrgenzaContrassegno;
	
	@NumeroColonna(numero = "66")
	@XmlElement(name="idRecDizContrassegno")
	private String idRecDizContrassegno;

	@NumeroColonna(numero = "67")
	@XmlElement(name="messaggioAssegnazione")
	private String messaggioAssegnazione;
	
	@NumeroColonna(numero = "68")
	@XmlElement(name="azioneDaFare")
	private String azioneDaFare;

	@NumeroColonna(numero = "69")
	@XmlElement(name="codAzioneDaFare")
	private String codAzioneDaFare;

	@NumeroColonna(numero = "70")
	@XmlElement(name="dettaglioAzioneDaFare")
	private String dettaglioAzioneDaFare;
	
	@NumeroColonna(numero = "71")
	@XmlElement(name="id")
	private String id;

	@NumeroColonna(numero = "72")
	@XmlElement(name="progrDownloadStampa")
	private String progrDownloadStampa;
	
	@NumeroColonna(numero = "73")
	@XmlElement(name="progrOrdinamento")
	private String progrOrdinamento;
	
	@NumeroColonna(numero = "74") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsSalvataggioEmail")
	private Date tsSalvataggioEmail;
	
	@NumeroColonna(numero = "75")
	@XmlElement(name="nroGiorniAperta")
	private Integer nroGiorniAperta;
	
	@NumeroColonna(numero = "76")
	@XmlElement(name="statoInvio")
	private String statoInvio;
	
	@NumeroColonna(numero = "77")
	@XmlElement(name="statoAccettazione")
	private String statoAccettazione;
	
	@NumeroColonna(numero = "78")
	@XmlElement(name="statoConsegna")
	private String statoConsegna;
	
	@NumeroColonna(numero = "79")
	@XmlElement(name="descrizioneStatoInvio")
	private String descrizioneStatoInvio;
	
	@NumeroColonna(numero = "80")
	@XmlElement(name="descrizioneStatoAccettazione")
	private String descrizioneStatoAccettazione;
	
	@NumeroColonna(numero = "81")
	@XmlElement(name="descrizioneStatoConsegna")
	private String descrizioneStatoConsegna;
	
	@NumeroColonna(numero = "82")
	@XmlElement(name="inCaricoDal")
	private String inCaricoDal;
	
	@NumeroColonna(numero = "83") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsStatoLavorazione")
	private Date tsStatoLavorazione;
	
	@NumeroColonna(numero = "84")
	@XmlElement(name="annotazioni")
	private String annotazioni;
	
	@NumeroColonna(numero = "85")
	@XmlElement(name="flgRichConfermaLettura")
	private String flgRichConfermaLettura;
	
	@NumeroColonna(numero = "86")
	@XmlElement(name="tagApposto")
	private String tagApposto;

	@NumeroColonna(numero = "87") @TipoData(tipo = Tipo.DATA)
	@XmlElement(name="tsChiusura")
	private Date tsChiusura;

	@NumeroColonna(numero = "88")
	@XmlElement(name="operatoreChiusura")
	private String operatoreChiusura;
	

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

	public String getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public String getFlgRichConfermaLettura() {
		return flgRichConfermaLettura;
	}
	public void setFlgRichConfermaLettura(String flgRichConfermaLettura) {
		this.flgRichConfermaLettura = flgRichConfermaLettura;
	}

	public String getTagApposto() {
		return tagApposto;
	}
	public void setTagApposto(String tagApposto) {
		this.tagApposto = tagApposto;
	}

	public String getDestinatariInvio() {
		return destinatariInvio;
	}

	public void setDestinatariInvio(String destinatariInvio) {
		this.destinatariInvio = destinatariInvio;
	}

	public String getDestinatariInvioInA() {
		return destinatariInvioInA;
	}

	public void setDestinatariInvioInA(String destinatariInvioInA) {
		this.destinatariInvioInA = destinatariInvioInA;
	}

	public String getDestinatariInvioInCc() {
		return destinatariInvioInCc;
	}

	public void setDestinatariInvioInCc(String destinatariInvioInCc) {
		this.destinatariInvioInCc = destinatariInvioInCc;
	}

	public String getDestinatariInvioInCcn() {
		return destinatariInvioInCcn;
	}

	public void setDestinatariInvioInCcn(String destinatariInvioInCcn) {
		this.destinatariInvioInCcn = destinatariInvioInCcn;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getFlgCommenti() {
		return flgCommenti;
	}

	public void setFlgCommenti(String flgCommenti) {
		this.flgCommenti = flgCommenti;
	}

	public String getFlgAssegnataUtente() {
		return flgAssegnataUtente;
	}

	public void setFlgAssegnataUtente(String flgAssegnataUtente) {
		this.flgAssegnataUtente = flgAssegnataUtente;
	}

	public String getFlgAssegnataUtenteUO() {
		return flgAssegnataUtenteUO;
	}

	public void setFlgAssegnataUtenteUO(String flgAssegnataUtenteUO) {
		this.flgAssegnataUtenteUO = flgAssegnataUtenteUO;
	}

	public Date getTsStatoLavorazione() {
		return tsStatoLavorazione;
	}

	public void setTsStatoLavorazione(Date tsStatoLavorazione) {
		this.tsStatoLavorazione = tsStatoLavorazione;
	}

	public Date getTsChiusura() {
		return tsChiusura;
	}

	public void setTsChiusura(Date tsChiusura) {
		this.tsChiusura = tsChiusura;
	}

	public String getOperatoreChiusura() {
		return operatoreChiusura;
	}

	public void setOperatoreChiusura(String operatoreChiusura) {
		this.operatoreChiusura = operatoreChiusura;
	}

}
