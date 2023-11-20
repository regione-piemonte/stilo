/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class PermessiCanvas extends ReplicableCanvas {
	
	private SelectItem tipoDestinatarioItem;
	private ExtendedTextItem codiceUoItem;
	private ExtendedTextItem codiceRapidoItem;
	private HiddenItem descrOrganigrammaHiddenItem;
	private HiddenItem idOrganigrammaHiddenItem;
	private HiddenItem tipoOrganigrammaHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private FilteredSelectItemWithDisplay utentiItem;
	private HiddenItem idGruppoInternoHiddenItem;
	private HiddenItem idGruppoEsternoHiddenItem;
	private FilteredSelectItemWithDisplay gruppiInterniItem;
	private SelectItem opzioniAccessoItem;
	private HiddenItem flgEreditataHiddenItem;
	private HiddenItem flgAssegnatarioHiddenItem;
	private HiddenItem flgInvioPerConoscenzaHiddenItem;

	private ReplicableCanvasForm mDynamicForm;

	public PermessiCanvas(PermessiItem item) {
		super(item);
	}

	public PermessiCanvas(PermessiItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		// tipo
		tipoDestinatarioItem = new SelectItem("tipoDestinatario");
		LinkedHashMap<String, String> tipoDestinatarioValueMap = new LinkedHashMap<String, String>();
		tipoDestinatarioValueMap.put("AOO", "Intera AOO");
		tipoDestinatarioValueMap.put("UO+SV", "Unità di personale/U.O.");
		tipoDestinatarioValueMap.put("UT", "Utente");
		tipoDestinatarioValueMap.put("G", "Lista di distribuzione");
		tipoDestinatarioItem.setDefaultValue("UO+SV");
		tipoDestinatarioItem.setShowTitle(false);
		tipoDestinatarioItem.setValueMap(tipoDestinatarioValueMap);
		tipoDestinatarioItem.setWidth(150);
		tipoDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearErrors(true);
				Record lRecord = mDynamicForm.getValuesAsRecord();
				lRecord.setAttribute("codiceUo", "");
				lRecord.setAttribute("codiceRapido", "");
				lRecord.setAttribute("tipoOrganigramma", "");
				lRecord.setAttribute("idOrganigramma", "");
				lRecord.setAttribute("organigramma", "");
				lRecord.setAttribute("idGruppo", "");
				lRecord.setAttribute("idGruppoInterno", "");
				lRecord.setAttribute("idGruppoEsterno", "");
				mDynamicForm.setValues(lRecord.toMap());
				mDynamicForm.markForRedraw();
			}
		});

		idOrganigrammaHiddenItem = new HiddenItem("idOrganigramma");
		tipoOrganigrammaHiddenItem = new HiddenItem("tipoOrganigramma");

		codiceUoItem = new ExtendedTextItem("codiceUo", "Cod. rapido");
		codiceUoItem.setWidth(120);
		codiceUoItem.setColSpan(1);
		codiceUoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "UO+SV".equals(tipoDestinatario);
			}
		});
		codiceUoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idOrganigramma", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("organigramma", (String) null);
				mDynamicForm.setValue("tipoOrganigramma", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codiceUoItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					organigrammaItem.fetchData(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									if (value.equals(codice)) {
										mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
										mDynamicForm.setValue("organigramma", data.get(i).getAttribute("id"));
										mDynamicForm.setValue("idOrganigramma", data.get(i).getAttribute("idUo"));
										mDynamicForm.setValue("tipoOrganigramma", data.get(i).getAttribute("typeNodo"));
										mDynamicForm.clearErrors(true);
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codiceUoItem.validate();
								codiceUoItem.blurItem();
							}
						}
					});
				} else {
					organigrammaItem.fetchData();
				}
			}
		});
		codiceUoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				if (codiceUoItem.getValue() != null && !"".equals(codiceUoItem.getValueAsString().trim())) {
					if (("UO+SV".equalsIgnoreCase(tipoDestinatario) && organigrammaItem.getValue() == null)) {
						return false;
					}
				}
				return true;
			}
		});

		descrOrganigrammaHiddenItem = new HiddenItem("descrizione");

		SelectGWTRestDataSource lGwtRestDataSourceOrganigramma = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		lGwtRestDataSourceOrganigramma.addParam("finalita", "ACL");
		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", lGwtRestDataSourceOrganigramma) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("organigramma", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codiceUo", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("tipoOrganigramma", record.getAttributeAsString("typeNodo"));
				mDynamicForm.setValue("idOrganigramma", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.clearErrors(true);			
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("organigramma", "");
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					mDynamicForm.setValue("codiceUo", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codiceUo", "");
				}
				mDynamicForm.setValue("idOrganigramma", "");
				mDynamicForm.setValue("tipoOrganigramma", "");
				mDynamicForm.setValue("descrizione", "");
				mDynamicForm.clearErrors(true);				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("organigramma", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						mDynamicForm.setValue("codiceUo", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codiceUo", "");
					}
					mDynamicForm.setValue("idOrganigramma", "");
					mDynamicForm.setValue("tipoOrganigramma", "");
					mDynamicForm.setValue("descrizione", "");
					mDynamicForm.clearErrors(true);					
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaPickListFields = new ArrayList<ListGridField>();
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			ListGridField typeNodoField = new ListGridField("iconaTypeNodo", "Tipo");
			typeNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			typeNodoField.setAlign(Alignment.CENTER);
			typeNodoField.setWidth(30);
			typeNodoField.setShowHover(false);
			typeNodoField.setCanFilter(false);
			typeNodoField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("iconaTypeNodo") != null && !"".equals(record.getAttributeAsString("iconaTypeNodo"))) {
						return "<div align=\"center\"><img src=\"images/organigramma/tipo/" + record.getAttribute("iconaTypeNodo")
								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(typeNodoField);
		}
		final ListGridField organigrammaCodiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		organigrammaCodiceField.setWidth(80);
		organigrammaCodiceField.setShowHover(true);
		organigrammaCodiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("descrizioneEstesa");
			}
		});
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			organigrammaCodiceField.setCanFilter(false);
			// TextItem organigrammaCodiceFilterEditorType = new TextItem();
			// organigrammaCodiceFilterEditorType.setCanEdit(false);
			// organigrammaCodiceField.setFilterEditorType(organigrammaCodiceFilterEditorType);
		}
		organigrammaPickListFields.add(organigrammaCodiceField);
		ListGridField organigrammaDescrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		organigrammaDescrizioneField.setWidth("*");
		organigrammaPickListFields.add(organigrammaDescrizioneField);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			ListGridField organigrammaFlgPuntoProtocolloField = new ListGridField("flgPuntoProtocollo", "Punto di Protocollo");
			organigrammaFlgPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			organigrammaFlgPuntoProtocolloField.setAlign(Alignment.CENTER);
			organigrammaFlgPuntoProtocolloField.setWidth(30);
			organigrammaFlgPuntoProtocolloField.setShowHover(true);
			organigrammaFlgPuntoProtocolloField.setCanFilter(false);
			organigrammaFlgPuntoProtocolloField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			organigrammaFlgPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record != null && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "Punto di Protocollo";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(organigrammaFlgPuntoProtocolloField);
		}
		organigrammaItem.setPickListFields(organigrammaPickListFields.toArray(new ListGridField[organigrammaPickListFields.size()]));
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			organigrammaItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());
		} else {
			organigrammaItem.setFilterLocally(true);
		}
		organigrammaItem.setAutoFetchData(false);
		organigrammaItem.setAlwaysFetchMissingValues(true);
		organigrammaItem.setFetchMissingValues(true);
		organigrammaItem.setValueField("id");
		organigrammaItem.setOptionDataSource(lGwtRestDataSourceOrganigramma);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(((PermessiItem)getItem()).getFilteredSelectItemWidth());
		organigrammaItem.setRequired(true);
		organigrammaItem.setClearable(true);
		organigrammaItem.setShowIcons(true);
		organigrammaItem.setClipStaticValue(false);
		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "UO+SV".equalsIgnoreCase(tipoDestinatarioItem.getValueAsString());
			}
		});
		organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return "UO+SV".equalsIgnoreCase(tipoDestinatarioItem.getValueAsString());
			}
		}));

		ListGrid pickListProperties = organigrammaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codiceUo = mDynamicForm.getValueAsString("codiceUo");
//				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codiceUo == null || "".equals(codiceUo))) {
//					if(codiceUoItem.getCanEdit() != null && codiceUoItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors("codiceUo", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}				
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				// organigrammaDS.addParam("ciToAdd", mDynamicForm.getValueAsString("organigramma"));
				organigrammaDS.addParam("codice", codiceUo);
				organigrammaItem.setOptionDataSource(organigrammaDS);
				organigrammaItem.invalidateDisplayValueCache();
			}
		});
		organigrammaItem.setPickListProperties(pickListProperties);

		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png",
				I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				PermessiLookupOrganigramma lookupOrganigrammaPopup = new PermessiLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "UO+SV".equalsIgnoreCase(tipoDestinatarioItem.getValueAsString());
			}
		});

		codiceRapidoItem = new ExtendedTextItem("codiceRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoItem.setWidth(120);
		codiceRapidoItem.setColSpan(1);
		codiceRapidoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && ("UT".equals(tipoDestinatario) || "G".equals(tipoDestinatario));
			}
		});
		codiceRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				final String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				if ("UT".equals(tipoDestinatario)) {
					mDynamicForm.setValue("idUtente", (String) null);
					mDynamicForm.clearErrors(true);
					final String value = codiceRapidoItem.getValueAsString();
					if (value != null && !"".equals(value)) {
						Criterion[] criterias = new Criterion[1];
						criterias[0] = new Criterion("codice", OperatorId.IEQUALS, value);
						SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,
								new String[] { "codice", "cognomeNome", "username" }, true);
						lGwtRestDataSourceUtenti.fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								boolean trovato = false;
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codice = data.get(i).getAttribute("codice");
										if (value.equals(codice)) {
											mDynamicForm.setValue("idUtente", data.get(i).getAttribute("idUtente"));
											trovato = true;
											break;
										}
									}
								}
								if (!trovato) {
									codiceRapidoItem.validate();
									codiceRapidoItem.blurItem();
								}
							}
						});
					}
				} else if ("G".equals(tipoDestinatario)) {
					cercaGruppo();
				}
			}
		});
		codiceRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				if (codiceRapidoItem.getValue() != null && !"".equals(codiceRapidoItem.getValueAsString().trim())) {
					if (("UT".equalsIgnoreCase(tipoDestinatario) && utentiItem.getValue() == null)
							|| ("G".equalsIgnoreCase(tipoDestinatario) && gruppiInterniItem.getValue() == null)) {
						return false;
					}
				}
				return true;
			}
		});

		SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"/*, "username"*/}, true);

		utentiItem = new FilteredSelectItemWithDisplay("idUtente", lGwtRestDataSourceUtenti) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codiceRapido", record.getAttributeAsString("codice"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idUtente", "");
				mDynamicForm.setValue("codiceRapido", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idUtente", "");
					mDynamicForm.setValue("codiceRapido", "");
				}
			}
		};
		utentiItem.setAutoFetchData(false);
		utentiItem.setAlwaysFetchMissingValues(true);
		utentiItem.setFetchMissingValues(true);

		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(90);
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//
		ListGridField utentiUsernameField = new ListGridField("username", "Username");
		utentiUsernameField.setWidth(90);
		utentiItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utentiItem.setFilterLocally(true);
		utentiItem.setValueField("idUtente");
		utentiItem.setOptionDataSource(lGwtRestDataSourceUtenti);
		utentiItem.setShowTitle(false);
		utentiItem.setWidth(((PermessiItem)getItem()).getFilteredSelectItemWidth());
		utentiItem.setRequired(true);
		utentiItem.setClearable(true);
		utentiItem.setShowIcons(true);		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
		utentiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "UT".equalsIgnoreCase(tipoDestinatarioItem.getValueAsString());
			}
		});

		// gruppi
		SelectGWTRestDataSource lGwtRestDataSourceGruppi = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
				new String[] { "nomeGruppo" }, true);
		lGwtRestDataSourceGruppi.extraparam.put("usaFlagSoggettiInterni", "S");

		gruppiInterniItem = new FilteredSelectItemWithDisplay("idGruppo", lGwtRestDataSourceGruppi) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codiceRapido", record.getAttributeAsString("codiceRapidoGruppo"));
				mDynamicForm.setValue("idGruppoInterno", record.getAttributeAsString("idSoggettiInterni"));
				mDynamicForm.setValue("idGruppoEsterno", record.getAttributeAsString("idSoggettiNonInterni"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idGruppo", "");
				mDynamicForm.setValue("codiceRapido", "");
				mDynamicForm.setValue("idGruppoInterno", "");
				mDynamicForm.setValue("idGruppoEsterno", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idGruppo", "");
					mDynamicForm.setValue("codiceRapido", "");
					mDynamicForm.setValue("idGruppoInterno", "");
					mDynamicForm.setValue("idGruppoEsterno", "");
				}
            }
		};
		gruppiInterniItem.setAutoFetchData(false);
		gruppiInterniItem.setAlwaysFetchMissingValues(true);
		gruppiInterniItem.setFetchMissingValues(true);

		ListGridField idSoggettiInterniField = new ListGridField("idSoggettiInterni");
		idSoggettiInterniField.setHidden(true);
		ListGridField idSoggettiNonInterniField = new ListGridField("idSoggettiNonInterni");
		idSoggettiNonInterniField.setHidden(true);
		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoGruppoField.setWidth(70);
		ListGridField nomeGruppoField = new ListGridField("nomeGruppo", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeGruppoField.setWidth("*");
		gruppiInterniItem.setPickListFields(idSoggettiInterniField, idSoggettiNonInterniField, codiceRapidoGruppoField, nomeGruppoField);
		gruppiInterniItem.setFilterLocally(true);
		gruppiInterniItem.setValueField("idGruppo");
		gruppiInterniItem.setOptionDataSource(lGwtRestDataSourceGruppi);
		gruppiInterniItem.setShowTitle(false);
		gruppiInterniItem.setWidth(((PermessiItem)getItem()).getFilteredSelectItemWidth());
		gruppiInterniItem.setRequired(true);
		gruppiInterniItem.setClearable(true);
		gruppiInterniItem.setShowIcons(true);
		gruppiInterniItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "G".equalsIgnoreCase(tipoDestinatarioItem.getValueAsString());
			}
		});

		idGruppoInternoHiddenItem = new HiddenItem("idGruppoInterno");
		idGruppoEsternoHiddenItem = new HiddenItem("idGruppoEsterno");

		opzioniAccessoItem = new SelectItem("opzioniAccesso");
		LinkedHashMap<String, String> opzioniAccessoValueMap = new LinkedHashMap<String, String>();
		if (((PermessiItem) getItem()).getIsFolder() != null && ((PermessiItem) getItem()).getIsFolder()) {
			opzioniAccessoValueMap.put("V", "sola visualizzazione");
			opzioniAccessoValueMap.put("D", "inserimento documenti");
			opzioniAccessoValueMap.put("C", "controllo completo");
		} else {
			opzioniAccessoValueMap.put("V", "sola visualizzazione");
			opzioniAccessoValueMap.put("C", "controllo completo");
		}
		opzioniAccessoItem.setDefaultValue("V");
		opzioniAccessoItem.setShowTitle(false);
		opzioniAccessoItem.setValueMap(opzioniAccessoValueMap);
		opzioniAccessoItem.setWidth(150);

		flgEreditataHiddenItem = new HiddenItem("flgEreditata");
		flgAssegnatarioHiddenItem = new HiddenItem("flgAssegnatario");
		flgInvioPerConoscenzaHiddenItem = new HiddenItem("flgInvioPerConoscenza");

		ImgItem iconaFlgAssegnatarioItem = new ImgItem("iconaFlgAssegnatario", "archivio/assegna.png", "Assegnatario");
		iconaFlgAssegnatarioItem.setColSpan(1);
		iconaFlgAssegnatarioItem.setIconWidth(16);
		iconaFlgAssegnatarioItem.setIconHeight(16);
		iconaFlgAssegnatarioItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaFlgAssegnatarioItem.setAlign(Alignment.LEFT);
		iconaFlgAssegnatarioItem.setWidth(16);
		iconaFlgAssegnatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (flgAssegnatarioHiddenItem.getValue() != null && flgAssegnatarioHiddenItem.getValue().equals(true));
			}
		});

		ImgItem iconaFlgInvioPerConoscenzaItem = new ImgItem("iconaFlgInvioPerConoscenza", "archivio/condividi.png", "Con invio per conoscenza");
		iconaFlgInvioPerConoscenzaItem.setColSpan(1);
		iconaFlgInvioPerConoscenzaItem.setIconWidth(16);
		iconaFlgInvioPerConoscenzaItem.setIconHeight(16);
		iconaFlgInvioPerConoscenzaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaFlgInvioPerConoscenzaItem.setAlign(Alignment.LEFT);
		iconaFlgInvioPerConoscenzaItem.setWidth(16);
		iconaFlgInvioPerConoscenzaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (flgInvioPerConoscenzaHiddenItem.getValue() != null && flgInvioPerConoscenzaHiddenItem.getValue().equals(true));
			}
		});

		mDynamicForm.setFields(flgEreditataHiddenItem, tipoDestinatarioItem, codiceUoItem, idOrganigrammaHiddenItem, tipoOrganigrammaHiddenItem,
				descrOrganigrammaHiddenItem, organigrammaItem, lookupOrganigrammaButton, codiceRapidoItem, utentiItem, idGruppoInternoHiddenItem,
				idGruppoEsternoHiddenItem, gruppiInterniItem, opzioniAccessoItem, flgEreditataHiddenItem, flgAssegnatarioHiddenItem,
				flgInvioPerConoscenzaHiddenItem, iconaFlgAssegnatarioItem, iconaFlgInvioPerConoscenzaItem);

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}

	protected void cercaGruppo() {
		mDynamicForm.setValue("idGruppo", (String) null);
		mDynamicForm.setValue("idGruppoInterno", (String) null);
		mDynamicForm.setValue("idGruppoEsterno", (String) null);
		mDynamicForm.clearErrors(true);
		final String value = codiceRapidoItem.getValueAsString().toUpperCase();
		if (value != null && !"".equals(value)) {
			SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
					new String[] { "codiceRapidoGruppo", "nomeGruppo" }, true);
			lGwtRestDataSource.extraparam.put("usaFlagSoggettiInterni", "S");
			lGwtRestDataSource.extraparam.put("codiceRapidoGruppo", value);
			lGwtRestDataSource.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapido = data.get(i).getAttribute("codiceRapidoGruppo");
							if (codiceRapido != null && value.equalsIgnoreCase(codiceRapido)) {
								mDynamicForm.setValue("codiceRapido", data.get(i).getAttribute("codiceRapidoGruppo"));
								mDynamicForm.setValue("idGruppo", data.get(i).getAttribute("idGruppo"));
								mDynamicForm.setValue("idGruppoInterno", data.get(i).getAttribute("idSoggettiInterni"));
								mDynamicForm.setValue("idGruppoEsterno", data.get(i).getAttribute("idSoggettiNonInterni"));
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codiceRapido",
								I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("destinatario", "in entrata"));
					}
				}
			});
		}
	}

	@Override
	public void editRecord(Record record) {
		mDynamicForm.clearErrors(true);
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma")) &&
			record.getAttribute("denominazione") != null && !"".equals(record.getAttributeAsString("denominazione"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			if ("UO".equals(record.getAttribute("tipoOrganigramma"))) {
				valueMap.put(record.getAttribute("organigramma"), "<b>" + record.getAttribute("denominazione") + "</b>");
			} else {
				valueMap.put(record.getAttribute("organigramma"), record.getAttribute("denominazione"));
			}
			organigrammaItem.setValueMap(valueMap);
		}
		if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente")) &&
			record.getAttribute("denominazione") != null && !"".equals(record.getAttributeAsString("denominazione"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idUtente"), record.getAttribute("denominazione"));
			utentiItem.setValueMap(valueMap);
		}
		if (record.getAttribute("idGruppo") != null && !"".equals(record.getAttributeAsString("idGruppo")) &&
			record.getAttribute("denominazione") != null && !"".equals(record.getAttributeAsString("denominazione"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idGruppo"), record.getAttribute("denominazione"));
			gruppiInterniItem.setValueMap(valueMap);
		}

		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))
				&& record.getAttribute("tipoOrganigramma") != null
				&& ("UO".equals(record.getAttributeAsString("tipoOrganigramma")) || "SV".equals(record.getAttributeAsString("tipoOrganigramma")))) {
			organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
			organigrammaDS.addParam("flgUoSv", record.getAttributeAsString("tipoOrganigramma"));
		} else {
			organigrammaDS.addParam("idUoSv", null);
			organigrammaDS.addParam("flgUoSv", null);
		}
		organigrammaItem.setOptionDataSource(organigrammaDS);

		GWTRestDataSource utentiDS = (GWTRestDataSource) utentiItem.getOptionDataSource();
		if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente"))) {
			utentiDS.addParam("idUtente", record.getAttributeAsString("idUtente"));
		} else {
			utentiDS.addParam("idUtente", null);
		}
		utentiItem.setOptionDataSource(utentiDS);

		super.editRecord(record);
	}

	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		if (tipo.indexOf("_") != -1) {
			tipo = tipo.substring(0, tipo.indexOf("_"));
		}
		if (idOrganigramma == null || "".equals(idOrganigramma)) {
			idOrganigramma = record.getAttribute("idFolder");
		}
		mDynamicForm.setValue("organigramma", tipo + idOrganigramma);
		mDynamicForm.setValue("idOrganigramma", idOrganigramma);
		mDynamicForm.setValue("codiceUo", record.getAttribute("codRapidoUo"));
		mDynamicForm.setValue("tipoOrganigramma", tipo);
		if (tipo.equals("UO") || tipo.equals("SV")) {
			mDynamicForm.setValue("tipoDestinatario", "UO+SV");
		}
		mDynamicForm.clearErrors(true);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				organigrammaItem.fetchData();
			}
		});		
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	public class PermessiLookupOrganigramma extends LookupOrganigrammaPopup {

		public PermessiLookupOrganigramma(Record record) {
			super(record, true, new Integer(1));
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
			return "ACL";
		}

	}

	public boolean isAssegnatario() {
		return mDynamicForm.getValueAsString("flgAssegnatario") != null && mDynamicForm.getValueAsString("flgAssegnatario").equals("true");
	}

	public boolean isInvioPerConoscenza() {
		return mDynamicForm.getValueAsString("flgInvioPerConoscenza") != null && mDynamicForm.getValueAsString("flgInvioPerConoscenza").equals("true");
	}

	public void setCanEditOpzioniAccesso(Boolean canEdit) {
		opzioniAccessoItem.setCanEdit(canEdit);
	}

}
