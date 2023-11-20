/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class DefAttivitaProcedimentiLayout extends CustomLayout{
	
	public DefAttivitaProcedimentiLayout(){
		super("definizione_attivita_procedimenti",
		       new GWTRestDataSource("DefAttivitaProcedimentiDataSource","idEventType",FieldType.TEXT),
			   new ConfigurableFilter("definizione_attivita_procedimenti"),
			   new DefAttivitaProcedimentiList("definizione_attivita_procedimenti"),
			   new DefAttivitaProcedimentiDetail("definizione_attivita_procedimenti"));
		
		multiselectButton.hide();
		
		if (!isAbilToIns()) {
			newButton.hide();
		}
		 
	}
	
	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("GP/EV;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("GP/EV;M");
	}
	
	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("GP/EV;FC");
	}
	
	public static boolean isRecordAbilToDel(boolean flgAnnLogico) {
		return !flgAnnLogico && isAbilToDel();
	}
		
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().definizione_attivita_procedimenti_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().definizione_attivita_procedimenti_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().definizione_attivita_procedimenti_view_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("descrizione");
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
		final boolean flgAnnLogico  = record.getAttribute("flgAnnLogico") != null && record.getAttributeAsString("flgAnnLogico").equals("0");
		if(isRecordAbilToDel(flgAnnLogico)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isAbilToMod()) {
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

}
