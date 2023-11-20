/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author dbe4235
 *
 */

public class CronologiaOperazioniList extends CustomList {

	private ListGridField tsOperazione;
	private ListGridField progressivoOperazioneXOrd;
	private ListGridField idUtenteOperazione;
	private ListGridField descrizioneUtenteOperazione;
	private ListGridField idUtenteOperazioneDelega;
	private ListGridField descrizioneUtenteOperazioneDelega;
	private ListGridField codTipoOperazione;
	private ListGridField descrizioneTipoOperazione;
	private ListGridField descrizioneDettagliOperazione;
	private ListGridField esitoOperazione;
	private ListGridField messaggioErroreOperazione;

	public CronologiaOperazioniList(String nomeEntita) {

		super(nomeEntita, false);			

		tsOperazione = new ListGridField("tsOperazione");
		tsOperazione.setType(ListGridFieldType.DATE);
		tsOperazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsOperazione.setHidden(true);
		tsOperazione.setCanHide(false);				

		progressivoOperazioneXOrd = new ListGridField("progressivoOperazioneXOrd", "Del");
		progressivoOperazioneXOrd.setDisplayField("tsOperazione");
		progressivoOperazioneXOrd.setSortByDisplayField(false);
		progressivoOperazioneXOrd.setCanHide(false);
		progressivoOperazioneXOrd.setCanReorder(false);
		progressivoOperazioneXOrd.setCanFreeze(false);	
		progressivoOperazioneXOrd.setShowDefaultContextMenu(false);
		progressivoOperazioneXOrd.setCanDragResize(true);
		
		idUtenteOperazione = new ListGridField("idUtenteOperazione");
		idUtenteOperazione.setHidden(true);
		idUtenteOperazione.setCanHide(false);

		descrizioneUtenteOperazione = new ListGridField("descrizioneUtenteOperazione", "Effettuato da");
		descrizioneUtenteOperazione.setAlign(Alignment.LEFT);
		
		idUtenteOperazioneDelega = new ListGridField("idUtenteOperazioneDelega");
		idUtenteOperazioneDelega.setHidden(true);
		idUtenteOperazioneDelega.setCanHide(false);

		descrizioneUtenteOperazioneDelega = new ListGridField("descrizioneUtenteOperazioneDelega", "A nome di");
		descrizioneUtenteOperazioneDelega.setAlign(Alignment.LEFT);
		
		codTipoOperazione = new ListGridField("codTipoOperazione");
		codTipoOperazione.setHidden(true);
		codTipoOperazione.setCanHide(false);

		descrizioneTipoOperazione = new ListGridField("descrizioneTipoOperazione", "Tipo");
		descrizioneTipoOperazione.setAlign(Alignment.LEFT);

		descrizioneDettagliOperazione = new ListGridField("descrizioneDettagliOperazione", "Dettagli");
		descrizioneDettagliOperazione.setAlign(Alignment.LEFT);
		
		esitoOperazione = new ListGridField("esitoOperazione", "Esito");
		esitoOperazione.setAlign(Alignment.LEFT);
		
		messaggioErroreOperazione = new ListGridField("messaggioErroreOperazione", "Messaggio di errore");
		messaggioErroreOperazione.setAlign(Alignment.LEFT);
		
		setFields(new ListGridField[] {
				tsOperazione,
				progressivoOperazioneXOrd,
				idUtenteOperazione,
				descrizioneUtenteOperazione,
				idUtenteOperazioneDelega,
				descrizioneUtenteOperazioneDelega,
				codTipoOperazione,
				descrizioneTipoOperazione,
				descrizioneDettagliOperazione,
				esitoOperazione,
				messaggioErroreOperazione
		});  

	}	

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}