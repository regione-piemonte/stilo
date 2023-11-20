/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class RelEventTypeProcessLayout extends CustomLayout {

	public RelEventTypeProcessLayout() {
		this(null, null);
	}

	public RelEventTypeProcessLayout(String id, String nome) {
		super("rel_event_type_process", getGWTRestDataSource(id, nome), new ConfigurableFilter("rel_event_type_process"), new RelEventTypeProcessList(
				"rel_event_type_process"), new RelEventTypeProcessDetail("rel_event_type_process"));

		multiselectButton.hide();

		if (!isAbilToIns()) {
			newButton.hide();
		}

	}

	private static GWTRestDataSource getGWTRestDataSource(String id, String nome) {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RelEventTypeProcessDataSource", "idEventTypeIn", FieldType.TEXT);
		lGWTRestDataSource.addParam("idProcessType", id);
		lGWTRestDataSource.addParam("nomeProcessType", nome);
		return lGWTRestDataSource;
	}

	public static boolean isAbilToIns() {
		return true;
	}

	public static boolean isAbilToMod() {
		return false;
	}

	public static boolean isAbilToDel() {
		return true;
	}

	public static boolean isRecordAbilToDel(boolean flgRecordNonCancellabile) {
		return !flgRecordNonCancellabile && isAbilToDel();
	}

	@Override
	public String getNewDetailTitle() {
		return "Nuova relazione tipo evento-processo";
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica relazione tipo evento-processo" + getTipoEstremiRecord(record);
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio relazione tipo evento-processo" + getTipoEstremiRecord(record);
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		
		return record.getAttribute("desEventTypeIn");
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		Record record = new Record(detail.getValuesManager().getValues());
		final boolean flgRecordNonCancellabile = record.getAttribute("flgVldXTuttiProcAmmIO") != null && record.getAttributeAsBoolean("flgVldXTuttiProcAmmIO");
		if (isRecordAbilToDel(flgRecordNonCancellabile)) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		if (isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		altreOpButton.hide();

	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

}
