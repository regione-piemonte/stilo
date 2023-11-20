/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

/**
 * Menu dei preferiti
 * @author michele
 *
 */
public class PreferenceMenu extends HLayout {
	
	private boolean nascondiPreferitiOnToolBar = false;

	public PreferenceMenu() {
		setAlign(Alignment.LEFT);			
		setCanAcceptDrop(true);			
		setCanDropComponents(true);
		setDropTypes("PreferenceMenuButton");		
	}
	
	public boolean addToPreference(MenuBean menu,boolean nascondiPreferitiOnToolBar) {
		this.nascondiPreferitiOnToolBar = nascondiPreferitiOnToolBar;
		return addToPreference(menu);
	}
		
	public boolean addToPreference(MenuBean menu){		
		boolean trovato = isPreference(menu.getNomeEntita());
		if(!trovato) {
//			if(getMembers().length < Layout.getGenericConfig().getNumMaxShortCut()) {
				PreferenceMenuButton button = new PreferenceMenuButton(menu.getNomeEntita(), menu.getTitle(), menu.getIcon(), this);						
				button.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						
						PreferenceMenuButton button = (PreferenceMenuButton) event.getSource();
						Layout.addPortlet(button.getNomeEntita());	
					}								
				});		
				if (UserInterfaceFactory.isAttivaAccessibilita() && nascondiPreferitiOnToolBar) {					
					button.hide();
				}
				addMember(button);			
				return true;
//			} else {
//				Layout.addMessage(new MessageBean("Non sono consentiti più di " + Layout.getGenericConfig().getNumMaxShortCut() + " tasti di accesso rapido sulla barra strumenti", "", MessageType.ERROR));				
//			}
		}
		return false;
	}
	
	public void removeFromPreference(MenuBean menu){
		for(Canvas member : getMembers()) {
			if(member instanceof PreferenceMenuButton) {
				if(((PreferenceMenuButton) member).getNomeEntita().equals(menu.getNomeEntita())) {
					removeMember(member);	
					break;
				}
			}			
		}
	}
	
	public void removeAllFromPreference(){		
		for(int i = (getMembers().length - 1); i >= 0; i--) {
			if(getMember(i) instanceof PreferenceMenuButton) {
				removeMember(getMember(i));					
			}			
		}
	}
	
	public String getPreferencesAsString() {
		String res = "";
		for(Canvas member : getMembers()) {
			if(member instanceof PreferenceMenuButton) {
				String nomeEntita = ((PreferenceMenuButton) member).getNomeEntita();
				if(!"".equals(res)) {
					res += ",";
				} 
				res += nomeEntita;				
			}
		}
		return res;
	}
	
	public boolean isPreference(String key) {		
		for(Canvas member : getMembers()) {
			if(member instanceof PreferenceMenuButton) {
				if(((PreferenceMenuButton)member).getNomeEntita().equals(key)) {
					return true;					
				}
			}			
		}				
		return false;
	}

}