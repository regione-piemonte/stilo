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

public class RicevutePostaInUscitaDetail extends CustomDetail { 
		
	RicevutePostaInUscitaDetail _instance;
	
	DynamicForm soggettiForm;
	DynamicForm datiPrincipaliForm;
	DynamicForm validitaForm;
	
	// HiddenItem 
	private HiddenItem 		idEmailItem;					
	// TextItem 
	private TextItem 		categoriaItem;
	private TextItem 		mittenteItem;	
	// DateItem
	private DateItem 		dataRicezioneItem;     		
	// DetailSection
	protected DetailSection datiPrincipaliSection;
	
	protected boolean editing;
	
	public RicevutePostaInUscitaDetail(String nomeEntita) {		
		
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
        idEmailItem = new HiddenItem("idEmail");
                
		categoriaItem = new TextItem("categoria", I18NUtil.getMessages().ricevutepostainuscita_detail_categoriaItem_title());
		categoriaItem.setWidth(250);	
		categoriaItem.setStartRow(true);
		
        dataRicezioneItem = new DateItem("dataRicezione", I18NUtil.getMessages().ricevutepostainuscita_detail_dataRicezioneItem_title());
        dataRicezioneItem.setStartRow(true);
        
        mittenteItem = new TextItem("mittente", I18NUtil.getMessages().ricevutepostainuscita_detail_mittenteItem_title());
        mittenteItem.setWidth(250);	
        mittenteItem.setStartRow(true);
         
        // Aggiungo gli item al form
        datiPrincipaliForm.setItems(idEmailItem, categoriaItem, dataRicezioneItem, mittenteItem);
        
        // Aggiungo il form alla section
        datiPrincipaliSection    = new DetailSection(I18NUtil.getMessages().ricevutepostainuscita_detail_datiPrincipaliSection_title(), false, true, false, datiPrincipaliForm);      
		
		// Creo il VLAYOUT MAIN
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);			
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);			
		lVLayout.addMember(datiPrincipaliSection);	
		addMember(lVLayout);
		
		setCanEdit(true);
	}	

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
	}
}
