/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
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
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DefAttivitaProcedimentiDetail extends CustomDetail {

	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;

	protected DynamicForm form;
	protected DynamicForm formMetadatiSpecifici;

	protected HiddenItem idEventType;

	protected TextItem descrizione;
	protected SelectItem categoria;
	protected CheckboxItem durativa;
	protected TextItem durataMax;
	protected CheckboxItem flgTuttiProcedimenti;
	protected FilteredSelectItem tipologiaDocumentaleTrattata;
	protected SelectItem dataDocumento;

	protected AttributiAddXEventiDelTipoItem attributiAddXEventiDelTipoItem;

	protected DetailSection detailSectionAttributiAddXEventiDelTipo;

	protected String rowid;
	protected LinkedHashMap<String, String> attributiAddTabs;
	protected HashMap<String, VLayout> attributiAddLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDetails;

	public DefAttivitaProcedimentiDetail(String nomeEntita) {
		
		super(nomeEntita);
		
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
		
		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setHeight("5");
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(12);
		form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		formMetadatiSpecifici = new DynamicForm();
		formMetadatiSpecifici.setValuesManager(vm);
		formMetadatiSpecifici.setWidth("100%");
		formMetadatiSpecifici.setHeight("5");
		formMetadatiSpecifici.setPadding(5);
		formMetadatiSpecifici.setWrapItemTitles(false);
		formMetadatiSpecifici.setNumCols(12);
		formMetadatiSpecifici.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idEventType = new HiddenItem("idEventType");

		descrizione = new TextItem("descrizione", I18NUtil.getMessages().definizione_attivita_procedimenti_descrizione());
		descrizione.setColSpan(10);
		descrizione.setStartRow(true);
		descrizione.setWidth(400);
		descrizione.setRequired(true);

		final GWTRestDataSource categTipiDS = new GWTRestDataSource("LoadCategoriaInOutDataSource", "key", FieldType.TEXT);
		categoria = new SelectItem("categoria", I18NUtil.getMessages().definizione_attivita_procedimenti_categoria());
		categoria.setCachePickListResults(false);
		categoria.setColSpan(4);
		categoria.setWidth(400);
		categoria.setValueField("key");
		categoria.setDisplayField("value");
		categoria.setOptionDataSource(categTipiDS);
		categoria.setClearable(true);
		categoria.setStartRow(true);

		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setColSpan(1);
		lSpacerItem.setStartRow(true);

		durativa = new CheckboxItem("puntualeDurativa", "Durativa");
		durativa.setColSpan(1);
		durativa.setWidth("*");
		durativa.setStartRow(false);
		durativa.setEndRow(false);
		durativa.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				form.redraw();
			}
		});

		/*
		 * E’ visibile solo se spuntato il check “Durativa”
		 */
		durataMax = new TextItem("durataMaxGiorni", "Durata Max in Giorni");
		durataMax.setColSpan(1);
		durataMax.setEndRow(true);
		durataMax.setWidth(150);
		durataMax.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(durativa.getValue() != null && (Boolean) durativa.getValue() ? true : false);
			}
		});

		flgTuttiProcedimenti = new CheckboxItem("flgTuttiProcedimenti", I18NUtil.getMessages().definizione_attivita_procedimenti_in_tutti_procedimenti());
		flgTuttiProcedimenti.setValue(false);
		flgTuttiProcedimenti.setColSpan(1);
		flgTuttiProcedimenti.setWidth("*");
		flgTuttiProcedimenti.setEndRow(true);

		final GWTRestDataSource tipoDocTrattTipiDS = new GWTRestDataSource("LoadTipoDocumentaleDataSource", "key", FieldType.TEXT, true);
		tipologiaDocumentaleTrattata = new FilteredSelectItem("tipologiaDocAss", "Tipologia documentale trattata") {

			@Override
			public void onOptionClick(Record record) {

				super.onOptionClick(record);
				form.markForRedraw();
			}

			@Override
			public void setValue(String value) {

				super.setValue(value);
				form.markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				form.markForRedraw();
			};
		};
		tipologiaDocumentaleTrattata.setColSpan(4);
		tipologiaDocumentaleTrattata.setWidth(420);
		ListGridField keyField = new ListGridField("key", "Nome");
		keyField.setHidden(true);
		ListGridField valueField = new ListGridField("value", "Etichetta");
		tipologiaDocumentaleTrattata.setPickListFields(keyField, valueField);
		tipologiaDocumentaleTrattata.setValueField("key");
		tipologiaDocumentaleTrattata.setDisplayField("value");
		tipologiaDocumentaleTrattata.setOptionDataSource(tipoDocTrattTipiDS);
		tipologiaDocumentaleTrattata.setClearable(true);
		tipologiaDocumentaleTrattata.setStartRow(true);
		tipologiaDocumentaleTrattata.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				form.markForRedraw();
			}
		});

		final GWTRestDataSource dataDocTipiDS = new GWTRestDataSource("LoadDataInizioDataSource", "key", FieldType.TEXT);
		dataDocumento = new SelectItem("codTipoDataRelDocInOut", "Data documento che costituisce<br/>la data/inizio attività");
		dataDocumento.setCachePickListResults(false);
		dataDocumento.setColSpan(4);
		dataDocumento.setOptionDataSource(dataDocTipiDS);
		dataDocumento.setStartRow(true);
		dataDocumento.setValueField("key");
		dataDocumento.setDisplayField("value");
		dataDocumento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(tipologiaDocumentaleTrattata.getValue() != null && !tipologiaDocumentaleTrattata.getValue().toString().equals("") ? true
						: false);
			}
		});

		/*
		 * SEZIONE METADATI SPECIFICI
		 */

		// ************************************************************************
		// FORM ATTRIBUTI CUSTOM
		// ************************************************************************

		attributiAddXEventiDelTipoItem = new AttributiAddXEventiDelTipoItem();
		attributiAddXEventiDelTipoItem.setName("listaAttributiAddXEventiDelTipo");
		attributiAddXEventiDelTipoItem.setShowTitle(false);
		attributiAddXEventiDelTipoItem.setOrdinabile(true);

		formMetadatiSpecifici.setFields(attributiAddXEventiDelTipoItem);

		detailSectionAttributiAddXEventiDelTipo = new DetailSection("Scelta attributi custom", true, true, false, formMetadatiSpecifici);

		form.setItems(idEventType, descrizione, categoria, lSpacerItem, durativa, durataMax, lSpacerItem, flgTuttiProcedimenti, tipologiaDocumentaleTrattata,
				dataDocumento);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(form);
		lVLayout.addMember(detailSectionAttributiAddXEventiDelTipo);
				
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
		lRecordLoad.setAttribute("nomeTabella", "DMT_EVENT_TYPES");
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
					lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_EVENT_TYPES");
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
		
		comboCategoriaEditNewRecord(initialValues);
		comboTipologiaTrattataEditNewRecord(initialValues);
		comboDataEditNewRecord(initialValues);

		caricaAttributiDinamici(null);		
	}

	@Override
	public void editRecord(Record record) {
		
		vm.clearErrors(true);
		clearTabErrors(tabSet);

		super.editRecord(record);

		comboCategoriaEditRecord(record);
		comboTipologiaTrattataEditRecord(record);
		comboDataEditRecord(record);
		
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
	
	private void comboDataEditNewRecord(Map initialValues) {
		
		GWTRestDataSource listaDataDS = (GWTRestDataSource) dataDocumento.getOptionDataSource();
		if (initialValues.get("idEventType") != null && !"".equals(initialValues.get("idEventType"))) {
			listaDataDS.addParam("idAttivita", (String) initialValues.get("idEventType"));
		} else {
			listaDataDS.addParam("idAttivita", null);
		}
		
		dataDocumento.setOptionDataSource(listaDataDS);
		dataDocumento.fetchData();
	}

	private void comboDataEditRecord(Record record) {
		
		GWTRestDataSource listaDataDS = (GWTRestDataSource) dataDocumento.getOptionDataSource();
		if (record.getAttribute("idEventType") != null && !"".equals(record.getAttributeAsString("idEventType"))) {
			listaDataDS.addParam("idAttivita", record.getAttributeAsString("idEventType"));
		} else {
			listaDataDS.addParam("idAttivita", null);
		}
		
		dataDocumento.setOptionDataSource(listaDataDS);
		dataDocumento.fetchData();
	}

	private void comboCategoriaEditNewRecord(Map initialValues) {
		
		GWTRestDataSource listaCategoriaDS = (GWTRestDataSource) categoria.getOptionDataSource();
		if (initialValues.get("idEventType") != null && !"".equals(initialValues.get("idEventType"))) {
			listaCategoriaDS.addParam("idAttMod", (String) initialValues.get("idEventType"));
		} else {
			listaCategoriaDS.addParam("idAttMod", null);
		}
		
		categoria.setOptionDataSource(listaCategoriaDS);
		categoria.fetchData();
	}

	private void comboCategoriaEditRecord(Record record) {
		
		GWTRestDataSource listaCategoriaDS = (GWTRestDataSource) categoria.getOptionDataSource();
		if (record.getAttribute("idEventType") != null && !"".equals(record.getAttributeAsString("idEventType"))) {
			listaCategoriaDS.addParam("idAttMod", record.getAttributeAsString("idEventType"));
		} else {
			listaCategoriaDS.addParam("idAttMod", null);
		}
		
		categoria.setOptionDataSource(listaCategoriaDS);
		categoria.fetchData();
	}

	private void comboTipologiaTrattataEditNewRecord(Map initialValues) {
		
		Record record = new Record(initialValues);
		
		GWTRestDataSource listaTipologiaDS = (GWTRestDataSource) tipologiaDocumentaleTrattata.getOptionDataSource();
		if (record.getAttribute("tipologiaDocAss") != null && !"".equals(record.getAttributeAsString("tipologiaDocAss"))) {
			String[] splitter = record.getAttributeAsString("tipologiaDocAss").split("&-&");
			listaTipologiaDS.addParam("ciToAdd", splitter[0]);
		} else {
			listaTipologiaDS.addParam("ciToAdd", null);
		}
		
		tipologiaDocumentaleTrattata.setOptionDataSource(listaTipologiaDS);
		tipologiaDocumentaleTrattata.fetchData();
	}

	private void comboTipologiaTrattataEditRecord(Record record) {
		
		GWTRestDataSource listaTipologiaDS = (GWTRestDataSource) tipologiaDocumentaleTrattata.getOptionDataSource();
		if (record.getAttribute("tipologiaDocAss") != null && !"".equals(record.getAttributeAsString("tipologiaDocAss"))) {
			String[] splitter = record.getAttributeAsString("tipologiaDocAss").split("&-&");
			listaTipologiaDS.addParam("ciToAdd", splitter[0]);
		} else {
			listaTipologiaDS.addParam("ciToAdd", null);
		}
		
		tipologiaDocumentaleTrattata.setOptionDataSource(listaTipologiaDS);
		tipologiaDocumentaleTrattata.fetchData();
	}
	
}