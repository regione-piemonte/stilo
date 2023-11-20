/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

public class IndirizziRichiedenteCanvas extends ReplicableCanvas {

	private HiddenItem rowIdItem;
	private SelectItem tipoItem;
	protected DateItem dataValidoDalItem;
	protected DateItem dataValidoFinoAlItem;
	protected FilteredSelectItem statoItem;
	protected SelectItem tipoToponimoItem;
	protected TextItem toponimoItem;
	protected TextItem indirizzoItem;
	protected TextItem civicoItem;
	protected TextItem appendiciItem;
	protected HiddenItem nomeComuneItem;
	protected FilteredSelectItem comuneItem;
	protected TextItem cittaItem;
	protected TextItem provinciaItem;
	protected SelectItem frazioneItem;
	protected SelectItem capItem;
	protected TextItem zonaItem;
	protected TextItem complementoIndirizzoItem;

	private ReplicableCanvasForm mDynamicForm;

	public IndirizziRichiedenteCanvas(IndirizziRichiedenteItem item) {
		super(item);
	}

	public IndirizziRichiedenteCanvas(IndirizziRichiedenteItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		CustomValidator indirizzoRichiedenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				Record record = new Record(mDynamicForm.getValues());
				if (isStatoItalia()) {
					if (isBlank(record.getAttributeAsString("toponimo")) && isBlank(record.getAttributeAsString("comune")) /*
																															 * &&
																															 * isBlank(record.getAttributeAsString
																															 * ("cap"))
																															 */) {
						return true;
					} else if (isNotBlank(record.getAttributeAsString("toponimo")) && isNotBlank(record.getAttributeAsString("comune")) /*
																																		 * && isNotBlank(record.
																																		 * getAttributeAsString
																																		 * ("cap"))
																																		 */) {
						return true;
					}
				} else {
					if (isBlank(record.getAttributeAsString("indirizzo")) && isBlank(record.getAttributeAsString("citta"))) {
						return true;
					} else if (isNotBlank(record.getAttributeAsString("indirizzo")) && isNotBlank(record.getAttributeAsString("citta"))) {
						return true;
					}
				}
				return isNotBlank((String) value);
			}
		};
		indirizzoRichiedenteValidator.setErrorMessage("Campo obbligatorio");

		final GWTRestDataSource tipoDS = new GWTRestDataSource("TipoIndirizzoDataSource", "key", FieldType.TEXT);
		final GWTRestDataSource statoDS = new GWTRestDataSource("StatoDataSource", "codIstatStato", FieldType.TEXT, true);
		final GWTRestDataSource tipoToponimoDS = new GWTRestDataSource("TipoToponimoDataSource", "key", FieldType.TEXT);
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			tipoToponimoDS.addParam("obbligatorio", "true");
		}
		final GWTRestDataSource comuniDS = new GWTRestDataSource("ComuneDataSource", "codIstatComune", FieldType.TEXT, true);
		comuniDS.addParam("flgSoloVld", "1");
		final GWTRestDataSource frazioniDS = new GWTRestDataSource("FrazioneDataSource", "frazione", FieldType.TEXT);
		final GWTRestDataSource capDS = new GWTRestDataSource("CapDataSource", "cap", FieldType.TEXT);

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		rowIdItem = new HiddenItem("rowId");

		tipoItem = new SelectItem("tipo");
		tipoItem.setShowTitle(false);
		tipoItem.setValueField("key");
		tipoItem.setDisplayField("value");
		tipoItem.setOptionDataSource(tipoDS);
		tipoItem.setWidth(150);
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			tipoItem.setRequired(true);
		}
		tipoItem.setCachePickListResults(false);
		// tipoItem.setAllowEmptyValue(true);
		tipoItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				criterias[0] = new Criterion("flgPersonaFisica", OperatorId.EQUALS, ((IndirizziRichiedenteItem) getItem()).getFlgPersonaFisica());
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		// tipoItem.setDefaultToFirstOption(true);
		String flgPersonaFisica = ((IndirizziRichiedenteItem) getItem()).getFlgPersonaFisica();
		if (flgPersonaFisica != null && "1".equals(flgPersonaFisica)) {
			tipoItem.setDefaultValue("RS"); // Residenza
		} else {
			tipoItem.setDefaultValue("SL"); // Sede Legale
		}

		String tipoIndirizzo = ((IndirizziRichiedenteItem) getItem()).getTipoIndirizzo();
		if (tipoIndirizzo != null && !"".equals(tipoIndirizzo)) {
			tipoItem.setVisible(false);
			tipoItem.setDefaultValue(tipoIndirizzo);
		}

		if (!((IndirizziRichiedenteItem) getItem()).isIndirizzoRichiedentePratica()) {
			dataValidoDalItem = new DateItem("dataValidoDal", I18NUtil.getMessages().soggetti_detail_indirizzi_dataValidoDalItem_title());
			dataValidoDalItem.setColSpan(1);
			dataValidoDalItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					
					GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
					statoDS.addParam("tsVld", DateUtil.format((Date) event.getValue()));
					statoItem.setOptionDataSource(statoDS);

					GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
					comuniDS.addParam("tsVld", DateUtil.format((Date) event.getValue()));
					comuneItem.setOptionDataSource(comuniDS);
				}
			});

			dataValidoFinoAlItem = new DateItem("dataValidoFinoAl", I18NUtil.getMessages().soggetti_detail_indirizzi_dataValidoFinoAlItem_title());
			dataValidoFinoAlItem.setColSpan(1);
		}

		statoItem = new FilteredSelectItem("stato", I18NUtil.getMessages().soggetti_detail_indirizzi_statoItem_title()) {

			@Override
			protected void clearSelect() {
				super.clearSelect();
				comuneItem.setValue("");
				provinciaItem.setValue("");
				cittaItem.setValue("");
				mDynamicForm.redraw();
			};
		};
		statoItem.setStartRow(true);
		// statoItem.setFilterLocally(true);
		ListGridField codIstatStatoField = new ListGridField("codIstatStato", "Cod. Istat");
		codIstatStatoField.setHidden(true);
		ListGridField nomeStatoField = new ListGridField("nomeStato", "Stato");
		statoItem.setPickListFields(codIstatStatoField, nomeStatoField);
		statoItem.setValueField("nomeStato");
		statoItem.setDisplayField("nomeStato");
		statoItem.setOptionDataSource(statoDS);
		statoItem.setColSpan(6);
		statoItem.setWidth(250);
		statoItem.setDefaultValue("ITALIA"); // ITALIA (cod = 200)
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			statoItem.setRequired(true);
		}
		// statoItem.setCachePickListResults(false);
		// statoItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
		// @Override
		// public Criteria getCriteria(FormItemFunctionContext itemContext) {
		// if(statoItem.getValue() != null && !"".equals((String) statoItem.getValue())) {
		// Criterion[] criterias = new Criterion[1];
		// criterias[0] = new Criterion("codIstatStato", OperatorId.EQUALS, (String) statoItem.getValue());
		// return new AdvancedCriteria(OperatorId.AND, criterias);
		// }
		// if(!((IndirizziRichiedenteItem)getItem()).isIndirizzoRichiedentePratica() && dataValidoDalItem.getValue() != null) {
		// Criterion[] criterias = new Criterion[1];
		// criterias[0] = new Criterion("tsVld", OperatorId.EQUALS, (String) dataValidoDalItem.getValue());
		// return new AdvancedCriteria(OperatorId.AND, criterias);
		// }
		// return null;
		// }
		// });
		statoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				mDynamicForm.redraw();
			}
		});

		tipoToponimoItem = new SelectItem("tipoToponimo");
		tipoToponimoItem.setValueField("key");
		tipoToponimoItem.setDisplayField("displayValue");
		tipoToponimoItem.setPickListFields(new ListGridField("value"));
		tipoToponimoItem.setShowTitle(false);
		tipoToponimoItem.setWidth(150);
		tipoToponimoItem.setStartRow(true);
		tipoToponimoItem.setAllowEmptyValue(false);
		tipoToponimoItem.setOptionDataSource(tipoToponimoDS);
		tipoToponimoItem.setDefaultValue("VIA");
		tipoToponimoItem.setAutoFetchData(true);
		tipoToponimoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isStatoItalia();
			}
		});
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			tipoToponimoItem.setAttribute("obbligatorio", true);
			tipoToponimoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					
					return isStatoItalia();
				}
			}));
		}

		toponimoItem = new TextItem("toponimo");
		toponimoItem.setShowTitle(false);
		toponimoItem.setWidth(250);
		toponimoItem.setColSpan(6);
		toponimoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isStatoItalia();
			}
		});
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			toponimoItem.setAttribute("obbligatorio", true);
			toponimoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					
					return isStatoItalia();
				}
			}));
		} else {
			toponimoItem.setValidators(indirizzoRichiedenteValidator);
		}

		indirizzoItem = new TextItem("indirizzo", I18NUtil.getMessages().soggetti_detail_indirizzi_indirizzoItem_title());
		indirizzoItem.setStartRow(true);
		indirizzoItem.setWidth(250);
		indirizzoItem.setColSpan(6);
		indirizzoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isStatoItalia();
			}
		});
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			indirizzoItem.setAttribute("obbligatorio", true);
			indirizzoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					
					return !isStatoItalia();
				}
			}));
		} else {
			indirizzoItem.setValidators(indirizzoRichiedenteValidator);
		}

		civicoItem = new TextItem("civico", I18NUtil.getMessages().soggetti_detail_indirizzi_civicoItem_title());
		civicoItem.setWidth(50);
		civicoItem.setLength(5);
		civicoItem.setTitleColSpan(2);
		if (!((IndirizziRichiedenteItem) getItem()).isIndirizzoRichiedentePratica()) {
			civicoItem.setHint("&nbsp;/");
			civicoItem.setHintStyle(it.eng.utility.Styles.formTitle);
		}

		appendiciItem = new TextItem("appendici");
		appendiciItem.setWidth(50);
		appendiciItem.setColSpan(1);
		appendiciItem.setTitleOrientation(TitleOrientation.RIGHT);
		appendiciItem.setShowTitle(false);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);

		nomeComuneItem = new HiddenItem("nomeComune");

		comuneItem = new FilteredSelectItem("comune", I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_title()) {

			@Override
			public void onOptionClick(Record record) {
				
				nomeComuneItem.setValue(record.getAttribute("nomeComune"));

				GWTRestDataSource comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				comuneDS.addParam("nomeComune", record.getAttribute("nomeComune"));
				comuneItem.setOptionDataSource(comuneDS);

				provinciaItem.setValue(record.getAttribute("targaProvincia"));
				Criterion[] criterias = new Criterion[1];
				String value = record.getAttribute(comuneItem.getValueField());
				if (value != null && !"".equals(value)) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, value);
				}
				frazioneItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						Map frazioneValueMap = response.getDataAsRecordList().getValueMap(frazioneItem.getValueField(), frazioneItem.getDisplayField());
						if (frazioneItem.getValue() != null && !"".equals(frazioneItem.getValueAsString())
								&& !frazioneValueMap.containsKey(frazioneItem.getValueAsString())) {
							frazioneItem.setValue("");
						}
						Criterion[] criterias = new Criterion[1];
						if (frazioneItem.getValue() != null && !"".equals(frazioneItem.getValueAsString())) {
							criterias[0] = new Criterion("frazione", OperatorId.IEQUALS, frazioneItem.getValueAsString());
						} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
							criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comuneItem.getValueAsString());
						}
						capItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								Map capValueMap = response.getDataAsRecordList().getValueMap(capItem.getValueField(), capItem.getDisplayField());
								if (capItem.getValue() != null && !"".equals(capItem.getValueAsString())
										&& !capValueMap.containsKey(capItem.getValueAsString())) {
									capItem.setValue("");
								}
								if (capValueMap.keySet().size() == 1) {
									capItem.setValue(capValueMap.keySet().iterator().next());
								}
							}
						});
					}
				});
			}

			@Override
			public void setValue(String value) {
				
				super.setValue(value);
				if (value == null || "".equals(value)) {
					nomeComuneItem.setValue("");

					GWTRestDataSource comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
					comuneDS.addParam("nomeComune", null);
					comuneItem.setOptionDataSource(comuneDS);

					provinciaItem.setValue("");
				}
			}
		};
		ListGridField codIstatComuneField = new ListGridField("codIstatComune", "Cod. Istat");
		codIstatComuneField.setHidden(true);
		ListGridField nomeComuneField = new ListGridField("nomeComune", "Comune");
		ListGridField targaProvinciaField = new ListGridField("targaProvincia", "Prov.");
		targaProvinciaField.setWidth(50);
		comuneItem.setPickListFields(codIstatComuneField, nomeComuneField, targaProvinciaField);
		comuneItem.setEmptyPickListMessage(I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage());
		// comuneItem.setFilterLocally(true);
		comuneItem.setValueField("codIstatComune");
		comuneItem.setDisplayField("nomeComune");
		comuneItem.setOptionDataSource(comuniDS);
		comuneItem.setWidth(250);
		comuneItem.setTitleColSpan(2);
		comuneItem.setColSpan(5);
		// comuneItem.setCachePickListResults(false);
		// comuneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
		// @Override
		// public Criteria getCriteria(FormItemFunctionContext itemContext) {
		// if(comuneItem.getValue() != null && !"".equals((String) comuneItem.getValue())) {
		// Criterion[] criterias = new Criterion[1];
		// criterias[0] = new Criterion("codIstatComune", OperatorId.EQUALS, (String) comuneItem.getValue());
		// return new AdvancedCriteria(OperatorId.AND, criterias);
		// }
		// if(!((IndirizziRichiedenteItem)getItem()).isIndirizzoRichiedentePratica() && dataValidoDalItem.getValue() != null) {
		// Criterion[] criterias = new Criterion[1];
		// criterias[0] = new Criterion("tsVld", OperatorId.EQUALS, (String) dataValidoDalItem.getValue());
		// return new AdvancedCriteria(OperatorId.AND, criterias);
		// }
		// return null;
		// }
		// });
		comuneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isStatoItalia();
			}
		});
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			comuneItem.setAttribute("obbligatorio", true);
			comuneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					
					return isStatoItalia();
				}
			}));
		} else {
			comuneItem.setValidators(indirizzoRichiedenteValidator);
			comuneItem.setClearable(true);
		}

		cittaItem = new TextItem("citta", I18NUtil.getMessages().soggetti_detail_indirizzi_cittaItem_title());
		cittaItem.setWidth(250);
		cittaItem.setStartRow(true);
		cittaItem.setColSpan(6);
		cittaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isStatoItalia();
			}
		});
		if (getItem().isObbligatorio() || !getItem().getNotReplicable()) {
			cittaItem.setAttribute("obbligatorio", true);
			cittaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					
					return !isStatoItalia();
				}
			}));
		} else {
			cittaItem.setValidators(indirizzoRichiedenteValidator);
		}

		provinciaItem = new TextItem("provincia", I18NUtil.getMessages().soggetti_detail_indirizzi_provinciaItem_title());
		provinciaItem.setWidth(50);
		provinciaItem.setColSpan(1);
		provinciaItem.setCanEdit(false);
		provinciaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isStatoItalia();
			}
		});

		frazioneItem = new SelectItem("frazione", I18NUtil.getMessages().soggetti_detail_indirizzi_frazioneItem_title()) {

			@Override
			public void onOptionClick(Record record) {
				
				Criterion[] criterias = new Criterion[1];
				String value = record.getAttribute(frazioneItem.getValueField());
				if (value != null && !"".equals(value)) {
					criterias[0] = new Criterion("frazione", OperatorId.IEQUALS, value);
				} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comuneItem.getValueAsString());
				}
				capItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						Map capValueMap = response.getDataAsRecordList().getValueMap(capItem.getValueField(), capItem.getDisplayField());
						if (capItem.getValue() != null && !"".equals(capItem.getValueAsString()) && !capValueMap.containsKey(capItem.getValueAsString())) {
							capItem.setValue("");
						}
						if (capValueMap.keySet().size() == 1) {
							capItem.setValue(capValueMap.keySet().iterator().next());
						}
					}
				});
			}
		};
		// ListGridField frazioneField = new ListGridField("frazione", "Frazione");
		// frazioneItem.setPickListFields(frazioneField);
		frazioneItem.setValueField("frazione");
		frazioneItem.setDisplayField("frazione");
		frazioneItem.setOptionDataSource(frazioniDS);
		frazioneItem.setWidth(250);
		frazioneItem.setColSpan(6);
		frazioneItem.setStartRow(true);
		// frazioneItem.setClearable(true);
		frazioneItem.setAllowEmptyValue(true);
		frazioneItem.setCachePickListResults(false);
		frazioneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comuneItem.getValueAsString());
				} else {
					criterias[0] = new Criterion("frazione", OperatorId.IS_NULL);
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});

		frazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isStatoItalia();
			}
		});

		capItem = new SelectItem("cap", I18NUtil.getMessages().soggetti_detail_indirizzi_capItem_title());
		ListGridField capField = new ListGridField("cap", "Cap");
		capItem.setPickListFields(capField);
		capItem.setValueField("cap");
		capItem.setDisplayField("cap");
		capItem.setOptionDataSource(capDS);
		capItem.setWidth(80);
		capItem.setColSpan(3);
		capItem.setTitleColSpan(2);
		capItem.setCachePickListResults(false);
		capItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				if (frazioneItem.getValue() != null && !"".equals(frazioneItem.getValueAsString())) {
					criterias[0] = new Criterion("frazione", OperatorId.IEQUALS, frazioneItem.getValueAsString());
				} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comuneItem.getValueAsString());
				} else {
					criterias[0] = new Criterion("cap", OperatorId.IS_NULL);
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		capItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isStatoItalia();
			}
		});
		// if(getItem().isObbligatorio() || !getItem().getNotReplicable()) {
		// capItem.setAttribute("obbligatorio", true);
		// capItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		// @Override
		// public boolean execute(FormItem formItem, Object value) {
		// 
		// return isStatoItalia();
		// }
		// }));
		// } else {
		// capItem.setValidators(indirizzoRichiedenteValidator);
		// capItem.setClearable(true);
		capItem.setAllowEmptyValue(true);
		// }

		zonaItem = new TextItem("zona", I18NUtil.getMessages().soggetti_detail_indirizzi_zonaItem_title());
		zonaItem.setWidth(150);
		zonaItem.setColSpan(5);

		complementoIndirizzoItem = new TextItem("complementoIndirizzo", I18NUtil.getMessages().soggetti_detail_indirizzi_complementoIndirizzoItem_title());
		complementoIndirizzoItem.setStartRow(true);
		complementoIndirizzoItem.setWidth(250);
		complementoIndirizzoItem.setWrapTitle(false);
		complementoIndirizzoItem.setColSpan(6);

		if (!((IndirizziRichiedenteItem) getItem()).isIndirizzoRichiedentePratica()) {
			mDynamicForm.setFields(rowIdItem, tipoItem, dataValidoDalItem, dataValidoFinoAlItem, statoItem, tipoToponimoItem, toponimoItem, indirizzoItem,
					civicoItem, appendiciItem, nomeComuneItem, comuneItem, cittaItem, provinciaItem, frazioneItem, capItem, zonaItem, complementoIndirizzoItem);
		} else {
			statoItem.setColSpan(2);
			statoItem.setTitleColSpan(1);
			tipoToponimoItem.setColSpan(1);
			toponimoItem.setColSpan(2);
			indirizzoItem.setTitleColSpan(1);
			indirizzoItem.setColSpan(2);
			civicoItem.setTitleColSpan(1);
			civicoItem.setColSpan(2);
			comuneItem.setStartRow(true);
			comuneItem.setTitleColSpan(1);
			comuneItem.setColSpan(2);
			cittaItem.setTitleColSpan(1);
			cittaItem.setColSpan(2);
			provinciaItem.setTitleColSpan(1);
			provinciaItem.setColSpan(2);
			frazioneItem.setTitleColSpan(1);
			frazioneItem.setColSpan(2);
			capItem.setTitleColSpan(1);
			capItem.setColSpan(2);

			mDynamicForm.setFields(rowIdItem, statoItem, tipoToponimoItem, toponimoItem, indirizzoItem, civicoItem, nomeComuneItem, comuneItem, cittaItem,
					provinciaItem, frazioneItem, capItem);
		}

		mDynamicForm.setNumCols(30);

		addChild(mDynamicForm);

	}

	public void validateTipo(String flgPersonaFisica) {
		mDynamicForm.redraw();
		Criterion[] criterias = new Criterion[1];
		criterias[0] = new Criterion("flgPersonaFisica", OperatorId.IEQUALS, flgPersonaFisica);
		tipoItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				Map tipoValueMap = response.getDataAsRecordList().getValueMap(tipoItem.getValueField(), tipoItem.getDisplayField());
				if (tipoItem.getValue() != null && !"".equals(tipoItem.getValueAsString()) && !tipoValueMap.containsKey(tipoItem.getValueAsString())) {
					tipoItem.setValue("");
				}
			}
		});
	}

	protected boolean isStatoItalia() {
		String stato = ((statoItem.getValue() != null) ? (String) statoItem.getValue() : "");
		return "".equals(stato) || "ITALIA".equals(stato);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		provinciaItem.setCanEdit(false);
	}

	@Override
	public void editRecord(Record record) {
		
		// GWTRestDataSource tipoDS = (GWTRestDataSource) tipoItem.getOptionDataSource();
		// if(record.getAttribute("rowId") != null && !"".equals(record.getAttributeAsString("rowId"))) {
		// tipoDS.addParam("rowId", record.getAttributeAsString("rowId"));
		// } else {
		// tipoDS.addParam("rowId", null);
		// }
		// String flgPersonaFisica = getParams() != null ? getParams().get("flgPersonaFisica") : "";
		// tipoDS.addParam("flgPersonaFisica", flgPersonaFisica);
		// tipoItem.setOptionDataSource(tipoDS);

		GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
		if (record.getAttribute("stato") != null && !"".equals(record.getAttributeAsString("stato"))) {
			statoDS.addParam("codIstatStato", record.getAttributeAsString("stato"));
		} else {
			statoDS.addParam("codIstatStato", null);
		}
		if (!((IndirizziRichiedenteItem) getItem()).isIndirizzoRichiedentePratica()) {
			try {
				statoDS.addParam("tsVld", record.getAttributeAsDate("dataValidoDal") != null ? DateUtil.format(record.getAttributeAsDate("dataValidoDal")) : null);
			} catch(Exception e) {
				statoDS.addParam("tsVld", record.getAttribute("dataValidoDal"));
			}
		}
		statoItem.setOptionDataSource(statoDS);

		GWTRestDataSource tipoToponimoDS = (GWTRestDataSource) tipoToponimoItem.getOptionDataSource();
		if (record.getAttribute("rowId") != null && !"".equals(record.getAttributeAsString("rowId"))) {
			tipoToponimoDS.addParam("rowId", record.getAttributeAsString("rowId"));
		} else {
			tipoToponimoDS.addParam("rowId", null);
		}
		tipoToponimoItem.setOptionDataSource(tipoToponimoDS);

		GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
		if (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune"))) {
			comuniDS.addParam("codIstatComune", record.getAttributeAsString("comune"));
			comuniDS.addParam("nomeComune", record.getAttributeAsString("nomeComune"));
		} else {
			comuniDS.addParam("codIstatComune", null);
			comuniDS.addParam("nomeComune", null);
		}
		if (!((IndirizziRichiedenteItem) getItem()).isIndirizzoRichiedentePratica()) {
			try {
				comuniDS.addParam("tsVld", record.getAttributeAsDate("dataValidoDal") != null ? DateUtil.format(record.getAttributeAsDate("dataValidoDal")) : null);
			} catch(Exception e) {
				comuniDS.addParam("tsVld", record.getAttribute("dataValidoDal"));
			}			
		}
		comuneItem.setOptionDataSource(comuniDS);

		super.editRecord(record);
	}

	private boolean isBlank(String str) {
		return str == null || "".equals(str);
	}

	private boolean isNotBlank(String str) {
		return str != null && !"".equals(str);
	}
}
