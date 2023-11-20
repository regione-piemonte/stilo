/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ListaWarningsPopup extends ModalWindow {

	public ListaWarningsPopup(String title, String message, RecordList listaWarnings, Integer larghezzaPopup, Integer altezzaPopup) {
		
		super("listaWarnings", true, false);
	
		setModalMaskOpacity(50);
		setWidth(larghezzaPopup != null ? larghezzaPopup.intValue() : 600);		
		setHeight(altezzaPopup != null ? altezzaPopup.intValue() : 300);
		setKeepInParentRect(true);
		setTitle(title != null ? title : "Avvertimenti");
		setShowModalMask(true);
		setShowCloseButton(true);
		
		setShowMaximizeButton(true);
		setShowMinimizeButton(false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		
		DynamicForm form = new DynamicForm();
		form.setItemLayout(FormLayoutType.ABSOLUTE);
		form.setHeight("5%");
		form.setWidth("80%");
		form.setLayoutAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		
		if(message != null && !"".equals(message)) {
			StaticTextItem messageItem = new StaticTextItem();		
			messageItem.setTextAlign(Alignment.LEFT);		
			messageItem.setValue("<span style=\"font-size:13;\">" + message + "</span>");
			messageItem.setWidth(450);
			messageItem.setWrap(false);
			messageItem.setTop(15);
			messageItem.setShowTitle(false);
			
			form.setItems(messageItem);
		}
		
		ListGrid listaWarningsGrid = new ListGrid(){
			
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				return "font-size:12;padding-right:20px;padding-left:5px";
			}
		};

		listaWarningsGrid.setLayoutAlign(Alignment.CENTER);
		listaWarningsGrid.setHeight("30%");
		listaWarningsGrid.setWidth("80%");
		listaWarningsGrid.setShowAllRecords(true); 
		listaWarningsGrid.setWrapCells(true);
		listaWarningsGrid.setFixedRecordHeights(false); 
		listaWarningsGrid.setShowHeader(false);
		
		ListGridField warning = new ListGridField("warning");
		warning.setWidth("*");
		warning.setAlign(Alignment.LEFT);
		warning.setWrap(true);
		
		listaWarningsGrid.setFields(warning);
		listaWarningsGrid.setData(listaWarnings);
		
		IButton closeButton = new IButton();
		closeButton.setLayoutAlign(Alignment.CENTER);
		closeButton.setTitle("Chiudi");
		closeButton.setIcon("ok.png");
		closeButton.setIconHeight(16);
		closeButton.setIconWidth(16);
		closeButton.setWidth(100);
		closeButton.setBottom(20);		
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				markForDestroy();
			}
		});
		
		VLayout layout = new VLayout();  
		layout.setMembersMargin(10);
		layout.setPadding(10);
		layout.setHeight("90%");
		layout.setWidth100();
		layout.addMember(form);
		layout.addMember(listaWarningsGrid);
		layout.addMember(closeButton);
		
		setBody(layout);
		setIcon("message/warning.png");
	}

}
