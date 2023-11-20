/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class CaricamentoRubricaClientiWindow extends CustomDetail {

	protected DynamicForm form;
	private SelectItem tipoCaricamentoSelect;
	protected DynamicForm formAttributi;
	protected DetailSection detailSectionAttributi;
	protected CampoCaricaRubricaClientiItem campoCaricaRubricaClientiItem;
	private GWTRestDataSource tipiDaImportareDataSource;

	protected DynamicForm formFile;
	private FileUploadItemWithFirmaAndMimeType uploadFileItem;
	private HiddenItem uriItem;
	private HiddenItem infoFileItem;

	private ToolStrip detailToolStrip;
	
	protected DetailToolStripButton salvaModelloButton;
	protected SaveModelloWindow saveModelloWindow;
	
	protected ToolStrip mainToolStrip;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	
	private HiddenItem descrizioneCompanyIdItem;

	public CaricamentoRubricaClientiWindow() {
		super("caricamento_rubrica");

		init();

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mainToolStrip, 0);
		
		populateFormFile();
		lVLayout.addMember(formFile);
		lVLayout.addMember(form);

		detailSectionAttributi = new DetailSection(I18NUtil.getMessages().caricamentoRubricaClientiWindow_section_attributi(), true, true, false, formAttributi);

		lVLayout.addMember(detailSectionAttributi);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
//		addMember(buildSaveButton());
		buildDetailToolStrip ();
		buildSaveButton();
		buildSalvaModelloButton();
		
		addMember(detailToolStrip);

	}

	public void populateFormFile() {
		formFile = new DynamicForm();
		formFile.setValuesManager(vm);
		formFile.setWrapItemTitles(false);
		formFile.setNumCols(15);
		formFile.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		uriItem = new HiddenItem("uri");
		infoFileItem = new HiddenItem("infoFile");

		CustomValidator lTipiAmmessiValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				final String uri = formFile.getValueAsString("uri");
				if (uri != null && !uri.equals("")) {
					final InfoFileRecord infoFile = new InfoFileRecord(formFile.getValue("infoFile"));
					return infoFile != null && infoFile.getMimetype() != null
							&& (infoFile.getMimetype().equals("application/excel") || infoFile.getMimetype().equals(
									"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
				}
				return true;
			}
		};
		lTipiAmmessiValidator.setErrorMessage("Formato non riconosciuto o non ammesso. Gli unici formati validi risultano essere xls/xlsx");

		CustomValidator lNomeFileValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return (value != null && !"".equals(value)) && uriItem.getValue() != null && !"".equals(uriItem.getValue());
			}
		};
		lNomeFileValidator.setErrorMessage("Attenzione, file non caricato correttamente");

//		final TextItem nomeFileItem = new TextItem("nomeFile", "<b>" + I18NUtil.getMessages().caricamentoRubricaClientiWindow_nomeFile() + "</b>");
		final TextItem nomeFileItem = new TextItem("nomeFile", (setTitleAlign("<b>" + I18NUtil.getMessages().caricamentoRubricaClientiWindow_nomeFile() + "</b>", 135, false)));
		nomeFileItem.setShowTitle(true);
		nomeFileItem.setWidth(750);
		nomeFileItem.setRequired(true);
		nomeFileItem.setColSpan(1);
		nomeFileItem.setAlign(Alignment.LEFT);
		nomeFileItem.setValidators(lTipiAmmessiValidator, lNomeFileValidator);

		uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				formFile.clearErrors(true);
				formFile.setValue("nomeFile", displayFileName);
				formFile.setValue("uri", uri);
				changedEventAfterUpload(displayFileName, uri, formFile, nomeFileItem, uriItem);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				uploadFileItem.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info == null || info.getMimetype() == null
						|| (!info.getMimetype().equals("application/excel") && !info.getMimetype().equals(
								"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
					GWTRestDataSource.printMessage(new MessageBean(
							"Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
					clickEliminaFile(formFile, uploadFileItem);
				} else {
					formFile.setValue("infoFile", info);
				}
			}
		});
		uploadFileItem.setColSpan(1);
		SpacerItem spacer = new SpacerItem();
		spacer.setEndRow(true);
		spacer.setHeight(10);
		formFile.setItems(spacer,nomeFileItem, uriItem, infoFileItem, uploadFileItem);
	}

	protected void clickEliminaFile(DynamicForm formFile, FileUploadItemWithFirmaAndMimeType uploadFileItem) {
		formFile.setValue("nomeFile", "");
		formFile.setValue("uri", "");
		formFile.clearValue("infoFile");
		uploadFileItem.getCanvas().redraw();
	}

	public void init() {

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWrapItemTitles(false);
//		form.setCellBorder(1);
		form.setNumCols(15);
		form.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		descrizioneCompanyIdItem = new HiddenItem("descrizioneCompanyId");
		campoCaricaRubricaClientiItem = new CampoCaricaRubricaClientiItem();
		campoCaricaRubricaClientiItem.setName("mappings");
		campoCaricaRubricaClientiItem.setShowTitle(false);
		campoCaricaRubricaClientiItem.setStartRow(true);

		tipiDaImportareDataSource = new GWTRestDataSource("LoadComboTipiDaImportareDataSource");
		tipoCaricamentoSelect = new SelectItem("companyId", I18NUtil.getMessages().caricamentoRubricaClientiWindow_tipiDaImportare());
//		tipoCaricamentoSelect = new SelectItem("companyId", (setTitleAlign(I18NUtil.getMessages().caricamentoRubricaClientiWindow_tipiDaImportare(), 140, false)));
//		tipoCaricamentoSelect = new SelectItem("companyId", I18NUtil.getMessages().caricamentoRubricaClientiWindow_tipiDaImportare());
		tipoCaricamentoSelect.setStartRow(true);
		tipoCaricamentoSelect.setOptionDataSource(tipiDaImportareDataSource);
		tipoCaricamentoSelect.setDisplayField("value");
		tipoCaricamentoSelect.setValueField("key");
		tipoCaricamentoSelect.setWidth(750);
		tipoCaricamentoSelect.setWrapTitle(false);
		tipoCaricamentoSelect.setAutoFetchData(false);
		tipoCaricamentoSelect.setRequired(true);
//		tipoCaricamentoSelect.setColSpan(3);
//		tipoCaricamentoSelect.setAlign(Alignment.RIGHT);
		tipoCaricamentoSelect.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				campoCaricaRubricaClientiItem.clearValue();
				campoCaricaRubricaClientiItem.setCompanyId((String) tipoCaricamentoSelect.getValue());
				if (event.getItem().getSelectedRecord().getAttribute("value") != null) {
					form.setValue("companyId", (String) tipoCaricamentoSelect.getValue());
				}
				markForRedraw();
			}
		});


		form.setItems(tipoCaricamentoSelect,descrizioneCompanyIdItem);

		formAttributi = new DynamicForm();
		formAttributi.setValuesManager(vm);
		formAttributi.setWrapItemTitles(false);
		formAttributi.setNumCols(15);
		formAttributi.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		formAttributi.setItems(campoCaricaRubricaClientiItem);
		
		
		createMainToolstrip();
	}
	
	private void createMainToolstrip() {

		// Creo la select relativa ai modelli
		createModelliSelectItem();
//		createSelectModelli();

		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);

		// Aggiungo le due select di interesse
		mainToolStrip.addFormItem(modelliSelectItem);
//		mainToolStrip.addFormItem(modelloSelectItem);

		mainToolStrip.addSpacer(50);
	}


	private void buildDetailToolStrip () {
		
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		// push all buttons to the right
		detailToolStrip.addFill();

	}

	protected void buildSaveButton() {
		final DetailToolStripButton salvaButton = new DetailToolStripButton(I18NUtil.getMessages().caricamentoRubrica_button_save(), "buttons/save.png");
		salvaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (vm.validate() && (campoCaricaRubricaClientiItem != null && campoCaricaRubricaClientiItem.validate())) {
					Layout.showWaitPopup(I18NUtil.getMessages().caricamentoRubrica_caricamentoInCorso());
					Record recordToSave = new Record(vm.getValues());

					Map oldValues = getMapValues();
					ArrayList<Map> lArrayList = (ArrayList<Map>) oldValues.get("mappings");
					recordToSave.setAttribute("mappings", "");
					if (lArrayList != null) {
						RecordList lRecordList = new RecordList();
						for (Map lMap : lArrayList) {
							Record lRecord = new Record(lMap);
							String attribute = lRecord.getAttribute("colonnaRif");
							if (attribute != null && !attribute.trim().equals("")) {
								lRecordList.add(lRecord);
							}
						}
						recordToSave.setAttribute("mappings", lRecordList);
					}
					
					GWTRestDataSource caricamentoRubricaClientiDatasource = new GWTRestDataSource("CaricamentoRubricaClientiDatasource");
					caricamentoRubricaClientiDatasource.addData(recordToSave, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

								Record returnedValue = response.getData()[0];

								String errorMessage = returnedValue.getAttribute("errorMessage");

								if (errorMessage != null && !errorMessage.isEmpty()) {

									Layout.addMessage(new MessageBean(errorMessage, "", MessageType.ERROR));

								} else {

									Layout.addMessage(new MessageBean(I18NUtil.getMessages().caricamentoRubrica_caricamentoAvvenutoConSuccesso(), "",
											MessageType.INFO));
								}
							}
							Layout.hideWaitPopup();
						}
					});
				}
			}
		});

		detailToolStrip.addButton(salvaButton);

	}

	protected void buildSalvaModelloButton() {
		
		// Creo e setto le impostazioni per il pulsante che dovra salvare il modello
		salvaModelloButton = new DetailToolStripButton(I18NUtil.getMessages().gestioneModelli_salvaModelloButton_title(), 
				"buttons/template_save.png");
		salvaModelloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
		});
		
		detailToolStrip.addButton(salvaModelloButton);

	}
	
	protected void createSaveModelloWindow(String nomeEntita) {
		saveModelloWindow = new SaveModelloWindow(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), nomeEntita,
				new SaveModelloAction(modelliDS, modelliSelectItem) {

					@Override
					public Map getMapValuesForAdd() {
						Map values = getMapValues();
						values.remove("attach");
						values.remove("uri");
						values.remove("infoFile");
						values.remove("nomeFile");
						return values;
					}
 
					@Override
					public Map getMapValuesForUpdate() {
						Map values = getMapValues();
						values.remove("attach");
						values.remove("uri");
						values.remove("infoFile");
						values.remove("nomeFile");
						return values;
					}
				}) {

			@Override
			public boolean isAbilToSavePublic() {
				return Layout.isPrivilegioAttivo("EML/MPB");
			}
			
			@Override
			public boolean isTrasmissioneAtti() {
				return false;
			}
		};
	}
	
	public void clickSalvaComeModello() {
		if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
			saveModelloWindow.clearValues();
			saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
			saveModelloWindow.redrawForms();
			saveModelloWindow.redraw();
			saveModelloWindow.show();
			//TODO CANCELLARE?
//			if (callback != null) {
//				callback.execute(null, null, null);
//			}
		}
	}
	
	public void createModelliSelectItem() {

		createModelliDatasource(nomeEntita);

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);
		modelliSelectItem.setWidth(350);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);

		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		});
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {

				if (isAbilToRemoveModello(event.getRecord())) {
					SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								final String key = event.getRecord().getAttribute("key");
								modelliDS.removeData(event.getRecord(), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										String value = (String) modelliSelectItem.getValue();
										if (key != null && value != null && key.equals(value)) {
											modelliSelectItem.setValue((String) null);
										}
									} 
								});
							}
						}
					});
				}		
			}   
		});

		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);
		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");
		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);
		
		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
//		modelliPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				String userid = (String) event.getRecord().getAttribute("userid");
				String prefName = (String) event.getRecord().getAttribute("prefName");
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
					criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
					if (userid.startsWith("UO.")) {
						String idUo = (String) event.getRecord().getAttribute("idUo");
						criteria.addCriteria("idUo", idUo);
					}
					modelliDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								Record values = new Record(JSON.decode(record.getAttribute("value")));
								Map oldValues = getMapValues();
								ArrayList<Map> lArrayList = (ArrayList<Map>) oldValues.get("attach");
								if (lArrayList != null) {
									RecordList lRecordList = new RecordList();
									for (Map lMap : lArrayList) {
										Record lRecord = new Record(lMap);
										lRecordList.add(lRecord);
									}
									values.setAttribute("attach", lRecordList);
								}
								if (values.getAttribute("companyId") != null && !values.getAttribute("companyId").equals("")) {
									campoCaricaRubricaClientiItem.setCompanyId(values.getAttribute("companyId"));
								}
								editNewRecord(values.toMap());
							}
						}
					});
				}
			}
		});
		modelliSelectItem.setPickListProperties(modelliPickListProperties);

		createSaveModelloWindow(nomeEntita);
	}
	
	protected void createModelliDatasource(String nomeEntita) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntita + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0");
	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}
	
	private Map getMapValues() {
		return vm.getValues();
	}


	protected void changedEventAfterUpload(final String displayFileName, final String uri, final DynamicForm formFile, final TextItem nomeFileItem,
			final HiddenItem uriItem) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFileItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return nomeFileItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return uriItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileItem.fireEvent(lChangedEventDisplay);
		uriItem.fireEvent(lChangedEventUri);
	}
	
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, tipoCaricamentoSelect, "companyId", new String[]{"descrizioneCompanyId"}, "key");
		super.editRecord(record);					
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
}
