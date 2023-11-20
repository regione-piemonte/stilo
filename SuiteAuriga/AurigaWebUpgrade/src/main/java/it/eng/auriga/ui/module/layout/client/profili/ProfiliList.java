/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProfiliList extends CustomList {
	
	private ListGridField idProfiloField;
	private ListGridField nomeProfiloField;
		
	protected ControlListGridField viewDettagliOperazioneButtonField;		
	
	public ProfiliList(String nomeEntita) {
		
		super(nomeEntita);
		
		// Visibili
		nomeProfiloField               = new ListGridField("nomeProfilo", 	            I18NUtil.getMessages().profili_list_nomeProfiloField_title());
		
		// Hidden
		idProfiloField    = new ListGridField("idProfilo");    idProfiloField.setHidden(true);		
		
		setFields(new ListGridField[] { // Visibili
				                        nomeProfiloField,				
				                        // Hidden
				                        idProfiloField
		                              }
				 );  
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

		
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		return ProfiliLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return ProfiliLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return ProfiliLayout.isRecordAbilToMod();
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return ProfiliLayout.isRecordAbilToDel();	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
		
}
