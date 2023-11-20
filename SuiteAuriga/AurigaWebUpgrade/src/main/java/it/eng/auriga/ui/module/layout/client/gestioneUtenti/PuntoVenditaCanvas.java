/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class PuntoVenditaCanvas extends ReplicableCanvas {
	
	// ReplicableCanvasForm
	protected ReplicableCanvasForm mDynamicForm;

	// HiddenItem 
	protected HiddenItem denominazionePuntoVenditaItem;

	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay puntoVenditaItem;	
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		denominazionePuntoVenditaItem       = new HiddenItem("denominazionePuntoVendita");
		
		puntoVenditaItem = createFilteredSelectItemWithDisplay("idPuntoVendita",
                                                               I18NUtil.getMessages().puntivendita_combo_label(), 
                                                               "LoadComboPuntiVenditaDataSource", 
                                                               new String[] {"denominazionePuntoVendita" ,"cittaPuntoVendita" , "targaProvinciaPuntoVendita" , "capPuntoVendita" , "indirizzoPuntoVendita" ,"civicoIndirizzoPuntoVendita"     }, 
                                                               new String[]{"idPuntoVendita"}, 
                                                               new String[]{I18NUtil.getMessages().puntivendita_combo_denominazioneField()    ,
                                                                            I18NUtil.getMessages().puntivendita_combo_cittaField()          ,
                                                                            I18NUtil.getMessages().puntivendita_combo_targaProvinciaField() ,       
                                                                            I18NUtil.getMessages().puntivendita_combo_capField()            ,
                                                                            I18NUtil.getMessages().puntivendita_combo_indirizzoField()      ,            
                                                                            I18NUtil.getMessages().puntivendita_combo_civicoIndirizzoField()      
                                                                           },
                
                                                               new Object[]{210, 150, 40, 50, 200, 50}, 
                                                               740, 
                                                               "idPuntoVendita", 
                                                               "denominazionePuntoVendita");                 
		puntoVenditaItem.setAllowEmptyValue(true);    	
		puntoVenditaItem.setFilterLocally(true);
		puntoVenditaItem.setAutoFetchData(true);
		puntoVenditaItem.setFetchMissingValues(false);		

		mDynamicForm.setFields(puntoVenditaItem);	
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
		if(record.getAttribute("idPuntoVendita") != null && !"".equals(record.getAttributeAsString("idPuntoVendita"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idPuntoVendita"), record.getAttribute("denominazionePuntoVendita"));
			puntoVenditaItem.setValueMap(valueMap);										
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
        if (name.equals("idPuntoVendita")) {
            mDynamicForm.setValue("denominazionePuntoVendita", record.getAttributeAsString("denominazionePuntoVendita"));	
        }
    }
  
	private void manageClearSelect(String name){		
		if (name.equals("idPuntoVendita")){			
			puntoVenditaItem.clearValue();
			mDynamicForm.setValue("idPuntoVendita", (String)null);			
			mDynamicForm.setValue("denominazionePuntoVendita", (String)null);
		}		
	}		
}