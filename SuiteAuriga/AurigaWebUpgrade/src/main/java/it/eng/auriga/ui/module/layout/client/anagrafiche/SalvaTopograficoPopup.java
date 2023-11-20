/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;

public abstract class SalvaTopograficoPopup extends ModalWindow {

	private SalvaTopograficoPopup _window;
	
	private TopograficoLayout portletLayout;
    private String idTopografico;
    private String codiceRapido;
    private String nome;
    private String descrizione;
    private String note;

	
	public SalvaTopograficoPopup(final Record record) {
		
		super("anagrafiche_topografico.detail", true);
		
		idTopografico = record.getAttribute("idTopografico");
		codiceRapido  = record.getAttribute("codiceRapido");
		nome          = record.getAttribute("nome");
		descrizione   = record.getAttribute("descrizione");
		note          = record.getAttribute("note");
		
		_window = this;
		
		setTitle("Compila dati topografico");  	
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
		portletLayout = new TopograficoLayout(true) {
			@Override
			public void lookupBack(Record record) {
				
				manageLookupBack(record);
				_window.markForDestroy();
			}			
		};
				
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
	
		portletLayout.newMode();
		
		
		((TopograficoDetail)portletLayout.getDetail()).getForm().idTopograficoItem.setValue(idTopografico);		
		((TopograficoDetail)portletLayout.getDetail()).getForm().codiceRapidoItem.setValue(codiceRapido);		
		((TopograficoDetail)portletLayout.getDetail()).getForm().nomeItem.setValue(nome);		
		((TopograficoDetail)portletLayout.getDetail()).getForm().descrizioneItem.setValue(descrizione);
		((TopograficoDetail)portletLayout.getDetail()).getForm().noteItem.setValue(note);        
         
        setIcon("menu/topografico.png");
              
	}
	
	@Override
	public void manageOnCloseClick() {
		
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public abstract void manageLookupBack(Record record);
	
}
