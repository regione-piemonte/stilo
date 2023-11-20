/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ErroreProposteConcorrentiPopUp extends ModalWindow {

	public ErroreProposteConcorrentiPopUp(String nomeEntita, RecordList listaErrori, Integer larghezzaPopup, Integer altezzaPopup, String title) {
		
		super(nomeEntita, true, false);
	
		setModalMaskOpacity(50);
		setWidth(larghezzaPopup != null ? larghezzaPopup.intValue() : 600);		
		setHeight(altezzaPopup != null ? altezzaPopup.intValue() : 300);
		setKeepInParentRect(true);
		setTitle(title);
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
		
//		StaticTextItem messageItem = new StaticTextItem();			
//		messageItem.setTextAlign(Alignment.LEFT);		
//		messageItem.setValue("<span style=\"font-size:13;\">Sono andati in errore " + listaErrori.getLength() + " record su " + numeroRecordSelezionati +". Di seguito il dettaglio: </span>");
//		messageItem.setWidth(450);
//		messageItem.setWrap(false);
//		messageItem.setTop(15);
//		messageItem.setShowTitle(false);
//		
//		form.setItems(messageItem);
		
		ListGrid errorsGrid = new ListGrid(){
			
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				return "font-size:12;padding-right:20px;padding-left:5px";
			}
		};

		errorsGrid.setLayoutAlign(Alignment.CENTER);
		errorsGrid.setHeight("30%");
		errorsGrid.setWidth("80%");
		errorsGrid.setShowAllRecords(true); 
		errorsGrid.setWrapCells(true);
		errorsGrid.setFixedRecordHeights(false); 
		
		ListGridField capitoloProposta = new ListGridField("capitoloProposta", "Capitolo");
		capitoloProposta.setAutoFitWidth(false);
		capitoloProposta.setWidth("10%");
		capitoloProposta.setAlign(Alignment.LEFT);
		capitoloProposta.setWrap(true);
		
		ListGridField contoProposta = new ListGridField("contoProposta", "Conto");
		contoProposta.setAutoFitWidth(false);
		contoProposta.setWidth("20%");
		contoProposta.setAlign(Alignment.LEFT);
		contoProposta.setWrap(true);
		
		ListGridField estremiProposta = new ListGridField("estremiProposta", "Estremi proposta");
		estremiProposta.setAutoFitWidth(false);
		estremiProposta.setWidth("35%");
		estremiProposta.setAlign(Alignment.LEFT);
		estremiProposta.setWrap(true);

		ListGridField oggettoProposta = new ListGridField("oggettoProposta", "Oggetto proposta");
		oggettoProposta.setWidth("*");
		oggettoProposta.setAlign(Alignment.LEFT);
		oggettoProposta.setWrap(true);
		
		ListGridField importoProposta = new ListGridField("importoProposta", "Importo");
		importoProposta.setWidth("*");
		importoProposta.setAlign(Alignment.RIGHT);
		importoProposta.setWrap(true);
		
		errorsGrid.setFields(capitoloProposta, contoProposta, estremiProposta, oggettoProposta, importoProposta);
		errorsGrid.setData(listaErrori);
		errorsGrid.setWrapCells(true);
		
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
				manageOnClick();
				closeWindow();
			}
		});
		
		VLayout layout = new VLayout();  
		layout.setMembersMargin(10);
		layout.setPadding(10);
		layout.setHeight("90%");
		layout.setWidth100();
		layout.addMember(form);
		layout.addMember(errorsGrid);
		layout.addMember(closeButton);
		
		setBody(layout);
		setIcon("message/error.png");
	}
	
	public void closeWindow() {
		markForDestroy();
	}

	public void manageOnClick() {
		
	}

}
