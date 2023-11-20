/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class CronologiaOperazioniEffettuateDettEmailList extends CustomList {

	private ListGridField idEmailInItem;
	private ListGridField progressivoAzioneItem;
	private ListGridField tsOperazioneItem;
	private ListGridField tsOperazioneXOrdItem;
	private ListGridField idUtenteOperazioneItem;
	private ListGridField decodificaIdUtenteOperazioneItem;
	private ListGridField idUtenteOperazioneSecondarioItem;
	private ListGridField decodificaIdUtenteOperazioneSecondarioItem;
	private ListGridField tipoOperazioneItem;
	private ListGridField decodificaTipoOperazioneItem;
	private ListGridField dettaglioOperazioneItem;
	private ListGridField esitoOperazioneItem;
	private ListGridField msgErroreItem;

	public CronologiaOperazioniEffettuateDettEmailList(String nomeEntita) {

		super(nomeEntita, false);

		idEmailInItem = new ListGridField("idEmailIn");
		idEmailInItem.setHidden(true);
		idEmailInItem.setCanHide(false);
		
		progressivoAzioneItem = new ListGridField("progressivoAzione");
		progressivoAzioneItem.setHidden(true);
		progressivoAzioneItem.setCanHide(false);				
		
		tsOperazioneItem = new ListGridField("tsOperazione");
		tsOperazioneItem.setType(ListGridFieldType.DATE);
		tsOperazioneItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsOperazioneItem.setHidden(true);
		tsOperazioneItem.setCanHide(false);		
		
		tsOperazioneXOrdItem = new ListGridField("tsOperazioneXOrd", I18NUtil.getMessages().cronologia_operazioni_effettuate_dettemail_del());
		tsOperazioneXOrdItem.setDisplayField("tsOperazione");
		tsOperazioneXOrdItem.setSortByDisplayField(false);
		tsOperazioneXOrdItem.setCanHide(false);
		tsOperazioneXOrdItem.setCanReorder(false);
		tsOperazioneXOrdItem.setCanFreeze(false);	
		tsOperazioneXOrdItem.setShowDefaultContextMenu(false);
		tsOperazioneXOrdItem.setCanDragResize(true);

		idUtenteOperazioneItem = new ListGridField("idUtenteOperazione");
		idUtenteOperazioneItem.setAlign(Alignment.LEFT);
		idUtenteOperazioneItem.setHidden(true);
		idUtenteOperazioneItem.setCanHide(false);

		decodificaIdUtenteOperazioneItem = new ListGridField("decodificaIdUtenteOperazione", I18NUtil.getMessages()
				.cronologia_operazioni_effettuate_dettemail_effettuatoda());
		decodificaIdUtenteOperazioneItem.setAlign(Alignment.LEFT);

		idUtenteOperazioneSecondarioItem = new ListGridField("idUtenteOperazioneSecondario");
		idUtenteOperazioneSecondarioItem.setAlign(Alignment.LEFT);
		idUtenteOperazioneSecondarioItem.setHidden(true);
		idUtenteOperazioneSecondarioItem.setCanHide(false);

		decodificaIdUtenteOperazioneSecondarioItem = new ListGridField("decodificaIdUtenteOperazioneSecondario", I18NUtil.getMessages()
				.cronologia_operazioni_effettuate_dettemail_anomedi());
		decodificaIdUtenteOperazioneSecondarioItem.setAlign(Alignment.LEFT);

		tipoOperazioneItem = new ListGridField("tipoOperazione", I18NUtil.getMessages().cronologia_operazioni_effettuate_dettemail_tipo());
		tipoOperazioneItem.setAlign(Alignment.LEFT);

		decodificaTipoOperazioneItem = new ListGridField("decodificaTipoOperazione");
		decodificaTipoOperazioneItem.setAlign(Alignment.LEFT);
		decodificaTipoOperazioneItem.setHidden(true);
		decodificaTipoOperazioneItem.setCanHide(false);

		dettaglioOperazioneItem = new ListGridField("dettaglioOperazione", I18NUtil.getMessages().cronologia_operazioni_effettuate_dettemail_dettagli());
		dettaglioOperazioneItem.setAlign(Alignment.LEFT);

		esitoOperazioneItem = new ListGridField("esitoOperazione");
		esitoOperazioneItem.setAlign(Alignment.LEFT);
		esitoOperazioneItem.setHidden(true);
		esitoOperazioneItem.setCanHide(false);

		msgErroreItem = new ListGridField("msgErrore");
		msgErroreItem.setAlign(Alignment.LEFT);
		msgErroreItem.setHidden(true);
		msgErroreItem.setCanHide(false);

		setFields(new ListGridField[] {

		idEmailInItem, progressivoAzioneItem, tsOperazioneItem, tsOperazioneXOrdItem, idUtenteOperazioneItem, decodificaIdUtenteOperazioneItem, idUtenteOperazioneSecondarioItem,
				decodificaIdUtenteOperazioneSecondarioItem, tipoOperazioneItem, decodificaTipoOperazioneItem, dettaglioOperazioneItem, esitoOperazioneItem,
				msgErroreItem });

	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

}
