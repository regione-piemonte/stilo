/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class FruitoriCasellaCanvas extends ReplicableCanvas {

	private SelectItem tipoItem;
	private ExtendedTextItem codRapidoItem;
	private FilteredSelectItemWithDisplay fruitoreItem;

	private CheckboxItem flgSmistatoreItem;
	private CheckboxItem flgMittenteItem;
	private CheckboxItem flgAmministratoreItem;
	private CheckboxItem flgIncludiSubordinatiItem;

	private ReplicableCanvasForm mDynamicForm;

	public FruitoriCasellaCanvas(FruitoriCasellaItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		tipoItem = new SelectItem("tipo");
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		tipoValueMap.put("ENTE", I18NUtil.getMessages().caselleEmail_soggettiAbilitati_tipoFruitore_ENTE_value());
		tipoValueMap.put("UO", I18NUtil.getMessages().caselleEmail_soggettiAbilitati_tipoFruitore_UO_value());
		tipoItem.setValueMap(tipoValueMap);
		tipoItem.setShowTitle(false);
		tipoItem.setWidth(100);
		tipoItem.setRequired(true);
		tipoItem.setAllowEmptyValue(false);
		tipoItem.setColSpan(1);
		tipoItem.setDefaultValue("UO");
		tipoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});

		codRapidoItem = new ExtendedTextItem("codRapido", "Cod.");
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idFruitoreCasella", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					fruitoreItem.fetchData(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									if (value.equals(codice)) {
										mDynamicForm.setValue("idFruitoreCasella", data.get(i).getAttribute("idFruitoreCasella"));
										mDynamicForm.clearErrors(true);
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codRapidoItem.validate();
								codRapidoItem.blurItem();
							}
						}
					});
				} else {
					fruitoreItem.fetchData();
				}
			}
		});
		codRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) && fruitoreItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});
		codRapidoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && tipoItem.getValue() != null
						&& "UO".equals(tipoItem.getValueAsString());
			}
		});

		SelectGWTRestDataSource fruitoreDS = new SelectGWTRestDataSource("LoadComboFruitoriCasellaDataSource", "idFruitoreCasella", FieldType.TEXT,
				new String[] { "denominazione" }, true);
		fruitoreDS.addParam("dominio", getItem().getForm().getValuesManager().getValueAsString("idSpAoo"));

		fruitoreItem = new FilteredSelectItemWithDisplay("idFruitoreCasella", fruitoreDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("idFruitoreCasella", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codice"));
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
					@Override
					public void execute() {
						fruitoreItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idFruitoreCasella", "");				
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codRapido", "");
				}				
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
					@Override
					public void execute() {
						fruitoreItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idFruitoreCasella", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codRapido", "");
					}
					mDynamicForm.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							fruitoreItem.fetchData();
						}
					});
				}
			}
		};
		List<ListGridField> fruitorePickListFields = new ArrayList<ListGridField>();
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			ListGridField tipoUoField = new ListGridField("iconaTipoUo", "Tipo");
			tipoUoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			tipoUoField.setAlign(Alignment.CENTER);
			tipoUoField.setWidth(30);
			tipoUoField.setShowHover(false);
			tipoUoField.setCanFilter(false);
			tipoUoField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("tipoUo") != null && !"".equals(record.getAttributeAsString("tipoUo"))) {
						return "<div align=\"center\"><img src=\"images/organigramma/tipo/UO_" + record.getAttribute("tipoUo")
								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			fruitorePickListFields.add(tipoUoField);
		}
		ListGridField codiceField = new ListGridField("codice", "Cod.");
		codiceField.setWidth(120);
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codiceField.setCanFilter(false);
			// TextItem codiceFilterEditorType = new TextItem();
			// codiceFilterEditorType.setCanEdit(false);
			// codiceField.setFilterEditorType(codiceFilterEditorType);
		}
		fruitorePickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("denominazione", "Denominazione");
		descrizioneField.setWidth("*");
		fruitorePickListFields.add(descrizioneField);
		fruitoreItem.setPickListFields(fruitorePickListFields.toArray(new ListGridField[fruitorePickListFields.size()]));
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			fruitoreItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());			
		} else {
			fruitoreItem.setFilterLocally(true);
		}
		fruitoreItem.setAutoFetchData(false);
		fruitoreItem.setAlwaysFetchMissingValues(true);
		fruitoreItem.setFetchMissingValues(true);
		fruitoreItem.setValueField("idFruitoreCasella");
		fruitoreItem.setShowTitle(false);
		fruitoreItem.setWidth(300);
		fruitoreItem.setClearable(true);
		fruitoreItem.setShowIcons(true);
		fruitoreItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("denominazione") : null;
			}
		});
		fruitoreItem.setAttribute("obbligatorio", true);
		fruitoreItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoItem.getValue() != null && "UO".equals(tipoItem.getValueAsString());
			}
		});
		fruitoreItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return tipoItem.getValue() != null && "UO".equals(tipoItem.getValueAsString());
			}
		}));

		ListGrid pickListProperties = fruitoreItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = mDynamicForm.getValueAsString("codRapido");
//				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codRapido == null || "".equals(codRapido))) {
//					if(codRapidoItem.getCanEdit() != null && codRapidoItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors("codRapido", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) fruitoreItem.getOptionDataSource();
				// organigrammaDS.addParam("ciToAdd", mDynamicForm.getValueAsString("idFruitoreCasella"));
				organigrammaDS.addParam("codice", codRapido);
				fruitoreItem.setOptionDataSource(organigrammaDS);
				fruitoreItem.invalidateDisplayValueCache();
			}
		});
		fruitoreItem.setPickListProperties(pickListProperties);
		
		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png",
				I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				FruitoreCasellaLookupOrganigramma fruitoreCasellaLookupOrganigramma = new FruitoreCasellaLookupOrganigramma(null);
				fruitoreCasellaLookupOrganigramma.show();
			}
		});
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"ENTE".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});
		
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setColSpan(1);
		spacer.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"ENTE".equalsIgnoreCase(tipoItem.getValueAsString());
			}
		});

		flgSmistatoreItem = new CheckboxItem("flgSmistatore", "smistatore");
		flgSmistatoreItem.setValue(false);
		flgSmistatoreItem.setColSpan(1);
		flgSmistatoreItem.setWidth("*");

		flgMittenteItem = new CheckboxItem("flgMittente", "mittente");
		flgMittenteItem.setValue(false);
		flgMittenteItem.setColSpan(1);
		flgMittenteItem.setWidth("*");

		flgAmministratoreItem = new CheckboxItem("flgAmministratore", "amministratore");
		flgAmministratoreItem.setValue(false);
		flgAmministratoreItem.setColSpan(1);
		flgAmministratoreItem.setWidth("*");

		flgIncludiSubordinatiItem = new CheckboxItem("flgIncludiSubordinati", "includi subordinati");
		flgIncludiSubordinatiItem.setValue(false);
		flgIncludiSubordinatiItem.setColSpan(1);
		flgIncludiSubordinatiItem.setWidth("*");

		mDynamicForm.setFields(tipoItem, codRapidoItem,
				lookupOrganigrammaButton, spacer,
				fruitoreItem, flgSmistatoreItem, flgMittenteItem, flgAmministratoreItem, flgIncludiSubordinatiItem);

		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		addChild(mDynamicForm);
	}

	@Override
	public void editRecord(Record record) {	
		manageLoadSelectInEditRecord(record, fruitoreItem, "idFruitoreCasella", "denominazione", "idFruitoreCasella");
		super.editRecord(record);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	public void setFormValuesFromRecord(final Record record) {
		
		mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
		mDynamicForm.setValue("idFruitoreCasella", (String) null);
		mDynamicForm.clearErrors(true);
		final String value = record.getAttribute("codRapidoUo");
		if (value != null && !"".equals(value)) {
			fruitoreItem.fetchData(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codice = data.get(i).getAttribute("codice");
							if (value.equals(codice)) {
								mDynamicForm.setValue("idFruitoreCasella", data.get(i).getAttribute("idFruitoreCasella"));
								mDynamicForm.clearErrors(true);
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						codRapidoItem.validate();
						codRapidoItem.blurItem();
					}
				}
			});
		} else {
			fruitoreItem.fetchData();
		}

	}
	
	public class FruitoreCasellaLookupOrganigramma extends LookupOrganigrammaPopup {

		public FruitoreCasellaLookupOrganigramma(Record record) {
			super(record, true, new Integer(0));
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String getFinalita() {
			return null;
		}

	}

}
