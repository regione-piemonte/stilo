/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class SoggettiGruppoEmailCanvas extends ReplicableCanvas{
	
	ReplicableCanvasForm mDynamicForm;
	HiddenItem idSoggettoGruppoItem;	
	HiddenItem tipoMembroItem;	
	TextItem denominazioneSoggettoItem;	
	TextItem indirizzoMailSoggettoItem;	
	ImgItem tipoMembroSoggettoImgButtonItem;
	ImgItem tipoMembroGruppoImgButtonItem;
	
	@Override
	public void disegna() {		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		idSoggettoGruppoItem 	= new HiddenItem("idSoggettoGruppo");
		
		//  TIPO MEMBRO ( S = Soggetto rubrica; G = Gruppo )
		tipoMembroItem = new HiddenItem("tipoMembro");
		tipoMembroItem.setDefaultValue("S"); 
		
		// Tipo = SOGGETTI
		tipoMembroSoggettoImgButtonItem = new ImgItem("tipoMembroSoggettoImgButton", "anagrafiche/rubrica_email/tipoIndirizzo/S.png", I18NUtil.getMessages().invioudmail_detail_tipoDestinatarioSingoloItem_title());
		tipoMembroSoggettoImgButtonItem.setColSpan(1);
		tipoMembroSoggettoImgButtonItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("tipoMembro")==null || form.getValue("tipoMembro").equals("") || (form.getValue("tipoMembro")!=null && form.getValue("tipoMembro").equals("S")) ;
			}
		});	

		// Tipo = GRUPPO
		tipoMembroGruppoImgButtonItem = new ImgItem("tipoMembroGruppoImgButton", "anagrafiche/rubrica_email/tipoIndirizzo/G.png", I18NUtil.getMessages().invioudmail_detail_tipoDestinatarioGruppoItem_title());
		tipoMembroGruppoImgButtonItem.setColSpan(1);
		tipoMembroGruppoImgButtonItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("tipoMembro")!=null && form.getValue("tipoMembro").equals("G");
			}
		});
	        
		// denominazione
		denominazioneSoggettoItem = new TextItem("denominazioneSoggetto",   I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());	   
		denominazioneSoggettoItem.setWidth(300);	
		denominazioneSoggettoItem.setCanEdit(false);
		
		// e-mail
		indirizzoMailSoggettoItem = new TextItem("indirizzoMailSoggetto", I18NUtil.getMessages().invioudmail_detail_indirizzoMailItem_title());	   
		indirizzoMailSoggettoItem.setWidth(300);	
		indirizzoMailSoggettoItem.setCanEdit(false);
		indirizzoMailSoggettoItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("tipoMembro")==null || form.getValue("tipoMembro").equals("") || (form.getValue("tipoMembro")!=null && form.getValue("tipoMembro").equals("S")) ;
			}
		});	
				
		mDynamicForm.setFields(
				idSoggettoGruppoItem, 
        		tipoMembroItem,
        		tipoMembroSoggettoImgButtonItem,
        		tipoMembroGruppoImgButtonItem,
        		denominazioneSoggettoItem,
        		indirizzoMailSoggettoItem
        		);
		
		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths("100", "50", "100", "50", "100",  "50", "100",  "100",  "100",  "100");
		
		addChild(mDynamicForm);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}	
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{mDynamicForm};
	}	
	

	protected void  clearFormSoggettoValues(Record lRecord){
		mDynamicForm.clearErrors(true);		
		lRecord.setAttribute("tipoMembro", "");
		lRecord.setAttribute("denominazioneSoggetto", "");
		lRecord.setAttribute("indirizzoMailSoggetto", "");
		mDynamicForm.setValues(lRecord.toMap());		
	}

	protected void  clearIdSoggetto(Record lRecord){
		mDynamicForm.clearErrors(true);		
		lRecord.setAttribute("idSoggettoGruppo", "");
		mDynamicForm.setValues(lRecord.toMap());		
	}

	
	public void setFormValuesFromRecordRubricaEmail(Record record) {					
		clearFormSoggettoValues(record);
		mDynamicForm.clearErrors(true);		
		mDynamicForm.setValue("tipoMembro", record.getAttribute("tipoIndirizzo"));
		mDynamicForm.setValue("idSoggettoGruppo", record.getAttribute("idVoceRubrica"));
		mDynamicForm.setValue("denominazioneSoggetto", record.getAttribute("nome"));			
		mDynamicForm.setValue("indirizzoMailSoggetto", record.getAttribute("indirizzoEmail"));
		mDynamicForm.markForRedraw();
	}	
	
	protected boolean isSoggetto(String tipoSoggetto){		
		if ("S".equals(tipoSoggetto)  ) 
			return true;
		else
			return false;
	}	
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		denominazioneSoggettoItem.setCanEdit(false);
		indirizzoMailSoggettoItem.setCanEdit(false);
	}
	
}
