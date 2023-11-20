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
import com.smartgwt.client.types.TitleOrientation;
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
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class PermessiConfigFlussiCanvas extends ReplicableCanvas {
	
	private SelectItem tipoDestinatarioItem;
	private ExtendedTextItem codiceUoItem;
	private HiddenItem idOrganigrammaHiddenItem;
	private HiddenItem descrOrganigrammaHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private ExtendedTextItem codiceRapidoItem;
	private FilteredSelectItemWithDisplay utentiItem;
	private HiddenItem idGruppoInternoHiddenItem;
	private HiddenItem idGruppoEsternoHiddenItem;
	private FilteredSelectItemWithDisplay gruppiInterniItem;
	private SelectItem ruoliAmmItem;
	private HiddenItem flgUoRuoloAmmItem;
	private HiddenItem idUoRuoloAmmItem;		
	private HiddenItem descrizioneUoRuoloAmmItem;		
	private HiddenItem codiceUoRuoloAmmItem;			
	private HiddenItem organigrammaUoRuoloAmmItem;		
	private HiddenItem flgUoSubordinateRuoloAmmItem;
	private HiddenItem flgRuoloProcUoRuoloAmmItem;
	private HiddenItem idRuoloProcUoRuoloAmmItem;	
	private HiddenItem descrRuoloProcUoRuoloAmmItem;
	private HiddenItem flgRuoloProcSvRuoloAmmItem;
	private HiddenItem idRuoloProcSvRuoloAmmItem;
	private HiddenItem descrRuoloProcSvRuoloAmmItem;
	private HiddenItem uoSovraordinataDiLivelloItem;
	private HiddenItem uoSovraordinataDiTipoItem;
	private ImgButtonItem altriDatiRuoliAmmButton;
	private SelectItem ruoliAttSoggIntProcItem;
	private SelectItem opzioniAccessoItem;
	private CheckboxItem flgInibitaEscalationSovraordinatiItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public PermessiConfigFlussiCanvas(PermessiConfigFlussiItem item) {
		super(item);
	}

	public PermessiConfigFlussiCanvas(PermessiConfigFlussiItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		// tipo
		tipoDestinatarioItem = new SelectItem("tipoDestinatario");
		LinkedHashMap<String, String> tipoDestinatarioValueMap = new LinkedHashMap<String, String>();
		tipoDestinatarioValueMap.put("UT", "Operatore");
		tipoDestinatarioValueMap.put("SV", "Postazione operatore in organigramma");
		tipoDestinatarioValueMap.put("UO", "Unità organizzativa");
		tipoDestinatarioValueMap.put("G", "Gruppo/Lista di distribuzione");
		tipoDestinatarioValueMap.put("R", "Ruolo in organigramma");
		tipoDestinatarioValueMap.put("RP", "Attore/ruolo di processo");
		tipoDestinatarioItem.setDefaultToFirstOption(true);
		tipoDestinatarioItem.setShowTitle(false);
		tipoDestinatarioItem.setRequired(true);
		tipoDestinatarioItem.setValueMap(tipoDestinatarioValueMap);
		tipoDestinatarioItem.setWidth(250);
		tipoDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				if(tipoDestinatario != null && "UO".equals(tipoDestinatario)) {
					organigrammaDS.addParam("tipoAssegnatari", "UO");					
				} else if(tipoDestinatario != null && "SV".equals(tipoDestinatario)) {
					organigrammaDS.addParam("tipoAssegnatari", "SV");
				} else {
					organigrammaDS.addParam("tipoAssegnatari", "UO;SV");
				}
				organigrammaItem.setOptionDataSource(organigrammaDS);
				
				mDynamicForm.clearErrors(true);
				Record lRecord = mDynamicForm.getValuesAsRecord();
				lRecord.setAttribute("codiceUo", "");
				lRecord.setAttribute("idOrganigramma", "");
				lRecord.setAttribute("organigramma", "");
				lRecord.setAttribute("descrizione", "");
				
				lRecord.setAttribute("idRuoloAmm", "");
				lRecord.setAttribute("idRuoloAttSoggIntProc", "");
				
				lRecord.setAttribute("codiceRapido", "");
				lRecord.setAttribute("idGruppo", "");
				lRecord.setAttribute("idGruppoInterno", "");
				lRecord.setAttribute("idGruppoEsterno", "");
				mDynamicForm.setValues(lRecord.toMap());
				mDynamicForm.markForRedraw();
			}
		});

		codiceUoItem = new ExtendedTextItem("codiceUo", "Cod. rapido");
		codiceUoItem.setWidth(120);
		codiceUoItem.setColSpan(1);
		codiceUoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && ("UO".equals(tipoDestinatario) || "SV".equals(tipoDestinatario));
			}
		});
		codiceUoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idOrganigramma", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("organigramma", (String) null);
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
										mDynamicForm.setValue("tipoOrganigrmma", data.get(i).getAttribute("typeNodo"));
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
					if(tipoDestinatario != null && ("UO".equals(tipoDestinatario) || "SV".equals(tipoDestinatario))) {
						if (organigrammaItem.getValue() == null) {
							return false;
						}
					}
				}
				return true;
			}
		});

		idOrganigrammaHiddenItem = new HiddenItem("idOrganigramma");
					
		descrOrganigrammaHiddenItem = new HiddenItem("descrizione");

		SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		organigrammaDS.addParam("finalita", "ACL");
		
		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", organigrammaDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("organigramma", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codiceUo", record.getAttributeAsString("codice"));
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
		organigrammaItem.setOptionDataSource(organigrammaDS);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(((PermessiConfigFlussiItem)getItem()).getFilteredSelectItemWidth());
		organigrammaItem.setRequired(true);
		organigrammaItem.setClearable(true);
		organigrammaItem.setShowIcons(true);
		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && ("UO".equals(tipoDestinatario) || "SV".equals(tipoDestinatario));
			}
		});
		organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && ("UO".equals(tipoDestinatario) || "SV".equals(tipoDestinatario));
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
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && ("UO".equals(tipoDestinatario) || "SV".equals(tipoDestinatario));
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
				if (tipoDestinatario != null && "UT".equals(tipoDestinatario)) {
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
				} else if (tipoDestinatario != null && "G".equals(tipoDestinatario)) {
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

		SelectGWTRestDataSource utentiDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"/*, "username"*/}, true);

		utentiItem = new FilteredSelectItemWithDisplay("idUtente", utentiDS) {

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
		utentiItem.setOptionDataSource(utentiDS);
		utentiItem.setShowTitle(false);
		utentiItem.setWidth(((PermessiConfigFlussiItem)getItem()).getFilteredSelectItemWidth());
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
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "UT".equals(tipoDestinatario);
			}
		});

		// gruppi
		SelectGWTRestDataSource gruppiDS = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
				new String[] { "nomeGruppo" }, true);
		gruppiDS.addParam("usaFlagSoggettiInterni", "S");
		gruppiDS.addParam("escludiFlgSoloAssegnabili", "true");

		gruppiInterniItem = new FilteredSelectItemWithDisplay("idGruppo", gruppiDS) {

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
		gruppiInterniItem.setOptionDataSource(gruppiDS);
		gruppiInterniItem.setShowTitle(false);
		gruppiInterniItem.setWidth(((PermessiConfigFlussiItem)getItem()).getFilteredSelectItemWidth());
		gruppiInterniItem.setRequired(true);
		gruppiInterniItem.setClearable(true);
		gruppiInterniItem.setShowIcons(true);
		gruppiInterniItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "G".equals(tipoDestinatario);
			}
		});

		idGruppoInternoHiddenItem = new HiddenItem("idGruppoInterno");
		idGruppoEsternoHiddenItem = new HiddenItem("idGruppoEsterno");
		
		GWTRestDataSource ruoliAmmDS = new GWTRestDataSource("LoadComboRuoliAmmDataSource");
		
		ruoliAmmItem = new SelectItem("idRuoloAmm");		
		ruoliAmmItem.setValueField("key");
		ruoliAmmItem.setDisplayField("value");
		ruoliAmmItem.setOptionDataSource(ruoliAmmDS);
		ruoliAmmItem.setAutoFetchData(false);
		ruoliAmmItem.setAlwaysFetchMissingValues(true);
		ruoliAmmItem.setFetchMissingValues(true);
		ruoliAmmItem.setShowTitle(false);
		ruoliAmmItem.setRequired(true);
		ruoliAmmItem.setClearable(true);
		ruoliAmmItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "R".equals(tipoDestinatario);
			}
		});
		
		flgUoRuoloAmmItem = new HiddenItem("flgUoRuoloAmm");	
		idUoRuoloAmmItem = new HiddenItem("idUoRuoloAmm");		
		descrizioneUoRuoloAmmItem = new HiddenItem("descrizioneUoRuoloAmm");		
		codiceUoRuoloAmmItem = new HiddenItem("codiceUoRuoloAmm");		
		organigrammaUoRuoloAmmItem = new HiddenItem("organigrammaUoRuoloAmm");		
		flgUoSubordinateRuoloAmmItem = new HiddenItem("flgUoSubordinateRuoloAmm");	
		flgRuoloProcUoRuoloAmmItem = new HiddenItem("flgRuoloProcUoRuoloAmm");
		idRuoloProcUoRuoloAmmItem = new HiddenItem("idRuoloProcUoRuoloAmm");		
		descrRuoloProcUoRuoloAmmItem = new HiddenItem("descrRuoloProcUoRuoloAmm");
		flgRuoloProcSvRuoloAmmItem = new HiddenItem("flgRuoloProcSvRuoloAmm");
		idRuoloProcSvRuoloAmmItem = new HiddenItem("idRuoloProcSvRuoloAmm");		
		descrRuoloProcSvRuoloAmmItem = new HiddenItem("descrRuoloProcSvRuoloAmm");
		uoSovraordinataDiLivelloItem = new HiddenItem("uoSovraordinataDiLivello");
		uoSovraordinataDiTipoItem = new HiddenItem("uoSovraordinataDiTipo");
		
		altriDatiRuoliAmmButton = new ImgButtonItem("altriDatiRuoliAmm", "buttons/altriDati.png",
				"Altri dati");
		altriDatiRuoliAmmButton.setAlwaysEnabled(true);
		altriDatiRuoliAmmButton.setColSpan(1);
		altriDatiRuoliAmmButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				AltriDatiRuoliAmmPopup altriDatiRuoliAmmPopup = new AltriDatiRuoliAmmPopup("Altri dati ruolo in organigramma " + ruoliAmmItem.getDisplayValue(), getEditing(), ((PermessiConfigFlussiItem) getItem()).getCodTipoFlusso(), mDynamicForm.getValuesAsRecord()) {

					@Override
					public void onClickOkButton(Record record) {
						
						mDynamicForm.setValue("flgUoRuoloAmm", record.getAttribute("flgUoRuoloAmm"));				
						mDynamicForm.setValue("idUoRuoloAmm", record.getAttribute("idUoRuoloAmm"));				
						mDynamicForm.setValue("descrizioneUoRuoloAmm", record.getAttribute("descrizioneUoRuoloAmm"));	 
						mDynamicForm.setValue("codiceUoRuoloAmm", record.getAttribute("codiceUoRuoloAmm"));	
						mDynamicForm.setValue("organigrammaUoRuoloAmm", record.getAttribute("organigrammaUoRuoloAmm"));	
						mDynamicForm.setValue("flgUoSubordinateRuoloAmm", record.getAttribute("flgUoSubordinateRuoloAmm"));												
						mDynamicForm.setValue("flgRuoloProcUoRuoloAmm", record.getAttribute("flgRuoloProcUoRuoloAmm"));				
						mDynamicForm.setValue("idRuoloProcUoRuoloAmm", record.getAttribute("idRuoloProcUoRuoloAmm"));
						mDynamicForm.setValue("descrRuoloProcUoRuoloAmm", record.getAttribute("descrRuoloProcUoRuoloAmm"));						
						mDynamicForm.setValue("flgRuoloProcSvRuoloAmm", record.getAttribute("flgRuoloProcSvRuoloAmm"));				
						mDynamicForm.setValue("idRuoloProcSvRuoloAmm", record.getAttribute("idRuoloProcSvRuoloAmm"));
						mDynamicForm.setValue("descrRuoloProcSvRuoloAmm", record.getAttribute("descrRuoloProcSvRuoloAmm"));
						
						if(record.getAttribute("flgRuoloProcSvRuoloAmm") != null && "true".equalsIgnoreCase(record.getAttribute("flgRuoloProcSvRuoloAmm"))) {
							mDynamicForm.setValue("uoSovraordinataDiLivello", record.getAttribute("uoSovrLivRuoloProcSv"));
							mDynamicForm.setValue("uoSovraordinataDiTipo", record.getAttribute("uoSovrTipoRuoloProcSv"));
						}
						
						if(record.getAttribute("flgRuoloProcUoRuoloAmm") != null && "true".equalsIgnoreCase(record.getAttribute("flgRuoloProcUoRuoloAmm"))) {
							mDynamicForm.setValue("uoSovraordinataDiLivello", record.getAttribute("uoSovrLivRuoloProcUo"));
							mDynamicForm.setValue("uoSovraordinataDiTipo", record.getAttribute("uoSovrTipoRuoloProcUo"));
						}
						
					};
				};				
				altriDatiRuoliAmmPopup.show();
			}
		});
		altriDatiRuoliAmmButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "R".equals(tipoDestinatario);
			}
		});
		
		GWTRestDataSource ruoliAttSoggIntProcDS = new GWTRestDataSource("LoadComboRuoliAttSoggIntProcDataSource");
		ruoliAttSoggIntProcDS.addParam("codTipoFlusso", ((PermessiConfigFlussiItem) getItem()).getCodTipoFlusso());
		
		ruoliAttSoggIntProcItem = new SelectItem("idRuoloAttSoggIntProc");		
		ruoliAttSoggIntProcItem.setValueField("key");
		ruoliAttSoggIntProcItem.setDisplayField("value");
		ruoliAttSoggIntProcItem.setOptionDataSource(ruoliAttSoggIntProcDS);
		ruoliAttSoggIntProcItem.setAutoFetchData(false);
		ruoliAttSoggIntProcItem.setAlwaysFetchMissingValues(true);
		ruoliAttSoggIntProcItem.setFetchMissingValues(true);
		ruoliAttSoggIntProcItem.setShowTitle(false);
		ruoliAttSoggIntProcItem.setRequired(true);
		ruoliAttSoggIntProcItem.setClearable(true);
		ruoliAttSoggIntProcItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "RP".equals(tipoDestinatario);
			}
		});		
		ListGrid codTipoFlussoPickListProperties = new ListGrid();
		codTipoFlussoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {				
				GWTRestDataSource ruoliAttSoggIntProcDS = (GWTRestDataSource) ruoliAttSoggIntProcItem.getOptionDataSource();
				ruoliAttSoggIntProcDS.addParam("codTipoFlusso", ((PermessiConfigFlussiItem) getItem()).getCodTipoFlusso());				
				ruoliAttSoggIntProcItem.setOptionDataSource(ruoliAttSoggIntProcDS);
				ruoliAttSoggIntProcItem.invalidateDisplayValueCache();
			}
		});
		ruoliAttSoggIntProcItem.setPickListProperties(codTipoFlussoPickListProperties);
		
		opzioniAccessoItem = new SelectItem("opzioniAccesso", "Consenti");
		LinkedHashMap<String, String> opzioniAccessoValueMap = new LinkedHashMap<String, String>();
		opzioniAccessoValueMap.put("V", "solo visibiltà");
		opzioniAccessoValueMap.put("E", "esecuzione");			
		opzioniAccessoValueMap.put("E-GS", "esecuzione (solo in gestione estesa)");
		opzioniAccessoItem.setDefaultValue("E");		
		opzioniAccessoItem.setValueMap(opzioniAccessoValueMap);
		opzioniAccessoItem.setWidth(150);

		// Inibita escalation ai sovraordinati
		flgInibitaEscalationSovraordinatiItem = new CheckboxItem("flgInibitaEscalationSovraordinati", "inibita escalation ai sovraordinati");
		flgInibitaEscalationSovraordinatiItem.setTitleOrientation(TitleOrientation.LEFT);
		flgInibitaEscalationSovraordinatiItem.setTitleAlign(Alignment.RIGHT);
		flgInibitaEscalationSovraordinatiItem.setShowTitle(true);
		flgInibitaEscalationSovraordinatiItem.setLabelAsTitle(true);
		flgInibitaEscalationSovraordinatiItem.setShowLabel(false);
		flgInibitaEscalationSovraordinatiItem.setDefaultValue(false);
		
		mDynamicForm.setFields(
				tipoDestinatarioItem, 
				codiceUoItem, idOrganigrammaHiddenItem, descrOrganigrammaHiddenItem, organigrammaItem, lookupOrganigrammaButton, 
				codiceRapidoItem, utentiItem, idGruppoInternoHiddenItem, idGruppoEsternoHiddenItem, gruppiInterniItem, 
				ruoliAmmItem, flgUoRuoloAmmItem, idUoRuoloAmmItem, descrizioneUoRuoloAmmItem, codiceUoRuoloAmmItem, organigrammaUoRuoloAmmItem,		
				flgUoSubordinateRuoloAmmItem, flgRuoloProcUoRuoloAmmItem, idRuoloProcUoRuoloAmmItem, descrRuoloProcUoRuoloAmmItem, flgRuoloProcSvRuoloAmmItem,
				idRuoloProcSvRuoloAmmItem, descrRuoloProcSvRuoloAmmItem, uoSovraordinataDiLivelloItem, uoSovraordinataDiTipoItem, 
				altriDatiRuoliAmmButton, ruoliAttSoggIntProcItem, opzioniAccessoItem , flgInibitaEscalationSovraordinatiItem
		);

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
			lGwtRestDataSource.addParam("usaFlagSoggettiInterni", "S");
			lGwtRestDataSource.addParam("codiceRapidoGruppo", value);
			lGwtRestDataSource.addParam("escludiFlgSoloAssegnabili", "true");
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
						mDynamicForm.setFieldErrors("codiceRapido", I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("destinatario", "in entrata"));
					}
				}
			});
		}
	}

	@Override
	public void editRecord(Record record) {
		
		mDynamicForm.clearErrors(true);
		
		String tipoDestinatario = record.getAttribute("tipoDestinatario");
			
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			if (tipoDestinatario != null && "UO".equals(tipoDestinatario)) {
				valueMap.put(record.getAttribute("organigramma"), "<b>" + record.getAttribute("denominazione") + "</b>");
			} else {
				valueMap.put(record.getAttribute("organigramma"), record.getAttribute("denominazione"));
			}
			organigrammaItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idUtente"), record.getAttribute("denominazione"));
			utentiItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("idGruppo") != null && !"".equals(record.getAttributeAsString("idGruppo"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idGruppo"), record.getAttribute("denominazione"));
			gruppiInterniItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("idRuoloAmm") != null && !"".equals(record.getAttributeAsString("idRuoloAmm"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idRuoloAmm"), record.getAttribute("denominazione"));
			ruoliAmmItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("idRuoloAttSoggIntProc") != null && !"".equals(record.getAttributeAsString("idRuoloAttSoggIntProc"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idRuoloAttSoggIntProc"), record.getAttribute("denominazione"));
			ruoliAttSoggIntProcItem.setValueMap(valueMap);
		}
		
		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
		
		if(tipoDestinatario != null && "UO".equals(tipoDestinatario)) {
			organigrammaDS.addParam("tipoAssegnatari", "UO");					
		} else if(tipoDestinatario != null && "SV".equals(tipoDestinatario)) {
			organigrammaDS.addParam("tipoAssegnatari", "SV");
		} else {
			organigrammaDS.addParam("tipoAssegnatari", "UO;SV");
		}
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))
				&& tipoDestinatario != null && ("UO".equals(tipoDestinatario) || "SV".equals(tipoDestinatario))) {
			organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
			organigrammaDS.addParam("flgUoSv", tipoDestinatario);
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
		if (tipo.equals("UO")) {
			mDynamicForm.setValue("tipoDestinatario", "UO");
		} else if (tipo.equals("SV")) {
			mDynamicForm.setValue("tipoDestinatario", "SV");
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

	public void setCanEditOpzioniAccesso(Boolean canEdit) {
		opzioniAccessoItem.setCanEdit(canEdit);
	}

}
