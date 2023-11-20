/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.layout.client.common.CustomList;

public class OperazioniEffettuateList extends CustomList {

	private ListGridField idUdFolder;
	private ListGridField flgUdFolder;
	private ListGridField tsOperazione;
	private ListGridField tsOperazioneXOrd;
	private ListGridField effettuatoDa;
	private ListGridField aNomeDi;
	private ListGridField tipo;
	private ListGridField dettagli;

	public OperazioniEffettuateList(String nomeEntita) {

		super(nomeEntita, false);			

		idUdFolder = new ListGridField("idUdFolder");
		idUdFolder.setHidden(true);
		idUdFolder.setCanHide(false);

		flgUdFolder = new ListGridField("flgUdFolder");
		flgUdFolder.setHidden(true);
		flgUdFolder.setCanHide(false);

		tsOperazione = new ListGridField("tsOperazione");
		tsOperazione.setType(ListGridFieldType.DATE);
		tsOperazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsOperazione.setHidden(true);
		tsOperazione.setCanHide(false);				

		tsOperazioneXOrd = new ListGridField("tsOperazioneXOrd", "Del");
		tsOperazioneXOrd.setDisplayField("tsOperazione");
		tsOperazioneXOrd.setSortByDisplayField(false);
		tsOperazioneXOrd.setCanHide(false);
		tsOperazioneXOrd.setCanReorder(false);
		tsOperazioneXOrd.setCanFreeze(false);	
		tsOperazioneXOrd.setShowDefaultContextMenu(false);
		tsOperazioneXOrd.setCanDragResize(true);

		effettuatoDa = new ListGridField("effettuatoDa", "Effettuato da");
		effettuatoDa.setAlign(Alignment.LEFT);

		aNomeDi = new ListGridField("aNomeDi", "A nome di");
		aNomeDi.setAlign(Alignment.LEFT);

		tipo = new ListGridField("tipo", "Tipo");
		tipo.setAlign(Alignment.LEFT);

		dettagli = new ListGridField("dettagli", "Dettagli");
		dettagli.setAlign(Alignment.LEFT);
		
		setFields(new ListGridField[] {
				idUdFolder,
				flgUdFolder,
				tsOperazione,
				tsOperazioneXOrd,
				effettuatoDa,
				aNomeDi,
				tipo, 				
				dettagli	
		});  

	}	

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
