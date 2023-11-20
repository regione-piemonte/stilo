/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class CaselleEmailLayout extends CustomLayout {
		
	public CaselleEmailLayout() {				
		
		super("caselle_email",
				new GWTRestDataSource("CaselleEmailDatasource", "idCasella", FieldType.TEXT),  
				new ConfigurableFilter("caselle_email"), 
				new CaselleEmailList("caselle_email"),
				new CaselleEmailDetail("caselle_email"),
				null,
				null,
				null
		);
			
		multiselectButton.hide();	
		newButton.hide();
		
	}
	
	public static boolean isAbilToMod() {
		return true;
	}
	
	public static boolean isAbilToDel() {
		return false;
	}
	
	public static boolean isAbilToModToCreaComeCopia() {
		return Layout.isPrivilegioAttivo("SIC/EML");
	}

	@Override
	public void onSaveButtonClick() {
		final Record record = ((CaselleEmailDetail)detail).getRecordToSave();		
		if(detail.validate()) {
			if(detail instanceof CaselleEmailComeCopiaDetail) {
				realSaveComeCopia(record);
			} else if(detail instanceof CaselleEmailDetail) {
				realSave(record);
			} 
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
		
	protected void realSaveComeCopia(final Record record) {
		Layout.showWaitPopup(I18NUtil.getMessages().caselleEmail_salvaComeCopia_message());		
		try {
			final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("CaselleEmailDatasource", "idCasella", FieldType.TEXT);
			lGWTRestDataSource.executecustom("creaCasellaComeCopia", record, new DSCallback() {					
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						try {
							list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {							
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									viewMode();		
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));								
								}
							});						
						} catch(Exception e) {
							Layout.hideWaitPopup();
						}
						if(!fullScreenDetail) {
							reloadListAndSetCurrentRecord(savedRecord);
						}	
					} else {
						Layout.hideWaitPopup();
					}
				}
			});			
		} catch(Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	@Override
	public String getNewDetailTitle() {
		if(detail instanceof CaselleEmailComeCopiaDetail) {
			return I18NUtil.getMessages().caselleEmail_newComeCopia_title();
		} 
		return I18NUtil.getMessages().caselleEmail_new_title();
	}
	
	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().caselleEmail_view_title(getTipoEstremiRecord(record));		
	}
	
	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().caselleEmail_edit_title(getTipoEstremiRecord(record));
	}
	
	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}
	
	@Override
	public void viewMode() {
		
		super.viewMode();			
		altreOpButton.hide();		
		deleteButton.hide();
	}
	
	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}
	
}
