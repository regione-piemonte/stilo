/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.utility.ui.module.layout.client.Layout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.FormItem;


public abstract class SubProfiliPopup extends ModalWindow {

	protected SubProfiliPopup _window;
	protected DynamicForm _form;
	private SelectItem subProfiloItem;
	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;

	public SubProfiliPopup() {
		
		super("subProfiliPopup", true);

		_window = this;
		
		// Creo il form 
		createForm();
		
		// Creo i due pulsanti
		createButtons();
		
		// Creo la bottoniera
		buttons = createButtonStack();

		setLayout();
	}

	private void createForm() {
		
		setTitle("Sub profili");

		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		setHeight(230);
		setWidth(750);
		
		//Per la rimozione degli elementi dal pulsante con la rotellina in alto a destra
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		_form = new DynamicForm();
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(1);
		_form.setColWidths("*","*");
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		
		// Combo dei sub profili
		
		GWTRestDataSource subProfiliUtenteDS = new GWTRestDataSource("LoadComboSubProfiloUtenteDataSource", "key", FieldType.TEXT);
		subProfiloItem = new SelectItem("listaSubProfili", I18NUtil.getMessages().gestioneutenti_subprofili_title());
		subProfiloItem.setMultiple(true);
		subProfiloItem.setOptionDataSource(subProfiliUtenteDS);  
		subProfiloItem.setAutoFetchData(true);
		subProfiloItem.setDisplayField("value");
		subProfiloItem.setValueField("key");			
		subProfiloItem.setWrapTitle(false);
		subProfiloItem.setCachePickListResults(false);
		subProfiloItem.setStartRow(true);
		subProfiloItem.setAllowEmptyValue(true);
		subProfiloItem.setAlwaysFetchMissingValues(true);
		subProfiloItem.setClearable(false);
		subProfiloItem.setRedrawOnChange(true);
		subProfiloItem.setWidth(600);   		
		subProfiloItem.setColSpan(10);
		subProfiloItem.setStartRow(true);
		
		//Aggiungo gli item al form
		_form.setFields(new FormItem[]{subProfiloItem});		
	}
	
	private void createButtons() {

		confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
					String[] listKey   = null;
					String[] listValue = null;					
					if (subProfiloItem.getValueAsString()!=null){
						listKey = subProfiloItem.getValueAsString().split(",");						
					}					
					if(subProfiloItem.getDisplayValue()!=null){
						listValue = subProfiloItem.getDisplayValue().split(",");
					}					
					RecordList lRecordListNew =  new RecordList();
					if (listKey!=null && listKey.length>0){
						int k = 0;
						for (String key : listKey){
							if (key != null && !key.equalsIgnoreCase("")){
								String value = listValue[k];
								Record recordSubProfilo = new Record();							
								recordSubProfilo.setAttribute("key",   key);
								recordSubProfilo.setAttribute("value", value);
								lRecordListNew.add(recordSubProfilo);	
								k++;
							}
						}
					}					
					onClickOkButton(lRecordListNew, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							_window.markForDestroy();
						}
					});	
			}
		});
		

		annullaButton = new Button("Annulla"); 
		annullaButton.setIcon("annulla.png");
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
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		return _buttons;
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
		
		layout.addMember(_form);

		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);

		setBody(portletLayout);
		
		setIcon("blank.png");
	}

	
	@Override
	public void manageOnCloseClick() {
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public DynamicForm getForm() {
		return _form;
	}
	
	public abstract void onClickOkButton(RecordList lRecordList, DSCallback callback);
}
