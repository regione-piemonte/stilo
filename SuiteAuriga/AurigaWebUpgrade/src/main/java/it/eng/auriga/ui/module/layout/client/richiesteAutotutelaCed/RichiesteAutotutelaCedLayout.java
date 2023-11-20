/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class RichiesteAutotutelaCedLayout extends CustomLayout {

	public RichiesteAutotutelaCedLayout(String nomeEntita) {

		super(nomeEntita, new GWTRestDataSource("RichiesteAutotutelaCedDataSource", "idRichiesta", FieldType.TEXT), new ConfigurableFilter(nomeEntita),
				new RichiesteAutotutelaCedList(nomeEntita), new RichiesteAutotutelaCedDetail(nomeEntita), null, null);

		multiselectButton.hide();
		newButton.hide();

	}

	@Override
	public String getTipoEstremiRecord(Record record) {

		return record.getAttribute("idRichiesta");
	}

	@Override
	public String getNewDetailTitle() {
		return "Nuova richiesta";
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio richiesta " + record.getAttribute("idRichiesta");
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica richiesta " + record.getAttribute("idRichiesta");
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();
		deleteButton.hide();
		editButton.hide();
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

}
