/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class StaticTextAreaItem extends com.smartgwt.client.widgets.form.fields.StaticTextItem {

	public StaticTextAreaItem() {
		setHeight(100);
		setWidth(250);
		setCanFocus(false);
		setTabIndex(-1);
		setType("textArea");
	}
	
	public StaticTextAreaItem(String name) {
		this();
	    setName(name);        
    }

    public StaticTextAreaItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
    }
	
}
