/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class ExtendedDateItem extends com.smartgwt.client.widgets.form.fields.DateItem{
	
	private ExtendedDateItem instance;
	
	public ExtendedDateItem() {
		instance = this;
//		this.setHeight(20);
		this.setWidth(120);
		this.setAttribute("allowRelativeDates", false);
		this.setUseTextField(true);		
		this.setEnforceDate(true);
		this.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		this.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setDefaultDataRange();
		ExtendedTextItem textFieldProperties = new ExtendedTextItem();	
		textFieldProperties.setChangeOnKeypress(false);			
		textFieldProperties.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
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
				instance.fireEvent(new ChangedEvent(instance.getJsObj()));
			}
		});	
		textFieldProperties.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);		
		setTextFieldProperties(textFieldProperties);					
	}	
	
	public ExtendedDateItem(String name) {
		this();
	    setName(name);        
    }

    public ExtendedDateItem(String name, String title) {
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
