/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

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
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class ValoriAmmessiPopup extends ModalWindow {
	
	private ValoriAmmessiPopup window;
	private ValuesManager vm;
	private ValoriAmmessiItem valoriAmmessiItem;
	private DetailSection detailSection;
	private DynamicForm formMain;
	
	private Button okButton;
	private Button annullaButton;
	
	public ValoriAmmessiPopup(String title, Record record, Boolean canEdit){
		
		super("valori_ammessi", true);
		
		window = this;
		
		vm = new ValuesManager();
		
		setHeight(300);
		setWidth(800);	
		
		setTitle(title);
		settingsMenu.removeItem(separatorMenuItem);
	    settingsMenu.removeItem(autoSearchMenuItem);
	    
	    disegnaForm();
		
		initForm(record);
				
		okButton = new Button("Ok");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(formMain.validate()) {
					final Record formRecord = new Record(formMain.getValues());
					
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							window.markForDestroy();
						}
					});	
				}
			}
		});
		
		annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
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
        _buttons.addMember(okButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(true);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.addMember(detailSection);
		addMember(layout);
		addMember(_buttons);
		
		setCanEdit(canEdit);
	}
	
	private void initForm(Record record) {
		RecordList valoriAmmessi = record != null ? record.getAttributeAsRecordList("valoriAmmessi") : null;
		if(valoriAmmessi != null) {
			valoriAmmessiItem.setAttribute("valoriAmmessi", valoriAmmessi);
			formMain.setValue("valoriAmmessi", valoriAmmessi.toArray());
		}
		formMain.markForRedraw();
	}
	
	private void disegnaForm() {

		formMain = new DynamicForm();
		formMain.setValuesManager(vm);
		formMain.setHeight("5");
		formMain.setPadding(5);
		formMain.setWrapItemTitles(false);
		formMain.setNumCols(3);
		formMain.setColWidths( 1, "*", "*");
    	
		valoriAmmessiItem = new ValoriAmmessiItem();
		valoriAmmessiItem.setName("valoriAmmessi");
		valoriAmmessiItem.setShowTitle(false);

		formMain.setFields(valoriAmmessiItem);
		
		detailSection = new DetailSection("Valori ammessi della colonna", false, true, false, formMain);
		detailSection.setViewReplicableItemHeight(300);
	}
	
	private void setCanEdit(Boolean canEdit) {
		if(canEdit) {
			okButton.show();
			annullaButton.show();
		} else {
			okButton.hide();
			annullaButton.hide();
		}
		setCanEdit(canEdit, formMain);		
	}
	
	private void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}
	
	public abstract void onClickOkButton(Record formRecord, DSCallback callback);
}