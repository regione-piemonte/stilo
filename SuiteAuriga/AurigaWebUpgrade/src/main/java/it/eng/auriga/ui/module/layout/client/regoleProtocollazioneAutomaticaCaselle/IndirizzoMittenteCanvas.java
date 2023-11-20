/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public class IndirizzoMittenteCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	private ExtendedTextItem indirizzoMittenteItem;
	
	public IndirizzoMittenteCanvas(IndirizzoMittenteItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		indirizzoMittenteItem = new ExtendedTextItem("indirizzoMittente", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_indirizzoMittenteItem_title());
		indirizzoMittenteItem.setWidth(200);
		indirizzoMittenteItem.setColSpan(1);
		
		mDynamicForm.setFields(
				indirizzoMittenteItem
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
