/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class GruppiSoggettiDetail extends CustomDetail { 

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;

	protected String rowid;
	protected LinkedHashMap<String, String> attributiAddTabs;
	protected HashMap<String, VLayout> attributiAddLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDetails;
	
	GruppiSoggettiDetail _instance;
	
	DynamicForm soggettiForm;
	DynamicForm datiPrincipaliForm;
	DynamicForm validitaForm;
	
	protected SoggettiGruppoItem soggettiItem;
	
	// HiddenItem 
	private HiddenItem 		idGruppoItem;				// ID gruppo
	
	// TextItem 
	private TextItem 		codiceRapidoItem;			// Codice rapido
	private TextItem 		nomeItem;					// denominazione
	
	// DateItem
	private DateItem 		dtValiditaDaItem;     		// Valido dal 
	private DateItem 		dtValiditaAItem;      		// Valido al		
	
	// CheckboxItem
	private CheckboxItem 	flgInibitaAssItem; // non utilizzabile in assegnazione/invio per conoscenza
	
	// DetailSection
	protected DetailSection soggettiSection;	
	protected DetailSection datiPrincipaliSection;
	protected DetailSection validitaSection;
	
	public GruppiSoggettiDetail(String nomeEntita) {		
		
		super(nomeEntita);
		
		_instance = this;		
				
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		createTabSet();

		setMembers(tabSet);
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
	}
	
	public VLayout getLayoutTabDatiPrincipali() {
		
        setInitValues();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
				
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		lVLayout.addMember(datiPrincipaliForm);
		lVLayout.addMember(validitaSection);	
		lVLayout.addMember(soggettiSection);	
		
		return lVLayout;
	}
	
	private void setInitValues() {

		//******************************************************************************************
    	// Sezione DATI PRINCIPALI
        //******************************************************************************************
		
		// Creo il form
        datiPrincipaliForm = new DynamicForm();
        datiPrincipaliForm.setValuesManager(vm);
        datiPrincipaliForm.setWidth("100%"); 
        datiPrincipaliForm.setHeight(10);  
        datiPrincipaliForm.setPadding(5);
        datiPrincipaliForm.setNumCols(6);
        datiPrincipaliForm.setColWidths(10 , 100,  10 , 100, 10 , "*");
        
        datiPrincipaliForm.setWrapItemTitles(false);

        // Creo gli item
        idGruppoItem     = new HiddenItem("idGruppo");
                
		codiceRapidoItem = new TextItem("codiceRapido", I18NUtil.getMessages().gruppisoggetti_detail_codiceRapidoItem_title());
		codiceRapidoItem.setWidth(120);	
		codiceRapidoItem.setStartRow(true);
		codiceRapidoItem.setEndRow(false);
	    
		nomeItem = new TextItem("nome", I18NUtil.getMessages().gruppisoggetti_detail_nomeItem_title());
        nomeItem.setRequired(true);
        nomeItem.setWidth(380);
        nomeItem.setStartRow(false);
        nomeItem.setEndRow(false);
       
		
        // Aggiungo gli item al form
        datiPrincipaliForm.setItems(idGruppoItem, codiceRapidoItem, nomeItem);
        
        
        //******************************************************************************************
    	// Sezione VALIDITA'
        //******************************************************************************************
		// Creo il form
        validitaForm = new DynamicForm();
        validitaForm.setValuesManager(vm);
        validitaForm.setWidth("100%");  
        validitaForm.setHeight(10); 
        validitaForm.setPadding(5);
        validitaForm.setNumCols(6);
        validitaForm.setColWidths(10 , 100,  10 , 100, 10 , "*");
        
        validitaForm.setWrapItemTitles(false);
        
        dtValiditaDaItem = new DateItem("dtValiditaDa", I18NUtil.getMessages().gruppisoggetti_detail_dtValiditaDaItem_title());
        dtValiditaDaItem.setStartRow(true);
        dtValiditaDaItem.setEndRow(false);		
        
        dtValiditaAItem = new DateItem("dtValiditaA", I18NUtil.getMessages().gruppisoggetti_detail_dtValiditaAItem_title());
        dtValiditaAItem.setStartRow(false);
        dtValiditaAItem.setEndRow(false);		
                
        flgInibitaAssItem = new CheckboxItem("flgInibitaAss", I18NUtil.getMessages().gruppisoggetti_detail_flgInibitaAssItem_title());
        flgInibitaAssItem.setValue(false);
        flgInibitaAssItem.setColSpan(5);
        flgInibitaAssItem.setWidth(10);
        flgInibitaAssItem.setStartRow(true);
        flgInibitaAssItem.setTitleOrientation(TitleOrientation.RIGHT);
        
        // Aggiungo gli item al form
        validitaForm.setItems(dtValiditaDaItem, dtValiditaAItem, flgInibitaAssItem);
        
        // Aggiungo il form alla section
        validitaSection    = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_validitaSection_title(), false, true, false, validitaForm);
        
        //******************************************************************************************
    	// Sezione SOGGETTI
    	//******************************************************************************************
        
        // Creo il form
        soggettiForm = new DynamicForm();
        soggettiForm.setValuesManager(vm);
        soggettiForm.setWidth("100%");
        soggettiForm.setHeight(10); 
        soggettiForm.setPadding(5);
        soggettiForm.setNumCols(8);
        soggettiForm.setWrapItemTitles(false);        
        
        // Creo gli item
        soggettiItem = new SoggettiGruppoItem();
        soggettiItem.setName("listaSoggettiGruppo");
        soggettiItem.setShowTitle(false);
        soggettiItem.setShowNewButton(true);
        
        
        // Aggiungo gli item al form
        soggettiForm.setFields(soggettiItem);
                        
        // Aggiungo il form alla section
		soggettiSection  = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_soggettiSection_title(), true, true, false, soggettiForm);			
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
		this.rowid = record.getAttribute("rowid");
		caricaAttributiDinamici(rowid);
	}

	
	public void caricaAttributiDinamici(final String rowid) {
		
		Record lRecordLoad = new Record();
		lRecordLoad.setAttribute("nomeTabella", "DMT_GRUPPI_SOGG_EST");
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
					lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_GRUPPI_SOGG_EST");
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
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);

		if (attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDetails.get(key);
				detail.setCanEdit(canEdit);
			}
		}
	}

	public String getTitleTabDatiPrincipali() {
		return "Dati principali";
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
	
	private void reloadComboFromRecord(Record record) {	
	}
	
}