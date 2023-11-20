/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public abstract class NewsDialogBox extends Dialog {
	
	private NewsDialogBox instance;
	private DynamicForm form;
	
	public NewsDialogBox(String title, String newsToShow) {
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setKeepInParentRect(true);		
		setTitle(title);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		setMessage(newsToShow);
		
		// nascondo i bottoni del Dialog
		setButtons();
		setShowToolbar(false);
		setShowCloseButton(false);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		
		CheckboxItem flgSkipNewsItem = new CheckboxItem("flgSkipNews", "non mostrare più questo messaggio");	
		flgSkipNewsItem.setWidth(5);
		
		form.setItems(flgSkipNewsItem);
				
		Button closeButton = new Button("Ok");   
		closeButton.setAutoFit(false);
		closeButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Record lRecord = new Record(form.getValues());
				Boolean flgSkipNews = lRecord.getAttributeAsBoolean("flgSkipNews");
				onClickButton(flgSkipNews != null && flgSkipNews);	
				instance.markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(20);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setAutoDraw(false);
		_buttons.setPadding(5);
		_buttons.addMember(closeButton);	
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(5);
		lVLayout.setLayoutTopMargin(5);
		lVLayout.setAlign(VerticalAlignment.CENTER);
		lVLayout.setOverflow(Overflow.VISIBLE);
		lVLayout.addMember(form);
		lVLayout.addMember(_buttons);	
				
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		draw();
		
		// se aggiungo il VLayout dopo il draw me lo mette in fondo, dopo il messaggio
		addItem(lVLayout);
		
		// faccio il resize per centrare il bottone nella maschera
		lVLayout.resizeTo(getWidthAsString(), "*");
	}

	public abstract void onClickButton(boolean flgSkipNews);
	
}
