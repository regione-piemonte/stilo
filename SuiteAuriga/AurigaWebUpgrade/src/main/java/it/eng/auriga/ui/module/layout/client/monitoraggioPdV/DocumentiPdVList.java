/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author DANCRIST
 *
 */

public class DocumentiPdVList extends CustomList {
	
	private ListGridField idPdVField;
	private ListGridField idDocOriginaleField;
	private ListGridField esitoField;
	private ListGridField codiceErroreField;
	private ListGridField messaggioErroreField;
	private ListGridField idTipologiaDocField;
	private ListGridField nomeTipologiaDocField;
	private ListGridField idDocSdCField;
	
	public DocumentiPdVList(String nomeEntita) {
		
		super(nomeEntita);

		idPdVField = new ListGridField("idPdV", I18NUtil.getMessages().documentiPdV_list_idPdVField_title());
		idPdVField.setHidden(true);
		idPdVField.setCanHide(false);
		idPdVField.setCanGroupBy(false);
		idPdVField.setCanReorder(false);
		idPdVField.setCanSort(false);
		idPdVField.setCanFreeze(false);
		idPdVField.setCanExport(false);
		idPdVField.setShowHover(false);
		
		idDocOriginaleField = new ListGridField("idDocOriginale", I18NUtil.getMessages().documentiPdV_list_idDocOriginaleField_title());
		idDocOriginaleField.setWrap(false);

		esitoField = new ListGridField("esito", I18NUtil.getMessages().documentiPdV_list_esitoField_title());
		esitoField.setWrap(false);
		
		codiceErroreField = new ListGridField("codiceErrore", I18NUtil.getMessages().documentiPdV_list_codiceErroreField_title());
		codiceErroreField.setWrap(false);
		
		messaggioErroreField  = new ListGridField("messaggioErrore", I18NUtil.getMessages().documentiPdV_list_messaggioErroreField_title());
		messaggioErroreField.setWrap(false);
		
		idTipologiaDocField = new ListGridField("idTipologiaDoc",I18NUtil.getMessages().documentiPdV_list_idTipologiaDocField_title());
		idTipologiaDocField.setHidden(true);
		idTipologiaDocField.setCanHide(false);
		idTipologiaDocField.setCanGroupBy(false);
		idTipologiaDocField.setCanReorder(false);
		idTipologiaDocField.setCanSort(false);
		idTipologiaDocField.setCanFreeze(false);
		idTipologiaDocField.setCanExport(false);
		idTipologiaDocField.setShowHover(false);
		
		nomeTipologiaDocField = new ListGridField("nomeTipologiaDoc", I18NUtil.getMessages().documentiPdV_list_nomeTipologiaDocField_title());
		nomeTipologiaDocField.setWrap(false);
		
		idDocSdCField = new ListGridField("idDocSdC", I18NUtil.getMessages().documentiPdV_list_idDocSdCField_title());
		idDocSdCField.setWrap(false);
		
		setFields(new ListGridField[] {
			idPdVField,
			idDocOriginaleField,
			esitoField,
			codiceErroreField,
			messaggioErroreField,
			idTipologiaDocField,
			nomeTipologiaDocField,
			idDocSdCField,
		});
		
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
	
	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
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
		return DocumentiPdVLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return DocumentiPdVLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return true;
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return true;
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
}
