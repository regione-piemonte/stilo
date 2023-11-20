/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.ContenutiFascicoloPopup;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.DettaglioPraticaPregressaWindow;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUfficioItems;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUtenteItems;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class NuovaAttiRichiestiCanvas extends ReplicableCanvas {
	
	private final int TITTLE_ALIGN_WIDTH = 130;
	private final int TITTLE_ALIGN_WIDTH_2 = 290;
	private VLayout lVLayoutCanvas;
	
	// DynamicForm
	private ReplicableCanvasForm estremiAttoRichiestoForm;
	private ReplicableCanvasForm tipoFascicoloForm;
	private ReplicableCanvasForm statoAttoRichiestoForm;
	private ReplicableCanvasForm responsabilePrelievoAttoForm;
	private ReplicableCanvasForm ufficioPrelievoAttoForm;
	private ReplicableCanvasForm noteAttoRichiestoForm;

	private SelectItem tipoProtocolloItem;
	private HiddenItem idFolderItem;
	protected ExtendedTextItem numProtocolloGeneraleItem;
	protected AnnoItem annoProtocolloGeneraleItem;
	protected ExtendedTextItem siglaProtocolloSettoreItem;
	protected ExtendedTextItem numProtocolloSettoreItem;
	protected ExtendedNumericItem subProtocolloSettoreItem;
	protected AnnoItem annoProtocolloSettoreItem;
	protected ExtendedTextItem numPraticaWorkflowItem;
	protected AnnoItem annoPraticaWorkflowItem;
	private StaticTextItem statoScansioneItem;	
	private ImgButtonItem visualizzaDettaglioPraticaButton;
	private ImgButtonItem visualizzaContenutiFascicoloButton;
	//private ImgButtonItem lookupArchivioButton;
	private StaticTextItem statoAttoDaSincronizzareItem;
	private ImgButtonItem sincronizzaStatoAttoButton;
	private SelectItem selectInArchivioItem;
	private HiddenItem desInArchivioItem;
	private HiddenItem visureCollegateItem;
	private ImgButtonItem visureCollegateButtonItem;
	
//	private SelectItem selectCompetenzaDiUrbanisticaItem;
//	private SelectItem selectCartaceoReperibileItem;
	private SelectItem selectTipoFascicoloItem;
	private HiddenItem desTipoFascicoloItem;
	private AnnoItem annoProtEdiliziaPrivataItem;
	private ExtendedTextItem numeroProtEdiliziaPrivataItem;
	private AnnoItem annoWorkflowItem;
	private ExtendedTextItem numeroWorkflowItem;
	private ExtendedTextItem numeroDepositoItem;
	private ExtendedTextItem classificaItem;
	private ExtendedTextItem udcItem;
	
//	protected CheckboxItem flgRichiestaVisioneCartaceoItem;
	private SelectItem selectStatoItem;
	private SelectItem selectTipoComunicazioneItem;
	private HiddenItem desTipoComunicazioneItem;
	
	private DateItem dataPrelievoItem;
	
	private SelezionaUfficioItems selezionaUfficioPrelievoItems;
	
	private SelezionaUtenteItems responsabilePrelievoItems;
		
	private TextAreaItem noteUffRichiedenteItem;
	private TextAreaItem noteCittadellaItem;
	private TextAreaItem noteSportelloItem;
	
	@Override
	public void disegna() {
		
		lVLayoutCanvas = new VLayout();
		lVLayoutCanvas.setOverflow(Overflow.VISIBLE);
		lVLayoutCanvas.setMargin(6);
		lVLayoutCanvas.setBorder("1px solid #a7abb4");
		lVLayoutCanvas.setWidth(1400);
		lVLayoutCanvas.setAutoHeight();
		lVLayoutCanvas.setPadding(11);
		
		buildEstremiAttoRichiestoForm();
		buildTipoFascicoloForm();
		buildStatoAttoRichiestoForm();
		buildUfficioPrelievoAttoForm();
		buildResponsabilePrelievoForm();
		buildNoteAttoRichiestoForm();
				
		lVLayoutCanvas.setMembers(estremiAttoRichiestoForm, tipoFascicoloForm, statoAttoRichiestoForm, responsabilePrelievoAttoForm, ufficioPrelievoAttoForm, noteAttoRichiestoForm);
				
		addChild(lVLayoutCanvas);
	}
	
	private void buildEstremiAttoRichiestoForm(){
		
		estremiAttoRichiestoForm = new ReplicableCanvasForm();
		estremiAttoRichiestoForm.setWidth100();
		estremiAttoRichiestoForm.setPadding(5);
		estremiAttoRichiestoForm.setWrapItemTitles(false);
		estremiAttoRichiestoForm.setNumCols(25);
		estremiAttoRichiestoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");		

		tipoProtocolloItem = new SelectItem("tipoProtocollo", setTitleAlign("Tipo protocollo/pratica", TITTLE_ALIGN_WIDTH, false));
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put(TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE.tipo, TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE.descrizione);
		tipoValueMap.put(TipoProtocolloPraticaEnum.PROTOCOLLO_DI_SETTORE.tipo, TipoProtocolloPraticaEnum.PROTOCOLLO_DI_SETTORE.descrizione);
		tipoValueMap.put(TipoProtocolloPraticaEnum.PRATICA_WORKFLOW.tipo, TipoProtocolloPraticaEnum.PRATICA_WORKFLOW.descrizione);
		tipoProtocolloItem.setValueMap(tipoValueMap);
		tipoProtocolloItem.setStartRow(true);
		tipoProtocolloItem.setColSpan(1);
		tipoProtocolloItem.setDefaultValue(TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE.tipo);
		tipoProtocolloItem.setRequired(true);
		tipoProtocolloItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				estremiAttoRichiestoForm.setValue("idFolder", (String)null);
				estremiAttoRichiestoForm.setValue("statoScansione", (String)null);
				String tipoProtocollo = (String) event.getValue();				
				if(tipoProtocollo != null && tipoProtocollo.equals(TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE.tipo)) {
					estremiAttoRichiestoForm.setValue("siglaProtocolloSettore", (String)null);
					estremiAttoRichiestoForm.setValue("numProtocolloSettore", (String)null);
					estremiAttoRichiestoForm.setValue("subProtocolloSettore", (String)null);
					estremiAttoRichiestoForm.setValue("annoProtocolloSettore", (String)null);		
				} else if(tipoProtocollo != null && tipoProtocollo.equals(TipoProtocolloPraticaEnum.PROTOCOLLO_DI_SETTORE.tipo)) {
					estremiAttoRichiestoForm.setValue("numProtocolloGenerale", (String)null);
					estremiAttoRichiestoForm.setValue("annoProtocolloGenerale", (String)null);					
				}else if(tipoProtocollo != null && tipoProtocollo.equals(TipoProtocolloPraticaEnum.PRATICA_WORKFLOW.tipo)) {
					estremiAttoRichiestoForm.setValue("numPraticaWorkflow", (String)null);
					estremiAttoRichiestoForm.setValue("annoPraticaWorkflow", (String)null);
				}
				markForRedraw();
				if (hasProtocolloGenerale()) {
					recuperaDatiProtocolloGenerale(false);
				} else if (hasProtocolloSettore()) {
					recuperaDatiProtocolloSettore(false);
				} else if (hasPraticaWorkflow()) {
					recuperaDatiPraticaWorkflow(false);
				}
			}
		});
		
		idFolderItem = new HiddenItem("idFolder");
		idFolderItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				if (idFolderItem.getValue() != null){
					recuperaDatiProtocolloFromIdFolder(false);
				}
			}
		});
						
		numProtocolloGeneraleItem = new ExtendedTextItem("numProtocolloGenerale", "N°") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		numProtocolloGeneraleItem.setWidth(100);
		numProtocolloGeneraleItem.setLength(50);
		numProtocolloGeneraleItem.setAttribute("obbligatorio", true);
		numProtocolloGeneraleItem.setKeyPressFilter("[0-9]");
		numProtocolloGeneraleItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloGenerale(false);
			}
		});
		numProtocolloGeneraleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloProtocolloGenerale();
			}
		});
		numProtocolloGeneraleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloProtocolloGenerale() && !isProtocolloGeneraleEmpty();
			}
		}));
			
		annoProtocolloGeneraleItem = new AnnoItem("annoProtocolloGenerale", "Anno") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		annoProtocolloGeneraleItem.setAttribute("obbligatorio", true);
		annoProtocolloGeneraleItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloGenerale(false);
			}
		});
		annoProtocolloGeneraleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloProtocolloGenerale();
			}
		});
		annoProtocolloGeneraleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloProtocolloGenerale() && !isProtocolloGeneraleEmpty();
			}
		}));
			
		siglaProtocolloSettoreItem = new ExtendedTextItem("siglaProtocolloSettore", "Registro") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		siglaProtocolloSettoreItem.setColSpan(1);
		siglaProtocolloSettoreItem.setWidth(100);
		siglaProtocolloSettoreItem.setLength(3);
		siglaProtocolloSettoreItem.setAttribute("obbligatorio", true);
		siglaProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore(false);
			}
		});
		siglaProtocolloSettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloProtocolloDiSettore();
			}
		});
		siglaProtocolloSettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloProtocolloDiSettore() && !isProtocolloSettoreEmpty();
			}
		}));
		
		numProtocolloSettoreItem = new ExtendedTextItem("numProtocolloSettore", "N°") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		numProtocolloSettoreItem.setWidth(100);
		numProtocolloSettoreItem.setLength(50);
		numProtocolloSettoreItem.setKeyPressFilter("[0-9]");
		numProtocolloSettoreItem.setAttribute("obbligatorio", true);
		numProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore(false);
			}
		});
		numProtocolloSettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloProtocolloDiSettore();
			}
		});
		numProtocolloSettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloProtocolloDiSettore() && !isProtocolloSettoreEmpty();
			}
		}));

		subProtocolloSettoreItem = new ExtendedNumericItem("subProtocolloSettore", "Sub", false) {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		subProtocolloSettoreItem.setWidth(100);
		subProtocolloSettoreItem.setLength(50);
		subProtocolloSettoreItem.setKeyPressFilter("[0-9]");
		subProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore(false);
			}
		});
		subProtocolloSettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloProtocolloDiSettore();
			}
		});
		
		annoProtocolloSettoreItem = new AnnoItem("annoProtocolloSettore", "Anno") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		annoProtocolloSettoreItem.setAttribute("obbligatorio", true);
		annoProtocolloSettoreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloSettore(false);
			}
		});
		annoProtocolloSettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloProtocolloDiSettore();
			}
		});
		annoProtocolloSettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloProtocolloDiSettore() && !isProtocolloSettoreEmpty();
			}
		}));
		
		numPraticaWorkflowItem = new ExtendedTextItem("numPraticaWorkflow", "N°") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		numPraticaWorkflowItem.setWidth(100);
		numPraticaWorkflowItem.setLength(50);
		numPraticaWorkflowItem.setAttribute("obbligatorio", true);
		numPraticaWorkflowItem.setKeyPressFilter("[0-9]");
		numPraticaWorkflowItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiPraticaWorkflow(false);
			}
		});
		numPraticaWorkflowItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloPraticaWorkflow();
			}
		});
		numPraticaWorkflowItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloPraticaWorkflow() && !isProtocolloPraticaWorkflowEmpty();
			}
		}));
			
		annoPraticaWorkflowItem = new AnnoItem("annoPraticaWorkflow", "Anno") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		annoPraticaWorkflowItem.setAttribute("obbligatorio", true);
		annoPraticaWorkflowItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				recuperaDatiPraticaWorkflow(false);
			}
		});
		annoPraticaWorkflowItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isTipoProtocolloPraticaWorkflow();
			}
		});
		annoPraticaWorkflowItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTipoProtocolloPraticaWorkflow() && !isProtocolloPraticaWorkflowEmpty();
			}
		}));
		
		// Icone stato scansione 
		statoScansioneItem = new StaticTextItem("statoScansione");
		statoScansioneItem.setShowValueIconOnly(true);
		statoScansioneItem.setShowTitle(false);
		statoScansioneItem.setWidth(16);
		statoScansioneItem.setHeight(16);
		statoScansioneItem.setValueIconSize(16);
		statoScansioneItem.setVAlign(VerticalAlignment.CENTER);
		statoScansioneItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoScansioneItem.setValueIconLeftPadding(5);
		
		Map<String, String> statoScansioneValueIcons = new HashMap<String, String>();
		statoScansioneValueIcons.put("C", "richiesteAccessoAtti/completamente_scansionata.png");
		statoScansioneValueIcons.put("P", "richiesteAccessoAtti/parzialmente_scansionata.png");
		statoScansioneValueIcons.put("N", "richiesteAccessoAtti/non_scansionata.png");
		statoScansioneValueIcons.put("", "richiesteAccessoAtti/non_scansionata.png");
		statoScansioneItem.setValueIcons(statoScansioneValueIcons);
		
		statoScansioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoScansioneItem.getValue() != null && !"".equalsIgnoreCase((String) statoScansioneItem.getValue());
			}
		});
		statoScansioneItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				String statoScansione = statoScansioneItem.getValue() != null ? (String) statoScansioneItem.getValue() : null;
				if (statoScansione != null && "C".equals(statoScansione)) {
					return "Completamente scansionata";	
				} else if (statoScansione != null && "P".equals(statoScansione)) {
					return "Parzialmente scansionata";
				}else if (statoScansione != null && "N".equals(statoScansione)) {
					return "Non ancora scansionata";
				}else {
					return "";
				}	
			}
		});

		visualizzaDettaglioPraticaButton = new ImgButtonItem("visualizzaDettaglioPratica", "buttons/detail.png", "Dettaglio pratica");
		visualizzaDettaglioPraticaButton.setAlwaysEnabled(true);
		visualizzaDettaglioPraticaButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {			
				new DettaglioPraticaPregressaWindow((String) idFolderItem.getValue()){
					
					@Override
					public void manageOnCloseClick() {
						boolean eseguitaAzioneSuPrelievo = (getPortletLayout() != null && getPortletLayout().getEseguitaAzioneSuPrelievo() != null && getPortletLayout().getEseguitaAzioneSuPrelievo());
						super.manageOnCloseClick();
						// Se lo stato dell'atto è editabile e nel dettaglio della pratica pregressa ho effettuato modifiche al registro prelievo, 
						// devo chiedere se importare le modifiche anche nella richiesta accesso atti
						if (eseguitaAzioneSuPrelievo && isStatoAttiEditable()) {
							
							SC.ask("I dati del prelievo sono stati aggiornati, li vuoi importare nella richiesta di accesso atti?", new BooleanCallback() {					
								@Override
								public void execute(Boolean value) {
									// Se value è true importo tutti i dati, altrimenti solamente quelli relativi alla scansione
									recuperaDatiProtocolloFromIdFolder(!value);
								}
							}); 
							
						} else {
							recuperaDatiProtocolloFromIdFolder(true);
						}
					}
				};
			}
		});
		visualizzaDettaglioPraticaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return idFolderItem.getValue() != null && !"".equals(idFolderItem.getValue());
			}
		});
		
//		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Ricerca in archivio");
//		lookupArchivioButton.setEndRow(false);
//		lookupArchivioButton.setColSpan(1);
//		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {
//
//			@Override
//			public void onIconClick(IconClickEvent event) {						
//				// Setto la radice come nodo di partenza predefinito
//				String idNodoRicerca = "/";
//				if (getItem() instanceof NuovoAttiRichiestiItem){
//					// Leggo il nodo di partenza dal dettaglio
//					idNodoRicerca = ((NuovoAttiRichiestiItem) getItem()).getIdNodoRicerca();
//				}
//				FascicoloLookupArchivio lookupArchivioPopup = new FascicoloLookupArchivio(estremiAttoRichiestoForm.getValuesAsRecord(), idNodoRicerca);
//				lookupArchivioPopup.show();
//			}
//		});		
		
		visualizzaContenutiFascicoloButton = new ImgButtonItem("visualizzaContenutiFascicoloButton", "menu/archivio.png"
				/*"archivio/flgUdFolder/fascicolo.png"*/, "Mostra contenuti fascicolo");
		visualizzaContenutiFascicoloButton.setEndRow(false);
		visualizzaContenutiFascicoloButton.setColSpan(1);
		visualizzaContenutiFascicoloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				// Setto la radice come nodo di partenza predefinito
				String idNodoRicerca = "/";
				if (getItem() instanceof NuovoAttiRichiestiItem){
					// Leggo il nodo di partenza dal dettaglio
					idNodoRicerca = ((NuovoAttiRichiestiItem) getItem()).getIdNodoRicerca();
				}
				Record lRecord = new Record();
				lRecord.setAttribute("idUdFolder", (String) idFolderItem.getValue());
				lRecord.setAttribute("idNode", idNodoRicerca);
				lRecord.setAttribute("provenienza", "SUE");
				ContenutiFascicoloPopup contenutiFascicoliPopup = new ContenutiFascicoloPopup(lRecord);
				contenutiFascicoliPopup.show();
			}
		});		
		
		statoAttoDaSincronizzareItem = new StaticTextItem("statoAttoDaSincronizzare", "<img src=\"images/notifica.png\" width=\"10\" size =\"10\" align=MIDDLE/>");
		statoAttoDaSincronizzareItem.setShowValueIconOnly(true);
		statoAttoDaSincronizzareItem.setShowTitle(false);
		statoAttoDaSincronizzareItem.setWidth(16);
		statoAttoDaSincronizzareItem.setHeight(16);
		statoAttoDaSincronizzareItem.setValueIconSize(16);
		statoAttoDaSincronizzareItem.setVAlign(VerticalAlignment.CENTER);
		statoAttoDaSincronizzareItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoAttoDaSincronizzareItem.setValueIconLeftPadding(5);
		
		Map<String, String> statoAttoDaSincronizzareValueIcons = new HashMap<String, String>();
		statoAttoDaSincronizzareValueIcons.put("1", "notifica.png");
		statoAttoDaSincronizzareValueIcons.put("0", "");
		statoAttoDaSincronizzareValueIcons.put("", "");
		statoAttoDaSincronizzareItem.setValueIcons(statoAttoDaSincronizzareValueIcons);
		
		statoAttoDaSincronizzareItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return statoAttoDaSincronizzareItem.getValue() != null && "1".equalsIgnoreCase((String) statoAttoDaSincronizzareItem.getValue());
			}
		});
		
		statoAttoDaSincronizzareItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Dati del fascicolo aggiornati rispetto a quelli riportati sulla presente richiesta";
			}
		});
		
		sincronizzaStatoAttoButton = new ImgButtonItem("sincronizzaStatoAttoButton", "refresh.png", "Sincronizza con dati fascicolo in archivio");
		sincronizzaStatoAttoButton.setAlwaysEnabled(true);
		
		sincronizzaStatoAttoButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {			
				recuperaDatiProtocolloFromIdFolder(false);
			}
		});
		
		sincronizzaStatoAttoButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isEstremiAttoEditable() || isStatoAttiEditable()) && statoAttoDaSincronizzareItem.getValue() != null && "1".equalsIgnoreCase((String) statoAttoDaSincronizzareItem.getValue()) && getEditing() != null && getEditing() == true;
			}
		});
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(20);
		
		final GWTRestDataSource selectInArchivioDS = new GWTRestDataSource("LoadComboInArchivioDataSource", "key", FieldType.TEXT);
		selectInArchivioItem = new SelectItem("inArchivio", "In archivio");		
		selectInArchivioItem.setValueField("key");
		selectInArchivioItem.setDisplayField("value");
		selectInArchivioItem.setOptionDataSource(selectInArchivioDS);
		selectInArchivioItem.setAutoFetchData(false);
		selectInArchivioItem.setAlwaysFetchMissingValues(true);
		selectInArchivioItem.setFetchMissingValues(true);
		selectInArchivioItem.setWidth(150);
		selectInArchivioItem.setStartRow(false);
		selectInArchivioItem.setRequired(true);		
		selectInArchivioItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getItem().getSelectedRecord() != null) {
					estremiAttoRichiestoForm.setValue("desInArchivio", event.getItem().getSelectedRecord().getAttribute("value"));
				}				
				statoAttoRichiestoForm.setValue("stato", "");
				reloadSelectStatoValueMap();
				tipoFascicoloForm.markForRedraw();
				statoAttoRichiestoForm.markForRedraw();
				responsabilePrelievoAttoForm.markForRedraw();
				ufficioPrelievoAttoForm.markForRedraw();
				noteAttoRichiestoForm.markForRedraw();
			}
		});
		
		desInArchivioItem = new HiddenItem("desInArchivio");
		
//		visureCollegateItem = new StaticTextItem("visureCollegate", setTitleAlign("Visure collegate", TITTLE_ALIGN_WIDTH, false));
//		visureCollegateItem.setWidth(360);
//		visureCollegateItem.setColSpan(15);
//		visureCollegateItem.setStartRow(true);
//		visureCollegateItem.setWrap(false);
//		visureCollegateItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				String visureCollegate = estremiAttoRichiestoForm.getValueAsString("visureCollegate");
//				return visureCollegate != null && !"".equalsIgnoreCase(visureCollegate);
//			}
//		});
		
		visureCollegateItem = new HiddenItem("visureCollegate");
		
		TitleItem titoloVisureCollegate = new TitleItem("Visure collegate");
		titoloVisureCollegate.setStartRow(false);
		titoloVisureCollegate.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				String visureCollegate = estremiAttoRichiestoForm.getValueAsString("visureCollegate");
				return visureCollegate != null && !"".equalsIgnoreCase(visureCollegate);
			}
		});
		
		visureCollegateButtonItem = new ImgButtonItem("visureCollegateItem", "buttons/documenti.png", "Visure collegate");
		visureCollegateButtonItem.setAlwaysEnabled(true);
		visureCollegateButtonItem.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {			
				String message = estremiAttoRichiestoForm.getValueAsString("visureCollegate");
				SC.say("Visure collegate", message);
			}
		});
		
		visureCollegateButtonItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String visureCollegate = estremiAttoRichiestoForm.getValueAsString("visureCollegate");
				return visureCollegate != null && !"".equalsIgnoreCase(visureCollegate);
			}
		});
			
		estremiAttoRichiestoForm.setFields(
				tipoProtocolloItem, idFolderItem,
				numProtocolloGeneraleItem, annoProtocolloGeneraleItem, 
				siglaProtocolloSettoreItem, numProtocolloSettoreItem, subProtocolloSettoreItem, annoProtocolloSettoreItem, 
				numPraticaWorkflowItem, annoPraticaWorkflowItem,
				statoScansioneItem, visualizzaDettaglioPraticaButton, statoAttoDaSincronizzareItem, 
				visualizzaContenutiFascicoloButton, sincronizzaStatoAttoButton, /*lookupArchivioButton,*/ spacerItem, selectInArchivioItem, desInArchivioItem, visureCollegateItem, spacerItem, titoloVisureCollegate, visureCollegateButtonItem);
	}
	
	private void buildTipoFascicoloForm(){
		
		tipoFascicoloForm = new ReplicableCanvasForm();
		tipoFascicoloForm.setWidth100();
		tipoFascicoloForm.setHeight(1);
		tipoFascicoloForm.setPadding(5);
		tipoFascicoloForm.setWrapItemTitles(false);
		tipoFascicoloForm.setNumCols(25);
		tipoFascicoloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(20);
		
		final GWTRestDataSource selectTipoFascicoloDS = new GWTRestDataSource("LoadComboTipoPraticaRichiestaAccessoAtti", "key", FieldType.TEXT);
		selectTipoFascicoloItem = new SelectItem("tipoFascicolo", setTitleAlign("Tipo fascicolo", TITTLE_ALIGN_WIDTH, true));
		selectTipoFascicoloItem.setValueField("key");
		selectTipoFascicoloItem.setDisplayField("value");
		selectTipoFascicoloItem.setOptionDataSource(selectTipoFascicoloDS);
		selectTipoFascicoloItem.setAutoFetchData(false);
		selectTipoFascicoloItem.setAlwaysFetchMissingValues(true);
		selectTipoFascicoloItem.setFetchMissingValues(true);
		selectTipoFascicoloItem.setWidth(120);
		selectTipoFascicoloItem.setStartRow(false);
		selectTipoFascicoloItem.setAllowEmptyValue(true);
		selectTipoFascicoloItem.setRequired(true);
		selectTipoFascicoloItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getItem().getSelectedRecord() != null) {
					tipoFascicoloForm.setValue("desTipoFascicolo", event.getItem().getSelectedRecord().getAttribute("value"));
				}
				tipoFascicoloForm.markForRedraw();
			}
		});
		
		desTipoFascicoloItem = new HiddenItem("desTipoFascicolo");
		
		numeroProtEdiliziaPrivataItem = new ExtendedTextItem("numeroProtEdiliziaPrivata", "EP N°");
		numeroProtEdiliziaPrivataItem.setWidth(100);
		numeroProtEdiliziaPrivataItem.setLength(50);
		numeroProtEdiliziaPrivataItem.setKeyPressFilter("[0-9]");
		
		
		annoProtEdiliziaPrivataItem = new AnnoItem("annoProtEdiliziaPrivata", "/");
		
		
		numeroWorkflowItem = new ExtendedTextItem("numeroWorkflow", "WF N°");
		numeroWorkflowItem.setWidth(100);
		numeroWorkflowItem.setLength(50);
		numeroWorkflowItem.setKeyPressFilter("[0-9]");
		numeroWorkflowItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isTipoProtocolloProtocolloGenerale() || isTipoProtocolloProtocolloDiSettore();
			}
		});
		
		annoWorkflowItem = new AnnoItem("annoWorkflow", "/");
		annoWorkflowItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isTipoProtocolloProtocolloGenerale() || isTipoProtocolloProtocolloDiSettore();
			}
		});
		
		numeroDepositoItem = new ExtendedTextItem("numeroDeposito", "N° deposito");
		numeroDepositoItem.setWidth(100);
		numeroDepositoItem.setLength(50);
		numeroDepositoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoFascicolo = tipoFascicoloForm.getValueAsString("tipoFascicolo");
				if (tipoFascicolo != null && "cementi armati".equalsIgnoreCase(tipoFascicolo)) {
					return true;
				}
				return false;
			}
		});
		
		classificaItem = new ExtendedTextItem("classifica", "Classifica");
		
		udcItem = new ExtendedTextItem("udc", "UDC");
		udcItem.setWidth(100);
		udcItem.setLength(50);
		udcItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				udcItem.setCanEdit(getEditing() && (isTaskArchivio() || !isArchivioCittadellaSelected()));
				return isArchivioCittadellaSelected();
			}
		});
				
		tipoFascicoloForm.setFields(selectTipoFascicoloItem, desTipoFascicoloItem, spacerItem, numeroProtEdiliziaPrivataItem, annoProtEdiliziaPrivataItem, spacerItem, numeroWorkflowItem, annoWorkflowItem, spacerItem, numeroDepositoItem, classificaItem, udcItem);		
	}
	
	private void buildStatoAttoRichiestoForm(){
		statoAttoRichiestoForm = new ReplicableCanvasForm();
		statoAttoRichiestoForm.setWidth100();
		statoAttoRichiestoForm.setHeight(1);
		statoAttoRichiestoForm.setPadding(5);
		statoAttoRichiestoForm.setWrapItemTitles(false);
		statoAttoRichiestoForm.setNumCols(18);
		statoAttoRichiestoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(20);
		
		selectStatoItem = new SelectItem("stato", setTitleAlign("Stato", TITTLE_ALIGN_WIDTH, false));
		selectStatoItem.setValueMap(buildSelectStatoValueMap());
		selectStatoItem.setStartRow(true);
		selectStatoItem.setDefaultValue(StatoEnum.VUOTO.tipoStato);
		selectStatoItem.setWidth(220);
		selectStatoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				responsabilePrelievoAttoForm.markForRedraw();
				ufficioPrelievoAttoForm.markForRedraw();
			}
		});
		
		final GWTRestDataSource selectTipoComunicazioneDS = new GWTRestDataSource("LoadComboTipoComunicazionePraticaRichiestaAccessoAtti", "key", FieldType.TEXT);
		selectTipoComunicazioneItem = new SelectItem("tipoComunicazione", "Tipo comunicazione");
		selectTipoComunicazioneItem.setValueField("key");
		selectTipoComunicazioneItem.setDisplayField("value");
		selectTipoComunicazioneItem.setOptionDataSource(selectTipoComunicazioneDS);
		selectTipoComunicazioneItem.setAutoFetchData(false);
		selectTipoComunicazioneItem.setAlwaysFetchMissingValues(true);
		selectTipoComunicazioneItem.setFetchMissingValues(true);		
		selectTipoComunicazioneItem.setWidth(220);
		selectTipoComunicazioneItem.setStartRow(false);
		selectTipoComunicazioneItem.setAllowEmptyValue(true);
		selectTipoComunicazioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				if (event.getItem().getSelectedRecord() != null) {
					statoAttoRichiestoForm.setValue("desTipoComunicazione", event.getItem().getSelectedRecord().getAttribute("value"));
				}
			}
		});
		
		desTipoComunicazioneItem = new HiddenItem("desTipoComunicazione");
		
		statoAttoRichiestoForm.setFields(selectStatoItem, spacerItem, selectTipoComunicazioneItem, desTipoComunicazioneItem);
	}
	
	private void buildUfficioPrelievoAttoForm(){
		
		ufficioPrelievoAttoForm = new ReplicableCanvasForm();
		ufficioPrelievoAttoForm.setWrapItemTitles(false);
		ufficioPrelievoAttoForm.setWidth100();
		ufficioPrelievoAttoForm.setHeight(1);
		ufficioPrelievoAttoForm.setPadding(5);
		ufficioPrelievoAttoForm.setNumCols(16);
		ufficioPrelievoAttoForm.setColWidths("50", "1", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "*");
		
		TitleItem titoloUfficioItem = new TitleItem(setTitleAlign("Prelievo effettuato dall'ufficio", TITTLE_ALIGN_WIDTH_2, false));
		titoloUfficioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}
		});
		
		selezionaUfficioPrelievoItems = new SelezionaUfficioItems(ufficioPrelievoAttoForm, "idUoUfficioPrelievo", "descrizioneUfficioPrelievo", "codRapidoUfficioPrelievo", "organigrammaUfficioPrelievo"){
			
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}
//			
//			@Override
//			protected boolean codRapidoItemValidator() {
//				if (isAttoDaRestituireInCittadella()){
//					return super.codRapidoItemValidator();
//				} else {
//					return true;
//				}
//			}
//			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}
//			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}			
		};
		
		dataPrelievoItem = new DateItem("dataPrelievo", "in data");
		dataPrelievoItem.setEndRow(true);
		dataPrelievoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}
		});
		
		List<FormItem> items = new ArrayList<FormItem>();
		items.add(titoloUfficioItem);
		items.addAll(selezionaUfficioPrelievoItems);
		items.add(dataPrelievoItem);
		
		ufficioPrelievoAttoForm.setFields(items.toArray(new FormItem[items.size()]));

	}
	
	private void buildResponsabilePrelievoForm(){
		
		responsabilePrelievoAttoForm = new ReplicableCanvasForm();
		responsabilePrelievoAttoForm.setWrapItemTitles(false);
		responsabilePrelievoAttoForm.setHeight(1);
		responsabilePrelievoAttoForm.setPadding(5);
		responsabilePrelievoAttoForm.setNumCols(16);
		responsabilePrelievoAttoForm.setColWidths("1", "10", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		responsabilePrelievoAttoForm.setWidth100();
		
				
		TitleItem titoloResponsabileItem = new TitleItem(setTitleAlign("Responsabile del prelievo", TITTLE_ALIGN_WIDTH_2, false));
		titoloResponsabileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}
		});
		
		responsabilePrelievoItems = new SelezionaUtenteItems(responsabilePrelievoAttoForm, "idUserResponsabilePrelievo", "usernameResponsabilePrelievo", 
				"codRapidoResponsabilePrelievo", "cognomeResponsabilePrelievo", "nomeResponsabilePrelievo", "codiceFiscaleResponsabilePrelievo", 
				"emailResponsabilePrelievo", "telefonoResponsabilePrelievo"){
			
			@Override
			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean lookupRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}

			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}

//			@Override
//			protected boolean nomeItemRequiredIfValidator(FormItem formItem, Object value) {
//				return super.nomeItemRequiredIfValidator(formItem, value) && isStatoAttiToShow() && isAttoDaRestituireInCittadella();
//			}

			@Override
			protected boolean nomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}

//			@Override
//			protected boolean cognomeItemRequiredIfValidator(FormItem formItem, Object value) {
//				return super.cognomeItemRequiredIfValidator(formItem, value) && isStatoAttiToShow() && isAttoDaRestituireInCittadella();
//			}

			@Override
			protected boolean cognomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();

			}
			
			@Override
			protected boolean utenteTrovatoButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return super.utenteTrovatoButtonShowIfCondition(item, value, form) && (isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici());
			}

			@Override
			protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}

			@Override
			protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoPrelevatoPerVisure() || isStatoPrelevatoDaAltriUffici();
			}

			@Override
			protected boolean telefonoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
			
			@Override
			protected boolean emailItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
			
		};
				
		List<FormItem> items = new ArrayList<FormItem>();
		items.add(titoloResponsabileItem);
		items.addAll(responsabilePrelievoItems);
		
		responsabilePrelievoAttoForm.setFields(items.toArray(new FormItem[items.size()]));
		
	}

	private void buildNoteAttoRichiestoForm(){
		
		noteAttoRichiestoForm = new ReplicableCanvasForm();
		noteAttoRichiestoForm.setWidth100();
		noteAttoRichiestoForm.setPadding(5);
		noteAttoRichiestoForm.setWrapItemTitles(false);
		noteAttoRichiestoForm.setNumCols(18);
		noteAttoRichiestoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setWidth(20);
		
		noteUffRichiedenteItem = new TextAreaItem("noteUffRichiedente", setTitleAlign("Note uff. visure", TITTLE_ALIGN_WIDTH, false));
		noteUffRichiedenteItem.setWidth(360);
		noteUffRichiedenteItem.setHeight(50);
		noteUffRichiedenteItem.setStartRow(true);
	
		noteCittadellaItem = new TextAreaItem("noteCittadella", "Note archivio");
		noteCittadellaItem.setWidth(360);
		noteCittadellaItem.setHeight(50);
		noteCittadellaItem.setStartRow(false);
		noteCittadellaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				noteCittadellaItem.setCanEdit(getEditing() && (isTaskArchivio() || !isArchivioCittadellaSelected()));
				return true;
			}
		});
		
		noteSportelloItem = new TextAreaItem("noteSportello", "Note sportello");
		noteSportelloItem.setWidth(360);
		noteSportelloItem.setHeight(50);
		noteSportelloItem.setStartRow(false);
			
		noteAttoRichiestoForm.setFields(noteUffRichiedenteItem, spacerItem, noteCittadellaItem, spacerItem, noteSportelloItem);
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] {estremiAttoRichiestoForm, tipoFascicoloForm, statoAttoRichiestoForm, responsabilePrelievoAttoForm, ufficioPrelievoAttoForm, noteAttoRichiestoForm};
	}
	
	@Override
	public Record getFormValuesAsRecord() {
		return super.getFormValuesAsRecord();
	}
	
	@Override
	public void editRecord(Record record) {
		manageLoadSelectInEditRecord(record, selectInArchivioItem, "inArchivio", "desInArchivio", null);
		manageLoadSelectInEditRecord(record, selectTipoFascicoloItem, "tipoFascicolo", "desTipoFascicolo", null);
		manageLoadSelectInEditRecord(record, selectTipoComunicazioneItem, "tipoComunicazione", "desTipoComunicazione", null);		
		super.editRecord(record);
		if (record.getAttribute("statoScansione") != null && "N".equalsIgnoreCase(record.getAttribute("statoScansione"))) {
			statoAttoRichiestoForm.setValue("flgRichiestaVisioneCartaceo", true);
		}
		selezionaUfficioPrelievoItems.afterEditRecord(record);
		reloadSelectStatoValueMap();
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		
		tipoProtocolloItem.setCanEdit(canEdit && isEstremiAttoEditable());
		numProtocolloGeneraleItem.setCanEdit(canEdit && isEstremiAttoEditable());
		annoProtocolloGeneraleItem.setCanEdit(canEdit && isEstremiAttoEditable());
		siglaProtocolloSettoreItem.setCanEdit(canEdit && isEstremiAttoEditable());
		numProtocolloSettoreItem.setCanEdit(canEdit && isEstremiAttoEditable());
		subProtocolloSettoreItem.setCanEdit(canEdit && isEstremiAttoEditable());
		annoProtocolloSettoreItem.setCanEdit(canEdit && isEstremiAttoEditable());
		
		udcItem.setCanEdit(canEdit && (isTaskArchivio() || !isArchivioCittadellaSelected()));
		
		noteUffRichiedenteItem.setCanEdit(canEdit && !isTaskArchivio());
		noteCittadellaItem.setCanEdit(canEdit && (isTaskArchivio() || !isArchivioCittadellaSelected()));
				
		if (getRemoveButton() != null) {
			getRemoveButton().setVisible(canEdit && isElencoAttoEditable());
		}
	
	}
		
	public void editRecordDaRecuperaDatiProtocollo(Record record, boolean aggiornaSoloStatoScansioni) {
		if(aggiornaSoloStatoScansioni){
			estremiAttoRichiestoForm.setValue("statoScansione", record.getAttribute("statoScansione"));
			boolean variazioni = false;
			if (!isRecordUgualiString(record, tipoFascicoloForm.getValuesAsRecord(), "tipoFascicolo", "numeroProtEdiliziaPrivata", "annoProtEdiliziaPrivata", "annoWorkflowItem", "numeroWorkflow", "numeroDeposito") ||
					!isRecordUgualiString(record, statoAttoRichiestoForm.getValuesAsRecord(), "stato", "classifica", "udc") ||
					!isRecordUgualiString(record, ufficioPrelievoAttoForm.getValuesAsRecord(), "idUoUfficioPrelievo", "descrizioneUfficioPrelievo", "codRapidoUfficioPrelievo", "organigrammaUfficioPrelievo") ||
					!isRecordUgualiString(record, responsabilePrelievoAttoForm.getValuesAsRecord(), "idUserResponsabilePrelievo", "cognomeResponsabilePrelievo", "nomeResponsabilePrelievo") ||
					!isRecordUgualiString(record, noteAttoRichiestoForm.getValuesAsRecord(), "noteCittadella")) {
				variazioni = true;
			}
//			if (!isRecordUgualiString(record, estremiAttoRichiestoForm.getValuesAsRecord(), "inArchivio") || !isRecordUgualiString(record, statoAttoRichiestoForm.getValuesAsRecord(), "stato", "classifica", "udc")) {
//				variazioni = true;
//			}
			estremiAttoRichiestoForm.setValue("statoAttoDaSincronizzare", variazioni ? "1" : "0");
			
		}else{
			estremiAttoRichiestoForm.editRecord(record);
			
			estremiAttoRichiestoForm.setValue("tipoProtocollo", record.getAttribute("tipoProtocollo"));
			estremiAttoRichiestoForm.setValue("idFolder", record.getAttribute("idFolder"));
			estremiAttoRichiestoForm.setValue("numProtocolloGenerale", record.getAttribute("numProtocolloGenerale"));
			estremiAttoRichiestoForm.setValue("annoProtocolloGenerale", record.getAttribute("annoProtocolloGenerale"));
			estremiAttoRichiestoForm.setValue("siglaProtocolloSettore", record.getAttribute("siglaProtocolloSettore"));
			estremiAttoRichiestoForm.setValue("numProtocolloSettore", record.getAttribute("numProtocolloSettore"));
			estremiAttoRichiestoForm.setValue("subProtocolloSettore", record.getAttribute("subProtocolloSettore"));
			estremiAttoRichiestoForm.setValue("annoProtocolloSettore", record.getAttribute("annoProtocolloSettore"));
			estremiAttoRichiestoForm.setValue("numPraticaWorkflow", record.getAttribute("numPraticaWorkflow"));
			estremiAttoRichiestoForm.setValue("annoPraticaWorkflow", record.getAttribute("annoPraticaWorkflow"));
			estremiAttoRichiestoForm.setValue("classifica", record.getAttribute("classifica"));
			
			statoAttoRichiestoForm.setValues(record.toMap());
			tipoFascicoloForm.setValues(record.toMap());
			ufficioPrelievoAttoForm.setValues(record.toMap());
			selezionaUfficioPrelievoItems.afterEditRecord(record);
			responsabilePrelievoAttoForm.setValues(record.toMap());
			noteAttoRichiestoForm.setValue("noteCittadella", record.getAttribute("noteCittadella"));
			
			String idFolder = record.getAttribute("idFolder");
			if ((idFolder == null) || ("".equalsIgnoreCase(idFolder))){
				statoAttoRichiestoForm.setValue("flgRichiestaVisioneCartaceo", false);
			}
		}
		// Aggiorno flgRichiestaVisioneCartaceoItem in base allo stato della scansione
		// Il fatto che sia modificabile o meno è gestito nello showif di flgRichiestaVisioneCartaceoItem
		String statoScansione = record.getAttribute("statoScansione");
		if (statoScansione != null && "C".equalsIgnoreCase(statoScansione)) {
			// Scansione completa, flgRichiestaVisioneCartaceoItem è visibile, deselezionato e modificabile
			statoAttoRichiestoForm.setValue("flgRichiestaVisioneCartaceo", false);
		} else if (statoScansione != null && "P".equalsIgnoreCase(statoScansione)) {
			// Scansione parziale, flgRichiestaVisioneCartaceoItem è visibile, selezionato e modificabile
			statoAttoRichiestoForm.setValue("flgRichiestaVisioneCartaceo", true);
		} else if (statoScansione != null && "N".equalsIgnoreCase(statoScansione)) {
			// Scansione non effettuata, flgRichiestaVisioneCartaceoItem è visibile, selezionato e non modificabile
			statoAttoRichiestoForm.setValue("flgRichiestaVisioneCartaceo", true);
		} else {
			// Non so lo stato della scansione, flgRichiestaVisioneCartaceoItem è visibile, modificabile e deselezionato
			statoAttoRichiestoForm.setValue("flgRichiestaVisioneCartaceo", false);
		}	
		
	}
	
	private boolean isRecordUgualiString(Record record1, Record record2, String ... attributiDaConfrontare) {
		for (String nomeAttributo : attributiDaConfrontare) {
			if (record1.getAttribute(nomeAttributo) == null && record2.getAttribute(nomeAttributo) == null) {
				// Entrambi gli attributi a null
				continue;
			}else if (record1.getAttribute(nomeAttributo) == null && record2.getAttribute(nomeAttributo) != null && "".equalsIgnoreCase(record2.getAttribute(nomeAttributo))) {
				// Considero null uguale a ""
				continue;
			}else if (record1.getAttribute(nomeAttributo) != null && "".equalsIgnoreCase(record1.getAttribute(nomeAttributo)) && record2.getAttribute(nomeAttributo) == null) {
				// Considero null uguale a ""
				continue;
			}else if ((record1.getAttribute(nomeAttributo) == null && record2.getAttribute(nomeAttributo) != null) || (record1.getAttribute(nomeAttributo) != null && record2.getAttribute(nomeAttributo) == null)){
				// Un attributo è null è l'altro è diverso da ""
				return false;
			}else if (!record1.getAttribute(nomeAttributo).equals(record2.getAttribute(nomeAttributo))) {
				// Gli attributi sono diversi da null e non uguali tra loro
				return false;
			}
		}
		return true;
	}
	
	protected TipoProtocolloPraticaEnum getTipoProtocollo() {
		String tipoProtocollo = estremiAttoRichiestoForm.getValue("tipoProtocollo") != null ? (String) estremiAttoRichiestoForm.getValue("tipoProtocollo") : "";
		if ((tipoProtocollo == null) || ("".equalsIgnoreCase(tipoProtocollo)) || (tipoProtocollo.equalsIgnoreCase(TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE.tipo()))){
			return TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE;
		} else if (tipoProtocollo.equalsIgnoreCase(TipoProtocolloPraticaEnum.PROTOCOLLO_DI_SETTORE.tipo())){
			return TipoProtocolloPraticaEnum.PROTOCOLLO_DI_SETTORE;
		} else {
			return TipoProtocolloPraticaEnum.PRATICA_WORKFLOW;
		}
	}
	 
	protected boolean isTipoProtocolloProtocolloGenerale() {
		return getTipoProtocollo().equals(TipoProtocolloPraticaEnum.PROTOCOLLO_GENERALE);
	}
	
	protected boolean isTipoProtocolloProtocolloDiSettore() {
		return getTipoProtocollo().equals(TipoProtocolloPraticaEnum.PROTOCOLLO_DI_SETTORE);
	}
	
	protected boolean isTipoProtocolloPraticaWorkflow() {
		return getTipoProtocollo().equals(TipoProtocolloPraticaEnum.PRATICA_WORKFLOW);
	}
	
	protected boolean isProtocolloGeneraleEmpty() {

		boolean hasNumProtocolloGeneraleEmpty = numProtocolloGeneraleItem.getValue() == null || "".equals(numProtocolloGeneraleItem.getValueAsString().trim());
		boolean hasAnnoProtocolloGeneraleEmpty = annoProtocolloGeneraleItem.getValue() == null || "".equals(annoProtocolloGeneraleItem.getValueAsString().trim());
		return hasNumProtocolloGeneraleEmpty && hasAnnoProtocolloGeneraleEmpty;
	}

	protected boolean isProtocolloSettoreEmpty() {

		boolean isSiglaProtocolloSettoreEmpty = siglaProtocolloSettoreItem.getValue() == null || "".equals(siglaProtocolloSettoreItem.getValueAsString().trim());
		boolean isNumProtocolloSettoreEmpty = numProtocolloSettoreItem.getValue() == null || "".equals(numProtocolloSettoreItem.getValueAsString().trim());
		boolean isAnnoProtocolloSettoreEmpty = annoProtocolloSettoreItem.getValue() == null || "".equals(annoProtocolloSettoreItem.getValueAsString().trim());
		return isSiglaProtocolloSettoreEmpty && isNumProtocolloSettoreEmpty && isAnnoProtocolloSettoreEmpty;
	}
	
	protected boolean isProtocolloPraticaWorkflowEmpty() {

		boolean hasNumPraticaWorkflowEmpty = numPraticaWorkflowItem.getValue() == null || "".equals(numPraticaWorkflowItem.getValueAsString().trim());
		boolean hasAnnoPraticaWorkflowEmpty = annoPraticaWorkflowItem.getValue() == null || "".equals(annoPraticaWorkflowItem.getValueAsString().trim());
		return hasNumPraticaWorkflowEmpty && hasAnnoPraticaWorkflowEmpty;
	}

	protected boolean hasProtocolloGenerale() {

		boolean hasNumProtocolloGenerale = numProtocolloGeneraleItem.getValue() != null && !"".equals(numProtocolloGeneraleItem.getValue());
		boolean hasAnnoProtocolloGenerale = annoProtocolloGeneraleItem.getValue() != null && !"".equals(annoProtocolloGeneraleItem.getValue());
		return hasNumProtocolloGenerale && hasAnnoProtocolloGenerale;
	}

	protected boolean hasProtocolloSettore() {

		boolean hasSiglaProtocolloSettore = siglaProtocolloSettoreItem.getValue() != null && !"".equals(siglaProtocolloSettoreItem.getValue());
		boolean hasNumProtocolloSettore = numProtocolloSettoreItem.getValue() != null && !"".equals(numProtocolloSettoreItem.getValue());
		boolean hasAnnoProtocolloSettore = annoProtocolloSettoreItem.getValue() != null && !"".equals(annoProtocolloSettoreItem.getValue());
		return hasSiglaProtocolloSettore && hasNumProtocolloSettore && hasAnnoProtocolloSettore;
	}
	
	protected boolean hasPraticaWorkflow() {

		boolean hasNumPraticaWorkflow = numPraticaWorkflowItem.getValue() != null && !"".equals(numPraticaWorkflowItem.getValue());
		boolean hasAnnoPraticaWorkflow = annoPraticaWorkflowItem.getValue() != null && !"".equals(annoPraticaWorkflowItem.getValue());
		return hasNumPraticaWorkflow && hasAnnoPraticaWorkflow;
	}

	protected void recuperaDatiProtocolloGenerale(boolean aggiornaSoloStatoScansioni) {

		if (hasProtocolloGenerale()) {
			Record lRecord = new Record();
			lRecord.setAttribute("tipoProtocollo", tipoProtocolloItem.getValueAsString());
			lRecord.setAttribute("numProtocolloGenerale", numProtocolloGeneraleItem.getValueAsString());
			lRecord.setAttribute("annoProtocolloGenerale", annoProtocolloGeneraleItem.getValueAsString());
			recuperaDatiProtocollo(lRecord, aggiornaSoloStatoScansioni);
		}
	}

	protected void recuperaDatiProtocolloSettore(boolean aggiornaSoloStatoScansioni) {

		if (hasProtocolloSettore()) {
			Record lRecord = new Record();
			lRecord.setAttribute("tipoProtocollo", tipoProtocolloItem.getValueAsString());
			lRecord.setAttribute("siglaProtocolloSettore", siglaProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("numProtocolloSettore", numProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("subProtocolloSettore", subProtocolloSettoreItem.getValueAsString());
			lRecord.setAttribute("annoProtocolloSettore", annoProtocolloSettoreItem.getValueAsString());
			recuperaDatiProtocollo(lRecord, aggiornaSoloStatoScansioni);
		}
	}
	
	protected void recuperaDatiPraticaWorkflow(boolean aggiornaSoloStatoScansioni) {

		if (hasPraticaWorkflow()) {
			Record lRecord = new Record();
			lRecord.setAttribute("tipoProtocollo", tipoProtocolloItem.getValueAsString());
			lRecord.setAttribute("numPraticaWorkflow", numPraticaWorkflowItem.getValueAsString());
			lRecord.setAttribute("annoPraticaWorkflow", annoPraticaWorkflowItem.getValueAsString());
			recuperaDatiProtocollo(lRecord, aggiornaSoloStatoScansioni);
		}
	}

	protected void recuperaDatiProtocolloFromIdFolder(boolean aggiornaSoloStatoScansioni){
		
		Record lRecord = new Record();
		lRecord.setAttribute("idFolder", (String) idFolderItem.getValue());
		recuperaDatiProtocollo(lRecord, aggiornaSoloStatoScansioni);
	}

	protected void recuperaDatiProtocollo(Record lRecord, final boolean aggiornaSoloStatoScansioni) {

		if (lRecord != null) {
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSource.executecustom("recuperaDatiAttoRichiesto", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						String idFolder = record.getAttribute("idFolder");
						if ((idFolder == null) || ("".equalsIgnoreCase(idFolder))){
							// Layout.addMessage(new MessageBean("Non esiste un fascicolo/pratica con gli estremi di protocollo specificati", "", MessageType.ERROR));
						}
						editRecordDaRecuperaDatiProtocollo(record, aggiornaSoloStatoScansioni);
						markForRedraw();
						
					}
				}
			});
		}
	}
	
	public void setFormValuesFromRecordArchivio(Record record, boolean aggiornaSoloStatoScansioni) {
		estremiAttoRichiestoForm.clearErrors(true);
		estremiAttoRichiestoForm.setValue("idFolder", record.getAttribute("idUdFolder"));
		// Carico i dati del fascicolo		
		if (idFolderItem.getValue() != null){
			recuperaDatiProtocolloFromIdFolder(aggiornaSoloStatoScansioni);
		}	
		estremiAttoRichiestoForm.markForRedraw();
	}
	
	private boolean isAttoPresenteInCittadella(){
		String stato = (String) statoAttoRichiestoForm.getValue("stato");
		return "presente in Cittadella".equalsIgnoreCase(stato);
	}
	
	private boolean isAttoNonPervenutoInCittadella(){
		String stato = (String) statoAttoRichiestoForm.getValue("stato");
		return "non pervenuto in Cittadella".equalsIgnoreCase(stato);
	}
	
	private boolean isAttoDaRestituireInCittadella(){
		String stato = (String) statoAttoRichiestoForm.getValue("stato");
		return "prelevato e ancora da restituire".equalsIgnoreCase(stato);
	}
	
	private boolean isSelectCartaceoReperibileItemToShow() {
		String selectCompetenzaDiUrbanisticaItemValue = statoAttoRichiestoForm.getValueAsString("competenzaDiUrbanistica");
		return !(selectCompetenzaDiUrbanisticaItemValue != null && "no".equalsIgnoreCase(selectCompetenzaDiUrbanisticaItemValue));
	}
	
	private boolean isSelectInArchivioItemToShow(){
		String selectCartaceoReperibileItemValue = statoAttoRichiestoForm.getValueAsString("cartaceoReperibile");
		return isSelectCartaceoReperibileItemToShow() && !(selectCartaceoReperibileItemValue != null && "no".equalsIgnoreCase(selectCartaceoReperibileItemValue));
	}
	
	private boolean isSelectCartaceoReperibileItemObbligatorio() {
		if (isSelectCartaceoReperibileItemToShow()) {
			String statoScansione = estremiAttoRichiestoForm.getValueAsString("statoScansione");
			return !(statoScansione != null && "C".equalsIgnoreCase(statoScansione));
		}
		return false;
	}
	
	private boolean isSelectInArchivioItemObbligatorio(){
		if (isSelectInArchivioItemToShow()) {
			String statoScansione = estremiAttoRichiestoForm.getValueAsString("statoScansione");
			return !(statoScansione != null && "C".equalsIgnoreCase(statoScansione));
		}
		return false;
	}
	
	public boolean isChangedAndValid() {
		
		String numProtocolloGenerale = estremiAttoRichiestoForm.getValueAsString("numProtocolloGenerale");
		String annoProtocolloGenerale = estremiAttoRichiestoForm.getValueAsString("annoProtocolloGenerale");
		if (numProtocolloGenerale != null && !"".equalsIgnoreCase(numProtocolloGenerale) && annoProtocolloGenerale != null && !"".equalsIgnoreCase(annoProtocolloGenerale)) {
			return true;
		}
		
		String numProtocolloSettore = estremiAttoRichiestoForm.getValueAsString("numProtocolloSettore");
		String annoProtocolloSettore = estremiAttoRichiestoForm.getValueAsString("annoProtocolloSettore");
		if (numProtocolloSettore != null && !"".equalsIgnoreCase(numProtocolloSettore) && annoProtocolloSettore != null && !"".equalsIgnoreCase(annoProtocolloSettore)) {
			return true;
		}
		
		String numPraticaWorkflow = estremiAttoRichiestoForm.getValueAsString("numPraticaWorkflow");
		String annoPraticaWorkflow = estremiAttoRichiestoForm.getValueAsString("annoPraticaWorkflow");
		if (numPraticaWorkflow != null && !"".equalsIgnoreCase(numPraticaWorkflow) && annoPraticaWorkflow != null && !"".equalsIgnoreCase(annoPraticaWorkflow)) {
			return true;
		}
		
		return false;
	}
	
	private void reloadSelectStatoValueMap(){
		LinkedHashMap<String, String> tipoValueMap = buildSelectStatoValueMap();
		selectStatoItem.setValueMap(tipoValueMap);
	}
	
	private LinkedHashMap<String, String> buildSelectStatoValueMap(){
		LinkedHashMap<String, String> statoValueMap = new LinkedHashMap<String, String>();
		statoValueMap.put(StatoEnum.VUOTO.tipoStato, StatoEnum.VUOTO.descrizioneStato());
		String archivio = estremiAttoRichiestoForm.getValueAsString("inArchivio");
		if (archivio != null && "cittadella".equalsIgnoreCase(archivio)) {
			statoValueMap.put(StatoEnum.PRESENTE_IN_CITTADELLA.tipoStato, StatoEnum.PRESENTE_IN_CITTADELLA.descrizioneStato());
			statoValueMap.put(StatoEnum.NON_PERVENUTO_IN_CITTADELLA.tipoStato, StatoEnum.NON_PERVENUTO_IN_CITTADELLA.descrizioneStato());
			statoValueMap.put(StatoEnum.PRELEVATO_PER_VISURE.tipoStato, StatoEnum.PRELEVATO_PER_VISURE.descrizioneStato());
			statoValueMap.put(StatoEnum.PRELEVATO_PER_ALTRI_UFFICI.tipoStato, StatoEnum.PRELEVATO_PER_ALTRI_UFFICI.descrizioneStato());
			statoValueMap.put(StatoEnum.ATTI_ERRATI.tipoStato, StatoEnum.ATTI_ERRATI.descrizioneStato());
			statoValueMap.put(StatoEnum.PERVENUTO_UFF_VISURE.tipoStato, StatoEnum.PERVENUTO_UFF_VISURE.descrizioneStato());
			statoValueMap.put(StatoEnum.IN_VISURA.tipoStato, StatoEnum.IN_VISURA.descrizioneStato());
			statoValueMap.put(StatoEnum.GESTIONE_COPIE.tipoStato, StatoEnum.GESTIONE_COPIE.descrizioneStato());
			statoValueMap.put(StatoEnum.TRATTENUTA_DA_UFFICIO.tipoStato, StatoEnum.TRATTENUTA_DA_UFFICIO.descrizioneStato());
			statoValueMap.put(StatoEnum.COMPETATO_ACCESSO.tipoStato, StatoEnum.COMPETATO_ACCESSO.descrizioneStato());
			statoValueMap.put(StatoEnum.SUPPLEMENTO_DI_RICERCA.tipoStato, StatoEnum.SUPPLEMENTO_DI_RICERCA.descrizioneStato());
			statoValueMap.put(StatoEnum.RESTITUITO_ALL_ARCHIVIO.tipoStato, StatoEnum.RESTITUITO_ALL_ARCHIVIO.descrizioneStato());			
		} else if (archivio != null && !"".equalsIgnoreCase(archivio)){
			statoValueMap.put(StatoEnum.TROVATO.tipoStato, StatoEnum.TROVATO.descrizioneStato());
			statoValueMap.put(StatoEnum.NON_TROVATO.tipoStato, StatoEnum.NON_TROVATO.descrizioneStato());
			statoValueMap.put(StatoEnum.PERVENUTO_UFF_VISURE.tipoStato, StatoEnum.PERVENUTO_UFF_VISURE.descrizioneStato());
			statoValueMap.put(StatoEnum.IN_VISURA.tipoStato, StatoEnum.IN_VISURA.descrizioneStato());
			statoValueMap.put(StatoEnum.GESTIONE_COPIE.tipoStato, StatoEnum.GESTIONE_COPIE.descrizioneStato());
			statoValueMap.put(StatoEnum.TRATTENUTA_DA_UFFICIO.tipoStato, StatoEnum.TRATTENUTA_DA_UFFICIO.descrizioneStato());
			statoValueMap.put(StatoEnum.COMPETATO_ACCESSO.tipoStato, StatoEnum.COMPETATO_ACCESSO.descrizioneStato());
			statoValueMap.put(StatoEnum.SUPPLEMENTO_DI_RICERCA.tipoStato, StatoEnum.SUPPLEMENTO_DI_RICERCA.descrizioneStato());
			statoValueMap.put(StatoEnum.RESTITUITO_ALL_ARCHIVIO.tipoStato, StatoEnum.RESTITUITO_ALL_ARCHIVIO.descrizioneStato());			
		} else {
			
		}
		return statoValueMap;
		
	}

	public class TitleStaticTextItem extends StaticTextItem {

		public TitleStaticTextItem(String title, int width) {
			setTitle(title);
			setColSpan(1);
			setDefaultValue(title + AurigaLayout.getSuffixFormItemTitle());
			setWidth(width);
			setShowTitle(false);
			setAlign(Alignment.RIGHT);
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
			setWrap(false);
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
		}
	}
	
	public class FascicoloLookupArchivio extends LookupArchivioPopup {

		public FascicoloLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}
		
		@Override
		public String getWindowTitle() {
			return "Fascicolazione";
		}
		
		@Override
		public String getFinalita() {
			return "SEL_X_ACCESSO_ATTI";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record, false);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}
	
	public enum TipoProtocolloPraticaEnum {
	    PROTOCOLLO_GENERALE("PG", "Protocollo generale"),
	    PROTOCOLLO_DI_SETTORE("PS", "Protocollo di settore"),
		PRATICA_WORKFLOW("WF", "Pratica WF");

	    private String tipo;
	    private String descrizione;

	    TipoProtocolloPraticaEnum(String tipo, String descrizione) {
	        this.tipo = tipo;
	        this.descrizione = descrizione;
	    }

	    public String tipo() {
	        return tipo;
	    }
	    
	    public String descrizione() {
	        return descrizione;
	    }
	}
	
	public enum StatoEnum {
		VUOTO("", ""),
		PRESENTE_IN_CITTADELLA("presente in Cittadella", "presente in Cittadella"),
		NON_PERVENUTO_IN_CITTADELLA("non pervenuto in Cittadella", "non pervenuto in Cittadella"),
		PRELEVATO_PER_VISURE("prelevato per visure", "prelevato per visure"),
		PRELEVATO_PER_ALTRI_UFFICI("prelevato da altri uffici", "prelevato da altri uffici"),
		ATTI_ERRATI("atti errati", "atti errati"),
		TROVATO("trovato", "trovato"),
		NON_TROVATO("non trovato", "non trovato"),
		PERVENUTO_UFF_VISURE ("pervenuto uff. visure",  "pervenuto uff. visure"),
		IN_VISURA ("in visura", "in visura"),
		GESTIONE_COPIE ("gestione copie", "gestione copie"),
		TRATTENUTA_DA_UFFICIO ("trattenuta da ufficio", "trattenuta da ufficio"),
		COMPETATO_ACCESSO ("completato accesso", "completato accesso"),
		SUPPLEMENTO_DI_RICERCA ("supplemento di ricerca", "supplemento di ricerca"),
		RESTITUITO_ALL_ARCHIVIO ("restituito all'archivio", "restituito all'archivio");

	    private String tipoStato;
	    private String descrizioneStato;

	    StatoEnum(String tipoStato, String descrizioneStato) {
	        this.tipoStato = tipoStato;
	        this.descrizioneStato = descrizioneStato;
	    }

	    public String tipoStato() {
	        return tipoStato;
	    }
	    
	    public String descrizioneStato() {
	        return descrizioneStato;
	    }
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	protected void setRequiredInItem(FormItem item, String title, boolean required) {
		item.setAttribute("obbligatorio", required);
		if (required) {
			item.setTitle(FrontendUtil.getRequiredFormItemTitle(title));
			item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		} else {
			item.setTitle(title);
			item.setTitleStyle(it.eng.utility.Styles.formTitle);
		}
	}
	
	@Override
	public boolean validate() {
		getValuesManager().clearErrors(true);		
		return getValuesManager().validate();
	}
	
//	protected boolean isStatoAttiToShow(){
//		//boolean toShow = ((AttiRichiestiItem)getItem()).isStatoAttiInCanvasToShow() && statoAttoRichiestoForm != null && statoAttoRichiestoForm.getValueAsString("stato") != null && !"".equalsIgnoreCase(statoAttoRichiestoForm.getValueAsString("stato"));
//		boolean toShow = ((AttiRichiestiItem)getItem()).isStatoAttiInCanvasToShow();
//		if(statoAttoRichiestoForm != null) {
//			statoAttoRichiestoForm.setVisible(toShow);			
//		}
//		if(responsabilePrelievoAttoForm != null) {
//			responsabilePrelievoAttoForm.setVisible(toShow && isAttoDaRestituireInCittadella());
//		}
//		if(ufficioPrelievoAttoForm != null) {
//			ufficioPrelievoAttoForm.setVisible(toShow && isAttoDaRestituireInCittadella());
//		}
//		return toShow;
//	}
	
	protected boolean isElencoAttoEditable() {
		return ((NuovoAttiRichiestiItem)getItem()).isElencoAttiRichiestiInCanvasEditable();
	}
	
	protected boolean isEstremiAttoEditable() {
		return ((NuovoAttiRichiestiItem)getItem()).isEstremiAttiRichiestiInCanvasEditable();
	}
	
	protected boolean isStatoAttiEditable(){
		return ((NuovoAttiRichiestiItem)getItem()).isStatoAttiInCanvasEditable();
	}
	
	protected boolean isStatoPrelevatoPerVisure(){
		String stato = statoAttoRichiestoForm.getValueAsString("stato");
		if (stato != null && "prelevato per visure".equalsIgnoreCase(stato)) {
			return true;
		}
		return false;
	}
	
	protected boolean isStatoPrelevatoDaAltriUffici(){
		String stato = statoAttoRichiestoForm.getValueAsString("stato");
		if (stato != null && "prelevato da altri uffici".equalsIgnoreCase(stato)) {
			return true;
		}
		return false;
	}
	
	protected boolean isArchivioCittadellaSelected() {
		String archivio = estremiAttoRichiestoForm != null ? estremiAttoRichiestoForm.getValueAsString("inArchivio") : "";
		if (archivio != null && "cittadella".equalsIgnoreCase(archivio)) {
			return true;
		}
		return false;
	}
	
	protected boolean isNoteUffRichiedenteToEnable(){
		return ((NuovoAttiRichiestiItem)getItem()).isNoteUffRichiedenteInCanvasToEnable();
	}
	
	protected boolean isNoteCittadellaToEnable(){
		return ((NuovoAttiRichiestiItem)getItem()).isNoteCittadellaInCanvasToEnable();
	}
	
	protected boolean isTaskArchivio(){
		return ((NuovoAttiRichiestiItem)getItem()).isTaskArchivioInCanvas();
	}

}
