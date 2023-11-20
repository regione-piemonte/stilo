/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class VisualizzaMessaggioOsservazioni extends ModalWindow {

	private HTMLFlow htmlFlow;
	
	public VisualizzaMessaggioOsservazioni(String nomeEntita, Record record) {
		
		super(nomeEntita, true);
		
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setLayoutLeftMargin(5);
		layout.setLayoutRightMargin(5);
		layout.setLayoutTopMargin(5);
		layout.setLayoutBottomMargin(5);
		
		setTitle("Dettaglio messaggio");
		setIcon("archivio/visualizzaFile.png");
		
		htmlFlow = new HTMLFlow();
		htmlFlow.setContents(record.getAttribute("message"));
		layout.addMember(htmlFlow);

		addItem(layout);
		
		show();
	}

}
