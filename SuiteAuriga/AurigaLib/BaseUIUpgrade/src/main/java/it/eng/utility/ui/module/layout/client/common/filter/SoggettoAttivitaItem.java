/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;

public class SoggettoAttivitaItem extends CustomItem {

	private ConfigurableFilter mFilter;
	
	private CustomItemFormField tipoSoggettoField;
	private SelectItem tipoSoggettoItem;	

	private CustomItemFormField dittaField;
	private CustomItemFormField unitaLocaliField;
	private CustomItemFormField privatoField;
	private CustomItemFormField puntoIndagineField;
	private CustomItemFormField impiantoAiaField;
	
	FilteredSelectItemWithDisplay dittaSelectItem;
	FilteredSelectItemWithDisplay privatoSelectItem;
	FilteredSelectItemWithDisplay puntoIndagineSelectItem;	
	FilteredSelectItemWithDisplay impiantoAiaSelectItem;
	SelectItemWithDisplay unitaLocaliSelectItem;
	
	private SelectGWTRestDataSource unitaLocaliDS;
	
	public SoggettoAttivitaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public SoggettoAttivitaItem(){
		super();
	}
		
	public SoggettoAttivitaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new SoggettoAttivitaItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		createTipoSoggetto();
		createDitta();
		createUnitaLocali();
		createPrivato();
		createPuntoIndagine();
		createImpiantoAia();
		return new CustomItemFormField[]{tipoSoggettoField, dittaField  , unitaLocaliField, privatoField , puntoIndagineField, impiantoAiaField};
	}

	protected void createTipoSoggetto() {	
		tipoSoggettoItem = new SelectItem("tipoSoggetto");	
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("1", "Ditta");
		lLinkedHashMap.put("2", "Privato");
		lLinkedHashMap.put("3", "Punto indagine");
		lLinkedHashMap.put("4", "Impianti AIA");
		tipoSoggettoItem.setValueMap(lLinkedHashMap);
		tipoSoggettoItem.setStartRow(true);
		tipoSoggettoItem.setWidth(150);
		tipoSoggettoItem.setAllowEmptyValue(true);
		tipoSoggettoField = new CustomItemFormField(tipoSoggettoItem, "Tipo", this);		
		tipoSoggettoField.setEditorType(tipoSoggettoItem);		
		tipoSoggettoField.setVisible(true);
		tipoSoggettoField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {		
				if (privatoField.isVisible()){
					privatoField.clearValue();
					privatoSelectItem.setValue((String)null);
					_form.setValue("privatoSearch", (String)null);
					privatoField.hide();
				}
				if (dittaField.isVisible()){
					dittaField.clearValue();
					dittaSelectItem.setValue((String)null);
					_form.setValue("dittaSearch", (String)null);
					dittaField.hide();									
				}
				if (unitaLocaliField.isVisible()){
					unitaLocaliField.clearValue();
					unitaLocaliSelectItem.setValue((String)null);
					_form.setValue("unitaLocaliSearch", (String)null);
					unitaLocaliField.hide();									
				}
				if (puntoIndagineField.isVisible()){
					puntoIndagineField.clearValue();
					puntoIndagineSelectItem.setValue((String)null);
					_form.setValue("puntoIndagineSearch", (String)null);
					puntoIndagineField.hide();									
				}
				if (impiantoAiaField.isVisible()){
					impiantoAiaField.clearValue();
					impiantoAiaSelectItem.setValue((String)null);
					_form.setValue("impiantoAiaSearch", (String)null);
					impiantoAiaField.hide();									
				}
				if(event.getItem().getValue()!=null){
					if (event.getItem().getValue().equals("1")){						
						dittaField.show();	
					}
					if (event.getItem().getValue().equals("2")){
						privatoField.show();
					}		
					if (event.getItem().getValue().equals("3")){
						puntoIndagineField.show();
					}		
					if (event.getItem().getValue().equals("4")){
						impiantoAiaField.show();
					}		
				}
			}
		});
	}
	
	protected void createDitta() {
		dittaSelectItem = createFilteredSelectItemDitta();		
		dittaSelectItem.setAllowEmptyValue(true);		
		dittaSelectItem.setWidth(500);
		dittaSelectItem.setStartRow(true);
		dittaSelectItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return form.getValue("tipoSoggetto")!=null && form.getValueAsString("tipoSoggetto").trim().equals("1");				
			}
		});
		dittaField = new CustomItemFormField(dittaSelectItem, "Denominazione", this);		
		dittaField.setEditorType(dittaSelectItem);
		dittaField.setVisible(false);
		dittaField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String idDittaSelected = null;
				if (event.getItem().getValue()!=null){
					idDittaSelected = event.getForm().getValueAsString("dittaSearch");
					if (unitaLocaliField.isVisible()){
						unitaLocaliField.clearValue();
					} else {
						unitaLocaliField.show();
					}
				} else {
					unitaLocaliField.clearValue();
					unitaLocaliField.hide();
				}
				unitaLocaliDS = (SelectGWTRestDataSource) unitaLocaliSelectItem.getOptionDataSource();
				unitaLocaliDS.addParam("idDittaSelected", idDittaSelected);
				unitaLocaliSelectItem.setOptionDataSource(unitaLocaliDS);	
			}
		});
	}
	
	protected void createUnitaLocali() {		
		unitaLocaliSelectItem = createSelectItemUnitaLocali();	
		unitaLocaliSelectItem.setAllowEmptyValue(false);		
		unitaLocaliSelectItem.setWidth(700);
		unitaLocaliSelectItem.setStartRow(true);
		unitaLocaliSelectItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
								
				Criteria lAdvancedCriteria = new Criteria();	
				lAdvancedCriteria.addCriteria(new Criteria("idditta",   (String)_form.getValue("dittaSearch")));
				return lAdvancedCriteria.asAdvancedCriteria();
			}
		});		
		unitaLocaliSelectItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return form.getValue("dittaSearch")!=null && !form.getValueAsString("dittaSearch").trim().equals("");	
			}
		});
		unitaLocaliField = new CustomItemFormField(unitaLocaliSelectItem, "Unità locale", this);		
		unitaLocaliField.setEditorType(unitaLocaliSelectItem);
		unitaLocaliField.setVisible(false);	
	}

	protected void createPrivato() {
		privatoSelectItem = createFilteredSelectItemPrivato();
		privatoSelectItem.setAllowEmptyValue(true);
		privatoSelectItem.setWidth(500);
		privatoSelectItem.setStartRow(false);
		privatoSelectItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return form.getValue("tipoSoggetto")!=null && form.getValueAsString("tipoSoggetto").trim().equals("2");				
			}
		});
		privatoField = new CustomItemFormField(privatoSelectItem, "Rag.sociale", this);		
		privatoField.setEditorType(privatoSelectItem);
		privatoField.setVisible(false);				
	}
	
	protected void createPuntoIndagine() {
		puntoIndagineSelectItem = createFilteredSelectItemPuntoIndagine();		
		puntoIndagineSelectItem.setAllowEmptyValue(true);		
		puntoIndagineSelectItem.setWidth(600);
		puntoIndagineSelectItem.setStartRow(false);
		puntoIndagineSelectItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return form.getValue("tipoSoggetto")!=null && form.getValueAsString("tipoSoggetto").trim().equals("3");				
			}
		});
		puntoIndagineField = new CustomItemFormField(puntoIndagineSelectItem, "Punto", this);		
		puntoIndagineField.setEditorType(puntoIndagineSelectItem);
		puntoIndagineField.setVisible(false);	
	}
	
	protected void createImpiantoAia() {
		impiantoAiaSelectItem = createFilteredSelectItemImpiantoAia();		
		impiantoAiaSelectItem.setAllowEmptyValue(true);		
		impiantoAiaSelectItem.setWidth(600);
		impiantoAiaSelectItem.setStartRow(false);
		impiantoAiaSelectItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {						
				return form.getValue("tipoSoggetto")!=null && form.getValueAsString("tipoSoggetto").trim().equals("4");				
			}
		});
		impiantoAiaField = new CustomItemFormField(impiantoAiaSelectItem, "Impianto", this);		
		impiantoAiaField.setEditorType(impiantoAiaSelectItem);
		impiantoAiaField.setVisible(false);	
	}
	
	private FilteredSelectItemWithDisplay createFilteredSelectItemDitta() {
		FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = createFilteredSelectItem("dittaSearch", 
				                                                                                "Ditta", 
				                                                                                "SinadocDitteComboDatasource", 
				                                                                                new String[]{"ragionesociale","indirizzolegalerapp", "codicefiscale" , "piva"}, 
				                                                                                new String[]{"idditta"}, 
				                                                                                new String[]{"Rag.sociale","Ind.legale rapp.", "Cod.Fiscale","P.IVA"}, 
				                                                                                new Object[]{"*", "*", 70,70}, 
				                                                                                500, 
				                                                                                "idditta", 
				                                                                                "ragionesociale",
				                                                                                true);
		return lFilteredSelectItemWithDisplay;
	}
	
	private SelectItemWithDisplay createSelectItemUnitaLocali() {
		SelectItemWithDisplay lSelectItemWithDisplay = createSelectItem("unitaLocaliSearch", 
				                                                        "Unità locale", 
				                                                        "SinadocUnitaLocaliComboDatasource", 
				                                                        new String[]{"progressivo", "siglaProvincia", "comune", "indirizzo", "numerocivico", "codattivita" , "descrizioneAttivita" }, 
				                                                        new String[]{"idul"}, 
				                                                        new String[]{"Prog.",       "Prov.",          "Comune", "Indirizzo", "N°",           "Cod.attività", "Attività Economica"  }, 
				                                                        new Object[]{50,            50,               "*",      "*" ,        40,             70,             "*"}, 
				                                                        700, 
				                                                        "idul", 
				                                                        "displayValue",
				                                                        true);
		return lSelectItemWithDisplay;
	}
	
	private FilteredSelectItemWithDisplay createFilteredSelectItemPrivato() {
		FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = createFilteredSelectItem("privatoSearch", 
				                                                                                "Privato", 
				                                                                                "SinadocPersoneComboDatasource", 
				                                                                                new String[]{"cognome", "nome", "descr", "indirizzo", "numerociv", "codicefiscale"}, 
				                                                                                new String[]{"idpersona"}, 
				                                                                                new String[]{"Cognome","Nome", "Comune", "Indirizzo", "Nr.", "Cod.Fiscale"}, 
				                                                                                new Object[]{"*", "*", "*", "*", 20 ,  70}, 
				                                                                                500, 
				                                                                                "idpersona", 
				                                                                                "genericSearch",
				                                                                                true);
		return lFilteredSelectItemWithDisplay;
	}
	
	private FilteredSelectItemWithDisplay createFilteredSelectItemPuntoIndagine() {
		FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = createFilteredSelectItem("puntoIndagineSearch", 
				                                                                                "Punto", 
				                                                                                "SinadocPuntoIndagineComboDatasource", 
				                                                                                new String[]{"puntoprelievo","provincia" ,"comune" ,"localita" ,"indirizzo" ,"codstazione","codiceregionale" },
				                                                                                new String[]{"idditta"}, 
				                                                                                new String[]{"Punto prelievo", "Provincia", "Comune", "Località", "Indirizzo" , "Cod. Stazione", "Codice Reg."},
				                                                                                new Object[]{220, 130, 150, 170, 170, 100, 100},
				                                                                                600, 
				                                                                                "idpuntoprelievo", 
				                                                                                "puntoprelievo",
				                                                                                true);
		return lFilteredSelectItemWithDisplay;
	}
	
	private FilteredSelectItemWithDisplay createFilteredSelectItemImpiantoAia() {
		FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = createFilteredSelectItem("impiantoAiaSearch", 
				                                                                                "Impianto", 
				                                                                                "SinapoliImpiantiAiaDatasource", 
				                                                                                new String[]{"ragionesociale",   "nomeimpianto" , "provincia" , "comune",  "indirizzo" , "codicefiscale" },                 
				                                                                                new String[]{"idditta"}, 
				                                                                                new String[]{"Azienda Gestore",  "Impianto",      "Prov.",      "Comune",  "Indirizzo" , "Cod. Fiscale"  },
				                                                                                new Object[]{250, 160, 100, 150, 150, 80},
				                                                                                600, 
				                                                                                "idimpianto", 
				                                                                                "nomeimpianto",
				                                                                                true);
		return lFilteredSelectItemWithDisplay;
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
		SelectItemWithDisplay lSelectItemWithDisplay = 
			new SelectItemWithDisplay(name, title, lGwtRestDataSource){
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
	private FilteredSelectItemWithDisplay createFilteredSelectItem(String name, 
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
		FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = 
			new FilteredSelectItemWithDisplay(name, title, lGwtRestDataSource){
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
		lFilteredSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[]{}));		
		lFilteredSelectItemWithDisplay.setFilterLocally(true);
		lFilteredSelectItemWithDisplay.setValueField(idField);  		
		lFilteredSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource); 
		lFilteredSelectItemWithDisplay.setWidth(widthTotale);
		lFilteredSelectItemWithDisplay.setRequired(false);
		lFilteredSelectItemWithDisplay.setClearable(isClearable);
		lFilteredSelectItemWithDisplay.setShowIcons(isClearable);
		lFilteredSelectItemWithDisplay.setCachePickListResults(false);
		lFilteredSelectItemWithDisplay.setDisplayField(displayField);
		return lFilteredSelectItemWithDisplay;
	}
	
	private void manageOnOptionClick(String name, Record record){
	}
	
	private void manageClearSelect(String name){
		if (name.equals("dittaSearch")){			
			dittaField.clearValue();
			dittaSelectItem.setValue((String)null);
			_form.setValue("dittaSearch", (String)null);
			
			unitaLocaliField.clearValue();
			unitaLocaliSelectItem.setValue((String)null);
			_form.setValue("unitaLocaliSearch", (String)null);
			unitaLocaliField.hide();
		}
		if (name.equals("unitaLocaliSearch")){			
			unitaLocaliField.clearValue();
			unitaLocaliSelectItem.setValue((String)null);
			_form.setValue("unitaLocaliSearch", (String)null);
		}
		if (name.equals("privatoSearch")){			
			privatoField.clearValue();
			privatoSelectItem.setValue((String)null);
			_form.setValue("privatoSearch", (String)null);
		}
	}

	public void setFilter(ConfigurableFilter filter) {
		mFilter = filter;
	}
	
}