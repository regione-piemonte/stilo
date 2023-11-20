/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.TimeZoneInfo;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
//import com.google.gwt.i18n.client.DateTimeFormat;
//import com.google.gwt.i18n.client.TimeZone;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.DateUtil;
//import com.smartgwt.client.widgets.form.DynamicForm;
//import com.smartgwt.client.widgets.form.FormItemInputTransformer;
//import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class DateTimeItem extends com.smartgwt.client.widgets.form.fields.DateTimeItem{
	public DateTimeItem() {
//		this.setHeight(20);
		this.setWidth(140);
		this.setAttribute("allowRelativeDates", false);
		this.setUseTextField(true);
		this.setEnforceDate(true);
		this.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		this.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATETIME);	
		this.setDefaultChooserDate(new Date (getValueNow()));
		setDefaultDataRange();
//		this.setInputTransformer(new FormItemInputTransformer() {
//		
//			@Override
//			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
//				if(value != null) { 
//					DateTimeFormat dtFormet    = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm"); 
//		            String today  = dtFormet.format(new Date());
//		            String date = dtFormet.format((Date)value);
//		            if(date.split(" ")[0].equals(today.split(" ")[0])) {
//			            if(oldValue != null) { // Changing date.	 
//			            	if(date.split(" ")[1].equals("00:00")) { 
//			            		 return date.split(" ")[0] + " " + today.split(" ")[1];
//			                } 
//			            } 
//			            return date.split(" ")[0] + " " + today.split(" ")[1];
//		            }
//		            return date.split(" ")[0] + " 00:00";
//				}
//				return "";
//			}
//        });

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
		textFieldProperties.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		setTextFieldProperties(textFieldProperties);	
	}
	
	
	
	public DateTimeItem(String name) {
		this();
	    setName(name);        
    }

    public DateTimeItem(String name, String title) {
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
    
    private Long getValueNow () {
    	TimeZoneConstants timeZoneConstants = GWT.create(TimeZoneConstants.class);
		TimeZone createTimeZone = TimeZone.createTimeZone( TimeZoneInfo.buildTimeZoneData( timeZoneConstants.europeRome() ) );
        String timezoneGmt = createTimeZone.getGMTString( new Date() ).replace( "GMT", "" );
//        this.setValue(date.split(" ")[0] + " " + displayHours + ":" + date.split(" ")[1].split(":")[1]);
        timezoneGmt = timezoneGmt.substring(timezoneGmt.indexOf("+"), timezoneGmt.indexOf(":"));
        Long milliseconds = new Date().getTime() + Long.parseLong(timezoneGmt) *60 *60 *1000;
    	return milliseconds;
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
