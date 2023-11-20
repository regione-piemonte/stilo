/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
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
import it.eng.utility.ui.module.layout.client.common.ChangeDependsFromEvent;
import it.eng.utility.ui.module.layout.client.common.ChangeDependsFromHandler;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.HasChangeDependsFromHandlers;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;

public class ComboBoxItemFiltrabile extends ComboBoxItem implements HasChangeDependsFromHandlers {

	private FilterSelectBean config;
	private ListGridField[] derivedFields;
	private ComboBoxItemFiltrabile _instance;
	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	private String hoverField;
	private Map<String, String> customProperties = new HashMap<>();
	
	public ComboBoxItemFiltrabile() {
		super();
	}
	
	public ComboBoxItemFiltrabile(final ConfigurableFilter filter, final FilterFieldBean bean, final DataSourceField field) {
		_filter = filter;
		_field = field;
		createSelect(bean);		
	}

	protected void createSelect(FilterFieldBean bean) {
		
		_instance = this;
		
		setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML((String)item.getDisplayValue());
			}
		});
		
		this.config = bean.getSelect();
		this.setWidth(config.getWidth() != null ? config.getWidth() : 250);
		if (config.getCustomProperties() != null ) {
			this.customProperties = config.getCustomProperties();
		}
		
		setValueField(customProperties.get("valueField"));
		setDisplayField(customProperties.get("displayField"));
		setShowPickerIcon(false);
		setCanEdit(true);
		setEndRow(false);
		setStartRow(false);
		setAutoFetchData(false);
		setAlwaysFetchMissingValues(true);
		setAddUnknownValues(true);
		setFetchDelay(500);
		setValidateOnChange(false);
		
		ListGridField lListGridField = new ListGridField(customProperties.get("listGridFieldName"));
		lListGridField.setWidth("100%");
		lListGridField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return cellFormat(record);
			}
		});
		
		setPickListFields(lListGridField);
		
		ListGrid lPickListProperties = new ListGrid();
		lPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		lPickListProperties.setShowHeader(false);
		lPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				event.cancel();
				if (getCustomProperties().get("displayField") != null) {
					setValue(event.getRecord().getAttribute(getCustomProperties().get("displayField")).replace(" ", ";"));
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
				
				String nome = event.getCriteria().getAttribute(getDisplayField());
				GWTRestDataSource lComboBoxDS = (GWTRestDataSource) getOptionDataSource();
				lComboBoxDS.addParam(customProperties.get("fetchKey"), nome);
				
				setOptionDataSource(lComboBoxDS);
//				invalidateDisplayValueCache();
			}
		});
		
		setPickListProperties(lPickListProperties);;
		
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
		setOptionDataSource(lGWTRestDataSource);
		setRedrawOnChange(true);

	}
	
	protected String cellFormat(ListGridRecord record) {
		return record.getAttributeAsString(customProperties.get("displayField"));
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		setPickListWidth(width + 50); // per togliere lo scroll orrizontale che compare con lo zoom al 125% su Chrome e che da problemi nella selezione del valore		
	}
	
	public void onOptionClick(ListGridRecord record) {
	}

	protected String manageHoverHtml(FormItem item, DynamicForm form) {
		return (String) item.getValue();
	}

	@Override
	public HandlerRegistration addChangeDependsFromHandler(ChangeDependsFromHandler handler) {
		return doAddHandler(handler, ChangeDependsFromEvent.getType());
	}

	public void setSelect(ComboBoxItemFiltrabile _instance) {
		this._instance = _instance;
	}

	public ComboBoxItemFiltrabile getSelect() {
		return _instance;
	}

	public void setField(DataSourceField _field) {
		this._field = _field;
	}

	public DataSourceField getField() {
		return _field;
	}

	public void setFilter(ConfigurableFilter _filter) {
		this._filter = _filter;
	}

	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setDerivedFields(ListGridField[] fields) {
		this.derivedFields = fields;
	}

	public ListGridField[] getDerivedFields() {
		return derivedFields;
	}

	public FilterSelectBean getConfiguration() {
		return config;
	}

	public void setConfiguration(FilterSelectBean config) {
		this.config = config;
	}

	public String getHoverField() {
		return hoverField;
	}

	public void setHoverField(String hoverField) {
		this.hoverField = hoverField;
	}
	
	public Map<String, String> getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(Map<String, String> customProperties) {
		this.customProperties = customProperties;
	}

	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
	}
	
}
