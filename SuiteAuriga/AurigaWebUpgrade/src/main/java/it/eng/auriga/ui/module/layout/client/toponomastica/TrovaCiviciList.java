/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 
 * @author cristiano
 *
 */
public class TrovaCiviciList extends CustomList {

	private ListGridField idCivico;
	private ListGridField civico;
	private ListGridField nrCivico;
	private ListGridField esponenteBarrato;
	private ListGridField cap;
	private ListGridField zona;

	public TrovaCiviciList(String nomeEntita) {
		super(nomeEntita);

		idCivico = new ListGridField("idCivico", I18NUtil.getMessages().trova_vie_list_tipoToponimo());
		idCivico.setCanHide(true);
		idCivico.setHidden(true);

		civico = new ListGridField("civico", I18NUtil.getMessages().trova_civici_list_civico());

		nrCivico = new ListGridField("nrCivico", I18NUtil.getMessages().trova_civici_list_nrCivico());
		nrCivico.setType(ListGridFieldType.INTEGER);

		esponenteBarrato = new ListGridField("esponenteBarrato", I18NUtil.getMessages().trova_civici_list_esponenteBarrato());

		cap = new ListGridField("cap", I18NUtil.getMessages().trova_civici_list_cap());

		zona = new ListGridField("zona", I18NUtil.getMessages().trova_civici_list_zona());

		setFields(idCivico, civico, nrCivico, esponenteBarrato, cap, zona);
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return TrovaVieLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return TrovaVieLayout.isAbilToDel();
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return true;
	}
}
