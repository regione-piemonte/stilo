/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class SubProfiliLayout extends CustomLayout {	

	public SubProfiliLayout() {
		this(null, null);
	}
	
	public SubProfiliLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public SubProfiliLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("sub_profili", 
				new GWTRestDataSource("SubProfiliDataSource", "idGruppoPriv", FieldType.TEXT),  
				new ConfigurableFilter("sub_profili"), 
				new SubProfiliList("sub_profili") ,
				new SubProfiliDetail("sub_profili"),
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
		return Layout.isPrivilegioAttivo("SIC/GP;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("SIC/GP;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("SIC/GP;FC");
	}	
	
	public static boolean isRecordAbilToMod() {		
		return isAbilToMod();
	}

	public static boolean isRecordAbilToDel() {
		return isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().sub_profili_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().sub_profili_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().sub_profili_detail_view_title(getTipoEstremiRecord(record));		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();	
		if(isAbilToDel()){
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