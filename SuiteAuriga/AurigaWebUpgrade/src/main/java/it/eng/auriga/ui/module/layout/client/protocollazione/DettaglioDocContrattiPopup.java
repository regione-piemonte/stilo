/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class DettaglioDocContrattiPopup extends ModalWindow {
	
	private DettaglioDocContrattiPopup window;
	
	private DynamicForm form;
	private TextItem tipoBarcodeItem;
	private TextItem barcodeItem;
	private TextItem energiaGasItem;
	private TextItem tipoSezioneContrattoItem;
	private TextItem codContrattoItem;
	private TextItem flgPresentiFirmeContrattoItem;
	private TextItem flgFirmeCompleteContrattoItem;
	private NumericItem nroFirmePrevisteContrattoItem;
	private NumericItem nroFirmeCompilateContrattoItem;
	private HiddenItem flgIncertezzaRilevazioneFirmeContrattoItem;
	private ImgButtonItem iconIncertezzaRilevazioneContrattoItem;
	
	private HStack buttons;
	private Button annullaButton;
	
	public DettaglioDocContrattiPopup(Record record) {
		
		super("dettaglio_doc_contratti", true);
		
		window = this;
		
		setTitle("Dettaglio doc. contratti");

		setAutoCenter(true);
		
		setHeight(350);
		setWidth(550);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		createForm();
		
		editRecord(record);
		
		createButtons();
		
		buttons = createButtonStack();
		
		setLayout();
		
	}
	
	private void createForm() {
		
		form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(10);
		form.setColWidths("1","1","1","1","1","*","*","*","*","*");
		form.setCellPadding(5);
		form.setWrapItemTitles(false);
		
		tipoBarcodeItem = new TextItem("tipoBarcode", "Tipo barcode");
		tipoBarcodeItem.setWidth(100);
		tipoBarcodeItem.setColSpan(1);
		tipoBarcodeItem.setCanEdit(false);
		
		barcodeItem = new TextItem("barcode", "Barcode");
		barcodeItem.setWidth(200);
		barcodeItem.setColSpan(4);
		barcodeItem.setCanEdit(false);
		
		energiaGasItem = new TextItem("energiaGas", "Energia/Gas");
		energiaGasItem.setWidth(100);
		energiaGasItem.setColSpan(1);
		energiaGasItem.setStartRow(true);
		energiaGasItem.setCanEdit(false);
		
		tipoSezioneContrattoItem = new TextItem("tipoSezioneContratto", "Tipo di sezione/modello");
		tipoSezioneContrattoItem.setWidth(292);
		tipoSezioneContrattoItem.setColSpan(8);
		tipoSezioneContrattoItem.setStartRow(true);
		tipoSezioneContrattoItem.setCanEdit(false);
		
		codContrattoItem = new TextItem("codContratto", "Cod. contratto");
		codContrattoItem.setWidth(292);
		codContrattoItem.setColSpan(8);
		codContrattoItem.setStartRow(true);
		codContrattoItem.setCanEdit(false);
		
		flgPresentiFirmeContrattoItem = new TextItem("flgPresentiFirmeContratto", "Presenti firme");
		flgPresentiFirmeContrattoItem.setWidth(100);
		flgPresentiFirmeContrattoItem.setColSpan(1);
		flgPresentiFirmeContrattoItem.setStartRow(true);
		flgPresentiFirmeContrattoItem.setCanEdit(false);
		
		flgIncertezzaRilevazioneFirmeContrattoItem = new HiddenItem("flgIncertezzaRilevazioneFirmeContratto");
		
		iconIncertezzaRilevazioneContrattoItem = new ImgButtonItem("iconIncertezzaRilevazioneContratto", "exclamation.png", "Incertezza nella rilevazione aree firma");
		iconIncertezzaRilevazioneContrattoItem.setEndRow(false);
		iconIncertezzaRilevazioneContrattoItem.setColSpan(1);
		
		iconIncertezzaRilevazioneContrattoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgIncertezzaRilevazioneFirmeContrattoItem!=null && flgIncertezzaRilevazioneFirmeContrattoItem.getValue()!=null && 
						flgIncertezzaRilevazioneFirmeContrattoItem.getValue().equals("1");
			}
		});
		
		flgFirmeCompleteContrattoItem = new TextItem("flgFirmeCompleteContratto", "Firme complete");
		flgFirmeCompleteContrattoItem.setWidth(100);
		flgFirmeCompleteContrattoItem.setColSpan(1);
		flgFirmeCompleteContrattoItem.setCanEdit(false);
		
		nroFirmePrevisteContrattoItem = new NumericItem("nroFirmePrevisteContratto", "N° firme previste");
		nroFirmePrevisteContrattoItem.setWidth(100);
		nroFirmePrevisteContrattoItem.setStartRow(true);
		nroFirmePrevisteContrattoItem.setCanEdit(false);
		nroFirmePrevisteContrattoItem.setColSpan(3);
		
		nroFirmeCompilateContrattoItem = new NumericItem("nroFirmeCompilateContratto", "N° firme compilate");
		nroFirmeCompilateContrattoItem.setWidth(100);
		nroFirmeCompilateContrattoItem.setStartRow(true);
		nroFirmeCompilateContrattoItem.setCanEdit(false);
		nroFirmeCompilateContrattoItem.setColSpan(3);
		
		form.setFields(tipoBarcodeItem, barcodeItem, energiaGasItem, tipoSezioneContrattoItem, codContrattoItem,flgPresentiFirmeContrattoItem, flgIncertezzaRilevazioneFirmeContrattoItem, iconIncertezzaRilevazioneContrattoItem, flgFirmeCompleteContrattoItem,nroFirmePrevisteContrattoItem,nroFirmeCompilateContrattoItem);
	}
	
	private void editRecord(Record record) {
		if(record != null) {
			if(record.getAttributeAsString("tipoBarcode") != null) {
				tipoBarcodeItem.setValue(record.getAttributeAsString("tipoBarcode"));
			}
			if(record.getAttributeAsString("barcode") != null) {
				barcodeItem.setValue(record.getAttributeAsString("barcode"));
			}
			if(record.getAttributeAsString("energiaGas") != null) {
				energiaGasItem.setValue(record.getAttributeAsString("energiaGas"));
			}
			if(record.getAttributeAsString("tipoSezioneContratto") != null) {
				tipoSezioneContrattoItem.setValue(record.getAttributeAsString("tipoSezioneContratto"));
			}
			if(record.getAttributeAsString("codContratto") != null) {
				codContrattoItem.setValue(record.getAttributeAsString("codContratto"));
			}
			if(record.getAttributeAsString("flgPresentiFirmeContratto") != null ) {
				flgPresentiFirmeContrattoItem.setValue(record.getAttributeAsString("flgPresentiFirmeContratto"));
			}
			if(record.getAttributeAsString("flgFirmeCompleteContratto") != null ) {
				flgFirmeCompleteContrattoItem.setValue(record.getAttributeAsString("flgFirmeCompleteContratto"));
			}
			if(record.getAttributeAsString("nroFirmePrevisteContratto") != null ) {
				nroFirmePrevisteContrattoItem.setValue(record.getAttributeAsString("nroFirmePrevisteContratto"));
			}
			if(record.getAttributeAsString("nroFirmeCompilateContratto") != null ) {
				nroFirmeCompilateContrattoItem.setValue(record.getAttributeAsString("nroFirmeCompilateContratto"));
			}
			if(record.getAttributeAsString("flgIncertezzaRilevazioneFirmeContratto") != null ) {
				flgIncertezzaRilevazioneFirmeContrattoItem.setValue(record.getAttributeAsString("flgIncertezzaRilevazioneFirmeContratto"));
			}
			
			form.markForRedraw();
		}
	}
	
	private void setLayout(){
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(form);

		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);

		setBody(portletLayout);
		
		setIcon("blank.png");
	}
	
	private void createButtons() {
		
		annullaButton = new Button("Indietro"); 
		annullaButton.setIcon("buttons/undo.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
	}
	
	private HStack createButtonStack() {
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(annullaButton);
		
		return _buttons;
	}

}