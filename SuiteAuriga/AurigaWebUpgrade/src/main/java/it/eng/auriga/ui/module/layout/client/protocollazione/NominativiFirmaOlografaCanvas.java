/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class NominativiFirmaOlografaCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	private TextItem cognomeItem;
	private TextItem nomeItem;

	public NominativiFirmaOlografaCanvas(NominativiFirmaOlografaItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setValidateOnChange(false);

		cognomeItem = new TextItem("cognome", "Cognome");
		cognomeItem.setEndRow(false);
		cognomeItem.setWidth(250);
		cognomeItem.setRequired(true);
		
		nomeItem = new TextItem("nome", "Nome");
		nomeItem.setEndRow(false);
		nomeItem.setWidth(250);
		nomeItem.setRequired(true);
		
		mDynamicForm.setFields(cognomeItem, nomeItem);

		mDynamicForm.setNumCols(19);
		mDynamicForm.setColWidths("50", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		addChild(mDynamicForm);

	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}
