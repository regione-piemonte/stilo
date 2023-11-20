/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class TextItem extends com.smartgwt.client.widgets.form.fields.TextItem {
	
	public TextItem(JavaScriptObject jsObj){
        super(jsObj);
    }

	public TextItem() {
//		this.setHeight(20);
		this.setWidth(250);
	}
	
	public TextItem(String name) {
		this();
	    setName(name);
    }

    public TextItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);  
    	if (UserInterfaceFactory.isAttivaAccessibilita()){
 	//    	setCanFocus(canEdit ? true : false); 			
    		setCanFocus(true);
 		} else {
 			setCanFocus(canEdit ? true : false);
 		}  
    }
	
}
