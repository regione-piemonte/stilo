/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 
 * @author cristiano
 *
 */

public class TrovaVieList extends CustomList {

	private ListGridField descrNomeToponimo;
	private ListGridField tipoToponimo;
	private ListGridField codiceViarioToponimo;
	private ListGridField capVie;
	private ListGridField zonaVie;

	public TrovaVieList(String nomeEntita) {
		super(nomeEntita);
		// TODO Auto-generated constructor stub

		descrNomeToponimo = new ListGridField("descrNomeToponimo", I18NUtil.getMessages().trova_vie_list_descrNomeToponimo());

		tipoToponimo = new ListGridField("tipoToponimo", I18NUtil.getMessages().trova_vie_list_tipoToponimo());

		codiceViarioToponimo = new ListGridField("codiceViarioToponimo", I18NUtil.getMessages().trova_vie_list_codiceViarioToponimo());

		capVie = new ListGridField("capVie", I18NUtil.getMessages().trova_vie_list_cap());

		zonaVie = new ListGridField("zonaVie", I18NUtil.getMessages().trova_vie_list_zona());

		setFields(descrNomeToponimo, tipoToponimo, codiceViarioToponimo, capVie, zonaVie);
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
