/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;


public class CentriCostoOrganigrammaCanvas extends ReplicableCanvas{

	private ReplicableCanvasForm mDynamicForm;
	private TextItem denominazioneCdCCdR;

	public CentriCostoOrganigrammaCanvas(CentriCostoOrganigrammaItem item) {
		super(item);			
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);		
		mDynamicForm.setValidateOnChange(false);

		denominazioneCdCCdR = new TextItem("denominazioneCdCCdR",I18NUtil.getMessages().organigramma_uo_detail_denominazioneCdCCdR_title());
		denominazioneCdCCdR.setRequired(false);
//		denominazioneCdCCdR.setEndRow(false);
		denominazioneCdCCdR.setWidth(120);		
//		denominazioneCdCCdR.setTitleColSpan(1);

		mDynamicForm.setFields(denominazioneCdCCdR);	

		mDynamicForm.setNumCols(10);

		addChild(mDynamicForm);

	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		denominazioneCdCCdR.setCanEdit(canEdit);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
	}
	
	private String setTitleAligned(String title){
		return "<span style=\"width: 150px; display: inline-block;\">"+title+"</span>";
	}
}