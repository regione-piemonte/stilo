/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class AssegnazioneEmailPopup extends ModalWindow {
	
	protected AssegnazioneEmailPopup _window;
	
	protected ValuesManager vm;

	protected DynamicForm formLegenda;
	protected DynamicForm formAssegnazione;
	protected DynamicForm formMessaggio;
		
	protected AssegnazioneEmailItem assegnazioneItem;
	protected CheckboxItem flgSelezionaItem;
	protected List<CheckboxItem> listaCheckboxPreferiti;
	
	protected TextAreaItem messaggioItem;

	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	protected RecordList listaPreferiti;
	
	public String idEmail = "";
	
	public AssegnazioneEmailPopup(Record pListRecord, RecordList pListaPreferiti) {

		super("assegnazione_email", true);

		_window = this;
		
		vm = new ValuesManager();
		
		listaPreferiti = pListaPreferiti;

		String oggetto = (pListRecord != null && pListRecord.getAttribute("oggetto") != null) ? pListRecord.getAttribute("oggetto") : "";
		String title = "Compila dati assegnazione e-mail";
		if (!"".equals(oggetto)) {
			title += " " + oggetto;
		}
		setTitle(title);

		idEmail = (pListRecord != null && pListRecord.getAttribute("idEmail") != null) ? pListRecord.getAttribute("idEmail") : "";
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		formAssegnazione = new DynamicForm();
		formAssegnazione.setValuesManager(vm);
		formAssegnazione.setKeepInParentRect(true);
		formAssegnazione.setWidth100();
		formAssegnazione.setHeight100();
		formAssegnazione.setNumCols(7);
		formAssegnazione.setColWidths(10, 10, 10, 10, 10, "*", "*");
		formAssegnazione.setCellPadding(2);
		formAssegnazione.setWrapItemTitles(false);

		assegnazioneItem = new AssegnazioneEmailItem() {
			
			@Override
			public int getFilteredSelectItemWidth() {
				return 475;
			}	
			
			@Override
			public String getIdEmail() {
				return idEmail;
			}
			
		};
		
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setShowTitle(false);
		assegnazioneItem.setCanEdit(true);
		assegnazioneItem.setColSpan(5);
		assegnazioneItem.setNotReplicable(true);
		assegnazioneItem.setAttribute("obbligatorio", true);
		
		if (listaPreferiti != null && !listaPreferiti.isEmpty()) {
			
			listaCheckboxPreferiti = new ArrayList<CheckboxItem>();
			
			flgSelezionaItem = new CheckboxItem("seleziona", "<i>Seleziona...</i>");
			flgSelezionaItem.setDefaultValue(true);
			flgSelezionaItem.setStartRow(false);
			flgSelezionaItem.setEndRow(false);			
			flgSelezionaItem.setColSpan(1);
			flgSelezionaItem.setWidth(100);
			flgSelezionaItem.setHeight(30);
			
			// Popolamento della lista di CheckBox
			listaCheckboxPreferiti.add(flgSelezionaItem);
			buildCheckboxListaPreferiti(listaPreferiti);
			
			List<FormItem> fields = new ArrayList<FormItem>();
			fields.add(flgSelezionaItem);
			fields.add(assegnazioneItem);
			for(int i= 1; i < listaCheckboxPreferiti.size(); i++){
				fields.add(listaCheckboxPreferiti.get(i));
			}
			
			manageClickCheckboxEsclusiveListaPreferiti();

			formAssegnazione.setFields(fields.toArray(new FormItem[fields.size()]));	
			
		} else {
			formAssegnazione.setFields(assegnazioneItem);			
		}
		
		formMessaggio = new DynamicForm();
		formMessaggio.setValuesManager(vm);
		formMessaggio.setKeepInParentRect(true);
		formMessaggio.setWidth100();
		formMessaggio.setHeight100();
		formMessaggio.setNumCols(5);
		formMessaggio.setColWidths(10, 10, 10, "*", "*");
		formMessaggio.setCellPadding(5);
		formMessaggio.setWrapItemTitles(false);
		
		messaggioItem = new TextAreaItem("messaggio");
		messaggioItem.setShowTitle(false);
		messaggioItem.setStartRow(true);
		messaggioItem.setLength(4000);
		messaggioItem.setHeight(40);
		messaggioItem.setColSpan(5);
		messaggioItem.setWidth(677);
		
		formMessaggio.setFields(messaggioItem);
		
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
							record.setAttribute("listaAssegnazioni", getAssegnazioni());
							record.setAttribute("messaggio", getMessaggio());
							
							onClickOkButton(record, new DSCallback() {
	
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									_window.markForDestroy();
								}
							});
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
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_LEGENDA_DIN_TIPO_UO")) {
				LegendaDinamicaPanel legendaPanel = new LegendaDinamicaPanel();
				layout.addMember(legendaPanel);
			} else {
				buildFormLegenda();
				layout.addMember(formLegenda);
			}
		}

		DetailSection detailSectionAssegnazione = new DetailSection("Destinatario", true, true, true, formAssegnazione);
		DetailSection detailSectionMessaggio = new DetailSection("Con messaggio", true, true, false, formMessaggio);
		
		layout.addMember(detailSectionAssegnazione);
		layout.addMember(detailSectionMessaggio);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
	}
	
	public String getMessaggio() {
		return (String) formMessaggio.getValue("messaggio");
	}
	
	public RecordList getAssegnazioni() {
		if (listaPreferiti != null && !listaPreferiti.isEmpty()) {							
			if(formAssegnazione.getValue("seleziona") != null && (Boolean) formAssegnazione.getValue("seleziona")) {
				return formAssegnazione.getValueAsRecordList("listaAssegnazioni");					
			} else { 
				RecordList listaAssegnazioni = new RecordList();
				for(int i=0; i < listaPreferiti.getLength(); i++){
					Record currentRecord = listaPreferiti.get(i);
					String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
					String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
					if(formAssegnazione.getValue(idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(idDestinatarioPreferito)){
						Record recordAssegnazioni = new Record();
						recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
						recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
						listaAssegnazioni.add(recordAssegnazioni);									
					}
				}	
				return listaAssegnazioni;		
			}
		} else {
			return formAssegnazione.getValueAsRecordList("listaAssegnazioni");				
		}	
	}

	private void buildFormLegenda() {
		
		formLegenda = new DynamicForm();
		formLegenda.setKeepInParentRect(true);
		formLegenda.setCellPadding(5);
		formLegenda.setWrapItemTitles(false);

		StaticTextItem tipoUOImage = new StaticTextItem("iconaStatoConsolidamento");
		tipoUOImage.setShowValueIconOnly(true);
		tipoUOImage.setShowTitle(false);
		tipoUOImage.setValueIconWidth(600);
		tipoUOImage.setValueIconHeight(60);
		tipoUOImage.setAlign(Alignment.LEFT);
		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("1", "organigramma/legenda_uo.png");
		tipoUOImage.setValueIcons(valueIcons);
		tipoUOImage.setDefaultValue("1");
		tipoUOImage.setDefaultIconSrc("organigramma/legenda_uo.png");

		formLegenda.setItems(tipoUOImage);
	}
	
	private void buildCheckboxListaPreferiti(RecordList listaPreferiti){
		for(int i=0; i < listaPreferiti.getLength();i++){
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			CheckboxItem flgPreferitoItem = new CheckboxItem(idDestinatarioPreferito, descrizioneDestinatarioPreferito);
			flgPreferitoItem.setStartRow(true);
			flgPreferitoItem.setColSpan(6);
			listaCheckboxPreferiti.add(flgPreferitoItem);
		}
	}

	private void manageClickCheckboxEsclusiveListaPreferiti(){
		for(CheckboxItem check : listaCheckboxPreferiti){
			check.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					
					String nameCheckboxClicked = event.getItem().getName();
					for(CheckboxItem check : listaCheckboxPreferiti){
						if(nameCheckboxClicked.equals(check.getName())){
							check.setValue(true);
						} else {
							check.setValue(false);
						}	
						formAssegnazione.clearErrors(true);
						if(nameCheckboxClicked.equals("seleziona")){
							assegnazioneItem.show();
							assegnazioneItem.setAttribute("obbligatorio", true);
						} else {
							assegnazioneItem.hide();
							assegnazioneItem.setAttribute("obbligatorio", false);
						}
					}				
				}
			});
		}
	}
	
	public boolean validate() {
		return assegnazioneItem.validate();
	}
	
	public void editRecordPerModificaInvio(Record record) {
		vm.editRecord(record);
		markForRedraw();
	}
	
	public abstract void onClickOkButton(Record record, DSCallback callback);
	
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