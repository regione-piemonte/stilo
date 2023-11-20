/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class RubricaGruppoEmailDetail extends CustomDetail { 
		
	private RubricaGruppoEmailDetail _instance;
	
	DynamicForm soggettiForm;
	DynamicForm datiPrincipaliForm;
	
	protected SoggettiGruppoEmailItem soggettiItem;
	
	// HiddenItem 
	private HiddenItem idVoceRubricaItem;
	private HiddenItem tipoIndirizzoItem;
		
	// TextItem 
	private TextItem nomeItem;

	// DetailSection
	protected DetailSection datiPrincipaliSection;
	protected DetailSection soggettiSection;	
	
	protected boolean editing;
	
	public RubricaGruppoEmailDetail(String nomeEntita) {		
		
		super(nomeEntita);
		
		_instance = this;
				
        //******************************************************************************************
    	// Sezione DATI PRINCIPALI
        //******************************************************************************************
		
		// Creo il form
        datiPrincipaliForm = new DynamicForm();
        datiPrincipaliForm.setValuesManager(vm);
        datiPrincipaliForm.setWidth("100%"); 
        datiPrincipaliForm.setHeight(10);  
        datiPrincipaliForm.setPadding(5);
        datiPrincipaliForm.setNumCols(8);
        datiPrincipaliForm.setWrapItemTitles(false);

        // Creo gli item
        idVoceRubricaItem     = new HiddenItem("idVoceRubrica");
        
        tipoIndirizzoItem = new HiddenItem("tipoIndirizzo");
	    tipoIndirizzoItem.setValue("G");
	    
		nomeItem = new TextItem("nome", I18NUtil.getMessages().gruppisoggetti_detail_nomeItem_title());
        nomeItem.setRequired(true);
        nomeItem.setWidth(380);
        nomeItem.setStartRow(false);
        nomeItem.setEndRow(false);
       
        // Aggiungo gli item al form
        datiPrincipaliForm.setItems(idVoceRubricaItem, tipoIndirizzoItem, nomeItem);
        
        // Aggiungo il form alla section
        datiPrincipaliSection    = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_datiPrincipaliSection_title(), false, true, false, datiPrincipaliForm);        
        
        //******************************************************************************************
    	// Sezione SOGGETTI
    	//******************************************************************************************
        
        // Creo il form
        soggettiForm = new DynamicForm();
        soggettiForm.setValuesManager(vm);
        soggettiForm.setWidth("100%");
        soggettiForm.setHeight(10); 
        soggettiForm.setPadding(5);
        soggettiForm.setNumCols(8);
        soggettiForm.setWrapItemTitles(false);
                
        // Creo gli item
        soggettiItem = new SoggettiGruppoEmailItem();
        soggettiItem.setName("listaSoggettiGruppo");
        soggettiItem.setShowTitle(false);
        soggettiItem.setShowNewButton(true);
        
        // Aggiungo gli item al form
        soggettiForm.setFields(soggettiItem);        
                
        // Aggiungo il form alla section
		soggettiSection  = new DetailSection(I18NUtil.getMessages().gruppisoggetti_detail_soggettiSection_title(), true, true, false, soggettiForm);
		
		// Creo il VLAYOUT MAIN
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);				
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);			
		lVLayout.addMember(datiPrincipaliSection);	
		lVLayout.addMember(soggettiSection);	
		addMember(lVLayout);
		//addMember(lVLayoutSpacer);			
		
		setCanEdit(true);
	}
	

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
	}
}