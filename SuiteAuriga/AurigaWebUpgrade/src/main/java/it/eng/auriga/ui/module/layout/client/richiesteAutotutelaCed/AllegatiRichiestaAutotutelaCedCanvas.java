/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AllegatiRichiestaAutotutelaCedCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	private TextItem stringaAllegatoItem;
	private TextItem tipologiaAllegatoItem;

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setNumCols(9);
		mDynamicForm.setColWidths(80, 1, 1, 1, 1, 1, 1, "*", "*");

		stringaAllegatoItem = new TextItem("stringaAllegato");
		stringaAllegatoItem.setShowTitle(false);
		stringaAllegatoItem.setWidth(250);

		tipologiaAllegatoItem = new TextItem("tipologiaAllegato", "Tipo");
		tipologiaAllegatoItem.setWidth(250);

		mDynamicForm.setFields(stringaAllegatoItem, tipologiaAllegatoItem);

		addChild(mDynamicForm);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}
