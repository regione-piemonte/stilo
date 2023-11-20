/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author dbe4235
 *
 */

public class EsibentePraticaPregressaCanvas extends ReplicableCanvas {
	
	private TextItem esibenteItem;

	private ReplicableCanvasForm mDynamicForm;

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		esibenteItem = new TextItem("value");
		esibenteItem.setShowTitle(false); 
		esibenteItem.setWidth(630);
		esibenteItem.setAlign(Alignment.CENTER);
		esibenteItem.setRequired(true);
		
		mDynamicForm.setFields(esibenteItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

}
