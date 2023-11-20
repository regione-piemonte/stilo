/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class FiltraDatiContenutiTabellaCanvas extends ReplicableCanvas{
	
	private ReplicableCanvasForm mDynamicForm;
	private HiddenItem nomeItem;
	private StaticTextItem inColonnaItem;
	private TextItem cercaItem;
	
	@Override
	public void disegna() {
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		// nascosti
		nomeItem = new HiddenItem("nome");
				
		inColonnaItem = new StaticTextItem("inColonna") {
			@Override
			public void setCanEdit(Boolean canEdit) {
				setTextBoxStyle(it.eng.utility.Styles.formTitle);
				super.setCanEdit(false);
			}
		};	
		inColonnaItem.setWidth(200);
		inColonnaItem.setType("text");				
		inColonnaItem.setShowTitle(false);
		inColonnaItem.setTextAlign(Alignment.RIGHT);		
		inColonnaItem.setCanFocus(false);
		inColonnaItem.setTabIndex(-1);
				
		cercaItem = new TextItem("cerca");
		cercaItem.setShowTitle(false);
		cercaItem.setWidth(650);
		
		mDynamicForm.setFields(nomeItem, inColonnaItem, cercaItem);	
							   
		mDynamicForm.setNumCols(4);		
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