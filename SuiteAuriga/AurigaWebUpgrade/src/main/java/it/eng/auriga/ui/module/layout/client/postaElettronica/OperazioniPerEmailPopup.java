/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class OperazioniPerEmailPopup extends ModalWindow {

	private ValuesManager _vm;

	protected OperazioniPerEmailPopup _window;
	
	protected DynamicForm utentiForm;
	protected DynamicForm messaggioForm;
	
	protected DetailSection detailSectionUtenti;
	protected DetailSection detailSectionMessaggio;
	
	protected FilteredSelectItemWithDisplay utentiItem;
	protected TextAreaItem messaggioItem;

	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	protected CheckboxItem flgSelezionaItem;
	protected List<CheckboxItem> listaCheckboxPreferiti;
	
	protected RecordList listaPreferiti;
	


	public OperazioniPerEmailPopup(final String tipoOperazione, final RecordList listaPreferiti) {

		super("metti_in_carico_email", true);

		_window = this;
		
		_vm = new ValuesManager();

		this.listaPreferiti = listaPreferiti;

		String title = "";
		if (tipoOperazione.equals(TipoOperazioneMail.PRESA_IN_CARICO.getValue())) {
			title = I18NUtil.getMessages().messa_incaricoemailpopup_title_presaInCarico();
		} else if (tipoOperazione.equals(TipoOperazioneMail.MESSA_IN_CARICO.getValue())) {
			title = I18NUtil.getMessages().messa_incaricoemailpopup_title_messaInCarico();
		} else if (tipoOperazione.equals(TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue())) {
			title = I18NUtil.getMessages().messa_incaricoemailpopup_title_mandaInApprovazione();
		} else if (tipoOperazione.equals(TipoOperazioneMail.RILASCIA.getValue())) {
			title = I18NUtil.getMessages().messa_incaricoemailpopup_title_rilascio();
		}
		setTitle(title);

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		if (tipoOperazione.equals(TipoOperazioneMail.MESSA_IN_CARICO.getValue())
				|| tipoOperazione.equals(TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue())) {
			
			utentiForm = new DynamicForm();
			utentiForm.setValuesManager(_vm);
			utentiForm.setKeepInParentRect(true);
			utentiForm.setWidth100();
			utentiForm.setHeight100();
			utentiForm.setNumCols(7);
			utentiForm.setColWidths(10, 10, 10, 10, 10, "*", "*");
			utentiForm.setCellPadding(2);
			utentiForm.setWrapItemTitles(false);
//			utentiForm.setCellBorder(1);

			SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource(
					"InCaricoSelectDataSource", "idUtente", FieldType.TEXT,
					new String[] { "cognomeNome", "username" }, true);
			lGwtRestDataSourceUtenti.extraparam.put("skipAction", "true");
//			lGwtRestDataSourceUtenti.extraparam.put("soloValidi", "true");
			
			utentiItem = new FilteredSelectItemWithDisplay("idUtente", lGwtRestDataSourceUtenti) {
				@Override
				public void onOptionClick(final Record record) {
					super.onOptionClick(record);					
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							LinkedHashMap<String, String> utentiValueMap = new LinkedHashMap<String, String>();
							utentiValueMap.put(record.getAttributeAsString("idUtente"), record.getAttributeAsString("cognomeNome") + " ** " + record.getAttributeAsString("username"));
							utentiItem.setValueMap(utentiValueMap);
						}
					});
				}

				@Override
				protected void clearSelect() {
					super.clearSelect();	
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							LinkedHashMap<String, String> utentiValueMap = new LinkedHashMap<String, String>();
							utentiItem.setValueMap(utentiValueMap);
							utentiForm.setValue("idUtente", "");
						}
					});					
				};

				@Override
				public void setValue(String value) {
					super.setValue(value);
					if (value == null || "".equals(value)) {
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {

							@Override
							public void execute() {
								LinkedHashMap<String, String> utentiValueMap = new LinkedHashMap<String, String>();
								utentiItem.setValueMap(utentiValueMap);
								utentiForm.setValue("idUtente", "");
							}
						});	
					}
				}	
			};
			utentiItem.setAutoFetchData(false);
			utentiItem.setAlwaysFetchMissingValues(true);
			utentiItem.setFetchMissingValues(true);
			ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//
			ListGridField utentiUsernameField = new ListGridField("username", "Username");
			utentiUsernameField.setWidth(250);//Impostazione della larghezza del campo Username
			utentiItem.setPickListFields(utentiCognomeNomeField, utentiUsernameField);
			utentiItem.setFilterLocally(true);
			utentiItem.setValueField("idUtente");
			utentiItem.setDisplayField("cognomeNome");
			utentiItem.setShowTitle(false);
			utentiItem.setOptionDataSource(lGwtRestDataSourceUtenti);
			utentiItem.setWidth(695); //Impostazione della larghezza del filtro di selezione
//			utentiItem.setRequired(true);
			utentiItem.setClearable(true);
			utentiItem.setShowIcons(true);
			utentiItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti");
			utentiItem.setItemHoverFormatter(new FormItemHoverFormatter() {

				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					return item.getSelectedRecord() != null
							? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
				}
			});		
			utentiItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					if (listaPreferiti == null || listaPreferiti.isEmpty() || (utentiForm.getValue("seleziona") != null && (Boolean) utentiForm.getValue("seleziona"))) {
						return true;
					} else {
						return false;
					}
				}
			});
			utentiItem.setAttribute("obbligatorio", true);
			utentiItem.setValidators(new RequiredIfValidator(new com.smartgwt.client.widgets.form.validator.RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					if (listaPreferiti == null || listaPreferiti.isEmpty() || (utentiForm.getValue("seleziona") != null && (Boolean) utentiForm.getValue("seleziona"))) {
						return true;
					} else {
						return false;
					}
				}
			}));

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
				fields.add(utentiItem);
				for(int i= 1; i < listaCheckboxPreferiti.size(); i++){
					fields.add(listaCheckboxPreferiti.get(i));
				}
				
				manageClickChecboxListaPreferiti();
				
				utentiForm.setFields(fields.toArray(new FormItem[fields.size()]));
			
			} else {				
				utentiForm.setFields(new FormItem[] { utentiItem });				
			}

			String utentiTitle = I18NUtil.getMessages().messa_incaricoemailpopup_utenti_title_mettiInCarico();
			if (tipoOperazione.equals(TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue())) {
				utentiTitle = I18NUtil.getMessages().messa_incaricoemailpopup_utenti_title_mandaInApprovazione();
			} 
			detailSectionUtenti = new DetailSection(utentiTitle, true, true, true, utentiForm);

		}
		
		messaggioForm = new DynamicForm();
		messaggioForm.setValuesManager(_vm);
		messaggioForm.setKeepInParentRect(true);
		messaggioForm.setWidth100();
		messaggioForm.setHeight100();
		messaggioForm.setNumCols(5);
		messaggioForm.setColWidths(10, 10, 10, "*", "*");
		messaggioForm.setCellPadding(5);
		messaggioForm.setWrapItemTitles(false);
//		messaggioForm.setCellBorder(1);
		
		messaggioItem = new TextAreaItem("motivo");
		messaggioItem.setShowTitle(false);
		messaggioItem.setStartRow(true);
		messaggioItem.setLength(1000);
		messaggioItem.setHeight(100);
		messaggioItem.setColSpan(5);
		messaggioItem.setWidth(676);

		messaggioForm.setFields(messaggioItem);
		
		String messaggioTitle = "Motivo";
		if (tipoOperazione.equals(TipoOperazioneMail.MANDA_IN_APPROVAZIONE.getValue())) {
			messaggioTitle = "Messaggio";
		} 
		detailSectionMessaggio = new DetailSection(messaggioTitle, true, true, false, messaggioForm);

		Button confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (validate()) {
					onClickOkButton(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							_window.markForDestroy();
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
		setTop(100);
		setLeft(200);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.setAlign(Alignment.CENTER);

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		if(detailSectionUtenti != null) {
			layout.addMember(detailSectionUtenti);
		}
		layout.addMember(detailSectionMessaggio);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
		
		setWidth(920);
		setHeight(400);

	}
	
	public String getMotivo() {
		return (String) messaggioForm.getValue("motivo");
	}
	
	public String getUtente() {
		if(utentiForm != null) {
			if (listaPreferiti != null && !listaPreferiti.isEmpty()) {				
				if(utentiForm.getValue("seleziona") != null && (Boolean) utentiForm.getValue("seleziona")) {
					return (String) utentiForm.getValue("idUtente");
				} else { 
					for(int i=0; i < listaPreferiti.getLength();i++){
						Record currentRecord = listaPreferiti.get(i);
						String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
						if(utentiForm.getValue(idDestinatarioPreferito) != null && (Boolean) utentiForm.getValue(idDestinatarioPreferito)){
							return idDestinatarioPreferito;						
						}
					}	
				}	
			} else {
				return (String) utentiForm.getValue("idUtente");
			}
		}
		return null;
	}

	private void buildCheckboxListaPreferiti(RecordList listaPreferiti){
		for(int i=0; i < listaPreferiti.getLength();i++){
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			CheckboxItem flgPreferitoItem = new CheckboxItem(idDestinatarioPreferito, descrizioneDestinatarioPreferito);
			flgPreferitoItem.setStartRow(true);
			flgPreferitoItem.setColSpan(5);
			listaCheckboxPreferiti.add(flgPreferitoItem);
		}
	}

	private void manageClickChecboxListaPreferiti(){
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
						if(utentiForm != null) {
							utentiForm.clearErrors(true);
							utentiForm.redraw();
						}	
					}				
				}
			});
		}
	}

	public abstract void onClickOkButton(DSCallback callback);

	public void editRecordPerModificaInvio(Record record) {
		_vm.editRecord(record);
		markForRedraw();
	}
	
	public boolean validate() {
		if(utentiItem != null) {
			return utentiItem.validate();
		}
		return true;
	}
	
	@Override
	protected void onDestroy() {
		if (_vm != null) {
			try {
				_vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}

}