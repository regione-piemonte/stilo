/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class LogOperazioniLayout extends CustomLayout {	

	public LogOperazioniLayout() {
		this(null, null);
	}
	
	public LogOperazioniLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public LogOperazioniLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("log_operazioni", 
				new GWTRestDataSource("LogOperazioniDataSource", "idLogOperazione", FieldType.TEXT),  
				new ConfigurableFilter("log_operazioni"), 
				new LogOperazioniList("log_operazioni") ,
				new LogOperazioniDetail("log_operazioni"),
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
		return false;
	}
	
	public static boolean isAbilToMod() {
		return false;
	}

	public static boolean isAbilToDel() {
		return false;
	}	
	
	public static boolean isRecordAbilToMod() {		
		return isAbilToMod();
	}

	public static boolean isRecordAbilToDel() {
		return isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().log_operazioni_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().log_operazioni_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().log_operazioni_detail_view_title(getTipoEstremiRecord(record));		
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


	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		return new GWTRestDataSource("LogOperazioniDataSource", "idLogOperazione", FieldType.TEXT);
	}

	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
}