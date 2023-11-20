/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.regexp.shared.RegExp;
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
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
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
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.EditorEmailWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.SceltaDestinatarioWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public abstract class OpzioniTrasmissioneAttiPopup extends ModalWindow {
	
	private static final int COMPONENT_WIDTH = 800;
	
	protected OpzioniTrasmissioneAttiPopup _window;
	protected ValuesManager _vm;
	
	//Modelli
	protected ToolStrip mainToolStrip;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected ListGridRecord recordModelloFocused;
	protected SaveModelloWindow saveModelloWindow;
	
	protected TabSet tabSetGenerale;
	protected Tab tabDatiInvioMail;
	protected Tab tabOpzioni;
	
	protected DynamicForm formDatiInvioMail;
	private SelectItem lSelectItemMittente;
	private FormItemIcon filtroDestinatariItem;
	private ComboBoxItem lComboBoxDestinatari;
	private ImgButtonItem lookupRubricaEmailDestinatariImgButton;
	private FormItemIcon filtroDestinatariCCItem;
	private ComboBoxItem lComboBoxDestinatariCC;
	private ImgButtonItem lookupRubricaEmailDestinatariCCImgButton;
	private TextItem lTextItemOggetto;
	private RichTextItem lRichTextItemBody;
	private ImgButtonItem editorBodyMailImgButton;
	
	protected DynamicForm formOpzioni;
	protected RadioGroupItem flgInclusiPareriItem;
	protected RadioGroupItem flgInclAllegatiPIItem;
	protected RadioGroupItem flgIntXPubblRadioItem;
	protected RadioGroupItem flgInvioAttiTrasmessiItem;
	
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton inviaButton;
	protected DetailToolStripButton salvaModelloButton;
	protected DetailToolStripButton annullaButton;
	
	public OpzioniTrasmissioneAttiPopup() {
		
		super("opzioni_trasmissione_atti", true);
		
		_window = this;
		this._vm = new ValuesManager();
		
		setTitle("Opzioni di trasmissione atti");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		
		setIcon("delibere/trasmissione_atti_seduta.png");
		
		createMainToolstrip();
		
		createFormDatiInvioMail();
		
		createFormOpzioni();
		
		createTabset();
		
		insertTabs();
				
		//Per creare il layout principale
		VLayout mainLayout = createMainLayout();
		mainLayout.setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		//Preference modello DEFAULT
		populateModelloDefault();

		addMember(mainLayout);
	}
	
	private void createMainToolstrip() {

		// Creo la select relativa ai modelli
		createModelliSelectItem();

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
				return true;
			}
			
			@Override
			public boolean isTrasmissioneAtti() {
				return true;
			}
		};
	}
	
	private void deleteModello(final ListGridRecord record) {
		if (isAbilToRemoveModello(record)) {				
			SC.ask("Sei sicuro di voler eliminare il modello " + record.getAttributeAsString("prefName") + " ?",
					new BooleanCallback() {
				
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
				criteria.addCriteria("flgIncludiPubbl", "1");
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
							
							final Map oldValues = getMapValues(); //Mappa dei campi presenti nel form
							
							//Mappa dei valori del modello da inserire nel form
							final Map newValues = new Record(JSON.decode(record.getAttribute("value"))).toMap(); 	
							
							final String mittentePref = (String) newValues.get("mittente");
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
											newValues.put("mittente", "");
										}
										
										/*
										 * Creo un record con i valori dei campi presenti nel form.
										 * Nel caso questi stessi campi siano presenti nel modello verranno
										 * modificati dal metodo successivo 
										 */
										final Record values = new Record(oldValues); 
										
										setNewValuesFromModel(values, oldValues, newValues);
									}
								}
							});
						}
					}
				});
			}
		}
	}
	
	private void setNewValuesFromModel(final Record values, final Map oldValues, final Map newValues) {
		
		//Mittente
		if(newValues.get("mittente") != null && !"".equals(newValues.get("mittente"))){
			values.setAttribute("mittente", newValues.get("mittente"));
		} else {
			values.setAttribute("mittente", oldValues.get("mittente"));
		}
		
		//Destinatari
		if(newValues.get("destinatari") != null && !"".equals(newValues.get("destinatari"))){
			values.setAttribute("destinatari", newValues.get("destinatari"));
		} else{
			values.setAttribute("destinatari", oldValues.get("destinatari"));
		}
		
		// Filtro destinatari principali
		if(newValues.get("recordFiltriDestinatari") != null) {
			values.setAttribute("recordFiltriDestinatari", newValues.get("recordFiltriDestinatari"));
		} else {
			values.setAttribute("recordFiltriDestinatari", oldValues.get("recordFiltriDestinatari"));
		}
		
		//Destinatari CC
		if(newValues.get("destinatariCC") != null && !"".equals(newValues.get("destinatariCC"))){
			values.setAttribute("destinatariCC", newValues.get("destinatariCC"));
		} else {
			values.setAttribute("destinatariCC", oldValues.get("destinatariCC"));
		}
		
		// Filtro destinatari principali CC
		if(newValues.get("recordFiltriDestinatariCC") != null) {
			values.setAttribute("recordFiltriDestinatariCC", newValues.get("recordFiltriDestinatariCC"));
		} else {
			values.setAttribute("recordFiltriDestinatariCC", oldValues.get("recordFiltriDestinatariCC"));
		}
		
		//Oggetto della mail
		if(newValues.get("oggetto") != null && !"".equals(newValues.get("oggetto"))){
			values.setAttribute("oggetto", newValues.get("oggetto"));
		} else {
			values.setAttribute("oggetto", oldValues.get("oggetto"));
		}
		
		//Body della mail
		if(newValues.get("body") != null && !"".equals(newValues.get("body"))){
			values.setAttribute("body", newValues.get("body"));
		} else {
			values.setAttribute("body", oldValues.get("body"));
		}
		
		//InclusiPareri
		if(newValues.get("flgInclusiPareri") != null && !"".equals(newValues.get("flgInclusiPareri"))){
			values.setAttribute("flgInclusiPareri", newValues.get("flgInclusiPareri"));
		} else {
			values.setAttribute("flgInclusiPareri", oldValues.get("flgInclusiPareri"));
		}
		
		//InclAllegatiPI
		if(newValues.get("flgInclAllegatiPI") != null && !"".equals(newValues.get("flgInclAllegatiPI"))){
			values.setAttribute("flgInclAllegatiPI", newValues.get("flgInclAllegatiPI"));
		} else {
			values.setAttribute("flgInclAllegatiPI", oldValues.get("flgInclAllegatiPI"));
		}
		
		//IntXPubbl
		if(newValues.get("flgIntXPubbl") != null && !"".equals(newValues.get("flgIntXPubbl"))){
			values.setAttribute("flgIntXPubbl", newValues.get("flgIntXPubbl"));
		} else {
			values.setAttribute("flgIntXPubbl", oldValues.get("flgIntXPubbl"));
		}
		
		//InvioAttiTrasmessi
		if(newValues.get("flgInvioAttiTrasmessi") != null && !"".equals(newValues.get("flgInvioAttiTrasmessi"))){
			values.setAttribute("flgInvioAttiTrasmessi", newValues.get("flgInvioAttiTrasmessi"));
		} else {
			values.setAttribute("flgInvioAttiTrasmessi", oldValues.get("flgInvioAttiTrasmessi"));
		}
		
		//Inserisco i nuovi valori all'interno dei rispettivi campi
		editNewRecord(values.toMap());	
	}
	
	private Map getMapValues() {
		return _vm.getValues();
	}
	
	private void editNewRecord(Map map) {
		
		_vm.editRecord(new Record(map));
		for(DynamicForm form : _vm.getMembers()) {
			form.markForRedraw();
		}
	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}
	
	protected void createModelliDatasource(String nomeEntita) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", "invioAttiSeduta" + getOrganoCollegiale());
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0"); //TODO RIMUOVERE?
	}
	
	protected void createTabset() throws IllegalStateException {
		tabSetGenerale = new TabSet();
		tabSetGenerale.setTabBarPosition(Side.TOP);
		tabSetGenerale.setTabBarAlign(Side.LEFT);
		tabSetGenerale.setWidth100();
		tabSetGenerale.setBorder("0px");
		tabSetGenerale.setCanFocus(false);
		tabSetGenerale.setTabIndex(-1);
		tabSetGenerale.setPaneMargin(0);
	}
	
	protected void insertTabs() throws IllegalStateException {

		createWindow();

		tabSetGenerale.addTab(tabDatiInvioMail);
		tabSetGenerale.addTab(tabOpzioni);
	}
	
	protected void createWindow() {

		tabDatiInvioMail = new Tab("<b>" + "Dati invio e-mail" + "</b>");
		tabDatiInvioMail.setPrompt("Dati invio e-mail");

		VLayout layoutDatiInvioMail = getLayoutDatiInvioMail();
		layoutDatiInvioMail.setTabIndex(-1);
		layoutDatiInvioMail.setCanFocus(false);
		
		VLayout createTabPaneDatiInvioMail = createTabPane(layoutDatiInvioMail);
		createTabPaneDatiInvioMail.setTabIndex(-1);
		createTabPaneDatiInvioMail.setCanFocus(false);
		tabDatiInvioMail.setPane(createTabPaneDatiInvioMail);
		
		tabOpzioni = new Tab("<b>" + "File da trasmettere per atto e altre opzioni" + "</b>");
		tabOpzioni.setPrompt("File da trasmettere per atto e altre opzioni");
		
		VLayout layoutOpzioni = getLayoutOpzioni();
		layoutOpzioni.setTabIndex(-1);
		layoutOpzioni.setCanFocus(false);
		
		VLayout createTabPaneOpzioni = createTabPane(layoutOpzioni);
		createTabPaneOpzioni.setTabIndex(-1);
		createTabPaneOpzioni.setCanFocus(false);
		tabOpzioni.setPane(createTabPaneOpzioni);
	}
	
	/**
	 * Metodo per costruire il pane associato ad un tab generico
	 * 
	 */
	protected VLayout createTabPane(VLayout layout) {

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		VLayout layoutTab = new VLayout();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			spacerLayout.setTabIndex(-1);
			spacerLayout.setCanFocus(false);
			layoutTab.setTabIndex(-1);
			layoutTab.setCanFocus(false);				
		}
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
	
	public VLayout getLayoutDatiInvioMail() {
		
		VLayout layoutDatiInvioMail = new VLayout(5);
				
		layoutDatiInvioMail.addMember(formDatiInvioMail);
		
		return layoutDatiInvioMail;
	}
	
	public VLayout getLayoutOpzioni() {
		
		VLayout layoutDatiOpzioni = new VLayout(5);
		
		layoutDatiOpzioni.addMember(formOpzioni);
		
		return layoutDatiOpzioni;
	}
	
	protected VLayout createMainLayout() {

		createDetailToolstrip();

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(mainToolStrip, 0);
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;
	}
	
	private void createDetailToolstrip() {
		inviaButton = new DetailToolStripButton("Invia", "buttons/send.png");
		inviaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				onClickOkButton(new DSCallback() {	
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						_window.markForDestroy();
					}
				});	
			}
		});
		
		salvaModelloButton = new DetailToolStripButton("Salva come modello", "buttons/template_save.png");
		salvaModelloButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
					saveModelloWindow.clearValues();
					saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
					saveModelloWindow.redrawForms();
					saveModelloWindow.redraw();
					saveModelloWindow.show();
				}
			}
		});

		annullaButton = new DetailToolStripButton("Annulla", "annulla.png");
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.markForDestroy();	
			}
		});

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(inviaButton);
		detailToolStrip.addButton(salvaModelloButton);
		detailToolStrip.addButton(annullaButton);
	}
	
	private void createFormDatiInvioMail() {
		
		formDatiInvioMail = new DynamicForm();													
		formDatiInvioMail.setKeepInParentRect(true);
		formDatiInvioMail.setWidth100();
		formDatiInvioMail.setHeight100();
		formDatiInvioMail.setNumCols(8);
		formDatiInvioMail.setColWidths(1,1,1,1,1,1,1,"*");		
		formDatiInvioMail.setCellPadding(5);
		formDatiInvioMail.setWrapItemTitles(false);	
		formDatiInvioMail.setValuesManager(_vm);
		
		// MITTENTE
		createMittente(); 
		
		// Creazione filtro destinatari
		creaFiltroDestinatariPrimari();
		
		//Creazione destinatari primari
		createDestinaratiPrimari(getValidatorEmail());
		
		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario 
		createLookupRubricaEmailDestinatari();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCC();
				
		//Creazione destinatari in CC
		createDestinaratiCC(getValidatorEmail());
				
		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario CC
		createLookupRubricaEmailDestinatariCC();
		
		// Oggetto
		createOggetto();

		// Casella testo HTML
		createRichTextArea();
				
		//Bottone per editare il corpo email
		createBodyEmailButton();
		
		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setStartRow(true);
		lSpacerItem.setColSpan(1);
		
		formDatiInvioMail.setFields(new FormItem[]{lSelectItemMittente,lComboBoxDestinatari,lookupRubricaEmailDestinatariImgButton,
				lComboBoxDestinatariCC,lookupRubricaEmailDestinatariCCImgButton,
				lTextItemOggetto,
				lSpacerItem,lRichTextItemBody,editorBodyMailImgButton
		});
	}

	private void createMittente() {
		lSelectItemMittente = new SelectItem("mittente", I18NUtil.getMessages().invionotificainteropform_mittenteItem_title());
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setRequired(true);
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("finalita", "INVIO_NUOVO_MSG");
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.setColSpan(5);
		lSelectItemMittente.setWidth(COMPONENT_WIDTH);
	}
	
	private void createDestinaratiPrimari(CustomValidator validator){
		
		GWTRestDataSource proposteDestinatariDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatari = new ComboBoxItem("destinatari", I18NUtil.getMessages().invionotificainteropform_destinatariItem_title());
		lComboBoxDestinatari.setValueField("indirizzoEmail");
		lComboBoxDestinatari.setDisplayField("indirizzoEmail");
		lComboBoxDestinatari.setShowPickerIcon(false);
		lComboBoxDestinatari.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatari.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatari.setColSpan(5);
		lComboBoxDestinatari.setEndRow(false);
		lComboBoxDestinatari.setStartRow(true);
		lComboBoxDestinatari.setValidators(validator);
		lComboBoxDestinatari.setAutoFetchData(false);
		lComboBoxDestinatari.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatari.setAddUnknownValues(true);
		lComboBoxDestinatari.setValidateOnChange(false);
		lComboBoxDestinatari.setOptionDataSource(proposteDestinatariDS);
		lComboBoxDestinatari.setFetchDelay(500);
		lComboBoxDestinatari.setIcons(filtroDestinatariItem);
		lComboBoxDestinatari.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				return StringUtil.asHTML((String) lComboBoxDestinatari.getValue());
			}
		});
		lComboBoxDestinatari.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				lComboBoxDestinatari.validate();
			}
		});
		
		ListGridField destinatariPrefNameField = new ListGridField("nomeContatto");
		destinatariPrefNameField.setWidth("100%");
		destinatariPrefNameField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record != null) {
					String res = null;
					String nomeContatto = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
					if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
						res = buildIconHtml("coarda.png", nomeContatto);
						return res;
					} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
						res = buildIconHtml("mail/PEO.png", nomeContatto);
						return res;
					} else {
						res = buildIconHtml("mail/mailingList.png", nomeContatto);
						return res;
					}
				}
				return null;
			}
		});
		lComboBoxDestinatari.setPickListFields(destinatariPrefNameField);
		
		ListGrid destinatariPickListProperties = new ListGrid();
		destinatariPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		destinatariPickListProperties.setShowHeader(false);
		destinatariPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				event.cancel();
				selezionaDestinatarioSecondario(event.getRecord(),true);
			}
		});
		destinatariPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String nome = lComboBoxDestinatari != null && lComboBoxDestinatari.getValue() != null ? (String) lComboBoxDestinatari.getValue() : null;
				GWTRestDataSource lComboBoxDestinatariDS = (GWTRestDataSource) lComboBoxDestinatari.getOptionDataSource();
				lComboBoxDestinatariDS.addParam("destinatario", nome);
				
				populateFiltriAutoCompletamento(lComboBoxDestinatariDS, formDatiInvioMail.getValuesAsRecord().getAttributeAsRecord("recordFiltriDestinatari"));
				
				lComboBoxDestinatari.setOptionDataSource(lComboBoxDestinatariDS);
				lComboBoxDestinatari.invalidateDisplayValueCache();
			}
		});
		lComboBoxDestinatari.setPickListProperties(destinatariPickListProperties);
	}
	
	private void creaFiltroDestinatariPrimari() {
		
		filtroDestinatariItem = new FormItemIcon();
		filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_off.png");  
		filtroDestinatariItem.setWidth(16);  
		filtroDestinatariItem.setHeight(16);  
		filtroDestinatariItem.setInline(true);  
		filtroDestinatariItem.setName("filtroDestinatari");
		filtroDestinatariItem.setPrompt("Filtro sulla scelta destinatari");
		filtroDestinatariItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent  event) {
				new SceltaDestinatarioWindow("Filtro sulla scelta destinatari",new Record(_vm.getValues()).getAttributeAsRecord("recordFiltriDestinatari")) {
					
					@Override
					public void salvaFiltri(Record record) {
						
						if(filtroPresente(record)) {
							filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_on.png");
						} else {
							filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_off.png");
						}
						
						formDatiInvioMail.setValue("recordFiltriDestinatari", record);
						_window.markForRedraw();
					}
				};
			}
		});
	}
	
	private void createLookupRubricaEmailDestinatari() {
		
		lookupRubricaEmailDestinatariImgButton = new ImgButtonItem("lookupRubricaEmailDestinatari", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariImgButton.setShowTitle(false);
		lookupRubricaEmailDestinatariImgButton.setAlwaysEnabled(true);
		lookupRubricaEmailDestinatariImgButton.setWidth(16);
		lookupRubricaEmailDestinatariImgButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariImgButton.setStartRow(false);
		lookupRubricaEmailDestinatariImgButton.setEndRow(true);
		lookupRubricaEmailDestinatariImgButton.setColSpan(1);
		lookupRubricaEmailDestinatariImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup("destinatari");
				lookupRubricaEmailPopup.show();
			}
		});
	}
	
    private void creaFiltroDestinatariCC() {
		
		filtroDestinatariCCItem = new FormItemIcon();
		filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_off.png");  
		filtroDestinatariCCItem.setWidth(16);  
		filtroDestinatariCCItem.setHeight(16);  
		filtroDestinatariCCItem.setInline(true);  
		filtroDestinatariCCItem.setName("filtroDestinatariCC");
		filtroDestinatariCCItem.setPrompt("Filtro sulla scelta destinatari");
		filtroDestinatariCCItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent  event) {
				new SceltaDestinatarioWindow("Filtro sulla scelta destinatari",new Record(_vm.getValues()).getAttributeAsRecord("recordFiltriDestinatariCC")) {
					
					@Override
					public void salvaFiltri(Record record) {
						
						if(filtroPresente(record)) {
							filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_on.png");
						} else {
							filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_off.png");
						}
						
						formDatiInvioMail.setValue("recordFiltriDestinatariCC", record);
						_window.markForRedraw();
					}
				};
			}
		});
	}
	
	private void createDestinaratiCC(CustomValidator validator){
		
		GWTRestDataSource proposteDestinatariCCDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatariCC = new ComboBoxItem("destinatariCC", I18NUtil.getMessages().invionotificainteropform_destinatariCCItem_title());
		lComboBoxDestinatariCC.setValueField("indirizzoEmail");
		lComboBoxDestinatariCC.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCC.setShowPickerIcon(false);
		lComboBoxDestinatariCC.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCC.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCC.setColSpan(5);
		lComboBoxDestinatariCC.setEndRow(false);
		lComboBoxDestinatariCC.setStartRow(true);
		lComboBoxDestinatariCC.setValidators(validator);
		lComboBoxDestinatariCC.setAutoFetchData(false);
		lComboBoxDestinatariCC.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCC.setAddUnknownValues(true);
		lComboBoxDestinatariCC.setValidateOnChange(false);
		lComboBoxDestinatariCC.setOptionDataSource(proposteDestinatariCCDS);
		lComboBoxDestinatariCC.setFetchDelay(500);
		lComboBoxDestinatariCC.setIcons(filtroDestinatariCCItem);
		lComboBoxDestinatariCC.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				return StringUtil.asHTML((String) lComboBoxDestinatariCC.getValue());
			}
		});
		lComboBoxDestinatariCC.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				lComboBoxDestinatariCC.validate();
			}
		});
		
		ListGridField destinatariPrefNameField = new ListGridField("nomeContatto");
		destinatariPrefNameField.setWidth("100%");
		destinatariPrefNameField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record != null) {
					String res = null;
					String nomeContatto = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
					if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
						res = buildIconHtml("coccarda.png", nomeContatto);
						return res;
					} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
						res = buildIconHtml("mail/PEO.png", nomeContatto);
						return res;
					} else {
						res = buildIconHtml("mail/mailingList.png", nomeContatto);
						return res;
					}
				}
				return null;
			}
		});
		lComboBoxDestinatariCC.setPickListFields(destinatariPrefNameField);
		
		ListGrid destinatariPickListProperties = new ListGrid();
		destinatariPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		destinatariPickListProperties.setShowHeader(false);
		destinatariPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				event.cancel();
				selezionaDestinatarioSecondario(event.getRecord(),true);
			}
		});
		destinatariPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String nome = lComboBoxDestinatariCC != null && lComboBoxDestinatariCC.getValue() != null ? (String) lComboBoxDestinatariCC.getValue() : null;
				GWTRestDataSource lComboBoxDestinatariCCDS = (GWTRestDataSource) lComboBoxDestinatariCC.getOptionDataSource();
				lComboBoxDestinatariCCDS.addParam("destinatario", nome);
				
				populateFiltriAutoCompletamento(lComboBoxDestinatariCCDS, formDatiInvioMail.getValuesAsRecord().getAttributeAsRecord("recordFiltriDestinatariCC"));
				
				lComboBoxDestinatariCC.setOptionDataSource(lComboBoxDestinatariCCDS);
				lComboBoxDestinatariCC.invalidateDisplayValueCache();
			}
		});
		lComboBoxDestinatariCC.setPickListProperties(destinatariPickListProperties);
	}
	
	private void createLookupRubricaEmailDestinatariCC() {
		
		lookupRubricaEmailDestinatariCCImgButton = new ImgButtonItem("lookupRubricaEmailDestinatariCC", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCImgButton.setShowTitle(false);
		lookupRubricaEmailDestinatariCCImgButton.setAlwaysEnabled(true);
		lookupRubricaEmailDestinatariCCImgButton.setWidth(16);
		lookupRubricaEmailDestinatariCCImgButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariCCImgButton.setStartRow(false);
		lookupRubricaEmailDestinatariCCImgButton.setEndRow(true);
		lookupRubricaEmailDestinatariCCImgButton.setColSpan(1);
		lookupRubricaEmailDestinatariCCImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup(
						"destinatariCC");
				lookupRubricaEmailPopup.show();
			}
		});
	}
	
	private void createOggetto() {
		lTextItemOggetto = new TextItem("oggetto", I18NUtil.getMessages().invionotificainteropform_oggettoItem_title());
		lTextItemOggetto.setWidth(COMPONENT_WIDTH);
		lTextItemOggetto.setColSpan(8);
		lTextItemOggetto.setStartRow(true);
		lTextItemOggetto.setEndRow(true);
	}
	
	private void createRichTextArea() {
		
		lRichTextItemBody = new RichTextItem("body");
		lRichTextItemBody.setDefaultValue("");
		lRichTextItemBody.setVisible(true);
		lRichTextItemBody.setShowTitle(false);
		lRichTextItemBody.setHeight(600);
		lRichTextItemBody.setWidth(COMPONENT_WIDTH);
		lRichTextItemBody.setColSpan(5);
		lRichTextItemBody.setStartRow(false);
		lRichTextItemBody.setEndRow(false);
		lRichTextItemBody.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
	}
	
	private void createBodyEmailButton() {
		
		editorBodyMailImgButton = new ImgButtonItem("editorBodyMail", "buttons/view_editor.png", I18NUtil.getMessages().posta_elettronica_editor_view_title_new_message());
		editorBodyMailImgButton.setShowTitle(false);
		editorBodyMailImgButton.setAlwaysEnabled(true);
		editorBodyMailImgButton.setWidth(10);
		editorBodyMailImgButton.setValueIconSize(32);
		editorBodyMailImgButton.setStartRow(false);
		editorBodyMailImgButton.setEndRow(true);
		editorBodyMailImgButton.setColSpan(1);
		editorBodyMailImgButton.setPrompt("Editor corpo email");
		editorBodyMailImgButton.setVAlign(VerticalAlignment.TOP);
		editorBodyMailImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageEditViewerBody();
			}
		});
	}
	
	private void manageEditViewerBody(){
		String body = (String) lRichTextItemBody.getValue();
		final EditorEmailWindow editorEmailWindow = new EditorEmailWindow(I18NUtil.getMessages().posta_elettronica_editor_view_title_new_message(),body){
			
			@Override
			public void manageOnCloseClick() {
				String body = getCurrentBody();
				lRichTextItemBody.setValue(body);
				markForDestroy();
			};
		};
		editorEmailWindow.show();
	}
	
	private void createFormOpzioni() {
		
		formOpzioni = new DynamicForm();													
		formOpzioni.setKeepInParentRect(true);
		formOpzioni.setWidth100();
		formOpzioni.setHeight100();
		formOpzioni.setNumCols(8);
		formOpzioni.setColWidths(1,1,1,1,1,1,1,"*");		
		formOpzioni.setCellPadding(5);
		formOpzioni.setWrapItemTitles(false);		
		formOpzioni.setValuesManager(_vm);
		
		flgInclusiPareriItem = new RadioGroupItem("flgInclusiPareri");
		LinkedHashMap<String, String> flgInclusiPareriValueMap = new LinkedHashMap<String, String>();
		flgInclusiPareriValueMap.put("SI", "SI");
		flgInclusiPareriValueMap.put("NO", "NO");
		flgInclusiPareriItem.setValueMap(flgInclusiPareriValueMap);
		flgInclusiPareriItem.setDefaultValue("SI");
		flgInclusiPareriItem.setVertical(false);
		flgInclusiPareriItem.setWrap(false);
		flgInclusiPareriItem.setTitle("Includi pareri");
		flgInclusiPareriItem.setShowTitle(true);
		flgInclusiPareriItem.setWidth(120);
		flgInclusiPareriItem.setColSpan(5);
		flgInclusiPareriItem.setStartRow(true);
		
		flgInclAllegatiPIItem = new RadioGroupItem("flgInclAllegatiPI");
		LinkedHashMap<String, String> flgInclAllegatiPIValueMap = new LinkedHashMap<String, String>();
		flgInclAllegatiPIValueMap.put("SEPARATI", "SI");
		flgInclAllegatiPIValueMap.put("NO", "NO");
		flgInclAllegatiPIItem.setValueMap(flgInclAllegatiPIValueMap);
		flgInclAllegatiPIItem.setStartRow(true);
		flgInclAllegatiPIItem.setDefaultValue("NO");
		flgInclAllegatiPIItem.setVertical(false);
		flgInclAllegatiPIItem.setWrap(false);
		flgInclAllegatiPIItem.setTitle("Includi allegati parte int. separati");
		flgInclAllegatiPIItem.setShowTitle(true);
		flgInclAllegatiPIItem.setWidth(120);
		flgInclAllegatiPIItem.setColSpan(5);
		flgInclAllegatiPIItem.setStartRow(true);
		
		
		flgIntXPubblRadioItem = new RadioGroupItem("flgIntXPubbl");
		LinkedHashMap<String, String> flgIntXPubblValueMap = new LinkedHashMap<String, String>();
		flgIntXPubblValueMap.put("INT", "integrale");
		flgIntXPubblValueMap.put("PUBBL", "per pubblicazione (con eventuali omissis)");
		flgIntXPubblValueMap.put("entrambe", "entrambe");
		flgIntXPubblRadioItem.setValueMap(flgIntXPubblValueMap);
		flgIntXPubblRadioItem.setDefaultValue("INT");
		flgIntXPubblRadioItem.setVertical(false);
		flgIntXPubblRadioItem.setWrap(false);
		flgIntXPubblRadioItem.setTitle("Versione da scaricare");
		flgIntXPubblRadioItem.setShowTitle(true);
		flgIntXPubblRadioItem.setWidth(120);
		flgIntXPubblRadioItem.setColSpan(5);
		flgIntXPubblRadioItem.setStartRow(true);
		
		flgInvioAttiTrasmessiItem = new RadioGroupItem("flgInvioAttiTrasmessi");
		LinkedHashMap<String, String> flgInvioAttiTrasmessiValueMap = new LinkedHashMap<String, String>();
		flgInvioAttiTrasmessiValueMap.put("NO", "NO");
		flgInvioAttiTrasmessiValueMap.put("SI_SOLO_VARIATI", "SI, solo se variati/integrati");
		flgInvioAttiTrasmessiValueMap.put("SI", "SI sempre");
		flgInvioAttiTrasmessiItem.setValueMap(flgInvioAttiTrasmessiValueMap);
		flgInvioAttiTrasmessiItem.setDefaultValue("NO");
		flgInvioAttiTrasmessiItem.setVertical(false);
		flgInvioAttiTrasmessiItem.setWrap(false);
		flgInvioAttiTrasmessiItem.setTitle("Invio atti già trasmessi");
		flgInvioAttiTrasmessiItem.setShowTitle(true);
		flgInvioAttiTrasmessiItem.setWidth(120);
		flgInvioAttiTrasmessiItem.setColSpan(5);
		flgInvioAttiTrasmessiItem.setStartRow(true);
		
		formOpzioni.setFields(new FormItem[]{flgInclusiPareriItem, flgInclAllegatiPIItem, flgIntXPubblRadioItem, flgInvioAttiTrasmessiItem });
	}
	
	
	public void selezionaDestinatarioSecondario(Record record,Boolean is) {
		if(is){	
			lComboBoxDestinatari.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		} else {
			lComboBoxDestinatariCC.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		}
		_window.markForRedraw();
	}
	
	private void populateFiltriAutoCompletamento(GWTRestDataSource gWTRestDataSource, Record record) {
		
		String tipoIndirizzo = "";
		String tipoDestinatario = "";
		String idSoggetto = "";
		if(record != null) {
			tipoIndirizzo = record.getAttributeAsString("tipoIndirizzo") != null ?
					record.getAttributeAsString("tipoIndirizzo") : "";
			tipoDestinatario = record.getAttributeAsString("tipoDestinatario") != null ?
					record.getAttributeAsString("tipoDestinatario") : "";
			if(record.getAttributeAsRecordList("listaMittenti") != null &&
					!record.getAttributeAsRecordList("listaMittenti").isEmpty()) {
				idSoggetto = record.getAttributeAsRecordList("listaMittenti").get(0).getAttribute("idSoggetto");
			}
		}
		gWTRestDataSource.addParam("tipoIndirizzo", tipoIndirizzo);
		gWTRestDataSource.addParam("tipoDestinatario", tipoDestinatario);
		gWTRestDataSource.addParam("idSoggetto", idSoggetto);
	}
	
	private Boolean filtroPresente(Record record) {
		Boolean verify = false;
		if(record != null) {
			if(!"ALL".equalsIgnoreCase(record.getAttribute("tipoIndirizzo"))) {
				verify = true;
			} else if(!"ALL".equalsIgnoreCase(record.getAttribute("tipoDestinatario"))) {
				verify = true;
			}
		}
		return verify;
	}
	
	/**
	 * Validator per il campo email
	 */
	private CustomValidator getValidatorEmail() {
		CustomValidator lEmailRegExpValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || value.equals(""))
					return true;
				RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
				String lString = (String) value;
				String[] list = IndirizziEmailSplitter.split(lString);
				boolean res = true;
				for(int i=0; i < list.length; i++){
					if (list[i] == null || list[i].equals(""))
						res = res && true;
					else
						res = res && regExp.test(list[i].trim());
				}
				return res;
			}
		};
		lEmailRegExpValidator.setErrorMessage(I18NUtil.getMessages().invionotificainteropform_destinatariValidatorErrorMessage());
		return lEmailRegExpValidator;
	}
	
	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
	}
	
	public class InvioMailMultiLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		private String fieldName;
		private HashMap<String, Integer> indirizzoRefCount = new HashMap<String, Integer>();

		public InvioMailMultiLookupRubricaEmailPopup(String pFieldName) {
			super(false);
			this.fieldName = pFieldName;
			String value = formDatiInvioMail.getValueAsString(fieldName);
			if (value != null && !value.equals("")) {
				StringSplitterClient st = new StringSplitterClient(value, ";");
				for (int i = 0; i < st.getTokens().length; i++) {
					String indirizzo = st.getTokens()[i];
					incrementaIndirizzoRefCount(indirizzo);
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									incrementaIndirizzoRefCount(indirizzo);
									formDatiInvioMail.setValue(fieldName, appendIndirizzoEmail(formDatiInvioMail.getValueAsString(fieldName), indirizzo));
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				incrementaIndirizzoRefCount(indirizzo);
				formDatiInvioMail.setValue(fieldName, appendIndirizzoEmail(formDatiInvioMail.getValueAsString(fieldName), indirizzo));
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									decrementaIndirizzoRefCount(indirizzo);
									if (!indirizzoRefCount.containsKey(indirizzo)) {
										formDatiInvioMail.setValue(fieldName, removeIndirizzoEmail(formDatiInvioMail.getValueAsString(fieldName), indirizzo));
									}
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				decrementaIndirizzoRefCount(indirizzo);
				if (!indirizzoRefCount.containsKey(indirizzo)) {
					formDatiInvioMail.setValue(fieldName, removeIndirizzoEmail(formDatiInvioMail.getValueAsString(fieldName), indirizzo));
				}
			}
		}

		private void incrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo)) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) + 1);
			} else {
				indirizzoRefCount.put(indirizzo, new Integer(1));
			}
		}

		private void decrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo) && indirizzoRefCount.get(indirizzo).intValue() > 1) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) - 1);
			} else {
				indirizzoRefCount.remove(indirizzo);
			}
		}
	}
	
	// Appende a str la stringa strToAppend, preceduta dal carattere ; se questo
	// non è presente alla fine di str
	public String appendIndirizzoEmail(String str, String strToAppend) {
		String res = "";
		if (str == null || str.equals("")) {
			res = strToAppend;
		} else if (strToAppend != null && !strToAppend.equals("") && !str.toLowerCase().contains(strToAppend.toLowerCase())) {
			String lastChar = str.substring(str.length() - 1);
			if (lastChar.equalsIgnoreCase(";")) {
				res = str + strToAppend;
			} else {
				res = str + ";" + strToAppend;
			}
		} else {
			res = str;
		}
		return res;
	}

	public String removeIndirizzoEmail(String str, String strToRemove) {
		String res = "";
		if (str != null && !str.equals("") && strToRemove != null && !strToRemove.equals("") && str.toLowerCase().contains(strToRemove.toLowerCase())) {
			StringSplitterClient st = new StringSplitterClient(str, ";");
			for (int i = 0; i < st.getTokens().length; i++) {
				if (!st.getTokens()[i].equalsIgnoreCase(strToRemove)) {
					res += st.getTokens()[i];
					if (i < (st.getTokens().length - 1)) {
						res += ";";
					}
				}
			}
		} else {
			res = str;
		}
		return res;
	}
	
	private void populateModelloDefault(){
		
		String userid = "PUBLIC."+ AurigaLayout.getIdDominio();
		String prefName = "DEFAULT";
		if (prefName != null && !"".equals(prefName)) {
			AdvancedCriteria criteria = new AdvancedCriteria();
			criteria.addCriteria("userId", OperatorId.EQUALS, userid);
			criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
			criteria.addCriteria("flgIncludiPubbl", "1");
			//Carico i dati relativi al modello selezionato
			modelliDS.fetchData(criteria, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record[] data = response.getData();
					if (data.length != 0) {
						Record record = data[0];
						
						final Map oldValues = getMapValues(); //Mappa dei campi presenti nel form
						
						//Mappa dei valori del modello da inserire nel form
						final Map newValues = new Record(JSON.decode(record.getAttribute("value"))).toMap(); 	
						
						final String mittentePref = (String) newValues.get("mittente");
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
										newValues.put("mittente", "");
									}
									
									/*
									 * Creo un record con i valori dei campi presenti nel form.
									 * Nel caso questi stessi campi siano presenti nel modello verranno
									 * modificati dal metodo successivo 
									 */
									final Record values = new Record(oldValues); 
									
									setNewValuesFromModel(values, oldValues, newValues);
										
									modelliSelectItem.setValue("DEFAULT");
								}
							}
						});
					}
				}
			});
		}
	}

	public abstract void onClickOkButton(DSCallback callback);
	
	public abstract String getOrganoCollegiale();
	
}