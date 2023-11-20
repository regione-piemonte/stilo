/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class ProfiliLayout extends CustomLayout {	

	public ProfiliLayout() {
		this(null, null);
	}
	
	public ProfiliLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public ProfiliLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("profili", 
				new GWTRestDataSource("ProfiliDataSource", "idProfilo", FieldType.TEXT),  
				new ConfigurableFilter("profili"), 
				new ProfiliList("profili") ,
				new ProfiliDetail("profili"),
				null,
				flgSelezioneSingola,
				showOnlyDetail
		);
		multiselectButton.hide();	

		if (!isAbilToIns()) {
			newButton.hide();
		}

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
	
	public static boolean isRecordAbilToMod() {		
		return isAbilToMod();
	}

	public static boolean isRecordAbilToDel() {
		return isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().profili_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().profili_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().profili_detail_view_title(getTipoEstremiRecord(record));		
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