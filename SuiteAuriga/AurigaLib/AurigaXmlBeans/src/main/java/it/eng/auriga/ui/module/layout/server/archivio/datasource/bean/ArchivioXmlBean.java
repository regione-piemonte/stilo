/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class ArchivioXmlBean {

	@NumeroColonna(numero = "1")
	private String flgUdFolder;

	@NumeroColonna(numero = "2")
	private String idUdFolder;

	@NumeroColonna(numero = "3")
	private String nome;

	@NumeroColonna(numero = "4")
	private String segnatura;

	@NumeroColonna(numero = "5")
	private String nroSecondario;

	@NumeroColonna(numero = "6")
	private String segnaturaXOrd;

	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsApertura;
	
	@NumeroColonna(numero = "10")
	private String repertorio;

	@NumeroColonna(numero = "14")
	private String nroProvvisorioAtto;

	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAvvioIterAtto;

	@NumeroColonna(numero = "16")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsChiusura;

	@NumeroColonna(numero = "17")
	private BigDecimal priorita;

	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "20")
	private String flgTipoProv;

	@NumeroColonna(numero = "29")
	private String idFolderApp;

	@NumeroColonna(numero = "30")
	private String percorsoFolderApp;
	
	@NumeroColonna(numero = "31")
	private String idTipo;

	@NumeroColonna(numero = "32")
	private String tipo;

	@NumeroColonna(numero = "33")
	private String idDocPrimario;

	@NumeroColonna(numero = "35")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInvio;

	@NumeroColonna(numero = "36")
	@TipoData(tipo = Tipo.DATA)
	private Date tsEliminazione;

	@NumeroColonna(numero = "37")
	private String eliminatoDa;

	@NumeroColonna(numero = "38")
	private String destinatariInvio;

	@NumeroColonna(numero = "39")
	private Boolean flgSelXFinalita;

	@NumeroColonna(numero = "42")
	private String assegnatari;

	@NumeroColonna(numero = "52")
	private String pubblicazione;

	@NumeroColonna(numero = "53")
	private String stato;

	@NumeroColonna(numero = "60")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRicezione;

	@NumeroColonna(numero = "61")
	private BigDecimal prioritaInvioNotifiche;

	@NumeroColonna(numero = "62")
	private String estremiInvioNotifiche;
	
	@NumeroColonna(numero = "72")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtEsecutivita;

	@NumeroColonna(numero = "88")
	private String note;

	@NumeroColonna(numero = "89")
	private BigDecimal score;

	@NumeroColonna(numero = "90")
	private String info;

	@NumeroColonna(numero = "91")
	private String mittenti;

	@NumeroColonna(numero = "92")
	private String destinatari;

	@NumeroColonna(numero = "93")
	private String nroDocConFile;

	@NumeroColonna(numero = "94")
	private String nroDocConFileFirmati;

	@NumeroColonna(numero = "95")
	private String nroDocConFileDaScanner;

	@NumeroColonna(numero = "96")
	private String flgRicevutaViaEmail;

	@NumeroColonna(numero = "97")
	private String flgInviataViaEmail;

	@NumeroColonna(numero = "99")
	private String descContenutiFascicolo;

	// @NumeroColonna(numero = "100")
	// private String responsabileFascicolo;

	@NumeroColonna(numero = "101")
	private String causaleAggNote;
	
	@NumeroColonna(numero = "102")
	private Integer flgImmediatamenteEseg;

	@NumeroColonna(numero = "104")
	private String flgSottopostoControlloRegAmm;
	
	@NumeroColonna(numero = "105")
	private String idProcessoControlloRegAmm;
	
	//TODO ATTENZIONE: questo attributo custom viene messo nella colonna 104 solo se il parametro ATTIVATO_MODULO_ATTI = true, non e' corretto mapparlo fisso nel bean e comunque va' tenuto come ultimo degli attributi custom
	@NumeroColonna(numero = "106")
	private Boolean flgAttoPregresso;
	
	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRegistrazione;

	@NumeroColonna(numero = "202")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRicezioneDoc;
	
	@NumeroColonna(numero = "203")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsDocumento;

	@NumeroColonna(numero = "204")
	private String flgAnnReg;

	@NumeroColonna(numero = "205")
	private String datiAnnReg;

	@NumeroColonna(numero = "206")
	private String fascicoliApp;

	@NumeroColonna(numero = "207")
	private String livelloRiservatezza;

	@NumeroColonna(numero = "208")
	private String flgAssegnatoAMe;

	@NumeroColonna(numero = "209")
	private String flgNotificatoAMe;

	@NumeroColonna(numero = "214")
	private String utenteProtocollante;

	@NumeroColonna(numero = "215")
	private String uoProtocollante;

	@NumeroColonna(numero = "216")
	private Integer flgPresaInCarico;

	@NumeroColonna(numero = "217")
	private String percorsoNome;

	@NumeroColonna(numero = "226")
	private String uoProponente;

	@NumeroColonna(numero = "227")
	private String statoTrasmissioneEmail;
	
	@NumeroColonna(numero = "232")
	private String estremiDocCapofila;
	
	@NumeroColonna(numero = "258")
	private String idUDTrasmessoInUscitaCon;
	
	@NumeroColonna(numero = "259")
	private String estremiUDTrasmessoInUscitaCon;
	
	@NumeroColonna(numero = "260")
	private String motivoAnnullamento;
	
	@NumeroColonna(numero = "261")
	private String attoAutAnnullamento;
	
	@NumeroColonna(numero = "263")
	@TipoData(tipo = Tipo.DATA)
	private Date tsPresaInCarico;
	
	@NumeroColonna(numero = "270")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioPubblicazione;
	
	@NumeroColonna(numero = "277")
	private String giorniPubblicazione;

	@NumeroColonna(numero = "278")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFinePubblicazione;
	
	@NumeroColonna(numero = "279")
	@TipoData(tipo = Tipo.DATA)
	private Date tsRichiestaPubblicazione;

	@NumeroColonna(numero = "280")
	private String richiedentePubblicazione;	
	
	@NumeroColonna(numero = "281")
	private String centroDiCosto;	
	
	@NumeroColonna(numero = "282")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenza;
	
	@NumeroColonna(numero = "283")
	private String idRichPubbl;
	
	@NumeroColonna(numero = "284")
	private String anno;
	
	@NumeroColonna(numero = "285")
	private String numero;
	
	@NumeroColonna(numero = "286")
	private String flgDeterminaContrarreTramiteGara;
	
	@NumeroColonna(numero = "287")
	private String flgDeterminaAggiudicaGara;
	
	@NumeroColonna(numero = "288")
	private String flgDeterminaRimodulazioneSpesaPostAggiudica;
	
	@NumeroColonna(numero = "289")
	private String flgLiquidazioneContestualeImpegni;
	
	@NumeroColonna(numero = "290")
	private String flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi;
	
	@NumeroColonna(numero = "291")
	private String tipoAffidamento;
	
	@NumeroColonna(numero = "292")
	private String materiaTipoAtto;
	
	@NumeroColonna(numero = "293")
	private String progettoLLPP;
	
	@NumeroColonna(numero = "294")
	private String beniServizi;
	
	@NumeroColonna(numero = "295")
	private String ordinativi;

	@NumeroColonna(numero = "296")
	private String perizie;
	
	@NumeroColonna(numero = "297")
	private String flgDatiCompletiPerProtocollazione;

	@NumeroColonna(numero = "298")
	private String nrLiquidazioneContestuale;
	
	@NumeroColonna(numero = "299")
	private String msgDiInvio;
	
	@NumeroColonna(numero = "300")
	private String societa;
	
	@NumeroColonna(numero = "301")
	@TipoData(tipo = Tipo.DATA)
	private Date tsScansione;
	
	@NumeroColonna(numero = "302")
	private String datiBarcode;
	
	@NumeroColonna(numero = "303")
	private String sedeScansione;
	
	@NumeroColonna(numero = "304")
	private String timbroProtRiconosciuto;
	
	@NumeroColonna(numero = "305")
	private String nroProtRiconosciuto;
	
	@NumeroColonna(numero = "306")
	private String motivoScarto;
	
	@NumeroColonna(numero = "307")
	private String statoTrasferimentoBloomfleet;
	
	@NumeroColonna(numero = "308")
	private Integer flgPresenzaContratti;
	
	@NumeroColonna(numero = "312")
	private String programmazioneAcquisti;
	
	@NumeroColonna(numero = "313")
	private String cui;
	
	@NumeroColonna(numero = "314")
	private String codiceCIG;
	
	@NumeroColonna(numero = "315")
	private String inConoscenzaA;
	
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public String getIdUdFolder() {
		return idUdFolder;
	}

	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	public String getNroSecondario() {
		return nroSecondario;
	}

	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}

	public String getSegnaturaXOrd() {
		return segnaturaXOrd;
	}

	public void setSegnaturaXOrd(String segnaturaXOrd) {
		this.segnaturaXOrd = segnaturaXOrd;
	}

	public Date getTsApertura() {
		return tsApertura;
	}

	public void setTsApertura(Date tsApertura) {
		this.tsApertura = tsApertura;
	}

	public String getRepertorio() {
		return repertorio;
	}

	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}

	public String getNroProvvisorioAtto() {
		return nroProvvisorioAtto;
	}

	public void setNroProvvisorioAtto(String nroProvvisorioAtto) {
		this.nroProvvisorioAtto = nroProvvisorioAtto;
	}

	public Date getDataAvvioIterAtto() {
		return dataAvvioIterAtto;
	}

	public void setDataAvvioIterAtto(Date dataAvvioIterAtto) {
		this.dataAvvioIterAtto = dataAvvioIterAtto;
	}

	public Date getTsChiusura() {
		return tsChiusura;
	}

	public void setTsChiusura(Date tsChiusura) {
		this.tsChiusura = tsChiusura;
	}

	public BigDecimal getPriorita() {
		return priorita;
	}

	public void setPriorita(BigDecimal priorita) {
		this.priorita = priorita;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public String getIdFolderApp() {
		return idFolderApp;
	}

	public void setIdFolderApp(String idFolderApp) {
		this.idFolderApp = idFolderApp;
	}

	public String getPercorsoFolderApp() {
		return percorsoFolderApp;
	}

	public void setPercorsoFolderApp(String percorsoFolderApp) {
		this.percorsoFolderApp = percorsoFolderApp;
	}

	public String getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public Date getTsEliminazione() {
		return tsEliminazione;
	}

	public void setTsEliminazione(Date tsEliminazione) {
		this.tsEliminazione = tsEliminazione;
	}

	public String getEliminatoDa() {
		return eliminatoDa;
	}

	public void setEliminatoDa(String eliminatoDa) {
		this.eliminatoDa = eliminatoDa;
	}

	public String getDestinatariInvio() {
		return destinatariInvio;
	}

	public void setDestinatariInvio(String destinatariInvio) {
		this.destinatariInvio = destinatariInvio;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(String assegnatari) {
		this.assegnatari = assegnatari;
	}

	public String getPubblicazione() {
		return pubblicazione;
	}

	public void setPubblicazione(String pubblicazione) {
		this.pubblicazione = pubblicazione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getTsRicezione() {
		return tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}

	public BigDecimal getPrioritaInvioNotifiche() {
		return prioritaInvioNotifiche;
	}

	public void setPrioritaInvioNotifiche(BigDecimal prioritaInvioNotifiche) {
		this.prioritaInvioNotifiche = prioritaInvioNotifiche;
	}

	public String getEstremiInvioNotifiche() {
		return estremiInvioNotifiche;
	}

	public void setEstremiInvioNotifiche(String estremiInvioNotifiche) {
		this.estremiInvioNotifiche = estremiInvioNotifiche;
	}

	public Date getDtEsecutivita() {
		return dtEsecutivita;
	}

	public void setDtEsecutivita(Date dtEsecutivita) {
		this.dtEsecutivita = dtEsecutivita;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMittenti() {
		return mittenti;
	}

	public void setMittenti(String mittenti) {
		this.mittenti = mittenti;
	}

	public String getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}

	public String getNroDocConFile() {
		return nroDocConFile;
	}

	public void setNroDocConFile(String nroDocConFile) {
		this.nroDocConFile = nroDocConFile;
	}

	public String getNroDocConFileFirmati() {
		return nroDocConFileFirmati;
	}

	public void setNroDocConFileFirmati(String nroDocConFileFirmati) {
		this.nroDocConFileFirmati = nroDocConFileFirmati;
	}

	public String getNroDocConFileDaScanner() {
		return nroDocConFileDaScanner;
	}

	public void setNroDocConFileDaScanner(String nroDocConFileDaScanner) {
		this.nroDocConFileDaScanner = nroDocConFileDaScanner;
	}

	public String getFlgRicevutaViaEmail() {
		return flgRicevutaViaEmail;
	}

	public void setFlgRicevutaViaEmail(String flgRicevutaViaEmail) {
		this.flgRicevutaViaEmail = flgRicevutaViaEmail;
	}

	public String getFlgInviataViaEmail() {
		return flgInviataViaEmail;
	}

	public void setFlgInviataViaEmail(String flgInviataViaEmail) {
		this.flgInviataViaEmail = flgInviataViaEmail;
	}

	public String getDescContenutiFascicolo() {
		return descContenutiFascicolo;
	}

	public void setDescContenutiFascicolo(String descContenutiFascicolo) {
		this.descContenutiFascicolo = descContenutiFascicolo;
	}

	public String getCausaleAggNote() {
		return causaleAggNote;
	}

	public void setCausaleAggNote(String causaleAggNote) {
		this.causaleAggNote = causaleAggNote;
	}

	public Integer getFlgImmediatamenteEseg() {
		return flgImmediatamenteEseg;
	}

	public void setFlgImmediatamenteEseg(Integer flgImmediatamenteEseg) {
		this.flgImmediatamenteEseg = flgImmediatamenteEseg;
	}

	public String getPerizie() {
		return perizie;
	}

	public void setPerizie(String perizie) {
		this.perizie = perizie;
	}

	public String getFlgSottopostoControlloRegAmm() {
		return flgSottopostoControlloRegAmm;
	}

	public void setFlgSottopostoControlloRegAmm(String flgSottopostoControlloRegAmm) {
		this.flgSottopostoControlloRegAmm = flgSottopostoControlloRegAmm;
	}

	public String getIdProcessoControlloRegAmm() {
		return idProcessoControlloRegAmm;
	}

	public void setIdProcessoControlloRegAmm(String idProcessoControlloRegAmm) {
		this.idProcessoControlloRegAmm = idProcessoControlloRegAmm;
	}

	public Boolean getFlgAttoPregresso() {
		return flgAttoPregresso;
	}

	public void setFlgAttoPregresso(Boolean flgAttoPregresso) {
		this.flgAttoPregresso = flgAttoPregresso;
	}

	public Date getTsRegistrazione() {
		return tsRegistrazione;
	}

	public void setTsRegistrazione(Date tsRegistrazione) {
		this.tsRegistrazione = tsRegistrazione;
	}

	public Date getTsDocumento() {
		return tsDocumento;
	}

	public void setTsDocumento(Date tsDocumento) {
		this.tsDocumento = tsDocumento;
	}

	public String getFlgAnnReg() {
		return flgAnnReg;
	}

	public void setFlgAnnReg(String flgAnnReg) {
		this.flgAnnReg = flgAnnReg;
	}

	public String getDatiAnnReg() {
		return datiAnnReg;
	}

	public void setDatiAnnReg(String datiAnnReg) {
		this.datiAnnReg = datiAnnReg;
	}

	public String getFascicoliApp() {
		return fascicoliApp;
	}

	public void setFascicoliApp(String fascicoliApp) {
		this.fascicoliApp = fascicoliApp;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public String getFlgAssegnatoAMe() {
		return flgAssegnatoAMe;
	}

	public void setFlgAssegnatoAMe(String flgAssegnatoAMe) {
		this.flgAssegnatoAMe = flgAssegnatoAMe;
	}

	public String getFlgNotificatoAMe() {
		return flgNotificatoAMe;
	}

	public void setFlgNotificatoAMe(String flgNotificatoAMe) {
		this.flgNotificatoAMe = flgNotificatoAMe;
	}

	public String getUtenteProtocollante() {
		return utenteProtocollante;
	}

	public void setUtenteProtocollante(String utenteProtocollante) {
		this.utenteProtocollante = utenteProtocollante;
	}

	public String getUoProtocollante() {
		return uoProtocollante;
	}

	public void setUoProtocollante(String uoProtocollante) {
		this.uoProtocollante = uoProtocollante;
	}

	public Integer getFlgPresaInCarico() {
		return flgPresaInCarico;
	}

	public void setFlgPresaInCarico(Integer flgPresaInCarico) {
		this.flgPresaInCarico = flgPresaInCarico;
	}

	public String getPercorsoNome() {
		return percorsoNome;
	}

	public void setPercorsoNome(String percorsoNome) {
		this.percorsoNome = percorsoNome;
	}

	public String getUoProponente() {
		return uoProponente;
	}

	public void setUoProponente(String uoProponente) {
		this.uoProponente = uoProponente;
	}

	public String getStatoTrasmissioneEmail() {
		return statoTrasmissioneEmail;
	}

	public void setStatoTrasmissioneEmail(String statoTrasmissioneEmail) {
		this.statoTrasmissioneEmail = statoTrasmissioneEmail;
	}

	public String getEstremiDocCapofila() {
		return estremiDocCapofila;
	}

	public void setEstremiDocCapofila(String estremiDocCapofila) {
		this.estremiDocCapofila = estremiDocCapofila;
	}

	public String getIdUDTrasmessoInUscitaCon() {
		return idUDTrasmessoInUscitaCon;
	}

	public void setIdUDTrasmessoInUscitaCon(String idUDTrasmessoInUscitaCon) {
		this.idUDTrasmessoInUscitaCon = idUDTrasmessoInUscitaCon;
	}

	public String getEstremiUDTrasmessoInUscitaCon() {
		return estremiUDTrasmessoInUscitaCon;
	}

	public void setEstremiUDTrasmessoInUscitaCon(String estremiUDTrasmessoInUscitaCon) {
		this.estremiUDTrasmessoInUscitaCon = estremiUDTrasmessoInUscitaCon;
	}

	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}

	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}

	public String getAttoAutAnnullamento() {
		return attoAutAnnullamento;
	}

	public void setAttoAutAnnullamento(String attoAutAnnullamento) {
		this.attoAutAnnullamento = attoAutAnnullamento;
	}

	public Date getTsPresaInCarico() {
		return tsPresaInCarico;
	}

	public void setTsPresaInCarico(Date tsPresaInCarico) {
		this.tsPresaInCarico = tsPresaInCarico;
	}

	public Date getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}

	public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}

	public String getGiorniPubblicazione() {
		return giorniPubblicazione;
	}

	public void setGiorniPubblicazione(String giorniPubblicazione) {
		this.giorniPubblicazione = giorniPubblicazione;
	}

	public Date getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}

	public void setDataFinePubblicazione(Date dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}

	public Date getTsRichiestaPubblicazione() {
		return tsRichiestaPubblicazione;
	}

	public void setTsRichiestaPubblicazione(Date tsRichiestaPubblicazione) {
		this.tsRichiestaPubblicazione = tsRichiestaPubblicazione;
	}

	public String getRichiedentePubblicazione() {
		return richiedentePubblicazione;
	}

	public void setRichiedentePubblicazione(String richiedentePubblicazione) {
		this.richiedentePubblicazione = richiedentePubblicazione;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getIdRichPubbl() {
		return idRichPubbl;
	}

	public void setIdRichPubbl(String idRichPubbl) {
		this.idRichPubbl = idRichPubbl;
	}
	
	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFlgDeterminaContrarreTramiteGara() {
		return flgDeterminaContrarreTramiteGara;
	}

	public void setFlgDeterminaContrarreTramiteGara(String flgDeterminaContrarreTramiteGara) {
		this.flgDeterminaContrarreTramiteGara = flgDeterminaContrarreTramiteGara;
	}

	public String getFlgDeterminaAggiudicaGara() {
		return flgDeterminaAggiudicaGara;
	}

	public void setFlgDeterminaAggiudicaGara(String flgDeterminaAggiudicaGara) {
		this.flgDeterminaAggiudicaGara = flgDeterminaAggiudicaGara;
	}

	public String getFlgDeterminaRimodulazioneSpesaPostAggiudica() {
		return flgDeterminaRimodulazioneSpesaPostAggiudica;
	}

	public void setFlgDeterminaRimodulazioneSpesaPostAggiudica(String flgDeterminaRimodulazioneSpesaPostAggiudica) {
		this.flgDeterminaRimodulazioneSpesaPostAggiudica = flgDeterminaRimodulazioneSpesaPostAggiudica;
	}

	public String getFlgLiquidazioneContestualeImpegni() {
		return flgLiquidazioneContestualeImpegni;
	}

	public void setFlgLiquidazioneContestualeImpegni(String flgLiquidazioneContestualeImpegni) {
		this.flgLiquidazioneContestualeImpegni = flgLiquidazioneContestualeImpegni;
	}

	public String getFlgLiquidazioneContestualiAspettiRilevanzaContabileDiversi() {
		return flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi;
	}

	public void setFlgLiquidazioneContestualiAspettiRilevanzaContabileDiversi(String flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi) {
		this.flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi = flgLiquidazioneContestualiAspettiRilevanzaContabileDiversi;
	}

	public String getTipoAffidamento() {
		return tipoAffidamento;
	}

	public void setTipoAffidamento(String tipoAffidamento) {
		this.tipoAffidamento = tipoAffidamento;
	}

	public String getMateriaTipoAtto() {
		return materiaTipoAtto;
	}

	public void setMateriaTipoAtto(String materiaTipoAtto) {
		this.materiaTipoAtto = materiaTipoAtto;
	}

	public String getProgettoLLPP() {
		return progettoLLPP;
	}

	public void setProgettoLLPP(String progettoLLPP) {
		this.progettoLLPP = progettoLLPP;
	}

	public String getBeniServizi() {
		return beniServizi;
	}

	public void setBeniServizi(String beniServizi) {
		this.beniServizi = beniServizi;
	}

	public String getOrdinativi() {
		return ordinativi;
	}

	public void setOrdinativi(String ordinativi) {
		this.ordinativi = ordinativi;
	}
	
	public String getFlgDatiCompletiPerProtocollazione() {
		return flgDatiCompletiPerProtocollazione;
	}

	public void setFlgDatiCompletiPerProtocollazione(String flgDatiCompletiPerProtocollazione) {
		this.flgDatiCompletiPerProtocollazione = flgDatiCompletiPerProtocollazione;
	}

	public String getNrLiquidazioneContestuale() {
		return nrLiquidazioneContestuale;
	}

	public void setNrLiquidazioneContestuale(String nrLiquidazioneContestuale) {
		this.nrLiquidazioneContestuale = nrLiquidazioneContestuale;
	}

	public String getMsgDiInvio() {
		return msgDiInvio;
	}

	public void setMsgDiInvio(String msgDiInvio) {
		this.msgDiInvio = msgDiInvio;
	}

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public Date getTsScansione() {
		return tsScansione;
	}

	public void setTsScansione(Date tsScansione) {
		this.tsScansione = tsScansione;
	}

	public String getDatiBarcode() {
		return datiBarcode;
	}

	public void setDatiBarcode(String datiBarcode) {
		this.datiBarcode = datiBarcode;
	}

	public String getSedeScansione() {
		return sedeScansione;
	}

	public void setSedeScansione(String sedeScansione) {
		this.sedeScansione = sedeScansione;
	}

	public String getTimbroProtRiconosciuto() {
		return timbroProtRiconosciuto;
	}

	public void setTimbroProtRiconosciuto(String timbroProtRiconosciuto) {
		this.timbroProtRiconosciuto = timbroProtRiconosciuto;
	}

	public String getNroProtRiconosciuto() {
		return nroProtRiconosciuto;
	}

	public void setNroProtRiconosciuto(String nroProtRiconosciuto) {
		this.nroProtRiconosciuto = nroProtRiconosciuto;
	}

	public Date getTsRicezioneDoc() {
		return tsRicezioneDoc;
	}

	public void setTsRicezioneDoc(Date tsRicezioneDoc) {
		this.tsRicezioneDoc = tsRicezioneDoc;
	}

	public String getMotivoScarto() {
		return motivoScarto;
	}

	public void setMotivoScarto(String motivoScarto) {
		this.motivoScarto = motivoScarto;
	}

	public String getStatoTrasferimentoBloomfleet() {
		return statoTrasferimentoBloomfleet;
	}

	public void setStatoTrasferimentoBloomfleet(String statoTrasferimentoBloomfleet) {
		this.statoTrasferimentoBloomfleet = statoTrasferimentoBloomfleet;
	}

	public Integer getFlgPresenzaContratti() {
		return flgPresenzaContratti;
	}

	public void setFlgPresenzaContratti(Integer flgPresenzaContratti) {
		this.flgPresenzaContratti = flgPresenzaContratti;
	}

	public String getProgrammazioneAcquisti() {
		return programmazioneAcquisti;
	}

	public void setProgrammazioneAcquisti(String programmazioneAcquisti) {
		this.programmazioneAcquisti = programmazioneAcquisti;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public String getInConoscenzaA() {
		return inConoscenzaA;
	}

	public void setInConoscenzaA(String inConoscenzaA) {
		this.inConoscenzaA = inConoscenzaA;
	}
	
}