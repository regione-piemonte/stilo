/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class CatalogoItem extends CustomItem {

	//DynamicForm _form;
	private ConfigurableFilter mFilter;
	
	private CustomItemFormField processoPrimarioCatalogoField;
	private SelectItem processoPrimarioCatalogoItem;	
	
	private CustomItemFormField categoriaField;
	private SelectItem categoriaItem;
	
	private CustomItemFormField sottoCategoriaField;
	private SelectItem sottoCategoriaItem;	
	
	private CustomItemFormField prestazioneField;
	private SelectItem prestazioneItem;
	
	private CustomItemFormField attivitaField;
	private SelectItem attivitaItem;

	private GWTRestDataSource categoriaDS;
	private GWTRestDataSource sottoCategoriaDS;
	
	private GWTRestDataSource prestazioneDS;
	private GWTRestDataSource attivitaDS;
	
	private GWTRestDataSource processoPrimarioDS;
	
	public CatalogoItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public CatalogoItem(){
		super();
	}
		
	public CatalogoItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new CatalogoItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		
		createProcessoPrimario();
		
		createCategoria();
		
		createSottoCategoria();
		
		creaPrestazione();
				
		creaAttivita();
		 
		return new CustomItemFormField[]{processoPrimarioCatalogoField, categoriaField  ,sottoCategoriaField, prestazioneField, attivitaField};
	}


	
	protected void createProcessoPrimario() {
		
		processoPrimarioCatalogoItem = new SelectItem("processoPrimarioCatalogoSelect");	
		processoPrimarioDS = new SelectGWTRestDataSource("ProcessiPrimariDataSource");
		processoPrimarioCatalogoItem.setOptionDataSource(processoPrimarioDS);	
		processoPrimarioCatalogoItem.setDisplayField("value");
		processoPrimarioCatalogoItem.setValueField("idProcesso");
		processoPrimarioCatalogoItem.setAllowEmptyValue(true);
		processoPrimarioCatalogoItem.setWidth(550);
		processoPrimarioCatalogoItem.setTextAlign(Alignment.LEFT);
		processoPrimarioCatalogoItem.setColSpan(3);
		processoPrimarioCatalogoField = new CustomItemFormField(processoPrimarioCatalogoItem, "Processo primario", this);		
		processoPrimarioCatalogoField.setEditorType(processoPrimarioCatalogoItem);		
		processoPrimarioCatalogoField.setVisible(true);

		processoPrimarioCatalogoField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String idProcessoPrimario = null;
				
				if(event.getItem().getValue()!=null){
					idProcessoPrimario = event.getForm().getValueAsString("processoPrimarioCatalogoSelect");
					
					if (categoriaField.isVisible()){
						categoriaField.clearValue();
					} else {
						categoriaField.show();
					}		
				}
				else{
					categoriaField.clearValue();
					categoriaField.hide();
				}
					
				sottoCategoriaField.clearValue();
				sottoCategoriaField.hide();
				
				attivitaField.clearValue();
				attivitaField.hide();
				
				prestazioneField.clearValue();
				prestazioneField.hide();
				
				categoriaDS = (GWTRestDataSource) categoriaItem.getOptionDataSource();
				categoriaDS.addParam("idProcessoPrimario", idProcessoPrimario);
				categoriaItem.setOptionDataSource(categoriaDS);	
				
								
				sottoCategoriaDS = (GWTRestDataSource) sottoCategoriaItem.getOptionDataSource();
				sottoCategoriaDS.addParam("idProcessoPrimario", idProcessoPrimario);
				sottoCategoriaItem.setOptionDataSource(sottoCategoriaDS);	
				
				prestazioneDS = (GWTRestDataSource) prestazioneItem.getOptionDataSource();
				prestazioneDS.addParam("idProcessoPrimario", idProcessoPrimario);
				prestazioneItem.setOptionDataSource(prestazioneDS);	
				
				attivitaDS = (GWTRestDataSource) attivitaItem.getOptionDataSource();
				attivitaDS.addParam("idProcessoPrimario", idProcessoPrimario);
				attivitaItem.setOptionDataSource(attivitaDS);	
				
			}
		});
	}
	
	
	protected void createCategoria() {
		categoriaItem = new SelectItem("categoriaSelect");	
		categoriaDS = new SelectGWTRestDataSource("CategoriaDataSource");
		categoriaItem.setOptionDataSource(categoriaDS);
		categoriaItem.setDisplayField("value");
		categoriaItem.setValueField("idCategoria");
		categoriaItem.setAllowEmptyValue(true);
		categoriaItem.setWidth(300);
		categoriaItem.setStartRow(true);
						
		categoriaItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				
				Criteria lAdvancedCriteria = new Criteria();
				lAdvancedCriteria.addCriteria(new Criteria("idProcessoPrimario",    (String)_form.getValue("processoPrimarioCatalogoSelect")));	
				return lAdvancedCriteria.asAdvancedCriteria();
			}
		});
		
		categoriaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return form.getValue("processoPrimarioCatalogoSelect")!=null && !form.getValueAsString("processoPrimarioCatalogoSelect").trim().equals("");
				
			}
		});
		
		
		categoriaField = new CustomItemFormField(categoriaItem, "Categoria", this);		
		categoriaField.setEditorType(categoriaItem);
		categoriaField.setVisible(false);
		
		categoriaField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getItem().getValue()!=null){
					if (sottoCategoriaField.isVisible()){
						sottoCategoriaField.clearValue();
					} else {
						sottoCategoriaField.show();
					}
					attivitaField.clearValue();
					attivitaField.hide();
					
					prestazioneField.clearValue();
					prestazioneField.hide();
				} else {
					prestazioneField.clearValue();
					prestazioneField.hide();
					
					attivitaField.clearValue();
					attivitaField.hide();
					
					sottoCategoriaField.clearValue();
					sottoCategoriaField.hide();
				}
			}
		});
	}
	

	
	private void createSottoCategoria() {
		sottoCategoriaItem = new SelectItem("sottoCategoriaSelect");
		sottoCategoriaDS = new SelectGWTRestDataSource("SottocategoriaDataSource");
		sottoCategoriaItem.setOptionDataSource(sottoCategoriaDS);		
		sottoCategoriaItem.setDisplayField("value");
		sottoCategoriaItem.setValueField("idSottocategoria");
		sottoCategoriaItem.setAllowEmptyValue(true);
		sottoCategoriaItem.setWidth(300);		
		sottoCategoriaItem.setStartRow(false);
		
		
		sottoCategoriaItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				
				Criteria lAdvancedCriteria = new Criteria();	
				lAdvancedCriteria.addCriteria(new Criteria("idProcessoPrimario",    (String)_form.getValue("processoPrimarioCatalogoSelect")));	
				lAdvancedCriteria.addCriteria(new Criteria("idCategoria",   (String)_form.getValue("categoriaSelect")));
				return lAdvancedCriteria.asAdvancedCriteria();
				
			}
		});
		
		
		sottoCategoriaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("categoriaSelect")!=null && !form.getValueAsString("categoriaSelect").trim().equals("");		
				
			}
		});
		
		
		sottoCategoriaField = new CustomItemFormField(sottoCategoriaItem, "Sottocategoria", this);
		sottoCategoriaField.setVisible(false);
		sottoCategoriaField.setEditorType(sottoCategoriaItem);
		
		sottoCategoriaField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				
				if (event.getItem().getValue()!=null){
					if (prestazioneField.isVisible()){
						prestazioneField.clearValue();
					} else {
						prestazioneField.show();
					}
					attivitaField.clearValue();
					attivitaField.hide();
				} else {
					attivitaField.clearValue();
					attivitaField.hide();
					
					prestazioneField.clearValue();
					prestazioneField.hide();
				}
			}
		});
			
		
		
	}

	private void creaPrestazione() {
		prestazioneItem = new SelectItem("prestazioneSelect");
		prestazioneDS = new SelectGWTRestDataSource("PrestazioneDataSource");
		prestazioneItem.setOptionDataSource(prestazioneDS);		
		prestazioneItem.setStartRow(true);
		prestazioneItem.setDisplayField("value");
		prestazioneItem.setValueField("idPrestazione");
		prestazioneItem.setAllowEmptyValue(true);
		prestazioneItem.setWidth(300);
				
		prestazioneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				
				Criteria lAdvancedCriteria = new Criteria();
				lAdvancedCriteria.addCriteria(new Criteria("idProcessoPrimario",    (String)_form.getValue("processoPrimarioCatalogoSelect")));				
				lAdvancedCriteria.addCriteria(new Criteria("idCategoria",           (String)_form.getValue("categoriaSelect")));
				lAdvancedCriteria.addCriteria(new Criteria("idSottocategoria",      (String)_form.getValue("sottoCategoriaSelect")));
				return lAdvancedCriteria.asAdvancedCriteria();
			}
		});
		prestazioneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("sottoCategoriaSelect")!=null && !form.getValueAsString("sottoCategoriaSelect").trim().equals(""); 	
			}
		});
		
		prestazioneField = new CustomItemFormField(prestazioneItem, "Prestazione", this);
		prestazioneField.setVisible(false);
		prestazioneField.setEditorType(prestazioneItem);
		
		prestazioneField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getItem().getValue()!=null){
					if (attivitaField.isVisible()){
						attivitaField.clearValue();
					} else {
						attivitaField.show();
					}
				} else {
					attivitaField.hide();
				}
			}
		});
	}
	
	private void creaAttivita() {
		attivitaItem = new SelectItem("attivitaSelect");
		attivitaDS = new SelectGWTRestDataSource("SinapoliAttivitaAmbientaleDataSource");
		attivitaItem.setOptionDataSource(attivitaDS);
		attivitaItem.setStartRow(false);
		attivitaItem.setDisplayField("value");
		attivitaItem.setValueField("idAttivita");
		attivitaItem.setAllowEmptyValue(true);
		attivitaItem.setWidth(300);
		
		attivitaItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criteria lAdvancedCriteria = new Criteria();
				lAdvancedCriteria.addCriteria(new Criteria("idProcessoPrimario",    (String)_form.getValue("processoPrimarioCatalogoSelect")));	
				lAdvancedCriteria.addCriteria(new Criteria("idCategoria",           (String)_form.getValue("categoriaSelect")));
				lAdvancedCriteria.addCriteria(new Criteria("idSottocategoria",      (String)_form.getValue("sottoCategoriaSelect")));				
				lAdvancedCriteria.addCriteria(new Criteria("idPrestazione",         (String)_form.getValue("prestazioneSelect")));
				return lAdvancedCriteria.asAdvancedCriteria();
			}
		});
		attivitaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("prestazioneSelect")!=null && !form.getValueAsString("prestazioneSelect").trim().equals("");
			
			}
		});
		
		attivitaField = new CustomItemFormField(attivitaItem, "Attivita", this);		
		attivitaField.setEditorType(attivitaItem);
		attivitaField.setVisible(false);
		
		attivitaField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				
				//if (((SelectItem)event.getItem()).getSelectedRecord()!=null){
				//	Record lRecord = ((SelectItem)event.getItem()).getSelectedRecord();
					// .....
				//}
			}
		});
	}
	
	public void setFilter(ConfigurableFilter filter) {
		mFilter = filter;
	}	
	
}