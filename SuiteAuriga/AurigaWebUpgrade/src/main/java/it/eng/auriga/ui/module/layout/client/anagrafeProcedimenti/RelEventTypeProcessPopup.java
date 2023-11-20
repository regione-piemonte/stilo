/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.defattivitaprocedimenti.RelEventTypeProcessLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class RelEventTypeProcessPopup extends ModalWindow {

	private RelEventTypeProcessPopup _window;
	protected Canvas portletLayout;

	public RelEventTypeProcessPopup(String idProcessType, final String nomeProcessType) {
		super("rel_event_type_process", true);

		_window = this;

		setTitle("Lista processi");

		// settingsMenu.removeItem(separatorMenuItem);
		// settingsMenu.removeItem(autoSearchMenuItem);

		portletLayout = new RelEventTypeProcessLayout(idProcessType, nomeProcessType) {

			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}

			@Override
			public void hideDetail(boolean reloadList) {
				super.hideDetail(reloadList);
				if (fullScreenDetail) {
					_window.setTitle("Lista tipi eventi collegati al processo: " + nomeProcessType);
				}
			}
		};
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		setBody(portletLayout);

		setIcon("menu/def_attivita_proc.png");

	}

}
