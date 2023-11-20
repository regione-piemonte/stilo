/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.defattivitaprocedimenti.AttributiAddXEventiDelTipoItem;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.titolario.LookupTitolarioPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author cristiano
 *
 */

public class TipoFascicoliAggregatiDetail extends CustomDetail {

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;

	protected String rowid;
	protected LinkedHashMap<String, String> attributiAddTabs;
	protected HashMap<String, VLayout> attributiAddLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDetails;
	
	protected DynamicForm formTipologiaFascicoliAggregati;
	private HiddenItem idFolderTypeItem;
	private TextItem nomeItem;
	private TextAreaItem annotazioniItem;
	private FilteredSelectItem idFolderTypeGenItem;
	private HiddenItem nomeFolderTypeGenItem;
	private FilteredSelectItem idProcessTypeItem;
	private HiddenItem nomeProcessTypeItem;
	
	protected DetailSection detailSectionTemplateTimbro;
	protected DynamicForm formTemplate;
	private TextItem templateNomeItem;
	private TextItem templateTimbroItem;
	private TextItem templateCodiceItem;

	private CheckboxItem flgConservPermItem;
	private NumericItem periodoConservInAnniItem;

	protected DynamicForm formAbilitazioni;
	private CheckboxItem flgRichAbilXVisualizzItem;
	private CheckboxItem flgRichAbilXGestItem;
	private CheckboxItem flgRichAbilXAssegnItem;

	protected DetailSection detailSectionClassificazione;
	protected DynamicForm formClassificazione;
	private TextItem livelliClassificazioneItem;
	private HiddenItem desClassificazioneItem;
	private FilteredSelectItemWithDisplay idClassificazioneItem;
	private ImgButtonItem lookupTitolarioButton;

	protected DetailSection detailSectionAttributiCustom;
	protected DynamicForm formMetadatiSpecifici;
	protected AttributiAddXEventiDelTipoItem defAttivitaProcedimentiItem;

	public TipoFascicoliAggregatiDetail(String nomeEntita) {

		super(nomeEntita);

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		createTabSet();

		setMembers(tabSet);
	}

	private void setInitValues() {

		formTipologiaFascicoliAggregati = new DynamicForm();
		formTipologiaFascicoliAggregati.setValuesManager(vm);
		formTipologiaFascicoliAggregati.setHeight("5");
		formTipologiaFascicoliAggregati.setPadding(5);
		formTipologiaFascicoliAggregati.setWrapItemTitles(false);
		formTipologiaFascicoliAggregati.setNumCols(15);
		formTipologiaFascicoliAggregati.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		idFolderTypeItem = new HiddenItem("idFolderType");

		nomeItem = new TextItem("nome", I18NUtil.getMessages().tipofascicoliaggr_detail_nome());
		nomeItem.setWidth(350);
		nomeItem.setRequired(true);
		nomeItem.setStartRow(true);
		nomeItem.setColSpan(14);

		annotazioniItem = new TextAreaItem("annotazioni", I18NUtil.getMessages().tipofascicoliaggr_detail_annotazioni());
		annotazioniItem.setStartRow(true);
		annotazioniItem.setLength(4000);
		annotazioniItem.setHeight(60);
		annotazioniItem.setWidth(650);
		annotazioniItem.setColSpan(14);

		nomeFolderTypeGenItem = new HiddenItem("nomeFolderTypeGen");

		GWTRestDataSource folderTypesGenDS = new GWTRestDataSource("LoadComboTipiFascAggrDataSource", "idFascAggrTypePadre", FieldType.TEXT, true);
		ListGridField idFascAggrTypePadreField = new ListGridField("idFascAggrTypePadre", "Id.");
		idFascAggrTypePadreField.setHidden(true);
		ListGridField nomeFascAggrTypePadreField = new ListGridField("nomeFascAggrTypePadre", "Nome");
		idFolderTypeGenItem = new FilteredSelectItem("idFolderTypeGen", I18NUtil.getMessages().tipofascicoliaggr_detail_idFolderTypeGen()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				formTipologiaFascicoliAggregati.setValue("nomeFolderTypeGen", record.getAttribute("nomeFascAggrTypePadre"));
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				formTipologiaFascicoliAggregati.setValue("nomeFolderTypeGen", "");
				markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					formTipologiaFascicoliAggregati.setValue("nomeFolderTypeGen", "");
				}
				markForRedraw();
            }

		};
		idFolderTypeGenItem.setPickListFields(idFascAggrTypePadreField, nomeFascAggrTypePadreField);
		idFolderTypeGenItem.setWidth(450);
		idFolderTypeGenItem.setStartRow(true);
		idFolderTypeGenItem.setClearable(true);
		idFolderTypeGenItem.setValueField("idFascAggrTypePadre");
		idFolderTypeGenItem.setDisplayField("nomeFascAggrTypePadre");
		idFolderTypeGenItem.setColSpan(14);
		idFolderTypeGenItem.setAllowEmptyValue(true);
		idFolderTypeGenItem.setOptionDataSource(folderTypesGenDS);
		idFolderTypeGenItem.addChangedHandler(new ChangedHandler() {

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
		idProcessTypeItem = new FilteredSelectItem("idProcessType", I18NUtil.getMessages().tipofascicoliaggr_detail_idProcessType()) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				formTipologiaFascicoliAggregati.setValue("nomeProcessType", record.getAttribute("value"));
				markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				formTipologiaFascicoliAggregati.setValue("nomeProcessType", "");
				markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					formTipologiaFascicoliAggregati.setValue("nomeProcessType", "");
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

		formTipologiaFascicoliAggregati.setItems(idFolderTypeItem, nomeItem, annotazioniItem, idFolderTypeGenItem, nomeFolderTypeGenItem, idProcessTypeItem, nomeProcessTypeItem);
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

		templateNomeItem = new TextItem("templateNome", I18NUtil.getMessages().tipofascicoliaggr_detail_template_nome());
		templateNomeItem.setStartRow(true);
		templateNomeItem.setWidth(450);
		templateNomeItem.setColSpan(1);

		templateTimbroItem = new TextItem("templateTimbro", I18NUtil.getMessages().tipofascicoliaggr_detail_template_timbro());
		templateTimbroItem.setWidth(450);
		templateTimbroItem.setColSpan(1);
		templateTimbroItem.setStartRow(true);

		templateCodiceItem = new TextItem("templateCode", I18NUtil.getMessages().tipofascicoliaggr_detail_template_codid());
		templateCodiceItem.setWidth(450);
		templateCodiceItem.setStartRow(true);
		templateCodiceItem.setColSpan(1);

		formTemplate.setItems(templateNomeItem, templateTimbroItem, templateCodiceItem);
		
		detailSectionTemplateTimbro = new DetailSection("Template", true, true, false, formTemplate);

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

		flgRichAbilXVisualizzItem = new CheckboxItem("flgRichAbilXVisualizz", I18NUtil.getMessages().tipofascicoliaggr_detail_flgRichAbilXVisualizz());
		flgRichAbilXVisualizzItem.setColSpan(1);
		flgRichAbilXVisualizzItem.setWidth("*");

		flgRichAbilXGestItem = new CheckboxItem("flgRichAbilXGest", I18NUtil.getMessages().tipofascicoliaggr_detail_flgRichAbilXGest());
		flgRichAbilXGestItem.setWidth("*");
		flgRichAbilXGestItem.setColSpan(1);

		flgRichAbilXAssegnItem = new CheckboxItem("flgRichAbilXAssegn", I18NUtil.getMessages().tipofascicoliaggr_detail_flgRichAbilXAssegn());
		flgRichAbilXAssegnItem.setWidth("*");
		flgRichAbilXAssegnItem.setColSpan(1);

		LinkedHashMap<String, Boolean> flgValueMap = new LinkedHashMap<String, Boolean>();
		flgValueMap.put("1", true);
		flgValueMap.put("0", false);
		flgValueMap.put("", false);
		flgValueMap.put(null, false);

		flgConservPermItem = new CheckboxItem("flgConservPerm", I18NUtil.getMessages().tipofascicoliaggr_detail_flgConservPerm());
		flgConservPermItem.setValueMap(flgValueMap);
		flgConservPermItem.setColSpan(1);
		flgConservPermItem.setWidth("*");
		flgConservPermItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				formAbilitazioni.redraw();
			}
		});

		periodoConservInAnniItem = new NumericItem("periodoConservInAnni", I18NUtil.getMessages().tipofascicoliaggr_detail_periodoConservInAnni());
		periodoConservInAnniItem.setColSpan(1);
		periodoConservInAnniItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				Boolean flgConservIllimitata = formAbilitazioni.getValue("flgConservPerm") != null && (Boolean) formAbilitazioni.getValue("flgConservPerm");
				return flgConservIllimitata == null || !flgConservIllimitata;
			}
		});

		formAbilitazioni.setItems(spacer, flgRichAbilXVisualizzItem, flgRichAbilXGestItem, flgRichAbilXAssegnItem, flgConservPermItem, periodoConservInAnniItem);
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

		defAttivitaProcedimentiItem = new AttributiAddXEventiDelTipoItem();
		defAttivitaProcedimentiItem.setName("listaAttributiAddizionali");
		defAttivitaProcedimentiItem.setShowTitle(false);
		defAttivitaProcedimentiItem.setOrdinabile(true);

		formMetadatiSpecifici.setFields(defAttivitaProcedimentiItem);
		
		detailSectionAttributiCustom = new DetailSection(I18NUtil.getMessages().tipofascicoliaggr_detail_section_attr_custom(), true, true, false, formMetadatiSpecifici);
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
	
	public String getTitleTabDatiPrincipali() {
		return "Dati principali";
	}
	
	
	public VLayout getLayoutTabDatiPrincipali() {

		setInitValues();

		setTemplateValues();
		
		setAbilValues();

		setClassificazione();

		setAttrCustValues();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);
		
		lVLayout.addMember(formTipologiaFascicoliAggregati);
		lVLayout.addMember(formAbilitazioni);
		lVLayout.addMember(detailSectionClassificazione);
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
		lRecordLoad.setAttribute("nomeTabella", "DMT_FOLDER_TYPES");
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
					lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_FOLDER_TYPES");
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


	private void setClassificazione() {

		formClassificazione = new DynamicForm();
		formClassificazione.setValuesManager(vm);
		formClassificazione.setHeight("5");
		formClassificazione.setPadding(5);
		formClassificazione.setWrapItemTitles(false);
		formClassificazione.setNumCols(15);
		formClassificazione.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		livelliClassificazioneItem = new TextItem("livelliClassificazione", I18NUtil.getMessages().tipofascicoliaggr_detail_livelliClassificazione());
		livelliClassificazioneItem.setWidth(100);
		livelliClassificazioneItem.setColSpan(1);
		// livelliClassificazioneItem.setRequired(true);
		livelliClassificazioneItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (livelliClassificazioneItem.getValue() != null && !"".equals(livelliClassificazioneItem.getValueAsString().trim())
						&& desClassificazioneItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});
		livelliClassificazioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				manageIndiceChange();
			}
		});

		// BOTTONI : seleziona dal titolario
		lookupTitolarioButton = new ImgButtonItem("lookupTitolarioButton", "lookup/titolario.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupTitolarioButton_prompt());
		lookupTitolarioButton.setWidth(16);
		lookupTitolarioButton.setColSpan(1);
		lookupTitolarioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				final ClassificaFascicoloLookupTitolario lookupTitolarioPopup = new ClassificaFascicoloLookupTitolario(formClassificazione.getValuesAsRecord());
				lookupTitolarioPopup.show();
			}
		});

		desClassificazioneItem = new HiddenItem("desClassificazione");

		// lista classifiche assegnabili
		SelectGWTRestDataSource classificheDS = new SelectGWTRestDataSource("LoadComboClassificaDataSource", "idClassifica", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		idClassificazioneItem = new FilteredSelectItemWithDisplay("idClassificazione", classificheDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
				classificheDS.addParam("descrizione", record.getAttributeAsString("descrizione"));
				desClassificazioneItem.setOptionDataSource(classificheDS);
				formClassificazione.setValue("idClassificazione", record.getAttributeAsString("idClassifica"));
				formClassificazione.setValue("desClassificazione", record.getAttributeAsString("descrizione"));
				formClassificazione.setValue("livelliClassificazione", record.getAttributeAsString("indice"));
				formClassificazione.clearFieldErrors("indice", true);
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
				classificheDS.addParam("descrizione", null);
				desClassificazioneItem.setOptionDataSource(classificheDS);
				formClassificazione.setValue("idClassificazione", "");
				formClassificazione.setValue("desClassificazione", "");
				formClassificazione.setValue("livelliClassificazione", "");
				formClassificazione.clearFieldErrors("indice", true);
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
					classificheDS.addParam("descrizione", null);
					desClassificazioneItem.setOptionDataSource(classificheDS);
					formClassificazione.setValue("idClassificazione", "");
					formClassificazione.setValue("desClassificazione", "");
					formClassificazione.setValue("livelliClassificazione", "");
					formClassificazione.clearFieldErrors("indice", true);
				}
			}
		};
		idClassificazioneItem.setAutoFetchData(false);
		idClassificazioneItem.setFetchMissingValues(true);
		ListGridField indiceField = new ListGridField("indice", "Indice");
		indiceField.setWidth(100);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		ListGridField descrizioneEstesaField = new ListGridField("descrizioneEstesa", "Descr. estesa");
		descrizioneEstesaField.setHidden(true);
		idClassificazioneItem.setPickListFields(indiceField, descrizioneField, descrizioneEstesaField);
		idClassificazioneItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());
		idClassificazioneItem.setValueField("idClassifica");
		idClassificazioneItem.setOptionDataSource(classificheDS);
		idClassificazioneItem.setShowTitle(false);
		idClassificazioneItem.setClearable(true);
		idClassificazioneItem.setShowIcons(true);
		idClassificazioneItem.setWidth(500);
		// idClassificazioneItem.setRequired(true);
		idClassificazioneItem.setColSpan(11);

		formClassificazione.setItems(livelliClassificazioneItem, lookupTitolarioButton, idClassificazioneItem, desClassificazioneItem);
		
		detailSectionClassificazione = new DetailSection("Classificazione", true, true, false, formClassificazione);

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
		
		if (record.getAttribute("idClassificazione") != null && !"".equals(record.getAttributeAsString("idClassificazione"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idClassificazione"), record.getAttribute("desClassificazione"));
			idClassificazioneItem.setValueMap(valueMap);
		}
		if (record.getAttribute("idFolderTypeGen") != null && !"".equals(record.getAttributeAsString("idFolderTypeGen"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idFolderTypeGen"), record.getAttribute("nomeFolderTypeGen"));
			idFolderTypeGenItem.setValueMap(valueMap);
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


	private void reloadComboFromRecord(Record record) {

		GWTRestDataSource classificheDS = (GWTRestDataSource) idClassificazioneItem.getOptionDataSource();		
		if (record.getAttribute("idClassificazione") != null && !"".equals(record.getAttribute("idClassificazione"))) {
			classificheDS.addParam("idClassifica", (String) record.getAttribute("idClassificazione"));
		} else {
			classificheDS.addParam("idClassifica", null);
		}
		idClassificazioneItem.setOptionDataSource(classificheDS);
		idClassificazioneItem.fetchData();
		
		GWTRestDataSource folderTypesGenDS = (GWTRestDataSource) idFolderTypeGenItem.getOptionDataSource();		
		if (record.getAttribute("idFolderType") != null && !"".equals(record.getAttribute("idFolderType"))) {
			folderTypesGenDS.addParam("idFolderType", (String) record.getAttribute("idFolderType"));
		} else {
			folderTypesGenDS.addParam("idFolderType", null);
		}
		if (record.getAttribute("idFolderTypeGen") != null && !"".equals(record.getAttribute("idFolderTypeGen"))) {
			folderTypesGenDS.addParam("idFolderTypeGen", (String) record.getAttribute("idFolderTypeGen"));
		} else {
			folderTypesGenDS.addParam("idFolderTypeGen", null);
		}
		idFolderTypeGenItem.setOptionDataSource(folderTypesGenDS);
		idFolderTypeGenItem.fetchData();
		
		GWTRestDataSource processTypesDS = (GWTRestDataSource) idProcessTypeItem.getOptionDataSource();		
		if (record.getAttribute("idProcessType") != null && !"".equals(record.getAttribute("idProcessType"))) {
			processTypesDS.addParam("idProcessType", (String) record.getAttribute("idProcessType"));
		} else {
			processTypesDS.addParam("idProcessType", null);
		}
		idProcessTypeItem.setOptionDataSource(processTypesDS);
		idProcessTypeItem.fetchData();
	}

	public class ClassificaFascicoloLookupTitolario extends LookupTitolarioPopup {

		public ClassificaFascicoloLookupTitolario(Record record) {
			super(record, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordTitolario(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	public void setFormValuesFromRecordTitolario(Record record) {
		
		formClassificazione.clearErrors(true);
		String idClassifica = record.getAttribute("idClassificazione");
		if (idClassifica == null || "".equals(idClassifica)) {
			idClassifica = record.getAttribute("idFolder");
		}
		formClassificazione.setValue("desClassificazione", record.getAttribute("descrizione"));
		formClassificazione.setValue("livelliClassificazione", record.getAttribute("indice"));
		formClassificazione.setValue("idClassificazione", idClassifica);

		manageIndiceChange();

		formClassificazione.markForRedraw();
	}

	protected void manageIndiceChange() {
		
		formClassificazione.setValue("idClassificazione", (String) null);
		formClassificazione.clearErrors(true);
		final String value = livelliClassificazioneItem.getValueAsString();
		SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
		classificheDS.addParam("indice", value);
		classificheDS.addParam("descrizione", null);
		idClassificazioneItem.setOptionDataSource(classificheDS);
		if (value != null && !"".equals(value)) {
			classificheDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String indice = data.get(i).getAttribute("indice");
							if (value.equals(indice)) {
								SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) idClassificazioneItem.getOptionDataSource();
								classificheDS.addParam("descrizione", data.get(i).getAttributeAsString("descrizione"));
								idClassificazioneItem.setOptionDataSource(classificheDS);

								formClassificazione.setValue("idClassificazione", data.get(i).getAttribute("idClassifica"));
								formClassificazione.setValue("desClassificazione", data.get(i).getAttributeAsString("descrizione"));
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						livelliClassificazioneItem.validate();
						livelliClassificazioneItem.blurItem();
					}
				}
			});
		}
	}

}
