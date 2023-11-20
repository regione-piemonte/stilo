/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;

public class StringaFullTextMistaComboboxItem extends CustomItem {
	
	private FilterSelectBean config;
	protected CustomItemFormField parole;
	protected FilterFieldBean filterBean;
	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	protected String _title;
	private Map<String, String> customProperties = new HashMap<>();
	protected ComboBoxItem paroleEditorType;
	
	public StringaFullTextMistaComboboxItem(){
		super();
	}
	
	public StringaFullTextMistaComboboxItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public StringaFullTextMistaComboboxItem(Map<String, String> property){
		super(property);
	}
	
	public StringaFullTextMistaComboboxItem(Map<String, String> property, DataSourceField field, ConfigurableFilter filter) {
		super();
		setFilter(filter);
		setField(field);
		setProperty(property);
	}
		
	public StringaFullTextMistaComboboxItem(String title, Map<String, String> property, DataSourceField field, ConfigurableFilter filter) {
		super();
		setFilter(filter);
		setField(field);
		setProperty(property);
		setTitle(title);
	}

	public StringaFullTextMistaComboboxItem(ConfigurableFilter filter, FilterFieldBean filterBean, String title, DataSourceField field) {
		super();
		setFilter(filter);
		setField(field);
		setFilterBean(filterBean);
		setTitle(title);
	}
	
	public StringaFullTextMistaComboboxItem(JavaScriptObject jsObj, ConfigurableFilter filter, FilterFieldBean filterBean, String title, DataSourceField field) {
		super(jsObj);
		setFilter(filter);
		setField(field);
		setFilterBean(filterBean);
		setTitle(title);
	}

	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {		
		CustomItem lCustomItem = new StringaFullTextMistaComboboxItem(jsObj, getFilter(), getFilterBean(), getTitle(), getField());
		return lCustomItem;
	}
	
	@Override
	public FormItem[] getFormFields() {
		parole = new CustomItemFormField("parole", this);						
		paroleEditorType = createComboBox();	
		parole.setEditorType(paroleEditorType);				
		return new FormItem[]{parole};	
	}
	
	private ComboBoxItem createComboBox() {
		
		paroleEditorType = new ComboBoxItem("parole");
		paroleEditorType.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				Map values = _form.getValues();								
				return StringUtil.asHTML((String) values.get("parole"));
			}
		});
		
		this.config = getFilterBean().getSelect();
		this.setWidth(config.getWidth() != null ? config.getWidth() : 250);
		if (config.getCustomProperties() != null ) {
			this.customProperties = config.getCustomProperties();
		}
		
		if (customProperties.get("valueField") != null && !"".equals(customProperties.get("valueField"))) {
			paroleEditorType.setValueField(customProperties.get("valueField"));
		}
		if (customProperties.get("displayField") != null && !"".equals(customProperties.get("displayField"))) {
			paroleEditorType.setDisplayField(customProperties.get("displayField"));
		}	
		
		paroleEditorType.setShowPickerIcon(false);
		paroleEditorType.setCanEdit(true);
		paroleEditorType.setEndRow(false);
		paroleEditorType.setStartRow(false);
		paroleEditorType.setAutoFetchData(false);
		paroleEditorType.setAlwaysFetchMissingValues(true);
		paroleEditorType.setAddUnknownValues(true);		
		paroleEditorType.setFetchDelay(500); // se valueField è diverso da displayField il fetchDelay non funziona e la fetch parte subito non appena si scrive qualcosa dentro la combo-box
		paroleEditorType.setValidateOnChange(false);
		
		if (customProperties.get("listGridFieldName") != null && !"".equals(customProperties.get("listGridFieldName"))) {
			ListGridField lListGridField = new ListGridField(customProperties.get("listGridFieldName"));
			lListGridField.setWidth("100%");
			lListGridField.setCellFormatter(new CellFormatter() {
	
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					return cellFormat(record);
				}
			});
			paroleEditorType.setPickListFields(lListGridField);
		}
		
		ListGrid lPickListProperties = new ListGrid();
		lPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		lPickListProperties.setShowHeader(false);
		lPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				event.cancel();
				if (customProperties.get("displayField") != null && !"".equals(customProperties.get("displayField"))) {
					if (customProperties.get("valuesSeparator") != null && !"".equals(customProperties.get("valuesSeparator"))) {
						parole.updateInternalValue("parole", event.getRecord().getAttribute(customProperties.get("displayField")).replace(" ", customProperties.get("valuesSeparator")));
					} else {
						parole.updateInternalValue("parole", event.getRecord().getAttribute(customProperties.get("displayField")));
					}
				}
				AdvancedCriteria advancedCrit = _filter.getCriteria();
				for (int i = 0; i < advancedCrit.getCriteria().length; i++) {
					if (advancedCrit.getCriteria()[i].getFieldName().equals(getField().getName())) {
						Criterion newCriterion = new Criterion();
						newCriterion = advancedCrit.getCriteria()[i];
						newCriterion.setAttribute("value", getValue());
						newCriterion.setOperator(OperatorId.IEQUALS);
						advancedCrit.getCriteria()[i] = newCriterion;
						_filter.setCriteria(advancedCrit);
						break;
					}
				}
			}
		});
		lPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String nome = event.getCriteria().getAttribute(paroleEditorType.getDisplayField());
				GWTRestDataSource lComboBoxDS = (GWTRestDataSource) paroleEditorType.getOptionDataSource();
				if (customProperties.get("fetchKey") != null && !"".equals(customProperties.get("fetchKey"))) {					
					lComboBoxDS.addParam(customProperties.get("fetchKey"), nome);
				}
				paroleEditorType.setOptionDataSource(lComboBoxDS);
//				invalidateDisplayValueCache();
			}
		});		
		paroleEditorType.setPickListProperties(lPickListProperties);;
		
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource(config.getDatasourceName());
		if(config.getDatasourceParams() != null) {
			for (String property : config.getDatasourceParams().keySet()) {
				lGWTRestDataSource.addParam(property, config.getDatasourceParams().get(property));
			}
		}
		for (String property : _filter.getExtraParam().keySet()) {
			lGWTRestDataSource.addParam(property, _filter.getExtraParam().get(property));
		}
		
		lGWTRestDataSource.setAutoCacheAllData(false);
		
		paroleEditorType.setOptionDataSource(lGWTRestDataSource);
		paroleEditorType.setRedrawOnChange(true);

		return paroleEditorType;
	
	}

//	public void manageChangedValue(String value, String itemName) {
//		if (getTitle().equalsIgnoreCase("esibente")) {
//			if (value != null && !"".equals(value)) {
//				final Record lRecord = new Record();
//				lRecord.setAttribute("denominazioneSoggetto", value);
//				lRecord.setAttribute("codTipoSoggetto", "PF");
//				cercaInRubricaAfterChangedField(lRecord, itemName);
//			}
//		} else {
//			final Record lRecord = new Record();
//			lRecord.setAttribute("denominazioneSoggetto", value);
//			cercaInRubricaAfterChangedField(lRecord, itemName);
//		}
//	}
//
//	protected void cercaInRubricaAfterChangedField(final Record record, final String itemName) {
//		Timer t1 = new Timer() {
//			public void run() {
//				cercaInRubrica(record, false, itemName);
//			}
//		};
//		t1.schedule(1000);
//
//	}
//
//	protected void cercaInRubrica(final Record record, final boolean showLookupWithNoResults, final String itemName) {
//		cercaSoggetto(record, new ServiceCallback<Record>() {
//
//			@Override
//			public void execute(Record object) {
//				if (object.getAttribute("trovatiSoggMultipliInRicerca") != null
//						&& object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca")) {
//					Map<String, Object> extraparams = new HashMap<>();
//					extraparams.put("finalita", getFinalitaPopup());
//					extraparams.put("tipiAmmessi", getTipiAmmessiPopup());
//					FilterLookupType lookupType = FilterLookupType.valueOf(property.get("lookupType"));
//					UserInterfaceFactory.onClickLookupButtonWithFilters(lookupType, record, extraparams, new ServiceCallback<Record>() {
//						
//						@Override
//						public void execute(Record object) {
//							parole.updateInternalValue(itemName, object.getAttribute("descrizione"));
//							setEqualOperator();
//						}
//					});
//
//				} else {
//
//					if (object.getAttribute("denominazioneSoggetto") != null
//							&& !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
//						parole.updateInternalValue(itemName, object.getAttribute("denominazioneSoggetto"));
//						setEqualOperator();
//					} else if (object.getAttribute("cognomeSoggetto") != null
//							&& !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("") && 
//							object.getAttribute("nomeSoggetto") != null
//							&& !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")){
//						parole.updateInternalValue(itemName, object.getAttributeAsString("cognomeSoggetto") + " " + object.getAttributeAsString("nomeSoggetto"));
//						setEqualOperator();
//					}
//				}
//				parole.updateInternalValue(itemName, record.getAttribute("denominazioneSoggetto"));
//			}
//		});
//	}
//
//	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
//		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
//		if (getTitle().equalsIgnoreCase("esibente")) {
//			lGwtRestService.addParam("tipiAmmessi", "PF");
//			lGwtRestService.addParam("flgsolovldin", "0");
//		} else {
//			lGwtRestService.addParam("tipiAmmessi", getTipiAmmessiDS());
//			lGwtRestService.addParam("finalita", "MITT_DEST");
//		}
//		
//		lGwtRestService.call(lRecord, callback);
//	}
//
//	public String getTipiAmmessiDS() {
//		return null;
//	}

	
	protected String cellFormat(ListGridRecord record) {
		if ("destinatario".equals(getFilterBean().getName()) || "mittente".equals(getFilterBean().getName())) {
			if (record != null) {
				String res = null;
				String nomeContatto = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
				if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
					res = buildIconHtml("coccarda.png", nomeContatto);
					return res;
				} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
					res = buildIconHtml("mail/PEO.png", nomeContatto);
					return res;
				} else {
					res = buildIconHtml("mail/mailingList.png", nomeContatto);
					return res;
				}
			}
		}
		if (customProperties.get("displayField") != null && !"".equals(customProperties.get("displayField"))) {
			return record.getAttributeAsString(customProperties.get("displayField"));
		}
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
	
	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
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

	public FilterFieldBean getFilterBean() {
		return filterBean;
	}

	public void setFilterBean(FilterFieldBean filterBean) {
		this.filterBean = filterBean;
	}

	public Map<String, String> getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(Map<String, String> customProperties) {
		this.customProperties = customProperties;
	}
	
}

