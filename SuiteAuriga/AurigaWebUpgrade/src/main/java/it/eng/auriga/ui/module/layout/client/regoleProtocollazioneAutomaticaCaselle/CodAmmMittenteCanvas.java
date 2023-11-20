/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public class CodAmmMittenteCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	private ExtendedTextItem codAmmMittenteItem;
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		codAmmMittenteItem = new ExtendedTextItem("codAmmMittente", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_codAmmMittenteCanvas_title());
		codAmmMittenteItem.setWidth(200);
		codAmmMittenteItem.setColSpan(1);
		
		mDynamicForm.setFields(
				codAmmMittenteItem
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
