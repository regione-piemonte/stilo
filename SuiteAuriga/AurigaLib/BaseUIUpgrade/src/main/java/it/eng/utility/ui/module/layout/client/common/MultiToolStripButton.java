/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public abstract class MultiToolStripButton extends ToolStripButton{

	private final CustomLayout layout;
	
	public boolean toShowTitle;
	
	public MultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle) {
		this(pIcon, pLayout, pTitle, false);
	}
	
	public MultiToolStripButton(String pIcon, CustomLayout pLayout, String pTitle, boolean pToShowTitle) {
		this.layout = pLayout;
		setIcon(pIcon); 
		setIconSize(16); 
		toShowTitle = pToShowTitle;
		setTitle(pTitle);		
		addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {  
				if (getList().getSelectedRecords().length==0){
					MessageBean bean = new MessageBean(I18NUtil.getMessages().noSelectedRecords_message(), "", MessageType.ERROR);				
					Layout.addMessage(bean);
				} else {
					doSomething();
				}
			}   
		}); 
	}
	
	@Override
	public void setTitle(String title) {
		
		if(toShowTitle) {
			super.setTitle(title);
		} else {
			setPrompt(title);
		}
	}
	
	@Override
	public String getTitle() {
		
		if(toShowTitle) {
			return super.getTitle();
		} else {
			return getPrompt();
		}
	}

	public abstract void doSomething();
	
	public boolean toShow() {
		return true;
	}

	public CustomLayout getLayout() {
		return layout;
	}
	
	public ListGrid getList() {
		if(layout != null) {
			return layout.getList();
		} 
		return null;
	}
}
