/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class RichAnnullamentoRegPopup extends ModalWindow {
	
	protected RichAnnullamentoRegPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected Label label;	
	protected HiddenItem codCategoriaHiddenItem;
	protected HiddenItem siglaHiddenItem;
	protected HiddenItem annoHiddenItem;
	protected HiddenItem nroHiddenItem;
	protected TextAreaItem motiviRichAnnullamentoItem;	
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public RichAnnullamentoRegPopup(String segnatura, Record record){
		
		super("rich_annullamento_reg", true);
		
		_window = this;
		
		setTitle(getWindowTitle(segnatura, record));  	
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);		
		
		codCategoriaHiddenItem = new HiddenItem("codCategoria");
		siglaHiddenItem = new HiddenItem("sigla");
		annoHiddenItem = new HiddenItem("anno");
		nroHiddenItem = new HiddenItem("nro");
		
		motiviRichAnnullamentoItem = new TextAreaItem("motiviRichAnnullamento", "Motivi");		
		motiviRichAnnullamentoItem.setStartRow(true);
		motiviRichAnnullamentoItem.setLength(4000);
		motiviRichAnnullamentoItem.setHeight(40);
		motiviRichAnnullamentoItem.setColSpan(8);
		motiviRichAnnullamentoItem.setWidth(650);	
		motiviRichAnnullamentoItem.setRequired(true);
		
		_form.setFields(new FormItem[]{codCategoriaHiddenItem, siglaHiddenItem, annoHiddenItem, nroHiddenItem, motiviRichAnnullamentoItem});
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(_form.validate()) {
					onClickOkButton(new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							_window.markForDestroy();
						}
					});						
				}
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		 		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(_form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("protocollazione/richAnnullamento.png");
		
		_form.editRecord(record);
		
		show();
		
	}
	
	public String getWindowTitle(String segnatura, Record record) {
		if(record.getAttribute("motiviRichAnnullamento") != null && !"".equals(record.getAttribute("motiviRichAnnullamento"))) {
			return "Modifica motivi della richiesta di annullamento della registrazione " + segnatura;
		} else {
			return "Compila motivi della richiesta di annullamento della registrazione " + segnatura;
		}
	}

	public abstract void onClickOkButton(DSCallback callback);
	
}
