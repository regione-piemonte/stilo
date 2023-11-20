/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

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
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SmistamentoPopup extends ModalWindow {
	
	protected SmistamentoPopup _window;
	
	protected DynamicForm formAssegnazione;
	protected DynamicForm formMessaggio;
	protected ValuesManager vm;
	
	protected String flgUdFolder;
	protected AssegnazioneItem assegnazioneItem;
	protected CheckboxItem flgSelezionaItem;
	protected List<CheckboxItem> listaCheckbox;
	protected List<CheckboxItem> listaCheckboxPreferiti;
	
	protected TextAreaItem messaggioInvioItem;
	protected SelectItem livelloPrioritaItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public SmistamentoPopup(String pFlgUdFolder, Record pListRecord){
		
		super("smistamento", true);
		
		_window = this;
		
		vm = new ValuesManager();
		
		flgUdFolder = pFlgUdFolder;
		
		String segnatura = (pListRecord != null && pListRecord.getAttribute("segnatura") != null) ? pListRecord.getAttribute("segnatura") : "";
		String title = "Compila dati smistamento";
		if(!"".equals(segnatura)) {
			title += " " + segnatura;
		}
		setTitle(title);
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		
		createFormAssegnazione();
		
		createFormMessaggio();
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(validate()) {
					
					final Record record = new Record(vm.getValues());
					record.setAttribute("listaAssegnazioni", getAssegnazioni());
					
					onClickOkButton(record, new DSCallback() {
						
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
		
		DetailSection detailSectionAssegnazione = new DetailSection("F".equals(flgUdFolder) ? "Destinatario" : "Destinatario/i", true, true, true, formAssegnazione);
		DetailSection detailSectionMessaggio = new DetailSection("Messaggio", true, true, false, formMessaggio);
		
		layout.addMember(detailSectionAssegnazione);
		layout.addMember(detailSectionMessaggio);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("archivio/smista.png");

	}
	
	public boolean validate() {
		if(getListaPreferiti() != null) {
			for(int i=0; i < getListaPreferiti().getLength(); i++){
				Record currentRecord = getListaPreferiti().get(i);
				String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
				String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
				if(formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
					return true;									
				}
			}	
		}
		return assegnazioneItem.validate();
	}
	
	private void createFormAssegnazione() {
		
		formAssegnazione = new DynamicForm();
		formAssegnazione.setValuesManager(vm);
		formAssegnazione.setKeepInParentRect(true);
		formAssegnazione.setWidth100();
		formAssegnazione.setHeight100();
		formAssegnazione.setNumCols(7);
		formAssegnazione.setColWidths(10, 10, 10, 10, 10, "*", "*");
		formAssegnazione.setCellPadding(2);
		formAssegnazione.setWrapItemTitles(false);
		
		assegnazioneItem = new AssegnazioneItem() {
			
			@Override
			public boolean showPreferiti() {
				if(getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
					return false;
				}
				return super.showPreferiti();
			}
			
			@Override
			public boolean showOpzioniInvioAssegnazioneButton() {
				return false;
			}
		};
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setTitle("Destinatario");	
		assegnazioneItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnazioneItem.setCanEdit(true);
		assegnazioneItem.setColSpan(4);
		assegnazioneItem.setFlgUdFolder(flgUdFolder);
		if (isAssegnazioneUnica()) {
			assegnazioneItem.setNotReplicable(true);
			assegnazioneItem.setFlgSenzaLD(true);
		}
		assegnazioneItem.setAttribute("obbligatorio", true);
		
		if ((getListaPreferiti() != null && !getListaPreferiti().isEmpty())) {
			
			listaCheckbox = new ArrayList<CheckboxItem>();				
			listaCheckboxPreferiti = new ArrayList<CheckboxItem>();					
						
			if (isAssegnazioneUnica()) {
				
				List<FormItem> fields = new ArrayList<FormItem>();
				
				flgSelezionaItem = new CheckboxItem("seleziona", "<i>Seleziona...</i>");
				flgSelezionaItem.setDefaultValue(true);
				flgSelezionaItem.setStartRow(false);
				flgSelezionaItem.setEndRow(false);			
				flgSelezionaItem.setColSpan(1);
				flgSelezionaItem.setWidth(100);
				flgSelezionaItem.setHeight(30);			
				
				fields.add(flgSelezionaItem);
				listaCheckbox.add(flgSelezionaItem);
				
				fields.add(assegnazioneItem);
				
				if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
		
					buildCheckboxListaPreferiti();	
					
					for(int i = 0; i < listaCheckboxPreferiti.size(); i++){
						fields.add(listaCheckboxPreferiti.get(i));
						listaCheckbox.add(listaCheckboxPreferiti.get(i));					
					}				
				}				
				manageClickCheckboxEsclusive();				
				formAssegnazione.setFields(fields.toArray(new FormItem[fields.size()]));
				
			} else {
				
				List<FormItem> fields = new ArrayList<FormItem>();
				fields.add(assegnazioneItem);
								
				if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {
					
					buildCheckboxListaPreferiti();
				
					for(int i = 0; i < listaCheckboxPreferiti.size(); i++){
						fields.add(listaCheckboxPreferiti.get(i));
					}
				}
				formAssegnazione.setFields(fields.toArray(new FormItem[fields.size()]));	
			}
		} else {		
			formAssegnazione.setFields(assegnazioneItem);	
		}
	}
	
	private void createFormMessaggio() {
		
		formMessaggio = new DynamicForm();													
		formMessaggio.setKeepInParentRect(true);
		formMessaggio.setWidth100();
		formMessaggio.setHeight100();
		formMessaggio.setNumCols(4);
		formMessaggio.setColWidths(120,"*","*","*");		
		formMessaggio.setWrapItemTitles(false);	
		formMessaggio.setValuesManager(vm);
		formMessaggio.setCellPadding(5);
		
		messaggioInvioItem = new TextAreaItem("messaggioInvio", "Messaggio");
		messaggioInvioItem.setStartRow(true);
		messaggioInvioItem.setLength(4000);
		messaggioInvioItem.setHeight(40);
		messaggioInvioItem.setColSpan(4);
		messaggioInvioItem.setWidth(750);			
		
		GWTRestDataSource livelloPrioritaDS = new GWTRestDataSource("LoadComboPrioritaInvioDataSource", "key", FieldType.TEXT);
		
		livelloPrioritaItem = new SelectItem("livelloPriorita", "Livello priorità"); 
		livelloPrioritaItem.setStartRow(true);
		livelloPrioritaItem.setOptionDataSource(livelloPrioritaDS);  
		livelloPrioritaItem.setAutoFetchData(true);
		livelloPrioritaItem.setDisplayField("value");
		livelloPrioritaItem.setValueField("key");			
		livelloPrioritaItem.setWidth(120);
		livelloPrioritaItem.setWrapTitle(false);
		livelloPrioritaItem.setColSpan(1);
		livelloPrioritaItem.setStartRow(true);
		livelloPrioritaItem.setAllowEmptyValue(true);
		
		formMessaggio.setFields(new FormItem[]{messaggioInvioItem, livelloPrioritaItem});
	}
	
	public RecordList getListaPreferiti() {
		return null;
	}

	public abstract void onClickOkButton(Record record, DSCallback callback);
	
	private void manageClickCheckboxEsclusive(){
		for(CheckboxItem check : listaCheckbox){
			check.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					
					String nameCheckboxClicked = event.getItem().getName();
					for(CheckboxItem check : listaCheckbox){
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
	
	private void buildCheckboxListaPreferiti(){
		for(int i = 0; i < getListaPreferiti().getLength();i++){
			Record currentRecord = getListaPreferiti().get(i);
			String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			CheckboxItem flgPreferitoItem = new CheckboxItem(tipoDestinatarioPreferito + idDestinatarioPreferito, descrizioneDestinatarioPreferito);
			flgPreferitoItem.setStartRow(true);
			flgPreferitoItem.setColSpan(6);
			listaCheckboxPreferiti.add(flgPreferitoItem);
		}
	}
	
	public RecordList getAssegnazioni() {
		if (getListaPreferiti() != null && !getListaPreferiti().isEmpty()) {		
			if(isAssegnazioneUnica()) {
				if(formAssegnazione.getValue("seleziona") != null && (Boolean) formAssegnazione.getValue("seleziona")) {
					return formAssegnazione.getValueAsRecordList("listaAssegnazioni");					
				} else { 
					RecordList listaAssegnazioni = new RecordList();
					for(int i=0; i < getListaPreferiti().getLength(); i++){
						Record currentRecord = getListaPreferiti().get(i);
						String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
						String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
						if(formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
							Record recordAssegnazioni = new Record();
							recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
							recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
							listaAssegnazioni.add(recordAssegnazioni);									
						}
					}	
					return listaAssegnazioni;		
				}
			} else {
				RecordList listaAssegnazioni =  formAssegnazione.getValueAsRecordList("listaAssegnazioni");	
				for(int i=0; i < getListaPreferiti().getLength(); i++){
					Record currentRecord = getListaPreferiti().get(i);
					String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
					String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
					if(formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito) != null && (Boolean) formAssegnazione.getValue(tipoDestinatarioPreferito + idDestinatarioPreferito)){
						Record recordAssegnazioni = new Record();
						recordAssegnazioni.setAttribute("typeNodo",tipoDestinatarioPreferito);
						recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
						listaAssegnazioni.add(recordAssegnazioni);									
					}
				}	
				return listaAssegnazioni;		
			}			
		} else {
			return formAssegnazione.getValueAsRecordList("listaAssegnazioni");				
		}	
	}
	
	public DynamicForm getFormMessaggio() {
		return formMessaggio;
	}

	public DynamicForm getFormAssegnazione() {
		return formAssegnazione;
	}

	public boolean isAssegnazioneUnica() {
		return ("F".equals(flgUdFolder) || isDocCartaceo());		
	}
	
	public boolean isDocCartaceo() {
		return "U".equals(flgUdFolder) && "C".equals(getCodSupportoOrig());		
	}
	
	public String getCodSupportoOrig() {
		return null;
	}
}