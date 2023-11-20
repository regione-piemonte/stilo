/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;

public class SavePreferenceWindow extends Window {

	private Boolean showFlgPubblica;
	private SavePreferenceForm form;
	private SavePreferenceAction savePreferenceAction;
	private SavePreferenceRecordAction savePreferenceRecordAction;

	public SavePreferenceWindow(String title, GWTRestDataSource datasource, boolean defaultValue, SavePreferenceAction action) {
		this.savePreferenceAction = action;
		this.showFlgPubblica = false;
		build(title, datasource, defaultValue);
	}
	
	public SavePreferenceWindow(String title, GWTRestDataSource datasource, boolean defaultValue, boolean showFlgPubblica, SavePreferenceRecordAction action) {
		this.savePreferenceRecordAction = action;
		this.showFlgPubblica = showFlgPubblica;
		build(title, datasource, defaultValue);
	}
	
	private void build(String title, GWTRestDataSource datasource, boolean defaultValue) {
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setTitle(title);
		if(isAttivaGestionePreferenzePubbliche()) {
			setWidth(430);
		} else {
			setWidth(310);
		}
		setHeight(100);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		form = new SavePreferenceForm(this, datasource, defaultValue);
		addItem(form);
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
	}
	
	public void savePreferenceAction(Record record) {
		if(savePreferenceAction != null) {
			savePreferenceAction.execute(record != null ? record.getAttribute("prefName") : null);
		} else if(savePreferenceRecordAction != null) {
			savePreferenceRecordAction.execute(record);
		}
		hide();
	}
	
	public boolean isAttivaGestionePreferenzePubbliche() {
		return showFlgPubblica != null && showFlgPubblica && Layout.isPrivilegioAttivo("UT/PRP");
	}

	public void manageOnCloseClick() {
		hide();
	}

	public SavePreferenceForm getForm() {
		return form;
	}

	public void setForm(SavePreferenceForm form) {
		this.form = form;
	}


	@Override
	protected void onDestroy() {
		if(form != null) {
			form.destroy();
		}
	}
}
