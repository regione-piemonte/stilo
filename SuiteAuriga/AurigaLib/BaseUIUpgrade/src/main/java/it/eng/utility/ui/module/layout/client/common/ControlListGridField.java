/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ControlListGridField extends ListGridField {

    public ControlListGridField(String name) {
    	super(name);
    	init();
    }
   
    public ControlListGridField(String name, String title) {
    	super(name, title);
    	init();
    }

    private void init() {
    	setAlign(Alignment.CENTER);
		setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		if(getTitle() != null && !"".equals(getTitle())) {
			setCanHide(true);
		} else {
			setCanHide(false);
		}
		setCanGroupBy(false);
//		setCanReorder(false);
		setCanSort(false);
		setCanFreeze(false);		
		setCanExport(false);
		setCanDragResize(false);		
		setCanEdit(false);
		setShowHover(false);	
		setWidth(25);		
		setShowDefaultContextMenu(false);
    }
	
}
