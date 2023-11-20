/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class StampaRegProtWindow extends ModalWindow {

	private StampaRegProtWindow window;

	private StampaRegProtLayout portletLayout;
	private String nomeEntita;

	public StampaRegProtWindow(String nomeEntita) {
		
		super(nomeEntita, false);
		setNomeEntita(nomeEntita);
		
		window = this;
		
		if(nomeEntita.equalsIgnoreCase("stampa_reg_prot"))
			setTitle("Stampa registro di protocollo");
		else if(nomeEntita.equalsIgnoreCase("stampa_reg_repertorio"))
			setTitle("Stampa registro di repertorio");
		else if (nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni"))
			setTitle("Stampa registro di pubblicazioni");
		else
			setTitle("Stampa registro di proposte atti");

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new StampaRegProtLayout(window);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
		setWidth(600);
		setHeight(200);
		
		setIcon("menu/stampa_reg_prot.png");
				
	}

	public String getNomeEntita() {
		return nomeEntita;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	
}
