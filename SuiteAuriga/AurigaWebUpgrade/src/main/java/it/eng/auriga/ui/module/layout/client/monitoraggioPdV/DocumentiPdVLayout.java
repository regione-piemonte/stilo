/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author DANCRIST
 *
 */

public class DocumentiPdVLayout extends CustomLayout {
	
	public DocumentiPdVLayout(String idPdV) {
		
		super("documentiPdV",
			getDocumentiPdVDataSource(idPdV), 
			new ConfigurableFilter("documentiPdV"),
			new DocumentiPdVList("documentiPdV"),
			new CustomDetail("documentiPdV"), 
			null, 
			null,
			null);
			
		multiselectButton.hide();

		if (!isAbilToIns()) {
			newButton.hide();
		}
	}
	
	public static GWTRestDataSource getDocumentiPdVDataSource(String idPdV) {
		
		GWTRestDataSource lDocumentiPdVDataSource = new GWTRestDataSource("DocumentiPdVDataSource", "idDocOriginale", FieldType.TEXT);
		lDocumentiPdVDataSource.addParam("idPdV", idPdV);
		return lDocumentiPdVDataSource;
	}
	
	public static boolean isAbilToIns() {
		return false;
	}

	public static boolean isAbilToMod() {
		return false;
	}

	public static boolean isAbilToDel() {
		return false;
	}

	@Override
	public String getNewDetailTitle() {
		return null;
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return null;
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return null;
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("idDocOriginale");
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToDel()) {
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
