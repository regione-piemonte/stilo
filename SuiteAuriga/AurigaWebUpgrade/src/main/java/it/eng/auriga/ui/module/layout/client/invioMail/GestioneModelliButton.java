/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.GestioneModelliWindow;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class GestioneModelliButton extends ToolStripButton{
	
	private String nomeEntita; 
	/**
	 * Costruttore in cui si esegue l'inizializzazione del button
	 */
	public GestioneModelliButton(final TipologiaMail tipologiaMail, final SelectItem modelliSelectItem) {
		super("", "modelli/modelli.png");
		
		setPrompt(I18NUtil.getMessages().gestioneModelli_title());
		
		if(tipologiaMail.equals(TipologiaMail.INOLTRO)){
			nomeEntita = "inoltro"; 
		}
		else if(tipologiaMail.equals(TipologiaMail.RISPOSTA)){
			nomeEntita = "risposta"; 
		}
		else if(tipologiaMail.equals(TipologiaMail.INVIO_UD)){
			nomeEntita = "invio_documento"; 
		} 
			
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				new GestioneModelliWindow(nomeEntita, "gestioneModelliWindow", tipologiaMail, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							/*
							 * Per l'aggiornamento della select dei modelli nella schermata iniziale.
							 * Senza questi non verrebbe aggiornata con nuovi modelli inseriti
							 */
							modelliSelectItem.clearValue();
							modelliSelectItem.fetchData();
						}
					}
				});
			}
		});
	}
	
	protected ToolStripButton getButton(){
		//Ritorno il pulsante appena creato
		return this;
	}
}