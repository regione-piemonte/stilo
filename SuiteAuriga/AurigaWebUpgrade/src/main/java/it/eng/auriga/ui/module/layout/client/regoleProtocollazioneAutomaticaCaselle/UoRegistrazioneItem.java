/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
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
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
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
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.regoleProtocollazioneAutomaticaCaselle.UoDestinatariaCanvas.UoRegistrazioneLookupOrganigramma;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class UoRegistrazioneItem extends CanvasItem {
	
	private DynamicForm mDynamicForm;
	
	private HiddenItem idUoHiddenItem;
	private HiddenItem typeNodoHiddenItem;
	private HiddenItem codRapidoChangedHiddenItem;
	private ExtendedTextItem codRapidoItem;
	private HiddenItem descrizioneHiddenItem;
	private FilteredSelectItemWithDisplay organigrammaItem;
	private ImgButtonItem lookupOrganigrammaButton;
	private FilteredSelectItemWithDisplay gruppoItem;
	
//	public void disegna() {
//
//		mDynamicForm = new DynamicForm();
//		mDynamicForm.setWrapItemTitles(false);
////		mDynamicForm.setCellBorder(1);
//												
//		idUoHiddenItem = new HiddenItem("idUo");
//		typeNodoHiddenItem = new HiddenItem("typeNodo");
//		codRapidoChangedHiddenItem = new HiddenItem("codRapidoChanged");
//		
//		codRapidoItem = new ExtendedTextItem("codRapido", "Cod.");
//		codRapidoItem.setWidth(120);
//		codRapidoItem.setColSpan(1);
//		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				mDynamicForm.setValue("codRapidoChanged", true);
//				mDynamicForm.setValue("idUo", (String) null);
//				mDynamicForm.setValue("descrizione", (String) null);
//				mDynamicForm.setValue("organigramma", (String) null);
//				mDynamicForm.setValue("typeNodo", (String) null);
//				mDynamicForm.setValue("gruppo", (String) null);
//				mDynamicForm.clearErrors(true);
//				final String value = codRapidoItem.getValueAsString();
//					if (value != null && !"".equals(value)) {
//						organigrammaItem.fetchData(new DSCallback() {
//
//							@Override
//							public void execute(DSResponse response, Object rawData, DSRequest request) {
//								RecordList data = response.getDataAsRecordList();
//								boolean trovato = false;
//								if (data.getLength() > 0) {
//									for (int i = 0; i < data.getLength(); i++) {
//										String codice = data.get(i).getAttribute("codice");
//										String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
//										if (value.equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
//											mDynamicForm.setValue("descrizione", data.get(i).getAttribute("descrizioneOrig"));
//											mDynamicForm.setValue("organigramma", data.get(i).getAttribute("id"));
//											mDynamicForm.setValue("idUo", data.get(i).getAttribute("idUo"));
//											mDynamicForm.setValue("typeNodo", data.get(i).getAttribute("typeNodo"));
//											mDynamicForm.clearErrors(true);
//											trovato = true;
//											manageOnChanged();
//											break;
//										}
//									}
//								}
//								if (!trovato) {
//									codRapidoItem.validate();
//									codRapidoItem.blurItem();
//								}
//							}
//						});
//					} else {
//						organigrammaItem.fetchData();
//					}
//				
//			}
//		});
//		codRapidoItem.setValidators(new CustomValidator() {
//
//			@Override
//			protected boolean condition(Object value) {
//					if (codRapidoItem.getValue() != null && !"".equals(codRapidoItem.getValueAsString().trim()) 
//							&& organigrammaItem.getValue() == null	&& gruppoItem.getValue() == null) {
//						return false;
//					}
//				return true;
//			}
//		});
//
//		descrizioneHiddenItem = new HiddenItem("descrizione");
//
//		SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
//				new String[] { "descrizione" }, true);
//		organigrammaDS.addParam("finalita", getFinalitaLoadComboOrganigramma());
//		organigrammaDS.addParam("flgSoloValide", "1");
//		
//		organigrammaItem = new FilteredSelectItemWithDisplay("organigramma", organigrammaDS) {
//
//			@Override
//			public void manageOnCellClick(CellClickEvent event) {
//				String flgSelXFinalita = event.getRecord().getAttributeAsString("flgSelXFinalita");
//				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
//					onOptionClick(event.getRecord());
//				} else {
//					event.cancel();
//					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
//					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//
//						@Override
//						public void execute() {
//							clearSelect();
//						}
//					});
//				}
//			}
//
//			@Override
//			public void onOptionClick(Record record) {
//				super.onOptionClick(record);
////				mDynamicForm.setValue("organigramma", record.getAttributeAsString("id"));
//				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codice"));
//				mDynamicForm.setValue("typeNodo", record.getAttributeAsString("typeNodo"));
//				mDynamicForm.setValue("idUo", record.getAttributeAsString("idUo"));
//				mDynamicForm.setValue("descrizione", record.getAttributeAsString("descrizioneOrig"));
//				mDynamicForm.clearErrors(true);
//				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//
//					@Override
//					public void execute() {
//						organigrammaItem.fetchData();
//					}
//				});
//			}
//
//			@Override
//			protected void clearSelect() {
//				super.clearSelect();
//				mDynamicForm.setValue("organigramma", "");
//				if(isDimOrganigrammaNonStd()) {
//					mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
//				} else {
//					mDynamicForm.setValue("codRapido", "");
//				}
//				mDynamicForm.setValue("idUo", "");
//				mDynamicForm.setValue("typeNodo", "");
//				mDynamicForm.setValue("descrizione", "");
//				mDynamicForm.clearErrors(true);				
//				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//
//					@Override
//					public void execute() {
//						organigrammaItem.fetchData();
//					}
//				});
//			};
//
//			@Override
//			public void setValue(String value) {
//				super.setValue(value);
//				if (value == null || "".equals(value)) {
//					mDynamicForm.setValue("organigramma", "");
//					if(isDimOrganigrammaNonStd()) {
//						mDynamicForm.setValue("codRapido", AurigaLayout.getCodRapidoOrganigramma());
//					} else {
//						mDynamicForm.setValue("codRapido", "");
//					}
//					mDynamicForm.setValue("idUo", "");
//					mDynamicForm.setValue("typeNodo", "");
//					mDynamicForm.setValue("descrizione", "");
//					mDynamicForm.clearErrors(true);				
//					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//
//						@Override
//						public void execute() {
//							organigrammaItem.fetchData();
//						}
//					});
//				}
//			}
//		};
//		List<ListGridField> organigrammaPickListFields = new ArrayList<ListGridField>();
//		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
//			ListGridField typeNodoField = new ListGridField("iconaTypeNodo", "Tipo");
//			typeNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
//			typeNodoField.setAlign(Alignment.CENTER);
//			typeNodoField.setWidth(30);
//			typeNodoField.setShowHover(false);
//			typeNodoField.setCanFilter(false);
//			typeNodoField.setCellFormatter(new CellFormatter() {
//
//				@Override
//				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//					if (record.getAttribute("iconaTypeNodo") != null && !"".equals(record.getAttributeAsString("iconaTypeNodo"))) {
//						return "<div align=\"center\"><img src=\"images/organigramma/tipo/" + record.getAttribute("iconaTypeNodo")
//								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//					}
//					return null;
//				}
//			});
//			organigrammaPickListFields.add(typeNodoField);
//		}
//		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
//		codiceField.setWidth(80);
//		codiceField.setShowHover(true);
//		codiceField.setHoverCustomizer(new HoverCustomizer() {
//			
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//				return record != null ? record.getAttribute("descrizioneEstesa") : null;
//			}
//		});
//		if (isDimOrganigrammaNonStd()) {
//			codiceField.setCanFilter(false);
//			// TextItem codiceFilterEditorType = new TextItem();
//			// codiceFilterEditorType.setCanEdit(false);
//			// codiceField.setFilterEditorType(codiceFilterEditorType);
//		}
//		organigrammaPickListFields.add(codiceField);
//		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
//		descrizioneField.setWidth("*");
//		organigrammaPickListFields.add(descrizioneField);
//
////		if (getItem() instanceof AssegnazioneEmailItem) {
////			// utilizzo stessa property colonna del flg Punto Protocollo
////			ListGridField flgPuntoRaccoltaEmailField = new ListGridField("flgPuntoProtocollo", I18NUtil.getMessages().organigramma_uo_detail_flgPuntoRaccoltaEmail_title());
////			flgPuntoRaccoltaEmailField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
////			flgPuntoRaccoltaEmailField.setAlign(Alignment.CENTER);
////			flgPuntoRaccoltaEmailField.setWidth(30);
////			flgPuntoRaccoltaEmailField.setShowHover(true);
////			flgPuntoRaccoltaEmailField.setCanFilter(false);
////			flgPuntoRaccoltaEmailField.setCellFormatter(new CellFormatter() {
////
////				@Override
////				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
////					if (record.getAttribute("flgPuntoRaccoltaEmail") != null && record.getAttributeAsBoolean("flgPuntoRaccoltaEmail")) {
////						return "<div align=\"center\"><img src=\"images/organigramma/puntoRaccoltaEmail.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
////					}
////					return null;
////				}
////			});
////			flgPuntoRaccoltaEmailField.setHoverCustomizer(new HoverCustomizer() {
////
////				@Override
////				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
////					if (record != null && record != null && record.getAttribute("flgPuntoRaccoltaEmail") != null
////							&& record.getAttributeAsBoolean("flgPuntoRaccoltaEmail")) {
////						return I18NUtil.getMessages().organigramma_uo_detail_flgPuntoRaccoltaEmailItem_hover();
////					}
////					return null;
////				}
////			});
////
////			organigrammaPickListFields.add(flgPuntoRaccoltaEmailField);
////		} else if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
////			ListGridField flgPuntoProtocolloField = new ListGridField("flgPuntoProtocollo", "Punto di Protocollo");
////			flgPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
////			flgPuntoProtocolloField.setAlign(Alignment.CENTER);
////			flgPuntoProtocolloField.setWidth(30);
////			flgPuntoProtocolloField.setShowHover(true);
////			flgPuntoProtocolloField.setCanFilter(false);
////			flgPuntoProtocolloField.setCellFormatter(new CellFormatter() {
////
////				@Override
////				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
////					if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
////						return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
////					}
////					return null;
////				}
////			});
////			flgPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {
////
////				@Override
////				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
////					if (record != null && record != null && record.getAttribute("flgPuntoProtocollo") != null
////							&& record.getAttributeAsBoolean("flgPuntoProtocollo")) {
////						return "Punto di Protocollo";
////					}
////					return null;
////				}
////			});
////			organigrammaPickListFields.add(flgPuntoProtocolloField);
////		}
//		
//		
//		organigrammaItem.setPickListFields(organigrammaPickListFields.toArray(new ListGridField[organigrammaPickListFields.size()]));
//		if (isDimOrganigrammaNonStd()) {
//			organigrammaItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());
//		} else {
//			organigrammaItem.setFilterLocally(true);
//		}
//		
//		organigrammaItem.setAutoFetchData(false);
//		organigrammaItem.setFetchMissingValues(true);
//		organigrammaItem.setAlwaysFetchMissingValues(true);
//		organigrammaItem.setCachePickListResults(false);		
//		organigrammaItem.setValueField("id");
//		organigrammaItem.setOptionDataSource(organigrammaDS);
//		organigrammaItem.setWidth(300);
//		organigrammaItem.setClearable(true);
//		organigrammaItem.setShowIcons(true);
//		if (AurigaLayout.getIsAttivaAccessibilita()) {
//			organigrammaItem.setTitle("Lista organigramma");
//			organigrammaItem.setShowTitle(true);	
//		} else {
//			organigrammaItem.setShowTitle(false);			
//		}
//		organigrammaItem.addChangedHandler(new ChangedHandler() {
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				markForRedraw();
//				manageOnChanged();
//			}
//		});
//		organigrammaItem.setItemHoverFormatter(new FormItemHoverFormatter() {
//
//			@Override
//			public String getHoverHTML(FormItem item, DynamicForm form) {
//				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
//			}
//		});
//		ListGrid organigrammaPickListProperties = organigrammaItem.getPickListProperties();
//		organigrammaPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//			@Override
//			public void onFilterData(FetchDataEvent event) {
//				String codRapido = mDynamicForm.getValueAsString("codRapido");
////				if (((AssegnazioneItem)getItem()).isDimOrganigrammaNonStd() && (codRapido == null || "".equals(codRapido))) {
////					if(codRapidoItem.getCanEdit() != null && codRapidoItem.getCanEdit()) {
////						mDynamicForm.setFieldErrors("codRapido", "Filtro obbligatorio per popolare la lista di scelta");
////					}
////				}				
//				GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaItem.getOptionDataSource();
//				// organigrammaDS.addParam("ciToAdd", mDynamicForm.getValueAsString("organigramma"));
//				organigrammaDS.addParam("codice", codRapido);
//				organigrammaDS.addParam("finalita", getFinalitaLoadComboOrganigramma());
//				organigrammaDS.addParam("flgSoloValide", "1");				
//				organigrammaItem.setOptionDataSource(organigrammaDS);
//				organigrammaItem.invalidateDisplayValueCache();
//			}
//		});
//		organigrammaItem.setPickListProperties(organigrammaPickListProperties);
//
//		lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png",
//				I18NUtil.getMessages().protocollazione_detail_lookupOrganigrammaButton_prompt());
//		lookupOrganigrammaButton.setColSpan(1);
//		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {
//
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				UoRegistrazioneLookupOrganigramma lookupOrganigrammaPopup = new UoRegistrazioneLookupOrganigramma(null);
//				lookupOrganigrammaPopup.show();
//			}
//		});
//		
//		mDynamicForm.setFields(
//			idUoHiddenItem, 
//			typeNodoHiddenItem, 
//			codRapidoChangedHiddenItem, 
//			codRapidoItem, 
//			descrizioneHiddenItem, 
//			lookupOrganigrammaButton, 
//			organigrammaItem
//		);
//
//		mDynamicForm.setNumCols(16);
//		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");
//
//		addChild(mDynamicForm);
//
//	}
//	
//	public void setFormValuesFromRecord(Record record) {
//		String idOrganigramma = record.getAttribute("idUoSvUt");
//		// if(idOrganigramma == null || "".equals(idOrganigramma)) {
//		// idOrganigramma = record.getAttribute("idFolder");
//		// }
//		String tipo = record.getAttribute("tipo");
//		int pos = tipo.indexOf("_");
//		if (pos != -1) {
//			tipo = tipo.substring(0, pos);
//		}
//		mDynamicForm.setValue("organigramma", tipo + idOrganigramma);
//		mDynamicForm.setValue("idUo", idOrganigramma);
//		mDynamicForm.setValue("typeNodo", tipo);
//		// mDynamicForm.setValue("descrizione", ""); // da settare
//		mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
//		mDynamicForm.clearErrors(true);
//		manageOnChanged();
//		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//
//			@Override
//			public void execute() {
//				organigrammaItem.fetchData();
//			}
//		});
//	}
//
//	public Record getFormValuesAsRecord() {
//		return mDynamicForm.getValuesAsRecord();
//	}
//
//	public boolean isDimOrganigrammaNonStd() {
//		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST/ES");		
//	}
//
//	public String getFinalitaOrganigrammaLookup() {
//		return "ASSEGNAZIONE";
//	}
//
//	public String getFinalitaLoadComboOrganigramma() {
//		return "ASS";
//	}
//
//	public void manageOnChanged() {
//		
//	}
}
