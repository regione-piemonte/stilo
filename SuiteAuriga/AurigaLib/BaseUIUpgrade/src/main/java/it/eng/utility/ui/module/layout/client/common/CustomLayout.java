/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Random;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.BaseWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList.DettaglioWindow;
import it.eng.utility.ui.module.layout.client.common.file.DownloadExportList;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.portal.Portlet;
import it.eng.utility.ui.module.layout.shared.bean.FilterType;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class CustomLayout extends VLayout {

	public static final String backgroundColor = "#F0F0F0";

	protected String nomePortlet;
	protected String nomeEntita;
	
	protected JSONEncoder encoder;

	protected GWTRestDataSource datasource;

	protected CustomLayout instance;
	protected ConfigurableFilter filter;
	protected DetailSection intestazione;

	protected CustomList list;
	protected CustomDetail detail;
	protected MultiLookupTileGrid multiLookupGrid;
	
	protected ReloadListCallback reloadListCallback;	

	protected SectionStack sectionStack;
	protected SectionStackSection searchSection;
	protected SectionStackSection detailSection;

	protected ToolStrip filterToolStrip;
	protected SelectItem ricercaPreferitaSelectItem;
	protected ListGrid ricercaPreferitaPickListProperties;
	protected SavePreferenceWindow saveRicercaPreferitaWindow;
	protected SelectItem layoutFiltroSelectItem;
	protected ListGrid layoutFiltroPickListProperties;
	protected SavePreferenceWindow saveLayoutFiltroWindow;
	protected ToolStripButton saveLayoutFiltroButton;

	protected GWTRestDataSource ricercaPreferitaDS;
	protected GWTRestDataSource ricercaPreferitaDefaultDS;
	protected GWTRestDataSource layoutFiltroDS;
	protected GWTRestDataSource layoutFiltroDefaultDS;
	protected GWTRestDataSource layoutGestioneSubDS;
	protected GWTRestDataSource layoutListaDS;
	protected GWTRestDataSource layoutListaDefaultDS;
	protected GWTRestDataSource autoSearchDS;
	protected GWTRestDataSource autoSearchDefaultDS;

	protected HStack filterButtons;
	//protected Button filtriTipologiaButton;
	protected Button searchButton;
	protected Button clearButton;
	protected Button saveFilterButton;

	protected ToolStrip topListToolStrip;
	protected ToolStripButton newButton;
	protected ToolStripButton refreshListButton;
	protected ToolStripButton stopSearchButton;
	protected ToolStripButton multiselectButton;
	protected ToolStripSeparator topListToolStripSeparator;
	protected SelectItem formatoExportSelectItem;
	protected ToolStripButton exportButton;
	protected ToolStripButton detailAutoButton;
	protected SelectItem layoutListaSelectItem;
	protected ListGrid layoutListaPickListProperties;
	protected SavePreferenceWindow saveLayoutListaWindow;
	protected ToolStripButton saveLayoutListaButton;

	protected ToolStrip bottomListToolStrip;
	// protected ToolStripButton deleteMultiButton;

	protected Img filtroAttivoImg;
	protected HLayout nroRecordLayout;
	protected Label nroRecordLabel;
	protected int numRecords;
	protected int numRecordsXPag = 0;
	protected int numPages = 0;

	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton backToListButton;
	protected DetailToolStripButton editButton;
	public DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton deleteButton;
	protected DetailToolStripButton altreOpButton;
	protected DetailToolStripButton lookupButton;

	protected HLayout mainLayout;
	protected VLayout leftLayout;
	protected VLayout rightLayout;
	protected VLayout searchLayout;
	protected VLayout filterLayout;

	protected VLayout bodyIntestazioneLayout;

	protected HLayout bodyListLayout;
	protected VLayout listLayout;
	protected VLayout detailLayout;
	protected VLayout multiLookupLayout;

	protected ListGridField[] listFields;
	protected boolean multiselect;
	protected boolean detailAuto;
	protected boolean fromViewMode;

	protected String mode;
	protected ListaBean configLista;
	protected Boolean fullScreenDetail;
	protected boolean lookup = false;
	protected boolean multiLookup = false;

	protected String finalita;
	protected Boolean flgSelezioneSingola;
	protected Boolean showOnlyDetail = false;

	protected boolean isFetched = false;
	protected AdvancedCriteria searchCriteria = null;

	// indica se durante il recupero dei dati lato server, il numero totale eccede il massimo numero di record visualizzabili
	protected boolean overflow = false;
	
	// in caso di overflow questi due campi indicano l'ordinamento fatto lato server prima di restituire i primi n record dei totali
	protected String overflowSortField = null; 
	protected boolean overflowSortDesc = false;
	
	private Portlet owner;

	protected DynamicForm intestazioneForm;
	
	// OVERFLOW
	protected NumericItem maxRecordVisualizzabiliItem;
	
	// PAGINAZIONE
	protected SelectItem nroRecordXPaginaItem;	
	protected ExtendedNumericItem nroPaginaItem;
	protected ToolStripButton nroPaginaFirstButton;
	protected ToolStripButton nroPaginaPrevButton;
	protected ToolStripButton nroPaginaNextButton;
	protected ToolStripButton nroPaginaLastButton;
	protected ToolStripSeparator nroPaginaToolStripSeparator;
	
	protected ListGridRecord recordRicercaPreferitaFocused;
	
	protected boolean flgGetSubordinati;
	protected ImgButtonItem funzGestioneSubordinatiButton;
	
	public CustomLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail) {
		this(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, null);
	}

	public CustomLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail,
			Boolean pShowOnlyDetail) {
		this(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, pShowOnlyDetail);
	}

	public CustomLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail,
			String pFinalita, Boolean pFlgSelezioneSingola) {
		this(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, null);
	}

	public CustomLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail,
			String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {
		this(nomeEntita, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);
	}

	public CustomLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList,
			CustomDetail pDetail) {
		buildCustomLayout(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, null);
	}

	public CustomLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList,
			CustomDetail pDetail, Boolean pShowOnlyDetail) {
		buildCustomLayout(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, null, null, pShowOnlyDetail);
	}

	public CustomLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList,
			CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola) {
		buildCustomLayout(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, null);
	}

	public CustomLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList,
			CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {
		buildCustomLayout(nomePortlet, nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);
	}

	protected void buildCustomLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList,
			CustomDetail pDetail, String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {

		init();

		this.encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);

		this.setVisible(false);

		this.nomePortlet = nomePortlet;
		this.nomeEntita = nomeEntita;

		this.finalita = pFinalita;
		this.flgSelezioneSingola = pFlgSelezioneSingola;
		this.showOnlyDetail = pShowOnlyDetail != null ? pShowOnlyDetail : false;

		this.datasource = pDatasource;
		this.datasource.addParam("finalita", finalita);

		this.instance = this;

		this.filter = pFilter != null ? pFilter : new ConfigurableFilter(null,
				new MapCreator("pkFieldName", datasource.getPrimaryKeyFieldName(), "|*|").getProperties());
		this.list = pList;
		this.detail = pDetail;

		this.configLista = Layout.getListConfig(list != null ? list.getNomeEntita() : this.nomeEntita);
		this.fullScreenDetail = configLista.getFullScreenDetail() != null ? configLista.getFullScreenDetail() : true;

		this.filter.setLayout(this);
		this.list.setLayout(this);
		this.detail.setLayout(this);

		this.list.setDataSource(datasource);
		this.detail.setDataSource(datasource);

		this.listFields = list.getAllFields();
		
		/**
		 * DATASOURCE PREFERENCE
		 */
		
		buildListAndFilterPreferenceDataSources();
				
		/**
		 * LAYOUT INTESTAZIONE
		 */

		intestazioneForm = new DynamicForm();
		intestazioneForm.setWidth("100%");
		intestazioneForm.setHeight("5");
		intestazioneForm.setPadding(5);
		intestazioneForm.setNumCols(10);
		intestazioneForm.setWrapItemTitles(false);
		intestazioneForm.setColWidths(140, 150, 140, 150, 140, 150, 140, 150, 140, "*");

		bodyIntestazioneLayout = new VLayout();
		bodyIntestazioneLayout.setHeight100();
		bodyIntestazioneLayout.addMember(intestazioneForm);
		bodyIntestazioneLayout.hide();

		/**
		 * LAYOUT FILTRO
		 */
		saveRicercaPreferitaWindow = buildSaveRicercaPreferitaWindow();
		
		ricercaPreferitaSelectItem = new SelectItem("prefName");
		ricercaPreferitaSelectItem.setValueField("key");
		ricercaPreferitaSelectItem.setDisplayField("displayValue");
		ricercaPreferitaSelectItem.setTitle(I18NUtil.getMessages().ricercaSelectItem_title());
		ricercaPreferitaSelectItem.setWrapTitle(false);
		ricercaPreferitaSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) ricercaPreferitaSelectItem.getValue();
			}
		});

		ricercaPreferitaPickListProperties = new ListGrid();
		ricercaPreferitaPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		ricercaPreferitaPickListProperties.setShowHeader(false);
//		ricercaPreferitaPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		ricercaPreferitaPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				loadRicercaPreferita(event.getRecord(), event.getColNum());
			}
		});
		if (UserInterfaceFactory.isAttivaAccessibilita()) {
			ricercaPreferitaPickListProperties.addSelectionChangedHandler(new SelectionChangedHandler() {
	
							@Override
				public void onSelectionChanged(SelectionEvent event) {
					// TODO Auto-generated method stub
					recordRicercaPreferitaFocused = event.getRecord();
								}
			});
			
			ricercaPreferitaPickListProperties.addBodyKeyPressHandler(new BodyKeyPressHandler() {
				
				@Override
				public void onBodyKeyPress(BodyKeyPressEvent event) {
					if (EventHandler.getKey().equalsIgnoreCase("Enter")) {
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {
								ListGridRecord selectedRecord = ricercaPreferitaSelectItem.getSelectedRecord();
								loadRicercaPreferita(selectedRecord, 1);
							}
						});
					} else if (EventHandler.getKey().equalsIgnoreCase("Delete")) {
						Layout.showConfirmDialogWithWarning("Attenzione!", I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), "Ok", "Annulla", new BooleanCallback() {
	
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									if (recordRicercaPreferitaFocused != null) {
										deleteRicercaPreferita(recordRicercaPreferitaFocused);
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
		ricercaPreferitaSelectItem.setPickListProperties(ricercaPreferitaPickListProperties);

		ricercaPreferitaSelectItem.setOptionDataSource(ricercaPreferitaDS);
//		ricercaPreferitaSelectItem.setAllowEmptyValue(true);

		ListGridField ricercaPreferitaKeyField = new ListGridField("key");
		ricercaPreferitaKeyField.setHidden(true);
		ListGridField ricercaPreferitaDisplayValueField = new ListGridField("displayValue");
		ricercaPreferitaDisplayValueField.setWidth("100%");
		ListGridField ricercaPreferitaRemoveField = new ListGridField("remove");
		ricercaPreferitaRemoveField.setType(ListGridFieldType.ICON);
		ricercaPreferitaRemoveField.setWidth(18);
		// Per accessibilità. se modelliRemoveField.setIgnoreKeyboardClicks(false);, alla pressione della freccetta giù, parte automaticamente l'action ( cancella il record )
		ricercaPreferitaRemoveField.setIgnoreKeyboardClicks(true);
		ricercaPreferitaRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemovePreference(record)) {					
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				} else {
					return null;
				}				
			}
		});
//		ricercaPreferitaRemoveField.setIsRemoveField(true);
		ricercaPreferitaRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {				
				if (isAbilToRemovePreference(event.getRecord())) {					
					deleteRicercaPreferita(event.getRecord());
				}
			}
		});
		ricercaPreferitaSelectItem.setPickListFields(ricercaPreferitaKeyField, ricercaPreferitaRemoveField, ricercaPreferitaDisplayValueField);		
		ricercaPreferitaSelectItem.setAutoFetchData(false);
		ricercaPreferitaSelectItem.setFetchMissingValues(true);

		saveLayoutFiltroWindow = buildSaveLayoutFiltroWindow();
		
		layoutFiltroSelectItem = new SelectItem("prefName");
		layoutFiltroSelectItem.setValueField("key");
		layoutFiltroSelectItem.setDisplayField("displayValue");
		layoutFiltroSelectItem.setTitle(I18NUtil.getMessages().layoutSelectItem_title());
		layoutFiltroSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) layoutFiltroSelectItem.getValue();
			}
		});

		layoutFiltroPickListProperties = new ListGrid();
		layoutFiltroPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		layoutFiltroPickListProperties.setShowHeader(false);
		//		layoutFiltroPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		layoutFiltroPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				boolean isRemoveField = isAbilToRemovePreference(event.getRecord()) && event.getColNum() == 0;
				if(!isRemoveField) {	
					String userId = event.getRecord().getAttribute("userid");
					String preferenceName = event.getRecord().getAttribute("prefName");
					if (preferenceName != null && !"".equals(preferenceName)) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("userId", OperatorId.EQUALS, userId);
						criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
						layoutFiltroDS.fetchData(criteria, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length > 0 && data[0].getAttribute("value") != null) {
									AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
									filter.setCriteria(criteria);
									ricercaPreferitaSelectItem.setValue((String) null);
								}
							}
						});
					} else {
						layoutFiltroDefaultDS.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length > 0 && data[0].getAttribute("value") != null) {
									AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
									filter.setCriteria(criteria);
									ricercaPreferitaSelectItem.setValue((String) null);
								}
							}
						});
					}
				}
			}
		});
		layoutFiltroSelectItem.setPickListProperties(layoutFiltroPickListProperties);

		layoutFiltroSelectItem.setOptionDataSource(layoutFiltroDS);
//		layoutFiltroSelectItem.setAllowEmptyValue(true);

		ListGridField layoutFiltroKeyField = new ListGridField("key");
		layoutFiltroKeyField.setHidden(true);
		ListGridField layoutFiltroDisplayValueField = new ListGridField("displayValue");
		layoutFiltroDisplayValueField.setWidth("100%");
		ListGridField layoutFiltroRemoveField = new ListGridField("remove");
		layoutFiltroRemoveField.setType(ListGridFieldType.ICON);
		layoutFiltroRemoveField.setWidth(18);
		layoutFiltroRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if (isAbilToRemovePreference(record)) {							
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				} else {
					return null;
				}	
			}
		});
		//layoutFiltroRemoveField.setIsRemoveField(true);
		layoutFiltroRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {				
				if (isAbilToRemovePreference(event.getRecord())) {							
					SC.ask(I18NUtil.getMessages().askDeletePreference(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								final String key = event.getRecord().getAttribute("key");
								layoutFiltroDS.removeData(event.getRecord(), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {										
										String value = (String) layoutFiltroSelectItem.getValue();
										if (key != null && value != null && key.equals(value)) {
											layoutFiltroSelectItem.setValue((String) null);
										}
									}
								});
							}
						}
					});
				}
			}
		});
		layoutFiltroSelectItem.setPickListFields(layoutFiltroKeyField, layoutFiltroRemoveField, layoutFiltroDisplayValueField);		
		layoutFiltroSelectItem.setAutoFetchData(false);
		layoutFiltroSelectItem.setFetchMissingValues(true);

		saveLayoutFiltroButton = new ToolStripButton(I18NUtil.getMessages().saveLayoutButton_title());
		saveLayoutFiltroButton.setIcon("buttons/save.png");
		saveLayoutFiltroButton.setIconSize(16);
		saveLayoutFiltroButton.setAutoFit(true);
		saveLayoutFiltroButton.setPrompt(I18NUtil.getMessages().saveLayoutFiltroButton_prompt());
		saveLayoutFiltroButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if ((!saveLayoutFiltroWindow.isDrawn()) || (!saveLayoutFiltroWindow.isVisible())) {
					saveLayoutFiltroWindow.getForm().clearValues();
					saveLayoutFiltroWindow.getForm().setValue((String) layoutFiltroSelectItem.getValue());			
					saveLayoutFiltroWindow.markForRedraw();
					saveLayoutFiltroWindow.show();
				}
			}
		});

		// NR. MAX RECORD DA VISUALIZZARE
		maxRecordVisualizzabiliItem = new NumericItem("maxRecordVisualizzabili", I18NUtil.getMessages().maxRecordVisualizzabiliItem_title(), false);
		maxRecordVisualizzabiliItem.setWidth(60);
		maxRecordVisualizzabiliItem.setColSpan(1);
		maxRecordVisualizzabiliItem.setWrapTitle(false);
		maxRecordVisualizzabiliItem.setLength(5);

		nroRecordXPaginaItem = new SelectItem("nroRecordXPagina", I18NUtil.getMessages().nroRecordXPagina_title());
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			nroRecordXPaginaItem.setValueMap("5", "10", "20", "50");
			String defaultRecPagAcc = UserInterfaceFactory.getParametroDB("DEFAULT_NRO_RECORD_PAGINAZIONE_ACCESSIBILITA");
			nroRecordXPaginaItem.setValue(defaultRecPagAcc != null && !"".equals(defaultRecPagAcc) ? defaultRecPagAcc : "20");
		} else {
		nroRecordXPaginaItem.setValueMap("", "5", "10", "20", "50", "100");
		}
		nroRecordXPaginaItem.setWidth(60);
		nroRecordXPaginaItem.setColSpan(1);
		nroRecordXPaginaItem.setWrapTitle(false);
		nroRecordXPaginaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(nroPaginaItem != null) {
					nroPaginaItem.setValue("");
					nroPaginaItem.hide();
				}
				if(nroPaginaFirstButton != null) {
					nroPaginaFirstButton.hide();
				}
				if(nroPaginaPrevButton != null) {
					nroPaginaPrevButton.hide();
				}
				if(nroPaginaNextButton != null) {
					nroPaginaNextButton.hide();
				}
				if(nroPaginaLastButton != null) {
					nroPaginaLastButton.hide();
				}
				if(nroPaginaToolStripSeparator != null) {
					nroPaginaToolStripSeparator.hide();
				}
				reloadList();
			}
		});
		
		layoutGestioneSubDS = UserInterfaceFactory.getPreferenceDataSource();
		// se uguale per tutte le maschere
		//layoutGestioneSubDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "attivaGestSubordinati" + getPrefKeySuffixSpecXDominio());
		// se diverso per ogni maschera
		layoutGestioneSubDS.addParam("prefKey", getPrefKeyPrefix() + "attivaGestSubordinati" + getPrefKeySuffixSpecXDominio());

		funzGestioneSubordinatiButton = new ImgButtonItem("gestione_estesa","blank.png","ATTIVA GESTIONE ESTESA");
		funzGestioneSubordinatiButton.setWidth(50);
		funzGestioneSubordinatiButton.setWrapTitle(false);
		funzGestioneSubordinatiButton.setShowTitle(true);
		funzGestioneSubordinatiButton.setTitle("ATTIVA GESTIONE ESTESA");
		funzGestioneSubordinatiButton.setColSpan(1);
		funzGestioneSubordinatiButton.setIconWidth(30);
		funzGestioneSubordinatiButton.setIconHeight(24);
		funzGestioneSubordinatiButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				if(!flgGetSubordinati) {
					flgGetSubordinati = true;
					attivaFunzGestioneSubordinati();
				} else {
					flgGetSubordinati = false;
					disattivaFunzGestioneSubordinati();
				}

				final String value = flgGetSubordinati ? "true" : "false";

				AdvancedCriteria criteriaGestioneSub = new AdvancedCriteria();
				criteriaGestioneSub.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
				layoutGestioneSubDS.fetchData(criteriaGestioneSub, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();

						if (data.length != 0) {
							Record record = data[0];
							record.setAttribute("value", value);
							layoutGestioneSubDS.updateData(record);
						} else {
							Record record = new Record();
							record.setAttribute("prefName", "DEFAULT");
							record.setAttribute("value", value);
							layoutGestioneSubDS.addData(record);
						}
					}
				});

				markForRedraw();
			}
		});

		filterToolStrip = new ToolStrip();
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			filterToolStrip.setTabIndex(null);
			filterToolStrip.setCanFocus(true);	
 		}
		filterToolStrip.setWidth100();
		filterToolStrip.setHeight(30);
		filterToolStrip.setBackgroundColor("transparent");
		filterToolStrip.setBackgroundImage("blank.png");
		filterToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		filterToolStrip.setBorder("0px");
		filterToolStrip.addFormItem(ricercaPreferitaSelectItem);
//		if(showMaxRecordVisualizzabiliItem()) {
//			filterToolStrip.addFormItem(maxRecordVisualizzabiliItem);
//		}
//		if(showPaginazioneItems()) {
//			filterToolStrip.addFormItem(nroRecordXPaginaItem);
//		}
		if(showFunzGestioneSubordinati()) {
			filterToolStrip.addFormItem(funzGestioneSubordinatiButton);
		}
		filterToolStrip.addFill(); // push all buttons to the right
		filterToolStrip.addFormItem(layoutFiltroSelectItem);
		filterToolStrip.addButton(saveLayoutFiltroButton);

		filterButtons = new HStack(3);
		filterButtons.setPadding(5);

		searchButton = new Button(I18NUtil.getMessages().searchButton_title());
		searchButton.setIcon("buttons/search.png");
		searchButton.setIconSize(16);
		searchButton.setAutoFit(false);
		searchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// settiamo la proprietà searchstarted come variabile di controllo 
				// che viene usata nei filtri di tipo "stringa_full_text_mista_ricerca_lookup"
				// per non far partire la ricerca con lookup se è stato cliccato il pulsante CERCA
				// senza prima togliere il focus dal textItem del filtro
				filter.setSearchStarted(true);
				
				// scatena l'evento blurchanged cosìcchè il valore inserito nel TextItem del 
				// filtro venga salvato nel filtro 
				searchButton.focusAfterGroup();
				
				manageOnClickSearchButton();
				
				// settiamo la proprietà searchstarted a false altrimenti se inserisco un valore 
				// nel TextItem e tolgo volontariamente il focus per far partire la ricerca con 
				// lookup, questa non parte.
				filter.setSearchStarted(false);
				
			}
		});

		clearButton = new Button(I18NUtil.getMessages().clearButton_title());
		clearButton.setIcon("buttons/clear.png");
		clearButton.setIconSize(16);
		clearButton.setAutoFit(false);
		clearButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				filter.clearValues();
			}
		});

		saveFilterButton = new Button(I18NUtil.getMessages().saveFilterButton_title());
//		saveFilterButton.setIcon("buttons/save.png");
//		saveFilterButton.setIconSize(16);
		saveFilterButton.setAutoFit(false);
		saveFilterButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if ((!saveRicercaPreferitaWindow.isDrawn()) || (!saveRicercaPreferitaWindow.isVisible())) {
					saveRicercaPreferitaWindow.getForm().clearValues();
					saveRicercaPreferitaWindow.getForm().setValue((String) ricercaPreferitaSelectItem.getValue());	
					saveRicercaPreferitaWindow.markForRedraw();
					saveRicercaPreferitaWindow.show();
				}				
			}
		});

		
		//filterButtons.addMember(filtriTipologiaButton);
		filterButtons.addMember(searchButton);
		filterButtons.addMember(clearButton);
		filterButtons.addMember(saveFilterButton);

		//filtriTipologiaButton.hide();
		
				
		filterLayout = new VLayout();
		filterLayout.setStyleName(it.eng.utility.Styles.filterLayout);
		filterLayout.setMembers(filterToolStrip, filter, filterButtons);

		if (getShowFilter()) {
			filterLayout.setVisible(true);
			filterLayout.setShowResizeBar(true);
		} else {
			filterLayout.setVisible(false);
		}

		
		/**
		 * LAYOUT LISTA
		 */

		newButton = new ToolStripButton();
		newButton.setIcon("buttons/new.png");
		newButton.setIconSize(16);
		newButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageNewClick();
			}
		});

		refreshListButton = new ToolStripButton();
		refreshListButton.setIcon("buttons/refreshList.png");
		refreshListButton.setIconSize(16);
		refreshListButton.setPrompt(I18NUtil.getMessages().refreshListButton_prompt());
		refreshListButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});

		stopSearchButton = new ToolStripButton();
		stopSearchButton.setIcon("buttons/stop.png");
		stopSearchButton.setIconSize(16);
		stopSearchButton.setPrompt("Interrompi ricerca");
		stopSearchButton.setVisible(false);
		stopSearchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageStopSearch();
			}
		});

		multiselectButton = new ToolStripButton();
		multiselectButton.setIcon("buttons/multiselect.png");
		multiselectButton.setIconSize(16);
		multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());

		detailAutoButton = new ToolStripButton();
		detailAutoButton.setIcon("buttons/detail_off.png");
		detailAutoButton.setIconSize(16);
		detailAutoButton.setPrompt(I18NUtil.getMessages().detailAutoOffButton_prompt());

		saveLayoutListaWindow = buildSaveLayoutListaWindow();
		
		layoutListaSelectItem = new SelectItem("prefName");
		layoutListaSelectItem.setValueField("key");
		layoutListaSelectItem.setDisplayField("displayValue");
		layoutListaSelectItem.setTitle(I18NUtil.getMessages().layoutSelectItem_title());
		layoutListaSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) layoutListaSelectItem.getValue();
			}
		});

		layoutListaPickListProperties = new ListGrid();
		layoutListaPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		layoutListaPickListProperties.setShowHeader(false);
//		layoutListaPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		layoutListaPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {				
				boolean isRemoveField = isAbilToRemovePreference(event.getRecord()) && event.getColNum() == 0;
				if(!isRemoveField) {	
					String userId = event.getRecord().getAttribute("userid");
					String preferenceName = event.getRecord().getAttribute("prefName");
					if (preferenceName != null && !"".equals(preferenceName)) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("userId", OperatorId.EQUALS, userId);
						criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
						layoutListaDS.fetchData(criteria, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									list.setViewState(record.getAttributeAsString("value"));
								}
							}
						});
					} else {
						layoutListaDefaultDS.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									list.setViewState(record.getAttributeAsString("value"));
								}
							}
						});
					}
				}
			}
		});
		layoutListaSelectItem.setPickListProperties(layoutListaPickListProperties);
		layoutListaSelectItem.setOptionDataSource(layoutListaDS);
//		layoutListaSelectItem.setAllowEmptyValue(true);

		ListGridField layoutListaKeyField = new ListGridField("key");
		layoutListaKeyField.setHidden(true);
		ListGridField layoutListaDisplayValueField = new ListGridField("displayValue");
		layoutListaDisplayValueField.setWidth("100%");
		ListGridField layoutListaRemoveField = new ListGridField("remove");
		layoutListaRemoveField.setType(ListGridFieldType.ICON);
		layoutListaRemoveField.setWidth(18);
		layoutListaRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemovePreference(record)) {							
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				} else {
					return null;
				}	
			}
		});
		//layoutListaRemoveField.setIsRemoveField(true);
		layoutListaRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {				
				if (isAbilToRemovePreference(event.getRecord())) {							
					SC.ask(I18NUtil.getMessages().askDeletePreference(), new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								final String key = event.getRecord().getAttribute("key");
								layoutListaDS.removeData(event.getRecord(), new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {										
										String value = (String) layoutListaSelectItem.getValue();
										if (key != null && value != null && key.equals(value)) {
											layoutListaSelectItem.setValue((String) null);
										}
									}
								});
							}
						}
					});
				}
			}
		});
		layoutListaSelectItem.setPickListFields(layoutListaKeyField, layoutListaRemoveField, layoutListaDisplayValueField);		
		layoutListaSelectItem.setAutoFetchData(false);
		layoutListaSelectItem.setFetchMissingValues(true);

		saveLayoutListaButton = new ToolStripButton(I18NUtil.getMessages().saveLayoutButton_title());
		saveLayoutListaButton.setIcon("buttons/save.png");
		saveLayoutListaButton.setIconSize(16);
		saveLayoutListaButton.setAutoFit(true);
		saveLayoutListaButton.setPrompt(I18NUtil.getMessages().saveLayoutListaButton_prompt());
		saveLayoutListaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if ((!saveLayoutListaWindow.isDrawn()) || (!saveLayoutListaWindow.isVisible())) {
					saveLayoutListaWindow.getForm().clearValues();
					saveLayoutListaWindow.getForm().setValue((String) layoutListaSelectItem.getValue());					
					saveLayoutListaWindow.markForRedraw();
					saveLayoutListaWindow.show();
				}				
			}
		});

		topListToolStripSeparator = new ToolStripSeparator();

		topListToolStrip = new ToolStrip();
		topListToolStrip.setWidth100();
		topListToolStrip.setHeight(30);
		topListToolStrip.setBackgroundColor("transparent");
		topListToolStrip.setBackgroundImage("blank.png");
		topListToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		topListToolStrip.setBorder("0px");
		for (ToolStripButton lToolStripButton : getCustomNewButtons()) {
			topListToolStrip.addButton(lToolStripButton);
		}
		topListToolStrip.addButton(newButton);
		topListToolStrip.addButton(refreshListButton);
		if (isStoppableSearch()) {
			topListToolStrip.addMember(stopSearchButton);
		}
		topListToolStrip.addButton(multiselectButton);
		topListToolStrip.addMember(topListToolStripSeparator);

		if (getCustomTopListButtons() != null && getCustomTopListButtons().length > 0) {
			for (ToolStripButton lToolStripButton : getCustomTopListButtons()) {
				topListToolStrip.addButton(lToolStripButton);
			}
		}
		
		if (getCustomTopListFormItems() != null && getCustomTopListFormItems().length > 0) {
			for (FormItem lFormItem : getCustomTopListFormItems()) {
				topListToolStrip.addFormItem(lFormItem);
			}
		}

		// topListToolStrip.addMember(filtroAttivo);
		// topListToolStrip.addSeparator();
		if(showMaxRecordVisualizzabiliItem()) {
			topListToolStrip.addFormItem(maxRecordVisualizzabiliItem);
		}
		if(showPaginazioneItems()) {
			topListToolStrip.addFormItem(nroRecordXPaginaItem);
		}
		topListToolStrip.addFill(); // push all buttons to the right
		topListToolStrip.addButton(detailAutoButton);
		if (fullScreenDetail) {
			detailAutoButton.hide();
		}
		topListToolStrip.addFormItem(layoutListaSelectItem);
		topListToolStrip.addButton(saveLayoutListaButton);

		// deleteMultiButton = new ToolStripButton();
		// deleteMultiButton.setIcon("buttons/delete.png");
		// deleteMultiButton.setIconSize(16);
		// deleteMultiButton.setPrompt(I18NUtil.getMessages().deleteMultiButton_prompt());
		// deleteMultiButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// final ListGridRecord[] selectedRecords = list.getSelectedRecords();
		// if(selectedRecords != null && selectedRecords.length > 0) {
		// SC.ask(I18NUtil.getMessages().windowDeleteAsk_title(), I18NUtil.getMessages().deleteButtonAsk_message(), new BooleanCallback() {
		// @Override
		// public void execute(Boolean value) {
		// if(value) {
		// for(final Record record : selectedRecords) {
		// list.removeData(record, new DSCallback() {
		// @Override
		// public void execute(DSResponse response, Object rawData, DSRequest request) {
		// if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
		// Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(getTipoEstremiRecord(record)), "", MessageType.INFO));
		// hideDetail(true);
		// } else {
		// Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(getTipoEstremiRecord(record)), "", MessageType.ERROR));
		// }
		// }
		// });
		// }
		// }
		// }
		// });
		// } else {
		// Layout.addMessage(new MessageBean(I18NUtil.getMessages().noSelectedRecords_message(), "", MessageType.INFO));
		// }
		// }
		// });

		formatoExportSelectItem = new SelectItem();
		formatoExportSelectItem.setShowTitle(false);
		formatoExportSelectItem.setWidth(160);

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("", I18NUtil.getMessages().selezione_formato_file_esportazione());
		valueMap.put("csv", "CSV");
		valueMap.put("pdf", "Adobe PDF");
		valueMap.put("xls", "Excel");
		valueMap.put("xml", "XML");

		LinkedHashMap<String, String> valueIconMap = new LinkedHashMap<String, String>();
		valueIconMap.put("", "");
		valueIconMap.put("csv", "export/csv.png");
		valueIconMap.put("pdf", "export/pdf.png");
		valueIconMap.put("xls", "export/xls.png");
		valueIconMap.put("xml", "export/xml.png");

		formatoExportSelectItem.setValueMap(valueMap);
		formatoExportSelectItem.setValueIcons(valueIconMap);
		formatoExportSelectItem.setDefaultValue("");

		exportButton = new ToolStripButton(I18NUtil.getMessages().exportButton_prompt());
		exportButton.setIcon("buttons/export.png");
		exportButton.setIconSize(16);
		exportButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				export();
			}
		});

		filtroAttivoImg = new Img("buttons/imbuto.png");
		// filtroAttivoImg.setAppImgDir("buttons/");
		filtroAttivoImg.setWidth(16);
		filtroAttivoImg.setHeight(16);
		filtroAttivoImg.setAlign(Alignment.CENTER);
		filtroAttivoImg.setValign(VerticalAlignment.CENTER);
		filtroAttivoImg.setPrompt(I18NUtil.getMessages().filtroAttivoImg_prompt());
		filtroAttivoImg.setVisible(false);

		nroRecordLayout = new HLayout();
		nroRecordLayout.setOverflow(Overflow.VISIBLE);
		nroRecordLayout.setWidth(5);

		nroRecordLabel = new Label();
		nroRecordLabel.setAlign(Alignment.CENTER);
		nroRecordLabel.setValign(VerticalAlignment.CENTER);
		nroRecordLabel.setWrap(false);
		nroRecordLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				manageNroRecordPlusClick(nroRecordLabel, numRecords);
			}
		});

		nroRecordLayout.setMembers(nroRecordLabel);
		
		nroPaginaItem = new ExtendedNumericItem("nroPagina", I18NUtil.getMessages().nroPagina_title(), false);
		nroPaginaItem.setValue("1");
		nroPaginaItem.setWidth(60);
		nroPaginaItem.setColSpan(1);
		nroPaginaItem.setWrapTitle(false);
		nroPaginaItem.setLength(5);
		nroPaginaItem.setHintStyle(it.eng.utility.Styles.formTitle);
		nroPaginaItem.setVisible(false);	
		nroPaginaItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(nroPaginaItem.getValueAsString() != null && !nroPaginaItem.getValueAsString().equalsIgnoreCase("")){
					int nroPagina = Integer.valueOf((String) nroPaginaItem.getValue());
					if(nroPagina < 1) {
						nroPaginaItem.setValue(1);
					} else if(nroPagina > numPages) {
						nroPaginaItem.setValue(numPages);
					}
				} else {
					nroPaginaItem.setValue(1);
				}
				reloadList();
			}
		});	
		
		nroPaginaFirstButton = new ToolStripButton();
		nroPaginaFirstButton.setIcon("[SKIN]DateChooser/doubleArrow_left.png");
		nroPaginaFirstButton.setIconSize(16);
		nroPaginaFirstButton.setPrompt("Vai alla prima pagina");
		nroPaginaFirstButton.setShowDisabled(false);
		nroPaginaFirstButton.setVisible(false);
		nroPaginaFirstButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(nroPaginaItem != null){
					nroPaginaItem.setValue(1);
					reloadList();			
				}
			}
		});
		
		nroPaginaPrevButton = new ToolStripButton();
		nroPaginaPrevButton.setIcon("[SKIN]DateChooser/arrow_left.png");
		nroPaginaPrevButton.setIconSize(16);
		nroPaginaPrevButton.setPrompt("Vai alla pagina precedente");
		nroPaginaPrevButton.setShowDisabled(false);
		nroPaginaPrevButton.setVisible(false);
		nroPaginaPrevButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(nroPaginaItem != null && nroPaginaItem.getValueAsString() != null && !nroPaginaItem.getValueAsString().equalsIgnoreCase("")){
					int nroPagina = Integer.valueOf((String) nroPaginaItem.getValue());
					if(nroPagina > 1) {
						nroPaginaItem.setValue(nroPagina - 1);
						reloadList();
					}
				}
			}
		});
		
		nroPaginaNextButton = new ToolStripButton();
		nroPaginaNextButton.setIcon("[SKIN]DateChooser/arrow_right.png");
		nroPaginaNextButton.setIconSize(16);
		nroPaginaNextButton.setPrompt("Vai alla pagina successiva");
		nroPaginaNextButton.setShowDisabled(false);
		nroPaginaNextButton.setVisible(false);
		nroPaginaNextButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(nroPaginaItem != null && nroPaginaItem.getValueAsString() != null && !nroPaginaItem.getValueAsString().equalsIgnoreCase("")){
					int nroPagina = Integer.valueOf((String) nroPaginaItem.getValue());
					if(nroPagina < numPages) {
						nroPaginaItem.setValue(nroPagina + 1);
						reloadList();
					}
				}
			}
		});
				
		nroPaginaLastButton = new ToolStripButton();
		nroPaginaLastButton.setIcon("[SKIN]DateChooser/doubleArrow_right.png");
		nroPaginaLastButton.setIconSize(16);
		nroPaginaLastButton.setPrompt("Vai all'ultima pagina");
		nroPaginaLastButton.setShowDisabled(false);
		nroPaginaLastButton.setVisible(false);
		nroPaginaLastButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(nroPaginaItem != null){
					nroPaginaItem.setValue(numPages);
					reloadList();			
				}
			}
		});
		
		nroPaginaToolStripSeparator = new ToolStripSeparator();
		nroPaginaToolStripSeparator.setVisible(false);	
		
		refreshNroRecord();

		bottomListToolStrip = new ToolStrip();
		bottomListToolStrip.setWidth100();
		bottomListToolStrip.setHeight(30);
		bottomListToolStrip.setBackgroundColor("transparent");
		bottomListToolStrip.setBackgroundImage("blank.png");
		bottomListToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		bottomListToolStrip.setBorder("0px");
		if(showExportListButton()) {	
			bottomListToolStrip.addFormItem(formatoExportSelectItem);
			bottomListToolStrip.addButton(exportButton);
		}
		if (getMultiselectButtons() != null && getMultiselectButtons().length > 0) {
			bottomListToolStrip.addSeparator();
			for (MultiToolStripButton lToolStripButton : getMultiselectButtons()) {
				bottomListToolStrip.addButton(lToolStripButton);
			}
		}
		if (getCustomBottomListButtons() != null && getCustomBottomListButtons().length > 0) {
			bottomListToolStrip.addSeparator();
			for (ToolStripButton lToolStripButton : getCustomBottomListButtons()) {
				bottomListToolStrip.addButton(lToolStripButton);
			}
		}
		bottomListToolStrip.addFill(); // push all buttons to the right
		if(showPaginazioneItems()) {
			bottomListToolStrip.addButton(nroPaginaFirstButton);
			bottomListToolStrip.addButton(nroPaginaPrevButton);
			bottomListToolStrip.addFormItem(nroPaginaItem);
			bottomListToolStrip.addButton(nroPaginaNextButton);
			bottomListToolStrip.addButton(nroPaginaLastButton);
			bottomListToolStrip.addMember(nroPaginaToolStripSeparator);
		}
		bottomListToolStrip.addMember(filtroAttivoImg);
		bottomListToolStrip.addSpacer(0);
		bottomListToolStrip.addMember(nroRecordLayout);
		bottomListToolStrip.addSpacer(0);

		multiselectButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getMultiselect()) {
					setMultiselect(false);
				} else {
					setMultiselect(true);
				}
			}
		});

		detailAutoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getDetailAuto()) {
					setDetailAuto(false);
					hideDetail();
				} else {
					setDetailAuto(true);
				}
			}
		});

		bodyListLayout = new HLayout();
		bodyListLayout.setMembers(list);
		bodyListLayout.setPadding(5);
		// bodyListLayout.setOverflow(Overflow.AUTO);

		listLayout = new VLayout();
		listLayout.setStyleName(it.eng.utility.Styles.listLayout);		
		listLayout.setHeight100();
		listLayout.addMember(topListToolStrip);
		listLayout.addMember(bodyListLayout);
		listLayout.addMember(bottomListToolStrip);

		/**
		 * LAYOUT DETTAGLIO
		 */

		backToListButton = new DetailToolStripButton(I18NUtil.getMessages().backToListButton_prompt(), "buttons/back.png");
		backToListButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Layout.isReloadDisabled()) {
					hideDetail(false);
				} else if (detail.isSaved()) {
					detail.setSaved(false);
					hideDetailAfterSave();
				} else
					hideDetail();
				if (UserInterfaceFactory.isAttivaAccessibilita()) {
					DettaglioWindow dettaglioWindow = getList().getDettaglioWindow();
					if (dettaglioWindow != null) {
						dettaglioWindow.setOpen(false);
						dettaglioWindow.close();
					}
					mostraBackButtonAccessibilita();
				}
			}
		});

		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onEditButtonClick();
			}
		});

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						onSaveButtonClick();
					}
				});
			}
		});

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						reloadDetailCallback();
					}
				});
			}
		});

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						undoCallback();
					}
				});
			}
		});

		deleteButton = new DetailToolStripButton(I18NUtil.getMessages().deleteButton_prompt(), "buttons/delete.png");
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record(detail.getValuesManager().getValues());
				delete(record);
			}
		});

		altreOpButton = new DetailToolStripButton(I18NUtil.getMessages().altreOpButton_prompt(), "buttons/altreOp.png");
		altreOpButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}
		});

		lookupButton = new DetailToolStripButton(getLookupButtonPrompt(), getLookupButtonImage());
		lookupButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record(detail.getValuesManager().getValues());
				record.setAttribute("from", "D");
				doLookup(record);
				if (!showOnlyDetail && multiLookup) {
					hideDetail();
				}
			}
		});

		createDetailToolStrip();

		detailLayout = new VLayout();
		detailLayout.setOverflow(Overflow.HIDDEN);
		setOverflow(Overflow.AUTO);

		multiLookupGrid = new MultiLookupTileGrid(this);

		multiLookupLayout = new VLayout();
		multiLookupLayout.setOverflow(Overflow.HIDDEN);
		multiLookupLayout.setVisible(false);
		multiLookupLayout.setHeight(120);
		multiLookupLayout.setWidth100();
		multiLookupLayout.setMembers(multiLookupGrid);

		mainLayout = new HLayout();
		leftLayout = new VLayout();
		rightLayout = new VLayout();
		searchLayout = new VLayout();

		if (!fullScreenDetail) {
			detailLayout.setHeight("20%");
			searchLayout.setShowResizeBar(true);
			searchLayout.setResizeBarTarget("next");
			searchLayout.setResizeFrom("B");
		} else {
			detailLayout.setHeight100();
			detail.setHeight100();
		}

		detailLayout.setMembers(detail, detailToolStrip);

		setWidth100();
		mainLayout.setWidth100();
		rightLayout.setWidth100();
		searchLayout.setWidth100();
		filterLayout.setWidth100();
		listLayout.setWidth100();
		detailLayout.setWidth100();
		bodyIntestazioneLayout.setWidth100();

		searchLayout.setMembers(bodyIntestazioneLayout, filterLayout, listLayout);
		rightLayout.setMembers(searchLayout, multiLookupLayout);
		mainLayout.setMembers(rightLayout);

		if (!(this instanceof CustomSimpleTreeLayout) && !(this instanceof CustomAdvancedTreeLayout)) {
			sectionStack = new SectionStack();
			sectionStack.setWidth100();
			sectionStack.setHeight100();
			sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
			sectionStack.setAnimateSections(false);
			sectionStack.setOverflow(Overflow.AUTO);

			searchSection = new SectionStackSection();
			searchSection.setShowHeader(false);
			searchSection.setExpanded(true);
			searchSection.setItems(mainLayout);

			detailSection = new SectionStackSection();
			detailSection.setShowHeader(false);
			detailSection.setExpanded(false);
			detailSection.setItems(detailLayout);

			sectionStack.setSections(searchSection, detailSection);

			setMembers(sectionStack);
		}

		if (flgSelezioneSingola == null) {
			setMultiLookup(false);
			setLookup(false);
		} else if (!flgSelezioneSingola) {
			setMultiLookup(true);
			setLookup(true);
		} else {
			setMultiLookup(false);
			setLookup(true);
		}

		if (!(this instanceof CustomAdvancedTreeLayout) && !(this instanceof CustomSimpleTreeLayout)) {
			setMultiselect(getDefaultMultiselect());
			setDetailAuto(getDefaultDetailAuto());
			hideDetail();
		}

		if (getMultiselectButtons().length == 0) {
			multiselectButton.hide();
		}

		if (!(this instanceof CustomSimpleTreeLayout) && !(this instanceof CustomAdvancedTreeLayout)) {

			if (configLista.getOrdinamentoDefault() != null) {
				for (String key : configLista.getOrdinamentoDefault().keySet()) {
					if ("ascending".equals(configLista.getOrdinamentoDefault().get(key))) {
						list.addSort(new SortSpecifier(key, SortDirection.ASCENDING));
					} else if ("descending".equals(configLista.getOrdinamentoDefault().get(key))) {
						list.addSort(new SortSpecifier(key, SortDirection.DESCENDING));
					}
				}
			}

			caricaPreference();
			caricaPreferenceGestioneSubordinati();
		}

	}
	
	public void buildListAndFilterPreferenceDataSources() {
		
		ricercaPreferitaDS = UserInterfaceFactory.getPreferenceDataSource();
		ricercaPreferitaDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "filter");
		
		ricercaPreferitaDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		ricercaPreferitaDefaultDS.addParam("userId", "DEFAULT");
		ricercaPreferitaDefaultDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "filter");
		ricercaPreferitaDefaultDS.addParam("prefName", "DEFAULT");
		
		layoutFiltroDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutFiltroDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "layout.filter");

		layoutFiltroDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutFiltroDefaultDS.addParam("userId", "DEFAULT");
		layoutFiltroDefaultDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "layout.filter");
		layoutFiltroDefaultDS.addParam("prefName", "DEFAULT");
		
		layoutListaDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "layout.grid");

		layoutListaDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDefaultDS.addParam("userId", "DEFAULT");
		layoutListaDefaultDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "layout.grid");
		layoutListaDefaultDS.addParam("prefName", "DEFAULT");
		
		autoSearchDS = UserInterfaceFactory.getPreferenceDataSource();
		autoSearchDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "autosearch");

		autoSearchDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		autoSearchDefaultDS.addParam("userId", "DEFAULT");
		autoSearchDefaultDS.addParam("prefKey", getPrefKeyPrefix() + (getFinalita() != null && !"".equals(getFinalita()) ? (getFinalita() + ".") : "") + "autosearch");
		autoSearchDefaultDS.addParam("prefName", "DEFAULT");
	}
	
	private void saveLayoutListaFromRicercaPreferita (final String key, final String prefName, final boolean flgPubblica) {
		if (prefName != null && !prefName.equals("")) {
			AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();
			if(flgPubblica) {
				String idDominio = Layout.getIdDominio();									
				criteriaLayoutLista.addCriteria("userId", OperatorId.EQUALS, "PUBLIC." + idDominio);
			}
			criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, prefName);
			layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					final Record[] data = response.getData();
					// Record layoutLista = new Record();
					// layoutLista.setAttribute("viewState", list.getViewState());
					// layoutLista.setAttribute("detailAuto", getDetailAuto());
					if (data.length != 0) {
//						SC.ask("Esiste già una preferenza di layout lista con lo stesso nome e verrà sovrascritta. Vuoi comunque procedere?", new BooleanCallback() {
//
//							@Override
//							public void execute(Boolean boolValue) {
//								if (boolValue){
									Record record = data[0];
									// record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
									record.setAttribute("value", list.getViewState());
									layoutListaDS.updateData(record, new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											layoutListaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutListaSelectItem.setValue(key);								
												}
											});
										}
									});
//								}
//							}
//						});						
					} else {
						Record record = new Record();
						if (flgPubblica) {
							String idDominio = Layout.getIdDominio();									
							record.setAttribute("userid", "PUBLIC." + idDominio);
						}
						record.setAttribute("prefName", prefName);
						// record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
						record.setAttribute("value", list.getViewState());
						record.setAttribute("flgAbilDel", true);
						layoutListaDS.addData(record, new DSCallback() {
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								layoutListaSelectItem.fetchData(new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										layoutListaSelectItem.setValue(key);								
									}
								});						
							}
						});
					}
				}
			});
		}
	}
	
	private void loadLayoutListaFromRicercaPreferita (ListGridRecord record) {
		final String userId = record.getAttribute("userid");
		final String preferenceName = record.getAttribute("prefName");
		if (preferenceName != null && !"".equals(preferenceName)) {
			AdvancedCriteria criteria = new AdvancedCriteria();
			criteria.addCriteria("userId", OperatorId.EQUALS, userId);
			criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
			layoutListaDS.fetchData(criteria, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record[] data = response.getData();
					if (data.length != 0) {
						Record record = data[0];
						list.setViewState(record.getAttributeAsString("value"));
						layoutListaSelectItem.setValue(record.getAttribute("key"));												
					} else {
//						AdvancedCriteria criteria = new AdvancedCriteria();
//						criteria.addCriteria("userId", OperatorId.EQUALS, userId);
//						criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
//						layoutListaDS.fetchData(criteria, new DSCallback() {
//
//							@Override
//							public void execute(DSResponse response, Object rawData, DSRequest request) {
//								Record[] data = response.getData();
//								if (data.length != 0) {
//									Record record = data[0];
//									list.setViewState(record.getAttributeAsString("value"));
//									layoutListaSelectItem.setValue("DEFAULT");
//								} else {
//									loadLayoutListaDefaultFromRicercaPreferita();
//								}
//							}
//						});						
					}
				}
			});
		}
	}
	
	private void loadLayoutListaDefaultFromRicercaPreferita () {
		layoutListaDefaultDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					Record record = data[0];
					list.setViewState(record.getAttributeAsString("value"));
					layoutListaSelectItem.setValue((String) null);
				} else {					
//					layoutListaSelectItem.setValue((String) null);
				}
			}
		});
	}

	private void deleteRicercaPreferita (final ListGridRecord record) {
		if (isAbilToRemovePreference(record)) {							
			SC.ask(I18NUtil.getMessages().askDeleteRicercaPreferita(), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if(value) {
						final String key = record.getAttribute("key");
						ricercaPreferitaDS.removeData(record, new DSCallback() {
		
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {								
								String value = (String) ricercaPreferitaSelectItem.getValue();
								if (key != null && value != null && key.equals(value)) {
									ricercaPreferitaSelectItem.setValue((String) null);
								}
							}
						});
					}
				}
			});
		}
	}
	
	private void loadRicercaPreferita (final ListGridRecord record, int numCol) {
		boolean isRemoveField = isAbilToRemovePreference(record) && numCol == 0;
		if(!isRemoveField) {	
			String userId = record.getAttribute("userid");
			String preferenceName = record.getAttribute("prefName");
			if (preferenceName != null && !"".equals(preferenceName)) {
				AdvancedCriteria criteria = new AdvancedCriteria();
				criteria.addCriteria("userId", OperatorId.EQUALS, userId);
				criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
				ricercaPreferitaDS.fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (data.length > 0 && data[0].getAttribute("value") != null) {
							AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
							//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?									
							setMaxRecordVisualizzabiliFromCriteria(criteria);	
							setNroRecordXPaginaFromCriteria(criteria);	
							filter.setCriteria(checkRetrocompatibilitaFiltri(criteria));
							layoutFiltroSelectItem.setValue((String) null);
							loadLayoutListaFromRicercaPreferita(record);
						}
					}
				});
			} else {
				ricercaPreferitaDefaultDS.fetchData(null, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (data.length > 0 && data[0].getAttribute("value") != null) {
							AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
							setMaxRecordVisualizzabili("");
							setNroRecordXPagina("");									
							filter.setCriteria(checkRetrocompatibilitaFiltri(criteria));
							layoutFiltroSelectItem.setValue((String) null);
							loadLayoutListaDefaultFromRicercaPreferita();
						}
					}
				});
			}
		}
	}

	public boolean hasDefaultValueRicercaPreferita() {
		return true;
	}

	public void createDetailToolStrip() {
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addButton(backToListButton);
		detailToolStrip.addFill(); // push all buttons to the right

		for (ToolStripButton lToolStripButton : getDetailButtons()) {
			detailToolStrip.addButton(lToolStripButton);
		}
	}

	public void manageNewClick() {
		list.deselectAllRecords();
		detail.markForRedraw();
		detail.editNewRecord();
		detail.getValuesManager().clearErrors(true);
		newMode();
	}

	public boolean isStoppableSearch() {
		Boolean hideStopSearchButton = UserInterfaceFactory.getParametroDBAsBoolean("HIDE_STOP_SEARCH_BUTTON");
		return hideStopSearchButton == null || !hideStopSearchButton;
	}

	protected void manageStopSearch() {
		list.setEmptyMessage(I18NUtil.getMessages().list_noSearchMessage());
		list.setData(new Record[] {});
		stopSearchButton.hide();
		try {
			Record lRecord = new Record();
			lRecord.setAttribute("uuid", uuidAttuale);
			lRecord.setAttribute("datasource", getDatasource().extraparam.get("sourceidobject"));
			OneCallGWTRestService<Record, Record> lOneCallStopSearchDatasource = new OneCallGWTRestService<Record, Record>("StopSearchDatasource");
			lOneCallStopSearchDatasource.setShowPrompt(false);
			lOneCallStopSearchDatasource.call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					uuidAttuale = null;
				}
			});
		} catch (Exception e) {
		}
	}

	public void hideStopSearchButton() {
		uuidAttuale = null;
		getDatasource().extraparam.remove("uuid");
		stopSearchButton.hide();
	}

	public GWTRestDataSource getDatasource() {
		return datasource;
	}

	public boolean getShowFilter() {
		return filter.isConfigured;
	}

	public boolean isForcedToAutoSearch() {
		return false;
	}

	public void reloadDetailCallback() {
		editMode();
	}

	public void undoCallback() {
		viewMode();
	}
	
	public void reloadListAndSetCurrentRecord(Record record) {
		String pkFieldName = list.getDataSource().getPrimaryKeyFieldName();
		if(pkFieldName != null) {
			String pkValue = record.getAttribute(pkFieldName);
			if(pkValue == null) {
				// se il record in input è quello del dettaglio e ha una primaryKey diversa rispetto alla lista
				// allora mi devo prendere il valore con la primaryKey del dettaglio
				pkValue = record.getAttribute(getDetailPrimaryKeyFieldName());
			}					
			ListGridRecord currentRecord = new ListGridRecord();
			currentRecord.setAttribute(pkFieldName, pkValue);
			list.setCurrentRecord(currentRecord);
			((GWTRestDataSource) list.getDataSource()).extraparam.put(pkFieldName, pkValue);
		}
		reloadList();
	}
	
	// Faccio un ricaricamento della lista applicando i filtri dell'ultima ricerca effettuata (non quelli visibili a maschera), se non era stata effettutata nessuna ricerca in precedenza non faccio nulla
	public void reloadList() {
		if(searchCriteria != null) {
			doSearch(searchCriteria);
		}
	}

	public String getDetailPrimaryKeyFieldName() {
		return detail.getDataSource().getPrimaryKeyFieldName();
	}

	public void onEditButtonClick() {
		editMode(true);
	}

	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if (detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	protected void realSave(final Record record) {
		DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();
		String pkFieldName = detail.getDataSource().getPrimaryKeyFieldName();
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = buildDSCallback();
		try {
			if ((saveOperationType != null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute(pkFieldName) == null
					|| record.getAttribute(pkFieldName).equals("")) {
				detail.getDataSource().addData(record, callback);
			} else {
				detail.getDataSource().updateData(record, callback);
			}
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}

	protected DSCallback buildDSCallback() {
		DSCallback callback = new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					try {
						list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								viewMode();
								Layout.hideWaitPopup();
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "",
										MessageType.INFO));
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					if (!fullScreenDetail) {
						reloadListAndSetCurrentRecord(savedRecord);
					}
				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		return callback;
	}

	/**
	 * Funzione che viene richiamata nel momento in cui si esegue il reload
	 * Si setta il valore dell'overflow a false in modo tale che, se si cambia elemento 
	 * nell'albero da visualizzare, venga aggiornato effettivamente tale visualizzazione
	 * Se questo flag non viene impostato allora nel momento in cui si esegue una ricerca 
	 * in cui il numero di elementi supera il limite consentito (e quindi pone overflow a true)
	 * e poi si esegue una ricerca in una sezione in cui il numero di elementi è inferiore allora
	 * non verrà modificato tale flag ma verrà comunque visualizzato il +
	 */
	public void refreshNroRecordOnReload() {
		overflow = false;
		overflowSortField = null;
		overflowSortDesc = false;
		numRecordsXPag = 0;
		numPages = 0;
		refreshNroRecord();
	}
	
	public void refreshNroRecord() {
		refreshNroRecord(null);
	}

	public void refreshNroRecord(Integer totalRows) {
		// il fatto di mostrare o meno il pulsante di espansione è una configurazione di sistema
		// Boolean showOverflow = Layout.getGenericConfig().getShowPlusAlertMaxRecord();		
		if(totalRows != null) {
			numRecords = totalRows.intValue();
		} else if(!showPaginazioneItems() || numRecordsXPag <= 0) {
			numRecords = list.getRecords().length;
		}
		if (numRecords > 0) {
			if (overflow) {
				nroRecordLabel
				.setContents(I18NUtil.getMessages().nroRecordLabel_prefix() + ": <span style=\"cursor:pointer\">" + numRecords + "&nbsp;+</span>");
			} else {
				nroRecordLabel.setContents(I18NUtil.getMessages().nroRecordLabel_prefix() + ": " + numRecords);
			}

			if (instance.isCreated()) {
				nroRecordLabel.show();
			} 
		} else {
			nroRecordLabel.hide();
		}
	}

	/**
	 * Punto di personalizzazione delle varie finestre che estendono CustomLayout
	 * 
	 * @param nroRecordLabel
	 * @param numRecords
	 */
	public void manageNroRecordPlusClick(final Label nroRecordLabel, final Integer numRecords) {

		if (nroRecordLabel.getContents().contains("+")) {

			AdvancedCriteria lAdvancedCriteria = filter.isVisible() ? filter.getCriteria() : searchCriteria;

			lAdvancedCriteria = buildSearchCriteria(lAdvancedCriteria);

			Record lRecordInput = new Record();
			lRecordInput.setAttribute("criteria", lAdvancedCriteria);

			// creo una nuova istanza per non alterare lo stato del datasource di riferimento della lista
			GWTRestDataSource referenceDatasource = createNroRecordDatasource();

			// se il metodo è stato correttamente implementato dalla
			// sottoclasse, quindi la funzionalità è stata resa disponibile,
			// allora procedo con il recupero dei dati
			if (referenceDatasource != null) {

				referenceDatasource.executecustom("getNroRecordTotali", lRecordInput, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record lRecordOutput = response.getData()[0];
							try {
								Integer nroRecordTot = lRecordOutput.getAttributeAsInt("nroRecordTot");
								nroRecordLabel.setContents(I18NUtil.getMessages().nroRecordLabel_prefix() + ": " + numRecords + " su "
										+ (numRecords.compareTo(nroRecordTot) > 0 ? numRecords : nroRecordTot));
							} catch (Exception e) {
							}
						}
					}
				});
			}
		}
	}

	/**
	 * Crea il datasource da utilizzare nella chiamata per il recupero del numero totale di record in caso di overflow. Deve essere creata una nuova istanza per
	 * non modificare lo stato del datasource orginale. Ogni specifico layout deve istanziare ed inizializzare il datasource di riferimento
	 * 
	 * @return
	 */
	protected GWTRestDataSource createNroRecordDatasource() {
		return null;
	}
	
	public boolean isPaginazioneAttiva() {
		return showPaginazioneItems() && numPages > 0;
	}

	public void refreshNroRecord(boolean overflow, String overflowSortField, boolean overflowSortDesc, Integer rowsForPage, Integer totalRows) {
		this.overflow = overflow;
		this.overflowSortField = overflow ? overflowSortField : null;
		this.overflowSortDesc = overflow ? overflowSortDesc : false;
		this.numRecords = totalRows != null ? totalRows.intValue() : 0;
		this.numRecordsXPag = rowsForPage != null ? rowsForPage.intValue() : 0;
		this.numPages = 0;
		if(numRecords > 0 && numRecordsXPag > 0) {
			this.numPages = (numRecords / numRecordsXPag); 
			if(numRecords % numRecordsXPag > 0) {
				this.numPages += 1; 
			}
		}
		refreshNroRecord(totalRows);		
		if(showPaginazioneItems() && isPaginazioneAttiva()) {
			list.setCanGroupBy(false);
			if(nroPaginaItem != null) {
				if(nroPaginaItem.getValueAsString() == null || "".equals(nroPaginaItem.getValueAsString())) {
					nroPaginaItem.setValue("1");			
				}
				nroPaginaItem.setHint("&nbsp;/&nbsp;" + numPages);
				nroPaginaItem.show();
			}
			if(nroPaginaFirstButton != null) {
				nroPaginaFirstButton.enable();
				nroPaginaFirstButton.show();
			}
			if(nroPaginaPrevButton != null) {
				nroPaginaPrevButton.enable();
				nroPaginaPrevButton.show();					
			}
			if(nroPaginaNextButton != null) {
				nroPaginaNextButton.enable();
				nroPaginaNextButton.show();
			}
			if(nroPaginaLastButton != null) {
				nroPaginaLastButton.enable();
				nroPaginaLastButton.show();
			}
			if(nroPaginaToolStripSeparator != null) {
				nroPaginaToolStripSeparator.show();
			}
		} else {
			list.setCanGroupBy(true);
			if(nroPaginaItem != null) {
				nroPaginaItem.setValue("1");
				nroPaginaItem.hide();
			}
			if(nroPaginaFirstButton != null) {
				nroPaginaFirstButton.hide();
			}
			if(nroPaginaPrevButton != null) {
				nroPaginaPrevButton.hide();
			}
			if(nroPaginaNextButton != null) {
				nroPaginaNextButton.hide();
			}
			if(nroPaginaLastButton != null) {
				nroPaginaLastButton.hide();
			}
			if(nroPaginaToolStripSeparator != null) {
				nroPaginaToolStripSeparator.hide();
			}
		}
	}

	public boolean isAbilToRemovePreference(Record record) {
		return (record.getAttribute("prefName") != null && !"".equals(record.getAttributeAsString("prefName")) && !"DEFAULT".equalsIgnoreCase(record.getAttributeAsString("prefName"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}

	public Record getRecordListaDefPrefs() {
		Record lRecord = new Record();
		String userIdForPrefs = Layout.getUtenteLoggato().getUseridForPrefs();
		lRecord.setAttribute("userId", userIdForPrefs != null && !"".equals(userIdForPrefs) ? userIdForPrefs : Layout.getUtenteLoggato().getUserid());
		lRecord.setAttribute("prefKeyPrefix", getPrefKeyPrefix());
		return lRecord;
	}

	public void getListaDefPrefs(final ServiceCallback<Record> callback) {
		getListaDefPrefs(getRecordListaDefPrefs(), callback);
	}

	public void getListaDefPrefs(Record record, final ServiceCallback<Record> callback) {		
		UserInterfaceFactory.getListaDefPrefsService().call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (object != null) {

					boolean autosearch = false;

					if (isForcedToAutoSearch()) {
						autosearch = true;
					} else if (object.getAttribute("autosearch") != null && !"".equals(object.getAttribute("autosearch"))) {
						autosearch = new Boolean(object.getAttribute("autosearch"));
					}

					boolean flgLayoutListaDefault = object.getAttribute("flgLayoutListaDefault") != null
							&& "1".equals(object.getAttribute("flgLayoutListaDefault"));

					String layoutLista = null;
					String layoutListaDefault = null;

					layoutListaSelectItem.setValue((String) null);

					if (object.getAttribute("layoutLista") != null && !"".equals(object.getAttribute("layoutLista"))) {
						if (flgLayoutListaDefault) {
							layoutListaDefault = object.getAttribute("layoutLista");
						} else {
							layoutLista = object.getAttribute("layoutLista");
						}
					}

					boolean flgRicercaPreferitaDefault = object.getAttribute("flgRicercaPreferitaDefault") != null
							&& "1".equals(object.getAttribute("flgRicercaPreferitaDefault"));
					boolean flgLayoutFiltroDefault = object.getAttribute("flgLayoutFiltroDefault") != null
							&& "1".equals(object.getAttribute("flgLayoutFiltroDefault"));

					String ricercaPreferita = null;
					String ricercaPreferitaDefault = null;
					String layoutFiltro = null;
					String layoutFiltroDefault = null;

					ricercaPreferitaSelectItem.setValue((String) null);
					layoutFiltroSelectItem.setValue((String) null);

					if (object.getAttribute("ricercaPreferita") != null && !"".equals(object.getAttribute("ricercaPreferita"))) {
						if (flgRicercaPreferitaDefault) {
							ricercaPreferitaDefault = object.getAttribute("ricercaPreferita");
						} else {
							ricercaPreferita = object.getAttribute("ricercaPreferita");
						}
					}
					if (object.getAttribute("layoutFiltro") != null && !"".equals(object.getAttribute("layoutFiltro"))) {
						if (flgLayoutFiltroDefault) {
							layoutFiltroDefault = object.getAttribute("layoutFiltro");
						} else {
							layoutFiltro = object.getAttribute("layoutFiltro");
						}
					}

					Record result = new Record();
					result.setAttribute("autosearch", autosearch);
					result.setAttribute("layoutLista", layoutLista);
					result.setAttribute("layoutListaDefault", layoutListaDefault);
					result.setAttribute("ricercaPreferita", ricercaPreferita);
					result.setAttribute("ricercaPreferitaDefault", ricercaPreferitaDefault);
					result.setAttribute("layoutFiltro", layoutFiltro);
					result.setAttribute("layoutFiltroDefault", layoutFiltroDefault);

					if (callback != null) {
						callback.execute(result);
					}
				}
			}
		});
	}

	protected void caricaPreference() {
		if (UserInterfaceFactory.getListaDefPrefsService() != null) {
			getListaDefPrefs(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (object.getAttribute("layoutLista") != null) {
						layoutListaSelectItem.setValue("DEFAULT");
						list.setViewState(object.getAttribute("layoutLista"));
					} else if (object.getAttribute("layoutListaDefault") != null) {
						layoutListaSelectItem.setValue((String) null);
						list.setViewState(object.getAttribute("layoutListaDefault"));
					} else {
						layoutListaSelectItem.setValue((String) null);
					}
					if (object.getAttribute("ricercaPreferita") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(object.getAttribute("ricercaPreferita")));
						ricercaPreferitaSelectItem.setValue("DEFAULT");
						layoutFiltroSelectItem.setValue((String) null);
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabiliFromCriteria(criteria);					
						setNroRecordXPaginaFromCriteria(criteria);				
						setCriteriaAndFirstSearch(checkRetrocompatibilitaFiltri(criteria), 
								object.getAttributeAsBoolean("autosearch"));
					} else if (object.getAttribute("layoutFiltro") != null) {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue("DEFAULT");
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");				
						setCriteriaAndFirstSearch(new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltro"))),
								object.getAttributeAsBoolean("autosearch"));
					} else if (object.getAttribute("ricercaPreferitaDefault") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(object.getAttribute("ricercaPreferitaDefault")));
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabiliFromCriteria(criteria);					
						setNroRecordXPaginaFromCriteria(criteria);				
						setCriteriaAndFirstSearch(checkRetrocompatibilitaFiltri(criteria),
								object.getAttributeAsBoolean("autosearch"));
					} else if (object.getAttribute("layoutFiltroDefault") != null) {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");				
						setCriteriaAndFirstSearch(new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltroDefault"))),
								object.getAttributeAsBoolean("autosearch"));
					} else {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						setMaxRecordVisualizzabili("");
						setNroRecordXPagina("");
						setDefaultCriteriaAndFirstSearch(object.getAttributeAsBoolean("autosearch"));
					} 
				}
			});
		} else {
			caricaPreferenceLista(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (isForcedToAutoSearch()) {
						caricaPreferenceRicerca(true);
					} else {
						AdvancedCriteria criteriaAutoSearch = new AdvancedCriteria();
						criteriaAutoSearch.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
						autoSearchDS.fetchData(criteriaAutoSearch, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									caricaPreferenceRicerca(new Boolean(record.getAttribute("value")));
								} else {
									autoSearchDefaultDS.fetchData(null, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											Record[] data = response.getData();
											if (data.length != 0) {
												Record record = data[0];
												caricaPreferenceRicerca(new Boolean(record.getAttribute("value")));
											} else {
												caricaPreferenceRicerca(false);
											}
										}
									});
								}
							}
						});
					}
				}
			});
		}
	}

	public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
		filter.setCriteria(new AdvancedCriteria());
		firstSearch(autosearch, true);
	}

	protected void caricaLayoutFiltro() {
		caricaLayoutFiltro(false);
	}

	protected void caricaLayoutFiltro(final boolean autoSearch) {
		if (UserInterfaceFactory.getListaDefPrefsService() != null) {
			getListaDefPrefs(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (object.getAttribute("layoutFiltro") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltro")));
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue("DEFAULT");
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");				
						setCriteriaAndFirstSearch(criteria, autoSearch);
					} else if (object.getAttribute("layoutFiltroDefault") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltroDefault")));
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");				
						setCriteriaAndFirstSearch(criteria, autoSearch);
					} else {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");				
						setDefaultCriteriaAndFirstSearch(autoSearch);
					}
				}
			});
		} else {
			AdvancedCriteria criteriaLayoutFiltro = new AdvancedCriteria();
			criteriaLayoutFiltro.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
			layoutFiltroDS.fetchData(criteriaLayoutFiltro, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record[] data = response.getData();
					if (data.length > 0 && data[0].getAttribute("value") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
						layoutFiltroSelectItem.setValue("DEFAULT");						
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");				
						setCriteriaAndFirstSearch(criteria, autoSearch);
					} else {
						layoutFiltroDefaultDS.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length > 0 && data[0].getAttribute("value") != null) {
									AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
									layoutFiltroSelectItem.setValue((String) null);
									//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
									setMaxRecordVisualizzabili("");					
									setNroRecordXPagina("");				
									setCriteriaAndFirstSearch(criteria, autoSearch);
								} else {
									//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
									setMaxRecordVisualizzabili("");					
									setNroRecordXPagina("");				
									setDefaultCriteriaAndFirstSearch(autoSearch);
								}
							}
						});
					}
				}
			});
		}
	}

	protected void caricaPreferenceRicerca(final boolean autoSearch) {
		AdvancedCriteria criteriaRicercaPreferita = new AdvancedCriteria();
		criteriaRicercaPreferita.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		ricercaPreferitaDS.fetchData(criteriaRicercaPreferita, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length > 0 && data[0].getAttribute("value") != null) {
					AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
					ricercaPreferitaSelectItem.setValue("DEFAULT");
					layoutFiltroSelectItem.setValue((String) null);		
					//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
					setMaxRecordVisualizzabiliFromCriteria(criteria);					
					setNroRecordXPaginaFromCriteria(criteria);				
					setCriteriaAndFirstSearch(criteria, autoSearch);
				} else {
					AdvancedCriteria criteriaLayoutFiltro = new AdvancedCriteria();
					criteriaLayoutFiltro.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					layoutFiltroDS.fetchData(criteriaLayoutFiltro, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length > 0 && data[0].getAttribute("value") != null) {
								AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
								ricercaPreferitaSelectItem.setValue((String) null);
								layoutFiltroSelectItem.setValue("DEFAULT");
								//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
								setMaxRecordVisualizzabili("");					
								setNroRecordXPagina("");				
								setCriteriaAndFirstSearch(criteria, autoSearch);
							} else {
								ricercaPreferitaDefaultDS.fetchData(null, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										Record[] data = response.getData();
										if (data.length > 0 && data[0].getAttribute("value") != null) {
											AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
											ricercaPreferitaSelectItem.setValue((String) null);
											layoutFiltroSelectItem.setValue((String) null);
											//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
											setMaxRecordVisualizzabiliFromCriteria(criteria);					
											setNroRecordXPaginaFromCriteria(criteria);				
											setCriteriaAndFirstSearch(criteria, autoSearch);
										} else {
											layoutFiltroDefaultDS.fetchData(null, new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													Record[] data = response.getData();
													if (data.length > 0 && data[0].getAttribute("value") != null) {
														AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
														ricercaPreferitaSelectItem.setValue((String) null);
														layoutFiltroSelectItem.setValue((String) null);
														//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
														setMaxRecordVisualizzabili("");					
														setNroRecordXPagina("");				
														setCriteriaAndFirstSearch(criteria, autoSearch);
													} else {
														ricercaPreferitaSelectItem.setValue((String) null);
														layoutFiltroSelectItem.setValue((String) null);
														//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
														setMaxRecordVisualizzabili("");					
														setNroRecordXPagina("");				
														setDefaultCriteriaAndFirstSearch(autoSearch);
													}
												}
											});
										}
									}
								});
							}
						}
					});
				}
			}
		});
	}

	protected void caricaPreferenceGestioneSubordinati() {

		AdvancedCriteria criteriaGestSubordinati = new AdvancedCriteria();
		criteriaGestSubordinati.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		layoutGestioneSubDS.fetchData(criteriaGestSubordinati, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					Record record = data[0];
					if(record.getAttributeAsString("value") != null && !"".equals(record.getAttributeAsString("value"))) {
						if("true".equals(record.getAttributeAsString("value"))) {
							flgGetSubordinati = true;
							attivaFunzGestioneSubordinati();
						} else {
							flgGetSubordinati = false;
							disattivaFunzGestioneSubordinati();
						}
					}else {
						flgGetSubordinati = false;
						disattivaFunzGestioneSubordinati();
					}
				} else {
					flgGetSubordinati = false;
					disattivaFunzGestioneSubordinati();
				}
			}  
		});
	}

	protected void caricaPreferenceLista(final DSCallback callback) {
		AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();
		criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					Record record = data[0];
					list.setViewState(record.getAttributeAsString("value"));
					layoutListaSelectItem.setValue("DEFAULT");
					callback.execute(new DSResponse(), null, new DSRequest());
				} else {
					layoutListaDefaultDS.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								list.setViewState(record.getAttributeAsString("value"));
								layoutListaSelectItem.setValue((String) null);
							} else {
								layoutListaSelectItem.setValue((String) null);
							}
							callback.execute(new DSResponse(), null, new DSRequest());
						}
					});
				}
			}
		});
	}
	
	// Utilizzato nei layout in cui ci sono sezioni diverse e vanno ricaricate le preference dei filtri e della lista
	public void reloadListAndFilter() {

		list.setData(new ListGridRecord[0]);		
		refreshNroRecordOnReload(); //Aggiorna la grafica relativamente al numero di record trovati
		filtroAttivoImg.hide();	
		setMultiselect(getDefaultMultiselect());
		
		list.setEmptyMessage(I18NUtil.getMessages().list_noSearchMessage());				
		
		if(saveRicercaPreferitaWindow != null) {
			saveRicercaPreferitaWindow.destroy();		
		}
		if(saveLayoutFiltroWindow != null) {
			saveLayoutFiltroWindow.destroy();		
		}
		if(saveLayoutListaWindow != null) {
			saveLayoutListaWindow.destroy();		
		}		
		
		// quando faccio il destroy delle SavePreferenceWindow viene fatto anche il destroy dei relativi datasource (che sono anche quelli delle select) quindi li devo ricreare
		buildListAndFilterPreferenceDataSources();				
		
		ricercaPreferitaSelectItem.setOptionDataSource(ricercaPreferitaDS);  
		layoutFiltroSelectItem.setOptionDataSource(layoutFiltroDS);  
		layoutListaSelectItem.setOptionDataSource(layoutListaDS);
				
		saveRicercaPreferitaWindow = buildSaveRicercaPreferitaWindow();
		saveLayoutFiltroWindow = buildSaveLayoutFiltroWindow();
		saveLayoutListaWindow = buildSaveLayoutListaWindow();
		
		ricercaPreferitaSelectItem.fetchData();
		ricercaPreferitaSelectItem.redraw();
		layoutFiltroSelectItem.fetchData();
		layoutFiltroSelectItem.redraw();
		layoutListaSelectItem.fetchData();
		layoutListaSelectItem.redraw();				
		
		caricaPreference();									

	}
	
	public SavePreferenceWindow buildSaveRicercaPreferitaWindow() {
		return new SavePreferenceWindow(I18NUtil.getMessages().saveFilterButton_title(), ricercaPreferitaDS, hasDefaultValueRicercaPreferita(), true,
				new SavePreferenceRecordAction() {

			@Override
			public void execute(Record record) {
				final String prefName = record != null ? record.getAttribute("prefName") : null;
				final boolean flgPubblica = record != null && record.getAttributeAsBoolean("flgPubblica") != null && record.getAttributeAsBoolean("flgPubblica");				
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteriaRicercaPreferita = new AdvancedCriteria();
					if(flgPubblica) {
						String idDominio = Layout.getIdDominio();									
						criteriaRicercaPreferita.addCriteria("userId", OperatorId.EQUALS, "PUBLIC." + idDominio);
					}
					criteriaRicercaPreferita.addCriteria("prefName", OperatorId.EQUALS, prefName);
					ricercaPreferitaDS.fetchData(criteriaRicercaPreferita, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							Record[] data = response.getData();
							
							// Ricavo i filtri impostati nella GUI
							AdvancedCriteria criteriaFiltroGui = filter.getCriteria(true);
							
							if (criteriaFiltroGui != null) {
								List<Criterion> newCriterionListFiltroGui = new ArrayList<Criterion>();
								for (int i = 0; i < criteriaFiltroGui.getCriteria().length; i++) {
									Criterion crit = criteriaFiltroGui.getCriteria()[i];
									if ("ricercaMailArchiviate".equals(criteriaFiltroGui.getCriteria()[i].getFieldName())) {
										Layout.addMessage(new MessageBean("Le opzioni relative al filtro 'Ricerca sulle e-mail archiviate' non verranno salvate nel filtro preferito", "", MessageType.WARNING));
									} else {
										newCriterionListFiltroGui.add(crit);
									}
								}						
								//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?								
								if(showMaxRecordVisualizzabiliItem() && maxRecordVisualizzabiliItem != null && maxRecordVisualizzabiliItem.getValueAsString() != null && !maxRecordVisualizzabiliItem.getValueAsString().equalsIgnoreCase("")) {
									int maxRecordVisualizzabili = Integer.valueOf((String) maxRecordVisualizzabiliItem.getValue());
									if (maxRecordVisualizzabili > 0){
										newCriterionListFiltroGui.add(new Criterion("maxRecordVisualizzabili", OperatorId.EQUALS, maxRecordVisualizzabiliItem.getValueAsString()));	
									}
								}							
								if(showPaginazioneItems() && nroRecordXPaginaItem != null && nroRecordXPaginaItem.getValueAsString() != null && !nroRecordXPaginaItem.getValueAsString().equalsIgnoreCase("")){
									int nroRecordXPagina = Integer.valueOf((String) nroRecordXPaginaItem.getValue());
									if (nroRecordXPagina > 0){
										newCriterionListFiltroGui.add(new Criterion("nroRecordXPagina", OperatorId.EQUALS, nroRecordXPaginaItem.getValueAsString()));	
									}
								}
								criteriaFiltroGui.setCriteria(newCriterionListFiltroGui.toArray(new Criterion[newCriterionListFiltroGui.size()]));
							}
							
							// Codifico la stringa dei criteria
							String criteriaStr = JSON.encode(criteriaFiltroGui.getJsObj(), encoder);
							if (data.length != 0) {
								Record record = data[0];
								record.setAttribute("value", criteriaStr);
								ricercaPreferitaDS.updateData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											ricercaPreferitaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													ricercaPreferitaSelectItem.setValue(record.getAttribute("key"));		
													saveLayoutListaFromRicercaPreferita(record.getAttribute("key"), prefName, flgPubblica);							
												}
											});																				
										}									
									}
								});
							} else {
								Record record = new Record();
								if (flgPubblica) {
									String idDominio = Layout.getIdDominio();									
									record.setAttribute("userid", "PUBLIC." + idDominio);
								}
								record.setAttribute("prefName", prefName);
								record.setAttribute("value", criteriaStr);
								record.setAttribute("flgAbilDel", true);
								ricercaPreferitaDS.addData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											ricercaPreferitaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													ricercaPreferitaSelectItem.setValue(record.getAttribute("key"));		
													saveLayoutListaFromRicercaPreferita(record.getAttribute("key"), prefName, flgPubblica);							
												}
											});																				
										}
									}
								});
							}
						}
					});
				}
			}
		});
	}
	
	public SavePreferenceWindow buildSaveLayoutFiltroWindow() {
		return new SavePreferenceWindow(I18NUtil.getMessages().saveLayoutButton_title(), layoutFiltroDS, true, true, new SavePreferenceRecordAction() {

			@Override
			public void execute(Record record) {
				final String prefName = record != null ? record.getAttribute("prefName") : null;
				final boolean flgPubblica = record != null && record.getAttributeAsBoolean("flgPubblica") != null && record.getAttributeAsBoolean("flgPubblica");				
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteriaLayoutFiltro = new AdvancedCriteria();
					if(flgPubblica) {
						String idDominio = Layout.getIdDominio();									
						criteriaLayoutFiltro.addCriteria("userId", OperatorId.EQUALS, "PUBLIC." + idDominio);
					}
					criteriaLayoutFiltro.addCriteria("prefName", OperatorId.EQUALS, prefName);
					layoutFiltroDS.fetchData(criteriaLayoutFiltro, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								String criteriaWithoutValuesStr = JSON.encode(filter.getCriteriaWithoutValues().getJsObj(), encoder);
								record.setAttribute("value", criteriaWithoutValuesStr);
								layoutFiltroDS.updateData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											layoutFiltroSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutFiltroSelectItem.setValue(record.getAttribute("key"));								
												}
											});																				
										}
									}
								});
							} else {
								Record record = new Record();
								if (flgPubblica) {
									String idDominio = Layout.getIdDominio();									
									record.setAttribute("userid", "PUBLIC." + idDominio);
								}
								record.setAttribute("prefName", prefName);
								String criteriaWithoutValuesStr = JSON.encode(filter.getCriteriaWithoutValues().getJsObj(), encoder);
								record.setAttribute("value", criteriaWithoutValuesStr);
								record.setAttribute("flgAbilDel", true);
								layoutFiltroDS.addData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											layoutFiltroSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutFiltroSelectItem.setValue(record.getAttribute("key"));								
												}
											});																				
										}		
									}
								});
							}
						}
					});
				}
			}
		});
	}
	
	public SavePreferenceWindow buildSaveLayoutListaWindow() {
		return new SavePreferenceWindow(I18NUtil.getMessages().saveLayoutButton_title(), layoutListaDS, true, true, new SavePreferenceRecordAction() {

			@Override
			public void execute(Record record) {
				final String prefName = record != null ? record.getAttribute("prefName") : null;
				final boolean flgPubblica = record != null && record.getAttributeAsBoolean("flgPubblica") != null && record.getAttributeAsBoolean("flgPubblica");				
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();
					if(flgPubblica) {
						String idDominio = Layout.getIdDominio();									
						criteriaLayoutLista.addCriteria("userId", OperatorId.EQUALS, "PUBLIC." + idDominio);
					}
					criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, prefName);
					layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							// Record layoutLista = new Record();
							// layoutLista.setAttribute("viewState", list.getViewState());
							// layoutLista.setAttribute("detailAuto", getDetailAuto());
							if (data.length != 0) {
								Record record = data[0];
								// record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
								record.setAttribute("value", list.getViewState());
								layoutListaDS.updateData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											layoutListaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutListaSelectItem.setValue(record.getAttribute("key"));								
												}
											});																				
										}						
									}
								});
							} else {
								Record record = new Record();
								if (flgPubblica) {
									String idDominio = Layout.getIdDominio();									
									record.setAttribute("userid", "PUBLIC." + idDominio);
								}
								record.setAttribute("prefName", prefName);
								// record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
								record.setAttribute("value", list.getViewState());
								record.setAttribute("flgAbilDel", true);
								layoutListaDS.addData(record, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											final Record record = response.getData()[0];
											layoutListaSelectItem.fetchData(new DSCallback() {
												
												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													layoutListaSelectItem.setValue(record.getAttribute("key"));								
												}
											});																				
										}					
									}
								});
							}
						}
					});
				}
			}
		});
	}

	public String getPrefKeyPrefix() {
		return Layout.getConfiguredPrefKeyPrefix() + this.nomeEntita + ".";
	}

	public String getPrefKeyPrefixForPortlet() {
		return Layout.getConfiguredPrefKeyPrefix() + this.nomePortlet + ".";
	}

	public boolean getDefaultMultiselect() {
		return false;
	}

	public boolean getDefaultDetailAuto() {
		return (Layout.getGenericConfig().getDefaultDetailAuto() != null) ? Layout.getGenericConfig().getDefaultDetailAuto() : true;
	}

	/**
	 * Funzione richiamata nel momento in cui non viene caricato nessun filtro di default
	 */
	public void defaultFirstSearch() {
		doSearch();
	}

	public void firstSearch(boolean autoSearch) {
		firstSearch(autoSearch, false);
	}

	public void firstSearch(boolean autoSearch, boolean noFilter) {
		if (autoSearch) {
			if (noFilter) {
				defaultFirstSearch();
			} else {
				doSearch();
			}
		}
	}

	public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, final boolean autoSearch) {
		filter.setCriteriaAndFirstSearch(criteria, autoSearch);
	}

	protected ToolStripButton[] getCustomNewButtons() {
		return new ToolStripButton[] {};
	}

	protected ToolStripButton[] getCustomTopListButtons() {
		return new ToolStripButton[] {};
	}
		
	protected FormItem[] getCustomTopListFormItems() {
		return new FormItem[] {};
	}

	protected MultiToolStripButton[] getMultiselectButtons() {
		return new MultiToolStripButton[] {};
	}

	protected ToolStripButton[] getCustomBottomListButtons() {
		return new ToolStripButton[] {};
	}

	protected ToolStripButton[] getCustomDetailButtons() {
		return new ToolStripButton[] {};
	}

	public List<ToolStripButton> getDetailButtons() {
		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();
		for (ToolStripButton lToolStripButton : getCustomDetailButtons()) {
			detailButtons.add(lToolStripButton);
		}
		detailButtons.add(editButton);
		detailButtons.add(saveButton);
		detailButtons.add(reloadDetailButton);
		detailButtons.add(undoButton);
		detailButtons.add(deleteButton);
		detailButtons.add(altreOpButton);
		detailButtons.add(lookupButton);
		return detailButtons;
	}

	public void reload() {
		reload(null);
	}

	public void reload(final DSCallback callback) {
		if (this.mode.equals("new")) {
			detail.editNewRecord();
			detail.getValuesManager().clearErrors(true);
			if (callback != null) {
				callback.execute(null, null, new DSRequest());
			}
		} else {
			Record record = new Record(detail.getValuesManager().getValues());
			detail.getDataSource().performCustomOperation("get", record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						detail.editRecord(record);
						detail.getValuesManager().clearErrors(true);
						if (callback != null) {
							callback.execute(response, null, new DSRequest());
						}
					}
				}
			}, new DSRequest());
		}
	}

	public void newMode() {
		manageNewMode(false);
	}
	
	public void manageNewMode(boolean skipDetailNewMode) {
		this.mode = "new";
		showDetail();
		if(!skipDetailNewMode) {
			detail.newMode();
		}
		if (fullScreenDetail && !showOnlyDetail) {
			backToListButton.show();
			if (UserInterfaceFactory.isAttivaAccessibilita()) {
				nascondiBackButtonAccessibilita();
			}
		} else {
			backToListButton.hide();
		}
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();
		deleteButton.hide();
		altreOpButton.hide();
		lookupButton.hide();
	}
	
	// PER ACCESSIBILITA : METODO PER NASCONDERE IL BOTTONE BACK SE SI ENTRA IN UNA MASCHERA CON IL TASTO "INDIETRO" GIA' INTEGRATO ES: LookupOggettarioPopup
	public void nascondiBackButtonAccessibilita() {
		
	}
	
	// PER ACCESSIBILITA : METODO PER MOSTRARE IL BOTTONE BACK QUANDO SI RITORNA DA UNA MASCHERA CON IL TASTO "INDIETRO" GIA' INTEGRATO ES: LookupOggettarioPopup
	public void mostraBackButtonAccessibilita() {
		
	}
	
	public void viewMode() {
		this.mode = "view";
		showDetail();
		detail.viewMode();
		if (fullScreenDetail && !showOnlyDetail) {
			backToListButton.show();
		} else {
			backToListButton.hide();
		}
		if (multiselect) {
			editButton.hide();
		} else {
			editButton.show();
		}
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();
		deleteButton.show();
		altreOpButton.show();
		if (isLookup()) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
	}
	
	public void editMode() {
		editMode(false);
	}

	public void editMode(boolean fromViewMode) {		
		this.mode = "edit";
		this.fromViewMode = fromViewMode;
		showDetail();
		detail.editMode();		
		if (fullScreenDetail && !showOnlyDetail) {
			backToListButton.show();
		} else {
			backToListButton.hide();
		}
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
		deleteButton.hide();
		altreOpButton.hide();
		lookupButton.hide();
	}
	
	/*
	public void manageOnClickFiltriTipologiaButton() {
	
	}
	*/
	
	public void manageOnClickSearchButton() {
		list.setCurrentRecord(null);
		if (filter.getNomeEntita().equals("posta_elettronica") || filter.getNomeEntita().equals("archivio")) {
			HashSet<String> alertMessagesRequested = new HashSet<>();	
			// prima di fare la ricerca verifico che non ci siano alert da dare sui filtri selezionati dall'utente: inizio da -1 così per il primo giro verifico
			// che ci sia un filtro data
			AdvancedCriteria lAdvancedCriteria = filter.isVisible() ? filter.getCriteria() : searchCriteria;
			checkFilterAlertMessages(filter.getNomeEntita(), lAdvancedCriteria, new Integer(-1), alertMessagesRequested);
		} else {
			// qui ci passo solo quando faccio una nuova ricerca cliccando il bottone, perciò devo resettare il numero di pagina
			if(nroPaginaItem != null) {
				nroPaginaItem.setValue("");
				nroPaginaItem.hide();
			}
			// tutti i criteria sono stati processati. Si procede all'esecuzione del search.
			doSearch();
		}
		
	}

	/**
	 * Il metodo restituisce un boolean che permette eventualmente di inibire la ricerca
	 * 
	 * @param criteria
	 * @return
	 */
	public boolean beforeSearch(AdvancedCriteria criteria) {
		return true;
	};

	public void doSearchAndSelectRecords(int[] recordsToSelect) {
		list.setRecordsToSelect(recordsToSelect);
		doSearch();
	}

	public void doSearch() {
		AdvancedCriteria lAdvancedCriteria = filter.isVisible() ? filter.getCriteria() : searchCriteria;
//		final JSONEncoder encoder = new JSONEncoder();
//		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
//		String criteriaStr = JSON.encode(filter.getCriteria().getJsObj(), encoder);
//		SC.say(criteriaStr);
		validateFilter(lAdvancedCriteria);
	}

	protected void validateFilter(final AdvancedCriteria lAdvancedCriteria) {
		Record lRecord = new Record();
		lRecord.setAttribute("name", filter.getNomeEntita());
		lRecord.setAttribute("criteria", lAdvancedCriteria);
		OneCallGWTRestService<Record, Record> lOneCallFiltriObbligatoriDatasource = new OneCallGWTRestService<Record, Record>("FiltriObbligatoriDatasource");
		lOneCallFiltriObbligatoriDatasource.setForceToShowPrompt(false);
		lOneCallFiltriObbligatoriDatasource.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (object.getAttributeAsBoolean("valid"))
					doSearch(lAdvancedCriteria);
				else
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().requiredFieldError_message(), "", MessageType.ERROR));
			}
		});
	}

	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if(!"finalita".equals(crit.getFieldName()) && 
				   !"maxRecordVisualizzabili".equals(crit.getFieldName()) && 
				   !"nroRecordXPagina".equals(crit.getFieldName()) && 
				   !"nroPagina".equals(crit.getFieldName())) {
					criterionList.add(crit);
				}
			}
		}

		if (finalita != null && !"".equals(finalita)) {
			criterionList.add(new Criterion("finalita", OperatorId.EQUALS, finalita));
		}
		
		if(showMaxRecordVisualizzabiliItem() && maxRecordVisualizzabiliItem != null && maxRecordVisualizzabiliItem.getValueAsString() != null && !maxRecordVisualizzabiliItem.getValueAsString().equalsIgnoreCase("")) {
			int maxRecordVisualizzabili = Integer.valueOf((String) maxRecordVisualizzabiliItem.getValue());
			if (maxRecordVisualizzabili > 0){
				criterionList.add(new Criterion("maxRecordVisualizzabili", OperatorId.EQUALS, maxRecordVisualizzabiliItem.getValueAsString()));	
			} else {
				maxRecordVisualizzabiliItem.setValue("");
			}
		}
		
		if(showPaginazioneItems() && nroRecordXPaginaItem != null && nroRecordXPaginaItem.getValueAsString() != null && !nroRecordXPaginaItem.getValueAsString().equalsIgnoreCase("")){
			int nroRecordXPagina = Integer.valueOf((String) nroRecordXPaginaItem.getValue());
			if (nroRecordXPagina > 0){
				criterionList.add(new Criterion("nroRecordXPagina", OperatorId.EQUALS, nroRecordXPaginaItem.getValueAsString()));	
			} else {
				nroRecordXPaginaItem.setValue("");
			}
		}
		
		if(showPaginazioneItems() && nroPaginaItem != null && nroPaginaItem.getValueAsString() != null && !nroPaginaItem.getValueAsString().equalsIgnoreCase("")){
			int nroPagina = Integer.valueOf((String) nroPaginaItem.getValue());
			if (nroPagina > 0){
				criterionList.add(new Criterion("nroPagina", OperatorId.EQUALS, nroPaginaItem.getValueAsString()));	
			} else {
				nroPaginaItem.setValue("");
			}
		}
		
		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return new AdvancedCriteria(OperatorId.AND, criterias);
	}

	public void doSearch(AdvancedCriteria criteria) {
		final AdvancedCriteria searchCriteria = buildSearchCriteria(criteria);
		HashSet<String> fieldNames = new HashSet<String>();
		AdvancedCriteria criteriaIncludeEmptyValue = null;
		criteriaIncludeEmptyValue = filter.getCriteria(true);
		if (criteriaIncludeEmptyValue != null && criteriaIncludeEmptyValue.getCriteria() != null) {
			for (Criterion lCriterion : criteriaIncludeEmptyValue.getCriteria()) {
				if (!fieldNames.contains(lCriterion.getFieldName())) {
					fieldNames.add(lCriterion.getFieldName());
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().duplicateFilter_message(), "", MessageType.ERROR));
					return;
				}
			}
		}
		if  (beforeSearch(searchCriteria) && !showOnlyDetail) {
			doSimpleSearch(searchCriteria);
		}
	}

	/**
	 * 
	 * @param nomeEntita
	 * @param lAdvancedCriteria
	 * @param posCriteria
	 * @param alertMessagesRequested
	 * 
	 * Verifica che non ci siano alert da dare all'utente per i filtri selezionati
	 */
	private void checkFilterAlertMessages(final String nomeEntita, final AdvancedCriteria lAdvancedCriteria, final int posCriteria, final Set<String> alertMessagesRequested) {
		// Filtri selezionati dall'utente 
		Criterion[] criterions = lAdvancedCriteria.getCriteria();

		if (posCriteria < criterions.length) {
			// non abbiamo ancora processato tutti i criteria.
			
			if (posCriteria == -1) {
				// sono al primo giro, verifico che ci sia almeno un filtro data o equivalente
				if (!hasDataFilter(criterions, nomeEntita) && !ignoreDateAlert(criterions, nomeEntita)) {
					String messageForAsk = "";
					if ("posta_elettronica".equals(nomeEntita)) {
						messageForAsk = I18NUtil.getMessages().beforeSearch_postaElettronica_dataInvio_meseMax();
					} else if ("archivio".equals(nomeEntita)) {
						messageForAsk = I18NUtil.getMessages().beforeSearch_archivio_data_meseMax();
					}
					addToRequestedMessagesAndAsk(nomeEntita, lAdvancedCriteria, posCriteria, alertMessagesRequested, messageForAsk);
				} else {
					int newIndex = posCriteria + 1;
					checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex, alertMessagesRequested);
				}
			} else { 
				final Criterion currentCriterion = criterions[posCriteria];
				String currentFieldName = currentCriterion.getFieldName();
				
				if ("classifica".equals(currentFieldName) 
						|| "maxRecordVisualizzabili".equals(currentFieldName) 
						|| "nroRecordXPagina".equals(currentFieldName) 
						|| "nroPagina".equals(currentFieldName) 
						|| "searchFulltext".equals(currentFieldName)) {
					// salto i filtri di default e passo al prossimo
					int newIndex = posCriteria + 1;
					checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex, alertMessagesRequested);

				} else if ("posta_elettronica".equals(nomeEntita)) {
					// criteria relativi ai filtri della posta elettronica
					if ("dataInvio".equals(currentFieldName)) {
							
						// verifico se il filtro dataInvio specifica un range maggiore di un mese (30gg)
						if (checkOverDateLimit(currentCriterion, 31) && !ignoreDateAlert(criterions, nomeEntita)) {
							// il filtro supera il range di un mese
							String messageForAsk = I18NUtil.getMessages().beforeSearch_postaElettronica_dataInvio_meseMax();
							if(alertMessagesRequested.contains(messageForAsk)) {
								// il messaggio è già stato richiesto all'utente. Si procede a processare il criteria successivo.
								int newIndex = posCriteria + 1;
								checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
							} else {
								addToRequestedMessagesAndAsk(nomeEntita, lAdvancedCriteria, posCriteria, alertMessagesRequested, messageForAsk);
							}
						} else {
							// il filtro NON supera il range di un mese, si procede a processare il criteria successivo.
							int newIndex = posCriteria + 1;
							checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
						}

					} else if ("ricercaMailArchiviate".equals(currentFieldName)) {
						// Se ho il filtro "Ricerca sulle e-mail archiviate" do alert
						
						String messageForAsk = null;
						String value = UserInterfaceFactory.getParametroDB("CLIENTE");
						if(value != null && !"".equals(value) && "CMMI".equals(value)) {
							messageForAsk = I18NUtil.getMessages().beforeSearch_postaElettronica_ricercaMailArchiviate("armadio");
						} else {
							messageForAsk = I18NUtil.getMessages().beforeSearch_postaElettronica_ricercaMailArchiviate("archivio");
						}
						 
						if(alertMessagesRequested.contains(messageForAsk)) {
							// il messaggio è già  stato richiesto all'utente. Si procede a processare il criteria successivo.
							int newIndex = posCriteria + 1;
							checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
						} else {
							addToRequestedMessagesAndAsk(nomeEntita, lAdvancedCriteria, posCriteria, alertMessagesRequested, messageForAsk);
						}
					} else if (isRicercaTestuale(currentCriterion)) {
						// se sto facendo una ricerca testuale verifico che il testo della ricerca non sia inferiore ai 5 caratteri 
						// e il tipo di operatore
						checkRicercaTestuale(nomeEntita, lAdvancedCriteria, posCriteria, alertMessagesRequested, currentCriterion);
					} else {
						// se non è nessuno di questi passo si procede a processare il criteria successivo.
						int newIndex = posCriteria + 1;
						checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
					}
				} else if ("archivio".equals(nomeEntita)) {
					// criteria relativi ai filtri dell'archivio ricerca documenti e fascicoli
					if ("altraNumerazioneData".equals(currentFieldName) || "tsRegistrazione".equals(currentFieldName) 
							|| "tsBozza".equals(currentFieldName) || "tsAperturaFascicolo".equals(currentFieldName) ) {

						if (checkOverDateLimit(currentCriterion, 31) && !ignoreDateAlert(criterions, nomeEntita)) {
							// il filtro supera il range di un mese
							String messageForAsk = I18NUtil.getMessages().beforeSearch_archivio_data_meseMax();
							if(alertMessagesRequested.contains(messageForAsk)) {
								// il messaggio è già stato richiesto all'utente. Si procede a processare il criteria successivo.
								int newIndex = posCriteria + 1;
								checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
							} else {
								addToRequestedMessagesAndAsk(nomeEntita, lAdvancedCriteria, posCriteria, alertMessagesRequested, messageForAsk);
							}
						} else {
							// il filtro NON supera il range di un mese, si procede a processare il criteria successivo.
							int newIndex = posCriteria + 1;
							checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
						}

					} else if (isRicercaTestuale(currentCriterion)) {
						// se sto facendo una ricerca testuale verifico che il testo della ricerca non sia inferiore ai 5 caratteri
						checkRicercaTestuale(nomeEntita, lAdvancedCriteria, posCriteria, alertMessagesRequested, currentCriterion);

					}  else {
						// se non è nessuno di questi passo si procede a processare il criteria successivo.
						int newIndex = posCriteria + 1;
						checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
					}
				} else {
					int newIndex = posCriteria + 1;
					checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alertMessagesRequested);
				}
			}
		} else {
			// qui ci passo solo quando faccio una nuova ricerca cliccando il bottone, perciò devo resettare il numero di pagina
			if(nroPaginaItem != null) {
				nroPaginaItem.setValue("");		
				nroPaginaItem.hide();
			}
			// tutti i criteria sono stati processati. Si procede all'esecuzione del search.
			doSearch();
		}
	}
	
	/**
	 * 
	 * @param criterions
	 * @return 
	 * 
	 * metodo che indica la presenza di almeno un filtro data
	 */
	private boolean hasDataFilter(Criterion[] criterions, String nomeEntita) {
		Map<String, OperatorId> fields =  new HashMap<>();

		// riverso i nomi dei criteria in un set
		for (Criterion criterion : criterions) {
			fields.put(criterion.getFieldName(), criterion.getOperator());
		}

		if("posta_elettronica".equals(nomeEntita)) {
			if(fields.containsKey("dataInvio")) {
				return true;
			} 
		} else if ("archivio".equals(nomeEntita)) {
			if(fields.containsKey("tsRegistrazione") || fields.containsKey("tsBozza") 
					|| fields.containsKey("altraNumerazioneData") || fields.containsKey("tsAperturaFascicolo")) {
				return true;
			} 
		}
		
		return false;
	}

	/**
	 * 
	 * @param lAdvancedCriteria
	 * @param nomeEntita
	 * @return false se non devo ignorare l'alert, true se lo devo ignorare
	 * 
	 * Verifico se devo ignorare l'alert sulla data che supera un intervallo massimo di un mese, quindi verifico se sono contestualmente
	 * in presenza di filtri più restrittivi
	 */
	private boolean ignoreDateAlert(Criterion[] criterions, String nomeEntita) {
		boolean ignore = false;
		Map<String, OperatorId> fields =  new HashMap<>();
		Map<String, Map> values =  new HashMap<>();

		// riverso i nomi dei criteria in una mappa
		for (Criterion criterion : criterions) {
			fields.put(criterion.getFieldName(), criterion.getOperator());
			values.put(criterion.getFieldName(), criterion.getValues());
		}
		
		int maxRangeSearcByNumNoAlert = Layout.getGenericConfig().getMaxRangeSearcByNumNoAlert();

		if("posta_elettronica".equals(nomeEntita)) {
			if (fields.containsKey("idMessaggio") && operatorIsEqual(fields, "idMessaggio")) {
				ignore = true;
			} 

			if (fields.containsKey("progressivo") && operatorIsEqual(fields, "progressivo")) {
				ignore = true;
			} 

			if ((fields.containsKey("mittente") && operatorIsEqual(fields, "mittente")) 
					|| (fields.containsKey("destinatario") && operatorIsEqual(fields, "destinatario"))) {
				ignore = true;
			} 
			
			return ignore;
			
		} else if ("archivio".equals(nomeEntita)) {
			
			if (fields.containsKey("nroProt") && operatorIsEqual(fields, "nroProt")) {
				ignore = true;
			} 
			
			if (fields.containsKey("nroProt") && operatorIsBetween(fields, "nroProt")) {
				Map map = values.get("nroProt");
				String stringStart = map.containsKey("start") && map.get("start") != null ? map.get("start").toString() : "";
				String stringEnd = map.containsKey("end") && map.get("end") != null ? map.get("end").toString() : "";
				int start=Integer.parseInt(stringStart != null && !stringStart.trim().equals("") ? stringStart : "1");  
				if (stringEnd != null && !stringEnd.trim().equals("")) {
					int end=Integer.parseInt(map.get("end").toString());  
					ignore = (end - start < maxRangeSearcByNumNoAlert);
				}
			} 
			
			if (fields.containsKey("nroBozza") && operatorIsEqual(fields, "nroBozza")) {
				ignore = true;
			} 

			if (fields.containsKey("nroBozza") && operatorIsBetween(fields, "nroBozza")) {
				Map map = values.get("nroBozza");
				String stringStart = map.containsKey("start") && map.get("start") != null ? map.get("start").toString() : "";
				String stringEnd = map.containsKey("end") && map.get("end") != null ? map.get("end").toString() : "";
				int start=Integer.parseInt(stringStart != null && !stringStart.trim().equals("") ? stringStart : "1");  
				if (stringEnd != null && !stringEnd.trim().equals("")) {
					int end=Integer.parseInt(map.get("end").toString());  
					ignore = (end - start < maxRangeSearcByNumNoAlert);
				}
			} 
			
			if (fields.containsKey("altraNumerazioneNr") && operatorIsEqual(fields, "altraNumerazioneNr")) {
				ignore = true;
			} 
			
			if (fields.containsKey("altraNumerazioneNr") && operatorIsBetween(fields, "altraNumerazioneNr")) {
				Map map = values.get("altraNumerazioneNr");
				String stringStart = map.containsKey("start") && map.get("start") != null ? map.get("start").toString() : "";
				String stringEnd = map.containsKey("end") && map.get("end") != null ? map.get("end").toString() : "";
				int start=Integer.parseInt(stringStart != null && !stringStart.trim().equals("") ? stringStart : "1");  
				if (stringEnd != null && !stringEnd.trim().equals("")) {
					int end=Integer.parseInt(map.get("end").toString());  
					ignore = (end - start < maxRangeSearcByNumNoAlert);
				}
			} 
			
			if (fields.containsKey("nroFascicolo") && operatorIsEqual(fields, "nroFascicolo")) {
				ignore = true;
			}
			
			if (fields.containsKey("nroFascicolo") && operatorIsBetween(fields, "nroFascicolo")) {
				Map map = values.get("nroFascicolo");
				String stringStart = map.containsKey("start") && map.get("start") != null ? map.get("start").toString() : "";
				String stringEnd = map.containsKey("end") && map.get("end") != null ? map.get("end").toString() : "";
				int start=Integer.parseInt(stringStart != null && !stringStart.trim().equals("") ? stringStart : "1");  
				if (stringEnd != null && !stringEnd.trim().equals("")) {
					int end=Integer.parseInt(map.get("end").toString());  
					ignore = (end - start < maxRangeSearcByNumNoAlert);
				}
			} 
			
			return ignore;
		
		} else {
			return true;
		}
	}

	private boolean operatorIsEqual(Map<String, OperatorId> fields, String nomeFiltro) {
		return fields.get(nomeFiltro).equals(OperatorId.EQUALS) || fields.get(nomeFiltro).equals(OperatorId.EQUALS_FIELD) 
				|| fields.get(nomeFiltro).equals(OperatorId.IEQUALS) || fields.get(nomeFiltro).equals(OperatorId.IEQUALS_FIELD);
	}
	
	private boolean operatorIsBetween(Map<String, OperatorId> fields, String nomeFiltro) {
		return fields.get(nomeFiltro).equals(OperatorId.BETWEEN_INCLUSIVE);
	}

	protected void checkRicercaTestuale5chars(final String nomeEntita, final AdvancedCriteria lAdvancedCriteria,
			final int posCriteria, final Set<String> alreadyRequestedMessages, final Record record) {
		String messageForAsk = I18NUtil.getMessages().beforeSearch_postaElettronica_ricercaTestualeMinore5caratteri();
		Boolean toAsk = false;

		if (record.getAttributeAsString("parole") != null ) {
			if (record.getAttributeAsString("parole").length() < 5) {
				toAsk = true;
			}
		} else {
			if (record.getJsObj() != null && record.getJsObj().toString().length() < 5 ) {
				toAsk = true;
			}
		}

		if(!toAsk || alreadyRequestedMessages.contains(messageForAsk)) {
			int newIndex = posCriteria + 1;
			checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alreadyRequestedMessages);
		} else if (toAsk){
			addToRequestedMessagesAndAsk(nomeEntita, lAdvancedCriteria, posCriteria, alreadyRequestedMessages,
					messageForAsk);
		}
	}

	private boolean isRicercaTestuale(Criterion pCriterion) {
		FilterType fieldName = filter.getMappaFields().get(pCriterion.getFieldName());
		return fieldName.equals(FilterType.stringa_full_text)
				|| fieldName.equals(FilterType.stringa_full_text_mista)
				|| fieldName.equals(FilterType.stringa_full_text_restricted)
				|| fieldName.equals(FilterType.stringa_ricerca_complessa_1)
				|| fieldName.equals(FilterType.stringa_ricerca_esatta)
				|| fieldName.equals(FilterType.stringa_ricerca_estesa_case_insensitive_1)
				|| fieldName.equals(FilterType.stringa_ricerca_estesa_case_insensitive_2)
				|| fieldName.equals(FilterType.stringa_ricerca_ristretta)
				|| fieldName.equals(FilterType.combo_box)
				|| fieldName.equals(FilterType.stringa_full_text_mista_ricerca_lookup);
	}
	
	private boolean isTextFilterToSkip(Criterion pCriterion) {
		return pCriterion.getFieldName().equalsIgnoreCase("codiceFascicolo") 
				|| pCriterion.getFieldName().equalsIgnoreCase("docCollegatiProt")
				|| pCriterion.getFieldName().equalsIgnoreCase("capoFilaFasc")
				|| pCriterion.getFieldName().equalsIgnoreCase("nroProtRicevuto")
				|| pCriterion.getFieldName().equalsIgnoreCase("nroRaccomandata");
	}

	protected void checkRicercaTestuale(final String nomeEntita, final AdvancedCriteria lAdvancedCriteria,
			final int posCriteria, final Set<String> alreadyRequestedMessages, final Criterion pCriterion) {

		final Record record = pCriterion.getAttributeAsRecord("value");

		if (record != null) {

			if (pCriterion.getOperator().equals(OperatorId.LIKE)) {
				// se l'operatore è CONTIENE do alert all'utente che la ricerca non è efficiente e che è consigliabile cambiare operatore,
				// verifico poi che la ricerca testuale venga fatta su più di 5 caratteri

				String FilterTitleContiene = "<font color=\"red\">"+"«contiene»"+"</font>";
				String messageForAskLike = I18NUtil.getMessages().beforeSearch_postaElettronica_ricercaTestualeContains(FilterTitleContiene);	
				
				Layout.showConfirmDialogWithWarning("Attenzione!", messageForAskLike, "Si", "No<", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value != null && value) {
							// se l'utente risponde positivamente cambio l'operatore del filtro da CONTIENE a PAROLE CHE INIZIANO PER
							changecriteria(pCriterion, lAdvancedCriteria);
						} 
						if (pCriterion.getOperator() != OperatorId.EQUALS && pCriterion.getOperator() != OperatorId.IEQUALS && 
									!isTextFilterToSkip(pCriterion)) {
							// verifico che la ricerca testuale venga fatta su più di 5 caratteri
							checkRicercaTestuale5chars(nomeEntita, lAdvancedCriteria, posCriteria, alreadyRequestedMessages, record);
						} else {
							int newIndex = posCriteria + 1;
							checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alreadyRequestedMessages);
						}
					}
				});				
			} else {
				
				if (pCriterion.getOperator() != OperatorId.EQUALS && pCriterion.getOperator() != OperatorId.IEQUALS && !isTextFilterToSkip(pCriterion)) {
					// verifico che la ricerca testuale venga fatta su più di 5 caratteri
					checkRicercaTestuale5chars(nomeEntita, lAdvancedCriteria, posCriteria, alreadyRequestedMessages, record);
				} else {
					int newIndex = posCriteria + 1;
					checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alreadyRequestedMessages);
				}
			}
		} else {
			int newIndex = posCriteria + 1;
			checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alreadyRequestedMessages);
		}
	}

	/**
	 * 
	 * @param nomeEntita
	 * @param lAdvancedCriteria
	 * @param posCriteria
	 * @param alreadyRequestedMessages
	 * @param messageForAsk
 	 * 
 	 * alert non ancora mostrato all'utente. Si procede ad inserirlo nella lista dei messaggi già richiesti e si richiede la domanda all'utente.
 	 * 
	 */
	protected void addToRequestedMessagesAndAsk(final String nomeEntita, final AdvancedCriteria lAdvancedCriteria,
			final int posCriteria, final Set<String> alreadyRequestedMessages, String messageForAsk) {

		alreadyRequestedMessages.add(messageForAsk);
		
		Layout.showConfirmDialogWithWarning("Attenzione!", messageForAsk, "Si", "No", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value != null && value) {
					int newIndex = posCriteria + 1;
					checkFilterAlertMessages(nomeEntita, lAdvancedCriteria, newIndex,alreadyRequestedMessages);
				} else {
					Layout.addMessage(
							new MessageBean("Ricerca interrotta dall'utente", "", MessageType.INFO));
				}
			}
		});

	}

	protected Boolean checkOverDateLimit(final Criterion pCriterion, int daysLimit) {
		Boolean toAsk = false;
		if (OperatorId.GREATER_THAN == pCriterion.getOperator() || OperatorId.GREATER_OR_EQUAL == pCriterion.getOperator()) {
			// caso del filtro "Data di invio maggiore di"
			Record valueCriteria = pCriterion.getAttributeAsRecord("value");
			if (valueCriteria.getAttributeAsDate("data") != null) {
				toAsk = valueCriteria.getAttributeAsDate("data") != null
						&& differenceDaysByDate(new Date(), valueCriteria.getAttributeAsDate("data"), daysLimit);
			} else {
				toAsk = true;
			}
		
		} else if (OperatorId.BETWEEN_INCLUSIVE == pCriterion.getOperator()) {
			Record startValue = pCriterion.getAttributeAsRecord("start");
			Record endValue = pCriterion.getAttributeAsRecord("end");
			if (startValue != null && endValue != null) {
				Date startDate = startValue.getAttributeAsDate("data") != null ? startValue.getAttributeAsDate("data") : pCriterion.getAttributeAsDate("start");	
				Date endDate = endValue.getAttributeAsDate("data") != null ? endValue.getAttributeAsDate("data") : pCriterion.getAttributeAsDate("end");
				if (startDate == null) {
					toAsk = true;
				} else {
					if (endDate == null) {
						toAsk = differenceDaysByDate(startDate, new Date(), daysLimit);
					} else {
						toAsk = differenceDaysByDate(startDate, endDate, daysLimit);
					}
				}				
			} else if (startValue != null && endValue == null) {
				Date startDate = startValue.getAttributeAsDate("data") != null ? startValue.getAttributeAsDate("data") : pCriterion.getAttributeAsDate("start");
				if (startDate == null) {
					toAsk = true;
				} else {
					toAsk = differenceDaysByDate(startDate, new Date(), daysLimit);
				}				
			} else if (startValue == null) {
				toAsk = true;
			}
		} else if (OperatorId.LESS_THAN == pCriterion.getOperator() || OperatorId.LESS_OR_EQUAL == pCriterion.getOperator()) {
			toAsk = true;
		} else if (OperatorId.LAST_N_DAYS == pCriterion.getOperator()) {
				int ultimiNGiorni;
				Record currentValueRecord = pCriterion.getAttributeAsRecord("value");
				if (currentValueRecord != null&& currentValueRecord.getAttribute("nGiorni") != null) {
					ultimiNGiorni = currentValueRecord.getAttributeAsInt("nGiorni");
					toAsk = ultimiNGiorni > daysLimit;
				} else {
					toAsk = true;
				}
		}
		return toAsk;
	}
	
	protected void changecriteria(Criterion pCriterion, AdvancedCriteria lAdvancedCriteria) {
		Criterion newCriterion = new Criterion();
		newCriterion = pCriterion;
		newCriterion.setOperator(OperatorId.WORDS_START_WITH);
		
		AdvancedCriteria initialCriteria = filter.getCriteria(true);
		Criterion[] modifiedCriterion = initialCriteria.getCriteria();


		for (int i = 0; i < modifiedCriterion.length; i++) {
			if (modifiedCriterion[i].getFieldName().equals(newCriterion.getFieldName())) {
				modifiedCriterion[i] = newCriterion;
			}
		}

		AdvancedCriteria newCriteria = new AdvancedCriteria();
		newCriteria.setCriteria(modifiedCriterion);		
		filter.setCriteria(newCriteria);
		
	}
	
	private String uuidAttuale;

	public void doSimpleSearch(AdvancedCriteria criteria) {
		if (isStoppableSearch()) {
			getDatasource().setShowPrompt(false);
			int lInt = Random.nextInt();
			uuidAttuale = lInt + "";
			getDatasource().extraparam.put("uuid", uuidAttuale);
			stopSearchButton.show();
		}

		if(showPaginazioneItems()) {
			if(nroPaginaFirstButton != null) {
				nroPaginaFirstButton.disable();
			}
			if(nroPaginaPrevButton != null) {
				nroPaginaPrevButton.disable();	
			}
			if(nroPaginaNextButton != null) {
				nroPaginaNextButton.disable();	
			}
			if(nroPaginaLastButton != null) {
				nroPaginaLastButton.disable();
			}
		}
		
		GWTRestDataSource.settingFetchReferences(this, getDatasource().getServerid());

		list.reloadFieldsFromCriteria(criteria);
		list.deselectAllRecords();
		list.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		list.clearSelezione();
		list.clearCheckBoxList();
		if (list.isGrouped()) {
			list.ungroup();
		}
		list.invalidateCache();
		try {
			list.getResultSet().setCriteria(criteria);
		} catch (Exception e) {
		}
		if (list.getDataAsRecordList().getLength() == 0) {
			list.fetchData(criteria);
		}
		getDatasource().extraparam.put("uuid", uuidAttuale);
		detail.getValuesManager().clearValues();
		isFetched = true;
		filtroAttivoImg.hide();
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion lCriterion : criteria.getCriteria()) {
				if (lCriterion.getValueAsString() != null && !"".equals(lCriterion.getValueAsString())) {
					// Commento questa parte di codice altrimenti se rifaccio una ricerca e i filtri sono nascosti vengono passati vuoti, invece devo ripetere
					// l'ultima ricerca fatta
					// if(filterLayout.isVisible()) {
					// searchCriteria = filter.getCriteria(true);
					// } else {
					// searchCriteria = null;
					// }
					filtroAttivoImg.show();
					break;
				}
			}
		}
		searchCriteria = criteria;
		setSearchCriteria(searchCriteria);
	}

	public boolean isFetched() {
		return isFetched;
	}

	public boolean isLookup() {
		return lookup;
	}

	public void setLookup(final Boolean lookup) {
		this.lookup = lookup;
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					list.refreshFields();
				} catch (Exception e) {
				}
				list.markForRedraw();
			}
		});
	}

	public boolean isMultiLookup() {
		return multiLookup;
	}

	public void setMultiLookup(Boolean multiLookup) {
		this.multiLookup = multiLookup;
		if (multiLookup != null && multiLookup) {
			multiLookupLayout.show();
		} else {
			multiLookupLayout.hide();
		}
		setLookup(multiLookup);
	}

	public void delete(final Record record) {
		SC.ask(getDeleteButtonAskMessage(), new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {
					list.removeData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(getTipoEstremiRecord(record)), "",
										MessageType.INFO));
								hideDetail(true);
								if(showPaginazioneItems()) {
									numRecords--;
								}
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(getTipoEstremiRecord(record)), "",
										MessageType.ERROR));
							}
							refreshNroRecord();
						}
					});
				}
			}
		});
	}

	protected String getDeleteButtonAskMessage() {
		return I18NUtil.getMessages().deleteButtonAsk_message();
	}

	// da implementare nella classe che estende CustomLayout: serve a costruire il record per la lookup multipla (3 attributi: id, icona e nome)
	protected Record createMultiLookupRecord(Record record) {
		return record;
	}

	public void doLookup(Record record) {
		if (multiLookup) {
			doMultiLookup(record);
		} else {
			lookupBack(record);
		}
	}

	protected void doMultiLookup(Record record) {
		if (multiLookupGrid.addRecord(createMultiLookupRecord(record))) {
			multiLookupBack(record);
		}
	}

	// da implementare nella classe che estende CustomLayout: contiene la logica di ritorno dalla lookup
	public void lookupBack(Record record) {

	}

	// da implementare nella classe che estende CustomLayout: contiene la logica di ritorno dalla lookup multipla
	public void multiLookupBack(Record record) {

	}

	// da implementare nella classe che estende CustomLayout: contiene la logica per rimuovere un record precedentemente selezionato dalla lookup multipla
	public void multiLookupUndo(Record record) {

	}

	public boolean showMultiselectButtonsUnderList() {
		return true;
	}

	public void setMultiselect(Boolean multiselect) {
		this.multiselect = multiselect;
		list.setShowMultiSelect(multiselect);
		if (multiselect) {
			list.deselectAllRecords();
			list.setShowSelectedStyle(false);
			multiselectButton.setIcon("buttons/multiselect_off.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOffButton_prompt());
			if (list.isDisableRecordComponent() && !UserInterfaceFactory.isAttivaAccessibilita()) {
				list.setSelectionAppearance(SelectionAppearance.CHECKBOX);
				list.setSelectionType(SelectionStyle.SIMPLE);
			} else {
				list.showField("selectRowAccessibleField");
				list.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
				list.setSelectionType(SelectionStyle.NONE);
			}
			// newButton.hide();
			// setDetailAuto(false);
			// formatoExportSelectItem.hide();
			// exportButton.hide();
		} else {
			list.deselectAllRecords();
			list.setCurrentRecord(null);
			if (UserInterfaceFactory.isAttivaAccessibilita()) {
				list.hideField("selectRowAccessibleField");
			}
			hideDetail();
			list.setShowSelectedStyle(true);
			multiselectButton.setIcon("buttons/multiselect.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
			list.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
			list.setSelectionType(SelectionStyle.SINGLE);
			// newButton.show();
			// setDetailAuto(true);
			// formatoExportSelectItem.show();
			// exportButton.show();
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				// try {
				// list.refreshFields();
				// } catch(Exception e) {
				// }
				redrawMultiselectButtons();
				list.markForRedraw();
			}
		});
	}

	public void redrawMultiselectButtons() {
		if (multiselect) {
			for (MultiToolStripButton lToolStripButton : getMultiselectButtons()) {
				if (lToolStripButton.toShow() && showMultiselectButtonsUnderList()) {
					lToolStripButton.show();
				} else {
					lToolStripButton.hide();
				}
			}
		} else {
			for (MultiToolStripButton lToolStripButton : getMultiselectButtons()) {
				lToolStripButton.hide();
			}
		}
	}

	public Boolean getMultiselect() {
		return multiselect;
	}

	public void setDetailAuto(Boolean detailAuto) {
		// if(!fullScreenDetail) {
		this.detailAuto = detailAuto;
		if (detailAuto) {
			detailAutoButton.setIcon("buttons/detail_off.png");
			detailAutoButton.setPrompt(I18NUtil.getMessages().detailAutoOffButton_prompt());
		} else {
			detailAutoButton.setIcon("buttons/detail.png");
			detailAutoButton.setPrompt(I18NUtil.getMessages().detailAutoOnButton_prompt());
			hideDetail();
		}
		// }
	}

	public Boolean getDetailAuto() {
		return detailAuto;
	}

	public void showHideDetailItems() {
	}

	public void redrawTitleOfPortlet() {
		if (fullScreenDetail) {
			String title = "";
			if (mode.equals("new")) {
				title = getNewDetailTitle();
			} else if (mode.equals("edit")) {
				title = getEditDetailTitle();
			} else if (mode.equals("view")) {
				title = getViewDetailTitle();
			}
			Layout.changeTitleOfPortlet(this.nomePortlet, title);
		}
	}

	public void showDetail() {
		this.show();
		if (this.mode != null) {
			redrawTitleOfPortlet();
			detailSection.setExpanded(true);
			showHideDetailItems();
		} else if (detail instanceof CustomDetailWithTabs) {
			((CustomDetailWithTabs) detail).getTabSet().selectTab(0);
		}
	}

	// public void showDetail2() {
	// boolean isDetailHidden = (this.mode == null);
	// this.show();
	// if(fullScreenDetail) {
	// String title = "";
	// if(mode != null) {
	// if(mode.equals("new")) {
	// title = getNewDetailTitle();
	// } else if(mode.equals("edit")) {
	// title = getEditDetailTitle();
	// } else if(mode.equals("view")) {
	// title = getViewDetailTitle();
	// }
	// }
	// Layout.changeTitleOfPortlet(this.nomePortlet, title);
	// searchLayout.hide();
	// showHideDetailItems();
	// // detail.show();
	// // detailToolStrip.show();
	// detailLayout.show();
	// detailLayout.setHeight100();
	// detailLayout.markForRedraw();
	// multiLookupLayout.hide();
	// } else {
	// showHideDetailItems();
	// // detail.show();
	// // detailToolStrip.show();
	// detailLayout.show();
	// multiLookupLayout.hide();
	// }
	// if(detail instanceof CustomDetailWithTabs && isDetailHidden) {
	// ((CustomDetailWithTabs) detail).getTabSet().selectTab(0);
	// }
	// }

	public void hideDetail() {
		hideDetail(false);
	}

	public void hideDetailAfterSave() {
		hideDetail(isFetched());
	}

	public void hideDetail(boolean reloadList) {
		this.show();
		if (this.mode != null) {
			mode = null;
			searchSection.setExpanded(true);
			if (fullScreenDetail) {
				Layout.changeTitleOfPortlet(this.nomePortlet, getBaseTitle());
				if (multiLookup) {
					multiLookupLayout.show();
				} else {
					multiLookupLayout.hide();
				}
			} else {
				multiLookupLayout.hide();
			}			
		}
		if (reloadList) {
			if(getReloadListCallback() != null) {
				getReloadListCallback().executeCallback();
			} else {
				Record record = new Record(detail.getValuesManager().getValues());
				reloadListAndSetCurrentRecord(record);
			}
		}
	}

	// public void hideDetail2(boolean reloadList) {
	// mode = null;
	// this.show();
	// detailLayout.hide();
	// // detail.hide();
	// // detailToolStrip.hide();
	// if(fullScreenDetail) {
	// searchLayout.show();
	// Layout.changeTitleOfPortlet(this.nomePortlet, getBaseTitle());
	// if(multiLookup) {
	// multiLookupLayout.show();
	// } else {
	// multiLookupLayout.hide();
	// }
	// } else {
	// multiLookupLayout.hide();
	// }
	// if(reloadList) {
	// Record record = new Record(detail.getValuesManager().getValues());
	// reloadListAndSetCurrentRecord(record);
	// }
	// }

	public void changeDetail(final GWTRestDataSource datasource, final CustomDetail pDetail) {

		if (this.detail != null) {
			// mi salvo sul nuovo dettaglio il fatto che sia stato effettuato il salvataggio della maschera,
			// in modo da abilitare eventualmente il ricaricamento della lista alla chiusura del dettaglio
			if (this.detail.isSaved()) {
				pDetail.setSaved(this.detail.isSaved());
			}	
			this.detail.markForDestroy();
		}

		this.detail = pDetail;

		this.detail.setLayout(this);

		this.detail.setDataSource(datasource);

		// filterLayout.setMembers(filterToolStrip, filter, filterButtons);
		// bodyListLayout.setMembers(list);
		// listLayout.setMembers(topListToolStrip, bodyListLayout, bottomListToolStrip);
		detailLayout.setMembers(detail, detailToolStrip);
		// searchLayout.setMembers(filterLayout, listLayout);
		// rightLayout.setMembers(searchLayout, multiLookupLayout);
		// mainLayout.setMembers(leftLayout, rightLayout);

		// filterLayout.markForRedraw();
		// bodyListLayout.markForRedraw();
		// listLayout.markForRedraw();
		detailLayout.markForRedraw();
		// searchLayout.markForRedraw();
		// rightLayout.markForRedraw();
		// mainLayout.markForRedraw();
		//
		// this.markForRedraw();
	}

	public String getBaseTitle() {
		MenuBean menu = Layout.getMenu(this.nomePortlet);
		return menu != null ? menu.getTitle() : "";
	}

	public String getNewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().newDetail_titlePrefix() + " " + getTipoEstremiRecord(record);
	}

	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().editDetail_titlePrefix() + " " + getTipoEstremiRecord(record);
	}

	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record);
	}

	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		int maxValueLenght = Layout.getGenericConfig().getColonneEstremiRecordMaxValueLength();
		List<String> colonneEstremiRecord = configLista.getColonneEstremiRecord();
		if(colonneEstremiRecord!=null) {
			for (String colonna : colonneEstremiRecord) {
				if (record.getAttribute(colonna) != null && !"".equals(record.getAttribute(colonna))) {
					if (maxValueLenght > 0 && record.getAttribute(colonna).length() > maxValueLenght)
						estremi += " " + record.getAttribute(colonna).substring(0, maxValueLenght);
					else
						estremi += " " + record.getAttribute(colonna);
				}
			}
		}
		return estremi;
	}

	public String getLookupButtonImage() {
		return "buttons/seleziona.png";
	}

	public String getLookupButtonImageDisabled() {
		return "buttons/seleziona_Disabled.png";
	}

	public String getLookupButtonPrompt() {
		return I18NUtil.getMessages().selezionaButton_prompt();
	}

	public void hideFiltroAttivoImg() {
		filtroAttivoImg.hide();
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Boolean getFullScreenDetail() {
		return fullScreenDetail;
	}

	public ConfigurableFilter getFilter() {
		return filter;
	}

	public CustomList getList() {
		return list;
	}

	public CustomDetail getDetail() {
		return detail;
	}

	public String getNomePortlet() {
		return nomePortlet;
	}

	public String getNomeEntita() {
		return nomeEntita;
	}

	public String getFinalita() {
		return finalita;
	}

	public Boolean getFlgSelezioneSingola() {
		return flgSelezioneSingola;
	}

	protected void init() {
	}

	public boolean getShowAvvioRicercaAutomatica() {
		return true;
	}

	public boolean getCanSetAsHomepage() {
		return true;
	}

	public void setPortlet(Portlet portlet) {
		owner = portlet;
	}

	public Portlet getPortlet() {
		return owner;
	}

	public void closeViewer() {

		for (Map.Entry<String, BaseWindow> entry : Layout.getOpenedViewers().entrySet()) {
			BaseWindow pw = entry.getValue();

			if (nomeEntita != null && nomeEntita.equals(pw.getPortletOpened())) {
				pw.destroy();
				Layout.getOpenedViewers().remove(entry.getKey());
			}
		}
	}

	// da implementare nella classe che estende CustomLayout: contiene la logica di ritorno dalla import multipla
	protected void multiImportBack(RecordList record) {
	}

	/**
	 * 
	 */
	protected void export() {

		final String formatoExport = formatoExportSelectItem.getValueAsString();

		if (list != null && list.getDataAsRecordList() != null && list.getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}

		if (formatoExport != null && !"".equals(formatoExport)) {

			boolean includeXord = formatoExport.equalsIgnoreCase("xls") || formatoExport.equalsIgnoreCase("xlsx") || formatoExport.equalsIgnoreCase("csv")
					|| formatoExport.equalsIgnoreCase("xml");

			if (list.isGrouped()) {

				Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "",
						MessageType.ERROR));

			} else {

				Record record = new Record();
				record.setAttribute("nomeEntita", getNomeEntita());
				record.setAttribute("formatoExport", formatoExport);
				record.setAttribute("csvSeparator", "|*|");
				record.setAttribute("criteria", list.getResultSet().getCriteria());

				LinkedHashMap<String, String> mappa = createFieldsMap(includeXord);

				String[] fields = new String[mappa.keySet().size()];
				String[] headers = new String[mappa.keySet().size()];
				int n = 0;
				for (String key : mappa.keySet()) {
					fields[n] = key;
					headers[n] = mappa.get(key);
					n++;
				}
				record.setAttribute("fields", fields);
				record.setAttribute("headers", headers);

				record.setAttribute("records", extractRecords(fields));

				record.setAttribute("overflow", overflow);
				record.setAttribute("paginazioneAttiva", isPaginazioneAttiva());

				DownloadExportList.downloadExportList(record, list);
			}
		} else {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file, selezionare il formato opportuno", "", MessageType.ERROR));
			return;
		}
	}

	/**
	 * Crea un array di records ciascuno avente solo i campi attualmente visibili valorizzati
	 * 
	 * @param fields
	 * @return
	 */
	protected Record[] extractRecords(String[] fields) {
		Record[] records = new Record[list.getRecords().length];
		for (int i = 0; i < list.getRecords().length; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				rec.setAttribute(fieldName, list.getRecords()[i].getAttribute(fieldName));
			}
			records[i] = rec;
		}
		return records;
	}

	/**
	 * @return
	 */
	protected LinkedHashMap<String, String> createFieldsMap(Boolean includeXord) {
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();

		for (int i = 0; i < list.getFields().length; i++) {

			ListGridField field = list.getFields()[i];
			String fieldName = field.getName();

			if (fieldName.endsWith("XOrd") && includeXord) {

				String fieldTitle = field.getTitle() + " (Ordinamento)";

				boolean isControlField = false;

				for (String currentControlField : list.getControlFields()) {
					if (currentControlField.equals(fieldName)) {
						isControlField = true;
						break;
					}
				}

				if (!isControlField && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
					mappa.put(fieldName, fieldTitle);
				}
			}

			if (fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();

			/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
			if (field.getDisplayField() != null)
				fieldName = field.getDisplayField();

			if (!list.getControlFields().contains(fieldName) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
				mappa.put(fieldName, fieldTitle);
			}
		}
		return mappa;
	}

	public boolean showExportListButton() {
		return UserInterfaceFactory.isAbilToExportList() && isAbilToExportList();	
	}

	public boolean isAbilToExportList() {
		return true;	
	}

	public ReloadListCallback getReloadListCallback() {
		return reloadListCallback;
	}

	public void setReloadListCallback(ReloadListCallback reloadListCallback) {
		this.reloadListCallback = reloadListCallback;
	}

	public boolean showMaxRecordVisualizzabiliItem() {
		return false;	
	}
	
	public boolean showPaginazioneItems() {
		return false;
	}

	public void setMaxRecordVisualizzabili(String value) {
		if (maxRecordVisualizzabiliItem != null){
			maxRecordVisualizzabiliItem.setValue(value);
		}
	}
	
	public String getMaxRecordVisualizzabili() {
		return maxRecordVisualizzabiliItem != null ? maxRecordVisualizzabiliItem.getValueAsString() : null;
	}
	
	public void setNroRecordXPagina(String value) {
		if (nroRecordXPaginaItem != null){
			if (UserInterfaceFactory.isAttivaAccessibilita() && value != null && value.equals("")){
				String defaultRecPagAcc = UserInterfaceFactory.getParametroDB("DEFAULT_NRO_RECORD_PAGINAZIONE_ACCESSIBILITA");
				nroRecordXPaginaItem.setValue(defaultRecPagAcc != null && !"".equals(defaultRecPagAcc) ? defaultRecPagAcc : "20");
			} else {
			nroRecordXPaginaItem.setValue(value);
		}
	}
	}

	public String getNroRecordXPagina() {
		return nroRecordXPaginaItem != null ? nroRecordXPaginaItem.getValueAsString() : null;
	}
	
	public void setNroPagina(String value) {
		if(nroPaginaItem != null) {
			nroPaginaItem.setValue(value);
		}
	}
	
	public String getNroPagina() {
		return nroPaginaItem != null ? nroPaginaItem.getValueAsString() : null;
	}
	
	public void manageAfterFilterChanged() {

	}

	public boolean showFunzGestioneSubordinati() {
		return false;
	}

	public void attivaFunzGestioneSubordinati() {
		funzGestioneSubordinatiButton.setPrompt("Attivata gestione estesa");
		funzGestioneSubordinatiButton.setDisabled(false);
		funzGestioneSubordinatiButton.setIcon("on.png");
		markForRedraw();
	}

	public void disattivaFunzGestioneSubordinati() {
		funzGestioneSubordinatiButton.setPrompt("Disattivata gestione estesa");
		funzGestioneSubordinatiButton.setDisabled(false);
		funzGestioneSubordinatiButton.setIcon("off.png");
		markForRedraw();
	}

	public static String getIdDominio() {
		if(Layout.getUtenteLoggato() != null) {
			if (Layout.getUtenteLoggato().getDominio() != null && Layout.getUtenteLoggato().getDominio().split(":").length == 2) {
				return Layout.getUtenteLoggato().getDominio().split(":")[1];
			} else {
				return Layout.getUtenteLoggato().getDominio();
			}
		}
		return null;
	}

	public static String getPrefKeySuffixSpecXDominio() {
		String idDominio = getIdDominio();
		return idDominio != null && !"".equals(idDominio) ? "." + idDominio : ""; 
	}


	/**
	 * 
	 * @param firstDate 
	 * @param secondDate
	 * @param targetDays 
	 * @return valore booleano indicante se la differenza tra le due date in giorni supera il numero di giorni considerato in input (targetDays)
	 */
	private boolean differenceDaysByDate(Date firstDate, Date secondDate, int targetDays) {
		long diffInDays = Math.abs(firstDate.getTime() - secondDate.getTime())/ (60*60*24*1000);
		return diffInDays >= targetDays;
	}

	@Override
	protected void onDestroy() {		
		if(datasource != null) {
			datasource.destroy();
		}
		if(saveRicercaPreferitaWindow != null) {
			saveRicercaPreferitaWindow.destroy();		
		}
		if(saveLayoutFiltroWindow != null) {
			saveLayoutFiltroWindow.destroy();
		}
		if(saveLayoutListaWindow != null) {
			saveLayoutListaWindow.destroy();
		}
		if(ricercaPreferitaDS != null) {
			ricercaPreferitaDS.destroy();
		}		
		if(ricercaPreferitaDefaultDS != null) {
			ricercaPreferitaDefaultDS.destroy();
		}
		if(layoutFiltroDS != null) {
			layoutFiltroDS.destroy();
		}
		if(layoutFiltroDefaultDS != null) {
			layoutFiltroDefaultDS.destroy();
		}
		if(layoutListaDS != null) {
			layoutListaDS.destroy();
		}
		if(layoutListaDefaultDS != null) {
			layoutListaDefaultDS.destroy();
		}
		if(autoSearchDS != null) {
			autoSearchDS.destroy();
		}
		if(autoSearchDefaultDS != null) {
			autoSearchDefaultDS.destroy();
		}
		if(layoutGestioneSubDS != null) {
			layoutGestioneSubDS.destroy();
		}
		super.onDestroy();
	}
	
	protected void setMaxRecordVisualizzabiliFromCriteria(AdvancedCriteria criteria) {
		if(showMaxRecordVisualizzabiliItem() && criteria != null) {
			for (int pos = 0; pos < criteria.getCriteria().length; pos++) {
				Criterion pCriterion = criteria.getCriteria()[pos];
				if (pCriterion.getFieldName() != null && pCriterion.getFieldName().equals("maxRecordVisualizzabili")) {
					setMaxRecordVisualizzabili(pCriterion.getValueAsString());
					break;
				}
			}
		}
	}
	
	protected void setNroRecordXPaginaFromCriteria(AdvancedCriteria criteria) {
		if(showPaginazioneItems() && criteria != null) {
			for (int pos = 0; pos < criteria.getCriteria().length; pos++) {
				Criterion pCriterion = criteria.getCriteria()[pos];
				if (pCriterion.getFieldName() != null && pCriterion.getFieldName().equals("nroRecordXPagina")) {
					setNroRecordXPagina(pCriterion.getValueAsString());
					break;
				}
			}
		}
	}
	
	protected AdvancedCriteria checkRetrocompatibilitaFiltri(AdvancedCriteria criteria) {
		AdvancedCriteria checkedCriteria = new AdvancedCriteria();
		for (int pos = 0; pos < criteria.getCriteria().length; pos++) {
			Criterion pCriterion = criteria.getCriteria()[pos];
			FilterType fieldName = filter.getMappaFields().get(pCriterion.getFieldName());
			if (fieldName != null) {
				if (fieldName.equals(FilterType.data_senza_ora_estesa)) {
					if (pCriterion.getAttributeAsRecord("start") != null ) {
						if (pCriterion.getAttributeAsRecord("start").getAttribute("data") == null) {
							Date dataToKeep = pCriterion.getAttributeAsDate("start");
							if (dataToKeep != null) {
								pCriterion.setAttribute("start", new Record());
								Record newData = new Record();
								newData.setAttribute("data", dataToKeep);
								pCriterion.setAttribute("start", newData);
							}
						}		
					}
					if (pCriterion.getAttributeAsRecord("value") != null ) {
						if (pCriterion.getAttributeAsRecord("value").getAttribute("data") == null) {
							Date dataToKeep = pCriterion.getAttributeAsDate("value");
							if (dataToKeep != null) {
								pCriterion.setAttribute("value", new Record());
								Record newData = new Record();
								newData.setAttribute("data", dataToKeep);
								pCriterion.setAttribute("value", newData);		
							}	
						}		
					}
					if (pCriterion.getAttributeAsRecord("end") != null ) {
						if (pCriterion.getAttributeAsRecord("end").getAttribute("data") == null) {
							Date dataToKeep = pCriterion.getAttributeAsDate("end");
							if (dataToKeep != null) {
								pCriterion.setAttribute("end", new Record());
								Record newData = new Record();
								newData.setAttribute("data", dataToKeep);
								pCriterion.setAttribute("end", newData);
							}
						}		
					}
				} 
			}		
			checkedCriteria.addCriteria(pCriterion);
		}

		return checkedCriteria;
	}
	
	public CustomLayout getCustomLayoutInstance() {
		return instance;
	}

	public AdvancedCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(AdvancedCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public DetailToolStripButton getBackToListButton() {
		return backToListButton;
	}
	
}
