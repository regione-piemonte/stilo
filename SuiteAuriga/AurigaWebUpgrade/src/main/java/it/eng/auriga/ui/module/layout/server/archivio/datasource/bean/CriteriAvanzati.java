/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class CriteriAvanzati implements Serializable {
	
	@XmlVariabile(nome="CINodo", tipo=TipoVariabile.SEMPLICE)
	private String idNode;
	
	@XmlVariabile(nome="@Registrazioni", tipo=TipoVariabile.LISTA)
	private List<Registrazione> registrazioni;
	
	@XmlVariabile(nome="DtRaccomandataDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRaccomandataDal;
	
	@XmlVariabile(nome="DtRaccomandataAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRaccomandataAl;
	
	@XmlVariabile(nome="NroRaccomandata", tipo=TipoVariabile.SEMPLICE)
	private String nroRaccomandata;
	
	@XmlVariabile(nome="DataAperturaDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAperturaDa;

	@XmlVariabile(nome="DataAperturaA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAperturaA;

	@XmlVariabile(nome="DataChiusuraDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataChiusuraDa;

	@XmlVariabile(nome="DataChiusuraA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataChiusuraA;

	@XmlVariabile(nome="FlagTipoProvDoc", tipo=TipoVariabile.SEMPLICE)
	private String flagTipoProvDoc;
	
	@XmlVariabile(nome="StatoAssegnazione", tipo=TipoVariabile.SEMPLICE)
	private String statoAssegnazione;
	
	@XmlVariabile(nome="StatoClassificazione", tipo=TipoVariabile.SEMPLICE)
	private String statoClassificazione;
	
	@XmlVariabile(nome="StatoFascicolazione", tipo=TipoVariabile.SEMPLICE)
	private String statoFascicolazione;
	
	@XmlVariabile(nome="SoloRecenti", tipo=TipoVariabile.SEMPLICE)
	private String soloRecenti;
	
	@XmlVariabile(nome="@StatiDoc", tipo=TipoVariabile.LISTA)
	private List<Stato> statiDoc;
	
	@XmlVariabile(nome="@StatiDettDoc", tipo=TipoVariabile.LISTA)
	private List<Stato> statiDettDoc;
	
	@XmlVariabile(nome="@StatiFolder", tipo=TipoVariabile.LISTA)
	private List<Stato> statiFolder;
	
	@XmlVariabile(nome="@StatiDettFolder", tipo=TipoVariabile.LISTA)
	private List<Stato> statiDettFolder;
	
	@XmlVariabile(nome="InteresseCessato", tipo=TipoVariabile.SEMPLICE)
	private String interesseCessato;
	
	@XmlVariabile(nome="StatoVisione", tipo=TipoVariabile.SEMPLICE)
	private String statoVisione;
	
	@XmlVariabile(nome="SoloDaLeggere", tipo=TipoVariabile.SEMPLICE)
	private String soloDaLeggere;	
	
	@XmlVariabile(nome="CodRuoloUtenteVsDocFolder", tipo=TipoVariabile.SEMPLICE)
	private String codRuoloUtenteVsDocFolder;
	
	@XmlVariabile(nome="FlagFirmaApposta", tipo=TipoVariabile.SEMPLICE)
	private String flagFirmaApposta;
	
	@XmlVariabile(nome="NomeWorkspaceApp", tipo=TipoVariabile.SEMPLICE)
	private String nomeWorkspaceApp;
	
	@XmlVariabile(nome="SoloAperti", tipo=TipoVariabile.SEMPLICE)
	private String soloAperti;
	
	@XmlVariabile(nome="DataInvioDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioDa;

	@XmlVariabile(nome="DataInvioA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioA;

	@XmlVariabile(nome="DestinatarioInvio", tipo=TipoVariabile.SEMPLICE)
	private String destinatarioInvio;
	
	@XmlVariabile(nome="DataArchiviazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataArchiviazioneDa;

	@XmlVariabile(nome="PresaInCaricoDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date presaInCaricoDal;
	
	@XmlVariabile(nome="PresaInCaricoAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date presaInCaricoAl;
	
	@XmlVariabile(nome="DataArchiviazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataArchiviazioneA;
	
	@XmlVariabile(nome="DataEliminazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataEliminazioneDa;

	@XmlVariabile(nome="DataEliminazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataEliminazioneA;

	@XmlVariabile(nome="CISezioneEliminazioneDa", tipo=TipoVariabile.SEMPLICE)
	private String sezioneEliminazione;	
	
	@XmlVariabile(nome="RestringiSottofascInserti", tipo=TipoVariabile.SEMPLICE)
	private String restringiSottofascInserti;
	
	@XmlVariabile(nome="SoloDocRicevutiViaEmailMGO", tipo=TipoVariabile.SEMPLICE)
	private String soloDocRicevutiViaEmail;	
	
	@XmlVariabile(nome="InviatiViaEmailMGO", tipo=TipoVariabile.SEMPLICE)
	private String inviatiViaEmail;	
	
	@XmlVariabile(nome="RestringiNews", tipo=TipoVariabile.SEMPLICE)
	private String restringiNews;
	
	@XmlVariabile(nome="NewsConNotificheCondivisione", tipo=TipoVariabile.SEMPLICE)
	private String newsConNotificheCondivisione;
	
	@XmlVariabile(nome="NewsConOsservazioni", tipo=TipoVariabile.SEMPLICE)
	private String newsConOsservazioni;
	
	@XmlVariabile(nome="NewsConNotificheAutomatiche", tipo=TipoVariabile.SEMPLICE)
	private String newsConNotificheAutomatiche;
	
	@XmlVariabile(nome="StatoPresaInCarico", tipo=TipoVariabile.SEMPLICE)
	private String statoPresaInCarico;
	
	@XmlVariabile(nome="ArchiviatoFileTimbrato", tipo=TipoVariabile.SEMPLICE)
	private String archiviatoFileTimbrato;
	
	@XmlVariabile(nome="@StatiRichiestaAnnullamentoReg", tipo=TipoVariabile.LISTA)
	private List<StatoRichAnnullamentoReg> statiRichiestaAnnullamentoReg;
	
	@XmlVariabile(nome="RestringiAttiAutAnnReg", tipo=TipoVariabile.SEMPLICE)
	private String restringiAttiAutAnnReg;
	
	@XmlVariabile(nome="IdDocType", tipo=TipoVariabile.SEMPLICE)
	private String idDocType;
	
	@XmlVariabile(nome="IdFolderType", tipo=TipoVariabile.SEMPLICE)
	private String idFolderType;
	
	@XmlVariabile(nome="DataAssegnSmistDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAssegnSmistDa;

	@XmlVariabile(nome="DataAssegnSmistA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAssegnSmistA;
	
	@XmlVariabile(nome="TipoAssegnazione", tipo=TipoVariabile.SEMPLICE)
	private String tipoAssegnazione;	
	
	@XmlVariabile(nome="DataNotificaDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataNotificaDa;

	@XmlVariabile(nome="DataNotificaA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataNotificaA;
	
	@XmlVariabile(nome="TipoNotifica", tipo=TipoVariabile.SEMPLICE)
	private String tipoNotifica;	
	
	@XmlVariabile(nome="@InviatiA", tipo=TipoVariabile.LISTA)
	private List<DestInvioNotifica> inviatiA;
	
	@XmlVariabile(nome="@AssegnatiA", tipo=TipoVariabile.LISTA)
	private List<DestInvioNotifica> assegnatiA;
	
	@XmlVariabile(nome="@InConoscenzaA", tipo=TipoVariabile.LISTA)
	private List<DestInvioNotifica> inConoscenzaA;
	
	@XmlVariabile(nome="@ConNotificheAutoA", tipo=TipoVariabile.LISTA)
	private List<DestInvioNotifica> conNotificheAutoA;
	
	@XmlVariabile(nome="SoloRegAnnullate", tipo=TipoVariabile.SEMPLICE)
	private String soloRegAnnullate;	 
	
	@XmlVariabile(nome="MittenteUD", tipo=TipoVariabile.SEMPLICE)
	private String mittenteUD;	
	
	@XmlVariabile(nome="OperMittenteUD", tipo=TipoVariabile.SEMPLICE)
	private String operMittenteUD;	
	
	@XmlVariabile(nome="IdMittenteUDInRubrica", tipo=TipoVariabile.SEMPLICE)
	private String idMittenteUDInRubrica;	
	
	@XmlVariabile(nome="DestinatarioUD", tipo=TipoVariabile.SEMPLICE)
	private String destinatarioUD;
	
	@XmlVariabile(nome="OperDestinatarioUD", tipo=TipoVariabile.SEMPLICE)
	private String operDestinatarioUD;	
	
	@XmlVariabile(nome="IdDestinatarioUDInRubrica", tipo=TipoVariabile.SEMPLICE)
	private String idDestinatarioUDInRubrica;	
	
	@XmlVariabile(nome="EsibenteUD", tipo=TipoVariabile.SEMPLICE)
	private String esibenteUD;
	
	@XmlVariabile(nome="OperEsibenteUD", tipo=TipoVariabile.SEMPLICE)
	private String operEsibenteUD;	
	
	@XmlVariabile(nome="IdEsibenteUDInRubrica", tipo=TipoVariabile.SEMPLICE)
	private String idEsibenteUDInRubrica;
	
	@XmlVariabile(nome="OggettoUD", tipo=TipoVariabile.SEMPLICE)
	private String oggettoUD;	
	
	@XmlVariabile(nome="NomeFolder", tipo=TipoVariabile.SEMPLICE)
	private String nomeFolder;	
	
	@XmlVariabile(nome="PresenzaFile", tipo=TipoVariabile.SEMPLICE)
	private String presenzaFile;	
	
	@XmlVariabile(nome="@MezziTrasmissione", tipo=TipoVariabile.LISTA)
	private List<MezziTrasmissioneFilterBean> mezziTrasmissione ;	
	
	@XmlVariabile(nome="NomeDocType", tipo=TipoVariabile.SEMPLICE)
	private String nomeDocType;

	@XmlVariabile(nome="ContraenteUD", tipo=TipoVariabile.SEMPLICE)
	private String contraenteUD;

	@XmlVariabile(nome="PIVA_CF_ContraenteUD", tipo=TipoVariabile.SEMPLICE)
	private String pIvaCfContraenteUD;
	
	@XmlVariabile(nome="ClienteUD", tipo=TipoVariabile.SEMPLICE)
	private String clienteUD;

	@XmlVariabile(nome="OperClienteUD", tipo=TipoVariabile.SEMPLICE)
	private String operClienteUD;	
	
	@XmlVariabile(nome="CIProvClienteUD", tipo=TipoVariabile.SEMPLICE)
	private String ciProvClienteUD;

	@XmlVariabile(nome="PIVA_CF_ClienteUD", tipo=TipoVariabile.SEMPLICE)
	private String pIvaCfClienteUD;
	
	@XmlVariabile(nome="@UOProponente", tipo=TipoVariabile.LISTA)
	private List<UoProponente> uoProponente;

	@XmlVariabile(nome="@UOCompetente", tipo=TipoVariabile.LISTA)
	private List<UoCompetente> uoCompetente;

	
	@XmlVariabile(nome="@UORegistrazione", tipo=TipoVariabile.LISTA)
	private List<UoRegistrazione> uoRegistrazione;
	
	@XmlVariabile(nome="@UOApertura", tipo=TipoVariabile.LISTA)
	private List<UoApertura> uoApertura;
	
	@XmlVariabile(nome="@UtenteAvvioAtto", tipo=TipoVariabile.LISTA)
	private List<Utente> utentiAvvioAtto;
	
	@XmlVariabile(nome="@UtentiAdozioneAtto", tipo=TipoVariabile.LISTA)
	private List<Utente> utentiAdozioneAtto;
	
	@XmlVariabile(nome="NoteUD", tipo=TipoVariabile.SEMPLICE)
	private String noteUd;

	@XmlVariabile(nome="DtFirmaAttoDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFirmaAttoDa;
		
	@XmlVariabile(nome="DtFirmaAttoAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFirmaAttoA;
	
	@XmlVariabile(nome="NroProtRicevuto", tipo=TipoVariabile.SEMPLICE)
	private String nroProtRicevuto;
		
	@XmlVariabile(nome="DtProtRicevutoDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataProtRicevutoDa;
	
	@XmlVariabile(nome="DtProtRicevutoAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataProtRicevutoA;
	
	@XmlVariabile(nome="StatiInvioEmail", tipo=TipoVariabile.SEMPLICE)
	private String statoTrasmissioneEmail;

	@XmlVariabile(nome="NroRichiestaStampaExpDa", tipo=TipoVariabile.SEMPLICE)
	private String nroRichiestaStampaExpDa;
	
	@XmlVariabile(nome="NroRichiestaStampaExpA", tipo=TipoVariabile.SEMPLICE)
	private String nroRichiestaStampaExpA;

	@XmlVariabile(nome="DtRichiestaStampaExpDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsRichiestaStampaExpDa;
	
	@XmlVariabile(nome="DtRichiestaStampaExpA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsRichiestaStampaExpA;
	
	@XmlVariabile(nome="CapofilaFasc", tipo=TipoVariabile.SEMPLICE)
	private String capoFilaFasc;
	
	@XmlVariabile(nome="ProtocolloCapofila", tipo=TipoVariabile.SEMPLICE)
	private String protocolloCapofila;
	
	@XmlVariabile(nome="ProtocolloCollegato", tipo=TipoVariabile.SEMPLICE)
	private String protocolloCollegato;	

	@XmlVariabile(nome="IdProvenienza", tipo=TipoVariabile.SEMPLICE)
	private String idProvenienza;
	
	@XmlVariabile(nome="IdPdVUltimoInvio", tipo=TipoVariabile.SEMPLICE)
	private String idPdVUltimoInvio;

	@XmlVariabile(nome="DataInvioConservazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioConservazioneDa;
	
	@XmlVariabile(nome="DataInvioConservazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioConservazioneA;
	
	@XmlVariabile(nome="DataAcquisizioneInConservazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAcquisizioneInConservazioneDa;
	
	@XmlVariabile(nome="DataAcquisizioneInConservazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAcquisizioneInConservazioneA;
	
	@XmlVariabile(nome="DataInizioConservazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioConservazioneDa;
	
	@XmlVariabile(nome="DataInizioConservazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioConservazioneA;
	
	@XmlVariabile(nome="IdInConservazione", tipo=TipoVariabile.SEMPLICE)
	private String idInConservazione;

	@XmlVariabile(nome="DtTermineConservazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataTermineConservazioneDa;
	
	@XmlVariabile(nome="DtTermineConservazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataTermineConservazioneA;
	
	@XmlVariabile(nome="DtScartoDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScartoDa;
	
	@XmlVariabile(nome="DtScartoA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScartoA;
	
	@XmlVariabile(nome="FlgProlungatoTempoConservazione", tipo=TipoVariabile.SEMPLICE)
	private String flgProlungatoTempoConservazione;
	
	@XmlVariabile(nome="FlgRichiestaEsibizioneAlConservatore", tipo=TipoVariabile.SEMPLICE)
	private String flgRichiestaEsibizioneAlConservatore;
	
	@XmlVariabile(nome="NomeUD", tipo=TipoVariabile.SEMPLICE)
	private String nomeUD;
	
	@XmlVariabile(nome="EscludiUOSVSubordinati", tipo=TipoVariabile.SEMPLICE)
	private String escludiUOSVSubordinati;
	
	@XmlVariabile(nome="IdIndirizzoInVario", tipo=TipoVariabile.SEMPLICE)
	private String idIndirizzoInVario;
	
	@XmlVariabile(nome="Indirizzo", tipo=TipoVariabile.SEMPLICE)
	private String indirizzo;
	
	@XmlVariabile(nome="@SupportiOrigDoc", tipo=TipoVariabile.LISTA)
	private List<SupportiOrigDoc> supportiOrigDoc;
	
	@XmlVariabile(nome="IdClassificazione", tipo=TipoVariabile.SEMPLICE)
	private String idClassificazione;	
	
	@XmlVariabile(nome="LivelliClassificazione", tipo=TipoVariabile.SEMPLICE)
	private String livelliClassificazione;	
	
	@XmlVariabile(nome="FlgInclSottoClassif", tipo=TipoVariabile.SEMPLICE)
	private String flgIncludiSottoClassifiche;	
	
	@XmlVariabile(nome="AnnoFascDa", tipo=TipoVariabile.SEMPLICE)
	private String annoFascicoloDa;
	
	@XmlVariabile(nome="AnnoFascA", tipo=TipoVariabile.SEMPLICE)
	private String annoFascicoloA;

	@XmlVariabile(nome="NumProgrFascDa", tipo=TipoVariabile.SEMPLICE)
	private String nroFascicoloDa;
	
	@XmlVariabile(nome="NumProgrFascA", tipo=TipoVariabile.SEMPLICE)
	private String nroFascicoloA;

	@XmlVariabile(nome="NumSottofascDa", tipo=TipoVariabile.SEMPLICE)
	private String nroSottoFascicoloDa;
	
	@XmlVariabile(nome="NumSottofascA", tipo=TipoVariabile.SEMPLICE)
	private String nroSottoFascicoloA;
	
	@XmlVariabile(nome="NroSecondarioFasc", tipo=TipoVariabile.SEMPLICE)
	private String codiceFascicolo;
	
	@XmlVariabile(nome="EstremiAttoAutAnn", tipo=TipoVariabile.SEMPLICE)
	private String estremiAttoAutAnnullamento;
	
	@XmlVariabile(nome="DataStesuraDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtStesuraDal;
	
	@XmlVariabile(nome="DataStesuraAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtStesuraAl;
	
	@XmlVariabile(nome="NominativoUD", tipo=TipoVariabile.SEMPLICE)
	private String nominativoUD;	
	
	@XmlVariabile(nome="OperNominativoUD", tipo=TipoVariabile.SEMPLICE)
	private String operNominativoUD;	

	@XmlVariabile(nome="TipiNominativoUD", tipo=TipoVariabile.SEMPLICE)
	private String tipiNominativoUD;	

	@XmlVariabile(nome="StatoPubblicazione", tipo=TipoVariabile.SEMPLICE)
	private String statoPubblicazione;
	
	@XmlVariabile(nome="CdCAtto", tipo=TipoVariabile.SEMPLICE)
	private String centroDiCosto;
	
	@XmlVariabile(nome="DataScadenzaDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaDa;
	
	@XmlVariabile(nome="DataScadenzaA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaA;
	
	@XmlVariabile(nome="CF_Nominativo", tipo=TipoVariabile.SEMPLICE)
	private String cfNominativoCollegato;
	
	@XmlVariabile(nome="FlgRiservatezza", tipo=TipoVariabile.SEMPLICE)
	private String flgRiservatezza;	
	
    @XmlVariabile(nome="PubblicazioneRettificata", tipo=TipoVariabile.SEMPLICE)
	private String pubblicazioneRettificata;
	
    @XmlVariabile(nome="PubblicazioneAnnullata", tipo=TipoVariabile.SEMPLICE)
   	private String pubblicazioneAnnullata;
   	  
    @XmlVariabile(nome="@UOPubblicazione", tipo=TipoVariabile.LISTA)
	private List<UoPubblicazione> uoPubblicazione;
    
    @XmlVariabile(nome="GGDurataPubblicazioneDa", tipo=TipoVariabile.SEMPLICE)
 	private String ggDurataPubblicazioneDa;
 	
 	@XmlVariabile(nome="GGDurataPubblicazioneA", tipo=TipoVariabile.SEMPLICE)
 	private String ggDurataPubblicazioneA;
 	
 	@XmlVariabile(nome="InizioPubblicazioneDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioPubblicazioneDal;
	
	@XmlVariabile(nome="InizioPubblicazioneAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioPubblicazioneAl;
	
	@XmlVariabile(nome="TerminePubblicazioneDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtTerminePubblicazioneDal;
	
	@XmlVariabile(nome="TerminePubblicazioneAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtTerminePubblicazioneAl;
		
	@XmlVariabile(nome="@UtentiPresaInCarico", tipo=TipoVariabile.LISTA)
	private List<Utente> utentiPresoInCarico;
	
	@XmlVariabile(nome="@UtentiPresaVisione", tipo=TipoVariabile.LISTA)
	private List<Utente> utentiPresaVisioneEffettuata;
	
	@XmlVariabile(nome="PresenzaFileFirmati", tipo=TipoVariabile.SEMPLICE)
	private String flgConFileFirmati;
	
	@XmlVariabile(nome="@FormatiFile", tipo=TipoVariabile.LISTA)
	private List<FormatiFile> formatiElettronici;
	
	@XmlVariabile(nome="OrganiDecentratiCompetenti", tipo=TipoVariabile.SEMPLICE)
	private String organiDecentratiCompetenti;
	
	@XmlVariabile(nome="SedutaCollegiale.Organo", tipo=TipoVariabile.SEMPLICE)
	private String sedutaCollegialeOrgano;
	
	@XmlVariabile(nome="SedutaCollegiale.Data", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtSedutaCollegialeData;
	
	@XmlVariabile(nome="SedutaCollegiale.Esito", tipo=TipoVariabile.SEMPLICE)
	private String sedutaCollegialeEsito;

	@XmlVariabile(nome="PassaggioDaSmistamento", tipo=TipoVariabile.SEMPLICE)
	private String flgPassaggioDaSmistamento;
	
	
	@XmlVariabile(nome="DtScansioneDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtScansioneDal;
	
	@XmlVariabile(nome="DtScansioneAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtScansioneAl;
	
	@XmlVariabile(nome="FlgArchivioPregresso", tipo=TipoVariabile.SEMPLICE)
	private String flgArchivioPregresso;	
	
	@XmlVariabile(nome="PresenzaOpere", tipo=TipoVariabile.SEMPLICE)
	private String presenzaOpere;
		
	@XmlVariabile(nome="SottoTipologiaAtto", tipo=TipoVariabile.SEMPLICE)
	private String sottoTipologiaAtto;
	
	
	@XmlVariabile(nome="StatiTrasfBloomfleet", tipo=TipoVariabile.SEMPLICE)
	private String statiTrasfBloomfleet;

	@XmlVariabile(nome="ListaRegoleProtAuto", tipo=TipoVariabile.SEMPLICE)
	private String regoleRegistrazioneAutomaticaEmail;

	
	@XmlVariabile(nome="RdAvsAltriAtti", tipo=TipoVariabile.SEMPLICE)
	private String rdAeAttiCollegati;
	
	@XmlVariabile(nome="StatoClassFascDocumenti", tipo=TipoVariabile.SEMPLICE)
	private String statoClassFascDocumenti;
	
	
	public String getIdNode() {
		return idNode;
	}

	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}

	public List<Registrazione> getRegistrazioni() {
		return registrazioni;
	}

	public void setRegistrazioni(List<Registrazione> registrazioni) {
		this.registrazioni = registrazioni;
	}

	public Date getDtRaccomandataDal() {
		return dtRaccomandataDal;
	}

	public void setDtRaccomandataDal(Date dtRaccomandataDal) {
		this.dtRaccomandataDal = dtRaccomandataDal;
	}

	public Date getDtRaccomandataAl() {
		return dtRaccomandataAl;
	}

	public void setDtRaccomandataAl(Date dtRaccomandataAl) {
		this.dtRaccomandataAl = dtRaccomandataAl;
	}

	public String getNroRaccomandata() {
		return nroRaccomandata;
	}

	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}

	public Date getDataAperturaDa() {
		return dataAperturaDa;
	}

	public void setDataAperturaDa(Date dataAperturaDa) {
		this.dataAperturaDa = dataAperturaDa;
	}

	public Date getDataAperturaA() {
		return dataAperturaA;
	}

	public void setDataAperturaA(Date dataAperturaA) {
		this.dataAperturaA = dataAperturaA;
	}

	public Date getDataChiusuraDa() {
		return dataChiusuraDa;
	}

	public void setDataChiusuraDa(Date dataChiusuraDa) {
		this.dataChiusuraDa = dataChiusuraDa;
	}

	public Date getDataChiusuraA() {
		return dataChiusuraA;
	}

	public void setDataChiusuraA(Date dataChiusuraA) {
		this.dataChiusuraA = dataChiusuraA;
	}

	public String getFlagTipoProvDoc() {
		return flagTipoProvDoc;
	}

	public void setFlagTipoProvDoc(String flagTipoProvDoc) {
		this.flagTipoProvDoc = flagTipoProvDoc;
	}

	public String getStatoAssegnazione() {
		return statoAssegnazione;
	}

	public void setStatoAssegnazione(String statoAssegnazione) {
		this.statoAssegnazione = statoAssegnazione;
	}

	public String getStatoClassificazione() {
		return statoClassificazione;
	}

	public void setStatoClassificazione(String statoClassificazione) {
		this.statoClassificazione = statoClassificazione;
	}

	public String getStatoFascicolazione() {
		return statoFascicolazione;
	}

	public void setStatoFascicolazione(String statoFascicolazione) {
		this.statoFascicolazione = statoFascicolazione;
	}

	public String getSoloRecenti() {
		return soloRecenti;
	}

	public void setSoloRecenti(String soloRecenti) {
		this.soloRecenti = soloRecenti;
	}

	public List<Stato> getStatiDoc() {
		return statiDoc;
	}

	public void setStatiDoc(List<Stato> statiDoc) {
		this.statiDoc = statiDoc;
	}

	public List<Stato> getStatiDettDoc() {
		return statiDettDoc;
	}

	public void setStatiDettDoc(List<Stato> statiDettDoc) {
		this.statiDettDoc = statiDettDoc;
	}

	public List<Stato> getStatiFolder() {
		return statiFolder;
	}

	public void setStatiFolder(List<Stato> statiFolder) {
		this.statiFolder = statiFolder;
	}

	public List<Stato> getStatiDettFolder() {
		return statiDettFolder;
	}

	public void setStatiDettFolder(List<Stato> statiDettFolder) {
		this.statiDettFolder = statiDettFolder;
	}

	public String getInteresseCessato() {
		return interesseCessato;
	}

	public void setInteresseCessato(String interesseCessato) {
		this.interesseCessato = interesseCessato;
	}

	public String getStatoVisione() {
		return statoVisione;
	}

	public void setStatoVisione(String statoVisione) {
		this.statoVisione = statoVisione;
	}

	public String getSoloDaLeggere() {
		return soloDaLeggere;
	}

	public void setSoloDaLeggere(String soloDaLeggere) {
		this.soloDaLeggere = soloDaLeggere;
	}

	public String getCodRuoloUtenteVsDocFolder() {
		return codRuoloUtenteVsDocFolder;
	}

	public void setCodRuoloUtenteVsDocFolder(String codRuoloUtenteVsDocFolder) {
		this.codRuoloUtenteVsDocFolder = codRuoloUtenteVsDocFolder;
	}

	public String getFlagFirmaApposta() {
		return flagFirmaApposta;
	}

	public void setFlagFirmaApposta(String flagFirmaApposta) {
		this.flagFirmaApposta = flagFirmaApposta;
	}

	public String getNomeWorkspaceApp() {
		return nomeWorkspaceApp;
	}

	public void setNomeWorkspaceApp(String nomeWorkspaceApp) {
		this.nomeWorkspaceApp = nomeWorkspaceApp;
	}

	public String getSoloAperti() {
		return soloAperti;
	}

	public void setSoloAperti(String soloAperti) {
		this.soloAperti = soloAperti;
	}

	public Date getDataInvioDa() {
		return dataInvioDa;
	}

	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}

	public Date getDataInvioA() {
		return dataInvioA;
	}

	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}

	public String getDestinatarioInvio() {
		return destinatarioInvio;
	}

	public void setDestinatarioInvio(String destinatarioInvio) {
		this.destinatarioInvio = destinatarioInvio;
	}

	public Date getDataArchiviazioneDa() {
		return dataArchiviazioneDa;
	}

	public void setDataArchiviazioneDa(Date dataArchiviazioneDa) {
		this.dataArchiviazioneDa = dataArchiviazioneDa;
	}

	public Date getPresaInCaricoDal() {
		return presaInCaricoDal;
	}

	public void setPresaInCaricoDal(Date presaInCaricoDal) {
		this.presaInCaricoDal = presaInCaricoDal;
	}

	public Date getPresaInCaricoAl() {
		return presaInCaricoAl;
	}

	public void setPresaInCaricoAl(Date presaInCaricoAl) {
		this.presaInCaricoAl = presaInCaricoAl;
	}

	public Date getDataArchiviazioneA() {
		return dataArchiviazioneA;
	}

	public void setDataArchiviazioneA(Date dataArchiviazioneA) {
		this.dataArchiviazioneA = dataArchiviazioneA;
	}

	public Date getDataEliminazioneDa() {
		return dataEliminazioneDa;
	}

	public void setDataEliminazioneDa(Date dataEliminazioneDa) {
		this.dataEliminazioneDa = dataEliminazioneDa;
	}

	public Date getDataEliminazioneA() {
		return dataEliminazioneA;
	}

	public void setDataEliminazioneA(Date dataEliminazioneA) {
		this.dataEliminazioneA = dataEliminazioneA;
	}

	public String getSezioneEliminazione() {
		return sezioneEliminazione;
	}

	public void setSezioneEliminazione(String sezioneEliminazione) {
		this.sezioneEliminazione = sezioneEliminazione;
	}

	public String getRestringiSottofascInserti() {
		return restringiSottofascInserti;
	}

	public void setRestringiSottofascInserti(String restringiSottofascInserti) {
		this.restringiSottofascInserti = restringiSottofascInserti;
	}

	public String getSoloDocRicevutiViaEmail() {
		return soloDocRicevutiViaEmail;
	}

	public void setSoloDocRicevutiViaEmail(String soloDocRicevutiViaEmail) {
		this.soloDocRicevutiViaEmail = soloDocRicevutiViaEmail;
	}

	public String getInviatiViaEmail() {
		return inviatiViaEmail;
	}

	public void setInviatiViaEmail(String inviatiViaEmail) {
		this.inviatiViaEmail = inviatiViaEmail;
	}

	public String getRestringiNews() {
		return restringiNews;
	}

	public void setRestringiNews(String restringiNews) {
		this.restringiNews = restringiNews;
	}

	public String getNewsConNotificheCondivisione() {
		return newsConNotificheCondivisione;
	}

	public void setNewsConNotificheCondivisione(String newsConNotificheCondivisione) {
		this.newsConNotificheCondivisione = newsConNotificheCondivisione;
	}

	public String getNewsConOsservazioni() {
		return newsConOsservazioni;
	}

	public void setNewsConOsservazioni(String newsConOsservazioni) {
		this.newsConOsservazioni = newsConOsservazioni;
	}

	public String getNewsConNotificheAutomatiche() {
		return newsConNotificheAutomatiche;
	}

	public void setNewsConNotificheAutomatiche(String newsConNotificheAutomatiche) {
		this.newsConNotificheAutomatiche = newsConNotificheAutomatiche;
	}

	public String getStatoPresaInCarico() {
		return statoPresaInCarico;
	}

	public void setStatoPresaInCarico(String statoPresaInCarico) {
		this.statoPresaInCarico = statoPresaInCarico;
	}

	public String getArchiviatoFileTimbrato() {
		return archiviatoFileTimbrato;
	}

	public void setArchiviatoFileTimbrato(String archiviatoFileTimbrato) {
		this.archiviatoFileTimbrato = archiviatoFileTimbrato;
	}

	public List<StatoRichAnnullamentoReg> getStatiRichiestaAnnullamentoReg() {
		return statiRichiestaAnnullamentoReg;
	}

	public void setStatiRichiestaAnnullamentoReg(List<StatoRichAnnullamentoReg> statiRichiestaAnnullamentoReg) {
		this.statiRichiestaAnnullamentoReg = statiRichiestaAnnullamentoReg;
	}

	public String getRestringiAttiAutAnnReg() {
		return restringiAttiAutAnnReg;
	}

	public void setRestringiAttiAutAnnReg(String restringiAttiAutAnnReg) {
		this.restringiAttiAutAnnReg = restringiAttiAutAnnReg;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdFolderType() {
		return idFolderType;
	}

	public void setIdFolderType(String idFolderType) {
		this.idFolderType = idFolderType;
	}

	public Date getDataAssegnSmistDa() {
		return dataAssegnSmistDa;
	}

	public void setDataAssegnSmistDa(Date dataAssegnSmistDa) {
		this.dataAssegnSmistDa = dataAssegnSmistDa;
	}

	public Date getDataAssegnSmistA() {
		return dataAssegnSmistA;
	}

	public void setDataAssegnSmistA(Date dataAssegnSmistA) {
		this.dataAssegnSmistA = dataAssegnSmistA;
	}

	public String getTipoAssegnazione() {
		return tipoAssegnazione;
	}

	public void setTipoAssegnazione(String tipoAssegnazione) {
		this.tipoAssegnazione = tipoAssegnazione;
	}

	public Date getDataNotificaDa() {
		return dataNotificaDa;
	}

	public void setDataNotificaDa(Date dataNotificaDa) {
		this.dataNotificaDa = dataNotificaDa;
	}

	public Date getDataNotificaA() {
		return dataNotificaA;
	}

	public void setDataNotificaA(Date dataNotificaA) {
		this.dataNotificaA = dataNotificaA;
	}

	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public List<DestInvioNotifica> getInviatiA() {
		return inviatiA;
	}

	public void setInviatiA(List<DestInvioNotifica> inviatiA) {
		this.inviatiA = inviatiA;
	}

	public List<DestInvioNotifica> getAssegnatiA() {
		return assegnatiA;
	}

	public void setAssegnatiA(List<DestInvioNotifica> assegnatiA) {
		this.assegnatiA = assegnatiA;
	}

	public List<DestInvioNotifica> getInConoscenzaA() {
		return inConoscenzaA;
	}

	public void setInConoscenzaA(List<DestInvioNotifica> inConoscenzaA) {
		this.inConoscenzaA = inConoscenzaA;
	}

	public List<DestInvioNotifica> getConNotificheAutoA() {
		return conNotificheAutoA;
	}

	public void setConNotificheAutoA(List<DestInvioNotifica> conNotificheAutoA) {
		this.conNotificheAutoA = conNotificheAutoA;
	}

	public String getSoloRegAnnullate() {
		return soloRegAnnullate;
	}

	public void setSoloRegAnnullate(String soloRegAnnullate) {
		this.soloRegAnnullate = soloRegAnnullate;
	}

	public String getMittenteUD() {
		return mittenteUD;
	}

	public void setMittenteUD(String mittenteUD) {
		this.mittenteUD = mittenteUD;
	}

	public String getOperMittenteUD() {
		return operMittenteUD;
	}

	public void setOperMittenteUD(String operMittenteUD) {
		this.operMittenteUD = operMittenteUD;
	}

	public String getIdMittenteUDInRubrica() {
		return idMittenteUDInRubrica;
	}

	public void setIdMittenteUDInRubrica(String idMittenteUDInRubrica) {
		this.idMittenteUDInRubrica = idMittenteUDInRubrica;
	}

	public String getDestinatarioUD() {
		return destinatarioUD;
	}

	public void setDestinatarioUD(String destinatarioUD) {
		this.destinatarioUD = destinatarioUD;
	}

	public String getOperDestinatarioUD() {
		return operDestinatarioUD;
	}

	public void setOperDestinatarioUD(String operDestinatarioUD) {
		this.operDestinatarioUD = operDestinatarioUD;
	}

	public String getIdDestinatarioUDInRubrica() {
		return idDestinatarioUDInRubrica;
	}

	public void setIdDestinatarioUDInRubrica(String idDestinatarioUDInRubrica) {
		this.idDestinatarioUDInRubrica = idDestinatarioUDInRubrica;
	}

	public String getEsibenteUD() {
		return esibenteUD;
	}

	public void setEsibenteUD(String esibenteUD) {
		this.esibenteUD = esibenteUD;
	}

	public String getOperEsibenteUD() {
		return operEsibenteUD;
	}

	public void setOperEsibenteUD(String operEsibenteUD) {
		this.operEsibenteUD = operEsibenteUD;
	}

	public String getIdEsibenteUDInRubrica() {
		return idEsibenteUDInRubrica;
	}

	public void setIdEsibenteUDInRubrica(String idEsibenteUDInRubrica) {
		this.idEsibenteUDInRubrica = idEsibenteUDInRubrica;
	}

	public String getOggettoUD() {
		return oggettoUD;
	}

	public void setOggettoUD(String oggettoUD) {
		this.oggettoUD = oggettoUD;
	}

	public String getNomeFolder() {
		return nomeFolder;
	}

	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}

	public String getPresenzaFile() {
		return presenzaFile;
	}

	public void setPresenzaFile(String presenzaFile) {
		this.presenzaFile = presenzaFile;
	}

	public List<MezziTrasmissioneFilterBean> getMezziTrasmissione() {
		return mezziTrasmissione;
	}

	public void setMezziTrasmissione(List<MezziTrasmissioneFilterBean> mezziTrasmissione) {
		this.mezziTrasmissione = mezziTrasmissione;
	}

	public String getNomeDocType() {
		return nomeDocType;
	}

	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}

	public String getContraenteUD() {
		return contraenteUD;
	}

	public void setContraenteUD(String contraenteUD) {
		this.contraenteUD = contraenteUD;
	}

	public String getpIvaCfContraenteUD() {
		return pIvaCfContraenteUD;
	}

	public void setpIvaCfContraenteUD(String pIvaCfContraenteUD) {
		this.pIvaCfContraenteUD = pIvaCfContraenteUD;
	}

	public String getClienteUD() {
		return clienteUD;
	}

	public void setClienteUD(String clienteUD) {
		this.clienteUD = clienteUD;
	}

	public String getOperClienteUD() {
		return operClienteUD;
	}

	public void setOperClienteUD(String operClienteUD) {
		this.operClienteUD = operClienteUD;
	}

	public String getCiProvClienteUD() {
		return ciProvClienteUD;
	}

	public void setCiProvClienteUD(String ciProvClienteUD) {
		this.ciProvClienteUD = ciProvClienteUD;
	}

	public String getpIvaCfClienteUD() {
		return pIvaCfClienteUD;
	}

	public void setpIvaCfClienteUD(String pIvaCfClienteUD) {
		this.pIvaCfClienteUD = pIvaCfClienteUD;
	}

	public List<UoProponente> getUoProponente() {
		return uoProponente;
	}

	public void setUoProponente(List<UoProponente> uoProponente) {
		this.uoProponente = uoProponente;
	}

	public List<UoRegistrazione> getUoRegistrazione() {
		return uoRegistrazione;
	}

	public void setUoRegistrazione(List<UoRegistrazione> uoRegistrazione) {
		this.uoRegistrazione = uoRegistrazione;
	}

	public List<UoApertura> getUoApertura() {
		return uoApertura;
	}

	public void setUoApertura(List<UoApertura> uoApertura) {
		this.uoApertura = uoApertura;
	}

	public List<Utente> getUtentiAvvioAtto() {
		return utentiAvvioAtto;
	}

	public void setUtentiAvvioAtto(List<Utente> utentiAvvioAtto) {
		this.utentiAvvioAtto = utentiAvvioAtto;
	}

	public List<Utente> getUtentiAdozioneAtto() {
		return utentiAdozioneAtto;
	}

	public void setUtentiAdozioneAtto(List<Utente> utentiAdozioneAtto) {
		this.utentiAdozioneAtto = utentiAdozioneAtto;
	}

	public String getNoteUd() {
		return noteUd;
	}

	public void setNoteUd(String noteUd) {
		this.noteUd = noteUd;
	}

	public Date getDataFirmaAttoDa() {
		return dataFirmaAttoDa;
	}

	public void setDataFirmaAttoDa(Date dataFirmaAttoDa) {
		this.dataFirmaAttoDa = dataFirmaAttoDa;
	}

	public Date getDataFirmaAttoA() {
		return dataFirmaAttoA;
	}

	public void setDataFirmaAttoA(Date dataFirmaAttoA) {
		this.dataFirmaAttoA = dataFirmaAttoA;
	}

	public String getNroProtRicevuto() {
		return nroProtRicevuto;
	}

	public void setNroProtRicevuto(String nroProtRicevuto) {
		this.nroProtRicevuto = nroProtRicevuto;
	}

	public Date getDataProtRicevutoDa() {
		return dataProtRicevutoDa;
	}

	public void setDataProtRicevutoDa(Date dataProtRicevutoDa) {
		this.dataProtRicevutoDa = dataProtRicevutoDa;
	}

	public Date getDataProtRicevutoA() {
		return dataProtRicevutoA;
	}

	public void setDataProtRicevutoA(Date dataProtRicevutoA) {
		this.dataProtRicevutoA = dataProtRicevutoA;
	}

	public String getStatoTrasmissioneEmail() {
		return statoTrasmissioneEmail;
	}

	public void setStatoTrasmissioneEmail(String statoTrasmissioneEmail) {
		this.statoTrasmissioneEmail = statoTrasmissioneEmail;
	}

	public String getNroRichiestaStampaExpDa() {
		return nroRichiestaStampaExpDa;
	}

	public void setNroRichiestaStampaExpDa(String nroRichiestaStampaExpDa) {
		this.nroRichiestaStampaExpDa = nroRichiestaStampaExpDa;
	}

	public String getNroRichiestaStampaExpA() {
		return nroRichiestaStampaExpA;
	}

	public void setNroRichiestaStampaExpA(String nroRichiestaStampaExpA) {
		this.nroRichiestaStampaExpA = nroRichiestaStampaExpA;
	}

	public Date getTsRichiestaStampaExpDa() {
		return tsRichiestaStampaExpDa;
	}

	public void setTsRichiestaStampaExpDa(Date tsRichiestaStampaExpDa) {
		this.tsRichiestaStampaExpDa = tsRichiestaStampaExpDa;
	}

	public Date getTsRichiestaStampaExpA() {
		return tsRichiestaStampaExpA;
	}

	public void setTsRichiestaStampaExpA(Date tsRichiestaStampaExpA) {
		this.tsRichiestaStampaExpA = tsRichiestaStampaExpA;
	}

	public String getCapoFilaFasc() {
		return capoFilaFasc;
	}

	public void setCapoFilaFasc(String capoFilaFasc) {
		this.capoFilaFasc = capoFilaFasc;
	}

	public String getProtocolloCapofila() {
		return protocolloCapofila;
	}

	public void setProtocolloCapofila(String protocolloCapofila) {
		this.protocolloCapofila = protocolloCapofila;
	}

	public String getProtocolloCollegato() {
		return protocolloCollegato;
	}

	public void setProtocolloCollegato(String protocolloCollegato) {
		this.protocolloCollegato = protocolloCollegato;
	}

	public String getIdProvenienza() {
		return idProvenienza;
	}

	public void setIdProvenienza(String idProvenienza) {
		this.idProvenienza = idProvenienza;
	}

	public String getIdPdVUltimoInvio() {
		return idPdVUltimoInvio;
	}

	public void setIdPdVUltimoInvio(String idPdVUltimoInvio) {
		this.idPdVUltimoInvio = idPdVUltimoInvio;
	}

	public Date getDataInvioConservazioneDa() {
		return dataInvioConservazioneDa;
	}

	public void setDataInvioConservazioneDa(Date dataInvioConservazioneDa) {
		this.dataInvioConservazioneDa = dataInvioConservazioneDa;
	}

	public Date getDataInvioConservazioneA() {
		return dataInvioConservazioneA;
	}

	public void setDataInvioConservazioneA(Date dataInvioConservazioneA) {
		this.dataInvioConservazioneA = dataInvioConservazioneA;
	}

	public Date getDataAcquisizioneInConservazioneDa() {
		return dataAcquisizioneInConservazioneDa;
	}

	public void setDataAcquisizioneInConservazioneDa(Date dataAcquisizioneInConservazioneDa) {
		this.dataAcquisizioneInConservazioneDa = dataAcquisizioneInConservazioneDa;
	}

	public Date getDataAcquisizioneInConservazioneA() {
		return dataAcquisizioneInConservazioneA;
	}

	public void setDataAcquisizioneInConservazioneA(Date dataAcquisizioneInConservazioneA) {
		this.dataAcquisizioneInConservazioneA = dataAcquisizioneInConservazioneA;
	}

	public Date getDataInizioConservazioneDa() {
		return dataInizioConservazioneDa;
	}

	public void setDataInizioConservazioneDa(Date dataInizioConservazioneDa) {
		this.dataInizioConservazioneDa = dataInizioConservazioneDa;
	}

	public Date getDataInizioConservazioneA() {
		return dataInizioConservazioneA;
	}

	public void setDataInizioConservazioneA(Date dataInizioConservazioneA) {
		this.dataInizioConservazioneA = dataInizioConservazioneA;
	}

	public String getIdInConservazione() {
		return idInConservazione;
	}

	public void setIdInConservazione(String idInConservazione) {
		this.idInConservazione = idInConservazione;
	}

	public Date getDataTermineConservazioneDa() {
		return dataTermineConservazioneDa;
	}

	public void setDataTermineConservazioneDa(Date dataTermineConservazioneDa) {
		this.dataTermineConservazioneDa = dataTermineConservazioneDa;
	}

	public Date getDataTermineConservazioneA() {
		return dataTermineConservazioneA;
	}

	public void setDataTermineConservazioneA(Date dataTermineConservazioneA) {
		this.dataTermineConservazioneA = dataTermineConservazioneA;
	}

	public Date getDataScartoDa() {
		return dataScartoDa;
	}

	public void setDataScartoDa(Date dataScartoDa) {
		this.dataScartoDa = dataScartoDa;
	}

	public Date getDataScartoA() {
		return dataScartoA;
	}

	public void setDataScartoA(Date dataScartoA) {
		this.dataScartoA = dataScartoA;
	}

	public String getFlgProlungatoTempoConservazione() {
		return flgProlungatoTempoConservazione;
	}

	public void setFlgProlungatoTempoConservazione(String flgProlungatoTempoConservazione) {
		this.flgProlungatoTempoConservazione = flgProlungatoTempoConservazione;
	}

	public String getFlgRichiestaEsibizioneAlConservatore() {
		return flgRichiestaEsibizioneAlConservatore;
	}

	public void setFlgRichiestaEsibizioneAlConservatore(String flgRichiestaEsibizioneAlConservatore) {
		this.flgRichiestaEsibizioneAlConservatore = flgRichiestaEsibizioneAlConservatore;
	}

	public String getNomeUD() {
		return nomeUD;
	}

	public void setNomeUD(String nomeUD) {
		this.nomeUD = nomeUD;
	}

	public String getEscludiUOSVSubordinati() {
		return escludiUOSVSubordinati;
	}

	public void setEscludiUOSVSubordinati(String escludiUOSVSubordinati) {
		this.escludiUOSVSubordinati = escludiUOSVSubordinati;
	}

	public String getIdIndirizzoInVario() {
		return idIndirizzoInVario;
	}

	public void setIdIndirizzoInVario(String idIndirizzoInVario) {
		this.idIndirizzoInVario = idIndirizzoInVario;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public List<SupportiOrigDoc> getSupportiOrigDoc() {
		return supportiOrigDoc;
	}

	public void setSupportiOrigDoc(List<SupportiOrigDoc> supportiOrigDoc) {
		this.supportiOrigDoc = supportiOrigDoc;
	}

	public String getIdClassificazione() {
		return idClassificazione;
	}

	public void setIdClassificazione(String idClassificazione) {
		this.idClassificazione = idClassificazione;
	}

	public String getLivelliClassificazione() {
		return livelliClassificazione;
	}

	public void setLivelliClassificazione(String livelliClassificazione) {
		this.livelliClassificazione = livelliClassificazione;
	}

	public String getFlgIncludiSottoClassifiche() {
		return flgIncludiSottoClassifiche;
	}

	public void setFlgIncludiSottoClassifiche(String flgIncludiSottoClassifiche) {
		this.flgIncludiSottoClassifiche = flgIncludiSottoClassifiche;
	}

	public String getAnnoFascicoloDa() {
		return annoFascicoloDa;
	}

	public void setAnnoFascicoloDa(String annoFascicoloDa) {
		this.annoFascicoloDa = annoFascicoloDa;
	}

	public String getAnnoFascicoloA() {
		return annoFascicoloA;
	}

	public void setAnnoFascicoloA(String annoFascicoloA) {
		this.annoFascicoloA = annoFascicoloA;
	}

	public String getNroFascicoloDa() {
		return nroFascicoloDa;
	}

	public void setNroFascicoloDa(String nroFascicoloDa) {
		this.nroFascicoloDa = nroFascicoloDa;
	}

	public String getNroFascicoloA() {
		return nroFascicoloA;
	}

	public void setNroFascicoloA(String nroFascicoloA) {
		this.nroFascicoloA = nroFascicoloA;
	}

	public String getNroSottoFascicoloDa() {
		return nroSottoFascicoloDa;
	}

	public void setNroSottoFascicoloDa(String nroSottoFascicoloDa) {
		this.nroSottoFascicoloDa = nroSottoFascicoloDa;
	}

	public String getNroSottoFascicoloA() {
		return nroSottoFascicoloA;
	}

	public void setNroSottoFascicoloA(String nroSottoFascicoloA) {
		this.nroSottoFascicoloA = nroSottoFascicoloA;
	}

	public String getCodiceFascicolo() {
		return codiceFascicolo;
	}

	public void setCodiceFascicolo(String codiceFascicolo) {
		this.codiceFascicolo = codiceFascicolo;
	}

	public String getEstremiAttoAutAnnullamento() {
		return estremiAttoAutAnnullamento;
	}

	public void setEstremiAttoAutAnnullamento(String estremiAttoAutAnnullamento) {
		this.estremiAttoAutAnnullamento = estremiAttoAutAnnullamento;
	}

	public Date getDtStesuraDal() {
		return dtStesuraDal;
	}

	public void setDtStesuraDal(Date dtStesuraDal) {
		this.dtStesuraDal = dtStesuraDal;
	}

	public Date getDtStesuraAl() {
		return dtStesuraAl;
	}

	public void setDtStesuraAl(Date dtStesuraAl) {
		this.dtStesuraAl = dtStesuraAl;
	}

	public String getNominativoUD() {
		return nominativoUD;
	}

	public void setNominativoUD(String nominativoUD) {
		this.nominativoUD = nominativoUD;
	}

	public String getOperNominativoUD() {
		return operNominativoUD;
	}

	public void setOperNominativoUD(String operNominativoUD) {
		this.operNominativoUD = operNominativoUD;
	}

	public String getTipiNominativoUD() {
		return tipiNominativoUD;
	}

	public void setTipiNominativoUD(String tipiNominativoUD) {
		this.tipiNominativoUD = tipiNominativoUD;
	}

	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}

	public void setStatoPubblicazione(String statoPubblicazione) {
		this.statoPubblicazione = statoPubblicazione;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public Date getDataScadenzaDa() {
		return dataScadenzaDa;
	}

	public void setDataScadenzaDa(Date dataScadenzaDa) {
		this.dataScadenzaDa = dataScadenzaDa;
	}

	public Date getDataScadenzaA() {
		return dataScadenzaA;
	}

	public void setDataScadenzaA(Date dataScadenzaA) {
		this.dataScadenzaA = dataScadenzaA;
	}

	public String getCfNominativoCollegato() {
		return cfNominativoCollegato;
	}

	public void setCfNominativoCollegato(String cfNominativoCollegato) {
		this.cfNominativoCollegato = cfNominativoCollegato;
	}

	public String getFlgRiservatezza() {
		return flgRiservatezza;
	}

	public void setFlgRiservatezza(String flgRiservatezza) {
		this.flgRiservatezza = flgRiservatezza;
	}

	public String getPubblicazioneRettificata() {
		return pubblicazioneRettificata;
	}

	public void setPubblicazioneRettificata(String pubblicazioneRettificata) {
		this.pubblicazioneRettificata = pubblicazioneRettificata;
	}

	public String getPubblicazioneAnnullata() {
		return pubblicazioneAnnullata;
	}

	public void setPubblicazioneAnnullata(String pubblicazioneAnnullata) {
		this.pubblicazioneAnnullata = pubblicazioneAnnullata;
	}

	public List<UoPubblicazione> getUoPubblicazione() {
		return uoPubblicazione;
	}

	public void setUoPubblicazione(List<UoPubblicazione> uoPubblicazione) {
		this.uoPubblicazione = uoPubblicazione;
	}

	public String getGgDurataPubblicazioneDa() {
		return ggDurataPubblicazioneDa;
	}

	public void setGgDurataPubblicazioneDa(String ggDurataPubblicazioneDa) {
		this.ggDurataPubblicazioneDa = ggDurataPubblicazioneDa;
	}

	public String getGgDurataPubblicazioneA() {
		return ggDurataPubblicazioneA;
	}

	public void setGgDurataPubblicazioneA(String ggDurataPubblicazioneA) {
		this.ggDurataPubblicazioneA = ggDurataPubblicazioneA;
	}

	public Date getDtInizioPubblicazioneDal() {
		return dtInizioPubblicazioneDal;
	}

	public void setDtInizioPubblicazioneDal(Date dtInizioPubblicazioneDal) {
		this.dtInizioPubblicazioneDal = dtInizioPubblicazioneDal;
	}

	public Date getDtInizioPubblicazioneAl() {
		return dtInizioPubblicazioneAl;
	}

	public void setDtInizioPubblicazioneAl(Date dtInizioPubblicazioneAl) {
		this.dtInizioPubblicazioneAl = dtInizioPubblicazioneAl;
	}

	public Date getDtTerminePubblicazioneDal() {
		return dtTerminePubblicazioneDal;
	}

	public void setDtTerminePubblicazioneDal(Date dtTerminePubblicazioneDal) {
		this.dtTerminePubblicazioneDal = dtTerminePubblicazioneDal;
	}

	public Date getDtTerminePubblicazioneAl() {
		return dtTerminePubblicazioneAl;
	}

	public void setDtTerminePubblicazioneAl(Date dtTerminePubblicazioneAl) {
		this.dtTerminePubblicazioneAl = dtTerminePubblicazioneAl;
	}

	public List<Utente> getUtentiPresoInCarico() {
		return utentiPresoInCarico;
	}

	public void setUtentiPresoInCarico(List<Utente> utentiPresoInCarico) {
		this.utentiPresoInCarico = utentiPresoInCarico;
	}

	public List<Utente> getUtentiPresaVisioneEffettuata() {
		return utentiPresaVisioneEffettuata;
	}

	public void setUtentiPresaVisioneEffettuata(List<Utente> utentiPresaVisioneEffettuata) {
		this.utentiPresaVisioneEffettuata = utentiPresaVisioneEffettuata;
	}

	public String getFlgConFileFirmati() {
		return flgConFileFirmati;
	}

	public void setFlgConFileFirmati(String flgConFileFirmati) {
		this.flgConFileFirmati = flgConFileFirmati;
	}

	public List<FormatiFile> getFormatiElettronici() {
		return formatiElettronici;
	}

	public void setFormatiElettronici(List<FormatiFile> formatiElettronici) {
		this.formatiElettronici = formatiElettronici;
	}

	public String getOrganiDecentratiCompetenti() {
		return organiDecentratiCompetenti;
	}

	public void setOrganiDecentratiCompetenti(String organiDecentratiCompetenti) {
		this.organiDecentratiCompetenti = organiDecentratiCompetenti;
	}

	public String getSedutaCollegialeOrgano() {
		return sedutaCollegialeOrgano;
	}

	public void setSedutaCollegialeOrgano(String sedutaCollegialeOrgano) {
		this.sedutaCollegialeOrgano = sedutaCollegialeOrgano;
	}

	public Date getDtSedutaCollegialeData() {
		return dtSedutaCollegialeData;
	}

	public void setDtSedutaCollegialeData(Date dtSedutaCollegialeData) {
		this.dtSedutaCollegialeData = dtSedutaCollegialeData;
	}

	public String getSedutaCollegialeEsito() {
		return sedutaCollegialeEsito;
	}

	public void setSedutaCollegialeEsito(String sedutaCollegialeEsito) {
		this.sedutaCollegialeEsito = sedutaCollegialeEsito;
	}

	public String getFlgPassaggioDaSmistamento() {
		return flgPassaggioDaSmistamento;
	}

	public void setFlgPassaggioDaSmistamento(String flgPassaggioDaSmistamento) {
		this.flgPassaggioDaSmistamento = flgPassaggioDaSmistamento;
	}

	public List<UoCompetente> getUoCompetente() {
		return uoCompetente;
	}

	public void setUoCompetente(List<UoCompetente> uoCompetente) {
		this.uoCompetente = uoCompetente;
	}

	public Date getDtScansioneDal() {
		return dtScansioneDal;
	}

	public void setDtScansioneDal(Date dtScansioneDal) {
		this.dtScansioneDal = dtScansioneDal;
	}

	public Date getDtScansioneAl() {
		return dtScansioneAl;
	}

	public void setDtScansioneAl(Date dtScansioneAl) {
		this.dtScansioneAl = dtScansioneAl;
	}

	public String getFlgArchivioPregresso() {
		return flgArchivioPregresso;
	}

	public void setFlgArchivioPregresso(String flgArchivioPregresso) {
		this.flgArchivioPregresso = flgArchivioPregresso;
	}

	public String getPresenzaOpere() {
		return presenzaOpere;
	}

	public void setPresenzaOpere(String presenzaOpere) {
		this.presenzaOpere = presenzaOpere;
	}

	public String getSottoTipologiaAtto() {
		return sottoTipologiaAtto;
	}

	public void setSottoTipologiaAtto(String sottoTipologiaAtto) {
		this.sottoTipologiaAtto = sottoTipologiaAtto;
	}

	public String getStatiTrasfBloomfleet() {
		return statiTrasfBloomfleet;
	}

	public void setStatiTrasfBloomfleet(String statiTrasfBloomfleet) {
		this.statiTrasfBloomfleet = statiTrasfBloomfleet;
	}

	public String getRegoleRegistrazioneAutomaticaEmail() {
		return regoleRegistrazioneAutomaticaEmail;
	}

	public void setRegoleRegistrazioneAutomaticaEmail(String regoleRegistrazioneAutomaticaEmail) {
		this.regoleRegistrazioneAutomaticaEmail = regoleRegistrazioneAutomaticaEmail;
	}

	public String getRdAeAttiCollegati() {
		return rdAeAttiCollegati;
	}

	public void setRdAeAttiCollegati(String rdAeAttiCollegati) {
		this.rdAeAttiCollegati = rdAeAttiCollegati;
	}

	public String getStatoClassFascDocumenti() {
		return statoClassFascDocumenti;
	}

	public void setStatoClassFascDocumenti(String statoClassFascDocumenti) {
		this.statoClassFascDocumenti = statoClassFascDocumenti;
	}

	
	
}