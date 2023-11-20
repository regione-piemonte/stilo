/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.archivio.PraticaPregressaDetail.PraticaPregressaDetailSection;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUfficioItems;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUtenteItems;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public abstract class AzioneSuPraticaPregressaPopup extends Window {
	
	private final int TITTLE_ALIGN_WIDTH = 150;
		
	private ValuesManager vm = new ValuesManager();
	
	protected VLayout layout;
	
	protected PraticaPregressaDetailSection datiArchivioPrelievoSection;
	protected DynamicForm datiArchivioPrelievoUfficioPrelievoForm;
	protected DynamicForm datiArchivioPrelievoResponsabilePrelievoForm;
	protected DynamicForm datiArchivioPrelievoNoteForm;
	private SelezionaUfficioItems ufficioPrelievoItems;
	private DateItem dataPrelievoItem;
	private SelezionaUtenteItems responsabilePrelievoItems;
	private TextAreaItem noteUffRichiedenteItem;
	private TextAreaItem noteCittadellaItem;
	
	protected DynamicForm datiAppuntamentoForm;
	private DateItem dataAppuntamentoItem;
	private TimeItem orarioAppuntamentoItem;
	
	protected DynamicForm dataRestituzionePrelievoForm;
	private DateItem dataRestituzionePrelievoItem;
	
	protected DynamicForm motiviPrelievoForm;
	private TextAreaItem motiviPrelievoItem;
	
	private StaticTextItem alertItem;
	
	public AzioneSuPraticaPregressaPopup(Record record){
		
		super();
				
		setIsModal(true);
		setAutoCenter(true);
		setKeepInParentRect(true);				
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(false);
		setShowResizeBar(false);
		
		final String codOperazione = record.getAttribute("codOperPrelievoSuFascArchivio");
		
		layout = new VLayout();
		layout.setAlign(Alignment.CENTER);
		layout.setAlign(VerticalAlignment.CENTER);
		
		if(codOperazione.equalsIgnoreCase("REGISTRA_PRELIEVO")) {
			
			setHeight(270);
			setWidth(1000);	
			
			setTitle("Registra prelievo");
			
			buildFormDatiPrelievo();
		
		} else if(codOperazione.equalsIgnoreCase("MODIFICA_PRELIEVO")) {
			
			setHeight(270);
			setWidth(1000);	
			
			setTitle("Modifica prelievo");
			
			buildFormDatiPrelievo();
			
			initForm(record);
		
		} else if(codOperazione.equalsIgnoreCase("ELIMINA_PRELIEVO")) {
			
			setHeight(200);
			setWidth(456);	
			
			setTitle("Compila motivazioni operazione");
			
			motiviPrelievoForm = new DynamicForm();
			motiviPrelievoForm.setValuesManager(vm);
			motiviPrelievoForm.setKeepInParentRect(true);
			motiviPrelievoForm.setWidth100();
			motiviPrelievoForm.setHeight100();
			motiviPrelievoForm.setNumCols(1);
			motiviPrelievoForm.setColWidths("*");
			motiviPrelievoForm.setCellPadding(5);
			motiviPrelievoForm.setOverflow(Overflow.VISIBLE);
	
			motiviPrelievoItem = new TextAreaItem("motiviPrelievoSuFascArchivio");			
			motiviPrelievoItem.setShowTitle(false);
			motiviPrelievoItem.setHeight(100);
			motiviPrelievoItem.setWidth(450);
			
			motiviPrelievoForm.setFields(motiviPrelievoItem);
			
			layout.addMember(motiviPrelievoForm);
		
		} else {
			
			setHeight(150);
			setWidth(350);	
		
			setTitle("Registra restituzione prelievo");
			
			dataRestituzionePrelievoForm = new DynamicForm();
			dataRestituzionePrelievoForm.setValuesManager(vm);
			dataRestituzionePrelievoForm.setKeepInParentRect(true);
			dataRestituzionePrelievoForm.setWidth100();
			dataRestituzionePrelievoForm.setHeight100();			
			dataRestituzionePrelievoForm.setCellPadding(5);
			dataRestituzionePrelievoForm.setOverflow(Overflow.VISIBLE);
			
			SpacerItem vSpacer = new SpacerItem();
			vSpacer.setHeight(5);
			
			dataRestituzionePrelievoItem = new DateItem("datiPrelievoDaArchivioDataRestituzionePrelievo", "Data restituzione");
			dataRestituzionePrelievoItem.setRequired(true);
			dataRestituzionePrelievoItem.setWrapTitle(false);
			dataRestituzionePrelievoItem.setDefaultValue(new Date());
			
			dataRestituzionePrelievoForm.setFields(vSpacer, dataRestituzionePrelievoItem);
			
			layout.addMember(dataRestituzionePrelievoForm);	
		
		}		
			
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					onClickOkButton(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
				
							markForDestroy();
						}
					});
				}
			}
		});		
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(50);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
		
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.setAlign(Alignment.CENTER);

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		portletLayout.setAlign(Alignment.CENTER);
		portletLayout.setAlign(VerticalAlignment.CENTER);
		
		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		addItem(portletLayout);

	}
	
	private void buildFormDatiPrelievo() {
		datiArchivioPrelievoUfficioPrelievoForm = new DynamicForm();
		datiArchivioPrelievoUfficioPrelievoForm.setValuesManager(vm);
		datiArchivioPrelievoUfficioPrelievoForm.setWidth("100%");
		datiArchivioPrelievoUfficioPrelievoForm.setHeight("5");
		datiArchivioPrelievoUfficioPrelievoForm.setPadding(5);
		datiArchivioPrelievoUfficioPrelievoForm.setWrapItemTitles(false);
		datiArchivioPrelievoUfficioPrelievoForm.setNumCols(16);
		datiArchivioPrelievoUfficioPrelievoForm.setHeight(1);
		datiArchivioPrelievoUfficioPrelievoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoUfficioPrelievoForm.setTabID("HEADER");
		
		TitleItem titoloUfficioItem = new TitleItem(setTitleAlign("Prelievo effettuato dall'ufficio", TITTLE_ALIGN_WIDTH, false));
		titoloUfficioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return true;
			}
		});
				
		ufficioPrelievoItems = new SelezionaUfficioItems(datiArchivioPrelievoUfficioPrelievoForm, "datiPrelievoDaArchivioIdUO", "datiPrelievoDaArchivioDesUO", "datiPrelievoDaArchivioCodUO", "datiPrelievoDaArchivioOrganigrammaUO"){
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return true;
			}
			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return true;
			}
			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return true;
			}
					
		};
		
		dataPrelievoItem = new DateItem("datiPrelievoDaArchivioDataPrelievo", "in data");
		dataPrelievoItem.setRequired(true);
		dataPrelievoItem.setEndRow(true);
		dataPrelievoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return true;
			}
		});
		
		List<FormItem> itemsUfficioPrelievo = new ArrayList<FormItem>();
		itemsUfficioPrelievo.add(titoloUfficioItem);
		itemsUfficioPrelievo.addAll(ufficioPrelievoItems);
		itemsUfficioPrelievo.add(dataPrelievoItem);
		
		datiArchivioPrelievoUfficioPrelievoForm.setFields(itemsUfficioPrelievo.toArray(new FormItem[itemsUfficioPrelievo.size()]));
		
		datiArchivioPrelievoResponsabilePrelievoForm = new DynamicForm();
		datiArchivioPrelievoResponsabilePrelievoForm.setValuesManager(vm);
		datiArchivioPrelievoResponsabilePrelievoForm.setWidth("100%");
		datiArchivioPrelievoResponsabilePrelievoForm.setHeight("5");
		datiArchivioPrelievoResponsabilePrelievoForm.setPadding(5);
		datiArchivioPrelievoResponsabilePrelievoForm.setWrapItemTitles(false);
		datiArchivioPrelievoResponsabilePrelievoForm.setNumCols(16);
		datiArchivioPrelievoResponsabilePrelievoForm.setHeight(1);
		datiArchivioPrelievoResponsabilePrelievoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoResponsabilePrelievoForm.setTabID("HEADER");
		
		TitleItem titoloRespponsabileItem = new TitleItem(setTitleAlign("Responsabile del prelievo", TITTLE_ALIGN_WIDTH, false));
		titoloRespponsabileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return true;
			}
		});
		
		responsabilePrelievoItems = new SelezionaUtenteItems(datiArchivioPrelievoResponsabilePrelievoForm, "datiPrelievoDaArchivioIdUserResponsabile", "datiPrelievoDaArchivioUsernameResponsabile", 
				"datiPrelievoDaArchivioCodRapidoResponsabile", "datiPrelievoDaArchivioCognomeResponsabile", "datiPrelievoDaArchivioNomeResponsabile", "datiPrelievoDaArchivioCodiceFiscaleResponsabile", 
				"DatiPrelievoDaArchivioEmailResponsabile", "DatiPrelievoDaArchivioTelefonoResponsabile"){
			
			@Override
			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean lookupRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return true;
			}

			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return true;
			}

			@Override
			protected boolean nomeItemRequiredIfValidator(FormItem formItem, Object value) {
				return super.nomeItemRequiredIfValidator(formItem, value);
			}

			@Override
			protected boolean nomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return true;
			}

			@Override
			protected boolean cognomeItemRequiredIfValidator(FormItem formItem, Object value) {
				return super.cognomeItemRequiredIfValidator(formItem, value);
			}

			@Override
			protected boolean cognomeItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return true;

			}

			@Override
			protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return true;
			}

			@Override
			protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return true;
			}

			@Override
			protected boolean telefonoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
			
			@Override
			protected boolean emailItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
			
		};
		
		List<FormItem> itemsResponsabilePrelievo = new ArrayList<FormItem>();
		itemsResponsabilePrelievo.add(titoloRespponsabileItem);
		itemsResponsabilePrelievo.addAll(responsabilePrelievoItems);
		
		datiArchivioPrelievoResponsabilePrelievoForm.setFields(itemsResponsabilePrelievo.toArray(new FormItem[itemsResponsabilePrelievo.size()]));
		
		datiArchivioPrelievoNoteForm = new DynamicForm();
		datiArchivioPrelievoNoteForm.setValuesManager(vm);
		datiArchivioPrelievoNoteForm.setWidth("100%");
		datiArchivioPrelievoNoteForm.setHeight("5");
		datiArchivioPrelievoNoteForm.setPadding(5);
		datiArchivioPrelievoNoteForm.setWrapItemTitles(false);
		datiArchivioPrelievoNoteForm.setNumCols(16);
		datiArchivioPrelievoNoteForm.setHeight(1);
		datiArchivioPrelievoNoteForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*" );
		datiArchivioPrelievoNoteForm.setTabID("HEADER");
		
		noteUffRichiedenteItem = new TextAreaItem("datiPrelievoDaArchivioNoteRichiedente", setTitleAlign("Note uff. richiedente", TITTLE_ALIGN_WIDTH, false));
		noteUffRichiedenteItem.setWidth(300);
		noteUffRichiedenteItem.setHeight(50);
		noteUffRichiedenteItem.setStartRow(true);
	
		noteCittadellaItem = new TextAreaItem("datiPrelievoDaArchivioNoteArchivio", setTitleAlign("Note cittadella", TITTLE_ALIGN_WIDTH, false));
		noteCittadellaItem.setWidth(300);
		noteCittadellaItem.setHeight(50);
		noteCittadellaItem.setStartRow(false);
					
		
		datiArchivioPrelievoNoteForm.setItems(noteUffRichiedenteItem, noteCittadellaItem);
		
		layout.addMember(datiArchivioPrelievoUfficioPrelievoForm);
		layout.addMember(datiArchivioPrelievoResponsabilePrelievoForm); 
		layout.addMember(datiArchivioPrelievoNoteForm);
		
	}
	
	private void initForm(Record record) {
		
		datiArchivioPrelievoUfficioPrelievoForm.setValue("datiPrelievoDaArchivioIdUO", record.getAttribute("datiPrelievoDaArchivioIdUO"));
		datiArchivioPrelievoUfficioPrelievoForm.setValue("datiPrelievoDaArchivioCodUO", record.getAttribute("datiPrelievoDaArchivioCodUO"));
		datiArchivioPrelievoUfficioPrelievoForm.setValue("datiPrelievoDaArchivioOrganigrammaUO", record.getAttribute("datiPrelievoDaArchivioOrganigrammaUO"));
		datiArchivioPrelievoUfficioPrelievoForm.setValue("datiPrelievoDaArchivioDesUO", record.getAttribute("datiPrelievoDaArchivioDesUO"));
		datiArchivioPrelievoUfficioPrelievoForm.setValue("datiPrelievoDaArchivioDataPrelievo", record.getAttribute("datiPrelievoDaArchivioDataPrelievo"));
		
		datiArchivioPrelievoResponsabilePrelievoForm.setValue("datiPrelievoDaArchivioIdUserResponsabile", record.getAttribute("datiPrelievoDaArchivioIdUserResponsabile"));
		datiArchivioPrelievoResponsabilePrelievoForm.setValue("datiPrelievoDaArchivioCognomeResponsabile", record.getAttribute("datiPrelievoDaArchivioCognomeResponsabile"));
		datiArchivioPrelievoResponsabilePrelievoForm.setValue("datiPrelievoDaArchivioNomeResponsabile", record.getAttribute("datiPrelievoDaArchivioNomeResponsabile"));
		
		datiArchivioPrelievoNoteForm.setValue("datiPrelievoDaArchivioNoteRichiedente", record.getAttribute("datiPrelievoDaArchivioNoteRichiedente"));
		datiArchivioPrelievoNoteForm.setValue("datiPrelievoDaArchivioNoteArchivio", record.getAttribute("datiPrelievoDaArchivioNoteArchivio"));
		
		ufficioPrelievoItems.afterEditRecord(record);
		
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	
		
	public abstract void onClickOkButton(DSCallback service);
	
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
