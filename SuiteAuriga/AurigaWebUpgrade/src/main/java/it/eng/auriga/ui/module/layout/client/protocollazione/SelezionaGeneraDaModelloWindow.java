/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public abstract class SelezionaGeneraDaModelloWindow extends Window{
	
	private DynamicForm selezionaGeneraDaModelloForm;
	private SelectItem selezionaModelloItem;
	private HiddenItem tipoModelloItem;
	private HiddenItem flgConvertiInPdfItem;
	private ButtonItem caricaModelloButtonItem;
	
	private RecordList listaRecords;
	
	public SelezionaGeneraDaModelloWindow(RecordList listaRecords){
		
		setListaRecords(listaRecords);
		
		designWindow();
		
	}

	public RecordList getListaRecords() {
		return listaRecords;
	}

	public void setListaRecords(RecordList listaRecords) {
		this.listaRecords = listaRecords;
	}

	private void designWindow(){
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona modello");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		createForm();
		
		addItem(selezionaGeneraDaModelloForm);	
		
		setShowTitle(true);
		setHeaderIcon("menu/elenchiAlbi.png");
	}

	private DynamicForm createForm(){
		
		selezionaGeneraDaModelloForm = new DynamicForm();
		selezionaGeneraDaModelloForm.setAlign(Alignment.CENTER);	
		selezionaGeneraDaModelloForm.setWidth(100);  
		selezionaGeneraDaModelloForm.setNumCols(4);  
         
		tipoModelloItem = new HiddenItem("tipoModello");		
		flgConvertiInPdfItem = new HiddenItem("flgConvertiInPdf");
	        
        selezionaModelloItem = new SelectItem("idModello") {
        	@Override
        	public void onOptionClick(Record record) {
        		
        		selezionaModelloItem.setValue(record.getAttribute("idModello"));		
        		tipoModelloItem.setValue(record.getAttribute("tipoModello"));
        		flgConvertiInPdfItem.setValue(record.getAttribute("flgConvertiInPdf"));
        	}
        	@Override
			public void setValue(String value) {
				if (value == null || "".equals(value)) {
					selezionaModelloItem.setValue("");
					tipoModelloItem.setValue("");
	        		flgConvertiInPdfItem.setValue("");
				}				
			}

			@Override
			protected void clearSelect() {
				selezionaModelloItem.setValue("");
				tipoModelloItem.setValue("");
        		flgConvertiInPdfItem.setValue("");
			};
        };  
        selezionaModelloItem.setTitle("Seleziona modello");          
        selezionaModelloItem.setShowTitle(true);
        selezionaModelloItem.setWidth(250);
        selezionaModelloItem.setValueField("idModello");
        selezionaModelloItem.setDisplayField("nomeModello");	
        
//        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();        
//        for (int i = 0; i<listaRecords.getLength(); i++) {        	
//        	valueMap.put(listaRecords.get(i).getAttribute("idModello"), listaRecords.get(i).getAttribute("nomeModello"));          	
//		}        
//        selezionaModelloItem.setValueMap(valueMap);      
        
        ModelliDS modelliDS = new ModelliDS();     
        Record[] lRecordModelli = new Record[listaRecords.getLength()];
		for (int i = 0; i < listaRecords.getLength(); i++) {        				
			lRecordModelli[i] = listaRecords.get(i);          	
		}
        modelliDS.setTestData(lRecordModelli);
        selezionaModelloItem.setOptionDataSource(modelliDS);   
        
		caricaModelloButtonItem = new ButtonItem();
		caricaModelloButtonItem.setTitle(I18NUtil.getMessages().savePreferenceButton_title());
		caricaModelloButtonItem.setIcon("ok.png");
		caricaModelloButtonItem.setIconHeight(16);
		caricaModelloButtonItem.setIconWidth(16);
		caricaModelloButtonItem.setColSpan(4);
		caricaModelloButtonItem.setWidth(100);
		caricaModelloButtonItem.setTop(20);
		caricaModelloButtonItem.setAlign(Alignment.CENTER);
			
		caricaModelloButtonItem.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				String value = (String) selezionaModelloItem.getValue();
				if(value != null && !"".equals(value)){				
					caricaModelloSelezionato(value, (String) tipoModelloItem.getValue(), (String) flgConvertiInPdfItem.getValue());					
					markForDestroy();					
				} else {
					Layout.addMessage(new MessageBean("Nessun modello selezionato","", MessageType.ERROR));
				}
			}
			
		});
                  
		selezionaGeneraDaModelloForm.setWidth100();
		selezionaGeneraDaModelloForm.setHeight100();
		selezionaGeneraDaModelloForm.setNumCols(2);
		selezionaGeneraDaModelloForm.setColWidths(120, 120);
		selezionaGeneraDaModelloForm.setCellPadding(5);
		selezionaGeneraDaModelloForm.setCanSubmit(true);
		selezionaGeneraDaModelloForm.setItems(selezionaModelloItem, caricaModelloButtonItem);  
		selezionaGeneraDaModelloForm.setAlign(Alignment.CENTER);
		
        return selezionaGeneraDaModelloForm;
	}
	
	public abstract void caricaModelloSelezionato(String idModello, String tipoModello, String flgConvertiInPdf);
	
	public class ModelliDS extends DataSource {

		public ModelliDS() {

			DataSourceTextField idModello = new DataSourceTextField("idModello");
			idModello.setPrimaryKey(true);
			idModello.setHidden(true);
			
			DataSourceTextField nomeModello = new DataSourceTextField("nomeModello");
			
			DataSourceTextField flgConvertiInPdf = new DataSourceTextField("flgConvertiInPdf");
			flgConvertiInPdf.setHidden(true);
			
			DataSourceTextField tipoModello = new DataSourceTextField("tipoModello");
			tipoModello.setHidden(true);
			
			setFields(idModello, nomeModello, flgConvertiInPdf, tipoModello);
			
			setClientOnly(true);   
			
		}

	}
	
}
