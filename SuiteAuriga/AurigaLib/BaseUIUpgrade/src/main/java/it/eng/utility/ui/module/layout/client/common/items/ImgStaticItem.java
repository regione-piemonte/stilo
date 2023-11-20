/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class ImgStaticItem extends com.smartgwt.client.widgets.form.fields.StaticTextItem {

		private FormItemIcon icons;
		
		public ImgStaticItem(String name, String icon, String prompt) {
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
	    	icons.setCursor(Cursor.ARROW);
	    	setIcons(icons);
	    	setPrompt(prompt);
	    	setCellStyle(it.eng.utility.Styles.formCell);
	    	setShowTitle(false);    	
	    	setRedrawOnChange(true);
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
	    
	    public void setIcon(String icon) {
	    	icons.setSrc(icon);     	
	    }
	    
	    @Override
	    public void setPrompt(final String prompt) {
	    	icons.setPrompt(prompt);     	
	    }

	}