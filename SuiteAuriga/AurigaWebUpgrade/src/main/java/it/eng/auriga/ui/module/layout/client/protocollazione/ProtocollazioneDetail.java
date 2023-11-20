/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.TimeZoneInfo;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.DocumentDetail;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.PortScannerUtility;
import it.eng.auriga.ui.module.layout.client.PortScannerUtility.PortScannerCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupTopograficoPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SalvaTopograficoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioLayout;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioList;
import it.eng.auriga.ui.module.layout.client.archivio.AssegnazionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.AzioneApposizionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.ClassificazioneFascicolazionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.CondivisionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.InviiEffettuatiWindow;
import it.eng.auriga.ui.module.layout.client.archivio.OrganizzaPopup;
import it.eng.auriga.ui.module.layout.client.archivio.RestituzionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.RichAnnullamentoRegPopup;
import it.eng.auriga.ui.module.layout.client.archivio.SegnaComeArchiviatoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.SegnaComeVisionatoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.SmistamentoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.TipologiaApposizione;
import it.eng.auriga.ui.module.layout.client.attiAutorizzazione.DettaglioAttoAutorizzazioneAnnRegWindow;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.DocumentItem;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.auriga.ui.module.layout.client.istanze.IstanzeWindow;
import it.eng.auriga.ui.module.layout.client.oggettario.LookupOggettarioPopup;
import it.eng.auriga.ui.module.layout.client.oggettario.SalvaModelloPopup;
import it.eng.auriga.ui.module.layout.client.osservazioniNotifiche.OsservazioniNotificheWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRispostaProtWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.postainarrivo.PostaInArrivoRegistrazioneWindow;
import it.eng.auriga.ui.module.layout.client.postainuscita.PostaInUscitaRegistrazioneWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskDettUdGenDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.TaskNuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.AltreVieItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.EsibentiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.InteressatiItem;
import it.eng.auriga.ui.module.layout.client.pubblicazioneAlbo.DettaglioRichiestaPubblicazioneWindow;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaEtichettaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroBean;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroCopertinaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.auriga.ui.module.layout.shared.invioRaccomandate.ETypePoste;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList.DettaglioWindow;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.FrecciaDetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.client.portal.Portlet;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

/**
 * Maschera di dettaglio di una UD
 * 
 * @author Mattia Zanin
 *
 */

public abstract class ProtocollazioneDetail extends DocumentDetail {

	protected ProtocollazioneDetail instance;
	
	protected DettaglioPostaElettronica dettaglioPostaElettronica;
	protected EditaProtocolloWindowFromMail editaProtocolloWindowFromMail;

	protected JSONEncoder encoder;

	// Tipologia documentale
	protected String tipoDocumento;

	// Rowid del documento
	protected String rowidDoc;

	// Modalità della maschera (new|view|edit)
	protected String mode;
	// Modalità in editing della maschera
	// (aggiuntaFile|modificaDatiReg|protocollaRegEmergenza|protocollazioneMail)
	protected String editMode;

	// Mappa dei valori originali caricati in maschera
	protected Map valuesOrig;

	// Indica se è la maschera di dettaglio del documento: se non lo è vengono
	// mostrati i bottoni
	// "Nuova protocollazione" e "Nuova protocollazione come copia"
//	protected boolean isDettaglioDoc = false;

	// Indica se è attiva la modalità di variazione dei dati di registrazione
	private boolean modificaDatiReg = false;

	// Motivi di variazione dei dati di registrazione
	private String motivoVariazione;

	// Mappa degli utenti che popola la combo "previa conferma di" nella sezione
	// "Assegnazione"
	protected LinkedHashMap<String, String> utentiAbilCPAValueMap;

	// Forza a mostrare la voce "Elimina" nel menu a tendina che si apre quando
	// clicchi il bottone "Altre operazioni" del file primario
	protected boolean forceToShowElimina = false;
	
	/*
	 * Creato questo flag come "semaforo" di accesso al pulsante
	 * di salvataggio
	 */
	private boolean abilSaveButton = true;
	
	
	/*************************
	 * LAYOUT DELLA MASCHERA *
	 *************************/

	protected VLayout mainLayout;

	protected ToolStrip mainToolStrip;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected SelectItem uoProtocollanteSelectItem;
	protected ListGridRecord recordModelloFocused;

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali; //se è attiva la modalità con i dati principali del documento e di assegnazione/classificazione in un unico tab (SHOW_SINGLE_TAB_HEADER_IN_PROT = true)
	protected Tab tabDatiDocumento;
	protected Tab tabAssegnazioneEClassificazione;
	protected Tab tabEsibentiEInteressati;
	protected Tab tabDelegatoEFirmatari;
	protected Tab tabAltreVie;
	protected Tab tabPubblicazione;
	
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton anteprimaModelloButton;
	protected DetailToolStripButton stampaEtichettaButton;
	protected FrecciaDetailToolStripButton frecciaStampaEtichettaButton;	
	protected DetailToolStripButton stampaCopertinaButton;
	protected DetailToolStripButton stampaMenuButton;
	protected DetailToolStripButton stampaRicevutaButton;
	protected DetailToolStripButton nuovaProtButton;
	protected DetailToolStripButton nuovaProtComeCopiaButton;
	protected DetailToolStripButton salvaRegistraButton;
	protected DetailToolStripButton presaInCaricoButton;
	protected DetailToolStripButton restituisciButton;
	protected DetailToolStripButton segnaComeVisionatoButton;
	protected DetailToolStripButton classificazioneFascicolazioneButton;
	protected DetailToolStripButton modificaButton;
	protected DetailToolStripButton regAccessoCivicoButton;
	protected FrecciaDetailToolStripButton frecciaModificaButton;
	protected DetailToolStripButton modificaDatiRegButton;
	protected DetailToolStripButton revocaAttoButton;
	protected DetailToolStripButton protocollazioneEntrataButton;
	protected DetailToolStripButton protocollazioneUscitaButton;
	protected DetailToolStripButton protocollazioneInternaButton;
	protected DetailToolStripButton invioPECButton;
	protected DetailToolStripButton invioMailRicevutaButton;
	protected DetailToolStripButton invioPEOButton;
	protected DetailToolStripButton invioRaccomandataButton;
	protected DetailToolStripButton invioPostaPrioritariaButton;
	protected DetailToolStripButton verificaRegistrazioneButton;
	protected DetailToolStripButton salvaComeModelloButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton downloadDocZipButton;
	protected FrecciaDetailToolStripButton frecciaDownloadZipButton;
	protected DetailToolStripButton smistaButton;
	protected DetailToolStripButton smistaCCButton;
	protected DetailToolStripButton invioAlProtocolloButton;
	protected FrecciaDetailToolStripButton frecciaInvioAlProtocolloButton;
	protected DetailToolStripButton assegnaCondividiButton;
	protected FrecciaDetailToolStripButton frecciaAssegnaCondividiButton;
//	protected DetailToolStripButton assegnazioneButton;
//	protected FrecciaDetailToolStripButton frecciaAssegnazioneButton;
	protected DetailToolStripButton rispondiButton;
//	protected DetailToolStripButton condivisioneButton;
//	protected FrecciaDetailToolStripButton frecciaCondivisioneButton;
	protected DetailToolStripButton osservazioniNotificheButton;
	protected DetailToolStripButton apposizioneFirmaButton;
	protected DetailToolStripButton apposizioneFirmaProtocollazioneButton;
	protected DetailToolStripButton rifiutoApposizioneFirmaButton;
	protected DetailToolStripButton apposizioneVistoButton;
	protected DetailToolStripButton rifiutoApposizioneVistoButton;
	protected DetailToolStripButton pubblicazioneTraspAmmButton;
	
	protected SaveModelloWindow saveModelloWindow;
	
	/**********************
	 * TAB DATI DOCUMENTO *
	 **********************/

	protected DynamicForm protocolloGeneraleForm;
	protected HiddenItem idOperRegistrazioneItem;
	protected HiddenItem idUdHiddenItem;
	protected HiddenItem idUdNuovoComeCopiaHiddenItem;
	protected HiddenItem prefKeyModelloHiddenItem;
	protected HiddenItem prefNameModelloHiddenItem;
	protected HiddenItem useridModelloHiddenItem;
	protected HiddenItem idUoModelloHiddenItem;
	protected HiddenItem codStatoDettHiddenItem;
	protected HiddenItem codStatoHiddenItem;
	protected HiddenItem idProcessHiddenItem;
	protected HiddenItem estremiProcessHiddenItem;
	protected HiddenItem ruoloSmistamentoAttoHiddenItem;
	protected HiddenItem tipoDocumentoSalvatoHiddenItem;
	protected HiddenItem tipoDocumentoHiddenItem;
	protected HiddenItem nomeTipoDocumentoHiddenItem;
	protected HiddenItem flgTipoProvItem;
	protected HiddenItem flgVersoBozzaItem;
	protected HiddenItem timestampGetDataItem;
	protected ImgItem iconaTipoProtocolloItem;
	protected HiddenItem idEmailArrivoHiddenItem;
	protected HiddenItem casellaIsPECHiddenItem;
	protected HiddenItem emailInviataFlgPECHiddenItem;
	protected HiddenItem emailInviataFlgPEOHiddenItem;
	protected HiddenItem emailArrivoInteroperabileHiddenItem;
	protected HiddenItem emailProvIndirizzoHiddenItem;
	protected HiddenItem emailProvFlgPECHiddenItem;
	protected HiddenItem emailProvFlgCasellaIstituzHiddenItem;
	protected HiddenItem emailProvFlgDichIPAHiddenItem;
	protected HiddenItem emailProvGestorePECHiddenItem;
	protected HiddenItem inviataMailInteroperabileHiddenItem;
	protected ImgButtonItem iconaEmailRicevutaItem;
	protected ImgButtonItem iconaEmailInviataItem;
	protected ImgButtonItem ricEccezioniInteropItem;
	protected ImgButtonItem ricAggiornamentiInteropItem;
	protected ImgButtonItem ricAnnullamentiInteropItem;
	protected HiddenItem tipoProtocolloItem;
	protected HiddenItem codCategoriaProtocolloItem;
	protected TextItem siglaProtocolloItem;
	protected NumericItem nroProtocolloItem;
	protected TextItem subProtocolloItem;
	protected DateTimeItem dataProtocolloItem;
	protected HiddenItem idUdAttoAutAnnProtocolloItem;
	protected ImgButtonItem dettaglioUdAttoAutAnnProtocolloButton;
	protected HiddenItem datiAnnullamentoHiddenItem;
	protected HiddenItem datiRichAnnullamentoHiddenItem;
	protected ImgButtonItem iconaAnnullataItem;
	protected ImgButtonItem iconaRichAnnullamentoItem;
	protected TextItem societaItem;
	protected TextItem desUserProtocolloItem;
	protected TextItem desUOProtocolloItem;
	protected TextItem estremiRepertorioPerStrutturaItem;	
	protected ImgButtonItem visualizzaDettStdProtButton; // Visualizza il dettaglio del protocollo nella modalita standard
	protected HiddenItem abilAssegnazioneSmistamentoItem;
	protected HiddenItem abilRispondiItem;
	protected ImgButtonItem iterProcessoCollegatoButton;
	protected ImgButtonItem operazioniEffettuateButton;
	protected ImgButtonItem permessiUdButton;
	protected HiddenItem idUDTrasmessaInUscitaConItem;
	protected HiddenItem estremiUDTrasmessoInUscitaConItem;
	protected ImgButtonItem estremiUDTrasmessoInUscitaConButton;
	protected HiddenItem presenzaDocCollegatiItem;
	protected HiddenItem documentiDaCollegareItem;
	protected ImgButtonItem documentiCollegatiButton;
	protected ImgButtonItem collegaDocumentiButton;
	protected ImgButtonItem altriRiferimentiButton;
	protected ImgButtonItem conDatiAnnullatiButton;
	
	protected DynamicForm registrazioneSecondariaForm;
	protected ImgItem iconaTipoRegistrazioneSecondariaItem;
	protected TextItem registrazioneSecondariaSiglaItem;
	protected NumericItem registrazioneSecondariaNumeroItem;
	protected TextItem registrazioneSecondariaSubItem;
	protected DateTimeItem registrazioneSecondariaDataRegistrazioneItem;
	protected HiddenItem registrazioneSecondariaIdUdAttoAutAnnItem;
	protected ImgButtonItem visualizzaDettIstanzaButton; // Visualizza il dettaglio dell'istanza
	
	protected DynamicForm altreNumerazioniUDForm;
	protected TextItem altreNumerazioniUDItem;
	
	protected DynamicForm altraNumerazioneForm;
	protected HiddenItem regNumSecondariaDesGruppoItem;
	protected ImgItem iconaTipoAltraNumerazioneItem;
	protected TextItem altraNumerazioneSiglaItem;
	protected NumericItem altraNumerazioneNumeroItem;
	protected TextItem altraNumerazioneSubItem;
	protected DateTimeItem altraNumerazioneDataRegistrazioneItem;
	protected TextItem altraNumerazioneRedattoreItem;
	protected TextItem altraNumerazioneDestinatarioItem;
	protected TextItem altraNumerazioneEstremiRepXStruttItem;
	
	protected DynamicForm altraNumerazioneProvvisoriaForm;
	protected ImgItem iconaTipoAltraNumerazioneProvvisoriaItem;
	protected TextItem altraNumerazioneProvvisoriaSiglaItem;
	protected NumericItem altraNumerazioneProvvisoriaNumeroItem;
	protected TextItem altraNumerazioneProvvisoriaSubItem;
	protected DateTimeItem altraNumerazioneProvvisoriaDataRegistrazioneItem;
	protected DateItem dtEsecutivitaItem;
	protected CheckboxItem flgImmediatamenteEsegItem;
	
	protected DynamicForm attoLiquidazioneForm;
	protected TextItem estremiAttoLiquidazioneItem;
	protected HiddenItem idUdLiquidazioneItem;
	
	protected DynamicForm statoForm;
	protected TextItem statoItem;
	
	protected DynamicForm nuovaRegistrazioneForm;
	protected DynamicForm nuovaRegistrazioneProtGeneraleForm;
	protected CheckboxItem protocolloGeneraleItem;
	protected SelectItem repertorioItem;
	
	protected DynamicForm tipoDocumentoForm;
	protected FilteredSelectItem tipoDocumentoItem;
	protected HiddenItem tipoDocumentoSalvatoItem;
	protected HiddenItem nomeTipoDocumentoItem;
	protected HiddenItem codCategoriaAltraNumerazioneItem;
	protected HiddenItem siglaAltraNumerazioneItem;
	
	protected DynamicForm dataArrivoSpedizioneForm;
	protected HiddenItem dataArrivoProtocolloEditabileItem;
	protected DateItem dataArrivoProtocolloItem;
	protected DateItem dataSpedizioneCartaceoItem;
	
	protected DynamicForm tipologieParticolariA2AForm;
	protected CheckboxItem flgMultaItem;
	protected HiddenItem statoTrasferimentoBloomfleetItem;
	protected HiddenItem dettaglioTrasferimentoBloomfleetItem;
	protected CheckboxItem flgDocContrattiItem;	
	protected TextItem codContrattiItem;
	protected RadioGroupItem flgFirmeCompilateContrattiItem;
	protected CheckboxItem flgDocContrattiBarcodeItem;	

	protected DynamicForm mittentiForm;
	protected MittenteProtItem mittentiItem;
	
	protected DynamicForm controinteressatiForm1;
	protected RadioGroupItem flgPresentiControinteressatiItem;
	protected DynamicForm controinteressatiForm2;
	protected ControinteressatoItem controinteressatiItem;	

	protected DynamicForm destinatariForm;
	protected DestinatarioProtItem destinatariItem;

	protected DynamicForm contenutiForm;
	protected ExtendedTextItem codRapidoOggettoItem;
	protected ExtendedTextAreaItem oggettoItem;
	protected ImgButtonItem lookupTemplateOggettoButton;
	protected ImgButtonItem salvaComeTemplateOggettoButton;

	protected DynamicForm datiRicezioneForm1;
	protected SelectItem mezzoTrasmissioneItem;
	protected TitleItem raccommandataTitleItem;
	protected TextItem nroRaccomandataItem;
	protected DateItem dataRaccomandataItem;
	protected DateTimeItem dataEOraArrivoItem;
	protected DateTimeItem dataEOraSpedizioneItem;
//	protected ImgButtonItem altriDatiRicezioneButton;
//	protected HiddenItem mezzoTrasmissioneHiddenItem;
//	protected HiddenItem nroRaccomandataHiddenItem;
//	protected HiddenItem dataRaccomandataHiddenItem;
//	protected HiddenItem nroListaRaccomandataHiddenItem;
	protected DynamicForm datiRicezioneForm2;
	protected TitleItem protRicevutoTitleItem;
	protected TextItem rifOrigineProtRicevutoItem;
	protected ExtendedTextItem nroProtRicevutoItem;
	protected AnnoItem annoProtRicevutoItem;
	protected DateItem dataProtRicevutoItem;

	protected DynamicForm docCollegatoForm;
	protected SelectItem siglaDocRiferimentoItem;
	protected AnnoItem annoDocRiferimentoItem;
	protected ExtendedNumericItem nroDocRiferimentoItem;

	protected DynamicForm riservatezzaForm;
	protected SelectItem livelloRiservatezzaItem;
	protected DateItem dataRiservatezzaItem;
	protected SelectItem prioritaRiservatezzaItem;
	protected CheckboxItem flgRispostaUrgenteItem;

	protected DynamicForm filePrimarioForm;
	protected HiddenItem idDocPrimarioHiddenItem;
	protected HiddenItem idAttachEmailPrimarioItem;	
	protected HiddenItem uriFilePrimarioItem;
	protected HiddenItem tsInsLastVerFilePrimarioItem;
	protected HiddenItem infoFileItem;
	protected HiddenItem remoteUriFilePrimarioItem;
	protected HiddenItem isDocPrimarioChangedItem;
	protected HiddenItem nomeFilePrimarioOrigItem;
	protected HiddenItem nomeFilePrimarioTifItem;
	protected HiddenItem uriFilePrimarioTifItem;
	protected HiddenItem nomeFileVerPreFirmaItem;
	protected HiddenItem uriFileVerPreFirmaItem;
	protected HiddenItem improntaVerPreFirmaItem;
	protected HiddenItem infoFileVerPreFirmaItem;
	protected HiddenItem nroLastVerItem;
	protected HiddenItem noteMancanzaFileItem;
	protected HiddenItem idUDScansioneItem;
	protected ExtendedTextItem nomeFilePrimarioItem;
	protected FileUploadItemWithFirmaAndMimeType uploadFilePrimarioItem;
	protected NestedFormItem filePrimarioButtons;	
	protected ImgButtonItem showNoteMancanzaFileButton;
	protected ImgButtonItem previewButton;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem editButton;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem trasformaInAllegatoButton;
	protected ImgButtonItem generaDaModelloButton;
	protected ImgButtonItem altreOpButton;
	protected TextItem improntaItem;
	protected HiddenItem tipoBarcodePrimarioItem;
	protected HiddenItem barcodePrimarioItem;
	protected HiddenItem energiaGasPrimarioItem;
	protected HiddenItem tipoSezioneContrattoPrimarioItem;
	protected HiddenItem codContrattoPrimarioItem;
	protected HiddenItem flgPresentiFirmeContrattoPrimarioItem;
	protected HiddenItem flgFirmeCompleteContrattoPrimarioItem;
	protected HiddenItem nroFirmePrevisteContrattoPrimarioItem;
	protected HiddenItem nroFirmeCompilateContrattoPrimarioItem;
	protected HiddenItem flgIncertezzaRilevazioneFirmePrimarioItem;
	protected ImgButtonItem dettaglioDocContrattiBarcodeButton;
	protected CheckboxItem flgSostituisciVerPrecItem;
	protected CheckboxItem flgOriginaleCartaceoItem;
	protected CheckboxItem flgCopiaSostitutivaItem;
	protected HiddenItem flgTimbratoFilePostRegItem;
	protected HiddenItem attivaTimbroTipologiaItem;
	protected HiddenItem opzioniTimbroItem;
	protected CheckboxItem flgTimbraFilePostRegItem;
	protected CheckboxItem flgNoPubblPrimarioItem;
	protected AllegatiItem filePrimarioVerPubblItem;
	protected CheckboxItem flgDatiSensibiliItem;
	protected DocumentItem filePrimarioOmissisItem;
	protected SelectItem docPressoCentroPostaItem;
	
	protected DynamicForm nominativiFirmaOlografaForm;
	protected NominativiFirmaOlografaItem nominativiFirmaOlografaItem;
	
	protected DynamicForm richAccessoCivicoForm;
	protected CheckboxItem flgRichAccCivSempliceItem;
	protected CheckboxItem flgRichAccCivGeneralizzatoItem;
		
	protected DynamicForm fileAllegatiForm;
	protected CanvasItem fileAllegatiItem;
	
	protected DynamicForm regEmergenzaForm;
	protected ExtendedTextItem rifRegEmergenzaItem;
	protected ExtendedNumericItem nroRegEmergenzaItem;
	protected DateTimeItem dataRegEmergenzaItem;
	protected HiddenItem idUserRegEmergenzaHiddenItem;
	protected HiddenItem idUoRegEmergenzaHiddenItem;

	protected DynamicForm collocazioneFisicaForm;
	protected HiddenItem idToponimoItem;
	protected HiddenItem idToponimoOutItem;
	protected ExtendedTextItem codRapidoCollocazioneFisicaItem;
	protected TextItem nomeCollocazioneFisicaItem;
	protected HiddenItem descrizioneCollocazioneFisicaItem;
	protected HiddenItem noteCollocazioneFisicaItem;
	protected ImgButtonItem lookupTopograficoCollocazioneFisicaButton;
	protected ImgButtonItem salvaInTopograficoCollocazioneFisicaButton;

	protected DynamicForm altriDatiForm;
	protected DateItem dataDocumentoItem;
	protected TextAreaItem noteItem;

	protected DynamicForm periziaForm;
	protected PeriziaItem periziaItem; 
	
	protected ProtocollazioneHeaderDetailSection detailSectionRegistrazione;
	protected ProtocollazioneHeaderDetailSection detailSectionNuovaRegistrazione;
	protected ProtocollazioneHeaderDetailSection detailSectionNuovaRegistrazioneProtGenerale;
	protected ProtocollazioneHeaderDetailSection detailSectionTipoDocumento;
	protected ProtocollazioneHeaderDetailSection detailSectionTipologieParticolariA2A;
	protected ProtocollazioneHeaderDetailSection detailSectionDataArrivoSpedizione;
	protected ProtocollazioneDetailSection detailSectionIndirizzoRiferimento;
	protected ProtocollazioneDetailSection detailSectionMittenti;
	protected ProtocollazioneDetailSection detailSectionControinteressati;
	protected ProtocollazioneDetailSection detailSectionDestinatari;
	protected ProtocollazioneDetailSection detailSectionContenuti;
	protected ProtocollazioneDetailSection detailSectionAllegati;
	protected ProtocollazioneDetailSection detailSectionDatiRicezione;
	protected ProtocollazioneDetailSection detailSectionDocCollegato;
	protected ProtocollazioneDetailSection detailSectionRegEmergenza;
	protected ProtocollazioneDetailSection detailSectionCollocazioneFisica;
	protected ProtocollazioneDetailSection detailSectionAltriDati;	
	protected ProtocollazioneDetailSection detailSectionPerizia;
	

	/**************************************
	 * TAB ASSEGNAZIONE E CLASSIFICAZIONE *
	 **************************************/

	protected DynamicForm assegnazioneForm;
	protected AssegnazioneItem assegnazioneSalvataItem;
	protected AssegnazioneItem assegnazioneItem;

	protected DynamicForm confermaAssegnazioneForm;
	protected SelectItem utentiAbilCPAItem;
	protected HiddenItem idUserConfermaAssegnazioneHiddenItem;
	protected CheckboxItem flgPreviaConfermaAssegnazioneItem;

	protected DynamicForm condivisioneForm;
	protected CondivisioneItem condivisioneSalvataItem;
	protected CondivisioneItem condivisioneItem;

	protected DynamicForm classificazioneFascicolazioneForm;
	protected ClassificaFascicoloItem classificazioneFascicolazioneItem;

	protected DynamicForm folderCustomForm;
	protected FolderCustomItem folderCustomItem;

	protected ProtocollazioneDetailSection detailSectionAssegnazione;
	protected ProtocollazioneDetailSection detailSectionCondivisione;
	protected ProtocollazioneDetailSection detailSectionClassificazioneFascicolazione;
	protected ProtocollazioneDetailSection detailSectionFolderCustom;

	/******************************
	 * TAB ESIBENTI E INTERESSATI *
	 ******************************/

	protected DynamicForm esibentiForm;
	protected EsibentiItem esibentiItem;

	protected DynamicForm interessatiForm;
	protected InteressatiItem interessatiItem;

	protected ProtocollazioneDetailSection detailSectionEsibenti;
	protected ProtocollazioneDetailSection detailSectionInteressati;
	
	/******************************
	 * TAB DELEGATO E FIRMATARI *
	 ******************************/

	protected DynamicForm delegatoForm;
	protected DelegatoItem delegatoItem;

	protected DynamicForm firmatariForm;
	protected FirmatariItem firmatariItem;

	protected ProtocollazioneDetailSection detailSectionDelegato;
	protected ProtocollazioneDetailSection detailSectionFirmatari;

	/*****************
	 * TAB ALTRE VIE *
	 *****************/

	protected DynamicForm altreVieForm;
	protected AltreVieItem altreVieItem;

	protected ProtocollazioneDetailSection detailSectionAltreVie;

	/**********************
	 * TAB PUBBLICAZIONE  *
	 **********************/
	 
	protected DynamicForm pubblicazioneForm;
	protected HiddenItem idPubblicazioneHiddenItem;
	protected TextItem nroPubblicazioneItem;
	protected DateItem dataPubblicazioneItem;
	protected DateItem dataDalPubblicazioneItem;
	protected DateItem dataAlPubblicazioneItem;
	protected TextItem richiestaDaPubblicazioneItem;
	protected TextItem statoPubblicazioneItem;
	protected TextItem formaPubblicazioneItem;
	protected TextItem rettificataDaPubblicazioneItem;
	protected TextAreaItem motivoRettificaPubblicazioneItem;
	protected TextAreaItem motivoAnnullamentoPubblicazioneItem;
	protected TextAreaItem proroghePubblicazioneItem;
	
	protected DynamicForm ripubblicazioneForm;
	protected HiddenItem idRipubblicazioneHiddenItem;
	protected TextItem nroRipubblicazioneItem;
	protected DateItem dataRipubblicazioneItem;
	protected DateItem dataDalRipubblicazioneItem;
	protected DateItem dataAlRipubblicazioneItem;
	protected TextItem richiestaDaRipubblicazioneItem;
	protected TextItem statoRipubblicazioneItem;
	protected TextItem formaRipubblicazioneItem;
	protected TextItem rettificataDaRipubblicazioneItem;	
	protected TextAreaItem motivoRettificaRipubblicazioneItem;
	protected TextAreaItem motivoAnnullamentoRipubblicazioneItem;
	protected TextAreaItem prorogheRipubblicazioneItem;	
	
	protected ProtocollazioneDetailSection detailSectionPubblicazione;
	protected ProtocollazioneDetailSection detailSectionRipubblicazione;

	/******************************
	 * TAB ATTRIBUTI DINAMICI DOC *
	 ******************************/

	protected LinkedHashMap<String, String> attributiAddDocTabs;
	protected HashMap<String, VLayout> attributiAddDocLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDocDetails;
	protected Map<String, Object> attributiDinamiciDocDaCopiare;
		
	/*******************************************
	 * FLAG ABILITAZIONI MODIFICAFRECCIABUTTON *
	 *******************************************/
	
	private boolean isAbilModificaTipologia;
	private boolean isAbilModificaDatiExtraIter;
	private boolean isAbilModificaOpereAtto;
	private boolean isAbilModificaDatiPubblAtto;
	
	/****************
	 * INVIO POSTEL *
	 ****************/
	protected boolean isValidazionePostelOk;
	private String modalitaInvio;
	
	/*********
	 * ALTRO *
	 *********/
	
	protected Record recordProtocollato;
	
	public Record getRecordProtocollato() {
		return recordProtocollato;
	}

	
	public void setRecordProtocollato(Record recordProtocollato) {
		this.recordProtocollato = recordProtocollato;
	}

	/**
	 * Metodo costruttore
	 * 
	 */
	public ProtocollazioneDetail(String nomeEntita) {

		super(nomeEntita);

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			SC.setScreenReaderMode(true);
		} 
		instance = this;
		rowidDoc = null;
				
		encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		setPaddingAsLayoutMargin(false);

		createMainLayout();
		setMembers(mainLayout);

		editNewRecord();
		newMode();
	}
	
	/**
	 * Metodo astratto che indica se è attiva nella maschera la modalità wizard
	 *  
	 */
	public abstract boolean isModalitaWizard();
	
	/**
	 * Metodo statico che a partire dal record di dettaglio istanzia la maschera
	 * corretta da visualizzare
	 * 
	 */
	public static ProtocollazioneDetail getInstance(final Record record) {
		return getInstance(record, null);
	}

	/**
	 * Metodo statico che a partire dal record di dettaglio istanzia la maschera
	 * corretta da visualizzare
	 * 
	 */
	public static ProtocollazioneDetail getInstance(final Record record, ServiceCallback<Record> afterUpdateCallback) {

		String flgTipoProv = record.getAttribute("flgTipoProv") != null ? record.getAttribute("flgTipoProv") : "";
		String tipoNumerazionePrincipale = record.getAttribute("tipoProtocollo") != null
				? record.getAttribute("tipoProtocollo") : "";
		String siglaNumerazionePrincipale = record.getAttribute("siglaProtocollo") != null
				? record.getAttribute("siglaProtocollo") : "";
		String tipoNumerazioneSecondaria = record.getAttribute("tipoNumerazioneSecondaria") != null
				? record.getAttribute("tipoNumerazioneSecondaria") : "";
		String siglaNumerazioneSecondaria = record.getAttribute("siglaNumerazioneSecondaria") != null
				? record.getAttribute("siglaNumerazioneSecondaria") : "";
		boolean flgCaricamentoPGPregressoDaGUI = record.getAttributeAsBoolean("flgCaricamentoPGPregressoDaGUI") != null && record.getAttributeAsBoolean("flgCaricamentoPGPregressoDaGUI");
		boolean flgPropostaAtto = record.getAttributeAsBoolean("flgPropostaAtto") != null && record.getAttributeAsBoolean("flgPropostaAtto");
		boolean flgCompilazioneModulo = record.getAttributeAsBoolean("flgCompilazioneModulo") != null && record.getAttributeAsBoolean("flgCompilazioneModulo");
		// Protocollo PG@Web caricato da GUI
		if(flgCaricamentoPGPregressoDaGUI) {
			return new ProtocolloPregressoDetail("carica_protocollo_pregresso");			
		} 
		// Proposta atto / Atto
		else if (flgPropostaAtto){
			final Boolean flgAttoPregresso = record.getAttributeAsBoolean("flgAttoPregresso");
			return new ProtocollazioneDetailAtti("protocollazione_atti", null) {
				
				@Override
				public boolean isModalitaAllegatiGrid() {
					return ProtocollazioneUtil.isAttivaModalitaAllegatiGrid(record);
				}

				@Override
				public boolean isPregresso() {
					return flgAttoPregresso != null && flgAttoPregresso;
				}
			};
		}
		// Registro fatture
		else if ((tipoNumerazionePrincipale.equalsIgnoreCase("PG") && siglaNumerazioneSecondaria.equalsIgnoreCase("FATTURE")) ||
				 (siglaNumerazionePrincipale.equalsIgnoreCase("FATTURE"))) {
			return new RegistroFattureDetail("registro_fatture");
		}
		// Istanze (se ho come numerazione principale PG apro la maschera di protocollazione standard)
		else if (/*(tipoNumerazionePrincipale.equalsIgnoreCase("PG") && siglaNumerazioneSecondaria.equalsIgnoreCase("ISTANZA")) ||*/ 
				 (siglaNumerazionePrincipale.equalsIgnoreCase("ISTANZA"))) {
			return new IstanzeDetail("istanze");
		}
		// Repertorio in entrata
		else if (flgTipoProv.equalsIgnoreCase("E") && tipoNumerazionePrincipale.equalsIgnoreCase("R")) {
			return ProtocollazioneUtil.buildRepertorioDetail("E",record);
		}
		// Repertorio interno
		else if (flgTipoProv.equalsIgnoreCase("I") && tipoNumerazionePrincipale.equalsIgnoreCase("R")) {
			return ProtocollazioneUtil.buildRepertorioDetail("I",record);
		}
		// Repertorio in uscita
		else if (flgTipoProv.equalsIgnoreCase("U") && tipoNumerazionePrincipale.equalsIgnoreCase("R")) {
			return ProtocollazioneUtil.buildRepertorioDetail("U",record);
		}
//		// Atto
//		else if (tipoNumerazionePrincipale.equalsIgnoreCase("A") || tipoNumerazioneSecondaria.equalsIgnoreCase("A")) {
//			final Boolean flgAttoPregresso = record.getAttributeAsBoolean("flgAttoPregresso");
//			return new ProtocollazioneDetailAtti("protocollazione_atti", null) {
//
//				@Override
//				public boolean isPregresso() {
//					return flgAttoPregresso != null && flgAttoPregresso;
//				}
//			};
//		}
		// Protocollazione in entrata
		else if (flgTipoProv.equalsIgnoreCase("E") && (tipoNumerazionePrincipale.equalsIgnoreCase("PG") || tipoNumerazionePrincipale.equalsIgnoreCase("PP"))) {
			return ProtocollazioneUtil.buildProtocollazioneDetailEntrata(record, null, afterUpdateCallback);
		}
		// Protocollazione in uscita
		else if (flgTipoProv.equalsIgnoreCase("U") && (tipoNumerazionePrincipale.equalsIgnoreCase("PG") || tipoNumerazionePrincipale.equalsIgnoreCase("PP"))) {
			return ProtocollazioneUtil.buildProtocollazioneDetailUscita(record);
		}
		// Protocollazione interna
		else if ((flgTipoProv.equalsIgnoreCase("I") && (tipoNumerazionePrincipale.equalsIgnoreCase("PG") || tipoNumerazionePrincipale.equalsIgnoreCase("PP"))) ||						
				 (tipoNumerazionePrincipale.equalsIgnoreCase("PI"))) {
			return ProtocollazioneUtil.buildProtocollazioneDetailInterna(record);
		}
		// Bozza
		else if (tipoNumerazionePrincipale.equalsIgnoreCase("NI")) {
			// Compilazione modulo
			if(flgCompilazioneModulo) {
				String idTipoDoc = record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
				return new ProtocollazioneDetailModelli("compilazione_modello." + idTipoDoc);
			} else {
				return ProtocollazioneUtil.buildProtocollazioneDetailBozze(record, null, null, afterUpdateCallback);
			}
		} 
		// Documento registrato
		else {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("isDocRegistrato", "true");
			params.put("flgTipoProv", flgTipoProv);
			return ProtocollazioneUtil.buildProtocollazioneDetailBozze(record, params);
		}
		// return null;
	}

	/**
	 * Metodo che indica se ho un altro tipo di numerazione oltre al protocollo
	 * generale
	 * 
	 */
	public boolean isAltraNumerazione() {
		return false;
	}

	/**
	 * Metodo che restituisce la categoria del tipo di numerazione
	 * 
	 */
	public String getCategoriaAltraNumerazione() {
		return null;
	}

	/**
	 * Metodo che restituisce la sigla del tipo di numerazione
	 * 
	 */
	public String getSiglaAltraNumerazione() {
		return null;
	}

	/**
	 * Metodo che restituisce la label del tipo di numerazione
	 * 
	 */
	public String getTitleAltraNumerazione() {
		return null;
	}

	/**
	 * Metodo che restituisce l'icona associata al tipo di numerazione
	 * 
	 */
	public String getIconAltraNumerazione() {
		return null;
	}
	
	/**
	 * Metodo che restituisce la label del tipo di numerazione provvisoria
	 * 
	 */
	public String getTitleAltraNumerazioneProvvisoria() {
		return "Numerazione provvisoria di proposta";
	}

	/**
	 * Metodo che restituisce l'icona associata al tipo di numerazione provvisoria
	 * 
	 */
	public String getIconAltraNumerazioneProvvisoria() {
		return "protocollazione/provvisoria.png";
	}

	public ProtocollazioneDetail getProtocollazioneDetailInstance() {
		return instance;
	}
		
	public DettaglioPostaElettronica getDettaglioPostaElettronica() {
		return dettaglioPostaElettronica;
	}
	
	public void setDettaglioPostaElettronica(DettaglioPostaElettronica dettaglioPostaElettronica) {
		this.dettaglioPostaElettronica = dettaglioPostaElettronica;
	}

	public EditaProtocolloWindowFromMail getEditaProtocolloWindowFromMail() {
		return editaProtocolloWindowFromMail;
	}
	
	public void setEditaProtocolloWindowFromMail(EditaProtocolloWindowFromMail editaProtocolloWindowFromMail) {
		this.editaProtocolloWindowFromMail = editaProtocolloWindowFromMail;
	}
	
	/**
	 * Metodo che indica se mi trovo nella maschera di una bozza
	 * 
	 */
	public boolean isProtocollazioneDetailBozze() {
		return instance instanceof ProtocollazioneDetailBozze;
	}
	
	/**
	 * Metodo che indica se mi trovo nella maschera di compilazione modulo
	 * 
	 */
	public boolean isProtocollazioneDetailModelli() {
		return instance instanceof ProtocollazioneDetailModelli;
	}
	
	/**
	 * Metodo che indica se mi trovo nella maschera di una protocollazione in
	 * entrata
	 * 
	 */
	public boolean isProtocollazioneDetailEntrata() {
		return instance instanceof ProtocollazioneDetailEntrata;
	}

	/**
	 * Metodo che indica se mi trovo nella maschera di una protocollazione in
	 * uscita
	 * 
	 */
	public boolean isProtocollazioneDetailUscita() {
		return instance instanceof ProtocollazioneDetailUscita;
	}

	/**
	 * Metodo che indica se mi trovo nella maschera di una protocollazione
	 * interna
	 * 
	 */
	public boolean isProtocollazioneDetailInterna() {
		return instance instanceof ProtocollazioneDetailInterna;
	}

	/**
	 * Metodo che indica se mi trovo nella maschera di un repertorio in entrata
	 * 
	 */
	public boolean isRepertorioDetailEntrata() {
		return instance instanceof RepertorioDetailEntrata;
	}
	
	/**
	 * Metodo che indica se mi trovo nella maschera di un repertorio interno
	 * 
	 */
	public boolean isRepertorioDetailInterno() {
		return instance instanceof RepertorioDetailInterno;
	}
	
	/**
	 * Metodo che indica se mi trovo nella maschera di un repertorio in uscita
	 * 
	 */
	public boolean isRepertorioDetailUscita() {
		return instance instanceof RepertorioDetailUscita;
	}

	/**
	 * Metodo che indica se mi trovo nella maschera di un registro fatture
	 * 
	 */
	public boolean isRegistroFattureDetail() {
		return instance instanceof RegistroFattureDetail;
	}

	/**
	 * Metodo che indica se mi trovo nella maschera di un'istanza
	 * 
	 */
	public boolean isIstanzeDetail() {
		return instance instanceof IstanzeDetail;
	}
	
	/**
	 * Metodo che indica se mi trovo nella maschera di task dettaglio ud generico
	 * 
	 */
	public boolean isTaskDettUdGenDetail() {
		return instance instanceof TaskDettUdGenDetail;
	}


	public boolean isProtocollazioneDetailAtti() {
		return instance instanceof ProtocollazioneDetailAtti;
	}
	
	/**
	 * Metodo che indica se ci si trova in un protocollo di accesso atti sue"
	 * 
	 */
	public boolean isProtocollazioneAccessoAttiSue() {
		return false;
	}
	
	/**
	 * Metodo che indica se ci si trova in una richiesta accesso civico
	 * 
	 */
	public boolean isRichiestaAccessoCivico() {
		if(mode != null && mode.equals("edit") && editMode != null 
				&& "modificaRegAccessoCivico".equalsIgnoreCase(editMode)) {
			return true;
		}
		return false;		
	}
	
	/**
	 * Metodo che indica se ci si trova in una modifica dati extra iter
	 * 
	 */
	public boolean isModificaDatiExtraIter() {
		if(mode != null && mode.equals("edit") && editMode != null 
				&& "modificaDatiExtraIter".equalsIgnoreCase(editMode)) {
			return true;
		}
		return false;		
	}
	
	/**
	 * Metodo che indica se ci si trova in una richiesta contratti
	 * 
	 */
	public boolean isRichiestaContratti() {
		return false;
	}
	
	/**
	 * Metodo che indica se è un documento registrato o una bozza (tipo NI)
	 * 
	 */
	public boolean isDocumentoRegistrato() {
		return false;
	}

	/**
	 * Metodo che indica il tipo di provenienza della registrazione
	 * 
	 */
	public String getFlgTipoProv() {
		return null;
	}

	/**
	 * Metodo che indica se si è abilitati alla protocollazione
	 * 
	 */
	public boolean isAbilToProt() {
		return (getFlgTipoProv() != null) ? Layout.isPrivilegioAttivo("PRT/" + getFlgTipoProv()) : false;
	}

	/**
	 * Metodo che indica se si è abilitati alla stampa delle etichette per faldoni
	 * 
	 */
	public boolean isAbilToStampaEtichetteFaldoni() {
		return Layout.isPrivilegioAttivo("SEF");
	}

	/**
	 * Metodo che indica se la maschera è utilizzata all'interno di un iter
	 * procedurale
	 * 
	 */
	public boolean isTaskDetail() {
		return instance instanceof TaskFlussoInterface;
	}

	/**
	 * Metodo per costruire il layout della maschera
	 * 
	 */
	protected void createMainLayout() {

		createMainToolStrip();
		createTabSet();
		createDetailToolStrip();

		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(mainToolStrip);
		mainLayout.addMember(tabSet);
		mainLayout.addMember(detailToolStrip);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			Label label = new Label();
			label.setContents("<h5 style=\"margin-left: 9px\">Azioni</h5>");
			label.setWidth100();
			label.setHeight(30);		
			mainLayout.setTabIndex(-1);
			mainLayout.setCanFocus(false);
			mainLayout.setName("main_layout");
			mainLayout.addMember(label);				
		} 

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		}
	}

	/**
	 * Metodo per costruire la barra superiore contenente la select dei modelli
	 * e della U.O. protocollante
	 * 
	 */
	protected void createMainToolStrip() {

		createModelliSelectItem();
		createUoProtocollanteSelectItem();

		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);
		mainToolStrip.setRedrawOnResize(true);

		// se devo mostrare sia la select dei modelli che della U.O.
		// protocollante
		if (showModelliSelectItem() && showUoProtocollanteSelectItem()) {
			modelliSelectItem.setVisible(true);
			uoProtocollanteSelectItem.setVisible(true);
			mainToolStrip.addFormItem(modelliSelectItem);
			mainToolStrip.addFill();
			mainToolStrip.addFormItem(uoProtocollanteSelectItem);
		}
		// se devo mostrare solo la select dei modelli
		else if (showModelliSelectItem()) {
			modelliSelectItem.setVisible(true);
			uoProtocollanteSelectItem.setVisible(false);
			mainToolStrip.addFormItem(modelliSelectItem);
		}
		// se devo mostrare solo la select della U.O. protocollante
		else if (showUoProtocollanteSelectItem()) {
			modelliSelectItem.setVisible(false);
			uoProtocollanteSelectItem.setVisible(true);
			mainToolStrip.addFormItem(uoProtocollanteSelectItem);
		}
		// se non devo mostrare nessuna delle due
		else {
			modelliSelectItem.setVisible(false);
			uoProtocollanteSelectItem.setVisible(false);
			mainToolStrip.setVisible(false);
		}
	}

	/**
	 * Metodo che indica se mostrare o meno la select dei modelli
	 * 
	 */
	public boolean showModelliSelectItem() {
		return true;
	}
	
	/**
	 * Metodo che indica se è possibile rimuovere il modello dalla select
	 * 
	 */
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}

	/**
	 * Metodo per costruire la select dei modelli (per il settaggio dei valori
	 * dei campi a maschera)
	 * 
	 */
	protected void createModelliSelectItem() throws IllegalStateException {

		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntita + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("MRP") ? "1" : "0");

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setPrompt(I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title());
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);

		final ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		});
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (isAbilToRemoveModello(event.getRecord())) {
					deleteModello(event.getRecord());
				}				
			}   
		});
		// Per accessibilità. se modelliRemoveField.setIgnoreKeyboardClicks(false);, alla pressione della freccetta giù, parte automaticamente l'action ( cancella il record )
		modelliRemoveField.setIgnoreKeyboardClicks(true);
		
		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);
		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");
		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);

		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
//		modelliPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {				
				loadModello(event.getRecord(), event.getColNum());
			}
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			modelliPickListProperties.addSelectionChangedHandler(new SelectionChangedHandler() {
				
				@Override
				public void onSelectionChanged(SelectionEvent event) {
					recordModelloFocused = event.getRecord();
				}
			});
			
			modelliPickListProperties.addBodyKeyPressHandler(new BodyKeyPressHandler() {
				
				@Override
				public void onBodyKeyPress(BodyKeyPressEvent event) {
					if (EventHandler.getKey().equalsIgnoreCase("Enter")) {
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								ListGridRecord selectedRecord = modelliSelectItem.getSelectedRecord();
								loadModello(selectedRecord, 1);
							}
						});
					} else if (EventHandler.getKey().equalsIgnoreCase("Delete")) {
						Layout.showConfirmDialogWithWarning("Attenzione!", I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), "Ok", "Annulla", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if (recordModelloFocused != null) {
										deleteModello(recordModelloFocused);
									}
								} else {
									Layout.addMessage(
											new MessageBean("Cancellazione interrotta dall'utente", "", MessageType.INFO));
								}
							}
						});
					}
				}
			});		
		}
		modelliSelectItem.setPickListProperties(modelliPickListProperties);
	}
	
	private void deleteModello (final ListGridRecord record) {
		if (isAbilToRemoveModello(record)) {					
			SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if(value) {
						final String key = record.getAttribute("key");
						modelliDS.removeData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								String value = (String) modelliSelectItem.getValue();
								if (key != null && value != null && key.equals(value)) {
									modelliSelectItem.setValue((String) null);
								}
							} 
						});
					}
				}
			});
		}
	}
	
	private void loadModello (ListGridRecord record, int numCol) {
		boolean isRemoveField = isAbilToRemoveModello(record) && numCol == 0;
					if(!isRemoveField) {									
			String userid = (String) record.getAttribute("userid");
			String prefName = (String) record.getAttribute("prefName");
					if (prefName != null && !"".equals(prefName)) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
						criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
						if (userid.startsWith("UO.")) {
					String idUo = (String) record.getAttribute("idUo");
							criteria.addCriteria("idUo", idUo);
						}
						modelliDS.addParam("isControllaValiditaIndirizzoMezzoTrasm",  "1"); 					
						modelliDS.fetchData(criteria, new DSCallback() {
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									final Record recordModello = data[0];
									final Record record = new Record(JSON.decode(recordModello.getAttribute("value")));	
									final Record detailRecord = getRecordToSave();
									if(isProtFromEmailDaModello(detailRecord)) {
										if((record.getAttributeAsString("oggetto") != null &&
										    !"".equalsIgnoreCase(record.getAttributeAsString("oggetto")))) {
																									
											SC.ask("Vuoi sovrascrivere l'oggetto della mail con quello del modello ?", new BooleanCallback() {
												
												@Override
												public void execute(Boolean value) {
													populateRecordModelloProtEmail(record, detailRecord, value);		
													//clearFolderCustomDaModello(record);
													//clearClassFascDaModello(record);								
													populateFolderCustomDaModello(record);
													populateClassFascDaModello(record);
													editNewRecordFromModello(record.toMap(), recordModello);	
													setAttributiDinamiciDocDaCopiare(record.getAttributeAsMap("valori")); //TODO non funziona, non carica correttamente gli attributi dinamici a maschera e le validazioni della modalità wizard vanno in errore
													newDaModelloMode();	
												}
											});		
													
										} else {
											populateRecordModelloProtEmail(record, detailRecord, false);
											//clearFolderCustomDaModello(record);
											//clearClassFascDaModello(record);								
											populateFolderCustomDaModello(record);
											populateClassFascDaModello(record);	
											editNewRecordFromModello(record.toMap(), recordModello);
											setAttributiDinamiciDocDaCopiare(record.getAttributeAsMap("valori")); //TODO non funziona, non carica correttamente gli attributi dinamici a maschera e le validazioni della modalità wizard vanno in errore
											newDaModelloMode();	
										}									
									} else {								
										//clearFolderCustomDaModello(record);
										//clearClassFascDaModello(record);								
										populateFolderCustomDaModello(record);
										populateClassFascDaModello(record);		
										editNewRecordFromModello(record.toMap(), recordModello);		
										setAttributiDinamiciDocDaCopiare(record.getAttributeAsMap("valori")); //TODO non funziona, non carica correttamente gli attributi dinamici a maschera e le validazioni della modalità wizard vanno in errore
										newDaModelloMode();
									}
								}
							}						
						});
					}					
				}
			}
	
	/**
	 * Metodo che indica se provengo dall'azione di protocollazione/repertoriazione email 
	 */
	private Boolean isProtFromEmailDaModello(Record record) {
		boolean isFromEmail = false;
		if(record != null && record.getAttributeAsString("idEmailArrivo") != null
				&& !"".equalsIgnoreCase(record.getAttributeAsString("idEmailArrivo"))) {
			isFromEmail = true;
		}
		return isFromEmail;
	}
	
	/**
	 * Metodo che inietta i valori della protocollazione email nel record del modello
	 */
	private void populateRecordModelloProtEmail(Record recordModello, Record detailRecord, Boolean isAbilSovrOggetto) {
		/** DATI EMAIL */
		recordModello.setAttribute("flgPEC", detailRecord.getAttributeAsBoolean("flgPEC") != null && detailRecord.getAttributeAsBoolean("flgPEC"));
		recordModello.setAttribute("flgCasellaIstituzionale", detailRecord.getAttributeAsBoolean("flgCasellaIstituzionale") != null && detailRecord.getAttributeAsBoolean("flgCasellaIstituzionale"));
		recordModello.setAttribute("flgDichIPA", detailRecord.getAttributeAsBoolean("flgDichIPA") != null && detailRecord.getAttributeAsBoolean("flgDichIPA"));
		recordModello.setAttribute("gestorePEC", detailRecord.getAttributeAsString("gestorePEC"));	
		recordModello.setAttribute("idEmailArrivo", detailRecord.getAttributeAsString("idEmailArrivo"));
		recordModello.setAttribute("casellaEmailDestinatario", detailRecord.getAttributeAsString("casellaEmailDestinatario"));
		recordModello.setAttribute("casellaIsPEC", detailRecord.getAttributeAsBoolean("casellaIsPEC") != null && detailRecord.getAttributeAsBoolean("casellaIsPEC"));
		recordModello.setAttribute("emailArrivoInteroperabile", detailRecord.getAttributeAsBoolean("emailArrivoInteroperabile") != null && detailRecord.getAttributeAsBoolean("emailArrivoInteroperabile"));
		recordModello.setAttribute("emailInviataFlgPEC", detailRecord.getAttributeAsBoolean("emailInviataFlgPEC") != null && detailRecord.getAttributeAsBoolean("emailInviataFlgPEC"));
		recordModello.setAttribute("emailInviataFlgPEO", detailRecord.getAttributeAsBoolean("emailInviataFlgPEO") != null && detailRecord.getAttributeAsBoolean("emailInviataFlgPEO"));
		recordModello.setAttribute("inviataMailInteroperabile", detailRecord.getAttributeAsBoolean("inviataMailInteroperabile") != null && detailRecord.getAttributeAsBoolean("inviataMailInteroperabile"));

		/** DATI PROTOCOLLO */
		if(!isAbilSovrOggetto) {
			recordModello.setAttribute("oggetto", detailRecord.getAttributeAsString("oggetto"));
		}
		recordModello.setAttribute("listaMittenti", detailRecord.getAttributeAsRecordList("listaMittenti") != null ? detailRecord.getAttributeAsRecordList("listaMittenti") : null);
		recordModello.setAttribute("mezzoTrasmissione", detailRecord.getAttributeAsString("mezzoTrasmissione"));
		recordModello.setAttribute("dataEOraArrivo", detailRecord.getAttributeAsDate("dataEOraArrivo") != null ? detailRecord.getAttributeAsDate("dataEOraArrivo") : null);
		recordModello.setAttribute("dataEOraSpedizione", detailRecord.getAttributeAsDate("dataEOraSpedizione") != null ? detailRecord.getAttributeAsDate("dataEOraSpedizione") : null);
		recordModello.setAttribute("supportoOriginale", detailRecord.getAttributeAsString("supportoOriginale"));
		
		/**FILE PRIMARIO */
		recordModello.setAttribute("idDocPrimario", detailRecord.getAttribute("idDocPrimario") != null ? detailRecord.getAttribute("idDocPrimario"): null);
		recordModello.setAttribute("nomeFilePrimario", detailRecord.getAttributeAsString("nomeFilePrimario"));
		recordModello.setAttribute("nomeFilePrimarioOrig", detailRecord.getAttributeAsString("nomeFilePrimarioOrig"));
		recordModello.setAttribute("uriFilePrimario", detailRecord.getAttributeAsString("uriFilePrimario"));
		recordModello.setAttribute("remoteUriFilePrimario", detailRecord.getAttributeAsBoolean("remoteUriFilePrimario") != null && detailRecord.getAttributeAsBoolean("remoteUriFilePrimario"));
		recordModello.setAttribute("idAttachEmailPrimario", detailRecord.getAttributeAsString("idAttachEmailPrimario"));
		if(detailRecord.getAttributeAsJavaScriptObject("infoFile") != null) {
			InfoFileRecord lRecordInfo = InfoFileRecord.buildInfoFileRecord(detailRecord.getAttributeAsJavaScriptObject("infoFile"));
			recordModello.setAttribute("infoFile", lRecordInfo);
		}
		recordModello.setAttribute("impronta", detailRecord.getAttributeAsString("impronta"));
		recordModello.setAttribute("isDocPrimarioChanged", detailRecord.getAttributeAsBoolean("isDocPrimarioChanged") != null && detailRecord.getAttributeAsBoolean("isDocPrimarioChanged"));
		recordModello.setAttribute("flgNoPubblPrimario", detailRecord.getAttributeAsBoolean("flgNoPubblPrimario") != null && detailRecord.getAttributeAsBoolean("flgNoPubblPrimario"));
		recordModello.setAttribute("flgDatiSensibili", detailRecord.getAttributeAsBoolean("flgDatiSensibili") != null && detailRecord.getAttributeAsBoolean("flgDatiSensibili"));
		recordModello.setAttribute("isFilePrimarioPubblicato", detailRecord.getAttributeAsBoolean("isFilePrimarioPubblicato") != null && detailRecord.getAttributeAsBoolean("isFilePrimarioPubblicato"));
		recordModello.setAttribute("idUDScansione", detailRecord.getAttributeAsString("idUDScansione"));	
		recordModello.setAttribute("mimetypeVerPreFirma", detailRecord.getAttributeAsString("mimetypeVerPreFirma"));	
		recordModello.setAttribute("uriFileVerPreFirma", detailRecord.getAttributeAsString("uriFileVerPreFirma"));
		recordModello.setAttribute("nomeFileVerPreFirma", detailRecord.getAttributeAsString("nomeFileVerPreFirma"));
		recordModello.setAttribute("flgConvertibilePdfVerPreFirma", detailRecord.getAttributeAsBoolean("flgConvertibilePdfVerPreFirma") != null && detailRecord.getAttributeAsBoolean("flgConvertibilePdfVerPreFirma"));
		recordModello.setAttribute("improntaVerPreFirma", detailRecord.getAttributeAsString("improntaVerPreFirma"));
		if(detailRecord.getAttributeAsJavaScriptObject("infoFileVerPreFirma") != null) {
			InfoFileRecord lRecordInfoPreFirma = InfoFileRecord.buildInfoFileRecord(detailRecord.getAttributeAsJavaScriptObject("infoFileVerPreFirma"));
			recordModello.setAttribute("infoFileVerPreFirma", lRecordInfoPreFirma);
		}
		/**FILE PRIMARIO OMISSIS */
		recordModello.setAttribute("filePrimarioOmissis", detailRecord.getAttributeAsRecord("filePrimarioOmissis") != null ? detailRecord.getAttributeAsRecord("filePrimarioOmissis") : null);	
		/**ALLEGATI */
		recordModello.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati") != null ? detailRecord.getAttributeAsRecordList("listaAllegati") : null);
	}
	
	/**
	 * Metodo che rimuove i FolderCustom presenti nel modello a quelli in maschera
	 * in fase di protocollazione di una bozza.
	 */
//	private void clearFolderCustomDaModello(Record record) {		
//		if(isProtocollazioneDetailBozze()) {
//			if(record.getAttributeAsRecordList("listaFolderCustom") != null &&
//				!record.getAttributeAsRecordList("listaFolderCustom").isEmpty()) {
//				for (int i = 0; i < record.getAttributeAsRecordList("listaFolderCustom").getLength(); i++) {
//					Record folderCustomModello = record.getAttributeAsRecordList("listaFolderCustom").get(i);
//					if(folderCustomModello != null && folderCustomModello.getAttributeAsString("id") != null &&
//					   !"".equalsIgnoreCase(folderCustomModello.getAttributeAsString("id"))) {	
//							record.getAttributeAsRecordList("listaFolderCustom").remove(folderCustomModello);
//					}					
//				}
//				if(folderCustomForm != null && folderCustomForm.getValuesAsRecord() != null &&
//				   folderCustomForm.getValuesAsRecord().getAttributeAsRecordList("listaFolderCustom") != null &&
//				   !folderCustomForm.getValuesAsRecord().getAttributeAsRecordList("listaFolderCustom").isEmpty()) {
//				   RecordList lRecordListFolder = folderCustomForm.getValuesAsRecord().getAttributeAsRecordList("listaFolderCustom");
//				   if(lRecordListFolder != null) {
//					   for (int i = 0; i < lRecordListFolder.getLength(); i++) {
//							Record item = lRecordListFolder.get(i);
//							if(item != null && item.getAttributeAsString("id") != null &&
//							   !"".equalsIgnoreCase(item.getAttributeAsString("id"))) {	
//									record.getAttributeAsRecordList("listaFolderCustom").add(item);
//							}						
//					   }
//				   }	
//				}
//			}
//		}
//	}
	
	/**
	 * Metodo che rimuove i ClassFasc presenti nel modello a quelli in maschera
	 * in fase di protocollazione di una bozza.
	 */
//	private void clearClassFascDaModello(Record record) {		
//		if(isProtocollazioneDetailBozze()) {
//			if(record.getAttributeAsRecordList("listaClassFasc") != null &&
//				!record.getAttributeAsRecordList("listaClassFasc").isEmpty()) {
//				for (int i = 0; i < record.getAttributeAsRecordList("listaClassFasc").getLength(); i++) {
//					Record classFascModello = record.getAttributeAsRecordList("listaClassFasc").get(i);
//					if(classFascModello != null && 
//					   ((classFascModello.getAttributeAsString("idClassifica") != null && !"".equalsIgnoreCase(classFascModello.getAttributeAsString("idClassifica"))) ||
//					   (classFascModello.getAttributeAsString("idFolderFascicolo") != null && !"".equalsIgnoreCase(classFascModello.getAttributeAsString("idFolderFascicolo"))))) {							
//							record.getAttributeAsRecordList("listaClassFasc").remove(classFascModello);				
//					}											
//				}
//				if(classificazioneFascicolazioneForm != null && classificazioneFascicolazioneForm.getValuesAsRecord() != null &&
//						classificazioneFascicolazioneForm.getValuesAsRecord().getAttributeAsRecordList("listaClassFasc") != null &&
//				   !classificazioneFascicolazioneForm.getValuesAsRecord().getAttributeAsRecordList("listaClassFasc").isEmpty()) {
//				   RecordList lRecordListClassFasc = classificazioneFascicolazioneForm.getValuesAsRecord().getAttributeAsRecordList("listaClassFasc");
//				   if(lRecordListClassFasc != null) {
//					   for (int i = 0; i < lRecordListClassFasc.getLength(); i++) {
//							Record item = lRecordListClassFasc.get(i);
//							if(item != null && 
//							   ((item.getAttributeAsString("idClassifica") != null && !"".equalsIgnoreCase(item.getAttributeAsString("idClassifica"))) ||
//							   (item.getAttributeAsString("idFolderFascicolo") != null && !"".equalsIgnoreCase(item.getAttributeAsString("idFolderFascicolo"))))) {							
//									record.getAttributeAsRecordList("listaClassFasc").add(item);		
//							}									
//					   }
//				   }	
//				}
//			}
//		}
//	}
	
	/**
	 * Metodo che aggiunge i FolderCustom presenti nel modello a quelli in maschera
	 * in fase di protocollazione di una bozza.
	 */
	private void populateFolderCustomDaModello(Record recordModello) {
		if(isProtocollazioneDetailBozze()) {
			if(folderCustomForm != null && folderCustomForm.getValuesAsRecord() != null &&
			   folderCustomForm.getValuesAsRecord().getAttributeAsRecordList("listaFolderCustom") != null &&
			   !folderCustomForm.getValuesAsRecord().getAttributeAsRecordList("listaFolderCustom").isEmpty()) {
			    RecordList lRecordListFolder = folderCustomForm.getValuesAsRecord().getAttributeAsRecordList("listaFolderCustom");
			    if(lRecordListFolder != null) {
					for (int i = 0; i < lRecordListFolder.getLength(); i++) {
						Record recordFolderCustom = lRecordListFolder.get(i);
						if(recordFolderCustom != null && recordFolderCustom.getAttributeAsString("id") != null &&
						   !"".equalsIgnoreCase(recordFolderCustom.getAttributeAsString("id"))) {								
								recordModello.getAttributeAsRecordList("listaFolderCustom").add(recordFolderCustom);
						}
					}
			    }
			}
		}
	}
	
	/**
	 * Metodo che aggiunge i ClassFasc presenti nel modello a quelli in maschera
	 * in fase di protocollazione di una bozza.
	 */
	private void populateClassFascDaModello(Record recordModello) {
		if(isProtocollazioneDetailBozze()) {
			if(classificazioneFascicolazioneForm != null && classificazioneFascicolazioneForm.getValuesAsRecord() != null &&
			   classificazioneFascicolazioneForm.getValuesAsRecord().getAttributeAsRecordList("listaClassFasc") != null &&
			   !classificazioneFascicolazioneForm.getValuesAsRecord().getAttributeAsRecordList("listaClassFasc").isEmpty()) {
				RecordList lRecordListClassFasc = classificazioneFascicolazioneForm.getValuesAsRecord().getAttributeAsRecordList("listaClassFasc");	
				if(lRecordListClassFasc != null) {
					for (int i = 0; i < lRecordListClassFasc.getLength(); i++) {
						Record recordClassFasc = lRecordListClassFasc.get(i);
						if(recordClassFasc != null && 
						  ((recordClassFasc.getAttributeAsString("idClassifica") != null && !"".equalsIgnoreCase(recordClassFasc.getAttributeAsString("idClassifica"))) ||
						  (recordClassFasc.getAttributeAsString("idFolderFascicolo") != null && !"".equalsIgnoreCase(recordClassFasc.getAttributeAsString("idFolderFascicolo"))))) {							
								recordModello.getAttributeAsRecordList("listaClassFasc").add(recordClassFasc);						
						}
					}	
				}
			}
		}
	}
	
//	/**
//	 * Metodo che restituisce la mappa di tutte le UO di registrazione
//	 * 
//	 */
//	protected LinkedHashMap<String, String> getUoProtocollanteValueMap() {
//		return AurigaLayout.getUoRegistrazioneValueMap();
//	}

	/**
	 * Metodo che restituisce la mappa di tutte le UO di registrazione
	 * 
	 */
	protected LinkedHashMap<String, String> getUoProtocollanteValueMap() {
		if (getFlgTipoProv() != null && "E".equalsIgnoreCase(getFlgTipoProv())) {
			// Provengo da una protocollazione in entrata
			return AurigaLayout.getUoSpecificitaRegistrazioneEValueMap();
		} else if (getFlgTipoProv() != null) {
			// Provengo da una protocollazione in uscita o interna
			return AurigaLayout.getUoSpecificitaRegistrazioneUIValueMap();
		} else {
			// Provengo da una bozza
			return AurigaLayout.getUoRegistrazioneValueMap();
		}
			
	}
	
	/**
	 * Metodo che ritorna la mappa di tutte le UO di registrazione se non è settata una UO su cui è attiva la funzione di protocollo oppure una UO di lavoro predefinita, in tal caso ritornerà solo quella
	 * se ho un'unica UO di registrazione il metodo ovviamente restituirà solo quella
	 * 
	 */
	public LinkedHashMap<String, String> getSelezioneUoProtocollanteValueMap() {		
		return AurigaLayout.getSelezioneUoRegistrazioneValueMap(getFlgTipoProv());
	}

	/**
	 * Metodo che indica se mostrare o meno la select della U.O. protocollante
	 * 
	 */
	public boolean showUoProtocollanteSelectItem() {
		String flgTipoProv = getFlgTipoProv();
		if(isProtocollazioneBozza() && flgTipoProv != null && "U".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A()) {
			return false;
		}
		return /*isAbilToProt() && */getUoProtocollanteValueMap().size() > 1;
	}

	/**
	 * Metodo che restituisce la label della select U.O. protocollante
	 * 
	 */
	public String getTitleUoProtocollanteSelectItem() {
		return "<b>U.O. protocollante</b>";
	}
	
	/**
	 * Metodo per costruire la select della U.O. protocollante
	 * 
	 */
	protected void createUoProtocollanteSelectItem() {

		uoProtocollanteSelectItem = new SelectItem("uoProtocollante", getTitleUoProtocollanteSelectItem());
		uoProtocollanteSelectItem.setWidth((showModelliSelectItem() && showUoProtocollanteSelectItem()) ? 350 : 600);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			uoProtocollanteSelectItem.setCanFocus(true);		
		} 
		uoProtocollanteSelectItem.setWrapTitle(false);
		uoProtocollanteSelectItem.setDisplayField("descrizione");
		uoProtocollanteSelectItem.setValueField("idUo");
		uoProtocollanteSelectItem.setAllowEmptyValue(true);
		uoProtocollanteSelectItem.setRequired(true);
		uoProtocollanteSelectItem.setValueMap(getUoProtocollanteValueMap());
		if (getSelezioneUoProtocollanteValueMap().size() == 1) {
			String uoProtocollante = getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
			if (getUoProtocollanteValueMap() != null && getUoProtocollanteValueMap().containsKey(uoProtocollante)) {
				uoProtocollanteSelectItem.setValue(uoProtocollante);
				manageChangedUoProtocollante(uoProtocollante);
			}
		}
		uoProtocollanteSelectItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				manageChangedUoProtocollante((String) uoProtocollanteSelectItem.getValue());				
			}
		});
	}
	
	public void manageChangedUoProtocollante(String idUo) {
		if(mittentiItem != null && mittentiItem instanceof MittenteProtInternaItem) {
			((MittenteProtInternaItem) mittentiItem).manageOnChangedUoProtocollante(); // per ricaricare la select organigramma	
		}
		if(mittentiItem != null && mittentiItem instanceof MittenteProtUscitaItem) {
			((MittenteProtUscitaItem) mittentiItem).manageOnChangedUoProtocollante(); // per ricaricare la select organigramma
		}	
		if(destinatariItem != null && destinatariItem instanceof DestinatarioProtUscitaItem) {
			((DestinatarioProtUscitaItem) destinatariItem).manageOnChangedUoProtocollante(); // per ricaricare il mezzo di trasmissione
		}
		if(livelloRiservatezzaItem != null) {
			final String livelloRiservatezza = livelloRiservatezzaItem.getValueAsString();
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					livelloRiservatezzaItem.fetchData(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							if(livelloRiservatezza != null && !"".equals(livelloRiservatezza)) {
								boolean trovato = false;
								if (data.getLength() > 0) {						
									for (int i = 0; i < data.getLength(); i++) {
										String key = data.get(i).getAttribute("key");
										if (livelloRiservatezza.equals(key)) {
											trovato = true;
											break;
										}
									}
								}
								if (!trovato) {
									livelloRiservatezzaItem.setValue("");								
									livelloRiservatezzaItem.fireEvent(new ChangedEvent(livelloRiservatezzaItem.getJsObj()));
								}
							}
						}
					});
				}
			});		
		}	
	}

	/**
	 * Metodo che indica se mostrare o meno la barra inferiore contenente i
	 * bottoni delle azioni da dettaglio
	 * 
	 */
	public boolean showDetailToolStrip() {
		return true;
	}

	/**
	 * Metodo per costruire la barra inferiore contenente i bottoni delle azioni
	 * da dettaglio
	 * 
	 */
	protected void createDetailToolStrip() {

		createAnteprimaModelloButton();
		createStampaEtichettaButton();
		createFrecciaStampaEtichettaButton();
		createStampaCopertinaButton();
		createStampaMenuButton();
		createStampaRicevutaButton();
		createNuovaProtButton();
		createNuovaProtComeCopiaButton();
		createSalvaRegistraButton();
		createPresaInCaricoButton();
		createRestituisciButton();
		createSegnaComeVisionatoButton();
		createClassificiazioneFascicolazioneButton();
		createModificaButton();
		createFrecciaModificaButton();
		createRegAccessoCivicoButton();
		createModificaDatiRegButton();		
		createRevocaAttoButton();
		createProtocollazioneEntrataButton();
		createProtocollazioneUscitaButton();
		createProtocollazioneInternaButton();
		createInvioPECButton();
		createInvioMailRicevutaButton();
		createInvioPEOButton();
		createInviaRaccomandataButton();
		createInviaPostaPrioritariaButton();
		createVerificaRegistrazioneButton();
		createSalvaComeModelloButton();
		createReloadDetailButton();
		createUndoButton();
		createDownloadDocZipButton();
		createFrecciaDownloadZipButton();
		createSmistaButton();
		createSmistaCCButton();
		createInvioAlProtocolloButton();
		createFrecciaInvioAlProtocolloButton();
		createAssegnaCondividiButton();
		createFrecciaAssegnaCondividiButton();
//		createAssegnazioneButton();
//		createFrecciaAssegnazioneButton();
		createRispondiButton();
//		createCondivisioneButton();
//		createFrecciaCondivisioneButton();
		createOsservazioniNotificheButton();
		createFirmaButton();
		createApposizioneFirmaProtocollazioneButton();
		createVistoButton();
		createPubblicazioneTraspAmmButton();
		
		detailToolStrip = new ToolStrip();
		detailToolStrip.setName("bottoni");
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailToolStrip.setCanFocus(true);		
		} 
		detailToolStrip.addButton(anteprimaModelloButton);
		detailToolStrip.addButton(stampaEtichettaButton);
		detailToolStrip.addButton(frecciaStampaEtichettaButton);
		detailToolStrip.addButton(stampaMenuButton);
		detailToolStrip.addButton(stampaRicevutaButton);
		detailToolStrip.addButton(stampaCopertinaButton);
		detailToolStrip.addButton(invioMailRicevutaButton);
		detailToolStrip.addButton(nuovaProtButton);
		detailToolStrip.addButton(nuovaProtComeCopiaButton);
		detailToolStrip.addButton(salvaRegistraButton);
		detailToolStrip.addButton(presaInCaricoButton);
		detailToolStrip.addButton(restituisciButton);
		detailToolStrip.addButton(segnaComeVisionatoButton);
		detailToolStrip.addButton(classificazioneFascicolazioneButton);
		detailToolStrip.addButton(modificaButton);
		detailToolStrip.addButton(frecciaModificaButton);
		detailToolStrip.addButton(regAccessoCivicoButton);
		detailToolStrip.addButton(modificaDatiRegButton);		
		detailToolStrip.addButton(revocaAttoButton);
		detailToolStrip.addButton(protocollazioneEntrataButton);
		detailToolStrip.addButton(protocollazioneUscitaButton);
		detailToolStrip.addButton(protocollazioneInternaButton);
		detailToolStrip.addButton(invioPECButton);
		detailToolStrip.addButton(invioPEOButton);
		detailToolStrip.addButton(invioRaccomandataButton);
		detailToolStrip.addButton(invioPostaPrioritariaButton);
		detailToolStrip.addButton(verificaRegistrazioneButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);
		detailToolStrip.addButton(smistaButton);
		detailToolStrip.addButton(smistaCCButton);
		detailToolStrip.addButton(invioAlProtocolloButton);
		detailToolStrip.addButton(frecciaInvioAlProtocolloButton);
		detailToolStrip.addButton(assegnaCondividiButton);
		detailToolStrip.addButton(frecciaAssegnaCondividiButton);
//		detailToolStrip.addButton(assegnazioneButton);
//		detailToolStrip.addButton(frecciaAssegnazioneButton);
		detailToolStrip.addButton(rispondiButton);
//		detailToolStrip.addButton(condivisioneButton);
//		detailToolStrip.addButton(frecciaCondivisioneButton);
		detailToolStrip.addButton(osservazioniNotificheButton);
		detailToolStrip.addButton(salvaComeModelloButton);
		detailToolStrip.addButton(downloadDocZipButton);
		detailToolStrip.addButton(frecciaDownloadZipButton);
		detailToolStrip.addButton(apposizioneFirmaButton);
		detailToolStrip.addButton(apposizioneFirmaProtocollazioneButton);
		detailToolStrip.addButton(rifiutoApposizioneFirmaButton);
		detailToolStrip.addButton(apposizioneVistoButton);
		detailToolStrip.addButton(rifiutoApposizioneVistoButton);
		detailToolStrip.addButton(pubblicazioneTraspAmmButton);
	}

	/**
	 * Metodo che ritorna tutti i bottoni delle azioni da dettaglio
	 * 
	 */
	public List<ToolStripButton> getDetailToolStripButtons() {

		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();
		for (int i = 0; i < detailToolStrip.getMembers().length; i++) {
			if (detailToolStrip.getMembers()[i] instanceof ToolStripButton) {
				detailButtons.add((ToolStripButton) detailToolStrip.getMembers()[i]);
			}
		}
		return detailButtons;
	}
	
	/**
	 * Metodo che ritorna il titolo del bottone "Anteprima modello"
	 * 
	 */
	public String getTitleAnteprimaModelloButton() {
		return "Anteprima modello";
	}
	
	/**
	 * Metodo per costruire il bottone "Anteprima modello"
	 * 
	 */
	protected void createAnteprimaModelloButton() {

		anteprimaModelloButton = new DetailToolStripButton(getTitleAnteprimaModelloButton(), "protocollazione/generaDaModello.png");
		anteprimaModelloButton.setVisibility(Visibility.HIDDEN);
		anteprimaModelloButton.addClickHandler(new ClickHandler() {
	
			@Override
			public void onClick(ClickEvent event) {				
				generaDaModello(); //TODO vedere se usa solo ODT_FREEMARKER o tutti i tipi di modello
			}
		});	
	}

	/**
	 * Metodo per costruire il bottone "Stampa etichetta"
	 * 
	 */
	protected void createStampaEtichettaButton() {

		stampaEtichettaButton = new DetailToolStripButton(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO") ? "Timbra" : I18NUtil.getMessages().protocollazione_detail_stampaEtichettaButton_prompt(), "protocollazione/barcode.png");
		stampaEtichettaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickStampaEtichetta();
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa etichetta"
	 * 
	 */
	public void clickStampaEtichetta() {
		Record detailRecord = new Record(vm.getValues());
		clickStampaEtichetta(detailRecord);
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa etichetta" (richiamata dall'archivio)
	 * 
	 */
	public void clickStampaEtichetta(Record detailRecord) {
		if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa") || AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")){
			manageStampaEtichettaSenzaOpzStampa(detailRecord);
		} else {
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(detailRecord);
			stampaEtichettaPopup.show();
		}
	}
	
	public void clickStampaEtichettaSingoloAllegato() {

		Record detailRecord = new Record(vm.getValues());
		detailRecord.setAttribute("isSingoloAllegato", true);
		
		StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(detailRecord);
		stampaEtichettaPopup.show();
	}
	
	public void clickStampaEtichettaFaldone() {

		final String nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		Record detailRecord = new Record(vm.getValues());
		Record etichetta = new Record();
		String numeroProtocollo = detailRecord.getAttribute("nroProtocollo");
		String annoProtocollo = detailRecord.getAttribute("annoProtocollo");
		etichetta.setAttribute("testoFaldone", "PG" + numeroProtocollo + "/" + annoProtocollo);
		// Aggiunge degli 0 in modi da avere il numero doi protocollo di 7 cifre
		for (int i = numeroProtocollo.length() ; i < 7; i++) {
			numeroProtocollo = "0" + numeroProtocollo;
		}
		etichetta.setAttribute("barcodeFaldone", numeroProtocollo + "000" + annoProtocollo + "000");
		final Record[] etichette = new Record[1];
		etichette[0] = etichetta;
		StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, "1", new StampaEtichettaCallback() {

			@Override
			public void execute() {
			
			}
		});
		
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa etichetta"
	 * @param flgHideBarcode se valorizzato procedo alla stampa etichetta automatica post assegnazione
	 * 
	 */
	public void stampaEtichettaPostAssegnazione() {
		
		Record detailRecord = new Record(vm.getValues());
		
		if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa")){
			manageStampaEtichettaPostAssSenzaOpzStampa(detailRecord);
		} else {
			detailRecord.setAttribute("flgHideBarcode", true);
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(detailRecord);
			stampaEtichettaPopup.show();
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa etichetta"
	 * @param flgHideBarcode se valorizzato procedo alla stampa etichetta automatica post assegnazione
	 * 
	 */
	public void stampaEtichettaPostAssegnazioneRepertorio(String numeroNumerazioneSecondaria,String 
			tipoNumerazioneSecondaria ,Boolean isEtichettaFromModAss,Boolean isEtichettaFromRepe) {
		
		Record detailRecord = new Record(vm.getValues());
		
		detailRecord.setAttribute("numeroNumerazioneSecondaria", numeroNumerazioneSecondaria);
		detailRecord.setAttribute("tipoNumerazioneSecondaria", tipoNumerazioneSecondaria);
		detailRecord.setAttribute("isEtichettaFromModAss", isEtichettaFromModAss);
		detailRecord.setAttribute("isEtichettaFromRepe", isEtichettaFromRepe);
		if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa")){
			manageStampaEtichettaPostAssSenzaOpzStampa(detailRecord);
		} else {
			detailRecord.setAttribute("flgHideBarcode", true);
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(detailRecord);
			stampaEtichettaPopup.show();
		}
	}
	
	/**
	 * Gestione di stampa delle etichetta bypassando la scelta delle opzioni di stampa
	 */
	protected void manageStampaEtichettaPostAssSenzaOpzStampa(final Record record) {
		
		/**
		 * Viene verificato che sia stata selezionata una stampante in precedenza
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && 
				!"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			
			buildStampaEtichettaAutoPostAss(record, null);
		} else {
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

				@Override
				public void execute(String nomeStampante) {
					record.setAttribute("nomeStampante", nomeStampante);
					buildStampaEtichettaAutoPostAss(record,nomeStampante);
				}
			}, new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(),
							"", MessageType.ERROR));
				}
			});
		}	
	}
	
	/**
	 * Stampa dell'etichetta post-assegnazioni
	 */
	private void buildStampaEtichettaAutoPostAss(Record record, String nomeStampante) {
		
		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		lRecord.setAttribute("listaAllegati", record.getAttributeAsRecordList("listaAllegati"));
		if(nomeStampante == null || "".equals(nomeStampante)) {
			nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		}
		lRecord.setAttribute("nomeStampante", nomeStampante);
		lRecord.setAttribute("nroEtichette",  "1");		
		
		Record impostazioniStampa = AurigaLayout.getImpostazioneStampa();
		lRecord.setAttribute("flgPrimario", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario") : true);
		lRecord.setAttribute("flgAllegati", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati") : true);
		if(isFromCanaleSportello() || isFromCanalePregresso()) {
			lRecord.setAttribute("flgRicevutaXMittente", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgRicevutaXMittente") : true);
		}
		lRecord.setAttribute("flgHideBarcode", true);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){			
			lRecord.setAttribute("notazioneCopiaOriginale",  AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));
		}
		lRecord.setAttribute("isEtichettaFromRepe", record.getAttribute("isEtichettaFromRepe"));
		lRecord.setAttribute("isEtichettaFromModAss", record.getAttribute("isEtichettaFromModAss"));
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {

					@Override
					public void execute() {
					
					}
				});
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}
		});
	}
	
	protected void createFrecciaStampaEtichettaButton(){
		
		frecciaStampaEtichettaButton = new FrecciaDetailToolStripButton();
		frecciaStampaEtichettaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickFrecciaStampaEtichetta();
			}
		});
	}
	
	public void clickFrecciaStampaEtichetta() {
		
		Menu menu = new Menu();
		MenuItem standardItem = new MenuItem("Standard");
		standardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				clickStampaEtichetta();
			}
		});		
		menu.addItem(standardItem);
		
		MenuItem singoloAllegatoMenuItem = new MenuItem("Singolo allegato");
		singoloAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				clickStampaEtichettaSingoloAllegato();
			}
		});		
		menu.addItem(singoloAllegatoMenuItem);
		
		if (isAbilToStampaEtichetteFaldoni()) {
			MenuItem faldoneMenuItem = new MenuItem("Stampa etichetta per Cittadella");
			faldoneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickStampaEtichettaFaldone();
				}
			});		
			menu.addItem(faldoneMenuItem);
		}
		
		menu.showContextMenu();
	}

	/**
	 * Metodo per costruire il bottone "Stampa copertina"
	 * 
	 */
	protected void createStampaCopertinaButton() {

		stampaCopertinaButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt(),
				"stampa.png");
		stampaCopertinaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickStampaCopertina();
			}
		});
	}
	
	protected void createStampaMenuButton() {
		stampaMenuButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_stampe(),
				"stampa.png");
		stampaMenuButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Menu menu = new Menu();
				MenuItem stampaCopertina = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt());
				stampaCopertina.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
							clickStampaCopertina();
					}
				});		
				menu.addItem(stampaCopertina);
				
				MenuItem stampaRicevuta = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
				stampaRicevuta.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickStampaRicevuta();
					}
				});		
				menu.addItem(stampaRicevuta);
				
				menu.showContextMenu();
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa copertina"
	 * 
	 */
	public void clickStampaCopertina() {
		Record detailRecord = new Record(vm.getValues());
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");	
		lGwtRestDataSource.addParam("nomeModello", "COPERTINA_UD");
		lGwtRestDataSource.addParam("nomeFileStampa", "Copertina.pdf");
		lGwtRestDataSource.executecustom("creaStampa", detailRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					String nomeFilePdf = result.getAttribute("nomeFile");
					String uriFilePdf = result.getAttribute("uri");
					InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
					PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf, isEnablePreviewModal());
				}
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Stampa ricevuta"
	 * 
	 */
	protected void createStampaRicevutaButton() {

		stampaRicevutaButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt(),
				"stampa.png");
		stampaRicevutaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickStampaRicevuta();
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa ricevuta"
	 * 
	 */
	public void clickStampaRicevuta() {
		Record detailRecord = new Record(vm.getValues());
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");	
		lGwtRestDataSource.addParam("nomeModello", "RICEVUTA_REGISTRAZIONE_UD");
		lGwtRestDataSource.addParam("nomeFileStampa", "RicevutaRegistrazione.pdf");
		lGwtRestDataSource.executecustom("creaStampa", detailRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					String nomeFilePdf = result.getAttribute("nomeFile");
					String uriFilePdf = result.getAttribute("uri");
					InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
					PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf, isEnablePreviewModal());
				}
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa ricevuta"
	 * 
	 */
	public void getStampaRicevuta(final DSCallback callback) {
		Record detailRecord = new Record(vm.getValues());
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");	
		lGwtRestDataSource.addParam("nomeModello", "RICEVUTA_REGISTRAZIONE_UD");
		lGwtRestDataSource.addParam("nomeFileStampa", "RicevutaRegistrazione.pdf");
		lGwtRestDataSource.executecustom("creaStampa", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					callback.execute(response, null, new DSRequest());
				}
			}
		});
	}

	/**
	 * Metodo per costruire il bottone "Nuova protocollazione"
	 * 
	 */
	protected void createNuovaProtButton() {

		nuovaProtButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_nuovaProtButton_prompt(),
				"protocollazione/nuovaProt.png");
		nuovaProtButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickNuovaProt();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Nuova protocollazione"
	 * 
	 */
	public void clickNuovaProt() {

		Record detailRecord = new Record(vm.getValues());
		/**
		 * Quando seleziono una richiesta accesso civico semplice o generalizzata il documento viene tipizzato in automatico.
		 * L'azione di Nuova protocollazione non deve mantenere il tipo.
		 */
		if(detailRecord.getAttributeAsBoolean("flgRichAccCivSemplice") ||
				detailRecord.getAttributeAsBoolean("flgRichAccCivGeneralizzato")) {			
			Layout.selectPortlet("protocollazione_entrata");
			Canvas body = UserInterfaceFactory.getPortletLayout("protocollazione_entrata", null);
			if(body != null) {
				Layout.getOpenedPortlet("protocollazione_entrata").setBody(body);
			} else {
				closeParentWindow(instance);	
			}
		} else {
				
			Map<String, Object> initialValues = getNuovaProtInitialValues();
			if (initialValues != null && initialValues.size() > 0) {
				editNewRecord(initialValues);
			} else {
				editNewRecord();
			}
			setInitialValues();
			newMode();
		}
	}

	/**
	 * Metodo che costruisce la mappa di valori da preimpostare in seguito
	 * all'azione del bottone "Nuova protocollazione"
	 * 
	 */
	public Map<String, Object> getNuovaProtInitialValues() {

		Map<String, Object> initialValues = new HashMap<String, Object>();
		if (showAttributiDinamiciDoc()) {
			initialValues.put("tipoDocumento", tipoDocumento);
		}
		String mezzoTrasmissioneDefault = null; 
		String supportoOriginaleDefault = null;
		
		if(isRepertorioDetailEntrata()) {
			supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioEntrata");
		} else if(isRepertorioDetailInterno()) {
			supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioInterno");
		} else if(isRepertorioDetailUscita()) {
			supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoRepertorioUscita");
		} else if(isProtocollazioneDetailEntrata()){
			mezzoTrasmissioneDefault = AurigaLayout.getImpostazioniDocumento("mezzoTrasmissione");			
			if(isMezzoTrasmissioneDigitale(mezzoTrasmissioneDefault)) {
				supportoOriginaleDefault = "digitale";			
			} else if(isMezzoTrasmissioneCartaceo(mezzoTrasmissioneDefault)) {
				supportoOriginaleDefault = "cartaceo";				
			} else {
				supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoEntrata");
			}
		} else if(isProtocollazioneDetailUscita()){
			supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoUscita");
		} else if(isProtocollazioneDetailInterna()){
			supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoInterna");
		} else if(isProtocollazioneDetailBozze()){
			supportoOriginaleDefault = AurigaLayout.getImpostazioniDocumento("supportoBozze");
		} 
		if(mezzoTrasmissioneDefault != null && !"".equals(mezzoTrasmissioneDefault)) {
			initialValues.put("mezzoTrasmissione", mezzoTrasmissioneDefault);
		}
		if(supportoOriginaleDefault != null && !"".equals(supportoOriginaleDefault)) {
			initialValues.put("supportoOriginale", supportoOriginaleDefault);
		}
		if(dataEOraArrivoItem != null) {
			dataEOraArrivoItem.setDefaultChooserDate(new Date (getValueNow()));
		}
		if(dataEOraSpedizioneItem != null) {
			dataEOraSpedizioneItem.setDefaultChooserDate(new Date (getValueNow()));
		}
		return initialValues.size() > 0 ? initialValues : null;
	}
			
	public boolean isMezzoTrasmissioneDigitale(String mezzoTrasmissione) {
		return mezzoTrasmissione != null && ("PEC".equals(mezzoTrasmissione) || "PEO".equals(mezzoTrasmissione));		
	}

	public boolean isMezzoTrasmissioneCartaceo(String mezzoTrasmissione) {
		return mezzoTrasmissione != null && (isAbilRaccomandata(mezzoTrasmissione) || "L".equals(mezzoTrasmissione));
		//return mezzoTrasmissione != null && ("R".equals(mezzoTrasmissione) || "L".equals(mezzoTrasmissione));
	}

	/**
	 * Metodo per costruire il bottone "Nuova protocollazione come copia"
	 * 
	 */
	protected void createNuovaProtComeCopiaButton() {

		nuovaProtComeCopiaButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_nuovaProtComeCopiaButton_prompt(),
				"protocollazione/nuovaProtComeCopia.png");
		nuovaProtComeCopiaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickNuovaProtComeCopia(null);
			}
		});
	}
	
	public Map getValuesNuovaProtComeCopia() {
		Record lRecordValues = new Record(vm.getValues());
		// Assegnazione
		RecordList listaAssegnazioniSalvate = new RecordList();
		if(assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
			listaAssegnazioniSalvate = assegnazioneSalvataItem.getValueAsRecordList();
		}
		RecordList listaAssegnazioni = lRecordValues.getAttributeAsRecordList("listaAssegnazioni");
		RecordList listaAssegnazioniNew = new RecordList();
		if(listaAssegnazioniSalvate != null) {
			listaAssegnazioniNew.addList(listaAssegnazioniSalvate.toArray());
		}
		if(listaAssegnazioni != null) {
			listaAssegnazioniNew.addList(listaAssegnazioni.toArray());
		}
		lRecordValues.setAttribute("listaAssegnazioni", listaAssegnazioniNew);
		lRecordValues.setAttribute("listaAssegnazioniSalvate", new RecordList());
		// Condivisione
		RecordList listaDestInvioCCSalvati = new RecordList();
		if(condivisioneSalvataItem.getValueAsRecordList() != null && condivisioneSalvataItem.getValueAsRecordList().getLength() > 0 && condivisioneSalvataItem.hasValue()) {
			listaDestInvioCCSalvati = condivisioneSalvataItem.getValueAsRecordList();
		}
		RecordList listaDestInvioCC = lRecordValues.getAttributeAsRecordList("listaDestInvioCC");
		RecordList listaDestInvioCCNew = new RecordList();
		if(listaDestInvioCCSalvati != null) {
			listaDestInvioCCNew.addList(listaDestInvioCCSalvati.toArray());
		}
		if(listaDestInvioCC != null) {
			listaDestInvioCCNew.addList(listaDestInvioCC.toArray());
		}
		lRecordValues.setAttribute("listaDestInvioCC", listaDestInvioCCNew);
		lRecordValues.setAttribute("listaDestInvioCCSalvati", new RecordList());
		
		if(dataEOraArrivoItem != null) {
			dataEOraArrivoItem.setDefaultChooserDate(new Date());
		}
		if(dataEOraSpedizioneItem != null) {
			dataEOraSpedizioneItem.setDefaultChooserDate(new Date());
		}
		
		return lRecordValues.toMap();
	}

	/**
	 * Metodo che implementa l'azione del bottone "Nuova protocollazione come
	 * copia"
	 * 
	 */
	public void clickNuovaProtComeCopia(final ServiceCallback<Record> callback) {
		
		final Map values = getValuesNuovaProtComeCopia();
		NuovaProtComeCopiaPopup nuovaProtComeCopiaPopup = new NuovaProtComeCopiaPopup(instance) {

			@Override
			public void onClickOkButton(String flgTipoProvTo, final boolean flgNoEsibente, final boolean flgNoMittenti,
					final boolean flgNoDestinatari, final boolean flgNoAltriAssegnatari, final boolean flgNoOggetto,
					final boolean flgNoPrimario, final boolean flgNoAllegati, final boolean flgNoFileAllegati,
					final boolean flgNoDocumentiCollegati, final boolean flgNoFascicolazione) {
				if (isRegistroFattureDetail()) {
					((RegistroFattureDetail) instance).nuovaProtComeCopia(values, getAttributiDinamiciDocForLoad(),
							true, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, true, flgNoAllegati,
							flgNoFileAllegati, true, flgNoFascicolazione, false, callback);
					// rimango sulla stessa maschera quindi devo aggiornare il
					// titolo della portlet
					redrawTitleOfPortlet();
				}  else if (isRepertorioDetailEntrata()) {
					nuovaProtComeCopiaRepertorio("E",callback, values, flgNoMittenti, flgNoDestinatari,
							flgNoAltriAssegnatari, flgNoOggetto, flgNoAllegati, flgNoFileAllegati,
							flgNoDocumentiCollegati, flgNoFascicolazione);
				} else if (isRepertorioDetailInterno()) {
					nuovaProtComeCopiaRepertorio("I",callback, values, flgNoMittenti, flgNoDestinatari,
							flgNoAltriAssegnatari, flgNoOggetto, flgNoAllegati, flgNoFileAllegati,
							flgNoDocumentiCollegati, flgNoFascicolazione);
				} else if (isRepertorioDetailUscita()) {
					nuovaProtComeCopiaRepertorio("U",callback, values, flgNoMittenti, flgNoDestinatari,
							flgNoAltriAssegnatari, flgNoOggetto, flgNoAllegati, flgNoFileAllegati,
							flgNoDocumentiCollegati, flgNoFascicolazione);
				} else if (getFlgTipoProv() != null && ("E".equals(getFlgTipoProv()) || "I".equals(getFlgTipoProv()))
						&& "U".equals(flgTipoProvTo)) {
					final Record recordDettaglio = new Record(values);					
					if (!Layout.isOpenedPortlet("protocollazione_uscita")) {
						ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil
								.buildProtocollazioneDetailUscita(recordDettaglio);
						protocollazioneDetailUscita.nuovaProtComeCopia(values, getAttributiDinamiciDocForLoad(),
								true, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario,
								flgNoAllegati, flgNoFileAllegati, flgNoDocumentiCollegati, flgNoFascicolazione, false, null);
						Layout.addPortlet("protocollazione_uscita", protocollazioneDetailUscita);
					} else {
						final Portlet portlet = Layout.getOpenedPortlet("protocollazione_uscita");
						SC.ask("La finestra di \"" + portlet.getTitle()
								+ "\" risulta già aperta. Tutti i dati andranno persi. Continuare comunque l'operazione?",
								new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											Layout.selectPortlet("protocollazione_uscita");
											ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil
													.buildProtocollazioneDetailUscita(recordDettaglio);
											protocollazioneDetailUscita.nuovaProtComeCopia(values,
													getAttributiDinamiciDocForLoad(), true, flgNoMittenti, flgNoDestinatari,
													flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati,
													flgNoFileAllegati, flgNoDocumentiCollegati, flgNoFascicolazione, false, null);
											portlet.setBody(protocollazioneDetailUscita);
										}
									}
								});
					}
				} else {
					nuovaProtComeCopia(flgNoEsibente, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, flgNoOggetto, true,
							flgNoAllegati, flgNoFileAllegati, flgNoDocumentiCollegati, flgNoFascicolazione, false, callback);
					// rimango sulla stessa maschera quindi devo aggiornare il
					// titolo della portlet
					if(flgTipoProvTo != null && !"".equals(flgTipoProvTo)){
						if(getLayout() != null) {
							String verso = null;
							if("U".equals(flgTipoProvTo))
								verso = "in uscita";
							if("I".equals(flgTipoProvTo))
								verso = "interno";
							if("E".equals(flgTipoProvTo))
								verso = "in entrata";
							Layout.changeTitleOfPortlet(getLayout().getNomePortlet(), "Nuovo protocollo "+ verso);
						}
					}else{
						redrawTitleOfPortlet();
					}
				}
			}
		};
		nuovaProtComeCopiaPopup.show();
	}

	/**
	 * Metodo che fa la copia di se stesso sulla stessa maschera, escludendo i
	 * campi con i relativi flag a true
	 * 
	 */
	public void nuovaProtComeCopia(final boolean flgNoEsibente, final boolean flgNoMittenti, final boolean flgNoDestinatari,
			final boolean flgNoAltriAssegnatari, final boolean flgNoOggetto, final boolean flgNoPrimario,
			final boolean flgNoAllegati, final boolean flgNoFileAllegati, final boolean flgNoDocumentiCollegati, 
			final boolean flgNoFascicolazione, final boolean flgNoAttributiCustom, ServiceCallback<Record> callback) {

		final Map values = getValuesNuovaProtComeCopia();
		nuovaProtComeCopia(values, getAttributiDinamiciDocForLoad(), flgNoEsibente, flgNoMittenti, flgNoDestinatari,
				flgNoAltriAssegnatari, flgNoOggetto, flgNoPrimario, flgNoAllegati, flgNoFileAllegati, flgNoDocumentiCollegati,
				flgNoFascicolazione, flgNoAttributiCustom, callback);
	}

	/**
	 * Metodo che preimposta i valori dei campi a maschera con quelli in input,
	 * escludendo quelli con i relativi flag a true
	 * 
	 */
	public void nuovaProtComeCopia(Map values, Map attributiDinamiciDoc, final boolean flgNoEsibente, final boolean flgNoMittenti,
			final boolean flgNoDestinatari, final boolean flgNoAltriAssegnatari, final boolean flgNoOggetto,
			final boolean flgNoPrimario, final boolean flgNoAllegati, final boolean flgNoFileAllegati, final boolean flgNoDocumentiCollegati,
			final boolean flgNoFascicolazione, final boolean flgNoAttributiCustom, ServiceCallback<Record> callback) {
		
		if(showModelliSelectItem()) {
			if(modelliSelectItem != null) {
				modelliSelectItem.clearValue();
			}
		}

		if (isAltraNumerazione()) {
			String tipoProtocollo = (String) values.get("tipoProtocollo");
			if (tipoProtocollo != null && tipoProtocollo.equalsIgnoreCase("PG")) {
				values.put("protocolloGenerale", true);
			} else {
				values.put("protocolloGenerale", false);
			}
		}
		String nomeFilePrimario = (String) values.get("nomeFilePrimario");
		String uriFilePrimario = (String) values.get("uriFilePrimario");
		Boolean remoteUriFilePrimario = values.get("remoteUriFilePrimario") != null && (Boolean) values.get("remoteUriFilePrimario");
		String idAttachEmailPrimario = (String) values.get("idAttachEmailPrimario");
		Map infoFilePrimario = (Map) values.get("infoFile");
		removeValuesToSkipInNuovaProtComeCopia(values);		
		// chiamo il metodo editNewRecord() della superclasse o ricarico gli
		// attributi dinamici due volte
		super.editNewRecord();
		setInitialValues();
		Map defaultValues = vm.getValues();
		if (flgNoEsibente) {
			values.remove("listaEsibenti");
			if (defaultValues.get("listaEsibenti") != null) {
				values.put("listaEsibenti", defaultValues.get("listaEsibenti"));
			}
		}
		if (flgNoMittenti) {
			values.remove("listaMittenti");
			if (defaultValues.get("listaMittenti") != null) {
				values.put("listaMittenti", defaultValues.get("listaMittenti"));
			}
			ArrayList<Map> listaEsibenti = (ArrayList<Map>) values.get("listaEsibenti");
			if (listaEsibenti != null) {
				ArrayList<Map> newListaEsibenti = new ArrayList<Map>();
				for (Map esibente : listaEsibenti) {
					Map newEsibente = new HashMap();
					newEsibente.putAll(esibente);
					newEsibente.put("flgAncheMittente", false);
					newListaEsibenti.add(newEsibente);					
				}
				values.put("listaEsibenti", newListaEsibenti);
			}
		}
		if (flgNoDestinatari) {
			values.remove("listaDestinatari");
			if (defaultValues.get("listaDestinatari") != null) {
				values.put("listaDestinatari", defaultValues.get("listaDestinatari"));
			}
		}
		if (flgNoAltriAssegnatari) {
			values.remove("listaAssegnazioni");
			if (defaultValues.get("listaAssegnazioni") != null) {
				values.put("listaAssegnazioni", defaultValues.get("listaAssegnazioni"));
			}
			values.remove("flgPreviaConfermaAssegnazione");
			values.remove("utentiAbilCPA");
			values.remove("idUserConfermaAssegnazione");
			values.remove("listaDestInvioCC");
			if (defaultValues.get("listaDestInvioCC") != null) {
				values.put("listaDestInvioCC", defaultValues.get("listaDestInvioCC"));
			}
		}
		if (flgNoOggetto) {
			values.remove("oggetto");
		}
		if (flgNoAllegati) {
			values.remove("listaAllegati");
			if (defaultValues.get("listaAllegati") != null) {
				values.put("listaAllegati", defaultValues.get("listaAllegati"));
			}
		} else if (flgNoFileAllegati) {
			ArrayList<Map> listaAllegati = (ArrayList<Map>) values.get("listaAllegati");
			if (listaAllegati != null) {
				ArrayList<Map> newListaAllegati = new ArrayList<Map>();
				for (Map allegato : listaAllegati) {
					if ((allegato.get("listaTipiFileAllegato") != null
							&& !"".equals(allegato.get("listaTipiFileAllegato")))
							|| (allegato.get("descrizioneFileAllegato") != null
									&& !"".equals(allegato.get("descrizioneFileAllegato")))) {
						Map newAllegato = new HashMap();
						newAllegato.put("idTipoFileAllegato", allegato.get("idTipoFileAllegato"));
						newAllegato.put("descTipoFileAllegato", allegato.get("descTipoFileAllegato"));
						newAllegato.put("listaTipiFileAllegato", allegato.get("listaTipiFileAllegato"));
						newAllegato.put("descrizioneFileAllegato", allegato.get("descrizioneFileAllegato"));
						newListaAllegati.add(newAllegato);
					}
				}
				values.put("listaAllegati", newListaAllegati);
			}
		}
		if (flgNoDocumentiCollegati) {
			values.remove("listaDocumentiCollegati");
			if (defaultValues.get("listaDocumentiCollegati") != null) {
				values.put("listaDocumentiCollegati", defaultValues.get("listaDocumentiCollegati"));
			}
		} 
		if (flgNoFascicolazione) {
			values.remove("listaClassFasc");
			if (defaultValues.get("listaClassFasc") != null) {
				values.put("listaClassFasc", defaultValues.get("listaClassFasc"));
			}
			values.remove("listaFolderCustom");
			if (defaultValues.get("listaFolderCustom") != null) {
				values.put("listaFolderCustom", defaultValues.get("listaFolderCustom"));
			}
		}
		
		if (values.get("regNumSecondariaDesGruppo") != null){
			values.put("repertorio", values.get("regNumSecondariaDesGruppo"));
		}
		
		editNewRecord(values);
		vm.setValues(values);
		if (!flgNoPrimario) {
			if (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && uriFilePrimario != null
					&& !"".equals(uriFilePrimario)) {
				RecordList lRecordListAllegati = fileAllegatiForm.getValuesAsRecord()
						.getAttributeAsRecordList("listaAllegati");
				if (lRecordListAllegati == null || lRecordListAllegati.getLength() == 0) {
					lRecordListAllegati = new RecordList();
				}
				Record lRecordAllegato = new Record();
				lRecordAllegato.setAttribute("nomeFileAllegato", nomeFilePrimario);
				lRecordAllegato.setAttribute("uriFileAllegato", uriFilePrimario);
				lRecordAllegato.setAttribute("remoteUri", remoteUriFilePrimario);
				lRecordAllegato.setAttribute("idAttachEmail", idAttachEmailPrimario);
				lRecordAllegato.setAttribute("infoFile", infoFilePrimario);
				lRecordListAllegati.addAt(lRecordAllegato, 0);
				Record lRecord = new Record();
				lRecord.setAttribute("listaAllegati", lRecordListAllegati);
				fileAllegatiForm.setValues(lRecord.toMap());
				if(fileAllegatiItem != null) {
					if(fileAllegatiItem instanceof AllegatiGridItem) {
						((AllegatiGridItem)fileAllegatiItem).resetCanvasChanged();
					} else if(fileAllegatiItem instanceof AllegatiItem) {
						((AllegatiItem)fileAllegatiItem).resetCanvasChanged();
					}
				}
			}
		}
		if (!flgNoAttributiCustom) {
			setAttributiDinamiciDocDaCopiare(attributiDinamiciDoc);
		}
		if(callback != null) {
			callback.execute(null);
		} else {
			// solo quando la callback è null richiamo il newComeCopiaMode() del dettaglio
			// se invece arrivo da un layout (archivio o scrivania) la callback sarà valorizzata e verrà chiamato il newComeCopiaMode() del layout, non devo chiamare anche quello del dettaglio altrimenti fa casino
			newComeCopiaMode();	
		}
	}

	/**
	 * Metodo che rimuove dalla mappa le proprietà relative ai campi che non si
	 * possono copiare nella nuova maschera, o nel modello da salvare
	 * 
	 */
	
	public void removeValuesToSkipInModello(Map<String, Object> values) {				
		removeValuesToSkipInCopia(values, false);
	}
	
	public void removeValuesToSkipInNuovaProtComeCopia(Map<String, Object> values) {
		values.put("idUdNuovoComeCopia", values.get("idUd"));
		removeValuesToSkipInCopia(values, true);
	}
	
	private void removeValuesToSkipInCopia(Map<String, Object> values, boolean isNuovoProtComeCopia) {			
		values.remove("idUd");
		values.remove("idDocPrimario");
		values.remove("rowidDoc");
		values.remove("codStatoDett");
		values.remove("codStato");
		values.remove("idProcess");
		values.remove("estremiProcess");
		values.remove("ruoloSmistamentoAtto");		
		values.remove("idEmailArrivo");
		values.remove("casellaIsPEC");
		values.remove("emailInviataFlgPEC");
		values.remove("emailInviataFlgPEO");
		values.remove("emailArrivoInteroperabile");
		values.remove("emailProvIndirizzo");
		values.remove("emailProvFlgPEC");
		values.remove("emailProvFlgCasellaIstituz");
		values.remove("emailProvFlgDichIPA");
		values.remove("emailProvGestorePEC");
		values.remove("inviataMailInteroperabile");
		values.remove("ricEccezioniInterop");
		values.remove("ricAggiornamentiInterop");
		values.remove("ricAnnullamentiInterop");
		values.remove("segnatura");
		values.remove("siglaProtocollo");
		values.remove("tipoProtocollo");
		values.remove("nroProtocollo");
		values.remove("subProtocollo");
		values.remove("stato");
		values.remove("dataProtocollo");
		values.remove("annoProtocollo");		
		values.remove("datiAnnullamento");
		values.remove("datiRichAnnullamento");
		values.remove("desUserProtocollo");
		values.remove("desUOProtocollo");
		values.remove("estremiRepertorioPerStruttura");		
		values.remove("siglaNumerazioneSecondaria");
		values.remove("numeroNumerazioneSecondaria");
		values.remove("subNumerazioneSecondaria");
		values.remove("dataRegistrazioneNumerazioneSecondaria");
		values.remove("estremiAttoLiquidazione");
		values.remove("idUdLiquidazione");
		values.remove("dtEsecutivita");
		values.remove("flgImmediatamenteEseg");
		//TODO bisogna rimuovere anche i dati delle altre numerazioni
		values.remove("abilAssegnazioneSmistamento");
		values.remove("presenzaDocCollegati");
		values.remove("conDatiAnnullati");
		values.remove("codContratti");
		values.remove("flgFirmeCompilateContratti");
		values.remove("flgDocContrattiBarcode");
		// tolgo i dati relativi al file primario (in caso poi lo riaggiungo tra gli allegati)
		values.remove("idAttachEmailPrimario");
		values.remove("uriFilePrimario");
		values.remove("infoFile");
		values.remove("impronta");
		values.remove("remoteUriFilePrimario");
		values.remove("isDocPrimarioChanged");
		values.remove("nomeFilePrimarioOrig");
		values.remove("nomeFilePrimarioTif");
		values.remove("uriFilePrimarioTif");
		values.remove("nomeFileVerPreFirma");
		values.remove("uriFileVerPreFirma");
		values.remove("improntaVerPreFirma");
		values.remove("infoFileVerPreFirma");		
		values.remove("nroLastVer");
		values.remove("nomeFilePrimario");		
		values.remove("flgSostituisciVerPrec");		
		values.remove("flgOriginaleCartaceo");
		values.remove("flgCopiaSostitutiva");
		values.remove("flgTimbratoFilePostReg");
		values.remove("attivaTimbroTipologia");
		values.remove("opzioniTimbro");
		values.remove("flgTimbraFilePostReg");
		values.remove("flgNoPubblPrimario");
		values.remove("listaFilePrimarioVerPubbl");
		values.remove("flgDatiSensibili");
		values.remove("tipoBarcodePrimario");
		values.remove("barcodePrimario");
		values.remove("energiaGasPrimario");
		values.remove("tipoSezioneContrattoPrimario");
		values.remove("codContrattoPrimario");
		values.remove("flgPresentiFirmeContrattoPrimario");
		values.remove("flgFirmeCompleteContrattoPrimario");
		values.remove("nroFirmePrevisteContrattoPrimario");
		values.remove("nroFirmeCompilateContrattoPrimario");		
		values.remove("flgIncertezzaRilevazioneFirmePrimario");		
		values.put("filePrimarioOmissis", new HashMap());		
		values.remove("docPressoCentroPosta");		
		if(isNuovoProtComeCopia) {
			ArrayList<Map> listaMittenti = (ArrayList<Map>) values.get("listaMittenti");
			if (listaMittenti != null) {
				ArrayList<Map> newListaMittenti = new ArrayList<Map>();
				for (Map mitt : listaMittenti) {
					Map newMitt = new HashMap();
					newMitt.putAll(mitt);
					newMitt.put("idAssegnatario", null);
					newMitt.put("flgAssegnaAlMittenteXNuovaProtComeCopia", (Boolean) mitt.get("flgAssegnaAlMittente"));					
//					newMitt.put("flgSelXAssegnazione", null);
//					newMitt.put("flgAssegnaAlMittente", null); /**Decommentare se non si vuole tenere in memoria il valore del check "effettua assegnazione"*/
//					newMitt.put("opzioniInvio", null);
					newListaMittenti.add(newMitt);	
				}
				values.put("listaMittenti", newListaMittenti);
			}
			ArrayList<Map> listaDestinatari = (ArrayList<Map>) values.get("listaDestinatari");
			if (listaDestinatari != null) {
				ArrayList<Map> newListaDestinatari = new ArrayList<Map>();
				for (Map dest : listaDestinatari) {
					Map newDest = new HashMap();
					newDest.putAll(dest);
					newDest.put("idAssegnatario", null);
					newDest.put("idDestInvioCC", null);
					newDest.put("flgAssegnaAlDestinatarioXNuovaProtComeCopia", (Boolean) dest.get("flgAssegnaAlDestinatario"));										
//					newDest.put("flgSelXAssegnazione", null);
//					newDest.put("flgAssegnaAlDestinatario", null); /**Decommentare se non si vuole tenere in memoria il valore del check "effettua assegnazione"*/
//					newDest.put("opzioniInvio", null);
					newListaDestinatari.add(newDest);	
				}
				values.put("listaDestinatari", newListaDestinatari);
			}
			ArrayList<Map> listaAllegati = (ArrayList<Map>) values.get("listaAllegati");
			if (listaAllegati != null) {
				ArrayList<Map> newListaAllegati = new ArrayList<Map>();
				for (Map allegato : listaAllegati) {
					allegato.put("flgTimbratoFilePostReg", false);
					allegato.put("tipoBarcodeContratto", null);
					allegato.put("barcodeContratto", null);
					allegato.put("energiaGasContratto", null);
					allegato.put("tipoSezioneContratto", null);
					allegato.put("codContratto", null);
					allegato.put("flgPresentiFirmeContratto", null);
					allegato.put("flgFirmeCompleteContratto", null);
					allegato.put("nroFirmePrevisteContratto", null);
					allegato.put("nroFirmeCompilateContratto", null);
					allegato.put("flgIncertezzaRilevazioneFirmeContratto", null);
					newListaAllegati.add(allegato);
				}
				values.put("listaAllegati", newListaAllegati);
			}
			// se sto facendo un nuovo come copia tolgo i dati relativi al protocollo ricevuto (due registrazioni non possono mai averlo uguale)
			values.remove("rifOrigineProtRicevuto");
			values.remove("nroProtRicevuto");
			values.remove("annoProtRicevuto");
			values.remove("dataProtRicevuto");		
			values.remove("dataEOraArrivo");
			values.remove("dataEOraSpedizione");
//			values.remove("mezzoTrasmissione"); // questo lo tengo o mi blocca tutto in modalità wizard
			values.remove("nroRaccomandata");   // questo va tolto perchè non sarà mai uguale, mentre la data e il numero lista li tengo
//			values.remove("dataRaccomandata");
//			values.remove("nroListaRaccomandata"); 		
			// se sto facendo un nuovo come copia tolgo i dati relativi al registro di emergenza (due registrazioni non possono mai averlo uguale)
			values.remove("rifRegEmergenza");
			values.remove("nroRegEmergenza");
			values.remove("dataRegEmergenza");
			values.remove("idUserRegEmergenza");
			values.remove("idUoRegEmergenza");
		} else {
			// se invece sto salvando un modello
			// tolgo tipologia documentale e tutti i dati correlati perchè ci sono modelli differenziati per tipo
			// oppure soluzione alternativa differenziare i modelli per tipo doc. (vedi ATTIVA_MODELLI_X_TIPO_DOC)
//			values.remove("tipoDocumento");
//			values.remove("nomeTipoDocumento");			
//			values.remove("codCategoriaAltraNumerazione");
//			values.remove("siglaAltraNumerazione");	
			// tolgo anche tutti i file dagli allegati (tengo solo tipo e descrizione)
			ArrayList<Map> listaAllegati = (ArrayList<Map>) values.get("listaAllegati");
			if (listaAllegati != null) {
				ArrayList<Map> newListaAllegati = new ArrayList<Map>();
				for (Map allegato : listaAllegati) {
					if ((allegato.get("listaTipiFileAllegato") != null && !"".equals(allegato.get("listaTipiFileAllegato"))) || 
						(allegato.get("descrizioneFileAllegato") != null && !"".equals(allegato.get("descrizioneFileAllegato")))) {
						Map newAllegato = new HashMap();
						newAllegato.put("idTipoFileAllegato", allegato.get("idTipoFileAllegato"));
						newAllegato.put("descTipoFileAllegato", allegato.get("descTipoFileAllegato"));
						newAllegato.put("listaTipiFileAllegato", allegato.get("listaTipiFileAllegato"));
						newAllegato.put("descrizioneFileAllegato", allegato.get("descrizioneFileAllegato"));
						newListaAllegati.add(newAllegato);
					}
				}
				values.put("listaAllegati", newListaAllegati);
			}
		}
		values.remove("dataArrivoProtocolloEditabile");	
		values.remove("dataArrivoProtocollo");	
		values.remove("dataSpedizioneCartaceo");	
	}

	/**
	 * Metodo che restituisce la mappa di valori da preimpostare in seguito
	 * all'azione del bottone "Nuova protocollazione come copia"
	 * 
	 */
	public Map<String, Object> getNuovaProtComeCopiaValues(Map<String, Object> values) {
		return values;
	}

	/**
	 * Metodo che restituisce il titolo da dare al bottone "Registra"
	 * 
	 */
	public String getTitleRegistraButton() {
		return I18NUtil.getMessages().protocollazione_detail_registraButton_prompt();
	}

	/**
	 * Metodo che restituisce l'icona da dare al bottone "Registra"
	 * 
	 */
	public String getIconRegistraButton() {
		return "buttons/save.png";
	}

	/**
	 * Metodo che restituisce il titolo da dare al bottone "Salva"
	 * 
	 */
	public String getTitleSalvaButton() {
		return I18NUtil.getMessages().saveButton_prompt();
	}

	/**
	 * Metodo che restituisce l'icona da dare al bottone "Salva"
	 * 
	 */
	public String getIconSalvaButton() {
		return "buttons/save.png";
	}

	/**
	 * Metodo per costruire il bottone "Salva"/"Registra"
	 * 
	 */
	protected void createSalvaRegistraButton() {

		salvaRegistraButton = new DetailToolStripButton(getTitleRegistraButton(), getIconRegistraButton());
		salvaRegistraButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						
						clickSalvaRegistraSincronizzato();
					}
				});
			}
		});
	}
	
	private synchronized void clickSalvaRegistraSincronizzato() {
		
		if (abilSaveButton) {
			abilSaveButton = false;
		
			new Timer() {

				public void run() {
					// Riabilito il pulsante dopo che è passato il tempo predefinito.
					abilSaveButton = true;
				}
			}.schedule(5000); // Tempo dopo il quale si avvia l'esecuzione
		
			clickSalvaRegistra();
		}	
	}

	/**
	 * Metodo che implementa l'azione del bottone "Salva"/"Registra"
	 * 
	 */
	public void clickSalvaRegistra() {
		if (isModificaDatiExtraIter() ? validateAttributiDinamiciDoc() : validate()) {
			controlloObbligatorioNoteXRegSenzaFilePrimario(new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					/** Se siamo nella pagina di protocollo proposto con le scansioni associate di A2A non mostro i motivi di variazione ma li passo fissi*/
					if("caricaDatiImgAssoc".equalsIgnoreCase(editMode)) {
						checkPropagaRiservatezzaFascicoliCartelleAContenuti(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								clickRegistra("Associazione manuale scansioni");
							}
						});
					}
					else if (isModificaDatiReg()) {
						ModificaDatiRegPopup modificaDatiRegPopup = new ModificaDatiRegPopup(getMotivoVariazione()) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								checkPropagaRiservatezzaFascicoliCartelleAContenuti(new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										clickRegistra(motivoItem.getValueAsString());
										callback.execute(new DSResponse(), null, new DSRequest());
									}
								});
							}
							
							@Override
							public void onClickAnnullaButton() {
								abilSaveButton = true; //Riabilito il pulsante di salvataggio
								reopenAllSections();	
							}
						};
						modificaDatiRegPopup.show();
					} else {
						checkPropagaRiservatezzaFascicoliCartelleAContenuti(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								clickRegistra(null);
							}
						});
					}
				}				
			});			
		} else {
			abilSaveButton = true; //Riabilito il pulsante di salvataggio
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
		
	public void controlloObbligatorioNoteXRegSenzaFilePrimario(final DSCallback callback) {
		// se è una registrazione ed è obbligatorio inserire le note sulla mancanza del file primario
		String noteMancanzaFile = (String) noteMancanzaFileItem.getValue();
		boolean hasFilePrimario = filePrimarioForm.getValue("uriFilePrimario") != null && !"".equals(filePrimarioForm.getValue("uriFilePrimario"));
		RecordList listaAllegati = fileAllegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
		boolean hasAllegati = listaAllegati != null && listaAllegati.get(0) != null && listaAllegati.get(0).getAttribute("uriFileAllegato") != null && !"".equals(listaAllegati.get(0).getAttributeAsString("uriFileAllegato"));
		if(hasFilePrimario) {
			if(!validateDataArrivoProtocolloA2A(hasFilePrimario, hasAllegati)) {
				if(isUOAbilitatoScansioneMassiva()) {
					Layout.addMessage(new MessageBean("Data di arrivo obbligatoria (dato che non hai optato per la scansione massiva)", "", MessageType.ERROR));
				} else {
					Layout.addMessage(new MessageBean("Data di arrivo obbligatoria", "", MessageType.ERROR));
				}
			} else { 
				noteMancanzaFileItem.setValue("");
				callback.execute(new DSResponse(), null, new DSRequest());	
			}
		} else {
			if(AurigaLayout.isAttivoClienteA2A()) {
				if(!validateDataArrivoProtocolloA2A(hasFilePrimario, hasAllegati)) {
					if(isUOAbilitatoScansioneMassiva()) {
						Layout.addMessage(new MessageBean("Data di arrivo obbligatoria (dato che non hai optato per la scansione massiva)", "", MessageType.ERROR));
					} else {
						Layout.addMessage(new MessageBean("Data di arrivo obbligatoria", "", MessageType.ERROR));
					}
				} else {		
					//controllo introdotto per A2A, se non ha primario, allegati ed è un repertorio/protocollo in entrata e la UO non è abilitata alla scansione, mostro popup di note mancanza allegato
					if((isRepertorioDetailEntrata() || isProtocollazioneDetailEntrata()) && !isUOAbilitatoScansioneMassiva() && !hasAllegati) {
						mostraPopupNoteMancanzaFile(callback);
					} else {
						callback.execute(new DSResponse(), null, new DSRequest());
					}		
				}
			} else {
				if((noteMancanzaFile == null || "".equals(noteMancanzaFile.trim())) && (getFlgTipoProv() != null && !"".equals(getFlgTipoProv())) && isObbligatorioNoteXRegSenzaFilePrimario()) {			
					mostraPopupNoteMancanzaFile(callback);
				}else {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}				
		}
	}

	/**
	 * Condizione per cliente A2A valida se:
	 * se ProtocollazioneEntrata
	 * se FilePrimario o Allegato valorizzato
	 * se dataEOraArrivo valorizzato 
	 */
	private Boolean validateDataArrivoProtocolloA2A(boolean hasFilePrimario, boolean hasAllegati) {
		boolean validate = true;
		if(AurigaLayout.isAttivoClienteA2A()) {
			if(isProtocollazioneDetailEntrata() && (hasFilePrimario || hasAllegati)) {
				boolean dataArrivoProtocollo = datiRicezioneForm1 != null && datiRicezioneForm1.getValue("dataEOraArrivo") != null;
				if(!dataArrivoProtocollo) {
					validate = false;
				}
			}
		}
		return validate;
	}

	/**
	 * @param callback
	 */
	public void mostraPopupNoteMancanzaFile(final DSCallback callback) {
		NoteMancanzaFilePopup noteMancanzaFilePopup = new NoteMancanzaFilePopup("Compilazione note sulla mancanza del file", "", true) {
			
			@Override
			public void onClickOkButton(String noteMancanzaFile) {
				noteMancanzaFileItem.setValue(noteMancanzaFile);
				callback.execute(new DSResponse(), null, new DSRequest());
			}
			
			@Override
			public void onClickAnnullaButton() {
				abilSaveButton = true; //Riabilito il pulsante di salvataggio					
			}
		};
		noteMancanzaFilePopup.show();
	}

	/**
	 * Metodo che effettua il salvataggio/registrazione del documento
	 * 
	 */
	public void clickRegistra(final String motivoVarDatiReg) {
		manageGestionePuntiProtocollo(new ServiceCallback<String>() {

			@Override
			public void execute(String puntoProtocollo) {
				Record lRecordToSave = getRecordToSave(motivoVarDatiReg);
				if (puntoProtocollo != null && !"".equals(puntoProtocollo)) {
					lRecordToSave.setAttribute("puntoProtAssegnatario", puntoProtocollo);
				}				
				manageClickRegistra(lRecordToSave);		
			}			
		});		
	}
	
	public void manageGestionePuntiProtocollo(final ServiceCallback<String> callback) {
		if(callback != null) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT") && isAttivoAssegnatarioUnico()) {				
				GWTRestDataSource lPuntiProtocolloUoSvDatasource = new GWTRestDataSource("PuntiProtocolloUoSvDatasource", "key", FieldType.TEXT);
				lPuntiProtocolloUoSvDatasource.addParam("idAssegnatario", getIdAssegnatarioUnicoDaSalvare());
				lPuntiProtocolloUoSvDatasource.fetchData(null, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {							
							Record[] data = response.getData();
							if (data.length > 1) {
								SelezionaPuntoProtocolloWindow selezionaPuntoProtocolloWindow = new SelezionaPuntoProtocolloWindow(data, callback);
								selezionaPuntoProtocolloWindow.show();
							} else if (data.length == 1) {
								callback.execute(data[0].getAttribute("key"));							
							} else {
								callback.execute(null);							
							}
						} else {
							AurigaLayout.addMessage(new MessageBean("Si è verificato un errore durante la selezione del punto di protocollo associato all'assegnatario", "", MessageType.ERROR));
						}
					}
				});
			} else {
				callback.execute(null);				
			}
		}
	}
	
	public void manageClickRegistra(Record lRecordToSave) {

		final Map<String, Object> oldValues = vm.getValues();

		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.addParam("isAttivaGestioneErroriFile", "true");
		lGwtRestDataSource.addParam("skipSceltaApponiTimbro", AurigaLayout.getImpostazioneTimbro("skipSceltaApponiTimbro"));
		lGwtRestDataSource.addParam("rotazioneTimbroPref", AurigaLayout.getImpostazioneTimbro("rotazioneTimbro"));
		lGwtRestDataSource.addParam("posizioneTimbroPref", AurigaLayout.getImpostazioneTimbro("posizioneTimbro"));
		lGwtRestDataSource.addParam("tipoPaginaTimbroPref", AurigaLayout.getImpostazioneTimbro("tipoPagina"));
		if (isProtocollazioneDetailAtti()) {
			lGwtRestDataSource.addParam("isPropostaAtto", "true");
		}
		if (isRepertorioDetailEntrata() || isRepertorioDetailInterno() || isRepertorioDetailUscita()) {
			lGwtRestDataSource.addParam("isRepertorio", "true");
		}
		if (isRegistroFattureDetail()) {
			lGwtRestDataSource.addParam("isRegistrazioneFattura", "true");
		}
		if (isProtocollazioneDetailBozze()) {
			lGwtRestDataSource.addParam("isBozza", "true");
		}
		if (isIstanzeDetail()) {
			lGwtRestDataSource.addParam("isIstanza", "true");
		}
		if (isProtocollazioneAccessoAttiSue()) {
			lGwtRestDataSource.addParam("isRichiestaAccessoAtti", "true");
		}
		if(isRichiestaAccessoCivico()) {
			lGwtRestDataSource.addParam("isModificaRegAccessoCivico", "true");
		}
		if(isModificaDatiExtraIter()) {
			lGwtRestDataSource.addParam("isModificaDatiExtraIter", "true");
		}
		if (lRecordToSave.getAttribute("idUd") != null && !"".equals(lRecordToSave.getAttribute("idUd"))) {
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			try {
				
				RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
				final int nroAssSalvatePrec = (assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible() && listaAssegnazioniSalvate != null) ? listaAssegnazioniSalvate.getLength() : 0;														
				final boolean isEtichettaRepertorio = lRecordToSave.getAttributeAsBoolean("flgAncheRepertorio") != null && lRecordToSave.getAttributeAsBoolean("flgAncheRepertorio");
				lGwtRestDataSource.updateData(lRecordToSave, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageErroriFile(response.getData()[0], new ServiceCallback<Record>() {
								
								@Override
								public void execute(final Record lRecord) {
									if (lRecord.getAttribute("nroProtocollo") != null) {
										motivoVariazione = null;
										nroProtocolloItem.setTitle(
												I18NUtil.getMessages().protocollazione_detail_nroProtocolloItem_title());
										nroProtocolloItem.setValue(lRecord.getAttribute("nroProtocollo"));
										subProtocolloItem.setValue(lRecord.getAttribute("subProtocollo"));
										tipoProtocolloItem.setValue(lRecord.getAttribute("tipoProtocollo"));
										dataProtocolloItem.setValue(lRecord.getAttribute("dataProtocollo"));
										societaItem.setValue(lRecord.getAttribute("societa"));
										desUserProtocolloItem.setValue(lRecord.getAttribute("desUserProtocollo"));
										desUOProtocolloItem.setValue(lRecord.getAttribute("desUOProtocollo"));								
										estremiRepertorioPerStrutturaItem.setValue(lRecord.getAttribute("estremiRepertorioPerStruttura"));
										estremiAttoLiquidazioneItem.setValue(lRecord.getAttribute("estremiAttoLiquidazione"));
										idUdLiquidazioneItem.setValue(lRecord.getAttribute("idUdLiquidazione"));
										idUdHiddenItem.setValue(lRecord.getAttribute("idUd"));
										if (detailSectionRegistrazione != null) {
											if(showDetailSectionRegistrazione()) {
												detailSectionRegistrazione.setVisible(true);
											} else {
												detailSectionRegistrazione.setVisible(false);
											}
										}
										mainToolStrip.hide();
										if (layout != null) {
											Record lRecordToLoad = new Record();
											lRecordToLoad.setAttribute("flgUdFolder", "U");
											lRecordToLoad.setAttribute("idUdFolder", lRecord.getAttribute("idUd"));
											try {
												if (AurigaLayout.getIsAttivaAccessibilita() && layout instanceof ArchivioLayout) {
													layout.getList().publicManageDetailButtonClick(lRecordToLoad, getRecordNum());
													Layout.hideWaitPopup();
													//Stampa automatica delle etichette
//													manageStampaAutomaticaEtichettePostReg(record,nroAssSalvatePrec, isEtichettaRepertorio);
												} else {
												layout.getList().manageLoadDetail(lRecordToLoad, getRecordNum(),
														new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData,
																	DSRequest request) {
																Record record = response.getData()[0];
																layout.viewMode();
																Layout.hideWaitPopup();
																//Stampa automatica delle etichette
																manageStampaAutomaticaEtichettePostReg(record,nroAssSalvatePrec, isEtichettaRepertorio);
															}
														});
												}
											} catch (Exception e) {
												Layout.hideWaitPopup();
											}
											if (!layout.getFullScreenDetail()) {
												layout.reloadListAndSetCurrentRecord(lRecord);
											}
										} else {
											Record lRecordToLoad = new Record();
											lRecordToLoad.setAttribute("idUd", lRecord.getAttribute("idUd"));
											try {
												lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

													@Override
													public void execute(DSResponse response, Object rawData,
															DSRequest request) {
														if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
															Record record = response.getData()[0];
															editRecord(record);
															viewMode();
															afterUpdate(record);
															Layout.hideWaitPopup();
															manageStampaAutomaticaEtichettePostReg(record,nroAssSalvatePrec, isEtichettaRepertorio);
														}
													}
												});
											} catch (Exception e) {
												Layout.hideWaitPopup();
											}
										}
									} else {
										if (vm.isNewRecord()) {
											editNewRecord(oldValues);
										} else {
											editRecord(new Record(oldValues));
										}
										Layout.hideWaitPopup();
									}
								}
							});
						} else {
							Layout.hideWaitPopup();
						}
					}
				});
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.showWaitPopup("Registrazione in corso: potrebbe richiedere qualche secondo. Attendere...");
			try {
				lGwtRestDataSource.addData(lRecordToSave, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageErroriFile(response.getData()[0], new ServiceCallback<Record>() {
								
								@Override
								public void execute(final Record lRecord) {
									if (lRecord.getAttribute("nroProtocollo") != null) {
										motivoVariazione = null;
										nroProtocolloItem.setTitle(
												I18NUtil.getMessages().protocollazione_detail_nroProtocolloItem_title());
										nroProtocolloItem.setValue(lRecord.getAttribute("nroProtocollo"));
										subProtocolloItem.setValue(lRecord.getAttribute("subProtocollo"));
										tipoProtocolloItem.setValue(lRecord.getAttribute("tipoProtocollo"));
										dataProtocolloItem.setValue(lRecord.getAttribute("dataProtocollo"));
										societaItem.setValue(lRecord.getAttribute("societa"));
										desUserProtocolloItem.setValue(lRecord.getAttribute("desUserProtocollo"));
										desUOProtocolloItem.setValue(lRecord.getAttribute("desUOProtocollo"));
										estremiRepertorioPerStrutturaItem.setValue(lRecord.getAttribute("estremiRepertorioPerStruttura"));								
										estremiAttoLiquidazioneItem.setValue(lRecord.getAttribute("estremiAttoLiquidazione"));
										idUdLiquidazioneItem.setValue(lRecord.getAttribute("idUdLiquidazione"));
										idUdHiddenItem.setValue(lRecord.getAttribute("idUd"));
										if (detailSectionRegistrazione != null) {
											if(showDetailSectionRegistrazione()) {
												detailSectionRegistrazione.setVisible(true);
											} else {
												detailSectionRegistrazione.setVisible(false);
											}
										}
										mainToolStrip.hide();
										if (layout != null) {
											Record lRecordToLoad = new Record();
											lRecordToLoad.setAttribute("flgUdFolder", "U");
											lRecordToLoad.setAttribute("idUdFolder", lRecord.getAttribute("idUd"));
											try {
												layout.getList().manageLoadDetail(lRecordToLoad, getRecordNum(),
														new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData,
																	DSRequest request) {
																Record record = response.getData()[0];
																layout.viewMode();
																Layout.hideWaitPopup();
																if (isAttivaStampaEtichettaAutoReg(record)) {
																	clickStampaEtichetta();
																}
															}
														});
											} catch (Exception e) {
												Layout.hideWaitPopup();
											}
											if (!layout.getFullScreenDetail()) {
												layout.reloadListAndSetCurrentRecord(lRecord);
											}
										} else {
											Record lRecordToLoad = new Record();
											lRecordToLoad.setAttribute("idUd", lRecord.getAttribute("idUd"));
											try {
												lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

													@Override
													public void execute(DSResponse response, Object rawData,
															DSRequest request) {
														if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
															Record record = response.getData()[0];
															editRecord(record);
															viewMode();
															afterRegistra(record);
															Layout.hideWaitPopup();
															if (isAttivaStampaEtichettaAutoReg(record)) {
																clickStampaEtichetta();
															}
														}
													}
												});
											} catch (Exception e) {
												Layout.hideWaitPopup();
											}
										}
									} else {
										if (vm.isNewRecord()) {
											editNewRecord(oldValues);
										} else {
											editRecord(new Record(oldValues));
										}
										Layout.hideWaitPopup();
									}
								};
							});
						} else {
							Layout.hideWaitPopup();
						}
					}
				});
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}
		}
	}
	
	public void showErrorFilePrimario(String error) {
		Map<String, String> lMap = filePrimarioForm.getErrors();
		lMap.put("nomeFileAllegato", error);
		filePrimarioForm.clearErrors(true);
		filePrimarioForm.setShowInlineErrors(true);
		filePrimarioForm.setErrors(lMap);			
		filePrimarioForm.setFieldErrors("nomeFilePrimario", error);
		filePrimarioForm.showFieldErrors("nomeFilePrimario");
		filePrimarioForm.markForRedraw();
	}
	
	public void manageErroriFile(Record lRecord, ServiceCallback<Record> callback) {
		if(lRecord.getAttributeAsMap("erroriFile") != null && !lRecord.getAttributeAsMap("erroriFile").isEmpty()) {
			Layout.hideWaitPopup();
			HashMap<String, String> erroriFile = (HashMap<String, String>) lRecord.getAttributeAsMap("erroriFile");
			for(String key : erroriFile.keySet()) {
				if(key != null && key.equals("0")) {
					if (erroriFile.get(key) != null && !"".equals(erroriFile.get(key))) {
						// lo devo fare due volte altrimenti ogni tanto al primo giro non da l'evidenza grafica dell'errore sul file primario appena inserito
						showErrorFilePrimario(erroriFile.get(key));
						showErrorFilePrimario(erroriFile.get(key));
					}
					break;
				}
			}	
			// lo devo fare due volte altrimenti ogni tanto al primo giro non da l'evidenza grafica degli errori sui file allegati appena inseriti
			if(fileAllegatiItem != null) {
				fileAllegatiForm.clearErrors(true);
				if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).manageErroriFile(erroriFile);	
				} else if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).manageErroriFile(erroriFile);							
				}
			}
			if(fileAllegatiItem != null) {
				fileAllegatiForm.clearErrors(true);
				if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).manageErroriFile(erroriFile);	
				} else if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).manageErroriFile(erroriFile);							
				}
			}
		} else {
			if(callback != null) {
				callback.execute(lRecord);
			}
		}
	}
	
	/**
	 * Metodo che indica se è attiva la stampa automatica etichette ad avvenuta registrazione
	 * 
	 */
	public boolean isAttivaStampaEtichettaAutoReg(Record record) {

		/**
		 * La stampa automatica in modalita non wizard deve partire se almeno uno tra primario e allegati è senza file
		 */
		return AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") && isPrimarioOAllegatoSenzaFile(record);
	}
	
	protected boolean isPrimarioOAllegatoSenzaFile(Record record) {
		if(record.getAttribute("uriFilePrimario") == null || record.getAttribute("uriFilePrimario").isEmpty()) {
			return true;
		}
		if (record.getAttributeAsRecordList("listaAllegati") != null && record.getAttributeAsRecordList("listaAllegati").getLength() > 0) {
			for (int i = 0; i < record.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				Record currentAllegato = record.getAttributeAsRecordList("listaAllegati").get(i);
				if ((currentAllegato.getAttribute("listaTipiFileAllegato") != null && !"".equals(currentAllegato.getAttribute("listaTipiFileAllegato"))) || 
					(currentAllegato.getAttribute("descrizioneFileAllegato") != null && !"".equals(currentAllegato.getAttribute("descrizioneFileAllegato")))) {				
					String currentUriFileAllegato = currentAllegato.getAttribute("uriFileAllegato");
					if (currentUriFileAllegato == null || currentUriFileAllegato.isEmpty()) {
						return true;						
					}
				}
			}
		}	
		return false;
	}
	
	protected boolean isPrimarioOAllegatoConFile(Record record) {
		if(record.getAttribute("uriFilePrimario") != null && !record.getAttribute("uriFilePrimario").isEmpty()) {
			return true;
		}
		if (record.getAttributeAsRecordList("listaAllegati") != null && record.getAttributeAsRecordList("listaAllegati").getLength() > 0) {
			for (int i = 0; i < record.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				Record currentAllegato = record.getAttributeAsRecordList("listaAllegati").get(i);
				if ((currentAllegato.getAttribute("listaTipiFileAllegato") != null && !"".equals(currentAllegato.getAttribute("listaTipiFileAllegato"))) || 
						(currentAllegato.getAttribute("descrizioneFileAllegato") != null && !"".equals(currentAllegato.getAttribute("descrizioneFileAllegato")))) {				
					String currentUriFileAllegato = currentAllegato.getAttribute("uriFileAllegato");
					if (currentUriFileAllegato != null && !currentUriFileAllegato.isEmpty()) {
						return true;						
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare il bottone di stampa etichette
	 * 
	 */
	public boolean showStampaEtichettaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilStampaEtichetta");
	}
	
	/**
	 * Metodo che indica se mostrare le opzioni aggintive di freccia stampa etichette
	 * 
	 */
	public boolean showFrecciaStampaEtichettaButton(Record record) {
		return (!AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO"));
	}	

	/**
	 * Metodo che estende, previa conferma dell'utente, il livello di
	 * riservatezza del fascicolo/cartella al documento
	 * 
	 */
	public void checkPropagaRiservatezzaFascicoliCartelleAContenuti(final DSCallback callback) {

		boolean fascicoliRiservati = false;
		String estremiFascicoliRiservati = "";
		RecordList lRecordListFascicoli = classificazioneFascicolazioneForm.getValuesAsRecord()
				.getAttributeAsRecordList("listaClassFasc");
		if (lRecordListFascicoli != null && lRecordListFascicoli.getLength() > 0) {
			for (int i = 0; i < lRecordListFascicoli.getLength(); i++) {
				Record lRecord = lRecordListFascicoli.get(i);
				if (lRecord.getAttribute("livelloRiservatezza") != null
						&& "1".equals(lRecord.getAttribute("livelloRiservatezza"))) {
					if (!"".equals(estremiFascicoliRiservati)) {
						estremiFascicoliRiservati += ", ";
					}
					estremiFascicoliRiservati += getEstremiFascicoloFromRecord(lRecord);
					fascicoliRiservati = true;
				}
			}
		}
		boolean cartelleRiservate = false;
		String estremiCartelleRiservate = "";
		
		RecordList lRecordListCartelle = folderCustomForm.getValuesAsRecord()
				.getAttributeAsRecordList("listaFolderCustom");
		if (lRecordListCartelle != null && lRecordListCartelle.getLength() > 0) {
			for (int i = 0; i < lRecordListCartelle.getLength(); i++) {
				Record lRecord = lRecordListCartelle.get(i);
				if (lRecord.getAttribute("livelloRiservatezza") != null
						&& "1".equals(lRecord.getAttribute("livelloRiservatezza"))) {
					if (!"".equals(estremiCartelleRiservate)) {
						estremiCartelleRiservate += ", ";
					}
					estremiCartelleRiservate += getEstremiFolderCustomFromRecord(lRecord);
					cartelleRiservate = true;
				}
			}
		}
		
		if ((fascicoliRiservati || cartelleRiservate)
				&& (livelloRiservatezzaItem.getValue() == null || "".equals(livelloRiservatezzaItem.getValue()))) {
			String askMessage = null;
			if (fascicoliRiservati && cartelleRiservate) {
				askMessage = "Fascicolo/i " + estremiFascicoliRiservati + " e cartella/e " + estremiCartelleRiservate
						+ " riservati: vuoi estendere la riservatezza al documento?";
			} else if (fascicoliRiservati) {
				askMessage = "Fascicolo/i " + estremiFascicoliRiservati
						+ " riservati: vuoi estendere la riservatezza al documento?";
			} else {
				askMessage = "Cartella/e " + estremiCartelleRiservate
						+ " riservate: vuoi estendere la riservatezza al documento?";
			}
			SC.ask(askMessage, new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value == null) {
						return;
					}
					if (value) {
						livelloRiservatezzaItem.setValue("1");
					}
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			});
		} else {
			callback.execute(new DSResponse(), null, new DSRequest());
		}
	}

	/**
	 * Metodo che recupera gli estremi del fascicolo dal record in input
	 * 
	 */
	public String getEstremiFascicoloFromRecord(Record record) {

		String estremi = "";
		if (record.getAttributeAsString("annoFascicolo") != null
				&& !"".equals(record.getAttributeAsString("annoFascicolo"))) {
			estremi += record.getAttributeAsString("annoFascicolo") + " ";
		}
		if (record.getAttributeAsString("indiceClassifica") != null
				&& !"".equals(record.getAttributeAsString("indiceClassifica"))) {
			estremi += record.getAttributeAsString("indiceClassifica") + " ";
		}
		if (record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {
			estremi += "N° " + record.getAttributeAsString("nroFascicolo");
			if (record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
				estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
			}
			if (record.getAttributeAsString("nroInserto") != null && !"".equals(record.getAttributeAsString("nroInserto"))) {
				estremi += "/" + record.getAttributeAsString("nroInserto");
			}
			estremi += " ";
		}
		if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
			estremi += record.getAttributeAsString("nome");
		}
		return estremi;
	}

	/**
	 * Metodo che recupera gli estremi della cartella dal record in input
	 * 
	 */
	public String getEstremiFolderCustomFromRecord(Record record) {

		String estremi = "";
		if (record.getAttributeAsString("path") != null && !"".equals(record.getAttributeAsString("path"))) {
			estremi += record.getAttributeAsString("path") + " ";
		}
		return estremi;
	}

	/**
	 * Metodo che viene richiamato alla fine della registrazione
	 * 
	 */
	protected void afterRegistra(Record record) {
		
	}
	
	/**
	 * Metodo che viene richiamato alla fine del salvataggio in modifica
	 * 
	 */
	protected void afterUpdate(Record record) {

	}
	
	public boolean isFromCanaleSportello() {		
		return false;
	}
	
	public boolean isFromCanalePregresso() {		
		return false;
	}
	
	public String getUoProtocollanteSelezionata() {
		if (uoProtocollanteSelectItem != null) {
			if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
				return uoProtocollanteSelectItem.getValueAsString();
			} /*else if (getSelezioneUoProtocollanteValueMap().size() == 1) {
				return getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
			}*/
		} 
		return null;
	}

	/**
	 * Metodo per il recupero dei valori da salvare
	 * 
	 */
	public Record getRecordToSave(String motivoVarDatiReg) {

		final Record lDetailRecord = new Record(vm.getValues());
		RecordList lRecordListAllegati = null;
		if (fileAllegatiForm != null) {
			if (fileAllegatiForm.getValueAsRecordList("listaAllegati") != null) {
				RecordList listaAllegati = fileAllegatiForm.getValueAsRecordList("listaAllegati");
				lRecordListAllegati = new RecordList();
				if(listaAllegati != null) {
					for (int i = 0; i < listaAllegati.getLength(); i++) {
						Record lRecordAllegato = listaAllegati.get(i);
						InfoFileRecord lInfoFileRecordAllegato = InfoFileRecord.buildInfoFileRecord(listaAllegati.get(i).getAttributeAsJavaScriptObject("infoFile"));
						lRecordAllegato.setAttribute("infoFile", lInfoFileRecordAllegato);
						lRecordListAllegati.add(lRecordAllegato);
					}
				}
			}
		}
		Record lRecordToSave = new Record();
		lRecordToSave.setAttribute("flgTipoProv", getFlgTipoProv());
		if (uoProtocollanteSelectItem != null) {
			if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
				lRecordToSave.setAttribute("uoProtocollante", uoProtocollanteSelectItem.getValueAsString());
			} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {
				lRecordToSave.setAttribute("uoProtocollante", getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0]);
			}
		} 
		addFormValues(lRecordToSave, protocolloGeneraleForm);
		if (isAltraNumerazione()) {
			addFormValues(lRecordToSave, nuovaRegistrazioneForm);
		}
		if(showDetailSectionNuovaRegistrazioneProtGenerale()) {
			addFormValues(lRecordToSave, nuovaRegistrazioneProtGeneraleForm);
			if(repertorioItem != null && repertorioItem.getValueAsString() != null && !"".equals(repertorioItem.getValueAsString())) {
				lRecordToSave.setAttribute("flgAncheRepertorio", true);
			} else {
				lRecordToSave.setAttribute("flgAncheRepertorio", false);
			}
		}
		
		if(showDetailSectionTipoDocumento()) {
			addFormValues(lRecordToSave, tipoDocumentoForm);
		} else {
			lRecordToSave.setAttribute("tipoDocumento", tipoDocumento);
		}
		
		if(showDetailSectionDataArrivoSpedizione()) {
			addFormValues(lRecordToSave, dataArrivoSpedizioneForm);
		}
		
		if(showDetailSectionTipologieParticolariA2A()) {
			addFormValues(lRecordToSave, tipologieParticolariA2AForm);
		} 
		
		addFormValues(lRecordToSave, mittentiForm);
		if(showDetailSectionControinteressati()) {
			addFormValues(lRecordToSave, controinteressatiForm1);
			addFormValues(lRecordToSave, controinteressatiForm2);
		}
		if(showDetailSectionDestinatari()) {
			addFormValues(lRecordToSave, destinatariForm);
		}
		addFormValues(lRecordToSave, contenutiForm);
		if(isProtocollazioneDetailAtti() && AurigaLayout.getParametroDBAsBoolean("UPPERCASE_OGGETTO_ATTO")) {
            String oggetto = lRecordToSave.getAttribute("oggetto");
            if(oggetto != null && !"".equals(oggetto)){
            	lRecordToSave.setAttribute("oggetto", oggetto.toUpperCase());
            }
        }		
		addFormValues(lRecordToSave, riservatezzaForm);
		addFormValues(lRecordToSave, filePrimarioForm);
		addFormValues(lRecordToSave, nominativiFirmaOlografaForm);		
		addFormValues(lRecordToSave, richAccessoCivicoForm);
		lRecordToSave.setAttribute("listaAllegati", lRecordListAllegati);
		if (lDetailRecord.getAttribute("casellaEmailDestinatario") != null) {
			lRecordToSave.setAttribute("casellaEmailDestinatario",
					lDetailRecord.getAttribute("casellaEmailDestinatario"));
		}
		addFormValues(lRecordToSave, datiRicezioneForm1);
		addFormValues(lRecordToSave, datiRicezioneForm2);		
		addFormValues(lRecordToSave, docCollegatoForm);
		addFormValues(lRecordToSave, regEmergenzaForm);
		addFormValues(lRecordToSave, collocazioneFisicaForm);
		addFormValues(lRecordToSave, altriDatiForm);
		if(lDetailRecord.getAttribute("idUd") == null || "".equals(lDetailRecord.getAttribute("idUd")) || lDetailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")){
			addFormValues(lRecordToSave, assegnazioneForm);
			if (!isProtocollazioneDetailBozze()) {
				Record lRecordConfermaAssegnazione = confermaAssegnazioneForm.getValuesAsRecord();
				if ((utentiAbilCPAValueMap.size() == 0) || ((utentiAbilCPAValueMap.size() == 1)
						&& (flgPreviaConfermaAssegnazioneItem.getValueAsBoolean() == null
								|| !flgPreviaConfermaAssegnazioneItem.getValueAsBoolean()))) {
					lRecordConfermaAssegnazione.setAttribute("idUserConfermaAssegnazione", (String) null);
				}
				try {
					JSOHelper.addProperties(lRecordToSave.getJsObj(), lRecordConfermaAssegnazione.getJsObj());
				} catch (Exception e) {
				}
			}
		}
		addFormValues(lRecordToSave, condivisioneForm);
		addFormValues(lRecordToSave, classificazioneFascicolazioneForm);
		addFormValues(lRecordToSave, folderCustomForm);
		if (showDetailSectionEsibenti()) {
			addFormValues(lRecordToSave, esibentiForm);			
		}
		if (showDetailSectionInteressati()) {
			addFormValues(lRecordToSave, interessatiForm);			
		}
		if (showDetailSectionDelegato()) {
			addFormValues(lRecordToSave, delegatoForm);			
		}
		if (showDetailSectionFirmatari()) {
			addFormValues(lRecordToSave, firmatariForm);			
		}		
		if (showDetailSectionAltreVie()) {
			addFormValues(lRecordToSave, altreVieForm);
		}
		if(showDetailSectionPubblicazione()) {
			addFormValues(lRecordToSave, pubblicazioneForm);
		}
		if(showDetailSectionRipubblicazione()) {
			addFormValues(lRecordToSave, ripubblicazioneForm);
		}
		if (motivoVarDatiReg != null && !"".equals(motivoVarDatiReg)) {
			motivoVariazione = motivoVarDatiReg;
			lRecordToSave.setAttribute("motivoVarDatiReg", motivoVarDatiReg);
		}
		if (attributiAddDocDetails != null) {
			lRecordToSave.setAttribute("rowidDoc", rowidDoc);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
				
		if (showDetailSectionPerizia()) {
			addFormValues(lRecordToSave, periziaForm);			
		}
		
		return lRecordToSave;
	}
	
	/**
	 * Metodo per costruire il bottone "Presa in carico"
	 */
	protected void createPresaInCaricoButton() {
		presaInCaricoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_presaIncarico_title(),
				"archivio/prendiInCarico.png");
		presaInCaricoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickPresaInCarico();
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Restituisci"
	 */
	protected void createRestituisciButton() {
		restituisciButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_restituisci_title(),
				"archivio/restituzione.png");
		restituisciButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clickRestituisci();
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Segna come visionato"
	 */
	protected void createSegnaComeVisionatoButton() {
		segnaComeVisionatoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_segnaComeVisionato_title(),
				"postaElettronica/flgRicevutaLettura.png");
		segnaComeVisionatoButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clickSegnaComeVisionato();
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Fascicolazione"
	 */
	protected void createClassificiazioneFascicolazioneButton() {
		classificazioneFascicolazioneButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_fascicolazione_title(),
				"archivio/fascicola.png");
		classificazioneFascicolazioneButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickClassificazioneFascicolazione();
			}
		});
	}

	/**
	 * Metodo per costruire il bottone "Modifica"
	 * 
	 */
	protected void createModificaButton() {

		modificaButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		modificaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickModifica();
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Presa in carico"
	 */
	public void clickPresaInCarico() {
		final Record detailRecord = new Record(vm.getValues());
		if (detailRecord.getAttributeAsBoolean("abilPresaInCarico")) {
			final RecordList listaUd = new RecordList();
			final Record recordPresaInCarico = new Record();
			recordPresaInCarico.setAttribute("flgUdFolder", "U");
			recordPresaInCarico.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			listaUd.add(recordPresaInCarico);
			Record recordToSave = new Record();
			recordToSave.setAttribute("listaRecord", listaUd);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PresaInCaricoDataSource");
			lGwtRestDataSource.addData(recordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {				
					operationCallback(response, recordPresaInCarico, "idUdFolder",
							"Operazione effettuata con successo",
							"Il record selezionato e' andato in errore!", new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData,
										DSRequest request) {
									reload(new DSCallback() {
										@Override
										public void execute(DSResponse response, Object rawData,
												DSRequest request) {
											setSaved(true);
											if(layout != null) {
												layout.viewMode();
											} else {
												viewMode();
											}
										}
									});
								}
							});
				}
			});
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Restituisci"
	 */
	public void clickRestituisci() {
		final Record detailRecord = new Record(vm.getValues());
		RestituzionePopup restituzionePopup = new RestituzionePopup() {

			@Override
			public void onClickOkButton(final DSCallback callback) {
				detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
				detailRecord.setAttribute("flgUdFolder", "U");
				final RecordList listaUdFolder = new RecordList();
				listaUdFolder.add(detailRecord);
				final Record record = new Record();
				record.setAttribute("listaRecord", listaUdFolder);
				record.setAttribute("flgIgnoreWarning", _form.getValue("flgIgnoreWarning"));
				record.setAttribute("messaggio", _form.getValue("messaggio"));
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RestituzioneDataSource");
				lGwtRestDataSource.addData(record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(callback != null){
							callback.execute(response, rawData, request);
						}
						operationCallbackRestituisci(response, detailRecord, _form, new DSCallback() {
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {														
								
								Record lRecordToLoad = new Record();
								lRecordToLoad.setAttribute("idUd", detailRecord.getAttribute("idUd"));
								GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource", "idUd", FieldType.TEXT);
								if(layout != null && layout instanceof ScrivaniaLayout) {
									lGwtRestDataSourceProtocollo.addParam("idNode", ((ScrivaniaLayout)layout).getIdNode());
								}
								lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record record = response.getData()[0];
											editRecord(record);
											setSaved(true);
											if(layout != null) {
												layout.viewMode();
											} else {
												viewMode();
											}
											manageStampaEtichettaPostAssegnazione(record);
										}
									}
								});
							}
						});
					}
				});
			}
		};
		restituzionePopup.show();
	}
	
	/**
	 * Metodo per la stampa delle etichette in fase di post-assegnazione UD
	 */
	private void manageStampaEtichettaPostAssegnazione(Record detailRecord) {
		
		String codSupportoOrig = detailRecord != null && detailRecord.getAttributeAsString("codSupportoOrig") != null ? 
				detailRecord.getAttributeAsString("codSupportoOrig") : null;
		String segnaturaXOrd = detailRecord != null && detailRecord.getAttributeAsString("segnaturaXOrd") != null ? 
				detailRecord.getAttributeAsString("segnaturaXOrd") : null;
		
		if( (codSupportoOrig != null && "C".equals(codSupportoOrig)) &&
			(segnaturaXOrd != null && (segnaturaXOrd.startsWith("1-") || segnaturaXOrd.startsWith("2-"))) &&
			AurigaLayout.getParametroDBAsBoolean("ATTIVA_STAMPA_AUTO_ETICH_POST_ASS") &&
			AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") ){
				
			final Record recordToPrint = new Record();
			recordToPrint.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			recordToPrint.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));
			recordToPrint.setAttribute("idDoc", detailRecord.getAttribute("idDocPrimario"));
			if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa")){
								
				/**
				 * Viene verificato che sia stata selezionata una stampante in precedenza
				 */
				if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && 
						!"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
					buildStampaEtichettaAutoPostAss(recordToPrint, null);
				} else {
					PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

						@Override
						public void execute(String nomeStampante) {
							recordToPrint.setAttribute("nomeStampante", nomeStampante);
							buildStampaEtichettaAutoPostAss(recordToPrint,nomeStampante);
						}
					}, new PrinterScannerCallback() {
						
						@Override
						public void execute(String nomeStampante) {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(),
									"", MessageType.ERROR));
						}
					});
				}	
			} else {
				recordToPrint.setAttribute("flgHideBarcode", true);
				StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(recordToPrint);
				stampaEtichettaPopup.show();
			}
		}
	}
	
	private void operationCallbackRestituisci(DSResponse response, final Record record, DynamicForm _form, final DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				if (errorMessages.get(record.getAttribute("idUd")) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute("idUd"));
				} else {
					errorMsg = "Si è verificato un errore durante la restituzione!";
				}
			}
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				if (data.getAttributeAsInt("flgIgnoreWarning") != 1) {
					Layout.addMessage(new MessageBean("Restituzione effettuata con successo", "", MessageType.INFO));
					layout.reloadListAndSetCurrentRecord(record);
					if (callback != null) {
						callback.execute(new DSResponse(), null, new DSRequest());
					}
				} else {
					_form.setValue("flgIgnoreWarning", "1");
				}
			}
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Segna come visionato"
	 */
	public void clickSegnaComeVisionato() {
		final Record detailRecord = new Record(vm.getValues());
		if (detailRecord.getAttributeAsBoolean("abilSetVisionato")) {
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);		
			lGwtRestDataSource.addParam("flgUdFolder", "U");
			lGwtRestDataSource.addParam("idUdFolder", detailRecord.getAttribute("idUd"));
			lGwtRestDataSource.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						RecordList listaUoDestNotifiche = response.getDataAsRecordList();
						
						String title = I18NUtil.getMessages().segnaComeVisionato_Popup_Ud_title(detailRecord.getAttribute("segnatura"));
						
						SegnaComeVisionatoPopup segnaComeVisionatoPopup = new SegnaComeVisionatoPopup(title, null, listaUoDestNotifiche) {

							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								RecordList listaUdFolderNoteAggiornate = new RecordList();
								String noteInserita = object.getAttribute("note"); 
								final Record udFolderNoteAggiornate = new Record();
								udFolderNoteAggiornate.setAttribute("flgUdFolder", "U");
								udFolderNoteAggiornate.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
								udFolderNoteAggiornate.setAttribute("note", noteInserita);
								listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
								Record lRecordSelezionatiNoteAggiornate = new Record();
								lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
								lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));	
								lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));
								GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
								lGwtRestDataSourceArchivio.executecustom("segnaComeVisionato", lRecordSelezionatiNoteAggiornate, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (callback != null) {
											callback.execute(response, rawData, request);
										}
										operationCallback(response, udFolderNoteAggiornate, "idUdFolder", "Operazione effettuata con successo",
												"Si è verificato un errore durante l'aggiornamento !", new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData,
													DSRequest request) {
												
												reload(new DSCallback() {
													@Override
													public void execute(DSResponse response, Object rawData,
															DSRequest request) {
														setSaved(true);
														if(layout != null) {
															layout.viewMode();
														} else {
															viewMode();
														}
													}
												});
											}
										});
									}
								});
							}
						};
						segnaComeVisionatoPopup.show();
					}
				}
			});
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Segna come visionato"
	 */
	public void clickSegnaComeArchiviato() {
		final Record detailRecord = new Record(vm.getValues());
		if (detailRecord.getAttributeAsBoolean("abilArchiviazione")) {
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);		
			lGwtRestDataSource.addParam("flgUdFolder", "U");
			lGwtRestDataSource.addParam("idUdFolder", detailRecord.getAttribute("idUd"));
			final String idNodo = ((ScrivaniaLayout) layout).getIdNode();
			lGwtRestDataSource.addParam("altriParametriIn", "TIPO_AZIONE|*|A|*|CI_NODO_SCRIVANIA|*|" + idNodo);
			lGwtRestDataSource.fetchData(null, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						RecordList listaUoDestNotifiche = response.getDataAsRecordList();
						
						String title = I18NUtil.getMessages().segnaComeArchiviato_Popup_Ud_title(detailRecord.getAttribute("segnatura"));
						
						SegnaComeArchiviatoPopup segnaComeArchiviatoPopup = new SegnaComeArchiviatoPopup(title, null, listaUoDestNotifiche) {
							
							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								RecordList listaUdFolderNoteAggiornate = new RecordList();
								String noteInserita = object.getAttribute("note"); 
								final Record udFolderNoteAggiornate = new Record();
								udFolderNoteAggiornate.setAttribute("flgUdFolder", "U");
								udFolderNoteAggiornate.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
								udFolderNoteAggiornate.setAttribute("note", noteInserita);
								listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
								Record lRecordSelezionatiNoteAggiornate = new Record();
								lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
								lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));	
								lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));
								lRecordSelezionatiNoteAggiornate.setAttribute("idNodo", idNodo);
								GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
								lGwtRestDataSourceArchivio.executecustom("segnaComeArchiviato", lRecordSelezionatiNoteAggiornate, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (callback != null) {
											callback.execute(response, rawData, request);
										}
										operationCallback(response, udFolderNoteAggiornate, "idUdFolder", "Operazione effettuata con successo",
												"Si è verificato un errore durante l'aggiornamento !", new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData,
													DSRequest request) {
												
												reload(new DSCallback() {
													@Override
													public void execute(DSResponse response, Object rawData,
															DSRequest request) {
														setSaved(true);
														if(layout != null) {
															layout.viewMode();
														} else {
															viewMode();
														}
													}
												});
											}
										});
									}
								});
							}
						};
						segnaComeArchiviatoPopup.show();
					}
				}
			});
		}
	}
	
	private String getEstremiFolderFromList(Record record){
		String estremi = "";
		if(record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome"))){
			estremi = record.getAttribute("percorsoNome");
		}else{
			if (record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
				estremi += record.getAttributeAsString("annoFascicolo") + " ";
			}
			if (record.getAttributeAsString("indiceClassifica") != null && !"".equals(record.getAttributeAsString("indiceClassifica"))) {
				estremi += record.getAttributeAsString("indiceClassifica") + " ";
			}
			if (record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {
				estremi += "N° " + record.getAttributeAsString("nroFascicolo");
				if (record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
					estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
				}
				estremi += " ";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
		}
		return estremi;
	}
	
	/**
	 * Metodo che implementa l'azione del bottone Fascicolazione
	 */
	public void clickClassificazioneFascicolazione() {
		
		final Record detailRecord = new Record(vm.getValues());
		
		/** ABILITATO 
		 * - Classifica/fascicola 
		 * - Organizza 
		 **/
		if (detailRecord.getAttributeAsBoolean("abilClassificazioneFascicolazione") && detailRecord.getAttributeAsBoolean("abilOrganizza")) {
			Menu menu = new Menu();
			MenuItem classificaFascicolaMenuItem = new MenuItem("Classifica/fascicola", "archivio/classfasc.png");
			classificaFascicolaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordToClassFasc = new Record();
					recordToClassFasc.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
					recordToClassFasc.setAttribute("flgTipoProv", detailRecord.getAttribute("flgTipoProv"));				
					new ClassificazioneFascicolazionePopup(false, 
							recordToClassFasc, detailRecord) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							final RecordList listaUd = new RecordList();
							if(detailRecord.getAttribute("idUdFolder") == null || "".equalsIgnoreCase(detailRecord.getAttribute("idUdFolder"))) {
								detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
							}
							listaUd.add(detailRecord);
							Record record = new Record();
							record.setAttribute("listaRecord", listaUd);
							record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
							record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									operationCallback(response, recordToClassFasc, "idUdFolder",
											"Operazione effettuata con successo",
											"Il record selezionato e' andato in errore!", new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData,
														DSRequest request) {
													manageOnCloseClick();
													reload(new DSCallback() {
														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															setSaved(true);
															if(layout != null) {
																layout.viewMode();
															} else {
																viewMode();
															}
														}
													});
												}
											});
								}
							});
						}
					};
				}
			});	
			menu.addItem(classificaFascicolaMenuItem);
			
			MenuItem organizzaMenuItem = new MenuItem("Organizza", "archivio/organizza.png");
			organizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordToOrganizza = new Record();
					recordToOrganizza.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
					new OrganizzaPopup(recordToOrganizza) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							final RecordList listaUd = new RecordList();
							if(detailRecord.getAttribute("idUdFolder") == null || "".equalsIgnoreCase(detailRecord.getAttribute("idUdFolder"))) {
								detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
							}
							listaUd.add(detailRecord);
							Record record = new Record();
							record.setAttribute("listaRecord", listaUd);
							record.setAttribute("listaFolderCustom", _form.getValueAsRecordList("listaFolderCustom"));
							record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganizzaDataSource");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									operationCallback(response, recordToOrganizza, "idUdFolder",
											"Operazione effettuata con successo",
											"Il record selezionato e' andato in errore!", new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData,
														DSRequest request) {
													manageOnCloseClick();
													reload(new DSCallback() {
														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															setSaved(true);
															if(layout != null) {
																layout.viewMode();
															} else {
																viewMode();
															}
														}
													});
												}
											});
								}
							});
						}
					};
				}
			});
			menu.addItem(organizzaMenuItem);
			menu.showContextMenu();
		} else if (detailRecord.getAttributeAsBoolean("abilClassificazioneFascicolazione") && !detailRecord.getAttributeAsBoolean("abilOrganizza")) {
			/** ABILITATO: 		Classifica/fascicola 
			 *  NON ABILITATO:  Organizza 
			 **/
			final Record recordToClassFasc = new Record();
			recordToClassFasc.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			recordToClassFasc.setAttribute("flgTipoProv", detailRecord.getAttribute("flgTipoProv"));
			new ClassificazioneFascicolazionePopup(false, 
					recordToClassFasc, detailRecord) {

				@Override
				public void onClickOkButton(final DSCallback callback) {
					final RecordList listaUd = new RecordList();
					if(detailRecord.getAttribute("idUdFolder") == null || "".equalsIgnoreCase(detailRecord.getAttribute("idUdFolder"))) {
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
					}
					listaUd.add(detailRecord);
					Record record = new Record();
					record.setAttribute("listaRecord", listaUd);
					record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
					record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, recordToClassFasc, "idUdFolder",
									"Operazione effettuata con successo",
									"Il record selezionato e' andato in errore!", new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData,
												DSRequest request) {
											manageOnCloseClick();
											reload(new DSCallback() {
												@Override
												public void execute(DSResponse response, Object rawData,
														DSRequest request) {
													setSaved(true);
													if(layout != null) {
														layout.viewMode();
													} else {
														viewMode();
													}
												}
											});
										}
									});
						}
					});
				}
			};
			
		} else if (!detailRecord.getAttributeAsBoolean("abilClassificazioneFascicolazione") && detailRecord.getAttributeAsBoolean("abilOrganizza")) {
			/** ABILITATO: 		Organizza 
			 *  NON ABILITATO:  Classifica/fascicola 
			 **/
			final Record recordToOrganizza = new Record();
			recordToOrganizza.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			new OrganizzaPopup(recordToOrganizza) {

				@Override
				public void onClickOkButton(final DSCallback callback) {
					final RecordList listaUd = new RecordList();
					if(detailRecord.getAttribute("idUdFolder") == null || "".equalsIgnoreCase(detailRecord.getAttribute("idUdFolder"))) {
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
					}
					listaUd.add(detailRecord);
					Record record = new Record();
					record.setAttribute("listaRecord", listaUd);
					record.setAttribute("listaFolderCustom", _form.getValueAsRecordList("listaFolderCustom"));
					record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganizzaDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							operationCallback(response, recordToOrganizza, "idUdFolder",
									"Operazione effettuata con successo",
									"Il record selezionato e' andato in errore!", new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData,
												DSRequest request) {
											manageOnCloseClick();
											reload(new DSCallback() {
												@Override
												public void execute(DSResponse response, Object rawData,
														DSRequest request) {
													setSaved(true);
													if(layout != null) {
														layout.viewMode();
													} else {
														viewMode();
													}
												}
											});
										}
									});
						}
					});
				}
			};
		}
	}

	/**
	 * Metodo che implementa l'azione del bottone "Modifica"
	 * 
	 */
	public void clickModifica() {

		Record record = new Record(vm.getValues());
		if (record.getAttributeAsBoolean("abilModificaDati")) {
			modificaDatiMode();
		} else if (record.getAttributeAsBoolean("abilAggiuntaFile")) {
			aggiuntaFileMode();
		} else if (record.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
			modificaDatiExtraIterMode();
		}
	}
	
	/**
	 *  Metodo per costruire la freccia che apre il Menu per le azioni di Modifica
	 * 
	 */
	protected void createFrecciaModificaButton() {
		frecciaModificaButton = new FrecciaDetailToolStripButton();
		frecciaModificaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickFrecciaModifica();
			}
		});
	}
	
	public void clickFrecciaModifica() {
		
		getAbilitazioniFrecciaButton();
		
		Menu menu = new Menu();
		
		MenuItem standardItem = new MenuItem("Modifica");
		standardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				clickModifica();
			}
		});		
		menu.addItem(standardItem);
		
		if (isAbilModificaTipologia) {
			MenuItem modificaTipologiaMenuItem = new MenuItem("Assegna/modifica tipologia");
			modificaTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//aggiungere creazione popup
					new SceltaTipoDocPopup(false, tipoDocumento, "", null,
							null,"|*|FINALITA|*|ASS_TIPOLOGIA", new ServiceCallback<Record>() {

								@Override
								public void execute(Record lRecordTipoDoc) {

									final Record record = new Record(vm.getValues());
									String tipoDocumento = lRecordTipoDoc.getAttribute("idTipoDocumento");

									//CAPIRE SE BISOGNA PASSARE ANCHE QUESTE INFORMAZIONI ALLA STORE						
									//record.setAttribute("nomeTipoDocumento", lRecordTipoDoc.getAttribute("descTipoDocumento"));
									//record.setAttribute("flgOggettoNonObblig", lRecordTipoDoc.getAttribute("flgOggettoNonObblig"));
									//record.setAttribute("flgTipoDocConVie", lRecordTipoDoc.getAttribute("flgTipoDocConVie"));

									final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
											"ProtocolloDataSource");
									lGwtRestDataSource.extraparam.put("tipoDocumento", tipoDocumento);
									lGwtRestDataSource.executecustom("modificaTipologiaUD", record, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {

											operationCallback(response, record, "idUdFolder",
													"Operazione effettuata con successo",
													"Il record selezionato e' andato in errore!", new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															reload(new DSCallback() {
																@Override
																public void execute(DSResponse response, Object rawData,
																		DSRequest request) {
																	setSaved(true);
																	viewMode();
																}
															});
														}
													});
										}
									});

								}
							});
				}
			});
			menu.addItem(modificaTipologiaMenuItem);
		}
		
		if (isAbilModificaDatiExtraIter) {
			MenuItem modificaDatiExtraIterMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiExtraIter_menu_apri_title(), "pratiche/task/buttons/modifica_dati_extra_iter.png");
			modificaDatiExtraIterMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					modificaDatiExtraIterMode();
				}

			});
			menu.addItem(modificaDatiExtraIterMenuItem);
		}
		
		if (isAbilModificaOpereAtto) {
			MenuItem modificaOpereMenuItem = new MenuItem(I18NUtil.getMessages().modificaOpereAtto_menu_apri_title(), "pratiche/task/buttons/modifica_opere_atto.png");
			modificaOpereMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordDetail = new Record(vm.getValues());
					final String idUd      = recordDetail.getAttributeAsString("idUd");
					final String idProcess = recordDetail.getAttributeAsString("idProcess");
					final String activityName = "#AGG_EXTRA_ITER";
					Record lRecord = new Record();
					lRecord.setAttribute("idUd", idUd);
					lRecord.setAttribute("idProcess", idProcess);
					lRecord.setAttribute("activityName", activityName);
					String title = I18NUtil.getMessages().modificaOpereAtto_Popup_Ud_title(recordDetail.getAttribute("segnatura"));
					new DettaglioOpereAttoPopup(lRecord, title);
				}

			});
			menu.addItem(modificaOpereMenuItem);
		}
			
		if (isAbilModificaDatiPubblAtto) {
			MenuItem modificaDatiPubblAttoMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiPubblAtto_menu_apri_title(), "pratiche/task/buttons/modifica_dati_pubbl_atto.png");
			modificaDatiPubblAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordDetail = new Record(vm.getValues());
					final String idUd      = recordDetail.getAttributeAsString("idUd");
					final String idProcess = recordDetail.getAttributeAsString("idProcess");
					final String activityName = "#AGG_EXTRA_ITER";
					Record lRecord = new Record();
					lRecord.setAttribute("idUd", idUd);
					lRecord.setAttribute("idProcess", idProcess);
					lRecord.setAttribute("activityName", activityName);
					String title = I18NUtil.getMessages().modificaDatiPubblAtto_Popup_Ud_title(recordDetail.getAttribute("segnatura"));
					new DettaglioDatiPubblAttoPopup(lRecord, title);
				}

			});
			menu.addItem(modificaDatiPubblAttoMenuItem);
		}
		
		menu.showContextMenu();
	}
	
	public void getAbilitazioniFrecciaButton() {
		Record record = new Record(vm.getValues());
		isAbilModificaTipologia = record.getAttributeAsBoolean("abilModificaTipologia"); 
		isAbilModificaDatiExtraIter = record.getAttributeAsBoolean("abilModificaDatiExtraIter");
		isAbilModificaOpereAtto = record.getAttributeAsBoolean("abilModificaOpereAtto");
		isAbilModificaDatiPubblAtto = record.getAttributeAsBoolean("abilModificaDatiPubblAtto");
	}
	
	/**
	 * Metodo per costruire il bottone "Registrazione reg. accesso civ."
	 * 
	 */
	protected void createRegAccessoCivicoButton() {

		regAccessoCivicoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_regAccessoCivicoButton_title(), "buttons/modify.png");
		regAccessoCivicoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickRegAccessoCivico();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Registrazione reg. accesso civ."
	 * 
	 */
	public void clickRegAccessoCivico() {

		Record record = new Record(vm.getValues());
		if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
			modificaRegAccessoCivicoMode();
			salvaRegistraButton.setTitle("Registra");
		}
	}

	/**
	 * Metodo per costruire il bottone "Variazione dati registrazione"
	 * 
	 */
	protected void createModificaDatiRegButton() {

		modificaDatiRegButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_variazioneDatiRegistrazione_title(),
				"buttons/modificaDatiReg.png");
		modificaDatiRegButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickModificaDatiReg();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Variazione dati
	 * registrazione"
	 * 
	 */
	public void clickModificaDatiReg() {
		modificaDatiRegMode();
	}
	
	
	
	/**
	 * Metodo per costruire il bottone "Revoca atto"
	 */
	protected void createRevocaAttoButton() {

		revocaAttoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_revoca_atto_button(), 
				"buttons/revoca_atto.png");
		revocaAttoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickRevocaAtto(null);
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Prot. in entrata"
	 * 
	 */
	public void clickRevocaAtto(final CustomLayout layout) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", vm.getValue("idUd"));
		clickRevocaAtto(layout, lRecordToLoad);
	}
	
	public void clickRevocaAtto(final CustomLayout layout, Record lRecordToLoad) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
			lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record lRecordAtto = response.getData()[0];
						AurigaLayout.avviaRevocaAtto(lRecordAtto);
					}
				}
			});
		}
	}
	
	/**
	 * Metodo per costruire il bottone "Prot. in entrata"
	 */
	protected void createProtocollazioneEntrataButton() {

		protocollazioneEntrataButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_entrata_button(), 
				"menu/protocollazione_entrata.png");
		protocollazioneEntrataButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickProtocollazioneEntrata(null);
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Prot. in entrata"
	 * 
	 */
	public void clickProtocollazioneEntrata(final CustomLayout layout) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", vm.getValue("idUd"));
		clickProtocollazioneEntrata(layout, lRecordToLoad);
	}
	
	public void clickProtocollazioneEntrata(final CustomLayout layout, Record lRecordToLoad) {
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordDettaglio = response.getData()[0];
					if(layout != null) {
						ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(recordDettaglio);
						protocollazioneDetailEntrata.caricaDettaglioXProtBozza(layout, recordDettaglio);
						layout.changeDetail(lGwtRestDataSourceProtocollo, protocollazioneDetailEntrata);
						layout.editMode();
						if (layout instanceof ScrivaniaLayout) {
							((ScrivaniaLayout) layout).setSaveButtonTitle("Registra");
						} else if (layout instanceof ArchivioLayout) {
							((ArchivioLayout) layout).setSaveButtonTitle("Registra");
						}
						if (AurigaLayout.getIsAttivaAccessibilita() && AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal") && layout.getList() instanceof ArchivioList) {
							DettaglioWindow dettaglioWindow = ((ArchivioList)layout.getList()).getDettaglioWindow();
							if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
								dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), layout.getDetailButtons());
							}else {
								dettaglioWindow = ((ArchivioList)layout.getList()).new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), layout.getDetailButtons());
							}
						}
					} else {
						if (AurigaLayout.getParentWindow(instance) != null && AurigaLayout.getParentWindow(instance) instanceof ModalWindow) {
							ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(recordDettaglio, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									recordProtocollato = object;
									reload(new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											setSaved(true);
											viewMode();
										}
									});
								}
							}, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									recordProtocollato = object;
									reload(new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											setSaved(true);
											viewMode();
										}
									});
								}
							});
							protocollazioneDetailEntrata.caricaDettaglioXProtBozza(null, recordDettaglio);
							Layout.addModalWindow("protocollazione_entrata_modal", "Protocollazione in entrata", "menu/protocollazione_entrata.png", protocollazioneDetailEntrata);
						} else {
							if (!Layout.isOpenedPortlet("protocollazione_entrata")) {
								ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(recordDettaglio);
								protocollazioneDetailEntrata.caricaDettaglioXProtBozza(null, recordDettaglio);
								Layout.addPortlet("protocollazione_entrata", protocollazioneDetailEntrata);
								closeParentWindow(instance);
							} else {
								final Portlet portlet = Layout.getOpenedPortlet("protocollazione_entrata");
								SC.ask("La finestra di \"" + portlet.getTitle()
										+ "\" risulta già aperta. Tutti i dati andranno persi. Continuare comunque l'operazione?",
										new BooleanCallback() {
		
											@Override
											public void execute(Boolean value) {
												if (value) {
													Layout.selectPortlet("protocollazione_entrata");
													ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(recordDettaglio);
													protocollazioneDetailEntrata.caricaDettaglioXProtBozza(null, recordDettaglio);
													portlet.setBody(protocollazioneDetailEntrata);
													closeParentWindow(instance);
												}
											}
										});
							}	
						}
					}
				}
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Prot. in uscita"
	 */
	protected void createProtocollazioneUscitaButton() {

		protocollazioneUscitaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_uscita_button(), 
				"menu/protocollazione_uscita.png");
		protocollazioneUscitaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickProtocollazioneUscita(null);
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Prot. in uscita"
	 * 
	 */
	public void clickProtocollazioneUscita(final CustomLayout layout) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", vm.getValue("idUd"));
		clickProtocollazioneUscita(layout, lRecordToLoad);
	}
	
	public void clickProtocollazioneUscita(final CustomLayout layout, Record lRecordToLoad) {
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordDettaglio = response.getData()[0];
					if(layout != null) {
						ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordDettaglio);
						protocollazioneDetailUscita.caricaDettaglioXProtBozza(layout, recordDettaglio);
						layout.changeDetail(lGwtRestDataSourceProtocollo, protocollazioneDetailUscita);
						layout.editMode();
						if (layout instanceof ScrivaniaLayout) {
							((ScrivaniaLayout) layout).setSaveButtonTitle("Registra");
						} else if (layout instanceof ArchivioLayout) {
							((ArchivioLayout) layout).setSaveButtonTitle("Registra");
						}
						if (AurigaLayout.getIsAttivaAccessibilita() && AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal") && layout.getList() instanceof ArchivioList) {
							DettaglioWindow dettaglioWindow = ((ArchivioList)layout.getList()).getDettaglioWindow();
							if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
								dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), layout.getDetailButtons());
							}else {
								dettaglioWindow = ((ArchivioList)layout.getList()).new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), layout.getDetailButtons());
							}
						}
					} else {
						if (AurigaLayout.getParentWindow(instance) != null && AurigaLayout.getParentWindow(instance) instanceof ModalWindow) {
							ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordDettaglio, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									recordProtocollato = object;
									reload(new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											setSaved(true);
											viewMode();
										}
									});
								}
							}, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									recordProtocollato = object;
									reload(new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											setSaved(true);
											viewMode();
										}
									});
								}
							});
							protocollazioneDetailUscita.caricaDettaglioXProtBozza(null, recordDettaglio);
							Layout.addModalWindow("protocollazione_uscita_modal", "Protocollazione in uscita", "menu/protocollazione_uscita.png", protocollazioneDetailUscita);
						} else {
							if (!Layout.isOpenedPortlet("protocollazione_uscita")) {
								ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordDettaglio);
								protocollazioneDetailUscita.setZIndex(Integer.MAX_VALUE);
								protocollazioneDetailUscita.caricaDettaglioXProtBozza(null, recordDettaglio);
								Layout.addPortlet("protocollazione_uscita", protocollazioneDetailUscita);
								closeParentWindow(instance);
							} else {
								final Portlet portlet = Layout.getOpenedPortlet("protocollazione_uscita");
								SC.ask("La finestra di \"" + portlet.getTitle()
										+ "\" risulta già aperta. Tutti i dati andranno persi. Continuare comunque l'operazione?",
										new BooleanCallback() {
		
											@Override
											public void execute(Boolean value) {
												if (value) {
													Layout.selectPortlet("protocollazione_uscita");
													ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordDettaglio);
													protocollazioneDetailUscita.caricaDettaglioXProtBozza(null, recordDettaglio);
													portlet.setBody(protocollazioneDetailUscita);
													portlet.setIsModal(true);
													portlet.setIsModal(true);
													portlet.setShowModalMask(true);
													portlet.setModalMaskOpacity(50);
													portlet.setZIndex(Integer.MAX_VALUE);
													closeParentWindow(instance);
												}
											}
										});
							}	
						}
					}
				}
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Prot. interna"
	 */
	protected void createProtocollazioneInternaButton() {

		protocollazioneInternaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_interna_button(), 
				"menu/protocollazione_interna.png");
		protocollazioneInternaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickProtocollazioneInterna(null);
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Prot. interna"
	 * 
	 */
	
	public void clickProtocollazioneInterna(final CustomLayout layout) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", vm.getValue("idUd"));
		clickProtocollazioneInterna(layout, lRecordToLoad);
	}
	
	public void clickProtocollazioneInterna(final CustomLayout layout, Record lRecordToLoad) {
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordDettaglio = response.getData()[0];		
					if(layout != null) {
						ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordDettaglio);
						protocollazioneDetailInterna.caricaDettaglioXProtBozza(layout, recordDettaglio);
						layout.changeDetail(lGwtRestDataSourceProtocollo, protocollazioneDetailInterna);
						layout.editMode();
						if (layout instanceof ScrivaniaLayout) {
							((ScrivaniaLayout) layout).setSaveButtonTitle("Registra");
						} else if (layout instanceof ArchivioLayout) {
							((ArchivioLayout) layout).setSaveButtonTitle("Registra");
						}
						if (AurigaLayout.getIsAttivaAccessibilita() && AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal") && layout.getList() instanceof ArchivioList) {
							DettaglioWindow dettaglioWindow = ((ArchivioList)layout.getList()).getDettaglioWindow();
							if (dettaglioWindow != null && dettaglioWindow.isOpen()) {
								dettaglioWindow.updateContentDocumentWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), layout.getDetailButtons());
							}else {
								dettaglioWindow = ((ArchivioList)layout.getList()).new DettaglioWindow(layout.getDetail().getRecordToSave(), layout.getDetail(), layout.getViewDetailTitle(), layout.getDetailButtons());
							}
						}
					} else {
						if (AurigaLayout.getParentWindow(instance) != null && AurigaLayout.getParentWindow(instance) instanceof ModalWindow) {
							ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordDettaglio, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									recordProtocollato = object;
									reload(new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											setSaved(true);
											viewMode();
										}
									});
								}
							}, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record object) {
									recordProtocollato = object;
									reload(new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											setSaved(true);
											viewMode();
										}
									});
								}
							});
							protocollazioneDetailInterna.caricaDettaglioXProtBozza(null, recordDettaglio);
							Layout.addModalWindow("protocollazione_interna_modal", "Protocollazione interna", "menu/protocollazione_interna.png", protocollazioneDetailInterna);
						} else {
							if (!Layout.isOpenedPortlet("protocollazione_interna")) {
								ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordDettaglio);
								protocollazioneDetailInterna.caricaDettaglioXProtBozza(null, recordDettaglio);
								Layout.addPortlet("protocollazione_interna", protocollazioneDetailInterna);
								closeParentWindow(instance);
							} else {
								final Portlet portlet = Layout.getOpenedPortlet("protocollazione_interna");
								SC.ask("La finestra di \"" + portlet.getTitle()
										+ "\" risulta già aperta. Tutti i dati andranno persi. Continuare comunque l'operazione?",
										new BooleanCallback() {
		
											@Override
											public void execute(Boolean value) {
												if (value) {
													Layout.selectPortlet("protocollazione_interna");
													ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordDettaglio);
													protocollazioneDetailInterna.caricaDettaglioXProtBozza(null, recordDettaglio);
													portlet.setBody(protocollazioneDetailInterna);
													closeParentWindow(instance);
												}
											}
										});
							}	
						}
					}
				}
			}
		});
	}

	/**
	 * Metodo per costruire il bottone "Invio PEC"
	 * 
	 */
	protected void createInvioPECButton() {

		invioPECButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailinterop_title(),
				"anagrafiche/soggetti/flgEmailPecPeo/INTEROP.png");
		invioPECButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickInvioPECButton();
			}
		});
	}
	
	/**
	 * Metodo per costruire il bottone "Invio e-mail ricevuta"
	 * 
	 */
	protected void createInvioMailRicevutaButton() {
		
		invioMailRicevutaButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailricevuta_title(),
				"anagrafiche/soggetti/flgEmailPecPeo/INTEROP.png");
		invioMailRicevutaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clickInvioMailRicevutaButton();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Invio PEC"
	 * 
	 */
	public void clickInvioPECButton() {

		Record record = new Record(vm.getValues());
		
		final Boolean flgInvioPECMulti = record.getAttributeAsBoolean("flgInvioPECMulti") != null &&
				record.getAttributeAsBoolean("flgInvioPECMulti") ? true : false;
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
				"AurigaInvioUDMailDatasource");
		if(flgInvioPECMulti) {
			lGwtRestService.extraparam.put("PEC_MULTI", "1");
		} 
		lGwtRestService.extraparam.put("tipoMail", "PEC");
		lGwtRestService.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				
				if(flgInvioPECMulti) {
					object.setAttribute("tipoMail", "PEO");
					InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							reload(new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									setSaved(true);
									viewMode();
								}
							});
						}
					});
					lInvioUdMailWindow.loadMail(object);
					lInvioUdMailWindow.show();
					
				} else {
					InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEC", new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							reload(new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									setSaved(true);
									viewMode();
								}
							});
						}
					});
					lInvioUdMailWindow.loadMail(object);
					lInvioUdMailWindow.show();
				}

			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Invio e-mail ricevuta"
	 * 
	 */
	public void clickInvioMailRicevutaButton() {
		this.getStampaRicevuta(new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object data, DSRequest dsRequest) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					final String nomeFilePdf = result.getAttribute("nomeFile");
					final String uriFilePdf = result.getAttribute("uri");
					final InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
					Record record = new Record(vm.getValues());
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
					lGwtRestService.extraparam.put("tipoMail", "PEC");
					lGwtRestService.executecustom("callInvioRicevuta", record, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							final Record object = response.getData()[0];
							new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", instance,new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
									}
								}) 
							{
								@Override
								public Record getInitialRecordNuovoMessaggio() {				
									Record lRecord = new Record();
									
									lRecord.setAttribute("oggetto", object.getAttribute("oggetto"));
									lRecord.setAttribute("bodyHtml", object.getAttribute("bodyHtml"));
									lRecord.setAttribute("destinatari", object.getAttribute("destinatari"));
									lRecord.setAttribute("destinatariCC", object.getAttribute("destinatariCC"));
									lRecord.setAttribute("mittente", object.getAttribute("mittente"));				
									RecordList attachRecordList = new RecordList();
									Record attach = new Record();
									attach.setAttribute("fileNameAttach", nomeFilePdf);
									attach.setAttribute("infoFileAttach", infoFilePdf);
									attach.setAttribute("uriAttach", uriFilePdf);
									attachRecordList.add(attach);
									lRecord.setAttribute("attach", attachRecordList);				
									return lRecord;
								}
								

								@Override
								public String getTitleWindow() {
									return I18NUtil.getMessages().invioudmail_detail_mailricevuta_Windowtitle();
								}
							};
						}
					});
				}
				
			}
		});
	}

	/**
	 * Metodo per costruire il bottone "Invio e-mail ordinaria"
	 * 
	 */
	protected void createInvioPEOButton() {

		invioPEOButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailpeo_title(),
				"anagrafiche/soggetti/flgEmailPecPeo/PEO.png");
		invioPEOButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickInvioPEOButton();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Invio e-mail ordinaria"
	 * 
	 */
	public void clickInvioPEOButton() {

		Record record = new Record(vm.getValues());
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
				"AurigaInvioUDMailDatasource");
		lGwtRestService.extraparam.put("tipoMail", "PEO");
		lGwtRestService.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						reload(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								setSaved(true);
								viewMode();
							}
						});
					}
				});
				lInvioUdMailWindow.loadMail(object);
				lInvioUdMailWindow.show();
			}
		});
	}	
	
	/*** FEDERICO TROTTA - INIZIO ***/

	/**
	 * Metodo per costruire il bottone "Invio raccomandata"
	 * 
	 */
	public void createInviaRaccomandataButton() {

		if (invioRaccomandataButton == null) {
			invioRaccomandataButton = new DetailToolStripButton("Invio raccomandata", "postaElettronica/inoltro.png");
			invioRaccomandataButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
										
					Record record = new Record(vm.getValues());
					modalitaInvio = "raccomandata";
					clickPostalizza(record);
					
				}
			});
		}	
	}
	
	/**
	 * Metodo per costruire il bottone "Invio postaPrioritaria"
	 * 
	 */
	public void createInviaPostaPrioritariaButton() {

		if (invioPostaPrioritariaButton == null) {
			invioPostaPrioritariaButton = new DetailToolStripButton("Invio posta prioritaria", "postaElettronica/inoltro.png");
			invioPostaPrioritariaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
										
					Record record = new Record(vm.getValues());
					modalitaInvio = "posta prioritaria";
					clickPostalizza(record);
					
				}
			});
		}	
	}
	
	/**
	 * Metodo che che gestisce la validazione dei dati per postel e la creazione della finestra per il mittente postel
	 * 
	 */
	public void clickPostalizza(Record record) {
		
		if (isValidPostel(record)) {
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
					
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PostelDataSource");
			lGwtRestService.addParam("rotazioneTimbroPref", rotazioneTimbroPref);
			lGwtRestService.addParam("posizioneTimbroPref", posizioneTimbroPref);
			lGwtRestService.executecustom("generaTimbrati", record, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object data, DSRequest dsRequest) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						record.setAttribute("modalitaInvio", modalitaInvio);
						PostalizzaPopup p_popup = new PostalizzaPopup(record, instance);
						p_popup.show();	
					}
				}
			});
		}
	}
		
	/**
	 * Metodo che implementa l'azione del bottone "Invio raccomandata"
	 * 
	 */	
	public void clickInviaPostel(Record record) {

		try {
			Layout.showWaitPopup("Generazione file da spedire in corso...");
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PostelDataSource");
			lGwtRestService.call(record, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
									
					Layout.addMessage(new MessageBean("File creato con successo","", MessageType.INFO));
					Layout.hideWaitPopup();
					if("raccomandata".equals(modalitaInvio)) {
						invioRaccomandataButton.hide();
					}
					if("posta prioritaria".equals(modalitaInvio)) {
						invioPostaPrioritariaButton.hide();
					}
					Record lRecord = new Record(vm.getValues());
					RecordList listaDatiPostalizzazione = lRecord.getAttributeAsRecordList("listaDatiPostalizzazione");
					Record record = new Record();
					record.setAttribute("nomeLotto", "In elaborazione...");
					record.setAttribute("statoPostalizzazione", "In invio alla postalizzazione"); 
					record.setAttribute("utentePostalizzazione", lRecord.getAttributeAsString("desUserProtocollo"));
					record.setAttribute("mittente", object.getAttributeAsString("gruppoProtocollantePostelMittente"));
					
					Date now = new Date();
	                DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
	              
					record.setAttribute("dataPostalizzazione",  dateFormatter.format(now));
					if(listaDatiPostalizzazione == null) {
						listaDatiPostalizzazione = new RecordList();
					}
					listaDatiPostalizzazione.add(record);
					
//					if (showTabStatoPostalizzazione()) {
//						TODO gestire il caricamento dei dati di postalizzazione appena inseriti prima di abilitare l'if
//						if (tabStatoPostalizzazione == null) {
//							createTabStatoPostalizzazione(listaDatiPostalizzazione);
//							tabSet.addTab(tabStatoPostalizzazione);
//						}else {
//							tabStatoPostalizzazione.setPane(createTabPane(getLayoutStatoPostalizzazione(listaDatiPostalizzazione)));
//						}
//					}
				}
			});
		} catch(Exception e) {
			Layout.addMessage(new MessageBean("Errore creazione pacchetto: "+e.getMessage(), "", MessageType.ERROR));
			Layout.hideWaitPopup();
		}
	}
	
	public boolean isValidPostel(Record record) {

		isValidazionePostelOk = true;
		boolean showError = false;
		String errorMess = "";
		boolean minimoNumeroDestinatariPresente = false;

		RecordList listaDestinatari = record.getAttributeAsRecordList("listaDestinatari");

		for (int i=0; i < listaDestinatari.getLength(); i++) {

			Record destinatario = listaDestinatari.get(i);

			if(!ProtocollazioneUtil.isDestinatarioInterno(destinatario.getAttribute("tipoDestinatario")) && 
					destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario") != null && 
					destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("mezzoTrasmissioneDestinatario") != null ) { 

				if(("raccomandata".equals(modalitaInvio) && isAbilRaccomandata(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("mezzoTrasmissioneDestinatario")))
						||("posta prioritaria".equals(modalitaInvio) && isAbilPostaPrioritaria(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("mezzoTrasmissioneDestinatario")))){

					minimoNumeroDestinatariPresente = true;

					//controllo identificativo
					if (destinatario.getAttribute("tipoDestinatario").equalsIgnoreCase("PF")) {
						if ((destinatario.getAttribute("nomeDestinatario").length() + destinatario.getAttribute("cognomeDestinatario").length())>44) {
							Layout.addMessage(new MessageBean("Il nominativo supera il limite consentito di caratteri(44), si provvederà a troncarlo", "", MessageType.WARNING));
						}
					}else if (destinatario.getAttribute("denominazioneDestinatario").length()>44) {
						Layout.addMessage(new MessageBean("Il nominativo supera il limite consentito di caratteri(44), si provvederà a troncarlo", "", MessageType.WARNING));
					}

					//indirizzo
					if  (destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("stato") != null && "ITALIA".equalsIgnoreCase(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("stato"))){

						if  ((destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("civico")==null || "".equals(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("civico"))) &&
								(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo")!=null || !"".equals(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo"))) &&
								(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo").contains(","))) {
							destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").setAttribute("civico",destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo").substring(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo").indexOf(",") + 1));
						}

						if  (destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("tipoToponimo")==null || "".equals(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("tipoToponimo"))) 
							errorMess += "-Toponimo non presente<br>";
						if  (destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo")==null || "".equals(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("indirizzo"))) 
							errorMess += "-Indirizzo non presente<br>";
						if  (destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("cap")==null || "".equals(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("cap"))) 
							errorMess += "-Cap non presente<br>";
						if  (destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("civico")==null || "".equals(destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttribute("civico"))) 
							errorMess += "-Civico non presente";
						if  (!errorMess.isEmpty())
						{				
							Layout.addMessage(new MessageBean("Errore nell'inserimento dell'indirizzo:<br> " + errorMess, "", MessageType.ERROR));
							isValidazionePostelOk = false;
							showError = true;
						}else if((destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttributeAsString("tipoToponimo").length() + 
								destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttributeAsString("indirizzo").length() + 
								destinatario.getAttributeAsRecord("mezzoTrasmissioneDestinatario").getAttributeAsString("civico").length()) > 44) 
						{
							Layout.addMessage(new MessageBean("Lunghezza dell'indirizzo eccessiva, l'indirizzo non può essere maggiore di 44 caratteri", "", MessageType.ERROR));
							isValidazionePostelOk = false;
							showError = true;
						}
					}else {
						Layout.addMessage(new MessageBean("Assicurasi che il destinatario non sia estero e/o che l'indirizzo sia stato inserito correttamente", "", MessageType.ERROR));
						isValidazionePostelOk = false;
						showError = true;
					}
					if(showError)
						break;
				}
			}
		}
		if (!minimoNumeroDestinatariPresente) {
			if("raccomandata".equals(modalitaInvio)) {
				Layout.addMessage(new MessageBean("Assicurarsi che sia presente almeno un destinatario con mezzo di trasmissione di tipo Raccomandata", "", MessageType.ERROR));
				isValidazionePostelOk = false;
			}
			if("posta prioritaria".equals(modalitaInvio)) {
				Layout.addMessage(new MessageBean("Assicurarsi che sia presente almeno un destinatario con mezzo di trasmissione di tipo Posta prioritaria", "", MessageType.ERROR));
				isValidazionePostelOk = false;
			}
		}

		//Controllo presenza files
		if  ((record.getAttribute("uriFilePrimario") == null || "".equals(record.getAttribute("uriFilePrimario"))) 
				&&
				(record.getAttributeAsRecordList("listaAllegati")==null || "".equals(record.getAttributeAsRecordList("listaAllegati"))))

		{
			Layout.addMessage(new MessageBean("Postalizzazione non consentita: la registrazione non ha file associati", "", MessageType.ERROR));
			isValidazionePostelOk = false;
		}
		return isValidazionePostelOk;

	}
	
	/*** FEDERICO TROTTA - FINE ***/

	/**
	 * Metodo per costruire il bottone "Verifica"
	 * 
	 */
	protected void createVerificaRegistrazioneButton() {

		verificaRegistrazioneButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_verificaRegistrazioneButton_prompt(),
				"buttons/verificaDati.png");
		verificaRegistrazioneButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickVerificaRegistrazione();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Verifica"
	 * 
	 */
	public void clickVerificaRegistrazione() {

		Record lInputRecord = new Record();
		lInputRecord.setAttribute("datiReg", new Record(vm.getValues()));
		final GWTRestDataSource lVerificaRegDuplicataDataSource = new GWTRestDataSource(
				"VerificaRegDuplicataDataSource");
		lVerificaRegDuplicataDataSource.addData(lInputRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lOutputRecord = response.getData()[0];
					if ("N".equals(lOutputRecord.getAttribute("statoDuplicazione"))) {
						Layout.addMessage(new MessageBean(
								"Non risultano possibili registrazioni duplicate nel periodo considerato", "",
								MessageType.INFO));
					} else {
						if (lOutputRecord.getAttribute("warningMessage") != null
								&& !"".equals(lOutputRecord.getAttribute("warningMessage"))) {
							Layout.addMessage(new MessageBean(lOutputRecord.getAttribute("warningMessage"), "",
									MessageType.WARNING));
						}
						GWTRestDataSource lRegistrazioniDuplicataDataSource = new GWTRestDataSource(
								"RegistrazioniDuplicataDataSource", "idUd", FieldType.TEXT);
						lRegistrazioniDuplicataDataSource.addParam("datiRegXml",
								lOutputRecord.getAttribute("datiRegXml"));
						VerificaRegDuplicataWindow lVerificaRegDuplicataWindow = new VerificaRegDuplicataWindow(
								lRegistrazioniDuplicataDataSource);
						lVerificaRegDuplicataWindow.show();
					}
				}
			}
		});
	}

	/**
	 * Metodo per costruire la finestra di salvataggio del modello
	 * 
	 */
	protected void createSaveModelloWindow(String nomeEntita) {

		if (saveModelloWindow == null) {
			SaveModelloAction saveModelloAction = new SaveModelloAction(modelliDS, modelliSelectItem) {

				@Override
				public Map getMapValuesForAdd() {
					Map values = getValuesNuovaProtComeCopia();
					removeValuesToSkipInModello(values);		
					if (attributiAddDocDetails != null) {
						values.put("valori", getAttributiDinamiciDocForLoad());
					}
					return values;
				}

				@Override
				public Map getMapValuesForUpdate() {
					Map values = getValuesNuovaProtComeCopia();
					removeValuesToSkipInModello(values);  
					if (attributiAddDocDetails != null) {
						values.put("valori", getAttributiDinamiciDocForLoad());
					}
					return values;
				}
			};
			saveModelloWindow = new SaveModelloWindow(
					I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), nomeEntita,
					saveModelloAction) {

				@Override
				public boolean isAbilToSavePublic() {
					return Layout.isPrivilegioAttivo("MRP");
				}
				
				@Override
				public boolean isTrasmissioneAtti() {
					return false;
				}
			};
		}
	}

	/**
	 * Metodo per costruire il bottone "Salva come modello"
	 * 
	 */
	protected void createSalvaComeModelloButton() {

		salvaComeModelloButton = new DetailToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(),
				"buttons/template_save.png");
		salvaComeModelloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Salva come modello"
	 * 
	 */
	public void clickSalvaComeModello() {

		createSaveModelloWindow(nomeEntita);
		if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
			saveModelloWindow.clearValues();
			saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
			saveModelloWindow.redrawForms();
			saveModelloWindow.redraw();
			saveModelloWindow.show();
		}
	}

	/**
	 * Metodo per costruire il bottone "Ricarica dati"
	 * 
	 */
	protected void createReloadDetailButton() {

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(),
				"buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickReloadDetail();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Ricarica dati"
	 * 
	 */
	public void clickReloadDetail() {

		reload(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				reloadDetailCallback();
			}
		});
	}

	/**
	 * Metodo che implementa la callback dell'azione del bottone "Ricarica dati"
	 * 
	 */
	public void reloadDetailCallback() {
		if (editMode != null) {
			if ("protocollaMail".equals(editMode)) {
				protocollaMailMode();
			} else if ("protocollaRegEmergenza".equals(editMode)) {
				protocollaRegEmergenzaMode();
			} else if ("caricaDatiImgAssoc".equals(editMode)) {
				caricaDatiImgAssocMode();
			} else if ("modificaDatiReg".equals(editMode)) {
				modificaDatiRegMode();
			} else if ("caricaDatiImgAssoc".equals(editMode)) {
				modificaDatiRegMode();
			} else if ("aggiuntaFile".equals(editMode)) {
				aggiuntaFileMode();
			} else if ("modificaDati".equals(editMode)) {
				modificaDatiMode();
			} else if ("modificaRegAccessoCivico".equals(editMode)) {
				modificaRegAccessoCivicoMode();
			} else if ("modificaDatiExtraIter".equals(editMode)) {
				modificaDatiExtraIterMode();
			} else {
				viewMode();
			}
		} else {
			viewMode();
		}
	}

	/**
	 * Metodo per costruire il bottone "Annulla"
	 * 
	 */
	protected void createUndoButton() {

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickUndo();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Annulla"
	 * 
	 */
	public void clickUndo() {

		reload(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				viewMode();
			}
		});
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone "Scarica files come zip"
	 * 
	 */
	public boolean showDownloadDocZipButton(Record record) {

		boolean filePrimarioExists = record.getAttribute("uriFilePrimario") != null
				&& !record.getAttribute("uriFilePrimario").isEmpty();
		boolean allegatiExist = record.getAttributeAsRecordList("listaAllegati") != null
				&& record.getAttributeAsRecordList("listaAllegati").getLength() > 0;
		if (allegatiExist) {
			for (int i = 0; i < record.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				Record currentAllegato = record.getAttributeAsRecordList("listaAllegati").get(i);
				String currentUriFileAllegato = currentAllegato.getAttribute("uriFileAllegato");
				if (currentUriFileAllegato == null || currentUriFileAllegato.isEmpty()) {
					allegatiExist = Boolean.FALSE;
					break;
				}
			}
		}
		return filePrimarioExists || allegatiExist;
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE SMISTA
	 */
	public boolean showSmistaButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilSmista");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE SMISTACC
	 */
	public boolean showSmistaCCButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilSmistaCC");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE Assegnazioni/invii cononsc
	 */
	public boolean showModAssInviiCCButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilModAssInviiCC");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE ASSEGNAZIONE
	 */
	public boolean showAssegnazioneButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilAssegnazioneSmistamento");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE INVIO AL PROTOCOLLO
	 */
	public boolean showInvioAlProtocolloButton(Record record) {
		if(record != null && record.getAttributeAsRecordList("listaDestinatariUoProtocollazione") != null &&
				record.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() > 0) {
			for(int i=0; i < record.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength(); i++) {
				Record item = record.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(i);
				if(item.getAttributeAsString("idUo") != null && !"".equalsIgnoreCase(item.getAttributeAsString("idUo"))){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE RISPONDI
	 */
	public boolean showRispondiButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilRispondi");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE ASSEGNAZIONE
	 */
	public boolean showCondivisioneButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilCondivisione");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE MODIFICA/ANNULLA ASS./INVII CC
	 */
	public boolean showModAnnInviiEffettuatiButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilModAssInviiCC");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE OSSERVAZIONI NOTIFICHE
	 */
	public boolean showOsservazioniNotifiche(Record record){
		return record != null && record.getAttributeAsBoolean("abilOsservazioniNotifiche");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE REVOCA ATTO
	 */
	public boolean showRevocaAttoButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilRevocaAtto");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLAZIONE ENTRATA
	 */
	public boolean showProtocollazioneEntrataButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilProtocollazioneEntrata");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLAZIONE USCITA
	 */
	public boolean showProtocollazioneUscitaButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilProtocollazioneUscita");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLAZIONE INTERNA
	 */
	public boolean showProtocollazioneInternaButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilProtocollazioneInterna");
	}	
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE FIRMA
	 */
	public boolean showFirmaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilFirma");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PROTOCOLLA & FIRMA
	 */
	public boolean showFirmaProtocollaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilFirmaProtocolla");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE PUBBL. AMM. TRASP.
	 */
	public boolean showPubblicazioneTraspAmm(Record record){
		return record != null && record.getAttributeAsBoolean("abilPubblicazioneTraspAmm");
	}
	
	/**
	 * /**
	 *  Metodo per costruire la freccia che apre il Menu per le azioni di download file
	 * 
	 */
	protected void createFrecciaDownloadZipButton() {
		frecciaDownloadZipButton = new FrecciaDetailToolStripButton();
		frecciaDownloadZipButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickFrecciaDownload();
			}
		});
	}
	
	
	/**
	* Metodo che implementa l'azione del bottone "Scarica files come zip"
	*/
	public void clickFrecciaDownload() {
		
		final Menu downloadZipMenu = new Menu();
		MenuItem scaricaFileMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_originali(), "buttons/download_zip.png");
		scaricaFileMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickDownloadDocZip("scaricaOriginali");
			}
		});
		downloadZipMenu.addItem(scaricaFileMenuItem);
		
		if(showOperazioniTimbratura()) {
			MenuItem scaricaFileTimbratiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_segnatura(), "buttons/download_zip.png");
			scaricaFileTimbratiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadDocZip("scaricaTimbratiSegnatura");
				}
			});
			downloadZipMenu.addItem(scaricaFileTimbratiSegnaturaMenuItem);
	
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA")) {
				MenuItem scaricaFileTimbratiConformeStampaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_stampa(), "buttons/download_zip.png");
				scaricaFileTimbratiConformeStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadDocZip("scaricaTimbratiConformeStampa");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeStampaMenuItem);
	
				MenuItem scaricaFileTimbratiConformeDigitaleMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_digitale(),"buttons/download_zip.png");
				scaricaFileTimbratiConformeDigitaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadDocZip("scaricaTimbratiConformeDigitale");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeDigitaleMenuItem);
			}
	
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				MenuItem scaricaFileTimbratiConformeCartaceoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(),"buttons/download_zip.png");
				scaricaFileTimbratiConformeCartaceoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadDocZip("scaricaTimbratiConformeCartaceo");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeCartaceoMenuItem);
			}
		
		}
		
		if(Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem scaricaFileConformitaCustomMenuItem = new MenuItem("File " + labelConformitaCustom, "buttons/download_zip.png");
			scaricaFileConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadDocZip("scaricaConformitaCustom");
				}
			});
			downloadZipMenu.addItem(scaricaFileConformitaCustomMenuItem);
		}

		MenuItem scaricaFileSbustatiMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_sbustati(),"buttons/download_zip.png");
		scaricaFileSbustatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickDownloadDocZip("scaricaSbustati");
			}
		});
		downloadZipMenu.addItem(scaricaFileSbustatiMenuItem);

		downloadZipMenu.showContextMenu();
	}
	
	/**
	 * Metodo per costruire il bottone "Scarica files come zip"
	 * 
	 */
	protected void createDownloadDocZipButton() {

		downloadDocZipButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_list_downloadDocsZip(),
				"buttons/download_zip.png");
		downloadDocZipButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickDownloadDocZip("scaricaOriginali");
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Scarica files come zip"
	 * 
	 */
	public void clickDownloadDocZip(String operazione) {
		
		if(!"scaricaOriginali".equalsIgnoreCase(operazione) && !"scaricaSbustati".equalsIgnoreCase(operazione) && !"scaricaConformitaCustom".equalsIgnoreCase(operazione)) {
			if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaTimbroDocZip")) {
				showOpzioniTimbratura(operazione);
			}else {
				String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
				String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
				String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");
				
				Record opzioniTimbro = new Record();
				opzioniTimbro.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				opzioniTimbro.setAttribute("posizioneTimbro", posizioneTimbroPref);
				opzioniTimbro.setAttribute("tipoPagina", tipoPaginaPref);
				
				manageGenerateDocZip(operazione, opzioniTimbro);
				
			}
		}else {
			manageGenerateDocZip(operazione, null);
		}
	}
	
	public void showOpzioniTimbratura(final String operazione) {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record opzioniDefaultTimbro = new Record();
		opzioniDefaultTimbro.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		opzioniDefaultTimbro.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		opzioniDefaultTimbro.setAttribute("tipoPaginaPref", tipoPaginaPref);
		opzioniDefaultTimbro.setAttribute("scaricoZip", true);

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(opzioniDefaultTimbro, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				Record impostazioniTimbratura = new Record();
				impostazioniTimbratura.setAttribute("posizioneTimbro", object.getAttribute("posizioneTimbro"));
				impostazioniTimbratura.setAttribute("rotazioneTimbro", object.getAttribute("rotazioneTimbro"));
				impostazioniTimbratura.setAttribute("tipoPagina", object.getAttribute("tipoPaginaTimbro"));

				manageGenerateDocZip(operazione, impostazioniTimbratura);
			}			
		});
		
		apponiTimbroWindow.show();
	}

	/**
	 * @param operazione
	 * @param object 
	 */
	public void manageGenerateDocZip(String operazione, Record impostazioniTimbratura) {
		final Record record = new Record(vm.getValues());
		String segnatura = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura")
				: getEstremiProtocolloFromRecord(record);
		GWTRestDataSource datasource = new GWTRestDataSource("ProtocolloDataSource");
		datasource.extraparam.put("segnatura", segnatura);
		datasource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
		datasource.extraparam.put("operazione", operazione);
		datasource.setForceToShowPrompt(false);
		
		if(impostazioniTimbratura!=null) {
			datasource.extraparam.put("posizioneTimbro", impostazioniTimbratura.getAttribute("posizioneTimbro"));
			datasource.extraparam.put("rotazioneTimbro", impostazioniTimbratura.getAttribute("rotazioneTimbro"));
			datasource.extraparam.put("tipoPagina", impostazioniTimbratura.getAttribute("tipoPagina"));
		}
		
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
		datasource.executecustom("generateDocsZip", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record result = response.getData()[0];
				String message = result.getAttribute("message");
				String warningTimbro = result.getAttribute("warningTimbro");
				String warningSbusta = result.getAttribute("warningSbusta");

				if (message != null) {
					Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
				} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
					String zipUri = response.getData()[0].getAttribute("storageZipRemoteUri");
					String zipName = response.getData()[0].getAttribute("zipName");
					
					scaricaFile(zipName, zipUri, zipUri);
					
					if (warningTimbro != null && !"".equals(warningTimbro)) {
						Layout.addMessage(new MessageBean(warningTimbro, "", MessageType.WARNING));
					}else if (warningSbusta != null && !"".equals(warningSbusta)) {
						Layout.addMessage(new MessageBean(warningSbusta, "", MessageType.WARNING));
					}
				}
			}
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "Smista"
	 */
	protected void createSmistaButton() {
		smistaButton = new DetailToolStripButton("Smista","archivio/smistamento.png");
		smistaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buildSmistaMenu();
			}
			
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "SmistaCC"
	 */
	protected void createSmistaCCButton() {
		smistaCCButton = new DetailToolStripButton("Smista","archivio/smistamentoCC.png");
		smistaCCButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buildSmistaCCMenu();
			}
			
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "Assegnazioni / invii conosc."
	 */
	protected void createAssegnaCondividiButton() {

		assegnaCondividiButton = new DetailToolStripButton("Assegnazioni/invii conosc","archivio/assegna.png");
		assegnaCondividiButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buildFrecciaAssegnaCondividiMenu();
			}
			
		});
	}
	
	/**
	 *  Metodo per costruire la freccia del bottone "Assegnazioni / invii conosc."
	 */
	protected void createFrecciaAssegnaCondividiButton() {

		frecciaAssegnaCondividiButton = new FrecciaDetailToolStripButton();
		frecciaAssegnaCondividiButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				buildFrecciaAssegnaCondividiMenu();
			}
			
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "Invio al protocollo"
	 */
	protected void createInvioAlProtocolloButton() {

		invioAlProtocolloButton = new DetailToolStripButton("Invio al protocollo","archivio/invio_al_protocollo.png");
		invioAlProtocolloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickInvioAlProtocolloMenu();
			}
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "Invio al protocollo"
	 */
	protected void createFrecciaInvioAlProtocolloButton() {

		frecciaInvioAlProtocolloButton = new FrecciaDetailToolStripButton();
		frecciaInvioAlProtocolloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickFrecciaInvioAlProtocolloMenu();
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Smista"
	 */
	public void buildSmistaMenu() {
		final Record detailRecord = new Record(vm.getValues());
		Record recordDestPref = new Record();						
		RecordList listaAzioniRapide = new RecordList();
		Record recordAzioneSmista = new Record();
		recordAzioneSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
		listaAzioniRapide.add(recordAzioneSmista);
		recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
		recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
		lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
				new DSCallback() {

					@Override
					public void execute(DSResponse responseDestPref, Object rawDataDestPref,
							DSRequest requestDestPref) {
						if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record destinatariPreferiti = responseDestPref.getData()[0];
							if (AurigaLayout.getIsAttivaAccessibilita()) {
								clickActionSmistaStandard(destinatariPreferiti);		
							} else {
							clickSmista(destinatariPreferiti);
						}
					}
					}
				}, new DSRequest());
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Smista"
	 */
	public void buildSmistaCCMenu() {
		final Record detailRecord = new Record(vm.getValues());
		Record recordDestPref = new Record();						
		RecordList listaAzioniRapideSmistaCC = new RecordList();
		Record recordAzioneSmistaCC = new Record();
		recordAzioneSmistaCC.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
		listaAzioniRapideSmistaCC.add(recordAzioneSmistaCC);
		recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapideSmistaCC);										
		recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
		lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
				new DSCallback() {

					@Override
					public void execute(DSResponse responseDestPref, Object rawDataDestPref,
							DSRequest requestDestPref) {
						if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record destinatariPreferiti = responseDestPref.getData()[0];
							if (AurigaLayout.getIsAttivaAccessibilita()) {
								clickActionSmistaCCStandard(destinatariPreferiti);		
							} else {
							clickSmistaCC(destinatariPreferiti);
						}
					}
					}
				}, new DSRequest());
	}
	
	/**
	 * Metodo che implementa l'azione del bottone e della freccia di "Assegnazioni / invii conosc."
	 */
	public void buildFrecciaAssegnaCondividiMenu() {
		final Record detailRecord = new Record(vm.getValues());
		Record recordDestPref = new Record();						
		RecordList listaAzioniRapide = new RecordList();
		Record recordAzioneRapidaAssegna = new Record();
		recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
		listaAzioniRapide.add(recordAzioneRapidaAssegna);
		Record recordAzioneRapidaInvioCC = new Record();
		recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
		listaAzioniRapide.add(recordAzioneRapidaInvioCC);
		recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
		recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
		lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
				new DSCallback() {

					@Override
					public void execute(DSResponse responseDestPref, Object rawDataDestPref,
							DSRequest requestDestPref) {
						if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record destinatariPreferiti = responseDestPref.getData()[0];
//							if (AurigaLayout.getIsAttivaAccessibilita()) {
//								clickActionAssegnazioneStandard(destinatariPreferiti);		
//							} else {
							clickAssegnaCondividi(destinatariPreferiti);
//							}
					}
					}
				}, new DSRequest());
	}
	
	/**
	 * Metodo che implementa l'azione del bottone di "Invio al protocollo"
	 */
	public void clickInvioAlProtocolloMenu() {
		
		final Record detailRecord = new Record(vm.getValues());		
		if(detailRecord != null && detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione") != null) {
			if(detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() == 1 ) {	
				String idUo = detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(0).getAttributeAsString("idUo");
				clickInvioAlProtocolloRapido(detailRecord, idUo, "U");	
			} else if(detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() > 1 ) {
				final Menu creaInvioAlProtocollo = new Menu();
				List<MenuItem> listaMenuItem = new ArrayList<MenuItem>();
				for(int i=0; i < detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength(); i++) {
					Record currentRecord = detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(i);
					final String idUo = currentRecord.getAttributeAsString("idUo");
					
					MenuItem item = new MenuItem(currentRecord.getAttributeAsString("descrizione"));
					final Menu scelteRapide = new Menu();
					MenuItem invioAlProtocolloMenuRapidoItem = new MenuItem("Rapido");
					invioAlProtocolloMenuRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickInvioAlProtocolloRapido(detailRecord, idUo, "U");						
						}
					});
					
					MenuItem invioAlProtocolloMenuMessaggioItem = new MenuItem("Con messaggio");
					invioAlProtocolloMenuMessaggioItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
						@Override
						public void onClick(MenuItemClickEvent event) {
							
							final DestUOProtocollazioneWindow destUOProtocollazioneWindow = new DestUOProtocollazioneWindow() {
								
								@Override
								public void onClickOkButton(Record record, DSCallback callback) {
									String messaggioInvio = record != null && record.getAttributeAsString("messaggioInvio") != null
											? record.getAttributeAsString("messaggioInvio") : null;
									clickInvioAlProtocolloConMessaggio(detailRecord, idUo, "U", messaggioInvio, callback);
								};
							};
							destUOProtocollazioneWindow.show();
						}
					});
					
					scelteRapide.addItem(invioAlProtocolloMenuRapidoItem);
					scelteRapide.addItem(invioAlProtocolloMenuMessaggioItem);
					
					item.setSubmenu(scelteRapide);
					listaMenuItem.add(item);
				}
				
				for(MenuItem menuItem : listaMenuItem) {
					creaInvioAlProtocollo.addItem(menuItem);
				}
				
				creaInvioAlProtocollo.showContextMenu();
			}
		}
	}
	
	/**
	 * Metodo che implementa l'azione del della freccia di "Invio al protocollo"
	 */
	public void clickFrecciaInvioAlProtocolloMenu() {
		final Record detailRecord = new Record(vm.getValues());
		final Menu creaInvioAlProtocollo = new Menu(); 
		
		if(detailRecord != null && detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione") != null) {
			if(detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() == 1 ) {
				// Voce menu Invio al protocollo - Rapido 
				
				final String idUo = detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(0).getAttributeAsString("idUo");
				
				MenuItem invioAlProtocolloRapidoItem = new MenuItem("Rapido");
				invioAlProtocolloRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {						
						clickInvioAlProtocolloRapido(detailRecord, idUo, "U");
					}
				});
				
				MenuItem invioAlProtocolloMessaggioItem = new MenuItem("Con messaggio");
				invioAlProtocolloMessaggioItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						final DestUOProtocollazioneWindow destUOProtocollazioneWindow = new DestUOProtocollazioneWindow() {
							
							@Override
							public void onClickOkButton(Record record, DSCallback callback) {
								String messaggioInvio = record != null && record.getAttributeAsString("messaggioInvio") != null
										? record.getAttributeAsString("messaggioInvio") : null;
								clickInvioAlProtocolloConMessaggio(detailRecord, idUo, "U", messaggioInvio, callback);
							};
						};
						destUOProtocollazioneWindow.show();
					}
				});
				
				creaInvioAlProtocollo.addItem(invioAlProtocolloRapidoItem);
				creaInvioAlProtocollo.addItem(invioAlProtocolloMessaggioItem);
				
				creaInvioAlProtocollo.showContextMenu();
			} else if(detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength() > 1 ) {
				List<MenuItem> listaMenuItem = new ArrayList<MenuItem>();
				for(int i=0; i < detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").getLength(); i++) {
					Record currentRecord = detailRecord.getAttributeAsRecordList("listaDestinatariUoProtocollazione").get(i);
					final String idUo = currentRecord.getAttributeAsString("idUo");
					MenuItem item = new MenuItem(currentRecord.getAttributeAsString("descrizione"));
					final Menu scelteRapide = new Menu();
					
					MenuItem invioAlProtocolloRapidoItem = new MenuItem("Rapido");
					invioAlProtocolloRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickInvioAlProtocolloRapido(detailRecord, idUo, "U");						
						}
					});
					
					MenuItem invioAlProtocolloMessaggioItem = new MenuItem("Con messaggio");
					invioAlProtocolloMessaggioItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
						@Override
						public void onClick(MenuItemClickEvent event) {
							
							final DestUOProtocollazioneWindow destUOProtocollazioneWindow = new DestUOProtocollazioneWindow() {
								
								@Override
								public void onClickOkButton(Record record, DSCallback callback) {
									String messaggioInvio = record != null && record.getAttributeAsString("messaggioInvio") != null
											? record.getAttributeAsString("messaggioInvio") : null;
									clickInvioAlProtocolloConMessaggio(detailRecord, idUo, "U", messaggioInvio, callback);
								};
							};
							destUOProtocollazioneWindow.show();
						}
					});
					
					scelteRapide.addItem(invioAlProtocolloRapidoItem);
					scelteRapide.addItem(invioAlProtocolloMessaggioItem);
					
					item.setSubmenu(scelteRapide);
					listaMenuItem.add(item);
				}
				
				for(MenuItem menuItem : listaMenuItem) {
					creaInvioAlProtocollo.addItem(menuItem);
				}
				
				creaInvioAlProtocollo.showContextMenu();
			}
		}
	}

	private void clickInvioAlProtocolloRapido(final Record detailRecord, final String idUo, final String flgUdFolder) {
		
		final RecordList listaUdFolder = new RecordList();
		
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", flgUdFolder);
		}
		listaUdFolder.add(detailRecord);
		RecordList listaAssegnazioni = new RecordList();
		Record recordAssegnazioni = new Record();
		recordAssegnazioni.setAttribute("idUo", idUo);
		recordAssegnazioni.setAttribute("typeNodo","UO");
		listaAssegnazioni.add(recordAssegnazioni);
		
		final Record record = new Record();
		record.setAttribute("flgUdFolder", flgUdFolder);
		record.setAttribute("listaRecord", listaUdFolder);
		record.setAttribute("motivoInvio", "PP");
		record.setAttribute("listaAssegnazioni", listaAssegnazioni);
		
		Layout.showWaitPopup("Invio al protocollo in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					operationCallback(response, detailRecord,
							"idUdFolder", "Invio al protocollo effettuato con successo",
							"Si è verificato un errore durante l'invio al protocollo!", new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {														
							
							reload(new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									if(layout != null) {
										layout.viewMode();
									} else {
										viewMode();
									}
									setSaved(true);
								}
							});
						}
					});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}				
	}
	
	private void clickInvioAlProtocolloConMessaggio(final Record detailRecord, final String idUo,
			final String flgUdFolder, final String messaggioInvio, final DSCallback callback) {
	
		final RecordList listaUdFolder = new RecordList();
		
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", flgUdFolder);
		}
		listaUdFolder.add(detailRecord);
		RecordList listaAssegnazioni = new RecordList();
		Record recordAssegnazioni = new Record();
		recordAssegnazioni.setAttribute("idUo", idUo);
		recordAssegnazioni.setAttribute("typeNodo","UO");
		listaAssegnazioni.add(recordAssegnazioni);
		
		final Record record = new Record();
		record.setAttribute("flgUdFolder", flgUdFolder);
		record.setAttribute("messaggioInvio", messaggioInvio);
		record.setAttribute("motivoInvio", "PP");
		record.setAttribute("listaRecord", listaUdFolder);
		record.setAttribute("listaAssegnazioni", listaAssegnazioni);
		
		Layout.showWaitPopup("Invio al protocollo in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					operationCallback(response, detailRecord,
							"idUdFolder", "Invio al protocollo effettuato con successo",
							"Si è verificato un errore durante l'invio al protocollo!", new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {														
							
							if(callback != null){
								callback.execute(response, rawData, request);
							}
							reload(new DSCallback() {
								@Override
								public void execute(DSResponse response, Object rawData,
										DSRequest request) {
									if(layout != null) {
										layout.viewMode();
									} else {
										viewMode();
									}
									setSaved(true);
								}
							});
						}
					});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}				
	}
	
	public void clickActionSmistaStandard(final Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		RecordList listaUOPreferitiSmista = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
		RecordList listaUtentiPreferitiSmista = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));

		boolean noMenuRapidoSmista = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Smistamento 
		final RecordList listaPreferitiSmista = new RecordList(); // contiene tutti i preferiti per lo smistamento da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferitiSmista != null && !listaUOPreferitiSmista.isEmpty()){
			listaPreferitiSmista.addList(listaUOPreferitiSmista.toArray());
			noMenuRapidoSmista = false;
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferitiSmista != null && !listaUtentiPreferitiSmista.isEmpty()){
				listaPreferitiSmista.addList(listaUtentiPreferitiSmista.toArray());
				noMenuRapidoSmista = false;
			}
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		clickSmistamentoStandard(detailRecord, listaPreferitiSmista, codSupportoOrig, listaAssPreselMitt);
	}
	
	public void clickActionSmistaCCStandard(final Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		RecordList listaUOPreferitiSmistaCC = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
		RecordList c = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));

		boolean noMenuRapidoSmistaCC = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Smistamento 
		final RecordList listaPreferitiSmistaCC = new RecordList(); // contiene tutti i preferiti per lo smistamento da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferitiSmistaCC != null && !listaUOPreferitiSmistaCC.isEmpty()){
			listaPreferitiSmistaCC.addList(listaUOPreferitiSmistaCC.toArray());
			noMenuRapidoSmistaCC = false;
		}
		
		if(!flgSoloUo) {
			if(listaUOPreferitiSmistaCC != null && !listaUOPreferitiSmistaCC.isEmpty()){
				listaPreferitiSmistaCC.addList(listaUOPreferitiSmistaCC.toArray());
				noMenuRapidoSmistaCC = false;
			}
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		clickSmistamentoCCStandard(detailRecord, listaPreferitiSmistaCC, codSupportoOrig, listaAssPreselMitt);
	}
	
	public void clickActionAssegnazioneStandard (final Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		RecordList listaUOPreferitiAssegna = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		RecordList listaUtentiPreferitiAssegna = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		RecordList listaUOPreferitiCondividi = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		RecordList listaUtentiPreferitiCondividi = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));

		boolean noMenuRapidoAssegna = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Assegnazione 
		final RecordList listaPreferitiAssegna = new RecordList(); // contiene tutti i preferiti per l'assegnazione da visualizzare
		boolean noMenuRapidoCondividi = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Condivisione
		final RecordList listaPreferitiCondividi = new RecordList(); // contiene tutti i preferiti per la condivisione da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferitiAssegna != null && !listaUOPreferitiAssegna.isEmpty()){
			listaPreferitiAssegna.addList(listaUOPreferitiAssegna.toArray());
			noMenuRapidoAssegna = false;
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferitiAssegna != null && !listaUtentiPreferitiAssegna.isEmpty()){
				listaPreferitiAssegna.addList(listaUtentiPreferitiAssegna.toArray());
				noMenuRapidoAssegna = false;
			}
		}
		
		if(listaUOPreferitiCondividi != null && !listaUOPreferitiCondividi.isEmpty()){
			listaPreferitiCondividi.addList(listaUOPreferitiCondividi.toArray());
			noMenuRapidoCondividi = false;
		}
		
		if(listaUtentiPreferitiCondividi != null && !listaUtentiPreferitiCondividi.isEmpty()){
			listaPreferitiCondividi.addList(listaUtentiPreferitiCondividi.toArray());
			noMenuRapidoCondividi = false;
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		clickAssegnazioneStandard(detailRecord, listaPreferitiAssegna, codSupportoOrig, listaAssPreselMitt);
	}
	
	/**
	 * Metodo che implementa le voci del menu di "Smista"
	 */
	public void clickSmista(final Record destinatariPreferiti){
		
		final Record detailRecord = new Record(vm.getValues());
		
		final Menu creaSmista = new Menu(); 
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			creaSmista.setTabIndex(null);
			creaSmista.setCanFocus(true);		
		}
		
		RecordList listaUOPreferitiSmista = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
		RecordList listaUtentiPreferitiSmista = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));

		boolean noMenuRapidoSmista = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Smistamento
		final RecordList listaPreferitiSmista = new RecordList(); // contiene tutti i preferiti per lo smistamento da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferitiSmista != null && !listaUOPreferitiSmista.isEmpty()){
			listaPreferitiSmista.addList(listaUOPreferitiSmista.toArray());
			noMenuRapidoSmista = false;
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferitiSmista != null && !listaUtentiPreferitiSmista.isEmpty()){
				listaPreferitiSmista.addList(listaUtentiPreferitiSmista.toArray());
				noMenuRapidoSmista = false;
			}
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		
		// Voce menu Smista - Standard 
		MenuItem smistaMenuStandardItem = new MenuItem("Standard");
		smistaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickSmistamentoStandard(detailRecord, listaPreferitiSmista, codSupportoOrig, listaAssPreselMitt);
			}
		});

		creaSmista.addItem(smistaMenuStandardItem);

		// Voce menu Smista - Rapido 
		MenuItem smistaMenuRapidoItem = new MenuItem("Rapido");

		if(success != null && success == true){

			Menu scelteRapide = new Menu();

			if(noMenuRapidoSmista){
				smistaMenuRapidoItem.setEnabled(false);
			} else {
				buildMenuRapidoSmistamento(detailRecord, "U", listaPreferitiSmista, scelteRapide);
				smistaMenuRapidoItem.setSubmenu(scelteRapide);
			}

		} else {
			smistaMenuRapidoItem.setEnabled(false);
		}
		creaSmista.addItem(smistaMenuRapidoItem);
		
		creaSmista.showContextMenu();
		
	}
	
	/**
	 * Metodo che implementa le voci del menu di "SmistaCC"
	 */
	public void clickSmistaCC(final Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		final Menu creaSmistaCC = new Menu(); 
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			creaSmistaCC.setTabIndex(null);
			creaSmistaCC.setCanFocus(true);		
		}
		
		RecordList listaUOPreferitiSmistaCC = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
		RecordList listaUtentiPreferitiSmistaCC = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));

		boolean noMenuRapidoSmistaCC = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Smistamento
		final RecordList listaPreferitiSmistaCC = new RecordList(); // contiene tutti i preferiti per lo smistamento da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferitiSmistaCC != null && !listaUOPreferitiSmistaCC.isEmpty()){
			listaPreferitiSmistaCC.addList(listaUOPreferitiSmistaCC.toArray());
			noMenuRapidoSmistaCC = false;
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferitiSmistaCC != null && !listaUtentiPreferitiSmistaCC.isEmpty()){
				listaPreferitiSmistaCC.addList(listaUtentiPreferitiSmistaCC.toArray());
				noMenuRapidoSmistaCC = false;
			}
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		
		// Voce menu Smista - Standard 
		MenuItem smistaCCMenuStandardItem = new MenuItem("Standard");
		smistaCCMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickSmistamentoCCStandard(detailRecord, listaPreferitiSmistaCC, codSupportoOrig, listaAssPreselMitt);
			}
		});

		creaSmistaCC.addItem(smistaCCMenuStandardItem);

		// Voce menu Smista - Rapido 
		MenuItem smistaCCMenuRapidoItem = new MenuItem("Rapido");

		if(success != null && success == true){

			Menu scelteRapide = new Menu();

			if(noMenuRapidoSmistaCC){
				smistaCCMenuRapidoItem.setEnabled(false);
			} else {
				buildMenuRapidoSmistamentoCC(detailRecord, "U", listaPreferitiSmistaCC, scelteRapide);
				smistaCCMenuRapidoItem.setSubmenu(scelteRapide);
			}

		} else {
			smistaCCMenuRapidoItem.setEnabled(false);
		}
		creaSmistaCC.addItem(smistaCCMenuRapidoItem);
		
		creaSmistaCC.showContextMenu();
		
	}
	
	/**
	 * Metodo che implementa le voci del menu di "Assegnazioni / invii conosc."
	 */
	public void clickAssegnaCondividi(final Record destinatariPreferiti){
		
		final Record detailRecord = new Record(vm.getValues());
		
		final Menu creaAssegnaCondividi = new Menu(); 
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			creaAssegnaCondividi.setTabIndex(null);
			creaAssegnaCondividi.setCanFocus(true);		
		}
		
		RecordList listaUOPreferitiAssegna = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		RecordList listaUtentiPreferitiAssegna = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		RecordList listaUOPreferitiCondividi = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		RecordList listaUtentiPreferitiCondividi = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));

		boolean noMenuRapidoAssegna = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Assegnazione 
		final RecordList listaPreferitiAssegna = new RecordList(); // contiene tutti i preferiti per l'assegnazione da visualizzare
		boolean noMenuRapidoCondividi = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di Condivisione
		final RecordList listaPreferitiCondividi = new RecordList(); // contiene tutti i preferiti per la condivisione da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferitiAssegna != null && !listaUOPreferitiAssegna.isEmpty()){
			listaPreferitiAssegna.addList(listaUOPreferitiAssegna.toArray());
			noMenuRapidoAssegna = false;
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferitiAssegna != null && !listaUtentiPreferitiAssegna.isEmpty()){
				listaPreferitiAssegna.addList(listaUtentiPreferitiAssegna.toArray());
				noMenuRapidoAssegna = false;
			}
		}
		
		if(listaUOPreferitiCondividi != null && !listaUOPreferitiCondividi.isEmpty()){
			listaPreferitiCondividi.addList(listaUOPreferitiCondividi.toArray());
			noMenuRapidoCondividi = false;
		}
		
		if(listaUtentiPreferitiCondividi != null && !listaUtentiPreferitiCondividi.isEmpty()){
			listaPreferitiCondividi.addList(listaUtentiPreferitiCondividi.toArray());
			noMenuRapidoCondividi = false;
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");

		if (showAssegnazioneButton(detailRecord)) {
			// Voce menu Assegna - Standard 
			MenuItem assegnaMenuStandardItem = new MenuItem("Nuova assegn. competenza - Standard");
			assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickAssegnazioneStandard(detailRecord, listaPreferitiAssegna, codSupportoOrig, listaAssPreselMitt);
				}
			});

			creaAssegnaCondividi.addItem(assegnaMenuStandardItem);

			// Voce menu Assegna - Rapido 
			MenuItem assegnaMenuRapidoItem = new MenuItem("Nuova assegn. competenza - Rapida");

			if(success != null && success == true){

				Menu scelteRapide = new Menu();

				if(noMenuRapidoAssegna){
					assegnaMenuRapidoItem.setEnabled(false);
				} else {
					buildMenuRapidoAssegnazione(detailRecord, "U", listaPreferitiAssegna, scelteRapide);
					assegnaMenuRapidoItem.setSubmenu(scelteRapide);
				}

			} else {
				assegnaMenuRapidoItem.setEnabled(false);
			}
			creaAssegnaCondividi.addItem(assegnaMenuRapidoItem);
		}
		
		if (showCondivisioneButton(detailRecord)) {
			
			// Invio per conoscenza Standard
			MenuItem condivisioneMenuStandardItem = new MenuItem("Nuovo invio cc - Standard");
			condivisioneMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {

					clickCondivisioneStandard(detailRecord, listaPreferitiCondividi);
				}
			});
			creaAssegnaCondividi.addItem(condivisioneMenuStandardItem);

			// Invio per conoscenza Rapida
			MenuItem condivisioneMenuRapidoItem = new MenuItem("Nuovo invio cc - Rapido");

			if(success != null && success == true) {

				Menu scelteRapide = new Menu();					

				if(noMenuRapidoCondividi){
					condivisioneMenuRapidoItem.setEnabled(false);
				} else {
					buildMenuRapidoCondivisione(detailRecord, "U", listaPreferitiCondividi, scelteRapide);
					condivisioneMenuRapidoItem.setSubmenu(scelteRapide);
				}

			} else {
				condivisioneMenuRapidoItem.setEnabled(false);
			}
			creaAssegnaCondividi.addItem(condivisioneMenuRapidoItem);
		}
		
		// MODIFICA/ANNULLA ASS./INVII CC
		if (showModAnnInviiEffettuatiButton(detailRecord)) {
			
			MenuItem inviiEffettuatiMenuItem = new MenuItem("Modifica/annulla");
			inviiEffettuatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("InviiEffettuatiDataSource", "idInvioNotifica", FieldType.TEXT);
					lGWTRestDataSource.addParam("flgUdFolder", "U");
					lGWTRestDataSource.addParam("idUdFolder", detailRecord.getAttribute("idUd"));
					lGWTRestDataSource.fetchData(null, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								RecordList data = response.getDataAsRecordList();
								if (data.getLength() == 0) {
									Layout.addMessage(new MessageBean("Non risultano invii effettuati da te o dalla/e UO cui sei associato", "", MessageType.INFO));
								} else if (data.getLength() > 0) {
									String codSupportoOrig = detailRecord != null && detailRecord.getAttributeAsString("codSupportoOrig") != null ? 
											detailRecord.getAttributeAsString("codSupportoOrig") : null;						
									Record recordToPrint = new Record();
									recordToPrint.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
									recordToPrint.setAttribute("flgUdFolder", "U");
									recordToPrint.setAttribute("flgTipoProv", getFlgTipoProv());
									recordToPrint.setAttribute("estremiUdFolder", detailRecord.getAttribute("segnatura"));														
									recordToPrint.setAttribute("codSupportoOrig", codSupportoOrig);
									if (isConProtocolloParticolare()) {
										recordToPrint.setAttribute("segnaturaXOrd", "2-");
									} else if (isConProtocolloGenerale()) {
										recordToPrint.setAttribute("segnaturaXOrd", "1-");
									} else {
										recordToPrint.setAttribute("segnaturaXOrd", ""); 
									}
									recordToPrint.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));
									recordToPrint.setAttribute("idDoc", detailRecord.getAttribute("idDocPrimario"));									
									String title = "Invii effettutati sul documento " + detailRecord.getAttribute("segnatura");
									InviiEffettuatiWindow inviiEffettuatiPopup = new InviiEffettuatiWindow(lGWTRestDataSource, recordToPrint, title) {
	
										@Override
										public void manageOnCloseClick() {
											super.manageOnCloseClick();
											reload(new DSCallback() {
	
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													setSaved(true);
													viewMode();
												}
											});
										}
									};
									inviiEffettuatiPopup.show();
								}
							}
						}
					});
				}
			});
			creaAssegnaCondividi.addItem(inviiEffettuatiMenuItem);
		}
		creaAssegnaCondividi.showContextMenu();
	}
	
//	/**
//	 *  Metodo per costruire il bottone "Assegnazione"
//	 */
//	protected void createAssegnazioneButton() {
//
//		assegnazioneButton = new DetailToolStripButton("Assegna","archivio/assegna.png");
//		assegnazioneButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				final Record detailRecord = new Record(vm.getValues());
//				Record recordDestPref = new Record();						
//				RecordList listaAzioniRapide = new RecordList();
//				Record recordAzioneRapidaAssegna = new Record();
//				recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
//				listaAzioniRapide.add(recordAzioneRapidaAssegna);
//				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
//				recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
//				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
//				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
//						new DSCallback() {
//
//							@Override
//							public void execute(DSResponse responseDestPref, Object rawDataDestPref,
//									DSRequest requestDestPref) {
//								if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
//									Record destinatariPreferiti = responseDestPref.getData()[0];
//									clickAssegnazione(destinatariPreferiti);
//								}
//							}
//						}, new DSRequest());
//			}
//		});
//	}
//	
//	/**
//	 *  Metodo per costruire il bottone "Assegnazione"
//	 */
//	protected void createFrecciaAssegnazioneButton() {
//
//		frecciaAssegnazioneButton = new FrecciaDetailToolStripButton();
//		frecciaAssegnazioneButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				final Record detailRecord = new Record(vm.getValues());
//				Record recordDestPref = new Record();						
//				RecordList listaAzioniRapide = new RecordList();
//				Record recordAzioneRapidaAssegna = new Record();
//				recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
//				listaAzioniRapide.add(recordAzioneRapidaAssegna);
//				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
//				recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
//				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
//				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
//						new DSCallback() {
//
//							@Override
//							public void execute(DSResponse responseDestPref, Object rawDataDestPref,
//									DSRequest requestDestPref) {
//								if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
//									Record destinatariPreferiti = responseDestPref.getData()[0];
//									clickFrecciaAssegnazione(destinatariPreferiti);
//								}
//							}
//						}, new DSRequest());
//			}
//		});
//	}

	/**
	 * Metodo che implementa l'azione del bottone "Assegnazione"
	 */
	public void clickAssegnazione(Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		RecordList listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		RecordList listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
			listaPreferiti.addList(listaUOPreferiti.toArray());
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
				listaPreferiti.addList(listaUtentiPreferiti.toArray());
			}
		}
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		clickAssegnazioneStandard(detailRecord, listaPreferiti, codSupportoOrig, listaAssPreselMitt);
	}

	/**
	 * Metodo che implementa l'azione del bottone "Freccia assegnazioni"
	 */
	public void clickFrecciaAssegnazione(Record destinatariPreferiti){
		
		final Record detailRecord = new Record(vm.getValues());
		
		final Menu creaAssegna = new Menu(); 

		RecordList listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		RecordList listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));
		
		boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
		final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare
		
		boolean flgSoloUo = false;
		final String codSupportoOrig = detailRecord != null ? detailRecord.getAttributeAsString("codSupportoOrig") : null;
		if(codSupportoOrig != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "C".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "D".equals(codSupportoOrig)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "M".equals(codSupportoOrig))) {
				flgSoloUo = true;
			}
		}
		
		if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
			listaPreferiti.addList(listaUOPreferiti.toArray());
			noMenuRapido = false;
		}
		
		if(!flgSoloUo) {
			if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
				listaPreferiti.addList(listaUtentiPreferiti.toArray());
				noMenuRapido = false;
			}
		}
		
		final RecordList listaAssPreselMitt = detailRecord != null ? detailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
		
		// Assegnazione Standard 
		MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
		assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickAssegnazioneStandard(detailRecord, listaPreferiti, codSupportoOrig, listaAssPreselMitt);
			}
		});
		
		creaAssegna.addItem(assegnaMenuStandardItem);
		
		// Assegna Rapido 
		MenuItem assegnaMenuRapidoItem = new MenuItem("Rapido");
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		if(success != null && success == true){
				
			Menu scelteRapide = new Menu();
			
			if(noMenuRapido){
				assegnaMenuRapidoItem.setEnabled(false);
			} else {
				buildMenuRapidoAssegnazione(detailRecord, "U", listaPreferiti, scelteRapide);
				assegnaMenuRapidoItem.setSubmenu(scelteRapide);
			}
			
		} else {
			assegnaMenuRapidoItem.setEnabled(false);
		}
		creaAssegna.addItem(assegnaMenuRapidoItem);
		creaAssegna.showContextMenu();
	}
	
	private void buildMenuRapidoSmistamento(final Record detailRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength(); i++){
			
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");

			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					if(detailRecord.getAttributeAsString("idUdFolder") == null ||
							"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
						detailRecord.setAttribute("flgUdFolder", flgUdFolder);
					}
					listaUdFolder.add(detailRecord);
					RecordList listaSmistamento = new RecordList();
					Record recordSmistamento = new Record();
					recordSmistamento.setAttribute("idUo", idDestinatarioPreferito);
					recordSmistamento.setAttribute("typeNodo",tipoDestinatarioPreferito);
					listaSmistamento.add(recordSmistamento);
					
					final Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaAssegnazioni", listaSmistamento);
					record.setAttribute("motivoInvio", "#SMIST");
					
					Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								operationCallback(response, record,
										"idUdFolder", "Smistamento effettuato con successo",
										"Si è verificato un errore durante lo smistamento!", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {														
										
										reload(new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												/*
												 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
												 * una volta che viene chiuso il dettaglio
												 */
												setSaved(true);
												if(layout != null) {
													layout.viewMode();
												} else {
													viewMode();
												}
											}
										});
									}
								});
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);				
		}
	}
	
	private void buildMenuRapidoSmistamentoCC(final Record detailRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength(); i++){
			
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");

			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					if(detailRecord.getAttributeAsString("idUdFolder") == null ||
							"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
						detailRecord.setAttribute("flgUdFolder", flgUdFolder);
					}
					listaUdFolder.add(detailRecord);
					RecordList listaDestInvioCC = new RecordList();
					Record recordDestSmistaCC = new Record();
					recordDestSmistaCC.setAttribute("idUo", idDestinatarioPreferito);
					recordDestSmistaCC.setAttribute("typeNodo",tipoDestinatarioPreferito);
					listaDestInvioCC.add(recordDestSmistaCC);
					
					final Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaDestInvioCC", listaDestInvioCC);
					record.setAttribute("motivoInvio", "#SMIST");
					
					Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								operationCallback(response, record,
										"idUdFolder", "Smistamento effettuato con successo",
										"Si è verificato un errore durante lo smistamento!", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {														
										
										reload(new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												/*
												 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
												 * una volta che viene chiuso il dettaglio
												 */
												setSaved(true);
												if(layout != null) {
													layout.viewMode();
												} else {
													viewMode();
												}
											}
										});
									}
								});
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);				
		}
	}

	private void buildMenuRapidoAssegnazione(final Record detailRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength(); i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");

			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					if(detailRecord.getAttributeAsString("idUdFolder") == null ||
							"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
						detailRecord.setAttribute("flgUdFolder", flgUdFolder);
					}
					listaUdFolder.add(detailRecord);
					RecordList listaAssegnazioni = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
					listaAssegnazioni.add(recordAssegnazioni);
					
					final Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaAssegnazioni", listaAssegnazioni);
					
					Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								operationCallback(response, record,
										"idUdFolder", "Assegnazione effettuata con successo",
										"Si è verificato un errore durante l'assegnazione!", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {														
										
										reload(new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												if(isAttivaStampaEtichettaPostAss(null)){
													stampaEtichettaPostAssegnazione();
												}
											}
										});
									}
								});
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);				
		}
	}
	
	/**
	 * 	Metodo che implementa l'azione di Smista standard
	 */
	private void clickSmistamentoStandard(final Record detailRecord, final RecordList listaPreferiti,
			final String codSupportoOrig, final RecordList listaAssPreselMitt) {
		
		final SmistamentoPopup smistamentoPopup = new SmistamentoPopup("U", detailRecord) {
			
			@Override
			public RecordList getListaPreferiti() {
				return listaPreferiti;
			}		
			

			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				clickOkSmistamentoStandard(detailRecord, record, callback);
			}
			
		};
		smistamentoPopup.show();
	}
	
	/**
	 * 	Metodo che implementa l'azione di SmistaCC standard
	 */
	private void clickSmistamentoCCStandard(final Record detailRecord, final RecordList listaPreferiti,
			final String codSupportoOrig, final RecordList listaAssPreselMitt) {
		
		String title = "Compila dati smistamento " + getEstremiProtocolloFromRecord(detailRecord);
		final CondivisionePopup condivisionePopup = new CondivisionePopup("U", detailRecord, title) {
			
			@Override
			public boolean isSmistamentoCC() {
				return true;
			}
			
			@Override
			public RecordList getListaPreferiti() {
				return listaPreferiti;
			}	
			
			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				clickOkSmistamentoCCStandard(detailRecord, record, callback);
			}
			
		};
		condivisionePopup.show();
	}
	
	/**
	 * 	Metodo che implementa l'azione di Assegnazione standard
	 */
	private void clickAssegnazioneStandard(final Record detailRecord, final RecordList listaPreferiti,
			final String codSupportoOrig, final RecordList listaAssPreselMitt) {
		String title = "Compila dati assegnazione del documento " + detailRecord.getAttribute("segnatura");

		final AssegnazionePopup assegnazionePopup = new AssegnazionePopup("U", detailRecord, title) {

			@Override
			public String getFlgTipoProvDoc() {
				return getFlgTipoProv();																		
			}
			
			@Override
			public String getCodSupportoOrig() {
				return codSupportoOrig;
			}

			@Override
			public RecordList getListaPreferiti() {
				return listaPreferiti;
			}

			@Override
			public RecordList getListaAssegnatariMitt() {
				return listaAssPreselMitt;
			}

			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				clickOkAssegnazioneStandard(detailRecord, record, callback);
			}

		};
		assegnazionePopup.show();
	}
	
	/**
	 * 	Metodo che implementa l'OK della popup di Smista 
	 */
	private void clickOkSmistamentoStandard(final Record detailRecord, Record record, final DSCallback callback) {
		
		final RecordList listaUdFolder = new RecordList();
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", "U");
		}
		listaUdFolder.add(detailRecord);
		
		record.setAttribute("flgUdFolder", "U");
		record.setAttribute("listaRecord", listaUdFolder);
		record.setAttribute("motivoInvio", "#SMIST");
		
		Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					operationCallback(response, detailRecord, 
							"idUdFolder", 
							"Smistamento effettuato con successo",
							"Si è verificato un errore durante lo smistamento!", new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {														
									
									reload(new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											/*
											 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
											 * una volta che viene chiuso il dettaglio
											 */
											setSaved(true);
											if(layout != null) {
												layout.viewMode();
											} else {
												viewMode();
											}
											if(callback != null) {
												callback.execute(response, null, new DSRequest());
											}
										}
									});
								}
							});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	/**
	 * 	Metodo che implementa l'OK della popup di SmistaCC
	 */
	private void clickOkSmistamentoCCStandard(final Record detailRecord, Record record, final DSCallback callback) {
		
		final RecordList listaUdFolder = new RecordList();
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", "U");
		}
		listaUdFolder.add(detailRecord);
		
		record.setAttribute("flgUdFolder", "U");
		record.setAttribute("listaRecord", listaUdFolder);
		record.setAttribute("motivoInvio", "#SMIST");
		
		Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					operationCallback(response, detailRecord, 
							"idUdFolder", 
							"Smistamento effettuato con successo",
							"Si è verificato un errore durante lo smistamento!", new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {														
									
									reload(new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											/*
											 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
											 * una volta che viene chiuso il dettaglio
											 */
											setSaved(true);
											if(layout != null) {
												layout.viewMode();
											} else {
												viewMode();
											}
											if(callback != null) {
												callback.execute(response, null, new DSRequest());
											}
										}
									});
								}
							});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	/**
	 * 	Metodo che implementa l'OK della popup di Assegnazione 
	 */
	private void clickOkAssegnazioneStandard(final Record detailRecord, Record record, final DSCallback callback) {
		final RecordList listaUdFolder = new RecordList();
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", "U");
		}
		listaUdFolder.add(detailRecord);
		
		record.setAttribute("flgUdFolder", "U");
		record.setAttribute("listaRecord", listaUdFolder);
		
		Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					operationCallback(response, detailRecord, 
							"idUdFolder", 
							"Assegnazione effettuata con successo",
							"Si è verificato un errore durante l'assegnazione!", new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {														
									if(callback != null){
										callback.execute(response, rawData, request);
									}
									reload(new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											
											if(isAttivaStampaEtichettaPostAss(null)){
												stampaEtichettaPostAssegnazione();
											}
										}
									});
								}
							});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	/**
	 *  Metodo per costruire il bottone "Rispondi"
	 */
	protected void createRispondiButton() {

		rispondiButton = new DetailToolStripButton("Rispondi", "archivio/rispondi.png");
		rispondiButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageOnClickRispondi();
			}
		});
	}
	
	public void manageOnClickRispondi() {
		
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		
		final Record recordToShow = new Record();
		recordToShow.setAttribute("idUd", idUd);
		if (isProtocollazioneDetailEntrata()) {
			recordToShow.setAttribute("tipoProt", "E");
			new DettaglioRispostaProtWindow(recordToShow);
		} else if (isProtocollazioneDetailInterna()) {

			//controllo se il protocollo interno è abilitato alla risposta
			if(detailRecord.getAttributeAsBoolean("abilRispondiUscita")) {
				SC.ask("Sono presenti destinatari esterni nella risposta?", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							recordToShow.setAttribute("tipoProt", "E");
						}
						else {
							recordToShow.setAttribute("tipoProt", "I");							
						}
						new DettaglioRispostaProtWindow(recordToShow);
					}
				});
			}else {
				recordToShow.setAttribute("tipoProt", "I");
				new DettaglioRispostaProtWindow(recordToShow);
			}
		}
	}
	
//	/**
//	 *  Metodo per costruire il bottone "Invio per conoscenza"
//	 */
//	protected void createCondivisioneButton() {
//
//		condivisioneButton = new DetailToolStripButton("Invia per conoscenza","archivio/condividi.png");
//		condivisioneButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				final Record detailRecord = new Record(vm.getValues());
//				Record recordDestPref = new Record();						
//				RecordList listaAzioniRapide = new RecordList();
//				Record recordAzioneRapidaInvioCC = new Record();
//				recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
//				listaAzioniRapide.add(recordAzioneRapidaInvioCC);
//				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
//				recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
//				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
//				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
//						new DSCallback() {
//
//							@Override
//							public void execute(DSResponse responseDestPref, Object rawDataDestPref,
//									DSRequest requestDestPref) {
//								if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
//									Record destinatariPreferiti = responseDestPref.getData()[0];
//									clickCondivisione(destinatariPreferiti);
//								}
//							}
//						}, new DSRequest());
//			}
//		});
//	}
//	
//	/**
//	 *  Metodo per costruire il bottone "Freccia invio per conoscenza"
//	 */
//	protected void createFrecciaCondivisioneButton() {
//
//		frecciaCondivisioneButton = new FrecciaDetailToolStripButton();
//		frecciaCondivisioneButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				final Record detailRecord = new Record(vm.getValues());
//				Record recordDestPref = new Record();						
//				RecordList listaAzioniRapide = new RecordList();
//				Record recordAzioneRapidaInvioCC = new Record();
//				recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
//				listaAzioniRapide.add(recordAzioneRapidaInvioCC);
//				recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
//				recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
//				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
//				lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
//						new DSCallback() {
//
//							@Override
//							public void execute(DSResponse responseDestPref, Object rawDataDestPref,
//									DSRequest requestDestPref) {
//								if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
//									Record destinatariPreferiti = responseDestPref.getData()[0];
//									clickFrecciaCondivisione(destinatariPreferiti);
//								}
//							}
//						}, new DSRequest());
//			}
//		});
//	}
	
	
	/**
	 *  Metodo per costruire il bottone "Osservazioni e notifiche"
	 */
	protected void createOsservazioniNotificheButton() {

		osservazioniNotificheButton = new DetailToolStripButton("Notifiche","osservazioni_notifiche.png");
		osservazioniNotificheButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickOsservazioniNotifiche();
			}
		}); 
	}
	
	/**
	 *  Metodo per costruire il bottone "Firma"
	 */
	protected void createFirmaButton() {
		
		apposizioneFirmaButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneFirma_button_title(),"file/mini_sign.png");		
		apposizioneFirmaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				manageFirmaComplessiva(true, new ServiceCallback<Record>() {
						
					@Override
					public void execute(Record object) {
						
						/*
						 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
						 * una volta che viene chiuso il dettaglio
						 */
						setSaved(true);
					}
				});
			}
		});
		
		rifiutoApposizioneFirmaButton = new DetailToolStripButton(I18NUtil.getMessages().rifiutoApposizioneFirma_button_title(),"file/rifiuto_apposizione.png");
		rifiutoApposizioneFirmaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				manageFirmaComplessiva(false, new ServiceCallback<Record>() {
						
					@Override
					public void execute(Record object) {
						
						/*
						 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
						 * una volta che viene chiuso il dettaglio
						 */
						setSaved(true);
					}
				});
			}
		});
	}

	protected void createApposizioneFirmaProtocollazioneButton() {
		
		apposizioneFirmaProtocollazioneButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneFirmaProtocollazione_button_title(),"buttons/firmaEProtocolla.png");		
		apposizioneFirmaProtocollazioneButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				manageProtocollaTimbraEFirma(new ServiceCallback<Record>() {
						
					@Override
					public void execute(Record object) {
						
						/*
						 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
						 * una volta che viene chiuso il dettaglio
						 */
						setSaved(true);
					}
				});
			}
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "Apponi visto"
	 */
	protected void createVistoButton() {

		apposizioneVistoButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneVisto_button_title(),"ok.png");
		apposizioneVistoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				manageVistoDocumento(true, new ServiceCallback<Record>() {
						
					@Override
					public void execute(Record object) {
						
						/*
						 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
						 * una volta che viene chiuso il dettaglio
						 */
						setSaved(true);
					}
				});
			}
		});
		
		
		rifiutoApposizioneVistoButton = new DetailToolStripButton(I18NUtil.getMessages().rifiutoApposizioneVisto_button_title(),"ko.png");
		rifiutoApposizioneVistoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				manageVistoDocumento(false, new ServiceCallback<Record>() {
						
					@Override
					public void execute(Record object) {
						
						/*
						 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
						 * una volta che viene chiuso il dettaglio
						 */
						setSaved(true);
					}
				});
			}
		});
	}
	
	/**
	 *  Metodo per costruire il bottone "Pubbl. Trasp. Amm."
	 */
	protected void createPubblicazioneTraspAmmButton() {

		pubblicazioneTraspAmmButton = new DetailToolStripButton("Pubbl. Trasp. Amm.", "buttons/richiesta_pubblicazione.png");
		pubblicazioneTraspAmmButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final Record record = new Record(vm.getValues());
				new PubblicazioneTraspAmmPopup(record.getAttribute("idUd"));
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Osservazioni e notifiche"
	 */
	public void clickOsservazioniNotifiche() {
		
		final Record record = new Record(vm.getValues());
		//Documento
		String title = "Documento "+ getEstremiUdFromDetail(record) + " - Osservazioni e notifiche";
		new OsservazioniNotificheWindow(record.getAttribute("idUd"), "U", title);
	}
	
	/**
	 * Metodo che recupera dal record i dati relativi al protocollo
	 * 
	 */
	public String getEstremiProtocolloFromRecord(Record record) {

		String estremiProt = "PROT_";
		String tipoProv = record.getAttribute("flgTipoProv") != null ? record.getAttribute("flgTipoProv") : "";
		estremiProt += DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo")) + "_"
				+ record.getAttribute("nroProtocollo") + getSuffixSubProtocollo(record.getAttribute("subProtocollo")) + "_" + tipoProv;
		return estremiProt;
	}
	
	protected String getSuffixSubProtocollo(String subProtocollo) {
		return subProtocollo != null && !"".equals(subProtocollo) ? "." + subProtocollo : "";
		
	}

	/**
	 * Metodo per lo scarico di un file
	 * 
	 */
	public void scaricaFile(String display, String uri, String remoteUri) {

		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	// Metodo che indica se è attiva la modalità con i dati principali del documento e di assegnazione/classificazione in un unico tab
	public boolean showSingleTabHeader() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_SINGLE_TAB_HEADER_IN_PROT") || AurigaLayout.isAttivoClienteRER();
	}

	/**
	 * Metodo per costruire il TabSet
	 * 
	 */
	protected void createTabSet() throws IllegalStateException {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tabSet.setTabIndex(-1);
			tabSet.setCanFocus(false);		
		} else {
			tabSet.setCanFocus(false);
			tabSet.setTabIndex(-1);
		}
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setPaneMargin(0);

		if(showSingleTabHeader()) {
			createTabDatiPrincipali();	
			tabSet.addTab(tabDatiPrincipali);			
		} else {		
			createTabDatiDocumenti();
			createTabAssegnazioneEClassificazione();
			tabSet.addTab(tabDatiDocumento);
			if (showTabAssegnazioneEClassificazione()) {
				tabSet.addTab(tabAssegnazioneEClassificazione);
			}			
		}
		
		if (showTabEsibentiEInteressati()) {
			createTabEsibentiEInteressati();
			tabSet.addTab(tabEsibentiEInteressati);
		}
		
		if (showTabDelegatoEFirmatari()) {
			createTabDelegatoEFirmatari();
			tabSet.addTab(tabDelegatoEFirmatari);
		}
		
		if (showTabAltreVie()) {
			createTabAltreVie();
			tabSet.addTab(tabAltreVie);
		}
		
		if(showTabPubblicazione()) {
			createTabPubblicazione();
			tabSet.addTab(tabPubblicazione);
		}
	}

	/**
	 * Metodo per costruire il pane associato ad un tab generico
	 * 
	 */
	protected VLayout createTabPane(VLayout layout) {

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		VLayout layoutTab = new VLayout();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			spacerLayout.setTabIndex(-1);
			spacerLayout.setCanFocus(false);
			layoutTab.setTabIndex(-1);
			layoutTab.setCanFocus(false);				
		}
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
	
	/**
	 * Metodo per costruire il tab "Dati principali" se è attivo il tab unico (SHOW_SINGLE_TAB_HEADER_IN_PROT = true)
	 * 
	 */
	
	protected void createTabDatiPrincipali() {
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tabDatiPrincipali = new Tab("<h5><b>Dati principali</b></h5>");
			tabDatiPrincipali.setAttribute("tabID", "HEADER");
			tabDatiPrincipali.setPrompt("Dati principali");
			VLayout layoutDatiPrincipali = AurigaLayout.isAttivoClienteRER() ? getLayoutDatiPrincipaliRER() : getLayoutDatiPrincipali();
			layoutDatiPrincipali.setTabIndex(-1);
			layoutDatiPrincipali.setCanFocus(false);
			VLayout createTabPane = createTabPane(layoutDatiPrincipali);
			createTabPane.setTabIndex(-1);
			createTabPane.setCanFocus(false);
			tabDatiPrincipali.setPane(createTabPane);		
		} else {
			tabDatiPrincipali = new Tab("<b>Dati principali</b>");
			tabDatiPrincipali.setAttribute("tabID", "HEADER");
			tabDatiPrincipali.setPrompt("Dati principali");
			tabDatiPrincipali.setPane(createTabPane(AurigaLayout.isAttivoClienteRER() ? getLayoutDatiPrincipaliRER() : getLayoutDatiPrincipali()));
		}
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati principali" se è attivo il tab unico (SHOW_SINGLE_TAB_HEADER_IN_PROT = true)
	 * 
	 */
	
	public VLayout getLayoutDatiPrincipali() {
		
		VLayout layoutDatiPrincipali = new VLayout(5);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			layoutDatiPrincipali.setTabIndex(-1);
			layoutDatiPrincipali.setCanFocus(false);		
		}		
		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiPrincipali.addMember(detailSectionRegistrazione);

		createDetailSectionNuovaRegistrazione();
		detailSectionNuovaRegistrazione.setVisible(false);
		layoutDatiPrincipali.addMember(detailSectionNuovaRegistrazione);
		
		if(showDetailSectionNuovaRegistrazioneProtGenerale()) {
			createDetailSectionNuovaRegistrazioneProtGenerale();			
			layoutDatiPrincipali.addMember(detailSectionNuovaRegistrazioneProtGenerale);
		}

		if(showDetailSectionTipoDocumento()) {
			createDetailSectionTipoDocumento();
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			layoutDatiPrincipali.addMember(detailSectionTipoDocumento);
		}
		
		if(showDetailSectionTipologieParticolariA2A()) {
			createDetailSectionTipologieParticolariA2A();
			layoutDatiPrincipali.addMember(detailSectionTipologieParticolariA2A);
		}
		
		if (showDetailSectionDataArrivoSpedizione()) {
			createDetailSectionDataArrivoSpedizione();
			layoutDatiPrincipali.addMember(detailSectionDataArrivoSpedizione);
		}
		
		if (showDetailSectionDatiRicezione() && showDatiRicezioneBeforeMittenti()) {
			createDetailSectionDatiRicezione();
			layoutDatiPrincipali.addMember(detailSectionDatiRicezione);
		}

		createDetailSectionMittenti();
		layoutDatiPrincipali.addMember(detailSectionMittenti);
		
//		if (showDetailSectionControinteressati()) {
			createDetailSectionControinteressati();
			detailSectionControinteressati.setVisible(false);
			layoutDatiPrincipali.addMember(detailSectionControinteressati);
//		}

		if (showDetailSectionDestinatari()) {
			createDetailSectionDestinatari();
			layoutDatiPrincipali.addMember(detailSectionDestinatari);
		}

		createDetailSectionContenuti();
		layoutDatiPrincipali.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiPrincipali.addMember(detailSectionAllegati);

		if (showDetailSectionDatiRicezione() && !showDatiRicezioneBeforeMittenti()) {
			createDetailSectionDatiRicezione();
			layoutDatiPrincipali.addMember(detailSectionDatiRicezione);
		}

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiPrincipali.addMember(detailSectionDocCollegato);
		}

		if (showDetailSectionPerizia()) {
			createDetailSectionPerizia();
			layoutDatiPrincipali.addMember(detailSectionPerizia);
		}
		
		createDetailSectionAssegnazione();
		layoutDatiPrincipali.addMember(detailSectionAssegnazione);

		createDetailSectionCondivisione();
		layoutDatiPrincipali.addMember(detailSectionCondivisione);

		createDetailSectionClassificazioneFascicolazione();
		layoutDatiPrincipali.addMember(detailSectionClassificazioneFascicolazione);
		
		createDetailSectionFolderCustom();
		layoutDatiPrincipali.addMember(detailSectionFolderCustom);
		
		if (showDetailSectionRegEmergenza()) {
			createDetailSectionRegEmergenza();
			layoutDatiPrincipali.addMember(detailSectionRegEmergenza);
		}

		if (showDetailSectionCollocazioneFisica()) {
			createDetailSectionCollocazioneFisica();
			layoutDatiPrincipali.addMember(detailSectionCollocazioneFisica);
		}
		
		createDetailSectionAltriDati();
		layoutDatiPrincipali.addMember(detailSectionAltriDati);
		
		return layoutDatiPrincipali;
	}

	/**
	 * Metodo per costruire il tab "Dati documento"
	 * 
	 */
	protected void createTabDatiDocumenti() {

		tabDatiDocumento = new Tab(
				"<b>" + I18NUtil.getMessages().protocollazione_detail_tabDatiDocumento_title() + "</b>");
		tabDatiDocumento.setAttribute("tabID", "HEADER");
		tabDatiDocumento.setPrompt(I18NUtil.getMessages().protocollazione_detail_tabDatiDocumento_prompt());
		tabDatiDocumento.setPane(createTabPane(getLayoutDatiDocumento()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati documento"
	 * 
	 */
	public VLayout getLayoutDatiDocumento() {

		VLayout layoutDatiDocumento = new VLayout(5);

		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionRegistrazione);

		createDetailSectionNuovaRegistrazione();
		detailSectionNuovaRegistrazione.setVisible(false);
		layoutDatiDocumento.addMember(detailSectionNuovaRegistrazione);
		
		if(showDetailSectionNuovaRegistrazioneProtGenerale()) {
			createDetailSectionNuovaRegistrazioneProtGenerale();			
			layoutDatiDocumento.addMember(detailSectionNuovaRegistrazioneProtGenerale);
		}

		if(showDetailSectionTipoDocumento()) {
			createDetailSectionTipoDocumento();
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			layoutDatiDocumento.addMember(detailSectionTipoDocumento);
		}
		
		if(showDetailSectionTipologieParticolariA2A()) {
			createDetailSectionTipologieParticolariA2A();
			layoutDatiDocumento.addMember(detailSectionTipologieParticolariA2A);
		}
		
		if (showDetailSectionDataArrivoSpedizione()) {
			createDetailSectionDataArrivoSpedizione();
			layoutDatiDocumento.addMember(detailSectionDataArrivoSpedizione);
		}

		if (showDetailSectionDatiRicezione() && showDatiRicezioneBeforeMittenti()) {
			createDetailSectionDatiRicezione();
			layoutDatiDocumento.addMember(detailSectionDatiRicezione);
		}
		
		createDetailSectionMittenti();
		layoutDatiDocumento.addMember(detailSectionMittenti);
		
//		if (showDetailSectionControinteressati()) {
			createDetailSectionControinteressati();
			detailSectionControinteressati.setVisible(false);
			layoutDatiDocumento.addMember(detailSectionControinteressati);
//		}

		if (showDetailSectionDestinatari()) {
			createDetailSectionDestinatari();
			layoutDatiDocumento.addMember(detailSectionDestinatari);
		}

		createDetailSectionContenuti();
		layoutDatiDocumento.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiDocumento.addMember(detailSectionAllegati);

		if (showDetailSectionDatiRicezione() && !showDatiRicezioneBeforeMittenti()) {
			createDetailSectionDatiRicezione();
			layoutDatiDocumento.addMember(detailSectionDatiRicezione);
		}

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiDocumento.addMember(detailSectionDocCollegato);
		}

		if (showDetailSectionRegEmergenza()) {
			createDetailSectionRegEmergenza();
			layoutDatiDocumento.addMember(detailSectionRegEmergenza);
		}

		if (showDetailSectionCollocazioneFisica()) {
			createDetailSectionCollocazioneFisica();
			layoutDatiDocumento.addMember(detailSectionCollocazioneFisica);
		}

		createDetailSectionAltriDati();
		layoutDatiDocumento.addMember(detailSectionAltriDati);

		
		if (showDetailSectionPerizia()) {
			createDetailSectionPerizia();
			layoutDatiDocumento.addMember(detailSectionPerizia);
		}
		
		return layoutDatiDocumento;
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Registrazione"
	 * 
	 */
	public boolean showDetailSectionRegistrazione() {
		return true;
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sigla di registrazione
	 * 
	 */
	public boolean showSiglaProtocolloItem() {
		return false;
	}

	/**
	 * Metodo che restituisce la label del numero di registrazione
	 * 
	 */
	public String getTitleNroProtocolloItem(Record record) {
		
		if (record != null && record.getAttribute("tipoProtocollo") != null && !"".equals(record.getAttribute("tipoProtocollo"))) {
			if ("PG".equals(record.getAttribute("tipoProtocollo"))) {
				return I18NUtil.getMessages().protocollazione_detail_nroProtocolloItem_title();
			}
			else if ("NI".equals(record.getAttribute("tipoProtocollo"))) {				
				if (isDocumentoRegistrato()) {
					return "N° documento";
				} else if (record.getAttribute("tipoDocumento") != null && !"".equals(record.getAttribute("tipoDocumento"))) {
					return "N° documento";
				} else {
					return I18NUtil.getMessages().protocollazione_detail_nroBozzaItem_title();
				}
			}
			else if ("R".equals(record.getAttribute("tipoProtocollo"))) {
				// Se è ho una registrazione di repertorio ma non sono in RepertorioDetail devo cambiare anche l'icona (per esempio in TaskDettUdGenDetail)
				if(!isRepertorioDetailEntrata() && isProtocollazioneDetailEntrata()) {
					if(iconaTipoProtocolloItem != null) {
						iconaTipoProtocolloItem.setSrc("protocollazione/repertorio_entrata.png");
						iconaTipoProtocolloItem.setPrompt("Repertorio in entrata");
					}
				} else if(!isRepertorioDetailInterno() && isProtocollazioneDetailInterna()) {
					if(iconaTipoProtocolloItem != null) {
						iconaTipoProtocolloItem.setSrc("protocollazione/repertorio_interno.png");
						iconaTipoProtocolloItem.setPrompt("Repertorio interno");
					}
				} else if(!isRepertorioDetailUscita() && isProtocollazioneDetailUscita()) {
					if(iconaTipoProtocolloItem != null) {
						iconaTipoProtocolloItem.setSrc("protocollazione/repertorio_uscita.png");
						iconaTipoProtocolloItem.setPrompt("Repertorio in uscita");
					}
				}
				return "Repertorio " + record.getAttribute("siglaProtocollo") + " N°";
			}
			else if (record.getAttribute("siglaProtocollo") != null && !"".equals(record.getAttribute("siglaProtocollo"))) {	
				if("P.I.".equals(record.getAttribute("siglaProtocollo"))) {
					return "Prot. int. N°";
				} else {				
					return record.getAttribute("siglaProtocollo") + " N°";
				}
			}			
		}
		return "Registrazione N°";
	}

	/**
	 * Metodo che restituisce la label della data di registrazione
	 * 
	 */
	public String getTitleDataProtocolloItem() {
		return I18NUtil.getMessages().protocollazione_detail_dataProtocolloItem_title();
	}

	/**
	 * Metodo che restituisce la label dell'utente protocollante
	 * 
	 */
	public String getTitleDesUserProtocolloItem() {
		return I18NUtil.getMessages().protocollazione_detail_desUserProtocolloItem_title();
	}

	/**
	 * Metodo che indica se mostrare o meno l'ufficio di protocollo
	 * 
	 */
	public boolean showDesUOProtocolloItem() {
		return true; // NO PER GLI ATTI
	}
	
	/**
	 * Metodo che indica se mostrare o meno la Società
	 */
	public boolean showSocietaItem() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_MULTISOCIETA");
	}

	/**
	 * Metodo che restituisce la label dell'ufficio di protocollo
	 * 
	 */
	public String getTitleDesUOProtocolloItem() {
		return I18NUtil.getMessages().protocollazione_detail_desUOProtocolloItem_title();
	}

	/**
	 * Metodo che indica se mostrare o meno le icone di notifica di eccezione,
	 * aggiornamento e annullamento registrazione
	 * 
	 */
	public boolean showIconeNotificheInterop() {
		return false; // PROTOCOLLAZIONE (ANCHE PGWEB), ISTANZE, REG. FATTURE E
						// REPERTORIO
	}

	/**
	 * Metodo che indica se mostrare o meno l'icona di registrazione annullata
	 * 
	 */
	public boolean showIconaAnnullata() {
		return false; // PROTOCOLLAZIONE (ANCHE PGWEB), ISTANZE, REG. FATTURE E
						// REPERTORIO
	}

	/**
	 * Metodo che indica se mostrare o meno l'icona di richiesta annullamento
	 * della registrazione
	 * 
	 */
	public boolean showIconaRichAnnullamento() {
		return false; // PROTOCOLLAZIONE (ANCHE PGWEB), ISTANZE, REG. FATTURE E
						// REPERTORIO
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone "Iter processo collegato"
	 * 
	 */
	public boolean showIterProcessoCollegatoButton() {
		return !isTaskDetail() && idProcessHiddenItem.getValue() != null && !"".equals(idProcessHiddenItem.getValue());
	}
	
	/**
	 * Metodo che indica se mostrare o meno i bottoni relativi ai documenti collegati
	 * 
	 */
	public boolean showDocumentiCollegatiButton() {
		return true;
	}
	
	public boolean showDocumentiCollegatiButton(Record record) {
		return (record != null && ( record.getAttributeAsBoolean("abilModificaDati") || record.getAttributeAsBoolean("abilGestioneCollegamentiUD")));
	}
	
	/**
	 * Metodo che indica se mostrare o meno il bottone "Altri riferimenti"
	 * 
	 */
	public boolean showAltriRiferimentiButton() {
		return true;
	}

	/**
	 * Metodo che crea la sezione "Registrazione"
	 * 
	 */
	protected void createDetailSectionRegistrazione() {

		createProtocolloGeneraleForm();
		createRegistrazioneSecondariaForm();
		createAltreNumerazioniUDForm();
		createAltraNumerazioneForm();
		createAltraNumerazioneProvvisoriaForm();
		createAttoLiquidazioneForm();
		createStatoForm();
		
		detailSectionRegistrazione = new ProtocollazioneHeaderDetailSection(
				I18NUtil.getMessages().protocollazione_detail_registrazioneForm_title(), true, true, false,
				protocolloGeneraleForm, registrazioneSecondariaForm, altreNumerazioniUDForm ,altraNumerazioneForm, 
				altraNumerazioneProvvisoriaForm, attoLiquidazioneForm, statoForm) {

			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return getShowFirstCanvasWhenEmptyAfterOpen();
			}
		};
	}

	/**
	 * Metodo che crea il form di protocollo generale della sezione
	 * "Registrazione"
	 * 
	 */
	protected void createProtocolloGeneraleForm() {

		protocolloGeneraleForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			protocolloGeneraleForm.setCanFocus(true);		
		}
		protocolloGeneraleForm.setValuesManager(vm);
		protocolloGeneraleForm.setWidth100();
		protocolloGeneraleForm.setPadding(5);
		protocolloGeneraleForm.setWrapItemTitles(false);
		protocolloGeneraleForm.setNumCols(18);
		protocolloGeneraleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		protocolloGeneraleForm.setTabSet(tabSet);
		protocolloGeneraleForm.setTabID("HEADER");
		protocolloGeneraleForm.setVisibility(Visibility.HIDDEN);

		idOperRegistrazioneItem = new HiddenItem("idOperRegistrazione");
		idUdHiddenItem = new HiddenItem("idUd");
		idUdNuovoComeCopiaHiddenItem = new HiddenItem("idUdNuovoComeCopia");		
		prefKeyModelloHiddenItem = new HiddenItem("prefKeyModello");
		prefNameModelloHiddenItem = new HiddenItem("prefNameModello");
		useridModelloHiddenItem = new HiddenItem("useridModello");
		idUoModelloHiddenItem = new HiddenItem("idUoModello");
		codStatoDettHiddenItem = new HiddenItem("codStatoDett");
		codStatoHiddenItem = new HiddenItem("codStato");
		idProcessHiddenItem = new HiddenItem("idProcess");
		estremiProcessHiddenItem = new HiddenItem("estremiProcess");
		ruoloSmistamentoAttoHiddenItem = new HiddenItem("ruoloSmistamentoAtto");
		
		if (!showDetailSectionTipoDocumento()) {
			tipoDocumentoSalvatoHiddenItem= new HiddenItem("tipoDocumento");
			tipoDocumentoHiddenItem = new HiddenItem("tipoDocumento");
			tipoDocumentoHiddenItem.setDefaultValue(tipoDocumento);
			nomeTipoDocumentoHiddenItem = new HiddenItem("nomeTipoDocumento");		
		}

		flgTipoProvItem = new HiddenItem("flgTipoProv");
		flgTipoProvItem.setValue(getFlgTipoProv());
		
		flgVersoBozzaItem = new HiddenItem("flgVersoBozza");
		
		timestampGetDataItem = new HiddenItem("timestampGetData");

		if (isProtocollazioneDetailBozze()) {
//			if (isIstanzeDetail()) {
//				iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/istanze.png",
//						I18NUtil.getMessages().archivio_list_tipoProtocolloIstanzaAlt_value());
//			} else {
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/protocollazione_entrata.png",
							I18NUtil.getMessages().archivio_list_tipoProtocolloInEntrataAlt_value());
				} else if(getFlgTipoProv() != null && "U".equals(getFlgTipoProv())) {
					iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/protocollazione_uscita.png",
							I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());
				} else if(getFlgTipoProv() != null && "I".equals(getFlgTipoProv())) {
					iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/protocollazione_interna.png",
							I18NUtil.getMessages().archivio_list_tipoProtocolloInternoAlt_value());
				} else if (isDocumentoRegistrato()) {
					iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/doc.png",
							I18NUtil.getMessages().archivio_list_tipoDocumentoRegistratoAlt_value());
				} else {
					iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/numerazione_provvisoria.png",
							I18NUtil.getMessages().archivio_list_tipoProtocolloNULLAlt_value());
				}
//			}
		} else if (isProtocollazioneDetailAtti()) {
			iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/iter_atti.png", "Atto");
		} else if (isProtocollazioneDetailEntrata()) {
			iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/protocollazione_entrata.png",
					I18NUtil.getMessages().archivio_list_tipoProtocolloInEntrataAlt_value());
		} else if (isProtocollazioneDetailUscita()) {
			iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/protocollazione_uscita.png",
					I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());
		} else if (isProtocollazioneDetailInterna()) {
			iconaTipoProtocolloItem = new ImgItem("iconaTipoProtocollo", "menu/protocollazione_interna.png",
					I18NUtil.getMessages().archivio_list_tipoProtocolloInternoAlt_value());
		}
		iconaTipoProtocolloItem.setColSpan(1);
		iconaTipoProtocolloItem.setIconWidth(16);
		iconaTipoProtocolloItem.setIconHeight(16);
		iconaTipoProtocolloItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoProtocolloItem.setAlign(Alignment.LEFT);
		iconaTipoProtocolloItem.setWidth(16);

		idEmailArrivoHiddenItem = new HiddenItem("idEmailArrivo");
		casellaIsPECHiddenItem = new HiddenItem("casellaIsPEC");
		emailInviataFlgPECHiddenItem = new HiddenItem("emailInviataFlgPEC");
		emailInviataFlgPEOHiddenItem = new HiddenItem("emailInviataFlgPEO");
		emailArrivoInteroperabileHiddenItem = new HiddenItem("emailArrivoInteroperabile");
		emailProvIndirizzoHiddenItem = new HiddenItem("indirizzo");
		emailProvFlgPECHiddenItem = new HiddenItem("flgPEC");
		emailProvFlgCasellaIstituzHiddenItem = new HiddenItem("flgCasellaIstituzionale");
		emailProvFlgDichIPAHiddenItem = new HiddenItem("flgDichIPA");
		emailProvGestorePECHiddenItem = new HiddenItem("gestorePEC");
		inviataMailInteroperabileHiddenItem = new HiddenItem("inviataMailInteroperabile");

		iconaEmailRicevutaItem = new ImgButtonItem("iconaEmailRicevuta", "mail/ricevuta.png",
				I18NUtil.getMessages().archivio_detail_emailRicevutaAlt_value());
		iconaEmailRicevutaItem.setAlwaysEnabled(true);
		iconaEmailRicevutaItem.setColSpan(1);
		iconaEmailRicevutaItem.setIconWidth(16);
		iconaEmailRicevutaItem.setIconHeight(16);
		iconaEmailRicevutaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaEmailRicevutaItem.setAlign(Alignment.LEFT);
		iconaEmailRicevutaItem.setWidth(16);
		iconaEmailRicevutaItem.setRedrawOnChange(true);
		iconaEmailRicevutaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (idEmailArrivoHiddenItem.getValue() != null && !idEmailArrivoHiddenItem.getValue().equals(""));
			}
		});
		iconaEmailRicevutaItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				record.setAttribute("idEmail", (String) idEmailArrivoHiddenItem.getValue());
				record.setAttribute("flgIo", "I");
				new PostaInArrivoRegistrazioneWindow(record);
			}
		});

		iconaEmailInviataItem = new ImgButtonItem("iconaEmailInviata", "mail/inviata.png",
				I18NUtil.getMessages().archivio_detail_emailInviataAlt_value());
		iconaEmailInviataItem.setAlwaysEnabled(true);
		iconaEmailInviataItem.setColSpan(1);
		iconaEmailInviataItem.setIconWidth(16);
		iconaEmailInviataItem.setIconHeight(16);
		iconaEmailInviataItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaEmailInviataItem.setAlign(Alignment.LEFT);
		iconaEmailInviataItem.setWidth(16);
		iconaEmailInviataItem.setRedrawOnChange(true);
		iconaEmailInviataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((emailInviataFlgPECHiddenItem.getValue() != null
						&& emailInviataFlgPECHiddenItem.getValue().equals(true))
						|| (emailInviataFlgPEOHiddenItem.getValue() != null
								&& emailInviataFlgPEOHiddenItem.getValue().equals(true)));
			}
		});
		iconaEmailInviataItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue())
						: null;
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PostaInUscitaRegistrazioneDataSource",
						"idEmail", FieldType.TEXT);
				lGwtRestDataSource.addParam("idUd", idUd);
				lGwtRestDataSource.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							RecordList data = response.getDataAsRecordList();
							if (data.getLength() == 1) {
								new PostaInUscitaRegistrazioneWindow(data.get(0));
							} else if (data.getLength() > 0) {
								new PostaInUscitaRegistrazioneWindow(idUd);
							}
						}
					}
				});
			}
		});

		ricEccezioniInteropItem = new ImgButtonItem("ricEccezioniInterop", "postaElettronica/notifInteropEcc.png",
				"Pervenute eccezioni");
		ricEccezioniInteropItem.setAlwaysEnabled(true);
		ricEccezioniInteropItem.setColSpan(1);
		ricEccezioniInteropItem.setIconWidth(16);
		ricEccezioniInteropItem.setIconHeight(16);
		ricEccezioniInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		ricEccezioniInteropItem.setAlign(Alignment.LEFT);
		ricEccezioniInteropItem.setWidth(16);
		ricEccezioniInteropItem.setRedrawOnChange(true);
		ricEccezioniInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!showIconeNotificheInterop()) {
					return false;
				}
				return value != null ? (Boolean) value : false;
			}
		});

		ricAggiornamentiInteropItem = new ImgButtonItem("ricAggiornamentiInterop",
				"postaElettronica/notifInteropAgg.png", "Pervenute notifiche di aggiornamento");
		ricAggiornamentiInteropItem.setAlwaysEnabled(true);
		ricAggiornamentiInteropItem.setColSpan(1);
		ricAggiornamentiInteropItem.setIconWidth(16);
		ricAggiornamentiInteropItem.setIconHeight(16);
		ricAggiornamentiInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		ricAggiornamentiInteropItem.setAlign(Alignment.LEFT);
		ricAggiornamentiInteropItem.setWidth(16);
		ricAggiornamentiInteropItem.setRedrawOnChange(true);
		ricAggiornamentiInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!showIconeNotificheInterop()) {
					return false;
				}
				return value != null ? (Boolean) value : false;
			}
		});

		ricAnnullamentiInteropItem = new ImgButtonItem("ricAnnullamentiInterop", "postaElettronica/notifInteropAnn.png",
				"Pervenute notifiche di annullamento registrazione");
		ricAnnullamentiInteropItem.setAlwaysEnabled(true);
		ricAnnullamentiInteropItem.setColSpan(1);
		ricAnnullamentiInteropItem.setIconWidth(16);
		ricAnnullamentiInteropItem.setIconHeight(16);
		ricAnnullamentiInteropItem.setIconVAlign(VerticalAlignment.BOTTOM);
		ricAnnullamentiInteropItem.setAlign(Alignment.LEFT);
		ricAnnullamentiInteropItem.setWidth(16);
		ricAnnullamentiInteropItem.setRedrawOnChange(true);
		ricAnnullamentiInteropItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!showIconeNotificheInterop()) {
					return false;
				}
				return value != null ? (Boolean) value : false;
			}
		});

		tipoProtocolloItem = new HiddenItem("tipoProtocollo");
		
		codCategoriaProtocolloItem = new HiddenItem("codCategoriaProtocollo");

		siglaProtocolloItem = new TextItem("siglaProtocollo", getTitleNroProtocolloItem(null));
		siglaProtocolloItem.setWidth(80);
		siglaProtocolloItem.setShowTitle(false);
		siglaProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSiglaProtocolloItem();
			}
		});

		nroProtocolloItem = new NumericItem("nroProtocollo", getTitleNroProtocolloItem(null));
		nroProtocolloItem.setLength(7);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			siglaProtocolloItem.setCanFocus(true);
			nroProtocolloItem.setCanFocus(true);
			if (showSiglaProtocolloItem()) {
				nroProtocolloItem.setShowTitle(false);
			}		
		}

		subProtocolloItem = new TextItem("subProtocollo", "Sub");
		subProtocolloItem.setWidth(80);
		subProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		dataProtocolloItem = new DateTimeItem("dataProtocollo", getTitleDataProtocolloItem());
		
		idUdAttoAutAnnProtocolloItem = new HiddenItem("idUdAttoAutAnnProtocollo");
		
		dettaglioUdAttoAutAnnProtocolloButton = new ImgButtonItem("dettaglioUdAttoAutAnnProtocolloButton", "menu/atti_autorizzazione.png", "Visualizza dettaglio atto annullamento");
		dettaglioUdAttoAutAnnProtocolloButton.setColSpan(1);
		dettaglioUdAttoAutAnnProtocolloButton.setAlwaysEnabled(true);
		dettaglioUdAttoAutAnnProtocolloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {				
				if (isProtocollazioneDetailAtti()) {
					Record record = new Record();
					record.setAttribute("idUd", protocolloGeneraleForm.getValueAsString("idUdAttoAutAnnProtocollo"));
					new DettaglioRegProtAssociatoWindow(record, "Dettaglio atto annullamento");					
				} else {
					Record lRecord = new Record();
					lRecord.setAttribute("idAtto", protocolloGeneraleForm.getValueAsString("idUdAttoAutAnnProtocollo"));
					new DettaglioAttoAutorizzazioneAnnRegWindow(lRecord, "Dettaglio atto annullamento");	
				}					
			}
		});
		dettaglioUdAttoAutAnnProtocolloButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return protocolloGeneraleForm.getValueAsString("idUdAttoAutAnnProtocollo") != null && !"".equals(protocolloGeneraleForm.getValueAsString("idUdAttoAutAnnProtocollo"));
			}
		});

		datiAnnullamentoHiddenItem = new HiddenItem("datiAnnullamento");
		datiRichAnnullamentoHiddenItem = new HiddenItem("datiRichAnnullamento");

		// Icona registrazione annullata
		iconaAnnullataItem = new ImgButtonItem("annullata", "protocollazione/annullata.png", null);
		iconaAnnullataItem.setAlwaysEnabled(true);
		iconaAnnullataItem.setColSpan(1);
		iconaAnnullataItem.setIconWidth(16);
		iconaAnnullataItem.setIconHeight(16);
		iconaAnnullataItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaAnnullataItem.setAlign(Alignment.LEFT);
		iconaAnnullataItem.setWidth(16);
		iconaAnnullataItem.setRedrawOnChange(true);
		iconaAnnullataItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (!showIconaAnnullata()) {
					return false;
				}
				String datiAnnullamento = (String) protocolloGeneraleForm.getValueAsString("datiAnnullamento");
				if (datiAnnullamento != null && !"".equals(datiAnnullamento)) {
					iconaAnnullataItem.setPrompt(datiAnnullamento);
				}
				return value != null ? (Boolean) value : false;
			}
		});
		iconaAnnullataItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String datiAnnullamento = (String) protocolloGeneraleForm.getValueAsString("datiAnnullamento");
				if (datiAnnullamento != null && !"".equals(datiAnnullamento)) {
					return datiAnnullamento;
				}
				return null;
			}
		});

		// Icona richiesto annullamento registrazione
		iconaRichAnnullamentoItem = new ImgButtonItem("richAnnullamento", "protocollazione/richAnnullamento.png", null);
		iconaRichAnnullamentoItem.setAlwaysEnabled(true);
		iconaRichAnnullamentoItem.setColSpan(1);
		iconaRichAnnullamentoItem.setIconWidth(16);
		iconaRichAnnullamentoItem.setIconHeight(16);
		iconaRichAnnullamentoItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaRichAnnullamentoItem.setAlign(Alignment.LEFT);
		iconaRichAnnullamentoItem.setWidth(16);
		iconaRichAnnullamentoItem.setRedrawOnChange(true);
		iconaRichAnnullamentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if (!showIconaRichAnnullamento()) {
					return false;
				}
				String datiRichAnnullamento = (String) protocolloGeneraleForm.getValueAsString("datiRichAnnullamento");
				if (datiRichAnnullamento != null && !"".equals(datiRichAnnullamento)) {
					iconaRichAnnullamentoItem.setPrompt(datiRichAnnullamento);
				}
				return value != null ? (Boolean) value : false;
			}
		});
		iconaRichAnnullamentoItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String datiRichAnnullamento = (String) protocolloGeneraleForm.getValueAsString("datiRichAnnullamento");
				if (datiRichAnnullamento != null && !"".equals(datiRichAnnullamento)) {
					return datiRichAnnullamento;
				}
				return null;
			}
		});
		
		societaItem = new TextItem("societa", I18NUtil.getMessages().protocollazione_detail_societaItem_title());
		societaItem.setWidth(100);
		societaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSocietaItem();
			}
		});

		desUserProtocolloItem = new TextItem("desUserProtocollo", getTitleDesUserProtocolloItem());
		desUserProtocolloItem.setWidth(200);

		desUOProtocolloItem = new TextItem("desUOProtocollo", getTitleDesUOProtocolloItem());
		desUOProtocolloItem.setWidth(200);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			desUserProtocolloItem.setCanFocus(true);
			desUOProtocolloItem.setCanFocus(true);				
		}
		desUOProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDesUOProtocolloItem();
			}
		});	
				
		estremiRepertorioPerStrutturaItem = new TextItem("estremiRepertorioPerStruttura", "N° per struttura");
		estremiRepertorioPerStrutturaItem.setWidth(200);
		estremiRepertorioPerStrutturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);		 
			}
		});

		abilAssegnazioneSmistamentoItem = new HiddenItem("abilAssegnazioneSmistamento");
		abilAssegnazioneSmistamentoItem.setDefaultValue(true);
		
		abilRispondiItem = new HiddenItem("abilRispondi");
		
		visualizzaDettStdProtButton = new ImgButtonItem("visualizzaDettStdProtButton", "buttons/dati_protocollo.png", "Visualizza dati protocollo");
		visualizzaDettStdProtButton.setAlwaysEnabled(true);
		visualizzaDettStdProtButton.setEndRow(false);
		visualizzaDettStdProtButton.setColSpan(1);
		visualizzaDettStdProtButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				String estremi = "Protocollo Generale ";
				if (nroProtocolloItem.getValue() != null && !"".equals(nroProtocolloItem.getValue())) {
					estremi += nroProtocolloItem.getValue() + " ";
				}
				if (dataProtocolloItem.getValue() != null && !"".equals(dataProtocolloItem.getValue())) {
					estremi += "del " + DateUtil.format((Date) dataProtocolloItem.getValue());
				}
				String title = (estremi != null && !"".equals(estremi) ? "Dettaglio " + estremi : "");
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUd);
				DettaglioRegProtAssociatoWindow dettaglioProtWindow = new DettaglioRegProtAssociatoWindow(lRecord, "#DETT_STD_PROTOCOLLO", title);
			}
		});
		visualizzaDettStdProtButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				Record record = new Record(vm.getValues());
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;	
				String tipoNumerazionePrincipale = (tipoProtocolloItem.getValue() != null) ? (String) tipoProtocolloItem.getValue() : "";
				if (tipoNumerazionePrincipale.equalsIgnoreCase("PG") && isAltraNumerazione() && !isProtocolloGeneraleConRepertorio() && !isRepertorio()) {
					return idUd != null && !"".equals(idUd);	
				}				
				return false;
			}
		});

		iterProcessoCollegatoButton = new ImgButtonItem("iterProcessoCollegato", "buttons/gear.png",
				"Vai all’iter/processo collegato");
		iterProcessoCollegatoButton.setAlwaysEnabled(true);
		iterProcessoCollegatoButton.setColSpan(1);
		iterProcessoCollegatoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showIterProcessoCollegatoButton();
			}
		});
		iterProcessoCollegatoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				List<CustomTaskButton> customButtons = new ArrayList<CustomTaskButton>();
				if(AttiLayout.isAttivoSmistamentoAtti()) {		
					final CustomTaskButton buttonSmistaAtto = new CustomTaskButton("Smista", "pratiche/task/buttons/smista_atto.png") {

						public boolean isToShow(Record recordEvento) {
							Boolean flgAttivaSmistamento = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgAttivaSmistamento") : null;
							return flgAttivaSmistamento != null && flgAttivaSmistamento;
						}
					};
					buttonSmistaAtto.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							final Record recordAtto = new Record();
							recordAtto.setAttribute("idProcedimento", idProcessHiddenItem.getValue());
							recordAtto.setAttribute("ruoloSmistamento", ruoloSmistamentoAttoHiddenItem.getValue());
							recordAtto.setAttribute("unitaDocumentariaId", idUdHiddenItem.getValue());
							if(buttonSmistaAtto.getTaskDetail() != null && buttonSmistaAtto.getTaskDetail() instanceof TaskNuovaPropostaAtto2CompletaDetail) {
								((TaskNuovaPropostaAtto2CompletaDetail) buttonSmistaAtto.getTaskDetail()).salvaBeforeSmistaAtto(new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record object) {
										AurigaLayout.smistaAtto(layout, recordAtto, new ServiceCallback<Record>() {

											@Override
											public void execute(Record object) {
												((TaskNuovaPropostaAtto2CompletaDetail) buttonSmistaAtto.getTaskDetail()).chiudiAfterSmistaAtto();
											}											
										});								
									}
								});
							} else {
								AurigaLayout.smistaAtto(layout, recordAtto, null);
							}																				
						}
					});
					customButtons.add(buttonSmistaAtto);
				}
				AurigaLayout.apriDettaglioPratica((String) idProcessHiddenItem.getValue(),
						(String) estremiProcessHiddenItem.getValue(), customButtons, null);
			}
		});

		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate",
				"protocollazione/operazioniEffettuate.png",
				I18NUtil.getMessages().protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				String estremi = getTipoEstremiDocumento();
				new OperazioniEffettuateWindow(idUd, "U", I18NUtil.getMessages().operazionieffettuateDoc_window_title(estremi));
			}
		});

		permessiUdButton = new ImgButtonItem("permessiUd", "buttons/key.png", "Permessi");
		permessiUdButton.setAlwaysEnabled(true);
		permessiUdButton.setColSpan(1);
		permessiUdButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record(vm.getValues());
				new PermessiUdPopup(record);
			}
		});
		
		idUDTrasmessaInUscitaConItem = new HiddenItem("idUDTrasmessaInUscitaCon");
		estremiUDTrasmessoInUscitaConItem = new HiddenItem("estremiUDTrasmessoInUscitaCon");
		
		estremiUDTrasmessoInUscitaConButton = new ImgButtonItem("estremiUDTrasmessoInUscitaConButton", "protocollazione/estremi_ud_trasmessoinuscita.png", "Uscito con");
		estremiUDTrasmessoInUscitaConButton.setAlwaysEnabled(true);
		estremiUDTrasmessoInUscitaConButton.setColSpan(1);
		estremiUDTrasmessoInUscitaConButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				final String idUD = idUDTrasmessaInUscitaConItem != null && idUDTrasmessaInUscitaConItem.getValue() != null &&
						!"".equals(idUDTrasmessaInUscitaConItem.getValue())
						? (String)idUDTrasmessaInUscitaConItem.getValue() : null;
				if(idUD != null && !"".equals(idUD)){
					final String estremiUD = estremiUDTrasmessoInUscitaConItem != null && estremiUDTrasmessoInUscitaConItem.getValue() != null 
							&& !"".equals(estremiUDTrasmessoInUscitaConItem.getValue()) 
							? (String) estremiUDTrasmessoInUscitaConItem.getValue() : null;
					Record recordToShow = new Record();
					recordToShow.setAttribute("idUd", idUD);
					new DettaglioRegProtAssociatoWindow(recordToShow, "Dettaglio "+estremiUD);
				}
			}
		});
		estremiUDTrasmessoInUscitaConButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				final String idUD = idUDTrasmessaInUscitaConItem != null && idUDTrasmessaInUscitaConItem.getValue() != null &&
						!"".equals(idUDTrasmessaInUscitaConItem.getValue())
						? (String)idUDTrasmessaInUscitaConItem.getValue() : null;
				final String estremiUD = estremiUDTrasmessoInUscitaConItem != null && estremiUDTrasmessoInUscitaConItem.getValue() != null 
						&& !"".equals(estremiUDTrasmessoInUscitaConItem.getValue()) 
						? (String) estremiUDTrasmessoInUscitaConItem.getValue() : null;
				if((idUD != null && !"".equals(idUD)) && (estremiUD != null && !"".equals(estremiUD))){
					estremiUDTrasmessoInUscitaConButton.setPrompt("Dettaglio "+estremiUD);
					return true;
				}
				return false;
			}
		});
		
		if(showDocumentiCollegatiButton()) {
			
			presenzaDocCollegatiItem = new HiddenItem("presenzaDocCollegati");
			
			documentiDaCollegareItem = new HiddenItem("listaDocumentiDaCollegare");

			documentiCollegatiButton = new ImgButtonItem("documentiCollegatiButton", "buttons/link.png", "Documenti collegati");
			documentiCollegatiButton.setAlwaysEnabled(true);
			documentiCollegatiButton.setColSpan(1);
			documentiCollegatiButton.addIconClickHandler(new IconClickHandler() {
	
				@Override
				public void onIconClick(IconClickEvent event) {
					Record record = new Record(vm.getValues());
					DocumentiCollegatiPopup documentiCollegatiPopup = new DocumentiCollegatiPopup(record) {
	
						@Override
						public void onSaveButtonClick(RecordList listaDocumentiCollegati) {
							if (listaDocumentiCollegati != null && listaDocumentiCollegati.getLength() > 0) {
								protocolloGeneraleForm.setValue("presenzaDocCollegati", "1");
							} else {
								protocolloGeneraleForm.setValue("presenzaDocCollegati", "0");
							}
							protocolloGeneraleForm.markForRedraw();
						}
					};
				}
			});
			documentiCollegatiButton.setShowIfCondition(new FormItemIfFunction() {
	
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return protocolloGeneraleForm.getValueAsString("presenzaDocCollegati") != null && "1".equals(protocolloGeneraleForm.getValueAsString("presenzaDocCollegati"));
				}
			});
		
			collegaDocumentiButton = new ImgButtonItem("collegaDocumentiButton", "buttons/addlink.png", "Collega documenti");
			collegaDocumentiButton.setAlwaysEnabled(true);
			collegaDocumentiButton.setColSpan(1);
			collegaDocumentiButton.addIconClickHandler(new IconClickHandler() {
	
				@Override
				public void onIconClick(IconClickEvent event) {
					Record record = new Record(vm.getValues());
					DocumentiCollegatiPopup documentiCollegatiPopup = new DocumentiCollegatiPopup(record) {
	
						@Override
						public void onSaveButtonClick(RecordList listaDocumentiCollegati) {
							if (listaDocumentiCollegati != null && listaDocumentiCollegati.getLength() > 0) {
								protocolloGeneraleForm.setValue("presenzaDocCollegati", "1");
							} else {
								protocolloGeneraleForm.setValue("presenzaDocCollegati", "0");
							}
							protocolloGeneraleForm.markForRedraw();
						}
					};
				}
			});
			collegaDocumentiButton.setShowIfCondition(new FormItemIfFunction() {
	
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {					
					Record record = new Record(vm.getValues());
					
					// Il bottone si abilita se posso modificare i dati oppure se sono abilitato a collegare i documenti
					boolean show = showDocumentiCollegatiButton(record) && (protocolloGeneraleForm.getValueAsString("presenzaDocCollegati") == null ||  "".equals(protocolloGeneraleForm.getValueAsString("presenzaDocCollegati")) || "0".equals(protocolloGeneraleForm.getValueAsString("presenzaDocCollegati"))); 
					return show;
				}
			});
		}
		
		if(showAltriRiferimentiButton()) {		
			
			altriRiferimentiButton = new ImgButtonItem("altriRiferimentiButton", "buttons/altriRiferimenti.png",
					"Altri riferimenti");
			altriRiferimentiButton.setAlwaysEnabled(true);
			altriRiferimentiButton.setColSpan(1);
			altriRiferimentiButton.addIconClickHandler(new IconClickHandler() {
	
				@Override
				public void onIconClick(IconClickEvent event) {
					Record record = new Record(vm.getValues());
					new AltriRiferimentiPopup(record);
				}
			});
		}

		conDatiAnnullatiButton = new ImgButtonItem("conDatiAnnullati", "protocollazione/variazioni.png",
				I18NUtil.getMessages().protocollazione_detail_conDatiAnnullatiButton_prompt());
		conDatiAnnullatiButton.setAlwaysEnabled(true);
		conDatiAnnullatiButton.setColSpan(1);
		conDatiAnnullatiButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null ? (Boolean) value : false;
			}
		});
		conDatiAnnullatiButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				String estremi = getTipoEstremiDocumento();
				new VariazioniDatiRegWindow(idUd, estremi);
			}
		});

		List<FormItem> registrazioneFields = new ArrayList<FormItem>();
		registrazioneFields.add(idOperRegistrazioneItem);
		registrazioneFields.add(idUdHiddenItem);
		registrazioneFields.add(idUdNuovoComeCopiaHiddenItem);
		registrazioneFields.add(prefKeyModelloHiddenItem);
		registrazioneFields.add(prefNameModelloHiddenItem);
		registrazioneFields.add(useridModelloHiddenItem);
		registrazioneFields.add(idUoModelloHiddenItem);
		registrazioneFields.add(codStatoDettHiddenItem);
		registrazioneFields.add(codStatoHiddenItem);
		registrazioneFields.add(idProcessHiddenItem);
		registrazioneFields.add(estremiProcessHiddenItem);
		registrazioneFields.add(ruoloSmistamentoAttoHiddenItem);
		if (!showDetailSectionTipoDocumento()) {
			registrazioneFields.add(tipoDocumentoSalvatoHiddenItem);
			registrazioneFields.add(tipoDocumentoHiddenItem);
			registrazioneFields.add(nomeTipoDocumentoHiddenItem);
		}
		registrazioneFields.add(flgTipoProvItem);
		registrazioneFields.add(flgVersoBozzaItem);
		registrazioneFields.add(timestampGetDataItem);
		registrazioneFields.add(iconaTipoProtocolloItem);
		registrazioneFields.add(idEmailArrivoHiddenItem);
		registrazioneFields.add(casellaIsPECHiddenItem);		
		registrazioneFields.add(emailInviataFlgPECHiddenItem);
		registrazioneFields.add(emailInviataFlgPEOHiddenItem);
		registrazioneFields.add(emailArrivoInteroperabileHiddenItem);
		registrazioneFields.add(emailProvIndirizzoHiddenItem);
		registrazioneFields.add(emailProvFlgPECHiddenItem);
		registrazioneFields.add(emailProvFlgCasellaIstituzHiddenItem);
		registrazioneFields.add(emailProvFlgDichIPAHiddenItem);
		registrazioneFields.add(emailProvGestorePECHiddenItem);
		registrazioneFields.add(inviataMailInteroperabileHiddenItem);
		registrazioneFields.add(iconaEmailRicevutaItem);
		registrazioneFields.add(iconaEmailInviataItem);
		registrazioneFields.add(ricEccezioniInteropItem);
		registrazioneFields.add(ricAggiornamentiInteropItem);
		registrazioneFields.add(ricAnnullamentiInteropItem);
		registrazioneFields.add(tipoProtocolloItem);
		registrazioneFields.add(codCategoriaProtocolloItem);
		if (showSiglaProtocolloItem()) {
			registrazioneFields.add(siglaProtocolloItem);
		}
		registrazioneFields.add(nroProtocolloItem);
		registrazioneFields.add(subProtocolloItem);
		registrazioneFields.add(dataProtocolloItem);
		registrazioneFields.add(idUdAttoAutAnnProtocolloItem);
		registrazioneFields.add(dettaglioUdAttoAutAnnProtocolloButton);
		registrazioneFields.add(datiAnnullamentoHiddenItem);
		registrazioneFields.add(datiRichAnnullamentoHiddenItem);
		registrazioneFields.add(iconaAnnullataItem);
		registrazioneFields.add(iconaRichAnnullamentoItem);
		registrazioneFields.add(societaItem);
		registrazioneFields.add(desUserProtocolloItem);
		registrazioneFields.add(desUOProtocolloItem);
		registrazioneFields.add(estremiRepertorioPerStrutturaItem);		
		registrazioneFields.add(abilAssegnazioneSmistamentoItem);
		registrazioneFields.add(abilRispondiItem);
		registrazioneFields.add(visualizzaDettStdProtButton);		
		registrazioneFields.add(iterProcessoCollegatoButton);
		registrazioneFields.add(operazioniEffettuateButton);
		registrazioneFields.add(permessiUdButton);
		registrazioneFields.add(idUDTrasmessaInUscitaConItem);
		registrazioneFields.add(estremiUDTrasmessoInUscitaConItem);				
		registrazioneFields.add(estremiUDTrasmessoInUscitaConButton);
		if(showDocumentiCollegatiButton()) {
			registrazioneFields.add(presenzaDocCollegatiItem);
			registrazioneFields.add(documentiDaCollegareItem);
			registrazioneFields.add(documentiCollegatiButton);
			registrazioneFields.add(collegaDocumentiButton);
		}
		if(showAltriRiferimentiButton()) {
			registrazioneFields.add(altriRiferimentiButton);
		}
		registrazioneFields.add(conDatiAnnullatiButton);
		
		protocolloGeneraleForm.setFields(registrazioneFields.toArray(new FormItem[registrazioneFields.size()]));		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			protocolloGeneraleForm.setCanFocus(true);		
		}
	}
	
	
	/**
	 * Metodo che crea il form numerazione secondaria della sezione
	 * "Registrazione"
	 * 
	 */
	protected void createRegistrazioneSecondariaForm() {

		registrazioneSecondariaForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {

				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		registrazioneSecondariaForm.setValuesManager(vm);
		registrazioneSecondariaForm.setWidth100();
		registrazioneSecondariaForm.setPadding(5);
		registrazioneSecondariaForm.setWrapItemTitles(false);
		registrazioneSecondariaForm.setNumCols(18);
		registrazioneSecondariaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		registrazioneSecondariaForm.setTabSet(tabSet);
		registrazioneSecondariaForm.setTabID("HEADER");
		registrazioneSecondariaForm.setVisibility(Visibility.HIDDEN);

		iconaTipoRegistrazioneSecondariaItem = new ImgItem("iconaTipoProv", getIconAltraNumerazione(),
				getTitleAltraNumerazione());
		iconaTipoRegistrazioneSecondariaItem.setColSpan(1);
		iconaTipoRegistrazioneSecondariaItem.setIconWidth(16);
		iconaTipoRegistrazioneSecondariaItem.setIconHeight(16);
		iconaTipoRegistrazioneSecondariaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoRegistrazioneSecondariaItem.setAlign(Alignment.LEFT);
		iconaTipoRegistrazioneSecondariaItem.setWidth(16);
		iconaTipoRegistrazioneSecondariaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String siglaNumerazioneSecondaria = (registrazioneSecondariaSiglaItem.getValue() != null) ? (String) registrazioneSecondariaSiglaItem.getValue() : "";
				if (siglaNumerazioneSecondaria.equalsIgnoreCase("ISTANZA")) {
					iconaTipoRegistrazioneSecondariaItem.setSrc("menu/istanze.png");
					iconaTipoRegistrazioneSecondariaItem.setPrompt(I18NUtil.getMessages().archivio_list_tipoProtocolloIstanzaAlt_value());											
				} else {
					iconaTipoRegistrazioneSecondariaItem.setSrc(getIconAltraNumerazione());
					iconaTipoRegistrazioneSecondariaItem.setPrompt(getTitleAltraNumerazione());											
				}				
				return true;
			}
		});

		registrazioneSecondariaSiglaItem = new TextItem("siglaNumerazioneSecondaria",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneSiglaItem_title());
		registrazioneSecondariaSiglaItem.setWidth(80);
		registrazioneSecondariaSiglaItem.setShowTitle(false);

		registrazioneSecondariaNumeroItem = new NumericItem("numeroNumerazioneSecondaria",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneNumeroItem_title());

		registrazioneSecondariaSubItem = new TextItem("subNumerazioneSecondaria", "Sub");
		registrazioneSecondariaSubItem.setWidth(80);
		registrazioneSecondariaSubItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		registrazioneSecondariaDataRegistrazioneItem = new DateTimeItem("dataRegistrazioneNumerazioneSecondaria",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneDataRegistrazioneItem_title());
		registrazioneSecondariaDataRegistrazioneItem.setColSpan(1);
		
		dtEsecutivitaItem = new DateItem("dtEsecutivita", "Data esecutività");
		dtEsecutivitaItem.setColSpan(1);
		dtEsecutivitaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		flgImmediatamenteEsegItem = new CheckboxItem("flgImmediatamenteEseg", "Immediatamente eseguibile");
		flgImmediatamenteEsegItem.setColSpan(1);
		flgImmediatamenteEsegItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && (Boolean) value;
			}
		});
		
		visualizzaDettIstanzaButton = new ImgButtonItem("visualizzaDettIstanzaButton", "buttons/dati_istanza.png", "Visualizza dati istanza");
		visualizzaDettIstanzaButton.setAlwaysEnabled(true);
		visualizzaDettIstanzaButton.setEndRow(false);
		visualizzaDettIstanzaButton.setColSpan(1);
		visualizzaDettIstanzaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
				String tipoIstanza = "";
				if(!showDetailSectionTipoDocumento()) {
					tipoIstanza = nomeTipoDocumentoHiddenItem != null && nomeTipoDocumentoHiddenItem.getValue() != null ? String.valueOf(nomeTipoDocumentoHiddenItem.getValue()) : "";
				} else {
					tipoIstanza = tipoDocumentoItem != null && tipoDocumentoItem.getDisplayValue() != null ? tipoDocumentoItem.getDisplayValue() : "";
				}
				String nomeEntitaIstanza = "";
				if (tipoIstanza.equals("CED")) {
					nomeEntitaIstanza = "istanze_ced";
				}
				if (tipoIstanza.equals("AUTOTUTELA")) {
					nomeEntitaIstanza = "istanze_autotutela";
				}
				IstanzeWindow lIstanzeWindow = new IstanzeWindow(nomeEntitaIstanza, null) {

					@Override
					public void manageAfterCloseWindow() {
						
					}
				};
				lIstanzeWindow.viewRecord(idUd);					
			}
		});
		visualizzaDettIstanzaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				Record record = new Record(vm.getValues());
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;	
				String siglaNumerazioneSecondaria = (registrazioneSecondariaSiglaItem.getValue() != null) ? (String) registrazioneSecondariaSiglaItem.getValue() : "";
				if (siglaNumerazioneSecondaria.equalsIgnoreCase("ISTANZA")) {
					return idUd != null && !"".equals(idUd);	
				}				
				return false;
			}
		});
		
		registrazioneSecondariaIdUdAttoAutAnnItem = new HiddenItem("idUdAttoAutAnnRegSecondaria");
		
		ImgButtonItem dettaglioUdAttoAutAnnRegSecondariaButton = new ImgButtonItem("dettaglioUdAttoAutAnnRegSecondariaButton", "menu/atti_autorizzazione.png", "Visualizza dettaglio atto annullamento");
		dettaglioUdAttoAutAnnRegSecondariaButton.setColSpan(1);
		dettaglioUdAttoAutAnnRegSecondariaButton.setAlwaysEnabled(true);
		dettaglioUdAttoAutAnnRegSecondariaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record();
				lRecord.setAttribute("idAtto", registrazioneSecondariaForm.getValueAsString("idUdAttoAutAnnRegSecondaria"));
				new DettaglioAttoAutorizzazioneAnnRegWindow(lRecord, "Dettaglio atto annullamento");	
			}
		});
		dettaglioUdAttoAutAnnRegSecondariaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return registrazioneSecondariaForm.getValueAsString("idUdAttoAutAnnRegSecondaria") != null && !"".equals(registrazioneSecondariaForm.getValueAsString("idUdAttoAutAnnRegSecondaria"));
			}
		});
		
		ImgButtonItem annullaRegistrazioneSecondariaButton = new ImgButtonItem("annullaRegistrazioneSecondariaButton", "buttons/delete.png", "Annulla numerazione secondaria");
		annullaRegistrazioneSecondariaButton.setColSpan(1);
		annullaRegistrazioneSecondariaButton.setAlwaysEnabled(true);
		annullaRegistrazioneSecondariaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				final Record detailRecord = getRecordToSave();
				Record lRecord = new Record();
				lRecord.setAttribute("codCategoria", detailRecord.getAttribute("tipoNumerazioneSecondaria"));
				lRecord.setAttribute("sigla", detailRecord.getAttribute("siglaNumerazioneSecondaria"));
				lRecord.setAttribute("anno", detailRecord.getAttribute("annoNumerazioneSecondaria"));
				lRecord.setAttribute("nro", detailRecord.getAttribute("numeroNumerazioneSecondaria"));
				RichAnnullamentoRegPopup lRichAnnullamentoRegPopup = new RichAnnullamentoRegPopup("", lRecord) {

					@Override
					public void onClickOkButton(final DSCallback callback) {
						
						String motiviRichAnnullamento = new Record(_form.getValues()).getAttribute("motiviRichAnnullamento");
						detailRecord.setAttribute("motiviRichAnnullamento", motiviRichAnnullamento);
						GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("AnnullaNumerazioneSecondariaDataSource");
						lGWTRestService.call(detailRecord, new ServiceCallback<Record>() {
	
							@Override
							public void execute(Record object) {
								reload(new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										setSaved(true);
										if(layout != null) {
											layout.viewMode();
										} else {
											viewMode();
										}
										if(callback != null) {
											callback.execute(response, null, new DSRequest());
										}
									}
								});
							}
						});
					}
					
					@Override
					public String getWindowTitle(String segnatura, Record record) {
						return "Annullamento numerazione secondaria";
					}
				};				
			}
		});
		annullaRegistrazioneSecondariaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ANN_REG_SECONDARIA") && mode != null && mode.equals("view")) {
					return isModalitaWizard() && isProtocolloGeneraleConRepertorio();
				}
				return false;
			}
		});	
		
		registrazioneSecondariaForm.setFields(iconaTipoRegistrazioneSecondariaItem, registrazioneSecondariaSiglaItem, registrazioneSecondariaNumeroItem, registrazioneSecondariaSubItem,
				registrazioneSecondariaDataRegistrazioneItem,
				dtEsecutivitaItem,flgImmediatamenteEsegItem,
				visualizzaDettIstanzaButton, registrazioneSecondariaIdUdAttoAutAnnItem, dettaglioUdAttoAutAnnRegSecondariaButton, annullaRegistrazioneSecondariaButton);
	}
	
	/**
	 * Metodo che crea il form altre numerazioni UD della sezione
	 * "Registrazione"
	 * 
	 */
	protected void createAltreNumerazioniUDForm() {
		
		altreNumerazioniUDForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {

				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		altreNumerazioniUDForm.setValuesManager(vm);
		altreNumerazioniUDForm.setWidth100();
		altreNumerazioniUDForm.setPadding(5);
		altreNumerazioniUDForm.setWrapItemTitles(false);
		altreNumerazioniUDForm.setNumCols(18);
		altreNumerazioniUDForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		altreNumerazioniUDForm.setTabSet(tabSet);
		altreNumerazioniUDForm.setTabID("HEADER");
		altreNumerazioniUDForm.setVisibility(Visibility.HIDDEN);
		
		altreNumerazioniUDItem = new TextItem("altreNumerazioniUD", "Altre registrazioni/numeraz.");
		altreNumerazioniUDItem.setWidth(690);
		altreNumerazioniUDItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		altreNumerazioniUDForm.setFields(altreNumerazioniUDItem);
	}

	/**
	 * Metodo che crea il form altra numerazione principale della sezione "Registrazione"
	 * 
	 */
	protected void createAltraNumerazioneForm() {

		altraNumerazioneForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		altraNumerazioneForm.setValuesManager(vm);
		altraNumerazioneForm.setWidth100();
		altraNumerazioneForm.setPadding(5);
		altraNumerazioneForm.setWrapItemTitles(false);
		altraNumerazioneForm.setNumCols(18);
		altraNumerazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		altraNumerazioneForm.setTabSet(tabSet);
		altraNumerazioneForm.setTabID("HEADER");
		altraNumerazioneForm.setVisibility(Visibility.HIDDEN);
		
		regNumSecondariaDesGruppoItem = new HiddenItem("regNumSecondariaDesGruppo");

		iconaTipoAltraNumerazioneItem = new ImgItem("iconaTipoProv", getIconAltraNumerazione(),
				getTitleAltraNumerazione());
		iconaTipoAltraNumerazioneItem.setColSpan(1);
		iconaTipoAltraNumerazioneItem.setIconWidth(16);
		iconaTipoAltraNumerazioneItem.setIconHeight(16);
		iconaTipoAltraNumerazioneItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoAltraNumerazioneItem.setAlign(Alignment.LEFT);
		iconaTipoAltraNumerazioneItem.setWidth(16);
		iconaTipoAltraNumerazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				iconaTipoAltraNumerazioneItem.setSrc(getIconAltraNumerazione());
				iconaTipoAltraNumerazioneItem.setPrompt(getTitleAltraNumerazione());						
				return true;
			}
		});

		altraNumerazioneSiglaItem = new TextItem("siglaProtocollo",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneSiglaItem_title());
		altraNumerazioneSiglaItem.setWidth(80);
		altraNumerazioneSiglaItem.setColSpan(1);
		altraNumerazioneSiglaItem.setShowTitle(false);

		altraNumerazioneNumeroItem = new NumericItem("nroProtocollo",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneNumeroItem_title());
		altraNumerazioneNumeroItem.setColSpan(1);
		
		altraNumerazioneSubItem = new TextItem("subProtocollo", "Sub");
		altraNumerazioneSubItem.setWidth(80);
		altraNumerazioneSubItem.setColSpan(1);
		altraNumerazioneSubItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		altraNumerazioneDataRegistrazioneItem = new DateTimeItem("dataProtocollo",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneDataRegistrazioneItem_title());
		altraNumerazioneDataRegistrazioneItem.setColSpan(1);

		altraNumerazioneRedattoreItem = new TextItem("desUserProtocollo",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneRedattoreItem_title());
		altraNumerazioneRedattoreItem.setWidth(200);

		altraNumerazioneDestinatarioItem = new TextItem("desUOProtocollo",
				I18NUtil.getMessages().protocollazione_detail_altraNumerazioneDestinatarioItem_title());
		altraNumerazioneDestinatarioItem.setWidth(200);
		
		altraNumerazioneEstremiRepXStruttItem = new TextItem("estremiRepertorioPerStruttura", "N° per struttura");
		altraNumerazioneEstremiRepXStruttItem.setWidth(200);
		altraNumerazioneEstremiRepXStruttItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);		 
			}
		});
		
		List<FormItem> altraNumerazioneFields = new ArrayList<FormItem>();
		altraNumerazioneFields.add(regNumSecondariaDesGruppoItem);
		altraNumerazioneFields.add(iconaTipoAltraNumerazioneItem); 
		altraNumerazioneFields.add(altraNumerazioneSiglaItem);
		altraNumerazioneFields.add(altraNumerazioneNumeroItem);
		altraNumerazioneFields.add(altraNumerazioneSubItem);
		altraNumerazioneFields.add(altraNumerazioneDataRegistrazioneItem);
		
		/**
		 * Annullamento repertorio
		 */
		altraNumerazioneFields.add(idUdAttoAutAnnProtocolloItem);
		altraNumerazioneFields.add(dettaglioUdAttoAutAnnProtocolloButton);
		altraNumerazioneFields.add(datiAnnullamentoHiddenItem);
		altraNumerazioneFields.add(datiRichAnnullamentoHiddenItem);
		altraNumerazioneFields.add(iconaAnnullataItem);
		altraNumerazioneFields.add(iconaRichAnnullamentoItem);
		
		altraNumerazioneFields.add(altraNumerazioneRedattoreItem);
		altraNumerazioneFields.add(altraNumerazioneDestinatarioItem);
		altraNumerazioneFields.add(altraNumerazioneEstremiRepXStruttItem);
		altraNumerazioneFields.add(emailInviataFlgPECHiddenItem);
		altraNumerazioneFields.add(emailInviataFlgPEOHiddenItem);
		altraNumerazioneFields.add(iconaEmailInviataItem);		
		altraNumerazioneFields.add(iterProcessoCollegatoButton); 
		altraNumerazioneFields.add(operazioniEffettuateButton);
		altraNumerazioneFields.add(permessiUdButton);
		altraNumerazioneFields.add(idUDTrasmessaInUscitaConItem);
		altraNumerazioneFields.add(estremiUDTrasmessoInUscitaConItem);		
		altraNumerazioneFields.add(estremiUDTrasmessoInUscitaConButton);
		if(showDocumentiCollegatiButton()) {
			altraNumerazioneFields.add(presenzaDocCollegatiItem);
			altraNumerazioneFields.add(documentiDaCollegareItem);
			altraNumerazioneFields.add(documentiCollegatiButton);
			altraNumerazioneFields.add(collegaDocumentiButton);
		}
		if(showAltriRiferimentiButton()) {
			altraNumerazioneFields.add(altriRiferimentiButton);
		}
				
		altraNumerazioneFields.add(conDatiAnnullatiButton);
		
		altraNumerazioneForm.setFields(altraNumerazioneFields.toArray(new FormItem[altraNumerazioneFields.size()]));
	}
	
	/**
	 * Metodo che crea il form altra numerazione provvisoria della sezione "Registrazione"
	 * 
	 */
	protected void createAltraNumerazioneProvvisoriaForm() {
		
		altraNumerazioneProvvisoriaForm = new DynamicForm() {
	
			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		altraNumerazioneProvvisoriaForm.setValuesManager(vm);
		altraNumerazioneProvvisoriaForm.setWidth100();
		altraNumerazioneProvvisoriaForm.setPadding(5);
		altraNumerazioneProvvisoriaForm.setWrapItemTitles(false);
		altraNumerazioneProvvisoriaForm.setNumCols(18);
		altraNumerazioneProvvisoriaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		altraNumerazioneProvvisoriaForm.setTabSet(tabSet);
		altraNumerazioneProvvisoriaForm.setTabID("HEADER");
		altraNumerazioneProvvisoriaForm.setVisibility(Visibility.HIDDEN);

		iconaTipoAltraNumerazioneProvvisoriaItem = new ImgItem("iconaTipoProv", getIconAltraNumerazioneProvvisoria(), getTitleAltraNumerazioneProvvisoria());
		iconaTipoAltraNumerazioneProvvisoriaItem.setColSpan(1);
		iconaTipoAltraNumerazioneProvvisoriaItem.setIconWidth(16);
		iconaTipoAltraNumerazioneProvvisoriaItem.setIconHeight(16);
		iconaTipoAltraNumerazioneProvvisoriaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoAltraNumerazioneProvvisoriaItem.setAlign(Alignment.LEFT);
		iconaTipoAltraNumerazioneProvvisoriaItem.setWidth(16);
	
		altraNumerazioneProvvisoriaSiglaItem = new TextItem("siglaNumerazioneSecondaria", I18NUtil.getMessages()
				.protocollazione_detail_altraNumerazioneSiglaItem_title());
		altraNumerazioneProvvisoriaSiglaItem.setWidth(80);
		altraNumerazioneProvvisoriaSiglaItem.setShowTitle(false);
	
		altraNumerazioneProvvisoriaNumeroItem = new NumericItem("numeroNumerazioneSecondaria", I18NUtil.getMessages()
				.protocollazione_detail_altraNumerazioneNumeroItem_title());
		
		altraNumerazioneProvvisoriaSubItem = new TextItem("subNumerazioneSecondaria", "Sub");
		altraNumerazioneProvvisoriaSubItem.setWidth(80);
		altraNumerazioneProvvisoriaSubItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		altraNumerazioneProvvisoriaDataRegistrazioneItem = new DateTimeItem("dataRegistrazioneNumerazioneSecondaria", I18NUtil.getMessages()
				.protocollazione_detail_altraNumerazioneDataRegistrazioneItem_title());
		altraNumerazioneProvvisoriaDataRegistrazioneItem.setColSpan(1);
	
		altraNumerazioneProvvisoriaForm.setFields(iconaTipoAltraNumerazioneProvvisoriaItem, altraNumerazioneProvvisoriaSiglaItem, altraNumerazioneProvvisoriaNumeroItem,
				altraNumerazioneProvvisoriaSubItem, altraNumerazioneProvvisoriaDataRegistrazioneItem);
	}
	
	/**
	 * Metodo che crea il form atto liquidazione della sezione "Registrazione"
	 * 
	 */
	protected void createAttoLiquidazioneForm() {
		
		attoLiquidazioneForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		attoLiquidazioneForm.setValuesManager(vm);
		attoLiquidazioneForm.setWidth100();
		attoLiquidazioneForm.setPadding(5);
		attoLiquidazioneForm.setWrapItemTitles(false);
		attoLiquidazioneForm.setNumCols(18);
		attoLiquidazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		attoLiquidazioneForm.setTabSet(tabSet);
		attoLiquidazioneForm.setTabID("HEADER");
		attoLiquidazioneForm.setHeight(1);
		
		estremiAttoLiquidazioneItem = new TextItem("estremiAttoLiquidazione", "Atto liquidazione");		
		estremiAttoLiquidazioneItem.setWidth(250);
		estremiAttoLiquidazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);		 
			}
		});
		
		idUdLiquidazioneItem = new HiddenItem("idUdLiquidazione");
		
		ImgButtonItem dettaglioUdAttoLiquidazioneButton = new ImgButtonItem("dettaglioUdAttoLiquidazioneButton", "buttons/detail.png", "Visualizza dettaglio atto liquidazione");
		dettaglioUdAttoLiquidazioneButton.setColSpan(1);
		dettaglioUdAttoLiquidazioneButton.setAlwaysEnabled(true);
		dettaglioUdAttoLiquidazioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {				
				Record record = new Record();
				record.setAttribute("idUd", attoLiquidazioneForm.getValueAsString("idUdLiquidazione"));
				new DettaglioRegProtAssociatoWindow(record, "Dettaglio atto liquidazione " + attoLiquidazioneForm.getValueAsString("estremiAttoLiquidazione"));									
			}
		});
		dettaglioUdAttoLiquidazioneButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return attoLiquidazioneForm.getValueAsString("estremiAttoLiquidazione") != null && !"".equals(attoLiquidazioneForm.getValueAsString("estremiAttoLiquidazione")) && 
						attoLiquidazioneForm.getValueAsString("idUdLiquidazione") != null && !"".equals(attoLiquidazioneForm.getValueAsString("idUdLiquidazione"));
			}
		});
		
		attoLiquidazioneForm.setFields(estremiAttoLiquidazioneItem, idUdLiquidazioneItem, dettaglioUdAttoLiquidazioneButton);
	}
	
	protected void createStatoForm() {
		statoForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		statoForm.setValuesManager(vm);
		statoForm.setWidth100();
		statoForm.setPadding(5);
		statoForm.setWrapItemTitles(false);
		statoForm.setNumCols(2);
		statoForm.setColWidths(1, "*");
		statoForm.setTabSet(tabSet);
		statoForm.setTabID("HEADER");
		statoForm.setHeight(1);
		
		statoItem = new TextItem("stato", I18NUtil.getMessages().protocollazione_detail_stato_detail());
		statoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		statoForm.setFields(statoItem);
	}

	/**
	 * Metodo che indica se mostrare o meno la sezione di nuova registrazione
	 * 
	 */
	public boolean showDetailSectionNuovaRegistrazione() {
		return false;
	}

	/**
	 * Metodo che controlla se è presente una protocollazione generale associata
	 * al documento
	 * 
	 */
	public boolean isConProtocolloGenerale() {
		return tipoProtocolloItem.getValue() != null && ((String) tipoProtocolloItem.getValue()).equalsIgnoreCase("PG");
	}
	
	/**
	 * Metodo che controlla se è presente un'altra numerazione UD 
	 */
	public boolean isConAltraNumerazioneUD() {
		return altreNumerazioniUDItem.getValue() != null && !"".equals((String) tipoProtocolloItem.getValue());
	}

	/**
	 * Metodo che controlla se è presente una protocollazione particolare associata
	 * al documento 
	 */
	public boolean isConProtocolloParticolare() {
		return tipoProtocolloItem.getValue() != null && ((String) tipoProtocolloItem.getValue()).equalsIgnoreCase("PP");
	}
	/**
	 * Metodo che imposta il valore di default del check "A protocollo generale"
	 * 
	 */
	public boolean getDefaultProtocolloGenerale() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_REG_PG_DEFAULT_SU_REP");
	}

	/**
	 * Metodo che restituisce il titolo della sezione di nuova registrazione
	 * 
	 */
	public String getTitleDetailSectionNuovaRegistrazione() {
		return I18NUtil.getMessages().protocollazione_detail_registrazioneForm_title();
	}

	/**
	 * Metodo che crea la sezione di nuova registrazione
	 * 
	 */
	protected void createDetailSectionNuovaRegistrazione() {

		createNuovaRegistrazioneForm();

		detailSectionNuovaRegistrazione = new ProtocollazioneHeaderDetailSection(getTitleDetailSectionNuovaRegistrazione(), true, true,
				false, nuovaRegistrazioneForm){

			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return getShowFirstCanvasWhenEmptyAfterOpen();
			}
		};
	}
	
	
	/**
	 * Metodo che restituisce il titolo della Checkbox protocolloGeneraleItem
	 * 
	 */
	public String getTitleProtocolloGeneraleItem() {
		return "a Protocollo Generale";
	}

	/**
	 * Metodo che crea il form della sezione di nuova registrazione
	 * 
	 */
	protected void createNuovaRegistrazioneForm() {

		nuovaRegistrazioneForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		nuovaRegistrazioneForm.setValuesManager(vm);
		nuovaRegistrazioneForm.setWidth100();
		nuovaRegistrazioneForm.setPadding(5);
		nuovaRegistrazioneForm.setWrapItemTitles(false);
		nuovaRegistrazioneForm.setNumCols(18);
		nuovaRegistrazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		nuovaRegistrazioneForm.setTabSet(tabSet);
		nuovaRegistrazioneForm.setTabID("HEADER");

		protocolloGeneraleItem = new CheckboxItem("protocolloGenerale", getTitleProtocolloGeneraleItem());
		protocolloGeneraleItem.setColSpan(1);
		protocolloGeneraleItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToProt();
			}
		});
		protocolloGeneraleItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				nuovaRegistrazioneForm.markForRedraw();
			}
		});

		nuovaRegistrazioneForm.setFields(protocolloGeneraleItem);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione di nuova registrazione al protocollo generale con repertorio
	 * 
	 */
	
	public boolean showDetailSectionNuovaRegistrazioneProtGenerale() {
		// per ora lo attivo solo nella modalità wizard ma può essere usato anche nella modalità standard
		return isModalitaWizard() && !isAltraNumerazione() && !isProtocollazioneDetailBozze() && 
				!isIstanzeDetail() &&
				!isRepertorioDetailEntrata() && !isRepertorioDetailInterno() && !isRepertorioDetailUscita() &&
				!isRegistroFattureDetail() &&
				!isProtocolloGeneraleConRepertorio();
	}
	
	/**
	 * Metodo che crea la sezione di nuova registrazione al protocollo generale con repertorio
	 * 
	 */
	
	protected void createDetailSectionNuovaRegistrazioneProtGenerale() {
		
		createNuovaRegistrazioneProtGeneraleForm();
		
		detailSectionNuovaRegistrazioneProtGenerale = new ProtocollazioneHeaderDetailSection("Registrazione", true, false, false, nuovaRegistrazioneProtGeneraleForm);
	}
	
	/**
	 * Metodo che crea il form della sezione di nuova registrazione al protocollo generale con repertorio
	 * 
	 */
	
	protected void createNuovaRegistrazioneProtGeneraleForm() {
		
		nuovaRegistrazioneProtGeneraleForm = new DynamicForm();
		nuovaRegistrazioneProtGeneraleForm.setValuesManager(vm);
		nuovaRegistrazioneProtGeneraleForm.setWidth100();
		nuovaRegistrazioneProtGeneraleForm.setPadding(5);
		nuovaRegistrazioneProtGeneraleForm.setWrapItemTitles(false);
		nuovaRegistrazioneProtGeneraleForm.setNumCols(18);
		nuovaRegistrazioneProtGeneraleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		nuovaRegistrazioneProtGeneraleForm.setTabSet(tabSet);
		nuovaRegistrazioneProtGeneraleForm.setTabID("HEADER");
		
		protocolloGeneraleItem = new CheckboxItem("protocolloGenerale", "a Protocollo Generale");
		protocolloGeneraleItem.setColSpan(1);	
		protocolloGeneraleItem.setWidth(10);
		protocolloGeneraleItem.setDefaultValue(true);
		protocolloGeneraleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				item.setValue(true);
				item.setCanEdit(false);
				String tipoNumerazionePrincipale = vm.getValueAsString("tipoProtocollo") != null ? vm.getValueAsString("tipoProtocollo") : "";
				if((!isRepertorioDetailEntrata() && !isRepertorioDetailInterno()  && !isRepertorioDetailUscita()) && 
					 tipoNumerazionePrincipale.equalsIgnoreCase("PG")) {				
					return false;
				}
				return true;
			}
		});
			
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setWidth(15);
		
		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioDS.addParam("flgTipoProv", getFlgTipoProv());

		repertorioItem = new SelectItem("repertorio", "Numera anche nel registro");
		repertorioItem.setValueField("key");
		repertorioItem.setDisplayField("value");
		repertorioItem.setOptionDataSource(gruppiRepertorioDS);
		repertorioItem.setWidth(200);
		repertorioItem.setClearable(true);
		repertorioItem.setCachePickListResults(false);
		repertorioItem.setAllowEmptyValue(true);
		repertorioItem.setAutoFetchData(false);
		
		nuovaRegistrazioneProtGeneraleForm.setFields(protocolloGeneraleItem, spacerItem, repertorioItem);		
	}
	
	/**
	 * Metodo che indica se è un Protocollo Generale con numerazione secondaria nel registro di Repertorio
	 * 
	 */
	public boolean isProtocolloGeneraleConRepertorio() {
		String tipoNumerazionePrincipale = vm.getValueAsString("tipoProtocollo") != null ? vm.getValueAsString("tipoProtocollo") : "";
		String tipoNumerazioneSecondaria = vm.getValueAsString("tipoNumerazioneSecondaria") != null	? vm.getValueAsString("tipoNumerazioneSecondaria") : "";
		return  !isRepertorioDetailEntrata() && !isRepertorioDetailInterno() && !isRepertorioDetailUscita() &&
			    tipoNumerazionePrincipale.equalsIgnoreCase("PG") &&
				tipoNumerazioneSecondaria.equalsIgnoreCase("R");
	}
	
	/**
	 * Metodo che indica se è un Repertorio
	 */
	public boolean isRepertorio() {
		return !isRepertorioDetailEntrata() || !isRepertorioDetailInterno() || !isRepertorioDetailUscita();
	}

	/**
	 * Metodo che restituisce il datasource relativo alla combo del tipo
	 * documento
	 * 
	 */
	protected GWTRestDataSource getTipoDocumentoDatasource() {
		GWTRestDataSource tipoDocumentoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento",
				FieldType.TEXT, true);
		tipoDocumentoDS.addParam("categoriaReg", getCategoriaAltraNumerazione());
		tipoDocumentoDS.addParam("siglaReg", getSiglaAltraNumerazione());
		return tipoDocumentoDS;
	}

	/**
	 * Metodo che indica se mostrare o meno la sezione "Tipologia documentale"
	 * 
	 */
	public boolean showDetailSectionTipoDocumento() {
		return !(isTaskDetail() || isProtocollazioneDetailAtti())
				&& AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI");
	}

	/**
	 * Metodo che crea la sezione "Tipologia documentale"
	 * 
	 */
	protected void createDetailSectionTipoDocumento() {

		createTipoDocumentoForm();

		detailSectionTipoDocumento = new ProtocollazioneHeaderDetailSection(
				I18NUtil.getMessages().protocollazione_detail_tipoDocumentoItem_title(), true, false, false,
				tipoDocumentoForm){

			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return getShowFirstCanvasWhenEmptyAfterOpen();
			}
		};
	}

	/**
	 * Metodo che crea il form della sezione "Tipologia documentale"
	 * 
	 */
	protected void createTipoDocumentoForm() {

		tipoDocumentoForm = new DynamicForm();
		tipoDocumentoForm.setValuesManager(vm);
		tipoDocumentoForm.setWidth100();
		tipoDocumentoForm.setPadding(5);
		tipoDocumentoForm.setWrapItemTitles(false);
		tipoDocumentoForm.setNumCols(10);
		tipoDocumentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		tipoDocumentoForm.setTabSet(tabSet);
		tipoDocumentoForm.setTabID("HEADER");

		tipoDocumentoItem = new FilteredSelectItem("tipoDocumento", I18NUtil.getMessages().protocollazione_detail_tipoDocumentoItem_title()) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				tipoDocumentoForm.setValue("nomeTipoDocumento", record.getAttribute("descTipoDocumento"));
				tipoDocumentoForm.setValue("codCategoriaAltraNumerazione", record.getAttribute("codCategoriaAltraNumerazione"));
				tipoDocumentoForm.setValue("siglaAltraNumerazione", record.getAttribute("siglaAltraNumerazione"));
				tipoDocumentoForm.setValue("flgTipoDocConVie", record.getAttributeAsBoolean("flgTipoDocConVie"));
				tipoDocumentoForm.setValue("flgOggettoNonObblig", record.getAttributeAsBoolean("flgOggettoNonObblig"));
				tipoDocumentoForm.setValue("flgTipoProtModulo", record.getAttribute("flgTipoProtModulo"));				
			}
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				tipoDocumentoForm.setValue("nomeTipoDocumento", "");
				tipoDocumentoForm.setValue("codCategoriaAltraNumerazione", "");
				tipoDocumentoForm.setValue("siglaAltraNumerazione", "");
				tipoDocumentoForm.clearValue("flgTipoDocConVie");
				tipoDocumentoForm.clearValue("flgOggettoNonObblig");
				tipoDocumentoForm.setValue("flgTipoProtModulo", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					tipoDocumentoForm.setValue("nomeTipoDocumento", "");
					tipoDocumentoForm.setValue("codCategoriaAltraNumerazione", "");
					tipoDocumentoForm.setValue("siglaAltraNumerazione", "");
					tipoDocumentoForm.clearValue("flgTipoDocConVie");
					tipoDocumentoForm.clearValue("flgOggettoNonObblig");
					tipoDocumentoForm.setValue("flgTipoProtModulo", "");
				}
            }
			
		};
		ListGridField descTipoDocumentoField = new ListGridField("descTipoDocumento", I18NUtil.getMessages().protocollazione_detail_tipoItem_title());
		tipoDocumentoItem.setPickListFields(descTipoDocumentoField);
		tipoDocumentoItem.setFilterLocally(true);
		tipoDocumentoItem.setValueField("idTipoDocumento");
		tipoDocumentoItem.setDisplayField("descTipoDocumento");
		tipoDocumentoItem.setOptionDataSource(getTipoDocumentoDatasource());
		tipoDocumentoItem.setWidth(300);
		tipoDocumentoItem.setClearable(true);
		tipoDocumentoItem.setShowIcons(true);
		tipoDocumentoItem.setShowTitle(false);		
		tipoDocumentoItem.setAutoFetchData(false);
		tipoDocumentoItem.setAlwaysFetchMissingValues(true);
		tipoDocumentoItem.setFetchMissingValues(true);
	
		tipoDocumentoSalvatoItem = new HiddenItem("tipoDocumentoSalvato");
		nomeTipoDocumentoItem = new HiddenItem("nomeTipoDocumento");
		codCategoriaAltraNumerazioneItem = new HiddenItem("codCategoriaAltraNumerazione");
		siglaAltraNumerazioneItem = new HiddenItem("siglaAltraNumerazione");
		
		tipoDocumentoForm.setFields(tipoDocumentoItem, tipoDocumentoSalvatoItem, nomeTipoDocumentoItem, codCategoriaAltraNumerazioneItem, siglaAltraNumerazioneItem);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Tipologie particolari" (per A2A)
	 * 
	 */
	public boolean showDetailSectionTipologieParticolariA2A() {
		String flgTipoProv = getFlgTipoProv();
		boolean isNewMode = mode != null && mode.equals("new");
		boolean show = flgTipoProv != null && "E".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A() && 
				(!isNewMode || (isNewMode && (Layout.isPrivilegioAttivo("SET_MULTA") || Layout.isPrivilegioAttivo("SET_CONTRATTO"))));
		return show;
	}

	/**
	 * Metodo che crea la sezione "Tipologie particolari"
	 * 
	 */
	protected void createDetailSectionTipologieParticolariA2A() {

		createTipologieParticolariA2AForm();

		detailSectionTipologieParticolariA2A = new ProtocollazioneHeaderDetailSection("Tipologie particolari", true, false, false, tipologieParticolariA2AForm) {

			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return getShowFirstCanvasWhenEmptyAfterOpen();
			}
		};
	}

	/**
	 * Metodo che crea il form della sezione "Tipologie particolari"
	 * 
	 */
	protected void createTipologieParticolariA2AForm() {

		tipologieParticolariA2AForm = new DynamicForm();
		tipologieParticolariA2AForm.setValuesManager(vm);
		tipologieParticolariA2AForm.setWidth100();
		tipologieParticolariA2AForm.setPadding(5);
		tipologieParticolariA2AForm.setWrapItemTitles(false);
		tipologieParticolariA2AForm.setNumCols(15);
		tipologieParticolariA2AForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		tipologieParticolariA2AForm.setTabSet(tabSet);
		tipologieParticolariA2AForm.setTabID("HEADER");
		
		flgMultaItem = new CheckboxItem("flgMulta", "multa") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				if(Layout.isPrivilegioAttivo("SET_MULTA")) {
					if(flgDocContrattiBarcodeItem.getValueAsBoolean() != null && flgDocContrattiBarcodeItem.getValueAsBoolean()) {
						super.setCanEdit(false);
					} else {
						super.setCanEdit(canEdit);
					}
				}else {
					super.setCanEdit(false);
				}
			};
		};
		flgMultaItem.setColSpan(1);
		flgMultaItem.setWidth(25);
		flgMultaItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(final ChangeEvent event) {
				boolean checked = event.getValue() != null && (Boolean)event.getValue();
				if(!checked) {
					showMessaggioConfermaToltaSpuntaMulta();				
				} else {
					// in realtà se lo riprendono risulta assegnato alla UO di protocollo quindi bisognerebbe fare che se l'assegnatario = UO protocollante non dai più il messaggio
					// per il momento commentiamo questo pezzo e facciamo in modo che sovrascriva le assegnazioni già effettuate
//					Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;		
//					boolean s = recordOrig != null && recordOrig.getAttributeAsBoolean("flgMulta");
//					RecordList listaAssegnazioniSalvate = recordOrig != null ? recordOrig.getAttributeAsRecordList("listaAssegnazioni") : null;
//					if(mode.equals("edit") && listaAssegnazioniSalvate != null && !listaAssegnazioniSalvate.isEmpty()) {
//						event.cancel();
//						AurigaLayout.addMessage(new MessageBean("Protocollo già assegnato alla funzione competente. L'apposizione del flag \"multa\" al protocollo sarà possibile solo previo annullamento dell'assegnazione da parte della struttura protocollante o restituzione del protocollo da parte della funzione erroneamente designata come competente.", "", MessageType.ERROR));
//					}
				}
			}
		});
		flgMultaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				boolean checked = event.getValue() != null && (Boolean) event.getValue();					
				if (checked) {		
					// se è una multa					
					Record assInviiCCRecord = new Record();
					assInviiCCRecord.setAttribute("dettaglioProtocollo", getRecordToSave());
					final GWTRestDataSource protocolloDS = new GWTRestDataSource("ProtocolloDataSource");
					protocolloDS.executecustom("getListaAssInviiCCMulta", assInviiCCRecord, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {								
								effettuaAssInviiCCMulta(response.getData()[0]);				
							}
						}
					});
					tipologieParticolariA2AForm.setValue("flgDocContratti", false);
					tipologieParticolariA2AForm.setValue("flgDocContrattiBarcode", false);
				}
				tipologieParticolariA2AForm.redraw();
				if(uoProtocollanteSelectItem != null) {
					boolean flgMulta = tipologieParticolariA2AForm != null && tipologieParticolariA2AForm.getValue("flgMulta") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgMulta");
					if(flgMulta) {						
						if (getSelezioneUoProtocollanteValueMap().size() == 1) {
							String uoProtocollante = getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
							if (getUoProtocollanteValueMap() != null && getUoProtocollanteValueMap().containsKey(uoProtocollante)) {
								uoProtocollanteSelectItem.setValue(uoProtocollante);
								if(uoProtocollanteSelectItem.getValue() == null || !uoProtocollanteSelectItem.getValue().equals(uoProtocollante)) {
									manageChangedUoProtocollante(uoProtocollante);
								}
							}
						}	
					} 
					uoProtocollanteSelectItem.setCanEdit(editing);					
				}
			}
		});
		flgMultaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((Layout.isPrivilegioAttivo("SET_MULTA") && mode.equals("new")) || !mode.equals("new"));
			}
		});
		
		statoTrasferimentoBloomfleetItem = new HiddenItem("statoTrasferimentoBloomfleet");
		dettaglioTrasferimentoBloomfleetItem = new HiddenItem("dettaglioTrasferimentoBloomfleet");
				
		ImgButtonItem iconaTrasferitoABloomfleetItem = new ImgButtonItem("iconaTrasferitoABloomfleet", "protocollazione/B_verde.png", "Trasferito a Bloomfleet");
		iconaTrasferitoABloomfleetItem.setAlwaysEnabled(true);
		iconaTrasferitoABloomfleetItem.setColSpan(1);
		iconaTrasferitoABloomfleetItem.setIconWidth(16);
		iconaTrasferitoABloomfleetItem.setIconHeight(16);
		iconaTrasferitoABloomfleetItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTrasferitoABloomfleetItem.setAlign(Alignment.LEFT);
		iconaTrasferitoABloomfleetItem.setWidth(16);
		iconaTrasferitoABloomfleetItem.setRedrawOnChange(true);
		iconaTrasferitoABloomfleetItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String dettaglioTrasferimentoBloomfleet = tipologieParticolariA2AForm.getValueAsString("dettaglioTrasferimentoBloomfleet");
				if(dettaglioTrasferimentoBloomfleet != null && !"".equals(dettaglioTrasferimentoBloomfleet)) {
					item.setPrompt("Trasferito a Bloomfleet: " + dettaglioTrasferimentoBloomfleet);
				}
				boolean flgMulta = tipologieParticolariA2AForm.getValue("flgMulta") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgMulta");
				if(flgMulta) {
					String statoTrasferimentoBloomfleet = tipologieParticolariA2AForm.getValueAsString("statoTrasferimentoBloomfleet");
					return statoTrasferimentoBloomfleet != null && "trasferito".equalsIgnoreCase(statoTrasferimentoBloomfleet);
				}
				return false;
			}
		});
		
		ImgButtonItem iconaFallitoTrasferimentoABloomfleetItem = new ImgButtonItem("iconaFallitoTrasferimentoABloomfleet", "protocollazione/B_rossa.png", "Fallito trasferimento a Bloomfleet");
		iconaFallitoTrasferimentoABloomfleetItem.setAlwaysEnabled(true);
		iconaFallitoTrasferimentoABloomfleetItem.setColSpan(1);
		iconaFallitoTrasferimentoABloomfleetItem.setIconWidth(16);
		iconaFallitoTrasferimentoABloomfleetItem.setIconHeight(16);
		iconaFallitoTrasferimentoABloomfleetItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaFallitoTrasferimentoABloomfleetItem.setAlign(Alignment.LEFT);
		iconaFallitoTrasferimentoABloomfleetItem.setWidth(16);
		iconaFallitoTrasferimentoABloomfleetItem.setRedrawOnChange(true);
		iconaFallitoTrasferimentoABloomfleetItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String dettaglioTrasferimentoBloomfleet = tipologieParticolariA2AForm.getValueAsString("dettaglioTrasferimentoBloomfleet");
				if(dettaglioTrasferimentoBloomfleet != null && !"".equals(dettaglioTrasferimentoBloomfleet)) {
					item.setPrompt("Fallito trasferimento a Bloomfleet: " + dettaglioTrasferimentoBloomfleet);
				}
				boolean flgMulta = tipologieParticolariA2AForm.getValue("flgMulta") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgMulta");
				if(flgMulta) {
					String statoTrasferimentoBloomfleet = tipologieParticolariA2AForm.getValueAsString("statoTrasferimentoBloomfleet");
					return statoTrasferimentoBloomfleet != null && "in_errore".equalsIgnoreCase(statoTrasferimentoBloomfleet);
				}
				return false; 
			}
		});
		
		ImgButtonItem iconaDaTrasferireABloomfleetItem = new ImgButtonItem("iconaDaTrasferireABloomfleet", "protocollazione/B_bianca.png", "Da trasferire a Bloomfleet");
		iconaDaTrasferireABloomfleetItem.setAlwaysEnabled(true);
		iconaDaTrasferireABloomfleetItem.setColSpan(1);
		iconaDaTrasferireABloomfleetItem.setIconWidth(16);
		iconaDaTrasferireABloomfleetItem.setIconHeight(16);
		iconaDaTrasferireABloomfleetItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaDaTrasferireABloomfleetItem.setAlign(Alignment.LEFT);
		iconaDaTrasferireABloomfleetItem.setWidth(16);
		iconaDaTrasferireABloomfleetItem.setRedrawOnChange(true);
		iconaDaTrasferireABloomfleetItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgMulta = tipologieParticolariA2AForm.getValue("flgMulta") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgMulta");
				if(flgMulta) {
					String statoTrasferimentoBloomfleet = tipologieParticolariA2AForm.getValueAsString("statoTrasferimentoBloomfleet");
					return statoTrasferimentoBloomfleet != null && "da_trasferire".equalsIgnoreCase(statoTrasferimentoBloomfleet);
				}
				return false; 
			}
		});
		
		SpacerItem spacerDocContrattiItem = new SpacerItem();
		spacerDocContrattiItem.setColSpan(1);
		spacerDocContrattiItem.setWidth(10);		
		
		flgDocContrattiItem = new CheckboxItem("flgDocContratti", "documentazione contratti"){
			@Override
			public void setCanEdit(Boolean canEdit) {
				if(Layout.isPrivilegioAttivo("SET_CONTRATTO")) {
					if(flgDocContrattiBarcodeItem.getValueAsBoolean() != null && flgDocContrattiBarcodeItem.getValueAsBoolean()) {
						super.setCanEdit(false);
					} else {
						super.setCanEdit(canEdit);
					}
				}else {
					super.setCanEdit(false);
				}
			};
		};
		flgDocContrattiItem.setColSpan(1);
		flgDocContrattiItem.setWidth(25);
		flgDocContrattiItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(final ChangeEvent event) {
				boolean checked = event.getValue() != null && (Boolean)event.getValue();
				if(checked) {	
					showMessaggioConfermaToltaSpuntaMulta();	
				}
			}
		});
		flgDocContrattiItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				boolean checked = event.getValue() != null && (Boolean) event.getValue();					
				if (checked) {							
					tipologieParticolariA2AForm.setValue("flgMulta", false);
					tipologieParticolariA2AForm.setValue("flgDocContrattiBarcode", false);
				} 
				tipologieParticolariA2AForm.redraw();				
			}
		});
		flgDocContrattiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((Layout.isPrivilegioAttivo("SET_CONTRATTO") && mode.equals("new")) || !mode.equals("new"));
			}
		});
		
		codContrattiItem = new TextItem("codContratti", "Codice/i contratto");
		codContrattiItem.setColSpan(1);
		codContrattiItem.setWidth(250);		
		codContrattiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				boolean flgDocContratti = tipologieParticolariA2AForm.getValue("flgDocContratti") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgDocContratti");
				boolean flgDocContrattiBarcode = tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode");
				return flgDocContratti && !flgDocContrattiBarcode;
			}
		});
		
		flgFirmeCompilateContrattiItem = new RadioGroupItem("flgFirmeCompilateContratti", "Firme compilate");
		flgFirmeCompilateContrattiItem.setVertical(false);
		flgFirmeCompilateContrattiItem.setValueMap("SI", "NO", "N.A.");
		flgFirmeCompilateContrattiItem.setDefaultValue("N.A.");
		flgFirmeCompilateContrattiItem.setColSpan(1);
		flgFirmeCompilateContrattiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {		
				boolean flgDocContratti = tipologieParticolariA2AForm.getValue("flgDocContratti") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgDocContratti");
				boolean flgDocContrattiBarcode = tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode");
				return flgDocContratti && !flgDocContrattiBarcode;
			}
		});
		
		SpacerItem spacerDocContrattiBarcodeItem = new SpacerItem();
		spacerDocContrattiBarcodeItem.setColSpan(1);
		spacerDocContrattiBarcodeItem.setWidth(10);		
		
		flgDocContrattiBarcodeItem = new CheckboxItem("flgDocContrattiBarcode", "documentazione contratti con barcode"){
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			};
		};
		flgDocContrattiBarcodeItem.setColSpan(1);
		flgDocContrattiBarcodeItem.setWidth(25);		
		flgDocContrattiBarcodeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode");
			}
		});
		
		tipologieParticolariA2AForm.setFields(flgMultaItem, statoTrasferimentoBloomfleetItem, dettaglioTrasferimentoBloomfleetItem, iconaTrasferitoABloomfleetItem, iconaFallitoTrasferimentoABloomfleetItem, iconaDaTrasferireABloomfleetItem, spacerDocContrattiItem, flgDocContrattiItem, codContrattiItem, flgFirmeCompilateContrattiItem, spacerDocContrattiBarcodeItem, flgDocContrattiBarcodeItem);
	}
	
	public void showMessaggioConfermaToltaSpuntaMulta() {		
		Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;		
		boolean isMulta = recordOrig != null && recordOrig.getAttributeAsBoolean("flgMulta");
		if(mode.equals("edit") && isMulta) {
			String statoTrasferimentoBloomfleet = tipologieParticolariA2AForm.getValueAsString("statoTrasferimentoBloomfleet");
			if(statoTrasferimentoBloomfleet != null && "trasferito".equalsIgnoreCase(statoTrasferimentoBloomfleet)) {
				AurigaLayout.addMessage(new MessageBean("ATTENZIONE: togliendo l'indicazione che si tratta di multa, al salvataggio il protocollo tornerà alla funzione che ha protocollato. Tuttavia poiché il documento è già stato trasferito all'applicativo di gestione multe tale trasferimento non può essere annullato.", "", MessageType.WARNING));
			} else {
				AurigaLayout.addMessage(new MessageBean("ATTENZIONE: togliendo l'indicazione che si tratta di multa, al salvataggio il protocollo tornerà da prendere in carico da parte della funzione assegnataria", "", MessageType.WARNING));
			}
		}
	}
	
	public void effettuaAssInviiCCMulta(Record lRecordAssInviiCCMulta) {
		if(lRecordAssInviiCCMulta != null) {			
			RecordList listaAssegnazioniMulta = lRecordAssInviiCCMulta.getAttributeAsRecordList("listaAssegnazioni");			
			if(listaAssegnazioniMulta != null && !listaAssegnazioniMulta.isEmpty()) {
				if (assegnazioneForm != null) {		
					Record lRecordAssegnazione = assegnazioneForm.getValuesAsRecord();						
					// se ci sono delle righe vanno SOSTITUITE con quelle derivanti dalle UO indicate nel parametro ASS_INVII_CC_MULTE
					RecordList listaAssegnazioni = lRecordAssegnazione.getAttributeAsRecordList("listaAssegnazioni");
					listaAssegnazioni = new RecordList();
					listaAssegnazioni.addList(listaAssegnazioniMulta.toArray());					
					lRecordAssegnazione.setAttribute("listaAssegnazioni", listaAssegnazioni);
					lRecordAssegnazione.setAttribute("listaAssegnazioniSalvate", new RecordList());
					assegnazioneForm.setValues(lRecordAssegnazione.toMap());
					if(assegnazioneItem != null) {	
						assegnazioneItem.resetCanvasChanged();
					}
					if(assegnazioneSalvataItem != null) {	
						assegnazioneSalvataItem.hide();
					}					
					if (detailSectionAssegnazione != null) {		
						detailSectionAssegnazione.open();	
					}
				}					
			}
			RecordList listaDestInvioCCMulta = lRecordAssInviiCCMulta.getAttributeAsRecordList("listaDestInvioCC");
			if(listaDestInvioCCMulta != null && !listaDestInvioCCMulta.isEmpty()) {
				if (condivisioneForm != null) {		
					Record lRecordCondivisione = condivisioneForm.getValuesAsRecord();					
					// se ci sono delle righe vanno SOSTITUITE con quelle derivanti dalle UO indicate nel parametro ASS_INVII_CC_MULTE
					RecordList listaDestInvioCC = lRecordCondivisione.getAttributeAsRecordList("listaDestInvioCC");
					listaDestInvioCC = new RecordList();
					listaDestInvioCC.addList(listaDestInvioCCMulta.toArray());
					lRecordCondivisione.setAttribute("listaDestInvioCC", listaDestInvioCC);
					lRecordCondivisione.setAttribute("listaDestInvioCCSalvati", new RecordList());
					condivisioneForm.setValues(lRecordCondivisione.toMap());
					if(condivisioneItem != null) {				
						condivisioneItem.resetCanvasChanged();
					}
					if(condivisioneSalvataItem != null) {	
						condivisioneSalvataItem.hide();
					}
					if (detailSectionCondivisione != null) {		
						detailSectionCondivisione.open();	
					}					
				}			
			}
		}
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Date arrivo al protocollo e spedizione" (per A2A)
	 * 
	 */
	public boolean showDetailSectionDataArrivoSpedizione() {				
		String flgTipoProv = getFlgTipoProv();
		return flgTipoProv != null && "U".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A();
	}

	/**
	 * Metodo che crea la sezione "Date arrivo al protocollo e spedizione"
	 * 
	 */
	protected void createDetailSectionDataArrivoSpedizione() {

		createDataArrivoSpedizioneForm();

		detailSectionDataArrivoSpedizione = new ProtocollazioneHeaderDetailSection("Date arrivo al protocollo e spedizione", true, true, true, dataArrivoSpedizioneForm) {

			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return getShowFirstCanvasWhenEmptyAfterOpen();
			}
		};
	}

	/**
	 * Metodo che crea il form della sezione "Date arrivo al protocollo e spedizione"
	 * 
	 */
	protected void createDataArrivoSpedizioneForm() {

		dataArrivoSpedizioneForm = new DynamicForm();
		dataArrivoSpedizioneForm.setValuesManager(vm);
		dataArrivoSpedizioneForm.setWidth100();
		dataArrivoSpedizioneForm.setPadding(5);
		dataArrivoSpedizioneForm.setWrapItemTitles(false);
		dataArrivoSpedizioneForm.setNumCols(5);
		dataArrivoSpedizioneForm.setColWidths(1, 1, 1, 1, "*");
		dataArrivoSpedizioneForm.setTabSet(tabSet);
		dataArrivoSpedizioneForm.setTabID("HEADER");
		
		dataArrivoProtocolloEditabileItem = new HiddenItem("dataArrivoProtocolloEditabile");
		
		dataArrivoProtocolloItem = new DateItem("dataArrivoProtocollo", "Data arrivo al protocollo") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				Record detailRecord = new Record(vm.getValues());					
				boolean dataArrivoProtocolloEditabile = dataArrivoSpedizioneForm.getValue("dataArrivoProtocolloEditabile") != null && (Boolean) dataArrivoSpedizioneForm.getValue("dataArrivoProtocolloEditabile");					
				super.setCanEdit(dataArrivoProtocolloEditabile ? canEdit : false);				
			}
		};
		dataArrivoProtocolloItem.setColSpan(1);
		dataArrivoProtocolloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record detailRecord = new Record(vm.getValues());
				return showDetailSectionDataArrivoSpedizione() && (isProtocollazioneBozza() || detailRecord.getAttribute("dataArrivoProtocollo") != null);
			}
		});
		dataArrivoProtocolloItem.setAttribute("obbligatorio", true);
		dataArrivoProtocolloItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Record detailRecord = new Record(vm.getValues());
				return showDetailSectionDataArrivoSpedizione() && (isProtocollazioneBozza() || detailRecord.getAttribute("dataArrivoProtocollo") != null);
			}
		}));
		
		dataSpedizioneCartaceoItem = new DateItem("dataSpedizioneCartaceo", "Data spedizione cartaceo");
		dataSpedizioneCartaceoItem.setColSpan(1);
		dataSpedizioneCartaceoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record detailRecord = new Record(vm.getValues());
				return showDetailSectionDataArrivoSpedizione() && (isProtocollazioneBozza() || isProtocollazioneDetailUscita() || detailRecord.getAttribute("dataSpedizioneCartaceo") != null);				
			}
		});
		
		dataArrivoSpedizioneForm.setItems(dataArrivoProtocolloEditabileItem, dataArrivoProtocolloItem, dataSpedizioneCartaceoItem);
	}

	/**
	 * Metodo che indica se mostrare già aperta la sezione "Mittenti"
	 * 
	 */
	public boolean showOpenDetailSectionMittenti() {
		return false;
	}

	/**
	 * Metodo che indica se la sezione "Mittenti" è obbligatoria
	 * 
	 */
	public boolean isRequiredDetailSectionMittenti() {
		return false;
	}
	
	/**
	 * Metodo che effettua la validazione della sezione "Mittenti"
	 * 
	 */
	public boolean validateDetailSectionMittenti() {
		return mittentiItem.validate();
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Mittenti"
	 * 
	 */
	public String getTitleDetailSectionMittenti() {
		return I18NUtil.getMessages().protocollazione_detail_mittentiForm_title();
	}

	/**
	 * Metodo che crea la sezione "Mittenti"
	 * 
	 */
	protected void createDetailSectionMittenti() {

		createMittentiForm();

		detailSectionMittenti = new ProtocollazioneDetailSection(getTitleDetailSectionMittenti(), true, showOpenDetailSectionMittenti(), 
				isRequiredDetailSectionMittenti(), mittentiForm) {
			
			@Override
			public boolean validate() {
				return validateDetailSectionMittenti();
			}
			
			@Override
			public boolean isRequired() {
				if (esibentiItem != null && esibentiItem.getFlgAncheMittente() != null){
					return !esibentiItem.getFlgAncheMittente();
				}else{
					return super.isRequired();
				}
			}
		};
		detailSectionMittenti.setViewReplicableItemHeight(450);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailSectionMittenti.setTabIndex(-1);
			detailSectionMittenti.setCanFocus(false);		
		}
	}
	
	/**
	 * Metodo che crea il form della sezione "Mittenti"
	 * 
	 */
	protected void createMittentiForm() {

		createMittentiItem();

		mittentiForm = new DynamicForm();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			mittentiForm.setTabIndex(-1);
			mittentiForm.setCanFocus(false);		
		}
		mittentiForm.setValuesManager(vm);
		mittentiForm.setWidth("100%");
		mittentiForm.setPadding(5);
		// mittentiForm.setMargin(0);
		// mittentiForm.setTop(0);
		mittentiForm.setTabSet(tabSet);
		mittentiForm.setTabID("HEADER");
		mittentiForm.setFields(mittentiItem);
	}
	
	/**
	 * Metodo che crea l'item dei mittenti
	 * 
	 */
	protected void createMittentiItem() {

		mittentiItem = new MittenteProtUscitaItem() {

			@Override
			public boolean getShowItemsIndirizzo() {
				return false;
			}
			
			@Override
			public boolean isAttivoAssegnatarioUnicoProt() {
				return isAttivoAssegnatarioUnico();
			}
			
			@Override
			public int getNroAssegnazioniProt() {
				return getNroAssegnazioni();
			}
			
			@Override
			public String getUoProtocollante() {
				return getUoProtocollanteSelezionata();
			}	
			
			@Override
			public void manageChangedFlgAssegnaAlMittente(Record canvasRecord) {
				manageChangedFlgAssegnaAlMittDest();				
			}
		};
		mittentiItem.setName("listaMittenti");
		mittentiItem.setShowTitle(false);
		mittentiItem.setShowNewButton(true);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			mittentiItem.setTabIndex(-1);
			mittentiItem.setCanFocus(false);		
		}
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Controinteressati"
	 * 
	 */
	public boolean showDetailSectionControinteressati() {
		return isRichiestaAccessoCivico();
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Controinteressati"
	 * 
	 */
	public boolean showOpenDetailSectionControinteressati() {
		return true;
	}

	/**
	 * Metodo che indica se la sezione "Controinteressati" è obbligatoria
	 * 
	 */
	public boolean isRequiredDetailSectionControinteressati() {
		return false;
	}
	
	/**
	 * Metodo che effettua la validazione della sezione "Controinteressati"
	 * 
	 */
	public boolean validateDetailSectionControinteressati() {
		boolean valid = controinteressatiForm1.validate();
		return valid && controinteressatiItem.validate();
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Controinteressati"
	 * 
	 */
	public String getTitleDetailSectionControinteressati() {
		return "Controinteressati";
	}

	/**
	 * Metodo che crea la sezione "Controinteressati"
	 * 
	 */
	protected void createDetailSectionControinteressati() {

		createControinteressatiForm();

		detailSectionControinteressati = new ProtocollazioneDetailSection(getTitleDetailSectionControinteressati(), true, showOpenDetailSectionControinteressati(), 
				isRequiredDetailSectionControinteressati(), controinteressatiForm1, controinteressatiForm2) {
			
			@Override
			public boolean validate() {
				return validateDetailSectionControinteressati();
			}
		};
		detailSectionControinteressati.setViewReplicableItemHeight(450);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailSectionControinteressati.setTabIndex(-1);
			detailSectionControinteressati.setCanFocus(false);		
		}
	}
	
	/**
	 * Metodo che crea il form della sezione "Controinteressati"
	 * 
	 */
	protected void createControinteressatiForm() {
		
		controinteressatiForm1 = new DynamicForm();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			controinteressatiForm1.setTabIndex(-1);
			controinteressatiForm1.setCanFocus(false);		
		}
		controinteressatiForm1.setValuesManager(vm);
		controinteressatiForm1.setWidth100();
		controinteressatiForm1.setPadding(5);
		controinteressatiForm1.setNumCols(14);
		controinteressatiForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		// controinteressatiForm1.setMargin(0);
		// controinteressatiForm1.setTop(0);
		controinteressatiForm1.setTabSet(tabSet);
		controinteressatiForm1.setTabID("HEADER");
		controinteressatiForm1.setHeight(1);

		flgPresentiControinteressatiItem = new RadioGroupItem("flgPresentiControinteressati", "Presenti");
		flgPresentiControinteressatiItem.setStartRow(true);
		flgPresentiControinteressatiItem.setValueMap("SI", "NO");		
		flgPresentiControinteressatiItem.setDefaultValue("NO");
		flgPresentiControinteressatiItem.setVertical(false);
		flgPresentiControinteressatiItem.setWrap(false);
		flgPresentiControinteressatiItem.setShowDisabled(false);		
		flgPresentiControinteressatiItem.setAttribute("obbligatorio", true);		
		flgPresentiControinteressatiItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showDetailSectionControinteressati();
			}
		}));
		flgPresentiControinteressatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionControinteressati();
			}
		});			
		flgPresentiControinteressatiItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				controinteressatiForm2.markForRedraw();
				if(controinteressatiForm1.getValueAsString("flgPresentiControinteressati") != null && "SI".equalsIgnoreCase(controinteressatiForm1.getValueAsString("flgPresentiControinteressati"))) {
					if (controinteressatiItem.getEditing() != null && controinteressatiItem.getEditing() && controinteressatiItem.getTotalMembers() == 0) {
						controinteressatiItem.onClickNewButton();						
					}					
				}
			}
		});
		
		controinteressatiForm1.setFields(flgPresentiControinteressatiItem);
		
		controinteressatiForm2 = new DynamicForm();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			controinteressatiForm2.setTabIndex(-1);
			controinteressatiForm2.setCanFocus(false);		
		}
		controinteressatiForm2.setValuesManager(vm);
		controinteressatiForm2.setWidth100();
		controinteressatiForm2.setPadding(5);
		// controinteressatiForm2.setMargin(0);
		// controinteressatiForm2.setTop(0);
		controinteressatiForm2.setTabSet(tabSet);
		controinteressatiForm2.setTabID("HEADER");
		controinteressatiForm2.setHeight(1);

		createControinteressatiItem();

		controinteressatiForm2.setFields(controinteressatiItem);
	}
	
	/**
	 * Metodo che crea l'item dei controinteressati
	 * 
	 */
	protected void createControinteressatiItem() {

		controinteressatiItem = new ControinteressatoItem();
		controinteressatiItem.setName("listaControinteressati");
		controinteressatiItem.setShowTitle(false);
		controinteressatiItem.setShowNewButton(true);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			controinteressatiItem.setTabIndex(-1);
			controinteressatiItem.setCanFocus(false);		
		}
		controinteressatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showDetailSectionControinteressati()) {
					return controinteressatiForm1.getValueAsString("flgPresentiControinteressati") != null && "SI".equalsIgnoreCase(controinteressatiForm1.getValueAsString("flgPresentiControinteressati"));				
				} 
				return false;
			}
		});
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Destinatari"
	 * 
	 */
	public boolean showDetailSectionDestinatari() {		
		return true;
	}	
	
	/**
	 * Metodo che restituisce il titolo della sezione "Destinatari"
	 * 
	 */
	public String getTitleDetailSectionDestinatari() {		
		return I18NUtil.getMessages().protocollazione_detail_destinatariForm_title();
	}	

	/**
	 * Metodo che indica se mostrare già aperta la sezione "Destinatari"
	 * 
	 */
	public boolean showOpenDetailSectionDestinatari() {
		return false;
	}

	/**
	 * Metodo che indica se la sezione "Destinatari" è obbligatoria
	 * 
	 */
	public boolean isRequiredDetailSectionDestinatari() {
		return false;
	}
	
	/**
	 * Metodo che effettua la validazione della sezione "Destinatari"
	 * 
	 */
	public boolean validateDetailSectionDestinatari() {
		if(destinatariItem != null) { 
			return destinatariItem.validate();
		}
		return true;
	}

	/**
	 * Metodo che crea la sezione "Destinatari"
	 * 
	 */
	protected void createDetailSectionDestinatari() {

		createDestinatariForm();

		detailSectionDestinatari = new ProtocollazioneDetailSection(
				getTitleDetailSectionDestinatari(), true,
				showOpenDetailSectionDestinatari(), isRequiredDetailSectionDestinatari(), destinatariForm) {
			
			@Override
			public boolean validate() {
				return validateDetailSectionDestinatari();
			}
		};
		detailSectionDestinatari.setViewReplicableItemHeight(450);
	}

	/**
	 * Metodo che crea il form della sezione "Destinatari"
	 * 
	 */
	protected void createDestinatariForm() {

		createDestinatariItem();

		destinatariForm = new DynamicForm();
		destinatariForm.setValuesManager(vm);
		destinatariForm.setWidth("100%");
		destinatariForm.setPadding(5);
		destinatariForm.setTabSet(tabSet);
		destinatariForm.setTabID("HEADER");
		destinatariForm.setFields(destinatariItem);
	}

	/**
	 * Metodo che crea l'item dei destinatari
	 * 
	 */
	protected void createDestinatariItem() {

		destinatariItem = new DestinatarioProtUscitaItem() {
			
			@Override
			public boolean isAttivoAssegnatarioUnicoProt() {
				return isAttivoAssegnatarioUnico();
			}
			
			@Override
			public int getNroAssegnazioniProt() {
				return getNroAssegnazioni();
			}
			
			@Override
			public void manageChangedFlgAssegnaAlDestinatario(Record canvasRecord) {
				manageChangedFlgAssegnaAlMittDest();				
			}
		};
		destinatariItem.setName("listaDestinatari");
		destinatariItem.setShowTitle(false);
		destinatariItem.setShowNewButton(true);
	}

	/**
	 * Metodo che indica se mostrare o meno il check "sostituisci alla ver.
	 * prec"
	 * 
	 */
	public boolean showFlgSostituisciVerPrecItem() {
		return false; // BOZZE
	}

	/**
	 * Metodo che indica se provengo da una mail in arrivo (protocollata in
	 * entrata)
	 * 
	 */
	public boolean isFromEmail() {
		return protocolloGeneraleForm.getValue("idEmailArrivo") != null
				&& !protocolloGeneraleForm.getValue("idEmailArrivo").equals("");
	}
	
	/**
	 * Metodo che indica se provengo da una mail in arrivo con casella PEC
	 * 
	 */
	public boolean isFromCasellaPEC() {
		return protocolloGeneraleForm.getValue("casellaIsPEC") != null
				&& protocolloGeneraleForm.getValue("casellaIsPEC").equals(true);
	}

	/**
	 * Metodo che indica se provengo da una mail interoperabile (protocollata in
	 * entrata)
	 * 
	 */
	public boolean isEmailInterop() {
		return emailArrivoInteroperabileHiddenItem.getValue() != null
				&& emailArrivoInteroperabileHiddenItem.getValue().equals(true);
	}

	/**
	 * Metodo che indica se mostrare già aperta la sezione "Contenuti"
	 * 
	 */
	public boolean showOpenDetailSectionContenuti() {
		return true;
	}

	/**
	 * Metodo che indica se la sezione "Contenuti" è obbligatoria
	 * 
	 */
	public boolean isRequiredDetailSectionContenuti() {
		return isRequiredOggetto() || isRequiredFilePrimario();
	}
	
	/**
	 * Metodo che effettua la validazione della sezione "Contenuti"
	 * 
	 */
	public boolean validateDetailSectionContenuti() {
		return contenutiForm.validate();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il form dell'oggetto nella sezione
	 * "Contenuti"
	 * 
	 */
	public boolean showContenutiForm() {
		return true;
	}

	/**
	 * Metodo che indica se mostrare o meno il form di riservatezza nella
	 * sezione "Contenuti"
	 * 
	 */
	public boolean showRiservatezzaForm() {
		return true;
	}	

	/**
	 * Metodo che indica se mostrare o meno il form del file primario nella
	 * sezione "Contenuti"
	 * 
	 */
	public boolean showFilePrimarioForm() {
		return true;
	}

	public boolean showNominativiFirmaOlografaForm() {
		String flgTipoProv = getFlgTipoProv();
		return flgTipoProv != null && "U".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il form della richiesta accesso civico
	 * nella sezione "Contenuti"
	 */
	public boolean showRichAccessoCivicoForm() {
		return isRichiestaAccessoCivico() ||
				(!isRepertorioDetailEntrata() && isProtocollazioneDetailEntrata() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_FLUSSO_RICH_ACCESSO_ATTI"));
	}

	/**
	 * Metodo che crea la sezione "Contenuti"
	 * 
	 */
	protected void createDetailSectionContenuti() {

		createContenutiForm();
		createRiservatezzaForm();
		createFilePrimarioForm();
		createNominativiFirmaOlografaForm();
		createRichAccessoCivicoForm();

		List<DynamicForm> forms = new ArrayList<DynamicForm>();
		if (showContenutiForm()) {
			forms.add(contenutiForm);
		}
		if (showRiservatezzaForm()) {
			forms.add(riservatezzaForm);
		}
		if (showFilePrimarioForm()) {
			forms.add(filePrimarioForm);
		}
		if (showNominativiFirmaOlografaForm()) {
			forms.add(nominativiFirmaOlografaForm);
		}
		if(showRichAccessoCivicoForm()) {
			forms.add(richAccessoCivicoForm);
		}

		detailSectionContenuti = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_contenutiForm_title(),
				true, showOpenDetailSectionContenuti(), isRequiredDetailSectionContenuti(), forms.toArray(new DynamicForm[forms.size()])) {
			
			@Override
			public boolean validate() {
				return validateDetailSectionContenuti();
			}
		};
	}
	
	/**
	 * Metodo che restituisce la label dell'oggetto
	 * 
	 */
	public String getTitleOggetto() {
		return I18NUtil.getMessages().protocollazione_detail_oggettoItem_title();
	}
	
	/**
	 * Metodo che indica se l'oggetto è obbligatorio o meno
	 * 
	 */
	public boolean isRequiredOggetto() {
		return true;
	}
	
	/**
	 * Metodo che crea il form dell'oggetto della sezione "Contenuti"
	 * 
	 */
	protected void createContenutiForm() {

		contenutiForm = new DynamicForm();
		contenutiForm.setValuesManager(vm);
		contenutiForm.setWidth100();
		contenutiForm.setPadding(5);
		contenutiForm.setNumCols(14);
		contenutiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		contenutiForm.setWrapItemTitles(false);
		contenutiForm.setTabSet(tabSet);
		contenutiForm.setTabID("HEADER");

		// Cod.rapido
		codRapidoOggettoItem = new ExtendedTextItem("codRapidoOggetto",
				I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoOggettoItem.setStartRow(true);
		codRapidoOggettoItem.setWidth(120);
		codRapidoOggettoItem.setColSpan(1);
		codRapidoOggettoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String value = codRapidoOggettoItem.getValueAsString();
				contenutiForm.clearErrors(true);
				if (value != null && !"".equals(value)) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
							"GetOggettoDaModelloDatasource");
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoOggetto", codRapidoOggettoItem.getValue());
					lRecord.setAttribute("flgTipoProv", getFlgTipoProv());
					lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							if (object.getAttribute("oggetto") != null	&& !object.getAttribute("oggetto").equalsIgnoreCase("")) {
								contenutiForm.setValue("oggetto", object.getAttribute("oggetto"));
							} else {
								contenutiForm.setFieldErrors("codRapidoOggetto", "Codice inesistente in oggettario o relativo ad oggetto non previsto per il tipo di registrazione in corso");
							}
							manageChangedOggetto();
						}
					});
				}
			}
		});

		// Oggetto
		oggettoItem = new ExtendedTextAreaItem("oggetto", getTitleOggetto());
		oggettoItem.setRequired(isRequiredOggetto());
		oggettoItem.setLength(4000);
		oggettoItem.setHeight(40);
		// oggettoItem.setColSpan(8);
		oggettoItem.setWidth(650);
		oggettoItem.setEndRow(false);
		oggettoItem.addChangedBlurHandler(new ChangedHandler() {
            
            @Override
            public void onChanged(ChangedEvent event) {
            	manageChangedOggetto();                
            }
        });
//		if(isProtocollazioneDetailAtti() && AurigaLayout.getParametroDBAsBoolean("UPPERCASE_OGGETTO_ATTO")) {
//			oggettoItem.setInputTransformer(new FormItemInputTransformer() {
//				
//				@Override
//				public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
//					return value != null ? ((String)value).toUpperCase() : null;
//				}
//			});		
//		}

		// bottone per accedere ai template
		lookupTemplateOggettoButton = new ImgButtonItem("lookupTemplateOggetto", "lookup/oggettario.png",
				I18NUtil.getMessages().protocollazione_detail_lookupTemplateOggettoButton_prompt());
		// lookupTemplateOggettoButton.setEndRow(true);
		lookupTemplateOggettoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				LookupOggettarioPopup lookupOggettarioPopup = new LookupOggettarioPopup(getFlgTipoProv()) {

					@Override
					public void manageLookupBack(Record record) {
						contenutiForm.clearErrors(true);
						contenutiForm.setValue("codRapidoOggetto", record.getAttribute("nome"));
						contenutiForm.setValue("oggetto", record.getAttribute("oggetto"));
						manageChangedOggetto();
					}

					@Override
					public void manageMultiLookupUndo(Record record) {

					}

					@Override
					public void manageMultiLookupBack(Record record) {

					}
				};
				lookupOggettarioPopup.show();
			}
		});

		// bottone per salvare l'oggetto
		salvaComeTemplateOggettoButton = new ImgButtonItem("salvaComeTemplateOggetto", "buttons/saveIn.png",
				I18NUtil.getMessages().protocollazione_detail_salvaComeTemplateOggettoButton_prompt());
		// salvaComeTemplateOggettoButton.setEndRow(true);
		salvaComeTemplateOggettoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				SalvaModelloPopup salvaModelloPopup = new SalvaModelloPopup(getFlgTipoProv(),
						codRapidoOggettoItem.getValueAsString(), oggettoItem.getValueAsString()) {

					@Override
					public void manageLookupBack(Record record) {
						contenutiForm.clearErrors(true);
						contenutiForm.setValue("codRapidoOggetto", record.getAttribute("nome"));
						contenutiForm.setValue("oggetto", record.getAttribute("oggetto"));
						manageChangedOggetto();
					}
				};
				salvaModelloPopup.show();
			}
		});
		salvaComeTemplateOggettoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return Layout.isPrivilegioAttivo("UT/MO/PR;I");
			}
		});

		contenutiForm.setFields(codRapidoOggettoItem, lookupTemplateOggettoButton, oggettoItem,
				salvaComeTemplateOggettoButton);
	}
	
	/**
	 * Metodo che crea il form di riservatezza della sezione "Contenuti"
	 * 
	 */
	protected void createRiservatezzaForm() {

		riservatezzaForm = new DynamicForm();
		riservatezzaForm.setValuesManager(vm);
		riservatezzaForm.setWidth100();
		riservatezzaForm.setPadding(5);
		riservatezzaForm.setNumCols(14);
		riservatezzaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		riservatezzaForm.setWrapItemTitles(false);
		riservatezzaForm.setTabSet(tabSet);
		riservatezzaForm.setTabID("HEADER");

		// Livello riservatezza
		GWTRestDataSource livelloRiservatezzaDS = new GWTRestDataSource("LoadComboLivelloRiservatezzaDataSource", "key", FieldType.TEXT);
		livelloRiservatezzaItem = new SelectItem("livelloRiservatezza", I18NUtil.getMessages().protocollazione_detail_livelloRiservatezzaItem_title());
		livelloRiservatezzaItem.setOptionDataSource(livelloRiservatezzaDS);
		livelloRiservatezzaItem.setAutoFetchData(false);
		livelloRiservatezzaItem.setAlwaysFetchMissingValues(true);
		livelloRiservatezzaItem.setFetchMissingValues(true);
		livelloRiservatezzaItem.setDisplayField("value");
		livelloRiservatezzaItem.setValueField("key");
		livelloRiservatezzaItem.setWidth(100);
		livelloRiservatezzaItem.setWrapTitle(false);
		livelloRiservatezzaItem.setColSpan(1);
		livelloRiservatezzaItem.setStartRow(true);
		livelloRiservatezzaItem.setAllowEmptyValue(true);
		ListGrid livelloRiservatezzaPickListProperties = livelloRiservatezzaItem.getPickListProperties();
		livelloRiservatezzaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				GWTRestDataSource livelloRiservatezzaDS = (GWTRestDataSource) livelloRiservatezzaItem.getOptionDataSource();
				livelloRiservatezzaDS.addParam("uoProtocollante", getUoProtocollanteSelezionata());
				livelloRiservatezzaItem.setOptionDataSource(livelloRiservatezzaDS);
				livelloRiservatezzaItem.invalidateDisplayValueCache();
			}
		});
		livelloRiservatezzaItem.setPickListProperties(livelloRiservatezzaPickListProperties);
		livelloRiservatezzaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				riservatezzaForm.redraw();
			}
		});
		
		livelloRiservatezzaItem.setRedrawOnChange(true);
		
		
		// Data riservatezza
		dataRiservatezzaItem = new DateItem("dataRiservatezza",
				I18NUtil.getMessages().protocollazione_detail_dataRiservatezzaItem_title());
		dataRiservatezzaItem.setWrapTitle(false);
		dataRiservatezzaItem.setColSpan(1);
		dataRiservatezzaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return livelloRiservatezzaItem.getValue() != null;
			}
		});

		// Priorita riservatezza
		GWTRestDataSource prioritaRiservatezzaDS = new GWTRestDataSource("LoadComboPrioritaRiservatezzaDataSource", "key", FieldType.TEXT);
		prioritaRiservatezzaItem = new SelectItem("prioritaRiservatezza",I18NUtil.getMessages().protocollazione_detail_prioritaRiservatezzaItem_title());
		prioritaRiservatezzaItem.setOptionDataSource(prioritaRiservatezzaDS);
		prioritaRiservatezzaItem.setAllowEmptyValue(true);
		prioritaRiservatezzaItem.setAutoFetchData(false);
		prioritaRiservatezzaItem.setAlwaysFetchMissingValues(true);
		prioritaRiservatezzaItem.setFetchMissingValues(true);
		prioritaRiservatezzaItem.setWidth(150);
		prioritaRiservatezzaItem.setDisplayField("value");
		prioritaRiservatezzaItem.setValueField("key");
				
		flgRispostaUrgenteItem = new CheckboxItem("flgRispostaUrgente", "risposta urgente");
		flgRispostaUrgenteItem.setValue(false);
		flgRispostaUrgenteItem.setColSpan(1);
		flgRispostaUrgenteItem.setWidth("*");
		flgRispostaUrgenteItem.setShowTitle(true);
		flgRispostaUrgenteItem.setWrapTitle(false);
		flgRispostaUrgenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String flgTipoProv = getFlgTipoProv();
				return flgTipoProv != null && "E".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A();
			}
		});
		
		riservatezzaForm.setFields(livelloRiservatezzaItem, dataRiservatezzaItem, prioritaRiservatezzaItem, flgRispostaUrgenteItem);
	}

	/**
	 * Metodo che indica se è obbligatorio o meno inserire il file primario per
	 * la registrazione
	 * 
	 */
	public boolean isRequiredFilePrimario() {
		return false;
	}
	
	/**
	 * Metodo che indica se è obbligatorio o meno il campo relativo al nome file primario nella sezione "Contenuti"
	 * 
	 */
	public boolean isRequiredNomeFilePrimarioItem() {
		return isRequiredFilePrimario();
	}
	
	public boolean isObbligatorioNoteXRegSenzaFilePrimario() {
		return AurigaLayout.getParametroDBAsBoolean("NOTE_OBBL_X_REG_SENZA_FILE_PRIMARIO") && showFilePrimarioForm() && !isRequiredFilePrimario();
	}
	
	/**
	 * Metodo che indica se il file primario è in un formato ammesso
	 * 
	 */
	public boolean isFormatoAmmessoFilePrimario(InfoFileRecord info) {
		return true;
	}

	/**
	 * Metodo che indica il messaggio di warning da mostrare quando il file primario è in un formato non ammesso
	 * 
	 */
	public String getFormatoNonAmmessoFilePrimarioWarning() {
		return "Il file primario risulta in un formato non ammesso";
	}
	
	/**
	 * Metodo che restituisce la label del nome file primario
	 * 
	 */
	public String getTitleNomeFilePrimario() {
		return I18NUtil.getMessages().protocollazione_detail_nomeFilePrimarioItem_title();
	}

	/**
	 * Metodo che restituisce la larghezza del campo nome file primario
	 * 
	 */
	public int getWidthNomeFilePrimario() {
		return 250;
	}
	
	/**
	 * Metodo che restituisce la label del check "dati sensibili" del file primario
	 * 
	 */
	public String getTitleFlgDatiSensibili() {
		String labelFlgVerOmissis = AurigaLayout.getParametroDB("LABEL_FLG_VER_OMISSIS");
		if(labelFlgVerOmissis != null && !"".equals(labelFlgVerOmissis)) {
			return labelFlgVerOmissis;
		}		
		return "dati sensibili";
	}

	/**
	 * Metodo che indica se mostrare o meno il check "escludi pubbl." del file
	 * primario
	 * 
	 */
	public boolean showFlgNoPubblPrimarioItem() {
		return false;
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone di upload del file
	 * primario (abilita/disabilita anche l'acquisizione da scanner)
	 * 
	 */
	public boolean showUploadFilePrimario() {
		return true;
	}
	
	/**
	 * Metodo che indica se mostrare o meno i check "originale cartaceo" e
	 * "copia sostitutiva" del file primario
	 * 
	 */
	public boolean showFlgOriginaleCartaceoECopiaSostitutivaPrimario() {
		return false;
	}
	
	public boolean isAttivaTimbraturaFilePostReg() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_FILE_POST_REG") && !isTaskDetail() && !isProtocollazioneDetailBozze();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "apponi timbro" del file primario
	 * 
	 */
	public boolean showFlgTimbraFilePostReg() {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if(isAttivaTimbraturaFilePostReg()) {
			boolean isEditing = mode != null && (mode.equals("new") || (mode.equals("edit"))) && showUploadFilePrimario();
			String flgTipoProv = getFlgTipoProv();			
			String uriFilePrimario = (String) filePrimarioForm.getValue("uriFilePrimario");
			InfoFileRecord infoFilePrimario = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
//			String mimetype = infoFilePrimario != null && infoFilePrimario.getMimetype() != null ? infoFilePrimario.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
				uriFilePrimario != null && !"".equals(uriFilePrimario) && infoFilePrimario != null && !infoFilePrimario.isFirmato() /*&& !mimetype.startsWith("image")*/) {
				String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
				boolean isChanged = filePrimarioForm.getValue("isDocPrimarioChanged") != null && (Boolean) filePrimarioForm.getValue("isDocPrimarioChanged");
				boolean flgTimbratoFilePostReg = filePrimarioForm.getValue("flgTimbratoFilePostReg") != null && (Boolean) filePrimarioForm.getValue("flgTimbratoFilePostReg");
				boolean isNewOrChanged = idDocPrimario == null || "".equals(idDocPrimario) || isChanged;
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChanged || !flgTimbratoFilePostReg) {  
					return true;
				}				
			}
		}
		// Nel caso in cui il check non è visibile il valore va settato sempre a false
		filePrimarioForm.setValue("flgTimbraFilePostReg",false);
		return false;		
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "dati sensibili" e la versione con omissis del file primario
	 * 
	 */
	public boolean showVersioneOmissis() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_VERS_CON_OMISSIS") && !showFlgNoPubblPrimarioItem();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il campo "Doc. presso Centro Posta"
	 * 
	 */
	public boolean showDocPressoCentroPostaItem() {
		return AurigaLayout.getParametroDB("DOC_PRESSO_CENTRO_POSTA") != null && !"".equals(AurigaLayout.getParametroDB("DOC_PRESSO_CENTRO_POSTA"));
	}
	
	/**
	 * Metodo che indica se è obbligatorio il campo "Doc. presso Centro Posta"
	 * 
	 */
	public boolean isRequiredDocPressoCentroPostaItem() {
		return showDocPressoCentroPostaItem() && "mandatory".equalsIgnoreCase(AurigaLayout.getParametroDB("DOC_PRESSO_CENTRO_POSTA"));
	}
	
	/**
	 * Metodo che crea il form del file primario della sezione "Contenuti"
	 * 
	 */
	protected void createFilePrimarioForm() {

		filePrimarioForm = new DynamicForm();
		filePrimarioForm.setValuesManager(vm);
		filePrimarioForm.setWidth100();
		filePrimarioForm.setPadding(5);
		filePrimarioForm.setNumCols(14);
		filePrimarioForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		filePrimarioForm.setWrapItemTitles(false);
		filePrimarioForm.setTabSet(tabSet);
		filePrimarioForm.setTabID("HEADER");

		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");
		idAttachEmailPrimarioItem = new HiddenItem("idAttachEmailPrimario");
		uriFilePrimarioItem = new HiddenItem("uriFilePrimario");
		tsInsLastVerFilePrimarioItem = new HiddenItem("tsInsLastVerFilePrimario");
		infoFileItem = new HiddenItem("infoFile");
		remoteUriFilePrimarioItem = new HiddenItem("remoteUriFilePrimario");
		isDocPrimarioChangedItem = new HiddenItem("isDocPrimarioChanged");
		isDocPrimarioChangedItem.setDefaultValue(false);
		nomeFilePrimarioOrigItem = new HiddenItem("nomeFilePrimarioOrig");
		nomeFilePrimarioTifItem = new HiddenItem("nomeFilePrimarioTif");
		uriFilePrimarioTifItem = new HiddenItem("uriFilePrimarioTif");		
		nomeFileVerPreFirmaItem = new HiddenItem("nomeFileVerPreFirma");
		uriFileVerPreFirmaItem = new HiddenItem("uriFileVerPreFirma");
		improntaVerPreFirmaItem = new HiddenItem("improntaVerPreFirma");
		infoFileVerPreFirmaItem = new HiddenItem("infoFileVerPreFirma");
		nroLastVerItem = new HiddenItem("nroLastVer");
		noteMancanzaFileItem = new HiddenItem("noteMancanzaFile");
		idUDScansioneItem = new HiddenItem("idUDScansione");
		
		// file primario
		nomeFilePrimarioItem = new ExtendedTextItem("nomeFilePrimario", getTitleNomeFilePrimario());
		nomeFilePrimarioItem.setStartRow(true);
		// nomeFilePrimarioItem.setColSpan(4);
		nomeFilePrimarioItem.setWidth(getWidthNomeFilePrimario());
		nomeFilePrimarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (nomeFilePrimarioItem.getValue() == null
						|| ((String) nomeFilePrimarioItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (filePrimarioForm.getValueAsString("nomeFilePrimarioOrig") != null
						&& !"".equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))
						&& !nomeFilePrimarioItem.getValue()
								.equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))) {
					manageChangedFilePrimario();
				}
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
			}
		});
		if(showFilePrimarioForm()) {
			
			List<CustomValidator> filePrimarioValidators = new ArrayList<CustomValidator>();
			
			if(AurigaLayout.getParametroDBAsBoolean("FILE_REG_IN_UNICA_SOLUZIONE") && !isProtocollazioneDetailBozze()) {
				CustomValidator lRequiredIfAllegatiPresenti = new CustomValidator() {
					
					@Override
					protected boolean condition(Object value) {
						boolean isValorizzatoPrimario = (value != null && !"".equals(value)) && uriFilePrimarioItem.getValue() != null
								&& !"".equals(uriFilePrimarioItem.getValue());
						RecordList listaAllegati = fileAllegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
						boolean isValorizzatoAllegati = listaAllegati != null && listaAllegati.get(0) != null && listaAllegati.get(0).getAttribute("uriFileAllegato") != null && !"".equals(listaAllegati.get(0).getAttributeAsString("uriFileAllegato"));
						if (!isValorizzatoPrimario && isValorizzatoAllegati) {
							return false;
						} 
						return true;
					}
				};
				lRequiredIfAllegatiPresenti.setErrorMessage("File primario obbligatorio se presenti allegati");
				filePrimarioValidators.add(lRequiredIfAllegatiPresenti);
			}

			if (isRequiredFilePrimario()) {
				nomeFilePrimarioItem.setAttribute("obbligatorio", true);
				CustomValidator lRequiredFilePrimarioValidator = new CustomValidator() {
	
					@Override
					protected boolean condition(Object value) {
						if(isRequiredNomeFilePrimarioItem()) {
							return (value != null && !"".equals(value)) && uriFilePrimarioItem.getValue() != null
									&& !"".equals(uriFilePrimarioItem.getValue());
						}
						return true;
					}
				};
				lRequiredFilePrimarioValidator.setErrorMessage("Campo obbligatorio");
				filePrimarioValidators.add(lRequiredFilePrimarioValidator);
			}
	
			CustomValidator lFormatoAmmessoFilePrimarioValidator = new CustomValidator() {
	
				@Override
				protected boolean condition(Object value) {
					InfoFileRecord infoFile = filePrimarioForm.getValue("infoFile") != null
							? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
					return isFormatoAmmessoFilePrimario(infoFile);
				}
			};
			lFormatoAmmessoFilePrimarioValidator.setErrorMessage("Formato non ammesso");
			filePrimarioValidators.add(lFormatoAmmessoFilePrimarioValidator);
			
			CustomValidator lRequiredA2A = new CustomValidator() {
				
				@Override
				protected boolean condition(Object value) {
					
					if(AurigaLayout.isAttivoClienteA2A() && (uriFilePrimarioItem.getValue() == null || "".equals(uriFilePrimarioItem.getValue()))) {
						//REPERTORIO/PROTOCOLLO INTERNO SENZA FILE PRIMARIO
						if(isRepertorioDetailInterno() || isProtocollazioneDetailInterna()) {
							return false;
						}
						//REPERTORIO/PROTOCOLLO IN USCITA SENZA FILE PRIMARIO SE SENZA UO ABILITATA ALLA SCANSIONE
						if((isRepertorioDetailUscita() || isProtocollazioneDetailUscita()) &&
								(!isUOAbilitatoScansioneMassiva())) {
							return false;
						}						
					}					
					return true;
				}
			};
			lRequiredA2A.setErrorMessage("Obbligatorio caricare il file primario");
			filePrimarioValidators.add(lRequiredA2A);
	
			nomeFilePrimarioItem.setValidators(filePrimarioValidators.toArray(new CustomValidator[filePrimarioValidators.size()]));
		}
		
		uploadFilePrimarioItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				filePrimarioForm.setValue("nomeFilePrimario", displayFileName);
				filePrimarioForm.setValue("uriFilePrimario", uri);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", displayFileName);
				filePrimarioForm.setValue("uriFileVerPreFirma", uri);
				changedEventAfterUpload(displayFileName, uri);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				uploadFilePrimarioItem.getCanvas().redraw();

			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);		
				String nomeFilePrimario = filePrimarioForm.getValueAsString("nomeFilePrimario");
				String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())
						|| (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && nomeFilePrimarioOrig != null
								&& !"".equals(nomeFilePrimarioOrig)
								&& !nomeFilePrimario.equals(nomeFilePrimarioOrig))) {
					manageChangedFilePrimario();
				}
				if (!isFormatoAmmessoFilePrimario(info)) {
					GWTRestDataSource.printMessage(new MessageBean(getFormatoNonAmmessoFilePrimarioWarning(),
							"", MessageType.ERROR));
					
					/*
					 * Visto l'errore dovuto al fatto che il file non è nel formato richiesto
					 * rimuovo le informazioni associate al file primario di cui si è tentato l'inserimento.
					 * In questo modo all'aggiornamento della grafica non viene riempita la text e non vengono
					 * abilitati i pulsanti di firma, etc. (normalmente abilitati nel caso di file in 
					 * formato corretto) 
					 */
					clickEliminaFile();
				}
				if (info.isFirmato() && !info.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean(
							"Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
			}
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			uploadFilePrimarioItem.setCanFocus(true);		
		}
		uploadFilePrimarioItem.setColSpan(1);
		uploadFilePrimarioItem.setAttribute("nascosto", !showUploadFilePrimario());
		uploadFilePrimarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				uploadFilePrimarioItem.setAttribute("nascosto", !showUploadFilePrimario());
				return showUploadFilePrimario();
			}
		});

		createFilePrimarioButtons();
		
		improntaItem = new TextItem("impronta", "Impronta") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		improntaItem.setWidth(350);
		improntaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				improntaItem.setCanEdit(false);
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					String impronta = lInfoFileRecord != null && lInfoFileRecord.getImpronta() != null ? lInfoFileRecord.getImpronta() : "";
					improntaItem.setValue(impronta);
					return impronta != null && !"".equals(impronta) && AurigaLayout.getParametroDBAsBoolean("SHOW_IMPRONTA_FILE");
				} else {
					improntaItem.setValue("");
					return false;
				}
			}
		});
		
		tipoBarcodePrimarioItem = new HiddenItem("tipoBarcodePrimario");
		barcodePrimarioItem = new HiddenItem("barcodePrimario");
		energiaGasPrimarioItem = new HiddenItem("energiaGasPrimario");
		tipoSezioneContrattoPrimarioItem = new HiddenItem("tipoSezioneContrattoPrimario");
		codContrattoPrimarioItem = new HiddenItem("codContrattoPrimario");
		flgPresentiFirmeContrattoPrimarioItem = new HiddenItem("flgPresentiFirmeContrattoPrimario");
		flgFirmeCompleteContrattoPrimarioItem = new HiddenItem("flgFirmeCompleteContrattoPrimario");
		nroFirmePrevisteContrattoPrimarioItem = new HiddenItem("nroFirmePrevisteContrattoPrimario");
		nroFirmeCompilateContrattoPrimarioItem = new HiddenItem("nroFirmeCompilateContrattoPrimario");
		flgIncertezzaRilevazioneFirmePrimarioItem = new HiddenItem("flgIncertezzaRilevazioneFirmePrimario");
		
		dettaglioDocContrattiBarcodeButton = new ImgButtonItem("dettaglioDocContrattiBarcodeButton", "buttons/altriDati.png",	"Dettaglio doc. contratti");
		dettaglioDocContrattiBarcodeButton.setAlwaysEnabled(true);
		dettaglioDocContrattiBarcodeButton.setColSpan(1);
		dettaglioDocContrattiBarcodeButton.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {			
				String flgTipoProv = getFlgTipoProv();			
				if(flgTipoProv != null && "E".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A()) {
					boolean flgDocContrattiBarcode = tipologieParticolariA2AForm != null && tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode") != null && (Boolean) tipologieParticolariA2AForm.getValue("flgDocContrattiBarcode");
					return flgDocContrattiBarcode;
				}
				return false;
			}
		});
		dettaglioDocContrattiBarcodeButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record detailRecord = new Record(vm.getValues());					
				Record lRecordDettDocContratti = new Record();
				lRecordDettDocContratti.setAttribute("tipoBarcode", detailRecord.getAttribute("tipoBarcodePrimario"));
				lRecordDettDocContratti.setAttribute("barcode", detailRecord.getAttribute("barcodePrimario"));
				lRecordDettDocContratti.setAttribute("energiaGas", detailRecord.getAttribute("energiaGasPrimario"));
				lRecordDettDocContratti.setAttribute("tipoSezioneContratto", detailRecord.getAttribute("tipoSezioneContrattoPrimario"));
				lRecordDettDocContratti.setAttribute("codContratto", detailRecord.getAttribute("codContrattoPrimario"));
				lRecordDettDocContratti.setAttribute("flgPresentiFirmeContratto", detailRecord.getAttribute("flgPresentiFirmeContrattoPrimario"));
				lRecordDettDocContratti.setAttribute("flgFirmeCompleteContratto", detailRecord.getAttribute("flgFirmeCompleteContrattoPrimario"));
				lRecordDettDocContratti.setAttribute("nroFirmePrevisteContratto", detailRecord.getAttribute("nroFirmePrevisteContrattoPrimario"));
				lRecordDettDocContratti.setAttribute("nroFirmeCompilateContratto", detailRecord.getAttribute("nroFirmeCompilateContrattoPrimario"));
				lRecordDettDocContratti.setAttribute("flgIncertezzaRilevazioneFirmeContratto", detailRecord.getAttribute("flgIncertezzaRilevazioneFirmePrimario"));
				DettaglioDocContrattiPopup lDettaglioDocContrattiPopup = new DettaglioDocContrattiPopup(lRecordDettDocContratti);
				lDettaglioDocContrattiPopup.show();
			}
		});
		
		flgSostituisciVerPrecItem = new CheckboxItem("flgSostituisciVerPrec", "sostituisci alla ver. prec");
		flgSostituisciVerPrecItem.setWidth("*");
		flgSostituisciVerPrecItem.setDefaultValue(false);
		flgSostituisciVerPrecItem.setVisible(false);

		flgOriginaleCartaceoItem = new CheckboxItem("flgOriginaleCartaceo", "originale cartaceo");
		flgOriginaleCartaceoItem.setValue(false);
		flgOriginaleCartaceoItem.setColSpan(1);
		flgOriginaleCartaceoItem.setWidth("*");
		// flgOriginaleCartaceoItem.setLabelAsTitle(true);
		flgOriginaleCartaceoItem.setShowTitle(true);
		flgOriginaleCartaceoItem.setWrapTitle(false);
		flgOriginaleCartaceoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgOriginaleCartaceoECopiaSostitutivaPrimario();
			}
		});
		flgOriginaleCartaceoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				filePrimarioForm.markForRedraw();
				manageChangedPrimario();
			}
		});

		flgCopiaSostitutivaItem = new CheckboxItem("flgCopiaSostitutiva", "copia sostitutiva");
		flgCopiaSostitutivaItem.setValue(false);
		flgCopiaSostitutivaItem.setColSpan(1);
		flgCopiaSostitutivaItem.setWidth("*");
		// flgCopiaSostitutivaItem.setLabelAsTitle(true);
		flgCopiaSostitutivaItem.setShowTitle(true);
		flgCopiaSostitutivaItem.setWrapTitle(false);
		flgCopiaSostitutivaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgOriginaleCartaceoECopiaSostitutivaPrimario() && (flgOriginaleCartaceoItem.getValue() != null
						&& (Boolean) flgOriginaleCartaceoItem.getValue());
			}
		});
		flgCopiaSostitutivaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageChangedPrimario();
			}
		});
				
		flgTimbratoFilePostRegItem = new HiddenItem("flgTimbratoFilePostReg");
		attivaTimbroTipologiaItem = new HiddenItem("attivaTimbroTipologia");		
		opzioniTimbroItem = new HiddenItem("opzioniTimbro");
		
		flgTimbraFilePostRegItem = new CheckboxItem("flgTimbraFilePostReg", I18NUtil.getMessages().protocollazione_flgTimbraFilePostReg());
		flgTimbraFilePostRegItem.setValue(false);
		flgTimbraFilePostRegItem.setColSpan(1);
		flgTimbraFilePostRegItem.setWidth("*");
		flgTimbraFilePostRegItem.setShowTitle(true);
		flgTimbraFilePostRegItem.setWrapTitle(false);
		flgTimbraFilePostRegItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgTimbraFilePostReg();
			}
		});
		flgTimbraFilePostRegItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					//Verifico se il file è timbrabile
					if (canBeTimbrato(lInfoFileRecord)) {
						if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")) {
							showOpzioniTimbratura();
						}
					} else {
						Layout.addMessage(new MessageBean("Il formato del file non ne consente la timbratura", "", MessageType.WARNING));
						flgTimbraFilePostRegItem.setValue(false);
					}
				}
			}
		});
		
		flgNoPubblPrimarioItem = new CheckboxItem("flgNoPubblPrimario", I18NUtil.getMessages().protocollazione_flg_no_pubbl());
		flgNoPubblPrimarioItem.setValue(false);
		flgNoPubblPrimarioItem.setColSpan(1);
		flgNoPubblPrimarioItem.setWidth("*");
		flgNoPubblPrimarioItem.setShowTitle(true);
		flgNoPubblPrimarioItem.setWrapTitle(false);
		flgNoPubblPrimarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoPubblPrimarioItem();
			}
		});
		flgNoPubblPrimarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				filePrimarioForm.markForRedraw();
			}
		});

		filePrimarioVerPubblItem = new AllegatiItem() {

			@Override
			public String getFlgTipoProvProtocollo() {
				return getFlgTipoProv();
			}
			
			@Override
			public boolean showUpload() {
				return showUploadFilePrimario();
			}

			@Override
			public boolean showAcquisisciDaScanner() {
				return showUploadFilePrimario();
			}

			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}

			@Override
			public boolean showGeneraDaModello() {
				return showGeneraDaModelloButton();
			}
		
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}

			@Override
			public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf,
					final ServiceCallback<Record> callback) {

				final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");
				lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
				lGeneraDaModelloDataSource.addParam("flgMostraDatiSensibili", "false");
				lGeneraDaModelloDataSource.executecustom("caricaModello",
						getRecordCaricaModello(idModello, tipoModello), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									if (callback != null) {
										callback.execute(response.getData()[0]);
									}
								}
							}
						});
			}

			@Override
			public String getFixedTipoAllegato() {
				return tipoDocumento;
			}

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showDescrizioneFileAllegato() {
				return false;
			}

			@Override
			public String getTitleNomeFileAllegato() {
				return FrontendUtil.getRequiredFormItemTitle("Vers. per pubbl.");
			}

			@Override
			public Integer getNomeFileWidth() {
				return 250;
			}

			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return true;
			}

			@Override
			public Boolean validate() {
				if (showFlgNoPubblPrimarioItem() && flgNoPubblPrimarioItem.getValueAsBoolean() != null
						&& flgNoPubblPrimarioItem.getValueAsBoolean()) {
					return super.validate();
				}
				return true;
			}
			
			@Override
			public Boolean valuesAreValid() {
				if (showFlgNoPubblPrimarioItem() && flgNoPubblPrimarioItem.getValueAsBoolean() != null
						&& flgNoPubblPrimarioItem.getValueAsBoolean()) {
					return super.valuesAreValid();
				}
				return true;				
			}
			
			@Override
			public String getIdUd() {
				return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
			}
			
			@Override
			public boolean isShowModalPreview() {
				return isEnablePreviewModal();
			}
		};
		filePrimarioVerPubblItem.setName("listaFilePrimarioVerPubbl");
		filePrimarioVerPubblItem.setStartRow(false);
		filePrimarioVerPubblItem.setColSpan(5);
		filePrimarioVerPubblItem.setShowFlgParteDispositivo(false);
		filePrimarioVerPubblItem.setShowFlgNoPubblAllegato(false);
		filePrimarioVerPubblItem.setShowTitle(false);
		filePrimarioVerPubblItem.setNotReplicable(true);
		filePrimarioVerPubblItem.setAttribute("obbligatorio", true);
		filePrimarioVerPubblItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNoPubblPrimarioItem() && flgNoPubblPrimarioItem.getValueAsBoolean() != null
						&& flgNoPubblPrimarioItem.getValueAsBoolean();
			}
		});
		
		flgDatiSensibiliItem = new CheckboxItem("flgDatiSensibili", getTitleFlgDatiSensibili() + "&nbsp;&nbsp;&nbsp;");
		flgDatiSensibiliItem.setValue(false);
		flgDatiSensibiliItem.setColSpan(1);
		flgDatiSensibiliItem.setWidth("*");
		// flgDatiSensibiliItem.setLabelAsTitle(true);
		flgDatiSensibiliItem.setShowTitle(true);
		flgDatiSensibiliItem.setWrapTitle(false);
		flgDatiSensibiliItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showVersioneOmissis();
			}
		});
		flgDatiSensibiliItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				filePrimarioForm.markForRedraw();
			}
		});

		filePrimarioOmissisItem = new DocumentItem() {
			
			@Override
			public String getTipoDocumento() {
				return tipoDocumento;
			}
			
			@Override
			public String getFlgTipoProvProtocollo() {
				return getFlgTipoProv();
			}
			
			@Override
			public String getIdProcGeneraDaModello() {
				return null; // qui non lo devo passare, come nel generaDaModello del file primario
			}
			
			@Override
			public Date getDataRifValiditaFirma() {
				// come per il file primario
				if(!isProtocollazioneDetailAtti() && !isTaskDetail()) {			
					if (dataProtocolloItem.getValueAsDate() != null) {
						return dataProtocolloItem.getValueAsDate();					
					}
				}
				return null;
			}
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showUploadItem() {
				return instance.showUploadFilePrimario();
			}

			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return instance.showUploadFilePrimario();
			}

			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}

			@Override
			public boolean showGeneraDaModelloButton() {
				return instance.showGeneraDaModelloButton();
			}
			
			@Override
			public boolean hideTimbraMenuItems() {
				return false;
			}
			
			@Override
			public boolean showFlgSostituisciVerPrecItem() {
				return instance.showFlgSostituisciVerPrecItem();
			}
			
			@Override
			public boolean isAttivaTimbraturaFilePostReg() {
				return instance.isAttivaTimbraturaFilePostReg();
			}

			@Override
			public Boolean validate() {
				if (showVersioneOmissis() && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					return super.validate();
				}
				return true;
			}
			
			@Override
			public Boolean valuesAreValid() {
				if (showVersioneOmissis() && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					return super.valuesAreValid();
				}
				return true;				
			}
			
			@Override
			public void manageOnChangedDocument() {
				instance.manageChangedContenuti();
			}

			@Override
			public void manageOnChangedDocumentFile() {
				instance.manageChangedFilePrimarioOmissis();
			}
			
			@Override
			public Record getRecordCaricaModello(String idModello, String tipoModello) {
				return instance.getRecordCaricaModello(idModello, tipoModello);
			}
			
			@Override
			public GWTRestDataSource getGeneraDaModelloDataSource(String idModello, String tipoModello, String flgConvertiInPdf) {
				final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");				
				lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
				lGeneraDaModelloDataSource.addParam("flgMostraDatiSensibili", "false");
				return lGeneraDaModelloDataSource;
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				return instance.isFormatoAmmessoFilePrimario(info);
			}
		
			@Override
			public boolean enableAcquisisciDaScannerMenu() {
				return enableAcquisisciDaScannerMenuItem();
			}
			
			@Override
			public boolean showTimbraBarcodeMenuItems() {
				return true;
			}
			
			@Override
			public boolean isAttivaTimbroTipologia() {
				return isAttivaTimbroTipologiaProtocollazione();
			}
			
			@Override
			public boolean getEditingProtocollo() {
				return mode != null && (mode.equals("new") || (mode.equals("edit")));
			}
			
			@Override
			protected boolean showAzioniTimbratura() {
				return showOperazioniTimbratura();
			}
		};
		filePrimarioOmissisItem.setStartRow(false);
		filePrimarioOmissisItem.setName("filePrimarioOmissis");
		filePrimarioOmissisItem.setTitle(I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title());
		filePrimarioOmissisItem.setShowTitle(true);
		filePrimarioOmissisItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (showVersioneOmissis() && flgDatiSensibiliItem.getValueAsBoolean() != null && flgDatiSensibiliItem.getValueAsBoolean()) {
					filePrimarioOmissisItem.setAttribute("obbligatorio", true);
					filePrimarioOmissisItem.setTitle(FrontendUtil.getRequiredFormItemTitle((I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title())));
					filePrimarioOmissisItem.setRequired(true);
					return true;
				} else {
					filePrimarioOmissisItem.setAttribute("obbligatorio", false);
					filePrimarioOmissisItem.setTitle((I18NUtil.getMessages().protocollazione_detail_nomeFileOmissisAllegatoItem_title()));
					filePrimarioOmissisItem.setRequired(false);
					return false;				
				}
			}
		});
		
		SpacerItem spacerDocPressoCentroPostaItem = new SpacerItem();
		spacerDocPressoCentroPostaItem.setColSpan(1);
		spacerDocPressoCentroPostaItem.setWidth(10);
		spacerDocPressoCentroPostaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDocPressoCentroPostaItem();
			}
		});
		
		docPressoCentroPostaItem = new SelectItem("docPressoCentroPosta", "Doc. presso Centro Posta");
		LinkedHashMap<String, String> lMapDocPressoCentroPosta = new LinkedHashMap<String, String>();
		lMapDocPressoCentroPosta.put("SI", "Si");
		lMapDocPressoCentroPosta.put("NO", "No");
		docPressoCentroPostaItem.setValueMap(lMapDocPressoCentroPosta);
		docPressoCentroPostaItem.setWidth(120);
		docPressoCentroPostaItem.setWrapTitle(false);
		docPressoCentroPostaItem.setAllowEmptyValue(true);	
		if(isRequiredDocPressoCentroPostaItem()) {
			docPressoCentroPostaItem.setRequired(true);
		}		
		docPressoCentroPostaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDocPressoCentroPostaItem();
			}
		});
		
		List<FormItem> filePrimarioFields = new ArrayList<FormItem>();
		filePrimarioFields.add(idDocPrimarioHiddenItem);
		filePrimarioFields.add(idAttachEmailPrimarioItem);
		filePrimarioFields.add(uriFilePrimarioItem);
		filePrimarioFields.add(tsInsLastVerFilePrimarioItem);
		filePrimarioFields.add(infoFileItem);
		filePrimarioFields.add(remoteUriFilePrimarioItem);
		filePrimarioFields.add(isDocPrimarioChangedItem);
		filePrimarioFields.add(nomeFilePrimarioOrigItem);
		filePrimarioFields.add(nomeFilePrimarioTifItem);
		filePrimarioFields.add(uriFilePrimarioTifItem);
		filePrimarioFields.add(nomeFileVerPreFirmaItem);
		filePrimarioFields.add(uriFileVerPreFirmaItem);
		filePrimarioFields.add(improntaVerPreFirmaItem);
		filePrimarioFields.add(infoFileVerPreFirmaItem);
		filePrimarioFields.add(nroLastVerItem);
		filePrimarioFields.add(noteMancanzaFileItem);		
		filePrimarioFields.add(nomeFilePrimarioItem);
		filePrimarioFields.add(uploadFilePrimarioItem);
		filePrimarioFields.add(filePrimarioButtons);
		filePrimarioFields.add(improntaItem);
		filePrimarioFields.add(tipoBarcodePrimarioItem);
		filePrimarioFields.add(barcodePrimarioItem);
		filePrimarioFields.add(energiaGasPrimarioItem);
		filePrimarioFields.add(tipoSezioneContrattoPrimarioItem);
		filePrimarioFields.add(codContrattoPrimarioItem);
		filePrimarioFields.add(flgPresentiFirmeContrattoPrimarioItem);
		filePrimarioFields.add(flgFirmeCompleteContrattoPrimarioItem);
		filePrimarioFields.add(nroFirmePrevisteContrattoPrimarioItem);
		filePrimarioFields.add(nroFirmeCompilateContrattoPrimarioItem);
		filePrimarioFields.add(flgIncertezzaRilevazioneFirmePrimarioItem);
		filePrimarioFields.add(dettaglioDocContrattiBarcodeButton);
		filePrimarioFields.add(flgSostituisciVerPrecItem);
		filePrimarioFields.add(flgOriginaleCartaceoItem);
		filePrimarioFields.add(flgCopiaSostitutivaItem);
		filePrimarioFields.add(flgTimbratoFilePostRegItem);
		filePrimarioFields.add(attivaTimbroTipologiaItem);
		filePrimarioFields.add(opzioniTimbroItem);		
		filePrimarioFields.add(flgTimbraFilePostRegItem);
		filePrimarioFields.add(flgNoPubblPrimarioItem);
		filePrimarioFields.add(filePrimarioVerPubblItem);
		filePrimarioFields.add(flgDatiSensibiliItem);
		filePrimarioFields.add(filePrimarioOmissisItem);
		filePrimarioFields.add(spacerDocPressoCentroPostaItem);
		filePrimarioFields.add(docPressoCentroPostaItem);
		filePrimarioFields.add(idUDScansioneItem);

		filePrimarioForm.setFields(filePrimarioFields.toArray(new FormItem[filePrimarioFields.size()]));
	}


	/**
	 * Metodo che crea il form dei firmatari (firma oleografa) nella sezione "Contenuti"
	 */
	protected void createNominativiFirmaOlografaForm() {
		
		nominativiFirmaOlografaForm = new DynamicForm();
		nominativiFirmaOlografaForm.setValuesManager(vm);
		nominativiFirmaOlografaForm.setWidth100();
		nominativiFirmaOlografaForm.setPadding(5);
		nominativiFirmaOlografaForm.setNumCols(4);
		nominativiFirmaOlografaForm.setColWidths(1, 1, 1, "*");
		nominativiFirmaOlografaForm.setWrapItemTitles(false);
		nominativiFirmaOlografaForm.setTabSet(tabSet);
		nominativiFirmaOlografaForm.setTabID("HEADER");
		
		nominativiFirmaOlografaItem = new NominativiFirmaOlografaItem() {
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">Firmatario/i</span>");
				return lVLayout;
			}
		};
		nominativiFirmaOlografaItem.setName("listaNominativiFirmaOlografa");
		nominativiFirmaOlografaItem.setShowTitle(false);
		nominativiFirmaOlografaItem.setColSpan(4);
		
		nominativiFirmaOlografaForm.setItems(nominativiFirmaOlografaItem);
	}
	
	/**
	 * Metodo che crea il form della richiesta accesso civico nella sezione "Contenuti"
	 */
	protected void createRichAccessoCivicoForm() {
		
		richAccessoCivicoForm = new DynamicForm();
		richAccessoCivicoForm.setValuesManager(vm);
		richAccessoCivicoForm.setWidth100();
		richAccessoCivicoForm.setPadding(5);
		richAccessoCivicoForm.setNumCols(4);
		richAccessoCivicoForm.setColWidths(1, 1, 1, "*");
		richAccessoCivicoForm.setWrapItemTitles(false);
		richAccessoCivicoForm.setTabSet(tabSet);
		richAccessoCivicoForm.setTabID("HEADER");
		
		TitleItem richAccCivicoTitleItem = new TitleItem(I18NUtil.getMessages().protocollazione_detail_richAccCivicoTitle());
		
		flgRichAccCivSempliceItem = new CheckboxItem("flgRichAccCivSemplice", I18NUtil.getMessages().protocollazione_detail_flgRichAccCivSemplice_title()) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				if(isRichiestaAccessoCivico()) {
					super.setCanEdit(false);
				} else {
					super.setCanEdit(canEdit);
				}
			};
		};
		flgRichAccCivSempliceItem.setColSpan(1);
		flgRichAccCivSempliceItem.setWidth(25);
		flgRichAccCivSempliceItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				boolean checked = event.getValue() != null && (Boolean) event.getValue();									
				if (checked) {	
					manageOnChangedFlgRichAccCivSemplice();
				}
				richAccessoCivicoForm.redraw();								
			}
		});
		
		flgRichAccCivGeneralizzatoItem = new CheckboxItem("flgRichAccCivGeneralizzato", I18NUtil.getMessages().protocollazione_detail_flgRichAccCivGeneralizzato_title()) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				if(isRichiestaAccessoCivico()) {
					super.setCanEdit(false);
				} else {
					super.setCanEdit(canEdit);
				}
			};
		};
		flgRichAccCivGeneralizzatoItem.setColSpan(1);
		flgRichAccCivGeneralizzatoItem.setWidth(25);
		flgRichAccCivGeneralizzatoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				boolean checked = event.getValue() != null && (Boolean) event.getValue();					
				if (checked) {	
					manageOnChangedFlgRichAccCivGeneralizzato();
				}
				richAccessoCivicoForm.redraw();				
			}
		});
		
		richAccessoCivicoForm.setFields(richAccCivicoTitleItem, flgRichAccCivSempliceItem, flgRichAccCivGeneralizzatoItem);
	}
	
	protected void manageOnChangedFlgRichAccCivSemplice() {
		// se è una rich. accesso civico semplice
		Record assInviiCCRecord = new Record();
		assInviiCCRecord.setAttribute("dettaglioProtocollo", getRecordToSave());
		final GWTRestDataSource protocolloDS = new GWTRestDataSource("ProtocolloDataSource");
		protocolloDS.extraparam.put("richiestaAccesso", "S");		
		protocolloDS.extraparam.put("mode", editMode);
		protocolloDS.executecustom("getListaAssInviiCCAccessoCivico", assInviiCCRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					effettuaAssInviiCCAccessoCivico("S", response.getData()[0]);								
				}
			}
		});		
		richAccessoCivicoForm.setValue("flgRichAccCivGeneralizzato", false);
	}
	
	protected void manageOnChangedFlgRichAccCivGeneralizzato() {
		// se è una rich. accesso civico generalizzato
		Record assInviiCCRecord = new Record();
		assInviiCCRecord.setAttribute("dettaglioProtocollo", getRecordToSave());
		final GWTRestDataSource protocolloDS = new GWTRestDataSource("ProtocolloDataSource");
		protocolloDS.extraparam.put("richiestaAccesso", "G");		
		protocolloDS.extraparam.put("mode", editMode);
		protocolloDS.executecustom("getListaAssInviiCCAccessoCivico", assInviiCCRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {								
					effettuaAssInviiCCAccessoCivico("G", response.getData()[0]);				
				}
			}
		});
		richAccessoCivicoForm.setValue("flgRichAccCivSemplice", false);
	}
	
	public void effettuaAssInviiCCAccessoCivico(String richiestaAccesso, Record lRecordAssInviiCCAccessoCivico) {
		if(lRecordAssInviiCCAccessoCivico != null) {			
			if("modificaDati".equalsIgnoreCase(editMode)) {
				RecordList listaAssegnazioniAccessoCivico = lRecordAssInviiCCAccessoCivico.getAttributeAsRecordList("listaAssegnazioni");
				if(listaAssegnazioniAccessoCivico != null && !listaAssegnazioniAccessoCivico.isEmpty()) {
					if (assegnazioneForm != null) {		
						Record lRecordAssegnazione = assegnazioneForm.getValuesAsRecord();							
						RecordList listaAssegnazioni = lRecordAssegnazione.getAttributeAsRecordList("listaAssegnazioni");
						if (listaAssegnazioni == null || listaAssegnazioni.getLength() == 0) {
							listaAssegnazioni = new RecordList();
						}			
						listaAssegnazioni.addList(listaAssegnazioniAccessoCivico.toArray());
						lRecordAssegnazione.setAttribute("listaAssegnazioni", listaAssegnazioni);
						assegnazioneForm.setValues(lRecordAssegnazione.toMap());
						if(assegnazioneItem != null) {	
							assegnazioneItem.resetCanvasChanged();
						}
						if (detailSectionAssegnazione != null) {		
							detailSectionAssegnazione.open();	
						}
					}	
				}
				RecordList listaDestInvioCCAccessoCivico = lRecordAssInviiCCAccessoCivico.getAttributeAsRecordList("listaDestInvioCC");
				if(listaDestInvioCCAccessoCivico != null && !listaDestInvioCCAccessoCivico.isEmpty()) {
					if (condivisioneForm != null) {		
						Record lRecordCondivisione = condivisioneForm.getValuesAsRecord();								
						RecordList listaDestInvioCC = lRecordCondivisione.getAttributeAsRecordList("listaDestInvioCC");
						if (listaDestInvioCC == null || listaDestInvioCC.getLength() == 0) {
							listaDestInvioCC = new RecordList();
						}			
						listaDestInvioCC.addList(listaDestInvioCCAccessoCivico.toArray());		
						lRecordCondivisione.setAttribute("listaDestInvioCC", listaDestInvioCC);
						condivisioneForm.setValues(lRecordCondivisione.toMap());
						if(condivisioneItem != null) {				
							condivisioneItem.resetCanvasChanged();
						}
						if (detailSectionCondivisione != null) {		
							detailSectionCondivisione.open();	
						}
					}			
				}
			} else {		
				RecordList listaDestAssAccessoCivico = lRecordAssInviiCCAccessoCivico.getAttributeAsRecordList("listaDestinatariAssegnazioni");
				if(listaDestAssAccessoCivico != null && !listaDestAssAccessoCivico.isEmpty()) {		
					if (destinatariForm != null) {		
						Record lRecordDestinatarioAss = destinatariForm.getValuesAsRecord();											
						RecordList listaDestinatari = lRecordDestinatarioAss.getAttributeAsRecordList("listaDestinatari");
						if (listaDestinatari == null || listaDestinatari.getLength() == 0) {
							listaDestinatari = new RecordList();
						}
						if(richiestaAccesso != null && "S".equalsIgnoreCase(richiestaAccesso) && !AurigaLayout.getParametroDBAsBoolean("ASS_INVII_CC_ACCESSO_SEMPLICE_APPEND")) {
							listaDestinatari = new RecordList();							
						} 
						if(richiestaAccesso != null && "G".equalsIgnoreCase(richiestaAccesso) && !AurigaLayout.getParametroDBAsBoolean("ASS_INVII_CC_ACCESSO_GENERALIZZATO_APPEND")) {
							listaDestinatari = new RecordList();							
						}
						RecordList listaDestinatariNew = new RecordList();
						for(int i = 0; i < listaDestinatari.getLength(); i++) {
							// escludiamo tutte le righe preimpostate con i check di accesso civico
							if(!listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatarioReadOnly") && !listaDestinatari.get(i).getAttributeAsBoolean("flgPCReadOnly")) {
								listaDestinatariNew.add(listaDestinatari.get(i));
							}							
						}
						listaDestinatariNew.addList(listaDestAssAccessoCivico.toArray());
						lRecordDestinatarioAss.setAttribute("listaDestinatari", listaDestinatariNew);
						destinatariForm.setValues(lRecordDestinatarioAss.toMap());
						if(destinatariItem != null) {	
							destinatariItem.resetCanvasChanged();
						}
						if (detailSectionDestinatari != null) {		
							detailSectionDestinatari.open();	
						}
					}	
				}
				RecordList listaDestInvioCCAccessoCivico = lRecordAssInviiCCAccessoCivico.getAttributeAsRecordList("listaDestinatariInviiCC");
				if(listaDestInvioCCAccessoCivico != null && !listaDestInvioCCAccessoCivico.isEmpty()) {
					if (destinatariForm != null) {		
						Record lRecordDestinatarioInvioCC = destinatariForm.getValuesAsRecord();											
						RecordList listaDestinatari = lRecordDestinatarioInvioCC.getAttributeAsRecordList("listaDestinatari");
						if (listaDestinatari == null || listaDestinatari.getLength() == 0) {
							listaDestinatari = new RecordList();
						}						
						if(richiestaAccesso != null && "S".equalsIgnoreCase(richiestaAccesso) && !AurigaLayout.getParametroDBAsBoolean("ASS_INVII_CC_ACCESSO_SEMPLICE_APPEND")) {
							listaDestinatari = new RecordList();							
						} 
						if(richiestaAccesso != null && "G".equalsIgnoreCase(richiestaAccesso) && !AurigaLayout.getParametroDBAsBoolean("ASS_INVII_CC_ACCESSO_GENERALIZZATO_APPEND")) {
							listaDestinatari = new RecordList();							
						}						
						RecordList listaDestinatariNew = new RecordList();
						for(int i = 0; i < listaDestinatari.getLength(); i++) {
							// escludiamo tutte le righe preimpostate con i check di accesso civico
							if(!listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatarioReadOnly") && !listaDestinatari.get(i).getAttributeAsBoolean("flgPCReadOnly")) {
								listaDestinatariNew.add(listaDestinatari.get(i));
							}
						}
						listaDestinatariNew.addList(listaDestInvioCCAccessoCivico.toArray());		
						lRecordDestinatarioInvioCC.setAttribute("listaDestinatari", listaDestinatariNew);
						destinatariForm.setValues(lRecordDestinatarioInvioCC.toMap());
						if(destinatariItem != null) {				
							destinatariItem.resetCanvasChanged();
						}
						if (detailSectionDestinatari != null) {		
							detailSectionDestinatari.open();	
						}
					}			
				}
			}		
		}
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone "Genera da modello" del
	 * file primario
	 * 
	 */
	public boolean showGeneraDaModelloButton() {
		// lo faccio vedere solo se ho il tipo documento valorizzato e non è un documento in entrata
		return tipoDocumento != null && !"".equals(tipoDocumento) && !(getFlgTipoProv() != null && "E".equals(getFlgTipoProv()));
//		return false;
	}

	/**
	 * Metodo che crea i bottoni delle azioni su file primario
	 * 
	 */
	protected void createFilePrimarioButtons() {

		filePrimarioButtons = new NestedFormItem("filePrimarioButtons");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			filePrimarioButtons.setCanFocus(true);		
		}
		filePrimarioButtons.setWidth(10);
		filePrimarioButtons.setOverflow(Overflow.VISIBLE);
		filePrimarioButtons.setNumCols(7);
		filePrimarioButtons.setColWidths(16, 16, 16, 16, 16, 16, 16);
		filePrimarioButtons.setColSpan(1);
		
		showNoteMancanzaFileButton = new ImgButtonItem("showNoteMancanzaFileButton", "archivio/note_commenti.png", "Informazioni sulla mancanza del file");
		showNoteMancanzaFileButton.setAlwaysEnabled(true);
		showNoteMancanzaFileButton.setColSpan(1);
		showNoteMancanzaFileButton.setIconWidth(16);
		showNoteMancanzaFileButton.setIconHeight(16);
		showNoteMancanzaFileButton.setIconVAlign(VerticalAlignment.BOTTOM);
		showNoteMancanzaFileButton.setAlign(Alignment.LEFT);
		showNoteMancanzaFileButton.setWidth(16);
		showNoteMancanzaFileButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean hasFilePrimario = filePrimarioForm.getValue("uriFilePrimario") != null && !"".equals(filePrimarioForm.getValue("uriFilePrimario"));
				return !hasFilePrimario && noteMancanzaFileItem.getValue() != null && !"".equals(noteMancanzaFileItem.getValue());
			}
		});
		showNoteMancanzaFileButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				NoteMancanzaFilePopup noteMancanzaFilePopup = new NoteMancanzaFilePopup("Informazioni sulla mancanza del file", (String) noteMancanzaFileItem.getValue(), editing) {
					
					@Override
					public void onClickOkButton(String noteMancanzaFile) {
						noteMancanzaFileItem.setValue(noteMancanzaFile);
					}
					
					@Override
					public void onClickAnnullaButton() {
									
					}
				};
				noteMancanzaFilePopup.show();				
			}
		});

		previewButton = new ImgButtonItem("previewButton", "file/preview.png",
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt());
		previewButton.setAlwaysEnabled(true);
		previewButton.setColSpan(1);
		previewButton.setIconWidth(16);
		previewButton.setIconHeight(16);
		previewButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewButton.setAlign(Alignment.LEFT);
		previewButton.setWidth(16);
		previewButton.setRedrawOnChange(true);
		previewButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				return false;

			}
		});
		previewButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewFile();
			}
		});

		previewEditButton = new ImgButtonItem("previewEditButton", "file/previewEdit.png",
				I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt());
		previewEditButton.setAlwaysEnabled(true);
		previewEditButton.setColSpan(1);
		previewEditButton.setIconWidth(16);
		previewEditButton.setIconHeight(16);
		previewEditButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewEditButton.setAlign(Alignment.LEFT);
		previewEditButton.setWidth(16);
		previewEditButton.setRedrawOnChange(true);
		previewEditButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")
						&& (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				}
				return false;
			}
		});
		previewEditButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewEditFile();
			}
		});

		editButton = new ImgButtonItem("editButton", "file/editDoc.png", "Modifica documento");
		editButton.setAlwaysEnabled(false);
		editButton.setColSpan(1);
		editButton.setIconWidth(16);
		editButton.setIconHeight(16);
		editButton.setIconVAlign(VerticalAlignment.BOTTOM);
		editButton.setAlign(Alignment.LEFT);
		editButton.setWidth(16);
		editButton.setRedrawOnChange(true);
		editButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showEditButton();
			}
		});
		editButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickEditFile();
			}
		});

		fileFirmatoDigButton = new ImgButtonItem("fileFirmatoDigButton", "firma/firma.png",
				I18NUtil.getMessages().protocollazione_detail_fileFirmatoDigButton_prompt());
		fileFirmatoDigButton.setAlwaysEnabled(true);
		fileFirmatoDigButton.setColSpan(1);
		fileFirmatoDigButton.setIconWidth(16);
		fileFirmatoDigButton.setIconHeight(16);
		fileFirmatoDigButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileFirmatoDigButton.setAlign(Alignment.LEFT);
		fileFirmatoDigButton.setWidth(16);
		fileFirmatoDigButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
				}
				return false;
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();
			}
		});

		firmaNonValidaButton = new ImgButtonItem("firmaNonValidaButton", "firma/firmaNonValida.png",
				I18NUtil.getMessages().protocollazione_detail_firmaNonValidaButton_prompt());
		firmaNonValidaButton.setAlwaysEnabled(true);
		firmaNonValidaButton.setColSpan(1);
		firmaNonValidaButton.setIconWidth(16);
		firmaNonValidaButton.setIconHeight(16);
		firmaNonValidaButton.setIconVAlign(VerticalAlignment.BOTTOM);
		firmaNonValidaButton.setAlign(Alignment.LEFT);
		firmaNonValidaButton.setWidth(16);
		firmaNonValidaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
				}
				return false;
			}
		});
		firmaNonValidaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();
			}
		});
		
		fileMarcatoTempButton = new ImgButtonItem("fileMarcatoTempButton", "marca/marcaNonValida.png", I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
		fileMarcatoTempButton.setAlwaysEnabled(true);
		fileMarcatoTempButton.setColSpan(1);
		fileMarcatoTempButton.setIconWidth(16);
		fileMarcatoTempButton.setIconHeight(16);
		fileMarcatoTempButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileMarcatoTempButton.setAlign(Alignment.LEFT);
		fileMarcatoTempButton.setWidth(16);
		fileMarcatoTempButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					if (lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (lInfoFileRecord.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleNonValida.png");
							return true;
						} else {
							Date dataOraMarcaTemporale = lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale();
							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale)));
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleValida.png");
							return true;
						}
					} else {
						return false;
					}
				}
				return false;
			}
		});

		altreOpButton = new ImgButtonItem("altreOpButton", "buttons/altreOp.png",
				I18NUtil.getMessages().altreOpButton_prompt());
		altreOpButton.setAlwaysEnabled(true);
		altreOpButton.setColSpan(1);
		altreOpButton.setIconWidth(16);
		altreOpButton.setIconHeight(16);
		altreOpButton.setIconVAlign(VerticalAlignment.BOTTOM);
		altreOpButton.setAlign(Alignment.LEFT);
		altreOpButton.setWidth(16);
		altreOpButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				final Menu altreOpMenu = new Menu();
				altreOpMenu.setOverflow(Overflow.VISIBLE);
				altreOpMenu.setShowIcons(true);
				altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
				altreOpMenu.setCanDragRecordsOut(false);
				altreOpMenu.setWidth("*");
				altreOpMenu.setHeight("*");

				for (MenuItem item : createAltreOpMenuItems()) {
					altreOpMenu.addItem(item);
				}
				
				if(showOperazioniTimbratura()) {
					buildMenuBarcodeEtichetta(altreOpMenu);	
				}
				
				final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
				if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
					String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
					MenuItem timbroConformitaCustomMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
					timbroConformitaCustomMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					timbroConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									clickTimbraDatiSegnatura("COPIA_CONFORME_CUSTOM");
								}
							});

					altreOpMenu.addItem(timbroConformitaCustomMenuItem);

				}

				altreOpMenu.showContextMenu();
			}
		});

		trasformaInAllegatoButton = new ImgButtonItem("trasformaInAllegatoButton", "buttons/exchange.png",
				"Trasforma in allegato");
		trasformaInAllegatoButton.setAlwaysEnabled(true);
		trasformaInAllegatoButton.setColSpan(1);
		trasformaInAllegatoButton.setIconWidth(16);
		trasformaInAllegatoButton.setIconHeight(16);
		trasformaInAllegatoButton.setIconVAlign(VerticalAlignment.BOTTOM);
		trasformaInAllegatoButton.setAlign(Alignment.LEFT);
		trasformaInAllegatoButton.setWidth(16);
		trasformaInAllegatoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (isFromEmail() && editing && uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && (editMode.equals("modificaDatiReg") || editMode.equals("protocollaMail"))) {
					return true;
				}else if(editMode.equals("caricaDatiImgAssoc") && editing && uriFilePrimarioItem.getValue() != null && !"".equals(uriFilePrimarioItem.getValue())) {
					return true;
				}
				return false;
			}
		});
		trasformaInAllegatoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickTrasformaInAllegato();
			}
		});

		generaDaModelloButton = new ImgButtonItem("generaDaModelloButton", "protocollazione/generaDaModello.png",
				I18NUtil.getMessages().protocollazione_detail_generaDaModelloButton_prompt());
		generaDaModelloButton.setAlwaysEnabled(false);
		generaDaModelloButton.setColSpan(1);
		generaDaModelloButton.setIconWidth(16);
		generaDaModelloButton.setIconHeight(16);
		generaDaModelloButton.setIconVAlign(VerticalAlignment.BOTTOM);
		generaDaModelloButton.setAlign(Alignment.LEFT);
		generaDaModelloButton.setWidth(16);
		generaDaModelloButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showGeneraDaModelloButton();
			}
		});
		generaDaModelloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
//				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
//					SC.ask("E' gia presente un file. Vuoi procedere alla generazione sovrascrivendo il file esistente?", new BooleanCallback() {
//
//						@Override
//						public void execute(Boolean value) {
//
//							if (value != null && value) {
//								generaDaModello();
//							}
//						}
//					});
//				} else {
//					generaDaModello();
//				}
				generaDaModello();
			}
		});
		
		filePrimarioButtons.setNestedFormItems(showNoteMancanzaFileButton, previewButton, previewEditButton, editButton, fileFirmatoDigButton,
				firmaNonValidaButton, fileMarcatoTempButton, altreOpButton, trasformaInAllegatoButton, generaDaModelloButton);
	}
	
	public void generaDaModello() {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(
				"LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", tipoDocumento);
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(
							data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello,
								String flgConvertiInPdf) {
							caricaModello(idModello, tipoModello, flgConvertiInPdf);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						caricaModello(idModello, data.get(0).getAttribute("tipoModello"), data.get(0).getAttribute("flgConvertiInPdf"));
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}

			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Genera da modello" del file
	 * primario
	 * 
	 */
	public void caricaModello(String idModello, String tipoModello, String flgConvertiInPdf) {
		final GWTRestDataSource lGeneraDaModelloDataSource = new GWTRestDataSource("GeneraDaModelloDataSource");
		lGeneraDaModelloDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		lGeneraDaModelloDataSource.executecustom("caricaModello", getRecordCaricaModello(idModello, tipoModello),
				new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageAfterCaricaModello(response.getData()[0]);
						}
					}
				});
	}

	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera il
	 * file primario da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModello(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		String idProcess = (String) idProcessHiddenItem.getValue();
		modelloRecord.setAttribute("processId", getIdProcessTask() != null ? getIdProcessTask() : idProcess);
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
		return modelloRecord;
	}

	/**
	 * Metodo di callback del servizio di generazione del modello con i valori
	 * iniettati, che carica a maschera il nuovo file primario
	 * 
	 */
	public void manageAfterCaricaModello(Record record) {
		String nomeFilePrimario = record.getAttribute("nomeFilePrimario");
		String uriFilePrimario = record.getAttribute("uriFilePrimario");
		filePrimarioForm.setValue("nomeFilePrimario", nomeFilePrimario);
		filePrimarioForm.setValue("uriFilePrimario", uriFilePrimario);
		filePrimarioForm.setValue("nomeFilePrimarioTif", "");
		filePrimarioForm.setValue("uriFilePrimarioTif", "");
		filePrimarioForm.setValue("remoteUriFilePrimario", false);
		filePrimarioForm.setValue("uriFileVerPreFirma", uriFilePrimario);
		filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFilePrimario);
		changedEventAfterUpload(record.getAttribute("nomeFilePrimario"), record.getAttribute("uriFilePrimario"));
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
		filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
		InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
				? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		filePrimarioForm.setValue("infoFile", info);
		filePrimarioForm.setValue("infoFileVerPreFirma", info);		
		String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");
		if (precImpronta == null || !precImpronta.equals(info.getImpronta())
				|| (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && nomeFilePrimarioOrig != null
						&& !"".equals(nomeFilePrimarioOrig) && !nomeFilePrimario.equals(nomeFilePrimarioOrig))) {
			manageChangedFilePrimario();
		}
		if (!isFormatoAmmessoFilePrimario(info)) {
			GWTRestDataSource.printMessage(new MessageBean(getFormatoNonAmmessoFilePrimarioWarning(), "", MessageType.ERROR));
			
			/*
			 * Visto l'errore dovuto al fatto che il file non è nel formato richiesto
			 * rimuovo le informazioni associate al file primario di cui si è tentato l'inserimento.
			 * In questo modo all'aggiornamento della grafica non viene riempita la text e non vengono
			 * abilitati i pulsanti di firma, etc. (normalmente abilitati nel caso di file in 
			 * formato corretto) 
			 */
			clickEliminaFile();
		}
		if (info.isFirmato() && !info.isFirmaValida()) {
			GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna",
					"", MessageType.WARNING));
		}
		if(filePrimarioForm != null) {
			filePrimarioForm.markForRedraw();
		}
		if(filePrimarioButtons != null) {
			filePrimarioButtons.markForRedraw();
		}
		manageChangedPrimario();
		if (showEditButton()) {
			clickEditFile();
		} else {
			clickPreviewFile();
		} 
	}

	/**
	 * Metodo che crea le voci del menu associato al bottone "Altre operazioni"
	 * del file primario
	 * 
	 */
	protected List<MenuItem> createAltreOpMenuItems() {

		MenuItem acquisisciDaScannerMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(), "file/scanner.png");
		acquisisciDaScannerMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return enableAcquisisciDaScannerMenuItem();
			}
		});
		acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickAcquisisciDaScanner();
			}
		});

		MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(),
				"file/mini_sign.png");
		firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
			}
		});
		firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickFirma();
			}
		});

		MenuItem downloadMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
		InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (lInfoFileRecord != null) {
			// Se è firmato P7M
			if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
				Menu showFirmatoAndSbustato = new Menu();
				MenuItem firmato = new MenuItem(
						I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
				firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFile();
					}
				});
				MenuItem sbustato = new MenuItem(
						I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
				sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFileSbustato();
					}
				});
				showFirmatoAndSbustato.setItems(firmato, sbustato);
				downloadMenuItem.setSubmenu(showFirmatoAndSbustato);
			} else {
				downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFile();
					}
				});
			}
		}
		downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
			}
		});

		// Attestato di conformità originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(), "file/attestato.png");
		attestatoConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				if (!mode.equals("new")
						&& (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null;
				}
				return false;
			}
		});
		attestatoConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
						
				final InfoFileRecord fileAllegato = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
				Record detailRecord = new Record(vm.getValues());
				final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
				final String idDoc = filePrimarioForm.getValueAsString("idDocPrimario");
				
				SC.ask("Vuoi firmare digitalmente l’attestato ?", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							creaAttestato(idUd, idDoc, fileAllegato, uri, true);
						} else {
							creaAttestato(idUd, idDoc, fileAllegato, uri, false);
						}
					}
				});
			}
		});

		MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(),
				"file/editdelete.png");
		eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				// se è valorizzato il file e non è vuoto
				if (uriFilePrimarioItem.getValue() != null && !"".equals(uriFilePrimarioItem.getValue())) {
					// se sono in una mail
					// if (isFromEmail()) {
					// // se il file non ha il mimetype (ovvero è di un formato
					// non ammesso) mostro l'elimina
					// if (lInfoFileRecord.getMimetype()== null ||
					// lInfoFileRecord.getMimetype().trim().equals("")){
					// return true;
					// }
					// // se idAttach è vuoto (eventuale body) mostro l'elimina
					// if (idAttachEmailPrimarioItem.getValue() == null ||
					// idAttachEmailPrimarioItem.getValue().equals("")){
					// return true;
					// }
					// // altrimenti non lo mostro
					// else return false;
					// }
					return true;
				}
				return false;
			}
		});
		eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickEliminaFile();
			}
		});

		MenuItem visualizzaVersioniMenuItem = new MenuItem("Visualizza versioni", "file/version.png");
		visualizzaVersioniMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				// Se è valorizzato il file
				boolean isVisible = false;
				Integer nroLastVers = filePrimarioForm != null
						&& filePrimarioForm.getValueAsString("nroLastVer") != null
						&& !"".equals(filePrimarioForm.getValueAsString("nroLastVer"))
								? new Integer(filePrimarioForm.getValueAsString("nroLastVer")) : null;
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					if (nroLastVers != null && nroLastVers > 1) {
						isVisible = true;
					}
				}
				return isVisible;
			}
		});
		visualizzaVersioniMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				String estremi = getTipoEstremiDocumento();
				String idDocIn = filePrimarioForm.getValueAsString("idDocPrimario");
				Record recordProtocollo = protocolloGeneraleForm.getValuesAsRecord();
				visualizzaVersioni(idDocIn, "FP", null, estremi, recordProtocollo);
			}
		});

		List<MenuItem> altreOpMenuItems = new ArrayList<MenuItem>();
		altreOpMenuItems.add(visualizzaVersioniMenuItem);
		altreOpMenuItems.add(downloadMenuItem);
		if (filePrimarioButtons.isEditing()) {
			altreOpMenuItems.add(acquisisciDaScannerMenuItem);
			altreOpMenuItems.add(firmaMenuItem);
		}
		
		altreOpMenuItems.add(attestatoConformitaOriginaleMenuItem);
		if (!mode.equals("view") && (filePrimarioButtons.isEditing() || forceToShowElimina || isFromEmail())
				&& nomeFilePrimarioItem.getCanEdit()) {
			altreOpMenuItems.add(eliminaMenuItem);
		}

		return altreOpMenuItems;
	}

	/**
	 * Metodo che abilita o meno la cancellazione degli allegati (non più
	 * utilizzato)
	 * 
	 */
	@Deprecated
	public boolean canRemoveAllegato() {
		return !isFromEmail();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "parere" nella sezione
	 * "Allegati"
	 * 
	 */	
	public boolean showFlgParereInAllegatiItem() {
		return false;
	}

	/**
	 * Metodo che indica se mostrare o meno il check "parte disp." nella sezione
	 * "Allegati"
	 * 
	 */
	public boolean showFlgParteDispositivoInAllegatiItem() {
		return isTaskDetail() || isProtocollazioneDetailAtti() || isProtocollazioneDetailBozze(); // ATTI
	}

	/**
	 * Metodo che indica se mostrare o meno il check "escludi pubbl." nella sezione
	 * "Allegati"
	 * 
	 */
	public boolean showFlgNoPubblInAllegatiItem() {
		return false;
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone "Importa file da altri
	 * documenti" nella sezione "Allegati"
	 * 
	 */
	public boolean showImportaFileDaDocumentiInAllegatiItem() {

		if (getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) { // PROT. ENTRATA E REG. FATTURE
			return false;
		}
		return true;
	}

	/**
	 * Metodo che indica se mostrare o meno il bottone "Genera da modello" nella
	 * sezione "Allegati"
	 * 
	 */
	public boolean showGeneraDaModelloInAllegatiItem() {
		return false;
	}

	/**
	 * Metodo che restituisce il titolo della sezione "Allegati"
	 * 
	 */
	public String getTitleDetailSectionAllegati() {		
		return I18NUtil.getMessages().protocollazione_detail_fileAllegatiForm_title();
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Allegati"
	 * 
	 */
	public boolean showOpenDetailSectionAllegati() {
		return AurigaLayout.isAttivoClienteRER();
	}
		
	/**
	 * Metodo che crea la sezione "Allegati"
	 * 
	 */
	protected void createDetailSectionAllegati() {

		createAllegatiForm();

		detailSectionAllegati = new ProtocollazioneDetailSection(
				getTitleDetailSectionAllegati(), true, showOpenDetailSectionAllegati(), false,
				fileAllegatiForm);
	}
	
	/**
	 * Metodo che crea il form della sezione "Allegati"
	 * 
	 */
	protected void createAllegatiForm() {

		createAllegatiItem();
		
		fileAllegatiForm = new DynamicForm() {
			
			@Override
			public boolean hasValue(Record defaultRecord) {
				// perchè la sezione deve risultare valorizzata anche quando gli allegati sono nascosti
				if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiGridItem)) {
					RecordList listaAllegati = fileAllegatiForm.getValueAsRecordList("listaAllegati");
					return listaAllegati != null && listaAllegati.getLength() > 0;
				}
				return super.hasValue(defaultRecord);
			}			
		};
		fileAllegatiForm.setValuesManager(vm);
		fileAllegatiForm.setWidth("100%");
		fileAllegatiForm.setPadding(5);
		fileAllegatiForm.setTabSet(tabSet);
		fileAllegatiForm.setTabID("HEADER");
		fileAllegatiForm.setFields(fileAllegatiItem);
	}
	
	public boolean isModalitaAllegatiGrid() {
		return false;
	}
	
	/**
	 * Metodo che crea l'item degli allegati
	 * 
	 */
	protected void createAllegatiItem() {
		
		if(isModalitaAllegatiGrid()) {
			/* NUOVA GESTIONE ALLEGATI CON GRIDITEM */
			fileAllegatiItem = new AllegatiGridItem("listaAllegati", "listaAllegatiProt") {
					
				@Override
				public String getFlgTipoProvProtocollo() {
					return getFlgTipoProv();
				}
				
				@Override
				public String getIdProcess() {
					return getIdProcessTask();
				}

				@Override
				public String getIdProcessType() {
					return getIdProcessTypeTask();
				}

				@Override
				public String getIdTaskCorrenteAllegati() {
					return getIdTaskCorrente();
				}

				@Override
				public HashSet<String> getTipiModelliAttiAllegati() {
					return getTipiModelliAttiInAllegati();
				}
				
				@Override
				public Record getDetailRecord() {
					return getRecordToSave(null);				
				}
				
				@Override
				public boolean isObbligatorioFile() {
					return isObbligatorioFileInAllegati();
				}

				// @Override
				// public boolean showUpload() {
				// return !isFromEmail();
				// };

				@Override
				public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
					return !enableAcquisisciDaScannerMenuItem();
				};

				@Override
				public boolean sonoInMail() {
					return isFromEmail();
				};
				
				@Override
				public boolean isScansioneImgAssoc() {
					return editMode!=null && "caricaDatiImgAssoc".equalsIgnoreCase(editMode) ;
				};

				@Override
				public void clickTrasformaInPrimario(int index) {
					clickTrasfInPrimario(index);
				};

				@Override
				public boolean sonoModificaVisualizza() {
					return !mode.equals("new");
				}

				@Override
				public boolean canBeEditedByApplet() {
					return canEditByApplet();
				}

				@Override
				public boolean isAttivaTimbraturaFilePostRegAllegato() {
					return isAttivaTimbraturaFilePostReg();
				}

				@Override
				public boolean getShowGeneraDaModello() {
					return showGeneraDaModelloInAllegatiItem();
				}
				
				@Override
				public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
					return getRecordCaricaModelloInAllegati(idModello, tipoModello);
				}

				@Override
				public void manageChangedFileAllegati() {
					manageChangedFileInAllegatiItem();
				}

				@Override
				public void manageChangedFileAllegatiParteDispositivo() {
					manageChangedFileParteDispositivoInAllegatiItem();
				}

				@Override
				public boolean isFormatoAmmesso(InfoFileRecord info) {
					return isFormatoAmmessoFileAllegato(info);
				}
				
				@Override
				public boolean isAttivaTimbroTipologia() {
					return isAttivaTimbroTipologiaProtocollazione();
				}
				
				@Override
				public boolean isAttivaVociBarcode() {
					
					String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				
				@Override
				public String getIdDocFilePrimario() {
					
					String idDocPrimario = (idDocPrimarioHiddenItem.getValue() != null) ? String.valueOf(idDocPrimarioHiddenItem.getValue()) : null;
					return idDocPrimario;
				}
				
				@Override
				public String getFinalitaImportaAllegatiMultiLookupArchivio() {
					return  "IMPORTA_UD";
				}
				
				@Override
				public String getImportaFileDocumentiButtonTitle() {
					return  I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
				}
				
				@Override
				public boolean getShowTimbraBarcodeMenuOmissis() {
					return true;
				}
				
				@Override
				public String getIdUd() {
					return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
				}
				
				@Override
				public boolean isAttivaAllegatoUd() {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ALLEGATO_UD");
				}
				
				@Override
				public boolean getShowFlgParere() {
					return showFlgParereInAllegatiItem();
				}
				
				@Override
				public boolean getShowFlgParteDispositivo() {
					return showFlgParteDispositivoInAllegatiItem();
				}
				
				@Override
				public boolean getShowFlgNoPubblAllegato() {
					return showFlgNoPubblInAllegatiItem();
				}
				
				@Override
				public boolean getShowFlgSostituisciVerPrec() {
					return showFlgSostituisciVerPrecItem();
				}
				
				@Override
				public boolean getShowImportaFileDaDocumenti() {
					return showImportaFileDaDocumentiInAllegatiItem();
				}
				
				@Override
				public boolean isGrigliaEditabile() {
					return true;
				}	
				
				@Override
				public boolean isShowModalPreview() {
					return isEnablePreviewModal();
				}

				@Override
				public void onRecordSelected(Record record) {
					// TODO Auto-generated method stub
					
				}
			};
			fileAllegatiItem.setShowTitle(false);
			fileAllegatiItem.setHeight(200);
		} else {
			fileAllegatiItem = new AllegatiItem() {
				
				@Override
				public String getFlgTipoProvProtocollo() {
					return getFlgTipoProv();
				}
				
				@Override
				public String getIdProcess() {
					return getIdProcessTask();
				}

				@Override
				public String getIdProcessType() {
					return getIdProcessTypeTask();
				}

				@Override
				public String getIdTaskCorrenteAllegati() {
					return getIdTaskCorrente();
				}

				@Override
				public HashSet<String> getTipiModelliAttiAllegati() {
					return getTipiModelliAttiInAllegati();
				}
				
				@Override
				public boolean isObbligatorioFile() {
					return isObbligatorioFileInAllegati();
				}

				// @Override
				// public boolean showUpload() {
				// return !isFromEmail();
				// };

				@Override
				public boolean showAcquisisciDaScanner() {
					return enableAcquisisciDaScannerMenuItem();
				};

				@Override
				public boolean sonoInMail() {
					return isFromEmail();
				};
				
				@Override
				public boolean isScansioneImgAssoc() {
					return editMode!=null && "caricaDatiImgAssoc".equalsIgnoreCase(editMode);
				};

				@Override
				public void clickTrasformaInPrimario(int index) {
					clickTrasfInPrimario(index);
				};

				@Override
				public boolean sonoModificaVisualizza() {
					return !mode.equals("new");
				}

				@Override
				public boolean canBeEditedByApplet() {
					return canEditByApplet();
				}

				@Override
				public boolean isAttivaTimbraturaFilePostRegAllegato() {
					return isAttivaTimbraturaFilePostReg();
				}

				@Override
				public boolean showGeneraDaModello() {
					return showGeneraDaModelloInAllegatiItem();
				}
				
				@Override
				public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
					return getRecordCaricaModelloInAllegati(idModello, tipoModello);
				}

				@Override
				public void manageChangedFileAllegati() {
					manageChangedFileInAllegatiItem();
				}

				@Override
				public void manageChangedFileAllegatiParteDispositivo() {
					manageChangedFileParteDispositivoInAllegatiItem();
				}

				@Override
				public boolean isFormatoAmmesso(InfoFileRecord info) {
					return isFormatoAmmessoFileAllegato(info);
				}
				
				@Override
				public boolean isAttivaTimbroTipologia() {
					return isAttivaTimbroTipologiaProtocollazione();
				}
				
				@Override
				public boolean isAttivaVociBarcode() {
					
					String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				
				@Override
				public String getIdDocFilePrimario() {
					
					String idDocPrimario = (idDocPrimarioHiddenItem.getValue() != null) ? String.valueOf(idDocPrimarioHiddenItem.getValue()) : null;
					return idDocPrimario;
				}
				
				@Override
				public String getFinalitaImportaAllegatiMultiLookupArchivio() {
					return  "IMPORTA_UD";
				}
				
				@Override
				public String getImportaFileDocumentiBtnTitle() {
					return  I18NUtil.getMessages().protocollazione_detail_importaDocumentiDaAltriDocumenti_title();
				}
				
				@Override
				public boolean showTimbraBarcodeMenuOmissis() {
					return true;
				}
				
				@Override
				public String getIdUd() {
					return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
				}
				
				@Override
				public boolean isAttivaAllegatoUd() {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ALLEGATO_UD");
				}
				
				@Override
				public boolean isShowModalPreview() {
					return isEnablePreviewModal();
				}
			};
			fileAllegatiItem.setName("listaAllegati");
			fileAllegatiItem.setShowTitle(false);
			((AllegatiItem)fileAllegatiItem).setShowFlgParere(showFlgParereInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).setShowFlgParteDispositivo(showFlgParteDispositivoInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).setShowFlgNoPubblAllegato(showFlgNoPubblInAllegatiItem());
			((AllegatiItem)fileAllegatiItem).setShowFlgSostituisciVerPrec(showFlgSostituisciVerPrecItem());
			((AllegatiItem)fileAllegatiItem).setShowImportaFileDaDocumenti(showImportaFileDaDocumentiInAllegatiItem());
		}
	}

	/**
	 * Metodo che restituisce l'identificativo del processo, da passare alla
	 * sezione degli allegati
	 * 
	 */	
	public String getIdProcessTask() {
		return (String) idProcessHiddenItem.getValue();
	}

	/**
	 * Metodo che restituisce il tipo del processo, da passare alla sezione
	 * degli allegati
	 * 
	 */
	public String getIdProcessTypeTask() {
		return null;
	}

	/**
	 * Metodo che restituisce l'identificativo del task corrente, da passare
	 * alla sezione degli allegati
	 * 
	 */
	public String getIdTaskCorrente() {
		return null;
	}

	/**
	 * Metodo che indica se il file allegato è in un formato ammesso
	 * 
	 */
	public boolean isFormatoAmmessoFileAllegato(InfoFileRecord info) {
		return true;
	}
	
	
	/**
	 * Metodo che indica se sono da attivare le voci di menù per la stampa dei barcode ( Su A4 ed Etichetta )
	 * con tipologia
	 */
	public boolean isAttivaTimbroTipologiaProtocollazione(){
		return vm.getValue("attivaTimbroTipologia") != null && ((Boolean) vm.getValue("attivaTimbroTipologia"));
	}

	/**
	 * Metodo che restituisce la mappa dei modelli relativi agli atti associati
	 * al task, da passare alla sezione degli allegati
	 * 
	 */
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return null;
	}
	
	/**
	 * Metodo che indica se il file negli allegati è obbligatorio, o se basta specificare uno tra tipo e descrizione
	 * 
	 */
	public boolean isObbligatorioFileInAllegati() {
		return false;
	}

	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera
	 * l'allegato da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModelloInAllegati(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
		modelloRecord.setAttribute("idUd", idUd);
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
		}
		return modelloRecord;
	}

	/**
	 * Metodo che indica se mostrare o meno la sezione "Dati di ricezione"
	 * 
	 */
	public boolean showDetailSectionDatiRicezione() {
		return false; // PROT_ENTRATA; ISTANZE; REGISTRO_FATTURE
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Dati di ricezione"
	 * 
	 */
	public boolean showOpenDetailSectionDatiRicezione() {
		return !isModalitaWizard() && isRequiredMezzoTrasmissioneInDatiRicezione();
	}

	/**
	 * Metodo che indica se la sezione "Dati di ricezione" è obbligatoria
	 * 
	 */
	public boolean isRequiredDetailSectionDatiRicezione() {
		return !isModalitaWizard() && isRequiredMezzoTrasmissioneInDatiRicezione();
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Dati di ricezione" prima della sezione "Mittenti"
	 * 
	 */
	public boolean showDatiRicezioneBeforeMittenti() {
		return isRequiredMezzoTrasmissioneInDatiRicezione();
	}
	
	/**
	 * Metodo che indica se il mezzo di trasmissione nella sezione "Dati di ricezione" è obbligatorio
	 * 
	 */
	public boolean isRequiredMezzoTrasmissioneInDatiRicezione() {
		return false;
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Dati di ricezione"
	 * 
	 */
	public String getTitleDetailSectionDatiRicezione() {
		return I18NUtil.getMessages().protocollazione_detail_datiRicezioneForm_title();
	}
	
	/**
	 * Metodo che crea la sezione "Dati di ricezione"
	 * 
	 */
	protected void createDetailSectionDatiRicezione() {

		createDatiRicezioneForm();

		detailSectionDatiRicezione = new ProtocollazioneDetailSection(getTitleDetailSectionDatiRicezione(), true, showOpenDetailSectionDatiRicezione(), isRequiredDetailSectionDatiRicezione(), datiRicezioneForm1, datiRicezioneForm2);
	}

	/**
	 * Metodo che crea il form della sezione "Dati di ricezione"
	 * 
	 */
	protected void createDatiRicezioneForm() {

		datiRicezioneForm1 = new DynamicForm();
		datiRicezioneForm1.setValuesManager(vm);
		datiRicezioneForm1.setWidth("100%");
		datiRicezioneForm1.setPadding(5);
		datiRicezioneForm1.setWrapItemTitles(false);
		datiRicezioneForm1.setNumCols(15);
		datiRicezioneForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiRicezioneForm1.setTabSet(tabSet);
		datiRicezioneForm1.setTabID("HEADER");
		
		//Mezzi di trasmissione
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboMezziTrasmissioneDataSource", "key", FieldType.TEXT);
		
		mezzoTrasmissioneItem = new SelectItem("mezzoTrasmissione", I18NUtil.getMessages().protocollazione_detail_mezzoTrasmissioneItem_title()) {
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid mezzoTrasmissionePickListProperties = super.builPickListProperties();					
				mezzoTrasmissionePickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource mezziTrasmissioneDS = (GWTRestDataSource) mezzoTrasmissioneItem.getOptionDataSource();		
						mezziTrasmissioneDS.extraparam.put("idRegistrazione", idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null);
						mezzoTrasmissioneItem.setOptionDataSource(mezziTrasmissioneDS);
						mezzoTrasmissioneItem.invalidateDisplayValueCache();
					}
				});
				return mezzoTrasmissionePickListProperties;
			}
		};
		mezzoTrasmissioneItem.setOptionDataSource(mezziTrasmissioneDS);  
		mezzoTrasmissioneItem.setAutoFetchData(true);
		mezzoTrasmissioneItem.setDisplayField("value");
		mezzoTrasmissioneItem.setValueField("key");			
		mezzoTrasmissioneItem.setWrapTitle(false);
		mezzoTrasmissioneItem.setStartRow(true);
		if(isRequiredMezzoTrasmissioneInDatiRicezione()) {
			mezzoTrasmissioneItem.setRequired(true);
		} else {
			mezzoTrasmissioneItem.setAllowEmptyValue(true);
		}
		mezzoTrasmissioneItem.setColSpan(4);
		mezzoTrasmissioneItem.setWidth(250);   
		mezzoTrasmissioneItem.addChangedHandler(new ChangedHandler() {				
			@Override
			public void onChanged(ChangedEvent event) {
				//if (!"R".equals(datiRicezioneForm1.getValueAsString("mezzoTrasmissione")))
				if (!isAbilRaccomandata(datiRicezioneForm1.getValue("mezzoTrasmissione"))) {
					datiRicezioneForm1.setValue("nroRaccomandata", "");
					datiRicezioneForm1.setValue("dataRaccomandata", "");
				}
								
				datiRicezioneForm1.redraw();
			}
		});			
				
		raccommandataTitleItem = new TitleItem(I18NUtil.getMessages().protocollazione_detail_raccomandataItem_title(), false);
		raccommandataTitleItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				//return datiRicezioneForm1.getValue("mezzoTrasmissione") != null && "R".equals(datiRicezioneForm1.getValueAsString("mezzoTrasmissione"));				
				return isAbilRaccomandata(datiRicezioneForm1.getValue("mezzoTrasmissione"));
			}
		});
        
		nroRaccomandataItem = new TextItem("nroRaccomandata", I18NUtil.getMessages().protocollazione_detail_nroRaccomandataItem_title());
		nroRaccomandataItem.setColSpan(1);
		nroRaccomandataItem.setWidth(100);
		nroRaccomandataItem.setLength(50);
		nroRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				
				//return datiRicezioneForm1.getValue("mezzoTrasmissione") != null && "R".equals(datiRicezioneForm1.getValueAsString("mezzoTrasmissione"));
				return isAbilRaccomandata(datiRicezioneForm1.getValue("mezzoTrasmissione"));
			}
		});
        
		dataRaccomandataItem = new DateItem("dataRaccomandata", I18NUtil.getMessages().protocollazione_detail_dataRaccomandataItem_title());  					
		dataRaccomandataItem.setColSpan(1);
		dataRaccomandataItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				String codici = AurigaLayout.getParametroDB("COD_MEZZI_RACCOMANDATA");
				
				//return datiRicezioneForm1.getValue("mezzoTrasmissione") != null && "R".equals(datiRicezioneForm1.getValueAsString("mezzoTrasmissione"));
				return datiRicezioneForm1.getValue("mezzoTrasmissione") != null && (codici !=null && !codici.equalsIgnoreCase("") && codici.indexOf(datiRicezioneForm1.getValueAsString("mezzoTrasmissione")) != -1);				
			}
		});
		
		dataEOraArrivoItem = new DateTimeItem("dataEOraArrivo",	I18NUtil.getMessages().protocollazione_detail_dataEOraArrivoItem_title());
		dataEOraArrivoItem.setColSpan(1);
		dataEOraArrivoItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return !AurigaLayout.getParametroDBAsBoolean("HIDE_TS_ARRIVO_DOC");
			}
		});
		
		dataEOraSpedizioneItem = new DateTimeItem("dataEOraSpedizione",	"Data e ora spedizione");
		dataEOraSpedizioneItem.setColSpan(1);
		dataEOraSpedizioneItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				String flgTipoProv = getFlgTipoProv();
				return flgTipoProv != null && "E".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A();
			}
		});		
		
		/*
		altriDatiRicezioneButton = new ImgButtonItem("altriDatiRicezione", "buttons/altriDati.png",	I18NUtil.getMessages().protocollazione_detail_altriDatiRicezioneButton_prompt());
		altriDatiRicezioneButton.setAlwaysEnabled(true);
		altriDatiRicezioneButton.setColSpan(1);
		altriDatiRicezioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				AltriDatiRicezionePopup altriDatiRicezionePopup = new AltriDatiRicezionePopup(editing, idUd) {
					
					@Override
					public boolean isRequiredMezzoTrasmissione() {
						return isRequiredMezzoTrasmissioneInDatiRicezione();
					}

					@Override
					public void onClickOkButton() {
						if (mezzoTrasmissioneItem.getValue() == null || "".equals(mezzoTrasmissioneItem.getValue())) {
							altriDatiRicezioneButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_altriDatiRicezioneButton_prompt());
						} else {
							// Se e' una RACCOMANDATA devo mostrare anche il NR. e DATA
							if ("R".equals(mezzoTrasmissioneItem.getValue())) {
								altriDatiRicezioneButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_altriDatiRicezioneButton_prompt2(
									"" + mezzoTrasmissioneItem.getSelectedRecord().getAttribute(mezzoTrasmissioneItem.getDisplayFieldName()),
									"" + nroRaccomandataItem.getValue(), DateUtil.format(dataRaccomandataItem.getValueAsDate())));
							} else {
								altriDatiRicezioneButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_altriDatiRicezioneButton_prompt1(
									"" + mezzoTrasmissioneItem.getSelectedRecord().getAttribute(mezzoTrasmissioneItem.getDisplayFieldName())));
							}
						}
						if (mezzoTrasmissioneItem.getValue() == null || "".equals(mezzoTrasmissioneItem.getValue()) || !"R".equals(mezzoTrasmissioneItem.getValue())) {
							nroRaccomandataItem.clearValue();
							dataRaccomandataItem.clearValue();
							nroListaRaccomandataHiddenItem.clearValue();
						}
						mezzoTrasmissioneHiddenItem.setValue(mezzoTrasmissioneItem.getValue());
						nroRaccomandataHiddenItem.setValue(nroRaccomandataItem.getValue());
						dataRaccomandataHiddenItem.setValue(dataRaccomandataItem.getValue());
//						nroListaRaccomandataHiddenItem.setValue(nroListaRaccomandataItem.getValue());
					};
				};
				altriDatiRicezionePopup.mezzoTrasmissioneItem.setValue(mezzoTrasmissioneHiddenItem.getValue());
				altriDatiRicezionePopup.nroRaccomandataItem.setValue(nroRaccomandataHiddenItem.getValue());
				altriDatiRicezionePopup.dataRaccomandataItem.setValue(dataRaccomandataHiddenItem.getValue());
//				altriDatiRicezionePopup.nroListaRaccomandataItem.setValue(nroListaRaccomandataHiddenItem.getValue());
				altriDatiRicezionePopup.show();
			}
		});
		
		mezzoTrasmissioneHiddenItem = new HiddenItem("mezzoTrasmissione");
		nroRaccomandataHiddenItem = new HiddenItem("nroRaccomandata");
		dataRaccomandataHiddenItem = new HiddenItem("dataRaccomandata");
		nroListaRaccomandataHiddenItem = new HiddenItem("nroListaRaccomandata");
		*/
		
		List<FormItem> fields = new ArrayList<FormItem>();
		fields.add(mezzoTrasmissioneItem);
		fields.add(raccommandataTitleItem);
		fields.add(nroRaccomandataItem);
		fields.add(dataRaccomandataItem);
//		fields.add(new SpacerItem());
		fields.add(dataEOraArrivoItem);
		fields.add(dataEOraSpedizioneItem);
//		fields.add(altriDatiRicezioneButton);
//		fields.add(mezzoTrasmissioneHiddenItem);
//		fields.add(nroRaccomandataHiddenItem);
//		fields.add(dataRaccomandataHiddenItem);
//		fields.add(nroListaRaccomandataHiddenItem);
		
		datiRicezioneForm1.setFields(fields.toArray(new FormItem[fields.size()]));
		
		datiRicezioneForm2 = new DynamicForm();
		datiRicezioneForm2.setValuesManager(vm);
		datiRicezioneForm2.setWidth("100%");
		datiRicezioneForm2.setPadding(5);
		datiRicezioneForm2.setWrapItemTitles(false);
		datiRicezioneForm2.setNumCols(15);
		datiRicezioneForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiRicezioneForm2.setTabSet(tabSet);
		datiRicezioneForm2.setTabID("HEADER");

		CustomValidator lValorizzatoSeAltriValorizzatiValidatorProtRicevuto = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String name = getFormItem().getName();
				if (isBlank(value)) {
					boolean valid = true;
					if ("nroProtRicevuto".equals(name)) {
						valid = valid && isBlank(annoProtRicevutoItem.getValue()) && isBlank(dataProtRicevutoItem.getValue());
					} else if ("annoProtRicevuto".equals(name)) {
						valid = valid && (isBlank(nroProtRicevutoItem.getValue()) || !isBlank(dataProtRicevutoItem.getValue()));
					} else if ("dataProtRicevuto".equals(name)) {
						valid = valid && (isBlank(nroProtRicevutoItem.getValue()) || !isBlank(annoProtRicevutoItem.getValue()));
					}
					return valid;
				}
				return true;
			}

			private boolean isBlank(Object value) {
				return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
			}
		};
		
		protRicevutoTitleItem = new TitleItem(I18NUtil.getMessages().protocollazione_detail_protocolloRicevutoItem_title());
		protRicevutoTitleItem.setStartRow(true);
		
		rifOrigineProtRicevutoItem = new TextItem("rifOrigineProtRicevuto",	I18NUtil.getMessages().protocollazione_detail_rifOrigineProtRicevutoItem_title());
		rifOrigineProtRicevutoItem.setWidth(150);
		rifOrigineProtRicevutoItem.setLength(50);
		rifOrigineProtRicevutoItem.setWrapTitle(false);

		nroProtRicevutoItem = new ExtendedTextItem("nroProtRicevuto", I18NUtil.getMessages().protocollazione_detail_nroProtRicevutoItem_title());
		nroProtRicevutoItem.setColSpan(1);
		nroProtRicevutoItem.setWidth(100);
		nroProtRicevutoItem.setLength(50);
		nroProtRicevutoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		nroProtRicevutoItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroProtRicevutoItem.validate();
				annoProtRicevutoItem.validate();
				dataProtRicevutoItem.validate();
			}
		});

		annoProtRicevutoItem = new AnnoItem("annoProtRicevuto",	I18NUtil.getMessages().protocollazione_detail_annoProtRicevutoItem_title());
		annoProtRicevutoItem.setColSpan(1);
		annoProtRicevutoItem.setWidth(100);
		annoProtRicevutoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		annoProtRicevutoItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroProtRicevutoItem.validate();
				annoProtRicevutoItem.validate();
				dataProtRicevutoItem.validate();
			}
		});

		dataProtRicevutoItem = new DateItem("dataProtRicevuto",	I18NUtil.getMessages().protocollazione_detail_dataProtRicevutoItem_title());
		dataProtRicevutoItem.setColSpan(1);
		dataProtRicevutoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		dataProtRicevutoItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroProtRicevutoItem.validate();
				annoProtRicevutoItem.validate();
				dataProtRicevutoItem.validate();
			}
		});
				
		datiRicezioneForm2.setFields(protRicevutoTitleItem, rifOrigineProtRicevutoItem, nroProtRicevutoItem, annoProtRicevutoItem, dataProtRicevutoItem);
	}

	/**
	 * Metodo che indica se mostrare o meno la sezione "Doc. collegato"
	 * 
	 */
	public boolean showDetailSectionDocCollegato() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_DOC_PRECEDENTE");
	}

	/**
	 * Metodo che crea la sezione "Doc. collegato"
	 * 
	 */
	protected void createDetailSectionDocCollegato() {

		createDocCollegatoForm();

		detailSectionDocCollegato = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_docCollegatoForm_title(), true, false, false,
				docCollegatoForm);
	}

	/**
	 * Metodo che crea il form della sezione "Doc. collegato"
	 * 
	 */
	protected void createDocCollegatoForm() {

		docCollegatoForm = new DynamicForm();
		docCollegatoForm.setValuesManager(vm);
		docCollegatoForm.setWidth("100%");
		docCollegatoForm.setPadding(5);
		docCollegatoForm.setWrapItemTitles(false);
		docCollegatoForm.setNumCols(9);
		docCollegatoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, "*", "*");
		docCollegatoForm.setTabSet(tabSet);
		docCollegatoForm.setTabID("HEADER");

		ValorizzatoSeAltriValorizzatiValidator lValorizzatoSeAltriValorizzatiValidatorDocRif = new ValorizzatoSeAltriValorizzatiValidator();
		lValorizzatoSeAltriValorizzatiValidatorDocRif
				.setFields(new String[] { "siglaDocRiferimento", "annoDocRiferimento", "nroDocRiferimento" });

		siglaDocRiferimentoItem = new SelectItem("siglaDocRiferimento",
				I18NUtil.getMessages().protocollazione_detail_siglaDocRiferimentoItem_title());
		LinkedHashMap<String, String> lMapSiglaDocRif = new LinkedHashMap<String, String>();
		lMapSiglaDocRif.put("PG", "Prot. Generale");
		lMapSiglaDocRif.put("I", "Prot. Interno");
		siglaDocRiferimentoItem.setValueMap(lMapSiglaDocRif);
		siglaDocRiferimentoItem.setWidth(120);
		siglaDocRiferimentoItem.setWrapTitle(false);
		siglaDocRiferimentoItem.setAllowEmptyValue(true);
		siglaDocRiferimentoItem.setStartRow(true);
		siglaDocRiferimentoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorDocRif);
		// siglaDocRiferimentoItem.setValidateOnChange(true);
		siglaDocRiferimentoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				siglaDocRiferimentoItem.validate();
				annoDocRiferimentoItem.validate();
				nroDocRiferimentoItem.validate();
			}
		});

		annoDocRiferimentoItem = new AnnoItem("annoDocRiferimento",
				I18NUtil.getMessages().protocollazione_detail_annoDocRiferimentoItem_title());
		annoDocRiferimentoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorDocRif);
		// annoDocRiferimentoItem.setValidateOnChange(true);
		annoDocRiferimentoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				siglaDocRiferimentoItem.validate();
				annoDocRiferimentoItem.validate();
				nroDocRiferimentoItem.validate();
			}
		});

		nroDocRiferimentoItem = new ExtendedNumericItem("nroDocRiferimento",
				I18NUtil.getMessages().protocollazione_detail_nroDocRiferimentoItem_title());
		nroDocRiferimentoItem.setLength(7);
		nroDocRiferimentoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorDocRif);
		// nroDocRiferimentoItem.setValidateOnChange(true);
		nroDocRiferimentoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				siglaDocRiferimentoItem.validate();
				annoDocRiferimentoItem.validate();
				nroDocRiferimentoItem.validate();
			}
		});

		docCollegatoForm.setFields(siglaDocRiferimentoItem, annoDocRiferimentoItem, nroDocRiferimentoItem);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Reg. emergenza"
	 * 
	 */
	public boolean showDetailSectionRegEmergenza() {
		return false; // PROT_ENTRATA; PROT_USCITA; REGISTRO_FATTURE
	}

	/**
	 * Metodo che crea la sezione "Reg. emergenza"
	 * 
	 */
	protected void createDetailSectionRegEmergenza() {

		createRegEmergenzaForm();

		detailSectionRegEmergenza = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_regEmergenzaForm_title(), true, false, false,
				regEmergenzaForm);
	}

	/**
	 * Metodo che crea il form della sezione "Reg. emergenza"
	 * 
	 */
	protected void createRegEmergenzaForm() {

		regEmergenzaForm = new DynamicForm();
		regEmergenzaForm.setValuesManager(vm);
		regEmergenzaForm.setWidth("100%");
		regEmergenzaForm.setPadding(5);
		regEmergenzaForm.setWrapItemTitles(false);
		regEmergenzaForm.setNumCols(10);
		regEmergenzaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		regEmergenzaForm.setTabSet(tabSet);
		regEmergenzaForm.setTabID("HEADER");

		ValorizzatoSeAltriValorizzatiValidator lValorizzatoSeAltriValorizzatiValidatorEmergenza = new ValorizzatoSeAltriValorizzatiValidator();
		lValorizzatoSeAltriValorizzatiValidatorEmergenza
				.setFields(new String[] { "rifRegEmergenza", "nroRegEmergenza", "dataRegEmergenza" });

		rifRegEmergenzaItem = new ExtendedTextItem("rifRegEmergenza",
				I18NUtil.getMessages().protocollazione_detail_rifRegEmergenzaItem_title());
		rifRegEmergenzaItem.setWidth(150);
		rifRegEmergenzaItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorEmergenza);
		rifRegEmergenzaItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				rifRegEmergenzaItem.validate();
				nroRegEmergenzaItem.validate();
				dataRegEmergenzaItem.validate();
			}
		});

		nroRegEmergenzaItem = new ExtendedNumericItem("nroRegEmergenza",
				I18NUtil.getMessages().protocollazione_detail_nroRegEmergenzaItem_title());
		nroRegEmergenzaItem.setLength(7);
		nroRegEmergenzaItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorEmergenza);
		nroRegEmergenzaItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				rifRegEmergenzaItem.validate();
				nroRegEmergenzaItem.validate();
				dataRegEmergenzaItem.validate();
			}
		});

		dataRegEmergenzaItem = new DateTimeItem("dataRegEmergenza",
				I18NUtil.getMessages().protocollazione_detail_dataRegEmergenzaItem_title());
		dataRegEmergenzaItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorEmergenza);
		dataRegEmergenzaItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				rifRegEmergenzaItem.validate();
				nroRegEmergenzaItem.validate();
				dataRegEmergenzaItem.validate();
			}
		});

		idUserRegEmergenzaHiddenItem = new HiddenItem("idUserRegEmergenza");
		idUoRegEmergenzaHiddenItem = new HiddenItem("idUoRegEmergenza");

		regEmergenzaForm.setFields(rifRegEmergenzaItem, nroRegEmergenzaItem, dataRegEmergenzaItem,
				idUserRegEmergenzaHiddenItem, idUoRegEmergenzaHiddenItem);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Collocazione fisica"
	 * 
	 */
	public boolean showDetailSectionCollocazioneFisica() {
		if(isRichiestaAccessoCivico() || isRichiestaContratti()) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo che crea la sezione "Collocazione fisica"
	 * 
	 */
	protected void createDetailSectionCollocazioneFisica() {

		createCollocazioneFisicaForm();

		detailSectionCollocazioneFisica = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_collocazioneFisicaForm_title(), true, false, false,
				collocazioneFisicaForm);
	}

	/**
	 * Metodo che crea il form della sezione "Collocazione fisica"
	 * 
	 */
	protected void createCollocazioneFisicaForm() {

		collocazioneFisicaForm = new DynamicForm();
		collocazioneFisicaForm.setValuesManager(vm);
		collocazioneFisicaForm.setWidth("100%");
		collocazioneFisicaForm.setHeight(10);
		collocazioneFisicaForm.setPadding(5);
		collocazioneFisicaForm.setNumCols(10);
		collocazioneFisicaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		collocazioneFisicaForm.setWrapItemTitles(false);
		collocazioneFisicaForm.setTabSet(tabSet);
		collocazioneFisicaForm.setTabID("HEADER");

		idToponimoItem = new HiddenItem("idToponimo");
		idToponimoOutItem = new HiddenItem("idToponimoOut");

		// cod.rapido
		codRapidoCollocazioneFisicaItem = new ExtendedTextItem("codRapidoCollocazioneFisica",
				I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoCollocazioneFisicaItem.setEndRow(false);
		codRapidoCollocazioneFisicaItem.setWidth(120);
		codRapidoCollocazioneFisicaItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String value = codRapidoCollocazioneFisicaItem.getValueAsString();
				collocazioneFisicaForm.clearErrors(true);
				collocazioneFisicaForm.setValue("idToponimo", "");
				collocazioneFisicaForm.setValue("idToponimoOut", "");
				collocazioneFisicaForm.setValue("nomeCollocazioneFisica", "");
				collocazioneFisicaForm.setValue("descrizioneCollocazioneFisica", "");
				collocazioneFisicaForm.setValue("noteCollocazioneFisica", "");

				if (value != null && !"".equals(value)) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
							"FindToponomasticoDatasource");
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoToponimo", codRapidoCollocazioneFisicaItem.getValue());
					lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							// Se non ha trovato niente
							if (object.getAttributeAsBoolean("vuoto")) {
								collocazioneFisicaForm.setFieldErrors("codRapidoCollocazioneFisica", I18NUtil.getMessages().protocollazione_detail_esitoValidazione_KO_value());
							} else {
								collocazioneFisicaForm.clearFieldErrors("codRapidoCollocazioneFisica", true);
								if (object.getAttribute("idToponimoOut") != null
										&& !object.getAttribute("idToponimoOut").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("idToponimoOut",
											object.getAttribute("idToponimoOut"));
								}
								if (object.getAttribute("codRapidoToponimo") != null
										&& !object.getAttribute("codRapidoToponimo").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("codRapidoCollocazioneFisica",
											object.getAttribute("codRapidoToponimo"));
								}
								if (object.getAttribute("nomeToponimo") != null
										&& !object.getAttribute("nomeToponimo").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("nomeCollocazioneFisica",
											object.getAttribute("nomeToponimo"));
								}
								if (object.getAttribute("descrToponimo") != null
										&& !object.getAttribute("descrToponimo").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("descrizioneCollocazioneFisica",
											object.getAttribute("descrToponimo"));
								}
								if (object.getAttribute("idToponimo") != null
										&& !object.getAttribute("idToponimo").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("idToponimo", object.getAttribute("idToponimo"));
								}
							}
						}
					});
				}
			}
		});

		nomeCollocazioneFisicaItem = new TextItem("nomeCollocazioneFisica",
				I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeCollocazioneFisicaItem.setShowTitle(false);

		descrizioneCollocazioneFisicaItem = new HiddenItem("descrizioneCollocazioneFisica");
		descrizioneCollocazioneFisicaItem
				.setTitle(I18NUtil.getMessages().protocollazione_detail_descrizioneItem_title());
		descrizioneCollocazioneFisicaItem.setWidth(500);
		descrizioneCollocazioneFisicaItem.setColSpan(2);

		noteCollocazioneFisicaItem = new HiddenItem("noteCollocazioneFisica");

		lookupTopograficoCollocazioneFisicaButton = new ImgButtonItem("lookupTopograficoCollocazioneFisicaButton",
				"lookup/topografico.png",
				I18NUtil.getMessages().protocollazione_detail_lookupTopograficoButton_prompt());
		lookupTopograficoCollocazioneFisicaButton.setEndRow(false);
		lookupTopograficoCollocazioneFisicaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				LookupTopograficoPopup lookupTopograficoPopup = new LookupTopograficoPopup() {

					@Override
					public void manageLookupBack(Record record) {
						collocazioneFisicaForm.setValue("idToponimo", record.getAttribute("idTopografico"));
						collocazioneFisicaForm.setValue("codRapidoCollocazioneFisica",
								record.getAttribute("codiceRapido"));
						collocazioneFisicaForm.setValue("nomeCollocazioneFisica", record.getAttribute("nome"));
						collocazioneFisicaForm.setValue("descrizioneCollocazioneFisica",
								record.getAttribute("descrizione"));
						collocazioneFisicaForm.setValue("noteCollocazioneFisica", record.getAttribute("note"));
					}

					@Override
					public void manageMultiLookupUndo(Record record) {

					}

					@Override
					public void manageMultiLookupBack(Record record) {

					}
				};
				lookupTopograficoPopup.show();
			}
		});

		salvaInTopograficoCollocazioneFisicaButton = new ImgButtonItem("salvaInTopograficoCollocazioneFisicaButton",
				"buttons/saveIn.png", I18NUtil.getMessages().protocollazione_detail_salvaInTopograficoButton_prompt());
		salvaInTopograficoCollocazioneFisicaButton.setColSpan(1);
		salvaInTopograficoCollocazioneFisicaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String nomeCollocazioneFisica = (String) nomeCollocazioneFisicaItem.getValue();
				Record lRecord = new Record();
				lRecord.setAttribute("codiceRapido", codRapidoCollocazioneFisicaItem.getValue());
				lRecord.setAttribute("nome", nomeCollocazioneFisicaItem.getValue());
				lRecord.setAttribute("descrizione", descrizioneCollocazioneFisicaItem.getValue());
				lRecord.setAttribute("note", noteCollocazioneFisicaItem.getValue());
				SalvaTopograficoPopup salvaTopograficoPopup = new SalvaTopograficoPopup(lRecord) {

					@Override
					public void manageLookupBack(Record record) {
						codRapidoCollocazioneFisicaItem.setValue(record.getAttribute("codiceRapido"));
						nomeCollocazioneFisicaItem.setValue(record.getAttribute("nome"));
						descrizioneCollocazioneFisicaItem.setValue(record.getAttribute("descrizione"));
						noteCollocazioneFisicaItem.setValue(record.getAttribute("note"));
						idToponimoItem.setValue(record.getAttribute("idTopografico"));
						collocazioneFisicaForm.clearErrors(true);
					}
				};
				salvaTopograficoPopup.show();
			}
		});
		salvaInTopograficoCollocazioneFisicaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return Layout.isPrivilegioAttivo("UT/TOP;I");
			}
		});

		collocazioneFisicaForm.setFields(idToponimoItem, idToponimoOutItem, codRapidoCollocazioneFisicaItem,
				lookupTopograficoCollocazioneFisicaButton, nomeCollocazioneFisicaItem,
				descrizioneCollocazioneFisicaItem, noteCollocazioneFisicaItem,
				salvaInTopograficoCollocazioneFisicaButton);
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Altri dati"
	 * 
	 */
	public String getTitleDetailSectionAltriDati() {
		return I18NUtil.getMessages().protocollazione_detail_altriDatiForm_title();
	}

	/**
	 * Metodo che crea la sezione "Altri dati"
	 * 
	 */
	protected void createDetailSectionAltriDati() {

		createAltriDatiForm();

		detailSectionAltriDati = new ProtocollazioneDetailSection(getTitleDetailSectionAltriDati(),
				true, false, false, altriDatiForm);
	}

	/**
	 * Metodo che crea il form della sezione "Altri dati"
	 * 
	 */
	protected void createAltriDatiForm() { // DIVERSO IN ATTI e ATTI2

		altriDatiForm = new DynamicForm();
		altriDatiForm.setValuesManager(vm);
		altriDatiForm.setWidth100();
		altriDatiForm.setPadding(5);
		altriDatiForm.setWrapItemTitles(false);
		altriDatiForm.setNumCols(10);
		altriDatiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		altriDatiForm.setTabSet(tabSet);
		altriDatiForm.setTabID("HEADER");

		// Data stesura
		dataDocumentoItem = new DateItem("dataDocumento",
				I18NUtil.getMessages().protocollazione_detail_dataDocumentoItem_title());
		dataDocumentoItem.setColSpan(1);
				
		// Note
		noteItem = new TextAreaItem("note", I18NUtil.getMessages().protocollazione_detail_noteItem_title());
		noteItem.setHeight(40);
		noteItem.setWidth(650);
		noteItem.setStartRow(true);
		noteItem.setColSpan(9);

		altriDatiForm.setFields(dataDocumentoItem, noteItem);
	}

	/**
	 * Metodo che indica se mostrare o meno il tab "Assegnazione e
	 * classificazione"
	 * 
	 */
	public boolean showTabAssegnazioneEClassificazione() {
		return true;
	}

	/**
	 * Metodo per costruire il tab "Assegnazione e classificazione"
	 * 
	 */
	protected void createTabAssegnazioneEClassificazione() {

		if (AurigaLayout.isAttivoClassifSenzaFasc(getFlgTipoProv())) {
			tabAssegnazioneEClassificazione = new Tab("<b>"
					+ I18NUtil.getMessages().protocollazione_detail_tabAssegnazioneEClassificazione_title() + "</b>");
			tabAssegnazioneEClassificazione.setAttribute("tabID", "ASSEGN_CLASSIF");
			tabAssegnazioneEClassificazione
					.setPrompt(I18NUtil.getMessages().protocollazione_detail_tabAssegnazioneEClassificazione_prompt());

		} else {
			tabAssegnazioneEClassificazione = new Tab("<b>"
					+ I18NUtil.getMessages().protocollazione_detail_tabAssegnazioneEFascicolazione_title() + "</b>");
			tabAssegnazioneEClassificazione.setAttribute("tabID", "ASSEGN_CLASSIF");
			tabAssegnazioneEClassificazione
					.setPrompt(I18NUtil.getMessages().protocollazione_detail_tabAssegnazioneEFascicolazione_prompt());
		}
		tabAssegnazioneEClassificazione.setPane(createTabPane(getLayoutAssegnazioneEClassificazione()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Assegnazione e classificazione"
	 * 
	 */
	public VLayout getLayoutAssegnazioneEClassificazione() {

		VLayout layoutAssegnazioneEClassificazione = new VLayout(5);

		createDetailSectionAssegnazione();
		layoutAssegnazioneEClassificazione.addMember(detailSectionAssegnazione);

		createDetailSectionCondivisione();
		layoutAssegnazioneEClassificazione.addMember(detailSectionCondivisione);

		createDetailSectionClassificazioneFascicolazione();
		layoutAssegnazioneEClassificazione.addMember(detailSectionClassificazioneFascicolazione);

		createDetailSectionFolderCustom();
		layoutAssegnazioneEClassificazione.addMember(detailSectionFolderCustom);

		return layoutAssegnazioneEClassificazione;
	}

	/**
	 * Metodo che indica se mostrare o meno il form di conferma assegnazione
	 * nella sezione "Assegnato a"
	 * 
	 */
	public boolean showConfermaAssegnazioneForm() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Assegna a"
	 * 
	 */
	public boolean showOpenDetailSectionAssegnazione() {
		return true;
	}

	/**
	 * Metodo che indica se la sezione "Assegna a" è obbligatoria
	 * 
	 */
	public boolean isRequiredDetailSectionAssegnazione() {
		return false;
	}

	/**
	 * Metodo che costruisce la sezione "Assegnato a"
	 * 
	 */
	protected void createDetailSectionAssegnazione() {

		createAssegnazioneForm();
		createConfermaAssegnazioneForm();

		List<DynamicForm> forms = new ArrayList<DynamicForm>();
		forms.add(assegnazioneForm);
		if (showConfermaAssegnazioneForm()) {
			forms.add(confermaAssegnazioneForm);
		}

		detailSectionAssegnazione = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_assegnazioneForm_title(), true, showOpenDetailSectionAssegnazione(), isRequiredDetailSectionAssegnazione(),
				forms.toArray(new DynamicForm[forms.size()])) {
			
			@Override
			public boolean validate() {
				//IMPORTANTE: in validazione listaAssegnazioni e listaAssegnazioniSalvate vanno considerate come un elemento unico, quindi i due controlli vanno messi in OR
				boolean validAssegnazione = assegnazioneItem != null && assegnazioneItem.getVisible() && assegnazioneItem.validate();
				boolean validAssegnazioneSalvata = assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible() && assegnazioneSalvataItem.validate();
				boolean valid = validAssegnazione || validAssegnazioneSalvata;
				if (showConfermaAssegnazioneForm()) {
					valid = confermaAssegnazioneForm.validate() && valid;
				}
				return valid;
			}
			
			@Override
			public boolean valuesAreValid() {
				//IMPORTANTE: in validazione listaAssegnazioni e listaAssegnazioniSalvate vanno considerate come un elemento unico, quindi i due controlli vanno messi in OR
				boolean validAssegnazione = assegnazioneItem != null && assegnazioneItem.getVisible() && assegnazioneItem.valuesAreValid();
				boolean validAssegnazioneSalvata = assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible() && assegnazioneSalvataItem.valuesAreValid();
				boolean valid = validAssegnazione || validAssegnazioneSalvata;
				if (showConfermaAssegnazioneForm()) {
					valid = confermaAssegnazioneForm.valuesAreValid(false) && valid;
				}
				return valid;
			}
		};
	}

	/**
	 * Metodo che costruisce il form di assegnazione della sezione "Assegnato a"
	 * 
	 */
	protected void createAssegnazioneForm() {
		
		createAssegnazioneItem();
		
		assegnazioneForm = new DynamicForm();
		assegnazioneForm.setValuesManager(vm);
		assegnazioneForm.setWidth("100%");
		assegnazioneForm.setHeight("5");
		assegnazioneForm.setPadding(5);
		assegnazioneForm.setTabSet(tabSet);
		if(showSingleTabHeader()) {
			assegnazioneForm.setTabID("HEADER");
		} else {
			assegnazioneForm.setTabID("ASSEGN_CLASSIF");
		}
		assegnazioneForm.setFields(assegnazioneSalvataItem, assegnazioneItem);
	}
	
	/**
	 * Metodo che crea l'item degli assegnatari
	 * 
	 */
	protected void createAssegnazioneItem() {
		
		assegnazioneSalvataItem = new AssegnazioneItem() {
			
			@Override
			public Boolean getShowRemoveButton() {			
				return true;
			}
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				if (getCanvas() != null) {
					final VLayout lVLayout = (VLayout) getCanvas();
					for (int i = 0; i < lVLayout.getMembers().length; i++) {
						Canvas lVLayoutMember = lVLayout.getMember(i);
						if (lVLayoutMember instanceof HLayout) {
							for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
								if (lHLayoutMember instanceof ReplicableCanvas) {
									ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
									lReplicableCanvas.setCanEdit(false);
								} else if (lHLayoutMember instanceof RemoveButton) {
									// se è un bottone di remove lo disabilito
									((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
									((RemoveButton) lHLayoutMember).show();
								}									
							}
						}
					}
				}	
			}
			
			@Override
			public boolean isAttivoAssegnatarioUnicoProt() {
				return isAttivoAssegnatarioUnico();
			}	
		};
		assegnazioneSalvataItem.setName("listaAssegnazioniSalvate");
		assegnazioneSalvataItem.setFlgUdFolder("U");
		assegnazioneSalvataItem.setShowTitle(false);
		assegnazioneSalvataItem.setStartRow(true);
		assegnazioneSalvataItem.setNotReplicable(true);
				
		assegnazioneItem = new AssegnazioneItem() {
			
			@Override
			public String getFinalitaOrganigrammaLookup() {
				String suffissoFinalita = "";
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					suffissoFinalita = "_ENTRATA";
				} 
				return super.getFinalitaOrganigrammaLookup() + suffissoFinalita;
			}
		
			@Override
			public String getFinalitaLoadComboOrganigramma() {
				String suffissoFinalita = "";
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					suffissoFinalita = "_ENTRATA";			
				}
				return super.getFinalitaLoadComboOrganigramma() + suffissoFinalita;
			}
			
			@Override
			public boolean isDimOrganigrammaNonStd() {
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST_E/ES");
				}
				return super.isDimOrganigrammaNonStd();
			}
			
			@Override
			public boolean isAttivoAssegnatarioUnicoProt() {
				return isAttivoAssegnatarioUnico();
			}
		};
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setFlgUdFolder("U");
		assegnazioneItem.setShowTitle(false);
		assegnazioneItem.setStartRow(true);
		if (isRequiredDetailSectionAssegnazione()) {
			assegnazioneItem.setAttribute("obbligatorio", true);
		}
		assegnazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				int nroMittentiAssegnatari = getNroMittentiAssegnatari();
				int nroDestinatariAssegnatari = getNroDestinatariAssegnatari();
				if(nroMittentiAssegnatari > 0 || nroDestinatariAssegnatari > 0) {
					assegnazioneItem.setAttribute("obbligatorio", false);
				} else if (assegnazioneSalvataItem != null && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
					assegnazioneItem.setAttribute("obbligatorio", false);
				} else {
					assegnazioneItem.setAttribute("obbligatorio", isRequiredDetailSectionAssegnazione());					
				}
				assegnazioneItem.storeValue(form.getValueAsRecordList(assegnazioneItem.getName()));
				return true;
			}
		});				
	}

	/**
	 * Metodo che costruisce il form di conferma assegnazione della sezione
	 * "Assegnato a"
	 * 
	 */
	protected void createConfermaAssegnazioneForm() {

		confermaAssegnazioneForm = new DynamicForm();
		confermaAssegnazioneForm.setValuesManager(vm);
		confermaAssegnazioneForm.setWidth("100%");
		confermaAssegnazioneForm.setHeight("5");
		confermaAssegnazioneForm.setPadding(5);
		confermaAssegnazioneForm.setTabSet(tabSet);
		if(showSingleTabHeader()) {
			confermaAssegnazioneForm.setTabID("HEADER");
		} else {
			confermaAssegnazioneForm.setTabID("ASSEGN_CLASSIF");
		}
		
		utentiAbilCPAValueMap = AurigaLayout.getUtentiAbilCPAValueMap();

		utentiAbilCPAItem = new SelectItem("utentiAbilCPA", "previa conferma di") {

			@Override
			public void onOptionClick(Record record) {
				confermaAssegnazioneForm.setValue("idUserConfermaAssegnazione",
						record.getAttributeAsString("idUtente"));
			}
		};
		utentiAbilCPAItem.setValueField("idUtente");
		utentiAbilCPAItem.setValueMap(utentiAbilCPAValueMap);
		utentiAbilCPAItem.setVisible(utentiAbilCPAValueMap.size() > 1);
		utentiAbilCPAItem.setWidth(180);
		utentiAbilCPAItem.setAllowEmptyValue(true);

		idUserConfermaAssegnazioneHiddenItem = new HiddenItem("idUserConfermaAssegnazione");

		flgPreviaConfermaAssegnazioneItem = new CheckboxItem("flgPreviaConfermaAssegnazione", "previa conferma");
		flgPreviaConfermaAssegnazioneItem.setWidth("*");
		flgPreviaConfermaAssegnazioneItem.setDefaultValue(false);
		flgPreviaConfermaAssegnazioneItem.setVisible(utentiAbilCPAValueMap.size() == 1);
		flgPreviaConfermaAssegnazioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (flgPreviaConfermaAssegnazioneItem.getValueAsBoolean()) {
					String key = utentiAbilCPAValueMap.keySet().iterator().next();
					idUserConfermaAssegnazioneHiddenItem.setValue(key);
				} else {
					idUserConfermaAssegnazioneHiddenItem.setValue((String) null);
				}
			}
		});

		confermaAssegnazioneForm.setFields(utentiAbilCPAItem, idUserConfermaAssegnazioneHiddenItem,
				flgPreviaConfermaAssegnazioneItem);
	}

	/**
	 * Metodo che costruisce la sezione "Inviato per conoscenza a"
	 * 
	 */
	protected void createDetailSectionCondivisione() {

		createCondivisioneForm();

		detailSectionCondivisione = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_condivisioneForm_title(), true, true, false,
				condivisioneForm);
	}
	
	/**
	 * Metodo che costruisce il form della sezione "Inviato per conoscenza a"
	 * 
	 */
	protected void createCondivisioneForm() {

		createCondivisioneItem();

		condivisioneForm = new DynamicForm();
		condivisioneForm.setValuesManager(vm);
		condivisioneForm.setWidth("100%");
		condivisioneForm.setHeight("5");
		condivisioneForm.setPadding(5);
		condivisioneForm.setTabSet(tabSet);
		if(showSingleTabHeader()) {
			condivisioneForm.setTabID("HEADER");
		} else {
			condivisioneForm.setTabID("ASSEGN_CLASSIF");	
		}
		condivisioneForm.setFields(condivisioneSalvataItem, condivisioneItem);
	}
	
	/**
	 * Metodo che crea l'item degli invii per conoscenza
	 * 
	 */
	protected void createCondivisioneItem() {
				
		condivisioneSalvataItem = new CondivisioneItem() {
			
			@Override
			public Boolean getShowRemoveButton() {			
				return true;
			}
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				if (getCanvas() != null) {
					final VLayout lVLayout = (VLayout) getCanvas();
					for (int i = 0; i < lVLayout.getMembers().length; i++) {
						Canvas lVLayoutMember = lVLayout.getMember(i);
						if (lVLayoutMember instanceof HLayout) {
							for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
								if (lHLayoutMember instanceof ReplicableCanvas) {
									ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
									lReplicableCanvas.setCanEdit(false);
								} else if (lHLayoutMember instanceof RemoveButton) {
									// se è un bottone di remove lo disabilito
									((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
									((RemoveButton) lHLayoutMember).show();
								}									
							}
						}
					}
				}	
			}
		};
		condivisioneSalvataItem.setName("listaDestInvioCCSalvati");
		condivisioneSalvataItem.setShowTitle(false);
		condivisioneSalvataItem.setFlgUdFolder("U");
		condivisioneSalvataItem.setStartRow(true);
		condivisioneSalvataItem.setNotReplicable(true);
		
		condivisioneItem = new CondivisioneItem() {
			
			@Override
			public String getFinalitaOrganigrammaLookup() {
				String suffissoFinalita = "";
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					suffissoFinalita = "_ENTRATA";
				} 
				return super.getFinalitaOrganigrammaLookup() + suffissoFinalita;
			}
		
			@Override
			public String getFinalitaLoadComboOrganigramma() {
				String suffissoFinalita = "";
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					suffissoFinalita = "_ENTRATA";			
				}
				return super.getFinalitaLoadComboOrganigramma() + suffissoFinalita;
			}
			
			@Override
			public boolean isDimOrganigrammaNonStd() {
				if(getFlgTipoProv() != null && "E".equals(getFlgTipoProv())) {
					return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST_E/ES");
				}
				return super.isDimOrganigrammaNonStd();
			}					
		};
		condivisioneItem.setName("listaDestInvioCC");
		condivisioneItem.setShowTitle(false);
		condivisioneItem.setFlgUdFolder("U");
		condivisioneItem.setStartRow(true);
	}

	/**
	 * Metodo che indica se è obbligatoria la sezione "Classificazione/fascicolazione"
	 * 
	 */
	public boolean isRequiredDetailSectionClassificazioneFascicolazione() {
		return false;
	}

	/**
	 * Metodo che effettua la validazione della sezione "Classificazione/fascicolazione"
	 * 
	 */
	public boolean validateDetailSectionClassificazioneFascicolazione() {
		return classificazioneFascicolazioneItem.validate();
	}
	
	/**
	 * Metodo che costruisce la sezione "Classificazione/fascicolazione"
	 * 
	 */
	protected void createDetailSectionClassificazioneFascicolazione() {

		createClassificazioneFascicolazioneForm();

		if (AurigaLayout.isAttivoClassifSenzaFasc(getFlgTipoProv())) {
			detailSectionClassificazioneFascicolazione = new ProtocollazioneDetailSection(
					I18NUtil.getMessages().protocollazione_detail_classificazioneFascicolazioneForm_title(), true, true,
					isRequiredDetailSectionClassificazioneFascicolazione(), classificazioneFascicolazioneForm) {
				
				@Override
				public boolean validate() {
					return validateDetailSectionClassificazioneFascicolazione();
				}
			};
		} else {
			detailSectionClassificazioneFascicolazione = new ProtocollazioneDetailSection(
					I18NUtil.getMessages().protocollazione_detail_fascicolazioneForm_title(), true, true,
					isRequiredDetailSectionClassificazioneFascicolazione(), classificazioneFascicolazioneForm) {
				
				@Override
				public boolean validate() {
					return validateDetailSectionClassificazioneFascicolazione();
				}
			};
		}
		
		detailSectionClassificazioneFascicolazione.setVisibility(showDetailSectionClassificazioneFascicolazione() ? Visibility.INHERIT : Visibility.HIDDEN);
	}

	/**
	 * Metodo che costruisce il form della sezione
	 * "Classificazione/fascicolazione"
	 * 
	 */
	protected void createClassificazioneFascicolazioneForm() {

		classificazioneFascicolazioneForm = new DynamicForm();
		classificazioneFascicolazioneForm.setValuesManager(vm);
		classificazioneFascicolazioneForm.setWidth("100%");
		classificazioneFascicolazioneForm.setHeight("5");
		classificazioneFascicolazioneForm.setPadding(5);
		classificazioneFascicolazioneForm.setTabSet(tabSet);
		if(showSingleTabHeader()) {
			classificazioneFascicolazioneForm.setTabID("HEADER");
		} else {
			classificazioneFascicolazioneForm.setTabID("ASSEGN_CLASSIF");
		}

		classificazioneFascicolazioneItem = new ClassificaFascicoloItem(){
			@Override
			public boolean skipValidation() {
				if(showDetailSectionClassificazioneFascicolazione()) {
					return super.skipValidation();
				}
				return true;
			}
		};
		classificazioneFascicolazioneItem.setName("listaClassFasc");
		classificazioneFascicolazioneItem.setShowTitle(false);
		if (isRequiredDetailSectionClassificazioneFascicolazione()) {
			classificazioneFascicolazioneItem.setAttribute("obbligatorio", true);
		}
		if (!AurigaLayout.isAttivoClassifSenzaFasc(getFlgTipoProv())) {
			classificazioneFascicolazioneItem.setFascicoloObbligatorio(true);
		}

		classificazioneFascicolazioneForm.setFields(classificazioneFascicolazioneItem);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione classificazione/fascicolazione
	 * 
	 */
	public boolean showDetailSectionClassificazioneFascicolazione() {
		return !AurigaLayout.getParametroDBAsBoolean("DISATTIVA_CLASSIFICAZIONE"); 
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Cartelle"
	 * 
	 */
	public boolean showDetailSectionFolderCustom() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_FOLDER_CUSTOM"); 
	}

	/**
	 * Metodo che restituisce il titolo della sezione "Cartelle"
	 * 
	 */
	public String getTitleDetailSectionFolderCustom() {
		return I18NUtil.getMessages().protocollazione_detail_folderCustomForm_title();
	}
	
	/**
	 * Metodo che costruisce la sezione "Cartelle"
	 * 
	 */
	protected void createDetailSectionFolderCustom() {

		createFolderCustomForm();

		detailSectionFolderCustom = new ProtocollazioneDetailSection(
				getTitleDetailSectionFolderCustom(), true, true, false,
				folderCustomForm);
		detailSectionFolderCustom.setVisibility(showDetailSectionFolderCustom() ? Visibility.INHERIT : Visibility.HIDDEN);
	}

	/**
	 * Metodo che costruisce il form della sezione "Cartelle"
	 * 
	 */
	protected void createFolderCustomForm() {

		folderCustomForm = new DynamicForm();
		folderCustomForm.setValuesManager(vm);
		folderCustomForm.setWidth("100%");
		folderCustomForm.setHeight("5");
		folderCustomForm.setPadding(5);
		folderCustomForm.setTabSet(tabSet);
		if(showSingleTabHeader()) {
			folderCustomForm.setTabID("HEADER");
		} else {
			folderCustomForm.setTabID("ASSEGN_CLASSIF");
		}

		folderCustomItem = new FolderCustomItem();
		folderCustomItem.setName("listaFolderCustom");
		folderCustomItem.setShowTitle(false);

		folderCustomForm.setFields(folderCustomItem);
	}
	
	/**
	 * Metodo che indica se mostrare o meno il tab "Esibente e interessati"
	 * 
	 */
	public boolean showTabEsibentiEInteressati() {
		return showDetailSectionEsibenti() || showDetailSectionInteressati();
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Esibente"
	 * 
	 */
	public boolean showDetailSectionEsibenti() {
		return isProtocollazioneDetailEntrata() && ProtocollazioneUtil.isAttivoEsibenteSenzaWizard();
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Interessati"
	 * 
	 */
	public boolean showDetailSectionInteressati() {
		return ProtocollazioneUtil.isAttivoInteressatiSenzaWizard();
	}	
	
	/**
	 * Metodo che indica se mostrare o meno il tab "Delegato e firmatari"
	 * 
	 */
	public boolean showTabDelegatoEFirmatari() {
		return showDetailSectionDelegato() || showDetailSectionFirmatari();
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Delegato"
	 * 
	 */
	public boolean showDetailSectionDelegato() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Firmatari"
	 * 
	 */
	public boolean showDetailSectionFirmatari() {
		return false;
	}

	/**
	 * Metodo che indica se mostrare o meno la sezione "Perizia"
	 * 
	 */
	public boolean showDetailSectionPerizia() {
		return AurigaLayout.isAttivoClienteADSP();		
	}
		
	/**
	 * Metodo per costruire il tab "Esibente e interessati" / "Interessati"
	 * 
	 */
	protected void createTabEsibentiEInteressati() {

		tabEsibentiEInteressati = new Tab("<b>" + (isProtocollazioneDetailEntrata()
				? I18NUtil.getMessages().protocollazione_detail_tabEsibentiEInteressati_title()
				: I18NUtil.getMessages().protocollazione_detail_tabInteressati_title()) + "</b>");
		tabEsibentiEInteressati.setAttribute("tabID", "ESIBENTI_INTERESSATI");
		tabEsibentiEInteressati.setPrompt(isProtocollazioneDetailEntrata()
				? I18NUtil.getMessages().protocollazione_detail_tabEsibentiEInteressati_prompt()
				: I18NUtil.getMessages().protocollazione_detail_tabInteressati_prompt());
		tabEsibentiEInteressati.setPane(createTabPane(getLayoutEsibentiEInteressati()));
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Esibente e interessati" /
	 * "Interessati"
	 * 
	 */
	public VLayout getLayoutEsibentiEInteressati() {

		VLayout layoutEsibentiEInteressati = new VLayout(5);

		if (showDetailSectionEsibenti()) {
			createDetailSectionEsibenti();
			layoutEsibentiEInteressati.addMember(detailSectionEsibenti);
		}

		if (showDetailSectionInteressati()) {
			createDetailSectionInteressati();
			layoutEsibentiEInteressati.addMember(detailSectionInteressati);
		}

		return layoutEsibentiEInteressati;
	}
	
	/**
	 * Metodo che crea la sezione "Esibente"
	 * 
	 */
	protected void createDetailSectionEsibenti() {

		createEsibentiForm();

		detailSectionEsibenti = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_esibentiForm_title(),
				true, true, false, esibentiForm);
	}

	/**
	 * Metodo che crea il form della sezione "Esibente"
	 * 
	 */
	protected void createEsibentiForm() {
		
		createEsibentiItem();

		esibentiForm = new DynamicForm();
		esibentiForm.setValuesManager(vm);
		esibentiForm.setWidth("100%");
		esibentiForm.setHeight("5");
		esibentiForm.setPadding(5);
		esibentiForm.setTabSet(tabSet);
		esibentiForm.setTabID("ESIBENTI_INTERESSATI");
		esibentiForm.setFields(esibentiItem);
	}
	
	/**
	 * Metodo che crea l'item dell'esibente
	 * 
	 */
	protected void createEsibentiItem() {
				
		esibentiItem = new EsibentiItem();
		esibentiItem.setName("listaEsibenti");
		esibentiItem.setShowTitle(false);
		esibentiItem.setNotReplicable(true);
	}

	/**
	 * Metodo che crea la sezione "Interessati"
	 * 
	 */
	protected void createDetailSectionInteressati() {

		createInteressatiForm();

		detailSectionInteressati = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_interessatiForm_title(), true, true, false,
				interessatiForm);
		detailSectionInteressati.setViewReplicableItemHeight(450);
	}

	/**
	 * Metodo che crea il form della sezione "Interessati"
	 * 
	 */
	protected void createInteressatiForm() {

		interessatiForm = new DynamicForm();
		interessatiForm.setValuesManager(vm);
		interessatiForm.setWidth("100%");
		interessatiForm.setHeight("5");
		interessatiForm.setPadding(5);
		interessatiForm.setTabSet(tabSet);
		interessatiForm.setTabID("ESIBENTI_INTERESSATI");

		interessatiItem = new InteressatiItem();
		interessatiItem.setName("listaInteressati");
		interessatiItem.setShowTitle(false);

		interessatiForm.setFields(interessatiItem);
	}
	
	/**
	 * Metodo per costruire il tab "Delegato e firmatari"
	 * 
	 */
	protected void createTabDelegatoEFirmatari() {

		tabDelegatoEFirmatari = new Tab("<b>" + I18NUtil.getMessages().protocollazione_detail_tabDelegatoEFirmatari_title() + "</b>");
		tabDelegatoEFirmatari.setAttribute("tabID", "DELEGATO_FIRMATARI");
		tabDelegatoEFirmatari.setPrompt(I18NUtil.getMessages().protocollazione_detail_tabDelegatoEFirmatari_prompt());
		tabDelegatoEFirmatari.setPane(createTabPane(getLayoutDelegatoEFirmatari()));
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Delegato e firmatari"
	 * 
	 */
	public VLayout getLayoutDelegatoEFirmatari() {

		VLayout layoutDelegatoEFirmatari = new VLayout(5);

		if (showDetailSectionDelegato()) {
			createDetailSectionDelegato();
			layoutDelegatoEFirmatari.addMember(detailSectionDelegato);
		}

		if (showDetailSectionFirmatari()) {
			createDetailSectionFirmatari();
			layoutDelegatoEFirmatari.addMember(detailSectionFirmatari);
		}

		return layoutDelegatoEFirmatari;
	}
	
	/**
	 * Metodo che crea la sezione "Delegato"
	 * 
	 */
	protected void createDetailSectionDelegato() {

		createDelegatoForm();

		detailSectionDelegato = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_delegatoForm_title(),
				true, true, false, delegatoForm);
	}

	/**
	 * Metodo che crea il form della sezione "Delegato"
	 * 
	 */
	protected void createDelegatoForm() {
		
		delegatoForm = new DynamicForm();
		delegatoForm.setValuesManager(vm);
		delegatoForm.setWidth("100%");
		delegatoForm.setHeight("5");
		delegatoForm.setPadding(5);
		delegatoForm.setTabSet(tabSet);
		delegatoForm.setTabID("DELEGATO_FIRMATARI");
		
		delegatoItem = new DelegatoItem() {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		delegatoItem.setName("listaDelegato");
		delegatoItem.setShowTitle(false);
		delegatoItem.setNotReplicable(true);
		
		delegatoForm.setFields(delegatoItem);
	}	

	/**
	 * Metodo che crea la sezione "Firmatari"
	 * 
	 */
	protected void createDetailSectionFirmatari() {

		createFirmatariForm();

		detailSectionFirmatari = new ProtocollazioneDetailSection(
				I18NUtil.getMessages().protocollazione_detail_firmatariForm_title(), true, true, false,
				firmatariForm);
		detailSectionFirmatari.setViewReplicableItemHeight(450);
	}

	/**
	 * Metodo che crea il form della sezione "Firmatari"
	 * 
	 */
	protected void createFirmatariForm() {

		firmatariForm = new DynamicForm();
		firmatariForm.setValuesManager(vm);
		firmatariForm.setWidth("100%");
		firmatariForm.setHeight("5");
		firmatariForm.setPadding(5);
		firmatariForm.setTabSet(tabSet);
		firmatariForm.setTabID("DELEGATO_FIRMATARI");

		firmatariItem = new FirmatariItem() {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		firmatariItem.setName("listaFirmatari");
		firmatariItem.setShowTitle(false);

		firmatariForm.setFields(firmatariItem);
	}
	
	/**
	 * Metodo che crea la sezione "Perizia"
	 * 
	 */
	protected void createDetailSectionPerizia() {

		createPeriziaForm();

		detailSectionPerizia = new ProtocollazioneDetailSection(I18NUtil.getMessages().protocollazione_detail_periziaForm_title(), true, true, false, periziaForm){
			@Override
			public boolean validate() {
				return validateDetailSectionPerizia();
			}
		};
	}

	
	/**
	 * Metodo che crea il form della sezione "Perizia"
	 * 
	 */
	protected void createPeriziaForm() {
		
		periziaForm = new DynamicForm();
		periziaForm.setValuesManager(vm);
		periziaForm.setWidth("100%");
		periziaForm.setHeight("5");
		periziaForm.setPadding(5);
		periziaForm.setTabSet(tabSet);
		periziaForm.setTabID("HEADER");
		
		periziaItem = new PeriziaItem();
		periziaItem.setName("listaPerizie");
		periziaItem.setShowTitle(false);
		periziaItem.setNotReplicable(false);	
		
		periziaForm.setFields(periziaItem);
	}
	
	
	/**
	 * Metodo che effettua la validazione della sezione "Perizia"
	 * 
	 */
	public boolean validateDetailSectionPerizia() {
		return periziaItem.validate();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il tab "Altre ubicazioni"
	 * 
	 */
	public boolean showTabAltreVie() {
		return showDetailSectionAltreVie();
	}

	/**
	 * Metodo che indica se mostrare o meno la sezione "Altre ubicazioni"
	 * 
	 */
	public boolean showDetailSectionAltreVie() {
		return ProtocollazioneUtil.isAttivoAltreVieSenzaWizard();
	}
	
	
	
	/**
	 * Metodo per costruire il tab "Altre vie"
	 * 
	 */
	protected void createTabAltreVie() {

		tabAltreVie = new Tab("<b>" + I18NUtil.getMessages().protocollazione_detail_tabAltreVie_title() + "</b>");
		tabAltreVie.setAttribute("tabID", "ALTRE_VIE");
		tabAltreVie.setPrompt(I18NUtil.getMessages().protocollazione_detail_tabAltreVie_prompt());
		tabAltreVie.setPane(createTabPane(getLayoutAltreVie()));
	}

	
	/**
	 * Metodo che restituisce il layout del tab "Altre vie"
	 * 
	 */
	public VLayout getLayoutAltreVie() {

		VLayout layoutAltreVie = new VLayout(5);

		if(showDetailSectionAltreVie()) {
			createDetailSectionAltreVie();
			layoutAltreVie.addMember(detailSectionAltreVie);
		}

		return layoutAltreVie;
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Altri ubicazioni"
	 * 
	 */
	public String getTitleDetailSectionAltreVie() {
		return I18NUtil.getMessages().protocollazione_detail_altreVieForm_title();
	}

	/**
	 * Metodo che indica se è obbligatoria la sezione "Altri ubicazioni"
	 * 
	 */
	public boolean isRequiredDetailSectionAltreVie() {
		return false;
	}
	
	/**
	 * Metodo che indica se mostrare già aperta la sezione "Altri ubicazioni"
	 * 
	 */
	public boolean showOpenDetailSectionAltreVie() {
		return true;
	}
	
	/**
	 * Metodo che indica se mostrare la sezione "Altri ubicazioni" dopo gli header (e gli eventuali attributi custom)
	 * 
	 */
	public boolean showDetailSectionAltreVieAfterHeader() {
		return false;
	}

	/**
	 * Metodo che crea la sezione "Altre ubicazioni"
	 * 
	 */
	protected void createDetailSectionAltreVie() {

		createAltreVieForm();

		detailSectionAltreVie = new ProtocollazioneDetailSection(getTitleDetailSectionAltreVie(), true, showOpenDetailSectionAltreVie(), isRequiredDetailSectionAltreVie(), altreVieForm);			
		if(!isNotReplicableAltreVieItem()) {
			detailSectionAltreVie.setViewReplicableItemHeight(450);			
		}
	}
	
	/**
	 * Metodo che indica se mostrare o meno il check "fuori comune" nella sezione
	 * 
	 */
	public boolean showFlgFuoriComuneInAltreVie() {
		return true;
	}
	
	/**
	 * Metodo che indica se la sezione contiene un indirizzo "fuori comune" oppure no
	 * 
	 */
	public boolean getFlgFuoriComuneInAltreVie() {
		return true;
	}
	
	/**
	 * Metodo che indica se la sezione contiene un singolo indirizzo
	 * 
	 */
	public boolean isNotReplicableAltreVieItem() {
		return false;
	}
		
	/**
	 * Metodo che crea il form del tab "Altre ubicazioni"
	 * 
	 */
	protected void createAltreVieForm() {

		altreVieForm = new DynamicForm();
		altreVieForm.setValuesManager(vm);
		altreVieForm.setWidth("100%");
		altreVieForm.setHeight("5");
		altreVieForm.setPadding(5);
		altreVieForm.setTabSet(tabSet);
		altreVieForm.setTabID("ALTRE_VIE");
		
		altreVieItem = new AltreVieItem() {
			
			@Override
			public boolean showFlgFuoriComune() {
				return showFlgFuoriComuneInAltreVie();
			}
			
			@Override
			public boolean getFlgFuoriComune() {
				return getFlgFuoriComuneInAltreVie();
			}
			
			@Override
			public boolean isIndirizzoObbligatorioInCanvas() {
				return isRequiredDetailSectionAltreVie();
			}
			
			@Override
			public void manageOnChanged() {
				manageOnChangedAltreVieItem();				
			}
			
			@Override
			public boolean getShowStato() {
				if(!showFlgFuoriComuneInAltreVie() && !getFlgFuoriComuneInAltreVie()) {
					return false;
				}
				return true;
			}
		};
		altreVieItem.setName("listaAltreVie");
		altreVieItem.setShowTitle(false);
		if (isRequiredDetailSectionAltreVie()) {
			altreVieItem.setAttribute("obbligatorio", true);
		}
		if (isNotReplicableAltreVieItem()) {
			altreVieItem.setNotReplicable(true);
		}
			
		altreVieForm.setFields(altreVieItem);
	}
	
	public void manageOnChangedAltreVieItem() {
		
	}
	
	/**
	 * Metodo che indica se mostrare o meno il tab "Dati pubblicazione"
	 * 
	 */
	public boolean showTabPubblicazione() {
		return showDetailSectionPubblicazione() || showDetailSectionRipubblicazione();
	}
	
	/**
	 * Metodo per costruire il tab "Dati pubblicazione"
	 * 
	 */
	public void createTabPubblicazione() {
		tabPubblicazione = new Tab("<b>" + I18NUtil.getMessages().protocollazione_detail_tabPubblicazione_title() + "</b>");
		tabPubblicazione.setAttribute("tabID", "PUBBL");
		tabPubblicazione.setPrompt(I18NUtil.getMessages().protocollazione_detail_tabPubblicazione_prompt());
		tabPubblicazione.setPane(createTabPane(getLayoutPubblicazione()));
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati pubblicazione"
	 * 
	 */
	public VLayout getLayoutPubblicazione() {

		VLayout layoutPubblicazione = new VLayout(5);

		if(showDetailSectionPubblicazione()) {
			createDetailSectionPubblicazione();
			layoutPubblicazione.addMember(detailSectionPubblicazione);
		}
		
		if(showDetailSectionRipubblicazione()) {
			createDetailSectionRipubblicazione();
			layoutPubblicazione.addMember(detailSectionRipubblicazione);
		}

		return layoutPubblicazione;
	}
	
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Pubblicazione"
	 * 
	 */
	public boolean showDetailSectionPubblicazione() {
		return false;
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Pubblicazione"
	 * 
	 */
	public String getTitleDetailSectionPubblicazione() {
		return I18NUtil.getMessages().protocollazione_detail_pubblicazioneForm_title();
	}
	
	/**
	 * Metodo che crea la sezione "Pubblicazione"
	 * 
	 */
	public void createDetailSectionPubblicazione() {
		
		createPubblicazioneForm();

		detailSectionPubblicazione = new ProtocollazioneDetailSection(getTitleDetailSectionPubblicazione(), true, true, false, pubblicazioneForm);					
	}
	
	/**
	 * Metodo che crea il form della sezione "Pubblicazione"
	 * @param <idRichPubblHiddenItem>
	 * 
	 */
	public <idRichPubblHiddenItem> void createPubblicazioneForm() {

		pubblicazioneForm = new DynamicForm();
		pubblicazioneForm.setValuesManager(vm);
		pubblicazioneForm.setWidth("100%");
		pubblicazioneForm.setHeight("5");
		pubblicazioneForm.setPadding(5);
		pubblicazioneForm.setNumCols(22);
		pubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");		
		pubblicazioneForm.setWrapItemTitles(false);
		pubblicazioneForm.setTabSet(tabSet);
		pubblicazioneForm.setTabID("PUBBL");
		
		idPubblicazioneHiddenItem = new HiddenItem("idPubblicazione");
		
		nroPubblicazioneItem = new TextItem("nroPubblicazione", setTitleAlign("N°", 60)) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		nroPubblicazioneItem.setWidth(100);
		nroPubblicazioneItem.setColSpan(1);
		nroPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				nroPubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		dataPubblicazioneItem = new DateItem("dataPubblicazione", "del") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataPubblicazioneItem.setWidth(80);
		dataPubblicazioneItem.setColSpan(1);
		dataPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				dataPubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		dataDalPubblicazioneItem = new DateItem("dataDalPubblicazione", "dal") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataDalPubblicazioneItem.setWidth(80);
		dataDalPubblicazioneItem.setColSpan(1);
		dataDalPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				dataDalPubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		dataAlPubblicazioneItem = new DateItem("dataAlPubblicazione", "al") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataAlPubblicazioneItem.setWidth(80);
		dataAlPubblicazioneItem.setColSpan(1);
		dataAlPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				dataAlPubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		richiestaDaPubblicazioneItem = new TextItem("richiestaDaPubblicazione", "Effettutata da") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		richiestaDaPubblicazioneItem.setWidth(200);
		richiestaDaPubblicazioneItem.setColSpan(1);
		richiestaDaPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				richiestaDaPubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		statoPubblicazioneItem = new TextItem("statoPubblicazione", "Stato") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		statoPubblicazioneItem.setWidth(150);
		statoPubblicazioneItem.setColSpan(1);
		statoPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				statoPubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		formaPubblicazioneItem = new TextItem("formaPubblicazione", "Forma") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		formaPubblicazioneItem.setWidth(150);
		formaPubblicazioneItem.setColSpan(1);
		formaPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				formaPubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		rettificataDaPubblicazioneItem = new TextItem("rettificataDaPubblicazione", "Rettificata dalla pubbl.") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		rettificataDaPubblicazioneItem.setWidth(650);
		rettificataDaPubblicazioneItem.setStartRow(true);
		rettificataDaPubblicazioneItem.setColSpan(20);
		rettificataDaPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				rettificataDaPubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		motivoRettificaPubblicazioneItem = new TextAreaItem("motivoRettificaPubblicazione", "Motivo rettifica") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		motivoRettificaPubblicazioneItem.setHeight(40);
		motivoRettificaPubblicazioneItem.setWidth(650);
		motivoRettificaPubblicazioneItem.setStartRow(true);
		motivoRettificaPubblicazioneItem.setColSpan(20);
		motivoRettificaPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				motivoRettificaPubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		motivoAnnullamentoPubblicazioneItem = new TextAreaItem("motivoAnnullamentoPubblicazione", "Motivo annullamento") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		motivoAnnullamentoPubblicazioneItem.setHeight(40);
		motivoAnnullamentoPubblicazioneItem.setWidth(650);
		motivoAnnullamentoPubblicazioneItem.setStartRow(true);
		motivoAnnullamentoPubblicazioneItem.setColSpan(20);
		motivoAnnullamentoPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				motivoAnnullamentoPubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		proroghePubblicazioneItem = new TextAreaItem("proroghePubblicazione", setTitleAlign("Proroghe", 60)) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		proroghePubblicazioneItem.setHeight(80);
		proroghePubblicazioneItem.setWidth(650);
		proroghePubblicazioneItem.setStartRow(true);
		proroghePubblicazioneItem.setColSpan(20);
		proroghePubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				proroghePubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		ImgButtonItem iconaPubblicazioneItem = new ImgButtonItem("iconaPubblicazione", "buttons/richiesta_pubblicazione.png", "Accedi al dettaglio pubblicazione");
		iconaPubblicazioneItem.setAlwaysEnabled(true);
		iconaPubblicazioneItem.setColSpan(1);
		iconaPubblicazioneItem.setIconWidth(16);
		iconaPubblicazioneItem.setIconHeight(16);
		iconaPubblicazioneItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaPubblicazioneItem.setAlign(Alignment.LEFT);
		iconaPubblicazioneItem.setWidth(16);
		iconaPubblicazioneItem.setRedrawOnChange(true);
		iconaPubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (idPubblicazioneHiddenItem.getValue() != null && !idPubblicazioneHiddenItem.getValue().equals(""));
			}
		});
		iconaPubblicazioneItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUdFolder", idUd);
				lRecordToLoad.setAttribute("idRichPubbl", idPubblicazioneHiddenItem.getValue());
				lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordDettaglio = response.getData()[0];
							DettaglioRichiestaPubblicazioneWindow lDettaglioRichiestaPubblicazioneWindow = new DettaglioRichiestaPubblicazioneWindow(recordDettaglio);
							lDettaglioRichiestaPubblicazioneWindow.show();
						}
					}
				});				
			}
		});

		pubblicazioneForm.setFields(
			idPubblicazioneHiddenItem,
			nroPubblicazioneItem,
			dataPubblicazioneItem,
			dataDalPubblicazioneItem,
			dataAlPubblicazioneItem,
			richiestaDaPubblicazioneItem,
			statoPubblicazioneItem,
			formaPubblicazioneItem,
			iconaPubblicazioneItem,
			rettificataDaPubblicazioneItem,
			motivoRettificaPubblicazioneItem,
			motivoAnnullamentoPubblicazioneItem,
			proroghePubblicazioneItem	
		);
	}
	
	/**
	 * Metodo che indica se mostrare o meno la sezione "Ripubblicazione"
	 * 
	 */
	public boolean showDetailSectionRipubblicazione() {
		return false;
	}
	
	/**
	 * Metodo che restituisce il titolo della sezione "Ripubblicazione"
	 * 
	 */
	public String getTitleDetailSectionRipubblicazione() {
		return I18NUtil.getMessages().protocollazione_detail_ripubblicazioneForm_title();
	}
	
	/**
	 * Metodo che crea la sezione "Ripubblicazione"
	 * 
	 */
	public void createDetailSectionRipubblicazione() {
		
		createRipubblicazioneForm();

		detailSectionRipubblicazione = new ProtocollazioneDetailSection(getTitleDetailSectionRipubblicazione(), true, true, false, ripubblicazioneForm);						
	}
	
	/**
	 * Metodo che crea il form della sezione "Ripubblicazione"
	 * 
	 */
	public void createRipubblicazioneForm() {

		ripubblicazioneForm = new DynamicForm();
		ripubblicazioneForm.setValuesManager(vm);
		ripubblicazioneForm.setWidth("100%");
		ripubblicazioneForm.setHeight("5");
		ripubblicazioneForm.setPadding(5);
		ripubblicazioneForm.setNumCols(22);
		ripubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");		
		ripubblicazioneForm.setWrapItemTitles(false);
		ripubblicazioneForm.setTabSet(tabSet);
		ripubblicazioneForm.setTabID("PUBBL");
		
		idRipubblicazioneHiddenItem = new HiddenItem("idRipubblicazione");
		
		nroRipubblicazioneItem = new TextItem("nroRipubblicazione", setTitleAlign("N°", 60)) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		nroRipubblicazioneItem.setWidth(100);
		nroRipubblicazioneItem.setColSpan(1);
		nroRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				nroRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		dataRipubblicazioneItem = new DateItem("dataRipubblicazione", "del") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataRipubblicazioneItem.setWidth(80);
		dataRipubblicazioneItem.setColSpan(1);
		dataRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				dataRipubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		dataDalRipubblicazioneItem = new DateItem("dataDalRipubblicazione", "dal") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataDalRipubblicazioneItem.setWidth(80);
		dataDalRipubblicazioneItem.setColSpan(1);
		dataDalRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				dataDalRipubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		dataAlRipubblicazioneItem = new DateItem("dataAlRipubblicazione", "al") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataAlRipubblicazioneItem.setWidth(80);
		dataAlRipubblicazioneItem.setColSpan(1);
		dataAlRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				dataAlRipubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		richiestaDaRipubblicazioneItem = new TextItem("richiestaDaRipubblicazione", "Effettutata da") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		richiestaDaRipubblicazioneItem.setWidth(200);
		richiestaDaRipubblicazioneItem.setColSpan(1);
		richiestaDaRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				richiestaDaRipubblicazioneItem.setCanEdit(false);
				return value != null;
			}
		});
		
		statoRipubblicazioneItem = new TextItem("statoRipubblicazione", "Stato") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		statoRipubblicazioneItem.setWidth(150);
		statoRipubblicazioneItem.setColSpan(1);
		statoRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				statoRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		formaRipubblicazioneItem = new TextItem("formaRipubblicazione", "Forma") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		formaRipubblicazioneItem.setWidth(150);
		formaRipubblicazioneItem.setColSpan(1);
		formaRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				formaRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		rettificataDaRipubblicazioneItem = new TextItem("rettificataDaRipubblicazione", "Rettificata dalla pubbl.") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		rettificataDaRipubblicazioneItem.setWidth(650);
		rettificataDaRipubblicazioneItem.setStartRow(true);
		rettificataDaRipubblicazioneItem.setColSpan(20);
		rettificataDaRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				rettificataDaRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		motivoRettificaRipubblicazioneItem = new TextAreaItem("motivoRettificaRipubblicazione", "Motivo rettifica") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		motivoRettificaRipubblicazioneItem.setHeight(40);
		motivoRettificaRipubblicazioneItem.setWidth(650);
		motivoRettificaRipubblicazioneItem.setStartRow(true);
		motivoRettificaRipubblicazioneItem.setColSpan(20);
		motivoRettificaRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				motivoRettificaRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		motivoAnnullamentoRipubblicazioneItem = new TextAreaItem("motivoAnnullamentoRipubblicazione", "Motivo annullamento") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		motivoAnnullamentoRipubblicazioneItem.setHeight(40);
		motivoAnnullamentoRipubblicazioneItem.setWidth(650);
		motivoAnnullamentoRipubblicazioneItem.setStartRow(true);
		motivoAnnullamentoRipubblicazioneItem.setColSpan(20);
		motivoAnnullamentoRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				motivoAnnullamentoRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		prorogheRipubblicazioneItem = new TextAreaItem("prorogheRipubblicazione", setTitleAlign("Proroghe", 60)) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		prorogheRipubblicazioneItem.setHeight(80);
		prorogheRipubblicazioneItem.setWidth(650);
		prorogheRipubblicazioneItem.setStartRow(true);
		prorogheRipubblicazioneItem.setColSpan(20);
		prorogheRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				prorogheRipubblicazioneItem.setCanEdit(false);
				return value != null && !"".equals((String) value);
			}
		});
		
		ImgButtonItem iconaRipubblicazioneItem = new ImgButtonItem("iconaRipubblicazione", "buttons/richiesta_pubblicazione.png", "Accedi al dettaglio ri-pubblicazione");
		iconaRipubblicazioneItem.setAlwaysEnabled(true);
		iconaRipubblicazioneItem.setColSpan(1);
		iconaRipubblicazioneItem.setIconWidth(16);
		iconaRipubblicazioneItem.setIconHeight(16);
		iconaRipubblicazioneItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaRipubblicazioneItem.setAlign(Alignment.LEFT);
		iconaRipubblicazioneItem.setWidth(16);
		iconaRipubblicazioneItem.setRedrawOnChange(true);
		iconaRipubblicazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (idRipubblicazioneHiddenItem.getValue() != null && !idRipubblicazioneHiddenItem.getValue().equals(""));
			}
		});
		iconaRipubblicazioneItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUdFolder", idUd);
				lRecordToLoad.setAttribute("idRichPubbl", idRipubblicazioneHiddenItem.getValue());
				lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordDettaglio = response.getData()[0];
							DettaglioRichiestaPubblicazioneWindow lDettaglioRichiestaPubblicazioneWindow = new DettaglioRichiestaPubblicazioneWindow(recordDettaglio);
							lDettaglioRichiestaPubblicazioneWindow.show();
						}
					}
				});
			}
		});

		ripubblicazioneForm.setFields(
			idRipubblicazioneHiddenItem,
			nroRipubblicazioneItem,
			dataRipubblicazioneItem,
			dataDalRipubblicazioneItem,
			dataAlRipubblicazioneItem,
			richiestaDaRipubblicazioneItem,
			statoRipubblicazioneItem,
			formaRipubblicazioneItem,
			iconaRipubblicazioneItem,
			rettificataDaRipubblicazioneItem,	
			motivoRettificaRipubblicazioneItem,
			motivoAnnullamentoRipubblicazioneItem,
			prorogheRipubblicazioneItem
		);
	}
	
	/**
	 * Metodo che indica se mi trovo nel dettaglio UD di un atto.
	 * 
	 */
	public boolean isDettaglioUdAtto() {
		return false;
	}

	/**
	 * Metodo che indica se mostrare o meno a maschera gli attributi dinamici
	 * del documento. Gli attributi vengono raggruppati in vari tab a seconda
	 * del gruppo a cui appartengono. Se il codice del gruppo inizia con HEADER_
	 * vengono messi nel primo tab, sotto le sezioni di intestazione.
	 * 
	 */
	public boolean showAttributiDinamiciDoc() {
		if(isTaskDetail()) {
			return false;
		}
		if(isProtocollazioneDetailAtti()) {
			return isDettaglioUdAtto();
		}
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI");
	}

	/**
	 * Metodo per costruire i tab relativi ai gruppi degli attributi dinamici
	 * del documento
	 * 
	 */
	protected void caricaAttributiDinamiciDoc(final String idTipoDocumento, final String rowidDoc) {

		if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
			Record lRecordLoad = new Record();
			lRecordLoad.setAttribute("idTipoDocumento", idTipoDocumento);
			new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTipoDocDataSource").call(lRecordLoad,
					new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							final boolean isReload = (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0);
							attributiAddDocTabs = (LinkedHashMap<String, String>) object
									.getAttributeAsMap("gruppiAttributiCustomTipoDoc");
							attributiAddDocLayouts = new HashMap<String, VLayout>();
							attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
							if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
								GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
										"AttributiDinamiciDatasource");
								lGwtRestService.addParam("flgNomeAttrConSuff", "true");
								// se sono nel dettaglio UD di un atto non devo caricare tutti gli attributi dinamici che vedo nei passi dell'iter, ma solo una parte modificabili anche da dettaglio UD
								if(isDettaglioUdAtto()) {		
									lGwtRestService.addParam("flgDettaglioUdAtto", "true");
								}
								Record lAttributiDinamiciRecord = new Record();
								lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS+UD");
								lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
								lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDocumento);
								lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
										if (attributiAdd != null && !attributiAdd.isEmpty()) {
											for (final String key : attributiAddDocTabs.keySet()) {
												RecordList attributiAddCategoria = new RecordList();
												for (int i = 0; i < attributiAdd.getLength(); i++) {
													Record attr = attributiAdd.get(i);
													if (attr.getAttribute("categoria") != null
															&& (attr.getAttribute("categoria").equalsIgnoreCase(key)
																	|| ("HEADER_" + attr.getAttribute("categoria"))
																			.equalsIgnoreCase(key))) {
														attributiAddCategoria.add(attr);
													}
												}
												if (!attributiAddCategoria.isEmpty()) {
													if(key.equals("#HIDDEN")) {
														// Gli attributi che fanno parte di questo gruppo non li considero
													} else if (key.startsWith("HEADER_")) {
														AttributiDinamiciDetail detail = new AttributiDinamiciDetail(
																"attributiDinamici", attributiAddCategoria,
																object.getAttributeAsMap("mappaDettAttrLista"),
																object.getAttributeAsMap("mappaValoriAttrLista"),
																object.getAttributeAsMap("mappaVariazioniAttrLista"),
																object.getAttributeAsMap("mappaDocumenti"), null,
																tabSet, "HEADER") {
															
															@Override
															public void customizeItem(FormItem item, AttributiDinamiciDetail detail) {
																customizeAttributiDinamiciItem(item, detail);
															}
															
															@Override
															public boolean isOpenableSection(DetailSection detailSection) {
																return isOpenableDetailSection(detailSection);
															}

															@Override
															public String getOpenErrorMessageSection(DetailSection detailSection) {
																return getOpenErrorMessageDetailSection(detailSection);
															}
															
															public void manageOnChangedRequiredItem(FormItem item) {
																manageOnChangedRequiredAttrDinamicoItemInHeaderSection(item);
															};
														};
														detail.setCanEdit(new Boolean(editing || isModificaDatiExtraIter()));
														attributiAddDocDetails.put(key, detail);
														VLayout layoutFirstTab = (VLayout) tabSet.getTab(0).getPane();
														VLayout layout = (VLayout) layoutFirstTab.getMembers()[0];
														attributiAddDocLayouts.put(key, layout);
														int pos = 0;
														for (Canvas member : layout.getMembers()) {
															if (member instanceof HeaderDetailSection) {
																pos++;
															} else {
																break;
															}
														}
														for (DetailSection detailSection : attributiAddDocDetails
																.get(key).getDetailSections()) {
															if (isReload) {																
																DetailSection detailSectionToReload = ((DetailSection) layout.getMember(pos++));
																detailSectionToReload.setForms(detailSection.getForms());
															} else {																
																layout.addMember(detailSection, pos++);
															}
														}
													} else {
														AttributiDinamiciDetail detail = new AttributiDinamiciDetail(
																"attributiDinamici", attributiAddCategoria,
																object.getAttributeAsMap("mappaDettAttrLista"),
																object.getAttributeAsMap("mappaValoriAttrLista"),
																object.getAttributeAsMap("mappaVariazioniAttrLista"),
																object.getAttributeAsMap("mappaDocumenti"), null,
																tabSet, key) {
															
															@Override
															public void customizeItem(FormItem item, AttributiDinamiciDetail detail) {
																customizeAttributiDinamiciItem(item, detail);
															}
														};
														detail.setCanEdit(new Boolean(editing || isModificaDatiExtraIter()));														
														attributiAddDocDetails.put(key, detail);
														VLayout layout = new VLayout();
														layout.setHeight100();
														layout.setWidth100();
														layout.setMembers(detail);
														attributiAddDocLayouts.put(key, layout);
														VLayout layoutTab = new VLayout();
														layoutTab.addMember(layout);
														if (tabSet.getTabWithID(key) != null) {
															tabSet.getTabWithID(key).setPane(layoutTab);
														} else {
															Tab tab = new Tab(
																	"<b>" + attributiAddDocTabs.get(key) + "</b>");
															tab.setAttribute("tabID", key);
															tab.setPrompt(attributiAddDocTabs.get(key));
															tab.setPane(layoutTab);
															tabSet.addTab(tab);
														}
													}
												}
											}
											afterCaricaAttributiDinamiciDoc();
										}
									}
								});
							}
						}
					});
		}
	}
	
	/**
	 * Metodo che viene richiamato quando cambia il valore di un attributo dinamico (obbligatorio) in una delle sezioni del tab principale
	 * 
	 */
	public void manageOnChangedRequiredAttrDinamicoItemInHeaderSection(FormItem item) {
		
	}

	/**
	 * Metodo che viene richiamato alla fine del caricamento degli attributi
	 * dinamici del documento
	 * 
	 */
	protected void afterCaricaAttributiDinamiciDoc() {

		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
			attributiDinamiciDocDaCopiare = null;
		}
	}

	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento dopo l'azione del bottone "Nuova protocollazione come copia"
	 * 
	 */
	protected void setAttributiDinamiciDocDaCopiare(Map<String, Object> attributiDinamiciDocDaCopiare) {

		this.attributiDinamiciDocDaCopiare = attributiDinamiciDocDaCopiare;
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
		}
	}

	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento
	 * 
	 */
	protected void setAttributiDinamiciDoc(Map<String, Object> valori) {

		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).editNewRecord(valori);
				}
			}
		}
	}

	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici 
	 * associati al documento, nella modalità per il caricamento dei dati a maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NOMI delle colonne 
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDocForLoad() {

		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(detailRecord.toMap());
				}
			}
		}
		return attributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici
	 * associati al documento, nella modalità per il salvataggio dei dati da maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NUMERI delle colonne
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDoc() {

		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciDoc;
	}

	/**
	 * Metodo per il recupero da maschera dei tipi degli attributi dinamici
	 * associati al documento
	 * 
	 */
	public Map<String, String> getTipiAttributiDinamiciDoc() {

		Map<String, String> tipiAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (tipiAttributiDinamiciDoc == null) {
						tipiAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciDoc;
	}

	/**
	 * Metodo che serve ad allineare i titoli dei campi all'inizio della riga
	 * 
	 */
	protected String setTitleAlign(String title, int width) {
		return "<span style=\"width: " + width + "px; display: inline-block;\">" + title + "</span>";
	}

	/**
	 * Metodo che effettua il redraw del titolo della finestra
	 * 
	 */
	public void redrawTitleOfPortlet() {

		if (getLayout() != null) {
			getLayout().redrawTitleOfPortlet();
		}
	}
	
	/**
	 * Metodo che mi restituisce gli estremi del documento
	 * 
	 */
	public String getTipoEstremiDocumento() {
		String estremi = "";
		if (this instanceof ProtocollazioneDetailAtti) {
			if (siglaProtocolloItem.getValue() != null && !"".equals(siglaProtocolloItem.getValue())) {
				estremi += siglaProtocolloItem.getValue() + " ";
			}
			if (nroProtocolloItem.getValue() != null && !"".equals(nroProtocolloItem.getValue())) {
				estremi += nroProtocolloItem.getValue() + " ";
			}
			if (subProtocolloItem.getValue() != null && !"".equals(subProtocolloItem.getValue())) {
				estremi += "sub " + subProtocolloItem.getValue() + " ";
			}
			if (dataProtocolloItem.getValue() != null && !"".equals(dataProtocolloItem.getValue())) {
				estremi += "del " + DateUtil.format((Date) dataProtocolloItem.getValue());
			}
		} else {
			if (tipoProtocolloItem.getValue() != null && !"".equals(tipoProtocolloItem.getValue())) {
				if ("NI".equals(tipoProtocolloItem.getValue())) {
					estremi += "bozza ";
				} else if ("PP".equals(tipoProtocolloItem.getValue())) {
					estremi += "Prot. ";
				} else {
					estremi += tipoProtocolloItem.getValue() + " ";
				}
			}
			if (siglaProtocolloItem.getValue() != null && !"".equals(siglaProtocolloItem.getValue())) {
				estremi += siglaProtocolloItem.getValue() + " ";
			}
			if (nroProtocolloItem.getValue() != null && !"".equals(nroProtocolloItem.getValue())) {
				estremi += nroProtocolloItem.getValue() + " ";
			}
			if (subProtocolloItem.getValue() != null && !"".equals(subProtocolloItem.getValue())) {
				estremi += "sub " + subProtocolloItem.getValue() + " ";
			}
			if (dataProtocolloItem.getValue() != null && !"".equals(dataProtocolloItem.getValue())) {
				estremi += "del " + DateUtil.format((Date) dataProtocolloItem.getValue());
			}			
		}
		return estremi;
	}

	/**
	 * Metodo che effettua il refresh degli indici di tabulazione relativi ai
	 * campi della maschera
	 * 
	 */
	@Override
	public void refreshTabIndex() {
		if(it.eng.utility.ui.module.layout.client.Layout.isAttivoRefreshTabIndex()) {
			int tabIndex = 0;
			if (getNomeEntita() != null && Layout.getOpenedPortletIndex(getNomeEntita()) > 0) {
				tabIndex = (Layout.getOpenedPortletIndex(getNomeEntita()) * 1000000);
			}
			tabIndex++;
			if (modelliSelectItem != null) {
				if (!AurigaLayout.getIsAttivaAccessibilita()) {
				modelliSelectItem.setTabIndex(0);
				}
				modelliSelectItem.setGlobalTabIndex(0);
				CustomDetail.showItemTabIndex(modelliSelectItem);
			}
			tabIndex++;
			if (uoProtocollanteSelectItem != null) {
				if (!AurigaLayout.getIsAttivaAccessibilita()) {
				uoProtocollanteSelectItem.setTabIndex(0);
				}				
				uoProtocollanteSelectItem.setGlobalTabIndex(0);
				CustomDetail.showItemTabIndex(uoProtocollanteSelectItem);
			}
			refreshTabIndex(++tabIndex);
		}
	}
	
	/**
	 * Metodo che recupera tutti i form della maschera
	 * 
	 */
	public List<DynamicForm> getAllDetailForms() {
		List<DynamicForm> allDetailForms = super.getAllDetailForms();
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				for (DynamicForm form : detail.getForms()) {
					allDetailForms.add(form);
				}
			}
		}
		return allDetailForms;
	}

	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors(TabSet tabSet) {
		super.showTabErrors(tabSet);
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	
	
	/**
	 * Metodo per effettuare delle customizzazioni sugli item delle maschere dinamiche
	 * 
	 */
	public void customizeAttributiDinamiciItem(final FormItem item, final AttributiDinamiciDetail detail) {
		if(item.getName() != null && "RICH_ACCESSO_CIVICO_ESITO_MAIN_Doc".equals(item.getName())) {	
			item.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					for (DynamicForm form : detail.getForms()) {
						form.markForRedraw();
					}
				}
			});	
		} else if(item.getName() != null && "RICH_ACCESSO_CIVICO_ESITO_DETTAGLI_Doc".equals(item.getName())) {
			item.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					Record values = detail.getRecordToSave();
					String esitoMain = values.getAttributeAsString("RICH_ACCESSO_CIVICO_ESITO_MAIN_Doc");
					String labelOrig = item.getAttribute("labelOrig");
					if(esitoMain != null && ("accoglimento parziale".equalsIgnoreCase(esitoMain) || "attesa di chiarimenti".equalsIgnoreCase(esitoMain))) {
						item.setAttribute("obbligatorio", true);
						if (labelOrig.startsWith("<span")) {
	 						int start = labelOrig.indexOf(">") + 1;
	 						int end = labelOrig.indexOf("</span>", start);
	 						String realTitle = labelOrig.substring(start, end);	 						
	 						String title = labelOrig.substring(0, start);
	 						title += FrontendUtil.getRequiredFormItemTitle(realTitle); 						
	 						title += labelOrig.substring(end);
	 						item.setTitle(title);
	 					} else {
	 						item.setTitle(FrontendUtil.getRequiredFormItemTitle(labelOrig)); 						 						
	 					}						
					} else {
						item.setAttribute("obbligatorio", false);
						item.setTitle(labelOrig);
					}
					detail.alignFormItemTitleWidth(true);
					return true;
				}
			});	
			item.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					Record values = detail.getRecordToSave();
					String esitoMain = values.getAttributeAsString("RICH_ACCESSO_CIVICO_ESITO_MAIN_Doc");
					return esitoMain != null && ("accoglimento parziale".equalsIgnoreCase(esitoMain) || "attesa di chiarimenti".equalsIgnoreCase(esitoMain));																								
				}
			}));
		}
	}
	
	/**
	 * Metodo che effettua la validazione dei dati in maschera
	 * 
	 */
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		if (filePrimarioForm != null) {
			if (filePrimarioForm.getValue("uriFilePrimario") != null && !"".equals(filePrimarioForm.getValue("uriFilePrimario"))) {
				InfoFileRecord infoFilePrimario = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				if (infoFilePrimario == null || infoFilePrimario.getMimetype() == null || infoFilePrimario.getMimetype().equals("")) {
					filePrimarioForm.setFieldErrors("nomeFilePrimario", "Formato file non riconosciuto");
					valid = false;
				}
				if (infoFilePrimario.getBytes() <= 0) {
					filePrimarioForm.setFieldErrors("nomeFilePrimario", "Il file ha dimensione 0");
					valid = false;
				}
			} 
//			else if(getFlgTipoProv() != null && !"".equals(getFlgTipoProv()) && isObbligatorioNoteXRegSenzaFilePrimario()) {
//				if(noteItem != null) {
//					if (noteItem.getValueAsString() == null || "".equals(noteItem.getValueAsString().trim())) {
//						altriDatiForm.setFieldErrors("note", "Devi indicare nelle note la motivazione per cui non viene caricato il file principale");
//						valid = false;
//					}
//				}
//			}
		}	
		if(fileAllegatiItem != null) {
			if(fileAllegatiItem instanceof AllegatiGridItem) {
				valid = ((AllegatiGridItem)fileAllegatiItem).validate() && valid;
			}
		}
		valid = validateAttributiDinamiciDoc() && valid;
		if (uoProtocollanteSelectItem != null) {
			valid = (!showUoProtocollanteSelectItem() || !mainToolStrip.isVisible() || uoProtocollanteSelectItem.validate()) && valid;
		}	
		return valid;
	}
	
	public boolean validateAttributiDinamiciDoc() {
		boolean valid = true;
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				if(!detail.customValidate()) {
					valid = false;
				}
				for (DynamicForm form : detail.getForms()) {
					form.clearErrors(true);
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							boolean itemValid = lReplicableItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
									lReplicableItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							boolean itemValid = lIDocumentItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lIDocumentItem != null && lIDocumentItem.getForm() != null && lIDocumentItem.getForm().getDetailSection() != null) {
									lIDocumentItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof CKEditorItem) {
							CKEditorItem lCKEditorItem = (CKEditorItem) item;
							boolean itemValid = lCKEditorItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lCKEditorItem != null && lCKEditorItem.getForm() != null && lCKEditorItem.getForm().getDetailSection() != null) {
									lCKEditorItem.getForm().getDetailSection().open();
								}
							}
						} else {
							boolean itemValid = item.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
									item.getForm().getDetailSection().open();
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
		
	/**************************************************************************
	 * METODI RELATIVI ALLE MODALITA' DI APERTURA DELLA MASCHERA DI DETTAGLIO *
	 **************************************************************************/

	/**
	 * Metodo di nuovo dettaglio
	 * 
	 */
	public void nuovoDettaglio(CustomLayout layout) {
		
		manageResponse(layout, null, null);
		newMode();
	}

	/**
	 * Metodo di nuovo dettaglio con valori preimpostati
	 * 
	 */
	public void nuovoDettaglio(CustomLayout layout, Map initialValues) {
		
		manageResponse(layout, null, initialValues);
		if(isRichiestaAccessoCivico()) {	
			if(initialValues.get("flgRichAccCivSemplice") != null  &&
					(Boolean)initialValues.get("flgRichAccCivSemplice")) {
				manageOnChangedFlgRichAccCivSemplice();
			} else if(initialValues.get("flgRichAccCivGeneralizzato") != null  &&
					(Boolean)initialValues.get("flgRichAccCivGeneralizzato")) {
				manageOnChangedFlgRichAccCivGeneralizzato();
			}
		}
		newMode();
	}

	/**
	 * Metodo che carica il dettaglio di un documento a maschera
	 * 
	 */
	public void caricaDettaglio(CustomLayout layout, Record lRecord) {
		
		manageResponse(layout, lRecord, null);
		String listaDatiAnnullati = lRecord.getAttribute("listaDatiAnnullati");
		if (listaDatiAnnullati != null && !"".equals(listaDatiAnnullati)) {
			mostraVariazioni(listaDatiAnnullati);
		}
	}

	public void caricaDettaglioXProtBozza(CustomLayout layout, final Record lRecord) {
		
		caricaDettaglio(layout, lRecord);
		editMode();
		manageApponiTimbroXProtBozza(lRecord);
		salvaRegistraButton.setTitle(getTitleRegistraButton());
		salvaRegistraButton.setIcon(getIconRegistraButton());
	}
	
	/**
	 * Metodo che preimposta i valori per il timbro di file primario ed allegati
	 * 
	 */
	public void manageApponiTimbroXProtBozza(Record lRecord) {
		
		//File primario
		if(lRecord.getAttribute("uriFilePrimario") != null && !"".equals(lRecord.getAttribute("uriFilePrimario"))){
			preimpostaApponiTimbroXProtBozza();
		}
		//Allegati
		if (lRecord.getAttributeAsRecordList("listaAllegati") != null && lRecord.getAttributeAsRecordList("listaAllegati").getLength() > 0) {
			if(fileAllegatiItem != null) {
				if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).preimpostaApponiTimbroXProtBozza();
				} else if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).preimpostaApponiTimbroXProtBozza();
				}				
			}
		}
	}
	
	/**
	 * Metodo che evidenzia i campi a maschera che hanno subito delle variazioni
	 * nei dati
	 * 
	 */
	public void mostraVariazioni(String listaDatiAnnullati) {

		StringSplitterClient st = new StringSplitterClient(listaDatiAnnullati, ";");
		ArrayList<String> variazioni = new ArrayList<String>();
		for (String token : st.getTokens()) {
			if (token.startsWith("#TipoAllegato.") || token.startsWith("#DesAllegato.")
					|| token.startsWith("#FileAllegato.")) {
				int nroAllegato = Integer.parseInt(token.substring(token.indexOf(".") + 1));
				if(fileAllegatiItem != null) {
					if(fileAllegatiItem instanceof AllegatiGridItem) {
						((AllegatiGridItem)fileAllegatiItem).mostraVariazione(nroAllegato, token.substring(0, token.indexOf(".")));
					} else if(fileAllegatiItem instanceof AllegatiItem) {
						((AllegatiItem)fileAllegatiItem).mostraVariazione(nroAllegato, token.substring(0, token.indexOf(".")));
					}				
				}								
			} else {
				variazioni.add(token);
			}
		}
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if ((item.getName().equals("dataEOraArrivo") && variazioni.contains("#TsArrivo"))
						|| (item.getName().equals("dataProtRicevuto") && variazioni.contains("#DtDocRicevuto"))
						|| (item.getName().equals("annoProtRicevuto") && variazioni.contains("#AnnoDocRicevuto"))
						|| (item.getName().equals("nroProtRicevuto") && variazioni.contains("#EstremiRegDocRicevuto"))
						|| (item.getName().equals("rifOrigineProtRicevuto") && variazioni.contains("#RifDocRicevuto"))
						|| (item.getName().equals("rifRegEmergenza") && variazioni.contains("#RegEmergenza.Registro"))
						|| (item.getName().equals("nroRegEmergenza") && variazioni.contains("#RegEmergenza.Nro"))
						|| (item.getName().equals("dataRegEmergenza")
								&& variazioni.contains("#RegEmergenza.TsRegistrazione"))
						|| (item.getName().equals("oggetto") && variazioni.contains("#DesOgg"))
						|| (item.getName().equals("nomeFilePrimario") && variazioni.contains("#FilePrimario"))
						|| (item.getName().equals("docPressoCentroPosta") && variazioni.contains("PRESSO_CENTRO_POSTA"))) {
					item.setCellStyle(it.eng.utility.Styles.formCellVariazione);
					item.redraw();
				} else if ((item.getName().equals("listaMittenti") && variazioni.contains("#@MittentiEsterni"))
						|| (item.getName().equals("listaDestinatari") && variazioni.contains("#@DestinatariEsterni"))) {
					form.setBackgroundColor("#FFFAAF");
					form.setBorder("2px solid #FF0");
					form.show();
				}
			}
		}
	}

	/**
	 * Metodo che carica i valori a maschera per la protocollazione di una
	 * e-mail
	 * 
	 */
	public void nuovoDettaglioMail(Record lRecord) {
		
		editRecord(lRecord); // chiamo la editRecord() invece della editNewRecord() altrimenti si perde i valori di alcune sezioni ripetute (per es. gli allegati)
		//File primario
		if(lRecord.getAttribute("uriFilePrimario") != null && !"".equals(lRecord.getAttribute("uriFilePrimario"))){
			preimpostaApponiTimbroFromEmail();
		}
		//Allegati
		if (lRecord.getAttributeAsRecordList("listaAllegati") != null && lRecord.getAttributeAsRecordList("listaAllegati").getLength() > 0) {
			if(fileAllegatiItem != null) {
				if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).preimpostaApponiTimbroFromEmail();
				} else if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).preimpostaApponiTimbroFromEmail();
				}				
			}
		}
	}

	/**
	 * Metodo che carica i valori a maschera per la protocollazione di un
	 * registro di emergenza
	 * 
	 */
	public void nuovoDettaglioRegEmergenza(Record lRecord) {
		
		editRecord(lRecord); // vedi sopra
	}

	/**
	 * Metodo che contiene le logiche da applicare in fase di
	 * inizalizzazione/caricamento dei dati a maschera
	 * 
	 */
	protected void manageResponse(CustomLayout layout, Record lRecord, Map initialValues) {

		this.layout = layout;
		if (lRecord != null) {
			editRecord(lRecord);
		} else {
			if (initialValues != null) {
				editNewRecord(initialValues);
			} else {
				editNewRecord();
			}
		}
		contenutiForm.redraw();
//		isDettaglioDoc = true;
		setCanEdit(false);
		setInitialValues();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		invioPECButton.hide();
		invioMailRicevutaButton.hide();
		invioPEOButton.hide();
		invioPostaPrioritariaButton.hide();
		invioRaccomandataButton.hide();
		salvaComeModelloButton.hide();
		mainToolStrip.hide();
		if(layout != null) {
			detailToolStrip.hide();
		}
		if (detailSectionRegistrazione != null) {
			detailSectionRegistrazione.setVisible(showDetailSectionRegistrazione() && lRecord != null);					
		}
		if (detailSectionTipoDocumento != null) {
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
		}
		if (detailSectionDataArrivoSpedizione != null) {						
			if(showDetailSectionDataArrivoSpedizione()) {
				boolean showDataArrivoProtocollo = isProtocollazioneBozza() || (lRecord != null && lRecord.getAttribute("dataArrivoProtocollo") != null);
				boolean showDataSpedizioneCartaceo = isProtocollazioneBozza() || isProtocollazioneDetailUscita() || (lRecord != null && lRecord.getAttribute("dataSpedizioneCartaceo") != null);
				if(showDataArrivoProtocollo || showDataSpedizioneCartaceo) {
					detailSectionDataArrivoSpedizione.setVisible(true);
					if(showDataArrivoProtocollo && showDataSpedizioneCartaceo) {
						detailSectionDataArrivoSpedizione.setTitle("Date arrivo al protocollo e spedizione");
						detailSectionDataArrivoSpedizione.setRequired(true);
						dataArrivoProtocolloItem.setShowTitle(true);
						dataSpedizioneCartaceoItem.setShowTitle(true);
					} else if(showDataArrivoProtocollo) {
						detailSectionDataArrivoSpedizione.setTitle("Data arrivo al protocollo");
						detailSectionDataArrivoSpedizione.setRequired(true);
						dataArrivoProtocolloItem.setShowTitle(false);
						dataSpedizioneCartaceoItem.setShowTitle(false);
					} else if(showDataSpedizioneCartaceo) {
						detailSectionDataArrivoSpedizione.setTitle("Data spedizione cartaceo");
						detailSectionDataArrivoSpedizione.setRequired(false);						
						dataArrivoProtocolloItem.setShowTitle(false);
						dataSpedizioneCartaceoItem.setShowTitle(false);
					}
				} else {
					detailSectionDataArrivoSpedizione.setVisible(false);										
				}
			}
		}	
		//TODO ASSEGNAZIONE ???
		/*
		AssegnazioneItem lAssegnazioneItem = (AssegnazioneItem) assegnazioneForm.getItem("listaAssegnazioni");
		final VLayout lVLayoutAssegnazione = (VLayout) lAssegnazioneItem.getCanvas();
		for (Canvas lCanvas : lVLayoutAssegnazione.getMembers()) {
			if (lCanvas instanceof HLayout) {
				for (Canvas lHLayoutMember : ((HLayout) lCanvas).getMembers()) {
					if (lHLayoutMember instanceof ReplicableCanvas) {
						AssegnazioneCanvas lReplicableCanvas = (AssegnazioneCanvas) lHLayoutMember;
						lReplicableCanvas.getForm()[0].getField("opzioniInvioAssegnazioneButton").hide();
					}
				}
			}
		}
		*/
	}

	/**
	 * Metodo che viene richiamato in fase di inizializzazione della maschera,
	 * per preimpostare la modalità di apertura delle sezioni
	 * 
	 */
	public void setInitialValues() {
		
		flgTipoProvItem.setValue(getFlgTipoProv());
		if (detailSectionRegistrazione != null) {
			detailSectionRegistrazione.setVisible(false);
		}
		if (detailSectionTipoDocumento != null) {
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			detailSectionTipoDocumento.open();
		}
		if (detailSectionTipologieParticolariA2A != null) {
			detailSectionTipologieParticolariA2A.open();
		}
		if (detailSectionDataArrivoSpedizione != null) {
			detailSectionDataArrivoSpedizione.open();
		}		
		if (detailSectionMittenti != null) {
			if(detailSectionMittenti.isRequired() || detailSectionMittenti.showOpen()) {
				detailSectionMittenti.open();
			} else {
				detailSectionMittenti.openIfhasValue();
			}
		}
		if (detailSectionControinteressati != null) {
			detailSectionControinteressati.open();
		}		
		if (detailSectionDestinatari != null) {
			if(detailSectionDestinatari.isRequired() || detailSectionDestinatari.showOpen()) {
				detailSectionDestinatari.open();
			} else {
				detailSectionDestinatari.openIfhasValue();
			}
		}
		if (detailSectionContenuti != null) {
			detailSectionContenuti.open();
		}
		if (detailSectionAllegati != null) {
			if (detailSectionAllegati.showOpen()) {
				detailSectionAllegati.open();
			} else {
				detailSectionAllegati.openIfhasValue();
			}
		}
		if (detailSectionDatiRicezione != null) {
			if(detailSectionDatiRicezione.isRequired()) {
				detailSectionDatiRicezione.open();
			} else {
				detailSectionDatiRicezione.openIfhasValue();
			}
		}
		if (detailSectionDocCollegato != null) {
			detailSectionDocCollegato.openIfhasValue();
		}
		if (detailSectionRegEmergenza != null) {
			detailSectionRegEmergenza.openIfhasValue();
		}
		if (detailSectionCollocazioneFisica != null) {
			detailSectionCollocazioneFisica.openIfhasValue();
		}
		if (detailSectionAltriDati != null) {
			detailSectionAltriDati.openIfhasValue();
		}
		if (detailSectionAssegnazione != null) {
			detailSectionAssegnazione.open();
		}
		if (detailSectionCondivisione != null) {
			detailSectionCondivisione.open();
		}
		if (detailSectionClassificazioneFascicolazione != null) {
			detailSectionClassificazioneFascicolazione.open();
		}
		if (detailSectionFolderCustom != null) {
			detailSectionFolderCustom.open();
		}		
		if (detailSectionEsibenti != null) {
			detailSectionEsibenti.open();
		}
		if (detailSectionInteressati != null) {
			detailSectionInteressati.open();
		}
		if (detailSectionDelegato != null) {
			detailSectionDelegato.open();
		}
		if (detailSectionFirmatari != null) {
			detailSectionFirmatari.open();
		}
		if (detailSectionAltreVie != null) {
			detailSectionAltreVie.open();				
		}		
	}

	/**
	 * Metodo che reinizializza i campi a maschera (con gli eventuali valori di
	 * default, o semplicemente li sbianca)
	 * 
	 */
	@Override
	public void editNewRecord() {

		vm.clearErrors(true);
		clearTabErrors(tabSet);
		valuesOrig = null;
		super.editNewRecord();
		if(idOperRegistrazioneItem != null) {
			String uniqueUUID = SC.generateID();
			idOperRegistrazioneItem.setValue(uniqueUUID);
		}
		if (!isProtocollazioneDetailBozze()) {
			utentiAbilCPAValueMap = AurigaLayout.getUtentiAbilCPAValueMap();
			utentiAbilCPAItem.setValueMap(utentiAbilCPAValueMap);
			redrawConfermaAssegnazione();
			flgPreviaConfermaAssegnazioneItem.setValue(false);
			utentiAbilCPAItem.setValue((String) null);
		}
		if (showAttributiDinamiciDoc()) {
			if (tipoDocumentoItem != null) {
				tipoDocumentoItem.setValue(tipoDocumento);
			}
			caricaAttributiDinamiciDoc(tipoDocumento, null);
		}
		if (isAltraNumerazione()) {
			if(protocolloGeneraleItem != null) {
				protocolloGeneraleItem.setValue(isAbilToProt() ? getDefaultProtocolloGenerale() : false);				
			}
		}
	}
	
	/**
	 * Metodo che reinizializza i campi a maschera, partendo dai valori salvati nel modello
	 * 
	 */	
	public void editNewRecordFromModello(Map valuesFromModello, Record recordModello) {
		if(recordModello != null) {
			valuesFromModello.put("prefKeyModello", recordModello.getAttribute("prefKey"));
			valuesFromModello.put("prefNameModello", recordModello.getAttribute("prefName"));
			valuesFromModello.put("useridModello", recordModello.getAttribute("userid"));
			valuesFromModello.put("idUoModello", recordModello.getAttribute("idUo"));
		}		
//		valuesFromModello.put("tipoDocumentoSalvato", valuesFromModello.get("tipoDocumento"));
//		RecordList listaAllegati = new Record(valuesFromModello).getAttributeAsRecordList("listaAllegati");
//		if (listaAllegati != null) {
//			for (int i = 0; i < listaAllegati.getLength(); i++) {
//				listaAllegati.get(i).setAttribute("idTipoFileAllegatoSalvato", listaAllegati.get(i).getAttribute("listaTipiFileAllegato"));				
//			}
//		}	
//		valuesFromModello.put("listaAllegati", listaAllegati);
		editNewRecord(valuesFromModello);
	}

	/**
	 * Metodo che reinizializza i campi a maschera, preimpostando alcuni valori
	 * (passati nella mappa in input)
	 * 
	 */
	@Override
	public void editNewRecord(Map initialValues) {

		if (livelloRiservatezzaItem != null) {
			if (initialValues != null && initialValues.get("livelloRiservatezza") != null && !"".equalsIgnoreCase( String.valueOf(initialValues.get("livelloRiservatezza")))   
					&& initialValues.get("descrizioneRiservatezza") != null && !"".equalsIgnoreCase((String) initialValues.get("descrizioneRiservatezza"))){
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put( String.valueOf(initialValues.get("livelloRiservatezza")), (String)initialValues.get("descrizioneRiservatezza"));
				livelloRiservatezzaItem.setValueMap(valueMap);
				livelloRiservatezzaItem.setValue(String.valueOf(initialValues.get("livelloRiservatezza"))); 		
			}
		}
		if (prioritaRiservatezzaItem != null) {
			if (initialValues != null && initialValues.get("prioritaRiservatezza") != null && !"".equalsIgnoreCase((String) initialValues.get("prioritaRiservatezza")) && 
				initialValues.get("descrizionePrioritaRiservatezza") != null && !"".equalsIgnoreCase((String) initialValues.get("descrizionePrioritaRiservatezza"))){
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				String descrizionePrioritaRiservatezza = "<div><img src=\"images/protocollazione/riservatezza/" + (String)initialValues.get("prioritaRiservatezza") +".png\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + (String)initialValues.get("descrizionePrioritaRiservatezza") + "</div>";
				valueMap.put((String)initialValues.get("prioritaRiservatezza"), descrizionePrioritaRiservatezza);
				prioritaRiservatezzaItem.setValueMap(valueMap);
				prioritaRiservatezzaItem.setValue((String)initialValues.get("prioritaRiservatezza"));			
			}
		}
		if (tipoDocumentoItem != null) {
			if (initialValues != null && initialValues.get("tipoDocumento") != null && !"".equalsIgnoreCase((String) initialValues.get("tipoDocumento")) && 
				initialValues.get("nomeTipoDocumento") != null && !"".equalsIgnoreCase((String) initialValues.get("nomeTipoDocumento"))){			
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put((String)initialValues.get("tipoDocumento"), (String)initialValues.get("nomeTipoDocumento"));
				tipoDocumentoItem.setValueMap(valueMap);
				tipoDocumentoItem.setValue((String)initialValues.get("tipoDocumento"));			
			}
		}
		if (repertorioItem != null) {
			if (initialValues != null && initialValues.get("repertorio") != null && !"".equalsIgnoreCase((String) initialValues.get("repertorio"))){			
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put((String)initialValues.get("repertorio"), (String)initialValues.get("repertorio"));
				repertorioItem.setValueMap(valueMap);
				repertorioItem.setValue((String)initialValues.get("repertorio"));			
			}
		}
		
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		valuesOrig = initialValues;
		String uniqueUUID = SC.generateID();
		initialValues.put("idOperRegistrazione", uniqueUUID);
		super.editNewRecord(initialValues);
		if (!isProtocollazioneDetailBozze()) {
			utentiAbilCPAValueMap = AurigaLayout.getUtentiAbilCPAValueMap();
			utentiAbilCPAItem.setValueMap(utentiAbilCPAValueMap);
			redrawConfermaAssegnazione();
		}
		if (showAttributiDinamiciDoc()) {
			this.tipoDocumento = new Record(initialValues).getAttribute("tipoDocumento");
			caricaAttributiDinamiciDoc(tipoDocumento, null);
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_MODELLI_X_TIPO_DOC")) {
				if(tipoDocumento != null && !"".equals(tipoDocumento)) {
					if(modelliDS != null && modelliSelectItem != null) {
						modelliDS.addParam("prefKey", nomeEntita + ".modelli." + tipoDocumento);
						modelliSelectItem.setOptionDataSource(modelliDS);
						modelliSelectItem.fetchData();
					}
				}
			}
		}
		if (isAltraNumerazione()) {
			if(protocolloGeneraleItem != null) {
				if (isAbilToProt()) {
					if (!initialValues.containsKey("protocolloGenerale")) {
						protocolloGeneraleItem.setValue(getDefaultProtocolloGenerale());
					} else {
						protocolloGeneraleItem.setValue(initialValues.get("protocolloGenerale"));
					}
				} else {
					protocolloGeneraleItem.setValue(false);
				}			
			}			
		}
	}

	/**
	 * Metodo che carica i valori del record nei campi a maschera
	 * 
	 */
	@Override
	public void editRecord(Record record) {
		editRecord(record, false);
	}
	
	public void editRecord(Record record, boolean skipCaricaAttributiDinamiciDoc) {
		
		if (uoProtocollanteSelectItem != null) {
			if (record.getAttribute("uoProtocollante") != null && !"".equalsIgnoreCase(record.getAttribute("uoProtocollante"))) {		
				if(uoProtocollanteSelectItem.getValueMap() != null && uoProtocollanteSelectItem.getValueMap().containsKey(record.getAttribute("uoProtocollante"))) {
					uoProtocollanteSelectItem.setValue(record.getAttribute("uoProtocollante"));
					manageChangedUoProtocollante(record.getAttribute("uoProtocollante"));
				} else {
					record.setAttribute("uoProtocollante", "");
				}
			}
		}
		
		if (record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {
			String idUd = record.getAttributeAsString("idUd");
			if (record.getAttributeAsRecord("filePrimarioOmissis") != null) {
				record.getAttributeAsRecord("filePrimarioOmissis").setAttribute("idUd", idUd);
			}
		}
		
		if (livelloRiservatezzaItem != null) {
			if (record.getAttribute("livelloRiservatezza") != null && !"".equalsIgnoreCase(record.getAttribute("livelloRiservatezza")) && 
				record.getAttribute("descrizioneRiservatezza") != null && !"".equalsIgnoreCase(record.getAttribute("descrizioneRiservatezza"))){
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("livelloRiservatezza"), record.getAttribute("descrizioneRiservatezza"));
				livelloRiservatezzaItem.setValueMap(valueMap);
				livelloRiservatezzaItem.setValue(record.getAttribute("livelloRiservatezza"));			
			}
		}
		if (prioritaRiservatezzaItem != null) {
			if (record.getAttribute("prioritaRiservatezza") != null && !"".equalsIgnoreCase(record.getAttribute("prioritaRiservatezza")) && 
				record.getAttribute("descrizionePrioritaRiservatezza") != null && !"".equalsIgnoreCase(record.getAttribute("descrizionePrioritaRiservatezza"))){
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				String descrizionePrioritaRiservatezza = "<div><img src=\"images/protocollazione/riservatezza/" + record.getAttribute("prioritaRiservatezza") +".png\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + record.getAttribute("descrizionePrioritaRiservatezza") + "</div>";
				valueMap.put(record.getAttribute("prioritaRiservatezza"), descrizionePrioritaRiservatezza);
				prioritaRiservatezzaItem.setValueMap(valueMap);
				prioritaRiservatezzaItem.setValue(record.getAttribute("prioritaRiservatezza"));			
			}
		}
		if (tipoDocumentoItem != null) {
			if (record.getAttribute("tipoDocumento") != null && !"".equalsIgnoreCase(record.getAttribute("tipoDocumento")) && 
				record.getAttribute("nomeTipoDocumento") != null && !"".equalsIgnoreCase(record.getAttribute("nomeTipoDocumento"))){
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("tipoDocumento"), record.getAttribute("nomeTipoDocumento"));
				tipoDocumentoItem.setValueMap(valueMap);
				tipoDocumentoItem.setValue(record.getAttribute("tipoDocumento"));			
			}
		}
		
		if (periziaItem != null) {
			periziaForm.markForRedraw();
			if (record.getAttribute("codice") != null && !"".equalsIgnoreCase(record.getAttribute("codice")) && 
					record.getAttribute("descrizione") != null && !"".equalsIgnoreCase(record.getAttribute("descrizione"))){
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					valueMap.put(record.getAttribute("codice"), record.getAttribute("descrizione"));
					tipoDocumentoItem.setValueMap(valueMap);
					tipoDocumentoItem.setValue(record.getAttribute("tipoDocumento"));			
				}
			
		}
		
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		valuesOrig = record.toMap();
		if (showSiglaProtocolloItem()) {
			siglaProtocolloItem.setTitle(getTitleNroProtocolloItem(record));
		} else {
			nroProtocolloItem.setTitle(getTitleNroProtocolloItem(record));
		}
		if (tipoDocumentoItem != null) {
			GWTRestDataSource tipoDocumentoDS = (GWTRestDataSource) tipoDocumentoItem.getOptionDataSource();
			if (record.getAttribute("tipoDocumento") != null
					&& !"".equals(record.getAttributeAsString("tipoDocumento"))) {
				tipoDocumentoDS.addParam("idTipoDocumento", record.getAttributeAsString("tipoDocumento"));
			} else {
				tipoDocumentoDS.addParam("idTipoDocumento", null);
			}
			tipoDocumentoItem.setOptionDataSource(tipoDocumentoDS);
		}
		record.setAttribute("nomeFilePrimarioOrig", record.getAttribute("nomeFilePrimario"));	
		String uniqueUUID = SC.generateID();
		record.setAttribute("idOperRegistrazione", uniqueUUID);
		super.editRecord(record);
		if (!isProtocollazioneDetailBozze()) {
			String idUserConfermaAssegnazione = record.getAttribute("idUserConfermaAssegnazione");
			if (idUserConfermaAssegnazione != null && !"".equals(idUserConfermaAssegnazione)) {
				utentiAbilCPAValueMap = AurigaLayout.getUtentiAbilCPAValueMap();
				if (!utentiAbilCPAValueMap.containsKey(idUserConfermaAssegnazione)) {
					String displayValue = record.getAttribute("desUserConfermaAssegnazione") + " ("
							+ record.getAttribute("usernameConfermaAssegnazione") + ")";
					utentiAbilCPAValueMap.put(idUserConfermaAssegnazione, displayValue);
				}
				utentiAbilCPAItem.setValueMap(utentiAbilCPAValueMap);
				redrawConfermaAssegnazione();
				if (utentiAbilCPAValueMap.size() == 1) {
					flgPreviaConfermaAssegnazioneItem.setValue(true);
				} else if (utentiAbilCPAValueMap.size() > 1) {
					utentiAbilCPAItem.setValueMap(utentiAbilCPAValueMap);
					utentiAbilCPAItem.setValue(idUserConfermaAssegnazione);
				}
			} else {
				utentiAbilCPAValueMap = AurigaLayout.getUtentiAbilCPAValueMap();
				utentiAbilCPAItem.setValueMap(utentiAbilCPAValueMap);
				redrawConfermaAssegnazione();
				flgPreviaConfermaAssegnazioneItem.setValue(false);
				utentiAbilCPAItem.setValue((String) null);
			}
		}
		if (!skipCaricaAttributiDinamiciDoc && showAttributiDinamiciDoc()) {
			this.tipoDocumento = record.getAttribute("tipoDocumento");
			this.rowidDoc = record.getAttribute("rowidDoc");
			caricaAttributiDinamiciDoc(tipoDocumento, rowidDoc);
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_MODELLI_X_TIPO_DOC")) {
				if(tipoDocumento != null && !"".equals(tipoDocumento)) {
					if(modelliDS != null && modelliSelectItem != null) {
						modelliDS.addParam("prefKey", nomeEntita + ".modelli." + tipoDocumento);
						modelliSelectItem.setOptionDataSource(modelliDS);
						modelliSelectItem.fetchData();
					}
				}
			}
		}
		if (isAltraNumerazione()) {
			if(protocolloGeneraleItem != null) {
				protocolloGeneraleItem.setValue(record.getAttributeAsBoolean("protocolloGenerale") != null && record.getAttributeAsBoolean("protocolloGenerale")); 
			}
			if (record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
				if(isConProtocolloGenerale()) {
					registrazioneSecondariaForm.setVisible(true);
					altraNumerazioneProvvisoriaForm.setVisible(false);
				} else {
					registrazioneSecondariaForm.setVisible(false);
					altraNumerazioneProvvisoriaForm.setVisible(true);
				}
			} else {
				registrazioneSecondariaForm.setVisible(false);
				altraNumerazioneProvvisoriaForm.setVisible(false);			
			}
			if ((record.getAttribute("nroProtocollo") != null && !"".equals(record.getAttribute("nroProtocollo")))
					&& (record.getAttribute("numeroNumerazioneSecondaria") != null && !"".equals(record.getAttribute("numeroNumerazioneSecondaria")))) {
				iconaTipoAltraNumerazioneItem.setPrompt(getTitleAltraNumerazione());	
			} else {
				iconaTipoAltraNumerazioneItem.setPrompt(getTitleAltraNumerazioneProvvisoria());			
			}
		}		
	}

	/**
	 * Metodo che effettua il redraw del form di conferma assegnazione
	 * 
	 */
	public void redrawConfermaAssegnazione() {

		if (utentiAbilCPAValueMap.size() == 0) {
			confermaAssegnazioneForm.hide();
			utentiAbilCPAItem.hide();
			flgPreviaConfermaAssegnazioneItem.hide();
		} else if (utentiAbilCPAValueMap.size() == 1) {
			confermaAssegnazioneForm.show();
			utentiAbilCPAItem.hide();
			flgPreviaConfermaAssegnazioneItem.show();
		} else if (utentiAbilCPAValueMap.size() > 1) {
			confermaAssegnazioneForm.show();
			utentiAbilCPAItem.show();
			flgPreviaConfermaAssegnazioneItem.hide();
		}
		confermaAssegnazioneForm.redraw();
	}

	/**
	 * Metodo che abilita/disabilita l'editing dei campi a maschera
	 * 
	 */
	@Override
	public void setCanEdit(boolean canEdit) {

		super.setCanEdit(canEdit);

		if (isProtocollazioneDetailAtti()) {
			if (getUoProtocollanteValueMap().size() == 1) {
				if (uoProtocollanteSelectItem != null) {
					uoProtocollanteSelectItem.setCanEdit(false);
					if (!AurigaLayout.getIsAttivaAccessibilita()) {
						uoProtocollanteSelectItem.setTabIndex(-1);
					}
				}
			}
		}
		
		setCanEdit(false, protocolloGeneraleForm); // i campi della sezione "Registrazione" devono essere sempre read-only
		siglaProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		nroProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		subProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		dataProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		societaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		desUserProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		desUOProtocolloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		estremiRepertorioPerStrutturaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
				
		
		setCanEdit(false, registrazioneSecondariaForm);
		registrazioneSecondariaSiglaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		registrazioneSecondariaNumeroItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		registrazioneSecondariaSubItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		registrazioneSecondariaDataRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		dtEsecutivitaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		setCanEdit(false, altreNumerazioniUDForm);
		altreNumerazioniUDItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);

		setCanEdit(false, altraNumerazioneForm);
		altraNumerazioneSiglaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneNumeroItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneSubItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneDataRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneRedattoreItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneDestinatarioItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			
		altraNumerazioneEstremiRepXStruttItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
				
		setCanEdit(false, altraNumerazioneProvvisoriaForm);
		altraNumerazioneProvvisoriaSiglaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneProvvisoriaNumeroItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneProvvisoriaSubItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		altraNumerazioneProvvisoriaDataRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		if (!AurigaLayout.getIsAttivaAccessibilita()) {
			siglaProtocolloItem.setTabIndex(-1);
			nroProtocolloItem.setTabIndex(-1);
			subProtocolloItem.setTabIndex(-1);
			statoItem.setTabIndex(-1);
			dataProtocolloItem.setTabIndex(-1);
			societaItem.setTabIndex(-1);
			desUserProtocolloItem.setTabIndex(-1);
			desUOProtocolloItem.setTabIndex(-1);
			estremiRepertorioPerStrutturaItem.setTabIndex(-1);
			registrazioneSecondariaSiglaItem.setTabIndex(-1);
			registrazioneSecondariaNumeroItem.setTabIndex(-1);
			registrazioneSecondariaSubItem.setTabIndex(-1);
			registrazioneSecondariaDataRegistrazioneItem.setTabIndex(-1);
			dtEsecutivitaItem.setTabIndex(-1);
			flgImmediatamenteEsegItem.setTabIndex(-1);
			altraNumerazioneSiglaItem.setTabIndex(-1);
			altraNumerazioneNumeroItem.setTabIndex(-1);
			altraNumerazioneSubItem.setTabIndex(-1);
			altraNumerazioneDataRegistrazioneItem.setTabIndex(-1);
			altraNumerazioneRedattoreItem.setTabIndex(-1);
			altraNumerazioneDestinatarioItem.setTabIndex(-1);	
			altraNumerazioneEstremiRepXStruttItem.setTabIndex(-1);
			altraNumerazioneProvvisoriaSiglaItem.setTabIndex(-1);
			altraNumerazioneProvvisoriaNumeroItem.setTabIndex(-1);
			altraNumerazioneProvvisoriaSubItem.setTabIndex(-1);
			altraNumerazioneProvvisoriaDataRegistrazioneItem.setTabIndex(-1);
		}
		
		setCanEdit(false, attoLiquidazioneForm);
		
		setCanEdit(false, statoForm);
		statoItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		
		estremiAttoLiquidazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		estremiAttoLiquidazioneItem.setTabIndex(-1);
		
		if (showAttributiDinamiciDoc()) {
			if (tipoDocumentoItem != null) {
				tipoDocumentoItem.setCanEdit(false);
			}
		}

		boolean hasProcessoCollegato = idProcessHiddenItem != null && idProcessHiddenItem.getValue() != null
				&& !"".equals(idProcessHiddenItem.getValue());
		if (!isTaskDetail() && !hasProcessoCollegato) {
			if (attributiAddDocDetails != null) {
				for (String key : attributiAddDocDetails.keySet()) {
					AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
					detail.setCanEdit(new Boolean(canEdit || isModificaDatiExtraIter()));
					// for (DynamicForm form : detail.getForms()) {
					// setCanEdit(canEdit, form);
					// }
				}
			}
		}
		if(filePrimarioForm != null) {
			filePrimarioForm.markForRedraw();
		}
		if(filePrimarioButtons != null) {
			filePrimarioButtons.markForRedraw();
		}
		if(fileAllegatiItem != null) {
			fileAllegatiItem.redraw();
		}
		manageChangedPrimario();		
	}

	/**
	 * Metodo che imposta la maschera in modalità new (nuovo dettaglio)
	 * 
	 */
	@Override
	public void newMode() {
		newMode(false);
	}

	/**
	 * Metodo che imposta la maschera in modalità new (nuovo dettaglio come copia di un'altra registrazione)
	 * 
	 */
	public void newComeCopiaMode() {
		newMode(true);
	}
	
	/**
	 * Metodo che imposta la maschera in modalità new (nuovo dettaglio con caricamento dei dati da modello)
	 * 
	 */
	public void newDaModelloMode() {
		newMode(true);
	}
	
	/**
	 * Metodo che indica se si sta protocollando una bozza (protocollazione in uscita o interna)
	 * 
	 */
	public boolean isProtocollazioneBozza() {
		Record detailRecord = new Record(vm.getValues());
		String tipoNumerazionePrincipale = detailRecord.getAttribute("tipoProtocollo") != null ? detailRecord.getAttribute("tipoProtocollo") : "";
		if (tipoNumerazionePrincipale != null && tipoNumerazionePrincipale.equalsIgnoreCase("NI") && (getFlgTipoProv() != null && ("U".equals(getFlgTipoProv()) || "I".equals(getFlgTipoProv())))) {
			return true;
		}
		return false;
	}
	
	public void showHideMainToolStrip() {
		mainToolStrip.hide();	
		if(mode != null) {
			if(mode.equals("new")) {
				if (showUoProtocollanteSelectItem() || showModelliSelectItem()) {
					try {						
						if (modelliSelectItem != null) {					
							if (showModelliSelectItem()) {
								modelliSelectItem.show(); // nel caso lo avessi nascosto in precedenza							
							} else {							
								modelliSelectItem.hide();							
							}
						}	
					} catch(Exception e) {
						// ho aggiunto questo blocco try/catch perchè lo show/hide di modelliSelectItem in alcuni casi andava in errore
					}
					mainToolStrip.show();
				}
			} else if(mode.equals("edit")) {
				if(isProtocollazioneBozza() || (isAltraNumerazione() && !isConProtocolloGenerale())) {
					if (showUoProtocollanteSelectItem()) {
						try {
							if (modelliSelectItem != null) {
								modelliSelectItem.hide(); // nascondo la select dei modelli								
							}		
						} catch(Exception e) {
							// ho aggiunto questo blocco try/catch perchè lo show/hide di modelliSelectItem in alcuni casi andava in errore
						}
						mainToolStrip.show(); // visualizzo la barra superiore in cui è presente la select per selezionare la U.O. protocollante						
					}
				} 
			}
		}
	}

	public void newMode(boolean skipAggiuntaModeInFolders) {

		this.mode = "new";
		tabSet.selectTab(0);
		setModificaDatiReg(false);
		setCanEdit(true);
		setInitialValues();
		showHideMainToolStrip();
		salvaRegistraButton.setTitle(getTitleRegistraButton());
		salvaRegistraButton.setIcon(getIconRegistraButton());
		downloadDocZipButton.hide();
		frecciaDownloadZipButton.hide();
		nuovaProtButton.hide();
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		stampaCopertinaButton.hide();
		stampaRicevutaButton.hide();
		stampaMenuButton.hide();
		nuovaProtComeCopiaButton.hide();
		salvaRegistraButton.show();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		frecciaModificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		revocaAttoButton.hide();
		protocollazioneEntrataButton.hide();
		protocollazioneUscitaButton.hide();
		protocollazioneInternaButton.hide();
		invioPECButton.hide();
		invioMailRicevutaButton.hide();
		invioPEOButton.hide();
		invioPostaPrioritariaButton.hide();
		invioRaccomandataButton.hide();
		smistaButton.hide();
		smistaCCButton.hide();
		invioAlProtocolloButton.hide();
		frecciaInvioAlProtocolloButton.hide();
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
//		assegnazioneButton.hide();
//		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
//		condivisioneButton.hide();
//		frecciaCondivisioneButton.hide();
		osservazioniNotificheButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneFirmaProtocollazioneButton.hide();
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();		
		pubblicazioneTraspAmmButton.hide();
		if (isProtocollazioneDetailBozze() || isProtocollazioneDetailAtti() /*|| isIstanzeDetail()*/) {
			verificaRegistrazioneButton.hide();
		} else {
			verificaRegistrazioneButton.show();
		}
		if (showModelliSelectItem()) {
			salvaComeModelloButton.show();
		} else {
			salvaComeModelloButton.hide();
		}
		reloadDetailButton.hide();
		undoButton.hide();
		if (detailSectionRegistrazione != null) {
			detailSectionRegistrazione.hide();
		}
		if (detailSectionNuovaRegistrazione != null) {
			if (isAltraNumerazione() && showDetailSectionNuovaRegistrazione()) {
				detailSectionNuovaRegistrazione.show();
			} else {
				detailSectionNuovaRegistrazione.hide();
			}
		}
		if (detailSectionNuovaRegistrazioneProtGenerale != null) {
			if (showDetailSectionNuovaRegistrazioneProtGenerale()) {
				detailSectionNuovaRegistrazioneProtGenerale.setTitle("Registrazione");
				detailSectionNuovaRegistrazioneProtGenerale.show();
			} else {
				detailSectionNuovaRegistrazioneProtGenerale.hide();
			}
		}
		/*********** Per rich. accesso civico e contratti ************/
		if (detailSectionControinteressati != null) {
			if (showDetailSectionControinteressati()) {
				detailSectionControinteressati.show();
			} else {
				detailSectionControinteressati.hide();
			}
		}
		if (detailSectionCollocazioneFisica != null) {
			if (showDetailSectionCollocazioneFisica()) {
				detailSectionCollocazioneFisica.show();
			} else {
				detailSectionCollocazioneFisica.hide();
			}
		}
		/*************************************************************/
		if (mittentiItem != null) {
			mittentiItem.setShowFlgAssegnaAlMittente(true);
		}
		if (destinatariItem != null) {
			destinatariItem.setShowFlgAssegnaAlDestinatario(true);
		}
		if (detailSectionAssegnazione != null) {
			detailSectionAssegnazione.setTitle(I18NUtil.getMessages().protocollazione_detail_assegnazioneForm_title());
			if(isProtocollazioneDetailBozze() || (isProtocollazioneDetailUscita() && AurigaLayout.isAttivoClienteA2A())) {
				detailSectionAssegnazione.hide();
			} else {
				detailSectionAssegnazione.show();
			}
			if (assegnazioneItem != null) {
				assegnazioneItem.show();
			}
			if (assegnazioneSalvataItem != null) {
				assegnazioneSalvataItem.hide();
			}
		}
		if (detailSectionCondivisione != null) {
			detailSectionCondivisione.setTitle(I18NUtil.getMessages().protocollazione_detail_condivisioneForm_title());
			if(isProtocollazioneDetailBozze()) {
				detailSectionCondivisione.hide();
			} else {
				detailSectionCondivisione.show();
			}
			if (condivisioneSalvataItem != null) {
				condivisioneSalvataItem.hide();
			}
		}
		// se sto creando il documento dentro una specifica cartella/fascicolo
		// devo preimpostare i valori di quella cartella/fascicolo nella
		// sezione relativa e impostare la modalità in aggiunta; se invece sto
		// facendo una nuova prot. come copia o caricando da modello la sezione deve mantenere
		// la modalità standard, anche se ho dei valori preimpostati
		if(detailSectionClassificazioneFascicolazione != null) {
			if (!skipAggiuntaModeInFolders) {
				RecordList listaClassFasc = classificazioneFascicolazioneForm.getValuesAsRecord()
						.getAttributeAsRecordList("listaClassFasc");
				if (listaClassFasc != null && listaClassFasc.getLength() > 0) {
					Record lFirstRecord = listaClassFasc.get(0);
					if (lFirstRecord.getAttribute("idFolderFascicolo") != null
							&& !"".equals(lFirstRecord.getAttribute("idFolderFascicolo"))) {
						classificazioneFascicolazioneItem.setAggiuntaMode();
					}
				}
			}
		}
		if (detailSectionFolderCustom != null) {
			if (showDetailSectionFolderCustom()) {
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_FLD_CUSTOM_IN_NEW_PROT")) {
					detailSectionFolderCustom.show();
					if (!skipAggiuntaModeInFolders) {
						RecordList listaFolderCustom = folderCustomForm.getValuesAsRecord()
								.getAttributeAsRecordList("listaFolderCustom");
						if (listaFolderCustom != null && listaFolderCustom.getLength() > 0) {
							Record lFirstRecord = listaFolderCustom.get(0);
							if (lFirstRecord.getAttribute("id") != null
									&& !"".equals(lFirstRecord.getAttribute("id"))) {
								folderCustomItem.setAggiuntaMode();
							}
						}
					}
				} else {
					detailSectionFolderCustom.hide();
				}
			} else {
				detailSectionFolderCustom.hide();
			}
		}
		if (detailSectionTipologieParticolariA2A != null) {
			if(showDetailSectionTipologieParticolariA2A()) {
				detailSectionTipologieParticolariA2A.show();
			} else {
				detailSectionTipologieParticolariA2A.hide();
			}
		}
	}

	/**
	 * Metodo che imposta la maschera in modalità view (sola visualizzazione del
	 * dettaglio)
	 * 
	 */
	@Override
	public void viewMode() {

		this.mode = "view";
		setCanEdit(false);
		setInitialValues();
		showHideMainToolStrip();
		Record detailRecord = new Record(vm.getValues());
		if (isProtocollazioneDetailBozze() /*|| isIstanzeDetail()*/) {
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			if(showSmistaButton(detailRecord)) {				
				smistaButton.show();
			} else {
				smistaButton.hide();
			}
			if(showSmistaCCButton(detailRecord)) {				
				smistaCCButton.show();
			} else {
				smistaCCButton.hide();
			}
			if(showInvioAlProtocolloButton(detailRecord)) {
				invioAlProtocolloButton.show();
				frecciaInvioAlProtocolloButton.show();
			} else {
				invioAlProtocolloButton.hide();		
				frecciaInvioAlProtocolloButton.hide();
			}
			if(!showAssegnazioneButton(detailRecord) && !showCondivisioneButton(detailRecord) && !showModAssInviiCCButton(detailRecord)) {
				assegnaCondividiButton.hide();
				frecciaAssegnaCondividiButton.hide();
			} else {
				assegnaCondividiButton.show();
				frecciaAssegnaCondividiButton.show();
			}
			/*if(showAssegnazioneButton(detailRecord)){
				assegnazioneButton.show();
				frecciaAssegnazioneButton.show();
			}else{
				assegnazioneButton.hide();
				frecciaAssegnazioneButton.hide();
			}
			if(showCondivisioneButton(detailRecord)){
				condivisioneButton.show();		
				frecciaCondivisioneButton.show();
			}else{
				condivisioneButton.hide();
				frecciaCondivisioneButton.hide();
			}*/
		} else {
			if (showStampaEtichettaButton(detailRecord)) {												
				stampaEtichettaButton.show();
				if (showFrecciaStampaEtichettaButton(detailRecord)) {
					frecciaStampaEtichettaButton.show();
				} else {
					frecciaStampaEtichettaButton.hide();
				}
			} else {
				stampaEtichettaButton.hide();
				frecciaStampaEtichettaButton.hide();
			}
			if(showSmistaButton(detailRecord)) {				
				smistaButton.show();
			} else {
				smistaButton.hide();
			}
			if(showSmistaCCButton(detailRecord)) {				
				smistaCCButton.show();
			} else {
				smistaCCButton.hide();
			}
			if(showInvioAlProtocolloButton(detailRecord)) {
				invioAlProtocolloButton.show();
				frecciaInvioAlProtocolloButton.show();
			} else {
				invioAlProtocolloButton.hide();
				frecciaInvioAlProtocolloButton.hide();
			}
			if(!showAssegnazioneButton(detailRecord) && !showCondivisioneButton(detailRecord) && !showModAssInviiCCButton(detailRecord)) {
				assegnaCondividiButton.hide();
				frecciaAssegnaCondividiButton.hide();
			} else {
				assegnaCondividiButton.show();
				frecciaAssegnaCondividiButton.show();
			}
			/*if(showAssegnazioneButton(detailRecord)){
				assegnazioneButton.show();
				frecciaAssegnazioneButton.show();
			}else{
				assegnazioneButton.hide();
				frecciaAssegnazioneButton.hide();
			}
			if(showCondivisioneButton(detailRecord)){
				condivisioneButton.show();		
				frecciaCondivisioneButton.show();
			}else{
				condivisioneButton.hide();
				frecciaCondivisioneButton.hide();
			}*/
		}
		if (detailRecord.getAttributeAsBoolean("abilStampaCopertina") && detailRecord.getAttributeAsBoolean("abilStampaRicevuta")) {
			stampaMenuButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampe());
			stampaMenuButton.show();
			stampaCopertinaButton.hide();
			stampaRicevutaButton.hide();
		} else {
			stampaMenuButton.hide();
			if (detailRecord.getAttributeAsBoolean("abilStampaCopertina")) {
				stampaCopertinaButton.show();
			} else {
				stampaCopertinaButton.hide();
			}
			if (detailRecord.getAttributeAsBoolean("abilStampaRicevuta")) {
				stampaRicevutaButton.show();
			} else {
				stampaRicevutaButton.hide();
			}
		}
		if ((isAbilToProt() || isProtocollazioneDetailBozze() /*|| isIstanzeDetail()*/) /*&& !isDettaglioDoc*/ //&& !isFromEmail()
				&& (editMode == null || !editMode.equals("protocollaRegEmergenza"))) {
			nuovaProtButton.show();
			if(detailRecord.getAttributeAsBoolean("abilNuovoComeCopia")) {
				nuovaProtComeCopiaButton.show();
			} else {
				nuovaProtComeCopiaButton.hide();
			}
		} else {
			nuovaProtButton.hide();
			nuovaProtComeCopiaButton.hide();
		}	
		if (isFromEmail()){
			nuovaProtButton.hide();
		}		
		if (detailRecord.getAttributeAsBoolean("abilPresaInCarico")) {
			presaInCaricoButton.show();
		} else {
			presaInCaricoButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilRestituzione")) {
			restituisciButton.show();
		} else {
			restituisciButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilSetVisionato")) {
			segnaComeVisionatoButton.show();
		} else {
			segnaComeVisionatoButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilClassificazioneFascicolazione") ||
				detailRecord.getAttributeAsBoolean("abilOrganizza")) {
			classificazioneFascicolazioneButton.show();
		} else {
			classificazioneFascicolazioneButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilModificaDatiExtraIter") || detailRecord.getAttributeAsBoolean("abilAggiuntaFile") || detailRecord.getAttributeAsBoolean("abilModificaDati")) {
			modificaButton.show();
		} else {
			modificaButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilRegAccessoCivico")) {
			regAccessoCivicoButton.show();
		} else {
			regAccessoCivicoButton.hide();
		}		
		if (detailRecord.getAttributeAsBoolean("abilModificaTipologia") || detailRecord.getAttributeAsBoolean("abilModificaDatiExtraIter") || detailRecord.getAttributeAsBoolean("abilModificaOpereAtto") || detailRecord.getAttributeAsBoolean("abilModificaDatiPubblAtto")){
			frecciaModificaButton.show();
		} else {
			frecciaModificaButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilModificaDatiRegistrazione")) {
			modificaDatiRegButton.show();
		} else {
			modificaDatiRegButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilInvioPEC")) {
			invioPECButton.show();
		} else {
			invioPECButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilInvioEmailRicevuta")) {
			invioMailRicevutaButton.show();
		} else {
			invioMailRicevutaButton.hide();
		}
		if (detailRecord.getAttributeAsBoolean("abilInvioPEO")) {
			invioPEOButton.show();
		} else {
			invioPEOButton.hide();
		}
		if (Layout.isPrivilegioAttivo("PRT/U") && isProtocollazioneDetailUscita() && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")){
			ProtocollazioneUtil.isPossibleToPostel(detailRecord.getAttributeAsInt("idUd"), ETypePoste.POSTA_PRIORITARIA.value(), new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record record) {
					if(record.getAttributeAsBoolean("isDaPostalizzare")){
						invioPostaPrioritariaButton.show();
					}else {
						invioPostaPrioritariaButton.hide();
					}
				}
			});
		}else {
			invioPostaPrioritariaButton.hide();
		}
		if (Layout.isPrivilegioAttivo("PRT/U") && isProtocollazioneDetailUscita() && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")){
			ProtocollazioneUtil.isPossibleToPostel(detailRecord.getAttributeAsInt("idUd"), ETypePoste.RACCOMANDATA.value(), new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record record) {
					if(record.getAttributeAsBoolean("isDaPostalizzare")){
						invioRaccomandataButton.show();
					}else {
						invioRaccomandataButton.hide();
					}
				}
			});
		}else {
			invioRaccomandataButton.hide();
		}	
		salvaRegistraButton.hide();
		verificaRegistrazioneButton.hide();
		salvaComeModelloButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();
		if (showDownloadDocZipButton(detailRecord)) {
			downloadDocZipButton.show();
			frecciaDownloadZipButton.show();
		} else {
			downloadDocZipButton.hide();
			frecciaDownloadZipButton.hide();
		}
		if(showOsservazioniNotifiche(detailRecord)){
			osservazioniNotificheButton.show();
		}else{
			osservazioniNotificheButton.hide();
		}		
		if (showRevocaAttoButton(detailRecord)){
			revocaAttoButton.show();
		}else{
			revocaAttoButton.hide();
		}
		if (isProtocollazioneDetailModelli() && showProtocollazioneEntrataButton(detailRecord)){
			protocollazioneEntrataButton.show();
		}else{
			protocollazioneEntrataButton.hide();
		}
		if (isProtocollazioneDetailBozze() && showProtocollazioneUscitaButton(detailRecord)){
			protocollazioneUscitaButton.show();
		}else{
			protocollazioneUscitaButton.hide();
		}
		if (isProtocollazioneDetailBozze() && showProtocollazioneInternaButton(detailRecord)){
			protocollazioneInternaButton.show();
		}else{
			protocollazioneInternaButton.hide();
		}	
		if (showFirmaButton(detailRecord)){
			apposizioneFirmaButton.show();
			rifiutoApposizioneFirmaButton.show();
		}else{
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
		}
		if (showFirmaProtocollaButton(detailRecord)){
			apposizioneFirmaProtocollazioneButton.show();
		}else{
			apposizioneFirmaProtocollazioneButton.hide();
		}
		if (showVistoDocumentoButton(detailRecord)){
			apposizioneVistoButton.show();
			rifiutoApposizioneVistoButton.show();
		}else{
			apposizioneVistoButton.hide();
			rifiutoApposizioneVistoButton.hide();
		}
		if(showPubblicazioneTraspAmm(detailRecord)){
			pubblicazioneTraspAmmButton.show();
		}else{
			pubblicazioneTraspAmmButton.hide();
		}
		if (detailSectionRegistrazione != null) {
			if(showDetailSectionRegistrazione()) {
				detailSectionRegistrazione.show();
			} else {
				detailSectionRegistrazione.hide();
			}
		}
		if (detailSectionNuovaRegistrazione != null) {
			detailSectionNuovaRegistrazione.hide();
		}
		/*********** Per rich. accesso civico e contratti ************/
		if (detailSectionControinteressati != null) {
			if (showDetailSectionControinteressati()) {
				detailSectionControinteressati.show();
			} else {
				detailSectionControinteressati.hide();
			}
		}
		if (detailSectionCollocazioneFisica != null) {
			if (showDetailSectionCollocazioneFisica()) {
				detailSectionCollocazioneFisica.show();
			} else {
				detailSectionCollocazioneFisica.hide();
			}
		}
		/*************************************************************/
		Record record = new Record(vm.getValues());
		if (isAltraNumerazione()) {
			if (isConProtocolloGenerale()) {
				protocolloGeneraleForm.setVisible(true);
				if(record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
					registrazioneSecondariaForm.setVisible(true);
				} else {
					registrazioneSecondariaForm.setVisible(false);
				}	
				if(isConAltraNumerazioneUD()) {
					altreNumerazioniUDForm.setVisible(true);
				} else {
					altreNumerazioniUDForm.setVisible(false);
				}
				altraNumerazioneForm.setVisible(false);
				altraNumerazioneProvvisoriaForm.setVisible(false);						
			} else {				
				protocolloGeneraleForm.setVisible(false);
				registrazioneSecondariaForm.setVisible(false);
				if(isConAltraNumerazioneUD()) {
					altreNumerazioniUDForm.setVisible(true);
				} else {
					altreNumerazioniUDForm.setVisible(false);
				}
				altraNumerazioneForm.setVisible(true);
				if(record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
					altraNumerazioneProvvisoriaForm.setVisible(true);
				} else {
					altraNumerazioneProvvisoriaForm.setVisible(false);
				}
			}			
		} else {
			protocolloGeneraleForm.setVisible(true);
			if(record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
				registrazioneSecondariaForm.setVisible(true);
			} else {
				registrazioneSecondariaForm.setVisible(false);
			}
			if(isConAltraNumerazioneUD()) {
				altreNumerazioniUDForm.setVisible(true);
			} else {
				altreNumerazioniUDForm.setVisible(false);
			}
			altraNumerazioneForm.setVisible(false);
			altraNumerazioneProvvisoriaForm.setVisible(false);		
		} 
		if (detailSectionNuovaRegistrazioneProtGenerale != null) {
			detailSectionNuovaRegistrazioneProtGenerale.hide();
		}
		if (mittentiItem != null) {
			mittentiItem.setShowFlgAssegnaAlMittente(detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento"));
		}
		if (destinatariItem != null) {
			destinatariItem.setShowFlgAssegnaAlDestinatario(detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento"));
		}
		if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
			((AllegatiItem)fileAllegatiItem).setDetailRecord(detailRecord);
		}
		if(filePrimarioOmissisItem != null) { 
			filePrimarioOmissisItem.setDetailRecord(detailRecord);
		}
		if (detailSectionAssegnazione != null) {
			detailSectionAssegnazione.setTitle(I18NUtil.getMessages().protocollazione_detail_assegnazioneForm_title_readonly());
			if(isProtocollazioneDetailBozze() || (isProtocollazioneDetailUscita() && AurigaLayout.isAttivoClienteA2A())) {
				detailSectionAssegnazione.hide();				
			} else {
				detailSectionAssegnazione.show();				
			}				
			if (assegnazioneItem != null) {
				assegnazioneItem.show();
			}
			if (assegnazioneSalvataItem != null) {
				assegnazioneSalvataItem.hide();
			}
		}
		if (detailSectionCondivisione != null) {
			detailSectionCondivisione.setTitle(I18NUtil.getMessages().protocollazione_detail_condivisioneForm_title_readonly());
			if(isProtocollazioneDetailBozze()) {
				detailSectionCondivisione.hide();				
			} else {
				detailSectionCondivisione.show();				
			}
			if (condivisioneSalvataItem != null) {
				condivisioneSalvataItem.hide();
			}
		}
		if(showRispondiButton(detailRecord) && rispondiButton !=  null) {
			rispondiButton.show();
		} else if (rispondiButton !=  null) {
			rispondiButton.hide();
		}
		if (detailSectionFolderCustom != null) {
			if(showDetailSectionFolderCustom()) {
				detailSectionFolderCustom.show();
			} else {
				detailSectionFolderCustom.hide();
			}
		}
		if (detailSectionTipologieParticolariA2A != null) {
			if(showDetailSectionTipologieParticolariA2A()) {
				detailSectionTipologieParticolariA2A.show();
			} else {
				detailSectionTipologieParticolariA2A.hide();
			}
		}
	} 

	/**
	 * Metodo che imposta la maschera in modalità edit (modifica del dettaglio)
	 * 
	 */
	@Override
	public void editMode() {

		this.mode = "edit";
		setCanEdit(true);
		setInitialValues();		
		showHideMainToolStrip();
		Record detailRecord = new Record(vm.getValues());
		salvaRegistraButton.setTitle(getTitleSalvaButton());
		salvaRegistraButton.setIcon(getIconSalvaButton());
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		smistaButton.hide();
		smistaCCButton.hide();
		invioAlProtocolloButton.hide();
		frecciaInvioAlProtocolloButton.hide();
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
//		assegnazioneButton.hide();
//		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
//		condivisioneButton.hide();		
//		frecciaCondivisioneButton.hide();
		stampaCopertinaButton.hide();
		stampaRicevutaButton.hide();
		stampaMenuButton.hide();
		nuovaProtButton.hide();
		nuovaProtComeCopiaButton.hide();
		salvaRegistraButton.show();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		frecciaModificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		invioPECButton.hide();
		invioMailRicevutaButton.hide();
		invioPEOButton.hide();		
		invioPostaPrioritariaButton.hide();
		invioRaccomandataButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneFirmaProtocollazioneButton.hide();
		apposizioneVistoButton.hide();
		rifiutoApposizioneVistoButton.hide();
		pubblicazioneTraspAmmButton.hide();
		if (isProtocollazioneDetailBozze() || isProtocollazioneDetailAtti() /*|| isIstanzeDetail()*/) {
			verificaRegistrazioneButton.hide();
		} else {
			verificaRegistrazioneButton.show();
		}
		revocaAttoButton.hide();
		protocollazioneEntrataButton.hide();
		protocollazioneUscitaButton.hide();
		protocollazioneInternaButton.hide();
		salvaComeModelloButton.hide();
		reloadDetailButton.show();
		undoButton.show();
		if (detailSectionRegistrazione != null) {
			if(showDetailSectionRegistrazione()) {
				detailSectionRegistrazione.show();
			} else {
				detailSectionRegistrazione.hide();
			}
		}
		if (detailSectionNuovaRegistrazione != null) {
			detailSectionNuovaRegistrazione.hide();
		}
		/*********** Per rich. accesso civico e contratti ************/
		if (detailSectionControinteressati != null) {
			if (showDetailSectionControinteressati()) {
				detailSectionControinteressati.show();
			} else {
				detailSectionControinteressati.hide();
			}
		}		
		if (detailSectionCollocazioneFisica != null) {
			if (showDetailSectionCollocazioneFisica()) {
				detailSectionCollocazioneFisica.show();
			} else {
				detailSectionCollocazioneFisica.hide();
			}
		}
		/*************************************************************/
		Record record = new Record(vm.getValues());
		if (isAltraNumerazione()) {
			if (isConProtocolloGenerale()) {
				protocolloGeneraleForm.setVisible(true);
				if(record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
					registrazioneSecondariaForm.setVisible(true);
				} else {
					registrazioneSecondariaForm.setVisible(false);
				}		
				altraNumerazioneForm.setVisible(false);
				altraNumerazioneProvvisoriaForm.setVisible(false);						
			} else {
				if (getUoProtocollanteValueMap().size() > 1) {
					mainToolStrip.show(); // Visualizzo la barra superiore in cui è presente la select per selezionare l'U.O.
				}
				// se non è associato un protocollo generale
				if (detailSectionNuovaRegistrazione != null) {
					if (showDetailSectionNuovaRegistrazione()) {
						detailSectionNuovaRegistrazione.show();
					}
				}
				protocolloGeneraleForm.setVisible(false);
				registrazioneSecondariaForm.setVisible(false);
				altraNumerazioneForm.setVisible(true);
				if(record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
					altraNumerazioneProvvisoriaForm.setVisible(true);
				} else {
					altraNumerazioneProvvisoriaForm.setVisible(false);
				}
			}			
		} else {
			protocolloGeneraleForm.setVisible(true);
			if(record.getAttributeAsString("siglaNumerazioneSecondaria") != null && !"".equals(record.getAttributeAsString("siglaNumerazioneSecondaria"))) {
				registrazioneSecondariaForm.setVisible(true);
			} else {
				registrazioneSecondariaForm.setVisible(false);
			}
			altraNumerazioneForm.setVisible(false);
			altraNumerazioneProvvisoriaForm.setVisible(false);		
		}		
		if (detailSectionNuovaRegistrazioneProtGenerale != null) {
			if (showDetailSectionNuovaRegistrazioneProtGenerale()) {
				detailSectionNuovaRegistrazioneProtGenerale.setTitle("Aggiungi registrazione");
				detailSectionNuovaRegistrazioneProtGenerale.show();
			} else {
				detailSectionNuovaRegistrazioneProtGenerale.hide();
			}
		}
		if (mittentiItem != null) {
			mittentiItem.setShowFlgAssegnaAlMittente(detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento"));
		}
		if (destinatariItem != null) {
			destinatariItem.setShowFlgAssegnaAlDestinatario(detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento"));
		}
		if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
			((AllegatiItem)fileAllegatiItem).setDetailRecord(detailRecord);
		}
		if(filePrimarioOmissisItem != null) {
			filePrimarioOmissisItem.setDetailRecord(detailRecord);
		}
		if (detailSectionAssegnazione != null) {
			detailSectionAssegnazione.setTitle(I18NUtil.getMessages().protocollazione_detail_assegnazioneForm_title());
			if(isProtocollazioneDetailBozze() || (isProtocollazioneDetailUscita() && AurigaLayout.isAttivoClienteA2A())) {
				detailSectionAssegnazione.hide();
			} else {
				detailSectionAssegnazione.show();
			}
			if (assegnazioneItem != null) {
				if (detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")) {				
					assegnazioneItem.show();
				} else {
					assegnazioneItem.hide();
				}
			}
			RecordList listaAssegnazioni = detailRecord.getAttributeAsRecordList("listaAssegnazioni");
			String idUserConfermaAssegnazione = detailRecord.getAttribute("idUserConfermaAssegnazione");
			boolean presentiAssegnatari = (listaAssegnazioni != null && !listaAssegnazioni.isEmpty());
			boolean assegnazioniDaConfermare = presentiAssegnatari
					&& (idUserConfermaAssegnazione != null && !"".equals(idUserConfermaAssegnazione));
			if (instance instanceof ProtocollazioneDetailBozze || !assegnazioniDaConfermare) {
				if(assegnazioneItem != null) {
					if(assegnazioneItem.getValueAsRecordList() != null && assegnazioneItem.getValueAsRecordList().getLength() > 0 && assegnazioneItem.hasValue()) {
						Record lRecord = new Record();
						lRecord.setAttribute("listaAssegnazioniSalvate", assegnazioneItem.getValueAsRecordList());
						lRecord.setAttribute("listaAssegnazioni", new RecordList());
						assegnazioneForm.setValues(lRecord.toMap());
						assegnazioneItem.resetCanvasChanged();
						detailSectionAssegnazione.open();					
					}
					// se ho gia un assegnatario salvato ed è attivo l'assegnatario unico nascondo la lista delle assegnazioni
					// è sbagliato fare il controllo sulla visibilità, perchè se ha un valore poi lo mostrerò sicuramente (vedi un paio di condizioni sotto a questa)
					// quindi se ho assegnatario unico devo sempre nascondere assegnazioneItem, anche se assegnazioneSalvataItem ha un valore ma in questo punto è invisibile
					// if (assegnazioneItem.getAssegnatarioUnico() && assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible() && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
					if (assegnazioneItem.getAssegnatarioUnico() && assegnazioneSalvataItem != null && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
						assegnazioneItem.drawAndSetValue(new RecordList());
						assegnazioneItem.hide();									
					}
				}
			}
			if (!isProtocollazioneDetailBozze()) {
				if (presentiAssegnatari && (idUserConfermaAssegnazione == null || "".equals(idUserConfermaAssegnazione))) {
					confermaAssegnazioneForm.hide();
					confermaAssegnazioneForm.redraw();
					utentiAbilCPAItem.hide();
					flgPreviaConfermaAssegnazioneItem.hide();
				}
			}			
			if (assegnazioneSalvataItem != null) {
				if(assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) {
					assegnazioneSalvataItem.show();				
				} else {
					assegnazioneSalvataItem.hide();
				}
			}
			if(!assegnazioneItem.getVisible() && !assegnazioneSalvataItem.getVisible()) {
				detailSectionAssegnazione.hide();					
			}
		}		
		if (detailSectionCondivisione != null) {
			detailSectionCondivisione.setTitle(I18NUtil.getMessages().protocollazione_detail_condivisioneForm_title());		
			if(isProtocollazioneDetailBozze()) {
				detailSectionCondivisione.hide();
			} else {
				detailSectionCondivisione.show();
			}
			if(condivisioneItem.getValueAsRecordList() != null && condivisioneItem.getValueAsRecordList().getLength() > 0 && condivisioneItem.hasValue()) {				
				Record lRecord = new Record();
				lRecord.setAttribute("listaDestInvioCCSalvati", condivisioneItem.getValueAsRecordList());
				lRecord.setAttribute("listaDestInvioCC", new RecordList());
				condivisioneForm.setValues(lRecord.toMap());
				condivisioneItem.resetCanvasChanged();
				detailSectionCondivisione.open();				
			}				
			if (condivisioneSalvataItem != null) {
				if(condivisioneSalvataItem.getValueAsRecordList() != null && condivisioneSalvataItem.getValueAsRecordList().getLength() > 0 && condivisioneSalvataItem.hasValue()) {
					condivisioneSalvataItem.show();
				} else {
					condivisioneSalvataItem.hide();
				}
			}	
		}		
		if (detailSectionFolderCustom != null) {
			if(showDetailSectionFolderCustom()) {
				detailSectionFolderCustom.show();
			} else {
				detailSectionFolderCustom.hide();
			}
		}
		if (detailSectionTipologieParticolariA2A != null) {
			if(showDetailSectionTipologieParticolariA2A()) {
				detailSectionTipologieParticolariA2A.show();
			} else {
				detailSectionTipologieParticolariA2A.hide();
			}
		}
		if (isProtocollazioneBozza()) {
			reloadDetailButton.hide();
			undoButton.hide();
			salvaComeModelloButton.hide();
		}
	}
	
	/**
	 * Metodo che imposta la maschera in modalità di modifica registrazione accesso civico 
	 */
	public void modificaRegAccessoCivicoMode() {
		modificaDatiMode("modificaRegAccessoCivico", null);
	}
	
	/**
	 * Metodo che imposta la maschera in modalità di modifica dati extra iter 
	 */
	public void modificaDatiExtraIterMode() {
		this.editMode = "modificaDatiExtraIter";
		setModificaDatiReg(false);
		editMode();
		setCanEdit(false);
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				detail.setCanEdit(true);
			}
		}
	}

	/**
	 * Metodo che imposta la maschera in modalità di modifica (senza
	 * l'abilitazione ad aggiungere file)
	 * 
	 */
	public void modificaDatiMode() {
		modificaDatiMode("modificaDati", null);
	}
	
	
	public void modificaDatiMode(Boolean abilAggiuntaFile) {
		modificaDatiMode("modificaDati", abilAggiuntaFile);
	}

	/**
	 * Metodo che imposta la maschera in modalità di modifica (con o senza
	 * l'abilitazione ad aggiungere file, a seconda del parametro passato in
	 * input)
	 * 
	 */
	public void modificaDatiMode(String editMode, Boolean abilAggiuntaFile) {

		this.editMode = editMode;
		setModificaDatiReg(false);
		editMode();
		if (mittentiItem != null) {
			mittentiItem.setCanEdit(false);
		}
		if (mittentiForm != null) {
			setCanEdit(false, mittentiForm);
		}		
		if (destinatariItem != null) {
			destinatariItem.setCanEdit(false);
			if (isProtocollazioneDetailUscita()) {
				destinatariItem.setCanEditMezzoTrasmissioneMode(false);
			}
		}
		if (destinatariForm != null) {
			setCanEdit(false, destinatariForm);
		}
		if (codRapidoOggettoItem != null) {
			codRapidoOggettoItem.setCanEdit(false);
		}
		if (oggettoItem != null) {
			oggettoItem.setCanEdit(false);
		}
		if (lookupTemplateOggettoButton != null) {
			lookupTemplateOggettoButton.setCanEdit(false);
		}
		if (salvaComeTemplateOggettoButton != null) {
			salvaComeTemplateOggettoButton.setCanEdit(false);
		}
		if (generaDaModelloButton != null) {
			generaDaModelloButton.setCanEdit(false);
		}
		if(docPressoCentroPostaItem != null) {
			docPressoCentroPostaItem.setCanEdit(false);
		}
		if (regEmergenzaForm != null) {
			setCanEdit(false, regEmergenzaForm);
		}		
		if (datiRicezioneForm1 != null) {
			setCanEdit(false, datiRicezioneForm1);
			if(dataEOraSpedizioneItem != null) {
				dataEOraSpedizioneItem.setCanEdit(true);
			}
		}
		if (datiRicezioneForm2 != null) {
			setCanEdit(false, datiRicezioneForm2);
		}
		Record record = new Record(vm.getValues());
		if (abilAggiuntaFile == null) {
			abilAggiuntaFile = record.getAttributeAsBoolean("abilAggiuntaFile");
		}		
		if (AurigaLayout.getParametroDBAsBoolean("FILE_REG_IN_UNICA_SOLUZIONE") && !isProtocollazioneDetailBozze() && hasFile()) {
			nomeFilePrimarioItem.setCanEdit(false);
			filePrimarioButtons.setCanEdit(false);
			uploadFilePrimarioItem.setCanEdit(false);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(false);
			}
			flgDatiSensibiliItem.setCanEdit(false);
			filePrimarioOmissisItem.setCanEditProtocollo(false);
			fileAllegatiItem.setCanEdit(false);
		} else {
			if (abilAggiuntaFile && (isProtocollazioneDetailBozze()	|| (uriFilePrimarioItem.getValue() == null || uriFilePrimarioItem.getValue().equals("")))) {
				nomeFilePrimarioItem.setCanEdit(true);
				filePrimarioButtons.setCanEdit(true);
				uploadFilePrimarioItem.setCanEdit(true);
				if (generaDaModelloButton != null) {
					generaDaModelloButton.setCanEdit(true);
				}								
			} else {
				nomeFilePrimarioItem.setCanEdit(false);
				filePrimarioButtons.setCanEdit(false);
				uploadFilePrimarioItem.setCanEdit(false);
				if (generaDaModelloButton != null) {
					generaDaModelloButton.setCanEdit(false);
				}							
			}
			if (filePrimarioOmissisItem.isNotNull()) {
				filePrimarioOmissisItem.setCanEditProtocollo(false);
			} else {
				filePrimarioOmissisItem.setCanEditProtocollo(true);
			}	
			if (abilAggiuntaFile) {
				if(fileAllegatiItem != null) {
					if(fileAllegatiItem instanceof AllegatiGridItem) {
						((AllegatiGridItem)fileAllegatiItem).setAggiuntaFileMode();
					} else if(fileAllegatiItem instanceof AllegatiItem) {
						((AllegatiItem)fileAllegatiItem).setAggiuntaFileMode();
					}
				}
			} else {
				fileAllegatiItem.setCanEdit(false);
			}		
		}
		forceToShowElimina = false;
		// if(isFromEmail()) {
		// nomeFilePrimarioItem.setCanEdit(true);
		// filePrimarioButtons.setCanEdit(true);
		// uploadFilePrimarioItem.setCanEdit(true);
		// Record record = new Record(getValuesManager().getValues());
		// boolean abilAggiuntaFile =
		// record.getAttributeAsBoolean("abilAggiuntaFile");
		// if(abilAggiuntaFile) {
		// forceToShowElimina = true;
		// fileAllegatiItem.setNotCanEditFileMode(true);
		// } else {
		// fileAllegatiItem.setCanEdit(false);
		// }
		// }
	}

	/**
	 * Metodo che imposta la maschera in modalità di modifica dei dati di
	 * registrazione
	 * 
	 */
	public void modificaDatiRegMode() {

		this.editMode = "modificaDatiReg";
		setModificaDatiReg(true);
		editMode();
		if (AurigaLayout.getParametroDBAsBoolean("FILE_REG_IN_UNICA_SOLUZIONE") && !isProtocollazioneDetailBozze() && hasFile()) {
			nomeFilePrimarioItem.setCanEdit(false);
			filePrimarioButtons.setCanEdit(false);
			uploadFilePrimarioItem.setCanEdit(false);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(false);
			}
			flgDatiSensibiliItem.setCanEdit(false);
			filePrimarioOmissisItem.setCanEditProtocollo(false);
			fileAllegatiItem.setCanEdit(false);
		}
		forceToShowElimina = false;
		// if(isFromEmail()) {
		// nomeFilePrimarioItem.setCanEdit(false);
		// filePrimarioButtons.setCanEdit(false);
		// uploadFilePrimarioItem.setCanEdit(false);
		// forceToShowElimina = !isEmailInterop();
		// fileAllegatiItem.setNotCanEditFileMode(!isEmailInterop());
		// }
	}

	/**
	 * Metodo che imposta la maschera in modalità di aggiunta file
	 * 
	 */
	public void aggiuntaFileMode() {

		this.editMode = "aggiuntaFile";
		setModificaDatiReg(false);
		editMode();
		setCanEdit(false);
		if (AurigaLayout.getParametroDBAsBoolean("FILE_REG_IN_UNICA_SOLUZIONE") && !isProtocollazioneDetailBozze() && hasFile()) {
			nomeFilePrimarioItem.setCanEdit(false);
			filePrimarioButtons.setCanEdit(false);
			uploadFilePrimarioItem.setCanEdit(false);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(false);
			}
			flgDatiSensibiliItem.setCanEdit(false);
			filePrimarioOmissisItem.setCanEditProtocollo(false);
			fileAllegatiItem.setCanEdit(false);
		} else { 	
			if (isProtocollazioneDetailBozze()	|| (uriFilePrimarioItem.getValue() == null || uriFilePrimarioItem.getValue().equals(""))) {
				nomeFilePrimarioItem.setCanEdit(true);
				filePrimarioButtons.setCanEdit(true);
				uploadFilePrimarioItem.setCanEdit(true);
				generaDaModelloButton.setCanEdit(true);
			} else {
				nomeFilePrimarioItem.setCanEdit(false);
				filePrimarioButtons.setCanEdit(false);
				uploadFilePrimarioItem.setCanEdit(false);
				generaDaModelloButton.setCanEdit(false);
			}
			if (filePrimarioOmissisItem.isNotNull()) {
				filePrimarioOmissisItem.setCanEditProtocollo(false);
			} else {
				filePrimarioOmissisItem.setCanEditProtocollo(true);
			}
			if(fileAllegatiItem != null) {
				if(fileAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)fileAllegatiItem).setAggiuntaFileMode();
				} else if(fileAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)fileAllegatiItem).setAggiuntaFileMode();
				}
			}
		}				
		forceToShowElimina = false;
		// if(isFromEmail()) {
		// nomeFilePrimarioItem.setCanEdit(true);
		// filePrimarioButtons.setCanEdit(true);
		// uploadFilePrimarioItem.setCanEdit(true);
		// forceToShowElimina = true;
		// fileAllegatiItem.setNotCanEditFileMode(true);
		// }
	}

	/**
	 * Metodo che imposta la maschera in modalità di protocollazione dell'e-mail
	 * 
	 */
	public void protocollaMailMode() {

		this.editMode = "protocollaMail";
		setInitialValues();
		salvaRegistraButton.setTitle(getTitleRegistraButton());
		salvaRegistraButton.setIcon(getIconRegistraButton());
		if (detailSectionFolderCustom != null) {
			if (showDetailSectionFolderCustom()) {
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_FLD_CUSTOM_IN_NEW_PROT")) {
					detailSectionFolderCustom.show();
				} else {
					detailSectionFolderCustom.hide();
				}
			} else {
				detailSectionFolderCustom.hide();
			}
		}
		if (detailSectionTipologieParticolariA2A != null) {
			if(showDetailSectionTipologieParticolariA2A()) {
				detailSectionTipologieParticolariA2A.show();
			} else {
				detailSectionTipologieParticolariA2A.hide();
			}
		}
		forceToShowElimina = false;
		if (isFromEmail()) {
			nomeFilePrimarioItem.setCanEdit(true);
			filePrimarioButtons.setCanEdit(true);
			uploadFilePrimarioItem.setCanEdit(true);
			forceToShowElimina = true;
		}
	}

	/**
	 * Metodo che imposta la maschera in modalità di protocollazione di un
	 * registro di emergenza
	 * 
	 */
	public void protocollaRegEmergenzaMode() {

		this.editMode = "protocollaRegEmergenza";
		setInitialValues();
		rifRegEmergenzaItem.setCanEdit(false);
		nroRegEmergenzaItem.setCanEdit(false);
		dataRegEmergenzaItem.setCanEdit(false);
		salvaRegistraButton.setTitle(getTitleRegistraButton());
		salvaRegistraButton.setIcon(getIconRegistraButton());
		if (detailSectionFolderCustom != null) {
			if (showDetailSectionFolderCustom()) {
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_FLD_CUSTOM_IN_NEW_PROT")) {
					detailSectionFolderCustom.show();
				} else {
					detailSectionFolderCustom.hide();
				}
			} else {
				detailSectionFolderCustom.hide();
			}
		}
		if (detailSectionTipologieParticolariA2A != null) {
			if(showDetailSectionTipologieParticolariA2A()) {
				detailSectionTipologieParticolariA2A.show();
			} else {
				detailSectionTipologieParticolariA2A.hide();
			}
		}
	}
	
	/**
	 * Metodo che imposta la maschera in modalità di associazione immagine/i al protocollo
	 * 
	 */
	public void caricaDatiImgAssocMode() {
		this.editMode = "caricaDatiImgAssoc";
		setModificaDatiReg(true);
		editMode();
		if (AurigaLayout.getParametroDBAsBoolean("FILE_REG_IN_UNICA_SOLUZIONE") && !isProtocollazioneDetailBozze() && hasFile()) {
			nomeFilePrimarioItem.setCanEdit(false);
			filePrimarioButtons.setCanEdit(false);
			uploadFilePrimarioItem.setCanEdit(false);
			if (generaDaModelloButton != null) {
				generaDaModelloButton.setCanEdit(false);
			}
			flgDatiSensibiliItem.setCanEdit(false);
			filePrimarioOmissisItem.setCanEditProtocollo(false);
			fileAllegatiItem.setCanEdit(false);
		}
		forceToShowElimina = false;
		reloadDetailButton.hide();
		verificaRegistrazioneButton.hide();
		// if(isFromEmail()) {
		// nomeFilePrimarioItem.setCanEdit(false);
		// filePrimarioButtons.setCanEdit(false);
		// uploadFilePrimarioItem.setCanEdit(false);
		// forceToShowElimina = !isEmailInterop();
		// fileAllegatiItem.setNotCanEditFileMode(!isEmailInterop());
		// }	
		
	}

	/**
	 * Metodo che indica se aggiungere in automatico la prima riga nelle sezioni ripetute vuote quando le apro
	 * 
	 */
	public boolean getShowFirstCanvasWhenEmptyAfterOpen() {
		return true;
	}
	
	/*********************************
	 * METODI PER ASSEGNATARIO UNICO *
	 *********************************/
	
	public boolean isAttivoAssegnatarioUnico() {		
		if (getFlgTipoProv() != null && "E".equalsIgnoreCase(getFlgTipoProv())) {
			return AurigaLayout.getParametroDBAsBoolean("ASSEGNATARIO_UNICO_ENTRATA");
		}
		return false;					
	}
	
	public void manageChangeFlgAssegnaAlMittDest(ChangeEvent event) {
		if(event.getValue() != null && (Boolean)event.getValue()) {	
			if (isAttivoAssegnatarioUnico()) {
				int nroAssegnazioni = getNroAssegnazioni();
				if(nroAssegnazioni > 0) {
					event.cancel();		
					AurigaLayout.addMessage(new MessageBean("Si può mettere un solo assegnatario", "", MessageType.ERROR));
				}
			}			
		}
	}
	
	public void manageChangedFlgAssegnaAlMittDestWizard() {		
		if (assegnazioneForm != null) {
			assegnazioneForm.clearErrors(true);
			assegnazioneForm.redraw();
		}	
		showHideDetailSectionAssegnazione();		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {	
				if (assegnazioneItem != null) {
					assegnazioneItem.redraw();
					assegnazioneItem.validate();	
				}
			}
		});
	}
	
	public void manageChangedFlgAssegnaAlMittDest() {
		manageChangedFlgAssegnaAlMittDest(null);
	}
	
	public void manageChangedFlgAssegnaAlMittDest(final ServiceCallback<Record> callback) {
		if(isRequiredDetailSectionAssegnazione()) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {	
					if (assegnazioneForm != null) {
						assegnazioneForm.clearErrors(true);
						assegnazioneForm.redraw();
					}	
					if(callback != null) {
						callback.execute(null);
					}
				}
			});	
		}
	}
	
	public void showHideDetailSectionAssegnazione() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {	
				if (detailSectionAssegnazione != null) {
					detailSectionAssegnazione.show();
				}
				if (mode == null || mode.equals("new")) {		
					int nroMittentiAssegnatari = getNroMittentiAssegnatari();
					int nroDestinatariAssegnatari = getNroDestinatariAssegnatari();
					if(nroMittentiAssegnatari > 0 || nroDestinatariAssegnatari > 0) {	
						if(isAttivoAssegnatarioUnico()) {		
							int nroAssegnatari = getNroAssegnatari();
							if(nroAssegnatari == 0) {
								if (detailSectionAssegnazione != null) {
									detailSectionAssegnazione.hide();
								}
							}
						}
					}
				}
				afterShowHideDetailSectionAssegnazione();
			}
		});
	}
	
	public void afterShowHideDetailSectionAssegnazione() {
		
	}
	
	public String getIdAssegnatarioFromRecordAssegnazione(Record record) {
		if("LD".equalsIgnoreCase(record.getAttribute("tipo"))) {
			return "G" + record.getAttribute("gruppo");				
		} else if("PREF".equalsIgnoreCase(record.getAttribute("tipo"))) {
			return record.getAttribute("typeNodo") + record.getAttribute("idUo");
		} else {
			return record.getAttribute("organigramma");
		}			
	}
	
	public int getNroAssegnatari() {
		RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
		int nroAssegnatari = 0;
		if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
			if(listaAssegnazioni != null) {
				for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						nroAssegnatari++;
					}
				}
			}			
		}
		return nroAssegnatari;
	}
	
	public int getNroAssegnatariSalvati() {
		RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
		int nroAssegnatariSalvati = 0;
		if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
			if(listaAssegnazioniSalvate != null) {
				for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));						
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						nroAssegnatariSalvati++;								
					}
				}
			}													
		}	
		return nroAssegnatariSalvati;
	}
			
	public int getNroMittentiAssegnatari() {
		int nroMittentiAssegnatari = 0;
		RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
		if (listaMittenti != null) {
			for(int i = 0; i < listaMittenti.getLength(); i++) {
				if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
					String idAssegnatario = null;
					if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
						idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
						idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
						idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
					} 
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
						nroMittentiAssegnatari++;
					}
				}
			}
		}
		return nroMittentiAssegnatari;
	}
			
	public int getNroDestinatariAssegnatari() {
		int nroDestinatariAssegnatari = 0;
		if(showDetailSectionDestinatari()) {
			RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
			if (listaDestinatari != null) {
				for(int i = 0; i < listaDestinatari.getLength(); i++) {
					if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
						String idAssegnatario = null;
						if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							nroDestinatariAssegnatari++;
						}						
					}
				}
			}
		}
		return nroDestinatariAssegnatari;
	}
	
	public String getIdAssegnatarioUnicoDaSalvare() {
		RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
		if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
			if(listaAssegnazioniSalvate != null && listaAssegnazioniSalvate.getLength() > 0) {
				return null;
			}		
		} else if(getNroAssegnazioni() == 1) {
			RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
			if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
				if(listaAssegnazioni != null) {
					for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							return idAssegnatario;
						}
					}
				}			
			}
			RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null) {
				for(int i = 0; i < listaMittenti.getLength(); i++) {
					if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
						String idAssegnatario = null;
						if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							return idAssegnatario;
						}
					}
				}
			}
			if(showDetailSectionDestinatari()) {
				RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
				if (listaDestinatari != null) {
					for(int i = 0; i < listaDestinatari.getLength(); i++) {
						if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
							String idAssegnatario = null;
							if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
								idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
							} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
								idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
							} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
								idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
							} 
							if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
								return idAssegnatario;
							}						
						}
					}
				}
			}
		}
		return null;
	}
			
	public int getNroAssegnazioni() {
		HashSet<String> assegnazioni = new HashSet<String>();
		RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
		int nroAssegnatariSalvati = 0;
		if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
			if(listaAssegnazioniSalvate != null) {
				for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroAssegnatariSalvati++;
						}
					}
				}
			}		
		}
		RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
		int nroAssegnatari = 0;
		if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
			if(listaAssegnazioni != null) {
				for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
					String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroAssegnatari++;
						}
					}
				}
			}			
		}
		int nroMittentiAssegnatari = 0;
		RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
		if (listaMittenti != null) {
			for(int i = 0; i < listaMittenti.getLength(); i++) {
				if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
					String idAssegnatario = null;
					if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
						idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
						idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
					} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
						idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
					} 
					if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
						if(!assegnazioni.contains(idAssegnatario)) {
							assegnazioni.add(idAssegnatario);
							nroMittentiAssegnatari++;
						}
					}
				}
			}
		}
		int nroDestinatariAssegnatari = 0;
		if(showDetailSectionDestinatari()) {
			RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
			if (listaDestinatari != null) {
				for(int i = 0; i < listaDestinatari.getLength(); i++) {
					if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
						String idAssegnatario = null;
						if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroDestinatariAssegnatari++;
							}
						}						
					}
				}
			}
		}
		int nroAssegnazioni = nroAssegnatariSalvati + nroAssegnatari + nroMittentiAssegnatari + nroDestinatariAssegnatari;
		return nroAssegnazioni;
	}
	
	public boolean validateAssegnazioni() {
		boolean valid = true;
		if (mode == null || mode.equals("new") || mode.equals("edit")) {						
			HashSet<String> assegnazioni = new HashSet<String>();
			RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
			int nroAssegnatariSalvati = 0;
			if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
				if(listaAssegnazioniSalvate != null) {
					for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatariSalvati++;
							}
						}
					}
				}													
			}
			RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
			int nroAssegnatari = 0;
			if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
				if(listaAssegnazioni != null) {
					for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatari++;
							}
						}
					}
				}							
			}
			int nroMittentiAssegnatari = 0;
			RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null) {
				for(int i = 0; i < listaMittenti.getLength(); i++) {
					if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
						String idAssegnatario = null;
						if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroMittentiAssegnatari++;
							}
						}
					}
				}
			}			
			int nroDestinatariAssegnatari = 0;
			if(showDetailSectionDestinatari()) {
				RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
				if (listaDestinatari != null) {
					for(int i = 0; i < listaDestinatari.getLength(); i++) {
						if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
							String idAssegnatario = null;
							if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
								idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
							} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
								idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
							} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
								idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
							} 
							if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
								if(!assegnazioni.contains(idAssegnatario)) {
									assegnazioni.add(idAssegnatario);
									nroDestinatariAssegnatari++;
								}
							}						
						}
					}
				}
			}
			int nroAssegnazioni = nroAssegnatariSalvati + nroAssegnatari + nroMittentiAssegnatari + nroDestinatariAssegnatari;
			if(isRequiredDetailSectionAssegnazione()) {
				if (nroAssegnazioni == 0) {
					if (isOpenableDetailSection(detailSectionAssegnazione)) {
						detailSectionAssegnazione.open();
						assegnazioneForm.setFieldErrors("listaAssegnazioni", "Obbligatorio inserire almeno un assegnatario");
					}
					valid = false;					
				}
			}
			if (isAttivoAssegnatarioUnico()) {				
				if (nroAssegnazioni > 0) {
					if (nroAssegnazioni == 1 && nroAssegnatari == 1 && !validateAssegnatario(listaAssegnazioni.get(0))) {
						if (isOpenableDetailSection(detailSectionAssegnazione)) {
							detailSectionAssegnazione.open();
							assegnazioneForm.setFieldErrors("listaAssegnazioni", "Si può mettere un assegnatario che sia in organigramma e che sia\na) un punto di protocollo\nb) una UO/postazione che abbia come punto di protocollo collegato la UO che sta protocollando");
						}
						valid = false;
					} else if (nroAssegnazioni > 1 && nroAssegnazioni > nroAssegnatariSalvati) {
//							if (nroAssegnatariSalvati > 0) {
//								if (isOpenableDetailSection(detailSectionAssegnazione)) {
//									detailSectionAssegnazione.open();
//									assegnazioneForm.setFieldErrors("listaAssegnazioniSalvate", "Si può mettere un solo assegnatario");
//								}
//							}
						if (nroAssegnatari > 0) {
							if (isOpenableDetailSection(detailSectionAssegnazione)) {
								detailSectionAssegnazione.open();
								assegnazioneForm.setFieldErrors("listaAssegnazioni", "Si può mettere un solo assegnatario");
							}
						}
						if (nroMittentiAssegnatari > 0) {
							if (isOpenableDetailSection(detailSectionMittenti)) {
								detailSectionMittenti.open();
								mittentiForm.setFieldErrors("listaMittenti", "Si può mettere un solo assegnatario");
							}
						}
						if (nroDestinatariAssegnatari > 0) {
							if(detailSectionDestinatari != null) {								
								if (isOpenableDetailSection(detailSectionDestinatari)) {
									detailSectionDestinatari.open();
									if(destinatariForm != null) {
										destinatariForm.setFieldErrors("listaDestinatari", "Si può mettere un solo assegnatario");
									}
								}
							}
						}
						valid = false;
					}
				}
			}
		}
		return valid;
	}
	
	public boolean valuesAreValidAssegnazioni() {
		boolean valid = true;
		if (mode == null || mode.equals("new") || mode.equals("edit")) {						
			HashSet<String> assegnazioni = new HashSet<String>();
			RecordList listaAssegnazioniSalvate = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioniSalvate");
			int nroAssegnatariSalvati = 0;
			if(assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible()) {
				if(listaAssegnazioniSalvate != null) {
					for(int n = 0; n < listaAssegnazioniSalvate.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioniSalvate.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatariSalvati++;
							}
						}
					}
				}													
			}
			RecordList listaAssegnazioni = new Record(vm.getValues()).getAttributeAsRecordList("listaAssegnazioni");
			int nroAssegnatari = 0;
			if(assegnazioneItem != null && assegnazioneItem.getVisible()) {
				if(listaAssegnazioni != null) {
					for(int n = 0; n < listaAssegnazioni.getLength(); n++) {
						String idAssegnatario = getIdAssegnatarioFromRecordAssegnazione(listaAssegnazioni.get(n));						
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroAssegnatari++;
							}
						}
					}
				}							
			}
			int nroMittentiAssegnatari = 0;
			RecordList listaMittenti = new Record(vm.getValues()).getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null) {
				for(int i = 0; i < listaMittenti.getLength(); i++) {
					if(listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente") != null && listaMittenti.get(i).getAttributeAsBoolean("flgAssegnaAlMittente")) {
						String idAssegnatario = null;
						if(listaMittenti.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUoSoggetto"))) {
							idAssegnatario = "UO" + listaMittenti.get(i).getAttribute("idUoSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idScrivaniaSoggetto"))) {
							idAssegnatario = "SV" + listaMittenti.get(i).getAttribute("idScrivaniaSoggetto");
						} else if(listaMittenti.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaMittenti.get(i).getAttribute("idUserSoggetto"))) {
							idAssegnatario = "UT" + listaMittenti.get(i).getAttribute("idUserSoggetto");
						} 
						if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
							if(!assegnazioni.contains(idAssegnatario)) {
								assegnazioni.add(idAssegnatario);
								nroMittentiAssegnatari++;
							}
						}
					}
				}
			}			
			int nroDestinatariAssegnatari = 0;
			if(showDetailSectionDestinatari()) {
				RecordList listaDestinatari = new Record(vm.getValues()).getAttributeAsRecordList("listaDestinatari");
				if (listaDestinatari != null) {
					for(int i = 0; i < listaDestinatari.getLength(); i++) {
						if(listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario") != null && listaDestinatari.get(i).getAttributeAsBoolean("flgAssegnaAlDestinatario")) {
							String idAssegnatario = null;
							if(listaDestinatari.get(i).getAttribute("idUoSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUoSoggetto"))) {
								idAssegnatario = "UO" + listaDestinatari.get(i).getAttribute("idUoSoggetto");
							} else if(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto"))) {
								idAssegnatario = "SV" + listaDestinatari.get(i).getAttribute("idScrivaniaSoggetto");
							} else if(listaDestinatari.get(i).getAttribute("idUserSoggetto") != null && !"".equals(listaDestinatari.get(i).getAttribute("idUserSoggetto"))) {
								idAssegnatario = "UT" + listaDestinatari.get(i).getAttribute("idUserSoggetto");
							} 
							if(idAssegnatario != null && !"".equals(idAssegnatario)) {					
								if(!assegnazioni.contains(idAssegnatario)) {
									assegnazioni.add(idAssegnatario);
									nroDestinatariAssegnatari++;
								}
							}						
						}
					}
				}
			}
			int nroAssegnazioni = nroAssegnatariSalvati + nroAssegnatari + nroMittentiAssegnatari + nroDestinatariAssegnatari;
			if(isRequiredDetailSectionAssegnazione()) {
				if (nroAssegnazioni == 0) {
					valid = false;
				}
			}
			if (isAttivoAssegnatarioUnico()) {	
				if (nroAssegnazioni > 0) {
					if (nroAssegnazioni == 1 && nroAssegnatari == 1 && !validateAssegnatario(listaAssegnazioni.get(0))) {						
						valid = false;
					} else if (nroAssegnazioni > 1 && nroAssegnazioni > nroAssegnatariSalvati) {
						valid = false;
					}
				}
			}
		}
		return valid;
	}

	public boolean validateAssegnatario(Record recordAssegnatario) {
		
		if (recordAssegnatario == null)
			return false;
		return true;
	}
	
	/*************************************************
	 * METODI RELATIVI ALLE AZIONI SUL FILE PRIMARIO *
	 *************************************************/

	/**
	 * Metodo che indica se c'è un file (primario o negli allegati)
	 * 
	 */
	public boolean hasFile() {
		return hasFilePrimario() || hasFileAllegati();		
	}
	
	public boolean hasFilePrimario() {
		boolean hasFilePrimario = false;
		String uriFilePrimario = filePrimarioForm.getValueAsString("uriFilePrimario");
		if (uriFilePrimario != null && !"".equals(uriFilePrimario)) {
			hasFilePrimario = true;
		}		
		return hasFilePrimario;
	}
	
	public boolean hasFileAllegati() {
		boolean hasFileAllegati = false;		
		RecordList listaAllegati = new Record(vm.getValues()).getAttributeAsRecordList("listaAllegati");
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				String uriFileAllegato = listaAllegati.get(i).getAttributeAsString("uriFileAllegato");
				if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
					hasFileAllegati = true;
					break;
				}
			}
		}		
		return hasFileAllegati;
	}
	
	/**
	 * Metodo che indica se è abilitata o meno l'acquisizione da scanner
	 * 
	 */
	public boolean enableAcquisisciDaScannerMenuItem() {
		return !isFromEmail() && showUploadFilePrimario();
	}

	public void clickAcquisisciDaScanner() {

		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(final String filePdf, final String uriPdf, String fileTif, String uriTif,
					String record) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", filePdf);
				filePrimarioForm.setValue("uriFilePrimario", uriPdf);
				filePrimarioForm.setValue("nomeFilePrimarioTif", fileTif);
				filePrimarioForm.setValue("uriFilePrimarioTif", uriTif);
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", filePdf);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriPdf);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}

	public void clickFirma() {

		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		// FirmaWindow firmaWindow = new FirmaWindow(uri, display,
		// lInfoFileRecord) {
		// @Override
		// public void firmaCallBack(String nomeFileFirmato, String
		// uriFileFirmato, String record) {
		// InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") !=
		// null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) :
		// null;
		// String precImpronta = precInfo != null ? precInfo.getImpronta() :
		// null;
		// InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
		// filePrimarioForm.setValue("infoFile", info);
		// if(precImpronta == null || !precImpronta.equals(info.getImpronta()))
		// {
		// manageChangedFilePrimario();
		// }
		// filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
		// filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
		// filePrimarioForm.setValue("nomeFilePrimarioTif", "");
		// filePrimarioForm.setValue("uriFilePrimarioTif", "");
		// filePrimarioForm.setValue("remoteUriFilePrimario", false);
		// if(filePrimarioForm != null) {
		//	 filePrimarioForm.markForRedraw();
		// }
		// if(filePrimarioButtons != null) {
		// 	 filePrimarioButtons.markForRedraw();
		// }
		// manageChangedPrimario();
		// changedEventAfterUpload(nomeFileFirmato,uriFileFirmato);
		// }
		// };
		// firmaWindow.show();
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
				filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}
	
	/**
	 * TIMBRA DATI SEGNATURA - DmpkRegistrazionedocGettimbrodigreg
	 */
	public void clickTimbraDatiSegnatura(String finalita) {
		String nomeFile = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		String remote = filePrimarioForm.getValueAsString("remoteUriFilePrimario");		
		InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		Record detailRecord = new Record(vm.getValues());
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocPrimario = filePrimarioForm != null ? filePrimarioForm.getValueAsString("idDocPrimario") : null;
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
						
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}
		
		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDocPrimario);
			record.setAttribute("nomeFile", nomeFile);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remote);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
				
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		}else{
				
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, nomeFile, Boolean.valueOf(remote), precInfo.getMimetype(), idUd, idDocPrimario, rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	/**
	 * TIMBRA DATI SEGNATURA - DmpkRegistrazionedocGettimbrospecxtipo
	 */
	public void clickTimbraDatiTipologia() {
		
		String idDocPrimario = new Record(vm.getValues()).getAttribute("idDocPrimario");
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			
			Record record = new Record();
			record.setAttribute("idDoc", idDocPrimario);
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			
			TimbroCopertinaUtil.buildDatiTipologia(record);
			
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
		
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
			copertinaTimbroWindow.show();
		}
	}
	
	
	//*************** OPERAZIONE DA DETTAGLIO UD - BARCODE SU A4 & BARCODE SU ETICHETTA ( Dati segnatura & Dati tipologia ) ***************
	
	/**
	 *  DATI SEGNATURA - DmpkRegistrazionedocGettimbrodigreg
	 *  @param provenienza = B: barcode & E: etichetta
	 */
	public void clickBarcodeDatiSegnatura(String tipologia,String posizione) {
		
		if(tipologia != null && !"".equals(tipologia) && "E".equals(tipologia)){
			
			clickBarcodeEtichettaDatiSegnatura(posizione);
		}else{
		
			Record detailRecord = new Record(vm.getValues());
			String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
			
			String numeroAllegato = null;
			if(posizione != null && "P".equals(posizione)){
				numeroAllegato = "0";
			}
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";		
			
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				
				Record record = new Record();
				record.setAttribute("idUd", idUd);
				record.setAttribute("numeroAllegato", numeroAllegato);
				record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
				record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
				record.setAttribute("skipScelteOpzioniCopertina", "true");
				
				TimbroCopertinaUtil.buildDatiSegnatura(record);
				
			}else{
				
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("idUd", idUd);
				copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				copertinaTimbroRecord.setAttribute("provenienza", "A");
				copertinaTimbroRecord.setAttribute("posizionale", posizione);
				
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean);
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 *  DATI TIPOLOGIA - DmpkRegistrazionedocGettimbrospecxtipo
	 */
	public void clickBarcodeDatiTipologia(String tipo){
		
		if(tipo != null && !"".equals(tipo) && "E".equals(tipo)){
			
			clickBarcodeEtichettaDatiTipologia();
		}else{
		
			Record detailRecord = new Record(vm.getValues());
			String idDocPrimario = detailRecord != null ? detailRecord.getAttribute("idDocPrimario") : null;
			String numeroAllegato = "";
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
			
			
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				
				Record record = new Record();
				record.setAttribute("idDoc", idDocPrimario);
				record.setAttribute("numeroAllegato", numeroAllegato);
				record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
				record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
				record.setAttribute("skipScelteOpzioniCopertina", "true");
				
				TimbroCopertinaUtil.buildDatiTipologia(record);
				
			}else{
				
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 * Barcode multipli su A4 - Dati segnatura & tipologia 
	 */
	public void clickBarcodeMultipli(String tipo,String posizionale){
		
		Record detailRecord = new Record(vm.getValues());
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocPrimario = new Record(vm.getValues()).getAttribute("idDocPrimario");
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
				
		String numeroAllegato = null;
		if(posizionale != null && "P".equals(posizionale)){
			numeroAllegato = "0";
		}
				
		if(tipo != null && !"".equals(tipo) && "T".equals(tipo)){
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idDoc", idDocPrimario);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"","T","");
			copertinaTimbroWindow.show();
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idUd", idUd);
			copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			copertinaTimbroRecord.setAttribute("provenienza", "P");
			copertinaTimbroRecord.setAttribute("posizionale", posizionale);
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"","",posizionale);
			copertinaTimbroWindow.show();
		}
	}
	
	/**
	 * Barcode multipli su etichetta
	 */
	public void clickBarcodeSuEtichettaMultipli(String tipo,String posizione) {
			
		Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		if(tipo != null && "T".equals(tipo)){
			String idDocPrimario = detailRecord.getAttribute("idDocPrimario");
			record.setAttribute("tipologia", "T");
			record.setAttribute("idDoc", idDocPrimario);
		}
		
		if(posizione != null && "P".equals(posizione)){
			record.setAttribute("nrAllegato", "0");
		}
		record.setAttribute("posizione", posizione);
		
		/**
		 * Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			
			record.setAttribute("nomeStampante", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
			
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
			stampaEtichettaPopup.show();
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
					
				@Override
				public void execute(String nomeStampante) {

					record.setAttribute("nomeStampante", nomeStampante);					
					StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
					stampaEtichettaPopup.show();
				}
			}, null);
		}
	}
	
	/**
	 * Barcode su etichetta - Dati segnatura
	 */
	public void clickBarcodeEtichettaDatiSegnatura(String posizione) {
		
		Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		final String nrAllegato = posizione != null && "P".equals(posizione) ? "0" : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", nrAllegato);
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
				}
			}, null);
		}
	}
	
	/**
	 * Barcode su etichetta - Dati tipologia
	 */
	public void clickBarcodeEtichettaDatiTipologia() {
		
		Record detailRecord = new Record(vm.getValues());
		final String idDocPrimario = detailRecord != null ? detailRecord.getAttribute("idDocPrimario") : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idDoc", idDocPrimario);
		recordToPrint.setAttribute("numeroAllegato", "");
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
	
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null	&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
		} else {
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
				}
			}, null);
		}
	}
			
	/**
	 * Gestione di stampa delle etichetta bypassando la scelta delle opzioni di stampa
	 */
	protected void manageStampaEtichettaSenzaOpzStampa(final Record record) {
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")) {
			// Stampa con stampigliatrice
			/**
			 * Viene verificato che sia stata selezionata una porta in precedenza
			 */
			if(AurigaLayout.getImpostazioneStampa("portaStampanteTimbraturaCartaceo") != null && !"".equals(AurigaLayout.getImpostazioneStampa("portaStampanteTimbraturaCartaceo"))){
				buildStampaEtichettaAutoPostReg(record, null, null);
			} else {
				PortScannerUtility.portScanner("", new PortScannerCallback() {

					@Override
					public void execute(String portaStampanteTimbraturaCartaceo) {
						record.setAttribute("portaStampanteTimbraturaCartaceo", portaStampanteTimbraturaCartaceo);
						buildStampaEtichettaAutoPostReg(record, "", portaStampanteTimbraturaCartaceo);
					}
				}, new PortScannerCallback() {
					
					@Override
					public void execute(String nomeStampante) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(), "", MessageType.ERROR));
					}
				});
			}
		} else {
			/**
			 * Viene verificato che sia stata selezionata una stampante in precedenza
			 */
			if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
				buildStampaEtichettaAutoPostReg(record, null, null);
			} else {
				PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

					@Override
					public void execute(String nomeStampante) {
						record.setAttribute("nomeStampante", nomeStampante);
						buildStampaEtichettaAutoPostReg(record, nomeStampante, "");
					}
				}, new PrinterScannerCallback() {
					
					@Override
					public void execute(String nomeStampante) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(), "", MessageType.ERROR));
					}
				});
			}
		}	
	}

	/**
	 * Stampa dell'etichetta post-registrazione
	 */
	private void buildStampaEtichettaAutoPostReg(Record record, String nomeStampante, String portaStampanteTimbraturaCartaceo) {
		
		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		lRecord.setAttribute("listaAllegati", record.getAttributeAsRecordList("listaAllegati"));
		if(nomeStampante == null || "".equals(nomeStampante)) {
			nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		}
		lRecord.setAttribute("nomeStampante", nomeStampante);
		if(portaStampanteTimbraturaCartaceo == null || "".equals(portaStampanteTimbraturaCartaceo)) {
			portaStampanteTimbraturaCartaceo = AurigaLayout.getImpostazioneStampa("portaStampanteTimbraturaCartaceo");
		}
		lRecord.setAttribute("portaStampanteTimbraturaCartaceo", portaStampanteTimbraturaCartaceo);
		lRecord.setAttribute("nroEtichette",  "1");		
		
		Record impostazioniStampa = AurigaLayout.getImpostazioneStampa();
		lRecord.setAttribute("flgPrimario", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario") : true);
		lRecord.setAttribute("flgAllegati", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati") : true);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO")) {
			lRecord.setAttribute("flgSegnRegPrincipale", true);
		} else {
			lRecord.setAttribute("flgSegnRegPrincipale", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegPrincipale") : true);
		}
		lRecord.setAttribute("flgSegnRegSecondaria", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgSegnRegSecondaria") : true);
		if(isFromCanaleSportello() || isFromCanalePregresso()) {
			lRecord.setAttribute("flgRicevutaXMittente", impostazioniStampa != null ? AurigaLayout.getImpostazioneStampaAsBoolean("flgRicevutaXMittente") : true);
		}
		lRecord.setAttribute("flgHideBarcode", !AurigaLayout.getImpostazioneStampaAsBoolean("stampaBarcode"));
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){			
			lRecord.setAttribute("notazioneCopiaOriginale",  AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));
		}
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final String portaStampanteTimbraturaCartaceo = object.getAttribute("portaStampanteTimbraturaCartaceo");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, portaStampanteTimbraturaCartaceo, etichette, numCopie, new StampaEtichettaCallback() {

					@Override
					public void execute() {
					
					}
				});
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}
		});
	}
	
	protected boolean showEditButton() {
		
		final String display = filePrimarioForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = filePrimarioForm.getValueAsString("uriFileVerPreFirma");
		if (canEditByApplet() && uri != null && !uri.equals("")) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
				if (uri != null && !uri.equals("")) {
					String estensione = null;					
					/**
					 * File corrente non firmato
					 */
					Object infoFile = filePrimarioForm.getValue("infoFile");
					if (infoFile != null) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
						if (lInfoFileRecord.getCorrectFileName() != null && !lInfoFileRecord.getCorrectFileName().trim().equals("")) {
							estensione = lInfoFileRecord.getCorrectFileName().substring(lInfoFileRecord.getCorrectFileName().lastIndexOf(".") + 1);
						}
						if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
							estensione = display.substring(display.lastIndexOf(".") + 1);
						}
						if (estensione.equalsIgnoreCase("p7m")) {
							String displaySbustato = display.substring(0, display.lastIndexOf("."));
							estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
						}
						for (String lString : Layout.getGenericConfig().getEditableExtension()) {
							if (lString.equals(estensione)) {
								return true;
							}
						}
					}
					/**
					 * File pre versione di quello firmato
					 */
					Object infoFilePreVer = filePrimarioForm.getValue("infoFileVerPreFirma");
					if(infoFilePreVer != null) {
						InfoFileRecord lInfoFilePreVerRecord = new InfoFileRecord(infoFilePreVer);
						if (lInfoFilePreVerRecord.getCorrectFileName() != null && !lInfoFilePreVerRecord.getCorrectFileName().trim().equals("")) {
							estensione = lInfoFilePreVerRecord.getCorrectFileName().substring(lInfoFilePreVerRecord.getCorrectFileName().lastIndexOf(".") + 1);
						}
						if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
							estensione = display.substring(display.lastIndexOf(".") + 1);
						}
						if (estensione.equalsIgnoreCase("p7m")) {
							String displaySbustato = display.substring(0, display.lastIndexOf("."));
							estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
						}
						for (String lString : Layout.getGenericConfig().getEditableExtension()) {
							if (lString.equals(estensione)) {
								return true;
							}
						}
					} 			
					return false;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	protected void clickEditFile() {
		
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = filePrimarioForm.getValueAsString("uriFileVerPreFirma");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));		
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (info.isFirmato()) {
			SC.ask("Modificando il file perderai la/le firme già apposte. Vuoi comunque procedere?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value){
						InfoFileRecord infoPreFirma = new InfoFileRecord(filePrimarioForm.getValue("infoFileVerPreFirma"));
						editFile(infoPreFirma, display, uri, remoteUri);
					}
				}
			});
		} else {
			editFile(info, display, uri, remoteUri);
		}
	}

	private void editFile(InfoFileRecord info, String display, String docStorageUri, Boolean remoteUri) {

		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		}
		if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = filePrimarioForm.getValueAsString("improntaVerPreFirma");
		if (impronta == null || "".equals(impronta)) {
			impronta = info.getImpronta();
		}
		if (estensione.equalsIgnoreCase("p7m")) {
			Record lRecordDaSbustare = new Record();
			lRecordDaSbustare.setAttribute("displayFilename", display);
			lRecordDaSbustare.setAttribute("uri", docStorageUri);
			lRecordDaSbustare.setAttribute("remoteUri", remoteUri);
			new GWTRestService<Record, Record>("SbustaFileService").call(lRecordDaSbustare,
					new ServiceCallback<Record>() {

						@Override
						public void execute(final Record lRecordSbustato) {
							String displaySbustato = lRecordSbustato.getAttribute("displayFilename");
							String uriSbustato = lRecordSbustato.getAttribute("uri");
							String estensioneSbustato = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
							String improntaSbustato = lRecordSbustato.getAttribute("impronta");
							openEditFileWindow(displaySbustato, uriSbustato, false, estensioneSbustato,
									improntaSbustato);
						}
					});
		} else {
			openEditFileWindow(display, docStorageUri, remoteUri, estensione, impronta);
		}
	}

	public void openEditFileWindow(String display, String docStorageUri, Boolean remoteUri, String estensione,
			String impronta) {

		OpenEditorUtility.openEditor(display, docStorageUri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, String record) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
				filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFileFirmato);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriFileFirmato);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}

	public void clickPreviewFile() {

		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
					"VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	public void clickPreviewEditFile() {

		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>(
					"VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		
		// Callback per la gestione della versione con omisses
		PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
			
			@Override
			public void executeSalvaVersConOmissis(Record record) {
				if (showVersioneOmissis()) {
					String uri = record.getAttribute("uri");
					String displayFileName = record.getAttribute("fileName");
					Record infoFile = record.getAttributeAsRecord("infoFile");
					filePrimarioForm.setValue("flgDatiSensibili", true);
					flgDatiSensibiliItem.setValue(true);
					filePrimarioForm.markForRedraw();
					filePrimarioOmissisItem.updateAfterUpload(displayFileName, uri, infoFile);
				}
			}
			
			@Override
			public void executeSalva(Record record) {
				String uriFile = record.getAttribute("uri");
				String nomeFile = record.getAttribute("fileName");
				InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFilePrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFile);
				filePrimarioForm.setValue("uriFilePrimario", uriFile);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFile);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriFile);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.setValue("infoFileVerPreFirma", info);
				if(filePrimarioForm != null) {
					filePrimarioForm.markForRedraw();
				}
				if(filePrimarioButtons != null) {
					filePrimarioButtons.markForRedraw();
				}
				manageChangedPrimario();
				changedEventAfterUpload(nomeFile, uriFile);				
			}
			
			@Override
			public void executeOnError() {				
			}
		};
		
		boolean isViewMode = !filePrimarioButtons.isEditing();
		boolean enableOptionToSaveInOmissisForm = showVersioneOmissis();		
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, isViewMode, enableOptionToSaveInOmissisForm, isEnablePreviewModal());
		// PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// in realtà il costruttore effettua anche lo show, quindi non è
		// necessario inizializzare una variabile
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri,
		// info, "FileToExtractBean", display);
	}

	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri,
			final Boolean remoteUri, InfoFileRecord info) {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd,
				rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv"))
						&& detailRecord.getAttribute("idUd") != null && !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri, timbroEnabled,
						lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {
						InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null
								? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						filePrimarioForm.setValue("infoFile", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							manageChangedFilePrimario();
						}
						filePrimarioForm.setValue("nomeFilePrimario", filePdf);
						filePrimarioForm.setValue("uriFilePrimario", uriPdf);
						filePrimarioForm.setValue("nomeFilePrimarioTif", "");
						filePrimarioForm.setValue("uriFilePrimarioTif", "");
						filePrimarioForm.setValue("remoteUriFilePrimario", false);
						filePrimarioForm.setValue("nomeFileVerPreFirma", filePdf);
						filePrimarioForm.setValue("uriFileVerPreFirma", uriPdf);
						filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
						filePrimarioForm.setValue("infoFileVerPreFirma", info);
						if(filePrimarioForm != null) {
							filePrimarioForm.markForRedraw();
						}
						if(filePrimarioButtons != null) {
							filePrimarioButtons.markForRedraw();
						}
						manageChangedPrimario();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

	// Download del file
	public void clickDownloadFile() {

		String idUd = new Record(vm.getValues()).getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	public void clickDownloadFileSbustato() {

		String idUd = new Record(vm.getValues()).getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Cancellazione del file
	public void clickEliminaFile() {

		nomeFilePrimarioItem.setValue("");
		uriFilePrimarioItem.setValue("");
		nomeFilePrimarioTifItem.setValue("");
		uriFilePrimarioTifItem.setValue("");
		infoFileItem.clearValue();
		if(filePrimarioForm != null) {
			filePrimarioForm.markForRedraw();
		}
		if(filePrimarioButtons != null) {
			filePrimarioButtons.markForRedraw();
		}
		manageChangedPrimario();
		uploadFilePrimarioItem.getCanvas().redraw();
		if (generaDaModelloButton != null) {
			generaDaModelloButton.redraw();
		}
		remoteUriFilePrimarioItem.setValue(false);
		nomeFileVerPreFirmaItem.setValue("");
		uriFileVerPreFirmaItem.setValue("");
		improntaVerPreFirmaItem.setValue("");
		infoFileVerPreFirmaItem.clearValue();
		idAttachEmailPrimarioItem.clearValue();
	}

	public void addToRecent(String idUd, String idDoc) {

		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
	}

	protected void changedEventAfterUpload(final String displayFileName, final String uri) {

		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFilePrimarioItem.getJsObj()) {

			public DynamicForm getForm() {
				return filePrimarioForm;
			};

			@Override
			public FormItem getItem() {
				return nomeFilePrimarioItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriFilePrimarioItem.getJsObj()) {

			public DynamicForm getForm() {
				return filePrimarioForm;
			};

			@Override
			public FormItem getItem() {
				return uriFilePrimarioItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFilePrimarioItem.fireEvent(lChangedEventDisplay);
		uriFilePrimarioItem.fireEvent(lChangedEventUri);
	}

	public boolean canEditByApplet() {
		return isProtocollazioneDetailBozze() || isProtocollazioneDetailAtti();
	}

	protected void manageFileFirmatoButtonClick() {

		final InfoFileRecord infoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		final String uriFilePrimario = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Record detailRecord = new Record(vm.getValues());
		final String idDoc = filePrimarioForm.getValueAsString("idDocPrimario");
		final String idUd = detailRecord.getAttributeAsString("idUd");
		MenuItem informazioniFirmaMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, false);
			}
		});
		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/barcode.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, true);
			}
		});
		final Menu fileFirmatoMenu = new Menu();
		fileFirmatoMenu.setOverflow(Overflow.VISIBLE);
		fileFirmatoMenu.setShowIcons(true);
		fileFirmatoMenu.setSelectionType(SelectionStyle.SINGLE);
		fileFirmatoMenu.setCanDragRecordsOut(false);
		fileFirmatoMenu.setWidth("*");
		fileFirmatoMenu.setHeight("*");
		fileFirmatoMenu.addItem(informazioniFirmaMenuItem);
		fileFirmatoMenu.addItem(stampaFileCertificazioneMenuItem);
		fileFirmatoMenu.showContextMenu();
	}

	private void generaFileFirmaAndPreview(final InfoFileRecord infoFilePrimario, String uriFilePrimario,
			boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFilePrimario);
		record.setAttribute("uriAttach", uriFilePrimario);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		if(!isProtocollazioneDetailAtti() && !isTaskDetail()) {			
			if (dataProtocolloItem.getValueAsDate() != null) {
				String dataRiferimento = DateUtil.format(dataProtocolloItem.getValueAsDate());
				lGwtRestDataSource.extraparam.put("dataRiferimento", dataRiferimento);
			}
		}
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				// String url = (GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" + URL.encode(uri));
				preview(null, null, filename, uri, false, infoFile);
			}
		});
	}

	protected void creaAttestato(String idUd, String idDoc, final InfoFileRecord infoFileAllegato, String uriFileAllegato,
			final boolean attestatoFirmato) {

		Record estremiProtocollo = protocolloGeneraleForm.getValuesAsRecord();
		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);
		record.setAttribute("siglaProtocollo", estremiProtocollo.getAttribute("siglaProtocollo"));
		record.setAttribute("nroProtocollo", estremiProtocollo.getAttribute("nroProtocollo"));
		record.setAttribute("subProtocollo", estremiProtocollo.getAttribute("subProtocollo"));
		record.setAttribute("dataProtocollo", DateUtil.formatAsShortDatetime((estremiProtocollo.getAttributeAsDate("dataProtocollo"))));
		record.setAttribute("desUserProtocollo", estremiProtocollo.getAttribute("desUserProtocollo"));
		record.setAttribute("desUOProtocollo", estremiProtocollo.getAttribute("desUOProtocollo"));
		record.setAttribute("estremiRepertorioPerStruttura", estremiProtocollo.getAttribute("estremiRepertorioPerStruttura"));
		record.setAttribute("idUd", estremiProtocollo.getAttribute("idUd"));
		record.setAttribute("idDoc", filePrimarioForm.getValueAsString("idDocPrimario"));
		record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
		lGwtRestDataSource.extraparam.put("attestatoFirmato", Boolean.toString(attestatoFirmato));
		lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());
		lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				final InfoFileRecord infoFile = InfoFileRecord
						.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				final String filename = infoFile.getCorrectFileName();
				final String uri = data.getAttribute("tempFileOut");
				if (!attestatoFirmato) {
					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", filename);
					lRecord.setAttribute("uri", uri);
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", false);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					FirmaUtility.firmaMultipla(uri, filename, infoFile, new FirmaCallback() {

						@Override
						public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", nomeFileFirmato);
							lRecord.setAttribute("uri", uriFileFirmato);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", false);
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					});
				}
			}
		});
	}

	/**
	 * Metodo che visualizza le versioni del file salvate
	 * 
	 */
	public static void visualizzaVersioni(String idDoc, String tipoFile, String nroAllegato, String estremi,
			Record recordProtocollo) {

		String title = "";
		if (tipoFile != null) {
			if (tipoFile.equals("FP")) {
				title = "Versioni del file primario";
				if(estremi != null && !"".equals(estremi)) {
					title += " del documento: " + estremi;
				}
			} else if (tipoFile.equals("A")) {
				title = "Versioni del file allegato N° " + nroAllegato;
				if(estremi != null && !"".equals(estremi)) {
					title += " del documento: " + estremi;
				}
			}  else if (tipoFile.equals("AO")) {
				title = "Versioni del file allegato (con omissis) N° " + nroAllegato;
				if(estremi != null && !"".equals(estremi)) {
					title += " del documento: " + estremi;
				}
			} else if (tipoFile.equals("DI")) {
				title = "Versioni del documento di istruttoria N° " + nroAllegato;
			}
		} else {
			title = "Versioni del file";
		}
		new VisualizzaVersioniFileWindow(idDoc, title, recordProtocollo);
	}

	/**
	 * Metodo che scambia l'allegato della posizione passata in input con il
	 * file primario
	 * 
	 */
	protected void clickTrasfInPrimario(int index) {

		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		Record lRecordAttuale = new Record(vm.getValues());
		lGwtRestDataSourceProtocollo.addParam("index", index + "");
		lGwtRestDataSourceProtocollo.executecustom("trasformaSecondarioInPrimario", lRecordAttuale, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record lRecord = response.getData()[0];
				editRecord(lRecord, true);
				filePrimarioForm.redraw();
				fileAllegatiItem.redraw();
				if (detailSectionContenuti != null) {
					detailSectionContenuti.open();
				}
				if (detailSectionAllegati != null) {
					detailSectionAllegati.openIfhasValue();
				}
				if (editMode != null) {
					if ("protocollaMail".equals(editMode)) {
						protocollaMailMode();
					} else if ("protocollaRegEmergenza".equals(editMode)) {
						protocollaRegEmergenzaMode();
					} else if ("caricaDatiImgAssoc".equals(editMode)) {
						caricaDatiImgAssocMode();
					} else if ("modificaDatiReg".equals(editMode)) {
						modificaDatiRegMode();
					} else if ("caricaDatiImgAssoc".equals(editMode)) {
						modificaDatiRegMode();
					} else if ("aggiuntaFile".equals(editMode)) {
						aggiuntaFileMode();
					} else if ("modificaDati".equals(editMode)) {
						modificaDatiMode();
					} else if ("modificaRegAccessoCivico".equals(editMode)) {
						modificaRegAccessoCivicoMode();
					} else if ("modificaDatiExtraIter".equals(editMode)) {
						modificaDatiExtraIterMode();
					}
					nuovoDettaglioMail(lRecord);
				}
				Layout.hideWaitPopup();
			}
		});
	}

	/**
	 * Metodo che sposta il file primario negli allegati
	 * 
	 */
	protected void clickTrasformaInAllegato() {

		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		Record lRecordAttuale = new Record(vm.getValues());
		lGwtRestDataSourceProtocollo.executecustom("trasformaPrimarioInSecondario", lRecordAttuale, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record lRecord = response.getData()[0];
				editRecord(lRecord, true);
				filePrimarioForm.redraw();
				fileAllegatiItem.redraw();
				if (detailSectionContenuti != null) {
					detailSectionContenuti.open();
				}
				if (detailSectionAllegati != null) {
					detailSectionAllegati.openIfhasValue();
				}
				if (editMode != null) {
					if ("protocollaMail".equals(editMode)) {
						protocollaMailMode();
					} else if ("protocollaRegEmergenza".equals(editMode)) {
						protocollaRegEmergenzaMode();
					} else if ("caricaDatiImgAssoc".equals(editMode)) {
						caricaDatiImgAssocMode();
					} else if ("modificaDatiReg".equals(editMode)) {
						modificaDatiRegMode();
					} else if ("caricaDatiImgAssoc".equals(editMode)) {
						modificaDatiRegMode();
					} else if ("aggiuntaFile".equals(editMode)) {
						aggiuntaFileMode();
					} else if ("modificaDati".equals(editMode)) {
						modificaDatiMode();
					} else if ("modificaRegAccessoCivico".equals(editMode)) {
						modificaRegAccessoCivicoMode();
					} else if ("modificaDatiExtraIter".equals(editMode)) {
						modificaDatiExtraIterMode();
					}
					nuovoDettaglioMail(lRecord);
				}
			}
		});
	}
	
	/**
	 * Metodo che viene richiamato quando cambia qualcosa nel form dell'oggetto
	 * 
	 */
	public void manageChangedContenuti() {
		
	}
	
	/**
	 * Metodo che viene richiamato quando cambia l'oggetto
	 * 
	 */
	public void manageChangedOggetto() {
		
		String oggetto = (contenutiForm != null && contenutiForm.getValueAsString("oggetto") != null)
				? contenutiForm.getValueAsString("oggetto")/*.replaceAll("<br/>", "\n")*/ : null;
        if(oggetto != null && !"".equals(oggetto)){        
			if(isProtocollazioneDetailAtti() && AurigaLayout.getParametroDBAsBoolean("UPPERCASE_OGGETTO_ATTO")) {
	                contenutiForm.setValue("oggetto", oggetto.toUpperCase());
	        }
        }
        manageChangedContenuti();
	}
	
	/**
	 * Metodo che viene richiamato quando cambia il file primario
	 * 
	 */
	public void manageChangedPrimario() {
		// questa parte
		if(!isModalitaWizard() && isAttivoAssegnatarioUnico()) {
		
			Record detailRecord = new Record(vm.getValues());
			
			// Ricarico i mittenti per vedere se settare il valore di default nel check "effettua assegnazione"
			if(mittentiForm != null) {
				mittentiForm.clearErrors(true);
				if(mittentiItem != null) {
					mittentiItem.reloadTipoValueMap();
				}
				mittentiForm.redraw();
			}
			
			if(destinatariForm != null) {
				destinatariForm.clearErrors(true);
				if(destinatariItem != null) {
					destinatariItem.reloadTipoValueMap();
				}
				destinatariForm.redraw();
			}
			
			if(assegnazioneForm != null) {
				assegnazioneForm.clearErrors(true);
				if(assegnazioneItem != null) {
					if (mode == null || mode.equals("new")) {
						assegnazioneItem.show();
					} else if (detailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")) {				
						assegnazioneItem.show();
					} else {
						assegnazioneItem.hide();
					}
					if (isAttivoAssegnatarioUnico()) {
						assegnazioneItem.setAssegnatarioUnico(true);
						assegnazioneItem.setFlgSenzaLD(true);
					} else {
						assegnazioneItem.setAssegnatarioUnico(false);
						assegnazioneItem.setFlgSenzaLD(false);
					} 	
					assegnazioneItem.reloadTipoValueMap();
					// se ho gia un assegnatario salvato ed è attivo l'assegnatario unico nascondo la lista delle assegnazioni
					if (assegnazioneItem.getAssegnatarioUnico() && assegnazioneSalvataItem != null && assegnazioneSalvataItem.getVisible() && assegnazioneSalvataItem.getValueAsRecordList() != null && assegnazioneSalvataItem.getValueAsRecordList().getLength() > 0 && assegnazioneSalvataItem.hasValue()) { 
						assegnazioneItem.drawAndSetValue(new RecordList());
						assegnazioneItem.hide();		
					}
				}			
				assegnazioneForm.redraw();
			}
			
			showHideDetailSectionAssegnazione();
			
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {	
					if(mittentiItem != null) {
						mittentiItem.redraw();				
						mittentiItem.validate();						
					}
					if(detailSectionMittenti != null && detailSectionMittenti.isOpen()) {
						detailSectionMittenti.open(); // per riaggiungere l'eventuale prima riga vuota se è stata tolta dalla validazione
					}
					if(destinatariItem != null) {
						destinatariItem.redraw();				
						destinatariItem.validate();	
					}		
					if(detailSectionDestinatari != null && detailSectionDestinatari.isOpen()) {
						detailSectionDestinatari.open(); // per riaggiungere l'eventuale prima riga vuota se è stata tolta dalla validazione
					}
					if(assegnazioneItem != null) {
						assegnazioneItem.redraw();
						assegnazioneItem.validate();
					}
					if(detailSectionAssegnazione != null && detailSectionAssegnazione.isOpen()) {
						detailSectionAssegnazione.open(); // per riaggiungere l'eventuale prima riga vuota se è stata tolta dalla validazione
					}
					// dopo i validate verifico se ci sono ancora errori da evidenziare nel tab
//					clearTabErrors();
//					showTabErrors();
				}
			});							
		}
	}

	/**
	 * Metodo che viene richiamato quando cambia il contenuto (si fa il
	 * confronto delle impronte dei file) o il nome del file primario
	 * 
	 */
	public void manageChangedFilePrimario() {

		filePrimarioForm.setValue("isDocPrimarioChanged", true);
		filePrimarioForm.redraw();		
		if (showFlgSostituisciVerPrecItem()) {
			try {
				Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;
				InfoFileRecord precInfo = recordOrig != null && recordOrig.getAttributeAsObject("infoFile") != null
						? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = filePrimarioForm.getValue("infoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("infoFile"))
						: null;
				String impronta = info != null ? info.getImpronta() : null;
				if (flgSostituisciVerPrecItem != null) {
					if (precImpronta != null && impronta != null && !impronta.equals(precImpronta)) {
						flgSostituisciVerPrecItem.show();
					} else {
						flgSostituisciVerPrecItem.hide();
					}
				}
			} catch (Exception e) {
			}
		}
		if(isAttivaTimbraturaFilePostReg() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
			if (canBeTimbrato(lInfoFileRecord) && !lInfoFileRecord.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG") || AurigaLayout.getParametroDB("FORZA_TIMBRATURA_FILE_POST_REG").equalsIgnoreCase("solo_primario")) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")) {
						boolean flgTimbraFilePostRegValue = Boolean.parseBoolean(filePrimarioForm.getValueAsString("flgTimbraFilePostReg"));
						
	//					if (!(flgTimbraFilePostRegItem.getValue() != null && (Boolean) flgTimbraFilePostRegItem.getValue())) {
						if (!flgTimbraFilePostRegValue) {
							showOpzioniTimbratura();
						} else {
							SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
									new BooleanCallback() {
	
										@Override
										public void execute(Boolean value) {
											if (value) {
												showOpzioniTimbratura();
											}
										}
									});
						}
					}
					flgTimbraFilePostRegItem.setValue(true);
				} else {
					flgTimbraFilePostRegItem.setValue(false);
				}
			}
			else {
				flgTimbraFilePostRegItem.setValue(false);
			}
		}
	}
	
	/*
	 * Viene premimpostato il checkbox 'Apponi timbro' se si proviene da una protocollazione email 
	 */
	public void preimpostaApponiTimbroFromEmail() {
		if(isAttivaTimbraturaFilePostReg() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
			if (lInfoFileRecord != null && canBeTimbrato(lInfoFileRecord) && (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG") || AurigaLayout.getParametroDB("FORZA_TIMBRATURA_FILE_POST_REG").equalsIgnoreCase("solo_primario"))) {
				flgTimbraFilePostRegItem.setValue(true);
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}			
		}
	}
	
	/*
	 * Viene premimpostato il checkbox 'Apponi timbro' se si proviene da una protocollazione di una bozza 
	 */
	public void preimpostaApponiTimbroXProtBozza() {
		if(isAttivaTimbraturaFilePostReg() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
			if (lInfoFileRecord != null && canBeTimbrato(lInfoFileRecord) && (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG") || AurigaLayout.getParametroDB("FORZA_TIMBRATURA_FILE_POST_REG").equalsIgnoreCase("solo_primario"))) {
				if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")) {
					boolean flgTimbraFilePostRegValue = Boolean.parseBoolean(filePrimarioForm.getValueAsString("flgTimbraFilePostReg"));
					if (!flgTimbraFilePostRegValue) {
						showOpzioniTimbratura();
					} else {
						SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
								new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											showOpzioniTimbratura();
										}
									}
								});
					}
				}
				flgTimbraFilePostRegItem.setValue(true);
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}			
		}
	}
	
	public void manageChangedFilePrimarioOmissis() {
	
	}

	/**
	 * Metodo che viene richiamato quando cambia il contenuto (si fa il
	 * confronto delle impronte dei file) o il nome di un file allegato
	 * 
	 */
	public void manageChangedFileInAllegatiItem() {

	}

	/**
	 * Metodo che viene richiamato quando cambia il contenuto (si fa il
	 * confronto delle impronte dei file) o il nome di un file allegato che è
	 * parte dispositivo
	 * 
	 */
	public void manageChangedFileParteDispositivoInAllegatiItem() {

	}
	
	/**
	 * 
	 * Metodo richiamato per la costruzione del menu per la stampa dei barcode
	 * su A4 & Etichette, con segnatura o tipologia, con posizione o senza.
	 */
	protected void buildMenuBarcodeEtichetta(Menu altreOpMenu){
		
		/**
		 * TIMBRA 
		 * Visibile solo se è presente l'uri del file Primario
		 */
		
		//Variabile che gestisce l'aggiunta della voce unica o del sottomenu "Timbra"
		boolean flgAddSubMenuTimbra = false;
				
		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		MenuItem timbraDatiSegnaturaMenuItem = null;
		MenuItem timbraDatiTipologiaMenuItem = null;
		MenuItem timbroConformitaOriginaleMenuItem = null;
		MenuItem timbroCopiaConformeMenuItem = null;
		if (lInfoFileRecord != null) {
			Menu timbraSubMenu = new Menu();
			timbraDatiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiSegnaturaMenuItem(), "file/timbra.gif");
			timbraDatiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickTimbraDatiSegnatura("");
				}
			});
			timbraDatiSegnaturaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				}
			});
			timbraSubMenu.addItem(timbraDatiSegnaturaMenuItem);
			
			if(isAttivaTimbroTipologiaProtocollazione()){
				timbraDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiTipologiaMenuItem());
				timbraDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiTipologia();					
					}
				});
				timbraDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return isAttivaTimbroTipologiaProtocollazione() && (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) && lInfoFileRecord.isConvertibile();
					}
				});
				
				
				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbraDatiTipologiaMenuItem);
			}
			
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && 
						(lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
					timbroConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
					timbroConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
						}
					});
					timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickTimbraDatiSegnatura("CONFORMITA_ORIG_CARTACEO");
						}
					});
					
					flgAddSubMenuTimbra=true;
					timbraSubMenu.addItem(timbroConformitaOriginaleMenuItem);
				}
			}
			
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
				flgAddSubMenuTimbra=true;
				
				timbroCopiaConformeMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
				timbroCopiaConformeMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					}
				});
				
				Menu sottoMenuCopiaConformeItem = new Menu();
				
				MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
				timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura("CONFORMITA_ORIG_DIGITALE_STAMPA");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
				
				MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
				timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
				
				timbroCopiaConformeMenuItem.setSubmenu(sottoMenuCopiaConformeItem);				
				timbraSubMenu.addItem(timbroCopiaConformeMenuItem);
			}
	
			timbraMenuItem.setSubmenu(timbraSubMenu);
			
		}
		timbraMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			}
		});
		
		
		/**
		 * BARCODE SU A4
		 */
		MenuItem barcodeA4MenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4MenuItem() , "protocollazione/barcode.png");
	
		Menu barcodeA4SubMenu = new Menu();
		
		//Dati segnatura 
		MenuItem barcodeA4NrDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4NrDocumentoMenuItem() ,"protocollazione/nr_documento.png");
		barcodeA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("","");
			}
		});
		MenuItem barcodeA4NrDocumentoPoisizioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4NrDocumentoPoisizioneMenuItem() ,"protocollazione/nr_documento_posizione.png");
		barcodeA4NrDocumentoPoisizioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("","P");
			}
		});
		MenuItem barcodeA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4DatiTipologiaMenuItem());
		barcodeA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiTipologia("T");
			}
		});
		barcodeA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeA4SubMenu.setItems(barcodeA4NrDocumentoMenuItem, barcodeA4NrDocumentoPoisizioneMenuItem, barcodeA4DatiTipologiaMenuItem);
		barcodeA4MenuItem.setSubmenu(barcodeA4SubMenu);
		
		/**
		 * BARCODE SU A4 MULTPIPLI
		 */
		MenuItem barcodeA4MultipliMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeA4MultipliMenuItem(), "blank.png");
		
		Menu barcodeMultipliA4SubMenu = new Menu();
		
		//Dati segnatura
		MenuItem barcodeMultipliA4NrDocumentoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4NrDocumentoMenuItem(),"protocollazione/nr_documento.png");
		barcodeMultipliA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeMultipli("","");
			}
		});
		MenuItem barcodeMultipliA4NrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4NrDocPosMenuItem() ,"protocollazione/nr_documento_posizione.png");
		barcodeMultipliA4NrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeMultipli("","P");
			}
		});
		MenuItem barcodeMultipliA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliA4DatiTipologiaMenuItem());
		barcodeMultipliA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeMultipli("T","");
			}
		});
		barcodeMultipliA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeMultipliA4SubMenu.setItems(barcodeMultipliA4NrDocumentoMenuItem, barcodeMultipliA4NrDocPosMenuItem, barcodeMultipliA4DatiTipologiaMenuItem);
		barcodeA4MultipliMenuItem.setSubmenu(barcodeMultipliA4SubMenu);
		
		/**
		 * BARCODE SU ETICHETTA
		 */
		MenuItem barcodeEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaMenuItem(), "protocollazione/barcode.png");
		
		Menu barcodeEtichettaSubMenu = new Menu();
		MenuItem barcodeEtichettaNrDocMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaNrDocMenuItem(),"protocollazione/nr_documento.png");
		barcodeEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("E","");
			}
		});
		MenuItem barcodeEtichettaNrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaNrDocPosMenuItem(),"protocollazione/nr_documento_posizione.png");
		barcodeEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiSegnatura("E","P");
			}
		});
		
		MenuItem barcodeEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaDatiTipologiaMenuItem());
		barcodeEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeDatiTipologia("E");
			}
		});
		barcodeEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeEtichettaSubMenu.setItems(barcodeEtichettaNrDocMenuItem, barcodeEtichettaNrDocPosMenuItem,barcodeEtichettaDatiTipologiaMenuItem);
		barcodeEtichettaMenuItem.setSubmenu(barcodeEtichettaSubMenu);
		
		/**
		 * BARCODE SU ETICHETTA MULTIPLI
		 */
		
		MenuItem barcodeEtichettaMultiploMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeEtichettaMultiploMenuItem());
		Menu barcodeMultipliEtichettaSubMenu = new Menu();
		
		MenuItem barcodeMultipliEtichettaNrDocMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaNrDocMenuItem(),"protocollazione/nr_documento.png");
		barcodeMultipliEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeSuEtichettaMultipli("","");
			}
		});
		MenuItem barcodeMultipliEtichettaNrDocPosMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaNrDocPosMenuItem(),"protocollazione/nr_documento_posizione.png");
		barcodeMultipliEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeSuEtichettaMultipli("","P");
			}
		});
		MenuItem barcodeMultipliEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_barcodeMultipliEtichettaDatiTipologiaMenuItem());
		barcodeMultipliEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				clickBarcodeSuEtichettaMultipli("T","");
			}
		});
		barcodeMultipliEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
			
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return isAttivaTimbroTipologiaProtocollazione() /*&& uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")*/;
			}
		});
		
		barcodeMultipliEtichettaSubMenu.setItems(barcodeMultipliEtichettaNrDocMenuItem, barcodeMultipliEtichettaNrDocPosMenuItem,barcodeMultipliEtichettaDatiTipologiaMenuItem);
		barcodeEtichettaMultiploMenuItem.setSubmenu(barcodeMultipliEtichettaSubMenu);
		
		String idUd = new Record(vm.getValues()).getAttribute("idUd");
		if (idUd != null && !"".equals(idUd)) {
			// Se ho piu voci aggiungo il sottoMenu Timbra
			if (flgAddSubMenuTimbra) {
				altreOpMenu.addItem(timbraMenuItem);
				// Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
			} else {
				if(timbraDatiSegnaturaMenuItem!=null) {
					timbraDatiSegnaturaMenuItem.setTitle("Timbra");
					altreOpMenu.addItem(timbraDatiSegnaturaMenuItem);
				}
			}

			if (AurigaLayout.getParametroDBAsBoolean("SHOW_BARCODE_MENU")) {
				altreOpMenu.addItem(barcodeA4MenuItem);
				altreOpMenu.addItem(barcodeA4MultipliMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMultiploMenuItem);
			}
		}
	}
	
	/**
	 * Metodo che indica se la sezione è apribile (nella modalità wizard)
	 * 
	 */
	public boolean isOpenableDetailSection(DetailSection sectionToOpen) {
		return true;
	}
	
	/**
	 * Metodo che restituisce il messaggio di errore se la sezione non è apribile (nella modalità wizard)
	 * 
	 */
	public String getOpenErrorMessageDetailSection(DetailSection sectionToOpen) {
		return null;
	}
	
	/**
	 * Classe che implementa una sezione di intestazione nella maschera di protocollo
	 * 
	 */
	public class ProtocollazioneHeaderDetailSection extends HeaderDetailSection {

		public ProtocollazioneHeaderDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
			this(pTitle,  null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public ProtocollazioneHeaderDetailSection(String pTitle, final Integer  pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
			this(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}
		
		public ProtocollazioneHeaderDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final String pBackgroundColor, final DynamicForm... forms) {
			super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
		}		
		
		@Override
		public boolean isOpenable() {
			return isOpenableDetailSection(this);
		}

		@Override
		public String getOpenErrorMessage() {
			return getOpenErrorMessageDetailSection(this);
		}
		
		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return getShowFirstCanvasWhenEmptyAfterOpen();
		}
	}
	
	/**
	 * Metodo per ricavare il titolo del bottone Osservazioni e notifiche
	 */
	private String getEstremiUdFromDetail(Record record){
		return record.getAttribute("segnatura");
	}
	
	/**
	 * Classe che implementa una sezione nella maschera di protocollo
	 * 
	 */
	public class ProtocollazioneDetailSection extends DetailSection {
		
		public ProtocollazioneDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
			this(pTitle, null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public ProtocollazioneDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final DynamicForm... forms) {
			this(pTitle, pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public ProtocollazioneDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final String pBackgroundColor, final DynamicForm... forms) {
			super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				this.setTabIndex(-1);
				setCanFocus(false);			
			}
		}
		
		@Override
		public boolean isOpenable() {
			return isOpenableDetailSection(this);
		}

		@Override
		public String getOpenErrorMessage() {
			return getOpenErrorMessageDetailSection(this);
		}
		
		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return getShowFirstCanvasWhenEmptyAfterOpen();
		}
	}		

	/*********************
	 * GETTER AND SETTER *
	 *********************/

	public boolean isModificaDatiReg() {
		return modificaDatiReg;
	}

	public void setModificaDatiReg(boolean modificaDatiReg) {
		this.modificaDatiReg = modificaDatiReg;
	}

	public String getMotivoVariazione() {
		return motivoVariazione;
	}

	public void setMotivoVariazione(String motivoVariazione) {
		this.motivoVariazione = motivoVariazione;
	}
	
	protected boolean isUOAbilitatoScansioneMassiva() {
		Record lRecordUoRegistrazione = null;
		if (uoProtocollanteSelectItem != null) {
			if (uoProtocollanteSelectItem.getValueAsString() != null && !"".equals(uoProtocollanteSelectItem.getValueAsString())) {
				lRecordUoRegistrazione = AurigaLayout.getUoRegistrazioneMap().get(uoProtocollanteSelectItem.getValueAsString());				
			} else if (getSelezioneUoProtocollanteValueMap().size() == 1) {				
				lRecordUoRegistrazione = AurigaLayout.getUoRegistrazioneMap().get(getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0]);				
			}
		}
		boolean flgInibitaScansione = lRecordUoRegistrazione != null && lRecordUoRegistrazione.getAttribute("flgInibitaScansioneMassiva") != null && "1".equals(lRecordUoRegistrazione.getAttribute("flgInibitaScansioneMassiva"));
		
		/*Se non è inibitato significa che è abilitato alla scansione*/
		return !flgInibitaScansione;
	}
	
	
	/**
	 * ********************** METODI PER LA FIRMA COMPLESSIVA DEI FILE **********************
	 */
	
//	public void manageFirmaComplessiva(final ServiceCallback<Record> callbackManageFirma) {
//
//		Record detailRecord = new Record(getValuesManager().getValues());					
//		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
//		lArchivioDatasource.performCustomOperation("getListaPerFirmaSingolaConAllegati", detailRecord, new DSCallback() {
//
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//					clickFirmaComplessivaFile(response.getData()[0], callbackManageFirma);
//				}
//			}
//		}, new DSRequest());
//	}
//	
//	private void clickFirmaComplessivaFile(Record record, final ServiceCallback<Record> callbackManageFirma) {
//		
//		if(record.getAttributeAsRecordArray("files").length == 0){
//			Layout.addMessage(new MessageBean("Nessun file da firmare", "", MessageType.ERROR));
//		}else{
//			FirmaUtility.firmaMultipla(record.getAttributeAsRecordArray("files"), new FirmaMultiplaCallback() {
//
//				@Override
//				public void execute(Map<String, Record> files, Record[] filesAndUd) {
//					
//					manageFirmaCallBack(files, filesAndUd, callbackManageFirma);
//				}
//			});
//		}
//	}
//
//	protected void manageFirmaCallBack(final Map<String, Record> files, final Record[] filesAndUd, final ServiceCallback<Record> callbackManageFirma) {
//		
//		Layout.showWaitPopup("Elaborazione del documento firmato in corso...");
//		final Record lRecord = new Record();
//		final Record[] lRecords = new Record[files.size()];
//		int i = 0;
//		for (String lString : files.keySet()) {
//			Record recordToInsert = files.get(lString);
//			for (Record recordConUd : filesAndUd) {
//				if (recordConUd.getAttribute("idFile").equals(lString)) {
//					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
//				}
//			}
//			lRecords[i] = recordToInsert;
//			i++;
//		}
//		lRecord.setAttribute("files", lRecords);
//		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
//		lArchivioDatasource.performCustomOperation("aggiornaDocumentoFirmato", lRecord, new DSCallback() {
//
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//					Layout.hideWaitPopup();
//					Record lCallbackRecord = response.getData()[0];
//					Record[] lRecordsInError = lCallbackRecord.getAttributeAsRecordArray("files");
//					if (lRecordsInError.length > 0 && lRecordsInError.length == lRecords.length) {
//						//La firma di TUTTI i file è andata in errore
//						Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma del documento", "", MessageType.ERROR));
//						return;
//					} else if (lRecordsInError.length > 0){
//						//La firma di ALCUNI file è andata in errore
////						Layout.addMessage(new MessageBean("Alcuni file del documento non sono stati firmati correttamente", "", MessageType.WARNING));
//						SC.ask("Alcuni file del documento non sono stati firmati correttamente. Continuare comunque con l'azione successiva?", new BooleanCallback() {
//							
//							@Override
//							public void execute(Boolean value) {
//								if(value) {
//									manageAzioneSuccessivaFirma(callbackManageFirma);
//								}
//							}
//						});
//					} else if (lRecordsInError.length == 0){
//						//Il documento è stato firmato correttamente
//						Layout.addMessage(new MessageBean("Il documento è stato firmato con successo", "", MessageType.INFO));	
//						manageAzioneSuccessivaFirma(callbackManageFirma);
//					}
//					
//				} else {
//					Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma del documento", "", MessageType.ERROR));
//				}
//			}
//		}, new DSRequest());
//	}
//	
//	public void manageAzioneSuccessivaFirma(final ServiceCallback<Record> callbackManageFirma) {
//		Record currentRecord = new Record(getValuesManager().getValues());
//		AzioneSuccessivaPopup popup = new AzioneSuccessivaPopup(currentRecord, new ServiceCallback<Record>() {
//			
//			@Override
//			public void execute(Record response) {
//				reload(null);
//				callbackManageFirma.execute(response);
//			}
//		}); 
//		popup.show();
//	}
	
	
	public void manageFirmaComplessiva(boolean apposizioneForzata, final ServiceCallback<Record> callbackManageFirma) {
		
		Record detailRecord = new Record(getValuesManager().getValues());
		
		/*
		 * detailRecord contiene nel param contrassegnato come idUd l'id dell'ud che si ha selezionato 
		 */
		AzioneApposizionePopup popup = new AzioneApposizionePopup(detailRecord, new Record(getValuesManager().getValues()), TipologiaApposizione.FIRMA,
				apposizioneForzata, null, new ServiceCallback<Record>() {
			
			@Override
			public void execute(final Record object) {
				
				//Ricarico la grafica
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(layout != null) {
							layout.viewMode();
						} else {
							viewMode();
						}
						if(callbackManageFirma != null) {
							callbackManageFirma.execute(object);
						}
					}
				});
			}
		}); 
		
		//Devo visualizzare il popup per inserire la motivazione nel caso in cui si rifiuti l'apposizione
		if(!apposizioneForzata){
			popup.show();
		}
		
//		final Record detailRecord = new Record(getValuesManager().getValues());					
//		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
//		lArchivioDatasource.performCustomOperation("getListaPerFirmaSingolaConAllegati", detailRecord, new DSCallback() {
//
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//					
//					clickFirmaComplessivaFile(response.getData()[0], callbackManageFirma);
//				}
//			}
//		}, new DSRequest());
		
	}
	
	//DA QUI
//	private void clickFirmaComplessivaFile(Record record, final ServiceCallback<Record> callbackManageFirma) {
//		
//		if(record.getAttributeAsRecordArray("files").length == 0){
//			Layout.addMessage(new MessageBean("Nessun file da firmare", "", MessageType.ERROR));
//		}else{
//			FirmaUtility.firmaMultipla(record.getAttributeAsRecordArray("files"), new FirmaMultiplaCallback() {
//
//				@Override
//				public void execute(Map<String, Record> files, Record[] filesAndUd) {
//					
//					manageFirmaCallBack(files, filesAndUd, callbackManageFirma);
//				}
//			});
//		}
//	}
//
//	protected void manageFirmaCallBack(final Map<String, Record> files, final Record[] filesAndUd, final ServiceCallback<Record> callbackManageFirma) {
//		
//		Layout.showWaitPopup("Elaborazione del documento firmato in corso...");
//		final Record lRecord = new Record();
//		final Record[] lRecords = new Record[files.size()];
//		int i = 0;
//		for (String lString : files.keySet()) {
//			Record recordToInsert = files.get(lString);
//			for (Record recordConUd : filesAndUd) {
//				if (recordConUd.getAttribute("idFile").equals(lString)) {
//					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
//				}
//			}
//			lRecords[i] = recordToInsert;
//			i++;
//		}
//		lRecord.setAttribute("files", lRecords);
//		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
//		lArchivioDatasource.performCustomOperation("aggiornaDocumentoFirmato", lRecord, new DSCallback() {
//
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//					Layout.hideWaitPopup();
//					Record lCallbackRecord = response.getData()[0];
//					Record[] lRecordsInError = lCallbackRecord.getAttributeAsRecordArray("files");
//					if (lRecordsInError.length > 0 && lRecordsInError.length == lRecords.length) {
//						//La firma di TUTTI i file è andata in errore
//						Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma del documento", "", MessageType.ERROR));
//						return;
//					} else if (lRecordsInError.length > 0){
//						//La firma di ALCUNI file è andata in errore
////						Layout.addMessage(new MessageBean("Alcuni file del documento non sono stati firmati correttamente", "", MessageType.WARNING));
//						SC.ask("Alcuni file del documento non sono stati firmati correttamente. Continuare comunque con l'azione successiva?", new BooleanCallback() {
//							
//							@Override
//							public void execute(Boolean value) {
//								if(value) {
//									manageAzioneSuccessivaFirma(callbackManageFirma);
//								}
//							}
//						});
//					} else if (lRecordsInError.length == 0){
//						//Il documento è stato firmato correttamente
//						Layout.addMessage(new MessageBean("Il documento è stato firmato con successo", "", MessageType.INFO));	
//						manageAzioneSuccessivaFirma(callbackManageFirma);
//					}
//					
//				} else {
//					Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma del documento", "", MessageType.ERROR));
//				}
//			}
//		}, new DSRequest());
//	}
//	
//	public void manageAzioneSuccessivaFirma(final ServiceCallback<Record> callbackManageFirma) {
//		Record currentRecord = new Record(getValuesManager().getValues());
//		AzioneSuccessivaPopup popup = new AzioneSuccessivaPopup(currentRecord, new ServiceCallback<Record>() {
//			
//			@Override
//			public void execute(Record response) {
//				reload(null);
//				callbackManageFirma.execute(response);
//			}
//		}); 
//		popup.show();
//	}
	//A QUI
	
	
	/**
	 * ********************** METODI PER LA FIRMA COMPLESSIVA DEI FILE E PROTOCOLLAZIONE **********************
	 */
	public void manageProtocollaTimbraEFirma(final ServiceCallback<Record> callbackManageFirma) {
		final Record detailRecord = new Record(getValuesManager().getValues());
		detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
		detailRecord.setAttribute("flgTipoProv", detailRecord.getAttribute("flgVersoBozza")); // nel dettaglio flgTipoProv è null perchè è una bozza quindi devo passare flgVersoBozza
		final RecordList listaRecord = new RecordList();
		listaRecord.add(detailRecord);
		final Record recordDaProtocollare = new Record();
		recordDaProtocollare.setAttribute("listaRecord", listaRecord);
		Map<String, String> mappaErrori = new HashMap<>();
		
		DocumentDetail.avviaProtocollaTimbraEFirma(this, recordDaProtocollare, mappaErrori, new ServiceCallback<DSResponse>() {
			
			@Override
			public void execute(DSResponse response) {
				DSCallback protocollaTimbraEFirmaMassiviCallback = new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {														
						reload(new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								setSaved(true);
								if(layout != null) {
									layout.viewMode();
								} else {
									viewMode();														
								}
								if(callbackManageFirma != null) {
									callbackManageFirma.execute(detailRecord);
								}
							}
						});
					}
				};						
				operationCallback(response, detailRecord,
						"idUd", "Firma con segnatura di protocollo effettuata con successo",
						"Errore nella firma con segnatura di protocollo.", 
						protocollaTimbraEFirmaMassiviCallback, 
						protocollaTimbraEFirmaMassiviCallback);		
			}
		});
	}
	
	/**
	 * ********************** METODI PER IL VISTO DELLE UD **********************
	 */
	public void manageVistoDocumento(boolean apposizioneForzata, final ServiceCallback<Record> callbackManageVistoDocumento){
		Record detailRecord = new Record(getValuesManager().getValues());	
		
		/*
		 * detailRecord contiene nel param contrassegnato come idUd l'id dell'ud che si ha selezionato 
		 */
		AzioneApposizionePopup popup = new AzioneApposizionePopup(detailRecord, new Record(getValuesManager().getValues()), 
							TipologiaApposizione.VISTO, apposizioneForzata, null, new ServiceCallback<Record>() {
			
			@Override
			public void execute(final Record object) {
				
				//Ricarico la grafica
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(layout != null) {
							layout.viewMode();
						} else {
							viewMode();														
						}
						if(callbackManageVistoDocumento != null) {
							callbackManageVistoDocumento.execute(object);
						}
					}
				});
			}
		}); 
		//Devo visualizzare il popup per inserire la motivazione nel caso in cui si rifiuti l'apposizione
		if(!apposizioneForzata){
			popup.show();
		}
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE VISTO
	 */
	public boolean showVistoDocumentoButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilVistoElettronico");
	}
	
	/**
	 * GESTIONE STAMPA ETICHETTA POST REGISTRAZIONE
	 */
	private void manageStampaAutomaticaEtichettePostReg(Record record,int nroAssSalvatePrec, boolean isEtichettaRepertorio) {
		if (isAttivaStampaEtichettaPostAss(record)) {	
			
			String numeroNumerazioneSecondaria = record.getAttribute("numeroNumerazioneSecondaria") != null ?
					record.getAttribute("numeroNumerazioneSecondaria") : "";
			String tipoNumerazioneSecondaria = record.getAttribute("tipoNumerazioneSecondaria") != null ?
					record.getAttribute("tipoNumerazioneSecondaria") : "";
			
			// Assegnazioni modificate = false & Repertorio = true
			if(!isAssegnToMod(record, nroAssSalvatePrec) && isEtichettaRepertorio){
				stampaEtichettaPostAssegnazioneRepertorio(numeroNumerazioneSecondaria,
						tipoNumerazioneSecondaria,false,true);		
			}
			// Assegnazioni modificate = true & Repertorio = false
			else if(isAssegnToMod(record, nroAssSalvatePrec) && isEtichettaRepertorio){
				stampaEtichettaPostAssegnazione();		
			}
			// Assegnazioni modificate = true & Repertorio = true
			else if(isAssegnToMod(record, nroAssSalvatePrec) && isEtichettaRepertorio){
				stampaEtichettaPostAssegnazioneRepertorio(numeroNumerazioneSecondaria,
						tipoNumerazioneSecondaria,true,true);		
			}
			// Assegnazioni modificate = false & Repertorio = false
			else if(isAssegnToMod(record, nroAssSalvatePrec) && isEtichettaRepertorio){
				stampaEtichettaPostAssegnazione();		
			}
		}
	}
	
	/**
	 * METODO PER VERIFICARE ABILITAZIONE STAMPA ETICHETTA AUTOMATICA POST ASSEGNAZIONE
	 * Nella modalità Non Wizard non viene verificato il Supporto originale cartaceo
	 */
	protected boolean isAttivaStampaEtichettaPostAss(Record record) {
		return  isConProtocolloGenerale() &&
				AurigaLayout.getParametroDBAsBoolean("ATTIVA_STAMPA_AUTO_ETICH_POST_ASS") &&
				AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg");
	}
	
	/**
	 * Metodo che verifica se sono state variate le assegnazioni in fase di registrazione
	 */
	protected boolean isAssegnToMod(Record record, int nroAssSalvatePrec){
		RecordList listaAssegnazioni = record.getAttributeAsRecordList("listaAssegnazioni");
		int nroAssSalvate = listaAssegnazioni != null ? listaAssegnazioni.getLength() : 0;																																									
		if(nroAssSalvate > nroAssSalvatePrec) {
			return true;														
		}
		return false;
	}
	
	public void showOpzioniTimbratura() {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record record = new Record();
		record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		record.setAttribute("tipoPaginaPref", tipoPaginaPref);

		record.setAttribute("nomeFile", filePrimarioForm.getValueAsString("nomeFilePrimario"));
		record.setAttribute("uriFile", filePrimarioForm.getValueAsString("uriFilePrimario"));
		record.setAttribute("remote", filePrimarioForm.getValueAsString("remoteUriFilePrimario")); // remoteUri
		InfoFileRecord infoFile = filePrimarioForm.getValue("infoFile") != null
				? new InfoFileRecord(filePrimarioForm.getValue("infoFile"))
				: null;
		if (infoFile != null) {
			record.setAttribute("infoFile", infoFile);
		}

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				filePrimarioForm.setValue("opzioniTimbro", object);
			}
		});
		apponiTimbroWindow.show();

	}
		
	/**
	 * Viene verificato se il file da timbrare in fase di post registrazione è convertibile in pdf.
	 */
	public static boolean canBeTimbrato(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null)
			return false;
		else {
			String mimetype = lInfoFileRecord.getMimetype() != null ? lInfoFileRecord.getMimetype() : "";
			if (mimetype != null) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || lInfoFileRecord.isConvertibile()) {
					return true;
				} else
					return false;
			} else
				return false;
		}
	}
	
	private void nuovaProtComeCopiaRepertorio(final String tipologia,final ServiceCallback<Record> callback, final Map values,
			final boolean flgNoMittenti, final boolean flgNoDestinatari, final boolean flgNoAltriAssegnatari,
			final boolean flgNoOggetto, final boolean flgNoAllegati, final boolean flgNoFileAllegati,
			final boolean flgNoDocumentiCollegati, final boolean flgNoFascicolazione) {

			String tipoProtocollo = (String) values.get("tipoProtocollo");
			String repertorio = null;
			if (tipoProtocollo != null && tipoProtocollo.equalsIgnoreCase("PG")) {
				values.put("repertorio", (String) values.get("siglaNumerazioneSecondaria"));
			} else {
				values.put("repertorio", (String) values.get("siglaProtocollo"));
			}
			/**
			 * Viene verificato se l'utente è abilitato ad effettuare l'azione di " nuovo come copia " del repertorio di partenza
			 */
			nuovaProtComeCopia(values, getAttributiDinamiciDocForLoad(), true, flgNoMittenti, flgNoDestinatari, flgNoAltriAssegnatari, 
					flgNoOggetto, true, flgNoAllegati, flgNoFileAllegati, flgNoDocumentiCollegati, flgNoFascicolazione, false, callback);
				// rimango sulla stessa maschera quindi devo aggiornare il  titolo della portlet
			redrawTitleOfPortlet();
			
	}

	public void closeParentWindow(Canvas instance) {
		
		if (AurigaLayout.getParentWindow(instance) != null) {
			if (AurigaLayout.getParentWindow(instance) instanceof Portlet) {
				Layout.removePortlet(((Portlet) AurigaLayout.getParentWindow(instance)).getNomeEntita());
			} else if (AurigaLayout.getParentWindow(instance) instanceof ModalWindow) {
				AurigaLayout.getParentWindow(instance).markForDestroy();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		if(saveModelloWindow != null) {
			saveModelloWindow.destroy();
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}

	protected boolean isValidFutureDate(Date dataInput ) {
		boolean ret = false;
		Date today = new Date();
		if (dataInput.compareTo(today) > 0) {
		    	ret = true;
	    } else if (dataInput.compareTo(today) < 0) {
	        	ret = false;
	    } else if (dataInput.compareTo(today) == 0) {
	        	ret = false;
	    } else {
	        	ret = false;
	    }
		return ret;
	}
	
	protected boolean isValidFutureDateOld(Date dataInput ) {
		
		Date today = new Date();
		String[] todaySplitted = DateUtil.formatAsShortDate(today).split("/");
		int todayGiorno = Integer.valueOf(todaySplitted[0]).intValue();		
		int todayMese = Integer.valueOf(todaySplitted[1]).intValue();					
		int todayAnno = Integer.valueOf(todaySplitted[2]).intValue();		
		
		//Date dataInput = (Date) value;
		String[] dataInputSplitted = DateUtil.formatAsShortDate(dataInput).split("/");
		int dataInputGiorno = Integer.valueOf(dataInputSplitted[0]).intValue();				
		int dataInputMese = Integer.valueOf(dataInputSplitted[1]).intValue();					
		int dataInputAnno = Integer.valueOf(dataInputSplitted[2]).intValue();
		if(dataInputAnno > todayAnno) {
			return true;
		}
		if(dataInputMese > todayMese && dataInputAnno == todayAnno) {
			return true;
		}
		if(dataInputGiorno > todayGiorno && dataInputMese == todayMese && dataInputAnno == todayAnno) {
			return true;
		}			
		return false;
		
	}
	
    private Long getValueNow() {
    	TimeZoneConstants timeZoneConstants = GWT.create(TimeZoneConstants.class);
		TimeZone createTimeZone = TimeZone.createTimeZone( TimeZoneInfo.buildTimeZoneData( timeZoneConstants.europeRome() ) );
        String timezoneGmt = createTimeZone.getGMTString( new Date() ).replace( "GMT", "" );
        timezoneGmt = timezoneGmt.substring(timezoneGmt.indexOf("+"), timezoneGmt.indexOf(":"));
        Long milliseconds = new Date().getTime() + Long.parseLong(timezoneGmt) *60 *60 *1000;
    	return milliseconds;
    }
	
	/****************************************************************
	 * 	       		VERSIONE LAYOUT PER RER - INIZIO       			*
	 ****************************************************************/
	
	public VLayout getLayoutDatiPrincipaliRER() {

		VLayout layoutDatiPrincipaliRER = new VLayout(5);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			layoutDatiPrincipaliRER.setTabIndex(-1);
			layoutDatiPrincipaliRER.setCanFocus(false);		
		}		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiPrincipaliRER.addMember(detailSectionRegistrazione);

		createDetailSectionNuovaRegistrazione();
		detailSectionNuovaRegistrazione.setVisible(false);
		layoutDatiPrincipaliRER.addMember(detailSectionNuovaRegistrazione);
		
		if(showDetailSectionNuovaRegistrazioneProtGenerale()) {
			createDetailSectionNuovaRegistrazioneProtGenerale();			
			layoutDatiPrincipaliRER.addMember(detailSectionNuovaRegistrazioneProtGenerale);
		}

		if(showDetailSectionTipoDocumento()) {
			createDetailSectionTipoDocumento();
			detailSectionTipoDocumento.setVisible(tipoDocumento != null && !"".equals(tipoDocumento));
			layoutDatiPrincipaliRER.addMember(detailSectionTipoDocumento);
		}
		
		createDetailSectionMittenti();
		layoutDatiPrincipaliRER.addMember(detailSectionMittenti);
		
		if (showDetailSectionDestinatari()) {
			createDetailSectionDestinatari();
			layoutDatiPrincipaliRER.addMember(detailSectionDestinatari);
		}
		
		createDetailSectionAssegnazione();
		layoutDatiPrincipaliRER.addMember(detailSectionAssegnazione);

		createDetailSectionCondivisione();
		layoutDatiPrincipaliRER.addMember(detailSectionCondivisione);

		createDetailSectionContenuti();
		layoutDatiPrincipaliRER.addMember(detailSectionContenuti);

		createDetailSectionAllegati();
		layoutDatiPrincipaliRER.addMember(detailSectionAllegati);

		createDetailSectionClassificazioneFascicolazione();
		layoutDatiPrincipaliRER.addMember(detailSectionClassificazioneFascicolazione);
	
		createDetailSectionFolderCustom();
		layoutDatiPrincipaliRER.addMember(detailSectionFolderCustom);
		
		if (showDetailSectionDatiRicezione()) {
			createDetailSectionDatiRicezione();
			layoutDatiPrincipaliRER.addMember(detailSectionDatiRicezione);
		}

		if(showDetailSectionDocCollegato()) {
			createDetailSectionDocCollegato();
			layoutDatiPrincipaliRER.addMember(detailSectionDocCollegato);
		}

		if (showDetailSectionRegEmergenza()) {
			createDetailSectionRegEmergenza();
			layoutDatiPrincipaliRER.addMember(detailSectionRegEmergenza);
		}

		if (showDetailSectionCollocazioneFisica()) {
			createDetailSectionCollocazioneFisica();
			layoutDatiPrincipaliRER.addMember(detailSectionCollocazioneFisica);
		}
		
		createDetailSectionAltriDati();
		layoutDatiPrincipaliRER.addMember(detailSectionAltriDati);
			
		return layoutDatiPrincipaliRER;
	}
	
	/****************************************************************
	 * 	       		VERSIONE LAYOUT PER RER - FINE       			*
	 ****************************************************************/
	
	private boolean isBlank(Object value) {
		return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
	}
	
	private boolean isAbilRaccomandata(Object codiceMezzoTrasmissioneIn){
		
		if(isBlank(codiceMezzoTrasmissioneIn))
			return false;
		
		String codici = AurigaLayout.getParametroDB("COD_MEZZI_RACCOMANDATA");
		
		if(isBlank(codici))
			codici = "R;";
		
		// Se il codice del mezzo di trasmissione e' nel parametro, allora mostro i campi della raccomandata
		String[] tokens = new StringSplitterClient(codici, ";").getTokens();
		if (tokens.length > 0) {			
			for (int i = 0; i < tokens.length; i++) {				
				if (tokens[i].equals(codiceMezzoTrasmissioneIn)) {
                   return true;					
				}
			}
		}
		return false;
	}
	
	private boolean isAbilPostaPrioritaria(Object codiceMezzoTrasmissioneIn){
		
		if(isBlank(codiceMezzoTrasmissioneIn))
			return false;
		
		String codici = AurigaLayout.getParametroDB("COD_MEZZI_RACCOMANDATA");
		
		if(isBlank(codici))
			codici = "PP;";
		
		// Se il codice del mezzo di trasmissione e' nel parametro, allora mostro i campi della raccomandata
		String[] tokens = new StringSplitterClient(codici, ";").getTokens();
		if (tokens.length > 0) {			
			for (int i = 0; i < tokens.length; i++) {				
				if (tokens[i].equals(codiceMezzoTrasmissioneIn)) {
                   return true;					
				}
			}
		}
		return false;
	}
	
	private boolean showOperazioniTimbratura() {
		return codCategoriaProtocolloItem!=null && codCategoriaProtocolloItem.getValue()!=null && !"".equals(codCategoriaProtocolloItem.getValue()) &&
				("PP".equals(codCategoriaProtocolloItem.getValue()) || "PG".equals(codCategoriaProtocolloItem.getValue()) || "R".equals(codCategoriaProtocolloItem.getValue()));
	}
	
	public void setModalitaInvio(String modalitaInvio) {
		this.modalitaInvio = modalitaInvio;
	}
	
	protected boolean isEnablePreviewModal() {
		return !AurigaLayout.getParametroDBAsBoolean("PREVIEW_NON_MODALE");
	}
}