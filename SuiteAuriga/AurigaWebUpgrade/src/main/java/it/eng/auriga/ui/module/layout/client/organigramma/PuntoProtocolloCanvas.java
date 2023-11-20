/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class PuntoProtocolloCanvas extends ReplicableCanvas {

	private HiddenItem idUoHiddenItem;
	private ExtendedTextItem codRapidoItem;
	private HiddenItem descrizioneHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private ReplicableCanvasForm mDynamicForm;

	public PuntoProtocolloCanvas(PuntoProtocolloItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idUoHiddenItem = new HiddenItem("idUo");

		codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUo", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("organigramma", (String) null);
				mDynamicForm.setValue("gruppo", (String) null);
				mDynamicForm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("codice", OperatorId.IEQUALS, value);

					SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboPuntiProtocolloDataSource", "id", FieldType.TEXT,
							new String[] { "codice", "descrizione" }, true);
					lGwtRestDataSource.addParam("finalita", "SEL_PUNTI_PROT");
					lGwtRestDataSource.fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String codice = data.get(i).getAttribute("codice");
									String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
									if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
										mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizione"));
										mDynamicForm.setValue("organigramma", data.get(i).getAttribute("id"));
										mDynamicForm.setValue("idUo", data.get(i).getAttribute("idUo"));
										mDynamicForm.setValue("typeNodo", data.get(i).getAttribute("typeNodo"));
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
				}
			}
		});
		codRapidoItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) && organigrammaItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});

		descrizioneHiddenItem = new HiddenItem("descrizione");

		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboPuntiProtocolloDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		lGwtRestDataSource.addParam("finalita", "SEL_PUNTI_PROT");
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
			}

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("idUo", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("organigramma", "");
				mDynamicForm.setValue("codRapido", "");
				mDynamicForm.setValue("idUo", "");
				mDynamicForm.setValue("typeNodo", "");
				mDynamicForm.setValue("descrizione", "");
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("organigramma", "");
					mDynamicForm.setValue("codRapido", "");
					mDynamicForm.setValue("idUo", "");
					mDynamicForm.setValue("typeNodo", "");
					mDynamicForm.setValue("descrizione", "");
				}
			}
		};
		organigrammaItem.setAutoFetchData(true);
		organigrammaItem.setFetchMissingValues(true);

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
		codiceField.setWidth(100);		
		organigrammaPickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		organigrammaPickListFields.add(descrizioneField);
		ListGridField descrizioneEstesaField = new ListGridField("descrizioneEstesa", I18NUtil.getMessages().organigramma_list_descrizioneEstesaField_title());
		descrizioneEstesaField.setHidden(true);
		organigrammaPickListFields.add(descrizioneEstesaField);

		organigrammaItem.setPickListFields(organigrammaPickListFields.toArray(new ListGridField[organigrammaPickListFields.size()]));
		organigrammaItem.setFilterLocally(true);
		organigrammaItem.setValueField("id");
		organigrammaItem.setOptionDataSource(lGwtRestDataSource);
		organigrammaItem.setShowTitle(false);
		organigrammaItem.setWidth(500);
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
				return true;
			}
		});
		organigrammaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return (!notReplicable || obbligatorio);
			}
		}));

		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				PuntoProtocolloLookupOrganigramma lookupOrganigrammaPopup = new PuntoProtocolloLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});

		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setColSpan(1);
		spacer.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});

		mDynamicForm.setFields(idUoHiddenItem, codRapidoItem, descrizioneHiddenItem, lookupOrganigrammaButton, spacer, organigrammaItem);
//		mDynamicForm.setFields(idUoHiddenItem, codRapidoItem, lookupOrganigrammaButton, spacer, organigrammaItem);
				

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}

	@Override
	public void editRecord(Record record) {

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put(record.getAttribute("organigramma"), "<b>" + record.getAttribute("descrizione") + "</b>");

		organigrammaItem.setValueMap(valueMap);

		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
		organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idUo"));
		organigrammaDS.addParam("flgUoSv", "UO");
		organigrammaItem.setOptionDataSource(organigrammaDS);
		super.editRecord(record);
	}

	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		mDynamicForm.setValue("organigramma", tipo + idOrganigramma);
		mDynamicForm.setValue("idUo", idOrganigramma);
		mDynamicForm.setValue("typeNodo", tipo);
		mDynamicForm.setValue("descrizione", ""); // da settare
		mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	public class PuntoProtocolloLookupOrganigramma extends LookupOrganigrammaPopup {

		public PuntoProtocolloLookupOrganigramma(Record record) {
			super(record, true, 1);
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
			return "SEL_PUNTI_PROT";
		}

	}

}
