/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ProtocollazioneList extends CustomList {

	private ListGridField id; 
	private ListGridField nome;
	private ListGridField cognome;
	private ListGridField denominazione;
	private ListGridField oggetto;

	public ProtocollazioneList(String nomeEntita){

		super(nomeEntita);

		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);

		nome 			= new ListGridField("nome", "Nome");
		cognome 		= new ListGridField("cognome", "Cognome");
		denominazione 	= new ListGridField("denominazione", "Denominazione");
		oggetto 		= new ListGridField("oggetto", "Oggetto");

		setFields(
				id, 
				nome,
				cognome,
				denominazione,
				oggetto

				);

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
	}

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
