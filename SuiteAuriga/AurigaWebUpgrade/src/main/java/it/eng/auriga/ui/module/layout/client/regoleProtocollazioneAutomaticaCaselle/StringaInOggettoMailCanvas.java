/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public class StringaInOggettoMailCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	private ExtendedTextItem parolaInOggettoMailItem;
	
	public StringaInOggettoMailCanvas(StringaInOggettoMailItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		parolaInOggettoMailItem = new ExtendedTextItem("parolaInOggettoMail", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_parolaInOggettoMailItem_title());
		parolaInOggettoMailItem.setWidth(400);
		parolaInOggettoMailItem.setColSpan(1);
//		parolaInOggettoMailItem.setPrompt(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_parolaInOggettoMailItem_prompt());
		
		mDynamicForm.setFields(
				parolaInOggettoMailItem
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
