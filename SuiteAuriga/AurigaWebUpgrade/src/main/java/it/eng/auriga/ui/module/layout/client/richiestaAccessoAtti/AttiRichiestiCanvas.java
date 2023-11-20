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
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
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

public class AttiRichiestiCanvas extends ReplicableCanvas {
	
	private final int TITTLE_ALIGN_WIDTH = 110;
	private final int TITTLE_ALIGN_WIDTH_2 = 250;
	private VLayout lVLayoutCanvas;
	
	// DynamicForm
	private ReplicableCanvasForm estremiAttoRichiestoForm;
	private ReplicableCanvasForm statoAttoRichiestoForm;
	private ReplicableCanvasForm ufficioPrelievoAttoForm;
	private ReplicableCanvasForm responsabilePrelievoAttoForm;
	private ReplicableCanvasForm noteAttoRichiestoForm;

	private SelectItem tipoProtocolloItem;
	private HiddenItem idFolderItem;
	protected ExtendedTextItem numProtocolloGeneraleItem;
	protected AnnoItem annoProtocolloGeneraleItem;
	protected ExtendedTextItem siglaProtocolloSettoreItem;
	protected ExtendedTextItem numProtocolloSettoreItem;
	protected ExtendedNumericItem subProtocolloSettoreItem;
	protected AnnoItem annoProtocolloSettoreItem;
	
	private StaticTextItem statoScansioneItem;	
	private ImgButtonItem visualizzaDettaglioPraticaButton;
	private ImgButtonItem lookupArchivioButton;
	private StaticTextItem statoAttoDaSincronizzareItem;
	private ImgButtonItem sincronizzaStatoAttoButton;
	
	private ExtendedTextItem classificaItem;
	
	private SelectItem selectStatoItem;
	private ExtendedTextItem udcItem;
	private SelezionaUfficioItems selezionaUfficioPrelievoItems;
	private DateItem dataPrelievoItem;
	private SelezionaUtenteItems responsabilePrelievoItems;
	
	private TextAreaItem noteUffRichiedenteItem;
	private TextAreaItem noteCittadellaItem;	

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
		buildStatoAttoRichiestoForm();
		buildUfficioPrelievoAttoForm();
		buildResponsabilePrelievoForm();
		buildNoteAttoRichiestoForm();
				
		lVLayoutCanvas.setMembers(estremiAttoRichiestoForm, statoAttoRichiestoForm, ufficioPrelievoAttoForm, responsabilePrelievoAttoForm, noteAttoRichiestoForm);
				
		addChild(lVLayoutCanvas);
	}
	
	private void buildEstremiAttoRichiestoForm(){
		
		estremiAttoRichiestoForm = new ReplicableCanvasForm();
		estremiAttoRichiestoForm.setWidth100();
		estremiAttoRichiestoForm.setPadding(5);
		estremiAttoRichiestoForm.setWrapItemTitles(false);
		estremiAttoRichiestoForm.setNumCols(25);
		estremiAttoRichiestoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");		

		tipoProtocolloItem = new SelectItem("tipoProtocollo", setTitleAlign("Tipo protocollo", TITTLE_ALIGN_WIDTH, false));
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put(TipoProtocolloEnum.PROTOCOLLO_GENERALE.tipoProtocollo, TipoProtocolloEnum.PROTOCOLLO_GENERALE.descrizioneProtocollo);
		tipoValueMap.put(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE.tipoProtocollo, TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE.descrizioneProtocollo);
		tipoProtocolloItem.setValueMap(tipoValueMap);
		tipoProtocolloItem.setStartRow(true);
		tipoProtocolloItem.setColSpan(1);
		tipoProtocolloItem.setDefaultValue(TipoProtocolloEnum.PROTOCOLLO_GENERALE.tipoProtocollo);
		tipoProtocolloItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				estremiAttoRichiestoForm.setValue("idFolder", (String)null);
				estremiAttoRichiestoForm.setValue("statoScansione", (String)null);
				String tipoProtocollo = (String) event.getValue();				
				if(tipoProtocollo != null && tipoProtocollo.equals(TipoProtocolloEnum.PROTOCOLLO_GENERALE.tipoProtocollo)) {
					estremiAttoRichiestoForm.setValue("siglaProtocolloSettore", (String)null);
					estremiAttoRichiestoForm.setValue("numProtocolloSettore", (String)null);
					estremiAttoRichiestoForm.setValue("subProtocolloSettore", (String)null);
					estremiAttoRichiestoForm.setValue("annoProtocolloSettore", (String)null);					
				} else if(tipoProtocollo != null && tipoProtocollo.equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE.tipoProtocollo)) {
					estremiAttoRichiestoForm.setValue("numProtocolloGenerale", (String)null);
					estremiAttoRichiestoForm.setValue("annoProtocolloGenerale", (String)null);					
				}				
				markForRedraw();
			}
		});
		tipoProtocolloItem.setRequired(true);
		
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
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_GENERALE);
			}
		});
		numProtocolloGeneraleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_GENERALE) && !isProtocolloGeneraleEmpty();
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
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_GENERALE);
			}
		});
		annoProtocolloGeneraleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_GENERALE) && !isProtocolloGeneraleEmpty();
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
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE);
			}
		});
		siglaProtocolloSettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE) && !isProtocolloSettoreEmpty();
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
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE);
			}
		});
		numProtocolloSettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE) && !isProtocolloSettoreEmpty();
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
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE);
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
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE);
			}
		});
		annoProtocolloSettoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getTipoProtocollo().equals(TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE) && !isProtocolloSettoreEmpty();
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
				DettaglioPraticaPregressaWindow dettaglioPraticaPregressaWindow = new DettaglioPraticaPregressaWindow((String) idFolderItem.getValue()){
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
		
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Ricerca in archivio");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				// Setto la radice come nodo di partenza predefinito
				String idNodoRicerca = "/";
				if (getItem() instanceof AttiRichiestiItem){
					// Leggo il nodo di partenza dal dettaglio
					idNodoRicerca = ((AttiRichiestiItem) getItem()).getIdNodoRicerca();
				}
				FascicoloLookupArchivio lookupArchivioPopup = new FascicoloLookupArchivio(estremiAttoRichiestoForm.getValuesAsRecord(), idNodoRicerca);
				lookupArchivioPopup.show();
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
				//return isStatoAttiEditable() && statoAttoDaSincronizzareItem.getValue() != null && "1".equalsIgnoreCase((String) statoAttoDaSincronizzareItem.getValue()) && getEditing() != null && getEditing() == true;
				return (isEstremiAttoEditable() || isStatoAttiEditable()) && statoAttoDaSincronizzareItem.getValue() != null && "1".equalsIgnoreCase((String) statoAttoDaSincronizzareItem.getValue()) && getEditing() != null && getEditing() == true;
			}
		});
		
		classificaItem = new ExtendedTextItem("classifica", "Classifica");
		
		SpacerItem classificaSpacerItem = new SpacerItem();
		classificaSpacerItem.setWidth(15);
			
		estremiAttoRichiestoForm.setFields(
				tipoProtocolloItem, idFolderItem,
				numProtocolloGeneraleItem, annoProtocolloGeneraleItem, 
				siglaProtocolloSettoreItem, numProtocolloSettoreItem, subProtocolloSettoreItem, annoProtocolloSettoreItem, 
				statoScansioneItem, visualizzaDettaglioPraticaButton, statoAttoDaSincronizzareItem, 
				sincronizzaStatoAttoButton, lookupArchivioButton, classificaSpacerItem, classificaItem);
	}
	
	private void buildStatoAttoRichiestoForm(){
		
		statoAttoRichiestoForm = new ReplicableCanvasForm();
		statoAttoRichiestoForm.setWidth100();
		statoAttoRichiestoForm.setHeight(1);
		statoAttoRichiestoForm.setPadding(5);
		statoAttoRichiestoForm.setWrapItemTitles(false);
		statoAttoRichiestoForm.setNumCols(18);
		statoAttoRichiestoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
				
		selectStatoItem = new SelectItem("stato", setTitleAlign("Stato", TITTLE_ALIGN_WIDTH, false));
		LinkedHashMap<String, String> statoValueMap = new LinkedHashMap<String, String>();
		statoValueMap.put(StatoEnum.VUOTO.tipoStato, StatoEnum.VUOTO.descrizioneStato());
		statoValueMap.put(StatoEnum.PRESENTE_IN_CITTADELLA.tipoStato, StatoEnum.PRESENTE_IN_CITTADELLA.descrizioneStato());
		statoValueMap.put(StatoEnum.PRELEVATO_E_ANCORA_DA_RESTITUIRE.tipoStato, StatoEnum.PRELEVATO_E_ANCORA_DA_RESTITUIRE.descrizioneStato());
		statoValueMap.put(StatoEnum.NON_PERVENUTO_IN_CITTADELLA.tipoStato, StatoEnum.NON_PERVENUTO_IN_CITTADELLA.descrizioneStato());
		selectStatoItem.setValueMap(statoValueMap);
		selectStatoItem.setStartRow(true);
		selectStatoItem.setDefaultValue(StatoEnum.VUOTO.tipoStato);
		selectStatoItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				markForRedraw();
			}
		});
		selectStatoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoAttiToShow();
			}
		});
		
		udcItem = new ExtendedTextItem("udc", "UDC");
		udcItem.setWidth(100);
		udcItem.setLength(50);
		udcItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoAttiToShow() && (isAttoPresenteInCittadella() || isAttoDaRestituireInCittadella());
			}
		});
		
		statoAttoRichiestoForm.setFields(selectStatoItem, udcItem);		
	}
	
	private void buildUfficioPrelievoAttoForm(){
		
		ufficioPrelievoAttoForm = new ReplicableCanvasForm();
		ufficioPrelievoAttoForm.setWrapItemTitles(false);
		ufficioPrelievoAttoForm.setWidth100();
		ufficioPrelievoAttoForm.setHeight(1);
		ufficioPrelievoAttoForm.setPadding(5);
		ufficioPrelievoAttoForm.setNumCols(16);
		ufficioPrelievoAttoForm.setColWidths("50", "1", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");
		
		TitleItem titoloUfficioItem = new TitleItem(setTitleAlign("Prelievo effettuato dall'ufficio", TITTLE_ALIGN_WIDTH_2, false));
		titoloUfficioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}
		});
		
		selezionaUfficioPrelievoItems = new SelezionaUfficioItems(ufficioPrelievoAttoForm, "idUoUfficioPrelievo", "descrizioneUfficioPrelievo", "codRapidoUfficioPrelievo", "organigrammaUfficioPrelievo"){
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}
			
			@Override
			protected boolean codRapidoItemValidator() {
				if (isAttoDaRestituireInCittadella()){
					return super.codRapidoItemValidator();
				} else {
					return true;
				}
			}
			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}
			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}
			
			
		};
		
		dataPrelievoItem = new DateItem("dataPrelievo", "in data");
		dataPrelievoItem.setEndRow(true);
		dataPrelievoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
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
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
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
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}

			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}

			@Override
			protected boolean nomeItemRequiredIfValidator(FormItem formItem, Object value) {
				return super.nomeItemRequiredIfValidator(formItem, value) && isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}

			@Override
			protected boolean nomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}

			@Override
			protected boolean cognomeItemRequiredIfValidator(FormItem formItem, Object value) {
				return super.cognomeItemRequiredIfValidator(formItem, value) && isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}

			@Override
			protected boolean cognomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();

			}

			@Override
			protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
			}

			@Override
			protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return isStatoAttiToShow() && isAttoDaRestituireInCittadella();
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
		
		noteUffRichiedenteItem = new TextAreaItem("noteUffRichiedente", setTitleAlign("Note uff. richiedente", TITTLE_ALIGN_WIDTH, false));
		noteUffRichiedenteItem.setWidth(360);
		noteUffRichiedenteItem.setHeight(50);
		noteUffRichiedenteItem.setStartRow(true);
		
		SpacerItem noteAttoRichiestoSpacerItem = new SpacerItem();
		noteAttoRichiestoSpacerItem.setWidth(22);
	
		noteCittadellaItem = new TextAreaItem("noteCittadella", "Note cittadella");
		noteCittadellaItem.setWidth(360);
		noteCittadellaItem.setHeight(50);
		noteCittadellaItem.setStartRow(false);
		
		noteAttoRichiestoForm.setFields(noteUffRichiedenteItem, noteAttoRichiestoSpacerItem, noteCittadellaItem);
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] {estremiAttoRichiestoForm, statoAttoRichiestoForm, ufficioPrelievoAttoForm, responsabilePrelievoAttoForm, noteAttoRichiestoForm};
	}
	
	@Override
	public Record getFormValuesAsRecord() {
		return super.getFormValuesAsRecord();
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		selezionaUfficioPrelievoItems.afterEditRecord(record);
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
		
		selectStatoItem.setCanEdit(canEdit && isStatoAttiEditable());
		udcItem.setCanEdit(canEdit && isStatoAttiEditable());
		selezionaUfficioPrelievoItems.setCanEdit(canEdit && isStatoAttiEditable());
		dataPrelievoItem.setCanEdit(canEdit && isStatoAttiEditable());
		responsabilePrelievoItems.setCanEdit(canEdit && isStatoAttiEditable());
		
		noteUffRichiedenteItem.setCanEdit(canEdit && isNoteUffRichiedenteToEnable());
		noteCittadellaItem.setCanEdit(canEdit && isNoteCittadellaToEnable());
		
		if (getRemoveButton() != null) {
			//getRemoveButton().setAlwaysDisabled(canEdit && isElencoAttoEditable());
			getRemoveButton().setVisible(canEdit && isElencoAttoEditable());
		}
	}
		
	public void editRecordDaRecuperaDatiProtocollo(Record record, boolean aggiornaSoloStatoScansioni) {
		if(aggiornaSoloStatoScansioni){
			estremiAttoRichiestoForm.setValue("statoScansione", record.getAttribute("statoScansione"));
			boolean variazioni = false;
			String statoAtto = statoAttoRichiestoForm.getValueAsString("stato");
			if (isRecordUgualiString(record, statoAttoRichiestoForm.getValuesAsRecord(), "stato")) {
				if (statoAtto != null && statoAtto.toLowerCase().startsWith("presente")) {
					if (!isRecordUgualiString(record, estremiAttoRichiestoForm.getValuesAsRecord(), "classifica") ||
							!isRecordUgualiString(record, statoAttoRichiestoForm.getValuesAsRecord(), "stato", "udc") ||
							!isRecordUgualiString(record, noteAttoRichiestoForm.getValuesAsRecord(), "noteCittadella")){
						variazioni = true;
					}
				} else if (statoAtto != null && statoAtto.toLowerCase().startsWith("prelevato")) {
					if (!isRecordUgualiString(record, estremiAttoRichiestoForm.getValuesAsRecord(), "classifica") ||
							!isRecordUgualiString(record, statoAttoRichiestoForm.getValuesAsRecord(), "stato", "udc") ||
							!isRecordUgualiString(record, ufficioPrelievoAttoForm.getValuesAsRecord(), "idUoUfficioPrelievo", "descrizioneUfficioPrelievo", "codRapidoUfficioPrelievo", "organigrammaUfficioPrelievo") ||
							!isRecordUgualiString(record, responsabilePrelievoAttoForm.getValuesAsRecord(), "idUserResponsabilePrelievo", "cognomeResponsabilePrelievo", "nomeResponsabilePrelievo") ||
							!isRecordUgualiString(record, noteAttoRichiestoForm.getValuesAsRecord(), "noteCittadella")) {
						variazioni = true;					
					}
				}else if (statoAtto != null && statoAtto.toLowerCase().startsWith("non pervenuto")) {
					if (!isRecordUgualiString(record, estremiAttoRichiestoForm.getValuesAsRecord(), "classifica") ||
							!isRecordUgualiString(record, statoAttoRichiestoForm.getValuesAsRecord(), "stato") ||
							!isRecordUgualiString(record, noteAttoRichiestoForm.getValuesAsRecord(), "noteCittadella")){
						variazioni = true;
					}
				}
			}else {
				variazioni = true;
			}
			
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
			estremiAttoRichiestoForm.setValue("classifica", record.getAttribute("classifica"));
			
			statoAttoRichiestoForm.setValues(record.toMap());
			ufficioPrelievoAttoForm.setValues(record.toMap());
			selezionaUfficioPrelievoItems.afterEditRecord(record);
			responsabilePrelievoAttoForm.setValues(record.toMap());
			noteAttoRichiestoForm.setValue("noteCittadella", record.getAttribute("noteCittadella"));

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
	
	protected TipoProtocolloEnum getTipoProtocollo() {
		String tipoProtocollo = estremiAttoRichiestoForm.getValue("tipoProtocollo") != null ? (String) estremiAttoRichiestoForm.getValue("tipoProtocollo") : "";
		if ((tipoProtocollo == null) || ("".equalsIgnoreCase(tipoProtocollo)) || (tipoProtocollo.equalsIgnoreCase(TipoProtocolloEnum.PROTOCOLLO_GENERALE.tipoProtocollo()))){
			return TipoProtocolloEnum.PROTOCOLLO_GENERALE;
		}else{
			return TipoProtocolloEnum.PROTOCOLLO_DI_SETTORE;
		}
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

	private void setIfNotNull(String property, Record record, String attribute) {
		if (estremiAttoRichiestoForm.getValue(property) == null) {
			estremiAttoRichiestoForm.setValue(property, record.getAttribute(attribute));
		} else {
			if (!estremiAttoRichiestoForm.getValue(property).equals(record.getAttribute(attribute))) {
				estremiAttoRichiestoForm.setValue(property, record.getAttribute(attribute));
			}
		}
	}

	private void setIfNotNull(String property, String value) {
		if (estremiAttoRichiestoForm.getValue(property) == null) {
			estremiAttoRichiestoForm.setValue(property, value);
		} else {
			if (!estremiAttoRichiestoForm.getValue(property).equals(value)) {
				estremiAttoRichiestoForm.setValue(property, value);
			}
		}
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
	
	public enum TipoProtocolloEnum {
	    PROTOCOLLO_GENERALE("PG", "Protocollo generale"),
	    PROTOCOLLO_DI_SETTORE("PS", "Protocollo di settore");

	    private String tipoProtocollo;
	    private String descrizioneProtocollo;

	    TipoProtocolloEnum(String tipoProtocollo, String descrizioneProtocollo) {
	        this.tipoProtocollo = tipoProtocollo;
	        this.descrizioneProtocollo = descrizioneProtocollo;
	    }

	    public String tipoProtocollo() {
	        return tipoProtocollo;
	    }
	    
	    public String descrizioneProtocollo() {
	        return descrizioneProtocollo;
	    }
	}
	
	public enum StatoEnum {
		VUOTO("", ""),
		NON_PERVENUTO_IN_CITTADELLA("non pervenuto in Cittadella", "non pervenuto in Cittadella"),
		PRESENTE_IN_CITTADELLA("presente in Cittadella", "presente in Cittadella"),
		PRELEVATO_E_ANCORA_DA_RESTITUIRE("prelevato e ancora da restituire", "prelevato e ancora da restituire");

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
	
	@Override
	public boolean validate() {
		getValuesManager().clearErrors(true);		
		return getValuesManager().validate();
	}
	
	protected boolean isStatoAttiToShow(){
		//boolean toShow = ((AttiRichiestiItem)getItem()).isStatoAttiInCanvasToShow() && statoAttoRichiestoForm != null && statoAttoRichiestoForm.getValueAsString("stato") != null && !"".equalsIgnoreCase(statoAttoRichiestoForm.getValueAsString("stato"));
		boolean toShow = ((AttiRichiestiItem)getItem()).isStatoAttiInCanvasToShow();
		if(statoAttoRichiestoForm != null) {
			statoAttoRichiestoForm.setVisible(toShow);			
		}
		if(responsabilePrelievoAttoForm != null) {
			responsabilePrelievoAttoForm.setVisible(toShow && isAttoDaRestituireInCittadella());
		}
		if(ufficioPrelievoAttoForm != null) {
			ufficioPrelievoAttoForm.setVisible(toShow && isAttoDaRestituireInCittadella());
		}
		return toShow;
	}
	
	protected boolean isElencoAttoEditable() {
		return ((AttiRichiestiItem)getItem()).isElencoAttiRichiestiInCanvasEditable();
	}
	
	protected boolean isEstremiAttoEditable() {
		return ((AttiRichiestiItem)getItem()).isEstremiAttiRichiestiInCanvasEditable();
	}
	
	protected boolean isStatoAttiEditable(){
		return ((AttiRichiestiItem)getItem()).isStatoAttiInCanvasEditable();
	}
	
	protected boolean isNoteUffRichiedenteToEnable(){
		return ((AttiRichiestiItem)getItem()).isNoteUffRichiedenteInCanvasToEnable();
	}
	protected boolean isNoteCittadellaToEnable(){
		return ((AttiRichiestiItem)getItem()).isNoteCittadellaInCanvasToEnable();
	}
	
	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		ufficioPrelievoAttoForm.setValue("organigrammaUfficio", tipo + idOrganigramma);
		ufficioPrelievoAttoForm.setValue("idUoUfficio", idOrganigramma);
		ufficioPrelievoAttoForm.setValue("typeNodoUfficio", tipo);
		ufficioPrelievoAttoForm.setValue("descrizioneUfficio", ""); // da settare
		ufficioPrelievoAttoForm.setValue("codRapidoUfficio", record.getAttribute("codRapidoUo"));
	}
	
	public class AttiRichiestiLookupOrganigramma extends LookupOrganigrammaPopup {

		public AttiRichiestiLookupOrganigramma(Record record) {
			super(record, true, 1);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String getFinalita() {
			return null;
		}

	}

}
