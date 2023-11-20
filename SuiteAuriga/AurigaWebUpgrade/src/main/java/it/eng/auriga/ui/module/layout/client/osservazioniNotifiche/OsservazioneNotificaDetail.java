/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import com.smartgwt.client.types.Alignment;

public class OsservazioneNotificaDetail extends CustomDetail {
	
	protected OsservazioneNotificaDetail instance;
	
	private DynamicForm osservazioneNotificaForm;
	private DynamicForm osservazioneNotificaForm2;
	
	private SelectItem destinatariItem;
	private CKEditorItem messageItem;
	private SelectItem prioritaItem;
	private DetailSection osservazioneNotificaSection;

	// VLayout
	private VLayout lVLayoutMain;
	
	// DynamicForm
	protected DynamicForm tipoProtocollazioneForm;
	
	// Toolstrip contenente i bottoni di dettaglio
	protected DetailToolStripButton saveButton;
	
	// Toolstrip contenente i bottoni di dettaglio
	protected ToolStrip detailToolStrip;
	
	private String idUdFolder;
	private String flgUdFolder;
	private CustomLayout osservazioniNotificheLayout;

	public OsservazioneNotificaDetail(String nomeEntita,String idUdFolder,String flgUdFolder,CustomLayout listaOsservazioniNotificheLayout) {

		super(nomeEntita);
		
		instance = this;
		
		this.idUdFolder = idUdFolder;
		this.flgUdFolder = flgUdFolder;
		this.osservazioniNotificheLayout = listaOsservazioniNotificheLayout;
		
		init();
	}
	
	protected void init() {

		setPaddingAsLayoutMargin(false);
		
		lVLayoutMain = new VLayout();
		lVLayoutMain.setOverflow(Overflow.AUTO);
		
		buildEstremiRichiestaSection();
		
		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();
		
		lVLayoutMain.addMember(spacer);
		
		createDetailToolStrip();
		
		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(lVLayoutMain);
		mainLayout.addMember(detailToolStrip);		
		
		setMembers(mainLayout);			
	}
	
	protected void createDetailToolStrip() {

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().osservazioniNotifiche_layout_aggiungi_title(), "buttons/save.png");
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
		
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.setAlign(Alignment.CENTER);
		detailToolStrip.addButton(saveButton);
	}
	
	public void onSaveButtonClick() {		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {	
			if (validate()){
				if(osservazioneNotificaForm.validate()){
					Record record = new Record();
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaOsservazioniNotificheDataSource");
					lGwtRestDataSource.addParam("idUdFolder", idUdFolder);
					lGwtRestDataSource.addParam("flgUdFolder", flgUdFolder);
					record.setAttribute("destinatariOsservazioneNotifica", destinatariItem.getValueAsString());
					record.setAttribute("livelloPriorita", (String) prioritaItem.getValueAsString());
					record.setAttribute("messaggioOsservazioneNotifica", (String)messageItem.getValue());
					record.setAttribute("messaggioOsservazioneNotifica", messageItem != null ? (String)messageItem.getValue() : null);
					lGwtRestDataSource.addData(record, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean("Richiesta inserita con successo","",MessageType.INFO));
								if(osservazioniNotificheLayout != null){
									((OsservazioniNotificheLayout)osservazioniNotificheLayout).doSearch();
								}
							}
						}
					});
				}
			  }
			}
		});		
	}
	
	private void buildEstremiRichiestaSection() {
		
		osservazioneNotificaForm = new DynamicForm();
		osservazioneNotificaForm.setValuesManager(vm);
		osservazioneNotificaForm.setWidth("100%");
		osservazioneNotificaForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		osservazioneNotificaForm.setHeight("5");
		osservazioneNotificaForm.setPadding(5);
		osservazioneNotificaForm.setNumCols(10);
		osservazioneNotificaForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		osservazioneNotificaForm.setWrapItemTitles(false);
		
		// DESTINATARIO
		GWTRestDataSource destinatariOsservazioniDS = new GWTRestDataSource("LoadComboDestinatariOsservazioniDataSource", "key", FieldType.TEXT);
		destinatariOsservazioniDS.addParam("idUdFolder", idUdFolder);
		destinatariOsservazioniDS.addParam("flgUdFolder", flgUdFolder);
		destinatariItem = new SelectItem("destinatariOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_detail_destinatario_title());
		destinatariItem.setOptionDataSource(destinatariOsservazioniDS);
		destinatariItem.setAllowEmptyValue(true);
		destinatariItem.setAlwaysFetchMissingValues(true);
		destinatariItem.setAutoFetchData(false);		
		destinatariItem.setMultiple(true);
		destinatariItem.setWidth(817);
		destinatariItem.setStartRow(true);
		ListGrid pickListProperties = new ListGrid();
		pickListProperties.setShowHeader(true);
		destinatariItem.setPickListProperties(pickListProperties);
		ListGridField keyField = new ListGridField("key");
		keyField.setHidden(true);
		ListGridField valueField = new ListGridField("value","Tutti");
		destinatariItem.setPickListFields(keyField,valueField);
		destinatariItem.setValueField("key");
		destinatariItem.setDisplayField("value");
		destinatariItem.setRequired(true);
		destinatariItem.setColSpan(9);
		

		osservazioneNotificaForm2 = new DynamicForm();
		osservazioneNotificaForm2.setValuesManager(vm);
		osservazioneNotificaForm2.setWidth("100%");
		osservazioneNotificaForm2.setColWidths("1","1","1","1","1","1","1","1","*","*");
		osservazioneNotificaForm2.setHeight("5");
		osservazioneNotificaForm2.setPadding(5);
		osservazioneNotificaForm2.setNumCols(10);
		osservazioneNotificaForm2.setColWidths("1","1","1","1","1","1","1","1","*","*");
		osservazioneNotificaForm2.setWrapItemTitles(false);
		
		// MESSAGGIO
		messageItem = new CKEditorItem("messaggioOsservazioneNotifica", 4000, "EXTENDED", 4, 810, null);
		messageItem.setColSpan(7);
		messageItem.setStartRow(true);
		messageItem.setRequired(true);
		messageItem.setShowTitle(true);
		messageItem.setTitle(I18NUtil.getMessages().osservazioniNotifiche_detail_messaggio_title());
		messageItem.setTitleVAlign(VerticalAlignment.TOP);
		
		// PRIORITA'
		GWTRestDataSource prioritaRiservatezzaDS = new GWTRestDataSource("LoadComboPrioritaInvioDataSource", "key", FieldType.TEXT);
		prioritaItem = new SelectItem("prioritaOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_detail_priorita_title());
		prioritaItem.setOptionDataSource(prioritaRiservatezzaDS);
		prioritaItem.setAutoFetchData(false);
		prioritaItem.setAlwaysFetchMissingValues(true);
		prioritaItem.setFetchMissingValues(true);
		prioritaItem.setAllowEmptyValue(true);
		prioritaItem.setWidth(100);
		prioritaItem.setDisplayField("value");
		prioritaItem.setValueField("key");
		prioritaItem.setEndRow(true);
		prioritaItem.setStartRow(true);
				
		osservazioneNotificaForm.setItems(destinatariItem);

		osservazioneNotificaForm2.setItems(messageItem , prioritaItem );

		osservazioneNotificaSection = new DetailSection("Nuovo messaggio", null, true, true, false, osservazioneNotificaForm,osservazioneNotificaForm2);
				
		lVLayoutMain.addMember(osservazioneNotificaSection);		
	}

	public boolean showDetailToolStrip() {
		return getLayout() == null;
	}
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate();
		
		// Faccio la validazione dei CKEditor obbligatori
		if(messageItem != null) {
			valid = messageItem.validate() && valid;
		}	
		return valid;
	}
}
