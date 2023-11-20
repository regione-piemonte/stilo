/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class RuoliAmministrativiLayout extends CustomLayout {	

	public RuoliAmministrativiLayout() {
		this(null, null);
	}
	
	public RuoliAmministrativiLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public RuoliAmministrativiLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("anagrafiche_ruoli_amministrativi", 
				new GWTRestDataSource("AnagraficaRuoliAmministrativiDataSource", "idRuolo", FieldType.TEXT),  
				new ConfigurableFilter("anagrafiche_ruoli_amministrativi"), 
				new RuoliAmministrativiList("anagrafiche_ruoli_amministrativi") ,
				new RuoliAmministrativiDetail("anagrafiche_ruoli_amministrativi"),
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
		return Layout.isPrivilegioAttivo("SIC/RA;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("SIC/RA;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("SIC/RA;FC");
	}	
	
	public static boolean isRecordAbilToMod(boolean flgLocked) {
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgLocked) {
		return flgAttivo && !flgLocked && isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().ruoli_amministrativi_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().ruoli_amministrativi_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().ruoli_amministrativi_detail_view_title(getTipoEstremiRecord(record));		
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
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		if(isRecordAbilToDel(flgValido, recProtetto)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(recProtetto)) {
			editButton.show();
		} else{
			editButton.hide();
		}		
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();
		return newRecord;
	}
}