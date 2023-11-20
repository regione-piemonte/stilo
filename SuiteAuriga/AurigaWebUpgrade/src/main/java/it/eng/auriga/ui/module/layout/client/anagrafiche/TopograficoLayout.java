/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class TopograficoLayout extends CustomLayout {	

	public TopograficoLayout() {
		this(null, null);
	}
	
	public TopograficoLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public TopograficoLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("anagrafiche_topografico", 
				new GWTRestDataSource("AnagraficaTopograficoDataSource", "idTopografico", FieldType.TEXT),  
				new ConfigurableFilter("anagrafiche_topografico"), 
				new TopograficoList("anagrafiche_topografico") ,
				new TopograficoDetail("anagrafiche_topografico"),
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
		
		return Layout.isPrivilegioAttivo("UT/TOP;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("UT/TOP;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("UT/TOP;FC");
	}	
	
	public static boolean isRecordAbilToMod(boolean flgLocked) {
		
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgLocked) {
		
		return flgAttivo && !flgLocked && isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().topografico_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().topografico_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().topografico_detail_view_title(getTipoEstremiRecord(record));		
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

		// TODO ......
		return newRecord;
	}

}
