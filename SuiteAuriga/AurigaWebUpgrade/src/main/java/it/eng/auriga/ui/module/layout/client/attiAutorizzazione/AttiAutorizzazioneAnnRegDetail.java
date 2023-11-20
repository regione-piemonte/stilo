/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AttiAutorizzazioneAnnRegDetail extends CustomDetail {

	protected DynamicForm attoForm;
	private DynamicForm regDaAnnullareForm;

	private HiddenItem idAttoHiddenItem;
	private NumericItem nroBozzaItem;
	private DateItem tsRegBozzaItem;
	private TextItem desUteBozzaItem;
	private NumericItem nroAttoItem;
	private DateItem tsRegAttoItem;
	private TextItem desUteAttoItem;
	private TextAreaItem oggettoItem;
	private RegDaAnnullareItem regDaAnnullareItem;
	
	private DetailSection regDaAnnullareSection;

	public AttiAutorizzazioneAnnRegDetail(String nomeEntita) {

		super(nomeEntita);

		attoForm = new DynamicForm();
		attoForm.setValuesManager(vm);  			
		attoForm.setWidth("100%"); 
		attoForm.setPadding(5);
		attoForm.setWrapItemTitles(false);
		
		attoForm.setNumCols(10);
		attoForm.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idAttoHiddenItem = new HiddenItem("idAtto");

		nroBozzaItem = new NumericItem("nroBozza", I18NUtil.getMessages().atti_autorizzazione_annreg_nroBozza());
		nroBozzaItem.setStartRow(true);
				
		tsRegBozzaItem = new DateItem("tsRegBozza", I18NUtil.getMessages().atti_autorizzazione_annreg_tsRegBozza());
		
		desUteBozzaItem = new TextItem("desUteBozza", I18NUtil.getMessages().atti_autorizzazione_annreg_desUteBozza());
		
		nroAttoItem = new NumericItem("", I18NUtil.getMessages().atti_autorizzazione_annreg_nroAtto());
		nroAttoItem.setStartRow(true);
		
		tsRegAttoItem = new DateItem("tsRegAtto", I18NUtil.getMessages().atti_autorizzazione_annreg_tsRegAtto());		
		
		desUteAttoItem = new TextItem("desUteAtto", I18NUtil.getMessages().atti_autorizzazione_annreg_desUteAtto());
		
		oggettoItem = new TextAreaItem("oggetto", I18NUtil.getMessages().atti_autorizzazione_annreg_oggetto());
		oggettoItem.setStartRow(true);
		oggettoItem.setLength(4000);
		oggettoItem.setHeight(40);
		oggettoItem.setWidth(650);
		oggettoItem.setColSpan(8);
		oggettoItem.setRequired(true);
		
		attoForm.setItems(
			idAttoHiddenItem, 
			nroBozzaItem, 
			tsRegBozzaItem,
			desUteBozzaItem,
			nroAttoItem, 
			tsRegAttoItem,
			desUteAttoItem,
			oggettoItem
		);
		
		regDaAnnullareForm = new DynamicForm();  
		regDaAnnullareForm.setValuesManager(vm);  			
		regDaAnnullareForm.setWidth("100%");  
		regDaAnnullareForm.setPadding(5);  		
		
		regDaAnnullareItem = new RegDaAnnullareItem();		
		regDaAnnullareItem.setName("listaRegDaAnnullare");
		regDaAnnullareItem.setShowTitle(false);
		regDaAnnullareItem.setShowNewButton(false);
		
		regDaAnnullareForm.setItems(regDaAnnullareItem);

		regDaAnnullareSection = new DetailSection(I18NUtil.getMessages().atti_autorizzazione_annreg_regDaAnnullareSection(), true, true, true, regDaAnnullareForm);
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
				
		lVLayout.addMember(attoForm);	
		lVLayout.addMember(regDaAnnullareSection);			
			
		addMember(lVLayout);
		addMember(lVLayoutSpacer);		
	}

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
		nroBozzaItem.setCanEdit(false);
		tsRegBozzaItem.setCanEdit(false);
		desUteBozzaItem.setCanEdit(false);
		nroAttoItem.setCanEdit(false); 
		tsRegAttoItem.setCanEdit(false);
		desUteAttoItem.setCanEdit(false);
		if(this.mode != null && "new".equals(this.mode)) {
			regDaAnnullareSection.hide();
		} else {
			regDaAnnullareSection.show();
		}
	}	

}