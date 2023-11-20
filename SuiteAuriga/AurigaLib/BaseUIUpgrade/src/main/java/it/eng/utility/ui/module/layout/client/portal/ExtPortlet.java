/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class ExtPortlet extends Portlet {

	public ExtPortlet(String nomeEntita, HashMap<String, String> params) {
		super(nomeEntita, true);		
		
		settingsMenu.removeItem(setHomepageMenuItem);
		settingsMenu.removeItem(saveDimPosMenuItem);
		settingsMenu.removeItem(separatorMenuItem);	
		
		Canvas portletLayout = UserInterfaceFactory.getPortletLayout(nomeEntita, params);
		if (portletLayout != null){
			portletLayout.setHeight100();
			portletLayout.setWidth100();
			addItem(portletLayout);
		}
        
        setVisible(false);
        setCanDragReposition(false);  
        setCanDragResize(false);   
        setKeepInParentRect(true);
        setAutoDraw(true);
        
        MenuBean bean = Layout.getMenu(nomeEntita);
        if (bean != null){
        	setTitle(bean.getTitle());  	  
        	setIcon(bean.getIcon());
        }
        
        setIsModal(true);
        setShowModalMask(true);
        setModalMaskOpacity(50); 
        
		setShowMinimizeButton(false);
		setShowMaximizeButton(false);
		setShowCloseButton(false);		
		 
        setHeaderControls(HeaderControls.HEADER_ICON, 
        				  HeaderControls.HEADER_LABEL, 
        				  HeaderControls.MAXIMIZE_BUTTON, 
        				  settingsHeaderControl, 
//        				  helpHeaderControl, 
        				  HeaderControls.CLOSE_BUTTON);   
	}

}
