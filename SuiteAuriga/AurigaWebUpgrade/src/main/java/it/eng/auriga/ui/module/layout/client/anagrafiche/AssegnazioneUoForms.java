/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public abstract class AssegnazioneUoForms {

	private ValuesManager vm;

	private DynamicForm organigrammaForm;
	private DynamicForm rubricaDiForm;
	private DynamicForm flgForm;

	private HiddenItem idUoHiddenItem;
	private HiddenItem typeNodoHiddenItem;
	private HiddenItem descrizioneHiddenItem;

	private ExtendedTextItem codRapidoItem;
	private FilteredSelectItemWithDisplay organigrammaItem;

	private SelectItem uoCollegateItem;

	protected StaticTextItem labelUoCorrenteItem;
	protected CheckboxItem flgVisibileDaSottoUoItem;
	protected CheckboxItem flgModificabileDaSottoUoItem;

	private String idUdProtocollo;

	private String mode;
	public String titleCodRapidoValue;
	

	public AssegnazioneUoForms(String idUdProtocollo, ValuesManager vm) {

		this.vm = vm;
		this.idUdProtocollo = idUdProtocollo;

		organigrammaForm = new DynamicForm() {

			@Override
			public void editRecord(Record record) {
				organigrammaForm.clearErrors(true);
				if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					if (record.getAttribute("typeNodo") != null && "UO".equals(record.getAttribute("typeNodo"))) {
						valueMap.put(record.getAttribute("organigramma"), "<b>" + record.getAttribute("descrizione") + "</b>");
					} else {
						valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
					}
					organigrammaItem.setValueMap(valueMap);
				}
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("organigramma"))
						&& record.getAttribute("typeNodo") != null
						&& ("UO".equals(record.getAttributeAsString("typeNodo")) || "SV".equals(record.getAttributeAsString("typeNodo")))) {
					organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
					organigrammaDS.addParam("flgUoSv", record.getAttributeAsString("typeNodo"));
				} else {
					organigrammaDS.addParam("idUoSv", null);
					organigrammaDS.addParam("flgUoSv", null);
				}
				organigrammaItem.setOptionDataSource(organigrammaDS);
				super.editRecord(record);
			}
		};
		organigrammaForm.setWidth100();
		organigrammaForm.setHeight100();
		organigrammaForm.setMargin(getFormMargin());
		organigrammaForm.setPadding(getFormPadding());
		organigrammaForm.setWrapItemTitles(false);
		organigrammaForm.setNumCols(16);
		organigrammaForm.setColWidths("1","1","1","1","1","1","1","1","1","1","1","1","1","1","*","*");
		organigrammaForm.setValuesManager(vm);

		idUoHiddenItem = new HiddenItem("idUoAssociata");
		descrizioneHiddenItem = new HiddenItem("descrizioneUo");

		if (isAbilInserireModificareSoggInQualsiasiUo()) {
			disegnaAssegnazioneTutteUo();
		} else {
			disegnaAssegnazioneUoCollegateUtente();
		}

		disegnaLabelUoCorrenteAndFlag();

		newMode();
	}

	public void disegnaAssegnazioneTutteUo() {

		typeNodoHiddenItem = new HiddenItem("typeNodo");
		codRapidoItem = new ExtendedTextItem("codRapido", getTitleCodRapidoItem());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codRapidoItem.setDefaultValue(AurigaLayout.getCodRapidoOrganigramma());			
		}	
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				vm.setValue("idUoAssociata", (String) null);
				vm.setValue("descrizioneUo", (String) null);
				vm.setValue("organigramma", (String) null);
				vm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();

				if (value != null && !"".equals(value)) {
					organigrammaItem.fetchData(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
									if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
										vm.setValue("descrizioneUo", data.get(i).getAttribute("descrizioneOrig"));
										vm.setValue("organigramma", data.get(i).getAttribute("id"));
										vm.setValue("idUoAssociata", data.get(i).getAttribute("idUo"));
										vm.clearErrors(true);
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								codRapidoItem.validate();
								codRapidoItem.blurItem();
							}
							redrawForms();
						}
					});
				} else {
					organigrammaItem.fetchData();
				}

				redrawForms();
			}
		});
		codRapidoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isPartizionamentoRubricaAbilitato() && showOrganigrammaItem() && mostraFormDiAssegnazione();
			}
		});

		// descrizioneHiddenItem = new HiddenItem("descrizioneUo");

		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboUoCollegateUtenteAdminDatasource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		lGwtRestDataSource.addParam("finalita", getFinalitaForComboOrganigramma());
		lGwtRestDataSource.addParam("idUd", idUdProtocollo);
		lGwtRestDataSource.addParam("tipoAssegnatari", getTipoAssegnatariForComboOrganigramma());
		
		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", lGwtRestDataSource) {
			
			@Override
			public void manageOnEnterPress(BodyKeyPressEvent event, Record record) {
				String flgSelXFinalita = record.getAttributeAsString("flgSelXFinalita");
				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
					onOptionClick(record);
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						
						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
				redrawForms();
			}

			@Override
			public void manageOnCellClick(CellClickEvent event) {
				String flgSelXFinalita = event.getRecord().getAttributeAsString("flgSelXFinalita");
				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
					onOptionClick(event.getRecord());
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
				redrawForms();
			}

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				vm.setValue("organigramma", record.getAttributeAsString("id"));
				vm.setValue("codRapido", record.getAttributeAsString("codice"));
				vm.setValue("idUoAssociata", record.getAttributeAsString("idUo"));
				vm.setValue("descrizioneUo", record.getAttributeAsString("descrizioneOrig"));
				vm.clearErrors(true);					
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
				vm.setValue("organigramma", "");
				if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					vm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					vm.setValue("codRapido", "");
				}
				vm.setValue("idUoAssociata", "");
				vm.setValue("typeNodo", "");
				vm.setValue("descrizioneUo", "");
				vm.clearErrors(true);					
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
					}
				});
				redrawForms();
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					vm.setValue("organigramma", "");
					if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
						vm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						vm.setValue("codRapido", "");
					}
					vm.setValue("idUoAssociata", "");
					vm.setValue("typeNodo", "");
					vm.setValue("descrizioneUo", "");
					vm.clearErrors(true);					
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaItem.fetchData();
						}
					});
				}
				redrawForms();
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
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(120);
		codiceField.setShowHover(true);
		codiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("descrizioneEstesa");
			}
		});
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			codiceField.setCanFilter(false);
		}
		organigrammaPickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		organigrammaPickListFields.add(descrizioneField);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			ListGridField flgPuntoProtocolloField = new ListGridField("flgPuntoProtocollo", "Punto di Protocollo");
			flgPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			flgPuntoProtocolloField.setAlign(Alignment.CENTER);
			flgPuntoProtocolloField.setWidth(30);
			flgPuntoProtocolloField.setShowHover(true);
			flgPuntoProtocolloField.setCanFilter(false);
			flgPuntoProtocolloField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			flgPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record != null && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "Punto di Protocollo";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(flgPuntoProtocolloField);
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
		organigrammaItem.setOptionDataSource(lGwtRestDataSource);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(getOrganigrammaItemWidth());
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
				return isPartizionamentoRubricaAbilitato() && showOrganigrammaItem() && mostraFormDiAssegnazione();
			}
		});

		ListGrid pickListProperties = organigrammaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = organigrammaForm.getValueAsString("codRapido");
//				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && (codRapido == null || "".equals(codRapido))) {
//					if(codRapidoItem.getCanEdit() != null && codRapidoItem.getCanEdit()) {
//						organigrammaForm.setFieldErrors("codRapido", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				organigrammaDS.addParam("codice", codRapido);
				organigrammaDS.addParam("finalita", getFinalitaForComboOrganigramma());
				organigrammaDS.addParam("idUd", idUdProtocollo);
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
				AssegnazioneLookupOrganigramma lookupOrganigrammaPopup = new AssegnazioneLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showOrganigrammaItem() && mostraFormDiAssegnazione();
			}
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			lookupOrganigrammaButton.setCanFocus(true);
		}

		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setColSpan(1);
		spacer.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPartizionamentoRubricaAbilitato() && showOrganigrammaItem() && mostraFormDiAssegnazione();
			}
		});

		organigrammaForm.setFields(idUoHiddenItem, typeNodoHiddenItem, codRapidoItem, descrizioneHiddenItem, lookupOrganigrammaButton, spacer,
				organigrammaItem);

	}

	public int getOrganigrammaItemWidth() {
		return 400;
	}

	private void disegnaAssegnazioneUoCollegateUtente() {
		LinkedHashMap<String, String> mappaUoCollegate = AurigaLayout.getSelezioneUoCollegateUtenteValueMap();

		uoCollegateItem = new SelectItem("uoCollegate");
		uoCollegateItem.setValueField("idUo");
		uoCollegateItem.setDisplayField("descrizione");
		uoCollegateItem.setShowTitle(isUoCollegateShowTitle());
		uoCollegateItem.setTitle(I18NUtil.getMessages().protocollazione_detail_uoItem_title());
		uoCollegateItem.setWidth(getUoCollegateItemWidth());
		uoCollegateItem.setClearable(true);
		uoCollegateItem.setShowIcons(true);
		uoCollegateItem.setValueMap(mappaUoCollegate);
		uoCollegateItem.setColSpan(3);
		uoCollegateItem.setAttribute("obbligatorio", isUoCollegateObbligatorio());
		uoCollegateItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		uoCollegateItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				organigrammaForm.clearErrors(true);
				if (event.getValue() != null) {
					String idUo = (String) event.getValue();
					// Tolgo i primo 2 caratteri (UO, SV, ...), in modo da avere solo il codice idUo
					onOptionClick(idUo.substring(2));
				}
			}
		});
		uoCollegateItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPartizionamentoRubricaAbilitato() && showUoCollegateItem();
			}
		}));
		uoCollegateItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPartizionamentoRubricaAbilitato() && showUoCollegateItem() && mostraFormDiAssegnazione();
			}
		});

		organigrammaForm.setFields(idUoHiddenItem, descrizioneHiddenItem, uoCollegateItem);

	}

	public int getUoCollegateItemWidth() {
		return 400;
	}
	
	protected void onOptionClick(String idUo) {
		vm.setValue("idUoAssociata", idUo);
		idUoHiddenItem.setValue(idUo);
		redrawForms();
	}

	private void disegnaLabelUoCorrenteAndFlag() {
		labelUoCorrenteItem = new StaticTextItem();
		labelUoCorrenteItem.setShowTitle(false);
		labelUoCorrenteItem.setStartRow(true);
		labelUoCorrenteItem.setWrap(false);
		labelUoCorrenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isPartizionamentoRubricaAbilitato() && showLabelUoCorrenteItem() && mostraFormDiAssegnazione();
			}
		});
		rubricaDiForm = new DynamicForm();
		rubricaDiForm.setWidth100();
		rubricaDiForm.setHeight100();
		rubricaDiForm.setNumCols(10);
		rubricaDiForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		rubricaDiForm.setValuesManager(vm);
		rubricaDiForm.setWrapItemTitles(false);
		rubricaDiForm.setMargin(getFormMargin());
		rubricaDiForm.setPadding(getFormPadding());
		rubricaDiForm.markForRedraw();
		rubricaDiForm.setFields(labelUoCorrenteItem);

		flgVisibileDaSottoUoItem = new CheckboxItem("flgVisibileDaSottoUo", I18NUtil.getMessages().soggetti_detail_flgVisibileSottoUo_title());
		flgVisibileDaSottoUoItem.setStartRow(true);
		flgVisibileDaSottoUoItem.setWidth("*");
		flgVisibileDaSottoUoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				String idUoAssociata = vm.getValueAsString("idUoAssociata");
				return isPartizionamentoRubricaAbilitato() && (idUoAssociata != null) && (!"".equalsIgnoreCase(idUoAssociata)) && mostraFormDiAssegnazione();
			}
		});

		flgVisibileDaSottoUoItem.setTitle(getTitleFlgVisibileDaSottoUoItem());

		flgModificabileDaSottoUoItem = new CheckboxItem("flgModificabileDaSottoUo", I18NUtil.getMessages().soggetti_detail_flgModificabileSottoUo_title());
		flgModificabileDaSottoUoItem.setStartRow(false);
		flgModificabileDaSottoUoItem.setWidth("*");
		flgModificabileDaSottoUoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				String idUoAssociata = vm.getValueAsString("idUoAssociata");
				return isFlgModificabileDaSottoUoVisible() && isPartizionamentoRubricaAbilitato() && (idUoAssociata != null) && (!"".equalsIgnoreCase(idUoAssociata)) && mostraFormDiAssegnazione();
			}
		});

		flgForm = new DynamicForm();
		flgForm.setWidth100();
		flgForm.setHeight100();
		flgForm.setNumCols(10);
		flgForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		flgForm.setValuesManager(vm);
		flgForm.setWrapItemTitles(false);
		flgForm.setMargin(getFormMargin());
		flgForm.setPadding(getFormPadding());
		flgForm.markForRedraw();
		flgForm.setFields(flgVisibileDaSottoUoItem, flgModificabileDaSottoUoItem);

	}

	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		if ((idOrganigramma != null) && (!"".equalsIgnoreCase(idOrganigramma))) {
			String tipo = record.getAttribute("tipo");
			if (isAbilInserireModificareSoggInQualsiasiUo()) {
				int pos = tipo.indexOf("_");
				if (pos != -1) {
					tipo = tipo.substring(0, pos);
				}				
				vm.setValue("organigramma", tipo + idOrganigramma);
				vm.setValue("idUoAssociata", idOrganigramma);
				vm.setValue("typeNodo", tipo);
				// mDynamicForm.setValue("descrizione", ""); // da settare
				vm.setValue("codRapido", record.getAttribute("codRapidoUo"));
				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					if(record.getAttribute("codRapidoUo") == null || "".equalsIgnoreCase(record.getAttribute("codRapidoUo"))) {			
						vm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());						
					}
				}	
				if (record.getAttributeAsBoolean("flgVisibileDaSottoUo") != null && record.getAttributeAsBoolean("flgVisibileDaSottoUo")) {
					vm.setValue("flgVisibileDaSottoUo", true);
				}
				if (record.getAttributeAsBoolean("flgModificabileDaSottoUo") != null && record.getAttributeAsBoolean("flgModificabileDaSottoUo")) {
					vm.setValue("flgModificabileDaSottoUo", true);
				}
				vm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaItem.fetchData();
					}
				});
			} else {
				vm.setValue("uoCollegate", tipo + idOrganigramma);
			}
		} else {
			if (isAbilInserireModificareSoggInQualsiasiUo()) {				
				vm.setValue("codRapido", record.getAttribute("codRapidoUo"));
				if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
					if(record.getAttribute("codRapidoUo") == null || "".equalsIgnoreCase(record.getAttribute("codRapidoUo"))) {			
						vm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());						
					}
				}	
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
					@Override
					public void execute() {
						organigrammaItem.fetchData();						
					}
				});
			}
		}
		redrawForms();
	}

	public Record getFormValuesAsRecord() {
		return organigrammaForm.getValuesAsRecord();
	}

	public class AssegnazioneLookupOrganigramma extends LookupOrganigrammaPopup {

		public AssegnazioneLookupOrganigramma(Record record) {
			super(record, true, 0);
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
			return getFinalitaForLookupOrganigramma();
		}

		@Override
		public String getIdUd() {
			return idUdProtocollo;
		}

	}

	public void viewMode() {
		mode = "view";
		setCanEdit(false);
		redrawForms();		
	}

	public void editMode() {
		mode = "edit";
		setCanEdit(true);		
	}

	public void newMode() {
		mode = "new";
		setCanEdit(true);
		redrawForms();
	}

	private void checkForzaSelectUO() {
		// Sono obbligato ad associare a quella UO
		// Se ne ho una sola blocco la selezione su quella
		// In questo caso non mostro neppure la select, che tanto non potrebbe essere modificata
		if (AurigaLayout.getSelezioneUoCollegateUtenteValueMap().size() == 1) {
			// Ricavo l'idUo della UO collegata
			Set<String> listaIdUo = AurigaLayout.getSelezioneUoCollegateUtenteValueMap().keySet();
			Iterator<String> iterator = listaIdUo.iterator();
			// Sono sicuro che ce ne sia una
			String idUo = iterator.next();
			String descrizioneUO = AurigaLayout.getSelezioneUoCollegateUtenteValueMap().get(idUo);
			// Forzo la select a posizionarsi su quella idUo
			vm.setValue("uoCollegate", "UO" + idUo);
			// Scrivo la descrizione nella label, e la visualizzo
			setUoCorrenteItemValue(getInRubricaDiMessage() + " " + descrizioneUO);
			vm.setValue("idUoAssociata", idUo.substring(2));
		}
	}

	public void setCanEdit(boolean canEdit) {
		for (FormItem item : organigrammaForm.getFields()) {
			item.setCanEdit(canEdit);
			item.redraw();
		}
	}

	private boolean showUoCollegateItem() {
		return (mode != null && ("new".equals(mode) || "edit".equals(mode))) && !isAbilInserireModificareSoggInQualsiasiUo()
				&& AurigaLayout.getSelezioneUoCollegateUtenteValueMap().size() > 1;
	}

	private boolean showOrganigrammaItem() {
		return (mode != null && ("new".equals(mode) || "edit".equals(mode))) && isAbilInserireModificareSoggInQualsiasiUo();
	}

	private boolean showLabelUoCorrenteItem() {
		return (mode != null && "view".equals(mode)) || (!isAbilInserireModificareSoggInQualsiasiUo() && AurigaLayout.getSelezioneUoCollegateUtenteValueMap().size() == 1);
	}

	private void setUoCorrenteItemValue(String value) {
		if (labelUoCorrenteItem != null) {
			labelUoCorrenteItem.setValue("<span style=\"color:#1D66B2\"><b>" + value + "</span></b>");
		}
	}
	
	public boolean isVisibleForms() {
		return isOrganigrammaFormVisible() || isRubricaDiFormVisible() || isFlgFormVisible();
	}

	public DynamicForm[] getForms() {
		DynamicForm[] listaForm = new DynamicForm[] { organigrammaForm, rubricaDiForm, flgForm };
		return listaForm;
	}
	
	public boolean isOrganigrammaFormVisible() {		
		if (isAbilInserireModificareSoggInQualsiasiUo()) {
			return isPartizionamentoRubricaAbilitato() && showOrganigrammaItem();
		} else {
			return isPartizionamentoRubricaAbilitato() && showUoCollegateItem();
		}
	}
	
	public boolean isRubricaDiFormVisible() {
		return isPartizionamentoRubricaAbilitato() && showLabelUoCorrenteItem();
	}
	
	public boolean isFlgFormVisible() {
		String idUoAssociata = vm.getValueAsString("idUoAssociata");
		return isPartizionamentoRubricaAbilitato() && (idUoAssociata != null) && (!"".equalsIgnoreCase(idUoAssociata));
	}

	
	public void refreshTitleCodRapidoItem(String title){
		codRapidoItem.setTitle(title);
		codRapidoItem.redraw();
	
	}
	
	public void redrawForms() {
		if(mode != null && ("new".equals(mode) || "edit".equals(mode))) {
			if (isPartizionamentoRubricaAbilitato()) {
				if (!isAbilInserireModificareSoggInQualsiasiUo()) {
					checkForzaSelectUO();
				} 
			}
			if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				if(codRapidoItem != null && (codRapidoItem.getValueAsString() == null || "".equalsIgnoreCase(codRapidoItem.getValueAsString()))) {			
					codRapidoItem.setValue(AurigaLayout.getCodRapidoOrganigramma());
				}
			}
		} else {
			if (isPartizionamentoRubricaAbilitato()) {
				// Prendo l'idUo di assegnazione dall'item corretto,
				// a seconda che sia o no abilitato all'inserimento del soggetto in ogni UO
				String idUoSoggetto = organigrammaForm.getValueAsString("idUoAssociata");
				String descrizione = organigrammaForm.getValueAsString("descrizioneUo");
				if (descrizione == null || "".equalsIgnoreCase(descrizione)) {
					descrizione = AurigaLayout.getSelezioneUoCollegateUtenteValueMap().get("UO"+idUoSoggetto);
				}
				if (descrizione == null || "".equalsIgnoreCase(descrizione)) {
					Record vmRecord = new Record(vm.getValues());
					descrizione = vmRecord.getAttribute("numeroLivelli") + " - " +  vmRecord.getAttribute("denominazioneUo");
				}
				// A questo punto ho l'idUo di appartenenza (potrebbe essere vuoto se il soggetto è in rubrica condivisa)
				if ((idUoSoggetto != null) && (!"".equalsIgnoreCase(idUoSoggetto))) {
					// Non sono in rubrica condivisa
					setUoCorrenteItemValue(getInRubricaDiMessage() + " " + descrizione);
				} else {
					// Sono in rubrica condivisa
					setUoCorrenteItemValue(getInRubricaCondivisaMessage());
				}			
			}
		}
		if (organigrammaForm != null) {
			organigrammaForm.setVisible(isOrganigrammaFormVisible());
			organigrammaForm.markForRedraw();
		}
		if (rubricaDiForm != null) {
			rubricaDiForm.setVisible(isRubricaDiFormVisible());
			rubricaDiForm.markForRedraw();
		}
		if (flgForm != null) {
			flgForm.setVisible(isFlgFormVisible());
			flgForm.markForRedraw();
		}
	}

	public String getFinalitaForLookupOrganigramma() {
		return null;
	}

	public String getFinalitaForComboOrganigramma() {
		return null;
	}
	
	public String getTipoAssegnatariForComboOrganigramma() {
		return null;
	}

	public boolean isUoCollegateShowTitle() {
		return false;
	}

	public boolean isUoCollegateObbligatorio() {
		return true;
	}

	public boolean isFlgModificabileDaSottoUoVisible() {
		return true;
	}

	public String getTitleFlgVisibileDaSottoUoItem() {
		return I18NUtil.getMessages().soggetti_detail_flgVisibileSottoUo_title();
	}
	
	public boolean mostraFormDiAssegnazione() {
		return true;
	}

	public abstract boolean isPartizionamentoRubricaAbilitato();

	public abstract boolean isAbilInserireModificareSoggInQualsiasiUo();
	
	public String getInRubricaDiMessage() {
		return I18NUtil.getMessages().soggetti_detail_inRubricaDi_message();
	}
	
	public String getInRubricaCondivisaMessage() {
		return I18NUtil.getMessages().soggetti_detail_inRubricaCondivisa_message();
	}
	
	public String getTitleCodRapidoItem() {
		return I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title();
	}
	
   
   public String getTitleCodRapidoValue(){
	   return titleCodRapidoValue;
   }
   
   public void setTitleCodRapidoValue(String titleCodRapidoValue){
	   this.titleCodRapidoValue = titleCodRapidoValue;
   }
   
   public int getFormMargin() {
	   return 5;
   }
   
   public int getFormPadding() {
	   return 0;
   }
   
}
