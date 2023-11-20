/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;

import it.eng.utility.Styles;

public class DominioWindowSSO extends Window{
	
	private DynamicForm form; 
	
	// Cacco Federico 02-12-2015
	// Mantis 0000027
	// Devo tenere traccia se la login è dovuta alla scadenza della sessione
	private boolean ignoraControlloLingua;
	
	public DominioWindowSSO(boolean ignoraControlloLingua){
		this.ignoraControlloLingua = ignoraControlloLingua;
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(370);		
		setHeight(140);
		setKeepInParentRect(true);
		setTitle("Selezione dominio");
		setShowModalMask(true);
		setModalMaskStyle(Styles.loginModalMask);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		form = new DominioFormSSO(this);
		addItem(form);		
		setShowTitle(true);		
	}

	public void dominioSelezionato(String dominio) {
		this.markForDestroy();
	}

	public void setDominio(LinkedHashMap<String, String> lHashMap) {
		form.getField("dominio").setValueMap(lHashMap);
	}
	
}
