/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */
public class ProcedimentiCollegatiPopup extends ModalWindow {
	
	private ProcedimentiCollegatiList procedimentiCollegatiList;

	public ProcedimentiCollegatiPopup(String nomeEntita, RecordList listaProcessi) {
		super(nomeEntita, true, true);
		
		setModalMaskOpacity(50);
		setHeight(348);
		setWidth(550);	
		setKeepInParentRect(true);
		setTitle("Lista procedimenti collegati");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		procedimentiCollegatiList = new ProcedimentiCollegatiList("procedimenti_collegati_list");
		procedimentiCollegatiList.setCanEdit(false);
		procedimentiCollegatiList.setData(listaProcessi);
	
		VLayout layout = new VLayout();  
		layout.setPadding(5);
		layout.setHeight100();
		layout.setWidth100();
		layout.addMember(procedimentiCollegatiList);

		setIcon("buttons/link.png");
		setBody(layout);
	}

}