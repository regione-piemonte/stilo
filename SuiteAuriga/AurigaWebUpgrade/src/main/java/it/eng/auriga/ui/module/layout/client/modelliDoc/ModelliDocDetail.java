/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ModelliDocDetail extends CustomDetail {

	protected ModelliDocDetail instance;

	protected DetailSection datiModelloSection;
	protected DynamicForm datiModelloForm;
	protected HiddenItem idModelloItem;
	protected HiddenItem idDocItem;
	protected TextItem nomeModelloItem;
	protected TextAreaItem desModelloItem;
	protected SelectItem tipoModelloItem;
	protected HiddenItem nomeTabellaItem;
	protected CheckboxItem flgGeneraPdfItem;
	protected SelectItem tipoEntitaAssociataItem;	
	protected HiddenItem idEntitaAssociataItem;
	protected HiddenItem nomeEntitaAssociataItem;
	protected SelectItem tipoDocumentoItem;
	protected SelectItem tipoFolderItem;
	protected SelectItem tipoProcedimentoItem;
	protected TextAreaItem noteItem;
	
	protected DetailSection fileModelloSection;
	protected DynamicForm fileModelloForm;	
	protected AllegatiItem fileModelloItem;
	
	public ModelliDocDetail(String nomeEntita) {

		super(nomeEntita);

		instance = this;

		createDatiModelloSection();
		createFileModelloSection();
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		lVLayout.addMember(datiModelloSection);
		lVLayout.addMember(fileModelloSection);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}
		
	protected void createDatiModelloSection() {

		createDatiModelloForm();

		datiModelloSection = new DetailSection("Dati principali", true, true, true, datiModelloForm);		
	}
	
	protected void createDatiModelloForm() {
		
		datiModelloForm = new DynamicForm();
		datiModelloForm.setValuesManager(vm);
		datiModelloForm.setWidth100();
		datiModelloForm.setPadding(5);
		datiModelloForm.setWrapItemTitles(false);
		datiModelloForm.setNumCols(10);
		datiModelloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		idModelloItem = new HiddenItem("idModello");
		idDocItem = new HiddenItem("idDoc");
		
		nomeModelloItem = new TextItem("nomeModello", "Nome");
		nomeModelloItem.setWidth(250);
		nomeModelloItem.setStartRow(true);
		nomeModelloItem.setRequired(true);
		
		desModelloItem = new TextAreaItem("desModello", "Descrizione");	
		desModelloItem.setLength(4000);
		desModelloItem.setHeight(40);
		desModelloItem.setWidth(654);
		desModelloItem.setColSpan(4);
		desModelloItem.setStartRow(true);

		tipoModelloItem = new SelectItem("tipoModello", "Tipo");
		LinkedHashMap<String, String> tipoModelloValueMap = new LinkedHashMap<String, String>();
		tipoModelloValueMap.put(TipoModelloDoc.ODT_CON_FREEMARKERS.getValue(), "odt con freemarkers");
		tipoModelloValueMap.put(TipoModelloDoc.DOCX_CON_CAMPI_CONTROLLO.getValue(), "docx con campi di controllo");
		tipoModelloValueMap.put(TipoModelloDoc.DOCX_CON_PLACEHOLDER.getValue(), "docx con placeholder");
		tipoModelloItem.setValueMap(tipoModelloValueMap);
		tipoModelloItem.setAllowEmptyValue(false);
		tipoModelloItem.setWidth(250);
		tipoModelloItem.setStartRow(true);
		tipoModelloItem.setRequired(true);
		
		nomeTabellaItem = new HiddenItem("nomeTabella");
		
		flgGeneraPdfItem = new CheckboxItem("flgGeneraPdf", "genera in pdf");
		flgGeneraPdfItem.setWidth(334);
		flgGeneraPdfItem.setEndRow(true);
		
		tipoEntitaAssociataItem = new SelectItem("tipoEntitaAssociata", "Tipo oggetto associato");
		LinkedHashMap<String, String> tipoEntitaAssociataValueMap = new LinkedHashMap<String, String>();
		tipoEntitaAssociataValueMap.put("TD", "Tipo documento");
		tipoEntitaAssociataValueMap.put("TF", "Tipo folder");
//		tipoEntitaAssociataValueMap.put("TP", "Tipo di processo/procedimento");
		tipoEntitaAssociataItem.setValueMap(tipoEntitaAssociataValueMap);
		tipoEntitaAssociataItem.setAllowEmptyValue(true);
		tipoEntitaAssociataItem.setWidth(250);
		tipoEntitaAssociataItem.setStartRow(true);
		tipoEntitaAssociataItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				datiModelloForm.markForRedraw();
			}
		});
		
		idEntitaAssociataItem = new HiddenItem("idEntitaAssociata");
		nomeEntitaAssociataItem = new HiddenItem("nomeEntitaAssociata");
		
		GWTRestDataSource tipoDocumentoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		
		tipoDocumentoItem = new SelectItem("tipoDocumento") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				vm.setValue("idEntitaAssociata", record.getAttribute("idTipoDocumento"));
				vm.setValue("nomeEntitaAssociata", record.getAttribute("descTipoDocumento"));	
				datiModelloForm.markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				vm.setValue("tipoDocumento", "");	
				vm.setValue("idEntitaAssociata", "");	
				vm.setValue("nomeEntitaAssociata", "");	
				datiModelloForm.markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					vm.setValue("tipoDocumento", "");
					vm.setValue("idEntitaAssociata", "");	
					vm.setValue("nomeEntitaAssociata", "");	
				}
				datiModelloForm.markForRedraw();
            }
			
		};
		tipoDocumentoItem.setWidth(334);
		tipoDocumentoItem.setEndRow(true);
		tipoDocumentoItem.setShowTitle(false);
		tipoDocumentoItem.setPickListWidth(450);
		tipoDocumentoItem.setValueField("idTipoDocumento");
		tipoDocumentoItem.setDisplayField("descTipoDocumento");
		tipoDocumentoItem.setOptionDataSource(tipoDocumentoDS);
		tipoDocumentoItem.setClearable(true);
		tipoDocumentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("tipoEntitaAssociata") != null && "TD".equals(vm.getValueAsString("tipoEntitaAssociata"));
			}
		});
		
		GWTRestDataSource tipoFolderDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
		
		tipoFolderItem = new SelectItem("tipoFolder") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				vm.setValue("idEntitaAssociata", record.getAttribute("idFolderType"));
				vm.setValue("nomeEntitaAssociata", record.getAttribute("descFolderType"));	
				datiModelloForm.markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				vm.setValue("tipoFolder", "");	
				vm.setValue("idEntitaAssociata", "");	
				vm.setValue("nomeEntitaAssociata", "");	
				datiModelloForm.markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					vm.setValue("tipoFolder", "");
					vm.setValue("idEntitaAssociata", "");	
					vm.setValue("nomeEntitaAssociata", "");	
				}
				datiModelloForm.markForRedraw();
            }
			
		};
		tipoFolderItem.setWidth(334);
		tipoFolderItem.setEndRow(true);
		tipoFolderItem.setShowTitle(false);
		tipoFolderItem.setPickListWidth(450);
		tipoFolderItem.setValueField("idFolderType");
		tipoFolderItem.setDisplayField("descFolderType");
		tipoFolderItem.setOptionDataSource(tipoFolderDS);
		tipoFolderItem.setClearable(true);
		tipoFolderItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("tipoEntitaAssociata") != null && "TF".equals(vm.getValueAsString("tipoEntitaAssociata"));
			}
		});
		
		GWTRestDataSource tipoProcedimentoDS = new GWTRestDataSource("LoadComboTipiProcDataSource", "key", FieldType.TEXT);
		
		tipoProcedimentoItem = new SelectItem("tipoProcedimento") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				vm.setValue("idEntitaAssociata", record.getAttribute("key"));
				vm.setValue("nomeEntitaAssociata", record.getAttribute("value"));	
				datiModelloForm.markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				vm.setValue("tipoProcedimento", "");	
				vm.setValue("idEntitaAssociata", "");	
				vm.setValue("nomeEntitaAssociata", "");	
				datiModelloForm.markForRedraw();
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					vm.setValue("tipoProcedimento", "");
					vm.setValue("idEntitaAssociata", "");	
					vm.setValue("nomeEntitaAssociata", "");	
				}
				datiModelloForm.markForRedraw();
            }
			
		};
		tipoProcedimentoItem.setWidth(334);
		tipoProcedimentoItem.setEndRow(true);
		tipoProcedimentoItem.setShowTitle(false);
		tipoProcedimentoItem.setPickListWidth(450);
		tipoProcedimentoItem.setValueField("key");
		tipoProcedimentoItem.setDisplayField("value");
		tipoProcedimentoItem.setOptionDataSource(tipoProcedimentoDS);		
		tipoProcedimentoItem.setClearable(true);
		tipoProcedimentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return vm.getValueAsString("tipoEntitaAssociata") != null && "TP".equals(vm.getValueAsString("tipoEntitaAssociata"));
			}
		});
		
		noteItem = new TextAreaItem("note", "Annotazioni");	
		noteItem.setLength(4000);
		noteItem.setHeight(40);
		noteItem.setWidth(654);
		noteItem.setColSpan(4);
		noteItem.setStartRow(true);
				
		datiModelloForm.setFields(
			idModelloItem, 
			idDocItem, 
			nomeModelloItem, 
			desModelloItem,
			tipoModelloItem,
			nomeTabellaItem,
			flgGeneraPdfItem,
			tipoEntitaAssociataItem,	
			idEntitaAssociataItem,
			nomeEntitaAssociataItem,
			tipoDocumentoItem,
			tipoFolderItem,
			tipoProcedimentoItem,
			noteItem
		);	
		
	}
	
	protected void createFileModelloSection() {

		createFileModelloForm();

		fileModelloSection = new DetailSection("File del modello", true, true, true, fileModelloForm);		
	}
	
	protected void createFileModelloForm() {
		
		fileModelloForm = new DynamicForm();
		fileModelloForm.setValuesManager(vm);
		fileModelloForm.setWidth100();
		fileModelloForm.setPadding(5);
		fileModelloForm.setWrapItemTitles(false);
		fileModelloForm.setNumCols(10);
		fileModelloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
				
		fileModelloItem = new AllegatiItem() {
			
			@Override
			public boolean getShowTitleNomeFileAllegato() {
				return false;
			}
			
			@Override
			public boolean validateFormatoFileAllegato(InfoFileRecord lInfoFileRecord) {									
				if(vm.getValueAsString("tipoModello") != null && TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equals(vm.getValueAsString("tipoModello"))) {
					return !lInfoFileRecord.isFirmato() && lInfoFileRecord.getCorrectFileName() != null && lInfoFileRecord.getCorrectFileName().toLowerCase().endsWith(".odt");
				} else {
					return !lInfoFileRecord.isFirmato() && lInfoFileRecord.getCorrectFileName() != null && lInfoFileRecord.getCorrectFileName().toLowerCase().endsWith(".docx");
				}
			}

			@Override
			public String getFormatoFileNonValidoErrorMessage() {
				return "Il file non è nel formato atteso";
			}

			@Override
			public boolean showNumeroAllegato() {
				return false;
			};

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showDescrizioneFileAllegato() {
				return false;
			}
			
			@Override
			public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
				return true;
			}
			
			@Override
			public boolean isHideFirmaInAltreOperazioniButton() {
				return true;
			}
			
			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return true;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}
			
		};
		fileModelloItem.setName("listaModelli");
		fileModelloItem.setShowTitle(false);
		fileModelloItem.setNotReplicable(true);
		fileModelloItem.setAttribute("obbligatorio", true);
		fileModelloItem.setHideVisualizzaVersioniButton(true);
				
		fileModelloForm.setFields(fileModelloItem);		
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		vm.setValue("tipoDocumento", (String) null);
		vm.setValue("tipoFolder", (String) null);
		vm.setValue("tipoProcedimento", (String) null);
		if (record.getAttribute("tipoEntitaAssociata") != null) {
			if ("TD".equals(record.getAttribute("tipoEntitaAssociata"))) {
				vm.setValue("tipoDocumento", record.getAttribute("idEntitaAssociata"));	
				if (tipoDocumentoItem != null) {
					if (record.getAttribute("idEntitaAssociata") != null && !"".equalsIgnoreCase(record.getAttribute("idEntitaAssociata")) && 
						record.getAttribute("nomeEntitaAssociata") != null && !"".equalsIgnoreCase(record.getAttribute("nomeEntitaAssociata"))){
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						valueMap.put(record.getAttribute("idEntitaAssociata"), record.getAttribute("nomeEntitaAssociata"));
						tipoDocumentoItem.setValueMap(valueMap);							
					}
				}
				GWTRestDataSource tipoDocumentoDS = (GWTRestDataSource) tipoDocumentoItem.getOptionDataSource();
				if (record.getAttribute("idEntitaAssociata") != null && !"".equals(record.getAttribute("idEntitaAssociata"))) {
					tipoDocumentoDS.addParam("idTipoDocumento", record.getAttribute("idEntitaAssociata"));
				} else {
					tipoDocumentoDS.addParam("idTipoDocumento", null);
				}				
				tipoDocumentoItem.setOptionDataSource(tipoDocumentoDS);				
			} else if ("TF".equals(record.getAttribute("tipoEntitaAssociata"))) {
				vm.setValue("tipoFolder", record.getAttribute("idEntitaAssociata"));	
				if (tipoFolderItem != null) {
					if (record.getAttribute("idEntitaAssociata") != null && !"".equalsIgnoreCase(record.getAttribute("idEntitaAssociata")) && 
						record.getAttribute("nomeEntitaAssociata") != null && !"".equalsIgnoreCase(record.getAttribute("nomeEntitaAssociata"))){
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						valueMap.put(record.getAttribute("idEntitaAssociata"), record.getAttribute("nomeEntitaAssociata"));
						tipoFolderItem.setValueMap(valueMap);							
					}
				}
				GWTRestDataSource tipoFolderDS = (GWTRestDataSource) tipoFolderItem.getOptionDataSource();
				if (record.getAttribute("idEntitaAssociata") != null && !"".equals(record.getAttribute("idEntitaAssociata"))) {
					tipoFolderDS.addParam("idFolderType", record.getAttribute("idEntitaAssociata"));
				} else {
					tipoFolderDS.addParam("idFolderType", null);
				}				
				tipoFolderItem.setOptionDataSource(tipoFolderDS);	
			} else if ("TP".equals(record.getAttribute("tipoEntitaAssociata"))) {
				vm.setValue("tipoProcedimento", record.getAttribute("idEntitaAssociata"));
				if (tipoProcedimentoItem != null) {
					if (record.getAttribute("idEntitaAssociata") != null && !"".equalsIgnoreCase(record.getAttribute("idEntitaAssociata")) && 
						record.getAttribute("nomeEntitaAssociata") != null && !"".equalsIgnoreCase(record.getAttribute("nomeEntitaAssociata"))){
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						valueMap.put(record.getAttribute("idEntitaAssociata"), record.getAttribute("nomeEntitaAssociata"));
						tipoProcedimentoItem.setValueMap(valueMap);							
					}
				}
				GWTRestDataSource tipoProcedimentoDS = (GWTRestDataSource) tipoProcedimentoItem.getOptionDataSource();
				if (record.getAttribute("idEntitaAssociata") != null && !"".equals(record.getAttribute("idEntitaAssociata"))) {
					tipoProcedimentoDS.addParam("idProcessType", record.getAttribute("idEntitaAssociata"));
				} else {
					tipoProcedimentoDS.addParam("idProcessType", null);
				}				
				tipoProcedimentoItem.setOptionDataSource(tipoProcedimentoDS);	
			}
		}	
	}
	
	public boolean isRecordAbilToProfile(Record record) {
		RecordList listaModelli = record.getAttributeAsRecordList("listaModelli");
		Record recordFileModello = listaModelli != null && listaModelli.getLength() > 0 ? listaModelli.get(0) : null;
		String uriFileModello = recordFileModello != null ? recordFileModello.getAttributeAsString("uriFileAllegato") : null;
		return uriFileModello != null && !"".equals(uriFileModello) &&
			   record.getAttributeAsString("tipoModello") != null && TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equals(record.getAttributeAsString("tipoModello")) /**&&
			   record.getAttributeAsString("nomeTabella") != null && !"".equals(record.getAttributeAsString("nomeTabella")) 		
			   record.getAttributeAsString("tipoEntitaAssociata") != null && !"".equals(record.getAttributeAsString("tipoEntitaAssociata")) &&		
			   record.getAttributeAsString("idEntitaAssociata") != null && !"".equals(record.getAttributeAsString("idEntitaAssociata")) &&
			   record.getAttributeAsString("nomeEntitaAssociata") != null && !"".equals(record.getAttributeAsString("nomeEntitaAssociata"))*/;
	}
	
	public void manageProfilaButtonClick() {		
		Record record = new Record(vm.getValues());		
		ProfilaturaModelliDocWindow profilaturaModelliDocWindow = new ProfilaturaModelliDocWindow(record.getAttributeAsString("idModello"), record.getAttributeAsString("nomeModello"), record.getAttributeAsString("nomeTabella"), record.getAttributeAsString("tipoEntitaAssociata"), record.getAttributeAsString("idEntitaAssociata"), record.getAttributeAsString("nomeEntitaAssociata")) {
			
			@Override
			public void manageOnCloseClick() {
				super.manageOnCloseClick();
				if(getLayout() != null) {
					getLayout().reload();
				}
			}
		};
		profilaturaModelliDocWindow.show();		
	}	
	
	public void onSaveButtonClick() {
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				vm.clearErrors(true);
				if(validate()) {
					final Record lRecordToSave = getRecordToSave();
					final String idModello = lRecordToSave.getAttribute("idModello");
					String tipoModello = lRecordToSave.getAttribute("tipoModello");
					RecordList listaModelli = lRecordToSave.getAttributeAsRecordList("listaModelli");
					Record recordFileModello = listaModelli != null && listaModelli.getLength() > 0 ? listaModelli.get(0) : null;
					// Forzo isChanged a true per fare in moido che la profilatura venga ricalcolata ad ogni salvataggio
					recordFileModello.setAttribute("isChanged", true);
					boolean isChangedFileModello = recordFileModello != null && recordFileModello.getAttributeAsBoolean("isChanged") != null ? recordFileModello.getAttributeAsBoolean("isChanged") : false;
					// Se sono in modifica di un odt_con_freemarkers e ho cambiato il file del modello mostro l'alert
					if (idModello != null && !"".equals(idModello) && isChangedFileModello && tipoModello != null && TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equals(tipoModello)) {
						SC.ask("Il modello risulta già profilato. Modificando il file potresti perdere alcuni dati relativi alla profilatura del modello. Continuare?", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if(value != null && value) {
									salvaModello(lRecordToSave);
								}
							}
						});	
					} else {
						salvaModello(lRecordToSave);
					}
				}
			}
		});
	}
	
	public void salvaModello(Record record) {
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		try {			
			DSCallback callback = new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						try {
							layout.getList().manageLoadDetail(savedRecord, getRecordNum(), new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									layout.viewMode();
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(layout.getTipoEstremiRecord(response.getData()[0])), "",
											MessageType.INFO));
								}
							});
						} catch (Exception e) {
							Layout.hideWaitPopup();
						}
						if (!layout.getFullScreenDetail()) {
							layout.reloadListAndSetCurrentRecord(savedRecord);
						}
					} else {
						Layout.hideWaitPopup();
					}
				}
			};			
			if (record.getAttribute("idModello") != null && !"".equals(record.getAttribute("idModello"))) {
				lGwtRestDataSource.updateData(record, callback);
			} else {
				lGwtRestDataSource.addData(record, callback);
			}
		} catch (Exception e) {
			Layout.hideWaitPopup();					
		}
	}

}
