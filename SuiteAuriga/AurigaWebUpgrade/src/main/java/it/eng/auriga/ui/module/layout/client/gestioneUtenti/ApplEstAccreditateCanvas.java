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
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ApplEstAccreditateCanvas extends ReplicableCanvas{
	
	private ReplicableCanvasForm mDynamicForm;
	private TextItem codiceApplIstEstItem;
	private TextItem denominazioneApplEstItem;
	private TextItem idUtenteApplEstItem;
	private TextItem usernameUtenteApplEstItem;
	private TextItem uoPerRegDocItem;
	private HiddenItem codiceApplEstItem;
	private HiddenItem codiceIstApplItem;
	private HiddenItem idUoCollegataUtenteItem;
	private HiddenItem descrizioneUoCollegataUtenteItem;
	private HiddenItem passwordUtenteApplEstItem;
	private HiddenItem flgUsaCredenzialiDiverseAurigaItem;
	
	private ImgButtonItem modificaButton;
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		buildItems();
		
		mDynamicForm.setFields(	codiceApplIstEstItem,		             
		                        denominazioneApplEstItem,
		                        idUtenteApplEstItem,
		                        usernameUtenteApplEstItem,
		                        uoPerRegDocItem,
		                        modificaButton,
		                        codiceApplEstItem,
		                        codiceIstApplItem,
		                        passwordUtenteApplEstItem,
		                        idUoCollegataUtenteItem,
                           		descrizioneUoCollegataUtenteItem,
                           		flgUsaCredenzialiDiverseAurigaItem
		);	
							   
		mDynamicForm.setNumCols(10);		
		addChild(mDynamicForm);
	}
	
	private void buildItems() {
		
		codiceApplEstItem                   = new HiddenItem("codiceApplEst");
		codiceIstApplItem                   = new HiddenItem("codiceIstAppl");
		passwordUtenteApplEstItem           = new HiddenItem("passwordUtenteApplEst");
		idUoCollegataUtenteItem             = new HiddenItem("idUoCollegataUtente");
		descrizioneUoCollegataUtenteItem    = new HiddenItem("descrizioneUoCollegataUtente");
		flgUsaCredenzialiDiverseAurigaItem  = new HiddenItem("flgUsaCredenzialiDiverseAuriga");
		
		codiceApplIstEstItem      = new TextItem("codiceApplIstEst",       I18NUtil.getMessages().gestioneutenti_applEstAccred_codiceApplIstEstItem_title());                  codiceApplIstEstItem.setTitleOrientation(TitleOrientation.TOP); codiceApplIstEstItem.setWidth(110);
		denominazioneApplEstItem  = new TextItem("denominazioneApplEst",   I18NUtil.getMessages().gestioneutenti_applEstAccred_denominazioneApplEstItem_title());              denominazioneApplEstItem.setTitleOrientation(TitleOrientation.TOP); denominazioneApplEstItem.setWidth(220);
		idUtenteApplEstItem       = new TextItem("idUtenteApplEst",        I18NUtil.getMessages().gestioneutenti_applEstAccred_idUtenteApplEstItem_title());                   idUtenteApplEstItem.setTitleOrientation(TitleOrientation.TOP); idUtenteApplEstItem.setWidth(170);
		usernameUtenteApplEstItem = new TextItem("usernameUtenteApplEst",  I18NUtil.getMessages().gestioneutenti_applEstAccred_usernameUtenteApplEstItem_title());             usernameUtenteApplEstItem.setTitleOrientation(TitleOrientation.TOP); usernameUtenteApplEstItem.setWidth(190);
		uoPerRegDocItem           = new TextItem("uoPerRegDoc",            I18NUtil.getMessages().gestioneutenti_applEstAccred_uoPerRegDocItem_title());                       uoPerRegDocItem.setTitleOrientation(TitleOrientation.TOP); uoPerRegDocItem.setWidth(370);
		
		// Bottone MODIFICA
		modificaButton = new ImgButtonItem("modifica", "buttons/modify.png", "Modifica");
		modificaButton.setAlwaysEnabled(true);
		modificaButton.setColSpan(1);
		modificaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record recordDetail  = ((ApplEstAccreditateItem)getItem()).getDetailRecord();
				String utente   = recordDetail.getAttribute("cognome") +  " " + recordDetail.getAttribute("nome");
				String username = recordDetail.getAttribute("username");
				String title = "Associazione " + utente + "["+username+"]" +" vs applicazione abilitata ai servizi"; 
				
				String idUser = recordDetail.getAttribute("idUser");
				
				Record record = new Record();
				
				String codiceApplIstEst = "|*|";
				
				if(mDynamicForm.getValue("codiceApplEst")!=null && !mDynamicForm.getValue("codiceApplEst").toString().equalsIgnoreCase("") )
					codiceApplIstEst = mDynamicForm.getValue("codiceApplEst") + codiceApplIstEst;
				
				if(mDynamicForm.getValue("codiceIstAppl")!=null && !mDynamicForm.getValue("codiceIstAppl").toString().equalsIgnoreCase(""))
					codiceApplIstEst = codiceApplIstEst + mDynamicForm.getValue("codiceIstAppl");
				
				record.setAttribute("codiceApplIstEst",               codiceApplIstEst);
				record.setAttribute("denominazioneApplEst",           mDynamicForm.getValue("denominazioneApplEst"));
				record.setAttribute("idUtenteApplEst",                mDynamicForm.getValue("idUtenteApplEst"));
				record.setAttribute("usernameUtenteApplEst",          mDynamicForm.getValue("usernameUtenteApplEst"));
				record.setAttribute("passwordUtenteApplEst",          mDynamicForm.getValue("passwordUtenteApplEst"));
				record.setAttribute("idUoCollegataUtente",            mDynamicForm.getValue("idUoCollegataUtente"));
				record.setAttribute("descrizioneUoCollegataUtente",   mDynamicForm.getValue("descrizioneUoCollegataUtente"));
				record.setAttribute("flgUsaCredenzialiDiverseAuriga", mDynamicForm.getValue("flgUsaCredenzialiDiverseAuriga"));
				record.setAttribute("idUser", idUser);
				
				AgganciaUtenteApplicazioneEstPopup agganciaUtenteApplicazioneEstPopup = new AgganciaUtenteApplicazioneEstPopup(record, title, ((ApplEstAccreditateItem)getItem()).getMode()) {

					@Override
					public void onClickOkButton(Record formRecord, DSCallback callback) {
						Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
						Layout.hideWaitPopup();
						setFormValuesFromRecordAfterMod(formRecord);
						markForDestroy();
					}
				};
				agganciaUtenteApplicazioneEstPopup.show();
			}
		});
		modificaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilToModApplEstAccred();
			}
		});
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	public  boolean isAbilToModApplEstAccred() {
		
		return Layout.isPrivilegioAttivo("SIC/UT;M") && (((ApplEstAccreditateItem)getItem()).isEditMode() || ((ApplEstAccreditateItem)getItem()).isNewMode());
	}
	
	public  boolean isAbilToDelApplEstAccred() {
		
		return Layout.isPrivilegioAttivo("SIC/UT;FC") && (((ApplEstAccreditateItem)getItem()).isEditMode() || ((ApplEstAccreditateItem)getItem()).isNewMode());
	}
	
	public void setFormValuesFromRecordAfterNew(Record record) {
		String applicazioniEsterne ="";
		String codiceApplEst = "";
		String codiceIstAppl = "";
		
		if (record.getAttribute("applicazioniEsterne")!=null && !record.getAttribute("applicazioniEsterne").equalsIgnoreCase("")) {
			applicazioniEsterne = record.getAttributeAsString("applicazioniEsterne");	
			
			if ( applicazioniEsterne.indexOf("|*|") >0 ){
				applicazioniEsterne = applicazioniEsterne.replace("|*|", ":");
				
				String[] splitter = applicazioniEsterne.split(":");
				
				if (splitter.length >0 && splitter[0]!=null && !splitter[0].equalsIgnoreCase(""))
					codiceApplEst = splitter[0];
				
				if (splitter.length >1 && splitter[1]!=null && !splitter[1].equalsIgnoreCase(""))
					codiceIstAppl = splitter[1];
			}
		}
		
		String codiceApplIstEst = "";
		if (!codiceApplEst.equalsIgnoreCase("")){
			codiceApplIstEst = codiceApplEst;
			if (!codiceIstAppl.equalsIgnoreCase("")){
				codiceApplIstEst = codiceApplIstEst + "-" + codiceIstAppl;
			}
		}		
		mDynamicForm.setValue("codiceApplEst", codiceApplEst);
		mDynamicForm.setValue("codiceIstAppl", codiceIstAppl);
		mDynamicForm.setValue("codiceApplIstEst", codiceApplIstEst);
		mDynamicForm.setValue("denominazioneApplEst", record.getAttribute("denominazioneApplEst"));
		mDynamicForm.setValue("idUtenteApplEst", record.getAttribute("idUtenteApplEst"));
		mDynamicForm.setValue("usernameUtenteApplEst", record.getAttribute("usernameUtenteApplEst"));
		mDynamicForm.setValue("passwordUtenteApplEst", record.getAttribute("passwordUtenteApplEst"));
		mDynamicForm.setValue("idUoCollegataUtente", record.getAttribute("idUoCollegataUtente"));
		mDynamicForm.setValue("descrizioneUoCollegataUtente", record.getAttribute("descrizioneUoCollegataUtente"));
		mDynamicForm.setValue("uoPerRegDoc", record.getAttribute("descrizioneUoCollegataUtente"));
		mDynamicForm.setValue("flgUsaCredenzialiDiverseAuriga", record.getAttribute("flgUsaCredenzialiDiverseAuriga"));
	}
	
	public void setFormValuesFromRecordAfterMod(Record record) {
		
		String applicazioniEsterne ="";
		String codiceApplEst = "";
		String codiceIstAppl = "";
				
		if (record.getAttribute("applicazioniEsterne")!=null && !record.getAttribute("applicazioniEsterne").equalsIgnoreCase("")) {
			applicazioniEsterne = record.getAttributeAsString("applicazioniEsterne");	
			
			if ( applicazioniEsterne.indexOf("|*|") >0 ){
				applicazioniEsterne = applicazioniEsterne.replace("|*|", ":");
				
				String[] splitter = applicazioniEsterne.split(":");
				
				if (splitter.length >0 && splitter[0]!=null && !splitter[0].equalsIgnoreCase(""))
					codiceApplEst = splitter[0];
				
				if (splitter.length >1 && splitter[1]!=null && !splitter[1].equalsIgnoreCase(""))
					codiceIstAppl = splitter[1];
			}
		}
		
		String codiceApplIstEst = "";
		if (!codiceApplEst.equalsIgnoreCase("")){
			codiceApplIstEst = codiceApplEst;
			if (!codiceIstAppl.equalsIgnoreCase("")){
				codiceApplIstEst = codiceApplIstEst + "-" + codiceIstAppl;
			}
		}
		
		mDynamicForm.setValue("codiceApplEst", codiceApplEst);
		mDynamicForm.setValue("codiceIstAppl", codiceIstAppl);
		mDynamicForm.setValue("codiceApplIstEst", codiceApplIstEst);
		mDynamicForm.setValue("denominazioneApplEst", record.getAttribute("denominazioneApplEst"));
		mDynamicForm.setValue("idUtenteApplEst", record.getAttribute("idUtenteApplEst"));
		mDynamicForm.setValue("usernameUtenteApplEst", record.getAttribute("usernameUtenteApplEst"));
		mDynamicForm.setValue("passwordUtenteApplEst", record.getAttribute("passwordUtenteApplEst"));
		mDynamicForm.setValue("idUoCollegataUtente", record.getAttribute("idUoCollegataUtente"));
		mDynamicForm.setValue("descrizioneUoCollegataUtente", record.getAttribute("descrizioneUoCollegataUtente"));
		mDynamicForm.setValue("uoPerRegDoc", record.getAttribute("descrizioneUoCollegataUtente"));
		mDynamicForm.setValue("flgUsaCredenzialiDiverseAuriga", record.getAttribute("flgUsaCredenzialiDiverseAuriga"));
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		codiceApplIstEstItem.setCanEdit(false);
		denominazioneApplEstItem.setCanEdit(false);
		idUtenteApplEstItem.setCanEdit(false);
		usernameUtenteApplEstItem.setCanEdit(false);
		uoPerRegDocItem.setCanEdit(false);
	}
}