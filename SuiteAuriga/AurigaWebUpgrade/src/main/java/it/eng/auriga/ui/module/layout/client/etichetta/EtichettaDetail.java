/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class EtichettaDetail extends CustomDetail {
	
	private EtichettaWindow window;
	private VLayout opzioniLayout;
	private EtichettaForm opzioniEtichettaForm;
	private VLayout vLayoutDetail;
	private DetailToolStripButton etichettaButton;
	private ToolStrip detailToolStrip;
	
	
	public EtichettaDetail(String nomeEntita, EtichettaWindow etichettaWindow) {
		super(nomeEntita);
		window = etichettaWindow;
		opzioniLayout = new VLayout(); 
		opzioniEtichettaForm = new EtichettaForm();
		opzioniLayout.setMembers(opzioniEtichettaForm);
		initEtichettaButton();
		initToolStrip();
		vLayoutDetail = new VLayout();
		vLayoutDetail.setWidth100();
		vLayoutDetail.setMembers(opzioniLayout, etichettaButton);
		addMember(vLayoutDetail);
	}


	private void initToolStrip() {
		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(etichettaButton);
		
	}


	private void initEtichettaButton() {
		etichettaButton = new DetailToolStripButton("Procedi", "ok.png");
		etichettaButton.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) { 
				stampa();		
			}   
		});
		
	}


	protected void stampa() {
		
		
	}

}
