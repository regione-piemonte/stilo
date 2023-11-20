/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class OpzioniListaSceltaAttributoCustomCanvas extends ReplicableCanvas {
	
	private TextItem opzioniItem;

	private ReplicableCanvasForm mDynamicForm;

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		opzioniItem = new TextItem("opzione");
		opzioniItem.setShowTitle(false); 
		opzioniItem.setWidth(300);
		opzioniItem.setAlign(Alignment.CENTER);
		
		mDynamicForm.setFields(opzioniItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

}
