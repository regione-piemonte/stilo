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
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;

public class TipiDocDetail extends CustomDetail {
	
	protected TipiDocDetail _instance;
	
	// DynamicForm 
	protected DynamicForm tipiDocForm;
	
	// DetailSection
	protected DetailSection tipiDocSection;

	// HiddenItem
	protected HiddenItem idTipoDocItem;	
	protected HiddenItem flgDiSistemaItem;
	protected HiddenItem flgValidoItem;
	protected HiddenItem flgAnnItem;
	protected HiddenItem flgIgnoreWarningItem;	
	protected HiddenItem descrizioneTipoDocItem;
	

	// SelectItem
	
	// TextItem
	protected TextItem codTipoDocItem;
	protected TextItem nomeTipoDocItem;
	

	
	protected boolean editing;
	
	public TipiDocDetail(String nomeEntita) {		
		
		super(nomeEntita);
		
		_instance = this;
		
		// campi hidden
		idTipoDocItem 		 = new HiddenItem("idTipoDoc");				
		flgDiSistemaItem 	 = new HiddenItem("flgDiSistema");			
		flgValidoItem 		 = new HiddenItem("flgValido");		
		flgAnnItem 			 = new HiddenItem("flgAnn");
		flgIgnoreWarningItem = new HiddenItem("flgIgnoreWarning"); flgIgnoreWarningItem.setDefaultValue(0);

		descrizioneTipoDocItem 			 = new HiddenItem("descrizioneTipoDoc");
		
		// **************************************************************
		// * TIPI DOC FORM
		// **************************************************************
		tipiDocForm = new DynamicForm();
		tipiDocForm.setValuesManager(vm);
		tipiDocForm.setWidth("100%");  
		tipiDocForm.setHeight("5");  
		tipiDocForm.setPadding(5);
		tipiDocForm.setNumCols(2);
		tipiDocForm.setWrapItemTitles(false);
		tipiDocForm.setColWidths(160, "*");	
		
		// codice 
		codTipoDocItem = new TextItem("codTipoDoc", I18NUtil.getMessages().tipi_doc_detail_codTipoDocItem_title());
		codTipoDocItem.setWidth(100);
		codTipoDocItem.setStartRow(true);		
		codTipoDocItem.setAttribute("obbligatorio", true);		
		codTipoDocItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return true;
			}
		}));
		
		// nome
		nomeTipoDocItem = new TextItem("nomeTipoDoc", I18NUtil.getMessages().tipi_doc_detail_nomeTipoDocItem_title());
		nomeTipoDocItem.setWidth(300);
		nomeTipoDocItem.setStartRow(true);		
		nomeTipoDocItem.setAttribute("obbligatorio", true);		
		nomeTipoDocItem.setStartRow(true);	
		nomeTipoDocItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return true;
			}
		}));
		
		
		
		tipiDocForm.setItems(   codTipoDocItem,
								nomeTipoDocItem,
								
								flgDiSistemaItem,			
								flgValidoItem,
								flgAnnItem,
								flgIgnoreWarningItem,							
								idTipoDocItem,
								descrizioneTipoDocItem
		);
		
		tipiDocSection = new DetailSection(I18NUtil.getMessages().tipi_doc_detail_tipiDocSection_title(), true, true, false, tipiDocForm);

		
	
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
				
		lVLayout.addMember(tipiDocSection);	
			
		addMember(lVLayout);
		addMember(lVLayoutSpacer);		
	}
	
	@Override
	public void editNewRecord() {
		
		super.editNewRecord();
	}
	
	@Override
	public void editRecord(Record record) {
		

		super.editRecord(record);	
		
		markForRedraw();
	}
	
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
	}	

	public boolean isPersonaFisica() {
		
		return false;
	}
	
	public boolean isPersonaFisica(Record record) {
		return false;
	}
}
