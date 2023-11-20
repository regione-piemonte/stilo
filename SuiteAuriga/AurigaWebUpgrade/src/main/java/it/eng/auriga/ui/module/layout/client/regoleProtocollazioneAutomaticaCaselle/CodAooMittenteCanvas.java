/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public class CodAooMittenteCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	private ExtendedTextItem codAooMittenteItem;
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		codAooMittenteItem = new ExtendedTextItem("codAooMittente", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_codAooMittenteCanvas_title());
		codAooMittenteItem.setWidth(200);
		codAooMittenteItem.setColSpan(1);
		
		mDynamicForm.setFields(
				codAooMittenteItem
				);
		
		mDynamicForm.setNumCols(1);
		mDynamicForm.setColWidths("*");

		addChild(mDynamicForm);
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}
	
}
