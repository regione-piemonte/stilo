/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class ListaFunzionalitaList extends CustomList{
	
	private ListGridField codice;
	private ListGridField codFunzP1;
	private ListGridField codFunzP2;
	private ListGridField codFunzP3;
	private ListGridField descrizione;
	private ListGridField flgSoloDisp;
	
	public ListaFunzionalitaList(String nomeEntita)
	{
		super(nomeEntita, false);			
		
		codice = new ListGridField("codice",I18NUtil.getMessages().lista_funzionalita_codice());
		codice.setHidden(true);
		
		codFunzP1 = new ListGridField("codFunzP1",I18NUtil.getMessages().lista_funzionalita_codFunzP1());
		codFunzP2 = new ListGridField("codFunzP2",I18NUtil.getMessages().lista_funzionalita_codFunzP2());
		codFunzP3 = new ListGridField("codFunzP3",I18NUtil.getMessages().lista_funzionalita_codFunzP3());
		descrizione  = new ListGridField("descrizione",I18NUtil.getMessages().lista_funzionalita_descrizione());
		
		flgSoloDisp = new ListGridField("flgSoloDisp",I18NUtil.getMessages().lista_funzionalita_flgSoloDisp());
		flgSoloDisp.setHidden(true);
		
		setFields(
			codice,
			codFunzP1,
			codFunzP2,
			codFunzP3,
			descrizione,
			flgSoloDisp				
		);
	}	
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 25;
	}
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {
			
		Canvas lCanvasReturn = null;
			
		if (fieldName.equals("buttons")) {				
				
			ImgButton lookupButton = buildLookupButton(record);		
				
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(1);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
							
			if(layout.isLookup()) {
				if(!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
			}
				
			lCanvasReturn = recordCanvas;
						
		}			
			
		return lCanvasReturn;
	}

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	 public boolean isDisableRecordComponent() {
		
		return true;
	 };
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
