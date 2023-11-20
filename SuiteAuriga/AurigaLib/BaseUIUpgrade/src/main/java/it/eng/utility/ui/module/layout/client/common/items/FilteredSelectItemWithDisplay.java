/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.Styles;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;

public class FilteredSelectItemWithDisplay extends FilteredSelectItem {
	
	public FilteredSelectItemWithDisplay(SelectGWTRestDataSource datasource) {
		super();
	    setOptionDataSource(datasource);
	    setDisplayField("displayValue");
//	    setTextBoxStyle(Styles.hideItemScrollBar);
	}

	public FilteredSelectItemWithDisplay(String name, SelectGWTRestDataSource datasource) {
		super();
	    setName(name);  
	    setOptionDataSource(datasource);
	    setDisplayField("displayValue");
//	    setTextBoxStyle(Styles.hideItemScrollBar);
    }

    public FilteredSelectItemWithDisplay(String name, String title, SelectGWTRestDataSource datasource) {
    	super();
        setName(name);
		setTitle(title);
		setOptionDataSource(datasource);
		setDisplayField("displayValue");
//		setTextBoxStyle(Styles.hideItemScrollBar);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly + " " + it.eng.utility.Styles.hideItemScrollBar);  
    	if (UserInterfaceFactory.isAttivaAccessibilita()){
 	//    	setCanFocus(canEdit ? true : false); 			
    		setCanFocus(true);
 		} else {
 			setCanFocus(canEdit ? true : false);
 		}  
    }
}
