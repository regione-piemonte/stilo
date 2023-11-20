/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AnagrafeProcedimentiDetail extends CustomDetail {

	protected DetailSection detailSectionInfoAnagrafeProcedimento;
	protected DetailSection detailSectionAbilitazioni;
	protected DetailSection detailSectionFlussiWorklow;

	protected DynamicForm form;
	protected DynamicForm formAbilitazioni;
	protected DynamicForm formFlussiWorkflow;

	private HiddenItem idItem;

	private TextItem nomeItem;
	private TextAreaItem descrizioneItem;

	private NumericItem durataMax;
	// private TextItem uoCompetente;
	// private TextItem responsabile;
	private TextAreaItem rifNormativi;

	private DateItem dtInizioVldItem;
	private DateItem dtFineVldItem;
	private SelectItem tipoIniziativaItem;
	private CheckboxItem flgSospendibileItem;
	private NumericItem nroMaxSospensioniItem;
	private CheckboxItem flgInterrompibileItem;
	private NumericItem nroMaxGGXInterrItem;
	private CheckboxItem flgPartiEsterneItem;
	private CheckboxItem flgSilenzioAssensoItem;
	private NumericItem flgGGSilenzioAssensoItem;
	private SelectItem flgFascSFItem;
	// private CheckboxItem flgRichAbilXVisualizzItem;
	// private CheckboxItem flgLockedItem;
	private HiddenItem nomeProcessTypeGen;
	private FilteredSelectItem idProcessTypeGenItem;

	private HiddenItem flgAmministrativoItem;

	private FlussiAssociatiWorkflowItem flussiAssociatiWorkflowItem;

	public AnagrafeProcedimentiDetail(String nomeEntita) {
		super(nomeEntita);

		setInputForm();

		setFormAbil();

		setFormWorkflow();

		detailSectionInfoAnagrafeProcedimento = new DetailSection("Informazioni procedimento", true, true, false, form);

		detailSectionAbilitazioni = new DetailSection("Tempistiche", true, true, false, formAbilitazioni);

		detailSectionFlussiWorklow = new DetailSection("Flussi workflow associati", true, true, false, formFlussiWorkflow) {
			
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(detailSectionInfoAnagrafeProcedimento);
		lVLayout.addMember(detailSectionAbilitazioni);
		lVLayout.addMember(detailSectionFlussiWorklow);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	private void setInputForm() {
		
		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setHeight("5");
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(10);
		form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idItem = new HiddenItem("id");
		idItem.setVisible(false);

		flgAmministrativoItem = new HiddenItem("flgAmministrativo");

		nomeItem = new TextItem("nome", I18NUtil.getMessages().anagrafe_procedimenti_nome());
		nomeItem.setRequired(true);
		nomeItem.setWidth(250);
		nomeItem.setColSpan(1);
		nomeItem.setStartRow(true);
		
		descrizioneItem = new TextAreaItem("descrizione", I18NUtil.getMessages().anagrafe_procedimenti_descrizione());
		descrizioneItem.setColSpan(10);
		descrizioneItem.setStartRow(true);
		descrizioneItem.setLength(4000);
		descrizioneItem.setHeight(60);
		descrizioneItem.setWidth(650);

		rifNormativi = new TextAreaItem("rifNormativi", I18NUtil.getMessages().anagrafe_procedimenti_Rif_normativi());
		rifNormativi.setColSpan(10);
		rifNormativi.setStartRow(true);
		rifNormativi.setLength(4000);
		rifNormativi.setHeight(60);
		rifNormativi.setWidth(650);

		// uoCompetente = new TextItem("uoCompetente", I18NUtil.getMessages().anagrafe_procedimenti_UOCompetente());
		// uoCompetente.setWidth(250);

		// responsabile = new TextItem("responsabile", I18NUtil.getMessages().anagrafe_procedimenti_responsabile());
		// responsabile.setWidth(250);
		// responsabile.setStartRow(true);

		durataMax = new NumericItem("durataMax", I18NUtil.getMessages().anagrafe_procedimenti_duratamax());

		dtInizioVldItem = new DateItem("dtInizioVld", "Data inizio validita'");
		dtInizioVldItem.setColSpan(1);
		// dtInizioVldItem.setStartRow(true);

		dtFineVldItem = new DateItem("dtFineVld", "Data fine validita'");
		dtFineVldItem.setColSpan(1);

		tipoIniziativaItem = new SelectItem("iniziativa", "Tipo inziativa");
		LinkedHashMap<String, String> flgTipoIniziativaValueMap = new LinkedHashMap<String, String>();
		flgTipoIniziativaValueMap.put("P", "di Parte");
		flgTipoIniziativaValueMap.put("U", "d'Ufficio");
		tipoIniziativaItem.setValueMap(flgTipoIniziativaValueMap);
		tipoIniziativaItem.setColSpan(1);
		tipoIniziativaItem.setStartRow(true);
		tipoIniziativaItem.setAllowEmptyValue(true);
		tipoIniziativaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		tipoIniziativaItem.setAttribute("obbligatorio", true);
		tipoIniziativaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return flgAmministrativoItem.getValue() != null && (Boolean) flgAmministrativoItem.getValue();
			}
		}));

		flgFascSFItem = new SelectItem("flgFascSF", "Fascicolazione");
		LinkedHashMap<String, String> flgFascSFValueMap = new LinkedHashMap<String, String>();
		flgFascSFValueMap.put("F", "Fascicolo");
		flgFascSFValueMap.put("S", "Sotto-fascicolo");
		flgFascSFItem.setValueMap(flgFascSFValueMap);
		flgFascSFItem.setColSpan(1);
		flgFascSFItem.setAllowEmptyValue(true);
		flgFascSFItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		nomeProcessTypeGen = new HiddenItem("nomeProcessTypeGen");

		GWTRestDataSource dipendenzeAltriAttributiDS = new GWTRestDataSource("LoadComboAnagrafeProcedimentoDataSource", "key", FieldType.TEXT, true);
		ListGridField keyField = new ListGridField("key");
		keyField.setHidden(true);
		ListGridField nomeField = new ListGridField("value", "Nome");
		idProcessTypeGenItem = new FilteredSelectItem("idProcessTypeGen", "Ricade in") {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				nomeProcessTypeGen.setValue(record.getAttribute("value"));
				markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					nomeProcessTypeGen.setValue("");
				}
				markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				nomeProcessTypeGen.setValue("");
				markForRedraw();
			};
		};
		idProcessTypeGenItem.setPickListFields(keyField, nomeField);
		idProcessTypeGenItem.setWidth(460);
		idProcessTypeGenItem.setClearable(true);
		idProcessTypeGenItem.setValueField("key");
		idProcessTypeGenItem.setDisplayField("value");
		idProcessTypeGenItem.setColSpan(10);
		idProcessTypeGenItem.setStartRow(true);
		// idProcessTypeGenItem.setRequired(true);
		idProcessTypeGenItem.setOptionDataSource(dipendenzeAltriAttributiDS);
		idProcessTypeGenItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		form.setItems(idItem, flgAmministrativoItem, nomeItem, durataMax, descrizioneItem, rifNormativi, /* uoCompetente, responsabile, */
				tipoIniziativaItem, flgFascSFItem, nomeProcessTypeGen, idProcessTypeGenItem, dtInizioVldItem, dtFineVldItem);

	}

	private void setFormAbil() {
		
		formAbilitazioni = new DynamicForm();
		formAbilitazioni.setValuesManager(vm);
		formAbilitazioni.setWidth("100%");
		formAbilitazioni.setHeight("5");
		formAbilitazioni.setPadding(5);
		formAbilitazioni.setWrapItemTitles(false);
		formAbilitazioni.setNumCols(10);
		formAbilitazioni.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		// flgRichAbilXVisualizzItem = new CheckboxItem("flgRichAbilXVisualizz", "Visualizza processi del dato tipo");
		// flgRichAbilXVisualizzItem.setColSpan(1);
		// flgRichAbilXVisualizzItem.setWidth(50);
		// flgRichAbilXVisualizzItem.setStartRow(true);

		// flgLockedItem = new CheckboxItem("flgLocked", "Non modificabile/cancellabile in GUI");
		// flgLockedItem.setColSpan(1);
		// flgLockedItem.setWidth(50);

		flgSospendibileItem = new CheckboxItem("flgSospendibile", "Sospendibile");
		flgSospendibileItem.setColSpan(1);
		flgSospendibileItem.setWidth(50);
		flgSospendibileItem.setStartRow(true);
		flgSospendibileItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		nroMaxSospensioniItem = new NumericItem("nroMaxSospensioni", "Max numero di sospensioni");
		nroMaxSospensioniItem.setHint("(gg)");
		nroMaxSospensioniItem.setWidth(100);
		nroMaxSospensioniItem.setColSpan(1);
		nroMaxSospensioniItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(flgSospendibileItem.getValue() != null && (Boolean) flgSospendibileItem.getValue() ? true : false);
			}
		});

		flgInterrompibileItem = new CheckboxItem("flgInterrompibile", "Interrompibile");
		flgInterrompibileItem.setColSpan(1);
		flgInterrompibileItem.setWidth(50);
		flgInterrompibileItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		nroMaxGGXInterrItem = new NumericItem("nroMaxGGXInterr", "Max numero di interruzioni");
		nroMaxGGXInterrItem.setHint("(gg)");
		nroMaxGGXInterrItem.setWidth(100);
		nroMaxGGXInterrItem.setColSpan(1);
		nroMaxGGXInterrItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(flgInterrompibileItem.getValue() != null && (Boolean) flgInterrompibileItem.getValue() ? true : false);
			}
		});

		flgSilenzioAssensoItem = new CheckboxItem("flgSilenzioAssenso", "Silenzio-assenso");
		flgSilenzioAssensoItem.setColSpan(1);
		flgSilenzioAssensoItem.setWidth(50);
		flgSilenzioAssensoItem.setStartRow(true);
		flgSilenzioAssensoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		flgGGSilenzioAssensoItem = new NumericItem("nrGGSilenzioAssenso", "Giorni silenzio-assenso");
		flgGGSilenzioAssensoItem.setWidth(100);
		flgGGSilenzioAssensoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(flgSilenzioAssensoItem.getValue() != null && (Boolean) flgSilenzioAssensoItem.getValue() ? true : false);
			}
		});

		flgPartiEsterneItem = new CheckboxItem("flgPartiEsterne", "Parti esterne");
		flgPartiEsterneItem.setColSpan(1);
		flgPartiEsterneItem.setWidth(50);

		formAbilitazioni.setItems(/* flgRichAbilXVisualizzItem, flgLockedItem, */flgSospendibileItem, nroMaxSospensioniItem, flgInterrompibileItem,
				nroMaxGGXInterrItem, flgSilenzioAssensoItem, flgGGSilenzioAssensoItem, flgPartiEsterneItem);
	}

	private void setFormWorkflow() {
		
		formFlussiWorkflow = new DynamicForm();
		formFlussiWorkflow.setValuesManager(vm);
		formFlussiWorkflow.setWidth("100%");
		formFlussiWorkflow.setHeight("5");
		formFlussiWorkflow.setPadding(5);
		formFlussiWorkflow.setWrapItemTitles(false);
		formFlussiWorkflow.setNumCols(10);
		formFlussiWorkflow.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		flussiAssociatiWorkflowItem = new FlussiAssociatiWorkflowItem();
		flussiAssociatiWorkflowItem.setName("listaFlussiWfAssociati");
		flussiAssociatiWorkflowItem.setStartRow(true);
		flussiAssociatiWorkflowItem.setShowTitle(false);

		formFlussiWorkflow.setItems(flussiAssociatiWorkflowItem);
	}

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		comboIdProcessTypeEditRecord(record);
	}

	private void comboIdProcessTypeEditRecord(Record record) {
		
		GWTRestDataSource listaDataDS = (GWTRestDataSource) idProcessTypeGenItem.getOptionDataSource();
		if (record.getAttribute("idProcessTypeGen") != null && !"".equals(record.getAttributeAsString("idProcessTypeGen"))) {
			listaDataDS.addParam("idProcessTypeGen", record.getAttributeAsString("idProcessTypeGen"));
		} else {
			listaDataDS.addParam("idProcessTypeGen", null);
		}
		if (record.getAttribute("nomeProcessTypeGen") != null && !"".equals(record.getAttributeAsString("nomeProcessTypeGen"))) {
			listaDataDS.addParam("nomeProcessTypeGen", record.getAttributeAsString("nomeProcessTypeGen"));
		} else {
			listaDataDS.addParam("nomeProcessTypeGen", null);
		}
		idProcessTypeGenItem.setOptionDataSource(listaDataDS);
		idProcessTypeGenItem.fetchData();
	}
	
	@Override
	public void newMode() {
		super.newMode();
		setInitialValues();
	}
	
	@Override
	public void viewMode() {
		super.viewMode();
		setInitialValues();
	}
	
	@Override
	public void editMode() {
		super.editMode();
		setInitialValues();
	}
	
	public void setInitialValues() {		
		if (detailSectionFlussiWorklow != null) {
			detailSectionFlussiWorklow.open();			
		}	
	}

	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = new Record();
		addFormValues(lRecordToSave, form);
		addFormValues(lRecordToSave, formAbilitazioni);
		addFormValues(lRecordToSave, formFlussiWorkflow);
		return lRecordToSave;
	}
	
	protected static void addFormValues(Record record, DynamicForm form) {

		if (form != null) {
			try {
				Record formRecord = form.getValuesAsRecord();
				for (Object fieldName : form.getValues().keySet()) {
					FormItem item = form.getItem((String) fieldName);
					if (item != null && (item instanceof ReplicableItem)) {						
						final RecordList lRecordList = new RecordList();
						ReplicableCanvas[] allCanvas = ((ReplicableItem) item).getAllCanvas();
						if(allCanvas != null && allCanvas.length > 0) {
							for (ReplicableCanvas lReplicableCanvas : allCanvas) {
								if(lReplicableCanvas.hasValue(((ReplicableItem) item).getCanvasDefaultRecord())) {
									lRecordList.add(lReplicableCanvas.getFormValuesAsRecord());
								}
							}
							if(((ReplicableItem) item).isObbligatorio() && lRecordList.getLength() == 0) {
								lRecordList.add(allCanvas[0].getFormValuesAsRecord());
							}
						}
						formRecord.setAttribute((String) fieldName, lRecordList);						
					} 
				}
				JSOHelper.addProperties(record.getJsObj(), formRecord.getJsObj());
			} catch (Exception e) {
			}
		}
	}
	
}
