/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.applicazioniEsterne;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import java.util.Map;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.Canvas;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Side;

/**
 * 
 * @author ottavio passalacqua
 *
 */
public class ApplicazioniEsterneDetail extends CustomDetail {

    // DynamicForm
	private DynamicForm formApplicazioniEsterne;
	
	// HiddenItem
	private HiddenItem idApplEsternaItem;
	
	// TextItem
	private TextItem codApplicazioneItem;
	private TextItem codIstanzaItem;
	private TextItem nomeItem;
	
	// CheckboxItem
	private CheckboxItem flgUsaCredenzialiDiverseItem;
	
	ApplicazioniEsterneDetail _instance;

	// Attributi custom
	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;

	protected String rowid;
	protected LinkedHashMap<String, String> attributiAddTabs;
	protected HashMap<String, VLayout> attributiAddLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDetails;

	public ApplicazioniEsterneDetail(String nomeEntita) {
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
	
			lVLayout.addMember(formApplicazioniEsterne);
			
			return lVLayout;
	}
		
	public String getTitleTabDatiPrincipali() {
			return "Dati principali";
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
		
	private void setInitValues() {
		   
		buildDatiPrincipaliSection();
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
	
	@Override
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);

		if (attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDetails.get(key);
				detail.setCanEdit(canEdit);
			}
		}
	}

	public void caricaAttributiDinamici(final String rowid) {
		Record lRecordLoad = new Record();
		lRecordLoad.setAttribute("nomeTabella", "DMT_APPLICAZIONI_ESTERNE");
		new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTabellaDataSource").call(lRecordLoad, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				final boolean isReload = (attributiAddTabs != null && attributiAddTabs.size() > 0);
				if(attributiAddLayouts != null) {
					for (String key : attributiAddLayouts.keySet()) {
						// se inizia con HEADER_ non devo cancellare il layout perchè è quello del tab principale
						if(key != null && !key.startsWith("HEADER_")) {
							try { attributiAddLayouts.get(key).destroy(); } catch(Exception e) {}
						}
					}
				}
				if(attributiAddDetails != null) {
					for (String key : attributiAddDetails.keySet()) {
						try { attributiAddDetails.get(key).destroy(); } catch(Exception e) {}				
					}
				}
				attributiAddTabs = (LinkedHashMap<String, String>) object.getAttributeAsMap("gruppiAttributiCustomTabella");
				attributiAddLayouts = new HashMap<String, VLayout>();
				attributiAddDetails = new HashMap<String, AttributiDinamiciDetail>();
				if (attributiAddTabs != null && attributiAddTabs.size() > 0) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");					
					Record lAttributiDinamiciRecord = new Record();
					lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_APPLICAZIONI_ESTERNE");
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

    // SEZIONE DATI PRINCIPALI
	private void buildDatiPrincipaliSection(){
		
		// FORM MAIN
		formApplicazioniEsterne = new DynamicForm();
		formApplicazioniEsterne.setValuesManager(vm);
		formApplicazioniEsterne.setHeight("5");
		formApplicazioniEsterne.setPadding(5);
		formApplicazioniEsterne.setWrapItemTitles(false);
		formApplicazioniEsterne.setNumCols(4);
		formApplicazioniEsterne.setColWidths("1", "1", "1", "*");
		
		// nascosti
		idApplEsternaItem = new HiddenItem("idApplEsterna");
		
		// Codice applicazione
		codApplicazioneItem = new TextItem("codApplicazione", I18NUtil.getMessages().applicazioni_esterne_codApplicazione_detail());    
		codApplicazioneItem.setLength(30);
		codApplicazioneItem.setRequired(true);
		codApplicazioneItem.setStartRow(true);
		
		// Codice istanza
		codIstanzaItem      = new TextItem("codIstanza",      I18NUtil.getMessages().applicazioni_esterne_codIstanza_detail());
		codIstanzaItem.setLength(30);
		
		// Nome applicazione
		nomeItem = new TextItem("nome", I18NUtil.getMessages().applicazioni_esterne_nome_detail());
		nomeItem.setColSpan(3);
		nomeItem.setLength(1000);
		nomeItem.setWidth(575);
		nomeItem.setRequired(true);
		nomeItem.setStartRow(true);
		
		flgUsaCredenzialiDiverseItem = new CheckboxItem("flgUsaCredenzialiDiverse", I18NUtil.getMessages().applicazioni_esterne_flgUsaCredenzialiDiverse_detail());
		flgUsaCredenzialiDiverseItem.setTitleOrientation(TitleOrientation.LEFT);
		flgUsaCredenzialiDiverseItem.setShowTitle(true);
		flgUsaCredenzialiDiverseItem.setLabelAsTitle(true);
		flgUsaCredenzialiDiverseItem.setShowLabel(false);
		flgUsaCredenzialiDiverseItem.setDefaultValue(false);
		flgUsaCredenzialiDiverseItem.setStartRow(true);
		
		
	    formApplicazioniEsterne.setItems(idApplEsternaItem,
	    		                         codApplicazioneItem,
										 codIstanzaItem,
		                                 nomeItem, 
		                                 flgUsaCredenzialiDiverseItem
										 );
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();		
		if(attributiAddLayouts != null) {
			for (String key : attributiAddLayouts.keySet()) {
				// se inizia con HEADER_ non devo cancellare il layout perchè è quello del tab principale
				if(key != null && !key.startsWith("HEADER_")) {
					try { attributiAddLayouts.get(key).destroy(); } catch(Exception e) {}
				}
			}
		}
		if(attributiAddDetails != null) {
			for (String key : attributiAddDetails.keySet()) {
				try { attributiAddDetails.get(key).destroy(); } catch(Exception e) {}				
			}
		}
		attributiAddTabs = null;
		attributiAddLayouts = null;		
		attributiAddDetails = null;
	}
	
}
