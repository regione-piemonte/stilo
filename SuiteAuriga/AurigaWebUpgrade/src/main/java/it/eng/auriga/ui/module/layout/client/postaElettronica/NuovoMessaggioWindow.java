/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.AttachmentReplicableItem;
import it.eng.auriga.ui.module.layout.client.invioMail.InvioMailLayoutNew;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class NuovoMessaggioWindow extends PostaElettronicaWindow {

	protected NuovoMessaggioWindow _window;

	private InvioMailLayoutNew _layout;

	protected GWTRestDataSource modelliDS;
	protected GWTRestDataSource firmeDS;

	protected ToolStrip mainToolStrip;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected SelectItem firmaInCalceSelectItem;
	protected ListGrid firmaInCalcePickListProperties;
	protected ToolStripButton firmaPredefinitaButton;

	protected SaveModelloWindow saveModelloWindow;
	protected DetailToolStripButton salvaComeModelloButton;

	protected FileUploadItemWithFirmaAndMimeType uploadFileItem;
	protected ListGridRecord recordModelloFocused;

	public NuovoMessaggioWindow(String nomeEntita,String tipoRel, DSCallback callback) {
		super(nomeEntita, true, tipoRel, null, null, false, null, null, null, callback);

		_window = this;
	}

	public NuovoMessaggioWindow(String nomeEntita,String tipoRel, CustomLayout customLayoutToDoSearch, DSCallback callback) {
		super(nomeEntita, true, tipoRel, null, null, false, null, null, null, customLayoutToDoSearch, callback);

		_window = this;
	}

	public NuovoMessaggioWindow(String nomeEntita,String tipoRel, CustomDetail customDetailToReload, DSCallback callback) {
		super(nomeEntita, true, tipoRel, null, null, false, null, null, null, customDetailToReload, callback);

		_window = this;
	}

	@Override
	public String getTitleWindow() {
		return I18NUtil.getMessages().posta_elettronica_nuovo_mess_window();
	}

	@Override
	public String getIconWindow() {
		return "mail/PEO.png";
	}

	@Override
	protected VLayout createMainLayout() {

		VLayout mainLayout = super.createMainLayout();

		createMainToolstrip();

		createSalvaComeModelloButton();

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(confermaButton);
		detailToolStrip.addButton(salvaBozzaButton);
		detailToolStrip.addButton(salvaComeModelloButton);

		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();

		mainLayout.addMember(mainToolStrip, 0);
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;
	}

	private void createSalvaComeModelloButton() {
		// Creo e setto le impostazioni per il pulsante che dovrà salvare il modello
		salvaComeModelloButton = new DetailToolStripButton(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(),
				"buttons/template_save.png");
		salvaComeModelloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
		});
	}

	private void createMainToolstrip() {

		// Creo la select relativa ai modelli
		createModelliSelectItem();

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			// Creo la select relativa alla firma in calce
			createSelectFirmaInCalce();
		}

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
		
		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			
			mainToolStrip.addFormItem(firmaInCalceSelectItem);
			
			mainToolStrip.addButton(firmaPredefinitaButton);
		}
		
		TitleItem uploadFileTitleItem = new TitleItem("Inserisci un' immagine");
		uploadFileTitleItem.setColSpan(1);

		uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				afterUploadImmagineFirmaEmailHtml(uri, displayFileName);
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
				if (info.isFirmato() || info.getMimetype() == null || !info.getMimetype().toLowerCase().startsWith("image/")) {
					GWTRestDataSource.printMessage(new MessageBean("Il file non è un' immagine", "", MessageType.ERROR));
				}
			}
		});
		uploadFileItem.setColSpan(1);
		uploadFileItem.setWidth(16);		
		
		mainToolStrip.addSpacer(50);
		mainToolStrip.addFormItem(uploadFileTitleItem);
		mainToolStrip.addFormItem(uploadFileItem);		
	}
	
	public void afterUploadImmagineFirmaEmailHtml(String uri, String display) {
		
		if(_layout != null && _layout.getForm() != null){
			if(_layout.getForm().getStyleText().equals("text")){
				//Si sta considerando la modalità text				
				Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));				
			}else{
				// Prelevo i valori del form
				Map formValues = getMapValues();
				GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
				// Pongo un extraparam per l'immagine da aggiungere al corpo
				corpoMailDataSource.extraparam.put("image", "<img src=\"" + display + ":" + uri + "\" />");
				corpoMailDataSource.performCustomOperation("aggiungiImmagine", new Record(formValues), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							editNewRecord(record.toMap());
						}
					}
				}, new DSRequest());
			}
		}
	}

	@Override
	public VLayout getLayoutDatiWindow() {
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		buildNuovoMessaggio();

		_layout.setHeight100();
		_layout.setWidth100();

		return _layout;
	}
	
	public Record getInitialRecordNuovoMessaggio() {		
		return new Record();
	}

	private void buildNuovoMessaggio() {

		_layout = new InvioMailLayoutNew(this, nomeEntita, vm, null) {
			
			@Override
			public boolean getFormFlgSalvaInviatiDefaultValue() {				
				return getDefaultSaveSentEmail();
			}
		};

		final Record lRecordNuovoMessaggio = getInitialRecordNuovoMessaggio();

		if (AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")) {
			// Se sono nella modalità per cui devo visualizzare le firme in calce

			GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
			// Pongo un extraparam per il valore della nuova firma
			corpoMailDataSource.extraparam.put("newSignature", AurigaLayout.getHtmlSignature("nuovo"));
			corpoMailDataSource.performCustomOperation("addSignature", lRecordNuovoMessaggio, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];

						lRecordNuovoMessaggio.setAttribute("bodyHtml", record.getAttribute("bodyHtml"));

						_layout.getForm().editNewRecord(lRecordNuovoMessaggio.toMap());
						_layout.getForm().clearErrors(true);
						
						//Imposto il valore della select con quella che è stata pre-impostata come automatica
						if(AurigaLayout.getFirmaEmailAutoNuova() != null && !AurigaLayout.getFirmaEmailAutoNuova().equals("")){
							firmaInCalceSelectItem.setValue(AurigaLayout.getFirmaEmailAutoNuova());
						}
						
						show();
					}
				}
			}, new DSRequest());
		} else {
			if (AurigaLayout.getFirmaEmailHtml() != null) {
				lRecordNuovoMessaggio.setAttribute("bodyHtml", "<br/><br/>" + AurigaLayout.getFirmaEmailHtml());
			} else {
				lRecordNuovoMessaggio.setAttribute("bodyHtml", "");
			}

			_layout.getForm().editNewRecord(lRecordNuovoMessaggio.toMap());
			_layout.getForm().clearErrors(true);
			show();
		}

	}
	
	public boolean getDefaultSaveSentEmail() {
		return AurigaLayout.getParametroDBAsBoolean("DEFAULT_SAVE_SENT_EMAIL");
	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
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
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);

		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		// Per accessibilità. se modelliRemoveField.setIgnoreKeyboardClicks(false);, alla pressione della freccetta giù, parte automaticamente l'action ( cancella il record )
		modelliRemoveField.setIgnoreKeyboardClicks(true);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		});
		//modelliRemoveField.setIsRemoveField(true);

		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);
		
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				deleteModello(event.getRecord());				
			}   
		});

		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");

		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);

		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
		/*
		 * Se presente la seguente riga viene inserita una x a destra per cancellare il record
		 */
		//modelliPickListProperties.setCanRemoveRecords(true);
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				loadModello(event.getRecord(), event.getColNum());
			}
		});

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			modelliPickListProperties.addSelectionChangedHandler(new SelectionChangedHandler() {
				
				@Override
				public void onSelectionChanged(SelectionEvent event) {
					// TODO Auto-generated method stub
					recordModelloFocused = event.getRecord();
				}
			});
			
			modelliPickListProperties.addBodyKeyPressHandler(new BodyKeyPressHandler() {
				
				@Override
				public void onBodyKeyPress(BodyKeyPressEvent event) {
					if (EventHandler.getKey().equalsIgnoreCase("Enter")) {
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								ListGridRecord selectedRecord = modelliSelectItem.getSelectedRecord();
								loadModello(selectedRecord, 1);
							}
						});
					} else if (EventHandler.getKey().equalsIgnoreCase("Delete")) {
						Layout.showConfirmDialogWithWarning("Attenzione!", I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), "Ok", "Annulla", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if (recordModelloFocused != null) {
										deleteModello(recordModelloFocused);
									}
								} else {
									Layout.addMessage(
											new MessageBean("Cancellazione interrotta dall'utente", "", MessageType.INFO));
								}
							}
						});
					}
				}
			});		
		}
		
		modelliSelectItem.setPickListProperties(modelliPickListProperties);

		createSaveModelloWindow(nomeEntita);
	}
	
	private void deleteModello (final ListGridRecord record) {
		if (isAbilToRemoveModello(record)) {				
			SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if(value) {
						final String key = record.getAttribute("key");
						modelliDS.removeData(record, new DSCallback() {

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
	
	private void loadModello(ListGridRecord record, int numCol) {
		boolean isRemoveField = isAbilToRemoveModello(record) && numCol == 0;
		if(!isRemoveField) {
			String userid = (String) record.getAttribute("userid");
			String prefName = (String) record.getAttribute("prefName");
			if (prefName != null && !"".equals(prefName)) {
				AdvancedCriteria criteria = new AdvancedCriteria();
				criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
				criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
				if (userid.startsWith("UO.")) {
					String idUo = (String) record.getAttribute("idUo");
					criteria.addCriteria("idUo", idUo);
				}
				//Carico i dati relativi al modello selezionato
				modelliDS.fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (data.length != 0) {
							Record record = data[0];
							final Record values = new Record(JSON.decode(record.getAttribute("value")));													
							final String mittentePref = values.getAttributeAsString("mittente");
							GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
							accounts.addParam("finalita", "INVIO_NUOVO_MSG");
							accounts.fetchData(null, new DSCallback() {
								
								@Override
								public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
									if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
										RecordList result = dsResponse.getDataAsRecordList();
										Boolean isValid = false;
										for(int i=0; i < result.getLength(); i++) {
											if(result.get(i).getAttributeAsString("key").equalsIgnoreCase(mittentePref)) {
												isValid = true;
												break;
											}
										}
										if(!isValid) {
											values.setAttribute("mittente", "");
										}
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
										editNewRecord(values.toMap());
									}
								}
							});
						}
					}
				});
			}
		}
	}

	private void createSelectFirmaInCalce() {
		createFirmeDatasource();
		
		//Creo il pulsante per selezionare la firma predefinita
		firmaPredefinitaButton = new ToolStripButton("", "menu/firma_email.png");
		firmaPredefinitaButton.setPrompt(I18NUtil.getMessages().firme_in_calce_firmaPredefinita_title());
		/*
		 * L'immagine di background normalmente è già impostata.
		 * Ne setto una vuota così da non avere lo stile precedente ma per vedere in trasparenza 
		 * lo sfondo della finestra
		 */
		firmaPredefinitaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				replaceSignature();
			}
			
			private void replaceSignature() {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					} else {
						// Ottengo la firma impostata come predefinita
						final String nomeFirmaPredefinita = AurigaLayout.getFirmaEmailPredefinita();
						String firmaPredefinita = nomeFirmaPredefinita != null && !nomeFirmaPredefinita.equals("") ? AurigaLayout.getFirmaEmailHtml(nomeFirmaPredefinita) : null;
		
						if (firmaPredefinita != null && !firmaPredefinita.equals("")) {
							// Prelevo i valori del form
							Map formValues = getMapValues();
		
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							// Pongo un extraparam per il valore della nuova firma
							corpoMailDataSource.extraparam.put("newSignature", firmaPredefinita);
							corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
							corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										editNewRecord(record.toMap());
										firmaInCalceSelectItem.setValue(nomeFirmaPredefinita);
									}
								}
							}, new DSRequest());
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_firmaPredefinitaNonPresente_warningMessage(), "", MessageType.WARNING));
						}
					}
				}
			}
		});
		
		// Creo la select
		firmaInCalceSelectItem = new SelectItem("firmaInCalce");
		firmaInCalceSelectItem.setValueField("prefName");
		firmaInCalceSelectItem.setDisplayField("prefName");

		firmaInCalceSelectItem.setTitle("<b>" + I18NUtil.getMessages().firme_in_calce_modelliSelectItem_title() + "</b>");
		firmaInCalceSelectItem.setWrapTitle(false); // In questo modo il titolo non viene mandato a capo
		firmaInCalceSelectItem.setCachePickListResults(false);
		firmaInCalceSelectItem.setRedrawOnChange(true);
		firmaInCalceSelectItem.setShowOptionsFromDataSource(true);
		firmaInCalceSelectItem.setOptionDataSource(firmeDS);
		firmaInCalceSelectItem.setAllowEmptyValue(true);

		firmaInCalcePickListProperties = new ListGrid();
		firmaInCalcePickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		firmaInCalcePickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		firmaInCalcePickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text					
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));					
					} else {
						final String preferenceName = event.getRecord().getAttribute("prefName");
						if (preferenceName != null && !"".equals(preferenceName)) {
							AdvancedCriteria criteria = new AdvancedCriteria();
							criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
							firmeDS.fetchData(criteria, new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record[] data = response.getData();
									if (data.length != 0) {
										Record record = data[0];
		
										// Prelevo i valori del form
										Map formValues = getMapValues();
		
										GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
										// Pongo un extraparam per il valore della nuova firma
										corpoMailDataSource.extraparam.put("newSignature", record.getAttributeAsString("value"));
										corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
										corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record record = response.getData()[0];
		
													editNewRecord(record.toMap());
												}
											}
										}, new DSRequest());
									}
								}
							});
						}
					}
				}
			}
		});

		// Inserisco la picklist
		firmaInCalceSelectItem.setPickListProperties(firmaInCalcePickListProperties);
	}

	private void createFirmeDatasource() {
		firmeDS = UserInterfaceFactory.getPreferenceDataSource();
		firmeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
	}

	protected void createModelliDatasource(String nomeEntita) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", nomeEntita + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0");
	}

	protected void createSaveModelloWindow(String nomeEntita) {
		saveModelloWindow = new SaveModelloWindow(I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), nomeEntita,
				new SaveModelloAction(modelliDS, modelliSelectItem) {

					@Override
					public Map getMapValuesForAdd() {
						Map values = getMapValues();
						values.remove("attach");
						return values;
					}

					@Override
					public Map getMapValuesForUpdate() {
						Map values = getMapValues();
						values.remove("attach");
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
		}
	}

	private void editNewRecord(Map map) {
		_layout.getForm().filtroPresenteFromModello(map);
		vm.editRecord(new Record(map));
		_layout.getForm().verifyChangeStyle();
	}

	private Map getMapValues() {
		return vm.getValues();
	}

	@Override
	public void setFileAsAllegato(Record record) {
		AttachmentReplicableItem item = (AttachmentReplicableItem) _layout.getForm().getAttachmentReplicableItem();
		item.setFileAsAllegatoFromWindow(record);
	}

	@Override
	public void inviaMail(DSCallback callback) {
		_layout.inviaMail(callback);
	}

	@Override
	public void salvaBozza(DSCallback callback) {
		_layout.salvaBozza(callback, "N");
	}

	@Override
	protected void onDestroy() {
		if(saveModelloWindow != null) {
			saveModelloWindow.destroy();
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}
	
}