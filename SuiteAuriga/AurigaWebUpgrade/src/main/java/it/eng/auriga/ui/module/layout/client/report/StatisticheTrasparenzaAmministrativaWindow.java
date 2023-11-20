/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class StatisticheTrasparenzaAmministrativaWindow extends ModalWindow {

	private StatisticheTrasparenzaAmministrativaDetail portletLayout;
	private ToolStrip bottoni;
	
	public StatisticheTrasparenzaAmministrativaWindow() {
		
		super("statisticheTrasparenzaAmministrativa", false);
		
		setTitle(I18NUtil.getMessages().statisticheTrasparenzaAmministrativa_window_title());

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
						
		portletLayout = new StatisticheTrasparenzaAmministrativaDetail();
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			bottoni = (ToolStrip) portletLayout.getMember("bottoni");
			addBackButton(bottoni);
			portletLayout.addMember(bottoni);		
		}		
		setBody(portletLayout);
		setWidth(730);
		setHeight(180);
		
		setIcon("menu/statisticheTrasparenzaAmministrativa.png");
				
	}	

	private void addBackButton(ToolStrip bottoni) {
		
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				close();
			}
		});
		
		bottoni.addButton(closeModalWindow);
	}

}