/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;

public class InformazioniWindow extends ModalWindow{
	
	private InformazioniWindow instance;
	
	public InformazioniWindow(String nomeEntita) {
		super(nomeEntita, true, true);
	
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(600);		
		setHeight(300);
		setKeepInParentRect(true);
		setTitle("Informazioni");
		setShowModalMask(true);
		setShowCloseButton(true);
		
		setShowMaximizeButton(true);
		setShowMinimizeButton(false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		Canvas canvas = new Canvas();
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.setContents(AurigaLayout.getParametroDB("PROCUCT_INFO"));
		
		setBody(canvas);
		
		setShowTitle(true);
		setHeaderIcon("about.png");
	
	}

}
