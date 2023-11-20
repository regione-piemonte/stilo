/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class StaticTextItem extends com.smartgwt.client.widgets.form.fields.StaticTextItem {

	public StaticTextItem() {
//		setHeight(20);
		setWidth(250);
		setCanFocus(false);
		setTabIndex(-1);
		setType("text");
	}	
	
	public StaticTextItem(String name) {
		this();
	    setName(name);        
    }

    public StaticTextItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(it.eng.utility.Styles.staticTextItem);
//    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);    	 			
    }
	
}
