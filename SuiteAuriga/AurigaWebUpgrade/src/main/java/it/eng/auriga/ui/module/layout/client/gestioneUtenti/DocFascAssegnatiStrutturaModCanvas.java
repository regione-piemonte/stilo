/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DocFascAssegnatiStrutturaModCanvas extends ReplicableCanvas {
	
	private ReplicableCanvasForm formMain;
	
	// TextItem
	private TextItem codRapidoItem;
	private TextItem denominazioneUoItem;
	
	// HiddenItem
	private HiddenItem idUoItem;

    // CheckboxItem
	private CheckboxItem flgIncluseSottoUoItem;
	private CheckboxItem flgIncluseScrivanieItem;
	private CheckboxItem flgVisDocFascRisItem;
	
	// ImgButtonItem
	private ImgButtonItem modificaButton;
	
	@Override
	public void disegna() {
		
		formMain = new ReplicableCanvasForm();
		formMain.setWrapItemTitles(false);		
		
		buildItems();
		
		formMain.setFields(	   codRapidoItem,
				               denominazioneUoItem,
				               flgIncluseSottoUoItem,
				               flgIncluseScrivanieItem,
				               flgVisDocFascRisItem,
				               modificaButton,			             
				               idUoItem
		);			
		
		formMain.setNumCols(12);		
		addChild(formMain);
	}
	
	private void buildItems() {		
			
		idUoItem  = new HiddenItem("idUo");		
		
		codRapidoItem = new TextItem("codRapido",I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_codRapidoItem_title());  
		codRapidoItem.setTitleOrientation(TitleOrientation.TOP); 
		codRapidoItem.setWidth(120);
		
		denominazioneUoItem = new TextItem("denominazioneUo",I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_denominazioneUoItem_title());
		denominazioneUoItem.setTitleOrientation(TitleOrientation.TOP);
		denominazioneUoItem.setWidth(300);
		
        flgIncluseSottoUoItem = new CheckboxItem("flgIncluseSottoUo", "anche sotto-U.O."); 
        flgIncluseSottoUoItem.setTitleOrientation(TitleOrientation.TOP); 
        flgIncluseSottoUoItem.setWidth(130);
        
		flgIncluseScrivanieItem = new CheckboxItem("flgIncluseScrivanie", "anche alle postazioni utente");
		flgIncluseScrivanieItem.setTitleOrientation(TitleOrientation.TOP);
		flgIncluseScrivanieItem.setWidth(130);
		
		flgVisDocFascRisItem = new CheckboxItem("flgVisDocFascRis", "anche doc. e fasc. riservati"); 
		flgVisDocFascRisItem.setTitleOrientation(TitleOrientation.TOP);
		flgVisDocFascRisItem.setWidth(130);
		
		// Bottone MODIFICA
		modificaButton = new ImgButtonItem("modifica", "buttons/modify.png", "Modifica");
		modificaButton.setAlwaysEnabled(true);
		modificaButton.setColSpan(1);
		modificaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				String title = "Modifica associazione";
				Record record = new Record();
				
				record.setAttribute("idUo", formMain.getValue("idUo"));
				record.setAttribute("codRapido", formMain.getValue("codRapido"));
				record.setAttribute("denominazioneUo", formMain.getValue("denominazioneUo"));
				record.setAttribute("descrizione", formMain.getValue("denominazioneUo"));
				record.setAttribute("organigramma", formMain.getValue("denominazioneUo"));
				record.setAttribute("flgIncluseSottoUo", formMain.getValue("flgIncluseSottoUo") != null ? (Boolean) formMain.getValue("flgIncluseSottoUo") : false);
				record.setAttribute("flgIncluseScrivanie", formMain.getValue("flgIncluseScrivanie") != null? (Boolean) formMain.getValue("flgIncluseScrivanie") : false);
				record.setAttribute("flgVisDocFascRis", formMain.getValue("flgVisDocFascRis") != null ? (Boolean) formMain.getValue("flgVisDocFascRis") : false);
				
				String mode = ((ModificaDocumentiFascicoliStrutturaItem)getItem()).getMode();
					
				AgganciaDocumentiFascicoliStrutturaUOPopup agganciaUtenteUOPopup = new AgganciaDocumentiFascicoliStrutturaUOPopup(record, title, mode) {
					
					@Override
					public void onClickOkButton(Record formRecord, DSCallback callback) {
						Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
						Layout.hideWaitPopup();
						setFormValuesFromRecordAfterMod(formRecord);
						markForRedraw();
						markForDestroy();
					}
				};
				agganciaUtenteUOPopup.show();
			}
		});
		modificaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModUoAssociateUtente();
			}
		});
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {		
		return new ReplicableCanvasForm[]{formMain};
	}
	
	public boolean isAbilToModUoAssociateUtente() {
		return Layout.isPrivilegioAttivo("SIC/SO;M") && (((ModificaDocumentiFascicoliStrutturaItem)getItem()).isEditMode() || 
				((ModificaDocumentiFascicoliStrutturaItem)getItem()).isNewMode());
	}
	
	public boolean isAbilToDelUoAssociateUtente() {
		return Layout.isPrivilegioAttivo("SIC/SO;FC") && (((ModificaDocumentiFascicoliStrutturaItem)getItem()).isEditMode() || 
				((ModificaDocumentiFascicoliStrutturaItem)getItem()).isNewMode());
	}
	
	public void setFormValuesFromRecordAfterNew(Record record) {

        formMain.setValue("codRapido", record.getAttribute("codRapido"));
        String organigramma = record.getAttribute("organigramma");
        String idUo = togliPrefissoUO(organigramma);
        formMain.setValue("idUo", idUo);       
        formMain.setValue("denominazioneUo", record.getAttribute("denominazioneUo"));     
        formMain.setValue("flgIncluseSottoUo", record.getAttributeAsBoolean("flgIncluseSottoUo"));
        formMain.setValue("flgIncluseScrivanie", record.getAttributeAsBoolean("flgIncluseScrivanie"));
        formMain.setValue("flgVisDocFascRis", record.getAttributeAsBoolean("flgVisDocFascRis"));    
	}
	
	public void setFormValuesFromRecordAfterMod(Record record) {
		
		formMain.setValue("codRapido", record.getAttribute("codRapido"));
        formMain.setValue("idUo", record.getAttribute("idUo"));
        formMain.setValue("denominazioneUo", record.getAttribute("denominazioneUo"));
        formMain.setValue("flgIncluseSottoUo", record.getAttributeAsBoolean("flgIncluseSottoUo"));
        formMain.setValue("flgIncluseScrivanie", record.getAttributeAsBoolean("flgIncluseScrivanie"));
        formMain.setValue("flgVisDocFascRis", record.getAttributeAsBoolean("flgVisDocFascRis"));
	}	
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		codRapidoItem.setCanEdit(false);
		denominazioneUoItem.setCanEdit(false);
		flgIncluseSottoUoItem.setCanEdit(false);
		flgIncluseScrivanieItem.setCanEdit(false);
		flgVisDocFascRisItem.setCanEdit(false);
	}
	
    public String togliPrefissoUO(String stringIn){
    	String out = stringIn;
    	if(stringIn!=null && !stringIn.equalsIgnoreCase("") && stringIn.indexOf("UO") != -1 ){
    		out = stringIn.replaceAll("UO", "").trim();
    	}
    	return out;
    }
}