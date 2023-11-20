/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedCustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;

public class StringaFullTextMistaRicercaLookupItem extends CustomItem {
	
	protected ExtendedCustomItemFormField parole;
	
	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	protected String _title;
	protected Map<String, String> property = new HashMap<>();
	
	public StringaFullTextMistaRicercaLookupItem(){
		super();
	}
	
	public StringaFullTextMistaRicercaLookupItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public StringaFullTextMistaRicercaLookupItem(Map<String, String> property){
		super(property);
	}
	
	public StringaFullTextMistaRicercaLookupItem(String title, Map<String, String> property){
		super(property);
		setTitle(title);
	}
	
	public StringaFullTextMistaRicercaLookupItem(Map<String, String> property, DataSourceField field, ConfigurableFilter filter) {
		super();
		setFilter(filter);
		setField(field);
		setProperty(property);
	}
	
	public StringaFullTextMistaRicercaLookupItem(JavaScriptObject jsObj, DataSourceField field, ConfigurableFilter filter,final Map<String, String> property) {
		super(jsObj);
		setFilter(filter);
		setField(field);
		setProperty(property);
	}
		
	public StringaFullTextMistaRicercaLookupItem(String title, Map<String, String> property, DataSourceField field, ConfigurableFilter filter) {
		super();
		setFilter(filter);
		setField(field);
		setProperty(property);
		setTitle(title);
	}
	
	public StringaFullTextMistaRicercaLookupItem(JavaScriptObject jsObj, String title, DataSourceField field, ConfigurableFilter filter,final Map<String, String> property) {
		super(jsObj);
		setFilter(filter);
		setField(field);
		setProperty(property);
		setTitle(title);
	}

	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new StringaFullTextMistaRicercaLookupItem(jsObj, getTitle(), getField(), getFilter(), getProperty());
		return lCustomItem;
	}
	
	@Override
	public FormItem[] getFormFields() {

		parole = new ExtendedCustomItemFormField("parole", this){
			
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
		ExtendedTextItem paroleEditorType = new ExtendedTextItem();								
		parole.setEditorType(paroleEditorType);		
		
		return new FormItem[]{parole};	
	}
	
	public void manageChangedValue(String value, String itemName) {
		if (getTitle().equalsIgnoreCase("esibente")) {
			if (value != null && !"".equals(value)) {
				final Record lRecord = new Record();
				lRecord.setAttribute("denominazioneSoggetto", value);
				lRecord.setAttribute("codTipoSoggetto", "PF");
				cercaInRubricaAfterChangedField(lRecord, itemName);
			}
		} else {
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
							parole.updateInternalValue(itemName, object.getAttribute("descrizione"));
							setEqualOperator();
						}
					});
				} else {
					if (object.getAttribute("denominazioneSoggetto") != null
							&& !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
						parole.updateInternalValue(itemName, object.getAttribute("denominazioneSoggetto"));
						setEqualOperator();
					} else if (object.getAttribute("cognomeSoggetto") != null
							&& !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("") && 
							object.getAttribute("nomeSoggetto") != null
							&& !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")){
						parole.updateInternalValue(itemName, object.getAttributeAsString("cognomeSoggetto") + " " + object.getAttributeAsString("nomeSoggetto"));
						setEqualOperator();
					}
				}
				parole.updateInternalValue(itemName, record.getAttribute("denominazioneSoggetto"));
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		if (getTitle().equalsIgnoreCase("esibente")) {
			lGwtRestService.addParam("tipiAmmessi", "PF");
			lGwtRestService.addParam("flgsolovldin", "0");
		} else {
			lGwtRestService.addParam("tipiAmmessi", getTipiAmmessiDS());
			lGwtRestService.addParam("finalita", "MITT_DEST");
		}
		lGwtRestService.call(lRecord, callback);
	}

	public String getTipiAmmessiDS() {
		return null;
	}

	protected void setEqualOperator() {
		AdvancedCriteria advancedCrit =  getFilter().getCriteria(true);
		for (int i = 0; i < advancedCrit.getCriteria().length; i++) {
			if (advancedCrit.getCriteria()[i].getFieldName().equals(getField().getName())) {
				Criterion newCriterion = new Criterion();
				newCriterion = advancedCrit.getCriteria()[i];
				newCriterion.setAttribute("value", getValue());
				newCriterion.setOperator(OperatorId.IEQUALS);
				advancedCrit.getCriteria()[i] = newCriterion;
				getFilter().setCriteria(advancedCrit);
				break;
			}
		}
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
		if(_form.getItem("parole") != null) { 
			if(operator != null && "wordsStartWith".equals(operator)) {
				_form.getItem("parole").setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
				_form.getItem("parole").setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());									
			} else {					
				_form.getItem("parole").setTextBoxStyle(it.eng.utility.Styles.textItem);
				_form.getItem("parole").setPrompt(null);
			}	
			_form.getItem("parole").updateState();
		}
	}
	
	public String getFinalitaPopup() {
		if (getTitle().equalsIgnoreCase("esibente")) {
			return "SEL_PF";
		}
		return null;
	}
	
	public String[] getTipiAmmessiPopup() {
		if (getTitle().equalsIgnoreCase("esibente")) {
			return new String[] {"PF"};
		} else {
			return  new String[0];	
		}
	}
	
	public DataSourceField getField() {
		return _field;
	}

	public void setField(DataSourceField field) {
		this._field = field;
	}

	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setFilter(ConfigurableFilter filter) {
		this._filter = filter;
	}

	public Map<String, String> getProperty() {
		return property;
	}

	public void setProperty(Map<String, String> property) {
		this.property = property;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		this._title = title;
	}
	
}
