/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.DocumentDetail;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.ApposizioneCommentiPopup;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioDetail;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioFilter;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioList;
import it.eng.auriga.ui.module.layout.client.archivio.AssegnaStatoUdPopup;
import it.eng.auriga.ui.module.layout.client.archivio.AssegnazionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.ClassificazioneFascicolazionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.CondivisionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.EliminazionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.FolderCustomDetail;
import it.eng.auriga.ui.module.layout.client.archivio.LookupAttiAutorizzazioneAnnRegPopup;
import it.eng.auriga.ui.module.layout.client.archivio.MezziTrasmissionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.ModificaPreassegnazionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.OrganizzaPopup;
import it.eng.auriga.ui.module.layout.client.archivio.PraticaPregressaDetail;
import it.eng.auriga.ui.module.layout.client.archivio.RestituzionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.SceltaTipoFolderPopup;
import it.eng.auriga.ui.module.layout.client.archivio.SegnaComeArchiviatoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.SegnaComeVisionatoPopup;
import it.eng.auriga.ui.module.layout.client.archivio.SmistamentoPopup;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.avvioProcedimento.SceltaTipoProcGenPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.InoltroMailWindow;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.auriga.ui.module.layout.client.modelliDoc.ModelliDocDetail;
import it.eng.auriga.ui.module.layout.client.osservazioniNotifiche.OsservazioniNotificheWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.ApposizioneTagCommentiMailWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.AssegnazioneEmailPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DatiOperazioneRichiestaWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.ModificaRegProtAssociatoImgWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.MotiviOperazionePopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.OperazioniPerEmailPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaDetail;
import it.eng.auriga.ui.module.layout.client.postaElettronica.TipoOperazioneMail;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.ApponiTimbroWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.DettaglioDatiPubblAttoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.DettaglioOpereAttoPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailAtti;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailBozze;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailModelli;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PubblicazioneTraspAmmPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.RegistroFattureDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.RepertorioDetailUscita;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.VerificaRegDuplicataWindow;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.AzioneSuRichAccessoAttiPopup;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiDetail;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiWindow;
import it.eng.auriga.ui.module.layout.shared.invioRaccomandate.ETypePoste;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTreeLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.FrecciaDetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class ScrivaniaLayout extends CustomSimpleTreeLayout {

	private String azione;
	private String idNode;
	private String nomeNodo;
	private String idFolder;
	private String tipoNodo;
	private String flgUdFolder;
	private String criteriAvanzati;
	private String classifica;
	private String idUtenteModPec;
	private String codSezione; 
	
	/** AZIONI MASSIVE **/
	private boolean abilApposizioneFirma;
	private boolean apposizioneFirmaProtocollazione;
	private boolean abilRifiutoFirma;
	private boolean abilApposizioneVisto;
	private boolean abilRifiutoVisto;
	private boolean abilConfermaPreassegnazione;
	private boolean abilModificaPreassegnazione;
	private boolean abilInserimentoInAttoAutorizzAnn;
	private boolean abilPresaInCarico;
	private boolean abilClassificazioneFascicolazione;
	private boolean abilFascicolazione;
	private boolean abilFolderizzazione;
	private boolean abilAssegnazione;
	private boolean abilRestituzione;
	private boolean abilSmistamento;
	private boolean abilSmistamentoCC;
	private boolean abilInvioPerConoscenza;
	private boolean abilArchiviazione;
	private boolean abilAnnullamentoArchiviazione;
	private boolean abilAggiuntaAiPreferiti;
	private boolean abilRimozioneDaiPreferiti;
	private boolean abilAssegnazioneRiservatezza;
	private boolean abilRimozioneRiservatezza;
	private boolean abilAnnullamentoEliminazione;
	private boolean abilEliminazione;
	private boolean abilEliminazioneImgScansione;
	private boolean abilAssociazioneImgAProtocollo;
	private boolean abilApposizioneCommenti;
	private boolean abilStampaEtichetta;
	private boolean abilDownloadDocZipMultiButton;
	private boolean abilModificaStatoDocMultiButton;
	private boolean abilModificaTipologiaMultiButton;
	private boolean abilChiudiFascicoloMultiButton;
	private boolean abilRiapriFascicoloMultiButton;
	private boolean abilSegnaComeVisionatoMultiButton;
	
	private boolean abilRichiesteAccessoAttiInvioInApprovazione;
	private boolean abilRichiesteAccessoAttiApprovazione;
	private boolean abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
	private boolean abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
	private boolean abilRichiesteAccessoAttiRegistrazioneAppuntamento;
	private boolean abilRichiesteAccessoAttiAnnullamentoAppuntamento;
	private boolean abilRichiesteAccessoAttiRegistrazionePrelievo;
	private boolean abilRichiesteAccessoAttiRegistrazioneRestituzione;
	private boolean abilRichiesteAccessoAttiAnullamentoRichiesta;
	private boolean abilRichiesteAccessoAttiStampaFoglioPrelievo;
	private boolean abilRichiesteAccessoAttiEliminazioneRichiesta;
	
	private boolean abilEmailChiusuraLavorazione;
	private boolean abilEmailRiaperturaLavorazione;
	private boolean abilEmailAssegnazione;
	private boolean abilEmailAnnullamentoAssegnazione;
	private boolean abilEmailInoltro;
	private boolean abilEmailPresaInCarico;
	private boolean abilEmailMessaInCarico;
	private boolean abilEmailInvioInApprovazione;
	private boolean abilEmailRilascio;
	private boolean abilEmailEliminazione;
	private boolean abilEmailApposizioneTagCommenti;
 
	protected ToolStripButton newUdBozzeButton;
	protected ToolStripButton nuovoMessaggioButton;
	// protected ToolStripButton firmaEmailButton;

	private ArchivioMultiToolStripButton apposizioneFirmaMultiButton; //apposizioneFirma
	private ArchivioMultiToolStripButton rifiutoApposizioneFirmaMultiButton;//rifiutoFirma
	private ArchivioMultiToolStripButton protocollaEFirmaMultiButton; //protocollaEFirma
	private ArchivioMultiToolStripButton vistoMultiButtonApposizione;//apposizioneVisto
	private ArchivioMultiToolStripButton vistoMultiButtonRifiuto;//rifiutoVisto
	private ArchivioMultiToolStripButton confermaAssegnazioneMultiButton;//confermaPreassegnazione 
	private ArchivioMultiToolStripButton modificaPreassegnazioneMultiButton;//modificaPreassegnazione  
	private ArchivioMultiToolStripButton inserisciInAttoAutMultiButton;//inserimentoInAttoAutorizzAnn
	private ArchivioMultiToolStripButton prendiInCaricoMultiButton;//presaInCarico
	private ArchivioMultiToolStripButton classificaMultiButton; // classificazioneFascicolazione
	private ArchivioMultiToolStripButton fascicolaMultiButton; // fascicolazione
	private ArchivioMultiToolStripButton organizzaMultiButton; // folderizzazione 
	private ArchivioMultiToolStripButton assegnaMultiButton; //assegnazione 
	private ArchivioMultiToolStripButton restituisciMultiButton; // restituzione 
	private ArchivioMultiToolStripButton smistaMultiButton; // smistamento 
	private ArchivioMultiToolStripButton smistaCCMultiButton; // smistamento 
	private ArchivioMultiToolStripButton condividiMultiButton;//invioPerConoscenza 
//	private ArchivioMultiToolStripButton archiviazioneMultiButton; 
	private ArchivioMultiToolStripButton annullaArchiviazioneMultiButton;//annullamentoArchiviazione 
	private ArchivioMultiToolStripButton aggiungiAPreferitiMultiButton;//aggiuntaAiPreferiti
	private ArchivioMultiToolStripButton rimuoviDaPreferitiMultiButton;//rimozioneDaiPreferiti 
	private ArchivioMultiToolStripButton assegnaRiservatezzaMultiButton;//assegnazioneRiservatezza 
	private ArchivioMultiToolStripButton rimuoviRiservatezzaMultiButton;//rimozioneRiservatezza 
	private ArchivioMultiToolStripButton ripristinaMultiButton;// annullamentoEliminazione 
	private ArchivioMultiToolStripButton eliminaMultiButton;//eliminazione
	private ArchivioMultiToolStripButton eliminaImgScansioneMultiButton;//eliminazione file immgani della scansione
	
	private ArchivioMultiToolStripButton apposizioneCommentiMultiButton;//apposizioneCommenti 
	private ArchivioMultiToolStripButton stampaEtichettaMultiButton;//stampaEtichetta 
	private ArchivioMultiToolStripButton downloadDocZipMultiButton;//downloadComeZip 
	private ArchivioMultiToolStripButton modificaStatoDocMultiButton;//impostazioneStatoDocumento 
	private ArchivioMultiToolStripButton modificaTipologiaMultiButton;//modificaTipologiaDocumento 
	private ArchivioMultiToolStripButton chiudiFascicoloMultiButton;//chiusuraFascicolo 
	private ArchivioMultiToolStripButton riapriFascicoloMultiButton;//riaperturaFascicolo 
	private ArchivioMultiToolStripButton segnaComeVisionatoMultiButton;//impostazioneComeVisionato 
	private ArchivioMultiToolStripButton segnaComeArchiviatoMultiButton;//impostazioneComeArchiviato 
	private ArchivioMultiToolStripButton associazioneImgAProtocolloMultiButton; 

	
	// OPERAZIONI SULLE RICHIESTE ACCESSO ATTI
	private ToolStripButton nuovaRichiestaAttoButton;
	private RichAccessoAttiMultiToolStripButton invioInApprovazioneMultiButton;//richiesteAccessoAtti.invioInApprovazione
	private RichAccessoAttiMultiToolStripButton approvazioneMultiButton;//richiesteAccessoAtti.approvazione 
	private RichAccessoAttiMultiToolStripButton invioEsitoVerificaArchivioMultiButton;//richiesteAccessoAtti.invioEsitoVerificaArchivio
	private RichAccessoAttiMultiToolStripButton abilitaAppuntamentoDaAgendaMultiButton;//richiesteAccessoAtti.abilitazioneAppuntamentoDaAgenda
	private RichAccessoAttiMultiToolStripButton setAppuntamentoMultiButton;//richiesteAccessoAtti.registrazioneAppuntamento             
	private RichAccessoAttiMultiToolStripButton annullamentoAppuntamentoMultiButton;//richiesteAccessoAtti.annullamentoAppuntamento   
	private RichAccessoAttiMultiToolStripButton registraPrelievoMultiButton;//richiesteAccessoAtti.registrazionePrelievo           
	private RichAccessoAttiMultiToolStripButton registraRestituzioneMultiButton;//richiesteAccessoAtti.registrazioneRestituzione       
	private RichAccessoAttiMultiToolStripButton annullamentoMultiButton;//richiesteAccessoAtti.anullamentoRichiesta
	private RichAccessoAttiMultiToolStripButton stampaFoglioPrelievoMultiButton;//richiesteAccessoAtti.stampaFoglioPrelievo
	private RichAccessoAttiMultiToolStripButton eliminaRichAccessoAttiMultiButton;//richiesteAccessoAtti.eliminazioneRichiesta
	
	// OPERAZIONI SUI MODELLI DOCUMENTALI
	private ToolStripButton newModelloDocButton;
	
	// OPERAZIONI SULLE EMAIL
	private EmailMultiToolStripButton archiviaEmailMultiButton;//email.chiusuraLavorazione
	private EmailMultiToolStripButton annullaArchiviazioneEmailMultiButton;//email.riaperturaLavorazione
	private EmailMultiToolStripButton assegnaEmailMultiButton;//email.assegnazione
	private EmailMultiToolStripButton annullaAssegnaEmailMultiButton;//email.annullamentoAssegnazione
	private EmailMultiToolStripButton inoltraEmailMultiButton;//email.inoltro 
	private EmailMultiToolStripButton presaInCaricoEmailMultiButton;//email.presaInCarico 
	private EmailMultiToolStripButton messaInCaricoEmailMultiButton;//email.messaInCarico 
	private EmailMultiToolStripButton mandaInApprovazioneMultiButton;//email.invioInApprovazione 
	private EmailMultiToolStripButton rilasciaCaricoEmailMultiButton;//email.rilascio 
	private EmailMultiToolStripButton eliminaEmailMultiButton;//email.eliminazione 
	private EmailMultiToolStripButton apposizioneTagCommentiMultiButton;//email.apposizioneTagCommenti

	protected DetailToolStripButton stampaEtichettaButton;
	protected DetailToolStripButton stampaRicevutaButton;
	protected FrecciaDetailToolStripButton frecciaStampaEtichettaButton;
	protected DetailToolStripButton assegnazioneButton;
	
	protected DetailToolStripButton smistaButton;
	protected DetailToolStripButton smistaCCButton;
	protected DetailToolStripButton invioAlProtocolloButton;
	protected FrecciaDetailToolStripButton frecciaInvioAlProtocolloButton;
	protected FrecciaDetailToolStripButton frecciaAssegnazioneButton;
	protected DetailToolStripButton assegnaCondividiButton;
	protected FrecciaDetailToolStripButton frecciaAssegnaCondividiButton;
	protected DetailToolStripButton rispondiButton;
	protected DetailToolStripButton condivisioneButton;
	protected FrecciaDetailToolStripButton frecciaCondivisioneButton;
	protected DetailToolStripButton stampaButton;
	protected DetailToolStripButton nuovaProtComeCopiaButton;
	protected DetailToolStripButton profilaModelloDocButton;
	protected DetailToolStripButton presaInCaricoButton;
	protected DetailToolStripButton restituisciButton;
	protected DetailToolStripButton segnaComeVisionatoButton;
	protected DetailToolStripButton segnaComeArchiviatoButton;
	protected DetailToolStripButton classificazioneFascicolazioneButton;
	protected DetailToolStripButton modificaButton;
	protected FrecciaDetailToolStripButton frecciaModificaButton;
	protected DetailToolStripButton regAccessoCivicoButton;
	protected DetailToolStripButton modificaDatiRegButton;
	protected DetailToolStripButton revocaAttoButton;
	protected DetailToolStripButton protocollazioneEntrataButton;
	protected DetailToolStripButton protocollazioneUscitaButton;
	protected DetailToolStripButton protocollazioneInternaButton;
	// protected DetailToolStripButton permessiUdButton;
	protected DetailToolStripButton invioPECButton;
	protected DetailToolStripButton invioPEOButton;
	protected DetailToolStripButton inviaRaccomandataButton;
	protected DetailToolStripButton inviaPostaPrioritariaButton;
	protected DetailToolStripButton verificaRegistrazioneButton;
	protected DetailToolStripButton salvaComeModelloButton;
	
	// Dettaglio Pratica pregressa
	protected DetailToolStripButton registraPrelievoButton;
	protected DetailToolStripButton modificaPrelievoButton;
	protected DetailToolStripButton eliminaPrelievoButton;
	protected DetailToolStripButton registraRestituzionePrelievoButton;

	protected DetailToolStripButton downloadDocZipButton;
	protected FrecciaDetailToolStripButton frecciaDownloadZipButton;
	protected DetailToolStripButton chiudiFascicoloButton;
	protected DetailToolStripButton riapriFascicoloButton;
	protected DetailToolStripButton versaInArchivioStoricoFascicoloButton;
	protected DetailToolStripButton avviaIterButton;
	protected DetailToolStripButton osservazioniNotificheButton;
	protected DetailToolStripButton apposizioneFirmaButton;
	protected DetailToolStripButton apposizioneFirmaProtocollazioneButton;
	protected DetailToolStripButton rifiutoApposizioneFirmaButton;
	protected DetailToolStripButton vistoButtonApposizione;
	protected DetailToolStripButton vistoButtonRifiuto;
	protected DetailToolStripButton pubblicazioneTraspAmmButton;
	
	/*******************************************
	 * FLAG ABILITAZIONI MODIFICAFRECCIABUTTON *
	 *******************************************/
	
	private boolean isAbilModificaTipologia;
	private boolean isAbilModificaDatiExtraIter;
	private boolean isAbilModificaOpereAtto;
	private boolean isAbilModificaDatiPubblAtto;
	
	//Taglia ed Incolla UO
	protected Record cutNode;

	private final int ALT_POPUP_ERR_MASS = 300;
	private final int LARG_POPUP_ERR_MASS = 600;

	public ScrivaniaLayout() {
		super("scrivania", "archivio", new GWTRestDataSource("ScrivaniaTreeDatasource", true, "idNode", FieldType.TEXT), 
				new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT), 
				new ScrivaniaTree("scrivania"),
				new ArchivioFilter("archivio", null), 
				new ArchivioList("archivio", true), 
				new CustomDetail("archivio"));

		this.newButton.hide();
	}

	@Override
	public void doSimpleSearch(AdvancedCriteria criteria) {
		
		super.doSimpleSearch(criteria);
		((ScrivaniaTree) tree).aggiornaNroContenutiScrivania();
	}

	@Override
	public boolean showMultiselectButtonsUnderList() {
		
		return (azione == null || !"emergenza".equals(azione));
	}
	
	@Override
	public boolean showPaginazioneItems() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_SCRIVANIA")) {
			if (idNode == null) {
				return true; // questo è il caso in cui non ho ancora selezionato una sezione della scrivania, quindi devo tornare true per fare in modo che i campi della paginazione vengano aggiunti al layout della maschera 
			} else if (idNode.startsWith("E.")) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_CRUSCOTTO_MAIL");
			} else if(azione != null && "archivio".equals(azione)) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_ARCHIVIO");
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void reload(final DSCallback callback) {
		
		if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).reload(callback);
		} else if(detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).reload(callback);
		} /*else if(detail instanceof ModelliDocDetail) {
			((ModelliDocDetail) detail).reload(callback);
		}*/ else {
			super.reload(callback);
		}
	}
	
	@Override
	public void reloadDetailCallback() {	
		
		if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).reloadDetailCallback();
		} else {
			super.reloadDetailCallback();
		}
	}

	@Override
	public void delete(final Record record) {
		if (detail instanceof ArchivioDetail || detail instanceof FolderCustomDetail) {
			((ArchivioList) list).deleteFascicoloFromList(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					hideDetail(true);
				}
			});
		}
	}

	public void setSaveButtonTitle(String title) {
		
		saveButton.setTitle(title);
	}

	@Override
	public void newMode() {
		newMode(false);
	}

	public void newComeCopiaMode() {
		newMode(true);
	}

	public void newMode(boolean isNewProtComeCopia) {		
		// Commento super.newMode() e richiamo direttamente la sua implementazione qui dentro, facendo lo skip di detail.newMode(), altrimenti verrebbe impostata la modalità in aggiunta su cartelle e fascicoli, anche se sto facendo il nuovo come copia
//		super.newMode();
		manageNewMode(true);		
		profilaModelloDocButton.hide();
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();		
		saveButton.setTitle(I18NUtil.getMessages().saveButton_prompt());
		if (detail instanceof ArchivioDetail) {
			((ArchivioDetail) detail).newMode();
			verificaRegistrazioneButton.hide();
			altreOpButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof FolderCustomDetail) {
			((FolderCustomDetail) detail).newMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).newMode(isNewProtComeCopia);
			saveButton.setTitle(I18NUtil.getMessages().protocollazione_detail_registraButton_prompt());
			altreOpButton.hide();
			deleteButton.hide();
			if (detail instanceof ProtocollazioneDetailBozze) {
				verificaRegistrazioneButton.hide();
				salvaComeModelloButton.show();
				setSaveButtonTitle(I18NUtil.getMessages().bozze_creanuovabozza_salva());
			} else {
				verificaRegistrazioneButton.show();
				if (((ProtocollazioneDetail) detail).showModelliSelectItem()) {
					salvaComeModelloButton.show();
				} else {
					salvaComeModelloButton.hide();
				}
			}
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).newMode();
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof ModelliDocDetail) {
			((ModelliDocDetail) detail).newMode();
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else {
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		}
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		assegnazioneButton.hide();
		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
		condivisioneButton.hide();
		frecciaCondivisioneButton.hide();
		smistaButton.hide();
		smistaCCButton.hide();
		invioAlProtocolloButton.hide();
		frecciaInvioAlProtocolloButton.hide();
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
		stampaButton.hide();
		stampaRicevutaButton.hide();
		nuovaProtComeCopiaButton.hide();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		segnaComeArchiviatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		frecciaModificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		revocaAttoButton.hide();
		protocollazioneEntrataButton.hide();
		protocollazioneUscitaButton.hide();
		protocollazioneInternaButton.hide();
		// permessiUdButton.hide();
		invioPECButton.hide();
		invioPEOButton.hide();
		inviaRaccomandataButton.hide();
		inviaPostaPrioritariaButton.hide();
		downloadDocZipButton.hide();
		frecciaDownloadZipButton.hide();
		chiudiFascicoloButton.hide();
		riapriFascicoloButton.hide();
		versaInArchivioStoricoFascicoloButton.hide();
		avviaIterButton.hide();
		osservazioniNotificheButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneFirmaProtocollazioneButton.hide();
		vistoButtonApposizione.hide();
		vistoButtonRifiuto.hide();
		pubblicazioneTraspAmmButton.hide();
	}

	@Override
	public void viewMode() {		
		boolean isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");		
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			detail.viewMode();
			if (fullScreenDetail && !showOnlyDetail) {
				backToListButton.show();
			} else {
				backToListButton.hide();
			}
			if (multiselect) {
				editButton.hide();
			} else {
				editButton.show();
			}
			saveButton.hide();
			reloadDetailButton.hide();
			undoButton.hide();
			deleteButton.show();
			altreOpButton.show();
			if (isLookup()) {
				lookupButton.show();
			} else {
				lookupButton.hide();
			}
		} else {
			super.viewMode();
		}		
		profilaModelloDocButton.hide();
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();
		rispondiButton.hide();		
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof ArchivioDetail) {
			((ArchivioDetail) detail).viewMode();
			if (record.getAttributeAsBoolean("abilModificaDati")) {
				editButton.show();
			} else {
				editButton.hide();
			}
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			if (record.getAttributeAsBoolean("abilArchiviazione")) {
				segnaComeArchiviatoButton.show();
			} else {
				segnaComeArchiviatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			if (record.getAttributeAsBoolean("abilModificaTipologia")) {
				frecciaModificaButton.show();
			} else {
				frecciaModificaButton.hide();
			}
			if (record.getAttributeAsBoolean("abilEliminazione")) {
				deleteButton.show();
			} else {
				deleteButton.hide();
			}
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();	
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			if (record.getAttributeAsBoolean("abilStampaCopertina") || record.getAttributeAsBoolean("abilStampaSegnatura") || record.getAttributeAsBoolean("abilStampaListaContenuti")) {
				String titleUnicaStampaAbilitata = getTitleUnicaStampaAbilitata(record);
				stampaButton.setTitle(titleUnicaStampaAbilitata != null ? titleUnicaStampaAbilitata : "Stampa ...");
				stampaButton.show();									
			} else {
				stampaButton.hide();				
			}
			if (record.getAttributeAsBoolean("abilStampaRicevuta")) {
				stampaRicevutaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
				stampaRicevutaButton.show();									
			} else {
				stampaRicevutaButton.hide();				
			}
			nuovaProtComeCopiaButton.hide();
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			if (record.getAttributeAsBoolean("abilArchiviazione")) {
				segnaComeArchiviatoButton.show();
			} else {
				segnaComeArchiviatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			salvaComeModelloButton.hide();
			if (record.getAttributeAsBoolean("abilAvvioIterWF")) {
				avviaIterButton.show();
			} else {
				avviaIterButton.hide();
			}
			if (record.getAttributeAsBoolean("abilOsservazioniNotifiche")){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			vistoButtonApposizione.hide();
			vistoButtonRifiuto.hide();
			pubblicazioneTraspAmmButton.hide();
		} else if (detail instanceof FolderCustomDetail) {
			((FolderCustomDetail) detail).viewMode();
			if (record.getAttributeAsBoolean("abilModificaDati")) {
				editButton.show();
			} else {
				editButton.hide();
			}
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			if (record.getAttributeAsBoolean("abilArchiviazione")) {
				segnaComeArchiviatoButton.show();
			} else {
				segnaComeArchiviatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			if (record.getAttributeAsBoolean("abilModificaTipologia")) {
				frecciaModificaButton.show();
			} else {
				frecciaModificaButton.hide();
			}
			if (record.getAttributeAsBoolean("abilEliminazione")) {
				deleteButton.show();
			} else {
				deleteButton.hide();
			}
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaButton.hide();
			stampaRicevutaButton.hide();
			nuovaProtComeCopiaButton.hide();
			presaInCaricoButton.hide();
			if (record.getAttributeAsBoolean("abilRestituzione")) {
				restituisciButton.show();
			} else {
				restituisciButton.hide();
			}
			if (record.getAttributeAsBoolean("abilSetVisionato")) {
				segnaComeVisionatoButton.show();
			} else {
				segnaComeVisionatoButton.hide();
			}
			if (record.getAttributeAsBoolean("abilArchiviazione")) {
				segnaComeArchiviatoButton.show();
			} else {
				segnaComeArchiviatoButton.hide();
			}
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			salvaComeModelloButton.hide();
			if (record.getAttributeAsBoolean("abilAvvioIterWF")) {
				avviaIterButton.show();
			} else {
				avviaIterButton.hide();
			}
			if (record.getAttributeAsBoolean("abilOsservazioniNotifiche")){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			vistoButtonApposizione.hide();
			vistoButtonRifiuto.hide();
			pubblicazioneTraspAmmButton.hide();
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showRegistraPrelievoButton()) {
				registraPrelievoButton.show();
			}
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showModificaPrelievoButton()) {
				modificaPrelievoButton.show();
			}
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showEliminaPrelievoButton()) {
				eliminaPrelievoButton.show();
			}
			if ((detail instanceof PraticaPregressaDetail) && ((PraticaPregressaDetail) detail).showRegistraRestituzionePrelievoButton()) {
				registraRestituzionePrelievoButton.show();
			}
		} else if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).viewMode();
			if (detail instanceof ProtocollazioneDetailBozze) {
				stampaEtichettaButton.hide();
				frecciaStampaEtichettaButton.hide();
//				if(showAssegnazioneButton(record)){
//					assegnazioneButton.show();
//					frecciaAssegnazioneButton.show();
//				}else{
					assegnazioneButton.hide();
					frecciaAssegnazioneButton.hide();
//				}
//				if(showCondivisioneButton(record)){
//					condivisioneButton.show();		
//					frecciaCondivisioneButton.show();
//				}else{
					condivisioneButton.hide();
					frecciaCondivisioneButton.hide();
//				}
				if(showSmistaButton(record)) {
					smistaButton.show();
				} else {
					smistaButton.hide();
				}
				if(showSmistaCCButton(record)) {
					smistaCCButton.show();
				} else {
					smistaCCButton.hide();
				}
				if(showInvioAlProtocolloButton(record)) {
					invioAlProtocolloButton.show();
					frecciaInvioAlProtocolloButton.show();
				} else {
					invioAlProtocolloButton.hide();
					frecciaInvioAlProtocolloButton.hide();
				}
				if(!showAssegnazioneButton(record) && !showCondivisioneButton(record) && !showModAssInviiCCButton(record)) {
					assegnaCondividiButton.hide();
					frecciaAssegnaCondividiButton.hide();
				} else {
					assegnaCondividiButton.show();
					frecciaAssegnaCondividiButton.show();
				}
				if (record.getAttributeAsBoolean("abilStampaCopertina")) {
					stampaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt());
					stampaButton.show();
				} else {
					stampaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilStampaRicevuta")) {
					stampaRicevutaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
					stampaRicevutaButton.show();
				} else {
					stampaRicevutaButton.hide();
				}
				if(record.getAttributeAsBoolean("abilNuovoComeCopia")) {
					nuovaProtComeCopiaButton.show();
				} else {
					nuovaProtComeCopiaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilPresaInCarico")) {
					presaInCaricoButton.show();
				} else {
					presaInCaricoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRestituzione")) {
					restituisciButton.show();
				} else {
					restituisciButton.hide();
				}
				if (record.getAttributeAsBoolean("abilSetVisionato")) {
					segnaComeVisionatoButton.show();
				} else {
					segnaComeVisionatoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilArchiviazione")) {
					segnaComeArchiviatoButton.show();
				} else {
					segnaComeArchiviatoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilClassificazioneFascicolazione") ||
						record.getAttributeAsBoolean("abilOrganizza")) {
					classificazioneFascicolazioneButton.show();
				} else {
					classificazioneFascicolazioneButton.hide();
				}
				if(record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilModificaDati")) {
					modificaButton.show();
				} else {
					modificaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
					regAccessoCivicoButton.show();
				} else {
					regAccessoCivicoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaTipologia") || record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilModificaOpereAtto") || record.getAttributeAsBoolean("abilModificaDatiPubblAtto")) {
					frecciaModificaButton.show();
				} else {
					frecciaModificaButton.hide();
				}
				modificaDatiRegButton.hide();
				salvaComeModelloButton.hide();
				revocaAttoButton.hide();				
				if(detail instanceof ProtocollazioneDetailModelli && showProtocollazioneEntrataButton(record)){
					protocollazioneEntrataButton.show();
				}else{
					protocollazioneEntrataButton.hide();
				}
				if(showProtocollazioneUscitaButton(record)){
					protocollazioneUscitaButton.show();
				}else{
					protocollazioneUscitaButton.hide();
				}
				if(showProtocollazioneInternaButton(record)){
					protocollazioneInternaButton.show();
				}else{
					protocollazioneInternaButton.hide();
				}
				if (showFirmaButton(record)){
					apposizioneFirmaButton.show();
					rifiutoApposizioneFirmaButton.show();
				}else{
					apposizioneFirmaButton.hide();
					rifiutoApposizioneFirmaButton.hide();
				}
				if (showFirmaProtocollaButton(record)){
					apposizioneFirmaProtocollazioneButton.show();
				}else{
					apposizioneFirmaProtocollazioneButton.hide();
				}
				if (showVistoDocumentoButton(record)){
					vistoButtonApposizione.show();
					vistoButtonRifiuto.show();
				}else{
					vistoButtonApposizione.hide();
					vistoButtonRifiuto.hide();
				}
			} else {
				if (((ProtocollazioneDetail) detail).showStampaEtichettaButton(record)) {				
					stampaEtichettaButton.show();
					if (((ProtocollazioneDetail) detail).showFrecciaStampaEtichettaButton(record)) {
						frecciaStampaEtichettaButton.show();
					} else {
						frecciaStampaEtichettaButton.hide();
					}
				} else {
					stampaEtichettaButton.hide();
					frecciaStampaEtichettaButton.hide();
				}
//				if(showAssegnazioneButton(record)){
//					assegnazioneButton.show();
//					frecciaAssegnazioneButton.show();
//				}else{
					assegnazioneButton.hide();
					frecciaAssegnazioneButton.hide();
//				}
				if(!(detail instanceof RepertorioDetailUscita) && !(detail instanceof ProtocollazioneDetailUscita)){
					if(showRispondiButton(record)){
						rispondiButton.show();
					}	
				}
//				if(showCondivisioneButton(record)){
//					condivisioneButton.show();		
//					frecciaCondivisioneButton.show();
//				}else{
					condivisioneButton.hide();
					frecciaCondivisioneButton.hide();
//				}
				if(showSmistaButton(record)) {
					smistaButton.show();
				} else {
					smistaButton.hide();
				}
				if(showSmistaCCButton(record)) {
					smistaCCButton.show();
				} else {
					smistaCCButton.hide();
				}
				if(showInvioAlProtocolloButton(record)) {
					invioAlProtocolloButton.show();
					frecciaInvioAlProtocolloButton.show();
				} else {
					invioAlProtocolloButton.hide();
					frecciaInvioAlProtocolloButton.hide();
				}
				if(!showAssegnazioneButton(record) && !showCondivisioneButton(record) && !showModAssInviiCCButton(record)) {
					assegnaCondividiButton.hide();
					frecciaAssegnaCondividiButton.hide();
				} else {
					assegnaCondividiButton.show();
					frecciaAssegnaCondividiButton.show();
				}
				if (record.getAttributeAsBoolean("abilStampaCopertina")) {
					stampaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaCopertinaButton_prompt());
					stampaButton.show();
				} else {
					stampaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilStampaRicevuta")) {
					stampaRicevutaButton.setTitle(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt());
					stampaRicevutaButton.show();									
				} else {
					stampaRicevutaButton.hide();				
				}
				if (((ProtocollazioneDetail) detail).isAbilToProt()) {
					if(record.getAttributeAsBoolean("abilNuovoComeCopia")) {
						nuovaProtComeCopiaButton.show();
					} else {
						nuovaProtComeCopiaButton.hide();
					}
				} else {
					nuovaProtComeCopiaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilPresaInCarico")) {
					presaInCaricoButton.show();
				} else {
					presaInCaricoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRestituzione")) {
					restituisciButton.show();
				} else {
					restituisciButton.hide();
				}
				if (record.getAttributeAsBoolean("abilSetVisionato")) {
					segnaComeVisionatoButton.show();
				} else {
					segnaComeVisionatoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilArchiviazione")) {
					segnaComeArchiviatoButton.show();
				} else {
					segnaComeArchiviatoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilClassificazioneFascicolazione") ||
						record.getAttributeAsBoolean("abilOrganizza")) {
					classificazioneFascicolazioneButton.show();
				} else {
					classificazioneFascicolazioneButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilAggiuntaFile") || record.getAttributeAsBoolean("abilModificaDati")) {
					modificaButton.show();
				} else {
					modificaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
					regAccessoCivicoButton.show();
				} else {
					regAccessoCivicoButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaTipologia") || record.getAttributeAsBoolean("abilModificaDatiExtraIter") || record.getAttributeAsBoolean("abilModificaOpereAtto") || record.getAttributeAsBoolean("abilModificaDatiPubblAtto")) {
					frecciaModificaButton.show();
				} else {
					frecciaModificaButton.hide();
				}
				if (record.getAttributeAsBoolean("abilModificaDatiRegistrazione")) {
					modificaDatiRegButton.show();
				} else {
					modificaDatiRegButton.hide();
				}
				if (((ProtocollazioneDetail) detail).showModelliSelectItem()) {
					salvaComeModelloButton.show();
				} else {
					salvaComeModelloButton.hide();
				}
				if(showRevocaAttoButton(record)){
					revocaAttoButton.show();
				}else{
					revocaAttoButton.hide();
				}
				protocollazioneEntrataButton.hide();
				protocollazioneUscitaButton.hide();
				protocollazioneInternaButton.hide();
				if (showFirmaButton(record)){
					apposizioneFirmaButton.show();
					rifiutoApposizioneFirmaButton.show();
				}else{
					apposizioneFirmaButton.hide();
					rifiutoApposizioneFirmaButton.hide();
				}
				if (showFirmaProtocollaButton(record)){
					apposizioneFirmaProtocollazioneButton.show();
				}else{
					apposizioneFirmaProtocollazioneButton.hide();
				}
				if (showVistoDocumentoButton(record)){
					vistoButtonApposizione.show();
					vistoButtonRifiuto.show();
				}else{
					vistoButtonApposizione.hide();
					vistoButtonRifiuto.hide();
				}
			}
			// permessiUdButton.show();
			if (record.getAttributeAsBoolean("abilInvioPEC")) {
				invioPECButton.show();
			} else {
				invioPECButton.hide();
			}
			if (record.getAttributeAsBoolean("abilInvioPEO")) {
				invioPEOButton.show();
			} else {
				invioPEOButton.hide();
			}
			if (Layout.isPrivilegioAttivo("PRT/U") && ((ProtocollazioneDetail) detail) instanceof ProtocollazioneDetailUscita && !(record.getAttributeAsBoolean("annullata")) && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")) {
				ProtocollazioneUtil.isPossibleToPostel(record.getAttributeAsInt("idUd"), ETypePoste.RACCOMANDATA.value(), new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record record) {
						if(record.getAttributeAsBoolean("isDaPostalizzare")){
							inviaRaccomandataButton.show();
						}else {
							inviaRaccomandataButton.hide();
						}
					}
				});
			}else {
				inviaRaccomandataButton.hide();
			}
			if (Layout.isPrivilegioAttivo("PRT/U") && ((ProtocollazioneDetail) detail) instanceof ProtocollazioneDetailUscita && !(record.getAttributeAsBoolean("annullata")) && AurigaLayout.getParametroDB("CLIENTE").equalsIgnoreCase("ARPA_LAZ")) {
				ProtocollazioneUtil.isPossibleToPostel(record.getAttributeAsInt("idUd"), ETypePoste.POSTA_PRIORITARIA.value(), new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record record) {
						if(record.getAttributeAsBoolean("isDaPostalizzare")){
							inviaPostaPrioritariaButton.show();
						}else {
							inviaPostaPrioritariaButton.hide();
						}
					}
				});
			}else {
				inviaPostaPrioritariaButton.hide();
			}
			editButton.hide();
			deleteButton.hide();
			if (record.getAttributeAsBoolean("abilAvvioIterWF")) {
				avviaIterButton.show();
			} else {
				avviaIterButton.hide();
			}
			if (record.getAttributeAsBoolean("abilOsservazioniNotifiche")){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			if (record.getAttributeAsBoolean("abilPubblicazioneTraspAmm")) {
				pubblicazioneTraspAmmButton.show();
			} else {
				pubblicazioneTraspAmmButton.hide();
			}	
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).viewMode();
			if (record.getAttributeAsBoolean("abilModificaDati")) {
				editButton.show();
			} else {
				editButton.hide();
			}
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			segnaComeArchiviatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			if (record.getAttributeAsBoolean("abilModificaTipologia")) {
				frecciaModificaButton.show();
			} else {
				frecciaModificaButton.hide();
			}
			if (record.getAttributeAsBoolean("abilOsservazioniNotifiche")){
				osservazioniNotificheButton.show();
			}else{
				osservazioniNotificheButton.hide();
			}
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
//			// Se il bottone di assegnazione va' mostrato anche in RichiestaAccessiAttiDetail, allora i vari metodi vanno sistemati e centralizzati in DocumentDetail
//			if(showAssegnazioneButton(record)){
//				assegnazioneButton.show();
//				frecciaAssegnazioneButton.show();
//			}else{
				assegnazioneButton.hide();
				frecciaAssegnazioneButton.hide();
//			}
			if(showCondivisioneButton(record)){
				condivisioneButton.show();		
				frecciaCondivisioneButton.show();
			}else{
				condivisioneButton.hide();
				frecciaCondivisioneButton.hide();
			}
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaButton.hide();
			stampaRicevutaButton.hide();
			nuovaProtComeCopiaButton.hide();	
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			segnaComeArchiviatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
			frecciaModificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			deleteButton.hide();
			salvaComeModelloButton.hide();
			avviaIterButton.hide();
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			vistoButtonApposizione.hide();
			vistoButtonRifiuto.hide();
			pubblicazioneTraspAmmButton.hide();
		} else if (detail instanceof ModelliDocDetail) {
			((ModelliDocDetail) detail).viewMode();
			editButton.show();
			if(((ModelliDocDetail) detail).isRecordAbilToProfile(record)) {
				profilaModelloDocButton.show();
			} else {
				profilaModelloDocButton.hide();
			}
			osservazioniNotificheButton.hide();
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaButton.hide();
			stampaRicevutaButton.hide();
			nuovaProtComeCopiaButton.hide();
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			segnaComeArchiviatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			deleteButton.hide();
			salvaComeModelloButton.hide();
			avviaIterButton.hide();			
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			vistoButtonApposizione.hide();
			vistoButtonRifiuto.hide();
			pubblicazioneTraspAmmButton.hide();
		} else {
			stampaEtichettaButton.hide();
			frecciaStampaEtichettaButton.hide();
			assegnazioneButton.hide();
			frecciaAssegnazioneButton.hide();
			condivisioneButton.hide();
			frecciaCondivisioneButton.hide();
			smistaButton.hide();
			smistaCCButton.hide();
			invioAlProtocolloButton.hide();
			frecciaInvioAlProtocolloButton.hide();
			assegnaCondividiButton.hide();
			frecciaAssegnaCondividiButton.hide();
			stampaButton.hide();
			stampaRicevutaButton.hide();
			nuovaProtComeCopiaButton.hide();
			editButton.hide();
			presaInCaricoButton.hide();
			restituisciButton.hide();
			segnaComeVisionatoButton.hide();
			segnaComeArchiviatoButton.hide();
			classificazioneFascicolazioneButton.hide();
			modificaButton.hide();
			regAccessoCivicoButton.hide();
			modificaDatiRegButton.hide();
			// permessiUdButton.hide();
			invioPECButton.hide();
			invioPEOButton.hide();
			inviaRaccomandataButton.hide();
			inviaPostaPrioritariaButton.hide();
			deleteButton.hide();
			salvaComeModelloButton.hide();
			avviaIterButton.hide();			
			revocaAttoButton.hide();
			protocollazioneEntrataButton.hide();
			protocollazioneUscitaButton.hide();
			protocollazioneInternaButton.hide();
			apposizioneFirmaButton.hide();
			rifiutoApposizioneFirmaButton.hide();
			apposizioneFirmaProtocollazioneButton.hide();
			vistoButtonApposizione.hide();
			vistoButtonRifiuto.hide();
			pubblicazioneTraspAmmButton.hide();
//			if ((detail instanceof RepertorioDetailEntrata || detail instanceof RepertorioDetailEntrata) && showRispondiButton(record)){
//				rispondiButton.show();
//			}else{
//				rispondiButton.hide();
//			}
		}
		altreOpButton.hide();
		verificaRegistrazioneButton.hide();
		if ((detail instanceof ProtocollazioneDetail) && ((ProtocollazioneDetail) detail).showDownloadDocZipButton(record)) {
			downloadDocZipButton.show();
			frecciaDownloadZipButton.show();
		} else {
			downloadDocZipButton.hide();
			frecciaDownloadZipButton.hide();
		}
		if (detail instanceof ArchivioDetail) {
			if (record.getAttributeAsBoolean("abilChiudiFascicolo")) {
				chiudiFascicoloButton.show();
			} else {
				chiudiFascicoloButton.hide();
			}
			if (record.getAttributeAsBoolean("abilRiapriFascicolo")) {
				riapriFascicoloButton.show();
			} else {
				riapriFascicoloButton.hide();
			}
			if (record.getAttributeAsBoolean("flgFascTitolario")) {
				if (record.getAttributeAsBoolean("abilVersaInArchivioStoricoFascicolo")) {
					versaInArchivioStoricoFascicoloButton.show();
				} else {
					versaInArchivioStoricoFascicoloButton.hide();
				}
			} else {
				versaInArchivioStoricoFascicoloButton.hide();
			}
		} else {
			chiudiFascicoloButton.hide();
			versaInArchivioStoricoFascicoloButton.hide();
			riapriFascicoloButton.hide();
		}
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		boolean isActiveModal = AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal");
		
		if (AurigaLayout.getIsAttivaAccessibilita() && isActiveModal) {
			this.mode = "edit";
			this.fromViewMode = fromViewMode;
			detail.editMode();
			if (fullScreenDetail && !showOnlyDetail) {
				backToListButton.show();
			} else {
				backToListButton.hide();
			}
			editButton.hide();
			saveButton.show();
			reloadDetailButton.show();
			undoButton.show();
			deleteButton.hide();
			altreOpButton.hide();
			lookupButton.hide();
		} else {
		super.editMode(fromViewMode);
		}
		
		profilaModelloDocButton.hide();
		registraPrelievoButton.hide();
		modificaPrelievoButton.hide();
		eliminaPrelievoButton.hide();
		registraRestituzionePrelievoButton.hide();
		
		if (detail instanceof ArchivioDetail) {
			((ArchivioDetail) detail).editMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof FolderCustomDetail) {
			((FolderCustomDetail) detail).editMode();
			altreOpButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).editMode();
			saveButton.setTitle(I18NUtil.getMessages().saveButton_prompt());
			altreOpButton.hide();
			deleteButton.hide();
			if (detail instanceof ProtocollazioneDetailBozze) {
				verificaRegistrazioneButton.hide();
				salvaComeModelloButton.show();
				setSaveButtonTitle(I18NUtil.getMessages().bozze_creanuovabozza_salva());
			} else {
				verificaRegistrazioneButton.show();
				if (((ProtocollazioneDetail) detail).showModelliSelectItem()) {
					salvaComeModelloButton.show();
				} else {
					salvaComeModelloButton.hide();
				}
			}
			if (((ProtocollazioneDetail) detail).isProtocollazioneBozza()) {
				reloadDetailButton.hide();
				undoButton.hide();
				salvaComeModelloButton.hide();
			}
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).editMode();
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else if (detail instanceof ModelliDocDetail) {
			((ModelliDocDetail) detail).editMode();
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		} else {
			altreOpButton.hide();
			deleteButton.hide();
			verificaRegistrazioneButton.hide();
			salvaComeModelloButton.hide();
		}
		stampaEtichettaButton.hide();
		frecciaStampaEtichettaButton.hide();
		assegnazioneButton.hide();
		frecciaAssegnazioneButton.hide();
		rispondiButton.hide();
		condivisioneButton.hide();
		frecciaCondivisioneButton.hide();
		smistaButton.hide();
		smistaCCButton.hide();
		invioAlProtocolloButton.hide();
		frecciaInvioAlProtocolloButton.hide();
		assegnaCondividiButton.hide();
		frecciaAssegnaCondividiButton.hide();
		stampaButton.hide();
		stampaRicevutaButton.hide();
		nuovaProtComeCopiaButton.hide();
		presaInCaricoButton.hide();
		restituisciButton.hide();
		segnaComeVisionatoButton.hide();
		segnaComeArchiviatoButton.hide();
		classificazioneFascicolazioneButton.hide();
		modificaButton.hide();
		frecciaModificaButton.hide();
		regAccessoCivicoButton.hide();
		modificaDatiRegButton.hide();
		// permessiUdButton.hide();
		invioPECButton.hide();
		invioPEOButton.hide();
		inviaRaccomandataButton.hide();
		inviaPostaPrioritariaButton.hide();
		chiudiFascicoloButton.hide();
		riapriFascicoloButton.hide();
		versaInArchivioStoricoFascicoloButton.hide();
		avviaIterButton.hide();
		apposizioneFirmaButton.hide();
		rifiutoApposizioneFirmaButton.hide();
		apposizioneFirmaProtocollazioneButton.hide();
		revocaAttoButton.hide();
		protocollazioneEntrataButton.hide();
		protocollazioneUscitaButton.hide();
		protocollazioneInternaButton.hide();
		vistoButtonApposizione.hide();
		vistoButtonRifiuto.hide();
		pubblicazioneTraspAmmButton.hide();	
	}
	
	private String getTitleUnicaStampaAbilitata(Record record) {
		String title = null;
		int nroStampeAbilitate = 0;
		if (record.getAttributeAsBoolean("abilStampaCopertina")) {				
			nroStampeAbilitate++;			
			title = "Stampa copertina" ;
		}
		if (record.getAttributeAsBoolean("abilStampaSegnatura")) {
			nroStampeAbilitate++;	
			title = "Stampa segnatura";
		}
		if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {				
			nroStampeAbilitate++;
			title = "Stampa lista contenuti";
		}
		if(nroStampeAbilitate > 1) {
			return null;
		}
		return title;
	}
	
	private String getNomeModelloUnicaStampaAbilitata(Record record) {
		String nomeModello = null;
		int nroStampeAbilitate = 0;
		if (record.getAttributeAsBoolean("abilStampaCopertina")) {				
			nroStampeAbilitate++;			
			nomeModello = "COPERTINA_FASC" ;
		}
		if (record.getAttributeAsBoolean("abilStampaSegnatura")) {
			nroStampeAbilitate++;	
			nomeModello = "SEGNATURA_FASC";
		}
		if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {				
			nroStampeAbilitate++;
			nomeModello = "LISTA_CONTENUTI_FASC";
		}
		if(nroStampeAbilitate > 1) {
			return null;
		}
		return nomeModello;
	}

	@Override
	public List<ToolStripButton> getDetailButtons() {
		
		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();

		if(stampaEtichettaButton == null) {		
			stampaEtichettaButton = new DetailToolStripButton(
					AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_CARTACEO") ? "Timbra" 
					: I18NUtil.getMessages().protocollazione_detail_stampaEtichettaButton_prompt(),
					"protocollazione/barcode.png");
			stampaEtichettaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickStampaEtichetta();
				}
			});
		}
		
		if(stampaRicevutaButton == null) {
			stampaRicevutaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_stampaRicevutaButton_prompt(),
					"stampa.png");
			stampaRicevutaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickStampaRicevuta();
				}
			});
		}
		
		if(smistaButton == null) {
			smistaButton = new DetailToolStripButton("Smista","archivio/smistamento.png");
			smistaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();																		
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmista = new Record();
						recordAzioneRapidaSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmista);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmista = new Record();
						recordAzioneRapidaSmista.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmista);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickSmista(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(smistaCCButton == null) {
			smistaCCButton = new DetailToolStripButton("Smista","archivio/smistamentoCC.png");
			smistaCCButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();																		
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmistaCC = new Record();
						recordAzioneRapidaSmistaCC.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmistaCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaSmistaCC = new Record();
						recordAzioneRapidaSmistaCC.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaSmistaCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickSmistaCC(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(assegnaCondividiButton == null) {
			assegnaCondividiButton = new DetailToolStripButton("Assegnazioni/invii conosc.","archivio/assegna.png");
			assegnaCondividiButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();																		
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickAssegnaCondividi(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(frecciaAssegnaCondividiButton == null) {			
			frecciaAssegnaCondividiButton = new FrecciaDetailToolStripButton();
			frecciaAssegnaCondividiButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);			
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);									
					} 					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickAssegnaCondividi(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(assegnazioneButton == null) {
			assegnazioneButton = new DetailToolStripButton("Assegna","archivio/assegna.png");
			assegnazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof ProtocollazioneDetail){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
					} 				
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickAssegnazione(destinatariPreferiti); 											
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(invioAlProtocolloButton == null) {
			invioAlProtocolloButton = new DetailToolStripButton("Invio al protocollo","archivio/invio_al_protocollo.png");
			invioAlProtocolloButton.addClickHandler(new ClickHandler()  {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickInvioAlProtocolloMenu(); 											
					}
				}
			});
		}
		
		if(frecciaInvioAlProtocolloButton == null) {					
			frecciaInvioAlProtocolloButton = new FrecciaDetailToolStripButton();
			frecciaInvioAlProtocolloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickFrecciaInvioAlProtocolloMenu();
				}
			});
		}
		
		if(rispondiButton == null) {
			rispondiButton = new DetailToolStripButton("Rispondi", "archivio/rispondi.png");
			rispondiButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).manageOnClickRispondi();
					}	
				}
			});
		}
		
		if(condivisioneButton == null) {
			condivisioneButton = new DetailToolStripButton("Invia per conoscenza","archivio/condividi.png");
			condivisioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof DocumentDetail){ // che è come fare ((detail instanceof ProtocollazioneDetail) || (detail instanceof RichiestaAccessoAttiDetail)) 
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
					}
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof DocumentDetail) {
											((DocumentDetail) detail).clickCondivisione(destinatariPreferiti);
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										}
									}
								}
							}, new DSRequest());
				}
			});
		}
		
		if(frecciaStampaEtichettaButton == null) {					
			frecciaStampaEtichettaButton = new FrecciaDetailToolStripButton();
			frecciaStampaEtichettaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					((ProtocollazioneDetail) detail).clickFrecciaStampaEtichetta();
				}
			});
		}
		
		if(frecciaAssegnazioneButton == null) {			
			frecciaAssegnazioneButton = new FrecciaDetailToolStripButton();
			frecciaAssegnazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof ProtocollazioneDetail){ 
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaAssegna = new Record();
						recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaAssegna);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof ProtocollazioneDetail) {
											((ProtocollazioneDetail) detail).clickFrecciaAssegnazione(destinatariPreferiti); 																					
										}
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										} 										
									}
								}
							}, new DSRequest());
					
				}
			});
		}
		
		if(frecciaCondivisioneButton == null) {			
			frecciaCondivisioneButton = new FrecciaDetailToolStripButton();
			frecciaCondivisioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					Record recordDestPref = new Record();						
					if(detail instanceof DocumentDetail){ // che è come fare ((detail instanceof ProtocollazioneDetail) || (detail instanceof RichiestaAccessoAttiDetail))
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
						recordDestPref.setAttribute("idUd", detailRecord.getAttribute("idUd"));
					} 
					else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)){
						RecordList listaAzioniRapide = new RecordList();
						Record recordAzioneRapidaInvioCC = new Record();
						recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
						listaAzioniRapide.add(recordAzioneRapidaInvioCC);
						recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);	
					} 
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
							new DSCallback() {

								@Override
								public void execute(DSResponse responseDestPref, Object rawDataDestPref,
										DSRequest requestDestPref) {
									if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record destinatariPreferiti = responseDestPref.getData()[0];
										if (detail instanceof DocumentDetail) {
											((DocumentDetail) detail).clickFrecciaCondivisione(destinatariPreferiti);
										} 
										else if (detail instanceof ArchivioDetail){
											//TODO
										} 
										else if (detail instanceof FolderCustomDetail){
											//TODO
										}
									}
								}
							}, new DSRequest());
				}
			});
		}
		
		if(stampaButton == null) {
			stampaButton = new DetailToolStripButton("Stampa", "stampa.png");
			stampaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickStampaCopertina();
					} else if(detail instanceof ArchivioDetail) {
						Record record = new Record(detail.getValuesManager().getValues());
						String nomeModelloUnicaStampaAbilitata = getNomeModelloUnicaStampaAbilitata(record);
						if(nomeModelloUnicaStampaAbilitata != null) {
							((ArchivioDetail) detail).clickStampa(nomeModelloUnicaStampaAbilitata);
						} else {
							Menu stampaMenu = new Menu();
							if (record.getAttributeAsBoolean("abilStampaCopertina")) {										
								MenuItem stampaCopertinaMenuItem = new MenuItem("Copertina");
								stampaCopertinaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										((ArchivioDetail) detail).clickStampa("COPERTINA_FASC");
									}
								});
								stampaMenu.addItem(stampaCopertinaMenuItem);
							}
							if (record.getAttributeAsBoolean("abilStampaSegnatura")) {																	
								MenuItem stampaSegnaturaMenuItem = new MenuItem("Segnatura");
								stampaSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										((ArchivioDetail) detail).clickStampa("SEGNATURA_FASC");
									}
								});						
								stampaMenu.addItem(stampaSegnaturaMenuItem);
							}
							if (record.getAttributeAsBoolean("abilStampaListaContenuti")) {																	
								MenuItem stampaListaContenutiMenuItem = new MenuItem("Lista contenuti");
								stampaListaContenutiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										((ArchivioDetail) detail).clickStampa("LISTA_CONTENUTI_FASC");
									}
								});						
								stampaMenu.addItem(stampaListaContenutiMenuItem);
							}
							stampaMenu.showContextMenu();	
						}				
					}
				}
			});			
		}
		
		if(nuovaProtComeCopiaButton == null) {	
			nuovaProtComeCopiaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_nuovaProtComeCopiaButton_prompt(),
					"protocollazione/nuovaProtComeCopia.png");
			nuovaProtComeCopiaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickNuovaProtComeCopia(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								newComeCopiaMode();
							}
						});
					}
				}
			});
		}
		
		if(profilaModelloDocButton == null) {	
			profilaModelloDocButton = new DetailToolStripButton("Profila", "lookup/attributiadd.png");
			profilaModelloDocButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof ModelliDocDetail) {
						((ModelliDocDetail) detail).manageProfilaButtonClick();
					}
				}
			});
		}
		
		if(presaInCaricoButton == null) {	
			presaInCaricoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_presaIncarico_title(),
					"archivio/prendiInCarico.png");
			presaInCaricoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickPresaInCarico();
					}
				}
			});
		}
		
		if(restituisciButton == null) {	
			restituisciButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_restituisci_title(),
					"archivio/restituzione.png");
			restituisciButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickRestituisci();
					} else if (detail instanceof ArchivioDetail) {
						((ArchivioDetail) detail).clickRestituisci();
					}
				}
			});
		}
		
		if(segnaComeVisionatoButton == null) {	
			segnaComeVisionatoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_segnaComeVisionato_title(),
					"postaElettronica/flgRicevutaLettura.png");
			segnaComeVisionatoButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickSegnaComeVisionato();
					} else if (detail instanceof ArchivioDetail) {
						((ArchivioDetail) detail).clickSegnaComeVisionato();
					}
				}
			});
		}
		
		if(segnaComeArchiviatoButton == null) {	
			segnaComeArchiviatoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_segnaComeArchiviato_title(),
					"archivio/archiviazione.png");
			segnaComeArchiviatoButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickSegnaComeArchiviato();
					} else if (detail instanceof ArchivioDetail) {
						((ArchivioDetail) detail).clickSegnaComeArchiviato();
					}
				}
			});
		}
		
		if(classificazioneFascicolazioneButton == null) {	
			classificazioneFascicolazioneButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_fascicolazione_title(), 
					"archivio/fascicola.png");
			classificazioneFascicolazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickClassificazioneFascicolazione();					 
					}
				}
			});
		}
		
		if(modificaButton == null) {	
			modificaButton = new DetailToolStripButton("Modifica", "buttons/modify.png");
			modificaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode(true);
					Record record = new Record(detail.getValuesManager().getValues());
					if (detail instanceof ProtocollazioneDetail) {
						if (!(detail instanceof ProtocollazioneDetailBozze)) {
							if (record.getAttributeAsBoolean("abilModificaDati")) {
								((ProtocollazioneDetail) detail).modificaDatiMode(record.getAttributeAsBoolean("abilAggiuntaFile"));
							} else if (record.getAttributeAsBoolean("abilAggiuntaFile")) {
								((ProtocollazioneDetail) detail).aggiuntaFileMode();
							} else if (record.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
								((ProtocollazioneDetail) detail).modificaDatiExtraIterMode();
							} else {
								viewMode();
							}
						}
					}
				}
			});
		}
		
		if(frecciaModificaButton == null) {			
			frecciaModificaButton = new FrecciaDetailToolStripButton();
			frecciaModificaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					clickFrecciaModifica();
				}
			});
		}
		
		if(regAccessoCivicoButton == null) {	
			regAccessoCivicoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_regAccessoCivicoButton_title(), "buttons/modify.png");
			regAccessoCivicoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode(true);
					Record record = new Record(detail.getValuesManager().getValues());
					if (detail instanceof ProtocollazioneDetail) {
						if (!(detail instanceof ProtocollazioneDetailBozze)) {
							if (record.getAttributeAsBoolean("abilRegAccessoCivico")) {
								((ProtocollazioneDetail) detail).modificaRegAccessoCivicoMode();
								setSaveButtonTitle("Registra");
							} else {
								viewMode();
							}
						}
					}
				}
			});
		}
		
		if(modificaDatiRegButton == null) {	
			modificaDatiRegButton = new DetailToolStripButton("Variazione dati registrazione", "buttons/modificaDatiReg.png");
			modificaDatiRegButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode(true);
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).modificaDatiRegMode();
					}
				}
			});
		}
				
		if(revocaAttoButton == null) {	
			revocaAttoButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_revoca_atto_button(),
					"buttons/revoca_atto.png");
			revocaAttoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickRevocaAtto(instance);
					}
				}
			});
		}
		
		if(protocollazioneEntrataButton == null) {	
			protocollazioneEntrataButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_entrata_button(),
					"menu/protocollazione_entrata.png");
			protocollazioneEntrataButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickProtocollazioneEntrata(instance);
					}
				}
			});
		}
		
		if(protocollazioneUscitaButton == null) {	
			protocollazioneUscitaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_uscita_button(),
					"menu/protocollazione_uscita.png");
			protocollazioneUscitaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickProtocollazioneUscita(instance);
					}
				}
			});
		}
		
		if(protocollazioneInternaButton == null) {	
			protocollazioneInternaButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_protocollazione_interna_button(), 
					"menu/protocollazione_interna.png");
			protocollazioneInternaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickProtocollazioneInterna(instance);
					}
				}
			});
		}
		
		if(invioPECButton == null) {	
			invioPECButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailinterop_title(),
					"anagrafiche/soggetti/flgEmailPecPeo/INTEROP.png");
			invioPECButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Record record = new Record(detail.getValuesManager().getValues());
					
					final Boolean flgInvioPECMulti = record.getAttributeAsBoolean("flgInvioPECMulti") != null &&
							record.getAttributeAsBoolean("flgInvioPECMulti") ? true : false;	
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
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
			});
		}
		
		if(invioPEOButton == null) {	
			invioPEOButton = new DetailToolStripButton(I18NUtil.getMessages().invioudmail_detail_mailpeo_title(), "anagrafiche/soggetti/flgEmailPecPeo/PEO.png");
			invioPEOButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Record record = new Record(detail.getValuesManager().getValues());
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
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
			});
		}
		
		if (inviaRaccomandataButton == null) {
			inviaRaccomandataButton = new DetailToolStripButton("Invio raccomandata", "postaElettronica/inoltro.png");
			inviaRaccomandataButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					Record record = new Record(detail.getValuesManager().getValues());

					if (detail instanceof ProtocollazioneDetail ) {
						((ProtocollazioneDetail) detail).setModalitaInvio("raccomandata");
						((ProtocollazioneDetail) detail).clickPostalizza(record);
					}
				}
			});
		}
		
		if (inviaPostaPrioritariaButton == null) {
			inviaPostaPrioritariaButton = new DetailToolStripButton("Invio posta prioritaria", "postaElettronica/inoltro.png");
			inviaPostaPrioritariaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					Record record = new Record(detail.getValuesManager().getValues());

					if (detail instanceof ProtocollazioneDetail ) {
						((ProtocollazioneDetail) detail).setModalitaInvio("posta prioritaria");
						((ProtocollazioneDetail) detail).clickPostalizza(record);
					}
				}
			});
		}	
		
		if(verificaRegistrazioneButton == null) {	
			verificaRegistrazioneButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_verificaRegistrazioneButton_prompt(),
					"buttons/verificaDati.png");
			verificaRegistrazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Record lInputRecord = new Record();
					lInputRecord.setAttribute("datiReg", new Record(detail.getValuesManager().getValues()));
					final GWTRestDataSource lVerificaRegDuplicataDataSource = new GWTRestDataSource("VerificaRegDuplicataDataSource");
					lVerificaRegDuplicataDataSource.addData(lInputRecord, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record lOutputRecord = response.getData()[0];
								if ("N".equals(lOutputRecord.getAttribute("statoDuplicazione"))) {
									Layout.addMessage(new MessageBean("Non risultano possibili registrazioni duplicate nel periodo considerato", "",
											MessageType.INFO));
								} else {
									if (lOutputRecord.getAttribute("warningMessage") != null && !"".equals(lOutputRecord.getAttribute("warningMessage"))) {
										Layout.addMessage(new MessageBean(lOutputRecord.getAttribute("warningMessage"), "", MessageType.WARNING));
									}
									GWTRestDataSource lRegistrazioniDuplicataDataSource = new GWTRestDataSource("RegistrazioniDuplicataDataSource", "idUd",
											FieldType.TEXT);
									lRegistrazioniDuplicataDataSource.addParam("datiRegXml", lOutputRecord.getAttribute("datiRegXml"));
									VerificaRegDuplicataWindow lVerificaRegDuplicataWindow = new VerificaRegDuplicataWindow(lRegistrazioniDuplicataDataSource);
									lVerificaRegDuplicataWindow.show();
								}
							}
						}
					});
				}
			});
		}
		
		if(salvaComeModelloButton == null) {	
			salvaComeModelloButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(),
					"buttons/template_save.png");
			salvaComeModelloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickSalvaComeModello();
					}
				}
			});
		}
		
		if(registraPrelievoButton == null) {	
			registraPrelievoButton = new DetailToolStripButton("Registra prelievo", "richiesteAccessoAtti/azioni/registraPrelievo.png");
			registraPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickRegistraPrelievoButton();
					}
				}
			});
		}
		
		if(modificaPrelievoButton == null) {	
			modificaPrelievoButton = new DetailToolStripButton("Modifica prelievo", "richiesteAccessoAtti/azioni/modificaPrelievo.png");
			modificaPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickModificaPrelievoButton();
					}
				}
			});
		}
		
		if(eliminaPrelievoButton == null) {	
			eliminaPrelievoButton = new DetailToolStripButton("Elimina prelievo", "richiesteAccessoAtti/azioni/eliminaPrelievo.png");
			eliminaPrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickEliminaPrelievoButton();
					}
				}
			});
		}
		
		if(registraRestituzionePrelievoButton == null) {	
			registraRestituzionePrelievoButton = new DetailToolStripButton("Registra restituzione prelievo", "richiesteAccessoAtti/azioni/registraRestituzione.png");
			registraRestituzionePrelievoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof PraticaPregressaDetail) {
						((PraticaPregressaDetail) detail).onClickRegistraRestituzionePrelievoButton();
					}
				}
			});
		}
		
		if(downloadDocZipButton == null) {	
			downloadDocZipButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_list_downloadDocsZip(), "buttons/download_zip.png");
			downloadDocZipButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if(detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaOriginali");
					}
				}
			});
		}
		
		if(frecciaDownloadZipButton == null) {
			frecciaDownloadZipButton = new FrecciaDetailToolStripButton();
			frecciaDownloadZipButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					clickFrecciaDownload();
				}
			});
		}
		
		if(chiudiFascicoloButton == null) {	
			chiudiFascicoloButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_chiudiFascicoloButton_prompt(), "archivio/save_key.png");
			chiudiFascicoloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Sei sicuro di voler procedere alla chiusura del fascicolo ?", new BooleanCallback() {
	
						@Override
						public void execute(Boolean value) {
							if (value) {
								Record recordSelected = new Record(detail.getValuesManager().getValues());
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(recordSelected);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
								lGwtRestDataSource.executecustom("chiudiFascicolo", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Operazione effettuata con successo",
												"Il record selezionato e' andato in errore!", "Il record selezionato e' andato in errore!", null);
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												viewMode();
											}
										});
									}
								});
							}
						}
					});
				}
			});
		}
		
		if(riapriFascicoloButton == null) {	
			riapriFascicoloButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_riapriFascicoloButton_prompt(), "archivio/annullaArchiviazione.png");
			riapriFascicoloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Sei sicuro di voler procedere alla riapertura del fascicolo ?", new BooleanCallback() {
	
						@Override
						public void execute(Boolean value) {
							if (value) {
								Record recordSelected = new Record(detail.getValuesManager().getValues());
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(recordSelected);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
								lGwtRestDataSource.executecustom("riapriFascicolo", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Operazione effettuata con successo",
												"Il record selezionato e' andato in errore!", "Il record selezionato e' andato in errore!", null);
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												viewMode();
											}
										});
									}
								});
							}
						}
					});
				}
			});
		}
		
		if(versaInArchivioStoricoFascicoloButton == null) {	
			versaInArchivioStoricoFascicoloButton = new DetailToolStripButton(I18NUtil.getMessages().archivio_versaInArchivioStoricoFascicoloButton_prompt(),
					"archivio/cassaforte.png");
			versaInArchivioStoricoFascicoloButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Sei sicuro di voler procedere al versamento del fascicolo nell'archivio storico ?", new BooleanCallback() {
	
						@Override
						public void execute(Boolean value) {
							if (value) {
								Record recordSelected = new Record(detail.getValuesManager().getValues());
								final RecordList listaUdFolder = new RecordList();
								listaUdFolder.add(recordSelected);
								final Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
								lGwtRestDataSource.executecustom("versaInArchivioStoricoFascicolo", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Operazione effettuata con successo",
												"Il record selezionato e' andato in errore!", "Il record selezionato e' andato in errore!", null);
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												
												viewMode();
											}
										});
									}
								});
							}
						}
					});
				}
			});
		}
		
		if(avviaIterButton == null) {	
			avviaIterButton = new DetailToolStripButton("Avvia iter guidato", "buttons/gear.png");
			avviaIterButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					if (detail instanceof ProtocollazioneDetail) {
						final String idUd = detailRecord.getAttribute("idUd");
						if(detailRecord.getAttribute("flgTipoProv") != null && "E".equalsIgnoreCase(detailRecord.getAttribute("flgTipoProv"))) {
							Record lRecordAvvio = new Record();
							lRecordAvvio.setAttribute("idTipoProc", detailRecord.getAttribute("idProcessTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("nomeTipoProc", detailRecord.getAttribute("nomeProcessTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("idTipoFlussoActiviti", detailRecord.getAttribute("nomeTipoFlussoIterWFRisposta"));
							lRecordAvvio.setAttribute("idTipoDocProposta", detailRecord.getAttribute("idDocTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("nomeTipoDocProposta", detailRecord.getAttribute("nomeDocTypeIterWFRisposta"));
							lRecordAvvio.setAttribute("categoriaProposta", detailRecord.getAttribute("codCategoriaNumIniIterWFRisposta"));
							lRecordAvvio.setAttribute("siglaProposta", detailRecord.getAttribute("siglaNumIniIterWFRisposta"));			
							lRecordAvvio.setAttribute("idUdRichiesta", idUd);	
							AurigaLayout.avviaPratica(lRecordAvvio, new BooleanCallback() {

								@Override
								public void execute(Boolean value) {
									reload(new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {													
											viewMode();
											detail.setSaved(true); // per ricaricare la lista quando chiudo il dettaglio											
										}
									});
								}								
							});						
						} else {
							apriSceltaTipoProcGenPopup("D", detailRecord.getAttribute("tipoDocumento"), new ServiceCallback<Record>() {
		
								@Override
								public void execute(Record record) {
									
									record.setAttribute("idUd", idUd);
									Layout.showWaitPopup("Avvio procedimento in corso...");
									GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AvvioProcedimentoGenericoDatasource");
									lGwtRestService.call(record, new ServiceCallback<Record>() {
		
										@Override
										public void execute(Record object) {
											Layout.hideWaitPopup();
											Layout.addMessage(new MessageBean("Procedimento avviato con successo", "", MessageType.INFO));
											reload(new DSCallback() {
		
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {													
													viewMode();
													detail.setSaved(true); // per ricaricare la lista quando chiudo il dettaglio
													apriDettProcedimentoFromUd(idUd);
												}
											});
										}
									});
								}
							});
						}
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)) {
						final String idFolder = detailRecord.getAttribute("idUdFolder");
						apriSceltaTipoProcGenPopup("F", detailRecord.getAttribute("idFolderType"), new ServiceCallback<Record>() {
	
							@Override
							public void execute(Record record) {
								
								record.setAttribute("idFolder", idFolder);
								Layout.showWaitPopup("Avvio procedimento in corso...");
								GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AvvioProcedimentoGenericoDatasource");
								lGwtRestService.call(record, new ServiceCallback<Record>() {
	
									@Override
									public void execute(Record object) {
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean("Procedimento avviato con successo", "", MessageType.INFO));
										reload(new DSCallback() {
	
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {												
												viewMode();
												detail.setSaved(true); // per ricaricare la lista quando chiudo il dettaglio
												apriDettProcedimentoFromFolder(idFolder);
											}
										});
									}
								});
							}
						});
					}
				}
			});
		}
		
		if(osservazioniNotificheButton == null) {	
			osservazioniNotificheButton = new DetailToolStripButton("Osservazioni e notifiche", "osservazioni_notifiche.png");
			osservazioniNotificheButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					String title = null;
					if (detail instanceof ProtocollazioneDetail || detail instanceof RichiestaAccessoAttiDetail) {
						title = "Documento "+ getEstremiUdFromLayout(detailRecord) + " - Osservazioni e notifiche";
						new OsservazioniNotificheWindow(detailRecord.getAttribute("idUd"),"U", title);
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)) {
						title = "Fascicolo "+ getEstremiFolderFromLayout(detailRecord) + " - Osservazioni e notifiche";
						new OsservazioniNotificheWindow(detailRecord.getAttribute("idUdFolder"),"F", title);
					}					
				}
			});
		}
		
		if(apposizioneFirmaButton == null) {	
			apposizioneFirmaButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneFirma_button_title(),"file/mini_sign.png");
			apposizioneFirmaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageFirmaComplessiva(true, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record response) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
								
								//Aggiorno la grafica relativa
//								doSearchAfterFirmaConEsitoAzione();
							}
						});
					}
				}
			});
		}
		
		if(rifiutoApposizioneFirmaButton == null) {	
			rifiutoApposizioneFirmaButton = new DetailToolStripButton(I18NUtil.getMessages().rifiutoApposizioneFirma_button_title(),"file/rifiuto_apposizione.png");
			rifiutoApposizioneFirmaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageFirmaComplessiva(false, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record response) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
								
								//Aggiorno la grafica relativa
//								doSearchAfterFirmaConEsitoAzione();
							}
						});
					}
				}
			});
		}
		
		if(apposizioneFirmaProtocollazioneButton == null) {	
			apposizioneFirmaProtocollazioneButton = new DetailToolStripButton(I18NUtil.getMessages().apposizioneFirmaProtocollazione_button_title(),"buttons/firmaEProtocolla.png");
			apposizioneFirmaProtocollazioneButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageProtocollaTimbraEFirma(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record response) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
								
								//Aggiorno la grafica relativa
//								doSearchAfterFirmaConEsitoAzione();
							}
						});
					}
				}
			});
		}
		
		if(vistoButtonApposizione == null) {	
			vistoButtonApposizione = new DetailToolStripButton(I18NUtil.getMessages().apposizioneVisto_button_title(),"ok.png");
			vistoButtonApposizione.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageVistoDocumento(true, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record response) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
								
//								RecordList listaRecordUd = response.getAttributeAsRecordList("listaRecordUd");
								
								//Aggiorno la grafica relativa
//								doSearchAfterApposizioneVisto(listaRecordUd);
							}
						});
					} 
				}
			});
		} 
		
		if(vistoButtonRifiuto == null) {	
			vistoButtonRifiuto = new DetailToolStripButton(I18NUtil.getMessages().rifiutoApposizioneVisto_button_title(),"ko.png");
			vistoButtonRifiuto.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail)detail).manageVistoDocumento(false, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record response) {
								
								/*
								 * Imposto a true il flag in modo da eseguire l'aggiornamento della lista 
								 * una volta che viene chiuso il dettaglio
								 */
								((ProtocollazioneDetail)detail).setSaved(true);
								
//								RecordList listaRecordUd = response.getAttributeAsRecordList("listaRecordUd");
								
								//Aggiorno la grafica relativa
//								doSearchAfterApposizioneVisto(listaRecordUd);
							}
						});
					} 
				}
			});
		} 
				
		if(pubblicazioneTraspAmmButton == null) {	
			pubblicazioneTraspAmmButton = new DetailToolStripButton("Pubbl. Trasp. Amm.", "buttons/richiesta_pubblicazione.png");
			pubblicazioneTraspAmmButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					final Record detailRecord = new Record(detail.getValuesManager().getValues());
					new PubblicazioneTraspAmmPopup(detailRecord.getAttribute("idUd"));
				}
			});
		}
		
		detailButtons.add(apposizioneFirmaButton);
		detailButtons.add(apposizioneFirmaProtocollazioneButton);
		detailButtons.add(rifiutoApposizioneFirmaButton);
		detailButtons.add(vistoButtonApposizione);
		detailButtons.add(vistoButtonRifiuto);
		detailButtons.add(stampaEtichettaButton);
		detailButtons.add(frecciaStampaEtichettaButton);	
		detailButtons.add(stampaRicevutaButton);
		detailButtons.add(stampaButton);	
		detailButtons.add(nuovaProtComeCopiaButton);
		detailButtons.add(profilaModelloDocButton);
		detailButtons.add(saveButton);
		detailButtons.add(editButton);
		detailButtons.add(presaInCaricoButton);
		detailButtons.add(restituisciButton);
		detailButtons.add(segnaComeVisionatoButton);
		detailButtons.add(segnaComeArchiviatoButton);
		detailButtons.add(classificazioneFascicolazioneButton);
		detailButtons.add(modificaButton);
		detailButtons.add(frecciaModificaButton);
		detailButtons.add(regAccessoCivicoButton);
		detailButtons.add(modificaDatiRegButton);
		detailButtons.add(revocaAttoButton);
		detailButtons.add(protocollazioneEntrataButton);
		detailButtons.add(protocollazioneUscitaButton);
		detailButtons.add(protocollazioneInternaButton);
		detailButtons.add(invioPECButton);
		detailButtons.add(invioPEOButton);
		detailButtons.add(inviaRaccomandataButton);
		detailButtons.add(inviaPostaPrioritariaButton);
		detailButtons.add(verificaRegistrazioneButton);
		detailButtons.add(reloadDetailButton);
		detailButtons.add(undoButton);
		detailButtons.add(deleteButton);
		detailButtons.add(smistaButton);
		detailButtons.add(smistaCCButton);
		detailButtons.add(invioAlProtocolloButton);
		detailButtons.add(frecciaInvioAlProtocolloButton);
		detailButtons.add(assegnaCondividiButton);
		detailButtons.add(frecciaAssegnaCondividiButton);
		detailButtons.add(assegnazioneButton);
		detailButtons.add(frecciaAssegnazioneButton);
		detailButtons.add(rispondiButton);
		detailButtons.add(condivisioneButton);
		detailButtons.add(frecciaCondivisioneButton);
		detailButtons.add(osservazioniNotificheButton);
		detailButtons.add(registraPrelievoButton);
		detailButtons.add(modificaPrelievoButton);
		detailButtons.add(eliminaPrelievoButton);
		detailButtons.add(registraRestituzionePrelievoButton);
		detailButtons.add(salvaComeModelloButton);
		detailButtons.add(altreOpButton);
		detailButtons.add(lookupButton);
		detailButtons.add(downloadDocZipButton);
		detailButtons.add(frecciaDownloadZipButton);
		detailButtons.add(chiudiFascicoloButton);
		detailButtons.add(riapriFascicoloButton);
		detailButtons.add(versaInArchivioStoricoFascicoloButton);
		detailButtons.add(avviaIterButton);
		detailButtons.add(pubblicazioneTraspAmmButton);

		if (detail instanceof RichiestaAccessoAttiDetail) {
			detailButtons.addAll(((RichiestaAccessoAttiDetail)detail).getAzioniButtons());
		}
		
		/*
		if (detail instanceof ModelliDocDetail) {
			detailButtons.addAll(((ModelliDocDetail)detail).getAzioniButtons());
		}
		*/
		
		return detailButtons;
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
				((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaOriginali");
			}
		});
		downloadZipMenu.addItem(scaricaFileMenuItem);
		
		final Record detailRecord = new Record(detail.getValuesManager().getValues());
		if(showOperazioniTimbratura(detailRecord)) {

			MenuItem scaricaFileTimbratiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_segnatura(), "buttons/download_zip.png");
			scaricaFileTimbratiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaTimbratiSegnatura");
				}
			});
			downloadZipMenu.addItem(scaricaFileTimbratiSegnaturaMenuItem);
	
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA")) {
				MenuItem scaricaFileTimbratiConformeStampaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_stampa(), "buttons/download_zip.png");
				scaricaFileTimbratiConformeStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaTimbratiConformeStampa");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeStampaMenuItem);
	
				MenuItem scaricaFileTimbratiConformeDigitaleMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_digitale(),"buttons/download_zip.png");
				scaricaFileTimbratiConformeDigitaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaTimbratiConformeDigitale");
					}
				});
				downloadZipMenu.addItem(scaricaFileTimbratiConformeDigitaleMenuItem);
			}
	
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				MenuItem scaricaFileTimbratiConformeCartaceoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(),"buttons/download_zip.png");
				scaricaFileTimbratiConformeCartaceoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaTimbratiConformeCartaceo");
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
					((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaConformitaCustom");
				}
			});
			downloadZipMenu.addItem(scaricaFileConformitaCustomMenuItem);
		}

		MenuItem scaricaFileSbustatiMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_sbustati(),"buttons/download_zip.png");
		scaricaFileSbustatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((ProtocollazioneDetail) detail).clickDownloadDocZip("scaricaSbustati");
			}
		});
		downloadZipMenu.addItem(scaricaFileSbustatiMenuItem);

		downloadZipMenu.showContextMenu();
	}

	public void apriSceltaTipoProcGenPopup(final String flgDocFolder, final String tipo, final ServiceCallback<Record> callback) {
		
		GWTRestDataSource idTipoProcDS = new GWTRestDataSource("LoadComboTipoProcGenDataSource", "idTipoProc", FieldType.TEXT, true);
		idTipoProcDS.addParam("flgDocFolder", flgDocFolder);
		idTipoProcDS.addParam("tipo", tipo);
		idTipoProcDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (response.getData().length <= 1) {
					Record lRecord = new Record();
					if (response.getData().length == 1) {
						lRecord.setAttribute("idProcessType", response.getData()[0].getAttribute("idTipoProc"));
						lRecord.setAttribute("desProcessType", response.getData()[0].getAttribute("nomeTipoProc"));
						lRecord.setAttribute("flowTypeId", response.getData()[0].getAttribute("flowTypeId"));
					}
					if (callback != null) {
						callback.execute(lRecord);
					}
				} else {
					new SceltaTipoProcGenPopup(flgDocFolder, tipo, callback);
				}
			}
		});
	}

	public void getAbilitazioniFrecciaButton() {
		Record record = new Record(detail.getValuesManager().getValues());
		isAbilModificaTipologia = record.getAttributeAsBoolean("abilModificaTipologia"); 
		isAbilModificaDatiExtraIter = record.getAttributeAsBoolean("abilModificaDatiExtraIter");
		isAbilModificaOpereAtto = record.getAttributeAsBoolean("abilModificaOpereAtto");
		isAbilModificaDatiPubblAtto = record.getAttributeAsBoolean("abilModificaDatiPubblAtto");
	}
	
	private void clickFrecciaModifica() {
		
		getAbilitazioniFrecciaButton();

		Menu menu = new Menu();

		MenuItem standardItem = new MenuItem("Modifica");
		standardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				editMode(true);
				Record record = new Record(detail.getValuesManager().getValues());
				if (detail instanceof ProtocollazioneDetail) {
					if (!(detail instanceof ProtocollazioneDetailBozze)) {
						if (record.getAttributeAsBoolean("abilModificaDati")) {
							((ProtocollazioneDetail) detail).modificaDatiMode();
						} else if (record.getAttributeAsBoolean("abilAggiuntaFile")) {
							((ProtocollazioneDetail) detail).aggiuntaFileMode();
						} else if (record.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
							((ProtocollazioneDetail) detail).modificaDatiExtraIterMode();
						} else {
							viewMode();
						}
					}
				}
			}
		});
		menu.addItem(standardItem);

		if (isAbilModificaTipologia) {	
			MenuItem modificaTipologiaMenuItem = new MenuItem("Assegna/modifica tipologia");
			modificaTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					//se il record è un unità documentale
					if (detail instanceof ProtocollazioneDetail || detail instanceof RichiestaAccessoAttiDetail) {
		
						final Record recordDetail = new Record(detail.getValuesManager().getValues());
						final String tipoDocumento = recordDetail.getAttribute("tipoDocumento");
		
						new SceltaTipoDocPopup(false, tipoDocumento, null, null, null,"|*|FINALITA|*|ASS_TIPOLOGIA",
								new ServiceCallback<Record>() {
		
									@Override
									public void execute(Record lRecordTipoDoc) {
		
										String idTipoDocumento = lRecordTipoDoc.getAttribute("idTipoDocumento");
		
										    //se la tipologia selezionata è diversa da quella attuale
											if (!idTipoDocumento.equals(tipoDocumento) && idTipoDocumento!=null) {
												final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
														"ProtocolloDataSource");
												lGwtRestDataSource.extraparam.put("tipoDocumento", idTipoDocumento);
												lGwtRestDataSource.executecustom("modificaTipologiaUD", recordDetail,
														new DSCallback() {
		
															@Override
															public void execute(DSResponse response, Object rawData,
																	DSRequest request) {
																singleOperationCallback(response, recordDetail, "idUdFolder",
																		"segnatura", "Operazione effettuata con successo",
																		"Il record selezionato e' andato in errore!", null);
																reload(new DSCallback() {
		
																	@Override
																	public void execute(DSResponse response, Object rawData,
																			DSRequest request) {
		
																		detail.setSaved(true);
																		viewMode();
																	}
																});
															}
														});
											}
									}
								});
		
						//se il record è una folder
					} else if ((detail instanceof ArchivioDetail) || (detail instanceof FolderCustomDetail)) {
		
						final Record recordDetail = new Record(detail.getValuesManager().getValues());
						final String folderType = recordDetail.getAttribute("idFolderType");
		
						Record record = new Record();
						record.setAttribute("idClassifica", recordDetail.getAttribute("idClassifica"));
						record.setAttribute("idFolderApp", recordDetail.getAttribute("idFolderApp"));
						record.setAttribute("idFolderType", recordDetail.getAttribute("idFolderType"));
		
						SceltaTipoFolderPopup lSceltaTipoFolderPopup = new SceltaTipoFolderPopup(false, folderType, null, record,
								new ServiceCallback<Record>() {
		
									@Override
									public void execute(Record lRecordTipoDoc) {
		
										String idFolderType = lRecordTipoDoc.getAttribute("idFolderType");
		
										//se la tipologia selezionata è diversa da quella attuale
											if (!folderType.equals(idFolderType) && idFolderType!=null) {
												final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
														"ArchivioDatasource");
												lGwtRestDataSource.extraparam.put("idFolderType", idFolderType);
												lGwtRestDataSource.executecustom("modificaTipologia", recordDetail,
														new DSCallback() {
		
															@Override
															public void execute(DSResponse response, Object rawData,
																	DSRequest request) {
																singleOperationCallback(response, recordDetail, "idUdFolder",
																		"segnatura", "Operazione effettuata con successo",
																		"Il record selezionato e' andato in errore!", null);
																reload(new DSCallback() {
		
																	@Override
																	public void execute(DSResponse response, Object rawData,
																			DSRequest request) {
		
																		detail.setSaved(true);
																		viewMode();
																	}
																});
															}
														});
											}
									}
								});
					}
				}
			});
			menu.addItem(modificaTipologiaMenuItem);
		}
		
		if (isAbilModificaDatiExtraIter) {
			MenuItem modificaDatiExtraIterMenuItem = new MenuItem(I18NUtil.getMessages().modificaDatiExtraIter_menu_apri_title(), "pratiche/task/buttons/modifica_dati_extra_iter.png");
			modificaDatiExtraIterMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					editMode(true);
					if (detail instanceof ProtocollazioneDetail) {
						((ProtocollazioneDetail) detail).modificaDatiExtraIterMode();						
					}
				}

			});
			menu.addItem(modificaDatiExtraIterMenuItem);
		}
		
		if (isAbilModificaOpereAtto) {
			MenuItem modificaOpereMenuItem = new MenuItem(I18NUtil.getMessages().modificaOpereAtto_menu_apri_title(), "pratiche/task/buttons/modifica_opere_atto.png");
			modificaOpereMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					final Record recordDetail = new Record(detail.getValuesManager().getValues());
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
					final Record recordDetail = new Record(detail.getValuesManager().getValues());
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
	
	public void apriDettProcedimentoFromUd(String idUd) {
		Record lRecordDetail = new Record();
		lRecordDetail.setAttribute("idUd", idUd);
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.getData(lRecordDetail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordDoc = response.getData()[0];
					AurigaLayout.apriDettaglioPratica(lRecordDoc.getAttribute("idProcess"), lRecordDoc.getAttribute("estremiProcess"));
				}
			}
		});
	}

	public void apriDettProcedimentoFromFolder(String idFolder) {
		Record lRecordDetail = new Record();
		lRecordDetail.setAttribute("idUdFolder", idFolder);
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.getData(lRecordDetail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFolder = response.getData()[0];
					AurigaLayout.apriDettaglioPratica(lRecordFolder.getAttribute("idProcess"), lRecordFolder.getAttribute("estremiProcess"));
				}
			}
		});
	}

	@Override
	public String getNewDetailTitle() {
		if (detail instanceof ProtocollazioneDetail) {
			Record record = new Record(detail.getValuesManager().getValues());
			String tipoDocumento = record.getAttributeAsString("tipoDocumento");
			if (tipoDocumento != null && !"".equals(tipoDocumento)) {
				return "Nuovo documento";
			} else {
				return "Nuovo documento bozza";
			}
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			return "Nuova richiesta accesso atti";
		} else if (detail instanceof ModelliDocDetail) {
			return "Nuovo modello documentale";
		}
		return null;
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof ArchivioDetail) {
			String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
			String nroInserto = record.getAttributeAsString("nroInserto");
			if (nroInserto != null && !"".equals(nroInserto)) {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " inserto " + getTipoEstremiRecord(record);
			} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " sotto-fascicolo " + getTipoEstremiRecord(record);
			} else {
				return I18NUtil.getMessages().editDetail_titlePrefix() + " fascicolo " + getTipoEstremiRecord(record);
			}
		} else if (detail instanceof FolderCustomDetail) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " cartella " + getTipoEstremiRecord(record);
		} else if (detail instanceof ProtocollazioneDetail) {
			if (detail instanceof ProtocollazioneDetailAtti) {
				String nomeTipoDocumento = record.getAttributeAsString("nomeTipoDocumento");
				return I18NUtil.getMessages().editDetail_titlePrefix() + " documento " + nomeTipoDocumento + " " + getTipoEstremiRecord(record);
			} else {
				if(((ProtocollazioneDetail)detail).isProtocollazioneBozza()) {
					if(((ProtocollazioneDetail)detail).getFlgTipoProv() != null) {
						if("U".equals(((ProtocollazioneDetail)detail).getFlgTipoProv())) {
							return "Protocollazione in uscita documento " + getTipoEstremiRecord(record);
						} else if("I".equals(((ProtocollazioneDetail)detail).getFlgTipoProv())) {
							return "Protocollazione interna documento " + getTipoEstremiRecord(record);
						}
					}
					return "Registrazione documento " + getTipoEstremiRecord(record);
				} else {
					return I18NUtil.getMessages().editDetail_titlePrefix() + " documento " + getTipoEstremiRecord(record);
				}
			}
		} else if (detail instanceof DettaglioPostaElettronica) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " e-mail " + getTipoEstremiRecord(record);
		} else if (detail instanceof PostaElettronicaDetail) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " e-mail " + getTipoEstremiRecord(record);
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " richiesta accesso atti " + getTipoEstremiRecord(record);
		}  else if (detail instanceof ModelliDocDetail) {
			return I18NUtil.getMessages().editDetail_titlePrefix() + " modello documentale " + getTipoEstremiRecord(record);
		}
		return null;
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof ArchivioDetail) {
			String nroSottofascicolo = record.getAttributeAsString("nroSottofascicolo");
			String nroInserto = record.getAttributeAsString("nroInserto");
			if (nroInserto != null && !"".equals(nroInserto)) {
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " inserto " + getTipoEstremiRecord(record);
			} else if (nroSottofascicolo != null && !"".equals(nroSottofascicolo)) {
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " sotto-fascicolo " + getTipoEstremiRecord(record);
			} else {
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " fascicolo " + getTipoEstremiRecord(record);
			}
		} else if (detail instanceof FolderCustomDetail) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " cartella " + getTipoEstremiRecord(record);
		} else if (detail instanceof ProtocollazioneDetail) {
			if (detail instanceof ProtocollazioneDetailAtti) {
				String nomeTipoDocumento = record.getAttributeAsString("nomeTipoDocumento");
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " documento " + nomeTipoDocumento + " " + getTipoEstremiRecord(record);
			} else if (detail instanceof RegistroFattureDetail) {
				return I18NUtil.getMessages().viewDetail_titlePrefix() + " documento Registro Fatture " + getTipoEstremiRecord(record);
			}
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " documento " + getTipoEstremiRecord(record);
		} else if (detail instanceof DettaglioPostaElettronica) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " e-mail " + getTipoEstremiRecord(record);
		} else if (detail instanceof PostaElettronicaDetail) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " e-mail " + getTipoEstremiRecord(record);
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " richiesta accesso atti " + getTipoEstremiRecord(record); 
		} else if (detail instanceof ModelliDocDetail) {
			return I18NUtil.getMessages().viewDetail_titlePrefix() + " modello documentale " + getTipoEstremiRecord(record);
		}
		return null;
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		if (detail instanceof ArchivioDetail) {
			String estremi = "";
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
				if (record.getAttributeAsString("nroInserto") != null && !"".equals(record.getAttributeAsString("nroInserto"))) {
					estremi += "/" + record.getAttributeAsString("nroInserto");
				}
				estremi += " ";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
			return estremi;
		} else if (detail instanceof FolderCustomDetail) {
			String estremi = "";
			if (record.getAttributeAsString("percorsoFolderApp") != null && !"".equals(record.getAttributeAsString("percorsoFolderApp"))) {
				estremi += record.getAttributeAsString("percorsoFolderApp") + "/";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
			return estremi;
		} else if (detail instanceof PostaElettronicaDetail) {
			return super.getTipoEstremiRecord(record);
		} else if (detail instanceof DettaglioPostaElettronica) {
			return super.getTipoEstremiRecord(record);
		} else if (detail instanceof ProtocollazioneDetail) {
			return getTipoEstremiRecordProtocollazione(record);
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			String subProtocolloPregressoPrefix = record.getAttribute("subProtocolloPregresso") != null && !"".equals(record.getAttribute("subProtocolloPregresso")) ? " sub " + record.getAttribute("subProtocolloPregresso") : "";
			if ((record.getAttribute("tipoRichiedente") == null) || (!"I".equalsIgnoreCase(record.getAttribute("tipoRichiedente")))){
			    return "Prot. N° " + record.getAttribute("nroProtocolloPregresso") + subProtocolloPregressoPrefix + " anno " +  record.getAttribute("annoProtocolloPregresso");
			} else {
			    return "Rich. N° " + record.getAttribute("nroProtocolloPregresso") + subProtocolloPregressoPrefix + " anno " +  record.getAttribute("annoProtocolloPregresso");
			}
		} else if (detail instanceof ModelliDocDetail) {
			return record.getAttribute("nomeModello");
		}
		return null;
	}
	
	protected String getTipoEstremiRecordProtocollazione(Record record) {
		String estremi = "";
		if (detail instanceof ProtocollazioneDetailAtti) {
			if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
				estremi += record.getAttributeAsString("siglaProtocollo") + " ";
			}
			if (record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
				estremi += record.getAttributeAsString("nroProtocollo") + " ";
			}
			if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
				estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
			}
			if (record.getAttributeAsDate("dataProtocollo") != null) {
				estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
			}
		} else {
			if (record.getAttributeAsString("tipoProtocollo") != null && !"".equals(record.getAttributeAsString("tipoProtocollo"))) {
				if ("NI".equals(record.getAttributeAsString("tipoProtocollo"))) {
					estremi += "bozza ";
				} else if ("PP".equals(record.getAttributeAsString("tipoProtocollo"))) {
					estremi += "Prot. ";
				} else {
					estremi += record.getAttributeAsString("tipoProtocollo") + " ";
				}
			}
			if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
				estremi += record.getAttributeAsString("siglaProtocollo") + " ";
			}
			if (record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
				estremi += record.getAttributeAsString("nroProtocollo") + " ";
			}
			if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
				estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
			}
			if (record.getAttributeAsDate("dataProtocollo") != null) {
				estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
			}
		}
		return estremi;
	}

	public void onSaveButtonClick() {
		if (detail instanceof ProtocollazioneDetail) {
			((ProtocollazioneDetail) detail).clickSalvaRegistra();
		} else if (detail instanceof RichiestaAccessoAttiDetail) {
			((RichiestaAccessoAttiDetail) detail).onSaveButtonClick();
		} else if (detail instanceof PraticaPregressaDetail) {
			((PraticaPregressaDetail) detail).onSaveButtonClick();
		} else if (detail instanceof ModelliDocDetail) {
			((ModelliDocDetail) detail).onSaveButtonClick();
		} else {
			super.onSaveButtonClick();
		}
	}

	@Override
	public String getDetailPrimaryKeyFieldName() {
		if (detail instanceof ProtocollazioneDetail) {
			return "idUd";
		} else {
			return super.getDetailPrimaryKeyFieldName();
		}
	}

	// Arrivo e News >>> IdFolder = -9
	// - Ricevuti per competenza => IdNode = FD.2A | D.2A | F.2A
	// - Ricevuti per conoscenza => IdNode = FD.2CC | D.2CC | F.2CC
	// - Con notifiche => IdNode = FD.2NA | D.2NA | F.2NA

	// Bozze >>> IdNode = D.BOZZE

	// Stampe ed export >>> IdNode = D.23

	// Documenti da completare /lavorare
	// - Da assegnare >>> IdNode = D.7
	// - Da classificare >>> IdNode = D.9
	// - Da fascicolare >>> IdNode = D.10
	// - Da firmare >>> IdNode = D.11
	// - Da protocollare >>> IdNode = D.12

	// Archiviati >>> IdNode = FD.21 | D.21 | F.21

	// Recenti
	// - Registrazioni effettuate >>> IdNode = D.18
	// - Documenti lavorati >>> IdNode = D.20
	// - Scaricati e visualizzati >>> IdNode = D.25
	// - Fascicoli aperti >>> IdNode = F.19
	// - Fascicoli lavorati >>> IdNode = F.30

	// Preferiti >>> IdFolder = -999

	// Inviati >>> IdFolder = -9999 | -99991

	// Eliminati >>> IdFolder = -99999

	private boolean isPrivilegioDocFascAttivo(String idNodo, String privDoc, String privFasc) {
		return idNodo != null
				&& ((idNodo.startsWith("D.") && Layout.isPrivilegioAttivo(privDoc)) || (idNodo.startsWith("F.") && Layout.isPrivilegioAttivo(privFasc)) || (idNodo
						.startsWith("FD.") && (Layout.isPrivilegioAttivo(privDoc) || Layout.isPrivilegioAttivo(privFasc))));
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		/*******************************
		 * OPERAZIONI MASSIVE ARCHIVIO *
		 *******************************/
		if (apposizioneFirmaMultiButton == null) {
			apposizioneFirmaMultiButton = new ArchivioMultiToolStripButton("file/mini_sign.png", this, I18NUtil.getMessages().apposizioneFirma_button_title(), false) {

				@Override
				public boolean toShowForArchivio() {
					return abilApposizioneFirma;
					//return (idNode != null && idNode.startsWith("D.11")); // Documenti da completare /lavorare - Da firmare
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUd.add(list.getSelectedRecords()[i]);
					}
					((ArchivioList)list).manageFirmaMassiva(listaUd, true, AurigaLayout.getImpostazioneFirma("tipoFirma"));
				}
			};
		}
		
		if (rifiutoApposizioneFirmaMultiButton == null) {
			rifiutoApposizioneFirmaMultiButton = new ArchivioMultiToolStripButton("file/rifiuto_apposizione.png", this, I18NUtil.getMessages().rifiutoApposizioneFirma_button_title(), false) {

				@Override
				public boolean toShowForArchivio() {
					return abilRifiutoFirma;
					//return (idNode != null && idNode.startsWith("D.11")); // Documenti da completare /lavorare - Da firmare
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUd.add(list.getSelectedRecords()[i]);
					}
					((ArchivioList)list).manageFirmaMassiva(listaUd, false, null);
				}
			};
		}
		
		/*
		 * Inserisco il pulsante che permette di eseguire l'apposizione della firma e protocollare
		 */
		if (protocollaEFirmaMultiButton == null) {
			protocollaEFirmaMultiButton = new ArchivioMultiToolStripButton("buttons/firmaEProtocolla.png", this, I18NUtil.getMessages().apposizioneFirmaProtocollazione_button_title(), false) {

				@Override
				public boolean toShowForArchivio() {
					return apposizioneFirmaProtocollazione;
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUd.add(list.getSelectedRecords()[i]);
					}
					((ArchivioList)list).manageProtocollaTimbraEFirmaMassivi(listaUd, true, AurigaLayout.getImpostazioneFirma("tipoFirma"));
				}
			};
		}
		
		/*
		 * Inserisco il pulsante che permette di eseguire l'apposizione del visto per l'UD relativa
		 */
		if (vistoMultiButtonApposizione == null) {
			vistoMultiButtonApposizione = new ArchivioMultiToolStripButton("ok.png", this, I18NUtil.getMessages().apposizioneVisto_button_title(), false) {

				@Override
				public boolean toShowForArchivio() {
					return abilApposizioneVisto;
					//return (idNode != null && idNode.startsWith("D.VISTARE")); // Documenti da vistare
				}

				@Override
				public void doSomething() {
					manageVistoButtonClickOnMultiToolstrip(true);
				}
			};
		}
		
		if (vistoMultiButtonRifiuto == null) {
			vistoMultiButtonRifiuto = new ArchivioMultiToolStripButton("ko.png", this, I18NUtil.getMessages().rifiutoApposizioneVisto_button_title(), false) {

				@Override
				public boolean toShowForArchivio() {
					return abilRifiutoVisto;
					//return (idNode != null && idNode.startsWith("D.VISTARE")); // Documenti da vistare
				}

				@Override
				public void doSomething() {
					manageVistoButtonClickOnMultiToolstrip(false);
				}
			};
		}
		
		if (confermaAssegnazioneMultiButton == null) {
			confermaAssegnazioneMultiButton = new ArchivioMultiToolStripButton("archivio/confermaAssegnazione.png", this, "Conferma assegnazione", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilConfermaPreassegnazione;
					//return (idNode != null && "D.24".equals(idNode)); // Documenti - Assegnazioni da confermare
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUd.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUd);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ConfermaAssegnazioneDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUd, "idUdFolder", "segnatura", "Conferma assegnazione effettuata con successo",
									"Tutti i record selezionati per la conferma assegnazione sono andati in errore!",
									"Alcuni dei record selezionati per la conferma assegnazione sono andati in errore!", null);
						}
					});
				}
			};
		}
		if (modificaPreassegnazioneMultiButton == null) {
			modificaPreassegnazioneMultiButton = new ArchivioMultiToolStripButton("archivio/modificaPreassegnazione.png", this, "Modifica pre-assegnazione",
					false) {

				@Override
				public boolean toShowForArchivio() {
					return abilModificaPreassegnazione;
					//return (idNode != null && "D.24".equals(idNode)); // Documenti - Assegnazioni da confermare
				}

				@Override
				public void doSomething() {
					String flgUdFolder = null;
					if (idNode.startsWith("F.")) {
						flgUdFolder = "F";
					} else if (idNode.startsWith("D.")) {
						flgUdFolder = "U";
					}
					final ModificaPreassegnazionePopup modificaPreassegnazionePopup = new ModificaPreassegnazionePopup(flgUdFolder) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaUd = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaUd.add(list.getSelectedRecords()[i]);
							}
							Record record = new Record();
							record.setAttribute("listaRecord", listaUd);
							record.setAttribute("listaAssegnazioni", _form.getValueAsRecordList("listaAssegnazioni"));
							Layout.showWaitPopup("Modifica pre-assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModificaPreassegnazioneDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUd, "idUdFolder", "segnatura",
												"Modifica pre-assegnazione effettuata con successo",
												"Tutti i record selezionati per la modifica pre-assegnazione sono andati in errore!",
												"Alcuni dei record selezionati per la modifica pre-assegnazione sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
					modificaPreassegnazionePopup.show();
				}
			};
		}

		if (inserisciInAttoAutMultiButton == null) {
			inserisciInAttoAutMultiButton = new ArchivioMultiToolStripButton("archivio/attiAut.png", this, "Inserisci in atto di autorizzazione", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilInserimentoInAttoAutorizzAnn;
					//return (idNode != null && "D.14".equals(idNode)); // Da completare / lavorare - Annullamenti da autorizzare
				}

				@Override
				public void doSomething() {
					final RecordList listaRegDaAnnullare = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						Record regDaAnnullare = new Record();
						regDaAnnullare.setAttribute("idUd", list.getSelectedRecords()[i].getAttribute("idUdFolder"));
						listaRegDaAnnullare.add(regDaAnnullare);
					}
					LookupAttiAutorizzazioneAnnRegPopup lookupAttiAutorizzazioneAnnRegPopup = new LookupAttiAutorizzazioneAnnRegPopup(listaRegDaAnnullare) {

						@Override
						public void manageLookupBack(Record selectedRecord) {
							
							selectedRecord.setAttribute("listaRegDaAnnullare", listaRegDaAnnullare);
							final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiAutorizzazioneAnnRegDatasource", true, "idAtto",
									FieldType.TEXT);
							lGwtRestDataSource.executecustom("inserisciRegInAttoAut", selectedRecord, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean("Registrazioni inserite nell'atto di autorizzazione", "", MessageType.INFO));
										doSearch();
									}
								}
							});
						}

						@Override
						public void manageOnCloseClick() {
							
							super.manageOnCloseClick();
							doSearch();
						}
					};
					lookupAttiAutorizzazioneAnnRegPopup.show();
				}
			};
		}
		if (prendiInCaricoMultiButton == null) {
			prendiInCaricoMultiButton = new ArchivioMultiToolStripButton("archivio/prendiInCarico.png", this, "Prendi in carico", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilPresaInCarico;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM") && 
//							(idNode.startsWith("FD.2A")	|| idNode.startsWith("D.2A") || idNode.startsWith("F.2A"))); // Arrivo e novità - Per competenza
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PresaInCaricoDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Presa in carico effettuata con successo",
									"Tutti i record selezionati per la presa in carico sono andati in errore!",
									"Alcuni dei record selezionati per la presa in carico sono andati in errore!", null);
						}
					});
				}
			};
		}
		if (assegnaMultiButton == null) {
			assegnaMultiButton = new ArchivioMultiToolStripButton("archivio/assegna.png", this, "Assegna", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilAssegnazione;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/INV", "GRD/FLD/INV") && 
//							(idNode.startsWith("D.2A") || // Arrivo e novità - Per competenza -  Documenti
//							 "D.7".equals(idNode) || // Documenti da completare /lavorare - Da assegnare
//							 "D.11".equals(idNode) || // Documenti da completare /lavorare - Da firmare
//							 "D.12".equals(idNode) || // Documenti da completare /lavorare - Da protocollare
//							 "D.18".equals(idNode) || // Recenti - Registrazioni effettuate
//							 "D.20".equals(idNode) || // Recenti - Documenti lavorati
//							 idNode.startsWith("F.2A") || // Arrivo e novità - Per competenza - Fascicoli
//							 "F.19".equals(idNode) || // Recenti - Fascicoli aperti
//							 "F.30".equals(idNode) || // Recenti - Fascicoli lavorati
//							 "D.BOZZE".equals(idNode) // Bozze
//					));
				}

				@Override
				public void doSomething() {
					
					final String flgUdFolder;
					if (idNode.startsWith("F.")) {
						flgUdFolder = "F";
					} else if (idNode.startsWith("D.")) {
						flgUdFolder = "U";
					} else {
						flgUdFolder = null;
					}
					
					final RecordList listaUdFolder = new RecordList();
					// Prendo il primo flag per confrontarlo con tutti gli altri
					String flgTipoProvFirst = list.getSelectedRecords()[0].getAttribute("flgTipoProv");
					boolean isStessoFlgTipoProv = true;
					boolean hasFlgTipoProvEntrata = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
						if (list.getSelectedRecords()[i].getAttribute("flgTipoProv") != null && list.getSelectedRecords()[i].getAttribute("flgTipoProv").equalsIgnoreCase("E")) {
							hasFlgTipoProvEntrata = true;
						}							
						if(list.getSelectedRecords()[i].getAttribute("flgTipoProv") != null ? !list.getSelectedRecords()[i].getAttribute("flgTipoProv").equals(flgTipoProvFirst) : flgTipoProvFirst != null) {
							isStessoFlgTipoProv = false;
						}
					}
					final String flgTipoProv = isStessoFlgTipoProv ? flgTipoProvFirst : null;	
					final boolean isFlgTipoProvMassiva = !isStessoFlgTipoProv && hasFlgTipoProvEntrata;
					
					final Menu creaAssegna = new Menu(); // Menu principale Standar/Rapido
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					if(flgUdFolder.equals("U")){
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_DOC.getValue());
					} else {
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue());
					}
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
								RecordList listaUOPreferiti = null;
								RecordList listaUtentiPreferiti = null;
								if(flgUdFolder != null && flgUdFolder.equals("U")){
									if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite") != null ) {
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_DOC.getValue()));
									} else {
										listaUOPreferiti = new RecordList();
									}
									if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti") != null ) {
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_DOC.getValue()));
									} else {
										listaUtentiPreferiti = new RecordList();
									}												
								} else if(flgUdFolder != null && flgUdFolder.equals("F")){
									
									if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite") != null ) {										
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));
									} else {
										listaUOPreferiti = new RecordList();
									}
									if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti") != null ) {										
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.ASSEGNA_FOLDER.getValue()));					
									} else {
										listaUtentiPreferiti = new RecordList();
									}
								}
								
								boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
								final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare (sia UO che Utenti)
								
								if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
									listaPreferiti.addList(listaUOPreferiti.toArray());
									noMenuRapido = false;
								}
								
								if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
									listaPreferiti.addList(listaUtentiPreferiti.toArray());
									noMenuRapido = false;
								}
							
								// Assegna Standard 
								MenuItem assegnaMenuStandardItem = new MenuItem("Standard");								
								assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										
										final AssegnazionePopup assegnazionePopup = new AssegnazionePopup(flgUdFolder, null) {
											
											@Override
											public String getFlgTipoProvDoc() {
												if(flgUdFolder.equals("U")) {
													return flgTipoProv;														
												}
												return null;
											}
											
											@Override
											public String getSuffissoFinalitaOrganigramma() {
												if(flgUdFolder.equals("U") && isFlgTipoProvMassiva) {
													return "_MASSIVA";													
												}
												return super.getSuffissoFinalitaOrganigramma();	
											}
											
											@Override
											public RecordList getListaPreferiti() {
												return listaPreferiti;
											}
											
											@Override
											public void onClickOkButton(Record record, final DSCallback callback) {
												
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												
												Layout.showWaitPopup("Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione effettuata con successo",
																	"Tutti i record selezionati per l'assegnazione sono andati in errore!",
																	"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", callback);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}		
										};
										assegnazionePopup.show();
									}
								});
								creaAssegna.addItem(assegnaMenuStandardItem);
								
								// Assegna Rapido
								MenuItem assegnaMenuRapidoItem = new MenuItem("Rapida");				
								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								if(success != null && success && !noMenuRapido ){
										
									Menu scelteRapide = new Menu();
										
									for(int i=0; i < listaPreferiti.getLength();i++){
										Record currentRecord = listaPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
											
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
												
											@Override
											public void onClick(MenuItemClickEvent event) {
													
												RecordList listaAssegnazioni = new RecordList();
												Record recordAssegnazioni = new Record();
												recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
												recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
												listaAssegnazioni.add(recordAssegnazioni);
													
												Record record = new Record();
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("listaAssegnazioni", listaAssegnazioni);
													
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione effettuata con successo",
																	"Tutti i record selezionati per l'assegnazione sono andati in errore!",
																	"Alcuni dei record selezionati per l'assegnazione sono andati in errore!", null);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										});
										scelteRapide.addItem(currentRapidoItem);
									}
									assegnaMenuRapidoItem.setSubmenu(scelteRapide);
								} else {
									assegnaMenuRapidoItem.setEnabled(false);
								}
								
								creaAssegna.addItem(assegnaMenuRapidoItem);
								creaAssegna.showContextMenu();
							}
						}
					}, new DSRequest());
				}
			};
		}
		
		if (restituisciMultiButton == null) {
			restituisciMultiButton = new ArchivioMultiToolStripButton("archivio/restituzione.png", this, "Restituisci", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilRestituzione;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/RS", "GRD/FLD/RS") &&
//						(idNode.equals("D.2A.DP.R")	|| idNode.equals("D.2A.DP"))  // Nodi "Da prendere in carico"
//					);
				}

				@Override
				public void doSomething() {
					final RestituzionePopup restituzionePopup = new RestituzionePopup() {
						@Override
						public void onClickOkButton(final DSCallback callback) {
			
							final RecordList listaUdFolder = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaUdFolder.add(list.getSelectedRecords()[i]);
							}
							Record record = new Record();
							record.setAttribute("listaRecord", listaUdFolder);
							record.setAttribute("messaggio", _form.getValue("messaggio"));
							
							markForDestroy();
							Layout.showWaitPopup("Restituzione in corso: potrebbe richiedere qualche secondo. Attendere…");
							
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RestituzioneDataSource");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Restituizione effettuata con successo",
											"Tutti i record selezionati per la restituzione sono andati in errore!",
											"Alcuni dei record selezionati per la restituzione sono andati in errore!", null);
								}
							});
							
						}
						
					};
					restituzionePopup.show();
				}
			};
		}
		
		if(smistaMultiButton == null) {
		   smistaMultiButton = new ArchivioMultiToolStripButton("archivio/smistamento.png", this, "Smista", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilSmistamento;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/INV", "GRD/FLD/INV") && 
//							(idNode.startsWith("D.2A") || // Arrivo e novità - Per competenza - Documenti
//							 "D.7".equals(idNode) || // Documenti da completare /lavorare - Da assegnare
//							 "D.18".equals(idNode) || // Recenti - Registrazioni effettuate
//							 "D.20".equals(idNode) // Recenti - Documenti lavorati
//							))
//							&& AurigaLayout.getParametroDBAsBoolean("ATTIVA_FUNZ_SMISTAMENTO");
				}

				@Override
				public void doSomething() {
					
					final String flgUdFolder;
					if (idNode.startsWith("F.")) {
						flgUdFolder = "F";
					} else if (idNode.startsWith("D.")) {
						flgUdFolder = "U";
					}  else {
						flgUdFolder = null;
					}
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					if(flgUdFolder.equals("U")){
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue());
					} else {
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue());
					}
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
					
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
								RecordList listaUOPreferiti = null;
								RecordList listaUtentiPreferiti = null;
								if(flgUdFolder != null && flgUdFolder.equals("U")){								
									if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite") != null ) {
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
									} else {
										listaUOPreferiti = new RecordList();
									}
									if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti") != null ) {
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));
									} else {
										listaUtentiPreferiti = new RecordList();
									}
								} else if(flgUdFolder != null && flgUdFolder.equals("F")){									
									if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite") != null ) {
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_FOLDER.getValue()));
									} else {
										listaUOPreferiti = new RecordList();
									}
									if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti") != null ) {
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_FOLDER.getValue()));
									} else {
										listaUtentiPreferiti = new RecordList();
									}	
								}
								
								boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
								final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare (sia UO che Utenti)
								
								if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
									listaPreferiti.addList(listaUOPreferiti.toArray());
									noMenuRapido = false;
								}
								
								if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
									listaPreferiti.addList(listaUtentiPreferiti.toArray());
									noMenuRapido = false;
								}
								
								final Menu creaSmista = new Menu(); // Menu principale Standar/Rapido
								
								// Smista Standard 
								MenuItem smistaMenuStandardItem = new MenuItem("Standard");							
								smistaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										
										final SmistamentoPopup smistamentoPopup = new SmistamentoPopup(flgUdFolder, null) {
											
											@Override
											public RecordList getListaPreferiti() {
												return listaPreferiti;
											}			
											
											@Override
											public void onClickOkButton(Record record, final DSCallback callback) {
																								
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("motivoInvio", "#SMIST");
												
												Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Smistamento effettuato con successo",
																	"Tutti i record selezionati per lo smistamento sono andati in errore!",
																	"Alcuni dei record selezionati per lo smistamento sono andati in errore!", callback);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										};
										smistamentoPopup.show();
									}
								});
								creaSmista.addItem(smistaMenuStandardItem);
								
								// Smista Rapido
								MenuItem smistaMenuRapidoItem = new MenuItem("Rapida");				
								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								if(success != null && success && !noMenuRapido ){
										
									Menu scelteRapide = new Menu();
										
									for(int i=0; i < listaPreferiti.getLength();i++){
										Record currentRecord = listaPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
											
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
												
											@Override
											public void onClick(MenuItemClickEvent event) {
													
												RecordList listaAssegnazioni = new RecordList();
												Record recordAssegnazioni = new Record();
												recordAssegnazioni.setAttribute("idUo",     idDestinatarioPreferito);
												recordAssegnazioni.setAttribute("typeNodo", tipoDestinatarioPreferito);
												listaAssegnazioni.add(recordAssegnazioni);
													
												Record record = new Record();
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("listaAssegnazioni", listaAssegnazioni);												
												record.setAttribute("motivoInvio", "#SMIST");
													
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneSmistamentoDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Smistamento effettuato con successo",
																	"Tutti i record selezionati per lo smistamento sono andati in errore!",
																	"Alcuni dei record selezionati per lo smistamento sono andati in errore!", null);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										});
										scelteRapide.addItem(currentRapidoItem);
									}
									smistaMenuRapidoItem.setSubmenu(scelteRapide);
								} else {
									smistaMenuRapidoItem.setEnabled(false);
								}								
								creaSmista.addItem(smistaMenuRapidoItem);
								creaSmista.showContextMenu();
							}
						}
					});				
				}
			};
		}	
		if (smistaCCMultiButton == null) {
			smistaCCMultiButton = new ArchivioMultiToolStripButton("archivio/smistamentoCC.png", this, "Smista", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilSmistamentoCC;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/INV", "GRD/FLD/INV") && 
//							(idNode.startsWith("D.2A") || // Arrivo e novità - Per competenza - Documenti
//							 "D.7".equals(idNode) || // Documenti da completare /lavorare - Da assegnare
//							 "D.18".equals(idNode) || // Recenti - Registrazioni effettuate
//							 "D.20".equals(idNode) // Recenti - Documenti lavorati
//							))
//							&& AurigaLayout.getParametroDBAsBoolean("ATTIVA_FUNZ_SMISTAMENTO");
				}

				@Override
				public void doSomething() {
					
					final String flgUdFolder;
					if (idNode.startsWith("F.")) {
						flgUdFolder = "F";
					} else if (idNode.startsWith("D.")) {
						flgUdFolder = "U";
					}  else {
						flgUdFolder = null;
					}
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					if(flgUdFolder.equals("U")){
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.SMISTA_DOC.getValue());
					} else {
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.SMISTA_FOLDER.getValue());
					}
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
					
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
								RecordList listaUOPreferiti = null;
								RecordList listaUtentiPreferiti = null;
								if(flgUdFolder != null && flgUdFolder.equals("U")){								
									if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite") != null ) {
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_DOC.getValue()));
									} else {
										listaUOPreferiti = new RecordList();
									}
									if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti") != null ) {
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_DOC.getValue()));
									} else {
										listaUtentiPreferiti = new RecordList();
									}
								} else if(flgUdFolder != null && flgUdFolder.equals("F")){									
									if(destinatariPreferiti.getAttributeAsMap("mappaUOPreferite") != null ) {
										listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.SMISTA_FOLDER.getValue()));
									} else {
										listaUOPreferiti = new RecordList();
									}
									if(destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti") != null ) {
										listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.SMISTA_FOLDER.getValue()));
									} else {
										listaUtentiPreferiti = new RecordList();
									}	
								}
								
								boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
								final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare (sia UO che Utenti)
								
								if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
									listaPreferiti.addList(listaUOPreferiti.toArray());
									noMenuRapido = false;
								}
								
								if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
									listaPreferiti.addList(listaUtentiPreferiti.toArray());
									noMenuRapido = false;
								}
								
								final Menu creaSmistaCC = new Menu(); // Menu principale Standar/Rapido
								
								// SmistaCC Standard 
								MenuItem smistaCCMenuStandardItem = new MenuItem("Standard");							
								smistaCCMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										
										String title = "Compila dati smistamento";
										final CondivisionePopup condivisionePopup = new CondivisionePopup(flgUdFolder, null, title) {	
											
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

												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("motivoInvio", "#SMIST");
												
												Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Smistamento effettuato con successo",
																	"Tutti i record selezionati per lo smistamento sono andati in errore!",
																	"Alcuni dei record selezionati per lo smistamento sono andati in errore!", callback);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										};
										condivisionePopup.show();
									}
								});
								creaSmistaCC.addItem(smistaCCMenuStandardItem);
								
								// SmistaCC Rapido
								MenuItem smistaCCMenuRapidoItem = new MenuItem("Rapida");				
								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								if(success != null && success && !noMenuRapido ){
										
									Menu scelteRapide = new Menu();
										
									for(int i=0; i < listaPreferiti.getLength();i++){
										Record currentRecord = listaPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
											
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
												
											@Override
											public void onClick(MenuItemClickEvent event) {
													
												RecordList listaDestInvioCC = new RecordList();
												Record recordDestInvioCC = new Record();
												recordDestInvioCC.setAttribute("idUo",     idDestinatarioPreferito);
												recordDestInvioCC.setAttribute("typeNodo", tipoDestinatarioPreferito);
												listaDestInvioCC.add(recordDestInvioCC);
													
												Record record = new Record();
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("listaDestInvioCC", listaDestInvioCC);
												record.setAttribute("motivoInvio", "#SMIST");
													
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Smistamento effettuato con successo",
																	"Tutti i record selezionati per lo smistamento sono andati in errore!",
																	"Alcuni dei record selezionati per lo smistamento sono andati in errore!", null);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										});
										scelteRapide.addItem(currentRapidoItem);
									}
									smistaCCMenuRapidoItem.setSubmenu(scelteRapide);
								} else {
									smistaCCMenuRapidoItem.setEnabled(false);
								}
								
								creaSmistaCC.addItem(smistaCCMenuRapidoItem);
								creaSmistaCC.showContextMenu();
							}
						}
					});
					
				}
			};
		}
		if (classificaMultiButton == null) {
			classificaMultiButton = new ArchivioMultiToolStripButton("archivio/classfasc.png", this, "Classifica/fascicola", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilClassificazioneFascicolazione;
					//return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM") && (AurigaLayout.isAttivoClassifSenzaFasc(null) && "D.9".equals(idNode))); // Documenti da completare /lavorare - Da classificare (se CLASSIF_SENZA_FASC = true)
				}

				@Override
				public void doSomething() {
					final ClassificazioneFascicolazionePopup classificazioneFascicolazionePopup = new ClassificazioneFascicolazionePopup(false, null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaUd = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaUd.add(list.getSelectedRecords()[i]);
							}
							Record record = new Record();
							record.setAttribute("listaRecord", listaUd);
							record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
							record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
							lGwtRestDataSource.addParam("inAppend", "true");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									massiveOperationCallback(response, listaUd, "idUdFolder", "segnatura",
											"Classificazione/fascicolazione effettuata con successo",
											"Tutti i record selezionati per la classificazione/fascicolazione sono andati in errore!",
											"Alcuni dei record selezionati per la classificazione/fascicolazione sono andati in errore!", callback);
								}
							});
						}
					};
				}
			};
		}
		if (fascicolaMultiButton == null) {
			fascicolaMultiButton = new ArchivioMultiToolStripButton("archivio/fascicola.png", this, "Fascicola", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilFascicolazione;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM") && 
//							((!AurigaLayout.isAttivoClassifSenzaFasc(null) && "D.9".equals(idNode)) || // Documenti da completare /lavorare - Da classificare (se CLASSIF_SENZA_FASC = false)
//							 "D.10".equals(idNode) || // Documenti da completare /lavorare - Da fascicolare
//							 idNode.startsWith("D.2A") || // Arrivo e novità - Per competenza - Documenti
//							 "D.18".equals(idNode) || // Recenti - Registrazioni effettuate
//							 "D.20".equals(idNode) // Recenti - Documenti lavorati
//					));
				}

				@Override
				public void doSomething() {
					final ClassificazioneFascicolazionePopup fascicolazionePopup = new ClassificazioneFascicolazionePopup(true, null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaUd = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaUd.add(list.getSelectedRecords()[i]);
							}
							Record record = new Record();
							record.setAttribute("listaRecord", listaUd);
							record.setAttribute("listaClassFasc", _form.getValueAsRecordList("listaClassFasc"));
							record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ClassificazioneFascicolazioneDataSource");
							lGwtRestDataSource.addParam("inAppend", "true");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									massiveOperationCallback(response, listaUd, "idUdFolder", "segnatura", "Fascicolazione effettuata con successo",
											"Tutti i record selezionati per la fascicolazione sono andati in errore!",
											"Alcuni dei record selezionati per la fascicolazione sono andati in errore!", callback);
								}
							});
						}
					};
				}
			};
		}
		
		if (modificaTipologiaMultiButton == null) {
			modificaTipologiaMultiButton = new ArchivioMultiToolStripButton("archivio/modificaTipologia.png", this,"Modifica tipologia documentale", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilModificaTipologiaMultiButton;
//					return (Layout.isPrivilegioAttivo("GRD/UD/UUD") || Layout.isPrivilegioAttivo("GRD/FLD/UM"));
//					return true;
				}

				@Override
				public void doSomething() {
					
					Boolean isFolder = false;
					Boolean isUd = false;
					int numElement = 0;
					final RecordList listaUdFolder = new RecordList();
					
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if("F".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))){
							isFolder = true;
							numElement++;
						}else if("U".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))) {
							isUd = true;
							numElement++;
						}
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					////ho selezionato solo Folder
					if(isFolder && !isUd){
						Record record = new Record();
						String idFolder = null;
						String descType = null;
						
						//se ho selezionato un solo elemento inizializzo la tipologia nella select
						if(numElement==1) {
							idFolder = listaUdFolder.get(0).getAttribute("idFolderType");
							descType = listaUdFolder.get(0).getAttribute("tipo");
							record.setAttribute("idClassifica", listaUdFolder.get(0).getAttribute("idClassifica"));
						}
						final String descrizione= descType;
						
						record.setAttribute("idFolderApp", listaUdFolder.get(0).getAttribute("idFolderApp"));
						record.setAttribute("idFolderType", listaUdFolder.get(0).getAttribute("idFolderType"));

						new SceltaTipoFolderPopup(false, idFolder, descType, record,
								new ServiceCallback<Record>() {

									@Override
									public void execute(Record lRecordTipoDoc) {

										String idFolderType = lRecordTipoDoc.getAttribute("idFolderType");
										
										Record record = new Record();
										record.setAttribute("listaRecord", listaUdFolder);

										//se la tipologia selezionata è diversa da quella attuale
										if (!descrizione.equals(idFolderType)) {
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
													"ArchivioDatasource");
											lGwtRestDataSource.extraparam.put("idFolderType", idFolderType);
											lGwtRestDataSource.executecustom("modificaTipologiaMassiva", record,
													new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															massiveOperationCallback(response, listaUdFolder,
																	"idUdFolder", "segnatura",
																	"Operazione effettuata con successo",
																	"Tutti i record selezionati per la modifica della tipologia sono andati in errore!",
																	"Alcuni dei record selezionati per la modifica della tipologia sono andati in errore!",
																	null);
														}
													});

										}
									}
								});

					//ho selezionato solo UD
					} else if(!isFolder && isUd) {
						String idDocumento = null;
						String descType = null;
						
						//se ho selezionato un solo elemento inizializzo la tipologia nella select
						if(numElement==1) {
							descType = listaUdFolder.get(0).getAttribute("tipo");
							idDocumento = listaUdFolder.get(0).getAttribute("idTipoDocumento");
						}
						final String descrizione= descType;
						//aggiungere creazione popup
						new SceltaTipoDocPopup(false, idDocumento, descType, null, null, "|*|FINALITA|*|ASS_TIPOLOGIA",
								new ServiceCallback<Record>() {
			
							@Override
							public void execute(Record lRecordTipoDoc) {
			
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								
								String tipoDocumento = lRecordTipoDoc.getAttribute("idTipoDocumento");
								
										//se la tipologia selezionata è diversa da quella attuale
										if (!descrizione.equals(tipoDocumento)) {
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
													"ArchivioDatasource");
											lGwtRestDataSource.extraparam.put("tipoDocumento", tipoDocumento);
											lGwtRestDataSource.executecustom("modificaTipologiaMassiva", record,
													new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData,
																DSRequest request) {
															massiveOperationCallback(response, listaUdFolder,
																	"idUdFolder", "segnatura",
																	"Operazione effettuata con successo",
																	"Tutti i record selezionati per la modifica della tipologia sono andati in errore!",
																	"Alcuni dei record selezionati per la modifica della tipologia sono andati in errore!",
																	null);
														}
													});
										}
							}
						});
					}else {
						Layout.addMessage(new MessageBean("Per questo tipo di azione devi selezionare solo documenti o solo fascicoli/aggregati di documenti",
								"", MessageType.WARNING));
					}
				}
			};
		}
		
		if (organizzaMultiButton == null) {
			organizzaMultiButton = new ArchivioMultiToolStripButton("archivio/organizza.png", this, "Organizza", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilFolderizzazione;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM") && 
//							AurigaLayout.getParametroDBAsBoolean("ATTIVA_FOLDER_CUSTOM") & 
//							("D.9".equals(idNode) || // Documenti da completare /lavorare - Da classificare
//							 "D.10".equals(idNode) || // Documenti da completare /lavorare - Da fascicolare
//							 idNode.startsWith("D.2A") || // Arrivo e novità - Per competenza - Documenti
//							 "D.18".equals(idNode) || // Recenti - Registrazioni effettuate
//							 "D.20".equals(idNode) // Recenti - Documenti lavorati
//					));
				}

				@Override
				public void doSomething() {
					final OrganizzaPopup organizzaPopup = new OrganizzaPopup(null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaUd = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaUd.add(list.getSelectedRecords()[i]);
							}
							Record record = new Record();
							record.setAttribute("listaRecord", listaUd);
							record.setAttribute("listaFolderCustom", _form.getValueAsRecordList("listaFolderCustom"));
							record.setAttribute("livelloRiservatezza", _form.getValue("livelloRiservatezza"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganizzaDataSource");
							lGwtRestDataSource.addParam("inAppend", "true");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									massiveOperationCallback(response, listaUd, "idUdFolder", "segnatura",
											"Organizzazione nella cartella/e effettuata con successo",
											"Tutti i record selezionati per l'organizzazione nella cartella/e sono andati in errore!",
											"Alcuni dei record selezionati per l'organizzazione nella cartella/e sono andati in errore!", callback);
								}
							});
						}
					};
				}
			};
		}
		if (condividiMultiButton == null) {
			condividiMultiButton = new ArchivioMultiToolStripButton("archivio/condividi.png", this, "Invia per conoscenza", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilInvioPerConoscenza;
					//return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/NOT", "GRD/FLD/NOT") && !idNode.equals("D.23")); // Solo documenti o solo fascicoli (tranne Stampe ed esportazioni su file)
				}

				@Override
				public void doSomething() {
					
					final String flgUdFolder;
					if (idNode.startsWith("F.")) {
						flgUdFolder = "F";
					} else if (idNode.startsWith("D.")) {
						flgUdFolder = "U";
					} else {
						flgUdFolder = null;
					}
					
					final RecordList listaUdFolder = new RecordList();
					// Prendo il primo flag per confrontarlo con tutti gli altri
					String flgTipoProvFirst = list.getSelectedRecords()[0].getAttribute("flgTipoProv");
					boolean isStessoFlgTipoProv = true;
					boolean hasFlgTipoProvEntrata = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
						if (list.getSelectedRecords()[i].getAttribute("flgTipoProv") != null && list.getSelectedRecords()[i].getAttribute("flgTipoProv").equalsIgnoreCase("E")) {
							hasFlgTipoProvEntrata = true;
						}							
						if(list.getSelectedRecords()[i].getAttribute("flgTipoProv") != null ? !list.getSelectedRecords()[i].getAttribute("flgTipoProv").equals(flgTipoProvFirst) : flgTipoProvFirst != null) {
							isStessoFlgTipoProv = false;
						}
					}
					final String flgTipoProv = isStessoFlgTipoProv ? flgTipoProvFirst : null;	
					final boolean isFlgTipoProvMassiva = !isStessoFlgTipoProv && hasFlgTipoProvEntrata;
					
					final Menu creaCondividi = new Menu(); // Menu principale Standar/Rapido
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					if(flgUdFolder.equals("U")){
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_DOC.getValue());
					} else {
						recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue());
					}
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
								RecordList listaUOPreferiti = null;
								RecordList listaUtentiPreferiti = null;
								if(flgUdFolder != null && flgUdFolder.equals("U")){
//									listaUOPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteDoc");
//									listaUtentiPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiDoc");
									listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
									listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));				
								} else if(flgUdFolder != null && flgUdFolder.equals("F")){
//									listaUOPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUOPreferiteFolder");
//									listaUtentiPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiFolder");
									listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_FOLDER.getValue()));
									listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_FOLDER.getValue()));					
								}								
								
								boolean noMenuRapido = true; // identifica la presenza o menu di valori da visualizzare nel menu rapido
								final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare (sia UO che Utenti)
								
								if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
									listaPreferiti.addList(listaUOPreferiti.toArray());
									noMenuRapido = false;
								}
								
								if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
									listaPreferiti.addList(listaUtentiPreferiti.toArray());
									noMenuRapido = false;
								}
																
								// Condividi Standard 
								MenuItem condivisioneMenuStandardItem = new MenuItem("Standard");
								condivisioneMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										final CondivisionePopup condivisionePopup = new CondivisionePopup(flgUdFolder, null) {
											
											@Override
											public String getFlgTipoProvDoc() {
												if(flgUdFolder.equals("U")) {
													return flgTipoProv;														
												}
												return null;
											}
											
											@Override
											public String getSuffissoFinalitaOrganigramma() {
												if(flgUdFolder.equals("U") && isFlgTipoProvMassiva) {
													return "_MASSIVA";													
												}
												return super.getSuffissoFinalitaOrganigramma();	
											}
											
											@Override
											public RecordList getListaPreferiti() {
												return listaPreferiti;
											}

											@Override
											public void onClickOkButton(Record record, final DSCallback callback) {
												
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												
												Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
																	"Invio per conoscenza effettuato con successo",
																	"Tutti i record selezionati per l'invio per conoscenza sono andati in errore!",
																	"Alcuni dei record selezionati per l'invio per conoscenza sono andati in errore!", callback);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										};
										condivisionePopup.show();
									}	
								});
								creaCondividi.addItem(condivisioneMenuStandardItem);
								
								// Condividi rapido 
								MenuItem condividiMenuRapidoItem = new MenuItem("Rapida");				
								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								if(success != null && success && !noMenuRapido){
									
									Menu scelteRapide = new Menu();
									
									for(int i=0; i < listaPreferiti.getLength();i++){
										Record currentRecord = listaPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												RecordList listaDestInvioCC = new RecordList();
												Record recordDest = new Record();
												recordDest.setAttribute("idUo", idDestinatarioPreferito);
												recordDest.setAttribute("typeNodo",tipoDestinatarioPreferito);
												recordDest.setAttribute("listaDestInvioCC", listaDestInvioCC);
												listaDestInvioCC.add(recordDest);
												
												Record record = new Record();
												record.setAttribute("flgUdFolder", flgUdFolder);
												record.setAttribute("listaRecord", listaUdFolder);
												record.setAttribute("listaDestInvioCC", listaDestInvioCC);
												Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
																	"Invio per conoscenza effettuato con successo",
																	"Tutti i record selezionati per l'invio per conoscenza sono andati in errore!",
																	"Alcuni dei record selezionati per l'invio per conoscenza sono andati in errore!", null);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
									}
									condividiMenuRapidoItem.setSubmenu(scelteRapide);

								} else {
									condividiMenuRapidoItem.setEnabled(false);
								}
								creaCondividi.addItem(condividiMenuRapidoItem);
								creaCondividi.showContextMenu();
							}
						}
					}, new DSRequest());
				}
			};
		}

		if (stampaEtichettaMultiButton == null) {
			stampaEtichettaMultiButton = new ArchivioMultiToolStripButton("protocollazione/barcode.png", this, "Stampa etichetta", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilStampaEtichetta;
					//return (idNode != null && idNode.startsWith("D.") && !idNode.equalsIgnoreCase("D.BOZZE"));
				}

				@Override
				public void doSomething() {
					final RecordList records = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++)
						records.add(list.getSelectedRecords()[i]);

					// Leggo i valori del filtro MEZZO DI TRASMISSIONE
					JavaScriptObject mezziTrasmissioneFilter = getMezzoTrasmissioneValueFilter();

					if (mezziTrasmissioneFilter == null) {
						Record listMezziTrasmissioneFilter = new Record();
						final MezziTrasmissionePopup mezziTrasmissionePopup = new MezziTrasmissionePopup(listMezziTrasmissioneFilter) {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								Record record = new Record();
								record.setAttribute("listaRecord", records);
								if ((JavaScriptObject) _form.getValue("listaMezziTrasmissione") != null) {
									record.setAttribute("mezziTrasmissione", _form.getValueAsString("listaMezziTrasmissione"));
								}

								final GWTRestService<Record, Record> lGwtRestDataSource = new GWTRestService<Record, Record>(
										"StampaEtichettaIndirizzoDataSource");
								lGwtRestDataSource.executecustom("getListaEtichette", record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massivePrintLabelCallback(lGwtRestDataSource, response, records, null);
									}
								});
								this.markForDestroy();
							}
						};
						mezziTrasmissionePopup.show();
					} else {
						Record record = new Record();
						record.setAttribute("listaRecord", records);
						record.setAttribute("mezziTrasmissione", mezziTrasmissioneFilter.toString());
						final GWTRestService<Record, Record> lGwtRestDataSource = new GWTRestService<Record, Record>("StampaEtichettaIndirizzoDataSource");
						lGwtRestDataSource.executecustom("getListaEtichette", record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massivePrintLabelCallback(lGwtRestDataSource, response, records, null);
							}
						});
					}
				}
			};
		}

//		if (archiviazioneMultiButton == null) {
//			archiviazioneMultiButton = new ArchivioMultiToolStripButton("scrivania/interesseCessato.png", this,
//					AurigaLayout.getParametroDB("LABEL_SET_INTERESSE_CESSATO_FASC"), false) {
//
//				@Override
//				public boolean toShowForArchivio() {					
//					return abilArchiviazione;
//					return isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM")
//							&& ((idNode != null && ("D.7".equals(idNode) || "D.9".equals(idNode) || "D.10".equals(idNode))) || (idFolder != null && ("-9"
//									.equals(idFolder) || "-99999".equals(idFolder))) && !controlloButtonArchiviaMassivo());
//				}
//
//				@Override
//				public void doSomething() {
//					final RecordList listaUdFolder = new RecordList();
//					for (int i = 0; i < list.getSelectedRecords().length; i++) {
//						listaUdFolder.add(list.getSelectedRecords()[i]);
//					}
//					Record record = new Record();
//					record.setAttribute("listaRecord", listaUdFolder);
//					record.setAttribute("flgInteresseCessato", "1");
//					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneDataSource");
//					lGwtRestDataSource.addData(record, new DSCallback() {
//
//						@Override
//						public void execute(DSResponse response, Object rawData, DSRequest request) {
//							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", I18NUtil.getMessages()
//									.posta_elettronica_archiviazione_successo(), I18NUtil.getMessages().posta_elettronica_archiviazione_errore_totale(),
//									I18NUtil.getMessages().posta_elettronica_archiviazione_errore_parziale(), null);
//						}
//					});
//				}
//			};
//		}
		if (annullaArchiviazioneMultiButton == null) {
			annullaArchiviazioneMultiButton = new ArchivioMultiToolStripButton("archivio/annullaArchiviazione.png", this, I18NUtil.getMessages()
					.annulla_archiviazione_label_button(), false) {

				@Override
				public boolean toShowForArchivio() {					
					return abilAnnullamentoArchiviazione;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM")
//							&& ("FD.21".equals(idNode) || "D.21".equals(idNode) || "F.21".equals(idNode)) && !controlloButtonArchiviaMassivo());
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}

					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("flgInteresseCessato", "0");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", I18NUtil.getMessages()
									.posta_elettronica_annulla_archiviazione_successo(), I18NUtil.getMessages()
									.posta_elettronica_annulla_archiviazione_errore_totale(), I18NUtil.getMessages()
									.posta_elettronica_annulla_archiviazione_errore_parziale(), null);
						}
					});
				}
			};
		}
		if (aggiungiAPreferitiMultiButton == null) {
			aggiungiAPreferitiMultiButton = new ArchivioMultiToolStripButton("archivio/aggiungiAPreferiti.png", this, "Aggiungi ai preferiti", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilAggiuntaAiPreferiti;
					//return Layout.isPrivilegioAttivo("PRF") && ((idFolder == null || !"-999".equals(idFolder)) && !(idNode != null && idNode.equals("D.23"))); // Se non è in Preferiti (e non è in Stampe ed esportazioni su file)
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AggiuntaAPreferitiDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Aggiunta ai \"Preferiti\" effettuata con successo",
									"Tutti i record selezionati per l'aggiunta ai \"Preferiti\" sono andati in errore!",
									"Alcuni dei record selezionati per l'aggiunta ai \"Preferiti\" sono andati in errore!", null);
						}
					});
				}
			};
		}
		if (rimuoviDaPreferitiMultiButton == null) {
			rimuoviDaPreferitiMultiButton = new ArchivioMultiToolStripButton("archivio/rimuoviDaPreferiti.png", this, "Rimuovi dai preferiti", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilRimozioneDaiPreferiti;
					//return Layout.isPrivilegioAttivo("PRF") && (idFolder != null && "-999".equals(idFolder)); // Se è in Preferiti
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RimozioneDaPreferitiDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Rimozione dai \"Preferiti\" effettuata con successo",
									"Tutti i record selezionati per la rimozione dai \"Preferiti\" sono andati in errore!",
									"Alcuni dei record selezionati per la rimozione dai \"Preferiti\" sono andati in errore!", null);
						}
					});
				}
			};
		}
		if (assegnaRiservatezzaMultiButton == null) {
			assegnaRiservatezzaMultiButton = new ArchivioMultiToolStripButton("archivio/assegnaRiservatezza.png", this, "Assegna riservatezza", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilAssegnazioneRiservatezza;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM") && !idNode.equalsIgnoreCase("D.BOZZE") && !idNode
//							.equalsIgnoreCase("D.23")); // Se non è in Bozze e non è in Stampe e export
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("livelloRiservatezza", "1");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModificaLivelloRiservatezzaDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", I18NUtil.getMessages()
									.posta_elettronica_assegnazione_successo(), I18NUtil.getMessages().posta_elettronica_assegnazione_errore_totale(), I18NUtil
									.getMessages().posta_elettronica_assegnazione_errore_parziale(), null);
						}
					});
				}
			};
		}
		if (rimuoviRiservatezzaMultiButton == null) {
			rimuoviRiservatezzaMultiButton = new ArchivioMultiToolStripButton("archivio/rimuoviRiservatezza.png", this, "Rimuovi riservatezza", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilRimozioneRiservatezza;
//					return (idNode != null && isPrivilegioDocFascAttivo(idNode, "GRD/UD/UUD", "GRD/FLD/UM") && !idNode.equalsIgnoreCase("D.BOZZE") && !idNode
//							.equalsIgnoreCase("D.23")); // Se non è in Bozze e non è in Stampe e export
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("livelloRiservatezza", "");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModificaLivelloRiservatezzaDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Rimozione riservatezza effettuata con successo",
									"Tutti i record selezionati per la rimozione riservatezza sono andati in errore!",
									"Alcuni dei record selezionati per la rimozione riservatezza sono andati in errore!", null);
						}
					});
				}
			};
		}
		if (ripristinaMultiButton == null) {
			ripristinaMultiButton = new ArchivioMultiToolStripButton("archivio/ripristina.png", this, "Ripristina nella sezione di origine", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilAnnullamentoEliminazione;
					//return Layout.isPrivilegioAttivo("ELS") && (idFolder != null && "-99999".equals(idFolder)); // Se è in Eliminati
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RipristinoDataSource");
					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Ripristino effettuata con successo",
									"Tutti i record selezionati per il ripristino sono andati in errore!",
									"Alcuni dei record selezionati per il ripristino sono andati in errore!", null);
						}
					});
				}
			};
		}
		
		
		if (eliminaImgScansioneMultiButton == null) {
			eliminaImgScansioneMultiButton = new ArchivioMultiToolStripButton("buttons/delete.png", this, "Elimina", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilEliminazioneImgScansione;
				}

				@Override
				public void doSomething() {
						SC.ask("Sei sicuro di voler cancellare le immagini selezionate ?", new BooleanCallback() {
							@Override 
							public void execute(Boolean value) {  
								if (value) {
									final RecordList listaUdFolder = new RecordList();
									for (int i = 0; i < list.getSelectedRecords().length; i++) {
										listaUdFolder.add(list.getSelectedRecords()[i]);
									}
									Record record = new Record();
									record.setAttribute("listaRecord", listaUdFolder);
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
									lGwtRestDataSource.executecustom("cancellaScansioni", record, new DSCallback() {

										@Override public void execute(DSResponse response, Object rawData, DSRequest request) { 
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", 
													"Tutte le immagini selezionate sono state cancellate", 
													"Tutte le immagini selezionate sono andate in errore!",
													"Alcune immagini selezionate sono andate in errore!", null); 
										} 
									}); 
								} 
							} 
						});
					
				}
			};
		}
		
		if (eliminaMultiButton == null) {
			eliminaMultiButton = new ArchivioMultiToolStripButton("buttons/cestino.png", this, "Sposta in Eliminati", false) {

				@Override
				public boolean toShowForArchivio() {
					return abilEliminazione;
				}

				@Override
				public void doSomething() {
 
						final EliminazionePopup eliminazionePopup = new EliminazionePopup() {

							@Override
							public void onClickOkButton(final DSCallback callback) {
								
								final RecordList listaUdFolder = new RecordList();
								for (int i = 0; i < list.getSelectedRecords().length; i++) {
									Record rec = new Record();
									rec.setAttribute("flgUdFolder", list.getSelectedRecords()[i].getAttribute("flgUdFolder"));
									rec.setAttribute("idUdFolder", list.getSelectedRecords()[i].getAttribute("idUdFolder"));
									listaUdFolder.add(rec);
								}
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								record.setAttribute("motivo", _form.getValue("motivo"));
								record.setAttribute("sezioneAreaLav", codSezione);
								GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneDaAreaLavoroDataSource");
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (idFolder != null && "-99999".equals(idFolder)) {
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
													"Eliminazione dalla Scrivania effettuata con successo",
													"Tutti i record selezionati per l'eliminazione dalla Scrivania sono andati in errore!",
													"Alcuni dei record selezionati per l'eliminazione dalla Scrivania sono andati in errore!", callback);
										} else {
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
													"Spostamento in Eliminati effettuato con successo",
													"Tutti i record selezionati per lo spostamento in Eliminati sono andati in errore!",
													"Alcuni dei record selezionati per lo spostamento in Eliminati sono andati in errore!", callback);
										}
									}
								});
							}
						};
						eliminazionePopup.show();
					
					
					
				}
			};
		}


		if (downloadDocZipMultiButton == null) {
			downloadDocZipMultiButton = new ArchivioMultiToolStripButton("buttons/download_zip.png", this, I18NUtil.getMessages().archivio_list_downloadDocsZip()) {

				@Override
				public boolean toShowForArchivio() {
					return abilDownloadDocZipMultiButton;
//					boolean id = idNode != null && !idNode.startsWith("F.");
//					// idNode.startsWith("D.")
//					return id;
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isFolder = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						String tipoDoc = (list.getSelectedRecords()[i]).getAttribute("flgUdFolder");
						if (!tipoDoc.startsWith("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);
						} else
							isFolder = true;
					}
					if (!listaUdFolder.isEmpty()) {
						if (isFolder)
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_downloadDocsZip(), "",MessageType.WARNING));

						final Menu downloadZipMenu = new Menu();
						MenuItem scaricaFileMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_originali(), "buttons/download_zip.png");
						scaricaFileMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								downloadZipDocument(listaUdFolder, "scaricaOriginali");
							}
						});
						downloadZipMenu.addItem(scaricaFileMenuItem);
						
						final Record detailRecord = new Record(detail.getValuesManager().getValues());
						if(showOperazioniTimbratura(detailRecord)) {

							MenuItem scaricaFileTimbratiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_segnatura(), "buttons/download_zip.png");
							scaricaFileTimbratiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
								@Override
								public void onClick(MenuItemClickEvent event) {
									downloadZipDocument(listaUdFolder, "scaricaTimbratiSegnatura");
								}
							});
							downloadZipMenu.addItem(scaricaFileTimbratiSegnaturaMenuItem);
	
							if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA")) {
								MenuItem scaricaFileTimbratiConformeStampaMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_stampa(), "buttons/download_zip.png");
								scaricaFileTimbratiConformeStampaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
									@Override
									public void onClick(MenuItemClickEvent event) {
										downloadZipDocument(listaUdFolder, "scaricaTimbratiConformeStampa");
									}
								});
								downloadZipMenu.addItem(scaricaFileTimbratiConformeStampaMenuItem);
	
								MenuItem scaricaFileTimbratiConformeDigitaleMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_digitale(),"buttons/download_zip.png");
								scaricaFileTimbratiConformeDigitaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
									@Override
									public void onClick(MenuItemClickEvent event) {
										downloadZipDocument(listaUdFolder, "scaricaTimbratiConformeDigitale");
									}
								});
								downloadZipMenu.addItem(scaricaFileTimbratiConformeDigitaleMenuItem);
							}
	
							if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
								MenuItem scaricaFileTimbratiConformeCartaceoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_timbrati_conforme_cartaceo(),"buttons/download_zip.png");
								scaricaFileTimbratiConformeCartaceoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
									@Override
									public void onClick(MenuItemClickEvent event) {
										downloadZipDocument(listaUdFolder, "scaricaTimbratiConformeCartaceo");
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
									downloadZipDocument(listaUdFolder, "scaricaConformitaCustom");
								}
							});
							downloadZipMenu.addItem(scaricaFileConformitaCustomMenuItem);
						}

						MenuItem scaricaFileSbustatiMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_downloadDocsZip_sbustati() ,"buttons/download_zip.png");
						scaricaFileSbustatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								downloadZipDocument(listaUdFolder, "scaricaSbustati");
							}
						});
						downloadZipMenu.addItem(scaricaFileSbustatiMenuItem);

						downloadZipMenu.showContextMenu();
					} else {
						Layout.addMessage(
								new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_alldoc_downloadDocsZip(),
										"", MessageType.WARNING));
					}
				}

				public void downloadZipDocument(final RecordList listaUdFolder, String operazione) {
					if(!"scaricaOriginali".equalsIgnoreCase(operazione) && !"scaricaSbustati".equalsIgnoreCase(operazione) && !"scaricaConformitaCustom".equalsIgnoreCase(operazione)) {
						if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaTimbroDocZip")) {
							showOpzioniTimbratura(listaUdFolder, operazione);
						}else {
							String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
							String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
							String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");
							
							Record opzioniTimbro = new Record();
							opzioniTimbro.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
							opzioniTimbro.setAttribute("posizioneTimbro", posizioneTimbroPref);
							opzioniTimbro.setAttribute("tipoPagina", tipoPaginaPref);
							
							manageGenerateDocZip(listaUdFolder, operazione, opzioniTimbro);
							
						}
					}else {
						manageGenerateDocZip(listaUdFolder, operazione, null);
					}
				}

				
			};
		}
		// MODIFICA STATO DOCUMENTI
		if (modificaStatoDocMultiButton == null) {
			modificaStatoDocMultiButton = new ArchivioMultiToolStripButton("archivio/archiviaConcludi.png", this,
					I18NUtil.getMessages().archivio_layout_archiviaConludiLavorazione_title()) {

				@Override
				public boolean toShowForArchivio() {
					return abilModificaStatoDocMultiButton;
				}

				@Override
				public void doSomething() {
					
					final RecordList listaUdFolder = new RecordList();
					Boolean isFolder = false;
					Boolean isUd = false;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if("F".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))){
							isFolder = true;
						} else if("U".equals(list.getSelectedRecords()[i].getAttributeAsString("flgUdFolder"))){
							isUd = true;
						}
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if(!isFolder && isUd) {
						new AssegnaStatoUdPopup("U") {
	
							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
								lGwtRestDataSource.extraparam.put("stato", object.getAttribute("statoUd"));
								lGwtRestDataSource.executecustom("aggiornaStatoDocumenti", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione stato effettuata con successo",
												"Tutti i record selezionati per l'assegnazione dello stato sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione dello stato sono andati in errore!", null);
									}
								});
								
								this.markForDestroy();
							}
						};
					} else if(isFolder && !isUd) {
						new AssegnaStatoUdPopup("F") {
	
							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								
								Record record = new Record();
								record.setAttribute("listaRecord", listaUdFolder);
								
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
								lGwtRestDataSource.extraparam.put("stato", object.getAttribute("statoUd"));
								lGwtRestDataSource.executecustom("aggiornaStatoDocumenti", record, new DSCallback() {
	
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", "Assegnazione stato effettuata con successo",
												"Tutti i record selezionati per l'assegnazione dello stato sono andati in errore!",
												"Alcuni dei record selezionati per l'assegnazione dello stato sono andati in errore!", null);
									}
								});
								
								this.markForDestroy();
							}
						};
					} else {
						Layout.addMessage(new MessageBean("Per questo tipo di azione devi selezionare solo documenti o solo fascicoli/aggregati di documenti",
								"", MessageType.WARNING));
					}
				}
			};
		}

		if (apposizioneCommentiMultiButton == null) {
			apposizioneCommentiMultiButton = new ArchivioMultiToolStripButton("archivio/note_commenti.png", this, I18NUtil.getMessages().apposizioneCommenti_menu_apri_title(), false) {

				@Override
				public boolean toShowForArchivio() {
					return abilApposizioneCommenti;
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if (listaUdFolder.getLength() == 1) {
						((ArchivioList) getList()).manageApposizioneCommenti(listaUdFolder.get(0));
					} else {
						
						String title = I18NUtil.getMessages().archivio_layout_apposizioneCommentiPopupMassivo_title();
						new ApposizioneCommentiPopup(title, null, null) {

							@Override
							public void onClickOkButton(Record object, final DSCallback callback) {
								RecordList listaUdFolderNoteAggiornate = new RecordList();
								for (int i = 0; i < listaUdFolder.getLength(); i++) {
									Record udFolder = listaUdFolder.get(i);
									String noteOld = udFolder.getAttribute("note");
									String noteInserita = object.getAttribute("note");
									String causaleAggNoteInserita = object.getAttribute("causaleAggNote");
									StringBuilder noteAggiornata = new StringBuilder();
									if (noteOld != null && !"".equals(noteOld)) {
										noteAggiornata.append(noteOld);
										noteAggiornata.append("\n");
									}
									noteAggiornata.append(noteInserita);
									// aggiorno aggiungendo la nota inserita alle note già presenti
									Record udFolderNoteAggiornate = new Record();
									udFolderNoteAggiornate.setAttribute("flgUdFolder", udFolder.getAttribute("flgUdFolder"));
									udFolderNoteAggiornate.setAttribute("idUdFolder", udFolder.getAttribute("idUdFolder"));
									if (noteAggiornata != null) {
										udFolderNoteAggiornate.setAttribute("note", noteAggiornata.toString());
									}
									if (causaleAggNoteInserita != null) {
										udFolderNoteAggiornate.setAttribute("causaleAggNote", causaleAggNoteInserita);
									}
									listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
								}
								Record lRecordSelezionatiNoteAggiornate = new Record();
								lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
								lGwtRestDataSource.executecustom("apposizioneCommenti", lRecordSelezionatiNoteAggiornate, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
												"Apposizione commenti effettuata con successo",
												"Tutti i record selezionati per l'apposizione commenti sono andati in errore!",
												"Alcuni dei record selezionati per l'apposizione commenti sono andati in errore!", callback);
									}
								});
							}
						};
					}
				}
			};
		}
		
		if (segnaComeVisionatoMultiButton == null) {
			segnaComeVisionatoMultiButton = new ArchivioMultiToolStripButton("postaElettronica/flgRicevutaLettura.png", this, I18NUtil.getMessages().segnaComeVisionato_menu_apri_title(), false) {
				
				@Override
				public boolean toShowForArchivio() {
					return abilSegnaComeVisionatoMultiButton;
				}
				
				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if (listaUdFolder.getLength() == 1) {
						((ArchivioList) getList()).segnaComeVisionato(listaUdFolder.get(0));
					} else {
						
						// Segna come visionato massivo
						
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);
						lGwtRestDataSource.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									
									RecordList listaUoDestNotifiche = response.getDataAsRecordList();
									
									String title = I18NUtil.getMessages().segnaComeVisionato_PopupMassivo_title();
									
									SegnaComeVisionatoPopup segnaComeVisionatoPopup = new SegnaComeVisionatoPopup(title, null, listaUoDestNotifiche) {

										@Override
										public void onClickOkButton(Record object, final DSCallback callback) {
											RecordList listaUdFolderNoteAggiornate = new RecordList();
											for (int i = 0; i < listaUdFolder.getLength(); i++) {
												Record udFolder = listaUdFolder.get(i);
												String noteInserita = object.getAttribute("note");
												Record udFolderNoteAggiornate = new Record();
												udFolderNoteAggiornate.setAttribute("flgUdFolder", udFolder.getAttribute("flgUdFolder"));
												udFolderNoteAggiornate.setAttribute("idUdFolder", udFolder.getAttribute("idUdFolder"));
												udFolderNoteAggiornate.setAttribute("note", noteInserita);
												listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
											}
											Record lRecordSelezionatiNoteAggiornate = new Record();
											lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
											lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));	
											lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
											lGwtRestDataSource.executecustom("segnaComeVisionato", lRecordSelezionatiNoteAggiornate, new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
															"Operazione effettuata con successo",
															"Tutti i record selezionati sono andati in errore!",
															"Alcuni dei record selezionati sono andati in errore!", callback);
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
			};
		}
		
		if (segnaComeArchiviatoMultiButton == null) {
			segnaComeArchiviatoMultiButton = new ArchivioMultiToolStripButton("archivio/archiviazione.png", this, I18NUtil.getMessages().segnaComeArchiviato_menu_apri_title(), false) {
				
				@Override
				public boolean toShowForArchivio() {
					return abilArchiviazione;
				}
				
				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUdFolder.add(list.getSelectedRecords()[i]);
					}
					if (listaUdFolder.getLength() == 1) {
						((ArchivioList) getList()).segnaComeArchiviato(listaUdFolder.get(0));
					} else {
						
						// Segna come archiviato massivo
						
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);
						String idNodo = getIdNode();
						lGwtRestDataSource.addParam("altriParametriIn", "TIPO_AZIONE|*|A|*|CI_NODO_SCRIVANIA|*|" + idNodo);
						lGwtRestDataSource.fetchData(null, new DSCallback() {
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									
									RecordList listaUoDestNotifiche = response.getDataAsRecordList();
									
									String title = I18NUtil.getMessages().segnaComeArchiviato_PopupMassivo_title();
									
									SegnaComeArchiviatoPopup segnaComeArchiviatoPopup = new SegnaComeArchiviatoPopup(title, null, listaUoDestNotifiche) {
										
										@Override
										public void onClickOkButton(Record object, final DSCallback callback) {
											RecordList listaUdFolderNoteAggiornate = new RecordList();
											for (int i = 0; i < listaUdFolder.getLength(); i++) {
												Record udFolder = listaUdFolder.get(i);
												String noteInserita = object.getAttribute("note");
												Record udFolderNoteAggiornate = new Record();
												udFolderNoteAggiornate.setAttribute("flgUdFolder", udFolder.getAttribute("flgUdFolder"));
												udFolderNoteAggiornate.setAttribute("idUdFolder", udFolder.getAttribute("idUdFolder"));
												udFolderNoteAggiornate.setAttribute("note", noteInserita);
												listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
											}
											Record lRecordSelezionatiNoteAggiornate = new Record();
											lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
											lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));		
											lRecordSelezionatiNoteAggiornate.setAttribute("idNodo", getIdNode());
											final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
											lGwtRestDataSource.executecustom("segnaComeArchiviato", lRecordSelezionatiNoteAggiornate, new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura",
															"Operazione effettuata con successo",
															"Tutti i record selezionati sono andati in errore!",
															"Alcuni dei record selezionati sono andati in errore!", callback);
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
			};
		}
		
		if (associazioneImgAProtocolloMultiButton == null) {
			associazioneImgAProtocolloMultiButton = new ArchivioMultiToolStripButton("postaElettronica/associaProtocolloPratica.png", this, "Associa immagine/i a protocollo") {

				@Override
				public boolean toShowForArchivio() {
					return abilAssociazioneImgAProtocollo;
				}
				
				@Override
				public void doSomething() {					
					manageAssociazioneImgAProtocolloMassiva();
				}
			};
		}
		
		/*********************************************
		 * OPERAZIONI MASSIVE RICHIESTE ACCESSO ATTI *
		 *********************************************/
		
		if (invioInApprovazioneMultiButton == null) {
			invioInApprovazioneMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/invioInApprovazione.png", this, "Manda in approvazione", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiInvioInApprovazione; 
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("INVIO_IN_APPROVAZIONE");
				}
			};
		}
		
		if (approvazioneMultiButton == null) {
			approvazioneMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/approvazione.png", this, "Approva", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiApprovazione;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("APPROVAZIONE");
				}
			};
		}
		
		if (invioEsitoVerificaArchivioMultiButton == null){
			invioEsitoVerificaArchivioMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/invioEsitoVerificaArchivio.png", this, "Invia esito verifica al rich.", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("INVIO_ESITO_VERIFICA_ARCHIVIO");
				}
			};
		}
		
		if (abilitaAppuntamentoDaAgendaMultiButton == null){
			abilitaAppuntamentoDaAgendaMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/abilitaAppuntamentoDaAgenda.png", this, "Abilita appuntamento da Agenda", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("ABILITA_APPUNTAMENTO_DA_AGENDA");
				}
			};
		}
		
		if (setAppuntamentoMultiButton == null){
			setAppuntamentoMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/setAppuntamento.png", this, "Fissa appuntamento", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiRegistrazioneAppuntamento;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("SET_APPUNTAMENTO");
				}
			};
		}
		
		if (annullamentoAppuntamentoMultiButton == null){
			annullamentoAppuntamentoMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/annullamentoAppuntamento.png", this, "Annulla appuntamento", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiAnnullamentoAppuntamento;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("ANNULLAMENTO_APPUNTAMENTO");
				}
			};
		}
		
		if (registraPrelievoMultiButton == null){
			registraPrelievoMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/registraPrelievo.png", this, "Registra prelievo effettivo", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiRegistrazionePrelievo;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("REGISTRA_PRELIEVO");
				}
			};
		}
		
		if (registraRestituzioneMultiButton == null){
			registraRestituzioneMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/registraRestituzione.png", this, "Registra restituzione", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiRegistrazioneRestituzione;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("REGISTRA_RESTITUZIONE");
				}
			};
		}
		
		if (annullamentoMultiButton == null){
			annullamentoMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/annullamento.png", this, "Elimina richiesta", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiAnullamentoRichiesta;
				}

				@Override
				public void doSomething() {					
					manageAzioneSuRichAccessoAttiMassiva("ANNULLAMENTO");
				}
			};
		}   
		
		if (stampaFoglioPrelievoMultiButton == null){
			stampaFoglioPrelievoMultiButton = new RichAccessoAttiMultiToolStripButton("richiesteAccessoAtti/azioni/stampaFoglioPrelievo.png", this, "Stampa foglio prelievo", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiStampaFoglioPrelievo;
				}

				@Override
				public void doSomething() {					
					manageAzioneStampaFoglioPrelievoMassiva();
				}
			};
		}
		
		if (eliminaRichAccessoAttiMultiButton == null) {
			eliminaRichAccessoAttiMultiButton = new RichAccessoAttiMultiToolStripButton("buttons/cestino.png", this, "Sposta in Eliminati", false) {

				@Override
				public boolean toShowForRichAccessoAtti() {
					return abilRichiesteAccessoAttiEliminazioneRichiesta;
				}

				@Override
				public void doSomething() {
					final EliminazionePopup eliminazionePopup = new EliminazionePopup() {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaUdFolder = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaUdFolder.add(list.getSelectedRecords()[i]);
							}
							Record record = new Record();
							record.setAttribute("listaRecord", listaUdFolder);
							record.setAttribute("motivo", _form.getValue("motivo"));
							record.setAttribute("sezioneAreaLav", codSezione);
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneRichAccessoAttiDaAreaLavoroDataSource");
							lGwtRestDataSource.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									massiveOperationCallback(response, listaUdFolder, "idUd", "protocollo",
											"Spostamento in Eliminati effettuato con successo",
											"Tutti i record selezionati per lo spostamento in Eliminati sono andati in errore!",
											"Alcuni dei record selezionati per lo spostamento in Eliminati sono andati in errore!", callback);
								}
							});
						}
					};
					eliminazionePopup.show();
				}
			};
		}
		
		/****************************
		 * OPERAZIONI MASSIVE EMAIL *
		 ****************************/
		if (archiviaEmailMultiButton == null) {
			archiviaEmailMultiButton = new EmailMultiToolStripButton("archivio/archiviazione.png", this,
					AurigaLayout.getParametroDB("LABEL_ARCHIVIAZIONE_EMAIL"), false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailChiusuraLavorazione;
					//return classifica != null && !classifica.startsWith("standard.archiviata");
				}

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}
					DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instance, listaEmail, true, true);
					operazioneRichiestaWindow.show();
				}
			};
		}

		if (annullaArchiviazioneEmailMultiButton == null) {
			annullaArchiviazioneEmailMultiButton = new EmailMultiToolStripButton("archivio/annullaArchiviazione.png", this, I18NUtil.getMessages()
					.annulla_archiviazione_label_button(), false) {
				
				@Override
				public boolean toShowForEmail() {
					return abilEmailRiaperturaLavorazione;
					//return classifica != null && classifica.startsWith("standard.archiviata");
				}

				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++)
						listaUdFolder.add(list.getSelectedRecords()[i]);

					DatiOperazioneRichiestaWindow operazioneRichiestaWindow = new DatiOperazioneRichiestaWindow(instance, listaUdFolder, true, false);
					operazioneRichiestaWindow.show();
				}
			};
		}
		
		/**
		 * Apposizione Tag & Commenti
		 */
		if (apposizioneTagCommentiMultiButton == null) {
			apposizioneTagCommentiMultiButton = new EmailMultiToolStripButton("postaElettronica/apposizione_tag_commenti.png", this,
					I18NUtil.getMessages().posta_elettronica_layout_apposizioneTagCommenti(), false) {
				
				@Override
				public boolean toShowForEmail() {
					return abilEmailApposizioneTagCommenti;
					//return true;
				}

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++){
						listaEmail.add(list.getSelectedRecords()[i]);
					}
				
					ApposizioneTagCommentiMailWindow lApposizioneTagCommentiMailWindow =
							new ApposizioneTagCommentiMailWindow("apposizione_tag_commenti_mail",listaEmail, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							doSearch();
						}
					});
				}
			};
		}

		if (assegnaEmailMultiButton == null) {
			assegnaEmailMultiButton = new EmailMultiToolStripButton("archivio/assegna.png", this, I18NUtil.getMessages().posta_elettronica_set_uo_competente(),
					false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailAssegnazione;
				}

				@Override
				public void doSomething() {

					
					final Menu creaAssegnaUO = new Menu(); 
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.ASSEGNA_UO_COMPETENTE.getValue()));
								
								// Scelta U.O. Standard 
								MenuItem assegnaMenuStandardItem = new MenuItem("Standard");
								assegnaMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										
										final RecordList listaEmail = new RecordList();
										for (int i = 0; i < list.getSelectedRecords().length; i++) {
											listaEmail.add(list.getSelectedRecords()[i]);
										}												
																			
										// Solo se il parametro RESTR_ASS_EMAIL_SOLO_SMISTATORI e' valorizzato passo la concatenazione delle prime 100 email 
										Record recordListaEmail = new Record();
										if(AurigaLayout.getParametroDB("RESTR_ASS_EMAIL_SOLO_SMISTATORI") != null && !AurigaLayout.getParametroDB("RESTR_ASS_EMAIL_SOLO_SMISTATORI").equalsIgnoreCase("")){	
											if(listaEmail != null && listaEmail.getLength() > 0) {
											      String listaIdEmail = "";
											      for (int i = 0; i < 100; i++) {
											        Record record = listaEmail.get(i);
											        if(record != null && record.getAttribute("idEmail") != null &&
											        		!"".equalsIgnoreCase(record.getAttribute("idEmail"))) {
												        String idEmail = record.getAttribute("idEmail");
												        if (i == 0) {
												          listaIdEmail = idEmail;
												        } else {
												          listaIdEmail = String.valueOf(listaIdEmail) + ";" + idEmail;
												        } 
											        }
											      } 
											      if (!listaIdEmail.equalsIgnoreCase(""))
											    	  recordListaEmail.setAttribute("idEmail", listaIdEmail); 
											 } 
										}

										final AssegnazioneEmailPopup assegnazioneEmailPopup = new AssegnazioneEmailPopup(recordListaEmail, listaDestinatariPreferiti) {

											@Override
											public void onClickOkButton(Record record, final DSCallback callback) {

												
												record.setAttribute("listaRecord", listaEmail);
												
												Layout.showWaitPopup( "Assegnazione in corso: potrebbe richiedere qualche secondo. Attendere…");
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource( "AssegnazioneEmailDataSource");
												lGwtRestDataSource.addParam("isMassivo", "1");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaEmail, "idEmail", "oggetto",
																			I18NUtil.getMessages().posta_elettronica_assegnazione_successo(),
																			I18NUtil.getMessages().posta_elettronica_assegnazione_errore_totale(),
																			I18NUtil.getMessages().posta_elettronica_assegnazione_errore_parziale(),
																			callback);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
											}
										};
										assegnazioneEmailPopup.show();

									}
								});
								creaAssegnaUO.addItem(assegnaMenuStandardItem);
								
								// Scelta U.O. Rapida
								MenuItem assegnaMenuRapidoItem = new MenuItem("Rapida");				

								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								
								if(success != null && success == true
										&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
									
									Menu scelteRapide = new Menu();

									for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
										
										Record currentRecord = listaDestinatariPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										 
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												
												RecordList listaAssegnazioni = new RecordList();
												Record recordAssegnazioni = new Record();
												recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
												recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
												listaAssegnazioni.add(recordAssegnazioni);

												Record record = new Record();
												record.setAttribute("listaRecord", listaEmail);
												record.setAttribute("listaAssegnazioni", listaAssegnazioni);
												
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_assegnazione_in_corso());
												GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AssegnazioneEmailDataSource");
												lGwtRestDataSource.addParam("isMassivo", "1");
												try {
													lGwtRestDataSource.addData(record, new DSCallback() {

														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															massiveOperationCallback(response, listaEmail, "idEmail", "oggetto",
																	I18NUtil.getMessages().posta_elettronica_assegnazione_successo(),
																	I18NUtil.getMessages().posta_elettronica_assegnazione_errore_totale(),
																	I18NUtil.getMessages().posta_elettronica_assegnazione_errore_parziale(), null);
														}
													});
												} catch (Exception e) {
													Layout.hideWaitPopup();
												}
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
										
									}
									
									assegnaMenuRapidoItem.setSubmenu(scelteRapide);
								} else {
									assegnaMenuRapidoItem.setEnabled(false);
								}
								creaAssegnaUO.addItem(assegnaMenuRapidoItem);
								
								creaAssegnaUO.showContextMenu();
								}
							}
						}, new DSRequest());
				}
			};
		}
		
		if (annullaAssegnaEmailMultiButton == null) {
			annullaAssegnaEmailMultiButton = new EmailMultiToolStripButton("archivio/annulla_uo_competente.png", this, I18NUtil.getMessages()
					.posta_elettronica_annulla_uo_competente(), false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailAnnullamentoAssegnazione;
//					if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_ASSEGNAZIONE_EMAIL")) {
//						return false;
//					}
//					return classifica != null && classifica.startsWith("standard.arrivo");
				}

				@Override
				public void doSomething() {

					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}
					
					Record record = new Record();
					record.setAttribute("listaAnnullamenti", listaEmail);
					
					MotiviOperazionePopup motiviPopup = new MotiviOperazionePopup(record, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							manageAnnullamentoMassivoAssegnazioneUOCompetente(object);
							
						}
					});
					motiviPopup.show();
				}
			};
		}

		if (inoltraEmailMultiButton == null) {
			inoltraEmailMultiButton = new EmailMultiToolStripButton("postaElettronica/inoltro.png", this, "Inoltra", false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailInoltro;
//					if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_FORWARD_EMAIL")) {
//						return false;
//					}
//					return true;
				}

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}
					final Record record = new Record();
					record.setAttribute("listaRecord", listaEmail);
					final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
					lGwtRestServiceInvioNotifica.call(record, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							InoltroMailWindow lInoltroEmailWindow = new InoltroMailWindow("inoltro", record, instance, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									doSearch();
								}
							});
						}
					});
				}
			};
		}

		// PRESA IN CARICO MAIL
		if (presaInCaricoEmailMultiButton == null) {
			presaInCaricoEmailMultiButton = new EmailMultiToolStripButton("postaElettronica/prendiInCarico.png", this, "Prendi in Carico", false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailPresaInCarico;
//					if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
//						return false;
//					}
//					return ((Layout.isPrivilegioAttivo("EML/LK/ASS")) || (Layout.isPrivilegioAttivo("EML/PLK/ASS")));
				}

				@Override
				public void doSomething() {
					final OperazioniPerEmailPopup presaInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.PRESA_IN_CARICO.getValue(), null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaEmail = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaEmail.add(list.getSelectedRecords()[i]);
							}
							Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_presa_in_carico_in_corso());
							buildTipoOperazione(listaEmail, null, getMotivo(), true, TipoOperazioneMail.PRESA_IN_CARICO.getValue());
							markForDestroy();
						}
					};
					presaInCaricoEmailPopup.show();
				}
			};
		}

		// MANDA IN CARICO MAIL
		if (messaInCaricoEmailMultiButton == null) {
			messaInCaricoEmailMultiButton = new EmailMultiToolStripButton("postaElettronica/mettiInCarico.png", this, "Metti in Carico", false) {

				public boolean toShowForEmail() {
					return abilEmailMessaInCarico;
//					if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
//						return false;
//					}
//					return Layout.isPrivilegioAttivo("EML/PLK/ASS");
				}

				@Override
				public void doSomething() {
					
					final Menu creaInCarico = new Menu();
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.METTI_IN_CARICO.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
//								final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.METTI_IN_CARICO.getValue()));								
								
								// Messa in carico Standard 
								MenuItem messaInCaricoMenuStandardItem = new MenuItem("Standard");
								messaInCaricoMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										final OperazioniPerEmailPopup mettiInCaricoEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.MESSA_IN_CARICO.getValue(), listaDestinatariPreferiti) {

											@Override
											public void onClickOkButton(final DSCallback callback) {
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
												buildTipoOperazione(listaEmail, getUtente(), getMotivo(), true, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
												markForDestroy();
											}
										};
										mettiInCaricoEmailPopup.show();
									}
								});
								creaInCarico.addItem(messaInCaricoMenuStandardItem);
								
								// Messa in carico Rapida
								MenuItem messaInCaricoRapidaItem = new MenuItem("Rapida");				

								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								
								if(success != null && success == true
										&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
									
									Menu scelteRapide = new Menu();

									for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
										
										Record currentRecord = listaDestinatariPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
//										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										 
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												String motivo = null;
												String userLockFor = idDestinatarioPreferito;
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
												buildTipoOperazione(listaEmail, userLockFor, motivo, true, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
										
									}
									
									messaInCaricoRapidaItem.setSubmenu(scelteRapide);
								} else {
									messaInCaricoRapidaItem.setEnabled(false);
								}
								creaInCarico.addItem(messaInCaricoRapidaItem);
								
								creaInCarico.showContextMenu();
								}
							}
						}, new DSRequest());
					
				}
			};
		}

		// MANDA IN APPROVAZIONE
		if (mandaInApprovazioneMultiButton == null) {
			mandaInApprovazioneMultiButton = new EmailMultiToolStripButton("postaElettronica/manda_in_approvazione.png", this, "Manda in approvazione", false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailInvioInApprovazione;
//					if(AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
//						return false;
//					}
//					return ((Layout.isPrivilegioAttivo("EML/LK/ASS")) || (Layout.isPrivilegioAttivo("EML/PLK/ASS")));
				}

				@Override
				public void doSomething() {
					
					final Menu creaInApprovazioneMenu = new Menu();
					
					Record recordDestPref = new Record();						
					RecordList listaAzioniRapide = new RecordList();
					Record recordAzioneRapida = new Record();						
					recordAzioneRapida.setAttribute("azioneRapida", AzioniRapide.MANDA_IN_APPROVAZIONE.getValue());
					listaAzioniRapide.add(recordAzioneRapida);
					recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);					
					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
					lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref, new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record destinatariPreferiti = response.getData()[0];
//								final RecordList listaDestinatariPreferiti = destinatariPreferiti.getAttributeAsRecordList("listaUtentiPreferitiMail");
								final RecordList listaDestinatariPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.MANDA_IN_APPROVAZIONE.getValue()));
								
								// Manda in approvazione Standard 
								MenuItem mandaInApprovazioneStandardItem = new MenuItem("Standard");
								mandaInApprovazioneStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										final OperazioniPerEmailPopup mandInApprovazioneEmailPopup = new OperazioniPerEmailPopup(
												TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue(), listaDestinatariPreferiti) {

											@Override
											public void onClickOkButton(final DSCallback callback) {
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												String motivo = getMotivo();
												if (motivo != null && !"".equalsIgnoreCase(motivo)) {
													motivo = "[APPROVAZIONE]" + motivo;
												} else {
													motivo = "[APPROVAZIONE]";
												}												
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_approvazione_in_corso());
												buildTipoOperazione(listaEmail, getUtente(), motivo, true, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
												markForDestroy();
											}
										};
										mandInApprovazioneEmailPopup.show();
										
									}
								});
								creaInApprovazioneMenu.addItem(mandaInApprovazioneStandardItem);
								
								// Manda in approvazione Rapida
								MenuItem mandaInApprovazioneRapidaItem = new MenuItem("Rapida");				

								Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
								
								
								if(success != null && success == true
										&& listaDestinatariPreferiti != null && !listaDestinatariPreferiti.isEmpty()){
									
									Menu scelteRapide = new Menu();

									for(int i=0; i < listaDestinatariPreferiti.getLength();i++){
										
										Record currentRecord = listaDestinatariPreferiti.get(i);
										final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
//										final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
										final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
										 
										MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
										currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												
												final RecordList listaEmail = new RecordList();
												for (int i = 0; i < list.getSelectedRecords().length; i++) {
													listaEmail.add(list.getSelectedRecords()[i]);
												}
												String motivo = "[APPROVAZIONE]";
												String userLockFor = idDestinatarioPreferito;
												Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_messa_in_carico_in_corso());
												buildTipoOperazione(listaEmail, userLockFor, motivo, true, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
												
											}
										});
										scelteRapide.addItem(currentRapidoItem);
										
									}
									
									mandaInApprovazioneRapidaItem.setSubmenu(scelteRapide);
								} else {
									mandaInApprovazioneRapidaItem.setEnabled(false);
								}
								creaInApprovazioneMenu.addItem(mandaInApprovazioneRapidaItem);
								
								creaInApprovazioneMenu.showContextMenu();
								}
							}
						}, new DSRequest());
				}
			};
		}

		// RILASCIA MAIL
		if (rilasciaCaricoEmailMultiButton == null) {
			rilasciaCaricoEmailMultiButton = new EmailMultiToolStripButton("postaElettronica/rilascia.png", this, "Rilascia Mail", false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailRilascio;
//					if (AurigaLayout.getParametroDBAsBoolean("DISATTIVA_PRESA_IN_CARICO_EMAIL")) {
//						return false;
//					}
//					return Layout.isPrivilegioAttivo("EML/ULK/ASS");
				}

				@Override
				public void doSomething() {
					final OperazioniPerEmailPopup rilascioEmailPopup = new OperazioniPerEmailPopup(TipoOperazioneMail.RILASCIA.getValue(), null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							
							final RecordList listaEmail = new RecordList();
							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaEmail.add(list.getSelectedRecords()[i]);
							}
							Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_rilascio_in_corso());
							rilascio(listaEmail, null, getMotivo(), true);
							markForDestroy();
						}
					};
					rilascioEmailPopup.show();
				}
			};
		}
		if (eliminaEmailMultiButton == null) {
			eliminaEmailMultiButton = new EmailMultiToolStripButton("buttons/cestino.png", this, "Eliminazione", false) {

				@Override
				public boolean toShowForEmail() {
					return abilEmailEliminazione;
					//return Layout.isPrivilegioAttivo("EML/DEL");
				}

				@Override
				public void doSomething() {
					final RecordList listaEmail = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaEmail.add(list.getSelectedRecords()[i]);
					}
					final Record record = new Record();
					record.setAttribute("listaRecord", listaEmail);
					Layout.showWaitPopup("L’operazione potrebbe impiegare alcuni secondi…");
					GWTRestDataSource lGwtRestServiceEliminaEmail = new GWTRestDataSource("EliminazioneMassivaEmailDataSource");
					try {
						lGwtRestServiceEliminaEmail.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								massiveOperationCallback(response, listaEmail, "idEmail", "oggetto", "Eliminazione effettuata con successo",
										"Tutti i record selezionati per l'eliminazione sono andati in errore!",
										"Alcuni dei record selezionati per l'eliminazione sono andati in errore!", null);
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			};
		}
		if(chiudiFascicoloMultiButton == null) {
			chiudiFascicoloMultiButton = new ArchivioMultiToolStripButton("archivio/archiviazione.png", this, "Chiusura fascicolo/aggregato", false ) {
				
				@Override
				public boolean toShowForArchivio() {
					return abilChiudiFascicoloMultiButton;
//					if (idNode != null && idNode.startsWith("F.") ) {
//						return Layout.isPrivilegioAttivo("GRD/FLD/C");
//					}
//					return false;
				} 
				
				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isSoloFascicoli = true;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);	
						}
						else {
							isSoloFascicoli = false;
							break;
						}
					}
					if (isSoloFascicoli) {
						SC.ask("Sei sicuro di voler chiudere i fascicoli ?", new BooleanCallback() {
							@Override 
							public void execute(Boolean value) {  
								if (value) {
									Record record = new Record();
									record.setAttribute("listaRecord", listaUdFolder);
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
									lGwtRestDataSource.executecustom("chiudiFascicolo", record, new DSCallback() {

										@Override public void execute(DSResponse response, Object rawData, DSRequest request) { 
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", 
													"Tutti i fascicoli/aggregati selezionati sono stati chiusi", 
													"Tutti i fascicoli selezionati sono andati in errore!",
													"Alcuni dei fascicoli selezionati sono andati in errore!", null); 
										} 
									}); 
								} 
							} 
						}); 
					} else
						Layout.addMessage(new MessageBean("La selezione comprende anche dei documenti: operazione di chiusura non consentita", "", MessageType.ERROR));
				}	
			}; 
		}
		
		if(riapriFascicoloMultiButton == null) {
			riapriFascicoloMultiButton = new ArchivioMultiToolStripButton("archivio/annullaArchiviazione.png", this, "Riapertura fascicolo/aggregato", false ) {
				
				@Override
				public boolean toShowForArchivio() {
					return abilRiapriFascicoloMultiButton;
//					if (idNode != null && idNode.startsWith("F.") ) {
//						return Layout.isPrivilegioAttivo("GRD/FLD/C");
//					}
//					return false;
				} 
				
				@Override
				public void doSomething() {
					final RecordList listaUdFolder = new RecordList();
					boolean isSoloFascicoli = true;
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						if (list.getSelectedRecords()[i].getAttribute("flgUdFolder").equalsIgnoreCase("F")) {
							listaUdFolder.add(list.getSelectedRecords()[i]);	
						}
						else {
							isSoloFascicoli = false;
							break;
						}
					}
					if (isSoloFascicoli) {
						SC.ask("Sei sicuro di voler riaprire i fascicoli ?", new BooleanCallback() {
							@Override 
							public void execute(Boolean value) {  
								if (value) {
									Record record = new Record();
									record.setAttribute("listaRecord", listaUdFolder);
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioneMassivaArchivioDataSource");
									lGwtRestDataSource.executecustom("riapriFascicolo", record, new DSCallback() {

										@Override public void execute(DSResponse response, Object rawData, DSRequest request) { 
											massiveOperationCallback(response, listaUdFolder, "idUdFolder", "segnatura", 
													"Tutti i fascicoli/aggregati selezionati sono stati riaperti", 
													"Tutti i fascicoli selezionati sono andati in errore!",
													"Alcuni dei fascicoli selezionati sono andati in errore!", null); 
										} 
									}); 
								} 
							} 
						}); 
					} else
						Layout.addMessage(new MessageBean("La selezione comprende anche dei documenti: operazione di riapertura non consentita", "", MessageType.ERROR));
				}
				
			}; 
		}
		
		return new MultiToolStripButton[] {
				
				// ARCHIVIO
				apposizioneFirmaMultiButton,
				protocollaEFirmaMultiButton,
				rifiutoApposizioneFirmaMultiButton,
				vistoMultiButtonApposizione,
				vistoMultiButtonRifiuto,
				confermaAssegnazioneMultiButton, 
				modificaPreassegnazioneMultiButton,
				inserisciInAttoAutMultiButton, 
				prendiInCaricoMultiButton, 
				assegnaMultiButton, 
				restituisciMultiButton,
				smistaMultiButton,
				smistaCCMultiButton, 
				classificaMultiButton, 
				fascicolaMultiButton, 
				organizzaMultiButton, 
				condividiMultiButton, 
//				archiviazioneMultiButton, 
				annullaArchiviazioneMultiButton, 
				aggiungiAPreferitiMultiButton,
				rimuoviDaPreferitiMultiButton, 
				assegnaRiservatezzaMultiButton, 
				rimuoviRiservatezzaMultiButton, 
				ripristinaMultiButton, 
				apposizioneCommentiMultiButton, 
				stampaEtichettaMultiButton, 
				downloadDocZipMultiButton,
				modificaStatoDocMultiButton,
				chiudiFascicoloMultiButton,
				riapriFascicoloMultiButton,
				modificaTipologiaMultiButton,
				segnaComeVisionatoMultiButton,
				segnaComeArchiviatoMultiButton,
				eliminaMultiButton,
				eliminaImgScansioneMultiButton,
				associazioneImgAProtocolloMultiButton,
//				versaInArchivioStoricoFascicoloMultiButton,
				
				// RICHIESTE ACCESSO ATTI				
				invioInApprovazioneMultiButton,
				approvazioneMultiButton,
				invioEsitoVerificaArchivioMultiButton,	
				abilitaAppuntamentoDaAgendaMultiButton,
				setAppuntamentoMultiButton,              
				annullamentoAppuntamentoMultiButton,   
				registraPrelievoMultiButton,            
				registraRestituzioneMultiButton,        
				annullamentoMultiButton,   
				stampaFoglioPrelievoMultiButton,
				eliminaRichAccessoAttiMultiButton,
				
				// EMAIL
				archiviaEmailMultiButton, 
				annullaArchiviazioneEmailMultiButton, 
				assegnaEmailMultiButton, 
				annullaAssegnaEmailMultiButton, 
				inoltraEmailMultiButton, 
				presaInCaricoEmailMultiButton,
				messaInCaricoEmailMultiButton, 
				mandaInApprovazioneMultiButton, 
				rilasciaCaricoEmailMultiButton, 
				apposizioneTagCommentiMultiButton,
				eliminaEmailMultiButton
		};
	}

	protected void manageAssociazioneImgAProtocolloMassiva() {
		String listaIdUDScansioni  = "";
		
		if (list.getSelectedRecords().length < 1) {
			Layout.addMessage(new MessageBean("Nessun record selezionato", "", MessageType.WARNING));
			return;
		}
		
		for (int i = 0; i < list.getSelectedRecords().length; i++) {
			String idUd = list.getSelectedRecords()[i].getAttribute("idUdFolder");
			listaIdUDScansioni = listaIdUDScansioni + idUd + ";"; 
		}
		
		final String listaIdUDScansioniFinal = listaIdUDScansioni;
		
	    final CercaUDPopup lCercaUDPopup = new CercaUDPopup() {
			
			@Override
			public void onClickButton() {
				
				final String idUd = getIdUd();	
				final String nroProt = getAnnoProt();
				final String annoProt = getNumProt();
				
				if(idUd!=null && !"".equalsIgnoreCase(idUd)) {
					apriProtocolloAssociatoImg(listaIdUDScansioniFinal, idUd, nroProt, annoProt, _window);
				}else if(nroProt!=null && !"".equalsIgnoreCase(nroProt) && annoProt!=null && !"".equalsIgnoreCase(annoProt)) {
					cercaProtocolloEvent(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							if(object.getAttribute("idUd")!= null && !"".equalsIgnoreCase(object.getAttribute("idUd"))) {
								apriProtocolloAssociatoImg(listaIdUDScansioniFinal, object.getAttribute("idUd"), nroProt, annoProt, _window);
							}else {
								SC.warn("Protocollo inesistente");
							}							
						}
					});
				}else {
					SC.warn("Protocollo inesistente");
				}								
			}
		};
		lCercaUDPopup.show();
		
		/**
		 * record.setAttribute("dataAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValue("dataAppuntamento") : null);
				record.setAttribute("orarioAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValueAsString("orarioAppuntamento") : null);
				record.setAttribute("dataPrelievo", datiPrelievoForm != null ? datiPrelievoForm.getValue("dataPrelievo") : null);
				record.setAttribute("dataRestituzione", datiRestituzioneForm != null ? datiRestituzioneForm.getValue("dataRestituzione") : null);
				record.setAttribute("motivi", motiviForm != null ? motiviForm.getValue("motivi") : null );
				GWTRestService<Record, Record> lSetAzioneSuRichAccessoAttiDataSource = new GWTRestService<Record, Record>("SetAzioneSuRichAccessoAttiDataSource");
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
				try{
					lSetAzioneSuRichAccessoAttiDataSource.addData(record, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								boolean success = manageErroreMassivoAzioneRichiestaAtti(record);
								azioneSuRichAccessoAttiPopup.markForDestroy();
							}
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
		 * 
		 * 
		 * 
		 * 
		 * */
	}

	public void scaricaFile(String display, String uri, String remoteUri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void massivePrintLabelCallback(GWTRestService<Record, Record> lGwtRestDataSource, DSResponse response, RecordList lista, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			int numFascicoli = data.getAttributeAsInt("numFascicoli");
			boolean printEtichetta = data.getAttributeAsStringArray("etichette") != null && data.getAttributeAsStringArray("etichette").length > 0;

			if (lista.getLength() == numFascicoli)
				Layout.addMessage(new MessageBean("La selezione comprende solo fascicoli. La stampa etichetta indirizzi è prevista solo per i documenti,", "",
						MessageType.ERROR));

			else if (errorMessages != null) {
				Layout.addMessage(new MessageBean(data.getAttributeAsMap("errorMessages").get("error").toString(), "", MessageType.ERROR));
			} else if (printEtichetta) {
				Record record = new Record();
				record.setAttribute("formatoExport", "PDF");
				record.setAttribute("fields", data.getAttributeAsStringArray("etichette"));
				lGwtRestDataSource.call(record, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						String filename = "Results." + "PDF";
						String uri = object.getAttribute("tempFileOut");
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(uri));
					}
				});
			} else
				Layout.addMessage(new MessageBean("Nessun documento da stampare", "", MessageType.INFO));
		}
	}

	public boolean controlloButtonArchiviaMassivo() {
		return AurigaLayout.getParametroDBAsBoolean("HIDE_ARCH_DOC");
	}

	public abstract class ArchivioMultiToolStripButton extends MultiToolStripButton {

		public ArchivioMultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle) {
			super(pIcon, pLayout, pTitle);
		}

		public ArchivioMultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle, boolean pToShowTitle) {
			super(pIcon, pLayout, pTitle, pToShowTitle);
		}

		public abstract void doSomething();

		public abstract boolean toShowForArchivio();

		@Override
		public boolean toShow() {
			if(azione != null && (azione.equals("archivio") || azione.equals("emergenza"))) {
				if (idNode != null && (idNode.startsWith("FD.") || idNode.startsWith("D.") || idNode.startsWith("F."))) {
					return toShowForArchivio();
				}
			} 
			return false;			
		}

	}
	
	public abstract class RichAccessoAttiMultiToolStripButton extends MultiToolStripButton {

		public RichAccessoAttiMultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle) {
			super(pIcon, pLayout, pTitle);
		}

		public RichAccessoAttiMultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle, boolean pToShowTitle) {
			super(pIcon, pLayout, pTitle, pToShowTitle);
		}

		public abstract void doSomething();

		public abstract boolean toShowForRichAccessoAtti();

		@Override
		public boolean toShow() {
			if(azione != null && azione.equals("richiestaAccessoAtti")) {
				if (idNode != null && idNode.startsWith("D.")) {
					return toShowForRichAccessoAtti();
				}
			}
			return false;
		}

	}
	
	public abstract class EmailMultiToolStripButton extends MultiToolStripButton {

		public EmailMultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle) {
			super(pIcon, pLayout, pTitle);
		}

		public EmailMultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle, boolean pToShowTitle) {
			super(pIcon, pLayout, pTitle, pToShowTitle);
		}

		public abstract void doSomething();

		public abstract boolean toShowForEmail();

		@Override
		public boolean toShow() {
			if (idNode != null && idNode.startsWith("E.")) {
				return toShowForEmail();
			}
			return false;
		}
	}

	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						recordsToSelect[rec++] = list.getRecordIndex(record);
						if (idNode != null && idNode.startsWith("E.")) {
							errorMsg += "<br/>Mail inviata il " + record.getAttribute("tsInvioClient") + 
										" da " + (record.getAttribute("flgIo").equals("O") ? record.getAttribute("casellaRicezione") : record.getAttribute("accountMittente")) + 
										": " + errorMessages.get(record.getAttribute(pkField));
						} else {
							Record recordErrore = new Record();
							if ("U".equals(record.getAttribute("flgUdFolder"))) {
								recordErrore.setAttribute("idError", getEstremiUdFromLayout(record));
								errorMsg += "<br/>" + getEstremiUdFromLayout(record) + ": " + errorMessages.get(record.getAttribute(pkField));
							} else if ("F".equals(record.getAttribute("flgUdFolder"))) {
								recordErrore.setAttribute("idError", getEstremiFolderFromLayout(record));
								errorMsg += "<br/>" + getEstremiFolderFromLayout(record) + ": " + errorMessages.get(record.getAttribute(pkField));
							} else {
								recordErrore.setAttribute("idError", record.getAttribute(nameField));
								errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));							
							}
							recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
							listaErrori.add(recordErrore);
						}
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null && !"".equals(errorMsg)) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				if (successMessage != null && !"".equalsIgnoreCase(successMessage)) {
					Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				}
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	public void doSearchAfterFirma() {
			
		//Eseguo una ricerca nella lista
		doSearch(); 
	}

	
	//TODO Togliere e usare solo doSearchFirma?
	public void doSearchAfterFirma(List<String> idUdDocumentiInError) {
		
		//Eseguo una ricerca nella lista
		doSearch(); 	
		
		//TODO RIMUOVERE PERCHE ESEGUITO DALLA STORE?
		// Federico Cacco 13.01.2016
		// Mantis 41
		// Rimuovo dalla scrivania dei file da firmare i record elaborati correttamente
//		if ("D.11".equalsIgnoreCase(idNode)) {
//			final RecordList listaRecordDaRimuovere = new RecordList();
//			// Rimuovo solo i record per cui l'elaborazione è andata a buon fine
//			for (Record lRecordSelected : getList().getSelectedRecords()) {
//				String idUd = lRecordSelected.getAttribute("idUdFolder");
//				if (!idUdDocumentiInError.contains(idUd)) {
//					// Il record è stato elaborato con successo, va eliminato
//					Record record = new Record();
//					record.setAttribute("idUdFolder", idUd);
//					record.setAttribute("flgUdFolder", "U");
//					listaRecordDaRimuovere.add(record);
//				}
//			}
//			// Elimino solo se sono nella scrivania dei documenti da firmare
//			if (listaRecordDaRimuovere.getLength() > 0) {
//				// Ci sono record da eliminare
//				Record record = new Record();
//				record.setAttribute("listaRecord", listaRecordDaRimuovere);
//				record.setAttribute("motivo", "");
//				record.setAttribute("sezioneAreaLav", "DFI");
//				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneDaAreaLavoroDataSource");
//				lGwtRestDataSource.addData(record, new DSCallback() {
//
//					@Override
//					public void execute(DSResponse response, Object rawData, DSRequest request) {
//						massiveOperationCallback(response, listaRecordDaRimuovere, "idUdFolder", "nome", "",
//								"Non è stato possibile eliminare tutti i record firmati. Effettuare l'operazione manualmente",
//								"Non è stato possibile eliminare tutti i record firmati. Effettuare l'operazione manualmente", null);
//						doSearch();
//					}
//				});
//			} else {
//				doSearch();
//			}
//		} else {
//			doSearch();
//		}
	}
	
	//TODO TOgliere e usare solo doSearchFirma?
	public void doSearchAfterFirmaConEsitoAzione() {
		
		//Eseguo una ricerca nella lista
		doSearch(); 
		
		
		//TODO TOGLIERE PERCHE ESEGUITO DALLA STORE
//		if(listaRecordUd != null && listaRecordUd.getAttribute("idUd") != null){
//			// Elimino l'UD ritornata dal popup di selezione dell'azione successiva
//			
//			/*
//			 * Creo un recordList con il codice UD del record da eliminare
//			 * perchè è stato elaborato con successo
//			 */
//			final RecordList listaRecordDaRimuovere = new RecordList();
//			Record record = new Record();
//			record.setAttribute("idUdFolder", listaRecordUd.getAttribute("idUd"));
//			record.setAttribute("flgUdFolder", "U");
//			listaRecordDaRimuovere.add(record);
//			
//			//Creo il record da inviare al datasource
//			Record recordToSend = new Record();
//			recordToSend.setAttribute("listaRecord", listaRecordDaRimuovere);
//			recordToSend.setAttribute("motivo", "");
//			recordToSend.setAttribute("sezioneAreaLav", "DFI");
//			
//			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneDaAreaLavoroDataSource");
//			lGwtRestDataSource.addData(recordToSend, new DSCallback() {
//
//				@Override
//				public void execute(DSResponse response, Object rawData, DSRequest request) {
//					massiveOperationCallback(response, listaRecordDaRimuovere, "idUdFolder", "nome", "",
//							"Non è stato possibile eliminare tutti i record firmati. Effettuare l'operazione manualmente",
//							"Non è stato possibile eliminare tutti i record firmati. Effettuare l'operazione manualmente", null);
//					doSearch();
//				}
//			});
//		}
	}
	
	
	
	public void doSearchAfterApposizioneVisto() {
		
		//Eseguo una ricerca nella lista
		doSearch(); 
		
		//TODO Cancellare perchè eseguito dalla stored??
		// Rimuovo dalla scrivania dei file su cui apporre il visto i record elaborati correttamente
		
		// Elimino solo se sono nella scrivania dei documenti da vistare
//		if ("D.VISTARE".equalsIgnoreCase(idNode)) { // Documenti da vistare
////			final RecordList listaRecordDaRimuovere = new RecordList();
////			// Rimuovo solo i record per cui l'elaborazione è andata a buon fine
////			for (Record lRecordSelected : getList().getSelectedRecords()) {
////				String idUd = lRecordSelected.getAttribute("idUdFolder");
////				if (!idUdDocumentiInError.contains(idUd)) {
////					// Il record è stato elaborato con successo, va eliminato
////					Record record = new Record();
////					record.setAttribute("idUdFolder", idUd);
////					record.setAttribute("flgUdFolder", "U");
////					listaRecordDaRimuovere.add(record);
////				}
////			}
//			
//			if (listaRecordUd.getLength() > 0) {
//				
//				// Ci sono record da eliminare
//				Record record = new Record();
//				record.setAttribute("listaRecord", listaRecordUd);
//				record.setAttribute("motivo", "");
//				//La sezione DAV è quella relativa al "Da apporre con visto" indicata nella relativa store
//				record.setAttribute("sezioneAreaLav", "DAV"); 
//				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("EliminazioneDaAreaLavoroDataSource");
//				lGwtRestDataSource.addData(record, new DSCallback() {
//
//					@Override
//					public void execute(DSResponse response, Object rawData, DSRequest request) {
//						massiveOperationCallback(response, listaRecordUd, "idUdFolder", "nome", "",
//								"Non è stato possibile eliminare tutti i record in cui è stato apposto il visto. Effettuare l'operazione manualmente",
//								"Non è stato possibile eliminare tutti i record in cui è stato apposto il visto. Effettuare l'operazione manualmente", null);
//						doSearch();
//					}
//				});
//			} else {
//				doSearch();
//			}
//		} else {
//			doSearch();
//		}
	}

	@Override
	public void setMultiselect(Boolean multiselect) {
		
		super.setMultiselect(multiselect);
		newButton.hide();
		if (idNode != null && ("D.BOZZE".equals(idNode) || "D.DIA".equals(idNode)) && Layout.isPrivilegioAttivo("GRD/UD/IUD")) {
			newUdBozzeButton.show();
		} else {
			newUdBozzeButton.hide();
		}
		if (classifica != null && !classifica.equals("standard.eliminata") && Layout.isPrivilegioAttivo("EML/INS")) {
			nuovoMessaggioButton.show();
		} else {
			nuovoMessaggioButton.hide();
		}
		if(idNode != null &&  idNode.equalsIgnoreCase("D.RAA.I")){
			nuovaRichiestaAttoButton.show();
		}else{
			nuovaRichiestaAttoButton.hide();
		}
		if(azione != null &&  azione.equalsIgnoreCase("modelliDoc")){
			newModelloDocButton.show();
		}else{
			newModelloDocButton.hide();
		}
		// if (idNode != null && idNode.startsWith("E.")) {
		// firmaEmailButton.show();
		// } else {
		// firmaEmailButton.hide();
		// }
	}

	@Override
	protected ToolStripButton[] getCustomNewButtons() {
		
		newUdBozzeButton = new ToolStripButton();
		newUdBozzeButton.setIcon("archivio/newUd.png");
		newUdBozzeButton.setIconSize(16);
		newUdBozzeButton.setPrompt(I18NUtil.getMessages().archivio_list_newUdButton_prompt());
		newUdBozzeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				if(AurigaLayout.getImpostazioniDocumentoAsBoolean("skipSceltaTipologiaBozza")) {
					String idTipoDoc = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoBozza");
					String nomeTipoDoc = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoBozza");
					Boolean flgTipoDocConVie = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgTipoDocConVieBozza");
					Boolean flgOggettoNonObblig = AurigaLayout.getImpostazioniDocumentoAsBoolean("flgOggettoNonObbligBozza");											
					Map<String, Object> initialValues = new HashMap<String, Object>();
					if (idTipoDoc != null && !"".equals(idTipoDoc)) {
						initialValues.put("tipoDocumento", idTipoDoc);
						initialValues.put("nomeTipoDocumento", nomeTipoDoc);
						initialValues.put("flgTipoDocConVie", flgTipoDocConVie);				
						initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
					}
					ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, initialValues, null);
					protocollazioneDetailBozze.nuovoDettaglio(instance, initialValues);
					if (AurigaLayout.getIsAttivaAccessibilita()) {
						protocollazioneDetailBozze.setTabIndex(-1);
						protocollazioneDetailBozze.setCanFocus(false);
						ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailBozze, "menu/scrivania.png", nomeEntita,
								"Nuovo documento bozza");
						window.show();
					} else {
					changeDetail(new GWTRestDataSource("ProtocolloDataSource"), protocollazioneDetailBozze);
					}
					newMode();
				} else {
					String idTipoDocDefault = AurigaLayout.getImpostazioniDocumento("idTipoDocumentoBozza");
//					String descTipoDocDefault = AurigaLayout.getImpostazioniDocumento("descTipoDocumentoBozza");				
					AurigaLayout.apriSceltaTipoDocPopup(false, idTipoDocDefault, null, null, new ServiceCallback<Record>() {
		
						@Override
						public void execute(Record lRecordTipoDoc) {
		
							String idTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
							String nomeTipoDoc = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
							Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
							Boolean flgOggettoNonObblig = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgOggettoNonObblig") : null;																		
							Map<String, Object> initialValues = new HashMap<String, Object>();
							if (idTipoDoc != null && !"".equals(idTipoDoc)) {
								initialValues.put("tipoDocumento", idTipoDoc);
								initialValues.put("nomeTipoDocumento", nomeTipoDoc);
								initialValues.put("flgTipoDocConVie", flgTipoDocConVie);				
								initialValues.put("flgOggettoNonObblig", flgOggettoNonObblig);
							}
							ProtocollazioneDetailBozze protocollazioneDetailBozze = ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, initialValues, null);
							protocollazioneDetailBozze.nuovoDettaglio(instance, initialValues);
							if (AurigaLayout.getIsAttivaAccessibilita()) {
								protocollazioneDetailBozze.setTabIndex(-1);
								protocollazioneDetailBozze.setCanFocus(false);
								ProtocollazioneWindow window = new ProtocollazioneWindow(protocollazioneDetailBozze, "menu/scrivania.png", nomeEntita,
										"Nuovo documento bozza");
								window.show();
							} else {
							changeDetail(new GWTRestDataSource("ProtocolloDataSource"), protocollazioneDetailBozze);
							}
							newMode();
						}
					});
				}
			}
		});

		nuovoMessaggioButton = new ToolStripButton();
		nuovoMessaggioButton.setIcon("mail/PEO.png");
		nuovoMessaggioButton.setIconSize(16);
		nuovoMessaggioButton.setPrompt("Nuovo messaggio");
		nuovoMessaggioButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaInvioMailDatasource");
				// NuovoMessaggioPopup nuovoMessaggioPopup = new NuovoMessaggioPopup(lGWTRestDataSource);
				// nuovoMessaggioPopup.show();
				NuovoMessaggioWindow lNuovoMessaggioWindow = new NuovoMessaggioWindow("nuovo_messaggio_cruscotto","invioNuovoMessaggio", instance, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						doSearch();
					}
				});
			}
		});
		
		nuovaRichiestaAttoButton = new ToolStripButton();
		nuovaRichiestaAttoButton.setIcon("buttons/new.png");
		nuovaRichiestaAttoButton.setIconSize(16);
		nuovaRichiestaAttoButton.setPrompt("Nuova richiesta accesso atti");
		nuovaRichiestaAttoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				manageNuovaRichiestaAccessoAtti();
			}
		});
		
		newModelloDocButton = new ToolStripButton();
		newModelloDocButton.setIcon("buttons/new.png");
		newModelloDocButton.setIconSize(16);
		newModelloDocButton.setPrompt("Nuovo modello documentale");
		newModelloDocButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				changeDetail((GWTRestDataSource) list.getDataSource(),  new ModelliDocDetail("modelliDoc"));
				detail.editNewRecord();
				newMode();
			}
		});

		// firmaEmailButton = new ToolStripButton();
		// firmaEmailButton.setIcon("menu/firma_email.png");
		// firmaEmailButton.setIconSize(16);
		// firmaEmailButton.setPrompt(I18NUtil.getMessages().configUtenteMenuFirmaEmail_title());
		// firmaEmailButton.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// final GWTRestDataSource firmaEmailHtmlDS = UserInterfaceFactory.getPreferenceDataSource();
		// firmaEmailHtmlDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
		// AdvancedCriteria firmaEmailHtmlCriteria = new AdvancedCriteria();
		// firmaEmailHtmlCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		// firmaEmailHtmlDS.fetchData(firmaEmailHtmlCriteria, new DSCallback() {
		//
		// @Override
		// public void execute(DSResponse response, Object rawData, DSRequest request) {
		// final Record recordPref = (response.getStatus() == DSResponse.STATUS_SUCCESS && response.getData().length != 0) ? response.getData()[0]
		// : null;
		// FirmaEmailHtmlPopup firmaEmailHtmlPopup = new FirmaEmailHtmlPopup(recordPref != null ? recordPref.getAttributeAsString("value") : null,
		// new SavePreferenceAction() {
		//
		// public void execute(final String valueToSave) {
		// if (recordPref != null) {
		// recordPref.setAttribute("value", valueToSave);
		// firmaEmailHtmlDS.updateData(recordPref);
		// } else {
		// Record record = new Record();
		// record.setAttribute("prefName", "DEFAULT");
		// record.setAttribute("value", valueToSave);
		// firmaEmailHtmlDS.addData(record);
		// }
		// AurigaLayout.addMessage(new MessageBean("Firma automatica in calce alle e-mail salvata con successo", "",
		// MessageType.INFO));
		// }
		// });
		// firmaEmailHtmlPopup.show();
		// }
		// });
		// }
		// });

		ToolStripButton[] customNewButtons = { newUdBozzeButton, nuovoMessaggioButton, nuovaRichiestaAttoButton, newModelloDocButton /*, firmaEmailButton */};
		return customNewButtons;
	}

	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for (Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}
		// if(parametri != null && !"".equals(parametri)) {
		// criterionList.add(new Criterion("parametri", OperatorId.EQUALS, parametri));
		// }
		if (idNode != null && !"".equals(idNode)) {
			criterionList.add(new Criterion("idNode", OperatorId.EQUALS, idNode));
		}
		if (idFolder != null && !"".equals(idFolder)) {
			criterionList.add(new Criterion("idFolder", OperatorId.EQUALS, idFolder));
		}
		if (flgUdFolder != null && !"".equals(flgUdFolder)) {
			criterionList.add(new Criterion("flgUdFolder", OperatorId.EQUALS, flgUdFolder));
		}
		if (criteriAvanzati != null && !"".equals(criteriAvanzati)) {
			criterionList.add(new Criterion("criteriAvanzati", OperatorId.EQUALS, criteriAvanzati));
		}
		if (classifica != null && !"".equals(classifica)) {
			criterionList.add(new Criterion("classifica", OperatorId.EQUALS, classifica));
		}
		if (idUtenteModPec != null && !"".equals(idUtenteModPec)) {
			criterionList.add(new Criterion("idUtenteModPec", OperatorId.EQUALS, idUtenteModPec));
		}
		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}

	// Restituisce i valori del filtro MEZZO DI TRASMISSIONE
	private JavaScriptObject getMezzoTrasmissioneValueFilter() {
		JavaScriptObject mezziTrasmissioneFilter = null;

		for (int i = 0; i < list.getLayout().getFilter().getClauseStack().getMembers().length - 1; i++) {
			FormItem lClauseFieldNameItem = list.getLayout().getFilter().getClauseFieldNameItem(i);
			if (lClauseFieldNameItem.getValue() != null && lClauseFieldNameItem.getValue().equals("mezzoTrasmissione")) {
				FormItem lFormItemValueItem = list.getLayout().getFilter().getClauseValueItem(i);
				if (lFormItemValueItem != null) {
					Object lObject = lFormItemValueItem.getValue();
					if (lObject instanceof JavaScriptObject) {
						mezziTrasmissioneFilter = (JavaScriptObject) lObject;
					}
				}
			}
		}
		return mezziTrasmissioneFilter;
	}

	public void annullaArchiviazioneEmail(final Record record, final boolean isMassiva) {
		record.setAttribute("flgInteresseCessato", "0");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnnullaArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = annullaArchiviazione(response, record, "idEmail", "id", I18NUtil.getMessages()
						.posta_elettronica_annulla_archiviazione_errore_totale(), I18NUtil.getMessages()
						.posta_elettronica_annulla_archiviazione_errore_parziale(), isMassiva);
				RecordList records = record.getAttributeAsRecordList("listaRecord");
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {
						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < records.getLength(); i++) {
							Record record = records.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}

					doSearchAndSelectRecords(recordsToSelect);
					Layout.hideWaitPopup();
				}

				reloadListAndSetCurrentRecord(records.get(0));
				Layout.hideWaitPopup();

				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	private String annullaArchiviazione(DSResponse response, Record record, String pkField, final String nameField, String completeErrorMessage,
			String partialErrorMessage, boolean isMassiva) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			RecordList listaRecord = record.getAttributeAsRecordList("listaRecord");
			if (errorMessages != null && errorMessages.size() > 0) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if (listaRecord.getLength() > errorMessages.size()) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record recordTmp = listaRecord.get(i);
						if (errorMessages.get(recordTmp.getAttribute(pkField)) != null) {
							recordErrore = new Record();
							recordErrore.setAttribute("idError", recordTmp.getAttribute(nameField));
							recordErrore.setAttribute("descrizione", errorMessages.get(recordTmp.getAttribute(pkField)));
							listaErrori.add(recordErrore);
						}
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					errorMsg = I18NUtil.getMessages().posta_elettronica_annulla_archiviazione_errore();
					Record recordErrore = listaRecord.get(0);
					errorMsg += "<br/>" + recordErrore.getAttribute(nameField) + ": " + errorMessages.get(recordErrore.getAttribute(pkField));
				}
			}
		}
		return errorMsg;
	}

	public void archiviazioneEmail(final Record record, final boolean isMassiva) {
		String classifica = getFilter().getExtraParam().get("classifica");
		record.setAttribute("classifica", classifica);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchiviazioneEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = archiviaEmail(response, record, "idEmail", "id", I18NUtil.getMessages().posta_elettronica_archiviazione_errore_totale(),
						I18NUtil.getMessages().posta_elettronica_archiviazione_errore_parziale(), isMassiva);
				RecordList records = record.getAttributeAsRecordList("listaRecord");
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {
						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < records.getLength(); i++) {
							Record record = records.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
				} else
					reloadListAndSetCurrentRecord(records.get(0));

				Layout.hideWaitPopup();

				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_archiviazione_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	private String archiviaEmail(DSResponse response, Record record, String pkField, String nameField, String completeErrorMessage, String partialErrorMessage,
			boolean isMassiva) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			RecordList listaRecord = record.getAttributeAsRecordList("listaRecord");
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			if (errorMessages != null && errorMessages.size() > 0) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if (listaRecord.getLength() > errorMessages.size()) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record recordTmp = listaRecord.get(i);
						if (errorMessages.get(recordTmp.getAttribute(pkField)) != null) {
							recordErrore = new Record();
							recordErrore.setAttribute("idError", recordTmp.getAttribute(nameField));
							recordErrore.setAttribute("descrizione", errorMessages.get(recordTmp.getAttribute(pkField)));
							listaErrori.add(recordErrore);
						}
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					errorMsg = I18NUtil.getMessages().posta_elettronica_archiviazione_errore();
					errorMsg += "<br/>" + data.getAttribute(nameField) + ": " + errorMessages.get(data.getAttribute(pkField));
				}
			}
		}
		return errorMsg;
	}

	public void buildTipoOperazione(final RecordList listaMail, String idUserLockFor, String motivi, final boolean isMassiva, final String tipoOperazione) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		record.setAttribute("iduserlockfor", idUserLockFor);
		record.setAttribute("motivi", motivi);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = "";
				if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(tipoOperazione)) {
					errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id", I18NUtil.getMessages()
							.posta_elettronica_presa_in_carico_errore_totale(), I18NUtil.getMessages().posta_elettronica_presa_in_carico_errore_parziale(),
							isMassiva, TipoOperazioneMail.PRESA_IN_CARICO.getValue());
				} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(tipoOperazione)) {
					errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id", I18NUtil.getMessages()
							.posta_elettronica_messa_in_carico_errore_totale(), I18NUtil.getMessages().posta_elettronica_messa_in_carico_errore_parziale(),
							isMassiva, TipoOperazioneMail.MESSA_IN_CARICO.getValue());
				} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(tipoOperazione)) {
					errorMsg = tipoOperazioneErrorMessage(response, listaMail, "idEmail", "id", I18NUtil.getMessages()
							.posta_elettronica_manda_in_approvazione_errore_parziale(), I18NUtil.getMessages()
							.posta_elettronica_manda_in_approvazione_errore_totale(), isMassiva, TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue());
				}
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {
						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < listaMail.getLength(); i++) {
							Record record = listaMail.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
					Layout.hideWaitPopup();
				}
				reloadListAndSetCurrentRecord(listaMail.get(0));
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(tipoOperazione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_presa_in_carico_successo(), "", MessageType.INFO));
					} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(tipoOperazione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_messa_in_carico_successo(), "", MessageType.INFO));
					} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(tipoOperazione)) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_successo(), "", MessageType.INFO));
					}
				}
			}
		});
	}

	public void rilascio(final RecordList listaMail, String usreLockFor, String motivo, final boolean isMassiva) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = rilascioErrorMessage(response, listaMail, "idEmail", "id", I18NUtil.getMessages().posta_elettronica_rilascio_errore_totale(),
						I18NUtil.getMessages().posta_elettronica_rilascio_errore_parziale(), isMassiva);
				if (isMassiva) {
					int[] recordsToSelect = null;
					Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() > 0) {
						recordsToSelect = new int[errorMessages.size()];
						int rec = 0;
						for (int i = 0; i < listaMail.getLength(); i++) {
							Record record = listaMail.get(i);
							if (errorMessages.get(record.getAttribute("idEmail")) != null) {
								recordsToSelect[rec++] = list.getRecordIndex(record);
							}
						}
					}
					doSearchAndSelectRecords(recordsToSelect);
					Layout.hideWaitPopup();
				}
				reloadListAndSetCurrentRecord(listaMail.get(0));
				Layout.hideWaitPopup();
				if (errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_rilascio_successo(), "", MessageType.INFO));
				}
			}
		});
	}

	private String tipoOperazioneErrorMessage(DSResponse response, RecordList listaRecord, String pkField, String nameField, String completeErrorMessage,
			String partialErrorMessage, boolean isMassiva, String tipoOperazione) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			Boolean storeInError = data.getAttributeAsBoolean("storeInError");
			if ((errorMessages != null) && (errorMessages.size() > 0)) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if ((listaRecord.getLength() > errorMessages.size()) && (storeInError == false)) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					if (!storeInError) {
						for (int i = 0; i < listaRecord.getLength(); i++) {
							Record record = listaRecord.get(i);
							if (errorMessages.get(record.getAttribute(pkField)) != null) {
								recordErrore = new Record();
								recordErrore.setAttribute("idError", record.getAttribute(nameField));
								recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
								listaErrori.add(recordErrore);
							}
						}
					}
					if (listaErrori.getLength() > 0) {
						ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(),
								LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
						errorePopup.show();
					}
				} else {
					if (TipoOperazioneMail.PRESA_IN_CARICO.getValue().equals(tipoOperazione)) {
						errorMsg = I18NUtil.getMessages().posta_elettronica_presa_in_carico_errore();
					} else if (TipoOperazioneMail.MESSA_IN_CARICO.getValue().equals(tipoOperazione)) {
						errorMsg = I18NUtil.getMessages().posta_elettronica_messa_in_carico_errore();
					} else if (TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue().equals(tipoOperazione)) {
						errorMsg = I18NUtil.getMessages().posta_elettronica_manda_in_approvazione_errore();
					}
					errorMsg += "<br/>" + errorMessages.get(listaRecord.get(0).getAttribute(pkField));
				}
			}
		}
		return errorMsg;
	}

	private String rilascioErrorMessage(DSResponse response, RecordList listaRecord, String pkField, String nameField, String completeErrorMessage,
			String partialErrorMessage, boolean isMassiva) {
		String errorMsg = null;
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			Boolean storeInError = data.getAttributeAsBoolean("storeInError");
			if ((errorMessages != null) && (errorMessages.size() > 0)) {
				if (isMassiva) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if ((listaRecord.getLength() > errorMessages.size()) && (storeInError == false)) {
						errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
					} else {
						errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
					}
					if (!storeInError) {
						for (int i = 0; i < listaRecord.getLength(); i++) {
							Record record = listaRecord.get(i);
							if (errorMessages.get(record.getAttribute(pkField)) != null) {
								recordErrore = new Record();
								recordErrore.setAttribute("idError", record.getAttribute(nameField));
								recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
								listaErrori.add(recordErrore);
							}
						}
					}
					if (listaErrori.getLength() > 0) {
						ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(),
								LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
						errorePopup.show();
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					errorMsg = I18NUtil.getMessages().posta_elettronica_rilascio_errore();
					errorMsg += "<br/>" + errorMessages.get(listaRecord.get(0).getAttribute(pkField));
				}
			}
		}
		return errorMsg;
	}

	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}

	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource gWTRestDataSource = (GWTRestDataSource) getList().getDataSource();
		gWTRestDataSource.setForceToShowPrompt(false);
		
		return gWTRestDataSource;
	}

	private void manageAnnullamentoMassivoAssegnazioneUOCompetente(Record object) {
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		if (errorMessages != null && errorMessages.size() > 0) {
			Layout.hideWaitPopup();
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_error(), "", MessageType.ERROR));
		} else {
			Layout.hideWaitPopup();
			doSearch();
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_annulla_uo_competente_success(), "", MessageType.INFO));
		}
	}
	
	private void manageAzioneSuRichAccessoAttiMassiva(String codOperazione) {
		final RecordList recordListaIdUd = new RecordList();
		for (int i = 0; i < list.getSelectedRecords().length; i++) {
			String idUd = list.getSelectedRecords()[i].getAttribute("idUd");
			Record recordIdUd = new Record();
			recordIdUd.setAttribute("idUd", idUd);
			recordListaIdUd.add(recordIdUd);
		}
		final Record record = new Record();
		record.setAttribute("listaRecord", recordListaIdUd);
		record.setAttribute("codOperazione", codOperazione);
	    final AzioneSuRichAccessoAttiPopup lAzioneSuRichAccessoAttiPopup = new AzioneSuRichAccessoAttiPopup(record) {
			
			@Override
			public void onClickOkButton(DSCallback service) {
				
				record.setAttribute("dataAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValue("dataAppuntamento") : null);
				record.setAttribute("orarioAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValueAsString("orarioAppuntamento") : null);
				record.setAttribute("dataPrelievo", datiPrelievoForm != null ? datiPrelievoForm.getValue("dataPrelievo") : null);
				record.setAttribute("dataRestituzione", datiRestituzioneForm != null ? datiRestituzioneForm.getValue("dataRestituzione") : null);
				record.setAttribute("motivi", motiviForm != null ? motiviForm.getValue("motivi") : null );
				GWTRestService<Record, Record> lSetAzioneSuRichAccessoAttiDataSource = new GWTRestService<Record, Record>("SetAzioneSuRichAccessoAttiDataSource");
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
				try{
					lSetAzioneSuRichAccessoAttiDataSource.addData(record, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								boolean success = manageErroreMassivoAzioneRichiestaAtti(record);
								azioneSuRichAccessoAttiPopup.markForDestroy();
							}
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
	}
	
	private void manageAzioneStampaFoglioPrelievoMassiva() {
		final RecordList recordListaIdUd = new RecordList();
		for (int i = 0; i < list.getSelectedRecords().length; i++) {
			String idUd = list.getSelectedRecords()[i].getAttribute("idUd");
			String protocollo = list.getSelectedRecords()[i].getAttribute("protocollo");
			String statoPrelievo = list.getSelectedRecords()[i].getAttribute("statoPrelievo");
			String elencoIdFolderAttiRich = list.getSelectedRecords()[i].getAttribute("elencoIdFolderAttiRich");
			Record recordIdUd = new Record();
			recordIdUd.setAttribute("idUd", idUd);
			recordIdUd.setAttribute("protocollo", protocollo);
			recordIdUd.setAttribute("statoPrelievo", statoPrelievo);
			recordIdUd.setAttribute("elencoIdFolderAttiRich", elencoIdFolderAttiRich);
			
			recordListaIdUd.add(recordIdUd);
		}
		final Record record = new Record();
		record.setAttribute("listaRecord", recordListaIdUd);
		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		try{
			new GWTRestService<Record, Record>("SetAzioneSuRichAccessoAttiDataSource").executecustom("generaFoglioPrelievo", record, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						boolean inError = result.getAttributeAsBoolean("recuperoModelloInError");
						if (inError){
							Layout.addMessage(new MessageBean(result.getAttribute("recuperoModelloErrorMessage"), "", MessageType.ERROR));
						}else{
							manageErroreMassivoAzioneRichiestaAtti(result);
							String nomeFilePdf = result.getAttribute("nomeFileFoglioPrelievo");
							String uriFilePdf = result.getAttribute("uriFileFoglioPrelievo");
							if (uriFilePdf != null && !"".equalsIgnoreCase(uriFilePdf)){
								InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFileFoglioPrelievo").getJsObj()));
								PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
							}
						}
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	@Override
	public void changeLayout(String nomeEntita, final GWTRestDataSource datasource, final ConfigurableFilter pFilter, final CustomList pList, final CustomDetail pDetail) {
		
		super.changeLayout(nomeEntita, datasource, pFilter, pList, pDetail);
		
		// Per ricaricare i bottoni di dettaglio nel caso delle richieste di accesso agli atti
		createDetailToolStrip();
		detailLayout.setMembers(detail, detailToolStrip);					
		detailLayout.markForRedraw();	
		
		newButton.hide();
		if (idNode != null && ("D.BOZZE".equals(idNode) || "D.DIA".equals(idNode)) && Layout.isPrivilegioAttivo("GRD/UD/IUD")) {
			newUdBozzeButton.show();
		} else {
			newUdBozzeButton.hide();
		}
		if (classifica != null && !classifica.equals("standard.eliminata") && Layout.isPrivilegioAttivo("EML/INS")) {
			nuovoMessaggioButton.show();
		} else {
			nuovoMessaggioButton.hide();
		}
		if(idNode != null &&  idNode.equalsIgnoreCase("D.RAA.I")){
			nuovaRichiestaAttoButton.show();
		}else{
			nuovaRichiestaAttoButton.hide();
		}
		if(azione != null &&  azione.equalsIgnoreCase("modelliDoc")){
			newModelloDocButton.show();
		}else{
			newModelloDocButton.hide();
		}
	}
	
	@Override
	public void changeDetail(final GWTRestDataSource datasource, final CustomDetail pDetail) {

		super.changeDetail(datasource, pDetail);
			
		// Per ricaricare i bottoni di dettaglio nel caso delle richieste di accesso agli atti
		createDetailToolStrip();		
		detailLayout.setMembers(detail, detailToolStrip);		
		detailLayout.markForRedraw();
	}
	
	private boolean manageErroreMassivoAzioneRichiestaAtti(Record object) {
		
		boolean verify = false;
		RecordList listaRecord = object.getAttributeAsRecordList("listaRecord");
		RecordList listaErrori = new RecordList();
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		String errorMsg = null;
		int[] recordsToSelect = null;
		if (errorMessages != null && errorMessages.size() > 0) {
			recordsToSelect = new int[errorMessages.size()];
			if (listaRecord.getLength() > errorMessages.size()) {
				errorMsg = "Alcuni dei record selezionati per l''operazione sono andati in errore!";
			} else {
				errorMsg = "Tutti i record selezionati per l''operazione sono andati in errore!";
			}
			int rec = 0;
				
			for (int i = 0; i < listaRecord.getLength(); i++) {
				Record record = listaRecord.get(i);
				
				if (errorMessages.get(record.getAttribute("idUd")) != null) {
					recordsToSelect[rec++] = list.getRecordIndex(record);
					String descrizione = (String) errorMessages.get(record.getAttribute("idUd"));
					String numRichiesta = descrizione.substring(0, descrizione.indexOf("@#@"));
					descrizione = descrizione.substring(descrizione.indexOf("@#@") + 3, descrizione.length());
					record.setAttribute("idError", numRichiesta);
					record.setAttribute("descrizione", descrizione);
					listaErrori.add(record);
				}
			}
		}

		if (listaErrori != null && listaErrori.getLength() > 0 && !listaErrori.isEmpty()) {
			Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup("", "N° richiesta", listaErrori, listaRecord.getLength(),
					LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
			errorePopup.show();
		} else {
			verify = true;
			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));										
		}
		doSearchAndSelectRecords(recordsToSelect);
		return verify;
	}
	
	public void singleOperationCallback(DSResponse response, Record record, String pkField, String nameField, String successMessage,
			String errorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				errorMsg = errorMessage != null ? errorMessage : "";
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
				}
			}
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	private void manageNuovaRichiestaAccessoAtti(){
		
		AurigaLayout.apriSceltaTipoRichiestaAccessoAttiPopup(null, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lRecordTipoDoc) {
				
				String idTipoDocumento = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
				String descTipoDocumento = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
				Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
				String siglaPraticaSuSistUfficioRichiedente = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("siglaPraticaSuSistUfficioRichiedente") : null;
				String idNodoDefaultRicercaAtti = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idNodoDefaultRicercaAtti") : null;
				Map<String, Object> initialValues = new HashMap<String, Object>();
				if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
					initialValues.put("tipoDocumento", idTipoDocumento);
					initialValues.put("nomeTipoDocumento", descTipoDocumento);
					initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
					initialValues.put("siglaPraticaSuSistUfficioRichiedente", siglaPraticaSuSistUfficioRichiedente);
					initialValues.put("idNodoDefaultRicercaAtti", idNodoDefaultRicercaAtti);
				}
				RichiestaAccessoAttiWindow lRichiestaAccessoAttiWindow = new RichiestaAccessoAttiWindow(initialValues, instance);
				lRichiestaAccessoAttiWindow.show();

//				String idTipoDocumento = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("idTipoDocumento") : null;
//				String descTipoDocumento = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("descTipoDocumento") : null;
//				Boolean flgTipoDocConVie = lRecordTipoDoc != null ? lRecordTipoDoc.getAttributeAsBoolean("flgTipoDocConVie") : null;
//				String siglaPraticaSuSistUfficioRichiedente = lRecordTipoDoc != null ? lRecordTipoDoc.getAttribute("siglaPraticaSuSistUfficioRichiedente") : null;
//				Map<String, Object> initialValues = new HashMap<String, Object>();
//				if (idTipoDocumento != null && !"".equals(idTipoDocumento)) {
//					initialValues.put("tipoDocumento", idTipoDocumento);
//					initialValues.put("nomeTipoDocumento", descTipoDocumento);
//					initialValues.put("flgTipoDocConVie", flgTipoDocConVie);
//					initialValues.put("siglaPraticaSuSistUfficioRichiedente", siglaPraticaSuSistUfficioRichiedente);						
//				}
//				RichiestaAccessoAttiDetail lRichiestaAccessoAttiDetail = new RichiestaAccessoAttiDetail("richiesta_accesso_atti");
//				lRichiestaAccessoAttiDetail.editNewRecord(initialValues);
//				lRichiestaAccessoAttiDetail.newMode();
			}
		});
	}
	
	public void resettaParametriRicerca(){
		setIdNode(null);
		setNomeNodo(null);
		setTipoNodo(null);
		setIdFolder(null);
		setFlgUdFolder(null);
		setCriteriAvanzati(null);
		setClassifica(null);
		setIdUtenteModPec(null);
		setCodSezione(null);
		
		/** AZIONI MASSIVE **/
		
		setAbilApposizioneFirma(false);
		setAbilApposizioneFirmaProtocollazione(false);
		setAbilRifiutoFirma(false); 
		setAbilApposizioneVisto(false); 
		setAbilRifiutoVisto(false); 
		setAbilConfermaPreassegnazione(false); 
		setAbilModificaPreassegnazione(false); 
		setAbilInserimentoInAttoAutorizzAnn(false); 
		setAbilPresaInCarico(false); 
		setAbilClassificazioneFascicolazione(false); 
		setAbilFascicolazione(false); 
		setAbilFolderizzazione(false); 
		setAbilAssegnazione(false); 
		setAbilRestituzione(false); 
		setAbilSmistamento(false); 
		setAbilSmistamentoCC(false);
		setAbilInvioPerConoscenza(false); 
		setAbilArchiviazione(false); 
		setAbilAnnullamentoArchiviazione(false); 
		setAbilAggiuntaAiPreferiti(false); 
		setAbilRimozioneDaiPreferiti(false); 
		setAbilAssegnazioneRiservatezza(false); 
		setAbilRimozioneRiservatezza(false); 
		setAbilAnnullamentoEliminazione(false); 
		setAbilEliminazione(false);
		setAbilEliminazioneImgScansione(false);
		setAbilApposizioneCommenti(false); 
		setAbilStampaEtichetta(false); 
		setAbilDownloadDocZipMultiButton(false); 
		setAbilModificaStatoDocMultiButton(false); 
		setAbilModificaTipologiaMultiButton(false); 
		setAbilChiudiFascicoloMultiButton(false); 
		setAbilRiapriFascicoloMultiButton(false); 
		setAbilSegnaComeVisionatoMultiButton(false); 
		
		setAbilRichiesteAccessoAttiInvioInApprovazione(false); 
		setAbilRichiesteAccessoAttiApprovazione(false); 
		setAbilRichiesteAccessoAttiInvioEsitoVerificaArchivio(false); 
		setAbilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda(false); 
		setAbilRichiesteAccessoAttiRegistrazioneAppuntamento(false); 
		setAbilRichiesteAccessoAttiAnnullamentoAppuntamento(false); 
		setAbilRichiesteAccessoAttiRegistrazionePrelievo(false); 
		setAbilRichiesteAccessoAttiRegistrazioneRestituzione(false); 
		setAbilRichiesteAccessoAttiAnullamentoRichiesta(false); 
		setAbilRichiesteAccessoAttiStampaFoglioPrelievo(false); 
		setAbilRichiesteAccessoAttiEliminazioneRichiesta(false); 
		setAbilAssociazioneImgAProtocollo(false);
		
		setAbilEmailChiusuraLavorazione(false); 
		setAbilEmailRiaperturaLavorazione(false); 
		setAbilEmailAssegnazione(false); 
		setAbilEmailAnnullamentoAssegnazione(false); 
		setAbilEmailInoltro(false); 
		setAbilEmailPresaInCarico(false); 
		setAbilEmailMessaInCarico(false); 
		setAbilEmailInvioInApprovazione(false); 
		setAbilEmailRilascio(false); 
		setAbilEmailEliminazione(false); 
		setAbilEmailApposizioneTagCommenti(false); 
	}
	
	private String getEstremiFolderFromLayout(Record record){
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
	
	private String getEstremiUdFromLayout(Record record){
		return record.getAttribute("segnatura");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE FIRMA
	 */
	public boolean showFirmaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilFirma");
	}
	
	/**
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE FIRMA E PROTOCOLLA
	 */
	public boolean showFirmaProtocollaButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilFirmaProtocolla");
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
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE VISTO
	 */
	public boolean showVistoDocumentoButton(Record record) {
		return record != null && record.getAttributeAsBoolean("abilVistoElettronico");
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
	 * METODO PER VERIFICA ABILITAZIONE BOTTONE CONDIVISIONE
	 */
	public boolean showCondivisioneButton(Record record){
		return record != null && record.getAttributeAsBoolean("abilCondivisione");
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
	
	private void manageVistoButtonClickOnMultiToolstrip(boolean apposizioneForzata) {
		final RecordList listaUd = new RecordList();
		for (int i = 0; i < list.getSelectedRecords().length; i++) {
			listaUd.add(list.getSelectedRecords()[i]);
		}
		((ArchivioList)list).manageApponiVisto(listaUd, apposizioneForzata);
	}
	
	/**
	 * Gestione funzionalità Taglia & Incolla
	 */
	
	public void setCutNode(Record cutNode){
		this.cutNode = cutNode;
	}
	
	public Record getCutNode() {
		return cutNode;
	}
	
	//TODO
	public void paste(final Record currentRecord,final Boolean isFromList) {
		
		Record record = new Record();
		getList().manageLoadDetail(record, -1, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				
			}
		});
	}
	
	/**
	 * @param listaUdFolder
	 * @param operazione
	 * @param objObjectect 
	 */
	public void manageGenerateDocZip(final RecordList listaUdFolder, String operazione, Record impostazioniTimbratura) {
		Record record = new Record();
		record.setAttribute("listaRecord", listaUdFolder);

		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
		lGwtRestDataSource.extraparam.put("messageError", I18NUtil.getMessages().alert_archivio_list_downloadDocsZip());
		lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
		lGwtRestDataSource.extraparam.put("operazione", operazione);
		lGwtRestDataSource.setForceToShowPrompt(false);
		
		if(impostazioniTimbratura!=null) {
			lGwtRestDataSource.extraparam.put("posizioneTimbro", impostazioniTimbratura.getAttribute("posizioneTimbro"));
			lGwtRestDataSource.extraparam.put("rotazioneTimbro", impostazioniTimbratura.getAttribute("rotazioneTimbro"));
			lGwtRestDataSource.extraparam.put("tipoPagina", impostazioniTimbratura.getAttribute("tipoPagina"));
		}
		
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
		lGwtRestDataSource.executecustom("generateDocsZip", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					
					String message = record.getAttribute("message");
					String warningTimbro = record.getAttribute("warningTimbro");
					String warningSbusta = record.getAttribute("warningSbusta");
					
					if (message != null && !"".equals(message)) {
						Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
					} else {
						String uri = record.getAttribute("storageZipRemoteUri");
						String nomeFile = record.getAttribute("zipName");
						Record infoFileRecord = record.getAttributeAsRecord("infoFile");
						Record lRecord = new Record();
						lRecord.setAttribute("displayFilename", nomeFile);
						lRecord.setAttribute("uri", uri);
						lRecord.setAttribute("sbustato", "false");
						lRecord.setAttribute("remoteUri", true);
						lRecord.setAttribute("infoFile", infoFileRecord);
						
						DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						
						if (warningTimbro != null && !"".equals(warningTimbro)) {
							Layout.addMessage(new MessageBean(warningTimbro, "", MessageType.WARNING));
						}else if (warningSbusta != null && !"".equals(warningSbusta)) {
							Layout.addMessage(new MessageBean(warningSbusta, "", MessageType.WARNING));
						}
					}
				}

			}
		});
	}
	
	public void showOpzioniTimbratura(final RecordList listaUdFolder, final String operazione) {

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
				
				manageGenerateDocZip(listaUdFolder, operazione, impostazioniTimbratura);
				
			}
			
		});
		apponiTimbroWindow.show();

	}
	
	public String getAzione() {
		return azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

	public String getIdNode() {
		return idNode;
	}

	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}

	public String getCriteriAvanzati() {
		return criteriAvanzati;
	}

	public void setCriteriAvanzati(String criteriAvanzati) {
		this.criteriAvanzati = criteriAvanzati;
	}

	public String getClassifica() {
		return classifica;
	}

	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}

	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}

	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public String getTipoNodo() {
		return tipoNodo;
	}

	public void setTipoNodo(String tipoNodo) {
		this.tipoNodo = tipoNodo;
	}

	public String getNomeNodo() {
		return nomeNodo;
	}

	public void setNomeNodo(String nomeNodo) {
		this.nomeNodo = nomeNodo;
	}

	public String getCodSezione() {
		return codSezione;
	}

	public void setCodSezione(String codSezione) {
		this.codSezione = codSezione;
	}
	
	/*
	 * AZIONI MASSIVE
	 */
	
	public Boolean getAbilApposizioneFirma() {
		return abilApposizioneFirma;
	}

	public void setAbilApposizioneFirma(Boolean abilApposizioneFirma) {
		this.abilApposizioneFirma = abilApposizioneFirma;
	}
	
	public Boolean getAbilApposizioneFirmaProtocollazione() {
		return apposizioneFirmaProtocollazione;
	}

	public void setAbilApposizioneFirmaProtocollazione(boolean apposizioneFirmaProtocollazione) {
		this.apposizioneFirmaProtocollazione = apposizioneFirmaProtocollazione;
	}

	public boolean isAbilRifiutoFirma() {
		return abilRifiutoFirma;
	}

	public void setAbilRifiutoFirma(boolean abilRifiutoFirma) {
		this.abilRifiutoFirma = abilRifiutoFirma;
	}

	public boolean isAbilApposizioneVisto() {
		return abilApposizioneVisto;
	}

	public void setAbilApposizioneVisto(boolean abilApposizioneVisto) {
		this.abilApposizioneVisto = abilApposizioneVisto;
	}

	public boolean isAbilRifiutoVisto() {
		return abilRifiutoVisto;
	}

	public void setAbilRifiutoVisto(boolean abilRifiutoVisto) {
		this.abilRifiutoVisto = abilRifiutoVisto;
	}

	public boolean isAbilConfermaPreassegnazione() {
		return abilConfermaPreassegnazione;
	}

	public void setAbilConfermaPreassegnazione(boolean abilConfermaPreassegnazione) {
		this.abilConfermaPreassegnazione = abilConfermaPreassegnazione;
	}

	public boolean isAbilModificaPreassegnazione() {
		return abilModificaPreassegnazione;
	}

	public void setAbilModificaPreassegnazione(boolean abilModificaPreassegnazione) {
		this.abilModificaPreassegnazione = abilModificaPreassegnazione;
	}

	public boolean isAbilInserimentoInAttoAutorizzAnn() {
		return abilInserimentoInAttoAutorizzAnn;
	}

	public void setAbilInserimentoInAttoAutorizzAnn(boolean abilInserimentoInAttoAutorizzAnn) {
		this.abilInserimentoInAttoAutorizzAnn = abilInserimentoInAttoAutorizzAnn;
	}

	public boolean isAbilPresaInCarico() {
		return abilPresaInCarico;
	}

	public void setAbilPresaInCarico(boolean abilPresaInCarico) {
		this.abilPresaInCarico = abilPresaInCarico;
	}

	public boolean isAbilClassificazioneFascicolazione() {
		return abilClassificazioneFascicolazione;
	}

	public void setAbilClassificazioneFascicolazione(boolean abilClassificazioneFascicolazione) {
		this.abilClassificazioneFascicolazione = abilClassificazioneFascicolazione;
	}

	public boolean isAbilFascicolazione() {
		return abilFascicolazione;
	}

	public void setAbilFascicolazione(boolean abilFascicolazione) {
		this.abilFascicolazione = abilFascicolazione;
	}

	public boolean isAbilFolderizzazione() {
		return abilFolderizzazione;
	}

	public void setAbilFolderizzazione(boolean abilFolderizzazione) {
		this.abilFolderizzazione = abilFolderizzazione;
	}

	public boolean isAbilAssegnazione() {
		return abilAssegnazione;
	}

	public void setAbilAssegnazione(boolean abilAssegnazione) {
		this.abilAssegnazione = abilAssegnazione;
	}

	public boolean isAbilRestituzione() {
		return abilRestituzione;
	}

	public void setAbilRestituzione(boolean abilRestituzione) {
		this.abilRestituzione = abilRestituzione;
	}

	public boolean isAbilSmistamento() {
		return abilSmistamento;
	}

	public void setAbilSmistamento(boolean abilSmistamento) {
		this.abilSmistamento = abilSmistamento;
	}
	
	public void setAbilSmistamentoCC(boolean abilSmistamentoCC) {
		this.abilSmistamentoCC = abilSmistamentoCC;
	}

	public boolean isAbilInvioPerConoscenza() {
		return abilInvioPerConoscenza;
	}

	public void setAbilInvioPerConoscenza(boolean abilInvioPerConoscenza) {
		this.abilInvioPerConoscenza = abilInvioPerConoscenza;
	}

	public boolean isAbilArchiviazione() {
		return abilArchiviazione;
	}

	public void setAbilArchiviazione(boolean abilArchiviazione) {
		this.abilArchiviazione = abilArchiviazione;
	}

	public boolean isAbilAnnullamentoArchiviazione() {
		return abilAnnullamentoArchiviazione;
	}

	public void setAbilAnnullamentoArchiviazione(boolean abilAnnullamentoArchiviazione) {
		this.abilAnnullamentoArchiviazione = abilAnnullamentoArchiviazione;
	}

	public boolean isAbilAggiuntaAiPreferiti() {
		return abilAggiuntaAiPreferiti;
	}

	public void setAbilAggiuntaAiPreferiti(boolean abilAggiuntaAiPreferiti) {
		this.abilAggiuntaAiPreferiti = abilAggiuntaAiPreferiti;
	}

	public boolean isAbilRimozioneDaiPreferiti() {
		return abilRimozioneDaiPreferiti;
	}

	public void setAbilRimozioneDaiPreferiti(boolean abilRimozioneDaiPreferiti) {
		this.abilRimozioneDaiPreferiti = abilRimozioneDaiPreferiti;
	}

	public boolean isAbilAssegnazioneRiservatezza() {
		return abilAssegnazioneRiservatezza;
	}

	public void setAbilAssegnazioneRiservatezza(boolean abilAssegnazioneRiservatezza) {
		this.abilAssegnazioneRiservatezza = abilAssegnazioneRiservatezza;
	}

	public boolean isAbilRimozioneRiservatezza() {
		return abilRimozioneRiservatezza;
	}

	public void setAbilRimozioneRiservatezza(boolean abilRimozioneRiservatezza) {
		this.abilRimozioneRiservatezza = abilRimozioneRiservatezza;
	}

	public boolean isAbilAnnullamentoEliminazione() {
		return abilAnnullamentoEliminazione;
	}

	public void setAbilAnnullamentoEliminazione(boolean abilAnnullamentoEliminazione) {
		this.abilAnnullamentoEliminazione = abilAnnullamentoEliminazione;
	}

	public boolean isAbilEliminazione() {
		return abilEliminazione;
	}

	public void setAbilEliminazione(boolean abilEliminazione) {
		this.abilEliminazione = abilEliminazione;
	}

	public boolean isAbilEliminazioneImgScansione() {
		return abilEliminazioneImgScansione;
	}

	public void setAbilEliminazioneImgScansione(boolean abilEliminazioneImgScansione) {
		this.abilEliminazioneImgScansione = abilEliminazioneImgScansione;
	}
	
	public boolean isAbilApposizioneCommenti() {
		return abilApposizioneCommenti;
	}

	public void setAbilApposizioneCommenti(boolean abilApposizioneCommenti) {
		this.abilApposizioneCommenti = abilApposizioneCommenti;
	}

	public boolean isAbilStampaEtichetta() {
		return abilStampaEtichetta;
	}

	public void setAbilStampaEtichetta(boolean abilStampaEtichetta) {
		this.abilStampaEtichetta = abilStampaEtichetta;
	}

	public boolean isAbilDownloadDocZipMultiButton() {
		return abilDownloadDocZipMultiButton;
	}

	public void setAbilDownloadDocZipMultiButton(boolean abilDownloadDocZipMultiButton) {
		this.abilDownloadDocZipMultiButton = abilDownloadDocZipMultiButton;
	}

	public boolean isAbilModificaStatoDocMultiButton() {
		return abilModificaStatoDocMultiButton;
	}

	public void setAbilModificaStatoDocMultiButton(boolean abilModificaStatoDocMultiButton) {
		this.abilModificaStatoDocMultiButton = abilModificaStatoDocMultiButton;
	}

	public boolean isAbilModificaTipologiaMultiButton() {
		return abilModificaTipologiaMultiButton;
	}

	public void setAbilModificaTipologiaMultiButton(boolean abilModificaTipologiaMultiButton) {
		this.abilModificaTipologiaMultiButton = abilModificaTipologiaMultiButton;
	}

	public boolean isAbilChiudiFascicoloMultiButton() {
		return abilChiudiFascicoloMultiButton;
	}

	public void setAbilChiudiFascicoloMultiButton(boolean abilChiudiFascicoloMultiButton) {
		this.abilChiudiFascicoloMultiButton = abilChiudiFascicoloMultiButton;
	}

	public boolean isAbilRiapriFascicoloMultiButton() {
		return abilRiapriFascicoloMultiButton;
	}

	public void setAbilRiapriFascicoloMultiButton(boolean abilRiapriFascicoloMultiButton) {
		this.abilRiapriFascicoloMultiButton = abilRiapriFascicoloMultiButton;
	}

	public boolean isAbilSegnaComeVisionatoMultiButton() {
		return abilSegnaComeVisionatoMultiButton;
	}

	public void setAbilSegnaComeVisionatoMultiButton(boolean abilSegnaComeVisionatoMultiButton) {
		this.abilSegnaComeVisionatoMultiButton = abilSegnaComeVisionatoMultiButton;
	}

	public boolean isAbilRichiesteAccessoAttiInvioInApprovazione() {
		return abilRichiesteAccessoAttiInvioInApprovazione;
	}

	public void setAbilRichiesteAccessoAttiInvioInApprovazione(boolean abilRichiesteAccessoAttiInvioInApprovazione) {
		this.abilRichiesteAccessoAttiInvioInApprovazione = abilRichiesteAccessoAttiInvioInApprovazione;
	}

	public boolean isAbilRichiesteAccessoAttiApprovazione() {
		return abilRichiesteAccessoAttiApprovazione;
	}

	public void setAbilRichiesteAccessoAttiApprovazione(boolean abilRichiesteAccessoAttiApprovazione) {
		this.abilRichiesteAccessoAttiApprovazione = abilRichiesteAccessoAttiApprovazione;
	}

	public boolean isAbilRichiesteAccessoAttiInvioEsitoVerificaArchivio() {
		return abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
	}

	public void setAbilRichiesteAccessoAttiInvioEsitoVerificaArchivio(
			boolean abilRichiesteAccessoAttiInvioEsitoVerificaArchivio) {
		this.abilRichiesteAccessoAttiInvioEsitoVerificaArchivio = abilRichiesteAccessoAttiInvioEsitoVerificaArchivio;
	}

	public boolean isAbilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda() {
		return abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
	}

	public void setAbilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda(
			boolean abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda) {
		this.abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda = abilRichiesteAccessoAttiAbilitazioneAppuntamentoDaAgenda;
	}

	public boolean isAbilRichiesteAccessoAttiRegistrazioneAppuntamento() {
		return abilRichiesteAccessoAttiRegistrazioneAppuntamento;
	}

	public void setAbilRichiesteAccessoAttiRegistrazioneAppuntamento(
			boolean abilRichiesteAccessoAttiRegistrazioneAppuntamento) {
		this.abilRichiesteAccessoAttiRegistrazioneAppuntamento = abilRichiesteAccessoAttiRegistrazioneAppuntamento;
	}

	public boolean isAbilRichiesteAccessoAttiAnnullamentoAppuntamento() {
		return abilRichiesteAccessoAttiAnnullamentoAppuntamento;
	}

	public void setAbilRichiesteAccessoAttiAnnullamentoAppuntamento(
			boolean abilRichiesteAccessoAttiAnnullamentoAppuntamento) {
		this.abilRichiesteAccessoAttiAnnullamentoAppuntamento = abilRichiesteAccessoAttiAnnullamentoAppuntamento;
	}

	public boolean isAbilRichiesteAccessoAttiRegistrazionePrelievo() {
		return abilRichiesteAccessoAttiRegistrazionePrelievo;
	}

	public void setAbilRichiesteAccessoAttiRegistrazionePrelievo(boolean abilRichiesteAccessoAttiRegistrazionePrelievo) {
		this.abilRichiesteAccessoAttiRegistrazionePrelievo = abilRichiesteAccessoAttiRegistrazionePrelievo;
	}

	public boolean isAbilRichiesteAccessoAttiRegistrazioneRestituzione() {
		return abilRichiesteAccessoAttiRegistrazioneRestituzione;
	}

	public void setAbilRichiesteAccessoAttiRegistrazioneRestituzione(
			boolean abilRichiesteAccessoAttiRegistrazioneRestituzione) {
		this.abilRichiesteAccessoAttiRegistrazioneRestituzione = abilRichiesteAccessoAttiRegistrazioneRestituzione;
	}

	public boolean isAbilRichiesteAccessoAttiAnullamentoRichiesta() {
		return abilRichiesteAccessoAttiAnullamentoRichiesta;
	}

	public void setAbilRichiesteAccessoAttiAnullamentoRichiesta(boolean abilRichiesteAccessoAttiAnullamentoRichiesta) {
		this.abilRichiesteAccessoAttiAnullamentoRichiesta = abilRichiesteAccessoAttiAnullamentoRichiesta;
	}

	public boolean isAbilRichiesteAccessoAttiStampaFoglioPrelievo() {
		return abilRichiesteAccessoAttiStampaFoglioPrelievo;
	}

	public void setAbilRichiesteAccessoAttiStampaFoglioPrelievo(boolean abilRichiesteAccessoAttiStampaFoglioPrelievo) {
		this.abilRichiesteAccessoAttiStampaFoglioPrelievo = abilRichiesteAccessoAttiStampaFoglioPrelievo;
	}

	public boolean isAbilRichiesteAccessoAttiEliminazioneRichiesta() {
		return abilRichiesteAccessoAttiEliminazioneRichiesta;
	}

	public void setAbilRichiesteAccessoAttiEliminazioneRichiesta(boolean abilRichiesteAccessoAttiEliminazioneRichiesta) {
		this.abilRichiesteAccessoAttiEliminazioneRichiesta = abilRichiesteAccessoAttiEliminazioneRichiesta;
	}
	
	public boolean isAbilAssociazioneImgAProtocollo() {
		return abilAssociazioneImgAProtocollo;
	}

	public void setAbilAssociazioneImgAProtocollo(boolean abilAssociazioneImgAProtocollo) {
		this.abilAssociazioneImgAProtocollo = abilAssociazioneImgAProtocollo;
	}
	
	public boolean isAbilEmailChiusuraLavorazione() {
		return abilEmailChiusuraLavorazione;
	}

	public void setAbilEmailChiusuraLavorazione(boolean abilEmailChiusuraLavorazione) {
		this.abilEmailChiusuraLavorazione = abilEmailChiusuraLavorazione;
	}

	public boolean isAbilEmailRiaperturaLavorazione() {
		return abilEmailRiaperturaLavorazione;
	}

	public void setAbilEmailRiaperturaLavorazione(boolean abilEmailRiaperturaLavorazione) {
		this.abilEmailRiaperturaLavorazione = abilEmailRiaperturaLavorazione;
	}

	public boolean isAbilEmailAssegnazione() {
		return abilEmailAssegnazione;
	}

	public void setAbilEmailAssegnazione(boolean abilEmailAssegnazione) {
		this.abilEmailAssegnazione = abilEmailAssegnazione;
	}

	public boolean isAbilEmailAnnullamentoAssegnazione() {
		return abilEmailAnnullamentoAssegnazione;
	}

	public void setAbilEmailAnnullamentoAssegnazione(boolean abilEmailAnnullamentoAssegnazione) {
		this.abilEmailAnnullamentoAssegnazione = abilEmailAnnullamentoAssegnazione;
	}

	public boolean isAbilEmailInoltro() {
		return abilEmailInoltro;
	}

	public void setAbilEmailInoltro(boolean abilEmailInoltro) {
		this.abilEmailInoltro = abilEmailInoltro;
	}

	public boolean isAbilEmailPresaInCarico() {
		return abilEmailPresaInCarico;
	}

	public void setAbilEmailPresaInCarico(boolean abilEmailPresaInCarico) {
		this.abilEmailPresaInCarico = abilEmailPresaInCarico;
	}

	public boolean isAbilEmailMessaInCarico() {
		return abilEmailMessaInCarico;
	}

	public void setAbilEmailMessaInCarico(boolean abilEmailMessaInCarico) {
		this.abilEmailMessaInCarico = abilEmailMessaInCarico;
	}

	public boolean isAbilEmailInvioInApprovazione() {
		return abilEmailInvioInApprovazione;
	}

	public void setAbilEmailInvioInApprovazione(boolean abilEmailInvioInApprovazione) {
		this.abilEmailInvioInApprovazione = abilEmailInvioInApprovazione;
	}

	public boolean isAbilEmailRilascio() {
		return abilEmailRilascio;
	}

	public void setAbilEmailRilascio(boolean abilEmailRilascio) {
		this.abilEmailRilascio = abilEmailRilascio;
	}

	public boolean isAbilEmailEliminazione() {
		return abilEmailEliminazione;
	}

	public void setAbilEmailEliminazione(boolean abilEmailEliminazione) {
		this.abilEmailEliminazione = abilEmailEliminazione;
	}

	public boolean isAbilEmailApposizioneTagCommenti() {
		return abilEmailApposizioneTagCommenti;
	}

	public void setAbilEmailApposizioneTagCommenti(boolean abilEmailApposizioneTagCommenti) {
		this.abilEmailApposizioneTagCommenti = abilEmailApposizioneTagCommenti;
	}

	/**
	 * @param listaIdUDScansioniFinal
	 * @param idUd
	 * @param nroProt
	 * @param annoProt
	 * @param _window 
	 */
	public void apriProtocolloAssociatoImg(final String listaIdUDScansioniFinal, String idUd, String nroProt,
			String annoProt, CercaUDPopup _window) {
		_window.markForDestroy();
		
		Layout.showWaitPopup("Caricamento in corso..");

		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		lRecord.setAttribute("listaIdUdScansioni", listaIdUDScansioniFinal);
		new ModificaRegProtAssociatoImgWindow(lRecord, "Dettaglio prot. N° " + nroProt + "/" + annoProt, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				try {
					doSearch();
				} catch (Exception e) {
				}
			}
		});
		
		Layout.hideWaitPopup();
	}
	
	public void manageEliminaImgScansione(){
		
	}
	
	private boolean showOperazioniTimbratura(Record detailRecord) {
		return detailRecord != null && detailRecord.getAttribute("codCategoriaProtocollo") != null && !"".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo"))
				&& ("PG".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")) ||
						"R".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")) ||
						"PP".equalsIgnoreCase(detailRecord.getAttribute("codCategoriaProtocollo")));
	}
}