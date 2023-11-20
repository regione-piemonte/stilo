/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AreeCommercialiCanvas extends ReplicableCanvas{
	protected SelectItemWithDisplay areaCommercialiItem;	
	private ReplicableCanvasForm mDynamicForm;
	

	private HiddenItem denominazioneAreaCommercialiItem;
	
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		

		denominazioneAreaCommercialiItem       = new HiddenItem("denominazioneAreaCommerciali");
		
		

		areaCommercialiItem = createSelectItem("idAreaCommerciali",
				                              I18NUtil.getMessages().gestioneutenti_combo_areecommerciali_title(), 
				                              "LoadComboAreeCommercialiDataSource", 
				                              new String[] { "denominazioneAreaCommerciali"}, 
				                              new String[]{"idAreaCommerciali"}, 
				                              new String[]{I18NUtil.getMessages().gestioneutenti_combo_areecommerciali_denominazioneItem_title()
			                                              }, 
				                              new Object[]{300}, 
				                              410, 
				                              "idAreaCommerciali", 
				                              "denominazioneAreaCommerciali",
				                              true);
		areaCommercialiItem.setAllowEmptyValue(false);
		
		areaCommercialiItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
									
				String idAreaCommerciali = "";
				if (((AreeCommercialiItem)getItem()).getIdAreaCommerciali() !=null){
					idAreaCommerciali = ((AreeCommercialiItem)getItem()).getIdAreaCommerciali();				
				}				
				SelectGWTRestDataSource societaDS = (SelectGWTRestDataSource) areaCommercialiItem.getOptionDataSource();
				societaDS.addParam("idAreaCommerciali", idAreaCommerciali);
				areaCommercialiItem.setOptionDataSource(societaDS);	
				return new Criteria("idAreaCommerciali", idAreaCommerciali);															
			}
		});	
		
		
		mDynamicForm.setFields(areaCommercialiItem,
				               denominazioneAreaCommercialiItem);	
							   
		mDynamicForm.setNumCols(2);		
		addChild(mDynamicForm);
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	
	@Override
	public void editRecord(Record record) {
				
		super.editRecord(record);
		
		//Inizializzo le combo con la descrizione restituita dal servizio		
		if(record.getAttribute("idAreaCommerciali") != null && !"".equals(record.getAttributeAsString("idAreaCommerciali"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idAreaCommerciali"), record.getAttribute("denominazioneAreaCommerciali"));
			areaCommercialiItem.setValueMap(valueMap);										
		} 
		
	}
	
	
	
		
	
	private void manageOnOptionClick(String name, Record record){	
		
		String idAreaCommerciali               = record.getAttributeAsString("idAreaCommerciali");
		String denominazioneAreaCommerciali  = record.getAttributeAsString("denominazioneAreaCommerciali");

		mDynamicForm.setValue("idAreaCommerciali",             idAreaCommerciali);	
        mDynamicForm.setValue("denominazioneAreaCommerciali",  denominazioneAreaCommerciali);	
		
		
	}	
	private void manageClearSelect(String name){
		
		if (name.equals("idAreaCommerciali")){			
			areaCommercialiItem.clearValue();
			areaCommercialiItem.setValue((String)null);
			mDynamicForm.setValue("idAreaCommerciali", (String)null);			
			denominazioneAreaCommercialiItem.setValue((String)null);
			mDynamicForm.setValue("denominazioneAreaCommerciali", (String)null);
		}
	}		
	
	private SelectItemWithDisplay createSelectItem(String name, 
                                                   String title,
                                                   String datasourceName, 
                                                   String[] campiVisibili, 
                                                   String[] campiHidden, 
                                                   String[] descrizioni, 
                                                   Object[] width,
                                                   int widthTotale, 
                                                   String idField, 
                                                   String displayField,
                                                   boolean isClearable){

        SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
        SelectItemWithDisplay lSelectItemWithDisplay = new SelectItemWithDisplay(name, title, lGwtRestDataSource){
            @Override
            public void onOptionClick(Record record) {
            	manageOnOptionClick(getName(), record);
            }
            @Override
            protected void clearSelect() {
            	super.clearSelect();	
            	manageClearSelect(getName());
            }
        };

        int i=0;

        List<ListGridField> lList = new ArrayList<ListGridField>();
        for (String lString : campiVisibili){
        	ListGridField field = new ListGridField(lString, descrizioni[i]);
        	if (width[i] instanceof String){
        		field.setWidth((String)width[i]);
        	} else field.setWidth((Integer)width[i]);
        	
        	i++;
        	lList.add(field);
        }
        for (String lString : campiHidden){
        	ListGridField field = new ListGridField(lString, lString);
        	field.setHidden(true);
        	lList.add(field);
        }
        lSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[]{}));		
        lSelectItemWithDisplay.setFilterLocally(true);
        lSelectItemWithDisplay.setValueField(idField);  		
        lSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource); 
        lSelectItemWithDisplay.setWidth(widthTotale);
        lSelectItemWithDisplay.setRequired(false);
        lSelectItemWithDisplay.setClearable(isClearable);
        lSelectItemWithDisplay.setShowIcons(isClearable);
        lSelectItemWithDisplay.setCachePickListResults(false);
        lSelectItemWithDisplay.setDisplayField(displayField);
        return lSelectItemWithDisplay;
	}
}
