/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * @author DANCRIST
 *
 */

public class StatisticheMailStoricizzateWindow extends ModalWindow {
	
	private StatisticheMailStoricizzateDetail portletLayout;
	
	public StatisticheMailStoricizzateWindow() {
		
		super("statisticheMailStoricizzate", false);
		
		setTitle(I18NUtil.getMessages().statisticheMailStoricizzate_window_title());

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
						
		portletLayout = new StatisticheMailStoricizzateDetail();
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
		setWidth(880);
		setHeight(640);
		
		setIcon("menu/statisticheMailStoricizzate.png");
		
		portletLayout.markForRedraw();
	}	
}