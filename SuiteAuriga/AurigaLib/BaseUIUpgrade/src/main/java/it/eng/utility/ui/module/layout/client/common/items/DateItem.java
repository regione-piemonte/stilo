/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class DateItem extends com.smartgwt.client.widgets.form.fields.DateItem{
	
	public DateItem() {
//		this.setHeight(20);
		this.setWidth(110);
		this.setAttribute("allowRelativeDates", false);
		this.setUseTextField(true);		
		this.setEnforceDate(true);
		this.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		this.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);  	
		setDefaultDataRange();
		TextItem textFieldProperties = new TextItem();	
		textFieldProperties.setChangeOnKeypress(false);			
		textFieldProperties.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {	
				String value = event.getValue() != null ? (String) event.getValue() : null;
				//String oldValue = event.getOldValue() != null ? (String) event.getOldValue() : null;	
				if(value != null && !"".equals(value)) {
					DateUtil.setDateInputFormat("DMY");
					Date date = DateUtil.parseInput(value);
					if(date != null) {
						event.getForm().setValue(getName(),(String)value);
					} else {
						event.getForm().setValue(getName(),(String)null);
					}
				} else {
					event.getForm().setValue(getName(),(String) null);
				}		
				event.getForm().markForRedraw();
			}
		});	
		textFieldProperties.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);		
		setTextFieldProperties(textFieldProperties);					
	}	
	
	public DateItem(String name) {
		this();
	    setName(name);        
    }

    public DateItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
    	if(!canEdit) {
    		setShowPickerIcon(false);
    	} else {
    		setShowPickerIcon(true);
    	}
    	setCanFocus(canEdit ? true : false);    	  
    }
    
    protected void setDefaultDataRange() {
    	GenericConfigBean config = Layout.getGenericConfig();
    	if(config!=null) {
    		if(config.getMinAnno()!=null && !"".equals(config.getMinAnno())) {
    			int minAnno = new Integer(config.getMinAnno()) - 1900;
    			Date startDate = new Date(minAnno, 11, 31);
    			this.setStartDate(startDate);
    		}
    		if(config.getMaxAnno()!=null && !"".equals(config.getMaxAnno())) {
    			int maxAnno = new Integer(config.getMaxAnno()) - 1900;
    			Date endDate = new Date(maxAnno, 11, 31);
    			this.setEndDate(endDate);
    		}
    	}

    }
}
