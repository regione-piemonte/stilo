/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class TagCommentiAttiCompletiCanvas extends ReplicableCanvas {
	
	protected ReplicableCanvasForm tagForm;
	protected SelectItem itemLavTag;
	
	public TagCommentiAttiCompletiCanvas(TagCommentiAttiCompletiItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {

		preparaItemTagForm();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {

		return new ReplicableCanvasForm[] {tagForm};
	}
	
	private void preparaItemTagForm() {
		
		tagForm = new ReplicableCanvasForm();
		tagForm.setWrapItemTitles(false);
		tagForm.setNumCols(10);
		tagForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboTagAttoCompletoDataSource", "key", FieldType.TEXT);
		itemLavTag = new SelectItem("itemLavTag", "Tag");
		itemLavTag.setMultiple(true);
		itemLavTag.setOptionDataSource(mezziTrasmissioneDS);  
		itemLavTag.setAutoFetchData(true);
		itemLavTag.setDisplayField("value");
		itemLavTag.setValueField("key");			
		itemLavTag.setWrapTitle(false);
		itemLavTag.setStartRow(true);
		itemLavTag.setAllowEmptyValue(true);
		itemLavTag.setClearable(false);
		itemLavTag.setRedrawOnChange(true);
		itemLavTag.setWidth(500);   		
        itemLavTag.setColSpan(1);

		tagForm.setFields(itemLavTag);
		tagForm.setNumCols(10);
		tagForm.setColWidths("1","1","1","1","1","1","1","1","*","*");
		
		addChild(tagForm);
	}
	
}