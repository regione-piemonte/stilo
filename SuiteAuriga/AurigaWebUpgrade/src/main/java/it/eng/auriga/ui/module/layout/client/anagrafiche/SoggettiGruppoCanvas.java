/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class SoggettiGruppoCanvas extends ReplicableCanvas{
	
	protected ReplicableCanvasForm mDynamicForm;
	protected HiddenItem idSoggettoGruppoItem;	
	protected HiddenItem tipoMembroItem;	
	protected ImgItem tipoMembroSoggettoImgButtonItem;
	protected ImgItem tipoMembroGruppoImgButtonItem;
	protected TextItem denominazioneSoggettoItem;
	protected TextItem codiceRapidoSoggettoItem;
	protected TextItem codfiscaleSoggettoItem;	 
	protected TextItem tipologiaSoggettoItem;	 
	protected HiddenItem flgInOrganigrammaItem;	 	
	protected ImgItem flgInOrganigrammaImgButtonItem ;
	
	@Override
	public void disegna() {		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		idSoggettoGruppoItem 	= new HiddenItem("idSoggettoGruppo");
		
		//  TIPO MEMBRO ( S = Soggetto rubrica; G = Gruppo )
		tipoMembroItem = new HiddenItem("tipoMembro");
		tipoMembroItem.setDefaultValue("S"); 
		
		// icona soggetto
		tipoMembroSoggettoImgButtonItem = new ImgItem("tipoMembroSoggettoImgButton", "menu/soggetti.png", I18NUtil.getMessages().gruppisoggetti_detail_tipoMembroSoggettoAlt_value());
		tipoMembroSoggettoImgButtonItem.setRedrawOnChange(true);
		tipoMembroSoggettoImgButtonItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
								
				return isSoggetto(tipoMembroItem.getValue().toString());
			}
		});		
		
		// icona gruppo
		tipoMembroGruppoImgButtonItem = new ImgItem("tipoMembroGruppoImgButton", "menu/gruppi_soggetti.png", I18NUtil.getMessages().gruppisoggetti_detail_tipoMembroGruppoAlt_value());	
		tipoMembroGruppoImgButtonItem.setRedrawOnChange(true);
		tipoMembroGruppoImgButtonItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
					
				return !isSoggetto(tipoMembroItem.getValue().toString());
			}
		});		
		
		// tipologia
		tipologiaSoggettoItem = new TextItem("tipologiaSoggetto");				
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();  				
		styleMap.put("#IAMM",I18NUtil.getMessages().soggetti_tipo_IAMM_value());
		styleMap.put("UO;UOI", I18NUtil.getMessages().soggetti_tipo_UOUOI_value());  
		styleMap.put("UP", "Unità di personale");
		styleMap.put("#APA", I18NUtil.getMessages().soggetti_tipo_APA_value());
		styleMap.put("#AF",	I18NUtil.getMessages().soggetti_tipo_AF_value());
		styleMap.put("#AG",	I18NUtil.getMessages().soggetti_tipo_AG_value());		
		styleMap.put("#APA", I18NUtil.getMessages().soggetti_tipo_APA_value());
		styleMap.put("#IAMM", I18NUtil.getMessages().soggetti_tipo_IAMM_value());	
		tipologiaSoggettoItem.setShowTitle(false);  		
		tipologiaSoggettoItem.setValueMap(styleMap);
		tipologiaSoggettoItem.setWidth(150);
		tipologiaSoggettoItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isSoggetto(tipoMembroItem.getValue().toString());
			}
		}); 
	        		
		// codice rapido 
		codiceRapidoSoggettoItem = new TextItem("codiceRapidoSoggetto",   I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());	   
		codiceRapidoSoggettoItem.setWidth(120);	
		//codiceRapidoSoggettoItem.disable();
		codiceRapidoSoggettoItem.setCanEdit(false);
		
		// denominazione
		denominazioneSoggettoItem = new TextItem("denominazioneSoggetto",   I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());	   
		denominazioneSoggettoItem.setWidth(300);	
		//denominazioneSoggettoItem.disable();
		denominazioneSoggettoItem.setCanEdit(false);
		
		// cod.fiscale
		codfiscaleSoggettoItem = new TextItem("codfiscaleSoggetto",   I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codfiscaleSoggettoItem.setWidth(150);
		codfiscaleSoggettoItem.setLength(16);
		//codfiscaleSoggettoItem.disable();
		codfiscaleSoggettoItem.setCanEdit(false);
		
		codfiscaleSoggettoItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isSoggetto(tipoMembroItem.getValue().toString());
			}
		});
		
		// IN ORGANIGRAMMA
		flgInOrganigrammaItem = new HiddenItem("flgInOrganigramma");
			
			// icona soggetto
		flgInOrganigrammaImgButtonItem = new ImgItem("flgInOrganigrammaImgButton", "anagrafiche/soggetti/soggInOrg.png", I18NUtil.getMessages().soggetti_list_flgInOrganigrammaField_title());
		flgInOrganigrammaImgButtonItem.setRedrawOnChange(true);
		flgInOrganigrammaImgButtonItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
								
				return flgInOrganigrammaItem.getValue() != null && "1".equals(flgInOrganigrammaItem.getValue());
			}
		});		
				
		mDynamicForm.setFields(
				idSoggettoGruppoItem, 
        		tipoMembroItem,
        		tipoMembroSoggettoImgButtonItem,
        		tipoMembroGruppoImgButtonItem,
        		tipologiaSoggettoItem,
        		codiceRapidoSoggettoItem,
        		denominazioneSoggettoItem, 
        		codfiscaleSoggettoItem,
        		flgInOrganigrammaItem,
        		flgInOrganigrammaImgButtonItem
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
		lRecord.setAttribute("codiceRapidoSoggetto", "");
		lRecord.setAttribute("denominazioneSoggetto", "");
		lRecord.setAttribute("codfiscaleSoggetto", "");
		mDynamicForm.setValues(lRecord.toMap());		
	}

	protected void  clearIdSoggetto(Record lRecord){
		mDynamicForm.clearErrors(true);		
		lRecord.setAttribute("idSoggettoGruppo", "");
		mDynamicForm.setValues(lRecord.toMap());		
	}

	
	public void setFormValuesFromRecordRubrica(Record record) {					
		clearFormSoggettoValues(record);
		mDynamicForm.clearErrors(true);		
		mDynamicForm.setValue("tipoMembro", "S");
		mDynamicForm.setValue("idSoggettoGruppo", record.getAttribute("idSoggetto"));
		mDynamicForm.setValue("codiceRapidoSoggetto", record.getAttribute("codiceRapido"));
		String denominazione = record.getAttribute("denominazione");
		if(denominazione == null || "".equals(denominazione)) {
			denominazione = record.getAttribute("cognome") + " " + record.getAttribute("nome");
		}		
		mDynamicForm.setValue("denominazioneSoggetto", denominazione);
		mDynamicForm.setValue("codfiscaleSoggetto",    record.getAttribute("codiceFiscale"));
		mDynamicForm.setValue("tipologiaSoggetto", record.getAttribute("tipo"));
		mDynamicForm.setValue("flgInOrganigramma", record.getAttribute("flgInOrganigramma"));	
	}	
	
	public void setFormValuesFromRecordGruppi(Record record) {					
		clearFormSoggettoValues(record);
		mDynamicForm.clearErrors(true);		
		mDynamicForm.setValue("tipoMembro", "G");
		mDynamicForm.setValue("idSoggettoGruppo", record.getAttribute("idGruppo"));
		mDynamicForm.setValue("codiceRapidoSoggetto", record.getAttribute("codiceRapido"));
		mDynamicForm.setValue("denominazioneSoggetto", record.getAttribute("nome"));	
		mDynamicForm.setValue("codfiscaleSoggetto", (String) null);
		mDynamicForm.setValue("tipologiaSoggetto", (String) null);
		mDynamicForm.setValue("flgInOrganigramma", (String) null);
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
		codiceRapidoSoggettoItem.setCanEdit(false);		
		denominazioneSoggettoItem.setCanEdit(false);
		codfiscaleSoggettoItem.setCanEdit(false);
		tipologiaSoggettoItem.setCanEdit(false);
	}
	
}
