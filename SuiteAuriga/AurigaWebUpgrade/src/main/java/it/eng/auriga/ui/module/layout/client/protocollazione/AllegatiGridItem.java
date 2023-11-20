/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DropCompleteEvent;
import com.smartgwt.client.widgets.events.DropCompleteHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.ListaWarningsPopup;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.NuovaPropostaAtto2CompletaDetail;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaEtichettaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroBean;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroCopertinaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.UpdateableRecordComponent;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public abstract class AllegatiGridItem extends GridItem implements IDatiSensibiliItem {
		
	protected AllegatiListGridField id;
	protected AllegatiListGridField flgSalvato;
	protected AllegatiListGridField flgNoModificaDati;
	protected AllegatiListGridField idUdAppartenenza;
	protected AllegatiListGridField isUdSenzaFileImportata;
	protected AllegatiListGridField idUdAllegato;
	protected AllegatiListGridField idTask;
	protected AllegatiListGridField idEmail;
	protected AllegatiListGridField idAttachEmail;
	protected AllegatiListGridField collegaDocumentoImportato;	
	protected AllegatiListGridField idDocAllegato;
	protected AllegatiListGridField fileImportato;
	protected AllegatiListGridField uriFileAllegato;
	protected AllegatiListGridField tsInsLastVerFileAllegato;
	protected AllegatiListGridField infoFile;
	protected AllegatiListGridField remoteUri;
	protected AllegatiListGridField isChanged;
	protected AllegatiListGridField nomeFileAllegatoOrig;
	protected AllegatiListGridField nomeFileAllegatoTif;
	protected AllegatiListGridField uriFileAllegatoTif;
	protected AllegatiListGridField nomeFileVerPreFirma;
	protected AllegatiListGridField uriFileVerPreFirma;
	protected AllegatiListGridField improntaVerPreFirma;
	protected AllegatiListGridField infoFileVerPreFirma;
	protected AllegatiListGridField nroUltimaVersione;	
	protected AllegatiListGridField mostraErrori;		
	protected AllegatiListGridField numeroAllegato;	
	protected AllegatiListGridField numeroAllegatoAfterDrop;
	protected AllegatiListGridField flgProvEsterna;
	protected AllegatiListGridField flgParteDispositivoSalvato;
	protected AllegatiListGridField flgParteDispositivo;
	protected AllegatiListGridField flgParere;
	protected AllegatiListGridField idTipoFileAllegatoSalvato;	
	protected AllegatiListGridField idTipoFileAllegato;
	protected AllegatiListGridField descTipoFileAllegato;
	protected AllegatiListGridField listaTipiFileAllegato;
	protected AllegatiListGridField descrizioneFileAllegato;
	protected AllegatiListGridField uploadFileAllegatoButton;
	protected AllegatiListGridField nomeFileAllegato;		
//	protected AllegatiListGridField previewFileAllegatoButton;	
	protected AllegatiListGridField altreOpFileAllegatoButton;	
	protected AllegatiListGridField fileFirmatoDigButton;
	protected AllegatiListGridField fileMarcatoTempButton;
	protected AllegatiListGridField dimensioneSignificativa;
	protected AllegatiListGridField impronta;
	protected AllegatiListGridField trasformaInPrimarioButton;
	protected AllegatiListGridField dettaglioUdAllegatoButton;
	protected AllegatiListGridField estremiProtUd;
	protected AllegatiListGridField dettaglioEstremiProtocolloButton;
	protected AllegatiListGridField dataRicezione;
	protected AllegatiListGridField tipoBarcodeContratto;
	protected AllegatiListGridField barcodeContratto;
	protected AllegatiListGridField energiaGasContratto;
	protected AllegatiListGridField tipoSezioneContratto;
	protected AllegatiListGridField codContratto;
	protected AllegatiListGridField flgPresentiFirmeContratto;
	protected AllegatiListGridField flgFirmeCompleteContratto;
	protected AllegatiListGridField nroFirmePrevisteContratto;
	protected AllegatiListGridField nroFirmeCompilateContratto; 
	protected AllegatiListGridField flgIncertezzaRilevazioneFirmeContratto; 
	protected AllegatiListGridField dettaglioDocContrattiBarcodeButton;
	protected AllegatiListGridField flgSostituisciVerPrec;
	protected AllegatiListGridField flgOriginaleCartaceo;
	protected AllegatiListGridField flgCopiaSostitutiva;	
	protected AllegatiListGridField flgTimbratoFilePostReg;
	protected AllegatiListGridField opzioniTimbro;
	protected AllegatiListGridField flgTimbraFilePostReg;
	protected AllegatiListGridField emailButtons; //TODO da gestire?
	protected AllegatiListGridField flgNoPubblAllegato;
	protected AllegatiListGridField flgPubblicaSeparato;
	protected AllegatiListGridField flgDaUnireAlDispositivo;
	protected AllegatiListGridField posAllegatiUniti;
	protected AllegatiListGridField flgPaginaFileUnione;
	protected AllegatiListGridField nroPagFileUnione;
	protected AllegatiListGridField nroPagFileUnioneOmissis;
	protected AllegatiListGridField flgDatiProtettiTipo1;
	protected AllegatiListGridField flgDatiProtettiTipo2;
	protected AllegatiListGridField flgDatiProtettiTipo3;
	protected AllegatiListGridField flgDatiProtettiTipo4;
	protected AllegatiListGridField flgDatiSensibili;	
	protected AllegatiListGridField idDocOmissis;
	protected AllegatiListGridField uriFileOmissis; 
	protected AllegatiListGridField infoFileOmissis; 
	protected AllegatiListGridField remoteUriOmissis; 
	protected AllegatiListGridField isChangedOmissis; 
	protected AllegatiListGridField nomeFileOrigOmissis; 
	protected AllegatiListGridField nomeFileTifOmissis; 
	protected AllegatiListGridField uriFileTifOmissis;
	protected AllegatiListGridField nomeFileVerPreFirmaOmissis;
	protected AllegatiListGridField uriFileVerPreFirmaOmissis;
	protected AllegatiListGridField improntaVerPreFirmaOmissis;
	protected AllegatiListGridField infoFileVerPreFirmaOmissis;
	protected AllegatiListGridField nroUltimaVersioneOmissis;
	protected AllegatiListGridField uploadFileOmissisButton;
	protected AllegatiListGridField nomeFileOmissis;	
//	protected AllegatiListGridField previewFileOmissisButton;
	protected AllegatiListGridField altreOpFileOmissisButton;	
	protected AllegatiListGridField fileFirmatoDigOmissisButton;
	protected AllegatiListGridField fileMarcatoTempOmissisButton;
	protected AllegatiListGridField dimensioneSignificativaOmissis;
	protected AllegatiListGridField improntaOmissis;
	protected AllegatiListGridField flgSostituisciVerPrecOmissis;	
	protected AllegatiListGridField flgTimbratoFilePostRegOmissis;
	protected AllegatiListGridField opzioniTimbroOmissis;
	protected AllegatiListGridField flgTimbraFilePostRegOmissis;
	protected AllegatiListGridField numFileCaricatiInUploadMultiplo;
	protected AllegatiListGridField idUDScansione;
	protected AllegatiListGridField flgGenAutoDaModello;
	protected AllegatiListGridField flgGenDaModelloDaFirmare;
	protected AllegatiListGridField nroAllegato;
		
	protected AllegatiListGridField esitoInvioACTASerieAttiIntegrali;
	protected AllegatiListGridField esitoInvioACTASeriePubbl;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	protected ControlListGridField selezionaButtonField;
	
	protected FileMultipleUploadItemWithFirmaAndMimeType uploadButton;
	protected ImgButtonItem importaFileDocumentiButton;
	
//	protected HashMap<String, CheckboxItem> checkboxItemList = new HashMap<String, CheckboxItem>();

	protected boolean readOnly;
	protected boolean soloPreparazioneVersPubblicazione;
	protected boolean soloOmissisModProprietaFile;
	private boolean attivaModificaEsclusionePubblicazione;
	private boolean aggiuntaFile;
	private boolean canEditOnlyOmissis;
	
	protected HashMap<String, HashSet<String>> mappaErrori = new HashMap<String, HashSet<String>>();
	
	protected int count = 0;
	private int indexLastAdd = -1;
	
	private int numFileCaricatiInErrore = 0;
	private int numFileCaricatiInUploadMultiploGlobal = 0;
	
	public AllegatiGridItem(String name) {
		this(name, "listaAllegati");
	}

	public AllegatiGridItem(String name, String nomeListaPref) {
		
		super(name, nomeListaPref);
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new AllegatiListGridField("id"); id.setHidden(true); id.setCanHide(false);		 
		flgSalvato = new AllegatiListGridField("flgSalvato"); flgSalvato.setHidden(true); flgSalvato.setCanHide(false);		 
		flgNoModificaDati = new AllegatiListGridField("flgNoModificaDati"); flgNoModificaDati.setHidden(true); flgNoModificaDati.setCanHide(false);
		
		idUdAppartenenza = new AllegatiListGridField("idUdAppartenenza"); idUdAppartenenza.setHidden(true); idUdAppartenenza.setCanHide(false);
		isUdSenzaFileImportata = new AllegatiListGridField("isUdSenzaFileImportata"); isUdSenzaFileImportata.setHidden(true); isUdSenzaFileImportata.setCanHide(false);
		idUdAllegato = new AllegatiListGridField("idUdAllegato"); idUdAllegato.setHidden(true); idUdAllegato.setCanHide(false);
		idTask = new AllegatiListGridField("idTask"); idTask.setHidden(true); idTask.setCanHide(false);
		idEmail = new AllegatiListGridField("idEmail"); idEmail.setHidden(true); idEmail.setCanHide(false);
		idAttachEmail = new AllegatiListGridField("idAttachEmail"); idAttachEmail.setHidden(true); idAttachEmail.setCanHide(false);
		collegaDocumentoImportato = new AllegatiListGridField("collegaDocumentoImportato"); collegaDocumentoImportato.setHidden(true); collegaDocumentoImportato.setCanHide(false);
		idDocAllegato = new AllegatiListGridField("idDocAllegato"); idDocAllegato.setHidden(true); idDocAllegato.setCanHide(false);
		fileImportato = new AllegatiListGridField("fileImportato"); fileImportato.setHidden(true); fileImportato.setCanHide(false);
		uriFileAllegato = new AllegatiListGridField("uriFileAllegato"); uriFileAllegato.setHidden(true); uriFileAllegato.setCanHide(false);
		tsInsLastVerFileAllegato = new AllegatiListGridField("tsInsLastVerFileAllegato"); tsInsLastVerFileAllegato.setHidden(true); tsInsLastVerFileAllegato.setCanHide(false);				
		infoFile = new AllegatiListGridField("infoFile"); infoFile.setHidden(true); infoFile.setCanHide(false);
		remoteUri = new AllegatiListGridField("remoteUri"); remoteUri.setHidden(true); remoteUri.setCanHide(false);
		isChanged = new AllegatiListGridField("isChanged"); isChanged.setHidden(true); isChanged.setCanHide(false);
		nomeFileAllegatoOrig = new AllegatiListGridField("nomeFileAllegatoOrig"); nomeFileAllegatoOrig.setHidden(true); nomeFileAllegatoOrig.setCanHide(false);
		nomeFileAllegatoTif = new AllegatiListGridField("nomeFileAllegatoTif"); nomeFileAllegatoTif.setHidden(true); nomeFileAllegatoTif.setCanHide(false);
		uriFileAllegatoTif = new AllegatiListGridField("uriFileAllegatoTif"); uriFileAllegatoTif.setHidden(true); uriFileAllegatoTif.setCanHide(false);
		nomeFileVerPreFirma = new AllegatiListGridField("nomeFileVerPreFirma"); nomeFileVerPreFirma.setHidden(true); nomeFileVerPreFirma.setCanHide(false);
		uriFileVerPreFirma = new AllegatiListGridField("uriFileVerPreFirma"); uriFileVerPreFirma.setHidden(true); uriFileVerPreFirma.setCanHide(false);
		improntaVerPreFirma = new AllegatiListGridField("improntaVerPreFirma"); improntaVerPreFirma.setHidden(true); improntaVerPreFirma.setCanHide(false);
		infoFileVerPreFirma = new AllegatiListGridField("infoFileVerPreFirma"); infoFileVerPreFirma.setHidden(true); infoFileVerPreFirma.setCanHide(false);
		nroUltimaVersione = new AllegatiListGridField("nroUltimaVersione"); nroUltimaVersione.setHidden(true); nroUltimaVersione.setCanHide(false);
		idUDScansione = new AllegatiListGridField("idUDScansione"); idUDScansione.setHidden(true); idUDScansione.setCanHide(false);
		flgGenAutoDaModello = new AllegatiListGridField("flgGenAutoDaModello"); flgGenAutoDaModello.setHidden(true); flgGenAutoDaModello.setCanHide(false);
		flgGenDaModelloDaFirmare = new AllegatiListGridField("flgGenDaModelloDaFirmare"); flgGenDaModelloDaFirmare.setHidden(true); flgGenDaModelloDaFirmare.setCanHide(false);
		
		nroAllegato = new AllegatiListGridField("nroAllegato"); nroAllegato.setHidden(true); nroAllegato.setCanHide(false);
		
		mostraErrori = new AllegatiListGridField("mostraErrori");
		mostraErrori.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		mostraErrori.setCanHide(false);
		mostraErrori.setCanDragResize(false);				
		mostraErrori.setWidth(25);		
		mostraErrori.setAttribute("custom", true);	
		mostraErrori.setAlign(Alignment.CENTER);
		mostraErrori.setShowHover(true);		
		mostraErrori.setCanReorder(false);
		mostraErrori.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(record.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {
					return buildImgButtonHtml("exclamation.png");
				}
				return null;
			}
		});
		mostraErrori.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(record.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {
					return "Mostra errori";
				}
				return null;				
			}
		});		
		mostraErrori.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(listRecord.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {					
					StringBuffer buffer = new StringBuffer();
					buffer.append("<ul>");
					for(String errore : listaErrori) {
						buffer.append("<li>" + errore + "</li>");			
					}
					buffer.append("</ul>");			
					SC.warn(buffer.toString());
				}																							
			}
		});	
			
		numeroAllegato = new AllegatiListGridField("numeroProgrAllegato", "N°"); 
		numeroAllegato.setWidth(40);
		numeroAllegato.setType(ListGridFieldType.INTEGER);
		
		numeroAllegatoAfterDrop = new AllegatiListGridField("numeroProgrAllegatoAfterDrop"); numeroAllegatoAfterDrop.setHidden(true); numeroAllegatoAfterDrop.setCanHide(false);
		
		flgProvEsterna = new AllegatiListGridField("flgProvEsterna", getTitleFlgProvEsterna());			
		flgProvEsterna.setType(ListGridFieldType.ICON);
		flgProvEsterna.setWidth(30);
		flgProvEsterna.setIconWidth(16);
		flgProvEsterna.setIconHeight(16);
		flgProvEsterna.setDefaultValue(false);
		Map<String, String> flgProvEsternaValueIcons = new HashMap<String, String>();		
		flgProvEsternaValueIcons.put("true", "protocollazione/provEsterna.png"); //TODO sarebbe meglio non utilizzare quella dei soggetti ma metterne una ad hoc per gli allegati (lettera E)
		flgProvEsternaValueIcons.put("false", "blank.png");
		flgProvEsternaValueIcons.put("undefined", "blank.png");
		flgProvEsternaValueIcons.put("", "blank.png");
		flgProvEsterna.setValueIcons(flgProvEsternaValueIcons);		
		flgProvEsterna.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgProvEsterna") != null && "true".equals(record.getAttribute("flgProvEsterna"))) {
					return "Provenienza esterna";
				}				
				return null;
			}
		});
		flgProvEsterna.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowFlgProvEsterna();
			}
		});	
		
		flgParteDispositivoSalvato = new AllegatiListGridField("flgParteDispositivoSalvato"); flgParteDispositivoSalvato.setHidden(true); flgParteDispositivoSalvato.setCanHide(false);
		
		flgParteDispositivo = new AllegatiListGridField("flgParteDispositivo", getTitleFlgParteDispositivo());			
		/*
		flgParteDispositivo.setAttribute("isCheckEditabile", true);
		flgParteDispositivo.setAttribute("custom", true);	
		flgParteDispositivo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return null;
			}
		});
		*/		
		flgParteDispositivo.setType(ListGridFieldType.ICON);
		flgParteDispositivo.setWidth(30);
		flgParteDispositivo.setIconWidth(16);
		flgParteDispositivo.setIconHeight(16);
		flgParteDispositivo.setDefaultValue(false);
		Map<String, String> flgParteDispositivoValueIcons = new HashMap<String, String>();		
		flgParteDispositivoValueIcons.put("true", "attiInLavorazione/parteIntegrante.png");
		flgParteDispositivoValueIcons.put("false", "blank.png");
		flgParteDispositivoValueIcons.put("undefined", "blank.png");
		flgParteDispositivoValueIcons.put("", "blank.png");
		/* TODOCHECK
		flgParteDispositivoValueIcons.put("true", "checked.png");
		flgParteDispositivoValueIcons.put("false", "unchecked.png");
		flgParteDispositivoValueIcons.put("undefined", "unchecked.png");
		flgParteDispositivoValueIcons.put("", "unchecked.png");
		*/
		flgParteDispositivo.setValueIcons(flgParteDispositivoValueIcons);		
		flgParteDispositivo.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgParteDispositivo") != null && "true".equals(record.getAttribute("flgParteDispositivo"))) {
					return getTitleFlgParteDispositivo();
				}				
				return null;
			}
		});
		flgParteDispositivo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowFlgParteDispositivo();
			}
		});		
		/* TODOCHECK
		flgParteDispositivo.setBaseStyle(it.eng.utility.Styles.cellClickable);		
		flgParteDispositivo.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) {									
					boolean checked = event.getRecord().getAttribute("flgParteDispositivo") != null && "true".equalsIgnoreCase(event.getRecord().getAttribute("flgParteDispositivo"));
					if(checked) {
						event.getRecord().setAttribute("flgParteDispositivo", false);
						event.getRecord().setAttribute("flgNoPubblAllegato", true);
						event.getRecord().setAttribute("flgPubblicaSeparato", false);
						event.getRecord().setAttribute("flgDatiSensibili", false);
					} else {
						event.getRecord().setAttribute("flgParteDispositivo", true);
					}
					updateGridItemValue();
					refreshNroAllegato();
				}
			}
		});
		*/

		flgParere = new AllegatiListGridField("flgParere", getTitleFlgParere());			
		flgParere.setType(ListGridFieldType.ICON);
		flgParere.setWidth(30);
		flgParere.setIconWidth(16);
		flgParere.setIconHeight(16);
		flgParere.setDefaultValue(false);
		Map<String, String> flgParereValueIcons = new HashMap<String, String>();		
		flgParereValueIcons.put("true", "attiInLavorazione/parere.png");
		flgParereValueIcons.put("false", "blank.png");
		flgParereValueIcons.put("undefined", "blank.png");
		flgParereValueIcons.put("", "blank.png");
		flgParere.setValueIcons(flgParereValueIcons);		
		flgParere.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgParere") != null && "true".equals(record.getAttribute("flgParere"))) {
					return "trattasi di parere";
				}				
				return null;
			}
		});
		flgParere.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowFlgParere();
			}
		});	
		
		idTipoFileAllegatoSalvato = new AllegatiListGridField("idTipoFileAllegatoSalvato"); idTipoFileAllegatoSalvato.setHidden(true); idTipoFileAllegatoSalvato.setCanHide(false);		
		idTipoFileAllegato = new AllegatiListGridField("idTipoFileAllegato"); idTipoFileAllegato.setHidden(true); idTipoFileAllegato.setCanHide(false);
		listaTipiFileAllegato = new AllegatiListGridField("listaTipiFileAllegato"); listaTipiFileAllegato.setHidden(true); listaTipiFileAllegato.setCanHide(false);
		
		descTipoFileAllegato = new AllegatiListGridField("descTipoFileAllegato", "Tipo");
		descTipoFileAllegato.setEscapeHTML(true);
		
		descrizioneFileAllegato = new AllegatiListGridField("descrizioneFileAllegato", "Descrizione");
		descrizioneFileAllegato.setEscapeHTML(true);
		
		uploadFileAllegatoButton = new AllegatiListGridField("uploadFileAllegatoButton", !isDisableGridRecordComponent() ? "Upload file (vers. integrale)" : "");
		uploadFileAllegatoButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		uploadFileAllegatoButton.setCanDragResize(false);				
		uploadFileAllegatoButton.setWidth(25);		
		uploadFileAllegatoButton.setAttribute("custom", true);	
		uploadFileAllegatoButton.setAlign(Alignment.CENTER);
		uploadFileAllegatoButton.setShowHover(true);		
		uploadFileAllegatoButton.setCanReorder(false);
//		if(isDisableGridRecordComponent()) {	
//			uploadFileAllegatoButton.setCellFormatter(new CellFormatter() {	
//				
//				@Override
//				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
//					if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) || isModificabileVersOmissis(record)) {
//						return buildImgButtonHtml("file/fileopen.png");							
//					}
//					return null;
//				}
//			});
//			uploadFileAllegatoButton.setHoverCustomizer(new HoverCustomizer() {			
//				
//				@Override
//				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//					if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) || isModificabileVersOmissis(record)) {
//						return "Seleziona file";					
//					}
//					return null;						
//				}
//			});	
//		}		
		uploadFileAllegatoButton.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {				
				event.cancel();
//				if(isDisableGridRecordComponent()) {	
//					//TODO apro una popup per fare l'upload del file
//				}
			}
		});
		
		nomeFileAllegato = new AllegatiListGridField("nomeFileAllegato", getShowVersioneOmissis() ? getTitleNomeFileAllegato() + " (vers. integrale)" : getTitleNomeFileAllegato());	
//		nomeFileAllegato.setBaseStyle(it.eng.utility.Styles.cellLink);
		nomeFileAllegato.setEscapeHTML(true);
		nomeFileAllegato.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override 
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				String uriFileAllegato = listRecord.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
					if(PreviewWindow.canBePreviewed(infoFileAllegato)) {
						clickPreviewFile(listRecord, false);					
					} else {
						Layout.addMessage(new MessageBean("Il formato del file non permette di visualizzarne l'anteprima", "", MessageType.ERROR));
					}
					/*
					final Menu visualizzaScaricaFileMenu = new Menu();
					visualizzaScaricaFileMenu.setOverflow(Overflow.VISIBLE);
					visualizzaScaricaFileMenu.setShowIcons(true);
					visualizzaScaricaFileMenu.setSelectionType(SelectionStyle.SINGLE);
					visualizzaScaricaFileMenu.setCanDragRecordsOut(false);
					visualizzaScaricaFileMenu.setWidth("*");
					visualizzaScaricaFileMenu.setHeight("*");
					InfoFileRecord infoFileAllegato = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
					if(PreviewWindow.canBePreviewed(infoFileAllegato)) {
						MenuItem previewMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
						previewMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickPreviewFile(listRecord);
							}
						});
						visualizzaScaricaFileMenu.addItem(previewMenuItem);						
					}
					MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
					if(infoFileAllegato != null && infoFileAllegato.isFirmato() && infoFileAllegato.getTipoFirma().startsWith("CAdES")) {
						Menu downloadFirmatoSbustatoMenu = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile(listRecord);
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileSbustato(listRecord);
							}
						});
						downloadFirmatoSbustatoMenu.setItems(firmato, sbustato);
						downloadMenuItem.setSubmenu(downloadFirmatoSbustatoMenu);		
					} else {
						downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile(listRecord);
							}
						});
					}
					visualizzaScaricaFileMenu.addItem(downloadMenuItem);
					visualizzaScaricaFileMenu.showContextMenu();
					*/				
				}
			}
		});
		
		/*
		previewFileAllegatoButton = new AllegatiListGridField("previewFileAllegatoButton");
		previewFileAllegatoButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		previewFileAllegatoButton.setCanDragResize(false);				
		previewFileAllegatoButton.setWidth(25);		
		previewFileAllegatoButton.setAttribute("custom", true);	
		previewFileAllegatoButton.setAlign(Alignment.CENTER);
		previewFileAllegatoButton.setShowHover(true);		
		previewFileAllegatoButton.setCanReorder(false);
		previewFileAllegatoButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					if(PreviewWindow.canBePreviewed(infoFileAllegato)) {
						return buildImgButtonHtml("file/preview.png");				
					}							
				} 	
				return null;		
			}
		});
		previewFileAllegatoButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					if(PreviewWindow.canBePreviewed(infoFileAllegato)) {
						return I18NUtil.getMessages().protocollazione_detail_previewButton_prompt();				
					}							
				} 	
				return null;								
			}
		});		
		previewFileAllegatoButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				String uriFileAllegato = listRecord.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
					if(PreviewWindow.canBePreviewed(infoFileAllegato)) {
						clickPreviewFile(listRecord);						
					}							
				} 									
			}
		});
		*/
		
		altreOpFileAllegatoButton = new AllegatiListGridField("altreOpFileAllegatoButton");
		altreOpFileAllegatoButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		altreOpFileAllegatoButton.setCanDragResize(false);				
		altreOpFileAllegatoButton.setWidth(25);		
		altreOpFileAllegatoButton.setAttribute("custom", true);	
		altreOpFileAllegatoButton.setAlign(Alignment.CENTER);
		altreOpFileAllegatoButton.setShowHover(true);		
		altreOpFileAllegatoButton.setCanReorder(false);
		altreOpFileAllegatoButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				final String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) || (uriFileAllegato != null && !uriFileAllegato.equals(""))) {
					return buildImgButtonHtml("buttons/altreOp.png");
				}
				return null;
			}
		});
		altreOpFileAllegatoButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				final String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) || (uriFileAllegato != null && !uriFileAllegato.equals(""))) {
					return I18NUtil.getMessages().altreOpButton_prompt();
				}
				return null;
			}
		});		
		altreOpFileAllegatoButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final String uriFileAllegato = event.getRecord().getAttribute("uriFileAllegato");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) || (uriFileAllegato != null && !uriFileAllegato.equals(""))) {
					showAltreOpFileAllegatoMenu(event.getRecord());
				}																								
			}
		});	
			
		fileFirmatoDigButton = new AllegatiListGridField("fileFirmatoDigButton");
		fileFirmatoDigButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		fileFirmatoDigButton.setCanDragResize(false);				
		fileFirmatoDigButton.setWidth(25);		
		fileFirmatoDigButton.setAttribute("custom", true);	
		fileFirmatoDigButton.setAlign(Alignment.CENTER);
		fileFirmatoDigButton.setShowHover(true);		
		fileFirmatoDigButton.setCanReorder(false);
		fileFirmatoDigButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
						if(infoFileAllegato.isFirmaValida()) {
							return buildImgButtonHtml("firma/firma.png");
						} else {
							return buildImgButtonHtml("firma/firmaNonValida.png");
						}
					}					
				} 
				return null;					
			}
		});
		fileFirmatoDigButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
						if(infoFileAllegato.isFirmaValida()) {
							return I18NUtil.getMessages().protocollazione_detail_fileFirmatoDigButton_prompt();	
						} else {
							return I18NUtil.getMessages().protocollazione_detail_firmaNonValidaButton_prompt();
						}
					}					
				} 
				return null;								
			}
		});		
		fileFirmatoDigButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final String uriFileAllegato = event.getRecord().getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					final InfoFileRecord infoFileAllegato = event.getRecord().getAttribute("infoFile") != null ? new InfoFileRecord(event.getRecord().getAttributeAsRecord("infoFile")) : null;
					if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
						event.cancel();
						MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
						informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								generaFileFirmaAndPreview(infoFileAllegato, uriFileAllegato, false);								
							}
						});
						MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
								"protocollazione/stampaEtichetta.png");
						stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								generaFileFirmaAndPreview(infoFileAllegato, uriFileAllegato, true);								
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
				} 									
			}
		});	
		
		fileMarcatoTempButton = new AllegatiListGridField("fileMarcatoTempButton");
		fileMarcatoTempButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		fileMarcatoTempButton.setCanDragResize(false);				
		fileMarcatoTempButton.setWidth(25);		
		fileMarcatoTempButton.setAttribute("custom", true);	
		fileMarcatoTempButton.setAlign(Alignment.CENTER);
		fileMarcatoTempButton.setShowHover(true);		
		fileMarcatoTempButton.setCanReorder(false);
		fileMarcatoTempButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {					
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					if (infoFileAllegato != null && infoFileAllegato.getInfoFirmaMarca() != null && infoFileAllegato.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (infoFileAllegato.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							return buildImgButtonHtml("marcaTemporale/marcaTemporaleNonValida.png");
						} else {
							return buildImgButtonHtml("marcaTemporale/marcaTemporaleValida.png");
						}
					}
				}
				return null;
			}
		});		
		fileMarcatoTempButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					if (infoFileAllegato != null && infoFileAllegato.getInfoFirmaMarca() != null && infoFileAllegato.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (infoFileAllegato.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							return I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt();
						} else {
							Date dataOraMarcaTemporale = infoFileAllegato.getInfoFirmaMarca().getDataOraMarcaTemporale();
							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
							return I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale));
						}
					}
				}
				return null;							
			}
		});		
		
		dimensioneSignificativa = new AllegatiListGridField("dimensioneSignificativa");
		dimensioneSignificativa.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		dimensioneSignificativa.setCanDragResize(false);				
		dimensioneSignificativa.setWidth(25);		
		dimensioneSignificativa.setAttribute("custom", true);	
		dimensioneSignificativa.setAlign(Alignment.CENTER);
		dimensioneSignificativa.setShowHover(true);		
		dimensioneSignificativa.setCanReorder(false);
		dimensioneSignificativa.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					long dimAlertAllegato = getDimAlertAllegato(); 
					if(dimAlertAllegato > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > dimAlertAllegato) {
						return buildImgButtonHtml("warning.png");
					}					
				} 
				return null;					
			}
		});
		dimensioneSignificativa.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
					InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
					long dimAlertAllegato = getDimAlertAllegato(); 
					if(dimAlertAllegato > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > dimAlertAllegato) {						
						final float MEGABYTE = 1024L * 1024L;
						float dimensioneInMB = infoFileAllegato.getBytes() / MEGABYTE;						
						return "Dimensione significativa: " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB";
					} 				
				} 				
				return null;								
			}
		});	
		dimensioneSignificativa.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getDimAlertAllegato() > 0;
			}
		});		
		
		trasformaInPrimarioButton = new AllegatiListGridField("trasformaInPrimarioButton");
		trasformaInPrimarioButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		trasformaInPrimarioButton.setCanDragResize(false);				
		trasformaInPrimarioButton.setWidth(25);		
		trasformaInPrimarioButton.setAttribute("custom", true);	
		trasformaInPrimarioButton.setAlign(Alignment.CENTER);
		trasformaInPrimarioButton.setShowHover(true);		
		trasformaInPrimarioButton.setCanReorder(false);
		trasformaInPrimarioButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				final String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) && (uriFileAllegato != null && !uriFileAllegato.equals("")) && (sonoInMail() || isScansioneImgAssoc())) {
					return buildImgButtonHtml("buttons/exchange.png");
				}				
				return null;
			}
		});
		trasformaInPrimarioButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				final String uriFileAllegato = record.getAttribute("uriFileAllegato");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) && (uriFileAllegato != null && !uriFileAllegato.equals("")) && (sonoInMail() || isScansioneImgAssoc())) {
					return "Trasforma in primario";
				}
				return null;
			}
		});		
		trasformaInPrimarioButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final String uriFileAllegato = event.getRecord().getAttribute("uriFileAllegato");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) && (uriFileAllegato != null && !uriFileAllegato.equals("")) && (sonoInMail() || isScansioneImgAssoc())) {
					clickTrasformaInPrimario(grid.getRecordIndex(event.getRecord()));					
				}
			}
		});
		
		if(AurigaLayout.getParametroDBAsBoolean("SHOW_IMPRONTA_FILE")) {
			impronta = new AllegatiListGridField("impronta", getShowVersioneOmissis() ? "Impronta (vers. integrale)" : "Impronta");
			impronta.setAttribute("custom", true);	
			impronta.setShowHover(true);		
			impronta.setCanReorder(false);
			impronta.setCellFormatter(new CellFormatter() {	
				
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
					final String uriFileAllegato = record.getAttribute("uriFileAllegato");					
					if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
						InfoFileRecord lInfoFileRecord = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
						String impronta = lInfoFileRecord != null && lInfoFileRecord.getImpronta() != null ? lInfoFileRecord.getImpronta() : "";
						return impronta;
					}
					return null;
				}
			});
		} else {
			impronta = new AllegatiListGridField("impronta"); impronta.setHidden(true); impronta.setCanHide(false);			
		}
		
		dettaglioUdAllegatoButton = new AllegatiListGridField("dettaglioUdAllegatoButton");
		dettaglioUdAllegatoButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		dettaglioUdAllegatoButton.setCanDragResize(false);				
		dettaglioUdAllegatoButton.setWidth(25);		
		dettaglioUdAllegatoButton.setAttribute("custom", true);	
		dettaglioUdAllegatoButton.setAlign(Alignment.CENTER);
		dettaglioUdAllegatoButton.setShowHover(true);		
		dettaglioUdAllegatoButton.setCanReorder(false);
		dettaglioUdAllegatoButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(showDettaglioUdAllegato(record)) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		dettaglioUdAllegatoButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(showDettaglioUdAllegato(record)) {
					return "Apri dettaglio unità documentaria corrispondente all'allegato";								
				}
				return null;
			}
		});		
		dettaglioUdAllegatoButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				if(showDettaglioUdAllegato(listRecord)) {					
					String title = "Dettaglio unità documentaria corrispondente all'allegato";
					Record lRecord = new Record();
					lRecord.setAttribute("idUd", listRecord.getAttribute("idUdAllegato"));
					new DettaglioRegProtAssociatoWindow(lRecord, null, title);
				}
			}
		});
		
		if(isDocIstruttoriaDettFascicoloGenCompleto()) {
			estremiProtUd = new AllegatiListGridField("estremiProtUd", "Estremi registrazione");
		} else {
			estremiProtUd = new AllegatiListGridField("estremiProtUd"); estremiProtUd.setHidden(true); estremiProtUd.setCanHide(false);
		}
		
		dettaglioEstremiProtocolloButton = new AllegatiListGridField("dettaglioEstremiProtocolloButton");
		dettaglioEstremiProtocolloButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		dettaglioEstremiProtocolloButton.setCanDragResize(false);				
		dettaglioEstremiProtocolloButton.setWidth(25);		
		dettaglioEstremiProtocolloButton.setAttribute("custom", true);	
		dettaglioEstremiProtocolloButton.setAlign(Alignment.CENTER);
		dettaglioEstremiProtocolloButton.setShowHover(true);		
		dettaglioEstremiProtocolloButton.setCanReorder(false);
		dettaglioEstremiProtocolloButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(showDettaglioEstremiProtocollo(record)) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		dettaglioEstremiProtocolloButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(showDettaglioEstremiProtocollo(record)) {
					if(record.getAttribute("estremiProtUd") != null && !"".equals(record.getAttribute("estremiProtUd"))) {				
						return "Apri dettaglio registrazione " + record.getAttribute("estremiProtUd");			
					} else {
						return "Apri dettaglio documento";
					}
				}
				return null;
			}
		});		
		dettaglioEstremiProtocolloButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				if(showDettaglioEstremiProtocollo(listRecord)) {					
					if (listRecord.getAttribute("idUdAppartenenza") != null && !"".equalsIgnoreCase(listRecord.getAttribute("idUdAppartenenza"))){
						String title = null;
						if(listRecord.getAttribute("estremiProtUd") != null && !"".equals(listRecord.getAttribute("estremiProtUd"))) {				
							title = "Dettaglio registrazione " + listRecord.getAttribute("estremiProtUd");			
						} else {
							title = "Dettaglio documento";
						}
						Record lRecord = new Record();
						lRecord.setAttribute("idUd", listRecord.getAttribute("idUdAppartenenza"));
						new DettaglioRegProtAssociatoWindow(lRecord, null, title) {
							
							@Override
							protected void onDestroy() {
								super.onDestroy();
								String estremiProt = listRecord.getAttribute("estremiProtUd");
								if (estremiProt == null || "".equalsIgnoreCase(estremiProt)) {
									if (getBody() instanceof ProtocollazioneDetail) {
										Record recordProtocollato = ((ProtocollazioneDetail) getBody()).getRecordProtocollato();
										aggiornaEstremiProtUdAfterProtocolla(listRecord, recordProtocollato);							
									}
								}
							}
						};
					} else {
						SC.say("Non e' possibile accedere ai dettagli perche' il riferimento del protocollo di apertura e' inesistente.");
					}
				}
			}
		});
		
		dataRicezione = new AllegatiListGridField("dataRicezione"); dataRicezione.setHidden(true); dataRicezione.setCanHide(false);
//		dataRicezione = new AllegatiListGridField("dataRicezione", "Ricevuto il");
//		dataRicezione.setType(ListGridFieldType.DATE);
//		dataRicezione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		tipoBarcodeContratto = new AllegatiListGridField("tipoBarcodeContratto"); tipoBarcodeContratto.setHidden(true); tipoBarcodeContratto.setCanHide(false);
		barcodeContratto = new AllegatiListGridField("barcodeContratto"); barcodeContratto.setHidden(true); barcodeContratto.setCanHide(false);
		energiaGasContratto = new AllegatiListGridField("energiaGasContratto"); energiaGasContratto.setHidden(true); energiaGasContratto.setCanHide(false);
		tipoSezioneContratto = new AllegatiListGridField("tipoSezioneContratto"); tipoSezioneContratto.setHidden(true); tipoSezioneContratto.setCanHide(false);
		codContratto = new AllegatiListGridField("codContratto"); codContratto.setHidden(true); codContratto.setCanHide(false);
		flgPresentiFirmeContratto = new AllegatiListGridField("flgPresentiFirmeContratto"); flgPresentiFirmeContratto.setHidden(true); flgPresentiFirmeContratto.setCanHide(false);
		flgFirmeCompleteContratto = new AllegatiListGridField("flgFirmeCompleteContratto"); flgFirmeCompleteContratto.setHidden(true); flgFirmeCompleteContratto.setCanHide(false);
		nroFirmePrevisteContratto = new AllegatiListGridField("nroFirmePrevisteContratto"); nroFirmePrevisteContratto.setHidden(true); nroFirmePrevisteContratto.setCanHide(false);
		nroFirmeCompilateContratto = new AllegatiListGridField("nroFirmeCompilateContratto"); nroFirmeCompilateContratto.setHidden(true); nroFirmeCompilateContratto.setCanHide(false);
		flgIncertezzaRilevazioneFirmeContratto = new AllegatiListGridField("flgIncertezzaRilevazioneFirmeContratto"); flgIncertezzaRilevazioneFirmeContratto.setHidden(true); flgIncertezzaRilevazioneFirmeContratto.setCanHide(false);
				
		dettaglioDocContrattiBarcodeButton = new AllegatiListGridField("dettaglioDocContrattiBarcodeButton");
		dettaglioDocContrattiBarcodeButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		dettaglioDocContrattiBarcodeButton.setCanDragResize(false);				
		dettaglioDocContrattiBarcodeButton.setWidth(25);		
		dettaglioDocContrattiBarcodeButton.setAttribute("custom", true);	
		dettaglioDocContrattiBarcodeButton.setAlign(Alignment.CENTER);
		dettaglioDocContrattiBarcodeButton.setShowHover(true);		
		dettaglioDocContrattiBarcodeButton.setCanReorder(false);
		dettaglioDocContrattiBarcodeButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(showDettaglioDocContrattiBarcode()) {
					return buildImgButtonHtml("buttons/altriDati.png");
				}
				return null;
			}
		});
		dettaglioDocContrattiBarcodeButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(showDettaglioDocContrattiBarcode()) {
					return "Dettaglio doc. contratti";
				}
				return null;
			}
		});		
		dettaglioDocContrattiBarcodeButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				if(showDettaglioDocContrattiBarcode()) {
					Record lRecordDettDocContratti = new Record();
					lRecordDettDocContratti.setAttribute("tipoBarcode", event.getRecord().getAttribute("tipoBarcodeContratto"));
					lRecordDettDocContratti.setAttribute("barcode", event.getRecord().getAttribute("barcodeContratto"));
					lRecordDettDocContratti.setAttribute("energiaGas", event.getRecord().getAttribute("energiaGasContratto"));
					lRecordDettDocContratti.setAttribute("tipoSezioneContratto", event.getRecord().getAttribute("tipoSezioneContratto"));
					lRecordDettDocContratti.setAttribute("codContratto", event.getRecord().getAttribute("codContratto"));
					lRecordDettDocContratti.setAttribute("flgPresentiFirmeContratto", event.getRecord().getAttribute("flgPresentiFirmeContratto"));
					lRecordDettDocContratti.setAttribute("flgFirmeCompleteContratto", event.getRecord().getAttribute("flgFirmeCompleteContratto"));
					lRecordDettDocContratti.setAttribute("nroFirmePrevisteContratto", event.getRecord().getAttribute("nroFirmePrevisteContratto"));
					lRecordDettDocContratti.setAttribute("nroFirmeCompilateContratto", event.getRecord().getAttribute("nroFirmeCompilateContratto"));
					lRecordDettDocContratti.setAttribute("flgIncertezzaRilevazioneFirmeContratto", event.getRecord().getAttribute("flgIncertezzaRilevazioneFirmeContratto"));
					DettaglioDocContrattiPopup lDettaglioDocContrattiPopup = new DettaglioDocContrattiPopup(lRecordDettDocContratti);
					lDettaglioDocContrattiPopup.show();	
				}
			}
		});

		flgSostituisciVerPrec = new AllegatiListGridField("flgSostituisciVerPrec"); flgSostituisciVerPrec.setHidden(true); flgSostituisciVerPrec.setCanHide(false);
//		flgSostituisciVerPrec = new AllegatiListGridField("flgSostituisciVerPrec", "Sostituisci vers. prec.");
//		flgSostituisciVerPrec.setType(ListGridFieldType.ICON);
//		flgSostituisciVerPrec.setWidth(30);
//		flgSostituisciVerPrec.setIconWidth(16);
//		flgSostituisciVerPrec.setIconHeight(16);
//		flgSostituisciVerPrec.setDefaultValue(false);
//		Map<String, String> flgSostituisciVerPrecValueIcons = new HashMap<String, String>();		
//		flgSostituisciVerPrecValueIcons.put("true", "ok.png");
//		flgSostituisciVerPrecValueIcons.put("false", "blank.png");
//		flgSostituisciVerPrecValueIcons.put("undefined", "blank.png");
//		flgSostituisciVerPrecValueIcons.put("", "blank.png");
//		flgSostituisciVerPrec.setValueIcons(flgSostituisciVerPrecValueIcons);		
//		flgSostituisciVerPrec.setHoverCustomizer(new HoverCustomizer() {		
//			
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
//				if(record.getAttribute("flgSostituisciVerPrec") != null && "true".equals(record.getAttribute("flgSostituisciVerPrec"))) {
//					return "Sostituisci vers. prec.";
//				}				
//				return null;
//			}
//		});		
		
//		flgOriginaleCartaceo = new AllegatiListGridField("flgOriginaleCartaceo"); flgOriginaleCartaceo.setHidden(true); flgOriginaleCartaceo.setCanHide(false);
		flgOriginaleCartaceo = new AllegatiListGridField("flgOriginaleCartaceo", "Originale cartaceo");
		flgOriginaleCartaceo.setType(ListGridFieldType.ICON);
		flgOriginaleCartaceo.setWidth(30);
		flgOriginaleCartaceo.setIconWidth(16);
		flgOriginaleCartaceo.setIconHeight(16);
		flgOriginaleCartaceo.setDefaultValue(false);
		Map<String, String> flgOriginaleCartaceoValueIcons = new HashMap<String, String>();		
		flgOriginaleCartaceoValueIcons.put("true", "ok.png");
		flgOriginaleCartaceoValueIcons.put("false", "blank.png");
		flgOriginaleCartaceoValueIcons.put("undefined", "blank.png");
		flgOriginaleCartaceoValueIcons.put("", "blank.png");
		flgOriginaleCartaceo.setValueIcons(flgOriginaleCartaceoValueIcons);		
		flgOriginaleCartaceo.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgOriginaleCartaceo") != null && "true".equals(record.getAttribute("flgOriginaleCartaceo"))) {
					return "Originale cartaceo";
				}				
				return null;
			}
		});		
		flgOriginaleCartaceo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isProtInModalitaWizard();
			}
		});		
		
//		flgCopiaSostitutiva = new AllegatiListGridField("flgCopiaSostitutiva"); flgCopiaSostitutiva.setHidden(true); flgCopiaSostitutiva.setCanHide(false);z
		flgCopiaSostitutiva = new AllegatiListGridField("flgCopiaSostitutiva", "Copia sostitutiva");
		flgCopiaSostitutiva.setType(ListGridFieldType.ICON);
		flgCopiaSostitutiva.setWidth(30);
		flgCopiaSostitutiva.setIconWidth(16);
		flgCopiaSostitutiva.setIconHeight(16);
		flgCopiaSostitutiva.setDefaultValue(false);
		Map<String, String> flgCopiaSostitutivaValueIcons = new HashMap<String, String>();		
		flgCopiaSostitutivaValueIcons.put("true", "ok.png");
		flgCopiaSostitutivaValueIcons.put("false", "blank.png");
		flgCopiaSostitutivaValueIcons.put("undefined", "blank.png");
		flgCopiaSostitutivaValueIcons.put("", "blank.png");
		flgCopiaSostitutiva.setValueIcons(flgCopiaSostitutivaValueIcons);		
		flgCopiaSostitutiva.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgCopiaSostitutiva") != null && "true".equals(record.getAttribute("flgCopiaSostitutiva"))) {
					return "Copia sostitutiva";
				}				
				return null;
			}
		});		
		flgCopiaSostitutiva.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isProtInModalitaWizard();
			}
		});		
		
		flgTimbratoFilePostReg = new AllegatiListGridField("flgTimbratoFilePostReg"); flgTimbratoFilePostReg.setHidden(true); flgTimbratoFilePostReg.setCanHide(false);
		opzioniTimbro = new AllegatiListGridField("opzioniTimbro"); opzioniTimbro.setHidden(true); opzioniTimbro.setCanHide(false);
		
//		flgTimbraFilePostReg = new AllegatiListGridField("flgTimbraFilePostReg"); flgTimbraFilePostReg.setHidden(true); flgTimbraFilePostReg.setCanHide(false);
		flgTimbraFilePostReg = new AllegatiListGridField("flgTimbraFilePostReg", "Da timbrare");
		flgTimbraFilePostReg.setType(ListGridFieldType.ICON);
		flgTimbraFilePostReg.setWidth(30);
		flgTimbraFilePostReg.setIconWidth(16);
		flgTimbraFilePostReg.setIconHeight(16);
		flgTimbraFilePostReg.setDefaultValue(false);
		Map<String, String> flgTimbraFilePostRegValueIcons = new HashMap<String, String>();		
		flgTimbraFilePostRegValueIcons.put("true", "ok.png");
		flgTimbraFilePostRegValueIcons.put("false", "blank.png");
		flgTimbraFilePostRegValueIcons.put("undefined", "blank.png");
		flgTimbraFilePostRegValueIcons.put("", "blank.png");
		flgTimbraFilePostReg.setValueIcons(flgTimbraFilePostRegValueIcons);		
		flgTimbraFilePostReg.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgTimbraFilePostReg") != null && "true".equals(record.getAttribute("flgTimbraFilePostReg"))) {
					return "Timbra file post reg.";
				}				
				return null;
			}
		});			
		flgTimbraFilePostReg.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isAttivaTimbraturaFilePostRegAllegato();
			}
		});		
		
		flgNoPubblAllegato = new AllegatiListGridField("flgNoPubblAllegato", getTitleFlgNoPubblAllegato());		
		/*
		flgNoPubblAllegato.setAttribute("isCheckEditabile", true);
		flgNoPubblAllegato.setAttribute("custom", true);	
		flgNoPubblAllegato.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return null;
			}
		});
		*/		
		flgNoPubblAllegato.setType(ListGridFieldType.ICON);
		flgNoPubblAllegato.setWidth(30);
		flgNoPubblAllegato.setIconWidth(16);
		flgNoPubblAllegato.setIconHeight(16);
		flgNoPubblAllegato.setDefaultValue(false);
		Map<String, String> flgNoPubblAllegatoValueIcons = new HashMap<String, String>();	
		flgNoPubblAllegatoValueIcons.put("true", "attiInLavorazione/escludiDaPubbl.png");
		flgNoPubblAllegatoValueIcons.put("false", "blank.png");
		flgNoPubblAllegatoValueIcons.put("undefined", "blank.png");
		flgNoPubblAllegatoValueIcons.put("", "blank.png");
		/* TODOCHECK
		flgNoPubblAllegatoValueIcons.put("true", "checked.png");
		flgNoPubblAllegatoValueIcons.put("false", "unchecked.png");
		flgNoPubblAllegatoValueIcons.put("undefined", "unchecked.png");
		flgNoPubblAllegatoValueIcons.put("", "unchecked.png");
		*/
		flgNoPubblAllegato.setValueIcons(flgNoPubblAllegatoValueIcons);		
		flgNoPubblAllegato.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgNoPubblAllegato") != null && "true".equals(record.getAttribute("flgNoPubblAllegato"))) {
					return "escluso dalla pubblicazione";
				}				
				return null;
			}
		});
		flgNoPubblAllegato.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowFlgNoPubblAllegato();
			}
		});		
		/* TODOCHECK
		flgNoPubblAllegato.setBaseStyle(it.eng.utility.Styles.cellClickable);		
		flgNoPubblAllegato.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				boolean flgParteDispositivo = event.getRecord().getAttribute("flgParteDispositivo") != null && "true".equals(event.getRecord().getAttribute("flgParteDispositivo"));
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord()) && flgParteDispositivo) {	
					boolean checked = event.getRecord().getAttribute("flgNoPubblAllegato") != null && "true".equalsIgnoreCase(event.getRecord().getAttribute("flgNoPubblAllegato"));
					if(checked) {
						event.getRecord().setAttribute("flgNoPubblAllegato", false);
					} else {
						event.getRecord().setAttribute("flgNoPubblAllegato", true);						
					}
					updateGridItemValue();
					refreshNroAllegato();
				}
			}
		});
		*/
		
		flgPubblicaSeparato = new AllegatiListGridField("flgPubblicaSeparato"); flgPubblicaSeparato.setHidden(true); flgPubblicaSeparato.setCanHide(false);
//		flgPubblicaSeparato = new AllegatiListGridField("flgPubblicaSeparato", getTitleFlgPubblicaSeparato());		
//		/*
//		flgPubblicaSeparato.setAttribute("isCheckEditabile", true);
//		flgPubblicaSeparato.setAttribute("custom", true);	
//		flgPubblicaSeparato.setCellFormatter(new CellFormatter() {
//			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				return null;
//			}
//		});
//		*/		
//		flgPubblicaSeparato.setType(ListGridFieldType.ICON);
//		flgPubblicaSeparato.setWidth(30);
//		flgPubblicaSeparato.setIconWidth(16);
//		flgPubblicaSeparato.setIconHeight(16);
//		flgPubblicaSeparato.setDefaultValue(false);
//		Map<String, String> flgPubblicaSeparatoValueIcons = new HashMap<String, String>();		
//		flgPubblicaSeparatoValueIcons.put("true", "ok.png");
//		flgPubblicaSeparatoValueIcons.put("false", "blank.png");
//		flgPubblicaSeparatoValueIcons.put("undefined", "blank.png");
//		flgPubblicaSeparatoValueIcons.put("", "blank.png");
//		/* TODOCHECK
//		flgPubblicaSeparatoValueIcons.put("true", "checked.png");
//		flgPubblicaSeparatoValueIcons.put("false", "unchecked.png");
//		flgPubblicaSeparatoValueIcons.put("undefined", "unchecked.png");
//		flgPubblicaSeparatoValueIcons.put("", "unchecked.png");
//		*/
//		flgPubblicaSeparato.setValueIcons(flgPubblicaSeparatoValueIcons);		
//		flgPubblicaSeparato.setHoverCustomizer(new HoverCustomizer() {		
//			
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
//				if(record.getAttribute("flgPubblicaSeparato") != null && "true".equals(record.getAttribute("flgPubblicaSeparato"))) {
//					return getTitleFlgPubblicaSeparato();
//				}				
//				return null;
//			}
//		});
//		flgPubblicaSeparato.setShowIfCondition(new ListGridFieldIfFunction() {
//			
//			@Override
//			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
//				return getShowFlgPubblicaSeparato() && !getFlgPubblicazioneAllegatiUguale();
//			}
//		});
//		/* TODOCHECK
//		flgPubblicaSeparato.setBaseStyle(it.eng.utility.Styles.cellClickable);		
//		flgPubblicaSeparato.addRecordClickHandler(new RecordClickHandler() {				
//			@Override
//			public void onRecordClick(RecordClickEvent event) {
//				event.cancel();				
//				boolean flgParteDispositivo = event.getRecord().getAttribute("flgParteDispositivo") != null && "true".equals(event.getRecord().getAttribute("flgParteDispositivo"));
//				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord()) && flgParteDispositivo) {	
//					boolean checked = event.getRecord().getAttribute("flgPubblicaSeparato") != null && "true".equalsIgnoreCase(event.getRecord().getAttribute("flgPubblicaSeparato"));
//					if(checked) {
//						event.getRecord().setAttribute("flgPubblicaSeparato", false);
//					} else {						
//						event.getRecord().setAttribute("flgPubblicaSeparato", true);
//					}
//					updateGridItemValue();
//					refreshNroAllegato();
//				}
//			}
//		});
//		*/
				
		flgDaUnireAlDispositivo = new AllegatiListGridField("flgDaUnireAlDispositivo", "Da unire al dispositivo");	
		flgDaUnireAlDispositivo.setAttribute("custom", true);	
		flgDaUnireAlDispositivo.setAlign(Alignment.CENTER);
		flgDaUnireAlDispositivo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				boolean isParere = getShowFlgParere() && record.getAttribute("flgParere") != null && "true".equals(record.getAttribute("flgParere"));
				boolean isParteIntegrante = getShowFlgParteDispositivo() && record.getAttribute("flgParteDispositivo") != null && "true".equals(record.getAttribute("flgParteDispositivo"));
				if(isParere || isParteIntegrante) {					
					if(record.getAttribute("flgPubblicaSeparato") != null && "true".equals(record.getAttribute("flgPubblicaSeparato"))) {
						return buildIconHtml("attiInLavorazione/S.png");	
					}				
					return buildIconHtml("attiInLavorazione/U.png");
				}
				return null;
			}
		});	
		flgDaUnireAlDispositivo.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				boolean isParere = getShowFlgParere() && record.getAttribute("flgParere") != null && "true".equals(record.getAttribute("flgParere"));
				boolean isParteIntegrante = getShowFlgParteDispositivo() && record.getAttribute("flgParteDispositivo") != null && "true".equals(record.getAttribute("flgParteDispositivo"));
				if(isParere || isParteIntegrante) {	
					if(record.getAttribute("flgPubblicaSeparato") != null && "true".equals(record.getAttribute("flgPubblicaSeparato"))) {
						return "Separato";
					}				
					return "Unito";
				}
				return null;
			}
		});
		flgDaUnireAlDispositivo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowFlgPubblicaSeparato() && !getFlgPubblicazioneAllegatiUguale();
			}
		});
		
		posAllegatiUniti = new AllegatiListGridField("posAllegatiUniti", "Unione dopo pag.");
		posAllegatiUniti.setAttribute("custom", true);	
		posAllegatiUniti.setCellAlign(Alignment.LEFT);	
		posAllegatiUniti.setWidth(150);
		posAllegatiUniti.setCanGroupBy(false);		
		posAllegatiUniti.setCanReorder(false);
		posAllegatiUniti.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				boolean isParteIntegrante = getShowFlgParteDispositivo() && record.getAttribute("flgParteDispositivo") != null && "true".equals(record.getAttribute("flgParteDispositivo"));
				if(isParteIntegrante) {					
					boolean flgPubblicaSeparato = false;
					if(getShowFlgPubblicaSeparato()) {
						if(getFlgPubblicazioneAllegatiUguale()) {
							flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
						} else {
							flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && record.getAttributeAsBoolean("flgPubblicaSeparato");
						}
					}						
					if(flgPubblicaSeparato) {
						return null;	
					}						
					String flgPaginaFileUnione = record.getAttribute("flgPaginaFileUnione");					
					String nroPagFileUnione = flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione) ? record.getAttribute("nroPagFileUnione") : "";
					return nroPagFileUnione != null && !"".equals(nroPagFileUnione) ? nroPagFileUnione : "ultima";					
				}
				return null;
			}
		});
		posAllegatiUniti.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				boolean isParteIntegrante = getShowFlgParteDispositivo() && record.getAttribute("flgParteDispositivo") != null && "true".equals(record.getAttribute("flgParteDispositivo"));
				if(isParteIntegrante) {					
					boolean flgPubblicaSeparato = false;
					if(getShowFlgPubblicaSeparato()) {
						if(getFlgPubblicazioneAllegatiUguale()) {
							flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
						} else {
							flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && record.getAttributeAsBoolean("flgPubblicaSeparato");
						}
					}						
					if(flgPubblicaSeparato) {
						return null;	
					}						
					String flgPaginaFileUnione = record.getAttribute("flgPaginaFileUnione");					
					String nroPagFileUnione = flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione) ? record.getAttribute("nroPagFileUnione") : "";
					String nroPagFileUnioneOmissis = flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione) ? record.getAttribute("nroPagFileUnioneOmissis") : "";
					if(getShowVersioneOmissis()) {
						String str = "";
						str += (nroPagFileUnione != null && !"".equals(nroPagFileUnione) ? nroPagFileUnione : "ultima") + "&nbsp;(vers.&nbsp;integrale)";
						str += "<br/>" + (nroPagFileUnioneOmissis != null && !"".equals(nroPagFileUnioneOmissis) ? nroPagFileUnioneOmissis : "ultima") + "&nbsp;(vers.&nbsp;per&nbsp;pubbl.)";
						return str;
					} else {
						return nroPagFileUnione != null && !"".equals(nroPagFileUnione) ? nroPagFileUnione : "ultima";					
					}	
				}
				return null;						
			}
		});		
		posAllegatiUniti.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				boolean isParere = getShowFlgParere() && record.getAttribute("flgParere") != null && "true".equals(record.getAttribute("flgParere"));
				boolean isParteIntegrante = getShowFlgParteDispositivo() && record.getAttribute("flgParteDispositivo") != null && "true".equals(record.getAttribute("flgParteDispositivo"));
				if(isParere || isParteIntegrante) {	
					boolean flgPubblicaSeparato = false;
					if(getShowFlgPubblicaSeparato()) {
						if(getFlgPubblicazioneAllegatiUguale()) {
							flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
						} else {
							flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && record.getAttributeAsBoolean("flgPubblicaSeparato");
						}
					}						
					if(flgPubblicaSeparato) {
						return Long.MAX_VALUE;	
					}	
					String flgPaginaFileUnione = record.getAttribute("flgPaginaFileUnione");									
					String nroPagFileUnione = flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione) ? record.getAttribute("nroPagFileUnione") : "";
					if(nroPagFileUnione != null && !"".equals(nroPagFileUnione)) {
						return new Integer(nroPagFileUnione).intValue();
					}
					return Integer.MAX_VALUE;
				}
				return Long.MAX_VALUE;
			}
		});
		posAllegatiUniti.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isAttivaSceltaPosizioneAllegatiUniti();
			}
		});
		
		flgPaginaFileUnione = new AllegatiListGridField("flgPaginaFileUnione"); flgPaginaFileUnione.setHidden(true); flgPaginaFileUnione.setCanHide(false);
		nroPagFileUnione = new AllegatiListGridField("nroPagFileUnione"); nroPagFileUnione.setHidden(true); nroPagFileUnione.setCanHide(false);
		nroPagFileUnioneOmissis = new AllegatiListGridField("nroPagFileUnioneOmissis"); nroPagFileUnioneOmissis.setHidden(true); nroPagFileUnioneOmissis.setCanHide(false);
					
		flgDatiProtettiTipo1 = new AllegatiListGridField("flgDatiProtettiTipo1"); flgDatiProtettiTipo1.setHidden(true); flgDatiProtettiTipo1.setCanHide(false);
		
		flgDatiProtettiTipo2 = new AllegatiListGridField("flgDatiProtettiTipo2"); flgDatiProtettiTipo2.setHidden(true); flgDatiProtettiTipo2.setCanHide(false);
		
		flgDatiProtettiTipo3 = new AllegatiListGridField("flgDatiProtettiTipo3"); flgDatiProtettiTipo3.setHidden(true); flgDatiProtettiTipo3.setCanHide(false);
		
		flgDatiProtettiTipo4 = new AllegatiListGridField("flgDatiProtettiTipo4"); flgDatiProtettiTipo4.setHidden(true); flgDatiProtettiTipo4.setCanHide(false);
		
		flgDatiSensibili = new AllegatiListGridField("flgDatiSensibili", getTitleFlgDatiSensibili());			
		/*
		flgDatiSensibili.setAttribute("isCheckEditabile", true);
		flgDatiSensibili.setAttribute("custom", true);	
		flgDatiSensibili.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return null;
			}
		});
		*/		
		flgDatiSensibili.setType(ListGridFieldType.ICON);		
		flgDatiSensibili.setWidth(30);
		flgDatiSensibili.setIconWidth(16);
		flgDatiSensibili.setIconHeight(16);
		flgDatiSensibili.setDefaultValue(false);
		Map<String, String> flgDatiSensibiliValueIcons = new HashMap<String, String>();		
		flgDatiSensibiliValueIcons.put("true", "lock.png");
		flgDatiSensibiliValueIcons.put("false", "blank.png");
		flgDatiSensibiliValueIcons.put("undefined", "blank.png");
		flgDatiSensibiliValueIcons.put("", "blank.png");
		/* TODOCHECK
		flgDatiSensibiliValueIcons.put("true", "checked.png");
		flgDatiSensibiliValueIcons.put("false", "unchecked.png");
		flgDatiSensibiliValueIcons.put("undefined", "unchecked.png");
		flgDatiSensibiliValueIcons.put("", "unchecked.png");
		*/
		flgDatiSensibili.setValueIcons(flgDatiSensibiliValueIcons);		
		flgDatiSensibili.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgDatiSensibili") != null && "true".equals(record.getAttribute("flgDatiSensibili"))) {
					return getTitleFlgDatiSensibili();
				}				
				return null;
			}
		});
		flgDatiSensibili.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});
		/* TODOCHECK
		flgDatiSensibili.setBaseStyle(it.eng.utility.Styles.cellClickable);		
		flgDatiSensibili.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) {	
					boolean checked = event.getRecord().getAttribute("flgDatiSensibili") != null && "true".equalsIgnoreCase(event.getRecord().getAttribute("flgDatiSensibili"));
					if(checked) {
						event.getRecord().setAttribute("flgDatiSensibili", false);
						event.getRecord().setAttribute("nomeFileOmissis", "");
						event.getRecord().setAttribute("uriFileOmissis", "");
						event.getRecord().setAttribute("nomeFileTifOmissis", "");
						event.getRecord().setAttribute("uriFileTifOmissis", "");
						event.getRecord().setAttribute("remoteUriOmissis", false);	
						event.getRecord().setAttribute("nomeFileVerPreFirmaOmissis", "");
						event.getRecord().setAttribute("uriFileVerPreFirmaOmissis", "");
					} else {
						event.getRecord().setAttribute("flgDatiSensibili", true);
					}
					updateGridItemValue();
					refreshNroAllegato();
				}
			}
		});
		*/

		idDocOmissis = new AllegatiListGridField("idDocOmissis"); idDocOmissis.setHidden(true); idDocOmissis.setCanHide(false);
		uriFileOmissis = new AllegatiListGridField("uriFileOmissis"); uriFileOmissis.setHidden(true); uriFileOmissis.setCanHide(false);
		infoFileOmissis = new AllegatiListGridField("infoFileOmissis"); infoFileOmissis.setHidden(true); infoFileOmissis.setCanHide(false);
		remoteUriOmissis = new AllegatiListGridField("remoteUriOmissis"); remoteUriOmissis.setHidden(true); remoteUriOmissis.setCanHide(false);
		isChangedOmissis = new AllegatiListGridField("isChangedOmissis"); isChangedOmissis.setHidden(true); isChangedOmissis.setCanHide(false);
		nomeFileOrigOmissis = new AllegatiListGridField("nomeFileOrigOmissis"); nomeFileOrigOmissis.setHidden(true); nomeFileOrigOmissis.setCanHide(false);
		nomeFileTifOmissis = new AllegatiListGridField("nomeFileTifOmissis"); nomeFileTifOmissis.setHidden(true); nomeFileTifOmissis.setCanHide(false);
		uriFileTifOmissis = new AllegatiListGridField("uriFileTifOmissis"); uriFileTifOmissis.setHidden(true); uriFileTifOmissis.setCanHide(false);
		nomeFileVerPreFirmaOmissis = new AllegatiListGridField("nomeFileVerPreFirmaOmissis"); nomeFileVerPreFirmaOmissis.setHidden(true); nomeFileVerPreFirmaOmissis.setCanHide(false);
		uriFileVerPreFirmaOmissis = new AllegatiListGridField("uriFileVerPreFirmaOmissis"); uriFileVerPreFirmaOmissis.setHidden(true); uriFileVerPreFirmaOmissis.setCanHide(false);
		improntaVerPreFirmaOmissis = new AllegatiListGridField("improntaVerPreFirmaOmissis"); improntaVerPreFirmaOmissis.setHidden(true); improntaVerPreFirmaOmissis.setCanHide(false);
		infoFileVerPreFirmaOmissis = new AllegatiListGridField("infoFileVerPreFirmaOmissis"); infoFileVerPreFirmaOmissis.setHidden(true); infoFileVerPreFirmaOmissis.setCanHide(false);
		nroUltimaVersioneOmissis = new AllegatiListGridField("nroUltimaVersioneOmissis"); nroUltimaVersioneOmissis.setHidden(true); nroUltimaVersioneOmissis.setCanHide(false);
		numFileCaricatiInUploadMultiplo = new AllegatiListGridField("numFileCaricatiInUploadMultiplo"); numFileCaricatiInUploadMultiplo.setHidden(true); numFileCaricatiInUploadMultiplo.setCanHide(false);							

		uploadFileOmissisButton = new AllegatiListGridField("uploadFileOmissisButton", !isDisableGridRecordComponent() ? "Upload file (vers. con omissis)" : "");
		uploadFileOmissisButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		uploadFileOmissisButton.setCanDragResize(false);				
		uploadFileOmissisButton.setWidth(25);		
		uploadFileOmissisButton.setAttribute("custom", true);	
		uploadFileOmissisButton.setAlign(Alignment.CENTER);
		uploadFileOmissisButton.setShowHover(true);		
		uploadFileOmissisButton.setCanReorder(false);
//		if(isDisableGridRecordComponent()) {	
//			uploadFileOmissisButton.setCellFormatter(new CellFormatter() {	
//				
//				@Override
//				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
//					if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) || isModificabileVersOmissis(record)) {
//						return buildImgButtonHtml("file/fileopen.png");							
//					}
//					return null;
//				}
//			});
//			uploadFileOmissisButton.setHoverCustomizer(new HoverCustomizer() {			
//				
//				@Override
//				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//					if((isEditable() && isShowEditButtons() && isShowModifyButton(record)) || isModificabileVersOmissis(record)) {
//						return "Seleziona file";					
//					}
//					return null;						
//				}
//			});	
//		}		
		uploadFileOmissisButton.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
//				if(isDisableGridRecordComponent()) {	
//					//TODO apro una popup per fare l'upload del file
//				}
			}
		});
		uploadFileOmissisButton.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});		
		
		nomeFileOmissis = new AllegatiListGridField("nomeFileOmissis", getTitleNomeFileAllegato() + " (vers. con omissis)");	
//		nomeFileOmissis.setBaseStyle(it.eng.utility.Styles.cellLink);
		nomeFileOmissis.setEscapeHTML(true);		
		nomeFileOmissis.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();			
				final ListGridRecord listRecord = event.getRecord();
				String uriFileOmissis = listRecord.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
					if(PreviewWindow.canBePreviewed(infoFileOmissis)) {
						clickPreviewFile(listRecord, true);		
					} else {
						Layout.addMessage(new MessageBean("Il formato del file non permette di visualizzarne l'anteprima", "", MessageType.ERROR));
					}
					/*
					final Menu visualizzaScaricaFileMenu = new Menu();
					visualizzaScaricaFileMenu.setOverflow(Overflow.VISIBLE);
					visualizzaScaricaFileMenu.setShowIcons(true);
					visualizzaScaricaFileMenu.setSelectionType(SelectionStyle.SINGLE);
					visualizzaScaricaFileMenu.setCanDragRecordsOut(false);
					visualizzaScaricaFileMenu.setWidth("*");
					visualizzaScaricaFileMenu.setHeight("*");
					InfoFileRecord infoFileOmissis = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
					if(PreviewWindow.canBePreviewed(infoFileOmissis)) {
						MenuItem previewMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
						previewMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickPreviewFileOmissis(listRecord);
							}
						});
						visualizzaScaricaFileMenu.addItem(previewMenuItem);			
					}
					MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
					if(infoFileOmissis != null && infoFileOmissis.isFirmato() && infoFileOmissis.getTipoFirma().startsWith("CAdES")) {
						Menu downloadFirmatoSbustatoMenu = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileOmissis(listRecord);
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileOmissisSbustato(listRecord);
							}
						});
						downloadFirmatoSbustatoMenu.setItems(firmato, sbustato);
						downloadMenuItem.setSubmenu(downloadFirmatoSbustatoMenu);		
					} else {
						downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileOmissis(listRecord);
							}
						});
					}
					visualizzaScaricaFileMenu.addItem(downloadMenuItem);
					visualizzaScaricaFileMenu.showContextMenu();	
					*/
				} 				
			}
		});
		nomeFileOmissis.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});
		
		/*
		previewFileOmissisButton = new AllegatiListGridField("previewFileOmissisButton");
		previewFileOmissisButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		previewFileOmissisButton.setCanDragResize(false);				
		previewFileOmissisButton.setWidth(25);		
		previewFileOmissisButton.setAttribute("custom", true);	
		previewFileOmissisButton.setAlign(Alignment.CENTER);
		previewFileOmissisButton.setShowHover(true);		
		previewFileOmissisButton.setCanReorder(false);
		previewFileOmissisButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					if(PreviewWindow.canBePreviewed(infoFileOmissis)) {
						return buildImgButtonHtml("file/preview.png");				
					}							
				} 	
				return null;		
			}
		});
		previewFileOmissisButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					if(PreviewWindow.canBePreviewed(infoFileOmissis)) {
						return I18NUtil.getMessages().protocollazione_detail_previewButton_prompt();				
					}							
				} 	
				return null;								
			}
		});		
		previewFileOmissisButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				String uriFileOmissis = listRecord.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
					if(PreviewWindow.canBePreviewed(infoFileOmissis)) {
						clickPreviewFileOmissis(listRecord);						
					}							
				} 									
			}
		});		
		previewFileOmissisButton.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});
		*/
		
		altreOpFileOmissisButton = new AllegatiListGridField("altreOpFileOmissisButton");
		altreOpFileOmissisButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		altreOpFileOmissisButton.setCanDragResize(false);				
		altreOpFileOmissisButton.setWidth(25);		
		altreOpFileOmissisButton.setAttribute("custom", true);	
		altreOpFileOmissisButton.setAlign(Alignment.CENTER);
		altreOpFileOmissisButton.setShowHover(true);		
		altreOpFileOmissisButton.setCanReorder(false);
		altreOpFileOmissisButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				final String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(record) && !isNonModificabileVersOmissis(record)) || (uriFileOmissis != null && !uriFileOmissis.equals(""))) {
					return buildImgButtonHtml("buttons/altreOp.png");							
				}
				return null;
			}
		});
		altreOpFileOmissisButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				final String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(record) && !isNonModificabileVersOmissis(record)) || (uriFileOmissis != null && !uriFileOmissis.equals(""))) {
					return I18NUtil.getMessages().altreOpButton_prompt();					
				}
				return null;						
			}
		});		
		altreOpFileOmissisButton.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final String uriFileOmissis = event.getRecord().getAttribute("uriFileOmissis");
				if((isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord()) && !isNonModificabileVersOmissis(event.getRecord())) || (uriFileOmissis != null && !uriFileOmissis.equals(""))) {
					showAltreOpFileOmissisMenu(event.getRecord());
				}						
			}
		});		
		altreOpFileOmissisButton.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});
		
		fileFirmatoDigOmissisButton = new AllegatiListGridField("fileFirmatoDigOmissisButton");
		fileFirmatoDigOmissisButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		fileFirmatoDigOmissisButton.setCanDragResize(false);				
		fileFirmatoDigOmissisButton.setWidth(25);
		fileFirmatoDigOmissisButton.setAttribute("custom", true);	
		fileFirmatoDigOmissisButton.setAlign(Alignment.CENTER);
		fileFirmatoDigOmissisButton.setShowHover(true);		
		fileFirmatoDigOmissisButton.setCanReorder(false);
		fileFirmatoDigOmissisButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {					
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
						if(infoFileOmissis.isFirmaValida()) {
							return buildImgButtonHtml("firma/firma.png");
						} else {
							return buildImgButtonHtml("firma/firmaNonValida.png");
						}
					}					
				} 
				return null;					
			}
		});
		fileFirmatoDigOmissisButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
						if(infoFileOmissis.isFirmaValida()) {
							return I18NUtil.getMessages().protocollazione_detail_fileFirmatoDigButton_prompt();	
						} else {
							return I18NUtil.getMessages().protocollazione_detail_firmaNonValidaButton_prompt();
						}
					}					
				} 
				return null;								
			}
		});		
		fileFirmatoDigOmissisButton.addRecordClickHandler(new RecordClickHandler() { 	 
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final String uriFileOmissis = event.getRecord().getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					final InfoFileRecord infoFileOmissis = event.getRecord().getAttribute("infoFileOmissis") != null ? new InfoFileRecord(event.getRecord().getAttributeAsRecord("infoFileOmissis")) : null;
					if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
						event.cancel();
						MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
						informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								generaFileFirmaAndPreview(infoFileOmissis, uriFileOmissis, false);								
							}
						});
						MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
								"protocollazione/stampaEtichetta.png");
						stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								generaFileFirmaAndPreview(infoFileOmissis, uriFileOmissis, true);								
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
				} 									
			}
		});	
		fileFirmatoDigOmissisButton.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});		
		
		fileMarcatoTempOmissisButton = new AllegatiListGridField("fileMarcatoTempOmissisButton");
		fileMarcatoTempOmissisButton.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		fileMarcatoTempOmissisButton.setCanDragResize(false);				
		fileMarcatoTempOmissisButton.setWidth(25);		
		fileMarcatoTempOmissisButton.setAttribute("custom", true);	
		fileMarcatoTempOmissisButton.setAlign(Alignment.CENTER);
		fileMarcatoTempOmissisButton.setShowHover(true);		
		fileMarcatoTempOmissisButton.setCanReorder(false);
		fileMarcatoTempOmissisButton.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {					
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					if (infoFileOmissis != null && infoFileOmissis.getInfoFirmaMarca() != null && infoFileOmissis.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (infoFileOmissis.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							return buildImgButtonHtml("marcaTemporale/marcaTemporaleNonValida.png");
						} else {
							return buildImgButtonHtml("marcaTemporale/marcaTemporaleValida.png");
						}
					}
				}
				return null;
			}
		});		
		fileMarcatoTempOmissisButton.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					if (infoFileOmissis != null && infoFileOmissis.getInfoFirmaMarca() != null && infoFileOmissis.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (infoFileOmissis.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							return I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt();
						} else {
							Date dataOraMarcaTemporale = infoFileOmissis.getInfoFirmaMarca().getDataOraMarcaTemporale();
							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
							return I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale));
						}
					}
				}
				return null;							
			}
		});	
		fileMarcatoTempOmissisButton.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis();
			}
		});	
		
		dimensioneSignificativaOmissis = new AllegatiListGridField("dimensioneSignificativaOmissis");
		dimensioneSignificativaOmissis.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		dimensioneSignificativaOmissis.setCanDragResize(false);				
		dimensioneSignificativaOmissis.setWidth(25);		
		dimensioneSignificativaOmissis.setAttribute("custom", true);	
		dimensioneSignificativaOmissis.setAlign(Alignment.CENTER);
		dimensioneSignificativaOmissis.setShowHover(true);		
		dimensioneSignificativaOmissis.setCanReorder(false);
		dimensioneSignificativaOmissis.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					long dimAlertAllegato = getDimAlertAllegato(); 
					if(dimAlertAllegato > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > dimAlertAllegato) {
						return buildImgButtonHtml("warning.png");
					}				
				} 
				return null;					
			}
		});
		dimensioneSignificativaOmissis.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
					InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
					long dimAlertAllegato = getDimAlertAllegato(); 
					if(dimAlertAllegato > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > dimAlertAllegato) {						
						final float MEGABYTE = 1024L * 1024L;
						float dimensioneInMB = infoFileOmissis.getBytes() / MEGABYTE;						
						return "Dimensione significativa: " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB";
					} 				
				} 				
				return null;								
			}
		});
		dimensioneSignificativaOmissis.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis() && getDimAlertAllegato() > 0;
			}
		});	
		
		if(AurigaLayout.getParametroDBAsBoolean("SHOW_IMPRONTA_FILE")) {
			improntaOmissis = new AllegatiListGridField("improntaOmissis", "Impronta (vers. con omissis)");
			improntaOmissis.setAttribute("custom", true);	
			improntaOmissis.setShowHover(true);		
			improntaOmissis.setCanReorder(false);
			improntaOmissis.setCellFormatter(new CellFormatter() {	
				
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
					final String uriFileOmissis = record.getAttribute("uriFileOmissis");					
					if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
						InfoFileRecord lInfoFileRecordOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
						String improntaOmissis = lInfoFileRecordOmissis != null && lInfoFileRecordOmissis.getImpronta() != null ? lInfoFileRecordOmissis.getImpronta() : "";
						return improntaOmissis;
					}
					return null;
				}
			});
			improntaOmissis.setShowIfCondition(new ListGridFieldIfFunction() {
				
				@Override
				public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
					return getShowVersioneOmissis();
				}
			});	
		} else {
			improntaOmissis = new AllegatiListGridField("improntaOmissis"); improntaOmissis.setHidden(true); improntaOmissis.setCanHide(false);			
		}
		
				
		flgSostituisciVerPrecOmissis = new AllegatiListGridField("flgSostituisciVerPrecOmissis"); flgSostituisciVerPrecOmissis.setHidden(true); flgSostituisciVerPrecOmissis.setCanHide(false);
//		flgSostituisciVerPrecOmissis = new AllegatiListGridField("flgSostituisciVerPrecOmissis", "Sostituisci vers. prec. omissis");
//		flgSostituisciVerPrecOmissis.setType(ListGridFieldType.ICON);
//		flgSostituisciVerPrecOmissis.setWidth(30);
//		flgSostituisciVerPrecOmissis.setIconWidth(16);
//		flgSostituisciVerPrecOmissis.setIconHeight(16);
//		flgSostituisciVerPrecOmissis.setDefaultValue(false);
//		Map<String, String> flgSostituisciVerPrecOmissisValueIcons = new HashMap<String, String>();		
//		flgSostituisciVerPrecOmissisValueIcons.put("true", "ok.png");
//		flgSostituisciVerPrecOmissisValueIcons.put("false", "blank.png");
//		flgSostituisciVerPrecOmissisValueIcons.put("undefined", "blank.png");
//		flgSostituisciVerPrecOmissisValueIcons.put("", "blank.png");
//		flgSostituisciVerPrecOmissis.setValueIcons(flgSostituisciVerPrecOmissisValueIcons);		
//		flgSostituisciVerPrecOmissis.setHoverCustomizer(new HoverCustomizer() {		
//			
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
//				if(record.getAttribute("flgSostituisciVerPrecOmissis") != null && "true".equals(record.getAttribute("flgSostituisciVerPrecOmissis"))) {
//					return "Sostituisci vers. prec. omissis";
//				}				
//				return null;
//			}
//		});	
//		flgSostituisciVerPrecOmissis.setShowIfCondition(new ListGridFieldIfFunction() {
//			
//			@Override
//			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
//				return getShowVersioneOmissis();
//			}
//		});	
		
		flgTimbratoFilePostRegOmissis = new AllegatiListGridField("flgTimbratoFilePostRegOmissis"); flgTimbratoFilePostRegOmissis.setHidden(true); flgTimbratoFilePostRegOmissis.setCanHide(false);
		opzioniTimbroOmissis = new AllegatiListGridField("opzioniTimbroOmissis"); opzioniTimbroOmissis.setHidden(true); opzioniTimbroOmissis.setCanHide(false);
	
//		flgTimbraFilePostRegOmissis = new AllegatiListGridField("flgTimbraFilePostRegOmissis"); flgTimbraFilePostRegOmissis.setHidden(true); flgTimbraFilePostRegOmissis.setCanHide(false);
		flgTimbraFilePostRegOmissis = new AllegatiListGridField("flgTimbraFilePostRegOmissis", "Ver. omissis da timbrare");
		flgTimbraFilePostRegOmissis.setType(ListGridFieldType.ICON);
		flgTimbraFilePostRegOmissis.setWidth(30);
		flgTimbraFilePostRegOmissis.setIconWidth(16);
		flgTimbraFilePostRegOmissis.setIconHeight(16);
		flgTimbraFilePostRegOmissis.setDefaultValue(false);
		Map<String, String> flgTimbraFilePostRegOmissisValueIcons = new HashMap<String, String>();		
		flgTimbraFilePostRegOmissisValueIcons.put("true", "ok.png");
		flgTimbraFilePostRegOmissisValueIcons.put("false", "blank.png");
		flgTimbraFilePostRegOmissisValueIcons.put("undefined", "blank.png");
		flgTimbraFilePostRegOmissisValueIcons.put("", "blank.png");
		flgTimbraFilePostRegOmissis.setValueIcons(flgTimbraFilePostRegOmissisValueIcons);		
		flgTimbraFilePostRegOmissis.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgTimbraFilePostRegOmissis") != null && "true".equals(record.getAttribute("flgTimbraFilePostRegOmissis"))) {
					return "Timbra file omissis post reg.";
				}				
				return null;
			}
		});		
		flgTimbraFilePostRegOmissis.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return getShowVersioneOmissis() && isAttivaTimbraturaFilePostRegAllegato();
			}
		});	
		
		esitoInvioACTASerieAttiIntegrali = new AllegatiListGridField("esitoInvioACTASerieAttiIntegrali", "Esito archiviazione in ACTA (serie atti in vers. integrale)");			
		esitoInvioACTASerieAttiIntegrali.setType(ListGridFieldType.ICON);		
		esitoInvioACTASerieAttiIntegrali.setWidth(30);
		esitoInvioACTASerieAttiIntegrali.setIconWidth(16);
		esitoInvioACTASerieAttiIntegrali.setIconHeight(16);
		Map<String, String> esitoInvioACTASerieAttiIntegraliValueIcons = new HashMap<String, String>();		
		esitoInvioACTASerieAttiIntegraliValueIcons.put("OK", "ok.png");
		esitoInvioACTASerieAttiIntegraliValueIcons.put("KO", "ko.png");
		esitoInvioACTASerieAttiIntegraliValueIcons.put("undefined", "blank.png");
		esitoInvioACTASerieAttiIntegraliValueIcons.put("", "blank.png");
		esitoInvioACTASerieAttiIntegrali.setValueIcons(esitoInvioACTASerieAttiIntegraliValueIcons);		
		esitoInvioACTASerieAttiIntegrali.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("esitoInvioACTASerieAttiIntegrali") != null && "OK".equals(record.getAttribute("esitoInvioACTASerieAttiIntegrali"))) {
					return "archiviato in ACTA nella serie degli atti in versione integrale";
				} else if(record.getAttribute("esitoInvioACTASerieAttiIntegrali") != null && "KO".equals(record.getAttribute("esitoInvioACTASerieAttiIntegrali"))) {
					return "fallita archiviazione in ACTA nella serie degli atti in versione integrale";				
				}
				return null;
			}
		});
		esitoInvioACTASerieAttiIntegrali.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
				return fascSmistACTA != null && (NuovaPropostaAtto2CompletaDetail._MANDATORY.equalsIgnoreCase(fascSmistACTA) || NuovaPropostaAtto2CompletaDetail._OPTIONAL.equalsIgnoreCase(fascSmistACTA));					
			}
		});
		
		esitoInvioACTASeriePubbl = new AllegatiListGridField("esitoInvioACTASeriePubbl", "Esito archiviazione in ACTA (serie di pubbl.)");			
		esitoInvioACTASeriePubbl.setType(ListGridFieldType.ICON);		
		esitoInvioACTASeriePubbl.setWidth(30);
		esitoInvioACTASeriePubbl.setIconWidth(16);
		esitoInvioACTASeriePubbl.setIconHeight(16);
		Map<String, String> esitoInvioACTASeriePubblValueIcons = new HashMap<String, String>();		
		esitoInvioACTASeriePubblValueIcons.put("OK", "ok.png");
		esitoInvioACTASeriePubblValueIcons.put("KO", "ko.png");
		esitoInvioACTASeriePubblValueIcons.put("undefined", "blank.png");
		esitoInvioACTASeriePubblValueIcons.put("", "blank.png");
		esitoInvioACTASeriePubbl.setValueIcons(esitoInvioACTASeriePubblValueIcons);		
		esitoInvioACTASeriePubbl.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("esitoInvioACTASeriePubbl") != null && "OK".equals(record.getAttribute("esitoInvioACTASeriePubbl"))) {
					return "archiviato in ACTA nella serie di pubblicazione";
				} else if(record.getAttribute("esitoInvioACTASeriePubbl") != null && "KO".equals(record.getAttribute("esitoInvioACTASeriePubbl"))) {
					return "fallita archiviazione in ACTA nella serie di pubblicazione";				
				}
				return null;
			}
		});
		esitoInvioACTASeriePubbl.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
				return fascSmistACTA != null && (NuovaPropostaAtto2CompletaDetail._MANDATORY.equalsIgnoreCase(fascSmistACTA) || NuovaPropostaAtto2CompletaDetail._OPTIONAL.equalsIgnoreCase(fascSmistACTA));
			}
		});
		
		List<ListGridField> gridFields = new ArrayList<ListGridField>();

		gridFields.add(id);
		gridFields.add(flgSalvato);
		gridFields.add(flgNoModificaDati);
		gridFields.add(idUdAppartenenza);		
		gridFields.add(isUdSenzaFileImportata);
		gridFields.add(idUdAllegato);	
		gridFields.add(idTask);
		gridFields.add(idEmail);
		gridFields.add(idAttachEmail);
		gridFields.add(collegaDocumentoImportato);
		gridFields.add(idDocAllegato);
		gridFields.add(fileImportato);
		gridFields.add(uriFileAllegato);
		gridFields.add(tsInsLastVerFileAllegato);
		gridFields.add(infoFile);
		gridFields.add(remoteUri);
		gridFields.add(isChanged);
		gridFields.add(nomeFileAllegatoOrig);
		gridFields.add(nomeFileAllegatoTif);
		gridFields.add(uriFileAllegatoTif);
		gridFields.add(nomeFileVerPreFirma);
		gridFields.add(uriFileVerPreFirma);
		gridFields.add(improntaVerPreFirma);
		gridFields.add(infoFileVerPreFirma);
		gridFields.add(nroUltimaVersione);
		gridFields.add(idUDScansione);
		gridFields.add(flgGenAutoDaModello);
		gridFields.add(flgGenDaModelloDaFirmare);
		gridFields.add(nroAllegato);
		gridFields.add(mostraErrori);
		gridFields.add(numeroAllegato);	
		gridFields.add(numeroAllegatoAfterDrop);
		gridFields.add(dettaglioUdAllegatoButton);		
		if(isDocIstruttoriaDettFascicoloGenCompleto()) {
			gridFields.add(estremiProtUd);
			gridFields.add(dettaglioEstremiProtocolloButton);
			gridFields.add(dataRicezione);
		}				
		gridFields.add(flgProvEsterna);
		gridFields.add(flgParteDispositivoSalvato);
		gridFields.add(flgParteDispositivo);
		gridFields.add(flgParere);
		gridFields.add(idTipoFileAllegatoSalvato);
		gridFields.add(idTipoFileAllegato);
		gridFields.add(descTipoFileAllegato);
		gridFields.add(listaTipiFileAllegato);
		gridFields.add(descrizioneFileAllegato);
		gridFields.add(uploadFileAllegatoButton);
		gridFields.add(nomeFileAllegato);
		//gridFields.add(previewFileAllegatoButton);
		gridFields.add(altreOpFileAllegatoButton);
		gridFields.add(fileFirmatoDigButton);
		gridFields.add(fileMarcatoTempButton);
		gridFields.add(dimensioneSignificativa);
		if(!isDocIstruttoriaDettFascicoloGenCompleto()) {
			gridFields.add(estremiProtUd);
			gridFields.add(dettaglioEstremiProtocolloButton);
			gridFields.add(dataRicezione);			
		}
		gridFields.add(tipoBarcodeContratto);
		gridFields.add(barcodeContratto);
		gridFields.add(energiaGasContratto);
		gridFields.add(tipoSezioneContratto);
		gridFields.add(codContratto);
		gridFields.add(flgPresentiFirmeContratto);
		gridFields.add(flgFirmeCompleteContratto);
		gridFields.add(nroFirmePrevisteContratto);
		gridFields.add(nroFirmeCompilateContratto);
		gridFields.add(flgIncertezzaRilevazioneFirmeContratto);
		gridFields.add(dettaglioDocContrattiBarcodeButton);
		gridFields.add(flgSostituisciVerPrec);
		gridFields.add(flgOriginaleCartaceo);
		gridFields.add(flgCopiaSostitutiva);
		gridFields.add(flgTimbratoFilePostReg);
		gridFields.add(opzioniTimbro);
		gridFields.add(flgTimbraFilePostReg);
		gridFields.add(flgNoPubblAllegato);
		gridFields.add(flgPubblicaSeparato);
		if(getShowFlgPubblicaSeparato() && !getFlgPubblicazioneAllegatiUguale()) {
			gridFields.add(flgDaUnireAlDispositivo);
		}
		gridFields.add(posAllegatiUniti);
		gridFields.add(flgPaginaFileUnione);
		gridFields.add(nroPagFileUnione);
		gridFields.add(nroPagFileUnioneOmissis);		
		gridFields.add(flgDatiProtettiTipo1);
		gridFields.add(flgDatiProtettiTipo2);
		gridFields.add(flgDatiProtettiTipo3);
		gridFields.add(flgDatiProtettiTipo4);
		gridFields.add(flgDatiSensibili);
		// OMISSIS
		gridFields.add(idDocOmissis);
		gridFields.add(uriFileOmissis);
		gridFields.add(infoFileOmissis);
		gridFields.add(remoteUriOmissis);
		gridFields.add(isChangedOmissis);
		gridFields.add(nomeFileOrigOmissis);
		gridFields.add(nomeFileTifOmissis);
		gridFields.add(uriFileTifOmissis);
		gridFields.add(nomeFileVerPreFirmaOmissis);
		gridFields.add(uriFileVerPreFirmaOmissis);
		gridFields.add(improntaVerPreFirmaOmissis);
		gridFields.add(infoFileVerPreFirmaOmissis);
		gridFields.add(nroUltimaVersioneOmissis);
		gridFields.add(uploadFileOmissisButton);
		gridFields.add(nomeFileOmissis);
		//gridFields.add(previewFileOmissisButton);
		gridFields.add(altreOpFileOmissisButton);
		gridFields.add(fileFirmatoDigOmissisButton);
		gridFields.add(fileMarcatoTempOmissisButton);
		gridFields.add(dimensioneSignificativaOmissis);
		gridFields.add(flgSostituisciVerPrecOmissis);
		gridFields.add(flgTimbratoFilePostRegOmissis);
		gridFields.add(opzioniTimbroOmissis);
		gridFields.add(flgTimbraFilePostRegOmissis);
		// ARCHIVIAZIONE IN ACTA
		gridFields.add(esitoInvioACTASerieAttiIntegrali);
		gridFields.add(esitoInvioACTASeriePubbl);
		gridFields.add(numFileCaricatiInUploadMultiplo);
		gridFields.add(trasformaInPrimarioButton);
		
		setGridFields(gridFields.toArray(new ListGridField[gridFields.size()]));
	}

	@Override
	public void clearErrors() {
		super.clearErrors();
		mappaErrori = new HashMap<String, HashSet<String>>();	
		refreshRows();
	}
	
	public Map getMapErrors() {
		HashMap<String, String> errors = new HashMap<String, String>();
		if(mappaErrori != null) {
			for(String id: mappaErrori.keySet()) {
				if(mappaErrori.get(id) != null && mappaErrori.get(id).size() > 0) {
					errors.put(id, "Questa riga contiene errori");
				}
			}
		}
		return errors;
	}
	
	@Override
	public Boolean validate() {
		
		// le stesse logiche di validazione vanno replicate nel metodo valuesAreValid, ma senza popolare mappaErrori
		
		boolean valid = true;
		
		mappaErrori = new HashMap<String, HashSet<String>>();
		
		String rifiutoAllegatiConFirmeNonValide = getRifiutoAllegatiConFirmeNonValide();
		
		for (ListGridRecord record : grid.getRecords()) {
			
			String id = record.getAttribute("id");
			
			mappaErrori.put(id, new HashSet<String>());
			
			final float MEGABYTE = 1024L * 1024L;
			long dimMaxAllegatoXPubblInMB = getDimMaxAllegatoXPubbl(); // questa è in MB
			
			boolean flgParteDispositivo = record.getAttributeAsBoolean("flgParteDispositivo") != null && record.getAttributeAsBoolean("flgParteDispositivo");
			boolean flgNoPubblAllegato = record.getAttributeAsBoolean("flgNoPubblAllegato") != null && record.getAttributeAsBoolean("flgNoPubblAllegato");	
			boolean flgDatiSensibili = record.getAttributeAsBoolean("flgDatiSensibili") != null && record.getAttributeAsBoolean("flgDatiSensibili");
			boolean flgPubblicaSeparato = false;
			if(getShowFlgPubblicaSeparato()) {
				if(getFlgPubblicazioneAllegatiUguale()) {
					flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
				} else {
					flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && record.getAttributeAsBoolean("flgPubblicaSeparato");
				}
			}			 	
			boolean showFileOmissis = getShowVersioneOmissis() && flgDatiSensibili;		
			
			String tipoAllegato = record.getAttribute("listaTipiFileAllegato");
			String descrizioneFileAllegato = record.getAttribute("descrizioneFileAllegato");
			
			InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
			String uriFileAllegato = record.getAttribute("uriFileAllegato");
			
			if(isObbligatorioFile() && (uriFileAllegato == null || uriFileAllegato.equals(""))) {
				// se è un ud allegato il file non deve essere obbligatorio
				if(!isUdAllegato(record)) {
					mappaErrori.get(id).add("Il file è obbligatorio");
					valid = false;		
				}
			} else if ((tipoAllegato == null || tipoAllegato.equals("")) && (descrizioneFileAllegato == null || descrizioneFileAllegato.trim().equals("")) && (uriFileAllegato == null || uriFileAllegato.equals(""))) {
				mappaErrori.get(id).add("Almeno uno tra tipo, descrizione e file deve essere valorizzato");
				valid = false;
			}

			if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
				if (infoFileAllegato == null || infoFileAllegato.getMimetype() == null || infoFileAllegato.getMimetype().equals("")) {
					mappaErrori.get(id).add("Formato file non riconosciuto");
					valid = false;
				} else if (infoFileAllegato != null && !validateFormatoFileAllegato(infoFileAllegato)) {
					mappaErrori.get(id).add(getFormatoFileNonValidoErrorMessage());
					valid = false;
				} else if (infoFileAllegato != null && infoFileAllegato.getBytes() <= 0) {
					mappaErrori.get(id).add("Il file ha dimensione 0");
					valid = false;
				}
				if (isProtInModalitaWizard() && isCanaleSupportoCartaceo() &&
						(infoFileAllegato == null || infoFileAllegato.getMimetype() == null || (!infoFileAllegato.getMimetype().equals("application/pdf") && !infoFileAllegato.getMimetype().startsWith("image")))) {
					mappaErrori.get(id).add("Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale");
					valid = false;
				}			
				// il tipo del file caricato non è tra quelli accettati
				if (infoFileAllegato != null && !isFormatoAmmesso(infoFileAllegato)) {
					mappaErrori.get(id).add("Formato file non valido");
					valid = false;
				} else if(flgParteDispositivo && infoFileAllegato != null && !isFormatoAmmessoParteIntegrante(infoFileAllegato)) {
					mappaErrori.get(id).add("Formato file non ammesso per un allegato parte integrante");
					valid = false;			
				}
				
				if(flgParteDispositivo && infoFileAllegato != null && !PreviewWindow.isPdfConvertibile(infoFileAllegato) && !flgPubblicaSeparato) {
					mappaErrori.get(id).add("File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto");
					valid = false;	
				}
				
				if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
					if(!infoFileAllegato.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
						if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
							mappaErrori.get(id).add("Il file presenta firme non valide alla data odierna e non può essere caricato");
							valid = false;
						} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
							mappaErrori.get(id).add("Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante");
							valid = false;
						} 
					} else if(isDisattivaUnioneAllegatiFirmati()) {
						// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
					}
				}
				
				// Se è un pdf protetto lo pubblico separatamente
				if (flgParteDispositivo && isPubblicazioneSeparataPdfProtetti() && infoFileAllegato != null && infoFileAllegato.isPdfProtetto() && !flgPubblicaSeparato) {
					mappaErrori.get(id).add("File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto");
					valid = false;	
				}
							
				if(flgParteDispositivo && !flgDatiSensibili && dimMaxAllegatoXPubblInMB > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
					mappaErrori.get(id).add("La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione");
					valid = false;	
				}
			}
			
			if(showFileOmissis) {
				
				InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				
				if(flgParteDispositivo && !flgNoPubblAllegato && (uriFileOmissis == null || uriFileOmissis.equals(""))) {					
					mappaErrori.get(id).add("Il file omissis è obbligatorio");
					valid = false;
				}

				if (uriFileOmissis != null && !"".equals(uriFileOmissis)) {
					if (infoFileOmissis == null || infoFileOmissis.getMimetype() == null || infoFileOmissis.getMimetype().equals("")) {
						mappaErrori.get(id).add("Formato file omissis non riconosciuto");
						valid = false;
					} else if (infoFileOmissis != null && !validateFormatoFileAllegato(infoFileOmissis)) {
						mappaErrori.get(id).add(getFormatoFileOmissisNonValidoErrorMessage());
						valid = false;
					} else if (infoFileOmissis != null && infoFileOmissis.getBytes() <= 0) {
						mappaErrori.get(id).add("Il file omissis ha dimensione 0");
						valid = false;
					}
					
					if (infoFileOmissis != null && !isFormatoAmmesso(infoFileOmissis)) {
						mappaErrori.get(id).add("Formato file omissis non valido");
						valid = false;
					} else if(flgParteDispositivo && infoFileOmissis != null && !isFormatoAmmessoParteIntegrante(infoFileOmissis)) {
						mappaErrori.get(id).add("Formato file omissis non ammesso per un allegato parte integrante");
						valid = false;			
					}
					
					if(flgParteDispositivo && infoFileOmissis != null && !PreviewWindow.isPdfConvertibile(infoFileOmissis) && !flgPubblicaSeparato) {
						mappaErrori.get(id).add("File omissis non convertibile in formato pdf: non è possibile unirlo al testo dell'atto");
						valid = false;	
					}
					
					if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
						if(!infoFileOmissis.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
							if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
								mappaErrori.get(id).add("Il file omissis presenta firme non valide alla data odierna e non può essere caricato");
								valid = false;
							} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
								mappaErrori.get(id).add("Il file omissis presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante");
								valid = false;
							}
						} else if(isDisattivaUnioneAllegatiFirmati()) {
							// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
						}
					}
					
					// Se è un pdf protetto lo pubblico separatamente
					if (flgParteDispositivo && isPubblicazioneSeparataPdfProtetti() && infoFileOmissis != null && infoFileOmissis.isPdfProtetto() && !flgPubblicaSeparato) {
						mappaErrori.get(id).add("File omissis protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto");
						valid = false;	
					}
					
					if(flgParteDispositivo && dimMaxAllegatoXPubblInMB > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
						mappaErrori.get(id).add("La dimensione del file omissis è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione");
						valid = false;	
					}
				}
			}
			
			grid.refreshRow(grid.getRecordIndex(record));
		}
		
		return valid;
	}
	
	public Boolean valuesAreValid() {
		
		boolean valid = true;
		
		String rifiutoAllegatiConFirmeNonValide = getRifiutoAllegatiConFirmeNonValide();
		
		for (ListGridRecord record : grid.getRecords()) {
			
			String id = record.getAttribute("id");
			
			final float MEGABYTE = 1024L * 1024L;
			long dimMaxAllegatoXPubblInMB = getDimMaxAllegatoXPubbl(); // questa è in MB
			
			boolean flgParteDispositivo = record.getAttributeAsBoolean("flgParteDispositivo") != null && record.getAttributeAsBoolean("flgParteDispositivo");
			boolean flgNoPubblAllegato = record.getAttributeAsBoolean("flgNoPubblAllegato") != null && record.getAttributeAsBoolean("flgNoPubblAllegato");	
			boolean flgDatiSensibili = record.getAttributeAsBoolean("flgDatiSensibili") != null && record.getAttributeAsBoolean("flgDatiSensibili");
			boolean flgPubblicaSeparato = false;
			if(getShowFlgPubblicaSeparato()) {
				if(getFlgPubblicazioneAllegatiUguale()) {
					flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
				} else {
					flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && record.getAttributeAsBoolean("flgPubblicaSeparato");
				}
			}
			boolean showFileOmissis = getShowVersioneOmissis() && flgDatiSensibili;		
			
			String tipoAllegato = record.getAttribute("listaTipiFileAllegato");
			String descrizioneFileAllegato = record.getAttribute("descrizioneFileAllegato");
			
			InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
			String uriFileAllegato = record.getAttribute("uriFileAllegato");
			
			if(isObbligatorioFile() && (uriFileAllegato == null || uriFileAllegato.equals(""))) {
				// se è un ud allegato il file non deve essere obbligatorio
				if(!isUdAllegato(record)) {
					valid = false;
				}
			} else if ((tipoAllegato == null || tipoAllegato.equals("")) && (descrizioneFileAllegato == null || descrizioneFileAllegato.trim().equals("")) && (uriFileAllegato == null || uriFileAllegato.equals(""))) {
				valid = false;
			}

			if (uriFileAllegato != null && !"".equals(uriFileAllegato)) {
				if (infoFileAllegato == null || infoFileAllegato.getMimetype() == null || infoFileAllegato.getMimetype().equals("")) {
					valid = false;
				} else if (infoFileAllegato != null && !validateFormatoFileAllegato(infoFileAllegato)) {
					valid = false;
				} else if (infoFileAllegato != null && infoFileAllegato.getBytes() <= 0) {
					valid = false;
				}
				
				// il tipo del file caricato non è tra quelli accettati
				if (infoFileAllegato != null && !isFormatoAmmesso(infoFileAllegato)) {
					valid = false;
				} else if(flgParteDispositivo && infoFileAllegato != null && !isFormatoAmmessoParteIntegrante(infoFileAllegato)) {
					valid = false;			
				}
				
				if(flgParteDispositivo && infoFileAllegato != null && !PreviewWindow.isPdfConvertibile(infoFileAllegato) && !flgPubblicaSeparato) {
					valid = false;	
				}
				
				if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
					if(!infoFileAllegato.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
						if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
							valid = false;
						} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
							valid = false;
						} 
					} else if(isDisattivaUnioneAllegatiFirmati()) {
						// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
					}
				}
				
				// Se è un pdf protetto lo pubblico separatamente
				if (flgParteDispositivo && isPubblicazioneSeparataPdfProtetti() && infoFileAllegato != null && infoFileAllegato.isPdfProtetto() && !flgPubblicaSeparato) {
					valid = false;	
				}
							
				if(flgParteDispositivo && !flgDatiSensibili && dimMaxAllegatoXPubblInMB > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
					valid = false;	
				}
			}
			
			if(showFileOmissis) {
				
				InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
				String uriFileOmissis = record.getAttribute("uriFileOmissis");
				
				if(flgParteDispositivo && !flgNoPubblAllegato && (uriFileOmissis == null || uriFileOmissis.equals(""))) {					
					valid = false;
				}

				if (uriFileOmissis != null && !"".equals(uriFileOmissis)) {
					if (infoFileOmissis == null || infoFileOmissis.getMimetype() == null || infoFileOmissis.getMimetype().equals("")) {
						valid = false;
					} else if (infoFileOmissis != null && !validateFormatoFileAllegato(infoFileOmissis)) {
						valid = false;
					} else if (infoFileOmissis != null && infoFileOmissis.getBytes() <= 0) {
						valid = false;
					}
					
					if (infoFileOmissis != null && !isFormatoAmmesso(infoFileOmissis)) {
						valid = false;
					} else if(flgParteDispositivo && infoFileOmissis != null && !isFormatoAmmessoParteIntegrante(infoFileOmissis)) {
						valid = false;			
					}
					
					if(flgParteDispositivo && infoFileOmissis != null && !PreviewWindow.isPdfConvertibile(infoFileOmissis) && !flgPubblicaSeparato) {
						valid = false;	
					}
					
					if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
						if(!infoFileOmissis.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
							if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
								valid = false;
							} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
								valid = false;
							}
						} else if(isDisattivaUnioneAllegatiFirmati()) {
							// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
						}
					}
					
					// Se è un pdf protetto lo pubblico separatamente
					if (flgParteDispositivo && isPubblicazioneSeparataPdfProtetti() && infoFileOmissis != null && infoFileOmissis.isPdfProtetto() && !flgPubblicaSeparato) {
						valid = false;	
					}
					
					if(flgParteDispositivo && dimMaxAllegatoXPubblInMB > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
						valid = false;	
					}
				}
			}
		}
		
		return valid;
	}
	
	public void showAltreOpFileAllegatoMenu(final ListGridRecord listRecord) {
		final String uriFileAllegato = listRecord.getAttribute("uriFileAllegato");
		final InfoFileRecord infoFileAllegato = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
		final Menu altreOpMenu = new Menu();
		altreOpMenu.setOverflow(Overflow.VISIBLE);
		altreOpMenu.setShowIcons(true);
		altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
		altreOpMenu.setCanDragRecordsOut(false);
		altreOpMenu.setWidth("*");
		altreOpMenu.setHeight("*");				
		if (!isHideVisualizzaVersioniButton()) {
			MenuItem visualizzaVersioniMenuItem = new MenuItem("Visualizza versioni", "file/version.png");
			visualizzaVersioniMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					Integer nroUltimaVersione = listRecord != null && listRecord.getAttribute("nroUltimaVersione") != null && !"".equals(listRecord.getAttribute("nroUltimaVersione")) ? new Integer(listRecord.getAttribute("nroUltimaVersione")) : null;	
					return nroUltimaVersione != null && nroUltimaVersione > 1;
				}
			});
			visualizzaVersioniMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickVisualizzaVersioni(listRecord);					
				}
			});
			altreOpMenu.addItem(visualizzaVersioniMenuItem);
		}
		MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
		downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return uriFileAllegato != null && !uriFileAllegato.equals("");
			}
		});
		if(infoFileAllegato != null && infoFileAllegato.isFirmato() && infoFileAllegato.getTipoFirma().startsWith("CAdES")) {
			Menu downloadFirmatoSbustatoMenu = new Menu();
			MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadFile(listRecord);
				}
			});
			MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadFileSbustato(listRecord);
				}
			});
			downloadFirmatoSbustatoMenu.setItems(firmato, sbustato);
			downloadMenuItem.setSubmenu(downloadFirmatoSbustatoMenu);		
		} else {
			downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadFile(listRecord);
				}
			});
		}
		altreOpMenu.addItem(downloadMenuItem);	
		if(isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isModificabileSoloVersOmissis(listRecord)) {
			if (!isHideAcquisisciDaScannerInAltreOperazioniButton()) {
				MenuItem acquisisciDaScannerMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(),	"file/scanner.png");
				acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickAcquisisciDaScanner(listRecord);
					}
				});
				altreOpMenu.addItem(acquisisciDaScannerMenuItem);
			}
			if (!isHideFirmaInAltreOperazioniButton()) {
				MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(), "file/mini_sign.png");
				firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return uriFileAllegato != null && !uriFileAllegato.equals("");
					}
				});
				firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickFirma(listRecord);
					}
				});	
				altreOpMenu.addItem(firmaMenuItem);		
			}	
		}
		if (!isHideTimbraInAltreOperazioniButton()) {
			MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(), "file/attestato.png");
			attestatoConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
						return infoFileAllegato != null;
					}
					return false;
				}
			});
			attestatoConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String strIdUdAppartenza = listRecord.getAttribute("idUdAppartenenza");	
					Record detailRecord = getDetailRecord();		
					final String idUd;
					if (detailRecord != null ) {
						idUd = detailRecord.getAttribute("idUd");
					} else {
						//se detailRecord è null vuol dire che l'allegatoItem è quello di DocumentiIstruttoria,
						//quindi l'idUd è l'idUdAppartenenza del canvas
						idUd = strIdUdAppartenza;
					}
					final String idDoc = listRecord.getAttributeAsString("idDocAllegato");
					
					SC.ask("Vuoi firmare digitalmente l'attestato ?", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								creaAttestato(idUd, idDoc, infoFileAllegato, uriFileAllegato, true);
							} else {
								creaAttestato(idUd, idDoc, infoFileAllegato, uriFileAllegato, false);
							}
						}
					});
				}
			});					
			altreOpMenu.addItem(attestatoConformitaOriginaleMenuItem);	
			
			if(showOperazioniTimbraturaAllegato(listRecord)) {				
				buildMenuBarcodeEtichetta(listRecord, altreOpMenu, false);
			}
			
			final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject("infoFileOmissis"));
			if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
				String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
				MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
				timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								final String idDoc = listRecord.getAttribute("idDocAllegato");
								final String nomeFile = listRecord.getAttribute("nomeFileAllegato");
								final String uri = listRecord.getAttribute("uriFileAllegato");
								final String remoteUri = listRecord.getAttribute("remoteUri");
								final String idUdAppartenenza = listRecord.getAttribute("idUdAppartenenza");	
								clickTimbraDatiSegnatura(idDoc, nomeFile, uri, remoteUri, lInfoFileRecord, idUdAppartenenza, "COPIA_CONFORME_CUSTOM");
							}
						});

				altreOpMenu.addItem(timbroConformitaCustomAllegatoMenuItem);

			}
			
		}	
		if((isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isModificabileSoloVersOmissis(listRecord)) || isForceToShowGeneraDaModelloForTipoTaskDoc()) {				
			if (getShowGeneraDaModello() && !isUdProtocollata(listRecord)) {
				MenuItem generaDaModelloMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_generaDaModelloButton_prompt(), "protocollazione/generaDaModello.png");
				generaDaModelloMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
						
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						String tipoAllegato = getTipoAllegato(listRecord);						
						return tipoAllegato != null && !"".equals(tipoAllegato);					
					}
				});
				generaDaModelloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickGeneraDaModello(listRecord, false);
					}
				});
				altreOpMenu.addItem(generaDaModelloMenuItem);	
			}
		}
		if(isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isModificabileSoloVersOmissis(listRecord)) {							
			if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
				MenuItem previewEditMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
				previewEditMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
		
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						if (uriFileAllegato != null && !uriFileAllegato.equals("")) {
							return infoFileAllegato != null && infoFileAllegato.isConvertibile();
						} 
						return false;
					}
				});
				previewEditMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickPreviewEditFile(listRecord, false);						
					}
				});
				altreOpMenu.addItem(previewEditMenuItem);	
			}
			if (canBeEditedByApplet() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
				MenuItem editMenuItem = new MenuItem("Modifica documento", "file/editDoc.png");
				editMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
		
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return isShowEditMenuItem(listRecord, false);
					}
				});
				editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickEditFile(listRecord, false);						
					}
				});
				altreOpMenu.addItem(editMenuItem);	
			}
		}
		if(isEditable() && isShowEditButtons() && (isShowModifyButton(listRecord) || sonoInMail()) && !isModificabileSoloVersOmissis(listRecord)) {
			MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
			eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return uriFileAllegato != null && !uriFileAllegato.equals("");
				}
			});
			eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickEliminaFile(listRecord);
				}
			});
			altreOpMenu.addItem(eliminaMenuItem);
		}		
		altreOpMenu.showContextMenu();		
	}
	
	public void showAltreOpFileOmissisMenu(final ListGridRecord listRecord) {
		final String uriFileOmissis = listRecord.getAttribute("uriFileOmissis");
		final InfoFileRecord infoFileOmissis = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
		final Menu altreOpMenuOmissis = new Menu();
		altreOpMenuOmissis.setOverflow(Overflow.VISIBLE);
		altreOpMenuOmissis.setShowIcons(true);
		altreOpMenuOmissis.setSelectionType(SelectionStyle.SINGLE);
		altreOpMenuOmissis.setCanDragRecordsOut(false);
		altreOpMenuOmissis.setWidth("*");
		altreOpMenuOmissis.setHeight("*");						
		if (!isHideVisualizzaVersioniButton()) {
			MenuItem visualizzaVersioniMenuItem = new MenuItem("Visualizza versioni", "file/version.png");
			visualizzaVersioniMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {							
					Integer nroUltimaVersioneOmissis = listRecord != null && listRecord.getAttribute("nroUltimaVersioneOmissis") != null && !"".equals(listRecord.getAttribute("nroUltimaVersioneOmissis")) ? new Integer(listRecord.getAttribute("nroUltimaVersioneOmissis")) : null;	
					return nroUltimaVersioneOmissis != null && nroUltimaVersioneOmissis > 1;
				}
			});
			visualizzaVersioniMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickVisualizzaVersioniOmissis(listRecord);	
				}
			});
			altreOpMenuOmissis.addItem(visualizzaVersioniMenuItem);
		}				
		MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
		downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return uriFileOmissis != null && !uriFileOmissis.equals("");
			}
		});
		if(infoFileOmissis != null && infoFileOmissis.isFirmato() && infoFileOmissis.getTipoFirma().startsWith("CAdES")) {
			Menu downloadFirmatoSbustatoMenu = new Menu();
			MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadFileOmissis(listRecord);
				}
			});
			MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadFileOmissisSbustato(listRecord);
				}
			});
			downloadFirmatoSbustatoMenu.setItems(firmato, sbustato);
			downloadMenuItem.setSubmenu(downloadFirmatoSbustatoMenu);		
		} else {
			downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickDownloadFileOmissis(listRecord);
				}
			});
		}
		altreOpMenuOmissis.addItem(downloadMenuItem);		
		if((isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isNonModificabileVersOmissis(listRecord))) {
			if (!isHideAcquisisciDaScannerInAltreOperazioniButton()) {
				MenuItem acquisisciDaScannerMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(),	"file/scanner.png");
				acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickAcquisisciDaScannerOmissis(listRecord);
					}
				});
				altreOpMenuOmissis.addItem(acquisisciDaScannerMenuItem);
			}
			if (!isHideFirmaInAltreOperazioniButton()) {
				MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(), "file/mini_sign.png");
				firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return uriFileOmissis != null && !uriFileOmissis.equals("");
					}
				});
				firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickFirmaOmissis(listRecord);
					}
				});	
				altreOpMenuOmissis.addItem(firmaMenuItem);		
			}
		}
		if (!isHideTimbraInAltreOperazioniButton()) {
			MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(), "file/attestato.png");
			attestatoConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
						return infoFileOmissis != null;
					}
					return false;
				}
			});
			attestatoConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String strIdUdAppartenza = listRecord.getAttribute("idUdAppartenenza");	
					Record detailRecord = getDetailRecord();		
					final String idUd;
					if (detailRecord != null ) {
						idUd = detailRecord.getAttribute("idUd");
					} else {
						//se detailRecord è null vuol dire che l'allegatoItem è quello di DocumentiIstruttoria,
						//quindi l'idUd è l'idUdAppartenenza del canvas
						idUd = strIdUdAppartenza;
					}
					final String idDoc = listRecord.getAttributeAsString("idDocOmissis");
					SC.ask("Vuoi firmare digitalmente l'attestato ?", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								creaAttestato(idUd, idDoc, infoFileOmissis, uriFileOmissis, true);
							} else {
								creaAttestato(idUd, idDoc, infoFileOmissis, uriFileOmissis, false);
							}
						}
					});
				}
			});					
			altreOpMenuOmissis.addItem(attestatoConformitaOriginaleMenuItem);	
			
			if(showOperazioniTimbraturaAllegato(listRecord)) {											
				buildMenuBarcodeEtichetta(listRecord, altreOpMenuOmissis, true);	
			}
			
			final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject("infoFileOmissis"));
			if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
				String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
				MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
				timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								
								final String idDoc = listRecord.getAttribute("idDocOmissis");
								final String nomeFile = listRecord.getAttribute("nomeFileOmissis");
								final String uri = listRecord.getAttribute("uriFileOmissis");
								final String remoteUri = listRecord.getAttribute("remoteUriOmissis");
								final String idUdAppartenenza = listRecord.getAttribute("idUdAppartenenza");	
								clickTimbraDatiSegnatura(idDoc, nomeFile, uri, remoteUri, lInfoFileRecord, idUdAppartenenza, "COPIA_CONFORME_CUSTOM");
							}
						});

				altreOpMenuOmissis.addItem(timbroConformitaCustomAllegatoMenuItem);

			}
			
		}
		if((isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isNonModificabileVersOmissis(listRecord)) || isForceToShowGeneraDaModelloForTipoTaskDoc()) {			
			if (getShowGeneraDaModello() && !isUdProtocollata(listRecord)) {
				MenuItem generaDaModelloMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_generaDaModelloButton_prompt(), "protocollazione/generaDaModello.png");
				generaDaModelloMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						String tipoAllegato = getTipoAllegato(listRecord);						
						return tipoAllegato != null && !"".equals(tipoAllegato);						
					}
				});
				generaDaModelloMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickGeneraDaModello(listRecord, true);
					}
				});
				altreOpMenuOmissis.addItem(generaDaModelloMenuItem);	
			}
		}
		if((isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isNonModificabileVersOmissis(listRecord))) {						
		 	if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
				MenuItem previewEditMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
				previewEditMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
		
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						if (uriFileOmissis != null && !uriFileOmissis.equals("")) {
							return infoFileOmissis != null && infoFileOmissis.isConvertibile();
						} 
						return false;
					}
				});
				previewEditMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickPreviewEditFile(listRecord, true);						
					}
				});
				altreOpMenuOmissis.addItem(previewEditMenuItem);	
			}
			if (canBeEditedByApplet() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
				MenuItem editMenuItem = new MenuItem("Modifica documento", "file/editDoc.png");
				editMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
		
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return isShowEditMenuItem(listRecord, true);
					}
				});
				editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickEditFile(listRecord, true);						
					}
				});
				altreOpMenuOmissis.addItem(editMenuItem);	
			}
		}
		if((isEditable() && isShowEditButtons() && (isShowModifyButton(listRecord) || sonoInMail()) && !isNonModificabileVersOmissis(listRecord))) {
			
			MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
			eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return uriFileOmissis != null && !uriFileOmissis.equals("");
				}
			});
			eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickEliminaFileOmissis(listRecord);
				}
			});
			altreOpMenuOmissis.addItem(eliminaMenuItem);					
		}
		altreOpMenuOmissis.showContextMenu();		
	}
	
//	public CheckboxItem getCheckboxItemFromRecord(Record record, String fieldName) {
//		String checkName = fieldName + "_" + record.getAttribute("id");
//		return checkboxItemList != null ? checkboxItemList.get(checkName) : null;
//	}
	
	public boolean skipUpdDocAllegatiNonModificati() {
		return false;
	}
	
	@Override
	protected void manageOnShowValue(RecordList data) {
		// ATTENZIONE:
		// ogni volta che setto un valore sul gridItem finisco qui dentro, sia facendo il setValue() sull'item sia settando il valore nel form o nel vm che lo contiene, quindi non solo quando carico i valori dalla loadDettaglio
		// anche quando fa lo show per la prima volta del gridItem (tipo selezionando il tab che lo contiene) entra qui dentro 
		if (data != null) {
			for (int i = 0; i < data.getLength(); i++) {
				Record lRecord = data.get(i);
				if(lRecord.getAttribute("id") == null) {
					if(lRecord.getAttribute("idDocAllegato") != null && !"".equals(lRecord.getAttribute("idDocAllegato"))) {
						lRecord.setAttribute("id", lRecord.getAttribute("idDocAllegato"));
					} else { 
						lRecord.setAttribute("id", "NEW_" + count++);
					}
				}
				//lRecord.setAttribute("flgSalvato", true); // ATTENZIONE: questo va tolto da qui perchè non passa qui dentro solo quando fa la loadDettaglio (leggi sopra), quindi devo settarlo direttamente nel metodo get() di ArchivioDataSource quando recupero i documenti di istruttoria del fascicolo
				if(skipUpdDocAllegatiNonModificati()) {
					// solo se è un allegato già salvato
					if(lRecord.getAttribute("idDocAllegato") != null && !"".equals(lRecord.getAttribute("idDocAllegato"))) {
						// lasciare il controllo con flgNoModificaDati preso come stringa con lRecord.getAttribute("flgNoModificaDati")
						// preso come boolean con lRecord.getAttributeAsBoolean("flgNoModificaDati") torna false e non va bene
						if(lRecord.getAttribute("flgNoModificaDati") == null) { // devo settarlo solo quando arriva dalla loadDettaglio quindi quando è null
							lRecord.setAttribute("flgNoModificaDati", true); // se true indica che questo allegato non ha subito modifiche ai metadati (rispetto a quelle caricate da DB) e quindi si può fare lo skip della UpdDoc
						}
					}
				}
				lRecord.setAttribute("valuesOrig", lRecord.toMap());
			}
		}
		setData(data);
	}
	
	@Override
	public void setData(RecordList data) {
		mappaErrori = new HashMap<String, HashSet<String>>();
//		checkboxItemList = new HashMap<String, CheckboxItem>();	
		super.setData(data);
		refreshNroAllegato();		
	}
	
	@Override
	public void addData(Record record) {		
		super.addData(record);
//		if(checkboxItemList != null) {
//			for(String checkName : checkboxItemList.keySet()) {
//				if(checkName.endsWith("_" + record.getAttribute("id"))) {
//					String fieldName = checkName.substring(0, checkName.indexOf("_"));
//					if(checkboxItemList.get(checkName) != null) {
//						checkboxItemList.get(checkName).setValue(record.getAttributeAsBoolean(fieldName));
//						if(checkboxItemList.get(checkName).getForm() != null) {
//							checkboxItemList.get(checkName).getForm().markForRedraw();
//						}
//					}		
//				}
//			}
//		}
	}
	
	
	@Override
	public void updateData(Record record, Record oldRecord) {
		String id = record.getAttribute("id");		
		mappaErrori.put(id, new HashSet<String>());		
		super.updateData(record, oldRecord);
//		if(checkboxItemList != null) {
//			for(String checkName : checkboxItemList.keySet()) {
//				if(checkName.endsWith("_" + record.getAttribute("id"))) {
//					String fieldName = checkName.substring(0, checkName.indexOf("_"));
//					if(checkboxItemList.get(checkName) != null) {
//						checkboxItemList.get(checkName).setValue(record.getAttributeAsBoolean(fieldName));
//						if(checkboxItemList.get(checkName).getForm() != null) {
//							checkboxItemList.get(checkName).getForm().markForRedraw();
//						}
//					}					
//				}
//			}
//		}
	}
		
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
		manageOnChanged();
		redrawCollegaDocumentiImportati();	
	}
	
	@Override
	public boolean isDisableGridRecordComponent() {
		return true; // disabilito i bottoni di upload in lista perchè danno problemi di performance in fase di rendering
	}
		
	@Override
	public Canvas createGridRecordComponent(final ListGrid grid, final ListGridRecord record, Integer colNum) {			
		if(!isDisableGridRecordComponent()) {				
			return manageCreateGridRecordComponent(grid, record, colNum);	
		}
		return null;
	}
	
	public Canvas manageCreateGridRecordComponent(final ListGrid grid, final ListGridRecord record, Integer colNum) {			
		final ListGridField field = grid.getField(colNum);
		Canvas lCanvasReturn = null;
		if(field.getName().equals("uploadFileAllegatoButton")) {	
			if(isEditable() && isShowEditButtons() && isShowModifyButton(record) && !isModificabileSoloVersOmissis(record)) {
				DynamicForm uploadAllegatoForm = new DynamicForm();
				uploadAllegatoForm.setOverflow(Overflow.VISIBLE);
				uploadAllegatoForm.setWidth(1);
				uploadAllegatoForm.setHeight(1);
				final FileUploadItemWithFirmaAndMimeType uploadAllegatoButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {
	
					@Override
					public void uploadEnd(String displayFileName, String uri) {
						grid.deselectAllRecords();		
						Record currentRecord = getRecordFromId(record.getAttribute("id")); // devo riprendere il record dalla lista perchè quello che mi arriva in questo metodo potrebbe non essere aggiornato
						final Record newRecord = new Record(currentRecord.getJsObj());
						newRecord.setAttribute("changed", true);
						newRecord.setAttribute("flgNoModificaDati", false);
//						if(uri != null && !"".equals(uri)) {
//							newRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
//							if(!getFlgParteDispositivoDefaultValue()) {
//								newRecord.setAttribute("flgNoPubblAllegato", true);
//								newRecord.setAttribute("flgPubblicaSeparato", false);
//								newRecord.setAttribute("flgDatiSensibili", false);
//							} else {
//								newRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
//							}	
//						}
						if (isProtInModalitaWizard()) {
							if (isCanaleSupportoDigitale()) {
								newRecord.setAttribute("flgOriginaleCartaceo", false);
							}
							if (isCanaleSupportoCartaceo()) {
								newRecord.setAttribute("flgOriginaleCartaceo", true);
							}
						}
						newRecord.setAttribute("nomeFileAllegato", displayFileName != null ? displayFileName : "");
						newRecord.setAttribute("uriFileAllegato", uri != null ? uri : "");
						newRecord.setAttribute("nomeFileAllegatoTif", "");
						newRecord.setAttribute("uriFileAllegatoTif", "");
						newRecord.setAttribute("remoteUri", false);
						newRecord.setAttribute("nomeFileVerPreFirma", displayFileName != null ? displayFileName : "");
						newRecord.setAttribute("uriFileVerPreFirma", uri != null ? uri : "");
						newRecord.setAttribute("isUdSenzaFileImportata", false);
						updateDataAndRefresh(newRecord, currentRecord);							
					}
	
					@Override
					public void manageError(String error) {
						String errorMessage = "Errore nel caricamento del file";
						if (error != null && !"".equals(error))
							errorMessage += ": " + error;
						Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
					}
				}, new ManageInfoCallbackHandler() {
	
					@Override
					public void manageInfo(InfoFileRecord info) {
						grid.deselectAllRecords();						
						Record currentRecord = getRecordFromId(record.getAttribute("id")); // devo riprendere il record dalla lista perchè quello che mi arriva in questo metodo potrebbe non essere aggiornato
						InfoFileRecord precInfo = currentRecord.getAttribute("infoFile") != null ? new InfoFileRecord(currentRecord.getAttributeAsRecord("infoFile")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						final Record newRecord = new Record(currentRecord.getJsObj());
						newRecord.setAttribute("infoFile", info);
						String displayName = currentRecord.getAttribute("nomeFileAllegato");
						String displayNameOrig = currentRecord.getAttribute("nomeFileAllegatoOrig");
						if (precImpronta == null
								|| !precImpronta.equals(info.getImpronta())
								|| (displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName
										.equals(displayNameOrig))) {
							newRecord.setAttribute("isChanged", true);
							manageChangedFileAllegato(newRecord);
						}
						newRecord.setAttribute("improntaVerPreFirma", info.getImpronta());
						newRecord.setAttribute("infoFileVerPreFirma", info);							
						updateDataAndRefresh(newRecord, currentRecord);						
						controlAfterUpload(info, newRecord, false);						
					}
				});
//				uploadAllegatoButton.setShowIfCondition(new FormItemIfFunction() {
//					
//					@Override
//					public boolean execute(FormItem item, Object value, DynamicForm form) {
//						boolean showUploadAllegato = isEditable() && isShowEditButtons() && isShowModifyButton(record) && !isModificabileSoloVersOmissis();
//						uploadAllegatoButton.setAttribute("nascosto", !showUploadAllegato);
//						return showUploadAllegato;
//					}
//				});
				uploadAllegatoButton.setCanFocus(false);
				uploadAllegatoButton.setTabIndex(-1);
				uploadAllegatoForm.setItems(uploadAllegatoButton);
				UploadAllegatoRecordComponent lUploadAllegatoRecordComponent = new UploadAllegatoRecordComponent(uploadAllegatoForm);						
				lCanvasReturn = lUploadAllegatoRecordComponent;		
			}
		} else if(field.getName().equals("uploadFileOmissisButton")) {	
			if((isEditable() && isShowEditButtons() && isShowModifyButton(record) && !isNonModificabileVersOmissis(record))) {
				DynamicForm uploadOmissisForm = new DynamicForm();
				uploadOmissisForm.setOverflow(Overflow.VISIBLE);
				uploadOmissisForm.setWidth(1);
				uploadOmissisForm.setHeight(1);
				final FileUploadItemWithFirmaAndMimeType uploadOmissisButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {
	
					@Override
					public void uploadEnd(String displayFileName, String uri) {
						grid.deselectAllRecords();
						Record currentRecord = getRecordFromId(record.getAttribute("id")); // devo riprendere il record dalla lista perchè quello che mi arriva in questo metodo potrebbe non essere aggiornato
						final Record newRecord = new Record(currentRecord.getJsObj());
						newRecord.setAttribute("changed", true);
						newRecord.setAttribute("flgNoModificaDati", false);
//						if(uri != null && !"".equals(uri)) {
//							newRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
//							if(!getFlgParteDispositivoDefaultValue()) {
//								newRecord.setAttribute("flgNoPubblAllegato", true);
//								newRecord.setAttribute("flgPubblicaSeparato", false);
//								newRecord.setAttribute("flgDatiSensibili", false);
//							} else {
//								newRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
//							}
//						}
						if (isProtInModalitaWizard()) {
							if (isCanaleSupportoDigitale()) {
								newRecord.setAttribute("flgOriginaleCartaceo", false);
							}
							if (isCanaleSupportoCartaceo()) {
								newRecord.setAttribute("flgOriginaleCartaceo", true);
							}
						}
						newRecord.setAttribute("nomeFileOmissis", displayFileName != null ? displayFileName : "");
						newRecord.setAttribute("uriFileOmissis", uri != null ? uri : "");
						newRecord.setAttribute("nomeFileTifOmissis", "");
						newRecord.setAttribute("uriFileTifOmissis", "");
						newRecord.setAttribute("remoteUriOmissis", false);	
						newRecord.setAttribute("nomeFileVerPreFirmaOmissis", displayFileName != null ? displayFileName : "");
						newRecord.setAttribute("uriFileVerPreFirmaOmissis", uri != null ? uri : "");
						newRecord.setAttribute("flgDatiSensibili", uri != null && !"".equals(uri));	
						updateDataAndRefresh(newRecord, currentRecord);	
					}
	
					@Override
					public void manageError(String error) {
						String errorMessage = "Errore nel caricamento del file";
						if (error != null && !"".equals(error))
							errorMessage += ": " + error;
						Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
					}
				}, new ManageInfoCallbackHandler() {
	
					@Override
					public void manageInfo(InfoFileRecord info) {
						grid.deselectAllRecords();						
						Record currentRecord = getRecordFromId(record.getAttribute("id")); // devo riprendere il record dalla lista perchè quello che mi arriva in questo metodo potrebbe non essere aggiornato
						InfoFileRecord precInfo = currentRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(currentRecord.getAttributeAsRecord("infoFileOmissis")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						final Record newRecord = new Record(currentRecord.getJsObj());
						newRecord.setAttribute("infoFileOmissis", info);
						String displayName = currentRecord.getAttribute("nomeFileOmissis");
						String displayNameOrig = currentRecord.getAttribute("nomeFileOrigOmissis");
						if (precImpronta == null
								|| !precImpronta.equals(info.getImpronta())
								|| (displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName
										.equals(displayNameOrig))) {
							newRecord.setAttribute("isChangedOmissis", true);
							manageChangedFileOmissis(newRecord);
						}
						newRecord.setAttribute("improntaVerPreFirmaOmissis", info.getImpronta());
						newRecord.setAttribute("infoFileVerPreFirmaOmissis", info);	
						updateDataAndRefresh(newRecord, currentRecord);						
						controlAfterUpload(info, newRecord, true);
					}
				});
//				uploadOmissisButton.setShowIfCondition(new FormItemIfFunction() {
//					
//					@Override
//					public boolean execute(FormItem item, Object value, DynamicForm form) {
//						boolean showUploadOmissis = (isEditable() && isShowEditButtons() && isShowModifyButton(record)) || isModificabileVersOmissis(record);
//						uploadOmissisButton.setAttribute("nascosto", !showUploadOmissis);
//						return showUploadOmissis;
//					}
//				});
				uploadOmissisButton.setCanFocus(false);
				uploadOmissisButton.setTabIndex(-1);
				uploadOmissisForm.setItems(uploadOmissisButton);
				UploadAllegatoRecordComponent lUploadOmissisRecordComponent = new UploadAllegatoRecordComponent(uploadOmissisForm);						
				lCanvasReturn = lUploadOmissisRecordComponent;	
			}
		} 
		/*
		else if(field.getAttributeAsBoolean("isCheckEditabile") != null && field.getAttributeAsBoolean("isCheckEditabile")) {	
			DynamicForm checkForm = new DynamicForm();
			checkForm.setOverflow(Overflow.VISIBLE);
			checkForm.setWidth(1);
			checkForm.setHeight(1);
			String checkName = field.getName() + "_" + record.getAttribute("id");
			CheckboxItem checkItem = new CheckboxItem(checkName);	
			checkItem.setShowTitle(false);
			checkItem.setShowLabel(false);
			checkItem.setValue(record.getAttributeAsBoolean(field.getName()));			
			checkItem.setWidth("20");
			checkItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					final Boolean checkValue = (Boolean) event.getValue();
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {		
							record.setAttribute(field.getName(), checkValue);
							if(field.getName().equals("flgParteDispositivo")) {
								CheckboxItem flgNoPubblAllegatoItem = getCheckboxItemFromRecord(record, "flgNoPubblAllegato");
								CheckboxItem flgPubblicaSeparatoItem = getCheckboxItemFromRecord(record, "flgPubblicaSeparato");
								if(!checkValue) {
									record.setAttribute("flgNoPubblAllegato", false);
									if(flgNoPubblAllegatoItem != null) {
										flgNoPubblAllegatoItem.setValue(false);
									}
									record.setAttribute("flgPubblicaSeparato", false);
									if(flgPubblicaSeparatoItem != null) {
										flgPubblicaSeparatoItem.setValue(false);
									}
									record.setAttribute("flgDatiSensibili", false);
									if(flgDatiSensibiliItem != null) {
										flgDatiSensibiliItem.setValue(false);
									}
								}
								updateGridItemValue();
								if(flgNoPubblAllegatoItem != null && flgNoPubblAllegatoItem.getForm() != null) {
									flgNoPubblAllegatoItem.getForm().markForRedraw();
									 
								} 
								if(flgPubblicaSeparatoItem != null && flgPubblicaSeparatoItem.getForm() != null) {
									flgPubblicaSeparatoItem.getForm().markForRedraw();
								}
								if(flgDatiSensibiliItem != null && flgDatiSensibiliItem.getForm() != null) {
									flgDatiSensibiliItem.getForm().markForRedraw();
								}
							} else {
								updateGridItemValue();
							}							
						}
					});	
				}
			});
			checkItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(field.getName().equals("flgNoPubblAllegato")) {
						return record.getAttributeAsBoolean("flgParteDispositivo");
					} else if(field.getName().equals("flgPubblicaSeparato")) {
						return record.getAttributeAsBoolean("flgParteDispositivo");
					}
					return true;
				}
			});
			checkItem.setCanEdit((isEditable() && isShowEditButtons() && isShowModifyButton(record)));
			checkForm.setItems(checkItem);
			checkboxItemList.put(checkName, checkItem);
			HLayout recordCanvas = new HLayout(1);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(25);
			recordCanvas.setAlign(Alignment.CENTER);
			recordCanvas.addMember(checkForm);		
			lCanvasReturn = recordCanvas;			
		}
		*/
		if (lCanvasReturn!=null) {
			lCanvasReturn.setAutoHeight();
			lCanvasReturn.markForRedraw();
		}
		return lCanvasReturn;	
	}
	
	public GWTRestDataSource getTipiFileAllegatoDataSource() {
		return null;				
	}
	
	public boolean showFilterEditorInTipiFileAllegato() {
		String idProcess = getIdProcess();
		if (idProcess != null && !"".equals(idProcess)) {
			return false;
		}
		return true;
	}
	
	public Record getDetailRecord() {
		return null;
	}
	
	public Date getDataRifValiditaFirma() {
		String idProcess = getIdProcess();
		if(idProcess == null || "".equals(idProcess)) {
			Record recordProtocollo = getDetailRecord();
			return recordProtocollo != null ? recordProtocollo.getAttributeAsDate("dataProtocollo") : null;
		}	
		return null;
	}
		
	public void manageOnChanged() {

	}
	
	public void resetCanvasChanged() {
		for (ListGridRecord record : grid.getRecords()) {
			record.setAttribute("changed", false);
		}
	}
	
	public void refreshNroAllegato() {
		List<String> listIdRecordsSel = getIdSelectedRecords();
		if (getShowFlgParteDispositivo() && getSortByFlgParteDispositivo()) {
			grid.clearSort();
			grid.addSort(new SortSpecifier("flgParteDispositivo", SortDirection.DESCENDING));
			if(isAttivaSceltaPosizioneAllegatiUniti()) {
				grid.addSort(new SortSpecifier("posAllegatiUniti", SortDirection.ASCENDING));							
			}
			grid.addSort(new SortSpecifier("numeroProgrAllegatoAfterDrop", SortDirection.ASCENDING));		
			
		}
		grid.deselectAllRecords();
		int n = 1;
		for (ListGridRecord record : grid.getRecords()) {
			record.setAttribute("numeroProgrAllegato", n);
			record.setAttribute("numeroProgrAllegatoAfterDrop", n);
			n++;
		}
		updateGridItemValue();		
		reselectRecords(listIdRecordsSel);
	}
	
	private ListGridRecord getRecordFromId(String id) {
		for (ListGridRecord listGridRecord : grid.getRecords()) {
			if (listGridRecord.getAttribute("id").equals(id)) {
				return listGridRecord;
			}
		}
		return null;
	}

	private List<String> getIdSelectedRecords() {
		List<String> listIdRecordsSel = new ArrayList<String>();		
		ListGridRecord[] recordsSel = grid.getSelectedRecords();
		for (ListGridRecord listGridRecord : recordsSel) {
			listIdRecordsSel.add(listGridRecord.getAttribute("id"));
		}		
		return listIdRecordsSel;
	}

	private void reselectRecords(List<String> listIdRecordsSel) {
		for (String idRecordToSel : listIdRecordsSel) {
			for (ListGridRecord listGridRecord : grid.getRecords()) {
				if (listGridRecord.getAttribute("id").equals(idRecordToSel)) {
					grid.selectRecord(listGridRecord);
				}
			}
		}
	}
	
	public void manageErroriFile(HashMap<String, String> erroriFile) {
		if(erroriFile != null && !erroriFile.isEmpty()) {
			for(String key : erroriFile.keySet()) {
				if(key != null && !"".equals(key) && !"0".equals(key)) {
					ListGridRecord lRecord = getAllegatoRecordFromNumero(key);
					if (erroriFile.get(key) != null && !"".equals(erroriFile.get(key))) {
						String id = lRecord.getAttribute("id");
						if(mappaErrori.get(id) == null) {
							mappaErrori.put(id, new HashSet<String>());
						}
						mappaErrori.get(id).add(erroriFile.get(key));					
					}
				}
			}
			refreshRows();
		}
	}
	
	public ListGridRecord getAllegatoRecordFromNumero(String numero) {
		int numeroAllegato = 0;
		for (ListGridRecord record : grid.getRecords()) {
			numeroAllegato++;
			if (numero != null && !"".equals(numero) && Integer.parseInt(numero) == numeroAllegato) {
				return record;				
			}
		}
		return null;
	}

	public ListGridRecord getAllegatoRecordFromTipo(String tipo) {
		for (ListGridRecord record : grid.getRecords()) {
			String tipoAllegato = record.getAttribute("listaTipiFileAllegato");
			if (tipoAllegato != null && tipoAllegato.equals(tipo)) {
				if (!readOnly || isNewOrSavedInTaskCorrente(record)) {
					return record;
				}
			}
		}
		return null;		
	}
	
	public boolean isUdAllegato(Record record) {
		String idUdAllegato = record.getAttribute("idUdAllegato");
		return (idUdAllegato != null && !"".equals(idUdAllegato));
	}

	public boolean isUdProtocollata(Record record) {
		String estremiProtUd = record.getAttribute("estremiProtUd");
		return estremiProtUd != null && !"".equals(estremiProtUd);
	}
	
	protected boolean isUdDocIstruttoriaCedAutotutela(Record record) {
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
		return (idUdAppartenenza != null && !"".equals(idUdAppartenenza) && isDocumentiIstruttoria() && isDocCedAutotutela());
	}
	
	protected boolean isUdDocPraticaVisura(Record record) {
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
		return (idUdAppartenenza != null && !"".equals(idUdAppartenenza) && isDocumentiIstruttoria() && isDocPraticaVisure());
	}
	
	protected boolean isUdDocIstruttoriaDettFascicoloGenCompleto(Record record) {
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
		return (idUdAppartenenza != null && !"".equals(idUdAppartenenza) && isDocIstruttoriaDettFascicoloGenCompleto());
	}
	
	// se è un allegato generato da modello a completamento task
	public boolean isAllegatoModelloAtti(Record record) {
		String tipoAllegato = record.getAttribute("listaTipiFileAllegato");		
		String descrTipoAllegato = record.getAttribute("descrizioneFileAllegato");		
		HashSet<String> tipiModelliAtti = getTipiModelliAttiAllegati();
		return (tipoAllegato != null && tipiModelliAtti != null && tipiModelliAtti.contains(tipoAllegato))
				|| (descrTipoAllegato != null && descrTipoAllegato.equals(DettaglioPraticaLayout.getNomeModelloPubblicazione()));
	}
	
	// se è un allegato è appena stato creato o è stato salvato nel task corrente
	public boolean isNewOrSavedInTaskCorrente(Record record) {
		String idTaskCorrente = getIdTaskCorrenteAllegati();
		String idDocAllegato = record.getAttribute("idDocAllegato");
		String idTaskAllegato = record.getAttribute("idTask");
		// IMPORTANTE: se non sono negli atti ma arrivo dalla maschera di dettaglio UD questo metodo dovrà tornare sempre true o altrimenti gli allegati risulterebbero non editabili quando invece dovrebbero esserlo
		// in questo caso sono a posto perchè se arrivo dal dettaglio UD la condizione (idTaskAllegato == null && !isReadOnly()) tornera sempre true, quindi non va' assolutamente tolta
		return (idDocAllegato == null || "".equals(idDocAllegato)) || (idTaskAllegato == null && !isReadOnly()) || (idTaskCorrente != null && idTaskAllegato != null && idTaskCorrente.equalsIgnoreCase(idTaskAllegato));
	}
	
	public boolean isForceToShowGeneraDaModelloForTipoTaskDoc() {
		return false;
	}
	
	@Override
	public boolean hasDatiSensibili() {
		if(getShowFlgNoPubblAllegato() || getShowVersioneOmissis()) {	
			for (ListGridRecord record : grid.getRecords()) {
				boolean isParteDispositivo = record.getAttribute("flgParteDispositivo") != null ? Boolean.parseBoolean(record.getAttribute("flgParteDispositivo")) : false;
				boolean isEsclusoDaPubbl = getShowFlgNoPubblAllegato() && record.getAttribute("flgNoPubblAllegato") != null ? Boolean.parseBoolean(record.getAttribute("flgNoPubblAllegato")) : false;
				boolean isPubblicazioneSeparata = getShowFlgPubblicaSeparato() && isParteDispositivo && record.getAttribute("flgPubblicaSeparato") != null ? Boolean.parseBoolean(record.getAttribute("flgPubblicaSeparato")) : false;
				boolean hasDatiSensibili = getShowVersioneOmissis() && record.getAttribute("flgDatiSensibili") != null ? Boolean.parseBoolean(record.getAttribute("flgDatiSensibili")) : false;
				if(isParteDispositivo && (!isPubblicazioneSeparata || hasListaAllegatiParteIntSeparatiXPubbl()) && (isEsclusoDaPubbl || hasDatiSensibili)) {
					//faccio vers per pubbl. se è parte integrante AND (è unito OR ha il modello per gli allegati PI seperati per pubbl) AND (è escluso dalla pubbl. o ha dati sensibili)
					return true;
				}
			}
		}
		return false;			
	}
	
	public boolean hasListaAllegatiParteIntSeparatiXPubbl() {
		return false;
	}
		
	@Override
	public ListGrid buildGrid() {
		final ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.addDropCompleteHandler(new DropCompleteHandler() {
			
			@Override
			public void onDropComplete(DropCompleteEvent event) {
				int n = 1;
				for (ListGridRecord record : grid.getRecords()) {
					record.setAttribute("numeroProgrAllegatoAfterDrop", n);
					n++;
				}
				refreshNroAllegato();
			}
		});
		// Ordinamenti iniziali
		if (getShowFlgParteDispositivo() && getSortByFlgParteDispositivo()) {
			grid.addSort(new SortSpecifier("flgParteDispositivo", SortDirection.DESCENDING));			
		}
		
		grid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (event.getRecord() != null) {
					onRecordSelected(event.getRecord());
				}
			}
		});
		
		return grid;		
	}
	
	@Override
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {			
			if (grid.getFieldName(colNum).equals("nomeFileAllegato")) {  
	            if (record.getAttribute("nomeFileAllegato") != null && !"".equals(record.getAttribute("nomeFileAllegato").trim())) {  
	                return it.eng.utility.Styles.cellLink;  
	            } 
	        } else if (grid.getFieldName(colNum).equals("nomeFileOmissis")) {  
	        	if (record.getAttribute("nomeFileOmissis") != null && !"".equals(record.getAttribute("nomeFileOmissis").trim())) {  
	                return it.eng.utility.Styles.cellLink;  
	            } 
	        }
			String datoAnnullato = record.getAttribute("datoAnnullato");
			if(datoAnnullato != null) {
				if (datoAnnullato.equals("#TipoAllegato") && grid.getFieldName(colNum).equals("listaTipiFileAllegato")) {  
					return it.eng.utility.Styles.cellVariazione;  
			    } else if (datoAnnullato.equals("#DesAllegato") && grid.getFieldName(colNum).equals("descrizioneFileAllegato")) { 
			    	return it.eng.utility.Styles.cellVariazione; 
				} else if (datoAnnullato.equals("#FileAllegato") && grid.getFieldName(colNum).equals("nomeFileAllegato")) { 
					return it.eng.utility.Styles.cellVariazione; 
				}
			}
			return null;
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> editButtons = new ArrayList<ToolStripButton>();		
		ToolStripButton newButton = new ToolStripButton();   
		newButton.setIcon("buttons/new.png");   
		newButton.setIconSize(16);
		newButton.setPrefix("newButton");
		newButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		newButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				  onClickNewButton();
			}   
		});  		
		if (isShowNewButton()) {
			editButtons.add(newButton);
		}				
		return editButtons;
	}
	
	private RecordList listaRecordCaricatiInUploadMultiplo = new RecordList();
	
	public List<Canvas> buildCustomEditCanvas() {
		List<Canvas> editCanvas = new ArrayList<Canvas>();		
		uploadButton = new FileMultipleUploadItemWithFirmaAndMimeType(new UploadMultipleItemCallBackHandler() {

			@Override
			public void uploadEnd(String displayFileName, String uri, String numFileCaricatiInUploadMultiplo) {
				grid.deselectAllRecords();
				int nroLastAllegato = grid.getRecords().length;				
				final Record newRecord = new Record();
				newRecord.setAttribute("id", "NEW_" + count++);
				newRecord.setAttribute("flgSalvato", false);
				newRecord.setAttribute("changed", true);
				newRecord.setAttribute("numeroProgrAllegato", nroLastAllegato + 1);
				newRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroLastAllegato + 1);
				if(uri != null && !"".equals(uri)) {
					newRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
					if (!getFlgParteDispositivoDefaultValue()){
						newRecord.setAttribute("flgNoPubblAllegato", true);
						newRecord.setAttribute("flgPubblicaSeparato", false);
						newRecord.setAttribute("flgDatiSensibili", false);
					} else {
						newRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
					}
				}
				if (isProtInModalitaWizard()) {
					if (isCanaleSupportoDigitale()) {
						newRecord.setAttribute("flgOriginaleCartaceo", false);
					}
					if (isCanaleSupportoCartaceo()) {
						newRecord.setAttribute("flgOriginaleCartaceo", true);
					}
				}
				newRecord.setAttribute("nomeFileAllegato", displayFileName != null ? displayFileName : "");
				newRecord.setAttribute("uriFileAllegato", uri != null ? uri : "");
				newRecord.setAttribute("nomeFileAllegatoTif", "");
				newRecord.setAttribute("uriFileAllegatoTif", "");
				newRecord.setAttribute("remoteUri", false);	
				newRecord.setAttribute("nomeFileVerPreFirma", displayFileName != null ? displayFileName : "");
				newRecord.setAttribute("uriFileVerPreFirma", uri != null ? uri : "");
				newRecord.setAttribute("isUdSenzaFileImportata", false);
				newRecord.setAttribute("flgTimbraFilePostReg", false);	
				newRecord.setAttribute("flgTimbraFilePostRegOmissis", false);	
				newRecord.setAttribute("numFileCaricatiInUploadMultiplo", numFileCaricatiInUploadMultiplo);	
				// mi recupero il numero totale di file selezionati in upload
				int numFileCaricati = numFileCaricatiInUploadMultiplo != null && !"".equals(numFileCaricatiInUploadMultiplo) ? Integer.parseInt(numFileCaricatiInUploadMultiplo) : 0;
				// se il numero è maggiore di 1, e quindi si tratta di un upload multiplo
				if(numFileCaricati > 1) {
					// mi recupero il numero progressivo del file di cui sto facendo l'upload
					int nroProgrFile = listaRecordCaricatiInUploadMultiplo.getLength() + 1;
					if(nroProgrFile == 1) {
						// a partire dal primo upload mostro la waitPopup
						Layout.showWaitPopup("Caricamento file in corso...");
						numFileCaricatiInUploadMultiploGlobal = numFileCaricati;
					}
					// se non ho ancora terminato l'upload di tutti i file
					if(nroProgrFile <= numFileCaricati) {
						// aggiungo il mio file a listaRecordCaricatiInUploadMultiplo senza fare l'addData(), quella la farò soltanto alla fine quando avrò recuperato tutti i file con le relative info
						listaRecordCaricatiInUploadMultiplo.add(newRecord);						
					} else {
						// l'addData() di tutti i file la farò soltanto alla fine, quando avrò recuperato anche tutte le info
//						addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo);
//						listaRecordCaricatiInUploadMultiplo = new RecordList();
					}
				}
				// se invece è un upload singolo
				else {
					// in questo caso faccio direttamente l'addData() e resetto listaRecordCaricatiInUploadMultiplo
					addDataAndRefresh(newRecord);
					listaRecordCaricatiInUploadMultiplo = new RecordList();
				}
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				
				numFileCaricatiInErrore++;
				int nroProgrFile = listaRecordCaricatiInUploadMultiplo.getLength();
				if(nroProgrFile + numFileCaricatiInErrore == numFileCaricatiInUploadMultiploGlobal) {
					// aggiungo tutti i record alla grid con addAllData()
					addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo);
					// faccio i controlli su tutta la lista di file che ho aggiunto con l'upload multiplo
					controlAllAfterUploadMultiplo(listaRecordCaricatiInUploadMultiplo);
					// resetto listaRecordCaricatiInUploadMultiplo
					listaRecordCaricatiInUploadMultiplo = new RecordList();
					numFileCaricatiInErrore = 0;
				}
			}

			@Override
			public void cancelMultipleUpload() {
//				SC.say("Upload annullato");
				listaRecordCaricatiInUploadMultiplo = new RecordList();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				grid.deselectAllRecords();
				final Record record;
				// se è in corso un upload multiplo, e quindi listaRecordCaricatiInUploadMultiplo contiene degli elementi
				if(listaRecordCaricatiInUploadMultiplo.getLength() > 0) {
					// il record su cui dovò aggiornare le info sarà l'ultimo aggiunto a listaRecordCaricatiInUploadMultiplo
					record = listaRecordCaricatiInUploadMultiplo.get(listaRecordCaricatiInUploadMultiplo.getLength() - 1);
				} else if (indexLastAdd != -1) {
					record = grid.getRecords()[indexLastAdd];
				} else {
					int nroLastAllegato = grid.getRecords().length;					
					record = grid.getRecords()[nroLastAllegato - 1];
				}				
				InfoFileRecord precInfo = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				String displayName = record.getAttribute("nomeFileAllegato");
				String displayNameOrig = record.getAttribute("nomeFileAllegatoOrig");			
				final Record newRecord = new Record(record.getJsObj());
				newRecord.setAttribute("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || 
					(displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName.equals(displayNameOrig))) {
					newRecord.setAttribute("isChanged", true);
					manageChangedFileAllegato(newRecord);
				}
				newRecord.setAttribute("improntaVerPreFirma", info.getImpronta());
				newRecord.setAttribute("infoFileVerPreFirma", info);
				// se è in corso un upload multiplo, e quindi listaRecordCaricatiInUploadMultiplo contiene degli elementi
				if(listaRecordCaricatiInUploadMultiplo.getLength() > 0) {
					// mi recupero il numero totale di file selezionati in upload
					int numFileCaricati = newRecord.getAttribute("numFileCaricatiInUploadMultiplo") != null ? newRecord.getAttributeAsInt("numFileCaricatiInUploadMultiplo") : 0;
					// mi recupero il numero progressivo del file di cui ho appena fatto l'upload
					int nroProgrFile = listaRecordCaricatiInUploadMultiplo.getLength();				
					// aggiorno in listaRecordCaricatiInUploadMultiplo le info del file di cui ho appena fatto l'upload
					listaRecordCaricatiInUploadMultiplo.set(listaRecordCaricatiInUploadMultiplo.getLength() - 1, record);
					// se sono arrivato alla fine e ho ancora terminato l'upload di tutti i file
					if(nroProgrFile == numFileCaricati) {
						// aggiungo tutti i record alla grid con addAllData()
						addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo);
						// nascondo la waitPopup
//						Layout.hideWaitPopup(); // lo faccio in controlAllAfterUploadMultiplo
						// faccio i controlli su tutta la lista di file che ho aggiunto con l'upload multiplo
						controlAllAfterUploadMultiplo(listaRecordCaricatiInUploadMultiplo);
						// resetto listaRecordCaricatiInUploadMultiplo
						listaRecordCaricatiInUploadMultiplo = new RecordList();
					}
				} 
				// se è un upload singolo con updateData() aggiorno direttamente le info del file di cui ho appena fatto l'upload
				else {
					updateDataAndRefresh(newRecord, record);				
					// faccio i controlli sul file di cui ho appena fatto l'upload
					controlAfterUpload(info, newRecord, displayName, false);
					// resetto listaRecordCaricatiInUploadMultiplo
					listaRecordCaricatiInUploadMultiplo = new RecordList();
				}
			}
		});
		uploadButton.setCanFocus(false);
		uploadButton.setTabIndex(-1);
				
		if (isShowNewButton()) {

			DynamicForm otherNewButtonsForm = new DynamicForm();
			otherNewButtonsForm.setHeight(1);
			otherNewButtonsForm.setWidth(1);
			otherNewButtonsForm.setOverflow(Overflow.VISIBLE);
			otherNewButtonsForm.setPrefix("otherNewButtons");
			otherNewButtonsForm.setNumCols(20);
			
			List<FormItem> listaOtherNewButtonsFormFields = new ArrayList<FormItem>();
			listaOtherNewButtonsFormFields.add(uploadButton);
			
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && getShowImportaFileDaDocumenti()) {
				importaFileDocumentiButton = new ImgButtonItem("importaFileDocumentiButton", "buttons/importaFileDocumenti.png", getImportaFileDocumentiButtonTitle());
				importaFileDocumentiButton.addIconClickHandler(new IconClickHandler() {

					@Override
					public void onIconClick(IconClickEvent event) {
						ImportaAllegatiGridMultiLookupArchivio lookupArchivioPopup = new ImportaAllegatiGridMultiLookupArchivio(null);
						lookupArchivioPopup.show();
					}
				});	
				importaFileDocumentiButton.setCanFocus(false);
				importaFileDocumentiButton.setTabIndex(-1);
				listaOtherNewButtonsFormFields.add(importaFileDocumentiButton);
			}
			
			listaOtherNewButtonsFormFields.addAll(getCustmOtherNewButtons());
			otherNewButtonsForm.setFields(listaOtherNewButtonsFormFields.toArray(new FormItem[listaOtherNewButtonsFormFields.size()]));
			
			editCanvas.add(otherNewButtonsForm);
		}
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && getShowImportaFileDaDocumenti() && getShowCollegaDocumentiImportati()) {
			final CheckboxItem collegaDocumentiImportatiItem = new CheckboxItem("collegaDocumentiImportati", I18NUtil.getMessages().protocollazione_detail_collegaDocumentiFileImportati_title());
			collegaDocumentiImportatiItem.setValue(true);
			collegaDocumentiImportatiItem.setShowIfCondition(new FormItemIfFunction() {
	
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					boolean fileImportatiPresenti = false;
					for (ListGridRecord record : grid.getRecords()) {
						boolean fileImportato = record.getAttributeAsBoolean("fileImportato") != null && record.getAttributeAsBoolean("fileImportato");
						if (fileImportato) {
							fileImportatiPresenti = true;
						}
					}
					// Se non ci sono file importati resetto la check box
					if (!fileImportatiPresenti) {
						collegaDocumentiImportatiItem.setValue(true);
					}
					return fileImportatiPresenti;
				}
			});
			collegaDocumentiImportatiItem.addChangedHandler(new ChangedHandler() {
	
				@Override
				public void onChanged(ChangedEvent event) {
					if (event.getValue() != null) {
						for (ListGridRecord record : grid.getRecords()) {
							boolean fileImportato = record.getAttributeAsBoolean("fileImportato") != null && record.getAttributeAsBoolean("fileImportato");
							if (fileImportato) {
								record.setAttribute("collegaDocumentoImportato", event.getValue() != null && (Boolean) event.getValue());
							}
						}
						updateGridItemValue();	
					}
				}
			});
			DynamicForm collegaDocumentiImportatiForm = new DynamicForm();
			collegaDocumentiImportatiForm.setHeight(1);
			collegaDocumentiImportatiForm.setWidth(1);
			collegaDocumentiImportatiForm.setOverflow(Overflow.VISIBLE);
			collegaDocumentiImportatiForm.setPrefix("collegaDocumentiImportati");
			collegaDocumentiImportatiForm.setFields(collegaDocumentiImportatiItem);
	        editCanvas.add(collegaDocumentiImportatiForm);
		}
		
		if(getShowFlgPubblicaSeparato() && getFlgPubblicazioneAllegatiUguale()) { 
			CheckboxItem flgPubblicaAllegatiSeparatiItem = new CheckboxItem("flgPubblicaAllegatiSeparati", getTitleFlgPubblicaSeparato());
			flgPubblicaAllegatiSeparatiItem.setValue(getFlgAllegAttoPubblSepDefault());
			flgPubblicaAllegatiSeparatiItem.setColSpan(1);
			flgPubblicaAllegatiSeparatiItem.setWidth("*");
			// flgPubblicaAllegatiSeparatiItem.setLabelAsTitle(true);
			flgPubblicaAllegatiSeparatiItem.setShowTitle(true);
			flgPubblicaAllegatiSeparatiItem.setWrapTitle(false);			
			DynamicForm flgPubblicaAllegatiSeparatiForm = new DynamicForm();
			flgPubblicaAllegatiSeparatiForm.setHeight(1);
			flgPubblicaAllegatiSeparatiForm.setWidth(1);
			flgPubblicaAllegatiSeparatiForm.setOverflow(Overflow.VISIBLE);
			flgPubblicaAllegatiSeparatiForm.setPrefix("flgPubblicaAllegatiSeparati");
			flgPubblicaAllegatiSeparatiForm.setFields(flgPubblicaAllegatiSeparatiItem);
	        editCanvas.add(flgPubblicaAllegatiSeparatiForm);
		}
		
		return editCanvas;
	}
	
	public List<FormItem> getCustmOtherNewButtons() {
		return new ArrayList<FormItem>();
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}
	
	public void readOnlyMode() {
		setReadOnly(true);
		setCanEdit(true, false);		
	}
	
	public void setSoloPreparazioneVersPubblicazione(boolean soloPreparazioneVersPubblicazione) {
		this.soloPreparazioneVersPubblicazione = soloPreparazioneVersPubblicazione;
	}

	public boolean isSoloPreparazioneVersPubblicazione() {
		return soloPreparazioneVersPubblicazione;
	}
	
	public void soloPreparazioneVersPubblicazioneMode() {
		setSoloPreparazioneVersPubblicazione(true);
		setCanEdit(true, false);	
	}
	
	public void setSoloOmissisModProprietaFile(boolean soloOmissisModProprietaFile) {
		this.soloOmissisModProprietaFile = soloOmissisModProprietaFile;
	}

	public boolean isSoloOmissisModProprietaFile() {
		return soloOmissisModProprietaFile;
	}
		
	public boolean isAllegatiNonParteIntegranteNonEditabili() {
		return false;
	}

	public void soloOmissisModProprietaFileMode() {
		setSoloOmissisModProprietaFile(true);
		setCanEdit(true, false);		
		getGrid().setCanReorderRecords(true);		
	}
		
	public void setAttivaModificaEsclusionePubblicazione(boolean attivaModificaEsclusionePubblicazione) {
		this.attivaModificaEsclusionePubblicazione = attivaModificaEsclusionePubblicazione;
	}

	public boolean isAttivaModificaEsclusionePubblicazione() {
		return attivaModificaEsclusionePubblicazione;
	}
	
	public void attivaModificaEsclusionePubblicazioneMode() {
		setAttivaModificaEsclusionePubblicazione(true);
		setCanEdit(isEditable(), false);	
	}

	public void setAggiuntaFile(boolean aggiuntaFile) {
		this.aggiuntaFile = aggiuntaFile;
	}

	public boolean isAggiuntaFile() {
		return aggiuntaFile;
	}
	
	public void setAggiuntaFileMode() {
		setAggiuntaFile(true);
		setCanEdit(true, false);	
	}
	
	public void setCanEditOnlyOmissis(boolean canEditOnlyOmissis) {
		this.canEditOnlyOmissis = canEditOnlyOmissis;
	}

	public boolean isCanEditOnlyOmissis() {
		return canEditOnlyOmissis;
	}
	
	public void canEditOnlyOmissisMode() {
		setCanEditOnlyOmissis(true);
		setCanEdit(true, false);
	}
	
//	public void canEditOnlyOmissisMode() {
//		setCanEditOnlyOmissis(true);
//		setCanEdit(true, false);
//		hideOtherNewButtonsForm();
//		setReadOnly(false);
//		setShowDeleteButton(false);
//		setShowEditButtons(true);
//		setShowModifyButton(true);
//		if(this.getCanvas() != null) {
//			for(Canvas member : toolStrip.getMembers()) {
//				if(member instanceof ToolStripButton && !member.getTitle().equals(I18NUtil.getMessages().saveLayoutButton_title())) {
//						member.hide();
//				}
//			}
//			modifyButtonField.setCanEdit(false);
//			modifyButtonField.setHidden(true);
//			deleteButtonField.setCanEdit(false);
//			deleteButtonField.setHidden(true);
//			if (this.uploadFileAllegatoButton!=null) {
//				this.uploadFileAllegatoButton.setCanEdit(false);
//				this.uploadFileAllegatoButton.setHidden(true);
//			}
//			if (this.uploadFileOmissisButton!=null) {	
//				this.uploadFileOmissisButton.setCanEdit(true);
//			}
//			if (this.flgDatiSensibili!=null) {
//				this.flgDatiSensibili.setCanEdit(true);
//			}
//			if (this.nomeFileOmissis!=null) {
//				this.nomeFileOmissis.setCanEdit(true);
//			}
//			if (this.flgNoPubblAllegato!=null) {
//				this.flgNoPubblAllegato.setCanEdit(true);
//			}
//			if (this.numeroAllegato!=null) {
//				numeroAllegato.setCanEdit(false);
//			}
//			if (this.descTipoFileAllegato!=null) {
//				descTipoFileAllegato.setCanEdit(false);
//			}
//			if (this.descrizioneFileAllegato!=null) {
//				descrizioneFileAllegato.setCanEdit(false);
//			}
//			if (this.altreOpFileAllegatoButton!=null) {
//				altreOpFileAllegatoButton.setCanEdit(false);
//				altreOpFileAllegatoButton.setHidden(true);
//			}
//			if (this.altreOpFileOmissisButton!=null) {
//				altreOpFileOmissisButton.setCanEdit(true);
//			}
//			if (this.uploadFileOmissisButton!=null) {
//				uploadFileOmissisButton.setCanEdit(true);
//			}
//		}
//	}
	
	public boolean isNonModificabileVersOmissis(ListGridRecord record) {
		if(isSoloPreparazioneVersPubblicazione()) {
			boolean flgParteDispositivo = record.getAttribute("flgParteDispositivo") != null && "true".equalsIgnoreCase(record.getAttribute("flgParteDispositivo"));
			boolean flgNoPubblAllegato = record.getAttribute("flgNoPubblAllegato") != null && "true".equalsIgnoreCase(record.getAttribute("flgNoPubblAllegato"));
			boolean flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && "true".equalsIgnoreCase(record.getAttribute("flgPubblicaSeparato"));
			boolean flgPubblicaAllegatiSeparati = isFlgPubblicaAllegatiSeparati();
			boolean isModificabileVersOmissis = flgParteDispositivo && !flgNoPubblAllegato && (flgPubblicaAllegatiSeparati || flgPubblicaSeparato);
			return !isModificabileVersOmissis;
		}
		return false;
	}
		
	public boolean isModificabileSoloVersOmissis(ListGridRecord record) {
		boolean isModificabileSoloVersOmissis = isSoloPreparazioneVersPubblicazione() || isSoloOmissisModProprietaFile() || isCanEditOnlyOmissis();
		return isModificabileSoloVersOmissis;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setCanEdit(canEdit, true);
	}
	
	public void setCanEdit(Boolean canEdit, boolean flgResetMode) {
		
		setEditable(canEdit);
		
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("newButton")) {
					if(isEditable() && isShowEditButtons() && isShowNewButton()) {
						member.show();	
					} else {
						member.hide();						
					}
				}
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("otherNewButtons")) {
					if(isEditable() && isShowEditButtons() && isShowNewButton()) {
						member.show();	
					} else {
						member.hide();						
					}		
				}
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("flgPubblicaAllegatiSeparati")) {
					if(member instanceof DynamicForm) {
						for (FormItem item : ((DynamicForm)member).getFields()) {
							item.setCanEdit(isEditable() && isShowEditButtons() && !isReadOnly());
							item.redraw();
						}
					}
				}
			}		
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();			
//			refreshGridRecordComponents();
		}
		
		if(flgResetMode) {
			setReadOnly(false);
			setSoloPreparazioneVersPubblicazione(false);
			setSoloOmissisModProprietaFile(false);
			setAttivaModificaEsclusionePubblicazione(false);
			setAggiuntaFile(false);
			setCanEditOnlyOmissis(false);
		}				
	}	
	
	@Override
	public void redrawRecordButtons() {
		if(!isDisableGridRecordComponent()) {			
			try { grid.hideField("uploadFileAllegatoButton"); } catch(Exception e) {}
			try { grid.hideField("uploadFileOmissisButton"); } catch(Exception e) {}
		}
		try { grid.refreshFields(); } catch(Exception e) {}
		if(!isDisableGridRecordComponent()) {	
			try { grid.showField("uploadFileAllegatoButton"); } catch(Exception e) {}
			try { grid.showField("uploadFileOmissisButton"); } catch(Exception e) {}
		}
		grid.markForRedraw();			
	}
	
	public boolean isFlgOmissisChecked() {
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("flgDatiSensibili")) {
					if((boolean) member.getCanvasItem().getValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void hideOtherNewButtonsForm() {		
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("otherNewButtons")) {
					member.hide();												
				}
			}
		}		
	}
	
	public void setFlgPubblicaAllegatiSeparati(boolean flgPubblicaAllegatiSeparati) {
		if(getShowFlgPubblicaSeparato() && getFlgPubblicazioneAllegatiUguale()) { 			
			if(this.getCanvas() != null) {
				for(Canvas member : toolStrip.getMembers()) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("flgPubblicaAllegatiSeparati")) {
						if(member instanceof DynamicForm) {
							((DynamicForm)member).setValue("flgPubblicaAllegatiSeparati", flgPubblicaAllegatiSeparati);
						}
					}
				}
			}
		}
	}
	
	public boolean isFlgPubblicaAllegatiSeparati() {
		if(getShowFlgPubblicaSeparato() && getFlgPubblicazioneAllegatiUguale()) { 			
			if(this.getCanvas() != null) {
				for(Canvas member : toolStrip.getMembers()) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("flgPubblicaAllegatiSeparati")) {
						if(member instanceof DynamicForm) {
							Boolean flgPubblicaAllegatiSeparati = ((DynamicForm)member).getValue("flgPubblicaAllegatiSeparati") != null && (Boolean) ((DynamicForm)member).getValue("flgPubblicaAllegatiSeparati");
							return flgPubblicaAllegatiSeparati != null && flgPubblicaAllegatiSeparati;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isCollegaDocumentiImportati() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && getShowImportaFileDaDocumenti() && getShowCollegaDocumentiImportati()) {			
			if(this.getCanvas() != null) {
				for(Canvas member : toolStrip.getMembers()) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("collegaDocumentiImportati")) {
						if(member instanceof DynamicForm) {
							Boolean collegaDocumentiImportati = ((DynamicForm)member).getValue("collegaDocumentiImportati") != null && (Boolean) ((DynamicForm)member).getValue("collegaDocumentiImportati");
							return collegaDocumentiImportati != null && collegaDocumentiImportati;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void redrawCollegaDocumentiImportati() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_IMP_FILE_ALTRI_DOC") && getShowImportaFileDaDocumenti() && getShowCollegaDocumentiImportati()) {				
			if(this.getCanvas() != null) {
				for(Canvas member : toolStrip.getMembers()) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("collegaDocumentiImportati")) {
						if(member instanceof DynamicForm) {
							((DynamicForm)member).markForRedraw();
						}
					}
				}
			}
		}
	}
	
	public boolean getFlgParteDispositivoDefaultValue() {	
		boolean flgParteDispositivoDefaultValue = false;
		if(getShowFlgParteDispositivo() && !isDocumentiIstruttoria()) {
			// Controllo se devo settare il valore di default di parte integrante a true
			// Vale true se ((readOnly && abilitazione all'esclusione vale true) || (!readOnly && FLG_ALLEG_ATTO_PARTE_INT_DEFAULT vale true))
			boolean flgAllegAttoParteIntDefault = getFlgAllegAttoParteIntDefault();
			boolean isAttivaModificaEsclusionePubblicazione = isAttivaModificaEsclusionePubblicazione();
			if ((flgAllegAttoParteIntDefault && !isReadOnly()) || (isReadOnly() && isAttivaModificaEsclusionePubblicazione)){
				flgParteDispositivoDefaultValue = true;
			}else{
				flgParteDispositivoDefaultValue = false;
			}			
		}
		return flgParteDispositivoDefaultValue;
	}
	
	public boolean getFlgPubblicaSeparatoDefaultValue() {
		boolean flgParteDispositivoDefaultValue = getFlgParteDispositivoDefaultValue();
		boolean flgPubblicaSeparatoDefaultValue = false;
		if(getShowFlgPubblicaSeparato() /*&& !getFlgPubblicazioneAllegatiUguale()*/ && flgParteDispositivoDefaultValue) {
			flgPubblicaSeparatoDefaultValue = getFlgAllegAttoPubblSepDefault();
		}
		return flgPubblicaSeparatoDefaultValue;
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();	
		
		if(isShowSelectedButton()) {
			ControlListGridField selezionaButtonField = buildSelectedButtonField();	
			buttonsList.add(selezionaButtonField);
		}
		
		
		if(isShowEditButtons()) {
//			if(detailButtonField == null) {
//				detailButtonField = buildDetailButtonField();					
//			}
//			buttonsList.add(detailButtonField);
			if(isShowModifyButton()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
		} else {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
		}
		return buttonsList;
	}

	public boolean isShowSelectedButton() {
		return false;
	}

	/**
	 * @return
	 */
	public ControlListGridField buildSelectedButtonField() {
		ControlListGridField selezionaButtonField = new ControlListGridField("selezionaButton");  
		selezionaButtonField.setAttribute("custom", true);	
		selezionaButtonField.setShowHover(true);		
		selezionaButtonField.setCanReorder(false);
		selezionaButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				return buildImgButtonHtml("buttons/seleziona.png");
			}
		});
		selezionaButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Seleziona file";					
			}
		});		
		selezionaButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onRecordSelected(event.getRecord());				
			}
		});
		return selezionaButtonField;
	}
	
	public boolean isShowDetailButton(Record record) {
		if(showDettaglioUdAllegato(record) || showDettaglioEstremiProtocollo(record)) {		
			return false;
		}			
		return true;
	}
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {		
				if(isShowDetailButton(record)) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isShowDetailButton(record)) {
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(isShowDetailButton(event.getRecord())) {
					onClickDetailButton(event.getRecord());
				}
			}
		});		
		return detailButtonField;
	}
	
	public boolean isShowNewButton() {
		if(isSoloPreparazioneVersPubblicazione()) {
			return false;
		}		
		if(isSoloOmissisModProprietaFile()) {
			if(!isAllegatiNonParteIntegranteNonEditabili()) {
				return true;
			} else {
				return false;
			}
		}
		if(isCanEditOnlyOmissis()) {
			return false;
		}
		return true;
	}

	public boolean isShowModifyButton(Record record) {
		boolean isNewOrSavedInTaskCorrente = isNewOrSavedInTaskCorrente(record);
		boolean isAllegatoModelloAtti = isAllegatoModelloAtti(record);
		boolean isUdProtocollata = isUdProtocollata(record);
		boolean isUdAllegato = isUdAllegato(record); // per le UD allegato la riga deve essere modificabile per la descrizione	
		if (!isNewOrSavedInTaskCorrente || isAllegatoModelloAtti || isUdProtocollata) {		
			return false; // non è modificabile
		}
		if(isAggiuntaFile()	&& record.getAttribute("id") != null && !record.getAttribute("id").startsWith("NEW_")) {
			return false;
		}		
		return true;
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return buildImgButtonHtml("buttons/modify.png");
				} else if(isShowDetailButton(record)) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return I18NUtil.getMessages().modifyButton_prompt();
				} else if(isShowDetailButton(record)) {
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) {
					onClickModifyButton(event.getRecord());
				} else if(isShowDetailButton(event.getRecord())) {
					onClickDetailButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public boolean isShowDeleteButton(Record record) {
		// se è un documento o una unita doc. appena inserita o importata (e non ancora salvata) devo sempre poterla rimuovere
		if(record.getAttribute("id") != null && record.getAttribute("id").startsWith("NEW_")) {
			return true;
		}
		boolean isNewOrSavedInTaskCorrente = isNewOrSavedInTaskCorrente(record);
		boolean isAllegatoModelloAtti = isAllegatoModelloAtti(record);
		boolean isUdProtocollata = isUdProtocollata(record); 
		boolean isUdAllegato = isUdAllegato(record); // per le UD allegato la riga deve essere cancellabile
		if (!isNewOrSavedInTaskCorrente || isAllegatoModelloAtti || isUdProtocollata) {
			return false;
		}
		if(isSoloPreparazioneVersPubblicazione()) {
			return false;
		}
		if(isSoloOmissisModProprietaFile()) {
			boolean flgParteDispositivo = record.getAttribute("flgParteDispositivo") != null && record.getAttributeAsBoolean("flgParteDispositivo");
			if(!isAllegatiNonParteIntegranteNonEditabili() && !flgParteDispositivo) {
				return true;
			} else {
				return false;
			}
		}
		if(isCanEditOnlyOmissis()) {
			return false;
		}
		if(isAggiuntaFile()	&& record.getAttribute("id") != null && !record.getAttribute("id").startsWith("NEW_")) {
			return false;
		}
		return true;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(event.getRecord())) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});			 
		return deleteButtonField;
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
//		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields(getNomeLista(), fields);
	}	
	
	@Override
	public void onClickNewButton() {
		onClickNewButton(null);		
	}
	
	public void onClickNewButton(Record record) {
		grid.deselectAllRecords();
		AllegatoWindow lAllegatoWindow = new AllegatoWindow(this, "allegatoWindow", true, record, true) {
			
			@Override
			public void addAllegati(final RecordList listaNewRecord) {				
				if(listaNewRecord != null) {					
					int nroLastAllegato = grid.getRecords().length;
					for(int i  = 0; i < listaNewRecord.getLength(); i++) {
						// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB
						listaNewRecord.get(i).setAttribute("id", "NEW_" + count++);
						listaNewRecord.get(i).setAttribute("flgSalvato", false);
						listaNewRecord.get(i).setAttribute("changed", true);
						int numeroNewAllegato = nroLastAllegato + Integer.parseInt(listaNewRecord.get(i).getAttribute("numeroProgrAllegato"));
						listaNewRecord.get(i).setAttribute("numeroProgrAllegato", numeroNewAllegato);
						listaNewRecord.get(i).setAttribute("numeroProgrAllegatoAfterDrop", numeroNewAllegato);
//						addDataAndRefresh(listaNewRecord.get(i));
					}					
					addAllDataAndRefresh(listaNewRecord);						
				}
			}
		};
		lAllegatoWindow.show();
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final AllegatoWindow lAllegatoWindow = new AllegatoWindow(this, "allegatoWindow", false, record, false);
		lAllegatoWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono
		grid.deselectAllRecords();
		final int nroAllegato = Integer.parseInt(record.getAttribute("numeroProgrAllegato"));
		final AllegatoWindow lAllegatoWindow = new AllegatoWindow(this, "allegatoWindow", false, record, true) {
			
			@Override
			public void updateAllegato(final Record updatedRecord) {
				updatedRecord.setAttribute("changed", true);
				updatedRecord.setAttribute("flgNoModificaDati", false);
				updatedRecord.setAttribute("numeroProgrAllegato", nroAllegato);	
				updatedRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroAllegato);
				updateDataAndRefresh(updatedRecord, record);		
			}		
		};
		lAllegatoWindow.show();
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);		
		refreshNroAllegato();
	}
	
	public abstract boolean isGrigliaEditabile();
	
	public abstract void onRecordSelected(Record record);
	
	public String getIdProcess() {
		return null;
	}
	
	public String getIdFolder() {
		return null;
	}
	
	public String getIdDocFilePrimario(){
		return null;
	}
	
	public String getIdUd() {
		return null;
	}
	
	public String getIdProcessType() {
		return null;
	}

	public String getIdTaskCorrenteAllegati() {
		return null;
	}

	public HashSet<String> getTipiModelliAttiAllegati() {
		return null;
	}
	
	public boolean isObbligatorioFile() {
		return false;
	}
		
	public boolean validateFormatoFileAllegato(InfoFileRecord info) {
		return true;
	}
	
	public String getFormatoFileNonValidoErrorMessage() {
		return "Formato file non valido";
	}
	
	public String getFormatoFileOmissisNonValidoErrorMessage() {
		return "Formato file omissis non valido";
	}

	public boolean isFormatoAmmesso(InfoFileRecord info) {
		return true;
	}
		
	public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {
		return true;
	}	
	
	public String getRifiutoAllegatiConFirmeNonValide() {
		return null;
	}
	
	public boolean isDisattivaUnioneAllegatiFirmati() {
		return false;
	}
	
	public boolean isPubblicazioneSeparataPdfProtetti() {
		return false;
	}
	
	public String getFixedTipoAllegato() {
		return null;
	}	
	
	public void setFixedTipoFileAllegato(Record record, String idTipoFileAllegato, String descTipoFileAllegato) {
		record.setAttribute("idTipoFileAllegatoFixed", idTipoFileAllegato);
		record.setAttribute("descTipoFileAllegatoFixed", descTipoFileAllegato);
		// setto i valori del record
		record.setAttribute("listaTipiFileAllegato", idTipoFileAllegato);
		record.setAttribute("idTipoFileAllegato", idTipoFileAllegato);
		record.setAttribute("descTipoFileAllegato", descTipoFileAllegato);
	}
	
	public boolean getShowTipoAllegato() {
		return true;
	}
	
	public String getTipoAllegato(Record record) {
		String tipoAllegato = getShowTipoAllegato() ? record.getAttribute("listaTipiFileAllegato") : null;
		if(tipoAllegato == null || "".equals(tipoAllegato)) {
			tipoAllegato = record.getAttribute("idTipoFileAllegatoFixed") != null && !"".equals(record.getAttribute("idTipoFileAllegatoFixed")) ? record.getAttribute("idTipoFileAllegatoFixed") : getFixedTipoAllegato();
		}
		return tipoAllegato;
	}
	
	public boolean getShowGeneraDaModello() {
		return false;
	}
	
	public boolean isModelloModificabile(String idModello) {
		return true;
	}
	
	public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lGeneraDaModelloWithDatiDocDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
		lGeneraDaModelloWithDatiDocDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		lGeneraDaModelloWithDatiDocDataSource.executecustom("caricaModello", getRecordCaricaModelloAllegato(idModello, tipoModello), new DSCallback() {

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

	public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", getDetailRecord() != null ? getDetailRecord().getAttribute("idUd") : null);
		return modelloRecord;
	}
	
	public boolean getFlgAllegAttoParteIntDefault() {
		return AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PARTE_INT_DEFAULT");
	}
	
	public boolean getFlgAllegAttoPubblSepDefault() {
		return AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT");
	}
	
	public boolean isProtInModalitaWizard() {
		return false;
	}
	
	public boolean isCanaleSupportoDigitale() {
		return false;
	}

	public boolean isCanaleSupportoCartaceo() {
		return false;
	}
	
	public String getTitleFlgProvEsterna() {
		return "Provenienza";
	}
	
	public String getTitleFlgParere() {
		return "Parere";
	}
	
	public String getTitleFlgParteDispositivo() {
		return "Parte integrante";
	}
	
	public String getTitleFlgNoPubblAllegato() {
		return "Escludi pubblicazione";
	}
	
	public String getTitleFlgPubblicaSeparato() {
		String labelFlgAllegatoPIAttoSeparato = AurigaLayout.getParametroDB("LABEL_FLG_ALLEGATO_PI_ATTO_SEPARATO");
		if(labelFlgAllegatoPIAttoSeparato != null && !"".equals(labelFlgAllegatoPIAttoSeparato)) {
			return labelFlgAllegatoPIAttoSeparato;
		}		
		return "da non unire al dispositivo";
	}
	
	public String getTitleFlgDatiSensibili() {
		String labelFlgVerOmissis = AurigaLayout.getParametroDB("LABEL_FLG_VER_OMISSIS");
		if(labelFlgVerOmissis != null && !"".equals(labelFlgVerOmissis)) {
			return labelFlgVerOmissis;
		}		
		return "Ver. con omissis per pubblicazione";
	}
	
	public Integer getWidthDescrizioneFileAllegato() {
		return null;
	}
	
	public String getTitleNomeFileAllegato() {
		return "File";
	}
	
	public Integer getWidthNomeFileAllegato() {
		return null;
	}
	
	public boolean getShowFlgDatiProtettiTipo1() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo1() {
		return "dati protetti tipo 1";
	}
	
	public boolean getShowFlgDatiProtettiTipo2() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo2() {
		return "dati protetti tipo 2";
	}
	
	public boolean getShowFlgDatiProtettiTipo3() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo3() {
		return "dati protetti tipo 3";
	}
	
	public boolean getShowFlgDatiProtettiTipo4() {
		return false;
	}
	
	public String getTitleFlgDatiProtettiTipo4() {
		return "dati protetti tipo 4";
	}
	
	public boolean showDettaglioDocContrattiBarcode() {
		String flgTipoProv = getFlgTipoProvProtocollo();			
		if(flgTipoProv != null && "E".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A()) {
			Record detailRecord = getDetailRecord();
			boolean flgDocContrattiBarcode = detailRecord != null && detailRecord.getAttributeAsBoolean("flgDocContrattiBarcode") != null && detailRecord.getAttributeAsBoolean("flgDocContrattiBarcode");											
			return flgDocContrattiBarcode;
		}
		return false;
	}
	
	public boolean showDettaglioUdAllegato(Record record) {
		return isUdAllegato(record);
	}
	
	public boolean showDettaglioEstremiProtocollo(Record record) {
		return (isUdProtocollata(record) && isUdDocIstruttoriaCedAutotutela(record)) || isUdDocPraticaVisura(record) || isUdDocIstruttoriaDettFascicoloGenCompleto(record);
	}
	
	public void aggiornaEstremiProtUdAfterProtocolla(ListGridRecord listRecord, Record recordProtocollato) {
		if (recordProtocollato != null) {
			String codCategoriaProtocollo = recordProtocollato.getAttributeAsString("codCategoriaProtocollo");
			String numeroProtocollo = recordProtocollato.getAttributeAsString("nroProtocollo");
			String dataProtocollo = recordProtocollato.getAttributeAsString("dataProtocollo");
			if((codCategoriaProtocollo != null && "PG".equalsIgnoreCase(codCategoriaProtocollo)) && (numeroProtocollo != null && !"".equals(numeroProtocollo)) && (dataProtocollo != null && !"".equals(dataProtocollo))) {				
				String suffNumeroProtocollo = "";
				if (numeroProtocollo != null) {
					for (int i = 0; i < 7 - numeroProtocollo.length(); i++) {
						suffNumeroProtocollo += "0";
					}
				}
				if (dataProtocollo != null) {
					dataProtocollo = dataProtocollo.substring(0, 10);
				}
				String estremiProtocollo = codCategoriaProtocollo + " N° " + suffNumeroProtocollo + numeroProtocollo + " del " + dataProtocollo;
				listRecord.setAttribute("estremiProtUd", estremiProtocollo);
			}			
		}
	}
	
	public boolean isAttivaSceltaPosizioneAllegatiUniti() {
		return false;
	}
	
	public boolean getShowVersioneOmissis() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_VERS_CON_OMISSIS") /*&& !isShowFlgNoPubblAllegato()*/;
	}
	
	public boolean getShowFlgSostituisciVerPrec() {
		return false;
	}
	
	public boolean getShowFlgProvEsterna() {
		return false;
	}
	
	public boolean getShowFlgParere() {
		return false;
	}
	
	public boolean getSortByFlgParteDispositivo() {
		return false;
	}
	
	public boolean getShowFlgParteDispositivo() {
		return false;
	}
	
	public boolean getShowFlgNoPubblAllegato() {
		return false;
	}
	
	public boolean getShowFlgPubblicaSeparato() {
		return false;
	}
	
	public boolean getFlgPubblicazioneAllegatiUguale() {
		return false;
	}
	
	public boolean getFlgSoloPreparazioneVersPubblicazione() {
		return false;
	}
	
	public boolean getFlgSoloOmissisModProprietaFile() {
		return false;
	}
		
	public boolean getShowImportaFileDaDocumenti() {
		return true;
	}
	
	public boolean getShowCollegaDocumentiImportati() {
		return true;
	}
	
	public boolean getShowFlgTimbraFilePostReg(Record record) {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if (isAttivaTimbraturaFilePostRegAllegato()) {		
			boolean isEditing = isEditable() && isShowEditButtons() && isShowModifyButton(record);
			String flgTipoProv = getFlgTipoProvProtocollo();
			String uriFileAllegato = record.getAttribute("uriFileAllegato");
			InfoFileRecord infoFileAllegato = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
//			String mimetype = infoFileAllegato != null && infoFileAllegato.getMimetype() != null ? infoFileAllegato.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
				uriFileAllegato != null && !"".equals(uriFileAllegato) && infoFileAllegato != null && !infoFileAllegato.isFirmato() /*&& !mimetype.startsWith("image")*/) {
				String idDocAllegato = record.getAttribute("idDocAllegato");
				boolean isChanged = record.getAttributeAsBoolean("isChanged") != null && record.getAttributeAsBoolean("isChanged");
				boolean flgTimbratoFilePostReg = record.getAttributeAsBoolean("flgTimbratoFilePostReg") != null && record.getAttributeAsBoolean("flgTimbratoFilePostReg");
				boolean isNewOrChanged = idDocAllegato == null || "".equals(idDocAllegato) || isChanged;			
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChanged || !flgTimbratoFilePostReg) {  
					return true;
				}				
			}
		}
		return false;
	}
	
	public String getImportaFileDocumentiButtonTitle() {
		return  I18NUtil.getMessages().protocollazione_detail_importaFileDaAltriDocumenti_title();
	}
	
	public String getFinalitaImportaAllegatiMultiLookupArchivio() {
		return "IMPORTA_FILE";
	}
	
	public long getDimAlertAllegato() {
		return -1;
	}
	
	public long getDimMaxAllegatoXPubbl() {
		return -1;
	}
	
	public boolean isHideVisualizzaVersioniButton() {
		return false;
	}
	
	public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
		return false;
	}
	
	public boolean isHideFirmaInAltreOperazioniButton() {
		return false;
	}

	public boolean isHideTimbraInAltreOperazioniButton() {
		return false;
	}
	
	public boolean isAttivaTimbroTipologia() {
		return false;
	}
	
	public boolean isAttivaVociBarcode(){
		return false;
	}
	
	public boolean canBeEditedByApplet() {
		return false;
	}
	
	public boolean isAttivaTimbraturaFilePostRegAllegato() {
		return false; //return AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_FILE_POST_REG");
	}
	
	public boolean sonoInMail() {
		return false;
	}
	
	public boolean isScansioneImgAssoc() {
		return false;
	}
	
	public boolean sonoModificaVisualizza() {
		return false;
	}
	
	public void clickTrasformaInPrimario(int index) {

	}
	
	public boolean isFromFolderPraticaPregressa(){
		return false;
	}
	
	public String getNroDocumentoBarcodeLabel() {
		return "N° documento";
	}
	
	protected boolean isDocumentiInizialiIstanza() {
		return false;
	}
	
	protected boolean isDocumentiIstruttoria() {
		return false;
	}
		
	public boolean isDocPraticaVisure() {
		return false;
	}
	
	public boolean isDocCedAutotutela() {
		return false;
	}
	
	public boolean isDocIstruttoriaDettFascicoloGenCompleto() {
		return false;
	}

	public String getEmailContribuente() {
		return null;
	}
	
	public String getFlgTipoProvProtocollo() {
		return null;
	}
	
	public boolean getShowTimbraBarcodeMenuOmissis() {
		return false;
	}
	
	public boolean getShowFlgTimbraFilePostRegOmissis(Record record) {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if (isAttivaTimbraturaFilePostRegAllegato()) {		
			boolean isEditing = isEditable() && isShowEditButtons() && isShowModifyButton(record);
			String flgTipoProv = getFlgTipoProvProtocollo();
			String uriFileOmissis = record.getAttribute("uriFileOmissis");
			InfoFileRecord infoFileOmissis = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;			
//			String mimetypeOmissis = infoFileOmissis != null && infoFileOmissis.getMimetype() != null ? infoFileOmissis.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
					uriFileOmissis != null && !"".equals(uriFileOmissis) && infoFileOmissis != null && !infoFileOmissis.isFirmato() /*&& !mimetypeOmissis.startsWith("image")*/) {
				String idDocOmissis = record.getAttribute("idDocOmissis");
				boolean isChangedOmissis = record.getAttributeAsBoolean("isChangedOmissis") != null && record.getAttributeAsBoolean("isChangedOmissis");
				boolean flgTimbratoFilePostRegOmissis = record.getAttributeAsBoolean("flgTimbratoFilePostRegOmissis") != null && record.getAttributeAsBoolean("flgTimbratoFilePostRegOmissis");
				boolean isNewOrChangedOmissis = idDocOmissis == null || "".equals(idDocOmissis) || isChangedOmissis;			
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChangedOmissis || !flgTimbratoFilePostRegOmissis) {  
					return true;
				}				
			}
		}
		return false;	
	}
	
	/************************************************************************************************************************************************/
	
	// Visualizza informazioni firma digitale
	public void generaFileFirmaAndPreview(final InfoFileRecord infoFileAllegato, String uriFileAllegato, boolean aggiungiFirma) {
		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));		
		if(getDataRifValiditaFirma() != null) {
			String dataRiferimento = DateUtil.format(getDataRifValiditaFirma());
			lGwtRestDataSource.extraparam.put("dataRiferimento", dataRiferimento);
		}
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", filename, isShowModalPreview());
			}
		});
	}
	
	public String getEstremiProtocollo() {
		String estremiProtocollo = null;
		Record recordProtocollo = getDetailRecord();
		if (recordProtocollo != null) {
			estremiProtocollo = recordProtocollo.getAttribute("siglaProtocollo") + " " + recordProtocollo.getAttribute("nroProtocollo") +
					getSuffixSubProtocollo(recordProtocollo.getAttribute("subProtocollo")) + " " + DateUtil.formatAsShortDatetime(recordProtocollo.getAttributeAsDate("dataProtocollo"));
		} 
		return estremiProtocollo;
	}

	protected String getSuffixSubProtocollo(String subProtocollo) {
		return subProtocollo != null && !"".equals(subProtocollo) ? "." + subProtocollo : "";		
	}

	// Visualizza versioni file
	public void clickVisualizzaVersioni(Record record) {
		clickVisualizzaVersioni(grid.getRecord(grid.getRecordIndex(record)));
	}	
	
	public void clickVisualizzaVersioni(ListGridRecord listRecord) {
		ProtocollazioneDetail.visualizzaVersioni(listRecord.getAttributeAsString("idDocAllegato"), "A", listRecord.getAttributeAsString("numeroProgrAllegato"), getEstremiProtocollo(), getDetailRecord());
	}

	public void clickVisualizzaVersioniOmissis(Record record) {
		clickVisualizzaVersioniOmissis(grid.getRecord(grid.getRecordIndex(record)));
	}
	
	public void clickVisualizzaVersioniOmissis(ListGridRecord listRecord) {
		ProtocollazioneDetail.visualizzaVersioni(listRecord.getAttributeAsString("idDocOmissis"), "AO", listRecord.getAttributeAsString("numeroProgrAllegato"), getEstremiProtocollo(), getDetailRecord());
	}	
	
	// Preview del file
	public void clickPreviewFile(final ListGridRecord listRecord, final boolean flgOmissis) {
		
		final String idDocFieldName = flgOmissis ? "idDocOmissis" : "idDocAllegato"; 
		final String nomeFileFieldName = flgOmissis ? "nomeFileOmissis" : "nomeFileAllegato"; 
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String remoteUriFieldName = flgOmissis ? "remoteUriOmissis" : "remoteUri";
		
		final Record detailRecord = getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDoc = listRecord.getAttribute(idDocFieldName);
		addToRecent(idUd, idDoc);
		final String display = listRecord.getAttribute(nomeFileFieldName);
		final String uri = listRecord.getAttribute(uriFileFieldName);
		final Boolean remoteUri = Boolean.valueOf(listRecord.getAttribute(remoteUriFieldName));
		final InfoFileRecord info = listRecord.getAttribute(infoFileFieldName) != null ? new InfoFileRecord(listRecord.getAttributeAsRecord(infoFileFieldName)) : null;
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
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
						preview(listRecord, detailRecord, idUd, display, uri, remoteUri, info, flgOmissis);
					}
				}
			});
		} else {
			preview(listRecord, detailRecord, idUd, display, uri, remoteUri, info, flgOmissis);
		}
	}		
	
	public void preview(final ListGridRecord listRecord, final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info, boolean isOmissis) {
		if(isModificabileSoloVersOmissis(listRecord) && !isNonModificabileVersOmissis(listRecord)) {
			PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
				
				@Override
				public void executeSalvaVersConOmissis(Record record) {
					
				}
				
				@Override
				public void executeSalva(Record record) {				
					if (getShowVersioneOmissis()) {
						grid.deselectAllRecords();										
						String uri = record.getAttribute("uri");
						String displayFileName = record.getAttribute("fileName");
						InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
						InfoFileRecord precInfo = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						final Record listNewRecord = new Record(listRecord.getJsObj());
						listNewRecord.setAttribute("changed", true);
						listNewRecord.setAttribute("flgNoModificaDati", false);
						listNewRecord.setAttribute("infoFileOmissis", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							listNewRecord.setAttribute("isChangedOmissis", true);
							manageChangedFileOmissis(listNewRecord);
						}
						listNewRecord.setAttribute("nomeFileOmissis", displayFileName != null ? displayFileName : "");
						listNewRecord.setAttribute("uriFileOmissis", uri != null ? uri : "");
						listNewRecord.setAttribute("nomeFileTifOmissis", "");
						listNewRecord.setAttribute("uriFileTifOmissis", "");
						listNewRecord.setAttribute("remoteUriOmissis", false);
						listNewRecord.setAttribute("nomeFileVerPreFirmaOmissis", displayFileName != null ? displayFileName : "");
						listNewRecord.setAttribute("uriFileVerPreFirmaOmissis", uri != null ? uri : "");
						listNewRecord.setAttribute("improntaVerPreFirmaOmissis", info.getImpronta());
						listNewRecord.setAttribute("infoFileVerPreFirmaOmissis", info);	
						listNewRecord.setAttribute("flgDatiSensibili", uri != null && !"".equals(uri));
						updateDataAndRefresh(listNewRecord, listRecord);	
						controlAfterUpload(info, listNewRecord, true);	
					}		
				}
				
				@Override
				public void executeOnError() {	
					
				}				
			};
			
			PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, false, false, isShowModalPreview());						
		} else if(isOmissis) {
			PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
				
				@Override
				public void executeSalvaVersConOmissis(Record record) {
					
				}
				
				@Override
				public void executeSalva(Record record) {				
					if (getShowVersioneOmissis()) {
						grid.deselectAllRecords();										
						String uri = record.getAttribute("uri");
						String displayFileName = record.getAttribute("fileName");
						InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
						InfoFileRecord precInfo = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						final Record listNewRecord = new Record(listRecord.getJsObj());
						listNewRecord.setAttribute("changed", true);
						listNewRecord.setAttribute("flgNoModificaDati", false);
						listNewRecord.setAttribute("infoFileOmissis", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							listNewRecord.setAttribute("isChangedOmissis", true);
							manageChangedFileOmissis(listNewRecord);
						}
						listNewRecord.setAttribute("nomeFileOmissis", displayFileName != null ? displayFileName : "");
						listNewRecord.setAttribute("uriFileOmissis", uri != null ? uri : "");
						listNewRecord.setAttribute("nomeFileTifOmissis", "");
						listNewRecord.setAttribute("uriFileTifOmissis", "");
						listNewRecord.setAttribute("remoteUriOmissis", false);
						listNewRecord.setAttribute("nomeFileVerPreFirmaOmissis", displayFileName != null ? displayFileName : "");
						listNewRecord.setAttribute("uriFileVerPreFirmaOmissis", uri != null ? uri : "");
						listNewRecord.setAttribute("improntaVerPreFirmaOmissis", info.getImpronta());
						listNewRecord.setAttribute("infoFileVerPreFirmaOmissis", info);	
						listNewRecord.setAttribute("flgDatiSensibili", uri != null && !"".equals(uri));
						updateDataAndRefresh(listNewRecord, listRecord);	
						controlAfterUpload(info, listNewRecord, true);	
					}		
				}
				
				@Override
				public void executeOnError() {	
					
				}				
			};
			
			boolean isViewMode = !(isEditable() && isShowEditButtons() && isShowModifyButton(listRecord));
			PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, isViewMode, false, isShowModalPreview());
		} else {
			PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
				
				@Override
				public void executeSalvaVersConOmissis(Record record) {
					if (getShowVersioneOmissis()) {
						grid.deselectAllRecords();										
						String uri = record.getAttribute("uri");
						String displayFileName = record.getAttribute("fileName");
						InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
						InfoFileRecord precInfo = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						final Record listNewRecord = new Record(listRecord.getJsObj());
						listNewRecord.setAttribute("changed", true);
						listNewRecord.setAttribute("flgNoModificaDati", false);
						listNewRecord.setAttribute("infoFileOmissis", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							listNewRecord.setAttribute("isChangedOmissis", true);
							manageChangedFileOmissis(listNewRecord);
						}
						listNewRecord.setAttribute("nomeFileOmissis", displayFileName != null ? displayFileName : "");
						listNewRecord.setAttribute("uriFileOmissis", uri != null ? uri : "");
						listNewRecord.setAttribute("nomeFileTifOmissis", "");
						listNewRecord.setAttribute("uriFileTifOmissis", "");
						listNewRecord.setAttribute("remoteUriOmissis", false);
						listNewRecord.setAttribute("nomeFileVerPreFirmaOmissis", displayFileName != null ? displayFileName : "");
						listNewRecord.setAttribute("uriFileVerPreFirmaOmissis", uri != null ? uri : "");
						listNewRecord.setAttribute("improntaVerPreFirmaOmissis", info.getImpronta());
						listNewRecord.setAttribute("infoFileVerPreFirmaOmissis", info);	
						listNewRecord.setAttribute("flgDatiSensibili", uri != null && !"".equals(uri));
						updateDataAndRefresh(listNewRecord, listRecord);		
						controlAfterUpload(info, listNewRecord, true);	
					}
				}
				
				@Override
				public void executeSalva(Record record) {
					grid.deselectAllRecords();									
					String uri = record.getAttribute("uri");
					String displayFileName = record.getAttribute("fileName");
					InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
					InfoFileRecord precInfo = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
					String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
					final Record listNewRecord = new Record(listRecord.getJsObj());
					listNewRecord.setAttribute("changed", true);
					listNewRecord.setAttribute("flgNoModificaDati", false);
					listNewRecord.setAttribute("infoFile", info);
					if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
						listNewRecord.setAttribute("isChanged", true);
						manageChangedFileAllegato(listNewRecord);
					}
					listNewRecord.setAttribute("nomeFileAllegato", displayFileName != null ? displayFileName : "");
					listNewRecord.setAttribute("uriFileAllegato", uri != null ? uri : "");
					listNewRecord.setAttribute("nomeFileAllegatoTif", "");
					listNewRecord.setAttribute("uriFileAllegatoTif", "");
					listNewRecord.setAttribute("remoteUri", false);
					listNewRecord.setAttribute("nomeFileVerPreFirma", displayFileName != null ? displayFileName : "");
					listNewRecord.setAttribute("uriFileVerPreFirma", uri != null ? uri : "");
					listNewRecord.setAttribute("improntaVerPreFirma", info.getImpronta());
					listNewRecord.setAttribute("infoFileVerPreFirma", info);				
					updateDataAndRefresh(listNewRecord, listRecord);	
					controlAfterUpload(info, listNewRecord, false);	
				}
				
				@Override
				public void executeOnError() {				
				}
			};
	 
			boolean isViewMode = !(isEditable() && isShowEditButtons() && isShowModifyButton(listRecord) && !isModificabileSoloVersOmissis(listRecord));
			boolean enableOptionToSaveInOmissisForm = getShowVersioneOmissis();
			PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, isViewMode, enableOptionToSaveInOmissisForm, isShowModalPreview());			
		}
	}

	// Download del file
	protected void clickDownloadFile(Record record) {
		Record detailRecord = getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = record.getAttribute("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		String display = record.getAttribute("nomeFileAllegato");
		String uri = record.getAttribute("uriFileAllegato");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", record.getAttribute("remoteUri"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	protected void clickDownloadFileOmissis(Record record) {
		Record detailRecord = getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocOmissis = record.getAttribute("idDocOmissis");
		addToRecent(idUd, idDocOmissis);
		String display = record.getAttribute("nomeFileOmissis");
		String uri = record.getAttribute("uriFileOmissis");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", record.getAttribute("remoteUriOmissis"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file p7m sbustato
	public void clickDownloadFileSbustato(Record record) {
		Record detailRecord = getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = record.getAttribute("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		String display = record.getAttribute("nomeFileAllegato");
		String uri = record.getAttribute("uriFileAllegato");
		InfoFileRecord info = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", record.getAttribute("remoteUri"));
		lRecord.setAttribute("correctFilename", info.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public void clickDownloadFileOmissisSbustato(Record record) {
		Record detailRecord = getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocOmissis = record.getAttribute("idDocOmissis");
		addToRecent(idUd, idDocOmissis);
		String display = record.getAttribute("nomeFileOmissis");
		String uri = record.getAttribute("uriFileOmissis");
		InfoFileRecord info = record.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFileOmissis")) : null;
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", record.getAttribute("remoteUriOmissis"));
		lRecord.setAttribute("correctFilename", info.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	// Acquisisci da scanner
	public void clickAcquisisciDaScanner(final ListGridRecord listRecord) {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(String filePdf, String uriPdf, String fileTif, String uriTif, String record) {				
				grid.deselectAllRecords();									
				InfoFileRecord precInfo = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				final Record newListRecord = new Record(listRecord.getJsObj());
				newListRecord.setAttribute("changed", true);
				newListRecord.setAttribute("flgNoModificaDati", false);
//				if(uriPdf != null && !"".equals(uriPdf)) {
//					newListRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
//					if(!getFlgParteDispositivoDefaultValue()) {
//						newListRecord.setAttribute("flgNoPubblAllegato", true);
//						newListRecord.setAttribute("flgPubblicaSeparato", false);
//						newListRecord.setAttribute("flgDatiSensibili", false);
//					} else {
//						newListRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
//					}	
//				}
				if (isProtInModalitaWizard()) {
					if (isCanaleSupportoDigitale()) {
						newListRecord.setAttribute("flgOriginaleCartaceo", false);
					}
					if (isCanaleSupportoCartaceo()) {
						newListRecord.setAttribute("flgOriginaleCartaceo", true);
					}
				}
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				newListRecord.setAttribute("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					newListRecord.setAttribute("isChanged", true);
					manageChangedFileAllegato(newListRecord);
				}
				newListRecord.setAttribute("nomeFileAllegato", filePdf != null ? filePdf : "");
				newListRecord.setAttribute("uriFileAllegato", uriPdf != null ? uriPdf : "");
				newListRecord.setAttribute("nomeFileAllegatoTif", fileTif != null ? fileTif : "");
				newListRecord.setAttribute("uriFileAllegatoTif", uriTif != null ? uriTif : "");
				newListRecord.setAttribute("remoteUri", false);
				newListRecord.setAttribute("nomeFileVerPreFirma", filePdf != null ? filePdf : "");
				newListRecord.setAttribute("uriFileVerPreFirma", uriPdf != null ? uriPdf : "");
				newListRecord.setAttribute("improntaVerPreFirma", info.getImpronta());
				newListRecord.setAttribute("infoFileVerPreFirma", info);				
				updateDataAndRefresh(newListRecord, listRecord);		
				controlAfterUpload(info, newListRecord, false);															
			}
		});
	}
	
	public void clickAcquisisciDaScannerOmissis(final ListGridRecord listRecord) {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(String filePdf, String uriPdf, String fileTif, String uriTif, String record) {				
				grid.deselectAllRecords();									
				InfoFileRecord precInfo = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				final Record newListRecord = new Record(listRecord.getJsObj());
				newListRecord.setAttribute("changed", true);
				newListRecord.setAttribute("flgNoModificaDati", false);
//				if(uriPdf != null && !"".equals(uriPdf)) {
//					newListRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
//					if(!getFlgParteDispositivoDefaultValue()) {
//						newListRecord.setAttribute("flgNoPubblAllegato", true);
//						newListRecord.setAttribute("flgPubblicaSeparato", false);
//						newListRecord.setAttribute("flgDatiSensibili", false);
//					} else {
//						newListRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
//					}						
//				}
				if (isProtInModalitaWizard()) {
					if (isCanaleSupportoDigitale()) {
						newListRecord.setAttribute("flgOriginaleCartaceo", false);
					}
					if (isCanaleSupportoCartaceo()) {
						newListRecord.setAttribute("flgOriginaleCartaceo", true);
					}
				}
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				newListRecord.setAttribute("infoFileOmissis", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					newListRecord.setAttribute("isChangedOmissis", true);
					manageChangedFileOmissis(newListRecord);
				}
				newListRecord.setAttribute("nomeFileOmissis", filePdf != null ? filePdf : "");
				newListRecord.setAttribute("uriFileOmissis", uriPdf != null ? uriPdf : "");
				newListRecord.setAttribute("nomeFileTifOmissis", fileTif != null ? fileTif : "");
				newListRecord.setAttribute("uriFileTifOmissis", uriTif != null ? uriTif : "");
				newListRecord.setAttribute("remoteUriOmissis", false);
				newListRecord.setAttribute("nomeFileVerPreFirmaOmissis", filePdf != null ? filePdf : "");
				newListRecord.setAttribute("uriFileVerPreFirmaOmissis", uriPdf != null ? uriPdf : "");
				newListRecord.setAttribute("improntaVerPreFirmaOmissis", info.getImpronta());
				newListRecord.setAttribute("infoFileVerPreFirmaOmissis", info);				
				newListRecord.setAttribute("flgDatiSensibili", uriPdf != null ? uriPdf : "");	
				updateDataAndRefresh(newListRecord, listRecord);	
				controlAfterUpload(info, newListRecord, true);								
			}
		});		
	}

	// Firma file
	public void clickFirma(ListGridRecord listRecord) {
		firma(listRecord, null);
	}

	public void firma(final ListGridRecord listRecord, final ServiceCallback<Record> callback) {
		String display = listRecord.getAttribute("nomeFileAllegato");
		String uri = listRecord.getAttribute("uriFileAllegato");
		InfoFileRecord info = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
		FirmaUtility.firmaMultipla(uri, display, info, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
				grid.deselectAllRecords();									
				InfoFileRecord precInfo = listRecord.getAttribute("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFile")) : null;
				final String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				final Record newListRecord = new Record(listRecord.getJsObj());
				newListRecord.setAttribute("changed", true);
				newListRecord.setAttribute("flgNoModificaDati", false);
				newListRecord.setAttribute("infoFile", infoFileFirmato);
				if (precImpronta == null || !precImpronta.equals(infoFileFirmato.getImpronta())) {
					newListRecord.setAttribute("isChanged", true);
					manageChangedFileAllegato(newListRecord);
				}
				newListRecord.setAttribute("nomeFileAllegato", nomeFileFirmato != null ? nomeFileFirmato : "");
				newListRecord.setAttribute("uriFileAllegato", uriFileFirmato != null ? uriFileFirmato : "");
				newListRecord.setAttribute("nomeFileAllegatoTif", "");
				newListRecord.setAttribute("uriFileAllegatoTif", "");
				newListRecord.setAttribute("remoteUri", false);
				updateDataAndRefresh(newListRecord, listRecord);
				controlAfterUpload(infoFileFirmato, newListRecord, false);	
				if (callback != null) {
					callback.execute(newListRecord);
				}
			}
		});
	}
	
	public void clickFirmaOmissis(ListGridRecord listRecord) {
		firmaOmissis(listRecord, null);
	}

	public void firmaOmissis(final ListGridRecord listRecord, final ServiceCallback<Record> callback) {
		String display = listRecord.getAttribute("nomeFileOmissis");
		String uri = listRecord.getAttribute("uriFileOmissis");
		InfoFileRecord info = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
		FirmaUtility.firmaMultipla(uri, display, info, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
				grid.deselectAllRecords();					
				InfoFileRecord precInfo = listRecord.getAttribute("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsRecord("infoFileOmissis")) : null;
				final String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				final Record newListRecord = new Record(listRecord.getJsObj());
				newListRecord.setAttribute("changed", true);
				newListRecord.setAttribute("flgNoModificaDati", false);
				newListRecord.setAttribute("infoFileOmissis", infoFileFirmato);
				if (precImpronta == null || !precImpronta.equals(infoFileFirmato.getImpronta())) {
					newListRecord.setAttribute("isChangedOmissis", true);
					manageChangedFileOmissis(newListRecord);
				}				
				newListRecord.setAttribute("nomeFileOmissis", nomeFileFirmato != null ? nomeFileFirmato : "");
				newListRecord.setAttribute("uriFileOmissis", uriFileFirmato != null ? uriFileFirmato : "");
				newListRecord.setAttribute("nomeFileTifOmissis", "");
				newListRecord.setAttribute("uriFileTifOmissis", "");
				newListRecord.setAttribute("remoteUriOmissis", false);
				updateDataAndRefresh(newListRecord, listRecord);
				controlAfterUpload(infoFileFirmato, newListRecord, true);	
				if (callback != null) {
					callback.execute(newListRecord);
				}
			}
		});
	}
	
	// Copertina attestato conformità all'originale
	private void creaAttestato(String idUd, String idDoc, final InfoFileRecord infoFileAllegato, String uriFileAllegato, final boolean attestatoFirmato) {
		Record estremiProtocollo = getDetailRecord();
		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);
		record.setAttribute("siglaProtocollo", estremiProtocollo.getAttribute("siglaProtocollo"));
		record.setAttribute("nroProtocollo", estremiProtocollo.getAttribute("nroProtocollo"));
		record.setAttribute("desUserProtocollo", estremiProtocollo.getAttribute("desUserProtocollo"));
		record.setAttribute("desUOProtocollo", estremiProtocollo.getAttribute("desUOProtocollo"));
		try {
			record.setAttribute("dataProtocollo", DateUtil.formatAsShortDatetime((estremiProtocollo.getAttributeAsDate("dataProtocollo"))));
		} catch (Exception e) {
		}
		record.setAttribute("idUd", idUd);
		record.setAttribute("idDoc", idDoc);
		record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
		lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());
		lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
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
	 * 
	 * Metodo per la costruzione delle voci di Menù relative a 
	 * - Timbra ( Dati segnatura, Dati tipologia, Timbro conformita cartaceo, Timbro conformita originale )
	 * - Barcode su A4 ( Nro documento , Nro documento + posizione )
	 * - Barcode multipli su A4 ( Nro documento , Nro documento + posizione )
	 * - Barcode su etichetta ( Nro documento , Nro documento + posizione )
	 * - Barcode multipli su etichetta ( Nro documento , Nro documento + posizione )
	 */	
	protected void buildMenuBarcodeEtichetta(ListGridRecord record, Menu altreOpMenu, boolean flgOmissis){		
		
		final String idDocFieldName = flgOmissis ? "idDocOmissis" : "idDocAllegato"; 
		final String nomeFileFieldName = flgOmissis ? "nomeFileOmissis" : "nomeFileAllegato"; 
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String remoteUriFieldName = flgOmissis ? "remoteUriOmissis" : "remoteUri";
		
		boolean flgAddSubMenuTimbra = false;
		final String idDoc = record.getAttribute(idDocFieldName);
		final String nomeFile = record.getAttribute(nomeFileFieldName);
		final String uri = record.getAttribute(uriFileFieldName);
		final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(record.getAttributeAsObject(infoFileFieldName));		
		final String remoteUri = record.getAttribute(remoteUriFieldName);
		final String idUdAppartenenza = record.getAttribute("idUdAppartenenza");	
		final String numeroProgrAllegato = record.getAttribute("numeroProgrAllegato");	
		final String listaTipiFileAllegato = record.getAttribute("listaTipiFileAllegato");	
		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();
		MenuItem timbraDatiSegnaturaMenuItem = null;		
		/**
		 * COSTRUISCO IL TASTO TIMBRA SE:
		 * 
		 * 1.Se si sta aprendo il menu della risposta di una istanza CED o autotutela e questa
			 è stata protocollata, devo visualizzare il l'opzione di scarico del file timbrato.
			 
			  o
			 
		   2.Se attiva la voce di barcorde ed è presente l'uri del file Primario
		*/
		if( (((isUdDocIstruttoriaCedAutotutela(record) /*&& isTipoRisposta*/) || isUdDocIstruttoriaDettFascicoloGenCompleto(record)) && isUdProtocollata(record)) 
				|| (isAttivaVociBarcode() && lInfoFileRecord != null)) {
			timbraDatiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiSegnaturaMenuItem(), "file/timbra.gif");
			timbraDatiSegnaturaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return uri != null && !uri.equals("");
				}
			});			
			timbraDatiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					clickTimbraDatiSegnatura(idDoc, nomeFile, uri, remoteUri, lInfoFileRecord, idUdAppartenenza, "");
				}
			});			
			timbraSubMenu.addItem(timbraDatiSegnaturaMenuItem);			
		} 
		if (isAttivaVociBarcode() && lInfoFileRecord != null){			
			MenuItem timbraDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_timbraDatiTipologiaMenuItem());
			timbraDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {				
					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					if(getIdDocFilePrimario() != null && !"".equals(getIdDocFilePrimario())){
						clickTimbraDatiTipologia();
					}else{
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().allegato_canvas_messageDocumentoNonTipizzato(),"",MessageType.WARNING));
					}
				}
			});
			timbraDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {	
					return isAttivaTimbroTipologia() && (uri != null && !uri.equals("")) && 
						   getIdDocFilePrimario() != null && !"".equals(getIdDocFilePrimario()) && 
						   lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				}
			});
			flgAddSubMenuTimbra=true;
			timbraSubMenu.addItem(timbraDatiTipologiaMenuItem);
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
				if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && 
						(lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
					MenuItem timbroConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
					timbroConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							return uri != null && !uri.equals("");
						}
					});
					timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickTimbraDatiSegnatura(idDoc, nomeFile, uri, remoteUri, lInfoFileRecord, idUdAppartenenza, "CONFORMITA_ORIG_CARTACEO");
						}
					});		
					timbraSubMenu.addItem(timbroConformitaOriginaleMenuItem);
				}
			}
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
				MenuItem timbroCopiaConformeMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
				timbroCopiaConformeMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return uri != null && !uri.equals("");
					}
				});
				Menu sottoMenuCopiaConformeItem = new Menu();
				
				MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
				timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura(idDoc, nomeFile, uri, remoteUri, lInfoFileRecord, idUdAppartenenza, "CONFORMITA_ORIG_DIGITALE_STAMPA");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
				
				MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
				timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						clickTimbraDatiSegnatura(idDoc, nomeFile, uri, remoteUri, lInfoFileRecord, idUdAppartenenza, "CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
					}
				});
				sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
				
				timbroCopiaConformeMenuItem.setSubmenu(sottoMenuCopiaConformeItem);				
				timbraSubMenu.addItem(timbroCopiaConformeMenuItem);
			}					
			timbraMenuItem.setSubmenu(timbraSubMenu);
			timbraMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return uri != null && !uri.equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				}
			});
			/**
			 * BARCODE SU A4
			 */
			MenuItem barcodeA4MenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeA4MenuItem(), "protocollazione/barcode.png");
			Menu barcodeA4SubMenu = new Menu();
			MenuItem barcodeA4NrDocumentoMenuItem = new MenuItem(getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeSegnatura(numeroProgrAllegato, listaTipiFileAllegato, "","");
				}
			});
			MenuItem barcodeA4NrDocumentoPoisizioneMenuItem = new MenuItem(getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeA4NrDocumentoPoisizioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeSegnatura(numeroProgrAllegato, listaTipiFileAllegato, "","P");
				}
			});
			MenuItem barcodeA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeA4DatiTipologiaMenuItem());
			barcodeA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {	
					clickBarcodeTipologia("T");
				}
			});
			barcodeA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					return  isAttivaTimbroTipologia() /*&& (uri != null && !uri.equals(""))*/
							&& getIdDocFilePrimario() != null && !"".equals(getIdDocFilePrimario());
				}
			});
			barcodeA4SubMenu.setItems(barcodeA4NrDocumentoMenuItem, barcodeA4NrDocumentoPoisizioneMenuItem, barcodeA4DatiTipologiaMenuItem);
			barcodeA4MenuItem.setSubmenu(barcodeA4SubMenu);
			/**
			 * BARCODE SU A4 MULTPIPLI
			 */
			MenuItem barcodeA4MultipliMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeA4MultipliMenuItem(), "blank.png");
			Menu barcodeMultipliA4SubMenu = new Menu();
			//Dati segnatura
			MenuItem barcodeMultipliA4NrDocumentoMenuItem = new MenuItem(getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeMultipliA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeMultipli(numeroProgrAllegato, idDoc, listaTipiFileAllegato, "","");
				}
			});
			MenuItem barcodeMultipliA4NrDocPosMenuItem = new MenuItem(getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeMultipliA4NrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeMultipli(numeroProgrAllegato, idDoc, listaTipiFileAllegato, "","P");
				}
			});
			MenuItem barcodeMultipliA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeMultipliA4DatiTipologiaMenuItem());
			barcodeMultipliA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeMultipli(numeroProgrAllegato, idDoc, listaTipiFileAllegato, "T","");
				}
			});
			barcodeMultipliA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					return  isAttivaTimbroTipologia() /*&& (uri != null && !uri.equals(""))*/
							&& getIdDocFilePrimario() != null && !"".equals(getIdDocFilePrimario());
				}
			});
			barcodeMultipliA4SubMenu.setItems(barcodeMultipliA4NrDocumentoMenuItem, barcodeMultipliA4NrDocPosMenuItem, barcodeMultipliA4DatiTipologiaMenuItem);
			barcodeA4MultipliMenuItem.setSubmenu(barcodeMultipliA4SubMenu);
			/**
			 * BARCODE SU ETICHETTA
			 */
			MenuItem barcodeEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeEtichettaMenuItem(), "protocollazione/stampaEtichetta.png");	
			Menu barcodeEtichettaSubMenu = new Menu();
			MenuItem barcodeEtichettaNrDocMenuItem = new MenuItem(getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {			
					clickBarcodeSegnatura(numeroProgrAllegato, listaTipiFileAllegato, "E","");
				}
			});
			MenuItem barcodeEtichettaNrDocPosMenuItem = new MenuItem(getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeSegnatura(numeroProgrAllegato, listaTipiFileAllegato, "E","P");
				}
			});
			MenuItem barcodeEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeEtichettaDatiTipologiaMenuItem());
			barcodeEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeTipologia("E");
				}
			});
			barcodeEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					return  isAttivaTimbroTipologia() /*&& (uri != null && !uri.equals(""))*/
							&& getIdDocFilePrimario() != null && !"".equals(getIdDocFilePrimario());
				}
			});
			barcodeEtichettaSubMenu.setItems(barcodeEtichettaNrDocMenuItem, barcodeEtichettaNrDocPosMenuItem,barcodeEtichettaDatiTipologiaMenuItem);
			barcodeEtichettaMenuItem.setSubmenu(barcodeEtichettaSubMenu);
			/**
			 * BARCODE SU ETICHETTA MULTIPLI
			 */
			MenuItem barcodeEtichettaMultiploMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeEtichettaMultiploMenuItem(), "blank.png");
			Menu barcodeMultipliEtichettaSubMenu = new Menu();
			
			MenuItem barcodeMultipliEtichettaNrDocMenuItem = new MenuItem(getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeMultipliEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {				
					clickBarcodeEtichettaMultipli(numeroProgrAllegato, idDoc, listaTipiFileAllegato, "","");
				}
			});
			MenuItem barcodeMultipliEtichettaNrDocPosMenuItem = new MenuItem(getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeMultipliEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeEtichettaMultipli(numeroProgrAllegato, idDoc, listaTipiFileAllegato, "","P");
				}
			});
			MenuItem barcodeMultipliEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeMultipliEtichettaDatiTipologiaMenuItem());
			barcodeMultipliEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					clickBarcodeEtichettaMultipli(numeroProgrAllegato, idDoc, listaTipiFileAllegato, "T", "");
				}
			});
			barcodeMultipliEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					return  isAttivaTimbroTipologia() /*&& (uri != null && !uri.equals(""))*/
							&& getIdDocFilePrimario() != null && !"".equals(getIdDocFilePrimario());
				}
			});
			barcodeMultipliEtichettaSubMenu.setItems(barcodeMultipliEtichettaNrDocMenuItem, barcodeMultipliEtichettaNrDocPosMenuItem,barcodeMultipliEtichettaDatiTipologiaMenuItem);
			barcodeEtichettaMultiploMenuItem.setSubmenu(barcodeMultipliEtichettaSubMenu);
			if (AurigaLayout.getParametroDBAsBoolean("SHOW_BARCODE_MENU")) {
				altreOpMenu.addItem(barcodeA4MenuItem);
				altreOpMenu.addItem(barcodeA4MultipliMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMenuItem);
				altreOpMenu.addItem(barcodeEtichettaMultiploMenuItem);
			}
		}
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenuTimbra) {
			altreOpMenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			if(timbraDatiSegnaturaMenuItem!=null) {
				timbraDatiSegnaturaMenuItem.setTitle("Timbra");
				altreOpMenu.addItem(timbraDatiSegnaturaMenuItem);
			}
		}
	}
	
	/**
	 * Voce Menù Timbra ( scelta Dati segnatura )
	 */
	public void clickTimbraDatiSegnatura(String idDoc, String nomeFile, String uri, String remote, InfoFileRecord infoFile, String idUdAppartenenza, String finalita) {
		InfoFileRecord precInfo = infoFile;
		Record detailRecord = getDetailRecord();		
		String idUd = null;
		if (detailRecord != null ) {
			idUd = detailRecord.getAttribute("idUd");
		} else {
			//se detailRecord è null vuol dire che l'allegatoItem è quello di DocumentiIstruttoria,
			//quindi l'idUd è l'idUdAppartenenza del canvas
			idUd = idUdAppartenenza;
		}
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
			record.setAttribute("idDoc", idDoc);
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
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, nomeFile, Boolean.valueOf(remote), 
					precInfo.getMimetype(), idUd, idDoc, rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	/**
	 * Voce Menù Timbra ( Dati tipologia )
	 */
	public void clickTimbraDatiTipologia() {
		//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
		String idDocAllegato = getIdDocFilePrimario();//mDynamicForm.getValueAsString("idDocAllegato");
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";				
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			Record record = new Record();
			if(isFromFolderPraticaPregressa()){				
				record.setAttribute("idFolder", getIdFolder());
			}else{
				record.setAttribute("idDoc", idDocAllegato);
			}
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			TimbroCopertinaUtil.buildDatiTipologia(record);
		}else{	
			Record copertinaTimbroRecord = new Record();
			if(isFromFolderPraticaPregressa()){				
				copertinaTimbroRecord.setAttribute("idFolder", getIdFolder());
			}else{
				copertinaTimbroRecord.setAttribute("idDoc", idDocAllegato);
			}
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
			copertinaTimbroWindow.show();
		}
	}
	
	/**
	 *  Voce di Menu: Barcode su A4 & Barcode su etichetta
	 *  Scelte: Nro documento & Nro documento + posizione
	 *  Viene gestito il tipo di barcode se A4 oppure su etichetta tramite il parametro @param: tipologia
	 *  Viene gestito il tipo di scelta se Nro documento oppure Nro documento + posizione tramite il parametro @param: posizionale
	 */
	public void clickBarcodeSegnatura(String numeroProgrAllegato, String listaTipiFileAllegato, String tipologia, String posizionale) {
		if(tipologia != null && !"".equals(tipologia) && "E".equals(tipologia)){
			//Barcode su etichetta
			clickBarcodeEtichettaDatiSegnatura(numeroProgrAllegato, listaTipiFileAllegato, posizionale);
		}else{
			Record detailRecord = getDetailRecord();
			String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
			String numeroAllegato = null;
			if(posizionale != null && "P".equals(posizionale)){
				numeroAllegato = numeroProgrAllegato;
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
				if(isFromFolderPraticaPregressa()){				
					record.setAttribute("barcodePraticaPregressa", "true");
					record.setAttribute("idFolder", getIdFolder());
					record.setAttribute("sezionePratica", listaTipiFileAllegato);
				}
				TimbroCopertinaUtil.buildDatiSegnatura(record);
			} else{
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("idUd", idUd);
				copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				copertinaTimbroRecord.setAttribute("provenienza", "A");
				copertinaTimbroRecord.setAttribute("posizionale", posizionale);
				if(isFromFolderPraticaPregressa()){				
					copertinaTimbroRecord.setAttribute("barcodePraticaPregressa", "true");
					copertinaTimbroRecord.setAttribute("idFolder", getIdFolder());
					copertinaTimbroRecord.setAttribute("sezionePratica", listaTipiFileAllegato);
				}
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);				
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"A","",posizionale);
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 * Barcode su etichetta: Nro documento oppure Nro documento + posizione 
	 */
	public void clickBarcodeEtichettaDatiSegnatura(String numeroProgrAllegato, String listaTipiFileAllegato, String posizione) {
		Record detailRecord = getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		final String nrAllegato = posizione != null && "P".equals(posizione) ? numeroProgrAllegato : null;
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", nrAllegato);
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		if(isFromFolderPraticaPregressa()){
			recordToPrint.setAttribute("barcodePraticaPregressa", "true");
			recordToPrint.setAttribute("idFolder", getIdFolder());
			recordToPrint.setAttribute("sezionePratica", listaTipiFileAllegato);
		}
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
	 * 
	 * Metodo relativo alla creazione dei Barcode su A4 oppure su etichetta con Tipologia
	 */
	public void clickBarcodeTipologia(String tipo){
		if(tipo != null && !"".equals(tipo) && "E".equals(tipo)){
			//Metodo relativo alla creazione dei Barcode su etichetta con Tipologia
			clickBarcodeEtichettaDatiTipologia();
		}else{
			//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
			String idDocAllegato = getIdDocFilePrimario();//mDynamicForm.getValueAsString("idDocAllegato");
			String numeroAllegato = "";
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				Record record = new Record();
				if(isFromFolderPraticaPregressa()){				
					record.setAttribute("idFolder", getIdFolder());
				}else{
					record.setAttribute("idDoc", idDocAllegato);
				}
				record.setAttribute("numeroAllegato", numeroAllegato);
				record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
				record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
				record.setAttribute("skipScelteOpzioniCopertina", "true");
				TimbroCopertinaUtil.buildDatiTipologia(record);
			}else{
				Record copertinaTimbroRecord = new Record();
				copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
				copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
				copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
				if(isFromFolderPraticaPregressa()){
					copertinaTimbroRecord.setAttribute("idFolder", getIdFolder());
				}else{
					copertinaTimbroRecord.setAttribute("idDoc", idDocAllegato);
				}
				CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
				CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,false,"","T","");
				copertinaTimbroWindow.show();
			}
		}
	}
	
	/**
	 * Barcode su etichetta - Dati tipologia
	 */
	public void clickBarcodeEtichettaDatiTipologia() {
		//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
		final String idDocAllegato = getIdDocFilePrimario();//mDynamicForm.getValueAsString("idDocAllegato");
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("numeroAllegato", "");
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		if(isFromFolderPraticaPregressa()){
			recordToPrint.setAttribute("idFolder", getIdFolder());
		}else{
			recordToPrint.setAttribute("idDoc", idDocAllegato);
		}
		/**
		 * Se non è presente la stampante per i barcode su etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
				
				@Override
				public void execute(String nomeStampante) {
					CopertinaEtichettaUtil.stampaEtichettaDatiTipologia(recordToPrint);
				}
			}, null);
		}
	}
	
	/**
	 * Barcode multipli su A4 - Dati segnatura & tipologia e Nro documento (@param: tipo )
	 * Per Nro documento oppure Nro documento + posizione (@param: posizionale)
	 */
	public void clickBarcodeMultipli(String numeroProgrAllegato, String idDocAllegato, String listaTipiFileAllegato, String tipo, String posizionale){
		Record detailRecord = getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";		
		String numeroAllegato = null;
		if(posizionale != null && "P".equals(posizionale)){
			numeroAllegato = numeroProgrAllegato;
		}	
		/**
		 * DATI TIPOLOGIA
		 */
		if(tipo != null && !"".equals(tipo) && "T".equals(tipo)){
			Record copertinaTimbroRecord = new Record();
			if(isFromFolderPraticaPregressa()){
				copertinaTimbroRecord.setAttribute("idFolder", getIdFolder());
				copertinaTimbroRecord.setAttribute("barcodePraticaPregressa", "true");
				copertinaTimbroRecord.setAttribute("sezionePratica", listaTipiFileAllegato);
			}else{
				copertinaTimbroRecord.setAttribute("idDoc", idDocAllegato);
			}
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "T");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"A","T","");
			copertinaTimbroWindow.show();
		}else{
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			copertinaTimbroRecord.setAttribute("provenienza", "A");
			copertinaTimbroRecord.setAttribute("posizionale", posizionale);
			if(isFromFolderPraticaPregressa()){	
				copertinaTimbroRecord.setAttribute("barcodePraticaPregressa", "true");
				copertinaTimbroRecord.setAttribute("idFolder", getIdFolder());
				copertinaTimbroRecord.setAttribute("sezionePratica", listaTipiFileAllegato);
			}else{
				copertinaTimbroRecord.setAttribute("idUd", idUd);
			}
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean,true,"A","",posizionale);
			copertinaTimbroWindow.show();
		}
	}
	
	/**
	 * Barcode multipli su etichetta con Dati segnatura oppure Dati tipologia ( @param: tipo )
	 * Per Nro documento oppure Nro documento + posizione ( @param: posizione )
	 */
	public void clickBarcodeEtichettaMultipli(String numeroProgrAllegato, String idDocAllegato, String listaTipiFileAllegato, String tipo, String posizione) {			
		Record detailRecord = getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;		
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		if(tipo != null && "T".equals(tipo)){
			record.setAttribute("tipologia", "T");
			if(isFromFolderPraticaPregressa()){
				record.setAttribute("idFolder", getIdFolder());
				record.setAttribute("barcodePraticaPregressa", "true");
				record.setAttribute("sezionePratica", listaTipiFileAllegato);
			}else{
				record.setAttribute("idDoc", idDocAllegato);
			}
			record.setAttribute("nrAllegato", numeroProgrAllegato);
		}else{
			if(isFromFolderPraticaPregressa()){
				record.setAttribute("idFolder", getIdFolder());
				record.setAttribute("barcodePraticaPregressa", "true");
				record.setAttribute("sezionePratica", listaTipiFileAllegato);
			}else{
				record.setAttribute("idUd", idUd);
			}
		}		
		if(posizione != null && "P".equals(posizione)){
			record.setAttribute("nrAllegato", numeroProgrAllegato);
		}
		record.setAttribute("posizione", posizione);		
		/**
		 * Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){			
			record.setAttribute("nomeStampante", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
			record.setAttribute("provenienza", "A");
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
	
	public void clickGeneraDaModello(final ListGridRecord listRecord, final boolean flgOmissis) {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", getTipoAllegato(listRecord));
		lGwtRestDataSource.addParam("idProcess", getIdProcess());
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello, String flgConvertiInPdf) {
							afterSelezioneModello(idModello, tipoModello, flgConvertiInPdf, listRecord, flgOmissis);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						afterSelezioneModello(idModello, data.get(0).getAttribute("tipoModello"), data.get(0).getAttribute("flgConvertiInPdf"), listRecord, flgOmissis);
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}
			}
		});
	}
	
	private void afterSelezioneModello(final String idModello, final String tipoModello, final String flgConvertiInPdf, final ListGridRecord listRecord, final boolean flgOmissis) {
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		String uri = listRecord.getAttribute(uriFileFieldName);
		if (isModelloModificabile(idModello) && uri != null && !uri.equals("")) {
			SC.ask("E' gia presente un file. Vuoi procedere alla generazione sovrascrivendo il file esistente?", new BooleanCallback() {
				@Override
				public void execute(Boolean value) {

					if (value != null && value) {
						caricaModello(idModello, tipoModello, flgConvertiInPdf, listRecord, flgOmissis);
					}
				}
			});
		} else {
			caricaModello(idModello, tipoModello, flgConvertiInPdf, listRecord, flgOmissis);
		}
	}

	public void caricaModello(String idModello, String tipoModello, String flgConvertiInPdf, final ListGridRecord listRecord, final boolean flgOmissis) {
		caricaModelloAllegato(idModello, tipoModello, flgConvertiInPdf, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageAfterCaricaModelloAllegato(object, listRecord, flgOmissis);
			}
		});
	}

	public void manageAfterCaricaModelloAllegato(Record recordFileDaModello, ListGridRecord listRecord, boolean flgOmissis) {
		
		final String nomeFileFieldName = flgOmissis ? "nomeFileOmissis" : "nomeFileAllegato"; 
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String remoteUriFieldName = flgOmissis ? "remoteUriOmissis" : "remoteUri";
		final String isChangedFieldName = flgOmissis ? "isChangedOmissis" : "isChanged";
		final String nomeFileTifFieldName = flgOmissis ? "nomeFileTifOmissis" : "nomeFileAllegatoTif";
		final String uriFileTifFieldName = flgOmissis ? "uriFileTifOmissis" : "uriFileAllegatoTif";
		final String nomeFileVerPreFirmaFieldName = flgOmissis ? "nomeFileVerPreFirmaOmissis" : "nomeFileVerPreFirma"; 
		final String uriFileVerPreFirmaFieldName = flgOmissis ? "uriFileVerPreFirmaOmissis" : "uriFileVerPreFirma";
		final String improntaVerPreFirmaFieldName = flgOmissis ? "improntaVerPreFirmaOmissis" : "improntaVerPreFirma"; 
		final String infoFileVerPreFirmaFieldName = flgOmissis ? "infoFileVerPreFirmaOmissis" : "infoFileVerPreFirma"; 
	
		grid.deselectAllRecords();				
		String nomeFileDaModello = recordFileDaModello.getAttribute("nomeFilePrimario");
		String uriFileDaModello = recordFileDaModello.getAttribute("uriFilePrimario");
		String infoFileDaModello = recordFileDaModello.getAttribute("infoFile");
		InfoFileRecord infoDaModello = InfoFileRecord.buildInfoFileString(infoFileDaModello);
		InfoFileRecord precInfo = listRecord.getAttribute(infoFileFieldName) != null ? new InfoFileRecord(listRecord.getAttributeAsRecord(infoFileFieldName)) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		final Record newListRecord = new Record(listRecord.getJsObj());
		newListRecord.setAttribute("changed", true);
		newListRecord.setAttribute("flgNoModificaDati", false);
		newListRecord.setAttribute(infoFileFieldName, infoDaModello);
		if (precImpronta == null || !precImpronta.equals(infoDaModello.getImpronta())) {
			newListRecord.setAttribute(isChangedFieldName, true);
			if(flgOmissis) {
				manageChangedFileOmissis(newListRecord);	
			} else {
				manageChangedFileAllegato(newListRecord);
			}
		}
		newListRecord.setAttribute(nomeFileFieldName, nomeFileDaModello != null ? nomeFileDaModello : "");
		newListRecord.setAttribute(uriFileFieldName, uriFileDaModello != null ? uriFileDaModello : "");
		newListRecord.setAttribute(nomeFileTifFieldName, "");
		newListRecord.setAttribute(uriFileTifFieldName, "");
		newListRecord.setAttribute(remoteUriFieldName, false);
		newListRecord.setAttribute(nomeFileVerPreFirmaFieldName, nomeFileDaModello != null ? nomeFileDaModello : "");
		newListRecord.setAttribute(uriFileVerPreFirmaFieldName, uriFileDaModello != null ? uriFileDaModello : "");
		newListRecord.setAttribute(improntaVerPreFirmaFieldName, infoDaModello.getImpronta());
		newListRecord.setAttribute(infoFileVerPreFirmaFieldName, infoDaModello);				
		updateDataAndRefresh(newListRecord, listRecord);	
		controlAfterUpload(infoDaModello, newListRecord, flgOmissis);
		if (isShowEditMenuItem(listRecord, flgOmissis)) {
			clickEditFile(listRecord, flgOmissis);
		} else {
			clickPreviewFile(listRecord, flgOmissis);
		}
	}
	
	public void clickPreviewEditFile(final Record listRecord, final boolean flgOmissis) {	
		
		final String idDocFieldName = flgOmissis ? "idDocOmissis" : "idDocAllegato"; 
		final String nomeFileFieldName = flgOmissis ? "nomeFileOmissis" : "nomeFileAllegato"; 
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String remoteUriFieldName = flgOmissis ? "remoteUriOmissis" : "remoteUri";
		final String isChangedFieldName = flgOmissis ? "isChangedOmissis" : "isChanged";
		final String nomeFileTifFieldName = flgOmissis ? "nomeFileTifOmissis" : "nomeFileAllegatoTif";
		final String uriFileTifFieldName = flgOmissis ? "uriFileTifOmissis" : "uriFileAllegatoTif";
		final String nomeFileVerPreFirmaFieldName = flgOmissis ? "nomeFileVerPreFirmaOmissis" : "nomeFileVerPreFirma"; 
		final String uriFileVerPreFirmaFieldName = flgOmissis ? "uriFileVerPreFirmaOmissis" : "uriFileVerPreFirma";
		final String improntaVerPreFirmaFieldName = flgOmissis ? "improntaVerPreFirmaOmissis" : "improntaVerPreFirma"; 
		final String infoFileVerPreFirmaFieldName = flgOmissis ? "infoFileVerPreFirmaOmissis" : "infoFileVerPreFirma"; 
		
		final Record detailRecord = getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		final String idDocAllegato = listRecord.getAttribute(idDocFieldName);
		addToRecent(idUd, idDocAllegato);
		final String display = listRecord.getAttribute(nomeFileFieldName);
		final String uri = listRecord.getAttribute(uriFileFieldName);
		final InfoFileRecord info = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject(infoFileFieldName));		
		final Boolean remoteUri = Boolean.valueOf(listRecord.getAttribute(remoteUriFieldName));		
		final ServiceCallback<Record> callback = new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				grid.deselectAllRecords();				
				String nomeFileNew = object.getAttribute("nomeFile");
				String uriNew = object.getAttribute("uri");
				String infoFileNew = object.getAttribute("infoFile");
				InfoFileRecord newInfo = InfoFileRecord.buildInfoFileString(infoFileNew);
				InfoFileRecord precInfo = listRecord.getAttribute(infoFileFieldName) != null ? new InfoFileRecord(listRecord.getAttributeAsRecord(infoFileFieldName)) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				final Record newListRecord = new Record(listRecord.getJsObj());
				newListRecord.setAttribute("changed", true);
				newListRecord.setAttribute("flgNoModificaDati", false);
				newListRecord.setAttribute(infoFileFieldName, newInfo);
				if (precImpronta == null || !precImpronta.equals(newInfo.getImpronta())) {
					newListRecord.setAttribute(isChangedFieldName, true);
					if(flgOmissis) {
						manageChangedFileOmissis(newListRecord);	
					} else {
						manageChangedFileAllegato(newListRecord);
					}
				}
				newListRecord.setAttribute(nomeFileFieldName, nomeFileNew != null ? nomeFileNew : "");
				newListRecord.setAttribute(uriFileFieldName, uriNew != null ? uriNew : "");
				newListRecord.setAttribute(nomeFileTifFieldName, "");
				newListRecord.setAttribute(uriFileTifFieldName, "");
				newListRecord.setAttribute(remoteUriFieldName, false);
				newListRecord.setAttribute(nomeFileVerPreFirmaFieldName, nomeFileNew != null ? nomeFileNew : "");
				newListRecord.setAttribute(uriFileVerPreFirmaFieldName, uriNew != null ? uriNew : "");
				newListRecord.setAttribute(improntaVerPreFirmaFieldName, newInfo.getImpronta());
				newListRecord.setAttribute(infoFileVerPreFirmaFieldName, newInfo);				
				updateDataAndRefresh(newListRecord, listRecord);	
				controlAfterUpload(newInfo, newListRecord, flgOmissis);
			}
		};		
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
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
						previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info, callback);
					}
				}
			});
		} else {
			previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info, callback);
		}
	}
	
	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info, final ServiceCallback<Record> callback) {		
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
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri, timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String nomeFilePdf, String uriPdf, String infoFilePdf) {
						if(callback != null) {
							Record recordCallback = new Record();
							recordCallback.setAttribute("nomeFile", nomeFilePdf);
							recordCallback.setAttribute("uri", uriPdf);
							recordCallback.setAttribute("infoFile", infoFilePdf);
							if(callback != null) {
								callback.execute(recordCallback);
							}
						}						
					}
				};
				previewDocWindow.show();
			}
		});
	}
	
	protected boolean isShowEditMenuItem(Record listRecord, boolean flgOmissis) {
				
		final String nomeFileVerPreFirmaFieldName = flgOmissis ? "nomeFileVerPreFirmaOmissis" : "nomeFileVerPreFirma"; 
		final String uriFileVerPreFirmaFieldName = flgOmissis ? "uriFileVerPreFirmaOmissis" : "uriFileVerPreFirma";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String infoFileVerPreFirmaFieldName = flgOmissis ? "infoFileVerPreFirmaOmissis" : "infoFileVerPreFirma"; 
		
		final String display = listRecord.getAttribute(nomeFileVerPreFirmaFieldName);
		final String uri = listRecord.getAttribute(uriFileVerPreFirmaFieldName);
		
		if (uri != null && !uri.equals("")) {
			String estensione = null;
			/**
			 * File corrente non firmato
			 */
			final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject(infoFileFieldName));		
			if (infoFile != null) {
				if (infoFile.getCorrectFileName() != null && !infoFile.getCorrectFileName().trim().equals("")) {
					estensione = infoFile.getCorrectFileName().substring(infoFile.getCorrectFileName().lastIndexOf(".") + 1);
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
			final InfoFileRecord infoFileVerPreFirma = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject(infoFileVerPreFirmaFieldName));		
			if(infoFileVerPreFirma != null) {
				if (infoFileVerPreFirma.getCorrectFileName() != null && !infoFileVerPreFirma.getCorrectFileName().trim().equals("")) {
					estensione = infoFileVerPreFirma.getCorrectFileName().substring(infoFileVerPreFirma.getCorrectFileName().lastIndexOf(".") + 1);
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
		}
		return false;
	}
	
	protected void clickEditFile(final Record listRecord, final boolean flgOmissis) {
		
		final String idDocFieldName = flgOmissis ? "idDocOmissis" : "idDocAllegato"; 
		final String nomeFileFieldName = flgOmissis ? "nomeFileOmissis" : "nomeFileAllegato"; 
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String remoteUriFieldName = flgOmissis ? "remoteUriOmissis" : "remoteUri";
		final String isChangedFieldName = flgOmissis ? "isChangedOmissis" : "isChanged";
		final String nomeFileTifFieldName = flgOmissis ? "nomeFileTifOmissis" : "nomeFileAllegatoTif";
		final String uriFileTifFieldName = flgOmissis ? "uriFileTifOmissis" : "uriFileAllegatoTif";
		final String nomeFileVerPreFirmaFieldName = flgOmissis ? "nomeFileVerPreFirmaOmissis" : "nomeFileVerPreFirma"; 
		final String uriFileVerPreFirmaFieldName = flgOmissis ? "uriFileVerPreFirmaOmissis" : "uriFileVerPreFirma";
		final String improntaVerPreFirmaFieldName = flgOmissis ? "improntaVerPreFirmaOmissis" : "improntaVerPreFirma"; 
		final String infoFileVerPreFirmaFieldName = flgOmissis ? "infoFileVerPreFirmaOmissis" : "infoFileVerPreFirma"; 
		
		final Record detailRecord = getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		final String idDocAllegato = listRecord.getAttribute(idDocFieldName);
		addToRecent(idUd, idDocAllegato);
		final String display = listRecord.getAttribute(nomeFileVerPreFirmaFieldName);
		final String uri = listRecord.getAttribute(uriFileVerPreFirmaFieldName);
		final String impronta = listRecord.getAttribute(improntaVerPreFirmaFieldName);
		final InfoFileRecord info = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject(infoFileFieldName));		
		final Boolean remoteUri = Boolean.valueOf(listRecord.getAttribute(remoteUriFieldName));		
		final ServiceCallback<Record> callback = new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				grid.deselectAllRecords();								
				String nomeFileNew = object.getAttribute("nomeFile");
				String uriNew = object.getAttribute("uri");
				String infoFileNew = object.getAttribute("infoFile");
				InfoFileRecord newInfo = InfoFileRecord.buildInfoFileString(infoFileNew);
				InfoFileRecord precInfo = listRecord.getAttribute(infoFileFieldName) != null ? new InfoFileRecord(listRecord.getAttributeAsRecord(infoFileFieldName)) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;				
				final Record newListRecord = new Record(listRecord.getJsObj());
				newListRecord.setAttribute("changed", true);
				newListRecord.setAttribute("flgNoModificaDati", false);
				newListRecord.setAttribute(infoFileFieldName, newInfo);
				if (precImpronta == null || !precImpronta.equals(newInfo.getImpronta())) {
					newListRecord.setAttribute(isChangedFieldName, true);
					if(flgOmissis) {
						manageChangedFileOmissis(newListRecord);	
					} else {
						manageChangedFileAllegato(newListRecord);
					}
				}
				newListRecord.setAttribute(nomeFileFieldName, nomeFileNew != null ? nomeFileNew : "");
				newListRecord.setAttribute(uriFileFieldName, uriNew != null ? uriNew : "");
				newListRecord.setAttribute(nomeFileTifFieldName, "");
				newListRecord.setAttribute(uriFileTifFieldName, "");
				newListRecord.setAttribute(remoteUriFieldName, false);
				newListRecord.setAttribute(nomeFileVerPreFirmaFieldName, nomeFileNew != null ? nomeFileNew : "");
				newListRecord.setAttribute(uriFileVerPreFirmaFieldName, uriNew != null ? uriNew : "");
				newListRecord.setAttribute(improntaVerPreFirmaFieldName, newInfo.getImpronta());
				newListRecord.setAttribute(infoFileVerPreFirmaFieldName, newInfo);				
				updateDataAndRefresh(newListRecord, listRecord);	
				controlAfterUpload(newInfo, newListRecord, flgOmissis);
			}
		};
		if (info.isFirmato()) {
			SC.ask("Modificando il file perderai la/le firme già apposte. Vuoi comunque procedere?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value){
						final InfoFileRecord infoVerPreFirma = InfoFileRecord.buildInfoFileRecord(listRecord.getAttributeAsObject(infoFileVerPreFirmaFieldName));		
						editFile(infoVerPreFirma, display, uri, remoteUri, impronta, callback);
					}
				}
			});
		} else {
			editFile(info, display, uri, remoteUri, impronta, callback);
		}
	}
	
	private void editFile(InfoFileRecord info, String display, String uri, Boolean remoteUri, String impronta, final ServiceCallback<Record> callback) {
		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		}
		if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		if (impronta == null || "".equals(impronta)) {
			impronta = info.getImpronta();
		}
		if (estensione.equalsIgnoreCase("p7m")) {
			Record lRecordDaSbustare = new Record();
			lRecordDaSbustare.setAttribute("displayFilename", display);
			lRecordDaSbustare.setAttribute("uri", uri);
			lRecordDaSbustare.setAttribute("remoteUri", remoteUri);
			new GWTRestService<Record, Record>("SbustaFileService").call(lRecordDaSbustare, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordSbustato) {
					String displaySbustato = lRecordSbustato.getAttribute("displayFilename");
					String uriSbustato = lRecordSbustato.getAttribute("uri");
					String estensioneSbustato = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
					String improntaSbustato = lRecordSbustato.getAttribute("impronta");
					openEditFileWindow(displaySbustato, uriSbustato, false, estensioneSbustato, improntaSbustato, callback);
				}
			});
		} else {
			openEditFileWindow(display, uri, remoteUri, estensione, impronta, callback);
		}
	}

	public void openEditFileWindow(String display, String uri, Boolean remoteUri, String estensione, String impronta, final ServiceCallback<Record> callback) {
		OpenEditorUtility.openEditor(display, uri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFile, String uri, String infoFile) {
				if(callback != null) {
					Record recordCallback = new Record();
					recordCallback.setAttribute("nomeFile", nomeFile);
					recordCallback.setAttribute("uri", uri);
					recordCallback.setAttribute("infoFile", infoFile);
					if(callback != null) {
						callback.execute(recordCallback);
					}
				}
			}
		});
	}
	
	// Cancellazione del file
	protected void clickEliminaFile(Record listRecord) {
		grid.deselectAllRecords();										
		final Record listNewRecord = new Record(listRecord.getJsObj());
		listNewRecord.setAttribute("changed", true);
		listNewRecord.setAttribute("flgNoModificaDati", false);
		listNewRecord.setAttribute("nomeFileAllegato", "");
		listNewRecord.setAttribute("uriFileAllegato", "");
		listNewRecord.setAttribute("nomeFileAllegatoTif", "");
		listNewRecord.setAttribute("uriFileAllegatoTif", "");
		listNewRecord.setAttribute("nomeFileVerPreFirma", "");
		listNewRecord.setAttribute("uriFileVerPreFirma", "");
		listNewRecord.setAttribute("improntaVerPreFirma", "");
		listNewRecord.setAttribute("infoFileVerPreFirma", (Record) null);	
		listNewRecord.setAttribute("infoFile", (Record) null);	
		listNewRecord.setAttribute("isUdSenzaFileImportata", false);
		updateDataAndRefresh(listNewRecord, listRecord);	
	}
	
	protected void clickEliminaFileOmissis(Record listRecord) {
		grid.deselectAllRecords();										
		final Record listNewRecord = new Record(listRecord.getJsObj());
		listNewRecord.setAttribute("changed", true);
		listNewRecord.setAttribute("flgNoModificaDati", false);
		listNewRecord.setAttribute("nomeFileOmissis", "");
		listNewRecord.setAttribute("uriFileOmissis", "");
		listNewRecord.setAttribute("nomeFileTifOmissis", "");
		listNewRecord.setAttribute("uriFileTifOmissis", "");
		listNewRecord.setAttribute("nomeFileVerPreFirmaOmissis", "");
		listNewRecord.setAttribute("uriFileVerPreFirmaOmissis", "");
		listNewRecord.setAttribute("improntaVerPreFirmaOmissis", "");
		listNewRecord.setAttribute("infoFileVerPreFirmaOmissis", (Record) null);	
		listNewRecord.setAttribute("infoFileOmissis", (Record) null);
		listNewRecord.setAttribute("flgDatiSensibili", false);	
		updateDataAndRefresh(listNewRecord, listRecord);
	}
	
	public void controlAllAfterUploadMultiplo(RecordList listaRecord) {
		final List<MessageBean> listaWarningMessages = new ArrayList<MessageBean>();
		for(int i = 0; i < listaRecord.getLength(); i++) {
			Record record = listaRecord.get(i);
			String uri = record.getAttribute("uriFileAllegato");
			if(uri != null && !"".equals(uri)) {				
				String displayName = record.getAttribute("nomeFileAllegato");
				InfoFileRecord info = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
				manageControlAfterUpload(info, record, displayName, listaWarningMessages, false);
			}
		}		
		updateGridItemValue();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				Layout.hideWaitPopup();
				if(listaWarningMessages != null && listaWarningMessages.size() > 0) {	
					if(listaWarningMessages.size() == 1) {
						GWTRestDataSource.printMessage(listaWarningMessages.get(0));
					} else {
						RecordList listaWarnings = new RecordList();
						for(int i = 0; i < listaWarningMessages.size(); i++) {
							Record warningRecord = new Record();
							warningRecord.setAttribute("warning", listaWarningMessages.get(i).getMessage());
							listaWarnings.add(warningRecord);
						}
						ListaWarningsPopup listaWarningsPopup = new ListaWarningsPopup("Avvertimenti", "Il caricamento dei file è stato eseguito con alcuni warning. Di seguito il dettaglio: ", listaWarnings, 600, 300);
						listaWarningsPopup.show();
					}
				}
			}
		});		
	}
	
	public void controlAfterUpload(InfoFileRecord info, Record record, boolean flgOmissis) {
		manageControlAfterUpload(info, record, null, null, flgOmissis);
		updateGridItemValue();
	}
	
	public void controlAfterUpload(InfoFileRecord info, Record record, String displayName, boolean flgOmissis) {		
		manageControlAfterUpload(info, record, displayName, null, flgOmissis);
		updateGridItemValue();
	}	
	
	
	private void manageControlAfterUpload(InfoFileRecord info, Record record, String displayName, List<MessageBean> listaWarningMessages, boolean flgOmissis) {		
		boolean flgParteDispositivo = record.getAttribute("flgParteDispositivo") != null && record.getAttributeAsBoolean("flgParteDispositivo");
		boolean flgNoPubblAllegato = record.getAttribute("flgNoPubblAllegato") != null	&& record.getAttributeAsBoolean("flgNoPubblAllegato");
		boolean flgDatiSensibili = record.getAttribute("flgDatiSensibili") != null	&& record.getAttributeAsBoolean("flgDatiSensibili");
//		boolean flgPubblicaSeparato = false;
//		if(getShowFlgPubblicaSeparato()) {
//			if(getFlgPubblicazioneAllegatiUguale()) {
//				flgPubblicaSeparato = isFlgPubblicaAllegatiSeparati();
//			} else {
//				flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato") != null && record.getAttributeAsBoolean("flgPubblicaSeparato");
//			}
//		}
		
		if (isProtInModalitaWizard() && isCanaleSupportoCartaceo() &&
				(info == null || info.getMimetype() == null || (!info.getMimetype().equals("application/pdf") && !info.getMimetype().startsWith("image")))) {
			MessageBean message = null;					
			if(displayName != null && !"".equals(displayName)) {
				message = new MessageBean("Il file " + displayName + " non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale", "", MessageType.WARNING);				
			} else {
				message = new MessageBean("Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale", "", MessageType.WARNING);
			}	
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
			/*
			 * Visto l'errore dovuto al fatto che il file non è nel formato richiesto
			 * rimuovo le informazioni associate al file primario di cui si è tentato l'inserimento.
			 * In questo modo all'aggiornamento della grafica non viene riempita la text e non vengono
			 * abilitati i pulsanti di firma, etc. (normalmente abilitati nel caso di file in 
			 * formato corretto) 
			 */
//			if(flgOmissis) { 
//				clickEliminaFileOmissis(record);
//			} else {
//				clickEliminaFile(record);
//			}		
		}	
		
		if (!isFormatoAmmesso(info)) {
			MessageBean message = null;
			if(displayName != null && !"".equals(displayName)) {
				message = new MessageBean("Il file " + displayName + " risulta in un formato non ammesso", "", MessageType.WARNING);	
			} else {
				message = new MessageBean("Il file risulta in un formato non ammesso", "", MessageType.WARNING);
			}
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
		} else if(flgParteDispositivo && !isFormatoAmmessoParteIntegrante(info)) {
			MessageBean message = null;
			if(displayName != null && !"".equals(displayName)) {
				message = new MessageBean("Il file " + displayName + " risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING);	
			} else {
				message = new MessageBean("Il file risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING);
			}
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
//			if(flgOmissis) { 
//				clickEliminaFileOmissis(record);
//			} else {
//				clickEliminaFile(record);
//			}
		}
		if(flgParteDispositivo && (!flgDatiSensibili || flgOmissis) && !PreviewWindow.isPdfConvertibile(info) /*&& !flgPubblicaSeparato*/) {
			if(getShowFlgPubblicaSeparato()) {
				if(getFlgPubblicazioneAllegatiUguale()) {
					setFlgPubblicaAllegatiSeparati(true);
				} else {
					record.setAttribute("flgPubblicaSeparato", true);
				}
			}
			MessageBean message = null;
			if(displayName != null && !"".equals(displayName)) {
				message = new MessageBean("File " + displayName + " non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING);
			} else {
				message = new MessageBean("File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING);
			}
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
		}
		if (info.isFirmato()) {
			String rifiutoAllegatiConFirmeNonValide = getRifiutoAllegatiConFirmeNonValide();
			if(!info.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
				if("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
					flgParteDispositivo = false;
					record.setAttribute("flgParteDispositivo", false);	
					flgNoPubblAllegato = true;
					record.setAttribute("flgNoPubblAllegato", true);	
//					flgPubblicaSeparato = false;
					record.setAttribute("flgPubblicaSeparato", false);	
					flgDatiSensibili = false;
					record.setAttribute("flgDatiSensibili", false);
					MessageBean message = null;
					if(displayName != null && !"".equals(displayName)) {
						message = new MessageBean("Il file " + displayName + " presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante: è stato automaticamente inserito come allegato NON parte integrante", "", MessageType.WARNING);	
					} else {
						message = new MessageBean("Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante: è stato automaticamente inserito come allegato NON parte integrante", "", MessageType.WARNING);
					} 
					if(listaWarningMessages != null) listaWarningMessages.add(message); 
					else GWTRestDataSource.printMessage(message);
				} else {
					MessageBean message = null;
					if(displayName != null && !"".equals(displayName)) {
						message = new MessageBean("Il file " + displayName + " presenta firme non valide alla data odierna", "", MessageType.WARNING);	
					} else {
						message = new MessageBean("Il file presenta firme non valide alla data odierna", "", MessageType.WARNING);
					}	
					if(listaWarningMessages != null) listaWarningMessages.add(message); 
					else GWTRestDataSource.printMessage(message);
				}
			} else if(isDisattivaUnioneAllegatiFirmati()) {
				if(flgParteDispositivo && (!flgDatiSensibili || flgOmissis) && !flgNoPubblAllegato) {
					if(getShowFlgPubblicaSeparato()) {
						if(getFlgPubblicazioneAllegatiUguale()) {
							setFlgPubblicaAllegatiSeparati(true);
						} else {
							record.setAttribute("flgPubblicaSeparato", true);
						}
					}
//					MessageBean message = null;					
//					if(displayName != null && !"".equals(displayName)) {
//						message = new MessageBean("Il file " + displayName + " è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING);					
//					} else {
//						message = new MessageBean("Il file è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING);
//					}
//					if(listaWarningMessages != null) listaWarningMessages.add(message); 
//					else GWTRestDataSource.printMessage(message);
				} 
			}
		}
		if(flgParteDispositivo && (!flgDatiSensibili || flgOmissis) && isPubblicazioneSeparataPdfProtetti() && info.isPdfProtetto() /*&& !flgPubblicaSeparato*/) {
			if(getShowFlgPubblicaSeparato()) {
				if(getFlgPubblicazioneAllegatiUguale()) {
					setFlgPubblicaAllegatiSeparati(true);
				} else {
					record.setAttribute("flgPubblicaSeparato", true);
				}
			}
			MessageBean message = null;
			if(displayName != null && !"".equals(displayName)) {
				message = new MessageBean("File " + displayName + " protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING);
			} else {
				message = new MessageBean("File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING);
			}
			if(listaWarningMessages != null) listaWarningMessages.add(message); 
			else GWTRestDataSource.printMessage(message);
		}
		final float MEGABYTE = 1024L * 1024L;
		long dimAlertAllegato = getDimAlertAllegato(); // questo è in bytes
		long dimMaxAllegatoXPubblInMB = getDimMaxAllegatoXPubbl(); // questa è in MB
		if(dimMaxAllegatoXPubblInMB > 0 && info != null && info.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE)) {						
			if(flgParteDispositivo && (!flgDatiSensibili || flgOmissis)) {
				if(getShowFlgNoPubblAllegato()) {
					record.setAttribute("flgNoPubblAllegato", true);														
				}
				if(getShowFlgPubblicaSeparato()) {
					if(getFlgPubblicazioneAllegatiUguale()) {
						setFlgPubblicaAllegatiSeparati(true);
					} else {
						record.setAttribute("flgPubblicaSeparato", true);														
					}
				}											
				MessageBean message = null;
				if(displayName != null && !"".equals(displayName)) {
					message = new MessageBean("La dimensione del file " + displayName + " è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING);					
				} else {
					message = new MessageBean("La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING);	
				}				
				if(listaWarningMessages != null) listaWarningMessages.add(message); 
				else GWTRestDataSource.printMessage(message);
			}	
		} else if(dimAlertAllegato > 0 && info != null && info.getBytes() > dimAlertAllegato) {
			float dimensioneInMB = info.getBytes() / MEGABYTE;						
			if(flgParteDispositivo && (!flgDatiSensibili || flgOmissis) && !flgNoPubblAllegato) {
				if(getShowFlgPubblicaSeparato()) {
					if(getFlgPubblicazioneAllegatiUguale()) {
						setFlgPubblicaAllegatiSeparati(true);
					} else {
						record.setAttribute("flgPubblicaSeparato", true);														
					}
				}											
				MessageBean message = null;
				if(displayName != null && !"".equals(displayName)) {
					message = new MessageBean("Attenzione: la dimensione del file " + displayName + " è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING);					
				} else {
					message = new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING);	
				}				
				if(listaWarningMessages != null) listaWarningMessages.add(message); 
				else GWTRestDataSource.printMessage(message);
			} else {										
				MessageBean message = null;
				if(displayName != null && !"".equals(displayName)) {
					message = new MessageBean("Attenzione: la dimensione del file " + displayName + " è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING);	
				} else {
					message = new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING);	
				}
				if(listaWarningMessages != null) listaWarningMessages.add(message); 
				else GWTRestDataSource.printMessage(message);
			}								
		}
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
	
	public class AllegatiListGridField extends ListGridField {
		
		public AllegatiListGridField(String name) {
	    	super(name);
	    	init();
	    }
	   
	    public AllegatiListGridField(String name, String title) {
	    	super(name, title);
	    	init();
	    }

	    private void init() {
	    	if(getTitle() != null && !"".equals(getTitle())) {
				setCanHide(true);
			} else {
				setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
				setCanHide(false);
			}
	    	if(getShowFlgParteDispositivo() && getSortByFlgParteDispositivo()) {
	    		setCanSort(false);
	    	}
			setCanReorder(false);
			setCanFreeze(false);		
			setCanDragResize(true);		
			setShowDefaultContextMenu(false);
	    }
	}
	
	@Override
	public void updateDataAndRefresh(final Record record, Record oldRecord) {
		updateDataAndRefresh(record, oldRecord, false);
	}
	
	public void updateDataAndRefresh(final Record record, Record oldRecord, boolean skipRefreshNroAllegato) {
		if(getShowFlgPubblicaSeparato() && getFlgPubblicazioneAllegatiUguale() && record.getAttributeAsBoolean("flgPubblicaAllegatiSeparatiInAllegatiGrid")) {
			setFlgPubblicaAllegatiSeparati(true);
		}
		updateData(record, oldRecord);
		grid.refreshRow(grid.getRecordIndex(oldRecord));
//		grid.refreshRecordComponent(grid.getRecordIndex(oldRecord));
//		grid.invalidateRecordComponents();
		if(!skipRefreshNroAllegato) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
				@Override
				public void execute() {
					grid.selectSingleRecord(record);
					refreshNroAllegato();
				}
			});		
		}
	}
	
	public void addDataAndRefresh(final Record record) {
		addDataAndRefresh(record, false);
	}
	
	public void addDataAndRefresh(final Record record, boolean skipRefreshNroAllegato) {
		if(getShowFlgPubblicaSeparato() && getFlgPubblicazioneAllegatiUguale() && record.getAttributeAsBoolean("flgPubblicaAllegatiSeparatiInAllegatiGrid")) {
			setFlgPubblicaAllegatiSeparati(true);
		}
		addData(record);	
		grid.refreshRow(grid.getRecordIndex(record));
		indexLastAdd = grid.getRecordIndex(record);
//		grid.refreshRecordComponent(grid.getRecordIndex(record));
//		grid.invalidateRecordComponents(); 
		if(!skipRefreshNroAllegato) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	    		
				@Override
				public void execute() {
					grid.selectSingleRecord(record);
					refreshNroAllegato();
				}
	    	});	
		}
	}
	
	public void addAllDataAndRefresh(final RecordList listaRecord) {
		addAllDataAndRefresh(listaRecord, false);
	}
	
	public void addAllDataAndRefresh(final RecordList listaRecord, boolean skipRefreshNroAllegato) {
		for(int i = 0; i < listaRecord.getLength(); i++) {
			Record record = listaRecord.get(i);
			if(getShowFlgPubblicaSeparato() && getFlgPubblicazioneAllegatiUguale() && record.getAttributeAsBoolean("flgPubblicaAllegatiSeparatiInAllegatiGrid")) {
				setFlgPubblicaAllegatiSeparati(true);
			}
			addData(record);
			grid.refreshRow(grid.getRecordIndex(record));
			indexLastAdd = grid.getRecordIndex(record);
		}		
//		grid.refreshRecordComponent(grid.getRecordIndex(record));
//		grid.invalidateRecordComponents(); 
		if(!skipRefreshNroAllegato) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	    		
				@Override
				public void execute() {
					refreshNroAllegato();
				}
	    	});	
		}
	}
	
	public void mostraVariazione(int nroAllegato, String datoAnnullato) {
		for (ListGridRecord record : grid.getRecords()) {
			if (Integer.parseInt(record.getAttribute("numeroProgrAllegato")) == nroAllegato) {
				record.setAttribute("datoAnnullato", datoAnnullato);
				grid.refreshRow(grid.getRecordIndex(record));
			}
		}
	}
	
	public void preimpostaApponiTimbroFromEmail() {
		grid.deselectAllRecords();
		for (ListGridRecord record : grid.getRecords()) {
			if(isAttivaTimbraturaFilePostRegAllegato() && getShowFlgTimbraFilePostReg(record)) {
				InfoFileRecord lInfoFileRecord = record.getAttributeAsObject("infoFile") != null ? new InfoFileRecord(record.getAttributeAsObject("infoFile")) : null;
				if (lInfoFileRecord != null && canBeTimbrato(lInfoFileRecord) && AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					record.setAttribute("flgTimbraFilePostReg", true);
				} else {
					record.setAttribute("flgTimbraFilePostReg", false);
				}				
			}
		}
		updateGridItemValue();
	}
	
	public void preimpostaApponiTimbroXProtBozza() {
		grid.deselectAllRecords();
		for (ListGridRecord record : grid.getRecords()) {
			if(isAttivaTimbraturaFilePostRegAllegato() && getShowFlgTimbraFilePostReg(record)) {
				InfoFileRecord lInfoFileRecord = record.getAttributeAsObject("infoFile") != null ? new InfoFileRecord(record.getAttributeAsObject("infoFile")) : null;
				if (lInfoFileRecord != null && canBeTimbrato(lInfoFileRecord) && AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					record.setAttribute("flgTimbraFilePostReg", true);
				} else {
					record.setAttribute("flgTimbraFilePostReg", false);
				}				
			}
		}
		updateGridItemValue();
	}
	
	public void manageChangedFileAllegato(final Record listRecord) {
		manageChangedFileAllegato(listRecord, false);
	}
	
	public void manageChangedFileAllegato(final Record listRecord, boolean skipRefreshNroAllegato) {
		boolean isNewAllegato = listRecord.getAttribute("id") != null && listRecord.getAttribute("id").startsWith("NEW_");
		manageChangedFileAllegati();
		manageChangedFileAllegatoParteDispositivo(listRecord, true);
//		if (getShowFlgSostituisciVerPrec()) {
//			Record recordOrig = listRecord.getAttributeAsMap("valuesOrig") != null ? new Record(listRecord.getAttributeAsMap("valuesOrig")) : null;
//			InfoFileRecord precInfo = recordOrig != null && recordOrig.getAttributeAsObject("infoFile") != null
//					? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFile")) : null;
//			String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
//			InfoFileRecord info = listRecord.getAttributeAsObject("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsObject("infoFile")) : null;
//			String impronta = info != null ? info.getImpronta() : null;
//			if (precImpronta != null && impronta != null && !impronta.equals(precImpronta)) {
//				showFlgSostituisciVerPrec();
//			} else {
//				hideFlgSostituisciVerPrec();
//			}		
//		}
		if(isAttivaTimbraturaFilePostRegAllegato() && getShowFlgTimbraFilePostReg(listRecord)) {
			boolean flgTimbraFilePostReg;
			InfoFileRecord lInfoFileRecord = listRecord.getAttributeAsObject("infoFile") != null ? new InfoFileRecord(listRecord.getAttributeAsObject("infoFile")) : null;
			if (canBeTimbrato(lInfoFileRecord) && !lInfoFileRecord.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro") && (listRecord.getAttribute("numFileCaricatiInUploadMultiplo")!=null && listRecord.getAttributeAsInt("numFileCaricatiInUploadMultiplo")==1 || listRecord.getAttribute("numFileCaricatiInUploadMultiplo")==null)) {
						if (!listRecord.getAttributeAsBoolean("flgTimbraFilePostReg")) {
							showOpzioniTimbratura(listRecord, false);
						} else {
							SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
									new BooleanCallback() {

										@Override
										public void execute(Boolean value) {
											if (value) {
												showOpzioniTimbratura(listRecord, false);
											}
										}
									});
						}
					}
					flgTimbraFilePostReg = true;
				} else {
					flgTimbraFilePostReg = false;
				} 
			} else {
				flgTimbraFilePostReg = false;
			}			
			// se flgTimbraFilePostReg è false ed è un nuovo allegato non serve settarlo sul record, perchè di default è già null
			// allo stesso modo anche numFileCaricatiInUploadMultiplo non serve settarlo se è un nuovo allegato
			if(!isNewAllegato || flgTimbraFilePostReg) {
				grid.deselectAllRecords();
				final Record listNewRecord = new Record(listRecord.getJsObj());
				listNewRecord.setAttribute("flgTimbraFilePostReg", flgTimbraFilePostReg);
				listNewRecord.setAttribute("numFileCaricatiInUploadMultiplo", "1");				
				updateDataAndRefresh(listNewRecord, listRecord, skipRefreshNroAllegato);
			}
		} 
		// numFileCaricatiInUploadMultiplo non serve settarlo se è un nuovo allegato
		else if(!isNewAllegato) {
			grid.deselectAllRecords();
			final Record listNewRecord = new Record(listRecord.getJsObj());
			listNewRecord.setAttribute("numFileCaricatiInUploadMultiplo", "1");				
			updateDataAndRefresh(listNewRecord, listRecord, skipRefreshNroAllegato);
		}
	}
	
	public void manageChangedFileOmissis(final Record listRecord) {
		boolean isNewAllegato = listRecord.getAttribute("id") != null && listRecord.getAttribute("id").startsWith("NEW_");
		manageChangedFileAllegati();
		manageChangedFileAllegatoParteDispositivo(listRecord, true);
//		if (getShowFlgSostituisciVerPrec()) {
//			Record recordOrig = listRecord.getAttributeAsMap("valuesOrig") != null ? new Record(listRecord.getAttributeAsMap("valuesOrig")) : null;
//			InfoFileRecord precInfoOmissis = recordOrig != null && recordOrig.getAttributeAsObject("infoFileOmissis") != null
//					? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFileOmissis")) : null;
//			String precImprontaOmissis = precInfoOmissis != null ? precInfoOmissis.getImpronta() : null;
//			InfoFileRecord infoOmissis = listRecord.getAttributeAsObject("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsObject("infoFileOmissis")) : null;
//			String improntaOmissis = infoOmissis != null ? infoOmissis.getImpronta() : null;
//			if (precImprontaOmissis != null && improntaOmissis != null && !improntaOmissis.equals(precImprontaOmissis)) {
//				showFlgSostituisciVerPrecOmissis();
//			} else {
//				hideFlgSostituisciVerPrecOmissis();
//			}					
//		}
		if (isAttivaTimbraturaFilePostRegAllegato() && getShowFlgTimbraFilePostRegOmissis(listRecord)) {
			boolean flgTimbraFilePostRegOmissis;
			InfoFileRecord lInfoFileRecordOmissis = listRecord.getAttributeAsObject("infoFileOmissis") != null ? new InfoFileRecord(listRecord.getAttributeAsObject("infoFileOmissis")) : null;
			// Verifico se il File è timbrabile
			if (canBeTimbrato(lInfoFileRecordOmissis) && !lInfoFileRecordOmissis.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro") && (listRecord.getAttribute("numFileCaricatiInUploadMultiplo")!=null && listRecord.getAttributeAsInt("numFileCaricatiInUploadMultiplo")==1 || listRecord.getAttribute("numFileCaricatiInUploadMultiplo")==null)) {
						final Record listNewRecord = new Record(listRecord.getJsObj());
						if (!listNewRecord.getAttributeAsBoolean("flgTimbraFilePostRegOmissis")) {
							showOpzioniTimbratura(listRecord, false);
						} else {
							SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
									new BooleanCallback() {

										@Override
										public void execute(Boolean value) {
											if (value) {
												showOpzioniTimbratura(listRecord, false);
											}
										}
									});
						}
					}
					flgTimbraFilePostRegOmissis = true;
				} else {
					flgTimbraFilePostRegOmissis = false;
				}
			} else {
				flgTimbraFilePostRegOmissis = false;
			}
			// se flgTimbraFilePostRegOmissis è false ed è un nuovo allegato non serve settarlo sul record, perchè di default è già null
			// allo stesso modo anche numFileCaricatiInUploadMultiplo non serve settarlo se è un nuovo allegato
			if(!isNewAllegato || flgTimbraFilePostRegOmissis) {
				grid.deselectAllRecords();
				final Record listNewRecord = new Record(listRecord.getJsObj());
				listNewRecord.setAttribute("flgTimbraFilePostRegOmissis", flgTimbraFilePostRegOmissis);
				listNewRecord.setAttribute("numFileCaricatiInUploadMultiplo", "1");
				updateDataAndRefresh(listNewRecord, listRecord);
			}
		} 
		// numFileCaricatiInUploadMultiplo non serve settarlo se è un nuovo allegato
		else if(!isNewAllegato) {
			grid.deselectAllRecords();
			final Record listNewRecord = new Record(listRecord.getJsObj());
			listNewRecord.setAttribute("numFileCaricatiInUploadMultiplo", "1");		
			updateDataAndRefresh(listNewRecord, listRecord);
		}
	}
	
	public void manageChangedFileAllegatoParteDispositivo(Record listRecord, boolean isChanged) {
		if (getShowFlgParteDispositivo()) {
			boolean flgParteDispositivo = listRecord.getAttributeAsBoolean("flgParteDispositivo");
			boolean flgParteDispositivoSalvato = listRecord.getAttributeAsBoolean("flgParteDispositivoSalvato");
			if (flgParteDispositivo && (isChanged || !flgParteDispositivoSalvato)) {
				manageChangedFileAllegatiParteDispositivo();
			}
		}
	}
	
	public void showOpzioniTimbratura(final Record listRecord, boolean flgOmissis) {

		final String nomeFileFieldName = flgOmissis ? "nomeFileOmissis" : "nomeFileAllegato"; 
		final String uriFileFieldName = flgOmissis ? "uriFileOmissis" : "uriFileAllegato";
		final String infoFileFieldName = flgOmissis ? "infoFileOmissis" : "infoFile";
		final String remoteUriFieldName = flgOmissis ? "remoteUriOmissis" : "remoteUri";
		final String opzioniTimbroFieldName = flgOmissis ? "opzioniTimbroOmissis" : "opzioniTimbro";
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record record = new Record();
		record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		record.setAttribute("tipoPaginaPref", tipoPaginaPref);
		record.setAttribute("nomeFile", listRecord.getAttribute(nomeFileFieldName));
		record.setAttribute("uriFile", listRecord.getAttribute(uriFileFieldName));
		record.setAttribute("remote", listRecord.getAttribute(remoteUriFieldName));
		InfoFileRecord infoFile = listRecord.getAttribute(infoFileFieldName) != null ? new InfoFileRecord(listRecord.getAttributeAsRecord(infoFileFieldName)) : null;
		if (infoFile != null) {
			record.setAttribute("infoFile", infoFile);
		}

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				grid.deselectAllRecords();
				final Record listNewRecord = new Record(listRecord.getJsObj());
				listNewRecord.setAttribute(opzioniTimbroFieldName, object);
				updateDataAndRefresh(listNewRecord, listRecord);
			}
		});
		apponiTimbroWindow.show();
	}
	
	/**
	 * Viene verificato se il file da timbrare in fase di post registrazione è convertibile in pdf.
	 */
	public static boolean canBeTimbrato(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null) {
			return false;
		} else {
			String mimetype = lInfoFileRecord.getMimetype() != null ? lInfoFileRecord.getMimetype() : "";
			if (mimetype != null) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || lInfoFileRecord.isConvertibile()) {
					return true;
				} 
			}
			return false;
		}
	}
	
	public void manageChangedFileAllegatiParteDispositivo() {

	}

	public void manageChangedFileAllegati() {

	}
	
	public class ImportaAllegatiGridMultiLookupArchivio extends LookupArchivioPopup {

		private List<Record> multiLookupList = new ArrayList<Record>();

		public ImportaAllegatiGridMultiLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		@Override
		public String getWindowTitle() {
			return getImportaFileDocumentiButtonTitle();
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaImportaAllegatiMultiLookupArchivio();
		}

		@Override
		public void manageOnCloseClick() {
			super.manageOnCloseClick();
			if ((multiLookupList != null) && (multiLookupList.size() > 0)) {
				RecordList lRecordListDocumentToImport = new RecordList();
				for (Record record : multiLookupList) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
					lRecordListDocumentToImport.add(lRecordToLoad);
				}
				Record recordMassivo = new Record();
				recordMassivo.setAttribute("listaRecord", lRecordListDocumentToImport);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
				Layout.showWaitPopup(I18NUtil.getMessages().protocollazione_detail_importazioneFileWaitPopup_title());
				// Se cambia la logica nell'import bisogna riportarla anche nella medesima funzione in AllegatiItem
				lGwtRestDataSource.performCustomOperation("getProtocolloMassivo", recordMassivo, new DSCallback() {

					@Override
					public void execute(final DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							grid.deselectAllRecords();							
							if ((isDocCedAutotutela() && isDocumentiIstruttoria()) || isDocIstruttoriaDettFascicoloGenCompleto()) {
								importaUnitaDocumentaleDocIstruttoria(response);								
							} else {								
								if(isAttivaAllegatoUd()) {
									SC.ask("Vuoi importare un allegato per ogni riga-unità documentale selezionata? In caso contrario verrà importato un allegato per ogni file della/e righe selezionate.", new BooleanCallback() {

										@Override
										public void execute(Boolean value) {
											if (value){
												importaUnitaDocumentaleAllegato(response);											
											} else {
												importaFileFromUnitaDocumentale(response);												
											}
										}
									});									
								} else {								
									importaFileFromUnitaDocumentale(response);									
								}																
							}															
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_importazioneFile_error(), "", MessageType.ERROR));
						}
						Layout.hideWaitPopup();
					}
				}, new DSRequest());
			}

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			multiLookupList.add(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.size(); i++) {
					Record values = multiLookupList.get(i);
					if (values.getAttribute("idUdFolder").equals(record.getAttribute("id"))) {
						multiLookupList.remove(i);
						break;
					}
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

	}
	
	public boolean isAttivaAllegatoUd() {
		return false;
	}
	
	public void importaUnitaDocumentaleDocIstruttoria(DSResponse response) {		
		RecordList listaNewRecord = new RecordList();	
		RecordList listaRecordImport = response.getData()[0].getAttributeAsRecordList("listaRecord");
		if(listaRecordImport != null) {
			for (int i = 0; i < listaRecordImport.getLength(); i++) {
				Record recordImport = listaRecordImport.get(i);
				// Verifico la presenza di allegati
				boolean haveFile = false;
				if (recordImport.getAttribute("listaAllegati") != null && recordImport.getAttributeAsRecordArray("listaAllegati").length > 0) {
					haveFile = true;
				}
				recordImport.setAttribute("flgIdUdAppartenenzaContieneAllegati", haveFile);
				// SOLO se è protocollato o repertoriato valorizzo estremiProtUd
				if(recordImport.getAttribute("codCategoriaProtocollo") != null && ("PG".equals(recordImport.getAttribute("codCategoriaProtocollo")) || "R".equals(recordImport.getAttribute("codCategoriaProtocollo")))) {
					String estremiUd = "";
					if("PG".equals(recordImport.getAttribute("codCategoriaProtocollo"))) {
						estremiUd += "Prot. "; 
					} else if(recordImport.getAttribute("siglaProtocollo") != null && !"".equals(recordImport.getAttribute("siglaProtocollo"))) {
						estremiUd += recordImport.getAttribute("siglaProtocollo") + " "; 
					}										
					String dataProtocollo = DateUtil.format((Date) recordImport.getAttributeAsDate("dataProtocollo"));
					if(dataProtocollo == null || "".equals(dataProtocollo)) {
						dataProtocollo = recordImport.getAttribute("dataProtocollo") != null ? recordImport.getAttribute("dataProtocollo").substring(0, 10) : "";
					}
					estremiUd += dataProtocollo + "." + recordImport.getAttribute("nroProtocollo");
					recordImport.setAttribute("estremiProtUd", estremiUd);
				}
				// Metto una riga che rappresenta l'unità documentale
				Record newRecord = createNewRecordFromRecordImportaUnitaDocumentale(recordImport);
	//			addDataAndRefresh(newRecord, true);
				listaNewRecord.add(newRecord);									
			}
		}
		manageAfterImporta(listaNewRecord, response.getData()[0].getAttributeAsMap("errorMessages"));
	}		
	
	public void importaUnitaDocumentaleAllegato(DSResponse response) {
		RecordList listaNewRecord = new RecordList();	
		RecordList listaRecordImport = response.getData()[0].getAttributeAsRecordList("listaRecord");
		if(listaRecordImport != null) {
			for (int i = 0; i < listaRecordImport.getLength(); i++) {
				Record recordImport = listaRecordImport.get(i);
				// Metto una riga che rappresenta l'unità documentale
				Record newRecord = createNewRecordFromRecordImportaUnitaDocumentaleAllegato(recordImport);
//				addDataAndRefresh(newRecord, true);
				listaNewRecord.add(newRecord);									
			}
		}
		manageAfterImporta(listaNewRecord, response.getData()[0].getAttributeAsMap("errorMessages"));
	}
	
	public void importaFileFromUnitaDocumentale(DSResponse response) {
		RecordList listaNewRecord = new RecordList();	
		RecordList listaRecordImport = response.getData()[0].getAttributeAsRecordList("listaRecord");
		if(listaRecordImport != null) {
			for (int i = 0; i < listaRecordImport.getLength(); i++) {
				Record recordImport = listaRecordImport.get(i);
				String idUd = recordImport.getAttribute("idUd");
				String segnatura = recordImport.getAttribute("segnatura") != null ? recordImport.getAttribute("segnatura") : "";
				int numeroAllegato = 0;								
				boolean haveFile = false;
				// Verifico che sia presente il file primario
				if ((recordImport.getAttribute("uriFilePrimario") != null)
						&& (!"".equalsIgnoreCase(recordImport.getAttribute("uriFilePrimario")))) {
					haveFile = true;
					Record newRecord = createNewRecordFromRecordImportaFilePrimario(recordImport);
//					addDataAndRefresh(newRecord, true);	
//					InfoFileRecord info = newRecord.getAttribute("infoFile") != null ? new InfoFileRecord(newRecord.getAttributeAsRecord("infoFile")) : null;
//					controlAfterUpload(info, newRecord, false);
					listaNewRecord.add(newRecord);
				}
				// Verifico gli allegati
				if ((recordImport.getAttribute("listaAllegati") != null)) {
					Record[] listaAllegati = recordImport.getAttributeAsRecordArray("listaAllegati");
					for (Record recordAllegato : listaAllegati) {
						boolean allegatoDaImportare = false;
						if (!importConCtrlAllegatiXImportUnitaDoc()) {
							allegatoDaImportare = true;
						} else {
							boolean flgAttivaCtrlAllegatiXImportUnitaDoc = recordImport.getAttributeAsBoolean("flgAttivaCtrlAllegatiXImportUnitaDoc");
							boolean flgAllegatoDaImportare = recordAllegato.getAttributeAsBoolean("flgAllegatoDaImportare");
							if ((!flgAttivaCtrlAllegatiXImportUnitaDoc) || (flgAttivaCtrlAllegatiXImportUnitaDoc && flgAllegatoDaImportare)) {
								allegatoDaImportare = true;
							}
						}
						if (allegatoDaImportare) {
							haveFile = true;
							numeroAllegato += 1;
							Record newRecord = createNewRecordFromRecordImportaFileAllegato(recordAllegato, numeroAllegato, idUd, segnatura);
//							addDataAndRefresh(newRecord, true);
//							InfoFileRecord info = newRecord.getAttribute("infoFile") != null ? new InfoFileRecord(newRecord.getAttributeAsRecord("infoFile")) : null;
//							controlAfterUpload(info, newRecord, false);
							listaNewRecord.add(newRecord);
						}
					}
				}								
				// Se non ho nè primario nè allegati metto una riga che rappresenta l'unità documentale
				if (!haveFile) {
//					int nroLastAllegato = grid.getRecords().length;				
					Record newRecord = createNewRecordFromRecordImportaUnitaDocumentale(recordImport);
//					addDataAndRefresh(newRecord, true);
					listaNewRecord.add(newRecord);
				}
			}
		}
		manageAfterImporta(listaNewRecord, response.getData()[0].getAttributeAsMap("errorMessages"));
	}
	
	public void manageAfterImporta(RecordList listaNewRecord, Map mappaErrori) {		
		// aggiungo tutti i record importati alla grid con addAllData()
		addAllDataAndRefresh(listaNewRecord);
		// faccio i controlli su tutta la lista di file che ho importato
		controlAllAfterUploadMultiplo(listaNewRecord);							
		if (mappaErrori != null && mappaErrori.size() > 0 && mappaErrori.get("segnatureInError") != null && !"".equalsIgnoreCase((String) mappaErrori.get("segnatureInError"))) {
			String listaSegnatureInError = (String) mappaErrori.get("segnatureInError");
			String message = I18NUtil.getMessages().protocollazione_detail_elencoSegnatureInError_error() + " " + listaSegnatureInError;
			Layout.addMessage(new MessageBean(message, "", MessageType.WARNING));
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    		
			@Override
			public void execute() {
				refreshNroAllegato();
			}
    	});
	}
	
	public Record createNewRecordFromRecordImportaFilePrimario(Record record) {
		int nroLastAllegato = grid.getRecords().length;		
		String idUd = record.getAttribute("idUd");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";		
		String idDocType = null; //record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
		String nomeDocType = null; //record.getAttribute("nomeTipoDocumento") != null ? record.getAttribute("nomeTipoDocumento") : "";
		String displayFileName = record.getAttribute("nomeFilePrimario");
		String uri = record.getAttribute("uriFilePrimario");		
		// TODO omissis ?
		final Record newRecord = new Record();
		newRecord.setAttribute("id", "NEW_" + count++);
		newRecord.setAttribute("flgSalvato", false);
		newRecord.setAttribute("changed", true);
		newRecord.setAttribute("numeroProgrAllegato", nroLastAllegato + 1);	
		newRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroLastAllegato + 1);	
		if(uri != null && !"".equals(uri)) {
			newRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
			if (!getFlgParteDispositivoDefaultValue()){
				newRecord.setAttribute("flgNoPubblAllegato", true);
				newRecord.setAttribute("flgPubblicaSeparato", false);
				newRecord.setAttribute("flgDatiSensibili", false);
			} else {
				newRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
			}
		}
		if (isProtInModalitaWizard()) {
			if (isCanaleSupportoDigitale()) {
				newRecord.setAttribute("flgOriginaleCartaceo", false);
			}
			if (isCanaleSupportoCartaceo()) {
				newRecord.setAttribute("flgOriginaleCartaceo", true);
			}
		}
		newRecord.setAttribute("nomeFileAllegato", displayFileName != null ? displayFileName : "");
		newRecord.setAttribute("uriFileAllegato", uri != null ? uri : "");
		newRecord.setAttribute("remoteUri", false);	
		newRecord.setAttribute("nomeFileVerPreFirma", displayFileName != null ? displayFileName : "");
		newRecord.setAttribute("uriFileVerPreFirma", uri != null ? uri : "");
		newRecord.setAttribute("fileImportato", true);
		newRecord.setAttribute("idUdAppartenenza", idUd);
		newRecord.setAttribute("collegaDocumentoImportato", isCollegaDocumentiImportati());		
		newRecord.setAttribute("descrizioneFileAllegato", descrizione);
		newRecord.setAttribute("listaTipiFileAllegato", idDocType);
		newRecord.setAttribute("idTipoFileAllegato", idDocType);
		newRecord.setAttribute("descTipoFileAllegato", nomeDocType);
		newRecord.setAttribute("isChanged", true);
		newRecord.setAttribute("isUdSenzaFileImportata", false);				
		manageChangedFileAllegato(newRecord, true);
		newRecord.setAttribute("infoFile", new InfoFileRecord(record.getAttributeAsRecord("infoFile")));
		return newRecord;
	}

	public Record createNewRecordFromRecordImportaFileAllegato(Record record, int numeroAllegato, String idUd,
			String segnatura) {
		int nroLastAllegato = grid.getRecords().length;				
		String idDocType = record.getAttribute("listaTipiFileAllegato") != null ? record.getAttribute("listaTipiFileAllegato") : "";
		String nomeDocType = record.getAttribute("descTipoFileAllegato") != null ? record.getAttribute("descTipoFileAllegato") : "";
		String displayFileName = record.getAttribute("nomeFileAllegato");
		String uri = record.getAttribute("uriFileAllegato");
		String descrizione = segnatura + " Allegato N. " + (numeroAllegato);
		final Record newRecord = new Record();
		newRecord.setAttribute("id", "NEW_" + count++);
		newRecord.setAttribute("flgSalvato", false);
		newRecord.setAttribute("changed", true);
		newRecord.setAttribute("numeroProgrAllegato", nroLastAllegato + 1);	
		newRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroLastAllegato + 1);	
		if(uri != null && !"".equals(uri)) {
			newRecord.setAttribute("flgParteDispositivo", getFlgParteDispositivoDefaultValue());
			if (!getFlgParteDispositivoDefaultValue()){
				newRecord.setAttribute("flgNoPubblAllegato", true);
				newRecord.setAttribute("flgPubblicaSeparato", false);
				newRecord.setAttribute("flgDatiSensibili", false);
			} else {
				newRecord.setAttribute("flgPubblicaSeparato", getFlgPubblicaSeparatoDefaultValue());
			}
		}
		if (isProtInModalitaWizard()) {
			if (isCanaleSupportoDigitale()) {
				newRecord.setAttribute("flgOriginaleCartaceo", false);
			}
			if (isCanaleSupportoCartaceo()) {
				newRecord.setAttribute("flgOriginaleCartaceo", true);
			}
		}
		newRecord.setAttribute("nomeFileAllegato", displayFileName != null ? displayFileName : "");
		newRecord.setAttribute("uriFileAllegato", uri != null ? uri : "");
		newRecord.setAttribute("remoteUri", false);	
		newRecord.setAttribute("nomeFileVerPreFirma", displayFileName != null ? displayFileName : "");
		newRecord.setAttribute("uriFileVerPreFirma", uri != null ? uri : "");
		newRecord.setAttribute("fileImportato", true);
		newRecord.setAttribute("idUdAppartenenza", idUd);
		newRecord.setAttribute("collegaDocumentoImportato", isCollegaDocumentiImportati());		
		newRecord.setAttribute("descrizioneFileAllegato", descrizione);
		newRecord.setAttribute("listaTipiFileAllegato", idDocType);
		newRecord.setAttribute("idTipoFileAllegato", idDocType);
		newRecord.setAttribute("descTipoFileAllegato", nomeDocType);
		newRecord.setAttribute("isChanged", true);
		newRecord.setAttribute("isUdSenzaFileImportata", false);		
		manageChangedFileAllegato(newRecord, true);
		newRecord.setAttribute("infoFile", new InfoFileRecord(record.getAttributeAsRecord("infoFile")));
		return newRecord;
	}
	
	public Record createNewRecordFromRecordImportaUnitaDocumentale(Record record) {
		int nroLastAllegato = grid.getRecords().length;			
		String idUd = record.getAttribute("idUd");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";		
		String idDocType = record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
		String nomeDocType = record.getAttribute("nomeTipoDocumento") != null ? record.getAttribute("nomeTipoDocumento") : "";		
		String estremiProtUd = record.getAttribute("estremiProtUd") != null ? record.getAttribute("estremiProtUd") : "";
		boolean presenteFilePrimario = record.getAttribute("uriFilePrimario") != null && !"".equalsIgnoreCase(record.getAttribute("uriFilePrimario"));				
		final Record newRecord = new Record();
		newRecord.setAttribute("id", "NEW_" + count++);
		newRecord.setAttribute("flgSalvato", false);
		newRecord.setAttribute("changed", true);
		newRecord.setAttribute("numeroProgrAllegato", nroLastAllegato + 1);	
		newRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroLastAllegato + 1);
		if (presenteFilePrimario) {
			newRecord.setAttribute("nomeFileAllegato", record.getAttribute("nomeFilePrimario"));
			newRecord.setAttribute("uriFileAllegato", record.getAttribute("uriFilePrimario"));
		} else {
			newRecord.setAttribute("nomeFileAllegato", "");
			newRecord.setAttribute("uriFileAllegato", "");
		}
		// idDocPrimario è prensente anche se non c'è il file primario
		newRecord.setAttribute("idDocAllegato", record.getAttribute("idDocPrimario"));		
		newRecord.setAttribute("remoteUri", false);				
		newRecord.setAttribute("fileImportato", true);
		newRecord.setAttribute("idUdAppartenenza", idUd);
		newRecord.setAttribute("collegaDocumentoImportato", isCollegaDocumentiImportati());		
		newRecord.setAttribute("descrizioneFileAllegato", descrizione);
		newRecord.setAttribute("listaTipiFileAllegato", idDocType);
		newRecord.setAttribute("idTipoFileAllegato", idDocType);
		newRecord.setAttribute("descTipoFileAllegato", nomeDocType);	
		// Quando importo un'unità documentale isChanged deve essere a false, altrimenti al salvataggio scatta un nuovo versionamento del documento dell'unità documentale importata.
		// Tale versionamento inoltre fallisce se l'utente non ha pemessi di modifica sull'unità documentale importata.
		newRecord.setAttribute("isChanged", false);
		newRecord.setAttribute("isUdSenzaFileImportata", !presenteFilePrimario); 
		newRecord.setAttribute("estremiProtUd", estremiProtUd);
		newRecord.setAttribute("flgIdUdAppartenenzaContieneAllegati", record.getAttributeAsBoolean("flgIdUdAppartenenzaContieneAllegati"));
		manageChangedFileAllegato(newRecord, true);
		if (presenteFilePrimario) {
			newRecord.setAttribute("infoFile", new InfoFileRecord(record.getAttributeAsRecord("infoFile")));			
		}
		return newRecord;
	}
	
	public Record createNewRecordFromRecordImportaUnitaDocumentaleAllegato(Record record) {
		int nroLastAllegato = grid.getRecords().length;			
		String idUd = record.getAttribute("idUd");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";		
		String idDocType = record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
		String nomeDocType = record.getAttribute("nomeTipoDocumento") != null ? record.getAttribute("nomeTipoDocumento") : "";		
		final Record newRecord = new Record();
		newRecord.setAttribute("id", "NEW_" + count++);
		newRecord.setAttribute("flgSalvato", false);
		newRecord.setAttribute("changed", true);
		newRecord.setAttribute("numeroProgrAllegato", nroLastAllegato + 1);	
		newRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroLastAllegato + 1);
		newRecord.setAttribute("nomeFileAllegato", "");
		newRecord.setAttribute("uriFileAllegato", "");
		// idDocPrimario è prensente anche se non c'è il file primario
		newRecord.setAttribute("idDocAllegato", record.getAttribute("idDocPrimario"));		
		newRecord.setAttribute("fileImportato", true);
		newRecord.setAttribute("idUdAllegato", idUd);	
		newRecord.setAttribute("descrizioneFileAllegato", descrizione);
		newRecord.setAttribute("listaTipiFileAllegato", idDocType);
		newRecord.setAttribute("idTipoFileAllegato", idDocType);
		newRecord.setAttribute("descTipoFileAllegato", nomeDocType);
		newRecord.setAttribute("isChanged", false);
//		newRecord.setAttribute("isUdSenzaFileImportata", true);
		manageChangedFileAllegato(newRecord, true);
		return newRecord;
	}
	
	public class UploadAllegatoRecordComponent extends HLayout implements UpdateableRecordComponent {

		private DynamicForm form;
		
		public UploadAllegatoRecordComponent(DynamicForm form) {
			super(1);
			this.form = form;
			setHeight(22);   
			setWidth(25);
			setAlign(Alignment.CENTER);
			addMember(form);
		}
		
		@Override
		public void updateComponent(ListGridRecord record) {
			form.markForRedraw();
		}
		
		public void fireEventUpload() {			
			if(form.getFields()[0] != null) {
				ClickEvent lClickEvent = new ClickEvent(form.getFields()[0].getJsObj());
				form.getFields()[0].fireEvent(lClickEvent);
			}
		}
		
	}
	
	public boolean showOperazioniTimbraturaAllegato(Record listRecord) {
		Record detailRecord = getDetailRecord();
		return detailRecord != null && detailRecord.getAttribute("codCategoriaProtocollo")!= null && 
				("PP".equals(detailRecord.getAttribute("codCategoriaProtocollo")) || "PG".equals(detailRecord.getAttribute("codCategoriaProtocollo")) || "R".equals(detailRecord.getAttribute("codCategoriaProtocollo")));
	}
	
	public boolean importConCtrlAllegatiXImportUnitaDoc() {
		return false;
	}
	
	public boolean isShowModalPreview() {
		return true;
	}
	
}
