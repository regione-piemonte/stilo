/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class MonitoraggioPdVLayout extends CustomLayout {
	
	public MonitoraggioPdVLayout() {
		this(null, null, null);
	}

	public MonitoraggioPdVLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		
		super("monitoraggioPdV",
				new GWTRestDataSource("MonitoraggioPdVDataSource", "idPdV", FieldType.TEXT), 
				new MonitoraggioPdVFilter("monitoraggioPdV") {
					
					@Override
					protected SelectItem createSelectField() {	
						SelectItem selectField = super.createSelectField();
						selectField.setWidth(300);
						return selectField;
					}
				},
				new MonitoraggioPdVList("monitoraggioPdV"),
				new CustomDetail("monitoraggioPdV"), 
				finalita, flgSelezioneSingola, showOnlyDetail);

		multiselectButton.hide();

		newButton.hide();		
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource referenceDatasource = new GWTRestDataSource("MonitoraggioPdVDataSource", "idEmail", FieldType.TEXT);
		// permette l'interazione utente anche durante l'elaborazione lato server
		referenceDatasource.setForceToShowPrompt(false);
		return referenceDatasource;
	}

	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().monitoraggioPdV_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().monitoraggioPdV_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().monitoraggioPdV_view_title(getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("idPdV");
	}

	@Override
	public void newMode() {
		super.newMode();			
		editButton.hide();
		deleteButton.hide();
		altreOpButton.hide();
	}
	
	@Override
	public void viewMode() {
		super.viewMode();		
		editButton.hide();
		deleteButton.hide();
		altreOpButton.hide();		
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);		
		editButton.hide();
		deleteButton.hide();
		altreOpButton.hide();
	}
	

}
