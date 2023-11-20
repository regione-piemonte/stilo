/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;

public class ListaFunzionalitaWindow extends ModalWindow{

	ListaFunzionalitaWindow _window;
	protected Canvas portletLayout;

	protected String windowTitle;
	
	public ListaFunzionalitaWindow(String title, final String idProfilo, final String nomeProfilo, final boolean funzioniAbilitate, final boolean canEditProfilo) {
	    
		this(title, idProfilo, nomeProfilo, funzioniAbilitate, canEditProfilo, "PR");
	}
	
	public ListaFunzionalitaWindow(String title, final String idProfilo, final String nomeProfilo, final boolean funzioniAbilitate, final boolean canEditProfilo, String flgTpDestAbil) {
		
		super("lista_funzionalita",true);
		
		_window = this;
		
		windowTitle = title + " " + nomeProfilo;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaListaFunzionalitaDataSource");
		lGWTRestDataSource.addParam("flgStatoAbil", funzioniAbilitate ? "1" : "0");
		lGWTRestDataSource.addParam("flgTpDestAbil", flgTpDestAbil);
		lGWTRestDataSource.addParam("idDestAbil", idProfilo);
		
		portletLayout = new ListaFunzionalitaLayout(lGWTRestDataSource, idProfilo, nomeProfilo, flgTpDestAbil) {
			@Override
			public boolean isForcedToAutoSearch() {
				
				return true;
			}	
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle(windowTitle); 
				} 	
			}
			
			@Override
			public boolean getCanEditProfilo() 
			{		
				return canEditProfilo;
			}
			
			@Override
			public boolean isFunzioniAbilitate() {
				
				return funzioniAbilitate;
			}
			
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);		
		
		setIcon("lookup/abilfunzioni.png");

	}
	
	public void ricaricaLista() {
		((ListaFunzionalitaLayout)portletLayout).doSearch();
	}
	
}