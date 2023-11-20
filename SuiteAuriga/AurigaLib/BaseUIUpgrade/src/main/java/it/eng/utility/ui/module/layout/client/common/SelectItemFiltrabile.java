/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.ListGridComponent;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;

import it.eng.utility.ui.module.core.client.TabletUtility;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterType;

public class SelectItemFiltrabile extends SelectItem implements HasChangeDependsFromHandlers {

	private FilterSelectBean config;
	private ListGridField[] derivedFields;
	private SelectItemFiltrabile _instance;
	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	private String hoverField;
	private Map initialValueMap;
	private int idPosition;
	private String primaryKey;

	public SelectItemFiltrabile(JavaScriptObject jsObj) {
		super(jsObj);
	}

	public SelectItemFiltrabile(final String selectName, final List<String> dependsFrom) {
		createSelect(Layout.getFilterSelectConfig().getSelects().get(selectName), dependsFrom, false);
	}

	public SelectItemFiltrabile(final ConfigurableFilter filter, final FilterFieldBean bean, final DataSourceField field) {
		_filter = filter;
		_field = field;
		createSelect(bean.getSelect(), bean.getDependsFrom(), true);		
		if(bean.getDictionaryEntry() != null && !"".equals(bean.getDictionaryEntry())) {
			if(getOptionDataSource() != null && (getOptionDataSource() instanceof GWTRestDataSource)) {
				((GWTRestDataSource)getOptionDataSource()).addParam("dictionaryEntry", bean.getDictionaryEntry());
			}
		}
	}
	
	@Override
	public void setValue(Object value) {
		super.setValue(value);
	};

	@Override
	public String[] getValues() {
		return super.getValues();
	}
	
	@Override
	public void fetchData() {
		super.fetchData();
	}
	
	@Override
	public void fetchData(DSCallback callback) {
		super.fetchData(callback);
	}

	@Override
	public void setValue(String value) {
		SelectItem lSelectItem = new SelectItem(getJsObj());
		if (lSelectItem.getMultiple()) {
			if (value == null)
				super.setValues(new String[] {});
			String[] values = value.split(",");
			super.setValues(values);
		} else {
			super.setValue(value);
		}

	};

	private String manageHoverHTML(ListGridRecord record, int colNum, Object value) {
		if (record == null)
			return null;
		Map<String, String> lMap = record.getAttributeAsMap("hoverValues");
		if (lMap == null)
			return null;
		if (_instance.isMultiple() != null && _instance.isMultiple()) {
			if (colNum == 0)
				return null;
			else
				colNum--;
		}
		ListGridField[] fields = _instance.getDerivedFields();
		String fieldName = fields[colNum].getName();
		if (lMap.containsKey(fieldName))
			return lMap.get(fieldName);
		else
			return record.getAttributeAsString(fieldName);
	}

	protected String manageCellFormatter(ListGridRecord record, int colNum, Object value) {
		if (_instance.isMultiple() != null && _instance.isMultiple())
			colNum--;
		ListGridField[] fields = _instance.getDerivedFields();
		Map<String, String> lMap = record.getAttributeAsMap("formatValues");
		String fieldName = fields[colNum].getName();
		if (lMap.containsKey(fieldName))
			return lMap.get(fieldName);
		else
			return (String) value;
	}

	protected void createSelect(FilterSelectBean select, final List<String> dependsFrom, boolean onFilter) {
		
		_instance = this;

		setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return manageHoverHtml(item, form);
			}
		});
		
		this.config = select;

		this.setWidth(config.getWidth() != null ? config.getWidth() : 250);

		final ListGrid pickListProperties = new ListGrid();
		
		pickListProperties.setCanHover(true);
		pickListProperties.setShowAllRecords(false);
		pickListProperties.setGridComponents(new ListGridComponent[] { ListGridComponent.HEADER, ListGridComponent.FILTER_EDITOR, ListGridComponent.BODY });
		
		if (config.isCanFilter()) {
			pickListProperties.setShowFilterEditor(true);
			pickListProperties.setFilterOnKeypress(true);
			pickListProperties.setFetchDelay(1000);	
			Button filterButton = new Button();
			filterButton.setIcon("buttons/search.png");
			filterButton.setShowRollOverIcon(false);
			pickListProperties.setFilterButtonProperties(filterButton);
			pickListProperties.setFilterButtonPrompt(I18NUtil.getMessages().filteredSelectItem_filterButton_prompt());
			pickListProperties.addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {

				@Override
				public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
					manageEditorSubmit(event);				
					int pos = _filter.getClausePositionByFieldName(_field.getName());
					if(pos != -1) {
						final SelectItem selectItem = (SelectItem) _filter.getClauseValueItem(pos);
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
			});
		}
		
		pickListProperties.setLeaveScrollbarGap(false);
		pickListProperties.setFixedRecordHeights(true);
		// disabilito questa riga perchè altrimenti in alcuni casi vengono troncati i valori dentro le select (es. sul filtro "Tipo di copie" in
		// "Ricerca documenti e fascicoli")
		// pickListProperties.setWrapCells(true);

		Integer rowsHeight = config.getLayout().getRowsHeight() != null ? config.getLayout().getRowsHeight() : 1;

		pickListProperties.setCellHeight(rowsHeight * Layout.getGenericConfig().getRecordHeightDefault());
		
		pickListProperties.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return manageHoverHTML(record, colNum, value);
			}
		});

		pickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				
				onOptionClick(event.getRecord());
			}
		});

		ListGridField[] fields = new ListGridField[config.getLayout().getFields().size()];
		ListGridField[] fieldsDerived = new ListGridField[config.getLayout().getFields().size() - 1];
		int count = 0;
		int countDerived = 0;

		int totalWidth = 0;

		for (ItemFilterBean lItemFilterBean : config.getLayout().getFields()) {
			SelectItemFiltrabileField field = new SelectItemFiltrabileField(lItemFilterBean, _instance);
			field.setParent(pickListProperties);
			if (lItemFilterBean.isValue()) {
				primaryKey = lItemFilterBean.getName();
				idPosition = count;
				setValueField(lItemFilterBean.getName());
			} else {
				// il displayField è sempre displayValue
//				if (lItemFilterBean.isDisplay()) {
//					setDisplayField(lItemFilterBean.getName());						
//				}
				if (lItemFilterBean.getWidth() != null) {
					field.setWidth(lItemFilterBean.getWidth().intValue());
					totalWidth = totalWidth + lItemFilterBean.getWidth().intValue();
				} else {
					if (lItemFilterBean.getType().equals(ItemFilterType.ICON)) {
						field.setWidth(30);
						totalWidth = totalWidth + 30;
					} else if (lItemFilterBean.getType().equals(ItemFilterType.BOOLEAN)) {
						field.setWidth(80);
						totalWidth = totalWidth + 80;
					} else if (lItemFilterBean.getType().equals(ItemFilterType.DATE) || lItemFilterBean.getType().equals(ItemFilterType.NUMBER)) {
						totalWidth = totalWidth + 100;
						field.setWidth(100);
					} else {
						field.setWidth(80);
						totalWidth = totalWidth + 80;
					}
				}
				if (count == config.getLayout().getFields().size() - 1) {
					field.setWidth("*");
				}
				fieldsDerived[countDerived] = field;
				countDerived++;
			}
			fields[count] = field;
			count++;
		}

		setDisplayField("displayValue");

		pickListProperties.setDefaultFields(fields);
		if (config.getMultiple() != null && config.getMultiple().equals(Boolean.TRUE)) {
			setPickListWidth(totalWidth + 40);
		} else {
			setPickListWidth(totalWidth + 20);
		}
		
		if(countDerived > 1) {
			pickListProperties.setShowHeader(true);		
			pickListProperties.setShowHeaderContextMenu(false);
			pickListProperties.setShowHeaderMenuButton(false);
			setPickListHeaderHeight(22);			
		} else {
			pickListProperties.setShowHeader(false);
			setPickListHeaderHeight(0);
		}
		
		setPickListFields(fields);
		
		setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setDerivedFields(fieldsDerived);
		pickListProperties.setDataPageSize(Layout.getGenericConfig().getFiltrableSelectPageSize());

		if (countDerived == 1) {
			fieldsDerived[0].setWidth(null);
			setPickListWidth(null);
		} else if (select.getWidth() != null && select.getWidth().intValue() > 0) {
			if (getPickListWidth().intValue() < select.getWidth().intValue()) {
				setPickListWidth(null);
			}
		}

		// se commento questo pezzo la selezione del valore della select filtrabile funziona su firefox altrimenti no
		// pickListProperties.setCellFormatter(new CellFormatter() {
		// @Override
		// public String format(Object value, ListGridRecord record, int rowNum,
		// int colNum) {
		// return manageCellFormatter(record, colNum, value);
		// }
		// });

		// Se è attivo il filtro dependsFrom, lo devo aggiungere nei criteria di default passati
		if (onFilter) {
			if (dependsFrom != null && dependsFrom.size() > 0) {
				settingCriteria(dependsFrom);
			}
		}

		// multipla
		if (config.getMultiple() != null && config.getMultiple().equals(Boolean.TRUE)) {
			pickListProperties.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			pickListProperties.setSelectionType(SelectionStyle.SIMPLE);
			setMultiple(true);
			setMultipleAppearance(MultipleAppearance.PICKLIST);
		}

		pickListProperties.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		pickListProperties.setCanResizeFields(true);		
		// pickListProperties.setAutoFitData(Autofit.HORIZONTAL);

		if (config.getOrdinamentoDefault() != null) {
			for (String key : config.getOrdinamentoDefault().keySet()) {
				if ("ascending".equals(config.getOrdinamentoDefault().get(key))) {
					pickListProperties.addSort(new SortSpecifier(key, SortDirection.ASCENDING));
				} else if ("descending".equals(config.getOrdinamentoDefault().get(key))) {
					pickListProperties.addSort(new SortSpecifier(key, SortDirection.DESCENDING));
				}
			}
		}

		if (config.getEmptyPickListMessage() != null && !"".equals(config.getEmptyPickListMessage())) {
			setEmptyPickListMessage(config.getEmptyPickListMessage());
		}

		if (getAutoFetchAbilitato()) {
			pickListProperties.setAutoFetchData(true);
		}
		pickListProperties.setAutoFetchDisplayMap(true);

		pickListProperties.setDataFetchMode(FetchMode.PAGED);

		setPickListProperties(pickListProperties);

		if (!getAutoFetchAbilitato()) {
			setAutoFetchData(false);
			setFetchMissingValues(true);
			setAlwaysFetchMissingValues(true);
		}

		setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return manageHover(item);
			}
		});

		SelectGWTRestDataSource lSelectGWTRestDataSource = new SelectGWTRestDataSource(config.getDatasourceName(), getValueField(), FieldType.TEXT);
		if (dependsFrom != null && dependsFrom.size() > 0) {
			lSelectGWTRestDataSource.setCacheAllData(false);
			// lGwtRestDataSource.setCacheMaxAge(0);
		}
		lSelectGWTRestDataSource.setDataURL("rest/datasourceservicerichieste/all");
		// setCachePickListResults(true);
		lSelectGWTRestDataSource.addParam("datasourcename", config.getDatasourceName());
		if(config.getDatasourceParams() != null) {
			for (String property : config.getDatasourceParams().keySet()) {
				lSelectGWTRestDataSource.addParam(property, config.getDatasourceParams().get(property));
			}
		}
		for (String property : _filter.getExtraParam().keySet()) {
			lSelectGWTRestDataSource.addParam(property, _filter.getExtraParam().get(property));
		}
		lSelectGWTRestDataSource.setAutoCacheAllData(false);
		setOptionDataSource(lSelectGWTRestDataSource);

		if(getAutoFetchAbilitato()) {
			retrieveInitialValueMap(pickListProperties);
		}

		setRedrawOnChange(true);
		// setAlwaysFetchMissingValues(true);

		if (onFilter) {

			addDataArrivedHandler(new DataArrivedHandler() {

				@Override
				public void onDataArrived(DataArrivedEvent event) {
					manageDataArrivedEvent(event);
				}
			});

			// addChangeHandler(new ChangeHandler() {
			// @Override
			// public void onChange(ChangeEvent event) {
			// 
			// String selezioneOld = event.getOldValue() != null ? ((JavaScriptObject)event.getOldValue()).toString() : null;
			// String selezione = event.getValue() != null ? ((JavaScriptObject)event.getValue()).toString() : null;
			// List<String> valuesList = new ArrayList<String>();
			// if(selezioneOld != null && !"".equals(selezioneOld)) {
			// StringTokenizer st = new StringTokenizer(selezioneOld, ",");
			// for(int i = 0; i < st.getTokens().length; i++) {
			// if(!valuesList.contains(st.getTokens()[i])) {
			// valuesList.add(st.getTokens()[i]);
			// }
			// }
			// }
			// if(selezione != null && !"".equals(selezione)) {
			// StringTokenizer st = new StringTokenizer(selezione, ",");
			// for(int i = 0; i < st.getTokens().length; i++) {
			// if(!valuesList.contains(st.getTokens()[i])) {
			// valuesList.add(st.getTokens()[i]);
			// }
			// }
			// }
			// String[] values = valuesList.toArray(new String[valuesList.size()]);
			// }
			// });

			addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					
					_field.setAttribute("selectionChanged", true);
				}
			});

			pickListProperties.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					
					if (_field.getAttribute("selectionChanged") != null && _field.getAttributeAsBoolean("selectionChanged")) {
						_field.setAttribute("selectionChanged", false);
						for (String key : _filter.getMappaDependsFrom().keySet()) {
							if (_filter.getMappaDependsFrom().get(key).contains(_field.getName())) {
								_filter.getMappaSelects().get(key).fireEvent(new ChangeDependsFromEvent(_filter, _field));
							}
						}
					}
				}
			});

			addChangeDependsFromHandler(new ChangeDependsFromHandler() {

				@Override
				public void onChangeDependsFrom(ChangeDependsFromEvent event) {
					
					_filter.setCriteria(_filter.getCriteria(true));
					// _filter.controlCriterionValue(_field.getName());
				}
			});
		}
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		setPickListWidth(width + 50); // per togliere lo scroll orrizontale che compare con lo zoom al 125% su Chrome e che da problemi nella selezione del valore		
	}
	
	public void setIdValoreUnico(String value) {
		if(getOptionDataSource() != null && (getOptionDataSource() instanceof GWTRestDataSource)) {
			((GWTRestDataSource)getOptionDataSource()).addOnlyFirstCallParam("idValoreUnico", value);
		}
	}
	
	public boolean getAutoFetchAbilitato() {
		return false;
	}

	public void settingCriteria(final List<String> dependsFrom) {
		setCachePickListResults(false);
		setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				AdvancedCriteria criteria = _filter.getCriteria(true);
				if (criteria.getCriteria() != null) {
					Criterion[] criterias = new Criterion[dependsFrom.size()];
					int n = 0;
					for (String lString : dependsFrom) {
						boolean found = false;
						for (Criterion criterion : criteria.getCriteria()) {
							if (lString.equals(criterion.getFieldName())) {
								criterias[n] = criterion;
								found = true;
							}
						}
						if (!found) {
							Criterion lCriterion = new Criterion(lString, OperatorId.EQUALS, "");
							criterias[n] = lCriterion;
						}
						n++;
					}

					AdvancedCriteria pickListFilterCriteria = new AdvancedCriteria(OperatorId.AND, criterias);
					JSONEncoder encoder = new JSONEncoder();
					encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
					return pickListFilterCriteria;
				}
				return criteria;
			}
		});

	}

	public void onOptionClick(ListGridRecord record) {
	}

	protected String manageHoverHtml(FormItem item, DynamicForm form) {
		return (String) item.getValue();

	}

	private void retrieveInitialValueMap(final ListGrid pickListProperties) {
		Criterion lCriterion = new Criterion("loadAll", OperatorId.EQUALS, "true");
		getOptionDataSource().fetchData(lCriterion, new DSCallback() {
	
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				initialValueMap = response.getDataAsRecordList().getValueMap(getValueField(), getDisplayField());
				initialValueMap.put("", "");
				_field.setValueMap(initialValueMap);
				if(pickListProperties != null) {
					TabletUtility.resizeForAndroid(pickListProperties, response.getDataAsRecordList().getValueMap(getValueField(), getDisplayField()).size());
				}
			}
		});
	}

	private Criteria actualCriteria = null;

	protected void manageEditorSubmit(FilterEditorSubmitEvent event) {
		// lListGrid.getField("descrizione").getJsObj();
		if (actualCriteria == null) {
			actualCriteria = event.getCriteria();
			// Map<String, Object> lMap = actualCriteria.getValues();
			// for (String lString : lMap.keySet()){
			// if (lMap.get(lString)!=null){
			// System.out.println("Changed value of " + lString);
			// }
			// }
		} else {
			Criteria oldCriteria = actualCriteria;
			Map<String, Object> lMap = oldCriteria.getValues();
			actualCriteria = event.getCriteria();
			// Map<String, Object> lMapOld = actualCriteria.getValues();
			// for (String lString : lMap.keySet()){
			// if (lMap.get(lString)!=lMapOld.get(lString)){
			// System.out.println("Changed value of " + lString);
			// }
			// }
		}
	}

	@Override
	public HandlerRegistration addChangeDependsFromHandler(ChangeDependsFromHandler handler) {
		
		return doAddHandler(handler, ChangeDependsFromEvent.getType());

	}

	public void setSelect(SelectItemFiltrabile _instance) {
		this._instance = _instance;
	}

	public SelectItemFiltrabile getSelect() {
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

	public void manageDataArrivedEvent(DataArrivedEvent event) {
		
		if (!config.isCanFilter()) {
			Map valueMap = event.getData().getValueMap(getValueField(), getDisplayField());
			// if(!_field.getValueMap().equals(valueMap)) {
			_field.setValueMap(valueMap);
			_filter.getUpdatedValueMapFields().add(_field.getName());
			_filter.fireEvent(new ChangeValueMapEvent(_filter, _field, false));
			// }
		} else {
			_field.setValueMap(initialValueMap);
			_filter.getUpdatedValueMapFields().add(_field.getName());
			setFetchMissingValues(true);
			setAlwaysFetchMissingValues(true);
			_filter.fireEvent(new ChangeValueMapEvent(_filter, _field, true));
		}
	}

	protected String manageHover(FormItem item) {
		SelectItem lSelectItem = (SelectItem) item;
		if (hoverField != null)
			return lSelectItem.getSelectedRecord().getAttributeAsString(hoverField);
		if (_instance.isMultiple() != null && _instance.isMultiple()) {
			return lSelectItem.getDisplayValue();
		} else {
			String lString = (String) item.getValue();
			if (lString == null || lString.isEmpty())
				return null;
			return (String) item.getDisplayValue();
		}
	}

	public String getHoverField() {
		return hoverField;
	}

	public void setHoverField(String hoverField) {
		this.hoverField = hoverField;
	}

}
