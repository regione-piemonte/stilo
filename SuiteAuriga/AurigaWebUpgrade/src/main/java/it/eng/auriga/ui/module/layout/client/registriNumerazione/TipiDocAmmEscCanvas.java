/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class TipiDocAmmEscCanvas extends ReplicableCanvas {
	
	// ReplicableCanvasForm
	protected ReplicableCanvasForm mDynamicForm;

	// HiddenItem 
	protected HiddenItem descTipoDocumentoItem;

	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay tipoDocumentoItem;	
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		descTipoDocumentoItem = new HiddenItem("descTipoDocumento");
		
		tipoDocumentoItem = createFilteredSelectItemWithDisplay("idTipoDocumento",
                                                               "Tipol. documentale", 
                                                               "LoadComboTipoDocumentoDataSource", 
                                                               new String[] {"descTipoDocumento"}, 
                                                               new String[] {"idTipoDocumento"}, 
                                                               new String[] {"Denominazione"},                
                                                               new Object[]{735}, 
                                                               740, 
                                                               "idTipoDocumento", 
                                                               "descTipoDocumento");                 
		tipoDocumentoItem.setAllowEmptyValue(true);    	
		tipoDocumentoItem.setFilterLocally(true);
		tipoDocumentoItem.setAutoFetchData(true);
		tipoDocumentoItem.setFetchMissingValues(false);	
		tipoDocumentoItem.setShowTitle(false);

		mDynamicForm.setFields(tipoDocumentoItem);	
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
		if(record.getAttribute("idTipoDocumento") != null && !"".equals(record.getAttributeAsString("idTipoDocumento"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idTipoDocumento"), record.getAttribute("descTipoDocumento"));
			tipoDocumentoItem.setValueMap(valueMap);										
		} 
	}

  private FilteredSelectItemWithDisplay createFilteredSelectItemWithDisplay(String name,
                                                                            String title, 
                                                                            String datasourceName, 
                                                                            String[] campiVisibili,
                                                                            String[] campiHidden, 
                                                                            String[] descrizioni, 
                                                                            Object[] width,
                                                                            int widthTotale, 
                                                                            String idField, 
                                                                            String displayField) {
	  
         SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
    
         FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = new FilteredSelectItemWithDisplay(name, title, lGwtRestDataSource) {

             @Override
             public void onOptionClick(Record record) {
            	 super.onOptionClick(record);
                 manageOnOptionClick(getName(), record);
             }

             @Override
             protected void clearSelect() {
                super.clearSelect();
                manageClearSelect(getName());
             }               
         };

         int i = 0;
         List<ListGridField> lList = new ArrayList<ListGridField>();
         for (String lString : campiVisibili) {
             ListGridField field = new ListGridField(lString, descrizioni[i]);
             if (width[i] instanceof String) {
                field.setWidth((String) width[i]);
             } 
             else
                field.setWidth((Integer) width[i]);

             i++;

             lList.add(field);
         }

         for (String lString : campiHidden) {
              ListGridField field = new ListGridField(lString, lString);
              field.setHidden(true);
              lList.add(field);
         }

         lFilteredSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[] {}));
         lFilteredSelectItemWithDisplay.setFilterLocally(true);
         lFilteredSelectItemWithDisplay.setValueField(idField);
         lFilteredSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource);
         lFilteredSelectItemWithDisplay.setWidth(widthTotale);
         lFilteredSelectItemWithDisplay.setRequired(false);
         lFilteredSelectItemWithDisplay.setClearable(true);
         lFilteredSelectItemWithDisplay.setShowIcons(true);
         lFilteredSelectItemWithDisplay.setDisplayField(displayField);  
         lFilteredSelectItemWithDisplay.setCachePickListResults(false);      
         return lFilteredSelectItemWithDisplay;
     }

    private void manageOnOptionClick(String name, Record record){	
        if (name.equals("idTipoDocumento")) {
            mDynamicForm.setValue("descTipoDocumento", record.getAttributeAsString("descTipoDocumento"));	
        }
    }
  
	private void manageClearSelect(String name){		
		if (name.equals("idTipoDocumento")){			
			tipoDocumentoItem.clearValue();
			mDynamicForm.setValue("idTipoDocumento", (String)null);			
			mDynamicForm.setValue("descTipoDocumento", (String)null);
		}		
	}		
}