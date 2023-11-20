/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

import it.eng.utility.Styles;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class TextAreaItem extends com.smartgwt.client.widgets.form.fields.TextAreaItem {
	
	public TextAreaItem() {
		this.setHeight(100);
		this.setWidth(250);
		buildViewer(this);
	}
	
	public TextAreaItem(String name) {
		this();
	    setName(name);
    }

    public TextAreaItem(String name, String title) {
    	this();
        setName(name);
		setTitle(title);
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
    	super.setCanEdit(canEdit);
    	setTextBoxStyle(canEdit ? it.eng.utility.Styles.textAreaItem : it.eng.utility.Styles.textAreaItemReadonly);    	 	
    	if (UserInterfaceFactory.isAttivaAccessibilita()){
 	//    	setCanFocus(canEdit ? true : false); 			
    		setCanFocus(true);
 		} else {
 			setCanFocus(canEdit ? true : false);
 		}  	
    }
    
    protected void buildViewer(final TextAreaItem item) {
    	if(showViewer()) {
    		FormItemIcon viewerIcon = new FormItemIcon();
    		viewerIcon.setHeight(16);
    		viewerIcon.setWidth(16);
//    		viewerIcon.setInline(true);       
    		viewerIcon.setNeverDisable(true);
    		viewerIcon.setSrc("buttons/view.png");
    		viewerIcon.setPrompt("Visualizza contenuti");
    		viewerIcon.setCursor(Cursor.POINTER);
    		viewerIcon.setAttribute("cellStyle", Styles.formCellClickable);
    		viewerIcon.addFormItemClickHandler(new FormItemClickHandler() {

    			@Override
    			public void onFormItemClick(FormItemIconClickEvent event) {
    				manageOnViewerClick(item);    				
    			}
    		});
    			
    		List<FormItemIcon> icons = new ArrayList<FormItemIcon>();
    		icons.add(viewerIcon);
    		item.setIcons(icons.toArray(new FormItemIcon[icons.size()]));
    		item.setIconVAlign(VerticalAlignment.CENTER);
    	}
    }
    
    public void manageOnViewerClick(final TextAreaItem item) {
    	ViewerTextAreaValueWindow lViewerTextAreaValueWindow = new ViewerTextAreaValueWindow(item);
    	lViewerTextAreaValueWindow.show();
    }
    
    public boolean showViewer() {
    	return true;
    }
    
    @Override
	public void setWidth(int width) {
    	if(showViewer()) {
    		super.setWidth(width + 19);
    	} else {
    		super.setWidth(width);
    	}
	}
	
}
