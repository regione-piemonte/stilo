/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SubProfiliList extends CustomList {
	
	private ListGridField idGruppoPrivField;
	private ListGridField nomeGruppoPrivField;
	private ListGridField noteGruppoPrivField;
	
	public SubProfiliList(String nomeEntita) {
		
		super(nomeEntita);

		// Hidden
		idGruppoPrivField    = new ListGridField("idGruppoPriv");    idGruppoPrivField.setHidden(true);		

		// Visibili
		nomeGruppoPrivField = new ListGridField("nomeGruppoPriv", I18NUtil.getMessages().sub_profili_list_nomeGruppoPrivField_title());
		noteGruppoPrivField = new ListGridField("noteGruppoPriv", I18NUtil.getMessages().sub_profili_list_noteGruppoPrivField_title());
		
		setFields(new ListGridField[] { // Hidden
                                        idGruppoPrivField,
				                        // Visibili
				                        nomeGruppoPrivField,				
				                        noteGruppoPrivField				                        
		                              }
				 );  
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);	
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		}, new DSRequest());	
	}
	
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
		
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		return SubProfiliLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return SubProfiliLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return SubProfiliLayout.isRecordAbilToMod();
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return SubProfiliLayout.isRecordAbilToDel();	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/		
}