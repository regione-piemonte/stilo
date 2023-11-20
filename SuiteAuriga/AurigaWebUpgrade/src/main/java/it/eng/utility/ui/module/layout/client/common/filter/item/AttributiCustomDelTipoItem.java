/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class AttributiCustomDelTipoItem extends CustomItem {
	
	private CustomItemFormField tipo;
	private CustomItemFormField desTipo;
	
	public AttributiCustomDelTipoItem(JavaScriptObject jsObject) {
		super(jsObject);
	}

	public AttributiCustomDelTipoItem(){
		super();
	}
		
	public AttributiCustomDelTipoItem(final Map<String, String> property){
		super(property);
	}
	
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new AttributiCustomDelTipoItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	protected void editRecord(Record record) {		
		if(record.getAttribute("tipo") != null && !"".equals(record.getAttribute("tipo")) && 
		   record.getAttribute("desTipo") != null && !"".equals(record.getAttribute("desTipo"))) {
			LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
			tipoValueMap.put(record.getAttribute("tipo"), record.getAttribute("desTipo"));
			tipo.setValueMap(tipoValueMap);
		}		
		super.editRecord(record);
		_instance.storeValue(record);		
	}
 
	@Override
	public CustomItemFormField[] getFormFields() {

		String nomeTabella = property.get("nomeTabella");
		
		final String pkFieldName;
		final String displayFieldName;
		GWTRestDataSource tipoDS = null;
		if(nomeTabella == null || "".equals(nomeTabella)) {
			return new CustomItemFormField[0];
		} else if(nomeTabella.equalsIgnoreCase("DMT_FOLDER")) {
			pkFieldName = "idFolderType";
			displayFieldName = "descFolderType";
			tipoDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
			tipoDS.addParam("isFromFilter", "true");
//			tipoDS.addParam("idClassifica", property.get("idClassifica"));
//			tipoDS.addParam("idFolderApp", property.get("idFolderApp"));
//			tipoDS.addParam("idFolderType", property.get("idFolderType"));
		} else if(nomeTabella.equalsIgnoreCase("DMT_DOCUMENTS")) {
			pkFieldName = "idTipoDocumento";
			displayFieldName = "descTipoDocumento";
			tipoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT);
			tipoDS.addParam("isFromFilter", "true");
			tipoDS.addParam("isSoloDaPubblicare", property.get("isSoloDaPubblicare"));
			if(property.get("isArchivioPregresso") != null) {
				tipoDS.addParam("isArchivioPregresso", property.get("isArchivioPregresso"));
			}
//			tipoDS.addParam("categoriaReg", property.get("categoriaReg"));
//			tipoDS.addParam("siglaReg", property.get("siglaReg"));
		} else {
			return new CustomItemFormField[0];
		}
				
		tipo = new CustomItemFormField("tipo", this);
		SelectItem tipoEditorType = new SelectItem("tipo"){
			@Override
			public void onOptionClick(Record record) {
				String tipoOld = _form.getValueAsString("tipo");
				String tipo = record.getAttribute(pkFieldName);
				String desTipo = record.getAttribute(displayFieldName);
				_form.setValue("tipo", tipo);	
				_form.setValue("desTipo", desTipo);	
				if(tipoOld != null && tipo != null && !tipo.equals(tipoOld)) {
					_form.setValue("filtriCustom", "");
					_form.setValue("displayFiltriCustom", "");
				}				
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));					
			}
			
			@Override
			public void setValue(String value) {
				if (value == null || "".equals(value)) {
					_form.setValue("tipo", "");
					_form.setValue("desTipo", "");	
					_form.setValue("filtriCustom", "");
					_form.setValue("displayFiltriCustom", "");
					_form.markForRedraw();
					_instance.storeValue(new Record(_form.getValues()));
				}				
			}

			@Override
			protected void clearSelect() {
				_form.setValue("tipo", "");
				_form.setValue("desTipo", "");	
				_form.setValue("filtriCustom", "");
				_form.setValue("displayFiltriCustom", "");
				_form.markForRedraw();
				_instance.storeValue(new Record(_form.getValues()));
			};
		};
		tipoEditorType.setWidth(439);
		tipoEditorType.setValueField(pkFieldName);
		tipoEditorType.setDisplayField(displayFieldName);	        
		tipoEditorType.setOptionDataSource(tipoDS);  	
		tipoEditorType.setAllowEmptyValue(false);					
		tipoEditorType.setClearable(true);		
		tipoEditorType.setAutoFetchData(false);
		tipoEditorType.setFetchMissingValues(true);
		tipoEditorType.setAlwaysFetchMissingValues(true);
		tipo.setEditorType(tipoEditorType);
		
		desTipo = new CustomItemFormField("desTipo", this);
		desTipo.setEditorType(new HiddenItem());			

		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			
			CustomItemFormField filtriCustom = new CustomItemFormField("filtriCustom", this);
			filtriCustom.setEditorType(new HiddenItem());	
			
			CustomItemFormField displayFiltriCustom = new CustomItemFormField("displayFiltriCustom", this);
			displayFiltriCustom.setEditorType(new HiddenItem());	
			
			CustomItemFormField tipiAttributiCustom = new CustomItemFormField("tipiAttributiCustom", this);
			tipiAttributiCustom.setEditorType(new HiddenItem());	

			CustomItemFormField setFiltriCustomButton = new CustomItemFormField("setFiltriCustom", this);
			ImgButtonItem setFiltriCustomButtonEditorType = new ImgButtonItem("setFiltriCustomButton", "buttons/filtri_custom.png", "Imposta filtri avanzati");
			setFiltriCustomButtonEditorType.setAlign(Alignment.CENTER);
			setFiltriCustomButtonEditorType.setAlwaysEnabled(true);
			setFiltriCustomButtonEditorType.setIconWidth(16);
			setFiltriCustomButtonEditorType.setIconHeight(16);
			setFiltriCustomButtonEditorType.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
					Record record = new Record();
					record.setAttribute("nomeTabella", property.get("nomeTabella"));				
					record.setAttribute("tipoEntita", _form.getValue("tipo"));
					lGwtRestService.executecustom("getAttributiDinamiciXRicerca", record, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record object = response.getData()[0];
								RecordList attributiXRicerca = object.getAttributeAsRecordList("attributiXRicerca");
								if (attributiXRicerca != null && !attributiXRicerca.isEmpty()) {
									final Map<String, String> mappaTipiAttributiCustom = new HashMap<String, String>();
									for(int i = 0; i < attributiXRicerca.getLength(); i++) {
										Record attr = attributiXRicerca.get(i);
										mappaTipiAttributiCustom.put(attr.getAttribute("nome"), attr.getAttribute("tipo"));
									}								
									FiltriCustomDelTipoWindow lFiltriCustomTipoFolderWindow = new FiltriCustomDelTipoWindow("filtri_custom_" + property.get("nomeTabella"), attributiXRicerca, _form.getValueAsString("displayFiltriCustom"), new ServiceCallback<Record>() {
		
										@Override
										public void execute(Record record) {
											_form.setValue("filtriCustom", record.getAttribute("filtriCustom"));
											_form.setValue("displayFiltriCustom", record.getAttribute("displayFiltriCustom"));										
											_form.setValue("tipiAttributiCustom", mappaTipiAttributiCustom);										
											_form.markForRedraw();
											_instance.storeValue(new Record(_form.getValues()));
										}					
									});
									lFiltriCustomTipoFolderWindow.show();
								} else {
									Layout.addMessage(new MessageBean("Nessun filtro associato al tipo selezionato!", "", MessageType.INFO));
								}
							}
						}
					});			
				}
			});
			setFiltriCustomButtonEditorType.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					boolean ret = false;
					if ( _form.getValue("tipo") != null && !"".equals((String) _form.getValue("tipo")) ){
						ret = true;
						String val = (String) _form.getValue("tipo");
						// Se nella lista e' presente il valore "non tipizzato" (-1) non mostro l'icona imputo 
						// n.b. L'id viene settato nel LoadComboTipoDocumentoDataSource
						if (val.equalsIgnoreCase("-1")){
							ret = false;
						}
					}										
					return ret;
				}
			});
			setFiltriCustomButton.setEditorType(setFiltriCustomButtonEditorType);
			
			return new CustomItemFormField[] { tipo, desTipo, filtriCustom, displayFiltriCustom, tipiAttributiCustom, setFiltriCustomButton };
			
		} 
		
		return new CustomItemFormField[] { tipo };
	}
}