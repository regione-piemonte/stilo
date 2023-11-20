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
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
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

public class VisibEmailTransCaselleAssociateUOCanvas extends ReplicableCanvas{
		
	private ReplicableCanvasForm mDynamicForm;
	private ExtendedTextItem codRapidoItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private CheckboxItem flgIncludiSottoUOItem;
	private HiddenItem idUoHiddenItem;
	private HiddenItem typeNodoHiddenItem;
	private HiddenItem descrizioneHiddenItem;
	private ImgButtonItem lookupOrganigrammaButton;
	private SpacerItem spacer;
	
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		buildItems();
		
		mDynamicForm.setFields(
			idUoHiddenItem, 
	        typeNodoHiddenItem, 
	        codRapidoItem, 
	        descrizioneHiddenItem,
			lookupOrganigrammaButton,
	        spacer, 
			organigrammaItem,
			flgIncludiSottoUOItem 
		);

		mDynamicForm.setNumCols(10);
		addChild(mDynamicForm);
	}
	
	private void buildItems() {
		
		idUoHiddenItem        = new HiddenItem("idUo");
		typeNodoHiddenItem    = new HiddenItem("typeNodo");
		descrizioneHiddenItem = new HiddenItem("descrizione");
		
		lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				VisibEmailTransCaselleAssociateUOLookupOrganigramma lookupOrganigrammaPopup = new VisibEmailTransCaselleAssociateUOLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});

		spacer = new SpacerItem(); 
		spacer.setWidth(20); 
		spacer.setColSpan(1);
			
		
		// COD.RAPIDO
		
		codRapidoItem = new ExtendedTextItem("codRapido", "Cod.UO");
		//codRapidoItem.setTitleOrientation(TitleOrientation.TOP);
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idUo", (String) null);
				mDynamicForm.setValue("descrizione", (String) null);
				mDynamicForm.setValue("organigramma", (String) null);
				mDynamicForm.setValue("typeNodo", (String) null);
				mDynamicForm.clearErrors(true);
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
											mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
											mDynamicForm.setValue("organigramma", data.get(i).getAttribute("id"));
											mDynamicForm.setValue("idUo", data.get(i).getAttribute("idUo"));
											mDynamicForm.setValue("typeNodo", data.get(i).getAttribute("typeNodo"));
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
						organigrammaItem.fetchData();
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
		
		// DENOMINAZIONE UO		
		SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT, new String[] { "descrizione" }, true);
		organigrammaDS.addParam("tipoAssegnatari", "UO");
		organigrammaDS.addParam("finalita", "ALTRO");
		organigrammaDS.addParam("idUd", null);

		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", "Denominazione UO", organigrammaDS) {
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("organigramma", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("typeNodo", record.getAttributeAsString("typeNodo"));
				mDynamicForm.setValue("idUo", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.setValue("flgIncludiSottoUO", false);
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
					mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codRapido", "");
				}
				mDynamicForm.setValue("idUo", "");
				mDynamicForm.setValue("typeNodo", "");
				mDynamicForm.setValue("descrizione", "");
				mDynamicForm.setValue("flgIncludiSottoUO", false);
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
						mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codRapido", "");
					}
					mDynamicForm.setValue("idUo", "");
					mDynamicForm.setValue("typeNodo", "");
					mDynamicForm.setValue("descrizione", "");
					mDynamicForm.setValue("flgIncludiSottoUO", false);
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
		final ListGridField codiceField = new ListGridField("codice", "Cod. rapido");
		codiceField.setWidth(80);
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
		ListGridField descrizioneField = new ListGridField("descrizione", "Denominazione");
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
		organigrammaItem.setOptionDataSource(organigrammaDS);
		organigrammaItem.setWidth(400);
		organigrammaItem.setClearable(true);
		organigrammaItem.setShowIcons(true);
		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		ListGrid pickListProperties = organigrammaItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {
			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapido = mDynamicForm.getValueAsString("codRapido");
				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
				organigrammaDS.addParam("codice", codRapido);
				organigrammaItem.setOptionDataSource(organigrammaDS);
				organigrammaItem.invalidateDisplayValueCache();
			}
		});
		organigrammaItem.setPickListProperties(pickListProperties);
		//organigrammaItem.setTitleOrientation(TitleOrientation.TOP);
				
		// INCLUDI SOTTO UO
		flgIncludiSottoUOItem = new CheckboxItem("flgIncludiSottoUO", "incluse UO subordinate");
		flgIncludiSottoUOItem.setColSpan(1);
		flgIncludiSottoUOItem.setWidth(50);
		flgIncludiSottoUOItem.setAlign(Alignment.LEFT);		
	
	}
	

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		
		//Inizializzo le combo con la descrizione restituita dal servizio		
		mDynamicForm.clearErrors(true);
		if (record.getAttribute("organigramma") != null && !"".equals(record.getAttributeAsString("idUo"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("organigramma"), record.getAttribute("descrizione"));
			organigrammaItem.setValueMap(valueMap);
		}
	}
	
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	public void setFormValuesFromRecord(Record record) {
		String idOrganigramma = record.getAttribute("idUoSvUt");
		// if(idOrganigramma == null || "".equals(idOrganigramma)) {
		// idOrganigramma = record.getAttribute("idFolder");
		// }
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		mDynamicForm.setValue("organigramma", tipo + idOrganigramma);
		mDynamicForm.setValue("idUo", idOrganigramma);
		mDynamicForm.setValue("typeNodo", tipo);
		// mDynamicForm.setValue("descrizione", ""); // da settare
		mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
		mDynamicForm.clearErrors(true);
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					organigrammaItem.fetchData();
				}
			});
		}
	}
	
	public class VisibEmailTransCaselleAssociateUOLookupOrganigramma extends LookupOrganigrammaPopup {

		public VisibEmailTransCaselleAssociateUOLookupOrganigramma(Record record) {
			super(record, true, new Integer(0)); // includo solo UO
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
			return "ALTRO";
		}

	}
	
}
