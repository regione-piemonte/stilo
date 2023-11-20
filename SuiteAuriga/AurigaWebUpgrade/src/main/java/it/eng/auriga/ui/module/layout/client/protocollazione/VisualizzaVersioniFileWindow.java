/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;

/**
 * 
 * @author Cristiano Daniele
 *
 */
public class VisualizzaVersioniFileWindow extends ModalWindow {

	private VisualizzaVersioniFileWindow _window;

	private Canvas portletLayout;

	public VisualizzaVersioniFileWindow(String idDocIn, String title, Record recordProtocollo) {
	       this(idDocIn, false, title, recordProtocollo, true);	
	};
	
	public VisualizzaVersioniFileWindow(String idDocIn, String title, Record recordProtocollo, boolean abilAttestatoConformitaButton) {
		 this(idDocIn, false, title, recordProtocollo, true);	
	};
	
	public VisualizzaVersioniFileWindow(String idDocIn, boolean isFileOmissis, String title, Record recordProtocollo) {
		this(idDocIn, isFileOmissis, title, recordProtocollo, true);	
	};
	
	public VisualizzaVersioniFileWindow(String idDocIn, boolean isFileOmissis, String title, Record recordProtocollo, boolean abilAttestatoConformitaButton) {
		super("visualizza_versioni_file", true);

		setTitle(title);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VisualizzaVersioniFileDataSource");
		lGwtRestDataSource.addParam("idDocIn", idDocIn);
		if(isFileOmissis) {
			lGwtRestDataSource.addParam("isFileOmissis", "true");
		}

		recordProtocollo.setAttribute("idDocIn", idDocIn);
		
		portletLayout = new VisualizzaVersioniFileLayout(lGwtRestDataSource, recordProtocollo, abilAttestatoConformitaButton) {

			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}
		};

		((VisualizzaVersioniFileLayout) portletLayout).setLookup(false);

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("file/version.png");

		show();
	}

}
