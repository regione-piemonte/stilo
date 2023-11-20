/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

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
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ConfigurazioneFlussiDetail extends CustomDetail {

	protected ConfigurazioneFlussiDetail instance;
	
	// DetailSection
	protected DetailSection aclSection;
	protected DetailSection attributiAddEditabiliSection;
	
	// DynamicForm
	protected DynamicForm mainForm;
	protected DynamicForm aclForm;
	protected DynamicForm attributiAddEditabiliForm;
			
	protected SelectItem codTipoFlussoItem;
	protected TextItem idTaskItem;
	protected TextItem nomeTaskItem;
	protected HiddenItem idFaseOldItem;
	protected TextItem idFaseItem;
	protected NumericItem numeroOrdineItem;
	
	protected PermessiConfigFlussiItem listaAclItem;
	
	protected AttributiAddEditabiliItem listaAttributiAddEditabiliItem;
	protected CheckboxItem flgNessunoItem;
	
	public ConfigurazioneFlussiDetail(String nomeEntita) {
		
		super(nomeEntita);
		
		instance = this;
			
		// FORM PRINCIPALE
		mainForm = new DynamicForm();
		mainForm.setValuesManager(vm);
		mainForm.setWidth("100%");  
		mainForm.setHeight("5");  
		mainForm.setPadding(5);
		mainForm.setNumCols(4);
		mainForm.setColWidths(70,1,1,"*");
		mainForm.setWrapItemTitles(false);
		
		GWTRestDataSource codTipoFlussoDS = new GWTRestDataSource("FlussiWorkflowDataSource", "key", FieldType.TEXT, true);
		
		codTipoFlussoItem = new SelectItem("codTipoFlusso", I18NUtil.getMessages().configurazione_flussi_detail_codTipoFlussoItem()); 
		codTipoFlussoItem.setWidth(300);
		codTipoFlussoItem.setStartRow(true);
		codTipoFlussoItem.setOptionDataSource(codTipoFlussoDS);		
		codTipoFlussoItem.setValueField("key");
		codTipoFlussoItem.setDisplayField("value");
		codTipoFlussoItem.setRequired(true);		
		
		idTaskItem = new TextItem("idTask",  I18NUtil.getMessages().configurazione_flussi_detail_idTaskItem());
		idTaskItem.setWidth(300);
		idTaskItem.setStartRow(true);
		idTaskItem.setAttribute("obbligatorio", true);
		idTaskItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTaskDetail();
			}
		}));
		idTaskItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isTaskDetail();
			}
		});
		
		nomeTaskItem = new TextItem("nomeTask",  I18NUtil.getMessages().configurazione_flussi_detail_nomeTaskItem());
		nomeTaskItem.setWidth(300);
		nomeTaskItem.setStartRow(true);
		nomeTaskItem.setAttribute("obbligatorio", true);
		nomeTaskItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isTaskDetail();
			}
		}));
		nomeTaskItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isTaskDetail();
			}
		});
		
		idFaseOldItem = new HiddenItem("idFaseOld");
		
		idFaseItem = new TextItem("idFase",  I18NUtil.getMessages().configurazione_flussi_detail_idFaseItem()); 
		idFaseItem.setWidth(300);
		idFaseItem.setRequired(true);
		idFaseItem.setStartRow(true);
				
		numeroOrdineItem = new NumericItem("numeroOrdine", I18NUtil.getMessages().configurazione_flussi_detail_numeroOrdineItem());
		numeroOrdineItem.setWidth(100);
		numeroOrdineItem.setRequired(true);
		numeroOrdineItem.setStartRow(true);
		
		mainForm.setFields(codTipoFlussoItem, idTaskItem, nomeTaskItem, idFaseOldItem, idFaseItem, numeroOrdineItem);
		
		// FORM ACL
		aclForm = new DynamicForm();
		aclForm.setValuesManager(vm);
		aclForm.setWidth("100%");  
		aclForm.setHeight("5");  
		aclForm.setPadding(5);
		aclForm.setNumCols(5);
		aclForm.setWrapItemTitles(false);
		
		listaAclItem = new PermessiConfigFlussiItem() {
			
			@Override
			public String getCodTipoFlusso() {
				return codTipoFlussoItem.getValueAsString();
			}			
		};
		listaAclItem.setName("listaAcl");
		listaAclItem.setShowTitle(false);
		
		aclForm.setItems(listaAclItem);
		
		aclSection  = new DetailSection(I18NUtil.getMessages().configurazione_flussi_detail_aclSection_title(), true, true,  false, aclForm) {
			
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
		
		// FORM NOMI ATTRIBUTI ADD EDITABILI
		attributiAddEditabiliForm = new DynamicForm();
		attributiAddEditabiliForm.setValuesManager(vm);
		attributiAddEditabiliForm.setWidth("100%");  
		attributiAddEditabiliForm.setHeight("5");  
		attributiAddEditabiliForm.setPadding(5);
		attributiAddEditabiliForm.setNumCols(5);
		attributiAddEditabiliForm.setWrapItemTitles(false);
		
		flgNessunoItem = new CheckboxItem("flgNessuno", "nessuno");
		flgNessunoItem.setColSpan(1);
		flgNessunoItem.setWidth(50);
		flgNessunoItem.setEndRow(true);
		flgNessunoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				attributiAddEditabiliForm.redraw();
			}
		});
		
		listaAttributiAddEditabiliItem = new AttributiAddEditabiliItem() {
			
			@Override
			public String getCodTipoFlusso() {
				return codTipoFlussoItem.getValueAsString();
			}
			
			@Override
			public String getNomeTask() {
				return nomeTaskItem.getValueAsString();
			}
			
		};
		listaAttributiAddEditabiliItem.setName("listaAttributiAddEditabili");
		listaAttributiAddEditabiliItem.setShowTitle(false);
		listaAttributiAddEditabiliItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean flgNessuno = flgNessunoItem.getValueAsBoolean() != null && flgNessunoItem.getValueAsBoolean();
				return !flgNessuno; 
			}
		});
		
		attributiAddEditabiliForm.setItems(flgNessunoItem, listaAttributiAddEditabiliItem);
		
		attributiAddEditabiliSection  = new DetailSection(I18NUtil.getMessages().configurazione_flussi_detail_attributiAddEditabiliSection_title(), true, true,  false, attributiAddEditabiliForm) {
			
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		lVLayout.addMember(mainForm);
		lVLayout.addMember(aclSection);
		lVLayout.addMember(attributiAddEditabiliSection);
		addMember(lVLayout);
		addMember(lVLayoutSpacer);					
	}
	
	@Override
	public void editNewRecord() {		
		super.editNewRecord();
		if (attributiAddEditabiliSection != null) {
			attributiAddEditabiliSection.hide(); // è una fase
		}
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		if (attributiAddEditabiliSection != null) {
			attributiAddEditabiliSection.hide(); // è una fase
		}
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		if (attributiAddEditabiliSection != null) {
			if(isTaskDetail()) {
				attributiAddEditabiliSection.show(); // è un task
			} else {
				attributiAddEditabiliSection.hide(); // è una fase
			}
		}
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
		if (aclSection != null) {
			aclSection.open();			
		}
		if (attributiAddEditabiliSection != null) {
			attributiAddEditabiliSection.open();			
		}	
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		codTipoFlussoItem.setCanEdit(false);		
		idTaskItem.setCanEdit(false);
		nomeTaskItem.setCanEdit(false);
	}
	
	public boolean isTaskDetail() {
		return idTaskItem.getValue() != null && !"".equals(idTaskItem.getValue()); 
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = new Record();
		addFormValues(lRecordToSave, mainForm);
		addFormValues(lRecordToSave, aclForm);
		addFormValues(lRecordToSave, attributiAddEditabiliForm);
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
