/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class RegoleProtocollazioneAutomaticaCaselleLayout extends CustomLayout {	

//	private RegoleProtocollazioneAutomaticaCaselleDetail detail;
	
	public RegoleProtocollazioneAutomaticaCaselleLayout() {
		this(null, null);
	}
	
	public RegoleProtocollazioneAutomaticaCaselleLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public RegoleProtocollazioneAutomaticaCaselleLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("regole_protocollazione_automatica_caselle_pec_peo", 
				new GWTRestDataSource("RegoleProtocollazioneAutomaticaCaselleDatasource", "idRegola", FieldType.TEXT),  
				new ConfigurableFilter("regole_protocollazione_automatica_caselle_pec_peo"), 
				new RegoleProtocollazioneAutomaticaCaselleList("regole_protocollazione_automatica_caselle_pec_peo") ,
				new RegoleProtocollazioneAutomaticaCaselleDetail("regole_protocollazione_automatica_caselle_pec_peo"),
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
		return true; //Layout.isPrivilegioAttivo("SIC/RA;I");
	}
	
	public static boolean isAbilToMod() {
		return true; //Layout.isPrivilegioAttivo("SIC/RA;M");
	}

	public static boolean isAbilToDel() {
		return true; //Layout.isPrivilegioAttivo("SIC/RA;FC");
	}	
	
	public static boolean isRecordAbilToMod(boolean flgLocked) {
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgLocked) {
		return flgAttivo && !flgLocked && isAbilToDel();
	}	

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_detail_view_title(getTipoEstremiRecord(record));		
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
	
	@Override
	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	@Override
	protected void realSave(final Record record) {
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = new DSCallback() {					
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					
					try {
						((RegoleProtocollazioneAutomaticaCaselleDetail)detail).loadDettaglioAfterSave(savedRecord.getAttributeAsString("idRegola"), new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								detail.editRecord(object);
								detail.getValuesManager().clearErrors(true);
								viewMode();		
								Layout.hideWaitPopup();
//								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(object)), "", MessageType.INFO));								
							}
						});
											
					} catch(Exception e) {
						Layout.hideWaitPopup();
					}					
				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		try {
			if(record.getAttribute("idRegola")==null || record.getAttribute("idRegola").equals("")) {	
				DataSource dataSource2 = getDatasource();
				getDatasource().addData(record, callback);
			} else {
				getDatasource().updateData(record, callback);
			}
		} catch(Exception e) {
			Layout.hideWaitPopup();
		}
	}
}