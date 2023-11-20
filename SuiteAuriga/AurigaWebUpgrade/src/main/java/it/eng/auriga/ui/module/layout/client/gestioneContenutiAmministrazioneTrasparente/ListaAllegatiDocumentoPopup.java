/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class ListaAllegatiDocumentoPopup extends ModalWindow {
	
	protected ListaAllegatiDocumentoPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected ListaFileItem listaFileGridItem;
	
	public ListaAllegatiDocumentoPopup(){
		
		super("lista_file_documento_popup", true);
		
		_window = this;
		
		setTitle(getWindowTitle());  	
		setAutoCenter(true);
		setWidth(600);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths("*");		
		_form.setCellPadding(2);
		_form.setWrapItemTitles(false);		
		
		listaFileGridItem = new ListaFileItem("listaAllegatiDocumento") {

			@Override
			void onRecordSelected(Record record) {
				_window.markForDestroy();	
				manageRecordSelection(record);				
			
			}
			
		};
		listaFileGridItem.setStartRow(true);
		listaFileGridItem.setHeight(345);
		
		_form.setFields(listaFileGridItem);
		 		
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
						
		setBody(portletLayout);
				
		setIcon("menu/file.png");
		
	}
	
	public String getWindowTitle() {
		return "Seleziona un file dalla lista";
	}
	
	public void initContent(Record record) {
		_form.setValue("listaAllegatiDocumento", record.getAttributeAsRecordArray("listaAllegati"));
	}

	public abstract void manageRecordSelection(Record record);
	
}
