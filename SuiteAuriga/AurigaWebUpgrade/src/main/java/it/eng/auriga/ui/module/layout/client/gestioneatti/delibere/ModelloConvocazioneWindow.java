/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class ModelloConvocazioneWindow extends ModalWindow {
	
	protected ModelloConvocazioneWindow _instance = this;
	
	protected DetailSection dettSeduteDetailSection;
	protected DynamicForm dettSeduteHtmlForm;
	protected CKEditorItem dettSeduteHtmlItem;
	
	protected DetailSection testoHtmlDetailSection;
	protected DynamicForm testoHtmlForm;
	protected CKEditorItem testoHtmlItem;
	
	private Boolean showEditorDettSedute;
	private String dettSeduteHtml;
	private String testoHtml;
	
	public ModelloConvocazioneWindow(Record record) { 
		
		super("modello_convocazione_window", true);
		
		this.showEditorDettSedute = record.getAttributeAsBoolean("showEditorDettSedute");
		this.dettSeduteHtml = record.getAttributeAsString("dettSeduteHtml");
		this.testoHtml = record.getAttributeAsString("testoHtml");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		setWidth(1000);
		setHeight(600);	
		setTop(50);  
//		setVisibility(Visibility.HIDDEN);  
//		setAlign(Alignment.CENTER);  
		setAnimateTime(1200); // milliseconds
//		setCanDragReposition(true);
//		setCanDragResize(true);
//		setKeepInParentRect(true);
//		setIsModal(false);
//		setAutoCenter(false);
//		setShowCloseButton(false);
//		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle("Dati convocazione");

		createDettSeduteHtmlForm();
		createTestoHtmlForm();
		
		dettSeduteDetailSection = new DetailSection("Dettaglio sedute", true, true, false, dettSeduteHtmlForm);	
		testoHtmlDetailSection = new DetailSection("Testo libero convocazione", true, true, false, testoHtmlForm);
		
		if(showEditorDettSedute) {
			layout.addMember(dettSeduteDetailSection);
		}
		layout.addMember(testoHtmlDetailSection);
		
		final DetailToolStripButton saveButton = new DetailToolStripButton("Genera", "ok.png");
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				Record recordToSave = new Record();
				
				recordToSave.setAttribute("showEditorDettSedute", showEditorDettSedute);
				if(showEditorDettSedute) {
					if(dettSeduteHtmlItem.getValue() == null || "".equalsIgnoreCase(dettSeduteHtmlItem.getValue())) {
						/*
						 * METTO IL CARATTERE VUOTO PER EVITARE ERRORE DI UNMARSHAL DI UNA SEZIONE CACHE VUOTA
						 */
						dettSeduteHtmlItem.setValue("<p> </p>");					
					}
					recordToSave.setAttribute("dettSeduteHtml", dettSeduteHtmlItem.getValue());
				}	
				
				if(testoHtmlItem.getValue() == null || "".equalsIgnoreCase(testoHtmlItem.getValue())) {
					/*
					 * METTO IL CARATTERE VUOTO PER EVITARE ERRORE DI UNMARSHAL DI UNA SEZIONE CACHE VUOTA
					 */
					testoHtmlItem.setValue("<p> </p>");					
				}
				recordToSave.setAttribute("testoModello", testoHtmlItem.getValue());
							
				saveButton.focusAfterGroup();
				manageOnOkButtonClick(recordToSave);
				markForDestroy();							
			}
		});
		
		DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		annullaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {				
				markForDestroy();
			}
		});
		
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(saveButton);
		_buttons.addMember(annullaButton);
		
		portletLayout.addMember(_buttons);
		
		addItem(portletLayout);
		
		setIcon("protocollazione/generaDaModello.png");	
	}
	
	protected void createDettSeduteHtmlForm() {
		
		dettSeduteHtmlForm = new DynamicForm();													
		dettSeduteHtmlForm.setKeepInParentRect(true);
		dettSeduteHtmlForm.setNumCols(5);
		dettSeduteHtmlForm.setColWidths("*","1","1","1","1");		
		dettSeduteHtmlForm.setCellPadding(5);		
		dettSeduteHtmlForm.setWrapItemTitles(false);
		dettSeduteHtmlForm.setBorder("1px");
		
		dettSeduteHtmlItem = new CKEditorItem("dettSedute", -1, "STANDARD", 8, -1, "", false, true);
		dettSeduteHtmlItem.setTitle("");
		dettSeduteHtmlItem.setWidth("100%");
		dettSeduteHtmlItem.setShowTitle(false);
		dettSeduteHtmlItem.setStartRow(true);
		dettSeduteHtmlItem.setCanEdit(true);
		dettSeduteHtmlItem.setValue(dettSeduteHtml);

		dettSeduteHtmlForm.setItems(dettSeduteHtmlItem);
	}
	
	protected void createTestoHtmlForm() {
		
		testoHtmlForm = new DynamicForm();													
		testoHtmlForm.setKeepInParentRect(true);
		testoHtmlForm.setNumCols(5);
		testoHtmlForm.setColWidths("*","1","1","1","1");		
		testoHtmlForm.setCellPadding(5);		
		testoHtmlForm.setWrapItemTitles(false);
		testoHtmlForm.setBorder("1px");
		
		testoHtmlItem = new CKEditorItem("testoModello", -1, "STANDARD", 8, -1, "", false, true);
		testoHtmlItem.setTitle("");
		testoHtmlItem.setWidth("100%");
		testoHtmlItem.setShowTitle(false);
		testoHtmlItem.setStartRow(true);
		testoHtmlItem.setCanEdit(true);
		testoHtmlItem.setValue(testoHtml);

		testoHtmlForm.setItems(testoHtmlItem);
	}
	
	public void manageOnOkButtonClick(Record values) {

	}
	
}