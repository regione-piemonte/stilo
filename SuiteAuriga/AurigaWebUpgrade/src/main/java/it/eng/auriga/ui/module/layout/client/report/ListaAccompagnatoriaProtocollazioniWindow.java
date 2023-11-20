/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class ListaAccompagnatoriaProtocollazioniWindow extends ModalWindow {
	
	private ListaAccompagnatoriaProtocollazioniDetail portletLayout;
	protected ToolStrip detailToolStrip;
	
	public ListaAccompagnatoriaProtocollazioniWindow() {
		
		super("listaAccompagnatoriaProtocollazioni", false);
		setTitle(I18NUtil.getMessages().listaAccompagnatoriaProtocollazioniwindow_title());

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
						
		portletLayout = new ListaAccompagnatoriaProtocollazioniDetail("listaAccompagnatoriaProtocollazioni");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailToolStrip = (ToolStrip) portletLayout.getMember("bottoni");
			addBackButton();
			portletLayout.setHeight(450);
			portletLayout.addMember(detailToolStrip);		
		}		
		setBody(portletLayout);
		
		setIcon("menu/listaAccompagnatoriaProt.png");
		
		portletLayout.markForRedraw();
	}	
	
	private void addBackButton() {
		
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow);
	}

}