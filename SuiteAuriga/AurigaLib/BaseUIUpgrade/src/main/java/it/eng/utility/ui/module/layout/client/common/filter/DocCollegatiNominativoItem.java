/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedCustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;

public class DocCollegatiNominativoItem extends CustomItem {
	
	private ExtendedCustomItemFormField nominativo;
	private CustomItemFormField tipiNominativi;

	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	protected Map<String, String> property = new HashMap<>();
	
	public DocCollegatiNominativoItem(JavaScriptObject jsObj) {
		super(jsObj);
	}
	
	public DocCollegatiNominativoItem(Map<String, String> property, DataSourceField field, ConfigurableFilter filter) {
		super();
		setfilter(filter);
		setfield(field);
		setProperty(property);
	}
	
	public DocCollegatiNominativoItem(JavaScriptObject jsObj, DataSourceField field, ConfigurableFilter filter,final Map<String, String> property) {
		super(jsObj);
		setfilter(filter);
		setfield(field);
		setProperty(property);
	}
	
	public DocCollegatiNominativoItem() {
		super();
	}
	
	@Override
	public FormItem[] getFormFields() {
		createNominativoField();
		createTipiNominativiField();
		return new FormItem[]{nominativo, tipiNominativi};
	}

	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		DocCollegatiNominativoItem lFilterCanvasItem = new DocCollegatiNominativoItem(jsObj, getfield(), getfilter(), getProperty());
		return lFilterCanvasItem;
	}
	
	protected void createNominativoField() {
		nominativo = new ExtendedCustomItemFormField("nominativo", this) {
			
			@Override
			protected void manageChanged(ChangedEvent event) {
				super.manageChanged(event);
				if(!UserInterfaceFactory.getParametroDBAsBoolean("DISATTIVA_AUTOCOMPLETAMENTO_FILTRI")) {					
					// controllo la proprietà searchstarted del ConfigurableFilter 
					// per non far partire la ricerca con lookup se è stato cliccato il pulsante CERCA
					// senza prima togliere il focus dal textItem del filtro
					if (!_filter.getSearchStarted()) {
						manageChangedValue((String) event.getValue(), event.getItem().getName());
					}
				}
			}
		};
		ExtendedTextItem nominativoEditorType = new ExtendedTextItem("nominativo", "Doc. collegati al nominativo");
		nominativoEditorType.setWidth(220);
		nominativoEditorType.setStartRow(false);
		nominativoEditorType.setShowTitle(false);
		nominativo.setEditorType(nominativoEditorType);	
	}
	
	protected void createTipiNominativiField() {
		tipiNominativi = new CustomItemFormField("tipiNominativi", "di tipo", this);
		SelectItem tipiNominativiEditorType = createTipiNominativiSelectItem();
		tipiNominativiEditorType.setAllowEmptyValue(true);
		tipiNominativiEditorType.setWidth(140);
		tipiNominativiEditorType.setStartRow(false);
		tipiNominativiEditorType.setShowTitle(true);
		tipiNominativi.setEditorType(tipiNominativiEditorType);
	}

	private SelectItem createTipiNominativiSelectItem(){
		SelectGWTRestDataSource lSelectGWTRestDataSource = new SelectGWTRestDataSource("LoadComboTipiNominativiFilterDataSource", "key", FieldType.TEXT);
		lSelectGWTRestDataSource.addParam("isFromFilter", "true");
		SelectItem lFilteredSelectItem = new SelectItem("tipiNominativi");	
		lFilteredSelectItem.setOptionDataSource(lSelectGWTRestDataSource);
		lFilteredSelectItem.setDisplayField("value");
		lFilteredSelectItem.setValueField("key");
		lFilteredSelectItem.setAllowEmptyValue(true);
		lFilteredSelectItem.setWidth(300);
		lFilteredSelectItem.setStartRow(true);
		lFilteredSelectItem.setAutoFetchData(false);
        lFilteredSelectItem.setFilterLocally(false);
        lFilteredSelectItem.setRequired(false);
        lFilteredSelectItem.setShowIcons(true);
        lFilteredSelectItem.setCachePickListResults(false);
        lFilteredSelectItem.setMultiple(true);
        return lFilteredSelectItem;
   }

	@Override
	protected void disegna(Object value) {
		super.disegna(value);
		redrawParoleTextBoxStyle(null);
	}
	
	@Override
	public void onChangeFilter(String clauseOperator) {
		super.onChangeFilter(clauseOperator);
		redrawParoleTextBoxStyle(clauseOperator);
	}
	
	private void redrawParoleTextBoxStyle(String operator) {
		if(_form.getItem("nominativo") != null) { 
			if(operator != null && "wordsStartWith".equals(operator)) {
				_form.getItem("nominativo").setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
				_form.getItem("nominativo").setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());									
			} else {					
				_form.getItem("nominativo").setTextBoxStyle(it.eng.utility.Styles.textItem);
				_form.getItem("nominativo").setPrompt(null);
			}	
			_form.getItem("nominativo").updateState();
		}
	}
	
	public void manageChangedValue(String value, String itemName) {
		if (value != null && !"".equals(value)) {
			final Record lRecord = new Record();
			lRecord.setAttribute("denominazioneSoggetto", value);
			cercaInRubricaAfterChangedField(lRecord, itemName);
		}
	}

	protected void cercaInRubricaAfterChangedField(final Record record, final String itemName) {
		Timer t1 = new Timer() {
			public void run() {
				cercaInRubrica(record, false, itemName);
			}
		};
		t1.schedule(1000);
	}

	protected void cercaInRubrica(final Record record, final boolean showLookupWithNoResults, final String itemName) {
		cercaSoggetto(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (object.getAttribute("trovatiSoggMultipliInRicerca") != null
						&& object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca")) {
					Map<String, Object> extraparams = new HashMap<>();
					extraparams.put("finalita", getFinalitaPopup());
					extraparams.put("tipiAmmessi", getTipiAmmessiPopup());
					FilterLookupType lookupType = FilterLookupType.valueOf(property.get("lookupType"));
					UserInterfaceFactory.onClickLookupButtonWithFilters(lookupType, record, extraparams, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							nominativo.updateInternalValue(itemName, object.getAttribute("descrizione"));
							setEqualOperator();
						}
					});
				} else {
					if (object.getAttribute("denominazioneSoggetto") != null
							&& !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
						nominativo.updateInternalValue(itemName, object.getAttribute("denominazioneSoggetto"));
						setEqualOperator();
					} else if (object.getAttribute("cognomeSoggetto") != null
							&& !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("") && 
							object.getAttribute("nomeSoggetto") != null
							&& !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")){
						nominativo.updateInternalValue(itemName, object.getAttributeAsString("cognomeSoggetto") + " " + object.getAttributeAsString("nomeSoggetto"));
						setEqualOperator();
					}
				}
				nominativo.updateInternalValue(itemName, record.getAttribute("denominazioneSoggetto"));
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("tipiAmmessi", "AOOI,PA,PF,PG");
		lGwtRestService.addParam("finalita", "MITT_DEST");
		lGwtRestService.call(lRecord, callback);
	}

	protected void setEqualOperator() {
		AdvancedCriteria advancedCrit =  getfilter().getCriteria(true);
		for (int i = 0; i < advancedCrit.getCriteria().length; i++) {
			if (advancedCrit.getCriteria()[i].getFieldName().equals(getfield().getName())) {
				Criterion newCriterion = new Criterion();
				newCriterion = advancedCrit.getCriteria()[i];
				newCriterion.setAttribute("value", getValue());
				newCriterion.setOperator(OperatorId.IEQUALS);
				advancedCrit.getCriteria()[i] = newCriterion;
				getfilter().setCriteria(advancedCrit);
				break;
			}
		}
	}
	
	public String getFinalitaPopup() {
		return null;
	}
	
	public String[] getTipiAmmessiPopup() {
		return null;
	}
	
	public DataSourceField getfield() {
		return _field;
	}

	public void setfield(DataSourceField field) {
		this._field = field;
	}

	public ConfigurableFilter getfilter() {
		return _filter;
	}

	public void setfilter(ConfigurableFilter filter) {
		this._filter = filter;
	}

	public Map<String, String> getProperty() {
		return property;
	}

	public void setProperty(Map<String, String> property) {
		this.property = property;
	}
	
}
