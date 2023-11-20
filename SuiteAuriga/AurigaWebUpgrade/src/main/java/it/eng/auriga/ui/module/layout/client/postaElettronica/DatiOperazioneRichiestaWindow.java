/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class DatiOperazioneRichiestaWindow extends Window{
	
	private DynamicForm datiOperazioneRichiestaForm;
	private RadioGroupItem radioGroup = new RadioGroupItem("opzioni");
	private TextAreaItem motivazioneItem;
	private RecordList listaRecords;
	private CustomLayout customLayout;
	private boolean isMassive;
	private boolean isArchiviazione;
	private DettaglioPostaElettronica dettaglioPostaElettronica;
	private CheckboxItem lCheckboxRilascia;
	
	public DatiOperazioneRichiestaWindow(CustomLayout customLayout, RecordList listaRecords, boolean isMassive, boolean isArchiviazione) {
		
		this.listaRecords = listaRecords;
		this.customLayout = customLayout;
		this.isMassive = isMassive;
		this.isArchiviazione = isArchiviazione;
		
		disegna();
	
	}
	
	public DatiOperazioneRichiestaWindow(DettaglioPostaElettronica dettaglioPostaElettronica, RecordList listaRecords, boolean isArchiviazione) {
		
		this.listaRecords = listaRecords;
		this.dettaglioPostaElettronica = dettaglioPostaElettronica;
		this.isArchiviazione = isArchiviazione;
		
		disegna();
		
	}
	
	private void disegna(){
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(500);		
		setHeight(250);
		setKeepInParentRect(true);
		setTitle("Compila dati operazione richiesta");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowResizer(true);
		setCanDragResize(true);
		setShowFooter(true);
		datiOperazioneRichiestaForm = creaForm();
		
		datiOperazioneRichiestaForm.setAlign(Alignment.CENTER);	
		
		addItem(datiOperazioneRichiestaForm);	
		
	}
	
	private DynamicForm creaForm(){
		
		final DynamicForm form = new DynamicForm();  
        form.setNumCols(4);  
        
        // Opzioni 
        LinkedHashMap<String, String> radioGroupMap = new LinkedHashMap<String, String>();
        
        
        
        if(listaRecords.getLength() == 1)
        {
        	if(isArchiviazione)
        	{
        	
        		Record recordTmp = new Record((JavaScriptObject)listaRecords.get(0).getAttributeAsObject("azioneDaFareBean"));
        	
        		String codiceAzioneDaFare = recordTmp.getAttributeAsString("codAzioneDaFare");
        	
	        	if(codiceAzioneDaFare != null && !codiceAzioneDaFare.equals("")){
	        	
	        		radioGroupMap.put("COMPL", "Completa");  
	        	    radioGroupMap.put("ANN", "Annulla");  
	        	    
	        	    creaGruppoRadio(radioGroupMap, "Azione da fare");
	        		
	        	}
        	
	        	else radioGroup.setVisible(false);
	        		
        	}
        	else
        		radioGroup.setVisible(false);
        	
        }
        
        // più record
        else{
        	
        	if(isArchiviazione){
        		
	        	radioGroupMap.put("COMPL", "Completarla");  
	    	    radioGroupMap.put("ANN", "Annullarla");  
	    	    
	    	    creaGruppoRadio(radioGroupMap, "In caso sia specificata azione da fare aperta");
	    	    
	    	    radioGroup.setVisible(true);
        	}
        	else
        		
        		radioGroup.setVisible(false);
        }
		
	    	
		
		motivazioneItem = new TextAreaItem("motivazione", "Motivazione");
		motivazioneItem.setDefaultValue("");
		motivazioneItem.setShowTitle(true);				
		motivazioneItem.setHeight(80);
		motivazioneItem.setWidth(350);
		motivazioneItem.setColSpan(4);
		motivazioneItem.setAlign(Alignment.LEFT);
		motivazioneItem.setEndRow(true);	
		
		ButtonItem saveAzioneButtonItem = new ButtonItem();
		saveAzioneButtonItem.setTitle(I18NUtil.getMessages().savePreferenceButton_title());
		saveAzioneButtonItem.setIcon("ok.png");
		saveAzioneButtonItem.setIconHeight(16);
		saveAzioneButtonItem.setIconWidth(16);
		saveAzioneButtonItem.setColSpan(4);
		saveAzioneButtonItem.setWidth(100);
		saveAzioneButtonItem.setTop(20);
		saveAzioneButtonItem.setAlign(Alignment.CENTER);
		
		saveAzioneButtonItem.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(datiOperazioneRichiestaForm.validate())
				{
				
					final Record record = new Record();
					record.setAttribute("listaRecord", listaRecords);
					record.setAttribute("operazioneRichiesta", radioGroup.getValue());
					record.setAttribute("motivazione", motivazioneItem.getValue());
					
					Boolean flg = (Boolean)lCheckboxRilascia.getValue();
					
					if(isMassive)
					{
						record.setAttribute("flgRilascia", "1");
					}
					else
					{
						if(flg!=null && flg.booleanValue())
							record.setAttribute("flgRilascia", "2");
						else
							record.setAttribute("flgRilascia", "0");
					}
					
					markForDestroy();
					
					if(customLayout != null){
						
						if(customLayout instanceof PostaElettronicaLayout){
						
							if(isArchiviazione)
					
								((PostaElettronicaLayout)customLayout).archiviazioneEmail(record, isMassive);
							else	
								((PostaElettronicaLayout)customLayout).annullaArchiviazioneEmail(record, isMassive);
						}
						
						else if(customLayout instanceof ScrivaniaLayout){
							
							if(isArchiviazione)
								
								((ScrivaniaLayout)customLayout).archiviazioneEmail(record, isMassive);
							else	
								((ScrivaniaLayout)customLayout).annullaArchiviazioneEmail(record, isMassive);
	
						}
					}
					// vengo dal dettaglio	
					else
						
						if(isArchiviazione) {
							dettaglioPostaElettronica.archiviaMail(record);
						}
						else {
							dettaglioPostaElettronica.annullaArchiviazioneEmail(record);
						}
					
					postArchiviaMail();
						
				}
				
				
			}
					
		});
		
		
		lCheckboxRilascia = new CheckboxItem("flgRilascia", "Rilascia al termine dell’operazione");
		lCheckboxRilascia.setWrapTitle(false);
		lCheckboxRilascia.setColSpan(1);
		lCheckboxRilascia.setWidth("*");
		lCheckboxRilascia.setEndRow(true);
		
		lCheckboxRilascia.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form)
			{
				if(isMassive || isArchiviazione)
					return false;
				
				return true;
			}
		});
		
		
		SpacerItem lSpacerItem1 = new SpacerItem();
		lSpacerItem1.setColSpan(1);
		
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(120, 120);
		form.setCellPadding(5);
		form.setCanSubmit(true);
        form.setItems(radioGroup, motivazioneItem, lSpacerItem1,lCheckboxRilascia,saveAzioneButtonItem);  
        form.setAlign(Alignment.CENTER);
		
		return form;
		
	}
	
	public void postArchiviaMail () {
		
	}
	
	private void creaGruppoRadio(LinkedHashMap<String, String> mapRadio, String title){
		
		radioGroup.setValueMap(mapRadio); 
	    radioGroup.setVertical(false);
	    radioGroup.setTitle(title);
	    radioGroup.setWrap(false);	
	    if(mapRadio.containsKey(""))
	    	radioGroup.setDefaultValue("");	
	    radioGroup.setTitleVAlign(VerticalAlignment.TOP);
	    radioGroup.setWidth(100);
	    radioGroup.setEndRow(true);	
	   // radioGroup.setRequired(true);
	    
	    RadioButtonValidator validator = new RadioButtonValidator();
	    
	    radioGroup.setValidators(validator);
		
	}

	
	private class RadioButtonValidator extends CustomValidator
	{

		@Override
		protected boolean condition(Object value) 
		{
			
			if(listaRecords.getLength() == 1)
			{
				
				return value == null? false :true;
					
			}
			else
			{
				return value !=null ? true:  false;
			}
			
		}
		
	}
}

