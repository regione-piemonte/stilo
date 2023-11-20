/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem.RemoveButton;

public class ItemLavorazioneMailItem extends ReplicableItem {

	private Record detailRecord;

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {

		int i = getAllCanvas().length;
		return new ItemLavorazioneMailCanvas(this);
	}

	public void downloadFile(ServiceCallback<Record> lDsCallback) {

	}

	@Override
	public ReplicableCanvas onClickNewButton() {

		return super.onClickNewButton();
	}

	public void trasformaInAllegatoEmail(Record record) {

	}

	public boolean showStampaFileButton() {
		return false;
	}

	public boolean isDettaglioMail() {
		return false;
	}
	
	@Override
	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = new HLayout();
		addButtonsLayout.setMembersMargin(3);
		for (ImgButton button : createAddButtons()) {
			button.setTabIndex(-1);
			button.setCanFocus(false);
			addButtonsLayout.addMember(button);
		}
		return addButtonsLayout;
	}
	
	@Override
	protected RemoveButton createRemoveButton(final VLayout lVLayout, final HLayout lHLayout) {
		// Creo il bottone di remove
		RemoveButton removeButton = new RemoveButton();
		removeButton.setShowDown(false);
		removeButton.setShowRollOver(false);
		removeButton.setMargin(2);
		removeButton.setSize(20);
		removeButton.setSrc("buttons/remove.png");
		removeButton.setPrompt(I18NUtil.getMessages().removeButton_prompt());
		removeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setUpClickRemoveHandler(lVLayout, lHLayout);
			}
		});
		removeButton.setTabIndex(-1);
		removeButton.setCanFocus(false);
		return removeButton;
	}

}
