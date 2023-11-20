/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class RichiestaAccessoAttiWindow extends ModalWindow {

	private CustomLayout _layoutScrivaniaLayout;
	private RichiestaAccessoAttiWindow _window;
	private RichiestaAccessoAttiDetail portletLayout;
	
	public RichiestaAccessoAttiWindow(Map<String, Object> initialValues,CustomLayout _layoutScrivaniaLayout) {
	
		super("richiesta_accesso_atti", true);
		
		setTitle("Dettaglio richiesta accesso atto");  	
		
		_window = this;
		this._layoutScrivaniaLayout = _layoutScrivaniaLayout;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		   
		portletLayout = new RichiestaAccessoAttiDetail("richiesta_accesso_atti");
		portletLayout.editNewRecord(initialValues);
		portletLayout.newMode();
			
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
    
       	setIcon("blank.png");          
	}
	
	@Override
	public void manageOnCloseClick() {
		
		super.manageOnCloseClick();
		if(_layoutScrivaniaLayout != null){
			_layoutScrivaniaLayout.doSearch();
		}
	}
}