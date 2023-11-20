/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.common.items.FileButtonsItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDMailWindow;
import it.eng.auriga.ui.module.layout.client.istanze.IstanzeWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.postainarrivo.PostaInArrivoRegistrazioneWindow;
import it.eng.auriga.ui.module.layout.client.postainuscita.PostaInUscitaRegistrazioneWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaEtichettaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroBean;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroCopertinaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.utility.Styles;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class AllegatoCanvas extends ReplicableCanvas {
	
	protected final int TITTLE_ALIGN_FROM_ALLEGATO_DETAIL_IN_GRID_ITEM_WIDTH = 100;
	
	protected VLayout lVLayoutCanvas;

	protected ReplicableCanvasForm mDynamicForm;
	protected ReplicableCanvasForm mDynamicFormOmissis;
	
	protected HiddenItem idUdAppartenenzaItem;
	protected HiddenItem flgIdUdAppartenenzaContieneAllegatiItem;
	protected HiddenItem idUdAllegatoItem;	
	protected HiddenItem flgAllegatoDaImportareItem; // non lo metto in AllegatiGridItem? forse non serve perchè essendo una grid e non un form i valori poi vengono passati lo stesso
	protected HiddenItem flgSalvatoItem;
	protected HiddenItem flgTipoProvXProtItem; // non lo metto in AllegatiGridItem? forse non serve perchè essendo una grid e non un form i valori poi vengono passati lo stesso
	protected HiddenItem ruoloUdItem;
	protected HiddenItem isUdSenzaFileImportataItem;
	protected HiddenItem idTaskItem;
	protected HiddenItem idEmailItem;
	protected HiddenItem idAttachEmailItem;
	protected HiddenItem collegaDocumentoImportatoItem;	
	protected HiddenItem idDocAllegatoHiddenItem;
	protected HiddenItem fileImportatoItem;
	protected HiddenItem uriFileAllegatoItem;
	protected HiddenItem tsInsLastVerFileAllegatoItem;
	protected HiddenItem infoFileItem;
	protected HiddenItem remoteUriItem;
	protected HiddenItem isChangedItem;
	protected HiddenItem nomeFileAllegatoOrigItem;
	protected HiddenItem nomeFileAllegatoTifItem;
	protected HiddenItem uriFileAllegatoTifItem;
	protected HiddenItem nomeFileVerPreFirmaItem;
	protected HiddenItem uriFileVerPreFirmaItem;
	protected HiddenItem improntaVerPreFirmaItem;
	protected HiddenItem infoFileVerPreFirmaItem;
	protected HiddenItem nroUltimaVersioneItem;
	protected HiddenItem esitoInvioACTASerieAttiIntegraliItem;
	protected HiddenItem esitoInvioACTASeriePubblItem;
	protected HiddenItem numFileCaricatiInUploadMultiploItem;
	protected HiddenItem flgGenAutoDaModelloItem;
	protected HiddenItem flgGenDaModelloDaFirmareItem;
	
	protected TextItem numeroAllegatoItem;
	protected CheckboxItem flgProvEsternaItem;
	protected CheckboxItem flgParereItem;
	protected HiddenItem flgParteDispositivoSalvatoItem;
	protected CheckboxItem flgParteDispositivoItem;
	protected CheckboxItem flgNoPubblAllegatoItem;
	protected CheckboxItem flgPubblicaSeparatoItem;
	protected HiddenItem flgPubblicaAllegatiSeparatiInAllegatiGridItem;
	protected RadioGroupItem flgPaginaFileUnioneItem;
	protected ExtendedTextItem nroPagFileUnioneItem;
	protected TextItem nroPagFileUnioneOmissisItem;
	protected CheckboxItem flgDatiProtettiTipo1Item;
	protected CheckboxItem flgDatiProtettiTipo2Item;
	protected CheckboxItem flgDatiProtettiTipo3Item;
	protected CheckboxItem flgDatiProtettiTipo4Item;
	protected CheckboxItem flgDatiSensibiliItem;
	
	protected HiddenItem idTipoFileAllegatoSalvatoHiddenItem;
	protected HiddenItem idTipoFileAllegatoHiddenItem;
	protected HiddenItem descTipoFileAllegatoHiddenItem;
	protected FilteredSelectItem listaTipiFileAllegatoItem;
	protected ExtendedTextItem descrizioneFileAllegatoItem;
	
	protected ExtendedTextItem nomeFileAllegatoItem;
	protected FileUploadItemWithFirmaAndMimeType uploadAllegatoItem;
	protected NestedFormItem allegatoButtons;
	protected ImgButtonItem previewButton;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem editButton;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem dimensioneSignificativaIcon; 
	protected ImgButtonItem downloadOutsideAltreOpMenuButton;
	protected ImgButtonItem altreOpButton;
	protected ImgButtonItem generaDaModelloButton;
	protected ImgButtonItem trasformaInPrimarioButton;
	protected ImgButtonItem protocollaInUscitaButton; //TODO da mettere in AllegatiGridItem?
	protected TextItem improntaItem; 
	protected HiddenItem tipoBarcodeContrattoItem;
	protected HiddenItem barcodeContrattoItem;
	protected HiddenItem energiaGasContrattoItem;
	protected HiddenItem tipoSezioneContrattoItem;
	protected HiddenItem codContrattoItem;
	protected HiddenItem flgPresentiFirmeContrattoItem;
	protected HiddenItem flgFirmeCompleteContrattoItem;
	protected HiddenItem nroFirmePrevisteContrattoItem;
	protected HiddenItem nroFirmeCompilateContrattoItem;
	protected HiddenItem idUDScansioneItem;
	protected HiddenItem flgIncertezzaRilevazioneFirmeContrattoItem;
	protected ImgButtonItem dettaglioDocContrattiBarcodeButton;
	protected TextItem estremiProtUdItem;
	protected ImgButtonItem dettaglioEstremiProtocolloButton;
	protected ImgButtonItem protocollazioneEntrataButton; //TODO da mettere in AllegatiGridItem?
	protected ImgButtonItem protocollazioneUscitaButton; //TODO da mettere in AllegatiGridItem?
	protected ImgButtonItem protocollazioneInternaButton; //TODO da mettere in AllegatiGridItem?
	protected ImgButtonItem dettaglioIstanzaButton; //TODO da mettere in AllegatiGridItem?
	protected DateItem dataRicezioneItem;
	protected ImgButtonItem gestSepAllegatiUDButton;
	protected ImgButtonItem dettaglioUdAllegatoButton;	

	protected CheckboxItem flgSostituisciVerPrecItem;
	protected CheckboxItem flgOriginaleCartaceoItem;
	protected CheckboxItem flgCopiaSostitutivaItem;
	
	protected HiddenItem flgTimbratoFilePostRegItem;
	protected HiddenItem opzioniTimbroItem;
	protected CheckboxItem flgTimbraFilePostRegItem;
	protected NestedFormItem emailButtons;
	
	protected HiddenItem idDocOmissisItem;
	protected HiddenItem uriFileOmissisItem; 
	protected HiddenItem infoFileOmissisItem; 
	protected HiddenItem remoteUriOmissisItem; 
	protected HiddenItem isChangedOmissisItem; 
	protected HiddenItem nomeFileOrigOmissisItem; 
	protected HiddenItem nomeFileTifOmissisItem; 
	protected HiddenItem uriFileTifOmissisItem;
	protected HiddenItem nomeFileVerPreFirmaOmissisItem;
	protected HiddenItem uriFileVerPreFirmaOmissisItem;
	protected HiddenItem improntaVerPreFirmaOmissisItem;
	protected HiddenItem infoFileVerPreFirmaOmissisItem;
	protected HiddenItem nroUltimaVersioneOmissisItem;
	
	protected ExtendedTextItem nomeFileOmissisItem;	
	protected FileButtonsItem fileOmissisButtons;
	
	protected CheckboxItem flgSostituisciVerPrecOmissisItem;
	
	protected HiddenItem flgTimbratoFilePostRegOmissisItem;
	protected HiddenItem opzioniTimbroOmissisItem;
	protected CheckboxItem flgTimbraFilePostRegOmissisItem;
//	protected DocumentItem fileOmissisAllegatoItem;

	protected boolean forceToShowGeneraDaModelloForTipoTaskDoc = false;
	
	// Indica se il file a cui si fa riferimento è stato importato da un altro documento
	protected boolean fileImportato = false;
	// Indica (nel caso di file importato) se l'unità documentale di provenienza deve essere collegata a quella attuale
	// protected boolean collegaDocumentoImportato = false;

	protected Integer nomeFileWidth;

	protected Map valuesOrig;
	
	protected String idTipoFileAllegato;
	protected String descTipoFileAllegato;
	
	public AllegatoCanvas(AllegatiItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	public AllegatoCanvas(AllegatiItem item) {
		super(item);
	}
	
	/**
	 * Per risolvere il problema che si verificava quando si utilizzava AllegatoCanvas nel dettaglio di AllegatiGridItem:
	 * - Se cambio il nome di un file che ha la vers omissis lo porta in lista modificato e lo salva correttamente: tutto ok
	 * - Se cambio il nome di un file SENZA ver omissis lo porta in lista correttamente modificato ma al salvataggio perde la modifica
	 * - Se cambio il nome di una versione omissis al salvataggio perde la modifica
	 * Quando un ExtendedTextItem è l'ultimo visibile a comparire in maschera, se dopo averci editato dentro clicco per es. il bottone di salvataggio, 
	 * viene intercettato solo l'evento del click del bottone, senza entrare nel ChangedBlurHandler dell'ExtendedTextItem, anche se si utilizza il focusAfterGroup().
	 * Per ovviare a questa cosa si deve aggiungere subito dopo un item FOCUSABLE (in questo caso sia dopo nomeFileAllegatoItem che dopo nomeFileOmissisItem) 
	 * "nascosto", così da scatenare il blur dell'ultimo item ed intercettare l'evento ChangedEvent e settare la proprietà isChanged a true
	 * 
	 */	
	protected ImgButtonItem buildTransparentItem() {
		ImgButtonItem item = new ImgButtonItem("transaparentButton","blank.png", "");
		item.setCanFocus(true);
		item.setWidth(0);
		item.setIconWidth(1);
		item.setIconHeight(1);
		item.setColSpan(0);
		item.setSelectOnFocus(false);
		item.setCellStyle(Styles.transparent);
		return item;
	}

	@Override
	public void disegna() {
		
		if (getParams() != null) {
			String lString = getParams().get("nomeFileWidth");
			if (lString != null && !lString.trim().equals("")) {
				nomeFileWidth = Integer.valueOf(lString);
			}
		}
				
		buildVLayoutCanvas();
		
		addChild(lVLayoutCanvas);
	}
	
	public void buildVLayoutCanvas() {
		
		buildDynamicForm();
		buildDynamicFormOmissis();

		lVLayoutCanvas = new VLayout();
		lVLayoutCanvas.setHeight(5);
		lVLayoutCanvas.setOverflow(Overflow.VISIBLE);
		lVLayoutCanvas.setMembers(mDynamicForm, mDynamicFormOmissis);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			lVLayoutCanvas.setBorder("1px solid grey");
			lVLayoutCanvas.setMargin(5);
			lVLayoutCanvas.setPadding(5);
		}
	}
	
	public void buildDynamicForm() {
		
		mDynamicForm = new ReplicableCanvasForm() {
			
			@Override
			public boolean hasValue(Record defaultRecord) {

				Map<String,Object> values = getValuesManager() != null ? getValuesManager().getValues() : getValues();
				if (values != null && values.size() > 0) {
					if (checkAllegatoValorizzato(values)) {
						return true;
					}
				}
				return false;
			}
		};
			
		mDynamicForm.setNumCols(40);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		// id tipo file
		idTipoFileAllegatoSalvatoHiddenItem = new HiddenItem("idTipoFileAllegatoSalvato");
		idTipoFileAllegatoHiddenItem = new HiddenItem("idTipoFileAllegato");
		descTipoFileAllegatoHiddenItem = new HiddenItem("descTipoFileAllegato");

		// numero file
		numeroAllegatoItem = new TextItem("numeroProgrAllegato", "N°");
		numeroAllegatoItem.setWidth(30);
		numeroAllegatoItem.setShowTitle(true);
		numeroAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				mDynamicForm.setValue("numeroProgrAllegato", "" + getCounter());
				return getItem().getTotalMembers() > 1 && showNumeroAllegato();
				// String numeroProgrAllegato = item.getValue() != null ? (String) item.getValue() : null;
				// return ((numeroProgrAllegato != null) && (numeroProgrAllegato.length() > 0));
			}
		});

		// Nro Ultima Versione
		nroUltimaVersioneItem = new HiddenItem("nroUltimaVersione");
		
		// Archiviazione in ACTA
		esitoInvioACTASerieAttiIntegraliItem = new HiddenItem("esitoInvioACTASerieAttiIntegrali");
		esitoInvioACTASeriePubblItem = new HiddenItem("esitoInvioACTASeriePubbl");

		numFileCaricatiInUploadMultiploItem = new HiddenItem("numFileCaricatiInUploadMultiplo");
		idUDScansioneItem = new HiddenItem("idUDScansione");
		
		// File generati da modello
		flgGenAutoDaModelloItem = new HiddenItem("flgGenAutoDaModello");
		flgGenDaModelloDaFirmareItem = new HiddenItem("flgGenDaModelloDaFirmare");
		
		// descrizione tipo documento
		listaTipiFileAllegatoItem = new FilteredSelectItem("listaTipiFileAllegato", ((AllegatiItem) getItem()).getTitleTipiFileAllegato()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("idTipoFileAllegato", record.getAttributeAsString("idTipoFileAllegato"));
				mDynamicForm.setValue("descTipoFileAllegato", record.getAttributeAsString("descTipoFileAllegato"));
				if(showFlgParere()) {
					String flgParere = record.getAttribute("flgParere");
					if(flgParere != null && !"".equals(flgParere)) {
						mDynamicForm.setValue("flgParere", "1".equals(flgParere));
					}
					// se è un parere il check parte integrante è sempre a false e la pubblicazione è sempre separata, 
					// l'esclusione dalla pubbl. invece viene letta dal record del tipo selezionato (se il valore è null lo ignora)
					if(flgParere != null && "1".equals(flgParere)) {
						if(showFlgParteDispositivo()) mDynamicForm.setValue("flgParteDispositivo", false);
						if(showFlgNoPubblAllegato()) {
							String flgNoPubbl = record.getAttribute("flgNoPubbl");
							if(flgNoPubbl != null && !"".equals(flgNoPubbl)) {
								mDynamicForm.setValue("flgNoPubblAllegato", "1".equals(flgNoPubbl));
							}
						}						
						if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", true);
					} else if(showFlgParteDispositivo()) {
						String flgParteDispositivo = record.getAttribute("flgParteDispositivo");	
						if(flgParteDispositivo != null && !"".equals(flgParteDispositivo)) {
							mDynamicForm.setValue("flgParteDispositivo", "1".equals(flgParteDispositivo));
							// se non è parte integrante viene sempre escluso dalla pubblicazione e il check pubblica separatamente è sempre a false
							if("0".equals(flgParteDispositivo)) {
								if(showFlgNoPubblAllegato()) mDynamicForm.setValue("flgNoPubblAllegato", true);
								if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", false);
								boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
								boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;
								if(showFileOmissis) {
									mDynamicForm.setValue("flgDatiSensibili", false);
									manageOnChangedFlgDatiSensibili();
								}
							} 
							// se invece è parte integrante il valore degli altri due check viene letto dal record del tipo selezionato (se il valore è null lo ignora)
							else if("1".equals(flgParteDispositivo)) {
								if(showFlgNoPubblAllegato()) {
									String flgNoPubbl = record.getAttribute("flgNoPubbl");
									if(flgNoPubbl != null && !"".equals(flgNoPubbl)) {
										mDynamicForm.setValue("flgNoPubblAllegato", "1".equals(flgNoPubbl));
									}	
								}
								if(showFlgPubblicaSeparato()) {
									String flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato");
									if(flgPubblicaSeparato != null && !"".equals(flgPubblicaSeparato)) {
										mDynamicForm.setValue("flgPubblicaSeparato", "1".equals(flgPubblicaSeparato));
									}
								}												
							}
						} else {
							// se nel record del tipo selezionato il valore da preimpostare per il check parte integrante è null, viene ignorato anche il valore degli altri due flag
						}
					}
				} else if(showFlgParteDispositivo()) {
					String flgParteDispositivo = record.getAttribute("flgParteDispositivo");	
					if(flgParteDispositivo != null && !"".equals(flgParteDispositivo)) {
						mDynamicForm.setValue("flgParteDispositivo", "1".equals(flgParteDispositivo));
						// se non è parte integrante viene sempre escluso dalla pubblicazione e il check pubblica separatamente è sempre a false
						if("0".equals(flgParteDispositivo)) {
							if(showFlgNoPubblAllegato()) mDynamicForm.setValue("flgNoPubblAllegato", true);
							if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", false);
							boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
							boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;
							if(showFileOmissis) {
								mDynamicForm.setValue("flgDatiSensibili", false);
								manageOnChangedFlgDatiSensibili();
							}
						} 
						// se invece è parte integrante il valore degli altri due check viene letto dal record del tipo selezionato (se il valore è null lo ignora)
						else if("1".equals(flgParteDispositivo)) {
							if(showFlgNoPubblAllegato()) {
								String flgNoPubbl = record.getAttribute("flgNoPubbl");
								if(flgNoPubbl != null && !"".equals(flgNoPubbl)) {
									mDynamicForm.setValue("flgNoPubblAllegato", "1".equals(flgNoPubbl));
								}	
							}
							if(showFlgPubblicaSeparato()) {
								String flgPubblicaSeparato = record.getAttribute("flgPubblicaSeparato");
								if(flgPubblicaSeparato != null && !"".equals(flgPubblicaSeparato)) {
									mDynamicForm.setValue("flgPubblicaSeparato", "1".equals(flgPubblicaSeparato));
								}
							}												
						}
					} else {
						// se nel record del tipo selezionato il valore da preimpostare per il check parte integrante è null, viene ignorato anche il valore degli altri due flag
					}
				}
				mDynamicForm.markForRedraw();
				redrawButtons();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("listaTipiFileAllegato", "");
				mDynamicForm.setValue("idTipoFileAllegato", "");
				mDynamicForm.setValue("descTipoFileAllegato", "");
				if(showFlgParere()) {
					boolean flgParere = mDynamicForm.getValue("flgParere") != null && new Boolean(mDynamicForm.getValueAsString("flgParere"));
					if(flgParere) {
						mDynamicForm.setValue("flgParere", false);
						if(showFlgParteDispositivo()) mDynamicForm.setValue("flgParteDispositivo", false);
						if(showFlgNoPubblAllegato()) mDynamicForm.setValue("flgNoPubblAllegato", true);
						if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", false);
						if(((AllegatiItem) getItem()).getShowVersioneOmissis()) {
							mDynamicForm.setValue("flgDatiSensibili", false);
							manageOnChangedFlgDatiSensibili();
						}
					}
				}
				mDynamicForm.markForRedraw();
				redrawButtons();
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("listaTipiFileAllegato", "");
					mDynamicForm.setValue("idTipoFileAllegato", "");
					mDynamicForm.setValue("descTipoFileAllegato", "");
					if(showFlgParere()) {
						boolean flgParere = mDynamicForm.getValue("flgParere") != null && new Boolean(mDynamicForm.getValueAsString("flgParere"));
						if(flgParere) {
							mDynamicForm.setValue("flgParere", false);
							if(showFlgParteDispositivo()) mDynamicForm.setValue("flgParteDispositivo", false);
							if(showFlgNoPubblAllegato()) mDynamicForm.setValue("flgNoPubblAllegato", true);
							if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", false);
							boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
							boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;
							if(showFileOmissis) {
								mDynamicForm.setValue("flgDatiSensibili", false);
								manageOnChangedFlgDatiSensibili();
							}
						}	
					}
					mDynamicForm.markForRedraw();
					redrawButtons();
				}
			}

			@Override
			protected ListGrid builPickListProperties() {
				ListGrid pickListProperties = super.builPickListProperties();
				pickListProperties.setShowHeader(false);
				if (!((AllegatiItem) getItem()).showFilterEditorInTipiFileAllegato()) {
					pickListProperties.setShowFilterEditor(false);
				}
				return pickListProperties;
			}
		};
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			listaTipiFileAllegatoItem.setStartRow(true);
			listaTipiFileAllegatoItem.setWidth(650);
			listaTipiFileAllegatoItem.setColSpan(30);
		} else {
			listaTipiFileAllegatoItem.setWidth(250);		
		}
		listaTipiFileAllegatoItem.setAutoFetchData(false);
		listaTipiFileAllegatoItem.setFetchMissingValues(true);
		ListGridField descTipoFileAllegatoField = new ListGridField("descTipoFileAllegato", I18NUtil.getMessages().protocollazione_detail_tipoItem_title());
		listaTipiFileAllegatoItem.setPickListFields(descTipoFileAllegatoField);
		listaTipiFileAllegatoItem.setFilterLocally(true);
		listaTipiFileAllegatoItem.setValueField("idTipoFileAllegato");
		listaTipiFileAllegatoItem.setDisplayField("descTipoFileAllegato");
		listaTipiFileAllegatoItem.setOptionDataSource(((AllegatiItem) getItem()).getTipiFileAllegatoDataSource());
		listaTipiFileAllegatoItem.setClearable(true);
		listaTipiFileAllegatoItem.setShowIcons(true);
		listaTipiFileAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				if (idTipoFileAllegato != null && !"".equals(idTipoFileAllegato) && descTipoFileAllegato != null && !"".equals(descTipoFileAllegato)) {

					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					valueMap.put(idTipoFileAllegato, descTipoFileAllegato);
					listaTipiFileAllegatoItem.setValueMap(valueMap);
					listaTipiFileAllegatoItem.setCanEdit(false);

					mDynamicForm.setValue("listaTipiFileAllegato", idTipoFileAllegato);
					mDynamicForm.setValue("idTipoFileAllegato", idTipoFileAllegato);
					mDynamicForm.setValue("descTipoFileAllegato", descTipoFileAllegato);
					
					listaTipiFileAllegatoItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
					// getInstance().setBackgroundColor("#FFFAAF");
					// getInstance().setBorder("2px solid #FF0");
				}

				return showTipoAllegato();
			}
		});
		listaTipiFileAllegatoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {				
				if(((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto()) {
					redrawCanEditAfterChangedTipoDocIstruttoriaDettFascicoloGenCompleto(); // se il tipo è un modello della generazione automatica devo disabilitare i campi del documento e tutti i tasti che aggiornano il file					
				}
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
			}
		});

		descrizioneFileAllegatoItem = new ExtendedTextItem("descrizioneFileAllegato", ((AllegatiItem) getItem()).getTitleDescrizioneFileAllegato());
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			descrizioneFileAllegatoItem.setStartRow(true);
			descrizioneFileAllegatoItem.setShowTitle(true);
			descrizioneFileAllegatoItem.setShowHint(false);
			descrizioneFileAllegatoItem.setTitle(((AllegatiItem) getItem()).getTitleDescrizioneFileAllegato());		
			descrizioneFileAllegatoItem.setWidth(650);
			descrizioneFileAllegatoItem.setColSpan(30);
		} else {
			descrizioneFileAllegatoItem.setShowTitle(false);
			descrizioneFileAllegatoItem.setShowHint(true);
			descrizioneFileAllegatoItem.setShowHintInField(true);
			descrizioneFileAllegatoItem.setHint(((AllegatiItem) getItem()).getTitleDescrizioneFileAllegato());
			if (((AllegatiItem) getItem()).getWidthDescrizioneFileAllegato() != null) {
				descrizioneFileAllegatoItem.setWidth(((AllegatiItem) getItem()).getWidthDescrizioneFileAllegato().intValue());
			} else {
				descrizioneFileAllegatoItem.setWidth(250);
			}
		}
		descrizioneFileAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDescrizioneFileAllegato();
			}
		});
		descrizioneFileAllegatoItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				((AllegatiItem) getItem()).manageOnChanged();
			}
		});

		nomeFileAllegatoItem = new ExtendedTextItem("nomeFileAllegato", setTitleAlignFromAllegatoDetailInGridItem(((AllegatiItem) getItem()).getTitleNomeFileAllegato(), false));
		nomeFileAllegatoItem.setShowTitle(((AllegatiItem) getItem()).getShowTitleNomeFileAllegato());
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			nomeFileAllegatoItem.setStartRow(true);
			nomeFileAllegatoItem.setWidth(650);
			nomeFileAllegatoItem.setColSpan(30);
		} else {
			if (((AllegatiItem) getItem()).getWidthNomeFileAllegato() != null) {
				nomeFileAllegatoItem.setWidth(((AllegatiItem) getItem()).getWidthNomeFileAllegato().intValue());
			} else {
				nomeFileAllegatoItem.setWidth(nomeFileWidth != null ? nomeFileWidth : 250);
			}	
		}
		// nomeFileAllegatoItem.setRedrawOnChange(true);
		nomeFileAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				nomeFileAllegatoItem.setTitle(setTitleAlignFromAllegatoDetailInGridItem(((AllegatiItem) getItem()).getTitleNomeFileAllegato(), false));
				return showNomeFileAllegato();
			}
		});
		nomeFileAllegatoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (nomeFileAllegatoItem.getValue() == null || ((String) nomeFileAllegatoItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (mDynamicForm.getValueAsString("nomeFileAllegatoOrig") != null && !"".equals(mDynamicForm.getValueAsString("nomeFileAllegatoOrig"))
						&& !nomeFileAllegatoItem.getValue().equals(mDynamicForm.getValueAsString("nomeFileAllegatoOrig"))) {
					manageChangedFileAllegato();
				}
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
			}
		});
		nomeFileAllegatoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {

				InfoFileRecord infoFileAllegato = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				return ((AllegatiItem) getItem()).isFormatoAmmesso(infoFileAllegato);
			}

		});

		idDocAllegatoHiddenItem = new HiddenItem("idDocAllegato");
		uriFileAllegatoItem = new HiddenItem("uriFileAllegato");
		tsInsLastVerFileAllegatoItem = new HiddenItem("tsInsLastVerFileAllegato");
		fileImportatoItem = new HiddenItem("fileImportato");
		idUdAppartenenzaItem = new HiddenItem("idUdAppartenenza");
		flgIdUdAppartenenzaContieneAllegatiItem = new HiddenItem("flgIdUdAppartenenzaContieneAllegati");
		idUdAllegatoItem = new HiddenItem("idUdAllegato");
		flgAllegatoDaImportareItem = new HiddenItem("flgAllegatoDaImportareItem");
		flgSalvatoItem = new HiddenItem("flgSalvato");
		flgTipoProvXProtItem = new HiddenItem("flgTipoProvXProt");
		ruoloUdItem = new HiddenItem("ruoloUd");
		nomeFileAllegatoOrigItem = new HiddenItem("nomeFileAllegatoOrig");
		nomeFileAllegatoTifItem = new HiddenItem("nomeFileAllegatoTif"); // NOME del Tif
		uriFileAllegatoTifItem = new HiddenItem("uriFileAllegatoTif"); // URI del Tif
		uriFileVerPreFirmaItem = new HiddenItem("uriFileVerPreFirma");
		nomeFileVerPreFirmaItem = new HiddenItem("nomeFileVerPreFirma");
		improntaVerPreFirmaItem = new HiddenItem("improntaVerPreFirma");
		infoFileVerPreFirmaItem = new HiddenItem("infoFileVerPreFirma");				
		collegaDocumentoImportatoItem = new HiddenItem("collegaDocumentoImportato");
		isUdSenzaFileImportataItem = new HiddenItem("isUdSenzaFileImportata");
		remoteUriItem = new HiddenItem("remoteUri");
		isChangedItem = new HiddenItem("isChanged");
		isChangedItem.setDefaultValue(false);
		idTaskItem = new HiddenItem("idTask");
		idEmailItem = new HiddenItem("idEmail");		
		idAttachEmailItem = new HiddenItem("idAttachEmail");

		uploadAllegatoItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				mDynamicForm.setValue("nomeFileAllegato", displayFileName);
				mDynamicForm.setValue("uriFileAllegato", uri);
				mDynamicForm.setValue("nomeFileAllegatoTif", "");
				mDynamicForm.setValue("uriFileAllegatoTif", "");
				mDynamicForm.setValue("remoteUri", false);
				mDynamicForm.setValue("nomeFileVerPreFirma", displayFileName);
				mDynamicForm.setValue("uriFileVerPreFirma", uri);
				mDynamicForm.setValue("isUdSenzaFileImportata", false);
				changedEventAfterUpload(displayFileName, uri);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
				uploadAllegatoItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				mDynamicForm.setValue("infoFile", info);
				mDynamicForm.setValue("improntaVerPreFirma", info.getImpronta());
				mDynamicForm.setValue("infoFileVerPreFirma", info);				
				String nomeFileAllegato = mDynamicForm.getValueAsString("nomeFileAllegato");
				String nomeFileAllegatoOrig = mDynamicForm.getValueAsString("nomeFileAllegatoOrig");
				if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || (nomeFileAllegato != null && !"".equals(nomeFileAllegato)
						&& nomeFileAllegatoOrig != null && !"".equals(nomeFileAllegatoOrig) && !nomeFileAllegato.equals(nomeFileAllegatoOrig))) {
					manageChangedFileAllegato();
				}
				controlAfterUpload(info);				
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
			}
		});
		uploadAllegatoItem.setAttribute("nascosto", !showUpload());		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			uploadAllegatoItem.setCanFocus(true);
		}
		uploadAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				uploadAllegatoItem.setAttribute("nascosto", !showUpload());				
				return showUpload();
			}
		});
		uploadAllegatoItem.setColSpan(1);

		allegatoButtons = new NestedFormItem("allegatoButtons");
		allegatoButtons.setWidth(10);
		allegatoButtons.setOverflow(Overflow.VISIBLE);

		if (((AllegatiItem) getItem()).showVisualizzaFileUdButton()) {

			ImgButtonItem visualizzaFileUdButton = new ImgButtonItem("visualizzaFileUdButton", "allegati.png", "File allegati");
			visualizzaFileUdButton.setAlwaysEnabled(true);
			visualizzaFileUdButton.setColSpan(1);
			visualizzaFileUdButton.setIconWidth(16);
			visualizzaFileUdButton.setIconHeight(16);
			visualizzaFileUdButton.setIconVAlign(VerticalAlignment.BOTTOM);
			visualizzaFileUdButton.setAlign(Alignment.LEFT);
			visualizzaFileUdButton.setWidth(16);
			visualizzaFileUdButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return (idUdAppartenenzaItem.getValue() != null && !idUdAppartenenzaItem.getValue().equals(""));
				}
			});
			visualizzaFileUdButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					visualizzaFileUd();
				}
			});
			
			allegatoButtons.setNumCols(7);
			allegatoButtons.setColWidths("5","1","1","1","1","1","1");
			allegatoButtons.setColSpan(1);
			
			allegatoButtons.setNestedFormItems(visualizzaFileUdButton);			

		} else {

			previewButton = new ImgButtonItem("previewButton", "file/preview.png", I18NUtil.getMessages().protocollazione_detail_previewButton_prompt());
			previewButton.setAlwaysEnabled(true);
			previewButton.setColSpan(1);
			previewButton.setIconWidth(16);
			previewButton.setIconHeight(16);
			previewButton.setIconVAlign(VerticalAlignment.BOTTOM);
			previewButton.setAlign(Alignment.LEFT);
			previewButton.setWidth(16);
			previewButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto() && isAllegatoModelloAtti() && (!isDocFirmato() && !isUdProtocollata())) {
						previewButton.setPrompt("Anteprima da modello");
						return true;
					} else {
						previewButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt());
						if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
							InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
							return PreviewWindow.canBePreviewed(lInfoFileRecord);
						}
						return false;
					}
				}
			});
			previewButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					if(((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto() && isAllegatoModelloAtti() && (!isDocFirmato() && !isUdProtocollata())) {
						clickAnteprimaDaModello();
					} else {
						clickPreviewFile();
					}
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
					if (getEditing() && allegatoButtons.isEditing() && !isModificabileSoloVersOmissis()) {
						if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")
								&& (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))) {
							InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
							return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
						}
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

					if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
						return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
					} else
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

					if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
						return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
					} else
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
					if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
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
			
			dimensioneSignificativaIcon = new ImgButtonItem("dimensioneSignificativaIcon", "warning.png", "Dimensione significativa");
			dimensioneSignificativaIcon.setCellStyle(it.eng.utility.Styles.formCell);
			dimensioneSignificativaIcon.setAlwaysEnabled(true);
			dimensioneSignificativaIcon.setColSpan(1);
			dimensioneSignificativaIcon.setIconWidth(16);
			dimensioneSignificativaIcon.setIconHeight(16);
			dimensioneSignificativaIcon.setIconVAlign(VerticalAlignment.BOTTOM);
			dimensioneSignificativaIcon.setAlign(Alignment.LEFT);
			dimensioneSignificativaIcon.setWidth(16);
			dimensioneSignificativaIcon.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
						InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
						long dimAlertAllegato = ((AllegatiItem) getItem()).getDimAlertAllegato();
						if(dimAlertAllegato > 0 && lInfoFileRecord != null && lInfoFileRecord.getBytes() > dimAlertAllegato) {						
							final float MEGABYTE = 1024L * 1024L;
							float dimensioneInMB = lInfoFileRecord.getBytes() / MEGABYTE;						
							dimensioneSignificativaIcon.setPrompt("Dimensione significativa: " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB");
							return true;
						} 		
					} 
					return false;
				}
			});
			
			downloadOutsideAltreOpMenuButton = new ImgButtonItem("downloadOutsideAltreOpMenuButton", "file/download_manager.png", I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt());
			downloadOutsideAltreOpMenuButton.setCellStyle(it.eng.utility.Styles.formCell);
			downloadOutsideAltreOpMenuButton.setAlwaysEnabled(true);
			downloadOutsideAltreOpMenuButton.setColSpan(1);
			downloadOutsideAltreOpMenuButton.setIconWidth(16);
			downloadOutsideAltreOpMenuButton.setIconHeight(16);
			downloadOutsideAltreOpMenuButton.setIconVAlign(VerticalAlignment.BOTTOM);
			downloadOutsideAltreOpMenuButton.setAlign(Alignment.LEFT);
			downloadOutsideAltreOpMenuButton.setWidth(16);
			downloadOutsideAltreOpMenuButton.addIconClickHandler(new IconClickHandler() {
				
				@Override
				public void onIconClick(IconClickEvent event) {
					InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
					if (lInfoFileRecord != null) {
						// Se è firmato P7M
						if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
							Menu showFirmatoAndSbustato = new Menu();
							MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
							firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									clickDownloadFile();
								}
							});
							MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
							sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									clickDownloadFileSbustato();
								}
							});
							showFirmatoAndSbustato.setItems(firmato, sbustato);
							showFirmatoAndSbustato.showContextMenu();
						} else {
							clickDownloadFile();
						}
					}
				}
			});
			downloadOutsideAltreOpMenuButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("") && isShowDownloadOutsideAltreOperazioniButton();				
				}
			});

			altreOpButton = new ImgButtonItem("altreOpButton", "buttons/altreOp.png", I18NUtil.getMessages().altreOpButton_prompt());
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

					manageAltreOpButtonClick();
				}

			});			
			altreOpButton.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return !isUdSenzaFileImportata() && !isUdAllegato() && ((AllegatiItem) getItem()).showAltreOp();
				}
			});
			
			generaDaModelloButton = new ImgButtonItem("generaDaModelloButton", "protocollazione/generaDaModello.png",
					I18NUtil.getMessages().protocollazione_detail_generaDaModelloButton_prompt()) {
				
				@Override
				public void setCanEdit(Boolean canEdit) {
					super.setCanEdit(forceToShowGeneraDaModelloForTipoTaskDoc ? true : canEdit);						
				}
			};
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
					if(((AllegatiItem) getItem()).showGeneraDaModello() && !isModificabileSoloVersOmissis()) {
						// Il tasto non è visibile se il documento è già protocollato
						if (isUdProtocollata()) {
							return false;
						} else {
							String tipoAllegato = getTipoAllegato();
							return tipoAllegato != null && !"".equals(tipoAllegato);
						}
					}
					return false;
				}
			});
			generaDaModelloButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
//					if(forceToShowGeneraDaModelloForTipoTaskDoc) {
//						generaDaModello();
//					} else if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
//						SC.ask("E' gia presente un file. Vuoi procedere alla generazione sovrascrivendo il file esistente?", new BooleanCallback() {
//
//							@Override
//							public void execute(Boolean value) {
//
//								if (value != null && value) {
//									generaDaModello();
//								}
//							}
//						});
//					} else {
//						generaDaModello();
//					}
					generaDaModello();
				}
			});				

			trasformaInPrimarioButton = new ImgButtonItem("trasformaInPrimarioButton", "buttons/exchange.png", "Trasforma in primario");
			trasformaInPrimarioButton.setAlwaysEnabled(true);
			trasformaInPrimarioButton.setColSpan(1);
			trasformaInPrimarioButton.setIconWidth(16);
			trasformaInPrimarioButton.setIconHeight(16);
			trasformaInPrimarioButton.setIconVAlign(VerticalAlignment.BOTTOM);
			trasformaInPrimarioButton.setAlign(Alignment.LEFT);
			trasformaInPrimarioButton.setWidth(16);
			trasformaInPrimarioButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if(!((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {						
						if (getEditing() && uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")
								&& (((AllegatiItem) getItem()).sonoInMail() || ((AllegatiItem) getItem()).isScansioneImgAssoc())) {
							return true;
						}
					}
					return false;
				}
			});
			trasformaInPrimarioButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					clickTrasformaInPrimario(getHLayout());

				}
			});
			
			// Da mettere solo per CED e AUTOTUTELE
			protocollaInUscitaButton = new ImgButtonItem("protocollaInUscitaButton", "buttons/protocollazione.png", "Protocolla");
			protocollaInUscitaButton.setAlwaysEnabled(true);
			protocollaInUscitaButton.setColSpan(1);
			protocollaInUscitaButton.setIconWidth(16);
			protocollaInUscitaButton.setIconHeight(16);
			protocollaInUscitaButton.setIconVAlign(VerticalAlignment.BOTTOM);
			protocollaInUscitaButton.setAlign(Alignment.LEFT);
			protocollaInUscitaButton.setWidth(16);
			protocollaInUscitaButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					// FIXME Devo verificare che non sia già protocollato
					if (!isUdProtocollata() && uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
						 InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
						// FIXME Mettere controllo firma valida?
						return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && isUdDocIstruttoriaCedAutotutela();
					} else
						return false;
				}
			});
			protocollaInUscitaButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					Record record = mDynamicForm.getValuesAsRecord();
					((AllegatiItem) getItem()).clickProtocolla(record, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							((AllegatiItem) getItem()).afterClickProtocolla(object);
						}
					});
				}
			});
			
			List<FormItem> allegatoFormItems = new ArrayList<FormItem>();
			allegatoFormItems.add(generaDaModelloButton);
			allegatoFormItems.add(previewButton);
			allegatoFormItems.add(previewEditButton);
			allegatoFormItems.add(editButton);
			allegatoFormItems.add(fileFirmatoDigButton);
			allegatoFormItems.add(firmaNonValidaButton);
			allegatoFormItems.add(fileMarcatoTempButton);
			allegatoFormItems.add(dimensioneSignificativaIcon);
			allegatoFormItems.add(downloadOutsideAltreOpMenuButton);
			allegatoFormItems.add(altreOpButton);
			allegatoFormItems.add(trasformaInPrimarioButton);
			allegatoFormItems.add(protocollaInUscitaButton);
			
			allegatoButtons.setColSpan(1);
			allegatoButtons.setNumCols(10);
			allegatoButtons.setColWidths(16, 16, 16, 16, 16, 16, 16, 16, 16, 16);

			allegatoButtons.setNestedFormItems(allegatoFormItems.toArray(new FormItem[allegatoFormItems.size()]));

		}

		infoFileItem = new HiddenItem("infoFile");
		infoFileItem.setVisible(false);
		
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
				if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					String impronta = lInfoFileRecord != null && lInfoFileRecord.getImpronta() != null ? lInfoFileRecord.getImpronta() : "";
					improntaItem.setValue(impronta);
					return impronta != null && !"".equals(impronta) && AurigaLayout.getParametroDBAsBoolean("SHOW_IMPRONTA_FILE");
				} else {
					improntaItem.setValue("");
					return false;
				}
			}
		});
		
		tipoBarcodeContrattoItem = new HiddenItem("tipoBarcodeContratto");
		barcodeContrattoItem = new HiddenItem("barcodeContratto");
		energiaGasContrattoItem = new HiddenItem("energiaGasContratto");
		tipoSezioneContrattoItem = new HiddenItem("tipoSezioneContratto");
		codContrattoItem = new HiddenItem("codContratto");
		flgPresentiFirmeContrattoItem = new HiddenItem("flgPresentiFirmeContratto");
		flgFirmeCompleteContrattoItem = new HiddenItem("flgFirmeCompleteContratto");
		nroFirmePrevisteContrattoItem = new HiddenItem("nroFirmePrevisteContratto");
		nroFirmeCompilateContrattoItem = new HiddenItem("nroFirmeCompilateContratto");		
		flgIncertezzaRilevazioneFirmeContrattoItem = new HiddenItem("flgIncertezzaRilevazioneFirmeContratto");		
		
		dettaglioDocContrattiBarcodeButton = new ImgButtonItem("dettaglioDocContrattiBarcodeButton", "buttons/altriDati.png",	"Dettaglio doc. contratti");
		dettaglioDocContrattiBarcodeButton.setAlwaysEnabled(true);
		dettaglioDocContrattiBarcodeButton.setColSpan(1);
		dettaglioDocContrattiBarcodeButton.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();				
				String flgTipoProv = getFlgTipoProvProtocollo();			
				if(flgTipoProv != null && "E".equals(flgTipoProv) && AurigaLayout.isAttivoClienteA2A()) {
					boolean flgDocContrattiBarcode = detailRecord != null && detailRecord.getAttributeAsBoolean("flgDocContrattiBarcode") != null && detailRecord.getAttributeAsBoolean("flgDocContrattiBarcode");				
					return flgDocContrattiBarcode;
				}
				return false;				
			}
		});
		dettaglioDocContrattiBarcodeButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record allegatoRecord = new Record(mDynamicForm.getValues());
				Record lRecordDettDocContratti = new Record();
				lRecordDettDocContratti.setAttribute("tipoBarcode", allegatoRecord.getAttribute("tipoBarcodeContratto"));
				lRecordDettDocContratti.setAttribute("barcode", allegatoRecord.getAttribute("barcodeContratto"));
				lRecordDettDocContratti.setAttribute("energiaGas", allegatoRecord.getAttribute("energiaGasContratto"));
				lRecordDettDocContratti.setAttribute("tipoSezioneContratto", allegatoRecord.getAttribute("tipoSezioneContratto"));
				lRecordDettDocContratti.setAttribute("codContratto", allegatoRecord.getAttribute("codContratto"));
				lRecordDettDocContratti.setAttribute("flgPresentiFirmeContratto", allegatoRecord.getAttribute("flgPresentiFirmeContratto"));
				lRecordDettDocContratti.setAttribute("flgFirmeCompleteContratto", allegatoRecord.getAttribute("flgFirmeCompleteContratto"));
				lRecordDettDocContratti.setAttribute("nroFirmePrevisteContratto", allegatoRecord.getAttribute("nroFirmePrevisteContratto"));
				lRecordDettDocContratti.setAttribute("nroFirmeCompilateContratto", allegatoRecord.getAttribute("nroFirmeCompilateContratto"));
				lRecordDettDocContratti.setAttribute("flgIncertezzaRilevazioneFirmeContratto", allegatoRecord.getAttribute("flgIncertezzaRilevazioneFirmeContratto"));
				DettaglioDocContrattiPopup lDettaglioDocContrattiPopup = new DettaglioDocContrattiPopup(lRecordDettDocContratti);
				lDettaglioDocContrattiPopup.show();
			}
		});
		
		opzioniTimbroItem = new HiddenItem("opzioniTimbro");
		
		flgProvEsternaItem = new CheckboxItem("flgProvEsterna", "provenienza esterna&nbsp;");
		flgProvEsternaItem.setColSpan(1);
		flgProvEsternaItem.setWidth("*");
		// flgProvEsternaItem.setLabelAsTitle(true);
		flgProvEsternaItem.setShowTitle(true);
		flgProvEsternaItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgProvEsternaItem.setStartRow(true);			
		}
		flgProvEsternaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgProvEsterna();
			}
		});
		
		flgParereItem = new CheckboxItem("flgParere", "parere&nbsp;");
		flgParereItem.setColSpan(1);
		flgParereItem.setWidth("*");
		// flgParereItem.setLabelAsTitle(true);
		flgParereItem.setShowTitle(true);
		flgParereItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgParereItem.setStartRow(true);			
		}
		flgParereItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				flgParereItem.setCanEdit(false);
				return showFlgParere() && isParere();
			}
		});

		boolean flgParteDispositivoDefaultValue = false;
		if(showFlgParteDispositivo() && !((AllegatiItem) getItem()).isDocumentiIstruttoria()) {
			// Controllo se devo settare il valore di default di parte integrante a true
			// Vale true se ((readOnly && abilitazione all'esclusione vale true) || (!readOnly && FLG_ALLEG_ATTO_PARTE_INT_DEFAULT vale true))
			boolean flgAllegAttoParteIntDefault = ((AllegatiItem) getItem()).getFlgAllegAttoParteIntDefault();
			boolean isAttivaModificaEsclusionePubblicazione = ((AllegatiItem) getItem()).isAttivaModificaEsclusionePubblicazione();
			if(isSoloOmissisModProprietaFileMode() && !((AllegatiItem) getItem()).isAllegatiNonParteIntegranteNonEditabili()) {
				flgParteDispositivoDefaultValue = false;
			} else if((flgAllegAttoParteIntDefault && !isReadOnly()) || ((isReadOnly() && isAttivaModificaEsclusionePubblicazione))){
				flgParteDispositivoDefaultValue = true;
			} else {
				flgParteDispositivoDefaultValue = false;
			}			
		}
		
		flgParteDispositivoItem = new CheckboxItem("flgParteDispositivo", ((AllegatiItem) getItem()).getTitleFlgParteDispositivo() + "&nbsp;");
		flgParteDispositivoItem.setValue(flgParteDispositivoDefaultValue);
		flgParteDispositivoItem.setColSpan(1);
		flgParteDispositivoItem.setWidth("*");
		// flgParteDispositivoItem.setLabelAsTitle(true);
		flgParteDispositivoItem.setShowTitle(true);
		flgParteDispositivoItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgParteDispositivoItem.setStartRow(true);			
		}
		flgParteDispositivoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgParere() && isParere()) {
					flgParteDispositivoItem.setValue(false);
					return false;
				} else {
					return showFlgParteDispositivo();
				}
			}
		});
		flgParteDispositivoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
				String rifiutoAllegatiConFirmeNonValide = ((AllegatiItem) getItem()).getRifiutoAllegatiConFirmeNonValide();
				boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
				boolean flgNoPubblAllegato = mDynamicForm.getValue("flgNoPubblAllegato") != null && new Boolean(mDynamicForm.getValueAsString("flgNoPubblAllegato"));
				boolean flgPubblicaSeparato = isFlgPubblicaSeparato();				
				boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null && new Boolean(mDynamicForm.getValueAsString("flgDatiSensibili"));
				boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;					
				if (!flgParteDispositivo) {
					flgNoPubblAllegato = true;
					mDynamicForm.setValue("flgNoPubblAllegato", true);
					flgPubblicaSeparato = false;
					mDynamicForm.clearValue("flgPubblicaSeparato");
					if(showFileOmissis) {
						flgDatiSensibili = false;
						showFileOmissis = false;
						mDynamicForm.setValue("flgDatiSensibili", false);
						manageOnChangedFlgDatiSensibili();
					}
				} else {
					flgNoPubblAllegato = false;
					mDynamicForm.clearValue("flgNoPubblAllegato");
					if(flgParteDispositivo && showFlgPubblicaSeparato() && ((AllegatiItem) getItem()).getFlgAllegAttoPubblSepDefault()) {
						flgPubblicaSeparato = true;
						mDynamicForm.setValue("flgPubblicaSeparato", true);
					}
				}
				InfoFileRecord infoFileAllegato = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;			
				if(flgParteDispositivo && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(infoFileAllegato)) {
					GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING));
//					clickEliminaFile();	
				}	
				if (flgParteDispositivo && !showFileOmissis && infoFileAllegato != null && !PreviewWindow.isPdfConvertibile(infoFileAllegato) && !flgPubblicaSeparato) {
					if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
						mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
					} 
					if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
						if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
							((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
						} else {
							mDynamicForm.setValue("flgPubblicaSeparato", true);
						}
					}
					GWTRestDataSource.printMessage(new MessageBean("File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));
				}
				if (infoFileAllegato != null && infoFileAllegato.isFirmato()) {
					if(!infoFileAllegato.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null) {
						if("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
							GWTRestDataSource.printMessage(new MessageBean("Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante", "", MessageType.WARNING));					
						}
					} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
						if(flgParteDispositivo && !showFileOmissis && !flgNoPubblAllegato) {
							if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
								mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
							} 
							if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
								if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
									((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
								} else {
									mDynamicForm.setValue("flgPubblicaSeparato", true);														
								}
							}
//							GWTRestDataSource.printMessage(new MessageBean("Il file è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING));					
						}
					}
				}
				boolean pubblicazioneSeparataPdfProtetti = ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti();
				if (flgParteDispositivo && !showFileOmissis && pubblicazioneSeparataPdfProtetti && infoFileAllegato != null && infoFileAllegato.isPdfProtetto() && !flgPubblicaSeparato) {
					if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
						mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
					} 
					if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
						if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
							((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
						} else {
							mDynamicForm.setValue("flgPubblicaSeparato", true);
						}
					}
					GWTRestDataSource.printMessage(new MessageBean("File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));
				}
				final float MEGABYTE = 1024L * 1024L;
				long dimAlertAllegato = ((AllegatiItem) getItem()).getDimAlertAllegato(); // questo è in bytes
				long dimMaxAllegatoXPubblInMB = ((AllegatiItem) getItem()).getDimMaxAllegatoXPubbl(); // questa è in MB
				if(dimMaxAllegatoXPubblInMB > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE)) {
					if(flgParteDispositivo && !showFileOmissis) {
						if(((AllegatiItem)getItem()).isShowFlgNoPubblAllegato()) {
							mDynamicForm.setValue("flgNoPubblAllegato", true);
						}	
						if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
							mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
						} 
						if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
							if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
								((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
							} else {
								mDynamicForm.setValue("flgPubblicaSeparato", true);
							}
						}
						GWTRestDataSource.printMessage(new MessageBean("La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING));
					}	
				} else if(dimAlertAllegato > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > dimAlertAllegato) {						
					float dimensioneInMB = infoFileAllegato.getBytes() / MEGABYTE;						
					if(flgParteDispositivo && !showFileOmissis && !flgNoPubblAllegato) {
						if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
							mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
						} 
						if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
							if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
								((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
							} else {
								mDynamicForm.setValue("flgPubblicaSeparato", true);
							}
						}
						GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING));
					} else {						
						GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING));
					}
					mDynamicForm.markForRedraw();
				}
				if(showFileOmissis) {
					InfoFileRecord infoFileOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;
					if(flgParteDispositivo && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(infoFileOmissis)) {
						GWTRestDataSource.printMessage(new MessageBean("Il file omissis risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING));
//						fileOmissisButtons.clickEliminaFile();
					}
					if (flgParteDispositivo && infoFileOmissis!= null && !PreviewWindow.isPdfConvertibile(infoFileOmissis) && !flgPubblicaSeparato) {
						if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
							mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
						} 
						if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
							if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
								((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
							} else {
								mDynamicForm.setValue("flgPubblicaSeparato", true);												
							}
						}
						GWTRestDataSource.printMessage(new MessageBean("File omissis non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));
					}	
					if (infoFileOmissis!= null && infoFileOmissis.isFirmato()) {
						if(!infoFileOmissis.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
							if("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
								GWTRestDataSource.printMessage(new MessageBean("Il file omissis presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante", "", MessageType.WARNING));
							}
						} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
							if(flgParteDispositivo && !flgNoPubblAllegato) {
								if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
									mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
								} 
								if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
									if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
										((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
									} else {
										mDynamicForm.setValue("flgPubblicaSeparato", true);														
									}
								}
//								GWTRestDataSource.printMessage(new MessageBean("Il file omissis è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING));					
							}
						}
					}	
					if (flgParteDispositivo && pubblicazioneSeparataPdfProtetti && infoFileOmissis!= null && infoFileOmissis.isPdfProtetto() && !flgPubblicaSeparato) {
						if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
							mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
						} 
						if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
							if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
								((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
							} else {
								mDynamicForm.setValue("flgPubblicaSeparato", true);												
							}
						}
						GWTRestDataSource.printMessage(new MessageBean("File omissis protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));
					}			
					if(dimMaxAllegatoXPubblInMB > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE)) {						
						if(flgParteDispositivo) {
							if(((AllegatiItem)getItem()).isShowFlgNoPubblAllegato()) {
								mDynamicForm.setValue("flgNoPubblAllegato", true);																				
							}	
							if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
								mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
							} 
							if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
								if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
									((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
								} else {
									mDynamicForm.setValue("flgPubblicaSeparato", true);												
								}
							}
							GWTRestDataSource.printMessage(new MessageBean("La dimensione del file omissis è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING));
						}	
					} else if(dimAlertAllegato > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > dimAlertAllegato) {						
						float dimensioneInMBOmissis = infoFileOmissis.getBytes() / MEGABYTE;						
						if(flgParteDispositivo && !flgNoPubblAllegato) {
							if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
								mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
							} 
							if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
								if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
									((AllegatiItem)getItem()).setFlgPubblicaAllegatiSeparati(true);
								} else {
									mDynamicForm.setValue("flgPubblicaSeparato", true);														
								}
							}
							GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file omissis è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMBOmissis) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING));
						} else {						
							GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file omissis è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMBOmissis) + " MB", "", MessageType.WARNING));
						}
						mDynamicForm.markForRedraw();					
					}
				}				
				manageChangedFileAllegatoParteDispositivo(false);
			}
		});

		flgParteDispositivoSalvatoItem = new HiddenItem("flgParteDispositivoSalvato");
		flgParteDispositivoSalvatoItem.setValue(false);

		flgNoPubblAllegatoItem = new CheckboxItem("flgNoPubblAllegato", ((AllegatiItem) getItem()).getTitleFlgNoPubblAllegato() + "&nbsp;");
		if (showFlgParteDispositivo() && !flgParteDispositivoDefaultValue) {
			flgNoPubblAllegatoItem.setValue(true);
		}
		flgNoPubblAllegatoItem.setColSpan(1);
		flgNoPubblAllegatoItem.setWidth("*");
		// flgNoPubblAllegatoItem.setLabelAsTitle(true);
		flgNoPubblAllegatoItem.setShowTitle(true);
		flgNoPubblAllegatoItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgNoPubblAllegatoItem.setStartRow(true);			
		}
		flgNoPubblAllegatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (((AllegatiItem) getItem()).isCanEditOnlyOmissis()) {
					flgNoPubblAllegatoItem.setDisabled(false);
					flgNoPubblAllegatoItem.setCanEdit(true);
				} else {
					if(showFlgParere() && isParere()) {
						flgNoPubblAllegatoItem.setDisabled(false);
	//					flgNoPubblAllegatoItem.setCanEdit(getEditing());						
					} else if(showFlgParteDispositivo()) {					
						boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
						if (flgParteDispositivo) {
							// Controllo se ho forzato la possibilità di escludere i file dalla pubblicazione anche se in readOnly
							boolean attivaModificaEsclusionePubblicazione = ((AllegatiItem) getItem()).isAttivaModificaEsclusionePubblicazione();
							if (attivaModificaEsclusionePubblicazione){
								flgNoPubblAllegatoItem.setDisabled(false);
								flgNoPubblAllegatoItem.setCanEdit(true);
							} else if (((AllegatiItem) getItem()).getCanEdit()) {
								flgNoPubblAllegatoItem.setDisabled(false);
	//							flgNoPubblAllegatoItem.setCanEdit(true);
							}										
						} else {
							flgNoPubblAllegatoItem.setDisabled(true);
	//						flgNoPubblAllegatoItem.setCanEdit(false);
						}
					}
				}
				return showFlgNoPubblAllegato();
			}
		});
		
		boolean flgPubblicaSeparatoDefaultValue = false;
		if(showFlgPubblicaSeparato() && flgParteDispositivoDefaultValue) {
			flgPubblicaSeparatoDefaultValue = ((AllegatiItem) getItem()).getFlgAllegAttoPubblSepDefault();
		}
		
		flgPubblicaSeparatoItem = new CheckboxItem("flgPubblicaSeparato", ((AllegatiItem) getItem()).getTitleFlgPubblicaSeparato() + "&nbsp;") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				// se è il parametro SEPARAZ_ALLEG_PI_ATTO_SOLO_AUTOMATICA = true per gli allegati parte integrante il check di "separato" è sempre disabilitato, anche se valgono gli automatismi soliti (separazione se sopra soglia, se di formato non pdfizzabile, se file firmato)
				super.setCanEdit(AurigaLayout.getParametroDBAsBoolean("SEPARAZ_ALLEG_PI_ATTO_SOLO_AUTOMATICA") ? false : canEdit);
			}
		};
		flgPubblicaSeparatoItem.setValue(flgPubblicaSeparatoDefaultValue);
		flgPubblicaSeparatoItem.setColSpan(1);
		flgPubblicaSeparatoItem.setWidth("*");
		// flgPubblicaSeparatoItem.setLabelAsTitle(true);
		flgPubblicaSeparatoItem.setShowTitle(true);
		flgPubblicaSeparatoItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgPubblicaSeparatoItem.setStartRow(true);			
		}
		if(((AllegatiItem)getItem()).isAttivaSceltaPosizioneAllegatiUniti()) {
			flgPubblicaSeparatoItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					mDynamicForm.markForRedraw();
				}
			});
		}
		flgPubblicaSeparatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {	
				if(showFlgParere() && isParere()) {
					flgPubblicaSeparatoItem.setValue(true);
					flgPubblicaSeparatoItem.setDisabled(true);
//					flgPubblicaSeparatoItem.setCanEdit(false);
					return showFlgPubblicaSeparato();
				} else if(showFlgParteDispositivo()) {		
					boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
					flgPubblicaSeparatoItem.setDisabled(false);
//					flgPubblicaSeparatoItem.setCanEdit(getEditing());
					if (flgParteDispositivo) {
						boolean pubblicazioneSeparataPdfProtetti = ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti();
						InfoFileRecord infoOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;
						InfoFileRecord info = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
						boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
						boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;		
						if(showFileOmissis && infoOmissis != null) {
							if(!PreviewWindow.isPdfConvertibile(infoOmissis)) {
								flgPubblicaSeparatoItem.setDisabled(true);
//								flgPubblicaSeparatoItem.setCanEdit(false);
							}
							if(pubblicazioneSeparataPdfProtetti && infoOmissis.isPdfProtetto()) {
								flgPubblicaSeparatoItem.setDisabled(true);
//								flgPubblicaSeparatoItem.setCanEdit(false);
							}
						} else if(!showFileOmissis && info != null) {
							if(!PreviewWindow.isPdfConvertibile(info)) {
								flgPubblicaSeparatoItem.setDisabled(true);
//								flgPubblicaSeparatoItem.setCanEdit(false);
							}
							if(pubblicazioneSeparataPdfProtetti && info.isPdfProtetto()) {
								flgPubblicaSeparatoItem.setDisabled(true);
//								flgPubblicaSeparatoItem.setCanEdit(false);
							}
						}	
					}
					return flgParteDispositivo && showFlgPubblicaSeparato();
				}
				return showFlgPubblicaSeparato();
			}
		});
		
		flgPubblicaAllegatiSeparatiInAllegatiGridItem = new HiddenItem("flgPubblicaAllegatiSeparatiInAllegatiGrid");
		flgPubblicaAllegatiSeparatiInAllegatiGridItem.setValue(false);
		
		flgPaginaFileUnioneItem = new RadioGroupItem("flgPaginaFileUnione", "Unione dopo pag.");
		flgPaginaFileUnioneItem.setValueMap("ultima", "n.ro");
		flgPaginaFileUnioneItem.setDefaultValue("ultima");
		flgPaginaFileUnioneItem.setVertical(false);
		flgPaginaFileUnioneItem.setWrap(false);
		flgPaginaFileUnioneItem.setShowDisabled(false);
		flgPaginaFileUnioneItem.setRequired(true);			
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgPaginaFileUnioneItem.setStartRow(true);			
		}
		flgPaginaFileUnioneItem.setColSpan(1);		
		flgPaginaFileUnioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgPaginaFileUnione()) {
					return true;
				} else {
					mDynamicForm.setValue("flgPaginaFileUnione", "ultima");
					mDynamicForm.setValue("nroPagFileUnione", "");
					mDynamicForm.setValue("nroPagFileUnioneOmissis", "");
					return false;
				}			
			}
		});
		flgPaginaFileUnioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(event.getValue() != null && "ultima".equals((String) event.getValue())) {
					mDynamicForm.setValue("nroPagFileUnione", "");
					mDynamicForm.setValue("nroPagFileUnioneOmissis", "");
				}				
				mDynamicForm.markForRedraw();
			}
		});
		
		nroPagFileUnioneItem = new ExtendedTextItem("nroPagFileUnione");
		nroPagFileUnioneItem.setWidth(100);
		nroPagFileUnioneItem.setLength(50);
		nroPagFileUnioneItem.setShowTitle(false);
		nroPagFileUnioneItem.setHint("(vers. integrale)");
		nroPagFileUnioneItem.setKeyPressFilter("[0-9]");
		nroPagFileUnioneItem.setAttribute("obbligatorio", true);
		nroPagFileUnioneItem.setColSpan(1);
		nroPagFileUnioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if(showFlgPaginaFileUnione()) {
					String flgPaginaFileUnione = mDynamicForm.getValue("flgPaginaFileUnione") != null ? mDynamicForm.getValueAsString("flgPaginaFileUnione") : "";				
					return flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione);
				}
				return false;
			}
		});
		nroPagFileUnioneItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				String nroPagFileUnione = (String) event.getValue();
				if(nroPagFileUnione != null && !"".equals(nroPagFileUnione)) {
					String nroPagFileUnioneOmissis = (String) mDynamicForm.getValue("nroPagFileUnioneOmissis");
					if(nroPagFileUnioneOmissis == null || "".equals(nroPagFileUnioneOmissis.trim())) {
						mDynamicForm.setValue("nroPagFileUnioneOmissis", nroPagFileUnione);
					}			
				}
			}
		});
		nroPagFileUnioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showFlgPaginaFileUnione()) {
					String flgPaginaFileUnione = mDynamicForm.getValue("flgPaginaFileUnione") != null ? mDynamicForm.getValueAsString("flgPaginaFileUnione") : "";				
					return flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione);
				}
				return false;
			}
		}));
		
		nroPagFileUnioneOmissisItem = new TextItem("nroPagFileUnioneOmissis");
		nroPagFileUnioneOmissisItem.setWidth(100);
		nroPagFileUnioneOmissisItem.setLength(50);
		nroPagFileUnioneOmissisItem.setShowTitle(false);
		nroPagFileUnioneOmissisItem.setHint("(vers. per pubbl.)");
		nroPagFileUnioneOmissisItem.setKeyPressFilter("[0-9]");
		nroPagFileUnioneOmissisItem.setAttribute("obbligatorio", true);
		nroPagFileUnioneOmissisItem.setColSpan(1);
		nroPagFileUnioneOmissisItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if(showFlgPaginaFileUnione()) {
					String flgPaginaFileUnione = mDynamicForm.getValue("flgPaginaFileUnione") != null ? mDynamicForm.getValueAsString("flgPaginaFileUnione") : "";				
					return flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione) && ((AllegatiItem) getItem()).getShowVersioneOmissis();
				}
				return false;
			}
		});
		nroPagFileUnioneOmissisItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showFlgPaginaFileUnione()) {
					String flgPaginaFileUnione = mDynamicForm.getValue("flgPaginaFileUnione") != null ? mDynamicForm.getValueAsString("flgPaginaFileUnione") : "";				
					return flgPaginaFileUnione != null && "n.ro".equals(flgPaginaFileUnione) && ((AllegatiItem) getItem()).getShowVersioneOmissis();
				}
				return false;
			}
		}));		

		flgDatiProtettiTipo1Item = new CheckboxItem("flgDatiProtettiTipo1", ((AllegatiItem) getItem()).getTitleFlgDatiProtettiTipo1() + "&nbsp;");
		flgDatiProtettiTipo1Item.setValue(false);
		flgDatiProtettiTipo1Item.setColSpan(1);
		flgDatiProtettiTipo1Item.setWidth("*");
		// flgDatiProtettiTipo1Item.setLabelAsTitle(true);
		flgDatiProtettiTipo1Item.setShowTitle(true);
		flgDatiProtettiTipo1Item.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgDatiProtettiTipo1Item.setStartRow(true);			
		}
		flgDatiProtettiTipo1Item.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo1();
			}
		});
		
		flgDatiProtettiTipo2Item = new CheckboxItem("flgDatiProtettiTipo2", ((AllegatiItem) getItem()).getTitleFlgDatiProtettiTipo2() + "&nbsp;");
		flgDatiProtettiTipo2Item.setValue(false);
		flgDatiProtettiTipo2Item.setColSpan(1);
		flgDatiProtettiTipo2Item.setWidth("*");
		// flgDatiProtettiTipo2Item.setLabelAsTitle(true);
		flgDatiProtettiTipo2Item.setShowTitle(true);
		flgDatiProtettiTipo2Item.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgDatiProtettiTipo2Item.setStartRow(true);			
		}
		flgDatiProtettiTipo2Item.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo2();
			}
		});
		
		flgDatiProtettiTipo3Item = new CheckboxItem("flgDatiProtettiTipo3", ((AllegatiItem) getItem()).getTitleFlgDatiProtettiTipo3() + "&nbsp;");
		flgDatiProtettiTipo3Item.setValue(false);
		flgDatiProtettiTipo3Item.setColSpan(1);
		flgDatiProtettiTipo3Item.setWidth("*");
		// flgDatiProtettiTipo3Item.setLabelAsTitle(true);
		flgDatiProtettiTipo3Item.setShowTitle(true);
		flgDatiProtettiTipo3Item.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgDatiProtettiTipo3Item.setStartRow(true);			
		}
		flgDatiProtettiTipo3Item.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo3();
			}
		});
		
		flgDatiProtettiTipo4Item = new CheckboxItem("flgDatiProtettiTipo4", ((AllegatiItem) getItem()).getTitleFlgDatiProtettiTipo4() + "&nbsp;");
		flgDatiProtettiTipo4Item.setValue(false);
		flgDatiProtettiTipo4Item.setColSpan(1);
		flgDatiProtettiTipo4Item.setWidth("*");
		// flgDatiProtettiTipo4Item.setLabelAsTitle(true);
		flgDatiProtettiTipo4Item.setShowTitle(true);
		flgDatiProtettiTipo4Item.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgDatiProtettiTipo4Item.setStartRow(true);			
		}
		flgDatiProtettiTipo4Item.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo4();
			}
		});

		flgDatiSensibiliItem = new CheckboxItem("flgDatiSensibili", ((AllegatiItem) getItem()).getTitleFlgDatiSensibili() + "&nbsp;");
		flgDatiSensibiliItem.setValue(false);
		flgDatiSensibiliItem.setColSpan(1);
		flgDatiSensibiliItem.setWidth("*");
		// flgDatiSensibiliItem.setLabelAsTitle(true);
		flgDatiSensibiliItem.setShowTitle(true);
		flgDatiSensibiliItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgDatiSensibiliItem.setStartRow(true);			
		}
		flgDatiSensibiliItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgParere() && isParere()) {
					flgDatiSensibiliItem.setDisabled(false);
					return ((AllegatiItem) getItem()).getShowVersioneOmissis();
				} else if(showFlgParteDispositivo()) {
					boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
					if (flgParteDispositivo) {
						flgDatiSensibiliItem.setDisabled(false);
					} else {
						flgDatiSensibiliItem.setDisabled(true);
					}
					return flgParteDispositivo && ((AllegatiItem) getItem()).getShowVersioneOmissis();				
				}
				return ((AllegatiItem) getItem()).getShowVersioneOmissis();
			}
		});
		flgDatiSensibiliItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageOnChangedFlgDatiSensibili();
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
				if (!((AllegatiItem) getItem()).isProtInModalitaWizard()) {
					return false;
				}
				boolean isDigitale = ((AllegatiItem) getItem()).isCanaleSupportoDigitale();
				boolean isCartaceo = ((AllegatiItem) getItem()).isCanaleSupportoCartaceo();
				if (isDigitale) {
					mDynamicForm.setValue("flgOriginaleCartaceo", false);
				}
				if (isCartaceo) {
					mDynamicForm.setValue("flgOriginaleCartaceo", true);
				}
				return !isDigitale && !isCartaceo;
			}
		});
		flgOriginaleCartaceoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
				((AllegatiItem) getItem()).manageOnChanged();
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
				if (isUdSenzaFileImportata()) {
					return false;
				}
				if (isUdAllegato()) {
					return false;
				}
				if (!((AllegatiItem) getItem()).isProtInModalitaWizard()) {
					return false;
				}
				boolean isDigitale = ((AllegatiItem) getItem()).isCanaleSupportoDigitale();
				return !isDigitale && (mDynamicForm.getValue("flgOriginaleCartaceo") != null && (Boolean) mDynamicForm.getValue("flgOriginaleCartaceo"));
			}
		});
		flgCopiaSostitutivaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((AllegatiItem) getItem()).manageOnChanged();
			}
		});
		
		flgTimbratoFilePostRegItem = new HiddenItem("flgTimbratoFilePostReg");		
		
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
				if(event.getValue() != null && (Boolean)event.getValue()){
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					//Verifico se il file è timbrabile
					if (canBeTimbrato(lInfoFileRecord)) {
						if(!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")){
							showOpzioniTimbratura();						
						} 
					} else {
						Layout.addMessage(new MessageBean("Il formato del file non ne consente la timbratura", "", MessageType.WARNING));
						flgTimbraFilePostRegItem.setValue(false);
					}
				}	
			}
		});
		
		estremiProtUdItem = new TextItem("estremiProtUd", "Registrato come") {
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};				
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			estremiProtUdItem.setStartRow(true);
			estremiProtUdItem.setShowTitle(true);
			estremiProtUdItem.setWidth(650);
			estremiProtUdItem.setColSpan(30);
		} else {
			if(((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto()) {
				// se è un doc. istruttoria dett. fascicolo gen. completo non devo mostrare la label
				estremiProtUdItem.setShowTitle(false);
				estremiProtUdItem.setShowHint(true);
				estremiProtUdItem.setShowHintInField(true);
				estremiProtUdItem.setHint("Registrato come");		
			} else {
				estremiProtUdItem.setShowTitle(true);				
			}
			estremiProtUdItem.setWidth(200);					
		}	
		estremiProtUdItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto()) {
					return true; // se è un doc. istruttoria dett. fascicolo gen. completo lo mostro sempre, anche se non valorizzato
				}
				return (isUdDocIstruttoriaCedAutotutela() || isDocumentiInizialiIstanza() || isUdDocPraticaVisura()) && isUdProtocollata();
			}
		});
		
		dettaglioEstremiProtocolloButton = new ImgButtonItem("dettaglioEstremiProtocolloButton", "buttons/detail.png", "Apri dettaglio documento");
		dettaglioEstremiProtocolloButton.setShowTitle(false);
		dettaglioEstremiProtocolloButton.setAlwaysEnabled(true);
		dettaglioEstremiProtocolloButton.setStartRow(false);
		dettaglioEstremiProtocolloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				dettaglioProtocollo();
			}
		});
		dettaglioEstremiProtocolloButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isUdProtocollata() && isUdDocIstruttoriaCedAutotutela()) || isUdDocPraticaVisura() || isUdDocIstruttoriaDettFascicoloGenCompleto();
			}
		});
					
		protocollazioneEntrataButton = new ImgButtonItem("protocollazioneEntrataButton", "menu/protocollazione_entrata.png", I18NUtil.getMessages().protocollazione_detail_protocollazione_entrata_button());
		protocollazioneEntrataButton.setShowTitle(false);
		protocollazioneEntrataButton.setAlwaysEnabled(true);
		protocollazioneEntrataButton.setStartRow(false);
		protocollazioneEntrataButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickProtocollazioneEntrata();
			}
		});
		protocollazioneEntrataButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showProtocollazioneButtons() && !isUdProtocollata() && isAbilToProt("E");
			}
		});
		
		protocollazioneUscitaButton = new ImgButtonItem("protocollazioneUscitaButton", "menu/protocollazione_uscita.png", I18NUtil.getMessages().protocollazione_detail_protocollazione_uscita_button());
		protocollazioneUscitaButton.setShowTitle(false);
		protocollazioneUscitaButton.setAlwaysEnabled(true);
		protocollazioneUscitaButton.setStartRow(false);
		protocollazioneUscitaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickProtocollazioneUscita();
			}
		});
		protocollazioneUscitaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showProtocollazioneButtons() && !isUdProtocollata() && isAbilToProt("U");
			}
		});
		
		protocollazioneInternaButton = new ImgButtonItem("protocollazioneInternaButton", "menu/protocollazione_interna.png", I18NUtil.getMessages().protocollazione_detail_protocollazione_interna_button());
		protocollazioneInternaButton.setShowTitle(false);
		protocollazioneInternaButton.setAlwaysEnabled(true);
		protocollazioneInternaButton.setStartRow(false);
		protocollazioneInternaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickProtocollazioneInterna();
			}
		});
		protocollazioneInternaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showProtocollazioneButtons() && !isUdProtocollata() && isAbilToProt("I");
			}
		});
		
		dettaglioIstanzaButton = new ImgButtonItem("dettaglioIstanzaButton", "buttons/detail.png", "Apri dettaglio istanza");
		dettaglioIstanzaButton.setAlwaysEnabled(true);
		dettaglioIstanzaButton.setColSpan(1);
		dettaglioIstanzaButton.setIconWidth(16);
		dettaglioIstanzaButton.setIconHeight(16);
		dettaglioIstanzaButton.setIconVAlign(VerticalAlignment.BOTTOM);
		dettaglioIstanzaButton.setAlign(Alignment.LEFT);
		dettaglioIstanzaButton.setWidth(16);
		dettaglioIstanzaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (idUdAppartenenzaItem.getValue() != null && !idUdAppartenenzaItem.getValue().equals("")) && ((AllegatiItem) getItem()).isDocumentiInizialiIstanza();
			}
		});
		dettaglioIstanzaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				visualizzaDettaglioIstanza();
			}
		});
		
		dataRicezioneItem = new DateItem("dataRicezione", I18NUtil.getMessages().protocollazione_detail_dataRicezioneItem_title()) {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		dataRicezioneItem.setColSpan(1);
		dataRicezioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isDocumentiInizialiIstanza() && value != null;
			}
		});
		
		gestSepAllegatiUDButton = new ImgButtonItem("gestSepAllegatiUDButton", "allegati.png", "Allegati");
		gestSepAllegatiUDButton.setAlwaysEnabled(true);
		gestSepAllegatiUDButton.setColSpan(1);
		gestSepAllegatiUDButton.setIconWidth(16);
		gestSepAllegatiUDButton.setIconHeight(16);
		gestSepAllegatiUDButton.setIconVAlign(VerticalAlignment.BOTTOM);
		gestSepAllegatiUDButton.setAlign(Alignment.LEFT);
		gestSepAllegatiUDButton.setWidth(16);
		gestSepAllegatiUDButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgIdUdAppartenenzaContieneAllegati = mDynamicForm.getValueAsString("flgIdUdAppartenenzaContieneAllegati") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgIdUdAppartenenzaContieneAllegati")) : false;
				if (flgIdUdAppartenenzaContieneAllegati) {
					gestSepAllegatiUDButton.setIcon("allegati.png");
					gestSepAllegatiUDButton.setPrompt("Allegati");
				} else {
					gestSepAllegatiUDButton.setIcon("aggiungiAllegati.png");
					gestSepAllegatiUDButton.setPrompt("Aggiungi allegati");
				}
				return visualizzaGestSepAllegatiUDButton();
			}
		});
		gestSepAllegatiUDButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				visualizzagestSepAllegati();
			}
		});

		dettaglioUdAllegatoButton = new ImgButtonItem("dettaglioUdAllegatoButton", "buttons/detail.png", "Apri dettaglio unità documentaria corrispondente all'allegato");
		dettaglioUdAllegatoButton.setShowTitle(false);
		dettaglioUdAllegatoButton.setAlwaysEnabled(true);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			dettaglioUdAllegatoButton.setStartRow(true);
		} else {
			dettaglioUdAllegatoButton.setStartRow(false);		
		}
		dettaglioUdAllegatoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				dettaglioUdAllegato();
			}
		});
		dettaglioUdAllegatoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isUdAllegato();
			}
		});
		
		if (((AllegatiItem) getItem()).isDocumentiInizialiIstanza()) {		
			
			emailButtons = new NestedFormItem("emailButtons");
			emailButtons.setWidth(10);
			emailButtons.setOverflow(Overflow.VISIBLE);
			emailButtons.setNumCols(7);
			emailButtons.setColWidths("5","1","1","1","1","1","1");
			emailButtons.setColSpan(1);
			
			ImgButtonItem visualizzaEmailButton = new ImgButtonItem("visualizzaEmailButton", "buttons/visualizzaEmail.png", "Visualizza e-mail con cui è stata acquisita l'istanza");
			visualizzaEmailButton.setAlwaysEnabled(true);
			visualizzaEmailButton.setColSpan(1);
			visualizzaEmailButton.setIconWidth(16);
			visualizzaEmailButton.setIconHeight(16);
			visualizzaEmailButton.setIconVAlign(VerticalAlignment.BOTTOM);
			visualizzaEmailButton.setAlign(Alignment.LEFT);
			visualizzaEmailButton.setWidth(16);

			visualizzaEmailButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idEmail = (String) idEmailItem.getValue();
					return (idEmail != null && !idEmail.equals(""));
				}
			});

			visualizzaEmailButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					if(idEmailItem.getValue() != null && !idEmailItem.getValue().equals("")) {
						Record record = new Record();
						record.setAttribute("idEmail", (String) idEmailItem.getValue());
						record.setAttribute("flgIo", "I");
						PostaInArrivoRegistrazioneWindow lPostaInArrivoRegistrazioneWindow = new PostaInArrivoRegistrazioneWindow(record) {
							@Override
							public String getTitlePostaInArrivoRegistrazioneWindow() {
								return "E-mail con cui è stata acquisita l'istanza";
							}
						};
					}					
				}
			});
			
			emailButtons.setNestedFormItems(visualizzaEmailButton);
		}
		
		if (((AllegatiItem) getItem()).isDocumentiIstruttoria()) {
			
			emailButtons = new NestedFormItem("emailButtons");
			emailButtons.setWidth(10);
			emailButtons.setOverflow(Overflow.VISIBLE);
			emailButtons.setNumCols(7);
			emailButtons.setColWidths("5","1","1","1","1","1","1");
			emailButtons.setColSpan(1);
			
			ImgButtonItem visualizzaEmailButton = new ImgButtonItem("visualizzaEmailButton", "buttons/visualizzaEmail.png", "Visualizza e-mail di trasmissione del documento");
			visualizzaEmailButton.setAlwaysEnabled(true);
			visualizzaEmailButton.setColSpan(1);
			visualizzaEmailButton.setIconWidth(16);
			visualizzaEmailButton.setIconHeight(16);
			visualizzaEmailButton.setIconVAlign(VerticalAlignment.BOTTOM);
			visualizzaEmailButton.setAlign(Alignment.LEFT);
			visualizzaEmailButton.setWidth(16);

			visualizzaEmailButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idEmail = (String) idEmailItem.getValue();
					return (idEmail != null && !idEmail.equals(""));
				}
			});

			visualizzaEmailButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					if(idEmailItem.getValue() != null && !idEmailItem.getValue().equals("")) {						
						Record record = new Record(mDynamicForm.getValues());
						final String idUd = record.getAttributeAsString("idUdAppartenenza");
						GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PostaInUscitaRegistrazioneDataSource", "idEmail", FieldType.TEXT);
						lGwtRestDataSource.addParam("idUd", idUd);
						lGwtRestDataSource.fetchData(null, new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									RecordList data = response.getDataAsRecordList();
									if (data.getLength() == 1) {
										PostaInUscitaRegistrazioneWindow postaInUscitaRegistrazioneWindow = new PostaInUscitaRegistrazioneWindow(data.get(0)) {
											@Override
											public String getTitlePostaInUscitaRegistrazioneWindow() {
												return "E-mail di trasmissione del documento";
											}
										};
									} else if (data.getLength() > 0) {
										PostaInUscitaRegistrazioneWindow postaInUscitaRegistrazioneWindow = new PostaInUscitaRegistrazioneWindow(idUd) {
											@Override
											public String getTitlePostaInUscitaRegistrazioneWindow() {
												return "E-mail di trasmissione del documento";
											}
										};
									}
								}
							}
						});
					}
				}
			});
			
			ImgButtonItem inviaEmailButton = new ImgButtonItem("inviaEmailButton", "buttons/send.png", "Invia e-mail");
			inviaEmailButton.setAlwaysEnabled(true);
			inviaEmailButton.setColSpan(1);
			inviaEmailButton.setIconWidth(16);
			inviaEmailButton.setIconHeight(16);
			inviaEmailButton.setIconVAlign(VerticalAlignment.BOTTOM);
			inviaEmailButton.setAlign(Alignment.LEFT);
			inviaEmailButton.setWidth(16);
			inviaEmailButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
				}
			});
			inviaEmailButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					if(uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
						Record record = new Record(mDynamicForm.getValues());
						GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaInvioUDMailDatasource");
						if (isUdDocIstruttoriaCedAutotutela() || isUdDocPraticaVisura()) {
							// Se non sono in ced autotutela o visure non passo per la store di preparazione mail
							lGwtRestService.extraparam.put("usaStorePreparaMail", "true");
						} 
						lGwtRestService.extraparam.put("tipoMail", "PEO");
						lGwtRestService.extraparam.put("indirizzoEmailContribuente", ((AllegatiItem)getItem()).getEmailContribuente());
						lGwtRestService.executecustom("caricaMailDocumentoIstruttoria", record, new DSCallback() {			
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {					
								if (response.getStatus() == DSResponse.STATUS_SUCCESS){
									Record object =  response.getData()[0];
									InvioUDMailWindow lInvioUdMailWindow = new InvioUDMailWindow("PEO", new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											String idEmail = (String) idEmailItem.getValue();
											if (idEmail == null || idEmail.equals("")) {				
												idEmailItem.setValue("99999"); // se già non c'era qualcosa gli setto un valore a caso per abilitargli il bottone di visualizza e-mail
											}
											mDynamicForm.markForRedraw();
											redrawButtons();
										}
									});
									lInvioUdMailWindow.loadMail(object);
									lInvioUdMailWindow.show();
								} 					
							}
						});
					}
				}
			});
			
			emailButtons.setNestedFormItems(visualizzaEmailButton, inviaEmailButton);
		}
		
		List<FormItem> items = new ArrayList<FormItem>();
		items.add(idUdAppartenenzaItem);
		items.add(flgIdUdAppartenenzaContieneAllegatiItem);
		items.add(idUdAllegatoItem);
		items.add(flgAllegatoDaImportareItem);
		items.add(flgSalvatoItem);
		items.add(flgTipoProvXProtItem);
		items.add(ruoloUdItem);
		items.add(isUdSenzaFileImportataItem);
		items.add(idTaskItem);
		items.add(idEmailItem);
		items.add(idAttachEmailItem);
		items.add(collegaDocumentoImportatoItem);	
		items.add(idDocAllegatoHiddenItem);
		items.add(fileImportatoItem);
		items.add(uriFileAllegatoItem);
		items.add(tsInsLastVerFileAllegatoItem);
		items.add(infoFileItem);
		items.add(remoteUriItem);
		items.add(isChangedItem);
		items.add(nomeFileAllegatoOrigItem);
		items.add(nomeFileAllegatoTifItem);
		items.add(uriFileAllegatoTifItem);
		items.add(nomeFileVerPreFirmaItem);
		items.add(uriFileVerPreFirmaItem);
		items.add(improntaVerPreFirmaItem);
		items.add(infoFileVerPreFirmaItem);
		items.add(nroUltimaVersioneItem);
		items.add(esitoInvioACTASerieAttiIntegraliItem);
		items.add(esitoInvioACTASeriePubblItem);
		items.add(numFileCaricatiInUploadMultiploItem);
		items.add(idUDScansioneItem);
		items.add(flgGenAutoDaModelloItem);
		items.add(flgGenDaModelloDaFirmareItem);
		items.add(numeroAllegatoItem);
		if(((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto()) {
			items.add(estremiProtUdItem);  // se è un doc. istruttoria dett. fascicolo gen. completo gli estremi vanno mostrati dopo il numero progressivo del documento
			items.add(dettaglioEstremiProtocolloButton);
			items.add(protocollazioneEntrataButton);
			items.add(protocollazioneUscitaButton);
			items.add(protocollazioneInternaButton);
			items.add(dettaglioIstanzaButton);
			items.add(dataRicezioneItem);
			items.add(gestSepAllegatiUDButton);
			if(emailButtons != null) {
				items.add(emailButtons);
			}			
		}
		items.add(flgProvEsternaItem);	
		items.add(flgParereItem);	
		items.add(flgParteDispositivoSalvatoItem);	
		items.add(flgParteDispositivoItem);
		items.add(flgNoPubblAllegatoItem);
		items.add(flgPubblicaSeparatoItem);	
		items.add(flgPubblicaAllegatiSeparatiInAllegatiGridItem);
		items.add(flgPaginaFileUnioneItem);
		items.add(nroPagFileUnioneItem);
		items.add(nroPagFileUnioneOmissisItem);
		items.add(flgDatiProtettiTipo1Item);
		items.add(flgDatiProtettiTipo2Item);
		items.add(flgDatiProtettiTipo3Item);
		items.add(flgDatiProtettiTipo4Item);
		items.add(flgDatiSensibiliItem);	
		items.add(idTipoFileAllegatoSalvatoHiddenItem);
		items.add(idTipoFileAllegatoHiddenItem);
		items.add(descTipoFileAllegatoHiddenItem);
		items.add(listaTipiFileAllegatoItem);
		items.add(descrizioneFileAllegatoItem);
		items.add(nomeFileAllegatoItem);
		items.add(buildTransparentItem());
		items.add(uploadAllegatoItem);
		items.add(allegatoButtons);	
		items.add(improntaItem);
		items.add(tipoBarcodeContrattoItem);
		items.add(barcodeContrattoItem);
		items.add(energiaGasContrattoItem);
		items.add(tipoSezioneContrattoItem);
		items.add(codContrattoItem);
		items.add(flgPresentiFirmeContrattoItem);
		items.add(flgFirmeCompleteContrattoItem);
		items.add(nroFirmePrevisteContrattoItem);
		items.add(nroFirmeCompilateContrattoItem);
		items.add(flgIncertezzaRilevazioneFirmeContrattoItem);
		items.add(dettaglioDocContrattiBarcodeButton);
		items.add(flgSostituisciVerPrecItem);
		items.add(flgOriginaleCartaceoItem);		
		items.add(flgCopiaSostitutivaItem);	
		items.add(opzioniTimbroItem);	
		items.add(flgTimbratoFilePostRegItem);
		items.add(flgTimbraFilePostRegItem);	
		if(!((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto()) {
			items.add(estremiProtUdItem);
			items.add(dettaglioEstremiProtocolloButton);
			items.add(protocollazioneEntrataButton);
			items.add(protocollazioneUscitaButton);
			items.add(protocollazioneInternaButton);
			items.add(dettaglioIstanzaButton);
			items.add(dataRicezioneItem);
			items.add(gestSepAllegatiUDButton);
			if(emailButtons != null) {
				items.add(emailButtons);
			}
		}
		items.add(dettaglioUdAllegatoButton);	
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));
	}
	
	public boolean visualizzaGestSepAllegatiUDButton() {
		return ((AllegatiItem) getItem()).isAttivaGestSepAllegatiUD() && isIdUdAppartenenzaValorizzato();
	}

	public void buildDynamicFormOmissis() {
		
		mDynamicFormOmissis = new ReplicableCanvasForm();			
		mDynamicFormOmissis.setNumCols(40);
		mDynamicFormOmissis.setVisibility(Visibility.HIDDEN);		
		mDynamicFormOmissis.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
//		fileOmissisAllegatoItem = new DocumentItem() {
//		
//			@Override
//			public Boolean getRequired() {
//				boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null ? new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo")) : false;
//				return flgParteDispositivo;
//			}
//		
//			@Override
//			public int getWidth() {
//				return 320;
//			}
//		};
//		fileOmissisAllegatoItem.setName("fileOmissisAllegato");
//		fileOmissisAllegatoItem.setTitle(((AllegatiItem) getItem()).getTitleNomeFileOmissisAllegato());
//		fileOmissisAllegatoItem.setShowIfCondition(new FormItemIfFunction() {
//		
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null ? new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo")) : false;
//				if(flgParteDispositivo) {
//					fileOmissisAllegatoItem.setAttribute("obbligatorio", true);	
//					fileOmissisAllegatoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(((AllegatiItem) getItem()).getTitleNomeFileOmissisAllegato()));
//				} else {
//					fileOmissisAllegatoItem.setAttribute("obbligatorio", false);	
//					fileOmissisAllegatoItem.setTitle(((AllegatiItem) getItem()).getTitleNomeFileOmissisAllegato());
//				}
//				return true;
//			}
//		});
//	
//		mDynamicFormOmissis.setFields(fileOmissisAllegatoItem);
		
		idDocOmissisItem = new HiddenItem("idDocOmissis");		
		uriFileOmissisItem = new HiddenItem("uriFileOmissis");
		infoFileOmissisItem = new HiddenItem("infoFileOmissis");
		remoteUriOmissisItem = new HiddenItem("remoteUriOmissis");
		isChangedOmissisItem = new HiddenItem("isChangedOmissis");
		nomeFileOrigOmissisItem = new HiddenItem("nomeFileOrigOmissis");
		nomeFileTifOmissisItem = new HiddenItem("nomeFileTifOmissis");
		uriFileTifOmissisItem = new HiddenItem("uriFileTifOmissis");		
		nomeFileVerPreFirmaOmissisItem = new HiddenItem("nomeFileVerPreFirmaOmissis");
		uriFileVerPreFirmaOmissisItem = new HiddenItem("uriFileVerPreFirmaOmissis");
		improntaVerPreFirmaOmissisItem = new HiddenItem("improntaVerPreFirmaOmissis");
		infoFileVerPreFirmaOmissisItem = new HiddenItem("infoFileVerPreFirmaOmissis");
		nroUltimaVersioneOmissisItem = new HiddenItem("nroUltimaVersioneOmissis");		
		
		nomeFileOmissisItem = new ExtendedTextItem("nomeFileOmissis", setTitleAlignFromAllegatoDetailInGridItem(((AllegatiItem) getItem()).getTitleNomeFileOmissis(), false));		
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			nomeFileOmissisItem.setStartRow(true);
			nomeFileOmissisItem.setWidth(650);
			nomeFileOmissisItem.setColSpan(30);
		} else {
			if (((AllegatiItem) getItem()).getWidthNomeFileAllegato() != null) {
				nomeFileOmissisItem.setWidth(((AllegatiItem) getItem()).getWidthNomeFileAllegato().intValue());
			} else {
				nomeFileOmissisItem.setWidth(250);
			}	
		}
		nomeFileOmissisItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				if (nomeFileOmissisItem.getValueAsString() != null && !nomeFileOmissisItem.getValueAsString().equals("")) {
					if (mDynamicFormOmissis.getValueAsString("nomeFileOrigOmissis") != null && !mDynamicFormOmissis.getValueAsString("nomeFileOrigOmissis").equals("")
							&& !nomeFileOmissisItem.getValueAsString().equals(mDynamicFormOmissis.getValueAsString("nomeFileOrigOmissis"))) {
						mDynamicFormOmissis.setValue("isChangedOmissis", true);						
					}
				} else {
					fileOmissisButtons.clickEliminaFile();
				}
				mDynamicFormOmissis.markForRedraw();
				fileOmissisButtons.markForRedraw();
			}
		});
		nomeFileOmissisItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null ? new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo")) : false;
				boolean flgNoPubblAllegato = mDynamicForm.getValueAsString("flgNoPubblAllegato") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgNoPubblAllegato")) : false;	
				if(flgParteDispositivo && !flgNoPubblAllegato) {
					nomeFileOmissisItem.setAttribute("obbligatorio", true);	
					nomeFileOmissisItem.setTitle(setTitleAlignFromAllegatoDetailInGridItem(FrontendUtil.getRequiredFormItemTitle(((AllegatiItem) getItem()).getTitleNomeFileOmissis()), true));
				} else {
					nomeFileOmissisItem.setAttribute("obbligatorio", false);	
					nomeFileOmissisItem.setTitle(setTitleAlignFromAllegatoDetailInGridItem(((AllegatiItem) getItem()).getTitleNomeFileOmissis(), false));
				}
				return true;
			}
		});			
		nomeFileOmissisItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {

				InfoFileRecord infoFileOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;
				return ((AllegatiItem) getItem()).isFormatoAmmesso(infoFileOmissis);
			}

		});			
		
		fileOmissisButtons = new FileButtonsItem("fileOmissisButtons", mDynamicFormOmissis /*,uploadOmissisItem*/) {
			
			@Override
			public String getSuffixFieldName() {
				return "Omissis";
			}

			@Override
			public boolean showAltreOperazioni() {
				return  ((AllegatiItem) getItem()).showAltreOpOmissis();
			}
			@Override
			public AllegatoCanvas getAllegatoCanvas() {
				return (AllegatoCanvas) getInstance();
			}
			
			@Override
			public boolean showTimbraBarcodeMenuItems() {
				return ((AllegatiItem) getItem()).showTimbraBarcodeMenuOmissis();
			}
			
			@Override
			public void manageOnChanged() {
				((AllegatiItem) getItem()).manageOnChanged();
			}

			@Override
			public void manageOnChangedFile() {
				manageChangedFileOmissis();
			}

			@Override
			public String getIdUd() {
				return ((AllegatiItem) getItem()).getIdUd();
			}

			// per la funzionalità di Dati tipologia viene utilizzato l'idDoc del file primario e non quello dell'allegato
			@Override
			public String getIdDocPrimario() {
				return ((AllegatiItem) getItem()).getIdDocFilePrimario();
			}
			
			@Override
			public String getTipoDoc() {
				return getTipoAllegato();
			}
			
			@Override
			public String getFlgTipoProv() {
				return getFlgTipoProvProtocollo();					
			}
			
			@Override
			public String getIdProcGeneraDaModello() {
				return ((AllegatiItem) getItem()).getIdProcess();
			}
			
			@Override
			public boolean showUploadItem() {
				return ((AllegatiItem) getItem()).showUpload();
			}
			
			@Override
			public boolean canBeEditedByApplet() {
				return ((AllegatiItem) getItem()).canBeEditedByApplet();
			}
			
			@Override
			public Date getDataRifValiditaFirma() {
				return ((AllegatiItem) getItem()).getDataRifValiditaFirma();				
			}
			
			@Override
			public Record getRecordProtocollo() {
				return ((AllegatiItem)getItem()).getDetailRecord();
			}
			
			@Override
			public String getEstremiProtocollo() {
				return ((AllegatiItem)getItem()).getEstremiProtocollo();
			}
			
			@Override
			public boolean isAttivaTimbroTipologia() {
				return ((AllegatiItem)getItem()).isAttivaTimbroTipologia();
			}
			
			@Override
			public String getTipoFile() {
				return "AO";
			}
			
			@Override
			public String getNroProgr() {
				return mDynamicForm.getValueAsString("numeroProgrAllegato");
			}	
			
			@Override
			public Record getRecordCaricaModello(String idModello, String tipoModello) {
				return ((AllegatiItem) getItem()).getRecordCaricaModelloAllegato(idModello, tipoModello);
			}
			
			@Override
			public GWTRestDataSource getGeneraDaModelloDataSource(String idModello, String tipoModello, String flgConvertiInPdf) {
				final GWTRestDataSource lGeneraDaModelloWithDatiDocDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
				lGeneraDaModelloWithDatiDocDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
				lGeneraDaModelloWithDatiDocDataSource.addParam("flgMostraDatiSensibili", "false");
				return lGeneraDaModelloWithDatiDocDataSource;
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				return ((AllegatiItem) getItem()).isFormatoAmmesso(info);
			}
			
			@Override
			public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {	
				return ((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(info);
			}
			
			@Override
			public String getRifiutoAllegatiConFirmeNonValide() {
				return ((AllegatiItem) getItem()).getRifiutoAllegatiConFirmeNonValide();
			}
			
			@Override
			public boolean isDisattivaUnioneAllegatiFirmati() {
				return ((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati();
			}
			
			@Override
			public boolean isPubblicazioneSeparataPdfProtetti() {
				return ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti();
			}
			
			@Override
			public boolean enableAcquisisciDaScannerMenuItem() {
				return getShowAcquisisciDaScanner();
			}
			
			@Override
			public long getDimAlertAllegato() {
				return ((AllegatiItem) getItem()).getDimAlertAllegato();
			}
			
			@Override
			public long getDimMaxAllegatoXPubbl() {
				return ((AllegatiItem) getItem()).getDimMaxAllegatoXPubbl();
			}
			
			@Override
			public boolean isEditing() {
				return super.isEditing() && !isNonModificabileVersOmissis();
			}
		};
		
		flgSostituisciVerPrecOmissisItem = new CheckboxItem("flgSostituisciVerPrecOmissis", "sostituisci alla ver. prec");
		flgSostituisciVerPrecOmissisItem.setWidth("*");
		flgSostituisciVerPrecOmissisItem.setDefaultValue(false);
		flgSostituisciVerPrecOmissisItem.setVisible(false);
				
		flgTimbratoFilePostRegOmissisItem = new HiddenItem("flgTimbratoFilePostRegOmissis");		
		opzioniTimbroOmissisItem = new HiddenItem("opzioniTimbroOmissis");
		
		flgTimbraFilePostRegOmissisItem = new CheckboxItem("flgTimbraFilePostRegOmissis", I18NUtil.getMessages().protocollazione_flgTimbraFilePostReg());
		flgTimbraFilePostRegOmissisItem.setValue(false);
		flgTimbraFilePostRegOmissisItem.setColSpan(1);
		flgTimbraFilePostRegOmissisItem.setWidth("*");
		flgTimbraFilePostRegOmissisItem.setShowTitle(true);
		flgTimbraFilePostRegOmissisItem.setWrapTitle(false);
		flgTimbraFilePostRegOmissisItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgTimbraFileOmissisPostReg();
			}
		});	
		flgTimbraFilePostRegOmissisItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(event.getValue() != null && (Boolean)event.getValue()){
					if(!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro")){

						InfoFileRecord lInfoFileRecordOmissis = new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis"));

						//Verifico se il File è timbrabile
						if (canBeTimbrato(lInfoFileRecordOmissis)) {
							
							showOpzioniTimbraturaOmissis();
						
						} else {
							Layout.addMessage(new MessageBean("Il formato del file non ne consente la timbratura", "",
									MessageType.WARNING));
							flgTimbraFilePostRegOmissisItem.setValue(false);
						}
					}
				}	
			}
		});
		
		mDynamicFormOmissis.setFields(
			idDocOmissisItem,
			uriFileOmissisItem, 
			infoFileOmissisItem, 
			remoteUriOmissisItem, 
			isChangedOmissisItem, 
			nomeFileOrigOmissisItem, 
			nomeFileTifOmissisItem, 
			uriFileTifOmissisItem,
			nomeFileVerPreFirmaOmissisItem,
			uriFileVerPreFirmaOmissisItem,
			improntaVerPreFirmaOmissisItem,
			infoFileVerPreFirmaOmissisItem,
			nroUltimaVersioneOmissisItem,
			nomeFileOmissisItem, 
			buildTransparentItem(),
			/*uploadOmissisItem,*/
			fileOmissisButtons,
			flgSostituisciVerPrecOmissisItem,
			flgTimbratoFilePostRegOmissisItem,
			opzioniTimbroOmissisItem,
			flgTimbraFilePostRegOmissisItem
		);
	}
	
	public void showErrorFile(String error) {		
		if (error != null && !"".equals(error)) {
			mDynamicForm.setShowInlineErrors(true);
			Map<String, String> lMap = mDynamicForm.getErrors();
			lMap.put("nomeFileAllegato", error);
			mDynamicForm.setErrors(lMap);			
			mDynamicForm.setFieldErrors("nomeFileAllegato", error);
			mDynamicForm.showFieldErrors("nomeFileAllegato");
			mDynamicForm.markForRedraw();
		}
	}

	//  controllo che l'allegato sia effettivamente valorizzato
	public Boolean checkAllegatoValorizzato(Map<String, Object> lMap){
		if ((lMap.get("listaTipiFileAllegato") != null && !"".equals(lMap.get("listaTipiFileAllegato"))) || 
			(lMap.get("descrizioneFileAllegato") != null && !"".equals(lMap.get("descrizioneFileAllegato"))) || 
			(lMap.get("nomeFileAllegato") != null && !"".equals(lMap.get("nomeFileAllegato")))) {
			return true;
		}		
		return false;	
	}
	
	public void redrawButtons() {
		if(allegatoButtons != null) {
			allegatoButtons.markForRedraw();
		}
		if(emailButtons != null) {
			emailButtons.markForRedraw();
		}
	}
	
	public void visualizzaDettaglioIstanza() {
		
		Record record = new Record(mDynamicForm.getValues());
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");

		if (idUdAppartenenza != null && !"".equals(idUdAppartenenza)) {
			
			String nomeEntita = "";
			if (record.getAttribute("descTipoFileAllegato").equals("CED")) {
				nomeEntita = "istanze_ced";
			}
			if (record.getAttribute("descTipoFileAllegato").equals("AUTOTUTELA")) {
				nomeEntita = "istanze_autotutela";
			}
			IstanzeWindow lIstanzeWindow = new IstanzeWindow(nomeEntita, null) {
				
				@Override
				public void manageAfterCloseWindow() {
					
				}
			};
			lIstanzeWindow.viewRecord(idUdAppartenenza);
		}
	}

    public void visualizzagestSepAllegati() {
		
		Record record = new Record(mDynamicForm.getValues());
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
//		boolean flgIdUdAppartenenzaContieneAllegati = mDynamicForm.getValueAsString("flgIdUdAppartenenzaContieneAllegati") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgIdUdAppartenenzaContieneAllegati")) : false;

		if (idUdAppartenenza != null && !"".equals(idUdAppartenenza)) {
			Layout.showWaitPopup("Caricamento in corso...");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", idUdAppartenenza);
			final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record recordDettaglio = response.getData()[0];
						GestSepAllegatiUDPopup lGestSepAllegatiUDPopup = new GestSepAllegatiUDPopup("Allegati") {
							
							@Override
							public void onSaveCallback(Record record) {
								RecordList listaAllegati = record.getAttribute("listaAllegati") != null ?  record.getAttributeAsRecordList("listaAllegati") : null;
								boolean allegatiPresenti = listaAllegati != null && listaAllegati.getLength() > 0;
								mDynamicForm.setValue("flgIdUdAppartenenzaContieneAllegati", allegatiPresenti);
								mDynamicForm.markForRedraw();
							};
						};
						lGestSepAllegatiUDPopup.editRecord(recordDettaglio);
						lGestSepAllegatiUDPopup.setCanEdit(true);
						lGestSepAllegatiUDPopup.show();
					}
				}
			});
		}
	}
	
	public void visualizzaFileUd() {

		Record record = new Record(mDynamicForm.getValues());
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");

		if (idUdAppartenenza != null && !"".equals(idUdAppartenenza)) {
						
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", idUdAppartenenza);
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					final Record detailRecord = response.getData()[0];

					final Menu contextMenu = new Menu();
					contextMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");

					if (detailRecord.getAttributeAsString("uriFilePrimario") != null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) {
						InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
						MenuItem filePrimarioMenuItem = new MenuItem(detailRecord.getAttributeAsString("nomeFilePrimario"));
						Menu operazioniFilePrimarioSubmenu = new Menu();
						MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(),
								"file/preview.png");
						visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								visualizzaFilePrimarioUd(detailRecord);
							}
						});
						visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
						operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);
						MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
						// Se è firmato P7M
						if (lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
							Menu scaricaFilePrimarioSubMenu = new Menu();
							MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
							firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									scaricaFilePrimarioUd(detailRecord);
								}
							});
							MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
							sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									scaricaFilePrimarioSbustatoUd(detailRecord);
								}
							});
							scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
							scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
						} else {
							scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									scaricaFilePrimarioUd(detailRecord);
								}
							});
						}
						operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);
						filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
						contextMenu.addItem(filePrimarioMenuItem);
					}

					RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
					if (listaAllegati != null) {
						for (int n = 0; n < listaAllegati.getLength(); n++) {
							final int nroAllegato = n;
							final Record allegatoRecord = listaAllegati.get(n);
							MenuItem fileAllegatoMenuItem = new MenuItem(allegatoRecord.getAttributeAsString("nomeFileAllegato"));
							Menu operazioniFileAllegatoSubmenu = new Menu();
							InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
							MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(),
									"file/preview.png");
							visualizzaFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									visualizzaFileAllegatoUd(detailRecord, nroAllegato);
								}
							});
							visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
							operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);
							MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
							// Se è firmato P7M
							if (lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
								Menu scaricaFileAllegatoSubMenu = new Menu();
								MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
								firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										scaricaFileAllegatoUd(detailRecord, nroAllegato);
									}
								});
								MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
								sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										scaricaFileAllegatoSbustatoUd(detailRecord, nroAllegato);
									}
								});
								scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
								scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
							} else {
								scaricaFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

									@Override
									public void onClick(MenuItemClickEvent event) {
										scaricaFileAllegatoUd(detailRecord, nroAllegato);
									}
								});
							}
							operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);
							fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
							contextMenu.addItem(fileAllegatoMenuItem);
						}
					}

					contextMenu.showContextMenu();
				}
			});
		}
	}
	
	public void generaDaModello() {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", getTipoAllegato());
		lGwtRestDataSource.addParam("idProcess", ((AllegatiItem) getItem()).getIdProcess());
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello, String flgConvertiInPdf) {
							afterSelezioneModello(idModello, tipoModello, flgConvertiInPdf);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						afterSelezioneModello(idModello, data.get(0).getAttribute("tipoModello"), data.get(0).getAttribute("flgConvertiInPdf"));
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}
			}
		});
	}
	
	private void afterSelezioneModelloXAnteprima(final String idModello, final String tipoModello, final String flgConvertiInPdf) {
		caricaModelloXAnteprima(idModello, tipoModello, flgConvertiInPdf);		
	}

	public void caricaModelloXAnteprima(String idModello, String tipoModello, String flgConvertiInPdf) {		
		((AllegatiItem) getItem()).caricaModelloAllegato(idModello, tipoModello, flgConvertiInPdf, (String) mDynamicForm.getValue("idUdAppartenenza"), new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageAfterCaricaModelloAllegatoXAnteprima(object);
			}
		});
	}
	public void manageAfterCaricaModelloAllegatoXAnteprima(Record record) {
		final Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		final String display = record.getAttribute("nomeFilePrimario");
		final String uri = record.getAttribute("uriFilePrimario");
		final Boolean remoteUri = false;
		final InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
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
						preview(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}
	
	private void afterSelezioneModello(final String idModello, final String tipoModello, final String flgConvertiInPdf) {
		if (((AllegatiItem)getItem()).isModelloModificabile(idModello) && uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {
			SC.ask("E' gia presente un file. Vuoi procedere alla generazione sovrascrivendo il file esistente?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value != null && value) {
						caricaModello(idModello, tipoModello, flgConvertiInPdf);
					}
				}
			});
		} else {
			caricaModello(idModello, tipoModello, flgConvertiInPdf);
		}
	}

	public void caricaModello(String idModello, String tipoModello, String flgConvertiInPdf) {		
		((AllegatiItem) getItem()).caricaModelloAllegato(idModello, tipoModello, flgConvertiInPdf, (String) mDynamicForm.getValue("idUdAppartenenza"), new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageAfterCaricaModelloAllegato(object);
			}
		});
	}

	public void manageAfterCaricaModelloAllegato(Record record) {
		String nomeFileAllegato = record.getAttribute("nomeFilePrimario");
		String uriFileAllegato = record.getAttribute("uriFilePrimario");
		mDynamicForm.setValue("nomeFileAllegato", nomeFileAllegato);
		mDynamicForm.setValue("uriFileAllegato", uriFileAllegato);
		mDynamicForm.setValue("nomeFileAllegatoTif", "");
		mDynamicForm.setValue("uriFileAllegatoTif", "");
		mDynamicForm.setValue("remoteUri", false);
		mDynamicForm.setValue("uriFileVerPreFirma", uriFileAllegato);
		mDynamicForm.setValue("nomeFileVerPreFirma", nomeFileAllegato);
		changedEventAfterUpload(record.getAttribute("nomeFileAllegato"), record.getAttribute("uriFileAllegato"));
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
		mDynamicForm.setValue("improntaVerPreFirma", info.getImpronta());
		mDynamicForm.setValue("infoFileVerPreFirma", info);		
		InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		mDynamicForm.setValue("infoFile", info);
		mDynamicForm.setValue("isUdSenzaFileImportata", false);
		String nomeFileAllegatoOrig = mDynamicForm.getValueAsString("nomeFileAllegatoOrig");
		if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || (nomeFileAllegato != null && !"".equals(nomeFileAllegato)
				&& nomeFileAllegatoOrig != null && !"".equals(nomeFileAllegatoOrig) && !nomeFileAllegato.equals(nomeFileAllegatoOrig))) {
			manageChangedFileAllegato();
		}
		controlAfterUpload(info);	
		mDynamicForm.markForRedraw();
		redrawButtons();
		((AllegatiItem) getItem()).manageOnChanged();
		if (showEditButton()) {
			clickEditFile();
		} else {
			clickPreviewFile();
		}
	}

	public void manageAltreOpButtonClick() {

		// Acquisisci da scanner
		MenuItem acquisisciDaScannerMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(),
				"file/scanner.png");
		acquisisciDaScannerMenuItem.setEnableIfCondition(getShowAquisisciDaScannerCondition());
		acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickAcquisisciDaScanner();
			}
		});

		//Firma file
		MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(), "file/mini_sign.png");
		firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {

				return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
			}
		});
		firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				clickFirma();
			}
		});

		//Download file
		MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
		InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
		if (lInfoFileRecord != null) {
			// Se è firmato P7M
			if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
				Menu showFirmatoAndSbustato = new Menu();
				MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
				firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickDownloadFile();
					}
				});
				MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
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

				return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
			}
		});
		
		// Copertina attestato conformità all'originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(),
				"file/attestato.png");
		attestatoConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				if (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("")) {

					InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
					return lInfoFileRecord != null;

				}
				return false;
			}
		});

		attestatoConformitaOriginaleMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				final InfoFileRecord fileAllegato = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				final String uri = mDynamicForm.getValueAsString("uriFileAllegato");
				Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
				final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
				final String idDoc = mDynamicForm.getValueAsString("idDocAllegato");

				SC.ask("Vuoi firmare digitalmente l'attestato ?", new BooleanCallback() {

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

		//Elimina file
		MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
		eliminaMenuItem.setEnableIfCondition(getShowElimina());
		eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				clickEliminaFile();
			}
		});

		//Visualizza versioni
		MenuItem visualizzaVersioniMenuItem = new MenuItem("Visualizza versioni", "file/version.png");
		visualizzaVersioniMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				// Se è valorizzato il file
				boolean isVisible = false;
				Integer nroUltimaVersione = mDynamicForm != null && mDynamicForm.getValueAsString("nroUltimaVersione") != null
						&& !"".equals(mDynamicForm.getValueAsString("nroUltimaVersione")) ? new Integer(mDynamicForm.getValueAsString("nroUltimaVersione"))
								: null;

				if (nroUltimaVersione != null && nroUltimaVersione > 1) {
					isVisible = true;
				}

				return isVisible;
			}
		});
		visualizzaVersioniMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {	
				((AllegatiItem) getItem()).visualizzaVersioni(new Record(mDynamicForm.getValues()));			
			}
		});

		final Menu altreOpMenu = new Menu();
		altreOpMenu.setOverflow(Overflow.VISIBLE);
		altreOpMenu.setShowIcons(true);
		altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
		altreOpMenu.setCanDragRecordsOut(false);
		altreOpMenu.setWidth("*");
		altreOpMenu.setHeight("*");

		if (!isHideVisualizzaVersioniButton()) {
			altreOpMenu.addItem(visualizzaVersioniMenuItem);
		}
		
		if (!isShowDownloadOutsideAltreOperazioniButton()) {
			altreOpMenu.addItem(downloadMenuItem);
		}
		
		if (getEditing() && allegatoButtons.isEditing() && !isModificabileSoloVersOmissis()) {
			if (!isHideAcquisisciDaScannerInAltreOperazioniButton()) {
				altreOpMenu.addItem(acquisisciDaScannerMenuItem);
			}
			if (!isHideFirmaInAltreOperazioniButton()) {
				altreOpMenu.addItem(firmaMenuItem);
			}
		}
		
		if (!isHideTimbraInAltreOperazioniButton()) {

			altreOpMenu.addItem(attestatoConformitaOriginaleMenuItem);
			
			if(((AllegatiItem) getItem()).showOperazioniTimbraturaAllegato(new Record(mDynamicForm.getValues()))) {
				buildMenuBarcodeEtichetta(altreOpMenu);
			}
			
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
			
		}
		// altreOpMenu.addItem(visualizzaInfoFirma);

		if (getEditing() && (allegatoButtons.isEditing() || sonoInMail()) && !isModificabileSoloVersOmissis() && nomeFileAllegatoItem.getCanEdit()) {
			altreOpMenu.addItem(eliminaMenuItem);
		}
		altreOpMenu.showContextMenu();

	}

	public void manageChangedFileAllegato() {
		mDynamicForm.setValue("isChanged", true);
		((AllegatiItem) getItem()).manageChangedFileAllegati();
		manageChangedFileAllegatoParteDispositivo(true);
		nomeFileAllegatoItem.validate();
		mDynamicForm.redraw();		
		if (showFlgSostituisciVerPrec()) {
			Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;
			InfoFileRecord precInfo = recordOrig != null && recordOrig.getAttributeAsObject("infoFile") != null
					? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFile")) : null;
			String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
			InfoFileRecord info = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
			String impronta = info != null ? info.getImpronta() : null;
			if(flgSostituisciVerPrecItem != null) {
				if (precImpronta != null && impronta != null && !impronta.equals(precImpronta)) {
					flgSostituisciVerPrecItem.show();
				} else {
					flgSostituisciVerPrecItem.hide();
				}
			}			
		}
		if(((AllegatiItem) getItem()).isAttivaTimbraturaFilePostRegAllegato() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
			if (canBeTimbrato(lInfoFileRecord) && !lInfoFileRecord.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG") ) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro") && ((numFileCaricatiInUploadMultiploItem.getValue()!=null && Integer.parseInt((String) numFileCaricatiInUploadMultiploItem.getValue())==1) || numFileCaricatiInUploadMultiploItem.getValue()==null )) {									
						boolean flgTimbraFilePostRegValue = Boolean.parseBoolean(mDynamicForm.getValueAsString("flgTimbraFilePostReg"));	
						
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
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}
		}			
		numFileCaricatiInUploadMultiploItem.setValue("1");
	}
	
	/*
	 * Viene preimpostato il checkbox 'Apponi timbro' se si proviene da una protocollazione email 
	 */
	public void preimpostaApponiTimbroFromEmail() {
		if(((AllegatiItem) getItem()).isAttivaTimbraturaFilePostRegAllegato() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecord = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
			if (lInfoFileRecord != null && canBeTimbrato(lInfoFileRecord) && AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
				flgTimbraFilePostRegItem.setValue(true);
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}
		}
	}
	
	/*
	 * Viene preimpostato il checkbox 'Apponi timbro' se si proviene da una protocollazione di una bozza 
	 */
	public void preimpostaApponiTimbroXProtBozza() {
		if(((AllegatiItem) getItem()).isAttivaTimbraturaFilePostRegAllegato() && showFlgTimbraFilePostReg()) {				
			InfoFileRecord lInfoFileRecord = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
			if (lInfoFileRecord != null && canBeTimbrato(lInfoFileRecord) && AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
				flgTimbraFilePostRegItem.setValue(true);
			} else {
				flgTimbraFilePostRegItem.setValue(false);
			}	
		}
	}
	
	public void manageChangedFileOmissis() {
		mDynamicFormOmissis.setValue("isChangedOmissis", true);
		((AllegatiItem) getItem()).manageChangedFileAllegati();
		manageChangedFileAllegatoParteDispositivo(true);
		nomeFileOmissisItem.validate();
		mDynamicFormOmissis.redraw();		
		if (showFlgSostituisciVerPrec()) {
			Record recordOrig = valuesOrig != null ? new Record(valuesOrig) : null;
			InfoFileRecord precInfoOmissis = recordOrig != null && recordOrig.getAttributeAsObject("infoFileOmissis") != null
					? new InfoFileRecord(recordOrig.getAttributeAsObject("infoFileOmissis")) : null;
			String precImprontaOmissis = precInfoOmissis != null ? precInfoOmissis.getImpronta() : null;
			InfoFileRecord infoOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;
			String improntaOmissis = infoOmissis != null ? infoOmissis.getImpronta() : null;
			if(flgSostituisciVerPrecOmissisItem != null) {
				if (precImprontaOmissis != null && improntaOmissis != null && !improntaOmissis.equals(precImprontaOmissis)) {
					flgSostituisciVerPrecOmissisItem.show();
				} else {
					flgSostituisciVerPrecOmissisItem.hide();
				}
			}			
		}
		if(((AllegatiItem) getItem()).isAttivaTimbraturaFilePostRegAllegato() && showFlgTimbraFilePostReg()) {
			InfoFileRecord lInfoFileRecordOmissis = new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis"));
			// Verifico se il File è timbrabile
			if (canBeTimbrato(lInfoFileRecordOmissis) && !lInfoFileRecordOmissis.isFirmato()) {
				if (AurigaLayout.getParametroDBAsBoolean("FORZA_TIMBRATURA_FILE_POST_REG")) {
					if (!AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaApponiTimbro") && ((numFileCaricatiInUploadMultiploItem.getValue()!=null && Integer.parseInt((String) numFileCaricatiInUploadMultiploItem.getValue())==1) || numFileCaricatiInUploadMultiploItem.getValue()==null )) {
						boolean flgTimbraFilePostRegOmissisValue = Boolean.parseBoolean(mDynamicFormOmissis.getValueAsString("flgTimbraFilePostRegOmissis"));
						
//						if (!(flgTmbraFilePostRegOmissisItem.getValue() != null && (Boolean) flgTimbraFilePostRegOmissisItem.getValue())) {
						if (!flgTimbraFilePostRegOmissisValue) {
							showOpzioniTimbratura();
						} else {
							SC.ask("Vuoi verificare/modificare le opzioni di timbratura per il nuovo file ?",
									new BooleanCallback() {

										@Override
										public void execute(Boolean value) {
											if (value) {
												showOpzioniTimbraturaOmissis();
											}
										}
									});
						}
					}
					flgTimbraFilePostRegOmissisItem.setValue(true);
				} else {
					flgTimbraFilePostRegOmissisItem.setValue(false);
				}
			} else {
				flgTimbraFilePostRegOmissisItem.setValue(false);
			}
		}
		numFileCaricatiInUploadMultiploItem.setValue("1");		
	}

	public void manageChangedFileAllegatoParteDispositivo(boolean isChanged) {
		if (showFlgParteDispositivo()) {
			boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
			boolean flgParteDispositivoSalvato = mDynamicForm.getValue("flgParteDispositivoSalvato") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivoSalvato"));
			if (flgParteDispositivo && (isChanged || !flgParteDispositivoSalvato)) {
				((AllegatiItem) getItem()).manageChangedFileAllegatiParteDispositivo();
			}
		}
	}

	public void setFixedTipoFileAllegato(String idTipoFileAllegato, String descTipoFileAllegato) {
		this.idTipoFileAllegato = idTipoFileAllegato;
		this.descTipoFileAllegato = descTipoFileAllegato;
		if(mDynamicForm != null) {
			mDynamicForm.setValue("listaTipiFileAllegato", idTipoFileAllegato);
			mDynamicForm.setValue("idTipoFileAllegato", idTipoFileAllegato);
			mDynamicForm.setValue("descTipoFileAllegato", descTipoFileAllegato);
		}
	}
	
	private boolean showFlgProvEsterna() {
		return ((AllegatiItem) getItem()).isShowFlgProvEsterna();
	}
	
	private boolean showFlgParere() {
		return ((AllegatiItem) getItem()).isShowFlgParere();
	}
	
	private boolean showFlgParteDispositivo() {
		return ((AllegatiItem) getItem()).isShowFlgParteDispositivo();
	}

	private boolean showFlgNoPubblAllegato() {
		return ((AllegatiItem) getItem()).isShowFlgNoPubblAllegato();
	}
	
	private boolean showFlgPubblicaSeparato() {
		return ((AllegatiItem) getItem()).isShowFlgPubblicaSeparato() && !((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale();
	}
	
	private boolean showFlgPaginaFileUnione() {		
		if(((AllegatiItem)getItem()).isAttivaSceltaPosizioneAllegatiUniti() && isParteDispositivo() && !isFlgPubblicaSeparato()) {
			return true;
		}
		return false;
	}
	
	private boolean showFlgSostituisciVerPrec() {
		return ((AllegatiItem) getItem()).isShowFlgSostituisciVerPrec();
	}
	
	// Recupero il flgTipoProv della maschera di protocollo
	private String getFlgTipoProvProtocollo() {
		return ((AllegatiItem) getItem()).getFlgTipoProvProtocollo();			
	}
	
	private boolean showFlgEscludiPubblicazione() {
		return  ((AllegatiItem) getItem()).isShowFlgEscludiPubblicazione();
	}
	
	private boolean showFlgTimbraFilePostReg() {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if (((AllegatiItem) getItem()).isAttivaTimbraturaFilePostRegAllegato()) {		
			boolean isEditing = getEditing() && ((AllegatiItem) getItem()).showUpload();
			String flgTipoProv = getFlgTipoProvProtocollo();
			String uriFileAllegato = (String) mDynamicForm.getValue("uriFileAllegato");
			InfoFileRecord infoFileAllegato = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
//			String mimetype = infoFileAllegato != null && infoFileAllegato.getMimetype() != null ? infoFileAllegato.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
				uriFileAllegato != null && !"".equals(uriFileAllegato) && infoFileAllegato != null && !infoFileAllegato.isFirmato() /*&& !mimetype.startsWith("image")*/) {
				String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
				boolean isChanged = mDynamicForm.getValue("isChanged") != null && (Boolean) mDynamicForm.getValue("isChanged");
				boolean flgTimbratoFilePostReg = mDynamicForm.getValue("flgTimbratoFilePostReg") != null && (Boolean) mDynamicForm.getValue("flgTimbratoFilePostReg");
				boolean isNewOrChanged = idDocAllegato == null || "".equals(idDocAllegato) || isChanged;			
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChanged || !flgTimbratoFilePostReg) {  
					return true;
				}				
			}
		}
		// Nel caso in cui il check non è visibile il valore va settato sempre a false
		mDynamicForm.setValue("flgTimbraFilePostReg",false);
		return false;		
	}
	
	private boolean showFlgTimbraFileOmissisPostReg() {
		/*
		Il check deve apparire quando c'è il file, NON è firmato digitalmente & se:
	    a) si sta facendo una nuova registrazione
	    b) si sta facendo una nuova registrazione come copia
	    c) si sta facendo una nuova registrazione da modello
	    d) si sta protocollando una bozza
	    e) si sta modificando una registrazione e si è fatto upload/scansione di un nuovo file OR se il file era già presente e la stored non indicava che il file era timbrato (il caso d può ricadere in questo).
	    */ 
		if (((AllegatiItem) getItem()).isAttivaTimbraturaFilePostRegAllegato()) {		
			boolean isEditing = getEditing() && ((AllegatiItem) getItem()).showUpload();
			String flgTipoProv = getFlgTipoProvProtocollo();
			String uriFileOmissis = (String) mDynamicFormOmissis.getValue("uriFileOmissis");
			InfoFileRecord infoFileOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;
//			String mimetypeOmissis = infoFileOmissis != null && infoFileOmissis.getMimetype() != null ? infoFileOmissis.getMimetype() : "";
			// se sto facendo una nuova registrazione o una variazione di una già esistente, e se il file c'è e non è firmato digitalmente
			if (flgTipoProv != null && !"".equals(flgTipoProv) && isEditing && 
					uriFileOmissis != null && !"".equals(uriFileOmissis) && infoFileOmissis != null && !infoFileOmissis.isFirmato() /*&& !mimetypeOmissis.startsWith("image")*/) {
				String idDocOmissis = mDynamicFormOmissis.getValueAsString("idDocOmissis");
				boolean isChangedOmissis = mDynamicFormOmissis.getValue("isChangedOmissis") != null && (Boolean) mDynamicFormOmissis.getValue("isChangedOmissis");
				boolean flgTimbratoFilePostRegOmissis = mDynamicFormOmissis.getValue("flgTimbratoFilePostRegOmissis") != null && (Boolean) mDynamicFormOmissis.getValue("flgTimbratoFilePostRegOmissis");
				boolean isNewOrChangedOmissis = idDocOmissis == null || "".equals(idDocOmissis) || isChangedOmissis;			
				// se il file è nuovo oppure era già presente e la stored non indicava che il file era timbrato 
				if (isNewOrChangedOmissis || !flgTimbratoFilePostRegOmissis) {  
					return true;
				}				
			}
		}
		// Nel caso in cui il check non è visibile il valore va settato sempre a false
		mDynamicFormOmissis.setValue("flgTimbraFilePostRegOmissis",false);
		return false;		
	}
	
	private boolean isHideVisualizzaVersioniButton() {
		return ((AllegatiItem) getItem()).isHideVisualizzaVersioniButton();
	}
	
	private boolean isShowDownloadOutsideAltreOperazioniButton() {
		return ((AllegatiItem) getItem()).isShowDownloadOutsideAltreOperazioniButton();
	}
	
	private boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
		return ((AllegatiItem) getItem()).isHideAcquisisciDaScannerInAltreOperazioniButton();
	}
	
	private boolean isHideFirmaInAltreOperazioniButton() {
		return ((AllegatiItem) getItem()).isHideFirmaInAltreOperazioniButton();
	}

	private boolean isHideTimbraInAltreOperazioniButton() {
		return ((AllegatiItem) getItem()).isHideTimbraInAltreOperazioniButton();
	}

	protected void clickTrasformaInPrimario(HLayout hLayout) {
		((AllegatiItem) getItem()).clickTrasformaInPrimario(hLayout);
	}

	protected MenuItemIfFunction getShowElimina() {

		return new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				// se è valorizzato il file e non è vuoto
				if (uriFileAllegatoItem.getValue() != null && !"".equals(uriFileAllegatoItem.getValue())) {
					// se sono in una mail
					// if (sonoInMail()) {
					// // se il file non ha il mimetype (ovvero è di un formato non ammesso) mostro l'elimina
					// if (lInfoFileRecord.getMimetype()== null || lInfoFileRecord.getMimetype().trim().equals("")){
					// return true;
					// }
					// // se idAttach è vuoto (eventuale body) mostro l'elimina
					// if (idAttachEmailItem.getValue() == null || idAttachEmailItem.getValue().equals("")){
					// return true;
					// }
					// // altrimenti non lo mostro
					// else return false;
					// }
					return true;
				}
				return false;
			}
		};
	}

	protected boolean sonoInMail() {
		return ((AllegatiItem) getItem()).sonoInMail();
	}

	protected boolean sonoModificaVisualizza() {
		return ((AllegatiItem) getItem()).sonoModificaVisualizza();
	}

	protected boolean showNumeroAllegato() {
		return ((AllegatiItem) getItem()).showNumeroAllegato();
	}

	protected boolean showTipoAllegato() {
		return ((AllegatiItem) getItem()).showTipoAllegato();
	}

	protected boolean showDescrizioneFileAllegato() {
		return ((AllegatiItem) getItem()).showDescrizioneFileAllegato();
	}

	protected boolean showNomeFileAllegato() {
		return ((AllegatiItem) getItem()).showNomeFileAllegato();
	}

	protected MenuItemIfFunction getShowAquisisciDaScannerCondition() {
		return new MenuItemIfFunction() {

			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {

				return getShowAcquisisciDaScanner();
			}
		};
	}

	protected boolean getShowAcquisisciDaScanner() {
		return ((AllegatiItem) getItem()).showAcquisisciDaScanner();
	}

	protected boolean showUpload() {
		return ((AllegatiItem) getItem()).showUpload();
	}

	public boolean validateFormatoFileAllegato(InfoFileRecord infoFileAllegato) {
		return ((AllegatiItem) getItem()).validateFormatoFileAllegato(infoFileAllegato);
	}

	public String getFormatoFileNonValidoErrorMessage() {
		return ((AllegatiItem) getItem()).getFormatoFileNonValidoErrorMessage();
	}
	
	protected boolean isUdDocIstruttoriaCedAutotutela() {
		Record record = new Record(mDynamicForm.getValues());
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
		return (idUdAppartenenza != null && !"".equals(idUdAppartenenza) && ((AllegatiItem) getItem()).isDocumentiIstruttoria() && ((AllegatiItem) getItem()).isDocCedAutotutela());
	}
	
	protected boolean isUdDocPraticaVisura() {
		Record record = new Record(mDynamicForm.getValues());
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
		return (idUdAppartenenza != null && !"".equals(idUdAppartenenza) && ((AllegatiItem) getItem()).isDocumentiIstruttoria() && ((AllegatiItem) getItem()).isDocPraticaVisure());
	}
	
	protected boolean isUdDocIstruttoriaDettFascicoloGenCompleto() {
		Record record = new Record(mDynamicForm.getValues());
		String idUdAppartenenza = record.getAttribute("idUdAppartenenza");
		return (idUdAppartenenza != null && !"".equals(idUdAppartenenza) && ((AllegatiItem) getItem()).isDocIstruttoriaDettFascicoloGenCompleto());
	}
	
	protected boolean isDocumentiInizialiIstanza() {
		return ((AllegatiItem) getItem()).isDocumentiInizialiIstanza();
	}
	
	protected boolean showProtocollazioneButtons() {
		return isUdDocPraticaVisura() || isUdDocIstruttoriaDettFascicoloGenCompleto();
	}
	
	@Override
	public boolean validate() {
		
		boolean valid = true;
		mDynamicForm.setShowInlineErrors(true);

		String rifiutoAllegatiConFirmeNonValide = ((AllegatiItem) getItem()).getRifiutoAllegatiConFirmeNonValide();
		
		final float MEGABYTE = 1024L * 1024L;
		long dimMaxAllegatoXPubblInMB = ((AllegatiItem) getItem()).getDimMaxAllegatoXPubbl(); // questa è in MB
		
		boolean flgParere = mDynamicForm.getValue("flgParere") != null ? new Boolean(mDynamicForm.getValueAsString("flgParere")) : false;
		boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null ? new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo")) : false;
		boolean flgNoPubblAllegato = mDynamicForm.getValueAsString("flgNoPubblAllegato") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgNoPubblAllegato")) : false;					
		boolean flgPubblicaSeparato = isFlgPubblicaSeparato();		
		boolean flgDatiProtettiTipo1 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo1() && mDynamicForm.getValueAsString("flgDatiProtettiTipo1") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo1")) : false;
		boolean flgDatiProtettiTipo2 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo2() && mDynamicForm.getValueAsString("flgDatiProtettiTipo2") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo2")) : false;
		boolean flgDatiProtettiTipo3 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo3() && mDynamicForm.getValueAsString("flgDatiProtettiTipo3") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo3")) : false;
		boolean flgDatiProtettiTipo4 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo4() && mDynamicForm.getValueAsString("flgDatiProtettiTipo4") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo4")) : false;		
		boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
		boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;		
		
		InfoFileRecord infoFileAllegato = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;

		if(((AllegatiItem) getItem()).isObbligatorioFile() && (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
			// se è un ud allegato il file non deve essere obbligatorio
			if(!isUdAllegato()) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "Campo obbligatorio");
				valid = false;
			}
		} else {		
			if (showTipoAllegato() && showDescrizioneFileAllegato()) {
				if ((listaTipiFileAllegatoItem.getValue() == null || listaTipiFileAllegatoItem.getValue().equals(""))
						&& (descrizioneFileAllegatoItem.getValue() == null || descrizioneFileAllegatoItem.getValueAsString().trim().equals(""))
						&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
					mDynamicForm.setFieldErrors("listaTipiFileAllegato", "Almeno uno tra tipo, descrizione e file deve essere valorizzato");
					valid = false;
				}
			} else if (showTipoAllegato() && !showDescrizioneFileAllegato()) {
				if ((listaTipiFileAllegatoItem.getValue() == null || listaTipiFileAllegatoItem.getValue().equals(""))
						&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
					mDynamicForm.setFieldErrors("listaTipiFileAllegato", "Almeno uno tra tipo e file deve essere valorizzato");
					valid = false;
				}
			} else if (!showTipoAllegato() && showDescrizioneFileAllegato()) {
				if ((descrizioneFileAllegatoItem.getValue() == null || descrizioneFileAllegatoItem.getValueAsString().trim().equals(""))
						&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
					mDynamicForm.setFieldErrors("descrizioneFileAllegato", "Almeno uno tra descrizione e file deve essere valorizzato");
					valid = false;
				}
			} else if (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals("")) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "Campo obbligatorio");
				valid = false;
			}
		}
		
		if (mDynamicForm.getValue("uriFileAllegato") != null && !"".equals(mDynamicForm.getValue("uriFileAllegato"))) {
			if (infoFileAllegato == null || infoFileAllegato.getMimetype() == null || infoFileAllegato.getMimetype().equals("")) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "Formato file non riconosciuto");
				valid = false;
			} else if (infoFileAllegato != null && !validateFormatoFileAllegato(infoFileAllegato)) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", getFormatoFileNonValidoErrorMessage());
				valid = false;
			} else if (infoFileAllegato != null && infoFileAllegato.getBytes() <= 0) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "Il file ha dimensione 0");
				valid = false;
			}
			
			// il tipo del file caricato non è tra quelli accettati
			if (infoFileAllegato != null && !((AllegatiItem) getItem()).isFormatoAmmesso(infoFileAllegato)) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "Formato file non valido");
				valid = false;
			} else if(flgParteDispositivo && infoFileAllegato != null && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(infoFileAllegato)) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "Formato file non ammesso per un allegato parte integrante");
				valid = false;
			}
			
			if (flgParteDispositivo && !showFileOmissis && infoFileAllegato != null && !PreviewWindow.isPdfConvertibile(infoFileAllegato) && !flgPubblicaSeparato) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto");
				valid = false;	
			}

			if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
				if(!infoFileAllegato.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
					if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
						mDynamicForm.setFieldErrors("nomeFileAllegato", "Il file presenta firme non valide alla data odierna e non può essere caricato");
						valid = false;
					} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
						mDynamicForm.setFieldErrors("nomeFileAllegato", "Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante");
						valid = false;
					} 
				} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
					// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
				}
			}
			
			// Se è un pdf protetto lo pubblico separatamente
			if (flgParteDispositivo && !showFileOmissis && ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti() && infoFileAllegato != null && infoFileAllegato.isPdfProtetto() && !flgPubblicaSeparato) {
				mDynamicForm.setFieldErrors("nomeFileAllegato", "File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto");
				valid = false;	
			}
										
			if(flgParteDispositivo && !showFileOmissis && dimMaxAllegatoXPubblInMB > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
				mDynamicForm.setFieldErrors("nomeFileAllegato", "La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione");
				valid = false;	
			}
		}

		if(showFileOmissis) {
		
			mDynamicFormOmissis.setShowInlineErrors(true);

			InfoFileRecord infoFileOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;

			if(flgParteDispositivo && !flgNoPubblAllegato && (uriFileOmissisItem.getValue() == null || uriFileOmissisItem.getValue().equals(""))) {
				mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Campo obbligatorio");
				valid = false;
			}

			if (mDynamicFormOmissis.getValue("uriFileOmissis") != null && !"".equals(mDynamicFormOmissis.getValue("uriFileOmissis"))) {
				if (infoFileOmissis == null || infoFileOmissis.getMimetype() == null || infoFileOmissis.getMimetype().equals("")) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Formato file non riconosciuto");
					valid = false;
				} else if (infoFileOmissis != null && !validateFormatoFileAllegato(infoFileOmissis)) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", getFormatoFileNonValidoErrorMessage());
					valid = false;
				} else if (infoFileOmissis != null && infoFileOmissis.getBytes() <= 0) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Il file ha dimensione 0");
					valid = false;
				}
				
				// il tipo del file caricato non è tra quelli accettati
				if (infoFileOmissis != null && !((AllegatiItem) getItem()).isFormatoAmmesso(infoFileOmissis)) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Formato file non valido");
					valid = false;
				} else if(flgParteDispositivo && infoFileOmissis != null && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(infoFileOmissis)) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Formato file non ammesso per un allegato parte integrante");
					valid = false;
				}
				
				if (flgParteDispositivo && infoFileOmissis != null && !PreviewWindow.isPdfConvertibile(infoFileOmissis) && !flgPubblicaSeparato) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto");
					valid = false;	
				}
				
				if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
					if(!infoFileOmissis.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
						if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
							mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Il file presenta firme non valide alla data odierna e non può essere caricato");
							valid = false;
						} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
							mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante");
							valid = false;
						} 
					} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
						// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
					}
				}
				
				// Se è un pdf protetto lo pubblico separatamente
				if (flgParteDispositivo && ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti() && infoFileOmissis != null && infoFileOmissis.isPdfProtetto() && !flgPubblicaSeparato) {
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto");
					valid = false;	
				}
							
				if(flgParteDispositivo && dimMaxAllegatoXPubblInMB > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
					mDynamicFormOmissis.setFieldErrors("nomeFileOmissis", "La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione");
					valid = false;	
				}
			}
		}
		
		return valid;
	}
	
	@Override
	public boolean valuesAreValid() {
		
		boolean valid = true;
		
		String rifiutoAllegatiConFirmeNonValide = ((AllegatiItem) getItem()).getRifiutoAllegatiConFirmeNonValide();
		
		final float MEGABYTE = 1024L * 1024L;
		long dimMaxAllegatoXPubblInMB = ((AllegatiItem) getItem()).getDimMaxAllegatoXPubbl(); // questa è in MB
		
		boolean flgParere = mDynamicForm.getValue("flgParere") != null ? new Boolean(mDynamicForm.getValueAsString("flgParere")) : false;
		boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null ? new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo")) : false;
		boolean flgNoPubblAllegato = mDynamicForm.getValueAsString("flgNoPubblAllegato") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgNoPubblAllegato")) : false;					
		boolean flgPubblicaSeparato = isFlgPubblicaSeparato();		
		boolean flgDatiProtettiTipo1 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo1() && mDynamicForm.getValueAsString("flgDatiProtettiTipo1") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo1")) : false;
		boolean flgDatiProtettiTipo2 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo2() && mDynamicForm.getValueAsString("flgDatiProtettiTipo2") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo2")) : false;
		boolean flgDatiProtettiTipo3 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo3() && mDynamicForm.getValueAsString("flgDatiProtettiTipo3") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo3")) : false;
		boolean flgDatiProtettiTipo4 = ((AllegatiItem) getItem()).getShowFlgDatiProtettiTipo4() && mDynamicForm.getValueAsString("flgDatiProtettiTipo4") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiProtettiTipo4")) : false;		
		boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
		boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;		
		
		InfoFileRecord infoFileAllegato = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;

		if(((AllegatiItem) getItem()).isObbligatorioFile() && (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
			// se è un ud allegato il file non deve essere obbligatorio
			if(!isUdAllegato()) {
				valid = false;
			}
		} else {		
			if (showTipoAllegato() && showDescrizioneFileAllegato()) {
				if ((listaTipiFileAllegatoItem.getValue() == null || listaTipiFileAllegatoItem.getValue().equals(""))
						&& (descrizioneFileAllegatoItem.getValue() == null || descrizioneFileAllegatoItem.getValueAsString().trim().equals(""))
						&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
					valid = false;
				}
			} else if (showTipoAllegato() && !showDescrizioneFileAllegato()) {
				if ((listaTipiFileAllegatoItem.getValue() == null || listaTipiFileAllegatoItem.getValue().equals(""))
						&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
					valid = false;
				}
			} else if (!showTipoAllegato() && showDescrizioneFileAllegato()) {
				if ((descrizioneFileAllegatoItem.getValue() == null || descrizioneFileAllegatoItem.getValueAsString().trim().equals(""))
						&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
					valid = false;
				}
			} else if (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals("")) {
				valid = false;
			}
		}
		
		if (mDynamicForm.getValue("uriFileAllegato") != null && !"".equals(mDynamicForm.getValue("uriFileAllegato"))) {
			if (infoFileAllegato == null || infoFileAllegato.getMimetype() == null || infoFileAllegato.getMimetype().equals("")) {
				valid = false;
			} else if (infoFileAllegato != null && !validateFormatoFileAllegato(infoFileAllegato)) {
				valid = false;
			} else if (infoFileAllegato != null && infoFileAllegato.getBytes() <= 0) {
				valid = false;
			}
			
			// il tipo del file caricato non è tra quelli accettati
			if (infoFileAllegato != null && !((AllegatiItem) getItem()).isFormatoAmmesso(infoFileAllegato)) {
				valid = false;
			} else if(flgParteDispositivo && infoFileAllegato != null && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(infoFileAllegato)) {
				valid = false;
			}
			
			if (flgParteDispositivo && !showFileOmissis && infoFileAllegato != null && !PreviewWindow.isPdfConvertibile(infoFileAllegato) && !flgPubblicaSeparato) {
				valid = false;	
			}

			if(infoFileAllegato != null && infoFileAllegato.isFirmato()) {
				if(!infoFileAllegato.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
					if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
						valid = false;
					} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
						valid = false;
					} 
				} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
					// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
				}
			}
			
			// Se è un pdf protetto lo pubblico separatamente
			if (flgParteDispositivo && !showFileOmissis && ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti() && infoFileAllegato != null && infoFileAllegato.isPdfProtetto() && !flgPubblicaSeparato) {
				valid = false;	
			}
										
			if(flgParteDispositivo && !showFileOmissis && dimMaxAllegatoXPubblInMB > 0 && infoFileAllegato != null && infoFileAllegato.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
				valid = false;	
			}
		}

		if(showFileOmissis) {
		
			InfoFileRecord infoFileOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null ? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis")) : null;

			if(flgParteDispositivo && !flgNoPubblAllegato && (uriFileOmissisItem.getValue() == null || uriFileOmissisItem.getValue().equals(""))) {
				valid = false;
			}

			if (mDynamicFormOmissis.getValue("uriFileOmissis") != null && !"".equals(mDynamicFormOmissis.getValue("uriFileOmissis"))) {
				if (infoFileOmissis == null || infoFileOmissis.getMimetype() == null || infoFileOmissis.getMimetype().equals("")) {
					valid = false;
				} else if (infoFileOmissis != null && !validateFormatoFileAllegato(infoFileOmissis)) {
					valid = false;
				} else if (infoFileOmissis != null && infoFileOmissis.getBytes() <= 0) {
					valid = false;
				}
				
				// il tipo del file caricato non è tra quelli accettati
				if (infoFileOmissis != null && !((AllegatiItem) getItem()).isFormatoAmmesso(infoFileOmissis)) {
					valid = false;
				} else if(flgParteDispositivo && infoFileOmissis != null && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(infoFileOmissis)) {
					valid = false;
				}
				
				if (flgParteDispositivo && infoFileOmissis != null && !PreviewWindow.isPdfConvertibile(infoFileOmissis) && !flgPubblicaSeparato) {
					valid = false;	
				}
				
				if(infoFileOmissis != null && infoFileOmissis.isFirmato()) {
					if(!infoFileOmissis.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
						if ("sempre".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide)) {
							valid = false;
						} else if ("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
							valid = false;
						} 
					} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
						// il fatto che un file firmato sia pubblicato separatamente per il momento resta un suggerimento, e non un vincolo obbligatorio
					}
				}
				
				// Se è un pdf protetto lo pubblico separatamente
				if (flgParteDispositivo && ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti() && infoFileOmissis != null && infoFileOmissis.isPdfProtetto() && !flgPubblicaSeparato) {
					valid = false;	
				}
							
				if(flgParteDispositivo && dimMaxAllegatoXPubblInMB > 0 && infoFileOmissis != null && infoFileOmissis.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE) && !(flgNoPubblAllegato && flgPubblicaSeparato)) {						
					valid = false;	
				}
			}
		}
		
		return valid;
	}

	public void clickAcquisisciDaScanner() {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(String filePdf, String uriPdf, String fileTif, String uriTif, String record) {
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				mDynamicForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFileAllegato();
				}
				mDynamicForm.setValue("nomeFileAllegato", filePdf);
				mDynamicForm.setValue("uriFileAllegato", uriPdf);
				mDynamicForm.setValue("nomeFileAllegatoTif", fileTif);
				mDynamicForm.setValue("uriFileAllegatoTif", uriTif);
				mDynamicForm.setValue("remoteUri", false);
				mDynamicForm.setValue("nomeFileVerPreFirma", filePdf);
				mDynamicForm.setValue("uriFileVerPreFirma", uriPdf);
				mDynamicForm.setValue("improntaVerPreFirma", info.getImpronta());
				mDynamicForm.setValue("infoFileVerPreFirma", info);				
				controlAfterUpload(info);				
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}

	public void clickFirma() {
		firma(null);
	}

	public void firma(final ServiceCallback<Record> callback) {
		String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		// FirmaWindow firmaWindow = new FirmaWindow(uri, display, lInfoFileRecord) {
		// @Override
		// public void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record) {
		// InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		// String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		// InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
		// mDynamicForm.setValue("infoFile", info);
		// if(precImpronta == null || !precImpronta.equals(info.getImpronta())) {
		// manageChangedFileAllegato();
		// }
		// mDynamicForm.setValue("nomeFileAllegato", nomeFileFirmato);
		// mDynamicForm.setValue("uriFileAllegato", uriFileFirmato);
		// mDynamicForm.setValue("nomeFileAllegatoTif", "");
		// mDynamicForm.setValue("uriFileAllegatoTif", "");
		// mDynamicForm.setValue("remoteUri", false);
		// controlAfterUpload(info);
		// mDynamicForm.markForRedraw();
		// redrawButtons();
		// ((AllegatiItem)getItem()).manageOnChanged();
		// changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
		// }
		// };
		// firmaWindow.show();
		((AllegatiItem) getItem()).manageOnClickFirma(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				mDynamicForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFileAllegato();
				}
				mDynamicForm.setValue("nomeFileAllegato", nomeFileFirmato);
				mDynamicForm.setValue("uriFileAllegato", uriFileFirmato);
				mDynamicForm.setValue("nomeFileAllegatoTif", "");
				mDynamicForm.setValue("uriFileAllegatoTif", "");
				mDynamicForm.setValue("remoteUri", false);
				controlAfterUpload(info);		
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
				if (callback != null) {
					callback.execute(new Record(mDynamicForm.getValues()));
				}
			}
		});
	}
	
	/**
	 *  06/03 FIXME: METODO IDENTICO A clickTimbraDatiSegnatura
	 *  
	 *  Apponi segnatura di registrazione oppure Timbra
	 */
	public void clickApponiSegnaturaTimbra() {
		String nomeFile = mDynamicForm.getValueAsString("nomeFileAllegato");
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		String remote = mDynamicForm.getValueAsString("remoteUri");
		String idDoc = mDynamicForm.getValueAsString("idDocAllegato");
		
		InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
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
			
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		}else{
			
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, nomeFile, Boolean.valueOf(remote), 
					precInfo.getMimetype(), idUd, idDoc, rotazioneTimbroPref,posizioneTimbroPref);
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	/*
	 * Barcode multipli su etichetta
	 */
	public void clickBarcodeSuEtichettaMultipli() {
				
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
			
			
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		record.setAttribute("provenienza", "A");
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

	public void clickAnteprimaDaModello() {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGeneraDaModelloDataSource", new String[] { "codice", "descrizione" }, true);
		lGwtRestDataSource.addParam("idTipoDocumento", getTipoAllegato());
		lGwtRestDataSource.addParam("idProcess", ((AllegatiItem) getItem()).getIdProcess());
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if (data.getLength() > 1) {
					SelezionaGeneraDaModelloWindow generaDaModelloWindow = new SelezionaGeneraDaModelloWindow(data) {

						@Override
						public void caricaModelloSelezionato(String idModello, String tipoModello, String flgConvertiInPdf) {
							afterSelezioneModelloXAnteprima(idModello, tipoModello, flgConvertiInPdf);
						}
					};
					generaDaModelloWindow.show();
				} else if (data.getLength() == 1) {
					String idModello = data.get(0).getAttribute("idModello");
					if (idModello != null && !"".equals(idModello)) {
						afterSelezioneModelloXAnteprima(idModello, data.get(0).getAttribute("tipoModello"), data.get(0).getAttribute("flgConvertiInPdf"));
					}
				} else {
					Layout.addMessage(new MessageBean("Nessun modello da caricare!", "", MessageType.INFO));
				}
			}
		});
	}

	public void clickPreviewFile() {
		final Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		final String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		final String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
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
						preview(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		if(isModificabileSoloVersOmissis() && !isNonModificabileVersOmissis()) {
			PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
				
				@Override
				public void executeSalvaVersConOmissis(Record record) {
					
				}
				
				@Override
				public void executeSalva(Record record) {				
					if (((AllegatiItem) getItem()).getShowVersioneOmissis()) {
						String uri = record.getAttribute("uri");
						String displayFileName = record.getAttribute("fileName");
						Record infoFile = record.getAttributeAsRecord("infoFile");
						mDynamicForm.setValue("flgDatiSensibili", true);
						flgDatiSensibiliItem.setValue(true);
						mDynamicForm.markForRedraw();
						mDynamicFormOmissis.show();	
						fileOmissisButtons.updateAfterUpload(displayFileName, uri, infoFile);
					}
				}
				
				@Override
				public void executeOnError() {	
					
				}				
			};
			
			PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, false, false, ((AllegatiItem) getItem()).isShowModalPreview());						
		} else {
			PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
				
				@Override
				public void executeSalvaVersConOmissis(Record record) {
					if (((AllegatiItem) getItem()).getShowVersioneOmissis()) {
						String uri = record.getAttribute("uri");
						String displayFileName = record.getAttribute("fileName");
						Record infoFile = record.getAttributeAsRecord("infoFile");
						mDynamicForm.setValue("flgDatiSensibili", true);
						flgDatiSensibiliItem.setValue(true);
						mDynamicForm.markForRedraw();
						mDynamicFormOmissis.show();	
						fileOmissisButtons.updateAfterUpload(displayFileName, uri, infoFile);
					}
				}
				
				@Override
				public void executeSalva(Record record) {
					
					String uri = record.getAttribute("uri");
					String displayFileName = record.getAttribute("fileName");
					InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
					InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
					String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
					mDynamicForm.setValue("infoFile", info);
					if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
						manageChangedFileAllegato();
					}
					mDynamicForm.setValue("nomeFileAllegato", displayFileName);
					mDynamicForm.setValue("uriFileAllegato", uri);
					mDynamicForm.setValue("nomeFileAllegatoTif", "");
					mDynamicForm.setValue("uriFileAllegatoTif", "");
					mDynamicForm.setValue("remoteUri", false);
					mDynamicForm.setValue("nomeFileVerPreFirma", displayFileName);
					mDynamicForm.setValue("uriFileVerPreFirma", uri);
					mDynamicForm.setValue("improntaVerPreFirma", info.getImpronta());
					mDynamicForm.setValue("infoFileVerPreFirma", info);	
					controlAfterUpload(info);
					mDynamicForm.markForRedraw();
					redrawButtons();
					((AllegatiItem) getItem()).manageOnChanged();
					changedEventAfterUpload(displayFileName, uri);
					
				}
				
				@Override
				public void executeOnError() {				
				}
			};
	 
			boolean isViewMode = !(getEditing() && allegatoButtons.isEditing() && !isModificabileSoloVersOmissis());
			boolean enableOptionToSaveInOmissisForm = ((AllegatiItem) getItem()).getShowVersioneOmissis();
			PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, lWindowPageSelectionCallback, isViewMode, enableOptionToSaveInOmissisForm, ((AllegatiItem) getItem()).isShowModalPreview());
		}		
	}

	public void clickPreviewEditFile() {
		final Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		final String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		final String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
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
						previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	protected boolean showEditButton() {
		final String display = mDynamicForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = mDynamicForm.getValueAsString("uriFileVerPreFirma");
		if (((AllegatiItem) getItem()).canBeEditedByApplet()) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
				if (uri != null && !uri.equals("")) {
					String estensione = null;
					/**
					 * File corrente non firmato
					 */
					Object infoFile = mDynamicForm.getValue("infoFile");
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
					Object infoFilePreVer = mDynamicForm.getValue("infoFileVerPreFirma");
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
				} else
					return false;
			} else
				return false;
		} else
			return false;
	}

	protected void clickEditFile() {
		final Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		final String display = mDynamicForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = mDynamicForm.getValueAsString("uriFileVerPreFirma");
		final Boolean remoteUri = Boolean.valueOf(mDynamicForm.getValueAsString("remoteUri"));
		
		final InfoFileRecord info = new InfoFileRecord(mDynamicForm.getValue("infoFile"));

		if (info != null && info.isFirmato()) {
			SC.ask("Modificando il file perderai la/le firme già apposte. Vuoi comunque procedere?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value){
						InfoFileRecord infoPreFirma = new InfoFileRecord(mDynamicForm.getValue("infoFileVerPreFirma"));
						editFile(infoPreFirma, display, uri, remoteUri);
					}
				}
			});
		}
		else
			editFile(info, display, uri, remoteUri);
	}

	private void editFile(InfoFileRecord info, String display, String uri, Boolean remoteUri) {
		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		}
		if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = mDynamicForm.getValueAsString("improntaVerPreFirma");
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
					openEditFileWindow(displaySbustato, uriSbustato, false, estensioneSbustato, improntaSbustato);
				}
			});
		} else {
			openEditFileWindow(display, uri, remoteUri, estensione, impronta);
		}
	}

	public void openEditFileWindow(String display, String uri, Boolean remoteUri, String estensione, String impronta) {
		OpenEditorUtility.openEditor(display, uri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFile, String uri, String infoFile) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(infoFile);
				InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				mDynamicForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedFileAllegato();
				}
				mDynamicForm.setValue("nomeFileAllegato", nomeFile);
				mDynamicForm.setValue("uriFileAllegato", uri);
				mDynamicForm.setValue("nomeFileAllegatoTif", "");
				mDynamicForm.setValue("uriFileAllegatoTif", "");
				mDynamicForm.setValue("remoteUri", false);
				mDynamicForm.setValue("nomeFileVerPreFirma", nomeFile);
				mDynamicForm.setValue("uriFileVerPreFirma", uri);
				mDynamicForm.setValue("improntaVerPreFirma", info.getImpronta());
				mDynamicForm.setValue("infoFileVerPreFirma", info);				
				controlAfterUpload(info);
				mDynamicForm.markForRedraw();
				redrawButtons();
				((AllegatiItem) getItem()).manageOnChanged();
				changedEventAfterUpload(nomeFile, uri);
			}
		});
	}

	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri,
			InfoFileRecord info) {
		
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
					public void uploadCallBack(String filePdf, String uriPdf, String record) {
						InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						mDynamicForm.setValue("infoFile", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							manageChangedFileAllegato();
						}
						mDynamicForm.setValue("nomeFileAllegato", filePdf);
						mDynamicForm.setValue("uriFileAllegato", uriPdf);
						mDynamicForm.setValue("nomeFileAllegatoTif", "");
						mDynamicForm.setValue("uriFileAllegatoTif", "");
						mDynamicForm.setValue("remoteUri", false);
						mDynamicForm.setValue("nomeFileVerPreFirma", filePdf);
						mDynamicForm.setValue("uriFileVerPreFirma", uriPdf);
						mDynamicForm.setValue("improntaVerPreFirma", info.getImpronta());
						mDynamicForm.setValue("infoFileVerPreFirma", info);						
						controlAfterUpload(info);
						mDynamicForm.markForRedraw();
						redrawButtons();
						((AllegatiItem) getItem()).manageOnChanged();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

	// Download del file
	protected void clickDownloadFile() {
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", mDynamicForm.getValueAsString("remoteUri"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	public void clickDownloadFileSbustato() {
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		String display = mDynamicForm.getValueAsString("nomeFileAllegato");
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", mDynamicForm.getValueAsString("remoteUri"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(mDynamicForm.getValue("infoFile"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Cancellazione del file
	protected void clickEliminaFile() {
		mDynamicForm.setValue("nomeFileAllegato", "");
		mDynamicForm.setValue("uriFileAllegato", "");
		mDynamicForm.clearValue("infoFile");
		mDynamicForm.setValue("nomeFileAllegatoTif", "");
		mDynamicForm.setValue("uriFileAllegatoTif", "");
		mDynamicForm.setValue("remoteUri", false);	
		mDynamicForm.setValue("nomeFileVerPreFirma", "");
		mDynamicForm.setValue("uriFileVerPreFirma", "");
		mDynamicForm.setValue("improntaVerPreFirma", "");
		mDynamicForm.clearValue("infoFileVerPreFirma");
		mDynamicForm.clearValue("isUdSenzaFileImportata");
		mDynamicForm.markForRedraw();
		redrawButtons();
		((AllegatiItem) getItem()).manageOnChanged();
		uploadAllegatoItem.getCanvas().redraw();
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

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm, mDynamicFormOmissis };
	}

	protected void changedEventAfterUpload(final String displayFileName, final String uri) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFileAllegatoItem.getJsObj()) {

			public DynamicForm getForm() {
				return mDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return nomeFileAllegatoItem;
			}

			@Override
			public Object getValue() {

				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriFileAllegatoItem.getJsObj()) {

			public DynamicForm getForm() {
				return mDynamicForm;
			};

			@Override
			public FormItem getItem() {
				return uriFileAllegatoItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileAllegatoItem.fireEvent(lChangedEventDisplay);
		uriFileAllegatoItem.fireEvent(lChangedEventUri);
	}
	
	public boolean isUdAllegato() {
		String idUdAllegato = mDynamicForm.getValueAsString("idUdAllegato");
		return (idUdAllegato != null && !"".equals(idUdAllegato));
	}

	public boolean isIdUdAppartenenzaValorizzato() {
		String idUdAppartenenza = mDynamicForm.getValueAsString("idUdAppartenenza");
		return idUdAppartenenza != null && !"".equals(idUdAppartenenza);
	}
	
	public boolean isUdProtocollata() {
		String estremiProtUd = mDynamicForm.getValueAsString("estremiProtUd");
		return estremiProtUd != null && !"".equals(estremiProtUd);
	}
	
	public boolean isDocFirmato() {
		InfoFileRecord lInfoFileRecord = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;
		return lInfoFileRecord != null && lInfoFileRecord.isFirmato();
	}

	public boolean isAbilToProt(String flgTipoProv) {
		if(flgTipoProv != null && mDynamicForm.getValueAsString("flgTipoProvXProt") != null) {
			String[] flgTipoProvXProtArray = new StringSplitterClient(mDynamicForm.getValueAsString("flgTipoProvXProt"), ";").getTokens();
			for (int i = 0; i < flgTipoProvXProtArray.length; i++) {
				if(flgTipoProv.equalsIgnoreCase(flgTipoProvXProtArray[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	// se è un allegato generato da modello a completamento task
	public boolean isAllegatoModelloAtti() {
		HashSet<String> tipiModelliAtti = (getItem() instanceof AllegatiItem) ? ((AllegatiItem) getItem()).getTipiModelliAttiAllegati() : null;
		return (listaTipiFileAllegatoItem.getValue() != null && tipiModelliAtti != null && tipiModelliAtti.contains(listaTipiFileAllegatoItem.getValue()))
				|| (descrizioneFileAllegatoItem.getValue() != null
						&& descrizioneFileAllegatoItem.getValue().equals(DettaglioPraticaLayout.getNomeModelloPubblicazione()));
	}

	// se è un allegato è appena stato creato o è stato salvato nel task corrente
	public boolean isNewOrSavedInTaskCorrente() {
		String idTaskCorrente = (getItem() instanceof AllegatiItem) ? ((AllegatiItem) getItem()).getIdTaskCorrenteAllegati() : null;
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		String idTaskAllegato = mDynamicForm.getValueAsString("idTask");
		// IMPORTANTE: se non sono negli atti ma arrivo dalla maschera di dettaglio UD questo metodo dovrà tornare sempre true o altrimenti gli allegati risulterebbero non editabili quando invece dovrebbero esserlo
		// in questo caso sono a posto perchè se arrivo dal dettaglio UD la condizione (idTaskAllegato == null && !isReadOnly()) tornera sempre true, quindi non va' assolutamente tolta
		return (idDocAllegato == null || "".equals(idDocAllegato)) || (idTaskAllegato == null && !isReadOnly()) || (idTaskCorrente != null && idTaskAllegato != null && idTaskCorrente.equalsIgnoreCase(idTaskAllegato));		
	}

	public void readOnlyMode() {
		setCanEdit(true);
		if (!isNewOrSavedInTaskCorrente() || isAllegatoModelloAtti() || isUdProtocollata()) {
			if (getRemoveButton() != null) {
				getRemoveButton().setAlwaysDisabled(true);
			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		} else if (isUdAllegato()) {
//			if (getRemoveButton() != null) {
//				getRemoveButton().setAlwaysDisabled(true); // la riga deve essere cancellabile
//						
//			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(true); // la descrizione deve essere editabile			
			nomeFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		} else {
			flgProvEsternaItem.setCanEdit(true);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(true);
			descrizioneFileAllegatoItem.setCanEdit(true);
			nomeFileAllegatoItem.setCanEdit(true);
			allegatoButtons.setCanEdit(true);
			uploadAllegatoItem.setCanEdit(true);
			flgDatiSensibiliItem.setCanEdit(true);
			nomeFileOmissisItem.setCanEdit(true);			
			fileOmissisButtons.setCanEdit(true);
//			uploadOmissisItem.setCanEdit(true);
		}
	}
	
	public void soloPreparazioneVersPubblicazioneMode() {
		setCanEdit(true);
		if (getRemoveButton() != null) {
			getRemoveButton().setAlwaysDisabled(true);
		}
		flgProvEsternaItem.setCanEdit(false);
		flgParereItem.setCanEdit(false);
		flgParteDispositivoItem.setCanEdit(false);
		flgNoPubblAllegatoItem.setCanEdit(false);
		flgPubblicaSeparatoItem.setCanEdit(false);
		listaTipiFileAllegatoItem.setCanEdit(false);
		descrizioneFileAllegatoItem.setCanEdit(false);
		nomeFileAllegatoItem.setCanEdit(false);
		allegatoButtons.setCanEdit(false);
		uploadAllegatoItem.setCanEdit(false);
		if(!isNonModificabileVersOmissis()) {
			flgDatiSensibiliItem.setCanEdit(true);
			nomeFileOmissisItem.setCanEdit(true);
			fileOmissisButtons.setCanEdit(true);
//			uploadOmissisItem.setCanEdit(true);
		} else {
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}
	
	public void soloOmissisModProprietaFileMode() {
		setCanEdit(true);
		boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null	&& new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
		if(flgParteDispositivo) {
			if (getRemoveButton() != null) {
				getRemoveButton().setAlwaysDisabled(true);
			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(true);
			flgNoPubblAllegatoItem.setCanEdit(true);
			flgPubblicaSeparatoItem.setCanEdit(true);
			listaTipiFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(true);
			nomeFileOmissisItem.setCanEdit(true);
			fileOmissisButtons.setCanEdit(true);
	//		uploadOmissisItem.setCanEdit(true);
		} else {
			flgProvEsternaItem.setCanEdit(true);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(true);
			descrizioneFileAllegatoItem.setCanEdit(true);
			nomeFileAllegatoItem.setCanEdit(true);
			allegatoButtons.setCanEdit(true);
			uploadAllegatoItem.setCanEdit(true);
			flgDatiSensibiliItem.setCanEdit(true);
			nomeFileOmissisItem.setCanEdit(true);
			fileOmissisButtons.setCanEdit(true);
//			uploadOmissisItem.setCanEdit(true);
		}
	}
	
	public void attivaModificaEsclusionePubblicazioneMode(){
		boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null	&& new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
		if (flgParteDispositivo){
			flgNoPubblAllegatoItem.setCanEdit(true);			
		}
	}

	public void aggiuntaFileMode() {
		setCanEdit(true);
		if ((uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals("")) && !isUdSenzaFileImportata() && !isUdAllegato()) {
			nomeFileAllegatoItem.setCanEdit(true);
			allegatoButtons.setCanEdit(true);
			uploadAllegatoItem.setCanEdit(true);
		} else {
			nomeFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);	
		}		
		if ((uriFileOmissisItem.getValue() == null || uriFileOmissisItem.getValue().equals("")) && !isUdSenzaFileImportata() && !isUdAllegato()) {
			flgDatiSensibiliItem.setCanEdit(true);
			nomeFileOmissisItem.setCanEdit(true);			
			fileOmissisButtons.setCanEdit(true);
//			uploadOmissisItem.setCanEdit(true);
		} else {
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}
	
	public void canEditOnlyOmissisMode() {
		flgDatiSensibiliItem.setCanEdit(true);
		nomeFileOmissisItem.setCanEdit(true);			
		fileOmissisButtons.setCanEdit(true);
		flgNoPubblAllegatoItem.setDisabled(false);
		flgNoPubblAllegatoItem.setCanEdit(true);
		setEditing(true);
		allegatoButtons.setCanEdit(true);
		if (getRemoveButton() != null) {
			getRemoveButton().setAlwaysDisabled(true);
		}
	}

	public void mostraVariazione(String datoAnnullato) {
		if (datoAnnullato.equals("#TipoAllegato")) {
			listaTipiFileAllegatoItem.setCellStyle(it.eng.utility.Styles.formCellVariazione);
			listaTipiFileAllegatoItem.redraw();
		} else if (datoAnnullato.equals("#DesAllegato")) {
			descrizioneFileAllegatoItem.setCellStyle(it.eng.utility.Styles.formCellVariazione);
			descrizioneFileAllegatoItem.redraw();
		} else if (datoAnnullato.equals("#FileAllegato")) {
			nomeFileAllegatoItem.setCellStyle(it.eng.utility.Styles.formCellVariazione);
			nomeFileAllegatoItem.redraw();
		}
	}

	@Override
	public void editRecord(Record record) {

		valuesOrig = record.toMap();
		
		if (record.getAttribute("idTipoFileAllegato") != null && !"".equals(record.getAttributeAsString("idTipoFileAllegato"))) {
			LinkedHashMap<String, String> listaTipiFileAllegatoValueMap = new LinkedHashMap<String, String>();
			listaTipiFileAllegatoValueMap.put(record.getAttribute("idTipoFileAllegato"), record.getAttribute("descTipoFileAllegato"));
			listaTipiFileAllegatoItem.setValueMap(listaTipiFileAllegatoValueMap);
		}
		
		GWTRestDataSource listaTipiFileAllegatoDS = (GWTRestDataSource) listaTipiFileAllegatoItem.getOptionDataSource();
		if (record.getAttribute("idTipoFileAllegato") != null && !"".equals(record.getAttributeAsString("idTipoFileAllegato"))) {
			listaTipiFileAllegatoDS.addParam("idTipoFileAllegato", record.getAttributeAsString("idTipoFileAllegato"));
		} else {
			listaTipiFileAllegatoDS.addParam("idTipoFileAllegato", null);
		}
		if (record.getAttribute("dictionaryEntrySezione") != null && !"".equals(record.getAttributeAsString("dictionaryEntrySezione"))) {
			listaTipiFileAllegatoDS.addParam("dictionaryEntrySezione", record.getAttributeAsString("dictionaryEntrySezione"));
		} else if(((AllegatiItem)getItem()).getDictionaryEntrySezione()  != null && !"".equals(((AllegatiItem)getItem()).getDictionaryEntrySezione())) {
			listaTipiFileAllegatoDS.addParam("dictionaryEntrySezione", ((AllegatiItem)getItem()).getDictionaryEntrySezione());
		} else {
			listaTipiFileAllegatoDS.addParam("dictionaryEntrySezione", null);
		}
		listaTipiFileAllegatoItem.setOptionDataSource(listaTipiFileAllegatoDS);
		
		record.setAttribute("nomeFileAllegatoOrig", record.getAttribute("nomeFileAllegato"));
		record.setAttribute("nomeFileOrigOmissis", record.getAttribute("nomeFileOmissis"));
		
		boolean flgDatiSensibili = record.getAttribute("flgDatiSensibili") != null ? new Boolean(record.getAttribute("flgDatiSensibili")) : false;
		boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;		
		
		// per sicurezza commento questo pezzo perchè quando arrivo da dalla popup di dettaglio della grid, modificando il record da settare sul canvas in realta sto' anche modificando il record della grid
//		if(!showFileOmissis) {
//			record.setAttribute("nomeFileOmissis", "");
//			record.setAttribute("uriFileOmissis", "");
//			record.setAttribute("infoFileOmissis", (Object) null);	
//			record.setAttribute("nomeFileTifOmissis", "");
//			record.setAttribute("uriFileTifOmissis", "");
//			record.setAttribute("remoteUriOmissis", false);	
//			record.setAttribute("nomeFileVerPreFirmaOmissis", "");
//			record.setAttribute("uriFileVerPreFirmaOmissis", "");
//			record.setAttribute("improntaVerPreFirmaOmissis", "");
//			record.setAttribute("infoFileVerPreFirmaOmissis", (Object) null);					
//		}

		super.editRecord(record);
			
		if(showFlgParere()) {
			boolean flgParere = record.getAttributeAsBoolean("flgParere");
			mDynamicForm.setValue("flgParere", flgParere);
			if(flgParere) {
				if(showFlgParteDispositivo()) mDynamicForm.setValue("flgParteDispositivo", false);
				if(showFlgNoPubblAllegato()) {
					boolean flgNoPubblAllegato = record.getAttributeAsBoolean("flgNoPubblAllegato");							
					mDynamicForm.setValue("flgNoPubblAllegato", flgNoPubblAllegato);
				}
				if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", true);
			} else if(showFlgParteDispositivo()) {
				boolean flgParteDispositivo = record.getAttributeAsBoolean("flgParteDispositivo");									
				mDynamicForm.setValue("flgParteDispositivo", flgParteDispositivo);
				if(!flgParteDispositivo) {
					if(showFlgNoPubblAllegato()) mDynamicForm.setValue("flgNoPubblAllegato", true);
					if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", false);
					if(showFileOmissis) {
						flgDatiSensibili = false;
						showFileOmissis = false;
						mDynamicForm.setValue("flgDatiSensibili", false);						
						manageOnChangedFlgDatiSensibili();
					}
				} else {
					if(showFlgNoPubblAllegato()) {
						boolean flgNoPubblAllegato = record.getAttributeAsBoolean("flgNoPubblAllegato");						
						mDynamicForm.setValue("flgNoPubblAllegato", flgNoPubblAllegato);							
					}
					if(showFlgPubblicaSeparato()) {
						boolean flgPubblicaSeparato = record.getAttributeAsBoolean("flgPubblicaSeparato");									
						mDynamicForm.setValue("flgPubblicaSeparato", flgPubblicaSeparato);							
					}
				}
			}
		} else if(showFlgParteDispositivo()) {
			boolean flgParteDispositivo = record.getAttributeAsBoolean("flgParteDispositivo");									
			mDynamicForm.setValue("flgParteDispositivo", flgParteDispositivo);
			if(!flgParteDispositivo) {
				if(showFlgNoPubblAllegato()) mDynamicForm.setValue("flgNoPubblAllegato", true);
				if(showFlgPubblicaSeparato()) mDynamicForm.setValue("flgPubblicaSeparato", false);
				if(showFileOmissis) {
					flgDatiSensibili = false;
					showFileOmissis = false;
					mDynamicForm.setValue("flgDatiSensibili", false);
					manageOnChangedFlgDatiSensibili();
				}
			} else {
				if(showFlgNoPubblAllegato()) {
					boolean flgNoPubblAllegato = record.getAttributeAsBoolean("flgNoPubblAllegato");							
					mDynamicForm.setValue("flgNoPubblAllegato", flgNoPubblAllegato);							
				}
				if(showFlgPubblicaSeparato()) {
					boolean flgPubblicaSeparato = record.getAttributeAsBoolean("flgPubblicaSeparato");									
					mDynamicForm.setValue("flgPubblicaSeparato", flgPubblicaSeparato);							
				}
			}
		}
		
		if(showFileOmissis) {
			mDynamicFormOmissis.show();			
		} else {
			mDynamicFormOmissis.hide();
		}
		
		mDynamicForm.markForRedraw();
		redrawButtons();
	}

	public void setNomeFileWidth(Integer nomeFileWidth) {
		this.nomeFileWidth = nomeFileWidth;
	}

	private void manageFileFirmatoButtonClick() {

		final InfoFileRecord infoFileAllegato = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
		final String uriFileAllegato = (String) uriFileAllegatoItem.getValue();

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

	private void generaFileFirmaAndPreview(final InfoFileRecord infoFileAllegato, String uriFileAllegato, boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));		
		if(((AllegatiItem) getItem()).getDataRifValiditaFirma() != null) {
			String dataRiferimento = DateUtil.format(((AllegatiItem) getItem()).getDataRifValiditaFirma());
			lGwtRestDataSource.extraparam.put("dataRiferimento", dataRiferimento);
		}
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				// String url = (GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" +
				// URL.encode(uri));
				// preview(null, null, filename, uri, false, infoFile);
				PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", filename, ((AllegatiItem) getItem()).isShowModalPreview());
				// new PreviewWindow(uri, false, infoFile, "FileToExtractBean", filename);
			}
		});

	}
	
	public String getTipoAllegato() {
		String tipoAllegato = showTipoAllegato() ? (String) mDynamicForm.getValue("listaTipiFileAllegato") : null;
		if(tipoAllegato == null || "".equals(tipoAllegato)) {
			tipoAllegato = idTipoFileAllegato != null && !"".equals(idTipoFileAllegato) ? idTipoFileAllegato : ((AllegatiItem)getItem()).getFixedTipoAllegato();
		}
		return tipoAllegato;
	}

	public NestedFormItem getAllegatoButtons() {
		return allegatoButtons;
	}
	
	public void setForceToShowGeneraDaModelloForTipoTaskDoc(boolean forceToShowGeneraDaModelloForTipoTaskDoc) {
		this.forceToShowGeneraDaModelloForTipoTaskDoc = forceToShowGeneraDaModelloForTipoTaskDoc;
	}

	public boolean isReadOnly() {
		return (getItem() instanceof AllegatiItem) ? ((AllegatiItem) getItem()).isReadOnly() : false;
	}
	
	public boolean isSoloPreparazioneVersPubblicazioneMode() {
		return (getItem() instanceof AllegatiItem) ? ((AllegatiItem) getItem()).isSoloPreparazioneVersPubblicazione() : false;
	}
	
	public boolean isSoloOmissisModProprietaFileMode() {
		return (getItem() instanceof AllegatiItem) ? ((AllegatiItem) getItem()).isSoloOmissisModProprietaFile() : false;
	}
	
	public boolean isCanEditOnlyOmissisMode() {
		return (getItem() instanceof AllegatiItem) ? ((AllegatiItem) getItem()).isCanEditOnlyOmissis() : false;
	}
	
	public boolean isFlgPubblicaSeparato() {
		if(getItem() instanceof AllegatiItem) {
			if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem() && ((AllegatiItem)getItem()).isFlgPubblicaAllegatiSeparati()) {
				return true;
			} else if(((AllegatiItem)getItem()).isShowFlgPubblicaSeparato()) {
				if(((AllegatiItem)getItem()).isFlgPubblicazioneAllegatiUguale()) {
					return ((AllegatiItem)getItem()).isFlgPubblicaAllegatiSeparati();
				} else {
					return mDynamicForm.getValue("flgPubblicaSeparato") != null	&& new Boolean(mDynamicForm.getValueAsString("flgPubblicaSeparato"));	
				}
			}	
		}
		return false;
	}
	
	public boolean isFlgOmissis() {
		boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
		return flgDatiSensibili;
	}
	
	public boolean isNonModificabileVersOmissis() {
		if(isSoloPreparazioneVersPubblicazioneMode()) {
			boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null	&& new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
			boolean flgNoPubblAllegato = mDynamicForm.getValue("flgNoPubblAllegato") != null	&& new Boolean(mDynamicForm.getValueAsString("flgNoPubblAllegato"));
			boolean flgPubblicaSeparato = isFlgPubblicaSeparato();
			boolean isModificabileVersOmissis = flgParteDispositivo && !flgNoPubblAllegato && flgPubblicaSeparato;
			return !isModificabileVersOmissis;
		}
		return false;
	}
	
	public boolean isModificabileSoloVersOmissis() {
		if(isSoloPreparazioneVersPubblicazioneMode()) {
			return true;
		} else if(isSoloOmissisModProprietaFileMode()) {
			boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null	&& new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
			if(flgParteDispositivo) {
				return true;
			} else {
				return false;
			}
		} else if(isCanEditOnlyOmissisMode()) {
			return true;
		}  		
		return false;
	}
	
	
	public void redrawCanEditAfterChangedTipoDocIstruttoriaDettFascicoloGenCompleto() {
		super.setCanEdit(getEditing());
		numeroAllegatoItem.setCanEdit(false);
		numeroAllegatoItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		if (isAllegatoModelloAtti()) {			
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {

		super.setCanEdit(canEdit);
		numeroAllegatoItem.setCanEdit(false);
		numeroAllegatoItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		if (isReadOnly()) {
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
		}
		if(isSoloOmissisModProprietaFileMode() && !((AllegatiItem) getItem()).isAllegatiNonParteIntegranteNonEditabili()) {			
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
		}
		if (!isNewOrSavedInTaskCorrente() || isAllegatoModelloAtti() || isUdProtocollata()) {
			if (getRemoveButton() != null) {
				getRemoveButton().setAlwaysDisabled(true);
			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		} else if (isUdAllegato()) {
//			if (getRemoveButton() != null) {
//				getRemoveButton().setAlwaysDisabled(true); // la riga deve essere cancellabile
//						
//			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(canEdit); // la descrizione deve essere editabile se la maschera è editabile	
			nomeFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
//		fileOmissisAllegatoItem.setCanEdit(canEdit);
	}

	private void creaAttestato(String idUd, String idDoc, final InfoFileRecord infoFileAllegato, String uriFileAllegato, final boolean attestatoFirmato) {

		Record estremiProtocollo = ((AllegatiItem) getItem()).getDetailRecord();

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

	public void setFormValuesFromRecordImportaFilePrimario(Record record) {		
		String idUd = record.getAttribute("idUd");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";
		String idDocType = null; //record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
		String nomeDocType = null; //record.getAttribute("nomeTipoDocumento") != null ? record.getAttribute("nomeTipoDocumento") : "";
		String displayFileName = record.getAttribute("nomeFilePrimario");
		String uri = record.getAttribute("uriFilePrimario");
		mDynamicForm.setValue("nomeFileAllegato", displayFileName);
		mDynamicForm.setValue("uriFileAllegato", uri);
		mDynamicForm.setValue("nomeFileAllegatoTif", "");
		mDynamicForm.setValue("uriFileAllegatoTif", "");
		mDynamicForm.setValue("remoteUri", false);
		mDynamicForm.setValue("fileImportato", true);
		mDynamicForm.setValue("idUdAppartenenza", idUd);
		mDynamicForm.setValue("collegaDocumentoImportato", ((AllegatiItem) getItem()).isCollegaDocumentiImportati());
		mDynamicForm.setValue("descrizioneFileAllegato", descrizione);
		mDynamicForm.setValue("listaTipiFileAllegato", idDocType);
		mDynamicForm.setValue("idTipoFileAllegato", idDocType);
		mDynamicForm.setValue("descTipoFileAllegato", nomeDocType);
		mDynamicForm.setValue("isChanged", true);
		mDynamicForm.setValue("isUdSenzaFileImportata", false);
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
		mDynamicForm.setValue("infoFile", info);
//		controlAfterUpload(info); // lo faccio dopo alla fine quando ho importato tutti i file 		
		mDynamicForm.markForRedraw();
		changedEventAfterUpload(displayFileName, uri);
		if (isUdProtocollata()) {
			// siccome l'ho appena aggiunto lo devo poter cancellare
//			if (getRemoveButton() != null) {
//				getRemoveButton().setAlwaysDisabled(true);
//			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}

	public void setFormValuesFromRecordImportaFileAllegato(Record record, int numeroAllegato, String idUd, String segnatura) {		
		String idDocType = record.getAttribute("listaTipiFileAllegato") != null ? record.getAttribute("listaTipiFileAllegato") : "";
		String nomeDocType = record.getAttribute("descTipoFileAllegato") != null ? record.getAttribute("descTipoFileAllegato") : "";
		String displayFileName = record.getAttribute("nomeFileAllegato");
		String uri = record.getAttribute("uriFileAllegato");
		String descrizione = segnatura + " Allegato N. " + (numeroAllegato);
		mDynamicForm.setValue("nomeFileAllegato", displayFileName);
		mDynamicForm.setValue("uriFileAllegato", uri);
		mDynamicForm.setValue("nomeFileAllegatoTif", "");
		mDynamicForm.setValue("uriFileAllegatoTif", "");
		mDynamicForm.setValue("remoteUri", false);
		mDynamicForm.setValue("fileImportato", true);
		mDynamicForm.setValue("idUdAppartenenza", idUd);
		mDynamicForm.setValue("collegaDocumentoImportato", ((AllegatiItem) getItem()).isCollegaDocumentiImportati());
		mDynamicForm.setValue("descrizioneFileAllegato", descrizione);
		mDynamicForm.setValue("listaTipiFileAllegato", idDocType);
		mDynamicForm.setValue("idTipoFileAllegato", idDocType);
		mDynamicForm.setValue("descTipoFileAllegato", nomeDocType);
		mDynamicForm.setValue("isChanged", true);
		mDynamicForm.setValue("isUdSenzaFileImportata", false);
		InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
		mDynamicForm.setValue("infoFile", info);
//		controlAfterUpload(info); // lo faccio dopo alla fine quando ho importato tutti i file 
		mDynamicForm.markForRedraw();
		changedEventAfterUpload(displayFileName, uri);
		if (isUdProtocollata()) {
			// siccome l'ho appena aggiunto lo devo poter cancellare
//			if (getRemoveButton() != null) {
//				getRemoveButton().setAlwaysDisabled(true);
//			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}
	
	public void setFormValuesFromRecordImportaUnitaDocumentale(Record record) {
		boolean presenteFilePrimario = record.getAttribute("uriFilePrimario") != null && !"".equalsIgnoreCase(record.getAttribute("uriFilePrimario"));
		String idUd = record.getAttribute("idUd");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";
		String idDocType = record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
		String nomeDocType = record.getAttribute("nomeTipoDocumento") != null ? record.getAttribute("nomeTipoDocumento") : "";
		String estremiProtUd = record.getAttribute("estremiProtUd") != null ? record.getAttribute("estremiProtUd") : "";
		if (presenteFilePrimario) {
			mDynamicForm.setValue("nomeFileAllegato", record.getAttribute("nomeFilePrimario"));
			mDynamicForm.setValue("uriFileAllegato", record.getAttribute("uriFilePrimario"));
		} else {
			mDynamicForm.setValue("nomeFileAllegato", "");
			mDynamicForm.setValue("uriFileAllegato", "");
		}
		// idDocPrimario è prensente anche se non c'è il file primario
		mDynamicForm.setValue("idDocAllegato", record.getAttribute("idDocPrimario"));
		mDynamicForm.setValue("nomeFileAllegatoTif", "");
		mDynamicForm.setValue("uriFileAllegatoTif", "");
		mDynamicForm.setValue("remoteUri", false);
		mDynamicForm.setValue("fileImportato", true);
		mDynamicForm.setValue("idUdAppartenenza", idUd);
		mDynamicForm.setValue("collegaDocumentoImportato", ((AllegatiItem) getItem()).isCollegaDocumentiImportati());
		mDynamicForm.setValue("descrizioneFileAllegato", descrizione);
		mDynamicForm.setValue("listaTipiFileAllegato", idDocType);
		mDynamicForm.setValue("idTipoFileAllegato", idDocType);
		mDynamicForm.setValue("descTipoFileAllegato", nomeDocType);		
		// Quando importo un'unità documentale isChanged deve essere a false, altrimenti al salvataggio scatta un nuovo versionamento del documento dell'unità documentale importata.
		// Tale versionamento inoltre fallisce se l'utente non ha pemessi di modifica sull'unità documentale importata.
		mDynamicForm.setValue("isChanged", false);
		mDynamicForm.setValue("isUdSenzaFileImportata", !presenteFilePrimario); 
		mDynamicForm.setValue("estremiProtUd", estremiProtUd);
		mDynamicForm.setValue("flgIdUdAppartenenzaContieneAllegati", record.getAttributeAsBoolean("flgIdUdAppartenenzaContieneAllegati"));
		if (presenteFilePrimario) {
			InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
			mDynamicForm.setValue("infoFile", info);
			mDynamicForm.markForRedraw();
			changedEventAfterUpload(record.getAttribute("nomeFilePrimario"), record.getAttribute("uriFilePrimario"));
		}
		if (isUdProtocollata()) {
			// siccome l'ho appena aggiunto lo devo poter cancellare
//			if (getRemoveButton() != null) {
//				getRemoveButton().setAlwaysDisabled(true);
//			}
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			descrizioneFileAllegatoItem.setCanEdit(false);
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}
	
	public void setFormValuesFromRecordImportaUnitaDocumentaleAllegato(Record record) {
		String idUd = record.getAttribute("idUd");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";
		String idDocType = record.getAttribute("tipoDocumento") != null ? record.getAttribute("tipoDocumento") : "";
		String nomeDocType = record.getAttribute("nomeTipoDocumento") != null ? record.getAttribute("nomeTipoDocumento") : "";
		mDynamicForm.setValue("nomeFileAllegato", "");
		mDynamicForm.setValue("uriFileAllegato", "");
		// idDocPrimario è prensente anche se non c'è il file primario
		mDynamicForm.setValue("idDocAllegato", record.getAttribute("idDocPrimario"));
		mDynamicForm.setValue("nomeFileAllegatoTif", "");
		mDynamicForm.setValue("uriFileAllegatoTif", "");
		mDynamicForm.setValue("fileImportato", true);
		mDynamicForm.setValue("idUdAllegato", idUd);
		mDynamicForm.setValue("descrizioneFileAllegato", descrizione);
		mDynamicForm.setValue("listaTipiFileAllegato", idDocType);
		mDynamicForm.setValue("idTipoFileAllegato", idDocType);
		mDynamicForm.setValue("descTipoFileAllegato", nomeDocType);		
		mDynamicForm.setValue("isChanged", false);
//		mDynamicForm.setValue("isUdSenzaFileImportata", true); 
		if (isUdAllegato()) {			
//			if (getRemoveButton() != null) {
//				getRemoveButton().setAlwaysDisabled(true); // la riga deve essere cancellabile
//			}			
			flgProvEsternaItem.setCanEdit(false);
			flgParereItem.setCanEdit(false);
			flgParteDispositivoItem.setCanEdit(false);
			flgNoPubblAllegatoItem.setCanEdit(false);
			flgPubblicaSeparatoItem.setCanEdit(false);
			listaTipiFileAllegatoItem.setCanEdit(false);
			nomeFileAllegatoItem.setCanEdit(false);
			// descrizioneFileAllegatoItem.setCanEdit(false); // la descrizione deve essere editabile
			allegatoButtons.setCanEdit(false);
			uploadAllegatoItem.setCanEdit(false);
			flgDatiSensibiliItem.setCanEdit(false);
			nomeFileOmissisItem.setCanEdit(false);			
			fileOmissisButtons.setCanEdit(false);
//			uploadOmissisItem.setCanEdit(false);
		}
	}

	public boolean isChangedAndValid() {
		boolean valid = true;
		if ((listaTipiFileAllegatoItem.getValue() == null || listaTipiFileAllegatoItem.getValue().equals(""))
				&& (descrizioneFileAllegatoItem.getValue() == null || descrizioneFileAllegatoItem.getValueAsString().trim().equals(""))
				&& (uriFileAllegatoItem.getValue() == null || uriFileAllegatoItem.getValue().equals(""))) {
			valid = false;
		}
		return isChanged() && valid;
	}

	/**
	 * I seguenti metodi sono utilizzati solo per le azioni relative ai file dell'unità documentaria, che in questo specifico caso è rappresentata dal canvas.
	 * Il bottone visualizzaFileUdButton, che apre il menu che richiama i seguenti metodi, viene mostrato al posto degli altri bottoni se e solo se il metodo
	 * showOnlyFileUdButton dell'item di riferimento restituisce true.
	 *
	 */

	public void visualizzaFilePrimarioUd(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaFileUd(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFileAllegatoUd(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaFileUd(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFileUd(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display, ((AllegatiItem) getItem()).isShowModalPreview());
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void scaricaFilePrimarioUd(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		scaricaFileUd(idUd, idDoc, display, uri, remoteUri);
	}

	public void scaricaFileAllegatoUd(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		scaricaFileUd(idUd, idDoc, display, uri, remoteUri);
	}

	public void scaricaFileUd(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFilePrimarioSbustatoUd(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void scaricaFileAllegatoSbustatoUd(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
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
	protected void buildMenuBarcodeEtichetta(Menu altreOpMenu){
		Record record = new Record(mDynamicForm.getValues());
//		boolean isTipoRisposta = isRispostaCanvas(record);
		boolean flgAddSubMenuTimbra = false;
		final InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(mDynamicForm.getValue("infoFile"));
		
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
		if( (((isUdDocIstruttoriaCedAutotutela() /*&& isTipoRisposta*/) || isUdDocIstruttoriaDettFascicoloGenCompleto())  && isUdProtocollata())  
				|| (((AllegatiItem) getItem()).isAttivaVociBarcode() && lInfoFileRecord != null)) {
			timbraDatiSegnaturaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraDatiSegnaturaMenuItem(), "file/timbra.gif");
			timbraDatiSegnaturaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
				}
			});
			
			timbraDatiSegnaturaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickTimbraDatiSegnatura("");
				}
			});
			
			timbraSubMenu.addItem(timbraDatiSegnaturaMenuItem);
			
		} 
		
		if (((AllegatiItem) getItem()).isAttivaVociBarcode() && lInfoFileRecord != null){
			
			MenuItem timbraDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_timbraDatiTipologiaMenuItem());
			timbraDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						
						//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
						if(((AllegatiItem) getItem()).getIdDocFilePrimario() != null && !"".equals(((AllegatiItem) getItem()).getIdDocFilePrimario())){
							clickTimbraDatiTipologia();
						}else{
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().allegato_canvas_messageDocumentoNonTipizzato(),"",MessageType.WARNING));
						}
					}
				});
				timbraDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
					
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						
						return ((AllegatiItem)getItem()).isAttivaTimbroTipologia() && (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))
							   && ((AllegatiItem) getItem()).getIdDocFilePrimario() != null && !"".equals(((AllegatiItem) getItem()).getIdDocFilePrimario()) && lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
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
								return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
							}
						});
						timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickTimbraDatiSegnatura("CONFORMITA_ORIG_CARTACEO");
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
							return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""));
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
			
			timbraMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					return (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals("") && lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				}
			});
			
			/**
			 * BARCODE SU A4
			 */
			MenuItem barcodeA4MenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeA4MenuItem(), "protocollazione/barcode.png");
		
			Menu barcodeA4SubMenu = new Menu();

			MenuItem barcodeA4NrDocumentoMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeSegnatura("","");
				}
			});
			MenuItem barcodeA4NrDocumentoPoisizioneMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeA4NrDocumentoPoisizioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeSegnatura("","P");
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
					return  ((AllegatiItem)getItem()).isAttivaTimbroTipologia() /*&& (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))*/
							&& ((AllegatiItem) getItem()).getIdDocFilePrimario() != null && !"".equals(((AllegatiItem) getItem()).getIdDocFilePrimario());
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
			MenuItem barcodeMultipliA4NrDocumentoMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeMultipliA4NrDocumentoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeMultipli("","");
				}
			});
			MenuItem barcodeMultipliA4NrDocPosMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeMultipliA4NrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeMultipli("","P");
				}
			});
			MenuItem barcodeMultipliA4DatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeMultipliA4DatiTipologiaMenuItem());
			barcodeMultipliA4DatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeMultipli("T","");
				}
			});
			barcodeMultipliA4DatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {
					
					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					return  ((AllegatiItem)getItem()).isAttivaTimbroTipologia() /*&& (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))*/
							&& ((AllegatiItem) getItem()).getIdDocFilePrimario() != null && !"".equals(((AllegatiItem) getItem()).getIdDocFilePrimario());
				}
			});
			
			barcodeMultipliA4SubMenu.setItems(barcodeMultipliA4NrDocumentoMenuItem, barcodeMultipliA4NrDocPosMenuItem, barcodeMultipliA4DatiTipologiaMenuItem);
			barcodeA4MultipliMenuItem.setSubmenu(barcodeMultipliA4SubMenu);
			
			/**
			 * BARCODE SU ETICHETTA
			 */
			MenuItem barcodeEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeEtichettaMenuItem(), "protocollazione/stampaEtichetta.png");
			
			Menu barcodeEtichettaSubMenu = new Menu();
			MenuItem barcodeEtichettaNrDocMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeSegnatura("E","");
				}
			});
			MenuItem barcodeEtichettaNrDocPosMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeSegnatura("E","P");
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
					return  ((AllegatiItem)getItem()).isAttivaTimbroTipologia() /*&& (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))*/
							&& ((AllegatiItem) getItem()).getIdDocFilePrimario() != null && !"".equals(((AllegatiItem) getItem()).getIdDocFilePrimario());
				}
			});
			
			barcodeEtichettaSubMenu.setItems(barcodeEtichettaNrDocMenuItem, barcodeEtichettaNrDocPosMenuItem,barcodeEtichettaDatiTipologiaMenuItem);
			barcodeEtichettaMenuItem.setSubmenu(barcodeEtichettaSubMenu);
			
			/**
			 * BARCODE SU ETICHETTA MULTIPLI
			 */
			
			MenuItem barcodeEtichettaMultiploMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeEtichettaMultiploMenuItem(), "blank.png");
			Menu barcodeMultipliEtichettaSubMenu = new Menu();
			
			MenuItem barcodeMultipliEtichettaNrDocMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel(),"protocollazione/nr_documento.png");
			barcodeMultipliEtichettaNrDocMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeEtichettaMultipli("","");
				}
			});
			MenuItem barcodeMultipliEtichettaNrDocPosMenuItem = new MenuItem(((AllegatiItem)getItem()).getNroDocumentoBarcodeLabel() + " + posizione","protocollazione/nr_documento_posizione.png");
			barcodeMultipliEtichettaNrDocPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeEtichettaMultipli("","P");
				}
			});
			MenuItem barcodeMultipliEtichettaDatiTipologiaMenuItem = new MenuItem(I18NUtil.getMessages().allegato_canvas_barcodeMultipliEtichettaDatiTipologiaMenuItem());
			barcodeMultipliEtichettaDatiTipologiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					clickBarcodeEtichettaMultipli("T", "");
				}
			});
			barcodeMultipliEtichettaDatiTipologiaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
				
				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {

					//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
					return  ((AllegatiItem)getItem()).isAttivaTimbroTipologia() /*&& (uriFileAllegatoItem.getValue() != null && !uriFileAllegatoItem.getValue().equals(""))*/
							&& ((AllegatiItem) getItem()).getIdDocFilePrimario() != null && !"".equals(((AllegatiItem) getItem()).getIdDocFilePrimario());
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
	 * Metodo per verificare se il canvas è una risposta all'istanza di CED o autotutela
	 */
	
	public boolean isRispostaCanvas() {
		Record record =  new Record(mDynamicForm.getValues());
		return isRispostaCanvas(record);		
	}
	
	protected boolean isRispostaCanvas(Record record) {
//		return record != null && record.getAttribute("descTipoFileAllegato") != null && (record.getAttribute("descTipoFileAllegato").equalsIgnoreCase("Risposta Istanza CED") || record.getAttribute("descTipoFileAllegato").equalsIgnoreCase("Risposta Istanza Autotutela"));
		return record != null && record.getAttribute("ruoloUd") != null && record.getAttribute("ruoloUd").equalsIgnoreCase("AFIN");		
	}

	/**
	 * Voce Menù Timbra ( scelta Dati segnatura )
	 */
	public void clickTimbraDatiSegnatura(String finalita) {
		
		String nomeFile = mDynamicForm.getValueAsString("nomeFileAllegato");
		String uri = mDynamicForm.getValueAsString("uriFileAllegato");
		String remote = mDynamicForm.getValueAsString("remoteUri");
		String idDoc = mDynamicForm.getValueAsString("idDocAllegato");
		Integer nroProgAllegato = new Integer(mDynamicForm.getValueAsString("numeroProgrAllegato"));
		InfoFileRecord precInfo = mDynamicForm.getValue("infoFile") != null ? new InfoFileRecord(mDynamicForm.getValue("infoFile")) : null;

		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		
		String idUd = null;
		if (detailRecord != null ) {
			idUd = detailRecord.getAttribute("idUd");
		} else {
			//se detailRecord è null vuol dire che l'allegatoItem è quello di DocumentiIstruttoria,
			//quindi l'idUd è l'idUdAppartenenza del canvas
			idUd = mDynamicForm.getValueAsString("idUdAppartenenza");
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
			if(nroProgAllegato!=null) {
				record.setAttribute("nroProgAllegato", nroProgAllegato);
			}
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
					precInfo.getMimetype(), idUd, idDoc, rotazioneTimbroPref, posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			if(nroProgAllegato!=null) {
				lFileDaTimbrareBean.setAttribute("nroProgAllegato", nroProgAllegato);
			}
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	/**
	 * Voce Menù Timbra ( Dati tipologia )
	 */
	public void clickTimbraDatiTipologia() {
		
		//Per la funzionalità di Dati tipologia viene utilizzato l'IdDoc del File primario e non quello dell'allegato
		String idDocAllegato = ((AllegatiItem) getItem()).getIdDocFilePrimario();//mDynamicForm.getValueAsString("idDocAllegato");
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";		
				
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			
			Record record = new Record();
			
			if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){				
				record.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
			}else{
				record.setAttribute("idDoc", idDocAllegato);
			}
			
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			
			TimbroCopertinaUtil.buildDatiTipologia(record);
			
		}else{
			
			Record copertinaTimbroRecord = new Record();
			if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){				
				
				copertinaTimbroRecord.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
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
	public void clickBarcodeSegnatura(String tipologia,String posizionale) {
		
		if(tipologia != null && !"".equals(tipologia) && "E".equals(tipologia)){
			//Barcode su etichetta
			clickBarcodeEtichettaDatiSegnatura(posizionale);
		}else{
		
			Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
			String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
			
			String numeroAllegato = null;
			if(posizionale != null && "P".equals(posizionale)){
				numeroAllegato = mDynamicForm.getValueAsString("numeroProgrAllegato");
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
				
				if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){				
					record.setAttribute("barcodePraticaPregressa", "true");
					record.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
					record.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
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
				if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){				
					copertinaTimbroRecord.setAttribute("barcodePraticaPregressa", "true");
					copertinaTimbroRecord.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
					copertinaTimbroRecord.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
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
	public void clickBarcodeEtichettaDatiSegnatura(String posizione) {
		
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		final String nrAllegato = posizione != null && "P".equals(posizione) ? mDynamicForm.getValueAsString("numeroProgrAllegato") : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", nrAllegato);
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		
		if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){
			recordToPrint.setAttribute("barcodePraticaPregressa", "true");
			recordToPrint.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
			recordToPrint.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
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
			String idDocAllegato = ((AllegatiItem) getItem()).getIdDocFilePrimario();//mDynamicForm.getValueAsString("idDocAllegato");
			String numeroAllegato = "";
			
			String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
			String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
					AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
			
			if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
				
				Record record = new Record();
				if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){				
					
					record.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
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
				
				if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){
					copertinaTimbroRecord.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
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
		final String idDocAllegato = ((AllegatiItem) getItem()).getIdDocFilePrimario();//mDynamicForm.getValueAsString("idDocAllegato");
	
		final Record recordToPrint = new Record();
		
		recordToPrint.setAttribute("numeroAllegato", "");
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){
			recordToPrint.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
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
	public void clickBarcodeMultipli(String tipo,String posizionale){
			
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
				
		String numeroAllegato = null;
		if(posizionale != null && "P".equals(posizionale)){
			numeroAllegato = mDynamicForm.getValueAsString("numeroProgrAllegato");
		}
				
		/**
		 * DATI TIPOLOGIA
		 */
		if(tipo != null && !"".equals(tipo) && "T".equals(tipo)){
			
			Record copertinaTimbroRecord = new Record();
			
			if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){
				copertinaTimbroRecord.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
				copertinaTimbroRecord.setAttribute("barcodePraticaPregressa", "true");
				copertinaTimbroRecord.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
			}else{
				copertinaTimbroRecord.setAttribute("idDoc", mDynamicForm.getValueAsString("idDocAllegato"));
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
			
			if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){	
				copertinaTimbroRecord.setAttribute("barcodePraticaPregressa", "true");
				copertinaTimbroRecord.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
				copertinaTimbroRecord.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
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
	public void clickBarcodeEtichettaMultipli(String tipo,String posizione) {
			
		Record detailRecord = ((AllegatiItem) getItem()).getDetailRecord();
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		String idDocAllegato = mDynamicForm.getValueAsString("idDocAllegato");
		
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		if(tipo != null && "T".equals(tipo)){
			record.setAttribute("tipologia", "T");
			if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){
				record.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
				record.setAttribute("barcodePraticaPregressa", "true");
				record.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
			}else{
				record.setAttribute("idDoc", idDocAllegato);
			}
			record.setAttribute("nrAllegato", mDynamicForm.getValueAsString("numeroProgrAllegato"));
		}else{
			if(((AllegatiItem) getItem()).isFromFolderPraticaPregressa()){
				record.setAttribute("idFolder", ((AllegatiItem) getItem()).getIdFolder());
				record.setAttribute("barcodePraticaPregressa", "true");
				record.setAttribute("sezionePratica", mDynamicForm.getValueAsString("listaTipiFileAllegato"));
			}else{
				record.setAttribute("idUd", idUd);
			}
		}
		
		if(posizione != null && "P".equals(posizione)){
			record.setAttribute("nrAllegato", mDynamicForm.getValueAsString("numeroProgrAllegato"));
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
	
	private boolean isUdSenzaFileImportata() {
		return mDynamicForm.getValue("isUdSenzaFileImportata") != null && (Boolean) mDynamicForm.getValue("isUdSenzaFileImportata");
	}
	
	public boolean isParere() {
		if(showFlgParere()) {
			return mDynamicForm.getValueAsString("flgParere") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgParere")) : false;			
		}
		return false;
	}
	
	public boolean isParteDispositivo() {
		if(showFlgParteDispositivo()) {
			if(isParere()) {
				return false;
			}
			return mDynamicForm.getValue("flgParteDispositivo") != null	&& new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
		}
		return false;
	}
	
	public boolean isEsclusoDaPubbl() {
		if(showFlgNoPubblAllegato()) {
			return mDynamicForm.getValueAsString("flgNoPubblAllegato") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgNoPubblAllegato")) : false;			
		}
		return false;
	}
	
	public boolean isPubblicazioneSeparata() {
		if(showFlgPubblicaSeparato()) {
			if(isParere()) {
				return true;
			} else if(isParteDispositivo()) {
				return mDynamicForm.getValueAsString("flgPubblicaSeparato") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgPubblicaSeparato")) : false;
			}
		}
		return false;
	}
	
	public boolean hasDatiSensibili() {
		if(((AllegatiItem) getItem()).getShowVersioneOmissis()) {
			return mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
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
		record.setAttribute("nomeFile", mDynamicForm.getValueAsString("nomeFileAllegato"));
		record.setAttribute("uriFile", mDynamicForm.getValueAsString("uriFileAllegato"));
		record.setAttribute("remote", mDynamicForm.getValueAsString("remoteUri"));
		InfoFileRecord infoFile = mDynamicForm.getValue("infoFile") != null
				? new InfoFileRecord(mDynamicForm.getValue("infoFile"))
				: null;
		if (infoFile != null) {
			record.setAttribute("infoFile", infoFile);
		}

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				mDynamicForm.setValue("opzioniTimbro", object);
			}
		});
		apponiTimbroWindow.show();

	}
	
	public void showOpzioniTimbraturaOmissis() {

		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro");
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro");
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina");

		Record record = new Record();
		record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
		record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
		record.setAttribute("tipoPaginaPref", tipoPaginaPref);
		record.setAttribute("nomeFile", mDynamicFormOmissis.getValueAsString("nomeFileOmissis"));
		record.setAttribute("uriFile", mDynamicFormOmissis.getValueAsString("uriFileOmissis"));
		record.setAttribute("remote", mDynamicFormOmissis.getValueAsString("remoteUriOmissis"));
		InfoFileRecord infoFileOmissis = mDynamicFormOmissis.getValue("infoFileOmissis") != null
				? new InfoFileRecord(mDynamicFormOmissis.getValue("infoFileOmissis"))
				: null;
		if (infoFileOmissis != null) {
			record.setAttribute("infoFile", infoFileOmissis);
		}

		ApponiTimbroWindow apponiTimbroWindow = new ApponiTimbroWindow(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				mDynamicFormOmissis.setValue("opzioniTimbroOmissis", object);
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
		
	private void dettaglioUdAllegato() {
		if (mDynamicForm.getValue("idUdAllegato") != null && !"".equalsIgnoreCase(mDynamicForm.getValueAsString("idUdAllegato"))){
			String title = "Dettaglio unità documentaria corrispondente all'allegato";
			Record lRecord = new Record();
			lRecord.setAttribute("idUd", mDynamicForm.getValue("idUdAllegato"));
			new DettaglioRegProtAssociatoWindow(lRecord, null, title);
		}
	}
	
	private void dettaglioProtocollo() {
		if (mDynamicForm.getValue("idUdAppartenenza") != null && !"".equalsIgnoreCase(mDynamicForm.getValueAsString("idUdAppartenenza"))){
			String title = null;
			if(mDynamicForm.getValue("estremiProtUd") != null && !"".equals(mDynamicForm.getValue("estremiProtUd"))) {				
				title = "Dettaglio prot. " + mDynamicForm.getValue("estremiProtUd");			
			} else {
				title = "Dettaglio documento";
			}
			Record lRecord = new Record();
			lRecord.setAttribute("idUd", mDynamicForm.getValue("idUdAppartenenza"));
			new DettaglioRegProtAssociatoWindow(lRecord, null, title) {
				
				@Override
				protected void onDestroy() {
					super.onDestroy();
					String estremiProt = mDynamicForm.getValueAsString("estremiProtUd");
					if (estremiProt == null || "".equalsIgnoreCase(estremiProt)) {
						if (getBody() instanceof ProtocollazioneDetail) {
							Record recordProtocollato = ((ProtocollazioneDetail) getBody()).getRecordProtocollato();
							aggiornaEstremiProtUdAfterProtocolla(recordProtocollato);							
						}
					}
				}
			};
		} else {
			SC.say("Non e' possibile accedere ai dettagli perche' il riferimento del protocollo di apertura e' inesistente.");
		}
	}
	
	public void clickProtocollazioneEntrata() {	
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd",  mDynamicForm.getValue("idUdAppartenenza"));
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordDettaglio = response.getData()[0];
					ProtocollazioneDetailEntrata protocollazioneDetailEntrata = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(recordDettaglio, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiornaEstremiProtUdAfterProtocolla(object);
						}
					}, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiornaEstremiProtUdAfterProtocolla(object);
						}
					});
					protocollazioneDetailEntrata.caricaDettaglioXProtBozza(null, recordDettaglio);
					Layout.addModalWindow("protocollazione_entrata_modal", "Protocollazione in entrata", "menu/protocollazione_entrata.png", protocollazioneDetailEntrata);
				}
			}
		});	
	}
	
	public void clickProtocollazioneUscita() {	
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd",  mDynamicForm.getValue("idUdAppartenenza"));
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordDettaglio = response.getData()[0];
					ProtocollazioneDetailUscita protocollazioneDetailUscita = ProtocollazioneUtil.buildProtocollazioneDetailUscita(recordDettaglio, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiornaEstremiProtUdAfterProtocolla(object);
						}
					}, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiornaEstremiProtUdAfterProtocolla(object);
						}
					});
					protocollazioneDetailUscita.caricaDettaglioXProtBozza(null, recordDettaglio);
					Layout.addModalWindow("protocollazione_uscita_modal", "Protocollazione in uscita", "menu/protocollazione_uscita.png", protocollazioneDetailUscita);
				}
			}
		});	
	}
	
	public void clickProtocollazioneInterna() {	
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd",  mDynamicForm.getValue("idUdAppartenenza"));
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.addParam("isProtocollazioneBozza", "true");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordDettaglio = response.getData()[0];
					ProtocollazioneDetailInterna protocollazioneDetailInterna = ProtocollazioneUtil.buildProtocollazioneDetailInterna(recordDettaglio, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiornaEstremiProtUdAfterProtocolla(object);
						}
					}, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							aggiornaEstremiProtUdAfterProtocolla(object);
						}
					});
					protocollazioneDetailInterna.caricaDettaglioXProtBozza(null, recordDettaglio);
					Layout.addModalWindow("protocollazione_interna_modal", "Protocollazione interna", "menu/protocollazione_interna.png", protocollazioneDetailInterna);
				}
			}
		});	
	}
	
	public void aggiornaEstremiProtUdAfterProtocolla(Record recordProtocollato) {
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
				mDynamicForm.setValue("estremiProtUd", estremiProtocollo);
			}			
		}
	}
	
	// questi controlli vengono sempre effettuati sul file vers. integrale perchè la parte omissis è su FileButtonsItem
	public void controlAfterUpload(InfoFileRecord info) {		
		
		boolean flgParere = mDynamicForm.getValue("flgParere") != null && new Boolean(mDynamicForm.getValueAsString("flgParere"));
		boolean flgParteDispositivo = mDynamicForm.getValue("flgParteDispositivo") != null && new Boolean(mDynamicForm.getValueAsString("flgParteDispositivo"));
		boolean flgNoPubblAllegato = mDynamicForm.getValue("flgNoPubblAllegato") != null && new Boolean(mDynamicForm.getValueAsString("flgNoPubblAllegato"));	
//		boolean flgPubblicaSeparato = isFlgPubblicaSeparato();	
		boolean flgDatiSensibili = mDynamicForm.getValue("flgDatiSensibili") != null && new Boolean(mDynamicForm.getValueAsString("flgDatiSensibili")); // controllo di non avere una vers. con omissis altrimenti va quella in pubblicazione e non la vers. integrale
		boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;		
		if (((AllegatiItem) getItem()).isProtInModalitaWizard() && ((AllegatiItem) getItem()).isCanaleSupportoCartaceo() &&
				(info == null || info.getMimetype() == null || (!info.getMimetype().equals("application/pdf") && !info.getMimetype().startsWith("image")))) {
			GWTRestDataSource.printMessage(new MessageBean(
					"Il file non è un'immagine come atteso: poiché il canale/supporto originale specificato indica che il documento è cartaceo puoi allegare solo la/le immagini - scansioni o foto - che ne rappresentano la copia digitale",
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
		if (!((AllegatiItem) getItem()).isFormatoAmmesso(info)) {
			GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non ammesso", "", MessageType.WARNING));
		} else if(flgParteDispositivo && !((AllegatiItem) getItem()).isFormatoAmmessoParteIntegrante(info)) {
			GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non ammesso per un allegato parte integrante", "", MessageType.WARNING));
//			clickEliminaFile();
		}
		if(flgParteDispositivo && !showFileOmissis && !PreviewWindow.isPdfConvertibile(info) /*&& !flgPubblicaSeparato*/) {			
			if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
				mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
			} 
			if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
				if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
					((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
				} else {
					mDynamicForm.setValue("flgPubblicaSeparato", true);														
				}
			}
			GWTRestDataSource.printMessage(new MessageBean("File non convertibile in formato pdf: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));		
		} 	
		if (info != null && info.isFirmato()) {
			String rifiutoAllegatiConFirmeNonValide = ((AllegatiItem) getItem()).getRifiutoAllegatiConFirmeNonValide();
			if(!info.isFirmaValida() && rifiutoAllegatiConFirmeNonValide != null && !"".equals(rifiutoAllegatiConFirmeNonValide)) {
				if("solo_allegati_parte_integrante".equalsIgnoreCase(rifiutoAllegatiConFirmeNonValide) && flgParteDispositivo) {
					flgParteDispositivo = false;
					mDynamicForm.setValue("flgParteDispositivo", false);	
					flgNoPubblAllegato = true;
					mDynamicForm.setValue("flgNoPubblAllegato", true);
//					flgPubblicaSeparato = false;
					mDynamicForm.setValue("flgPubblicaSeparato", false);
					if(showFileOmissis) {
						flgDatiSensibili = false;
						showFileOmissis = false;
						mDynamicForm.setValue("flgDatiSensibili", false);
						manageOnChangedFlgDatiSensibili();
					}
					GWTRestDataSource.printMessage(new MessageBean("Il file presenta firme non valide alla data odierna e non può essere caricato come allegato parte integrante: è stato automaticamente inserito come allegato NON parte integrante", "", MessageType.WARNING));
				} else {
					GWTRestDataSource.printMessage(new MessageBean("Il file presenta firme non valide alla data odierna", "", MessageType.WARNING));
				}
			} else if(((AllegatiItem) getItem()).isDisattivaUnioneAllegatiFirmati()) {
				if(flgParteDispositivo && !showFileOmissis && !flgNoPubblAllegato) {
					if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
						mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
					} 
					if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
						if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
							((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
						} else {
							mDynamicForm.setValue("flgPubblicaSeparato", true);														
						}
					}
//					GWTRestDataSource.printMessage(new MessageBean("Il file è firmato. Si consiglia pubblicazione separata.", "", MessageType.WARNING));					
				}
			}
		}
		boolean pubblicazioneSeparataPdfProtetti = ((AllegatiItem) getItem()).isPubblicazioneSeparataPdfProtetti();
		if(flgParteDispositivo && !showFileOmissis && pubblicazioneSeparataPdfProtetti && info.isPdfProtetto() /*&& !flgPubblicaSeparato*/) {			
			if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
				mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
			} 
			if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
				if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
					((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
				} else {
					mDynamicForm.setValue("flgPubblicaSeparato", true);														
				}
			}
			GWTRestDataSource.printMessage(new MessageBean("File protetto da qualsiasi modifica: non è possibile unirlo al testo dell'atto", "", MessageType.WARNING));		
		} 		
		final float MEGABYTE = 1024L * 1024L;
		long dimAlertAllegato = ((AllegatiItem) getItem()).getDimAlertAllegato(); // questo è in bytes
		long dimMaxAllegatoXPubblInMB = ((AllegatiItem) getItem()).getDimMaxAllegatoXPubbl(); // questa è in MB
		if(dimMaxAllegatoXPubblInMB > 0 && info != null && info.getBytes() > (dimMaxAllegatoXPubblInMB * MEGABYTE)) {						
			if(flgParteDispositivo && !showFileOmissis) {
				if(((AllegatiItem) getItem()).isShowFlgNoPubblAllegato()) {
					mDynamicForm.setValue("flgNoPubblAllegato", true);	
				}
				if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
					mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
				} 
				if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
					if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
						((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
					} else {
						mDynamicForm.setValue("flgPubblicaSeparato", true);														
					}
				}
				GWTRestDataSource.printMessage(new MessageBean("La dimensione del file è superiore alla soglia di " + dimMaxAllegatoXPubblInMB + " MB consentita per la pubblicazione", "", MessageType.WARNING));
			}
		} else if(dimAlertAllegato > 0 && info != null && info.getBytes() > dimAlertAllegato) {						
			float dimensioneInMB = info.getBytes() / MEGABYTE;						
			if(flgParteDispositivo && !showFileOmissis && !flgNoPubblAllegato) {
				if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
					mDynamicForm.setValue("flgPubblicaAllegatiSeparatiInAllegatiGrid", true);
				} 
				if(((AllegatiItem) getItem()).isShowFlgPubblicaSeparato()) {
					if(((AllegatiItem) getItem()).isFlgPubblicazioneAllegatiUguale()) {
						((AllegatiItem) getItem()).setFlgPubblicaAllegatiSeparati(true);
					} else {
						mDynamicForm.setValue("flgPubblicaSeparato", true);														
					}
				}
				GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB. Si consiglia pubblicazione separata.", "", MessageType.WARNING));					
			} else {
				GWTRestDataSource.printMessage(new MessageBean("Attenzione: la dimensione del file è di " + NumberFormat.getFormat("#,##0.00").format(dimensioneInMB) + " MB", "", MessageType.WARNING));					
			}
		}		
	}
	
	protected String setTitleAlignFromAllegatoDetailInGridItem(String title, boolean required) {
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			int width = TITTLE_ALIGN_FROM_ALLEGATO_DETAIL_IN_GRID_ITEM_WIDTH;
			if (title != null && title.startsWith("<span")) {
				return title;
			}
			return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
		}
		return title;
	}
	
	public void manageOnChangedFlgDatiSensibili() {
		boolean flgDatiSensibili = mDynamicForm.getValueAsString("flgDatiSensibili") != null ? Boolean.parseBoolean(mDynamicForm.getValueAsString("flgDatiSensibili")) : false;
		boolean showFileOmissis = ((AllegatiItem) getItem()).getShowVersioneOmissis() && flgDatiSensibili;
		if(showFileOmissis) {
			mDynamicFormOmissis.show();
		} else {
			mDynamicFormOmissis.hide();
			fileOmissisButtons.clickEliminaFile();
		}
		mDynamicForm.markForRedraw();
		mDynamicFormOmissis.markForRedraw();
	}
	
}