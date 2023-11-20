/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;

public class TimeItem extends com.smartgwt.client.widgets.form.fields.TimeItem {

	public TimeItem(JavaScriptObject jsObj){
        super(jsObj);
    }

	public TimeItem() {
		this.setWidth(250);
	}
	
	public TimeItem(String name) {
		this();
	    setName(name);
    }

    public TimeItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);  
    	setCanFocus(canEdit ? true : false);
    }
	
}