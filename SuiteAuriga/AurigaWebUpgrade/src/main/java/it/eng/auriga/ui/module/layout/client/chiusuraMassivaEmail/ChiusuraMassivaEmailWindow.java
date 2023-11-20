/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ChiusuraMassivaEmailWindow extends ModalWindow {

	private ChiusuraMassivaEmailWindow window;

	private ChiusuraMassivaEmailLayout portletLayout;
	private String nomeEntita;

	public ChiusuraMassivaEmailWindow(String nomeEntita) {
		
		super(nomeEntita, false);
		setNomeEntita(nomeEntita);
		
		window = this;
		
		setTitle("Richiesta chiusura massiva e-mail");

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new ChiusuraMassivaEmailLayout(window);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
		setWidth(600);
		setHeight(250);
		
		setIcon("menu/chiusura_massiva_email.png");
				
	}

	public String getNomeEntita() {
		return nomeEntita;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	
}
