/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;


public class ClassificazioneItem extends CustomItem {
	
	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	
	private HiddenItem livelliClassificazioneItem;
	private HiddenItem descrizioneClassificazioneItem;
	private FilteredSelectItemWithDisplay classificheItem;
	private CheckboxItem flgIncludiSottoClassificheItem;
	
	private CustomItemFormField livelliClassificazioneCustomItemFormField;
	private CustomItemFormField descrizioneClassificazioneCustomItemFormField;
	private CustomItemFormField classificheCustomItemFormField;
	private CustomItemFormField flgIncludiSottoClassificheCustomItemFormField;
	
	public ClassificazioneItem(JavaScriptObject jsObj, ConfigurableFilter filter, final DataSourceField field) {
		super(jsObj);
		_filter = filter;
		_field = field;
	}

	public ClassificazioneItem(ConfigurableFilter filter, final DataSourceField field) {
		super();
		_filter = filter;
		_field = field;
	}
	
	protected FormItem getItem(String fieldName) {
	   return _form.getItem(fieldName);
	} 
	
	@Override
	protected void editRecord(Record record) {
		if (record.getAttribute("classifiche") != null && !"".equals(record.getAttributeAsString("classifiche"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("classifiche"), record.getAttribute("livelliClassificazione") + " ** " + record.getAttribute("descrizioneClassificazione"));
			classificheCustomItemFormField.setValueMap(valueMap);
		}
		super.editRecord(record);
		_instance.storeValue(record);
	}
	   
//	@Override
//	protected void disegna(Object value) {					
//		super.disegna(value);
//		if (value != null && value instanceof JavaScriptObject) {			
//			Record record = new Record((JavaScriptObject) value);			
//			if (record.getAttribute("classifiche") != null && !"".equals(record.getAttributeAsString("classifiche"))) {
//				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
//				valueMap.put(record.getAttribute("classifiche"), record.getAttribute("livelliClassificazione") + " ** " + record.getAttribute("descrizioneClassificazione"));
//				classificheItem.setValueMap(valueMap);
//			}
//		}	
//	}

	@Override
	public CustomItemFormField[] getFormFields() {
		
		createHiddenItems();

		createClassificheItem();
		
		createFlgIncludiSottoClassificheItem();
		
		return new CustomItemFormField[] { 
				// Nascosti
				livelliClassificazioneCustomItemFormField,
		        descrizioneClassificazioneCustomItemFormField,			       
		        // Visibili
		        classificheCustomItemFormField,
		        flgIncludiSottoClassificheCustomItemFormField 				                           		                           
		};		
	}

	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		ClassificazioneItem lFilterCanvasItem = new ClassificazioneItem(jsObj, _filter, _field);
		return lFilterCanvasItem;
	}
	
	protected void createHiddenItems() {

		livelliClassificazioneCustomItemFormField = new CustomItemFormField("livelliClassificazione", this);
		livelliClassificazioneItem = new HiddenItem();
		livelliClassificazioneCustomItemFormField.setEditorType(livelliClassificazioneItem);

		descrizioneClassificazioneCustomItemFormField = new CustomItemFormField("descrizioneClassificazione", this);
		descrizioneClassificazioneItem = new HiddenItem();
		descrizioneClassificazioneCustomItemFormField.setEditorType(descrizioneClassificazioneItem);
	}
	
	protected void createClassificheItem() {
		
		classificheItem = createClassificheFilteredSelectItem();
//		classificheItem.setAllowEmptyValue(true);
		classificheItem.setWidth(458);
		classificheItem.setStartRow(false);
		classificheItem.setShowTitle(false);
		
		classificheCustomItemFormField = new CustomItemFormField("classifiche", "Classifica", this);
		classificheCustomItemFormField.setEditorType(classificheItem);
		classificheCustomItemFormField.setVisible(true);	
	}

	protected void createFlgIncludiSottoClassificheItem() {
		
		flgIncludiSottoClassificheItem = new CheckboxItem();
		flgIncludiSottoClassificheItem.setTitle("incluse sotto-classifiche");
		flgIncludiSottoClassificheItem.setWidth(50);
	
		flgIncludiSottoClassificheCustomItemFormField = new CustomItemFormField("flgIncludiSottoClassifiche", this);
		flgIncludiSottoClassificheCustomItemFormField.setEditorType(flgIncludiSottoClassificheItem);
	}
	
	private FilteredSelectItemWithDisplay createClassificheFilteredSelectItem(){
		
        SelectGWTRestDataSource lLoadComboClassificaFilterDataSource = new SelectGWTRestDataSource("LoadComboClassificaFilterDataSource", "idClassifica", FieldType.TEXT, new String[] { "indice", "descrizione" }, true);
        FilteredSelectItemWithDisplay lClassificheFilteredSelectItemWithDisplay = new FilteredSelectItemWithDisplay("classifiche", "Classificazione", lLoadComboClassificaFilterDataSource){
           
        	@Override
        	protected ListGrid builPickListProperties() {
        		ListGrid pickListProperties = super.builPickListProperties();
        		pickListProperties.setEmptyMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());
        		return pickListProperties;
        	}
        	@Override
            public void onOptionClick(Record record) {
        		super.onOptionClick(record);	
                manageOnOptionClick(record);                                                       
            }

            @Override
            protected void clearSelect() {
                super.clearSelect();	
                manageClearSelect();
            }
            
            @Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					 manageClearSelect();
				}
			}
            
            @Override
            public void doOnFilterEditorSubmit(FilterEditorSubmitEvent event, final FilteredSelectItem item) {
            	int pos = _filter.getClausePositionByFieldName(_field.getName());
				if(pos != -1) {
					final ClassificazioneItem lClassificazioneItem = (ClassificazioneItem) _filter.getClauseValueItem(pos);
					final SelectItem selectItem = SelectItem.getOrCreateRef(lClassificazioneItem.getItem("classifiche").getJsObj()); 
					selectItem.setPickListCriteria(event.getCriteria());
					event.cancel();				
					selectItem.fetchData(new DSCallback() {
						
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							selectItem.setPickListCriteria((Criteria)null);
						}
					});
				}					
            }
        };
        
        ListGridField idClassificaField = new ListGridField("idClassifica", "Id.");
        idClassificaField.setHidden(true);        
        ListGridField indiceField = new ListGridField("indice", "Indice");
        indiceField.setWidth(100);
        indiceField.setCanFilter(true);        
        ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
        descrizioneField.setWidth("*");
        descrizioneField.setCanFilter(true);        
        lClassificheFilteredSelectItemWithDisplay.setPickListFields(idClassificaField, indiceField, descrizioneField);		
        
        lClassificheFilteredSelectItemWithDisplay.setAutoFetchData(false);
        lClassificheFilteredSelectItemWithDisplay.setFetchMissingValues(true);        	      
//      lClassificheFilteredSelectItemWithDisplay.setFilterLocally(true);
//      lClassificheFilteredSelectItemWithDisplay.setCachePickListResults(false);     
        
        lClassificheFilteredSelectItemWithDisplay.setValueField("idClassifica");  		
        lClassificheFilteredSelectItemWithDisplay.setOptionDataSource(lLoadComboClassificaFilterDataSource); 
        lClassificheFilteredSelectItemWithDisplay.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());        
        lClassificheFilteredSelectItemWithDisplay.setHideEmptyPickList(false);
        lClassificheFilteredSelectItemWithDisplay.setRequired(false);
        lClassificheFilteredSelectItemWithDisplay.setClearable(true);
        lClassificheFilteredSelectItemWithDisplay.setShowIcons(true);
       
        return lClassificheFilteredSelectItemWithDisplay;
   }

   private void manageOnOptionClick(Record record){
	   
	   _form.setValue("livelliClassificazione", record.getAttribute("indice"));
	   _form.setValue("descrizioneClassificazione", record.getAttribute("descrizione"));
	   
	   	_instance.storeValue(new Record(_form.getValues()));
		_form.markForRedraw();
		
//	    SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
//		classificheDS.addParam("descrizione", record.getAttributeAsString("descrizione"));
//		classificheItem.setOptionDataSource(classificheDS);
   }
   
  private void manageClearSelect(){
	   
	   	_form.clearValue("classifiche");
	   	_form.clearValue("livelliClassificazione");
	   	_form.clearValue("descrizioneClassificazione");
		
		_instance.storeValue(new Record(_form.getValues()));
		_form.markForRedraw();
		
//		SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
//		classificheDS.addParam("descrizione", null);
//		classificheItem.setOptionDataSource(classificheDS);
	}
   
}
