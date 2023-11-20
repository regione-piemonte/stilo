/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public abstract class NoteAttoPopup extends ModalWindow {
	
	private NoteAttoPopup window;
	
	private ValuesManager vm;
	private DynamicForm form; 
	private TextAreaItem noteAttoItem;
	
	private Button confermaButton;
	private Button chiudiButton;
	
	private Boolean fromStoricoSeduta;
	
	public NoteAttoPopup(Record record){
		
		super("note_atto_popup", true);
		window = this;
		vm = new ValuesManager();
		
		fromStoricoSeduta = record.getAttributeAsBoolean("storico") != null && record.getAttributeAsBoolean("storico");
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(150);
		setWidth(500);	
		setKeepInParentRect(true);		
		setTitle("Note atto in seduta");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setValuesManager(vm);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		// Note
		noteAttoItem = new TextAreaItem("noteAtto","");
		noteAttoItem.setShowTitle(false);
		noteAttoItem.setColSpan(2);
		noteAttoItem.setHeight(200);
		noteAttoItem.setWidth(600);
		noteAttoItem.setAlign(Alignment.CENTER);
		noteAttoItem.setStartRow(true);
		noteAttoItem.setCanEdit(!fromStoricoSeduta);
				
		form.setFields(noteAttoItem);
		
		addItem(form);	
				
		confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					
					final Record formRecord = new Record(form.getValues());
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							window.markForDestroy();
						}
					});					
				}
			}
		});		
		
		chiudiButton = new Button("Chiudi");   
		chiudiButton.setIcon("annulla.png");
		chiudiButton.setIconSize(16); 
		chiudiButton.setAutoFit(false);
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		if(!fromStoricoSeduta) {
			_buttons.addMember(confermaButton);
		}
		_buttons.addMember(chiudiButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("buttons/note_seduta.png");
		
		editRecord(record);
	}
	
	private void editRecord(Record record) {
		if (record!=null){
			vm.setValue("noteAtto",record.getAttribute("noteAtto"));
		}
	}
	
	public abstract void onClickOkButton(Record object, DSCallback callback);
	
}