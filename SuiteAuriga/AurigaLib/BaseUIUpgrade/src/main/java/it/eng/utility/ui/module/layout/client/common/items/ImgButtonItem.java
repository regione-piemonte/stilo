/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class ImgButtonItem extends com.smartgwt.client.widgets.form.fields.StaticTextItem {

	private boolean alwaysEnabled = false;  
	private FormItemIcon icons;
	private boolean showIfSettato = false;
	private boolean settedCanEdit = true;

    public ImgButtonItem(String name, String icon, String prompt) {
    	super();
    	setName(name);     	
    	setColSpan(1);
		setWidth(20);
    	setIconWidth(16);
		setIconHeight(16);	
		setIconVAlign(VerticalAlignment.BOTTOM);	
		setShowValueIconOnly(true);
    	icons = new FormItemIcon();
    	icons.setSrc(icon);	    
    	setIcons(icons);
    	setPrompt(prompt);
    	setCellStyle(it.eng.utility.Styles.formCellClickable);
    	setShowTitle(false);
    	if (UserInterfaceFactory.isAttivaAccessibilita()){
    		setTitle(prompt);
 			super.setPrompt(prompt);   
	//    	setTabIndex(-1);
	    	setCanFocus(true);
 		} else {
 			setCanFocus(false);
    		setTabIndex(-1);	
 		}
    }
    
	@Override
	public void setShowIfCondition(final FormItemIfFunction showIf) {
		setRedrawOnChange(true);
		showIfSettato = true;
		super.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!alwaysEnabled && !settedCanEdit) return false;
				else return showIf.execute(item, value, form);
			}
		});
	}

    @Override
    public void setCanEdit(Boolean canEdit) {
    	this.settedCanEdit = canEdit;
    	if (showIfSettato) { 
    		try{
    			redraw();
    		}catch(Exception e){
    		}
		} else {
			if(!alwaysEnabled && !canEdit) {
				try{
					hide(); 
				}catch(Exception e){
					setVisible(false);
				}
			} else if(getAttributeAsBoolean("nascosto") != null && getAttributeAsBoolean("nascosto")) {
				try{
					hide(); 
				}catch(Exception e){
					setVisible(false);
				}
			} else {
				try{
					show(); 
				}catch(Exception e){
					setVisible(true);
				}			
			}
		}
    }
    
    public void setIcon(String icon) {
    	icons.setSrc(icon);     	
    }
    
    @Override
    public void setPrompt(final String prompt) {
    	icons.setPrompt(prompt);     	
    }

	public boolean isAlwaysEnabled() {
		return alwaysEnabled;
	}

	public void setAlwaysEnabled(boolean alwaysEnabled) {
		this.alwaysEnabled = alwaysEnabled;
	}
    
}
