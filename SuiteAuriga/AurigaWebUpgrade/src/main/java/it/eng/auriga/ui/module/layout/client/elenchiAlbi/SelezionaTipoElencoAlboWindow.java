/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

public class SelezionaTipoElencoAlboWindow extends Window {

	private SelezionaTipoElencoAlboWindow instance;
	private SelezionaTipoElencoAlboForm form; 
	
	public SelezionaTipoElencoAlboWindow(){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona tipo elenco / albo");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new SelezionaTipoElencoAlboForm(instance);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);	
		
		addItem(form);	
		
		setShowTitle(true);
		setHeaderIcon("menu/elenchiAlbi.png");
		
	}
	
}
