/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SoggettiAbilitatiCasellaWindow extends ModalWindow {

	protected SoggettiAbilitatiCasellaWindow window;

	protected SoggettiAbilitatiCasellaDetail detail;
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;

	protected CaselleEmailLayout caselleEmailLayout;

	public SoggettiAbilitatiCasellaWindow(CaselleEmailLayout caselleEmailLayout, Record record) {

		super("soggetti_abilitati_casella", true);

		window = this;

		this.caselleEmailLayout = caselleEmailLayout;

		setTitle(I18NUtil.getMessages().caselleEmail_soggettiAbilitatiCasella_window_title(""));

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		// TODO
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SoggettiAbilitatiCasellaDatasource", "idCasella", FieldType.TEXT);

		detail = new SoggettiAbilitatiCasellaDetail("soggetti_abilitati_casella");
		detail.setHeight100();
		detail.setDataSource(lGwtRestDataSource);

		/*
		 * BUTTON DETAIL
		 */

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onSaveButtonClick();
			}
		});

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload();
			}
		});

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);

		VLayout detailLayout = new VLayout();
		detailLayout.setOverflow(Overflow.HIDDEN);
		setOverflow(Overflow.AUTO);

		detailLayout.setMembers(detail, detailToolStrip);

		detailLayout.setHeight100();
		detailLayout.setWidth100();
		setBody(detailLayout);

		setIcon("buttons/grant.png");

		manageLoadDetail(record, detail.getRecordNum(), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record record = response.getData()[0];
				window.setTitle(I18NUtil.getMessages().caselleEmail_soggettiAbilitatiCasella_window_title(record.getAttribute("indirizzoEmail")));
			}
		});
	}

	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if (detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	protected void realSave(final Record record) {
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		try {
			detail.getDataSource().updateData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						caselleEmailLayout.doSearch();
						Layout.hideWaitPopup();
						manageOnCloseClick();
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}

	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		((GWTRestDataSource) detail.getDataSource()).getData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					detail.editRecord(record, recordNum);
					detail.getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		});
	}

	public void reload() {
		Record record = new Record(detail.getValuesManager().getValues());
		((GWTRestDataSource) detail.getDataSource()).getData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					detail.editRecord(record);
					detail.getValuesManager().clearErrors(true);
				}
			}
		});
	}

	@Override
	public void manageOnCloseClick() {
		
		markForDestroy();
	}

}