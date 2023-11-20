/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;

import it.eng.auriga.ui.module.layout.client.gestioneContenutiAmministrazioneTrasparente.ContenutiAmmTraspLayout;
import it.eng.auriga.ui.module.layout.client.gestioneContenutiAmministrazioneTrasparente.SezionePopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class PubblicazioneTraspAmmPopup extends ModalWindow {

	private PubblicazioneTraspAmmPopup _window;
	
	protected ValuesManager vm;
	
	private String idUd;	

	private DynamicForm sezioneForm;
	private SelectItem idSezione;
	private HiddenItem nomeSezione;

	public PubblicazioneTraspAmmPopup(String idUd) {

		super("pubblicazione_trasp_amm", true);

		_window = this;
		
		this.idUd = idUd;

		vm = new ValuesManager();
		
		setTitle("Dati pubblicazione nel sito di Trasparenza Amministrativa");
		setAutoCenter(true);
		setWidth(715);   
        setHeight(145);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		sezioneForm = new DynamicForm();
		sezioneForm.setValuesManager(vm);
		sezioneForm.setKeepInParentRect(true);
		sezioneForm.setWidth100();
		sezioneForm.setHeight100();
		sezioneForm.setNumCols(5);
		sezioneForm.setColWidths(10, 10, 10, "*", "*");
		sezioneForm.setCellPadding(5);
		sezioneForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource idSezioneDS = new SelectGWTRestDataSource("LoadComboSezionePubblTraspAmmDataSource", true, "idNode", FieldType.TEXT);
		
		idSezione = new SelectItem("idSezione") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				sezioneForm.setValue("nomeSezione", record.getAttribute("nome"));				
				sezioneForm.markForRedraw();				
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					sezioneForm.clearValue("idSezione");
					sezioneForm.clearValue("nomeSezione");
					sezioneForm.markForRedraw();
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				sezioneForm.clearValue("idSezione");
				sezioneForm.clearValue("nomeSezione");
				sezioneForm.markForRedraw();
			};					
			
			@Override
			protected ListGrid builPickListProperties() {
				final TreeGrid pickListProperties = new TreeGrid();
				pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
				pickListProperties.setSelectionType(SelectionStyle.NONE);
				pickListProperties.setShowSelectedStyle(false);
				pickListProperties.addCellClickHandler(new CellClickHandler() {
					
					@Override
					public void onCellClick(CellClickEvent event) {
						if (event.getRecord().getAttributeAsBoolean("flgToAbil")) {
							onOptionClick(event.getRecord());
						} else {
							event.cancel();
							Layout.addMessage(new MessageBean("Non sei abilitato a pubblicare nella sezione", "", MessageType.ERROR));
							Scheduler.get().scheduleDeferred(new ScheduledCommand() {

								@Override
								public void execute() {
									clearSelect();
								}
							});
						}	
					}
				});	
				/*
				pickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {			
						GWTRestDataSource idSezioneDS = (GWTRestDataSource) idSezione.getOptionDataSource();				
						idSezione.setOptionDataSource(idSezioneDS);
//						idSezione.invalidateDisplayValueCache(); // Questo non va messo altrimenti perdo la decodifica del valore selezionato
					}
				});
				*/				
				pickListProperties.setShowHeader(false);
		        pickListProperties.setAutoFitFieldWidths(true); 
		        pickListProperties.setShowAllRecords(true);
//		        pickListProperties.setBodyOverflow(Overflow.VISIBLE);
//		        pickListProperties.setOverflow(Overflow.VISIBLE);
		        pickListProperties.setLeaveScrollbarGap(false);
		        /*
		         * Impedisce il ricaricamento generale dell'albero ad ogni esplosione dei nodi anche 
		         * se nodi foglia
		         */
		        pickListProperties.setLoadDataOnDemand(false);
		        pickListProperties.setNodeIcon("blank.png");
		        pickListProperties.setFolderIcon("blank.png");
		        return pickListProperties;				
			}
		};
		idSezione.setShowTitle(false);
		idSezione.setStartRow(true);
		idSezione.setWrapTitle(false);
		idSezione.setWidth(600);
		idSezione.setColSpan(16);	
		idSezione.setPickListWidth(750);			
		ListGridField nomeField = new ListGridField("nome");		
		ListGridField idNodeField = new ListGridField("idNode");
		idNodeField.setHidden(true);		
		ListGridField flgToAbilField = new ListGridField("flgToAbil");
		flgToAbilField.setType(ListGridFieldType.BOOLEAN);
		flgToAbilField.setHidden(true);				
		/**
		 * Con la proprietà setDataSetType("tree"); nel setPickListFields va settato per primo un campo NON hidden
		 * perchè abbiamo riscontrato problemi di creazione del componente grafico
		 */
		idSezione.setPickListFields(nomeField, idNodeField, flgToAbilField);  
		idSezione.setDataSetType("tree"); 
		idSezione.setDisplayField("nome");
		idSezione.setValueField("idNode");              
		idSezione.setOptionDataSource(idSezioneDS);	
		idSezione.setAutoFetchData(false);
		idSezione.setAlwaysFetchMissingValues(false);
		idSezione.setFetchMissingValues(false);
		idSezione.setCachePickListResults(true);
		idSezione.setFetchDelay(500);
		idSezione.setClearable(true);	
		idSezione.setRequired(true);
				
		nomeSezione = new HiddenItem("nomeSezione");
		
		sezioneForm.setFields(idSezione, nomeSezione);

		Button confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						if (validate()) {
							Record record = new Record(vm.getValues());
							onClickOkButton(record);
							_window.markForDestroy();
						}
					}
				});			
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
	
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		DetailSection detailSectionSezione = new DetailSection("Nella sezione", true, true, true, sezioneForm);		
		layout.addMember(detailSectionSezione);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("buttons/richiesta_pubblicazione.png");
		
		show();    
	}
	
	public boolean validate() {
		return vm.validate();
	}
	
	public void editRecord(Record record) {
		vm.editRecord(record);
		markForRedraw();
	}
	
	public void onClickOkButton(Record record) {
		String idSezione = record.getAttribute("idSezione");
		SezionePopup sezionePopUp = new SezionePopup(null, null, idSezione, true, ContenutiAmmTraspLayout.TIPO_CONTENUTO_DOCUMENTO_COMPLESSO, false, null);
		sezionePopUp.editRecordNuovoContenuto(ContenutiAmmTraspLayout.TIPO_CONTENUTO_DOCUMENTO_COMPLESSO, idSezione, null, idUd);		
		sezionePopUp.setEditMode();
		sezionePopUp.show();		
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
}
