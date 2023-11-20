/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.Portlet;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.State;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Menu dei preferiti
 * @author michele
 *
 */
public class OpenedPortletMenu extends HLayout {

	public OpenedPortletMenu() {
		setAlign(Alignment.LEFT);		
	}	
	
	public void add(Portlet portlet){	
		boolean trovato = false;
		for(Canvas member : getMembers()) {
			if(member instanceof OpenedPortletMenuButton) {
				if(((OpenedPortletMenuButton) member).getNomeEntita().equals(portlet.getNomeEntita())) {
					trovato = true;
				} 
			}			
		}	
		if(!trovato) {
			OpenedPortletMenuButton button = new OpenedPortletMenuButton(portlet.getNomeEntita(), portlet.getTitle(), portlet.getIcon());
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					OpenedPortletMenuButton button = (OpenedPortletMenuButton) event.getSource();				
					Layout.selectPortlet(button.getNomeEntita());				
				}							
			});			
			addMember(button);
		}		
		redrawOpenedPortletMenuButtons();		
		Layout.selectPortlet(portlet.getNomeEntita());
	}
	
	public void redrawOpenedPortletMenuButtons() {
		try {
			if(getMembers().length * OpenedPortletMenuButton._WIDTH > Page.getWidth()-125) {
				for(Canvas member : getMembers()) {
					member.setWidth((Page.getWidth()-125)/getMembers().length);		
				}	
			} else {
				for(Canvas member : getMembers()) {
					member.setWidth(OpenedPortletMenuButton._WIDTH);		
				}
			}
		} catch(Exception e) {}
	}
	
	public void remove(Portlet portlet){
		OpenedPortletMenuButton buttonToRemove = null;
		for(Canvas member : getMembers()) {
			if(member instanceof OpenedPortletMenuButton) {
				if(((OpenedPortletMenuButton) member).getNomeEntita().equals(portlet.getNomeEntita())) {
					buttonToRemove = (OpenedPortletMenuButton) member;
					break;
				} 
			}			
		}	
		if(buttonToRemove != null) {
			removeMember(buttonToRemove);
			buttonToRemove.destroy();
		}
		List<Portlet> otherPortlets = Layout.getOtherOpenedPortlet(portlet.getNomeEntita());
		if(otherPortlets != null && otherPortlets.size() > 0) {			
			for(int i = (otherPortlets.size() - 1); i >= 0; i--) {
				if(!otherPortlets.get(i).isMinimizedPortlet()) {
					Layout.selectPortlet(otherPortlets.get(i).getNomeEntita());
					break;
				}
			}
		}
	}
	
	public void changeTitle(Portlet portlet){
		for(Canvas member : getMembers()) {
			if(member instanceof OpenedPortletMenuButton) {
				if(((OpenedPortletMenuButton) member).getNomeEntita().equals(portlet.getNomeEntita())) {					
					member.setPrompt(portlet.getTitle());
					String title = portlet.getTitle();					
					if (title.length()>20){
						title = title.substring(0,20) + "...";
					}
					member.setTitle(title);	
				}
			}			
		}		
	}
	
	public void select(String nomeEntita){
		for(Canvas member : getMembers()) {
			if(member instanceof OpenedPortletMenuButton) {
				if(((OpenedPortletMenuButton) member).getNomeEntita().equals(nomeEntita)) {					
					((OpenedPortletMenuButton) member).setSelected(true);
				} else {
					((OpenedPortletMenuButton) member).setSelected(false);
				}
			}			
		}		
	}
	
}