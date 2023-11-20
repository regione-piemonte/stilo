/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.data.Record;

public class ValoriAmmessiCanvas extends ReplicableCanvas{
	private ReplicableCanvasForm mDynamicForm;
	private TextItem valoreAmmessoItem;
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		valoreAmmessoItem = new TextItem("valoreAmmesso","Valore");
		valoreAmmessoItem.setShowTitle(false);
		valoreAmmessoItem.setWidth(740);
		
		mDynamicForm.setFields(valoreAmmessoItem);	
							   
		mDynamicForm.setNumCols(2);		
		addChild(mDynamicForm);
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
}