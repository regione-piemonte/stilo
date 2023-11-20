/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttachmentInvioNotEmailBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DestinatarioSUEBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.FileDaPubblicareSUEBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.RegNumPrincipale;

public class XmlDatiEventoOutBean {

	@XmlVariabile(nome = "#IdEvento", tipo = TipoVariabile.SEMPLICE)
	private String idEvento;
	@XmlVariabile(nome = "#RowidEvento", tipo = TipoVariabile.SEMPLICE)
	private String rowidEvento;
	@XmlVariabile(nome = "#IdEventType", tipo = TipoVariabile.SEMPLICE)
	private String idEventType;
	@XmlVariabile(nome = "#NomeEventType", tipo = TipoVariabile.SEMPLICE)
	private String nomeEventType;
	@XmlVariabile(nome = "#WarningMsg", tipo = TipoVariabile.SEMPLICE)
	private String warningMsg;
	@XmlVariabile(nome = "#FlgEventoDurativo", tipo = TipoVariabile.SEMPLICE)
	private Flag flgEventoDurativo;
	@XmlVariabile(nome = "#IdFolder", tipo = TipoVariabile.SEMPLICE)
	private String idFolder;
	@XmlVariabile(nome = "#IdUD", tipo = TipoVariabile.SEMPLICE)
	private String idUD;
	@XmlVariabile(nome = "#EstremiUD", tipo = TipoVariabile.SEMPLICE)
	private String estremiUD;
	@XmlVariabile(nome = "#TsInizioEvento", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsInizioEvento;
	@XmlVariabile(nome = "#TsFineEvento", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsFineEvento;
	@XmlVariabile(nome = "#IdDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private String idDocPrimario;
	@XmlVariabile(nome = "#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String idDocType;
	@XmlVariabile(nome = "#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocType;
	@XmlVariabile(nome = "#FlgDocEntrata", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDocEntrata;
	@XmlVariabile(nome = "#FilePrimario", tipo = TipoVariabile.NESTED)
	private FilePrimarioOutBean filePrimario;
	@XmlVariabile(nome = "#RegNumPrincipale", tipo = TipoVariabile.NESTED)
	private RegNumPrincipale regNumPrincipale;
	@XmlVariabile(nome = "#RegNumPrincipale.DtRegistrazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRegistrazione;
	@XmlVariabile(nome = "#RifDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String rifDocRicevuto;
	@XmlVariabile(nome = "#EstremiRegDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String estremiRegDocRicevuto;
	@XmlVariabile(nome = "#AnnoDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String annoDocRicevuto;
	@XmlVariabile(nome = "#DtDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtDocRicevuto;
	@XmlVariabile(nome = "#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String desOgg;
	@XmlVariabile(nome = "#DtStesura", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtStesura;
	@XmlVariabile(nome = "#@Allegati", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> allegati;
	@XmlVariabile(nome = "#NoteTask", tipo = TipoVariabile.SEMPLICE)
	private String noteTask;
	@XmlVariabile(nome = "#PresenzaEsito", tipo = TipoVariabile.SEMPLICE)
	private Flag presenzaEsito;
	@XmlVariabile(nome = "#Esito", tipo = TipoVariabile.SEMPLICE)
	private String esito;
	@XmlVariabile(nome = "#@ValoriEsito", tipo = TipoVariabile.LISTA)
	private List<ValoriEsitoOutBean> valoriEsito;
	@XmlVariabile(nome = "#@AttrCustomToShow", tipo = TipoVariabile.LISTA)
	private List<AttrCustomToShowOutBean> attrCustomToShow;
	@XmlVariabile(nome = "#IdModelloDoc", tipo = TipoVariabile.SEMPLICE)
	private String idModelloDoc;
	@XmlVariabile(nome = "#NomeModelloDoc", tipo = TipoVariabile.SEMPLICE)
	private String nomeModelloDoc;	
	@XmlVariabile(nome = "#URIModelloDoc", tipo = TipoVariabile.SEMPLICE)
	private String uriModelloDoc;
	@XmlVariabile(nome = "#TipoModelloDoc", tipo = TipoVariabile.SEMPLICE)
	private String tipoModelloDoc;
	@XmlVariabile(nome = "#DisplaynameFileDaModello", tipo = TipoVariabile.SEMPLICE)
	private String displaynameFileDaModello;
	@XmlVariabile(nome = "#MimetypeModelloDoc", tipo = TipoVariabile.SEMPLICE)
	private String mimetypeModelloDoc;
	@XmlVariabile(nome = "#NomeTastoSalvaProvvisorio", tipo = TipoVariabile.SEMPLICE)
	private String nomeTastoSalvaProvvisorio;
	@XmlVariabile(nome = "#NomeTastoSalvaDefinitivo", tipo = TipoVariabile.SEMPLICE)
	private String nomeTastoSalvaDefinitivo;
	@XmlVariabile(nome = "#NomeTastoSalvaProvvisorio_2", tipo = TipoVariabile.SEMPLICE)
	private String nomeTastoSalvaProvvisorio_2;
	@XmlVariabile(nome = "#NomeTastoSalvaDefinitivo_2", tipo = TipoVariabile.SEMPLICE)
	private String nomeTastoSalvaDefinitivo_2;
	@XmlVariabile(nome = "#AlertConfermaSalvaDefinitivo", tipo = TipoVariabile.SEMPLICE)
	private String alertConfermaSalvaDefinitivo;
	@XmlVariabile(nome = "#DocActions.@EsitiTask", tipo = TipoVariabile.LISTA)
	private List<EsitoTaskOkBean> esitiTaskAzioni;	
	@XmlVariabile(nome = "#DocActions.IdDoc", tipo = TipoVariabile.SEMPLICE)
	private String idDocAzioni;
	@XmlVariabile(nome = "#DocActions.Firma", tipo = TipoVariabile.SEMPLICE)
	private String flgFirmaFile;
	@XmlVariabile(nome = "#DocActions.ShowAnteprimaFileDaFirmare", tipo = TipoVariabile.SEMPLICE)
	private String flgShowAnteprimaFileDaFirmare;
	@XmlVariabile(nome = "#DocActions.Timbra", tipo = TipoVariabile.SEMPLICE)
	private String flgTimbraFile;
	@XmlVariabile(nome = "#DocActions.Protocolla", tipo = TipoVariabile.SEMPLICE)
	private String flgProtocolla;	
	@XmlVariabile(nome = "#DocActions.UnioneFile", tipo = TipoVariabile.SEMPLICE)
	private String flgUnioneFile;
	@XmlVariabile(nome = "#DocActions.UnioneFile.NomeFile", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNomeFile;
	@XmlVariabile(nome = "#DocActions.UnioneFile.NomeFileConOmissis", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNomeFileOmissis;
	@XmlVariabile(nome = "#DocActions.UnioneFile.IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileIdDocType;
	@XmlVariabile(nome = "#DocActions.UnioneFile.NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNomeDocType;
	@XmlVariabile(nome = "#DocActions.UnioneFile.Descrizione", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileDescrizione;
	@XmlVariabile(nome = "#ShowAnteprimaDefinitiva", tipo = TipoVariabile.SEMPLICE)
	private String flgShowAnteprimaDefinitiva;
	@XmlVariabile(nome = "#DocActions.ConversionePdf", tipo = TipoVariabile.SEMPLICE)
	private String flgConversionePdf;	
	@XmlVariabile(nome = "#DocActions.InvioPEC", tipo = TipoVariabile.SEMPLICE)
	private String flgInvioPEC;
	@XmlVariabile(nome = "#DocActions.InvioPEC.Subject", tipo = TipoVariabile.SEMPLICE)
	private String invioPECSubject;
	@XmlVariabile(nome = "#DocActions.InvioPEC.Body", tipo = TipoVariabile.SEMPLICE)
	private String invioPECBody;
	@XmlVariabile(nome = "#DocActions.InvioPEC.Destinatari", tipo = TipoVariabile.SEMPLICE)
	private String invioPECDestinatari;
	@XmlVariabile(nome = "#DocActions.InvioPEC.DestinatariCC", tipo = TipoVariabile.SEMPLICE)
	private String invioPECDestinatariCC;
	@XmlVariabile(nome = "#DocActions.InvioPEC.IdCasellaMittente", tipo = TipoVariabile.SEMPLICE)
	private String invioPECIdCasellaMittente;
	@XmlVariabile(nome = "#DocActions.InvioPEC.IndirizzoMittente", tipo = TipoVariabile.SEMPLICE)
	private String invioPECIndirizzoMittente;
	@XmlVariabile(nome = "#DocActions.InvioPEC.@Attach", tipo = TipoVariabile.SEMPLICE)
	private String invioPECAttach;
	@XmlVariabile(nome = "#DocActions.SUE.TipoEvento", tipo = TipoVariabile.SEMPLICE)
	private String eventoSUETipo;
	@XmlVariabile(nome = "#DocActions.SUE.IdPratica", tipo = TipoVariabile.SEMPLICE)
	private String eventoSUEIdPratica;
	@XmlVariabile(nome = "#DocActions.SUE.CodFiscale", tipo = TipoVariabile.SEMPLICE)
	private String eventoSUECodFiscale;
	@XmlVariabile(nome = "#DocActions.SUE.GiorniSospensione", tipo = TipoVariabile.SEMPLICE)
	private String eventoSUEGiorniSospensione;
	@XmlVariabile(nome = "#DocActions.SUE.EventoPubblico ", tipo = TipoVariabile.SEMPLICE)
	private String eventoSUEFlgPubblico;
	@XmlVariabile(nome = "#DocActions.SUE.@Destinatari", tipo = TipoVariabile.LISTA)
	private List<DestinatarioSUEBean> eventoSUEDestinatari;
	@XmlVariabile(nome = "#DocActions.SUE.@FileDaPubblicare", tipo = TipoVariabile.LISTA)
	private List<FileDaPubblicareSUEBean> eventoSUEFileDaPubblicare;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail", tipo = TipoVariabile.SEMPLICE)
	private String flgInvioNotEmail;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.Subject", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailSubject;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.Body", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailBody;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.@Destinatari", tipo = TipoVariabile.LISTA)
	private List<SimpleValueBean> invioNotEmailDestinatari;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.@DestinatariCC", tipo = TipoVariabile.LISTA)
	private List<SimpleValueBean> invioNotEmailDestinatariCC;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.@DestinatariCCN", tipo = TipoVariabile.LISTA)
	private List<SimpleValueBean> invioNotEmailDestinatariCCN;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.IdCasellaMittente", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailIdCasellaMittente;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.IndirizzoMittente", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailIndirizzoMittente;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.AliasIndirizzoMittente", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailAliasIndirizzoMittente;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.IsPEC", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailFlgPEC;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.@Attachment", tipo = TipoVariabile.LISTA)
	private List<AttachmentInvioNotEmailBean> invioNotEmailAttachment;	
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.FlgInvioMailXComplTask", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailFlgInvioMailXComplTask;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.ConfermaInvio", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailFlgConfermaInvio;
	@XmlVariabile(nome = "#DocActions.InvioNotEmail.CallXDettagliMail", tipo = TipoVariabile.SEMPLICE)
	private String invioNotEmailFlgCallXDettagliMail;
	@XmlVariabile(nome = "#Actions.NotificaMail", tipo = TipoVariabile.SEMPLICE)
	private String flgNotificaMail;
	@XmlVariabile(nome = "#Actions.NotificaMail.Subject", tipo = TipoVariabile.SEMPLICE)
	private String notificaMailSubject;
	@XmlVariabile(nome = "#Actions.NotificaMail.Body", tipo = TipoVariabile.SEMPLICE)
	private String notificaMailBody;
	@XmlVariabile(nome = "#Actions.NotificaMail.Destinatari", tipo = TipoVariabile.SEMPLICE)
	private String notificaMailDestinatari;
	@XmlVariabile(nome = "#Actions.NotificaMail.DestinatariCC", tipo = TipoVariabile.SEMPLICE)
	private String notificaMailDestinatariCC;
	@XmlVariabile(nome = "#Actions.NotificaMail.IdCasellaMittente", tipo = TipoVariabile.SEMPLICE)
	private String notificaMailIdCasellaMittente;
	@XmlVariabile(nome = "#Actions.NotificaMail.IndirizzoMittente", tipo = TipoVariabile.SEMPLICE)
	private String notificaMailIndirizzoMittente;
	@XmlVariabile(nome = "#DocActions.InvioFtpAlbo", tipo = TipoVariabile.SEMPLICE)
	private String flgInvioFtpAlbo;
	@XmlVariabile(nome = "#DocActions.Pubblica", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblica;
	@XmlVariabile(nome = "#DocActions.Pubblica.CompilaDatiPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String flgCompilaDatiPubblicazione;	
	@XmlVariabile(nome = "#DataInizioPubbl", tipo = TipoVariabile.SEMPLICE)
	private String dataInizioPubblicazione;
	@XmlVariabile(nome = "#GGDurataPubbl", tipo = TipoVariabile.SEMPLICE)
	private String giorniPubblicazione;
	@XmlVariabile(nome = "#RichIntPostConferenzaServizi", tipo = TipoVariabile.SEMPLICE)
	private Flag ulterioriIntegrazioni;
	@XmlVariabile(nome = "#RichIntegrNro", tipo = TipoVariabile.SEMPLICE)
	private String richIntegrNro;
	@XmlVariabile(nome = "#DesEnteAppartenenza", tipo = TipoVariabile.SEMPLICE)
	private String desEnteAppartenenza;
	@XmlVariabile(nome = "@FileDaUnire", tipo = TipoVariabile.LISTA)
	private List<FileDaUnireBean> fileDaUnire;
	@XmlVariabile(nome = "#NomeFileUnione", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileUnione;
	@XmlVariabile(nome = "@FileDaUnire_VersIntegrale", tipo = TipoVariabile.LISTA)
	private List<FileDaUnireBean> fileDaUnireVersIntegrale;
	@XmlVariabile(nome = "#NomeFileUnione_VersIntegrale", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileUnioneVersIntegrale;
	@XmlVariabile(nome = "#TipoDocFileUnioneVersIntegrale", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocFileUnioneVersIntegrale;
	@XmlVariabile(nome = "#SiglaRegistroAtto", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroAtto;
	@XmlVariabile(nome = "#SiglaRegistroAtto2", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroAtto2;
	@XmlVariabile(nome = "#Editor", tipo = TipoVariabile.SEMPLICE)
	private String editor;
	@XmlVariabile(nome = "#NomeAttrCustomEsitoTask", tipo = TipoVariabile.SEMPLICE)
	private String nomeAttrCustomEsitoTask;
	@XmlVariabile(nome = "#FlgReadOnly", tipo = TipoVariabile.SEMPLICE)
	private Flag flgReadOnly;
	@XmlVariabile(nome = "#IdModCopertina", tipo = TipoVariabile.SEMPLICE)
	private String idModCopertina;
	@XmlVariabile(nome = "#NomeModCopertina", tipo = TipoVariabile.SEMPLICE)
	private String nomeModCopertina;
	@XmlVariabile(nome = "#UriModCopertina", tipo = TipoVariabile.SEMPLICE)
	private String uriModCopertina;
	@XmlVariabile(nome = "#TipoModCopertina", tipo = TipoVariabile.SEMPLICE)
	private String tipoModCopertina;
	@XmlVariabile(nome = "#IdModCopertinaFinale", tipo = TipoVariabile.SEMPLICE)
	private String idModCopertinaFinale;
	@XmlVariabile(nome = "#NomeModCopertinaFinale", tipo = TipoVariabile.SEMPLICE)
	private String nomeModCopertinaFinale;
	@XmlVariabile(nome = "#UriModCopertinaFinale", tipo = TipoVariabile.SEMPLICE)
	private String uriModCopertinaFinale;
	@XmlVariabile(nome = "#TipoModCopertinaFinale", tipo = TipoVariabile.SEMPLICE)
	private String tipoModCopertinaFinale;	
	@XmlVariabile(nome = "#IdModAllegatiParteIntSeparati", tipo = TipoVariabile.SEMPLICE)
	private String idModAllegatiParteIntSeparati;
	@XmlVariabile(nome = "#NomeModAllegatiParteIntSeparati", tipo = TipoVariabile.SEMPLICE)
	private String nomeModAllegatiParteIntSeparati;
	@XmlVariabile(nome = "#UriModAllegatiParteIntSeparati", tipo = TipoVariabile.SEMPLICE)
	private String uriModAllegatiParteIntSeparati;
	@XmlVariabile(nome = "#TipoModAllegatiParteIntSeparati", tipo = TipoVariabile.SEMPLICE)
	private String tipoModAllegatiParteIntSeparati;
	@XmlVariabile(nome = "#IdModAllegatiParteIntSeparatiXPubbl", tipo = TipoVariabile.SEMPLICE)
	private String idModAllegatiParteIntSeparatiXPubbl;
	@XmlVariabile(nome = "#NomeModAllegatiParteIntSeparatiXPubbl", tipo = TipoVariabile.SEMPLICE)
	private String nomeModAllegatiParteIntSeparatiXPubbl;
	@XmlVariabile(nome = "#UriModAllegatiParteIntSeparatiXPubbl", tipo = TipoVariabile.SEMPLICE)
	private String uriModAllegatiParteIntSeparatiXPubbl;
	@XmlVariabile(nome = "#TipoModAllegatiParteIntSeparatiXPubbl", tipo = TipoVariabile.SEMPLICE)
	private String tipoModAllegatiParteIntSeparatiXPubbl;	
	@XmlVariabile(nome = "#AppendiceDaUnire", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAppendiceDaUnire;
	@XmlVariabile(nome = "#IdModAppendice", tipo = TipoVariabile.SEMPLICE)
	private String idModAppendice;
	@XmlVariabile(nome = "#NomeModAppendice", tipo = TipoVariabile.SEMPLICE)
	private String nomeModAppendice;
	@XmlVariabile(nome = "#UriModAppendice", tipo = TipoVariabile.SEMPLICE)
	private String uriModAppendice;
	@XmlVariabile(nome = "#TipoModAppendice", tipo = TipoVariabile.SEMPLICE)
	private String tipoModAppendice;
	@XmlVariabile(nome = "#URIAppendice", tipo = TipoVariabile.SEMPLICE)
	private String uriAppendice;
	@XmlVariabile(nome = "#DisplayFilenameAppendice", tipo = TipoVariabile.SEMPLICE)
	private String displayFilenameModAppendice;	
	@XmlVariabile(nome = "#IdModFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String idModFoglioFirme;
	@XmlVariabile(nome = "#NomeModFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String nomeModFoglioFirme;
	@XmlVariabile(nome = "#DisplayFilenameFoglioFirme", tipo = TipoVariabile.SEMPLICE)
	private String displayFilenameModFoglioFirme;	
	@XmlVariabile(nome = "#IdModFoglioFirme2", tipo = TipoVariabile.SEMPLICE)
	private String idModFoglioFirme2;
	@XmlVariabile(nome = "#NomeModFoglioFirme2", tipo = TipoVariabile.SEMPLICE)
	private String nomeModFoglioFirme2;
	@XmlVariabile(nome = "#DisplayFilenameFoglioFirme2", tipo = TipoVariabile.SEMPLICE)
	private String displayFilenameModFoglioFirme2;	
	@XmlVariabile(nome = "#IdModSchedaTrasp", tipo = TipoVariabile.SEMPLICE)
	private String idModSchedaTrasp;
	@XmlVariabile(nome = "#NomeModSchedaTrasp", tipo = TipoVariabile.SEMPLICE)
	private String nomeModSchedaTrasp;
	@XmlVariabile(nome = "#DisplayFilenameSchedaTrasp", tipo = TipoVariabile.SEMPLICE)
	private String displayFilenameModSchedaTrasp;	
	@XmlVariabile(nome = "#@AttributiAddDocTabs", tipo = TipoVariabile.LISTA)
	private List<AttributiAddDocTabBean> attributiAddDocTabs;
	@XmlVariabile(nome = "#@EsitiTaskOK", tipo = TipoVariabile.LISTA)
	private List<EsitoTaskOkBean> esitiTaskOk;
	@XmlVariabile(nome = "#@EsitiTaskKO", tipo = TipoVariabile.LISTA)
	private List<EsitoTaskOkBean> esitiTaskKo;
	@XmlVariabile(nome = "#FileUnioneDaFirmare", tipo = TipoVariabile.SEMPLICE)
	private Flag fileUnioneDaFirmare;
	@XmlVariabile(nome = "#FileUnioneDaTimbrare", tipo = TipoVariabile.SEMPLICE)
	private Flag fileUnioneDaTimbrare;
	// impostazioni per aggiunta intestazione allegati in fase di unione file
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Attiva", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileInfoAllegatoAttiva;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Pagine", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoPagine;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Formato", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoFormato;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Margine", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoMargine;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Orientamento", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoOrientamento;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Font", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoFont;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.FontSize", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoFontSize;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.TextColor", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoTextColor;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.Style", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoStyle;
	@XmlVariabile(nome = "#UnioneFile.InfoAllegato.MaxLenNomeFileAllegato", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileInfoAllegatoMaxLenNomeFileAllegato;
	// impostazioni per aggiunta numero pagina agli allegati in fase di unione file
	@XmlVariabile(nome = "#UnioneFile.NroPagina.Attiva", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaAttiva;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.Formato", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNroPaginaFormato;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.Posizione", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNroPaginaPosizione;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.EsludiAllegatiA4+", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaEscludiAllegatiMaggioriA4;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.NumerazioneDistintaXFileUniti", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaNumerazioneDistintaXFileUniti;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.Font", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNroPaginaFont;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.FontSize", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNroPaginaFontSize;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.TextColor", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNroPaginaTextColor;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.Style", tipo = TipoVariabile.SEMPLICE)
	private String unioneFileNroPaginaStyle;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.EscludiAllegatiUnitiInMezzo", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaEscludiAllegatiUnitiInMezzo;	
	@XmlVariabile(nome = "#UnioneFile.NroPagina.EscludiAllegatiUnitiInFondo", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaEscludiAllegatiUnitiInFondo;	
	@XmlVariabile(nome = "#UnioneFile.NroPagina.EscludiFoglioFirmeGrafiche", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaEscludiFoglioFirmeGrafiche;	
	@XmlVariabile(nome = "#UnioneFile.NroPagina.EscludiListaAllegatiSeparati", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaEscludiListaAllegatiSeparati;
	@XmlVariabile(nome = "#UnioneFile.NroPagina.EscludiAppendice", tipo = TipoVariabile.SEMPLICE)
	private Flag unioneFileNroPaginaEscludiAppendice;
	@XmlVariabile(nome = "#Timbro.Posizione", tipo = TipoVariabile.SEMPLICE)
	private String timbroPosizione;
	@XmlVariabile(nome = "#Timbro.Rotazione", tipo = TipoVariabile.SEMPLICE)
	private String timbroRotazione;
	@XmlVariabile(nome = "#Timbro.SelezionePagine", tipo = TipoVariabile.SEMPLICE)
	private String timbroSelezionePagine;
	@XmlVariabile(nome = "#Timbro.PaginaDa", tipo = TipoVariabile.SEMPLICE)
	private String timbroPaginaDa;
	@XmlVariabile(nome = "#Timbro.PaginaA", tipo = TipoVariabile.SEMPLICE)
	private String timbroPaginaA;
	@XmlVariabile(nome = "#CodNaturaRelVsUD", tipo = TipoVariabile.SEMPLICE)
	private String codNaturaRelVsUD;
	@XmlVariabile(nome = "#CodTabDefault", tipo = TipoVariabile.SEMPLICE)
	private String codTabDefault;
	@XmlVariabile(nome = "#TaskDoc.IdTipo", tipo = TipoVariabile.SEMPLICE)
	private String idTipoTaskDoc;
	@XmlVariabile(nome = "#TaskDoc.NomeTipo", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoTaskDoc;
	@XmlVariabile(nome = "#TaskDoc.Parere", tipo = TipoVariabile.SEMPLICE)
	private Flag flgParereTaskDoc;
	@XmlVariabile(nome = "#TaskDoc.ParteDispositivo", tipo = TipoVariabile.SEMPLICE)
	private Flag flgParteDispositivoTaskDoc;
	@XmlVariabile(nome = "#TaskDoc.EscludiPubbl", tipo = TipoVariabile.SEMPLICE)
	private Flag flgNoPubblTaskDoc;
	@XmlVariabile(nome = "#TaskDoc.PubblicaSeparato", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPubblicaSeparatoTaskDoc;
	@XmlVariabile(nome = "#@TaskDoc.ControlliXEsiti", tipo = TipoVariabile.LISTA)
	private List<ControlloXEsitoTaskDocBean> controlliXEsitiTaskDoc;
	@XmlVariabile(nome = "#AttivaModificaEsclusionePubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String attivaModificaEsclusionePubblicazione;
	@XmlVariabile(nome = "#AttivaEliminazioneUOCoinvolte", tipo = TipoVariabile.SEMPLICE)
	private String attivaEliminazioneUOCoinvolte;	
	@XmlVariabile(nome = "#SiglaRegistroNumFinale", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroNumFinale;
	@XmlVariabile(nome = "#TaskArchivio", tipo = TipoVariabile.SEMPLICE)
	private Flag taskArchivio;
	@XmlVariabile(nome = "#FlgDatiSpesaModificabili", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiSpesaEditabili;
	@XmlVariabile(nome = "#FlgCIGModificabile", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCIGEditabile;
	@XmlVariabile(nome = "#DocActions.SIB.TipoEvento", tipo = TipoVariabile.SEMPLICE)
	private String tipoEventoSIB;
	@XmlVariabile(nome = "#DocActions.SIB.TipoEvento.@Esiti", tipo = TipoVariabile.LISTA)
	private List<EsitoTaskOkBean> esitiTaskEventoSIB;		
	@XmlVariabile(nome = "#DocActions.SIB.@TipiEventoXEsito", tipo = TipoVariabile.LISTA)
	private List<EventoXEsitoTaskBean> tipiEventoSIBXEsitoTask;	
	@XmlVariabile(nome = "#DocActions.SIB.IdDirAdottante", tipo = TipoVariabile.SEMPLICE)
	private String idUoDirAdottanteSIB;
	@XmlVariabile(nome = "#DocActions.SIB.CodDirAdottante", tipo = TipoVariabile.SEMPLICE)
	private String codUoDirAdottanteSIB;
	@XmlVariabile(nome = "#DocActions.SIB.DesDirAdottante", tipo = TipoVariabile.SEMPLICE)
	private String desUoDirAdottanteSIB;	
	@XmlVariabile(nome = "#DocActions.Contabilia.@TipiEventoXEsito", tipo = TipoVariabile.LISTA)
	private List<EventoXEsitoTaskBean> tipiEventoContabiliaXEsitoTask;
	@XmlVariabile(nome = "#DocActions.SICRA.@TipiEventoXEsito", tipo = TipoVariabile.LISTA)
	private List<EventoXEsitoTaskBean> tipiEventoSICRAXEsitoTask;
	@XmlVariabile(nome = "#DocActions.CWOL.@TipiEventoXEsito", tipo = TipoVariabile.LISTA)
	private List<EventoXEsitoTaskBean> tipiEventoCWOLXEsitoTask;
	@XmlVariabile(nome = "#AttivaSmistamento", tipo = TipoVariabile.SEMPLICE)
	private String flgAttivaSmistamento;	
	@XmlVariabile(nome = "#RecuperaMovimentiContabDefinitivi", tipo = TipoVariabile.SEMPLICE)
	private String flgRecuperaMovimentiContabDefinitivi;
	@XmlVariabile(nome = "#NomeAttrListaDatiContabili", tipo = TipoVariabile.SEMPLICE)
	private String nomeAttrListaDatiContabili;
	@XmlVariabile(nome = "#NomeAttrListaDatiContabiliRichiesti", tipo = TipoVariabile.SEMPLICE)
	private String nomeAttrListaDatiContabiliRichiesti;	
	@XmlVariabile(nome = "#AttivaRequestMovimentiDaAMC", tipo = TipoVariabile.SEMPLICE)
	private String flgAttivaRequestMovimentiDaAMC;
	@XmlVariabile(nome = "#AttivaSalvataggioMovimentiDaAMC", tipo = TipoVariabile.SEMPLICE)
	private String flgAttivaSalvataggioMovimentiDaAMC;		
	@XmlVariabile(nome = "#EscludiFiltroCdCVsAMC", tipo = TipoVariabile.SEMPLICE)
	private String flgEscludiFiltroCdCVsAMC;
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.LivelloMaxUOPerRevisioneGrafica", tipo = TipoVariabile.SEMPLICE)
	private String livelloMaxRevisione;
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.TipiUOXRevisioneGrafica", tipo = TipoVariabile.SEMPLICE)
	private String tipiUORevisione;
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.nomeVersConfronto", tipo = TipoVariabile.SEMPLICE)
	private String nomeVersConfrontoOrganigramma;	
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.nroVersConfronto", tipo = TipoVariabile.SEMPLICE)
	private String nroVersConfrontoOrganigramma;	
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.nroVersLavoro", tipo = TipoVariabile.SEMPLICE)
	private String nroVersLavoroOrganigramma;	
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.azione", tipo = TipoVariabile.SEMPLICE)
	private String azioneOrganigramma;	
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.idDocOrganigramma", tipo = TipoVariabile.SEMPLICE)
	private String idDocOrganigramma;	
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.idDocArchiviazionePdf", tipo = TipoVariabile.SEMPLICE)
	private String idDocArchiviazionePdfOrganigramma;
	@XmlVariabile(nome = "#ParamsEditorOrganigramma.livelloRevisione", tipo = TipoVariabile.SEMPLICE)
	private String livelloRevisioneOrganigramma;
	@XmlVariabile(nome = "#IsDelibera", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDelibera;
	@XmlVariabile(nome = "#ShowDirigentiProponenti", tipo = TipoVariabile.SEMPLICE)
	private String showDirigentiProponenti;
	@XmlVariabile(nome = "#ShowAssessori", tipo = TipoVariabile.SEMPLICE)
	private String showAssessori;
	@XmlVariabile(nome = "#ShowConsiglieri", tipo = TipoVariabile.SEMPLICE)
	private String showConsiglieri;	
	@XmlVariabile(nome = "ParametriTipoAtto.@ListaAttributiCustom", tipo = TipoVariabile.LISTA)
	private List<AttributiCustomCablatiAttoXmlBean> parametriTipoAttoAttributiCustomCablati;
	@XmlVariabile(nome = "ParametriTipoAtto.DefaultAllegParteIntegrante", tipo = TipoVariabile.SEMPLICE)
	private Flag parametriTipoAttoDefaultAllegParteIntegrante;
	@XmlVariabile(nome = "ParametriTipoAtto.DefaultAllegParteIntegranteOrdPermanente", tipo = TipoVariabile.SEMPLICE)
	private Flag parametriTipoAttoDefaultAllegParteIntegranteOrdPermanente;
	@XmlVariabile(nome = "ParametriTipoAtto.DefaultAllegParteIntegranteOrdTemporanea", tipo = TipoVariabile.SEMPLICE)
	private Flag parametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea;
	@XmlVariabile(nome = "ParametriTipoAtto.DefaultAllegPubblSeparata", tipo = TipoVariabile.SEMPLICE)
	private Flag parametriTipoAttoDefaultAllegPubblSeparata;
	@XmlVariabile(nome = "ParametriTipoAtto.AttivaSceltaPosizioneAllegatiUniti", tipo = TipoVariabile.SEMPLICE)
	private Flag parametriTipoAttoAttivaSceltaPosizioneAllegatiUniti;
	@XmlVariabile(nome = "#PubblicazioneAllegatiUguale", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblicazioneAllegatiUguale;		
	@XmlVariabile(nome = "#SoloPreparazioneVersPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String flgSoloPreparazioneVersPubblicazione;
	@XmlVariabile(nome = "#CtrlMimeTypeAllegPI", tipo = TipoVariabile.SEMPLICE)
	private String flgCtrlMimeTypeAllegPI;
	@XmlVariabile(nome = "#DocActions.PROSA.Protocolla", tipo = TipoVariabile.SEMPLICE)
	private String flgProtocollazioneProsa;
	@XmlVariabile(nome = "#PresentiEmendamenti", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPresentiEmendamenti;
	@XmlVariabile(nome = "#DocActions.FirmaVersPubblAggiornata", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFirmaVersPubblAggiornata;
	@XmlVariabile(nome = "#AbilitaAggiornaStatoAttoPostDiscussione", tipo = TipoVariabile.SEMPLICE)
	private String abilAggiornaStatoAttoPostDiscussione;
	@XmlVariabile(nome = "@StatiXAggAttoPostDiscussione", tipo = TipoVariabile.LISTA)
	private List<StatoDocBean> statiXAggAttoPostDiscussione;
	@XmlVariabile(nome = "#ExportAttoInDocFmt", tipo = TipoVariabile.SEMPLICE)
	private String exportAttoInDocFmt;	
	@XmlVariabile(nome = "#AvvioLiquidazioneContabilia", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAvvioLiquidazioneContabilia;
	@XmlVariabile(nome = "#NumPropostaDiffXStruttura", tipo = TipoVariabile.SEMPLICE)
	private String flgNumPropostaDiffXStruttura;
	@XmlVariabile(nome = "@WarningMsgXEsito", tipo = TipoVariabile.LISTA)
	private List<WarningMsgXEsitoTaskBean> warningMsgXEsitoTask;
	@XmlVariabile(nome = "#EsitoTaskDaPreimpostare", tipo = TipoVariabile.SEMPLICE)
	private String esitoTaskDaPreimpostare;	
	@XmlVariabile(nome = "#MsgTaskDaPreimpostare", tipo = TipoVariabile.SEMPLICE)
	private String msgTaskDaPreimpostare;
	@XmlVariabile(nome = "#MsgTaskProvvisorio", tipo = TipoVariabile.SEMPLICE)
	private String msgTaskProvvisorio;
	@XmlVariabile(nome = "#AbilitaSelProponentiEstesi", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAbilToSelProponentiEstesi;
	@XmlVariabile(nome = "#AttivaUploadPdfAtto", tipo = TipoVariabile.SEMPLICE)
	private String flgAttivaUploadPdfAtto;	
	@XmlVariabile(nome = "#AttivaUploadPdfAttoOmissis", tipo = TipoVariabile.SEMPLICE)
	private String flgAttivaUploadPdfAttoOmissis;	
	@XmlVariabile(nome = "#SoloOmissisModProprietaFile", tipo = TipoVariabile.SEMPLICE)
	private String flgSoloOmissisModProprietaFile;
	@XmlVariabile(nome = "#DocActions.FirmaAutomatica", tipo = TipoVariabile.SEMPLICE)
	private String flgDocActionsFirmaAutomatica;
	@XmlVariabile(nome = "#DocActions.FirmaAutomatica.Provider", tipo = TipoVariabile.SEMPLICE)
	private String docActionsFirmaAutomaticaProvider;
	@XmlVariabile(nome = "#DocActions.FirmaAutomatica.UseridFirmatario", tipo = TipoVariabile.SEMPLICE)
	private String docActionsFirmaAutomaticaUseridFirmatario;
	@XmlVariabile(nome = "#DocActions.FirmaAutomatica.FirmaInDelega", tipo = TipoVariabile.SEMPLICE)
	private String docActionsFirmaAutomaticaFirmaInDelega;
	@XmlVariabile(nome = "#DocActions.FirmaAutomatica.Password", tipo = TipoVariabile.SEMPLICE)
	private String docActionsFirmaAutomaticaPassword;
	@XmlVariabile(nome = "@InfoFirmaGrafica", tipo = TipoVariabile.LISTA)
	private List<InfoFirmaGraficaBean> infoFirmaGrafica;
	@XmlVariabile(nome = "#IdModelloDocFirmeGrafiche", tipo = TipoVariabile.SEMPLICE)
	private String idModelloDocFirmeGrafiche;	
	@XmlVariabile(nome = "#TipoModelloDocFirmeGrafiche", tipo = TipoVariabile.SEMPLICE)
	private String tipoModelloDocFirmeGrafiche;	
	@XmlVariabile(nome = "#NomeModelloDocFirmeGrafiche", tipo = TipoVariabile.SEMPLICE)
	private String nomeModelloDocFirmeGrafiche;	
	@XmlVariabile(nome = "#IdDocModelloFirmeGrafiche", tipo = TipoVariabile.SEMPLICE)
	private String idDocModelloFirmeGrafiche;	
	@XmlVariabile(nome = "#NroPagineFirmeGrafiche", tipo = TipoVariabile.SEMPLICE)
	private String nroPagineFirmeGrafiche ;	
	
	
	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	public String getRowidEvento() {
		return rowidEvento;
	}
	public void setRowidEvento(String rowidEvento) {
		this.rowidEvento = rowidEvento;
	}
	public String getIdEventType() {
		return idEventType;
	}
	public void setIdEventType(String idEventType) {
		this.idEventType = idEventType;
	}
	public String getNomeEventType() {
		return nomeEventType;
	}
	public void setNomeEventType(String nomeEventType) {
		this.nomeEventType = nomeEventType;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	public Flag getFlgEventoDurativo() {
		return flgEventoDurativo;
	}
	public void setFlgEventoDurativo(Flag flgEventoDurativo) {
		this.flgEventoDurativo = flgEventoDurativo;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getIdUD() {
		return idUD;
	}
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	public String getEstremiUD() {
		return estremiUD;
	}
	public void setEstremiUD(String estremiUD) {
		this.estremiUD = estremiUD;
	}
	public Date getTsInizioEvento() {
		return tsInizioEvento;
	}
	public void setTsInizioEvento(Date tsInizioEvento) {
		this.tsInizioEvento = tsInizioEvento;
	}
	public Date getTsFineEvento() {
		return tsFineEvento;
	}
	public void setTsFineEvento(Date tsFineEvento) {
		this.tsFineEvento = tsFineEvento;
	}
	public String getIdDocPrimario() {
		return idDocPrimario;
	}
	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	public Flag getFlgDocEntrata() {
		return flgDocEntrata;
	}
	public void setFlgDocEntrata(Flag flgDocEntrata) {
		this.flgDocEntrata = flgDocEntrata;
	}
	public FilePrimarioOutBean getFilePrimario() {
		return filePrimario;
	}
	public void setFilePrimario(FilePrimarioOutBean filePrimario) {
		this.filePrimario = filePrimario;
	}
	public RegNumPrincipale getRegNumPrincipale() {
		return regNumPrincipale;
	}
	public void setRegNumPrincipale(RegNumPrincipale regNumPrincipale) {
		this.regNumPrincipale = regNumPrincipale;
	}
	public Date getDtRegistrazione() {
		return dtRegistrazione;
	}
	public void setDtRegistrazione(Date dtRegistrazione) {
		this.dtRegistrazione = dtRegistrazione;
	}
	public String getRifDocRicevuto() {
		return rifDocRicevuto;
	}
	public void setRifDocRicevuto(String rifDocRicevuto) {
		this.rifDocRicevuto = rifDocRicevuto;
	}
	public String getEstremiRegDocRicevuto() {
		return estremiRegDocRicevuto;
	}
	public void setEstremiRegDocRicevuto(String estremiRegDocRicevuto) {
		this.estremiRegDocRicevuto = estremiRegDocRicevuto;
	}
	public String getAnnoDocRicevuto() {
		return annoDocRicevuto;
	}
	public void setAnnoDocRicevuto(String annoDocRicevuto) {
		this.annoDocRicevuto = annoDocRicevuto;
	}
	public Date getDtDocRicevuto() {
		return dtDocRicevuto;
	}
	public void setDtDocRicevuto(Date dtDocRicevuto) {
		this.dtDocRicevuto = dtDocRicevuto;
	}
	public String getDesOgg() {
		return desOgg;
	}
	public void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}
	public Date getDtStesura() {
		return dtStesura;
	}
	public void setDtStesura(Date dtStesura) {
		this.dtStesura = dtStesura;
	}
	public List<AllegatiOutBean> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<AllegatiOutBean> allegati) {
		this.allegati = allegati;
	}
	public String getNoteTask() {
		return noteTask;
	}
	public void setNoteTask(String noteTask) {
		this.noteTask = noteTask;
	}
	public Flag getPresenzaEsito() {
		return presenzaEsito;
	}
	public void setPresenzaEsito(Flag presenzaEsito) {
		this.presenzaEsito = presenzaEsito;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public List<ValoriEsitoOutBean> getValoriEsito() {
		return valoriEsito;
	}
	public void setValoriEsito(List<ValoriEsitoOutBean> valoriEsito) {
		this.valoriEsito = valoriEsito;
	}
	public List<AttrCustomToShowOutBean> getAttrCustomToShow() {
		return attrCustomToShow;
	}
	public void setAttrCustomToShow(List<AttrCustomToShowOutBean> attrCustomToShow) {
		this.attrCustomToShow = attrCustomToShow;
	}
	public String getIdModelloDoc() {
		return idModelloDoc;
	}
	public void setIdModelloDoc(String idModelloDoc) {
		this.idModelloDoc = idModelloDoc;
	}
	public String getNomeModelloDoc() {
		return nomeModelloDoc;
	}
	public void setNomeModelloDoc(String nomeModelloDoc) {
		this.nomeModelloDoc = nomeModelloDoc;
	}
	public String getUriModelloDoc() {
		return uriModelloDoc;
	}
	public void setUriModelloDoc(String uriModelloDoc) {
		this.uriModelloDoc = uriModelloDoc;
	}
	public String getTipoModelloDoc() {
		return tipoModelloDoc;
	}
	public void setTipoModelloDoc(String tipoModelloDoc) {
		this.tipoModelloDoc = tipoModelloDoc;
	}
	public String getDisplaynameFileDaModello() {
		return displaynameFileDaModello;
	}
	public void setDisplaynameFileDaModello(String displaynameFileDaModello) {
		this.displaynameFileDaModello = displaynameFileDaModello;
	}
	public String getMimetypeModelloDoc() {
		return mimetypeModelloDoc;
	}
	public void setMimetypeModelloDoc(String mimetypeModelloDoc) {
		this.mimetypeModelloDoc = mimetypeModelloDoc;
	}
	public String getNomeTastoSalvaProvvisorio() {
		return nomeTastoSalvaProvvisorio;
	}
	public void setNomeTastoSalvaProvvisorio(String nomeTastoSalvaProvvisorio) {
		this.nomeTastoSalvaProvvisorio = nomeTastoSalvaProvvisorio;
	}
	public String getNomeTastoSalvaDefinitivo() {
		return nomeTastoSalvaDefinitivo;
	}
	public void setNomeTastoSalvaDefinitivo(String nomeTastoSalvaDefinitivo) {
		this.nomeTastoSalvaDefinitivo = nomeTastoSalvaDefinitivo;
	}
	public String getNomeTastoSalvaProvvisorio_2() {
		return nomeTastoSalvaProvvisorio_2;
	}
	public void setNomeTastoSalvaProvvisorio_2(String nomeTastoSalvaProvvisorio_2) {
		this.nomeTastoSalvaProvvisorio_2 = nomeTastoSalvaProvvisorio_2;
	}
	public String getNomeTastoSalvaDefinitivo_2() {
		return nomeTastoSalvaDefinitivo_2;
	}
	public void setNomeTastoSalvaDefinitivo_2(String nomeTastoSalvaDefinitivo_2) {
		this.nomeTastoSalvaDefinitivo_2 = nomeTastoSalvaDefinitivo_2;
	}
	public String getAlertConfermaSalvaDefinitivo() {
		return alertConfermaSalvaDefinitivo;
	}
	public void setAlertConfermaSalvaDefinitivo(String alertConfermaSalvaDefinitivo) {
		this.alertConfermaSalvaDefinitivo = alertConfermaSalvaDefinitivo;
	}
	public List<EsitoTaskOkBean> getEsitiTaskAzioni() {
		return esitiTaskAzioni;
	}
	public void setEsitiTaskAzioni(List<EsitoTaskOkBean> esitiTaskAzioni) {
		this.esitiTaskAzioni = esitiTaskAzioni;
	}
	public String getIdDocAzioni() {
		return idDocAzioni;
	}
	public void setIdDocAzioni(String idDocAzioni) {
		this.idDocAzioni = idDocAzioni;
	}
	public String getFlgFirmaFile() {
		return flgFirmaFile;
	}
	public void setFlgFirmaFile(String flgFirmaFile) {
		this.flgFirmaFile = flgFirmaFile;
	}
	public String getFlgShowAnteprimaFileDaFirmare() {
		return flgShowAnteprimaFileDaFirmare;
	}
	public void setFlgShowAnteprimaFileDaFirmare(String flgShowAnteprimaFileDaFirmare) {
		this.flgShowAnteprimaFileDaFirmare = flgShowAnteprimaFileDaFirmare;
	}
	public String getFlgTimbraFile() {
		return flgTimbraFile;
	}
	public void setFlgTimbraFile(String flgTimbraFile) {
		this.flgTimbraFile = flgTimbraFile;
	}
	public String getFlgProtocolla() {
		return flgProtocolla;
	}
	public void setFlgProtocolla(String flgProtocolla) {
		this.flgProtocolla = flgProtocolla;
	}
	public String getFlgUnioneFile() {
		return flgUnioneFile;
	}
	public void setFlgUnioneFile(String flgUnioneFile) {
		this.flgUnioneFile = flgUnioneFile;
	}
	public String getUnioneFileNomeFile() {
		return unioneFileNomeFile;
	}
	public void setUnioneFileNomeFile(String unioneFileNomeFile) {
		this.unioneFileNomeFile = unioneFileNomeFile;
	}
	public String getUnioneFileNomeFileOmissis() {
		return unioneFileNomeFileOmissis;
	}
	public void setUnioneFileNomeFileOmissis(String unioneFileNomeFileOmissis) {
		this.unioneFileNomeFileOmissis = unioneFileNomeFileOmissis;
	}
	public String getUnioneFileIdDocType() {
		return unioneFileIdDocType;
	}
	public void setUnioneFileIdDocType(String unioneFileIdDocType) {
		this.unioneFileIdDocType = unioneFileIdDocType;
	}
	public String getUnioneFileNomeDocType() {
		return unioneFileNomeDocType;
	}
	public void setUnioneFileNomeDocType(String unioneFileNomeDocType) {
		this.unioneFileNomeDocType = unioneFileNomeDocType;
	}
	public String getUnioneFileDescrizione() {
		return unioneFileDescrizione;
	}
	public void setUnioneFileDescrizione(String unioneFileDescrizione) {
		this.unioneFileDescrizione = unioneFileDescrizione;
	}
	public String getFlgShowAnteprimaDefinitiva() {
		return flgShowAnteprimaDefinitiva;
	}
	public void setFlgShowAnteprimaDefinitiva(String flgShowAnteprimaDefinitiva) {
		this.flgShowAnteprimaDefinitiva = flgShowAnteprimaDefinitiva;
	}
	public String getFlgConversionePdf() {
		return flgConversionePdf;
	}
	public void setFlgConversionePdf(String flgConversionePdf) {
		this.flgConversionePdf = flgConversionePdf;
	}
	public String getFlgInvioPEC() {
		return flgInvioPEC;
	}
	public void setFlgInvioPEC(String flgInvioPEC) {
		this.flgInvioPEC = flgInvioPEC;
	}
	public String getInvioPECSubject() {
		return invioPECSubject;
	}
	public void setInvioPECSubject(String invioPECSubject) {
		this.invioPECSubject = invioPECSubject;
	}
	public String getInvioPECBody() {
		return invioPECBody;
	}
	public void setInvioPECBody(String invioPECBody) {
		this.invioPECBody = invioPECBody;
	}
	public String getInvioPECDestinatari() {
		return invioPECDestinatari;
	}
	public void setInvioPECDestinatari(String invioPECDestinatari) {
		this.invioPECDestinatari = invioPECDestinatari;
	}
	public String getInvioPECDestinatariCC() {
		return invioPECDestinatariCC;
	}
	public void setInvioPECDestinatariCC(String invioPECDestinatariCC) {
		this.invioPECDestinatariCC = invioPECDestinatariCC;
	}
	public String getInvioPECIdCasellaMittente() {
		return invioPECIdCasellaMittente;
	}
	public void setInvioPECIdCasellaMittente(String invioPECIdCasellaMittente) {
		this.invioPECIdCasellaMittente = invioPECIdCasellaMittente;
	}
	public String getInvioPECIndirizzoMittente() {
		return invioPECIndirizzoMittente;
	}
	public void setInvioPECIndirizzoMittente(String invioPECIndirizzoMittente) {
		this.invioPECIndirizzoMittente = invioPECIndirizzoMittente;
	}
	public String getInvioPECAttach() {
		return invioPECAttach;
	}
	public void setInvioPECAttach(String invioPECAttach) {
		this.invioPECAttach = invioPECAttach;
	}
	public String getEventoSUETipo() {
		return eventoSUETipo;
	}
	public void setEventoSUETipo(String eventoSUETipo) {
		this.eventoSUETipo = eventoSUETipo;
	}
	public String getEventoSUEIdPratica() {
		return eventoSUEIdPratica;
	}
	public void setEventoSUEIdPratica(String eventoSUEIdPratica) {
		this.eventoSUEIdPratica = eventoSUEIdPratica;
	}
	public String getEventoSUECodFiscale() {
		return eventoSUECodFiscale;
	}
	public void setEventoSUECodFiscale(String eventoSUECodFiscale) {
		this.eventoSUECodFiscale = eventoSUECodFiscale;
	}
	public String getEventoSUEGiorniSospensione() {
		return eventoSUEGiorniSospensione;
	}
	public void setEventoSUEGiorniSospensione(String eventoSUEGiorniSospensione) {
		this.eventoSUEGiorniSospensione = eventoSUEGiorniSospensione;
	}
	public String getEventoSUEFlgPubblico() {
		return eventoSUEFlgPubblico;
	}
	public void setEventoSUEFlgPubblico(String eventoSUEFlgPubblico) {
		this.eventoSUEFlgPubblico = eventoSUEFlgPubblico;
	}
	public List<DestinatarioSUEBean> getEventoSUEDestinatari() {
		return eventoSUEDestinatari;
	}
	public void setEventoSUEDestinatari(List<DestinatarioSUEBean> eventoSUEDestinatari) {
		this.eventoSUEDestinatari = eventoSUEDestinatari;
	}
	public List<FileDaPubblicareSUEBean> getEventoSUEFileDaPubblicare() {
		return eventoSUEFileDaPubblicare;
	}
	public void setEventoSUEFileDaPubblicare(List<FileDaPubblicareSUEBean> eventoSUEFileDaPubblicare) {
		this.eventoSUEFileDaPubblicare = eventoSUEFileDaPubblicare;
	}
	public String getFlgInvioNotEmail() {
		return flgInvioNotEmail;
	}
	public void setFlgInvioNotEmail(String flgInvioNotEmail) {
		this.flgInvioNotEmail = flgInvioNotEmail;
	}
	public String getInvioNotEmailSubject() {
		return invioNotEmailSubject;
	}
	public void setInvioNotEmailSubject(String invioNotEmailSubject) {
		this.invioNotEmailSubject = invioNotEmailSubject;
	}
	public String getInvioNotEmailBody() {
		return invioNotEmailBody;
	}
	public void setInvioNotEmailBody(String invioNotEmailBody) {
		this.invioNotEmailBody = invioNotEmailBody;
	}
	public List<SimpleValueBean> getInvioNotEmailDestinatari() {
		return invioNotEmailDestinatari;
	}
	public void setInvioNotEmailDestinatari(List<SimpleValueBean> invioNotEmailDestinatari) {
		this.invioNotEmailDestinatari = invioNotEmailDestinatari;
	}
	public List<SimpleValueBean> getInvioNotEmailDestinatariCC() {
		return invioNotEmailDestinatariCC;
	}
	public void setInvioNotEmailDestinatariCC(List<SimpleValueBean> invioNotEmailDestinatariCC) {
		this.invioNotEmailDestinatariCC = invioNotEmailDestinatariCC;
	}
	public List<SimpleValueBean> getInvioNotEmailDestinatariCCN() {
		return invioNotEmailDestinatariCCN;
	}
	public void setInvioNotEmailDestinatariCCN(List<SimpleValueBean> invioNotEmailDestinatariCCN) {
		this.invioNotEmailDestinatariCCN = invioNotEmailDestinatariCCN;
	}
	public String getInvioNotEmailIdCasellaMittente() {
		return invioNotEmailIdCasellaMittente;
	}
	public void setInvioNotEmailIdCasellaMittente(String invioNotEmailIdCasellaMittente) {
		this.invioNotEmailIdCasellaMittente = invioNotEmailIdCasellaMittente;
	}
	public String getInvioNotEmailIndirizzoMittente() {
		return invioNotEmailIndirizzoMittente;
	}
	public void setInvioNotEmailIndirizzoMittente(String invioNotEmailIndirizzoMittente) {
		this.invioNotEmailIndirizzoMittente = invioNotEmailIndirizzoMittente;
	}
	public String getInvioNotEmailAliasIndirizzoMittente() {
		return invioNotEmailAliasIndirizzoMittente;
	}
	public void setInvioNotEmailAliasIndirizzoMittente(String invioNotEmailAliasIndirizzoMittente) {
		this.invioNotEmailAliasIndirizzoMittente = invioNotEmailAliasIndirizzoMittente;
	}
	public String getInvioNotEmailFlgPEC() {
		return invioNotEmailFlgPEC;
	}
	public void setInvioNotEmailFlgPEC(String invioNotEmailFlgPEC) {
		this.invioNotEmailFlgPEC = invioNotEmailFlgPEC;
	}
	public List<AttachmentInvioNotEmailBean> getInvioNotEmailAttachment() {
		return invioNotEmailAttachment;
	}
	public void setInvioNotEmailAttachment(List<AttachmentInvioNotEmailBean> invioNotEmailAttachment) {
		this.invioNotEmailAttachment = invioNotEmailAttachment;
	}
	public String getInvioNotEmailFlgInvioMailXComplTask() {
		return invioNotEmailFlgInvioMailXComplTask;
	}
	public void setInvioNotEmailFlgInvioMailXComplTask(String invioNotEmailFlgInvioMailXComplTask) {
		this.invioNotEmailFlgInvioMailXComplTask = invioNotEmailFlgInvioMailXComplTask;
	}
	public String getInvioNotEmailFlgConfermaInvio() {
		return invioNotEmailFlgConfermaInvio;
	}
	public void setInvioNotEmailFlgConfermaInvio(String invioNotEmailFlgConfermaInvio) {
		this.invioNotEmailFlgConfermaInvio = invioNotEmailFlgConfermaInvio;
	}
	public String getInvioNotEmailFlgCallXDettagliMail() {
		return invioNotEmailFlgCallXDettagliMail;
	}
	public void setInvioNotEmailFlgCallXDettagliMail(String invioNotEmailFlgCallXDettagliMail) {
		this.invioNotEmailFlgCallXDettagliMail = invioNotEmailFlgCallXDettagliMail;
	}
	public String getFlgNotificaMail() {
		return flgNotificaMail;
	}
	public void setFlgNotificaMail(String flgNotificaMail) {
		this.flgNotificaMail = flgNotificaMail;
	}
	public String getNotificaMailSubject() {
		return notificaMailSubject;
	}
	public void setNotificaMailSubject(String notificaMailSubject) {
		this.notificaMailSubject = notificaMailSubject;
	}
	public String getNotificaMailBody() {
		return notificaMailBody;
	}
	public void setNotificaMailBody(String notificaMailBody) {
		this.notificaMailBody = notificaMailBody;
	}
	public String getNotificaMailDestinatari() {
		return notificaMailDestinatari;
	}
	public void setNotificaMailDestinatari(String notificaMailDestinatari) {
		this.notificaMailDestinatari = notificaMailDestinatari;
	}
	public String getNotificaMailDestinatariCC() {
		return notificaMailDestinatariCC;
	}
	public void setNotificaMailDestinatariCC(String notificaMailDestinatariCC) {
		this.notificaMailDestinatariCC = notificaMailDestinatariCC;
	}
	public String getNotificaMailIdCasellaMittente() {
		return notificaMailIdCasellaMittente;
	}
	public void setNotificaMailIdCasellaMittente(String notificaMailIdCasellaMittente) {
		this.notificaMailIdCasellaMittente = notificaMailIdCasellaMittente;
	}
	public String getNotificaMailIndirizzoMittente() {
		return notificaMailIndirizzoMittente;
	}
	public void setNotificaMailIndirizzoMittente(String notificaMailIndirizzoMittente) {
		this.notificaMailIndirizzoMittente = notificaMailIndirizzoMittente;
	}
	public String getFlgInvioFtpAlbo() {
		return flgInvioFtpAlbo;
	}
	public void setFlgInvioFtpAlbo(String flgInvioFtpAlbo) {
		this.flgInvioFtpAlbo = flgInvioFtpAlbo;
	}
	public String getFlgPubblica() {
		return flgPubblica;
	}
	public void setFlgPubblica(String flgPubblica) {
		this.flgPubblica = flgPubblica;
	}
	public String getFlgCompilaDatiPubblicazione() {
		return flgCompilaDatiPubblicazione;
	}
	public void setFlgCompilaDatiPubblicazione(String flgCompilaDatiPubblicazione) {
		this.flgCompilaDatiPubblicazione = flgCompilaDatiPubblicazione;
	}
	public String getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	public void setDataInizioPubblicazione(String dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	public String getGiorniPubblicazione() {
		return giorniPubblicazione;
	}
	public void setGiorniPubblicazione(String giorniPubblicazione) {
		this.giorniPubblicazione = giorniPubblicazione;
	}
	public Flag getUlterioriIntegrazioni() {
		return ulterioriIntegrazioni;
	}
	public void setUlterioriIntegrazioni(Flag ulterioriIntegrazioni) {
		this.ulterioriIntegrazioni = ulterioriIntegrazioni;
	}
	public String getRichIntegrNro() {
		return richIntegrNro;
	}
	public void setRichIntegrNro(String richIntegrNro) {
		this.richIntegrNro = richIntegrNro;
	}
	public String getDesEnteAppartenenza() {
		return desEnteAppartenenza;
	}
	public void setDesEnteAppartenenza(String desEnteAppartenenza) {
		this.desEnteAppartenenza = desEnteAppartenenza;
	}
	public List<FileDaUnireBean> getFileDaUnire() {
		return fileDaUnire;
	}
	public void setFileDaUnire(List<FileDaUnireBean> fileDaUnire) {
		this.fileDaUnire = fileDaUnire;
	}
	public String getNomeFileUnione() {
		return nomeFileUnione;
	}
	public void setNomeFileUnione(String nomeFileUnione) {
		this.nomeFileUnione = nomeFileUnione;
	}
	public List<FileDaUnireBean> getFileDaUnireVersIntegrale() {
		return fileDaUnireVersIntegrale;
	}
	public void setFileDaUnireVersIntegrale(List<FileDaUnireBean> fileDaUnireVersIntegrale) {
		this.fileDaUnireVersIntegrale = fileDaUnireVersIntegrale;
	}
	public String getNomeFileUnioneVersIntegrale() {
		return nomeFileUnioneVersIntegrale;
	}
	public void setNomeFileUnioneVersIntegrale(String nomeFileUnioneVersIntegrale) {
		this.nomeFileUnioneVersIntegrale = nomeFileUnioneVersIntegrale;
	}
	public String getTipoDocFileUnioneVersIntegrale() {
		return tipoDocFileUnioneVersIntegrale;
	}
	public void setTipoDocFileUnioneVersIntegrale(String tipoDocFileUnioneVersIntegrale) {
		this.tipoDocFileUnioneVersIntegrale = tipoDocFileUnioneVersIntegrale;
	}
	public String getSiglaRegistroAtto() {
		return siglaRegistroAtto;
	}
	public void setSiglaRegistroAtto(String siglaRegistroAtto) {
		this.siglaRegistroAtto = siglaRegistroAtto;
	}
	public String getSiglaRegistroAtto2() {
		return siglaRegistroAtto2;
	}
	public void setSiglaRegistroAtto2(String siglaRegistroAtto2) {
		this.siglaRegistroAtto2 = siglaRegistroAtto2;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getNomeAttrCustomEsitoTask() {
		return nomeAttrCustomEsitoTask;
	}
	public void setNomeAttrCustomEsitoTask(String nomeAttrCustomEsitoTask) {
		this.nomeAttrCustomEsitoTask = nomeAttrCustomEsitoTask;
	}
	public Flag getFlgReadOnly() {
		return flgReadOnly;
	}
	public void setFlgReadOnly(Flag flgReadOnly) {
		this.flgReadOnly = flgReadOnly;
	}
	public String getIdModCopertina() {
		return idModCopertina;
	}
	public void setIdModCopertina(String idModCopertina) {
		this.idModCopertina = idModCopertina;
	}
	public String getNomeModCopertina() {
		return nomeModCopertina;
	}
	public void setNomeModCopertina(String nomeModCopertina) {
		this.nomeModCopertina = nomeModCopertina;
	}
	public String getUriModCopertina() {
		return uriModCopertina;
	}
	public void setUriModCopertina(String uriModCopertina) {
		this.uriModCopertina = uriModCopertina;
	}
	public String getTipoModCopertina() {
		return tipoModCopertina;
	}
	public void setTipoModCopertina(String tipoModCopertina) {
		this.tipoModCopertina = tipoModCopertina;
	}
	public String getIdModCopertinaFinale() {
		return idModCopertinaFinale;
	}
	public void setIdModCopertinaFinale(String idModCopertinaFinale) {
		this.idModCopertinaFinale = idModCopertinaFinale;
	}
	public String getNomeModCopertinaFinale() {
		return nomeModCopertinaFinale;
	}
	public void setNomeModCopertinaFinale(String nomeModCopertinaFinale) {
		this.nomeModCopertinaFinale = nomeModCopertinaFinale;
	}
	public String getUriModCopertinaFinale() {
		return uriModCopertinaFinale;
	}
	public void setUriModCopertinaFinale(String uriModCopertinaFinale) {
		this.uriModCopertinaFinale = uriModCopertinaFinale;
	}
	public String getTipoModCopertinaFinale() {
		return tipoModCopertinaFinale;
	}
	public void setTipoModCopertinaFinale(String tipoModCopertinaFinale) {
		this.tipoModCopertinaFinale = tipoModCopertinaFinale;
	}
	public String getIdModAllegatiParteIntSeparati() {
		return idModAllegatiParteIntSeparati;
	}
	public void setIdModAllegatiParteIntSeparati(String idModAllegatiParteIntSeparati) {
		this.idModAllegatiParteIntSeparati = idModAllegatiParteIntSeparati;
	}
	public String getNomeModAllegatiParteIntSeparati() {
		return nomeModAllegatiParteIntSeparati;
	}
	public void setNomeModAllegatiParteIntSeparati(String nomeModAllegatiParteIntSeparati) {
		this.nomeModAllegatiParteIntSeparati = nomeModAllegatiParteIntSeparati;
	}
	public String getUriModAllegatiParteIntSeparati() {
		return uriModAllegatiParteIntSeparati;
	}
	public void setUriModAllegatiParteIntSeparati(String uriModAllegatiParteIntSeparati) {
		this.uriModAllegatiParteIntSeparati = uriModAllegatiParteIntSeparati;
	}
	public String getTipoModAllegatiParteIntSeparati() {
		return tipoModAllegatiParteIntSeparati;
	}
	public void setTipoModAllegatiParteIntSeparati(String tipoModAllegatiParteIntSeparati) {
		this.tipoModAllegatiParteIntSeparati = tipoModAllegatiParteIntSeparati;
	}
	public String getIdModAllegatiParteIntSeparatiXPubbl() {
		return idModAllegatiParteIntSeparatiXPubbl;
	}
	public void setIdModAllegatiParteIntSeparatiXPubbl(String idModAllegatiParteIntSeparatiXPubbl) {
		this.idModAllegatiParteIntSeparatiXPubbl = idModAllegatiParteIntSeparatiXPubbl;
	}
	public String getNomeModAllegatiParteIntSeparatiXPubbl() {
		return nomeModAllegatiParteIntSeparatiXPubbl;
	}
	public void setNomeModAllegatiParteIntSeparatiXPubbl(String nomeModAllegatiParteIntSeparatiXPubbl) {
		this.nomeModAllegatiParteIntSeparatiXPubbl = nomeModAllegatiParteIntSeparatiXPubbl;
	}
	public String getUriModAllegatiParteIntSeparatiXPubbl() {
		return uriModAllegatiParteIntSeparatiXPubbl;
	}
	public void setUriModAllegatiParteIntSeparatiXPubbl(String uriModAllegatiParteIntSeparatiXPubbl) {
		this.uriModAllegatiParteIntSeparatiXPubbl = uriModAllegatiParteIntSeparatiXPubbl;
	}
	public String getTipoModAllegatiParteIntSeparatiXPubbl() {
		return tipoModAllegatiParteIntSeparatiXPubbl;
	}
	public void setTipoModAllegatiParteIntSeparatiXPubbl(String tipoModAllegatiParteIntSeparatiXPubbl) {
		this.tipoModAllegatiParteIntSeparatiXPubbl = tipoModAllegatiParteIntSeparatiXPubbl;
	}
	public Flag getFlgAppendiceDaUnire() {
		return flgAppendiceDaUnire;
	}
	public void setFlgAppendiceDaUnire(Flag flgAppendiceDaUnire) {
		this.flgAppendiceDaUnire = flgAppendiceDaUnire;
	}
	public String getIdModAppendice() {
		return idModAppendice;
	}
	public void setIdModAppendice(String idModAppendice) {
		this.idModAppendice = idModAppendice;
	}
	public String getNomeModAppendice() {
		return nomeModAppendice;
	}
	public void setNomeModAppendice(String nomeModAppendice) {
		this.nomeModAppendice = nomeModAppendice;
	}
	public String getUriModAppendice() {
		return uriModAppendice;
	}
	public void setUriModAppendice(String uriModAppendice) {
		this.uriModAppendice = uriModAppendice;
	}
	public String getTipoModAppendice() {
		return tipoModAppendice;
	}
	public void setTipoModAppendice(String tipoModAppendice) {
		this.tipoModAppendice = tipoModAppendice;
	}
	public String getUriAppendice() {
		return uriAppendice;
	}
	public void setUriAppendice(String uriAppendice) {
		this.uriAppendice = uriAppendice;
	}
	public String getDisplayFilenameModAppendice() {
		return displayFilenameModAppendice;
	}
	public void setDisplayFilenameModAppendice(String displayFilenameModAppendice) {
		this.displayFilenameModAppendice = displayFilenameModAppendice;
	}
	public String getIdModFoglioFirme() {
		return idModFoglioFirme;
	}
	public void setIdModFoglioFirme(String idModFoglioFirme) {
		this.idModFoglioFirme = idModFoglioFirme;
	}
	public String getNomeModFoglioFirme() {
		return nomeModFoglioFirme;
	}
	public void setNomeModFoglioFirme(String nomeModFoglioFirme) {
		this.nomeModFoglioFirme = nomeModFoglioFirme;
	}
	public String getDisplayFilenameModFoglioFirme() {
		return displayFilenameModFoglioFirme;
	}
	public void setDisplayFilenameModFoglioFirme(String displayFilenameModFoglioFirme) {
		this.displayFilenameModFoglioFirme = displayFilenameModFoglioFirme;
	}
	public String getIdModFoglioFirme2() {
		return idModFoglioFirme2;
	}
	public void setIdModFoglioFirme2(String idModFoglioFirme2) {
		this.idModFoglioFirme2 = idModFoglioFirme2;
	}
	public String getNomeModFoglioFirme2() {
		return nomeModFoglioFirme2;
	}
	public void setNomeModFoglioFirme2(String nomeModFoglioFirme2) {
		this.nomeModFoglioFirme2 = nomeModFoglioFirme2;
	}
	public String getDisplayFilenameModFoglioFirme2() {
		return displayFilenameModFoglioFirme2;
	}
	public void setDisplayFilenameModFoglioFirme2(String displayFilenameModFoglioFirme2) {
		this.displayFilenameModFoglioFirme2 = displayFilenameModFoglioFirme2;
	}
	public String getIdModSchedaTrasp() {
		return idModSchedaTrasp;
	}
	public void setIdModSchedaTrasp(String idModSchedaTrasp) {
		this.idModSchedaTrasp = idModSchedaTrasp;
	}
	public String getNomeModSchedaTrasp() {
		return nomeModSchedaTrasp;
	}
	public void setNomeModSchedaTrasp(String nomeModSchedaTrasp) {
		this.nomeModSchedaTrasp = nomeModSchedaTrasp;
	}
	public String getDisplayFilenameModSchedaTrasp() {
		return displayFilenameModSchedaTrasp;
	}
	public void setDisplayFilenameModSchedaTrasp(String displayFilenameModSchedaTrasp) {
		this.displayFilenameModSchedaTrasp = displayFilenameModSchedaTrasp;
	}
	public List<AttributiAddDocTabBean> getAttributiAddDocTabs() {
		return attributiAddDocTabs;
	}
	public void setAttributiAddDocTabs(List<AttributiAddDocTabBean> attributiAddDocTabs) {
		this.attributiAddDocTabs = attributiAddDocTabs;
	}
	public List<EsitoTaskOkBean> getEsitiTaskOk() {
		return esitiTaskOk;
	}
	public void setEsitiTaskOk(List<EsitoTaskOkBean> esitiTaskOk) {
		this.esitiTaskOk = esitiTaskOk;
	}
	public List<EsitoTaskOkBean> getEsitiTaskKo() {
		return esitiTaskKo;
	}
	public void setEsitiTaskKo(List<EsitoTaskOkBean> esitiTaskKo) {
		this.esitiTaskKo = esitiTaskKo;
	}
	public Flag getFileUnioneDaFirmare() {
		return fileUnioneDaFirmare;
	}
	public void setFileUnioneDaFirmare(Flag fileUnioneDaFirmare) {
		this.fileUnioneDaFirmare = fileUnioneDaFirmare;
	}
	public Flag getFileUnioneDaTimbrare() {
		return fileUnioneDaTimbrare;
	}
	public void setFileUnioneDaTimbrare(Flag fileUnioneDaTimbrare) {
		this.fileUnioneDaTimbrare = fileUnioneDaTimbrare;
	}
	public Flag getUnioneFileInfoAllegatoAttiva() {
		return unioneFileInfoAllegatoAttiva;
	}
	public void setUnioneFileInfoAllegatoAttiva(Flag unioneFileInfoAllegatoAttiva) {
		this.unioneFileInfoAllegatoAttiva = unioneFileInfoAllegatoAttiva;
	}
	public String getUnioneFileInfoAllegatoPagine() {
		return unioneFileInfoAllegatoPagine;
	}
	public void setUnioneFileInfoAllegatoPagine(String unioneFileInfoAllegatoPagine) {
		this.unioneFileInfoAllegatoPagine = unioneFileInfoAllegatoPagine;
	}
	public String getUnioneFileInfoAllegatoFormato() {
		return unioneFileInfoAllegatoFormato;
	}
	public void setUnioneFileInfoAllegatoFormato(String unioneFileInfoAllegatoFormato) {
		this.unioneFileInfoAllegatoFormato = unioneFileInfoAllegatoFormato;
	}
	public String getUnioneFileInfoAllegatoMargine() {
		return unioneFileInfoAllegatoMargine;
	}
	public void setUnioneFileInfoAllegatoMargine(String unioneFileInfoAllegatoMargine) {
		this.unioneFileInfoAllegatoMargine = unioneFileInfoAllegatoMargine;
	}
	public String getUnioneFileInfoAllegatoOrientamento() {
		return unioneFileInfoAllegatoOrientamento;
	}
	public void setUnioneFileInfoAllegatoOrientamento(String unioneFileInfoAllegatoOrientamento) {
		this.unioneFileInfoAllegatoOrientamento = unioneFileInfoAllegatoOrientamento;
	}
	public String getUnioneFileInfoAllegatoFont() {
		return unioneFileInfoAllegatoFont;
	}
	public void setUnioneFileInfoAllegatoFont(String unioneFileInfoAllegatoFont) {
		this.unioneFileInfoAllegatoFont = unioneFileInfoAllegatoFont;
	}
	public String getUnioneFileInfoAllegatoFontSize() {
		return unioneFileInfoAllegatoFontSize;
	}
	public void setUnioneFileInfoAllegatoFontSize(String unioneFileInfoAllegatoFontSize) {
		this.unioneFileInfoAllegatoFontSize = unioneFileInfoAllegatoFontSize;
	}
	public String getUnioneFileInfoAllegatoTextColor() {
		return unioneFileInfoAllegatoTextColor;
	}
	public void setUnioneFileInfoAllegatoTextColor(String unioneFileInfoAllegatoTextColor) {
		this.unioneFileInfoAllegatoTextColor = unioneFileInfoAllegatoTextColor;
	}
	public String getUnioneFileInfoAllegatoStyle() {
		return unioneFileInfoAllegatoStyle;
	}
	public void setUnioneFileInfoAllegatoStyle(String unioneFileInfoAllegatoStyle) {
		this.unioneFileInfoAllegatoStyle = unioneFileInfoAllegatoStyle;
	}
	public String getUnioneFileInfoAllegatoMaxLenNomeFileAllegato() {
		return unioneFileInfoAllegatoMaxLenNomeFileAllegato;
	}
	public void setUnioneFileInfoAllegatoMaxLenNomeFileAllegato(String unioneFileInfoAllegatoMaxLenNomeFileAllegato) {
		this.unioneFileInfoAllegatoMaxLenNomeFileAllegato = unioneFileInfoAllegatoMaxLenNomeFileAllegato;
	}
	public Flag getUnioneFileNroPaginaAttiva() {
		return unioneFileNroPaginaAttiva;
	}
	public void setUnioneFileNroPaginaAttiva(Flag unioneFileNroPaginaAttiva) {
		this.unioneFileNroPaginaAttiva = unioneFileNroPaginaAttiva;
	}
	public String getUnioneFileNroPaginaFormato() {
		return unioneFileNroPaginaFormato;
	}
	public void setUnioneFileNroPaginaFormato(String unioneFileNroPaginaFormato) {
		this.unioneFileNroPaginaFormato = unioneFileNroPaginaFormato;
	}
	public String getUnioneFileNroPaginaPosizione() {
		return unioneFileNroPaginaPosizione;
	}
	public void setUnioneFileNroPaginaPosizione(String unioneFileNroPaginaPosizione) {
		this.unioneFileNroPaginaPosizione = unioneFileNroPaginaPosizione;
	}
	public Flag getUnioneFileNroPaginaEscludiAllegatiMaggioriA4() {
		return unioneFileNroPaginaEscludiAllegatiMaggioriA4;
	}
	public void setUnioneFileNroPaginaEscludiAllegatiMaggioriA4(Flag unioneFileNroPaginaEscludiAllegatiMaggioriA4) {
		this.unioneFileNroPaginaEscludiAllegatiMaggioriA4 = unioneFileNroPaginaEscludiAllegatiMaggioriA4;
	}
	public Flag getUnioneFileNroPaginaNumerazioneDistintaXFileUniti() {
		return unioneFileNroPaginaNumerazioneDistintaXFileUniti;
	}
	public void setUnioneFileNroPaginaNumerazioneDistintaXFileUniti(Flag unioneFileNroPaginaNumerazioneDistintaXFileUniti) {
		this.unioneFileNroPaginaNumerazioneDistintaXFileUniti = unioneFileNroPaginaNumerazioneDistintaXFileUniti;
	}
	public String getUnioneFileNroPaginaFont() {
		return unioneFileNroPaginaFont;
	}
	public void setUnioneFileNroPaginaFont(String unioneFileNroPaginaFont) {
		this.unioneFileNroPaginaFont = unioneFileNroPaginaFont;
	}
	public String getUnioneFileNroPaginaFontSize() {
		return unioneFileNroPaginaFontSize;
	}
	public void setUnioneFileNroPaginaFontSize(String unioneFileNroPaginaFontSize) {
		this.unioneFileNroPaginaFontSize = unioneFileNroPaginaFontSize;
	}
	public String getUnioneFileNroPaginaTextColor() {
		return unioneFileNroPaginaTextColor;
	}
	public void setUnioneFileNroPaginaTextColor(String unioneFileNroPaginaTextColor) {
		this.unioneFileNroPaginaTextColor = unioneFileNroPaginaTextColor;
	}
	public String getUnioneFileNroPaginaStyle() {
		return unioneFileNroPaginaStyle;
	}
	public void setUnioneFileNroPaginaStyle(String unioneFileNroPaginaStyle) {
		this.unioneFileNroPaginaStyle = unioneFileNroPaginaStyle;
	}	
	public Flag getUnioneFileNroPaginaEscludiAllegatiUnitiInMezzo() {
		return unioneFileNroPaginaEscludiAllegatiUnitiInMezzo;
	}
	public void setUnioneFileNroPaginaEscludiAllegatiUnitiInMezzo(Flag unioneFileNroPaginaEscludiAllegatiUnitiInMezzo) {
		this.unioneFileNroPaginaEscludiAllegatiUnitiInMezzo = unioneFileNroPaginaEscludiAllegatiUnitiInMezzo;
	}
	public Flag getUnioneFileNroPaginaEscludiAllegatiUnitiInFondo() {
		return unioneFileNroPaginaEscludiAllegatiUnitiInFondo;
	}
	public void setUnioneFileNroPaginaEscludiAllegatiUnitiInFondo(Flag unioneFileNroPaginaEscludiAllegatiUnitiInFondo) {
		this.unioneFileNroPaginaEscludiAllegatiUnitiInFondo = unioneFileNroPaginaEscludiAllegatiUnitiInFondo;
	}
	public Flag getUnioneFileNroPaginaEscludiFoglioFirmeGrafiche() {
		return unioneFileNroPaginaEscludiFoglioFirmeGrafiche;
	}
	public void setUnioneFileNroPaginaEscludiFoglioFirmeGrafiche(Flag unioneFileNroPaginaEscludiFoglioFirmeGrafiche) {
		this.unioneFileNroPaginaEscludiFoglioFirmeGrafiche = unioneFileNroPaginaEscludiFoglioFirmeGrafiche;
	}
	public Flag getUnioneFileNroPaginaEscludiListaAllegatiSeparati() {
		return unioneFileNroPaginaEscludiListaAllegatiSeparati;
	}
	public void setUnioneFileNroPaginaEscludiListaAllegatiSeparati(Flag unioneFileNroPaginaEscludiListaAllegatiSeparati) {
		this.unioneFileNroPaginaEscludiListaAllegatiSeparati = unioneFileNroPaginaEscludiListaAllegatiSeparati;
	}
	public String getTimbroPosizione() {
		return timbroPosizione;
	}
	public void setTimbroPosizione(String timbroPosizione) {
		this.timbroPosizione = timbroPosizione;
	}
	public String getTimbroRotazione() {
		return timbroRotazione;
	}
	public void setTimbroRotazione(String timbroRotazione) {
		this.timbroRotazione = timbroRotazione;
	}
	public String getTimbroSelezionePagine() {
		return timbroSelezionePagine;
	}
	public void setTimbroSelezionePagine(String timbroSelezionePagine) {
		this.timbroSelezionePagine = timbroSelezionePagine;
	}
	public String getTimbroPaginaDa() {
		return timbroPaginaDa;
	}
	public void setTimbroPaginaDa(String timbroPaginaDa) {
		this.timbroPaginaDa = timbroPaginaDa;
	}
	public String getTimbroPaginaA() {
		return timbroPaginaA;
	}
	public void setTimbroPaginaA(String timbroPaginaA) {
		this.timbroPaginaA = timbroPaginaA;
	}
	public String getCodNaturaRelVsUD() {
		return codNaturaRelVsUD;
	}
	public void setCodNaturaRelVsUD(String codNaturaRelVsUD) {
		this.codNaturaRelVsUD = codNaturaRelVsUD;
	}
	public String getCodTabDefault() {
		return codTabDefault;
	}
	public void setCodTabDefault(String codTabDefault) {
		this.codTabDefault = codTabDefault;
	}
	public String getIdTipoTaskDoc() {
		return idTipoTaskDoc;
	}
	public void setIdTipoTaskDoc(String idTipoTaskDoc) {
		this.idTipoTaskDoc = idTipoTaskDoc;
	}
	public String getNomeTipoTaskDoc() {
		return nomeTipoTaskDoc;
	}
	public void setNomeTipoTaskDoc(String nomeTipoTaskDoc) {
		this.nomeTipoTaskDoc = nomeTipoTaskDoc;
	}
	public Flag getFlgParereTaskDoc() {
		return flgParereTaskDoc;
	}
	public void setFlgParereTaskDoc(Flag flgParereTaskDoc) {
		this.flgParereTaskDoc = flgParereTaskDoc;
	}
	public Flag getFlgParteDispositivoTaskDoc() {
		return flgParteDispositivoTaskDoc;
	}
	public void setFlgParteDispositivoTaskDoc(Flag flgParteDispositivoTaskDoc) {
		this.flgParteDispositivoTaskDoc = flgParteDispositivoTaskDoc;
	}
	public Flag getFlgNoPubblTaskDoc() {
		return flgNoPubblTaskDoc;
	}
	public void setFlgNoPubblTaskDoc(Flag flgNoPubblTaskDoc) {
		this.flgNoPubblTaskDoc = flgNoPubblTaskDoc;
	}
	public Flag getFlgPubblicaSeparatoTaskDoc() {
		return flgPubblicaSeparatoTaskDoc;
	}
	public void setFlgPubblicaSeparatoTaskDoc(Flag flgPubblicaSeparatoTaskDoc) {
		this.flgPubblicaSeparatoTaskDoc = flgPubblicaSeparatoTaskDoc;
	}
	public List<ControlloXEsitoTaskDocBean> getControlliXEsitiTaskDoc() {
		return controlliXEsitiTaskDoc;
	}
	public void setControlliXEsitiTaskDoc(List<ControlloXEsitoTaskDocBean> controlliXEsitiTaskDoc) {
		this.controlliXEsitiTaskDoc = controlliXEsitiTaskDoc;
	}
	public String getAttivaModificaEsclusionePubblicazione() {
		return attivaModificaEsclusionePubblicazione;
	}
	public void setAttivaModificaEsclusionePubblicazione(String attivaModificaEsclusionePubblicazione) {
		this.attivaModificaEsclusionePubblicazione = attivaModificaEsclusionePubblicazione;
	}
	public String getAttivaEliminazioneUOCoinvolte() {
		return attivaEliminazioneUOCoinvolte;
	}
	public void setAttivaEliminazioneUOCoinvolte(String attivaEliminazioneUOCoinvolte) {
		this.attivaEliminazioneUOCoinvolte = attivaEliminazioneUOCoinvolte;
	}
	public String getSiglaRegistroNumFinale() {
		return siglaRegistroNumFinale;
	}
	public void setSiglaRegistroNumFinale(String siglaRegistroNumFinale) {
		this.siglaRegistroNumFinale = siglaRegistroNumFinale;
	}
	public Flag getTaskArchivio() {
		return taskArchivio;
	}
	public void setTaskArchivio(Flag taskArchivio) {
		this.taskArchivio = taskArchivio;
	}
	public Flag getFlgDatiSpesaEditabili() {
		return flgDatiSpesaEditabili;
	}
	public void setFlgDatiSpesaEditabili(Flag flgDatiSpesaEditabili) {
		this.flgDatiSpesaEditabili = flgDatiSpesaEditabili;
	}
	public Flag getFlgCIGEditabile() {
		return flgCIGEditabile;
	}
	public void setFlgCIGEditabile(Flag flgCIGEditabile) {
		this.flgCIGEditabile = flgCIGEditabile;
	}
	public String getTipoEventoSIB() {
		return tipoEventoSIB;
	}
	public void setTipoEventoSIB(String tipoEventoSIB) {
		this.tipoEventoSIB = tipoEventoSIB;
	}
	public List<EsitoTaskOkBean> getEsitiTaskEventoSIB() {
		return esitiTaskEventoSIB;
	}
	public void setEsitiTaskEventoSIB(List<EsitoTaskOkBean> esitiTaskEventoSIB) {
		this.esitiTaskEventoSIB = esitiTaskEventoSIB;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoSIBXEsitoTask() {
		return tipiEventoSIBXEsitoTask;
	}
	public void setTipiEventoSIBXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoSIBXEsitoTask) {
		this.tipiEventoSIBXEsitoTask = tipiEventoSIBXEsitoTask;
	}
	public String getIdUoDirAdottanteSIB() {
		return idUoDirAdottanteSIB;
	}
	public void setIdUoDirAdottanteSIB(String idUoDirAdottanteSIB) {
		this.idUoDirAdottanteSIB = idUoDirAdottanteSIB;
	}
	public String getCodUoDirAdottanteSIB() {
		return codUoDirAdottanteSIB;
	}
	public void setCodUoDirAdottanteSIB(String codUoDirAdottanteSIB) {
		this.codUoDirAdottanteSIB = codUoDirAdottanteSIB;
	}
	public String getDesUoDirAdottanteSIB() {
		return desUoDirAdottanteSIB;
	}
	public void setDesUoDirAdottanteSIB(String desUoDirAdottanteSIB) {
		this.desUoDirAdottanteSIB = desUoDirAdottanteSIB;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoContabiliaXEsitoTask() {
		return tipiEventoContabiliaXEsitoTask;
	}
	public void setTipiEventoContabiliaXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoContabiliaXEsitoTask) {
		this.tipiEventoContabiliaXEsitoTask = tipiEventoContabiliaXEsitoTask;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoSICRAXEsitoTask() {
		return tipiEventoSICRAXEsitoTask;
	}
	public void setTipiEventoSICRAXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoSICRAXEsitoTask) {
		this.tipiEventoSICRAXEsitoTask = tipiEventoSICRAXEsitoTask;
	}
	public List<EventoXEsitoTaskBean> getTipiEventoCWOLXEsitoTask() {
		return tipiEventoCWOLXEsitoTask;
	}
	public void setTipiEventoCWOLXEsitoTask(List<EventoXEsitoTaskBean> tipiEventoCWOLXEsitoTask) {
		this.tipiEventoCWOLXEsitoTask = tipiEventoCWOLXEsitoTask;
	}
	public String getFlgAttivaSmistamento() {
		return flgAttivaSmistamento;
	}
	public void setFlgAttivaSmistamento(String flgAttivaSmistamento) {
		this.flgAttivaSmistamento = flgAttivaSmistamento;
	}
	public String getFlgRecuperaMovimentiContabDefinitivi() {
		return flgRecuperaMovimentiContabDefinitivi;
	}
	public void setFlgRecuperaMovimentiContabDefinitivi(String flgRecuperaMovimentiContabDefinitivi) {
		this.flgRecuperaMovimentiContabDefinitivi = flgRecuperaMovimentiContabDefinitivi;
	}
	public String getNomeAttrListaDatiContabili() {
		return nomeAttrListaDatiContabili;
	}
	public void setNomeAttrListaDatiContabili(String nomeAttrListaDatiContabili) {
		this.nomeAttrListaDatiContabili = nomeAttrListaDatiContabili;
	}
	public String getNomeAttrListaDatiContabiliRichiesti() {
		return nomeAttrListaDatiContabiliRichiesti;
	}
	public void setNomeAttrListaDatiContabiliRichiesti(String nomeAttrListaDatiContabiliRichiesti) {
		this.nomeAttrListaDatiContabiliRichiesti = nomeAttrListaDatiContabiliRichiesti;
	}
	public String getFlgAttivaRequestMovimentiDaAMC() {
		return flgAttivaRequestMovimentiDaAMC;
	}
	public void setFlgAttivaRequestMovimentiDaAMC(String flgAttivaRequestMovimentiDaAMC) {
		this.flgAttivaRequestMovimentiDaAMC = flgAttivaRequestMovimentiDaAMC;
	}
	public String getFlgAttivaSalvataggioMovimentiDaAMC() {
		return flgAttivaSalvataggioMovimentiDaAMC;
	}
	public void setFlgAttivaSalvataggioMovimentiDaAMC(String flgAttivaSalvataggioMovimentiDaAMC) {
		this.flgAttivaSalvataggioMovimentiDaAMC = flgAttivaSalvataggioMovimentiDaAMC;
	}
	public String getFlgEscludiFiltroCdCVsAMC() {
		return flgEscludiFiltroCdCVsAMC;
	}
	public void setFlgEscludiFiltroCdCVsAMC(String flgEscludiFiltroCdCVsAMC) {
		this.flgEscludiFiltroCdCVsAMC = flgEscludiFiltroCdCVsAMC;
	}
	public String getLivelloMaxRevisione() {
		return livelloMaxRevisione;
	}
	public void setLivelloMaxRevisione(String livelloMaxRevisione) {
		this.livelloMaxRevisione = livelloMaxRevisione;
	}
	public String getTipiUORevisione() {
		return tipiUORevisione;
	}
	public void setTipiUORevisione(String tipiUORevisione) {
		this.tipiUORevisione = tipiUORevisione;
	}
	public String getNomeVersConfrontoOrganigramma() {
		return nomeVersConfrontoOrganigramma;
	}
	public void setNomeVersConfrontoOrganigramma(String nomeVersConfrontoOrganigramma) {
		this.nomeVersConfrontoOrganigramma = nomeVersConfrontoOrganigramma;
	}
	public String getNroVersConfrontoOrganigramma() {
		return nroVersConfrontoOrganigramma;
	}
	public void setNroVersConfrontoOrganigramma(String nroVersConfrontoOrganigramma) {
		this.nroVersConfrontoOrganigramma = nroVersConfrontoOrganigramma;
	}
	public String getNroVersLavoroOrganigramma() {
		return nroVersLavoroOrganigramma;
	}
	public void setNroVersLavoroOrganigramma(String nroVersLavoroOrganigramma) {
		this.nroVersLavoroOrganigramma = nroVersLavoroOrganigramma;
	}
	public String getAzioneOrganigramma() {
		return azioneOrganigramma;
	}
	public void setAzioneOrganigramma(String azioneOrganigramma) {
		this.azioneOrganigramma = azioneOrganigramma;
	}
	public String getIdDocOrganigramma() {
		return idDocOrganigramma;
	}
	public void setIdDocOrganigramma(String idDocOrganigramma) {
		this.idDocOrganigramma = idDocOrganigramma;
	}
	public String getIdDocArchiviazionePdfOrganigramma() {
		return idDocArchiviazionePdfOrganigramma;
	}
	public void setIdDocArchiviazionePdfOrganigramma(String idDocArchiviazionePdfOrganigramma) {
		this.idDocArchiviazionePdfOrganigramma = idDocArchiviazionePdfOrganigramma;
	}
	public String getLivelloRevisioneOrganigramma() {
		return livelloRevisioneOrganigramma;
	}
	public void setLivelloRevisioneOrganigramma(String livelloRevisioneOrganigramma) {
		this.livelloRevisioneOrganigramma = livelloRevisioneOrganigramma;
	}
	public Flag getFlgDelibera() {
		return flgDelibera;
	}
	public void setFlgDelibera(Flag flgDelibera) {
		this.flgDelibera = flgDelibera;
	}
	public String getShowDirigentiProponenti() {
		return showDirigentiProponenti;
	}
	public void setShowDirigentiProponenti(String showDirigentiProponenti) {
		this.showDirigentiProponenti = showDirigentiProponenti;
	}
	public String getShowAssessori() {
		return showAssessori;
	}
	public void setShowAssessori(String showAssessori) {
		this.showAssessori = showAssessori;
	}
	public String getShowConsiglieri() {
		return showConsiglieri;
	}
	public void setShowConsiglieri(String showConsiglieri) {
		this.showConsiglieri = showConsiglieri;
	}
	public List<AttributiCustomCablatiAttoXmlBean> getParametriTipoAttoAttributiCustomCablati() {
		return parametriTipoAttoAttributiCustomCablati;
	}
	public void setParametriTipoAttoAttributiCustomCablati(
			List<AttributiCustomCablatiAttoXmlBean> parametriTipoAttoAttributiCustomCablati) {
		this.parametriTipoAttoAttributiCustomCablati = parametriTipoAttoAttributiCustomCablati;
	}
	public Flag getParametriTipoAttoDefaultAllegParteIntegrante() {
		return parametriTipoAttoDefaultAllegParteIntegrante;
	}
	public void setParametriTipoAttoDefaultAllegParteIntegrante(Flag parametriTipoAttoDefaultAllegParteIntegrante) {
		this.parametriTipoAttoDefaultAllegParteIntegrante = parametriTipoAttoDefaultAllegParteIntegrante;
	}
	public Flag getParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente() {
		return parametriTipoAttoDefaultAllegParteIntegranteOrdPermanente;
	}
	public void setParametriTipoAttoDefaultAllegParteIntegranteOrdPermanente(
			Flag parametriTipoAttoDefaultAllegParteIntegranteOrdPermanente) {
		this.parametriTipoAttoDefaultAllegParteIntegranteOrdPermanente = parametriTipoAttoDefaultAllegParteIntegranteOrdPermanente;
	}
	public Flag getParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea() {
		return parametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea;
	}
	public void setParametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea(
			Flag parametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea) {
		this.parametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea = parametriTipoAttoDefaultAllegParteIntegranteOrdTemporanea;
	}
	public Flag getParametriTipoAttoDefaultAllegPubblSeparata() {
		return parametriTipoAttoDefaultAllegPubblSeparata;
	}
	public void setParametriTipoAttoDefaultAllegPubblSeparata(Flag parametriTipoAttoDefaultAllegPubblSeparata) {
		this.parametriTipoAttoDefaultAllegPubblSeparata = parametriTipoAttoDefaultAllegPubblSeparata;
	}
	public Flag getParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti() {
		return parametriTipoAttoAttivaSceltaPosizioneAllegatiUniti;
	}
	public void setParametriTipoAttoAttivaSceltaPosizioneAllegatiUniti(
			Flag parametriTipoAttoAttivaSceltaPosizioneAllegatiUniti) {
		this.parametriTipoAttoAttivaSceltaPosizioneAllegatiUniti = parametriTipoAttoAttivaSceltaPosizioneAllegatiUniti;
	}
	public String getFlgPubblicazioneAllegatiUguale() {
		return flgPubblicazioneAllegatiUguale;
	}
	public void setFlgPubblicazioneAllegatiUguale(String flgPubblicazioneAllegatiUguale) {
		this.flgPubblicazioneAllegatiUguale = flgPubblicazioneAllegatiUguale;
	}
	public String getFlgSoloPreparazioneVersPubblicazione() {
		return flgSoloPreparazioneVersPubblicazione;
	}
	public void setFlgSoloPreparazioneVersPubblicazione(String flgSoloPreparazioneVersPubblicazione) {
		this.flgSoloPreparazioneVersPubblicazione = flgSoloPreparazioneVersPubblicazione;
	}
	public String getFlgCtrlMimeTypeAllegPI() {
		return flgCtrlMimeTypeAllegPI;
	}
	public void setFlgCtrlMimeTypeAllegPI(String flgCtrlMimeTypeAllegPI) {
		this.flgCtrlMimeTypeAllegPI = flgCtrlMimeTypeAllegPI;
	}
	public String getFlgProtocollazioneProsa() {
		return flgProtocollazioneProsa;
	}
	public void setFlgProtocollazioneProsa(String flgProtocollazioneProsa) {
		this.flgProtocollazioneProsa = flgProtocollazioneProsa;
	}
	public Flag getFlgPresentiEmendamenti() {
		return flgPresentiEmendamenti;
	}
	public void setFlgPresentiEmendamenti(Flag flgPresentiEmendamenti) {
		this.flgPresentiEmendamenti = flgPresentiEmendamenti;
	}
	public Flag getFlgFirmaVersPubblAggiornata() {
		return flgFirmaVersPubblAggiornata;
	}
	public void setFlgFirmaVersPubblAggiornata(Flag flgFirmaVersPubblAggiornata) {
		this.flgFirmaVersPubblAggiornata = flgFirmaVersPubblAggiornata;
	}
	public String getAbilAggiornaStatoAttoPostDiscussione() {
		return abilAggiornaStatoAttoPostDiscussione;
	}
	public void setAbilAggiornaStatoAttoPostDiscussione(String abilAggiornaStatoAttoPostDiscussione) {
		this.abilAggiornaStatoAttoPostDiscussione = abilAggiornaStatoAttoPostDiscussione;
	}
	public List<StatoDocBean> getStatiXAggAttoPostDiscussione() {
		return statiXAggAttoPostDiscussione;
	}
	public void setStatiXAggAttoPostDiscussione(List<StatoDocBean> statiXAggAttoPostDiscussione) {
		this.statiXAggAttoPostDiscussione = statiXAggAttoPostDiscussione;
	}
	public String getExportAttoInDocFmt() {
		return exportAttoInDocFmt;
	}
	public void setExportAttoInDocFmt(String exportAttoInDocFmt) {
		this.exportAttoInDocFmt = exportAttoInDocFmt;
	}
	public Flag getFlgAvvioLiquidazioneContabilia() {
		return flgAvvioLiquidazioneContabilia;
	}
	public void setFlgAvvioLiquidazioneContabilia(Flag flgAvvioLiquidazioneContabilia) {
		this.flgAvvioLiquidazioneContabilia = flgAvvioLiquidazioneContabilia;
	}
	public String getFlgNumPropostaDiffXStruttura() {
		return flgNumPropostaDiffXStruttura;
	}
	public void setFlgNumPropostaDiffXStruttura(String flgNumPropostaDiffXStruttura) {
		this.flgNumPropostaDiffXStruttura = flgNumPropostaDiffXStruttura;
	}
	public List<WarningMsgXEsitoTaskBean> getWarningMsgXEsitoTask() {
		return warningMsgXEsitoTask;
	}
	public void setWarningMsgXEsitoTask(List<WarningMsgXEsitoTaskBean> warningMsgXEsitoTask) {
		this.warningMsgXEsitoTask = warningMsgXEsitoTask;
	}
	public String getEsitoTaskDaPreimpostare() {
		return esitoTaskDaPreimpostare;
	}
	public void setEsitoTaskDaPreimpostare(String esitoTaskDaPreimpostare) {
		this.esitoTaskDaPreimpostare = esitoTaskDaPreimpostare;
	}
	public String getMsgTaskDaPreimpostare() {
		return msgTaskDaPreimpostare;
	}
	public void setMsgTaskDaPreimpostare(String msgTaskDaPreimpostare) {
		this.msgTaskDaPreimpostare = msgTaskDaPreimpostare;
	}
	public String getMsgTaskProvvisorio() {
		return msgTaskProvvisorio;
	}
	public void setMsgTaskProvvisorio(String msgTaskProvvisorio) {
		this.msgTaskProvvisorio = msgTaskProvvisorio;
	}	
	public Flag getFlgAbilToSelProponentiEstesi() {
		return flgAbilToSelProponentiEstesi;
	}
	public void setFlgAbilToSelProponentiEstesi(Flag flgAbilToSelProponentiEstesi) {
		this.flgAbilToSelProponentiEstesi = flgAbilToSelProponentiEstesi;
	}
	public String getFlgAttivaUploadPdfAtto() {
		return flgAttivaUploadPdfAtto;
	}
	public void setFlgAttivaUploadPdfAtto(String flgAttivaUploadPdfAtto) {
		this.flgAttivaUploadPdfAtto = flgAttivaUploadPdfAtto;
	}
	public String getFlgAttivaUploadPdfAttoOmissis() {
		return flgAttivaUploadPdfAttoOmissis;
	}
	public void setFlgAttivaUploadPdfAttoOmissis(String flgAttivaUploadPdfAttoOmissis) {
		this.flgAttivaUploadPdfAttoOmissis = flgAttivaUploadPdfAttoOmissis;
	}
	public String getFlgSoloOmissisModProprietaFile() {
		return flgSoloOmissisModProprietaFile;
	}
	public void setFlgSoloOmissisModProprietaFile(String flgSoloOmissisModProprietaFile) {
		this.flgSoloOmissisModProprietaFile = flgSoloOmissisModProprietaFile;
	}
	public String getFlgDocActionsFirmaAutomatica() {
		return flgDocActionsFirmaAutomatica;
	}
	public void setFlgDocActionsFirmaAutomatica(String flgDocActionsFirmaAutomatica) {
		this.flgDocActionsFirmaAutomatica = flgDocActionsFirmaAutomatica;
	}
	public String getDocActionsFirmaAutomaticaProvider() {
		return docActionsFirmaAutomaticaProvider;
	}
	public void setDocActionsFirmaAutomaticaProvider(String docActionsFirmaAutomaticaProvider) {
		this.docActionsFirmaAutomaticaProvider = docActionsFirmaAutomaticaProvider;
	}
	public String getDocActionsFirmaAutomaticaUseridFirmatario() {
		return docActionsFirmaAutomaticaUseridFirmatario;
	}
	public void setDocActionsFirmaAutomaticaUseridFirmatario(String docActionsFirmaAutomaticaUseridFirmatario) {
		this.docActionsFirmaAutomaticaUseridFirmatario = docActionsFirmaAutomaticaUseridFirmatario;
	}
	public String getDocActionsFirmaAutomaticaFirmaInDelega() {
		return docActionsFirmaAutomaticaFirmaInDelega;
	}
	public void setDocActionsFirmaAutomaticaFirmaInDelega(String docActionsFirmaAutomaticaFirmaInDelega) {
		this.docActionsFirmaAutomaticaFirmaInDelega = docActionsFirmaAutomaticaFirmaInDelega;
	}
	public String getDocActionsFirmaAutomaticaPassword() {
		return docActionsFirmaAutomaticaPassword;
	}
	public void setDocActionsFirmaAutomaticaPassword(String docActionsFirmaAutomaticaPassword) {
		this.docActionsFirmaAutomaticaPassword = docActionsFirmaAutomaticaPassword;
	}
	public List<InfoFirmaGraficaBean> getInfoFirmaGrafica() {
		return infoFirmaGrafica;
	}
	public void setInfoFirmaGrafica(List<InfoFirmaGraficaBean> infoFirmaGrafica) {
		this.infoFirmaGrafica = infoFirmaGrafica;
	}
	public String getIdModelloDocFirmeGrafiche() {
		return idModelloDocFirmeGrafiche;
	}
	public void setIdModelloDocFirmeGrafiche(String idModelloDocFirmeGrafiche) {
		this.idModelloDocFirmeGrafiche = idModelloDocFirmeGrafiche;
	}
	public String getTipoModelloDocFirmeGrafiche() {
		return tipoModelloDocFirmeGrafiche;
	}
	public void setTipoModelloDocFirmeGrafiche(String tipoModelloDocFirmeGrafiche) {
		this.tipoModelloDocFirmeGrafiche = tipoModelloDocFirmeGrafiche;
	}
	public String getNomeModelloDocFirmeGrafiche() {
		return nomeModelloDocFirmeGrafiche;
	}
	public void setNomeModelloDocFirmeGrafiche(String nomeModelloDocFirmeGrafiche) {
		this.nomeModelloDocFirmeGrafiche = nomeModelloDocFirmeGrafiche;
	}
	public String getIdDocModelloFirmeGrafiche() {
		return idDocModelloFirmeGrafiche;
	}	
	public void setIdDocModelloFirmeGrafiche(String idDocModelloFirmeGrafiche) {
		this.idDocModelloFirmeGrafiche = idDocModelloFirmeGrafiche;
	}
	public String getNroPagineFirmeGrafiche() {
		return nroPagineFirmeGrafiche;
	}
	public void setNroPagineFirmeGrafiche(String nroPagineFirmeGrafiche) {
		this.nroPagineFirmeGrafiche = nroPagineFirmeGrafiche;
	}
	public Flag getUnioneFileNroPaginaEscludiAppendice() {
		return unioneFileNroPaginaEscludiAppendice;
	}
	public void setUnioneFileNroPaginaEscludiAppendice(Flag unioneFileNroPaginaEscludiAppendice) {
		this.unioneFileNroPaginaEscludiAppendice = unioneFileNroPaginaEscludiAppendice;
	}
	
}