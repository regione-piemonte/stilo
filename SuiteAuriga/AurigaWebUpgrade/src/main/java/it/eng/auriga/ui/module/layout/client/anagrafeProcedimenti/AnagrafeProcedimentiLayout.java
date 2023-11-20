/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class AnagrafeProcedimentiLayout extends CustomLayout {

	public AnagrafeProcedimentiLayout() {
		this(null, null);
	}

	public AnagrafeProcedimentiLayout(String finalita, Boolean showOnlyDetail) {
		super("anagrafe_procedimenti", 
			  new GWTRestDataSource("AnagrafeProcedimentiDataSource", "id", FieldType.TEXT), 
			  new ConfigurableFilter("anagrafe_procedimenti"), 
			  new AnagrafeProcedimentiList("anagrafe_procedimenti"), 
			  new AnagrafeProcedimentiDetail("anagrafe_procedimenti"),
			  finalita, 
			  showOnlyDetail);

		multiselectButton.hide();

		
		if (isAbilToIns() && showNewButton()) {
			newButton.show();
		}
		else{
			newButton.hide();
		}
		
		setMultiselect(false);
	}

	public static boolean isAbilToIns() {
		return true;
	}

	public static boolean isAbilToMod() {
		return true;
	}

	public static boolean isAbilToDel() {
		return true;
	}

	@Override
	public String getNewDetailTitle() {
		return "Nuovo procedimento";
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio procedimento: " + (getTipoEstremiRecord(record));
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica procedimento: " + (getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("nome");
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
	
	public boolean showNewButton(){
		return true;
	}
	
	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();		
		newRecord.setAttribute("id", record.getAttributeAsString("id"));
		newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		newRecord.setAttribute("icona", "menu/anagrafe_procedimenti.png");
		return newRecord;
	}
	
	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}