/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaUOItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AvvioPropostaOrganigrammaDetail extends CustomDetail{

	private DetailSection detailSectionUfficioRevisioneOrganigramma;
	
	private DynamicForm avvioProcedimentoForm;
	private DynamicForm ufficioRevisioneOrganigrammaForm1;
	private DynamicForm ufficioRevisioneOrganigrammaForm2;
	private DynamicForm ufficioRevisioneOrganigrammaForm3;
	
	private FilteredSelectItemWithDisplay idProcessTypeItem;
	private HiddenItem nomeProcessTypeItem;
	private HiddenItem idDocTypeItem;
	private HiddenItem nomeDocTypeItem;
	
	private SelectItem flowTypeIdItem; 
	
	private RadioGroupItem flgTipoStrutturaItem;
	private SelezionaUOItem listaStrutturaEsistenteItem;
	private RadioGroupItem flgTipoNuovaStrutturaItem;
	private SelezionaUOItem listaStrutturaPadreItem;
	private TextItem codNuovaStrutturaItem;
	private TextItem nomeNuovaStrutturaItem;
	
	public AvvioPropostaOrganigrammaDetail(String nomeEntita) {
		super(nomeEntita);

		avvioProcedimentoForm = new DynamicForm();
		avvioProcedimentoForm.setNumCols(20);
		avvioProcedimentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		avvioProcedimentoForm.setValuesManager(vm);
		
		SelectGWTRestDataSource lLoadComboSelezionaProcOrganigrammaDataSource = new SelectGWTRestDataSource("LoadComboSelezionaProcOrganigrammaDataSource", "idProcessType", FieldType.INTEGER, new String[]{"nomeProcessType"}, true);
			
		nomeProcessTypeItem = new HiddenItem("nomeProcessType");
		idDocTypeItem = new HiddenItem("idDocType");
		nomeDocTypeItem = new HiddenItem("nomeDocType");
		
		idProcessTypeItem = new FilteredSelectItemWithDisplay("idProcessType", lLoadComboSelezionaProcOrganigrammaDataSource){
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid pickListProperties = super.builPickListProperties();
				pickListProperties.setShowHeader(false);				
				return pickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				avvioProcedimentoForm.setValue("nomeProcessType", record.getAttribute("nomeProcessType"));
				avvioProcedimentoForm.setValue("idDocType", record.getAttribute("idDocType"));
				avvioProcedimentoForm.setValue("nomeDocType", record.getAttribute("nomeDocType"));				
				flowTypeIdItem.clearValue();
				GWTRestDataSource lGWTRestDataSource = (GWTRestDataSource) flowTypeIdItem.getOptionDataSource();
				lGWTRestDataSource.addParam("idProcessType", record.getAttribute("idProcessType"));
				flowTypeIdItem.fetchData();
			}	
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				avvioProcedimentoForm.setValue("idProcessType", "");
				avvioProcedimentoForm.setValue("nomeProcessType", "");
				avvioProcedimentoForm.setValue("idDocType", "");
				avvioProcedimentoForm.setValue("nomeDocType", "");
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					avvioProcedimentoForm.setValue("idProcessType", "");
					avvioProcedimentoForm.setValue("nomeProcessType", "");
					avvioProcedimentoForm.setValue("idDocType", "");
					avvioProcedimentoForm.setValue("nomeDocType", "");				
				}
			}
		};	
		idProcessTypeItem.setStartRow(true);
		idProcessTypeItem.setWidth("*");
		ListGridField nomeField = new ListGridField("nomeProcessType", "Nome");
		idProcessTypeItem.setPickListFields(nomeField);		
		idProcessTypeItem.setFilterLocally(true);
		idProcessTypeItem.setValueField("idProcessType");
		idProcessTypeItem.setDisplayField("nomeProcessType");
		idProcessTypeItem.setTitle("Seleziona procedimento");
		idProcessTypeItem.setWrapTitle(false);
		idProcessTypeItem.setOptionDataSource(lLoadComboSelezionaProcOrganigrammaDataSource); 
		idProcessTypeItem.setWidth(500);
		idProcessTypeItem.setRequired(true);
		idProcessTypeItem.setClearable(true);
		idProcessTypeItem.setShowIcons(true);
		idProcessTypeItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {				
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("nomeProcessType") : null;
			}
		});		
		
		GWTRestDataSource lLoadComboSelezionaFlussoDiLavoroDataSource = new GWTRestDataSource("LoadComboSelezionaFlussoDiLavoroDataSource");
		
		flowTypeIdItem = new SelectItem("flowTypeId", "Seleziona flusso di lavoro");
		flowTypeIdItem.setStartRow(true);
		flowTypeIdItem.setVisible(false);
		flowTypeIdItem.setWidth(500);		
		flowTypeIdItem.setOptionDataSource(lLoadComboSelezionaFlussoDiLavoroDataSource);		
		flowTypeIdItem.setDisplayField("value");
		flowTypeIdItem.setValueField("key");
		flowTypeIdItem.setAutoFetchData(false);		
		flowTypeIdItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return flowTypeIdItem.isVisible();
			}
		}));
		flowTypeIdItem.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				manageDataArrived(event);
			}
		});
		
		avvioProcedimentoForm.setFields(idProcessTypeItem, nomeProcessTypeItem, idDocTypeItem, nomeDocTypeItem, flowTypeIdItem);
		
		ufficioRevisioneOrganigrammaForm1 = new DynamicForm();
		ufficioRevisioneOrganigrammaForm1.setNumCols(20);
		ufficioRevisioneOrganigrammaForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ufficioRevisioneOrganigrammaForm1.setCellPadding(2);
		ufficioRevisioneOrganigrammaForm1.setValuesManager(vm);
		
		flgTipoStrutturaItem = new RadioGroupItem("flgTipoStruttura");
		flgTipoStrutturaItem.setHeight(30);		
		flgTipoStrutturaItem.setWidth(250);
		flgTipoStrutturaItem.setStartRow(true);
		flgTipoStrutturaItem.setShowTitle(false);
		flgTipoStrutturaItem.setValueMap("nuova struttura", "struttura esistente"); 
		flgTipoStrutturaItem.setDefaultValue("struttura esistente");
		flgTipoStrutturaItem.setVertical(false);
		flgTipoStrutturaItem.setWrap(false);
		flgTipoStrutturaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				ufficioRevisioneOrganigrammaForm1.markForRedraw();
				ufficioRevisioneOrganigrammaForm2.markForRedraw();
				ufficioRevisioneOrganigrammaForm3.markForRedraw();
				if(isNuovaStruttura()) {
					ufficioRevisioneOrganigrammaForm2.show();
					ufficioRevisioneOrganigrammaForm3.show();
				} else {
					ufficioRevisioneOrganigrammaForm2.hide();
					ufficioRevisioneOrganigrammaForm3.hide();
				}
			}
		});
		
		listaStrutturaEsistenteItem = new SelezionaUOItem() {
			
			@Override
			public boolean skipValidation() {
				if(isStrutturaEsistente()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return 500;
			}
				
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			};			
		};
		listaStrutturaEsistenteItem.setName("listaStrutturaEsistente");
		listaStrutturaEsistenteItem.setStartRow(false);
		listaStrutturaEsistenteItem.setShowTitle(false);
		listaStrutturaEsistenteItem.setColSpan(18);
		listaStrutturaEsistenteItem.setNotReplicable(true);
		listaStrutturaEsistenteItem.setAttribute("obbligatorio", true);
		listaStrutturaEsistenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStrutturaEsistente();
			}
		});
		
		ufficioRevisioneOrganigrammaForm1.setFields(flgTipoStrutturaItem, listaStrutturaEsistenteItem);
		
		ufficioRevisioneOrganigrammaForm2 = new DynamicForm();
		ufficioRevisioneOrganigrammaForm2.setNumCols(20);
		ufficioRevisioneOrganigrammaForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ufficioRevisioneOrganigrammaForm2.setValuesManager(vm);
		ufficioRevisioneOrganigrammaForm2.setCellPadding(2);
		ufficioRevisioneOrganigrammaForm2.setVisibility(Visibility.HIDDEN);
		
		flgTipoNuovaStrutturaItem = new RadioGroupItem("flgTipoNuovaStruttura"); 
		flgTipoNuovaStrutturaItem.setHeight(30);		
		flgTipoNuovaStrutturaItem.setWidth(250);
		flgTipoNuovaStrutturaItem.setStartRow(true);
		flgTipoNuovaStrutturaItem.setShowTitle(false);
		flgTipoNuovaStrutturaItem.setValueMap("struttura apicale", "sotto struttura padre"); 
		flgTipoNuovaStrutturaItem.setDefaultValue("sotto struttura padre");
		flgTipoNuovaStrutturaItem.setVertical(false);
		flgTipoNuovaStrutturaItem.setWrap(false);
		flgTipoNuovaStrutturaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				ufficioRevisioneOrganigrammaForm1.markForRedraw();
				ufficioRevisioneOrganigrammaForm2.markForRedraw();
				ufficioRevisioneOrganigrammaForm3.markForRedraw();
			}
		});
		flgTipoNuovaStrutturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNuovaStruttura();
			}
		});
		
		listaStrutturaPadreItem = new SelezionaUOItem() {
			
			@Override
			public boolean skipValidation() {
				if(isNuovaStruttura() && isSottoStrutturaPadre()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return 500;
			}
				
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			};			
		};
		listaStrutturaPadreItem.setName("listaStrutturaPadre");
		listaStrutturaPadreItem.setStartRow(false);
		listaStrutturaPadreItem.setShowTitle(false);
		listaStrutturaPadreItem.setColSpan(18);
		listaStrutturaPadreItem.setNotReplicable(true);
		listaStrutturaPadreItem.setAttribute("obbligatorio", true);
		listaStrutturaPadreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNuovaStruttura() && isSottoStrutturaPadre();
			}
		});
		
		ufficioRevisioneOrganigrammaForm2.setFields(flgTipoNuovaStrutturaItem, listaStrutturaPadreItem);
		
		ufficioRevisioneOrganigrammaForm3 = new DynamicForm();
		ufficioRevisioneOrganigrammaForm3.setNumCols(20);
		ufficioRevisioneOrganigrammaForm3.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ufficioRevisioneOrganigrammaForm3.setValuesManager(vm);
		ufficioRevisioneOrganigrammaForm3.setCellPadding(2);
		ufficioRevisioneOrganigrammaForm3.setVisibility(Visibility.HIDDEN);
		
		CustomValidator codNuovaStrutturaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals((String) value))
					return true;
				return ((String) value).indexOf('.') == -1;
			}
		};
		codNuovaStrutturaValidator.setErrorMessage("Valore non valido: il codice può contenere un solo livello");
		
		codNuovaStrutturaItem = new TextItem("codNuovaStruttura", "Cod.");
		codNuovaStrutturaItem.setStartRow(true);
		codNuovaStrutturaItem.setWidth(120);
		codNuovaStrutturaItem.setColSpan(1);
		codNuovaStrutturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNuovaStruttura();
			}
		});
		codNuovaStrutturaItem.setAttribute("obbligatorio", true);
		codNuovaStrutturaItem.setValidators(codNuovaStrutturaValidator, new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isNuovaStruttura();
			}
		}));
		
		nomeNuovaStrutturaItem = new TextItem("nomeNuovaStruttura", "Descrizione");
		nomeNuovaStrutturaItem.setWidth(500);
		nomeNuovaStrutturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNuovaStruttura();
			}
		});
		nomeNuovaStrutturaItem.setAttribute("obbligatorio", true);
		nomeNuovaStrutturaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isNuovaStruttura();
			}
		}));
		
		ufficioRevisioneOrganigrammaForm3.setFields(codNuovaStrutturaItem, nomeNuovaStrutturaItem);
		
		detailSectionUfficioRevisioneOrganigramma = new DetailSection("Revisione organigramma di",true, true, true, ufficioRevisioneOrganigrammaForm1, ufficioRevisioneOrganigrammaForm2, ufficioRevisioneOrganigrammaForm3);
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);					
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(avvioProcedimentoForm);
		lVLayout.addMember(detailSectionUfficioRevisioneOrganigramma);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);		
	}
	
	public boolean isStrutturaEsistente() {
		return flgTipoStrutturaItem.getValue() != null && "struttura esistente".equals(flgTipoStrutturaItem.getValue());		
	}

	public boolean isNuovaStruttura() {
		return flgTipoStrutturaItem.getValue() != null && "nuova struttura".equals(flgTipoStrutturaItem.getValue());
	}
	
	public boolean isStrutturaApicale() {
		return flgTipoNuovaStrutturaItem.getValue() != null && "struttura apicale".equals(flgTipoNuovaStrutturaItem.getValue());
		
	}

	public boolean isSottoStrutturaPadre() {
		return flgTipoNuovaStrutturaItem.getValue() != null && "sotto struttura padre".equals(flgTipoNuovaStrutturaItem.getValue());
		
	}

	protected void manageDataArrived(DataArrivedEvent event) {
		ResultSet lResultSet = event.getData();
		if (lResultSet == null || lResultSet.getLength() == 0) {
			Layout.addMessage(new MessageBean("Procedimento senza flusso modellato: avvio non consentito", "", MessageType.ERROR));
		} else if (lResultSet.getLength() > 1 ){
			flowTypeIdItem.show();
		} else if (lResultSet.getLength() == 1 ){
			vm.setValue("flowTypeId", lResultSet.get(0).getAttribute("key"));
		}
	}

	public Record getRecordToSave() {
		Record lRecordToSave = new Record();
		lRecordToSave.setAttribute("idProcessType", idProcessTypeItem.getValue());
		lRecordToSave.setAttribute("nomeProcessType", nomeProcessTypeItem.getValue());
		lRecordToSave.setAttribute("idDocType", idDocTypeItem.getValue());
		lRecordToSave.setAttribute("nomeDocType", nomeDocTypeItem.getValue());
		lRecordToSave.setAttribute("flowTypeId", flowTypeIdItem.getValue());
		if(isStrutturaEsistente()) {
			RecordList listaStrutturaEsistente = ufficioRevisioneOrganigrammaForm1.getValueAsRecordList("listaStrutturaEsistente");
			if(listaStrutturaEsistente != null && listaStrutturaEsistente.getLength() > 0) {
				lRecordToSave.setAttribute("idUoRevisioneOrganigramma", listaStrutturaEsistente.get(0).getAttribute("idUo"));		
				lRecordToSave.setAttribute("codRapidoUoRevisioneOrganigramma", listaStrutturaEsistente.get(0).getAttribute("codRapido"));
				lRecordToSave.setAttribute("nomeUoRevisioneOrganigramma", listaStrutturaEsistente.get(0).getAttribute("descrizione"));			
			}
		} else if(isNuovaStruttura()) {
			if(isSottoStrutturaPadre()) {
				RecordList listaStrutturaPadre = ufficioRevisioneOrganigrammaForm2.getValueAsRecordList("listaStrutturaPadre");
				if(listaStrutturaPadre != null && listaStrutturaPadre.getLength() > 0) {
					lRecordToSave.setAttribute("idUoPadreRevisioneOrganigramma", listaStrutturaPadre.get(0).getAttribute("idUo"));							
					lRecordToSave.setAttribute("codRapidoUoRevisioneOrganigramma", listaStrutturaPadre.get(0).getAttribute("codRapido") + "." + ufficioRevisioneOrganigrammaForm3.getValue("codNuovaStruttura"));						
				} else {
					lRecordToSave.setAttribute("codRapidoUoRevisioneOrganigramma", ufficioRevisioneOrganigrammaForm3.getValue("codNuovaStruttura"));
				}
				lRecordToSave.setAttribute("nomeUoRevisioneOrganigramma", ufficioRevisioneOrganigrammaForm3.getValue("nomeNuovaStruttura"));	
			} else if(isStrutturaApicale()) {
				lRecordToSave.setAttribute("codRapidoUoRevisioneOrganigramma", ufficioRevisioneOrganigrammaForm3.getValue("codNuovaStruttura"));
				lRecordToSave.setAttribute("nomeUoRevisioneOrganigramma", ufficioRevisioneOrganigrammaForm3.getValue("nomeNuovaStruttura"));	
			}
		}
		return lRecordToSave;
	}

}
