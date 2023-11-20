/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.defattivitaprocedimenti.AttributiAddXEventiDelTipoItem;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author cristiano
 *
 */
public class TipologieDocumentaliDetail extends CustomDetail {

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;

	protected String rowid;
	protected LinkedHashMap<String, String> attributiAddTabs;
	protected HashMap<String, VLayout> attributiAddLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDetails;

	
	protected DynamicForm formTipologiaDocumentale;
	private HiddenItem idTipoDocItem;
	private TextItem nomeItem;
	private TextAreaItem descrizioneItem;
	private FilteredSelectItem idDocTypeGenItem;
	private HiddenItem nomeDocTypeGenItem;
	private FilteredSelectItem idProcessTypeItem;
	private HiddenItem nomeProcessTypeItem;
	
	private CheckboxItem flgConservPermInItem;
	private NumericItem periodoConservInItem;
	protected DynamicForm formAbilitazioni;
	private CheckboxItem flgRichAbilVisItem;
	private CheckboxItem flgRichAbilXGestInItem;
	private CheckboxItem flgRichAbilXAssegnInItem;
	private CheckboxItem flgAbilFirmaItem;

	protected DetailSection detailSectionTemplateTimbro;
	protected DynamicForm formTemplate;
	private TextItem templateNomeUDItem;
	private TextItem templateTimbroUDItem;
	
	protected DetailSection detailSectionAttributiCustom;
	protected DynamicForm formMetadatiSpecifici;
	protected AttributiAddXEventiDelTipoItem defAttivitaProcedimentiItem;
	private SelectItem flgTipoProvItem;
			
	protected DynamicForm formUoGpPrivAbilitatiPubblicazione;
	protected Tab tabUoGpPrivAbilitatiPubblicazione;
	protected VLayout layoutTabUoGpPrivAbilitatiPubblicazione;
	protected ListaUoGpPrivAbilitatiPubblicazioneItem listaUoGpPrivAbilitatiPubblicazioneItem;
	
	protected RadioGroupItem flgAllegatoItem;
	protected RadioGroupItem flgRichiestaFirmaDigitaleItem;
	private HiddenItem flgIsAssociataIterWf;
	
	public TipologieDocumentaliDetail(String nomeEntita) {
		
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		createTabSet();

		setMembers(tabSet);
	}

	private void setInitValues() {

		formTipologiaDocumentale = new DynamicForm();
		formTipologiaDocumentale.setValuesManager(vm);
		formTipologiaDocumentale.setHeight("5");
		formTipologiaDocumentale.setPadding(5);
		formTipologiaDocumentale.setWrapItemTitles(false);
		formTipologiaDocumentale.setNumCols(15);
		formTipologiaDocumentale.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		idTipoDocItem = new HiddenItem("idTipoDoc");
		flgIsAssociataIterWf = new HiddenItem("flgIsAssociataIterWf");

		nomeItem = new TextItem("nome", I18NUtil.getMessages().tipologieDocumentali_detail_nome());
		nomeItem.setWidth(350);
		nomeItem.setRequired(true);
		nomeItem.setStartRow(true);
		nomeItem.setColSpan(1);

		descrizioneItem = new TextAreaItem("descrizione", I18NUtil.getMessages().tipologieDocumentali_detail_descrizione());
		descrizioneItem.setStartRow(true);
		descrizioneItem.setLength(4000);
		descrizioneItem.setHeight(60);
		descrizioneItem.setWidth(650);
		descrizioneItem.setColSpan(14);
		
		// flag tipologia allegato
		LinkedHashMap<String, String> flgAllegatoMap = new LinkedHashMap<String, String>();  
		flgAllegatoMap.put("0", "No");  
		flgAllegatoMap.put("1", "SI, possibile allegato");
		flgAllegatoMap.put("2", "SI, solo allegato");
		
		flgAllegatoItem = new RadioGroupItem("flgAllegato", I18NUtil.getMessages().tipologieDocumentali_detail_flgAllegato());
		flgAllegatoItem.setStartRow(true);
		flgAllegatoItem.setColSpan(14);
		flgAllegatoItem.setVertical(false);
		flgAllegatoItem.setWrap(false);
		flgAllegatoItem.setShowTitle(true);
		flgAllegatoItem.setTitleOrientation(TitleOrientation.LEFT);
		flgAllegatoItem.setDefaultValue("0");
		flgAllegatoItem.setValueMap(flgAllegatoMap); 
		
		// flag richiesta firma digitale
		LinkedHashMap<String, String> flgRichiestaFirmaDigitaleMap = new LinkedHashMap<String, String>();  
		flgRichiestaFirmaDigitaleMap.put("0", "NO");
		flgRichiestaFirmaDigitaleMap.put("V", "SI, valida alla data di registrazione a protocollo/repertorio");  
		flgRichiestaFirmaDigitaleMap.put("P", "SI, anche se NON valida alla data di registrazione a protocollo/repertorio");
		
		flgRichiestaFirmaDigitaleItem = new RadioGroupItem("flgRichFirmaDigitale", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichFirmaDigitale());
		flgRichiestaFirmaDigitaleItem.setStartRow(true);
		flgRichiestaFirmaDigitaleItem.setColSpan(14);
		flgRichiestaFirmaDigitaleItem.setVertical(false);
		flgRichiestaFirmaDigitaleItem.setWrap(false);
		flgRichiestaFirmaDigitaleItem.setShowTitle(true);
		flgRichiestaFirmaDigitaleItem.setTitleOrientation(TitleOrientation.LEFT);
		flgRichiestaFirmaDigitaleItem.setDefaultValue("0");
		flgRichiestaFirmaDigitaleItem.setValueMap(flgRichiestaFirmaDigitaleMap); 

		nomeDocTypeGenItem = new HiddenItem("nomeDocTypeGen");

		GWTRestDataSource docTypesGenDS = new GWTRestDataSource("LoadComboTipiDocDataSource", "idDocumentoTypePadre", FieldType.TEXT, true);
		ListGridField idDocumentoTypePadreField = new ListGridField("idDocumentoTypePadre", "Id.");
		idDocumentoTypePadreField.setHidden(true);
		ListGridField nomeDocumentoTypePadreField = new ListGridField("nomeDocumentoTypePadre", "Nome");
		idDocTypeGenItem = new FilteredSelectItem("idDocTypeGen", I18NUtil.getMessages().tipologieDocumentali_detail_idDocTypeGen()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				formTipologiaDocumentale.setValue("nomeDocTypeGen", record.getAttribute("nomeDocumentoTypePadre"));
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				formTipologiaDocumentale.setValue("nomeDocTypeGen", "");	
				markForRedraw();
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					formTipologiaDocumentale.setValue("nomeDocTypeGen", "");
				}
				markForRedraw();
            }

		};
		idDocTypeGenItem.setPickListFields(idDocumentoTypePadreField, nomeDocumentoTypePadreField);
		idDocTypeGenItem.setWidth(450);
		idDocTypeGenItem.setStartRow(true);
		idDocTypeGenItem.setClearable(true);
		idDocTypeGenItem.setValueField("idDocumentoTypePadre");
		idDocTypeGenItem.setDisplayField("nomeDocumentoTypePadre");
		idDocTypeGenItem.setColSpan(14);
		idDocTypeGenItem.setAllowEmptyValue(true);
		idDocTypeGenItem.setOptionDataSource(docTypesGenDS);
		idDocTypeGenItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		nomeProcessTypeItem = new HiddenItem("nomeProcessType");
		
		GWTRestDataSource processTypesDS = new GWTRestDataSource("LoadComboTipiProcDataSource", "idProcessType", FieldType.TEXT, true);
		ListGridField keyField = new ListGridField("key", "Id.");
		keyField.setHidden(true);
		ListGridField valueField = new ListGridField("value", "Nome");
		idProcessTypeItem = new FilteredSelectItem("idProcessType", I18NUtil.getMessages().tipologieDocumentali_detail_idProcessType()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				formTipologiaDocumentale.setValue("nomeProcessType", record.getAttribute("value"));
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				formTipologiaDocumentale.setValue("nomeProcessType", "");
				markForRedraw();
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					formTipologiaDocumentale.setValue("nomeProcessType", "");
				}
				markForRedraw();
            }

		};
		idProcessTypeItem.setPickListFields(keyField, valueField);
		idProcessTypeItem.setWidth(450);
		idProcessTypeItem.setStartRow(true);
		idProcessTypeItem.setClearable(true);
		idProcessTypeItem.setValueField("key");
		idProcessTypeItem.setDisplayField("value");
		idProcessTypeItem.setColSpan(14);
		idProcessTypeItem.setAllowEmptyValue(true);
		idProcessTypeItem.setOptionDataSource(processTypesDS);
		idProcessTypeItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});	

		
		// Indica se in genere i documenti del dato tipo sono in entrata (E), interni (I) o in uscita (U) dal soggetto produttore / AOO -- (valori E/I/U)		
		flgTipoProvItem = new SelectItem("flgTipoProv", I18NUtil.getMessages().tipologieDocumentali_detail_flgTipoProv());
		LinkedHashMap<String, String> flgTipoProvValueMap = new LinkedHashMap<String, String>();
		flgTipoProvValueMap.put("E", "Entrata");
		flgTipoProvValueMap.put("I", "Interni");
		flgTipoProvValueMap.put("U", "Uscita");		
		flgTipoProvItem.setValueMap(flgTipoProvValueMap);
		flgTipoProvItem.setColSpan(1);
		flgTipoProvItem.setStartRow(true);
		flgTipoProvItem.setAllowEmptyValue(true);
		flgTipoProvItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		formTipologiaDocumentale.setItems(idTipoDocItem, nomeItem, descrizioneItem, flgAllegatoItem, idDocTypeGenItem, nomeDocTypeGenItem, idProcessTypeItem, flgTipoProvItem, flgRichiestaFirmaDigitaleItem, nomeProcessTypeItem, flgIsAssociataIterWf);
	}
	
	private void setTemplateValues() {
		
		formTemplate = new DynamicForm();
		formTemplate.setValuesManager(vm);
		formTemplate.setWidth("100%");
		formTemplate.setHeight("5");
		formTemplate.setPadding(5);
		formTemplate.setWrapItemTitles(false);
		formTemplate.setNumCols(12);
		formTemplate.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		templateNomeUDItem = new TextItem("templateNomeUD", I18NUtil.getMessages().tipologieDocumentali_detail_templateNome());
		templateNomeUDItem.setStartRow(true);
		templateNomeUDItem.setWidth(450);
		templateNomeUDItem.setColSpan(1);
		
		templateTimbroUDItem = new TextItem("templateTimbroUD", I18NUtil.getMessages().tipologieDocumentali_detail_templateTimbroUD());
		templateTimbroUDItem.setStartRow(true);
		templateTimbroUDItem.setWidth(450);
		templateTimbroUDItem.setColSpan(1);

		formTemplate.setItems(templateNomeUDItem, templateTimbroUDItem);
		
		detailSectionTemplateTimbro = new DetailSection(I18NUtil.getMessages().tipologieDocumentali_detail_section_template(), true, true, false, formTemplate);
	}

	private void setAbilValues() {

		formAbilitazioni = new DynamicForm();
		formAbilitazioni.setValuesManager(vm);
		formAbilitazioni.setHeight("5");
		formAbilitazioni.setPadding(5);
		formAbilitazioni.setWrapItemTitles(false);
		formAbilitazioni.setNumCols(15);
		formAbilitazioni.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		SpacerItem spacer = new SpacerItem();
		spacer.setColSpan(2);

		flgRichAbilVisItem = new CheckboxItem("flgRichAbilVis", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichAbilVis());
		flgRichAbilVisItem.setColSpan(1);
		flgRichAbilVisItem.setWidth("*");

		flgRichAbilXGestInItem = new CheckboxItem("flgRichAbilXGestIn", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichAbilXGestIn());
		flgRichAbilXGestInItem.setWidth("*");
		flgRichAbilXGestInItem.setColSpan(1);

		flgRichAbilXAssegnInItem = new CheckboxItem("flgRichAbilXAssegnIn", I18NUtil.getMessages().tipologieDocumentali_detail_flgRichAbilXAssegnIn());
		flgRichAbilXAssegnInItem.setWidth("*");
		flgRichAbilXAssegnInItem.setColSpan(1);

		flgAbilFirmaItem = new CheckboxItem("flgAbilFirma", I18NUtil.getMessages().tipologieDocumentali_detail_flgAbilFirma());
		flgAbilFirmaItem.setWidth("*");
		flgAbilFirmaItem.setColSpan(1);

		flgConservPermInItem = new CheckboxItem("flgConservPermIn", I18NUtil.getMessages().tipologieDocumentali_detail_flgConservPermIn());
		flgConservPermInItem.setColSpan(1);
		flgConservPermInItem.setWidth("*");
		flgConservPermInItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				formAbilitazioni.redraw();
			}
		});

		periodoConservInItem = new NumericItem("periodoConservAnni", I18NUtil.getMessages().tipologieDocumentali_detail_periodoConservIn());
		periodoConservInItem.setColSpan(1);
		periodoConservInItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(flgConservPermInItem.getValue() != null && (Boolean)flgConservPermInItem.getValue() ? true : false);
			}
		});
		
		formAbilitazioni.setItems(spacer, flgRichAbilVisItem, flgRichAbilXGestInItem, flgRichAbilXAssegnInItem, flgAbilFirmaItem, flgConservPermInItem, periodoConservInItem);
	}

	private void setAttrCustValues() {

		formMetadatiSpecifici = new DynamicForm();
		formMetadatiSpecifici.setValuesManager(vm);
		formMetadatiSpecifici.setWidth("100%");
		formMetadatiSpecifici.setHeight("5");
		formMetadatiSpecifici.setPadding(5);
		formMetadatiSpecifici.setWrapItemTitles(false);
		formMetadatiSpecifici.setNumCols(12);
		formMetadatiSpecifici.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		defAttivitaProcedimentiItem = new AttributiAddXEventiDelTipoItem() {
			@Override
			public String getFlgIsAssociataIterWf() {
				return "" + flgIsAssociataIterWf.getValue();
			}
		};
		defAttivitaProcedimentiItem.setName("listaAttributiAddizionali");
		defAttivitaProcedimentiItem.setShowTitle(false);
		defAttivitaProcedimentiItem.setOrdinabile(true);

		formMetadatiSpecifici.setFields(defAttivitaProcedimentiItem);
		
		detailSectionAttributiCustom = new DetailSection(I18NUtil.getMessages().tipologieDocumentali_detail_section_attr_custom(), true, true, false, formMetadatiSpecifici);
		
	}

	private void reloadComboFromRecord(Record record) {
		
		GWTRestDataSource docTypesGenDS = (GWTRestDataSource) idDocTypeGenItem.getOptionDataSource();
		if (record.getAttribute("idTipoDoc") != null && !"".equals(record.getAttribute("idTipoDoc"))) {
			docTypesGenDS.addParam("idTipoDoc", (String) record.getAttribute("idTipoDoc"));
		} else {
			docTypesGenDS.addParam("idTipoDoc", null);
		}
		if (record.getAttribute("idDocTypeGen") != null && !"".equals(record.getAttributeAsString("idDocTypeGen"))) {
			docTypesGenDS.addParam("idDocTypeGen", record.getAttributeAsString("idDocTypeGen"));
		} else {
			docTypesGenDS.addParam("idDocTypeGen", null);
		}
		idDocTypeGenItem.setOptionDataSource(docTypesGenDS);
		idDocTypeGenItem.fetchData();
		
		GWTRestDataSource processTypesDS = (GWTRestDataSource) idProcessTypeItem.getOptionDataSource();		
		if (record.getAttribute("idProcessType") != null && !"".equals(record.getAttribute("idProcessType"))) {
			processTypesDS.addParam("idProcessType", (String) record.getAttribute("idProcessType"));
		} else {
			processTypesDS.addParam("idProcessType", null);
		}
		idProcessTypeItem.setOptionDataSource(processTypesDS);
		idProcessTypeItem.fetchData();
	}

	
	protected void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);

		/**
		 *  Tab Dati principali
		 */
		tabDatiPrincipali = new Tab("<b>" + getTitleTabDatiPrincipali() + "</b>");
		tabDatiPrincipali.setAttribute("tabID", "HEADER");
		tabDatiPrincipali.setPrompt(getTitleTabDatiPrincipali());

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		layoutTabDatiPrincipali = createLayoutTab(getLayoutTabDatiPrincipali(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabDatiPrincipali.setPane(layoutTabDatiPrincipali);

		tabSet.addTab(tabDatiPrincipali);
		
		/**
		 *  Tab Abilitati alla pubblicazione
		 */
		tabUoGpPrivAbilitatiPubblicazione = new Tab("<b>" + getTitleTabUoGpPrivAbilitatiPubblicazione() + "</b>");
		tabUoGpPrivAbilitatiPubblicazione.setAttribute("tabID", "HEADER");
		tabUoGpPrivAbilitatiPubblicazione.setPrompt(getTitleTabUoGpPrivAbilitatiPubblicazione());

		layoutTabUoGpPrivAbilitatiPubblicazione = createLayoutTab(getLayoutTabUoGpPrivAbilitatiPubblicazione(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabUoGpPrivAbilitatiPubblicazione.setPane(layoutTabUoGpPrivAbilitatiPubblicazione);
		
		if (TipologieDocumentaliLayout.isAbilTipologieDocPubblicabili()) {
			tabSet.addTab(tabUoGpPrivAbilitatiPubblicazione);
		}
	}
	
	
	public String getTitleTabUoGpPrivAbilitatiPubblicazione() {
		return "Abilitati alla pubblicazione";
	}
	
	
	public VLayout getLayoutTabUoGpPrivAbilitatiPubblicazione() {
		
		formUoGpPrivAbilitatiPubblicazione = new DynamicForm();
		formUoGpPrivAbilitatiPubblicazione.setValuesManager(vm);
		formUoGpPrivAbilitatiPubblicazione.setWidth("100%");
		formUoGpPrivAbilitatiPubblicazione.setHeight("100%");
		formUoGpPrivAbilitatiPubblicazione.setPadding(5);
		formUoGpPrivAbilitatiPubblicazione.setWrapItemTitles(false);
		formUoGpPrivAbilitatiPubblicazione.setNumCols(12);
		formUoGpPrivAbilitatiPubblicazione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		formUoGpPrivAbilitatiPubblicazione.setValuesManager(vm);

		listaUoGpPrivAbilitatiPubblicazioneItem = new ListaUoGpPrivAbilitatiPubblicazioneItem("listaUoGpPrivAbilitatiPubblicazione") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
			
		};
		
		listaUoGpPrivAbilitatiPubblicazioneItem.setStartRow(true);
		listaUoGpPrivAbilitatiPubblicazioneItem.setShowTitle(false);
		listaUoGpPrivAbilitatiPubblicazioneItem.setHeight(245);
		formUoGpPrivAbilitatiPubblicazione.setItems(listaUoGpPrivAbilitatiPubblicazioneItem);
				
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formUoGpPrivAbilitatiPubblicazione);
				
		return lVLayout;
	}
	
	public String getTitleTabDatiPrincipali() {
		return "Dati principali";
	}
	
	public VLayout getLayoutTabDatiPrincipali() {
	
        setInitValues();
		
		setTemplateValues();

		setAbilValues();
		
		setAttrCustValues();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
				
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);


		lVLayout.addMember(formTipologiaDocumentale);
		lVLayout.addMember(formAbilitazioni);
		lVLayout.addMember(detailSectionTemplateTimbro);
		lVLayout.addMember(detailSectionAttributiCustom);

		return lVLayout;
	}
	
	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
	
	public List<DynamicForm> getAllDetailForms() {
		
		List<DynamicForm> allDetailForms = super.getAllDetailForms();
		if (attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDetails.get(key);
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
		
		if (attributiAddTabs != null) {
			for (String key : attributiAddTabs.keySet()) {
				if (attributiAddDetails.get(key) != null) {
					attributiAddDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	

	public boolean customValidate() {
		
		boolean valid = super.customValidate();		
		
		if (attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDetails.get(key);
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
	
	public Record getRecordToSave() {
		
		final Record lRecordToSave = new Record(vm.getValues());
		
		if (attributiAddDetails != null) {
			lRecordToSave.setAttribute("rowid", rowid);
			lRecordToSave.setAttribute("valori", getAttributiDinamici());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamici());
		}
		return lRecordToSave;
	}
	
    public void caricaAttributiDinamici(final String rowid) {
		
		Record lRecordLoad = new Record();
		lRecordLoad.setAttribute("nomeTabella", "DMT_DOC_TYPES");
		new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTabellaDataSource").call(lRecordLoad, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				final boolean isReload = (attributiAddTabs != null && attributiAddTabs.size() > 0);
				attributiAddTabs = (LinkedHashMap<String, String>) object.getAttributeAsMap("gruppiAttributiCustomTabella");
				attributiAddLayouts = new HashMap<String, VLayout>();
				attributiAddDetails = new HashMap<String, AttributiDinamiciDetail>();
				if (attributiAddTabs != null && attributiAddTabs.size() > 0) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");					
					Record lAttributiDinamiciRecord = new Record();
					lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOC_TYPES");
					lAttributiDinamiciRecord.setAttribute("rowId", rowid);
					lAttributiDinamiciRecord.setAttribute("tipoEntita", (String) null);
					lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
							if (attributiAdd != null && !attributiAdd.isEmpty()) {
								for (final String key : attributiAddTabs.keySet()) {
									RecordList attributiAddCategoria = new RecordList();
									for (int i = 0; i < attributiAdd.getLength(); i++) {
										Record attr = attributiAdd.get(i);
										if (attr.getAttribute("categoria") != null
												&& (attr.getAttribute("categoria").equalsIgnoreCase(key) || ("HEADER_" + attr.getAttribute("categoria"))
														.equalsIgnoreCase(key))) {
											attributiAddCategoria.add(attr);
										}
									}
									if (!attributiAddCategoria.isEmpty()) {
										if(key.equals("#HIDDEN")) {
											// Gli attributi che fanno parte di questo gruppo non li considero
										} else if (key.startsWith("HEADER_")) {
											AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
													.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
													.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
													tabSet, "HEADER");
											detail.setCanEdit(new Boolean(editing));
											attributiAddDetails.put(key, detail);
											VLayout layout = (VLayout) layoutTabDatiPrincipali.getMembers()[0];
											attributiAddLayouts.put(key, layout);
											int pos = 0;
											for (Canvas member : layout.getMembers()) {
												if (member instanceof HeaderDetailSection) {
													pos++;
												} else {
													break;
												}
											}												
											for (DetailSection detailSection : attributiAddDetails.get(key).getDetailSections()) {
												if (isReload) {
													((DetailSection) layout.getMember(pos++)).setForms(detailSection.getForms());
												} else {
													layout.addMember(detailSection, pos++);
												}
											}
										} else {
											AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
													.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
													.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
													tabSet, key);
											detail.setCanEdit(new Boolean(editing));
											attributiAddDetails.put(key, detail);
											VLayout layout = new VLayout();
											layout.setHeight100();
											layout.setWidth100();
											layout.setMembers(detail);
											attributiAddLayouts.put(key, layout);
											VLayout layoutTab = new VLayout();
											layoutTab.addMember(layout);
											if (tabSet.getTabWithID(key) != null) {
												tabSet.getTabWithID(key).setPane(layoutTab);
											} else {
												Tab tab = new Tab("<b>" + attributiAddTabs.get(key) + "</b>");
												tab.setAttribute("tabID", key);
												tab.setPrompt(attributiAddTabs.get(key));
												tab.setPane(layoutTab);
												tabSet.addTab(tab);
											}
										}
									}
								}
							}
						}
					});
				}
			}
		});
	}

    @Override
	public void editNewRecord() {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editNewRecord();
		caricaAttributiDinamici(null);
	}
    
    @Override
	public void editNewRecord(Map initialValues) {
    	vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editNewRecord(initialValues);
		reloadComboFromRecord(new Record(initialValues));
		caricaAttributiDinamici(null);
	}
    
    @Override
	public void editRecord(Record record) {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editRecord(record);
		reloadComboFromRecord(record);
		
		if (record.getAttribute("idDocTypeGen") != null && !"".equals(record.getAttributeAsString("idDocTypeGen"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idDocTypeGen"), record.getAttribute("nomeDocTypeGen"));
			idDocTypeGenItem.setValueMap(valueMap);
		}
		if (record.getAttribute("idProcessType") != null && !"".equals(record.getAttributeAsString("idProcessType"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idProcessType"), record.getAttribute("nomeProcessType"));
			idProcessTypeItem.setValueMap(valueMap);
		}
		this.rowid = record.getAttribute("rowid");
		caricaAttributiDinamici(rowid);
	}
    
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);

		if (attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDetails.get(key);
				detail.setCanEdit(canEdit);
			}
		}
	}

	public Map<String, Object> getAttributiDinamici() {
		
		Map<String, Object> attributiDinamici = null;
		
		if (attributiAddTabs != null) {
			for (String key : attributiAddTabs.keySet()) {
				if (attributiAddDetails.get(key) != null) {
					if (attributiDinamici == null) {
						attributiDinamici = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDetails.get(key).getRecordToSave();
					attributiDinamici.putAll(attributiAddDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamici;
	}

	public Map<String, String> getTipiAttributiDinamici() {
		
		Map<String, String> tipiAttributiDinamici = null;
		
		if (attributiAddTabs != null) {
			for (String key : attributiAddTabs.keySet()) {
				if (attributiAddDetails.get(key) != null) {
					if (tipiAttributiDinamici == null) {
						tipiAttributiDinamici = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDetails.get(key).getRecordToSave();
					tipiAttributiDinamici.putAll(attributiAddDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamici;
	}
}