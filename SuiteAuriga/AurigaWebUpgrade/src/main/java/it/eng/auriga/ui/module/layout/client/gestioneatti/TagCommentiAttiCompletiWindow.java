/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

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
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class TagCommentiAttiCompletiWindow extends ModalWindow {
	
	private ValuesManager vm;
	private DynamicForm form;
	private TagCommentiAttiCompletiItem tagCommentiAttiCompletiItem;
	private TagCommentiAttiCompletiWindow _window;
	private DSCallback callback;
	private String operazione;

	public TagCommentiAttiCompletiWindow(String nomeEntita,String operazione,final RecordList listaAtti,DSCallback callback) {
		super(nomeEntita);
		
		vm = new ValuesManager();
		this._window = this;
		this.callback = callback;
		this.operazione = operazione;
		
		if("APPOSIZIONE_TAG".equalsIgnoreCase(operazione)) {
			setTitle("Seleziona tag da apporre");
			setIcon("postaElettronica/apposizione_tag_commenti.png");
		} else {
			setTitle("Seleziona tag da eliminare");
			setIcon("postaElettronica/eliminazione_tag_commenti.png");
		}
		
		setAutoCenter(true);
		setWidth(850);
		setHeight(400);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		createForms();
		
		Button saveButton = new Button("Apponi tag");
		saveButton.setIcon("ok.png");
		saveButton.setIconSize(16);
		saveButton.setAutoFit(false);
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				manageSaveTag(listaAtti);	
			}
		});
		
		Button eliminaButton = new Button("Elimina tag");
		eliminaButton.setIcon("ok.png");
		eliminaButton.setIconSize(16);
		eliminaButton.setAutoFit(false);
		eliminaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				manageEliminaTag(listaAtti);	
			}
		});
		
		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		if("APPOSIZIONE_TAG".equalsIgnoreCase(operazione)) {
			_buttons.addMember(saveButton);
		} else {
			_buttons.addMember(eliminaButton);
		}
		_buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.addMember(form);

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);
		setBody(portletLayout);
		
		show();
	}
	
	private void createForms(){
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setValuesManager(vm);
		form.setPadding(5);
		form.setTop(50);
		
		tagCommentiAttiCompletiItem = new TagCommentiAttiCompletiItem();
		tagCommentiAttiCompletiItem.setShowTitle(false);
		tagCommentiAttiCompletiItem.setName("itemTagAttiCompleti");
		tagCommentiAttiCompletiItem.setAttribute("obbligatorio", true);
		
		form.setFields(tagCommentiAttiCompletiItem);
	}
	
	private void manageSaveTag(final RecordList listaRecord){
		
		if(tagCommentiAttiCompletiItem.validate()){
			tagCommentiAttiCompletiItem.removeEmptyCanvas();
			final Record currentRecord = new Record(vm.getValues());
			currentRecord.setAttribute("itemTagAttiCompleti", form.getValueAsRecordList("itemTagAttiCompleti"));
			if(verificaCampiVuoti(currentRecord)){
				Record record = new Record();
				record.setAttribute("listaRecord", listaRecord);
				RecordList recordList = new RecordList();
				recordList.add(currentRecord);
				
				RecordList itemTagAttiCompletiList = new RecordList();
				if(currentRecord != null && currentRecord.getAttributeAsRecordList("itemTagAttiCompleti") != null
						&& !currentRecord.getAttributeAsRecordList("itemTagAttiCompleti").isEmpty()){
					for(int i=0; i < currentRecord.getAttributeAsRecordList("itemTagAttiCompleti").getLength(); i++){
						Record item = currentRecord.getAttributeAsRecordList("itemTagAttiCompleti").get(i);
						Record itemTagAttiCompletiBean = new Record();					
						itemTagAttiCompletiBean.setAttribute("itemLavTag", item.getAttributeAsString("itemLavTag"));
						
						itemTagAttiCompletiList.add(itemTagAttiCompletiBean);
					}
					record.setAttribute("itemTagAttiCompleti", itemTagAttiCompletiList);
				}
				
				GWTRestService<Record, Record> gwtRestDataSource = new GWTRestService<Record, Record>("AzioneSuListaDocAttiCompletiDataSource");
				record.setAttribute("azione", "APPOSIZIONE_TAG");
				gwtRestDataSource.performCustomOperation("saveTagCommenti", record, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							Map<String,String> mapErrorMessages = result.getAttributeAsMap("errorMessages");
							if (mapErrorMessages != null && !mapErrorMessages.isEmpty()) {
								if(listaRecord.getLength() == 1){
									String error = null;
									for(Map.Entry<String, String> item : mapErrorMessages.entrySet()){
										error = item.getValue();
									}
									Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
								} else {
									RecordList listaErrori = new RecordList();
									for(Map.Entry<String, String> item : mapErrorMessages.entrySet()){
										Record record = new Record();
										record.setAttribute("idError", item.getKey());
										record.setAttribute("descrizione", item.getValue());
										listaErrori.add(record); 
									}
									ErroreMassivoPopup erroreMassivo = new ErroreMassivoPopup("apposizioneTagAttiCompleti",
											"Estremi atto/proposta", listaErrori, mapErrorMessages.size(), 600, 300);
									erroreMassivo.show();
								}
							}else{
								Layout.addMessage(new MessageBean("Operazione avvenuta con successo", "", MessageType.INFO));
								callback.execute(response, null, new DSRequest());
								_window.markForDestroy();
							}
						}
					}
				}, new DSRequest());
			} else {
				Layout.addMessage(new MessageBean("Attenzione, il campo Tag deve essere obbligatoriamente indicato" ,"",MessageType.WARNING));
			}
		}
	}
	
	private void manageEliminaTag(final RecordList listaRecord){
		
		if(tagCommentiAttiCompletiItem.validate()){
			tagCommentiAttiCompletiItem.removeEmptyCanvas();
			final Record currentRecord = new Record(vm.getValues());
			currentRecord.setAttribute("itemTagAttiCompleti", form.getValueAsRecordList("itemTagAttiCompleti"));
			if(verificaCampiVuoti(currentRecord)){
				Record record = new Record();
				record.setAttribute("listaRecord", listaRecord);
				RecordList recordList = new RecordList();
				recordList.add(currentRecord);
				
				RecordList itemTagAttiCompletiList = new RecordList();
				if(currentRecord != null && currentRecord.getAttributeAsRecordList("itemTagAttiCompleti") != null
						&& !currentRecord.getAttributeAsRecordList("itemTagAttiCompleti").isEmpty()){
					for(int i=0; i < currentRecord.getAttributeAsRecordList("itemTagAttiCompleti").getLength(); i++){
						Record item = currentRecord.getAttributeAsRecordList("itemTagAttiCompleti").get(i);
						Record itemTagAttiCompletiBean = new Record();					
						itemTagAttiCompletiBean.setAttribute("itemLavTag", item.getAttributeAsString("itemLavTag"));
						
						itemTagAttiCompletiList.add(itemTagAttiCompletiBean);
					}
					record.setAttribute("itemTagAttiCompleti", itemTagAttiCompletiList);
				}
				
				GWTRestService<Record, Record> gwtRestDataSource = new GWTRestService<Record, Record>("AzioneSuListaDocAttiCompletiDataSource");
				record.setAttribute("azione", "ELIMINAZIONE_TAG");
				gwtRestDataSource.performCustomOperation("eliminaTagCommenti", record, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							Map<String,String> mapErrorMessages = result.getAttributeAsMap("errorMessages");
							if (mapErrorMessages != null && !mapErrorMessages.isEmpty()) {
								if(listaRecord.getLength() == 1){
									String error = null;
									for(Map.Entry<String, String> item : mapErrorMessages.entrySet()){
										error = item.getValue();
									}
									Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
								} else {
									RecordList listaErrori = new RecordList();
									for(Map.Entry<String, String> item : mapErrorMessages.entrySet()){
										Record record = new Record();
										record.setAttribute("idError", item.getKey());
										record.setAttribute("descrizione", item.getValue());
										listaErrori.add(record); 
									}
									ErroreMassivoPopup erroreMassivo = new ErroreMassivoPopup("eliminazioneTagAttiCompleti",
											"Estremi atto/proposta", listaErrori, mapErrorMessages.size(), 600, 300);
									erroreMassivo.show();
								}
							} else {
								Layout.addMessage(new MessageBean("Operazione avvenuta con successo", "", MessageType.INFO));
								callback.execute(response, null, new DSRequest());
								_window.markForDestroy();
							}
						}
					}
				}, new DSRequest());
			} else {
				Layout.addMessage(new MessageBean("Attenzione, il campo Tag deve essere obbligatoriamente indicato" ,"",MessageType.WARNING));
			}
		}
	}
	
	private Boolean verificaCampiVuoti(Record record){
		
		Boolean isValid = true;
		if(record != null && record.getAttributeAsRecordList("itemTagAttiCompleti") != null
				&& !record.getAttributeAsRecordList("itemTagAttiCompleti").isEmpty()){
			Record item = record.getAttributeAsRecordList("itemTagAttiCompleti").get(0);
			if(item !=null && item.getAttributeAsString("itemLavTag") == null ){
				isValid = false;
			}
		}
		return isValid;
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