/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author DANCRIST
 *
 */

public class ContenutoFoglioImportatoLayout extends CustomLayout {
	
	
	public ContenutoFoglioImportatoLayout() {
		super("contenuto_foglio_importato", 
				new GWTRestDataSource("ContenutoFoglioImportatoDataSource", "idFoglio", FieldType.TEXT), 
				new ConfigurableFilter("contenuto_foglio_importato"),
				new ContenutoFoglioImportatoList("contenuto_foglio_importato"), 
				new ContenutoFoglioImportatoDetail("contenuto_foglio_importato"), 
				null, 
				null, 
				null
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
		return true;
	}

	public static boolean isAbilToDel() {
		return false;
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource gWTRestDataSource = (GWTRestDataSource) getList().getDataSource();
		gWTRestDataSource.setForceToShowPrompt(false);

		return gWTRestDataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		} else {
			return super.extractRecords(fields);
		}
	}
	
	@Override
	public void onSaveButtonClick() {
		final Record record = ((ContenutoFoglioImportatoDetail)detail).getRecordToSave();		
		if(detail.validate()) {
			if(detail instanceof ContenutoFoglioImportatoDetail) {
				realSave(record);
			} 
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	@Override
	protected DSCallback buildDSCallback() {
		DSCallback callback = new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					try {
						list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								viewMode();
								Layout.hideWaitPopup();
								Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					if (!fullScreenDetail) {
						reloadListAndSetCurrentRecord(savedRecord);
					}
				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		return callback;
	}
	
	@Override
	public String getViewDetailTitle() {
		final Record record = detail.getRecordToSave();
		return I18NUtil.getMessages().contenuto_foglio_importato_view_title(
				record.getAttribute("nrRiga"),record.getAttribute("nomeFile"));		
	}
	
	@Override
	public String getEditDetailTitle() {
		final Record record = detail.getRecordToSave();
		return I18NUtil.getMessages().contenuto_foglio_importato_edit_title(
				record.getAttribute("nrRiga"),record.getAttribute("nomeFile"));
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