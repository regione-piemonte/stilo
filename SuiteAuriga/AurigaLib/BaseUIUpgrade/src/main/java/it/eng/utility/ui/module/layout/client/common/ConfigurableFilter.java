/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.TopOperatorAppearance;
import com.smartgwt.client.types.ValueItemType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.form.FilterClause;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.ViewStateChangedEvent;
import com.smartgwt.client.widgets.grid.events.ViewStateChangedHandler;
import com.smartgwt.client.widgets.layout.VStack;

import it.eng.utility.ui.module.core.client.TabletUtility;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.FieldFetchDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.FilterDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.filter.AggregazioniPratica;
import it.eng.utility.ui.module.layout.client.common.filter.Anno;
import it.eng.utility.ui.module.layout.client.common.filter.AnnoRicercaEsatta;
import it.eng.utility.ui.module.layout.client.common.filter.AreaInterventoPratica;
import it.eng.utility.ui.module.layout.client.common.filter.AttributiCustomDelTipo;
import it.eng.utility.ui.module.layout.client.common.filter.AttributiCustomDelTipoRicercaEsatta;
import it.eng.utility.ui.module.layout.client.common.filter.Catalogo;
import it.eng.utility.ui.module.layout.client.common.filter.Check;
import it.eng.utility.ui.module.layout.client.common.filter.Classificazione;
import it.eng.utility.ui.module.layout.client.common.filter.CustomDataSourceField;
import it.eng.utility.ui.module.layout.client.common.filter.DataConOraEstesa;
import it.eng.utility.ui.module.layout.client.common.filter.DataEOra;
import it.eng.utility.ui.module.layout.client.common.filter.DataSenzaOra;
import it.eng.utility.ui.module.layout.client.common.filter.DataSenzaOraEstesa;
import it.eng.utility.ui.module.layout.client.common.filter.DateConOraFilterItem;
import it.eng.utility.ui.module.layout.client.common.filter.DocCollegatiNominativo;
import it.eng.utility.ui.module.layout.client.common.filter.EntitaAssociataModelloDoc;
import it.eng.utility.ui.module.layout.client.common.filter.EstremiRegistrazioneDoc;
import it.eng.utility.ui.module.layout.client.common.filter.InseritoInOdGDiscussioneSeduta;
import it.eng.utility.ui.module.layout.client.common.filter.ListaScelta;
import it.eng.utility.ui.module.layout.client.common.filter.ListaSceltaEstesa;
import it.eng.utility.ui.module.layout.client.common.filter.ListaSceltaOrganigramma;
import it.eng.utility.ui.module.layout.client.common.filter.Lookup;
import it.eng.utility.ui.module.layout.client.common.filter.Numero;
import it.eng.utility.ui.module.layout.client.common.filter.NumeroProgressivoEmail;
import it.eng.utility.ui.module.layout.client.common.filter.NumeroRicercaEsatta;
import it.eng.utility.ui.module.layout.client.common.filter.ProvenienzaDocApplicazione;
import it.eng.utility.ui.module.layout.client.common.filter.ScadenzaFilterItem;
import it.eng.utility.ui.module.layout.client.common.filter.SegnaturaFascicolo;
import it.eng.utility.ui.module.layout.client.common.filter.InoltroRagioneriaAssegnatario;
import it.eng.utility.ui.module.layout.client.common.filter.InoltroRagioneriaData;
import it.eng.utility.ui.module.layout.client.common.filter.SoggettoAttivita;
import it.eng.utility.ui.module.layout.client.common.filter.StringaFullText;
import it.eng.utility.ui.module.layout.client.common.filter.StringaFullTextMista;
import it.eng.utility.ui.module.layout.client.common.filter.StringaFullTextMistaCombobox;
import it.eng.utility.ui.module.layout.client.common.filter.StringaFullTextMistaRicercaLookup;
import it.eng.utility.ui.module.layout.client.common.filter.StringaFullTextRestricted;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaComplessa1;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaEsatta;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaEstesaCaseInsensitive1;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaEstesaCaseInsensitive1WithLookup;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaEstesaCaseInsensitive2;
import it.eng.utility.ui.module.layout.client.common.filter.StringaRicercaRistretta;
import it.eng.utility.ui.module.layout.client.common.filter.UOCollegata;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterType;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class ConfigurableFilter extends FilterBuilder implements HasChangeValueMapHandlers {

	protected ConfigurableFilter filter;
	protected CustomLayout layout;

	protected Map<String, String> extraparam = new HashMap<String, String>();

	protected String nomeEntita;

	protected Map<String, FilterType> mappaFields = new HashMap<String, FilterType>();
	protected Map<String, Boolean> mappaMultiple = new HashMap<String, Boolean>();
	protected Map<String, List<String>> mappaDependsFrom = new HashMap<String, List<String>>();
	protected Map<String, SelectItemFiltrabile> mappaSelects = new HashMap<String, SelectItemFiltrabile>();

//	protected LinkedHashMap<String, String> selectFieldValueMap;

	protected boolean fromSetCriteria;
	protected List<String> updatedValueMapFields = new ArrayList<String>();

	protected int lunghezzaPrecedente;
	
	protected FilterBean filterConfigBean;
	protected Boolean isConfigured;
	protected Boolean notAbleToManageAddFilter;
	
	protected Boolean reset;
	protected Boolean searchStarted = false;

	protected AdvancedCriteria criteriaPrec;

	protected ImgButton addButton;

	protected final Timer timer = new Timer() {

		@Override
		public void run() {
			if (reset) {
				reset = false;
				// System.out.println("reset value");
			} else {
				timer.cancel();
				// JSONEncoder encoder = new JSONEncoder();
				// encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
				// String criteriaStr = JSON.encode(filter.getCriteria().getJsObj(), encoder);
				// System.out.println(criteriaStr);
				layout.firstSearch(true);
			}
		}
	};

	public ConfigurableFilter(String lista) {
		createFilter(lista);
	}

	public ConfigurableFilter(String lista, Map<String, String> extraparam) {
		setExtraParam(extraparam);
		createFilter(lista);
	}

	protected void createFilter(String lista) {
		
		filter = this;
		
		setCanFocus(false);
		setTabIndex(-1);
		
		// Nel momento in cui viene disegnato la prima volta, recupero lo stack in cui è presente il bottone di add e lo gestisco
		// addVisibilityChangedHandler(new VisibilityChangedHandler() {
		// @Override
		// public void onVisibilityChanged(VisibilityChangedEvent event) {
		// HStack lAddButtonStack = (HStack) getClauseStack().getMembers()[getClauseStack().getMembers().length-1];
		// lAddButtonStack.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// manageClickOnStack(event);
		// }
		// });
		// }
		// });
		
		lunghezzaPrecedente = 0;
		setTopOperatorAppearance(TopOperatorAppearance.NONE);
		setShowSubClauseButton(false);

		setAddButtonPrompt(I18NUtil.getMessages().addButton_prompt());
		setRemoveButtonPrompt(I18NUtil.getMessages().removeButton_prompt());
		setMargin(5);

		// se cambio l'operatore o il campo mi cancella il valore
		setRetainValuesAcrossFields(false);

		// setAllowEmpty(true);

		setDynamicContents(true);

		// setWidth100();
		setAutoWidth();
		setAutoDraw(true);

		nomeEntita = lista;

		filterConfigBean = nomeEntita != null ? Layout.getFilterConfig(nomeEntita) : null;
		isConfigured = filterConfigBean != null && filterConfigBean.getFields() != null && filterConfigBean.getFields().size() > 0;
		notAbleToManageAddFilter = filterConfigBean != null && filterConfigBean.getNotAbleToManageAddFilter() != null ? filterConfigBean.getNotAbleToManageAddFilter() : false;
		if (!isConfigured) {
			filterConfigBean = new FilterBean();
			List<FilterFieldBean> fields = new ArrayList<FilterFieldBean>();
			FilterFieldBean field = new FilterFieldBean();
			field.setType(FilterType.stringa_ricerca_ristretta);
			field.setName(extraparam.get("pkFieldName"));
			field.setTitle("Id.");
			fields.add(field);
			filterConfigBean.setFields(fields);
		}
		
		setFieldPickerWidth(250);
		setOperatorPickerWidth(150);		

		SelectItem lFieldsSelectItem = createSelectField();
		setFieldPickerProperties(lFieldsSelectItem);
		
//		FieldOperatorCustomizer lFieldOperatorCustomizer = new FieldOperatorCustomizer() {
//			
//			@Override
//			public OperatorId[] getFieldOperators(String fieldName, OperatorId[] defaultOperators,
//					FilterBuilder filterBuilder) {
//				return new OperatorId[] { OperatorId.EQUALS };
//			}
//		};
//		setFieldOperatorCustomizer(lFieldOperatorCustomizer);
		
		FilterDataSource filterDS = createDataSource();
		setDataSource(filterDS);

		// setValidateOnChange(false);

		addChangeValueMapHandler(new ChangeValueMapHandler() {

			@Override
			public void onChangeValueMap(ChangeValueMapEvent event) {
				controlCriterionValue(event.getField().getName());
			}
		});

		// addFilterChangedHandler(new FilterChangedHandler() {
		// @Override
		// public void onFilterChanged(FilterChangedEvent event) {
		// manageFilterChanged();
		// final JSONEncoder encoder = new JSONEncoder();
		// encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		// String criteriaStr = JSON.encode(filter.getCriteria().getJsObj(), encoder);
		// SC.say(criteriaStr);
		// }
		// });
	}
	
	public FilterDataSource createDataSource() {
		
		FilterBean lFilterBean = getFilterConfigBean();
		
		FilterDataSource datasource = new FilterDataSource(createFields(lFilterBean));
		datasource.setTypeOperators(FieldType.CUSTOM, OperatorId.values());
		
		/*
		FieldType[] types = new FieldType[1];
        types[0] = FieldType.CUSTOM;
        
        Operator operatorRecursively = new Operator();
        operatorRecursively.setID("recursively");
        operatorRecursively.setTitle(I18NUtil.getMessages().operators_recursivelyTitle());
        operatorRecursively.setValueType(OperatorValueType.FIELD_TYPE);
        datasource.addSearchOperator(operatorRecursively, types);       
        
        Operator operatorTipo = new Operator();
        operatorTipo.setID("tipo");
        operatorTipo.setTitle(I18NUtil.getMessages().operators_tipoTitle());
        operatorTipo.setValueType(OperatorValueType.FIELD_TYPE);
        datasource.addSearchOperator(operatorTipo, types);
        
        Operator operatorAllTheWords = new Operator();
        operatorAllTheWords.setID("allTheWords");
        operatorAllTheWords.setTitle(I18NUtil.getMessages().operators_allTheWordsTitle());
        operatorAllTheWords.setValueType(OperatorValueType.FIELD_TYPE);
//      operatorAllTheWords.setEditorType("it.eng.utility.ui.module.layout.client.common.filter.item.FullTextItem");
        datasource.addSearchOperator(operatorAllTheWords, types);
   
        Operator operatorOneOrMoreWords = new Operator();
        operatorOneOrMoreWords.setID("oneOrMoreWords");
        operatorOneOrMoreWords.setTitle(I18NUtil.getMessages().operators_oneOrMoreWordsTitle());
        operatorOneOrMoreWords.setValueType(OperatorValueType.FIELD_TYPE);
//      operatorOneOrMoreWords.setEditorType("it.eng.utility.ui.module.layout.client.common.filter.item.FullTextItem");
        datasource.addSearchOperator(operatorOneOrMoreWords, types);
        
        Operator operatorWordsStartWith = new Operator();
        operatorWordsStartWith.setID("wordsStartWith");
        operatorWordsStartWith.setTitle(I18NUtil.getMessages().operators_wordsStartWithTitle());
        operatorWordsStartWith.setValueType(OperatorValueType.FIELD_TYPE);
//      operatorWordsStartWith.setEditorType("it.eng.utility.ui.module.layout.client.common.filter.item.StringaFullTextMistaItem");
        datasource.addSearchOperator(operatorWordsStartWith, types);
        
	    Operator operatorLike = new Operator();
	    operatorLike.setID("like");
	    operatorLike.setTitle(I18NUtil.getMessages().operators_likeTitle());
	    operatorLike.setValueType(OperatorValueType.FIELD_TYPE);
	    datasource.addSearchOperator(operatorLike, types);	
	    */
		
		return datasource;
	}

	public void controlCriterionValue(String fieldName) {
		DataSourceField field = filter.getDataSource().getField(fieldName);
		int pos = getClausePosition(fieldName);
		if (pos != -1) {
			FormItem lClauseValueItem = (FormItem) getClauseValueItem(pos);
			if (field.getMultiple()) {
				SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
				List<String> valuesList = new ArrayList<String>();
				for (String value : lClauseValueSelectItem.getValues()) {
					if (field.getValueMap().containsKey(value)) {
						valuesList.add(value);
					}
				}
				if (valuesList.size() > 0) {
					reset = true;
					String[] values = new String[valuesList.size()];
					for (int i = 0; i < valuesList.size(); i++) {
						values[i] = valuesList.get(i);
					}
					lClauseValueSelectItem.setValues(values);
				}
			} else {
				try {
					if (!field.getValueMap().containsKey((String) lClauseValueItem.getValue())) {
						reset = true;
						lClauseValueItem.setValue("");
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public VStack getClauseStack() {
		try {
//			return (VStack) VStack.getByJSObject(JSOHelper.getAttributeAsJavaScriptObject(filter.getJsObj(), "clauseStack"));
			return super.getClauseStack();
		} catch (Exception e) {
		}
		return new VStack();
	}

	public FilterClause getClause(int i) {
		try {			
			return (FilterClause) getClauseStack().getMembers()[i];
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	public int getClausePositionByFieldName(String fieldName) {
		try {			
			for(int i = 0; i < getClauseStack().getMembers().length; i++) {
				String clauseFieldName = (String) getClauseFieldNameItem(i).getValue();
				if(fieldName != null && clauseFieldName != null && fieldName.equals(clauseFieldName)) { 
					return i;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return -1;
	}

	public ImgButton getRemoveButton(int i) {
		try {
			FilterClause lFilterClause = getClause(i);
//			ImgButton removeButton = (ImgButton) ImgButton.getByJSObject(JSOHelper.getAttributeAsJavaScriptObject(lFilterClause.getJsObj(), "removeButton"));
			ImgButton removeButton = (ImgButton) lFilterClause.getRemoveButton();
			if (UserInterfaceFactory.isAttivaAccessibilita()){
	 			removeButton.setTabIndex(null);
				removeButton.setCanFocus(true);			
	 		} else {
				removeButton.setCanFocus(false);
				removeButton.setTabIndex(-1);
			}
			removeButton.setShowDown(false);
			removeButton.setShowRollOver(false);			
			removeButton.setPrompt("Rimuovi filtro");							
			return removeButton;
		} catch (Exception e) {
		}
		return null;
	}

	public ImgButton getAddButton() {
		try {
			if(addButton == null) {
//				addButton = (ImgButton) ImgButton.getByJSObject(JSOHelper.getAttributeAsJavaScriptObject(filter.getJsObj(), "addButton"));	
				addButton = super.getAddButton();
				if (UserInterfaceFactory.isAttivaAccessibilita()){
		 			addButton.setTabIndex(null);
					addButton.setCanFocus(true);			
		 		} else {
					addButton.setCanFocus(false);
					addButton.setTabIndex(-1);
				}
				addButton.setShowDown(false);
				addButton.setShowRollOver(false);
				addButton.setPrompt("Aggiungi filtro");				
				addButton.addClickHandler(new ClickHandler() {
	
					@Override
					public void onClick(ClickEvent event) {
						event.cancel();
						// long tStart = System.currentTimeMillis();
						LinkedHashMap<String, String> lMap = getLastClauseFieldNameValueMap();
						String nextFieldName = null;
						int available = 0;
						// Trovo il primo valore utile
						AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true);						
						getAddButton().hide();
						if (lMap != null && lMap.keySet().size() > 0) {
							for (String key : lMap.keySet()) {
								if (lAdvancedCriteria == null || lAdvancedCriteria.getCriteria() == null || lAdvancedCriteria.getCriteria().length == 0 || !isDuplicato(lAdvancedCriteria.getCriteria(), key)) {
									if (nextFieldName == null) {
										nextFieldName = key;
									} else {
										available++;
									}
								}
							}
						}
						// Se ci sono ancora valori disponibili mostro il bottone di add
						if (available > 0) {
							getAddButton().show();
						}
						// Se ho da settare un valore
						if (nextFieldName != null) {
							DataSourceField lDataSourceField = filter.getDataSource().getField(nextFieldName);
							Criterion lCriterion = new Criterion();
							// Cambio il fieldName del criterion con quello valido
							lCriterion.setFieldName(nextFieldName);
							// Recupero gli operatori validi
							String[] lString = lDataSourceField.getAttributeAsStringArray("validOperators");
							String myOperator = lString != null && lString.length > 0 ? lString[0] : null;
							lCriterion.setOperator(myOperator != null ? findRelativeOperator(myOperator) : null);
							lCriterion.setAttribute("value", (Object) null);
							filter.addCriterion(lCriterion);
							// long tEnd = System.currentTimeMillis();
							// SC.say("" + (tEnd - tStart) / 1000.0 + " sec");
						}
					}
				});	
			}
			return addButton;
		} catch (Exception e) {
		}
		return null;
	}
	
	@Override
	public FormItem getValueFieldProperties(FieldType type, String fieldName, OperatorId operatorId,
			ValueItemType itemType, String fieldType) {
		return super.getValueFieldProperties(type, fieldName, operatorId, itemType, fieldType);
	}

	public DynamicForm getClauseForm(int i) {
		try {
			return (DynamicForm) DynamicForm.getByJSObject(JSOHelper.getAttributeAsJavaScriptObject(getClause(i).getJsObj(), "clause"));
//			return getClause(i).getClause();
		} catch (Throwable e) {
		}
		return null;
	}

	public FormItem getClauseFieldNameItem(int i) {
		try {
			return getClauseFieldNameItem(getClauseForm(i));
		} catch (Exception e) {
		}
		return null;
	}

	public FormItem getClauseFieldNameItem(DynamicForm lClauseForm) {
		try {
			return (FormItem) lClauseForm.getField("fieldName");			
		} catch (Exception e) {
		}
		return null;
	}

	public FormItem getClauseOperatorItem(int i) {
		try {
			return getClauseOperatorItem(getClauseForm(i));
		} catch (Exception e) {
		}
		return null;
	}

	public FormItem getClauseOperatorItem(DynamicForm lClauseForm) {
		try {
			return (FormItem) lClauseForm.getField("operator");
		} catch (Exception e) {
		}
		return null;
	}

	public FormItem getClauseValueItem(int i) {
		try {
			return getClauseValueItem(getClauseForm(i));
		} catch (Exception e) {
		}
		return null;
	}
	
	public FormItem getClauseValueItemByField(int i, String field) {
		try {
			return getClauseValueItemByField(getClauseForm(i), field);
		} catch (Exception e) {
		}
		return null;
	}

	public FormItem getClauseValueItem(DynamicForm lClauseForm) {
		try {
			return getClauseValueItemByField(lClauseForm, "value");
		} catch (Exception e) {
		}
		return null;
	}
	
	public FormItem getClauseValueItemByField(DynamicForm lClauseForm, String field) {
		try {
			return (FormItem) lClauseForm.getField(field);
		} catch (Exception e) {
		}
		return null;
	}

	public int getClausePosition(String fieldName) {
		try {
			for (int i = 0; i < getClauseStack().getMembers().length - 1; i++) {
				FormItem lClauseFieldNameItem = getClauseFieldNameItem(i);
				if (lClauseFieldNameItem.getValue() != null && lClauseFieldNameItem.getValue().equals(fieldName)) {
					return i;
				}
			}
		} catch (Exception e) {
		}
		return -1;
	}

	/**
	 * L'evento viene scatenato dopo il filterChange. Quindi in pratica alla chiamata di questa funzione, il filtro contiene già l'ultima clausola aggiunta
	 * secondo i default interni
	 * 
	 * @param event
	 */
	protected void manageClickOnStack(ClickEvent event) {
		if (filter.getNotAbleToManageAddFilter()) {
			AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true).asAdvancedCriteria();
			SC.echo(filter.getCriteria().getJsObj());
			Criterion lCriterion = lAdvancedCriteria.getCriteria()[lAdvancedCriteria.getCriteria().length - 1];
			Criterion[] lCriterions = new Criterion[lAdvancedCriteria.getCriteria().length + 1];
			for (int i = 0; i < lCriterions.length - 1; i++) {
				lCriterions[i] = lAdvancedCriteria.getCriteria()[i];
			}
			lCriterions[lAdvancedCriteria.getCriteria().length] = lCriterion;
			filter.setCriteria(new AdvancedCriteria(OperatorId.AND, lCriterions));
		}
		adjustLastClauseWithCorrectOption();
		// AdvancedCriteria criteria = filter.getCriteria(true).asAdvancedCriteria();
		// if(criteria != null && criteria.getCriteria() != null) {
		// Criterion[] criterions = new Criterion[criteria.getCriteria().length];
		// for(int i = 0; i < criterions.length; i++){
		// Criterion crit = criteria.getCriteria()[i];
		// FilterBean lFilterBean = getFilterConfigBean();
		// List<FilterFieldBean> fields = lFilterBean.getFields();
		// for (FilterFieldBean lFieldBean : fields){
		// if (lFieldBean.getName().equals(crit.getFieldName()) && lFieldBean.getType()==FilterType.check && crit.getValueAsBoolean()==Boolean.FALSE){
		// JSOHelper.setAttribute(crit.getJsObj(), "value", "specialFalse");
		// }
		// }
		// criterions[i] = crit;
		// };
		// filter.setCriteria(new AdvancedCriteria(OperatorId.AND, criterions));
		// }
	}

	protected void manageFilterChanged() {
		// Tutte quelle corrette devono essere disegnate correttamente e aggiunta la gestione
		for (int i = 0; i < getClauseStack().getMembers().length - 1; i++) {
			manageClauseChanged(i);
		}
	}

	protected void manageClauseChanged(int i) {
		if(getLayout() != null) {
			getLayout().manageAfterFilterChanged();
		}
		final FilterClause lFilterClause = getClause(i);
		final FormItem lClauseFieldNameItem = getClauseFieldNameItem(i);
		final FormItem lClauseOperatorItem = getClauseOperatorItem(i);
		final FormItem lClauseValueItem = getClauseValueItem(i);
		if ((lClauseOperatorItem != null) && (TabletUtility.detectIfAndroidTablet())) {
			TabletUtility.addTabletWorkAround(lClauseOperatorItem);
		}
		// Recupero il bottone di remove
		ImgButton lImgButtonRemove = getRemoveButton(i);
		String lClauseFieldName = (String) lClauseFieldNameItem.getValue();
		String lClauseOperator = (String) lClauseOperatorItem.getValue();
		FilterBean lFilterBean = getFilterConfigBean();
		if (lFilterBean != null) {
			for (FilterFieldBean lFilterFieldBean : lFilterBean.getFields()) {
				if (lFilterFieldBean.getName().equals(lClauseFieldName)) {
					if (lFilterFieldBean.isRequired() || i == 0) {
						// Se obbligatorio o è il primo nascondo il bottone di remove
						lImgButtonRemove.setSrc("filter/blank.gif");
						lImgButtonRemove.setShowDisabled(true);
						lImgButtonRemove.setCanHover(false);
						lImgButtonRemove.setDisabled(true);
						if (lFilterFieldBean.isRequired()) {
							// lClauseFieldNameItem.setShowDisabled(false);
							lClauseFieldNameItem.setDisabled(true);
							lClauseFieldNameItem.setTextBoxStyle(it.eng.utility.Styles.selectItemTextReadOnly);
							lClauseFieldNameItem.setShowPickerIcon(false);		
//							lClauseFieldNameItem.setWidth(lClauseFieldNameItem.getWidth() + 2);
						}
						if (lFilterFieldBean.isRequiredForDepends()) {
							lClauseFieldNameItem.setDisabled(true);
							lClauseFieldNameItem.setTextBoxStyle(it.eng.utility.Styles.selectItemTextReadOnly);
							lClauseFieldNameItem.setShowPickerIcon(false);
//							lClauseFieldNameItem.setWidth(lClauseFieldNameItem.getWidth() + 2);
						}
						if (lFilterFieldBean.getType().equals(FilterType.stringa_full_text)) {
							// lClauseFieldNameItem.setShowDisabled(false);
							lClauseFieldNameItem.setDisabled(true);
							lClauseFieldNameItem.setTextBoxStyle(it.eng.utility.Styles.selectItemTextReadOnly);
							lClauseFieldNameItem.setShowPickerIcon(false);
//							lClauseFieldNameItem.setWidth(lClauseFieldNameItem.getWidth() + 2);
							lClauseOperatorItem.addChangeHandler(new ChangeHandler() {

								@Override
								public void onChange(ChangeEvent event) {
									event.cancel();
									// criteriaPrec = filter.getCriteria(true);
									AdvancedCriteria criteria = filter.getCriteria(true);
									List<Criterion> criterionList = new ArrayList<Criterion>();
									if (criteria != null && criteria.getCriteria() != null) {
										for (Criterion crit : criteria.getCriteria()) {
											if ("searchFulltext".equals(crit.getFieldName())) {
												OperatorId operator = findRelativeOperator((String) event.getValue());
												Criterion searchFulltextCrit = new Criterion("searchFulltext", operator);
												Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");
												JSOHelper.setAttribute(searchFulltextCrit.getJsObj(), "value", value);
												criterionList.add(searchFulltextCrit);
											} else {
												criterionList.add(crit);
											}
										}
									}
									final Criterion[] criterias = new Criterion[criterionList.size()];
									for (int i = 0; i < criterionList.size(); i++) {
										criterias[i] = criterionList.get(i);
									}
									filter.setCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
								}
							});
						}
					} else {
						// Altrimenti lo mostro
						lImgButtonRemove.show();
						// Aggiungo il manager
						lImgButtonRemove.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								manageRemoveClick(event, lFilterClause);
							}
						});
					}
					if(lClauseValueItem != null) {
						if (lFilterFieldBean.isFixed()) {
							lClauseValueItem.setShowDisabled(false);
							lClauseValueItem.setDisabled(true);
							lClauseValueItem.setValue(getFixedValues().get(lFilterFieldBean.getName()));
						}
						if(lClauseValueItem instanceof CustomItem) {
							((CustomItem) lClauseValueItem).onChangeFilter(lClauseOperator);
						} else if (lClauseValueItem instanceof com.smartgwt.client.widgets.form.fields.TextItem) {
							if (lClauseOperator.equals("wordsStartWith")) {
								lClauseValueItem.setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
								lClauseValueItem.setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());									
							} else {					
								lClauseValueItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
								lClauseValueItem.setPrompt(null);
							}	
						}
						lClauseValueItem.addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
								manageOnClauseValueChanged(event);
							}
						});
					} else {
						final FormItem lClauseStartItem = getClauseValueItemByField(i, "start");
						if(lClauseStartItem instanceof CustomItem) {
							((CustomItem) lClauseStartItem).onChangeFilter(lClauseOperator);
						}
						lClauseStartItem.addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
								manageOnClauseValueChanged(event);
							}
						});
						final FormItem lClauseEndItem = getClauseValueItemByField(i, "end");
						if(lClauseEndItem instanceof CustomItem) {
							((CustomItem) lClauseEndItem).onChangeFilter(lClauseOperator);
						}
						lClauseEndItem.addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
								manageOnClauseValueChanged(event);
							}
						});
					}
					if (lClauseOperator.equals("like")) {
						lClauseOperatorItem.setTextBoxStyle(it.eng.utility.Styles.selectItemTextRed);
					}
					/*
					SelectItemFiltrabile selectItem = mappaSelects.get(lFilterFieldBean.getName());
					if(selectItem != null && !selectItem.getAutoFetchAbilitato()) {
						Object value = getClauseValueItem(i).getValue();
						if(value != null) {
							DataSourceField field = filter.getDataSource().getField(lFilterFieldBean.getName());
							if(value instanceof String) {
								// se è una select a selezione singola
								if(field.getValueMap() == null || !field.getValueMap().containsKey(value) || field.getValueMap().get(value) == null) {
									selectItem.setIdValoreUnico((String)value);
								}
							} else {
								// se è una select a selezione multipla
								String[] values = value.toString().split(",");
								for(String key : values) {
									if(field.getValueMap() == null || !field.getValueMap().containsKey(key) || field.getValueMap().get(key) == null) {
										selectItem.setIdValoreUnico(value.toString());
										// se anche uno solo dei valori selezionati non è presente nel valueMap devo fare la fetch
										((SelectItemFiltrabile)getClauseValueItem(i)).fetchData();
										break;
									}									
								}																					
							}	
							getClauseValueItem(i).setValue(value);					
						}
					}
					*/
					addClauseItemsChangedHandler(i);
				}
			}
		}
	}
	
	protected FilterBean getFilterConfigBean() {
		return filterConfigBean;
	}

	protected FilterType getFilterType(String lFieldName) {
		FilterBean lFilterBean = getFilterConfigBean();
		if (lFilterBean != null) {
			for (FilterFieldBean lFilterFieldBean : lFilterBean.getFields()) {
				if (lFilterFieldBean.getName().equals(lFieldName)) {
					return lFilterFieldBean.getType();
				}
			}
		}
		return null;
	}

	private void addClauseItemsChangedHandler(int i) {
		final DynamicForm lClauseForm = getClauseForm(i);
		getClauseFieldNameItem(lClauseForm).addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageOnClauseFieldNameChanged(event);
			}
		});	    
		getClauseOperatorItem(lClauseForm).addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageOnClauseOperatorChanged(event);
			}
		});	      
	}
	
	public void manageOnClauseValueChanged(final ChangedEvent event) {
		if(getLayout() != null) {
			getLayout().manageAfterFilterChanged();
		}
	}
	
	public void manageOnClauseFieldNameChanged(final ChangedEvent event) {
		// FIXME Verificare che sia CustomDatasourceField e fieldtype Custom
		// Altrimenti comportamento di prima
		String fieldName = (String) event.getValue();
		DataSourceField lDataSourceField = filter.getDataSource().getField(fieldName);
		String[] validOperators = lDataSourceField.getAttributeAsStringArray("validOperators");
		String defaultOperator = validOperators[0];
		AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true);
		int pos = 0;
		Criterion lClauseCriterion = null;
		for(Criterion lCriterion : lAdvancedCriteria.getCriteria()) {
			if(lCriterion.getFieldName().equals(fieldName)) {
				lClauseCriterion = lCriterion;
				break;
			} else {
				pos++;
			}
		}
		if(lClauseCriterion != null) {
			lClauseCriterion.setOperator(findRelativeOperator(defaultOperator));
			lClauseCriterion.setAttribute("value", (Object) null);
			lClauseCriterion.setAttribute("start", (Object) null);
			lClauseCriterion.setAttribute("end", (Object) null);		
			lAdvancedCriteria.getCriteria()[pos] = lClauseCriterion;
			filter.setCriteria(lAdvancedCriteria);
			if (UserInterfaceFactory.isAttivaAccessibilita()){
				FormItem lClauseFieldNameItem = getClauseFieldNameItem(pos);
				lClauseFieldNameItem.focusInItem();
			}
		}
	}
	
	public void manageOnClauseOperatorChanged(final ChangedEvent event) {
		FormItem lClauseFieldNameItem = getClauseFieldNameItem(event.getForm());
		String fieldName = (String) lClauseFieldNameItem.getValue();
		AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true);
		int pos = 0;
		Criterion lClauseCriterion = null;
		for(Criterion lCriterion : lAdvancedCriteria.getCriteria()) {
			if(lCriterion.getFieldName().equals(fieldName)) {
				lClauseCriterion = lCriterion;
				break;
			} else {
				pos++;
			}
		}
		if(lClauseCriterion != null) {
			lClauseCriterion.setAttribute("value", (Object) null);
			lClauseCriterion.setAttribute("start", (Object) null);
			lClauseCriterion.setAttribute("end", (Object) null);		
			lAdvancedCriteria.getCriteria()[pos] = lClauseCriterion;
			filter.setCriteria(lAdvancedCriteria);
			if (UserInterfaceFactory.isAttivaAccessibilita()){
				FormItem lClauseOperatorItem = getClauseOperatorItem(pos);
				lClauseOperatorItem.focusInItem();
			}
		}		
		FormItem lClauseValueItem = (FormItem) getClauseValueItem(event.getForm());
		if(lClauseValueItem != null) { 
			if(lClauseValueItem instanceof CustomItem) {
				((CustomItem) lClauseValueItem).onChangeFilter((String)event.getValue());
			}
		} else {
			final FormItem lClauseStartItem = getClauseValueItemByField(event.getForm(), "start");
			if(lClauseStartItem instanceof CustomItem) {
				((CustomItem) lClauseStartItem).onChangeFilter((String)event.getValue());
			}
			final FormItem lClauseEndItem = getClauseValueItemByField(event.getForm(), "end");
			if(lClauseEndItem instanceof CustomItem) {
				((CustomItem) lClauseEndItem).onChangeFilter((String)event.getValue());
			}
		}
//		else {
//			operatorChanged(event.getForm());
//		}
	}

	public native void operatorChanged(DynamicForm lClauseForm) /*-{
		var form = lClauseForm.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
		form.creator.operatorChanged(form);
	}-*/;

	protected void manageRemoveClick(ClickEvent event, FilterClause lFilterClause) {
		filter.removeClause(lFilterClause);
		if(getLayout() != null) {
			getLayout().manageAfterFilterChanged();
		}
		getAddButton().show();
	}

	protected Criteria createCriteria(FormItemFunctionContext itemContext) {
		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
//		VStack lVStack = (VStack) itemContext.getFormItem().getForm().getParentElement().getParentElement();
//		lVStack.getParentElement();
//		FilterBuilder lFilter = (FilterBuilder) lVStack.getParentElement();
//		AdvancedCriteria lAdvancedCriteria = lFilter.getCriteria(true);
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria != null ? lAdvancedCriteria.getCriteria() : new Criterion[0];
		String selected = "";
		for (Criterion lCriterion : lCriterions) {
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())) {
				selected += lCriterion.getFieldName() + ",";
			}
		}
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}
	
	public int getLastClausePosition() {
		int pos = -1;
		for(Canvas member : getClauseStack().getMembers()) {
			if(member instanceof FilterClause) {
				pos++;
			} else {
				break;
			}
		}
		return pos;
	}

	public LinkedHashMap<String, String> getLastClauseFieldNameValueMap() {
		try {
			// Recupero la select sui campi relativa all'ultima clausola
			FormItem lLastClauseFieldNameItem = getClauseFieldNameItem(getLastClausePosition());
			if (lLastClauseFieldNameItem != null) {
				// Recupero la mappa
				LinkedHashMap<String, String> lMap = (LinkedHashMap<String, String>) JSOHelper.getAttributeAsMap(lLastClauseFieldNameItem.getJsObj(), "valueMap");
				return getMappaFiltriToShow(lMap);				
			}
		} catch (Exception e) {
		}
		return new LinkedHashMap<String, String>();
	}

	@Override
	public AdvancedCriteria getCriteria() {
		if (filter.isVisible()) {
			filter.hide();
			filter.show();
		}
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for(Canvas member : getClauseStack().getMembers()) {
			if(member instanceof FilterClause) {
				SearchForm clauseForm = ((FilterClause) member).getClause();
				Criterion crit = new Criterion();
				crit.setFieldName((String) clauseForm.getField("fieldName").getValue());
				crit.setOperator(findRelativeOperator((String) clauseForm.getField("operator").getValue()));
				if(clauseForm.getField("value") != null)
					crit.setAttribute("value", checkValueCriteria(clauseForm, "value"));
//					crit.setAttribute("value", clauseForm.getField("value").getValue());
				if(clauseForm.getField("start") != null) {
					crit.setAttribute("start", checkValueCriteria(clauseForm, "start"));
//					crit.setAttribute("start", clauseForm.getField("start").getValue());
				}
				if(clauseForm.getField("end") != null)
					crit.setAttribute("end", checkValueCriteria(clauseForm, "end"));
//					crit.setAttribute("end", clauseForm.getField("end").getValue());
				if(crit.getAttribute("value") != null || crit.getAttribute("start") != null || crit.getAttribute("end") != null) {
					criterionList.add(crit);
				}
			}
		}
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, criterionList.toArray(new Criterion[criterionList.size()]));
		return criteria;
//		return super.getCriteria();
	}
	
	private Object checkValueCriteria (SearchForm clauseForm, String field) {
		if (clauseForm.getField(field).getValue() != null && clauseForm.getField(field).getValue() instanceof JavaScriptObject) {
			JavaScriptObject jsObj = (JavaScriptObject)clauseForm.getField(field).getValue();
			try {
				Record rec = new Record(jsObj);
				String[] attributes = rec.getAttributes();
				for (String key : attributes) {
					String valueAttribute = rec.getAttribute(key);
					if (valueAttribute != null) {
						return clauseForm.getField(field).getValue();
					}
				}
				return null;
			} catch (Exception e) {
				// TODO: handle exception
				return clauseForm.getField(field).getValue();
			}
		}
		return clauseForm.getField(field).getValue();
	}

	@Override
	public AdvancedCriteria getCriteria(boolean includeEmptyValues) {
		if (filter.isVisible()) {
			filter.hide();
			filter.show();
		}
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for(Canvas member : getClauseStack().getMembers()) {
			if(member instanceof FilterClause) {
				SearchForm clauseForm = ((FilterClause) member).getClause();
				Criterion crit = new Criterion();
				crit.setFieldName((String) clauseForm.getField("fieldName").getValue());
				crit.setOperator(findRelativeOperator((String) clauseForm.getField("operator").getValue()));
				if(clauseForm.getField("value") != null)
					crit.setAttribute("value", checkValueCriteria(clauseForm, "value"));
//					crit.setAttribute("value", clauseForm.getField("value").getValue());
				if(clauseForm.getField("start") != null)
					crit.setAttribute("start", checkValueCriteria(clauseForm, "start"));
//					crit.setAttribute("start", clauseForm.getField("start").getValue());
				if(clauseForm.getField("end") != null)
					crit.setAttribute("end", checkValueCriteria(clauseForm, "end"));
//					crit.setAttribute("end", clauseForm.getField("end").getValue());
				if(includeEmptyValues || crit.getAttribute("value") != null || crit.getAttribute("start") != null || crit.getAttribute("end") != null) {
					criterionList.add(crit);
				}
			} 
		}
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, criterionList.toArray(new Criterion[criterionList.size()]));
		return criteria;
//		return super.getCriteria(includeEmptyValues);
	}
	
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
		return lMap;
	}
		
	@Override
	public void setCriteria(AdvancedCriteria criteria) {
		try {
			if(getLayout() != null) {
				// Azzero il numero massimo di record visualizzati
				getLayout().setMaxRecordVisualizzabili("");
				getLayout().manageAfterFilterChanged();			
			}
			// Devo togliere gli eventuali filtri non presenti nel filter.xml (capita ad esempio se nelle preference ho salvato filtri non più presenti)
			FilterBean lFilterBean = getFilterConfigBean();
			if (lFilterBean != null && lFilterBean.getFields() != null){
				// Creo una mappa con tutti i filtri possibili
				LinkedHashMap<String, String> mappaFiltri = new LinkedHashMap<String, String>();
				List<FilterFieldBean>listaFiltri = lFilterBean.getFields();
				for (FilterFieldBean filterFieldBean : listaFiltri) {
					mappaFiltri.put(filterFieldBean.getName(), "");
				}
				mappaFiltri = getMappaFiltriToShow(mappaFiltri);
				// Se ho dei criteri in ingresso faccio la verifica
				if ((criteria != null) && (criteria.getCriteria() != null) && (criteria.getCriteria().length > 0)){
					// Creo una lista dei criteri presenti anche nella lista dei filtri
					List<Criterion> criteriControllati  = new ArrayList<Criterion>();
					for(Criterion criterionDaControllare : criteria.getCriteria()){
						
						if ("maxRecordVisualizzabili".equalsIgnoreCase(criterionDaControllare.getFieldName())){
							// Il numero massimo di record da visualizzare va trattato a parte
							if (getLayout() != null && getLayout().showMaxRecordVisualizzabiliItem()) {
								getLayout().setMaxRecordVisualizzabili(criterionDaControllare.getValueAsString());
							}
						} else if ("nroRecordXPagina".equalsIgnoreCase(criterionDaControllare.getFieldName())){
							// La scelta pagina da visualizzare va trattato a parte
							if (getLayout() != null && getLayout().showPaginazioneItems()) {
								getLayout().setNroRecordXPagina(criterionDaControllare.getValueAsString());
							}
						} else if ("nroPagina".equalsIgnoreCase(criterionDaControllare.getFieldName())){
							// La scelta pagina da visualizzare va trattato a parte
							if (getLayout() != null && getLayout().showPaginazioneItems()) {
								getLayout().setNroPagina(criterionDaControllare.getValueAsString());
							}
						} else if (mappaFiltri.containsKey(criterionDaControllare.getFieldName())){
							// Il criterio da controllare è presente nella mappa, lo aggiungo alla lista dei criteri controllati
							criteriControllati.add(criterionDaControllare);
						}
						SelectItemFiltrabile selectItem = mappaSelects.get(criterionDaControllare.getFieldName());
						if(selectItem != null) {
							Object value = JSOHelper.getAttributeAsObject(criterionDaControllare.getJsObj(), "value");
							if(value != null) {
								DataSourceField field = filter.getDataSource().getField(criterionDaControllare.getFieldName());
								if(value instanceof String) {
									// se è una select a selezione singola
									if(field.getValueMap() == null || !field.getValueMap().containsKey(value) || field.getValueMap().get(value) == null) {
										selectItem.setIdValoreUnico((String)value);
									}
								} else {
									// se è una select a selezione multipla
									String[] values = value.toString().split(",");
									for(String key : values) {
										if(field.getValueMap() == null || !field.getValueMap().containsKey(key) || field.getValueMap().get(key) == null) {
											selectItem.setIdValoreUnico(value.toString());
											break;
										}									
									}																					
								}	
								JSOHelper.setAttribute(criterionDaControllare.getJsObj(), "value", value);					
							}
						}
					}
					// Creo il nuovo criteria che contiene solo i criteri verificati
					criteria = new AdvancedCriteria(criteria.getOperator() != null ? criteria.getOperator() : OperatorId.AND, criteriControllati.toArray(new Criterion[criteriControllati.size()]));
				}
			}
			super.setCriteria(criteria);
//			Criterion[] lCriterions = criteria != null ? criteria.getCriteria() : new Criterion[0];
			
//			for (Criterion lCriterion : lCriterions){
//				if(mappaSelects.containsKey(lCriterion.getFieldName())) {
//					if(lCriterion.getValueAsString() != null && !"".equals(lCriterion.getValueAsString())) {
//						
//					}
//				}
//			}
			LinkedHashMap<String, String> lMap = getLastClauseFieldNameValueMap();
			// Trovo il primo valore utile
			if (getAddButton() != null) {
				getAddButton().hide();
				if (lMap != null && lMap.keySet().size() > 1) {
					for (String key : lMap.keySet()) {
						if (criteria == null || criteria.getCriteria() == null || criteria.getCriteria().length == 0 || !isDuplicato(criteria.getCriteria(), key)) {
							getAddButton().show();
							break;
						}
					}
				}
			}
			manageFilterChanged();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void addCriterion(Criterion criterion) {
		super.addCriterion(criterion);
		if(getLayout() != null) {
			getLayout().manageAfterFilterChanged();
		}
		manageClauseChanged(getLastClausePosition());
	}

	/**
	 * La funzione prende l'ultima clausola, recupera l'item relativo al primo campo (la select sui campi), recupera il valore e la relativa valueMap. Verifica
	 * nella mappa qual'è il primo valore che deve essere selezionato, considerando quelli che sono già presenti nel filtro
	 * 
	 * Se il valore inserito è l'ultimo tra i disponibili, viene nascosto il bottone di add
	 */
	protected void adjustLastClauseWithCorrectOption() {
		LinkedHashMap<String, String> lMap = getLastClauseFieldNameValueMap();
		String nextFieldName = null;
		int available = 0;
		// Trovo il primo valore utile
		AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true);
		getAddButton().hide();
		if (lMap != null && lMap.keySet().size() > 0) {
			for (String key : lMap.keySet()) {
				if (lAdvancedCriteria == null || lAdvancedCriteria.getCriteria() == null || lAdvancedCriteria.getCriteria().length == 0 || !isDuplicato(lAdvancedCriteria.getCriteria(), key)) {
					if (nextFieldName == null) {
						nextFieldName = key;
					} else {
						available++;
					}
				}
			}
		}		
		// Se ci sono ancora valori disponibili mostro il bottone di add
		if (available > 0) {
			getAddButton().show();
		}
		// Se ho da settare un valore
		if (nextFieldName != null) {
			// Recupero le informazioni del valore
			DataSourceField lDataSourceField = filter.getDataSource().getField(nextFieldName);
			Criterion lClauseCriterion = lAdvancedCriteria.getCriteria()[lAdvancedCriteria.getCriteria().length - 1];
			// Cambio il fieldName del criterion con quello valido
			lClauseCriterion.setFieldName(nextFieldName);
			// Recupero gli operatori validi
			String[] lString = lDataSourceField.getAttributeAsStringArray("validOperators");
			String myOperator = lString[0];
			lClauseCriterion.setOperator(findRelativeOperator(myOperator));
			lClauseCriterion.setAttribute("value", (Object) null);
			lClauseCriterion.setAttribute("start", (Object) null);
			lClauseCriterion.setAttribute("end", (Object) null);
			// Setto il criterion
			lAdvancedCriteria.getCriteria()[lAdvancedCriteria.getCriteria().length - 1] = lClauseCriterion;
			// Setto i nuovi criteria
			filter.setCriteria(lAdvancedCriteria);
		}
	}

	/**
	 * Recupera il valore dell'operator a partire dai values
	 * 
	 * @param myOperator
	 * @return
	 */
	protected OperatorId findRelativeOperator(String value) {
		for (OperatorId lOperatorId : OperatorId.values()) {
			if (lOperatorId.getValue().toString().toLowerCase().equals(value.toLowerCase())) {
				return lOperatorId;
			}
		}
		return OperatorId.EQUALS;
	}

	/**
	 * Verifico se il valore passato è già presente come fieldName tra i criterion passati
	 * 
	 * @param lCriterions
	 * @param lString
	 * @return
	 */
	protected boolean isDuplicato(Criterion[] lCriterions, String lString) {
		for (Criterion lCriterion : lCriterions) {
			if (lCriterion.getFieldName() != null && lCriterion.getFieldName().equals(lString)) {
				return true;
			}
		}
		return false;
	}

	protected FieldFetchDataSource getFieldFetchDataSource() {
		FieldFetchDataSource lFieldFetchDataSource = new FieldFetchDataSource(nomeEntita);
		lFieldFetchDataSource.addParam("token", "");
		lFieldFetchDataSource.setCacheAllData(false);
		lFieldFetchDataSource.setPreventHTTPCaching(false);
		lFieldFetchDataSource.setFilter(filter);
		return lFieldFetchDataSource;
	}

	protected SelectItem createSelectField() {
		SelectItem lFieldsSelectItem = new SelectItem();
		lFieldsSelectItem.setOptionDataSource(getFieldFetchDataSource());
		lFieldsSelectItem.setDisplayField("title");
		lFieldsSelectItem.setValueField("name");
		LinkedHashMap<String, String> lMap = new LinkedHashMap<String, String>();
		lFieldsSelectItem.setValueMap(lMap);
		lFieldsSelectItem.setAddUnknownValues(false);
		lFieldsSelectItem.setValue((String) null);
		lFieldsSelectItem.setRequired(false);
		lFieldsSelectItem.setDefaultValue(false);
		lFieldsSelectItem.setDefaultToFirstOption(true);
		lFieldsSelectItem.setCachePickListResults(false);
		// Modifica per portare il focus nella lista di risultati nel caso in cui, aprendola, necessiti di un TAB per il focus corretto sui risultati
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			final ListGrid pickListProperties = new ListGrid();
			pickListProperties.addViewStateChangedHandler(new ViewStateChangedHandler() {
				
				@Override
				public void onViewStateChanged(ViewStateChangedEvent event) {
					pickListProperties.focusInNextTabElement();
				}
			});
			lFieldsSelectItem.setPickListProperties(pickListProperties);
		}
		
		lFieldsSelectItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				return createCriteria(itemContext);
			}
		});
		return lFieldsSelectItem;
	}

	protected DataSourceField[] createFields(FilterBean filterBean) {
		DataSourceField[] fields = new DataSourceField[(filterBean.getFields().size())];
		int counter = 0;
		for (FilterFieldBean lFilterFieldBean : filterBean.getFields()) {
			if (lFilterFieldBean.getSelect() != null && lFilterFieldBean.getDependsFrom() != null && lFilterFieldBean.getDependsFrom().size() > 0) {
				mappaDependsFrom.put(lFilterFieldBean.getName(), lFilterFieldBean.getDependsFrom());
			}
		}
		for (FilterFieldBean lFilterFieldBean : filterBean.getFields()) {
			mappaFields.put(lFilterFieldBean.getName(), lFilterFieldBean.getType());
			DataSourceField lDataSourceTextField = buildField(lFilterFieldBean);
			fields[counter] = lDataSourceTextField;
			counter++;
		}
		return fields;
	}

	protected DataSourceField buildField(FilterFieldBean lFilterFieldBean) {
		DataSourceField lDataSourceField = null;
		String title = lFilterFieldBean.isRequired() ? FrontendUtil.getRequiredFormItemTitle(lFilterFieldBean.getTitle()) : lFilterFieldBean.getTitle();
		FilterType type = lFilterFieldBean.getType();
		if (type == null)
			type = FilterType.stringa_ricerca_estesa_case_insensitive_1;
		switch (type) {
		case data_senza_ora:
			lDataSourceField = new DataSenzaOra(lFilterFieldBean.getName(), title);
			break;
		case data_senza_ora_estesa:
			lDataSourceField = new DataSenzaOraEstesa(lFilterFieldBean.getName(), title);
			break;
		case data_e_ora:
			lDataSourceField = new DataEOra(lFilterFieldBean.getName(), title);
			break;
		case data_con_ora:
			lDataSourceField = new DateConOraFilterItem(lFilterFieldBean.getName(), title);
			break;
		case data_con_ora_estesa:
			lDataSourceField = new DataConOraEstesa(lFilterFieldBean.getName(), title);
			break;
		case scadenza:
			lDataSourceField = new ScadenzaFilterItem(lFilterFieldBean.getName(), title);
			break;
		case numero:
			Map<String, String> lMapPropertyNumero = new HashMap<String, String>();
			lMapPropertyNumero.put("hasFloat", lFilterFieldBean.isHasFloat() ? "true" : "false");
			lMapPropertyNumero.put("nroDecimali", lFilterFieldBean.getNroDecimali());
			lDataSourceField = new Numero(lFilterFieldBean.getName(), title, lMapPropertyNumero);
			break;
		case anno:
			lDataSourceField = new Anno(lFilterFieldBean.getName(), title);
			break;
		case inoltro_ragioneria_data:
			lDataSourceField = new InoltroRagioneriaData(lFilterFieldBean.getName(), title);
			break;
		case inoltro_ragioneria_assegnatario:
			lDataSourceField = new InoltroRagioneriaAssegnatario(lFilterFieldBean.getName(), title);
			break;
		case check:
			lDataSourceField = new Check(lFilterFieldBean.getName(), title);
			break;
		case lista_scelta:
			lDataSourceField = buildListaScelta(title, lFilterFieldBean);
			break;
		case lista_scelta_estesa:
			lDataSourceField = buildListaSceltaEstesa(title, lFilterFieldBean);
			break;
		case lista_scelta_comuni:
			lDataSourceField = buildListaSceltaComuni(title, lFilterFieldBean);
			break;
		case lista_scelta_utenti:
			lDataSourceField = buildListaSceltaUtenti(title, lFilterFieldBean);
			break;			
		case lista_scelta_perizie:
			lDataSourceField = buildListaSceltaPerizie(title, lFilterFieldBean);
			break;	
		case combo_box:
			lDataSourceField = new StringaFullTextMistaCombobox(lFilterFieldBean.getName(), title, lFilterFieldBean, filter);
			break;
		case lista_scelta_organigramma:
			lDataSourceField = buildListaSceltaOrganigramma(title, lFilterFieldBean, lFilterFieldBean.getSelect() != null ? lFilterFieldBean.getSelect().getCustomProperties() : null);
			break;
		case stringa_full_text:
			Map<String, String> lMapPropertyStringaFullText = new HashMap<String, String>();
			lMapPropertyStringaFullText.put("nomeEntita", nomeEntita);
			lMapPropertyStringaFullText.put("categoria", lFilterFieldBean.getCategoria());
			// lMapPropertyStringaFullText.put("attributiDataSource", Layout.getAttributiDataSource());
			// lMapPropertyStringaFullText.put("categoria", lFilterFieldBean.getCategoria());
			String showFlgRicorsivaStringaFullText = getExtraParam().get("showFlgRicorsiva");
			if (showFlgRicorsivaStringaFullText == null) {
				showFlgRicorsivaStringaFullText = String.valueOf(lFilterFieldBean.isShowFlgRicorsiva());
			}
			lMapPropertyStringaFullText.put("showFlgRicorsiva", showFlgRicorsivaStringaFullText);
			String showSelectAttributiStringaFullText = getExtraParam().get("showSelectAttributi");
			if (showSelectAttributiStringaFullText == null) {
				showSelectAttributiStringaFullText = String.valueOf(lFilterFieldBean.isShowSelectAttributi());
			}
			lMapPropertyStringaFullText.put("showSelectAttributi", showSelectAttributiStringaFullText);
			lDataSourceField = new StringaFullText(lFilterFieldBean.getName(), title, lMapPropertyStringaFullText);
			break;
		case stringa_full_text_restricted:
			lDataSourceField = new StringaFullTextRestricted(lFilterFieldBean.getName(), title);
			break;
		case stringa_full_text_mista:
			lDataSourceField = new StringaFullTextMista(lFilterFieldBean.getName(), title);
			break;
		case stringa_full_text_mista_ricerca_lookup:
			Map<String, String> lMapPropertyStringaFullTextMistaRicercaLookup = new HashMap<String, String>();
			lMapPropertyStringaFullTextMistaRicercaLookup.put("lookupType", lFilterFieldBean.getLookupType());
			lMapPropertyStringaFullTextMistaRicercaLookup.put("showLookupDetail", lFilterFieldBean.isShowLookupDetail() ? "true" : "false");
			lDataSourceField = new StringaFullTextMistaRicercaLookup(lFilterFieldBean.getName(), title, lMapPropertyStringaFullTextMistaRicercaLookup, filter);
			break;
		case stringa_ricerca_ristretta:
			lDataSourceField = new StringaRicercaRistretta(lFilterFieldBean.getName(), title);
			break;
		case stringa_ricerca_esatta:
			lDataSourceField = new StringaRicercaEsatta(lFilterFieldBean.getName(), title);
			break;
		case stringa_ricerca_estesa_case_insensitive_1:
			lDataSourceField = new StringaRicercaEstesaCaseInsensitive1(lFilterFieldBean.getName(), title);
			break;
		case stringa_ricerca_estesa_case_insensitive_1_with_lookup:
			Map<String, String> lMapPropertyStringaRicercaEstesaCaseInsensitive1WithLookup = new HashMap<String, String>();
			lMapPropertyStringaRicercaEstesaCaseInsensitive1WithLookup.put("lookupType", lFilterFieldBean.getLookupType());
			lMapPropertyStringaRicercaEstesaCaseInsensitive1WithLookup.put("lookupField", lFilterFieldBean.getLookupField());
			lMapPropertyStringaRicercaEstesaCaseInsensitive1WithLookup.put("showLookupDetail", lFilterFieldBean.isShowLookupDetail() ? "true" : "false");
			lDataSourceField = new StringaRicercaEstesaCaseInsensitive1WithLookup(lFilterFieldBean.getName(), title, lMapPropertyStringaRicercaEstesaCaseInsensitive1WithLookup, filter);
			break;
		case stringa_ricerca_estesa_case_insensitive_2:
			lDataSourceField = new StringaRicercaEstesaCaseInsensitive2(lFilterFieldBean.getName(), title);
			break;	
		case stringa_ricerca_complessa_1:
			lDataSourceField = new StringaRicercaComplessa1(lFilterFieldBean.getName(), title);
			break;
		case estremi_registrazione_doc:
			lDataSourceField = new EstremiRegistrazioneDoc(lFilterFieldBean.getName(), title);
			break;
		case segnatura_fascicolo:
			lDataSourceField = new SegnaturaFascicolo(lFilterFieldBean.getName(), title);
			break;
		case numero_ricerca_esatta:
			lDataSourceField = new NumeroRicercaEsatta(lFilterFieldBean.getName(), title);
			break;
		case catalogo_pratica:
			lDataSourceField = new Catalogo(lFilterFieldBean.getName(), title);
			break;
		case soggetto_attivita:
			lDataSourceField = new SoggettoAttivita(lFilterFieldBean.getName(), title);
			break;
		case areaintervento_pratica:
			lDataSourceField = new AreaInterventoPratica(lFilterFieldBean.getName(), title);
			break;
		case aggregazioni_pratica:
			lDataSourceField = new AggregazioniPratica(lFilterFieldBean.getName(), title);
			break;
		case proven_doc_applicazione:
			lDataSourceField = new ProvenienzaDocApplicazione(lFilterFieldBean.getName(), title);
			break;
		case anno_ricerca_esatta:
			lDataSourceField = new AnnoRicercaEsatta(lFilterFieldBean.getName(), title);
			break;
		case lookup:
			Map<String, String> lMapPropertyLookup = new HashMap<String, String>();
			lMapPropertyLookup.put("lookupType", lFilterFieldBean.getLookupType());
			lMapPropertyLookup.put("showLookupDetail", lFilterFieldBean.isShowLookupDetail() ? "true" : "false");
			lDataSourceField = new Lookup(lFilterFieldBean.getName(), title, lMapPropertyLookup);
			break;
		case attributi_custom_del_tipo:
			Map<String, String> lMapPropertyAttributiCustomDelTipo = new HashMap<String, String>();
			lMapPropertyAttributiCustomDelTipo.put("nomeTabella", lFilterFieldBean.getNomeTabella());
			lDataSourceField = new AttributiCustomDelTipo(lFilterFieldBean.getName(), title, lMapPropertyAttributiCustomDelTipo);
			break;
		case entita_associata_modello_doc:
			lDataSourceField = new EntitaAssociataModelloDoc(lFilterFieldBean.getName(), title);
			break;
		case attributi_custom_del_tipo_ricerca_esatta:
			Map<String, String> lMapPropertyAttributiCustomDelTipoRicercaEsatta = new HashMap<String, String>();
			String nameField = null;
			String valueField = null;
			if (lFilterFieldBean.getDefaultIdNameField()!=null){
				nameField = lFilterFieldBean.getDefaultIdNameField();
				valueField = getExtraParam().get(nameField);
				lMapPropertyAttributiCustomDelTipoRicercaEsatta.put(nameField, valueField);
			}
			if (lFilterFieldBean.getDefaultDisplayNameField()!=null){
				nameField = lFilterFieldBean.getDefaultDisplayNameField();
				valueField = getExtraParam().get(nameField);
				lMapPropertyAttributiCustomDelTipoRicercaEsatta.put(nameField, valueField);
			}
			lMapPropertyAttributiCustomDelTipoRicercaEsatta.put("nomeTabella", lFilterFieldBean.getNomeTabella());
			String nomeEntitaLayout = getExtraParam().get("nomeEntitaLayout");
			lMapPropertyAttributiCustomDelTipoRicercaEsatta.put("nomeEntitaLayout", nomeEntitaLayout);
			lDataSourceField = new AttributiCustomDelTipoRicercaEsatta(lFilterFieldBean.getName(), title, lMapPropertyAttributiCustomDelTipoRicercaEsatta);
			break;
		case numero_progressivo_email:
			lDataSourceField = new NumeroProgressivoEmail(lFilterFieldBean.getName(), title);
			break;
		case inserito_in_odg_discussione_seduta:
			lDataSourceField = new InseritoInOdGDiscussioneSeduta(lFilterFieldBean.getName(), title);
			break;
		case uo_collegata:
			lDataSourceField = new UOCollegata(lFilterFieldBean.getName(), title);
			break;
		case classificazione:
			lDataSourceField = new Classificazione(lFilterFieldBean.getName(), title) {
				@Override
				public ConfigurableFilter getConfigurableFilter() {
					return filter;
				}
			};
			break;	
		case doc_collegati_nominativo:
			Map<String, String> lMapPropertyDocCollNom = new HashMap<String, String>();
			lMapPropertyDocCollNom.put("lookupType", lFilterFieldBean.getLookupType());
			lMapPropertyDocCollNom.put("showLookupDetail", lFilterFieldBean.isShowLookupDetail() ? "true" : "false");
			lDataSourceField = new DocCollegatiNominativo(lFilterFieldBean.getName(), title, lMapPropertyDocCollNom, filter);
			break;			
		case custom:
			lDataSourceField = buildCustom(title, lFilterFieldBean);
			break;
		default:
			lDataSourceField = new StringaRicercaEstesaCaseInsensitive1(lFilterFieldBean.getName(), title);
			break;
		}
		if (lFilterFieldBean.isRequired()) {
			lDataSourceField.setRequired(true);
		}
		return lDataSourceField;
	}

	public CustomDataSourceField buildCustom(String title, final FilterFieldBean lFilterFieldBean) {
		return new CustomDataSourceField(lFilterFieldBean.getName(), FieldType.CUSTOM, title) {

			@Override
			protected void init() {
				setAttribute("customType", "custom");
				FormItem editorType = UserInterfaceFactory.buildCustomFilterEditorType(lFilterFieldBean);
				if (editorType != null) {
					setEditorType(editorType);
				}
			}
		};
	}

	public FormItem buildCustomEditorType(FilterFieldBean lFilterFieldBean) {
		return new TextItem();
	}

	public ListaScelta buildListaScelta(String title, FilterFieldBean lFilterFieldBean) {
		ListaScelta lDataSourceField = null;
		if (lFilterFieldBean.getSelect() != null) {
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
			lDataSourceField.setMultiple(lFilterFieldBean.getSelect().getMultiple());
			if (lFilterFieldBean.getSelect().getValueMap() != null) {
				// lDataSourceField.setValueMap(lFilterFieldBean.getSelect().getValueMap());
				createSelectItem(lFilterFieldBean, lDataSourceField, (LinkedHashMap<String, String>) lFilterFieldBean.getSelect().getValueMap());
			} else {
				createFilteredSelectItem(lFilterFieldBean, lDataSourceField);
			}
			mappaMultiple.put(lFilterFieldBean.getName(), lFilterFieldBean.getSelect().getMultiple());
		} else {
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
		}
		return lDataSourceField;
	}

	public ListaSceltaEstesa buildListaSceltaEstesa(String title, FilterFieldBean lFilterFieldBean) {
		ListaSceltaEstesa lDataSourceField = null;
		if (lFilterFieldBean.getSelect() != null) {
			lDataSourceField = new ListaSceltaEstesa(lFilterFieldBean.getName(), title);
			lDataSourceField.setMultiple(lFilterFieldBean.getSelect().getMultiple());
			if (lFilterFieldBean.getSelect().getValueMap() != null) {
				// lDataSourceField.setValueMap(lFilterFieldBean.getSelect().getValueMap());
				createSelectItem(lFilterFieldBean, lDataSourceField, (LinkedHashMap<String, String>) lFilterFieldBean.getSelect().getValueMap());
			} else {
				createFilteredSelectItem(lFilterFieldBean, lDataSourceField);
			}
			mappaMultiple.put(lFilterFieldBean.getName(), lFilterFieldBean.getSelect().getMultiple());
		} else {
			lDataSourceField = new ListaSceltaEstesa(lFilterFieldBean.getName(), title);
		}
		return lDataSourceField;
	}

	public ListaScelta buildListaSceltaComuni(String title, FilterFieldBean lFilterFieldBean) {
		ListaScelta lDataSourceField = null;
		if (lFilterFieldBean.getSelect() != null) {
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
			lDataSourceField.setMultiple(lFilterFieldBean.getSelect().getMultiple());
			createFilteredSelectItem(lFilterFieldBean, lDataSourceField, I18NUtil.getMessages().emptyPickListComuniNonStdMessage());
			mappaMultiple.put(lFilterFieldBean.getName(), lFilterFieldBean.getSelect().getMultiple());
		} else {
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
		}
		return lDataSourceField;
	}

	public ListaScelta buildListaSceltaPerizie(String title, FilterFieldBean lFilterFieldBean) {
		ListaScelta lDataSourceField = null;
		lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
		lDataSourceField.setMultiple(lFilterFieldBean.getSelect().getMultiple());
		if (lFilterFieldBean.getSelect() != null) {
			createFilteredSelectItem(lFilterFieldBean, lDataSourceField);
			mappaMultiple.put(lFilterFieldBean.getName(), lFilterFieldBean.getSelect().getMultiple());
		} else {
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
		}
		return lDataSourceField;
	}
	
	
	public ListaScelta buildListaSceltaUtenti(String title, FilterFieldBean lFilterFieldBean) {
		ListaScelta lDataSourceField = null;
		lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
		lDataSourceField.setMultiple(lFilterFieldBean.getSelect().getMultiple());
		if (lFilterFieldBean.getSelect() != null) {
			if (UserInterfaceFactory.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
				createFilteredSelectItem(lFilterFieldBean, lDataSourceField, I18NUtil.getMessages().emptyPickListDimNonStdMessage());
			} else {
				createFilteredSelectItem(lFilterFieldBean, lDataSourceField);
			}
			mappaMultiple.put(lFilterFieldBean.getName(), lFilterFieldBean.getSelect().getMultiple());
		} else {
			lDataSourceField = new ListaScelta(lFilterFieldBean.getName(), title);
		}
		return lDataSourceField;
	}

	public DataSourceField buildListaSceltaOrganigramma(String title, FilterFieldBean lFilterFieldBean, Map<String, String> lMapProperty) {
		if (UserInterfaceFactory.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			return new ListaSceltaOrganigramma(lFilterFieldBean.getName(), title, lMapProperty);
		} else {
			return buildListaScelta(title, lFilterFieldBean);
		}
	}

	public void createFilteredSelectItem(FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField) {
		if (pFilterFieldBean.getSelect() != null && pFilterFieldBean.getSelect().getEmptyPickListMessage() != null
				&& !"".equals(pFilterFieldBean.getSelect().getEmptyPickListMessage())) {
			createFilteredSelectItem(pFilterFieldBean, pDataSourceField, pFilterFieldBean.getSelect().getEmptyPickListMessage());
		} else {
			createFilteredSelectItem(pFilterFieldBean, pDataSourceField, null);
		}
	}

	public void createFilteredSelectItem(FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField, String emptyPickListMessage) {
		SelectItemFiltrabile lSelectItemFiltrabile = new SelectItemFiltrabile(filter, pFilterFieldBean, pDataSourceField);
		if (emptyPickListMessage != null && !"".equals(emptyPickListMessage)) {
			lSelectItemFiltrabile.setEmptyPickListMessage(emptyPickListMessage);
		}
		pDataSourceField.setEditorType(lSelectItemFiltrabile);
		pDataSourceField.setFilterEditorType(SelectItem.class);
		mappaSelects.put(pFilterFieldBean.getName(), lSelectItemFiltrabile);
	}

	public void createSelectItem(FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField, LinkedHashMap<String, String> valueMap) {
		it.eng.utility.ui.module.layout.client.common.items.SelectItem lSelectItem = new it.eng.utility.ui.module.layout.client.common.items.SelectItem(
				pFilterFieldBean.getName(), pFilterFieldBean.getTitle());
		lSelectItem.setValueMap(valueMap);
		if (pFilterFieldBean.getSelect().getCustomProperties() != null && pFilterFieldBean.getSelect().getCustomProperties().containsKey("keyDefaultValue")){
			lSelectItem.setDefaultValue(pFilterFieldBean.getSelect().getCustomProperties().get("keyDefaultValue"));
		}
		pDataSourceField.setEditorType(lSelectItem);
		pDataSourceField.setFilterEditorType(SelectItem.class);		
	}

	public AdvancedCriteria getCriteriaWithoutValues() {
		AdvancedCriteria criteriaWithoutValues = new AdvancedCriteria();
		AdvancedCriteria filterCriteria = this.getCriteria(true);
		Criterion[] criteria = null;
		if (filterCriteria.getCriteria() != null && filterCriteria.getCriteria().length > 0) {
			criteria = new Criterion[filterCriteria.getCriteria().length];
			for (int i = 0; i < filterCriteria.getCriteria().length; i++) {
				criteria[i] = clearCriterionValues(filterCriteria.getCriteria()[i]);
			}
		}
		criteriaWithoutValues.setAttribute("criteria", criteria);
		return criteriaWithoutValues;
	}

	public void clearValues() {
		setCriteria(getCriteriaWithoutValues());
	}

	protected Criterion clearCriterionValues(Criterion criterion) {
		Criterion[] criteria = null;
		if (criterion.getCriteria() != null && criterion.getCriteria().length > 0) {
			criteria = new Criterion[criterion.getCriteria().length];
			for (int i = 0; i < criterion.getCriteria().length; i++) {
				criteria[i] = clearCriterionValues(criterion.getCriteria()[i]);
			}
		}
//		Criterion res = new Criterion(criterion.getFieldName(), criterion.getOperator());
		boolean isCheck = criterion.getValueAsString() != null && (criterion.getValueAsString().equalsIgnoreCase("true") || criterion.getValueAsString().equalsIgnoreCase("false"));
		Criterion res = isCheck ? new Criterion(criterion.getFieldName(), criterion.getOperator(), false) : new Criterion(criterion.getFieldName(), criterion.getOperator());
		res.setAttribute("criteria", criteria);
		return res;
	}

	public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, final boolean autoSearch) {
		setCriteria(criteria);
		if (autoSearch) {
			reset = true;
			timer.scheduleRepeating(100);
		}
	}

	@Override
	public HandlerRegistration addChangeValueMapHandler(ChangeValueMapHandler handler) {
		return doAddHandler(handler, ChangeValueMapEvent.getType());
	}

	/**
	 * Metodo da implementare nel caso alcuni filtri siano preimpostati con un valore fisso
	 *
	 * @return
	 */
	// Metodo da implementare nel caso alcuni filtri siano preimpostati con un valore fisso
	public HashMap<String, String> getFixedValues() {
		return new LinkedHashMap<String, String>();
	}

	public CustomLayout getLayout() {
		return layout;
	}

	public void setLayout(CustomLayout layout) {
		this.layout = layout;
	}

	public void setFilter(ConfigurableFilter filter) {
		this.filter = filter;
	}

	public ConfigurableFilter getFilter() {
		return filter;
	}

	public String getNomeEntita() {
		return nomeEntita;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	public Map<String, FilterType> getMappaFields() {
		return mappaFields;
	}

	public void setMappaFields(Map<String, FilterType> mappaFields) {
		this.mappaFields = mappaFields;
	}

	public Map<String, Boolean> getMappaMultiple() {
		return mappaMultiple;
	}

	public void setMappaMultiple(Map<String, Boolean> mappaMultiple) {
		this.mappaMultiple = mappaMultiple;
	}

	public Map<String, List<String>> getMappaDependsFrom() {
		return mappaDependsFrom;
	}

	public void setMappaDependsFrom(Map<String, List<String>> mappaDependsFrom) {
		this.mappaDependsFrom = mappaDependsFrom;
	}

	public Map<String, SelectItemFiltrabile> getMappaSelects() {
		return mappaSelects;
	}

	public void setMappaSelects(Map<String, SelectItemFiltrabile> mappaSelects) {
		this.mappaSelects = mappaSelects;
	}

	public List<String> getUpdatedValueMapFields() {
		return updatedValueMapFields;
	}

	public void setUpdatedValueMapFields(List<String> updatedValueMapFields) {
		this.updatedValueMapFields = updatedValueMapFields;
	}

	public Boolean getNotAbleToManageAddFilter() {
		return notAbleToManageAddFilter;
	}

	public void setNotAbleToManageAddFilter(Boolean notAbleToManageAddFilter) {
		this.notAbleToManageAddFilter = notAbleToManageAddFilter;
	}

	public static native void addIsContainerOrEqualsOperator()/*-{
		$wnd.isc.Operators.addClassProperties({
			isContainedWithinOrEqual : "is contained within or equal"
		});
		$wnd.isc.DataSource
				.addSearchOperator({
					ID : 'isContainedWithinOrEqual',
					title : 'is contained within or equal',
					valueType : 'fieldType',
					fieldTypes : "custom",
					requiresServer : false,
					hidden : false,
					condition : function(value, record, fieldName, criterion,
							operator) {
						return true;
					}
				});
	}-*/;

	public void setExtraParam(Map<String, String> extraparam) {
		this.extraparam = extraparam != null ? extraparam : new HashMap<String, String>();
	}

	public Map<String, String> getExtraParam() {
		return extraparam;
	}

	public Boolean getIsConfigured() {
		return isConfigured;
	}

	@Override
	public HandlerRegistration addFilterChangedHandler(FilterChangedHandler handler) {
		return null;
	}

	public Boolean getSearchStarted() {
		return searchStarted;
	}

	public void setSearchStarted(Boolean searchStarted) {
		this.searchStarted = searchStarted;
	}

}