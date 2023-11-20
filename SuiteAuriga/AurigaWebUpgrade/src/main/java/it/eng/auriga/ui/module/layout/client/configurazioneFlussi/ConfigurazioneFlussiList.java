/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class ConfigurazioneFlussiList extends CustomList {

	private ListGridField idTaskField;
	private ListGridField nomeTaskField;
	private ListGridField idFaseField;
	private ListGridField nomeFaseField;
	private ListGridField numeroOrdineField;

	protected ControlListGridField configuraButtonField;

	public ConfigurazioneFlussiList(String nomeEntita) {
		
		super(nomeEntita);
		
		idTaskField = new ListGridField("idTask", "Id. task");
		
		nomeTaskField = new ListGridField("nomeTask", "Nome task");
		
		idFaseField = new ListGridField("idFase", "Id. fase");
		idFaseField.setHidden(true);
		idFaseField.setCanHide(false);
		idFaseField.setCanSort(false);
		
		nomeFaseField = new ListGridField("nomeFase", "Fase");
		
		numeroOrdineField = new ListGridField("numeroOrdine", "N° ordine visualizzazione");
		numeroOrdineField.setType(ListGridFieldType.INTEGER);
		
		setFields(idTaskField, nomeTaskField, idFaseField, nomeFaseField, numeroOrdineField);
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
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		SC.ask(it.eng.auriga.ui.module.layout.client.i18n.I18NUtil.getMessages().configurazione_flussi_deleteButtonAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				if(value) {							
					removeData(record, new DSCallback() {								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout != null ? layout.getTipoEstremiRecord(record) : ""), "", MessageType.INFO));
								if(layout != null) layout.hideDetail(true);
							} 
//							else {
//								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout != null ? layout.getTipoEstremiRecord(record) : ""), "", MessageType.ERROR));										
//							}																																	
						}
					});													
				}
			}
		});     	
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
	protected String getDetailButtonPrompt() {
		return "Visualizza dettaglio";
	}
	
	@Override
	protected String getModifyButtonPrompt() {
		return "Profilatura";
	}

	@Override
	protected boolean showModifyButtonField() {
		return ConfigurazioneFlussiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return ConfigurazioneFlussiLayout.isAbilToDel();
	}	
	
	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		String idTask = record.getAttributeAsString("idTask");
		if (idTask == null || "".equalsIgnoreCase(idTask)) {
			return showDeleteButtonField();
		} else {
			return false;
		}
	}
	
	@Override
	protected String getDeleteButtonPrompt() {
		return it.eng.auriga.ui.module.layout.client.i18n.I18NUtil.getMessages().configurazione_flussi_deleteButton_prompt();
	}
	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
