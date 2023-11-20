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
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
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

public class ApposizioneTagCommentiMailWindow extends ModalWindow {
	
	private ValuesManager vm;
	private DynamicForm form;
	private ApposizioneTagCommentiMailItem apposizioneTagCommentiMailItem;
	private ApposizioneTagCommentiMailWindow _window;
	private DSCallback callback;

	public ApposizioneTagCommentiMailWindow(String nomeEntita,final RecordList listaEmail,DSCallback callback) {
		super(nomeEntita);
		
		setTitle(I18NUtil.getMessages().posta_elettronica_layout_apposizioneTagCommenti());
		setIcon("postaElettronica/apposizione_tag_commenti.png");
		
		vm = new ValuesManager();
		this._window = this;
		this.callback = callback;
		
		setAutoCenter(true);
		setWidth(850);
		setHeight(400);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		createForms();
		
		Button saveButton = new Button("Salva");
		saveButton.setIcon("ok.png");
		saveButton.setIconSize(16);
		saveButton.setAutoFit(false);
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				manageSaveTagCommenti(listaEmail);	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(saveButton);
		
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
		
		apposizioneTagCommentiMailItem = new ApposizioneTagCommentiMailItem();
		apposizioneTagCommentiMailItem.setShowTitle(false);
		apposizioneTagCommentiMailItem.setName("itemTagCommentiMail");
		apposizioneTagCommentiMailItem.setAttribute("obbligatorio", true);
		
		form.setFields(apposizioneTagCommentiMailItem);
	}
	
	private void manageSaveTagCommenti(final RecordList listaEmail){
		
		if(apposizioneTagCommentiMailItem.validate()){
			apposizioneTagCommentiMailItem.removeEmptyCanvas();
			final Record currentRecord = new Record(vm.getValues());
			currentRecord.setAttribute("itemTagCommentiMail", form.getValueAsRecordList("itemTagCommentiMail"));
			if(verificaCampiVuoti(currentRecord)){
				Record record = new Record();
				record.setAttribute("listaEmail", listaEmail);
				RecordList recordList = new RecordList();
				recordList.add(currentRecord);
				
				RecordList itemTagCommentiMailList = new RecordList();
				if(currentRecord != null && currentRecord.getAttributeAsRecordList("itemTagCommentiMail") != null
						&& !currentRecord.getAttributeAsRecordList("itemTagCommentiMail").isEmpty()){
					for(int i=0; i < currentRecord.getAttributeAsRecordList("itemTagCommentiMail").getLength(); i++){
						Record item = currentRecord.getAttributeAsRecordList("itemTagCommentiMail").get(i);
						Record itemTagCommentiMailBean = new Record();
						
						itemTagCommentiMailBean.setAttribute("flgInibitaModificaCancellazione", item.getAttributeAsString("flgInibitaModificaCancellazione"));
						itemTagCommentiMailBean.setAttribute("itemLavTag", item.getAttributeAsString("itemLavTag"));
						itemTagCommentiMailBean.setAttribute("itemLavCommento", item.getAttributeAsString("itemLavCommento"));
						
						itemTagCommentiMailList.add(itemTagCommentiMailBean);
					}
					record.setAttribute("itemTagCommentiMail", itemTagCommentiMailList);
				}
				
				GWTRestService<Record, Record> gwtRestDataSource = new GWTRestService<Record, Record>("ApposizioneTagCommentiMailDataSource");
				gwtRestDataSource.performCustomOperation("saveTagCommenti", record, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							Map<String,String> mapErrorMessages = result.getAttributeAsMap("errorMessages");
							if (mapErrorMessages != null && !mapErrorMessages.isEmpty()) {
								if(listaEmail.getLength() == 1){
									String error = null;
									for(Map.Entry<String, String> item : mapErrorMessages.entrySet()){
										error = item.getValue();
									}
									Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));
								}else{
									RecordList listaErrori = new RecordList();
									for(Map.Entry<String, String> item : mapErrorMessages.entrySet()){
										Record record = new Record();
										record.setAttribute("idError", item.getKey());
										record.setAttribute("descrizione", item.getValue());
										listaErrori.add(record); 
									}
									ErroreMassivoPopup erroreMassivo = new ErroreMassivoPopup("apposizioneTagCommentiMail",
											"Estremi email", listaErrori, mapErrorMessages.size(), 600, 300);
									erroreMassivo.show();
								}
							}else{
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_archiviazione_successo(), "", MessageType.INFO));
								callback.execute(response, null, new DSRequest());
								_window.markForDestroy();
							}
						}
					}
				}, new DSRequest());
			}else{
				Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_layout_apposizioneTagCommenti_validazione_errormsg()
						,"",MessageType.WARNING));
			}
		}
	}
	
	private Boolean verificaCampiVuoti(Record record){
		
		Boolean isValid = true;
		if(record != null && record.getAttributeAsRecordList("itemTagCommentiMail") != null
				&& !record.getAttributeAsRecordList("itemTagCommentiMail").isEmpty()){
			Record item = record.getAttributeAsRecordList("itemTagCommentiMail").get(0);
			if(item !=null && item.getAttributeAsString("itemLavTag") == null &&
					item.getAttributeAsString("itemLavCommento") == null){
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