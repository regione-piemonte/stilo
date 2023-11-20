/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class UoDestinatariaItem extends ReplicableItem {


	@Override
	public ReplicableCanvas getCanvasToReply() {
		UoDestinatariaCanvas lUoRegistrazioneCanvas = new UoDestinatariaCanvas(this);		
		return lUoRegistrazioneCanvas;
	}

	@Override
	protected ImgButton[] createAddButtons() {

		ImgButton[] addButtons = new ImgButton[1];

		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("[SKIN]actions/add.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();   	
			}   
		});

		//		addButtons[1] = new ImgButton();   
		//		addButtons[1].setSrc("lookup/organigrammamulti.png");   
		//		addButtons[1].setShowDown(false);   
		//		addButtons[1].setShowRollOver(false);      
		//		addButtons[1].setSize(16); 
		//		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupOrganigrammaButton_prompt());
		//		addButtons[1].addClickHandler(new ClickHandler() {	
		//			@Override
		//			public void onClick(ClickEvent event) {
		//				AssegnazioneMultiLookupOrganigramma lookupOrganigrammaPopup = new AssegnazioneMultiLookupOrganigramma(null);				
		//				lookupOrganigrammaPopup.show(); 	
		//			}   
		//		});
//		if (AurigaLayout.getIsAttivaAccessibilita()) {
//			addButtons[0].setTabIndex(null);
//			addButtons[0].setCanFocus(true);
//			addButtons[1].setTabIndex(null);
//			addButtons[1].setCanFocus(true);				
//		}		
		return addButtons;		
	}
	
	public boolean showFlgAssegnaAlDestinatarioItem() {
		return true;
	}

	public boolean isDimOrganigrammaNonStd() {
		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST/ES");		
	}

	public String getFinalitaOrganigrammaLookup() {
		return "ASSEGNAZIONE";
	}

	public String getFinalitaLoadComboOrganigramma() {
		return "ASS";
	}

	public void manageOnChanged() {
		
	}
	
}
