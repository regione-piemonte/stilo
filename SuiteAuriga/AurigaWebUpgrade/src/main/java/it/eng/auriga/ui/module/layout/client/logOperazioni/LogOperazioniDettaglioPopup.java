/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public class LogOperazioniDettaglioPopup extends Window {
	
	private LogOperazioniDettaglioPopup window;
	
	private DynamicForm form; 
	private TextAreaItem testoItem;
	
	public LogOperazioniDettaglioPopup(String testo){
		
		window = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);		
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		

		testoItem = new TextAreaItem("testo");
		testoItem.setCanEdit(false);
		testoItem.setShowHintInField(true);
		testoItem.setShowTitle(false);
		testoItem.setColSpan(2);
		testoItem.setHeight(100);
		testoItem.setWidth(450);
		testoItem.setAlign(Alignment.CENTER);
		testoItem.setValue(testo);
		testoItem.setStartRow(true);
				
		form.setFields(testoItem);
		
		addItem(form);	
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
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
        _buttons.addMember(confermaButton);
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		setShowTitle(false);
		setHeaderIcon("blank.png");
		
		draw();
	}
	
}